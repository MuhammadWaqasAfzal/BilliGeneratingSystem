package org.visualobjectsoftware.intellijaassignment.utility;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.io.File;


import javax.activation.*;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.io.File;
import javax.activation.*;

public class EmailUtility {

    public static void sendEmailWithAttachment(String recipient, String subject, String content, String filePath) throws MessagingException {
        // Assuming you are sending email from through Gmail's SMTP
        String host = "smtp.gmail.com";
        final String username = "javabilltest@gmail.com"; // Change accordingly
        //final String password = "MW12345678@"; // Change accordingly
        final String password = "uvcvezhygysnomww"; // Change accordingly

        // Set properties for the SMTP server
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        // Create a default MimeMessage object.
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject(subject);

        // Create a multipart message for attachment
        Multipart multipart = new MimeMultipart();

        // Set text message part
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(content);
        multipart.addBodyPart(messageBodyPart);

        // Second part is attachment
        messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filePath);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(new File(filePath).getName());
        multipart.addBodyPart(messageBodyPart);

        // Send the complete message parts
        message.setContent(multipart);

        // Send message
        Transport.send(message);
    }

    public static void main(String[] args) {
        String recipient = "recipient@example.com"; // Replace with the recipient's email address
        String subject = "Test Email";
        String content = "This is a test email with an attachment.";
        String filePath = "path_to_your_attachment_file.txt"; // Replace with the actual file path

        try {
            sendEmailWithAttachment(recipient, subject, content, filePath);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
