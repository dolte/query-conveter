package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra04_r21_Generated {

    public static String SQLcol_upso_list_SEL1() {
        String    query="";
        query +=" SELECT  AA.UPSO_CD  ,  CC.GRADNM  ,  CC.MONPRNCFEE  ,  BB.UPSO_NM  ,  BB.MNGEMSTR_NM  ,  BB.UPSO_ADDR  ,  BB.UPSO_PHON  ,  AA.START_YRMN||'~'||AA.END_YRMN  YRMN  ,  NVL(AA.REPT_AMT,0)  -  NVL(AA.COMIS,0)  REPT_AMT  ,  BB.STAFF_NM  ,  DECODE(AA.ACCU_GBN,  NULL,  '',  '고소입금')  REMAK  ,  DECODE(AA.ACCU_GBN,  NULL,  0,  1)  CNT  FROM  (   \n";
        query +=" SELECT  TA.REPT_DAY  ,  TA.REPT_NUM  ,  TA.REPT_GBN  ,  '-'  DISTR_SEQ  ,  TA.REPT_AMT  ,  TA.COMIS  ,  TA.UPSO_CD  ,  TB.START_YRMN  ,  TB.END_YRMN  ,  TB.ACCU_GBN  FROM  GIBU.TBRA_REPT  TA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  ACCU_GBN  ,  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  BRAN_CD  =  #{branCd}   \n";
        query +=" AND  MAPPING_DAY  LIKE  #{yrmn}||'%'  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  ACCU_GBN  )  TB  WHERE  TA.MAPPING_DAY  LIKE  #{yrmn}||'%'   \n";
        query +=" AND  TA.DISTR_GBN  IS  NULL   \n";
        query +=" AND  TA.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  TB.UPSO_CD  (+)  =  TA.UPSO_CD   \n";
        query +=" AND  TB.REPT_DAY(+)  =  TA.REPT_DAY   \n";
        query +=" AND  TB.REPT_NUM(+)  =  TA.REPT_NUM   \n";
        query +=" AND  TB.REPT_GBN(+)  =  TA.REPT_GBN  UNION  ALL   \n";
        query +=" SELECT  TA.REPT_DAY  ,  TA.REPT_NUM  ,  TA.REPT_GBN  ,  TA.DISTR_SEQ  ,  TA.DISTR_AMT  ,  0  ,  TA.UPSO_CD  ,  TB.START_YRMN  ,  TB.END_YRMN  ,  TB.ACCU_GBN  FROM  GIBU.TBRA_REPT_UPSO_DISTR  TA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  ,  ACCU_GBN  ,  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  BRAN_CD  =  #{branCd}   \n";
        query +=" AND  MAPPING_DAY  LIKE  #{yrmn}||'%'   \n";
        query +=" AND  DISTR_SEQ  IS  NOT  NULL  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ,  ACCU_GBN  )  TB  WHERE  TA.MAPPING_DAY  LIKE  #{yrmn}||'%'   \n";
        query +=" AND  TA.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  TB.UPSO_CD  (+)  =  TA.UPSO_CD   \n";
        query +=" AND  TB.REPT_DAY  (+)  =  TA.REPT_DAY   \n";
        query +=" AND  TB.REPT_NUM  (+)  =  TA.REPT_NUM   \n";
        query +=" AND  TB.REPT_GBN  (+)  =  TA.REPT_GBN   \n";
        query +=" AND  TB.DISTR_SEQ(+)  =  TA.DISTR_SEQ  )  AA  ,  (   \n";
        query +=" SELECT  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.MNGEMSTR_NM  ,  TA.STAFF_CD  ,  TB.HAN_NM  STAFF_NM  ,  TA.UPSO_NEW_ADDR1  ||  DECODE(TA.UPSO_NEW_ADDR2,  '',  '',  ',  '||TA.UPSO_NEW_ADDR2)  ||  TA.UPSO_REF_INFO  UPSO_ADDR  ,  TA.NEW_DAY  ,  TA.UPSO_PHON  FROM  GIBU.TBRA_UPSO  TA  ,  INSA.TINS_MST01  TB  WHERE  TB.STAFF_NUM(+)  =  TA.STAFF_CD  )  BB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  #{yrmn}||'31'   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.UPSO_CD  =  AA.UPSO_CD  ORDER  BY  UPSO_CD ";
        String xml ="";
            xml += "<select id=\"SQLcol_upso_list_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLcol_upso_list_SEL4() {
        String    query="";
        query +=" SELECT  AA.UPSO_CD  ,  CC.GRADNM  ,  CC.MONPRNCFEE  ,  BB.UPSO_NM  ,  BB.MNGEMSTR_NM  ,  BB.UPSO_ADDR  ,  BB.UPSO_PHON  ,  BB.START_YRMN||'~'||BB.END_YRMN  YRMN  ,  NVL(AA.REPT_AMT,0)  -  NVL(AA.COMIS,0)  REPT_AMT  ,  BB.STAFF_NM  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_AMT  ,  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  #{yrmn}||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  #{branCd}  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  DISTR_AMT  ,  0  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  #{yrmn}||'%'   \n";
        query +=" AND  BRAN_CD  =  #{branCd}  )  AA  ,  (   \n";
        query +=" SELECT  TA.UPSO_CD  ,  TB.UPSO_NM  ,  TB.MNGEMSTR_NM  ,  TB.STAFF_CD  ,  TC.HAN_NM  STAFF_NM  ,  TB.UPSO_NEW_ADDR1  ||  DECODE(TB.UPSO_NEW_ADDR2,  '',  '',  ',  '||TB.UPSO_NEW_ADDR2)  ||  TB.UPSO_REF_INFO  UPSO_ADDR  ,  TA.START_YRMN  ,  TA.END_YRMN  ,  TB.NEW_DAY  ,  TB.UPSO_PHON  FROM  (   \n";
        query +=" SELECT  ZB.UPSO_CD  ,  MIN(ZA.NOTE_YRMN)  START_YRMN  ,  MAX(ZA.NOTE_YRMN)  END_YRMN  FROM  GIBU.TBRA_NOTE  ZA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  #{yrmn}||'%'   \n";
        query +=" AND  BRAN_CD  =  #{branCd}  UNION   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  #{yrmn}||'%'   \n";
        query +=" AND  BRAN_CD  =  #{branCd}  )  ZB  WHERE  ZA.UPSO_CD(+)  =  ZB.UPSO_CD   \n";
        query +=" AND  ZA.REPT_DAY(+)  =  ZB.REPT_DAY   \n";
        query +=" AND  ZA.REPT_NUM(+)  =  ZB.REPT_NUM   \n";
        query +=" AND  ZA.REPT_GBN(+)  =  ZB.REPT_GBN  GROUP  BY  ZB.UPSO_CD  )  TA  ,  GIBU.TBRA_UPSO  TB  ,  INSA.TINS_MST01  TC  WHERE  TA.UPSO_CD  =  TB.UPSO_CD   \n";
        query +=" AND  TC.STAFF_NUM(+)  =  TB.STAFF_CD  )  BB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  (BB.NEW_DAY  >  #{yrmn}||'32'   \n";
        query +=" OR  BB.NEW_DAY  IS  NULL)   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.UPSO_CD  =  AA.UPSO_CD  ORDER  BY  UPSO_CD ";
        String xml ="";
            xml += "<select id=\"SQLcol_upso_list_SEL4\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "D:\\kosmos\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLcol_upso_list_SEL1() + "\n";
        content += SQLcol_upso_list_SEL4() + "\n";
        content += bottom;

        try {
            String fileName = "bra01_s01_1.xml";
            FileWriter fw = new FileWriter(dir + "bra04_r21.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

