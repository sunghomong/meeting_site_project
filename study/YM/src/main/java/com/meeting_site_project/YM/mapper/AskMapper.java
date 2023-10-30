package com.meeting_site_project.YM.mapper;

import com.meeting_site_project.YM.vo.AskContent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface AskMapper {

    public List<AskContent> selectAskList(); // 고객 문의 리스트 전체 조회

    AskContent selectAskListByAskId(String askId);

    public void updateAsk(AskContent askContent);

    public void deleteAskByAskId(String askId);

    public void updateAskStatus(AskContent askContent);
}
