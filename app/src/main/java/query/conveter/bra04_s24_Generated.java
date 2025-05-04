package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra04_s24_Generated {

    public static String SQLreq_virtual_info_SEL2() {
        String    query="";
        query +=" SELECT  SUM(CNT)  AS  CNT  FROM   \n";
        query +=" (SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_COMMAND_TBL  WHERE  COMMAND_WORK  =  'MEM'   \n";
        query +=" AND  COMMAND_LINE  =  'SEARCH'   \n";
        query +=" AND  COMMAND_STATUS  =  'B'   \n";
        query +=" AND  COMMAND_NO  =   \n";
        query +=" (SELECT  COMMAND_NO  FROM  GIBU.FMS_VIRACC_MEM_SH_TBL  WHERE  MEMBER_ID  =  #{upsoCd}   \n";
        query +=" AND  TRAN_STATUS  =  'B')   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_VIRACC_MEM_SH_TBL  WHERE  MEMBER_ID  =  #{upsoCd}   \n";
        query +=" AND  TRAN_STATUS  =  'B'   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDYYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')) ";
        String xml ="";
            xml += "<select id=\"SQLreq_virtual_info_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLchg_virtual_upso_SEL2() {
        String    query="";
        query +=" SELECT  SUM(CNT)  AS  CNT  FROM   \n";
        query +=" (SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_COMMAND_TBL  WHERE  COMMAND_WORK  =  'MEM'   \n";
        query +=" AND  COMMAND_LINE  =  'CHANGE'   \n";
        query +=" AND  COMMAND_STATUS  =  'B'   \n";
        query +=" AND  COMMAND_NO  =   \n";
        query +=" (SELECT  COMMAND_NO  FROM  GIBU.FMS_VIRACC_MEM_CH_TBL  WHERE  MEMBER_ID  =  #{upsoCd}   \n";
        query +=" AND  TRAN_STATUS  =  'B')   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_VIRACC_MEM_CH_TBL  WHERE  MEMBER_ID  =  #{upsoCd}   \n";
        query +=" AND  TRAN_STATUS  =  'B'   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')) ";
        String xml ="";
            xml += "<select id=\"SQLchg_virtual_upso_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLdel_virtual_upso_SEL2() {
        String    query="";
        query +=" SELECT  SUM(CNT)  AS  CNT  FROM   \n";
        query +=" (SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_COMMAND_TBL  WHERE  COMMAND_WORK  =  'MEM'   \n";
        query +=" AND  COMMAND_LINE  =  'CANCEL'   \n";
        query +=" AND  COMMAND_STATUS  =  'B'   \n";
        query +=" AND  COMMAND_NO  =   \n";
        query +=" (SELECT  COMMAND_NO  FROM  GIBU.FMS_VIRACC_MEM_DEL_TBL  WHERE  MEMBER_ID  =  #{upsoCd}   \n";
        query +=" AND  TRAN_STATUS  =  'B')   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_VIRACC_MEM_DEL_TBL  WHERE  MEMBER_ID  =  #{upsoCd}   \n";
        query +=" AND  TRAN_STATUS  =  'B'   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')) ";
        String xml ="";
            xml += "<select id=\"SQLdel_virtual_upso_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLins_virtual_upso_SEL2() {
        String    query="";
        query +=" SELECT  SUM(CNT)  AS  CNT  FROM   \n";
        query +=" (SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_COMMAND_TBL  WHERE  COMMAND_WORK  =  'MEM'   \n";
        query +=" AND  COMMAND_LINE  =  'APPLY'   \n";
        query +=" AND  COMMAND_STATUS  =  'B'   \n";
        query +=" AND  COMMAND_NO  =   \n";
        query +=" (SELECT  MAX(COMMAND_NO)  FROM  GIBU.FMS_VIRACC_MEM_IF_TBL  WHERE  MEMBER_ID  =  #{upsoCd}   \n";
        query +=" AND  TRAN_STATUS  =  'B')   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_VIRACC_MEM_IF_TBL  WHERE  MEMBER_ID  =  #{upsoCd}   \n";
        query +=" AND  TRAN_STATUS  =  'B'   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')) ";
        String xml ="";
            xml += "<select id=\"SQLins_virtual_upso_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLmng_virtual_bank_SEL5() {
        String    query="";
        query +=" SELECT  SUM(CNT)  AS  CNT  FROM   \n";
        query +=" (SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_COMMAND_TBL  WHERE  COMMAND_WORK  =  'MEM'   \n";
        query +=" AND  COMMAND_LINE  =  'BCHANGE'   \n";
        query +=" AND  COMMAND_STATUS  =  'B'   \n";
        query +=" AND  COMMAND_NO  IN   \n";
        query +=" (SELECT  COMMAND_NO  FROM  GIBU.FMS_VIRACC_MEM_BCH_TBL  WHERE  MEMBER_ID  =  #{upsoCd}   \n";
        query +=" AND  TRAN_STATUS  =  'B')   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_VIRACC_MEM_BCH_TBL  WHERE  MEMBER_ID  =  #{upsoCd}   \n";
        query +=" AND  TRAN_STATUS  =  'B'   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')) ";
        String xml ="";
            xml += "<select id=\"SQLmng_virtual_bank_SEL5\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLmng_virtual_send_gbn_SEL6() {
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  GBN  IS  NOT  NULL ";
        String xml ="";
            xml += "<select id=\"SQLmng_virtual_send_gbn_SEL6\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLmng_virtual_send_gbn_SEL10() {
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  BANK_CODE  =   \n";
        query +=" (SELECT  '0'  ||  BANK_CD  FROM  GIBU.TFMS_BANK  WHERE  BRAN_CD  IS  NULL   \n";
        query +=" AND  REMAK  =  'Y') ";
        String xml ="";
            xml += "<select id=\"SQLmng_virtual_send_gbn_SEL10\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLmng_virtual_send_gbn_XIUD8() {
        String    query="";
        query +=" UPDATE GIBU.TFMS_UPSO  \n";
        query +=" SET GBN = #{gbn}  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}  \n";
        query +=" AND USE_YN = 'Y'  \n";
        query +=" AND BANK_CODE = (SELECT MIN(BANK_CODE) FROM GIBU.TFMS_UPSO  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}  \n";
        query +=" AND USE_YN = 'Y')";
        String xml ="";
            xml += "<update id=\"SQLmng_virtual_send_gbn_XIUD8\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLsel_virtual_list_SEL1() {
        String    query="";
        query +=" SELECT UPSO_CD , UPSO_NM , OPBI_DAY , NEW_DAY , CLSBS_YRMN , VIRTUAL_YN , VIRTUAL_CNT , STAFF_CD , FIDU.GET_STAFF_NM(STAFF_CD) AS STAFF_NM  ";
        query +=" FROM (  ";
        query +=" SELECT B.UPSO_CD , UPSO_NM , OPBI_DAY , NEW_DAY , CLSBS_YRMN , (CASE WHEN COUNT(A.BANK_CODE) > 0 THEN 'Y' ELSE 'N' END) AS VIRTUAL_YN , COUNT(A.BANK_CODE) AS VIRTUAL_CNT , B.STAFF_CD  ";
        query +=" FROM GIBU.TFMS_UPSO A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE A.UPSO_CD(+) = B.UPSO_CD  ";
        query +=" AND A.USE_YN(+) = 'Y'  ";
        query +=" AND B.BRAN_CD = #{branCd}  ";
        
        query +=" GROUP BY B.UPSO_CD, UPSO_NM, OPBI_DAY, NEW_DAY, CLSBS_YRMN, B.STAFF_CD )  ";
        query +=" WHERE NOT (VIRTUAL_YN = 'N'  ";
        query +=" AND CLSBS_YRMN IS NOT NULL)  ";
        query +=" ORDER BY VIRTUAL_CNT DESC, CLSBS_YRMN DESC  ";
        String xml ="";
            xml += "<select id=\"SQLsel_virtual_list_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsel_virtual_temp_SEL1() {
        String    query="";
        query +=" SELECT  UPSO_CD  ,  BANK_CODE  AS  BANK_CD  ,   \n";
        query +=" (SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  BANK_CD  =  A.BANK_CODE)  AS  BANK_NM  ,  ACCOUNT_NM  AS  REPTPRES  ,  GIBU.FT_GET_ACCOUNT_FORMAT(VIRTUAL_ACCOUNT_NUM)  AS  ACCN_NUM  ,  PAY_CHECK_YN  ,  DEPOSIT  ,  ON_TIME_YN  ,  END_DAY  ,  USE_YN  ,  REMAK  ,  NVL(MOD_DATE,  INS_DATE)  AS  MOD_DATE  FROM  GIBU.TFMS_UPSO  A  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  USE_YN  =  'Y'  ORDER  BY  USE_YN  DESC,  BANK_CODE ";
        String xml ="";
            xml += "<select id=\"SQLsel_virtual_temp_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsel_virtual_temp_SEL2() {
        String    query="";
        query +=" SELECT  GBN  ,  UPSO_CD  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  GBN  IS  NOT  NULL ";
        String xml ="";
            xml += "<select id=\"SQLsel_virtual_temp_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsel_virtual_real_SEL1() {
        String    query="";
        query +=" SELECT  #{upsoCd}  AS  UPSO_CD  ,  BANK_CODE  AS  BANK_CD  ,   \n";
        query +=" (SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  BANK_CD  =  '0'  ||  A.BANK_CODE)  AS  BANK_NM  ,  ACCOUNT_NM  AS  REPTPRES  ,  GIBU.FT_GET_ACCOUNT_FORMAT(VIRTUAL_ACCOUNT_NUM)  AS  ACCN_NUM  ,  'S'  AS  GBN  ,  NVL(UPDATE_DT,  INSERT_DT)  AS  UPDATE_DT  FROM  GIBU.FMS_VIRACC_MEM_SH_RESULT_TBL  A  WHERE  MEMBER_ID  =  #{upsoCd}   \n";
        query +=" AND  COMMAND_NO  =   \n";
        query +=" (SELECT  MAX(COMMAND_NO)  FROM  GIBU.FMS_VIRACC_MEM_SH_RESULT_TBL  WHERE  MEMBER_ID  =  #{upsoCd})  ORDER  BY  BANK_CODE ";
        String xml ="";
            xml += "<select id=\"SQLsel_virtual_real_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsel_virtual_real_SEL2() {
        String    query="";
        query +=" SELECT  #{upsoCd}  AS  UPSO_CD  ,  BANK_CODE  AS  BANK_CD  ,   \n";
        query +=" (SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  BANK_CD  =  '0'  ||  A.BANK_CODE)  AS  BANK_NM  ,  ACCOUNT_NM  AS  REPTPRES  ,  GIBU.FT_GET_ACCOUNT_FORMAT(VIRTUAL_ACCOUNT_NUM)  AS  ACCN_NUM  ,  'S'  AS  GBN  ,  NVL(UPDATE_DT,  INSERT_DT)  AS  UPDATE_DT  FROM  GIBU.FMS_VIRACC_MEM_SH_RESULT_TBL  A  WHERE  MEMBER_ID  =  #{upsoCd}   \n";
        query +=" AND  PROC_DT  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  COMMAND_NO  =   \n";
        query +=" (SELECT  MAX(COMMAND_NO)  FROM  GIBU.FMS_VIRACC_MEM_SH_RESULT_TBL  WHERE  MEMBER_ID  =  #{upsoCd}   \n";
        query +=" AND  PROC_DT  =  TO_CHAR(SYSDATE,  'YYYYMMDD'))  ORDER  BY  BANK_CODE ";
        String xml ="";
            xml += "<select id=\"SQLsel_virtual_real_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLreq_virtual_rept_SEL2() {
        String    query="";
        query +=" SELECT  SUM(CNT)  AS  CNT  FROM   \n";
        query +=" (SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_COMMAND_TBL  WHERE  COMMAND_WORK  =  'DEP'   \n";
        query +=" AND  COMMAND_LINE  =  'RESULT'   \n";
        query +=" AND  COMMAND_STATUS  =  'B'  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_VIRACC_DEPOSIT_IF_TBL  WHERE  RECV_TYPE  =  'A'   \n";
        query +=" AND  TRAN_STATUS  =  'B') ";
        String xml ="";
            xml += "<select id=\"SQLreq_virtual_rept_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsel_virtual_rept_SEL1() {
        String    query="";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  BANK_CD  ,   \n";
        query +=" (SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  BANK_CD  =  A.BANK_CD)  AS  BANK_NM  ,  GIBU.FT_GET_ACCOUNT_FORMAT(ACCN_NUM)  AS  ACCN_NUM  ,  REPTPRES  ,  REPT_AMT  ,  MAPPING_DAY  ,  RECV_DAY  ,  NOTE_YRMN  ,  GBN  ,  GBN2  FROM  (   \n";
        query +=" SELECT  0  AS  R_NUM  ,  ''  AS  REPT_DAY  ,  ''  AS  REPT_NUM  ,  '0'  ||  BANK_CODE  AS  BANK_CD  ,  VIRTUAL_ACCOUNT_NUM  AS  ACCN_NUM  ,  DEPOSIT_NAME  AS  REPTPRES  ,  TO_NUMBER(DEPOSIT)  AS  REPT_AMT  ,  ''  AS  MAPPING_DAY  ,  DEPOSIT_DAY  AS  RECV_DAY  ,  '  ~  '  AS  NOTE_YRMN  ,  DECODE(TRANSACTION_KIND,  'D',  '입금',  '취소')  AS  GBN  ,  '미매핑'  AS  GBN2  FROM  GIBU.FMS_VIRACC_DEPOSIT_RESULT_TBL  WHERE  MEMBER_ID  =  #{upsoCd}   \n";
        query +=" AND  DEPOSIT_DAY  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  PROC_DT  =  TO_CHAR(SYSDATE,  'YYYYMMDD')  UNION  ALL   \n";
        query +=" SELECT  ROWNUM  AS  R_NUM  ,  REPT_DAY  ,  REPT_NUM  ,  BANK_CD  ,  ACCN_NUM  ,  REPTPRES  ,  REPT_AMT  ,  MAPPING_DAY  ,  RECV_DAY  ,  NOTE_YRMN  ,  '입금'  AS  GBN  ,  DECODE(GBN,  '-',  '미매핑',  '매핑')  AS  GBN2  FROM  (   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.REPTPRES  ,  A.REPT_AMT  ,  A.MAPPING_DAY  ,  A.RECV_DAY  ,  MIN(B.NOTE_YRMN)  ||  '  ~  '  ||  MAX(B.NOTE_YRMN)  AS  NOTE_YRMN  ,  NVL(NVL(B.UPSO_CD,  C.UPSO_CD),  '-')  AS  GBN  FROM  GIBU.TBRA_REPT_VIRTUAL  A  ,  GIBU.TBRA_NOTE  B  ,  GIBU.TBRA_REPT_BALANCE  C  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD(+)   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM(+)   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD(+)   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  A.RECV_DAY  >=  TO_CHAR(ADD_MONTHS(SYSDATE,  -6),  'YYYYMM')  ||  '01'  GROUP  BY  A.REPT_DAY,  A.REPT_NUM,  A.BANK_CD,  A.ACCN_NUM,  A.REPT_AMT,  A.MAPPING_DAY,  A.RECV_DAY,  A.REPTPRES  ,  B.UPSO_CD,  C.UPSO_CD  ORDER  BY  RECV_DAY  DESC  )  )  A  ORDER  BY  R_NUM ";
        String xml ="";
            xml += "<select id=\"SQLsel_virtual_rept_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "C:\\komca\\workspace\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLreq_virtual_info_SEL2() + "\n";
        content += SQLchg_virtual_upso_SEL2() + "\n";
        content += SQLdel_virtual_upso_SEL2() + "\n";
        content += SQLins_virtual_upso_SEL2() + "\n";
        content += SQLmng_virtual_bank_SEL5() + "\n";
        content += SQLmng_virtual_send_gbn_SEL6() + "\n";
        content += SQLmng_virtual_send_gbn_SEL10() + "\n";
        content += SQLmng_virtual_send_gbn_XIUD8() + "\n";
        content += SQLsel_virtual_list_SEL1() + "\n";
        content += SQLsel_virtual_temp_SEL1() + "\n";
        content += SQLsel_virtual_temp_SEL2() + "\n";
        content += SQLsel_virtual_real_SEL1() + "\n";
        content += SQLsel_virtual_real_SEL2() + "\n";
        content += SQLreq_virtual_rept_SEL2() + "\n";
        content += SQLsel_virtual_rept_SEL1() + "\n";
        content += bottom;

        try {
            String fileName = "bra01_s01_1.xml";
            FileWriter fw = new FileWriter(dir + "bra04_s24.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

