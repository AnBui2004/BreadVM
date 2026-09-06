package hey.bread.vm.manager;

import android.content.Context;

import hey.bread.terminal.Terminal2;

public class ProcessManager {
    public static boolean isQemuRunning(Context context) {
        return new Terminal2(context).executeOnThisThread("ps -e").contains("qemu-system-");
    }
}
