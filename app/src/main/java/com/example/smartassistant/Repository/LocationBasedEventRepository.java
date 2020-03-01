package com.example.smartassistant.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.smartassistant.AppDataBase;
import com.example.smartassistant.AsyncTask.DeleteAllLocationEventTask;
import com.example.smartassistant.AsyncTask.GetAllLocationEventTask;
import com.example.smartassistant.AsyncTask.GetLocationEventByIdTask;
import com.example.smartassistant.AsyncTask.LocationEventDeleteTask;
import com.example.smartassistant.AsyncTask.LocationEventInsertTask;
import com.example.smartassistant.AsyncTask.LocationEventUpdateTask;
import com.example.smartassistant.Dao.LocationBasedEventDao;
import com.example.smartassistant.Model.LocationBasedEvent;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class LocationBasedEventRepository {
    private LiveData<List<LocationBasedEvent>> allEvents;
    private LocationBasedEventDao locationBasedEventDao;
    private long row_id;
    private LocationBasedEvent event;

    public LocationBasedEventRepository(Application application) {
        AppDataBase dataBase=AppDataBase.getInstance(application);
        locationBasedEventDao=dataBase.locationBasedEventDao();

    }
    public void insert(LocationBasedEvent event){
        LocationEventInsertTask locationEventInsertTask=new LocationEventInsertTask(locationBasedEventDao);
        locationEventInsertTask.execute(event);

    }

    public void delete(LocationBasedEvent event){

    }

    public void deleteById(String id){
        LocationEventDeleteTask locationEventDeleteTask=new LocationEventDeleteTask(locationBasedEventDao);
        locationEventDeleteTask.execute(id);
    }
    public void deleteAllEvents(){
        locationBasedEventDao.deleteAllEvents();
    }

    public LiveData<List<LocationBasedEvent>> getAllEvents(){
        GetAllLocationEventTask allLocationEventTask=new GetAllLocationEventTask(locationBasedEventDao);
        try {
            allEvents= allLocationEventTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return allEvents;
    }
    public void updateEvent(LocationBasedEvent event){
        LocationEventUpdateTask locationEventUpdateTask=new LocationEventUpdateTask(locationBasedEventDao);
        locationEventUpdateTask.execute(event);

    }
    public LocationBasedEvent getEventById(String id){
        GetLocationEventByIdTask getLocationEventByIdTask =new GetLocationEventByIdTask(locationBasedEventDao);

        try {
            event= getLocationEventByIdTask.execute(id).get();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        return event ;
    }
    public void deleteAllEvent(){
        DeleteAllLocationEventTask deleteAllLocationEventTask=new DeleteAllLocationEventTask(locationBasedEventDao);
        deleteAllLocationEventTask.execute();
    }
}
