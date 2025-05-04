
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra11_s02
{
    public bra11_s02()
    {
    }
    //##**$$save_depo_day
    /*
    */
    public DOBJ CTLsave_depo_day(DOBJ dobj)
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
            dobj  = CALLsave_depo_day_SEL1(Conn, dobj);           //  �ű�/���� ����
            if( dobj.getRetObject("SEL1").getRecord().get("USE_YN").equals("Y"))
            {
                dobj  = CALLsave_depo_day_SEL6(Conn, dobj);           //  ����������������ȸ
                dobj  = CALLsave_depo_day_UPD3(Conn, dobj);           //  ��������������������
                dobj  = CALLsave_depo_day_INS4(Conn, dobj);           //  ���ο���������Է�
            }
            else
            {
                dobj  = CALLsave_depo_day_UPD5(Conn, dobj);           //  �����������ں���
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
    public DOBJ CTLsave_depo_day( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_depo_day_SEL1(Conn, dobj);           //  �ű�/���� ����
        if( dobj.getRetObject("SEL1").getRecord().get("USE_YN").equals("Y"))
        {
            dobj  = CALLsave_depo_day_SEL6(Conn, dobj);           //  ����������������ȸ
            dobj  = CALLsave_depo_day_UPD3(Conn, dobj);           //  ��������������������
            dobj  = CALLsave_depo_day_INS4(Conn, dobj);           //  ���ο���������Է�
        }
        else
        {
            dobj  = CALLsave_depo_day_UPD5(Conn, dobj);           //  �����������ں���
        }
        return dobj;
    }
    // �ű�/���� ����
    public DOBJ CALLsave_depo_day_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsave_depo_day_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_depo_day_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USE_YN = dobj.getRetObject("S").getRecord().get("USE_YN");   //��뱸��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :USE_YN  AS  USE_YN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("USE_YN", USE_YN);               //��뱸��
        return sobj;
    }
    // ����������������ȸ
    public DOBJ CALLsave_depo_day_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsave_depo_day_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_depo_day_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  END_YRMN  ,  END_DAY  ,  END_DATE  FROM  GIBU.TBRA_DEPO_DAY  WHERE  USE_YN  =  'Y' ";
        sobj.setSql(query);
        return sobj;
    }
    // ��������������������
    public DOBJ CALLsave_depo_day_UPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD3");
        VOBJ dvobj = dobj.getRetObject("SEL6");           //����������������ȸ���� ������Ų OBJECT�Դϴ�.(CALLsave_depo_day_SEL6)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_depo_day_UPD3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD3") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_depo_day_UPD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   USE_YN ="N";   //��뱸��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_DEPO_DAY  \n";
        query +=" set USE_YN=:USE_YN  \n";
        query +=" where END_YRMN=:END_YRMN";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("USE_YN", USE_YN);               //��뱸��
        return sobj;
    }
    // ���ο���������Է�
    public DOBJ CALLsave_depo_day_INS4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_depo_day_INS4(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS4") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_depo_day_INS4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   END_DAY = dvobj.getRecord().get("END_DAY");   //������
        String   END_DATE = dvobj.getRecord().get("END_DATE");   //���� �Ͻ�
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   USE_YN = dvobj.getRecord().get("USE_YN");   //��뱸��
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_DEPO_DAY (USE_YN, INS_DATE, INSPRES_ID, END_YRMN, END_DATE, END_DAY)  \n";
        query +=" values(:USE_YN , SYSDATE, :INSPRES_ID , :END_YRMN , :END_DATE , :END_DAY )";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("END_DATE", END_DATE);               //���� �Ͻ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("USE_YN", USE_YN);               //��뱸��
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // �����������ں���
    public DOBJ CALLsave_depo_day_UPD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_depo_day_UPD5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_depo_day_UPD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   END_DAY = dvobj.getRecord().get("END_DAY");   //������
        String   END_DATE = dvobj.getRecord().get("END_DATE");   //���� �Ͻ�
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_DEPO_DAY  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MOD_DATE=SYSDATE , END_DATE=:END_DATE , END_DAY=:END_DAY  \n";
        query +=" where END_YRMN=:END_YRMN";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("END_DATE", END_DATE);               //���� �Ͻ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    //##**$$save_depo_day
    //##**$$sel_depo_day
    /*
    */
    public DOBJ CTLsel_depo_day(DOBJ dobj)
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
            dobj  = CALLsel_depo_day_SEL1(Conn, dobj);           //  �Աݸ������� ��ȸ
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
    public DOBJ CTLsel_depo_day( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_depo_day_SEL1(Conn, dobj);           //  �Աݸ������� ��ȸ
        return dobj;
    }
    // �Աݸ������� ��ȸ
    public DOBJ CALLsel_depo_day_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_depo_day_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_depo_day_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  END_YRMN  ,  END_DAY  ,  END_DATE  ,  USE_YN  FROM  GIBU.TBRA_DEPO_DAY  WHERE  END_YRMN  =  :END_YRMN ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //������
        return sobj;
    }
    //##**$$sel_depo_day
    //##**$$end
}
