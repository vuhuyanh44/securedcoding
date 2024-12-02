package com.lgcns.hrm.cv.email;

import jakarta.mail.MessagingException;

public interface MailSenderTemplate {

    void sendSimpleMail(String to, String subject, String content, String... cc);

    void sendHtmlMail(String to, String subject, String content, String... cc) throws MessagingException;

    void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc) throws MessagingException;

    void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc) throws MessagingException;

}
