-- JDBC용 계정 : (USERNAME / PWD) C##JDBC / JDBC
CREATE USER C##JDBC IDENTIFIED BY JDBC;
GRANT CONNECT, RESOURCE TO C##JDBC;
-- 관리자 계정으로 위의 내용 진행
-- 테이블스페이스 설정
ALTER USER C##JDBC DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS;
-- 회원 정보를 저장할 테이블 (member)
DROP TABLE MEMBER;
CREATE TABLE MEMBER(
    USERNO NUMBER PRIMARY KEY,                  -- 회원번호
    USERID VARCHAR2(20) NOT NULL UNIQUE,        -- 회원아이디
    USERPW VARCHAR2(20) NOT NULL,               -- 회원 비밀번호
    USERNAME VARCHAR2(20) NOT NULL,             -- 이름
    GENDER CHAR(1) CHECK(GENDER IN ('M','F')),  -- 성별
    AGE NUMBER,                                 -- 나이
    EMAIL VARCHAR2(30) ,                        -- 이메일
    ADDRESS VARCHAR2(100),                      -- 주소
    PHONE VARCHAR2(13),                         -- 연락처
    HOBBY VARCHAR2(13),                         -- 취미
    ENROLLDATE DATE DEFAULT SYSDATE NOT NULL    -- 가입일
);

-- * 회원번호로 사용할 시퀀스 생성
DROP SEQUENCE SEQ_USERNO;
CREATE SEQUENCE SEQ_USERNO
NOCACHE;

-- * 테이블 스페이스 설정
INSERT INTO MEMBER
    VALUES (SEQ_USERNO.NEXTVAL, 'ADMIN','1234','관리자','M',20, 'ADMIN@KH.OR.KR','서울', '010-1010-0101',NULL,'2020-07-30');

INSERT INTO MEMBER
    VALUES (SEQ_USERNO.NEXTVAL, 'sjlim,','1234','임수진','F',20, 'sujim@KH.OR.KR',NULL, '010-9090-0101',NULL,DEFAULT);
COMMIT;
------------------------------------------------------------------

-- 테스트용 테이블 (TEST)
CREATE TABLE TEST(
    TNO NUMBER,
    TNAME VARCHAR2(30),
    TDATE DATE 
);

SELECT *FROM MEMBER where USERID = 'admin';
SELECT * FROM MEMBER;
INSERT INTO TEST VALUES(1, '기다운', SYSDATE);

COMMIT;













