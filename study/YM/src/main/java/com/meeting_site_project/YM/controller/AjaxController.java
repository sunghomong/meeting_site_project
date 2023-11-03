package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.MeetingService;
import com.meeting_site_project.YM.service.NoticeService;
import com.meeting_site_project.YM.vo.GroupInfo;
import com.meeting_site_project.YM.vo.Keyword;
import com.meeting_site_project.YM.vo.Notices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AjaxController {
    @Autowired
    MeetingService meetingService;
    @Autowired
    NoticeService noticeService;
    @PostMapping("/keywordRequest")
    @ResponseBody
    public List<Keyword> handleAjaxRequest(@RequestParam("firstKeyword") String firstKeyword) {
        // selectedValue를 이용한 처리

        List<Keyword> keywords = meetingService.selectSecondKeywordList(firstKeyword); // 예시로 yourService.getList 메서드를 호출

        return keywords;
    }

    @PostMapping("keywordByGroupRequest")
    public List<GroupInfo> keywordByGroupRequest (@RequestParam("firstKeyword") String firstKeyword, @RequestParam("groupType") int groupType) {
        System.out.println(firstKeyword);
        System.out.println(groupType);
        List<GroupInfo> groupList = meetingService.selectOnedayKeywordByGroupList(firstKeyword, groupType);

        return groupList;
    }

        @GetMapping("/notice/noticeDetail")
    public Notices showNoticeDetail(@RequestParam("noticeId") String noticeId, Model model) {

        Notices notices = noticeService.selectNoticeByNoticeId(noticeId);

        return notices;
    }
}
