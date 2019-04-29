package cn.gw.demo2.controller;

import cn.gw.demo2.service.EmailServer;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@Api("邮件")
public class EmailController {

    @Autowired
    private EmailServer emailServer;


    @GetMapping("/send")
    public String send(@RequestParam String msg, @RequestParam String... to) {
        return emailServer.sendMail(msg, to);
    }
}
