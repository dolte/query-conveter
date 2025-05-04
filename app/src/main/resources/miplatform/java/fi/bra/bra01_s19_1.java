
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s19_1
{
    public bra01_s19_1()
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
            dobj  = CALLlog_trans_exec_XIUD3(Conn, dobj);           //  �ߺ������� ����
            dobj  = CALLlog_trans_exec_XIUD2(Conn, dobj);           //  ������ ����
            dobj  = CALLlog_trans_exec_XIUD5(Conn, dobj);           //  �̷±��
            dobj  = CALLlog_trans_exec_SEL9(Conn, dobj);           //  ������ �ߺ����� Ȯ��
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
        dobj  = CALLlog_trans_exec_XIUD3(Conn, dobj);           //  �ߺ������� ����
        dobj  = CALLlog_trans_exec_XIUD2(Conn, dobj);           //  ������ ����
        dobj  = CALLlog_trans_exec_XIUD5(Conn, dobj);           //  �̷±��
        dobj  = CALLlog_trans_exec_SEL9(Conn, dobj);           //  ������ �ߺ����� Ȯ��
        return dobj;
    }
    // �ߺ������� ����
    public DOBJ CALLlog_trans_exec_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
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
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM FIDU.TDIS_OFFUSETUNEINFO  \n";
        query +=" WHERE USE_YRMN BETWEEN :START_YRMN  \n";
        query +=" AND :END_YRMN  \n";
        query +=" AND BSCON_CD='E0006'";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // ������ ����
    public DOBJ CALLlog_trans_exec_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
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
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.TDIS_OFFUSETUNEINFO(UPSO_CD, USE_YRMN, SEQ, BSCON_CD, BSCON_PROD_CD, USE_START_DATE, USE_END_DATE, OCC_DAY, ACMCN_CD, USE_FREQ, INS_DATE, INSPRES_ID) SELECT UPSO_CD, USE_YRMN, ROWNUM SEQ, 'E0006' AS BSCON_CD, BSCON_PROD_CD, USE_START_DATE, USE_END_DATE, OCC_DAY, ACMCN_CD, USE_FREQ, SYSDATE , :STAFF_CD FROM ( SELECT UPSO_CD, TO_CHAR (PLAY_SDATE, 'YYYYMM') USE_YRMN, SONG_ID BSCON_PROD_CD, PLAY_SDATE USE_START_DATE, PLAY_EDATE USE_END_DATE, TO_CHAR (PLAY_SDATE, 'YYYYMMDD') OCC_DAY, KARAOKE_NO ACMCN_CD, 1 USE_FREQ FROM LOG.KDS_STATISTICS WHERE TO_CHAR (PLAY_SDATE, 'YYYYMM') BETWEEN :START_YRMN AND :END_YRMN GROUP BY UPSO_CD, KARAOKE_NO, SONG_ID, PLAY_SDATE, PLAY_EDATE ) A";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // �̷±��
    public DOBJ CALLlog_trans_exec_XIUD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD5");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
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
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_KYLOG_TRANS_LOG SELECT YRMN , :STAFF_CD , SYSDATE FROM GIBU.COPY_YRMN WHERE YRMN BETWEEN :START_YRMN AND :END_YRMN";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // ������ �ߺ����� Ȯ��
    public DOBJ CALLlog_trans_exec_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLlog_trans_exec_SEL9(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlog_trans_exec_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  FROM  LOG.KDS_STATISTICS  WHERE  TO_CHAR  (PLAY_SDATE,  'YYYYMM')  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN  GROUP  BY  UPSO_CD,  KARAOKE_NO,SONG_ID,PLAY_SDATE,PLAY_EDATE  HAVING  COUNT(0)  >  1  ) ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    //##**$$log_trans_exec
    //##**$$end
}
