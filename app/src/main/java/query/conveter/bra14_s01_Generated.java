package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra14_s01_Generated {

    public static String SQLauto_card_app_save_SEL1() {
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  SEQ  FROM  GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION  WHERE  UPSO_CD  =  #{upsoCd} ";
        String xml ="";
            xml += "<select id=\"SQLauto_card_app_save_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLauto_card_app_save_UPD6() {
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION  \n";
        query +=" set MODPRES_ID=#{modpresId} , RELATION_CD=#{relationCd} , PAYPRES_NM=#{paypresNm} , CARD_CD=#{cardCd} , PAYPRES_PHON_NUM=#{paypresPhonNum} , VAILD_YRMM=#{vaildYrmm} , APPTN_DAY=#{apptnDay} , APP_GBN=#{appGbn} , MOD_DATE=SYSDATE , RESINUM=#{resinum} , CARD_NUM=#{cardNum} , PASSWD=#{passwd}  \n";
        query +=" where SEQ=#{seq}  \n";
        query +=" and UPSO_CD=#{upsoCd}  \n";
        query +=" and BRAN_CD=#{branCd}";
        String xml ="";
            xml += "<update id=\"SQLauto_card_app_save_UPD6\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLauto_card_app_save_INS5() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION (RELATION_CD, INSPRES_ID, PAYPRES_NM, SEQ, INS_DATE, CARD_CD, PAYPRES_PHON_NUM, VAILD_YRMM, APPTN_DAY, UPSO_CD, APP_GBN, RESINUM, CARD_NUM, BRAN_CD, PASSWD)  \n";
        query +=" values(#{relationCd} , #{inspresId} , #{paypresNm} , #{seq} , SYSDATE, #{cardCd} , #{paypresPhonNum} , #{vaildYrmm} , #{apptnDay} , #{upsoCd} , #{appGbn} , #{resinum} , #{cardNum} , #{branCd} , #{passwd} )";
        String xml ="";
            xml += "<insert id=\"SQLauto_card_app_save_INS5\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLauto_card_fileup_SEL_FILE() {
        String    query="";
        query +=" SELECT  '/upload_file/GIBU/APPLICATION_CARD/'  ||  SUBSTR(TO_CHAR(SYSDATE,'YYYYMM'),  1,  6)  AS  DFILEPATH  FROM  DUAL ";
        String xml ="";
            xml += "<select id=\"SQLauto_card_fileup_SEL_FILE\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLauto_card_fileup_SEL31() {
        String    query="";
        query +=" SELECT  SVR_FILE_NM  ,  SVR_FILE_ROUT  FROM  GIBU.TBRA_UPSO_AUTO_CARD_DOC_ATTCH  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  SEQ  =  #{seq} ";
        String xml ="";
            xml += "<select id=\"SQLauto_card_fileup_SEL31\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLauto_card_fileup_SEL25() {
        String    query="";
        query +=" SELECT  ''  ||  #{upsoCd}  ||  '-'  ||  TO_CHAR(SYSDATE,'yyyymmdd')  ||  '-'  ||  #{seq}  ||  '-'  ||  TO_CHAR  (SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR  (  #{fileNm},  INSTR  (  #{fileNm},  '.',  '-1'))  AS  DFILENAME  FROM  DUAL ";
        String xml ="";
            xml += "<select id=\"SQLauto_card_fileup_SEL25\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLauto_card_fileup_DEL40() {
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_AUTO_CARD_DOC_ATTCH  \n";
        query +=" where UPSO_CD=#{upsoCd}  \n";
        query +=" and SEQ=#{seq}";
        String xml ="";
            xml += "<delete id=\"SQLauto_card_fileup_DEL40\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLauto_card_fileup_SEL33() {
        String    query="";
        query +=" SELECT  ''  ||  #{upsoCd}  ||  '-'  ||  #{insDay}  ||  '-'  ||  #{seq}  ||  '-'  ||  TO_CHAR  (SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR  (  #{fileNm},  INSTR  (  #{fileNm},  '.',  '-1'))  AS  DFILENAME  FROM  DUAL ";
        String xml ="";
            xml += "<select id=\"SQLauto_card_fileup_SEL33\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLauto_card_fileup_INS31() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO_CARD_DOC_ATTCH (INS_DATE, INSPRES_ID, UPSO_CD, SVR_FILE_ROUT, SVR_FILE_NM, SEQ, REMAK, FILE_NM)  \n";
        query +=" values(SYSDATE, #{inspresId} , #{upsoCd} , #{svrFileRout} , #{svrFileNm} , #{seq} , #{remak} , #{fileNm} )";
        String xml ="";
            xml += "<insert id=\"SQLauto_card_fileup_INS31\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLauto_card_fileup_UPD36() {
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO_CARD_DOC_ATTCH  \n";
        query +=" set MODPRES_ID=#{modpresId} , MOD_DATE=SYSDATE , SVR_FILE_ROUT=#{svrFileRout} , SVR_FILE_NM=#{svrFileNm} , REMAK=#{remak} , FILE_NM=#{fileNm}  \n";
        query +=" where UPSO_CD=#{upsoCd}  \n";
        query +=" and SEQ=#{seq}";
        String xml ="";
            xml += "<update id=\"SQLauto_card_fileup_UPD36\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLauto_card_file_search_SEL1() {
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.SEQ  ,  A.FILE_NM  ,  A.SVR_FILE_NM  ,  A.SVR_FILE_ROUT  FROM  GIBU.TBRA_UPSO_AUTO_CARD_DOC_ATTCH  A  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.SEQ  =  #{seq}  ORDER  BY  SEQ ";
        String xml ="";
            xml += "<select id=\"SQLauto_card_file_search_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsearch_auto_card_SEL1() {
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  AS  BRAN_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD  ||  XB.UPSO_GRAD  AS  GRAD  ,  XB.GRADNM  ,  XA.STAFF_CD  ,  XC.HAN_NM  AS  STAFF_NM  ,  XB.MONPRNCFEE  ,  XA.UPSO_PHON  ,  XA.MNGEMSTR_NM  ,  XA.MNGEMSTR_RESINUM  ,  SUBSTR(REPLACE(XA.MNGEMSTR_HPNUM,  '-',  ''),  1,  11)  AS  MNGEMSTR_HPNUM  ,  XA.PERMMSTR_NM  ,  XA.PERMMSTR_RESINUM  ,  SUBSTR(REPLACE(XA.PERMMSTR_HPNUM,  '-',  ''),  1,  11)  AS  PERMMSTR_HPNUM  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,'',  '',',  '  ||  XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  AS  ADDR  ,  XA.CLIENT_NUM  ,  XA.CLSBS_YRMN  ||  '01'  AS  CLSBS_YRMN  ,  GIBU.FT_GET_AUTO_BANK_USE(XA.UPSO_CD)  CNT_AUTO_B  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  AS  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  AS  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  #{upsoCd}  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  INSA.TINS_MST01  XC  ,  INSA.TCPM_DEPT  XD  WHERE  XA.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.STAFF_NUM(+)  =  XA.STAFF_CD   \n";
        query +=" AND  XD.GIBU  =  XA.BRAN_CD ";
        String xml ="";
            xml += "<select id=\"SQLsearch_auto_card_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsearch_auto_card_SEL3() {
        String    query="";
        query +=" SELECT  BRAN_CD  ,UPSO_CD  ,SEQ  ,CARD_CD  ,CARD_NUM  ,RESINUM  ,PAYPRES_NM  ,PAYPRES_PHON_NUM  ,RELATION_CD  ,VAILD_YRMM  ,APPTN_DAY  ,PASSWD  ,APP_GBN  ,  NVL2(A.CONFIRM_DATE,'1','0')  CONFIRM_YN  ,FIDU.GET_STAFF_NM(CONFIRM_ID)  CONFIRM_NM  ,TO_CHAR(INS_DATE,'yyyymmdd')  INS_DATE  ,FIDU.GET_STAFF_NM(INSPRES_ID)  INSPRES_NM  ,MOD_DATE  ,MODPRES_ID  FROM  GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION  A  WHERE  1=1   \n";
        query +=" AND  BRAN_CD  =#{branCd}   \n";
        query +=" AND  UPSO_CD  =#{upsoCd}  ORDER  BY  1,2,3 ";
        String xml ="";
            xml += "<select id=\"SQLsearch_auto_card_SEL3\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "D:\\kosmos\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLauto_card_app_save_SEL1() + "\n";
        content += SQLauto_card_app_save_UPD6() + "\n";
        content += SQLauto_card_app_save_INS5() + "\n";
        content += SQLauto_card_fileup_SEL_FILE() + "\n";
        content += SQLauto_card_fileup_SEL31() + "\n";
        content += SQLauto_card_fileup_SEL25() + "\n";
        content += SQLauto_card_fileup_DEL40() + "\n";
        content += SQLauto_card_fileup_SEL33() + "\n";
        content += SQLauto_card_fileup_INS31() + "\n";
        content += SQLauto_card_fileup_UPD36() + "\n";
        content += SQLauto_card_file_search_SEL1() + "\n";
        content += SQLsearch_auto_card_SEL1() + "\n";
        content += SQLsearch_auto_card_SEL3() + "\n";
        content += bottom;

        try {
            String fileName = "bra01_s01_1.xml";
            FileWriter fw = new FileWriter(dir + "bra14_s01.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

