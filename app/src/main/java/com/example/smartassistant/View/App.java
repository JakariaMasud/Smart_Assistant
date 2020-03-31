package com.example.smartassistant.View;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.work.Configuration;
import androidx.work.WorkManager;

import com.example.smartassistant.DI.AppModule;
import com.example.smartassistant.DI.ApplicationComponent;
import com.example.smartassistant.DI.DaggerApplicationComponent;
import com.example.smartassistant.DI.DataBaseModule;
import com.example.smartassistant.DI.ServiceModule;
import com.example.smartassistant.DI.WorkerFactory;

public class App extends Application  {
    private ApplicationComponent applicationComponent;
    public static final String CHANNEL_ID = "AlertChannel";
    public static final String EVENT_ID="event_id";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels(getApplicationContext());
        applicationComponent= DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .dataBaseModule(new DataBaseModule())
                .serviceModule(new ServiceModule())
                .build();
        configureWorkManager();



    }
    public static void createNotificationChannels (Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Alert Channel", importance);
            channel.setDescription("Alert Channel");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public  ApplicationComponent getApplicationComponent() {

        return applicationComponent;
    }
    private void configureWorkManager() {
        WorkerFactory factory = applicationComponent.factory();
        Configuration config = new Configuration.Builder()
                .setWorkerFactory(factory)
                .build();

        WorkManager.initialize(this, config);
    }

}
