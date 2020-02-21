package com.example.smartassistant.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.smartassistant.AppDataBase;
import com.example.smartassistant.Dao.LocationBasedEventDao;
import com.example.smartassistant.Model.LocationBasedEvent;

import java.util.List;

public class LocationBasedEventRepository {
    private LiveData<List<LocationBasedEvent>> allEvents;
    private LocationBasedEventDao locationBasedEventDao;

    public LocationBasedEventRepository(Application application) {
        AppDataBase dataBase=AppDataBase.getInstance(application);
        locationBasedEventDao=dataBase.locationBasedEventDao();
        allEvents=locationBasedEventDao.getAllEvents();
    }
    public long insert(LocationBasedEvent event){
       long row_id= locationBasedEventDao.addEvent(event);
       return row_id;

    }

    public void delete(LocationBasedEvent event){
        locationBasedEventDao.deleteEvent(event);
    }

    public void deleteById(long id){
        locationBasedEventDao.deleteById(id);
    }
    public void deleteAllEvents(){
        locationBasedEventDao.deleteAllEvents();
    }
    public LiveData<List<LocationBasedEvent>> getAllEvents(){
        return allEvents;
    }
    public void updateEvent(LocationBasedEvent event){
        locationBasedEventDao.updateEvent(event);
    }
    public LocationBasedEvent getEventById(long id){
        return locationBasedEventDao.getEventById(id);
    }
}
