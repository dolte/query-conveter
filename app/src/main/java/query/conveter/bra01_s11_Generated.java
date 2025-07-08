package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra01_s11_Generated {

    public static String SQLsel_each_bill_SEL1() {
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XA.BSCON_CD  ,  XC.BSCONHAN_NM  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MST  AA  WHERE  AA.BRAN_CD  =  XA.BRAN_CD   \n";
        query +=" AND  AA.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  #{apptnYrmn}  )  AS  DUPCNT  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  #{apptnYrmn})  >  0)  THEN  XB.MONPRNCFEE2  -  TRUNC(XB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  XB.BSTYP_CD))  ELSE  XB.MONPRNCFEE  END)  +  NVL((SELECT  NVL(ADDT_AMT  +  EADDT_AMT,  0)  FROM  GIBU.TBRA_DEMD_OCR_MM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  #{apptnYrmn}   \n";
        query +=" AND  START_YRMN  =   \n";
        query +=" (SELECT  MIN(START_YRMN)  FROM  GIBU.TBRA_DEMD_OCR_MM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  #{apptnYrmn})),  0)  AS  MONPRNCFEE  ,  GIBU.FT_SPLIT(GIBU.FT_GET_DEMD_MONPRNCFEE(XA.UPSO_CD,  #{apptnYrmn}),  ',',  0)  AS  MONPRNCFEE2  ,  SUBSTR(#{apptnYrmn},0,4)  ||  '년  '  ||  SUBSTR(#{apptnYrmn},5,2)  ||  '월  음악사용료'  AS  ISS_BRE  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  (CASE  WHEN  GIBU.FT_GET_ONOFF_DATA_GBN(UPSO_CD)  LIKE  '%무선로그%'  THEN  MONPRNCFEE  -  TRUNC(MONPRNCFEE  *  0.7,  -1)  ELSE  MONPRNCFEE  END)  AS  MONPRNCFEE  ,  (CASE  WHEN  GIBU.FT_GET_ONOFF_DATA_GBN(UPSO_CD)  LIKE  '%무선로그%'  THEN  MONPRNCFEE2  -  TRUNC(MONPRNCFEE2  *  0.7,  -1)  ELSE  MONPRNCFEE2  END)  AS  MONPRNCFEE2  ,  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  >=  0   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  <  10  THEN  0  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  >=  10   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  <=  20  THEN  TRUNC(ZB.MONPRNCFEE  *  0.5,  -1)  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  ,  (CASE  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  >=  0   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  <  10  THEN  0  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  >=  10   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  <=  20  THEN  TRUNC(ZB.MONPRNCFEE2  *  0.5,  -1)  ELSE  ZB.MONPRNCFEE2  END)  AS  MONPRNCFEE2  ,  ZB.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  #{upsoCd}  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  )  XB  ,  FIDU.TLEV_BSCON  XC  WHERE  XA.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XA.UPSO_CD  NOT  IN   \n";
        query +=" (SELECT  AA.UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO  AA  ,  (   \n";
        query +=" SELECT  MAX(A.AUTO_NUM)  AS  AUTO_NUM  ,  A.UPSO_CD  ,  A.TERM_YN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BILL_ISS_YN  =  '1'  GROUP  BY  A.UPSO_CD,  A.TERM_YN)  BB  WHERE  BB.TERM_YN  =  'N'   \n";
        query +=" AND  BB.AUTO_NUM  =  AA.AUTO_NUM   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD)  UNION  ALL   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XA.BSCON_CD  ,  XC.BSCONHAN_NM  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MST  AA  WHERE  AA.BRAN_CD  =  XA.BRAN_CD   \n";
        query +=" AND  AA.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  #{apptnYrmn}  )  AS  DUPCNT  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  #{apptnYrmn})  >  0)  THEN  TRUNC(XB.MONPRNCFEE2  -  TRUNC(XB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  XB.BSTYP_CD))  -  TRUNC((XB.MONPRNCFEE2  -  TRUNC(XB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  XB.BSTYP_CD)))  *  0.01,  -1))  ELSE  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y')  >  0  THEN  TRUNC(XB.MONPRNCFEE  -  TRUNC(XB.MONPRNCFEE  *  0.01,  -1))  ELSE  TRUNC(XB.MONPRNCFEE  -  TRUNC(XB.MONPRNCFEE  *  0.01,  -1),  -1)  END)  END)  +  NVL((SELECT  NVL(ADDT_AMT  +  EADDT_AMT,  0)  FROM  GIBU.TBRA_DEMD_AUTO_MM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  #{apptnYrmn}   \n";
        query +=" AND  START_YRMN  =   \n";
        query +=" (SELECT  MIN(START_YRMN)  FROM  GIBU.TBRA_DEMD_AUTO_MM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  #{apptnYrmn})),  0)  AS  MONPRNCFEE  ,  GIBU.FT_SPLIT(GIBU.FT_GET_DEMD_MONPRNCFEE(XA.UPSO_CD,  #{apptnYrmn}),  ',',  0)  AS  MONPRNCFEE2  ,  SUBSTR(#{apptnYrmn},0,4)  ||  '년  '  ||  SUBSTR(#{apptnYrmn},5,2)  ||  '월  음악사용료'  AS  ISS_BRE  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  (CASE  WHEN  GIBU.FT_GET_ONOFF_DATA_GBN(UPSO_CD)  LIKE  '%무선로그%'  THEN  MONPRNCFEE  -  TRUNC(MONPRNCFEE  *  0.7,  -1)  ELSE  MONPRNCFEE  END)  AS  MONPRNCFEE  ,  (CASE  WHEN  GIBU.FT_GET_ONOFF_DATA_GBN(UPSO_CD)  LIKE  '%무선로그%'  THEN  MONPRNCFEE2  -  TRUNC(MONPRNCFEE2  *  0.7,  -1)  ELSE  MONPRNCFEE2  END)  AS  MONPRNCFEE2  ,  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  >=  0   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  <  10  THEN  0  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  >=  10   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  <=  20  THEN  TRUNC(ZB.MONPRNCFEE  *  0.5,  -1)  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  ,  (CASE  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  >=  0   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  <  10  THEN  0  WHEN  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  >=  10   \n";
        query +=" AND  GIBU.FT_GET_CLSED_DAYS(ZB.UPSO_CD,  #{apptnYrmn})  <=  20  THEN  TRUNC(ZB.MONPRNCFEE2  *  0.5,  -1)  ELSE  ZB.MONPRNCFEE2  END)  AS  MONPRNCFEE2  ,  ZB.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  #{upsoCd}  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  )  XB  ,  FIDU.TLEV_BSCON  XC  ,  GIBU.TBRA_UPSO_AUTO  XE  WHERE  XA.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  XA.BILL_ISS_YN  =  '1'   \n";
        query +=" AND  XE.TERM_YN  =  'N'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XE.UPSO_CD  IN   \n";
        query +=" (SELECT  AA.UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO  AA  ,  (   \n";
        query +=" SELECT  MAX(A.AUTO_NUM)  AS  AUTO_NUM  ,  A.UPSO_CD  ,  A.TERM_YN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BILL_ISS_YN  =  '1'  GROUP  BY  A.UPSO_CD,  A.TERM_YN)  BB  WHERE  BB.TERM_YN  =  'N'   \n";
        query +=" AND  BB.AUTO_NUM  =  AA.AUTO_NUM   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD)   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD ";
        String xml ="";
            xml += "<select id=\"SQLsel_each_bill_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLmng_bill_iss_DEL8() {
        String    query="";
        query +=" Delete from GIBU.TBRA_BILL_ISS_DTL  \n";
        query +=" where APPRV_NUM=#{apprvNum}";
        String xml ="";
            xml += "<delete id=\"SQLmng_bill_iss_DEL8\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLmng_bill_iss_UPD12() {
        String    query="";
        query +=" Update GIBU.TBRA_BILL_ISS_DTL  \n";
        query +=" set MODPRES_ID=#{modpresId} , ISS_COMPL_YN=#{issComplYn} , ISS_AMT=#{issAmt} , BILL_GBN=#{billGbn} , ISS_BRE=#{issBre} , ISS_DAY=#{issDay} , MOD_DATE=SYSDATE , REMAK=#{remak}  \n";
        query +=" where DEMD_NUM=#{demdNum}  \n";
        query +=" and APPRV_NUM=#{apprvNum}";
        String xml ="";
            xml += "<update id=\"SQLmng_bill_iss_UPD12\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLmng_bill_iss_SEL1() {
        String    query="";
        query +=" SELECT  #{apptnYrmn}  ||  LPAD(NVL(MAX(TO_NUMBER(SUBSTR(DEMD_NUM,  7)))  +  1,  0),  4,  '0')  AS  NEW_DEMD_NUM  FROM  GIBU.TBRA_BILL_ISS_MST  WHERE  APPTN_YRMN  =  #{apptnYrmn} ";
        String xml ="";
            xml += "<select id=\"SQLmng_bill_iss_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLmng_bill_iss_SEL11() {
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_BILL_ISS_DTL  WHERE  DEMD_NUM  =  #{demdNum} ";
        String xml ="";
            xml += "<select id=\"SQLmng_bill_iss_SEL11\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLmng_bill_iss_INS2() {
        String    query="";
        query +=" Insert into GIBU.TBRA_BILL_ISS_MST (DEMD_NUM, INS_DATE, INSPRES_ID, APPTN_YRMN, UPSO_CD, APPTN_GBN, BRAN_CD, BSCON_CD)  \n";
        query +=" values(#{demdNum} , SYSDATE, #{inspresId} , #{apptnYrmn} , #{upsoCd} , #{apptnGbn} , #{branCd} , #{bsconCd} )";
        String xml ="";
            xml += "<insert id=\"SQLmng_bill_iss_INS2\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLmng_bill_iss_XIUD11() {
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_BILL_ISS_DTL (DEMD_NUM, APPRV_NUM, BILL_KND, BILL_GBN, SUPPBSCON_CD, ISS_BRE, ISS_AMT, ISS_DAY, REMAK, ISS_COMPL_YN, INSPRES_ID, INS_DATE) SELECT DEMD_NUM , ( SELECT TO_CHAR(SYSDATE, 'YYYYMMDD') || TRIM(TO_CHAR(NVL(MAX(TO_NUMBER(SUBSTR(APPRV_NUM, 9), 'XXX')), 0) + RN, '0XX')) AS APPRV_NUM FROM GIBU.TBRA_BILL_ISS_DTL WHERE APPRV_NUM LIKE TO_CHAR(SYSDATE, 'YYYYMMDD') || '%' AND TO_NUMBER(SUBSTR(APPRV_NUM, 9), 'XXX') > 0 ) AS APPRV_NUM , BILL_KND , BILL_GBN , SUPPBSCON_CD , ISS_BRE , ISS_AMT , ISS_DAY , REMAK , ISS_COMPL_YN , INSPRES_ID , SYSDATE FROM ( SELECT #{demdNum} AS DEMD_NUM , ROWNUM AS RN , BILL_KND , #{billGbn} AS BILL_GBN , SUPPBSCON_CD , #{issBre} AS ISS_BRE , TO_NUMBER(ISS_AMT) AS ISS_AMT , #{issDay} AS ISS_DAY , #{remak} AS REMAK , #{issComplYn} AS ISS_COMPL_YN , #{inspresId} AS INSPRES_ID FROM ( SELECT 'KOMCA' AS SUPPBSCON_CD , TO_NUMBER(#{komcaAmt}) AS ISS_AMT , 1 AS R_NUM , '4' AS BILL_KND FROM DUAL UNION ALL SELECT 'T0000001' AS SUPPBSCON_CD , FLOOR(#{monprncfee2} * (SELECT MNG_RATE / 100 FROM GIBU.TBRA_BSCON_MNG_RATE WHERE BSTYP_CD = GIBU.FT_GET_BSTYP_INFO(#{upsoCd}) AND APPL_DAY < #{issDay})) AS ISS_AMT , 2 AS R_NUM , '5' AS BILL_KND FROM DUAL WHERE GIBU.FT_GET_BSTYP_INFO(#{upsoCd}) IN ('k', 'l', 'o') UNION ALL SELECT BSCON_CD , (CASE WHEN BSCON_CD = 'T0000001' THEN FLOOR(#{monprncfee2} * (SELECT MNG_RATE / 100 FROM GIBU.TBRA_BSCON_MNG_RATE WHERE BSTYP_CD = GIBU.FT_GET_BSTYP_INFO(#{upsoCd}) AND APPL_DAY < #{issDay})) ELSE DEMD_AMT END) AS ISS_AMT , ROWNUM + 1 AS R_NUM , (CASE WHEN (SELECT ATAX_YN FROM GIBU.TBRA_BSCON_CONTRINFO WHERE UPSO_CD = A.UPSO_CD AND BSCON_CD = A.BSCON_CD AND BSCON_UPSO_CD = A.BSCON_UPSO_CD AND SEQ = (SELECT MAX(SEQ) FROM GIBU.TBRA_BSCON_CONTRINFO WHERE UPSO_CD = A.UPSO_CD AND BSCON_CD = A.BSCON_CD AND BSCON_UPSO_CD = A.BSCON_UPSO_CD)) = 'Y' THEN '6' ELSE '5' END) AS BILL_KND FROM GIBU.TBRA_BSCON_DEMD_UPLOAD A WHERE DEMD_YRMN = #{apptnYrmn} AND UPSO_CD = #{upsoCd} ) A )";
        String xml ="";
            xml += "<insert id=\"SQLmng_bill_iss_XIUD11\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLmng_bill_iss_DEL7() {
        String    query="";
        query +=" Delete from GIBU.TBRA_BILL_ISS_MST  \n";
        query +=" where DEMD_NUM=#{demdNum}";
        String xml ="";
            xml += "<delete id=\"SQLmng_bill_iss_DEL7\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLchk_group_bill_SEL1() {
        String    query="";
        query +=" SELECT  COUNT(*)  AS  DUPCNT,  NVL(MAX((SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MST  AA  ,  GIBU.TBRA_BILL_ISS_DTL  BB  WHERE  AA.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  AA.APPTN_YRMN  =  #{apptnYrmn}   \n";
        query +=" AND  AA.DEMD_NUM  =  BB.DEMD_NUM   \n";
        query +=" AND  BB.ISS_COMPL_YN  =  '2'   \n";
        query +=" AND  AA.APPTN_GBN  ='1'  )),0)  AS  ISS_COMPL_YN_CNT  FROM  GIBU.TBRA_BILL_ISS_MST  A  WHERE  BRAN_CD  =  #{branCd}   \n";
        query +=" AND  APPTN_YRMN  =  #{apptnYrmn}   \n";
        query +=" AND  APPTN_GBN  ='1' ";
        String xml ="";
            xml += "<select id=\"SQLchk_group_bill_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsel_bill_iss_SEL1() {
        String    query="";
        query +=" SELECT  A.DEMD_NUM  ,  A.UPSO_CD  ,  B.APPRV_NUM  ,  C.UPSO_NM  ,  A.BSCON_CD  ,  D.BSCONHAN_NM  ,  A.BRAN_CD  ,  E.DEPT_NM  AS  BRAN_NM  ,  A.APPTN_YRMN  ,  B.ISS_BRE  ,  B.ISS_AMT  AS  KOMCA_AMT  ,   \n";
        query +=" (SELECT  SUM(ISS_AMT)  FROM  GIBU.TBRA_BILL_ISS_DTL  WHERE  DEMD_NUM  =  A.DEMD_NUM)  AS  ISS_AMT  ,  B.BILL_GBN  ,  B.REMAK  ,  B.ISS_DAY  ,  DECODE((SELECT  COUNT(1)  FROM  FIDU.BILL_TRANS  WHERE  BILLSEQ  =  B.BILL_NUM),  0,  1,  1,  2)  AS  ISS_COMPL_YN  ,  NVL(B.ISS_COMPL_YN,  0)  AS  ISSADD_YN  ,  B.BILL_NUM  ,  DECODE(A.APPTN_GBN,  '1',  '단체',  '2',  '개별')  APPTN_GBN  ,  B.BILL_KND  ,  B.SUPPBSCON_CD  ,  (CASE  WHEN  B.SUPPBSCON_CD  =  'KOMCA'  THEN  '(사)한국음악저작권협회'  ELSE   \n";
        query +=" (SELECT  HANMB_NM  FROM  FIDU.TMEM_MB  WHERE  MB_CD  =  B.SUPPBSCON_CD)  END)  AS  SUPPBSCON_NM  ,  FIDU.GET_STAFF_NM(C.STAFF_CD)  AS  STAFF_NM  FROM  GIBU.TBRA_BILL_ISS_MST  A  ,  GIBU.TBRA_BILL_ISS_DTL  B  ,  GIBU.TBRA_UPSO  C  ,  FIDU.TLEV_BSCON  D  ,  INSA.TCPM_DEPT  E  WHERE  A.BRAN_CD  =  DECODE(#{branCd},  NULL,  A.BRAN_CD,  'AL',  A.BRAN_CD,  #{branCd})   \n";
        query +=" AND  A.DEMD_NUM  =  B.DEMD_NUM   \n";
        query +=" AND  B.BILL_KND  IN  ('4',  '5',  '6')   \n";
        query +=" AND  A.APPTN_YRMN  =  #{apptnYrmn}   \n";
        query +=" AND  C.BRAN_CD  =  A.BRAN_CD   \n";
        query +=" AND  C.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  D.BSCON_CD  =  A.BSCON_CD   \n";
        query +=" AND  E.GIBU  =  A.BRAN_CD  ORDER  BY  C.BRAN_CD,  A.INS_DATE,  A.BSCON_CD,  B.SUPPBSCON_CD ";
        String xml ="";
            xml += "<select id=\"SQLsel_bill_iss_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLbra01_s11_groupIssue_DEL2() {
        String    query="";
        query +=" Delete from GIBU.TBRA_BILL_ISS_MNG  \n";
        query +=" where APPTN_YRMN=SUBSTR(#{apptnYrmn1},0,6)  \n";
        query +=" and APPTN_GBN=#{apptnGbn}  \n";
        query +=" and BRAN_CD=#{branCd}";
        String xml ="";
            xml += "<delete id=\"SQLbra01_s11_groupIssue_DEL2\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLbra01_s11_groupIssue_SEL1() {
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  XA.BRAN_CD  ,  SUBSTR(SUBSTR(#{issueYrmn1},0,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(#{issueYrmn1},0,6),5,2)  ||  '월  음악사용료'  AS  ISS_BRE  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  SUBSTR(#{issueYrmn1},0,6))  =  0)   \n";
        query +=" AND  ((SELECT  COUNT(1)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  XA.UPSO_CD)  >  0)  THEN  XD.MONPRNCFEE  WHEN  GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  SUBSTR(#{issueYrmn1},0,6))  >  0  THEN  XD.MONPRNCFEE  -  TRUNC(XD.MONPRNCFEE  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  XD.BSTYP_CD))  ELSE  XD.TOT_DEMD_AMT  END)  AS  ISS_AMT  ,  2  AS  BILL_GBN  ,  0  AS  ISS_COMPL_YN  ,  'I'  AS  CRUD  ,  ''  AS  REMAK  ,  '0'  AS  ISSADD_YN  ,  #{issDay}  AS  ISS_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  GIBU.TBRA_DEMD_OCR  XD  WHERE  XA.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  XA.BILL_ISS_YN  =  '1'   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XD.DEMD_YRMN  =  SUBSTR(#{issueYrmn1},0,6)   \n";
        query +=" AND  XD.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.DEMD_GBN  =  '32'   \n";
        query +=" AND  XD.TOT_DEMD_AMT  >  0  UNION  ALL   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  XA.BRAN_CD  ,  SUBSTR(SUBSTR(#{issueYrmn1},0,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(#{issueYrmn1},0,6),5,2)  ||  '월  음악사용료'  AS  ISS_BRE  ,  XF.MONPRNCFEE  AS  ISS_AMT  ,  2  AS  BILL_GBN  ,  0  AS  ISS_COMPL_YN  ,  'I'  AS  CRUD  ,  ''  AS  REMAK  ,  '0'  AS  ISSADD_YN  ,  #{issDay}  AS  ISS_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZB.UPSO_CD,  SUBSTR(#{issueYrmn1},0,6))  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XF  WHERE  XA.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  XA.BILL_ISS_YN  =  '1'   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XF.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XA.UPSO_CD  NOT  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  SUBSTR(#{issueYrmn1},0,6)   \n";
        query +=" AND  BRAN_CD  =  #{branCd}   \n";
        query +=" AND  DEMD_GBN  =  '32'   \n";
        query +=" AND  TOT_DEMD_AMT  >  0  )   \n";
        query +=" AND  XA.UPSO_CD  NOT  IN  (   \n";
        query +=" SELECT  AA.UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO  AA  ,  (   \n";
        query +=" SELECT  MAX(A.AUTO_NUM)  AUTO_NUM  ,  A.UPSO_CD  ,  A.TERM_YN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BILL_ISS_YN  =  '1'  GROUP  BY  A.UPSO_CD,  A.TERM_YN  )  BB  WHERE  BB.TERM_YN  =  'N'   \n";
        query +=" AND  BB.AUTO_NUM  =  AA.AUTO_NUM   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD  )  UNION  ALL   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  XA.BRAN_CD  ,  SUBSTR(SUBSTR(#{issueYrmn1},0,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(#{issueYrmn1},0,6),5,2)  ||  '월  음악사용료'  AS  ISS_BRE  ,  XE.TOT_DEMD_AMT  AS  ISS_AMT  ,  2  AS  BILL_GBN  ,  0  AS  ISS_COMPL_YN  ,  'I'  AS  CRUD  ,  ''  AS  REMAK  ,  '0'  AS  ISSADD_YN  ,  #{issDay}  AS  ISS_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  GIBU.TBRA_DEMD_AUTO  XE  WHERE  XA.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  XA.BILL_ISS_YN  =  '1'   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XE.DEMD_YRMN  =  SUBSTR(#{issueYrmn1},0,6)   \n";
        query +=" AND  XE.DEMD_GBN  =  '31'   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XE.TOT_DEMD_AMT  >  0  UNION  ALL   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  XA.BRAN_CD  ,  SUBSTR(SUBSTR(#{issueYrmn1},0,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(#{issueYrmn1},0,6),5,2)  ||  '월  음악사용료'  AS  ISS_BRE  ,  TRUNC(XF.MONPRNCFEE  -  TRUNC(XF.MONPRNCFEE  *  0.01,  -1),  -1)  AS  ISS_AMT  ,  2  AS  BILL_GBN  ,  0  AS  ISS_COMPL_YN  ,  'I'  AS  CRUD  ,  ''  AS  REMAK  ,  '0'  AS  ISSADD_YN  ,  #{issDay}  AS  ISS_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  GIBU.TBRA_UPSO_AUTO  XE  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZB.UPSO_CD,  SUBSTR(#{issueYrmn1},0,6))  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XF  WHERE  XA.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  XA.BILL_ISS_YN  =  '1'   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XE.TERM_YN  =  'N'   \n";
        query +=" AND  XA.UPSO_CD  NOT  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_AUTO  WHERE  DEMD_YRMN  =  SUBSTR(#{issueYrmn1},0,6)   \n";
        query +=" AND  DEMD_GBN  =  '31'   \n";
        query +=" AND  TOT_DEMD_AMT  >  0  )   \n";
        query +=" AND  XE.UPSO_CD  IN  (   \n";
        query +=" SELECT  AA.UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO  AA  ,  (   \n";
        query +=" SELECT  MAX(A.AUTO_NUM)  AUTO_NUM  ,  A.UPSO_CD  ,  A.TERM_YN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BILL_ISS_YN  =  '1'  GROUP  BY  A.UPSO_CD,  A.TERM_YN  )  BB  WHERE  BB.TERM_YN  =  'N'   \n";
        query +=" AND  BB.AUTO_NUM  =  AA.AUTO_NUM   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD  )   \n";
        query +=" AND  XF.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD  ORDER  BY  BSCON_CD,  UPSO_CD ";
        String xml ="";
            xml += "<select id=\"SQLbra01_s11_groupIssue_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLbra01_s11_dupCheck_SEL11() {
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MNG  AA  WHERE  AA.BRAN_CD  =  XA.BRAN_CD   \n";
        query +=" AND  AA.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  SUBSTR(#{issueYrmn1},1,6)  )  AS  DUPCNT  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  SUBSTR(#{issueYrmn1},1,6))  =  0)   \n";
        query +=" AND  ((SELECT  COUNT(1)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  XA.UPSO_CD)  >  0)  THEN  XD.MONPRNCFEE  WHEN  GIBU.FT_GET_IS_BSCON(XA.UPSO_CD,  SUBSTR(#{issueYrmn1},1,6))  >  0  THEN  XD.MONPRNCFEE  -  TRUNC(XD.MONPRNCFEE  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  XD.BSTYP_CD))  ELSE  XD.TOT_DEMD_AMT  END)  AS  MONPRNCFEE  ,  SUBSTR(SUBSTR(#{issueYrmn1},1,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(#{issueYrmn1},1,6),5,2)  ||  '월  음악사용료'  AS  ISS_BRE  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  GIBU.TBRA_DEMD_OCR  XD  WHERE  XA.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  XA.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XD.DEMD_YRMN  =  SUBSTR(#{issueYrmn1},1,6)   \n";
        query +=" AND  XD.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.DEMD_GBN  =  '32'   \n";
        query +=" AND  XD.TOT_DEMD_AMT  >  0  UNION  ALL   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XA.BSCON_CD  ,  XB.BSCONHAN_NM  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MNG  AA  WHERE  AA.BRAN_CD  =  XA.BRAN_CD   \n";
        query +=" AND  AA.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  SUBSTR(#{issueYrmn1},1,6)  )  AS  DUPCNT  ,  XE.TOT_DEMD_AMT  AS  MONPRNCFEE  ,  SUBSTR(SUBSTR(#{issueYrmn1},1,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(#{issueYrmn1},1,6),5,2)  ||  '월  음악사용료'  AS  ISS_BRE  FROM  GIBU.TBRA_UPSO  XA  ,  FIDU.TLEV_BSCON  XB  ,  GIBU.TBRA_DEMD_AUTO  XE  WHERE  XA.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  XA.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  XB.BSCON_CD  =  XA.BSCON_CD   \n";
        query +=" AND  XE.DEMD_YRMN  =  SUBSTR(#{issueYrmn1},1,6)   \n";
        query +=" AND  XE.DEMD_GBN  =  '31'   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XE.TOT_DEMD_AMT  >  0  ORDER  BY  BSCON_CD,  UPSO_CD ";
        String xml ="";
            xml += "<select id=\"SQLbra01_s11_dupCheck_SEL11\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLbra01_s11_dupCheck_SEL3() {
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XA.BSCON_CD  ,  XC.BSCONHAN_NM  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MNG  AA  WHERE  AA.BRAN_CD  =  XA.BRAN_CD   \n";
        query +=" AND  AA.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  AA.APPTN_YRMN  =  SUBSTR(#{issueYrmn1},1,6)  )  AS  DUPCNT  ,  XB.MONPRNCFEE  ,  SUBSTR(SUBSTR(#{issueYrmn1},1,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(#{issueYrmn1},1,6),5,2)  ||  '월  음악사용료'  AS  ISS_BRE  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZB.UPSO_CD,  SUBSTR(#{issueYrmn1},1,6))  >  0)  THEN  ZB.MONPRNCFEE2  -  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE/100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  #{upsoCd}  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  FIDU.TLEV_BSCON  XC  WHERE  XA.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.BSCON_CD  =  XA.BSCON_CD ";
        String xml ="";
            xml += "<select id=\"SQLbra01_s11_dupCheck_SEL3\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLbra01_s11_groupDupCheck_SEL1() {
        String    query="";
        query +=" SELECT  COUNT(*)  AS  DUPCNT,  NVL(MAX((SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MNG  AA  WHERE  AA.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  AA.APPTN_YRMN  =  SUBSTR(#{apptnYrmn1},1,6)   \n";
        query +=" AND  ISS_COMPL_YN  =  '2'   \n";
        query +=" AND  APPTN_GBN  ='1'  )),0)  AS  ISS_COMPL_YN_CNT  FROM  GIBU.TBRA_BILL_ISS_MNG  WHERE  BRAN_CD  =  #{branCd}   \n";
        query +=" AND  APPTN_YRMN  =  SUBSTR(#{apptnYrmn1},1,6)   \n";
        query +=" AND  APPTN_GBN  ='1' ";
        String xml ="";
            xml += "<select id=\"SQLbra01_s11_groupDupCheck_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLbra01_s11_delete_chk_SEL1() {
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  FIDU.TTAC_BILL  WHERE  BILL_NUM  =  #{billNum} ";
        String xml ="";
            xml += "<select id=\"SQLbra01_s11_delete_chk_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLbra01_s11_dupCheck02_SEL1() {
        String    query="";
        query +=" SELECT  XB.MONPRNCFEE  ,  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_BILL_ISS_MNG  WHERE  BSCON_CD  =  #{bsconCd}   \n";
        query +=" AND  APPTN_YRMN  =  SUBSTR(#{issueYrmn1},0,6)  )  DUPCNT  ,  SUBSTR(SUBSTR(#{issueYrmn1},0,6),0,4)  ||  '년  '  ||  SUBSTR(SUBSTR(#{issueYrmn1},0,6),5,2)  ||  '월  음악사용료'  AS  CTENT  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  DECODE(ZD.UPSO_CD,  NULL,  ZB.MONPRNCFEE,  (ZB.MONPRNCFEE*0.99))  MONPRNCFEE  ,  ZC.GRADNM  ,  ZB.BSTYP_CD  ,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ,  A.UPSO_CD  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  A.APPL_DAY  <=  TO_CHAR(SYSDATE,  'YYYYMMDD')  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  ,  GIBU.TBRA_UPSO_AUTO  ZD  WHERE  ZA.UPSO_CD  =  ZB.UPSO_CD   \n";
        query +=" AND  ZA.JOIN_SEQ  =  ZB.JOIN_SEQ   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  'z'   \n";
        query +=" AND  ZD.TERM_YN(+)  ='N'   \n";
        query +=" AND  ZD.UPSO_CD(+)  =  ZB.UPSO_CD  )  XB  WHERE  XA.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  XA.BSCON_CD  =  #{bsconCd}   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD ";
        String xml ="";
            xml += "<select id=\"SQLbra01_s11_dupCheck02_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "D:\\kosmos\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLsel_each_bill_SEL1() + "\n";
        content += SQLmng_bill_iss_DEL8() + "\n";
        content += SQLmng_bill_iss_UPD12() + "\n";
        content += SQLmng_bill_iss_SEL1() + "\n";
        content += SQLmng_bill_iss_SEL11() + "\n";
        content += SQLmng_bill_iss_INS2() + "\n";
        content += SQLmng_bill_iss_XIUD11() + "\n";
        content += SQLmng_bill_iss_DEL7() + "\n";
        content += SQLchk_group_bill_SEL1() + "\n";
        content += SQLsel_bill_iss_SEL1() + "\n";
        content += SQLbra01_s11_groupIssue_DEL2() + "\n";
        content += SQLbra01_s11_groupIssue_SEL1() + "\n";
        content += SQLbra01_s11_dupCheck_SEL11() + "\n";
        content += SQLbra01_s11_dupCheck_SEL3() + "\n";
        content += SQLbra01_s11_groupDupCheck_SEL1() + "\n";
        content += SQLbra01_s11_delete_chk_SEL1() + "\n";
        content += SQLbra01_s11_dupCheck02_SEL1() + "\n";
        content += bottom;

        try {
            String fileName = "bra01_s01_1.xml";
            FileWriter fw = new FileWriter(dir + "bra01_s11.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

