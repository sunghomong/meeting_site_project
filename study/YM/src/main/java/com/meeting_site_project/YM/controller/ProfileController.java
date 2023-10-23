package com.meeting_site_project.YM.controller;


import com.meeting_site_project.YM.service.ProfileService;
import com.meeting_site_project.YM.vo.Member;
import com.meeting_site_project.YM.vo.ProfileUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
        model.addAttribute("member", member);
        return "/profile/profileUpdateForm";
    }

    @PostMapping("/profileUpdate")
    public String profileUpdate(@Valid @ModelAttribute("profileUpdate") ProfileUpdate profileUpdate, MultipartFile picture) throws Exception{
        profileUpdate.setUserId(profileUpdate.getUserId().split(",")[0]);
        profileService.ProfileUpdate(profileUpdate, picture);
        return "redirect:/profile?userId="+profileUpdate.getUserId();
    }

}
