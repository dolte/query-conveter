package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra01_s30_Generated {

    public static String SQLrouter_search_SEL1() {
        String    query="";
        query +=" SELECT A.SERIAL_NO, A.SERIAL_NO_SEQ, A.BRAN_CD, GIBU.GET_BRAN_NM(A.BRAN_CD) BRAN_NM, A.UPSO_CD, B.UPSO_NM, A.BSTYP_CD, GIBU.FT_GET_BSTYP_NM(A.BSTYP_CD) BSTYP_NM, NVL(B.UPSO_NEW_ADDR1,B.UPSO_ADDR1) UPSO_ADDR, A.BSCON_CD, FIDU.GET_BSCON_NM(A.BSCON_CD) BSCON_NM, A.ROOM_NUM, A.ACMCN_CD, A.ROUTER_CD, B.STAFF_CD, FIDU.GET_STAFF_NM(B.STAFF_CD) STAFF_NM, B.CLSBS_YRMN CLSBS_YRMN, (  ";
        query +=" SELECT MAX(OCC_DAY)  ";
        query +=" FROM GIBU.TBRA_OFF_LOGDATA X  ";
        query +=" WHERE 1=1  ";
        query +=" AND X.ACMCN_CD = A.ACMCN_CD) AS LAST_REC_DAY, NVL((SELECT DISTINCT 1  ";
        query +=" FROM GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  ";
        query +=" WHERE SERIAL_NO = A.SERIAL_NO  ";
        query +=" AND SERIAL_NO_SEQ = DECODE(C.SERIAL_NO,'',A.SERIAL_NO_SEQ, TO_NUMBER(A.SERIAL_NO_SEQ)+1)) ,'0') ATTACH_YN, A.DSCT_YN, A.DSCT_START, A.DSCT_END, A.CHG_GBN, CASE WHEN C.SERIAL_NO IS NOT NULL  ";
        query +=" AND A.PROC_STS ='M' THEN 'N' ELSE A.PROC_STS END PROC_STS, A.REMAK_CHG, A.REMAK_CHG_D, CASE WHEN C.SERIAL_NO IS NOT NULL  ";
        query +=" AND A.PROC_STS ='M' THEN TO_CHAR(C.BRAN_CONFIRM_DATE,'yyyymmdd') ELSE TO_CHAR(A.BRAN_CONFIRM_DATE,'yyyymmdd') END BRAN_CONFIRM_DATE, CASE WHEN C.SERIAL_NO IS NOT NULL  ";
        query +=" AND A.PROC_STS ='M' THEN C.BRAN_CONFIRM_ID ELSE A.BRAN_CONFIRM_ID END BRAN_CONFIRM_ID, CASE WHEN C.SERIAL_NO IS NOT NULL  ";
        query +=" AND A.PROC_STS ='M' THEN TO_CHAR(C.MNG_CONFIRM_DATE,'yyyymmdd') ELSE TO_CHAR(A.MNG_CONFIRM_DATE,'yyyymmdd') END MNG_CONFIRM_DATE, CASE WHEN C.SERIAL_NO IS NOT NULL  ";
        query +=" AND A.PROC_STS ='M' THEN C.MNG_CONFIRM_ID ELSE A.MNG_CONFIRM_ID END MNG_CONFIRM_ID, CASE WHEN CLSBS_YRMN IS NOT NULL THEN '1' WHEN NVL((SELECT MAX(OCC_DAY)  ";
        query +=" FROM GIBU.TBRA_OFF_LOGDATA X  ";
        query +=" WHERE 1=1  ";
        query +=" AND X.ACMCN_CD = A.ACMCN_CD),'29991231') <= TO_CHAR(SYSDATE-7,'yyyymmdd') THEN '2' WHEN TO_CHAR(ADD_MONTHS(NVL(A.MNG_CONFIRM_DATE,'29991231'),47),'yyyymm') <=TO_CHAR(SYSDATE,'YYYYMM') THEN '3' ELSE '0' END REP_OBJ  ";
        query +=" FROM GIBU.TBRA_OFF_ROUTER_MNG A, GIBU.TBRA_UPSO B, (SELECT SERIAL_NO, PROC_STS, BRAN_CONFIRM_DATE, BRAN_CONFIRM_ID, MNG_CONFIRM_DATE, MNG_CONFIRM_ID  ";
        query +=" FROM GIBU.TBRA_OFF_ROUTER_MNG  ";
        query +=" WHERE 1=1  ";
        query +=" AND PROC_STS='N' ) C  ";
        query +=" WHERE 1=1  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.SERIAL_NO = C.SERIAL_NO(+)  ";
        query +=" AND A.BRAN_CD = DECODE(#{branCd}, 'AL', A.BRAN_CD, #{branCd})  ";
        query +=" AND A.PROC_STS <>'N'  ";
        query +=" ORDER BY BRAN_CD, UPSO_CD  ";
        String xml ="";
            xml += "<select id=\"SQLrouter_search_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLrouter_upso_info_SEL1() {
        String    query="";
        query +=" SELECT  UPSO_CD,  UPSO_NM,  BRAN_CD,  STAFF_CD,  A.UPSO_NEW_ADDR1  UPSO_ADDR,  FIDU.GET_STAFF_NM(STAFF_CD)  STAFF_NM,  GIBU.GET_BRAN_NM(BRAN_CD)  BRAN_NM,  GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD)  BSTYP_CD,  GIBU.FT_GET_BSTYP_NM(GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD))  BSTYP_NM  FROM  GIBU.TBRA_UPSO  A  WHERE  1=1   \n";
        query +=" AND  UPSO_CD  =#{upsoCd} ";
        String xml ="";
            xml += "<select id=\"SQLrouter_upso_info_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLrouter_upso_info_SEL2() {
        String    query="";
        query +=" SELECT  SUM(CNT)  CNT  FROM  (   \n";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_OFF_ROUTER_MNG  WHERE  1=1   \n";
        query +=" AND  UPSO_CD=#{upsoCd}   \n";
        query +=" AND  PROC_STS<>'N'  UNION  ALL   \n";
        query +=" SELECT  COUNT(*)  CNT  FROM  LOG.KDS_SHOPROOM  WHERE  1=1   \n";
        query +=" AND  UPSO_CD  =#{upsoCd}  ) ";
        String xml ="";
            xml += "<select id=\"SQLrouter_upso_info_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLrouter_upso_info_SEL3() {
        String    query="";
        query +=" SELECT  COUNT(MCHN_COMPY)  ACM_CNT  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A  ,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  1=1   \n";
        query +=" AND  A.MODEL_CD=  B.MODEL_CD   \n";
        query +=" AND  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  B.MCHN_COMPY  =  #{bsconCd}   \n";
        query +=" AND  A.ONOFF_GBN='F' ";
        String xml ="";
            xml += "<select id=\"SQLrouter_upso_info_SEL3\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLrouter_file_search_SEL1() {
        String    query="";
        query +=" SELECT  UPSO_CD,SERIAL_NO,SERIAL_NO_SEQ,SVR_FILE_ROUT,  SVR_FILE_NM,  FILE_NM  FROM  GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  A  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD=#{upsoCd}   \n";
        query +=" AND  A.SERIAL_NO=#{serialNo}   \n";
        query +=" AND  A.SERIAL_NO_SEQ=#{serialNoSeq}  ORDER  BY  UPSO_CD ";
        String xml ="";
            xml += "<select id=\"SQLrouter_file_search_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLrouter_n_del_SEL4() {
        String    query="";
        query +=" SELECT  SERIAL_NO,  SERIAL_NO_SEQ,  UPSO_CD  FROM  GIBU.TBRA_OFF_ROUTER_MNG  WHERE  SERIAL_NO=#{serialNo}   \n";
        query +=" AND  PROC_STS='N' ";
        String xml ="";
            xml += "<select id=\"SQLrouter_n_del_SEL4\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLrouter_n_del_DEL2() {
        String    query="";
        query +=" Delete from GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  \n";
        query +=" where SERIAL_NO=#{serialNo}  \n";
        query +=" and SERIAL_NO_SEQ=#{serialNoSeq}";
        String xml ="";
            xml += "<delete id=\"SQLrouter_n_del_DEL2\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLrouter_n_del_XIUD1() {
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_OFF_ROUTER_MNG A  \n";
        query +=" WHERE 1=1  \n";
        query +=" AND A.SERIAL_NO=#{serialNo}  \n";
        query +=" AND A.SERIAL_NO_SEQ=#{serialNoSeq}  \n";
        query +=" AND A.PROC_STS ='N'";
        String xml ="";
            xml += "<delete id=\"SQLrouter_n_del_XIUD1\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLrouter_n_del_XIUD5() {
        String    query="";
        query +=" UPDATE GIBU.TBRA_OFF_ROUTER_MNG A  \n";
        query +=" SET REMAK_CHG='', REMAK_CHG_D='' , CHG_GBN=''  \n";
        query +=" WHERE 1=1  \n";
        query +=" AND A.SERIAL_NO=#{serialNo}  \n";
        query +=" AND A.SERIAL_NO=#{serialNoSeq}  \n";
        query +=" AND A.PROC_STS ='M'";
        String xml ="";
            xml += "<update id=\"SQLrouter_n_del_XIUD5\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLrouter_n_search_SEL1() {
        String    query="";
        query +=" SELECT  A.BRAN_CD,  GIBU.GET_BRAN_NM(A.BRAN_CD)  BRAN_NM,  A.UPSO_CD,  B.UPSO_NM,  A.BSTYP_CD,  GIBU.FT_GET_BSTYP_NM(A.BSTYP_CD)  BSTYP_NM,  NVL(B.UPSO_NEW_ADDR1,B.UPSO_ADDR1)  UPSO_ADDR,  A.BSCON_CD,  FIDU.GET_BSCON_NM(A.BSCON_CD)  BSCON_NM,  A.ROOM_NUM,  A.ACMCN_CD,  A.SERIAL_NO,  A.SERIAL_NO_SEQ,  A.ROUTER_CD,  B.STAFF_CD,  FIDU.GET_STAFF_NM(B.STAFF_CD)  STAFF_NM,  B.CLSBS_YRMN  CLSBS_YRMN,  NVL((SELECT  DISTINCT  1  FROM  GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  WHERE  UPSO_CD=A.UPSO_CD)  ,'0')  ATTACH_YN,  A.PROC_STS,  A.DSCT_YN,  A.DSCT_START,  A.DSCT_END,  A.REMAK_CHG,  A.REMAK_CHG_D,  TO_CHAR(A.BRAN_CONFIRM_DATE,'yyyymmdd')  BRAN_CONFIRM_DATE,  A.BRAN_CONFIRM_ID,  TO_CHAR(A.MNG_CONFIRM_DATE,'yyyymmdd')  MNG_CONFIRM_DATE,  A.MNG_CONFIRM_ID  FROM  GIBU.TBRA_OFF_ROUTER_MNG  A,  GIBU.TBRA_UPSO  B  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD=B.UPSO_CD   \n";
        query +=" AND  A.SERIAL_NO=#{serialNo}   \n";
        query +=" AND  PROC_STS='N' ";
        String xml ="";
            xml += "<select id=\"SQLrouter_n_search_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLrouter_n_search_SEL2() {
        String    query="";
        query +=" SELECT  SERIAL_NO,  SERIAL_NO_SEQ,  UPSO_CD,SVR_FILE_ROUT,  SVR_FILE_NM,  FILE_NM  FROM  GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  A  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD=#{upsoCd}   \n";
        query +=" AND  A.SERIAL_NO  =#{serialNo}   \n";
        query +=" AND  A.SERIAL_NO_SEQ  =#{serialNoSeq}  ORDER  BY  UPSO_CD ";
        String xml ="";
            xml += "<select id=\"SQLrouter_n_search_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLrouter_n_save_SEL2() {
        String    query="";
        query +=" SELECT  MAX(SERIAL_NO_SEQ)  +1  SERIAL_NO_SEQ  FROM  GIBU.TBRA_OFF_ROUTER_MNG  WHERE  1=1   \n";
        query +=" AND  SERIAL_NO=#{serialNo} ";
        String xml ="";
            xml += "<select id=\"SQLrouter_n_save_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLrouter_n_save_UPD7() {
        String    query="";
        query +=" Update GIBU.TBRA_OFF_ROUTER_MNG  \n";
        query +=" set MODPRES_ID=#{modpresId} , DSCT_END=#{dsctEnd} , DSCT_YN=#{dsctYn} , BSTYP_CD=#{bstypCd} , ACMCN_CD=#{acmcnCd} , ROOM_NUM=#{roomNum} , BSCON_CD=#{bsconCd} , ROUTER_CD=#{routerCd} , DSCT_START=#{dsctStart} , MOD_DATE=SYSDATE , BRAN_CD=#{branCd}  \n";
        query +=" where PROC_STS=#{procSts}  \n";
        query +=" and SERIAL_NO_SEQ=#{serialNoSeq}  \n";
        query +=" and SERIAL_NO=#{serialNo}  \n";
        query +=" and UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLrouter_n_save_UPD7\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLrouter_n_save_INS6() {
        String    query="";
        query +=" Insert into GIBU.TBRA_OFF_ROUTER_MNG (INSPRES_ID, DSCT_END, PROC_STS, DSCT_YN, BSTYP_CD, ACMCN_CD, ROOM_NUM, SERIAL_NO_SEQ, BSCON_CD, INS_DATE, REMAK_CHG, SERIAL_NO, ROUTER_CD, REMAK_CHG_D, DSCT_START, UPSO_CD, BRAN_CD)  \n";
        query +=" values(#{inspresId} , #{dsctEnd} , #{procSts} , #{dsctYn} , #{bstypCd} , #{acmcnCd} , #{roomNum} , #{serialNoSeq} , #{bsconCd} , SYSDATE, #{remakChg} , #{serialNo} , #{routerCd} , #{remakChgD} , #{dsctStart} , #{upsoCd} , #{branCd} )";
        String xml ="";
            xml += "<insert id=\"SQLrouter_n_save_INS6\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLrouter_n_save_XIUD13() {
        String    query="";
        query +=" UPDATE GIBU.TBRA_OFF_ROUTER_MNG  \n";
        query +=" SET DSCT_END=#{dsctEnd} ,DSCT_YN=#{dsctYn} ,REMAK_CHG=#{remakChg} ,REMAK_CHG_D=#{remakChgD} ,CHG_GBN=#{chgGbn} ,MOD_DATE=SYSDATE ,MODPRES_ID=#{modpresId}  \n";
        query +=" WHERE 1=1  \n";
        query +=" AND UPSO_CD =#{upsoCd}  \n";
        query +=" AND PROC_STS =#{procSts}";
        String xml ="";
            xml += "<update id=\"SQLrouter_n_save_XIUD13\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLrouter_conf_UPD5() {
        String    query="";
        query +=" Update GIBU.TBRA_OFF_ROUTER_MNG  \n";
        query +=" set SERIAL_NO=#{serialNo} , BRAN_CONFIRM_ID=#{branConfirmId} , SERIAL_NO_SEQ=#{serialNoSeq} , BRAN_CONFIRM_DATE=SYSDATE  \n";
        query +=" where PROC_STS=#{procSts}  \n";
        query +=" and UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLrouter_conf_UPD5\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLrouter_conf_XIUD8() {
        String    query="";
        query +=" UPDATE GIBU.TBRA_OFF_ROUTER_MNG  \n";
        query +=" SET PROC_STS='L', STAFF_CD=#{befStaffCd}, CHG_GBN=#{chgGbn}, CONF_DATE=SYSDATE, MOD_DATE=SYSDATE, MODPRES_ID=#{modpresId}  \n";
        query +=" WHERE 1=1  \n";
        query +=" AND UPSO_CD=#{befUpsoCd}  \n";
        query +=" AND PROC_STS='M'";
        String xml ="";
            xml += "<update id=\"SQLrouter_conf_XIUD8\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLrouter_conf_XIUD7() {
        String    query="";
        query +=" UPDATE GIBU.TBRA_OFF_ROUTER_MNG  \n";
        query +=" SET PROC_STS='M' ,MNG_CONFIRM_DATE = SYSDATE ,MNG_CONFIRM_ID =#{mngConfirmId}  \n";
        query +=" WHERE 1=1  \n";
        query +=" AND UPSO_CD=#{upsoCd}  \n";
        query +=" AND PROC_STS='N'";
        String xml ="";
            xml += "<update id=\"SQLrouter_conf_XIUD7\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLrouter_conf_XIUD10() {
        String    query="";
        query +=" MERGE  INTO  LOG.KDS_SHOP  A  USING  (SELECT  UPSO_CD,  DSCT_START,  DSCT_END,  DSCT_YN  FROM  GIBU.TBRA_OFF_ROUTER_MNG  X  WHERE  1=1   \n";
        query +=" AND  X.SERIAL_NO=#{serialNo}   \n";
        query +=" AND  X.SERIAL_NO_SEQ  =#{serialNoSeq})  B  ON  (A.UPSO_CD=B.UPSO_CD  )  WHEN  MATCHED  THEN  UPDATE  SET  A.DSCT_START=  B.DSCT_START,  A.DSCT_END=  B.DSCT_END,  A.DSCT_YN=  B.DSCT_YN  WHEN  NOT  MATCHED  THEN  INSERT  (A.UPSO_CD,A.DSCT_START,A.DSCT_END,A.DSCT_YN)  VALUES  (B.UPSO_CD,B.DSCT_START,B.DSCT_END,B.DSCT_YN) ";
        String xml ="";
            xml += "<merge  id=\"SQLrouter_conf_XIUD10\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</merge >\n" ;

        return xml;

    }

    public static String SQLrouter_file_upload_SEL_FILE() {
        String    query="";
        query +=" SELECT  '/upload_file/GIBU/OFFLINE/'  ||  SUBSTR(TO_CHAR(SYSDATE,'YYYYMM'),  1,  6)  AS  DFILEPATH  FROM  DUAL ";
        String xml ="";
            xml += "<select id=\"SQLrouter_file_upload_SEL_FILE\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLrouter_file_upload_SEL31() {
        String    query="";
        query +=" SELECT  SVR_FILE_NM  ,  SVR_FILE_ROUT  FROM  GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  SERIAL_NO=#{serialNo}   \n";
        query +=" AND  SERIAL_NO_SEQ=#{serialNoSeq} ";
        String xml ="";
            xml += "<select id=\"SQLrouter_file_upload_SEL31\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLrouter_file_upload_SEL18() {
        String    query="";
        query +=" SELECT  SERIAL_NO_SEQ  FROM  GIBU.TBRA_OFF_ROUTER_MNG  WHERE  1=1   \n";
        query +=" AND  SERIAL_NO=#{serialNo}   \n";
        query +=" AND  PROC_STS='N' ";
        String xml ="";
            xml += "<select id=\"SQLrouter_file_upload_SEL18\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLrouter_file_upload_DEL40() {
        String    query="";
        query +=" Delete from GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  \n";
        query +=" where SERIAL_NO=#{serialNo}  \n";
        query +=" and UPSO_CD=#{upsoCd}  \n";
        query +=" and SERIAL_NO_SEQ=#{serialNoSeq}";
        String xml ="";
            xml += "<delete id=\"SQLrouter_file_upload_DEL40\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLrouter_file_upload_SEL25() {
        String    query="";
        query +=" SELECT  ''  ||  #{upsoCd}  ||  '-'  ||  TO_CHAR(SYSDATE,'yyyymmdd')  ||  '-'  ||  #{serialNoSeq}  ||  '-'  ||  TO_CHAR  (SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR  (  #{fileNm},  INSTR  (  #{fileNm},  '.',  '-1'))  AS  DFILENAME  FROM  DUAL ";
        String xml ="";
            xml += "<select id=\"SQLrouter_file_upload_SEL25\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLrouter_file_upload_SEL33() {
        String    query="";
        query +=" SELECT  ''  ||  #{upsoCd}  ||  '-'  ||  #{insDay}  ||  '-'  ||  #{serialNoSeq}  ||  '-'  ||  TO_CHAR  (SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR  (  #{fileNm},  INSTR  (  #{fileNm},  '.',  '-1'))  AS  DFILENAME  FROM  DUAL ";
        String xml ="";
            xml += "<select id=\"SQLrouter_file_upload_SEL33\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLrouter_file_upload_INS31() {
        String    query="";
        query +=" Insert into GIBU.TBRA_OFF_ROUTER_MNG_ATTACH (INS_DATE, INSPRES_ID, SERIAL_NO, UPSO_CD, SVR_FILE_ROUT, SVR_FILE_NM, SERIAL_NO_SEQ, REMAK, FILE_NM)  \n";
        query +=" values(SYSDATE, #{inspresId} , #{serialNo} , #{upsoCd} , #{svrFileRout} , #{svrFileNm} , #{serialNoSeq} , #{remak} , #{fileNm} )";
        String xml ="";
            xml += "<insert id=\"SQLrouter_file_upload_INS31\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLrouter_file_upload_UPD36() {
        String    query="";
        query +=" Update GIBU.TBRA_OFF_ROUTER_MNG_ATTACH  \n";
        query +=" set MODPRES_ID=#{modpresId} , MOD_DATE=SYSDATE , SVR_FILE_ROUT=#{svrFileRout} , SVR_FILE_NM=#{svrFileNm} , REMAK=#{remak} , FILE_NM=#{fileNm}  \n";
        query +=" where SERIAL_NO=#{serialNo}  \n";
        query +=" and UPSO_CD=#{upsoCd}  \n";
        query +=" and SERIAL_NO_SEQ=#{serialNoSeq}";
        String xml ="";
            xml += "<update id=\"SQLrouter_file_upload_UPD36\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLrouter_dsct_upd_UPD1() {
        String    query="";
        query +=" Update GIBU.TBRA_OFF_ROUTER_MNG  \n";
        query +=" set MODPRES_ID=#{modpresId} , DSCT_END=#{dsctEnd} , DSCT_YN=#{dsctYn} , DSCT_START=#{dsctStart} , MOD_DATE=SYSDATE  \n";
        query +=" where SERIAL_NO=#{serialNo}  \n";
        query +=" and UPSO_CD=#{upsoCd}  \n";
        query +=" and SERIAL_NO_SEQ=#{serialNoSeq}";
        String xml ="";
            xml += "<update id=\"SQLrouter_dsct_upd_UPD1\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLrouter_dsct_upd_UPD6() {
        String    query="";
        query +=" Update LOG.KDS_SHOP  \n";
        query +=" set MODPRES_ID=#{modpresId} , DSCT_END=#{dsctEnd} , DSCT_YN=#{dsctYn} , DSCT_START=#{dsctStart} , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLrouter_dsct_upd_UPD6\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "C:\\komca\\workspace\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLrouter_search_SEL1() + "\n";
        content += SQLrouter_upso_info_SEL1() + "\n";
        content += SQLrouter_upso_info_SEL2() + "\n";
        content += SQLrouter_upso_info_SEL3() + "\n";
        content += SQLrouter_file_search_SEL1() + "\n";
        content += SQLrouter_n_del_SEL4() + "\n";
        content += SQLrouter_n_del_DEL2() + "\n";
        content += SQLrouter_n_del_XIUD1() + "\n";
        content += SQLrouter_n_del_XIUD5() + "\n";
        content += SQLrouter_n_search_SEL1() + "\n";
        content += SQLrouter_n_search_SEL2() + "\n";
        content += SQLrouter_n_save_SEL2() + "\n";
        content += SQLrouter_n_save_UPD7() + "\n";
        content += SQLrouter_n_save_INS6() + "\n";
        content += SQLrouter_n_save_XIUD13() + "\n";
        content += SQLrouter_conf_UPD5() + "\n";
        content += SQLrouter_conf_XIUD8() + "\n";
        content += SQLrouter_conf_XIUD7() + "\n";
        content += SQLrouter_conf_XIUD10() + "\n";
        content += SQLrouter_file_upload_SEL_FILE() + "\n";
        content += SQLrouter_file_upload_SEL31() + "\n";
        content += SQLrouter_file_upload_SEL18() + "\n";
        content += SQLrouter_file_upload_DEL40() + "\n";
        content += SQLrouter_file_upload_SEL25() + "\n";
        content += SQLrouter_file_upload_SEL33() + "\n";
        content += SQLrouter_file_upload_INS31() + "\n";
        content += SQLrouter_file_upload_UPD36() + "\n";
        content += SQLrouter_dsct_upd_UPD1() + "\n";
        content += SQLrouter_dsct_upd_UPD6() + "\n";
        content += bottom;

        try {
            String fileName = "bra01_s01_1.xml";
            FileWriter fw = new FileWriter(dir + "bra01_s30.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

