package com.optical.common;

import com.alibaba.fastjson.JSON;
import com.optical.bean.WebSocketMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by mary on 2021/3/24.
 */
@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketService {

//    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);
//
//
//    //记录当前在线人数
//    private static int onlineCount = 0;
//    //记录累计登陆量
//    private static int visitCount = 0;
//
//
//    //线程安全set，保存当前每个登录用户的webSocketService 对象
//    private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<WebSocketService>();
//
//    //与某个客户端的连接会话，需要通过它来给客户端发送数据
//    private Session session;
//
//    //接收sid
//    private String sid="";
//
//
//    @OnOpen
//    public void onOpen(Session session,@PathParam("sid") String sid) {
//        this.session = session;
//        webSocketSet.add(this);     //加入set中
//        addOnlineCount();           //在线数加1
//        log.info("有新窗口开始监听:"+sid+",当前在线人数为" + getOnlineCount());
//        this.sid=sid;
//        try {
//            WebSocketMsg msg = new WebSocketMsg();
//            Map map = new HashMap();
//            map.put("msgType", 0);
//            map.put("text", "连接成功");
//            msg.setData(map);
//            msg.setShowWindow(0);
//            msg.setMsgType(0);
//            sendMessage(JSON.toJSONString(msg));
//        } catch (Exception e) {
//            log.error("websocket IO异常");
//        }
//    }
//
//
//    /**
//     * 连接关闭调用的方法
//     */
//    @OnClose
//    public void onClose() {
//        webSocketSet.remove(this);  //从set中删除
//        subOnlineCount();           //在线数减1
//        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
//    }
//
//    /**
//     * 收到客户端消息后调用的方法
//     *
//     * @param message 客户端发送过来的消息*/
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        log.info("收到来自窗口"+sid+"的信息:"+message);
//        //群发消息
////        for (WebSocketService item : webSocketSet) {
////            try {
////                item.sendMessage(JSON.toJSONString(message));
////                if(item.sid.equals(sid)){
////                    Map map = new HashMap();
////                    map.put("msgType", 0);
////                    map.put("text", message);
////
////                }
////            } catch (Exception e) {
////                log.error("websocket service Error! e = {}", e);
////            }
////        }
//    }
//
//    /**
//     *
//     * @param session
//     * @param error
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        log.error("发生错误! Exception! error! e={}", error);
//        log.error("相应session: {}", JSON.toJSONString(session));
//    }
//
//    /**
//     * 群发自定义消息
//     * */
//    public static void sendInfo(String message,@PathParam("sid") String sid) throws Exception {
//        log.info("推送消息到窗口"+sid+"，推送内容:"+message);
//        for (WebSocketService item : webSocketSet) {
//            try {
//                //这里可以设定只推送给这个sid的，为null则全部推送
//                if(sid==null) {
//                    item.sendMessage(message);
//                }else if(item.sid.equals(sid)){
//                    item.sendMessage(message);
//                }
//            } catch (Exception e) {
//                log.error("websocketService sendInfo exception! e={}", e);
//                continue;
//            }
//        }
//    }
//
//
//    /**
//     * 实现服务器主动推送
//     */
//    public void sendMessage(String message) throws Exception {
//        this.session.getBasicRemote().sendText(message);
//    }
//
//    public static synchronized int getOnlineCount() {
//        return onlineCount;
//    }
//
//    public static synchronized int getVisitCount() {
//        return visitCount;
//    }
//
//
//    public static synchronized void addOnlineCount() {
//        WebSocketService.onlineCount++;
//        WebSocketService.visitCount++;
//    }
//
//    public static synchronized void subOnlineCount() {
//        WebSocketService.onlineCount--;
//    }
//

}
