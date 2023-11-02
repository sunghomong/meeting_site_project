package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.CheckService;
import com.meeting_site_project.YM.service.MeetingService;
import com.meeting_site_project.YM.vo.AuthInfo;
import com.meeting_site_project.YM.vo.GroupInfo;
import com.meeting_site_project.YM.vo.Keyword;
import com.meeting_site_project.YM.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@Controller
public class MeetingController {

    MeetingService meetingService;
    CheckService checkService;

    @Autowired
    public MeetingController(MeetingService meetingService, CheckService checkService) {
        this.meetingService = meetingService;
        this.checkService = checkService;
    }

    @GetMapping("/onedayMtForm")
    public String onedayMeetingList(@RequestParam("number") int groupType, Model model) {

        List<GroupInfo> groupInfoList = meetingService.selectOnedayGroupList(groupType);
        List<Keyword> keywords = meetingService.selectFirstKeywordList();

        model.addAttribute("groupInfo",groupInfoList );
        model.addAttribute("keywords", keywords);

        return "meeting/onedayMtForm";
    }

    @GetMapping("/regulardayMtForm")
    public String regulardayMeetingList(@RequestParam("number") int groupType, Model model) {

        List<GroupInfo> groupInfoList = meetingService.selectRegulardayGroupList(groupType);
        List<Keyword> keywords = meetingService.selectFirstKeywordList();

        model.addAttribute("groupInfo",groupInfoList );
        model.addAttribute("keywords", keywords);

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
        if (groupInfo.getGroupType() == 0) {
            return "redirect:/onedayMtForm?number=0";
        }

        return "redirect:/regulardayMtForm?number=1";
    }

    @GetMapping("/meetingView")
    public String meetingView(@RequestParam String groupId, Model model) {

        GroupInfo groupInfo = meetingService.selectGroupInfoById(groupId);

        Member member = checkService.selectMemberById(groupInfo.getOwnerUserId());

        model.addAttribute("memberInfo", member);
        model.addAttribute("groupInfo", groupInfo);

        return "/meeting/meetingView";
    }

    @GetMapping("/meetingUpdateForm")
    public String meetingUpdateForm(@RequestParam String groupId,  Model model) {
        GroupInfo groupInfo = meetingService.selectGroupInfoById(groupId);
        List<Keyword> keywords = meetingService.selectFirstKeywordList();
        List<Keyword> secondKeywords = meetingService.selectSecondKeywordList(groupInfo.getFirstKeyword());
        model.addAttribute("groupInfo", groupInfo);
        model.addAttribute("keywords", keywords);
        model.addAttribute("secondKeywords", secondKeywords);
        return "/meeting/meetingUpdateForm";
    }

    @PostMapping("/meetingUpdateForm")
    public String meetingUpdate(GroupInfo groupInfo, MultipartFile picture) throws IOException {
        meetingService.updateMeeting(groupInfo, picture);
        meetingService.updateGroupKeyword(groupInfo);

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