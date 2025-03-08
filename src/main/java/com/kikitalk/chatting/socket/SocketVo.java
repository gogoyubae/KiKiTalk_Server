package com.kikitalk.chatting.socket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public class SocketVo {
    private String username; //유저 이름 저장
    private  String content; //메시니 내용 저장
}
