
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s19_3
{
    public bra01_s19_3()
    {
    }
    //##**$$log_trans_exec
    /*
    */
    public DOBJ CTLlog_trans_exec(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
            Conn.setAutoCommit(false);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLlog_trans_exec_XIUD3(Conn, dobj);           //  중복방지용 삭제
            dobj  = CALLlog_trans_exec_XIUD2(Conn, dobj);           //  데이터 복사
            dobj  = CALLlog_trans_exec_XIUD6(Conn, dobj);           //  이력
            dobj  = CALLlog_trans_exec_XIUD5(Conn, dobj);           //  이력기록
            dobj  = CALLlog_trans_exec_SEL9(Conn, dobj);           //  데이터 중복수집 확인
            Conn.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
                Conn.rollback();
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
    public DOBJ CTLlog_trans_exec( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlog_trans_exec_XIUD3(Conn, dobj);           //  중복방지용 삭제
        dobj  = CALLlog_trans_exec_XIUD2(Conn, dobj);           //  데이터 복사
        dobj  = CALLlog_trans_exec_XIUD6(Conn, dobj);           //  이력
        dobj  = CALLlog_trans_exec_XIUD5(Conn, dobj);           //  이력기록
        dobj  = CALLlog_trans_exec_SEL9(Conn, dobj);           //  데이터 중복수집 확인
        return dobj;
    }
    // 중복방지용 삭제
    public DOBJ CALLlog_trans_exec_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlog_trans_exec_XIUD3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlog_trans_exec_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM FIDU.TDIS_OFFUSETUNEINFO  \n";
        query +=" WHERE USE_YRMN BETWEEN :START_YRMN  \n";
        query +=" AND :END_YRMN  \n";
        query +=" AND BSCON_CD IN ('E0006', 'E0003')";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 데이터 복사
    public DOBJ CALLlog_trans_exec_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlog_trans_exec_XIUD2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlog_trans_exec_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.TDIS_OFFUSETUNEINFO(UPSO_CD, USE_YRMN, SEQ, BSCON_CD, BSCON_PROD_CD, USE_START_DATE, USE_END_DATE, OCC_DAY, ACMCN_CD, USE_FREQ, INS_DATE, INSPRES_ID) SELECT UPSO_CD, USE_YRMN, ROWNUM SEQ, BSCON_CD, BSCON_PROD_CD, USE_START_DATE, USE_END_DATE, OCC_DAY, ACMCN_CD, USE_FREQ, SYSDATE, :STAFF_CD FROM ( SELECT UPSO_CD, TO_CHAR (PLAY_SDATE, 'YYYYMM') USE_YRMN, SONG_ID BSCON_PROD_CD, PLAY_SDATE USE_START_DATE, PLAY_EDATE USE_END_DATE, TO_CHAR (PLAY_SDATE, 'YYYYMMDD') OCC_DAY, KARAOKE_NO ACMCN_CD, 1 USE_FREQ, BSCON_CD FROM LOG.KDS_STATISTICS WHERE TO_CHAR (PLAY_SDATE, 'YYYYMM') BETWEEN :START_YRMN AND :END_YRMN AND BSCON_CD IN ('E0006', 'E0003') GROUP BY UPSO_CD, KARAOKE_NO, SONG_ID, PLAY_SDATE, PLAY_EDATE, BSCON_CD UNION ALL SELECT (SELECT MAX(UPSO_CD) FROM GIBU.TBRA_OFF_ROUTER_MNG X WHERE X.ACMCN_CD = B.ACMCN_CD AND B.OCC_DAY BETWEEN TO_CHAR(X.MNG_CONFIRM_DATE,'YYYYMMDD') AND NVL(X.LOG_START_DAY,'99999999') ) UPSO_CD, SUBSTR (OCC_DAY, 1, 6) USE_YRMN, B.BSCON_PROD_CD BSCON_PROD_CD, TO_DATE(OCC_DAY || REPLACE(B.USE_TM,'000000','000001'),'YYYYMMDDHH24MISS') USE_START_DATE, TO_DATE('') USE_END_DATE, OCC_DAY OCC_DAY, B.ACMCN_CD ACMCN_CD, TO_NUMBER(B.USE_NUM) USE_FREQ, B.BSCON_CD FROM GIBU.TBRA_OFF_LOGDATA B WHERE 1=1 AND SUBSTR (B.OCC_DAY, 1, 6) BETWEEN :START_YRMN AND :END_YRMN AND (B.OCC_DAY, B.ACMCN_CD) NOT IN ( SELECT MIN (OCC_DAY), ACMCN_CD FROM GIBU.TBRA_OFF_LOGDATA GROUP BY ACMCN_CD) ) WHERE UPSO_CD IS NOT NULL";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 이력
    public DOBJ CALLlog_trans_exec_XIUD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD6");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlog_trans_exec_XIUD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlog_trans_exec_XIUD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_KYLOG_TRANS_DATA (YRMN, BRAN_CD, BSTYP_CD, UPSO_CNT_KY, UPSO_CNT_TJ, COL_UPSO_CNT_KY, COL_UPSO_CNT_TJ, COL_LOG_CNT_KY, COL_LOG_CNT_TJ) SELECT SUBSTR ( :START_YRMN, 1, 6) AS YRMN, BRAN_CD, BSTYP_CD, SUM (UPSO_CNT_KY) AS UPSO_CNT_KY, SUM (UPSO_CNT_TJ) AS UPSO_CNT_TJ, SUM (COL_UPSO_CNT_KY) AS COL_UPSO_CNT_KY, SUM (COL_UPSO_CNT_TJ) AS COL_UPSO_CNT_TJ, SUM (COL_LOG_CNT_KY) AS COL_LOG_CNT_KY, SUM (COL_LOG_CNT_TJ) AS COL_LOG_CNT_TJ FROM ( SELECT BRAN_CD, BSTYP_CD, DECODE (BSCON_CD, 'E0006', SUM (UPSO_CNT), 0) AS UPSO_CNT_KY, DECODE (BSCON_CD, 'E0006', 0, SUM (UPSO_CNT)) AS UPSO_CNT_TJ, DECODE (BSCON_CD, 'E0006', SUM (COL_UPSO_CNT), 0) AS COL_UPSO_CNT_KY, DECODE (BSCON_CD, 'E0006', 0, SUM (COL_UPSO_CNT)) AS COL_UPSO_CNT_TJ, DECODE (BSCON_CD, 'E0006', SUM (COL_LOG_CNT), 0) AS COL_LOG_CNT_KY, DECODE (BSCON_CD, 'E0006', 0, SUM (COL_LOG_CNT)) AS COL_LOG_CNT_TJ FROM ( SELECT BRAN_CD, BSTYP_CD, BSCON_CD, COUNT (*) UPSO_CNT, 0 AS COL_UPSO_CNT, 0 AS COL_LOG_CNT FROM (SELECT GIBU.FT_GET_BRAN_CD (A.UPSO_CD) BRAN_CD, GIBU.FT_GET_BSTYP_INFO (A.UPSO_CD) AS BSTYP_CD, BSCON_CD, UPSO_CD FROM GIBU.TBRA_OFF_ROUTER_MNG A WHERE 1 = 1 AND A.PROC_STS = 'M') GROUP BY BRAN_CD, BSTYP_CD, BSCON_CD UNION SELECT BRAN_CD, BSTYP_CD, BSCON_CD, 0 AS UPSO_CNT, COUNT (*) AS COL_UPSO_CNT, 0 AS COL_LOG_CNT FROM (SELECT GIBU.FT_GET_BRAN_CD (A.UPSO_CD) BRAN_CD, GIBU.FT_GET_BSTYP_INFO (A.UPSO_CD) AS BSTYP_CD, BSCON_CD, UPSO_CD FROM (SELECT DISTINCT (SELECT MAX (UPSO_CD) FROM GIBU.TBRA_OFF_ROUTER_MNG X WHERE X.ACMCN_CD = B.ACMCN_CD AND B.OCC_DAY BETWEEN TO_CHAR ( X.MNG_CONFIRM_DATE, 'YYYYMMDD') AND NVL ( X.LOG_START_DAY, '99999999')) UPSO_CD, BSCON_CD FROM GIBU.TBRA_OFF_LOGDATA B WHERE 1 = 1 AND SUBSTR (B.OCC_DAY, 1, 6) BETWEEN :START_YRMN AND :END_YRMN AND (B.OCC_DAY, B.ACMCN_CD) NOT IN ( SELECT MIN (OCC_DAY), ACMCN_CD FROM GIBU.TBRA_OFF_LOGDATA GROUP BY ACMCN_CD)) A WHERE UPSO_CD IS NOT NULL) GROUP BY BRAN_CD, BSTYP_CD, BSCON_CD UNION SELECT BRAN_CD, BSTYP_CD, BSCON_CD, 0 AS UPSO_CNT, 0 AS COL_UPSO_CNT, SUM(USE_NUM) AS COL_LOG_CNT FROM (SELECT GIBU.FT_GET_BRAN_CD (A.UPSO_CD) BRAN_CD, GIBU.FT_GET_BSTYP_INFO (A.UPSO_CD) AS BSTYP_CD, BSCON_CD, OCC_DAY, USE_TM, USE_NUM FROM (SELECT (SELECT MAX (UPSO_CD) FROM GIBU.TBRA_OFF_ROUTER_MNG X WHERE X.ACMCN_CD = B.ACMCN_CD AND B.OCC_DAY BETWEEN TO_CHAR ( X.MNG_CONFIRM_DATE, 'YYYYMMDD') AND NVL ( X.LOG_START_DAY, '99999999')) UPSO_CD, BSCON_CD, B.OCC_DAY, B.USE_TM, B.USE_NUM FROM GIBU.TBRA_OFF_LOGDATA B WHERE 1 = 1 AND SUBSTR (B.OCC_DAY, 1, 6) BETWEEN :START_YRMN AND :END_YRMN AND (B.OCC_DAY, B.ACMCN_CD) NOT IN ( SELECT MIN (OCC_DAY), ACMCN_CD FROM GIBU.TBRA_OFF_LOGDATA GROUP BY ACMCN_CD)) A WHERE UPSO_CD IS NOT NULL) GROUP BY BRAN_CD, BSTYP_CD, BSCON_CD UNION SELECT BRAN_CD , BSTYP_CD , BSCON_CD , 0 AS UPSO_CNT , COUNT(*) AS COL_UPSO_CNT , 0 AS COL_LOG_CNT FROM ( SELECT X.BRAN_CD , MAX(X.UPSO_CD) UPSO_CD , GIBU.FT_GET_BSTYP_INFO(MAX(X.UPSO_CD)) AS BSTYP_CD , X.SERIAL_NO , Y.BSCON_CD FROM LOG.KDS_STATISTICS X , ( SELECT A.UPSO_CD, A.BSCON_CD FROM LOG.KDS_SHOPROOM A , (SELECT UPSO_CD, MAX(REG_DATE) AS REG_DATE FROM LOG.KDS_SHOPROOM GROUP BY UPSO_CD) B WHERE A.UPSO_CD = B.UPSO_CD AND A.REG_DATE = B.REG_DATE AND A.UPSO_CD NOT IN (SELECT UPSO_CD FROM GIBU.TBRA_LOG_TEST WHERE USE_YN = 'Y') ) Y WHERE TO_CHAR (PLAY_SDATE, 'YYYYMM') BETWEEN :START_YRMN AND :END_YRMN AND X.UPSO_CD = Y.UPSO_CD GROUP BY X.BRAN_CD, X.SERIAL_NO, Y.BSCON_CD )B WHERE 1=1 GROUP BY BRAN_CD, BSTYP_CD, BSCON_CD UNION SELECT BRAN_CD , BSTYP_CD , BSCON_CD , 0 AS UPSO_CNT , 0 AS COL_UPSO_CNT , COUNT(*) AS COL_LOG_CNT FROM ( SELECT X.BRAN_CD , X.UPSO_CD , GIBU.FT_GET_BSTYP_INFO(X.UPSO_CD) AS BSTYP_CD , Y.BSCON_CD FROM LOG.KDS_STATISTICS X , ( SELECT A.UPSO_CD, A.BSCON_CD FROM LOG.KDS_SHOPROOM A , (SELECT UPSO_CD, MAX(REG_DATE) AS REG_DATE FROM LOG.KDS_SHOPROOM GROUP BY UPSO_CD) B WHERE A.UPSO_CD = B.UPSO_CD AND A.REG_DATE = B.REG_DATE AND A.UPSO_CD NOT IN (SELECT UPSO_CD FROM GIBU.TBRA_LOG_TEST WHERE USE_YN = 'Y') ) Y WHERE TO_CHAR (X.PLAY_SDATE, 'YYYYMM') BETWEEN :START_YRMN AND :END_YRMN AND X.UPSO_CD = Y.UPSO_CD GROUP BY X.BRAN_CD, X.UPSO_CD, Y.BSCON_CD, X.KARAOKE_NO, X.SONG_ID, X.PLAY_SDATE ) WHERE 1=1 GROUP BY BRAN_CD, BSTYP_CD, BSCON_CD ) GROUP BY BRAN_CD, BSTYP_CD, BSCON_CD ) GROUP BY BRAN_CD, BSTYP_CD";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 이력기록
    public DOBJ CALLlog_trans_exec_XIUD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD5");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlog_trans_exec_XIUD5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlog_trans_exec_XIUD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_KYLOG_TRANS_LOG SELECT YRMN , :STAFF_CD , SYSDATE FROM GIBU.COPY_YRMN WHERE YRMN BETWEEN :START_YRMN AND :END_YRMN";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 데이터 중복수집 확인
    public DOBJ CALLlog_trans_exec_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLlog_trans_exec_SEL9(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlog_trans_exec_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  FROM  LOG.KDS_STATISTICS  WHERE  TO_CHAR  (PLAY_SDATE,  'YYYYMM')  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN  GROUP  BY  UPSO_CD,  KARAOKE_NO,SONG_ID,PLAY_SDATE,PLAY_EDATE  HAVING  COUNT(0)  >  1  ) ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    //##**$$log_trans_exec
    //##**$$end
}
