package com.example.smartassistant.AsyncTask;

import android.os.AsyncTask;

import com.example.smartassistant.Dao.TimeBasedEventDao;

public class DeleteAllTimeEventTask extends AsyncTask<Void,Void,Void> {
    TimeBasedEventDao timeBasedEventDao;

    public DeleteAllTimeEventTask(final TimeBasedEventDao timeBasedEventDao) {
        this.timeBasedEventDao = timeBasedEventDao;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        timeBasedEventDao.deleteAllEvents();
        return null;
    }
}
