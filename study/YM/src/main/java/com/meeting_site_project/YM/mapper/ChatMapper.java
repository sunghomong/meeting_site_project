package com.meeting_site_project.YM.mapper;

import com.meeting_site_project.YM.vo.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ChatMapper {

    public void insertChatRoom(ChatRoom chatRoom);

    public List<ChatRoom> selectChatRoomListByUserId(String userId);
}
