package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.repository.MybatisRepository;
import com.meeting_site_project.YM.vo.ChangePassword;
import com.meeting_site_project.YM.vo.FindId;
import com.meeting_site_project.YM.vo.FindPassword;
import com.meeting_site_project.YM.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindAuthInfoService {

    MybatisRepository mybatisRepository;

    @Autowired
    public FindAuthInfoService(MybatisRepository mybatisRepository) {
        this.mybatisRepository = mybatisRepository;
    }

    public Member findId(FindId findId) {
        return mybatisRepository.findId(findId);
    }

    public Member findPassword(FindPassword findPassword) {
        return mybatisRepository.findPassword(findPassword);
    }

    public void changePassword(ChangePassword changePassword){mybatisRepository.changePassword(changePassword);};
}
