package com.meeting_site_project.YM.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ConfirmUserPassword {
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String userPassword;

}
