package com.example.admin.smssender;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Homepage extends AppCompatActivity {

    Interceptor intercept = new Interceptor();
    boolean setlistnerOn = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        final SharedPreferences pref = getSharedPreferences("pref_1",MODE_PRIVATE);
        String message = pref.getString("username",null);
        TextView view = (TextView)findViewById(R.id.textView);
        view.setText("Hello "+message);

        Button newmail = (Button)findViewById(R.id.goBack);
        Button startReciever = (Button)findViewById(R.id.reciever);

        startReciever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (setlistnerOn){
                    try {
                        IntentFilter filter = new IntentFilter();
                        filter.addAction("android.intent.action.PHONE_STATE");
                        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
                        registerReceiver(intercept, filter);
                        Toast.makeText(Homepage.this,"Listners started!!!",Toast.LENGTH_SHORT).show();
                    }catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                    setlistnerOn = false;
                }
                else {
                    unregisterReceiver(intercept);
                    Toast.makeText(Homepage.this,"Listners closed!!!",Toast.LENGTH_SHORT).show();
                    setlistnerOn = true;
                }

            }
        });
        newmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // removing the shared prefrences
                pref.edit().clear().apply();
                Intent intent = new Intent(Homepage.this,MainActivity.class);
                startActivity(intent);

            }
        });


    }
    //  This sets the activity to background
    //  It does not revert back to the previous activity
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    public void onDestroy() {
        unregisterReceiver(intercept);
        super.onDestroy();

    }

}
