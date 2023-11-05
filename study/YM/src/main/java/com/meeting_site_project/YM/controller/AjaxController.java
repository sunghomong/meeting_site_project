package com.meeting_site_project.YM.controller;

import com.meeting_site_project.YM.service.MeetingService;
import com.meeting_site_project.YM.service.NoticeService;
import com.meeting_site_project.YM.service.UpdateService;
import com.meeting_site_project.YM.vo.GroupInfo;
import com.meeting_site_project.YM.vo.Keyword;
import com.meeting_site_project.YM.vo.Notices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class AjaxController {
    @Autowired
    MeetingService meetingService;
    @Autowired
    NoticeService noticeService;
@Autowired
    UpdateService updateService;
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
    @PostMapping("/manager/memberEdit")
    public String editMemberAdmin(@RequestParam("userId") String userId, @RequestParam("userAdmin") int userAdmin) {
        // 수정된 데이터를 저장 또는 업데이트하는 로직을 구현
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("userId", userId);
        parameters.put("userAdmin", userAdmin);

        updateService.updateMemberAdmin(parameters);

        return "Success"; // 수정 후 회원 리스트로 이동
    }
}
