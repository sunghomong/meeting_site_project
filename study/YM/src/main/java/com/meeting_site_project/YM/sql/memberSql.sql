-- 회원 정보 테이블 ------------------------------------
create table member (
userId varchar(20),
userName NVARCHAR2(20),
userPicture NVARCHAR2(100),
picturePath NVARCHAR2(500),
userMbti NVARCHAR2(10),
userPassword NVARCHAR2(100),
userInfo NVARCHAR2(500) default null,
userAdmin number default 0,
birthday NVARCHAR2(50),
nickName NVARCHAR2(50),
emailId NVARCHAR2(50),
emailDomain NVARCHAR2(50),
signDate date default sysdate
CONSTRAINT member_pk PRIMARY KEY (userId)
);

-- 모임 테이블 ------------------------------------
CREATE TABLE groupInfo (
groupId NVARCHAR2(50),
ownerUserId VARCHAR(20),
sidoName NVARCHAR2(30),
sigoonName NVARCHAR2(30),
groupName NVARCHAR2(30),
groupInfo NVARCHAR2(2000),
groupNumberOfPeople NUMBER, -- 제한수
groupPicture NVARCHAR2(100),
groupPicturePath NVARCHAR2(500),
groupType number,
regDate DATE DEFAULT sysdate,
CONSTRAINT groupInfo_Pk PRIMARY KEY (groupId),
CONSTRAINT groupInfo_FK_PK_ownerUserId FOREIGN KEY(ownerUserId) REFERENCES member(userId)
ON DELETE CASCADE
);

-- 고객 문의를 위한 테이블
CREATE TABLE askList (
    askId NVARCHAR2(40),  -- 고유한 문의 ID (기본키)
    userId VARCHAR(20),  -- 외래 키로 member 테이블의 user_id 참조
    subject NVARCHAR2(50),  -- 문의 주제
    content NVARCHAR2(500),  -- 문의 내용
    createDate DATE DEFAULT sysdate,  -- 문의 생성 일자
    status NVARCHAR2(20) DEFAULT '미해결',  -- 문의 상태 (예: 미해결, 처리 중, 완료)
    attachmentName NVARCHAR2(100) DEFAULT NULL, -- 첨부 파일 이름
    attachmentPath NVARCHAR2(500) DEFAULT NULL, -- 첨부 파일 경로
    comments NVARCHAR2(500) DEFAULT NULL,  -- 댓글 정보
    history NVARCHAR2(30) DEFAULT NULL, -- 이력 정보 또는 변경 이력
    CONSTRAINT askList_FK_PK_userId FOREIGN KEY(userId) REFERENCES member(userId)
    ON DELETE CASCADE,
    CONSTRAINT askList_Pk PRIMARY KEY (askId)
);

-- 그룹의 키워드 테이블 ------------------------------------
CREATE TABLE groupByKeyword(
groupId NVARCHAR2(50),
firstKeyword NVARCHAR2(20),
secondKeyword NVARCHAR2(20),
CONSTRAINT groupByKeyword_FK_PK_secondKeyword FOREIGN KEY(secondKeyword) REFERENCES keyword (secondKeyword)
ON DELETE CASCADE,
CONSTRAINT groupByKeyword_FK_PK_groupId FOREIGN KEY(groupId) REFERENCES groupInfo (groupId)
ON DELETE CASCADE
);
-- 키워드 테이블 ------------------------------------
CREATE TABLE keyword (
firstKeyword nvarchar2(30),
secondKeyword nvarchar2(30) NOT NULL,
CONSTRAINT keyword_Pk PRIMARY KEY (secondKeyword)
);
-- 공지사항 테이블 ----------------------------------------
CREATE TABLE notices (
noticeId NVARCHAR2(40),  -- 고유한 공지 사항 ID (기본키)
userId VARCHAR(20),  -- 외래 키로 member 테이블의 userId 참조
title NVARCHAR2(30), -- 공지 사항의 제목
content NVARCHAR2(500), -- 공지 사항의 내용
createDate DATE DEFAULT sysdate, -- 생성 일자
attachmentName NVARCHAR2(100) DEFAULT NULL, -- 첨부 파일 이름
attachmentPath NVARCHAR2(300) DEFAULT NULL, -- 첨부 파일 경로
CONSTRAINT notices_FK_PK_userId FOREIGN KEY(userId) REFERENCES member(userId)
ON DELETE CASCADE,
CONSTRAINT notices_PK PRIMARY KEY (noticeId)
);
-- 고객 문의 댓글 테이블 -------------------------------------------
CREATE TABLE commentAsk (
commentId NVARCHAR2(40), -- 고유한 comment 댓글 ID (기본키)
userId VARCHAR(20), -- 외래 키로 member 테이블의 userId 참조
askId NVARCHAR2(40), -- 외래 키로 askList 테이블의 askId 참조
content nvarchar2(500), -- 댓글 내용
createDate DATE DEFAULT sysdate,
CONSTRAINT comment_FK_PK_userId FOREIGN KEY(userId) REFERENCES member(userId)
ON DELETE CASCADE,
CONSTRAINT comment_FK_PK_askId FOREIGN KEY(askId) REFERENCES askList(askId)
ON DELETE CASCADE,
CONSTRAINT comment_PK PRIMARY KEY (commentId)
);



