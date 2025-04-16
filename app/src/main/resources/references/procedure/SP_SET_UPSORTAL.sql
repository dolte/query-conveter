create or replace PROCEDURE "SP_SET_UPSORTAL"
(
        P_UPSO_CD       IN     VARCHAR2    -- 업소코드
    ,   P_OPBI_DAY      IN     VARCHAR2    -- 개업일자
    ,   P_BSTYP_CD      IN     VARCHAR2    -- 업종
)
/******************************************************************************
   NAME:       SP_SET_UPSORTAL
   PURPOSE:    2014.12.04 김은정
                     징수규정 변경으로 함저협과 공유저작물 지분율을 공제 후 월정료로 재산정
                     업소 등록 시 과거 재산정 부분이 사용료 관리 부분에 자동등록 되는 프로그램 
******************************************************************************/

AS
    V_BEFORE_APPL_DAY    VARCHAR2(8);   -- 이전 적용일
    V_BEFORE_RATE           NUMBER := 0;   -- 이전 적용율
    
    V_FLAG_FIRST            NUMBER := 0;   -- 최초의 월정료 등록여부 (0:미등록, 1:등록)
 
    
BEGIN

    /*----------------------------------------------------------------------------------------
        월사용료 지분율을 전체 획득한다.
        최초의 값은 모든 사용료 정보들 중 적용일자가 가장 작은 일자로 했기때문에
        첫번째 LOOP에서는 무조건 BEFORE값만 넣고 끝낸다.
    ----------------------------------------------------------------------------------------*/
    BEGIN
         
           FOR CUR_RATE IN (
                SELECT APPL_DAY, RATE 
                FROM   GIBU.TBRA_FEERATE_HISTY
                WHERE  BSTYP_CD = P_BSTYP_CD
                ORDER BY APPL_DAY
           ) LOOP
                 
                 
                 
                        IF (P_OPBI_DAY >=  CUR_RATE.APPL_DAY) THEN
                            V_BEFORE_APPL_DAY := CUR_RATE.APPL_DAY;
                            V_BEFORE_RATE := CUR_RATE.RATE;
                        ELSE 
                            IF (V_FLAG_FIRST = 0) THEN
                               -- 1. BEFORE로 딱한번 등록한다.
                               SP_SET_UPSORTAL_INSERT(P_UPSO_CD, P_OPBI_DAY, V_BEFORE_RATE, P_OPBI_DAY, P_BSTYP_CD);
                               V_FLAG_FIRST := 1;
                            END IF;
                            
                            IF (P_OPBI_DAY < '20130601' AND V_BEFORE_APPL_DAY < '20130601' AND CUR_RATE.APPL_DAY > '20130601') THEN
                               SP_SET_UPSORTAL_INSERT(P_UPSO_CD, '20130601', V_BEFORE_RATE, P_OPBI_DAY, P_BSTYP_CD);
                            END IF;
                            
                            -- 2. 현재로 한번 등록한다.
                            SP_SET_UPSORTAL_INSERT(P_UPSO_CD, CUR_RATE.APPL_DAY, CUR_RATE.RATE, P_OPBI_DAY, P_BSTYP_CD);
                            
                            V_BEFORE_APPL_DAY := CUR_RATE.APPL_DAY;
                            V_BEFORE_RATE := CUR_RATE.RATE;
                        END IF;  
                
                
                
           END LOOP;
           
           IF (V_FLAG_FIRST = 0) THEN
              -- 1. BEFORE로 딱한번 등록한다.
              SP_SET_UPSORTAL_INSERT(P_UPSO_CD, P_OPBI_DAY, V_BEFORE_RATE, P_OPBI_DAY, P_BSTYP_CD);
           END IF;
           
           COMMIT;
    
    END;

    
END SP_SET_UPSORTAL;

 