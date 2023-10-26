package com.meeting_site_project.YM.mapper;

import com.meeting_site_project.YM.vo.Notices;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticesMapper {

    public void insertNotice(Notices notices);


    List<Notices> selectNoticeListByUserId(String userId);

    List<Notices> selectNoticeList();

    Notices selectNoticeByNoticeId(String noticeId);
}
