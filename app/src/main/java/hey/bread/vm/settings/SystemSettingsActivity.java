package hey.bread.vm.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import hey.bread.qemu.MainSettingsManager;
import hey.bread.vm.databinding.ActivitySystemSettingsBinding;
import hey.bread.vm.utils.UIUtils;

import java.util.Objects;

public class SystemSettingsActivity extends AppCompatActivity {

    ActivitySystemSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.edgeToEdge(this);
        binding = ActivitySystemSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(view -> finish());
        initialize();
    }

    private void initialize() {
        binding.swCopyFile.setOnCheckedChangeListener((buttonView, isChecked) -> MainSettingsManager.setcopyFile(this, isChecked));
        binding.lnCopyFile.setOnClickListener(v -> binding.swCopyFile.toggle());

        binding.swQuickStart.setOnCheckedChangeListener((buttonView, isChecked) -> MainSettingsManager.setQuickStart(this, isChecked));
        binding.lnQuickStart.setOnClickListener(v -> binding.swQuickStart.toggle());

        binding.swAlwaysShowLogs.setOnCheckedChangeListener((buttonView, isChecked) -> SettingsData.alwaysShowLog(this, isChecked));
        binding.lnAlwaysShowLogs.setOnClickListener(v -> binding.swAlwaysShowLogs.toggle());

        binding.swCopyFile.setChecked(MainSettingsManager.copyFile(this));
        binding.swQuickStart.setChecked(MainSettingsManager.getQuickStart(this));
        binding.swAlwaysShowLogs.setChecked(SettingsData.alwaysShowLog(this));

        binding.lnLanguage.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                try {
                    Intent intent = new Intent(Settings.ACTION_APP_LOCALE_SETTINGS);
                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivity(intent);
                } catch (Exception e) {
                    showLanguageSelector();
                }
            } else {
                showLanguageSelector();
            }
        });

        binding.lnUpdate.setOnClickListener(v -> startActivity(new Intent(this, UpdaterActivity.class)));
    }

    void showLanguageSelector() {
        ItemSettingsSelector.language(this, ItemSettingsSelector.getLanguagePosition(this, MainSettingsManager.getLang(this)), (position, name, value) -> {
            if (!value.isEmpty()) {
                AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(value)
                );
            } else {
                AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.getEmptyLocaleList()
                );
            }

            MainSettingsManager.setLang(this, value);
        });
    }
}