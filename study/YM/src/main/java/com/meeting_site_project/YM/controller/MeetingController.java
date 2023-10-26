package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.MeetingService;
import com.meeting_site_project.YM.vo.AuthInfo;
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
import java.util.List;


@Controller
public class MeetingController {

    MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
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
        System.out.println(groupInfo);
        meetingService.insertFirstMeeting(groupInfo, picture);
        meetingService.insertGroupByKeyword(groupInfo);

        return "redirect:/onedayMtForm?number=0";
    }
}