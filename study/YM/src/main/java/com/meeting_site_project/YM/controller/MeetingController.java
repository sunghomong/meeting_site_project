package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.ChatService;
import com.meeting_site_project.YM.service.MeetingService;
import com.meeting_site_project.YM.vo.AuthInfo;
import com.meeting_site_project.YM.vo.ChatRoom;
import com.meeting_site_project.YM.vo.GroupInfo;
import com.meeting_site_project.YM.vo.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Controller
public class MeetingController {

    MeetingService meetingService;

    ChatService chatService;

    @Autowired
    public MeetingController(MeetingService meetingService, ChatService chatService) {
        this.meetingService = meetingService;
        this.chatService = chatService;
    }


    @GetMapping("/onedayMtForm")
    public String onedayMeetingList(@RequestParam("number") int groupType, Model model) {

        List<GroupInfo> groupInfoList = meetingService.selectOnedayGroupList(groupType);

        model.addAttribute("groupInfo",groupInfoList );

        return "meeting/onedayMtForm";
    }

    @GetMapping("/regulardayMtForm")
    public String regulardayMeetingList(@RequestParam("number") int groupType, Model model) {

        List<GroupInfo> groupInfoList = meetingService.selectRegulardayGroupList(groupType);

        model.addAttribute("groupInfo",groupInfoList );

        return "meeting/regulardayMtForm";
    }


    @GetMapping("/firstMeeting")
    public String firstMeeting(@RequestParam("number") int number, @ModelAttribute("groupInfo") GroupInfo groupInfo, HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute(LoginController.SessionConst.LOGIN_MEMBER);
        if (authInfo == null) {
            return "redirect:/login";
        }

        List<Keyword> keywords = meetingService.selectFirstKeywordList();

        String ownerUserId = authInfo.getUserId();
        model.addAttribute("keywords", keywords);
        model.addAttribute("ownerUserId", ownerUserId);
        model.addAttribute("groupType", number);
        return "meeting/firstMeeting";

    }


    @PostMapping("/firstMeeting")
    public String firstMeeting(@Valid @ModelAttribute("groupInfo") GroupInfo groupInfo, BindingResult bindingResult , Model model, MultipartFile picture) throws Exception {
        meetingService.insertFirstMeeting(groupInfo, picture);
        meetingService.insertGroupByKeyword(groupInfo);

        // 채팅방 생성
        ChatRoom chatRoom = new ChatRoom();

        // UUID를 사용하여 고유한 chatRoomId 생성
        String uniqueChatRoomId = UUID.randomUUID().toString();

        chatRoom.setChatRoomId(uniqueChatRoomId); // 채팅방 ID
        chatRoom.setOwnerId(groupInfo.getOwnerUserId()); // 채팅 방장
        chatRoom.setGroupId(groupInfo.getGroupId()); // 모임 ID
        chatRoom.setMaxUserCnt(groupInfo.getGroupNumberOfPeople()); // 최대 인원수
        chatRoom.setChatRoomName(groupInfo.getGroupName()); // 채팅방 이름
        chatRoom.setUserCount(1); // 채팅방 인원수 1 자동적으로 증가 (방장)

        chatService.insertChatRoom(chatRoom); // 채팅방 생성
        chatService.insertChatRoomOwnerMember(chatRoom); // chatRoom 데이터 가지고 멤버 생성


        if (groupInfo.getGroupType() == 0) {
            return "redirect:/onedayMtForm?number=0";
        }

        return "redirect:/regulardayMtForm?number=1";
    }

    @GetMapping("/meetingView")
    public String meetingView(@RequestParam String groupId, Model model) {

        GroupInfo groupInfo = meetingService.selectGroupInfoById(groupId);

        model.addAttribute("groupInfo", groupInfo);

        return "/meeting/meetingView";
    }

    @GetMapping("/meetingUpdateForm")
    public String meetingUpdateForm(@RequestParam String groupId,  Model model) {
        GroupInfo groupInfo = meetingService.selectGroupInfoById(groupId);
        List<Keyword> keywords = meetingService.selectKeywords();
        model.addAttribute("groupInfo", groupInfo);
        model.addAttribute("keywords", keywords);
        return "/meeting/meetingUpdateForm";
    }

    @PostMapping("/meetingUpdateForm")
    public String meetingUpdate(GroupInfo groupInfo, MultipartFile picture) throws IOException {
        System.out.println(groupInfo.getGroupId());
        meetingService.updateMeeting(groupInfo, picture);

        if (groupInfo.getGroupType() == 0) {
            return "redirect:/onedayMtForm?number=0";
        }

        return "redirect:/regulardayMtForm?number=1";
    }

    @GetMapping("deleteGroup")
    public String deleteGroup(@RequestParam String groupId) {

        meetingService.deleteGroup(groupId);

        return "redirect:/";
    }



}