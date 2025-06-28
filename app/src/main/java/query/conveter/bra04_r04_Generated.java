package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra04_r04_Generated {

    public static String SQLsending_info_SEL8() {
        String    query="";
        query +=" SELECT  TO_NUMBER(#{year})  AS  CNT  FROM  DUAL ";
        String xml ="";
            xml += "<select id=\"SQLsending_info_SEL8\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsending_info_SEL4() {
        String    query="";
        query +=" SELECT  B.YYYYMM  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  RNUM  ,  MAX(A_DAY)  A_DAY  ,  MAX(B_DAY)  B_DAY  ,  MAX(C_DAY)  C_DAY  ,  MAX(D_DAY)  D_DAY  ,  MAX(E_DAY)  E_DAY  ,  MAX(F_DAY)  F_DAY  ,  MAX(G_DAY)  G_DAY  ,  MAX(H_DAY)  H_DAY  ,  MAX(I_DAY)  I_DAY  ,  MAX(J_DAY)  J_DAY  ,  MAX(K_DAY)  K_DAY  FROM(   \n";
        query +=" SELECT  A_YRMN  ,  RNUM  ,  DECODE(A_DAY,  NULL,  '',  SUBSTR(A_DAY,1,4)||'/'||SUBSTR(A_DAY,5,2)||'/'||SUBSTR(A_DAY,7,2))  A_DAY  ,  DECODE(B_DAY,  NULL,  '',  SUBSTR(B_DAY,1,4)||'/'||SUBSTR(B_DAY,5,2)||'/'||SUBSTR(B_DAY,7,2))  B_DAY  ,  DECODE(C_DAY,  NULL,  '',  SUBSTR(C_DAY,1,4)||'/'||SUBSTR(C_DAY,5,2)||'/'||SUBSTR(C_DAY,7,2))  C_DAY  ,  DECODE(D_DAY,  NULL,  '',  SUBSTR(D_DAY,1,4)||'/'||SUBSTR(D_DAY,5,2)||'/'||SUBSTR(D_DAY,7,2))  D_DAY  ,  DECODE(E_DAY,  NULL,  '',  SUBSTR(E_DAY,1,4)||'/'||SUBSTR(E_DAY,5,2)||'/'||SUBSTR(E_DAY,7,2))  E_DAY  ,  DECODE(F_DAY,  NULL,  '',  SUBSTR(F_DAY,1,4)||'/'||SUBSTR(F_DAY,5,2)||'/'||SUBSTR(F_DAY,7,2))  F_DAY  ,  DECODE(G_DAY,  NULL,  '',  SUBSTR(G_DAY,1,4)||'/'||SUBSTR(G_DAY,5,2)||'/'||SUBSTR(G_DAY,7,2))  G_DAY  ,  DECODE(H_DAY,  NULL,  '',  SUBSTR(H_DAY,1,4)||'/'||SUBSTR(H_DAY,5,2)||'/'||SUBSTR(H_DAY,7,2))  H_DAY  ,  DECODE(I_DAY,  NULL,  '',  SUBSTR(I_DAY,1,4)||'/'||SUBSTR(I_DAY,5,2)||'/'||SUBSTR(I_DAY,7,2))  I_DAY  ,  DECODE(J_DAY,  NULL,  '',  SUBSTR(J_DAY,1,4)||'/'||SUBSTR(J_DAY,5,2)||'/'||SUBSTR(J_DAY,7,2))  J_DAY  ,  DECODE(K_DAY,  NULL,  '',  SUBSTR(K_DAY,1,4)||'/'||SUBSTR(K_DAY,5,2)||'/'||SUBSTR(K_DAY,7,2))  K_DAY  FROM  (   \n";
        query +=" SELECT  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  TO_CHAR(A.INS_DATE,'YYYYMMDD')  A_DAY  ,  A.DEMD_YRMN  A_YYYYMM  ,  row_number()  over  (partition  by  A.DEMD_YRMN  order  by  TO_CHAR(A.INS_DATE,'YYYYMMDD')  )  as  RNUM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.DEMD_YRMN  LIKE  #{year}  ||  '%'   \n";
        query +=" AND  A.OCR_PRNT  =  '1'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.A_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  TO_CHAR(A.INS_DATE,'YYYYMMDD')  B_DAY  ,  A.DEMD_YRMN  B_YYYYMM  ,  row_number()  over  (partition  by  A.DEMD_YRMN  order  by  TO_CHAR(A.INS_DATE,'YYYYMMDD')  )  as  RNUM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.DEMD_YRMN  LIKE  #{year}  ||  '%'   \n";
        query +=" AND  A.OCR_PRNT  =  '2'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.B_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  C_DAY  ,  SUBSTR(PRNT_DAY,1,6)  C_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(PRNT_DAY,1,6)  order  by  PRNT_DAY  )  as  RNUM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'O'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  #{year}||'%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.  C_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  D_DAY  ,  SUBSTR(PRNT_DAY,1,6)  D_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(PRNT_DAY,1,6)  order  by  PRNT_DAY  )  as  RNUM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'M'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  #{year}||'%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  BPAP_DAY  E_DAY  ,  SUBSTR(BPAP_DAY,1,6)  E_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(BPAP_DAY,1,6)  order  by  BPAP_DAY  )  as  RNUM  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.BPAP_GBN  =  '2'   \n";
        query +=" AND  A.BPAP_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.E_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  BPAP_DAY  F_DAY  ,  SUBSTR(BPAP_DAY,1,6)  F_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(BPAP_DAY,1,6)  order  by  BPAP_DAY  )  as  RNUM  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.BPAP_GBN  =  '3'   \n";
        query +=" AND  A.BPAP_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.F_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  G_DAY  ,  SUBSTR(DISP_DAY,1,6)  G_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(DISP_DAY,1,6)  order  by  DISP_DAY  )  as  RNUM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  #{year}  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'A'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.G_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  H_DAY  ,  SUBSTR(DISP_DAY,1,6)  H_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(DISP_DAY,1,6)  order  by  DISP_DAY  )  as  RNUM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  #{year}  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'B'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.H_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  I_DAY  ,  ''  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  I_DAY  ,  SUBSTR(DISP_DAY,1,6)  I_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(DISP_DAY,1,6)  order  by  DISP_DAY  )  as  RNUM  FROM  GIBU.TBRA_CONTR_TERM  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.I_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  J_DAY  ,  ''  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  VISIT_DAY  J_DAY  ,  SUBSTR(VISIT_DAY,1,6)  D_YYYYMM  ,  row_number()  over  (partition  by  SUBSTR(VISIT_DAY,1,6)  order  by  VISIT_DAY  )  as  RNUM  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.JOB_GBN  =  'L'   \n";
        query +=" AND  A.VISIT_DAY  LIKE  #{year}||'%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  ,  ''  B_DAY  ,  ''  C_DAY  ,  ''  D_DAY  ,  ''  E_DAY  ,  ''  F_DAY  ,  ''  G_DAY  ,  ''  H_DAY  ,  ''  I_DAY  ,  ''  J_DAY  ,  K_DAY  ,  A.RNUM  RNUM  FROM  (   \n";
        query +=" SELECT  TO_CHAR(A.SEND_DATE1,  'YYYYMMDD')  K_DAY  ,  TO_CHAR(A.SEND_DATE1,  'YYYYMM')  D_YYYYMM  ,  row_number()  over  (partition  by  TO_CHAR(A.SEND_DATE1,  'YYYYMM')  order  by  A.SEND_DATE1  )  as  RNUM  FROM  GIBU.TBRA_DEMD_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.PROC_GBN  =  '1'   \n";
        query +=" AND  A.DEMD_YRMN  LIKE  #{year}||'%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  )  XA  WHERE  (A_DAY  IS  NOT  NULL   \n";
        query +=" OR  B_DAY  IS  NOT  NULL   \n";
        query +=" OR  C_DAY  IS  NOT  NULL   \n";
        query +=" OR  D_DAY  IS  NOT  NULL   \n";
        query +=" OR  E_DAY  IS  NOT  NULL   \n";
        query +=" OR  F_DAY  IS  NOT  NULL   \n";
        query +=" OR  G_DAY  IS  NOT  NULL   \n";
        query +=" OR  H_DAY  IS  NOT  NULL   \n";
        query +=" OR  I_DAY  IS  NOT  NULL   \n";
        query +=" OR  J_DAY  IS  NOT  NULL   \n";
        query +=" OR  K_DAY  IS  NOT  NULL)  )  A,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  #{year}||  A.A_YRMN(+)  =  B.YYYYMM  GROUP  BY  B.YYYYMM,A.RNUM  ORDER  BY  B.YYYYMM,A.RNUM ";
        String xml ="";
            xml += "<select id=\"SQLsending_info_SEL4\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsending_info_SEL7() {
        String    query="";
        query +=" SELECT  B.YYYYMM  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  RNUM  ,  MAX(A_DAY)  AS  A_DAY  ,  MAX(B_DAY)  AS  B_DAY  ,  MAX(C_DAY)  AS  C_DAY  ,  MAX(D_DAY)  AS  D_DAY  ,  MAX(E_DAY)  AS  E_DAY  ,  MAX(F_DAY)  AS  F_DAY  ,  MAX(G_DAY)  AS  G_DAY  ,  MAX(H_DAY)  AS  H_DAY  ,  MAX(I_DAY)  AS  I_DAY  ,  MAX(J_DAY)  AS  J_DAY  ,  MAX(K_DAY)  AS  K_DAY  FROM  (   \n";
        query +=" SELECT  A_YRMN  ,  RNUM  ,  DECODE(A_DAY,  NULL,  '',  SUBSTR(A_DAY,  1,  4)  ||  '/'  ||  SUBSTR(A_DAY,  5,  2)  ||  '/'  ||  SUBSTR(A_DAY,  7,  2))  AS  A_DAY  ,  DECODE(B_DAY,  NULL,  '',  SUBSTR(B_DAY,  1,  4)  ||  '/'  ||  SUBSTR(B_DAY,  5,  2)  ||  '/'  ||  SUBSTR(B_DAY,  7,  2))  AS  B_DAY  ,  DECODE(C_DAY,  NULL,  '',  SUBSTR(C_DAY,  1,  4)  ||  '/'  ||  SUBSTR(C_DAY,  5,  2)  ||  '/'  ||  SUBSTR(C_DAY,  7,  2))  AS  C_DAY  ,  DECODE(D_DAY,  NULL,  '',  SUBSTR(D_DAY,  1,  4)  ||  '/'  ||  SUBSTR(D_DAY,  5,  2)  ||  '/'  ||  SUBSTR(D_DAY,  7,  2))  AS  D_DAY  ,  DECODE(E_DAY,  NULL,  '',  SUBSTR(E_DAY,  1,  4)  ||  '/'  ||  SUBSTR(E_DAY,  5,  2)  ||  '/'  ||  SUBSTR(E_DAY,  7,  2))  AS  E_DAY  ,  DECODE(F_DAY,  NULL,  '',  SUBSTR(F_DAY,  1,  4)  ||  '/'  ||  SUBSTR(F_DAY,  5,  2)  ||  '/'  ||  SUBSTR(F_DAY,  7,  2))  AS  F_DAY  ,  DECODE(G_DAY,  NULL,  '',  SUBSTR(G_DAY,  1,  4)  ||  '/'  ||  SUBSTR(G_DAY,  5,  2)  ||  '/'  ||  SUBSTR(G_DAY,  7,  2))  AS  G_DAY  ,  DECODE(H_DAY,  NULL,  '',  SUBSTR(H_DAY,  1,  4)  ||  '/'  ||  SUBSTR(H_DAY,  5,  2)  ||  '/'  ||  SUBSTR(H_DAY,  7,  2))  AS  H_DAY  ,  DECODE(I_DAY,  NULL,  '',  SUBSTR(I_DAY,  1,  4)  ||  '/'  ||  SUBSTR(I_DAY,  5,  2)  ||  '/'  ||  SUBSTR(I_DAY,  7,  2))  AS  I_DAY  ,  DECODE(J_DAY,  NULL,  '',  SUBSTR(J_DAY,  1,  4)  ||  '/'  ||  SUBSTR(J_DAY,  5,  2)  ||  '/'  ||  SUBSTR(J_DAY,  7,  2))  AS  J_DAY  ,  DECODE(K_DAY,  NULL,  '',  SUBSTR(K_DAY,  1,  4)  ||  '/'  ||  SUBSTR(K_DAY,  5,  2)  ||  '/'  ||  SUBSTR(K_DAY,  7,  2))  AS  K_DAY  FROM  (   \n";
        query +=" SELECT  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  A_DAY  ,  A.DEMD_YRMN  AS  A_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  A.DEMD_YRMN  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  A.DEMD_YRMN  BETWEEN  #{year}  ||  '01'   \n";
        query +=" AND  #{year}  ||  '12'   \n";
        query +=" AND  C.REQ_DTM  BETWEEN  #{year}  ||  '0101'   \n";
        query +=" AND  #{year}  ||  '1231'   \n";
        query +=" AND  C.REQ_DTM  LIKE  A.DEMD_YRMN  ||  '%'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_10'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.A_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  B_DAY  ,  A.DEMD_YRMN  AS  B_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  A.DEMD_YRMN  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  A.DEMD_YRMN  BETWEEN  #{year}  ||  '01'   \n";
        query +=" AND  #{year}  ||  '12'   \n";
        query +=" AND  C.REQ_DTM  BETWEEN  #{year}  ||  '0101'   \n";
        query +=" AND  #{year}  ||  '1231'   \n";
        query +=" AND  C.REQ_DTM  LIKE  A.DEMD_YRMN  ||  '%'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_11'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.B_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  AS  C_DAY  ,  SUBSTR(PRNT_DAY,  1,  6)  AS  C_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(PRNT_DAY,  1,  6)  ORDER  BY  PRNT_DAY)  AS  RNUM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'O'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.C_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  AS  D_DAY  ,  SUBSTR  (PRNT_DAY,  1,  6)  AS  D_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(PRNT_DAY,  1,  6)  ORDER  BY  PRNT_DAY)  AS  RNUM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'M'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  E_DAY  ,  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  AS  E_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  B.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  C.REQ_DTM  BETWEEN  #{year}  ||  '0101'   \n";
        query +=" AND  #{year}  ||  '1231'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_09'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.E_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  BPAP_DAY  AS  F_DAY  ,  SUBSTR(BPAP_DAY,  1,  6)  AS  F_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(BPAP_DAY,  1,  6)  ORDER  BY  BPAP_DAY)  AS  RNUM  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.BPAP_GBN  =  '3'   \n";
        query +=" AND  A.BPAP_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.F_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  ROWNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  AS  G_DAY  ,  SUBSTR(DISP_DAY,  1,  6)  AS  G_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(DISP_DAY,  1,  6)  ORDER  BY  DISP_DAY)  AS  RNUM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  #{year}  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'A'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.G_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  AS  H_DAY  ,  SUBSTR(DISP_DAY,  1,  6)  AS  H_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(DISP_DAY,  1,  6)  ORDER  BY  DISP_DAY)  AS  RNUM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  #{year}  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'B'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.H_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  AS  I_DAY  ,  SUBSTR(DISP_DAY,  1,  6)  AS  I_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(DISP_DAY,  1,  6)  ORDER  BY  DISP_DAY)  AS  RNUM  FROM  GIBU.TBRA_CONTR_TERM  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.I_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  VISIT_DAY  AS  J_DAY  ,  SUBSTR(VISIT_DAY,  1,  6)  AS  D_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(VISIT_DAY,  1,  6)  ORDER  BY  VISIT_DAY)  AS  RNUM  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.JOB_GBN  =  'L'   \n";
        query +=" AND  A.VISIT_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  K_DAY  ,  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  AS  D_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_DEMD_AUTO  A  ,  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  A.PROC_GBN  =  '1'   \n";
        query +=" AND  A.DEMD_YRMN  BETWEEN  #{year}  ||  '01'   \n";
        query +=" AND  #{year}  ||  '12'   \n";
        query +=" AND  C.REQ_DTM  BETWEEN  #{year}  ||  '0101'   \n";
        query +=" AND  #{year}  ||  '1231'   \n";
        query +=" AND  C.REQ_DTM  LIKE  A.DEMD_YRMN  ||  '%'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_12'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  )  XA  WHERE  (  A_DAY  IS  NOT  NULL   \n";
        query +=" OR  B_DAY  IS  NOT  NULL   \n";
        query +=" OR  C_DAY  IS  NOT  NULL   \n";
        query +=" OR  D_DAY  IS  NOT  NULL   \n";
        query +=" OR  E_DAY  IS  NOT  NULL   \n";
        query +=" OR  F_DAY  IS  NOT  NULL   \n";
        query +=" OR  G_DAY  IS  NOT  NULL   \n";
        query +=" OR  H_DAY  IS  NOT  NULL   \n";
        query +=" OR  I_DAY  IS  NOT  NULL   \n";
        query +=" OR  J_DAY  IS  NOT  NULL   \n";
        query +=" OR  K_DAY  IS  NOT  NULL)  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  #{year}  ||  A.A_YRMN(+)  =  B.YYYYMM  GROUP  BY  B.YYYYMM,  A.RNUM  ORDER  BY  B.YYYYMM,  A.RNUM ";
        String xml ="";
            xml += "<select id=\"SQLsending_info_SEL7\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsending_info_SEL1() {
        String    query="";
        query +=" SELECT  A_YRMN  ,  DECODE(A_DAY,  NULL,  '',  SUBSTR(A_DAY,1,4)||'/'||SUBSTR(A_DAY,5,2)||'/'||SUBSTR(A_DAY,7,2))  A_DAY  ,  B_YRMN  ,  DECODE(B_DAY,  NULL,  '',  SUBSTR(B_DAY,1,4)||'/'||SUBSTR(B_DAY,5,2)||'/'||SUBSTR(B_DAY,7,2))  B_DAY  ,  C_YRMN  ,  DECODE(C_DAY,  NULL,  '',  SUBSTR(C_DAY,1,4)||'/'||SUBSTR(C_DAY,5,2)||'/'||SUBSTR(C_DAY,7,2))  C_DAY  ,  D_YRMN  ,  DECODE(D_DAY,  NULL,  '',  SUBSTR(D_DAY,1,4)||'/'||SUBSTR(D_DAY,5,2)||'/'||SUBSTR(D_DAY,7,2))  D_DAY  ,  E_YRMN  ,  DECODE(E_DAY,  NULL,  '',  SUBSTR(E_DAY,1,4)||'/'||SUBSTR(E_DAY,5,2)||'/'||SUBSTR(E_DAY,7,2))  E_DAY  ,  F_YRMN  ,  DECODE(F_DAY,  NULL,  '',  SUBSTR(F_DAY,1,4)||'/'||SUBSTR(F_DAY,5,2)||'/'||SUBSTR(F_DAY,7,2))  F_DAY  ,  G_YRMN  ,  DECODE(G_DAY,  NULL,  '',  SUBSTR(G_DAY,1,4)||'/'||SUBSTR(G_DAY,5,2)||'/'||SUBSTR(G_DAY,7,2))  G_DAY  ,  H_YRMN  ,  DECODE(H_DAY,  NULL,  '',  SUBSTR(H_DAY,1,4)||'/'||SUBSTR(H_DAY,5,2)||'/'||SUBSTR(H_DAY,7,2))  H_DAY  ,  I_YRMN  ,  DECODE(I_DAY,  NULL,  '',  SUBSTR(I_DAY,1,4)||'/'||SUBSTR(I_DAY,5,2)||'/'||SUBSTR(I_DAY,7,2))  I_DAY  FROM  (   \n";
        query +=" SELECT  A_DAY  ,  SUBSTR(B.YYYYMM,5,2)  A_YRMN  FROM  (   \n";
        query +=" SELECT  TO_CHAR(A.INS_DATE,'YYYYMMDD')  A_DAY  ,  A.DEMD_YRMN  A_YYYYMM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.DEMD_YRMN  LIKE  #{year}  ||  '%'   \n";
        query +=" AND  A.OCR_PRNT  =  '1'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.A_YYYYMM(+)  =  B.YYYYMM  )  XA  ,  (   \n";
        query +=" SELECT  B_DAY  ,  SUBSTR(B.YYYYMM,5,2)  B_YRMN  FROM  (   \n";
        query +=" SELECT  TO_CHAR(A.INS_DATE,'YYYYMMDD')  B_DAY  ,  A.DEMD_YRMN  B_YYYYMM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.DEMD_YRMN  LIKE  #{year}  ||  '%'   \n";
        query +=" AND  A.OCR_PRNT  =  '2'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.B_YYYYMM(+)  =  B.YYYYMM  )  XB  ,  (   \n";
        query +=" SELECT  C_DAY  ,  SUBSTR(B.YYYYMM,5,2)  C_YRMN  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  C_DAY  ,  SUBSTR(PRNT_DAY,1,6)  C_YYYYMM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'O'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  #{year}||'%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.  C_YYYYMM(+)  =  B.YYYYMM  )  XC  ,  (   \n";
        query +=" SELECT  D_DAY  ,  SUBSTR(B.YYYYMM,5,2)  D_YRMN  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  D_DAY  ,  SUBSTR(PRNT_DAY,1,6)  D_YYYYMM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'M'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  #{year}||'%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM(+)  =  B.YYYYMM  )  XD  ,  (   \n";
        query +=" SELECT  E_DAY  ,  SUBSTR(B.YYYYMM,5,2)  E_YRMN  FROM  (   \n";
        query +=" SELECT  BPAP_DAY  E_DAY  ,  SUBSTR(BPAP_DAY,1,6)  E_YYYYMM  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.BPAP_GBN  =  '2'   \n";
        query +=" AND  A.BPAP_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.E_YYYYMM(+)  =  B.YYYYMM  )  XE  ,  (   \n";
        query +=" SELECT  F_DAY  ,  SUBSTR(B.YYYYMM,5,2)  F_YRMN  FROM  (   \n";
        query +=" SELECT  BPAP_DAY  F_DAY  ,  SUBSTR(BPAP_DAY,1,6)  F_YYYYMM  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.BPAP_GBN  =  '3'   \n";
        query +=" AND  A.BPAP_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.F_YYYYMM(+)  =  B.YYYYMM  )  XF  ,  (   \n";
        query +=" SELECT  G_DAY  ,  SUBSTR(B.YYYYMM,5,2)  G_YRMN  FROM  (   \n";
        query +=" SELECT  DISP_DAY  G_DAY  ,  SUBSTR(DISP_DAY,1,6)  G_YYYYMM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  #{year}  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'A'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.G_YYYYMM(+)  =  B.YYYYMM  )  XG  ,  (   \n";
        query +=" SELECT  H_DAY  ,  SUBSTR(B.YYYYMM,5,2)  H_YRMN  FROM  (   \n";
        query +=" SELECT  DISP_DAY  H_DAY  ,  SUBSTR(DISP_DAY,1,6)  H_YYYYMM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  #{year}  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'B'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.H_YYYYMM(+)  =  B.YYYYMM  )  XH  ,  (   \n";
        query +=" SELECT  I_DAY  ,  SUBSTR(B.YYYYMM,5,2)  I_YRMN  FROM  (   \n";
        query +=" SELECT  DISP_DAY  I_DAY  ,  SUBSTR(DISP_DAY,1,6)  I_YYYYMM  FROM  GIBU.TBRA_CONTR_TERM  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.I_YYYYMM(+)  =  B.YYYYMM  )  XI  WHERE  XA.A_YRMN  =  XB.B_YRMN   \n";
        query +=" AND  XB.B_YRMN  =  XC.C_YRMN   \n";
        query +=" AND  XC.C_YRMN  =  XD.D_YRMN   \n";
        query +=" AND  XD.D_YRMN  =  XE.E_YRMN   \n";
        query +=" AND  XE.E_YRMN  =  XF.F_YRMN   \n";
        query +=" AND  XF.F_YRMN  =  XG.G_YRMN   \n";
        query +=" AND  XG.G_YRMN  =  XH.H_YRMN   \n";
        query +=" AND  XH.H_YRMN  =  XI.I_YRMN  ORDER  BY  XA.A_YRMN ";
        String xml ="";
            xml += "<select id=\"SQLsending_info_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsending_info_SEL10() {
        String    query="";
        query +=" SELECT  B.YYYYMM  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  RNUM  ,  MAX(A_DAY)  AS  A_DAY  ,  MAX(B_DAY)  AS  B_DAY  ,  MAX(C_DAY)  AS  C_DAY  ,  MAX(D_DAY)  AS  D_DAY  ,  MAX(E_DAY)  AS  E_DAY  ,  MAX(F_DAY)  AS  F_DAY  ,  MAX(G_DAY)  AS  G_DAY  ,  MAX(H_DAY)  AS  H_DAY  ,  MAX(I_DAY)  AS  I_DAY  ,  MAX(J_DAY)  AS  J_DAY  ,  MAX(K_DAY)  AS  K_DAY  FROM  (   \n";
        query +=" SELECT  A_YRMN  ,  RNUM  ,  DECODE(A_DAY,  NULL,  '',  SUBSTR(A_DAY,  1,  4)  ||  '/'  ||  SUBSTR(A_DAY,  5,  2)  ||  '/'  ||  SUBSTR(A_DAY,  7,  2))  AS  A_DAY  ,  DECODE(B_DAY,  NULL,  '',  SUBSTR(B_DAY,  1,  4)  ||  '/'  ||  SUBSTR(B_DAY,  5,  2)  ||  '/'  ||  SUBSTR(B_DAY,  7,  2))  AS  B_DAY  ,  DECODE(C_DAY,  NULL,  '',  SUBSTR(C_DAY,  1,  4)  ||  '/'  ||  SUBSTR(C_DAY,  5,  2)  ||  '/'  ||  SUBSTR(C_DAY,  7,  2))  AS  C_DAY  ,  DECODE(D_DAY,  NULL,  '',  SUBSTR(D_DAY,  1,  4)  ||  '/'  ||  SUBSTR(D_DAY,  5,  2)  ||  '/'  ||  SUBSTR(D_DAY,  7,  2))  AS  D_DAY  ,  DECODE(E_DAY,  NULL,  '',  SUBSTR(E_DAY,  1,  4)  ||  '/'  ||  SUBSTR(E_DAY,  5,  2)  ||  '/'  ||  SUBSTR(E_DAY,  7,  2))  AS  E_DAY  ,  DECODE(F_DAY,  NULL,  '',  SUBSTR(F_DAY,  1,  4)  ||  '/'  ||  SUBSTR(F_DAY,  5,  2)  ||  '/'  ||  SUBSTR(F_DAY,  7,  2))  AS  F_DAY  ,  DECODE(G_DAY,  NULL,  '',  SUBSTR(G_DAY,  1,  4)  ||  '/'  ||  SUBSTR(G_DAY,  5,  2)  ||  '/'  ||  SUBSTR(G_DAY,  7,  2))  AS  G_DAY  ,  DECODE(H_DAY,  NULL,  '',  SUBSTR(H_DAY,  1,  4)  ||  '/'  ||  SUBSTR(H_DAY,  5,  2)  ||  '/'  ||  SUBSTR(H_DAY,  7,  2))  AS  H_DAY  ,  DECODE(I_DAY,  NULL,  '',  SUBSTR(I_DAY,  1,  4)  ||  '/'  ||  SUBSTR(I_DAY,  5,  2)  ||  '/'  ||  SUBSTR(I_DAY,  7,  2))  AS  I_DAY  ,  DECODE(J_DAY,  NULL,  '',  SUBSTR(J_DAY,  1,  4)  ||  '/'  ||  SUBSTR(J_DAY,  5,  2)  ||  '/'  ||  SUBSTR(J_DAY,  7,  2))  AS  J_DAY  ,  DECODE(K_DAY,  NULL,  '',  SUBSTR(K_DAY,  1,  4)  ||  '/'  ||  SUBSTR(K_DAY,  5,  2)  ||  '/'  ||  SUBSTR(K_DAY,  7,  2))  AS  K_DAY  FROM  (   \n";
        query +=" SELECT  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  A_DAY  ,  A.DEMD_YRMN  AS  A_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  A.DEMD_YRMN  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  A.DEMD_YRMN  BETWEEN  #{year}  ||  '01'   \n";
        query +=" AND  #{year}  ||  '12'   \n";
        query +=" AND  C.REQ_DTM  LIKE  A.DEMD_YRMN  ||  '%'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_16'   \n";
        query +=" AND  C.RESERVED4  IS  NULL  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.A_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  B_DAY  ,  A.DEMD_YRMN  AS  B_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  A.DEMD_YRMN  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  A.DEMD_YRMN  BETWEEN  #{year}  ||  '01'   \n";
        query +=" AND  #{year}  ||  '12'   \n";
        query +=" AND  C.REQ_DTM  LIKE  A.DEMD_YRMN  ||  '%'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_16'   \n";
        query +=" AND  C.RESERVED4  =  'DMND'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.B_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  AS  C_DAY  ,  SUBSTR(PRNT_DAY,  1,  6)  AS  C_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(PRNT_DAY,  1,  6)  ORDER  BY  PRNT_DAY)  AS  RNUM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'O'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.C_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  PRNT_DAY  AS  D_DAY  ,  SUBSTR  (PRNT_DAY,  1,  6)  AS  D_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(PRNT_DAY,  1,  6)  ORDER  BY  PRNT_DAY)  AS  RNUM  FROM  GIBU.TBRA_DEMD_INDTN  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.CRET_GBN  =  'M'   \n";
        query +=" AND  A.PRNT_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  E_DAY  ,  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  AS  E_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  B.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  C.REQ_DTM  BETWEEN  #{year}  ||  '0101'   \n";
        query +=" AND  #{year}  ||  '1231'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_09'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.E_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  VISIT_DAY  AS  F_DAY  ,  SUBSTR(VISIT_DAY,  1,  6)  AS  F_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(VISIT_DAY,  1,  6)  ORDER  BY  VISIT_DAY)  AS  RNUM  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.JOB_GBN  =  'C'   \n";
        query +=" AND  A.VISIT_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.F_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  ROWNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  AS  G_DAY  ,  SUBSTR(DISP_DAY,  1,  6)  AS  G_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(DISP_DAY,  1,  6)  ORDER  BY  DISP_DAY)  AS  RNUM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  #{year}  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'A'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.G_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  AS  H_DAY  ,  SUBSTR(DISP_DAY,  1,  6)  AS  H_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(DISP_DAY,  1,  6)  ORDER  BY  DISP_DAY)  AS  RNUM  FROM  GIBU.TBRA_DISP_HISTY  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  =  #{year}  ||  '%'   \n";
        query +=" AND  A.DISP_GBN  =  'B'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.H_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  I_DAY  ,  ''  AS  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  DISP_DAY  AS  I_DAY  ,  SUBSTR(DISP_DAY,  1,  6)  AS  I_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(DISP_DAY,  1,  6)  ORDER  BY  DISP_DAY)  AS  RNUM  FROM  GIBU.TBRA_CONTR_TERM  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.DISP_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.I_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  J_DAY  ,  ''  AS  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  VISIT_DAY  AS  J_DAY  ,  SUBSTR(VISIT_DAY,  1,  6)  AS  D_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(VISIT_DAY,  1,  6)  ORDER  BY  VISIT_DAY)  AS  RNUM  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  A.JOB_GBN  =  'L'   \n";
        query +=" AND  A.VISIT_DAY  LIKE  #{year}  ||  '%'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  UNION  ALL   \n";
        query +=" SELECT  ''  AS  A_DAY  ,  SUBSTR(B.YYYYMM,  5,  2)  AS  A_YRMN  ,  ''  AS  B_DAY  ,  ''  AS  C_DAY  ,  ''  AS  D_DAY  ,  ''  AS  E_DAY  ,  ''  AS  F_DAY  ,  ''  AS  G_DAY  ,  ''  AS  H_DAY  ,  ''  AS  I_DAY  ,  ''  AS  J_DAY  ,  K_DAY  ,  A.RNUM  AS  RNUM  FROM  (   \n";
        query +=" SELECT  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8)  AS  K_DAY  ,  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  AS  D_YYYYMM  ,  ROW_NUMBER  ()  OVER  (PARTITION  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  6)  ORDER  BY  SUBSTR(NVL(C.SMS_SND_DTM,  C.SND_DTM),  1,  8))  AS  RNUM  FROM  GIBU.TBRA_DEMD_AUTO  A  ,  GIBU.TBRA_UPSO  B  ,  KAKAO.MZSENDLOG  C  WHERE  B.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  C.USER_ID   \n";
        query +=" AND  A.PROC_GBN  =  '1'   \n";
        query +=" AND  A.DEMD_YRMN  BETWEEN  #{year}  ||  '01'   \n";
        query +=" AND  #{year}  ||  '12'   \n";
        query +=" AND  C.REQ_DTM  LIKE  A.DEMD_YRMN  ||  '%'   \n";
        query +=" AND  C.TMPL_CD  =  'JIBU_17'  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  A.D_YYYYMM  =  B.YYYYMM  )  XA  WHERE  (  A_DAY  IS  NOT  NULL   \n";
        query +=" OR  B_DAY  IS  NOT  NULL   \n";
        query +=" OR  C_DAY  IS  NOT  NULL   \n";
        query +=" OR  D_DAY  IS  NOT  NULL   \n";
        query +=" OR  E_DAY  IS  NOT  NULL   \n";
        query +=" OR  F_DAY  IS  NOT  NULL   \n";
        query +=" OR  G_DAY  IS  NOT  NULL   \n";
        query +=" OR  H_DAY  IS  NOT  NULL   \n";
        query +=" OR  I_DAY  IS  NOT  NULL   \n";
        query +=" OR  J_DAY  IS  NOT  NULL   \n";
        query +=" OR  K_DAY  IS  NOT  NULL)  )  A  ,  (   \n";
        query +=" SELECT  #{year}  ||  MM  AS  YYYYMM  FROM  GIBU.COPY_MM  )  B  WHERE  #{year}  ||  A.A_YRMN(+)  =  B.YYYYMM  GROUP  BY  B.YYYYMM,  A.RNUM  ORDER  BY  B.YYYYMM,  A.RNUM ";
        String xml ="";
            xml += "<select id=\"SQLsending_info_SEL10\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLbalance_detail_SEL1() {
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  D.CODE_NM  REPT_GBN  ,  A.REPT_AMT  ,  A.PROC_AMT  ,  A.BALANCE  ,  DECODE(B.MAPPING_DAY,  NULL,  C.MAPPING_DAY,  B.MAPPING_DAY)  MAPPING_DAY  FROM  GIBU.TBRA_REPT_BALANCE  A  ,  GIBU.TBRA_REPT  B  ,  GIBU.TBRA_REPT_UPSO_DISTR  C  ,  FIDU.TENV_CODE  D  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  B.REPT_DAY(+)  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM(+)  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN(+)  =  A.REPT_GBN   \n";
        query +=" AND  B.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  C.REPT_DAY(+)  =  A.REPT_DAY   \n";
        query +=" AND  C.REPT_NUM(+)  =  A.REPT_NUM   \n";
        query +=" AND  C.REPT_GBN(+)  =  A.REPT_GBN   \n";
        query +=" AND  C.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  D.HIGH_CD  =  '00141'   \n";
        query +=" AND  D.CODE_CD  =  A.REPT_GBN  ORDER  BY  A.PROC_DAY  ASC,  A.PROC_NUM  ASC ";
        String xml ="";
            xml += "<select id=\"SQLbalance_detail_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLminab_goso_info_SEL3() {
        String    query="";
        query +=" SELECT  GIBU.FT_GET_LAST_REPT_YRMN(#{upsoCd},6)  LAST_YRMN  ,  UPSO_CD  ,  GIBU.FT_GET_START_REPT_YRMN(#{upsoCd},6)  START_YRMN  ,  DECODE(CLSBS_YRMN,  NULL,  TO_CHAR(SYSDATE,'YYYYMM'),  CLSBS_YRMN)  END_YRMN  ,  #{recvDay}  AS  RECV_DAY  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  #{upsoCd} ";
        String xml ="";
            xml += "<select id=\"SQLminab_goso_info_SEL3\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLminab_goso_info_SEL11() {
        String    query="";
        query +=" SELECT  MONTHS_BETWEEN(TO_DATE(#{endYrmn},  'YYYYMM'),  TO_DATE(#{startYrmn},  'YYYYMM'))  +  1  MON_CNT  FROM  DUAL ";
        String xml ="";
            xml += "<select id=\"SQLminab_goso_info_SEL11\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLminab_goso_info_SEL5() {
        String    query="";
        query +=" SELECT  YRMN  FROM  (   \n";
        query +=" SELECT  YYYY  ||  MM  YRMN  FROM  GIBU.COPY_YY  YY  ,  GIBU.COPY_MM  MM  )  WHERE  YRMN  BETWEEN  #{startYrmn}   \n";
        query +=" AND  #{endYrmn} ";
        String xml ="";
            xml += "<select id=\"SQLminab_goso_info_SEL5\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLminab_goso_info_SEL8() {
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_CLSED  WHERE  START_YRMN  <=  #{yrmn}   \n";
        query +=" AND  #{yrmn}  <=  END_YRMN   \n";
        query +=" AND  UPSO_CD  =  #{upsoCd} ";
        String xml ="";
            xml += "<select id=\"SQLminab_goso_info_SEL8\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLminab_goso_info_SEL1() {
        String    query="";
        query +=" SELECT  #{lastYrmn}  AS  LAST_YRMN  ,  #{startYrmn}  AS  START_YRMN  ,  #{endYrmn}  AS  END_YRMN  ,  #{lastCnt}  AS  MON_CNT  ,  #{cnt}  AS  CLOSE_CNT  ,  #{totAmt}  AS  NONPAY_TOT_AMT  ,  #{totUseAmt}  AS  NONPAY_ORG_AMT  ,  #{totAddtAmt}  AS  NONPAY_ADDT_AMT  ,  #{totEaddtAmt}  AS  NONPAY_EXT_ADDT_AMT  FROM  DUAL ";
        String xml ="";
            xml += "<select id=\"SQLminab_goso_info_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLminab_goso_info_SEL2() {
        String    query="";
        query +=" SELECT  REQ_DAY  ,  COMPN_DAY  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  #{upsoCd}  ORDER  BY  REQ_DAY ";
        String xml ="";
            xml += "<select id=\"SQLminab_goso_info_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLminab_goso_info_SEL14() {
        String    query="";
        query +=" SELECT  NVL(BALANCE,0)  BALANCE  FROM  (   \n";
        query +=" SELECT  BALANCE  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  UPSO_CD  =  #{upsoCd}  ORDER  BY  PROC_DAY  DESC,  PROC_NUM  DESC  )  WHERE  ROWNUM  =  1 ";
        String xml ="";
            xml += "<select id=\"SQLminab_goso_info_SEL14\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLget_demd_amt_SEL1() {
        String    query="";
        query +=" SELECT  #{upsoCd}  AS  UPSO_CD  ,  NVL(#{demdGbn},  'O')  AS  DEMD_GBN  ,  SUBSTR(#{startYrmn},  1,  6)  AS  START_YRMN  ,  SUBSTR(#{endYrmn},  1,  6)  AS  END_YRMN  ,  #{recvDay}  AS  RECV_DAY  ,  ''  AS  RECV_DAY2  FROM  (   \n";
        query +=" SELECT  MAX(DEMD_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR  ) ";
        String xml ="";
            xml += "<select id=\"SQLget_demd_amt_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLget_demd_amt_SEL2() {
        String    query="";
        query +=" SELECT  #{monprncfee}  AS  MONPRNCFEE  ,  #{monprncfee2}  AS  MONPRNCFEE2  ,  #{demdMmcnt}  AS  DEMD_MMCNT  ,  #{totUseAmt}  AS  TOT_USE_AMT  ,  #{totAddtAmt}  +  #{totEaddtAmt}  AS  TOT_ADDT_AMT  ,  #{dsctAmt}  AS  DSCT_AMT  ,  #{totDemdAmt}  AS  TOT_DEMD_AMT  ,  TRUNC(#{useAmt},  -1)  AS  ACCU_USE_AMT  ,  TRUNC(#{addtAmt},  -1)  AS  ACCU_ADDT_AMT  ,  TRUNC(#{totAmt},  -1)  AS  ACCU_TOT_AMT  FROM  DUAL ";
        String xml ="";
            xml += "<select id=\"SQLget_demd_amt_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLget_demd_amt_SEL4() {
        String    query="";
        query +=" SELECT  APPL_DAY,  RATE  FROM  GIBU.TBRA_FEERATE_HISTY  WHERE  BSTYP_CD  =  GIBU.FT_GET_BSTYP_INFO(#{upsoCd})  ORDER  BY  APPL_DAY ";
        String xml ="";
            xml += "<select id=\"SQLget_demd_amt_SEL4\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLget_demd_amt_SEL5() {
        String    query="";
        query +=" SELECT  SUBSTR(DSCT_START,1,4)  ||  '/'  ||  SUBSTR(DSCT_START,5,2)  AS  DSCT_START  ,  SUBSTR(DSCT_END,1,4)  ||  '/'  ||  SUBSTR(DSCT_END,5,2)  AS  DSCT_END  FROM  LOG.KDS_SHOP  WHERE  UPSO_CD  =  #{upsoCd} ";
        String xml ="";
            xml += "<select id=\"SQLget_demd_amt_SEL5\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLget_demd_amt_SEL6() {
        String    query="";
        query +=" SELECT  APPL_DAY,  (100  -  MNG_RATE)  AS  RATE  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  GIBU.FT_GET_BSTYP_INFO(#{upsoCd})  ORDER  BY  APPL_DAY ";
        String xml ="";
            xml += "<select id=\"SQLget_demd_amt_SEL6\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_rept_detail_SEL1() {
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_AMT  ,  A.COMIS  ,  A.RECV_DAY  ,  D.BANK_NM  ,  A.ACCN_NUM  ,  NVL(B.BALANCE,  0)  BALANCE  FROM  (   \n";
        query +=" SELECT  XB.UPSO_CD  ,  XA.REPT_DAY  ,  XA.REPT_NUM  ,  XA.REPT_GBN  ,  XA.REPT_AMT  ,  XA.COMIS  ,  XA.RECV_DAY  ,  XA.BANK_CD  ,  XA.ACCN_NUM  ,  XB.DISTR_SEQ  FROM  GIBU.TBRA_REPT  XA  ,  GIBU.TBRA_NOTE  XB  WHERE  XB.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  XB.NOTE_YRMN  =  #{noteYrmn}   \n";
        query +=" AND  XB.NOTE_NUM  =  '0001'   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XA.REPT_DAY  =  XB.REPT_DAY   \n";
        query +=" AND  XA.REPT_NUM  =  XB.REPT_NUM   \n";
        query +=" AND  XA.REPT_GBN  =  XB.REPT_GBN  UNION  ALL   \n";
        query +=" SELECT  XC.UPSO_CD  ,  XA.REPT_DAY  ,  XA.REPT_NUM  ,  XA.REPT_GBN  ,  XA.DISTR_AMT  ,  XB.COMIS  ,  XB.RECV_DAY  ,  XB.BANK_CD  ,  XB.ACCN_NUM  ,  XA.DISTR_SEQ  FROM  GIBU.TBRA_REPT_UPSO_DISTR  XA  ,  GIBU.TBRA_REPT  XB  ,  GIBU.TBRA_NOTE  XC  WHERE  XC.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  XC.NOTE_YRMN  =  #{noteYrmn}   \n";
        query +=" AND  XC.NOTE_NUM  =  '0001'   \n";
        query +=" AND  XA.UPSO_CD  =  XC.UPSO_CD   \n";
        query +=" AND  XA.REPT_DAY  =  XC.REPT_DAY   \n";
        query +=" AND  XA.REPT_NUM  =  XC.REPT_NUM   \n";
        query +=" AND  XA.REPT_GBN  =  XC.REPT_GBN   \n";
        query +=" AND  XA.DISTR_SEQ  =  XC.DISTR_SEQ   \n";
        query +=" AND  XB.REPT_DAY  =  XC.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XC.REPT_NUM   \n";
        query +=" AND  XB.REPT_GBN  =  XC.REPT_GBN  )  A  ,  (   \n";
        query +=" SELECT  #{upsoCd}  AS  UPSO_CD  ,  NVL(BALANCE,  0)  BALANCE  FROM  (   \n";
        query +=" SELECT  PROC_DAY  ,  PROC_NUM  ,  BALANCE  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  PROC_DAY  ||  PROC_NUM  <  (   \n";
        query +=" SELECT  PROC  FROM(   \n";
        query +=" SELECT  PROC_DAY  ||  PROC_NUM  AS  PROC  FROM  GIBU.TBRA_REPT_BALANCE  XA  ,  GIBU.TBRA_NOTE  XB  WHERE  XB.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  XB.NOTE_YRMN  =  #{noteYrmn}   \n";
        query +=" AND  XB.NOTE_NUM  =  '0001'   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XA.REPT_DAY  =  XB.REPT_DAY   \n";
        query +=" AND  XA.REPT_NUM  =  XB.REPT_NUM   \n";
        query +=" AND  XA.REPT_GBN  =  XB.REPT_GBN   \n";
        query +=" AND  NVL(XA.DISTR_SEQ,  '-')  =  NVL(XB.DISTR_SEQ,  '-')  ORDER  BY  PROC_DAY  DESC,  PROC_NUM  DESC  )  WHERE  ROWNUM  =  1  )   \n";
        query +=" AND  UPSO_CD  =  #{upsoCd}  ORDER  BY  PROC_DAY  DESC,  PROC_NUM  DESC  )  )  B  ,  (   \n";
        query +=" SELECT  BANK_CD,  BANK_NM  FROM  ACCT.TCAC_BANK  UNION  ALL   \n";
        query +=" SELECT  CODE_CD  AS  BANK_CD,  CODE_NM  AS  BANK_NM  FROM  FIDU.TENV_CODE  WHERE  1=1   \n";
        query +=" AND  HIGH_CD='00495'  )D  WHERE  B.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  D.BANK_CD  =  A.BANK_CD ";
        String xml ="";
            xml += "<select id=\"SQLupso_rept_detail_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "D:\\kosmos\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLsending_info_SEL8() + "\n";
        content += SQLsending_info_SEL4() + "\n";
        content += SQLsending_info_SEL7() + "\n";
        content += SQLsending_info_SEL1() + "\n";
        content += SQLsending_info_SEL10() + "\n";
        content += SQLbalance_detail_SEL1() + "\n";
        content += SQLminab_goso_info_SEL3() + "\n";
        content += SQLminab_goso_info_SEL11() + "\n";
        content += SQLminab_goso_info_SEL5() + "\n";
        content += SQLminab_goso_info_SEL8() + "\n";
        content += SQLminab_goso_info_SEL1() + "\n";
        content += SQLminab_goso_info_SEL2() + "\n";
        content += SQLminab_goso_info_SEL14() + "\n";
        content += SQLget_demd_amt_SEL1() + "\n";
        content += SQLget_demd_amt_SEL2() + "\n";
        content += SQLget_demd_amt_SEL4() + "\n";
        content += SQLget_demd_amt_SEL5() + "\n";
        content += SQLget_demd_amt_SEL6() + "\n";
        content += SQLupso_rept_detail_SEL1() + "\n";
        content += bottom;

        try {
            FileWriter fw = new FileWriter(dir + "bra04_r04.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

