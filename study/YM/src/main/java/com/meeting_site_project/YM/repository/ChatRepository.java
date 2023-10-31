package com.meeting_site_project.YM.repository;

import com.meeting_site_project.YM.mapper.ChatMapper;
import com.meeting_site_project.YM.vo.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Repository
public class ChatRepository {

    ChatMapper chatMapper;

    @Autowired
    public ChatRepository(ChatMapper chatMapper) {
        this.chatMapper = chatMapper;
    }


    public void insertChatRoom(ChatRoom chatRoom) {
        chatMapper.insertChatRoom(chatRoom);
    }

    public List<ChatRoom> selectChatRoomListByUserId(String userId) {
        return chatMapper.selectChatRoomListByUserId(userId);
    }
}
