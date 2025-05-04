
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s17
{
    public bra01_s17()
    {
    }
    //##**$$search_logcnt_stat
    /*
    */
    public DOBJ CTLsearch_logcnt_stat(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("LOG").equals("100") || dobj.getRetObject("S").getRecord().get("LOG").equals("200") || dobj.getRetObject("S").getRecord().get("LOG").equals("300") || dobj.getRetObject("S").getRecord().get("LOG").equals("400") || dobj.getRetObject("S").getRecord().get("LOG").equals("500"))
            {
                dobj  = CALLsearch_logcnt_stat_SEL1(Conn, dobj);           //  미만들 조건조회
                dobj  = CALLsearch_logcnt_stat_MRG6( dobj);        //  결과
            }
            else if( dobj.getRetObject("S").getRecord().get("LOG").equals("999"))
            {
                dobj  = CALLsearch_logcnt_stat_SEL2(Conn, dobj);           //  1000회 미만조건조회
                dobj  = CALLsearch_logcnt_stat_MRG6( dobj);        //  결과
            }
            else
            {
                dobj  = CALLsearch_logcnt_stat_SEL3(Conn, dobj);           //  이상들 조건조회
                dobj  = CALLsearch_logcnt_stat_MRG6( dobj);        //  결과
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
    public DOBJ CTLsearch_logcnt_stat( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("LOG").equals("100") || dobj.getRetObject("S").getRecord().get("LOG").equals("200") || dobj.getRetObject("S").getRecord().get("LOG").equals("300") || dobj.getRetObject("S").getRecord().get("LOG").equals("400") || dobj.getRetObject("S").getRecord().get("LOG").equals("500"))
        {
            dobj  = CALLsearch_logcnt_stat_SEL1(Conn, dobj);           //  미만들 조건조회
            dobj  = CALLsearch_logcnt_stat_MRG6( dobj);        //  결과
        }
        else if( dobj.getRetObject("S").getRecord().get("LOG").equals("999"))
        {
            dobj  = CALLsearch_logcnt_stat_SEL2(Conn, dobj);           //  1000회 미만조건조회
            dobj  = CALLsearch_logcnt_stat_MRG6( dobj);        //  결과
        }
        else
        {
            dobj  = CALLsearch_logcnt_stat_SEL3(Conn, dobj);           //  이상들 조건조회
            dobj  = CALLsearch_logcnt_stat_MRG6( dobj);        //  결과
        }
        return dobj;
    }
    // 미만들 조건조회
    public DOBJ CALLsearch_logcnt_stat_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_logcnt_stat_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_logcnt_stat_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   LOG = dobj.getRetObject("S").getRecord().get("LOG");   //로그정보
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.UPSO_CD,  B.SERIAL_NO,  NVL(A.CNT,0)  AS  CNT,   \n";
        query +=" (SELECT  CO_PARING  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  B.SERIAL_NO)  AS  CO_PARING,  B.BRAN_NM,  B.UPSO_NM,  B.BSTYP_NM,  B.STAFF_NM  FROM  (   \n";
        query +=" SELECT  SERIAL_NO,  COUNT  (0)  AS  CNT  FROM  LOG.KDS_STATISTICS  WHERE  TO_CHAR  (PLAY_SDATE,  'YYYYMMDD')  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  GROUP  BY  SERIAL_NO  )  A,   \n";
        query +=" (SELECT  A.SERIAL_NO,  A.UPSO_CD,  C.BRAN_CD,  C.UPSO_NM,  C.BRAN_NM,  C.BSTYP_NM,  C.STAFF_NM  FROM   \n";
        query +=" (SELECT  SERIAL_NO,  SEQ,  UPSO_CD,  CO_STATUS  FROM  LOG.KDS_SHOPROOM  WHERE  CO_STATUS!='07005')  A,   \n";
        query +=" (SELECT  SERIAL_NO,  MAX  (SEQ)  AS  SEQ  FROM  LOG.KDS_SHOPROOM  GROUP  BY  SERIAL_NO)  B,   \n";
        query +=" (SELECT  BRAN_CD,  UPSO_CD,  UPSO_NM,  FIDU.GET_GIBU_NM  (BRAN_CD)  AS  BRAN_NM,  STAFF_CD,  FIDU.GET_STAFF_NM(STAFF_CD)  AS  STAFF_NM,   \n";
        query +=" (SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =  GIBU.FT_GET_BSTYP_INFO  (UPSO_CD))  AS  BSTYP_NM  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  DECODE  (:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD))  C  WHERE  A.SERIAL_NO  =  B.SERIAL_NO   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD)  B  WHERE  A.SERIAL_NO(+)  =  B.SERIAL_NO   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  NVL(A.CNT,0)  <  :LOG  ORDER  BY  B.BRAN_CD,  B.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("LOG", LOG);               //로그정보
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 결과
    public DOBJ CALLsearch_logcnt_stat_MRG6(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG6");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL2, SEL3","");
        rvobj.setName("MRG6") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 1000회 미만조건조회
    public DOBJ CALLsearch_logcnt_stat_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_logcnt_stat_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_logcnt_stat_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.UPSO_CD,  B.SERIAL_NO,  NVL(A.CNT,0)  AS  CNT,   \n";
        query +=" (SELECT  CO_PARING  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  B.SERIAL_NO)  AS  CO_PARING,  B.BRAN_NM,  B.UPSO_NM,  B.BSTYP_NM,  B.STAFF_NM  FROM  (   \n";
        query +=" SELECT  SERIAL_NO,  COUNT  (0)  AS  CNT  FROM  LOG.KDS_STATISTICS  WHERE  TO_CHAR  (PLAY_SDATE,  'YYYYMMDD')  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  GROUP  BY  SERIAL_NO  )  A,   \n";
        query +=" (SELECT  A.SERIAL_NO,  A.UPSO_CD,  C.BRAN_CD,  C.UPSO_NM,  C.BRAN_NM,  C.BSTYP_NM,  C.STAFF_NM  FROM   \n";
        query +=" (SELECT  SERIAL_NO,  SEQ,  UPSO_CD,  CO_STATUS  FROM  LOG.KDS_SHOPROOM  WHERE  CO_STATUS!='07005')  A,   \n";
        query +=" (SELECT  SERIAL_NO,  MAX  (SEQ)  AS  SEQ  FROM  LOG.KDS_SHOPROOM  GROUP  BY  SERIAL_NO)  B,   \n";
        query +=" (SELECT  BRAN_CD,  UPSO_CD,  UPSO_NM,  FIDU.GET_GIBU_NM  (BRAN_CD)  AS  BRAN_NM,  STAFF_CD,  FIDU.GET_STAFF_NM(STAFF_CD)  AS  STAFF_NM,   \n";
        query +=" (SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =  GIBU.FT_GET_BSTYP_INFO  (UPSO_CD))  AS  BSTYP_NM  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  DECODE  (:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD))  C  WHERE  A.SERIAL_NO  =  B.SERIAL_NO   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD)  B  WHERE  A.SERIAL_NO(+)  =  B.SERIAL_NO   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  NVL(A.CNT,0)  <  '1000'  ORDER  BY  B.BRAN_CD,  B.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 이상들 조건조회
    public DOBJ CALLsearch_logcnt_stat_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_logcnt_stat_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_logcnt_stat_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   LOG = dobj.getRetObject("S").getRecord().get("LOG");   //로그정보
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.UPSO_CD,  B.SERIAL_NO,  NVL(A.CNT,0)  AS  CNT,   \n";
        query +=" (SELECT  CO_PARING  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  B.SERIAL_NO)  AS  CO_PARING,  B.BRAN_NM,  B.UPSO_NM,  B.BSTYP_NM,  B.STAFF_NM  FROM  (   \n";
        query +=" SELECT  SERIAL_NO,  COUNT  (0)  AS  CNT  FROM  LOG.KDS_STATISTICS  WHERE  TO_CHAR  (PLAY_SDATE,  'YYYYMMDD')  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  GROUP  BY  SERIAL_NO  )  A,   \n";
        query +=" (SELECT  A.SERIAL_NO,  A.UPSO_CD,  C.BRAN_CD,  C.UPSO_NM,  C.BRAN_NM,  C.BSTYP_NM,  C.STAFF_NM  FROM   \n";
        query +=" (SELECT  SERIAL_NO,  SEQ,  UPSO_CD,  CO_STATUS  FROM  LOG.KDS_SHOPROOM  WHERE  CO_STATUS!='07005')  A,   \n";
        query +=" (SELECT  SERIAL_NO,  MAX  (SEQ)  AS  SEQ  FROM  LOG.KDS_SHOPROOM  GROUP  BY  SERIAL_NO)  B,   \n";
        query +=" (SELECT  BRAN_CD,  UPSO_CD,  UPSO_NM,  FIDU.GET_GIBU_NM  (BRAN_CD)  AS  BRAN_NM,  STAFF_CD,  FIDU.GET_STAFF_NM(STAFF_CD)  AS  STAFF_NM,   \n";
        query +=" (SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =  GIBU.FT_GET_BSTYP_INFO  (UPSO_CD))  AS  BSTYP_NM  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  DECODE  (:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD))  C  WHERE  A.SERIAL_NO  =  B.SERIAL_NO   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD)  B  WHERE  A.SERIAL_NO(+)  =  B.SERIAL_NO   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  NVL(A.CNT,0)  >=  :LOG  ORDER  BY  B.BRAN_CD,  B.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("LOG", LOG);               //로그정보
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$search_logcnt_stat
    //##**$$search_lastlog_stat
    /*
    */
    public DOBJ CTLsearch_lastlog_stat(DOBJ dobj)
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
            dobj  = CALLsearch_lastlog_stat_SEL1(Conn, dobj);           //  통계조회
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
    public DOBJ CTLsearch_lastlog_stat( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_lastlog_stat_SEL1(Conn, dobj);           //  통계조회
        return dobj;
    }
    // 통계조회
    public DOBJ CALLsearch_lastlog_stat_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_lastlog_stat_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_lastlog_stat_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   LOG = dobj.getRetObject("S").getRecord().get("LOG");   //로그정보
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.UPSO_CD,  A.SERIAL_NO,   \n";
        query +=" (SELECT  TO_CHAR(PARING_DATE,'YYYY-MM-DD  HH24:MI:SS')  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  A.SERIAL_NO)  AS  PARING_DATE,   \n";
        query +=" (SELECT  CO_PARING  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  A.SERIAL_NO)  AS  CO_PARING,  TO_CHAR(A.REG_DATE,'YYYY-MM-DD  HH24:MI:SS')  AS  REG_DATE,  A.DIFF_DAY,  B.BRAN_NM,  B.UPSO_NM,  B.BSTYP_NM,  B.STAFF_NM  FROM  (   \n";
        query +=" SELECT  SERIAL_NO  ,  MAX(REG_DATE)  AS  REG_DATE  ,  CEIL(TO_DATE(:START_DAY||'235959',  'YYYYMMDDHH24MISS')  -  MAX(REG_DATE))  AS  DIFF_DAY  FROM  LOG.KDS_STATISTICS  WHERE  REG_DATE  <  TO_DATE(:START_DAY||'235959',  'YYYYMMDDHH24MISS')   \n";
        query +=" AND  BRAN_CD  =  DECODE  (:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  GROUP  BY  SERIAL_NO  HAVING  CEIL(TO_DATE(:START_DAY||'235959',  'YYYYMMDDHH24MISS')  -  MAX(REG_DATE))  >=  :LOG)  A,   \n";
        query +=" (SELECT  A.SERIAL_NO,  A.UPSO_CD,  C.BRAN_CD,  C.UPSO_NM,  C.BRAN_NM,  C.BSTYP_NM,  C.STAFF_NM  FROM   \n";
        query +=" (SELECT  SERIAL_NO,  SEQ,  UPSO_CD,  CO_STATUS  FROM  LOG.KDS_SHOPROOM)  A,   \n";
        query +=" (SELECT  SERIAL_NO,  MAX  (SEQ)  AS  SEQ  FROM  LOG.KDS_SHOPROOM  GROUP  BY  SERIAL_NO)  B,   \n";
        query +=" (SELECT  BRAN_CD,  UPSO_CD,  UPSO_NM,  FIDU.GET_GIBU_NM  (BRAN_CD)  AS  BRAN_NM,  STAFF_CD,  FIDU.GET_STAFF_NM(STAFF_CD)  AS  STAFF_NM,   \n";
        query +=" (SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =  GIBU.FT_GET_BSTYP_INFO  (UPSO_CD))  AS  BSTYP_NM  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  DECODE  (:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD))  C  WHERE  A.SERIAL_NO  =  B.SERIAL_NO   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD)  B  WHERE  A.SERIAL_NO  =  B.SERIAL_NO   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)  ORDER  BY  B.BRAN_CD,  B.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("LOG", LOG);               //로그정보
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$search_lastlog_stat
    //##**$$search_lastlog_stat_1
    /*
    */
    public DOBJ CTLsearch_lastlog_stat_1(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("Y"))
            {
                dobj  = CALLsearch_lastlog_stat_1_SEL1(Conn, dobj);           //  통계조회
                dobj  = CALLsearch_lastlog_stat_1_MRG7( dobj);        //  결과
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("N"))
            {
                dobj  = CALLsearch_lastlog_stat_1_SEL5(Conn, dobj);           //  통계조회
                dobj  = CALLsearch_lastlog_stat_1_MRG7( dobj);        //  결과
            }
            else
            {
                dobj  = CALLsearch_lastlog_stat_1_SEL6(Conn, dobj);           //  통계조회
                dobj  = CALLsearch_lastlog_stat_1_MRG7( dobj);        //  결과
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
    public DOBJ CTLsearch_lastlog_stat_1( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("Y"))
        {
            dobj  = CALLsearch_lastlog_stat_1_SEL1(Conn, dobj);           //  통계조회
            dobj  = CALLsearch_lastlog_stat_1_MRG7( dobj);        //  결과
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("N"))
        {
            dobj  = CALLsearch_lastlog_stat_1_SEL5(Conn, dobj);           //  통계조회
            dobj  = CALLsearch_lastlog_stat_1_MRG7( dobj);        //  결과
        }
        else
        {
            dobj  = CALLsearch_lastlog_stat_1_SEL6(Conn, dobj);           //  통계조회
            dobj  = CALLsearch_lastlog_stat_1_MRG7( dobj);        //  결과
        }
        return dobj;
    }
    // 통계조회
    public DOBJ CALLsearch_lastlog_stat_1_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_lastlog_stat_1_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_lastlog_stat_1_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   LOG = dobj.getRetObject("S").getRecord().get("LOG");   //로그정보
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.UPSO_CD,  A.SERIAL_NO,   \n";
        query +=" (SELECT  TO_CHAR(PARING_DATE,'YYYY-MM-DD  HH24:MI:SS')  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  A.SERIAL_NO)  AS  PARING_DATE,   \n";
        query +=" (SELECT  CO_PARING  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  A.SERIAL_NO)  AS  CO_PARING,  TO_CHAR(A.REG_DATE,'YYYY-MM-DD  HH24:MI:SS')  AS  REG_DATE,  A.DIFF_DAY,  B.BRAN_NM,  B.UPSO_NM,  B.BSTYP_NM,  B.STAFF_NM  FROM  (   \n";
        query +=" SELECT  SERIAL_NO  ,  MAX(REG_DATE)  AS  REG_DATE  ,  CEIL(TO_DATE(:START_DAY||'235959',  'YYYYMMDDHH24MISS')  -  MAX(REG_DATE))  AS  DIFF_DAY  FROM  LOG.KDS_STATISTICS  WHERE  REG_DATE  <  TO_DATE(:START_DAY||'235959',  'YYYYMMDDHH24MISS')   \n";
        query +=" AND  BRAN_CD  =  DECODE  (:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  GROUP  BY  SERIAL_NO  HAVING  CEIL(TO_DATE(:START_DAY||'235959',  'YYYYMMDDHH24MISS')  -  MAX(REG_DATE))  >=  :LOG)  A,   \n";
        query +=" (SELECT  A.SERIAL_NO,  A.UPSO_CD,  C.BRAN_CD,  C.UPSO_NM,  C.BRAN_NM,  C.BSTYP_NM,  C.STAFF_NM  FROM   \n";
        query +=" (SELECT  SERIAL_NO,  SEQ,  UPSO_CD,  CO_STATUS  FROM  LOG.KDS_SHOPROOM  WHERE  CO_STATUS  <>  '07005'  )  A,   \n";
        query +=" (SELECT  SERIAL_NO,  MAX  (SEQ)  AS  SEQ  FROM  LOG.KDS_SHOPROOM  GROUP  BY  SERIAL_NO)  B,   \n";
        query +=" (SELECT  BRAN_CD,  UPSO_CD,  UPSO_NM,  FIDU.GET_GIBU_NM  (BRAN_CD)  AS  BRAN_NM,  STAFF_CD,  FIDU.GET_STAFF_NM(STAFF_CD)  AS  STAFF_NM,   \n";
        query +=" (SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =  GIBU.FT_GET_BSTYP_INFO  (UPSO_CD))  AS  BSTYP_NM  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  DECODE  (:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD))  C  WHERE  A.SERIAL_NO  =  B.SERIAL_NO   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD)  B  WHERE  A.SERIAL_NO  =  B.SERIAL_NO   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)  ORDER  BY  B.BRAN_CD,  B.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("LOG", LOG);               //로그정보
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 결과
    public DOBJ CALLsearch_lastlog_stat_1_MRG7(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG7");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL5, SEL6","");
        rvobj.setName("MRG7") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 통계조회
    public DOBJ CALLsearch_lastlog_stat_1_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_lastlog_stat_1_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_lastlog_stat_1_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   LOG = dobj.getRetObject("S").getRecord().get("LOG");   //로그정보
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.UPSO_CD,  A.SERIAL_NO,   \n";
        query +=" (SELECT  TO_CHAR(PARING_DATE,'YYYY-MM-DD  HH24:MI:SS')  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  A.SERIAL_NO)  AS  PARING_DATE,   \n";
        query +=" (SELECT  CO_PARING  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  A.SERIAL_NO)  AS  CO_PARING,  TO_CHAR(A.REG_DATE,'YYYY-MM-DD  HH24:MI:SS')  AS  REG_DATE,  A.DIFF_DAY,  B.BRAN_NM,  B.UPSO_NM,  B.BSTYP_NM,  B.STAFF_NM  FROM  (   \n";
        query +=" SELECT  SERIAL_NO  ,  MAX(REG_DATE)  AS  REG_DATE  ,  CEIL(TO_DATE(:START_DAY||'235959',  'YYYYMMDDHH24MISS')  -  MAX(REG_DATE))  AS  DIFF_DAY  FROM  LOG.KDS_STATISTICS  WHERE  REG_DATE  <  TO_DATE(:START_DAY||'235959',  'YYYYMMDDHH24MISS')   \n";
        query +=" AND  BRAN_CD  =  DECODE  (:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  GROUP  BY  SERIAL_NO  HAVING  CEIL(TO_DATE(:START_DAY||'235959',  'YYYYMMDDHH24MISS')  -  MAX(REG_DATE))  >=  :LOG)  A,   \n";
        query +=" (SELECT  A.SERIAL_NO,  A.UPSO_CD,  C.BRAN_CD,  C.UPSO_NM,  C.BRAN_NM,  C.BSTYP_NM,  C.STAFF_NM  FROM   \n";
        query +=" (SELECT  SERIAL_NO,  SEQ,  UPSO_CD,  CO_STATUS  FROM  LOG.KDS_SHOPROOM  WHERE  CO_STATUS  =  '07005'  )  A,   \n";
        query +=" (SELECT  SERIAL_NO,  MAX  (SEQ)  AS  SEQ  FROM  LOG.KDS_SHOPROOM  GROUP  BY  SERIAL_NO)  B,   \n";
        query +=" (SELECT  BRAN_CD,  UPSO_CD,  UPSO_NM,  FIDU.GET_GIBU_NM  (BRAN_CD)  AS  BRAN_NM,  STAFF_CD,  FIDU.GET_STAFF_NM(STAFF_CD)  AS  STAFF_NM,   \n";
        query +=" (SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =  GIBU.FT_GET_BSTYP_INFO  (UPSO_CD))  AS  BSTYP_NM  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  DECODE  (:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD))  C  WHERE  A.SERIAL_NO  =  B.SERIAL_NO   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD)  B  WHERE  A.SERIAL_NO  =  B.SERIAL_NO   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)  ORDER  BY  B.BRAN_CD,  B.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("LOG", LOG);               //로그정보
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 통계조회
    public DOBJ CALLsearch_lastlog_stat_1_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_lastlog_stat_1_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_lastlog_stat_1_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   LOG = dobj.getRetObject("S").getRecord().get("LOG");   //로그정보
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.UPSO_CD,  A.SERIAL_NO,   \n";
        query +=" (SELECT  TO_CHAR(PARING_DATE,'YYYY-MM-DD  HH24:MI:SS')  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  A.SERIAL_NO)  AS  PARING_DATE,   \n";
        query +=" (SELECT  CO_PARING  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  A.SERIAL_NO)  AS  CO_PARING,  TO_CHAR(A.REG_DATE,'YYYY-MM-DD  HH24:MI:SS')  AS  REG_DATE,  A.DIFF_DAY,  B.BRAN_NM,  B.UPSO_NM,  B.BSTYP_NM,  B.STAFF_NM  FROM  (   \n";
        query +=" SELECT  SERIAL_NO  ,  MAX(REG_DATE)  AS  REG_DATE  ,  CEIL(TO_DATE(:START_DAY||'235959',  'YYYYMMDDHH24MISS')  -  MAX(REG_DATE))  AS  DIFF_DAY  FROM  LOG.KDS_STATISTICS  WHERE  REG_DATE  <  TO_DATE(:START_DAY||'235959',  'YYYYMMDDHH24MISS')   \n";
        query +=" AND  BRAN_CD  =  DECODE  (:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  GROUP  BY  SERIAL_NO  HAVING  CEIL(TO_DATE(:START_DAY||'235959',  'YYYYMMDDHH24MISS')  -  MAX(REG_DATE))  >=  :LOG)  A,   \n";
        query +=" (SELECT  A.SERIAL_NO,  A.UPSO_CD,  C.BRAN_CD,  C.UPSO_NM,  C.BRAN_NM,  C.BSTYP_NM,  C.STAFF_NM  FROM   \n";
        query +=" (SELECT  SERIAL_NO,  SEQ,  UPSO_CD,  CO_STATUS  FROM  LOG.KDS_SHOPROOM  )  A,   \n";
        query +=" (SELECT  SERIAL_NO,  MAX  (SEQ)  AS  SEQ  FROM  LOG.KDS_SHOPROOM  GROUP  BY  SERIAL_NO)  B,   \n";
        query +=" (SELECT  BRAN_CD,  UPSO_CD,  UPSO_NM,  FIDU.GET_GIBU_NM  (BRAN_CD)  AS  BRAN_NM,  STAFF_CD,  FIDU.GET_STAFF_NM(STAFF_CD)  AS  STAFF_NM,   \n";
        query +=" (SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =  GIBU.FT_GET_BSTYP_INFO  (UPSO_CD))  AS  BSTYP_NM  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  DECODE  (:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD))  C  WHERE  A.SERIAL_NO  =  B.SERIAL_NO   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD)  B  WHERE  A.SERIAL_NO  =  B.SERIAL_NO   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)  ORDER  BY  B.BRAN_CD,  B.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("LOG", LOG);               //로그정보
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$search_lastlog_stat_1
    //##**$$get_code_log_stat
    /*
    */
    public DOBJ CTLget_code_log_stat(DOBJ dobj)
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
            dobj  = CALLget_code_log_stat_SEL1(Conn, dobj);           //  로그기상태코드코드
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
    public DOBJ CTLget_code_log_stat( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_code_log_stat_SEL1(Conn, dobj);           //  로그기상태코드코드
        return dobj;
    }
    // 로그기상태코드코드
    public DOBJ CALLget_code_log_stat_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_code_log_stat_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_code_log_stat_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CO_TYPE  ||  CO_CODE  AS  CO_CODE  ,  CO_NAME  FROM  LOG.KDS_CODE  WHERE  CO_TYPE  =  '08' ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$get_code_log_stat
    //##**$$end
}
