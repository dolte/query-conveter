
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_r01
{
    public bra01_r01()
    {
    }
    //##**$$sel_upsolog_comp
    /*
    */
    public DOBJ CTLsel_upsolog_comp(DOBJ dobj)
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
            dobj  = CALLsel_upsolog_comp_SEL1(Conn, dobj);           //  업소별횟수조회
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
    public DOBJ CTLsel_upsolog_comp( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_upsolog_comp_SEL1(Conn, dobj);           //  업소별횟수조회
        return dobj;
    }
    // 업소별횟수조회
    public DOBJ CALLsel_upsolog_comp_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_upsolog_comp_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_upsolog_comp_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  FIDU.GET_BSCON_NM(A.BRAN_CD)  AS  BRAN_NM  ,  A.UPSO_CD  ,  A.UPSO_NM  ,   \n";
        query +=" (SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =   \n";
        query +=" (SELECT  BSTYP_CD  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  JOIN_SEQ  =   \n";
        query +=" (SELECT  MAX(JOIN_SEQ)  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  A.UPSO_CD)))  AS  BSTYP_NM  ,  SUBSTR(A.SERIAL_NO,  -4)  AS  SERIAL_NO  ,  B.UPSO_NEW_ADDR1  ||  (CASE  WHEN  B.UPSO_NEW_ADDR2  IS  NOT  NULL   \n";
        query +=" OR  B.UPSO_REF_INFO  IS  NOT  NULL  THEN  ',  '  END)  ||  B.UPSO_NEW_ADDR2  ||  B.UPSO_REF_INFO  AS  UPSO_ADDR  ,  DECODE(A.BSCON_CD,  'E0003',  '태진',  'E0006',  '금영',  '')  AS  BSCON_NM  ,  B_USE_FREQ  ,  USE_FREQ  ,  ''  AS  BIGO  ,  USE_FREQ  -  B_USE_FREQ  AS  FREQ_RATE  ,  (CASE  WHEN  USE_FREQ  -  B_USE_FREQ  <  0  THEN  '▽감소'  WHEN  USE_FREQ  -  B_USE_FREQ  >  0  THEN  '▲증가'  ELSE  '─보합'  END)  AS  TRANS_CMNT  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  UPSO_CD  ,  UPSO_NM  ,  SERIAL_NO  ,  BSCON_CD  ,  SUM(B_USE_FREQ)  AS  B_USE_FREQ  ,  SUM(USE_FREQ)  AS  USE_FREQ  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD  ,  B.UPSO_CD  ,  B.UPSO_NM  ,  A.SERIAL_NO  ,   \n";
        query +=" (SELECT  --+  INDEX_DESC  (KDS_SHOPROOM  PK_KDS_SM_SEQ)  \n  BSCON_CD  FROM  LOG.KDS_SHOPROOM  WHERE  UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  CO_STATUS  =  '07001'   \n";
        query +=" AND  ROWNUM  =  1)  AS  BSCON_CD  ,  0  AS  B_USE_FREQ  ,  COUNT(1)  AS  USE_FREQ  FROM  LOG.KDS_STATISTICS  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.PLAY_SDATE  BETWEEN  TO_DATE(:START_YRMN  ||  '01000000',  'YYYYMMDDHH24MISS')   \n";
        query +=" AND  TO_DATE(TO_CHAR(LAST_DAY(TO_DATE(:START_YRMN,  'YYYYMM')),  'YYYYMMDD')  ||  '235959',  'YYYYMMDDHH24MISS')   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.BSCON_CD  =  DECODE(:BSCON_CD,  NULL,  A.BSCON_CD,  :BSCON_CD)  GROUP  BY  B.BRAN_CD,  B.UPSO_CD,  B.UPSO_NM,  A.SERIAL_NO,  A.BSCON_CD  UNION  ALL   \n";
        query +=" SELECT  B.BRAN_CD  ,  B.UPSO_CD  ,  B.UPSO_NM  ,  A.SERIAL_NO  ,  A.BSCON_CD  ,  COUNT(1)  AS  B_USE_FREQ  ,  0  AS  USE_FREQ  FROM  LOG.KDS_STATISTICS  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.PLAY_SDATE  BETWEEN  ADD_MONTHS(TO_DATE(:START_YRMN  ||  '01000000',  'YYYYMMDDHH24MISS'),  -1)   \n";
        query +=" AND  ADD_MONTHS(TO_DATE(TO_CHAR(LAST_DAY(TO_DATE(:START_YRMN,  'YYYYMM')),  'YYYYMMDD')  ||  '235959',  'YYYYMMDDHH24MISS'),  -1)   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.BSCON_CD  =  DECODE(:BSCON_CD,  NULL,  A.BSCON_CD,  :BSCON_CD)  GROUP  BY  B.BRAN_CD,  B.UPSO_CD,  B.UPSO_NM,  A.SERIAL_NO,  A.BSCON_CD  )  GROUP  BY  BRAN_CD,  UPSO_CD,  UPSO_NM,  SERIAL_NO,  BSCON_CD  )  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  ORDER  BY  BRAN_NM,  SERIAL_NO ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    //##**$$sel_upsolog_comp
    //##**$$sel_upsolog_term
    /*
    */
    public DOBJ CTLsel_upsolog_term(DOBJ dobj)
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
            dobj  = CALLsel_upsolog_term_SEL1(Conn, dobj);           //  기간조회
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
    public DOBJ CTLsel_upsolog_term( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_upsolog_term_SEL1(Conn, dobj);           //  기간조회
        return dobj;
    }
    // 기간조회
    public DOBJ CALLsel_upsolog_term_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_upsolog_term_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_upsolog_term_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  FIDU.GET_BSCON_NM(A.BRAN_CD)  AS  BRAN_NM  ,  A.UPSO_CD  ,  A.UPSO_NM  ,   \n";
        query +=" (SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =   \n";
        query +=" (SELECT  BSTYP_CD  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  JOIN_SEQ  =   \n";
        query +=" (SELECT  MAX(JOIN_SEQ)  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  A.UPSO_CD)))  AS  BSTYP_NM  ,  SUBSTR(A.SERIAL_NO,  -4)  AS  SERIAL_NO  ,  B.UPSO_NEW_ADDR1  ||  (CASE  WHEN  B.UPSO_NEW_ADDR2  IS  NOT  NULL   \n";
        query +=" OR  B.UPSO_REF_INFO  IS  NOT  NULL  THEN  ',  '  END)  ||  B.UPSO_NEW_ADDR2  ||  B.UPSO_REF_INFO  AS  UPSO_ADDR  ,  DECODE(A.BSCON_CD,  'E0003',  '태진',  'E0006',  '금영',  '')  AS  BSCON_NM  ,  USE_FREQ  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  UPSO_CD  ,  UPSO_NM  ,  SERIAL_NO  ,  BSCON_CD  ,  SUM(USE_FREQ)  AS  USE_FREQ  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD  ,  B.UPSO_CD  ,  B.UPSO_NM  ,  A.SERIAL_NO  ,   \n";
        query +=" (SELECT  --+  INDEX_DESC  (KDS_SHOPROOM  PK_KDS_SM_SEQ)  \n  BSCON_CD  FROM  LOG.KDS_SHOPROOM  WHERE  UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  CO_STATUS  =  '07001'   \n";
        query +=" AND  ROWNUM  =  1)  AS  BSCON_CD  ,  COUNT(1)  AS  USE_FREQ  FROM  LOG.KDS_STATISTICS  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.PLAY_SDATE  BETWEEN  TO_DATE(:START_DAY  ||  '000000',  'YYYYMMDDHH24MISS')   \n";
        query +=" AND  TO_DATE(:END_DAY  ||  '235959',  'YYYYMMDDHH24MISS')   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.BSCON_CD  =  DECODE(:BSCON_CD,  NULL,  A.BSCON_CD,  :BSCON_CD)  GROUP  BY  B.BRAN_CD,  B.UPSO_CD,  B.UPSO_NM,  A.SERIAL_NO,  A.BSCON_CD  )  GROUP  BY  BRAN_CD,  UPSO_CD,  UPSO_NM,  SERIAL_NO,  BSCON_CD  )  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  ORDER  BY  BRAN_NM,  SERIAL_NO ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    //##**$$sel_upsolog_term
    //##**$$end
}
