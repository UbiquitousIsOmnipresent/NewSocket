package com.Zhou.WebController;

import com.Zhou.Configuration.CodeTable;
import com.Zhou.Configuration.Response;
import com.Zhou.SockerServer.SocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private SocketServer socketServer;

    /**
     *
     * 客户端页面
     * @return
     */
    @RequestMapping(value = "/login")
    public Response login(@RequestParam("name")String name){
        CopyOnWriteArraySet<SocketServer> webSocketSet = SocketServer.getWebSocketSet();
        List<SocketServer> collect = webSocketSet.stream().filter(WebSocketServer -> WebSocketServer.getName().equals(name)).collect(Collectors.toList());
        if(collect.size() > 0){
            return new Response(CodeTable.EXCEPTION,"该昵称已存在");
        }
        return new Response<>(SocketServer.getOnlineCount());
    }

    /**
     *
     * 服务端页面
     * @param model
     * @return
     */
//    @RequestMapping(value = "/admin")
//    public String admin(Model model) {
//        int num = socketServer.getOnlineNum();
//        List<String> list = socketServer.getOnlineUsers();
//
//        model.addAttribute("num",num);
//        model.addAttribute("users",list);
//        return "admin";
//    }

    /**
     * 个人信息推送
     * @return
     */
    @RequestMapping("sendInfo")
    @ResponseBody
    public String sendmsg(String msg, String username) throws IOException {
        //第一个参数 :msg 发送的信息内容
        //第二个参数为用户长连接传的用户人数
        String [] persons = username.split(",");
        socketServer.sendInfo(msg);
        return "success";
    }

    /**
     * 推送给所有在线用户
     * @return
     */
    @RequestMapping("sendMessage")
    @ResponseBody
    public String sendAll(String msg) throws IOException {
        socketServer.sendMessage(msg);
        return "success";
    }
}
