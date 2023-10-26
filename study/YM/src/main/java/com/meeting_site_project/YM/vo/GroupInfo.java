package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupInfo {
    private String groupId;
    private String ownerUserId;
    private String sidoName;
    private String sigoonName;
    private String groupName;
    private String groupInfo;
    private int groupNumberOfPeople;
    private String groupPicture;
    private String groupPicturePath;
    private int groupType;
    private Date regDate;
    private String firstKeyword;
    private String secondKeyword;
}
