package com.meeting_site_project.YM.controller;


import com.meeting_site_project.YM.Security.SHA256;
import com.meeting_site_project.YM.service.ProfileService;
import com.meeting_site_project.YM.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@Controller
public class ProfileController {

    ProfileService profileService;
    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public String profileView(@RequestParam String userId, HttpSession session, Model model) {
        if(session.getAttribute("loginMember") == null) {
            return "redirect:/login";
        }
        Member member = profileService.selectById(userId);
        model.addAttribute("member", member);
        return "/profile/profileView";
    }

    @GetMapping("/profileUpdate")
    public String profileUpdateForm(@RequestParam String userId, ProfileUpdate profileUpdate, HttpSession session, Model model) throws Exception {
        if(session.getAttribute("loginMember") == null) {
            return "redirect:/login";
        }
        Member member = profileService.selectById(userId);
        member.setUserInfo(member.getUserInfo().replace("<br>","\r\n"));
        model.addAttribute("member", member);
        return "/profile/profileUpdateForm";
    }

    @PostMapping("/profileUpdate")
    public String profileUpdate(@Valid @ModelAttribute("profileUpdate") ProfileUpdate profileUpdate, MultipartFile picture, HttpServletRequest request, HttpSession session) throws Exception{
        profileUpdate.setUserInfo(profileUpdate.getUserInfo().replace("\r\n","<br>"));
        profileService.ProfileUpdate(profileUpdate, picture, session);

        Member member = profileService.selectById(profileUpdate.getUserId());
        AuthInfo authInfo = new AuthInfo(
                member.getUserId(), member.getUserPassword(), member.getUserName(), member.getNickName(), member.getUserPicture(),member.getPicturePath(), member.getUserAdmin());

        session = request.getSession(true);
        session.setAttribute(LoginController.SessionConst.LOGIN_MEMBER, authInfo); // 세션에 인증 정보 저장
        return "redirect:/profile?userId="+profileUpdate.getUserId();
    }

    @GetMapping("/confirmPassword")
    public String confirmPassword(@ModelAttribute("password") ConfirmUserPassword confirmUserPassword) {
        return "profile/confirmPasswordForm";
    }

    @PostMapping("/confirmPassword")
    public String confirmPasswordSuccess(@Valid @ModelAttribute("password") ConfirmUserPassword confirmUserPassword, BindingResult bindingResult, HttpSession session) throws NoSuchAlgorithmException {
        if(bindingResult.hasErrors()) {
            return "profile/confirmPasswordForm"; // 에러가 있으면 로그인 폼으로 이동
        }
        SHA256 sha256 = new SHA256();
        confirmUserPassword.setUserPassword(sha256.encrypt(confirmUserPassword.getUserPassword()));
        AuthInfo authInfo = (AuthInfo) session.getAttribute(LoginController.SessionConst.LOGIN_MEMBER);
        Member member = profileService.selectByPassword(confirmUserPassword.getUserPassword(), authInfo.getUserId());

        if(member==null) {
            bindingResult.reject("confirmPasswordFail", "비밀번호를 확인해주세요.");
            return "profile/confirmPasswordForm"; // 로그인 폼으로 이동
        }



        return "redirect:/changePassword?userId=" + member.getUserId();
    }


}
