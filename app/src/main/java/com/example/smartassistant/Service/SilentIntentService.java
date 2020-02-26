package com.example.smartassistant.Service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;


public class SilentIntentService extends JobIntentService {
    private static long eventId;
    SharedPreferences preferences;
    SharedPreferences.Editor sfEditor;


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.e("app","In silent service");
        preferences=getSharedPreferences("MyPref",MODE_PRIVATE);
        sfEditor=preferences.edit();
        sfEditor.putBoolean("isTimeEventActive",true);
        sfEditor.putLong("activatedTimeEvent",eventId);
        sfEditor.commit();
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int mode=audioManager.getRingerMode();
        if(mode==AudioManager.RINGER_MODE_NORMAL || mode==AudioManager.RINGER_MODE_VIBRATE){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }




    }
    public static  void enqueueWork(Context context,Intent intent,long id){
        eventId=id;
        enqueueWork(context,SilentIntentService.class,1234,intent);
    }
}
