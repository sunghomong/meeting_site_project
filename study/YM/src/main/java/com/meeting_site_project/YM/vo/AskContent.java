package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AskContent {
    private String askId; // 문의 ID
    private String userId; // 회원 ID
    private String subject; // 글 제목
    private String content; // 글 내용
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createDate; // 글 생성 일자
    private String status; // 문의 상태 (미해결, 처리 중, 완료 등)
    private String attachmentName; // 파일 이름
    private String attachmentPath; // 파일 경로
    private String comments; // 댓글 정보 (외래 키로 사용 예정)
    private String history; // 이력 정보 (수정 사항)
}
