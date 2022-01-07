package com.ruobai.hichat.controller;


import com.ruobai.hichat.service.ChatService;
import com.ruobai.hichat.vo.ChatHomeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.Session;

@RestController
@Slf4j
public class ChatController {
    //获取好友列表和群列表
    @Autowired
    private ChatService chatService;
    //通信地址
    private static final String REMOTE_WS = "ws://101.37.174.206:8081/myWs";
    //通信连接建立
    @GetMapping("/getChatHome")
    public ChatHomeVO getChatHome(String id){
        //建立一个WebSocket通信
        return chatService.getData(id);
    }
}
