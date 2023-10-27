package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.FindAuthInfoService;
import com.meeting_site_project.YM.vo.ChangePassword;
import com.meeting_site_project.YM.vo.FindId;
import com.meeting_site_project.YM.vo.FindPassword;
import com.meeting_site_project.YM.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class FindAuthInfoController {

    FindAuthInfoService findAuthInfoService;


    @Autowired
    public FindAuthInfoController(FindAuthInfoService findAuthInfoService) {
        this.findAuthInfoService = findAuthInfoService;
    }

    @GetMapping("/findId")
    public String findIdForm(@ModelAttribute("findId") FindId FindId) {
        return "/findAuthInfo/findIdForm";
    }

    @PostMapping("/findId")
    public String findIdSuccess(@Valid @ModelAttribute("findId") FindId findId, BindingResult bindingResult, Model model) {
        // 폼 유효성 검사 에러가 있는지 확인
        if(bindingResult.hasErrors()) {

            return "/findAuthInfo/findIdForm";
        }

        Member member = findAuthInfoService.findId(findId);

        if(member==null) {
            bindingResult.reject("findIdFail", "이름 또는 이메일을 확인해주세요.");
            return "findAuthInfo/findIdForm";
        }

        model.addAttribute("userId", member.getUserId());

        return "/findAuthInfo/findIdSuccess";
    }
    @GetMapping("/findPassword")
    public String findPasswordForm(@ModelAttribute("findPassword") FindPassword findPassword) {
        return "/findAuthInfo/findPasswordForm";
    }
    @PostMapping("/findPassword")
    public String findPasswordSuccess(@Valid @ModelAttribute("findPassword") FindPassword findPassword, BindingResult bindingResult) {
        // 폼 유효성 검사 에러가 있는지 확인
        if(bindingResult.hasErrors()) {
            return "/findAuthInfo/findPasswordForm";
        }

        Member member = findAuthInfoService.findPassword(findPassword);

        if(member==null) {
            bindingResult.reject("findPasswordFail", "아이디 또는 이메일을 확인해주세요.");
            return "findAuthInfo/findPasswordForm";
        }

        return "redirect:/changePassword?userId=" + member.getUserId();
    }

    @GetMapping("/changePassword")
    public String changePasswordForm(@ModelAttribute("changePassword") ChangePassword changePassword, @RequestParam("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "/findAuthInfo/changePasswordForm";
    }

    @PostMapping("/changePassword")
    public String changePasswordSuccess(@RequestParam("userId") String userId, @Valid @ModelAttribute("changePassword") ChangePassword changePassword, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "/findAuthInfo/changePasswordForm";
        }

        if(!changePassword.getUserPassword().equals(changePassword.getConfirmUserPassword())) {
            bindingResult.reject("changePasswordFail", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("userId", userId);
            return "findAuthInfo/changePasswordForm";
        }

        changePassword.setUserId(userId);
        findAuthInfoService.changePassword(changePassword);

        return "findAuthInfo/changePasswordSuccess";
    }

}
