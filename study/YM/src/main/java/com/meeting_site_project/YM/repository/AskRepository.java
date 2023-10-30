package com.meeting_site_project.YM.repository;

import com.meeting_site_project.YM.mapper.AskMapper;
import com.meeting_site_project.YM.vo.AskContent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Repository
public class AskRepository {

    AskMapper askMapper;

    @Autowired
    public AskRepository(AskMapper askMapper) {
        this.askMapper = askMapper;
    }

    public List<AskContent> selectAskList() {
        return askMapper.selectAskList();
    }

    public AskContent selectAskListByAskId(String askId) {
        return askMapper.selectAskListByAskId(askId);
    }

    public void updateAsk(AskContent askContent) {
        askMapper.updateAsk(askContent);
    }

    public void deleteAskByAskId(String askId) {
        askMapper.deleteAskByAskId(askId);
    }

    public void updateAskStatus(AskContent askContent) {
        askMapper.updateAskStatus(askContent);
    }
}
