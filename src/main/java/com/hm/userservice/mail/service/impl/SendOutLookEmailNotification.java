package com.hm.userservice.mail.service.impl;

import com.hm.userservice.mail.common.EmailConstants;
import com.hm.userservice.mail.model.EmailModel;
import com.hm.userservice.mail.service.EmailService;
import com.hm.userservice.mail.service.SendNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service("sendOutLookEmailNotification")
public class SendOutLookEmailNotification implements SendNotificationService {


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage preConfiguredMessage;

    @Autowired
    private EmailService emailService;


    /**
     * This method will send compose and send the message
     * */
    @Override
    public void sendEmailNotification(String to, String subject, String body) {
       // SimpleMailMessage message = new SimpleMailMessage();
        preConfiguredMessage.setTo(to);
        preConfiguredMessage.setSubject(subject);
        preConfiguredMessage.setText(body);
        mailSender.send(preConfiguredMessage);
    }

    @Override
    public void sendOrderStatusNotificationEmail(Integer status) {
        EmailModel email = new EmailModel();
        email.setTemplate(EmailConstants.NOTIFICATION_EMAIL_TEMPLATE);

        if(status == 1) {
            email.setSubject("Status of Your Application");
            email.setContent("Details Updated Successfully");
        } else {
            email.setSubject("Status of Your Application");
            email.setContent("Failed::::::");
        }
        emailService.sendEmail(email);
    }
}
