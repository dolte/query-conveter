package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra03_r11_Generated {

    public static String SQLauto_demd_rept_SEL1() {
        String    query="";
        query +=" SELECT  A.BRAN_CD  ,  A.UPSO_CD  ,  A.DEMD_YRMN  ,  TO_CHAR(ADD_MONTHS(TO_DATE(A.DEMD_YRMN,  'YYYYMM'),  1),  'YYYYMM')  AS  B_DEMD_YRMN  ,  B.CLIENT_NUM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.MONPRNCFEE  ,  (CASE  WHEN  (A.TOT_DEMD_AMT  -  A.MONPRNCFEE)  >  0  THEN  (A.TOT_DEMD_AMT  -  A.MONPRNCFEE)  ELSE  0  END)  AS  NONPY_AMT  ,  A.TOT_DEMD_AMT  ,  E.MONPRNCFEE  AS  B_MONPRNCFEE  ,  E.DSCT_AMT  AS  B_DSCT_AMT  ,  E.TOT_USE_AMT  AS  B_TOT_USE_AMT  ,  E.TOT_DEMD_AMT  AS  B_TOT_DEMD_AMT  ,  E.START_YRMN  AS  B_START_YRMN  ,  E.END_YRMN  AS  B_END_YRMN  ,  (E.TOT_DEMD_AMT  -  E.MONPRNCFEE)  AS  B_NONPY_AMT  ,  E.TOT_ADDT_AMT  +  E.TOT_EADDT_AMT  AS  ADDT_AMT  ,   \n";
        query +=" (SELECT  DECODE(BANK_NM,  NULL,  BANK_CD,  BANK_NM)  FROM  ACCT.TCAC_BANK_7  WHERE  BANK_CD  LIKE  SUBSTR(C.BANK_CD,  1,  3)  ||  '%'   \n";
        query +=" AND  ROWNUM  =  1)  AS  BANK_NM  ,  SUBSTR(C.AUTO_ACCNNUM,  1,  3)  AS  BANK_NO  ,  D.BIPLC_NM  AS  BRAN_NM  ,  D.ADDR  ||  D.HNM  AS  BRAN_ADDR  ,  H.IPPBX_INPHONE_NUM  AS  PHON_NUM2  ,  GIBU.FT_GET_PHONE_FORMAT(H.IPPBX_INPHONE_NUM)  AS  PHON_NUM  ,  D.POST_NUM  ,  B.UPSO_NM  AS  UPSO_NM  ,  B.UPSO_NM  AS  O_UPSO_NM  ,  DECODE(B.PAYPRES_GBN,  'B',  B.MNGEMSTR_NM,  B.PERMMSTR_NM)  AS  DAEPYO  ,  DECODE(B.MAIL_RCPT,  'U',  B.UPSO_NEW_ZIP,  'B',  B.MNGEMSTR_NEW_ZIP,  'O',  B.PERMMSTR_NEW_ZIP)  AS  ZIP  ,  DECODE(B.MAIL_RCPT,  'U',  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '  ||  B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ,  'B',  B.MNGEMSTR_NEW_ADDR1  ||  DECODE(B.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  B.MNGEMSTR_NEW_ADDR2)  ||  B.MNGEMSTR_REF_INFO  ,  'O',  B.PERMMSTR_NEW_ADDR1  ||  DECODE(B.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  B.PERMMSTR_NEW_ADDR2)  ||  B.PERMMSTR_REF_INFO)  AS  ADDR  ,  NVL(A.PROC_GBN,  '1')  AS  PROC_GBN  ,  G.GBN  ,  B.EMAIL_ADDR  AS  EMAIL  ,  C.PWD  ,  REGEXP_REPLACE(DECODE(B.PAYPRES_GBN,  'B',  B.MNGEMSTR_HPNUM,  NVL(B.PERMMSTR_HPNUM,  B.MNGEMSTR_HPNUM)),  '[^0-9]',  '')  AS  CP_NUM  ,  GIBU.GET_BRAN_NM(A.BRAN_CD)  AS  DEPT_NM  ,  F.RECV_DAY  ,  F.REPT_AMT  FROM  GIBU.TBRA_DEMD_AUTO  A  ,  GIBU.TBRA_UPSO  B  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  BANK_CD  ,  AUTO_ACCNNUM  ,  GBN  ,  EMAIL  ,  PWD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BANK_CD  ,  AUTO_ACCNNUM  ,  GBN  ,  EMAIL  ,  SUBSTR(RESINUM,  LENGTH(RESINUM)  -  6)  AS  PWD  ,  RANK()  OVER(PARTITION  BY  UPSO_CD  ORDER  BY  AUTO_NUM  DESC)  AS  RN  FROM  GIBU.TBRA_UPSO_AUTO  )  WHERE  RN  =  1  )  C  ,  INSA.TCPM_BIPLC  D  ,  GIBU.TBRA_DEMD_AUTO  E  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  B.RECV_DAY  ,  A.REPT_AMT  FROM  GIBU.TBRA_REPT_AUTO  A,  GIBU.TBRA_NOTE  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  =  '01'   \n";
        query +=" AND  B.BRAN_CD  LIKE  #{branCd}  ||  '%'   \n";
        query +=" AND  B.NOTE_YRMN  =  #{yrmn}  )  F  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  GBN  FROM  GIBU.TFMS_UPSO  WHERE  GBN  IS  NOT  NULL  )  G  ,  FIDU.TENV_MEMBER  H  WHERE  A.BRAN_CD  LIKE  #{branCd}  ||  '%'   \n";
        query +=" AND  A.DEMD_YRMN  =  #{yrmn}   \n";
        query +=" AND  A.TRNF_RSLT  IS  NULL   \n";
        query +=" AND  A.TOT_DEMD_AMT  >  0   \n";
        query +=" AND  A.DEMD_GBN  =  '31'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  D.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  E.DEMD_YRMN(+)  =  TO_CHAR(ADD_MONTHS(TO_DATE(  #{yrmn},  'YYYYMM'),  1),  'YYYYMM')   \n";
        query +=" AND  E.BRAN_CD(+)  =  A.BRAN_CD   \n";
        query +=" AND  E.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  E.TRNF_RSLT(+)  IS  NULL   \n";
        query +=" AND  E.TOT_DEMD_AMT(+)  >  0   \n";
        query +=" AND  E.DEMD_GBN(+)  =  '31'   \n";
        query +=" AND  NVL(A.PROC_GBN,  '1')  LIKE  #{procGbn}  ||  '%'   \n";
        query +=" AND  A.UPSO_CD  =  G.UPSO_CD   \n";
        query +=" AND  G.GBN  =  DECODE(  #{gbn},  'all',  G.GBN,  #{gbn})   \n";
        query +=" AND  B.PAPER_CANC_YN  =  'N'   \n";
        query +=" AND  A.UPSO_CD  =  F.UPSO_CD   \n";
        query +=" AND  B.STAFF_CD  =  H.USER_ID  ORDER  BY  A.UPSO_CD ";
        String xml ="";
            xml += "<select id=\"SQLauto_demd_rept_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsend_email_XIUD1() {
        String    query="";
        query +=" INSERT INTO amail.ems_mailqueue (seq, mail_code, to_email, to_name, from_email, from_name, subject, target_flag, map1, map2, map3, map4, map5, map_content, reg_date ) SELECT amail.ems_mailqueue_seq.nextval , '24' , #{toEmail} , #{toName} , 'local@komca.or.kr' , '한국음악저작권협회' , #{map1} || '자동이체 청구서' , 'N' , #{map1} , #{map2} , #{map3} , #{map4} , #{map5} , #{mapContent} , SYSDATE FROM DUAL";
        String xml ="";
            xml += "<insert id=\"SQLsend_email_XIUD1\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "D:\\kosmos\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLauto_demd_rept_SEL1() + "\n";
        content += SQLsend_email_XIUD1() + "\n";
        content += bottom;

        try {
            String fileName = "bra01_s01_1.xml";
            FileWriter fw = new FileWriter(dir + "bra03_r11.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

