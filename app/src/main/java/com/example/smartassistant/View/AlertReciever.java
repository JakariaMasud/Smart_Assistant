package com.example.smartassistant.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlertReciever extends BroadcastReceiver {
    private static final String TAG = "Reciever";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"alert recieved");
         Intent alertIntent=new Intent(context,AlertIntentService.class);
         AlertIntentService.enqueueAlert(context,alertIntent);




    }
}
