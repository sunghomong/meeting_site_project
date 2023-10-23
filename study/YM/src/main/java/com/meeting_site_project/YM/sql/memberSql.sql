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
groupId nvarchar2(30) PRIMARY KEY,
ownerUserId nvarchar2(30),
sidoName nvarchar2(30),
sigoonName nvarchar2(30),
groupName nvarchar2(30),
groupInfo nvarchar2(100),
groupNumberOfPeople number,
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
);