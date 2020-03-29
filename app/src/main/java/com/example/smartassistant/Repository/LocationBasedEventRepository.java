package com.example.smartassistant.Repository;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.example.smartassistant.AppDataBase;
import com.example.smartassistant.Dao.LocationBasedEventDao;
import com.example.smartassistant.Model.LocationBasedEvent;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LocationBasedEventRepository {
    private LiveData<List<LocationBasedEvent>> allEvents;
    private LocationBasedEventDao locationBasedEventDao;
    private LocationBasedEvent event;

    @Inject
    public LocationBasedEventRepository(LocationBasedEventDao dao) {
        locationBasedEventDao=dao;
        allEvents=locationBasedEventDao.getAllEvents();
    }
    public void insert(final LocationBasedEvent event){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                locationBasedEventDao.addEvent(event);
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

    public void delete(final LocationBasedEvent event){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                locationBasedEventDao.deleteEvent(event);
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
                locationBasedEventDao.deleteById(id);
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
                locationBasedEventDao.deleteAllEvents();
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
    public LiveData<List<LocationBasedEvent>> getAllEvents(){

        return allEvents;
    }
    public void updateEvent(final LocationBasedEvent event){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                locationBasedEventDao.updateEvent(event);
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
    public LocationBasedEvent getEventById(final String id){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                LocationBasedEvent event= locationBasedEventDao.getEventById(id);
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

                        Log.e("work","successfully got the item ");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

        return event ;
    }

}



