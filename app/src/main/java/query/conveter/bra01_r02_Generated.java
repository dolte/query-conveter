package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra01_r02_Generated {

    public static String SQLsatn_num_del_XIUD1() {
        String    query="";
        query +=" UPDATE GIBU.TBRA_CONFIRM_DOC  \n";
        query +=" SET SATN_NUM = ''  \n";
        query +=" WHERE SATN_NUM = #{lnkKey}";
        String xml ="";
            xml += "<update id=\"SQLsatn_num_del_XIUD1\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLsatn_num_del_XIUD2() {
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_CLSED  \n";
        query +=" SET SATN_NUM = ''  \n";
        query +=" WHERE SATN_NUM = #{lnkKey}";
        String xml ="";
            xml += "<update id=\"SQLsatn_num_del_XIUD2\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLsatn_num_del_SEL6() {
        String    query="";
        query +=" SELECT  #{lnkKey}  AS  LNK_KEY  FROM  DUAL ";
        String xml ="";
            xml += "<select id=\"SQLsatn_num_del_SEL6\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLapprv_stat_SEL2() {
        String    query="";
        query +=" SELECT  LNK_KEY  ,  DOC_STS  AS  STATUS_CD  ,  #{upsoCd}  AS  UPSO_CD  ,  #{seq}  AS  SEQ  ,  #{clsedGbn}  AS  CLSED_GBN  ,  #{gbn}  AS  GBN  FROM  TRGW.TEAG_APPDOC  WHERE  LNK_KEY  =  #{satnNum}   \n";
        query +=" AND  DOC_STS  IN  ('20',  '90') ";
        String xml ="";
            xml += "<select id=\"SQLapprv_stat_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsel_satn_list_SEL1() {
        String    query="";
        query +=" SELECT  '0'  AS  CHK  ,  A.UPSO_CD  ,  A.SEQ  ,  B.UPSO_NM  ,  TO_CHAR(A.INS_DATE,  'YYYY-MM-DD')  AS  INS_DATE  ,  A.GBN  AS  CLSED_GBN  ,  DECODE(A.GBN,  '2',  TO_CHAR(TO_DATE(A.START_YRMN,  'YYYYMM'),  'YYYY/MM'),  '')  AS  CLSBS_YRMN  ,  ''  AS  CLSED_DAY  ,  DECODE(A.GBN,  '1',  NVL(TO_CHAR(TO_DATE(A.START_DAY,  'YYYYMMDD'),  'YYYY/MM/DD'),  TO_CHAR(TO_DATE(A.START_YRMN,  'YYYYMM'),  'YYYY/MM')),  '')  AS  START_DAY  ,  DECODE(A.GBN,  '1',  NVL(TO_CHAR(TO_DATE(A.END_DAY,  'YYYYMMDD'),  'YYYY/MM/DD'),  TO_CHAR(TO_DATE(A.END_YRMN,  'YYYYMM'),  'YYYY/MM')),  '')  AS  END_DAY  ,  A.SATN_NUM  ,  ''  AS  SATN_STAT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_CONFIRM_DOC_ATTCH  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  SEQ  =  A.SEQ   \n";
        query +=" AND  LENGTH(FILES)  >  0)  AS  ATTCH_CHK  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_CONFIRM_DOC_ATTCH  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  SEQ  =  A.SEQ)  AS  ATTCH_CNT  ,  B.STAFF_CD  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  AS  STAFF_NM  ,  'D'  AS  GBN  FROM  GIBU.TBRA_CONFIRM_DOC  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(#{branCd},  'AL',  B.BRAN_CD,  #{branCd})   \n";
        query +=" AND  B.STAFF_CD  =  NVL(#{staffNum},  B.STAFF_CD)   \n";
        query +=" AND  A.GBN  =  NVL(#{clsedGbn},  A.GBN)   \n";
        query +=" AND  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  BETWEEN  #{startDay}   \n";
        query +=" AND  #{endDay}  UNION  ALL   \n";
        query +=" SELECT  '0'  AS  CHK  ,  TA.UPSO_CD  ,  1  AS  SEQ  ,  TA.UPSO_NM  ,  TO_CHAR(TA.INS_DATE,  'YYYY-MM-DD')  AS  INS_DATE  ,  '3'  AS  CLSED_GBN  ,  TO_CHAR(TO_DATE  (TA.START_YRMN,  'YYYYMM'),  'YYYY/MM')  AS  CLSBS_YRMN  ,  TA.START_YRMN  AS  CLSED_DAY  ,  ''  AS  START_YRMN  ,  ''  AS  END_YRMN  ,  TA.SATN_NUM  ,  ''  AS  SATN_STAT  ,  TO_NUMBER('')  AS  ATTCH_CHK  ,  TO_NUMBER('')  AS  ATTCH_CNT  ,  TA.STAFF_CD  ,  FIDU.GET_STAFF_NM(TA.STAFF_CD)  AS  STAFF_NM  ,  'C'  AS  GBN  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.UPSO_NM  ,  A.CLSBS_YRMN  ,  A.STAFF_CD  ,  B.START_YRMN  ,  B.INS_DATE  ,  B.SATN_NUM  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_UPSO_CLSED  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.CLSED_GBN  IN  ('02',  '03',  '04')   \n";
        query +=" AND  TO_CHAR(B.INS_DATE,  'YYYYMMDD')  BETWEEN  #{startDay}   \n";
        query +=" AND  #{endDay}   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(#{branCd},  'AL',  A.BRAN_CD,  #{branCd})   \n";
        query +=" AND  A.STAFF_CD  =  NVL(#{staffNum},  A.STAFF_CD)   \n";
        query +=" AND  (#{clsedGbn}  =  '3'   \n";
        query +=" OR  #{clsedGbn}  IS  NULL)  )  TA  ,  GIBU.TBRA_UPSO  XC  WHERE  XC.BEFORE_UPSO_CD  =  TA.UPSO_CD  ORDER  BY  STAFF_CD,  UPSO_CD,  SEQ ";
        String xml ="";
            xml += "<select id=\"SQLsel_satn_list_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLapprv_all_satn_SEL1() {
        String    query="";
        query +=" SELECT  MAX(SATN_NUM)  AS  SATN_NUM  FROM  (   \n";
        query +=" SELECT  TO_CHAR(SYSDATE,  'YYYYMMDD')  ||  '-'  ||  #{staffCd}  ||  '-'  ||  LPAD(NVL(MAX(SUBSTR(SATN_NUM,  18,  3)),  0)  +  1,  3,  '0')  AS  SATN_NUM  FROM  GIBU.TBRA_CONFIRM_DOC  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.SATN_NUM  LIKE  TO_CHAR(SYSDATE,  'YYYYMMDD')  ||  '-'  ||  #{staffCd}  ||  '%'  UNION  ALL   \n";
        query +=" SELECT  TO_CHAR(SYSDATE,  'YYYYMMDD')  ||  '-'  ||  #{staffCd}  ||  '-'  ||  LPAD(NVL(MAX(SUBSTR(SATN_NUM,  18,  3)),  0)  +  1,  3,  '0')  AS  SATN_NUM  FROM  GIBU.TBRA_UPSO_CLSED  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.SATN_NUM  LIKE  TO_CHAR(SYSDATE,  'YYYYMMDD')  ||  '-'  ||  #{staffCd}  ||  '%'  ) ";
        String xml ="";
            xml += "<select id=\"SQLapprv_all_satn_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLapprv_all_satn_XIUD1() {
        String    query="";
        query +=" UPDATE GIBU.TBRA_CONFIRM_DOC  \n";
        query +=" SET SATN_NUM = #{satnNum}  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}  \n";
        query +=" AND SEQ = #{seq}";
        String xml ="";
            xml += "<update id=\"SQLapprv_all_satn_XIUD1\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLapprv_all_satn_XIUD6() {
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_CLSED  \n";
        query +=" SET SATN_NUM = #{satnNum}  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}  \n";
        query +=" AND START_YRMN = #{clsedDay}";
        String xml ="";
            xml += "<update id=\"SQLapprv_all_satn_XIUD6\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "C:\\komca\\workspace\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLsatn_num_del_XIUD1() + "\n";
        content += SQLsatn_num_del_XIUD2() + "\n";
        content += SQLsatn_num_del_SEL6() + "\n";
        content += SQLapprv_stat_SEL2() + "\n";
        content += SQLsel_satn_list_SEL1() + "\n";
        content += SQLapprv_all_satn_SEL1() + "\n";
        content += SQLapprv_all_satn_XIUD1() + "\n";
        content += SQLapprv_all_satn_XIUD6() + "\n";
        content += bottom;

        try {
            String fileName = "bra01_s01_1.xml";
            FileWriter fw = new FileWriter(dir + "bra01_r02.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

