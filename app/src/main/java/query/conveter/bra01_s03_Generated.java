package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra01_s03_Generated {

    public static String SQLupso_amt_change_DEL22() {
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" where CHG_BRAN=#{chgBran}  \n";
        query +=" and CHG_NUM=#{chgNum}  \n";
        query +=" and CHG_DAY=#{chgDay}  \n";
        query +=" and UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<delete id=\"SQLupso_amt_change_DEL22\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_UPD7() {
        String    query="";
        query +=" Update  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" set MODPRES_ID=#{modpresId} , APPL_DAY=#{applDay} , UPSO_GRAD=#{upsoGrad} , USE_TIME=#{useTime} , MCHNDAESU=#{mchndaesu} , MONPRNCFEE=#{monprncfee} , BSTYP_CD=#{bstypCd} , MONPRNCFEE2=#{monprncfee2} , MOD_DATE=SYSDATE , REMAK=#{remak}  \n";
        query +=" where CHG_BRAN=#{chgBran}  \n";
        query +=" and CHG_DAY=#{chgDay}  \n";
        query +=" and CHG_NUM=#{chgNum}  \n";
        query +=" and UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_amt_change_UPD7\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_SEL7() {
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(CHG_NUM),  0)  +  1,  4,  '0')  CHG_NUM  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  CHG_BRAN  =  #{chgBran}   \n";
        query +=" AND  CHG_DAY  =  #{chgDay}   \n";
        query +=" AND  UPSO_CD  =  #{upsoCd} ";
        String xml ="";
            xml += "<select id=\"SQLupso_amt_change_SEL7\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_INS6() {
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSORTAL_INFO (JOIN_SEQ, APPL_DAY, CHG_BRAN, CHG_DAY, INSPRES_ID, UPSO_GRAD, USE_TIME, MCHNDAESU, MONPRNCFEE, BSTYP_CD, MONPRNCFEE2, INS_DATE, CHG_NUM, UPSO_CD, REMAK)  \n";
        query +=" values((SELECT NVL(MAX(JOIN_SEQ), 0) + 1 JOIN_SEQ FROM GIBU.TBRA_UPSORTAL_INFO ), #{applDay} , #{chgBran} , #{chgDay} , #{inspresId} , #{upsoGrad} , #{useTime} , #{mchndaesu} , #{monprncfee} , #{bstypCd} , #{monprncfee2} , SYSDATE, #{chgNum} , #{upsoCd} , #{remak} )";
        String xml ="";
            xml += "<insert id=\"SQLupso_amt_change_INS6\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_DEL24() {
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" where CHG_NUM=#{chgNum}  \n";
        query +=" and CHG_DAY=#{chgDay}  \n";
        query +=" and UPSO_CD=#{upsoCd}  \n";
        query +=" and BSTYP_CD=#{bstypCd}";
        String xml ="";
            xml += "<delete id=\"SQLupso_amt_change_DEL24\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_UPD42() {
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=#{modpresId} , MCHNDAESU=#{mchndaesu} , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_amt_change_UPD42\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_SEL26() {
        String    query="";
        query +=" SELECT  MCHNDAESU  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  JOIN_SEQ  =  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  #{upsoCd}  ) ";
        String xml ="";
            xml += "<select id=\"SQLupso_amt_change_SEL26\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_UPD40() {
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=#{modpresId} , MCHNDAESU=#{mchndaesu} , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_amt_change_UPD40\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_UPD25() {
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=#{modpresId} , MCHNDAESU=#{mchndaesu} , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=#{upsoCd}";
        String xml ="";
            xml += "<update id=\"SQLupso_amt_change_UPD25\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_DEL34() {
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" where CHG_BRAN=#{chgBran}  \n";
        query +=" and CHG_NUM=#{chgNum}  \n";
        query +=" and CHG_DAY=#{chgDay}  \n";
        query +=" and UPSO_CD=#{upsoCd}  \n";
        query +=" and GRAD_NUM=#{gradNum}";
        String xml ="";
            xml += "<delete id=\"SQLupso_amt_change_DEL34\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_SEL29() {
        String    query="";
        query +=" SELECT  CASE  WHEN  APPL_DAY  <  '20130601'  THEN   \n";
        query +=" (SELECT  STNDAMT  FROM  GIBU.TBRA_BSTYPGRAD_TEMP2013  WHERE  BSTYP_CD  =  #{bstypCd}   \n";
        query +=" AND  GRAD_GBN  =  #{gradGbn})  ELSE   \n";
        query +=" (SELECT  STNDAMT  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  #{bstypCd}   \n";
        query +=" AND  GRAD_GBN  =  #{gradGbn})END  STNDAMT  ,   \n";
        query +=" (SELECT  /*+  INDEX_DESC(TBRA_FEERATE_HISTY  TBRA_FEERATE_HISTY_IDX_PK)  */  RATE  FROM  GIBU.TBRA_FEERATE_HISTY  WHERE  APPL_DAY  <=  A.APPL_DAY   \n";
        query +=" AND  BSTYP_CD  =  GIBU.FT_GET_BSTYP_INFO(#{upsoCd})   \n";
        query +=" AND  ROWNUM  =  1)  RATE  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  CHG_DAY  =  #{chgDay}   \n";
        query +=" AND  CHG_NUM  =  #{chgNum}   \n";
        query +=" AND  CHG_BRAN  =  #{chgBran} ";
        String xml ="";
            xml += "<select id=\"SQLupso_amt_change_SEL29\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_SEL28() {
        String    query="";
        query +=" SELECT  CASE  WHEN  APPL_DAY  <  '20130601'  THEN   \n";
        query +=" (SELECT  STNDAMT  FROM  GIBU.TBRA_BSTYPGRAD_TEMP2013  WHERE  BSTYP_CD  =  #{bstypCd}   \n";
        query +=" AND  GRAD_GBN  =  #{gradGbn})  ELSE   \n";
        query +=" (SELECT  STNDAMT  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  #{bstypCd}   \n";
        query +=" AND  GRAD_GBN  =  #{gradGbn})END  STNDAMT  ,   \n";
        query +=" (SELECT  /*+  INDEX_DESC(TBRA_FEERATE_HISTY  TBRA_FEERATE_HISTY_IDX_PK)  */  RATE  FROM  GIBU.TBRA_FEERATE_HISTY  WHERE  APPL_DAY  <=  A.APPL_DAY   \n";
        query +=" AND  BSTYP_CD  =  GIBU.FT_GET_BSTYP_INFO(#{upsoCd})   \n";
        query +=" AND  ROWNUM  =  1)  RATE  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  CHG_DAY  =  #{chgDay}   \n";
        query +=" AND  CHG_NUM  =  #{chgNum}   \n";
        query +=" AND  CHG_BRAN  =  #{chgBran} ";
        String xml ="";
            xml += "<select id=\"SQLupso_amt_change_SEL28\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_INS33() {
        String    query="";
        query +=" Insert into GIBU.TBRA_NOREBANG_INFO (INS_DATE, CHG_BRAN, INSPRES_ID, CHG_NUM, CHG_DAY, STNDAMT, MCHNDAESU, AREA, UPSO_CD, BSTYP_CD, GRAD_GBN, GRAD_NUM)  \n";
        query +=" values(SYSDATE, #{chgBran} , #{inspresId} , #{chgNum} , #{chgDay} , #{stndamt} , #{mchndaesu} , #{area} , #{upsoCd} , #{bstypCd} , #{gradGbn} , (SELECT NVL(MAX(GRAD_NUM), 0) + 1 GRAD_NUM FROM GIBU.TBRA_NOREBANG_INFO WHERE UPSO_CD = #{gradNum1}))";
        String xml ="";
            xml += "<insert id=\"SQLupso_amt_change_INS33\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_UPD35() {
        String    query="";
        query +=" Update  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" set MODPRES_ID=#{modpresId} , STNDAMT=#{stndamt} , MCHNDAESU=#{mchndaesu} , AREA=#{area} , MOD_DATE=SYSDATE , BSTYP_CD=#{bstypCd} , GRAD_GBN=#{gradGbn}  \n";
        query +=" where CHG_BRAN=#{chgBran}  \n";
        query +=" and CHG_NUM=#{chgNum}  \n";
        query +=" and CHG_DAY=#{chgDay}  \n";
        query +=" and UPSO_CD=#{upsoCd}  \n";
        query +=" and GRAD_NUM=#{gradNum}";
        String xml ="";
            xml += "<update id=\"SQLupso_amt_change_UPD35\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_XIUD30() {
        String    query="";
        query +=" UPDATE  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" SET MONPRNCFEE2 = (SELECT SUM(STNDAMT * NVL(MCHNDAESU,0)) FROM  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}  \n";
        query +=" AND CHG_DAY = #{chgDay}  \n";
        query +=" AND CHG_NUM = #{chgNum}  \n";
        query +=" AND CHG_BRAN = #{chgBran}) , MONPRNCFEE = (SELECT TRUNC(SUM(STNDAMT * NVL(MCHNDAESU,0)) * 0.01 * #{rate}, -1) FROM  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}  \n";
        query +=" AND CHG_DAY = #{chgDay}  \n";
        query +=" AND CHG_NUM = #{chgNum}  \n";
        query +=" AND CHG_BRAN = #{chgBran})  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}  \n";
        query +=" AND CHG_DAY = #{chgDay}  \n";
        query +=" AND CHG_NUM = #{chgNum}  \n";
        query +=" AND CHG_BRAN = #{chgBran}";
        String xml ="";
            xml += "<update id=\"SQLupso_amt_change_XIUD30\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_XIUD31() {
        String    query="";
        query +=" UPDATE  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" SET MONPRNCFEE2 = (SELECT SUM(STNDAMT * NVL(MCHNDAESU,0)) FROM  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}  \n";
        query +=" AND CHG_DAY = #{chgDay}  \n";
        query +=" AND CHG_NUM = #{chgNum}  \n";
        query +=" AND CHG_BRAN = #{chgBran}) , MONPRNCFEE = (SELECT TRUNC(SUM(STNDAMT * NVL(MCHNDAESU,0)) * 0.01 * #{rate},-1) FROM  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}  \n";
        query +=" AND CHG_DAY = #{chgDay}  \n";
        query +=" AND CHG_NUM = #{chgNum}  \n";
        query +=" AND CHG_BRAN = #{chgBran})  \n";
        query +=" WHERE UPSO_CD = #{upsoCd}  \n";
        query +=" AND CHG_DAY = #{chgDay}  \n";
        query +=" AND CHG_NUM = #{chgNum}  \n";
        query +=" AND CHG_BRAN = #{chgBran}";
        String xml ="";
            xml += "<update id=\"SQLupso_amt_change_XIUD31\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_SEL9() {
        String    query="";
        query +=" SELECT  ROWNUM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  ,  TRIM(A.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  TRIM(A.BSTYP_CD)  BSTYP_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.MONPRNCFEE2  ,  A.APPL_DAY  ,  A.MCHNDAESU  ,  A.REMAK  ,  A.UPSO_CD  ,  A.USE_TIME  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =#{upsoCd}  ORDER  BY  A.CHG_DAY,  A.CHG_NUM ";
        String xml ="";
            xml += "<select id=\"SQLupso_amt_change_SEL9\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_amt_change_SEL19() {
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(A.BSTYP_CD)  ||  A.GRAD_GBN  GRAD  ,  TRIM(A.BSTYP_CD)  BSTYP_CD  ,  A.GRAD_GBN  ,  A.AREA  ,  A.MCHNDAESU  ,  A.STNDAMT  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  ,  A.GRAD_NUM  ,  (A.MCHNDAESU  *  A.STNDAMT)  AMT  FROM  GIBU.TBRA_NOREBANG_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  A.GRAD_GBN  =  B.GRAD_GBN   \n";
        query +=" AND  A.CHG_DAY  =  #{chgDay}   \n";
        query +=" AND  A.CHG_NUM  =  #{chgNum}   \n";
        query +=" AND  A.CHG_BRAN  =  #{chgBran} ";
        String xml ="";
            xml += "<select id=\"SQLupso_amt_change_SEL19\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLupso_noraebang_select_SEL1() {
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(A.BSTYP_CD)  ||  A.GRAD_GBN  GRAD  ,  TRIM(A.BSTYP_CD  )  BSTYP_CD  ,  A.GRAD_GBN  ,  A.AREA  ,  A.MCHNDAESU  ,  A.STNDAMT  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  ,  A.MCHNDAESU  *  A.STNDAMT  AMT  ,  A.GRAD_NUM  FROM  GIBU.TBRA_NOREBANG_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  #{upsoCd}   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  A.GRAD_GBN  =  B.GRAD_GBN   \n";
        query +=" AND  A.CHG_DAY  =  #{chgDay}   \n";
        query +=" AND  A.CHG_NUM  =  #{chgNum}   \n";
        query +=" AND  A.CHG_BRAN  =  #{chgBran} ";
        String xml ="";
            xml += "<select id=\"SQLupso_noraebang_select_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLfeerate_select_SEL1() {
        String    query="";
        query +=" SELECT  /*+  INDEX_DESC(TBRA_FEERATE_HISTY  TBRA_FEERATE_HISTY_IDX_PK)  */  RATE  FROM  GIBU.TBRA_FEERATE_HISTY  WHERE  APPL_DAY  <=  #{applDay}   \n";
        query +=" AND  BSTYP_CD  =  GIBU.FT_GET_BSTYP_INFO(#{upsoCd})   \n";
        query +=" AND  ROWNUM  =  1 ";
        String xml ="";
            xml += "<select id=\"SQLfeerate_select_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "D:\\kosmos\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLupso_amt_change_DEL22() + "\n";
        content += SQLupso_amt_change_UPD7() + "\n";
        content += SQLupso_amt_change_SEL7() + "\n";
        content += SQLupso_amt_change_INS6() + "\n";
        content += SQLupso_amt_change_DEL24() + "\n";
        content += SQLupso_amt_change_UPD42() + "\n";
        content += SQLupso_amt_change_SEL26() + "\n";
        content += SQLupso_amt_change_UPD40() + "\n";
        content += SQLupso_amt_change_UPD25() + "\n";
        content += SQLupso_amt_change_DEL34() + "\n";
        content += SQLupso_amt_change_SEL29() + "\n";
        content += SQLupso_amt_change_SEL28() + "\n";
        content += SQLupso_amt_change_INS33() + "\n";
        content += SQLupso_amt_change_UPD35() + "\n";
        content += SQLupso_amt_change_XIUD30() + "\n";
        content += SQLupso_amt_change_XIUD31() + "\n";
        content += SQLupso_amt_change_SEL9() + "\n";
        content += SQLupso_amt_change_SEL19() + "\n";
        content += SQLupso_noraebang_select_SEL1() + "\n";
        content += SQLfeerate_select_SEL1() + "\n";
        content += bottom;

        try {
            FileWriter fw = new FileWriter(dir + "bra01_s03.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

