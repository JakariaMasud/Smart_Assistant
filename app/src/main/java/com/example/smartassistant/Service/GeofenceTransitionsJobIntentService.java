package com.example.smartassistant.Service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceTransitionsJobIntentService extends JobIntentService {
    public static long eventId;

    SharedPreferences preferences;
    SharedPreferences.Editor sfEditor;

    public static void enqueueWork(Context context, Intent intent,long id) {
        eventId=id;
        enqueueWork(context, GeofenceTransitionsJobIntentService.class, 896, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent ){
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {

            Log.e("geo fencing", "error event");
            return;
        }
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int mode=audioManager.getRingerMode();
        int silent=AudioManager.RINGER_MODE_SILENT;
        int vibrate=AudioManager.RINGER_MODE_VIBRATE;
        int normal=AudioManager.RINGER_MODE_NORMAL;

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();
        if(geofenceTransition== Geofence.GEOFENCE_TRANSITION_ENTER){
            preferences=getSharedPreferences("MyPref",MODE_PRIVATE);
            sfEditor=preferences.edit();
            sfEditor.putBoolean("isLocationEventActive",true);
            sfEditor.putLong("activatedLocationEvent",eventId);
            sfEditor.commit();
            if(mode==normal || mode==vibrate)
            {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Log.e("work","successfully silenced done");
            }


        }
        else if(geofenceTransition==Geofence.GEOFENCE_TRANSITION_EXIT) {
            preferences=getSharedPreferences("MyPref",MODE_PRIVATE);
            sfEditor=preferences.edit();
            sfEditor.putBoolean("isLocationEventActive",false);
            sfEditor.putLong("activatedLocationEvent",-1);
            sfEditor.commit();
           ;
            if(mode==silent || mode==vibrate){
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Log.e("work","successfully normal mode done");
            }



        }

    }
}
