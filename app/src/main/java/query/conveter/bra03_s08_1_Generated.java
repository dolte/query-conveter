package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra03_s08_1_Generated {

    public static String SQLauto_demd_select_SEL5() {
        String    query="";
        query +=" SELECT  XC.BRAN_CD  ,  XB.UPSO_CD  ,  XC.UPSO_NM  ,  XC.CLIENT_NUM  ,  XB.BANK_CD  ,  XB.AUTO_ACCNNUM  ,  XB.RESINUM  ,  XA.START_YRMN  ,  XC.MCHNDAESU  ,  XC.MNGEMSTR_NM  ,  XA.BSTYP_CD  ||  XA.UPSO_GRAD  UPSO_GRAD  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DSCT_AMT  ,  XA.DEMD_MMCNT  ,  XA.MONPRNCFEE  ,  XE.BANK_NM  ,  XF.DEPT_NM  ,  XB.RECEPTION_GBN  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  SUBSTR(A.BANK_CD  ,  1,  3)  BANK_CD  ,  A.AUTO_ACCNNUM  ,  DECODE(LENGTH(A.RESINUM),  13,  SUBSTR(A.RESINUM,0,6)||'0000000',  A.RESINUM)  AS  RESINUM  ,  A.RECEPTION_GBN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(AUTO_NUM)  AUTO_NUM  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'  GROUP  BY  UPSO_CD  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.AUTO_NUM  =  B.AUTO_NUM   \n";
        query +=" AND  A.AUTO_ACCNNUM  IS  NOT  NULL  )  XB  ,  GIBU.TBRA_UPSO  XC  ,  ACCT.TCAC_BANK  XE  ,  INSA.TCPM_DEPT  XF  WHERE  XA.DEMD_YRMN  =  #{demdYrmn}   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XE.BANK_CD  =  XB.BANK_CD   \n";
        query +=" AND  XF.GIBU  =  XC.BRAN_CD   \n";
        query +=" AND  XA.DEMD_GBN  =  '31'   \n";
        query +=" AND  XA.TOT_DEMD_AMT  >  0   \n";
        query +=" AND  XA.UPSO_CD  NOT  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_REPT  WHERE  DEMD_YRMN  =  #{demdYrmn}  )   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NOT  NULL   \n";
        query +=" AND  XC.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  MONTHS_BETWEEN(XA.END_YRMN||'01',  XA.START_YRMN||'01')+1  >  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  BETWEEN  XA.START_YRMN   \n";
        query +=" AND  XA.END_YRMN  )  ORDER  BY  XC.BRAN_CD,  XB.UPSO_CD ";
        String xml ="";
            xml += "<select id=\"SQLauto_demd_select_SEL5\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLauto_demd_select_SEL13() {
        String    query="";
        query +=" SELECT  GIBU.FT_GET_BSTYP_NM(XA.BSTYP_CD)  AS  BSTYP_NM  ,  COUNT(1)  AS  DEMD_CNT  ,  SUM(XA.TOT_DEMD_AMT)  AS  TOT_DEMD_AMT  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(AUTO_NUM)  AS  AUTO_NUM  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'  GROUP  BY  UPSO_CD  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.AUTO_NUM  =  B.AUTO_NUM   \n";
        query +=" AND  A.AUTO_ACCNNUM  IS  NOT  NULL  )  XB  ,  GIBU.TBRA_UPSO  XC  WHERE  XA.DEMD_YRMN  =  #{demdYrmn}   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XA.DEMD_GBN  =  '31'   \n";
        query +=" AND  XA.TOT_DEMD_AMT  >  0   \n";
        query +=" AND  XA.UPSO_CD  NOT  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_REPT  WHERE  DEMD_YRMN  =  #{demdYrmn})   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NOT  NULL   \n";
        query +=" AND  XC.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  MONTHS_BETWEEN(XA.END_YRMN  ||  '01',  XA.START_YRMN  ||  '01')  +  1  >   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  BETWEEN  XA.START_YRMN   \n";
        query +=" AND  XA.END_YRMN)  GROUP  BY  XA.BSTYP_CD  ORDER  BY  1 ";
        String xml ="";
            xml += "<select id=\"SQLauto_demd_select_SEL13\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLauto_demd_select_SEL6() {
        String    query="";
        query +=" SELECT  MIN  (YRMNDAY)  PAY_DAY  FROM  INSA.TDUT_CALENDAR  WHERE  YRMNDAY  BETWEEN  TO_CHAR(ADD_MONTHS(TO_DATE(#{yrmnday},'YYYYMM'),1),'YYYYMM')  ||  '03'   \n";
        query +=" AND  TO_CHAR(ADD_MONTHS(TO_DATE(#{yrmnday},'YYYYMM'),1),'YYYYMM')  ||  '10'   \n";
        query +=" AND  HOLIDAY_YN  =  '0' ";
        String xml ="";
            xml += "<select id=\"SQLauto_demd_select_SEL6\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLauto_demd_select_XIUD10() {
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_AUTO  \n";
        query +=" SET TRNF_RSLT = #{trnfRslt} , MODPRES_ID = #{modpresId} , MOD_DATE = SYSDATE , PROC_GBN = #{procGbn} , SEND_DATE2 = SYSDATE  \n";
        query +=" WHERE DEMD_YRMN = #{demdYrmn}  \n";
        query +=" AND UPSO_CD IN (SELECT DISTINCT XB.UPSO_CD FROM GIBU.TBRA_DEMD_AUTO XA , ( SELECT A.UPSO_CD FROM GIBU.TBRA_UPSO_AUTO A , ( SELECT UPSO_CD , MAX(AUTO_NUM) AUTO_NUM FROM GIBU.TBRA_UPSO_AUTO  \n";
        query +=" WHERE TERM_YN = 'N' GROUP BY UPSO_CD ) B  \n";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  \n";
        query +=" AND A.AUTO_NUM = B.AUTO_NUM  \n";
        query +=" AND A.AUTO_ACCNNUM IS NOT NULL ) XB , GIBU.TBRA_UPSO XC  \n";
        query +=" WHERE XA.DEMD_YRMN = #{demdYrmn}  \n";
        query +=" AND XB.UPSO_CD = XA.UPSO_CD  \n";
        query +=" AND XC.UPSO_CD = XA.UPSO_CD  \n";
        query +=" AND XA.DEMD_GBN = '31'  \n";
        query +=" AND XA.TOT_DEMD_AMT > 0  \n";
        query +=" AND XA.UPSO_CD NOT IN ( SELECT UPSO_CD FROM GIBU.TBRA_DEMD_REPT  \n";
        query +=" WHERE DEMD_YRMN = #{demdYrmn} )  \n";
        query +=" AND XA.TRNF_RSLT IS NOT NULL  \n";
        query +=" AND XC.CLSBS_YRMN IS NULL  \n";
        query +=" AND MONTHS_BETWEEN(XA.END_YRMN||'01', XA.START_YRMN||'01')+1 > ( SELECT COUNT(*) FROM GIBU.TBRA_NOTE  \n";
        query +=" WHERE UPSO_CD = XB.UPSO_CD  \n";
        query +=" AND NOTE_YRMN BETWEEN XA.START_YRMN  \n";
        query +=" AND XA.END_YRMN ) )";
        String xml ="";
            xml += "<update id=\"SQLauto_demd_select_XIUD10\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLauto_demd_select_SEL1() {
        String    query="";
        query +=" SELECT  XC.BRAN_CD  ,  XB.UPSO_CD  ,  XC.UPSO_NM  ,  XC.CLIENT_NUM  ,  XB.BANK_CD  ,  XB.AUTO_ACCNNUM  ,  XB.RESINUM  ,  XA.START_YRMN  ,  XC.MCHNDAESU  ,  XC.MNGEMSTR_NM  ,  XA.BSTYP_CD  ||  XA.UPSO_GRAD  UPSO_GRAD  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DSCT_AMT  ,  XA.DEMD_MMCNT  ,  XA.MONPRNCFEE  ,  XE.BANK_NM  ,  XF.DEPT_NM  ,  XB.RECEPTION_GBN  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  SUBSTR(A.BANK_CD  ,  1,  3)  BANK_CD  ,  A.AUTO_ACCNNUM  ,  DECODE(LENGTH(A.RESINUM),  13,  SUBSTR(A.RESINUM,0,6)||'0000000',  A.RESINUM)  AS  RESINUM  ,  A.RECEPTION_GBN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(AUTO_NUM)  AUTO_NUM  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'  GROUP  BY  UPSO_CD  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.AUTO_NUM  =  B.AUTO_NUM   \n";
        query +=" AND  A.AUTO_ACCNNUM  IS  NOT  NULL  )  XB  ,  GIBU.TBRA_UPSO  XC  ,  ACCT.TCAC_BANK  XE  ,  INSA.TCPM_DEPT  XF  WHERE  XA.DEMD_YRMN  =  #{demdYrmn}   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XE.BANK_CD  =  XB.BANK_CD   \n";
        query +=" AND  XF.GIBU  =  XC.BRAN_CD   \n";
        query +=" AND  XA.DEMD_GBN  =  '31'   \n";
        query +=" AND  XA.TOT_DEMD_AMT  >  0  ORDER  BY  XC.BRAN_CD,  XB.UPSO_CD ";
        String xml ="";
            xml += "<select id=\"SQLauto_demd_select_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLauto_demd_select_SEL12() {
        String    query="";
        query +=" SELECT  GIBU.FT_GET_BSTYP_NM(XA.BSTYP_CD)  AS  BSTYP_NM  ,  COUNT(1)  AS  DEMD_CNT  ,  SUM(XA.TOT_DEMD_AMT)  AS  TOT_DEMD_AMT  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(AUTO_NUM)  AUTO_NUM  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'  GROUP  BY  UPSO_CD  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.AUTO_NUM  =  B.AUTO_NUM   \n";
        query +=" AND  A.AUTO_ACCNNUM  IS  NOT  NULL  )  XB  WHERE  XA.DEMD_YRMN  =  #{demdYrmn}   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XA.DEMD_GBN  =  '31'   \n";
        query +=" AND  XA.TOT_DEMD_AMT  >  0  GROUP  BY  XA.BSTYP_CD  ORDER  BY  1 ";
        String xml ="";
            xml += "<select id=\"SQLauto_demd_select_SEL12\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLauto_demd_select_SEL2() {
        String    query="";
        query +=" SELECT  MIN  (YRMNDAY)  PAY_DAY  FROM  INSA.TDUT_CALENDAR  WHERE  YRMNDAY  BETWEEN  #{yrmnday}  ||  '23'   \n";
        query +=" AND  SUBSTR(#{yrmnday},  1,  6)  ||  '31'   \n";
        query +=" AND  HOLIDAY_YN  =  '0' ";
        String xml ="";
            xml += "<select id=\"SQLauto_demd_select_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLauto_demd_select_SEL10() {
        String    query="";
        query +=" SELECT  MIN  (YRMNDAY)  PAY_DAY  FROM  INSA.TDUT_CALENDAR  WHERE  YRMNDAY  BETWEEN  TO_CHAR(ADD_MONTHS(TO_DATE(#{fromdate},'YYYYMMDD'),1),'YYYYMM')  ||  SUBSTR(#{fromdate},7,2)   \n";
        query +=" AND  TO_CHAR(ADD_MONTHS(TO_DATE(#{fromdate},'YYYYMMDD'),1),'YYYYMM')  ||  SUBSTR(#{todate},7,2)   \n";
        query +=" AND  HOLIDAY_YN  =  '0' ";
        String xml ="";
            xml += "<select id=\"SQLauto_demd_select_SEL10\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLauto_demd_select_XIUD14() {
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_AUTO  \n";
        query +=" SET PROC_GBN = #{procGbn} , SEND_DATE1 = SYSDATE  \n";
        query +=" WHERE DEMD_YRMN = #{demdYrmn}  \n";
        query +=" AND UPSO_CD IN (SELECT XB.UPSO_CD FROM GIBU.TBRA_DEMD_AUTO XA , ( SELECT A.UPSO_CD , SUBSTR(A.BANK_CD , 1, 3) BANK_CD , A.AUTO_ACCNNUM , A.RESINUM FROM GIBU.TBRA_UPSO_AUTO A , ( SELECT UPSO_CD , MAX(AUTO_NUM) AUTO_NUM FROM GIBU.TBRA_UPSO_AUTO  \n";
        query +=" WHERE TERM_YN = 'N' GROUP BY UPSO_CD ) B  \n";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  \n";
        query +=" AND A.AUTO_NUM = B.AUTO_NUM  \n";
        query +=" AND A.AUTO_ACCNNUM IS NOT NULL ) XB , GIBU.TBRA_UPSO XC , ACCT.TCAC_BANK XE , INSA.TCPM_DEPT XF  \n";
        query +=" WHERE XA.DEMD_YRMN = #{demdYrmn}  \n";
        query +=" AND XB.UPSO_CD = XA.UPSO_CD  \n";
        query +=" AND XC.UPSO_CD = XA.UPSO_CD  \n";
        query +=" AND XE.BANK_CD = XB.BANK_CD  \n";
        query +=" AND XF.GIBU = XC.BRAN_CD  \n";
        query +=" AND XA.DEMD_GBN = '31'  \n";
        query +=" AND XA.TOT_DEMD_AMT > 0 )";
        String xml ="";
            xml += "<update id=\"SQLauto_demd_select_XIUD14\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "/Users/pp01713/cursor-workspace/query-conveter/app/src/main/resources/";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLauto_demd_select_SEL5() + "\n";
        content += SQLauto_demd_select_SEL13() + "\n";
        content += SQLauto_demd_select_SEL6() + "\n";
        content += SQLauto_demd_select_XIUD10() + "\n";
        content += SQLauto_demd_select_SEL1() + "\n";
        content += SQLauto_demd_select_SEL12() + "\n";
        content += SQLauto_demd_select_SEL2() + "\n";
        content += SQLauto_demd_select_SEL10() + "\n";
        content += SQLauto_demd_select_XIUD14() + "\n";
        content += bottom;

        try {
            String fileName = "bra01_s01_1.xml";
            FileWriter fw = new FileWriter(dir + "bra03_s08_1.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

