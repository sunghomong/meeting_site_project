package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {

    private String userId;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String userPassword;
    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String confirmUserPassword;

}
