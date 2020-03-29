package com.example.smartassistant.DI;

import android.app.AlarmManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.os.PowerManager;

import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {

    @Provides
    AudioManager provideAudioManager(Application application){
        AudioManager audioManager=(AudioManager) application.getSystemService(Context.AUDIO_SERVICE);
        return audioManager;
    }

    @Provides
    PowerManager providePowerManager(Application application){
        PowerManager powerManager=(PowerManager) application.getSystemService(Context.POWER_SERVICE);
        return powerManager;
    }
    @Provides
    NotificationManager provideNotificationManager(Application application){
        NotificationManager notificationManager =
                (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);
        return notificationManager;
    }
    @Provides
    AlarmManager provideAlarmManager(Application application){
        AlarmManager alarmManager = (AlarmManager) application.getSystemService(Context.ALARM_SERVICE);
        return alarmManager;
    }
    @Provides
    GeofencingClient provideGeofencingClient(Application application){
        GeofencingClient geofencingClient=LocationServices.getGeofencingClient(application);
        return geofencingClient ;
    }


}
