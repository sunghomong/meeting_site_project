package com.meeting_site_project.YM.mapper;

import com.meeting_site_project.YM.vo.AskContent;
import com.meeting_site_project.YM.vo.JoinMember;
import com.meeting_site_project.YM.vo.Member;
import com.meeting_site_project.YM.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MemberMapper {

    @Select("select count(*) from member")
    public int getCnt();

    public int getCnt2();

    public void insertMember(JoinMember joinMember);

    public Member selectByIdPassword(LoginCommand loginCommand);

    public Member selectById(String userId);


    public List<Member> getMemberList();  //회원 전체 목록 조회

    Member selectMemberById(String userId); // 수정을 하기 위한 멤버 조회

    public Member selectByNickName(String nickName);

    public Member selectByEmail(String emailId, String emailDomain);

    public void profileUpdate(ProfileUpdate profileUpdate);

    public Member findId(FindId findId);

    public Member findPassword(FindPassword findPassword);

    public void changePassword(ChangePassword changePassword);
    public void updateMemberAdmin(HashMap<String, Object> parameters);

    public void deleteMemberById(String userId);

    public List<AskContent> selectAskList(); // 고객 문의 리스트 전체 조회

<<<<<<< HEAD
=======

>>>>>>> a7a8f0e698c59bf322c1eeaed80ef0f801f1616f
    public void insertAsk(AskContent askContent); // 고객 문의 등록 하기

    public void insertFirstMeeting(GroupInfo groupInfo);

    public GroupInfo selectByMeeting(int meeting);

    public int groupInfoSearchAll();

<<<<<<< HEAD
    List<GroupInfo> selectJoinList();
=======
    List<GroupInfo> selectOnedayGroupList(int groupType);


    List<GroupInfo> selectRegulardayGroupList(int groupType);


>>>>>>> a7a8f0e698c59bf322c1eeaed80ef0f801f1616f

    public Member selectByPassword(String userPassword, String userId);

    public AskContent selectAskDetailByAskId(String askId);

    List<Notices> selectNoticeList();

    List<Notices> selectNoticeListByUserId(String userId);

<<<<<<< HEAD
=======
    List<Keyword> selectFirstKeywordList();

    List<Keyword> selectSecondKeywordList(String firstKeyword);

    public void insertGroupByKeyword(GroupInfo groupInfo);
>>>>>>> a7a8f0e698c59bf322c1eeaed80ef0f801f1616f
}
