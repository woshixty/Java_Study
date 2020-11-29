package com.sailing.websocket.controller;

import com.sailing.websocket.common.WebSocketServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/22
 * websocket测试controller
 **/

@RestController
public class SocketController {
    @Resource
    private WebSocketServer webSocketServer;

    /**
     * 给指定用户推送消息
     * @param userName 用户名
     * @param message 消息
     * @throws IOException
     */
    @RequestMapping(value = "/socket", method = RequestMethod.GET)
    public void testSocket1(@RequestParam String userName, @RequestParam String message){
        webSocketServer.sendInfo(userName, message);
    }

    /**
     * 给所有用户推送消息
     * @param message 消息
     * @throws IOException
     */
    @RequestMapping(value = "/socket/all", method = RequestMethod.GET)
    public void testSocket2(@RequestParam String message){
        try {
            webSocketServer.onMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
