package com.example.smartassistant.Service;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.JobIntentService;

import com.example.smartassistant.AppDataBase;
import com.example.smartassistant.Dao.LocationBasedEventDao;
import com.example.smartassistant.Dao.TimeBasedEventDao;
import com.example.smartassistant.Model.LocationBasedEvent;
import com.example.smartassistant.Model.TimeBasedEvent;

import java.util.List;

public class MessageSendingService extends JobIntentService {
   public static String phoneNumber;
    SharedPreferences preferences;
    String timeEventId,locationEventId;
    boolean isTimeEventActive;
    boolean isLocationEventActive;
    TimeBasedEventDao timeBasedEventDao;
    LocationBasedEventDao locationBasedEventDao;
    LocationBasedEvent locationBasedEvent;
    TimeBasedEvent timeBasedEvent;
    List<Integer>idList;

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        timeBasedEventDao= AppDataBase.getInstance(getApplicationContext()).timeBasedEventDao();
        locationBasedEventDao=AppDataBase.getInstance(getApplicationContext()).locationBasedEventDao();
        preferences=getSharedPreferences("MyPref",MODE_PRIVATE);
        AudioManager audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int mode=audioManager.getRingerMode();
        int silent=AudioManager.RINGER_MODE_SILENT;
        int vibrate=AudioManager.RINGER_MODE_VIBRATE;
        int normal=AudioManager.RINGER_MODE_NORMAL;
        if(mode==silent || mode==vibrate){

            if(preferences.contains("isTimeEventActive")){
                isTimeEventActive=preferences.getBoolean("isTimeEventActive",false);
                if(isTimeEventActive){
                    timeEventId=preferences.getString("activatedTimeEvent",null);
                    if(timeEventId!=null){
                       timeBasedEvent= timeBasedEventDao.getEventById(timeEventId);
                        long currentTimeMillis= System.currentTimeMillis();
                        long dif=currentTimeMillis-timeBasedEvent.getSelectedTime();
                        if(dif<timeBasedEvent.getPeriod()*60*1000){
                            sendMessage(0,phoneNumber,"this is sample text message");
                        }

                    }


                }

            }
            else if(preferences.contains("isLocationEventActive")){
                isLocationEventActive=preferences.getBoolean("isLocationEventActive",false);
                if(isLocationEventActive){
                    locationEventId=preferences.getString("activatedLocationEvent",null);
                    if (locationEventId != null) {
                        sendMessage(0,phoneNumber,"you have entered the area");

                    }

                }
            }

        }



    }

    public static void enqueueMessage(Context context,Intent intent,String phone){
        phoneNumber=phone;
        enqueueWork(context,MessageSendingService.class,786,intent);

    }

    public void sendMessage(int simNo,String PhoneNumber,String message) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            SubscriptionManager subscriptionManager = SubscriptionManager.from(getApplicationContext());
            List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
            for(SubscriptionInfo info:subscriptionInfoList){
                idList.add(info.getSubscriptionId());

                Log.e("id","subsciption Id :"+info.getSubscriptionId());

            }
            SmsManager.getSmsManagerForSubscriptionId(idList.get(simNo))
                    .sendTextMessage("0167009714",null,"Hello",null,null);

        }



    }
}
