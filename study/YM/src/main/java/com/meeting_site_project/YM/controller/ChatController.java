package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.ChatService;
import com.meeting_site_project.YM.vo.AuthInfo;
import com.meeting_site_project.YM.vo.ChatMessage;
import com.meeting_site_project.YM.vo.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    ChatService chatService;

    @Autowired
    public ChatController( ChatService chatService) {
        this.chatService = chatService;
    }



    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    // 사용자 등록 요청 처리
    @MessageMapping("/chat.register") //"/app/chat.register"
    public void  register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {

        // 새로운 사용자의 세션 정보에 사용자 이름 저장
        System.out.println("chatMessage = " + chatMessage);
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        chatService.insertChatMessage(chatMessage);

        // 채팅방 아이디를 추출하고 해당 아이디로 메시지를 전달
        String chatRoomId = chatMessage.getChatRoomId();
        System.out.println("chatRoomId = " + chatRoomId);
        simpMessagingTemplate.convertAndSend("/topic/chatRoom/" + chatRoomId, chatMessage);
    }

    // 채팅 메시지 전송 요청 처리
//    @SendTo("/topic/public/{chatRoomId}")    // /topic/public 으로 메시지 전달
    @MessageMapping("/chat.send") //"/app/chat.send"
    public void sendMessage(@Payload ChatMessage chatMessage) {
        System.out.println("chatMessage = " + chatMessage);

        // 채팅방 아이디를 추출하고 해당 아이디로 메시지를 전달
        String chatRoomId = chatMessage.getChatRoomId();
        System.out.println("chatRoomId = " + chatRoomId);

        chatService.insertChatMessage(chatMessage); // 데이터 저장

        simpMessagingTemplate.convertAndSend("/topic/chatRoom/" + chatRoomId, chatMessage);
    }



    // 채팅 폼을 보여주는 페이지로 이동
    @GetMapping("/chat")
    public String chatForm() {
        return "/chat"; // "/chat" 페이지로 이동
    }

    @GetMapping("/chat/chatRoomList")
    public String createChatRoom(@RequestParam("userId") String userId, Model model, HttpSession session) {

        List<ChatRoom> chatRoomList = chatService.selectChatRoomListByUserId(userId);

        model.addAttribute("chatRoomList", chatRoomList);

        return "/chat/chatRoomList";
    }

    @GetMapping("/chat/chatRoom") // 채팅방으로 들어가는 로직 구현
    public void showChatForm(@RequestParam("chatRoomId") String chatRoomId,HttpSession session,Model model) {

        AuthInfo authInfo = (AuthInfo) session.getAttribute("loginMember");

        ChatRoom chatRoom = chatService.selectChatRoomInfoByRoomId(chatRoomId);
        System.out.println("authInfo = " + authInfo);
        model.addAttribute("chatRoom",chatRoom);
        model.addAttribute("userName",authInfo.getUserName());
        model.addAttribute("userId",authInfo.getUserId());
    }

    @GetMapping("/chat/getLastChatMessages/{chatRoomId}") // 채팅방의 이전 메시지 데이터 가져오기
    public ResponseEntity<List<ChatMessage>> getLastChatMessages(@PathVariable("chatRoomId") String chatRoomId,@Payload ChatMessage chatMessage) {
        List<ChatMessage> chatMessages = chatService.getLastChatMessagesByRoomId(chatRoomId);

        if (chatMessages != null) {
            return new ResponseEntity<>(chatMessages, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
