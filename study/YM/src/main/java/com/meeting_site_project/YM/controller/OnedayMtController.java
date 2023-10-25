package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.MeetingService;
import com.meeting_site_project.YM.vo.GroupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
public class OnedayMtController {

    MeetingService meetingService;

    @Autowired
    public OnedayMtController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/onedayMtForm")
    public String onedayMeetingList(HttpSession session, Model model) {

        List<GroupInfo> groupInfoList = meetingService.selectJoinList();

        model.addAttribute("groupInfo",groupInfoList );
//        GroupInfo groupInfo = meetingService.insertFirstMeeting();
 /*       GroupInfo gi = new GroupInfo();
        gi.setGroupPicturePath("/files/8f5066ee-90aa-4592-a4f8-f0418264ae9e_유스케이스 다이어그램.png");*/
//        model.addAttribute("groupInfo",groupInfo.getGroupInfo());
        return "meeting/onedayMtForm";
    }
}
