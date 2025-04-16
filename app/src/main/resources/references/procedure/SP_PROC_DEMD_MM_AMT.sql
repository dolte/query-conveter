create or replace PROCEDURE      SP_PROC_DEMD_MM_AMT
(
        P_UPSO_CD       IN     VARCHAR2    -- 업소 코드
    ,   P_START_YRMN    IN     VARCHAR2    -- 시작 년월
    ,   P_END_YRMN      IN     VARCHAR2    -- 종료 년월
    ,   P_DEMD_YRMN     IN     VARCHAR2    -- 청구 년월
    ,   P_CRET_GBN      IN     VARCHAR2    -- 생성 구분 (O : OCR / A : 자동이체 / M : MICR / C : 카드자동)
    ,   P_INSPRES_ID    IN     VARCHAR2    -- 등록자 ID
)
/******************************************************************************
   NAME:       SP_DEMD_MM_AMT
   PURPOSE:    대상기간 (시작년월, 종료년월) 을 입력받아 P_DEMD_YRMN 을
               기준으로 월별 사용료, 가산금, 중가산금을 계산하여 당월 청구 
               테이블에 저장한다.

   IN PARAM     P_UPSO_CD    IN     VARCHAR2    -- 업소 코드
            ,   P_START_YRMN IN     VARCHAR2    -- 시작 년월
            ,   P_END_YRMN   IN     VARCHAR2    -- 종료 년월
            ,   P_DEMD_YRMN  IN     VARCHAR2    -- 청구 년월
            ,   P_CRET_GBN   IN     VARCHAR2    -- 생성 구분 (O : OCR / A : 자동이체)

******************************************************************************/

AS
    V_OPBI_DAY          VARCHAR2(8);    -- 개업일
    V_INS_DAY           VARCHAR2(8);    -- 등록일
    V_NEW_DAY           VARCHAR2(8);    -- 최초 입금일
    V_BRAN_CD           VARCHAR2(8);    -- 지부 코드

    V_BSTYP_CD          VARCHAR2(8);    -- 청구월 업종
    V_UPSO_GRAD         VARCHAR2(2);    -- 청구월 업소 등급

    V_BALANCE           NUMBER := 0;    -- 잔고
    V_DEMD_MMCNT        NUMBER := 0;    -- 총 연체 개월수 
    V_MONPRNCFEE        NUMBER := 0;    -- 청구월 월정료
    V_MONPRNCFEE2       NUMBER := 0;    -- 청구월 월정료  

    V_USE_AMT           NUMBER := 0;    -- 청구월 사용료 (잔고 반영)
    V_ADDT_AMT          NUMBER := 0;    -- 청구월 가산금
    V_EADDT_AMT         NUMBER := 0;    -- 청구월 중 가산금 
    V_DEMD_AMT          NUMBER := 0;    -- 청구월 청구금액
    V_DSCT_AMT          NUMBER := 0;    -- 할인금액
    V_USE_AMT2          NUMBER := 0;    -- 통합징수에 대한 금액기준이 월정료가 아닌 월사용료로 변경됨에따라 GET_MM_AMT 에서 계산된 월사용료를 따로 보관하기 위함

    V_AUTO_DC           NUMBER := 0.01; -- 자동이체 할인률
    V_DC_AMT            NUMBER := 0;    -- 자동이체 할인 금액
    
    V_TEMP_YRMN         VARCHAR2(6);    -- TEMP 변수 : 요금 계산 년월
    V_DELAY_MMCNT       NUMBER := 0;    -- TEMP 변수 : 청구월부터 총 연체 개월
    V_DELAY_MM          NUMBER := 0;    -- TEMP 변수 : 월별 연체 개월
    V_INS_MM            NUMBER := 0;    -- TEMP 변수 : 등록월, 개업월간 차이
    V_TEMP_DS_AMT       NUMBER := 0;    -- TEMP 변수 : 할인금액
    V_INS_DATE          DATE;           -- 등록일       
    
    V_DEMD_NUM          VARCHAR2(4);    -- MICR 전용 청구번호
    
    V_BSCON_RATE        NUMBER := 0.0;  -- 타 단체 지분율
    V_BSCON_DEMD        NUMBER := 0;
    V_BSCON_DEMD2       NUMBER := 0;
    V_BSCON_TEMP_AMT    NUMBER := 0;
    V_BSCON_REPT        NUMBER := 0;
    V_CNT_OFFLINE       NUMBER := 0;        -- 청구 전월 오프라인 업소 포함 여부 
    V_KY_OFFLINE        VARCHAR2(6);        -- 금영로그수집기 인지 여부
    
    V_BSCON_WON         NUMBER := 0;
    
    V_MONPRNCFEE_RETURN VARCHAR2(100);
BEGIN

    V_INS_DATE := SYSDATE;

    BEGIN
        /*----------------------------------------------------------------------------------------
            업소 개업일, 최초 입금일, 등록일 정보 확인
        ----------------------------------------------------------------------------------------*/
        SELECT  OPBI_DAY
            ,   TO_CHAR(INS_DATE, 'YYYYMMDD')
            ,   NEW_DAY
            ,   BRAN_CD 
        INTO    V_OPBI_DAY
            ,   V_INS_DAY
            ,   V_NEW_DAY
            ,   V_BRAN_CD
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
        개업월, 등록월에서 청구월까지 차이 계산
    ----------------------------------------------------------------------------------------*/
    /*
    IF (SUBSTR(V_INS_DAY, 1, 6) > SUBSTR(V_OPBI_DAY, 1, 6)) THEN
        SELECT  MONTHS_BETWEEN(TO_DATE(SUBSTR(P_END_YRMN, 1, 6), 'YYYYMM'), TO_DATE(SUBSTR(V_INS_DAY, 1, 6), 'YYYYMM')) + 1 INTO V_INS_MM
        FROM    DUAL;
    ELSE
        SELECT  MONTHS_BETWEEN(TO_DATE(SUBSTR(P_END_YRMN, 1, 6), 'YYYYMM'), TO_DATE(SUBSTR(V_OPBI_DAY, 1, 6), 'YYYYMM')) + 1 INTO V_INS_MM
        FROM    DUAL;
    END IF;
    */
        
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
                    AND     YRMN  BETWEEN P_START_YRMN AND P_DEMD_YRMN
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
                    AND     YRMN  BETWEEN P_START_YRMN AND P_END_YRMN
                );
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            V_DEMD_MMCNT := 0;
    END;
    
    
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
                    AND     YRMN  BETWEEN P_START_YRMN AND P_END_YRMN
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
        SELECT  MONTHS_BETWEEN(TO_DATE(P_DEMD_YRMN, 'YYYYMM'), TO_DATE(V_TEMP_YRMN, 'YYYYMM')) + 1 INTO V_DELAY_MM FROM DUAL;
        
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
            ,   ''
            ,   P_DEMD_YRMN
            ,   V_USE_AMT
            ,   V_ADDT_AMT
            ,   V_EADDT_AMT
            ,   V_TEMP_DS_AMT
            ,   V_BALANCE
        );
        
        V_USE_AMT2 := V_USE_AMT;
        
        --음실련,음산협 통합징수
        SELECT COUNT(1)
          INTO V_BSCON_DEMD
          FROM GIBU.TBRA_BSCON_DEMD_UPLOAD
         WHERE DEMD_YRMN = V_TEMP_YRMN
           AND UPSO_CD = P_UPSO_CD;

        IF V_BSCON_DEMD > 0 THEN
            FOR CUR_BSCON IN (SELECT BSCON_CD
                                   , SUM(DEMD_AMT) AS DEMD_AMT
                                FROM GIBU.TBRA_BSCON_DEMD_UPLOAD
                               WHERE BSCON_CD <> 'T0000001'
                                 AND DEMD_YRMN = V_TEMP_YRMN
                                 AND UPSO_CD = P_UPSO_CD
                               GROUP BY BSCON_CD)
            LOOP
                V_BSCON_TEMP_AMT := CUR_BSCON.DEMD_AMT;
                
                --더넣은금액에서 음실련,음산협금액 처리
                IF V_BALANCE > 0 THEN
                    IF V_BALANCE > V_BSCON_TEMP_AMT THEN
                        V_BALANCE := V_BALANCE - V_BSCON_TEMP_AMT;
                        V_BSCON_TEMP_AMT := 0;
                    ELSE
                        V_BSCON_TEMP_AMT := V_BSCON_TEMP_AMT - V_BALANCE;
                        V_BALANCE := 0;
                    END IF;
                END IF;
                
                --음실련,음산협금액을 사용료에 추가
                V_USE_AMT := V_USE_AMT + V_BSCON_TEMP_AMT;

                -- 해당 업소의 청구내역이 있는지 확인
                SELECT COUNT(1)
                  INTO V_BSCON_DEMD2
                  FROM GIBU.TBRA_DEMD_REPT_BSCON
                 WHERE UPSO_CD   = P_UPSO_CD
                   AND DEMD_YRMN = V_TEMP_YRMN
                   AND BSCON_CD  = CUR_BSCON.BSCON_CD
                   AND SEQ       = (SELECT NVL(MAX(SEQ), 0) FROM GIBU.TBRA_DEMD_REPT_BSCON WHERE DEMD_YRMN = V_TEMP_YRMN AND BSCON_CD = CUR_BSCON.BSCON_CD AND UPSO_CD = P_UPSO_CD);
                   
                -- 해당 업소의 청구입금내역이 있는지 확인
                SELECT COUNT(1)
                  INTO V_BSCON_REPT
                  FROM GIBU.TBRA_DEMD_REPT_BSCON
                 WHERE BSCON_CD  = CUR_BSCON.BSCON_CD
                   AND UPSO_CD   = P_UPSO_CD
                   AND DEMD_YRMN = V_TEMP_YRMN
                   AND SEQ       = (SELECT NVL(MAX(SEQ), 0) FROM GIBU.TBRA_DEMD_REPT_BSCON WHERE DEMD_YRMN = V_TEMP_YRMN AND BSCON_CD = CUR_BSCON.BSCON_CD AND UPSO_CD = P_UPSO_CD)
                   AND REPT_YRMN IS NOT NULL;
                
                -- 청구내역만 있는 경우
                IF V_BSCON_DEMD2 > 0 AND V_BSCON_REPT = 0 THEN
                    UPDATE GIBU.TBRA_DEMD_REPT_BSCON
                       SET CERT_GBN   = P_CRET_GBN
                         , DEMD_DAY   = TO_CHAR(SYSDATE, 'YYYYMMDD')
                         , DEMD_NUM   = V_DEMD_NUM
                         , DEMD_AMT   = CUR_BSCON.DEMD_AMT
                         , MODPRES_ID = P_INSPRES_ID
                         , MOD_DATE   = SYSDATE
                     WHERE BSCON_CD  = CUR_BSCON.BSCON_CD
                       AND UPSO_CD   = P_UPSO_CD
                       AND DEMD_YRMN = V_TEMP_YRMN
                       AND SEQ       = (SELECT NVL(MAX(SEQ), 0) FROM GIBU.TBRA_DEMD_REPT_BSCON WHERE BSCON_CD = CUR_BSCON.BSCON_CD AND UPSO_CD = P_UPSO_CD AND DEMD_YRMN = V_TEMP_YRMN);
                -- 청구입금이 존재하는 경우
                ELSIF V_BSCON_DEMD2 > 0 AND V_BSCON_REPT > 0 THEN
                    -- 음실련,음산협 금액 저장
                    INSERT INTO GIBU.TBRA_DEMD_REPT_BSCON(DEMD_YRMN
                                                        , BSCON_CD
                                                        , UPSO_CD
                                                        , SEQ
                                                        , BRAN_CD
                                                        , BSTYP_CD
                                                        , MONPRNCFEE
                                                        , CERT_GBN
                                                        , DEMD_DAY
                                                        , DEMD_NUM
                                                        , DEMD_AMT
                                                        , INSPRES_ID
                                                        , INS_DATE)
                     VALUES (V_TEMP_YRMN
                           , CUR_BSCON.BSCON_CD
                           , P_UPSO_CD
                           , (SELECT MAX(SEQ)
                                FROM (SELECT NVL(MAX(SEQ), 0) + 1 AS SEQ FROM GIBU.TBRA_DEMD_REPT_BSCON WHERE DEMD_YRMN = V_TEMP_YRMN AND BSCON_CD = CUR_BSCON.BSCON_CD AND UPSO_CD = P_UPSO_CD
                                      UNION ALL
                                      SELECT NVL(MAX(SEQ), 0) + 1 AS SEQ FROM GIBU.TBRA_BSCON_RETURN_NOTE WHERE DEMD_YRMN = V_TEMP_YRMN AND BSCON_CD = CUR_BSCON.BSCON_CD AND UPSO_CD = P_UPSO_CD))
                           , V_BRAN_CD
                           , V_BSTYP_CD
                           , CUR_BSCON.DEMD_AMT
                           , P_CRET_GBN
                           , TO_CHAR(SYSDATE, 'YYYYMMDD')
                           , V_DEMD_NUM
                           , CUR_BSCON.DEMD_AMT
                           , P_INSPRES_ID
                           , SYSDATE);
                ELSE -- 청구내역이 없는경우
                    INSERT INTO GIBU.TBRA_DEMD_REPT_BSCON(DEMD_YRMN
                                                        , BSCON_CD
                                                        , UPSO_CD
                                                        , SEQ
                                                        , BRAN_CD
                                                        , BSTYP_CD
                                                        , MONPRNCFEE
                                                        , CERT_GBN
                                                        , DEMD_DAY
                                                        , DEMD_NUM
                                                        , DEMD_AMT
                                                        , INSPRES_ID
                                                        , INS_DATE)
                     VALUES (V_TEMP_YRMN
                           , CUR_BSCON.BSCON_CD
                           , P_UPSO_CD
                           , (SELECT NVL(MAX(SEQ) + 1, 0) FROM GIBU.TBRA_BSCON_RETURN_NOTE WHERE DEMD_YRMN = V_TEMP_YRMN AND BSCON_CD = CUR_BSCON.BSCON_CD AND UPSO_CD = P_UPSO_CD)
                           , V_BRAN_CD
                           , V_BSTYP_CD
                           , CUR_BSCON.DEMD_AMT
                           , P_CRET_GBN
                           , TO_CHAR(SYSDATE, 'YYYYMMDD')
                           , V_DEMD_NUM
                           , CUR_BSCON.DEMD_AMT
                           , P_INSPRES_ID
                           , SYSDATE);
                END IF;
            END LOOP;
        END IF;
        
        WRITE_LOG('GIBU.SP_PROC_DEMD_MM_AMT','###'||P_CRET_GBN||'###');
        
        -- 선납, 당월분이고 자동이체인 경우 1% 할인
        IF (P_CRET_GBN = 'A') THEN
        WRITE_LOG('GIBU.SP_PROC_DEMD_MM_AMT','2###'||P_CRET_GBN||'###');
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
            
            V_DSCT_AMT := V_TEMP_DS_AMT + V_DC_AMT;
            V_DEMD_AMT := TRUNC(V_USE_AMT) + TRUNC(V_ADDT_AMT) + TRUNC(V_EADDT_AMT) - V_DC_AMT;
        
            INSERT INTO GIBU.TBRA_DEMD_AUTO_MM (
                    DEMD_YRMN
                ,   UPSO_CD
                ,   START_YRMN
                ,   BRAN_CD
                ,   BSTYP_CD
                ,   UPSO_GRAD
                ,   MONPRNCFEE
                ,   USE_AMT
                ,   ADDT_AMT
                ,   EADDT_AMT
                ,   DSCT_AMT
                ,   DEMD_AMT
                ,   INSPRES_ID
                ,   INS_DATE
                ,   BALANCE
            )
            VALUES (
                    P_DEMD_YRMN
                ,   P_UPSO_CD
                ,   V_TEMP_YRMN
                ,   V_BRAN_CD
                ,   V_BSTYP_CD
                ,   V_UPSO_GRAD
                ,   V_MONPRNCFEE
                ,   TRUNC(V_USE_AMT)
                ,   TRUNC(V_ADDT_AMT)
                ,   TRUNC(V_EADDT_AMT)
                ,   V_DSCT_AMT
                ,   V_DEMD_AMT
                ,   P_INSPRES_ID
                ,   V_INS_DATE
                ,   V_BALANCE
            );
        ELSIF (P_CRET_GBN = 'M') THEN
            WRITE_LOG('GIBU.SP_PROC_DEMD_MM_AMT','3###'||P_CRET_GBN||'###');
            V_DEMD_AMT := TRUNC(V_USE_AMT) + TRUNC(V_ADDT_AMT) + TRUNC(V_EADDT_AMT);
            V_DSCT_AMT := V_TEMP_DS_AMT;
            
            BEGIN
                SELECT  LPAD(NVL(MAX(DEMD_NUM), 0) + 1, 4, '0') DEMD_NUM
                INTO   V_DEMD_NUM
                FROM   GIBU.TBRA_DEMD_INDTN
                WHERE  DEMD_DAY = TO_CHAR(SYSDATE, 'YYYYMMDD')
                AND    UPSO_CD = P_UPSO_CD;
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    V_DEMD_NUM := '9000';
            END;

            
            INSERT INTO GIBU.TBRA_DEMD_INDTN_MM (
                    DEMD_DAY
                ,   DEMD_NUM
                ,   UPSO_CD
                ,   START_YRMN
                ,   BRAN_CD
                ,   BSTYP_CD
                ,   UPSO_GRAD
                ,   MONPRNCFEE
                ,   USE_AMT
                ,   ADDT_AMT
                ,   EADDT_AMT
                ,   DSCT_AMT
                ,   DEMD_AMT
                ,   INSPRES_ID
                ,   INS_DATE
                ,   BALANCE
            )
            VALUES (
                    TO_CHAR(SYSDATE, 'YYYYMMDD')
                ,   V_DEMD_NUM
                ,   P_UPSO_CD
                ,   V_TEMP_YRMN
                ,   V_BRAN_CD
                ,   V_BSTYP_CD
                ,   V_UPSO_GRAD
                ,   V_MONPRNCFEE
                ,   TRUNC(V_USE_AMT)
                ,   TRUNC(V_ADDT_AMT)
                ,   TRUNC(V_EADDT_AMT)
                ,   V_DSCT_AMT
                ,   V_DEMD_AMT
                ,   P_INSPRES_ID
                ,   V_INS_DATE
                ,   V_BALANCE
            );
        ELSIF (P_CRET_GBN = 'C') THEN
        
            V_DEMD_AMT := TRUNC(V_USE_AMT) + TRUNC(V_ADDT_AMT) + TRUNC(V_EADDT_AMT);
            V_DSCT_AMT := V_TEMP_DS_AMT;
            
            INSERT INTO GIBU.TBRA_DEMD_CARD_MM (
                    DEMD_YRMN
                ,   UPSO_CD
                ,   START_YRMN
                ,   BRAN_CD
                ,   BSTYP_CD
                ,   UPSO_GRAD
                ,   MONPRNCFEE
                ,   USE_AMT
                ,   ADDT_AMT
                ,   EADDT_AMT
                ,   DSCT_AMT
                ,   DEMD_AMT
                ,   INSPRES_ID
                ,   INS_DATE
                ,   BALANCE
            )
            VALUES (
                    P_DEMD_YRMN
                ,   P_UPSO_CD
                ,   V_TEMP_YRMN
                ,   V_BRAN_CD
                ,   V_BSTYP_CD
                ,   V_UPSO_GRAD
                ,   V_MONPRNCFEE
                ,   TRUNC(V_USE_AMT)
                ,   TRUNC(V_ADDT_AMT)
                ,   TRUNC(V_EADDT_AMT)
                ,   V_DSCT_AMT
                ,   V_DEMD_AMT
                ,   P_INSPRES_ID
                ,   V_INS_DATE
                ,   V_BALANCE
            );
        ELSE

            V_DEMD_AMT := TRUNC(V_USE_AMT) + TRUNC(V_ADDT_AMT) + TRUNC(V_EADDT_AMT);
            V_DSCT_AMT := V_TEMP_DS_AMT;
            
            INSERT INTO GIBU.TBRA_DEMD_OCR_MM (
                    DEMD_YRMN
                ,   UPSO_CD
                ,   START_YRMN
                ,   BRAN_CD
                ,   BSTYP_CD
                ,   UPSO_GRAD
                ,   MONPRNCFEE
                ,   USE_AMT
                ,   ADDT_AMT
                ,   EADDT_AMT
                ,   DSCT_AMT
                ,   DEMD_AMT
                ,   INSPRES_ID
                ,   INS_DATE
                ,   BALANCE
            )
            VALUES (
                    P_DEMD_YRMN
                ,   P_UPSO_CD
                ,   V_TEMP_YRMN
                ,   V_BRAN_CD
                ,   V_BSTYP_CD
                ,   V_UPSO_GRAD
                ,   V_MONPRNCFEE
                ,   TRUNC(V_USE_AMT)
                ,   TRUNC(V_ADDT_AMT)
                ,   TRUNC(V_EADDT_AMT)
                ,   V_DSCT_AMT
                ,   V_DEMD_AMT
                ,   P_INSPRES_ID
                ,   V_INS_DATE
                ,   V_BALANCE
            );

        END IF;
        
        --해당업종이 함저협관리비율에 등록된 경우에만 함저협 금액 입력
        V_BSCON_DEMD := GIBU.FT_GET_IS_BSCON(P_UPSO_CD, V_TEMP_YRMN);

        IF V_TEMP_YRMN = P_END_YRMN AND V_BSCON_DEMD > 0 THEN
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
            
            /*SELECT  COUNT(*)
            INTO    V_CNT_OFFLINE
            FROM    GIBU.TBRA_OFF_UPSO_MNG 
            WHERE   ESTAB_YRMN = V_TEMP_YRMN
            AND     UPSO_CD = P_UPSO_CD;
            
            BEGIN
                 SELECT DSCT_YN
                 INTO   V_KY_OFFLINE
                 FROM   LOG.KDS_SHOP
                 WHERE ( V_TEMP_YRMN BETWEEN DSCT_START AND DSCT_END OR (DSCT_END IS NULL AND V_TEMP_YRMN >= DSCT_START))
                 AND    UPSO_CD = P_UPSO_CD;
            EXCEPTION
            WHEN OTHERS THEN
                 V_KY_OFFLINE := '0';
            END;

            IF (V_CNT_OFFLINE > 0 OR V_KY_OFFLINE = '1') THEN
                V_MONPRNCFEE := TRUNC(V_MONPRNCFEE * 0.7, -1);
            END IF;*/
               
            -- 해당 업소의 청구내역이 있는지 확인
            SELECT COUNT(1)
              INTO V_BSCON_DEMD2
              FROM GIBU.TBRA_DEMD_REPT_BSCON
             WHERE UPSO_CD   = P_UPSO_CD
               AND DEMD_YRMN = V_TEMP_YRMN
               AND BSCON_CD  = 'T0000001'
               AND SEQ       = (SELECT NVL(MAX(SEQ), 0) FROM GIBU.TBRA_DEMD_REPT_BSCON WHERE DEMD_YRMN = V_TEMP_YRMN AND BSCON_CD = 'T0000001' AND UPSO_CD = P_UPSO_CD);

            -- 해당 업소의 청구입금내역이 있는지 확인
            SELECT COUNT(1)
              INTO V_BSCON_REPT
              FROM GIBU.TBRA_DEMD_REPT_BSCON
             WHERE BSCON_CD  = 'T0000001'
               AND UPSO_CD   = P_UPSO_CD
               AND DEMD_YRMN = V_TEMP_YRMN
               AND SEQ       = (SELECT NVL(MAX(SEQ), 0) FROM GIBU.TBRA_DEMD_REPT_BSCON WHERE DEMD_YRMN = V_TEMP_YRMN AND BSCON_CD = 'T0000001' AND UPSO_CD = P_UPSO_CD)
               AND REPT_YRMN IS NOT NULL;

            -- 청구내역만 있는 경우
            IF V_BSCON_DEMD2 > 0 AND V_BSCON_REPT = 0 THEN
                UPDATE GIBU.TBRA_DEMD_REPT_BSCON
                   SET DEMD_MNG_RATE = V_BSCON_RATE
                     , CERT_GBN   = P_CRET_GBN
                     , DEMD_DAY   = TO_CHAR(SYSDATE, 'YYYYMMDD')
                     , DEMD_NUM   = V_DEMD_NUM
                     , MONPRNCFEE = V_USE_AMT2
                     , DEMD_AMT   = TRUNC(V_USE_AMT2 * V_BSCON_RATE)
                     , MODPRES_ID = P_INSPRES_ID
                     , MOD_DATE   = SYSDATE
                 WHERE BSCON_CD  = 'T0000001'
                   AND UPSO_CD   = P_UPSO_CD
                   AND DEMD_YRMN = V_TEMP_YRMN
                   AND SEQ       = (SELECT NVL(MAX(SEQ), 0) FROM GIBU.TBRA_DEMD_REPT_BSCON WHERE BSCON_CD = 'T0000001' AND UPSO_CD = P_UPSO_CD AND DEMD_YRMN = V_TEMP_YRMN);
            -- 청구입금이 존재하는 경우
            ELSIF V_BSCON_DEMD2 > 0 AND V_BSCON_REPT > 0 THEN
                -- 음실련,음산협 금액 저장
                INSERT INTO GIBU.TBRA_DEMD_REPT_BSCON(DEMD_YRMN
                                                    , BSCON_CD
                                                    , UPSO_CD
                                                    , SEQ
                                                    , BRAN_CD
                                                    , BSTYP_CD
                                                    , DEMD_MNG_RATE
                                                    , MONPRNCFEE
                                                    , CERT_GBN
                                                    , DEMD_DAY
                                                    , DEMD_NUM
                                                    , DEMD_AMT
                                                    , INSPRES_ID
                                                    , INS_DATE)
                 VALUES (V_TEMP_YRMN
                       , 'T0000001'
                       , P_UPSO_CD
                       , (SELECT MAX(SEQ)
                                FROM (SELECT NVL(MAX(SEQ), 0) + 1 AS SEQ FROM GIBU.TBRA_DEMD_REPT_BSCON WHERE DEMD_YRMN = V_TEMP_YRMN AND BSCON_CD = 'T0000001' AND UPSO_CD = P_UPSO_CD
                                      UNION ALL
                                      SELECT NVL(MAX(SEQ), 0) + 1 AS SEQ FROM GIBU.TBRA_BSCON_RETURN_NOTE WHERE DEMD_YRMN = V_TEMP_YRMN AND BSCON_CD = 'T0000001' AND UPSO_CD = P_UPSO_CD))
                       , V_BRAN_CD
                       , V_BSTYP_CD
                       , V_BSCON_RATE
                       , V_USE_AMT2
                       , P_CRET_GBN
                       , TO_CHAR(SYSDATE, 'YYYYMMDD')
                       , V_DEMD_NUM
                       , TRUNC(V_USE_AMT2 * V_BSCON_RATE)
                       , P_INSPRES_ID
                       , SYSDATE);
            ELSE -- 청구내역이 없는경우
                INSERT INTO GIBU.TBRA_DEMD_REPT_BSCON(DEMD_YRMN
                                                    , BSCON_CD
                                                    , UPSO_CD
                                                    , SEQ
                                                    , BRAN_CD
                                                    , BSTYP_CD
                                                    , DEMD_MNG_RATE
                                                    , MONPRNCFEE
                                                    , CERT_GBN
                                                    , DEMD_DAY
                                                    , DEMD_NUM
                                                    , DEMD_AMT
                                                    , INSPRES_ID
                                                    , INS_DATE)
                 VALUES (V_TEMP_YRMN
                       , 'T0000001'
                       , P_UPSO_CD
                       , (SELECT NVL(MAX(SEQ) + 1, 0) FROM GIBU.TBRA_BSCON_RETURN_NOTE WHERE DEMD_YRMN = V_TEMP_YRMN AND BSCON_CD = 'T0000001' AND UPSO_CD = P_UPSO_CD)
                       , V_BRAN_CD
                       , V_BSTYP_CD
                       , V_BSCON_RATE
                       , V_USE_AMT2
                       , P_CRET_GBN
                       , TO_CHAR(SYSDATE, 'YYYYMMDD')
                       , V_DEMD_NUM
                       , TRUNC(V_USE_AMT2 * V_BSCON_RATE)
                       , P_INSPRES_ID
                       , SYSDATE);
            END IF;
            
        END IF;
        
        -- 청구데이터가 생기지 않는 경우가 있어 변수 초기화해봄 (2017-07-21 장태진)
        V_BSCON_RATE     := 0.0;
        V_BSCON_DEMD     := 0;
        V_BSCON_DEMD2    := 0;
        V_BSCON_TEMP_AMT := 0;
        V_BSCON_REPT     := NULL;
        V_CNT_OFFLINE    := 0; 
        V_KY_OFFLINE     := NULL;
        V_BSCON_WON      := 0; 

    END LOOP;    
    

EXCEPTION
    WHEN OTHERS THEN
        V_USE_AMT    := -10;
        V_ADDT_AMT   := 0;
        V_EADDT_AMT  := 0;
        V_MONPRNCFEE := 0;

END;