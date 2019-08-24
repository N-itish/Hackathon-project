package com.example.admin.smssender;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        final SharedPreferences pref = getSharedPreferences("pref_1",MODE_PRIVATE);
        String message = pref.getString("usermail",null);
        TextView view = (TextView)findViewById(R.id.textView);
        view.setText("Hello "+message);

        Button newmail = (Button)findViewById(R.id.goBack);
        newmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,MainActivity.class);
                startActivity(intent);
                pref.edit().remove("usermail").commit();

            }
        });


    }
}
