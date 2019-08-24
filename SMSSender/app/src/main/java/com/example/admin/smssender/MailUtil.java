package com.example.admin.smssender;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
    private String email;
    public MailUtil(String email){
        this.email = email;
    }
    public void Start_Mail_for_msg(String msgBody){
        //Begin the mailing service
        Log.v("SUCCESS","Reached Start_Mail!!!");
        final String username = "techcotiviti123@gmail.com";
        final String password = "strongerpw123!";

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
            message.setFrom(new InternetAddress("techcotiviti123@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            Date date = new Date();
            message.setSubject("New Message on your phone " + date);

            String msg = "You just got a new message on your phone at "+ date;
            msg+= "\n------------------------------";
            msg+= "\n" + msgBody;
            message.setText(msg);
            //  + "\n\n No spam to my email, please!");
            Transport.send(message);
            System.out.println("Message sent");
        } catch (MessagingException e) {
            System.out.println("s");
            throw new RuntimeException(e);
        }
    }

    public void Start_Mail_for_call(String incomingNumber) {
        //Begin the mailing service
        Log.v("SUCCESS","Reached Start_Mail!!!");
        final String username = "techcotiviti123@gmail.com";
        final String password = "strongerpw123!";

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
            message.setFrom(new InternetAddress("techcotiviti123@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            Date date = new Date();
            message.setSubject("Phone Call Notification on your phone : " + date);
            message.setText("You just got a call on your phone at "+ date + "\nFrom: " + incomingNumber);
            Transport.send(message);
            System.out.println("Message sent");
        } catch (MessagingException e) {
            System.out.println("s");
            throw new RuntimeException(e);
        }
    }
}
