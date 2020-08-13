package com.Zhou.WebController;

import com.Zhou.Configuration.CodeTable;
import com.Zhou.SockerServer.SocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.Zhou.Configuration.Response;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/login")
//@Controller
public class WebController {

    @PostMapping("/login")
    public Response login(@RequestParam("name")String name){
        CopyOnWriteArraySet<SocketServer> webSocketSet = SocketServer.getWebSocketSet();
        List<SocketServer> collect = webSocketSet.stream().filter(WebSocketServer -> WebSocketServer.getName().equals(name)).collect(Collectors.toList());
        if(collect.size() > 0){
            return new Response(CodeTable.EXCEPTION,"该昵称已存在");
        }
        return new Response<>(SocketServer.getOnlineCount());
    }


}
