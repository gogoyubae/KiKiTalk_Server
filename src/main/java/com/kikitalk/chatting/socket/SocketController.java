package com.kikitalk.chatting.socket;

import org.springframework.stereotype.Controller;
import com.kikitalk.chatting.socket.SocketVo;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    @MessageMapping("/receive") //메시지를 받을 엔드포인트
    @SendTo("/send") //send로 메시지를 반환함

    public SocketVo SocketHandler(SocketVo socketVo){
        String username = socketVo.getUsername();
        String content = socketVo.getContent();

        SocketVo newMessage = new SocketVo(username, content);
        return newMessage;
    }
}
