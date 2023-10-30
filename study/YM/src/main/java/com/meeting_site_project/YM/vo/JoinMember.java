package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JoinMember {
    private String userId;
    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;
    private String userPicture;
    private String picturePath;
    private String userMbti;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String userPassword;
    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String confirmUserPassword;
    @NotBlank(message = "생일을 입력해주세요.")
    private String birthday;
    private String nickName;
    private String emailId;
    private String emailDomain;
    @NotBlank(message = "자기소개를 입력해주세요.")
    private String userInfo;


}
