
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_r16
{
    public bra01_r16()
    {
    }
    //##**$$cng_karaoke_sel
    /*
    */
    public DOBJ CTLcng_karaoke_sel(DOBJ dobj)
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
            dobj  = CALLcng_karaoke_sel_SEL1(Conn, dobj);           //  반주기변경정보
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
    public DOBJ CTLcng_karaoke_sel( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcng_karaoke_sel_SEL1(Conn, dobj);           //  반주기변경정보
        return dobj;
    }
    // 반주기변경정보
    // 반주기변경정보
    public DOBJ CALLcng_karaoke_sel_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcng_karaoke_sel_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcng_karaoke_sel_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ZB.BRAN_CD  ,  GIBU.GET_BRAN_NM(ZB.BRAN_CD)  AS  BRAN_NM  ,  ZB.UPSO_CD  ,  ZB.UPSO_NM  ,  ZC.SERIAL_NO  ,  ZC.CO_STATUS  ,  ZE.CO_NAME  ,  FIDU.GET_STAFF_NM(ZB.STAFF_CD)  AS  STAFF_NM  FROM  (   \n";
        query +=" SELECT  UPSO_CD  FROM  (   \n";
        query +=" SELECT  /*+  PARALLEL(TA)  */  TA.UPSO_CD,  KARAOKE_NO  FROM  LOG.KDS_STATISTICS  TA  ,  GIBU.TBRA_UPSO  TB  WHERE  PLAY_SDATE  BETWEEN  TO_DATE(:YRMN  ||  '01000000',  'YYYYMMDDHH24MISS')   \n";
        query +=" AND  TO_DATE(TO_CHAR(LAST_DAY(TO_DATE(:YRMN,  'YYYYMM')),  'YYYYMMDD')  ||  '235959',  'YYYYMMDDHH24MISS')   \n";
        query +=" AND  TA.UPSO_CD  =  TB.UPSO_CD   \n";
        query +=" AND  TB.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  TB.BRAN_CD,  :BRAN_CD)  GROUP  BY  TA.UPSO_CD,  KARAOKE_NO  )  GROUP  BY  UPSO_CD  HAVING  COUNT(1)  >  1  )  ZA  ,  GIBU.TBRA_UPSO  ZB  ,  LOG.KDS_SHOPROOM  ZC  ,  LOG.KDS_CODE  ZE  WHERE  ZA.UPSO_CD  =  ZB.UPSO_CD   \n";
        query +=" AND  ZC.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.SEQ  =   \n";
        query +=" (SELECT  MAX(SEQ)  FROM  LOG.KDS_SHOPROOM  WHERE  UPSO_CD  =  ZA.UPSO_CD)   \n";
        query +=" AND  ZE.CO_TYPE  ||  ZE.CO_CODE  =  ZC.CO_STATUS  ORDER  BY  CO_STATUS,  BRAN_CD,  UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$cng_karaoke_sel
    //##**$$dtl_karaoke_sel
    /*
    */
    public DOBJ CTLdtl_karaoke_sel(DOBJ dobj)
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
            dobj  = CALLdtl_karaoke_sel_SEL1(Conn, dobj);           //  시리얼번호의 개통정보
            dobj  = CALLdtl_karaoke_sel_SEL4(Conn, dobj);           //  로그수집정보조회
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
    public DOBJ CTLdtl_karaoke_sel( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdtl_karaoke_sel_SEL1(Conn, dobj);           //  시리얼번호의 개통정보
        dobj  = CALLdtl_karaoke_sel_SEL4(Conn, dobj);           //  로그수집정보조회
        return dobj;
    }
    // 시리얼번호의 개통정보
    public DOBJ CALLdtl_karaoke_sel_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLdtl_karaoke_sel_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdtl_karaoke_sel_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO = dobj.getRetObject("S").getRecord().get("SERIAL_NO");   //제품번호
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD,  ROOM_NAME,  SERIAL_NO,   \n";
        query +=" (SELECT  CO_NAME  FROM  LOG.KDS_CODE  WHERE  CO_TYPE  ||  CO_CODE  =  A.CO_STATUS)  AS  CO_NAME,  REG_DATE  FROM  LOG.KDS_SHOPROOM  A  WHERE  SERIAL_NO  =  :SERIAL_NO   \n";
        query +=" AND  SEQ  >=   \n";
        query +=" (SELECT  MIN(SEQ)  FROM  LOG.KDS_SHOPROOM  WHERE  SERIAL_NO  =  :SERIAL_NO   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD)  ORDER  BY  SEQ ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 로그수집정보조회
    public DOBJ CALLdtl_karaoke_sel_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLdtl_karaoke_sel_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdtl_karaoke_sel_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO = dobj.getRetObject("S").getRecord().get("SERIAL_NO");   //제품번호
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  PARALLEL(A)  */  UPSO_CD,  SERIAL_NO,  KARAOKE_NO,  MIN(PLAY_SDATE)  AS  MIN_PLAY,  MAX(PLAY_SDATE)  AS  MAX_PLAY  FROM  LOG.KDS_STATISTICS  A  WHERE  UPSO_CD  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  LOG.KDS_SHOPROOM  A  WHERE  SERIAL_NO  =  :SERIAL_NO   \n";
        query +=" AND  SEQ  >=   \n";
        query +=" (SELECT  MIN(SEQ)  FROM  LOG.KDS_SHOPROOM  WHERE  SERIAL_NO  =  :SERIAL_NO   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD))   \n";
        query +=" AND  PLAY_SDATE  BETWEEN  TO_DATE(:YRMN  ||  '01000000',  'YYYYMMDDHH24MISS')   \n";
        query +=" AND  TO_DATE(TO_CHAR(LAST_DAY(TO_DATE(:YRMN,  'YYYYMM')),  'YYYYMMDD')  ||  '235959',  'YYYYMMDDHH24MISS')  GROUP  BY  UPSO_CD,  SERIAL_NO,  KARAOKE_NO  ORDER  BY  3,4 ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("YRMN", YRMN);               //년월
        return sobj;
    }
    //##**$$dtl_karaoke_sel
    //##**$$end
}
