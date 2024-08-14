package com.hm.userservice.controller;


import com.hm.userservice.dto.Result;
import com.hm.userservice.entity.Employee;
import com.hm.userservice.mail.service.SendNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v2/sendEmail")
public class SendEmailNotificationController {

    @Autowired
    SendNotificationService sendNotificationService;

    @PostMapping("/send")
    public String sendEmail(@RequestParam String to,
                            @RequestParam String subject,
                            @RequestParam String text) {
        sendNotificationService.sendEmailNotification(to, subject, text);
        return "Email sent successfully";
    }

    @PostMapping("/sendOutLookEmail")
    public String sendOutLookEmail(@RequestParam Integer status) {
        sendNotificationService.sendOrderStatusNotificationEmail(status);
        return "Email sent successfully";
    }
}
