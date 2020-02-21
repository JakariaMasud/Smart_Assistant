package com.example.smartassistant.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class PhoneStateReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneCallListener listener=new PhoneCallListener(context);

        telephonyManager.listen(listener,PhoneStateListener.LISTEN_CALL_STATE);



    }
    public class PhoneCallListener extends PhoneStateListener{

        Context context;
        public PhoneCallListener (Context context){
            this.context=context;
        }



        @Override
        public void onCallStateChanged(final int state, final String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);
            if(state==TelephonyManager.CALL_STATE_OFFHOOK){
                Intent intent=new Intent(context,MessageSendingService.class);
                MessageSendingService.enqueueMessage(context,intent,phoneNumber);


            }

        }
    }
}
