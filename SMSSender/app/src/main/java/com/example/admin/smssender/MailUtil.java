package com.example.admin.smssender;


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
    static final String USERNAME = "techcotiviti123@gmail.com";
    static final String PASSWORD = "strongerpw123!";
    private String email;
    public MailUtil(String email){
        this.email = email;
    }


    public void sendMail(String messageToSend,String mailTypes){



        // Setting the properties for contacting the mail server
        Properties props = new Properties();
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //Creating a new session in the mail
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {

                        Log.v("SUCCESS","Session Created!!!");
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });
        //Trying to send the mail
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            Date date = new Date();
            message.setSubject("New Message on your phone " + date);
            //check if SMS or CALL
            switch (mailTypes){
                case "SMS":
                    message.setSubject("New Message on your phone " + date);
                    String msg = "You just got a new message on your phone at "+ date;
                    msg+= "\n------------------------------";
                    msg+= "\n" + messageToSend;
                    message.setText(msg);
                case "CALL":
                    message.setSubject("Phone Call Notification on your phone : " + date);
                    message.setText("You just got a call on your phone at "+ date + "\nFrom: " + messageToSend);
            }
            System.out.println(message);
            Transport.send(message);
            System.out.println("Message sent");
        } catch (MessagingException messagingError) {
            System.out.println("Error occured in Sending Mails");
            System.out.println(messagingError.getMessage());
        }
    }
}
