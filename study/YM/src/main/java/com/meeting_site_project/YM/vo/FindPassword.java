package com.meeting_site_project.YM.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindPassword {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;
    @NotBlank(message = "이메일을 입력해주세요.")
    private String emailId;
    @NotBlank(message = "이메일 도메인을 입력해주세요.")
    private String emailDomain;
}
