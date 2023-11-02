package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.Security.SHA256;
import com.meeting_site_project.YM.service.DeleteService;
import com.meeting_site_project.YM.service.ProfileService;
import com.meeting_site_project.YM.vo.AuthInfo;
import com.meeting_site_project.YM.vo.ConfirmUserPassword;
import com.meeting_site_project.YM.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@Controller
public class UserDeleteController {

    DeleteService deleteService;
    ProfileService profileService;
    @Autowired
    public UserDeleteController(DeleteService deleteService, ProfileService profileService) {
        this.deleteService = deleteService;
        this.profileService = profileService;
    }

    @GetMapping("/userDelete")
    public String userDelete(@ModelAttribute("password")ConfirmUserPassword confirmUserPassword) {

        return "/profile/userDeleteForm";
    }

    @PostMapping("/userDelete")
    public String userDeleteSuccess(@Valid @ModelAttribute("password")ConfirmUserPassword confirmUserPassword, BindingResult bindingResult, HttpSession session) throws NoSuchAlgorithmException {
        if(bindingResult.hasErrors()) {
            return "profile/userDeleteForm"; // 에러가 있으면 로그인 폼으로 이동
        }
        SHA256 sha256 = new SHA256();
        confirmUserPassword.setUserPassword(sha256.encrypt(confirmUserPassword.getUserPassword()));
        AuthInfo authInfo = (AuthInfo) session.getAttribute(LoginController.SessionConst.LOGIN_MEMBER);
        Member member = profileService.selectByPassword(confirmUserPassword.getUserPassword(), authInfo.getUserId());

        if(member==null) {
            bindingResult.reject("confirmPasswordFail", "비밀번호를 확인해주세요.");
            return "profile/userDeleteForm"; // 로그인 폼으로 이동
        }

        deleteService.deleteMemberById(authInfo.getUserId());
        session.invalidate(); // 세션 무효화. 즉, 로그아웃 처리
        return "/profile/userDeleteSuccess";
    }
}
