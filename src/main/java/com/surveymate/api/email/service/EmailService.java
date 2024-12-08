package com.surveymate.api.email.service;

public interface EmailService {

    void sendEmail(String to, String subject, String text);

}
