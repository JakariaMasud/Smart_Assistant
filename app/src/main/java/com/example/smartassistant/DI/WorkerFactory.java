package com.example.smartassistant.DI;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;

import java.util.Map;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Provider;

public class WorkerFactory extends androidx.work.WorkerFactory {
    private final Map<Class<? extends ListenableWorker>, Provider<ChildWorkerFactory>> creators;

    @Inject
    public WorkerFactory(final Map<Class<? extends ListenableWorker>, Provider<ChildWorkerFactory>> creators) {
        this.creators = creators;
    }

    @Nullable
    @Override
    public ListenableWorker createWorker(@NonNull Context appContext, @NonNull String workerClassName, @NonNull WorkerParameters workerParameters) {
        Provider<ChildWorkerFactory> factoryProvider;
        for (Map.Entry<Class<? extends ListenableWorker>, Provider<ChildWorkerFactory>> entry : creators.entrySet()) {
            if (Objects.equals(workerClassName, entry.getKey().getName())) {
               factoryProvider= entry.getValue();
               return factoryProvider.get().create(appContext,workerParameters);

            }

        }

return null;

    }
}
