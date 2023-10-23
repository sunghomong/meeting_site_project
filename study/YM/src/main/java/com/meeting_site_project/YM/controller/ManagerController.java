package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.vo.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("manager")
public class ManagerController {

    @GetMapping("")
    public String createForm() {
        return "manager/main";
    }



    @GetMapping("memberList")
    public String listMembers(Model model) {
        // 가상의 회원 목록 생성 (이 정보를 실제 데이터베이스에서 가져오도록 바꿀 수 있습니다)
        List<Member> members = new ArrayList<>();

//        members.add(new Member);
//        members.add(new Member);
        // SELECT * FROM member
        model.addAttribute("members", members);

        return "manager/memberList";
    }

    @GetMapping("edit/{userId}")
    public String editMemberPage(@PathVariable("userId") String userId, Model model) {
        // 특정 회원을 수정하는 페이지로 이동하는 로직을 구현
        userId = "홍길동";
        // 데이터베이스에서 userId에 해당하는 회원 정보를 가져오는 로직을 구현

        return "/manager/memberEdit"; // 수정 페이지로 이동
    }



    @GetMapping("delete/{userId}")
    public String deleteMember(@PathVariable("userId") String userId) {
        // 특정 회원을 삭제하는 로직을 구현
        // 회원을 삭제하고 회원 목록 페이지로 이동
        // 삭제 로직 구현
        return "redirect:/manager/memberList"; // 특정 회원 삭제 후 회원 목록 페이지로 리다이렉트
    }
}
