package com.example.smartassistant.BroadCastReciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.smartassistant.Utils.PhoneStateChangeListener;


public class PhoneStateReciever extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        PhoneStateChangeListener pscl = new PhoneStateChangeListener(context);
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(pscl, PhoneStateListener.LISTEN_CALL_STATE);
    }

}