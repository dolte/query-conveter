
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s22_10
{
    public bra01_s22_10()
    {
    }
    //##**$$save_log_data
    /*
    */
    public DOBJ CTLsave_log_data(DOBJ dobj)
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
            dobj  = CALLsave_log_data_DEL22(Conn, dobj);           //  �¿������ֱ���������
            dobj  = CALLsave_log_data_MPD24(Conn, dobj);           //  ���ֱ�¿�������
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLsave_log_data_UPD27(Conn, dobj);           //  ������ �����Ұ����ֱ� ����
            dobj  = CALLsave_log_data_SEL9(Conn, dobj);           //  �¿���������ȸ
            if( dobj.getRetObject("SEL9").getRecordCnt() == 1)
            {
                dobj  = CALLsave_log_data_UPD10(Conn, dobj);           //  ���� �¿�����������
            }
            else if( dobj.getRetObject("SEL9").getRecordCnt() > 1)
            {
                dobj  = CALLsave_log_data_UPD17(Conn, dobj);           //  ���� �¿�����������
            }
            else
            {
                dobj  = CALLsave_log_data_UPD18(Conn, dobj);           //  ���� �¿�����������
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
    public DOBJ CTLsave_log_data( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_log_data_DEL22(Conn, dobj);           //  �¿������ֱ���������
        dobj  = CALLsave_log_data_MPD24(Conn, dobj);           //  ���ֱ�¿�������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLsave_log_data_UPD27(Conn, dobj);           //  ������ �����Ұ����ֱ� ����
        dobj  = CALLsave_log_data_SEL9(Conn, dobj);           //  �¿���������ȸ
        if( dobj.getRetObject("SEL9").getRecordCnt() == 1)
        {
            dobj  = CALLsave_log_data_UPD10(Conn, dobj);           //  ���� �¿�����������
        }
        else if( dobj.getRetObject("SEL9").getRecordCnt() > 1)
        {
            dobj  = CALLsave_log_data_UPD17(Conn, dobj);           //  ���� �¿�����������
        }
        else
        {
            dobj  = CALLsave_log_data_UPD18(Conn, dobj);           //  ���� �¿�����������
        }
        return dobj;
    }
    // �¿������ֱ���������
    // �ش� ������ ���� �¿��� ���ֱ� ������ �����Ѵ�.
    public DOBJ CALLsave_log_data_DEL22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL22");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_log_data_DEL22(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL22") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_log_data_DEL22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_ONOFF_INFO  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ֱ�¿�������
    public DOBJ CALLsave_log_data_MPD24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD24");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MPD24");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("UPSO_CD").equals(""))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_log_data_INS16(Conn, dobj);           //  �¿������ֱ��������
            }
        }
        return dobj;
    }
    // �¿������ֱ��������
    // ���ο� �¿��� ���ֱ� ������ ����Ѵ�.
    public DOBJ CALLsave_log_data_INS16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS16");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_log_data_INS16(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS16") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_log_data_INS16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   ONOFF_GBN = dvobj.getRecord().get("ONOFF_GBN");   //�¿��� ����
        int   ACMCN_DAESU = dvobj.getRecord().getInt("ACMCN_DAESU");   //���ֱ� ���
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //�� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_ONOFF_INFO (INS_DATE, MODEL_CD, INSPRES_ID, ACMCN_DAESU, ONOFF_GBN, UPSO_CD)  \n";
        query +=" values(SYSDATE, :MODEL_CD , :INSPRES_ID , :ACMCN_DAESU , :ONOFF_GBN , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //�¿��� ����
        sobj.setInt("ACMCN_DAESU", ACMCN_DAESU);               //���ֱ� ���
        sobj.setString("MODEL_CD", MODEL_CD);               //�� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // ������ �����Ұ����ֱ� ����
    // �����Ұ����ֱ� ������ �� ����
    public DOBJ CALLsave_log_data_UPD27(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD27");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("UPD27");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_log_data_UPD27(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD27") ;
        rvobj.Println("UPD27");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_log_data_UPD27(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   COL_MCH_YN = dvobj.getRecord().get("COL_MCH_YN");
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.SDB_TBRA_UPSO  \n";
        query +=" set COL_MCH_YN=:COL_MCH_YN  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("COL_MCH_YN", COL_MCH_YN);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �¿���������ȸ
    // �ش� ������ �¿��� ������ ��ȸ�Ѵ�.
    public DOBJ CALLsave_log_data_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsave_log_data_SEL9(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        rvobj.Println("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_log_data_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S1").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  ONOFF_GBN  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �¿�����������
    // �¿��� ������ 1���� ��� �ش� �¿��� ������ �����Ѵ�.
    public DOBJ CALLsave_log_data_UPD10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD10");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("UPD10");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_log_data_UPD10(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD10") ;
        rvobj.Println("UPD10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_log_data_UPD10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //������ ID
        String   ONOFF_GBN = dobj.getRetObject("SEL9").getRecord().get("ONOFF_GBN");   //�¿��� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ONOFF_GBN=:ONOFF_GBN , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //�¿��� ����
        return sobj;
    }
    // ���� �¿�����������
    // �¿��������� 2�� �̻��ϰ�쿡�� �¶������� ó���Ѵ�
    public DOBJ CALLsave_log_data_UPD17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD17");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("UPD17");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_log_data_UPD17(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD17") ;
        rvobj.Println("UPD17");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_log_data_UPD17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //������ ID
        String   ONOFF_GBN ="O";   //�¿��� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ONOFF_GBN=:ONOFF_GBN , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //�¿��� ����
        return sobj;
    }
    // ���� �¿�����������
    // �׿��� ���
    public DOBJ CALLsave_log_data_UPD18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD18");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("UPD18");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_log_data_UPD18(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD18") ;
        rvobj.Println("UPD18");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_log_data_UPD18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //������ ID
        String   ONOFF_GBN ="";   //�¿��� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ONOFF_GBN=:ONOFF_GBN , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //�¿��� ����
        return sobj;
    }
    //##**$$save_log_data
    //##**$$end
}
