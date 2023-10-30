-- 회원 정보 테이블 ------------------------------------
create table member (
userId  nvarchar2(100),
userName nvarchar2(100),
userPicture nvarchar2(100),
picturePath NVARCHAR2(500),
userMbti nvarchar2(10),
userPassword nvarchar2(100),
userInfo nvarchar2(500) default null,
userAdmin number default 0,
birthday nvarchar2(50),
nickName nvarchar2(50),
emailId nvarchar2(50),
emailDomain nvarchar2(50),
signDate date default sysdate,
avgScore number default 0,
CONSTRAINT member_pk PRIMARY KEY (userId)
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
groupId nvarchar2(50) PRIMARY KEY,
ownerUserId nvarchar2(30),
sidoName nvarchar2(30),
sigoonName nvarchar2(30),
groupName nvarchar2(30),
groupInfo nvarchar2(100),
groupNumberOfPeople number,
groupPicture nvarchar2(100),
groupPicturePath NVARCHAR2(500),
groupType number,
refDate DATE DEFAULT sysdate
);

-- 고객 문의를 위한 테이블
CREATE TABLE askList (
    askId NVARCHAR2(40),  -- 고유한 문의 ID (기본키)
    userId nvarchar2(100),  -- 외래 키로 member 테이블의 user_id 참조
    subject nvarchar2(30),  -- 문의 주제
    content nvarchar2(500),  -- 문의 내용
    createDate DATE DEFAULT sysdate,  -- 문의 생성 일자
    status nvarchar2(20) DEFAULT '미해결',  -- 문의 상태 (예: 미해결, 처리 중, 완료)
    attachmentName nvarchar2(100) DEFAULT NULL, -- 첨부 파일 이름
    attachmentPath nvarchar2(300) DEFAULT NULL, -- 첨부 파일 경로
    comments nvarchar2(500) DEFAULT NULL,  -- 댓글 정보
    history nvarchar2(30) DEFAULT NULL, -- 이력 정보 또는 변경 이력
    CONSTRAINT askList_FK_PK_userId FOREIGN KEY(userId) REFERENCES member(userId)
    ON DELETE CASCADE,
    CONSTRAINT askList_Pk PRIMARY KEY (askId)
);

-- 그룹의 키워드 테이블 ------------------------------------
CREATE TABLE groupByKeyword(
groupId nvarchar2(50),
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
);
-- 공지사항 테이블 ----------------------------------------
CREATE TABLE notices (
noticeId NVARCHAR2(40),  -- 고유한 공지 사항 ID (기본키)
userId nvarchar2(100),  -- 외래 키로 member 테이블의 user_id 참조
title nvarchar2(30), -- 공지 사항의 제목
content nvarchar2(500), -- 공지 사항의 내용
createDate DATE DEFAULT sysdate, -- 생성 일자
attachmentName nvarchar2(100) DEFAULT NULL, -- 첨부 파일 이름
attachmentPath nvarchar2(300) DEFAULT NULL, -- 첨부 파일 경로
CONSTRAINT notices_FK_PK_userId FOREIGN KEY(userId) REFERENCES member(userId)
ON DELETE CASCADE,
CONSTRAINT notices_PK PRIMARY KEY (noticeId)
);