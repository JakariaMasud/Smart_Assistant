package com.example.smartassistant.Repository;

import android.app.Application;
import android.os.Looper;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.example.smartassistant.AppDataBase;
import com.example.smartassistant.AsyncTask.GetTimeEventByIdTask;
import com.example.smartassistant.Dao.TimeBasedEventDao;
import com.example.smartassistant.Model.TimeBasedEvent;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.functions.Action;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TimeBasedEventRepository {
    private LiveData<List<TimeBasedEvent>> allEvents;
    private TimeBasedEventDao timeBasedEventDao;
    private  TimeBasedEvent event;

    @Inject
    public TimeBasedEventRepository(TimeBasedEventDao dao) {
        timeBasedEventDao=dao;
        allEvents=timeBasedEventDao.getAllEvents();

    }
    public void insert(final TimeBasedEvent event){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                timeBasedEventDao.addEvent(event);
            }
        };
        Completable.fromRunnable(runnable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                        Log.e("work","successfully inserted ");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });






    }

    public void delete(final TimeBasedEvent event){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                timeBasedEventDao.deleteEvent(event);
            }
        };
        Completable.fromRunnable(runnable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                        Log.e("work","successfully deleted ");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void deleteById(final String id){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                timeBasedEventDao.deleteById(id);
            }
        };
        Completable.fromRunnable(runnable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                        Log.e("work","successfully deleted ");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });




    }
    public void deleteAllEvents(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                timeBasedEventDao.deleteAllEvents();
            }
        };
        Completable.fromRunnable(runnable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                        Log.e("work","successfully deleted all event ");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });


    }
    public LiveData<List<TimeBasedEvent>> getAllEvents(){

        return allEvents;
    }
    public void updateEvent(final TimeBasedEvent event){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                timeBasedEventDao.updateEvent(event);
            }
        };
        Completable.fromRunnable(runnable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                        Log.e("work","successfully deleted ");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
    public TimeBasedEvent getEventById(final String id) {
        GetTimeEventByIdTask task=new GetTimeEventByIdTask(timeBasedEventDao);
        try {
            event= task.execute(id).get();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }



return event;
    }
}
