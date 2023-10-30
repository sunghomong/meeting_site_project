package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.repository.MybatisRepository;
import com.meeting_site_project.YM.vo.GroupInfo;
import com.meeting_site_project.YM.vo.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    public GroupInfo selectGroupInfoById(String groupId) {
        return mybatisRepository.selectGroupInfoById(groupId);
    }

    public void insertGroupByKeyword(GroupInfo groupInfo) {
        mybatisRepository.insertGroupByKeyword(groupInfo);
    }

    public List<Keyword> selectKeywords() {
        return mybatisRepository.selectKeywords();
    }

    public void updateMeeting(GroupInfo groupInfo, MultipartFile picture) throws IOException {
        if (!picture.isEmpty()) {
            GroupInfo group = mybatisRepository.selectGroupInfoById(groupInfo.getGroupId());
            if (group.getGroupPicturePath() != null) {
                // 프로필 이미지 파일의 전체 경로를 구성합니다.
                String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static" + group.getGroupPicturePath();

                // File 객체를 생성하여 파일을 나타냅니다.
                File oldFile = new File(filePath);

                // 파일이 실제로 존재하는지 확인합니다.
                if (oldFile.exists()) {
                    // 파일을 삭제합니다.
                    oldFile.delete();
                }
            }

            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\groupFiles";

            UUID uuid = UUID.randomUUID();

            String fileName = uuid + "_" + picture.getOriginalFilename();

            File saveFile = new File(projectPath, fileName);

            picture.transferTo(saveFile);

            groupInfo.setGroupPicture(fileName);
            groupInfo.setGroupPicturePath("/groupFiles/" + fileName);


            mybatisRepository.insertFirstMeeting(groupInfo);

        }
        else if (picture.isEmpty()) {
            groupInfo.setGroupPicture(null);
            groupInfo.setGroupPicturePath(null);
        }
        mybatisRepository.updateMeeting(groupInfo);
    }

    public void deleteGroup(String groupId) {
        mybatisRepository.deleteGroup(groupId);
    }

}
