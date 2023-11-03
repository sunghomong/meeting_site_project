package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.ChatService;
import com.meeting_site_project.YM.service.MeetingService;
import com.meeting_site_project.YM.vo.AuthInfo;
import com.meeting_site_project.YM.vo.ChatMessage;
import com.meeting_site_project.YM.vo.ChatRoom;
import com.meeting_site_project.YM.vo.ChatRoomMembers;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    ChatService chatService;

    MeetingService meetingService;

    @Autowired
    public ChatController(ChatService chatService, MeetingService meetingService) {
        this.chatService = chatService;
        this.meetingService = meetingService;
    }



    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    // 사용자 등록 요청 처리
    @MessageMapping("/chat.register") //"/app/chat.register" 사용자가 처음 들어왔을때만 동작되는 메서드
    public void  register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {

        // 새로운 사용자의 세션 정보에 사용자 이름 저장
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        System.out.println("chatMessage.getSender() = " + chatMessage.getSender());

        chatService.insertChatMessage(chatMessage); // 메시지 저장

        // 채팅방 아이디를 추출하고 해당 아이디로 메시지를 전달
        String chatRoomId = chatMessage.getChatRoomId();
        simpMessagingTemplate.convertAndSend("/topic/chatRoom/" + chatRoomId, chatMessage);
    }

    // 채팅 메시지 전송 요청 처리
//    @SendTo("/topic/public/{chatRoomId}")    // /topic/public 으로 메시지 전달
    @MessageMapping("/chat.send") //"/app/chat.send"
    public void sendMessage(@Payload ChatMessage chatMessage) {
        // 채팅방 아이디를 추출하고 해당 아이디로 메시지를 전달
        String chatRoomId = chatMessage.getChatRoomId();

        // 보냈을때 날짜 비교후 데이터 전송
        // 바로 이전 메시지 가져오기
        ChatMessage previousMessage = chatService.isChatMessageDateByChatMessage(chatMessage);

        if (previousMessage != null) { // 바로 이전 메시지가 있다면 현재 메시지와 비교

            // 이전 메시지가 있으면 날짜를 비교
            Date previousDate = previousMessage.getMessageTime();
            Date currentDate = chatMessage.getMessageTime();

            // 이전 메시지와 현재 메시지의 날짜가 다른지 확인
            if (!areDatesEqual(previousDate, currentDate)) {
                // 날짜가 다를 경우, 새로운 데이터 저장 및 추출
                ChatMessage newChatMessage = new ChatMessage();

                // 현재 메시지의 날짜를 가져와서 시간만 0시 0분 0초로 설정
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(chatMessage.getMessageTime()); // 년월일
                calendar.set(Calendar.HOUR_OF_DAY, 0); // 0시
                calendar.set(Calendar.MINUTE, 0); // 0분
                calendar.set(Calendar.SECOND, 0); // 0초

                newChatMessage.setMessageTime(calendar.getTime());

                // 현재 메시지에서 년, 월, 일을 추출하여 "00년 00월 00일" 문자열 생성
                SimpleDateFormat dateFormat = new SimpleDateFormat("yy년 MM월 dd일");
                String dateStr = dateFormat.format(chatMessage.getMessageTime());
                System.out.println("dateStr = " + dateStr);
                newChatMessage.setContent(dateStr);

                // UUID를 사용하여 고유한 messageId 생성
                String uniqueMessageId = UUID.randomUUID().toString();

                newChatMessage.setMessageId(uniqueMessageId);
                newChatMessage.setChatRoomId(chatRoomId);
                newChatMessage.setType(ChatMessage.MessageType.valueOf("DATE"));

                // 방장 userId 값 가져오기
                String userId = chatService.selectChatOwnerIdByChatRoomId(chatRoomId);

                newChatMessage.setUserId(userId);

                chatService.insertChatDateMessageByChatMessage(chatMessage);

                simpMessagingTemplate.convertAndSend("/topic/chatRoom/" + chatRoomId, newChatMessage);
            }
        }

        chatService.insertChatMessage(chatMessage); // 데이터 저장

        simpMessagingTemplate.convertAndSend("/topic/chatRoom/" + chatRoomId, chatMessage);
    }

    // 날짜 형식을 변경하는 메서드
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 (E)");
        return dateFormat.format(date);
    }

    // 날짜 비교 메서드
    private boolean areDatesEqual(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    @MessageMapping("/chat.exit") //"/app/chat.exit"
    public void exitChatMember(@Payload ChatMessage chatMessage) { // 회원이 나가기 버튼 클릭시

        // 채팅방 아이디를 추출하고 해당 아이디로 메시지를 전달
        String chatRoomId = chatMessage.getChatRoomId();

        chatService.insertChatMessage(chatMessage); // 데이터 저장

        simpMessagingTemplate.convertAndSend("/topic/chatRoom/" + chatRoomId, chatMessage);

        ChatRoom chatRoom =chatService.selectChatRoomInfoByRoomId(chatRoomId);

        // 만약 나간 사람이 채팅방장일 경우 채팅방 삭제,모임 페이지 삭제
        String userId = chatMessage.getUserId();

        if(userId.equals(chatRoom.getOwnerId())) {
            meetingService.deleteGroup(chatRoom.getGroupId());
        }

        // 회원 정보 삭제
        chatService.deleteChatMemeberByChatMessage(chatMessage);
        // 채팅방 회원 수 -1
        chatService.subtractChatMemberCntByRoomId(chatRoomId);

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

        // 기존 사용자 판별해서 보내주기 (chatMessage가 그 테이블에 존재한다면 결과값 null 로 전달)
        // 1. chatMessage조회 (chatRoomId와 userId를 갖고서)
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChatRoomId(chatRoomId);
        chatMessage.setUserId(authInfo.getUserId());
        boolean isChatMessage = chatService.selectChatMessageByUserIdWithChatRoomId(chatMessage); // 실제로 그 채팅방에 message가 존재한다면 true,없다면 false
        // 체크를 한번 더 하는 이유는 나갔을 경우 데이터를 불러오는데 전 데이터도 불러와질수 있을 경우가 있기 때문이다.
        boolean isChatRoomMember = chatService.checkIsChatRoomMemberByUserIdAndChatRoomId(authInfo.getUserId(),chatRoomId); // 그 채팅방 회원인지 확인

        boolean finallyResult = false;
        if(isChatMessage && isChatRoomMember) { // 회원이 전에 들어온적이 있는지 판단
            finallyResult = true;
        }

        if(!isChatRoomMember) {
            // 1. 채팅방 회원 수 업데이트
            chatService.insertChatMemberCntByRoomId(chatRoomId); // 채팅방 멤버 1 추가
            chatService.insertChatRoomMember(authInfo.getUserId(),chatRoomId,authInfo.getNickName()); // 채팅방 멤버 추가
        }

        ChatRoom chatRoom = chatService.selectChatRoomInfoByRoomId(chatRoomId); // 채팅방 정보 불러오기

        model.addAttribute("userWasInChatRoom", finallyResult); // 회원이 전에 들어온적이 있는지 판단
        model.addAttribute("chatRoom",chatRoom);
        model.addAttribute("userName",authInfo.getUserName());
        model.addAttribute("userId",authInfo.getUserId());
    }

    @GetMapping("/chat/getLastChatMessages/{chatRoomId}") // 채팅방의 이전 메시지 데이터 가져오기 연결 성공
    public ResponseEntity<List<ChatMessage>> getLastChatMessages(@PathVariable("chatRoomId") String chatRoomId, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("loginMember");
        // 1. ChatRoomMembers 의 들어간 날짜 조회 후 그 날짜를 쿼리에서 비교 해서 그 시간 이후의 메서지들 추출해서 데이터 전송
        Date innerDate = chatService.selectChatMembersByRoomIdWithUserId(chatRoomId,authInfo.getUserId()); //들어간 날짜 조회

        List<ChatMessage> chatMessages = chatService.selectChatMessageAfterInnerDateByChatRoomId(innerDate,chatRoomId);


        if (chatMessages != null) { // 이전에 채팅 내용이 있다면 전달
            return new ResponseEntity<>(chatMessages, HttpStatus.OK);
        } else { // 이전에 채팅 내용이 없다면 넘김
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/chat/manage")
    public String moveGroupManagerPage (@RequestParam("groupId") String groupId) {



        return "";
    }

    @GetMapping("/chatMemberEdit")
    public String moveChatMemberEditPage(@RequestParam("userId") String userId,
                                         @RequestParam("groupId") String groupId) {

        ChatRoomMembers chatRoomMembers = chatService.selectChatMemberByUserIdWithGroupId(userId,groupId);



        return "";
    }
}
