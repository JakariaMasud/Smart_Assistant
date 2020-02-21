package com.example.smartassistant.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.smartassistant.Model.TimeBasedEvent;

import java.util.List;

@Dao
public interface TimeBasedEventDao {
    @Query("SELECT * FROM time_based_table")
    public LiveData<List<TimeBasedEvent>> getAllEvents();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long addEvent(TimeBasedEvent event);

    @Delete
    public void deleteEvent(TimeBasedEvent event);

    @Update
    public void updateEvent(TimeBasedEvent event);

    @Query("SELECT * FROM time_based_table WHERE id =:id")
    public TimeBasedEvent getEventById(long id);

    @Query("DELETE  FROM time_based_table WHERE id=:id")
    public void deleteById(long id);

    @Query("DELETE  FROM time_based_table")
    public void deleteAllEvents();

}
