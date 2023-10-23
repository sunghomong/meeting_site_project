package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.repository.MybatisRepository;
import com.meeting_site_project.YM.vo.AskContent;
import com.meeting_site_project.YM.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckService {

    //DI 의존성 주입 생성자 메서드 주입방식
    MybatisRepository mybatisRepository;

    @Autowired
    public CheckService(MybatisRepository mybatisRepository) {
        this.mybatisRepository = mybatisRepository;
    }

    public List<Member> findMembers() { // 전체 회원 목록 조회
        return mybatisRepository.getMemberList();
    }

    public Member selectMemberById(String userId) {
        return mybatisRepository.selectMemberById(userId);
    }


    public List<AskContent> selectAskList() {
        return mybatisRepository.selectAskList();
    }
}
