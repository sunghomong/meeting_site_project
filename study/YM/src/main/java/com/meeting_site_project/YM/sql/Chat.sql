CREATE TABLE chatMessages (
    messageId NVARCHAR2(50), -- 기본키 메시지의 id
    userId VARCHAR(20), -- member의 외래키
    chatId NVARCHAR2(50), -- chatRoom 의 chatId 외래키
    messageContent CLOB, -- 메시지 내용 CLOB -> 길이 제한 X
    type MessageType, -- 타입 유형 (text, join, leave)
    messageTime DATE DEFAULT sysdate,
    CONSTRAINT chatMessages_FK_PK_userId FOREIGN KEY(userId) REFERENCES member(userId)
    ON DELETE CASCADE,
    CONSTRAINT chatMessages_FK_PK_chatId FOREIGN KEY(chatId) REFERENCES chatRoom(chatId)
    ON DELETE CASCADE,
    CONSTRAINT chatMessages_Pk PRIMARY KEY (messageId)
);
CREATE TYPE MessageType AS ENUM ('text', 'join', 'leave'); -- enum 타입의 테이블 생성

CREATE TABLE chatRoom(
    chatRoomId NVARCHAR2(50), -- 고유의 채팅방 id 기본키
    groupId NVARCHAR2(50), --  groupInfo 의 groupId 외래키 (하나의 모임당 하나의 방을 생성하기위한 외래키)
    ownerId VARCHAR(20), -- 채팅방의 방장
    maxUserCnt NUMBER, -- 채팅방 최대 인원 제한
    userCount NUMBER, -- 채팅방 현재 인원 수
    CONSTRAINT chatRoom_FK_PK_groupId FOREIGN KEY(groupId) REFERENCES groupInfo(groupId)
    ON DELETE CASCADE,
    CONSTRAINT chatRoom_FK_PK_ownerId FOREIGN KEY(ownerId) REFERENCES member(userId)
    ON DELETE CASCADE,
    CONSTRAINT chatRoom_Pk PRIMARY KEY (chatId)
);

-- member가 속해 있는 채팅 room 들은 어떻게 분리??

CREATE TABLE chatRoomMembers ( -- 채팅방에 속해 있는 멤버들 조회
    roomUserId NVARCHAR2(50), -- 고유의 채팅방에 속한 유저 id (기본키)
    userId NVARCHAR2(20), -- member 테이블의 userId (외래키)
    chatRoomId NVARCHAR2(50), -- chatRoom 테이블의 chatRoomId(외래키)
    CONSTRAINT chatRoomMembers_FK_PK_userId FOREIGN KEY(userId) REFERENCES member(userId)
    ON DELETE CASCADE,
    CONSTRAINT chatRoomMembers_FK_PK_chatRoomId FOREIGN KEY(chatRoomId) REFERENCES chatRoom(chatRoomId)
    ON DELETE CASCADE,
    CONSTRAINT chatRoomMembers_Pk PRIMARY KEY (roomUserId)
);