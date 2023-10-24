package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.MeetingService;
import com.meeting_site_project.YM.vo.AuthInfo;
import com.meeting_site_project.YM.vo.GroupInfo;
import com.meeting_site_project.YM.vo.LoginCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class MeetingController {

    MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/firstMeeting")
    public String firstMeeting(@ModelAttribute("loginInfo") LoginCommand loginCommand, HttpServletRequest request, HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute(LoginController.SessionConst.LOGIN_MEMBER);

        // 이미 로그인한 상태
        if (authInfo != null) {
            model.addAttribute("groupInfoMeetingPoint", 1);
            return "/meeting/firstMeeting";
        }

        // 로그인 안한 상태/ 폼 화면으로 이동
        return "redirect:/login";
    }



}