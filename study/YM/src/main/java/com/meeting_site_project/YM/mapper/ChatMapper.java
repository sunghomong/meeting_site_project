package com.meeting_site_project.YM.mapper;

import com.meeting_site_project.YM.vo.ChatMessage;
import com.meeting_site_project.YM.vo.ChatRoom;
import com.meeting_site_project.YM.vo.ChatRoomMembers;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;


@Mapper
public interface ChatMapper {

    public void insertChatRoom(ChatRoom chatRoom);

    ChatRoom selectChatRoomInfoByRoomId(String roomId);

    void insertChatMessage(ChatMessage chatMessage);

    void insertChatRoomMembers(ChatRoomMembers chatRoomMembers);

    List<String> selectChatRoomIdListByUserId(String userId);

    ChatRoom selectChatRoomByChatRoomId(String chatRoomId);

    boolean selectChatMessageByUserIdWithChatRoomId(ChatMessage chatMessage);

    Date selectChatMembersByRoomIdWithUserId(ChatRoomMembers chatRoomMembers);

    List<ChatMessage> selectChatMessageAfterInnerDateByChatRoomId(ChatRoomMembers chatRoomMembers);

    String selectChatRoomIdByGroupId(String groupId);

    int selectChatMemberCntByChatRoomId(String chatRoomId);

    void insertChatMemberCntByRoomId(String chatRoomId);

    boolean checkIsChatRoomMemberByUserIdAndChatRoomId(ChatRoomMembers chatRoomMembers);

    ChatRoom selectChatRoomInfoByGroupId(String groupId);

    void deleteChatMemeberByChatMessage(ChatMessage chatMessage);

    void subtractChatMemberCntByRoomId(String chatRoomId);

    List<ChatMessage> selectChatMessageListByChatRoomId(String chatRoomId);

    void insertChatDateMessageByChatMessage(ChatMessage chatMessage);

    String selectChatOwnerIdByChatRoomId(String chatRoomId);

    List<ChatRoomMembers> selectChatRoomMemberListByGroupId(String groupId);

    ChatRoomMembers selectChatMemberByChatRoomMember(ChatRoomMembers chatRoomMembers);
}
