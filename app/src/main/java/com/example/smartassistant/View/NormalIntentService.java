package com.example.smartassistant.View;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class NormalIntentService extends JobIntentService {
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        AudioManager audiomanage = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        audiomanage.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        Log.e("work","restore to normal mode");
        Log.e("work","Job done");

    }

    public static void enqueueJob(Context context,Intent job){

        enqueueWork(context,NormalIntentService.class,789,job);
    }
}
