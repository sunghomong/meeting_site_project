package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.NoticeService;
import com.meeting_site_project.YM.vo.Notices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("")
    public String showNoticeListForm(Model model) {

        List<Notices> notices = noticeService.selectNoticeList();

        model.addAttribute("notices",notices);

        return "/notice/noticeList";
    }

//    @GetMapping("noticeDetail")
//    public String showNoticeDetail(@RequestParam("noticeId") String noticeId,Model model) {
//
//        Notices notices = noticeService.selectNoticeByNoticeId(noticeId);
//
//        model.addAttribute("notice",notices);
//
//        return "/notice/noticeDetail";
//    }


}
