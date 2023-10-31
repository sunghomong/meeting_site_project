package com.meeting_site_project.YM.repository;


import com.meeting_site_project.YM.mapper.KeyWordMapper;
import com.meeting_site_project.YM.vo.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KeyWordRepository {

    KeyWordMapper keyWordMapper;

    @Autowired
    public KeyWordRepository(KeyWordMapper keyWordMapper) {
        this.keyWordMapper = keyWordMapper;
    }

    public List<String> selectFirstKeyWords() {
        return keyWordMapper.selectFirstKeyWords();
    }

    public List<String> selectSecondKeyWords() {
        return keyWordMapper.selectSecondKeyWords();
    }

    public List<String> selectSecondKeyWordsByFirstKeyWord(String firstKeyword) {
        return keyWordMapper.selectSecondKeyWordsByFirstKeyWord(firstKeyword);
    }

    public void insertKeyWord(Keyword keyword) {
        keyWordMapper.insertKeyWord(keyword);
    }

    public String selectKeyWordBySecondKeyWord(String secondKeyword) {
        return keyWordMapper.selectKeyWordBySecondKeyWord(secondKeyword);
    }

    public void deleteKeyWordByFirstKeyWord(String firstKeyWord) {
        keyWordMapper.deleteKeyWordByFirstKeyWord(firstKeyWord);
    }

    public boolean deleteKeyWordBySecondKeyWord(String secondKeyword) {
        return keyWordMapper.deleteKeyWordBySecondKeyWord(secondKeyword);
    }
}
