package hey.bread.vm.crashtracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import hey.bread.qemu.MainSettingsManager;
import hey.bread.vm.AppConfig;
import hey.bread.vm.R;
import hey.bread.vm.SplashActivity;
import hey.bread.vm.databinding.ActivityLastCrashBinding;
import hey.bread.vm.utils.ClipboardUltils;
import hey.bread.vm.utils.DialogUtils;
import hey.bread.vm.utils.FileUtils;
import hey.bread.vm.utils.ProgressDialog;
import hey.bread.vm.utils.UIUtils;
import hey.bread.terminal.Terminal2;

public class LastCrashActivity extends AppCompatActivity {
    ActivityLastCrashBinding binding;

    String log = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIUtils.edgeToEdge(this);
        binding = ActivityLastCrashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        UIUtils.setOnApplyWindowInsetsListener(findViewById(R.id.main));

        read();

        binding.btnReport.setOnClickListener(v -> {
            DialogUtils.twoDialog(
                    this,
                    getString(R.string.report),
                    getString(R.string.send_log_to_server_content),
                    getString(R.string.send),
                    getString(R.string.cancel),
                    true,
                    R.drawable.send_24px,
                    true,
                    this::send,
                    null,
                    null
            );
        });

        binding.btnCopy.setOnClickListener(v -> ClipboardUltils.copyToClipboard(this, log));

        MainSettingsManager.setShowLastCrashLog(this, false);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(LastCrashActivity.this, SplashActivity.class));
                finish();
            }
        });
    }

    private void read() {
        log = FileUtils.isFileExists(AppConfig.lastCrashLogPath) ? FileUtils.readAFile(AppConfig.lastCrashLogPath) : "";

        if (log.isEmpty()) {
            binding.btnReport.setVisibility(View.GONE);
            binding.tvContent.setText(R.string.there_are_no_logs);
            return;
        }

        binding.tvContent.setText(log.length() > 100000 ? log.substring(0, 100000) + "..." : log);
    }

    private void send() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setText(getString(R.string.sending));
        progressDialog.show();

        if (FileUtils.isFileExists(AppConfig.lastCrashLogPath)) {
            new Thread(() -> {
                String fileName = getResources().getInteger(R.integer.app_version_code) + "_" + System.currentTimeMillis() + ".txt";

                if (!FileUtils.copyFile(AppConfig.lastCrashLogPath, AppConfig.internalDataDirPath + "usr/tmp", fileName)) {
                    runOnUiThread(() -> {
                        progressDialog.dismiss();
                        DialogUtils.oopsDialog(LastCrashActivity.this, getString(R.string.send_the_report_failed_content));
                    });

                    return;
                }

                runOnUiThread(() -> {
                    Terminal2 terminal2 = new Terminal2(this);
                    terminal2.setDefaultShellBash();
                    terminal2.execute("curl -F \"file=@/tmp/" + fileName + "\" -H \"X-Upload-Token:2026_07_30\" https://go.anbui.ovh/uploadlog; rm /tmp/" + fileName, new Terminal2.Terminal2Callback() {
                        @Override
                        public void onRunning(String command, String newLine) {
                            // Nothing to do.
                        }

                        @Override
                        public void onFinished(String command, String log, int status) {
                            if (isFinishing() || isDestroyed()) {
                                return;
                            }

                            // "Upload finished" = response from the server.
                            if (log.contains("Upload finished")) {
                                new Handler(Looper.getMainLooper()).post(() -> {
                                    progressDialog.dismiss();

                                    DialogUtils.oneDialog(
                                            LastCrashActivity.this,
                                            getString(R.string.done),
                                            getString(R.string.thank_you_for_sending_the_report),
                                            R.drawable.check_24px
                                    );

                                    binding.btnReport.setVisibility(View.GONE);
                                });
                            } else {
                                new Handler(Looper.getMainLooper()).post(() -> {
                                    progressDialog.dismiss();
                                    DialogUtils.oopsDialog(LastCrashActivity.this, getString(R.string.send_the_report_failed_content));
                                });
                            }
                        }

                        @Override
                        public void onError(String command, Exception exception) {
                            if (isFinishing() || isDestroyed()) {
                                return;
                            }

                            new Handler(Looper.getMainLooper()).post(() -> {
                                progressDialog.dismiss();
                                DialogUtils.oopsDialog(LastCrashActivity.this, getString(R.string.send_the_report_failed_content));
                            });
                        }
                    });
                });
            }).start();
        } else {
            progressDialog.dismiss();
            DialogUtils.oopsDialog(LastCrashActivity.this, getString(R.string.send_the_report_failed_content));
        }
    }
}