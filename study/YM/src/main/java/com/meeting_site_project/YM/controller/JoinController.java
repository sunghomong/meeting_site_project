package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.JoinService;
import com.meeting_site_project.YM.vo.AuthInfo;
import com.meeting_site_project.YM.vo.JoinMember;
import com.meeting_site_project.YM.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class JoinController {

    JoinService joinService;
    @Autowired
    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    // 회원가입 페이지로 이동하는 요청에 대한 핸들러 메서드
    @GetMapping("/join")
    public String loginForm(JoinMember joinmember, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute(LoginController.SessionConst.LOGIN_MEMBER);
        if(authInfo != null) {
            return "redirect:/";
        }
        // joinForm 객체를 모델에 추가하여 Thymeleaf에서 사용할 수 있도록 합니다.
        return "join/joinForm"; // "join/joinForm.html" 뷰 페이지를 반환합니다.
    }

    // 아이디 중복을 체크하는 요청에 대한 핸들러 메서드
    @PostMapping("/idCheck")
    @ResponseBody
    public String checkUserId(@RequestParam String userId) {
        // 아이디 중복 확인을 위해 JoinService에서 selectById 메서드를 호출합니다.
        Member member = joinService.selectById(userId);
        if (member == null) {
            return "true"; // 중복된 아이디가 없으면 "true"를 반환합니다.
        } else {
            return "false"; // 중복된 아이디가 있으면 "false"를 반환합니다.
        }
    }

    // 닉네임 중복을 체크하는 요청에 대한 핸들러 메서드
    @PostMapping("/nickNameCheck")
    @ResponseBody
    public String checkUserNickName(@RequestParam String nickName) {
        // 닉네임 중복 확인을 위해 JoinService에서 selectByNickName 메서드를 호출합니다.
        Member member = joinService.selectByNickName(nickName);
        if (member == null) {
            return "true"; // 중복된 닉네임이 없으면 "true"를 반환합니다.
        } else {
            return "false"; // 중복된 닉네임이 있으면 "false"를 반환합니다.
        }
    }

    // 이메일 중복을 체크하는 요청에 대한 핸들러 메서드
    @PostMapping("/emailCheck")
    @ResponseBody
    public String checkUserEmail(@RequestParam String emailId, @RequestParam String emailDomain) {
        // 이메일 중복 확인을 위해 JoinService에서 selectByEmail 메서드를 호출합니다.
        Member member = joinService.selectByEmail(emailId, emailDomain);
        if (member == null) {
            return "true"; // 중복된 이메일이 없으면 "true"를 반환합니다.
        } else {
            return "false"; // 중복된 이메일이 있으면 "false"를 반환합니다.
        }
    }

    // 회원가입 완료 페이지로 이동하는 요청에 대한 핸들러 메서드
    @GetMapping("joinSuccess")
    public String mainReturn() {
        return "home"; // "home.html" 뷰 페이지를 반환합니다.
    }

    // 회원가입 정보를 처리하는 요청에 대한 핸들러 메서드
    @PostMapping("/joinSuccess")
    public String joinSuccess(@Valid JoinMember joinMember, BindingResult bindingResult, MultipartFile userPicture ) throws Exception{
        // JoinMember 객체의 유효성을 검사하고, 에러가 있으면 bindingResult에 에러 정보를 저장합니다.

        joinService.insertMember(joinMember, userPicture); // 회원 정보를 DB에 저장합니다.
        return "join/joinSuccess"; // "join/joinSuccess.html" 뷰 페이지를 반환합니다.
    }
}
