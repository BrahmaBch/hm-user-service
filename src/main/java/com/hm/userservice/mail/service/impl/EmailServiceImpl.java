package com.hm.userservice.mail.service.impl;

import com.hm.userservice.mail.EmailHolder;
import com.hm.userservice.mail.common.EmailConstants;
import com.hm.userservice.mail.model.EmailModel;
import com.hm.userservice.mail.service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;

@Service @Slf4j
public class EmailServiceImpl implements EmailService {


    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.recipient}")
    private String toEmail;

    @Value("${spring.mail.cc-recipient}")
    private String toCCEmail;

    //@Value("${app.useNonAuthenticationSMTP}")
    private Boolean useNonAuthenticationSMTP = false;

    @Value("${spring.mail.host}")
    private String smtpServer;

    @Value("${spring.mail.port}")
    private String smtpPort;

    //@Value("${app.server-profile}")
    private String segregation = "dev";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailHolder emailHolder;

    @Autowired
    private Configuration config;
    @Override
    public void sendEmail(EmailModel mail) {
        EmailHolder holder = this.emailHolder.getEmailHolder();
        MimeMessageHelper helper = holder.getMimeMessageHelper();
        MimeMessage message = mailSender.createMimeMessage();
        try {
            helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            mail.setTo(toEmail);
            mail.setToCC(toCCEmail);
            mail.setFrom(fromEmail);
            helper.setTo(mail.getTo().toLowerCase().split(";"));
            if(!StringUtils.isBlank(mail.getToCC())) {
                helper.setCc(mail.getToCC().toLowerCase().split(";"));
            }
            helper.setFrom(fromEmail, "Brahmaiah");
            if(!StringUtils.isEmpty(segregation.trim())) {
                helper.setSubject(segregation +"\n:\n" + mail.getSubject());
            } else {
                helper.setSubject(mail.getSubject());
            }
            mail.setContent(getContentFromTemplate(mail, mail.getTemplate()));
            helper.setText(mail.getContent(), true);
            if (!this.useNonAuthenticationSMTP) {
                log.info("trying to send email...");
                mailSender.send(message);
                log.info("email successfully sent.");
            } else {
                sendEmailWithoutAuthentication(helper);
                log.info("email successfully sent for sendEmailWithoutAuthentication ");
            }
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Failed to send email, error is:" + e.getMessage());
        }
    }

    @Override
    public void sendErrorEmail(EmailModel mail) {
        EmailHolder holder = this.emailHolder.getEmailHolder();
        MimeMessageHelper helper = holder.getMimeMessageHelper();
        MimeMessage message = mailSender.createMimeMessage();

        try {
            helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            helper.setTo(toEmail.toLowerCase().split(";"));
            if(!StringUtils.isBlank(toCCEmail)) {
                helper.setCc(toCCEmail.toLowerCase().split(";"));
            }
            helper.setFrom(fromEmail, "Brahmaiah");
            if(!StringUtils.isEmpty(segregation.trim())) {
                helper.setSubject(segregation +"\n:\n" + mail.getSubject());
            } else {
                helper.setSubject(mail.getSubject());
            }
            if(mail.getTemplate() != null) {
                mail.setContent(getContentFromTemplate(mail, mail.getTemplate()));
                helper.setText(mail.getContent(), true);
            } else {
                helper.setText(mail.getStackTrace(), true);
            }

            if (!this.useNonAuthenticationSMTP) {
                log.info("trying to send error email...");
                mailSender.send(message);
                log.info("error email successfully sent.");
            } else {
                log.info("trying to send error email for sendEmailWithoutAuthentication...");
                sendEmailWithoutAuthentication(helper);
                log.info("error email successfully for sendEmailWithoutAuthentication");
            }
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
    }


    public void sendEmailWithoutAuthentication(MimeMessageHelper helper) {
        try {
            Properties props = new Properties();
            props.put("mail.debug", "true");
            props.put("mail.smtp.host", this.smtpServer);
            props.put("mail.smtp.port", smtpPort);

            Session session = Session.getDefaultInstance(props);
            MimeMessage message = helper.getMimeMessage();
            message.setFrom(new InternetAddress(this.fromEmail));

            message.setRecipients(Message.RecipientType.TO,
                    helper.getMimeMessage().getRecipients(Message.RecipientType.TO));
            message.setSubject(helper.getMimeMessage().getSubject());
            message.setContent(helper.getMimeMessage().getContent(), "UTF-8"); // as "text/plain"
            message.setSentDate(new Date());
            Transport.send(helper.getMimeMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getContentFromTemplate(Object model, String template) {
        String content = "";
        if(template == null) {
            template = EmailConstants.NOTIFICATION_EMAIL_TEMPLATE;
        }
        try {
            Optional<String> value = Optional.of(template);
            if (value.isPresent()) {
                Template t = config.getTemplate(template);
                content = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return content.toString();
    }



}
