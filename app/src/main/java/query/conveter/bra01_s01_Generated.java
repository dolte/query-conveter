package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra01_s01_Generated {

    public static String SQLonoff_check_list_SEL1() {
        String    query="";
        query +=" SELECT  :  GBN  AS  GBN  FROM  DUAL ";
        String xml ="";
            xml += "<select id=\"SQLonoff_check_list_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLonoff_check_list_SEL2() {
        String    query="";
        query +=" SELECT  /*+  optimizer_features_enable('11.1.0.6')  */  TA.BRAN_CD  ,  TC.DEPT_NM  BRAN_NM  ,  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.BSTYP_CD  ,  NVL((SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =  TA.BSTYP_CD  ),  TA.BSTYP_CD)  GRADNM  ,  TA.MCHNDAESU  ,  TA.ONOFF_GBN  ,  TA.INS_DATE  ,  NVL((SELECT  HAN_NM  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  TA.STAFF_CD),  TA.STAFF_CD)  STAFF_NM  ,  DECODE(TA.CLSBS_YRMN,  NULL,  '',  TA.CLSBS_YRMN||'01')  CLSBS_YRMN  FROM  (   \n";
        query +=" SELECT  XB.BRAN_CD  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  XA.BSTYP_CD  ,  XA.MCHNDAESU  ,  XB.ONOFF_GBN  ,  XB.INS_DATE  ,  XB.STAFF_CD  ,  XB.CLSBS_YRMN  FROM(   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MCHNDAESU,  ZB.BSTYP_CD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(#{branCd},  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  #{branCd})   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  B.UPSO_STAT='1'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZB.BSTYP_CD  IN  ('o','k','l','y','p')  )  XA  ,  GIBU.TBRA_UPSO  XB  WHERE  XB.UPSO_CD  =  XA.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD  ,  XA.MCHNDAESU  ,  XA.ONOFF_GBN  ,  XA.INS_DATE  ,  XA.STAFF_CD  ,  XA.CLSBS_YRMN  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD  ,  B.UPSO_CD  ,  B.UPSO_NM  ,  B.MCHNDAESU  ,  B.ONOFF_GBN  ,  B.INS_DATE  ,  B.STAFF_CD  ,  B.CLSBS_YRMN  FROM  GIBU.TBRA_REPT  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.MAPPING_DAY  >=  TO_CHAR(ADD_MONTHS(SYSDATE,-3),'YYYYMM')  ||  '00'   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(#{branCd},  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  #{branCd})   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  B.BRAN_CD  ,  B.UPSO_CD  ,  B.UPSO_NM  ,  B.MCHNDAESU  ,  B.ONOFF_GBN  ,  B.INS_DATE  ,  B.STAFF_CD  ,  B.CLSBS_YRMN  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.MAPPING_DAY  >=  TO_CHAR(ADD_MONTHS(SYSDATE,-3),'YYYYMM')  ||  '00'   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(#{branCd},  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  #{branCd})   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  )  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MCHNDAESU,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(#{branCd},  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  #{branCd})   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NOT  NULL  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZB.BSTYP_CD  IN  ('o','k','l','y','p')  )  XB  WHERE  XB.UPSO_CD  =  XA.UPSO_CD  )  TA  ,  INSA.TCPM_DEPT  TC  WHERE  TC.GIBU  =  TA.BRAN_CD  ORDER  BY  TA.BRAN_CD,  TA.UPSO_CD ";
        String xml ="";
            xml += "<select id=\"SQLonoff_check_list_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLonoff_check_list_SEL5() {
        String    query="";
        query +=" SELECT  XA.UPSO_CD,  XA.UPSO_NM,  XA.STAFF_CD,  XC.HAN_NM  STAFF_NM  FROM  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  OPBI_DAY  IS  NOT  NULL   \n";
        query +=" AND  INS_DATE  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  IS  NULL   \n";
        query +=" AND  to_char(SYSDATE,  'YYYYMM')  BETWEEN  TO_CHAR(INS_DATE,  'YYYYMM')   \n";
        query +=" AND  to_char(add_months(ins_date,3),  'YYYYMM')  MINUS  (   \n";
        query +=" SELECT  A.USER_ID  AS  UPSO_CD  FROM  KOMSMS.SDK_SMS_REPORT  A  WHERE  A.SEND_DATE  LIKE  substr(#{date1},1,6)  ||  '%'  UNION   \n";
        query +=" SELECT  A.UPSO_CD  FROM  GIBU.TBRA_UPSO_VISIT  A  WHERE  A.VISIT_DAY  LIKE  substr(#{date1},1,6)  ||  '%'  ))XB,  GIBU.TBRA_UPSO  XA,  INSA.TINS_MST01  XC  WHERE  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XA.BRAN_CD  =  DECODE(#{branCd},  NULL,  XA.BRAN_CD,  'AL',  XA.BRAN_CD,  #{branCd})   \n";
        query +=" AND  XC.STAFF_NUM(+)  =  XA.STAFF_CD  ORDER  BY  XA.BRAN_CD,  XA.STAFF_CD ";
        String xml ="";
            xml += "<select id=\"SQLonoff_check_list_SEL5\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLonoff_check_list_SEL3() {
        String    query="";
        query +=" SELECT  /*+  optimizer_features_enable('11.1.0.6')  */  SUM(CNT)  AS  CNT  FROM  (   \n";
        query +=" SELECT  COUNT(XA.UPSO_CD)  AS  CNT  FROM  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  AS  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(#{branCd},  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  #{branCd})   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  B.UPSO_STAT  =  '1'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZB.BSTYP_CD  IN  ('o',  'k',  'l',  'y',  'p')  )  XA  ,  GIBU.TBRA_UPSO  XB  WHERE  XB.UPSO_CD  =  XA.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  COUNT(XA.UPSO_CD)  AS  CNT  FROM  (   \n";
        query +=" SELECT  B.UPSO_CD  FROM  GIBU.TBRA_REPT  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.MAPPING_DAY  >=  '20100500'   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(#{branCd},  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  #{branCd})   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  B.UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_UPSO  B  WHERE  SUBSTR(A.MAPPING_DAY,  0,  6)  >=  '201005'   \n";
        query +=" AND  B.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD)  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  AS  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(#{branCd},  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  #{branCd})   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NOT  NULL  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZB.BSTYP_CD  IN  ('o',  'k',  'l',  'y',  'p')  )  XB  WHERE  XB.UPSO_CD  =  XA.UPSO_CD) ";
        String xml ="";
            xml += "<select id=\"SQLonoff_check_list_SEL3\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLnorebang_info_SEL1() {
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(A.BSTYP_CD)  ||  A.GRAD_GBN  GRAD  ,  A.AREA  ,  A.MCHNDAESU  ,  A.STNDAMT  ,  B.GRADNM  ,  A.CHG_NUM  ,  C.CHG_DAY  ,  C.CHG_BRAN  FROM  GIBU.TBRA_NOREBANG_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  C.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  A.GRAD_GBN  =  B.GRAD_GBN ";
        String xml ="";
            xml += "<select id=\"SQLnorebang_info_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLcheck_chgowner_SEL1() {
        String    query="";
        query +=" SELECT  UPSO_CD  ,  UPSO_NM  ,  0  CNT  FROM  GIBU.TBRA_UPSO  WHERE  BEFORE_UPSO_CD  =  #{upsoCd}  UNION  ALL   \n";
        query +=" SELECT  ''  UPSO_CD  ,  ''  UPSO_NM  ,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  #{upsoCd} ";
        String xml ="";
            xml += "<select id=\"SQLcheck_chgowner_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLaddr_chk_SEL1() {
        String    query="";
        query +=" SELECT  UPSO_CD  ,  UPSO_NM  ,  MNGEMSTR_NM  ,  UPSO_NEW_ADDR1  ||  DECODE(UPSO_NEW_ADDR2,  '',  '',  ',  '||UPSO_NEW_ADDR2)  ||  UPSO_REF_INFO  UPSO_ADDR  ,  CLSBS_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_NEW_ADDR1  =  #{upsoAddr1}  ORDER  BY  UPSO_CD ";
        String xml ="";
            xml += "<select id=\"SQLaddr_chk_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_SEL44() {
        String    query="";
        query +=" SELECT  LPAD(SNUM+1,7,'0')  SNUM  ,  SNUM_TYPE  ,  CDCLASS  ,  LPAD(SNUM+1,7,'0')  ||  CDCLASS  N_UPSO_CD  FROM  GIBU.TBRA_SNUM  WHERE  SNUM_TYPE  =  'U'   \n";
        query +=" AND  CDCLASS  =  #{branCd} ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_onoff_SEL44\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_SEL25() {
        String    query="";
        query +=" SELECT  UPSO_STAT  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  #{upsoCd} ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_onoff_SEL25\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_UPD44() {
        String    query="";
        query +=" Update GIBU.TBRA_SNUM  \n";
        query +=" set MODPRES_ID=#{modpresId} , MOD_DATE=SYSDATE , SNUM=#{snum}  \n";
        query +=" where CDCLASS=#{cdclass}  \n";
        query +=" and SNUM_TYPE=#{snumType}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_onoff_UPD44\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_INS6() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO (MNGEMSTR_HPNUM, PAYPRES_GBN, SONG_STUDENT, OPBI_DAY, UPSO_NEW_ADDR1, MNGEMSTR_ADDR1, INS_DATE, PERMMSTR_HPNUM, UPSO_STAT, AREA, PERMMSTR_REMAK, CLIENT_NUM, PERMMSTR_NM, PERMMSTR_RESINUM, MNGEMSTR_NEW_ADDR2, PERMMSTR_BD_MNG_NUM, UPSO_REMAK, MNGEMSTR_ADDR2, BSCON_CD, MNGEMSTR_NEW_ADDR1, MNG_ZIP, MAIL_RCPT, PERMMSTR_ADDR1, MNGEMSTR_BD_MNG_NUM, BIOWN_NUM, AERO_STUDENT, UPSO_NEW_ZIP1, UPSO_BD_MNG_NUM, BEFORE_UPSO_CD, INSPRES_ID, PERMMSTR_ZIP, UPSO_PHON, PERMMSTR_NEW_ZIP, BILL_ISS_YN, UPSO_REF_INFO, UPSO_ADDR1, STAFF_CD, MNGEMSTR_ZIP, MNGEMSTR_RESINUM, MNGEMSTR_PHONNUM, UPSO_CD, CONTR_NM, UPSO_NM, UPSO_NEW_ZIP, PAPER_CANC_YN, UPSO_NEW_ADDR2, PERMMSTR_REF_INFO, UPSO_ADDR2, MCHNDAESU, MNGEMSTR_REMAK, CORP_NUM, EMAIL_ADDR, PERMMSTR_ADDR2, MNGEMSTR_NM, PERMMSTR_NEW_ADDR1, PERMMSTR_NEW_ADDR2, MNGEMSTR_NEW_ZIP, MNGEMSTR_REF_INFO, PERMMSTR_PHONNUM, BRAN_CD, UPSO_ZIP)  \n";
        query +=" values(#{mngemstrHpnum} , #{paypresGbn} , #{songStudent} , #{opbiDay} , #{upsoNewAddr1} , #{mngemstrAddr1} , SYSDATE, #{permmstrHpnum} , #{upsoStat} , #{area} , #{permmstrRemak} , GIBU.FT_GET_AUTO_CLIENTNUM(#{clientNum1}), #{permmstrNm} , #{permmstrResinum} , #{mngemstrNewAddr2} , #{permmstrBdMngNum} , #{upsoRemak} , #{mngemstrAddr2} , #{bsconCd} , #{mngemstrNewAddr1} , #{mngZip} , #{mailRcpt} , #{permmstrAddr1} , #{mngemstrBdMngNum} , #{biownNum} , #{aeroStudent} , #{upsoNewZip1} , #{upsoBdMngNum} , #{beforeUpsoCd} , #{inspresId} , #{permmstrZip} , #{upsoPhon} , #{permmstrNewZip} , #{billIssYn} , #{upsoRefInfo} , #{upsoAddr1} , #{staffCd} , #{mngemstrZip} , #{mngemstrResinum} , #{mngemstrPhonnum} , #{upsoCd} , #{contrNm} , REPLACE(REPLACE(#{upsoNm1}, CHR(13), ''), CHR(10), ''), #{upsoNewZip} , #{paperCancYn} , #{upsoNewAddr2} , #{permmstrRefInfo} , #{upsoAddr2} , #{mchndaesu} , #{mngemstrRemak} , #{corpNum} , REPLACE(REPLACE(#{emailAddr1}, CHR(13), ''), CHR(10), ''), #{permmstrAddr2} , #{mngemstrNm} , #{permmstrNewAddr1} , #{permmstrNewAddr2} , #{mngemstrNewZip} , #{mngemstrRefInfo} , #{permmstrPhonnum} , #{branCd} , #{upsoZip} )";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_onoff_INS6\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_DEL27() {
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" where UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<delete id=\"SQLupso_regist_onoff_DEL27\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_DEL34() {
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" where UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<delete id=\"SQLupso_regist_onoff_DEL34\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_UPD28() {
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set AERO_STUDENT=#{aeroStudent} , MNGEMSTR_HPNUM=#{mngemstrHpnum} , SONG_STUDENT=#{songStudent} , OPBI_DAY=#{opbiDay} , BILL_ISS_YN=#{billIssYn} , MNGEMSTR_ADDR1=#{mngemstrAddr1} , UPSO_ADDR1=#{upsoAddr1} , STAFF_CD=#{staffCd} , UPSO_STAT=#{upsoStat} , MNGEMSTR_PHONNUM=#{mngemstrPhonnum} , MNGEMSTR_RESINUM=#{mngemstrResinum} , MNGEMSTR_ZIP=#{mngemstrZip} , AREA=#{area} , CONTR_NM=#{contrNm} , UPSO_NM=REPLACE(REPLACE(#{upsoNm1}, CHR(13), ''), CHR(10), '') , PERMMSTR_NM=#{permmstrNm} , PERMMSTR_RESINUM=#{permmstrResinum} , PAPER_CANC_YN=#{paperCancYn} , MCHNDAESU=#{mchndaesu} , UPSO_ADDR2=#{upsoAddr2} , UPSO_REMAK=#{upsoRemak} ,  \n";
        query +=" CORP_NUM=#{corpNum} , EMAIL_ADDR=REPLACE(REPLACE(#{emailAddr1}, CHR(13), ''), CHR(10), '') , MNGEMSTR_NM=#{mngemstrNm} , MNGEMSTR_ADDR2=#{mngemstrAddr2} , BSCON_CD=#{bsconCd} , MNG_ZIP=#{mngZip} , NEW_DAY=#{newDay} , MAIL_RCPT=#{mailRcpt} , PERMMSTR_PHONNUM=#{permmstrPhonnum} , BIOWN_NUM=#{biownNum} , BRAN_CD=#{branCd} , UPSO_ZIP=#{upsoZip}  \n";
        query +=" where UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_onoff_UPD28\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_SEL29() {
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(CHG_NUM),  0)  +  1,  4,  '0')  CHG_NUM  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  CHG_BRAN  =  #{branCd}   \n";
        query +=" AND  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  CHG_DAY  =  TO_CHAR(SYSDATE,'YYYYMMDD') ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_onoff_SEL29\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_INS62() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSORTAL_INFO (JOIN_SEQ, APPL_DAY, CHG_BRAN, CHG_DAY, INSPRES_ID, UPSO_GRAD, MCHNDAESU, MONPRNCFEE, BSTYP_CD, INS_DATE, MONPRNCFEE2, CHG_NUM, UPSO_CD)  \n";
        query +=" values((SELECT NVL(MAX(JOIN_SEQ), 0) + 1 JOIN_SEQ FROM GIBU.TBRA_UPSORTAL_INFO ), #{applDay} , #{chgBran} , TO_CHAR(SYSDATE,'YYYYMMDD'), #{inspresId} , #{upsoGrad} , #{mchndaesu} , #{monprncfee} , #{bstypCd} , SYSDATE, #{monprncfee2} , #{chgNum} , #{upsoCd} )";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_onoff_INS62\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_INS30() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSORTAL_INFO (JOIN_SEQ, APPL_DAY, CHG_BRAN, CHG_DAY, INSPRES_ID, UPSO_GRAD, USE_TIME, MCHNDAESU, MONPRNCFEE, BSTYP_CD, INS_DATE, CHG_NUM, UPSO_CD)  \n";
        query +=" values((SELECT NVL(MAX(JOIN_SEQ), 0) + 1 JOIN_SEQ FROM GIBU.TBRA_UPSORTAL_INFO ), DECODE(#{applDay1},NULL,TO_CHAR(SYSDATE,'YYYYMMDD'),#{applDay2}), #{chgBran} , TO_CHAR(SYSDATE,'YYYYMMDD'), #{inspresId} , #{upsoGrad} , #{useTime} , #{mchndaesu} , #{monprncfee} , #{bstypCd} , SYSDATE, #{chgNum} , #{upsoCd} )";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_onoff_INS30\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_INS63() {
        String    query="";
        query +=" Insert into GIBU.TBRA_NOREBANG_INFO (INS_DATE, CHG_BRAN, INSPRES_ID, CHG_NUM, CHG_DAY, MCHNDAESU, AREA, UPSO_CD, BSTYP_CD, GRAD_GBN, GRAD_NUM)  \n";
        query +=" values(SYSDATE, #{chgBran} , #{inspresId} , #{chgNum} , TO_CHAR(SYSDATE,'YYYYMMDD'), #{mchndaesu} , #{area} , #{upsoCd} , #{bstypCd} , #{gradGbn} , (SELECT NVL(MAX(GRAD_NUM), 0) + 1 GRAD_NUM FROM GIBU.TBRA_NOREBANG_INFO WHERE UPSO_CD = #{gradNum1}))";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_onoff_INS63\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_INS32() {
        String    query="";
        query +=" Insert into GIBU.TBRA_NOREBANG_INFO (INS_DATE, CHG_BRAN, INSPRES_ID, CHG_NUM, CHG_DAY, STNDAMT, MCHNDAESU, AREA, UPSO_CD, BSTYP_CD, GRAD_GBN, GRAD_NUM)  \n";
        query +=" values(SYSDATE, #{chgBran} , #{inspresId} , #{chgNum} , TO_CHAR(SYSDATE,'YYYYMMDD'), #{stndamt} , #{mchndaesu} , #{area} , #{upsoCd} , #{bstypCd} , #{gradGbn} , (SELECT NVL(MAX(GRAD_NUM), 0) + 1 GRAD_NUM FROM GIBU.TBRA_NOREBANG_INFO WHERE UPSO_CD = #{upsoCd}))";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_onoff_INS32\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_UPD14() {
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MNGEMSTR_HPNUM=#{mngemstrHpnum} , MODPRES_ID=#{modpresId} , PAYPRES_GBN=#{paypresGbn} , PERMMSTR_NEW_ZIP1=#{permmstrNewZip1} , SONG_STUDENT=#{songStudent} , OPBI_DAY=#{opbiDay} , UPSO_NEW_ADDR1=#{upsoNewAddr1} , MNGEMSTR_NEW_ZIP1=#{mngemstrNewZip1} , PERMMSTR_HPNUM=#{permmstrHpnum} , UPSO_STAT=#{upsoStat} , AREA=#{area} , PERMMSTR_REMAK=#{permmstrRemak} , PERMMSTR_NM=#{permmstrNm} , PERMMSTR_RESINUM=#{permmstrResinum} , MNGEMSTR_NEW_ADDR2=#{mngemstrNewAddr2} , PERMMSTR_BD_MNG_NUM=#{permmstrBdMngNum} , UPSO_REMAK=#{upsoRemak} , BSCON_CD=#{bsconCd} , MNGEMSTR_NEW_ADDR1=#{mngemstrNewAddr1} , NEW_DAY=#{newDay} , MNG_ZIP=#{mngZip} , MAIL_RCPT=#{mailRcpt} , MOD_DATE=SYSDATE , MNGEMSTR_BD_MNG_NUM=#{mngemstrBdMngNum} , BIOWN_NUM=#{biownNum} , AERO_STUDENT=#{aeroStudent} , UPSO_BD_MNG_NUM=#{upsoBdMngNum} , UPSO_NEW_ZIP1=#{upsoNewZip1} , UPSO_PHON=#{upsoPhon} , BILL_ISS_YN=#{billIssYn} , PERMMSTR_NEW_ZIP=#{permmstrNewZip} , UPSO_REF_INFO=#{upsoRefInfo} , STAFF_CD=#{staffCd} , MNGEMSTR_RESINUM=#{mngemstrResinum} , MNGEMSTR_PHONNUM=#{mngemstrPhonnum} , CONTR_NM=#{contrNm} , UPSO_NM=REPLACE(REPLACE(#{upsoNm1}, CHR(13), ''), CHR(10), '') , UPSO_NEW_ZIP=#{upsoNewZip} , PAPER_CANC_YN=#{paperCancYn} , UPSO_NEW_ADDR2=#{upsoNewAddr2} , PERMMSTR_REF_INFO=#{permmstrRefInfo} , MCHNDAESU=#{mchndaesu} , MNGEMSTR_REMAK=#{mngemstrRemak} ,  \n";
        query +=" CORP_NUM=#{corpNum} , EMAIL_ADDR=REPLACE(REPLACE(#{emailAddr1}, CHR(13), ''), CHR(10), '') , MNGEMSTR_NM=#{mngemstrNm} , PERMMSTR_NEW_ADDR1=#{permmstrNewAddr1} , PERMMSTR_NEW_ADDR2=#{permmstrNewAddr2} , MNGEMSTR_NEW_ZIP=#{mngemstrNewZip} , MNGEMSTR_REF_INFO=#{mngemstrRefInfo} , PERMMSTR_PHONNUM=#{permmstrPhonnum}  \n";
        query +=" where UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_onoff_UPD14\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_XIUD65() {
        String    query="";
        query +=" COMMIT ";
        String xml ="";
            xml += "<commit id=\"SQLupso_regist_onoff_XIUD65\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</commit>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_SEL35() {
        String    query="";
        query +=" SELECT  UPSO_CD  ,  CHG_DAY  ,  CHG_NUM  ,  CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  JOIN_SEQ  =  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  NVL(APPL_DAY,  CHG_DAY)  <=  TO_CHAR(SYSDATE,  'YYYYMMDD')  ) ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_onoff_SEL35\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_UPD34() {
        String    query="";
        query +=" Update  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" set MCHNDAESU=#{mchndaesu}  \n";
        query +=" where CHG_BRAN=#{chgBran}  \n";
        query +=" and CHG_NUM=#{chgNum}  \n";
        query +=" and CHG_DAY=#{chgDay}  \n";
        query +=" and UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_onoff_UPD34\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_INS55() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_ONOFF_INFO (INS_DATE, MODEL_CD, INSPRES_ID, ACMCN_DAESU, ONOFF_GBN, UPSO_CD)  \n";
        query +=" values(SYSDATE, #{modelCd} , #{inspresId} , #{acmcnDaesu} , #{onoffGbn} , #{upsoCd} )";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_onoff_INS55\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_SEL56() {
        String    query="";
        query +=" SELECT  DISTINCT  ONOFF_GBN  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  WHERE  UPSO_CD  =  #{upsoCd} ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_onoff_SEL56\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_SEL36() {
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  VISIT_DAY  =  TO_CHAR(SYSDATE,'YYYYMMDD')   \n";
        query +=" AND  JOB_GBN  =  #{jobGbn} ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_onoff_SEL36\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_UPD60() {
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=#{modpresId} , ONOFF_GBN=#{onoffGbn} , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_onoff_UPD60\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_INS21() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, VISIT_TIME, INSPRES_ID, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(#{visitSeq} , SYSDATE, TO_CHAR(SYSDATE, 'HH24MI'), #{inspresId} , TO_CHAR(SYSDATE,'YYYYMMDD'), #{jobGbn} , #{upsoCd} , #{conspres} , #{remak} )";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_onoff_INS21\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_DEL113() {
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" where CHG_NUM=#{chgNum}  \n";
        query +=" and UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<delete id=\"SQLupso_regist_onoff_DEL113\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_INS39() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(#{visitSeq} , SYSDATE, #{inspresId} , TO_CHAR(SYSDATE,'YYYYMMDD'), #{jobGbn} , (SELECT NVL(MAX(VISIT_NUM),0)+1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = TO_CHAR(SYSDATE,'YYYYMMDD') AND UPSO_CD = #{visitNum1} AND JOB_GBN = 'U' ), #{upsoCd} , #{remak} )";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_onoff_INS39\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_DEL114() {
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" where CHG_NUM=#{chgNum}  \n";
        query +=" and UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<delete id=\"SQLupso_regist_onoff_DEL114\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_INS38() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(#{visitSeq} , SYSDATE, #{inspresId} , TO_CHAR(SYSDATE,'YYYYMMDD'), #{jobGbn} , (SELECT NVL(MAX(VISIT_NUM),0)+1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = TO_CHAR(SYSDATE,'YYYYMMDD') AND UPSO_CD = #{visitNum1} AND JOB_GBN = 'U' ), #{upsoCd} , #{remak} )";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_onoff_INS38\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_XIUD55() {
        String    query="";
        query +=" COMMIT ";
        String xml ="";
            xml += "<commit id=\"SQLupso_regist_onoff_XIUD55\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</commit>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_XIUD41() {
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_ONOFF_INFO ( UPSO_CD , MODEL_CD , ONOFF_GBN , ACMCN_DAESU , INSPRES_ID , INS_DATE ) SELECT #{upsoCd} , MODEL_CD , ONOFF_GBN , ACMCN_DAESU , #{inspresId} , SYSDATE FROM GIBU.TBRA_UPSO_ONOFF_INFO WHERE UPSO_CD = #{beforeUpsoCd}";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_onoff_XIUD41\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_XIUD42() {
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_ACMCN_INFO ( UPSO_CD , MODEL_CD , ACMCN_DAESU , INSPRES_ID , INS_DATE ) SELECT #{upsoCd} , MODEL_CD , ACMCN_DAESU , #{inspresId} , SYSDATE FROM GIBU.TBRA_UPSO_ACMCN_INFO WHERE UPSO_CD = #{beforeUpsoCd}";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_onoff_XIUD42\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_XIUD43() {
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_AUBRY_INFO ( UPSO_CD , MODEL_CD , MODEL_NM , MCHN_COMPY , MCHN_COMPYNM , ACMCN_DAESU , INSPRES_ID , INS_DATE ) SELECT #{upsoCd} , MODEL_CD , MODEL_NM , MCHN_COMPY , MCHN_COMPYNM , ACMCN_DAESU , #{inspresId} , SYSDATE FROM GIBU.TBRA_UPSO_AUBRY_INFO WHERE UPSO_CD = #{beforeUpsoCd}";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_onoff_XIUD43\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_XIUD50() {
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET ONOFF_GBN = ( SELECT ONOFF_GBN FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE UPSO_CD =  \n";
        query +=" #{beforeUpsoCd})  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_onoff_XIUD50\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_XIUD68() {
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_VISIT (UPSO_CD, VISIT_DAY, VISIT_SEQ, JOB_GBN, CONSPRES, CONS_DATE, CONS_SEQ, REMAK, INSPRES_ID, INS_DATE, MODPRES_ID, MOD_DATE, VISIT_TIME) SELECT #{newUpsoCd} , TO_CHAR(INS_DATE, 'YYYYMMDD') AS VISIT_DAY , '1' AS VISIT_SEQ , 'V' AS JOB_GBN , '' AS CONSPRES , '' AS CONS_DATE , '' AS CONS_SEQ , '신규업소 개발(출장)' AS REMAK , INSPRES_ID , INS_DATE , '' AS MODPRES_ID , '' AS MOD_DATE , TO_CHAR(INS_DATE, 'HH24MI') AS VISIT_TIME FROM GIBU.TMOB_NEW_UPSO WHERE UPSO_CD = #{upsoCd}";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_onoff_XIUD68\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_SEL66() {
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TMOB_NEW_UPSO_ATTACH  WHERE  UPSO_CD  =  #{upsoCd} ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_onoff_SEL66\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_XIUD69() {
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_VISIT_ATTCH (UPSO_CD, VISIT_DAY, VISIT_SEQ, JOB_GBN, SEQ, FILE_NM, FILE_ROUT, FILE_TYPE, FILE_TEMPNM, FILE_SIZE, FILES) SELECT #{newUpsoCd} , TO_CHAR(A.INS_DATE, 'YYYYMMDD') AS VISIT_DAY , '1' AS VISIT_SEQ , 'V' AS JOB_GBN , '1' AS SEQ , FILE_NM , FILE_ROUT , FILE_TYPE , FILE_TEMPNM , FILE_SIZE , FILES FROM GIBU.TMOB_NEW_UPSO A , GIBU.TMOB_NEW_UPSO_ATTACH B WHERE A.UPSO_CD = B.UPSO_CD AND A.UPSO_CD = #{upsoCd}";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_onoff_XIUD69\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_XIUD66() {
        String    query="";
        query +=" UPDATE GIBU.TMOB_NEW_UPSO  \n";
        query +=" SET UPSO_CD = #{newUpsoCd}  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_onoff_XIUD66\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_XIUD70() {
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_MAP  \n";
        query +=" SET UPSO_CD = #{newUpsoCd}  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_onoff_XIUD70\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_SEL79() {
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_UPSO_AUTO_APPLICATION  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  PROC_DAY  IS  NULL   \n";
        query +=" AND  AUTO_NUM  IS  NULL ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_onoff_SEL79\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_XIUD84() {
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" SET UPSO_CD = #{newUpsoCd}  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}  \n";
        query +=" AND PROC_DAY IS NULL  \n";
        query +=" AND AUTO_NUM IS NULL";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_onoff_XIUD84\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_XIUD81() {
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_AUTO_DOC_ATTCH  \n";
        query +=" SET UPSO_CD = #{newUpsoCd}  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_onoff_XIUD81\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_UPD63() {
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=#{modpresId} , ONOFF_GBN=#{onoffGbn} , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_onoff_UPD63\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_UPD62() {
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=#{modpresId} , ONOFF_GBN=#{onoffGbn} , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_onoff_UPD62\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_UPD59() {
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO  \n";
        query +=" set GBN=#{gbn} , EMAIL=#{email}  \n";
        query +=" where UPSO_CD=#{upsoCd}  \n";
        query +=" and AUTO_NUM=#{autoNum}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_onoff_UPD59\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_SEL17() {
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.UPSO_NM  ,  A.BIOWN_NUM  ,  A.UPSO_PHON  ,  A.UPSO_ZIP  ,  A.UPSO_ADDR1  ,  A.UPSO_ADDR2  ,  A.MNGEMSTR_NM  ,  A.MNGEMSTR_PHONNUM  ,  A.MNGEMSTR_RESINUM  ,  A.MNGEMSTR_HPNUM  ,  A.MNGEMSTR_ZIP  ,  A.MNGEMSTR_ADDR1  ,  A.MNGEMSTR_ADDR2  ,  A.MNGEMSTR_REMAK  ,  A.PERMMSTR_NM  ,  A.PERMMSTR_PHONNUM  ,  A.PERMMSTR_RESINUM  ,  A.PERMMSTR_HPNUM  ,  A.PERMMSTR_ZIP  ,  A.PERMMSTR_ADDR1  ,  A.PERMMSTR_ADDR2  ,  A.PERMMSTR_REMAK  ,  A.OPBI_DAY  ,  A.PAYPRES_GBN  ,  A.NEW_DAY  ,  A.MAIL_RCPT  ,  A.PAPER_CANC_YN  ,  A.STAFF_CD  ,  D.HAN_NM  STAFF_NM  ,  A.UPSO_STAT  ,  A.BEFORE_UPSO_CD  ,  TRIM(B.BSTYP_CD)  ||  B.UPSO_GRAD  GRAD  ,  TRIM(B.BSTYP_CD)  BSTYP_CD  ,  B.UPSO_GRAD  ,  B.MONPRNCFEE  ,  B.USE_TIME  ,  TRIM(B.B_BSTYP_CD)  ||  B.B_UPSO_GRAD  B_GRAD  ,  B.B_BSTYP_CD  ,  B.B_UPSO_GRAD  ,  B.B_MONPRNCFEE  ,  B.B_USE_TIME  ,  B.GRADNM  ,  B.B_GRADNM  ,  B.CHG_DAY  ,  B.CHG_NUM  ,  B.CHG_BRAN  ,  B.B_CHG_DAY  ,  B.B_CHG_NUM  ,  B.B_CHG_BRAN  ,  TO_CHAR(A.INS_DATE,'YYYYMMDD')  INS_DATE  ,  B.B_UPSO_NM  ,  C.MCHNDAESU  ,  C.B_MCHNDAESU  ,  DECODE(A.CLSBS_YRMN,  NULL,  A.CLSBS_YRMN,  A.CLSBS_YRMN  ||  '01')  CLSBS_YRMN  ,  A.CLIENT_NUM  ,  A.BSCON_CD  ,  E.BSCONHAN_NM  ,  A.BILL_ISS_YN  ,  A.UPSO_REMAK  ,  A.BRAN_CD  ,  A.MNG_ZIP  ,  A.CORP_NUM  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  MAX(DECODE(DUMMY,  '1',  UPSO_CD  ))  UPSO_CD  ,  MAX(DECODE(DUMMY,  '1',  UPSO_GRAD  ))  UPSO_GRAD  ,  MAX(DECODE(DUMMY,  '1',  MONPRNCFEE))  MONPRNCFEE  ,  MAX(DECODE(DUMMY,  '1',  USE_TIME  ))  USE_TIME  ,  MAX(DECODE(DUMMY,  '1',  BSTYP_CD  ))  BSTYP_CD  ,  MAX(DECODE(DUMMY,  '1',  GRADNM  ))  GRADNM  ,  MAX(DECODE(DUMMY,  '1',  CHG_DAY))  CHG_DAY  ,  MAX(DECODE(DUMMY,  '1',  CHG_NUM))  CHG_NUM  ,  MAX(DECODE(DUMMY,  '1',  CHG_BRAN  ))  CHG_BRAN  ,  MAX(DECODE(DUMMY,  '2',  UPSO_CD  ))  B_UPSO_CD  ,  MAX(DECODE(DUMMY,  '2',  UPSO_GRAD  ))  B_UPSO_GRAD  ,  MAX(DECODE(DUMMY,  '2',  MONPRNCFEE))  B_MONPRNCFEE  ,  MAX(DECODE(DUMMY,  '2',  USE_TIME  ))  B_USE_TIME  ,  MAX(DECODE(DUMMY,  '2',  BSTYP_CD  ))  B_BSTYP_CD  ,  MAX(DECODE(DUMMY,  '2',  GRADNM  ))  B_GRADNM  ,  MAX(DECODE(DUMMY,  '2',  UPSO_NM  ))  B_UPSO_NM  ,  MAX(DECODE(DUMMY,  '2',  CHG_DAY))  B_CHG_DAY  ,  MAX(DECODE(DUMMY,  '2',  CHG_NUM))  B_CHG_NUM  ,  MAX(DECODE(DUMMY,  '2',  CHG_BRAN  ))  B_CHG_BRAN  FROM  (   \n";
        query +=" SELECT  *  FROM  (   \n";
        query +=" SELECT  '1'  DUMMY  ,  A.UPSO_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.USE_TIME  ,  A.BSTYP_CD  ,  ''  UPSO_NM  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  B.BSTYP_CD  =  A.BSTYP_CD   \n";
        query +=" AND  B.GRAD_GBN  =  A.UPSO_GRAD  ORDER  BY  A.CHG_DAY  DESC  ,A.CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  UNION  ALL   \n";
        query +=" SELECT  *  FROM  (   \n";
        query +=" SELECT  '2'  DUMMY  ,  A.UPSO_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.USE_TIME  ,  A.BSTYP_CD  ,  C.UPSO_NM  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  (   \n";
        query +=" SELECT  BEFORE_UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  #{upsoCd})   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  B.BSTYP_CD  =  A.BSTYP_CD   \n";
        query +=" AND  B.GRAD_GBN  =  A.UPSO_GRAD  ORDER  BY  A.CHG_DAY  DESC  ,A.CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  )  )  B  ,  (   \n";
        query +=" SELECT  MAX(MCHNDAESU)  MCHNDAESU  ,  MAX(B_MCHNDAESU)  B_MCHNDAESU  ,  MAX(UPSO_CD)  UPSO_CD  FROM  (   \n";
        query +=" SELECT  MCHNDAESU  MCHNDAESU  ,  NULL  B_MCHNDAESU  ,  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  #{upsoCd}  UNION  ALL   \n";
        query +=" SELECT  NULL  ,  MCHNDAESU  B_MCHNDAESU  ,  NULL  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  (   \n";
        query +=" SELECT  BEFORE_UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  #{upsoCd}  )  )  )  C  ,  INSA.TINS_MST01  D  ,  FIDU.TLEV_BSCON  E  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  B.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  C.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  D.STAFF_NUM  (+)  =  A.STAFF_CD   \n";
        query +=" AND  E.BSCON_CD(+)  =  A.BSCON_CD ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_onoff_SEL17\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_SEL24() {
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(A.BSTYP_CD)  ||  A.GRAD_GBN  GRAD  ,  A.AREA  ,  A.MCHNDAESU  ,  B.STNDAMT  ,  B.GRADNM  ,  A.MCHNDAESU  *  B.STNDAMT  AMT  FROM  GIBU.TBRA_NOREBANG_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  C.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  A.GRAD_GBN  =  B.GRAD_GBN  AND	A.CHG_DAY  =  #{chgDay}  AND	A.CHG_NUM  =  #{chgNum}  AND	A.CHG_BRAN  =  #{chgBran} ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_onoff_SEL24\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_onoff_XIUD80() {
        String    query="";
        query +=" UPDATE GIBU.TFMS_UPSO  \n";
        query +=" SET GBN = #{gbn} , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}  \n";
        query +=" AND USE_YN = 'Y'  \n";
        query +=" AND GBN IS NOT NULL";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_onoff_XIUD80\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_SEL44() {
        String    query="";
        query +=" SELECT  LPAD(SNUM+1,7,'0')  SNUM  ,  SNUM_TYPE  ,  CDCLASS  ,  LPAD(SNUM+1,7,'0')  ||  CDCLASS  N_UPSO_CD  FROM  GIBU.TBRA_SNUM  WHERE  SNUM_TYPE  =  'U'   \n";
        query +=" AND  CDCLASS  =  #{branCd} ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_ky_SEL44\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_SEL25() {
        String    query="";
        query +=" SELECT  UPSO_STAT  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  #{upsoCd} ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_ky_SEL25\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_UPD44() {
        String    query="";
        query +=" Update GIBU.TBRA_SNUM  \n";
        query +=" set MODPRES_ID=#{modpresId} , MOD_DATE=SYSDATE , SNUM=#{snum}  \n";
        query +=" where CDCLASS=#{cdclass}  \n";
        query +=" and SNUM_TYPE=#{snumType}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_ky_UPD44\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_INS6() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO (MNGEMSTR_HPNUM, PAYPRES_GBN, OPBI_DAY, UPSO_NEW_ADDR1, MNGEMSTR_ADDR1, INS_DATE, PERMMSTR_HPNUM, UPSO_STAT, AREA, PERMMSTR_REMAK, CLIENT_NUM, PERMMSTR_NM, PERMMSTR_RESINUM, MNGEMSTR_NEW_ADDR2, PERMMSTR_BD_MNG_NUM, UPSO_REMAK, MNGEMSTR_ADDR2, BSCON_CD, MNGEMSTR_NEW_ADDR1, MNG_ZIP, MAIL_RCPT, PERMMSTR_ADDR1, MNGEMSTR_BD_MNG_NUM, BIOWN_NUM, UPSO_NEW_ZIP1, UPSO_BD_MNG_NUM, BEFORE_UPSO_CD, INSPRES_ID, PERMMSTR_ZIP, UPSO_PHON, PERMMSTR_NEW_ZIP, BILL_ISS_YN, UPSO_REF_INFO, UPSO_ADDR1, STAFF_CD, MNGEMSTR_ZIP, MNGEMSTR_RESINUM, MNGEMSTR_PHONNUM, UPSO_CD, UPSO_NM, UPSO_NEW_ZIP, PAPER_CANC_YN, UPSO_NEW_ADDR2, PERMMSTR_REF_INFO, UPSO_ADDR2, MCHNDAESU, MNGEMSTR_REMAK, CORP_NUM, EMAIL_ADDR, PERMMSTR_ADDR2, MNGEMSTR_NM, PERMMSTR_NEW_ADDR1, PERMMSTR_NEW_ADDR2, MNGEMSTR_NEW_ZIP, MNGEMSTR_REF_INFO, PERMMSTR_PHONNUM, BRAN_CD, UPSO_ZIP)  \n";
        query +=" values(#{mngemstrHpnum} , #{paypresGbn} , #{opbiDay} , #{upsoNewAddr1} , #{mngemstrAddr1} , SYSDATE, #{permmstrHpnum} , #{upsoStat} , #{area} , #{permmstrRemak} , GIBU.FT_GET_AUTO_CLIENTNUM(#{clientNum1}), #{permmstrNm} , #{permmstrResinum} , #{mngemstrNewAddr2} , #{permmstrBdMngNum} , #{upsoRemak} , #{mngemstrAddr2} , #{bsconCd} , #{mngemstrNewAddr1} , #{mngZip} , #{mailRcpt} , #{permmstrAddr1} , #{mngemstrBdMngNum} , #{biownNum} , #{upsoNewZip1} , #{upsoBdMngNum} , #{beforeUpsoCd} , #{inspresId} , #{permmstrZip} , #{upsoPhon} , #{permmstrNewZip} , #{billIssYn} , #{upsoRefInfo} , #{upsoAddr1} , #{staffCd} , #{mngemstrZip} , #{mngemstrResinum} , #{mngemstrPhonnum} , #{upsoCd} , #{upsoNm} , #{upsoNewZip} , #{paperCancYn} , #{upsoNewAddr2} , #{permmstrRefInfo} , #{upsoAddr2} , #{mchndaesu} , #{mngemstrRemak} , #{corpNum} , #{emailAddr} , #{permmstrAddr2} , #{mngemstrNm} , #{permmstrNewAddr1} , #{permmstrNewAddr2} , #{mngemstrNewZip} , #{mngemstrRefInfo} , #{permmstrPhonnum} , #{branCd} , #{upsoZip} )";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_ky_INS6\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_DEL27() {
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" where UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<delete id=\"SQLupso_regist_ky_DEL27\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_DEL34() {
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" where UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<delete id=\"SQLupso_regist_ky_DEL34\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_UPD28() {
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MNGEMSTR_HPNUM=#{mngemstrHpnum} , OPBI_DAY=#{opbiDay} , BILL_ISS_YN=#{billIssYn} , MNGEMSTR_ADDR1=#{mngemstrAddr1} , UPSO_ADDR1=#{upsoAddr1} , STAFF_CD=#{staffCd} , UPSO_STAT=#{upsoStat} , MNGEMSTR_PHONNUM=#{mngemstrPhonnum} , MNGEMSTR_RESINUM=#{mngemstrResinum} , MNGEMSTR_ZIP=#{mngemstrZip} , AREA=#{area} , UPSO_NM=#{upsoNm} , PERMMSTR_NM=#{permmstrNm} , PERMMSTR_RESINUM=#{permmstrResinum} , PAPER_CANC_YN=#{paperCancYn} , MCHNDAESU=#{mchndaesu} , UPSO_ADDR2=#{upsoAddr2} , UPSO_REMAK=#{upsoRemak} ,  \n";
        query +=" CORP_NUM=#{corpNum} , EMAIL_ADDR=#{emailAddr} , MNGEMSTR_NM=#{mngemstrNm} , MNGEMSTR_ADDR2=#{mngemstrAddr2} , BSCON_CD=#{bsconCd} , MNG_ZIP=#{mngZip} , NEW_DAY=#{newDay} , MAIL_RCPT=#{mailRcpt} , PERMMSTR_PHONNUM=#{permmstrPhonnum} , BIOWN_NUM=#{biownNum} , BRAN_CD=#{branCd} , UPSO_ZIP=#{upsoZip}  \n";
        query +=" where UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_ky_UPD28\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_SEL29() {
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(CHG_NUM),  0)  +  1,  4,  '0')  CHG_NUM  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  CHG_BRAN  =  #{branCd}   \n";
        query +=" AND  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  CHG_DAY  =  TO_CHAR(SYSDATE,'YYYYMMDD') ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_ky_SEL29\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_INS62() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSORTAL_INFO (JOIN_SEQ, APPL_DAY, CHG_BRAN, CHG_DAY, INSPRES_ID, UPSO_GRAD, MCHNDAESU, MONPRNCFEE, BSTYP_CD, INS_DATE, MONPRNCFEE2, CHG_NUM, UPSO_CD)  \n";
        query +=" values((SELECT NVL(MAX(JOIN_SEQ), 0) + 1 JOIN_SEQ FROM GIBU.TBRA_UPSORTAL_INFO ), #{applDay} , #{chgBran} , TO_CHAR(SYSDATE,'YYYYMMDD'), #{inspresId} , #{upsoGrad} , #{mchndaesu} , #{monprncfee} , #{bstypCd} , SYSDATE, #{monprncfee2} , #{chgNum} , #{upsoCd} )";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_ky_INS62\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_INS30() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSORTAL_INFO (JOIN_SEQ, APPL_DAY, CHG_BRAN, CHG_DAY, INSPRES_ID, UPSO_GRAD, USE_TIME, MCHNDAESU, MONPRNCFEE, BSTYP_CD, INS_DATE, CHG_NUM, UPSO_CD)  \n";
        query +=" values((SELECT NVL(MAX(JOIN_SEQ), 0) + 1 JOIN_SEQ FROM GIBU.TBRA_UPSORTAL_INFO ), DECODE(#{applDay1},NULL,TO_CHAR(SYSDATE,'YYYYMMDD'),#{applDay2}), #{chgBran} , TO_CHAR(SYSDATE,'YYYYMMDD'), #{inspresId} , #{upsoGrad} , #{useTime} , #{mchndaesu} , #{monprncfee} , #{bstypCd} , SYSDATE, #{chgNum} , #{upsoCd} )";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_ky_INS30\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_INS63() {
        String    query="";
        query +=" Insert into GIBU.TBRA_NOREBANG_INFO (INS_DATE, CHG_BRAN, INSPRES_ID, CHG_NUM, CHG_DAY, MCHNDAESU, AREA, UPSO_CD, BSTYP_CD, GRAD_GBN, GRAD_NUM)  \n";
        query +=" values(SYSDATE, #{chgBran} , #{inspresId} , #{chgNum} , TO_CHAR(SYSDATE,'YYYYMMDD'), #{mchndaesu} , #{area} , #{upsoCd} , #{bstypCd} , #{gradGbn} , (SELECT NVL(MAX(GRAD_NUM), 0) + 1 GRAD_NUM FROM GIBU.TBRA_NOREBANG_INFO WHERE UPSO_CD = #{gradNum1}))";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_ky_INS63\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_INS32() {
        String    query="";
        query +=" Insert into GIBU.TBRA_NOREBANG_INFO (INS_DATE, CHG_BRAN, INSPRES_ID, CHG_NUM, CHG_DAY, STNDAMT, MCHNDAESU, AREA, UPSO_CD, BSTYP_CD, GRAD_GBN, GRAD_NUM)  \n";
        query +=" values(SYSDATE, #{chgBran} , #{inspresId} , #{chgNum} , TO_CHAR(SYSDATE,'YYYYMMDD'), #{stndamt} , #{mchndaesu} , #{area} , #{upsoCd} , #{bstypCd} , #{gradGbn} , (SELECT NVL(MAX(GRAD_NUM), 0) + 1 GRAD_NUM FROM GIBU.TBRA_NOREBANG_INFO WHERE UPSO_CD = #{upsoCd}))";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_ky_INS32\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_XIUD65() {
        String    query="";
        query +=" COMMIT ";
        String xml ="";
            xml += "<commit id=\"SQLupso_regist_ky_XIUD65\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</commit>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_UPD14() {
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MNGEMSTR_HPNUM=#{mngemstrHpnum} , MODPRES_ID=#{modpresId} , PAYPRES_GBN=#{paypresGbn} , PERMMSTR_NEW_ZIP1=#{permmstrNewZip1} , OPBI_DAY=#{opbiDay} , UPSO_NEW_ADDR1=#{upsoNewAddr1} , MNGEMSTR_NEW_ZIP1=#{mngemstrNewZip1} , PERMMSTR_HPNUM=#{permmstrHpnum} , UPSO_STAT=#{upsoStat} , AREA=#{area} , PERMMSTR_REMAK=#{permmstrRemak} , PERMMSTR_NM=#{permmstrNm} , PERMMSTR_RESINUM=#{permmstrResinum} , MNGEMSTR_NEW_ADDR2=#{mngemstrNewAddr2} , PERMMSTR_BD_MNG_NUM=#{permmstrBdMngNum} , UPSO_REMAK=#{upsoRemak} , BSCON_CD=#{bsconCd} , MNGEMSTR_NEW_ADDR1=#{mngemstrNewAddr1} , NEW_DAY=#{newDay} , MNG_ZIP=#{mngZip} , MAIL_RCPT=#{mailRcpt} , MOD_DATE=SYSDATE , MNGEMSTR_BD_MNG_NUM=#{mngemstrBdMngNum} , BIOWN_NUM=#{biownNum} , UPSO_BD_MNG_NUM=#{upsoBdMngNum} , UPSO_NEW_ZIP1=#{upsoNewZip1} , UPSO_PHON=#{upsoPhon} , BILL_ISS_YN=#{billIssYn} , PERMMSTR_NEW_ZIP=#{permmstrNewZip} , UPSO_REF_INFO=#{upsoRefInfo} , STAFF_CD=#{staffCd} , MNGEMSTR_RESINUM=#{mngemstrResinum} , MNGEMSTR_PHONNUM=#{mngemstrPhonnum} , UPSO_NM=#{upsoNm} , UPSO_NEW_ZIP=#{upsoNewZip} , PAPER_CANC_YN=#{paperCancYn} , UPSO_NEW_ADDR2=#{upsoNewAddr2} , PERMMSTR_REF_INFO=#{permmstrRefInfo} , MCHNDAESU=#{mchndaesu} , MNGEMSTR_REMAK=#{mngemstrRemak} ,  \n";
        query +=" CORP_NUM=#{corpNum} , EMAIL_ADDR=#{emailAddr} , MNGEMSTR_NM=#{mngemstrNm} , PERMMSTR_NEW_ADDR1=#{permmstrNewAddr1} , PERMMSTR_NEW_ADDR2=#{permmstrNewAddr2} , MNGEMSTR_NEW_ZIP=#{mngemstrNewZip} , MNGEMSTR_REF_INFO=#{mngemstrRefInfo} , PERMMSTR_PHONNUM=#{permmstrPhonnum}  \n";
        query +=" where UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_ky_UPD14\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_SEL35() {
        String    query="";
        query +=" SELECT  UPSO_CD  ,  CHG_DAY  ,  CHG_NUM  ,  CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  JOIN_SEQ  =  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  NVL(APPL_DAY,  CHG_DAY)  <=  TO_CHAR(SYSDATE,  'YYYYMMDD')  ) ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_ky_SEL35\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_DEL113() {
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" where CHG_NUM=#{chgNum}  \n";
        query +=" and UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<delete id=\"SQLupso_regist_ky_DEL113\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_UPD34() {
        String    query="";
        query +=" Update  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" set MCHNDAESU=#{mchndaesu}  \n";
        query +=" where CHG_BRAN=#{chgBran}  \n";
        query +=" and CHG_NUM=#{chgNum}  \n";
        query +=" and CHG_DAY=#{chgDay}  \n";
        query +=" and UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_ky_UPD34\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_DEL114() {
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" where CHG_NUM=#{chgNum}  \n";
        query +=" and UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<delete id=\"SQLupso_regist_ky_DEL114\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_XIUD55() {
        String    query="";
        query +=" COMMIT ";
        String xml ="";
            xml += "<commit id=\"SQLupso_regist_ky_XIUD55\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</commit>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_SEL36() {
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  VISIT_DAY  =  TO_CHAR(SYSDATE,'YYYYMMDD')   \n";
        query +=" AND  JOB_GBN  =  #{jobGbn} ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_ky_SEL36\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_INS21() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, VISIT_TIME, INSPRES_ID, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(#{visitSeq} , SYSDATE, TO_CHAR(SYSDATE, 'HH24MI'), #{inspresId} , TO_CHAR(SYSDATE,'YYYYMMDD'), #{jobGbn} , #{upsoCd} , #{conspres} , #{remak} )";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_ky_INS21\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_XIUD41() {
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_ONOFF_INFO ( UPSO_CD , MODEL_CD , ONOFF_GBN , ACMCN_DAESU , INSPRES_ID , INS_DATE ) SELECT #{upsoCd} , MODEL_CD , ONOFF_GBN , ACMCN_DAESU , #{inspresId} , SYSDATE FROM GIBU.TBRA_UPSO_ONOFF_INFO WHERE UPSO_CD = #{beforeUpsoCd}";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_ky_XIUD41\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_INS39() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(#{visitSeq} , SYSDATE, #{inspresId} , TO_CHAR(SYSDATE,'YYYYMMDD'), #{jobGbn} , (SELECT NVL(MAX(VISIT_NUM),0)+1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = TO_CHAR(SYSDATE,'YYYYMMDD') AND UPSO_CD = #{visitNum1} AND JOB_GBN = 'U' ), #{upsoCd} , #{remak} )";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_ky_INS39\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_XIUD42() {
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_ACMCN_INFO ( UPSO_CD , MODEL_CD , ACMCN_DAESU , INSPRES_ID , INS_DATE ) SELECT #{upsoCd} , MODEL_CD , ACMCN_DAESU , #{inspresId} , SYSDATE FROM GIBU.TBRA_UPSO_ACMCN_INFO WHERE UPSO_CD = #{beforeUpsoCd}";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_ky_XIUD42\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_INS38() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(#{visitSeq} , SYSDATE, #{inspresId} , TO_CHAR(SYSDATE,'YYYYMMDD'), #{jobGbn} , (SELECT NVL(MAX(VISIT_NUM),0)+1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = TO_CHAR(SYSDATE,'YYYYMMDD') AND UPSO_CD = #{visitNum1} AND JOB_GBN = 'U' ), #{upsoCd} , #{remak} )";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_ky_INS38\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_XIUD43() {
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_AUBRY_INFO ( UPSO_CD , MODEL_CD , MODEL_NM , MCHN_COMPY , MCHN_COMPYNM , ACMCN_DAESU , INSPRES_ID , INS_DATE ) SELECT #{upsoCd} , MODEL_CD , MODEL_NM , MCHN_COMPY , MCHN_COMPYNM , ACMCN_DAESU , #{inspresId} , SYSDATE FROM GIBU.TBRA_UPSO_AUBRY_INFO WHERE UPSO_CD = #{beforeUpsoCd}";
        String xml ="";
            xml += "<insert id=\"SQLupso_regist_ky_XIUD43\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_XIUD50() {
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET ONOFF_GBN = ( SELECT ONOFF_GBN FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE UPSO_CD =  \n";
        query +=" #{beforeUpsoCd})  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_ky_XIUD50\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_UPD59() {
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO  \n";
        query +=" set GBN=#{gbn} , EMAIL=#{email}  \n";
        query +=" where UPSO_CD=#{upsoCd}  \n";
        query +=" and AUTO_NUM=#{autoNum}";
        String xml ="";
            xml += "<update id=\"SQLupso_regist_ky_UPD59\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_SEL17() {
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.UPSO_NM  ,  A.BIOWN_NUM  ,  A.UPSO_PHON  ,  A.UPSO_ZIP  ,  A.UPSO_ADDR1  ,  A.UPSO_ADDR2  ,  A.MNGEMSTR_NM  ,  A.MNGEMSTR_PHONNUM  ,  A.MNGEMSTR_RESINUM  ,  A.MNGEMSTR_HPNUM  ,  A.MNGEMSTR_ZIP  ,  A.MNGEMSTR_ADDR1  ,  A.MNGEMSTR_ADDR2  ,  A.MNGEMSTR_REMAK  ,  A.PERMMSTR_NM  ,  A.PERMMSTR_PHONNUM  ,  A.PERMMSTR_RESINUM  ,  A.PERMMSTR_HPNUM  ,  A.PERMMSTR_ZIP  ,  A.PERMMSTR_ADDR1  ,  A.PERMMSTR_ADDR2  ,  A.PERMMSTR_REMAK  ,  A.OPBI_DAY  ,  A.PAYPRES_GBN  ,  A.NEW_DAY  ,  A.MAIL_RCPT  ,  A.PAPER_CANC_YN  ,  A.STAFF_CD  ,  D.HAN_NM  STAFF_NM  ,  A.UPSO_STAT  ,  A.BEFORE_UPSO_CD  ,  TRIM(B.BSTYP_CD)  ||  B.UPSO_GRAD  GRAD  ,  TRIM(B.BSTYP_CD)  BSTYP_CD  ,  B.UPSO_GRAD  ,  B.MONPRNCFEE  ,  B.USE_TIME  ,  TRIM(B.B_BSTYP_CD)  ||  B.B_UPSO_GRAD  B_GRAD  ,  B.B_BSTYP_CD  ,  B.B_UPSO_GRAD  ,  B.B_MONPRNCFEE  ,  B.B_USE_TIME  ,  B.GRADNM  ,  B.B_GRADNM  ,  B.CHG_DAY  ,  B.CHG_NUM  ,  B.CHG_BRAN  ,  B.B_CHG_DAY  ,  B.B_CHG_NUM  ,  B.B_CHG_BRAN  ,  TO_CHAR(A.INS_DATE,'YYYYMMDD')  INS_DATE  ,  B.B_UPSO_NM  ,  C.MCHNDAESU  ,  C.B_MCHNDAESU  ,  DECODE(A.CLSBS_YRMN,  NULL,  A.CLSBS_YRMN,  A.CLSBS_YRMN  ||  '01')  CLSBS_YRMN  ,  A.CLIENT_NUM  ,  A.BSCON_CD  ,  E.BSCONHAN_NM  ,  A.BILL_ISS_YN  ,  A.UPSO_REMAK  ,  A.BRAN_CD  ,  A.MNG_ZIP  ,  A.CORP_NUM  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  MAX(DECODE(DUMMY,  '1',  UPSO_CD  ))  UPSO_CD  ,  MAX(DECODE(DUMMY,  '1',  UPSO_GRAD  ))  UPSO_GRAD  ,  MAX(DECODE(DUMMY,  '1',  MONPRNCFEE))  MONPRNCFEE  ,  MAX(DECODE(DUMMY,  '1',  USE_TIME  ))  USE_TIME  ,  MAX(DECODE(DUMMY,  '1',  BSTYP_CD  ))  BSTYP_CD  ,  MAX(DECODE(DUMMY,  '1',  GRADNM  ))  GRADNM  ,  MAX(DECODE(DUMMY,  '1',  CHG_DAY))  CHG_DAY  ,  MAX(DECODE(DUMMY,  '1',  CHG_NUM))  CHG_NUM  ,  MAX(DECODE(DUMMY,  '1',  CHG_BRAN  ))  CHG_BRAN  ,  MAX(DECODE(DUMMY,  '2',  UPSO_CD  ))  B_UPSO_CD  ,  MAX(DECODE(DUMMY,  '2',  UPSO_GRAD  ))  B_UPSO_GRAD  ,  MAX(DECODE(DUMMY,  '2',  MONPRNCFEE))  B_MONPRNCFEE  ,  MAX(DECODE(DUMMY,  '2',  USE_TIME  ))  B_USE_TIME  ,  MAX(DECODE(DUMMY,  '2',  BSTYP_CD  ))  B_BSTYP_CD  ,  MAX(DECODE(DUMMY,  '2',  GRADNM  ))  B_GRADNM  ,  MAX(DECODE(DUMMY,  '2',  UPSO_NM  ))  B_UPSO_NM  ,  MAX(DECODE(DUMMY,  '2',  CHG_DAY))  B_CHG_DAY  ,  MAX(DECODE(DUMMY,  '2',  CHG_NUM))  B_CHG_NUM  ,  MAX(DECODE(DUMMY,  '2',  CHG_BRAN  ))  B_CHG_BRAN  FROM  (   \n";
        query +=" SELECT  *  FROM  (   \n";
        query +=" SELECT  '1'  DUMMY  ,  A.UPSO_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.USE_TIME  ,  A.BSTYP_CD  ,  ''  UPSO_NM  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  B.BSTYP_CD  =  A.BSTYP_CD   \n";
        query +=" AND  B.GRAD_GBN  =  A.UPSO_GRAD  ORDER  BY  A.CHG_DAY  DESC  ,A.CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  UNION  ALL   \n";
        query +=" SELECT  *  FROM  (   \n";
        query +=" SELECT  '2'  DUMMY  ,  A.UPSO_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.USE_TIME  ,  A.BSTYP_CD  ,  C.UPSO_NM  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  (   \n";
        query +=" SELECT  BEFORE_UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  #{upsoCd})   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  B.BSTYP_CD  =  A.BSTYP_CD   \n";
        query +=" AND  B.GRAD_GBN  =  A.UPSO_GRAD  ORDER  BY  A.CHG_DAY  DESC  ,A.CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  )  )  B  ,  (   \n";
        query +=" SELECT  MAX(MCHNDAESU)  MCHNDAESU  ,  MAX(B_MCHNDAESU)  B_MCHNDAESU  ,  MAX(UPSO_CD)  UPSO_CD  FROM  (   \n";
        query +=" SELECT  MCHNDAESU  MCHNDAESU  ,  NULL  B_MCHNDAESU  ,  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  #{upsoCd}  UNION  ALL   \n";
        query +=" SELECT  NULL  ,  MCHNDAESU  B_MCHNDAESU  ,  NULL  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  (   \n";
        query +=" SELECT  BEFORE_UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  #{upsoCd}  )  )  )  C  ,  INSA.TINS_MST01  D  ,  FIDU.TLEV_BSCON  E  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  B.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  C.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  D.STAFF_NUM  (+)  =  A.STAFF_CD   \n";
        query +=" AND  E.BSCON_CD(+)  =  A.BSCON_CD ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_ky_SEL17\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_regist_ky_SEL24() {
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(A.BSTYP_CD)  ||  A.GRAD_GBN  GRAD  ,  A.AREA  ,  A.MCHNDAESU  ,  B.STNDAMT  ,  B.GRADNM  ,  A.MCHNDAESU  *  B.STNDAMT  AMT  FROM  GIBU.TBRA_NOREBANG_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  C.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  A.GRAD_GBN  =  B.GRAD_GBN  AND	A.CHG_DAY  =  #{chgDay}  AND	A.CHG_NUM  =  #{chgNum}  AND	A.CHG_BRAN  =  #{chgBran} ";
        String xml ="";
            xml += "<select id=\"SQLupso_regist_ky_SEL24\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "C:\\komca\\workspace\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLonoff_check_list_SEL1() + "\n";
        content += SQLonoff_check_list_SEL2() + "\n";
        content += SQLonoff_check_list_SEL5() + "\n";
        content += SQLonoff_check_list_SEL3() + "\n";
        content += SQLnorebang_info_SEL1() + "\n";
        content += SQLcheck_chgowner_SEL1() + "\n";
        content += SQLaddr_chk_SEL1() + "\n";
        content += SQLupso_regist_onoff_SEL44() + "\n";
        content += SQLupso_regist_onoff_SEL25() + "\n";
        content += SQLupso_regist_onoff_UPD44() + "\n";
        content += SQLupso_regist_onoff_INS6() + "\n";
        content += SQLupso_regist_onoff_DEL27() + "\n";
        content += SQLupso_regist_onoff_DEL34() + "\n";
        content += SQLupso_regist_onoff_UPD28() + "\n";
        content += SQLupso_regist_onoff_SEL29() + "\n";
        content += SQLupso_regist_onoff_INS62() + "\n";
        content += SQLupso_regist_onoff_INS30() + "\n";
        content += SQLupso_regist_onoff_INS63() + "\n";
        content += SQLupso_regist_onoff_INS32() + "\n";
        content += SQLupso_regist_onoff_UPD14() + "\n";
        content += SQLupso_regist_onoff_XIUD65() + "\n";
        content += SQLupso_regist_onoff_SEL35() + "\n";
        content += SQLupso_regist_onoff_UPD34() + "\n";
        content += SQLupso_regist_onoff_INS55() + "\n";
        content += SQLupso_regist_onoff_SEL56() + "\n";
        content += SQLupso_regist_onoff_SEL36() + "\n";
        content += SQLupso_regist_onoff_UPD60() + "\n";
        content += SQLupso_regist_onoff_INS21() + "\n";
        content += SQLupso_regist_onoff_DEL113() + "\n";
        content += SQLupso_regist_onoff_INS39() + "\n";
        content += SQLupso_regist_onoff_DEL114() + "\n";
        content += SQLupso_regist_onoff_INS38() + "\n";
        content += SQLupso_regist_onoff_XIUD55() + "\n";
        content += SQLupso_regist_onoff_XIUD41() + "\n";
        content += SQLupso_regist_onoff_XIUD42() + "\n";
        content += SQLupso_regist_onoff_XIUD43() + "\n";
        content += SQLupso_regist_onoff_XIUD50() + "\n";
        content += SQLupso_regist_onoff_XIUD68() + "\n";
        content += SQLupso_regist_onoff_SEL66() + "\n";
        content += SQLupso_regist_onoff_XIUD69() + "\n";
        content += SQLupso_regist_onoff_XIUD66() + "\n";
        content += SQLupso_regist_onoff_XIUD70() + "\n";
        content += SQLupso_regist_onoff_SEL79() + "\n";
        content += SQLupso_regist_onoff_XIUD84() + "\n";
        content += SQLupso_regist_onoff_XIUD81() + "\n";
        content += SQLupso_regist_onoff_UPD63() + "\n";
        content += SQLupso_regist_onoff_UPD62() + "\n";
        content += SQLupso_regist_onoff_UPD59() + "\n";
        content += SQLupso_regist_onoff_SEL17() + "\n";
        content += SQLupso_regist_onoff_SEL24() + "\n";
        content += SQLupso_regist_onoff_XIUD80() + "\n";
        content += SQLupso_regist_ky_SEL44() + "\n";
        content += SQLupso_regist_ky_SEL25() + "\n";
        content += SQLupso_regist_ky_UPD44() + "\n";
        content += SQLupso_regist_ky_INS6() + "\n";
        content += SQLupso_regist_ky_DEL27() + "\n";
        content += SQLupso_regist_ky_DEL34() + "\n";
        content += SQLupso_regist_ky_UPD28() + "\n";
        content += SQLupso_regist_ky_SEL29() + "\n";
        content += SQLupso_regist_ky_INS62() + "\n";
        content += SQLupso_regist_ky_INS30() + "\n";
        content += SQLupso_regist_ky_INS63() + "\n";
        content += SQLupso_regist_ky_INS32() + "\n";
        content += SQLupso_regist_ky_XIUD65() + "\n";
        content += SQLupso_regist_ky_UPD14() + "\n";
        content += SQLupso_regist_ky_SEL35() + "\n";
        content += SQLupso_regist_ky_DEL113() + "\n";
        content += SQLupso_regist_ky_UPD34() + "\n";
        content += SQLupso_regist_ky_DEL114() + "\n";
        content += SQLupso_regist_ky_XIUD55() + "\n";
        content += SQLupso_regist_ky_SEL36() + "\n";
        content += SQLupso_regist_ky_INS21() + "\n";
        content += SQLupso_regist_ky_XIUD41() + "\n";
        content += SQLupso_regist_ky_INS39() + "\n";
        content += SQLupso_regist_ky_XIUD42() + "\n";
        content += SQLupso_regist_ky_INS38() + "\n";
        content += SQLupso_regist_ky_XIUD43() + "\n";
        content += SQLupso_regist_ky_XIUD50() + "\n";
        content += SQLupso_regist_ky_UPD59() + "\n";
        content += SQLupso_regist_ky_SEL17() + "\n";
        content += SQLupso_regist_ky_SEL24() + "\n";
        content += bottom;

        try {
            String fileName = "bra01_s01_1.xml";
            FileWriter fw = new FileWriter(dir + "bra01_s01.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

