create or replace PROCEDURE      SP_GET_DEMD_AMT_HIST
(
        P_UPSO_CD       IN     VARCHAR2    -- 업소 코드
    ,   P_START_YRMN    IN     VARCHAR2    -- 시작 년월
    ,   P_END_YRMN      IN     VARCHAR2    -- 종료 년월
    ,   P_DEMD_YRMN     IN     VARCHAR2    -- 청구 년월
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
   PURPOSE:    대상기간 (시작년월, 종료년월) 을 입력받아 청구년월을 기준으로
               사용료, 가산금, 중가산금을 계산한다.

   IN PARAM     P_UPSO_CD    IN     VARCHAR2    -- 업소 코드
            ,   P_START_YRMN IN     VARCHAR2    -- 시작 년월
            ,   P_END_YRMN   IN     VARCHAR2    -- 종료 년월
            ,   P_DEMD_YRMN  IN     VARCHAR2    -- 청구 년월
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

******************************************************************************/

AS
    V_START_YRMN        VARCHAR2(6);    -- 시작 년월
    V_END_YRMN          VARCHAR2(6);    -- 종료 년월
    V_DEMD_YRMN         VARCHAR2(6);    -- 청구 년월

    V_OPBI_DAY          VARCHAR2(8);    -- 개업일
    V_INS_DAY           VARCHAR2(8);    -- 등록일
    V_NEW_DAY           VARCHAR2(8);    -- 최초 입금일
    V_BRAN_CD           VARCHAR2(8);    -- 지부 코드
    V_CLSBS_YRMN        VARCHAR2(6);    -- 폐업년월

    V_DEMD_GBN          VARCHAR2(2);    -- 청구 구분
    V_BSTYP_CD          VARCHAR2(8);    -- 청구월 업종
    V_UPSO_GRAD         VARCHAR2(2);    -- 청구월 업소 등급

    V_BALANCE           NUMBER := 0;    -- 잔고
    V_DEMD_MMCNT        NUMBER := 0;    -- 총 연체 개월수 
    V_MONPRNCFEE        NUMBER := 0;    -- 청구월 월정료 
    V_MONPRNCFEE2       NUMBER := 0;

    V_USE_AMT           NUMBER := 0;    -- 청구월 사용료 (잔고 반영)
    V_ADDT_AMT          NUMBER := 0;    -- 청구월 가산금
    V_EADDT_AMT         NUMBER := 0;    -- 청구월 중 가산금 
    V_DSCT_AMT          NUMBER := 0;    -- 할인금액
    V_USE_AMT2          NUMBER := 0;    -- 통합징수에 대한 금액기준이 월정료가 아닌 월사용료로 변경됨에따라 GET_MM_AMT 에서 계산된 월사용료를 따로 보관하기 위함

    V_TUSE_AMT          NUMBER := 0;   -- 총 사용료 (잔고 반영)
    V_TADDT_AMT         NUMBER := 0;   -- 총 가산금
    V_TEADDT_AMT        NUMBER := 0;   -- 총 중 가산금 
    V_TDEMD_AMT         NUMBER := 0;   -- 총 청구금액    
    V_TDSCT_AMT         NUMBER := 0;   -- 총 할인금액

    V_AUTO_DC           NUMBER := 0.01; -- 자동이체 할인률
    V_DC_AMT            NUMBER := 0;    -- 자동이체 할인 금액
    
    V_TEMP_YRMN         VARCHAR2(6);    -- TEMP 변수 : 요금 계산 년월
    V_DELAY_MMCNT       NUMBER := 0;    -- TEMP 변수 : 청구월부터 총 연체 개월
    V_DELAY_MM          NUMBER := 0;    -- TEMP 변수 : 월별 연체 개월
    V_INS_MM            NUMBER := 0;    -- TEMP 변수 : 등록월, 개업월간 차이
    
    V_TMP_USE_AMT       NUMBER := 0;
    V_TMP_ADDT_AMT      NUMBER := 0;
    V_TMP_EADDT_AMT     NUMBER := 0;
    V_BSCON_DEMD        NUMBER := 0;
    V_BSCON_DEMD2       NUMBER := 0;
    V_BSCON_TEMP_AMT    NUMBER := 0;
    V_BSCON_WON         NUMBER := 0;
    V_BSCON_RATE        NUMBER := 0.0;
    
    V_MONPRNCFEE_RETURN VARCHAR2(100);
    
BEGIN

    V_START_YRMN := SUBSTR(P_START_YRMN, 1, 6);
    V_END_YRMN   := SUBSTR(P_END_YRMN  , 1, 6);
    V_DEMD_YRMN  := SUBSTR(P_DEMD_YRMN , 1, 6); 

    BEGIN
        /*----------------------------------------------------------------------------------------
            업소 개업일, 최초 입금일, 등록일 정보 확인
        ----------------------------------------------------------------------------------------*/
        SELECT  OPBI_DAY
            ,   TO_CHAR(INS_DATE, 'YYYYMMDD')
            ,   NEW_DAY
            ,   BRAN_CD
            ,   CLSBS_YRMN
        INTO    V_OPBI_DAY
            ,   V_INS_DAY
            ,   V_NEW_DAY
            ,   V_BRAN_CD
            ,   V_CLSBS_YRMN
        FROM    GIBU.TBRA_UPSO
        WHERE   UPSO_CD = P_UPSO_CD;    
    
    EXCEPTION
        WHEN OTHERS THEN
            RAISE;
    END;


    /*----------------------------------------------------------------------------------------
        청구월 잔고 확인
    ----------------------------------------------------------------------------------------*/
    BEGIN
        SELECT  BALANCE INTO V_BALANCE
        FROM    (
                    SELECT  NVL(BALANCE, 0) BALANCE
                    FROM    GIBU.TBRA_REPT_BALANCE
                    WHERE   UPSO_CD = P_UPSO_CD
                    ORDER BY PROC_DAY DESC, PROC_NUM DESC
                )
        WHERE   ROWNUM = 1;
    EXCEPTION
        WHEN OTHERS THEN
            V_BALANCE := 0;
    END;


    /*----------------------------------------------------------------------------------------
        개업월, 등록월 차이 계산
    ----------------------------------------------------------------------------------------*/
--    IF (SUBSTR(V_INS_DAY, 1, 6) > SUBSTR(V_OPBI_DAY, 1, 6)) THEN
--        SELECT  MONTHS_BETWEEN(TO_DATE(SUBSTR(P_END_YRMN, 1, 6), 'YYYYMM'), TO_DATE(SUBSTR(V_INS_DAY, 1, 6), 'YYYYMM')) + 1 INTO V_INS_MM
--        FROM    DUAL;
--    ELSE
--        SELECT  MONTHS_BETWEEN(TO_DATE(SUBSTR(P_END_YRMN, 1, 6), 'YYYYMM'), TO_DATE(SUBSTR(V_OPBI_DAY, 1, 6), 'YYYYMM')) + 1 INTO V_INS_MM
--        FROM    DUAL;
--    END IF;
    SELECT  MONTHS_BETWEEN(TO_DATE(SUBSTR(P_DEMD_YRMN, 1, 6), 'YYYYMM'), TO_DATE(SUBSTR(V_INS_DAY, 1, 6), 'YYYYMM')) + 1 INTO V_INS_MM
    FROM    DUAL;

    
    /*----------------------------------------------------------------------------------------
        청구월부터 총 연체 개월수 계산
    ----------------------------------------------------------------------------------------*/
    BEGIN
        SELECT  COUNT(YRMN)
        INTO    V_DELAY_MMCNT
        FROM    (
                    SELECT  YRMN YRMN
                    FROM    GIBU.COPY_YRMN A
                    WHERE   YRMN NOT IN (
                                SELECT  NOTE_YRMN 
                                FROM    GIBU.TBRA_NOTE
                                WHERE   UPSO_CD = P_UPSO_CD
                            )
                    AND     YRMN  BETWEEN V_START_YRMN 
                                                   AND (
                                                             CASE WHEN V_CLSBS_YRMN IS NULL THEN V_END_YRMN
                                                                      WHEN V_CLSBS_YRMN > V_END_YRMN THEN V_END_YRMN
                                                                       ELSE V_CLSBS_YRMN END
                                                            )
                );
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            V_DELAY_MMCNT := 0;
    END;
    
    /*----------------------------------------------------------------------------------------
        시작월 종료월 사이에 있는 청구 월수 계산
    ----------------------------------------------------------------------------------------*/
    BEGIN
        SELECT  COUNT(YRMN)
        INTO    V_DEMD_MMCNT
        FROM    (
                    SELECT  YRMN YRMN
                    FROM    GIBU.COPY_YRMN A
                    WHERE   YRMN NOT IN (
                                SELECT  NOTE_YRMN 
                                FROM    GIBU.TBRA_NOTE
                                WHERE   UPSO_CD = P_UPSO_CD
                            )
                    AND     YRMN  BETWEEN V_START_YRMN 
                                                    AND (
                                                             CASE WHEN V_CLSBS_YRMN IS NULL THEN V_END_YRMN
                                                                      WHEN V_CLSBS_YRMN > V_END_YRMN THEN V_END_YRMN
                                                                       ELSE V_CLSBS_YRMN END
                                                            )
                );
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            V_DEMD_MMCNT := 0;
    END;
    
    -- 사용료계산을 하기 위한 월정료 조회(통합징수로 인해 함수로 모듈화) 2021-12-23 장태진
    -- 루프가 돌지 않을 때 null 이 반환되는 문제가 있어서 cursor 밖에서도 조회를 실시
    V_MONPRNCFEE_RETURN := GIBU.FT_GET_DEMD_MONPRNCFEE(P_UPSO_CD, V_DEMD_YRMN);
    
    V_MONPRNCFEE := GIBU.FT_SPLIT(V_MONPRNCFEE_RETURN, ',', 0);
    V_MONPRNCFEE2 := GIBU.FT_SPLIT(V_MONPRNCFEE_RETURN, ',', 1);
    V_BSTYP_CD   := GIBU.FT_SPLIT(V_MONPRNCFEE_RETURN, ',', 2);
    V_UPSO_GRAD  := GIBU.FT_SPLIT(V_MONPRNCFEE_RETURN, ',', 3);
    
    
    /*----------------------------------------------------------------------------------------
        월별 사용료 계산
    ----------------------------------------------------------------------------------------*/
    FOR CUR_DEMD IN (
                    SELECT  YRMN YRMN
                    FROM    GIBU.COPY_YRMN A
                    WHERE   YRMN NOT IN (
                                SELECT  NOTE_YRMN 
                                FROM    GIBU.TBRA_NOTE
                                WHERE   UPSO_CD = P_UPSO_CD
                            )
                    AND     YRMN  BETWEEN V_START_YRMN 
                                                    AND (
                                                             CASE WHEN V_CLSBS_YRMN IS NULL THEN V_END_YRMN
                                                                      WHEN V_CLSBS_YRMN > V_END_YRMN THEN V_END_YRMN
                                                                       ELSE V_CLSBS_YRMN END
                                                            )
                    ORDER BY YRMN
    ) LOOP
        
        /*----------------------------------------------------------------------------------------
            요금 적용일을 적용하여 월 사용료를 계산한다.
        ----------------------------------------------------------------------------------------*/
        
        V_TEMP_YRMN := CUR_DEMD.YRMN;
        
        --해당년월에 함저협 관리비율이 등록되었는지 확인
        /*SELECT COUNT(1)
          INTO V_BSCON_DEMD
          FROM GIBU.TBRA_BSCON_MNG_RATE
         WHERE BSCON_CD = 'T0000001'
           AND APPL_DAY < V_TEMP_YRMN || '32';*/
        
        -- 사용료계산을 하기 위한 월정료 조회(통합징수로 인해 함수로 모듈화) 2021-12-23 장태진
        V_MONPRNCFEE_RETURN := GIBU.FT_GET_DEMD_MONPRNCFEE(P_UPSO_CD, V_TEMP_YRMN);
        
        V_MONPRNCFEE := GIBU.FT_SPLIT(V_MONPRNCFEE_RETURN, ',', 0);
        V_MONPRNCFEE2 := GIBU.FT_SPLIT(V_MONPRNCFEE_RETURN, ',', 1);
        V_BSTYP_CD   := GIBU.FT_SPLIT(V_MONPRNCFEE_RETURN, ',', 2);
        V_UPSO_GRAD  := GIBU.FT_SPLIT(V_MONPRNCFEE_RETURN, ',', 3);

        /*----------------------------------------------------------------------------------------
            계산월과 청구월간 개월수 계산 
        ----------------------------------------------------------------------------------------*/
        SELECT  MONTHS_BETWEEN(TO_DATE(V_DEMD_YRMN, 'YYYYMM'), TO_DATE(V_TEMP_YRMN, 'YYYYMM')) + 1 INTO V_DELAY_MM FROM DUAL;
        
        IF (V_DELAY_MM > 1) THEN --청구월이 1인 경우에는 원금만 부여되므로 IF문 패스
            IF (V_DELAY_MMCNT > 14) THEN --연체개월수가 15개월 이상인 경우에는 중가산금을 12개월치만 부여해야하므로 IF문 IN
                IF(V_DELAY_MMCNT - V_DELAY_MM > 11) THEN --연체개월수에서 개월수를 뺀 개월수가 12이상이면 중가산금부여가 완료되었으므로 가산금만 부여하기 위해 2개월로 고정
                    V_DELAY_MM := 2;
                ELSE --12미만이면 중가산금을 부여해야하므로 연체개월수에서 14를 뺀 만큼을 개월수에서 빼서 중가산금을 계산
                    V_DELAY_MM := V_DELAY_MM - (V_DELAY_MMCNT - 14);
                END IF;
                IF(V_DELAY_MM < 0) THEN
                    RAISE_APPLICATION_ERROR(-20000, '전체 연체개월수보다 계산월과 청구월간 개월수가 더 큽니다.');
                END IF;
            END IF;
        END IF;
        
        GIBU.SP_GET_MM_AMT (
                P_UPSO_CD
            ,   V_OPBI_DAY
            ,   V_NEW_DAY
            ,   V_BRAN_CD
            ,   V_TEMP_YRMN
            ,   V_BSTYP_CD
            ,   V_MONPRNCFEE
            ,   V_DELAY_MMCNT
            ,   V_DELAY_MM
            ,   V_INS_MM
            ,   P_RECV_DAY
            ,   V_DEMD_YRMN
            ,   V_USE_AMT
            ,   V_ADDT_AMT
            ,   V_EADDT_AMT
            ,   V_DSCT_AMT
            ,   V_BALANCE
        );
        
        V_USE_AMT2 := V_USE_AMT;
        
        -- 자동이체인 경우 1% 할인
        IF (P_CRET_GBN = 'A') THEN
            -- 통합징수 사업자 중 계산서를 발행하는 업체의 경우 1% 할인이 적용되기 때문에 함저협 금액은 자동이체 할인을 제외한다.
            -- 합저협으로 인한 통합징수여부 조회(통합징수로 인해 함수로 모듈화) 2021-12-23 장태진
            V_BSCON_WON := GIBU.FT_GET_IS_BSCON(P_UPSO_CD, V_TEMP_YRMN);
            
            IF V_BSCON_WON > 0 THEN
                -- 함저협 지분율 불러와 자동이체 할인할 금액에서 제외
                SELECT MNG_RATE / 100
                  INTO V_BSCON_RATE
                  FROM (
                             SELECT MNG_RATE
                               FROM GIBU.TBRA_BSCON_MNG_RATE
                              WHERE BSCON_CD = 'T0000001'
                                AND BSTYP_CD = V_BSTYP_CD
                                AND APPL_DAY < V_TEMP_YRMN || '32'
                           ORDER BY APPL_DAY DESC
                       )
                 WHERE ROWNUM = 1;
                 
                V_DC_AMT := TRUNC((V_USE_AMT2 - (TRUNC(V_USE_AMT2 * V_BSCON_RATE))) * V_AUTO_DC, -1);
            ELSE
                V_DC_AMT   := TRUNC(V_USE_AMT2 * V_AUTO_DC, -1);
            END IF;
            V_USE_AMT  := V_USE_AMT - V_DC_AMT;
            V_DSCT_AMT := V_DSCT_AMT + V_DC_AMT;
        END IF;
        
        --음실련,음산협 통합징수
        --계약정보에 있어야 청구도 나가기 때문에 제일먼저 계약정보를 확인
        FOR CUR_BSCON IN (SELECT BSCON_CD
                               , SUM(MONPRNCFEE) AS MONPRNCFEE
                            FROM GIBU.TBRA_BSCON_CONTRINFO A
                           WHERE BSCON_CD <> 'T0000001'
                             AND UPSO_CD = P_UPSO_CD
                             AND APPL_DAY < V_TEMP_YRMN || '32'
                             AND SEQ = (SELECT MAX(SEQ) FROM GIBU.TBRA_BSCON_CONTRINFO WHERE BSCON_CD <> 'T0000001' AND BSCON_UPSO_CD = A.BSCON_UPSO_CD AND APPL_DAY < V_TEMP_YRMN || '32')
                           GROUP BY BSCON_CD)
        LOOP
            --청구자료가 나갔는지 확인
            SELECT COUNT(1)
              INTO V_BSCON_DEMD
              FROM GIBU.TBRA_DEMD_REPT_BSCON
             WHERE DEMD_YRMN = V_TEMP_YRMN
               AND UPSO_CD = P_UPSO_CD
               AND BSCON_CD = CUR_BSCON.BSCON_CD
               AND (PROC_AMT IS NULL OR PROC_AMT = 0);
            
            --청구자료가 나간경우 청구자료의 청구금액을 사용료에 추가
            IF V_BSCON_DEMD > 0 THEN
                SELECT SUM(DEMD_AMT)
                  INTO V_BSCON_TEMP_AMT
                  FROM GIBU.TBRA_DEMD_REPT_BSCON
                 WHERE DEMD_YRMN = V_TEMP_YRMN
                   AND UPSO_CD = P_UPSO_CD
                   AND BSCON_CD = CUR_BSCON.BSCON_CD
                   AND (PROC_AMT IS NULL OR PROC_AMT = 0);
                
                V_USE_AMT := V_USE_AMT + V_BSCON_TEMP_AMT;
            --청구자료가 나가지 않은 경우
            ELSE
                --업로드된 청구자료는 있는지 확인
                SELECT COUNT(1)
                  INTO V_BSCON_DEMD2
                  FROM GIBU.TBRA_BSCON_DEMD_UPLOAD
                 WHERE DEMD_YRMN = V_TEMP_YRMN
                   AND UPSO_CD = P_UPSO_CD
                   AND BSCON_CD = CUR_BSCON.BSCON_CD;
                
                --업로드된 청구자료가 있으면 해당 청구자료의 청구금액을 사용료에 추가
                IF V_BSCON_DEMD2 > 0 THEN
                    SELECT SUM(DEMD_AMT)
                      INTO V_BSCON_TEMP_AMT
                      FROM GIBU.TBRA_BSCON_DEMD_UPLOAD
                     WHERE DEMD_YRMN = V_TEMP_YRMN
                       AND UPSO_CD = P_UPSO_CD
                       AND BSCON_CD = CUR_BSCON.BSCON_CD;
                       
                    V_USE_AMT := V_USE_AMT + V_BSCON_TEMP_AMT;
                --업로드된 청구자료도 없으면 해당계약기준월정료를 사용료에 추가
                ELSE
                    V_USE_AMT := V_USE_AMT + CUR_BSCON.MONPRNCFEE;
                END IF;
            END IF;
        END LOOP;
        
        -- 자동이체인 경우 1% 할인
        /*IF (P_CRET_GBN = 'A') THEN
        
            --V_DC_AMT   := TRUNC(V_USE_AMT * V_AUTO_DC, -1);
            V_DC_AMT   := TRUNC(V_MONPRNCFEE * V_AUTO_DC, -1);
            V_USE_AMT  := V_USE_AMT - V_DC_AMT;
            V_DSCT_AMT := V_DSCT_AMT + V_DC_AMT;

        END IF;*/

        V_TUSE_AMT   := V_TUSE_AMT   + TRUNC(V_USE_AMT);
        V_TADDT_AMT  := V_TADDT_AMT  + TRUNC(V_ADDT_AMT);
        V_TEADDT_AMT := V_TEADDT_AMT + TRUNC(V_EADDT_AMT); 
        V_TDSCT_AMT  := V_TDSCT_AMT  + TRUNC(V_DSCT_AMT);

    END LOOP;
    
    /*--------------------------------------------------------------------------
        잔고가 있는 경우의 처리
        잔고가 원금보다 큰 경우 이에 대한 처리 진행
    --------------------------------------------------------------------------*/
    IF (V_BALANCE > 0) THEN
    
        IF ((V_TUSE_AMT - V_BALANCE) >= 0) THEN
            V_TUSE_AMT := V_TUSE_AMT - V_BALANCE;
        ELSE 
            V_TMP_USE_AMT  := V_TUSE_AMT;
            V_TUSE_AMT     := 0;
            V_BALANCE      := V_BALANCE - V_TMP_USE_AMT;
            
            IF ((V_TADDT_AMT- V_BALANCE) >= 0) THEN
                V_TADDT_AMT := V_TADDT_AMT - V_BALANCE;
            ELSE
                V_TMP_ADDT_AMT := V_TADDT_AMT;
                V_TADDT_AMT    := 0;
                V_BALANCE      := V_BALANCE - V_TMP_ADDT_AMT;
            
                IF ((V_TEADDT_AMT - V_BALANCE) >= 0) THEN
                    V_TEADDT_AMT := V_TEADDT_AMT - V_BALANCE;
                ELSE
                    V_TMP_EADDT_AMT := V_TEADDT_AMT;
                    V_TEADDT_AMT    := 0;
                    V_BALANCE       := V_BALANCE - V_TMP_EADDT_AMT;
                END IF;
            END IF;
        
        END IF;
        
    END IF;                
            
    IF (P_CRET_GBN = 'A') THEN
        V_DEMD_GBN := '31'; 
    ELSE
        V_DEMD_GBN := '32'; 
    END IF;

    -- CS 와 절사 문제 동기화
    V_TUSE_AMT  := V_TUSE_AMT;
    V_TADDT_AMT := V_TADDT_AMT
                 + TRUNC((V_TADDT_AMT + V_TEADDT_AMT), -1)
                 - TRUNC(V_TADDT_AMT, -1)
                 - TRUNC(V_TEADDT_AMT, -1);
    V_TDEMD_AMT := V_TUSE_AMT + V_TADDT_AMT + V_TEADDT_AMT;

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
    
    
    
EXCEPTION
    WHEN OTHERS THEN
        P_TUSE_AMT   := '-10';
        P_TADDT_AMT  := '0';
        P_TEADDT_AMT := '0';
        P_TDEMD_AMT  := '0';
        P_DEMD_MMCNT := '0';

END SP_GET_DEMD_AMT_HIST;