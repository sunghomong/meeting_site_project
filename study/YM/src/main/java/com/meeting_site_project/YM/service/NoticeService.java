package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.repository.NoticesRepository;
import com.meeting_site_project.YM.vo.Notices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    NoticesRepository noticesRepository;

    @Autowired
    public NoticeService(NoticesRepository noticesRepository) {
        this.noticesRepository = noticesRepository;
    }


    public List<Notices> selectNoticeList() {
        return noticesRepository.selectNoticeList();
    }

    public Notices selectNoticeByNoticeId(String noticeId) {
        return noticesRepository.selectNoticeByNoticeId(noticeId);
    }
}
