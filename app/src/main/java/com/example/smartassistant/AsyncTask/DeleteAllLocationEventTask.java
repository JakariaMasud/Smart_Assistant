package com.example.smartassistant.AsyncTask;

import android.os.AsyncTask;

import com.example.smartassistant.Dao.LocationBasedEventDao;

public class DeleteAllLocationEventTask extends AsyncTask<Void,Void,Void> {
    LocationBasedEventDao locationBasedEventDao;

    public DeleteAllLocationEventTask(final LocationBasedEventDao locationBasedEventDao) {
        this.locationBasedEventDao = locationBasedEventDao;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        locationBasedEventDao.deleteAllEvents();
        return null;
    }
}
