package hey.bread.vm.manager;

import android.util.Log;

import hey.bread.qemu.Config;
import hey.bread.vm.BreadVmApplication;
import hey.bread.vm.utils.FileUtils;
import hey.bread.vm.sound.StreamAudio;

public class VmAudioManager {
    private static final String TAG = "VmAudioManager";
    public static final StreamAudio streamAudio = new StreamAudio(BreadVmApplication.getContext());
    public static String currentVmId = "";

    public static void stream(String vmID) {
        if (currentVmId.equals(vmID) && streamAudio.isPlaying()) return;

        currentVmId = vmID;

        if (streamAudio.isPlaying()) streamAudio.stop();

        streamAudio.setFile(VmFileManager.findAudioRaw(BreadVmApplication.getContext(), vmID));
        streamAudio.play();

        new Thread(() -> {
            while (streamAudio.isPlaying() && FileUtils.isFileExists(Config.getLocalQMPSocketPath(vmID))) {
                try { Thread.sleep(2000); } catch (InterruptedException ignored) { return; }
            }

            Log.d(TAG, "Stoped.");
            streamAudio.stop();
        }).start();
    }

    public static void set(String vmID) {
        currentVmId = vmID;
        streamAudio.setFile(VmFileManager.findAudioRaw(BreadVmApplication.getContext(), currentVmId));
    }
}
