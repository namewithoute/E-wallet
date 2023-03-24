package com.example.ewallet.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class SendMailService {
    private final String FROM_EMAIL="trn.truong89@gmail.com";
    @Autowired
    private JavaMailSender mailSender;
    public void sender(String toEmail,String username,String password){


        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(FROM_EMAIL);
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setSubject("THÔNG BÁO TÀI KHOẢN ĐĂNG NHẬP VÀO HỆ THỐNG");
        simpleMailMessage.setText("Tài khoản đăng nhập của bạn là: \n username: "+ username
                + "\n password: " + password );
        mailSender.send(simpleMailMessage);
    }
}
