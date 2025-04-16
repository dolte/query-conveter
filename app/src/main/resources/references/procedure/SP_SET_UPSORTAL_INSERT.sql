create or replace PROCEDURE      "SP_SET_UPSORTAL_INSERT"
(
        P_UPSO_CD       IN     VARCHAR2    -- 업소코드
    ,   P_APPL_DAY      IN     VARCHAR2    -- 적용일자
    ,   P_RATE          IN     NUMBER    -- 
    ,   P_OPBI_DAY      IN     VARCHAR2    -- 개업일자
    ,   P_BSTYP_CD      IN     VARCHAR2    
)
/******************************************************************************
   NAME:       SP_SET_UPSORTAL
   PURPOSE:    2014.12.04 김은정
                     징수규정 변경으로 함저협과 공유저작물 지분율을 공제 후 월정료로 재산정
                     업소 등록 시 과거 재산정 부분이 사용료 관리 부분에 자동등록 되는 프로그램 
******************************************************************************/

AS
    V_CHG_NUM               NUMBER := 0;  
    V_STNDAMT               NUMBER := 0;   
 
    
BEGIN

     BEGIN
        
            SELECT  LPAD(NVL(MAX(CHG_NUM), 0) + 1, 4, '0') CHG_NUM 
            INTO    V_CHG_NUM
            FROM    GIBU.TBRA_UPSORTAL_INFO 
            WHERE   UPSO_CD = P_UPSO_CD 
            AND     CHG_DAY = P_APPL_DAY
            AND   CHG_NUM LIKE '0%';
           
      EXCEPTION
          WHEN OTHERS THEN
               V_CHG_NUM := 0;
      END;
      
      IF (V_CHG_NUM <> 0) THEN
      
          --개업일자와 적용일자가 20130601보다 작으면 다른쿼리죵 월정액 획득 업종이 KLMNP이면 BEFORE로 만든다.
          IF (P_OPBI_DAY < 20130601 AND P_APPL_DAY < 20130601 AND (P_BSTYP_CD = 'o' OR P_BSTYP_CD = 'k' OR P_BSTYP_CD = 'l' OR P_BSTYP_CD = 'm' OR P_BSTYP_CD = 'n' OR P_BSTYP_CD = 'p')) THEN

             BEGIN
               SELECT STNDAMT
               INTO   V_STNDAMT
               FROM   GIBU.TBRA_BSTYPGRAD_TEMP2013
               WHERE BSTYP_CD || GRAD_GBN = (
                                             SELECT BSTYP_CD || UPSO_GRAD
                                             FROM   GIBU.TBRA_UPSORTAL_INFO
                                             WHERE  UPSO_CD = P_UPSO_CD
                                             AND    CHG_NUM = '9000'
                                            );
             EXCEPTION
                  WHEN OTHERS THEN
                      V_STNDAMT := 0;
             END;

          ELSE
             BEGIN
               SELECT STNDAMT
               INTO   V_STNDAMT
               FROM   GIBU.TBRA_BSTYPGRAD
               WHERE BSTYP_CD || GRAD_GBN = (
                                             SELECT BSTYP_CD || UPSO_GRAD
                                             FROM   GIBU.TBRA_UPSORTAL_INFO
                                             WHERE  UPSO_CD = P_UPSO_CD
                                             AND    CHG_NUM = '9000'
                                            );
             EXCEPTION
                  WHEN OTHERS THEN
                      V_STNDAMT := 0;
             END;


          END IF;
          
          IF (V_STNDAMT <> 0) THEN

            -- 노래방이면 노래방 정보 등록
            IF (P_BSTYP_CD = 'o') THEN
              INSERT INTO GIBU.TBRA_NOREBANG_INFO
                    (UPSO_CD, GRAD_NUM, CHG_DAY, CHG_NUM, CHG_BRAN, BSTYP_CD, GRAD_GBN, AREA, MCHNDAESU, INSPRES_ID, INS_DATE, STNDAMT)
                    (
                     SELECT  UPSO_CD
                           , (SELECT  NVL(MAX(GRAD_NUM), 0)+RNUM FROM GIBU.TBRA_NOREBANG_INFO WHERE UPSO_CD = P_UPSO_CD) --GRAD_NUM
                           , P_APPL_DAY    --CHG_DAY
                           , LPAD(V_CHG_NUM, 4,'0') --CHG_NUM
                           , CHG_BRAN
                           , BSTYP_CD
                           , GRAD_GBN
                           , AREA
                           , MCHNDAESU
                           , (SELECT INSPRES_ID FROM GIBU.TBRA_UPSO WHERE UPSO_CD = P_UPSO_CD) --INSPRES_ID
                           , SYSDATE      --INS_DATE
                           , CASE WHEN P_APPL_DAY < '20130601' THEN 
                                      (SELECT STNDAMT 
                                         FROM GIBU.TBRA_BSTYPGRAD_TEMP2013 
                                        WHERE BSTYP_CD = A.BSTYP_CD 
                                          AND GRAD_GBN = A.GRAD_GBN) 
                                  ELSE 
                                      (SELECT STNDAMT 
                                         FROM GIBU.TBRA_BSTYPGRAD 
                                        WHERE BSTYP_CD = A.BSTYP_CD 
                                          AND GRAD_GBN = A.GRAD_GBN)
                              END --STNDAMT 
                     FROM (
                             SELECT UPSO_CD
                                  , ROWNUM AS RNUM
                                  , CHG_BRAN
                                  , BSTYP_CD
                                  , GRAD_GBN
                                  , AREA
                                  , MCHNDAESU
                            FROM    GIBU.TBRA_NOREBANG_INFO  
                            WHERE   UPSO_CD = P_UPSO_CD
                            AND CHG_NUM = '9000'
                           )A
                   );
              END IF;

             --업소 월정료 등록
              INSERT INTO GIBU.TBRA_UPSORTAL_INFO
                        (UPSO_CD, CHG_DAY, CHG_NUM, CHG_BRAN, BSTYP_CD, UPSO_GRAD, MONPRNCFEE, APPL_DAY, MCHNDAESU, JOIN_SEQ, REMAK, INSPRES_ID, INS_DATE, USE_TIME, MONPRNCFEE2)
             (
              SELECT UPSO_CD
                        , P_APPL_DAY --CHG_DAY
                        , LPAD(V_CHG_NUM, 4,'0') --CHG_NUM
                        , CHG_BRAN --CHG_BRAN
                        , BSTYP_CD
                        , UPSO_GRAD
                        , TRUNC(DECODE(P_BSTYP_CD, 'o',(SELECT SUM(B.MCHNDAESU*B.STNDAMT) FROM GIBU.TBRA_NOREBANG_INFO B WHERE B.UPSO_CD = P_UPSO_CD AND B.CHG_DAY = P_APPL_DAY AND B.CHG_NUM = LPAD(V_CHG_NUM, 4,'0') AND B.CHG_BRAN = A.CHG_BRAN), V_STNDAMT) * P_RATE * 0.01, -1) --MONPRNCFEE
                        , P_APPL_DAY --APPL_DAY
                        , MCHNDAESU
                        , (SELECT NVL(MAX(JOIN_SEQ),0)+1 FROM GIBU.TBRA_UPSORTAL_INFO ) --JOIN_SEQ
                        , '' --REMAK
                        , (SELECT INSPRES_ID FROM GIBU.TBRA_UPSO WHERE UPSO_CD = P_UPSO_CD) --INSPRES_ID
                        , SYSDATE      --INS_DATE
                        , 0            --USE_TIME
                        , DECODE(P_BSTYP_CD, 'o',(SELECT SUM(B.MCHNDAESU*B.STNDAMT) FROM GIBU.TBRA_NOREBANG_INFO B WHERE B.UPSO_CD = P_UPSO_CD AND B.CHG_DAY = P_APPL_DAY AND B.CHG_NUM = LPAD(V_CHG_NUM, 4,'0') AND B.CHG_BRAN = A.CHG_BRAN), V_STNDAMT) --MONPRNCFEE2
               FROM GIBU.TBRA_UPSORTAL_INFO A
               WHERE UPSO_CD = P_UPSO_CD
               AND CHG_NUM = '9000'
              );
              
          END IF;

          
      END IF;

    
END SP_SET_UPSORTAL_INSERT;
 