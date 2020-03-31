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

import com.example.smartassistant.DI.ChildWorkerFactory;
import com.google.android.gms.location.Geofence;

import javax.inject.Inject;
import javax.inject.Provider;

import static android.content.Context.MODE_PRIVATE;
import static com.example.smartassistant.View.App.EVENT_ID;

public class GeofenceTransitionWorker extends Worker {
    PowerManager.WakeLock wakeLock;
    public String eventId;
    int geofenceTransition;
    SharedPreferences preferences;
    SharedPreferences.Editor sfEditor;
    AudioManager audioManager;
    PowerManager powerManager;
    public GeofenceTransitionWorker(@NonNull final Context context, @NonNull final WorkerParameters workerParams,SharedPreferences preferences,AudioManager audioManager,PowerManager powerManager) {
        super(context, workerParams);
        this.audioManager=audioManager;
        this.powerManager=powerManager;
        this.preferences=preferences;
        wakeLock=powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"TESTING:WAKE LOCK");
        wakeLock.acquire();
        Log.e("wake lock accuired","done");
    }

    @NonNull
    @Override
    public Result doWork() {
        sfEditor=preferences.edit();
        Data data=getInputData();
        eventId=data.getString(EVENT_ID);
        geofenceTransition =data.getInt("transition_type",-1);
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
         if(geofenceTransition==Geofence.GEOFENCE_TRANSITION_EXIT) {
            sfEditor=preferences.edit();
            sfEditor.putBoolean("isLocationEventActive",false);
            sfEditor.putString("activatedLocationEvent",null);
            sfEditor.commit();
            if(mode==silent ||mode==vibrate){
                NotificationHelper.displayNotification(getApplicationContext(),"Geo fence Alert","you have exited from your Location Event area");
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                Log.e("work","successfully normal mode done");
            }



        }
        wakeLock.release();
        return Result.success();
    }
    public static class Factory implements ChildWorkerFactory {
        Provider<SharedPreferences> preferences;
        Provider<AudioManager>audioManager;
        Provider <PowerManager> powerManager;

        @Inject
        public Factory(final Provider<SharedPreferences> preferences, final Provider<AudioManager> audioManager, final Provider<PowerManager> powerManager) {
            this.preferences = preferences;
            this.audioManager = audioManager;
            this.powerManager = powerManager;
        }

        @Override
        public Worker create(Context context, WorkerParameters parameters) {
            return new GeofenceTransitionWorker(context,parameters,preferences.get(),audioManager.get(),powerManager.get());
        }
    }
}
