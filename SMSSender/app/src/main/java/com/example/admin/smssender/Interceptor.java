package com.example.admin.smssender;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by admin on 8/24/2019.
 */

public class Interceptor extends BroadcastReceiver {
    public enum MailTypes{
        SMS,
        CALL
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        //Getting the users mail
        SharedPreferences prefs = context.getSharedPreferences("pref_1",Context.MODE_PRIVATE);
        final String email = prefs.getString("email",null);

        System.out.println(email);
        Log.v("SUCCESS","Detected by receiver!!!");

        //checking if the phone is ringing
        if(intent.getAction().equals("android.intent.action.PHONE_STATE")){


            String State = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            final String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if(State.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                //Sends the message to the user
                    Log.v("SUCCESS","reached InterceptCall");
                    if(incomingNumber != null) {
                        startMailThread(email,incomingNumber,MailTypes.CALL);
                    }
                    else
                    {
                        startMailThread(email,"unable to get phone no!!!",MailTypes.CALL);
                    }

            }
        }
        // checking if the sms is recieved
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Log.v("SUCCESS","Entered inside intent for sms");
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if(bundle !=null){
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i< msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        final String message = "\nFrom :  " + msg_from + "\n--------\nMessage Content: \n" + msgBody;
                        Log.v("SUCCESS", message);
                        startMailThread(email,message,MailTypes.SMS);
                    }
            }
        }
    }

    public void startMailThread(final String email, final String message, final MailTypes types){
        Thread mailThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Running the SMS Notifying mail thread!!!");
                MailUtil mailUtil = new MailUtil(email);
                switch (types){
                    case SMS:
                        mailUtil.Start_Mail_for_msg(message);
                    case CALL:
                        mailUtil.Start_Mail_for_call(message);
                }

            }
        });
        //Starting the mail thread
        System.out.println("Thread starting now !!!");
        mailThread.start();
    }
}
