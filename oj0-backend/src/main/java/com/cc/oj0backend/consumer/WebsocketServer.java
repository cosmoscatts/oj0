package com.cc.oj0backend.consumer;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/")
public class WebsocketServer {
    /**
     * 用户和 websocket server 的映射
     */
    public final static ConcurrentHashMap<Integer, WebsocketServer> users = new ConcurrentHashMap<>();
    private Session session;

    // Spring 单例与 Websocket 冲突
    // 这里使用 setter 进行注入

    /**
     * 建立连接
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
    }

    /**
     * 关闭链接
     */
    @OnClose
    public void onClose() {
//        if (!Objects.isNull(user)) {
//            users.remove(user.getId());
//        }
    }


    /**
     * 从 Client 接收消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {

    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 给 Client 发送消息
     */
    public void sendMessage(String message) {
        synchronized (session) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
