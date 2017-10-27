package com.mobilesoftphone4.ui.dialpad;



import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.mobilesoftphone4.utils.Log;

 public class ListenToPhoneState extends PhoneStateListener {

        public void onCallStateChanged(int state, String incomingNumber) {
            Log.i("telephony-example", "State changed: " + stateName(state));
        }

        String stateName(int state) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE: return "Idle";
                case TelephonyManager.CALL_STATE_OFFHOOK: return "Off hook";
                case TelephonyManager.CALL_STATE_RINGING: return "Ringing";
            }
            return Integer.toString(state);
        }
    }
