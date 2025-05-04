package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra01_r16_Generated {

    public static String SQLcng_karaoke_sel_SEL1() {
        String    query="";
        query +=" SELECT  ZB.BRAN_CD  ,  GIBU.GET_BRAN_NM(ZB.BRAN_CD)  AS  BRAN_NM  ,  ZB.UPSO_CD  ,  ZB.UPSO_NM  ,  ZC.SERIAL_NO  ,  ZC.CO_STATUS  ,  ZE.CO_NAME  ,  FIDU.GET_STAFF_NM(ZB.STAFF_CD)  AS  STAFF_NM  FROM  (   \n";
        query +=" SELECT  UPSO_CD  FROM  (   \n";
        query +=" SELECT  /*+  PARALLEL(TA)  */  TA.UPSO_CD,  KARAOKE_NO  FROM  LOG.KDS_STATISTICS  TA  ,  GIBU.TBRA_UPSO  TB  WHERE  PLAY_SDATE  BETWEEN  TO_DATE(#{yrmn}  ||  '01000000',  'YYYYMMDDHH24MISS')   \n";
        query +=" AND  TO_DATE(TO_CHAR(LAST_DAY(TO_DATE(#{yrmn},  'YYYYMM')),  'YYYYMMDD')  ||  '235959',  'YYYYMMDDHH24MISS')   \n";
        query +=" AND  TA.UPSO_CD  =  TB.UPSO_CD   \n";
        query +=" AND  TB.BRAN_CD  =  DECODE(#{branCd},  'AL',  TB.BRAN_CD,  #{branCd})  GROUP  BY  TA.UPSO_CD,  KARAOKE_NO  )  GROUP  BY  UPSO_CD  HAVING  COUNT(1)  >  1  )  ZA  ,  GIBU.TBRA_UPSO  ZB  ,  LOG.KDS_SHOPROOM  ZC  ,  LOG.KDS_CODE  ZE  WHERE  ZA.UPSO_CD  =  ZB.UPSO_CD   \n";
        query +=" AND  ZC.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.SEQ  =   \n";
        query +=" (SELECT  MAX(SEQ)  FROM  LOG.KDS_SHOPROOM  WHERE  UPSO_CD  =  ZA.UPSO_CD)   \n";
        query +=" AND  ZE.CO_TYPE  ||  ZE.CO_CODE  =  ZC.CO_STATUS  ORDER  BY  CO_STATUS,  BRAN_CD,  UPSO_CD ";
        String xml ="";
            xml += "<select id=\"SQLcng_karaoke_sel_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLdtl_karaoke_sel_SEL1() {
        String    query="";
        query +=" SELECT  UPSO_CD,  ROOM_NAME,  SERIAL_NO,   \n";
        query +=" (SELECT  CO_NAME  FROM  LOG.KDS_CODE  WHERE  CO_TYPE  ||  CO_CODE  =  A.CO_STATUS)  AS  CO_NAME,  REG_DATE  FROM  LOG.KDS_SHOPROOM  A  WHERE  SERIAL_NO  =  #{serialNo}   \n";
        query +=" AND  SEQ  >=   \n";
        query +=" (SELECT  MIN(SEQ)  FROM  LOG.KDS_SHOPROOM  WHERE  SERIAL_NO  =  #{serialNo}   \n";
        query +=" AND  UPSO_CD  =  #{upsoCd})  ORDER  BY  SEQ ";
        String xml ="";
            xml += "<select id=\"SQLdtl_karaoke_sel_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLdtl_karaoke_sel_SEL4() {
        String    query="";
        query +=" SELECT  /*+  PARALLEL(A)  */  UPSO_CD,  SERIAL_NO,  KARAOKE_NO,  MIN(PLAY_SDATE)  AS  MIN_PLAY,  MAX(PLAY_SDATE)  AS  MAX_PLAY  FROM  LOG.KDS_STATISTICS  A  WHERE  UPSO_CD  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  LOG.KDS_SHOPROOM  A  WHERE  SERIAL_NO  =  #{serialNo}   \n";
        query +=" AND  SEQ  >=   \n";
        query +=" (SELECT  MIN(SEQ)  FROM  LOG.KDS_SHOPROOM  WHERE  SERIAL_NO  =  #{serialNo}   \n";
        query +=" AND  UPSO_CD  =  #{upsoCd}))   \n";
        query +=" AND  PLAY_SDATE  BETWEEN  TO_DATE(#{yrmn}  ||  '01000000',  'YYYYMMDDHH24MISS')   \n";
        query +=" AND  TO_DATE(TO_CHAR(LAST_DAY(TO_DATE(#{yrmn},  'YYYYMM')),  'YYYYMMDD')  ||  '235959',  'YYYYMMDDHH24MISS')  GROUP  BY  UPSO_CD,  SERIAL_NO,  KARAOKE_NO  ORDER  BY  3,4 ";
        String xml ="";
            xml += "<select id=\"SQLdtl_karaoke_sel_SEL4\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "C:\\komca\\workspace\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLcng_karaoke_sel_SEL1() + "\n";
        content += SQLdtl_karaoke_sel_SEL1() + "\n";
        content += SQLdtl_karaoke_sel_SEL4() + "\n";
        content += bottom;

        try {
            String fileName = "bra01_s01_1.xml";
            FileWriter fw = new FileWriter(dir + "bra01_r16.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

