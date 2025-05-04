
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s09
{
    public bra01_s09()
    {
    }
    //##**$$upso_doc_view
    /*
    */
    public DOBJ CTLupso_doc_view(DOBJ dobj)
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
            dobj  = CALLupso_doc_view_SEL1(Conn, dobj);           //  ������ Ȯ�μ�����
            dobj  = CALLupso_doc_view_SEL2(Conn, dobj);           //  ÷������ ���
            dobj  = CALLupso_doc_view_MPD3(Conn, dobj);           //  ����
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
    public DOBJ CTLupso_doc_view( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_doc_view_SEL1(Conn, dobj);           //  ������ Ȯ�μ�����
        dobj  = CALLupso_doc_view_SEL2(Conn, dobj);           //  ÷������ ���
        dobj  = CALLupso_doc_view_MPD3(Conn, dobj);           //  ����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ������ Ȯ�μ�����
    public DOBJ CALLupso_doc_view_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_doc_view_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_doc_view_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SEQ = dobj.getRetObject("S").getRecord().get("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.SEND_DAY  ,  A.SEQ  ,  A.GBN  ,  A.MNG_GBN  ,  A.RESINUM  ,  A.NM  ,  A.PHON_NUM  ,  A.START_YRMN  ,  A.END_YRMN  ,  NVL(A.START_DAY,  A.START_YRMN  ||  '01')  AS  START_DAY  ,  DECODE(A.GBN,  '1',  NVL(A.END_DAY,  TO_CHAR(LAST_DAY(TO_DATE(A.END_YRMN  ||  '01',  'YYYYMMDD')),  'YYYYMMDD')),  '')  AS  END_DAY  ,  A.REMAK  ,  A.STAFF_NM  ,  A.INS_DATE  ,  B.FILE_ROUT  ,  B.FILE_TEMPNM  ,  A.SATN_NUM  ,  A.NONPY_PLAN  ,  A.REAS_GBN  FROM  GIBU.TBRA_CONFIRM_DOC  A,  GIBU.TBRA_CONFIRM_DOC_ATTCH  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.SEQ  =  :SEQ   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD(+)   \n";
        query +=" AND  A.SEQ  =  B.SEQ(+) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // ÷������ ���
    // ÷������ ����� �����´�.
    public DOBJ CALLupso_doc_view_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_doc_view_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_doc_view_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SEQ = dobj.getRetObject("S").getRecord().get("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  SEQ  ,  MNG_NUM  ,  FILE_ROUT  AS  PATH  ,  FILE_TEMPNM  AS  UNIFILENAME  ,  FILE_NM  AS  UPFILENAME  ,  FILES  AS  ATTCH_FILE_CTENT  ,  LENGTH(FILE_NM)  AS  PROF_CHG_FILENM_LEN  ,  FILE_TYPE  FROM  GIBU.TBRA_CONFIRM_DOC_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SEQ  =  :SEQ  ORDER  BY  MNG_NUM ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // ����
    public DOBJ CALLupso_doc_view_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("SEL2");         //÷������ ��Ͽ��� ������Ų OBJECT�Դϴ�.(CALLupso_doc_view_SEL2)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("PROF_CHG_FILENM_LEN").equals("0"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_doc_view_SEL4(Conn, dobj);           //  �������ϻ���
            }
        }
        return dobj;
    }
    // �������ϻ���
    // THOM_PRODSTTNTLETTER_ATTCHFILE�� �̿��Ͽ� �������� ����
    public DOBJ CALLupso_doc_view_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_doc_view_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        String fullname="";
        rvobj.first();
        while(rvobj.next())
        {
            wutil.makeFileOverwirte( dobj.getRetObject("R").getRecord().get("PATH"), dobj.getRetObject("R").getRecord().get("UNIFILENAME"),rvobj.getRecord().getBytes("FILES"));
        }
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_doc_view_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   MNG_NUM = dobj.getRetObject("R").getRecord().getDouble("MNG_NUM");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SEQ = dobj.getRetObject("R").getRecord().get("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  FILE_ROUT  ,  FILE_TEMPNM  ,  FILE_NM  ,  FILES  FROM  GIBU.TBRA_CONFIRM_DOC_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SEQ  =  :SEQ   \n";
        query +=" AND  MNG_NUM  =  :MNG_NUM ";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    //##**$$upso_doc_view
    //##**$$upso_doc_save
    /*
    */
    public DOBJ CTLupso_doc_save(DOBJ dobj)
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
            dobj  = CALLupso_doc_save_MIUD1(Conn, dobj);           //  ������Ȯ�μ�����
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
    public DOBJ CTLupso_doc_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_doc_save_MIUD1(Conn, dobj);           //  ������Ȯ�μ�����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ������Ȯ�μ�����
    public DOBJ CALLupso_doc_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLupso_doc_save_SEL19(Conn, dobj);           //  VISIT_SEQ���ϱ�
                dobj  = CALLupso_doc_save_INS5(Conn, dobj);           //  ���ҹ湮�ű�
                dobj  = CALLupso_doc_save_SEL12(Conn, dobj);           //  seq ȹ��
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_doc_save_UPD8(Conn, dobj);           //  ���ҹ湮����
                dobj  = CALLupso_doc_save_SEL14(Conn, dobj);           //  SEQȹ��
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_doc_save_SEL13(Conn, dobj);           //  �����ȣ Ȯ��
                if( dobj.getRetObject("SEL13").getRecord().getDouble("CNT") == 0)
                {
                    dobj  = CALLupso_doc_save_DEL10(Conn, dobj);           //  �����ͻ���
                    dobj  = CALLupso_doc_save_DEL11(Conn, dobj);           //  ÷�����ϻ���
                }
            }
        }
        return dobj;
    }
    // �����ȣ Ȯ��
    public DOBJ CALLupso_doc_save_SEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL13");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_doc_save_SEL13(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_doc_save_SEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SEQ = dobj.getRetObject("R").getRecord().get("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(LENGTH(SATN_NUM),  0)  AS  CNT  FROM  GIBU.TBRA_CONFIRM_DOC  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // ���ҹ湮����
    // ������ ��ϵ� ����Ÿ�� �����Ѵ�.
    public DOBJ CALLupso_doc_save_UPD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_doc_save_UPD8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_doc_save_UPD8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   END_DAY = dvobj.getRecord().get("END_DAY");   //������
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   NM = dvobj.getRecord().get("NM");   //����
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   MNG_GBN = dvobj.getRecord().get("MNG_GBN");   //���� ����
        String   NONPY_PLAN = dvobj.getRecord().get("NONPY_PLAN");   //�̼��ݾ���ġ��ȹ
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   START_DAY = dvobj.getRecord().get("START_DAY");   //������
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //���۳��
        String   STAFF_NM = dvobj.getRecord().get("STAFF_NM");   //��� ��
        String   SEQ = dvobj.getRecord().get("SEQ");   //������ȣ
        String   PHON_NUM = dvobj.getRecord().get("PHON_NUM");   //��ȭ ��ȣ
        String   SEND_DAY = dvobj.getRecord().get("SEND_DAY");   //�߼�����
        String   GBN = dvobj.getRecord().get("GBN");   //����
        String   REAS_GBN = dvobj.getRecord().get("REAS_GBN");   //��������
        String   MODPRES_ID = dobj.getRetObject("GOU").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_CONFIRM_DOC  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , REAS_GBN=:REAS_GBN , GBN=:GBN , SEND_DAY=:SEND_DAY , PHON_NUM=:PHON_NUM , STAFF_NM=:STAFF_NM , START_YRMN=:START_YRMN , START_DAY=:START_DAY , RESINUM=:RESINUM , MOD_DATE=SYSDATE , NONPY_PLAN=:NONPY_PLAN , MNG_GBN=:MNG_GBN , END_YRMN=:END_YRMN , NM=:NM , REMAK=:REMAK , END_DAY=:END_DAY  \n";
        query +=" where SEQ=:SEQ  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("NM", NM);               //����
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("MNG_GBN", MNG_GBN);               //���� ����
        sobj.setString("NONPY_PLAN", NONPY_PLAN);               //�̼��ݾ���ġ��ȹ
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("STAFF_NM", STAFF_NM);               //��� ��
        sobj.setString("SEQ", SEQ);               //������ȣ
        sobj.setString("PHON_NUM", PHON_NUM);               //��ȭ ��ȣ
        sobj.setString("SEND_DAY", SEND_DAY);               //�߼�����
        sobj.setString("GBN", GBN);               //����
        sobj.setString("REAS_GBN", REAS_GBN);               //��������
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // VISIT_SEQ���ϱ�
    // ���ҹ湮����� �Ϸù�ȣ(4�ڸ�)�� �����Ѵ�.
    public DOBJ CALLupso_doc_save_SEL19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL19");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL19");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_doc_save_SEL19(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_doc_save_SEL19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  SEQ  FROM  GIBU.TBRA_CONFIRM_DOC  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // SEQȹ��
    public DOBJ CALLupso_doc_save_SEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL14");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL14");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_doc_save_SEL14(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_doc_save_SEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SEQ = dobj.getRetObject("R").getRecord().get("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD,  :SEQ  AS  SEQ  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // ���ҹ湮�ű�
    // ����Ÿ�� ����Ѵ�.
    public DOBJ CALLupso_doc_save_INS5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLupso_doc_save_INS5(dobj, dvobj);
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
    private SQLObject SQLupso_doc_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   END_DAY = dvobj.getRecord().get("END_DAY");   //������
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   NM = dvobj.getRecord().get("NM");   //����
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   MNG_GBN = dvobj.getRecord().get("MNG_GBN");   //���� ����
        String   NONPY_PLAN = dvobj.getRecord().get("NONPY_PLAN");   //�̼��ݾ���ġ��ȹ
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   START_DAY = dvobj.getRecord().get("START_DAY");   //������
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //���۳��
        String   STAFF_NM = dvobj.getRecord().get("STAFF_NM");   //��� ��
        String   PHON_NUM = dvobj.getRecord().get("PHON_NUM");   //��ȭ ��ȣ
        String   SEND_DAY = dvobj.getRecord().get("SEND_DAY");   //�߼�����
        String   GBN = dvobj.getRecord().get("GBN");   //����
        String   REAS_GBN = dvobj.getRecord().get("REAS_GBN");   //��������
        String   INSPRES_ID = dobj.getRetObject("GOU").getRecord().get("USER_ID");   //����� ID
        String   SEQ = dobj.getRetObject("SEL19").getRecord().get("SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_CONFIRM_DOC (REAS_GBN, INSPRES_ID, GBN, SEND_DAY, PHON_NUM, SEQ, STAFF_NM, START_YRMN, INS_DATE, START_DAY, UPSO_CD, RESINUM, NONPY_PLAN, MNG_GBN, END_YRMN, NM, REMAK, END_DAY)  \n";
        query +=" values(:REAS_GBN , :INSPRES_ID , :GBN , :SEND_DAY , :PHON_NUM , :SEQ , :STAFF_NM , :START_YRMN , SYSDATE, :START_DAY , :UPSO_CD , :RESINUM , :NONPY_PLAN , :MNG_GBN , :END_YRMN , :NM , :REMAK , :END_DAY )";
        sobj.setSql(query);
        sobj.setString("END_DAY", END_DAY);               //������
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("NM", NM);               //����
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("MNG_GBN", MNG_GBN);               //���� ����
        sobj.setString("NONPY_PLAN", NONPY_PLAN);               //�̼��ݾ���ġ��ȹ
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("STAFF_NM", STAFF_NM);               //��� ��
        sobj.setString("PHON_NUM", PHON_NUM);               //��ȭ ��ȣ
        sobj.setString("SEND_DAY", SEND_DAY);               //�߼�����
        sobj.setString("GBN", GBN);               //����
        sobj.setString("REAS_GBN", REAS_GBN);               //��������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("SEQ", SEQ);               //�湮�ڼ���
        return sobj;
    }
    // �����ͻ���
    public DOBJ CALLupso_doc_save_DEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_doc_save_DEL10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL10") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_doc_save_DEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SEQ = dvobj.getRecord().get("SEQ");   //������ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_CONFIRM_DOC  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // seq ȹ��
    public DOBJ CALLupso_doc_save_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_doc_save_SEL12(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_doc_save_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   SEQ = dobj.getRetObject("SEL19").getRecord().get("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD,  :SEQ  AS  SEQ  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // ÷�����ϻ���
    public DOBJ CALLupso_doc_save_DEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL11");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_doc_save_DEL11(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL11") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_doc_save_DEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SEQ = dvobj.getRecord().get("SEQ");   //������ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_CONFIRM_DOC_ATTCH  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$upso_doc_save
    //##**$$upso_doc_list
    /*
    */
    public DOBJ CTLupso_doc_list(DOBJ dobj)
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
            dobj  = CALLupso_doc_list_SEL1(Conn, dobj);           //  ���ҹ湮����Ʈ
            dobj  = CALLupso_doc_list_SEL2(Conn, dobj);           //  ��������
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
    public DOBJ CTLupso_doc_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_doc_list_SEL1(Conn, dobj);           //  ���ҹ湮����Ʈ
        dobj  = CALLupso_doc_list_SEL2(Conn, dobj);           //  ��������
        return dobj;
    }
    // ���ҹ湮����Ʈ
    // ����,�ְ��������,����Ŭ����, ���Ҹ� ����� ��ϵ� ������ ��ȸ�Ѵ�.
    public DOBJ CALLupso_doc_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_doc_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_doc_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dvobj.getRecord().get("START_DAY");   //������
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   END_DAY = dvobj.getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.SEND_DAY  ,  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  AS  INS_DATE  ,  A.SEQ  ,  A.GBN  ,   \n";
        query +=" (SELECT  COUNT(FILE_NM)  FROM  GIBU.TBRA_CONFIRM_DOC_ATTCH  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.SEQ  =  A.SEQ)  AS  CNT  ,  A.SATN_NUM  ,  A.START_DAY  ,  A.END_DAY  FROM  GIBU.TBRA_CONFIRM_DOC  A  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.SEND_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  ORDER  BY  SEND_DAY  DESC ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    // ��������
    // ������ �⺻������ ��ȸ�Ѵ�.
    public DOBJ CALLupso_doc_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_doc_list_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_doc_list_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.UPSO_PHON  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,'',  '',',  '  ||  XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  AS  ADDR  ,  XA.BIOWN_NUM  ,  XA.MNGEMSTR_NM  ,  XA.MNGEMSTR_RESINUM  ,  XA.MNGEMSTR_HPNUM  ,  XA.PERMMSTR_NM  ,  XA.PERMMSTR_RESINUM  ,  XA.PERMMSTR_HPNUM  ,  XA.STAFF_CD  ,  XC.HAN_NM  AS  STAFF_NM  ,  XA.OPBI_DAY  FROM  GIBU.TBRA_UPSO  XA  ,  INSA.TINS_MST01  XC  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XC.STAFF_NUM(+)  =  XA.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$upso_doc_list
    //##**$$upso_doc_fileup
    /*
    */
    public DOBJ CTLupso_doc_fileup(DOBJ dobj)
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
            dobj  = CALLupso_doc_fileup_MIUD15(Conn, dobj);           //  �б�
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
    public DOBJ CTLupso_doc_fileup( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_doc_fileup_MIUD15(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLupso_doc_fileup_MIUD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD15");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MIUD15");
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_doc_fileup_FUT1( dobj);        //  ���Ϲ���
                dobj  = CALLupso_doc_fileup_CVT2( dobj);        //  ���Ϲ����̵�����
                dobj  = CALLupso_doc_fileup_INS5(Conn, dobj);           //  ���Ͼ��ε���������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_doc_fileup_UPD9(Conn, dobj);           //  ���ϱ��� ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_doc_fileup_DEL8(Conn, dobj);           //  ����
            }
        }
        return dobj;
    }
    // ����
    public DOBJ CALLupso_doc_fileup_DEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL8");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_doc_fileup_DEL8(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL8") ;
        rvobj.Println("DEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_doc_fileup_DEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        double   SEQ = dobj.getRetObject("S1").getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("S1").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_CONFIRM_DOC_ATTCH  \n";
        query +=" where MNG_NUM=:MNG_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ϱ��� ����
    public DOBJ CALLupso_doc_fileup_UPD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_doc_fileup_UPD9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_doc_fileup_UPD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   FILE_TYPE = dvobj.getRecord().get("FILE_TYPE");   //���� Ÿ��
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_CONFIRM_DOC_ATTCH  \n";
        query +=" set FILE_TYPE=:FILE_TYPE  \n";
        query +=" where MNG_NUM=:MNG_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("FILE_TYPE", FILE_TYPE);               //���� Ÿ��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        return sobj;
    }
    // ���Ϲ���
    public DOBJ CALLupso_doc_fileup_FUT1(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT1");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT1");
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
        dvobj.setName("FUT1") ;
        dvobj.Println("FUT1");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ϲ����̵�����
    public DOBJ CALLupso_doc_fileup_CVT2(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT2");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("R");        //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("CVT2");
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
        dvobj.setName("CVT2") ;
        dvobj.Println("CVT2");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLupso_doc_fileup_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String fullname="";
        dvobj.first();
        while(dvobj.next())
        {
            byte[] binFILES=wutil.inputFileStream("/home/jeus/komca_web/upload", dobj.getRetObject("S").getRecord().get("UNIFILENAME"));
            dvobj.getRecord().put("FILES", binFILES);
        }
        dvobj.Println("INS5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_doc_fileup_INS5(dobj, dvobj);
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
    private SQLObject SQLupso_doc_fileup_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FILE_TYPE = dvobj.getRecord().get("FILE_TYPE");   //���� Ÿ��
        byte[]   FILES = dvobj.getRecord().getBytes("FILES");   //����
        String   FILE_NM = dobj.getRetObject("R").getRecord().get("UPFILENAME");   //���ϸ�
        String   FILE_ROUT = dobj.getRetObject("CVT2").getRecord().get("DFILEPATH");   //���ϰ��
        double   FILE_SIZE = dobj.getRetObject("R").getRecord().getDouble("FILESIZE");   //���� ũ��
        String   FILE_TEMPNM = dobj.getRetObject("R").getRecord().get("UNIFILENAME");   //��ȯ ���ϸ�
        double   MNG_NUM = dobj.getRetObject("R").getRecord().getDouble("MNG_NUM");   //������ȣ
        double   SEQ = dobj.getRetObject("S1").getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("S1").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_CONFIRM_DOC_ATTCH (FILE_SIZE, MNG_NUM, FILE_TEMPNM, FILE_ROUT, UPSO_CD, FILE_TYPE, SEQ, FILE_NM, FILES)  \n";
        query +=" values(:FILE_SIZE , :MNG_NUM , :FILE_TEMPNM , :FILE_ROUT , :UPSO_CD , :FILE_TYPE , :SEQ , :FILE_NM , :FILES )";
        sobj.setSql(query);
        sobj.setString("FILE_TYPE", FILE_TYPE);               //���� Ÿ��
        sobj.setBlob("FILES", FILES);               //����
        sobj.setString("FILE_NM", FILE_NM);               //���ϸ�
        sobj.setString("FILE_ROUT", FILE_ROUT);               //���ϰ��
        sobj.setDouble("FILE_SIZE", FILE_SIZE);               //���� ũ��
        sobj.setString("FILE_TEMPNM", FILE_TEMPNM);               //��ȯ ���ϸ�
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$upso_doc_fileup
    //##**$$upso_visit_init
    /* * ���α׷��� : bra01_s09
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/25
    * ���� : ���ҹ湮 ��Ͻ� �ش� ������ �⺻������ ��ȸ�Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_visit_init(DOBJ dobj)
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
            dobj  = CALLupso_visit_init_SEL1(Conn, dobj);           //  ��������
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
    public DOBJ CTLupso_visit_init( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_visit_init_SEL1(Conn, dobj);           //  ��������
        return dobj;
    }
    // ��������
    // ������ �⺻������ ��ȸ�Ѵ�
    public DOBJ CALLupso_visit_init_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_visit_init_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_init_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.MNGEMSTR_NM  ,  XA.PERMMSTR_NM  ,  XA.STAFF_CD  ,  XC.HAN_NM  STAFF_NM  ,  XA.UPSO_PHON  ,  SUBSTR(XA.UPSO_NEW_ZIP,1,3)||'-'||SUBSTR(XA.UPSO_NEW_ZIP,4,3)  UPSO_ZIP  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  TRIM(XB.BSTYP_CD)  ||  XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XB.MONPRNCFEE  ,  XA.UPSO_CD  ,  XA.UPSO_NM  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  INSA.TINS_MST01  XC  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.STAFF_NUM(+)  =  XA.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$upso_visit_init
    //##**$$upso_sms_list
    /* * ���α׷��� : bra01_s09
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/30
    * ���� : ���Һ� SMS �߼۸���Ʈ�� ��ȸ�Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_sms_list(DOBJ dobj)
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
            dobj  = CALLupso_sms_list_SEL1(Conn, dobj);           //  ����Ʈ
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
    public DOBJ CTLupso_sms_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_sms_list_SEL1(Conn, dobj);           //  ����Ʈ
        return dobj;
    }
    // ����Ʈ
    // ���Һ� SMS �߼۸���Ʈ�� ��ȸ�Ѵ�.
    public DOBJ CALLupso_sms_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_sms_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_sms_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SEND_DATE  ,  DELIVER_DATE  ,  RESULT  ,  CODE_NM  ,  SMS_MSG  ,  DEST_NAME  ,  PHONE_NUMBER  FROM   \n";
        query +=" (SELECT  A.NOW_DATE  SEND_DATE  ,  B.DELIVER_DATE  ,  B.RESULT  ,  C.CODE_NM  ,  A.SMS_MSG  ,  B.DEST_NAME  ,  B.PHONE_NUMBER  FROM  KOMSMS.SDK_SMS_REPORT  A  ,  KOMSMS.SDK_SMS_REPORT_DETAIL  B  ,  FIDU.TENV_CODE  C  WHERE  A.USER_ID  =  :UPSO_CD   \n";
        query +=" AND  B.MSG_ID(+)  =  A.MSG_ID   \n";
        query +=" AND  C.HIGH_CD(+)  =  '00327'   \n";
        query +=" AND  C.CODE_CD(+)  =  RESULT   \n";
        query +=" AND  SUBSTR(A.NOW_DATE,0,8)  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  UNION   \n";
        query +=" SELECT  A.NOW_DATE  SEND_DATE  ,  B.DELIVER_DATE  ,  B.RESULT  ,  C.CODE_NM  ,  A.MMS_MSG  AS  SMS_MSG  ,  B.DEST_NAME  ,  B.PHONE_NUMBER  FROM  KOMSMS.SDK_MMS_REPORT  A  ,  KOMSMS.SDK_MMS_REPORT_DETAIL  B  ,  FIDU.TENV_CODE  C  WHERE  A.USER_ID  =  :UPSO_CD   \n";
        query +=" AND  B.MSG_ID(+)  =  A.MSG_ID   \n";
        query +=" AND  C.HIGH_CD(+)  =  '00327'   \n";
        query +=" AND  C.CODE_CD(+)  =  RESULT   \n";
        query +=" AND  SUBSTR(A.NOW_DATE,0,8)  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  )  ORDER  BY  SEND_DATE ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    //##**$$upso_sms_list
    //##**$$end
}
