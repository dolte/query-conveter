create or replace PROCEDURE      SP_GET_DEMD_AMT
(
        P_UPSO_CD       IN     VARCHAR2    -- 업소 코드
    ,   P_START_YRMN    IN     VARCHAR2    -- 시작 년월
    ,   P_END_YRMN      IN     VARCHAR2    -- 종료 년월
    ,   P_CRET_GBN      IN     VARCHAR2    -- 생성 구분 (O : OCR / A : 자동이체)
    ,   P_RECV_DAY      IN     VARCHAR2
    ,   P_BSTYP_CD      OUT    VARCHAR2    -- 업종
    ,   P_UPSO_GRAD     OUT    VARCHAR2    -- 업소 등급
    ,   P_MONPRNCFEE    OUT    VARCHAR2    -- 종료 년월 월정료
    ,   P_DEMD_GBN      OUT    VARCHAR2    -- 종료 년월 청구 구분
    ,   P_DEMD_MMCNT    OUT    VARCHAR2    -- 대상 기간 연체 개월 수
    ,   P_TUSE_AMT      OUT    VARCHAR2    -- 월 사용료
    ,   P_TADDT_AMT     OUT    VARCHAR2    -- 가산금
    ,   P_TEADDT_AMT    OUT    VARCHAR2    -- 중 가산금 
    ,   P_DSCT_AMT      OUT    VARCHAR2    -- 할인 금액
    ,   P_TDEMD_AMT     OUT    VARCHAR2    -- 청구 금액
)
/******************************************************************************
   NAME:       SP_GET_DEMD_AMT
   PURPOSE:    대상기간 (시작년월, 종료년월) 을 입력받아 가장 최근의 청구년월을 
               기준으로 사용료, 가산금, 중가산금을 계산한다.

   IN PARAM     P_UPSO_CD    IN     VARCHAR2    -- 업소 코드
            ,   P_ST_YRMN    IN     VARCHAR2    -- 시작 년월
            ,   P_ED_YRMN    IN     VARCHAR2    -- 종료 년월
            ,   P_CRET_GBN   IN     VARCHAR2    -- 생성 구분 (O : OCR / A : 자동이체)

   OUT PARAM    P_BSTYP_CD      OUT    VARCHAR2    -- 업종
            ,   P_UPSO_GRAD     OUT    VARCHAR2    -- 업소 등급
            ,   P_MONPRNCFEE    OUT    VARCHAR2    -- 종료 년월 월정료
            ,   P_DEMD_GBN      OUT    VARCHAR2    -- 종료 년월 청구 구분
            ,   P_DEMD_MMCNT    OUT    VARCHAR2    -- 대상 기간 연체 개월 수
            ,   P_TUSE_AMT      OUT    VARCHAR2    -- 월 사용료
            ,   P_TADDT_AMT     OUT    VARCHAR2    -- 가산금
            ,   P_TEADDT_AMT    OUT    VARCHAR2    -- 중 가산금 
            ,   P_DSCT_AMT      OUT    VARCHAR2    -- 할인 금액
            ,   P_DEMD_GBN      OUT    VARCHAR2    -- 종료 년월 청구 구분
            ,   P_TDEMD_AMT     OUT    VARCHAR2    -- 청구 금액

******************************************************************************/

AS

    V_DEMD_YRMN     VARCHAR2(6);    -- 청구 년월
    V_DEMD_GBN      VARCHAR2(2);    -- 청구 구분
    V_BSTYP_CD      VARCHAR2(8);    -- 청구월 업종
    V_UPSO_GRAD     VARCHAR2(2);    -- 청구월 업소 등급

    V_MONPRNCFEE    NUMBER := 0;    -- 월정료
    V_DEMD_MMCNT    NUMBER := 0;    -- 청구 월수

    V_TUSE_AMT      NUMBER := 0;    -- 총 사용료 (잔고 반영)
    V_TADDT_AMT     NUMBER := 0;    -- 총 가산금
    V_TEADDT_AMT    NUMBER := 0;    -- 총 중 가산금 
    V_TDEMD_AMT     NUMBER := 0;    -- 총 청구금액    
    V_TDSCT_AMT     NUMBER := 0;    -- 총 할인금액
    
    V_BSCON_WON         NUMBER := 0;
    
BEGIN

    BEGIN
        SELECT  MAX(DEMD_YRMN)
        INTO    V_DEMD_YRMN
        FROM    GIBU.TBRA_DEMD_OCR;

    EXCEPTION
        WHEN OTHERS THEN
            V_DEMD_YRMN := TO_CHAR(SYSDATE, 'YYYYMM');
    END;

    GIBU.SP_GET_DEMD_AMT_HIST
    (
            P_UPSO_CD
        ,   P_START_YRMN
        ,   P_END_YRMN
        ,   V_DEMD_YRMN
        ,   P_CRET_GBN
        ,   P_RECV_DAY
        ,   V_BSTYP_CD
        ,   V_UPSO_GRAD
        ,   V_MONPRNCFEE
        ,   V_DEMD_GBN
        ,   V_DEMD_MMCNT
        ,   V_TUSE_AMT
        ,   V_TADDT_AMT
        ,   V_TEADDT_AMT 
        ,   V_TDSCT_AMT
        ,   V_TDEMD_AMT
    );
    

    P_BSTYP_CD    := V_BSTYP_CD;
    P_UPSO_GRAD   := V_UPSO_GRAD;
    P_MONPRNCFEE  := TO_CHAR(V_MONPRNCFEE);
    P_DEMD_GBN    := V_DEMD_GBN;
    P_DEMD_MMCNT  := TO_CHAR(V_DEMD_MMCNT);
    SELECT COUNT(1) INTO V_BSCON_WON FROM GIBU.TBRA_BSCON_CONTRINFO WHERE UPSO_CD = P_UPSO_CD;
    IF V_BSCON_WON > 0 THEN
        P_TUSE_AMT    := TO_CHAR(TRUNC(V_TUSE_AMT));  -- 원 단위 절사
    ELSE
        P_TUSE_AMT    := TO_CHAR(TRUNC(V_TUSE_AMT   , -1));  -- 원 단위 절사
    END IF;
    P_TADDT_AMT   := TO_CHAR(TRUNC(V_TADDT_AMT  , -1));  -- 원 단위 절사
    P_TEADDT_AMT  := TO_CHAR(TRUNC(V_TEADDT_AMT , -1));  -- 원 단위 절사
    P_DSCT_AMT    := TO_CHAR(TRUNC(V_TDSCT_AMT  , -1));  -- 원 단위 절사
    --P_TDEMD_AMT   := TO_CHAR(TRUNC(V_TDEMD_AMT  , -1));  -- 원 단위 절사
    -- 원단위 절사 값 때문에 청구정보 통일을 위해서 위에서 절사처리된 (사용료+가산금+중가산금) 합한것을 총청구금액에 넣어준다. 2010.05.19
    P_TDEMD_AMT   := TO_CHAR(P_TUSE_AMT + P_TADDT_AMT + P_TEADDT_AMT);
    
    
END SP_GET_DEMD_AMT;
 