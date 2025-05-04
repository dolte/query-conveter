
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s09_4
{
    public bra01_s09_4()
    {
    }
    //##**$$upso_visit_regist
    /* * ���α׷��� : bra01_s09
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/25
    * ���� : ����/����Ŭ����/���Ҹ���/�ְ����/��Ÿ ���Ұ��� �������� �����Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_visit_regist(DOBJ dobj)
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
            dobj  = CALLupso_visit_regist_MIUD1(Conn, dobj);           //  ���ҹ湮����
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_visit_regist_MIUD2(Conn, dobj);           //  ���ҹ湮�󼼰���
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_visit_regist_SEL3(Conn, dobj);           //  ���ҹ湮����Ʈ
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
    public DOBJ CTLupso_visit_regist( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_visit_regist_MIUD1(Conn, dobj);           //  ���ҹ湮����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupso_visit_regist_MIUD2(Conn, dobj);           //  ���ҹ湮�󼼰���
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupso_visit_regist_SEL3(Conn, dobj);           //  ���ҹ湮����Ʈ
        return dobj;
    }
    // ���ҹ湮����
    public DOBJ CALLupso_visit_regist_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLupso_visit_regist_SEL11(Conn, dobj);           //  VISIT_SEQ���ϱ�
                dobj  = CALLupso_visit_regist_INS12(Conn, dobj);           //  ���ҹ湮�ű�
                if( dobj.getRetObject("R").getRecord().get("JOB_GBN").equals("P") || dobj.getRetObject("R").getRecord().get("JOB_GBN").equals("E"))
                {
                    dobj  = CALLupso_visit_regist_SEL88(Conn, dobj);           //  ������Ͽ���Ȯ��
                    if( ( dobj.getRetObject("SEL88").getRecordCnt() == 1 && dobj.getRetObject("SEL88").getRecord().get("VISIT_YN").equals("N") ))
                    {
                        dobj  = CALLupso_visit_regist_XIUD90(Conn, dobj);           //  �湮���� ����
                        if( dobj.getRetObject("S3").getRecordCnt() == 1)
                        {
                            dobj  = CALLupso_visit_regist_FUT16( dobj);        //  ���Ϲ���
                            dobj  = CALLupso_visit_regist_CVT17( dobj);        //  ���Ϲ����̵�����
                            dobj  = CALLupso_visit_regist_INS18(Conn, dobj);           //  ���Ͼ��ε���������
                        }
                    }
                    else
                    {
                        dobj  = CALLupso_visit_regist_XIUD91(Conn, dobj);           //  �湮���� �߰�
                        if( dobj.getRetObject("S3").getRecordCnt() == 1)
                        {
                            dobj  = CALLupso_visit_regist_FUT93( dobj);        //  ���Ϲ���
                            dobj  = CALLupso_visit_regist_CVT94( dobj);        //  ���Ϲ����̵�����
                            dobj  = CALLupso_visit_regist_INS95(Conn, dobj);           //  ���Ͼ��ε���������
                        }
                    }
                }
                else if( dobj.getRetObject("S3").getRecordCnt() == 1)
                {
                    dobj  = CALLupso_visit_regist_FUT20( dobj);        //  ���Ϲ���
                    dobj  = CALLupso_visit_regist_CVT21( dobj);        //  ���Ϲ����̵�����
                    dobj  = CALLupso_visit_regist_INS22(Conn, dobj);           //  ���Ͼ��ε���������
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_SEL24(Conn, dobj);           //  1
                if( dobj.getRetObject("R").getRecord().get("CNT1").equals("1") || dobj.getRetObject("R").getRecord().get("CNT2").equals("1"))
                {
                    dobj  = CALLupso_visit_regist_SEL26(Conn, dobj);           //  VISIT_SEQ���ϱ�
                    dobj  = CALLupso_visit_regist_XIUD27(Conn, dobj);           //  ���ҹ湮����
                    if( dobj.getRetObject("S3").getRecordCnt() == 1)
                    {
                        dobj  = CALLupso_visit_regist_DEL29(Conn, dobj);           //  ���Ͼ��ε���������
                        dobj  = CALLupso_visit_regist_FUT30( dobj);        //  ���Ϲ���
                        dobj  = CALLupso_visit_regist_CVT31( dobj);        //  ���Ϲ����̵�����
                        dobj  = CALLupso_visit_regist_INS32(Conn, dobj);           //  ���Ͼ��ε���������
                    }
                }
                else
                {
                    dobj  = CALLupso_visit_regist_UPD33(Conn, dobj);           //  ���ҹ湮����
                    if( dobj.getRetObject("S3").getRecordCnt() == 1)
                    {
                        dobj  = CALLupso_visit_regist_DEL35(Conn, dobj);           //  ���Ͼ��ε���������
                        dobj  = CALLupso_visit_regist_FUT36( dobj);        //  ���Ϲ���
                        dobj  = CALLupso_visit_regist_CVT37( dobj);        //  ���Ϲ����̵�����
                        dobj  = CALLupso_visit_regist_INS38(Conn, dobj);           //  ���Ͼ��ε���������
                    }
                }
            }
        }
        return dobj;
    }
    // VISIT_SEQ���ϱ�
    // ���ҹ湮����� �Ϸù�ȣ(4�ڸ�)�� �����Ѵ�.
    public DOBJ CALLupso_visit_regist_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_regist_SEL11(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        rvobj.Println("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("VISIT_DAY");   //�湮 ����
        String   JOB_GBN = dobj.getRetObject("R").getRecord().get("JOB_GBN");   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),  0)  +  1  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // 1
    public DOBJ CALLupso_visit_regist_SEL24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL24");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL24");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_regist_SEL24(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL24");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_SEL24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  1  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // ���ҹ湮�ű�
    // ����Ÿ�� ����Ѵ�.
    public DOBJ CALLupso_visit_regist_INS12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS12");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_INS12(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS12") ;
        rvobj.Println("INS12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_INS12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   CONSPRES = dvobj.getRecord().get("CONSPRES");   //�����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        int   CONS_SEQ = dvobj.getRecord().getInt("CONS_SEQ");   //��� ����
        String   VISIT_TIME = dvobj.getRecord().get("VISIT_TIME");   //�湮 �ð�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        int   VISIT_SEQ = dobj.getRetObject("SEL11").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, VISIT_TIME, INSPRES_ID, CONS_SEQ, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :VISIT_TIME , :INSPRES_ID , :CONS_SEQ , :VISIT_DAY , :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("CONS_SEQ", CONS_SEQ);               //��� ����
        sobj.setString("VISIT_TIME", VISIT_TIME);               //�湮 �ð�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // VISIT_SEQ���ϱ�
    // ���ҹ湮����� �Ϸù�ȣ(4�ڸ�)�� �����Ѵ�.
    public DOBJ CALLupso_visit_regist_SEL26(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL26");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL26");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_regist_SEL26(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL26");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_SEL26(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("NEW_VISIT_DAY");   //�湮 ����
        String   JOB_GBN = dobj.getRetObject("R").getRecord().get("NEW_JOB_GBN");   //���� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),  0)  +  1  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������Ͽ���Ȯ��
    public DOBJ CALLupso_visit_regist_SEL88(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL88");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL88");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_regist_SEL88(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL88");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_SEL88(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("VISIT_DAY");   //�湮 ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  VISIT_YN  FROM  GIBU.TMOB_VISIT_PLAN  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  STAFF_CD  =  :INSPRES_ID   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  'P'   \n";
        query +=" AND  VISIT_YN  IS  NOT  NULL ";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ҹ湮����
    // ������ ��ϵ� ����Ÿ�� �����Ѵ�.
    public DOBJ CALLupso_visit_regist_XIUD27(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD27");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_XIUD27(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD27");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_XIUD27(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CONSPRES = dobj.getRetObject("R").getRecord().get("CONSPRES");   //�����
        String   CONS_DATE = dobj.getRetObject("R").getRecord().get("CONS_DATE");   //��� �Ͻ�
        int   CONS_SEQ = dobj.getRetObject("R").getRecord().getInt("CONS_SEQ");   //��� ����
        String   JOB_GBN = dobj.getRetObject("R").getRecord().get("JOB_GBN");   //���� ����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   NEW_JOB_GBN = dobj.getRetObject("R").getRecord().get("NEW_JOB_GBN");   //�űԹ湮����
        String   NEW_VISIT_DAY = dobj.getRetObject("R").getRecord().get("NEW_VISIT_DAY");   //�űԹ湮����
        String   NEW_VISIT_SEQ = dobj.getRetObject("SEL24").getRecord().get("VISIT_SEQ");   //�űԹ湮����
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("VISIT_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("R").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        String   VISIT_TIME = dobj.getRetObject("R").getRecord().get("VISIT_TIME");   //�湮 �ð�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_VISIT  \n";
        query +=" SET CONSPRES = :CONSPRES , REMAK = :REMAK , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE , VISIT_DAY = :VISIT_DAY , VISIT_SEQ = :NEW_VISIT_SEQ , JOB_GBN = :JOB_GBN , CONS_DATE = :CONS_DATE , CONS_SEQ = :CONS_SEQ , VISIT_TIME = :VISIT_TIME  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND VISIT_DAY = :NEW_VISIT_DAY  \n";
        query +=" AND VISIT_SEQ = :VISIT_SEQ  \n";
        query +=" AND JOB_GBN = :NEW_JOB_GBN";
        sobj.setSql(query);
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("CONS_DATE", CONS_DATE);               //��� �Ͻ�
        sobj.setInt("CONS_SEQ", CONS_SEQ);               //��� ����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("NEW_JOB_GBN", NEW_JOB_GBN);               //�űԹ湮����
        sobj.setString("NEW_VISIT_DAY", NEW_VISIT_DAY);               //�űԹ湮����
        sobj.setString("NEW_VISIT_SEQ", NEW_VISIT_SEQ);               //�űԹ湮����
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        sobj.setString("VISIT_TIME", VISIT_TIME);               //�湮 �ð�
        return sobj;
    }
    // �湮���� ����
    public DOBJ CALLupso_visit_regist_XIUD90(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD90");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_XIUD90(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD90");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_XIUD90(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("VISIT_DAY");   //�湮 ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TMOB_VISIT_PLAN  \n";
        query +=" SET VISIT_YN = 'Y' , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND STAFF_CD = :MODPRES_ID  \n";
        query +=" AND VISIT_DAY = :VISIT_DAY  \n";
        query +=" AND JOB_GBN = 'P'  \n";
        query +=" AND VISIT_YN = 'N'";
        sobj.setSql(query);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        return sobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLupso_visit_regist_DEL29(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL29");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_DEL29(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL29") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_DEL29(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        String   JOB_GBN = dobj.getRetObject("R").getRecord().get("NEW_JOB_GBN");   //���� ����
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("NEW_VISIT_DAY");   //�湮 ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_VISIT_ATTCH  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        return sobj;
    }
    // ���Ϲ���
    public DOBJ CALLupso_visit_regist_FUT30(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT30");
        VOBJ dvobj = dobj.getRetObject("S3");      //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("FUT30");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("PATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT30") ;
        dvobj.Println("FUT30");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ϲ���
    public DOBJ CALLupso_visit_regist_FUT16(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT16");
        VOBJ dvobj = dobj.getRetObject("S3");      //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("FUT16");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("PATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT16") ;
        dvobj.Println("FUT16");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ϲ����̵�����
    public DOBJ CALLupso_visit_regist_CVT31(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT31");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("S3");        //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("CVT31");
        String[] outcolumns ={"UNIFILENAME","FILESIZE","PATH","UPFILENAME","DFILEPATH"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.cpRecord();
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            for(int i=0;i<outcolumns.length;i++)
            {
                if(!record.containsKey(outcolumns[i]))
                {
                    record.remove(outcolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        dvobj.setName("CVT31") ;
        dvobj.Println("CVT31");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ϲ����̵�����
    public DOBJ CALLupso_visit_regist_CVT17(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT17");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("S3");        //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("CVT17");
        String[] outcolumns ={"UNIFILENAME","FILESIZE","PATH","UPFILENAME","DFILEPATH"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.cpRecord();
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            for(int i=0;i<outcolumns.length;i++)
            {
                if(!record.containsKey(outcolumns[i]))
                {
                    record.remove(outcolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        dvobj.setName("CVT17") ;
        dvobj.Println("CVT17");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLupso_visit_regist_INS32(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS32");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String fullname="";
        dvobj.first();
        while(dvobj.next())
        {
            byte[] binFILES=wutil.inputFileStream("/home/jeus/komca_web/upload", dobj.getRetObject("S3").getRecord().get("UNIFILENAME"));
            dvobj.getRecord().put("FILES", binFILES);
        }
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_INS32(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS32") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_INS32(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        byte[]   FILES = dvobj.getRecord().getBytes("FILES");   //����
        String   FILE_NM = dobj.getRetObject("S3").getRecord().get("UPFILENAME");   //���ϸ�
        String   FILE_ROUT ="/home/jeus/komca_web/upload";   //���ϰ��
        double   FILE_SIZE = dobj.getRetObject("S3").getRecord().getDouble("FILESIZE");   //���� ũ��
        String   FILE_TEMPNM = dobj.getRetObject("S3").getRecord().get("UNIFILENAME");   //��ȯ ���ϸ�
        String   JOB_GBN = dobj.getRetObject("R").getRecord().get("NEW_JOB_GBN");   //���� ����
        double   SEQ = 1;   //������ȣ
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("NEW_VISIT_DAY");   //�湮 ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_ATTCH (VISIT_SEQ, FILE_SIZE, FILE_TEMPNM, VISIT_DAY, JOB_GBN, FILE_ROUT, UPSO_CD, SEQ, FILE_NM, FILES)  \n";
        query +=" values(:VISIT_SEQ , :FILE_SIZE , :FILE_TEMPNM , :VISIT_DAY , :JOB_GBN , :FILE_ROUT , :UPSO_CD , :SEQ , :FILE_NM , :FILES )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        sobj.setBlob("FILES", FILES);               //����
        sobj.setString("FILE_NM", FILE_NM);               //���ϸ�
        sobj.setString("FILE_ROUT", FILE_ROUT);               //���ϰ��
        sobj.setDouble("FILE_SIZE", FILE_SIZE);               //���� ũ��
        sobj.setString("FILE_TEMPNM", FILE_TEMPNM);               //��ȯ ���ϸ�
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        return sobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLupso_visit_regist_INS18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS18");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String fullname="";
        dvobj.first();
        while(dvobj.next())
        {
            byte[] binFILES=wutil.inputFileStream("/home/jeus/komca_web/upload", dobj.getRetObject("S3").getRecord().get("UNIFILENAME"));
            dvobj.getRecord().put("FILES", binFILES);
        }
        dvobj.Println("INS18");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_INS18(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS18") ;
        rvobj.Println("INS18");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_INS18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        byte[]   FILES = dvobj.getRecord().getBytes("FILES");   //����
        String   FILE_NM = dobj.getRetObject("S3").getRecord().get("UPFILENAME");   //���ϸ�
        String   FILE_ROUT ="/home/jeus/komca_web/upload";   //���ϰ��
        double   FILE_SIZE = dobj.getRetObject("S3").getRecord().getDouble("FILESIZE");   //���� ũ��
        String   FILE_TEMPNM = dobj.getRetObject("S3").getRecord().get("UNIFILENAME");   //��ȯ ���ϸ�
        double   SEQ = 1;   //������ȣ
        int   VISIT_SEQ = dobj.getRetObject("SEL11").getRecord().getInt("VISIT_SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_ATTCH (VISIT_SEQ, FILE_SIZE, FILE_TEMPNM, VISIT_DAY, JOB_GBN, FILE_ROUT, UPSO_CD, SEQ, FILE_NM, FILES)  \n";
        query +=" values(:VISIT_SEQ , :FILE_SIZE , :FILE_TEMPNM , :VISIT_DAY , :JOB_GBN , :FILE_ROUT , :UPSO_CD , :SEQ , :FILE_NM , :FILES )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setBlob("FILES", FILES);               //����
        sobj.setString("FILE_NM", FILE_NM);               //���ϸ�
        sobj.setString("FILE_ROUT", FILE_ROUT);               //���ϰ��
        sobj.setDouble("FILE_SIZE", FILE_SIZE);               //���� ũ��
        sobj.setString("FILE_TEMPNM", FILE_TEMPNM);               //��ȯ ���ϸ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //������ȣ
        return sobj;
    }
    // ���ҹ湮����
    // ������ ��ϵ� ����Ÿ�� �����Ѵ�.
    public DOBJ CALLupso_visit_regist_UPD33(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD33");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_UPD33(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD33") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_UPD33(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   CONSPRES = dvobj.getRecord().get("CONSPRES");   //�����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   CONS_DATE = dvobj.getRecord().get("CONS_DATE");   //��� �Ͻ�
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        int   CONS_SEQ = dvobj.getRecord().getInt("CONS_SEQ");   //��� ����
        String   VISIT_TIME = dvobj.getRecord().get("VISIT_TIME");   //�湮 �ð�
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , VISIT_TIME=:VISIT_TIME , CONS_SEQ=:CONS_SEQ , CONS_DATE=:CONS_DATE , MOD_DATE=SYSDATE , CONSPRES=:CONSPRES , REMAK=:REMAK  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("CONS_DATE", CONS_DATE);               //��� �Ͻ�
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("CONS_SEQ", CONS_SEQ);               //��� ����
        sobj.setString("VISIT_TIME", VISIT_TIME);               //�湮 �ð�
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // �湮���� �߰�
    public DOBJ CALLupso_visit_regist_XIUD91(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD91");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_XIUD91(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD91");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_XIUD91(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("VISIT_DAY");   //�湮 ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TMOB_VISIT_PLAN (STAFF_CD, BRAN_CD, VISIT_DAY, UPSO_CD, INSPRES_ID, INS_DATE, JOB_GBN) SELECT :INSPRES_ID , GIBU.FT_GET_BRAN_CD(:UPSO_CD) , :VISIT_DAY , :UPSO_CD , :INSPRES_ID , SYSDATE , 'P' FROM DUAL";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        return sobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLupso_visit_regist_DEL35(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL35");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_DEL35(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL35") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_DEL35(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_VISIT_ATTCH  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���Ϲ���
    public DOBJ CALLupso_visit_regist_FUT93(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT93");
        VOBJ dvobj = dobj.getRetObject("S3");      //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("FUT93");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("PATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT93") ;
        dvobj.Println("FUT93");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ϲ���
    public DOBJ CALLupso_visit_regist_FUT36(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT36");
        VOBJ dvobj = dobj.getRetObject("S3");      //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("FUT36");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("PATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT36") ;
        dvobj.Println("FUT36");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ϲ����̵�����
    public DOBJ CALLupso_visit_regist_CVT94(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT94");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("S3");        //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("CVT94");
        String[] outcolumns ={"UNIFILENAME","FILESIZE","PATH","UPFILENAME","DFILEPATH"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.cpRecord();
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            for(int i=0;i<outcolumns.length;i++)
            {
                if(!record.containsKey(outcolumns[i]))
                {
                    record.remove(outcolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        dvobj.setName("CVT94") ;
        dvobj.Println("CVT94");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ϲ����̵�����
    public DOBJ CALLupso_visit_regist_CVT37(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT37");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("S3");        //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("CVT37");
        String[] outcolumns ={"UNIFILENAME","FILESIZE","PATH","UPFILENAME","DFILEPATH"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.cpRecord();
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            for(int i=0;i<outcolumns.length;i++)
            {
                if(!record.containsKey(outcolumns[i]))
                {
                    record.remove(outcolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        dvobj.setName("CVT37") ;
        dvobj.Println("CVT37");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLupso_visit_regist_INS95(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS95");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String fullname="";
        dvobj.first();
        while(dvobj.next())
        {
            byte[] binFILES=wutil.inputFileStream("/home/jeus/komca_web/upload", dobj.getRetObject("S3").getRecord().get("UNIFILENAME"));
            dvobj.getRecord().put("FILES", binFILES);
        }
        dvobj.Println("INS95");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_INS95(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS95") ;
        rvobj.Println("INS95");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_INS95(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        byte[]   FILES = dvobj.getRecord().getBytes("FILES");   //����
        String   FILE_NM = dobj.getRetObject("S3").getRecord().get("UPFILENAME");   //���ϸ�
        String   FILE_ROUT ="/home/jeus/komca_web/upload";   //���ϰ��
        double   FILE_SIZE = dobj.getRetObject("S3").getRecord().getDouble("FILESIZE");   //���� ũ��
        String   FILE_TEMPNM = dobj.getRetObject("S3").getRecord().get("UNIFILENAME");   //��ȯ ���ϸ�
        double   SEQ = 1;   //������ȣ
        int   VISIT_SEQ = dobj.getRetObject("SEL11").getRecord().getInt("VISIT_SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_ATTCH (VISIT_SEQ, FILE_SIZE, FILE_TEMPNM, VISIT_DAY, JOB_GBN, FILE_ROUT, UPSO_CD, SEQ, FILE_NM, FILES)  \n";
        query +=" values(:VISIT_SEQ , :FILE_SIZE , :FILE_TEMPNM , :VISIT_DAY , :JOB_GBN , :FILE_ROUT , :UPSO_CD , :SEQ , :FILE_NM , :FILES )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setBlob("FILES", FILES);               //����
        sobj.setString("FILE_NM", FILE_NM);               //���ϸ�
        sobj.setString("FILE_ROUT", FILE_ROUT);               //���ϰ��
        sobj.setDouble("FILE_SIZE", FILE_SIZE);               //���� ũ��
        sobj.setString("FILE_TEMPNM", FILE_TEMPNM);               //��ȯ ���ϸ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //������ȣ
        return sobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLupso_visit_regist_INS38(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS38");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String fullname="";
        dvobj.first();
        while(dvobj.next())
        {
            byte[] binFILES=wutil.inputFileStream("/home/jeus/komca_web/upload", dobj.getRetObject("S3").getRecord().get("UNIFILENAME"));
            dvobj.getRecord().put("FILES", binFILES);
        }
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_INS38(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS38") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_INS38(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        byte[]   FILES = dvobj.getRecord().getBytes("FILES");   //����
        String   FILE_NM = dobj.getRetObject("S3").getRecord().get("UPFILENAME");   //���ϸ�
        String   FILE_ROUT ="/home/jeus/komca_web/upload";   //���ϰ��
        double   FILE_SIZE = dobj.getRetObject("S3").getRecord().getDouble("FILESIZE");   //���� ũ��
        String   FILE_TEMPNM = dobj.getRetObject("S3").getRecord().get("UNIFILENAME");   //��ȯ ���ϸ�
        double   SEQ = 1;   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_ATTCH (VISIT_SEQ, FILE_SIZE, FILE_TEMPNM, VISIT_DAY, JOB_GBN, FILE_ROUT, UPSO_CD, SEQ, FILE_NM, FILES)  \n";
        query +=" values(:VISIT_SEQ , :FILE_SIZE , :FILE_TEMPNM , :VISIT_DAY , :JOB_GBN , :FILE_ROUT , :UPSO_CD , :SEQ , :FILE_NM , :FILES )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        sobj.setBlob("FILES", FILES);               //����
        sobj.setString("FILE_NM", FILE_NM);               //���ϸ�
        sobj.setString("FILE_ROUT", FILE_ROUT);               //���ϰ��
        sobj.setDouble("FILE_SIZE", FILE_SIZE);               //���� ũ��
        sobj.setString("FILE_TEMPNM", FILE_TEMPNM);               //��ȯ ���ϸ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // ���Ϲ���
    public DOBJ CALLupso_visit_regist_FUT20(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT20");
        VOBJ dvobj = dobj.getRetObject("S3");      //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("FUT20");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("PATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT20") ;
        dvobj.Println("FUT20");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ϲ����̵�����
    public DOBJ CALLupso_visit_regist_CVT21(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT21");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("S3");        //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("CVT21");
        String[] outcolumns ={"UNIFILENAME","FILESIZE","PATH","UPFILENAME","DFILEPATH"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.cpRecord();
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            for(int i=0;i<outcolumns.length;i++)
            {
                if(!record.containsKey(outcolumns[i]))
                {
                    record.remove(outcolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        dvobj.setName("CVT21") ;
        dvobj.Println("CVT21");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLupso_visit_regist_INS22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS22");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String fullname="";
        dvobj.first();
        while(dvobj.next())
        {
            byte[] binFILES=wutil.inputFileStream("/home/jeus/komca_web/upload", dobj.getRetObject("S3").getRecord().get("UNIFILENAME"));
            dvobj.getRecord().put("FILES", binFILES);
        }
        dvobj.Println("INS22");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_INS22(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS22") ;
        rvobj.Println("INS22");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_INS22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        byte[]   FILES = dvobj.getRecord().getBytes("FILES");   //����
        String   FILE_NM = dobj.getRetObject("S3").getRecord().get("UPFILENAME");   //���ϸ�
        String   FILE_ROUT ="/home/jeus/komca_web/upload";   //���ϰ��
        double   FILE_SIZE = dobj.getRetObject("S3").getRecord().getDouble("FILESIZE");   //���� ũ��
        String   FILE_TEMPNM = dobj.getRetObject("S3").getRecord().get("UNIFILENAME");   //��ȯ ���ϸ�
        double   SEQ = 1;   //������ȣ
        int   VISIT_SEQ = dobj.getRetObject("SEL11").getRecord().getInt("VISIT_SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_ATTCH (VISIT_SEQ, FILE_SIZE, FILE_TEMPNM, VISIT_DAY, JOB_GBN, FILE_ROUT, UPSO_CD, SEQ, FILE_NM, FILES)  \n";
        query +=" values(:VISIT_SEQ , :FILE_SIZE , :FILE_TEMPNM , :VISIT_DAY , :JOB_GBN , :FILE_ROUT , :UPSO_CD , :SEQ , :FILE_NM , :FILES )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setBlob("FILES", FILES);               //����
        sobj.setString("FILE_NM", FILE_NM);               //���ϸ�
        sobj.setString("FILE_ROUT", FILE_ROUT);               //���ϰ��
        sobj.setDouble("FILE_SIZE", FILE_SIZE);               //���� ũ��
        sobj.setString("FILE_TEMPNM", FILE_TEMPNM);               //��ȯ ���ϸ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //������ȣ
        return sobj;
    }
    // ���ҹ湮�󼼰���
    public DOBJ CALLupso_visit_regist_MIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD2");
        VOBJ dvobj = dobj.getRetObject("S1");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_INS5(Conn, dobj);           //  ���ҹ湮���Է�
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_UPD7(Conn, dobj);           //  ���ҹ湮�󼼼���
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_DEL9(Conn, dobj);           //  ���ҹ湮�󼼻���
            }
        }
        return dobj;
    }
    // ���ҹ湮�󼼻���
    // ������ ����� �󼼳����� �����Ѵ�.
    public DOBJ CALLupso_visit_regist_DEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_DEL9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_DEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        int   VISIT_NUM = dvobj.getRecord().getInt("VISIT_NUM");   //�湮 ��ȣ
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and VISIT_NUM=:VISIT_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //�湮 ��ȣ
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���ҹ湮���Է�
    // �󼼳����� ����Ѵ�.
    public DOBJ CALLupso_visit_regist_INS5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLupso_visit_regist_INS5(dobj, dvobj);
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
    private SQLObject SQLupso_visit_regist_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        int   VISIT_NUM = dvobj.getRecord().getInt("VISIT_NUM");   //�湮 ��ȣ
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        int  VISIT_SEQ ;         //�湮�ڼ���
        if( ( dobj.getRetObject("S").getRecord().get("IUDFLAG").equals("I") ))
        {
            VISIT_SEQ = dobj.getRetObject("SEL11").getRecord().getInt("VISIT_SEQ");
        }
        else
        {
            VISIT_SEQ = dobj.getRetObject("R").getRecord().getInt("VISIT_SEQ");
        }
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :VISIT_NUM , :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //�湮 ��ȣ
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���ҹ湮�󼼼���
    // ������ ��ϵ� �󼼳����� �����Ѵ�.
    public DOBJ CALLupso_visit_regist_UPD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_UPD7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_UPD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        int   VISIT_NUM = dvobj.getRecord().getInt("VISIT_NUM");   //�湮 ��ȣ
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MOD_DATE=SYSDATE , REMAK=:REMAK  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and VISIT_NUM=:VISIT_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //�湮 ��ȣ
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // ���ҹ湮����Ʈ
    // ���ҹ湮 ���̺� ��ϵ� ����Ÿ�� ��ȸ�Ѵ�.
    public DOBJ CALLupso_visit_regist_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_regist_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S2").getRecord().get("START_DAY");   //������
        String   UPSO_CD = dobj.getRetObject("S2").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   END_DAY = dobj.getRetObject("S2").getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.VISIT_DAY  ,  A.JOB_GBN  ,  B.CODE_NM  ,  A.CONSPRES  ,  A.REMAK  ,  A.VISIT_SEQ  ,  A.CONS_DATE  ,  A.CONS_SEQ  ,  '/home/jeus/komca_web/upload'  AS  FILE_ROUT  ,  C.FILE_NM  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  FIDU.TENV_CODE  B  ,  GIBU.TBRA_UPSO_ADRS_FILEINFO  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.HIGH_CD  =  '00198'   \n";
        query +=" AND  A.JOB_GBN  =  B.CODE_CD   \n";
        query +=" AND  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.CONS_DATE  =  C.CONS_DATE(+)   \n";
        query +=" AND  A.CONS_SEQ  =  C.CONS_SEQ(+)  ORDER  BY  VISIT_DAY  DESC ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    //##**$$upso_visit_regist
    //##**$$end
}
