package com.ruobai.hichat.service;


import javax.websocket.Session;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface WebSocketService {
    //连接建立
    public void onOpen(Session session);
    //连接关闭
    public void onClose();
    public void onMessage(String message) throws UnsupportedEncodingException;
    public void onError(Session session,Throwable throwable);
    public void sendMessage(String message) throws IOException;
    //连接错误
    //发送信息
}
