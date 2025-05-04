
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s02
{
    public bra01_s02()
    {
    }
    //##**$$upso_acmcn_regist
    /* * ���α׷��� : bra01_s02
    * �ۼ��� : ������
    * �ۼ��� : 2009/10/21
    * ���� : ������ ���ֱ� ������ ����Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_acmcn_regist(DOBJ dobj)
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
            dobj  = CALLupso_acmcn_regist_DEL22(Conn, dobj);           //  �¿������ֱ���������
            dobj  = CALLupso_acmcn_regist_MPD24(Conn, dobj);           //  ���ֱ�¿�������
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_acmcn_regist_SEL19(Conn, dobj);           //  �¿���������ȸ
            if( dobj.getRetObject("SEL19").getRecordCnt() == 1)
            {
                dobj  = CALLupso_acmcn_regist_UPD21(Conn, dobj);           //  ���� �¿�����������
                dobj  = CALLupso_acmcn_regist_DEL23(Conn, dobj);           //  ���ֱ����������
                dobj  = CALLupso_acmcn_regist_MPD26(Conn, dobj);           //  ���ֱ������
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLupso_acmcn_regist_MIUD30(Conn, dobj);           //  ���긮����
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
            }
            else if( dobj.getRetObject("SEL19").getRecordCnt() > 1)
            {
                dobj  = CALLupso_acmcn_regist_UPD22(Conn, dobj);           //  ���� �¿�����������
                dobj  = CALLupso_acmcn_regist_DEL23(Conn, dobj);           //  ���ֱ����������
                dobj  = CALLupso_acmcn_regist_MPD26(Conn, dobj);           //  ���ֱ������
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLupso_acmcn_regist_MIUD30(Conn, dobj);           //  ���긮����
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
            }
            else
            {
                dobj  = CALLupso_acmcn_regist_UPD25(Conn, dobj);           //  ���� �¿�����������
                dobj  = CALLupso_acmcn_regist_DEL23(Conn, dobj);           //  ���ֱ����������
                dobj  = CALLupso_acmcn_regist_MPD26(Conn, dobj);           //  ���ֱ������
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLupso_acmcn_regist_MIUD30(Conn, dobj);           //  ���긮����
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
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
    public DOBJ CTLupso_acmcn_regist( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_acmcn_regist_DEL22(Conn, dobj);           //  �¿������ֱ���������
        dobj  = CALLupso_acmcn_regist_MPD24(Conn, dobj);           //  ���ֱ�¿�������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupso_acmcn_regist_SEL19(Conn, dobj);           //  �¿���������ȸ
        if( dobj.getRetObject("SEL19").getRecordCnt() == 1)
        {
            dobj  = CALLupso_acmcn_regist_UPD21(Conn, dobj);           //  ���� �¿�����������
            dobj  = CALLupso_acmcn_regist_DEL23(Conn, dobj);           //  ���ֱ����������
            dobj  = CALLupso_acmcn_regist_MPD26(Conn, dobj);           //  ���ֱ������
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_acmcn_regist_MIUD30(Conn, dobj);           //  ���긮����
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        else if( dobj.getRetObject("SEL19").getRecordCnt() > 1)
        {
            dobj  = CALLupso_acmcn_regist_UPD22(Conn, dobj);           //  ���� �¿�����������
            dobj  = CALLupso_acmcn_regist_DEL23(Conn, dobj);           //  ���ֱ����������
            dobj  = CALLupso_acmcn_regist_MPD26(Conn, dobj);           //  ���ֱ������
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_acmcn_regist_MIUD30(Conn, dobj);           //  ���긮����
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        else
        {
            dobj  = CALLupso_acmcn_regist_UPD25(Conn, dobj);           //  ���� �¿�����������
            dobj  = CALLupso_acmcn_regist_DEL23(Conn, dobj);           //  ���ֱ����������
            dobj  = CALLupso_acmcn_regist_MPD26(Conn, dobj);           //  ���ֱ������
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_acmcn_regist_MIUD30(Conn, dobj);           //  ���긮����
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // �¿������ֱ���������
    // �ش� ������ ���� �¿��� ���ֱ� ������ �����Ѵ�.
    public DOBJ CALLupso_acmcn_regist_DEL22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL22");
        VOBJ dvobj = dobj.getRetObject("S3");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_DEL22(dobj, dvobj);
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
    private SQLObject SQLupso_acmcn_regist_DEL22(DOBJ dobj, VOBJ dvobj) throws Exception
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
    public DOBJ CALLupso_acmcn_regist_MPD24(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLupso_acmcn_regist_INS16(Conn, dobj);           //  �¿������ֱ��������
            }
        }
        return dobj;
    }
    // �¿������ֱ��������
    // ���ο� �¿��� ���ֱ� ������ ����Ѵ�.
    public DOBJ CALLupso_acmcn_regist_INS16(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLupso_acmcn_regist_INS16(dobj, dvobj);
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
    private SQLObject SQLupso_acmcn_regist_INS16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //����� ID
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   ONOFF_GBN = dvobj.getRecord().get("ONOFF_GBN");   //�¿��� ����
        int   ACMCN_DAESU = dvobj.getRecord().getInt("ACMCN_DAESU");   //���ֱ� ���
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_ONOFF_INFO (MODEL_CD, ACMCN_DAESU, ONOFF_GBN, UPSO_CD)  \n";
        query +=" values(:MODEL_CD , :ACMCN_DAESU , :ONOFF_GBN , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //�¿��� ����
        sobj.setInt("ACMCN_DAESU", ACMCN_DAESU);               //���ֱ� ���
        sobj.setString("MODEL_CD", MODEL_CD);               //�� �ڵ�
        return sobj;
    }
    // �¿���������ȸ
    // �ش� ������ �¿��� ������ ��ȸ�Ѵ�.
    public DOBJ CALLupso_acmcn_regist_SEL19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL19");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL19");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_acmcn_regist_SEL19(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_SEL19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  ONOFF_GBN  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �¿�����������
    // �¿��� ������ 1���� ��� �ش� �¿��� ������ �����Ѵ�.
    public DOBJ CALLupso_acmcn_regist_UPD21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD21");
        VOBJ dvobj = dobj.getRetObject("S3");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_UPD21(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD21") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_UPD21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //������ ID
        String   ONOFF_GBN = dobj.getRetObject("SEL19").getRecord().get("ONOFF_GBN");   //�¿��� ����
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
    // ���ֱ����������
    // ���� ���ֱ� �������� �����Ѵ�.
    public DOBJ CALLupso_acmcn_regist_DEL23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL23");
        VOBJ dvobj = dobj.getRetObject("S3");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_DEL23(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL23") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_DEL23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_ACMCN_INFO  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ֱ������
    public DOBJ CALLupso_acmcn_regist_MPD26(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD26");
        VOBJ dvobj = dobj.getRetObject("S1");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("UPSO_CD").equals(""))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_acmcn_regist_INS17(Conn, dobj);           //  ���ֱ���������
            }
        }
        return dobj;
    }
    // ���ֱ���������
    // ���� ��ϵ� ���ֱ� �� ������ ����Ѵ�.
    public DOBJ CALLupso_acmcn_regist_INS17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS17");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_INS17(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS17") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_INS17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //����� ID
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        int   ACMCN_DAESU = dvobj.getRecord().getInt("ACMCN_DAESU");   //���ֱ� ���
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_ACMCN_INFO (MODEL_CD, ACMCN_DAESU, UPSO_CD)  \n";
        query +=" values(:MODEL_CD , :ACMCN_DAESU , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setInt("ACMCN_DAESU", ACMCN_DAESU);               //���ֱ� ���
        sobj.setString("MODEL_CD", MODEL_CD);               //�� �ڵ�
        return sobj;
    }
    // ���긮����
    public DOBJ CALLupso_acmcn_regist_MIUD30(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD30");
        VOBJ dvobj = dobj.getRetObject("S2");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_acmcn_regist_INS18(Conn, dobj);           //  ���
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_acmcn_regist_UPD19(Conn, dobj);           //  ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_acmcn_regist_DEL20(Conn, dobj);           //  ����
            }
        }
        return dobj;
    }
    // ����
    // ���� ���긮 ������ �����Ѵ�.
    public DOBJ CALLupso_acmcn_regist_DEL20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL20");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_DEL20(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL20") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_DEL20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_AUBRY_INFO  \n";
        query +=" where MODEL_CD=:MODEL_CD  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("MODEL_CD", MODEL_CD);               //�� �ڵ�
        return sobj;
    }
    // ���
    // ���ο� ���긮 ������ ����Ѵ�.
    public DOBJ CALLupso_acmcn_regist_INS18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS18");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_INS18(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS18") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_INS18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //����� ID
        String INS_DATE ="";        //��� �Ͻ�
        String   MODEL_NM = dvobj.getRecord().get("MODEL_NM");   //�� ��
        String   MCHN_COMPY = dvobj.getRecord().get("MCHN_COMPY");   //��� ȸ��
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   MCHN_COMPYNM = dvobj.getRecord().get("MCHN_COMPYNM");   //��� ȸ���
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUBRY_INFO (MODEL_CD, MCHN_COMPYNM, UPSO_CD, MCHN_COMPY, MODEL_NM)  \n";
        query +=" values(:MODEL_CD , :MCHN_COMPYNM , :UPSO_CD , :MCHN_COMPY , :MODEL_NM )";
        sobj.setSql(query);
        sobj.setString("MODEL_NM", MODEL_NM);               //�� ��
        sobj.setString("MCHN_COMPY", MCHN_COMPY);               //��� ȸ��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("MCHN_COMPYNM", MCHN_COMPYNM);               //��� ȸ���
        sobj.setString("MODEL_CD", MODEL_CD);               //�� �ڵ�
        return sobj;
    }
    // ����
    // ���� ���긮 ������ �����Ѵ�.
    public DOBJ CALLupso_acmcn_regist_UPD19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD19");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_UPD19(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD19") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_UPD19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //������ ID
        String MOD_DATE ="";        //���� �Ͻ�
        String   MODEL_NM = dvobj.getRecord().get("MODEL_NM");   //�� ��
        String   MCHN_COMPY = dvobj.getRecord().get("MCHN_COMPY");   //��� ȸ��
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   MCHN_COMPYNM = dvobj.getRecord().get("MCHN_COMPYNM");   //��� ȸ���
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUBRY_INFO  \n";
        query +=" set MCHN_COMPYNM=:MCHN_COMPYNM , MCHN_COMPY=:MCHN_COMPY , MODEL_NM=:MODEL_NM  \n";
        query +=" where MODEL_CD=:MODEL_CD  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MODEL_NM", MODEL_NM);               //�� ��
        sobj.setString("MCHN_COMPY", MCHN_COMPY);               //��� ȸ��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("MCHN_COMPYNM", MCHN_COMPYNM);               //��� ȸ���
        sobj.setString("MODEL_CD", MODEL_CD);               //�� �ڵ�
        return sobj;
    }
    // ���� �¿�����������
    // ON/OFF�Ѵ� ���� ��� ������ �¿��������� �¶���(O)�� �ȴ�.
    public DOBJ CALLupso_acmcn_regist_UPD22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD22");
        VOBJ dvobj = dobj.getRetObject("S3");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_UPD22(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD22") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_UPD22(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // �¿��������� ���� ��� ���� ONOFF_GBN�� ''�� UPDATE
    public DOBJ CALLupso_acmcn_regist_UPD25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD25");
        VOBJ dvobj = dobj.getRetObject("S3");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_acmcn_regist_UPD25(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD25") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_regist_UPD25(DOBJ dobj, VOBJ dvobj) throws Exception
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
    //##**$$upso_acmcn_regist
    //##**$$upso_acmcn_select
    /* * ���α׷��� : bra01_s02
    * �ۼ��� : ������
    * �ۼ��� : 2009/10/15
    * ���� : ���Һ� ����
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_acmcn_select(DOBJ dobj)
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
            dobj  = CALLupso_acmcn_select_SEL1(Conn, dobj);           //  ���Һ� �¿��� ���ֱ���ȸ
            dobj  = CALLupso_acmcn_select_SEL2(Conn, dobj);           //  ���Һ� ���ֱ���ȸ
            dobj  = CALLupso_acmcn_select_SEL3(Conn, dobj);           //  ���Һ� ���긮 ���� ��ȸ
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
    public DOBJ CTLupso_acmcn_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_acmcn_select_SEL1(Conn, dobj);           //  ���Һ� �¿��� ���ֱ���ȸ
        dobj  = CALLupso_acmcn_select_SEL2(Conn, dobj);           //  ���Һ� ���ֱ���ȸ
        dobj  = CALLupso_acmcn_select_SEL3(Conn, dobj);           //  ���Һ� ���긮 ���� ��ȸ
        return dobj;
    }
    // ���Һ� �¿��� ���ֱ���ȸ
    public DOBJ CALLupso_acmcn_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_acmcn_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XB.KIND  MCHN_COMPY  ,  NVL(SUM(XA.ACMCN_DAESU),  0)  ACMCN_DAESU  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  (CASE  B.MCHN_COMPY  WHEN  'E0006'  THEN  B.MCHN_COMPY  WHEN  'E0003'  THEN  B.MCHN_COMPY  ELSE  'ETC'  END)  MCHN_COMPY  ,  A.ONOFF_GBN  ,  ACMCN_DAESU  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A  ,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD  )  XA  ,  (   \n";
        query +=" SELECT  '1_KY_O'  KIND,  'E0006'  MCHN_COMPY,  'O'  ONOFF_GBN  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '4_KY_F',  'E0006',  'F'  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '2_TJ_O',  'E0003',  'O'  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '5_TJ_F',  'E0003',  'F'  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '3_ETC_O',  'ETC'  ,  'O'  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '6_ETC_F',  'ETC'  ,  'F'  FROM  DUAL  )  XB  WHERE  XB.ONOFF_GBN  =  XA.ONOFF_GBN  (+)   \n";
        query +=" AND  XB.MCHN_COMPY  =  XA.MCHN_COMPY  (+)  GROUP  BY  XB.KIND,  XA.UPSO_CD  ORDER  BY  XB.KIND ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���Һ� ���ֱ���ȸ
    public DOBJ CALLupso_acmcn_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_acmcn_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  XA.MCHN_COMPY  KMCHN_COMPY  ,  XA.MODEL_CD  KMODEL_CD  ,  XA.MODEL_NM  KMODEL_NM  ,  XA.ACMCN_DAESU  KACMCN_DAESU  ,  XA.GBN  KGBN  ,  XB.MCHN_COMPY  TMCHN_COMPY  ,  XB.MODEL_CD  TMODEL_CD  ,  XB.MODEL_NM  TMODEL_NM  ,  XB.ACMCN_DAESU  TACMCN_DAESU  ,  XB.GBN  TGBN  ,  XC.MCHN_COMPY  EMCHN_COMPY  ,  XC.MODEL_CD  EMODEL_CD  ,  XC.MODEL_NM  EMODEL_NM  ,  XC.ACMCN_DAESU  EACMCN_DAESU  ,  XC.GBN  EGBN  FROM  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0006'  )  XA  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0003'  )  XB  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  NOT  IN  ('E0003',  'E0006')  )  XC  WHERE  XA.N  =  XB.N  (+)   \n";
        query +=" AND  XA.N  =  XC.N  (+)  UNION   \n";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  XA.MCHN_COMPY  ,  XA.MODEL_CD  ,  XA.MODEL_NM  ,  XA.ACMCN_DAESU  ,  XA.GBN  ,  XB.MCHN_COMPY  ,  XB.MODEL_CD  ,  XB.MODEL_NM  ,  XB.ACMCN_DAESU  ,  XB.GBN  ,  XC.MCHN_COMPY  ,  XC.MODEL_CD  ,  XC.MODEL_NM  ,  XC.ACMCN_DAESU  ,  XC.GBN  FROM  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0006'  )  XA  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0003'  )  XB  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  NOT  IN  ('E0003',  'E0006')  )  XC  WHERE  XB.N  =  XA.N  (+)   \n";
        query +=" AND  XB.N  =  XC.N  (+)  UNION   \n";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  XA.MCHN_COMPY  ,  XA.MODEL_CD  ,  XA.MODEL_NM  ,  XA.ACMCN_DAESU  ,  XA.GBN  ,  XB.MCHN_COMPY  ,  XB.MODEL_CD  ,  XB.MODEL_NM  ,  XB.ACMCN_DAESU  ,  XB.GBN  ,  XC.MCHN_COMPY  ,  XC.MODEL_CD  ,  XC.MODEL_NM  ,  XC.ACMCN_DAESU  ,  XC.GBN  FROM  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0006'  )  XA  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0003'  )  XB  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  NOT  IN  ('E0003',  'E0006')  )  XC  WHERE  XC.N  =  XA.N  (+)   \n";
        query +=" AND  XC.N  =  XB.N  (+) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���Һ� ���긮 ���� ��ȸ
    public DOBJ CALLupso_acmcn_select_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_acmcn_select_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_select_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  MODEL_CD  ,  MODEL_NM  ,  MCHN_COMPYNM  ,  MCHN_COMPY  FROM  GIBU.TBRA_UPSO_AUBRY_INFO  WHERE  UPSO_CD=:UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$upso_acmcn_select
    //##**$$acmcn_select
    /* * ���α׷��� : bra01_s02
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/02
    * ���� :
    ���ֱ⺰ �������� ��ȸ�Ѵ�.
    MCHN_COMPY : ETC -> 0003, 0006�� ������ ���ֱ� ���� ��ȸ
    MCHN_COMPY : 0003 OR  0006-> �ش� �ڵ� ���ֱ� ���� ��ȸ
    MCHN_COMPY : ��ϵ� ��� ���ֱ� ���� ��ȸ
    1.NLL.NLL3 : ���μ������������� S ���� �ٷ� �б�(���Ǵޱ�)�� �������� �ʱ� ������ ���ǿ� ���� �бⰡ���ϵ��� ����
    4.MRG.MRG1 : ���� ���Ǻ��� ��ȸ�� ����Ÿ�� ȭ�鿡 ������ ��������Ÿ�� MIFLATFORM�� �����Ѵ�
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLacmcn_select(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("ETC"))
            {
                dobj  = CALLacmcn_select_SEL2(Conn, dobj);           //  ���ֱ�������ȸ(etc)
                dobj  = CALLacmcn_select_MRG1( dobj);        //  ������ħ
            }
            else if( dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("E0003") || dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("E0006"))
            {
                dobj  = CALLacmcn_select_SEL1(Conn, dobj);           //  ���ֱ�������ȸ
                dobj  = CALLacmcn_select_MRG1( dobj);        //  ������ħ
            }
            else
            {
                dobj  = CALLacmcn_select_SEL3(Conn, dobj);           //  ���𵨰˻�
                dobj  = CALLacmcn_select_MRG1( dobj);        //  ������ħ
            }
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
    public DOBJ CTLacmcn_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("ETC"))
        {
            dobj  = CALLacmcn_select_SEL2(Conn, dobj);           //  ���ֱ�������ȸ(etc)
            dobj  = CALLacmcn_select_MRG1( dobj);        //  ������ħ
        }
        else if( dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("E0003") || dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("E0006"))
        {
            dobj  = CALLacmcn_select_SEL1(Conn, dobj);           //  ���ֱ�������ȸ
            dobj  = CALLacmcn_select_MRG1( dobj);        //  ������ħ
        }
        else
        {
            dobj  = CALLacmcn_select_SEL3(Conn, dobj);           //  ���𵨰˻�
            dobj  = CALLacmcn_select_MRG1( dobj);        //  ������ħ
        }
        return dobj;
    }
    // ���ֱ�������ȸ(etc)
    public DOBJ CALLacmcn_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLacmcn_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLacmcn_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.BSCONHAN_NM  MCHN_COMPYNM  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  FIDU.TLEV_BSCON  B  WHERE  MCHN_COMPY  NOT  IN  ('E0003',  'E0006')   \n";
        query +=" AND  A.MCHN_COMPY  =  B.BSCON_CD  ORDER  BY  A.MODEL_NM ";
        sobj.setSql(query);
        return sobj;
    }
    // ������ħ
    public DOBJ CALLacmcn_select_MRG1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL3, SEL1, SEL2","MCHN_COMPYNM, MCHN_COMPY, MODEL_CD, MODEL_NM, GBN");
        rvobj.setName("MRG1") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ���ֱ�������ȸ
    public DOBJ CALLacmcn_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLacmcn_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLacmcn_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MCHN_COMPY = dobj.getRetObject("S").getRecord().get("MCHN_COMPY");   //��� ȸ��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.BSCONHAN_NM  MCHN_COMPYNM  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  FIDU.TLEV_BSCON  B  WHERE  MCHN_COMPY  =  :MCHN_COMPY   \n";
        query +=" AND  A.MCHN_COMPY  =  B.BSCON_CD  ORDER  BY  A.MODEL_NM ";
        sobj.setSql(query);
        sobj.setString("MCHN_COMPY", MCHN_COMPY);               //��� ȸ��
        return sobj;
    }
    // ���𵨰˻�
    public DOBJ CALLacmcn_select_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLacmcn_select_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLacmcn_select_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.BSCONHAN_NM  MCHN_COMPYNM  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  FIDU.TLEV_BSCON  B  WHERE  A.MCHN_COMPY  =  B.BSCON_CD  ORDER  BY  A.MODEL_NM ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$acmcn_select
    //##**$$end
}
