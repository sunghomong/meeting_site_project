package com.meeting_site_project.YM.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogoutController {

    // 로그아웃 요청에 대한 핸들러 메서드
    @GetMapping("/logout")
    public String logout(HttpSession session){

        session.invalidate(); // 세션 무효화. 즉, 로그아웃 처리

        return "redirect:/"; // 홈 화면으로 이동
    }
}