
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_r02
{
    public bra01_r02()
    {
    }
    //##**$$satn_num_del
    /*
    */
    public DOBJ CTLsatn_num_del(DOBJ dobj)
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
            dobj  = CALLsatn_num_del_XIUD1(Conn, dobj);           //  ��/��� �����ȣ �ϰ� ����
            dobj  = CALLsatn_num_del_XIUD2(Conn, dobj);           //  ���� �����ȣ �ϰ� ����
            dobj  = CALLsatn_num_del_SEL6(Conn, dobj);           //  �����ȣ�ѱ��
            dobj  = CALLsatn_num_del_OBJ5(Conn, dobj);           //  �׷���� ���������� ����
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
    public DOBJ CTLsatn_num_del( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsatn_num_del_XIUD1(Conn, dobj);           //  ��/��� �����ȣ �ϰ� ����
        dobj  = CALLsatn_num_del_XIUD2(Conn, dobj);           //  ���� �����ȣ �ϰ� ����
        dobj  = CALLsatn_num_del_SEL6(Conn, dobj);           //  �����ȣ�ѱ��
        dobj  = CALLsatn_num_del_OBJ5(Conn, dobj);           //  �׷���� ���������� ����
        return dobj;
    }
    // ��/��� �����ȣ �ϰ� ����
    public DOBJ CALLsatn_num_del_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsatn_num_del_XIUD1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsatn_num_del_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   LNK_KEY = dobj.getRetObject("S").getRecord().get("LNK_KEY");   //���� Ű
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_CONFIRM_DOC  \n";
        query +=" SET SATN_NUM = ''  \n";
        query +=" WHERE SATN_NUM = :LNK_KEY";
        sobj.setSql(query);
        sobj.setString("LNK_KEY", LNK_KEY);               //���� Ű
        return sobj;
    }
    // ���� �����ȣ �ϰ� ����
    public DOBJ CALLsatn_num_del_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsatn_num_del_XIUD2(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsatn_num_del_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   LNK_KEY = dobj.getRetObject("S").getRecord().get("LNK_KEY");   //���� Ű
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_CLSED  \n";
        query +=" SET SATN_NUM = ''  \n";
        query +=" WHERE SATN_NUM = :LNK_KEY";
        sobj.setSql(query);
        sobj.setString("LNK_KEY", LNK_KEY);               //���� Ű
        return sobj;
    }
    // �����ȣ�ѱ��
    public DOBJ CALLsatn_num_del_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsatn_num_del_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsatn_num_del_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   LNK_KEY = dobj.getRetObject("S").getRecord().get("LNK_KEY");   //���� Ű
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :LNK_KEY  AS  LNK_KEY  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("LNK_KEY", LNK_KEY);               //���� Ű
        return sobj;
    }
    // �׷���� ���������� ����
    public DOBJ CALLsatn_num_del_OBJ5(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OBJ5");
        CommUtil cu = new CommUtil(dobj);
        HashMap   classinfo = new HashMap();
        classinfo.put("OBJECTID","OBJ5");
        classinfo.put("PACKAGE","komca.ga.adm");
        classinfo.put("CLASS","adm01_r01");
        classinfo.put("METHOD","satn_num_del");
        classinfo.put("INMAP","S:SEL6");
        classinfo.put("OUTOBJ","");
        classinfo.put("OUTOBJ1","");
        dobj = cu.callPSInternal(dobj, null, classinfo );
        dobj.getRetObject("OBJ5").Println("OBJ5");
        return dobj;
    }
    //##**$$satn_num_del
    //##**$$apprv_stat
    /*
    */
    public DOBJ CTLapprv_stat(DOBJ dobj)
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
            dobj  = CALLapprv_stat_MPD3(Conn, dobj);           //  �б�
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
    public DOBJ CTLapprv_stat( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLapprv_stat_MPD3(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLapprv_stat_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLapprv_stat_SEL2(Conn, dobj);           //  �ϰ����������ȸ
                if( dobj.getRetObject("SEL2").getRecordCnt() > 0)
                {
                    dobj  = CALLapprv_stat_ADD5( dobj);        //  ������¸���߰�
                }
            }
        }
        return dobj;
    }
    // �ϰ����������ȸ
    public DOBJ CALLapprv_stat_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLapprv_stat_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLapprv_stat_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GBN = dobj.getRetObject("R").getRecord().get("GBN");   //����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //������ȣ
        String   CLSED_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");   //�޾� ����
        String   SATN_NUM = dobj.getRetObject("R").getRecord().get("SATN_NUM");   //�����ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LNK_KEY  ,  DOC_STS  AS  STATUS_CD  ,  :UPSO_CD  AS  UPSO_CD  ,  :SEQ  AS  SEQ  ,  :CLSED_GBN  AS  CLSED_GBN  ,  :GBN  AS  GBN  FROM  TRGW.TEAG_APPDOC  WHERE  LNK_KEY  =  :SATN_NUM   \n";
        query +=" AND  DOC_STS  IN  ('20',  '90') ";
        sobj.setSql(query);
        sobj.setString("GBN", GBN);               //����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("CLSED_GBN", CLSED_GBN);               //�޾� ����
        sobj.setString("SATN_NUM", SATN_NUM);               //�����ȣ
        return sobj;
    }
    // ������¸���߰�
    public DOBJ CALLapprv_stat_ADD5(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("ADD5");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        VOBJ dvobj = dobj.getRetObject("SEL2");          //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        rvobj = wutil.getAddedVobj(dobj,"ADD5","", dvobj );
        rvobj.setName("ADD5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    //##**$$apprv_stat
    //##**$$sel_satn_list
    /*
    */
    public DOBJ CTLsel_satn_list(DOBJ dobj)
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
            dobj  = CALLsel_satn_list_SEL1(Conn, dobj);           //  ���縮��Ʈ��ȸ
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
    public DOBJ CTLsel_satn_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_satn_list_SEL1(Conn, dobj);           //  ���縮��Ʈ��ȸ
        return dobj;
    }
    // ���縮��Ʈ��ȸ
    public DOBJ CALLsel_satn_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_satn_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_satn_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   STAFF_NUM = dobj.getRetObject("S").getRecord().get("STAFF_NUM");   //�����ȣ
        String   CLSED_GBN = dobj.getRetObject("S").getRecord().get("CLSED_GBN");   //�޾� ����
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '0'  AS  CHK  ,  A.UPSO_CD  ,  A.SEQ  ,  B.UPSO_NM  ,  TO_CHAR(A.INS_DATE,  'YYYY-MM-DD')  AS  INS_DATE  ,  A.GBN  AS  CLSED_GBN  ,  DECODE(A.GBN,  '2',  TO_CHAR(TO_DATE(A.START_YRMN,  'YYYYMM'),  'YYYY/MM'),  '')  AS  CLSBS_YRMN  ,  ''  AS  CLSED_DAY  ,  DECODE(A.GBN,  '1',  NVL(TO_CHAR(TO_DATE(A.START_DAY,  'YYYYMMDD'),  'YYYY/MM/DD'),  TO_CHAR(TO_DATE(A.START_YRMN,  'YYYYMM'),  'YYYY/MM')),  '')  AS  START_DAY  ,  DECODE(A.GBN,  '1',  NVL(TO_CHAR(TO_DATE(A.END_DAY,  'YYYYMMDD'),  'YYYY/MM/DD'),  TO_CHAR(TO_DATE(A.END_YRMN,  'YYYYMM'),  'YYYY/MM')),  '')  AS  END_DAY  ,  A.SATN_NUM  ,  ''  AS  SATN_STAT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_CONFIRM_DOC_ATTCH  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  SEQ  =  A.SEQ   \n";
        query +=" AND  LENGTH(FILES)  >  0)  AS  ATTCH_CHK  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_CONFIRM_DOC_ATTCH  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  SEQ  =  A.SEQ)  AS  ATTCH_CNT  ,  B.STAFF_CD  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  AS  STAFF_NM  ,  'D'  AS  GBN  FROM  GIBU.TBRA_CONFIRM_DOC  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  B.STAFF_CD  =  NVL(:STAFF_NUM,  B.STAFF_CD)   \n";
        query +=" AND  A.GBN  =  NVL(:CLSED_GBN,  A.GBN)   \n";
        query +=" AND  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  UNION  ALL   \n";
        query +=" SELECT  '0'  AS  CHK  ,  TA.UPSO_CD  ,  1  AS  SEQ  ,  TA.UPSO_NM  ,  TO_CHAR(TA.INS_DATE,  'YYYY-MM-DD')  AS  INS_DATE  ,  '3'  AS  CLSED_GBN  ,  TO_CHAR(TO_DATE  (TA.START_YRMN,  'YYYYMM'),  'YYYY/MM')  AS  CLSBS_YRMN  ,  TA.START_YRMN  AS  CLSED_DAY  ,  ''  AS  START_YRMN  ,  ''  AS  END_YRMN  ,  TA.SATN_NUM  ,  ''  AS  SATN_STAT  ,  TO_NUMBER('')  AS  ATTCH_CHK  ,  TO_NUMBER('')  AS  ATTCH_CNT  ,  TA.STAFF_CD  ,  FIDU.GET_STAFF_NM(TA.STAFF_CD)  AS  STAFF_NM  ,  'C'  AS  GBN  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.UPSO_NM  ,  A.CLSBS_YRMN  ,  A.STAFF_CD  ,  B.START_YRMN  ,  B.INS_DATE  ,  B.SATN_NUM  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_UPSO_CLSED  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.CLSED_GBN  IN  ('02',  '03',  '04')   \n";
        query +=" AND  TO_CHAR(B.INS_DATE,  'YYYYMMDD')  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_NUM,  A.STAFF_CD)   \n";
        query +=" AND  (:CLSED_GBN  =  '3'   \n";
        query +=" OR  :CLSED_GBN  IS  NULL)  )  TA  ,  GIBU.TBRA_UPSO  XC  WHERE  XC.BEFORE_UPSO_CD  =  TA.UPSO_CD  ORDER  BY  STAFF_CD,  UPSO_CD,  SEQ ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("STAFF_NUM", STAFF_NUM);               //�����ȣ
        sobj.setString("CLSED_GBN", CLSED_GBN);               //�޾� ����
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    //##**$$sel_satn_list
    //##**$$apprv_all_satn
    /*
    */
    public DOBJ CTLapprv_all_satn(DOBJ dobj)
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
            dobj  = CALLapprv_all_satn_SEL1(Conn, dobj);           //  �ϰ������ȣȹ��
            dobj  = CALLapprv_all_satn_MPD3(Conn, dobj);           //  �б�
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
    public DOBJ CTLapprv_all_satn( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLapprv_all_satn_SEL1(Conn, dobj);           //  �ϰ������ȣȹ��
        dobj  = CALLapprv_all_satn_MPD3(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �ϰ������ȣȹ��
    public DOBJ CALLapprv_all_satn_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLapprv_all_satn_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLapprv_all_satn_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(SATN_NUM)  AS  SATN_NUM  FROM  (   \n";
        query +=" SELECT  TO_CHAR(SYSDATE,  'YYYYMMDD')  ||  '-'  ||  :STAFF_CD  ||  '-'  ||  LPAD(NVL(MAX(SUBSTR(SATN_NUM,  18,  3)),  0)  +  1,  3,  '0')  AS  SATN_NUM  FROM  GIBU.TBRA_CONFIRM_DOC  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.SATN_NUM  LIKE  TO_CHAR(SYSDATE,  'YYYYMMDD')  ||  '-'  ||  :STAFF_CD  ||  '%'  UNION  ALL   \n";
        query +=" SELECT  TO_CHAR(SYSDATE,  'YYYYMMDD')  ||  '-'  ||  :STAFF_CD  ||  '-'  ||  LPAD(NVL(MAX(SUBSTR(SATN_NUM,  18,  3)),  0)  +  1,  3,  '0')  AS  SATN_NUM  FROM  GIBU.TBRA_UPSO_CLSED  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.SATN_NUM  LIKE  TO_CHAR(SYSDATE,  'YYYYMMDD')  ||  '-'  ||  :STAFF_CD  ||  '%'  ) ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        return sobj;
    }
    // �б�
    public DOBJ CALLapprv_all_satn_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("GBN").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLapprv_all_satn_XIUD1(Conn, dobj);           //  �ϰ������ȣ����
            }
            else
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLapprv_all_satn_XIUD6(Conn, dobj);           //  �ϰ������ȣ����
            }
        }
        return dobj;
    }
    // �ϰ������ȣ����
    public DOBJ CALLapprv_all_satn_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLapprv_all_satn_XIUD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLapprv_all_satn_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SATN_NUM = dobj.getRetObject("SEL1").getRecord().get("SATN_NUM");   //�����ȣ
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_CONFIRM_DOC  \n";
        query +=" SET SATN_NUM = :SATN_NUM  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND SEQ = :SEQ";
        sobj.setSql(query);
        sobj.setString("SATN_NUM", SATN_NUM);               //�����ȣ
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �ϰ������ȣ����
    public DOBJ CALLapprv_all_satn_XIUD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD6");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLapprv_all_satn_XIUD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLapprv_all_satn_XIUD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLSED_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //�޾� ����
        String   SATN_NUM = dobj.getRetObject("SEL1").getRecord().get("SATN_NUM");   //�����ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_CLSED  \n";
        query +=" SET SATN_NUM = :SATN_NUM  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND START_YRMN = :CLSED_DAY";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY", CLSED_DAY);               //�޾� ����
        sobj.setString("SATN_NUM", SATN_NUM);               //�����ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$apprv_all_satn
    //##**$$end
}
