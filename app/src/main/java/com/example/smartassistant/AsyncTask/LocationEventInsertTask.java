package com.example.smartassistant.AsyncTask;

import android.os.AsyncTask;

import com.example.smartassistant.Dao.LocationBasedEventDao;
import com.example.smartassistant.Model.LocationBasedEvent;

public class LocationEventInsertTask extends AsyncTask<LocationBasedEvent,Void,Long> {
    LocationBasedEventDao locationBasedEventDao;

    public LocationEventInsertTask(final LocationBasedEventDao locationBasedEventDao) {
        this.locationBasedEventDao = locationBasedEventDao;
    }

    @Override
    protected Long doInBackground(LocationBasedEvent... locationBasedEvents) {
        return locationBasedEventDao.addEvent(locationBasedEvents[0]);
    }

}
