package com.example.smartassistant.AsyncTask;

import android.os.AsyncTask;

import com.example.smartassistant.Dao.TimeBasedEventDao;
import com.example.smartassistant.Model.TimeBasedEvent;

public class TimeEventInsertTask extends AsyncTask<TimeBasedEvent,Void,Void> {
    TimeBasedEventDao TimeBasedEventDao;

    public TimeEventInsertTask(final TimeBasedEventDao TimeBasedEventDao) {
        this.TimeBasedEventDao = TimeBasedEventDao;
    }

    @Override
    protected Void doInBackground(TimeBasedEvent... TimeBasedEvents) {
        TimeBasedEventDao.addEvent(TimeBasedEvents[0]);
        return null;
    }


}
