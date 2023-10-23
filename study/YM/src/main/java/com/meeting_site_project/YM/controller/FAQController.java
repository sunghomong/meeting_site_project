package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.CheckService;
import com.meeting_site_project.YM.service.JoinService;
import com.meeting_site_project.YM.vo.AskContent;
import com.meeting_site_project.YM.vo.AuthInfo;
import com.meeting_site_project.YM.vo.LoginCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/FAQ")
public class FAQController {

    CheckService checkService;

    JoinService joinService;

    @Autowired
    public FAQController(CheckService checkService, JoinService joinService) {
        this.checkService = checkService;
        this.joinService = joinService;
    }


    @GetMapping("")
    public String showAskListForm(@ModelAttribute("loginInfo") LoginCommand loginCommand, HttpServletRequest request, HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute(LoginController.SessionConst.LOGIN_MEMBER);

        if (authInfo != null) { // 로그인 정보가 있을시
            model.addAttribute("userId", authInfo.getUserId());
        }

        List<AskContent> askList = checkService.selectAskList();
        model.addAttribute("askList",askList);

        return "/FAQ/askList"; // 문의 리스트로 이동
        // 로그인 안한 상태/ 폼 화면으로 이동

    }

    @GetMapping("createAsk")
    public  String createAskForm(@RequestParam(value = "userId", required = false) String userId) {

        if (userId.isEmpty()) {
            System.out.println("로그인이 필요합니다.");
            return "redirect:/login";
        } else {
            System.out.println(userId);
            return "/FAQ/createAsk";
        }
    }

    @PostMapping("createAsk")
    public String createAsk(@RequestBody AskContent askContent ,@RequestParam("userId") String userId) {
        // UUID를 사용하여 고유한 askId 생성
        askContent.setUserId(userId);
        String uniqueAskId = UUID.randomUUID().toString();
        // askContent에 생성된 askId 설정
        askContent.setAskId(uniqueAskId);

        // 폼 데이터를 받아 처리하는 코드를 작성
        joinService.insertAsk(askContent);
        // subject, content, attachments 등의 데이터를 사용하여 문의 작성 처리

        // 성공 또는 실패에 따라 리다이렉트할 URL을 반환
        return "redirect:/FAQ/askList"; // 성공했을 경우 리다이렉트할 URL
    }
}
