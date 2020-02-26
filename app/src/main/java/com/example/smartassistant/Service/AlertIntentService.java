package com.example.smartassistant.Service;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import static com.example.smartassistant.View.AddTimeEventFragment.EVENT;

public class AlertIntentService extends JobIntentService {
    private static long eventId;
    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        AudioManager audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int mode=audioManager.getRingerMode();
        if(mode==AudioManager.RINGER_MODE_NORMAL || mode==AudioManager.RINGER_MODE_VIBRATE){
            NotificationHelper.displayNotification(this,"Event Alert","your phone will be silence mode in a few moment");
        }
    }
    public static void enqueueAlert(Context context, Intent intent,long id){
        eventId=id;
        enqueueWork(context,AlertIntentService.class,567,intent);
    }
}
