package com.meeting_site_project.YM.repository;

import com.meeting_site_project.YM.mapper.ChatMapper;
import com.meeting_site_project.YM.mapper.MemberMapper;
import com.meeting_site_project.YM.vo.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

@org.springframework.stereotype.Repository
public class MybatisRepository implements Repository {

    MemberMapper memberMapper;
    ChatMapper chatMapper;

    @Autowired
    public MybatisRepository(MemberMapper memberMapper,ChatMapper chatMapper) {
        this.memberMapper = memberMapper;
        this.chatMapper = chatMapper;
    }

    // JoinMember 객체를 받아서 데이터베이스에 회원 정보를 추가하는 메서드
    public void insertMember(JoinMember joinMember) {
        memberMapper.insertMember(joinMember);
    }

    // 아이디와 비밀번호를 받아서 해당 정보와 일치하는 회원을 조회하는 메서드
    public Member selectByIdPassword(LoginCommand loginCommand) {
        return memberMapper.selectByIdPassword(loginCommand);
    }

    // 아이디를 받아서 해당하는 회원을 조회하는 메서드
    public Member selectById(String userId) {
        return memberMapper.selectById(userId);
    }


    public List<Member> getMemberListWithPaging(Criteria cri ) { // 전체 회원 조회를 위한 (관리자)
        return memberMapper.getMemberListWithPaging(cri);
    }

    public int getTotal() {
        return memberMapper.getTotal();
    }

    public Member selectMemberById(String userId) {
        return memberMapper.selectMemberById(userId);
    }


    // 닉네임을 받아서 해당하는 회원을 조회하는 메서드
    public Member selectByNickName(String nickName) {
        return memberMapper.selectByNickName(nickName);
    }

    // 이메일 아이디와 도메인을 받아서 해당하는 회원을 조회하는 메서드
    public Member selectByEmail(String emailId, String emailDomain) {

        return memberMapper.selectByEmail(emailId, emailDomain);
    }

    public Member selectByPassword(String userPassword, String userId) {
        return memberMapper.selectByPassword(userPassword, userId);
    }
    public void profileUpdate(ProfileUpdate profileUpdate) {
        memberMapper.profileUpdate(profileUpdate);
    }

    public Member findId(FindId findId) {
        return memberMapper.findId(findId);
    }

    public Member findPassword(FindPassword findPassword) {
        return memberMapper.findPassword(findPassword);
    }

    public void changePassword(ChangePassword changePassword) {
        memberMapper.changePassword(changePassword);
    }

    public void updateMemberAdmin(HashMap<String, Object> parameters) {
        memberMapper.updateMemberAdmin(parameters);
    }

    public void deleteMemberById(String userId) {
        memberMapper.deleteMemberById(userId);

    }

    public void deleteGroup(String groupId) {
        memberMapper.deleteGroup(groupId);
    }

    // 고객 문의 서비스 구현

    public void insertAsk(AskContent askContent) {
        memberMapper.insertAsk(askContent);
    }

    public void updateGroupKeyword(GroupInfo groupInfo) {
        memberMapper.updateGroupKeyword(groupInfo);
    }

    public AskContent selectAskDetailByAskId(String askId) {
        return memberMapper.selectAskDetailByAskId(askId);
    }

    public List<Notices> selectNoticeList() {
        return memberMapper.selectNoticeList();
    }


    public void insertFirstMeeting(GroupInfo groupInfo) {
        memberMapper.insertFirstMeeting(groupInfo);
    }

    public GroupInfo selectByMeeting(int meeting) {
        return memberMapper.selectByMeeting(meeting);
    }

    public List<GroupInfo> selectOnedayGroupList(int groupType) {
        return memberMapper.selectOnedayGroupList(groupType);
    }

    public List<Keyword> selectKeywords () {
        return memberMapper.selectKeywords();
    }

    public GroupInfo selectGroupInfoById(String groupId) {
        return memberMapper.selectGroupInfoById(groupId);
    }

    public List<GroupInfo> selectRegulardayGroupList(int groupType){
        return memberMapper.selectRegulardayGroupList(groupType);
    };

    public void updateMeeting(GroupInfo groupInfo) {
        memberMapper.updateMeeting(groupInfo);
    }
    public List<Keyword> selectFirstKeywordList() {
        return memberMapper.selectFirstKeywordList();
    }

    public List<Keyword> selectSecondKeywordList(String firstKeyword) {
        return memberMapper.selectSecondKeywordList(firstKeyword);
    }

    public void insertGroupByKeyword(GroupInfo groupInfo) {
        memberMapper.insertGroupByKeyword(groupInfo);
    }

    public List<Member> getSearchList (String userId) {
        return memberMapper.getSearchList(userId);
    }

    public List<GroupInfo> selectOnedayKeywordByGroupList (String firstKeyword, int groupType) {
        return memberMapper.selectOnedayKeywordByGroupList(firstKeyword, groupType);
    }


    public void updateGroupOwnerIdByUserIdAndGroupId(String groupId, String userId) {
        memberMapper.updateGroupOwnerIdByUserIdAndGroupId(groupId,userId);
        // group방장 업데이트 동시에 채팅방 방장도 업데이트
        chatMapper.updateChatRoomOwnerIdByUserIdAndGroupId(groupId,userId);
    }
}
