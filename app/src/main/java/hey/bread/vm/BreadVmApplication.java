package hey.bread.vm;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceManager;

import com.google.android.material.color.DynamicColors;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import hey.bread.qemu.Config;
import hey.bread.qemu.MainSettingsManager;
import hey.bread.vm.utils.FileUtils;
import hey.bread.vm.utils.GmsChecker;
import hey.bread.vm.utils.PackageUtils;
import hey.bread.vm.utils.UIUtils;

import java.lang.ref.WeakReference;

import hey.bread.vm.crashtracker.CrashHandler;

public class BreadVmApplication extends Application {
    public static BreadVmApplication appContext;
    private static WeakReference<Context> context;

    public static Context getContext() {
        return context.get();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        context = new WeakReference<>(getApplicationContext());

        Thread.setDefaultUncaughtExceptionHandler(
                new CrashHandler(this)
        );

        try {
            Class.forName("android.os.AsyncTask");
        } catch (Throwable ignore) {
            // ignored
        }
        setupTheme();
        setupLocale();

//        Locale locale = Locale.getDefault();
//        String language = locale.getLanguage();

//		if (language.contains("ar")) {
//			overrideFont("DEFAULT", R.font.cairo_regular);
//		} else {
//			overrideFont("DEFAULT", R.font.gilroy);
//		}
        setupAppConfig(getApplicationContext());

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                if (MainSettingsManager.getDynamicColor(activity))
                    DynamicColors.applyToActivityIfAvailable(activity);
            }

            @Override
            public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
            }
        });

        FirebaseCrashlytics.getInstance().log("App started: " + AppConfig.appVersion);

        if (GmsChecker.isAvailable(this)) {
            AppConfig.isGmsAvailable = true;
            FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.APP_OPEN, null);
        } else {
            FirebaseCrashlytics.getInstance().log("Device does not support GMS.");
        }
    }

    private void setupTheme() {
        UIUtils.setDarkOrLight(MainSettingsManager.getTheme(this));

//        if (MainSettingsManager.getDynamicColor(this))
//            DynamicColors.applyToActivitiesIfAvailable(this);

//        setTheme(R.style.AppTheme);
    }

    private void setupLocale() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String languageCode = sharedPreferences.getString("language", "");

        if (!languageCode.isEmpty()) {
            AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(languageCode)
            );
        } else {
            AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.getEmptyLocaleList()
            );
        }
    }

    public static Context getApp() {
        return appContext;
    }

    private void setupAppConfig(Context _context) {
        AppConfig.appVersion = PackageUtils.getThisVersionName(_context);
        AppConfig.appVersionCode = PackageUtils.getThisVersionCode(_context);
        AppConfig.internalDataDirPath = getFilesDir().getPath() + "/";
        AppConfig.basefiledir = AppConfig.datadirpath(_context) + "/.qemu/";
        AppConfig.maindirpath = FileUtils.getExternalFilesDirectory(_context).getPath() + "/";
        AppConfig.sharedFolder = AppConfig.maindirpath + "SharedFolder/";
        AppConfig.downloadsFolder = AppConfig.maindirpath + "Downloads/";
        AppConfig.romsdatajson = AppConfig.maindirpath + "roms-data.json";
        AppConfig.vmFolder = AppConfig.maindirpath + "roms/";
        AppConfig.recyclebin = AppConfig.maindirpath + "RecycleBin/";
        AppConfig.cvbiFolder = AppConfig.maindirpath + "cvbi/";
        AppConfig.lastCrashLogPath = AppConfig.internalDataDirPath + "logs/lastcrash.txt";

        Config.cacheDir = _context.getCacheDir().getAbsolutePath();
        Config.storagedir = Environment.getExternalStorageDirectory().toString();
    }
}
