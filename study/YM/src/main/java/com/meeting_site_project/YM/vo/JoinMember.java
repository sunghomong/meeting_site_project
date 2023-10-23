package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JoinMember {
    private String UserId;
    private String userName;
    private String userPicture;
    private String picturePath;
    private String userMbti;
    private String userPassword;
    private String confirmUserPassword;
    private String birthday;
    private String nickName;
    private String emailId;
    private String emailDomain;


}
