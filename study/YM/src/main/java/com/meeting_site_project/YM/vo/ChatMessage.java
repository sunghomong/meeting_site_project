package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    private String messageId;
    private String chatRoomId; // 고유 값
    private String userId;
    private String content;
    private String sender;
    private MessageType type;
    // "yyyy년 MM월 dd일 (E) a hh:mm" 형식으로 날짜와 시간 표시
    @DateTimeFormat(pattern = "yyyy년 MM월 dd일 (E) a hh:mm")
    private Date messageTime;

    public enum MessageType {
        CHAT, LEAVE, JOIN
    }


}