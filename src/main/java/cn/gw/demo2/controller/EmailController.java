package cn.gw.demo2.controller;

import cn.gw.demo2.interceptor.LogConsumedTime;
import cn.gw.demo2.service.EmailServer;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/email")
@Api("邮件")
public class EmailController {

    @Autowired
    private EmailServer emailServer;


    @LogConsumedTime
    @PostMapping("/send")
    public String send(@RequestBody List<String> to, @RequestParam String msg) {
        return emailServer.sendMail(msg, to);
    }

    @LogConsumedTime
    @PostMapping("/save")
    public String save(@RequestBody List<String> to, @RequestParam String msg) {
        return emailServer.sendMail(msg, to);
    }
}
