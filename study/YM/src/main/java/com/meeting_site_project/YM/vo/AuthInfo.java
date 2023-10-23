package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthInfo {

    private String userId;
    private String userPassword;
    private String userName;
    private String nickName;
    private String userPicture;
    private String picturePath;
    private int userAdmin;
}
