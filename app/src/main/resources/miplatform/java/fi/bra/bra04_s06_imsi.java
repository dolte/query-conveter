
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s06_imsi
{
    public bra04_s06_imsi()
    {
    }
    //##**$$rept_bankbook_insert_imsi
    /*
    */
    public DOBJ CTLrept_bankbook_insert_imsi(DOBJ dobj)
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
            ////
            String   TEMP1 ="0";         //�ӽ��÷�1
            dobj.setGVValue("TEMP1",TEMP1);
            dobj  = CALLrept_bankbook_insert_imsi_MIUD1(Conn, dobj);           //  �ο���� ó��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLrept_bankbook_insert_imsi_SEL10(Conn, dobj);           //  �������� ��ȸ
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
    public DOBJ CTLrept_bankbook_insert_imsi( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        ////
        String   TEMP1 ="0";         //�ӽ��÷�1
        dobj.setGVValue("TEMP1",TEMP1);
        dobj  = CALLrept_bankbook_insert_imsi_MIUD1(Conn, dobj);           //  �ο���� ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLrept_bankbook_insert_imsi_SEL10(Conn, dobj);           //  �������� ��ȸ
        return dobj;
    }
    // �ο���� ó��
    public DOBJ CALLrept_bankbook_insert_imsi_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrept_bankbook_insert_imsi_XIUD1(Conn, dobj);           //  �������� ����
                dobj  = CALLrept_bankbook_insert_imsi_SEL1(Conn, dobj);           //  �Է�/���� ����
                dobj  = CALLrept_bankbook_insert_imsi_SEL2(Conn, dobj);           //  �Ա� ������ ����
                if( dobj.getRetObject("SEL1").getRecord().get("CRUD").equals("U"))
                {
                    dobj  = CALLrept_bankbook_insert_imsi_SEL3(Conn, dobj);           //  ���� �������� ����
                    if( dobj.getRetObject("SEL3").getRecord().get("ACCU_GBN").equals("22"))
                    {
                        dobj  = CALLrept_bankbook_insert_imsi_OSP1(Conn, dobj);           //  ��� ���� ����
                        dobj  = CALLrept_bankbook_insert_imsi_SEL5(Conn, dobj);           //  �ű� ������ ���Ȯ��
                        if(!dobj.getRetObject("SEL5").getRecord().get("ACCU_DAY").equals(""))
                        {
                            dobj  = CALLrept_bankbook_insert_imsi_OSP4(Conn, dobj);           //  ��� ���� �Ա�
                        }
                        else
                        {
                            dobj  = CALLrept_bankbook_insert_imsi_OSP5(Conn, dobj);           //  ���� �Ա�
                        }
                    }
                    else
                    {
                        dobj  = CALLrept_bankbook_insert_imsi_OSP2(Conn, dobj);           //  ���� �Ա� ����
                        dobj  = CALLrept_bankbook_insert_imsi_SEL5(Conn, dobj);           //  �ű� ������ ���Ȯ��
                        if(!dobj.getRetObject("SEL5").getRecord().get("ACCU_DAY").equals(""))
                        {
                            dobj  = CALLrept_bankbook_insert_imsi_OSP4(Conn, dobj);           //  ��� ���� �Ա�
                        }
                        else
                        {
                            dobj  = CALLrept_bankbook_insert_imsi_OSP5(Conn, dobj);           //  ���� �Ա�
                        }
                    }
                }
                else if( dobj.getRetObject("SEL1").getRecord().get("CRUD").equals("I"))
                {
                    dobj  = CALLrept_bankbook_insert_imsi_SEL7(Conn, dobj);           //  ���Ȯ��
                    if(!dobj.getRetObject("SEL7").getRecord().get("ACCU_DAY").equals(""))
                    {
                        dobj  = CALLrept_bankbook_insert_imsi_OSP7(Conn, dobj);           //  ��� ���� �Ա�
                    }
                    else
                    {
                        dobj  = CALLrept_bankbook_insert_imsi_OSP8(Conn, dobj);           //  ���� �Ա�
                    }
                }
                else
                {
                    dobj  = CALLrept_bankbook_insert_imsi_SEL8(Conn, dobj);           //  ���Ȯ��
                    if( dobj.getRetObject("SEL8").getRecord().get("ACCU_GBN").equals("22"))
                    {
                        dobj  = CALLrept_bankbook_insert_imsi_OSP9(Conn, dobj);           //  ��� ����
                    }
                    else
                    {
                        dobj  = CALLrept_bankbook_insert_imsi_OSP10(Conn, dobj);           //  ���� �Ա� ����
                    }
                }
            }
        }
        return dobj;
    }
    // �������� ����
    public DOBJ CALLrept_bankbook_insert_imsi_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_bankbook_insert_imsi_XIUD1(dobj, dvobj);
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
    private SQLObject SQLrept_bankbook_insert_imsi_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE GIBU.TBRA_REPT_ERR  \n";
        query +=" WHERE REPT_DAY = :REPT_DAY  \n";
        query +=" AND REPT_GBN = :REPT_GBN  \n";
        query +=" AND BRAN_CD = :BRAN_CD  \n";
        query +=" AND	INSPRES_ID = :INSPRES_ID";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �Է�/���� ����
    // �Է� : �ű� �����ڵ� != NULL ���� : �ű� �����ڵ� == NULL ���� : �ű� �����ڵ� != ���� �����ڵ�
    public DOBJ CALLrept_bankbook_insert_imsi_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_imsi_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_imsi_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(:UPSO_CD,  NULL,  'D',  DECODE(A.UPSO_CD,  NULL,  'I',  'U'))  AS  CRUD  ,  A.UPSO_CD  BEFORE_UPSO_CD  ,  GIBU.FT_GET_LAST_REPT_YRMN(A.UPSO_CD,  6)  AS  LAST_YRMN  ,  :UPSO_CD  UPSO_CD  FROM  GIBU.TBRA_REPT  A  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Ա� ������ ����
    // ���ν����� ȣ���ϱ� ���� �Աݵ����͸� �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_imsi_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_imsi_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_imsi_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        double   COMIS = dobj.getRetObject("R").getRecord().getDouble("COMIS");   //������
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BEFORE_UPSO_CD = dobj.getRetObject("SEL1").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   BANK_CD = dobj.getRetObject("R").getRecord().get("BANK_CD");   //���� �ڵ�
        double   DEMD_MMCNT = dobj.getRetObject("R").getRecord().getDouble("DEMD_MMCNT");   //û��������
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   START_YRMN = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   CRUD = dobj.getRetObject("SEL1").getRecord().get("CRUD");   //CRUD
        String   MAPPING_DAY = dobj.getRetObject("R").getRecord().get("MAPPING_DAY");   //���� ����
        String   NOTE_YRMN = dobj.getRetObject("R").getRecord().get("NOTE_YRMN");   //���� ���
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   REPT_AMT = dobj.getRetObject("R").getRecord().getDouble("REPT_AMT");   //�Ա� �ݾ�
        double   NESS_AMT = dobj.getRetObject("R").getRecord().getDouble("NESS_AMT");   //�ʿ� �ݾ�
        String   RECV_DAY = dobj.getRetObject("R").getRecord().get("RECV_DAY");   //���� ����
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_YRMN = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   ACCN_NUM = dobj.getRetObject("R").getRecord().get("ACCN_NUM");   //���� ��ȣ
        double   BALANCE = dobj.getRetObject("R").getRecord().getDouble("BALANCE");   //�ܾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :NOTE_YRMN  AS  NOTE_YRMN  ,  NULL  AS  DISTR_SEQ  ,  :BRAN_CD  AS  BRAN_CD  ,  DECODE(:UPSO_CD,  NULL,  :BEFORE_UPSO_CD,  :UPSO_CD)  AS  UPSO_CD  ,  :DEMD_MMCNT  AS  DEMD_MMCNT  ,  SUBSTR(:START_YRMN,  1,  6)  AS  START_YRMN  ,  SUBSTR(:END_YRMN,  1,  6)  AS  END_YRMN  ,  :REPT_AMT  AS  REPT_AMT  ,  :COMIS  AS  COMIS  ,  :BALANCE  AS  BALANCE  ,  :RECV_DAY  AS  RECV_DAY  ,  :BANK_CD  AS  BANK_CD  ,  :ACCN_NUM  AS  ACCN_NUM  ,  :MAPPING_DAY  AS  MAPPING_DAY  ,  NULL  AS  DISTR_GBN  ,  NULL  AS  REMAK  ,  NULL  AS  CLAIM_GBN  ,  :CRUD  AS  CRUD  ,  :INSPRES_ID  AS  INSPRES_ID  ,  :NESS_AMT  AS  NESS_AMT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setDouble("DEMD_MMCNT", DEMD_MMCNT);               //û��������
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("CRUD", CRUD);               //CRUD
        sobj.setString("MAPPING_DAY", MAPPING_DAY);               //���� ����
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //���� ���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setDouble("NESS_AMT", NESS_AMT);               //�ʿ� �ݾ�
        sobj.setString("RECV_DAY", RECV_DAY);               //���� ����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        sobj.setDouble("BALANCE", BALANCE);               //�ܾ�
        return sobj;
    }
    // ���� �������� ����
    public DOBJ CALLrept_bankbook_insert_imsi_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_imsi_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_imsi_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   BEFORE_UPSO_CD = dobj.getRetObject("SEL1").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        double   NESS_AMT = dobj.getRetObject("R").getRecord().getDouble("NESS_AMT");   //�ʿ� �ݾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  (   \n";
        query +=" SELECT  MAX(NOTE_YRMN)  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  A.UPSO_CD  )  NOTE_YRMN  ,  A.BRAN_CD  AS  BRAN_CD  ,  A.UPSO_CD  AS  UPSO_CD  ,  NULL  AS  DISTR_SEQ  ,  NULL  AS  DEMD_MMCNT  ,  NULL  AS  START_YRMN  ,  NULL  AS  END_YRMN  ,  NULL  AS  REPT_AMT  ,  NULL  AS  COMIS  ,  NULL  AS  BALANCE  ,  NULL  AS  RECV_DAY  ,  NULL  AS  BANK_CD  ,  NULL  AS  ACCN_NUM  ,  NULL  AS  DISTR_GBN  ,  NULL  AS  CLAIM_GBN  ,  NULL  AS  REMAK  ,  'D'  AS  CRUD  ,  :INSPRES_ID  AS  INSPRES_ID  ,  (   \n";
        query +=" SELECT  MAX(ACCU_GBN)  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :BEFORE_UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  ACCU_GBN  ,  :NESS_AMT  AS  NESS_AMT  FROM  GIBU.TBRA_REPT  A  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setDouble("NESS_AMT", NESS_AMT);               //�ʿ� �ݾ�
        return sobj;
    }
    // ��� ���� ����
    // ���� ������ �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_imsi_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL3");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP1");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN_ACCU";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP1");
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �ű� ������ ���Ȯ��
    public DOBJ CALLrept_bankbook_insert_imsi_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_imsi_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_imsi_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  JUDG_CD  ,  SOL_START_YRMN  ,  SOL_END_YRMN  ,  SOL_ORG_AMT  ,  SOL_ADDT_AMT  ,  (   \n";
        query +=" SELECT  COUNT(YRMN)  FROM  GIBU.COPY_YRMN  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  A.SOL_START_YRMN   \n";
        query +=" AND  A.SOL_END_YRMN  )  DEMD_MMCNT  FROM  GIBU.TBRA_ACCU  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��� ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_imsi_OSP4(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP4");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP4");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CRUD ="I";         //CRUD
            record.put("CRUD",CRUD);
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN_ACCU";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP4");
        robj.Println("OSP4");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_imsi_OSP5(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP5");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP5");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CRUD ="I";         //CRUD
            record.put("CRUD",CRUD);
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP5");
        robj.Println("OSP5");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �Ա� ����
    // ���� ������ �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_imsi_OSP2(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP2");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL3");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP2");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP2");
        robj.Println("OSP2");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���Ȯ��
    public DOBJ CALLrept_bankbook_insert_imsi_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_imsi_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        rvobj.Println("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_imsi_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  JUDG_CD  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��� ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_imsi_OSP7(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP7");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP7");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN_ACCU";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP7");
        robj.Println("OSP7");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_imsi_OSP8(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP8");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP8");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP8");
        robj.Println("OSP8");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���Ȯ��
    public DOBJ CALLrept_bankbook_insert_imsi_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_imsi_SEL8(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        rvobj.Println("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_imsi_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   UPSO_CD = dobj.getRetObject("SEL1").getRecord().get("BEFORE_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_GBN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��� ����
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_imsi_OSP9(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP9");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP9");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN_ACCU";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP9");
        robj.Println("OSP9");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �Ա� ����
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_insert_imsi_OSP10(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP10");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP10");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP10");
        robj.Println("OSP10");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �������� ��ȸ
    public DOBJ CALLrept_bankbook_insert_imsi_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_insert_imsi_SEL10(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.setRetcode(1);
        rvobj.Println("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_insert_imsi_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("S").firstRecord().get("REPT_GBN");   //�Ա� ����
        String   CNT_INST = dobj.getGVString("TEMP1");   //CNT_INST
        String   BRAN_CD = dobj.getRetObject("S").firstRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  UPSO_CD  ,  START_YRMN  ,  END_YRMN  ,  REPT_AMT  ,  COMIS  ,  RECV_DAY  ,  ACCN_NUM  ,  ERR_GBN  ,  ERR_CTENT  FROM  GIBU.TBRA_REPT_ERR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  '1'  =  DECODE(:CNT_INST,  1,  NULL,  '1') ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("CNT_INST", CNT_INST);               //CNT_INST
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$rept_bankbook_insert_imsi
    //##**$$end
}
