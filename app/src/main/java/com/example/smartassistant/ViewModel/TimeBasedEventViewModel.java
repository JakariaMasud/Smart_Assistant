package com.example.smartassistant.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.Repository.TimeBasedEventRepository;
import java.util.List;

import javax.inject.Inject;

public class TimeBasedEventViewModel extends ViewModel {
    LiveData<List<TimeBasedEvent>> allEvents;
    TimeBasedEventRepository timeBasedEventRepository;

    @Inject
    public TimeBasedEventViewModel(@NonNull TimeBasedEventRepository repository) {
        timeBasedEventRepository =repository;
        allEvents=timeBasedEventRepository.getAllEvents();
    }

   public LiveData<List<TimeBasedEvent>> getAllEvents(){

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
        Log.e("in view model id: ",id);
        return timeBasedEventRepository.getEventById(id);
    }

}
