
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_test
{
    public bra04_test()
    {
    }
    //##**$$rept_bankbook_test
    /*
    */
    public DOBJ CTLrept_bankbook_test(DOBJ dobj)
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
            dobj  = CALLrept_bankbook_test_MIUD1(Conn, dobj);           //  �ο���� ó��
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
    public DOBJ CTLrept_bankbook_test( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_bankbook_test_MIUD1(Conn, dobj);           //  �ο���� ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �ο���� ó��
    public DOBJ CALLrept_bankbook_test_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLrept_bankbook_test_SEL1(Conn, dobj);           //  �Է�/���� ����
                dobj  = CALLrept_bankbook_test_SEL2(Conn, dobj);           //  �Ա� ������ ����
                if( dobj.getRetObject("SEL1").getRecord().get("CRUD").equals("U"))
                {
                    dobj  = CALLrept_bankbook_test_SEL3(Conn, dobj);           //  ���� �������� ����
                    dobj  = CALLrept_bankbook_test_SEL4(Conn, dobj);           //  �ܰ�ó�� ���� Ȯ��
                    if( dobj.getRetObject("SEL4").getRecord().get("END_YRMN").equals(""))
                    {
                        dobj  = CALLrept_bankbook_test_OSP1(Conn, dobj);           //  �ܰ�ó��
                        dobj  = CALLrept_bankbook_test_OSP2(Conn, dobj);           //  �ܰ�ó��
                    }
                    else
                    {
                        dobj  = CALLrept_bankbook_test_SEL5(Conn, dobj);           //  ���Ȯ��
                        if( dobj.getRetObject("SEL5").getRecord().get("JUDG_CD").equals("1"))
                        {
                            dobj  = CALLrept_bankbook_test_OSP3(Conn, dobj);           //  ��� ���� ����
                            dobj  = CALLrept_bankbook_test_OSP4(Conn, dobj);           //  ��� ���� �Ա�
                        }
                        else if( dobj.getRetObject("SEL5").getRecord().get("ACCU_DAY").equals(""))
                        {
                            dobj  = CALLrept_bankbook_test_OSP5(Conn, dobj);           //  ���� �Ա�
                            dobj  = CALLrept_bankbook_test_OSP6(Conn, dobj);           //  ���� �Ա�
                        }
                    }
                }
                else
                {
                    dobj  = CALLrept_bankbook_test_SEL6(Conn, dobj);           //  �ܰ�ó�� ���� Ȯ��
                    dobj  = CALLrept_bankbook_test_SEL7(Conn, dobj);           //  ���Ȯ��
                    if( dobj.getRetObject("SEL7").getRecord().get("JUDG_CD").equals("1"))
                    {
                        dobj  = CALLrept_bankbook_test_OSP8(Conn, dobj);           //  ��� ���� �Ա�
                    }
                    else if( dobj.getRetObject("SEL7").getRecord().get("ACCU_DAY").equals(""))
                    {
                        dobj  = CALLrept_bankbook_test_OSP9(Conn, dobj);           //  ���� �Ա�
                    }
                }
            }
        }
        return dobj;
    }
    // �Է�/���� ����
    // �Է� : �ű� �����ڵ� != NULL ���� : �ű� �����ڵ� == NULL ���� : �ű� �����ڵ� != ���� �����ڵ�
    public DOBJ CALLrept_bankbook_test_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_test_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_test_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(:UPSO_CD,  NULL,  'D',  DECODE(A.UPSO_CD,  NULL,  'I',  'U'))  AS  CRUD  FROM  GIBU.TBRA_REPT  A  WHERE  REPT_DAY  =  :REPT_DAY   \n";
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
    public DOBJ CALLrept_bankbook_test_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_test_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_test_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   COMIS = dobj.getRetObject("R").getRecord().getDouble("COMIS");   //������
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("RECV_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //����� ID
        String   BANK_CD = dobj.getRetObject("R").getRecord().get("BANK_CD");   //���� �ڵ�
        double   DEMD_MMCNT = dobj.getRetObject("R").getRecord().getDouble("DEMD_MMCNT");   //û��������
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   START_YRMN = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   NOTE_YRMN = dobj.getRetObject("R").getRecord().get("NOTE_YRMN");   //���� ���
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   REPT_AMT = dobj.getRetObject("R").getRecord().getDouble("REPT_AMT");   //�Ա� �ݾ�
        String   RECV_DAY = dobj.getRetObject("R").getRecord().get("RECV_DAY");   //���� ����
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_YRMN = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   ACCN_NUM = dobj.getRetObject("R").getRecord().get("ACCN_NUM");   //���� ��ȣ
        double   BALANCE = dobj.getRetObject("R").getRecord().getDouble("BALANCE");   //�ܾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :NOTE_YRMN  AS  NOTE_YRMN  ,  NULL  AS  DISTR_SEQ  ,  :BRAN_CD  AS  BRAN_CD  ,  :UPSO_CD  AS  UPSO_CD  ,  :DEMD_MMCNT  AS  DEMD_MMCNT  ,  SUBSTR(:START_YRMN,  1,  6)  AS  START_YRMN  ,  SUBSTR(:END_YRMN,  1,  6)  AS  END_YRMN  ,  :REPT_AMT  AS  REPT_AMT  ,  :COMIS  AS  COMIS  ,  :BALANCE  AS  BALANCE  ,  :RECV_DAY  AS  RECV_DAY  ,  :BANK_CD  AS  BANK_CD  ,  :ACCN_NUM  AS  ACCN_NUM  ,  NULL  AS  DISTR_GBN  ,  NULL  AS  REMAK  ,  :CRUD  AS  CRUD  ,  :INSPRES_ID  AS  INSPRES_ID  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setDouble("DEMD_MMCNT", DEMD_MMCNT);               //û��������
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //���� ���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setString("RECV_DAY", RECV_DAY);               //���� ����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        sobj.setDouble("BALANCE", BALANCE);               //�ܾ�
        return sobj;
    }
    // ���� �������� ����
    public DOBJ CALLrept_bankbook_test_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_test_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_test_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //����� ID
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  (   \n";
        query +=" SELECT  MAX(NOTE_YRMN)  FROM  TBRA_NOTE  WHERE  UPSO_CD  =  A.UPSO_CD  )  NOTE_YRMN  ,  A.BRAN_CD  AS  BRAN_CD  ,  A.UPSO_CD  AS  UPSO_CD  ,  NULL  AS  DISTR_SEQ  ,  NULL  AS  DEMD_MMCNT  ,  NULL  AS  START_YRMN  ,  NULL  AS  END_YRMN  ,  NULL  AS  REPT_AMT  ,  NULL  AS  COMIS  ,  NULL  AS  BALANCE  ,  NULL  AS  RECV_DAY  ,  NULL  AS  BANK_CD  ,  NULL  AS  ACCN_NUM  ,  NULL  AS  DISTR_GBN  ,  NULL  AS  REMAK  ,  'D'  AS  CRUD  ,  :INSPRES_ID  AS  INSPRES_ID  FROM  GIBU.TBRA_REPT  A  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �ܰ�ó�� ���� Ȯ��
    // �ش� �Ա������� �ܰ�� ó���� �������� �����Ѵ� �������� �����Ǿ� ���� ������ �ܰ�� ������
    public DOBJ CALLrept_bankbook_test_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_test_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_test_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :END_YRMN  AS  END_YRMN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //������
        return sobj;
    }
    // �ܰ�ó��
    // ���� ������ �ܰ� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_test_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL4");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","REPT_AMT","COMIS","RECV_DAY","BANK_CD","ACCN_NUM","REMAK","INSPRES_ID","CRUD"};
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
        String   spname ="GIBU.SP_PROC_BALANCE_INDTN";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
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
        dobj.setRetObject(robj);
        return dobj;
    }
    // �ܰ�ó��
    // �Աݱݾ��� �����ݾ׺��� ���� ��� �ܰ����̺� �����Ѵ�
    public DOBJ CALLrept_bankbook_test_OSP2(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP2");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","REPT_AMT","COMIS","RECV_DAY","BANK_CD","ACCN_NUM","REMAK","INSPRES_ID","CRUD"};
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
        String   spname ="GIBU.SP_PROC_BALANCE_INDTN";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
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
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���Ȯ��
    public DOBJ CALLrept_bankbook_test_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_test_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_test_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // ��� ���� ����
    // ���� ������ �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_test_OSP3(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP3");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL4");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP3");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","DISTR_GBN","REMAK","INSPRES_ID","CRUD"};
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
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
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
        robj.setName("OSP3");
        robj.Println("OSP3");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ��� ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_test_OSP4(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP4");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP4");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","DISTR_GBN","REMAK","INSPRES_ID","CRUD"};
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
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
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
    // ���� ������ �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_test_OSP5(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP5");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL4");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP5");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","DISTR_GBN","REMAK","INSPRES_ID","CRUD"};
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
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
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
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_test_OSP6(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP6");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP6");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","DISTR_GBN","REMAK","INSPRES_ID","CRUD"};
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
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
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
        robj.setName("OSP6");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �ܰ�ó�� ���� Ȯ��
    // �ش� �Ա������� �ܰ�� ó���� �������� �����Ѵ� �������� �����Ǿ� ���� ������ �ܰ�� ������
    public DOBJ CALLrept_bankbook_test_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_test_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_test_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :END_YRMN  AS  END_YRMN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //������
        return sobj;
    }
    // �ܰ�ó��
    // �Աݱݾ��� �����ݾ׺��� ���� ��� �ܰ����̺� �����Ѵ�
    public DOBJ CALLrept_bankbook_test_OSP7(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP7");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","REPT_AMT","COMIS","RECV_DAY","BANK_CD","ACCN_NUM","REMAK","INSPRES_ID","CRUD"};
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
        String   spname ="GIBU.SP_PROC_BALANCE_INDTN";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
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
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���Ȯ��
    public DOBJ CALLrept_bankbook_test_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_test_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_test_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
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
    public DOBJ CALLrept_bankbook_test_OSP8(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP8");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP8");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","DISTR_GBN","REMAK","INSPRES_ID","CRUD"};
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
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
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
    // ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_test_OSP9(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP9");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP9");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","REPT_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","DISTR_GBN","REMAK","INSPRES_ID","CRUD"};
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
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
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
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$rept_bankbook_test
    //##**$$end
}
