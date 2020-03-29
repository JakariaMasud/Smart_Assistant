package com.example.smartassistant.DI;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.PowerManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.smartassistant.Worker.AlertWorker;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

public class WorkerFactory extends androidx.work.WorkerFactory {

    SharedPreferences preferences;
    AudioManager audioManager;
    PowerManager powerManager;

    @Nullable
    @Override
    public ListenableWorker createWorker(@NonNull Context appContext, @NonNull String workerClassName, @NonNull WorkerParameters workerParameters) {
        try {
            Class worker=Class.forName(workerClassName).asSubclass(Worker.class);
            Constructor constructor = worker.getDeclaredConstructor(Context.class,WorkerParameters.class);
            Object instance = constructor.newInstance(appContext, workerParameters);
            if(instance== AlertWorker.class){


            }



        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
