package query.conveter;

import java.io.FileWriter;
import java.io.IOException;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

public class bra04_s21_Generated {

    public static String SQLsel_card_req_SEL1() {
        String    query="";
        query +=" SELECT A.BRAN_CD , GIBU.GET_BRAN_NM(A.BRAN_CD) AS BRAN_NM , A.STAFF_CD , FIDU.GET_STAFF_NM(A.STAFF_CD) AS STAFF_NM , A.INS_DAY , A.INS_NUM , A.UPSO_CD , B.UPSO_NM , A.NONPY_TERM , A.NONPY_AMT , A.CARD_GBN , A.CARD_NUM , A.TERM_YRMN , A.INSTP_MONTH_FREQ , A.START_YRMN || '01' AS START_YRMN , A.END_YRMN || '01' AS END_YRMN , A.PAY_AMT , A.PAY_DAY , A.APPRV_NUM , A.REPT_DAY , A.REPT_AMT , A.COMIS , A.REMAK , (CASE WHEN C.UPSO_CD IS NOT NULL THEN 'Y' ELSE 'N' END) AS REPT_YN  ";
        query +=" FROM GIBU.TBRA_REPT_CARD_REQ A , GIBU.TBRA_UPSO B , GIBU.TBRA_REPT_CARD C  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.BRAN_CD = DECODE(#{branCd}, 'AL', A.BRAN_CD, #{branCd})  ";
        query +=" AND A.STAFF_CD = NVL(#{staffCd}, A.STAFF_CD)  ";
        query +=" AND A.CARD_GBN = NVL(#{cardGbn}, A.CARD_GBN)  ";
        query +=" AND A.INS_DAY = SUBSTR(C.REMAK(+), 1, 8)  ";
        query +=" AND A.INS_NUM = SUBSTR(C.REMAK(+), 10, 4)  ";
            query +=" AND A.INS_DAY BETWEEN #{insStart}  ";
            query +=" AND #{insEnd}  ";
            query +=" AND A.PAY_DAY BETWEEN #{paytrmStartDay}  ";
            query +=" AND #{paytrmEndDay}  ";
            query +=" AND A.REPT_DAY BETWEEN #{reptFrom}  ";
            query +=" AND #{reptTo}  ";
        query +=" ORDER BY INS_DAY, INS_NUM  ";
        String xml ="";
            xml += "<select id=\"SQLsel_card_req_SEL1\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLmng_card_req_DEL7() {
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_CARD_REQ  \n";
        query +=" where INS_NUM=#{insNum}  \n";
        query +=" and INS_DAY=#{insDay}";
        String xml ="";
            xml += "<delete id=\"SQLmng_card_req_DEL7\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</delete>\n" ;

        return xml;

    }

    public static String SQLmng_card_req_UPD6() {
        String    query="";
        query +=" Update GIBU.TBRA_REPT_CARD_REQ  \n";
        query +=" set MODPRES_ID=#{modpresId} , COMIS=#{comis} , REPT_DAY=#{reptDay} , TERM_YRMN=#{termYrmn} , PAY_DAY=#{payDay} , START_YRMN=#{startYrmn} , CARD_GBN=#{cardGbn} , INSTP_MONTH_FREQ=#{instpMonthFreq} , NONPY_TERM=#{nonpyTerm} , UPSO_CD=#{upsoCd} , MOD_DATE=SYSDATE , REPT_AMT=#{reptAmt} , CARD_NUM=#{cardNum} , END_YRMN=#{endYrmn} , PAY_AMT=#{payAmt} , REMAK=#{remak} , APPRV_NUM=#{apprvNum} , NONPY_AMT=#{nonpyAmt}  \n";
        query +=" where INS_DAY=#{insDay}  \n";
        query +=" and INS_NUM=#{insNum}";
        String xml ="";
            xml += "<update id=\"SQLmng_card_req_UPD6\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</update>\n" ;

        return xml;

    }

    public static String SQLmng_card_req_SEL8() {
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(TO_NUMBER(INS_NUM)),  0)  +  1,  4,  '0')  AS  INS_NUM  FROM  GIBU.TBRA_REPT_CARD_REQ  WHERE  INS_DAY  =  TO_CHAR(SYSDATE,  'YYYYMMDD') ";
        String xml ="";
            xml += "<select id=\"SQLmng_card_req_SEL8\" parameterType=\"\" resultType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</select>\n" ;

        return xml;

    }

    public static String SQLmng_card_req_INS5() {
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_CARD_REQ (COMIS, REPT_DAY, INSPRES_ID, TERM_YRMN, INS_DAY, PAY_DAY, START_YRMN, INS_DATE, CARD_GBN, STAFF_CD, INSTP_MONTH_FREQ, NONPY_TERM, INS_NUM, UPSO_CD, REPT_AMT, CARD_NUM, PAY_AMT, BRAN_CD, END_YRMN, REMAK, APPRV_NUM, NONPY_AMT)  \n";
        query +=" values(#{comis} , #{reptDay} , #{inspresId} , #{termYrmn} , #{insDay} , #{payDay} , #{startYrmn} , SYSDATE, #{cardGbn} , #{staffCd} , #{instpMonthFreq} , #{nonpyTerm} , #{insNum} , #{upsoCd} , #{reptAmt} , #{cardNum} , #{payAmt} , #{branCd} , #{endYrmn} , #{remak} , #{apprvNum} , #{nonpyAmt} )";
        String xml ="";
            xml += "<insert id=\"SQLmng_card_req_INS5\" parameterType=\"\">\n";
        xml += SqlFormatter.format(query, FormatConfig.builder().indent("\t").build());
            xml += "\n</insert>\n" ;

        return xml;

    }

    public static void main(String[] args) {
        String dir = "D:\\kosmos\\query-conveter\\app\\src\\main\\resources\\";
        String top = "<mapper namespace=\"kr.or.komca.center..mapper.Mapper\">";
        String bottom = "</mapper>";

        String content = top + "\n";    
        content += SQLsel_card_req_SEL1() + "\n";
        content += SQLmng_card_req_DEL7() + "\n";
        content += SQLmng_card_req_UPD6() + "\n";
        content += SQLmng_card_req_SEL8() + "\n";
        content += SQLmng_card_req_INS5() + "\n";
        content += bottom;

        try {
            String fileName = "bra01_s01_1.xml";
            FileWriter fw = new FileWriter(dir + "bra04_s21.xml");
            fw.write(new String(content.getBytes(), "UTF-8"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

