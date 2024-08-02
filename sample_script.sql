-- JDBC�� ���� : (USERNAME / PWD) C##JDBC / JDBC
CREATE USER C##JDBC IDENTIFIED BY JDBC;
GRANT CONNECT, RESOURCE TO C##JDBC;
-- ������ �������� ���� ���� ����
-- ���̺����̽� ����
ALTER USER C##JDBC DEFAULT TABLESPACE USERS QUOTA UNLIMITED ON USERS;
-- ȸ�� ������ ������ ���̺� (member)
DROP TABLE MEMBER;
CREATE TABLE MEMBER(
    USERNO NUMBER PRIMARY KEY,                  -- ȸ����ȣ
    USERID VARCHAR2(20) NOT NULL UNIQUE,        -- ȸ�����̵�
    USERPW VARCHAR2(20) NOT NULL,               -- ȸ�� ��й�ȣ
    USERNAME VARCHAR2(20) NOT NULL,             -- �̸�
    GENDER CHAR(1) CHECK(GENDER IN ('M','F')),  -- ����
    AGE NUMBER,                                 -- ����
    EMAIL VARCHAR2(30) ,                        -- �̸���
    ADDRESS VARCHAR2(100),                      -- �ּ�
    PHONE VARCHAR2(13),                         -- ����ó
    HOBBY VARCHAR2(13),                         -- ���
    ENROLLDATE DATE DEFAULT SYSDATE NOT NULL    -- ������
);

-- * ȸ����ȣ�� ����� ������ ����
DROP SEQUENCE SEQ_USERNO;
CREATE SEQUENCE SEQ_USERNO
NOCACHE;

-- * ���̺� �����̽� ����
INSERT INTO MEMBER
    VALUES (SEQ_USERNO.NEXTVAL, 'ADMIN','1234','������','M',20, 'ADMIN@KH.OR.KR','����', '010-1010-0101',NULL,'2020-07-30');

INSERT INTO MEMBER
    VALUES (SEQ_USERNO.NEXTVAL, 'sjlim,','1234','�Ӽ���','F',20, 'sujim@KH.OR.KR',NULL, '010-9090-0101',NULL,DEFAULT);
COMMIT;
------------------------------------------------------------------

-- �׽�Ʈ�� ���̺� (TEST)
CREATE TABLE TEST(
    TNO NUMBER,
    TNAME VARCHAR2(30),
    TDATE DATE 
);

SELECT *FROM MEMBER where USERID = 'admin';
SELECT * FROM MEMBER;
INSERT INTO TEST VALUES(1, '��ٿ�', SYSDATE);

COMMIT;













