package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.repository.MybatisRepository;
import com.meeting_site_project.YM.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UpdateService {

    MybatisRepository mybatisRepository;

    @Autowired
    public UpdateService(MybatisRepository mybatisRepository) {
        this.mybatisRepository = mybatisRepository;
    }


    public void updateMemberAdmin(HashMap<String, Object> parameters) {
        mybatisRepository.updateMemberAdmin(parameters);
    }
}
