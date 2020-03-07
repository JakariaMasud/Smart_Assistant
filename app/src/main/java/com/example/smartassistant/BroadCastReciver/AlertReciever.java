package com.example.smartassistant.BroadCastReciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


import com.example.smartassistant.Worker.AlertWorker;

import static com.example.smartassistant.View.AddTimeEventFragment.EVENT;
import static com.example.smartassistant.View.App.EVENT_ID;


public class AlertReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String eventId=intent.getStringExtra(EVENT);
        Log.e("alert","triggerd");
        Log.e("time event",eventId.toString());
        Data data=new Data.Builder()
                .putString(EVENT_ID,eventId)
                .build();
        OneTimeWorkRequest oneTimeWorkRequest=
                new OneTimeWorkRequest.Builder(AlertWorker.class)
                        .setInputData(data).build();
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);


    }
}
