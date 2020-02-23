package com.example.smartassistant.AsyncTask;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.smartassistant.Dao.LocationBasedEventDao;
import com.example.smartassistant.Model.LocationBasedEvent;

import java.util.List;

public class GetAllLocationEventTask extends AsyncTask<Void ,Void,LiveData<List<LocationBasedEvent>>> {
    LocationBasedEventDao locationBasedEventDao;

    public GetAllLocationEventTask(final LocationBasedEventDao locationBasedEventDao) {
        this.locationBasedEventDao = locationBasedEventDao;
    }


    @Override
    protected LiveData<List<LocationBasedEvent>> doInBackground(Void... voids) {
        return locationBasedEventDao.getAllEvents();
    }
}
