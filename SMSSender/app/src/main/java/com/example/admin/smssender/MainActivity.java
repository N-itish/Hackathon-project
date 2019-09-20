package com.example.admin.smssender;

import android.Manifest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPrefrence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //   Checks if the app has permission for the READ_PHONE_STATE
        //   if permission is not given then ask for permission from the user
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_PHONE_STATE)){
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_PHONE_STATE},1);
            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_PHONE_STATE},1);
            }
        }

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.RECEIVE_SMS)){
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.RECEIVE_SMS},1);
            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.RECEIVE_SMS},1);
            }
        }

        //        Checking for permissios from the user
        final String[] phone_permission = {Manifest.permission.READ_CALL_LOG};
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_CALL_LOG)==PackageManager.PERMISSION_GRANTED){
            System.out.println("Permission for Call log is already present");

        }
        else
        {
            System.out.println("Requesting for the required permissions");
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    phone_permission, 123);
        }

        sharedPrefrence = getSharedPreferences("pref_1",MODE_PRIVATE);
        if(sharedPrefrence.getString("usermail",null) != null)
        {
            Intent intent = new Intent(MainActivity.this,Homepage.class);
            startActivity(intent);
        }
        else
        {
            final EditText email = (EditText)findViewById(R.id.email);
            final EditText username = (EditText)findViewById(R.id.name);
            Button setEmail = (Button)findViewById(R.id.setMail);
            setEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (email.getText().toString().replace(" ","").equalsIgnoreCase("") || username.getText().toString().replace(" ","").equalsIgnoreCase(""))
                    {
                        Toast.makeText(MainActivity.this,"Either email or username is empty",Toast.LENGTH_LONG).show();
                    }
                    else {
                        storeUserDetails userDetails = new storeUserDetails(email.getText().toString(), username.getText().toString());
                        userDetails.start();
                        Intent intent = new Intent(MainActivity.this, Homepage.class);
                        startActivity(intent);
                    }
                }
            });
        }

    }
    //Check if the user granted permission or not
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED ) {
                        System.out.println("Permission granted");
                        Toast.makeText(this, "Pemission granted!", Toast.LENGTH_LONG).show();
                    } else {
                        System.out.println("Permission denied");
                        Toast.makeText(this, "No Permission", Toast.LENGTH_LONG).show();
                    }
                    return;
                }
            }
        }
    }

    public class storeUserDetails extends Thread{
        SharedPreferences pref;
        private String email;
        private String username;

        public storeUserDetails(String email,String username){
            this.email = email;
            this.username = username;
            pref =  getSharedPreferences("pref_1",MODE_PRIVATE);
        }

        public void run(){
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("username",username);
            editor.putString("email",email);
            editor.apply();
            editor.commit();
            Log.v("SharedPref","Shared data is set");
        }

    }

}


