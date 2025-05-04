
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s14_2
{
    public bra01_s14_2()
    {
    }
    //##**$$select_offlog_save
    /* 1. ���ʵ���϶� : ������ ������ ���� INS16���� ����.
    2. �ι�° �̻��� ����϶� MIUD53���� ����.
    2-1 MIUD53�� �������� �����ͼ�
    2-2 MIUD 27�� �αױ����� �����ͼ��̴�.
    �������°� �����϶��� ������ �����ϴ�.
    �αױ����� �����ͼ��� �αױ���´� ���ǻ��°� �ƴ϶� �αױ��� ��������������.
    2-3 UPD57�� ��� ������̴�.
    */
    public DOBJ CTLselect_offlog_save(DOBJ dobj)
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
            dobj  = CALLselect_offlog_save_SEL1(Conn, dobj);           //  ���ʵ������ Ȯ��
            if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLselect_offlog_save_INS16(Conn, dobj);           //  ��������
                dobj  = CALLselect_offlog_save_UPD50(Conn, dobj);           //  ������
                dobj  = CALLselect_offlog_save_INS17(Conn, dobj);           //  ���һ��� �̷�
                dobj  = CALLselect_offlog_save_INS18(Conn, dobj);           //  ���̷�
                dobj  = CALLselect_offlog_save_INS19(Conn, dobj);           //  �����̷�
                dobj  = CALLselect_offlog_save_XIUD52(Conn, dobj);           //  ������������
            }
            else if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") > 0)
            {
                dobj  = CALLselect_offlog_save_MIUD57(Conn, dobj);           //  ��������
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLselect_offlog_save_XIUD213(Conn, dobj);           //  ���
                dobj  = CALLselect_offlog_save_UPD57(Conn, dobj);           //  ������
                dobj  = CALLselect_offlog_save_UPD49(Conn, dobj);           //  ������
                dobj  = CALLselect_offlog_save_XIUD54(Conn, dobj);           //  ������������
                dobj  = CALLselect_offlog_save_SEL51(Conn, dobj);           //  �������� ��������������ȹ��
                if( dobj.getRetObject("SEL51").getRecord().get("CO_STATUS").equals("07001"))
                {
                    dobj  = CALLselect_offlog_save_MIUD27(Conn, dobj);           //  �αױ�����
                    if(dobj.getRtncode() == 9)
                    {
                        Conn.rollback();
                        return dobj;
                    }
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
    public DOBJ CTLselect_offlog_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLselect_offlog_save_SEL1(Conn, dobj);           //  ���ʵ������ Ȯ��
        if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLselect_offlog_save_INS16(Conn, dobj);           //  ��������
            dobj  = CALLselect_offlog_save_UPD50(Conn, dobj);           //  ������
            dobj  = CALLselect_offlog_save_INS17(Conn, dobj);           //  ���һ��� �̷�
            dobj  = CALLselect_offlog_save_INS18(Conn, dobj);           //  ���̷�
            dobj  = CALLselect_offlog_save_INS19(Conn, dobj);           //  �����̷�
            dobj  = CALLselect_offlog_save_XIUD52(Conn, dobj);           //  ������������
        }
        else if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") > 0)
        {
            dobj  = CALLselect_offlog_save_MIUD57(Conn, dobj);           //  ��������
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLselect_offlog_save_XIUD213(Conn, dobj);           //  ���
            dobj  = CALLselect_offlog_save_UPD57(Conn, dobj);           //  ������
            dobj  = CALLselect_offlog_save_UPD49(Conn, dobj);           //  ������
            dobj  = CALLselect_offlog_save_XIUD54(Conn, dobj);           //  ������������
            dobj  = CALLselect_offlog_save_SEL51(Conn, dobj);           //  �������� ��������������ȹ��
            if( dobj.getRetObject("SEL51").getRecord().get("CO_STATUS").equals("07001"))
            {
                dobj  = CALLselect_offlog_save_MIUD27(Conn, dobj);           //  �αױ�����
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
            }
        }
        return dobj;
    }
    // ���ʵ������ Ȯ��
    public DOBJ CALLselect_offlog_save_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLselect_offlog_save_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  LOG.KDS_SHOP  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��������
    public DOBJ CALLselect_offlog_save_INS16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS16");
        VOBJ dvobj = dobj.getRetObject("S3");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS16(dobj, dvobj);
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
    private SQLObject SQLselect_offlog_save_INS16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   COMMENTS = dvobj.getRecord().get("COMMENTS");
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOP (COMMENTS, UPSO_CD)  \n";
        query +=" values(:COMMENTS , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("COMMENTS", COMMENTS);
        return sobj;
    }
    // ������
    public DOBJ CALLselect_offlog_save_UPD50(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD50");
        VOBJ dvobj = dobj.getRetObject("S4");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD50(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD50") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD50(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // ���һ��� �̷�
    public DOBJ CALLselect_offlog_save_INS17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS17");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS17(dobj, dvobj);
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
    private SQLObject SQLselect_offlog_save_INS17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        double SEQ = 0;        //������ȣ
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //����ڸ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CO_STATUS = dvobj.getRecord().get("CO_STATUS");   //�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOP_STATUSHISTORY (CO_STATUS, UPSO_CD, SEQ, REG_DATE, USER_NAME)  \n";
        query +=" values(:CO_STATUS , :UPSO_CD , LOG.IMT_KDSSH_SEQ.NEXTVAL, SYSDATE, :USER_NAME )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //����ڸ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CO_STATUS", CO_STATUS);               //�����
        return sobj;
    }
    // ���̷�
    // ȭ�鿡�� INSERT�� ��� �ִ´�
    public DOBJ CALLselect_offlog_save_INS18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS18");
        VOBJ dvobj = dobj.getRetObject("S2");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS18(dobj, dvobj);
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
    private SQLObject SQLselect_offlog_save_INS18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        double SEQ = 0;        //������ȣ
        String   ROOM_NAME = dvobj.getRecord().get("ROOM_NAME");   //�� �̸�
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   CO_STATUS = dobj.getRetObject("S1").getRecord().get("CO_STATUS");   //�ӽ��÷�2
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOPROOM (CO_STATUS, SERIAL_NO, UPSO_CD, SEQ, REG_DATE, BSCON_CD, ROOM_NAME)  \n";
        query +=" values(:CO_STATUS , :SERIAL_NO , :UPSO_CD , LOG.IMT_KDSSR_SEQ.NEXTVAL, SYSDATE, :BSCON_CD , :ROOM_NAME )";
        sobj.setSql(query);
        sobj.setString("ROOM_NAME", ROOM_NAME);               //�� �̸�
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("CO_STATUS", CO_STATUS);               //�ӽ��÷�2
        return sobj;
    }
    // �����̷�
    public DOBJ CALLselect_offlog_save_INS19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS19");
        VOBJ dvobj = dobj.getRetObject("S3");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS19(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS19") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        double SEQ = 0;        //������ȣ
        String USER_NM ="";        //����ڸ�
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //����ڸ�
        String   CO_MENU ="05002";   //�޴������ڵ�
        String   CO_PROC ="06001";   //�����̷��ڵ�
        String   REMOTE_IP = dvobj.getRecord().get("IPADDRESS");   //������
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����ڹ�ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_ACCESS_HISTORY (REMOTE_IP, CO_PROC, USER_ID, SEQ, REG_DATE, USER_NAME, CO_MENU)  \n";
        query +=" values(:REMOTE_IP , :CO_PROC , :USER_ID , LOG.IMT_KDSAH_SEQ.NEXTVAL, SYSDATE, :USER_NAME , :CO_MENU )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //����ڸ�
        sobj.setString("CO_MENU", CO_MENU);               //�޴������ڵ�
        sobj.setString("CO_PROC", CO_PROC);               //�����̷��ڵ�
        sobj.setString("REMOTE_IP", REMOTE_IP);               //������
        sobj.setString("USER_ID", USER_ID);               //����ڹ�ȣ
        return sobj;
    }
    // ������������
    public DOBJ CALLselect_offlog_save_XIUD52(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD52");
        VOBJ dvobj = dobj.getRetObject("S5");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_XIUD52(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD52");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_XIUD52(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DSCT_END = dobj.getRetObject("S5").getRecord().get("DSCT_END");   //���������
        String   DSCT_START = dobj.getRetObject("S5").getRecord().get("DSCT_START");   //���ν��ۿ�
        String   DSCT_YN = dobj.getRetObject("S5").getRecord().get("DSCT_YN");   //�������뿩��
        String   MODPRES_ID = dobj.getRetObject("S5").getRecord().get("MODPRES_ID");   //������ ID
        String   UPSO_CD = dobj.getRetObject("S3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE LOG.KDS_SHOP  \n";
        query +=" SET DSCT_START = SUBSTR(:DSCT_START, 1,6) , DSCT_END = SUBSTR(:DSCT_END, 1,6) , DSCT_YN = :DSCT_YN , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("DSCT_END", DSCT_END);               //���������
        sobj.setString("DSCT_START", DSCT_START);               //���ν��ۿ�
        sobj.setString("DSCT_YN", DSCT_YN);               //�������뿩��
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��������
    // ���������� �߰��� ���� (����,���� �Ұ���)
    public DOBJ CALLselect_offlog_save_MIUD57(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD57");
        VOBJ dvobj = dobj.getRetObject("S1");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLselect_offlog_save_SEL15(Conn, dobj);           //  �������� ��������
                if( dobj.getRetObject("S1").getRecord().get("CO_STATUS").equals("07001"))
                {
                    dobj  = CALLselect_offlog_save_INS48(Conn, dobj);           //  ���һ��� �̷�
                    dobj  = CALLselect_offlog_save_XIUD49(Conn, dobj);           //  ���̷¼���
                    dobj  = CALLselect_offlog_save_INS50(Conn, dobj);           //  �����̷�
                }
                else if( dobj.getRetObject("S1").getRecord().get("CO_STATUS").equals("07002") || dobj.getRetObject("S1").getRecord().get("CO_STATUS").equals("07003"))
                {
                    dobj  = CALLselect_offlog_save_INS40(Conn, dobj);           //  ���һ��� �̷�
                    dobj  = CALLselect_offlog_save_XIUD45(Conn, dobj);           //  ���̷¼���
                    dobj  = CALLselect_offlog_save_SEL46(Conn, dobj);           //  LOGHISTORY ������ڷ�
                    dobj  = CALLselect_offlog_save_INS23(Conn, dobj);           //  �α׼������̷�
                    dobj  = CALLselect_offlog_save_UPD25(Conn, dobj);           //  �αױ�����
                    dobj  = CALLselect_offlog_save_INS47(Conn, dobj);           //  �����̷�
                }
            }
        }
        return dobj;
    }
    // �������� ��������
    public DOBJ CALLselect_offlog_save_SEL15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL15");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL15");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLselect_offlog_save_SEL15(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL15");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_SEL15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  INDEX_DESC  (KDS_SHOP_STATUSHISTORY  PK_KDS_SH_SEQ)  */  A.CO_STATUS  FROM  LOG.KDS_SHOP_STATUSHISTORY  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���һ��� �̷�
    public DOBJ CALLselect_offlog_save_INS48(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS48");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS48(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS48") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS48(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        double SEQ = 0;        //������ȣ
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //����ڸ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CO_STATUS = dvobj.getRecord().get("CO_STATUS");   //�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOP_STATUSHISTORY (CO_STATUS, UPSO_CD, SEQ, REG_DATE, USER_NAME)  \n";
        query +=" values(:CO_STATUS , :UPSO_CD , LOG.IMT_KDSSH_SEQ.NEXTVAL, SYSDATE, :USER_NAME )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //����ڸ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CO_STATUS", CO_STATUS);               //�����
        return sobj;
    }
    // ���̷¼���
    // ����(07005)�� ���� ���� UPDATE
    public DOBJ CALLselect_offlog_save_XIUD49(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD49");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_XIUD49(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD49");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_XIUD49(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CO_STATUS = dobj.getRetObject("R").getRecord().get("CO_STATUS");   //CO_STATUS
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE LOG.KDS_SHOPROOM  \n";
        query +=" SET CO_STATUS = :CO_STATUS  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND CO_STATUS <> '07005'";
        sobj.setSql(query);
        sobj.setString("CO_STATUS", CO_STATUS);               //CO_STATUS
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����̷�
    public DOBJ CALLselect_offlog_save_INS50(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS50");
        VOBJ dvobj = dobj.getRetObject("S3");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS50(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS50") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS50(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        double SEQ = 0;        //������ȣ
        String USER_NM ="";        //����ڸ�
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //����ڸ�
        String   CO_MENU ="05002";   //�޴������ڵ�
        String   CO_PROC ="06001";   //�����̷��ڵ�
        String   REMOTE_IP = dvobj.getRecord().get("IPADDRESS");   //������
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����ڹ�ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_ACCESS_HISTORY (REMOTE_IP, CO_PROC, USER_ID, SEQ, REG_DATE, USER_NAME, CO_MENU)  \n";
        query +=" values(:REMOTE_IP , :CO_PROC , :USER_ID , LOG.IMT_KDSAH_SEQ.NEXTVAL, SYSDATE, :USER_NAME , :CO_MENU )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //����ڸ�
        sobj.setString("CO_MENU", CO_MENU);               //�޴������ڵ�
        sobj.setString("CO_PROC", CO_PROC);               //�����̷��ڵ�
        sobj.setString("REMOTE_IP", REMOTE_IP);               //������
        sobj.setString("USER_ID", USER_ID);               //����ڹ�ȣ
        return sobj;
    }
    // ���һ��� �̷�
    public DOBJ CALLselect_offlog_save_INS40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS40");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS40(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS40") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        double SEQ = 0;        //������ȣ
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //����ڸ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CO_STATUS = dvobj.getRecord().get("CO_STATUS");   //�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOP_STATUSHISTORY (CO_STATUS, UPSO_CD, SEQ, REG_DATE, USER_NAME)  \n";
        query +=" values(:CO_STATUS , :UPSO_CD , LOG.IMT_KDSSH_SEQ.NEXTVAL, SYSDATE, :USER_NAME )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //����ڸ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CO_STATUS", CO_STATUS);               //�����
        return sobj;
    }
    // ���̷¼���
    // ����(07005)�� ���� ���� UPDATE
    public DOBJ CALLselect_offlog_save_XIUD45(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD45");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_XIUD45(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD45");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_XIUD45(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CO_STATUS = dobj.getRetObject("R").getRecord().get("CO_STATUS");   //CO_STATUS
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE LOG.KDS_SHOPROOM  \n";
        query +=" SET CO_STATUS = :CO_STATUS  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND CO_STATUS <> '07005'";
        sobj.setSql(query);
        sobj.setString("CO_STATUS", CO_STATUS);               //CO_STATUS
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // LOGHISTORY ������ڷ�
    public DOBJ CALLselect_offlog_save_SEL46(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL46");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL46");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLselect_offlog_save_SEL46(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL46");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_SEL46(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SEQ  AS  REF_SEQ_SHOPROOM  ,  SERIAL_NO  ,  LOG.FT_GET_CO_AUTH(A.SERIAL_NO)  AS  CO_AUTH  ,   \n";
        query +=" (SELECT  CO_PARING  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  A.SERIAL_NO)  AS  CO_PARING  ,  CO_STATUS  ,  '11001'  AS  CO_SERIAL_MODIFY  FROM  LOG.KDS_SHOPROOM  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  CO_STATUS  <>  '07005' ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �α׼������̷�
    public DOBJ CALLselect_offlog_save_INS23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS23");
        VOBJ dvobj = dobj.getRetObject("SEL46");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS23(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS23") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        double SEQ = 0;        //������ȣ
        String USER_NM ="";        //����ڸ�
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //����ڸ�
        String   CO_PARING = dvobj.getRecord().get("CO_PARING");   //��������ڵ�
        String   CO_AUTH = dvobj.getRecord().get("CO_AUTH");
        String   REF_SEQ_SHOPROOM = dvobj.getRecord().get("REF_SEQ_SHOPROOM");
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   CO_STATUS = dvobj.getRecord().get("CO_STATUS");   //�����
        String   CO_SERIAL_MODIFY = dvobj.getRecord().get("CO_SERIAL_MODIFY");
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����ڹ�ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_LOGHISTORY (CO_SERIAL_MODIFY, CO_STATUS, SERIAL_NO, REF_SEQ_SHOPROOM, USER_ID, CO_AUTH, SEQ, REG_DATE, CO_PARING, USER_NAME)  \n";
        query +=" values(:CO_SERIAL_MODIFY , :CO_STATUS , :SERIAL_NO , :REF_SEQ_SHOPROOM , :USER_ID , :CO_AUTH , LOG.IMT_KDSLH_SEQ.NEXTVAL, SYSDATE, :CO_PARING , :USER_NAME )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //����ڸ�
        sobj.setString("CO_PARING", CO_PARING);               //��������ڵ�
        sobj.setString("CO_AUTH", CO_AUTH);
        sobj.setString("REF_SEQ_SHOPROOM", REF_SEQ_SHOPROOM);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("CO_STATUS", CO_STATUS);               //�����
        sobj.setString("CO_SERIAL_MODIFY", CO_SERIAL_MODIFY);
        sobj.setString("USER_ID", USER_ID);               //����ڹ�ȣ
        return sobj;
    }
    // �αױ�����
    // �������� �ʱ�ȭ
    public DOBJ CALLselect_offlog_save_UPD25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD25");
        VOBJ dvobj = dobj.getRetObject("SEL46");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD25(dobj, dvobj);
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
    private SQLObject SQLselect_offlog_save_UPD25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   AUTH_DATE = dvobj.getRecord().get("NULL");   //������¥
        String   CO_PARING ="08001";   //��������ڵ�
        String   PARING_DATE = dvobj.getRecord().get("NULL");   //���볯¥
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update  \n";
        query +=" LOG.KDS_LOGCOLLECTOR  \n";
        query +=" set AUTH_DATE=:AUTH_DATE , MOD_DATE=SYSDATE , PARING_DATE=:PARING_DATE , CO_PARING=:CO_PARING  \n";
        query +=" where SERIAL_NO=:SERIAL_NO";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("AUTH_DATE", AUTH_DATE);               //������¥
        sobj.setString("CO_PARING", CO_PARING);               //��������ڵ�
        sobj.setString("PARING_DATE", PARING_DATE);               //���볯¥
        return sobj;
    }
    // �����̷�
    public DOBJ CALLselect_offlog_save_INS47(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS47");
        VOBJ dvobj = dobj.getRetObject("S3");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS47(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS47") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS47(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        double SEQ = 0;        //������ȣ
        String USER_NM ="";        //����ڸ�
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //����ڸ�
        String   CO_MENU ="05002";   //�޴������ڵ�
        String   CO_PROC ="06002";   //�����̷��ڵ�
        String   REMOTE_IP = dvobj.getRecord().get("IPADDRESS");   //������
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����ڹ�ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_ACCESS_HISTORY (REMOTE_IP, CO_PROC, USER_ID, SEQ, REG_DATE, USER_NAME, CO_MENU)  \n";
        query +=" values(:REMOTE_IP , :CO_PROC , :USER_ID , LOG.IMT_KDSAH_SEQ.NEXTVAL, SYSDATE, :USER_NAME , :CO_MENU )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //����ڸ�
        sobj.setString("CO_MENU", CO_MENU);               //�޴������ڵ�
        sobj.setString("CO_PROC", CO_PROC);               //�����̷��ڵ�
        sobj.setString("REMOTE_IP", REMOTE_IP);               //������
        sobj.setString("USER_ID", USER_ID);               //����ڹ�ȣ
        return sobj;
    }
    // ���
    public DOBJ CALLselect_offlog_save_XIUD213(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD213");
        VOBJ dvobj = dobj.getRetObject("S3");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_XIUD213(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD213");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_XIUD213(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO LOG.KDS_SHOP_HIST( SEQ , UPSO_CD , COMMENTS , DSCT_START , DSCT_YN , DSCT_END , MODPRES_ID , MOD_DATE ) SELECT (SELECT NVL(MAX(SEQ), 0) + 1 FROM LOG.KDS_SHOP_HIST WHERE UPSO_CD = :UPSO_CD) , UPSO_CD , COMMENTS , DSCT_START , DSCT_YN , DSCT_END , MODPRES_ID , MOD_DATE FROM LOG.KDS_SHOP WHERE UPSO_CD = :UPSO_CD AND DSCT_START = (SELECT MAX(DSCT_START) FROM LOG.KDS_SHOP WHERE UPSO_CD = :UPSO_CD)";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������
    public DOBJ CALLselect_offlog_save_UPD57(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD57");
        VOBJ dvobj = dobj.getRetObject("S3");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD57(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD57") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD57(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   COMMENTS = dvobj.getRecord().get("COMMENTS");
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update LOG.KDS_SHOP  \n";
        query +=" set COMMENTS=:COMMENTS  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("COMMENTS", COMMENTS);
        return sobj;
    }
    // ������
    public DOBJ CALLselect_offlog_save_UPD49(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD49");
        VOBJ dvobj = dobj.getRetObject("S4");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD49(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD49") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD49(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // ������������
    public DOBJ CALLselect_offlog_save_XIUD54(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD54");
        VOBJ dvobj = dobj.getRetObject("S5");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_XIUD54(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD54");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_XIUD54(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DSCT_END = dobj.getRetObject("S5").getRecord().get("DSCT_END");   //���������
        String   DSCT_START = dobj.getRetObject("S5").getRecord().get("DSCT_START");   //���ν��ۿ�
        String   DSCT_YN = dobj.getRetObject("S5").getRecord().get("DSCT_YN");   //�������뿩��
        String   MODPRES_ID = dobj.getRetObject("S5").getRecord().get("MODPRES_ID");   //������ ID
        String   UPSO_CD = dobj.getRetObject("S3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE LOG.KDS_SHOP  \n";
        query +=" SET DSCT_START = SUBSTR(:DSCT_START, 1,6) , DSCT_END = SUBSTR(:DSCT_END, 1,6) , DSCT_YN = :DSCT_YN , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("DSCT_END", DSCT_END);               //���������
        sobj.setString("DSCT_START", DSCT_START);               //���ν��ۿ�
        sobj.setString("DSCT_YN", DSCT_YN);               //�������뿩��
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �������� ��������������ȹ��
    public DOBJ CALLselect_offlog_save_SEL51(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL51");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL51");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLselect_offlog_save_SEL51(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL51");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_SEL51(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  INDEX_DESC  (KDS_SHOP_STATUSHISTORY  PK_KDS_SH_SEQ)  */  A.CO_STATUS  FROM  LOG.KDS_SHOP_STATUSHISTORY  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �αױ�����
    public DOBJ CALLselect_offlog_save_MIUD27(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD27");
        VOBJ dvobj = dobj.getRetObject("S2");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLselect_offlog_save_INS99(Conn, dobj);           //  ���̷�
                dobj  = CALLselect_offlog_save_INS100(Conn, dobj);           //  �����̷�
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLselect_offlog_save_UPD147(Conn, dobj);           //  ���̷¼���
                dobj  = CALLselect_offlog_save_SEL33(Conn, dobj);           //  �������� SERIAL_NO
                if(!dobj.getRetObject("R").getRecord().get("SERIAL_NO").equals(dobj.getRetObject("SEL33").getRecord().get("SERIAL_NO")))
                {
                    dobj  = CALLselect_offlog_save_INS35(Conn, dobj);           //  �α׼������̷�
                    dobj  = CALLselect_offlog_save_UPD150(Conn, dobj);           //  �αױ�����
                    dobj  = CALLselect_offlog_save_UPD37(Conn, dobj);           //  ���̷¼���
                    dobj  = CALLselect_offlog_save_INS151(Conn, dobj);           //  �����̷�
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLselect_offlog_save_SEL101(Conn, dobj);           //  LOGHISTORY ������ڷ�
                dobj  = CALLselect_offlog_save_INS102(Conn, dobj);           //  �α׼������̷�
                dobj  = CALLselect_offlog_save_UPD145(Conn, dobj);           //  �αױ�����
                dobj  = CALLselect_offlog_save_UPD41(Conn, dobj);           //  ���̷¼���
                dobj  = CALLselect_offlog_save_INS146(Conn, dobj);           //  �����̷�
            }
        }
        return dobj;
    }
    // LOGHISTORY ������ڷ�
    public DOBJ CALLselect_offlog_save_SEL101(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL101");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL101");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLselect_offlog_save_SEL101(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL101");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_SEL101(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SEQ  AS  REF_SEQ_SHOPROOM  ,  SERIAL_NO  ,  LOG.FT_GET_CO_AUTH(A.SERIAL_NO)  AS  CO_AUTH  ,   \n";
        query +=" (SELECT  CO_PARING  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  A.SERIAL_NO)  AS  CO_PARING  ,  CO_STATUS  ,  '11003'  AS  CO_SERIAL_MODIFY  FROM  LOG.KDS_SHOPROOM  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // ���̷¼���
    // �������� SERIAL_NO�� ���� UPDATE XIUD����ؾ���
    public DOBJ CALLselect_offlog_save_UPD147(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD147");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD147(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD147") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD147(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ROOM_NAME = dvobj.getRecord().get("ROOM_NAME");   //�� �̸�
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update LOG.KDS_SHOPROOM  \n";
        query +=" set BSCON_CD=:BSCON_CD , ROOM_NAME=:ROOM_NAME  \n";
        query +=" where SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("ROOM_NAME", ROOM_NAME);               //�� �̸�
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // ���̷�
    // ȭ�鿡�� INSERT�� ��� �ִ´�
    public DOBJ CALLselect_offlog_save_INS99(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS99");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS99(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS99") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS99(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        double SEQ = 0;        //������ȣ
        String   ROOM_NAME = dvobj.getRecord().get("ROOM_NAME");   //�� �̸�
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   CO_STATUS = dobj.getRetObject("SEL51").getRecord().get("CO_STATUS");   //�ӽ��÷�2
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOPROOM (CO_STATUS, SERIAL_NO, UPSO_CD, SEQ, REG_DATE, BSCON_CD, ROOM_NAME)  \n";
        query +=" values(:CO_STATUS , :SERIAL_NO , :UPSO_CD , LOG.IMT_KDSSR_SEQ.NEXTVAL, SYSDATE, :BSCON_CD , :ROOM_NAME )";
        sobj.setSql(query);
        sobj.setString("ROOM_NAME", ROOM_NAME);               //�� �̸�
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("CO_STATUS", CO_STATUS);               //�ӽ��÷�2
        return sobj;
    }
    // �α׼������̷�
    public DOBJ CALLselect_offlog_save_INS102(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS102");
        VOBJ dvobj = dobj.getRetObject("SEL101");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS102(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS102") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS102(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        double SEQ = 0;        //������ȣ
        String USER_NM ="";        //����ڸ�
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //����ڸ�
        String   CO_PARING = dvobj.getRecord().get("CO_PARING");   //��������ڵ�
        String   CO_AUTH = dvobj.getRecord().get("CO_AUTH");
        String   REF_SEQ_SHOPROOM = dvobj.getRecord().get("REF_SEQ_SHOPROOM");
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   CO_STATUS = dvobj.getRecord().get("CO_STATUS");   //�����
        String   CO_SERIAL_MODIFY = dvobj.getRecord().get("CO_SERIAL_MODIFY");
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����ڹ�ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_LOGHISTORY (CO_SERIAL_MODIFY, CO_STATUS, SERIAL_NO, REF_SEQ_SHOPROOM, USER_ID, CO_AUTH, SEQ, REG_DATE, CO_PARING, USER_NAME)  \n";
        query +=" values(:CO_SERIAL_MODIFY , :CO_STATUS , :SERIAL_NO , :REF_SEQ_SHOPROOM , :USER_ID , :CO_AUTH , LOG.IMT_KDSLH_SEQ.NEXTVAL, SYSDATE, :CO_PARING , :USER_NAME )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //����ڸ�
        sobj.setString("CO_PARING", CO_PARING);               //��������ڵ�
        sobj.setString("CO_AUTH", CO_AUTH);
        sobj.setString("REF_SEQ_SHOPROOM", REF_SEQ_SHOPROOM);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("CO_STATUS", CO_STATUS);               //�����
        sobj.setString("CO_SERIAL_MODIFY", CO_SERIAL_MODIFY);
        sobj.setString("USER_ID", USER_ID);               //����ڹ�ȣ
        return sobj;
    }
    // �������� SERIAL_NO
    // �̰����� �ڷ�ȹ��
    public DOBJ CALLselect_offlog_save_SEL33(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL33");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL33");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLselect_offlog_save_SEL33(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL33");
        rvobj.Println("SEL33");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_SEL33(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SEQ  AS  REF_SEQ_SHOPROOM  ,  SERIAL_NO  ,  LOG.FT_GET_CO_AUTH(A.SERIAL_NO)  AS  CO_AUTH  ,   \n";
        query +=" (SELECT  CO_PARING  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  A.SERIAL_NO)  AS  CO_PARING  ,  CO_STATUS  ,  '11002'  AS  CO_SERIAL_MODIFY  FROM  LOG.KDS_SHOPROOM  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // �����̷�
    public DOBJ CALLselect_offlog_save_INS100(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS100");
        VOBJ dvobj = dobj.getRetObject("S3");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS100(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS100") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS100(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        double SEQ = 0;        //������ȣ
        String USER_NM ="";        //����ڸ�
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //����ڸ�
        String   CO_MENU ="05002";   //�޴������ڵ�
        String   CO_PROC ="06001";   //�����̷��ڵ�
        String   REMOTE_IP = dvobj.getRecord().get("IPADDRESS");   //������
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����ڹ�ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_ACCESS_HISTORY (REMOTE_IP, CO_PROC, USER_ID, SEQ, REG_DATE, USER_NAME, CO_MENU)  \n";
        query +=" values(:REMOTE_IP , :CO_PROC , :USER_ID , LOG.IMT_KDSAH_SEQ.NEXTVAL, SYSDATE, :USER_NAME , :CO_MENU )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //����ڸ�
        sobj.setString("CO_MENU", CO_MENU);               //�޴������ڵ�
        sobj.setString("CO_PROC", CO_PROC);               //�����̷��ڵ�
        sobj.setString("REMOTE_IP", REMOTE_IP);               //������
        sobj.setString("USER_ID", USER_ID);               //����ڹ�ȣ
        return sobj;
    }
    // �αױ�����
    // �������� �ʱ�ȭ
    public DOBJ CALLselect_offlog_save_UPD145(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD145");
        VOBJ dvobj = dobj.getRetObject("SEL101");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD145(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD145") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD145(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   AUTH_DATE = dvobj.getRecord().get("NULL");   //������¥
        String   CO_PARING ="08001";   //��������ڵ�
        String   PARING_DATE = dvobj.getRecord().get("NULL");   //���볯¥
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update  \n";
        query +=" LOG.KDS_LOGCOLLECTOR  \n";
        query +=" set AUTH_DATE=:AUTH_DATE , MOD_DATE=SYSDATE , PARING_DATE=:PARING_DATE , CO_PARING=:CO_PARING  \n";
        query +=" where SERIAL_NO=:SERIAL_NO";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("AUTH_DATE", AUTH_DATE);               //������¥
        sobj.setString("CO_PARING", CO_PARING);               //��������ڵ�
        sobj.setString("PARING_DATE", PARING_DATE);               //���볯¥
        return sobj;
    }
    // ���̷¼���
    // �������� SERIAL_NO�� ���� UPDATE XIUD����ؾ���
    public DOBJ CALLselect_offlog_save_UPD41(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD41");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD41(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD41") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD41(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   CO_STATUS ="07005";   //�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update LOG.KDS_SHOPROOM  \n";
        query +=" set CO_STATUS=:CO_STATUS  \n";
        query +=" where SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("CO_STATUS", CO_STATUS);               //�����
        return sobj;
    }
    // �α׼������̷�
    // ���� �αױ� �ø����ȣ�� �̷¿� ���
    public DOBJ CALLselect_offlog_save_INS35(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS35");
        VOBJ dvobj = dobj.getRetObject("SEL33");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("INS35");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS35(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS35") ;
        rvobj.Println("INS35");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS35(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        double SEQ = 0;        //������ȣ
        String USER_NM ="";        //����ڸ�
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //����ڸ�
        String   CO_PARING = dvobj.getRecord().get("CO_PARING");   //��������ڵ�
        String   CO_AUTH = dvobj.getRecord().get("CO_AUTH");
        String   REF_SEQ_SHOPROOM = dvobj.getRecord().get("REF_SEQ_SHOPROOM");
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   CO_STATUS = dvobj.getRecord().get("CO_STATUS");   //�����
        String   CO_SERIAL_MODIFY = dvobj.getRecord().get("CO_SERIAL_MODIFY");
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����ڹ�ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_LOGHISTORY (CO_SERIAL_MODIFY, CO_STATUS, SERIAL_NO, REF_SEQ_SHOPROOM, USER_ID, CO_AUTH, SEQ, REG_DATE, CO_PARING, USER_NAME)  \n";
        query +=" values(:CO_SERIAL_MODIFY , :CO_STATUS , :SERIAL_NO , :REF_SEQ_SHOPROOM , :USER_ID , :CO_AUTH , LOG.IMT_KDSLH_SEQ.NEXTVAL, SYSDATE, :CO_PARING , :USER_NAME )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //����ڸ�
        sobj.setString("CO_PARING", CO_PARING);               //��������ڵ�
        sobj.setString("CO_AUTH", CO_AUTH);
        sobj.setString("REF_SEQ_SHOPROOM", REF_SEQ_SHOPROOM);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("CO_STATUS", CO_STATUS);               //�����
        sobj.setString("CO_SERIAL_MODIFY", CO_SERIAL_MODIFY);
        sobj.setString("USER_ID", USER_ID);               //����ڹ�ȣ
        return sobj;
    }
    // �����̷�
    public DOBJ CALLselect_offlog_save_INS146(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS146");
        VOBJ dvobj = dobj.getRetObject("S3");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS146(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS146") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS146(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        double SEQ = 0;        //������ȣ
        String USER_NM ="";        //����ڸ�
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //����ڸ�
        String   CO_MENU ="05002";   //�޴������ڵ�
        String   CO_PROC ="06003";   //�����̷��ڵ�
        String   REMOTE_IP = dvobj.getRecord().get("IPADDRESS");   //������
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����ڹ�ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_ACCESS_HISTORY (REMOTE_IP, CO_PROC, USER_ID, SEQ, REG_DATE, USER_NAME, CO_MENU)  \n";
        query +=" values(:REMOTE_IP , :CO_PROC , :USER_ID , LOG.IMT_KDSAH_SEQ.NEXTVAL, SYSDATE, :USER_NAME , :CO_MENU )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //����ڸ�
        sobj.setString("CO_MENU", CO_MENU);               //�޴������ڵ�
        sobj.setString("CO_PROC", CO_PROC);               //�����̷��ڵ�
        sobj.setString("REMOTE_IP", REMOTE_IP);               //������
        sobj.setString("USER_ID", USER_ID);               //����ڹ�ȣ
        return sobj;
    }
    // �αױ�����
    // �������� �ʱ�ȭ
    public DOBJ CALLselect_offlog_save_UPD150(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD150");
        VOBJ dvobj = dobj.getRetObject("SEL33");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("UPD150");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD150(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD150") ;
        rvobj.Println("UPD150");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD150(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        String   AUTH_DATE = dvobj.getRecord().get("NULL");   //������¥
        String   CO_PARING ="08001";   //��������ڵ�
        String   PARING_DATE = dvobj.getRecord().get("NULL");   //���볯¥
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update  \n";
        query +=" LOG.KDS_LOGCOLLECTOR  \n";
        query +=" set AUTH_DATE=:AUTH_DATE , MOD_DATE=SYSDATE , PARING_DATE=:PARING_DATE , CO_PARING=:CO_PARING  \n";
        query +=" where SERIAL_NO=:SERIAL_NO";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        sobj.setString("AUTH_DATE", AUTH_DATE);               //������¥
        sobj.setString("CO_PARING", CO_PARING);               //��������ڵ�
        sobj.setString("PARING_DATE", PARING_DATE);               //���볯¥
        return sobj;
    }
    // ���̷¼���
    // �������� SERIAL_NO�� ���� UPDATE XIUD����ؾ���
    public DOBJ CALLselect_offlog_save_UPD37(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD37");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD37");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD37(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD37") ;
        rvobj.Println("UPD37");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD37(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //��ǰ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update LOG.KDS_SHOPROOM  \n";
        query +=" set SERIAL_NO=:SERIAL_NO  \n";
        query +=" where SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("SERIAL_NO", SERIAL_NO);               //��ǰ��ȣ
        return sobj;
    }
    // �����̷�
    public DOBJ CALLselect_offlog_save_INS151(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS151");
        VOBJ dvobj = dobj.getRetObject("S3");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("INS151");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS151(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS151") ;
        rvobj.Println("INS151");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS151(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //����Ͻ�
        double SEQ = 0;        //������ȣ
        String USER_NM ="";        //����ڸ�
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //����ڸ�
        String   CO_MENU ="05002";   //�޴������ڵ�
        String   CO_PROC ="06002";   //�����̷��ڵ�
        String   REMOTE_IP = dvobj.getRecord().get("IPADDRESS");   //������
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����ڹ�ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_ACCESS_HISTORY (REMOTE_IP, CO_PROC, USER_ID, SEQ, REG_DATE, USER_NAME, CO_MENU)  \n";
        query +=" values(:REMOTE_IP , :CO_PROC , :USER_ID , LOG.IMT_KDSAH_SEQ.NEXTVAL, SYSDATE, :USER_NAME , :CO_MENU )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //����ڸ�
        sobj.setString("CO_MENU", CO_MENU);               //�޴������ڵ�
        sobj.setString("CO_PROC", CO_PROC);               //�����̷��ڵ�
        sobj.setString("REMOTE_IP", REMOTE_IP);               //������
        sobj.setString("USER_ID", USER_ID);               //����ڹ�ȣ
        return sobj;
    }
    //##**$$select_offlog_save
    //##**$$sel_log_dsct_hist
    /*
    */
    public DOBJ CTLsel_log_dsct_hist(DOBJ dobj)
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
            dobj  = CALLsel_log_dsct_hist_SEL1(Conn, dobj);           //  �̷���ȸ
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
    public DOBJ CTLsel_log_dsct_hist( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_log_dsct_hist_SEL1(Conn, dobj);           //  �̷���ȸ
        return dobj;
    }
    // �̷���ȸ
    public DOBJ CALLsel_log_dsct_hist_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_log_dsct_hist_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_log_dsct_hist_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SEQ  ,  UPSO_CD  ,  COMMENTS  ,  DSCT_START  ,  DSCT_YN  ,  DSCT_END  ,  MODPRES_ID  ,  MOD_DATE  FROM  LOG.KDS_SHOP_HIST  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$sel_log_dsct_hist
    //##**$$end
}
