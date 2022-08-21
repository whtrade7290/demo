package com.example.demo.Service;

import com.example.demo.Handler.MailHandler;
import com.example.demo.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MailService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private MailHandler mail;


    public void send(String email, String randomNum) {


        try {
            mail = new MailHandler(sender);
            mail.setFrom("no-reply@norepy.com", "StoreJolly");
            mail.setTo(email);
            mail.setSubject("Store Jolly 인증메일 입니다.");
            mail.setText("고객님의 인증번호는 " + randomNum + " 입니다.<br>" + "정확하게 입력부탁드립니다.");
            mail.send();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
