
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s10
{
    public bra10_s10()
    {
    }
    //##**$$save_mobile_udtkpres
    /*
    */
    public DOBJ CTLsave_mobile_udtkpres(DOBJ dobj)
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
            dobj  = CALLsave_mobile_udtkpres_MIUD1(Conn, dobj);           //  �б�
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
    public DOBJ CTLsave_mobile_udtkpres( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_mobile_udtkpres_MIUD1(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLsave_mobile_udtkpres_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLsave_mobile_udtkpres_INS7(Conn, dobj);           //  ����� ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_mobile_udtkpres_UPD6(Conn, dobj);           //  ����� ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_mobile_udtkpres_DEL7(Conn, dobj);           //  ����� ����
            }
        }
        return dobj;
    }
    // ����� ����
    public DOBJ CALLsave_mobile_udtkpres_DEL7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsave_mobile_udtkpres_DEL7(dobj, dvobj);
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
    private SQLObject SQLsave_mobile_udtkpres_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UDTKPRES_ID = dvobj.getRecord().get("UDTKPRES_ID");   //����� ID
        String   DEPT_CD = dvobj.getRecord().get("DEPT_CD");   //�μ� �ڵ�
        String   GBN ="A";   //����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BILL_AUTH  \n";
        query +=" where DEPT_CD=:DEPT_CD  \n";
        query +=" and UDTKPRES_ID=:UDTKPRES_ID  \n";
        query +=" and GBN=:GBN";
        sobj.setSql(query);
        sobj.setString("UDTKPRES_ID", UDTKPRES_ID);               //����� ID
        sobj.setString("DEPT_CD", DEPT_CD);               //�μ� �ڵ�
        sobj.setString("GBN", GBN);               //����
        return sobj;
    }
    // ����� ����
    public DOBJ CALLsave_mobile_udtkpres_INS7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_mobile_udtkpres_INS7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_mobile_udtkpres_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   UDTKPRES_ID = dvobj.getRecord().get("UDTKPRES_ID");   //����� ID
        String   DEPT_CD = dvobj.getRecord().get("DEPT_CD");   //�μ� �ڵ�
        String   USER_PWD = dvobj.getRecord().get("USER_PWD");   //����ھ�ȣ
        String   GBN ="A";   //����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BILL_AUTH (USER_PWD, INS_DATE, INSPRES_ID, DEPT_CD, UDTKPRES_ID, GBN)  \n";
        query +=" values(:USER_PWD , SYSDATE, :INSPRES_ID , :DEPT_CD , :UDTKPRES_ID , :GBN )";
        sobj.setSql(query);
        sobj.setString("UDTKPRES_ID", UDTKPRES_ID);               //����� ID
        sobj.setString("DEPT_CD", DEPT_CD);               //�μ� �ڵ�
        sobj.setString("USER_PWD", USER_PWD);               //����ھ�ȣ
        sobj.setString("GBN", GBN);               //����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // ����� ����
    public DOBJ CALLsave_mobile_udtkpres_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsave_mobile_udtkpres_UPD6(dobj, dvobj);
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
    private SQLObject SQLsave_mobile_udtkpres_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   UDTKPRES_ID = dvobj.getRecord().get("UDTKPRES_ID");   //����� ID
        String   DEPT_CD = dvobj.getRecord().get("DEPT_CD");   //�μ� �ڵ�
        String   USER_PWD = dvobj.getRecord().get("USER_PWD");   //����ھ�ȣ
        String   GBN ="A";   //����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BILL_AUTH  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , USER_PWD=:USER_PWD , MOD_DATE=SYSDATE  \n";
        query +=" where DEPT_CD=:DEPT_CD  \n";
        query +=" and UDTKPRES_ID=:UDTKPRES_ID  \n";
        query +=" and GBN=:GBN";
        sobj.setSql(query);
        sobj.setString("UDTKPRES_ID", UDTKPRES_ID);               //����� ID
        sobj.setString("DEPT_CD", DEPT_CD);               //�μ� �ڵ�
        sobj.setString("USER_PWD", USER_PWD);               //����ھ�ȣ
        sobj.setString("GBN", GBN);               //����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    //##**$$save_mobile_udtkpres
    //##**$$sel_mobile_udtkpres
    /*
    */
    public DOBJ CTLsel_mobile_udtkpres(DOBJ dobj)
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
            dobj  = CALLsel_mobile_udtkpres_SEL1(Conn, dobj);           //  ���κ� ����� ��ȸ
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
    public DOBJ CTLsel_mobile_udtkpres( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_mobile_udtkpres_SEL1(Conn, dobj);           //  ���κ� ����� ��ȸ
        return dobj;
    }
    // ���κ� ����� ��ȸ
    public DOBJ CALLsel_mobile_udtkpres_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_mobile_udtkpres_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_mobile_udtkpres_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEPT_CD  AS  DEPT_CD  ,   \n";
        query +=" (SELECT  DEPT_NM  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =  A.DEPT_CD)  AS  DEPT_NM  ,  A.UDTKPRES_ID  AS  UDTKPRES_ID  ,  A.USER_PWD  AS  USER_PWD  ,  A.INSPRES_ID  AS  INSPRES_ID  ,   \n";
        query +=" (SELECT  HAN_NM  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  A.INSPRES_ID)  AS  INSPRES_NM  ,  A.INS_DATE  AS  INS_DATE  ,  A.MODPRES_ID  AS  MODPRES_ID  ,   \n";
        query +=" (SELECT  HAN_NM  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  A.MODPRES_ID)  AS  MODPRES_NM  ,  A.MOD_DATE  AS  MOD_DATE  FROM  GIBU.TBRA_BILL_AUTH  A  WHERE  GBN  =  'A'  ORDER  BY  A.DEPT_CD  ASC,  MOD_DATE  DESC,  INS_DATE  DESC ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$sel_mobile_udtkpres
    //##**$$end
}
