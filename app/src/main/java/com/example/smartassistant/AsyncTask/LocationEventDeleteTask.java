package com.example.smartassistant.AsyncTask;

import android.os.AsyncTask;

import com.example.smartassistant.Dao.LocationBasedEventDao;

public class LocationEventDeleteTask extends AsyncTask<String,Void,Void> {
    LocationBasedEventDao locationBasedEventDao;

    public LocationEventDeleteTask(final LocationBasedEventDao locationBasedEventDao) {
        this.locationBasedEventDao = locationBasedEventDao;
    }

    @Override
    protected Void doInBackground(String... strings) {
        locationBasedEventDao.deleteById(strings[0]);
        return null;
    }

}
