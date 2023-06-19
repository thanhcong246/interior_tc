package com.vn.tcshop.foodapp.Accounts;

import android.util.Log;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    private static final String TAG = "EmailSender";

    public static void sendEmail(final String recipientEmail, final String subject, final String body) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "587");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");

                    final String senderEmail = "aitechcc2@gmail.com"; // Gmail
                    final String senderPassword = "qswdgryagodcuecr"; // Mật khẩu Gmail

                    Session session = Session.getInstance(props, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(senderEmail, senderPassword);
                        }
                    });

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(senderEmail));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                    message.setSubject(subject);
                    message.setText(body);

                    Transport.send(message);
                    Log.d(TAG, "Email sent successfully!");
                } catch (MessagingException e) {
                    Log.e(TAG, "Error sending email: " + e.getMessage());
                }
            }
        });

        thread.start();
    }
}

