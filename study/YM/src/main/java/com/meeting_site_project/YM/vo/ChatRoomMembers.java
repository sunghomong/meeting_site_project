package com.meeting_site_project.YM.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomMembers {

    private String roomUserId;
    private String userId; // 고유 값
    private String chatRoomId; // 고유 값
    private int admin; // 1(채팅 방 방장) 0(채팅방 일반 회원) -1(채팅방 추방 당한 사람)
    @DateTimeFormat(pattern = "yyyy년-MM월-dd일'T'HH시-MM분-SS초")
    private Date chatInnerTime; // 들어온 시간
    private String nickName;
//    @DateTimeFormat(pattern = "yyyy년-MM월-dd일'T'HH시-MM분-SS초")
//    private Date chatOutTime; // 나간 시간 등록 제외? : 회원이 채팅방을 실수로 나가서 들어올 경우 데이터 초기화를 위해 아예 삭제
}
