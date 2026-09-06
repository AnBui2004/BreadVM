package hey.bread.vm.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import hey.bread.vm.AppConfig;

public class FCMManager {
    private static final String TAG = "FCMManager";

    public static void subscribe() {
        if (!AppConfig.isGmsAvailable) return;

        FirebaseMessaging.getInstance()
                .subscribeToTopic("breadvmandroidgithub")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Subscribed: breadvmandroidgithub");
                    } else {
                        Log.e(TAG, "Subscription failed: breadvmandroidgithub.", task.getException());
                    }
                });
    }

    public static void unSubscribe() {
        if (!AppConfig.isGmsAvailable) return;

        FirebaseMessaging.getInstance()
                .unsubscribeFromTopic("breadvmandroidgithub")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Unsubscribed: breadvmandroidgithub");
                    } else {
                        Log.e(TAG, "Cancellation failed: breadvmandroidgithub.", task.getException());
                    }
                });
    }
}
