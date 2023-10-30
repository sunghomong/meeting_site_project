package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.MeetingService;
import com.meeting_site_project.YM.vo.GroupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class OnedayMtController {

    MeetingService meetingService;

    @Autowired
    public OnedayMtController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

//    @GetMapping("/onedayMtForm")
//    public String onedayMeetingList(@RequestParam("number") int groupType, Model model) {
//
//        List<GroupInfo> groupInfoList = meetingService.selectOnedayGroupList(groupType);
//
//        model.addAttribute("groupInfo",groupInfoList );
//
//        return "meeting/onedayMtForm";
//    }
}
