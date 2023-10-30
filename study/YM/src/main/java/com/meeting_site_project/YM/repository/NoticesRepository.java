package com.meeting_site_project.YM.repository;

import com.meeting_site_project.YM.mapper.NoticesMapper;
import com.meeting_site_project.YM.vo.Notices;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Repository
public class NoticesRepository implements Repository{

    NoticesMapper noticesMapper;

    @Autowired
    public NoticesRepository(NoticesMapper noticesMapper) {
        this.noticesMapper = noticesMapper;
    }

    public List<Notices> selectNoticeListByUserId(String userId) { // 본인이 만든 공지 사항들 조회
        return noticesMapper.selectNoticeListByUserId(userId);
    }

    public void insertNotice(Notices notices) {
        noticesMapper.insertNotice(notices);
    }

    public List<Notices> selectNoticeList() {
        return noticesMapper.selectNoticeList();
    }

    public Notices selectNoticeByNoticeId(String noticeId) {
        return noticesMapper.selectNoticeByNoticeId(noticeId);
    }
}
