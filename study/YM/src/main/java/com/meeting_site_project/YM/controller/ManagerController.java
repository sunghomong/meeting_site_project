package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.CheckService;
import com.meeting_site_project.YM.service.DeleteService;
import com.meeting_site_project.YM.service.ManagerService;
import com.meeting_site_project.YM.service.UpdateService;
import com.meeting_site_project.YM.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {


    CheckService checkService;

    UpdateService updateService;

    DeleteService deleteService;

    ManagerService managerService;

    @Autowired
    public ManagerController(CheckService checkService, UpdateService updateService, DeleteService deleteService, ManagerService managerService) {
        this.checkService = checkService;
        this.updateService = updateService;
        this.deleteService = deleteService;
        this.managerService = managerService;
    }

    @GetMapping("")
    public String createForm(@RequestParam("userId") String userId, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute(LoginController.SessionConst.LOGIN_MEMBER);
        if(authInfo == null || authInfo.getUserAdmin() == 0) {
            return "redirect:/";
        }
        session.setAttribute("userId", userId);

        return "redirect:/manager/memberList";
    }


    @GetMapping("memberList")
    public String getMemberList(Criteria cri, Model model, HttpSession session) {

        AuthInfo authInfo = (AuthInfo) session.getAttribute(LoginController.SessionConst.LOGIN_MEMBER);
        if(authInfo == null || authInfo.getUserAdmin() == 0) {
            return "redirect:/";
        }

        int totalMember = checkService.getTotal();
        List<Member> memberList = checkService.getMemberListWithPaging(cri);

        model.addAttribute("memberList", memberList);
        model.addAttribute("pageMaker", new PageDTO(cri, totalMember));

        return "manager/memberList";
    }


    @GetMapping("delete/{userId}")
    public String deleteMember(@PathVariable("userId") String userId, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute(LoginController.SessionConst.LOGIN_MEMBER);
        if(authInfo == null || authInfo.getUserAdmin() == 0) {
            return "redirect:/";
        }

        deleteService.deleteMemberById(userId);

        return "redirect:/manager/memberList"; // 특정 회원 삭제 후 회원 목록 페이지로 리다이렉트
    }


    @GetMapping("/noticeList")
    public String noticeListFormForManager(HttpSession session,Model model) {

        AuthInfo authInfo = (AuthInfo) session.getAttribute("loginMember");
        if(authInfo == null || authInfo.getUserAdmin() == 0) {
            return "redirect:/";
        }

        List<Notices> notices = managerService.selectNoticeListByUserId(authInfo.getUserId()); // 본인이 만든 공지 사항 리스트 확인 (관리자)

        model.addAttribute("notices",notices);

        return "/manager/noticeList";
    }

    @GetMapping("createNotice")
    public String createNoticeForm(HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute(LoginController.SessionConst.LOGIN_MEMBER);
        if(authInfo == null || authInfo.getUserAdmin() == 0) {
            return "redirect:/";
        }

        return "/manager/createNotice";
    }

    @PostMapping("createNotice")
    public void insertNotice(@RequestParam("title") String title,
                               @RequestParam("content") String content,
                               @RequestParam("attachments") MultipartFile attachment,
                               HttpServletRequest request) throws IOException {

        AuthInfo authInfo = (AuthInfo) request.getSession().getAttribute("loginMember");

        Notices notices = new Notices();

        // UUID를 사용하여 고유한 askId 생성
        String uniqueNoticeId = UUID.randomUUID().toString();
        notices.setContent(content.replace("\r\n","<br>"));
        notices.setUserId(authInfo.getUserId());
        notices.setNoticeId(uniqueNoticeId);
        notices.setTitle(title);


        managerService.insertNotice(notices,attachment);

    }
    @GetMapping("/getSearchList")
    @ResponseBody
    private List<Member> getSearchList(@RequestParam("userId") String userId) {


        List<Member> searchList = checkService.getSearchList(userId);

        return searchList;
    }

}
