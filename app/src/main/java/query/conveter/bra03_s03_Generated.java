package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra03_s03_Generated {

    public static String SQLocr_demd_insert_SEL2() {
        String    query="";
        query +=" SELECT  A.DEMD_YRMN  ,  C.CODE_NM  DEMD_NM  ,  A.DEMD_GBN  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.ERR_GBN  ,  A.ERR_CTENT  FROM  GIBU.TBRA_DEMD_ERR  A  ,  GIBU.TBRA_UPSO  B  ,  FIDU.TENV_CODE  C  WHERE  A.DEMD_YRMN  =  #{demdYrmn}   \n";
        query +=" AND  A.CRET_GBN  =  'O'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  #{branCd}   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  A.DEMD_GBN  =  C.CODE_CD ";
        String xml ="";
            xml += "<select id=\"SQLocr_demd_insert_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLocr_demd_insert_SEL3() {
        String    query="";
        query +=" SELECT  XA.GIBU  BRAN_CD  ,  XA.DEPT_NM  ,  DECODE(XB.DEMD_YRMN,  NULL,  NULL,  '완료')  BRAN_END  FROM  INSA.TCPM_DEPT  XA  ,  (   \n";
        query +=" SELECT  DISTINCT  B.BRAN_CD  ,  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.DEMD_YRMN  =  #{demdYrmn}   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD  )  XB  WHERE  XA.DEPT_CD  BETWEEN  '106010100'   \n";
        query +=" AND  '106019999'   \n";
        query +=" AND  XA.GIBU  =  XB.BRAN_CD  (+)  ORDER  BY  BRAN_CD ";
        String xml ="";
            xml += "<select id=\"SQLocr_demd_insert_SEL3\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLocr_demd_init_SEL1() {
        String    query="";
        query +=" SELECT  XA.GIBU  BRAN_CD  ,  XA.DEPT_NM  ,  DECODE(XB.DEMD_YRMN,  NULL,  NULL,  '완료')  BRAN_END  ,  XB.INS_DAY  FROM  INSA.TCPM_DEPT  XA  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  MAX(DEMD_YRMN)  DEMD_YRMN  ,  TO_CHAR(MAX(INS_DATE),  'YYYYMMDD')  INS_DAY  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  =  #{demdYrmn}  GROUP  BY  BRAN_CD  )  XB  WHERE  XA.DEPT_CD  BETWEEN  '106010100'   \n";
        query +=" AND  '106019999'   \n";
        query +=" AND  XA.GIBU  =  XB.BRAN_CD  (+)  ORDER  BY  BRAN_CD ";
        String xml ="";
            xml += "<select id=\"SQLocr_demd_init_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLrept_closing_SEL1() {
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  END_YEAR  ||  END_MON  =  #{demdYrmn}   \n";
        query +=" AND  BRAN_CD  =  DECODE(#{branCd},  'AL',  BRAN_CD,  #{branCd}) ";
        String xml ="";
            xml += "<select id=\"SQLrept_closing_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "/Users/pp01713/cursor-workspace/query-conveter/app/src/main/resources/";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLocr_demd_insert_SEL2() + "\n";
        content += SQLocr_demd_insert_SEL3() + "\n";
        content += SQLocr_demd_init_SEL1() + "\n";
        content += SQLrept_closing_SEL1() + "\n";
        content += bottom;

        try {
            String fileName = "bra01_s01_1.xml";
            FileWriter fw = new FileWriter(dir + "bra03_s03.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

