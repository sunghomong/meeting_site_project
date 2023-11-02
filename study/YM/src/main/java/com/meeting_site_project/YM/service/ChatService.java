package com.meeting_site_project.YM.service;


import com.meeting_site_project.YM.repository.ChatRepository;
import com.meeting_site_project.YM.vo.ChatMessage;
import com.meeting_site_project.YM.vo.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        List<String> chatRoomIdList = chatRepository.selectChatRoomIdListByUserId(userId); // 회원 아이디가 갖고 있는 chatRoomId 리스트 출력해서 아이디들을 통한 룸 리스트 조회

        List<ChatRoom> chatRoomList = new ArrayList<>(); // 추출한 룸 아이디 리스트를 통해 채팅방 리스트를 추출

        for (String chatRoomId : chatRoomIdList) {
            ChatRoom chatRoom = chatRepository.selectChatRoomByChatRoomId(chatRoomId); // chatRepository에서 해당 아이디에 대한 채팅 룸 정보를 가져옴
            if (chatRoom != null) {
                chatRoomList.add(chatRoom);
            }
        }

        return chatRoomList;

    }

    public int selectChatRoomCntByUserId(String userId) {
        return chatRepository.selectChatRoomCntByUserId(userId);
    }

    public ChatRoom selectChatRoomInfoByRoomId(String roomId) {
        return  chatRepository.selectChatRoomInfoByRoomId(roomId);
    }

    public void insertChatMessage(ChatMessage chatMessage) {
        chatRepository.insertChatMessage(chatMessage);
    }


    public List<ChatMessage> getLastChatMessagesByRoomId(String chatRoomId) {
        return chatRepository.getLastChatMessagesByRoomId(chatRoomId);
    }

    public void insertChatRoomOwnerMember(ChatRoom chatRoom) {
        chatRepository.insertChatRoomOwnerMember(chatRoom);
    }
}
