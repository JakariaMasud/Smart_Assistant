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

public class SilentWorker extends Worker {
    private static String eventId;
    PowerManager.WakeLock wakeLock;
    SharedPreferences preferences;
    SharedPreferences.Editor sfEditor;
    public SilentWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        PowerManager powerManager=(PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        wakeLock=powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"TESTING:WAKE LOCK");
        wakeLock.acquire();
        Log.e("wake lock accuired","done");
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("app","In silent service");
        Data data=getInputData();
        eventId=data.getString(EVENT_ID);
        preferences= getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        sfEditor=preferences.edit();
        sfEditor.putBoolean("isTimeEventActive",true);
        sfEditor.putString("activatedTimeEvent",eventId);
        sfEditor.commit();
        AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        int mode=audioManager.getRingerMode();
        if(mode==AudioManager.RINGER_MODE_NORMAL || mode==AudioManager.RINGER_MODE_VIBRATE){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
        wakeLock.release();
        return Result.success();
    }
}
