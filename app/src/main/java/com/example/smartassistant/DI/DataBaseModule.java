package com.example.smartassistant.DI;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.example.smartassistant.AppDataBase;
import com.example.smartassistant.Dao.LocationBasedEventDao;
import com.example.smartassistant.Dao.TimeBasedEventDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

@Module
public class DataBaseModule {

    @Provides
    @Singleton
    AppDataBase provideAppDataBase(Application application) {
        return Room.databaseBuilder(application.getApplicationContext(), AppDataBase.class, "event_database")
                .fallbackToDestructiveMigration()
                .build();

    }

    @Provides
    @Singleton
    LocationBasedEventDao provideLocationBasedEventDao(AppDataBase appDataBase) {
        return appDataBase.locationBasedEventDao();
    }

    @Provides
    @Singleton
    TimeBasedEventDao provideTimeBasedEventDao(AppDataBase appDataBase) {
        return appDataBase.timeBasedEventDao();

    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application application) {
        return application.getSharedPreferences("MyPref", MODE_PRIVATE);
    }

    @Provides
    @Singleton
    SharedPreferences.Editor provideEditor(SharedPreferences sharedPreferences) {
        return sharedPreferences.edit();
    }
}
