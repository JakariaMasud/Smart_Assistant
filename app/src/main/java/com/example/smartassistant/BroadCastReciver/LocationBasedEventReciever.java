package com.example.smartassistant.BroadCastReciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.smartassistant.Service.GeofenceTransitionsJobIntentService;
import com.example.smartassistant.Worker.GeofenceTransitionWorker;
import com.example.smartassistant.Worker.SilentWorker;
import com.google.android.gms.location.GeofencingEvent;

import static com.example.smartassistant.View.App.EVENT_ID;

public class LocationBasedEventReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String eventId=intent.getStringExtra("locationEventId");
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {

            Log.e("geo fencing", "error event");
            return;
        }
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        Data data=new Data.Builder()
                .putInt("transition_type",geofenceTransition)
                .putString(EVENT_ID,eventId)
                .build();
        OneTimeWorkRequest oneTimeWorkRequest=
                new OneTimeWorkRequest.Builder(GeofenceTransitionWorker.class)
                        .setInputData(data).build();
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);


    }
}
