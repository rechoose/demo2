package cn.gw.demo2.service;

import java.util.List;

public interface EmailServer {
    String sendMail(String msg, List<String> to);
}
