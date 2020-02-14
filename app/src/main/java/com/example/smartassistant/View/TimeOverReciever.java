package com.example.smartassistant.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TimeOverReciever extends BroadcastReceiver {
    private static final String TAG = "Reciever";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: time over");
        NormalIntentService.enqueueJob(context,new Intent(context,NormalIntentService.class));
    }
}
