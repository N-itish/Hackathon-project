package com.example.admin.smssender;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInstaller;
import android.util.Log;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by admin on 8/23/2019.
 */

public class MailUtil {

    public void Start_Mail() {

        Log.v("SUCCESS","Reached Start_Mail!!!");
        final String username = "kshitizmaharjan@gmail.com";
        final String password = "kathmandunepal";
//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "465");
        Log.v("SUCCESS","Reached here!!!");


        //alternative
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {

                        Log.v("SUCCESS","Session Created!!!");
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("kshitizmaharjan@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("ujjwol.dandekhya@cotiviti.com"));
            Date date = new Date();
            message.setSubject("Phone Call Notification on your phone " + date);
            message.setText("You just got a call on your phone at "+ date);
                  //  + "\n\n No spam to my email, please!");
            Transport.send(message);
            System.out.println("Message sent");
        } catch (MessagingException e) {
            System.out.println("s");
            throw new RuntimeException(e);
        }
    }
}
