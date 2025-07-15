package query.conveter;

import java.io.*;
import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra04_r22_Generated {

    public static String SQLselect_tmp_account_SEL1() {
        String    query="";
        query +=" SELECT  XA.REPT_DAY  ,  XA.REPT_NUM  ,  XA.REPT_GBN  ,  XC.CODE_NM  ,  XA.BRAN_CD  ,  XA.REPT_AMT  ,  XA.OVER_AMT  ,  DECODE(XA.REPT_GBN,  '03',   \n";
        query +=" (SELECT  REPTPRES  FROM  GIBU.TBRA_REPT_TRANS  WHERE  REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  XA.REPT_NUM)  ,'')  AS  REPTPRES  ,  DECODE(XA.REPT_GBN,  '03',   \n";
        query +=" (SELECT  RECEPT_BANK  FROM  GIBU.TBRA_REPT_TRANS  WHERE  REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  XA.REPT_NUM)  ,'')  AS  TRANS_REMAK  ,  XB.BANK_NM  ,  XA.BANK_CD  ,  XA.ACCN_NUM  ,  XA.REMAK  ,  XA.RECV_DAY  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  BRAN_CD  ,  REPT_AMT  -  COMIS  REPT_AMT  ,  REPT_AMT  -  COMIS  OVER_AMT  ,  REMAK  ,  BANK_CD  ,  ACCN_NUM  ,  RECV_DAY  FROM  GIBU.TBRA_REPT  WHERE  BRAN_CD  =  #{branCd}   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  UPSO_CD  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  BRAN_CD  ,  REPT_AMT  -  COMIS  REPT_AMT  ,  REPT_AMT  -  COMIS  OVER_AMT  ,  '지부분배  가수금  (미분배)'  REMAK  ,  BANK_CD  ,  ACCN_NUM  ,  RECV_DAY  FROM  GIBU.TBRA_REPT  WHERE  BRAN_CD  =  #{branCd}   \n";
        query +=" AND  DISTR_GBN  =  '41'   \n";
        query +=" AND  REPT_DAY  ||  REPT_NUM  ||  REPT_GBN  NOT  IN  (   \n";
        query +=" SELECT  REPT_DAY  ||  REPT_NUM  ||  REPT_GBN  FROM  GIBU.TBRA_REPT_DISTR  )  UNION  ALL   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  C.BRAN_CD  ,  C.REPT_AMT  -  NVL(C.COMIS,  0)  REPT_AMT  ,  C.REPT_AMT  -  NVL(C.COMIS,  0)-  NVL(B.REPT_AMT,0)  OVER_AMT  ,  '지부분배  가수금'  REMAK  ,  C.BANK_CD  ,  C.ACCN_NUM  ,  C.RECV_DAY  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  SUM(NVL(DISTR_AMT,  0))  REPT_AMT  FROM  GIBU.TBRA_REPT_DISTR  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN  )  A,  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  SUM(NVL(DISTR_AMT,  0))  REPT_AMT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN  )  B  ,  GIBU.TBRA_REPT  C  WHERE  C.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  B.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  =  A.REPT_GBN   \n";
        query +=" AND  C.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  C.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  C.REPT_GBN  =  A.REPT_GBN   \n";
        query +=" AND  A.REPT_AMT  -  NVL(B.REPT_AMT,  0)  >  0  UNION  ALL   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.BRAN_CD  ,  (A.REPT_AMT  -  A.COMIS)  REPT_AMT  ,  (A.REPT_AMT  -  A.COMIS)  -  NVL(B.REPT_AMT,0)  OVER_AMT  ,  '업소분배  가수금'  REMAK  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.RECV_DAY  FROM  GIBU.TBRA_REPT  A  ,  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  SUM(NVL(DISTR_AMT,  0))  REPT_AMT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  BRAN_CD  =  #{branCd}  GROUP  BY  REPT_DAY,  REPT_NUM  )  B  WHERE  A.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM(+)   \n";
        query +=" AND  A.DISTR_GBN  =  '42'   \n";
        query +=" AND  (A.REPT_AMT  -  A.COMIS)  -  NVL(B.REPT_AMT,  0)  >  0  )  XA  ,  ACCT.TCAC_BANK  XB  ,  FIDU.TENV_CODE  XC  WHERE  XB.BANK_CD  =  XA.BANK_CD   \n";
        query +=" AND  XC.HIGH_CD  =  '00141'   \n";
        query +=" AND  XC.CODE_CD  =  XA.REPT_GBN   \n";
        query +=" AND  XA.ACCN_NUM  =  DECODE(#{accnNum},  NULL,  XA.ACCN_NUM,  #{accnNum})   \n";
        query +=" AND  XA.REPT_AMT  <>  0  ORDER  BY  XA.REPT_DAY,  XA.REPT_NUM ";
        String xml ="";
            xml += "<select id=\"SQLselect_tmp_account_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
        xml = xml.replaceAll("#\\s*\\{\\s*([a-zA-Z0-9_]+)\\s*\\}", "#{$1}");
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLselect_tmp_account_mapping_SEL1() {
        String    query="";
        query +=" SELECT  XA.REPT_DAY  ,  XA.REPT_NUM  ,  XA.REPT_GBN  ,  XE.CODE_NM  ,  XA.MAPPING_DAY  ,  XA.BRAN_CD  ,  XA.UPSO_CD  ,  XB.UPSO_NM  ,  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  XA.REPT_AMT  ,  DECODE(XA.REPT_GBN,  '03',   \n";
        query +=" (SELECT  REPTPRES  FROM  GIBU.TBRA_REPT_TRANS  WHERE  REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  XA.REPT_NUM)  ,'')  AS  REPTPRES  ,  XD.BANK_NM  ,  XA.BANK_CD  ,  XA.ACCN_NUM  ,  XA.REMAK  ,  XA.RECV_DAY  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  MAPPING_DAY  ,  BRAN_CD  ,  UPSO_CD  ,  REPT_AMT  -  COMIS  REPT_AMT  ,  REMAK  ,  BANK_CD  ,  ACCN_NUM  ,  RECV_DAY  FROM  GIBU.TBRA_REPT  WHERE  BRAN_CD  =  #{branCd}   \n";
        query +=" AND  SUBSTR(REPT_DAY,  1,  6)  <  SUBSTR(MAPPING_DAY,  1,  6)   \n";
        query +=" AND  MAPPING_DAY  BETWEEN  #{reptYrmn}  ||  '01'   \n";
        query +=" AND  #{reptYrmn}  ||  '31'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  REPT_GBN  =  '03'  UNION  ALL   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.MAPPING_DAY  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  A.REPT_AMT  ,  A.REMAK  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  B.RECV_DAY  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  BRAN_CD  ,  UPSO_CD  ,  DISTR_AMT  REPT_AMT  ,  MAPPING_DAY  ,  REMAK  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  BRAN_CD  =  #{branCd}   \n";
        query +=" AND  MAPPING_DAY  BETWEEN  #{reptYrmn}  ||  '01'   \n";
        query +=" AND  #{reptYrmn}  ||  '31'  )  A  ,  GIBU.TBRA_REPT  B  WHERE  A.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  SUBSTR(A.REPT_DAY,  1,  6)  <  SUBSTR(A.MAPPING_DAY,  1,  6)   \n";
        query +=" AND  B.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  =  A.REPT_GBN   \n";
        query +=" AND  B.REPT_GBN  =  '03'  )  XA  ,  GIBU.TBRA_UPSO  XB  ,  GIBU.TBRA_NOTE  XC  ,  ACCT.TCAC_BANK  XD  ,  FIDU.TENV_CODE  XE  WHERE  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.REPT_DAY(+)  =  XA.REPT_DAY   \n";
        query +=" AND  XC.REPT_NUM(+)  =  XA.REPT_NUM   \n";
        query +=" AND  XC.REPT_GBN(+)  =  XA.REPT_GBN   \n";
        query +=" AND  XD.BANK_CD  =  XA.BANK_CD   \n";
        query +=" AND  XE.HIGH_CD  =  '00141'   \n";
        query +=" AND  XE.CODE_CD  =  XA.REPT_GBN  GROUP  BY  XA.REPT_DAY,  XA.REPT_NUM,  XA.REPT_GBN,  XE.CODE_NM,  XA.MAPPING_DAY,  XA.BRAN_CD,  XA.UPSO_CD,  XB.UPSO_NM,  XA.REPT_AMT,  XD.BANK_NM,  XA.BANK_CD,  XA.ACCN_NUM,  XA.REMAK,  XA.RECV_DAY  ORDER  BY  REPT_DAY  DESC,  REPT_NUM  DESC ";
        String xml ="";
            xml += "<select id=\"SQLselect_tmp_account_mapping_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
        xml = xml.replaceAll("#\\s*\\{\\s*([a-zA-Z0-9_]+)\\s*\\}", "#{$1}");
            xml += "\n</select>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "D:\\kosmos\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLselect_tmp_account_SEL1() + "\n";
        content += SQLselect_tmp_account_mapping_SEL1() + "\n";
        content += bottom;

        try {
            String fileName = "bra04_r22.xml";
            OutputStreamWriter fw = new OutputStreamWriter(
                new FileOutputStream(dir + fileName), "UTF-8"
            );
            fw.write('﻿'); // BOM 추가
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

