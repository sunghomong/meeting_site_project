package com.meeting_site_project.YM.service;

import com.meeting_site_project.YM.repository.MybatisRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteService {

    MybatisRepository mybatisRepository;

    public DeleteService(MybatisRepository mybatisRepository) {
        this.mybatisRepository = mybatisRepository;
    }


    public void deleteMemberById(String userId) {
        mybatisRepository.deleteMemberById(userId);
    }
}
