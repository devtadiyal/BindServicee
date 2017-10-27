package com.mobilesoftphone4.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class IncomingSms extends BroadcastReceiver
{  int duration;
String message;
String senderNum;


    @Override
    public void onReceive(Context context, Intent intent)
    {
        System.out.println("SMS is Call--->");

        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null)
            {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj .length; i++)
                {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                     senderNum = phoneNumber.substring(2).trim() ;
                     message = currentMessage .getDisplayMessageBody();
                    Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);
                     duration = Toast.LENGTH_LONG;
                    
                    try
                    {
                        if (senderNum.equals("-Yepingo"))
                        {
                            System.out.println("set the value"+message);
                            VerifyOTPActivity.recivedSms(message );
                            
                        }
                    }
                    catch(Exception e){}
                   
                }
//                Toast toast = Toast.makeText(context,
//                        "senderNum:"+senderNum + ", message: " + message, duration);
//                toast.show();
            }

        } catch (Exception e)
        {

        }
    }

}
