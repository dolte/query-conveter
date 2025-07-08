
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tec03_s07
{
    public tec03_s07()
    {
    }
    //##**$$mngcomis_totalsave
    /* * ���α׷��� : tec03_s07
    * �ۼ��� : �̼���
    * �ۼ��� : 2009/10/06
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLmngcomis_totalsave(DOBJ dobj)
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
            dobj  = CALLmngcomis_totalsave_XIUD2(Conn, dobj);           //  ��������������
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
    public DOBJ CTLmngcomis_totalsave( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmngcomis_totalsave_XIUD2(Conn, dobj);           //  ��������������
        return dobj;
    }
    // ��������������
    public DOBJ CALLmngcomis_totalsave_XIUD2(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLmngcomis_totalsave_XIUD2(dobj, dvobj);
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
    private SQLObject SQLmngcomis_totalsave_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //���� ���
        String   AVECLASS_CD = dobj.getRetObject("S").getRecord().get("AVECLASS_CD");   //��ü�ߺз�
        String   LARGECLASS_CD = dobj.getRetObject("S").getRecord().get("LARGECLASS_CD");   //��з� �ڵ�
        double   MNGCOMIS_RATE = dobj.getRetObject("S").getRecord().getDouble("MNGCOMIS_RATE");   //����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE FIDU.tenv_mngcomis  \n";
        query +=" SET MNGCOMIS_RATE = :MNGCOMIS_RATE  \n";
        query +=" WHERE 1 = 1  \n";
        query +=" AND APPL_YRMN = :APPL_YRMN  \n";
        query +=" AND LARGECLASS_CD = :LARGECLASS_CD  \n";
        query +=" AND AVECLASS_CD = :AVECLASS_CD";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        sobj.setString("AVECLASS_CD", AVECLASS_CD);               //��ü�ߺз�
        sobj.setString("LARGECLASS_CD", LARGECLASS_CD);               //��з� �ڵ�
        sobj.setDouble("MNGCOMIS_RATE", MNGCOMIS_RATE);               //����
        return sobj;
    }
    //##**$$mngcomis_totalsave
    //##**$$mngcomis_save
    /* * ���α׷��� : tec03_s07
    * �ۼ��� : �̼���
    * �ۼ��� : 2009/10/06
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLmngcomis_save(DOBJ dobj)
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
            dobj  = CALLmngcomis_save_MIUD1(Conn, dobj);           //  ���������
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
    public DOBJ CTLmngcomis_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmngcomis_save_MIUD1(Conn, dobj);           //  ���������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ���������
    public DOBJ CALLmngcomis_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MIUD1");
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmngcomis_save_INS5(Conn, dobj);           //  ���������Է�
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmngcomis_save_UPD6(Conn, dobj);           //  ������������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmngcomis_save_DEL7(Conn, dobj);           //  ������������
            }
        }
        return dobj;
    }
    // ������������
    public DOBJ CALLmngcomis_save_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL7");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmngcomis_save_DEL7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        rvobj.Println("DEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmngcomis_save_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SCLASS_CD = dvobj.getRecord().get("SCLASS_CD");   //��ü��з��ڵ�
        double   MNGCOMIS_RATE = dvobj.getRecord().getDouble("MNGCOMIS_RATE");   //����
        String   AVECLASS_CD = dvobj.getRecord().get("AVECLASS_CD");   //��ü�ߺз�
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //��ü �ڵ�
        String   APPL_YRMN = dvobj.getRecord().get("APPL_YRMN");   //���� ���
        String   LARGECLASS_CD = dvobj.getRecord().get("LARGECLASS_CD");   //��з� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TENV_MNGCOMIS  \n";
        query +=" where LARGECLASS_CD=:LARGECLASS_CD  \n";
        query +=" and APPL_YRMN=:APPL_YRMN  \n";
        query +=" and MDM_CD=:MDM_CD  \n";
        query +=" and AVECLASS_CD=:AVECLASS_CD  \n";
        query +=" and MNGCOMIS_RATE=:MNGCOMIS_RATE  \n";
        query +=" and SCLASS_CD=:SCLASS_CD";
        sobj.setSql(query);
        sobj.setString("SCLASS_CD", SCLASS_CD);               //��ü��з��ڵ�
        sobj.setDouble("MNGCOMIS_RATE", MNGCOMIS_RATE);               //����
        sobj.setString("AVECLASS_CD", AVECLASS_CD);               //��ü�ߺз�
        sobj.setString("MDM_CD", MDM_CD);               //��ü �ڵ�
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        sobj.setString("LARGECLASS_CD", LARGECLASS_CD);               //��з� �ڵ�
        return sobj;
    }
    // ���������Է�
    public DOBJ CALLmngcomis_save_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmngcomis_save_INS5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        rvobj.Println("INS5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmngcomis_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SCLASS_CD = dvobj.getRecord().get("SCLASS_CD");   //��ü��з��ڵ�
        double   MNGCOMIS_RATE = dvobj.getRecord().getDouble("MNGCOMIS_RATE");   //����
        String   AVECLASS_CD = dvobj.getRecord().get("AVECLASS_CD");   //��ü�ߺз�
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //��ü �ڵ�
        String   APPL_YRMN = dvobj.getRecord().get("APPL_YRMN");   //���� ���
        String   LARGECLASS_CD = dvobj.getRecord().get("LARGECLASS_CD");   //��з� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TENV_MNGCOMIS (LARGECLASS_CD, APPL_YRMN, MDM_CD, AVECLASS_CD, MNGCOMIS_RATE, SCLASS_CD)  \n";
        query +=" values(:LARGECLASS_CD , :APPL_YRMN , :MDM_CD , :AVECLASS_CD , :MNGCOMIS_RATE , :SCLASS_CD )";
        sobj.setSql(query);
        sobj.setString("SCLASS_CD", SCLASS_CD);               //��ü��з��ڵ�
        sobj.setDouble("MNGCOMIS_RATE", MNGCOMIS_RATE);               //����
        sobj.setString("AVECLASS_CD", AVECLASS_CD);               //��ü�ߺз�
        sobj.setString("MDM_CD", MDM_CD);               //��ü �ڵ�
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        sobj.setString("LARGECLASS_CD", LARGECLASS_CD);               //��з� �ڵ�
        return sobj;
    }
    // ������������
    public DOBJ CALLmngcomis_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD6");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmngcomis_save_UPD6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        rvobj.Println("UPD6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmngcomis_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SCLASS_CD = dvobj.getRecord().get("SCLASS_CD");   //��ü��з��ڵ�
        double   MNGCOMIS_RATE = dvobj.getRecord().getDouble("MNGCOMIS_RATE");   //����
        String   AVECLASS_CD = dvobj.getRecord().get("AVECLASS_CD");   //��ü�ߺз�
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //��ü �ڵ�
        String   APPL_YRMN = dvobj.getRecord().get("APPL_YRMN");   //���� ���
        String   LARGECLASS_CD = dvobj.getRecord().get("LARGECLASS_CD");   //��з� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TENV_MNGCOMIS  \n";
        query +=" set MNGCOMIS_RATE=:MNGCOMIS_RATE  \n";
        query +=" where LARGECLASS_CD=:LARGECLASS_CD  \n";
        query +=" and APPL_YRMN=:APPL_YRMN  \n";
        query +=" and MDM_CD=:MDM_CD  \n";
        query +=" and AVECLASS_CD=:AVECLASS_CD  \n";
        query +=" and SCLASS_CD=:SCLASS_CD";
        sobj.setSql(query);
        sobj.setString("SCLASS_CD", SCLASS_CD);               //��ü��з��ڵ�
        sobj.setDouble("MNGCOMIS_RATE", MNGCOMIS_RATE);               //����
        sobj.setString("AVECLASS_CD", AVECLASS_CD);               //��ü�ߺз�
        sobj.setString("MDM_CD", MDM_CD);               //��ü �ڵ�
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        sobj.setString("LARGECLASS_CD", LARGECLASS_CD);               //��з� �ڵ�
        return sobj;
    }
    //##**$$mngcomis_save
    //##**$$mngcomis_sel
    /* * ���α׷��� : tec03_s07
    * �ۼ��� : ����ȯ
    * �ۼ��� : 2009/11/18
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLmngcomis_sel(DOBJ dobj)
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
            dobj  = CALLmngcomis_sel_SEL1(Conn, dobj);           //  ����������ȸ
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
    public DOBJ CTLmngcomis_sel( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmngcomis_sel_SEL1(Conn, dobj);           //  ����������ȸ
        return dobj;
    }
    // ����������ȸ
    public DOBJ CALLmngcomis_sel_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmngcomis_sel_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmngcomis_sel_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   LARGECLASS_CD = dobj.getRetObject("S").getRecord().get("LARGECLASS_CD");   //��з� �ڵ�
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //���� ���
        String   MDM_CD = dobj.getRetObject("S").getRecord().get("MDM_CD");   //��ü �ڵ�
        String   AVECLASS_CD = dobj.getRetObject("S").getRecord().get("AVECLASS_CD");   //��ü�ߺз�
        String   SCLASS_CD = dobj.getRetObject("S").getRecord().get("SCLASS_CD");   //��ü��з��ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.LARGECLASS_CD, A.LARGECLASS_CD_NM, A.AVECLASS_CD, A.AVECLASS_CD_NM, A.SCLASS_CD, A.SCLASS_CD_NM, A.MDM_CD, A.MDM_CD_NM, :APPL_YRMN AS APPL_YRMN, B.MNGCOMIS_RATE  ";
        query +=" FROM FIDU.TENV_MDMCD A ,FIDU.TENV_MNGCOMIS B , fidu.tenv_largeclasscd c, FIDU.TENV_AVECLASSCD d, FIDU.TENV_SCLASSCD f  ";
        query +=" WHERE A.LARGECLASS_CD = B.LARGECLASS_CD  ";
        query +=" AND A.AVECLASS_CD = B.AVECLASS_CD  ";
        query +=" AND A.SCLASS_CD = B.SCLASS_CD  ";
        query +=" AND A.MDM_CD = B.MDM_CD  ";
        query +=" AND B.APPL_YRMN = :APPL_YRMN  ";
        query +=" AND A.LARGECLASS_CD = C.LARGECLASS_CD  ";
        query +=" AND A.AVECLASS_CD = D.AVECLASS_CD  ";
        query +=" AND A.SCLASS_CD = F.SCLASS_CD  ";
        if( !LARGECLASS_CD.equals("0"))
        {
            query +=" AND A.LARGECLASS_CD = :LARGECLASS_CD  ";
        }
        if( !AVECLASS_CD.equals("0"))
        {
            query +=" AND A.AVECLASS_CD = :AVECLASS_CD  ";
        }
        if( !SCLASS_CD.equals("0"))
        {
            query +=" AND A.SCLASS_CD = :SCLASS_CD  ";
        }
        if( !MDM_CD.equals("0"))
        {
            query +=" AND A.MDM_CD = :MDM_CD  ";
        }
        query +=" ORDER BY C.SORT_SEQ, D.SORT_SEQ, F.SORT_SEQ, A.SORT_SEQ  ";
        sobj.setSql(query);
        if( !LARGECLASS_CD.equals("0"))
        {
            sobj.setString("LARGECLASS_CD", LARGECLASS_CD);               //��з� �ڵ�
        }
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        if( !MDM_CD.equals("0"))
        {
            sobj.setString("MDM_CD", MDM_CD);               //��ü �ڵ�
        }
        if( !AVECLASS_CD.equals("0"))
        {
            sobj.setString("AVECLASS_CD", AVECLASS_CD);               //��ü�ߺз�
        }
        if( !SCLASS_CD.equals("0"))
        {
            sobj.setString("SCLASS_CD", SCLASS_CD);               //��ü��з��ڵ�
        }
        return sobj;
    }
    //##**$$mngcomis_sel
    //##**$$end
}
