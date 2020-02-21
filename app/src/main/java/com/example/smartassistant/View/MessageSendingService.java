package com.example.smartassistant.View;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class MessageSendingService extends JobIntentService {
   public static String phoneNumber;

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Toast.makeText(this,"phone number: "+phoneNumber,Toast.LENGTH_LONG).show();

    }

    public static void enqueueMessage(Context context,Intent intent,String phone){
        enqueueWork(context,MessageSendingService.class,786,intent);
        phoneNumber=phone;
    }
}
