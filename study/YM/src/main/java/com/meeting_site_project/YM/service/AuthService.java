package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.repository.MybatisRepository;
import com.meeting_site_project.YM.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    MybatisRepository mybatisRepository;

    // MybatisRepository를 주입받는 생성자
    @Autowired
    public AuthService(MybatisRepository mybatisRepository) {
        this.mybatisRepository = mybatisRepository;
    }

    // 아이디와 비밀번호를 받아서 해당 정보와 일치하는 회원을 조회하는 메서드
    public Member selectByIdPassword(String userId, String userPassword) {
        return mybatisRepository.selectByIdPassword(userId, userPassword);
    }


}