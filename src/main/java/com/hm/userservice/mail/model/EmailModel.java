package com.hm.userservice.mail.model;


import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class EmailModel {

    private String to;
    private String from;
    private String toCC;
    private String subject;
    private String content;
    private String message;
    private String stackTrace;
    private String errorMessage;
    private String template;
}
