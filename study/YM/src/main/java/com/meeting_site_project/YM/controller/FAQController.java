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
import org.springframework.web.multipart.MultipartFile;

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
    public  String createAskForm(@RequestParam(value = "userId", required = false) String userId, Model model) {

        if (userId.isEmpty()) { // 로그인 정보 없을시
            return "redirect:/login";
        } else {
            model.addAttribute("userId",userId);
            return "/FAQ/createAsk";
        }
    }

    @PostMapping("createAsk")
    public void createAsk(@RequestParam("subject") String subject,
                          @RequestParam("content") String content,
                          @RequestParam("attachments") MultipartFile attachments ,
                          @RequestParam("userId") String userId) throws Exception {

        AskContent askContent = new AskContent();

        askContent.setSubject(subject);
        askContent.setContent(content);
        askContent.setUserId(userId);

        // UUID를 사용하여 고유한 askId 생성
        String uniqueAskId = UUID.randomUUID().toString();
        // askContent에 생성된 askId 설정
        askContent.setAskId(uniqueAskId);

        // 폼 데이터를 받아 처리하는 코드를 작성
        joinService.insertAsk(askContent,attachments);
    }

    @GetMapping("askDetail")
    public String askDetail(@RequestParam("askId") String askId,HttpServletRequest request,Model model) {

//        HttpSession session = request.getSession();
//
//        String userId = (String) session.getAttribute("userId"); // 세션에 저장되어 있는 userId 불러옴

        String userId = (String) request.getAttribute("userId");

        System.out.println(userId);

        AskContent askContent = checkService.selectAskDetailByAskId(askId);

//        request.getAttribute("userId",userId);
        model.addAttribute("askContent", askContent);

        return "/FAQ/askDetail";

    }

}
