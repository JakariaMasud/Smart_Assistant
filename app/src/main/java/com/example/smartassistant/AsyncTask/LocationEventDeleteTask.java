package com.example.smartassistant.AsyncTask;

import android.os.AsyncTask;

import com.example.smartassistant.Dao.LocationBasedEventDao;

public class LocationEventDeleteTask extends AsyncTask<Long,Void,Void> {
    LocationBasedEventDao locationBasedEventDao;

    public LocationEventDeleteTask(final LocationBasedEventDao locationBasedEventDao) {
        this.locationBasedEventDao = locationBasedEventDao;
    }

    @Override
    protected Void doInBackground(Long... Longs) {
        locationBasedEventDao.deleteById(Longs[0]);
        return null;
    }

}
