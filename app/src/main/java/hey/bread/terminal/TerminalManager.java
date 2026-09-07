package hey.bread.terminal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.termux.app.TermuxActivity;
import com.termux.app.TermuxService;

import java.io.File;

import hey.bread.vm.R;
import hey.bread.vm.settings.SettingsData;
import hey.bread.vm.utils.DeviceUtils;
import hey.bread.vm.utils.DialogUtils;
import hey.bread.vm.utils.FileUtils;

public class TerminalManager {
    public static void openTerminal(Activity activity) {
        if (SettingsData.terminalWarning(activity)) {
            activity.startActivity(new Intent(activity, TermuxActivity.class));
        } else {
            DialogUtils.threeDialog(
                    activity,
                    activity.getString(R.string.warning),
                    activity.getString(R.string.terminal_warning_content),
                    activity.getString(R.string.accept),
                    activity.getString(R.string.accept_and_dont_show_again),
                    activity.getString(R.string.close),
                    true,
                    R.drawable.warning_48px,
                    true,
                    () -> {
                        activity.startActivity(new Intent(activity, TermuxActivity.class));
                    },
                    () -> {
                        SettingsData.terminalWarning(activity, true);
                        activity.startActivity(new Intent(activity, TermuxActivity.class));
                    },
                    null,
                    null
            );
        }
    }

    public static String[] termuxArguments(Context context) {
        String nativeDir = context.getApplicationInfo().nativeLibraryDir + "/";
        String executableContent = """
                #!/system/bin/sh
                unset LD_PRELOAD
                unset TMPDIR
                unset XDG_RUNTIME_DIR
                unset PREFIX
                unset BOOTCLASSPATH
                unset ANDROID_ART_ROOT
                unset ANDROID_DATA
                unset ANDROID_I18N_ROOT
                unset ANDROID_ROOT
                unset ANDROID_TZDATA_ROOT
                unset COLORTERM
                unset DEX2OATBOOTCLASSPATH
                export USE_HEAP=1
                export DISPLAY=:0
                export PULSE_SERVER=127.0.0.1
                export PROOT_LOADER=${nativeDir}libproot-loader.so
                ${loader32}
                SHELL=/bin/bash
                HOME=/root
                LANG=C.UTF-8
                PATH=/usr/local/sbin:/usr/local/bin:/bin:/usr/bin:/sbin:/usr/sbin:/usr/games:/usr/local/games
                cmd="${nativeDir}libproot.so"
                cmd+=" --link2symlink"
                cmd+=" -0"
                cmd+=" -r /data/data/hey.bread.vm/files/distro"
                cmd+=" -b /dev"
                cmd+=" -b /proc"
                cmd+=" -b /sys"
                cmd+=" -b /data/data/hey.bread.vm/files"
                cmd+=" -b /data/data/hey.bread.vm/files/distro/root:/dev/shm"
                cmd+=" -b /data/data/hey.bread.vm/files/home:/home"
                cmd+=" -b /data/data/hey.bread.vm/files/usr/tmp:/tmp"
                cmd+=" -b /sdcard"
                cmd+=" -b /storage"
                cmd+=" -w /root"
                cmd+=" /bin/sh"
                exec $cmd
                """;

        executableContent = executableContent.replace("${loader32}", DeviceUtils.is64bit() ? "export PROOT_LOADER_32=${nativeDir}libproot-loader32.so" : "");

        return new String[]{ "-c", executableContent.replace("${nativeDir}", nativeDir) };
    }
}
