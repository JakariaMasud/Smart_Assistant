package com.example.smartassistant.Service;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import static com.example.smartassistant.View.AddTimeEventFragment.EVENT;

public class AlertIntentService extends JobIntentService {
    private static final String TAG = "AlertIntentService";
    private static String eventId;
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.e(TAG,"TRIGGERD");

        AudioManager audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int mode=audioManager.getRingerMode();
        if(mode==AudioManager.RINGER_MODE_NORMAL || mode==AudioManager.RINGER_MODE_VIBRATE){
            NotificationHelper.displayNotification(this,"Event Alert","your phone will be silence mode in a few moment");
        }
    }
    public static void enqueueAlert(Context context, Intent intent, String id){
        eventId=id;
        enqueueWork(context,AlertIntentService.class,567,intent);
    }
}
