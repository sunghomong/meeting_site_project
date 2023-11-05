package com.meeting_site_project.YM.repository;

import com.meeting_site_project.YM.mapper.ChatMapper;
import com.meeting_site_project.YM.vo.ChatMessage;
import com.meeting_site_project.YM.vo.ChatRoom;
import com.meeting_site_project.YM.vo.ChatRoomMembers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
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

    public ChatRoom selectChatRoomInfoByRoomId(String roomId) {
        return chatMapper.selectChatRoomInfoByRoomId(roomId);

    }

    public void insertChatMessage(ChatMessage chatMessage) {

        // UUID를 사용하여 고유한 askId 생성
        String uniqueMessageId = UUID.randomUUID().toString();

        chatMessage.setMessageId(uniqueMessageId);

        chatMapper.insertChatMessage(chatMessage);
    }


    public void insertChatRoomOwnerMember(ChatRoom chatRoom, String nickName) {
        ChatRoomMembers chatRoomMembers = new ChatRoomMembers();

        // UUID를 사용하여 고유한 chatRoomMembersId 생성
        String uniqueRoomUserId = UUID.randomUUID().toString();

        chatRoomMembers.setNickName(nickName);
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

    public boolean selectChatMessageByUserIdWithChatRoomId(ChatMessage chatMessage) {
        return chatMapper.selectChatMessageByUserIdWithChatRoomId(chatMessage);
    }

    public Date selectChatMembersByRoomIdWithUserId(ChatRoomMembers chatRoomMembers) {
        return chatMapper.selectChatMembersByRoomIdWithUserId(chatRoomMembers);
    }


    public List<ChatMessage> selectChatMessageAfterInnerDateByChatRoomId(ChatRoomMembers chatRoomMembers) {
        return chatMapper.selectChatMessageAfterInnerDateByChatRoomId(chatRoomMembers);
    }

    public String selectChatRoomIdByGroupId(String groupId) {
        return chatMapper.selectChatRoomIdByGroupId(groupId);
    }

    public int selectChatMemberCntByChatRoomId(String chatRoomId) {
        return chatMapper.selectChatMemberCntByChatRoomId(chatRoomId);
    }

    public void insertChatMemberCntByRoomId(String chatRoomId) {
        chatMapper.insertChatMemberCntByRoomId(chatRoomId);
    }

    public void insertChatRoomMember(String userId, String chatRoomId, String nickName) {
        ChatRoomMembers chatRoomMembers = new ChatRoomMembers();

        // UUID를 사용하여 고유한 chatRoomMembersId 생성
        String uniqueRoomUserId = UUID.randomUUID().toString();

        chatRoomMembers.setNickName(nickName);
        chatRoomMembers.setRoomUserId(uniqueRoomUserId); // 고유값
        chatRoomMembers.setChatRoomId(chatRoomId); // 채팅방 ID
        chatRoomMembers.setUserId(userId); // 채팅방장 ID
        chatRoomMembers.setAdmin(0); // 0 = 채팅방 일반

        chatMapper.insertChatRoomMembers(chatRoomMembers);
    }

    public boolean checkIsChatRoomMemberByUserIdAndChatRoomId(String userId, String chatRoomId) {
        ChatRoomMembers chatRoomMembers = new ChatRoomMembers();

        chatRoomMembers.setUserId(userId);
        chatRoomMembers.setChatRoomId(chatRoomId);

        return chatMapper.checkIsChatRoomMemberByUserIdAndChatRoomId(chatRoomMembers);
    }

    public ChatRoom selectChatRoomInfoByGroupId(String groupId) {
        return chatMapper.selectChatRoomInfoByGroupId(groupId);
    }

    public void deleteChatMemeberByChatMessage(ChatMessage chatMessage) {
        chatMapper.deleteChatMemeberByChatMessage(chatMessage);
    }

    public void subtractChatMemberCntByRoomId(String chatRoomId) {
        chatMapper.subtractChatMemberCntByRoomId(chatRoomId);
    }

    public List<ChatMessage> selectChatMessageListByChatRoomId(String chatRoomId) {
        return chatMapper.selectChatMessageListByChatRoomId(chatRoomId);
    }

    public void insertChatDateMessageByChatMessage(ChatMessage chatMessage) {
        chatMapper.insertChatDateMessageByChatMessage(chatMessage);
    }

    public String selectChatOwnerIdByChatRoomId(String chatRoomId) {
        return chatMapper.selectChatOwnerIdByChatRoomId(chatRoomId);
    }

    public List<ChatRoomMembers> selectChatRoomMemberListByChatRoomId(String chatRoomId) {
        return chatMapper.selectChatRoomMemberListByChatRoomId(chatRoomId);
    }


    public void updateChatRoomMemberAdminByRoomUserId(String roomUserId, int admin) {
        chatMapper.updateChatRoomMemberAdminByRoomUserId(roomUserId,admin);
    }

    public String selectGroupIdWhereChatRoomByChatRoomId(String chatRoomId) {
        return chatMapper.selectGroupIdWhereChatRoomByChatRoomId(chatRoomId);
    }

    public ChatRoomMembers selectChatRoomMemberByUserIdAndChatRoomId(String userId, String chatRoomId) {
        return chatMapper.selectChatRoomMemberByUserIdAndChatRoomId(userId,chatRoomId);
    }
}
