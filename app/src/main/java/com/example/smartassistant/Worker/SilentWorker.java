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

import javax.inject.Inject;
import javax.inject.Provider;

import static android.content.Context.MODE_PRIVATE;
import static com.example.smartassistant.View.App.EVENT_ID;

public class SilentWorker extends Worker {
    private static String eventId;
    PowerManager.WakeLock wakeLock;
    SharedPreferences preferences;
    SharedPreferences.Editor sfEditor;
    AudioManager audioManager;
    PowerManager powerManager;

    public SilentWorker(@NonNull Context context, @NonNull WorkerParameters workerParams,SharedPreferences preferences,AudioManager audioManager,PowerManager powerManager) {
        super(context, workerParams);
        this.preferences=preferences;
        this.audioManager=audioManager;
        this.powerManager=powerManager;
        wakeLock=powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"TESTING:WAKE LOCK");
        wakeLock.acquire();
        Log.e("wake lock accuired","done");
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("app","In silent service");
        sfEditor=preferences.edit();
        Data data=getInputData();
        eventId=data.getString(EVENT_ID);
        sfEditor.putBoolean("isTimeEventActive",true);
        sfEditor.putString("activatedTimeEvent",eventId);
        sfEditor.commit();
        int mode=audioManager.getRingerMode();
        if(mode==AudioManager.RINGER_MODE_NORMAL || mode==AudioManager.RINGER_MODE_VIBRATE){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
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
            return new SilentWorker(context,parameters,preferences.get(),audioManager.get(),powerManager.get());
        }
    }
}
