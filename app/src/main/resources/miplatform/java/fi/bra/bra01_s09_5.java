
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s09_5
{
    public bra01_s09_5()
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
            dobj  = CALLupso_visit_regist_MIUD13(Conn, dobj);           //  ���ҹ湮�󼼰���
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_visit_regist_SEL24(Conn, dobj);           //  ���ҹ湮����Ʈ
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
        dobj  = CALLupso_visit_regist_MIUD13(Conn, dobj);           //  ���ҹ湮�󼼰���
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupso_visit_regist_SEL24(Conn, dobj);           //  ���ҹ湮����Ʈ
        return dobj;
    }
    // ���ҹ湮����
    public DOBJ CALLupso_visit_regist_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLupso_visit_regist_SEL19(Conn, dobj);           //  VISIT_SEQ���ϱ�
                dobj  = CALLupso_visit_regist_INS5(Conn, dobj);           //  ���ҹ湮�ű�
                if( dobj.getRetObject("S3").getRecordCnt() == 1)
                {
                    dobj  = CALLupso_visit_regist_FUT16( dobj);        //  ���Ϲ���
                    dobj  = CALLupso_visit_regist_CVT17( dobj);        //  ���Ϲ����̵�����
                    dobj  = CALLupso_visit_regist_INS18(Conn, dobj);           //  ���Ͼ��ε���������
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_SEL30(Conn, dobj);           //  1
                if( dobj.getRetObject("R").getRecord().get("CNT1").equals("1") || dobj.getRetObject("R").getRecord().get("CNT2").equals("1"))
                {
                    dobj  = CALLupso_visit_regist_SEL27(Conn, dobj);           //  VISIT_SEQ���ϱ�
                    dobj  = CALLupso_visit_regist_XIUD31(Conn, dobj);           //  ���ҹ湮����
                    if( dobj.getRetObject("S3").getRecordCnt() == 1)
                    {
                        dobj  = CALLupso_visit_regist_DEL23(Conn, dobj);           //  ���Ͼ��ε���������
                        dobj  = CALLupso_visit_regist_FUT24( dobj);        //  ���Ϲ���
                        dobj  = CALLupso_visit_regist_CVT25( dobj);        //  ���Ϲ����̵�����
                        dobj  = CALLupso_visit_regist_INS26(Conn, dobj);           //  ���Ͼ��ε���������
                    }
                }
                else
                {
                    dobj  = CALLupso_visit_regist_UPD32(Conn, dobj);           //  ���ҹ湮����
                    if( dobj.getRetObject("S3").getRecordCnt() == 1)
                    {
                        dobj  = CALLupso_visit_regist_DEL34(Conn, dobj);           //  ���Ͼ��ε���������
                        dobj  = CALLupso_visit_regist_FUT35( dobj);        //  ���Ϲ���
                        dobj  = CALLupso_visit_regist_CVT36( dobj);        //  ���Ϲ����̵�����
                        dobj  = CALLupso_visit_regist_INS37(Conn, dobj);           //  ���Ͼ��ε���������
                    }
                }
            }
        }
        return dobj;
    }
    // VISIT_SEQ���ϱ�
    // ���ҹ湮����� �Ϸù�ȣ(4�ڸ�)�� �����Ѵ�.
    public DOBJ CALLupso_visit_regist_SEL19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL19");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL19");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_regist_SEL19(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_SEL19(DOBJ dobj, VOBJ dvobj) throws Exception
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
    public DOBJ CALLupso_visit_regist_SEL30(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL30");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL30");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_regist_SEL30(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL30");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_SEL30(DOBJ dobj, VOBJ dvobj) throws Exception
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
        String   CONSPRES = dvobj.getRecord().get("CONSPRES");   //�����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        String   VISIT_TIME = dvobj.getRecord().get("VISIT_TIME");   //�湮 �ð�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, VISIT_TIME, INSPRES_ID, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :VISIT_TIME , :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("VISIT_TIME", VISIT_TIME);               //�湮 �ð�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // VISIT_SEQ���ϱ�
    // ���ҹ湮����� �Ϸù�ȣ(4�ڸ�)�� �����Ѵ�.
    public DOBJ CALLupso_visit_regist_SEL27(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL27");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL27");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_regist_SEL27(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL27");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_SEL27(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // ���ҹ湮����
    // ������ ��ϵ� ����Ÿ�� �����Ѵ�.
    public DOBJ CALLupso_visit_regist_XIUD31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD31");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_XIUD31(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD31");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_XIUD31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CONSPRES = dobj.getRetObject("R").getRecord().get("CONSPRES");   //�����
        String   CONS_DATE = dobj.getRetObject("R").getRecord().get("CONS_DATE");   //��� �Ͻ�
        int   CONS_SEQ = dobj.getRetObject("R").getRecord().getInt("CONS_SEQ");   //��� ����
        String   JOB_GBN = dobj.getRetObject("R").getRecord().get("JOB_GBN");   //���� ����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   NEW_JOB_GBN = dobj.getRetObject("R").getRecord().get("NEW_JOB_GBN");   //�űԹ湮����
        String   NEW_VISIT_DAY = dobj.getRetObject("R").getRecord().get("NEW_VISIT_DAY");   //�űԹ湮����
        String   NEW_VISIT_SEQ = dobj.getRetObject("SEL27").getRecord().get("VISIT_SEQ");   //�űԹ湮����
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("VISIT_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("R").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        String   VISIT_TIME = dobj.getRetObject("R").getRecord().get("VISIT_TIME");   //�湮 �ð�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_VISIT  \n";
        query +=" SET CONSPRES = :CONSPRES , REMAK = :REMAK , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE , VISIT_DAY = :NEW_VISIT_DAY , VISIT_SEQ = :NEW_VISIT_SEQ , JOB_GBN = :NEW_JOB_GBN , CONS_DATE = :CONS_DATE , CONS_SEQ = :CONS_SEQ , VISIT_TIME = :VISIT_TIME  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND VISIT_DAY = :VISIT_DAY  \n";
        query +=" AND VISIT_SEQ = :VISIT_SEQ  \n";
        query +=" AND JOB_GBN = :JOB_GBN";
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
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_INS18(dobj, dvobj);
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
    private SQLObject SQLupso_visit_regist_INS18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        byte[]   FILES = dvobj.getRecord().getBytes("FILES");   //����
        String   FILE_NM = dobj.getRetObject("S3").getRecord().get("UPFILENAME");   //���ϸ�
        String   FILE_ROUT = dobj.getRetObject("CVT17").getRecord().get("DFILEPATH");   //���ϰ��
        double   FILE_SIZE = dobj.getRetObject("S3").getRecord().getDouble("FILESIZE");   //���� ũ��
        String   FILE_TEMPNM = dobj.getRetObject("S3").getRecord().get("UNIFILENAME");   //��ȯ ���ϸ�
        double   SEQ = 1;   //������ȣ
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //������ȣ
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
    public DOBJ CALLupso_visit_regist_DEL23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL23");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_DEL23(dobj, dvobj);
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
    private SQLObject SQLupso_visit_regist_DEL23(DOBJ dobj, VOBJ dvobj) throws Exception
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
    public DOBJ CALLupso_visit_regist_FUT24(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT24");
        VOBJ dvobj = dobj.getRetObject("S3");      //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("FUT24");
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
        dvobj.setName("FUT24") ;
        dvobj.Println("FUT24");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ϲ����̵�����
    public DOBJ CALLupso_visit_regist_CVT25(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT25");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("S3");        //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("CVT25");
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
        dvobj.setName("CVT25") ;
        dvobj.Println("CVT25");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLupso_visit_regist_INS26(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS26");
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
            sobj = SQLupso_visit_regist_INS26(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS26") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_INS26(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        byte[]   FILES = dvobj.getRecord().getBytes("FILES");   //����
        String   FILE_NM = dobj.getRetObject("S3").getRecord().get("UPFILENAME");   //���ϸ�
        String   FILE_ROUT = dobj.getRetObject("CVT25").getRecord().get("DFILEPATH");   //���ϰ��
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
    // ���ҹ湮����
    // ������ ��ϵ� ����Ÿ�� �����Ѵ�.
    public DOBJ CALLupso_visit_regist_UPD32(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD32");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_UPD32(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD32") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_UPD32(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // ���Ͼ��ε���������
    public DOBJ CALLupso_visit_regist_DEL34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL34");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_DEL34(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL34") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_DEL34(DOBJ dobj, VOBJ dvobj) throws Exception
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
    public DOBJ CALLupso_visit_regist_FUT35(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT35");
        VOBJ dvobj = dobj.getRetObject("S3");      //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("FUT35");
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
        dvobj.setName("FUT35") ;
        dvobj.Println("FUT35");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ϲ����̵�����
    public DOBJ CALLupso_visit_regist_CVT36(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT36");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("S3");        //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("CVT36");
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
        dvobj.setName("CVT36") ;
        dvobj.Println("CVT36");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLupso_visit_regist_INS37(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS37");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_INS37(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS37") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_INS37(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //���� ����
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //�湮 ����
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        byte[]   FILES = dvobj.getRecord().getBytes("FILES");   //����
        String   FILE_NM = dobj.getRetObject("S3").getRecord().get("UPFILENAME");   //���ϸ�
        String   FILE_ROUT = dobj.getRetObject("CVT25").getRecord().get("DFILEPATH");   //���ϰ��
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
    // ���ҹ湮�󼼰���
    public DOBJ CALLupso_visit_regist_MIUD13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD13");
        VOBJ dvobj = dobj.getRetObject("S1");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_INS7(Conn, dobj);           //  ���ҹ湮���Է�
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_UPD17(Conn, dobj);           //  ���ҹ湮�󼼼���
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_DEL18(Conn, dobj);           //  ���ҹ湮�󼼻���
            }
        }
        return dobj;
    }
    // ���ҹ湮�󼼻���
    // ������ ����� �󼼳����� �����Ѵ�.
    public DOBJ CALLupso_visit_regist_DEL18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL18");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_DEL18(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL18") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_DEL18(DOBJ dobj, VOBJ dvobj) throws Exception
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
    public DOBJ CALLupso_visit_regist_INS7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLupso_visit_regist_INS7(dobj, dvobj);
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
    private SQLObject SQLupso_visit_regist_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
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
            VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");
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
    public DOBJ CALLupso_visit_regist_UPD17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD17");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_UPD17(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD17") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_UPD17(DOBJ dobj, VOBJ dvobj) throws Exception
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
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_SEL24(DOBJ dobj, VOBJ dvobj) throws Exception
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
    //##**$$upso_visit_regist_bre
    /*
    */
    public DOBJ CTLupso_visit_regist_bre(DOBJ dobj)
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
            dobj  = CALLupso_visit_regist_bre_MIUD13(Conn, dobj);           //  ���ҹ湮�󼼰���
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
    public DOBJ CTLupso_visit_regist_bre( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_visit_regist_bre_MIUD13(Conn, dobj);           //  ���ҹ湮�󼼰���
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ���ҹ湮�󼼰���
    public DOBJ CALLupso_visit_regist_bre_MIUD13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD13");
        VOBJ dvobj = dobj.getRetObject("S1");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_bre_INS7(Conn, dobj);           //  ���ҹ湮���Է�
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_bre_UPD17(Conn, dobj);           //  ���ҹ湮�󼼼���
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_bre_DEL18(Conn, dobj);           //  ���ҹ湮�󼼻���
            }
        }
        return dobj;
    }
    // ���ҹ湮�󼼻���
    // ������ ����� �󼼳����� �����Ѵ�.
    public DOBJ CALLupso_visit_regist_bre_DEL18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL18");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_bre_DEL18(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL18") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_bre_DEL18(DOBJ dobj, VOBJ dvobj) throws Exception
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
    public DOBJ CALLupso_visit_regist_bre_INS7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLupso_visit_regist_bre_INS7(dobj, dvobj);
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
    private SQLObject SQLupso_visit_regist_bre_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
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
            VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");
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
    public DOBJ CALLupso_visit_regist_bre_UPD17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD17");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_bre_UPD17(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD17") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_bre_UPD17(DOBJ dobj, VOBJ dvobj) throws Exception
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
    //##**$$upso_visit_regist_bre
    //##**$$end
}
