package com.hm.userservice.mail.service;

import com.hm.userservice.mail.model.EmailModel;

public interface EmailService {
    void sendEmail(EmailModel model);
    void sendErrorEmail(EmailModel model);
}
