//package com.lgcns.hrm.cv.config;
//
//import com.lgcns.hrm.cv.email.JavaMailSenderTemplate;
//import jakarta.annotation.Resource;
//import jakarta.mail.MessagingException;
//import jakarta.mail.Session;
//import jakarta.mail.Store;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.boot.autoconfigure.mail.MailProperties;
//import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.mail.javamail.JavaMailSender;
//
//import java.util.Properties;
//
//@Configuration
//@AutoConfigureAfter(MailSenderAutoConfiguration.class)
//@RequiredArgsConstructor
//public class MailConfiguration {
//
//    @Resource
//    private JavaMailSender mailSender;
//
//    @Resource
//    private MailProperties mailProperties;
//
//    @Bean
//    @ConditionalOnBean({MailProperties.class, JavaMailSender.class})
//    public JavaMailSenderTemplate mailTemplate() {
//        return new JavaMailSenderTemplate(mailSender, mailProperties);
//    }
//
//    public Properties getMailProperties(Environment environment) {
//        Properties properties = new Properties();
//        properties.put("mail.imap.host", environment.getProperty("spring.mail-imap.host"));
//        properties.put("mail.imap.port", environment.getProperty("spring.mail-imap.port"));
//        properties.put("mail.imap.socketFactory.class", environment.getProperty("spring.mail-imap.socketFactory.class"));
//        properties.put("mail.imap.socketFactory.fallback", environment.getProperty("spring.mail-imap.socketFactory.fallback"));
//        properties.put("mail.imap.socketFactory.port", (environment.getProperty("spring.mail-imap.port")));
//        return properties;
//    }
//
//    @Bean
//    public Store setSessionStoreProperties(Environment environment) throws MessagingException {
//        Properties properties = getMailProperties(environment);
//        Session session = Session.getDefaultInstance(properties);
//
//        Store store = session.getStore(environment.getProperty("spring.mail-imap.store.protocol"));
//        store.connect(environment.getProperty("spring.mail-imap.username"), environment.getProperty("spring.mail-imap.password"));
//
//        return store;
//    }
//}
