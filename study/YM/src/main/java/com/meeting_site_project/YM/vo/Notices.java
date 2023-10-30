package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notices {

    private String noticeId; // 공지 사항 ID (기본키)
    private String userId; // 외래 키로 member 테이블의 user_id 참조
    private String title; // 공지 사항의 제목
    private String content; // 공지 사항의 내용

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate; // 생성 일자

    private String attachmentName; // 첨부 파일 이름
    private String attachmentPath; // 첨부 파일 경로
}