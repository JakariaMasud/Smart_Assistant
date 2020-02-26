package com.example.smartassistant.BroadCastReciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.smartassistant.Service.NormalIntentService;

import static com.example.smartassistant.View.AddTimeEventFragment.EVENT;

public class TimeOverReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long eventId=intent.getLongExtra(EVENT,0);
        NormalIntentService.enqueueJob(context,new Intent(context,NormalIntentService.class),eventId);
    }
}
