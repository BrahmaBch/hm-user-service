package com.hm.userservice.mail.service;

public interface SendNotificationService {

    public void sendEmailNotification(String to, String subject, String body);

    public void sendOrderStatusNotificationEmail(Integer status);
}
