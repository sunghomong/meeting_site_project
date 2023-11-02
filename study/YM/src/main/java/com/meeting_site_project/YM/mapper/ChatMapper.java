package com.meeting_site_project.YM.mapper;

import com.meeting_site_project.YM.vo.ChatMessage;
import com.meeting_site_project.YM.vo.ChatRoom;
import com.meeting_site_project.YM.vo.ChatRoomMembers;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ChatMapper {

    public void insertChatRoom(ChatRoom chatRoom);

    public List<ChatRoom> selectChatRoomListByUserId(String userId);

    int selectChatRoomCntByUserId(String userId);

    ChatRoom selectChatRoomInfoByRoomId(String roomId);

    void insertChatMessage(ChatMessage chatMessage);

    List<ChatMessage> getLastChatMessagesByRoomId(String chatRoomId);

    void insertChatRoomMembers(ChatRoomMembers chatRoomMembers);

    List<String> selectChatRoomIdListByUserId(String userId);

    ChatRoom selectChatRoomByChatRoomId(String chatRoomId);
}
