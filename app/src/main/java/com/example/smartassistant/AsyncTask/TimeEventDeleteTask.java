package com.example.smartassistant.AsyncTask;

import android.os.AsyncTask;

import com.example.smartassistant.Dao.LocationBasedEventDao;
import com.example.smartassistant.Dao.TimeBasedEventDao;
import com.example.smartassistant.Model.TimeBasedEvent;

public class TimeEventDeleteTask extends AsyncTask<String,Void,Void> {
    TimeBasedEventDao timeBasedEventDao;

    public TimeEventDeleteTask(final TimeBasedEventDao timeBasedEventDao) {
        this.timeBasedEventDao = timeBasedEventDao;
    }

    @Override
    protected Void doInBackground(String... strings) {
        timeBasedEventDao.deleteById(strings[0]);
        return null;
    }

}
