package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.ChatService;
import com.meeting_site_project.YM.vo.ChatMessage;
import com.meeting_site_project.YM.vo.ChatRoom;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ChatController {

    ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // 사용자 등록 요청 처리
    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // 새로운 사용자의 세션 정보에 사용자 이름 저장
        System.out.println("chatMessage = " + chatMessage);
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage; // 등록 메시지를 클라이언트에게 다시 전송
    }

    // 채팅 메시지 전송 요청 처리
    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        System.out.println("chatMessage = " + chatMessage);
        return chatMessage; // 수신한 채팅 메시지를 클라이언트에게 다시 전송
    }

    // 채팅 폼을 보여주는 페이지로 이동
    @GetMapping("/chat")
    public String chatForm() {
        return "/chat"; // "/chat" 페이지로 이동
    }

    @GetMapping("/chat/chatRoomList")
    public String createChatRoom(@RequestParam("userId") String userId) {

        List<ChatRoom> chatRoomList = chatService.selectChatRoomListByUserId(userId);

        return "";
    }
}
