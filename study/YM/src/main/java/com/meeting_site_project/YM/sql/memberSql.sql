-- 회원 정보 테이블 ------------------------------------
create table member (
userId  nvarchar2(100) primary key,
userName nvarchar2(100),
userPicture nvarchar2(100),
userMbti nvarchar2(10),
userPassword nvarchar2(100),
userInfo nvarchar2(500) default null,
userAdmin number default 0,
birthday nvarchar2(50),
nickName nvarchar2(50),
emailId nvarchar2(50),
emailDomain nvarchar2(50),
signDate date default sysdate
);
-- 회원 점수 테이블 ------------------------------------
create table userScore (
evaluatedUserId  nvarchar2(100),
userScore number,
evaluateUserId nvarchar2(100),
PRIMARY KEY (evaluatedUserId,evaluateUserId),
FOREIGN KEY (evaluatedUserId)
REFERENCES member(userId)
);
-- 모임 테이블 ------------------------------------
CREATE TABLE groupInfo (
groupId number PRIMARY KEY,
ownerUserId nvarchar2(30),
sidoName nvarchar2(30),
sigoonName nvarchar2(30),
groupName nvarchar2(30),
groupInfo nvarchar2(100),
groupNumberOfPeople number,
<<<<<<< HEAD
group_picture nvarchar2(100)
);

-- 고객 문의를 위한 테이블
CREATE TABLE ask (
    askId NUMBER PRIMARY KEY,  -- 고유한 문의 ID
    userId nvarchar2(100) REFERENCES member (userId),  -- 외래 키로 member 테이블의 user_id 참조
    subject nvarchar2(30),  -- 문의 주제
    content nvarchar2(500),  -- 문의 내용
    createDate DATE DEFAULT sysdate,  -- 문의 생성 일자
    status nvarchar2(20) DEFAULT '미해결',  -- 문의 상태 (예: 미해결, 처리 중, 완료)
    attachments nvarchar2(100) DEFAULT NULL,  -- 첨부 파일 정보
    comments nvarchar2(500) DEFAULT NULL,  -- 댓글 정보
    history nvarchar2(30) DEFAULT NULL -- 이력 정보 또는 변경 이력
=======
groupPicture nvarchar2(100),
groupPicturePath nvarchar2(300)
);
-- 그룹의 키워드 테이블 ------------------------------------
CREATE TABLE groupByKeyword(
groupId nvarchar2(30),
firstKeyword nvarchar2(30),
secondKeyword nvarchar2(30),
PRIMARY KEY (groupId),
FOREIGN KEY (groupId)
REFERENCES groupInfo(groupId)
);
-- 키워드 테이블 ------------------------------------
CREATE TABLE keyword (
firstKeyword nvarchar2(30),
secondKeyword nvarchar2(30) primary key
>>>>>>> b36670d54222f2707da0c6bb65b4a9f3058fccbf
);