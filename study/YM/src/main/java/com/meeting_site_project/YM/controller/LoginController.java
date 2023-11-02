package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.Security.SHA256;
import com.meeting_site_project.YM.service.AuthService;
import com.meeting_site_project.YM.vo.AuthInfo;
import com.meeting_site_project.YM.vo.LoginCommand;
import com.meeting_site_project.YM.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@Controller
public class LoginController {

    // AuthService를 주입받기 위한 필드
    AuthService authService;

    @Autowired
    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    // 세션 관련 상수 클래스
    public abstract class SessionConst {

        public static final String LOGIN_MEMBER = "loginMember";

    }

    // 로그인 폼을 불러오는 요청에 대한 핸들러 메서드
    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginInfo") LoginCommand loginCommand, HttpServletRequest request, HttpSession session) {
        AuthInfo authInfo = (AuthInfo) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // 이미 로그인한 상태면 홈 화면으로 이동
        if(authInfo != null) {
            return "redirect:/";
        }

        // 로그인 폼 화면으로 이동
        return "login/loginForm";
    }

    // 로그인 성공 시 처리하는 요청에 대한 핸들러 메서드
    @PostMapping("/login")
    public String loginSuccess(@Valid @ModelAttribute("loginInfo") LoginCommand loginCommand, BindingResult bindingResult, HttpServletRequest request) throws NoSuchAlgorithmException {


        // 폼 유효성 검사 에러가 있는지 확인
        if(bindingResult.hasErrors()) {
            return "login/loginForm"; // 에러가 있으면 로그인 폼으로 이동
        }

        // 사용자의 아이디와 비밀번호로 회원 정보 조회
        SHA256 sha256 = new SHA256();
        loginCommand.setUserPassword(sha256.encrypt(loginCommand.getUserPassword()));
        Member member = authService.selectByIdPassword(loginCommand);

        // 회원 정보가 없으면 로그인 실패 처리
        if(member==null) {
            bindingResult.reject("loginfail", "아이디 또는 비밀번호를 확인해주세요.");
            return "login/loginForm"; // 로그인 폼으로 이동
        }

        // 인증 정보 생성
        AuthInfo authInfo = new AuthInfo(
                member.getUserId(), member.getUserPassword(), member.getUserName(), member.getNickName(), member.getUserPicture(),member.getPicturePath(), member.getUserAdmin());

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_MEMBER, authInfo); // 세션에 인증 정보 저장

        return "redirect:/"; // 홈 화면으로 이동
    }
}