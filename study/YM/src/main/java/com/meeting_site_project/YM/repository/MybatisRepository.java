package com.meeting_site_project.YM.repository;

import com.meeting_site_project.YM.mapper.MemberMapper;
import com.meeting_site_project.YM.vo.*;
import org.springframework.beans.factory.annotation.Autowired;

<<<<<<< HEAD
=======
import java.util.HashMap;
import java.util.List;

>>>>>>> 0159ee1ba674e273d64fb5cc57a3a50aba730741
@org.springframework.stereotype.Repository
public class MybatisRepository implements Repository {

    MemberMapper memberMapper;

    @Autowired
    public MybatisRepository(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    // JoinMember 객체를 받아서 데이터베이스에 회원 정보를 추가하는 메서드
    public void insertMember(JoinMember joinMember) {
        memberMapper.insertMember(joinMember);
    }

    // 아이디와 비밀번호를 받아서 해당 정보와 일치하는 회원을 조회하는 메서드
    public Member selectByIdPassword(String userId, String userPassword) {
        return memberMapper.selectByIdPassword(userId, userPassword);
    }

    // 아이디를 받아서 해당하는 회원을 조회하는 메서드
    public Member selectById(String userId) {
        return memberMapper.selectById(userId);
    }

<<<<<<< HEAD
=======

    public List<Member> getMemberList() { // 전체 회원 조회를 위한 (관리자)
        return memberMapper.getMemberList();
    }

    public Member selectMemberById(String userId) {
        return memberMapper.selectMemberById(userId);
    }


>>>>>>> 0159ee1ba674e273d64fb5cc57a3a50aba730741
    // 닉네임을 받아서 해당하는 회원을 조회하는 메서드
    public Member selectByNickName(String nickName) {
        return memberMapper.selectByNickName(nickName);
    }

    // 이메일 아이디와 도메인을 받아서 해당하는 회원을 조회하는 메서드
    public Member selectByEmail(String emailId, String emailDomain) {
        return memberMapper.selectByEmail(emailId, emailDomain);
    }

<<<<<<< HEAD
    public void profileUpdate(ProfileUpdate profileUpdate) {
        memberMapper.profileUpdate(profileUpdate);
    }

    public Member findId(FindId findId) {
        return memberMapper.findId(findId);
    }

    public Member findPassword(FindPassword findPassword) {
        return memberMapper.findPassword(findPassword);
    }

    public void changePassword(ChangePassword changePassword ) {memberMapper.changePassword(changePassword);}
}
=======
    public void updateMemberAdmin(HashMap<String, Object> parameters) {
        memberMapper.updateMemberAdmin(parameters);
    }

    public void deleteMemberById(String userId) {
        memberMapper.deleteMemberById(userId);

    }
}

>>>>>>> 0159ee1ba674e273d64fb5cc57a3a50aba730741
