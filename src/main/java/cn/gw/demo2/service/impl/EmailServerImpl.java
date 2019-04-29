package cn.gw.demo2.service.impl;

import cn.gw.demo2.service.EmailServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailServerImpl implements EmailServer {

    @Autowired
    private SimpleMailMessage simpleMailMessage;
    @Autowired
    private MailSender mailSender;

    @Override
    public String sendMail(String msg, String... to) {

        try {
            simpleMailMessage.setText(msg);
            simpleMailMessage.setTo(to);
            mailSender.send(simpleMailMessage);
            return "ok";
        } catch (MailException e) {
            return "fail: " + e.getMessage();
        }
    }

}
