package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra14_s03_Generated {

    public static String SQLsel_auto_card_demd_SEL1() {
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.FMS_CREDIT_REAL_CASE_IF_TBL  WHERE  1=1   \n";
        query +=" AND  SUBSTR(PROC_DT,1,6)=#{startDay} ";
        String xml ="";
            xml += "<select id=\"SQLsel_auto_card_demd_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsel_auto_card_demd_SEL2() {
        String    query="";
        query +=" SELECT A.BRAN_CD, GIBU.GET_BRAN_NM (A.BRAN_CD) AS BRAN_NM, GIBU.FT_GET_BSTYPGRAD_NM (A.UPSO_CD, 'NM') AS BSTYP_NM, A.UPSO_CD, A.UPSO_NM, B.DEMD_YRMN, B.DEMD_MMCNT, B.TOT_USE_AMT, B.MONPRNCFEE, (CASE WHEN (SELECT COUNT (1)  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND COMPN_DAY IS NULL) > 0 THEN 'Y' ELSE 'N' END) AS ACCU_YN, A.CLSBS_YRMN, A.MNGEMSTR_NM, DECODE (C.APP_GBN, 0, '등록', '해지') APP_GBN, C.PAYPRES_NM, A.STAFF_CD, FIDU.GET_STAFF_NM(A.STAFF_CD) STAFF_NM, ''RESULT_MSG, D.REPT_DAY, D.REPT_AMT, D.COMIS  ";
        query +=" FROM GIBU.TBRA_UPSO A, GIBU.TBRA_DEMD_OCR B, GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION C, GIBU.TBRA_REPT D  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.UPSO_CD = C.UPSO_CD  ";
        query +=" AND A.UPSO_CD = D.UPSO_CD(+)  ";
        query +=" AND SUBSTR(D.REPT_DAY(+),1,6) = #{startDay}  ";
        query +=" AND D.REPT_GBN(+) = '10'  ";
        query +=" AND C.CONFIRM_DATE IS NOT NULL  ";
        query +=" AND C.APP_GBN=0  ";
        query +=" AND B.DEMD_MMCNT >0  ";
        query +=" AND A.BRAN_CD = DECODE ( #{branCd}, 'AL', A.BRAN_CD, #{branCd})  ";
        query +=" AND C.SEQ = (  ";
        query +=" SELECT MAX (SEQ)  ";
        query +=" FROM GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION XX  ";
        query +=" WHERE XX.UPSO_CD = C.UPSO_CD  ";
        query +=" GROUP BY XX.UPSO_CD)  ";
        query +=" AND B.DEMD_YRMN = #{startDay}  ";
            query +=" STAFF_CD =#{staffCd}  ";
        query +=" ORDER BY BRAN_CD, UPSO_CD  ";
        String xml ="";
            xml += "<select id=\"SQLsel_auto_card_demd_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsel_auto_card_demd_SEL3() {
        String    query="";
        query +=" SELECT A.BRAN_CD, GIBU.GET_BRAN_NM (A.BRAN_CD) AS BRAN_NM, GIBU.FT_GET_BSTYPGRAD_NM (A.UPSO_CD, 'NM') AS BSTYP_NM, A.UPSO_CD, A.UPSO_NM, B.DEMD_YRMN, B.DEMD_MMCNT, B.TOT_USE_AMT, B.MONPRNCFEE, (CASE WHEN (SELECT COUNT (1)  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND COMPN_DAY IS NULL) > 0 THEN 'Y' ELSE 'N' END) AS ACCU_YN, A.CLSBS_YRMN, A.MNGEMSTR_NM, DECODE (C.APP_GBN, 0, '등록', '해지') APP_GBN, C.PAYPRES_NM, A.STAFF_CD, FIDU.GET_STAFF_NM(A.STAFF_CD) STAFF_NM, (  ";
        query +=" SELECT RESULT_MSG  ";
        query +=" FROM (SELECT PROC_DT, PROC_SEQ, RESULT_MSG  ";
        query +=" FROM GIBU.FMS_CREDIT_REAL_CASE_IF_TBL  ";
        query +=" WHERE 1=1  ";
        query +=" AND MEMBER_ID = A.UPSO_CD  ";
        query +=" AND SUBSTR(PROC_DT,1,6) = B.DEMD_YRMN  ";
        query +=" ORDER BY 1 desc, 2 desc)  ";
        query +=" WHERE rownum=1 ) RESULT_MSG, D.REPT_DAY, D.REPT_AMT, D.COMIS  ";
        query +=" FROM GIBU.TBRA_UPSO A, GIBU.TBRA_DEMD_OCR B, GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION C, GIBU.TBRA_REPT D  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.UPSO_CD = C.UPSO_CD  ";
        query +=" AND A.UPSO_CD = D.UPSO_CD(+)  ";
        query +=" AND SUBSTR(D.REPT_DAY(+),1,6) = #{startDay}  ";
        query +=" AND D.REPT_GBN(+) = '10'  ";
        query +=" AND C.CONFIRM_DATE IS NOT NULL  ";
        query +=" AND A.BRAN_CD = DECODE ( #{branCd}, 'AL', A.BRAN_CD, #{branCd})  ";
        query +=" AND A.UPSO_CD IN (SELECT MEMBER_ID  ";
        query +=" FROM GIBU.FMS_CREDIT_REAL_CASE_IF_TBL X  ";
        query +=" WHERE SUBSTR(X.PROC_DT,1,6)=#{startDay})  ";
        query +=" AND B.DEMD_YRMN = #{startDay}  ";
            query +=" STAFF_CD =#{staffCd}  ";
        query +=" ORDER BY BRAN_CD, UPSO_CD  ";
        String xml ="";
            xml += "<select id=\"SQLsel_auto_card_demd_SEL3\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "D:\\kosmos\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLsel_auto_card_demd_SEL1() + "\n";
        content += SQLsel_auto_card_demd_SEL2() + "\n";
        content += SQLsel_auto_card_demd_SEL3() + "\n";
        content += bottom;

        try {
            String fileName = "bra01_s01_1.xml";
            FileWriter fw = new FileWriter(dir + "bra14_s03.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

