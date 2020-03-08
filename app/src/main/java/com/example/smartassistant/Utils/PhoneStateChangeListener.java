package com.example.smartassistant.Utils;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.smartassistant.Worker.MessageSendingWorker;

public class PhoneStateChangeListener extends PhoneStateListener {
    private Context context;
    public PhoneStateChangeListener(Context context) {
        this.context=context;
    }

    public static boolean wasRinging;
    String LOG_TAG = "PhoneListener";
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch(state){
            case TelephonyManager.CALL_STATE_RINGING:
                Log.i(LOG_TAG, "RINGING");
                wasRinging = true;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.i(LOG_TAG, "OFFHOOK");

                if (!wasRinging) {
                    // Start your new activity
                } else {
                    // Cancel your old activity
                }

                // this should be the last piece of code before the break
                wasRinging = true;
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                Log.i(LOG_TAG, "IDLE");
                // this should be the last piece of code before the break
                if(wasRinging){
                    Log.e("SEND","SENDING MSG");
                    Data data=new Data.Builder()
                            .putString("phone",incomingNumber)
                            .build();
                    OneTimeWorkRequest oneTimeWorkRequest=
                            new OneTimeWorkRequest.Builder(MessageSendingWorker.class)
                                    .setInputData(data).build();
                    WorkManager.getInstance(context).enqueue(oneTimeWorkRequest);
                }
                wasRinging = false;
                break;
        }
    }
}
