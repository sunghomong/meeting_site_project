package com.meeting_site_project.YM.service;


import com.meeting_site_project.YM.repository.ChatRepository;
import com.meeting_site_project.YM.vo.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void insertChatRoom(ChatRoom chatRoom) {
        chatRepository.insertChatRoom(chatRoom);
    }

    public List<ChatRoom> selectChatRoomListByUserId(String userId) {
        return chatRepository.selectChatRoomListByUserId(userId);

    }
}
