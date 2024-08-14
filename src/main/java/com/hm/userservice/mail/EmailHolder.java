package com.hm.userservice.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Component @Setter @Getter @NoArgsConstructor
public class EmailHolder {

    private MimeMessage message = null;
    private MimeMessageHelper mimeMessageHelper=null;

    @Autowired
    private JavaMailSender mailSender;

    //@Value("${app.useNonAuthenticationSMTP}")
    private Boolean useNonAuthenticationSMTP = false;

    @Value("${spring.mail.port}")
    private String smtpPort;

    @Value("${spring.mail.host}")
    private String smtpServer;

    public EmailHolder getEmailHolder() {
        if (useNonAuthenticationSMTP) {
            message = mailSender.createMimeMessage();
        } else {
            Properties props = new Properties();
            props.put("mail.smtp.host", smtpServer);
            props.put("mail.smtp.port", smtpPort);
            props.put("mail.debug", "true");
            Session session = Session.getDefaultInstance(props);
            message = new MimeMessage(session);
        }
        mimeMessageHelper = new MimeMessageHelper(message, "utf-8");

        return this;
    }

}
