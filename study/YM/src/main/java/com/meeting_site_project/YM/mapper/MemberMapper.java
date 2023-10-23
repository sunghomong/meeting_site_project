package com.meeting_site_project.YM.mapper;

<<<<<<< HEAD
import com.meeting_site_project.YM.vo.AskContent;
import com.meeting_site_project.YM.vo.JoinMember;
import com.meeting_site_project.YM.vo.Member;
=======
import com.meeting_site_project.YM.vo.*;
>>>>>>> b36670d54222f2707da0c6bb65b4a9f3058fccbf
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

<<<<<<< HEAD
=======
import java.util.HashMap;
import java.util.List;

>>>>>>> 0159ee1ba674e273d64fb5cc57a3a50aba730741
@Mapper
public interface MemberMapper {

    @Select("select count(*) from member")
    public int getCnt();

    public int getCnt2();

    public void insertMember(JoinMember joinMember);

    public Member selectByIdPassword(String userId, String userPassword);

    public Member selectById(String userId);

<<<<<<< HEAD
=======

    public List<Member> getMemberList();  //회원 전체 목록 조회

    Member selectMemberById(String userId); // 수정을 하기 위한 멤버 조회

>>>>>>> 0159ee1ba674e273d64fb5cc57a3a50aba730741
    public Member selectByNickName(String nickName);

    public Member selectByEmail(String emailId, String emailDomain);

<<<<<<< HEAD
    public void profileUpdate(ProfileUpdate profileUpdate);

    public Member findId(FindId findId);

    public Member findPassword(FindPassword findPassword);

    public void changePassword(ChangePassword changePassword);
=======
    public void updateMemberAdmin(HashMap<String, Object> parameters);

    public void deleteMemberById(String userId);
<<<<<<< HEAD

    public List<AskContent> selectAskList(); // 고객 문의 리스트 전체 조회

    public void insertAsk(AskContent askContent);
=======
>>>>>>> 0159ee1ba674e273d64fb5cc57a3a50aba730741
>>>>>>> b36670d54222f2707da0c6bb65b4a9f3058fccbf
}
