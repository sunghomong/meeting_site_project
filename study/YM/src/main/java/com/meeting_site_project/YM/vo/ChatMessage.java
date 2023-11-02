package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date messageTime;

    public enum MessageType {
        CHAT, LEAVE, JOIN
    }
}