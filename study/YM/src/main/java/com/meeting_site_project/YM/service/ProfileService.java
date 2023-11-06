package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.controller.LoginController;
import com.meeting_site_project.YM.repository.MybatisRepository;
import com.meeting_site_project.YM.vo.AuthInfo;
import com.meeting_site_project.YM.vo.Member;
import com.meeting_site_project.YM.vo.ProfileUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
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

    public void ProfileUpdate(ProfileUpdate profileUpdate, MultipartFile picture, HttpSession session) throws Exception {

        if (!picture.isEmpty()) {
            AuthInfo authInfo = (AuthInfo) session.getAttribute(LoginController.SessionConst.LOGIN_MEMBER);
            if (authInfo.getPicturePath() != null) {
                String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static" + authInfo.getPicturePath();

                // File 객체를 생성하여 파일을 나타냄
                File oldFile = new File(filePath);

                if (oldFile.exists()) {
                    // 기존 이미지 파일을 삭제
                    oldFile.delete();
                }
            }
            // 이미지 저장 경로
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

            UUID uuid = UUID.randomUUID();

            String fileName = uuid + "_" + picture.getOriginalFilename();

            File saveFile = new File(projectPath, fileName);

            picture.transferTo(saveFile);

            profileUpdate.setUserPicture(fileName);
            profileUpdate.setPicturePath("/files/" + fileName);

        } else if (picture.isEmpty()) {
            profileUpdate.setUserPicture(null);
            profileUpdate.setPicturePath(null);
        }
        mybatisRepository.profileUpdate(profileUpdate);
    }


    public Member selectByPassword(String userPassword, String userId) {
        return mybatisRepository.selectByPassword(userPassword, userId);
    }
}
