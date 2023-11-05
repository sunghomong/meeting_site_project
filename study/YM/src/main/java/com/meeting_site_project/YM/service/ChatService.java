package com.meeting_site_project.YM.service;


import com.meeting_site_project.YM.repository.ChatRepository;
import com.meeting_site_project.YM.vo.ChatMessage;
import com.meeting_site_project.YM.vo.ChatRoom;
import com.meeting_site_project.YM.vo.ChatRoomMembers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    public ChatRoom selectChatRoomInfoByRoomId(String roomId) {
        return  chatRepository.selectChatRoomInfoByRoomId(roomId);
    }

    public void insertChatMessage(ChatMessage chatMessage) {
        chatRepository.insertChatMessage(chatMessage);
    }

    public void insertChatRoomOwnerMember(ChatRoom chatRoom, String nickName) {
        chatRepository.insertChatRoomOwnerMember(chatRoom,nickName);
    }

    public boolean selectChatMessageByUserIdWithChatRoomId(ChatMessage chatMessage) {
        return chatRepository.selectChatMessageByUserIdWithChatRoomId(chatMessage);
    }

    public Date selectChatMembersByRoomIdWithUserId(String chatRoomId, String userId) {

        ChatRoomMembers chatRoomMembers = new ChatRoomMembers();

        chatRoomMembers.setChatRoomId(chatRoomId);
        chatRoomMembers.setUserId(userId);

        return chatRepository.selectChatMembersByRoomIdWithUserId(chatRoomMembers);
    }

    public List<ChatMessage> selectChatMessageAfterInnerDateByChatRoomId(Date innerDate, String chatRoomId) {

        ChatRoomMembers chatRoomMembers = new ChatRoomMembers();

        chatRoomMembers.setChatInnerTime(innerDate);
        chatRoomMembers.setChatRoomId(chatRoomId);

        return chatRepository.selectChatMessageAfterInnerDateByChatRoomId(chatRoomMembers);
    }


    public void insertChatMemberCntByRoomId(String chatRoomId) {
        chatRepository.insertChatMemberCntByRoomId(chatRoomId);
    }

    public void insertChatRoomMember(String userId, String chatRoomId,String nickName) {
        chatRepository.insertChatRoomMember(userId,chatRoomId,nickName);
    }

    public boolean checkIsChatRoomMemberByUserIdAndChatRoomId(String userId, String chatRoomId) {
        return chatRepository.checkIsChatRoomMemberByUserIdAndChatRoomId(userId,chatRoomId);
    }

    public ChatRoom selectChatRoomInfoByGroupId(String groupId) {
        return chatRepository.selectChatRoomInfoByGroupId(groupId);
    }

    public void deleteChatMemeberByChatMessage(ChatMessage chatMessage) {
        chatRepository.deleteChatMemeberByChatMessage(chatMessage);
    }

    public void subtractChatMemberCntByRoomId(String chatRoomId) {
        chatRepository.subtractChatMemberCntByRoomId(chatRoomId);
    }

    public ChatMessage isChatMessageDateByChatMessage(ChatMessage chatMessage) {

        List<ChatMessage> chatMessageList = chatRepository.selectChatMessageListByChatRoomId(chatMessage.getChatRoomId());

        if (!chatMessageList.isEmpty()) {
            // 가장 최근의 메시지를 이전 메시지로 간주
            return chatMessageList.get(chatMessageList.size() - 1);
        } else {
            return null;
        }

    }

    public void insertChatDateMessageByChatMessage(ChatMessage chatMessage) {
        chatRepository.insertChatDateMessageByChatMessage(chatMessage);
    }

    public String selectChatOwnerIdByChatRoomId(String chatRoomId) {
        return chatRepository.selectChatOwnerIdByChatRoomId(chatRoomId);
    }

    public List<ChatRoomMembers> selectChatRoomMemberListByChatRoomId(String chatRoomId) {
        return chatRepository.selectChatRoomMemberListByChatRoomId(chatRoomId);
    }


    public void updateChatRoomMemberAdminByRoomUserId(String roomUserId, int admin) {
        chatRepository.updateChatRoomMemberAdminByRoomUserId(roomUserId,admin);
    }

    public String selectGroupIdWhereChatRoomByChatRoomId(String chatRoomId) {
        return chatRepository.selectGroupIdWhereChatRoomByChatRoomId(chatRoomId);
    }

    public ChatRoomMembers selectChatRoomMemberByUserIdAndChatRoomId(String userId, String chatRoomId) {
        return chatRepository.selectChatRoomMemberByUserIdAndChatRoomId(userId,chatRoomId);
    }
}
