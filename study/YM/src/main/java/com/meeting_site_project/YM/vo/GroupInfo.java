package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupInfo {
    private String groupId; // 그룹 id
    private String ownerUserId; // 방장
    private String sidoName;
    private String sigoonName;
    private String groupName; // 그룹 이름
    private String groupInfo;
    private int groupNumberOfPeople; // 제한수
    private String groupPicture;
    private String groupPicturePath;
    private int groupType;
    private Date regDate;
    private String firstKeyword;
    private String secondKeyword;
}
