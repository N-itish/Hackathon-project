package com.example.admin.smssender;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //   Checks if the app has permission for the READ_PHONE_STATE
        //   if permission is not given then ask for permission
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_PHONE_STATE)){
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_PHONE_STATE},1);
            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_PHONE_STATE},1);
            }
        }
        else
        {
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
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
}

