create or replace PROCEDURE      SP_MISU_DEMD_OPEN(P_UPSO_CD IN VARCHAR2)
IS
V_DEMD_YRMN  VARCHAR2(6);   -- 청구년월
V_DEMD_DAY   VARCHAR2(8);   -- 청구일자 (INSERT용)
V_DEMD_NUM   VARCHAR2(4);   -- 청구번호

V_OPBI_YRMN  VARCHAR2(6);   -- 개업일자
V_END_YRMN   VARCHAR2(6);   -- 청구종료일자
V_BRAN_CD    VARCHAR2(2);   -- 지부코드
/******************************************************************************
   NAME:       SP_MISU_DEMD_OPEN
   PURPOSE:    신규업소의 MICR청구 생성한다.
               업소등록의 저장 (1000001006004001, upso_regist_ky) 에서 호출한다.
               

   
   NOTES:   1. 업소정보   개업월, 종료월 조회
            2. INDTN_MM을 등록한다.
            3. INDTN_MM의 합계로 INDTN을 등록한다.
******************************************************************************/
BEGIN
    
    V_DEMD_DAY := TO_CHAR(SYSDATE, 'YYYYMMDD');
    
    /***************************************************
        0. 현재 청구월을 조회한다.
    ***************************************************/
    BEGIN
        SELECT  MAX(DEMD_YRMN)
        INTO    V_DEMD_YRMN
        FROM    GIBU.TBRA_DEMD_OCR;
    EXCEPTION
        WHEN OTHERS THEN
            V_DEMD_YRMN := TO_CHAR(SYSDATE, 'YYYYMM');
    END;
    
    
    /***************************************************
        1. 업소정보   개업월, 종료월 조회
    ***************************************************/
    BEGIN
        SELECT BRAN_CD, OPBI_YRMN, END_YRMN
        INTO   V_BRAN_CD, V_OPBI_YRMN, V_END_YRMN 
        FROM ( 
                SELECT UPSO_CD, BRAN_CD, INS_DATE, SUBSTR(OPBI_DAY,1,6) AS OPBI_YRMN, CLSBS_YRMN
                     , (CASE WHEN CLSBS_YRMN IS NULL THEN V_DEMD_YRMN
                             WHEN CLSBS_YRMN > V_DEMD_YRMN THEN V_DEMD_YRMN
                             ELSE CLSBS_YRMN END
                        ) END_YRMN
                FROM   GIBU.TBRA_UPSO
                WHERE  UPSO_CD = P_UPSO_CD
                AND    CLSED_YN IS NULL
                AND    OPBI_DAY < V_DEMD_YRMN || '32'
             );
        
    EXCEPTION
        WHEN OTHERS THEN
            V_OPBI_YRMN := '';
            V_END_YRMN  := '';
    END;
    
    IF (V_OPBI_YRMN IS NOT NULL) THEN --MICR발생 대상이 맞을때. (미래의 업소를 미리 개업시킨경우는 MICR발생 안하니까.)
    
    
    /***************************************************
        2. INDTN_MM을 등록한다.
    ***************************************************/
    
    WRITE_LOG('GIBU.SP_PROC_DEMD_OPEN','###'||P_UPSO_CD||'###');    
    
        GIBU.SP_PROC_DEMD_MM_AMT(
            P_UPSO_CD
          , V_OPBI_YRMN
          , V_END_YRMN
          , V_DEMD_YRMN
          , 'M'
          , 'MICR_OPEN_EUN');
    
    
        SELECT  LPAD(NVL(MAX(DEMD_NUM), 0) + 1, 4, '0')
        INTO    V_DEMD_NUM 
        FROM    GIBU.TBRA_DEMD_INDTN
        WHERE   DEMD_DAY = V_DEMD_DAY
        AND     UPSO_CD = P_UPSO_CD;
        
        
        
        /***************************************************
            3. INDTN_MM의 합계로 INDTN을 등록한다.
        ***************************************************/
        INSERT INTO GIBU.TBRA_DEMD_INDTN (
              DEMD_DAY
            , DEMD_NUM
            , UPSO_CD
            , CRET_GBN
            , BRAN_CD
            , START_YRMN
            , END_YRMN
            , DEMD_GBN
            , BSTYP_CD
            , UPSO_GRAD
            , MONPRNCFEE
            , DEMD_MMCNT
            , TOT_USE_AMT
            , TOT_ADDT_AMT
            , TOT_EADDT_AMT
            , TOT_DEMD_AMT
            , DSCT_AMT
            , REMAK
            , INSPRES_ID
            , INS_DATE
        )
        SELECT V_DEMD_DAY
             , V_DEMD_NUM
             , P_UPSO_CD
             , 'M'
             , V_BRAN_CD
             , MIN(START_YRMN)
             , MAX(START_YRMN)
             , '33'
             , MAX(BSTYP_CD)
             , MAX(UPSO_GRAD)
             , MAX(MONPRNCFEE)
             , COUNT(*)
             , TRUNC(SUM(USE_AMT), -1)
             , TRUNC(SUM(ADDT_AMT), -1)
             , TRUNC(SUM(EADDT_AMT), -1)
             , TRUNC(SUM(DEMD_AMT), -1)
             , TRUNC(SUM(DSCT_AMT), -1)
             , '자동생성'
             , 'DEMD_MICR_OPEN'
             , SYSDATE               
        FROM   GIBU.TBRA_DEMD_INDTN_MM
        WHERE UPSO_CD = P_UPSO_CD
        AND   DEMD_DAY = V_DEMD_DAY
        AND   DEMD_AMT > 0;
    END IF;
    
        
        
        
    COMMIT; 
END SP_MISU_DEMD_OPEN;
 