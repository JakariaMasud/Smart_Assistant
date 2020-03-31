package com.example.smartassistant.DI;

import com.example.smartassistant.Worker.AlertWorker;
import com.example.smartassistant.Worker.GeofenceTransitionWorker;
import com.example.smartassistant.Worker.MessageSendingWorker;
import com.example.smartassistant.Worker.NormalWorker;
import com.example.smartassistant.Worker.SilentWorker;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(AlertWorker.class)
    abstract ChildWorkerFactory bindAlertWorker(AlertWorker.Factory factory);

    @Binds
    @IntoMap
    @WorkerKey(SilentWorker.class)
    abstract ChildWorkerFactory bindSilentWorker(SilentWorker.Factory factory);

    @Binds
    @IntoMap
    @WorkerKey(NormalWorker.class)
    abstract ChildWorkerFactory bindNormalWorker(NormalWorker.Factory factory);

    @Binds
    @IntoMap
    @WorkerKey(MessageSendingWorker.class)
    abstract ChildWorkerFactory bindMessageSendingWorker(MessageSendingWorker.Factory factory);

    @Binds
    @IntoMap
    @WorkerKey(GeofenceTransitionWorker.class)
    abstract ChildWorkerFactory bindGeofenceTransitionWorker(GeofenceTransitionWorker.Factory factory);



}
