package com.ruobai.hichat.server;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

//@ServerEndpoint("/send")
//@Component
public class WebSocketTest {
    private static int onlineCount=0;
    private static CopyOnWriteArrayList<WebSocketTest> webSocketSet=new CopyOnWriteArrayList<WebSocketTest>();
    private Session session;

    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        webSocketSet.add(this);//加入set中
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为"+getOnlineCount());
    }

    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        subOnlineCount();
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message,Session session){
        System.out.println("来自客户端的消息："+message);
//        群发消息
        for (WebSocketTest item:webSocketSet){
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    @OnError
    public void onError(Session session,Throwable throwable){
        System.out.println("发生错误！");
        throwable.printStackTrace();
    }
    //   下面是自定义的一些方法
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount(){
        return onlineCount;
    }
    public static synchronized void addOnlineCount(){
        WebSocketTest.onlineCount++;
    }
    public static synchronized void subOnlineCount(){
        WebSocketTest.onlineCount--;
    }

}
