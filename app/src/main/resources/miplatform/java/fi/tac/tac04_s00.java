
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac04_s00
{
    public tac04_s00()
    {
    }
    //##**$$tac04_s00_save
    /* * ���α׷��� : tac04_s00
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/17
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLtac04_s00_save(DOBJ dobj)
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
            dobj  = CALLtac04_s00_save_MIUD1(Conn, dobj);           //  ȯ���� �����ܾ� ���
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
    public DOBJ CTLtac04_s00_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac04_s00_save_MIUD1(Conn, dobj);           //  ȯ���� �����ܾ� ���
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ȯ���� �����ܾ� ���
    public DOBJ CALLtac04_s00_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtac04_s00_save_INS5(Conn, dobj);           //  ȯ���� �����ܾ� �Է�
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtac04_s00_save_UPD6(Conn, dobj);           //  ȯ���� �����ܾ� ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtac04_s00_save_DEL7(Conn, dobj);           //  ȯ���� �����ܾ� ����
            }
        }
        return dobj;
    }
    // ȯ���� �����ܾ� ����
    public DOBJ CALLtac04_s00_save_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtac04_s00_save_DEL7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac04_s00_save_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REDEM_MB_CD = dvobj.getRecord().get("REDEM_MB_CD");   //ȯ��ȸ���ڵ�
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_REDEMREDISTR_BALANCE  \n";
        query +=" where TRNSF_GBN=:TRNSF_GBN  \n";
        query +=" and REDEM_MB_CD=:REDEM_MB_CD";
        sobj.setSql(query);
        sobj.setString("REDEM_MB_CD", REDEM_MB_CD);               //ȯ��ȸ���ڵ�
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        return sobj;
    }
    // ȯ���� �����ܾ� �Է�
    public DOBJ CALLtac04_s00_save_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtac04_s00_save_INS5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac04_s00_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   REDEM_AMT = dvobj.getRecord().getDouble("REDEM_AMT");   //ȯ�� �ݾ�
        String   REDEM_END_YRMN = dvobj.getRecord().get("REDEM_END_YRMN");   //ȯ�� ���� ���
        String   REDEM_MB_CD = dvobj.getRecord().get("REDEM_MB_CD");   //ȯ��ȸ���ڵ�
        String   REDEM_TAMT = dvobj.getRecord().get("REDEM_TAMT");   //ȯ���������޾�
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        String   REDEM_START_YRMN = dvobj.getRecord().get("REDEM_START_YRMN");   //ȯ�� ���� ���
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //����� ID
        String   INS_DATE = dvobj.getRecord().get("INS_DATE");   //��� �Ͻ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_REDEMREDISTR_BALANCE (INS_DATE, INSPRES_ID, REDEM_START_YRMN, TRNSF_GBN, REDEM_TAMT, REDEM_MB_CD, REDEM_END_YRMN, REDEM_AMT)  \n";
        query +=" values(:INS_DATE , :INSPRES_ID , :REDEM_START_YRMN , :TRNSF_GBN , :REDEM_TAMT , :REDEM_MB_CD , :REDEM_END_YRMN , :REDEM_AMT )";
        sobj.setSql(query);
        sobj.setDouble("REDEM_AMT", REDEM_AMT);               //ȯ�� �ݾ�
        sobj.setString("REDEM_END_YRMN", REDEM_END_YRMN);               //ȯ�� ���� ���
        sobj.setString("REDEM_MB_CD", REDEM_MB_CD);               //ȯ��ȸ���ڵ�
        sobj.setString("REDEM_TAMT", REDEM_TAMT);               //ȯ���������޾�
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("REDEM_START_YRMN", REDEM_START_YRMN);               //ȯ�� ���� ���
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("INS_DATE", INS_DATE);               //��� �Ͻ�
        return sobj;
    }
    // ȯ���� �����ܾ� ����
    public DOBJ CALLtac04_s00_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtac04_s00_save_UPD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac04_s00_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   REDEM_AMT = dvobj.getRecord().getDouble("REDEM_AMT");   //ȯ�� �ݾ�
        String   REDEM_END_YRMN = dvobj.getRecord().get("REDEM_END_YRMN");   //ȯ�� ���� ���
        String   MOD_DATE = dvobj.getRecord().get("MOD_DATE");   //���� �Ͻ�
        String   REDEM_MB_CD = dvobj.getRecord().get("REDEM_MB_CD");   //ȯ��ȸ���ڵ�
        String   REDEM_TAMT = dvobj.getRecord().get("REDEM_TAMT");   //ȯ���������޾�
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        String   REDEM_START_YRMN = dvobj.getRecord().get("REDEM_START_YRMN");   //ȯ�� ���� ���
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_REDEMREDISTR_BALANCE  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , REDEM_START_YRMN=:REDEM_START_YRMN , REDEM_TAMT=:REDEM_TAMT , MOD_DATE=:MOD_DATE , REDEM_END_YRMN=:REDEM_END_YRMN , REDEM_AMT=:REDEM_AMT  \n";
        query +=" where TRNSF_GBN=:TRNSF_GBN  \n";
        query +=" and REDEM_MB_CD=:REDEM_MB_CD";
        sobj.setSql(query);
        sobj.setDouble("REDEM_AMT", REDEM_AMT);               //ȯ�� �ݾ�
        sobj.setString("REDEM_END_YRMN", REDEM_END_YRMN);               //ȯ�� ���� ���
        sobj.setString("MOD_DATE", MOD_DATE);               //���� �Ͻ�
        sobj.setString("REDEM_MB_CD", REDEM_MB_CD);               //ȯ��ȸ���ڵ�
        sobj.setString("REDEM_TAMT", REDEM_TAMT);               //ȯ���������޾�
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("REDEM_START_YRMN", REDEM_START_YRMN);               //ȯ�� ���� ���
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    //##**$$tac04_s00_save
    //##**$$end
}
