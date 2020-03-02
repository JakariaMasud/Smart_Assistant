package com.example.smartassistant.Service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.example.smartassistant.AppDataBase;
import com.example.smartassistant.Dao.TimeBasedEventDao;
import com.example.smartassistant.Model.TimeBasedEvent;

import static com.example.smartassistant.View.AddTimeEventFragment.EVENT;

public class NormalIntentService extends JobIntentService {
    SharedPreferences preferences;
    SharedPreferences.Editor sfEditor;
    TimeBasedEventDao timeBasedEventDao;
    AppDataBase dataBase;
    public static String timeEventId;
    TimeBasedEvent event;



    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.e("app","In normal service");
        preferences=getSharedPreferences("MyPref",MODE_PRIVATE);
        sfEditor=preferences.edit();
        dataBase=AppDataBase.getInstance(getApplicationContext());
        timeBasedEventDao=dataBase.timeBasedEventDao();
        sfEditor.putBoolean("isTimeEventActive",false);
        sfEditor.putString("activatedTimeEvent",null);
        sfEditor.commit();
        event=timeBasedEventDao.getEventById(timeEventId);
        if(event.getType().equals("Once")){
            timeBasedEventDao.deleteById(event.getId());
        }

        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        if(audioManager.getRingerMode()!=AudioManager.RINGER_MODE_NORMAL){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }

    }

    public static void enqueueJob(Context context, Intent job, String eventId){
        timeEventId=eventId;
        enqueueWork(context,NormalIntentService.class,789,job);
    }
}
