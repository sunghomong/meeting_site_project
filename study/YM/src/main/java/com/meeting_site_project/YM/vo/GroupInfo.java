package com.meeting_site_project.YM.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
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
    private Timestamp groupTime;
    private int meeting;
}
