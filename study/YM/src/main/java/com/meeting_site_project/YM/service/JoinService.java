package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.repository.MybatisRepository;
import com.meeting_site_project.YM.vo.AskContent;
import com.meeting_site_project.YM.vo.JoinMember;
import com.meeting_site_project.YM.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class JoinService {

    MybatisRepository mybatisRepository;

    // MybatisRepository를 주입받는 생성자
    @Autowired
    public JoinService(MybatisRepository mybatisRepository) {
        this.mybatisRepository = mybatisRepository;
    }

    // 회원 정보를 저장하는 메서드
    public void insertMember(JoinMember joinMember, MultipartFile picture) throws Exception{
        if (!picture.isEmpty()) {
            String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

            UUID uuid = UUID.randomUUID();



            String fileName = uuid + "_" + picture.getOriginalFilename();

            File saveFile = new File(projectPath, fileName);

            picture.transferTo(saveFile);

            joinMember.setUserPicture(fileName);
            joinMember.setPicturePath("/files/" + fileName);

        }
        mybatisRepository.insertMember(joinMember);
    }

    // 아이디로 회원을 조회하는 메서드
    public Member selectById(String userId) {
        return mybatisRepository.selectById(userId);
    }

    // 닉네임으로 회원을 조회하는 메서드
    public Member selectByNickName(String nickName) {
        return mybatisRepository.selectByNickName(nickName);
    }

    // 이메일 아이디와 도메인으로 회원을 조회하는 메서드
    public Member selectByEmail(String emailId, String emailDomain) {
        return mybatisRepository.selectByEmail(emailId, emailDomain);
    }

    public void insertAsk(AskContent askContent, MultipartFile attachments) throws Exception{

        // 윈도우 전용은 // 으로 처리가 가능하지만 다양한 처리를 위해 separator을 사용
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\attachments";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + attachments.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        attachments.transferTo(saveFile);

        askContent.setAttachmentName(fileName);
        askContent.setAttachmentPath("/attachments/" + fileName);

        mybatisRepository.insertAsk(askContent);
    }
}
