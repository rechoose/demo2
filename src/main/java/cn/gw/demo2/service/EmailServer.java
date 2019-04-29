package cn.gw.demo2.service;

public interface EmailServer {
    String sendMail(String msg, String... to);
}
