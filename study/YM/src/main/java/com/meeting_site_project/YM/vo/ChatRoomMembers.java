package com.meeting_site_project.YM.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomMembers {

    private String roomUserId;
    private String userId;
    private String chatRoomId;
    private int admin;
}
