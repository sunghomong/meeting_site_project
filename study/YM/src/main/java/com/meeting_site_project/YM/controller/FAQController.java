package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.CheckService;
import com.meeting_site_project.YM.service.FAQservice;
import com.meeting_site_project.YM.service.JoinService;
import com.meeting_site_project.YM.vo.AskContent;
import com.meeting_site_project.YM.vo.AuthInfo;
import com.meeting_site_project.YM.vo.CommentAsk;
import com.meeting_site_project.YM.vo.LoginCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/FAQ")
public class FAQController {

    CheckService checkService;

    JoinService joinService;

    FAQservice faQservice;

    @Autowired
    public FAQController(CheckService checkService, JoinService joinService, FAQservice faQservice) {
        this.checkService = checkService;
        this.joinService = joinService;
        this.faQservice = faQservice;
    }




    @GetMapping("")
    public String showAskListForm(@ModelAttribute("loginInfo") LoginCommand loginCommand, HttpServletRequest request, HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute(LoginController.SessionConst.LOGIN_MEMBER);


        if (authInfo != null) { // 로그인 정보가 있을시
            model.addAttribute("userId", authInfo.getUserId());
        }

        List<AskContent> askList = faQservice.selectAskList();
        model.addAttribute("askList",askList);

        return "/FAQ/askList"; // 문의 리스트로 이동
        // 로그인 안한 상태/ 폼 화면으로 이동

    }

    @GetMapping("createAsk")
    public  String createAskForm(HttpSession session, Model model) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute("loginMember");

        if (authInfo != null) { // 로그인 한 상태
            model.addAttribute("userId", authInfo.getUserId());
            model.addAttribute("loggedIn", true);
        } else { // 로그인 안한 상태
            model.addAttribute("notLoggedIn", true);
        }

        return "/FAQ/createAsk";
    }

    @PostMapping("createAsk")
    public void createAsk(@ModelAttribute("formData") AskContent askContent,
                          @RequestParam("attachments") MultipartFile attachments ) throws Exception {
        askContent.setContent(askContent.getContent().replace("\r\n","<br>"));
        // UUID를 사용하여 고유한 askId 생성
        String uniqueAskId = UUID.randomUUID().toString();
        // askContent에 생성된 askId 설정
        askContent.setAskId(uniqueAskId);

        // 폼 데이터를 받아 처리하는 코드를 작성
        joinService.insertAsk(askContent,attachments);
    }

    @GetMapping("askDetail")
    public String askDetail(@RequestParam("askId") String askId,HttpSession session,Model model) {

        AuthInfo authInfo = (AuthInfo) session.getAttribute("loginMember");


        if (authInfo != null) { // 로그인 정보가 있다면
            String loginMemberUserId = authInfo.getUserId();
            model.addAttribute("loginMemberUserId", loginMemberUserId);
            model.addAttribute("loginMemberUserAdmin", authInfo.getUserAdmin());
        }

        List<CommentAsk> commentAsk = faQservice.selectCommentAskByAskId(askId);

        if(commentAsk != null) {
            model.addAttribute("commentAskList", commentAsk);
        }

        AskContent askContent = checkService.selectAskDetailByAskId(askId);

        model.addAttribute("askContent", askContent);

        return "/FAQ/askDetail";

    }

    @GetMapping("askEdit")
    public String askEditForm(@Valid @RequestParam("askId") String askId,Model model) {

        AskContent askContent = faQservice.selectAskListByAskId(askId);
        askContent.setContent(askContent.getContent().replace("<br>","\r\n"));

        model.addAttribute("askContent",askContent);
        model.addAttribute("askId",askId);

        return "/FAQ/askEdit";
    }

    @PostMapping("askEdit")
    public void askEdit(@ModelAttribute("formData") AskContent askContent,
                          @RequestParam(value = "attachments", required = false) MultipartFile attachments) throws IOException {



        if (attachments != null && !attachments.isEmpty()) {
            // 폼 데이터를 받아 처리하는 코드를 작성
            FAQservice.updateAskWithAttachments(askContent,attachments);
        } else {
            askContent.setContent(askContent.getContent().replace("\r\n","<br>"));
            FAQservice.updateAsk(askContent);
        }

    }

    @GetMapping("askDelete/{askId}")
    public String askDelete(@PathVariable("askId") String askId) {

        faQservice.deleteAskByAskId(askId);

        return "redirect:/FAQ/";
    }

    @PostMapping("addComment")
    public String addComment(@RequestParam("askId") String askId,
                             @RequestParam("content") String content,
                             @RequestParam("status") String status,
                             HttpSession session) {

        AuthInfo authInfo = (AuthInfo) session.getAttribute("loginMember");

        CommentAsk commentAsk = new CommentAsk();

        // UUID를 사용하여 고유한 askId 생성
        String uniqueCommentId = UUID.randomUUID().toString();
        // askContent에 생성된 askId 설정
        commentAsk.setContent(content.replace("<br>","\r\n"));
        commentAsk.setUserId(authInfo.getUserId());
        commentAsk.setCommentId(uniqueCommentId);
        commentAsk.setContent(content);
        commentAsk.setAskId(askId);

        faQservice.insertCommentAsk(commentAsk); // 댓글 insert

        AskContent askContent = new AskContent();

        askContent.setAskId(askId);
        askContent.setStatus(status);

        faQservice.updateAskStatus(askContent); // 글 수정 사항 변경

        return "redirect:/FAQ/askDetail?askId=" + askId;
        // th:href="@{/FAQ/askDetail(askId=${ask.askId})}"
    }

}
