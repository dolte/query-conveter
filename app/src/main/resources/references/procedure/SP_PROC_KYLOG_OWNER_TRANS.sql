create or replace PROCEDURE      SP_PROC_KYLOG_OWNER_TRANS
(
      P_UPSO_CD                       IN     VARCHAR2    -- 새로생성된 업소코드
  ,   P_BEFORE_UPSO_CD          IN     CHAR    -- 이전업소코드
  ,   P_USER_ID                        IN     VARCHAR2    -- 사원ID
)
/******************************************************************************
   NAME:       SP_SET_OWNER_TRANS
   PURPOSE:    업주변경이 발생할 때 이전업소코드의 로그기정보를 신규업소에 모두 복사시킨다.
                      bra01_s01.xml 에서 실행됨
                      1) 새 업소의 KDS_SHOP, KDS_SHOP_STATUSHISTORY, KDS_SHOPROOM에 이전업소의 정보를 복사해 넣는다.
                      2) 이전업소의 KDS_SHOP_STATUS_HISTORY에 이관상태로 등록한다.
                      3) 이전업소의 KDS_SHOPROOM 테이블의 운영상태코드값을 이관으로 변경한다.
                      4) 이력을 기록한다.
                      5) 할인시작, 할인종료, 할인여부 정보를 지정한다.
               업주변경 시 통합징수 매칭내역을 자동으로 추가한다.(2017-08-07 장태진)
               업주변경 시 매장음악서비스 사업자 매칭내역을 자동으로 변경한다.(2018-03-13 이다섭)
******************************************************************************/

AS
    V_CNT         NUMBER := 0;
    V_DSCT_START  VARCHAR2(6);
    V_USER_NM     VARCHAR2(50);
    V_IPADDRESS   VARCHAR2(20);
    
    
BEGIN

    /* 로그수집기가 업주변경 시까지 매칭이 되어있는지 확인. */
    SELECT COUNT(1)
      INTO V_CNT
      FROM LOG.KDS_SHOPROOM A
         , LOG.KDS_LOGCOLLECTOR B
     WHERE A.SERIAL_NO = (SELECT SERIAL_NO
                            FROM LOG.KDS_SHOPROOM
                           WHERE SEQ = (SELECT MAX(SEQ) FROM LOG.KDS_SHOPROOM WHERE UPSO_CD = P_BEFORE_UPSO_CD AND CO_STATUS = '07001'))
       AND A.SERIAL_NO = B.SERIAL_NO
       AND B.CO_PARING <> '08003'
       AND A.CO_STATUS = '07001'
       AND A.UPSO_CD   = P_BEFORE_UPSO_CD;
    
    BEGIN
        SELECT USER_NM, IPADDRESS
        INTO  V_USER_NM, V_IPADDRESS
        FROM  FIDU.TENV_MEMBER
        WHERE USER_ID = P_USER_ID;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            V_USER_NM := ' ';
            V_IPADDRESS := '';
        WHEN OTHERS THEN
            V_USER_NM := ' ';
            V_IPADDRESS := '';
    END;
    
    /* 로그수집기가 업주변경 시까지 매칭이 되어있는 업소일때만 실시한다. */
    /* 해당 시리얼번호가 다른 업소로 이관된 경우에는 업주변경시 로그기 및 할인정보가 이관되지 않아야 하므로
       기존업소의 영업정보가 영업상태이고, 시리얼번호가 매칭된 업소가 1개인 경우에만. */
    IF V_CNT = 1 THEN
       
         INSERT INTO LOG.KDS_SHOP (UPSO_CD, COMMENTS)
         SELECT P_UPSO_CD, COMMENTS
         FROM   LOG.KDS_SHOP
         WHERE UPSO_CD = P_BEFORE_UPSO_CD;

         /*20151203정성윤 요청 최신정보 CO_STATUS가 07001:운영 인것만 이관
         웹관리의 로그기관리에서 가장 최신등록된의 데이터만 인정하는 문제를 해결하기 위함     */
         INSERT INTO LOG.KDS_SHOP_STATUSHISTORY (SEQ, UPSO_CD, CO_STATUS, USER_NAME, REG_DATE)
         SELECT IMT_KDSSH_SEQ.NEXTVAL, P_UPSO_CD, CO_STATUS, P_USER_ID, SYSDATE
         FROM   LOG.KDS_SHOP_STATUSHISTORY
         WHERE  UPSO_CD = P_BEFORE_UPSO_CD
         AND    CO_STATUS = '07001'
         AND    SEQ = (SELECT MAX(SEQ)
                       FROM   LOG.KDS_SHOP_STATUSHISTORY
                       WHERE  UPSO_CD = P_BEFORE_UPSO_CD );

         /*20151203정성윤 요청  CO_STATUS가 07001:운영 인것만 이관
         웹관리의 로그기관리에서 가장 최신등록된의 데이터만 인정하는 문제를 해결하기 위함     */        
         INSERT INTO LOG.KDS_SHOPROOM (SEQ, UPSO_CD, ROOM_NAME, SERIAL_NO, CO_STATUS, REG_DATE, BSCON_CD)
         SELECT IMT_KDSSR_SEQ.NEXTVAL, P_UPSO_CD, ROOM_NAME, SERIAL_NO, CO_STATUS, SYSDATE, BSCON_CD
         FROM   LOG.KDS_SHOPROOM
         WHERE  UPSO_CD = P_BEFORE_UPSO_CD
         AND    CO_STATUS = '07001';
               
         INSERT INTO LOG.KDS_SHOP_STATUSHISTORY (SEQ, UPSO_CD, CO_STATUS, USER_NAME, REG_DATE)
         VALUES (IMT_KDSSH_SEQ.NEXTVAL, P_BEFORE_UPSO_CD, '07004', P_USER_ID, SYSDATE);
         
         UPDATE LOG.KDS_SHOPROOM 
         SET    CO_STATUS = '07004'
         WHERE  UPSO_CD = P_BEFORE_UPSO_CD
         AND    CO_STATUS <> '07005';
   
         INSERT INTO LOG.KDS_ACCESS_HISTORY (SEQ, REG_DATE, REMOTE_IP, USER_ID, USER_NAME, CO_MENU,  CO_PROC)
         VALUES (IMT_KDSAH_SEQ.NEXTVAL, SYSDATE, V_IPADDRESS, P_USER_ID, V_USER_NM, '05002','06001');

        -- 이전업소의 할인시작월이 신규업소의 개업월보다 나중인 경우 그대로 복사
        SELECT DSCT_START
          INTO V_DSCT_START
          FROM LOG.KDS_SHOP
         WHERE UPSO_CD  = P_BEFORE_UPSO_CD;
         
         -- 이전업소의 할인종료 월 지정
         UPDATE LOG.KDS_SHOP
            SET DSCT_END = (SELECT TO_CHAR(ADD_MONTHS(TO_DATE(CLSBS_YRMN, 'YYYYMM'), '-1'), 'YYYYMM') AS CLSBS_YRMN
                              FROM GIBU.TBRA_UPSO
                             WHERE UPSO_CD = P_BEFORE_UPSO_CD)
          WHERE UPSO_CD  = P_BEFORE_UPSO_CD;
        
         -- 새 업소의 할인시작 월 지정, 할인여부 지정
         UPDATE LOG.KDS_SHOP
            SET DSCT_START = (CASE WHEN V_DSCT_START > (SELECT SUBSTR(OPBI_DAY,1,6) FROM GIBU.TBRA_UPSO WHERE UPSO_CD = P_UPSO_CD) THEN V_DSCT_START
                                   ELSE (SELECT SUBSTR(OPBI_DAY,1,6) FROM GIBU.TBRA_UPSO WHERE UPSO_CD = P_UPSO_CD)
                               END)
              , DSCT_YN    = (SELECT DSCT_YN
                                FROM LOG.KDS_SHOP
                               WHERE UPSO_CD = P_BEFORE_UPSO_CD)
          WHERE UPSO_CD    = P_UPSO_CD;
     
    END IF;

    INSERT INTO GIBU.TBRA_BSCON_CONTRINFO (  BSCON_CD
                                           , BSCON_UPSO_CD
                                           , BSCON_UPSO_NM
                                           , APPL_DAY
                                           , SEQ
                                           , UPSO_CD
                                           , UPSO_ZIP
                                           , UPSO_ADDR1
                                           , UPSO_ADDR2
                                           , MONPRNCFEE
                                           , MATCH_GBN
                                           , USE_YN
                                           , INSPRES_ID
                                           , INS_DATE
                                           , MODPRES_ID
                                           , MOD_DATE
                                           , BSTYP_CD
                                           , ATAX_YN)
    SELECT BSCON_CD
         , BSCON_UPSO_CD
         , BSCON_UPSO_NM
         , TO_CHAR(SYSDATE, 'YYYYMMDD')
         , (SELECT MAX(SEQ)+1 FROM GIBU.TBRA_BSCON_CONTRINFO WHERE UPSO_CD = P_BEFORE_UPSO_CD)
         , P_UPSO_CD
         , UPSO_ZIP
         , UPSO_ADDR1
         , UPSO_ADDR2
         , MONPRNCFEE
         , MATCH_GBN
         , USE_YN
         , 'AUTO'
         , SYSDATE
         , NULL
         , NULL
         , BSTYP_CD
         , ATAX_YN
      FROM GIBU.TBRA_BSCON_CONTRINFO
     WHERE UPSO_CD = P_BEFORE_UPSO_CD
       AND USE_YN = 'Y';
    
    UPDATE GIBU.TBRA_BSCON_CONTRINFO
       SET USE_YN = 'N'
         , MODPRES_ID = 'AUTO'
         , MOD_DATE = SYSDATE
     WHERE UPSO_CD = P_BEFORE_UPSO_CD
       AND USE_YN = 'Y';
       
    INSERT INTO GIBU.TBRA_STOMU_HISTY (BSCON_CD, BSCON_UPSO_CD, SEQ, CNG_COL, BEFO_CNG, AFT_CNG, INSPRES_ID, INS_DATE, REMAK)
    SELECT BSCON_CD
         , BSCON_UPSO_CD
         , (SELECT MAX(SEQ)+1 FROM GIBU.TBRA_STOMU_HISTY WHERE BSCON_CD = A.BSCON_CD AND BSCON_UPSO_CD = A.BSCON_UPSO_CD)
         , '업소코드'
         , P_BEFORE_UPSO_CD
         , P_UPSO_CD
         , 'AUTO'
         , SYSDATE
         , '업주변경에 따른 업소코드 변경'
     FROM GIBU.TBRA_STOMU_CONTRINFO A
    WHERE UPSO_CD = P_BEFORE_UPSO_CD;
    
   UPDATE GIBU.TBRA_STOMU_CONTRINFO
      SET UPSO_CD = P_UPSO_CD
        , MODPRES_ID = 'AUTO'
        , MOD_DATE = SYSDATE
    WHERE UPSO_CD = P_BEFORE_UPSO_CD;
    
END SP_PROC_KYLOG_OWNER_TRANS;
 