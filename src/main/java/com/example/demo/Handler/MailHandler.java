package com.example.demo.Handler;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
@Component
public class MailHandler {

    private JavaMailSender sender;

    private MimeMessage message;

    private MimeMessageHelper messageHelper;

    public MailHandler(JavaMailSender sender) throws MessagingException{
        this.sender = sender;
        this.message = this.sender.createMimeMessage();
        this.messageHelper = new MimeMessageHelper(message, true, "UTF-8");
    }

    public void setFrom(String email, String name) throws UnsupportedEncodingException, MessagingException{
        messageHelper.setFrom(email, name);
    }

    public void setTo(String email) throws MessagingException{
        messageHelper.setTo(email);
    }

    public void setSubject(String subject) throws MessagingException{
        messageHelper.setSubject(subject);
    }

    public void setText(String text) throws MessagingException{
        messageHelper.setText(text, true);
    }

    public void send(){
        try {
            sender.send(message);
        } catch (Exception e){
            e.printStackTrace();
        }
    }



}
