package com.example.admin.smssender;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by admin on 8/23/2019.
 */

public class SendMail extends Activity{
    public boolean sendEmail() {
        Log.i("Send email", "");
        String[] TO = {"sandesh.pokhrel56@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email.", "");
            return true;
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
