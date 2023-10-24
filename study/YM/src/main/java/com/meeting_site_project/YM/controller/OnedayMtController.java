package com.meeting_site_project.YM.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OnedayMtController {

    @GetMapping("onedayMtForm")
    public String onedayMtForm() {
        return "/meeting/onedayMtForm";
    }
}
