package com.example.lee.projectrun;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Email sender class
 * Created by maheshuk31 on 06/02/2016.
 */
public class EmailSender extends AsyncTask<Void,Void,Void> {

    private Context context;
    private Session session;
    private String email;
    private String subject;
    private String message;
    private String EMAIL = "teamvoidprojectrun@gmail.com";
    private String PASSWORD = "projectrun2016";

    /**
     * Constcutor used for sending an email in the RegisterActivity class.
     * @param context
     * @param email
     * @param subject
     * @param message
     */
    public EmailSender(Context context, String email, String subject, String message){
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    /**
     * In the background the email will be sent, it is currently set to a GMAIL account and has SSL
     * associated. It takes all the necessary paremeters for a email to be sent.
     * This is associated with the constructor will take what is in the fields in the RegisterActivity
     * and send an email to that person as well as a predefined message and subject line.
     * @param params
     * @return
     */
    @Override
    protected Void doInBackground(Void... params) {
        Properties property = new Properties();

        property.put("mail.smtp.host", "smtp.gmail.com");
        property.put("mail.smtp.socketFactory.port", "465");
        property.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        property.put("mail.smtp.auth", "true");
        property.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(property, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL, PASSWORD);
                    }
                });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);

            mimeMessage.setFrom(new InternetAddress(EMAIL));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);

            Transport.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
