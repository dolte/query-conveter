create or replace PROCEDURE      SP_PROC_CLAIM_COMPN_CHECK
(
        P_GBN               IN      VARCHAR2    -- 등록/삭제/수정 구분(등록:I, 수정,U, 삭제:D)
    ,   P_UPSO_CD           IN      VARCHAR2    -- 업소코드
    ,   P_COMIS             IN      VARCHAR2    -- 수수료
    ,   P_REPT_DAY          IN      VARCHAR2    -- 입금 년월
    ,   P_REPT_NUM          IN      VARCHAR2
    ,   P_REPT_GBN          IN      VARCHAR2
    ,   P_DISTR_SEQ         IN      VARCHAR2
    ,   P_INSPRES_ID        IN      VARCHAR2
)

/******************************************************************************
   NAME:       SP_PROC_CLAIM_COMPN_CHECK
   PURPOSE:    등록된 채권의 완료일자 입력 기준은 기존에 등록되었던 채권기간동안 원장에 빠짐없이 채워져 있을 경우 COMPN_DAY를 입력해준다.
               이 때 원장은 일반 입금분/고소입금분/휴폐업등 어떤걸로 채워져 있든 상관이 없다.
               SOL_AMT는 채권의뢰기간동안 원장에 표시된 금액의 합계가 된다.

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        2010-05-26          1. Created this procedure.

   NOTES:

   Automatically available Auto Replace Keywords:
      Object Name:     SP_PROC_CLAIM_CHECK
      Sysdate:         2010-05-26
      Date and Time:   2010-05-26, 오후 1:43:24, and 2010-05-26 오후 1:43:24
      Username:         (set in TOAD Options, Procedure Editor)
      Table Name:       (set in the "New PL/SQL Object" dialog)

******************************************************************************/
AS
    V_APPTN_YRMN        VARCHAR2(6);    -- 채권 신청 년월
    V_CLAIM_DEMD_AMT    NUMBER := 0;    -- 채권 총 청구금액
    V_CSTART_YRMN       VARCHAR(6);     -- 채권의뢰 기간 1
    V_CEND_YRMN         VARCHAR(6);     -- 채권의뢰 기간 2
    V_CDEMD_MMCNT       NUMBER := 0;    -- 채권의뢰 달수
    V_COMPN_DAY         VARCHAR(8);     -- 채권완료 일자
    
    V_TOT_NOTE_MON      NUMBER := 0;    -- 원장에 채워져 있는 달수       
    V_TOT_NOTE_AMT      NUMBER := 0;    -- 채권의뢰가 등록된 기간동안의 총 입금액
    
    V_CLAIM_AMT         NUMBER := 0;    
    V_DIFF_AMT          NUMBER := 0;

BEGIN
    BEGIN
        SELECT  APPTN_YRMN
            ,   TOT_DEMD_AMT
            ,   START_YRMN
            ,   END_YRMN
            ,   DEMD_MMCNT
            ,   COMPN_DAY
        INTO    V_APPTN_YRMN
            ,   V_CLAIM_DEMD_AMT
            ,   V_CSTART_YRMN
            ,   V_CEND_YRMN
            ,   V_CDEMD_MMCNT
            ,   V_COMPN_DAY
        FROM(
                SELECT  APPTN_YRMN
                    ,   TOT_DEMD_AMT
                    ,   START_YRMN
                    ,   END_YRMN
                    ,   DEMD_MMCNT
                    ,   COMPN_DAY
                FROM    GIBU.TBRA_DLGTN_CLAIM
                WHERE   UPSO_CD = P_UPSO_CD
                ORDER BY APPTN_YRMN DESC
        )
        WHERE   ROWNUM = 1;
        
            
    EXCEPTION
        WHEN OTHERS THEN
            V_APPTN_YRMN     := NULL;
            V_CLAIM_DEMD_AMT := 0;
            V_CSTART_YRMN    := NULL;
            V_CEND_YRMN      := NULL;
            V_CDEMD_MMCNT    := 0;
    END;
    
    SELECT  COUNT(*)        
        ,   SUM(USE_AMT)    
    INTO    V_TOT_NOTE_MON
        ,   V_TOT_NOTE_AMT                    
    FROM    GIBU.TBRA_NOTE
    WHERE   UPSO_CD = P_UPSO_CD
    AND     NOTE_YRMN >= V_CSTART_YRMN
    AND     NOTE_YRMN <= V_CEND_YRMN
    AND     REPT_GBN NOT BETWEEN '11' AND '20'
    AND     NOTE_YRMN IS NOT NULL;
        
    IF(P_GBN ='I') THEN 
        -- 채권정보가 있고 완료가 되지 않았을 경우 
        IF (V_APPTN_YRMN IS NOT NULL AND V_COMPN_DAY IS NULL) THEN
            -- 채권 의뢰기간과 원장에 채워져 있는 달수가 같거나 초과되면 채권 입금정보를 저장해준다.
            IF(V_CDEMD_MMCNT <= V_TOT_NOTE_MON) THEN
                V_CLAIM_AMT := V_TOT_NOTE_AMT;
                V_DIFF_AMT  := (V_CLAIM_DEMD_AMT - V_TOT_NOTE_AMT); -- 기존 등록되어 있던 채권의뢰금액 - 해당기간동안에 원장에 채워진 금액 합계의 차이 금액
                    
                UPDATE  GIBU.TBRA_DLGTN_CLAIM
                SET     SOL_START_YRMN = V_CSTART_YRMN  -- 기존 채권등록된 시작년원
                    ,   SOL_END_YRMN = V_CEND_YRMN      -- 기존 채권등록된 종료년월
                    ,   SOL_AMT = V_TOT_NOTE_AMT        -- 등록된 채권 기간동안 입금된 원장의 입금 합계
                    ,   COMIS = P_COMIS                 -- 마지막 입금분의 수수료 (휴업/폐업으로 인해 채권 기간이 채워졌을 경우에는 0)
                    ,   DIFF_AMT = V_DIFF_AMT           -- 기존 등록된 채권의뢰금액과 실제 원장에 채워진 금액의 차이
                    ,   COMPN_DAY = TO_CHAR(SYSDATE, 'YYYYMMDD')
                    ,   REPT_DAY = P_REPT_DAY           -- 마지막 입금분의 REPT_DAY (휴업/폐업으로 인해 채권기간이 채워졌을 경우에는 CLSED_DAY)
                    ,   REPT_NUM = P_REPT_NUM           -- 마지막 입금분의 REPT_NUM (휴업/폐업으로 인해 채권기간이 채워졌을 경우에는 CLSED_NUM)
                    ,   REPT_GBN = P_REPT_GBN           -- 마지막 입금분의 REPT_GBN (휴업/폐업으로 인해 채권기간이 채워졌을 경우에는 CLSED_GBN)
                    ,   DISTR_SEQ = P_DISTR_SEQ         -- 마지막 입금분의 REPT_DAY (휴업/폐업으로 인해 채권기간이 채워졌을 경우에는 0)
                    ,   MODPRES_ID = P_INSPRES_ID
                    ,   MOD_DATE = SYSDATE
                WHERE   UPSO_CD = P_UPSO_CD
                AND     APPTN_YRMN = V_APPTN_YRMN;        
            END IF;
        END IF;
    ELSIF(P_GBN='D') THEN
        IF (V_APPTN_YRMN IS NOT NULL AND V_COMPN_DAY IS NOT NULL) THEN
        -- 채권 의뢰기간이 원장에 채워져 있는 달수보다 적을 경우 기존 완료된 정보를 해제한다.
            IF(V_CDEMD_MMCNT > V_TOT_NOTE_MON) THEN
                    
                UPDATE  GIBU.TBRA_DLGTN_CLAIM
                SET     SOL_START_YRMN = ''
                    ,   SOL_END_YRMN = ''
                    ,   SOL_AMT = ''
                    ,   COMIS = ''
                    ,   DIFF_AMT = ''
                    ,   COMPN_DAY = ''
                    ,   REPT_DAY = ''
                    ,   REPT_NUM = ''
                    ,   REPT_GBN = ''
                    ,   DISTR_SEQ = ''
                    ,   MODPRES_ID = P_INSPRES_ID
                    ,   MOD_DATE = SYSDATE
                WHERE   UPSO_CD = P_UPSO_CD
                AND     APPTN_YRMN = V_APPTN_YRMN;        
            END IF;
        END IF;
    ELSE
        IF (V_APPTN_YRMN IS NOT NULL AND V_COMPN_DAY IS NULL) THEN
            -- 채권 의뢰기간과 원장에 채워져 있는 달수가 같거나 초과되면 채권 입금정보를 저장해준다.
            IF(V_CDEMD_MMCNT <= V_TOT_NOTE_MON) THEN
                V_CLAIM_AMT := V_TOT_NOTE_AMT;
                V_DIFF_AMT  := (V_CLAIM_DEMD_AMT - V_TOT_NOTE_AMT); -- 기존 등록되어 있던 채권의뢰금액 - 해당기간동안에 원장에 채워진 금액 합계의 차이 금액
                    
                UPDATE  GIBU.TBRA_DLGTN_CLAIM
                SET     SOL_START_YRMN = V_CSTART_YRMN
                    ,   SOL_END_YRMN = V_CEND_YRMN
                    ,   SOL_AMT = V_TOT_NOTE_AMT
                    ,   COMIS = P_COMIS
                    ,   DIFF_AMT = V_DIFF_AMT
                    ,   COMPN_DAY = TO_CHAR(SYSDATE, 'YYYYMMDD')
                    ,   REPT_DAY = P_REPT_DAY
                    ,   REPT_NUM = P_REPT_NUM
                    ,   REPT_GBN = P_REPT_GBN
                    ,   DISTR_SEQ = P_DISTR_SEQ
                    ,   MODPRES_ID = P_INSPRES_ID
                    ,   MOD_DATE = SYSDATE
                WHERE   UPSO_CD = P_UPSO_CD
                AND     APPTN_YRMN = V_APPTN_YRMN;        
            END IF;
        ELSIF(V_APPTN_YRMN IS NOT NULL AND V_COMPN_DAY IS NOT NULL) THEN
            -- 채권 의뢰기간이 원장에 채워져 있는 달수보다 적을 경우 기존 완료된 정보를 해제한다.
            IF(V_CDEMD_MMCNT > V_TOT_NOTE_MON) THEN
                    
                UPDATE  GIBU.TBRA_DLGTN_CLAIM
                SET     SOL_START_YRMN = ''
                    ,   SOL_END_YRMN = ''
                    ,   SOL_AMT = 0
                    ,   COMIS = 0
                    ,   DIFF_AMT = 0
                    ,   COMPN_DAY = ''
                    ,   REPT_DAY = ''
                    ,   REPT_NUM = ''
                    ,   REPT_GBN = ''
                    ,   DISTR_SEQ = ''
                    ,   MODPRES_ID = P_INSPRES_ID
                    ,   MOD_DATE = SYSDATE
                WHERE   UPSO_CD = P_UPSO_CD
                AND     APPTN_YRMN = V_APPTN_YRMN;        
            END IF;
        END IF;
    END IF;
            
            
END SP_PROC_CLAIM_COMPN_CHECK;
 