package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra01_s01_1_Generated {

    public static String SQLupsoinfo_search_SEL6() {
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.UPSO_NM  ,  A.BIOWN_NUM  ,  A.UPSO_PHON  ,  A.UPSO_NEW_ZIP  ,  A.UPSO_NEW_ZIP1  ,  A.UPSO_NEW_ADDR1  ,  A.UPSO_NEW_ADDR2  ,  A.UPSO_REF_INFO  ,  A.UPSO_BD_MNG_NUM  ,  A.UPSO_ZIP  ,  A.UPSO_ADDR1  ||  '  '  ||  A.UPSO_ADDR2  AS  UPSO_ADDR  ,  A.MNGEMSTR_NM  ,  A.MNGEMSTR_PHONNUM  ,  A.MNGEMSTR_RESINUM  ,  A.MNGEMSTR_HPNUM  ,  A.MNGEMSTR_NEW_ZIP  ,  A.MNGEMSTR_NEW_ZIP1  ,  A.MNGEMSTR_NEW_ADDR1  ,  A.MNGEMSTR_NEW_ADDR2  ,  A.MNGEMSTR_REF_INFO  ,  A.MNGEMSTR_BD_MNG_NUM  ,  A.MNGEMSTR_ZIP  ,  A.MNGEMSTR_ADDR1  ||  '  '  ||  A.MNGEMSTR_ADDR2  AS  MNGEMSTR_ADDR  ,  A.MNGEMSTR_REMAK  ,  A.PERMMSTR_NM  ,  A.PERMMSTR_PHONNUM  ,  A.PERMMSTR_RESINUM  ,  A.PERMMSTR_HPNUM  ,  A.PERMMSTR_NEW_ZIP  ,  A.PERMMSTR_NEW_ZIP1  ,  A.PERMMSTR_NEW_ADDR1  ,  A.PERMMSTR_NEW_ADDR2  ,  A.PERMMSTR_REF_INFO  ,  A.PERMMSTR_BD_MNG_NUM  ,  A.PERMMSTR_ZIP  ,  A.PERMMSTR_ADDR1  ||  '  '  ||  A.PERMMSTR_ADDR2  AS  PERMMSTR_ADDR  ,  A.PERMMSTR_REMAK  ,  A.OPBI_DAY  ,  A.PAYPRES_GBN  ,  A.NEW_DAY  ,  A.MAIL_RCPT  ,  A.PAPER_CANC_YN  ,  A.STAFF_CD  ,  D.HAN_NM  STAFF_NM  ,  '1'  UPSO_STAT  ,  A.BEFORE_UPSO_CD  ,  TRIM(B.BSTYP_CD)  ||  B.UPSO_GRAD  GRAD  ,  TRIM(B.BSTYP_CD)  BSTYP_CD  ,  B.UPSO_GRAD  ,  B.MONPRNCFEE  ,  B.USE_TIME  ,  TRIM(B.B_BSTYP_CD)  ||  B.B_UPSO_GRAD  B_GRAD  ,  B.B_BSTYP_CD  ,  B.B_UPSO_GRAD  ,  B.B_MONPRNCFEE  ,  B.B_USE_TIME  ,  B.GRADNM  ,  B.B_GRADNM  ,  B.CHG_DAY  ,  B.CHG_NUM  ,  B.CHG_BRAN  ,  B.B_CHG_DAY  ,  B.B_CHG_NUM  ,  B.B_CHG_BRAN  ,  TO_CHAR(A.INS_DATE,'YYYYMMDD')  INS_DATE  ,  B.B_UPSO_NM  ,  C.MCHNDAESU  ,  C.B_MCHNDAESU  ,  DECODE(A.CLSBS_YRMN,  NULL,  A.CLSBS_YRMN,  A.CLSBS_YRMN  ||  '01')  CLSBS_YRMN  ,  A.CLIENT_NUM  ,  A.BSCON_CD  ,  E.BSCONHAN_NM  ,  A.BILL_ISS_YN  ,  A.UPSO_REMAK  ,  A.BRAN_CD  ,  A.MNG_ZIP  ,  A.CORP_NUM  ,  A.EMAIL_ADDR  ,  A.AREA  ,  A.SONG_STUDENT  ,  A.AERO_STUDENT  ,  A.CONTR_NM  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  MAX(DECODE(DUMMY,  '1',  UPSO_CD  ))  UPSO_CD  ,  MAX(DECODE(DUMMY,  '1',  UPSO_GRAD  ))  UPSO_GRAD  ,  MAX(DECODE(DUMMY,  '1',  MONPRNCFEE))  MONPRNCFEE  ,  MAX(DECODE(DUMMY,  '1',  USE_TIME  ))  USE_TIME  ,  MAX(DECODE(DUMMY,  '1',  BSTYP_CD  ))  BSTYP_CD  ,  MAX(DECODE(DUMMY,  '1',  GRADNM  ))  GRADNM  ,  MAX(DECODE(DUMMY,  '1',  CHG_DAY))  CHG_DAY  ,  MAX(DECODE(DUMMY,  '1',  CHG_NUM))  CHG_NUM  ,  MAX(DECODE(DUMMY,  '1',  CHG_BRAN  ))  CHG_BRAN  ,  MAX(DECODE(DUMMY,  '2',  UPSO_CD  ))  B_UPSO_CD  ,  MAX(DECODE(DUMMY,  '2',  UPSO_GRAD  ))  B_UPSO_GRAD  ,  MAX(DECODE(DUMMY,  '2',  MONPRNCFEE))  B_MONPRNCFEE  ,  MAX(DECODE(DUMMY,  '2',  USE_TIME  ))  B_USE_TIME  ,  MAX(DECODE(DUMMY,  '2',  BSTYP_CD  ))  B_BSTYP_CD  ,  MAX(DECODE(DUMMY,  '2',  GRADNM  ))  B_GRADNM  ,  MAX(DECODE(DUMMY,  '2',  UPSO_NM  ))  B_UPSO_NM  ,  MAX(DECODE(DUMMY,  '2',  CHG_DAY))  B_CHG_DAY  ,  MAX(DECODE(DUMMY,  '2',  CHG_NUM))  B_CHG_NUM  ,  MAX(DECODE(DUMMY,  '2',  CHG_BRAN  ))  B_CHG_BRAN  FROM  (   \n";
        query +=" SELECT  *  FROM  (   \n";
        query +=" SELECT  '1'  DUMMY  ,  A.UPSO_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.USE_TIME  ,  A.BSTYP_CD  ,  ''  UPSO_NM  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  A.UPSO_GRAD  =  B.GRAD_GBN  ORDER  BY  A.CHG_DAY  DESC  ,A.CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  UNION  ALL   \n";
        query +=" SELECT  *  FROM  (   \n";
        query +=" SELECT  '2'  DUMMY  ,  A.UPSO_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.USE_TIME  ,  A.BSTYP_CD  ,  C.UPSO_NM  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  (   \n";
        query +=" SELECT  BEFORE_UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD)   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD(+)   \n";
        query +=" AND  A.UPSO_GRAD  =  B.GRAD_GBN(+)  ORDER  BY  A.CHG_DAY  DESC  ,A.CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  )  )  B  ,  (   \n";
        query +=" SELECT  MAX(MCHNDAESU)  MCHNDAESU  ,  MAX(B_MCHNDAESU)  B_MCHNDAESU  ,  MAX(UPSO_CD)  UPSO_CD  FROM  (   \n";
        query +=" SELECT  MCHNDAESU  MCHNDAESU  ,  NULL  B_MCHNDAESU  ,  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  NULL  ,  MCHNDAESU  B_MCHNDAESU  ,  NULL  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  (   \n";
        query +=" SELECT  BEFORE_UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD  )  )  )  C  ,  INSA.TINS_MST01  D  ,  FIDU.TLEV_BSCON  E  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD(+)   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD(+)   \n";
        query +=" AND  A.STAFF_CD  =  D.STAFF_NUM  (+)   \n";
        query +=" AND  A.BSCON_CD  =  E.BSCON_CD(+) ";
        String xml ="";
        xml += "<select id=\"SQLupsoinfo_search_SEL6\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query);
        xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupsoinfo_search_SEL7() {
        String    query="";
        query +=" SELECT  UPSO_CD  ,  AUTO_NUM  ,  (   \n";
        query +=" SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  USE_YN  =  'Y'   \n";
        query +=" AND  BANK_CD  =  SUBSTR(A.BANK_CD,  1,3)  )  AS  BANK_NM  ,  AUTO_ACCNNUM  ,  PAYPRES_NM  ,  APPTN_DAY  ,  GBN  ,  EMAIL  FROM  GIBU.TBRA_UPSO_AUTO  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  TERM_YN  =  'N'   \n";
        query +=" AND  AUTO_NUM  =  (   \n";
        query +=" SELECT  MAX(AUTO_NUM)  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  :UPSO_CD  ) ";
        String xml ="";
        xml += "<select id=\"SQLupsoinfo_search_SEL7\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query);
        xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupsoinfo_search_SEL4() {
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(A.BSTYP_CD)  ||  A.GRAD_GBN  GRAD  ,  A.AREA  ,  A.MCHNDAESU  ,  A.STNDAMT  ,  B.GRADNM  ,  A.MCHNDAESU  *  A.STNDAMT  AMT  FROM  GIBU.TBRA_NOREBANG_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  A.GRAD_GBN  =  B.GRAD_GBN  AND	A.CHG_DAY  =  :CHG_DAY  AND	A.CHG_NUM  =  :CHG_NUM  AND	A.CHG_BRAN  =  :CHG_BRAN ";
        String xml ="";
        xml += "<select id=\"SQLupsoinfo_search_SEL4\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query);
        xml += "\n</select>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "/Users/pp01713/cursor-workspace/query-conveter/app/src/main/resources/";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLupsoinfo_search_SEL6() + "\n";
        content += SQLupsoinfo_search_SEL7() + "\n";
        content += SQLupsoinfo_search_SEL4() + "\n";
        content += bottom;

        try {
            String fileName = "bra01_s01_1.xml";
            FileWriter fw = new FileWriter(dir + "bra01_s01_1.xml");
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

