package com.example.smartassistant.DI;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    Application mApplication;

    public AppModule(final Application mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    Application provideApplicationContext(){
        return mApplication;
    }

}
