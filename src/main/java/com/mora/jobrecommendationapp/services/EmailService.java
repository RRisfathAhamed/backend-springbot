package com.mora.jobrecommendationapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.resetPasswordUrl}")
    private String resetPasswordUrl;

    public void sendResetLink(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        // Modify the URL to include the custom path
        message.setText("To reset your password, click the link below:\n" + resetPasswordUrl + "/resetpassword/jobSeeker?token=" + token);
        mailSender.send(message);
    }

    public void sendResetLinkJobProvider(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        // Modify the URL to include the custom path for job providers
        message.setText("To reset your password, click the link below:\n" + resetPasswordUrl + "/resetpassword/jobProvider?token=" + token);
        mailSender.send(message);
    }
}
