package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;




@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private String userId;
    private String userName;
    private String userPicture;
    private String picturePath;
    private String userMbti;
    private String userPassword;
    private String userInfo;
    private int userAdmin;
    private String birthday;
    private String nickName;
    private String emailId;
    private String emailDomain;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date signDate;
    private int avgScore;

}
