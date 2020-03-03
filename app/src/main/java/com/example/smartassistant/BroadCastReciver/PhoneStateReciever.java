package com.example.smartassistant.BroadCastReciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.example.smartassistant.Service.MessageSendingService;

public class PhoneStateReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephonyManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneCallListener listener=new PhoneCallListener(context);

        telephonyManager.listen(listener,PhoneStateListener.LISTEN_CALL_STATE);



    }
    public class PhoneCallListener extends PhoneStateListener{


        int prev_state=0;
        Context context;
        public PhoneCallListener (Context context){
            this.context=context;
        }



        @Override
        public void onCallStateChanged(final int state, final String phoneNumber) {
            super.onCallStateChanged(state, phoneNumber);

            switch(state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    prev_state=state;

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:

                    prev_state = state;
                    break;
                case TelephonyManager.CALL_STATE_IDLE:


                    if ((prev_state == TelephonyManager.CALL_STATE_OFFHOOK)) {
                        prev_state = state;
                        //Answered Call which is ended
                    }
                    if ((prev_state == TelephonyManager.CALL_STATE_RINGING)) {
                        prev_state = state;

                        //Rejected or Missed call
                        Intent intent=new Intent(context, MessageSendingService.class);
                        MessageSendingService.enqueueMessage(context,intent,phoneNumber);
                    }
                    break;
            }



        }
    }
}