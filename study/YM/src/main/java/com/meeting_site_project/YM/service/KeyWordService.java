package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.repository.KeyWordRepository;
import com.meeting_site_project.YM.vo.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeyWordService {

    KeyWordRepository keyWordRepository;

    @Autowired
    public KeyWordService(KeyWordRepository keyWordRepository) {
        this.keyWordRepository = keyWordRepository;
    }

    public List<String> selectFirstKeyWords() {
        return keyWordRepository.selectFirstKeyWords();
    }

    public List<String> selectSecondKeyWords() {
        return keyWordRepository.selectSecondKeyWords();
    }

    public List<String> selectSecondKeyWordsByFirstKeyWord(String firstKeyword) {
        return keyWordRepository.selectSecondKeyWordsByFirstKeyWord(firstKeyword);
    }

    public void insertKeyWord(Keyword keyword) {
        keyWordRepository.insertKeyWord(keyword);
    }

    public String selectKeyWordBySecondKeyWord(String secondKeyword) {
        return keyWordRepository.selectKeyWordBySecondKeyWord(secondKeyword);
    }

    public void deleteKeyWordByFirstKeyWord(String firstKeyWord) {
        keyWordRepository.deleteKeyWordByFirstKeyWord(firstKeyWord);
    }

    public boolean deleteKeyWordBySecondKeyWord(String secondKeyword) {
        return keyWordRepository.deleteKeyWordBySecondKeyWord(secondKeyword);
    }
}
