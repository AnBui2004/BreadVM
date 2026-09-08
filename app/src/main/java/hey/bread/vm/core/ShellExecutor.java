package hey.bread.vm.core;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import hey.bread.vm.AppConfig;
import hey.bread.vm.logger.BreadStatus;
import hey.bread.vm.utils.TextUtils;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ShellExecutor {
    private static final String TAG = "ShellExecutor";
    private Process shellExecutorProcess;
    private ExecutorService executorService;
    private Future<?> processFuture;

    public ShellExecutor() {
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void exec(String command) {
        String logPath = AppConfig.maindirpath + "shell-executor.log";
        String shellPath = "/system/bin/sh";

        Runnable processRunnable = () -> {
            try (FileWriter logWriter = new FileWriter(logPath, true)) {
                ProcessBuilder pb = new ProcessBuilder(shellPath);
                pb.redirectErrorStream(true);

                shellExecutorProcess = pb.start();

                OutputStream outputStream = shellExecutorProcess.getOutputStream();

                Log.d(TAG, "Running command: " + TextUtils.redactSecrets(command));
                logWriter.write("Running command: " + TextUtils.redactSecrets(command) + "\n");
                outputStream.write((command + "\n").getBytes());
                outputStream.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(shellExecutorProcess.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    logWriter.write(line + "\n");
                    logWriter.flush();
                    Log.d(TAG, line);
                    String finalLine = line;
                    new Handler(Looper.getMainLooper()).post(() -> BreadStatus.logInfo(TAG + " > " + finalLine));
                }
            } catch (IOException e) {
                Log.e(TAG, "Error starting ShellExecutor", e);
                BreadStatus.logInfo(TAG + " > " + e.toString());
            }
        };

        processFuture = executorService.submit(processRunnable);
    }
}