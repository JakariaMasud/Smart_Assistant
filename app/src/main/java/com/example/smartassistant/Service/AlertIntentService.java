package com.example.smartassistant.Service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import static com.example.smartassistant.View.AddTimeEventFragment.EVENT;

public class AlertIntentService extends JobIntentService {
    private static final String TAG = "AlertIntentService";
    private static String eventId;
    SharedPreferences preferences;
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.e(TAG,"TRIGGERD");
        preferences=getSharedPreferences("MyPref",MODE_PRIVATE);
        AudioManager audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int mode=audioManager.getRingerMode();
        if(mode==AudioManager.RINGER_MODE_NORMAL || mode==AudioManager.RINGER_MODE_VIBRATE){
            String notificationTitle=preferences.getString("notificationTitle","alert service");
            String notificationDescription=preferences.getString("notificationDescription","your phone will be silence mode in a few moment");
            NotificationHelper.displayNotification(this,notificationTitle,notificationDescription);
        }
    }
    public static void enqueueAlert(Context context, Intent intent, String id){
        eventId=id;
        enqueueWork(context,AlertIntentService.class,567,intent);
    }
}
