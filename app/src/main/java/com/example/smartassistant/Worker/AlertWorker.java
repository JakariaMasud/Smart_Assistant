package com.example.smartassistant.Worker;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.PowerManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import static android.content.Context.MODE_PRIVATE;
import static com.example.smartassistant.View.App.EVENT_ID;

public class AlertWorker extends Worker {
    private static final String TAG = "AlertIntentService";
    private static String eventId;
    SharedPreferences preferences;
    AudioManager audioManager;
    PowerManager powerManager;

    PowerManager.WakeLock wakeLock;
    public AlertWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        powerManager=(PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        wakeLock=powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"TESTING:WAKE LOCK");
        wakeLock.acquire();
        Log.e("wake lock accuired","done");
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data=getInputData();
        eventId=data.getString(EVENT_ID);
        Log.e(TAG,"TRIGGERD");

        preferences=getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        audioManager=(AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        int mode=audioManager.getRingerMode();
        if(mode==AudioManager.RINGER_MODE_NORMAL || mode==AudioManager.RINGER_MODE_VIBRATE){
            String notificationTitle=preferences.getString("notificationTitle","alert service");
            String notificationDescription=preferences.getString("notificationDescription","your phone will be silence mode in a few moment");
            NotificationHelper.displayNotification(getApplicationContext(),notificationTitle,notificationDescription);
        }
        wakeLock.release();
        return Result.success();
    }
}
