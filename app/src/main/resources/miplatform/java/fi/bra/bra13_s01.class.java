
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra13_s01.class
{
    public bra13_s01.class()
    {
    }
    //##**$$staff_plan_save
    /*
    */
    public DOBJ CTLstaff_plan_save(DOBJ dobj)
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
            dobj  = CALLstaff_plan_save_MIUD1(Conn, dobj);           //  �б�
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
    public DOBJ CTLstaff_plan_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLstaff_plan_save_MIUD1(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLstaff_plan_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLstaff_plan_save_INS5(Conn, dobj);           //  �ű�
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLstaff_plan_save_UPD6(Conn, dobj);           //  ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLstaff_plan_save_DEL7(Conn, dobj);           //  ����
            }
        }
        return dobj;
    }
    // ����
    public DOBJ CALLstaff_plan_save_DEL7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLstaff_plan_save_DEL7(dobj, dvobj);
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
    private SQLObject SQLstaff_plan_save_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YYYY = dvobj.getRecord().get("YYYY");   //�⵵
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_COLLECT_PLAN  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and YYYY=:YYYY";
        sobj.setSql(query);
        sobj.setString("YYYY", YYYY);               //�⵵
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        return sobj;
    }
    // �ű�
    public DOBJ CALLstaff_plan_save_INS5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLstaff_plan_save_INS5(dobj, dvobj);
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
    private SQLObject SQLstaff_plan_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   MON7_AMT = dvobj.getRecord().get("MON7_AMT");   //7��
        String   YYYY = dvobj.getRecord().get("YYYY");   //�⵵
        String   MON11_AMT = dvobj.getRecord().get("MON11_AMT");   //11��
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   MON10_AMT = dvobj.getRecord().get("MON10_AMT");   //10��
        String   MON3_AMT = dvobj.getRecord().get("MON3_AMT");   //3��
        String   MON1_AMT = dvobj.getRecord().get("MON1_AMT");   //1��
        String   MON8_AMT = dvobj.getRecord().get("MON8_AMT");   //8��
        String   MON6_AMT = dvobj.getRecord().get("MON6_AMT");   //6��
        String   MON9_AMT = dvobj.getRecord().get("MON9_AMT");   //9��
        double   TOTAL_AMT = dvobj.getRecord().getDouble("TOTAL_AMT");   //�հ� ��
        String   MON2_AMT = dvobj.getRecord().get("MON2_AMT");   //2��
        String   MON12_AMT = dvobj.getRecord().get("MON12_AMT");   //12��
        String   MON4_AMT = dvobj.getRecord().get("MON4_AMT");   //4��
        String   MON5_AMT = dvobj.getRecord().get("MON5_AMT");   //5��
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_COLLECT_PLAN (MON5_AMT, INSPRES_ID, MON4_AMT, MON12_AMT, MON2_AMT, TOTAL_AMT, MON9_AMT, MON6_AMT, MON8_AMT, MON1_AMT, MON3_AMT, INS_DATE, MON10_AMT, STAFF_CD, MON11_AMT, YYYY, MON7_AMT)  \n";
        query +=" values(:MON5_AMT , :INSPRES_ID , :MON4_AMT , :MON12_AMT , :MON2_AMT , :TOTAL_AMT , :MON9_AMT , :MON6_AMT , :MON8_AMT , :MON1_AMT , :MON3_AMT , SYSDATE, :MON10_AMT , :STAFF_CD , :MON11_AMT , :YYYY , :MON7_AMT )";
        sobj.setSql(query);
        sobj.setString("MON7_AMT", MON7_AMT);               //7��
        sobj.setString("YYYY", YYYY);               //�⵵
        sobj.setString("MON11_AMT", MON11_AMT);               //11��
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("MON10_AMT", MON10_AMT);               //10��
        sobj.setString("MON3_AMT", MON3_AMT);               //3��
        sobj.setString("MON1_AMT", MON1_AMT);               //1��
        sobj.setString("MON8_AMT", MON8_AMT);               //8��
        sobj.setString("MON6_AMT", MON6_AMT);               //6��
        sobj.setString("MON9_AMT", MON9_AMT);               //9��
        sobj.setDouble("TOTAL_AMT", TOTAL_AMT);               //�հ� ��
        sobj.setString("MON2_AMT", MON2_AMT);               //2��
        sobj.setString("MON12_AMT", MON12_AMT);               //12��
        sobj.setString("MON4_AMT", MON4_AMT);               //4��
        sobj.setString("MON5_AMT", MON5_AMT);               //5��
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // ����
    public DOBJ CALLstaff_plan_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLstaff_plan_save_UPD6(dobj, dvobj);
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
    private SQLObject SQLstaff_plan_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   MON7_AMT = dvobj.getRecord().get("MON7_AMT");   //7��
        String   YYYY = dvobj.getRecord().get("YYYY");   //�⵵
        String   MON11_AMT = dvobj.getRecord().get("MON11_AMT");   //11��
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   MON10_AMT = dvobj.getRecord().get("MON10_AMT");   //10��
        String   MON3_AMT = dvobj.getRecord().get("MON3_AMT");   //3��
        String   MON1_AMT = dvobj.getRecord().get("MON1_AMT");   //1��
        String   MON8_AMT = dvobj.getRecord().get("MON8_AMT");   //8��
        String   MON6_AMT = dvobj.getRecord().get("MON6_AMT");   //6��
        String   MON9_AMT = dvobj.getRecord().get("MON9_AMT");   //9��
        double   TOTAL_AMT = dvobj.getRecord().getDouble("TOTAL_AMT");   //�հ� ��
        String   MON2_AMT = dvobj.getRecord().get("MON2_AMT");   //2��
        String   MON12_AMT = dvobj.getRecord().get("MON12_AMT");   //12��
        String   MON4_AMT = dvobj.getRecord().get("MON4_AMT");   //4��
        String   MON5_AMT = dvobj.getRecord().get("MON5_AMT");   //5��
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_COLLECT_PLAN  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MON5_AMT=:MON5_AMT , MON4_AMT=:MON4_AMT , MON12_AMT=:MON12_AMT , MON2_AMT=:MON2_AMT , TOTAL_AMT=:TOTAL_AMT , MON9_AMT=:MON9_AMT , MON6_AMT=:MON6_AMT , MON8_AMT=:MON8_AMT , MON1_AMT=:MON1_AMT , MON3_AMT=:MON3_AMT , MON10_AMT=:MON10_AMT , MON11_AMT=:MON11_AMT , MOD_DATE=SYSDATE , MON7_AMT=:MON7_AMT  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and YYYY=:YYYY";
        sobj.setSql(query);
        sobj.setString("MON7_AMT", MON7_AMT);               //7��
        sobj.setString("YYYY", YYYY);               //�⵵
        sobj.setString("MON11_AMT", MON11_AMT);               //11��
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("MON10_AMT", MON10_AMT);               //10��
        sobj.setString("MON3_AMT", MON3_AMT);               //3��
        sobj.setString("MON1_AMT", MON1_AMT);               //1��
        sobj.setString("MON8_AMT", MON8_AMT);               //8��
        sobj.setString("MON6_AMT", MON6_AMT);               //6��
        sobj.setString("MON9_AMT", MON9_AMT);               //9��
        sobj.setDouble("TOTAL_AMT", TOTAL_AMT);               //�հ� ��
        sobj.setString("MON2_AMT", MON2_AMT);               //2��
        sobj.setString("MON12_AMT", MON12_AMT);               //12��
        sobj.setString("MON4_AMT", MON4_AMT);               //4��
        sobj.setString("MON5_AMT", MON5_AMT);               //5��
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    //##**$$staff_plan_save
    //##**$$end
}
