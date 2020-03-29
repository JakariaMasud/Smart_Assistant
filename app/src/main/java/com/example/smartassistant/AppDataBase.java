package com.example.smartassistant;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.smartassistant.Dao.LocationBasedEventDao;
import com.example.smartassistant.Dao.TimeBasedEventDao;
import com.example.smartassistant.Model.LocationBasedEvent;
import com.example.smartassistant.Model.TimeBasedEvent;

@Database(version = 4,entities={LocationBasedEvent.class, TimeBasedEvent.class},exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract LocationBasedEventDao locationBasedEventDao();
    public abstract TimeBasedEventDao timeBasedEventDao();


}
