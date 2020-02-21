package com.example.smartassistant.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.smartassistant.AppDataBase;
import com.example.smartassistant.Dao.TimeBasedEventDao;
import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.ViewModel.TimeBasedEventViewModel;

import java.util.List;

public class TimeBasedEventRepository {
    private LiveData<List<TimeBasedEvent>> allEvents;
    private TimeBasedEventDao timeBasedEventDao;

    public TimeBasedEventRepository(Application application) {
        AppDataBase dataBase=AppDataBase.getInstance(application);
        timeBasedEventDao=dataBase.timeBasedEventDao();
        allEvents=timeBasedEventDao.getAllEvents();
    }
    public long insert(TimeBasedEvent event){
        long row_id=timeBasedEventDao.addEvent(event);
        return row_id;

    }

    public void delete(TimeBasedEvent event){
        timeBasedEventDao.deleteEvent(event);
    }

    public void deleteById(long id){
        timeBasedEventDao.deleteById(id);
    }
    public void deleteAllEvents(){
        timeBasedEventDao.deleteAllEvents();
    }
    public LiveData<List<TimeBasedEvent>> getAllEvents(){
        return allEvents;
    }
    public void updateEvent(TimeBasedEvent event){
        timeBasedEventDao.updateEvent(event);
    }
    public TimeBasedEvent getEventById(long id){
        return timeBasedEventDao.getEventById(id);
    }
}
