
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s10
{
    public bra01_s10()
    {
    }
    //##**$$memo_search
    /* ��ϵ� �޸� ������ ��ȸ��
    ���� ������ MEMO_CD ���� ������ MEMO_CD ���� 1,2,.....10,11 �̷������� ���� ������ ������ ����� �ȵ�
    ==> INS_DATE ���� ���ķ� ��ħ (09.11.26)
    */
    public DOBJ CTLmemo_search(DOBJ dobj)
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
            dobj  = CALLmemo_search_SEL1(Conn, dobj);           //  �޸� ��ȸ
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
    public DOBJ CTLmemo_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmemo_search_SEL1(Conn, dobj);           //  �޸� ��ȸ
        return dobj;
    }
    // �޸� ��ȸ
    // ��ϵ� �޸� ������ ��ȸ�Ѵ�.
    public DOBJ CALLmemo_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmemo_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmemo_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MEMO_CD,  MEMO_GBN,  CTENT  ,'1'  IO_CHK  FROM  GIBU.TBRA_MEMO_MNG  ORDER  BY  INS_DATE ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$memo_search
    //##**$$memo_save
    /* * ���α׷��� : bra01_s10
    * �ۼ��� : �̱���
    * �ۼ��� : 2009/11/02
    * ���� :
    �޸������� �űԷ� �����ϰų� ���� �����Ѵ�
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLmemo_save(DOBJ dobj)
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
            dobj  = CALLmemo_save_MIUD1(Conn, dobj);           //  �޸�����
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
    public DOBJ CTLmemo_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmemo_save_MIUD1(Conn, dobj);           //  �޸�����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �޸�����
    // �޸� ������ �����Ѵ�.
    public DOBJ CALLmemo_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLmemo_save_SEL10(Conn, dobj);           //  ���������ϱ�
                dobj  = CALLmemo_save_INS5(Conn, dobj);           //  �޸� ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmemo_save_UPD6(Conn, dobj);           //  �޸� ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmemo_save_DEL7(Conn, dobj);           //  �޸� ����
            }
        }
        return dobj;
    }
    // �޸� ����
    // �����޸� �����Ѵ�.
    public DOBJ CALLmemo_save_DEL7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLmemo_save_DEL7(dobj, dvobj);
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
    private SQLObject SQLmemo_save_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MEMO_CD = dvobj.getRecord().get("MEMO_CD");   //�޸� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_MEMO_MNG  \n";
        query +=" where MEMO_CD=:MEMO_CD";
        sobj.setSql(query);
        sobj.setString("MEMO_CD", MEMO_CD);               //�޸� �ڵ�
        return sobj;
    }
    // �޸� ����
    // �����޸� �����Ѵ�.
    public DOBJ CALLmemo_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLmemo_save_UPD6(dobj, dvobj);
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
    private SQLObject SQLmemo_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   MEMO_GBN = dvobj.getRecord().get("MEMO_GBN");   //�޸� ����
        String   CTENT = dvobj.getRecord().get("CTENT");   //����
        String   MEMO_CD = dvobj.getRecord().get("MEMO_CD");   //�޸� �ڵ�
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_MEMO_MNG  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MOD_DATE=SYSDATE , CTENT=:CTENT , MEMO_GBN=:MEMO_GBN  \n";
        query +=" where MEMO_CD=:MEMO_CD";
        sobj.setSql(query);
        sobj.setString("MEMO_GBN", MEMO_GBN);               //�޸� ����
        sobj.setString("CTENT", CTENT);               //����
        sobj.setString("MEMO_CD", MEMO_CD);               //�޸� �ڵ�
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // ���������ϱ�
    // �޸��� �Ϸù�ȣ�� �����Ѵ�.
    public DOBJ CALLmemo_save_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmemo_save_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmemo_save_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(TO_NUMBER(MEMO_CD)),  0)  +  1  MEMO_CD  FROM  GIBU.TBRA_MEMO_MNG ";
        sobj.setSql(query);
        return sobj;
    }
    // �޸� ����
    // �űԸ޸� ����Ѵ�.
    public DOBJ CALLmemo_save_INS5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLmemo_save_INS5(dobj, dvobj);
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
    private SQLObject SQLmemo_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   MEMO_GBN = dvobj.getRecord().get("MEMO_GBN");   //�޸� ����
        String   CTENT = dvobj.getRecord().get("CTENT");   //����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   MEMO_CD = dobj.getRetObject("SEL10").getRecord().get("MEMO_CD");   //�޸� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_MEMO_MNG (INS_DATE, INSPRES_ID, MEMO_CD, CTENT, MEMO_GBN)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :MEMO_CD , :CTENT , :MEMO_GBN )";
        sobj.setSql(query);
        sobj.setString("MEMO_GBN", MEMO_GBN);               //�޸� ����
        sobj.setString("CTENT", CTENT);               //����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("MEMO_CD", MEMO_CD);               //�޸� �ڵ�
        return sobj;
    }
    //##**$$memo_save
    //##**$$end
}
