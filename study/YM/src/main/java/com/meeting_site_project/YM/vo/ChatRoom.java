package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    private String chatRoomId;  // 채팅방의 고유 ID
    private String groupId;     // 그룹 정보의 ID (외래키)
    private String ownerId;     // 채팅방의 소유자 (방장) ID (외래키)
    private String chatRoomName; // 채팅방 이름
    private int maxUserCnt;     // 채팅방의 최대 사용자 수 제한
    private int userCount;      // 현재 채팅방에 있는 사용자 수
}
