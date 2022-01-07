package com.ruobai.hichat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruobai.hichat.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @error:
 *  @ServerEndpoint中不能IOC注入
 *  RestTemplate不能发送长数据
 */
@ServerEndpoint("/send")
@Slf4j
@Component
public class WebSocketServiceImpl implements WebSocketService {
    private static int onlineCount=0;
    private static CopyOnWriteArrayList<WebSocketServiceImpl> webSocketSet=new CopyOnWriteArrayList<WebSocketServiceImpl>();
    private Session session;
    private static final String REMOTE_URI = "http://101.37.174.206:8081/cq/sendmsg?msg=";
    /**
     * 解决不能自动注入问题
     */

    private RestTemplate restTemplate = new RestTemplate();

    @OnOpen
    @Override
    public void onOpen(Session session){
        this.session=session;
        webSocketSet.add(this);//加入set中
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为"+getOnlineCount());
    }

    @OnClose
    @Override
    public void onClose(){
        webSocketSet.remove(this);
        subOnlineCount();
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    @OnMessage
    @Override
    public void onMessage(String message) throws UnsupportedEncodingException {
        log.info("来自客户端的消息："+message);
        String result = null;
        JSONObject jsonObject = JSONObject.parseObject(message);
        log.info("来判断"+jsonObject.getJSONObject("data").getJSONObject("to").get("type").equals("friend"));
        if (jsonObject.getJSONObject("data").getJSONObject("to").get("type").equals("friend")){
            //发送给朋友
            String msg = "{\"type\":\""+(String)jsonObject.getJSONObject("data").getJSONObject("to").get("type")+
                    "\",\"botid\":\""+(String)jsonObject.getJSONObject("data").getJSONObject("mine").get("id")+
                    "\",\"fromgroup\":\""+""+
                    "\",\"fromqq\":\""+ (String) jsonObject.getJSONObject("data").getJSONObject("to").get("id")+
                    "\",\"text\":\""+(String)jsonObject.getJSONObject("data").getJSONObject("mine").get("content")+
                    "\"}";
            log.info("msg:"+msg);
            log.info("url:"+REMOTE_URI+msg);
            doGet(REMOTE_URI+URLEncoder.encode(msg,"UTF-8"));
//            msg = URLEncoder.encode(msg,"UTF-8");
//            result = restTemplate.getForObject(REMOTE_URI,String.class,msg);
            log.info(result);
        }else{
            //发给群
            String msg = "{\"type\":\""+(String) jsonObject.getJSONObject("data").getJSONObject("to").get("type")+
                    "\",\"botid\":\""+(String)jsonObject.getJSONObject("data").getJSONObject("mine").get("id")+
                    "\",\"fromgroup\":\""+(String)jsonObject.getJSONObject("data").getJSONObject("to").get("id")+
                    "\",\"fromqq\":\""+""+
                    "\",\"text\":\""+(String)jsonObject.getJSONObject("data").getJSONObject("mine").get("content")+
                    "\"}";
            log.info("msg:"+msg);
            log.info("url:"+REMOTE_URI+msg);
            doGet(REMOTE_URI+URLEncoder.encode(msg,"UTF-8"));
//            result = restTemplate.getForObject(REMOTE_URI + URLEncoder.encode(msg,"UTF-8"),String.class);
            log.info(result);
        }
    }

    @OnError
    @Override
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
        WebSocketServiceImpl.onlineCount++;
    }
    public static synchronized void subOnlineCount(){
        WebSocketServiceImpl.onlineCount--;
    }
    public static String doGet(String url) {
        try {
            HttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                return strResult;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
