package cn.gw.demo2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class EmailConfig {

    @Bean
    public SimpleMailMessage getSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("gongwei0318@163.com");
        simpleMailMessage.setSubject("[demo2项目]请您激活账户!");
        return simpleMailMessage;
    }

    @Bean
    public MailSender getMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.163.com");
        sender.setPort(25);
        sender.setUsername("gongwei0318@163.com");
        sender.setPassword("youxiang");
        sender.setDefaultEncoding("utf-8");
        return sender;
    }
}
