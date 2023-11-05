package com.meeting_site_project.YM.mapper;

import com.meeting_site_project.YM.vo.AskContent;
import com.meeting_site_project.YM.vo.JoinMember;
import com.meeting_site_project.YM.vo.Member;
import com.meeting_site_project.YM.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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


    public List<Member> getMemberListWithPaging(Criteria cri);
    public int getTotal();

    Member selectMemberById(String userId); // 수정을 하기 위한 멤버 조회

    public Member selectByNickName(String nickName);

    public Member selectByEmail(@Param("emailId") String emailId,@Param("emailDomain") String emailDomain);

    public void profileUpdate(ProfileUpdate profileUpdate);

    public Member findId(FindId findId);

    public Member findPassword(FindPassword findPassword);

    public void changePassword(ChangePassword changePassword);
    public void updateMemberAdmin(HashMap<String, Object> parameters);

    public void deleteMemberById(String userId);



    public void insertAsk(AskContent askContent); // 고객 문의 등록 하기

    public void insertFirstMeeting(GroupInfo groupInfo);

    public GroupInfo selectByMeeting(int meeting);

    public int groupInfoSearchAll();

    List<GroupInfo> selectOnedayGroupList(int groupType);


    List<GroupInfo> selectRegulardayGroupList(int groupType);

    public GroupInfo selectGroupInfoById(String groupId);

    List<Keyword> selectKeywords();

    public void updateMeeting(GroupInfo groupInfo);

    public Member selectByPassword(@Param("userPassword") String userPassword, @Param("userId") String userId);

    public AskContent selectAskDetailByAskId(String askId);

    List<Notices> selectNoticeList();


    List<Keyword> selectFirstKeywordList();

    List<Keyword> selectSecondKeywordList(String firstKeyword);

    List<GroupInfo> selectOnedayKeywordByGroupList(@Param("firstKeyword") String firstKeyword,@Param("groupType") int groupType);

    public void updateGroupKeyword(GroupInfo groupInfo);

    public void insertGroupByKeyword(GroupInfo groupInfo);

    public void deleteGroup(String groupId);

    public List<Member> getSearchList(String userId);

    void updateGroupOwnerIdByUserIdAndGroupId(@Param("groupId") String groupId, @Param("userId") String userId);
}
