package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.repository.MybatisRepository;
import com.meeting_site_project.YM.vo.GroupInfo;
import com.meeting_site_project.YM.vo.Keyword;
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

    public void insertFirstMeeting(GroupInfo groupInfo, MultipartFile picture) throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\groupFiles";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + picture.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        picture.transferTo(saveFile);

        groupInfo.setGroupPicture(fileName);
        groupInfo.setGroupPicturePath("/groupFiles/" + fileName);

        String groupId = UUID.randomUUID().toString().replaceAll("-", "");
        groupInfo.setGroupId(groupId);

        mybatisRepository.insertFirstMeeting(groupInfo);
    }

    public List<GroupInfo> selectOnedayGroupList(int groupType) {

        return mybatisRepository.selectOnedayGroupList(groupType);
    }

    public List<GroupInfo> selectRegulardayGroupList(int groupType) {

        return mybatisRepository.selectRegulardayGroupList(groupType);
    }

    public List<Keyword> selectFirstKeywordList() {
        return mybatisRepository.selectFirstKeywordList();
    }

    public List<Keyword> selectSecondKeywordList(String firstKeyword) {
        return mybatisRepository.selectSecondKeywordList(firstKeyword);
    }
    // public GroupInfo selectByMeeting(int meeting) {return mybatisRepository.selectByMeeting(meeting);
    // }
//    public GroupInfo selectGroupInById(String groupId) {
//        return mybatisRepository.selectGroupInById(groupId);
//    }

    public void insertGroupByKeyword(GroupInfo groupInfo) {
        mybatisRepository.insertGroupByKeyword(groupInfo);
    }

}
