package com.example.smartassistant.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.smartassistant.Model.LocationBasedEvent;
import com.example.smartassistant.Repository.LocationBasedEventRepository;

import java.util.List;

import javax.inject.Inject;

public class LocationBasedEventViewModel extends ViewModel {
    LiveData<List<LocationBasedEvent>> allEvents;
    LocationBasedEventRepository LocationBasedEventRepository;

    @Inject
    public LocationBasedEventViewModel(LocationBasedEventRepository repository) {
        LocationBasedEventRepository=repository;
        allEvents=LocationBasedEventRepository.getAllEvents();

    }

    public LiveData<List<LocationBasedEvent>> getAllEvents(){

        return allEvents;
    }

    public void insert(LocationBasedEvent event){
        LocationBasedEventRepository.insert(event);
    }

    public void update(LocationBasedEvent event){
        LocationBasedEventRepository.updateEvent(event);
    }
    public void delete(LocationBasedEvent event){
        LocationBasedEventRepository.delete(event);
    }
    public void deleteAllEvents(){
        LocationBasedEventRepository.deleteAllEvents();
    }
    public void deleteById(String id){
        LocationBasedEventRepository.deleteById(id);
    }

    public LocationBasedEvent getById(String id){
        return LocationBasedEventRepository.getEventById(id);
    }
}
