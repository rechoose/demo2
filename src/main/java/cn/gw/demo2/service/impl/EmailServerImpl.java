package cn.gw.demo2.service.impl;

import cn.gw.demo2.service.EmailServer;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class EmailServerImpl implements EmailServer {

    @Autowired
    private SimpleMailMessage simpleMailMessage;
    @Autowired
    private MailSender mailSender;

    @Override
    public String sendMail(String msg, List<String> to) {

        try {
            simpleMailMessage.setText(msg);
            simpleMailMessage.setTo(to.toArray(new String[to.size()]));
            mailSender.send(simpleMailMessage);
            return "ok";
        } catch (MailException e) {
            return "fail: " + e.getMessage();
        }
    }

}
