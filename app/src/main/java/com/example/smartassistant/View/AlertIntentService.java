package com.example.smartassistant.View;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class AlertIntentService extends JobIntentService {
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        NotificationHelper.createNotificationChannels(this);
    }
    public static void enqueueAlert(Context context, Intent intent){
        enqueueWork(context,AlertIntentService.class,567,intent);
    }
}
