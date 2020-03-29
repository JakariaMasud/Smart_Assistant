package com.example.smartassistant.Worker;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.PowerManager;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.google.android.gms.location.Geofence;
import static android.content.Context.MODE_PRIVATE;
import static com.example.smartassistant.View.App.EVENT_ID;

public class GeofenceTransitionWorker extends Worker {
    PowerManager.WakeLock wakeLock;
    public String eventId;
    int geofenceTransition;
    SharedPreferences preferences;
    SharedPreferences.Editor sfEditor;
    public GeofenceTransitionWorker(@NonNull final Context context, @NonNull final WorkerParameters workerParams) {
        super(context, workerParams);
        PowerManager powerManager=(PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        wakeLock=powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"TESTING:WAKE LOCK");
        wakeLock.acquire();
        Log.e("wake lock accuired","done");
    }

    @NonNull
    @Override
    public Result doWork() {
        preferences= getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        sfEditor=preferences.edit();
        Data data=getInputData();
        eventId=data.getString(EVENT_ID);
        geofenceTransition =data.getInt("transition_type",-1);
        AudioManager audioManager = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        int mode=audioManager.getRingerMode();
        int silent=AudioManager.RINGER_MODE_SILENT;
        int vibrate=AudioManager.RINGER_MODE_VIBRATE;
        int normal=AudioManager.RINGER_MODE_NORMAL;

        // Get the transition type.

        if(geofenceTransition== Geofence.GEOFENCE_TRANSITION_ENTER){
            sfEditor.putBoolean("isLocationEventActive",true);
            sfEditor.putString("activatedLocationEvent",eventId);
            sfEditor.commit();

            if(mode==normal || mode==vibrate)
            {
                NotificationHelper.displayNotification(getApplicationContext(),"Geo fence Alert","you have entred your Location Event area");
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                Log.e("work","successfully silenced done");
            }


        }
        else if(geofenceTransition==Geofence.GEOFENCE_TRANSITION_EXIT) {
            sfEditor=preferences.edit();
            sfEditor.putBoolean("isLocationEventActive",false);
            sfEditor.putString("activatedLocationEvent",null);
            sfEditor.commit();
            if(mode!=normal){
                NotificationHelper.displayNotification(getApplicationContext(),"Geo fence Alert","you have exited from your Location Event area");
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                Log.e("work","successfully normal mode done");
            }



        }
        wakeLock.release();
        return Result.success();
    }
}
