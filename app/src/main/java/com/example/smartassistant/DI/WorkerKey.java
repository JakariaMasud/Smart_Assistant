package com.example.smartassistant.DI;

import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.Worker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import dagger.MapKey;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface WorkerKey {
    Class<? extends ListenableWorker> value();

}