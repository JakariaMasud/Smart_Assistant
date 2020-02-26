package com.example.smartassistant.BroadCastReciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.media.AudioManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.smartassistant.Service.SilentIntentService;

import static com.example.smartassistant.View.AddTimeEventFragment.EVENT;

public class TimeEventReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long eventId=intent.getLongExtra(EVENT,0);
       Intent silentIntent=new Intent(context,SilentIntentService.class);
       SilentIntentService.enqueueWork(context,silentIntent,eventId);



}
}
