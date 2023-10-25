package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.MeetingService;
import com.meeting_site_project.YM.vo.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AjaxController {
    @Autowired
    MeetingService meetingService;
    @PostMapping("/keywordRequest")
    @ResponseBody
    public List<Keyword> handleAjaxRequest(@RequestParam("firstKeyword") String firstKeyword) {
        // selectedValue를 이용한 처리

        List<Keyword> keywords = meetingService.selectSecondKeywordList(firstKeyword); // 예시로 yourService.getList 메서드를 호출

        return keywords;
    }
}
