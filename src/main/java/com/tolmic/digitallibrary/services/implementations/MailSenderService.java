package com.tolmic.digitallibrary.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String emai, String subject, String messege) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("");
        mailMessage.setTo();
        mailMessage.setSubject(subject);
        mailMessage.setText(messege);

        mailSender.send(mailMessage);
    }

}
