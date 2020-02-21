package com.example.smartassistant.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.Repository.TimeBasedEventRepository;

import java.util.List;

public class TimeBasedEventViewModel extends AndroidViewModel{
    LiveData<List<TimeBasedEvent>> allEvents;
    TimeBasedEventRepository timeBasedEventRepository;

    public TimeBasedEventViewModel(@NonNull Application application) {
        super(application);
        timeBasedEventRepository =new TimeBasedEventRepository(application);
        allEvents=timeBasedEventRepository.getAllEvents();
    }


   public LiveData<List<TimeBasedEvent>> getAllEvents(){
        return allEvents;
    }

   public long insert(TimeBasedEvent event){
        long row_id=timeBasedEventRepository.insert(event);
        return row_id;
    }

    public void update(TimeBasedEvent event){
        timeBasedEventRepository.updateEvent(event);
    }
    public void delete(TimeBasedEvent event){
        timeBasedEventRepository.delete(event);
    }
    public void deleteAllEvents(){
        timeBasedEventRepository.deleteAllEvents();
    }
    public void deleteById(long id){
        timeBasedEventRepository.deleteById(id);
    }
    public TimeBasedEvent getById(long id){
       return timeBasedEventRepository.getEventById(id);
    }

}
