package cn.gw.demo2.service;

public interface MessageSendService {

    void sendMessage(String msgBody, String exchange, int timeOut);

}
