package com.example.smartassistant.AsyncTask;

import android.os.AsyncTask;

import com.example.smartassistant.Dao.LocationBasedEventDao;
import com.example.smartassistant.Model.LocationBasedEvent;

public class GetLocationEventByIdTask extends AsyncTask<Long,Void, LocationBasedEvent> {
    LocationBasedEventDao locationBasedEventDao;

    public GetLocationEventByIdTask(final LocationBasedEventDao locationBasedEventDao) {
        this.locationBasedEventDao = locationBasedEventDao;
    }

    @Override
    protected LocationBasedEvent doInBackground(Long... longs) {
        return locationBasedEventDao.getEventById(longs[0]);
    }
}
