package com.example.smartassistant.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.smartassistant.AppDataBase;
import com.example.smartassistant.AsyncTask.DeleteAllTimeEventTask;
import com.example.smartassistant.AsyncTask.GetTimeEventByIdTask;
import com.example.smartassistant.Dao.TimeBasedEventDao;
import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.AsyncTask.TimeEventDeleteTask;
import com.example.smartassistant.AsyncTask.TimeEventInsertTask;
import com.example.smartassistant.AsyncTask.TimeEventUpdateTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TimeBasedEventRepository {
    private LiveData<List<TimeBasedEvent>> allEvents;
    private TimeBasedEventDao timeBasedEventDao;
    private  TimeBasedEvent event;

    public TimeBasedEventRepository(Application application) {
        AppDataBase dataBase=AppDataBase.getInstance(application);
        timeBasedEventDao=dataBase.timeBasedEventDao();
        allEvents=timeBasedEventDao.getAllEvents();

    }
    public void insert(TimeBasedEvent event){
        TimeEventInsertTask insertTask=new TimeEventInsertTask(timeBasedEventDao);
        insertTask.execute(event);

    }

    public void delete(TimeBasedEvent event){
    }

    public void deleteById(String id){

        TimeEventDeleteTask timeEventDeleteTask=new TimeEventDeleteTask(timeBasedEventDao);
        timeEventDeleteTask.execute(id);
    }
    public void deleteAllEvents(){
        timeBasedEventDao.deleteAllEvents();
    }
    public LiveData<List<TimeBasedEvent>> getAllEvents(){

        return allEvents;
    }
    public void updateEvent(TimeBasedEvent event){

        TimeEventUpdateTask timeEventUpdateTask=new TimeEventUpdateTask(timeBasedEventDao);
        timeEventUpdateTask.execute();
    }
    public TimeBasedEvent getEventById(String id){
        GetTimeEventByIdTask getTimeEventByIdTask =new GetTimeEventByIdTask(timeBasedEventDao);

        try {
             event= getTimeEventByIdTask.execute(id).get();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        return event ;
    }
    public void deleteAllEvent(){
        DeleteAllTimeEventTask deleteAllTimeEventTask=new DeleteAllTimeEventTask(timeBasedEventDao);
        deleteAllTimeEventTask.execute();
    }
}
