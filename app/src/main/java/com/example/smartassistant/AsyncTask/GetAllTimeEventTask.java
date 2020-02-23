package com.example.smartassistant.AsyncTask;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.smartassistant.Dao.LocationBasedEventDao;
import com.example.smartassistant.Dao.TimeBasedEventDao;
import com.example.smartassistant.Model.LocationBasedEvent;
import com.example.smartassistant.Model.TimeBasedEvent;

import java.util.List;

public class GetAllTimeEventTask extends AsyncTask<Void ,Void,LiveData<List<TimeBasedEvent>>> {
    TimeBasedEventDao timeBasedEventDao;

    public GetAllTimeEventTask(final TimeBasedEventDao timeBasedEventDao) {
        this.timeBasedEventDao = timeBasedEventDao;
    }


    @Override
    protected LiveData<List<TimeBasedEvent>> doInBackground(Void... voids) {
        return timeBasedEventDao.getAllEvents();
    }


}
