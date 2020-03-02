package com.example.smartassistant.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.smartassistant.Model.LocationBasedEvent;

import java.util.List;

@Dao
public interface LocationBasedEventDao {

    @Query("SELECT * FROM location_based_table")
    public LiveData<List<LocationBasedEvent>>getAllEvents();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addEvent(LocationBasedEvent event);

    @Delete
    public void deleteEvent(LocationBasedEvent event);

    @Update
    public void updateEvent(LocationBasedEvent event);

    @Query("SELECT * FROM location_based_table WHERE id =:id")
    public LocationBasedEvent getEventById(String id);

    @Query("DELETE  FROM location_based_table WHERE id=:id")
    public void deleteById(String id);

    @Query("DELETE  FROM location_based_table")
    public void deleteAllEvents();

}
