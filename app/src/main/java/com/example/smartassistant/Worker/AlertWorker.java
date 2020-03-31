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

import com.example.smartassistant.DI.ChildWorkerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import static com.example.smartassistant.View.App.EVENT_ID;

public class AlertWorker extends Worker {
    private static final String TAG = "AlertIntentService";
    private static String eventId;
    SharedPreferences preferences;
    AudioManager audioManager;
    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;


    public AlertWorker(Context context, WorkerParameters parameters, SharedPreferences preferences,
                       AudioManager audioManager, PowerManager powerManager) {
        super(context, parameters);
        this.preferences=preferences;
        this.audioManager=audioManager;
        this.powerManager=powerManager;
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
        int mode=audioManager.getRingerMode();
        if(mode==AudioManager.RINGER_MODE_NORMAL || mode==AudioManager.RINGER_MODE_VIBRATE){
            String notificationTitle=preferences.getString("notificationTitle","alert service");
            String notificationDescription=preferences.getString("notificationDescription","your phone will be silence mode in a few moment");
            NotificationHelper.displayNotification(getApplicationContext(),notificationTitle,notificationDescription);
        }
        wakeLock.release();
        return Result.success();
    }


    public static class Factory implements ChildWorkerFactory {
        Provider<SharedPreferences>  preferences;
        Provider<AudioManager>audioManager;
        Provider <PowerManager> powerManager;

        @Inject
        public Factory(final Provider<SharedPreferences> preferences, final Provider<AudioManager> audioManager, final Provider<PowerManager> powerManager) {
            this.preferences = preferences;
            this.audioManager = audioManager;
            this.powerManager = powerManager;
        }

        @Override
        public Worker create(Context context, WorkerParameters parameters) {
            return new AlertWorker(context,parameters,preferences.get(),audioManager.get(),powerManager.get());
        }
    }
}
