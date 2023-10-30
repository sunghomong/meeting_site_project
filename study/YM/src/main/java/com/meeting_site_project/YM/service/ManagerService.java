package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.repository.NoticesRepository;
import com.meeting_site_project.YM.vo.Notices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ManagerService {

    NoticesRepository noticesRepository;

    @Autowired
    public ManagerService(NoticesRepository noticesRepository) {
        this.noticesRepository = noticesRepository;
    }

    public List<Notices> selectNoticeListByUserId(String userId) {
        return noticesRepository.selectNoticeListByUserId(userId);
    }

    public void insertNotice(Notices notices, MultipartFile attachment) throws IOException {

        // 윈도우 전용은 // 으로 처리가 가능하지만 다양한 처리를 위해 separator을 사용
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\attachments";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + attachment.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        attachment.transferTo(saveFile);

        notices.setAttachmentName(fileName);
        notices.setAttachmentPath("/attachments/" + fileName);

        noticesRepository.insertNotice(notices);

    }
}
