package com.example.smartassistant.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.smartassistant.AppDataBase;
import com.example.smartassistant.AsyncTask.DeleteAllTimeEventTask;
import com.example.smartassistant.AsyncTask.GetTimeEventByIdTask;
import com.example.smartassistant.Dao.TimeBasedEventDao;
import com.example.smartassistant.Model.TimeBasedEvent;
import com.example.smartassistant.AsyncTask.GetAllTimeEventTask;
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

    }
    public long insert(TimeBasedEvent event){
        TimeEventInsertTask insertTask=new TimeEventInsertTask(timeBasedEventDao);
        long row_id = 0;
        try {
            row_id=insertTask.execute(event).get();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return row_id ;

    }

    public void delete(TimeBasedEvent event){


    }

    public void deleteById(long id){

        TimeEventDeleteTask timeEventDeleteTask=new TimeEventDeleteTask(timeBasedEventDao);
        timeEventDeleteTask.execute(id);
    }
    public void deleteAllEvents(){
        timeBasedEventDao.deleteAllEvents();
    }
    public LiveData<List<TimeBasedEvent>> getAllEvents(){
        GetAllTimeEventTask allTimeEventTask=new GetAllTimeEventTask(timeBasedEventDao);
        try {
          allEvents= allTimeEventTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allEvents;
    }
    public void updateEvent(TimeBasedEvent event){

        TimeEventUpdateTask timeEventUpdateTask=new TimeEventUpdateTask(timeBasedEventDao);
        timeEventUpdateTask.execute();
    }
    public TimeBasedEvent getEventById(long id){
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
