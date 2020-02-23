package com.example.smartassistant.AsyncTask;

import android.os.AsyncTask;

import com.example.smartassistant.Dao.LocationBasedEventDao;
import com.example.smartassistant.Dao.TimeBasedEventDao;
import com.example.smartassistant.Model.LocationBasedEvent;
import com.example.smartassistant.Model.TimeBasedEvent;

public class TimeEventUpdateTask extends AsyncTask<TimeBasedEvent,Void,Void> {
    TimeBasedEventDao timeBasedEventDao;

    public TimeEventUpdateTask(final TimeBasedEventDao timeBasedEventDao) {
        this.timeBasedEventDao = timeBasedEventDao;
    }

    @Override
    protected Void doInBackground(TimeBasedEvent... timeBasedEvents) {
        timeBasedEventDao.updateEvent(timeBasedEvents[0]);
        return null ;
    }


}
