package com.example.smartassistant.AsyncTask;

import android.os.AsyncTask;

import com.example.smartassistant.Dao.TimeBasedEventDao;
import com.example.smartassistant.Model.TimeBasedEvent;

public class GetTimeEventByIdTask extends AsyncTask<String,Void, TimeBasedEvent> {
    TimeBasedEventDao timeBasedEventDao;

    public GetTimeEventByIdTask(final TimeBasedEventDao timeBasedEventDao) {
        this.timeBasedEventDao = timeBasedEventDao;
    }

    @Override
    protected TimeBasedEvent doInBackground(String... strings) {
        return timeBasedEventDao.getEventById(strings[0]);
    }
}
