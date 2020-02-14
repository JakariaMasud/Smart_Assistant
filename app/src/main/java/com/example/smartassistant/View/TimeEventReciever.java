package com.example.smartassistant.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.media.AudioManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

public class TimeEventReciever extends BroadcastReceiver {
    private static final String TAG = "Reciever";
    @Override
    public void onReceive(Context context, Intent intent) {
        SilentIntentService.enqueuWork(context,new Intent(context,SilentIntentService.class));


}
}
