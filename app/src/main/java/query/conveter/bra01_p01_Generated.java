package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra01_p01_Generated {

    public static String SQLsel_upso_doc_SEL1() {
        String    query="";
        query +=" SELECT  UPSO_CD  ,  FILE_TYPE  ,  FIDU.GET_CODE_NM('00198',  FILE_TYPE)  AS  FILE_TYPE_NM  ,  FILE_NM  ,  SVR_FILE_NM  ,  SVR_FILE_ROUT  ,  INSPRES_ID  ,  FIDU.GET_STAFF_NM(INSPRES_ID)  AS  INSPRES_NM  ,  INS_DATE  ,  MODPRES_ID  ,  FIDU.GET_STAFF_NM(MODPRES_ID)  AS  MODPRES_NM  ,  MOD_DATE  FROM  GIBU.TBRA_UPSO_DOC_ATTCH  WHERE  UPSO_CD  =  #{upsoCd} ";
        String xml ="";
            xml += "<select id=\"SQLsel_upso_doc_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsel_upso_doc_SEL2() {
        String    query="";
        query +=" SELECT  LISTAGG(B.CODE_NM  ||  ','  ||  (CASE  WHEN  A.FILE_NM  IS  NULL  THEN  'N'  ELSE  'Y'  END),  ',')  WITHIN  GROUP  (ORDER  BY  B.CODE_CD)  AS  RESULT  FROM  GIBU.TBRA_UPSO_DOC_ATTCH  A  ,  FIDU.TENV_CODE  B  WHERE  B.HIGH_CD  =  '00198'   \n";
        query +=" AND  B.CODE_ETC  =  'DI'   \n";
        query +=" AND  B.CODE_CD  =  A.FILE_TYPE(+)   \n";
        query +=" AND  A.UPSO_CD(+)  =  #{upsoCd} ";
        String xml ="";
            xml += "<select id=\"SQLsel_upso_doc_SEL2\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLsel_upso_doc_SEL3() {
        String    query="";
        query +=" SELECT  FIDU.GET_CODE_NM('00198',  EXTRACTVALUE(FILE_TYPE_XML,  '/PivotSet/item[1]/column[1]'))  AS  TYPE1  ,  DECODE(EXTRACTVALUE(FILE_TYPE_XML,  '/PivotSet/item[1]/column[2]'),  0,  'N',  'Y')  AS  FILE_YN1  ,  FIDU.GET_CODE_NM('00198',  EXTRACTVALUE(FILE_TYPE_XML,  '/PivotSet/item[2]/column[1]'))  AS  TYPE2  ,  DECODE(EXTRACTVALUE(FILE_TYPE_XML,  '/PivotSet/item[2]/column[2]'),  0,  'N',  'Y')  AS  FILE_YN2  ,  FIDU.GET_CODE_NM('00198',  EXTRACTVALUE(FILE_TYPE_XML,  '/PivotSet/item[3]/column[1]'))  AS  TYPE3  ,  DECODE(EXTRACTVALUE(FILE_TYPE_XML,  '/PivotSet/item[3]/column[2]'),  0,  'N',  'Y')  AS  FILE_YN3  FROM  (   \n";
        query +=" SELECT  A.FILE_NM  ,  A.FILE_TYPE  FROM  GIBU.TBRA_UPSO_DOC_ATTCH  A  ,  FIDU.TENV_CODE  B  WHERE  B.HIGH_CD  =  '00198'   \n";
        query +=" AND  B.CODE_ETC  =  'DI'   \n";
        query +=" AND  B.CODE_CD  =  A.FILE_TYPE(+)   \n";
        query +=" AND  A.UPSO_CD(+)  =  #{upsoCd}  )  PIVOT  XML  (  COUNT(FILE_NM)  FOR  FILE_TYPE  IN   \n";
        query +=" (SELECT  CODE_CD  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00198'   \n";
        query +=" AND  CODE_ETC  =  'DI')) ";
        String xml ="";
            xml += "<select id=\"SQLsel_upso_doc_SEL3\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLmng_upso_doc_SEL_FILE() {
        String    query="";
        query +=" SELECT  '/upload_file/GIBU/UPSO/'  ||  #{branCd}  AS  DFILEPATH  FROM  DUAL ";
        String xml ="";
            xml += "<select id=\"SQLmng_upso_doc_SEL_FILE\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLmng_upso_doc_SEL31() {
        String    query="";
        query +=" SELECT  SVR_FILE_NM  ,  SVR_FILE_ROUT  FROM  GIBU.TBRA_UPSO_DOC_ATTCH  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  FILE_TYPE  =  #{fileType} ";
        String xml ="";
            xml += "<select id=\"SQLmng_upso_doc_SEL31\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLmng_upso_doc_SEL25() {
        String    query="";
        query +=" SELECT  ''  ||  #{upsoCd}  ||  '-'  ||  #{fileType}  ||  '-'  ||  TO_CHAR(SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR(#{fileNm},  INSTR(#{fileNm},  '.',  '-1'))  AS  DFILENAME  FROM  DUAL ";
        String xml ="";
            xml += "<select id=\"SQLmng_upso_doc_SEL25\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLmng_upso_doc_DEL40() {
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_DOC_ATTCH  \n";
        query +=" where UPSO_CD=#{upsoCd}  \n";
        query +=" and FILE_TYPE=#{fileType}";
        String xml ="";
            xml += "<delete id=\"SQLmng_upso_doc_DEL40\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLmng_upso_doc_SEL33() {
        String    query="";
        query +=" SELECT  ''  ||  #{upsoCd}  ||  '-'  ||  #{fileType}  ||  '-'  ||  TO_CHAR(SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR(#{fileNm},  INSTR(#{fileNm},  '.',  '-1'))  AS  DFILENAME  FROM  DUAL ";
        String xml ="";
            xml += "<select id=\"SQLmng_upso_doc_SEL33\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLmng_upso_doc_INS31() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_DOC_ATTCH (INS_DATE, INSPRES_ID, UPSO_CD, SVR_FILE_ROUT, SVR_FILE_NM, FILE_TYPE, FILE_NM)  \n";
        query +=" values(SYSDATE, #{inspresId} , #{upsoCd} , #{svrFileRout} , #{svrFileNm} , #{fileType} , #{fileNm} )";
        String xml ="";
            xml += "<insert id=\"SQLmng_upso_doc_INS31\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLmng_upso_doc_UPD36() {
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_DOC_ATTCH  \n";
        query +=" set MODPRES_ID=#{modpresId} , MOD_DATE=SYSDATE , SVR_FILE_ROUT=#{svrFileRout} , SVR_FILE_NM=#{svrFileNm} , FILE_NM=#{fileNm}  \n";
        query +=" where UPSO_CD=#{upsoCd}  \n";
        query +=" and FILE_TYPE=#{fileType}";
        String xml ="";
            xml += "<update id=\"SQLmng_upso_doc_UPD36\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "C:\\komca\\workspace\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLsel_upso_doc_SEL1() + "\n";
        content += SQLsel_upso_doc_SEL2() + "\n";
        content += SQLsel_upso_doc_SEL3() + "\n";
        content += SQLmng_upso_doc_SEL_FILE() + "\n";
        content += SQLmng_upso_doc_SEL31() + "\n";
        content += SQLmng_upso_doc_SEL25() + "\n";
        content += SQLmng_upso_doc_DEL40() + "\n";
        content += SQLmng_upso_doc_SEL33() + "\n";
        content += SQLmng_upso_doc_INS31() + "\n";
        content += SQLmng_upso_doc_UPD36() + "\n";
        content += bottom;

        try {
            String fileName = "bra01_s01_1.xml";
            FileWriter fw = new FileWriter(dir + "bra01_p01.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

