package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileUpdate {

    private String userId;
    private String userName;
    private String userPicture;
    private String picturePath;
    private String userMbti;
    private String userInfo;
    private String birthday;
    private String nickName;
}
