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
import com.example.smartassistant.AppDataBase;
import com.example.smartassistant.Dao.TimeBasedEventDao;
import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.View.App;
import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;
import static com.example.smartassistant.View.App.EVENT_ID;

public class NormalWorker extends Worker {
    TimeBasedEvent event;
    PowerManager.WakeLock wakeLock;
    SharedPreferences preferences;
    SharedPreferences.Editor sfEditor;
   TimeBasedEventDao timeBasedEventDao;
    PowerManager powerManager;
    public static String timeEventId;


    public NormalWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        powerManager=(PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        wakeLock=powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"TESTING:WAKE LOCK");
        wakeLock.acquire();
        Log.e("wake lock accuired","done");

    }

    @NonNull
    @Override
    public Result doWork() {
        preferences=getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        sfEditor=preferences.edit();
        Data data=getInputData();
        timeEventId=data.getString(EVENT_ID);
        Log.e("app", "In normal service");
        sfEditor.putBoolean("isTimeEventActive", false);
        sfEditor.putString("activatedTimeEvent", null);
        sfEditor.commit();
        event = timeBasedEventDao.getEventById(timeEventId);
        if (event.getType().equals("Once")) {
            timeBasedEventDao.deleteById(event.getId());
            Log.e("delete","event deleted");
        }
        AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
        wakeLock.release();
        return Result.success();
    }


}



