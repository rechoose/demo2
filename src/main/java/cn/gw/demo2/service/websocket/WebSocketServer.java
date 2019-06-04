package cn.gw.demo2.service.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * @author
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */

@ServerEndpoint("/ws/topic/infosysofpark")
@Component
public class WebSocketServer {
    private static Logger logger = LoggerFactory.getLogger(WebSocketServer.class);


    //用来存放每个客户端对应的WebSocketTest对象，适用于同时与多个客户端通信
    public static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    //若要实现服务端与指定客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    public static ConcurrentHashMap<Session, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，通过它实现定向推送(只推送给某个用户)
    private Session session;


    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        webSocketMap.put(session, this); //加入map中
        logger.info("有新窗口开始监听:" + session.getId() + ",当前在线人数为" + webSocketSet.size());
        try {
            sendMessage(session, "连接成功");
        } catch (Exception e) {
            logger.error("websocket IO异常,消息:{}", e.getMessage());
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session closeSession) {
        webSocketSet.remove(this); //从set中删除
        webSocketMap.remove(closeSession); //从map中删除
        logger.info("有一连接关闭！" + closeSession.getId() + "当前在线人数为" + webSocketSet.size());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message           客户端发送过来的消息
     * @param sessionFromClient 可选的参数
     * @throws Exception
     */
    @OnMessage
    public void onMessage(String message, Session sessionFromClient) {

        logger.info("来自客户端" + sessionFromClient.getId() + "的消息:" + message);
        //推送给单个客户端
        if (webSocketMap.isEmpty()) {
            logger.info("websocket链接为空");
            return;
        }
        for (Session session : webSocketMap.keySet()) {
            if (session.equals(sessionFromClient)) {
                WebSocketServer item = (WebSocketServer) webSocketMap.get(sessionFromClient);
                String msg = "嗨，这是返回的信息";
                try {
                    item.sendMessage(sessionFromClient, msg);
                } catch (Exception e) {
                    logger.error("websocket发送消息异常,e:{}", e.getMessage());
                }
                return;
            }
        }
    }


    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("websocket发生错误session:{},异常:{}", session.getId(), error.getMessage());
    }


    //给所有客户端发送信息
    public void sendAllMessage(String message) throws IOException {
        if (webSocketMap.isEmpty()) {
            logger.info("websocket链接为空");
            return;
        }
        for (Session session : webSocketMap.keySet()) {
            WebSocketServer item = (WebSocketServer) webSocketMap.get(session);
            item.sendMessage(session, message);
        }
    }

    //定向发送信息
    public void sendMessage(Session toSession, String message) throws IOException {
        synchronized (this) {
            if (toSession.isOpen()) {//该session如果已被删除，则不执行发送请求，防止报错
                toSession.getBasicRemote().sendText(message);
                logger.info("websocket发送消息成功{},message:{}", toSession.getId(), message);
            } else {
                logger.error("链接已断开");
            }
        }
    }

}
