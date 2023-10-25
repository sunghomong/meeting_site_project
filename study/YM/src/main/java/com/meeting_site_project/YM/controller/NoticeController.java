package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.CheckService;
import com.meeting_site_project.YM.service.DeleteService;
import com.meeting_site_project.YM.service.JoinService;
import com.meeting_site_project.YM.vo.Notices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    CheckService checkService;
    DeleteService deleteService;
    JoinService joinService;

    public NoticeController(CheckService checkService, DeleteService deleteService, JoinService joinService) {
        this.checkService = checkService;
        this.deleteService = deleteService;
        this.joinService = joinService;
    }

    @GetMapping("")
    public String showNoticeListForm(Model model) {

        List<Notices> notices = checkService.selectNoticeList();

        model.addAttribute(notices);

        return "/notice/noticeList";
    }



}
