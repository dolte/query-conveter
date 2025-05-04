
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s09_3
{
    public bra01_s09_3()
    {
    }
    //##**$$upso_visit_d_list
    /* * ���α׷��� : bra01_s09
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/25
    * ���� : ���ҹ湮 �� ������ ��ȸ�Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_visit_d_list(DOBJ dobj)
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
            dobj  = CALLupso_visit_d_list_SEL1(Conn, dobj);           //  ���ҹ湮�󼼸���Ʈ
            dobj  = CALLupso_visit_d_list_SEL2(Conn, dobj);           //  ���� ÷������ ���
            dobj  = CALLupso_visit_d_list_MPD3(Conn, dobj);           //  ����
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
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
    public DOBJ CTLupso_visit_d_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_visit_d_list_SEL1(Conn, dobj);           //  ���ҹ湮�󼼸���Ʈ
        dobj  = CALLupso_visit_d_list_SEL2(Conn, dobj);           //  ���� ÷������ ���
        dobj  = CALLupso_visit_d_list_MPD3(Conn, dobj);           //  ����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ���ҹ湮�󼼸���Ʈ
    // ���ҹ湮 �� ������ ��ȸ�Ѵ�
    public DOBJ CALLupso_visit_d_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_d_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_d_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int   VISIT_SEQ = dobj.getRetObject("S").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        String   VISIT_DAY = dobj.getRetObject("S").getRecord().get("VISIT_DAY");   //�湮 ����
        String   JOB_GBN = dobj.getRetObject("S").getRecord().get("JOB_GBN");   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  VISIT_DAY  ,  JOB_GBN  ,  VISIT_NUM  ,  REMAK  ,  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT_BRE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN   \n";
        query +=" AND  VISIT_SEQ  =  :VISIT_SEQ ";
        sobj.setSql(query);
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� ÷������ ���
    public DOBJ CALLupso_visit_d_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_d_list_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_d_list_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int   VISIT_SEQ = dobj.getRetObject("S").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        String   VISIT_DAY = dobj.getRetObject("S").getRecord().get("VISIT_DAY");   //�湮 ����
        String   JOB_GBN = dobj.getRetObject("S").getRecord().get("JOB_GBN");   //���� ����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  SEQ  ,  '/home/jeus/komca_web/upload'  AS  PATH  ,  FILE_TEMPNM  UNIFILENAME  ,  FILE_NM  AS  UPFILENAME  ,  FILES  AS  ATTCH_FILE_CTENT  ,  LENGTH(FILE_NM)  AS  PROF_CHG_FILENM_LEN  FROM  GIBU.TBRA_UPSO_VISIT_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  VISIT_SEQ  =  :VISIT_SEQ   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN   \n";
        query +=" AND  (FILE_TYPE  IS  NULL   \n";
        query +=" OR  FILE_TYPE  <>  'mp4') ";
        sobj.setSql(query);
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ����
    public DOBJ CALLupso_visit_d_list_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("SEL2");         //���� ÷������ ��Ͽ��� ������Ų OBJECT�Դϴ�.(CALLupso_visit_d_list_SEL2)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("PROF_CHG_FILENM_LEN").equals("0"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_d_list_SEL6(Conn, dobj);           //  �������ϻ���
            }
        }
        return dobj;
    }
    // �������ϻ���
    // THOM_PRODSTTNTLETTER_ATTCHFILE�� �̿��Ͽ� �������� ����
    public DOBJ CALLupso_visit_d_list_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_d_list_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        String fullname="";
        rvobj.first();
        while(rvobj.next())
        {
            wutil.makeFileOverwirte( dobj.getRetObject("R").getRecord().get("PATH"), dobj.getRetObject("R").getRecord().get("UNIFILENAME"),rvobj.getRecord().getBytes("FILES"));
        }
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_d_list_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int   VISIT_SEQ = dobj.getRetObject("S").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        String   VISIT_DAY = dobj.getRetObject("S").getRecord().get("VISIT_DAY");   //�湮 ����
        String   JOB_GBN = dobj.getRetObject("S").getRecord().get("JOB_GBN");   //���� ����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '/home/jeus/komca_web/upload'  AS  FILE_ROUT  ,  FILE_TEMPNM  ,  FILE_NM  ,  FILES  FROM  GIBU.TBRA_UPSO_VISIT_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SEQ  =  :SEQ   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  VISIT_SEQ  =  :VISIT_SEQ   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    //##**$$upso_visit_d_list
    //##**$$end
}
