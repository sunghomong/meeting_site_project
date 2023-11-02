package com.meeting_site_project.YM.mapper;

import com.meeting_site_project.YM.vo.Keyword;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface KeyWordMapper {

    public List<String> selectFirstKeyWords();

    public List<String> selectSecondKeyWords();

    List<String> selectSecondKeyWordsByFirstKeyWord(String firstKeyword);

    void insertKeyWord(Keyword keyword);

    String selectKeyWordBySecondKeyWord(String secondKeyword);

    void deleteKeyWordByFirstKeyWord(String firstKeyWord);

    boolean deleteKeyWordBySecondKeyWord(String secondKeyword);
}
