package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.repository.MybatisRepository;
import com.meeting_site_project.YM.vo.Member;
import com.meeting_site_project.YM.vo.ProfileUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class ProfileService {

    MybatisRepository mybatisRepository;

    // MybatisRepository를 주입받는 생성자
    @Autowired
    public ProfileService(MybatisRepository mybatisRepository) {
        this.mybatisRepository = mybatisRepository;
    }

    public Member selectById(String userId) {
        return mybatisRepository.selectById(userId);
    }

    public void ProfileUpdate(ProfileUpdate profileUpdate, MultipartFile picture) throws Exception{

       if (picture != null) {

            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

            UUID uuid = UUID.randomUUID();

            String fileName = uuid + "_" + picture.getOriginalFilename();

            File saveFile = new File(projectPath, fileName);

           picture.transferTo(saveFile);

            profileUpdate.setUserPicture(fileName);
            profileUpdate.setPicturePath("/files/" + fileName);

        }
            mybatisRepository.profileUpdate(profileUpdate);
    }


}
