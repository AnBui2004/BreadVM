package hey.bread.vm;

import android.content.Context;

import hey.bread.vm.utils.DeviceUtils;

import java.io.File;

/**
 * @author dev
 */
public class AppConfig {

    // App Config
    public static String appVersion;
    public static int appVersionCode;
    public static final int standardSetupVersion = 922202607;
    public static final int coreSetupVersion = 0;
    public static String website = "https://anbui.ovh/";
    public static String websiteRaw = "https://raw.githubusercontent.com/AnBui2004/BreadVM/refs/heads/master/web/";
    public static String bootstrapfileslink = websiteRaw + "/data/setupfiles4.json";
    public static String community = website + "community";
    public static String dataRaw = websiteRaw + "data/";
    public static String updateJson = dataRaw + "UpdateConfig.json";

    public static String gitHub = "https://github.com/AnBui2004/BreadVM";

    // App config
    public static String datadirpath(Context activity) {
        File f = new File(activity.getExternalFilesDir("data") + "/BreadVM");
        return activity.getExternalFilesDir("data") + "/VBreadVM";
        //return FileUtils.getExternalFilesDirectory(activity).getPath();
    }
    public static String internalDataDirPath = "/data/data/hey.bread.vm/files/";
    public static String basefiledir = "";
    public static String maindirpath = "/sdcard/Documents/BreadVM/";
    public static String recyclebin = maindirpath + "RecycleBin/";
    //public static String basefiledir = datadirpath(SplashActivity.activity) + "/.qemu/";
    //public static String maindirpath = FileUtils.getExternalFilesDirectory(SplashActivity.activity).getPath() + "/";
    public static String sharedFolder = maindirpath + "SharedFolder/";
    public static String downloadsFolder = maindirpath + "Downloads/";
    public static String romsdatajson = maindirpath + "roms-data.json";
    public static String vmFolder = maindirpath + "roms/";
    public static String cvbiFolder = maindirpath + "cvbi/";
    public static String lastCrashLogPath = internalDataDirPath + "logs/lastcrash.txt";

    public static String neededPkgs() {
        if (DeviceUtils.isArm()) {
            return "sudo bash aria2 tar dwm xterm libslirp pixman" +
                    " spice libusbredirparser sndio-libs sdl2 sdl2_image-dev libepoxy-dev virglrenderer rdma-core fluxbox" +
                    " libusb libaio ncurses-libs curl libnfs gtk+3.0 libseccomp jack liburing pulseaudio" +
                    " mesa-dri-gallium mesa-vulkan-swrast vulkan-loader mesa-utils mesa-egl mesa-gbm mesa-vulkan-ati mesa-vulkan-broadcom mesa-vulkan-freedreno mesa-vulkan-panfrost" +
                    " capstone libcbor snappy lzo ndctl keyutils-libs vde2-libs libbpf linux-pam fuse3-libs libssh vte3 iasl cdrkit";
        } else {
            return "sudo bash aria2 tar dwm xterm libslirp pixman" +
                    " spice libusbredirparser sndio-libs sdl2 sdl2_image-dev libepoxy-dev virglrenderer rdma-core fluxbox" +
                    " libusb libaio ncurses-libs curl libnfs gtk+3.0 libseccomp jack liburing pulseaudio" +
                    " mesa-dri-gallium mesa-vulkan-swrast vulkan-loader mesa-utils mesa-egl" +
                    " capstone libcbor snappy lzo ndctl keyutils-libs vde2-libs libbpf linux-pam fuse3-libs libssh vte3 iasl cdrkit";
        }
    }

    public static String neededPkgs32bit() {
        if (DeviceUtils.isArm()) {
            return "sudo bash aria2 tar dwm xterm libslirp pixman" +
                    " spice libusbredirparser sndio-libs sdl2 sdl2_image-dev libepoxy-dev virglrenderer rdma-core fluxbox" +
                    " libusb libaio ncurses-libs curl libnfs gtk+3.0 libseccomp jack liburing pulseaudio" +
                    " mesa-dri-gallium mesa-vulkan-swrast vulkan-loader mesa-utils mesa-egl" +
                    " capstone libcbor snappy lzo ndctl keyutils-libs vde2-libs libbpf linux-pam fuse3-libs libssh vte3 iasl cdrkit";
        } else {
            return "sudo bash aria2 tar dwm xterm libslirp pixman" +
                    " spice libusbredirparser sndio-libs sdl2 sdl2_image-dev libepoxy-dev virglrenderer rdma-core fluxbox" +
                    " libusb libaio ncurses-libs curl libnfs gtk+3.0 libseccomp jack liburing pulseaudio" +
                    " mesa-dri-gallium mesa-vulkan-swrast vulkan-loader mesa-utils mesa-egl" +
                    " capstone libcbor snappy lzo ndctl keyutils-libs vde2-libs libbpf linux-pam fuse3-libs libssh vte3 libatomic iasl cdrkit";
        }
    }

    public static boolean needreinstallsystem = false;

    public static String virtIOWinUrl = "https://fedorapeople.org/groups/virt/virtio-win/direct-downloads/archive-virtio/virtio-win-0.1.285-1/virtio-win.iso";
    public static String virtIOWinUrlMd5 = "9e650d0e7c6e017a91ca299c8f7ed766";

    public static boolean isGmsAvailable = false;
}
