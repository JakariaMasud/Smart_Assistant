package com.example.smartassistant.BroadCastReciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.smartassistant.Service.GeofenceTransitionsJobIntentService;

public class LocationBasedEventReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long eventId=intent.getLongExtra("locationEventId",-1);
        GeofenceTransitionsJobIntentService.enqueueWork(context, intent,eventId);


    }
}
