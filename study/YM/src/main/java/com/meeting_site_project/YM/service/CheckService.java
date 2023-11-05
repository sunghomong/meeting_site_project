package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.repository.MybatisRepository;
import com.meeting_site_project.YM.vo.AskContent;
import com.meeting_site_project.YM.vo.Criteria;
import com.meeting_site_project.YM.vo.Member;
import com.meeting_site_project.YM.vo.Notices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckService {

    //DI 의존성 주입 생성자 메서드 주입방식
    MybatisRepository mybatisRepository;

    @Autowired
    public CheckService(MybatisRepository mybatisRepository) {
        this.mybatisRepository = mybatisRepository;
    }

    public List<Member> getMemberListWithPaging(Criteria cri) { // 전체 회원 목록 조회
        return mybatisRepository.getMemberListWithPaging(cri);
    }

    public int getTotal() {
        return mybatisRepository.getTotal();
    }

    public Member selectMemberById(String userId) {
        return mybatisRepository.selectMemberById(userId);
    }



    public AskContent selectAskDetailByAskId(String askId) {
        return mybatisRepository.selectAskDetailByAskId(askId);

    }

    public List<Notices> selectNoticeList() {
        return mybatisRepository.selectNoticeList();
    }


    public List<Member> getSearchList(String userId) {
        return mybatisRepository.getSearchList(userId);
    }
}
