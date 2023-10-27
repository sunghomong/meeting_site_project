package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.repository.AskRepository;
import com.meeting_site_project.YM.repository.CommentRepository;
import com.meeting_site_project.YM.vo.AskContent;
import com.meeting_site_project.YM.vo.CommentAsk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class FAQservice {

    public static AskRepository askRepository;
    CommentRepository commentRepository;

    @Autowired
    public FAQservice(CommentRepository commentRepository, AskRepository askRepository) {
        this.commentRepository = commentRepository;
        FAQservice.askRepository = askRepository;
    }


    public List<AskContent> selectAskList() {
        return askRepository.selectAskList();
    }

    public static void updateAsk(AskContent askContent) {
        // 1. 데이터베이스로부터 이전 파일 경로를 검색
        AskContent existingAsk = askRepository.selectAskListByAskId(askContent.getAskId());

        // 2. 이전 파일 그대로 설정
        askContent.setAttachmentName(existingAsk.getAttachmentName());
        askContent.setAttachmentPath(existingAsk.getAttachmentPath());

        // 3. 업데이트 쿼리를 실행
        askRepository.updateAsk(askContent);
    }

    public static void updateAskWithAttachments(AskContent askContent, MultipartFile attachments) throws IOException {
        // 윈도우 전용은 // 으로 처리가 가능하지만 다양한 처리를 위해 separator을 사용
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\attachments";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + attachments.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        attachments.transferTo(saveFile);

        askContent.setAttachmentName(fileName);
        askContent.setAttachmentPath("/attachments/" + fileName);

        askRepository.updateAsk(askContent);
    }

    public AskContent selectAskListByAskId(String askId) {

        return askRepository.selectAskListByAskId(askId);
    }

    public void deleteAskByAskId(String askId) {
        askRepository.deleteAskByAskId(askId);
    }

    public void updateAskStatus(AskContent askContent) {
        askRepository.updateAskStatus(askContent);
    }

    public void insertCommentAsk(CommentAsk commentAsk) {
        commentRepository.insertCommentAsk(commentAsk);
    }


    public List<CommentAsk> selectCommentAskByAskId(String askId) {
        return commentRepository.selectCommentAsk(askId);
    }
}
