package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.repository.MybatisRepository;
import com.meeting_site_project.YM.vo.GroupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class MeetingService {


    MybatisRepository mybatisRepository;

    @Autowired
    public MeetingService(MybatisRepository mybatisRepository) {
        this.mybatisRepository = mybatisRepository;
    }

    public void insertFirstMeeting(GroupInfo groupInfo, MultipartFile file) throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        groupInfo.setGroupPicture(fileName);
        groupInfo.setGroupPicturePath("/files/" + fileName);

        mybatisRepository.insertFirstMeeting(groupInfo);
    }

    public List<GroupInfo> selectJoinList() {

        return mybatisRepository.selectJoinList();
    }

    // public GroupInfo selectByMeeting(int meeting) {return mybatisRepository.selectByMeeting(meeting);
    // }
//    public GroupInfo selectGroupInById(String groupId) {
//        return mybatisRepository.selectGroupInById(groupId);
//    }


}
