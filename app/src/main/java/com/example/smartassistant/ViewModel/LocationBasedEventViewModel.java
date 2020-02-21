package com.example.smartassistant.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.smartassistant.Model.LocationBasedEvent;
import com.example.smartassistant.Repository.LocationBasedEventRepository;

import java.util.List;

public class LocationBasedEventViewModel extends AndroidViewModel {
    LiveData<List<LocationBasedEvent>> allEvents;
    LocationBasedEventRepository LocationBasedEventRepository;
    public LocationBasedEventViewModel(@NonNull Application application) {
        super(application);
        LocationBasedEventRepository =new LocationBasedEventRepository(application);
        allEvents=LocationBasedEventRepository.getAllEvents();
    }

    public LiveData<List<LocationBasedEvent>> getAllEvents(){
        return allEvents;
    }

    public long insert(LocationBasedEvent event){
        long row_id=LocationBasedEventRepository.insert(event);
        return row_id;
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
    public void deleteById(long id){
        LocationBasedEventRepository.deleteById(id);
    }

    public LocationBasedEvent getById(long id){
        return LocationBasedEventRepository.getEventById(id);
    }
}
