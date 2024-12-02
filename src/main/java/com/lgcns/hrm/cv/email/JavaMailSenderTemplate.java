//package com.lgcns.hrm.cv.email;
//
//import com.lgcns.hrm.cv.common.utils.ObjectUtil;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.mail.MailProperties;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.util.ObjectUtils;
//
//import java.io.File;
//
//@Slf4j
//@AllArgsConstructor
//public class JavaMailSenderTemplate implements MailSenderTemplate {
//
//    private final JavaMailSender mailSender;
//
//    private final MailProperties mailProperties;
//
//    @Override
//    public void sendSimpleMail(String to, String subject, String content, String... cc) {
//        var message = new SimpleMailMessage();
//        message.setFrom(mailProperties.getUsername());
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(content);
//        if (!ObjectUtil.isEmpty(cc)) {
//            message.setCc(cc);
//        }
//        mailSender.send(message);
//    }
//
//    @Override
//    public void sendHtmlMail(String to, String subject, String content, String... cc) throws MessagingException {
//        var message = mailSender.createMimeMessage();
//        var helper = buildHelper(to, subject, content, message, cc);
//        mailSender.send(message);
//    }
//
//    @Override
//    public void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc) throws MessagingException {
//        var message = mailSender.createMimeMessage();
//        var helper = buildHelper(to, subject, content, message, cc);
//        var file = new FileSystemResource(new File(filePath));
//        var fileName = filePath.substring(filePath.lastIndexOf(File.separator));
//        helper.addAttachment(fileName, file);
//        mailSender.send(message);
//    }
//
//    @Override
//    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc) throws MessagingException {
//        var message = mailSender.createMimeMessage();
//        var helper = buildHelper(to, subject, content, message, cc);
//        var res = new FileSystemResource(new File(rscPath));
//        helper.addInline(rscId, res);
//        mailSender.send(message);
//    }
//
//    private MimeMessageHelper buildHelper(String to, String subject, String content, MimeMessage message, String... cc) throws MessagingException {
//        var helper = new MimeMessageHelper(message, true);
//        helper.setFrom(mailProperties.getUsername());
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(content, true);
//        if (!ObjectUtils.isEmpty(cc)) {
//            helper.setCc(cc);
//        }
//        return helper;
//    }
//}
