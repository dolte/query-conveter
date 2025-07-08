
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac06_s01
{
    public tac06_s01()
    {
    }
    //##**$$checkDup_mem
    /*
    */
    public DOBJ CTLcheckDup_mem(DOBJ dobj)
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
            dobj  = CALLcheckDup_mem_SEL1(Conn, dobj);           //  �ߺ���ȸ
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
    public DOBJ CTLcheckDup_mem( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcheckDup_mem_SEL1(Conn, dobj);           //  �ߺ���ȸ
        return dobj;
    }
    // �ߺ���ȸ
    public DOBJ CALLcheckDup_mem_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLcheckDup_mem_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcheckDup_mem_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   TO = dobj.getRetObject("S").getRecord().get("TO");   //��������
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //�絵 ����
        String   FROM = dobj.getRetObject("S").getRecord().get("FROM");   //��������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  FIDU.TMEM_SUPPSUSP  WHERE  MB_CD  =  :MB_CD   \n";
        query +=" AND  TRNSF_GBN  =  :TRNSF_GBN   \n";
        query +=" AND  (SUPPSUSP_START_DAY  BETWEEN  :FROM   \n";
        query +=" AND  :TO   \n";
        query +=" OR  SUPPSUSP_RELS_DAY  BETWEEN  :FROM   \n";
        query +=" AND  :TO   \n";
        query +=" OR  :FROM  BETWEEN  SUPPSUSP_START_DAY   \n";
        query +=" AND  SUPPSUSP_RELS_DAY   \n";
        query +=" OR  :TO  BETWEEN  SUPPSUSP_START_DAY   \n";
        query +=" AND  SUPPSUSP_RELS_DAY) ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("TO", TO);               //��������
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("FROM", FROM);               //��������
        return sobj;
    }
    //##**$$checkDup_mem
    //##**$$checkDup_mdm
    /*
    */
    public DOBJ CTLcheckDup_mdm(DOBJ dobj)
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
            dobj  = CALLcheckDup_mdm_SEL1(Conn, dobj);           //  �ߺ���ȸ
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
    public DOBJ CTLcheckDup_mdm( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcheckDup_mdm_SEL1(Conn, dobj);           //  �ߺ���ȸ
        return dobj;
    }
    // �ߺ���ȸ
    public DOBJ CALLcheckDup_mdm_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLcheckDup_mdm_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcheckDup_mdm_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   TO = dobj.getRetObject("S").getRecord().get("TO");   //��������
        String   CLASS_CD = dobj.getRetObject("S").getRecord().get("CLASS_CD");   //�з� �ڵ�
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //�絵 ����
        String   FROM = dobj.getRetObject("S").getRecord().get("FROM");   //��������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  FIDU.TTAC_MDMSUPPSUSP  WHERE  MB_CD  =  :MB_CD   \n";
        query +=" AND  TRNSF_GBN  =  :TRNSF_GBN   \n";
        query +=" AND  (CLASS_CD  LIKE  :CLASS_CD  ||  '%'   \n";
        query +=" OR  :CLASS_CD  LIKE  CLASS_CD  ||  '%')   \n";
        query +=" AND  (SUPPSUSP_START_DAY  BETWEEN  :FROM   \n";
        query +=" AND  :TO   \n";
        query +=" OR  SUPPSUSP_RELS_DAY  BETWEEN  :FROM   \n";
        query +=" AND  :TO   \n";
        query +=" OR  :FROM  BETWEEN  SUPPSUSP_START_DAY   \n";
        query +=" AND  SUPPSUSP_RELS_DAY   \n";
        query +=" OR  :TO  BETWEEN  SUPPSUSP_START_DAY   \n";
        query +=" AND  SUPPSUSP_RELS_DAY) ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("TO", TO);               //��������
        sobj.setString("CLASS_CD", CLASS_CD);               //�з� �ڵ�
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("FROM", FROM);               //��������
        return sobj;
    }
    //##**$$checkDup_mdm
    //##**$$saveMst
    /* * ���α׷��� : tac06_s01
    * �ۼ��� : ����ȯ
    * �ۼ��� : 2009/11/27
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLsaveMst(DOBJ dobj)
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
            dobj  = CALLsaveMst_MIUD1(Conn, dobj);           //  ȸ�����޺����б�
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
    public DOBJ CTLsaveMst( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsaveMst_MIUD1(Conn, dobj);           //  ȸ�����޺����б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ȸ�����޺����б�
    public DOBJ CALLsaveMst_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLsaveMst_INS5(Conn, dobj);           //  ȸ�����޺�������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsaveMst_UPD6(Conn, dobj);           //  ���޺�������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsaveMst_DEL7(Conn, dobj);           //  ���޺�������
            }
        }
        return dobj;
    }
    // ���޺�������
    public DOBJ CALLsaveMst_DEL7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsaveMst_DEL7(dobj, dvobj);
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
    private SQLObject SQLsaveMst_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TMEM_SUPPSUSP  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN";
        sobj.setSql(query);
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    // ȸ�����޺�������
    public DOBJ CALLsaveMst_INS5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsaveMst_INS5(dobj, dvobj);
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
    private SQLObject SQLsaveMst_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        double MNG_NUM = 0;        //������ȣ
        String MOD_DATE ="";        //���� �Ͻ�
        String   SUPPSUSP_RELS_DAY = dvobj.getRecord().get("SUPPSUSP_RELS_DAY");   //���޺��� ���� ����
        String   SUPPSUSP_ORGMAN_CTENT = dvobj.getRecord().get("SUPPSUSP_ORGMAN_CTENT");   //���޺��� ���� ����
        String   SUPPSUSP_START_DAY = dvobj.getRecord().get("SUPPSUSP_START_DAY");   //���޺��� ���� ����
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //����� ID
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TMEM_SUPPSUSP (MODPRES_ID, MB_CD, INS_DATE, INSPRES_ID, MNG_NUM, TRNSF_GBN, SUPPSUSP_START_DAY, MOD_DATE, SUPPSUSP_ORGMAN_CTENT, SUPPSUSP_RELS_DAY)  \n";
        query +=" values(:MODPRES_ID , :MB_CD , TO_CHAR(SYSDATE,'YYYYMMDD'), :INSPRES_ID , (SELECT NVL(MAX(MNG_NUM),0) + 1 AS MNG_NUM from FIDU.TMEM_SUPPSUSP), :TRNSF_GBN , :SUPPSUSP_START_DAY , TO_CHAR(SYSDATE,'YYYYMMDD'), :SUPPSUSP_ORGMAN_CTENT , :SUPPSUSP_RELS_DAY )";
        sobj.setSql(query);
        sobj.setString("SUPPSUSP_RELS_DAY", SUPPSUSP_RELS_DAY);               //���޺��� ���� ����
        sobj.setString("SUPPSUSP_ORGMAN_CTENT", SUPPSUSP_ORGMAN_CTENT);               //���޺��� ���� ����
        sobj.setString("SUPPSUSP_START_DAY", SUPPSUSP_START_DAY);               //���޺��� ���� ����
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // ���޺�������
    public DOBJ CALLsaveMst_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsaveMst_UPD6(dobj, dvobj);
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
    private SQLObject SQLsaveMst_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPPSUSP_RELS_DAY = dvobj.getRecord().get("SUPPSUSP_RELS_DAY");   //���޺��� ���� ����
        String   SUPPSUSP_ORGMAN_CTENT = dvobj.getRecord().get("SUPPSUSP_ORGMAN_CTENT");   //���޺��� ���� ����
        String   MOD_DATE = dvobj.getRecord().get("MOD_DATE");   //���� �Ͻ�
        String   SUPPSUSP_START_DAY = dvobj.getRecord().get("SUPPSUSP_START_DAY");   //���޺��� ���� ����
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //����� ID
        String   INS_DATE = dvobj.getRecord().get("INS_DATE");   //��� �Ͻ�
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TMEM_SUPPSUSP  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , INS_DATE=:INS_DATE , INSPRES_ID=:INSPRES_ID , SUPPSUSP_START_DAY=:SUPPSUSP_START_DAY , MOD_DATE=:MOD_DATE ,  \n";
        query +=" SUPPSUSP_ORGMAN_CTENT=:SUPPSUSP_ORGMAN_CTENT , SUPPSUSP_RELS_DAY=:SUPPSUSP_RELS_DAY  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN";
        sobj.setSql(query);
        sobj.setString("SUPPSUSP_RELS_DAY", SUPPSUSP_RELS_DAY);               //���޺��� ���� ����
        sobj.setString("SUPPSUSP_ORGMAN_CTENT", SUPPSUSP_ORGMAN_CTENT);               //���޺��� ���� ����
        sobj.setString("MOD_DATE", MOD_DATE);               //���� �Ͻ�
        sobj.setString("SUPPSUSP_START_DAY", SUPPSUSP_START_DAY);               //���޺��� ���� ����
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("INS_DATE", INS_DATE);               //��� �Ͻ�
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    //##**$$saveMst
    //##**$$saveMst1
    /*
    */
    public DOBJ CTLsaveMst1(DOBJ dobj)
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
            dobj  = CALLsaveMst1_MIUD1(Conn, dobj);           //  ȸ�����޺����б�
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
    public DOBJ CTLsaveMst1( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsaveMst1_MIUD1(Conn, dobj);           //  ȸ�����޺����б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ȸ�����޺����б�
    public DOBJ CALLsaveMst1_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLsaveMst1_INS5(Conn, dobj);           //  ȸ����ü���޺�������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsaveMst1_UPD6(Conn, dobj);           //  ȸ����ü���޺�������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsaveMst1_DEL7(Conn, dobj);           //  ȸ����ü���޺�������
            }
        }
        return dobj;
    }
    // ȸ����ü���޺�������
    public DOBJ CALLsaveMst1_DEL7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsaveMst1_DEL7(dobj, dvobj);
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
    private SQLObject SQLsaveMst1_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   CLASS_CD = dvobj.getRecord().get("CLASS_CD");   //�з� �ڵ�
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_MDMSUPPSUSP  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and CLASS_CD=:CLASS_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN";
        sobj.setSql(query);
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("CLASS_CD", CLASS_CD);               //�з� �ڵ�
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    // ȸ����ü���޺�������
    public DOBJ CALLsaveMst1_INS5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsaveMst1_INS5(dobj, dvobj);
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
    private SQLObject SQLsaveMst1_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        double MNG_NUM = 0;        //������ȣ
        String MOD_DATE ="";        //���� �Ͻ�
        String   SUPPSUSP_RELS_DAY = dvobj.getRecord().get("SUPPSUSP_RELS_DAY");   //���޺��� ���� ����
        String   SUPPSUSP_ORGMAN_CTENT = dvobj.getRecord().get("SUPPSUSP_ORGMAN_CTENT");   //���޺��� ���� ����
        String   SUPPSUSP_START_DAY = dvobj.getRecord().get("SUPPSUSP_START_DAY");   //���޺��� ���� ����
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        String   CLASS_CD = dvobj.getRecord().get("CLASS_CD");   //�з� �ڵ�
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //����� ID
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_MDMSUPPSUSP (MODPRES_ID, MB_CD, INS_DATE, INSPRES_ID, CLASS_CD, MNG_NUM, TRNSF_GBN, SUPPSUSP_START_DAY, MOD_DATE, SUPPSUSP_ORGMAN_CTENT, SUPPSUSP_RELS_DAY)  \n";
        query +=" values(:MODPRES_ID , :MB_CD , TO_CHAR(SYSDATE,'YYYYMMDD'), :INSPRES_ID , :CLASS_CD , (SELECT NVL(MAX(MNG_NUM),0) + 1 AS MNG_NUM from FIDU.TTAC_MDMSUPPSUSP), :TRNSF_GBN , :SUPPSUSP_START_DAY , TO_CHAR(SYSDATE,'YYYYMMDD'), :SUPPSUSP_ORGMAN_CTENT , :SUPPSUSP_RELS_DAY )";
        sobj.setSql(query);
        sobj.setString("SUPPSUSP_RELS_DAY", SUPPSUSP_RELS_DAY);               //���޺��� ���� ����
        sobj.setString("SUPPSUSP_ORGMAN_CTENT", SUPPSUSP_ORGMAN_CTENT);               //���޺��� ���� ����
        sobj.setString("SUPPSUSP_START_DAY", SUPPSUSP_START_DAY);               //���޺��� ���� ����
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("CLASS_CD", CLASS_CD);               //�з� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // ȸ����ü���޺�������
    public DOBJ CALLsaveMst1_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsaveMst1_UPD6(dobj, dvobj);
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
    private SQLObject SQLsaveMst1_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPPSUSP_RELS_DAY = dvobj.getRecord().get("SUPPSUSP_RELS_DAY");   //���޺��� ���� ����
        String   SUPPSUSP_ORGMAN_CTENT = dvobj.getRecord().get("SUPPSUSP_ORGMAN_CTENT");   //���޺��� ���� ����
        String   MOD_DATE = dvobj.getRecord().get("MOD_DATE");   //���� �Ͻ�
        String   SUPPSUSP_START_DAY = dvobj.getRecord().get("SUPPSUSP_START_DAY");   //���޺��� ���� ����
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   CLASS_CD = dvobj.getRecord().get("CLASS_CD");   //�з� �ڵ�
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //����� ID
        String   INS_DATE = dvobj.getRecord().get("INS_DATE");   //��� �Ͻ�
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_MDMSUPPSUSP  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , INS_DATE=:INS_DATE , INSPRES_ID=:INSPRES_ID , SUPPSUSP_START_DAY=:SUPPSUSP_START_DAY , MOD_DATE=:MOD_DATE ,  \n";
        query +=" SUPPSUSP_ORGMAN_CTENT=:SUPPSUSP_ORGMAN_CTENT , SUPPSUSP_RELS_DAY=:SUPPSUSP_RELS_DAY  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and CLASS_CD=:CLASS_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN";
        sobj.setSql(query);
        sobj.setString("SUPPSUSP_RELS_DAY", SUPPSUSP_RELS_DAY);               //���޺��� ���� ����
        sobj.setString("SUPPSUSP_ORGMAN_CTENT", SUPPSUSP_ORGMAN_CTENT);               //���޺��� ���� ����
        sobj.setString("MOD_DATE", MOD_DATE);               //���� �Ͻ�
        sobj.setString("SUPPSUSP_START_DAY", SUPPSUSP_START_DAY);               //���޺��� ���� ����
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("CLASS_CD", CLASS_CD);               //�з� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("INS_DATE", INS_DATE);               //��� �Ͻ�
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    //##**$$saveMst1
    //##**$$searchMst
    /* * ���α׷��� : tac06_s01
    * �ۼ��� : ����ȯ
    * �ۼ��� : 2009/11/27
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLsearchMst(DOBJ dobj)
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
            dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  ȸ�����޺�����ȸ
            dobj  = CALLsearchMst_SEL2(Conn, dobj);           //  ȸ�����޺�����ȸ
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
    public DOBJ CTLsearchMst( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  ȸ�����޺�����ȸ
        dobj  = CALLsearchMst_SEL2(Conn, dobj);           //  ȸ�����޺�����ȸ
        return dobj;
    }
    // ȸ�����޺�����ȸ
    public DOBJ CALLsearchMst_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearchMst_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   TO = dobj.getRetObject("S").getRecord().get("TO");   //��������
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //�絵 ����
        String   FROM = dobj.getRetObject("S").getRecord().get("FROM");   //��������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  A.MNG_NUM,  A.TRNSF_GBN,  A.SUPPSUSP_START_DAY,  A.SUPPSUSP_ORGMAN_CTENT,  A.SUPPSUSP_RELS_DAY,  B.HANMB_NM  FROM  FIDU.TMEM_SUPPSUSP  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD=B.MB_CD   \n";
        query +=" AND  A.SUPPSUSP_START_DAY  >=  :FROM   \n";
        query +=" AND  A.SUPPSUSP_RELS_DAY  <=  :TO   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD||'%'   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN||'%'  ORDER  BY  A.SUPPSUSP_START_DAY ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("TO", TO);               //��������
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("FROM", FROM);               //��������
        return sobj;
    }
    // ȸ�����޺�����ȸ
    public DOBJ CALLsearchMst_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearchMst_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   TO = dobj.getRetObject("S").getRecord().get("TO");   //��������
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //�絵 ����
        String   FROM = dobj.getRetObject("S").getRecord().get("FROM");   //��������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  A.MNG_NUM,  A.TRNSF_GBN,  A.CLASS_CD,  SUBSTR(A.CLASS_CD,1,1)  AS  LARGECLASS_CD,  (CASE  WHEN  LENGTH(A.CLASS_CD)  =  1  THEN  '��ü'  ELSE  FIDU.GET_MDM_NM_EX(A.CLASS_CD,  2)  END)  AS  AVGCLASS_CD,  A.SUPPSUSP_START_DAY,  A.SUPPSUSP_ORGMAN_CTENT,  A.SUPPSUSP_RELS_DAY,  B.HANMB_NM  FROM  FIDU.TTAC_MDMSUPPSUSP  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD=B.MB_CD   \n";
        query +=" AND  A.SUPPSUSP_START_DAY  >=  :FROM   \n";
        query +=" AND  A.SUPPSUSP_RELS_DAY  <=  :TO   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD||'%'   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN||'%'  ORDER  BY  A.SUPPSUSP_START_DAY ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("TO", TO);               //��������
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("FROM", FROM);               //��������
        return sobj;
    }
    //##**$$searchMst
    //##**$$end
}
