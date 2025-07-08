
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac03_s08
{
    public tac03_s08()
    {
    }
    //##**$$save_except
    /*
    */
    public DOBJ CTLsave_except(DOBJ dobj)
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
            dobj  = CALLsave_except_MIUD1(Conn, dobj);           //  MIUD
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
    public DOBJ CTLsave_except( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_except_MIUD1(Conn, dobj);           //  MIUD
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // MIUD
    public DOBJ CALLsave_except_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLsave_except_INS5(Conn, dobj);           //  ��ȸ�������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_except_UPD6(Conn, dobj);           //  ��ȸ���� ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_except_DEL7(Conn, dobj);           //  ��ȸ���� ����
            }
        }
        return dobj;
    }
    // ��ȸ���� ����
    public DOBJ CALLsave_except_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_except_DEL7(dobj, dvobj);
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
    private SQLObject SQLsave_except_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //���� ���
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        String   EXCEPT_GBN = dvobj.getRecord().get("EXCEPT_GBN");   //��ȸ��������
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_EXCEPTAMT  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and EXCEPT_GBN=:EXCEPT_GBN  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN  \n";
        query +=" and SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("EXCEPT_GBN", EXCEPT_GBN);               //��ȸ��������
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    // ��ȸ�������
    public DOBJ CALLsave_except_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_except_INS5(dobj, dvobj);
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
    private SQLObject SQLsave_except_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //����� ID
        String INS_DATE ="";        //��� �Ͻ�
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //���� ���
        String   REMAK_CTENT = dvobj.getRecord().get("REMAK_CTENT");   //��� ����
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        double   OCC_AMT = dvobj.getRecord().getDouble("OCC_AMT");   //�߻� �ݾ�
        String   EXCEPT_GBN = dvobj.getRecord().get("EXCEPT_GBN");   //��ȸ��������
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_EXCEPTAMT (MB_CD, EXCEPT_GBN, OCC_AMT, TRNSF_GBN, REMAK_CTENT, SUPP_YRMN)  \n";
        query +=" values(:MB_CD , :EXCEPT_GBN , :OCC_AMT , :TRNSF_GBN , :REMAK_CTENT , :SUPP_YRMN )";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        sobj.setString("REMAK_CTENT", REMAK_CTENT);               //��� ����
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setDouble("OCC_AMT", OCC_AMT);               //�߻� �ݾ�
        sobj.setString("EXCEPT_GBN", EXCEPT_GBN);               //��ȸ��������
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    // ��ȸ���� ����
    public DOBJ CALLsave_except_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_except_UPD6(dobj, dvobj);
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
    private SQLObject SQLsave_except_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //������ ID
        String MOD_DATE ="";        //���� �Ͻ�
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //���� ���
        String   REMAK_CTENT = dvobj.getRecord().get("REMAK_CTENT");   //��� ����
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        double   OCC_AMT = dvobj.getRecord().getDouble("OCC_AMT");   //�߻� �ݾ�
        String   EXCEPT_GBN = dvobj.getRecord().get("EXCEPT_GBN");   //��ȸ��������
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_EXCEPTAMT  \n";
        query +=" set OCC_AMT=:OCC_AMT , REMAK_CTENT=:REMAK_CTENT  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and EXCEPT_GBN=:EXCEPT_GBN  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN  \n";
        query +=" and SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        sobj.setString("REMAK_CTENT", REMAK_CTENT);               //��� ����
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setDouble("OCC_AMT", OCC_AMT);               //�߻� �ݾ�
        sobj.setString("EXCEPT_GBN", EXCEPT_GBN);               //��ȸ��������
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    //##**$$save_except
    //##**$$search_except
    /*
    */
    public DOBJ CTLsearch_except(DOBJ dobj)
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
            dobj  = CALLsearch_except_SEL1(Conn, dobj);           //  ��ȸ�������к���ȸ
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
    public DOBJ CTLsearch_except( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_except_SEL1(Conn, dobj);           //  ��ȸ�������к���ȸ
        return dobj;
    }
    // ��ȸ�������к���ȸ
    public DOBJ CALLsearch_except_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_except_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_except_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   EXCEPT_GBN = dvobj.getRecord().get("EXCEPT_GBN");   //��ȸ��������
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT SUPP_YRMN , A.MB_CD , B.HANMB_NM MB_NM, TRNSF_GBN, EXCEPT_GBN , OCC_AMT, A.REMAK_CTENT  ";
        query +=" FROM FIDU.TTAC_EXCEPTAMT A, FIDU.TMEM_MB B  ";
        query +=" WHERE SUPP_YRMN =:SUPP_YRMN  ";
        query +=" AND EXCEPT_GBN =:EXCEPT_GBN  ";
        query +=" AND A.MB_CD = B.MB_CD  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND (A.MB_CD =:MB_CD)  ";
        }
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        }
        sobj.setString("EXCEPT_GBN", EXCEPT_GBN);               //��ȸ��������
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    //##**$$search_except
    //##**$$end
}
