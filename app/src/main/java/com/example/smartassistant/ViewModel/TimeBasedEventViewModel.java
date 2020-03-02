package com.example.smartassistant.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.Repository.TimeBasedEventRepository;
import java.util.List;

public class TimeBasedEventViewModel extends AndroidViewModel{
    LiveData<List<TimeBasedEvent>> allEvents;
    TimeBasedEventRepository timeBasedEventRepository;

    public TimeBasedEventViewModel(@NonNull Application application) {
        super(application);
        timeBasedEventRepository =new TimeBasedEventRepository(application);

    }

   public LiveData<List<TimeBasedEvent>> getAllEvents(){
       allEvents=timeBasedEventRepository.getAllEvents();
        return allEvents;
    }

   public void insert(TimeBasedEvent event){
        timeBasedEventRepository.insert(event);

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
    public void deleteById(String id){
        timeBasedEventRepository.deleteById(id);
    }
    public TimeBasedEvent getById(String id){
       return timeBasedEventRepository.getEventById(id);
    }

}
