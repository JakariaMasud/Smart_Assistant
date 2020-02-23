package com.example.smartassistant.AsyncTask;

import android.os.AsyncTask;

import com.example.smartassistant.Dao.LocationBasedEventDao;
import com.example.smartassistant.Model.LocationBasedEvent;

public class LocationEventUpdateTask extends AsyncTask<LocationBasedEvent,Void,Void> {
    LocationBasedEventDao locationBasedEventDao;

    public LocationEventUpdateTask(final LocationBasedEventDao locationBasedEventDao) {
        this.locationBasedEventDao = locationBasedEventDao;
    }

    @Override
    protected Void doInBackground(LocationBasedEvent... locationBasedEvents) {
        locationBasedEventDao.updateEvent(locationBasedEvents[0]);
        return null ;
    }


}
