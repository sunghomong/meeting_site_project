package com.meeting_site_project.YM.repository;

import com.meeting_site_project.YM.mapper.ChatMapper;
import com.meeting_site_project.YM.vo.ChatMessage;
import com.meeting_site_project.YM.vo.ChatRoom;
import com.meeting_site_project.YM.vo.ChatRoomMembers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

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

    public int selectChatRoomCntByUserId(String userId) {
        return chatMapper.selectChatRoomCntByUserId(userId);
    }

    public ChatRoom selectChatRoomInfoByRoomId(String roomId) {
        return chatMapper.selectChatRoomInfoByRoomId(roomId);

    }

    public void insertChatMessage(ChatMessage chatMessage) {

        // UUID를 사용하여 고유한 askId 생성
        String uniqueMessageId = UUID.randomUUID().toString();

        chatMessage.setMessageId(uniqueMessageId);

        chatMapper.insertChatMessage(chatMessage);
    }


    public List<ChatMessage> getLastChatMessagesByRoomId(String chatRoomId) {
        return chatMapper.getLastChatMessagesByRoomId(chatRoomId);
    }

    public void insertChatRoomOwnerMember(ChatRoom chatRoom) {
        ChatRoomMembers chatRoomMembers = new ChatRoomMembers();

        // UUID를 사용하여 고유한 chatRoomMembersId 생성
        String uniqueRoomUserId = UUID.randomUUID().toString();

        chatRoomMembers.setRoomUserId(uniqueRoomUserId); // 고유값
        chatRoomMembers.setChatRoomId(chatRoom.getChatRoomId()); // 채팅방 ID
        chatRoomMembers.setUserId(chatRoom.getOwnerId()); // 채팅방장 ID
        chatRoomMembers.setAdmin(1); // 1 = 채팅방 방장

        chatMapper.insertChatRoomMembers(chatRoomMembers);
    }

    public List<String> selectChatRoomIdListByUserId(String userId) {

        return chatMapper.selectChatRoomIdListByUserId(userId);
    }

    public ChatRoom selectChatRoomByChatRoomId(String chatRoomId) {
        return chatMapper.selectChatRoomByChatRoomId(chatRoomId);
    }
}
