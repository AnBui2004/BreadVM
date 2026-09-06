package hey.bread.vm.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hey.bread.vm.R;
import hey.bread.vm.BreadVmApplication;
import hey.bread.vm.adapter.LogsAdapter;
import hey.bread.vm.logger.BreadStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class LoggerFragment extends Fragment {

    View view;
    private final String CREDENTIAL_SHARED_PREF = "settings_prefs";
    private LogsAdapter mLogAdapter;
    private RecyclerView logList;
    private Timer _timer = new Timer();
    private TimerTask t;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        view = inflater.inflate(R.layout.fragment_logs, container, false);
        activity = getActivity();
        LinearLayoutManager layoutManager = new LinearLayoutManager(BreadVmApplication.getApp());
        mLogAdapter = new LogsAdapter(layoutManager, BreadVmApplication.getApp(), false);
        logList = (RecyclerView) view.findViewById(R.id.recyclerLog);
        logList.setAdapter(mLogAdapter);
        logList.setLayoutManager(layoutManager);
        mLogAdapter.scrollToLastPosition();
        try {
            Process process = Runtime.getRuntime().exec("logcat -e");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            Process process2 = Runtime.getRuntime().exec("logcat -w");
            BufferedReader bufferedReader2 = new BufferedReader(
                    new InputStreamReader(process2.getInputStream()));

            t = new TimerTask() {
                @Override
                public void run() {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (bufferedReader.readLine() != null || bufferedReader2.readLine() != null) {
                                    String logLine = bufferedReader.readLine();
                                    String logLine2 = bufferedReader2.readLine();
                                    BreadStatus.logError("<font color='red'>[E] "+logLine+"</font>");
                                    BreadStatus.logError("<font color='#FFC107'>[W] "+logLine2+"</font>");
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            };
            _timer.scheduleAtFixedRate(t, (int) (0), (int) (100));
        } catch (IOException e) {
            Toast.makeText(activity, "There was an error: " + Log.getStackTraceString(e), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        return view;
    }

}
