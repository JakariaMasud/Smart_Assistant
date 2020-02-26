package com.example.smartassistant.Service;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceTransitionsJobIntentService extends JobIntentService {

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, GeofenceTransitionsJobIntentService.class, 896, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {

            Log.e("geo fencing", "error event");
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if(geofenceTransition== Geofence.GEOFENCE_TRANSITION_ENTER){
            AudioManager audiomanage = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            Log.e("work","successfully silenced done");

        }
        else if(geofenceTransition==Geofence.GEOFENCE_TRANSITION_EXIT) {
            AudioManager audiomanage = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);
            audiomanage.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            Log.e("work","successfully normal mode done");


        }

    }
}
