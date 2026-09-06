package hey.bread.vm.manager;

import android.content.Context;

import hey.bread.qemu.MainSettingsManager;
import hey.bread.vm.AppConfig;
import hey.bread.vm.setupwizard.SetupFeatureCore;
import hey.bread.vm.utils.FileUtils;

public class FirmwareManager {
    public static boolean isExistOne() {
        return FileUtils.isFileExists(AppConfig.basefiledir + "bios-256k.bin") ||
                FileUtils.isFileExists(AppConfig.basefiledir + "QEMU_EFI.img") ||
                FileUtils.isFileExists(AppConfig.basefiledir + "QEMU_VARS.img") ||
                FileUtils.isFileExists(AppConfig.basefiledir + "RELEASEX64_OVMF.fd") ||
                FileUtils.isFileExists(AppConfig.basefiledir + "RELEASEX64_OVMF_VARS.fd");
    }

    public static void extract(Context context, boolean isUseUefi) {
        if (MainSettingsManager.useDefaultBios(context)) {
            String arch = MainSettingsManager.getArch(context);

            FileUtils.createDirectory(AppConfig.basefiledir);

            if (arch.equals("ARM64")) {
                if (!FileUtils.isFileExists(AppConfig.basefiledir + "QEMU_EFI.img"))
                    SetupFeatureCore.copyAssetToFile(context, "firmware/QEMU_EFI.img", AppConfig.basefiledir + "QEMU_EFI.img");

                if (!FileUtils.isFileExists(AppConfig.basefiledir + "QEMU_VARS.img"))
                    SetupFeatureCore.copyAssetToFile(context, "firmware/QEMU_VARS.img", AppConfig.basefiledir + "QEMU_VARS.img");
            } else if (arch.equals("X86_64") && (MainSettingsManager.getuseUEFI(context) || isUseUefi)) {
                if (!FileUtils.isFileExists(AppConfig.basefiledir + "edk2-x86_64-code.fd"))
                    SetupFeatureCore.copyAssetToFile(context, "firmware/edk2-x86_64-code.fd", AppConfig.basefiledir + "edk2-x86_64-code.fd");

                if (!FileUtils.isFileExists(AppConfig.basefiledir + "edk2-x86_64-vars.fd"))
                    SetupFeatureCore.copyAssetToFile(context, "firmware/edk2-x86_64-vars.fd", AppConfig.basefiledir + "edk2-x86_64-vars.fd");
            } else if (arch.equals(MainSettingsManager.I386_ARCH) && (MainSettingsManager.getuseUEFI(context) || isUseUefi)) {
                if (!FileUtils.isFileExists(AppConfig.basefiledir + "edk2-i386-code.fd"))
                    SetupFeatureCore.copyAssetToFile(context, "firmware/edk2-i386-code.fd", AppConfig.basefiledir + "edk2-i386-code.fd");

                if (!FileUtils.isFileExists(AppConfig.basefiledir + "edk2-i386-vars.fd"))
                    SetupFeatureCore.copyAssetToFile(context, "firmware/edk2-i386-vars.fd", AppConfig.basefiledir + "edk2-i386-vars.fd");
            } else {
                if (!FileUtils.isFileExists(AppConfig.basefiledir + "bios-256k.bin"))
                    SetupFeatureCore.copyAssetToFile(context, "firmware/bios-256k.bin", AppConfig.basefiledir + "bios-256k.bin");
            }
        }
    }

    public static void erase() {
        FileUtils.delete(AppConfig.basefiledir + "QEMU_VARS.img");
        FileUtils.delete(AppConfig.basefiledir + "edk2-i386-vars.fd");
        FileUtils.delete(AppConfig.basefiledir + "edk2-x86_64-vars.fd");
    }

    public static boolean isAVarFileExist(String folderPath) {
        return FileUtils.isFileExists(folderPath + "QEMU_VARS.img") ||
                FileUtils.isFileExists(folderPath + "edk2-i386-vars.fd") ||
                FileUtils.isFileExists(folderPath + "edk2-x86_64-vars.fd");
    }

    public static void erase(String folderPath) {
        FileUtils.delete(folderPath + "QEMU_VARS.img");
        FileUtils.delete(folderPath + "edk2-i386-vars.fd");
        FileUtils.delete(folderPath + "edk2-x86_64-vars.fd");
    }
}
