package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class com01_r07_Generated {

    public static String SQLp_upso_select_SEL5() {
        String    query="";
        query +=" SELECT TA.UPSO_CD , TA.UPSO_NM , TA.UPSO_STAT , TA.MNGEMSTR_NM , TA.PERMMSTR_NM , TA.UPSO_PHON , TA.MNGEMSTR_PHONNUM , TA.STAFF_CD , TA.STAFF_NM , TA.ADDR , TA.MONPRNCFEE , TA.GRAD , TA.GRADNM , TA.NEW_DAY , CASE WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '관리중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '관리|고소' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '개발중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '개발|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL  ";
        query +=" AND TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN '폐업|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL) THEN '폐업' END UPSO_STAT_NM , CASE WHEN (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN 'Y' ELSE 'N' END GOSO_YN , TB.ACCU_DAY , TB.ACCU_NUM , TB.ACCU_BRAN , TB.ACCU_GBN , TB.JUDG_CD , DECODE((SELECT COUNT(1)  ";
        query +=" FROM LOG.KDS_SHOPROOM  ";
        query +=" WHERE CO_STATUS = '07001'  ";
        query +=" AND UPSO_CD = TA.UPSO_CD), 1, '수집', '비수집') AS LOG_GET  ";
        query +=" FROM (  ";
        query +=" SELECT XA.UPSO_CD , XA.UPSO_NM , XA.UPSO_STAT , XA.MNGEMSTR_NM , XA.PERMMSTR_NM , XA.UPSO_PHON , XA.MNGEMSTR_PHONNUM , XA.STAFF_CD , XD.HAN_NM STAFF_NM , XA.UPSO_NEW_ADDR1 ||' '|| XA.UPSO_NEW_ADDR2 || XA.UPSO_REF_INFO AS ADDR , XB.MONPRNCFEE , TRIM(XC.BSTYP_CD) || TRIM(XC.GRAD_GBN) GRAD , XC.GRADNM , XA.NEW_DAY , XE.ACCU , XA.UPSO_NEW_ZIP AS UPSO_ZIP , XA.CLSBS_YRMN , GIBU.GET_MNG_ZIP(XA.UPSO_CD) AS MNG_ZIP , (SELECT NVL(COURT_NM, TOWNTWSHP)  ";
        query +=" FROM FIDU.TENV_POST  ";
        query +=" WHERE BD_MNG_NUM = XA.UPSO_BD_MNG_NUM) DONG  ";
        query +=" FROM GIBU.TBRA_UPSO XA , GIBU.TBRA_UPSORTAL_INFO XB , GIBU.TBRA_BSTYPGRAD XC , INSA.TINS_MST01 XD , (  ";
        query +=" SELECT UPSO_CD , MAX(ACCU_DAY || ACCU_NUM) ACCU  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD IN (SELECT UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO  ";
        query +=" WHERE BRAN_CD = #{branCd})  ";
        query +=" GROUP BY UPSO_CD ) XE , (  ";
        query +=" SELECT MAX(JOIN_SEQ) JOIN_SEQ , UPSO_CD  ";
        query +=" FROM (  ";
        query +=" SELECT XB.UPSO_CD , XA.JOIN_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO XA , (  ";
        query +=" SELECT A.UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO A  ";
        query +=" WHERE A.UPSO_CD = NVL(#{upsoCd}, A.UPSO_CD)  ";
        query +=" AND A.BRAN_CD = #{branCd}  ";
        query +=" AND A.UPSO_NM LIKE DECODE( #{upsoNm} , NULL, A.UPSO_NM, '%' || #{upsoNm} || '%')  ";
        query +=" AND A.CLSBS_YRMN IS NULL  ";
        query +=" AND A.NEW_DAY IS NOT NULL ) XB  ";
        query +=" WHERE XA.UPSO_CD = XB.UPSO_CD ) ZA  ";
        query +=" GROUP BY ZA.UPSO_CD ) XF  ";
        query +=" WHERE XA.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.JOIN_SEQ = XF.JOIN_SEQ  ";
        query +=" AND XC.BSTYP_CD = XB.BSTYP_CD  ";
        query +=" AND XC.GRAD_GBN = XB.UPSO_GRAD  ";
        query +=" AND XD.STAFF_NUM(+) = XA.STAFF_CD  ";
        query +=" AND XE.UPSO_CD (+) = XA.UPSO_CD ) TA , GIBU.TBRA_ACCU TB  ";
        query +=" WHERE TB.UPSO_CD (+) = TA.UPSO_CD  ";
        query +=" AND TB.ACCU_DAY(+) = SUBSTR(TA.ACCU, 1, 8)  ";
        query +=" AND TB.ACCU_NUM(+) = SUBSTR(TA.ACCU, 9, 4)  ";
        query +=" AND TA.MNG_ZIP = NVL(#{mngZip}, TA.MNG_ZIP)  ";
        query +=" AND TA.DONG = NVL(#{dong}, TA.DONG)  ";
        query +=" ORDER BY TA.UPSO_NM  ";
        String xml ="";
            xml += "<select id=\"SQLp_upso_select_SEL5\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLp_upso_select_SEL6() {
        String    query="";
        query +=" SELECT TA.UPSO_CD , TA.UPSO_NM , TA.UPSO_STAT , TA.MNGEMSTR_NM , TA.PERMMSTR_NM , TA.UPSO_PHON , TA.MNGEMSTR_PHONNUM , TA.STAFF_CD , TA.STAFF_NM , TA.ADDR , TA.MONPRNCFEE , TA.GRAD , TA.GRADNM , TA.NEW_DAY , CASE WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '관리중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '관리|고소' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '개발중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '개발|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL  ";
        query +=" AND TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN '폐업|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL) THEN '폐업' END UPSO_STAT_NM , CASE WHEN (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN 'Y' ELSE 'N' END GOSO_YN , TB.ACCU_DAY , TB.ACCU_NUM , TB.ACCU_BRAN , TB.ACCU_GBN , TB.JUDG_CD , DECODE((SELECT COUNT(1)  ";
        query +=" FROM LOG.KDS_SHOPROOM  ";
        query +=" WHERE CO_STATUS = '07001'  ";
        query +=" AND UPSO_CD = TA.UPSO_CD), 1, '수집', '비수집') AS LOG_GET  ";
        query +=" FROM (  ";
        query +=" SELECT XA.UPSO_CD , XA.UPSO_NM , XA.UPSO_STAT , XA.MNGEMSTR_NM , XA.PERMMSTR_NM , XA.UPSO_PHON , XA.MNGEMSTR_PHONNUM , XA.STAFF_CD , XD.HAN_NM STAFF_NM , XA.UPSO_NEW_ADDR1 ||' '|| XA.UPSO_NEW_ADDR2 || XA.UPSO_REF_INFO AS ADDR , XB.MONPRNCFEE , TRIM(XC.BSTYP_CD) || TRIM(XC.GRAD_GBN) GRAD , XC.GRADNM , XA.NEW_DAY , XE.ACCU , XA.UPSO_NEW_ZIP AS UPSO_ZIP , XA.CLSBS_YRMN , GIBU.GET_MNG_ZIP(XA.UPSO_CD) AS MNG_ZIP , (SELECT NVL(COURT_NM, TOWNTWSHP)  ";
        query +=" FROM FIDU.TENV_POST  ";
        query +=" WHERE BD_MNG_NUM = XA.UPSO_BD_MNG_NUM) DONG  ";
        query +=" FROM GIBU.TBRA_UPSO XA , GIBU.TBRA_UPSORTAL_INFO XB , GIBU.TBRA_BSTYPGRAD XC , INSA.TINS_MST01 XD , (  ";
        query +=" SELECT UPSO_CD , MAX(ACCU_DAY || ACCU_NUM) ACCU  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD IN (SELECT UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO  ";
        query +=" WHERE BRAN_CD = #{branCd})  ";
        query +=" GROUP BY UPSO_CD ) XE , (  ";
        query +=" SELECT MAX(JOIN_SEQ) JOIN_SEQ , UPSO_CD  ";
        query +=" FROM (  ";
        query +=" SELECT XB.UPSO_CD , XA.JOIN_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO XA , (  ";
        query +=" SELECT A.UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO A  ";
        query +=" WHERE A.UPSO_CD = NVL(#{upsoCd}, A.UPSO_CD)  ";
        query +=" AND A.BRAN_CD = #{branCd}  ";
        query +=" AND A.UPSO_NM LIKE DECODE( #{upsoNm} , NULL, A.UPSO_NM, '%' || #{upsoNm} || '%')  ";
        query +=" AND A.CLSBS_YRMN IS NULL  ";
        query +=" AND A.NEW_DAY IS NULL ) XB  ";
        query +=" WHERE XA.UPSO_CD = XB.UPSO_CD ) ZA  ";
        query +=" GROUP BY ZA.UPSO_CD ) XF  ";
        query +=" WHERE XA.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.JOIN_SEQ = XF.JOIN_SEQ  ";
        query +=" AND XC.BSTYP_CD = XB.BSTYP_CD  ";
        query +=" AND XC.GRAD_GBN = XB.UPSO_GRAD  ";
        query +=" AND XD.STAFF_NUM(+) = XA.STAFF_CD  ";
        query +=" AND XE.UPSO_CD (+) = XA.UPSO_CD ) TA , GIBU.TBRA_ACCU TB  ";
        query +=" WHERE TB.UPSO_CD (+) = TA.UPSO_CD  ";
        query +=" AND TB.ACCU_DAY(+) = SUBSTR(TA.ACCU, 1, 8)  ";
        query +=" AND TB.ACCU_NUM(+) = SUBSTR(TA.ACCU, 9, 4)  ";
        query +=" AND TA.MNG_ZIP = NVL(#{mngZip}, TA.MNG_ZIP)  ";
        query +=" AND TA.DONG = NVL(#{dong}, TA.DONG)  ";
        query +=" ORDER BY TA.UPSO_NM  ";
        String xml ="";
            xml += "<select id=\"SQLp_upso_select_SEL6\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLp_upso_select_SEL7() {
        String    query="";
        query +=" SELECT TA.UPSO_CD , TA.UPSO_NM , TA.UPSO_STAT , TA.MNGEMSTR_NM , TA.PERMMSTR_NM , TA.UPSO_PHON , TA.MNGEMSTR_PHONNUM , TA.STAFF_CD , TA.STAFF_NM , TA.ADDR , TA.MONPRNCFEE , TA.GRAD , TA.GRADNM , TA.NEW_DAY , CASE WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '관리중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '관리|고소' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '개발중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '개발|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL  ";
        query +=" AND TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN '폐업|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL) THEN '폐업' END UPSO_STAT_NM , CASE WHEN (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN 'Y' ELSE 'N' END GOSO_YN , TB.ACCU_DAY , TB.ACCU_NUM , TB.ACCU_BRAN , TB.ACCU_GBN , TB.JUDG_CD , DECODE((SELECT COUNT(1)  ";
        query +=" FROM LOG.KDS_SHOPROOM  ";
        query +=" WHERE CO_STATUS = '07001'  ";
        query +=" AND UPSO_CD = TA.UPSO_CD), 1, '수집', '비수집') AS LOG_GET  ";
        query +=" FROM (  ";
        query +=" SELECT XA.UPSO_CD , XA.UPSO_NM , XA.UPSO_STAT , XA.MNGEMSTR_NM , XA.PERMMSTR_NM , XA.UPSO_PHON , XA.MNGEMSTR_PHONNUM , XA.STAFF_CD , XD.HAN_NM STAFF_NM , XA.UPSO_NEW_ADDR1 ||' '|| XA.UPSO_NEW_ADDR2 || XA.UPSO_REF_INFO AS ADDR , XB.MONPRNCFEE , TRIM(XC.BSTYP_CD) || TRIM(XC.GRAD_GBN) GRAD , XC.GRADNM , XA.NEW_DAY , XE.ACCU , XA.UPSO_NEW_ZIP AS UPSO_ZIP , XA.CLSBS_YRMN , GIBU.GET_MNG_ZIP(XA.UPSO_CD) AS MNG_ZIP , (SELECT NVL(COURT_NM, TOWNTWSHP)  ";
        query +=" FROM FIDU.TENV_POST  ";
        query +=" WHERE BD_MNG_NUM = XA.UPSO_BD_MNG_NUM) DONG  ";
        query +=" FROM GIBU.TBRA_UPSO XA , GIBU.TBRA_UPSORTAL_INFO XB , GIBU.TBRA_BSTYPGRAD XC , INSA.TINS_MST01 XD , (  ";
        query +=" SELECT UPSO_CD , MAX(ACCU_DAY || ACCU_NUM) ACCU  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD IN (SELECT UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO  ";
        query +=" WHERE BRAN_CD = #{branCd})  ";
        query +=" GROUP BY UPSO_CD ) XE , (  ";
        query +=" SELECT MAX(JOIN_SEQ) JOIN_SEQ , UPSO_CD  ";
        query +=" FROM (  ";
        query +=" SELECT XB.UPSO_CD , XA.JOIN_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO XA , (  ";
        query +=" SELECT A.UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO A  ";
        query +=" WHERE A.UPSO_CD = NVL(#{upsoCd}, A.UPSO_CD)  ";
        query +=" AND A.BRAN_CD = #{branCd}  ";
        query +=" AND A.UPSO_NM LIKE DECODE( #{upsoNm} , NULL, A.UPSO_NM, '%' || #{upsoNm} || '%')  ";
        query +=" AND A.CLSBS_YRMN IS NOT NULL ) XB  ";
        query +=" WHERE XA.UPSO_CD = XB.UPSO_CD ) ZA  ";
        query +=" GROUP BY ZA.UPSO_CD ) XF  ";
        query +=" WHERE XA.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.JOIN_SEQ = XF.JOIN_SEQ  ";
        query +=" AND XC.BSTYP_CD = XB.BSTYP_CD  ";
        query +=" AND XC.GRAD_GBN = XB.UPSO_GRAD  ";
        query +=" AND XD.STAFF_NUM(+) = XA.STAFF_CD  ";
        query +=" AND XE.UPSO_CD (+) = XA.UPSO_CD ) TA , GIBU.TBRA_ACCU TB  ";
        query +=" WHERE TB.UPSO_CD (+) = TA.UPSO_CD  ";
        query +=" AND TB.ACCU_DAY(+) = SUBSTR(TA.ACCU, 1, 8)  ";
        query +=" AND TB.ACCU_NUM(+) = SUBSTR(TA.ACCU, 9, 4)  ";
        query +=" AND (TA.MNG_ZIP IS NULL  ";
        query +=" OR TA.MNG_ZIP = NVL(#{mngZip}, TA.MNG_ZIP))  ";
        query +=" AND (TA.DONG IS NULL  ";
        query +=" OR TA.DONG = NVL(#{dong}, TA.DONG))  ";
        query +=" ORDER BY TA.UPSO_NM  ";
        String xml ="";
            xml += "<select id=\"SQLp_upso_select_SEL7\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLp_upso_select_SEL8() {
        String    query="";
        query +=" SELECT TA.UPSO_CD , TA.UPSO_NM , TA.UPSO_STAT , TA.MNGEMSTR_NM , TA.PERMMSTR_NM , TA.UPSO_PHON , TA.MNGEMSTR_PHONNUM , TA.STAFF_CD , TA.STAFF_NM , TA.ADDR , TA.MONPRNCFEE , TA.GRAD , TA.GRADNM , TA.NEW_DAY , CASE WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '관리중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '관리|고소' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '개발중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '개발|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL  ";
        query +=" AND TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN '폐업|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL) THEN '폐업' END UPSO_STAT_NM , CASE WHEN (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN 'Y' ELSE 'N' END GOSO_YN , TB.ACCU_DAY , TB.ACCU_NUM , TB.ACCU_BRAN , TB.ACCU_GBN , TB.JUDG_CD , DECODE((SELECT COUNT(1)  ";
        query +=" FROM LOG.KDS_SHOPROOM  ";
        query +=" WHERE CO_STATUS = '07001'  ";
        query +=" AND UPSO_CD = TA.UPSO_CD), 1, '수집', '비수집') AS LOG_GET  ";
        query +=" FROM (  ";
        query +=" SELECT XA.UPSO_CD , XA.UPSO_NM , XA.UPSO_STAT , XA.MNGEMSTR_NM , XA.PERMMSTR_NM , XA.UPSO_PHON , XA.MNGEMSTR_PHONNUM , XA.STAFF_CD , XD.HAN_NM STAFF_NM , XA.UPSO_NEW_ADDR1 ||' '|| XA.UPSO_NEW_ADDR2 || XA.UPSO_REF_INFO AS ADDR , XB.MONPRNCFEE , TRIM(XC.BSTYP_CD) || TRIM(XC.GRAD_GBN) GRAD , XC.GRADNM , XA.NEW_DAY , XE.ACCU , XA.UPSO_NEW_ZIP AS UPSO_ZIP , XA.CLSBS_YRMN , GIBU.GET_MNG_ZIP(XA.UPSO_CD) AS MNG_ZIP , (SELECT NVL(COURT_NM, TOWNTWSHP)  ";
        query +=" FROM FIDU.TENV_POST  ";
        query +=" WHERE BD_MNG_NUM = XA.UPSO_BD_MNG_NUM) DONG  ";
        query +=" FROM GIBU.TBRA_UPSO XA , GIBU.TBRA_UPSORTAL_INFO XB , GIBU.TBRA_BSTYPGRAD XC , INSA.TINS_MST01 XD , (  ";
        query +=" SELECT UPSO_CD , MAX(ACCU_DAY || ACCU_NUM) ACCU  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD IN (SELECT UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO  ";
        query +=" WHERE BRAN_CD = #{branCd})  ";
        query +=" GROUP BY UPSO_CD ) XE , (  ";
        query +=" SELECT MAX(JOIN_SEQ) JOIN_SEQ , UPSO_CD  ";
        query +=" FROM (  ";
        query +=" SELECT XB.UPSO_CD , XA.JOIN_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO XA , (  ";
        query +=" SELECT A.UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO A  ";
        query +=" WHERE A.UPSO_CD = NVL(#{upsoCd}, A.UPSO_CD)  ";
        query +=" AND A.BRAN_CD = #{branCd}  ";
        query +=" AND A.UPSO_NM LIKE DECODE( #{upsoNm} , NULL, A.UPSO_NM, '%' || #{upsoNm} || '%')  ";
        query +=" ) XB  ";
        query +=" WHERE XA.UPSO_CD = XB.UPSO_CD ) ZA  ";
        query +=" GROUP BY ZA.UPSO_CD ) XF  ";
        query +=" WHERE XA.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.JOIN_SEQ = XF.JOIN_SEQ  ";
        query +=" AND XC.BSTYP_CD = XB.BSTYP_CD  ";
        query +=" AND XC.GRAD_GBN = XB.UPSO_GRAD  ";
        query +=" AND XD.STAFF_NUM(+) = XA.STAFF_CD  ";
        query +=" AND XE.UPSO_CD (+) = XA.UPSO_CD ) TA , GIBU.TBRA_ACCU TB  ";
        query +=" WHERE TB.UPSO_CD (+) = TA.UPSO_CD  ";
        query +=" AND TB.ACCU_DAY(+) = SUBSTR(TA.ACCU, 1, 8)  ";
        query +=" AND TB.ACCU_NUM(+) = SUBSTR(TA.ACCU, 9, 4)  ";
        query +=" AND (TA.MNG_ZIP IS NULL  ";
        query +=" OR TA.MNG_ZIP = NVL(#{mngZip}, TA.MNG_ZIP))  ";
        query +=" AND (TA.DONG IS NULL  ";
        query +=" OR TA.DONG = NVL(#{dong}, TA.DONG))  ";
        query +=" ORDER BY TA.UPSO_NM  ";
        String xml ="";
            xml += "<select id=\"SQLp_upso_select_SEL8\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLp_demd_select_SEL1() {
        String    query="";
        query +=" SELECT  A.DEMD_YRMN  ,  A.UPSO_CD  ,  D.UPSO_NM  ,  A.BRAN_CD  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.DEMD_GBN  ,  E.CODE_NM  DEMD_GBN_NM  ,  A.MONPRNCFEE  ,  A.DEMD_MMCNT  ,  A.DSCT_AMT  ,  A.TOT_ADDT_AMT  ,  A.TOT_EADDT_AMT  ,  A.TOT_DEMD_AMT  ,  GIBU.FT_GET_LAST_REPT_YRMN(#{upsoCd},  6)  LAST_REPT_YRMN  FROM  GIBU.TBRA_DEMD_OCR  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  #{upsoCd}  )  B  ,  (   \n";
        query +=" SELECT  UPSO_NM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  #{upsoCd}  )  D  ,  (   \n";
        query +=" SELECT  HIGH_CD  ,  CODE_CD  ,  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00141'  )  E  WHERE  A.UPSO_CD  =  B.UPSO_CD(+)   \n";
        query +=" AND  A.DEMD_YRMN  =  B.DEMD_YRMN(+)   \n";
        query +=" AND  A.DEMD_GBN  =  E.CODE_CD   \n";
        query +=" AND  A.UPSO_CD  =  #{upsoCd}  ORDER  BY  DEMD_YRMN  DESC ";
        String xml ="";
            xml += "<select id=\"SQLp_demd_select_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLp_upso_select_old_SEL2() {
        String    query="";
        query +=" SELECT  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.UPSO_STAT  ,  TA.MNGEMSTR_NM  ,  TA.PERMMSTR_NM  ,  TA.UPSO_PHON  ,  TA.MNGEMSTR_PHONNUM  ,  TA.STAFF_CD  ,  TA.STAFF_NM  ,  TA.ADDR  ,  TA.MONPRNCFEE  ,  TA.GRAD  ,  TA.GRADNM  ,  TA.NEW_DAY  ,  CASE  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '관리중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '관리|고소'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '개발중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '개발|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  '폐업|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL)  THEN  '폐업'  END  UPSO_STAT_NM  ,  CASE  WHEN  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  'Y'  ELSE  'N'  END  GOSO_YN  ,  TB.ACCU_DAY  ,  TB.ACCU_NUM  ,  TB.ACCU_BRAN  ,  TB.ACCU_GBN  ,  TB.JUDG_CD  FROM  (   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.UPSO_STAT  ,  XA.MNGEMSTR_NM  ,  XA.PERMMSTR_NM  ,  XA.UPSO_PHON  ,  XA.MNGEMSTR_PHONNUM  ,  XA.STAFF_CD  ,  XD.HAN_NM  STAFF_NM  ,  XA.UPSO_ADDR1  ||  XA.UPSO_ADDR2  ADDR  ,  XB.MONPRNCFEE  ,  TRIM(XC.BSTYP_CD)  ||  TRIM(XC.GRAD_GBN)  GRAD  ,  XC.GRADNM  ,  XA.NEW_DAY  ,  XE.ACCU  ,  XA.UPSO_ZIP  ,  XA.MNG_ZIP  ,  XA.CLSBS_YRMN  FROM  GIBU.TBRA_UPSO  XA  ,  GIBU.TBRA_UPSORTAL_INFO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  INSA.TINS_MST01  XD  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(ACCU_DAY  ||  ACCU_NUM)  ACCU  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  #{branCd})  GROUP  BY  UPSO_CD  )  XE  ,  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  ,  UPSO_CD  FROM  (   \n";
        query +=" SELECT  XB.UPSO_CD  ,  XA.JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  FROM  GIBU.TBRA_UPSO  A  WHERE  A.UPSO_CD  =  NVL(#{upsoCd},  A.UPSO_CD)   \n";
        query +=" AND  A.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  A.UPSO_NM  LIKE  DECODE(  #{upsoNm}  ,  NULL,  A.UPSO_NM,  '%'  ||  #{upsoNm}  ||  '%')   \n";
        query +=" AND  (NVL(A.UPSO_PHON,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.UPSO_PHON,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_PHONNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.MNGEMSTR_PHONNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_HPNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.MNGEMSTR_HPNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_PHONNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.PERMMSTR_PHONNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_HPNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.PERMMSTR_HPNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%'))   \n";
        query +=" AND  (NVL(A.MNGEMSTR_NM,  '-')  LIKE  DECODE(  #{mngemstrNm}  ,  NULL,  NVL(A.MNGEMSTR_NM,  '-'),  '%'  ||  #{mngemstrNm}  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_NM,  '-')  LIKE  DECODE(  #{mngemstrNm}  ,  NULL,  NVL(A.PERMMSTR_NM,  '-'),  '%'  ||  #{mngemstrNm}  ||  '%'))   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  A.NEW_DAY  IS  NOT  NULL  )  XB  WHERE  XA.UPSO_CD  =  XB.UPSO_CD  )  ZA  GROUP  BY  ZA.UPSO_CD  )  XF  WHERE  XA.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.JOIN_SEQ  =  XF.JOIN_SEQ   \n";
        query +=" AND  XC.BSTYP_CD  =  XB.BSTYP_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XB.UPSO_GRAD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD   \n";
        query +=" AND  XE.UPSO_CD  (+)  =  XA.UPSO_CD  )  TA  ,  GIBU.TBRA_ACCU  TB  WHERE  TB.UPSO_CD  (+)  =  TA.UPSO_CD   \n";
        query +=" AND  TB.ACCU_DAY(+)  =  SUBSTR(TA.ACCU,  1,  8)   \n";
        query +=" AND  TB.ACCU_NUM(+)  =  SUBSTR(TA.ACCU,  9,  4)   \n";
        query +=" AND  TA.MNG_ZIP  IN  (   \n";
        query +=" SELECT  MNG_ZIP  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  MNG_ZIP  =  NVL(#{mngZip},  MNG_ZIP)   \n";
        query +=" AND  ATTE  =  NVL(#{atte},  ATTE)   \n";
        query +=" AND  DSRCCNTY  =  NVL(#{dsrccnty},  DSRCCNTY)  )  ORDER  BY  TA.UPSO_NM ";
        String xml ="";
            xml += "<select id=\"SQLp_upso_select_old_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLp_upso_select_old_SEL3() {
        String    query="";
        query +=" SELECT  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.UPSO_STAT  ,  TA.MNGEMSTR_NM  ,  TA.PERMMSTR_NM  ,  TA.UPSO_PHON  ,  TA.MNGEMSTR_PHONNUM  ,  TA.STAFF_CD  ,  TA.STAFF_NM  ,  TA.ADDR  ,  TA.MONPRNCFEE  ,  TA.GRAD  ,  TA.GRADNM  ,  TA.NEW_DAY  ,  CASE  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '관리중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '관리|고소'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '개발중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '개발|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  '폐업|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL)  THEN  '폐업'  END  UPSO_STAT_NM  ,  CASE  WHEN  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  'Y'  ELSE  'N'  END  GOSO_YN  ,  TB.ACCU_DAY  ,  TB.ACCU_NUM  ,  TB.ACCU_BRAN  ,  TB.ACCU_GBN  ,  TB.JUDG_CD  FROM  (   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.UPSO_STAT  ,  XA.MNGEMSTR_NM  ,  XA.PERMMSTR_NM  ,  XA.UPSO_PHON  ,  XA.MNGEMSTR_PHONNUM  ,  XA.STAFF_CD  ,  XD.HAN_NM  STAFF_NM  ,  XA.UPSO_ADDR1  ||  XA.UPSO_ADDR2  ADDR  ,  XB.MONPRNCFEE  ,  TRIM(XC.BSTYP_CD)  ||  TRIM(XC.GRAD_GBN)  GRAD  ,  XC.GRADNM  ,  XA.CLSBS_YRMN  ,  XA.NEW_DAY  ,  XE.ACCU  ,  XA.UPSO_ZIP  ,  XA.MNG_ZIP  FROM  GIBU.TBRA_UPSO  XA  ,  GIBU.TBRA_UPSORTAL_INFO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  INSA.TINS_MST01  XD  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(ACCU_DAY  ||  ACCU_NUM)  ACCU  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  #{branCd})  GROUP  BY  UPSO_CD  )  XE  ,  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  ,  UPSO_CD  FROM  (   \n";
        query +=" SELECT  XB.UPSO_CD  ,  XA.JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  FROM  GIBU.TBRA_UPSO  A  WHERE  A.UPSO_CD  =  NVL(#{upsoCd},  A.UPSO_CD)   \n";
        query +=" AND  A.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  A.UPSO_NM  LIKE  DECODE(  #{upsoNm}  ,  NULL,  A.UPSO_NM,  '%'  ||  #{upsoNm}  ||  '%')   \n";
        query +=" AND  (NVL(A.UPSO_PHON,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.UPSO_PHON,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_PHONNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.MNGEMSTR_PHONNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_HPNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.MNGEMSTR_HPNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_PHONNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.PERMMSTR_PHONNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_HPNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.PERMMSTR_HPNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%'))   \n";
        query +=" AND  (NVL(A.MNGEMSTR_NM,  '-')  LIKE  DECODE(  #{mngemstrNm}  ,  NULL,  NVL(A.MNGEMSTR_NM,  '-'),  '%'  ||  #{mngemstrNm}  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_NM,  '-')  LIKE  DECODE(  #{mngemstrNm}  ,  NULL,  NVL(A.PERMMSTR_NM,  '-'),  '%'  ||  #{mngemstrNm}  ||  '%'))   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  A.NEW_DAY  IS  NULL  )  XB  WHERE  XA.UPSO_CD  =  XB.UPSO_CD  )  ZA  GROUP  BY  ZA.UPSO_CD  )  XF  WHERE  XA.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.JOIN_SEQ  =  XF.JOIN_SEQ   \n";
        query +=" AND  XC.BSTYP_CD  =  XB.BSTYP_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XB.UPSO_GRAD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD   \n";
        query +=" AND  XE.UPSO_CD  (+)  =  XA.UPSO_CD  )  TA  ,  GIBU.TBRA_ACCU  TB  WHERE  TB.UPSO_CD  (+)  =  TA.UPSO_CD   \n";
        query +=" AND  TB.ACCU_DAY(+)  =  SUBSTR(TA.ACCU,  1,  8)   \n";
        query +=" AND  TB.ACCU_NUM(+)  =  SUBSTR(TA.ACCU,  9,  4)   \n";
        query +=" AND  TA.MNG_ZIP  IN  (   \n";
        query +=" SELECT  MNG_ZIP  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  MNG_ZIP  =  NVL(#{mngZip},  MNG_ZIP)   \n";
        query +=" AND  ATTE  =  NVL(#{atte},  ATTE)   \n";
        query +=" AND  DSRCCNTY  =  NVL(#{dsrccnty},  DSRCCNTY)  )  ORDER  BY  TA.UPSO_NM ";
        String xml ="";
            xml += "<select id=\"SQLp_upso_select_old_SEL3\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLp_upso_select_old_SEL4() {
        String    query="";
        query +=" SELECT  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.UPSO_STAT  ,  TA.MNGEMSTR_NM  ,  TA.PERMMSTR_NM  ,  TA.UPSO_PHON  ,  TA.MNGEMSTR_PHONNUM  ,  TA.STAFF_CD  ,  TA.STAFF_NM  ,  TA.ADDR  ,  TA.MONPRNCFEE  ,  TA.GRAD  ,  TA.GRADNM  ,  TA.NEW_DAY  ,  CASE  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '관리중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '관리|고소'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '개발중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '개발|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  '폐업|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL)  THEN  '폐업'  END  UPSO_STAT_NM  ,  CASE  WHEN  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  'Y'  ELSE  'N'  END  GOSO_YN  ,  TB.ACCU_DAY  ,  TB.ACCU_NUM  ,  TB.ACCU_BRAN  ,  TB.ACCU_GBN  ,  TB.JUDG_CD  FROM  (   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.UPSO_STAT  ,  XA.MNGEMSTR_NM  ,  XA.PERMMSTR_NM  ,  XA.UPSO_PHON  ,  XA.MNGEMSTR_PHONNUM  ,  XA.STAFF_CD  ,  XD.HAN_NM  STAFF_NM  ,  XA.UPSO_ADDR1  ||  XA.UPSO_ADDR2  ADDR  ,  XB.MONPRNCFEE  ,  TRIM(XC.BSTYP_CD)  ||  TRIM(XC.GRAD_GBN)  GRAD  ,  XC.GRADNM  ,  XA.NEW_DAY  ,  XE.ACCU  ,  XA.UPSO_ZIP  ,  XA.MNG_ZIP  ,  XA.CLSBS_YRMN  FROM  GIBU.TBRA_UPSO  XA  ,  GIBU.TBRA_UPSORTAL_INFO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  INSA.TINS_MST01  XD  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(ACCU_DAY  ||  ACCU_NUM)  ACCU  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  #{branCd})  GROUP  BY  UPSO_CD  )  XE  ,  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  ,  UPSO_CD  FROM  (   \n";
        query +=" SELECT  XB.UPSO_CD  ,  XA.JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  FROM  GIBU.TBRA_UPSO  A  WHERE  A.UPSO_CD  =  NVL(#{upsoCd},  A.UPSO_CD)   \n";
        query +=" AND  A.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  A.UPSO_NM  LIKE  DECODE(  #{upsoNm}  ,  NULL,  A.UPSO_NM,  '%'  ||  #{upsoNm}  ||  '%')   \n";
        query +=" AND  (NVL(A.UPSO_PHON,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.UPSO_PHON,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_PHONNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.MNGEMSTR_PHONNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_HPNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.MNGEMSTR_HPNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_PHONNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.PERMMSTR_PHONNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_HPNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.PERMMSTR_HPNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%'))   \n";
        query +=" AND  (NVL(A.MNGEMSTR_NM,  '-')  LIKE  DECODE(  #{mngemstrNm}  ,  NULL,  NVL(A.MNGEMSTR_NM,  '-'),  '%'  ||  #{mngemstrNm}  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_NM,  '-')  LIKE  DECODE(  #{mngemstrNm}  ,  NULL,  NVL(A.PERMMSTR_NM,  '-'),  '%'  ||  #{mngemstrNm}  ||  '%'))   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NOT  NULL  )  XB  WHERE  XA.UPSO_CD  =  XB.UPSO_CD  )  ZA  GROUP  BY  ZA.UPSO_CD  )  XF  WHERE  XA.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.JOIN_SEQ  =  XF.JOIN_SEQ   \n";
        query +=" AND  XC.BSTYP_CD  =  XB.BSTYP_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XB.UPSO_GRAD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD   \n";
        query +=" AND  XE.UPSO_CD  (+)  =  XA.UPSO_CD  )  TA  ,  GIBU.TBRA_ACCU  TB  WHERE  TB.UPSO_CD  (+)  =  TA.UPSO_CD   \n";
        query +=" AND  TB.ACCU_DAY(+)  =  SUBSTR(TA.ACCU,  1,  8)   \n";
        query +=" AND  TB.ACCU_NUM(+)  =  SUBSTR(TA.ACCU,  9,  4)   \n";
        query +=" AND  TA.MNG_ZIP  IN  (   \n";
        query +=" SELECT  MNG_ZIP  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  MNG_ZIP  =  NVL(#{mngZip},  MNG_ZIP)   \n";
        query +=" AND  ATTE  =  NVL(#{atte},  ATTE)   \n";
        query +=" AND  DSRCCNTY  =  NVL(#{dsrccnty},  DSRCCNTY)  )  ORDER  BY  TA.UPSO_NM ";
        String xml ="";
            xml += "<select id=\"SQLp_upso_select_old_SEL4\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLp_upso_select_old_SEL26() {
        String    query="";
        query +=" SELECT  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.UPSO_STAT  ,  TA.MNGEMSTR_NM  ,  TA.PERMMSTR_NM  ,  TA.UPSO_PHON  ,  TA.MNGEMSTR_PHONNUM  ,  TA.STAFF_CD  ,  TC.HAN_NM  STAFF_NM  ,  TA.UPSO_ADDR1||'  '||TA.UPSO_ADDR2  ADDR  ,  TB.MONPRNCFEE  ,  TRIM(TB.BSTYP_CD)  ||  TRIM(TB.UPSO_GRAD)  GRAD  ,  TD.GRADNM  ,  ''  NEW_DAY  ,  '가업소'  UPSO_STAT_NM  ,  'N'  GOSO_YN  ,  ''  ACCU_DAY  ,  ''  ACCU_NUM  ,  ''  ACCU_BRAN  ,  ''  ACCU_GBN  ,  ''  JUDG_CD  FROM  GIBU.TBRA_UPSO  TA  ,  GIBU.TBRA_UPSORTAL_INFO  TB  ,  INSA.TINS_MST01  TC  ,  GIBU.TBRA_BSTYPGRAD  TD  WHERE  TA.UPSO_STAT  =  '2'   \n";
        query +=" AND  TA.UPSO_CD  =  NVL(#{upsoCd},  TA.UPSO_CD)   \n";
        query +=" AND  TA.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  TA.UPSO_NM  LIKE  DECODE(  #{upsoNm}  ,  NULL,  TA.UPSO_NM,  '%'  ||  #{upsoNm}  ||  '%')   \n";
        query +=" AND  TB.UPSO_CD(+)  =  TA.UPSO_CD   \n";
        query +=" AND  TC.STAFF_NUM(+)  =  TA.STAFF_CD   \n";
        query +=" AND  TD.GRAD_GBN(+)  =  TB.BSTYP_CD  ORDER  BY  TA.UPSO_NM ";
        String xml ="";
            xml += "<select id=\"SQLp_upso_select_old_SEL26\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLp_upso_select_old_SEL6() {
        String    query="";
        query +=" SELECT  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.UPSO_STAT  ,  TA.MNGEMSTR_NM  ,  TA.PERMMSTR_NM  ,  TA.UPSO_PHON  ,  TA.MNGEMSTR_PHONNUM  ,  TA.STAFF_CD  ,  TA.STAFF_NM  ,  TA.ADDR  ,  TA.MONPRNCFEE  ,  TA.GRAD  ,  TA.GRADNM  ,  TA.NEW_DAY  ,  CASE  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '관리중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '관리|고소'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '개발중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '개발|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  '폐업|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL)  THEN  '폐업'  END  UPSO_STAT_NM  ,  CASE  WHEN  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  'Y'  ELSE  'N'  END  GOSO_YN  ,  TB.ACCU_DAY  ,  TB.ACCU_NUM  ,  TB.ACCU_BRAN  ,  TB.ACCU_GBN  ,  TB.JUDG_CD  FROM  (   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.UPSO_STAT  ,  XA.MNGEMSTR_NM  ,  XA.PERMMSTR_NM  ,  XA.UPSO_PHON  ,  XA.MNGEMSTR_PHONNUM  ,  XA.STAFF_CD  ,  XD.HAN_NM  STAFF_NM  ,  XA.UPSO_ADDR1  ||  XA.UPSO_ADDR2  ADDR  ,  XB.MONPRNCFEE  ,  TRIM(XC.BSTYP_CD)  ||  TRIM(XC.GRAD_GBN)  GRAD  ,  XC.GRADNM  ,  XA.NEW_DAY  ,  XE.ACCU  ,  XA.UPSO_ZIP  ,  XA.CLSBS_YRMN  ,  XA.MNG_ZIP  FROM  GIBU.TBRA_UPSO  XA  ,  GIBU.TBRA_UPSORTAL_INFO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  INSA.TINS_MST01  XD  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(ACCU_DAY  ||  ACCU_NUM)  ACCU  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  #{branCd})  GROUP  BY  UPSO_CD  )  XE  ,  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  ,  UPSO_CD  FROM  (   \n";
        query +=" SELECT  XB.UPSO_CD  ,  XA.JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  FROM  GIBU.TBRA_UPSO  A  WHERE  A.UPSO_CD  =  NVL(#{upsoCd},  A.UPSO_CD)   \n";
        query +=" AND  A.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  A.UPSO_NM  LIKE  DECODE(  #{upsoNm}  ,  NULL,  A.UPSO_NM,  '%'  ||  #{upsoNm}  ||  '%')   \n";
        query +=" AND  (NVL(A.UPSO_PHON,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.UPSO_PHON,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_PHONNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.MNGEMSTR_PHONNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_HPNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.MNGEMSTR_HPNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_PHONNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.PERMMSTR_PHONNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_HPNUM,  '-')  LIKE  DECODE(#{upsoPhon},  NULL,  NVL(A.PERMMSTR_HPNUM,  '-'),  '%'  ||  #{upsoPhon}  ||  '%'))   \n";
        query +=" AND  (NVL(A.MNGEMSTR_NM,  '-')  LIKE  DECODE(  #{mngemstrNm}  ,  NULL,  NVL(A.MNGEMSTR_NM,  '-'),  '%'  ||  #{mngemstrNm}  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_NM,  '-')  LIKE  DECODE(  #{mngemstrNm}  ,  NULL,  NVL(A.PERMMSTR_NM,  '-'),  '%'  ||  #{mngemstrNm}  ||  '%'))  )  XB  WHERE  XA.UPSO_CD  =  XB.UPSO_CD  )  ZA  GROUP  BY  ZA.UPSO_CD  )  XF  WHERE  XA.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.JOIN_SEQ  =  XF.JOIN_SEQ   \n";
        query +=" AND  XC.BSTYP_CD  =  XB.BSTYP_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XB.UPSO_GRAD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD   \n";
        query +=" AND  XE.UPSO_CD  (+)  =  XA.UPSO_CD  )  TA  ,  GIBU.TBRA_ACCU  TB  WHERE  TB.UPSO_CD  (+)  =  TA.UPSO_CD   \n";
        query +=" AND  TB.ACCU_DAY(+)  =  SUBSTR(TA.ACCU,  1,  8)   \n";
        query +=" AND  TB.ACCU_NUM(+)  =  SUBSTR(TA.ACCU,  9,  4)   \n";
        query +=" AND  TA.MNG_ZIP  IN  (   \n";
        query +=" SELECT  MNG_ZIP  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  MNG_ZIP  =  NVL(#{mngZip},  MNG_ZIP)   \n";
        query +=" AND  ATTE  =  NVL(#{atte},  ATTE)   \n";
        query +=" AND  DSRCCNTY  =  NVL(#{dsrccnty},  DSRCCNTY)  )  ORDER  BY  TA.UPSO_NM ";
        String xml ="";
            xml += "<select id=\"SQLp_upso_select_old_SEL6\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsel_sigu_SEL1() {
        String    query="";
        query +=" SELECT  /*+  index_ffs(FIDU.TENV_POST)  */  atte  ,  citycntydsrc  FROM  FIDU.TENV_POST  group  by  citycntydsrc,  atte  order  by  atte,  citycntydsrc ";
        String xml ="";
            xml += "<select id=\"SQLsel_sigu_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "D:\\kosmos\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLp_upso_select_SEL5() + "\n";
        content += SQLp_upso_select_SEL6() + "\n";
        content += SQLp_upso_select_SEL7() + "\n";
        content += SQLp_upso_select_SEL8() + "\n";
        content += SQLp_demd_select_SEL1() + "\n";
        content += SQLp_upso_select_old_SEL2() + "\n";
        content += SQLp_upso_select_old_SEL3() + "\n";
        content += SQLp_upso_select_old_SEL4() + "\n";
        content += SQLp_upso_select_old_SEL26() + "\n";
        content += SQLp_upso_select_old_SEL6() + "\n";
        content += SQLsel_sigu_SEL1() + "\n";
        content += bottom;

        try {
            FileWriter fw = new FileWriter(dir + "com01_r07.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

