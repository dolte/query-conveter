
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra08_r09
{
    public bra08_r09()
    {
    }
    //##**$$sel_gov_compare
    /*
    */
    public DOBJ CTLsel_gov_compare(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            if( dobj.getRetObject("S").getRecord().get("SEARCH_TYPE").equals("1"))
            {
                dobj  = CALLsel_gov_compare_SEL3(Conn, dobj);           //  노래방 비교조회
            }
            else
            {
                dobj  = CALLsel_gov_compare_SEL4(Conn, dobj);           //  유흥/단란 비교조회
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
            }
            catch(Exception re)
            {
                re.printStackTrace();
            }
        }
        finally
        {
            try
            {
                Conn.close();
            }
            catch(SQLException e)
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e,"DataBase Close Error");
                e.printStackTrace();
            }
        }
        return dobj;
    }
    public DOBJ CTLsel_gov_compare( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("SEARCH_TYPE").equals("1"))
        {
            dobj  = CALLsel_gov_compare_SEL3(Conn, dobj);           //  노래방 비교조회
        }
        else
        {
            dobj  = CALLsel_gov_compare_SEL4(Conn, dobj);           //  유흥/단란 비교조회
        }
        return dobj;
    }
    // 노래방 비교조회
    public DOBJ CALLsel_gov_compare_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_gov_compare_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_gov_compare_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ROW_NUMBER()  OVER(ORDER  BY  SORT_CD,  ADDR)  AS  SEQ  ,  UPSO_CD  ,  UPSO_NM  ,  INS_DATE  ,  OPBI_DAY  ,  ADDR  ,  DONG  ,  GRADNM  ,  NOREBANG_GRAD  ,  STAFF_NM  ,  MCHNDAESU  ,  ROOM_CNT  ,  MATCH_YN  ,  DECODE(MCHNDAESU,  ROOM_CNT,  'O',  'X')  AS  COMPARE  ,  (  CASE  WHEN  ROOM_CNT  IS  NULL  THEN  '확인불가'  WHEN  MCHNDAESU  <  ROOM_CNT  THEN  'O'  ELSE  'X'  END  )  AS  LOSS_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_NM  AS  UPSO_NM  ,  A.UPSO_CD  AS  UPSO_CD  ,  TO_CHAR  (INS_DATE,  'YYYYMMDD')  AS  INS_DATE  ,  A.OPBI_DAY  AS  OPBI_DAY  ,  A.UPSO_NEW_ADDR1||'  '||A.UPSO_NEW_ADDR2  AS  ADDR  ,  (   \n";
        query +=" SELECT  PREFEC_NM  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  A.UPSO_BD_MNG_NUM  )  AS  DONG  ,  (   \n";
        query +=" SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  C.BSTYP_CD   \n";
        query +=" AND  GRAD_GBN  =  C.UPSO_GRAD  )  AS  GRADNM  ,  DECODE  (TRIM  (C.BSTYP_CD),  'o',  GIBU.FT_GET_NOREBANG_GRAD  (A.UPSO_CD),  '')  AS  NOREBANG_GRAD  ,  FIDU.GET_STAFF_NM  (A.STAFF_CD)  AS  STAFF_NM  ,  A.MCHNDAESU  AS  MCHNDAESU  ,  B.ROOM_CNT  AS  ROOM_CNT  ,  B.SITE_AREA  AS  SITE_AREA  ,  DECODE  (B.UPSO_CD,  NULL,  '미매칭',  '매칭')  AS  MATCH_YN  ,  (  CASE  WHEN  ROOM_CNT  =  MCHNDAESU  THEN  4  WHEN  ROOM_CNT  IS  NULL  THEN  3  WHEN  MCHNDAESU  <  ROOM_CNT  THEN  1  ELSE  2  END  )  SORT_CD  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  BIGO  AS  UPSO_CD,  TO_NUMBER(NOROOM_CNT)  AS  ROOM_CNT,  FACIL_AR  AS  SITE_AREA,  ADDR1  FROM  GIBU.TGOV_NOREBANG  ZA  WHERE  STAT_GBN  =  '1'   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD'))   \n";
        query +=" AND  APV_PERM_YMD  =   \n";
        query +=" (SELECT  MAX  (APV_PERM_YMD)  FROM  GIBU.TGOV_NOREBANG  WHERE  STAT_GBN  =  '1'   \n";
        query +=" AND  BIGO  =  ZA.BIGO   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD')))  UNION  ALL   \n";
        query +=" SELECT  BIGO  AS  UPSO_CD  ,  0  AS  ROOM_CNT  ,  REPLACE  (REPLACE  (NVL  (FACIL_TOT_SCP,  SITE_AREA),  '.00',  ''),  ',',  '')  AS  SITE_AREA  ,  ADDR1  FROM  GIBU.TGOV_DANRAN  ZB  WHERE  STAT_GBN  =  '1'   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD'))   \n";
        query +=" AND  APV_PERM_YMD  =   \n";
        query +=" (SELECT  MAX  (APV_PERM_YMD)  FROM  GIBU.TGOV_DANRAN  WHERE  STAT_GBN  =  '1'   \n";
        query +=" AND  BIGO  =  ZB.BIGO   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD')))  UNION  ALL   \n";
        query +=" SELECT  BIGO  AS  UPSO_CD  ,  0  AS  ROOM_CNT  ,  REPLACE  (REPLACE  (NVL  (FACIL_TOT_SCP,  SITE_AREA),  '.00',  ''),  ',',  '')  AS  SITE_AREA  ,  ADDR1  FROM  GIBU.TGOV_YUHEONG  ZC  WHERE  STAT_GBN  =  '1'   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD'))   \n";
        query +=" AND  APV_PERM_YMD  =   \n";
        query +=" (SELECT  MAX  (APV_PERM_YMD)  FROM  GIBU.TGOV_YUHEONG  WHERE  STAT_GBN  =  '1'   \n";
        query +=" AND  BIGO  =  ZC.BIGO   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD')))  )  B  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.MONPRNCFEE  ,  ZC.GRADNM  ,  ZB.BSTYP_CD  ,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX  (A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  NVL(:BRAN_CD,  B.BRAN_CD)   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NULL  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  C.BSTYP_CD  =  'o'   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  A.BRAN_CD  =  NVL(:BRAN_CD,  A.BRAN_CD)  UNION   \n";
        query +=" SELECT  A.UPSO_NM  AS  UPSO_NM  ,  A.UPSO_CD  AS  UPSO_CD  ,  TO_CHAR  (INS_DATE,  'YYYYMMDD')  AS  INS_DATE  ,  A.OPBI_DAY  AS  OPBI_DAY  ,  A.UPSO_NEW_ADDR1||'  '||A.UPSO_NEW_ADDR2  AS  ADDR  ,  (   \n";
        query +=" SELECT  PREFEC_NM  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  A.UPSO_BD_MNG_NUM  )  AS  DONG  ,  (   \n";
        query +=" SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  C.BSTYP_CD   \n";
        query +=" AND  GRAD_GBN  =  C.UPSO_GRAD  )  AS  GRADNM  ,  DECODE  (TRIM  (C.BSTYP_CD),  'o',  GIBU.FT_GET_NOREBANG_GRAD  (A.UPSO_CD),  '')  AS  NOREBANG_GRAD  ,  FIDU.GET_STAFF_NM  (A.STAFF_CD)  AS  STAFF_NM  ,  A.MCHNDAESU  AS  MCHNDAESU  ,  B.ROOM_CNT  AS  ROOM_CNT  ,  B.SITE_AREA  AS  SITE_AREA  ,  '주소매칭'  AS  MATCH_YN  ,  (  CASE  WHEN  ROOM_CNT  =  MCHNDAESU  THEN  4  WHEN  ROOM_CNT  IS  NULL  THEN  3  WHEN  MCHNDAESU  <  ROOM_CNT  THEN  1  ELSE  2  END  )  SORT_CD  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  BIGO  AS  UPSO_CD,  TO_NUMBER(NOROOM_CNT)  AS  ROOM_CNT,  FACIL_AR  AS  SITE_AREA,  TRIM(ADDR1)  AS  ADDR1  FROM  GIBU.TGOV_NOREBANG  ZA  WHERE  LENGTH(NVL(BIGO,'X'))  <>  '8'   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD'))  UNION  ALL   \n";
        query +=" SELECT  BIGO  AS  UPSO_CD  ,  0  AS  ROOM_CNT  ,  REPLACE  (REPLACE  (NVL  (FACIL_TOT_SCP,  SITE_AREA),  '.00',  ''),  ',',  '')  AS  SITE_AREA  ,  TRIM(ADDR1)  AS  ADDR1  FROM  GIBU.TGOV_DANRAN  ZB  WHERE  LENGTH(NVL(BIGO,'X'))  <>  '8'   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD'))  UNION  ALL   \n";
        query +=" SELECT  BIGO  AS  UPSO_CD  ,  0  AS  ROOM_CNT  ,  REPLACE  (REPLACE  (NVL  (FACIL_TOT_SCP,  SITE_AREA),  '.00',  ''),  ',',  '')  AS  SITE_AREA  ,  TRIM(ADDR1)  AS  ADDR1  FROM  GIBU.TGOV_YUHEONG  ZC  WHERE  LENGTH(NVL(BIGO,'X'))  <>  '8'   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD'))  )  B  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.MONPRNCFEE  ,  ZC.GRADNM  ,  ZB.BSTYP_CD  ,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX  (A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  NVL(:BRAN_CD,  B.BRAN_CD)   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NULL  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  C  WHERE  TRIM(A.MNGEMSTR_NEW_ADDR1)  =  B.ADDR1   \n";
        query +=" AND  B.UPSO_CD  IS  NULL   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  C.BSTYP_CD  =  'o'   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  A.BRAN_CD  =  NVL(:BRAN_CD,  A.BRAN_CD)  )  X ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 유흥/단란 비교조회
    public DOBJ CALLsel_gov_compare_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_gov_compare_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_gov_compare_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ROW_NUMBER()  OVER(  ORDER  BY  (  CASE  WHEN  GRAD  =  GRADNM  THEN  4  WHEN  GRAD  IS  NULL  THEN  3  WHEN  TO_NUMBER(SUBSTR(GRAD,5))  >  TO_NUMBER(SUBSTR(GRADNM,5))  THEN  1  ELSE  2  END  ),  ADDR)  AS  SEQ  ,  UPSO_CD  ,  UPSO_NM  ,  INS_DATE  ,  OPBI_DAY  ,  ADDR  ,  DONG  ,  GRADNM  ,  STAFF_NM  ,  SITE_AREA  ,  GRAD  ,  MATCH_YN  ,  DECODE(GRADNM,  GRAD,  'O',  'X')  AS  COMPARE  ,  (  CASE  WHEN  GRAD  IS  NULL  THEN  '확인불가'  WHEN  TO_NUMBER(SUBSTR(GRAD,5))  >  TO_NUMBER(SUBSTR(GRADNM,5))  THEN  'O'  ELSE  'X'  END  )  AS  LOSS_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  UPSO_NM  ,  INS_DATE  ,  OPBI_DAY  ,  ADDR  ,  DONG  ,  GRADNM  ,  STAFF_NM  ,  SITE_AREA  ,  DECODE(GRAD,  NULL,  NULL,  BSTYP||(CASE  WHEN  TO_NUMBER(GRAD)  <>  1   \n";
        query +=" AND  (ADDR  LIKE  '%읍  %'   \n";
        query +=" OR  ADDR  LIKE  '%면  %')  THEN  TO_NUMBER(GRAD)  -1  ELSE  TO_NUMBER(GRAD)  END))  GRAD  ,  MATCH_YN  FROM  (   \n";
        query +=" SELECT  A.UPSO_NM  AS  UPSO_NM  ,  A.UPSO_CD  AS  UPSO_CD  ,  '1'  AS  GBN  ,  TO_CHAR(INS_DATE,  'YYYYMMDD')  AS  INS_DATE  ,  A.OPBI_DAY  AS  OPBI_DAY  ,  A.UPSO_NEW_ADDR1||'  '||A.UPSO_NEW_ADDR2  AS  ADDR  ,  (   \n";
        query +=" SELECT  PREFEC_NM  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  A.UPSO_BD_MNG_NUM  )  AS  DONG  ,  (   \n";
        query +=" SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  C.BSTYP_CD   \n";
        query +=" AND  GRAD_GBN  =  C.UPSO_GRAD  )  AS  GRADNM  ,  FIDU.GET_STAFF_NM  (A.STAFF_CD)  AS  STAFF_NM  ,  B.SITE_AREA  AS  SITE_AREA  ,  DECODE(C.BSTYP_CD,  'k',  '유흥주점',  '단란주점')  AS  BSTYP  ,  (  CASE  WHEN  B.SITE_AREA  IS  NULL  THEN  NULL  WHEN  TO_NUMBER  (B.SITE_AREA)  <  66  THEN  '1'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  99  THEN  '2'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  132  THEN  '3'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  165  THEN  '4'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  198  THEN  '5'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  231  THEN  '6'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  264  THEN  '7'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  297  THEN  '8'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  330  THEN  '9'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  363  THEN  '10'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  396  THEN  '11'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  429  THEN  '12'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  462  THEN  '13'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  495  THEN  '14'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  528  THEN  '15'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  561  THEN  '16'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  594  THEN  '17'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  627  THEN  '18'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  660  THEN  '19'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  693  THEN  '20'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  726  THEN  '21'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  759  THEN  '22'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  792  THEN  '23'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  825  THEN  '24'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  858  THEN  '25'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  891  THEN  '26'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  924   \n";
        query +=" OR  TRIM  (C.BSTYP_CD)  <>  'l'  THEN  '27'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  957  THEN  '28'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  990  THEN  '29'  ELSE  '30'  END  )  AS  GRAD  ,  DECODE  (B.UPSO_CD,  NULL,  '미매칭',  '매칭')  AS  MATCH_YN  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  BIGO  AS  UPSO_CD,  NOROOM_CNT  AS  ROOM_CNT,  FACIL_AR  AS  SITE_AREA  FROM  GIBU.TGOV_NOREBANG  ZA  WHERE  STAT_GBN  =  '1'   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD'))   \n";
        query +=" AND  APV_PERM_YMD  =   \n";
        query +=" (SELECT  MAX  (APV_PERM_YMD)  FROM  GIBU.TGOV_NOREBANG  WHERE  STAT_GBN  =  '1'   \n";
        query +=" AND  BIGO  =  ZA.BIGO   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD')))  UNION  ALL   \n";
        query +=" SELECT  BIGO  AS  UPSO_CD  ,  ''  AS  ROOM_CNT  ,  REPLACE  (REPLACE  (NVL  (FACIL_TOT_SCP,  SITE_AREA),  '.00',  ''),  ',',  '')  AS  SITE_AREA  FROM  GIBU.TGOV_DANRAN  ZB  WHERE  STAT_GBN  =  '1'   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD'))   \n";
        query +=" AND  APV_PERM_YMD  =   \n";
        query +=" (SELECT  MAX  (APV_PERM_YMD)  FROM  GIBU.TGOV_DANRAN  WHERE  STAT_GBN  =  '1'   \n";
        query +=" AND  BIGO  =  ZB.BIGO   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD')))  UNION  ALL   \n";
        query +=" SELECT  BIGO  AS  UPSO_CD  ,  ''  AS  ROOM_CNT  ,  REPLACE  (REPLACE  (NVL  (FACIL_TOT_SCP,  SITE_AREA),  '.00',  ''),  ',',  '')  AS  SITE_AREA  FROM  GIBU.TGOV_YUHEONG  ZC  WHERE  STAT_GBN  =  '1'   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD'))   \n";
        query +=" AND  APV_PERM_YMD  =   \n";
        query +=" (SELECT  MAX  (APV_PERM_YMD)  FROM  GIBU.TGOV_YUHEONG  WHERE  STAT_GBN  =  '1'   \n";
        query +=" AND  BIGO  =  ZC.BIGO   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD'))))  B  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.MONPRNCFEE  ,  ZC.GRADNM  ,  ZB.BSTYP_CD  ,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX  (A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  NVL(:BRAN_CD,  B.BRAN_CD)   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NULL  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD)  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  C.BSTYP_CD  IN  ('l','k')   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  A.BRAN_CD  =  NVL(:BRAN_CD,  A.BRAN_CD)  UNION   \n";
        query +=" SELECT  A.UPSO_NM  AS  UPSO_NM  ,  A.UPSO_CD  AS  UPSO_CD  ,  '2'  AS  GBN  ,  TO_CHAR(INS_DATE,  'YYYYMMDD')  AS  INS_DATE  ,  A.OPBI_DAY  AS  OPBI_DAY  ,  A.UPSO_NEW_ADDR1||'  '||A.UPSO_NEW_ADDR2  AS  JUSO  ,  (   \n";
        query +=" SELECT  PREFEC_NM  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  A.UPSO_BD_MNG_NUM  )  AS  DONG  ,  (   \n";
        query +=" SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  C.BSTYP_CD   \n";
        query +=" AND  GRAD_GBN  =  C.UPSO_GRAD  )  AS  GRADNM  ,  FIDU.GET_STAFF_NM  (A.STAFF_CD)  AS  STAFF_NM  ,  B.SITE_AREA  AS  SITE_AREA  ,  DECODE(C.BSTYP_CD,  'k',  '유흥주점',  '단란주점')  AS  BSTYP  ,  (  CASE  WHEN  B.SITE_AREA  IS  NULL  THEN  NULL  WHEN  TO_NUMBER  (B.SITE_AREA)  <  66  THEN  '1'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  99  THEN  '2'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  132  THEN  '3'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  165  THEN  '4'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  198  THEN  '5'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  231  THEN  '6'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  264  THEN  '7'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  297  THEN  '8'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  330  THEN  '9'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  363  THEN  '10'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  396  THEN  '11'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  429  THEN  '12'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  462  THEN  '13'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  495  THEN  '14'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  528  THEN  '15'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  561  THEN  '16'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  594  THEN  '17'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  627  THEN  '18'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  660  THEN  '19'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  693  THEN  '20'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  726  THEN  '21'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  759  THEN  '22'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  792  THEN  '23'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  825  THEN  '24'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  858  THEN  '25'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  891  THEN  '26'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  924   \n";
        query +=" OR  TRIM  (C.BSTYP_CD)  <>  'l'  THEN  '27'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  957  THEN  '28'  WHEN  TO_NUMBER  (B.SITE_AREA)  <  990  THEN  '29'  ELSE  '30'  END  )  AS  GRAD  ,  '주소매칭'  AS  MATCH_YN  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  BIGO  AS  UPSO_CD,  NOROOM_CNT  AS  ROOM_CNT,  FACIL_AR  AS  SITE_AREA,  TRIM(ADDR1)  AS  ADDR1  FROM  GIBU.TGOV_NOREBANG  ZA  WHERE  LENGTH(NVL(BIGO,'X'))  <>  '8'   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD'))  UNION  ALL   \n";
        query +=" SELECT  BIGO  AS  UPSO_CD  ,  ''  AS  ROOM_CNT  ,  REPLACE  (REPLACE  (NVL  (FACIL_TOT_SCP,  SITE_AREA),  '.00',  ''),  ',',  '')  AS  SITE_AREA  ,  TRIM(ADDR1)  AS  ADDR1  FROM  GIBU.TGOV_DANRAN  ZB  WHERE  LENGTH(NVL(BIGO,'X'))  <>  '8'   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD'))  UNION  ALL   \n";
        query +=" SELECT  BIGO  AS  UPSO_CD  ,  ''  AS  ROOM_CNT  ,  REPLACE  (REPLACE  (NVL  (FACIL_TOT_SCP,  SITE_AREA),  '.00',  ''),  ',',  '')  AS  SITE_AREA  ,  TRIM(ADDR1)  AS  ADDR1  FROM  GIBU.TGOV_YUHEONG  ZC  WHERE  LENGTH(NVL(BIGO,'X'))  <>  '8'   \n";
        query +=" AND  (DCB_YMD  IS  NULL   \n";
        query +=" OR  DCB_YMD  >=  TO_CHAR  (SYSDATE,  'YYYYMMDD'))  )  B  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.MONPRNCFEE  ,  ZC.GRADNM  ,  ZB.BSTYP_CD  ,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX  (A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  NVL(:BRAN_CD,  B.BRAN_CD)   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NULL  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD)  C  WHERE  TRIM(A.MNGEMSTR_NEW_ADDR1)  =  B.ADDR1   \n";
        query +=" AND  A.UPSO_CD  IS  NULL   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  C.BSTYP_CD  IN  ('l','k')   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  A.BRAN_CD  =  NVL(:BRAN_CD,  A.BRAN_CD)  )  ) ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$sel_gov_compare
    //##**$$end
}
