package com.example.smartassistant.Worker;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.PowerManager;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.smartassistant.AppDataBase;
import com.example.smartassistant.Dao.TimeBasedEventDao;
import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.View.App;

import static android.content.Context.MODE_PRIVATE;

public class MessageSendingWorker extends Worker {
    public  String phoneNumber;
    SharedPreferences preferences;
    PowerManager.WakeLock wakeLock;
    String timeEventId,locationEventId;
    boolean isTimeEventActive;
    boolean isLocationEventActive;
    TimeBasedEventDao timeBasedEventDao;
    TimeBasedEvent timeBasedEvent;
    public MessageSendingWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        PowerManager powerManager=(PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        wakeLock=powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"TESTING:WAKE LOCK");
        wakeLock.acquire();
        Log.e("wake lock accuired","done");
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data=getInputData();
        phoneNumber=data.getString("phone");
        Log.e("phone",phoneNumber);
        preferences= getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        AudioManager audioManager=(AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        int mode=audioManager.getRingerMode();
        int silent=AudioManager.RINGER_MODE_SILENT;
        int vibrate=AudioManager.RINGER_MODE_VIBRATE;
        int normal=AudioManager.RINGER_MODE_NORMAL;
        if(mode!=normal){

            if(preferences.contains("isTimeEventActive")){
                isTimeEventActive=preferences.getBoolean("isTimeEventActive",false);
                if(isTimeEventActive){
                    timeEventId=preferences.getString("activatedTimeEvent",null);
                    if(timeEventId!=null){
                        timeBasedEvent= timeBasedEventDao.getEventById(timeEventId);
                        long currentTimeMillis= System.currentTimeMillis();
                        long dif=currentTimeMillis-timeBasedEvent.getSelectedTime();
                        if(dif<=timeBasedEvent.getPeriod()*60*1000){
                            int selectedSim=preferences.getInt("selectedSim",0);
                            String msgText=preferences.getString("msgText","I will call you back later");
                            sendMessage(selectedSim,phoneNumber,msgText);
                        }

                    }


                }

            }
            else if(preferences.contains("isLocationEventActive")){
                isLocationEventActive=preferences.getBoolean("isLocationEventActive",false);
                if(isLocationEventActive){
                    locationEventId=preferences.getString("activatedLocationEvent",null);
                    if (locationEventId != null) {
                        int selectedSim=preferences.getInt("selectedSim",0);
                        String msgText=preferences.getString("msgText","I will call you back later");
                        sendMessage(selectedSim,phoneNumber,msgText);

                    }

                }
            }

        }
        wakeLock.release();
        return Result.success();
    }
    public void sendMessage(int simNo,String PhoneNumber,String message) {
        Log.e("send","sending msg");
        Log.e("send",phoneNumber);

        SmsManager.getSmsManagerForSubscriptionId(simNo)
                .sendTextMessage(PhoneNumber.trim(),null,message,null,null);

    }
}
