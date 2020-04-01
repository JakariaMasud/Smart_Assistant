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
import com.example.smartassistant.DI.ChildWorkerFactory;
import com.example.smartassistant.Dao.TimeBasedEventDao;
import com.example.smartassistant.Model.TimeBasedEvent;
import javax.inject.Inject;
import javax.inject.Provider;
import static android.content.Context.MODE_PRIVATE;

public class MessageSendingWorker extends Worker {
    public  String phoneNumber;
    SharedPreferences preferences;
    PowerManager powerManager;
    AudioManager audioManager;
    PowerManager.WakeLock wakeLock;
    String timeEventId,locationEventId;
    boolean isTimeEventActive;
    boolean isLocationEventActive;
    TimeBasedEventDao timeBasedEventDao;
    TimeBasedEvent timeBasedEvent;
    public MessageSendingWorker(@NonNull Context context, @NonNull WorkerParameters workerParams,AudioManager audioManager,SharedPreferences preferences,PowerManager powerManager,TimeBasedEventDao timeBasedEventDao) {
        super(context, workerParams);
        this.preferences=preferences;
        this.powerManager=powerManager;
        this.audioManager=audioManager;
        this.timeBasedEventDao=timeBasedEventDao;
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
    public static class Factory implements ChildWorkerFactory {
        Provider<SharedPreferences> preferences;
        Provider<AudioManager>audioManager;
        Provider <PowerManager> powerManager;
        Provider <TimeBasedEventDao> timeBasedEventDao;

        @Inject
        public Factory(final Provider<SharedPreferences> preferences, final Provider<AudioManager> audioManager, final Provider<PowerManager> powerManager, final Provider<TimeBasedEventDao> timeBasedEventDao) {
            this.preferences = preferences;
            this.audioManager = audioManager;
            this.powerManager = powerManager;
            this.timeBasedEventDao = timeBasedEventDao;
        }




        @Override
        public Worker create(Context context, WorkerParameters parameters) {
            return new MessageSendingWorker(context,parameters,audioManager.get(),preferences.get(),powerManager.get(),timeBasedEventDao.get());
        }
    }

}
