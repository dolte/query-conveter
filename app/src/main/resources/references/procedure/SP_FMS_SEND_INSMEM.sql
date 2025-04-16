create or replace PROCEDURE      SP_FMS_SEND_INSMEM
(
    P_MEMBER_ID     IN      VARCHAR2    -- UPSO_CD
  , P_ACCOUNT_NM    IN      VARCHAR2    -- UPSO_NM
  , P_INS_STAFF     IN      VARCHAR2
)
AS
    V_COMMAND_NO    VARCHAR2(20);
    V_IF_KEY        VARCHAR2(20);
    V_CUST_ID       VARCHAR2(10);    -- SDSI 아이디
    V_CUST_PWD      VARCHAR2(10);    -- SDSI 비밀번호
BEGIN
    /*
        사용안함. 프로세스빌더에 업소등록시 자동생성으로 하였음
    */
    
    SELECT CUST_ID, CUST_PWD
      INTO V_CUST_ID, V_CUST_PWD
      FROM GIBU.TFMS_CONFIG;
    
    -- COMMAND_NO 값 가져오기
    /*SELECT LPAD(NVL(MAX(TO_NUMBER(COMMAND_NO)) + 1, 0), 20, '0')
      INTO V_COMMAND_NO
      FROM GIBU.FMS_COMMAND_TBL;*/
    V_COMMAND_NO := GIBU.FT_GET_COMMAND_NO();

    -- IF_KEY 값 가져오기
    /*SELECT LPAD(NVL(MAX(TO_NUMBER(IF_KEY)) + 1, 0), 16, '0')
      INTO V_IF_KEY
      FROM GIBU.FMS_VIRACC_MEM_IF_TBL;*/
    --V_IF_KEY := V_COMMAND_NO;

    -- 요청테이블에 삽입
    INSERT INTO GIBU.FMS_VIRACC_MEM_IF_TBL(COMMAND_NO, IF_KEY, PROC_DT, TRAN_STATUS, CUST_ID, CUST_PWD, PROC_KIND, DATA_KIND, MEMBER_ID, BANK_CODE, ACCOUNT_NM, PAY_CHECK_YN, DEPOSIT, INSERT_DT)
    SELECT V_COMMAND_NO                 AS COMMAND_NO
         , V_COMMAND_NO                 AS IF_KEY
         , TO_CHAR(SYSDATE, 'YYYYMMDD') AS PROC_DT
         , 'B'                          AS TRAN_STATUS
         , V_CUST_ID                    AS CUST_ID
         , V_CUST_PWD                   AS CUST_PWD
         , '1000'                       AS PROC_KIND
         , '100'                        AS DATA_KIND
         , P_MEMBER_ID                  AS MEMBER_ID
         , (SELECT BANK_CD FROM GIBU.TFMS_BANK WHERE BRAN_CD IS NULL AND REMAK = 'Y') AS BANK_CODE --11 농협 기본
         , REGEXP_REPLACE(SUBSTRB(P_ACCOUNT_NM, 1, 20), '[^[:alnum:]]') AS ACCOUNT_NM --업소명에 특수문자 등의 삽입으로 인한 오류방지
         , 'N'                          AS PAY_CHECK_YN
         , '0'                          AS DEPOSIT
         , SYSDATE                      AS INSERT_DT
      FROM DUAL;

    -- 명령어테이블에 삽입
    INSERT INTO GIBU.FMS_COMMAND_TBL(COMMAND_NO, COMMAND_JOB, COMMAND_WORK, COMMAND_LINE, COMMAND_YMD, COMMAND_SEQ, COMMAND_OTHER, COMMAND_STATUS, INSERT_ID, INSERT_DT)
    SELECT V_COMMAND_NO                 AS COMMAND_NO
         , 'VIRACC'                     AS COMMAND_JOB
         , 'MEM'                        AS COMMAND_WORK
         , 'APPLY'                      AS COMMAND_LINE
         , TO_CHAR(SYSDATE, 'YYYYMMDD') AS COMMAND_YMD
         , V_COMMAND_NO                 AS COMMAND_SEQ
         , V_CUST_ID                    AS COMMAND_OTHER
         , 'B'                          AS COMMAND_STATUS
         , P_INS_STAFF
         , SYSDATE
      FROM DUAL;

END SP_FMS_SEND_INSMEM;