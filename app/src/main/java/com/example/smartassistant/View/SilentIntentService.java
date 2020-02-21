package com.example.smartassistant.View;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;


public class SilentIntentService extends JobIntentService {


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        AudioManager audiomanage = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
        audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        Log.e("work","in enque ");
        Log.e("work","successfully silenced done");

    }
    static  void enqueuWork(Context context,Intent intent){
        enqueueWork(context,SilentIntentService.class,1234,intent);
    }
}
