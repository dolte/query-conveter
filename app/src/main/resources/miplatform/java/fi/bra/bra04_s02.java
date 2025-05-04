
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s02
{
    public bra04_s02()
    {
    }
    //##**$$rept_closing
    /* * ���α׷��� : bra04_s02
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/11
    * ����    : �Աݿ��� �Աݸ��� ������ Ȯ���Ѵ�.
    Input
    BRAN_CD (���� �ڵ�)
    REPT_DAY (�Ա�����)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLrept_closing(DOBJ dobj)
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
            dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  ���κ�����üũ
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
    public DOBJ CTLrept_closing( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  ���κ�����üũ
        return dobj;
    }
    // ���κ�����üũ
    // ���κ� �������̺��� �ش� ��� ������ �ִ��� üũ�Ѵ�
    public DOBJ CALLrept_closing_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_closing_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        String message ="�ش�Ⱓ�� ���������� �����մϴ�. ���������� Ȯ���ϼ���.";
        dobj.setRetmsg(message);
        return dobj;
    }
    private SQLObject SQLrept_closing_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(:REPT_DAY,1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(:REPT_DAY,5,2)  =  END_MON ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա� ����
        return sobj;
    }
    //##**$$rept_closing
    //##**$$rept_auto_insert
    /* * ���α׷��� : bra04_s02
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/26
    * ����    : �ڵ���ü �Աݳ����� �����Ѵ�.
    1) �ݰ������ ���۵Ǵ� �ڵ���ü ��� ������ '������ �߻���' ������ ���۵�
    2) ������ �߻��� ������ ���� �˻� �� ���� ���� (����ȣ�� û�����۳��, û�������� ������ �̿��Ͽ� ������)
    3) û�� ��Ͽ��� ������ �߻����� ���� �׸��� ��ȸ�Ͽ� �Ա����̺� (TBRA_REPT, TBRA_REPT_AUTO) �� ����
    4) ���� ���� �� �Ա�û�� ���� ���� ����
    5) �Ա� ��� ��ȸ / ���� ��� ��ȸ �� ����
    MODIFY : 2010.07.07  �ǳ���
    - �����ڵ带 ���� ����� 7�ڸ� �ڵ忡�� '004'�� �Էµǵ��� ���� ����
    Input
    ACCN_NUM (���� ��ȣ)
    ACCN_NUM (���� ��ȣ)
    AUTO_GBN (AUTO_GBN)
    BANK_CD (���� �ڵ�)
    CLIENT_NUM (�� ��ȣ)
    INSPRES_ID (����� ID)
    RECPT_CD (����ó �ڵ�)
    RECV_BANK_CD (RECV_BANK_CD)
    RECV_DAY (���� ����)
    REMAK (���)
    REPT_AMT (�Ա� �ݾ�)
    REPT_DAY (�Ա�����)
    REPT_YRMN (�Ա� ���)
    RESI_NUM (�ֹ� ��ȣ)
    SEQ_NUM (�Ϸ� ��ȣ)
    TRNF_RSLT (��ü ���)
    * ������ : 2010.03.02
    * ������ :
    * �������� SEL4, SEL5: �����߰�: ����, �����ڵ��
    */
    public DOBJ CTLrept_auto_insert(DOBJ dobj)
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
            dobj  = CALLrept_auto_insert_SEL1(Conn, dobj);           //  �Աݸ���Ȯ��
            if( dobj.getRetObject("SEL1").getRecord().get("BRANEND").equals("0"))
            {
                dobj  = CALLrept_auto_insert_MPD11(Conn, dobj);           //  �Ա����� ó��
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLrept_auto_insert_SEL17(Conn, dobj);           //  OSP �ڷ����
                dobj  = CALLrept_auto_insert_DEL18(Conn, dobj);           //  �������� ����
                dobj  = CALLrept_auto_insert_OSP1(Conn, dobj);           //  �ڵ���ü �Ա�
                dobj  = CALLrept_auto_insert_SEL2(Conn, dobj);           //  �ڵ���ü �Ա� ��� �˻�
                dobj  = CALLrept_auto_insert_SEL3(Conn, dobj);           //  �ڵ���ü �Ա� ��� ��ȸ
                dobj  = CALLrept_auto_insert_SEL4(Conn, dobj);           //  �ڵ���ü�Աݿ��� ����(�ݰ��)
                dobj  = CALLrept_auto_insert_SEL5(Conn, dobj);           //  �ڵ���ü�Աݿ���(����)
            }
            else
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="�ش� ����� �Աݸ����� �ֽ��ϴ�.";
                    dobj.setRetmsg(message);
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
    public DOBJ CTLrept_auto_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_auto_insert_SEL1(Conn, dobj);           //  �Աݸ���Ȯ��
        if( dobj.getRetObject("SEL1").getRecord().get("BRANEND").equals("0"))
        {
            dobj  = CALLrept_auto_insert_MPD11(Conn, dobj);           //  �Ա����� ó��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLrept_auto_insert_SEL17(Conn, dobj);           //  OSP �ڷ����
            dobj  = CALLrept_auto_insert_DEL18(Conn, dobj);           //  �������� ����
            dobj  = CALLrept_auto_insert_OSP1(Conn, dobj);           //  �ڵ���ü �Ա�
            dobj  = CALLrept_auto_insert_SEL2(Conn, dobj);           //  �ڵ���ü �Ա� ��� �˻�
            dobj  = CALLrept_auto_insert_SEL3(Conn, dobj);           //  �ڵ���ü �Ա� ��� ��ȸ
            dobj  = CALLrept_auto_insert_SEL4(Conn, dobj);           //  �ڵ���ü�Աݿ��� ����(�ݰ��)
            dobj  = CALLrept_auto_insert_SEL5(Conn, dobj);           //  �ڵ���ü�Աݿ���(����)
        }
        else
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="�ش� ����� �Աݸ����� �ֽ��ϴ�.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // �Աݸ���Ȯ��
    // ��û�� �Աݳ���� �Աݸ����� �ִ� ��� ���� ó��
    public DOBJ CALLrept_auto_insert_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_auto_insert_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //�Ա� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  BRANEND  FROM  GIBU.TBRA_BRANEND  WHERE  END_YEAR  =  SUBSTR(:REPT_YRMN,  1,  4)   \n";
        query +=" AND  END_MON  =  SUBSTR(:REPT_YRMN,  5,  2) ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        return sobj;
    }
    // �Ա����� ó��
    // ���ڵ� ������ �Ա������� ó���Ѵ�
    public DOBJ CALLrept_auto_insert_MPD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD11");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MPD11");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("AUTO_GBN").equals("22"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrept_auto_insert_XIUD13(Conn, dobj);           //  ��ü ��� ����
            }
        }
        return dobj;
    }
    // ��ü ��� ����
    // û�� ������ ���� ��ü����� �����Ѵ�
    public DOBJ CALLrept_auto_insert_XIUD13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD13");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_auto_insert_XIUD13(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert_XIUD13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLIENT_NUM = dobj.getRetObject("R").getRecord().get("CLIENT_NUM");   //�� ��ȣ
        String   DEMD_YRMN = dobj.getRetObject("R").getRecord().get("REPT_YRMN");   //û�� ���
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   TRNF_RSLT = dobj.getRetObject("R").getRecord().get("TRNF_RSLT");   //��ü ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_AUTO  \n";
        query +=" SET TRNF_RSLT = :TRNF_RSLT , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE	DEMD_YRMN = :DEMD_YRMN  \n";
        query +=" AND UPSO_CD = ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE CLIENT_NUM = :CLIENT_NUM )";
        sobj.setSql(query);
        sobj.setString("CLIENT_NUM", CLIENT_NUM);               //�� ��ȣ
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("TRNF_RSLT", TRNF_RSLT);               //��ü ���
        return sobj;
    }
    // OSP �ڷ����
    // MODIFY : 2010.07.07  �ǳ��� - �����ڵ带 ���� ����� 7�ڸ� �ڵ忡�� '004'�� �Էµǵ��� ���� ����
    public DOBJ CALLrept_auto_insert_SEL17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL17");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL17");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_auto_insert_SEL17(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL17");
        rvobj.Println("SEL17");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert_SEL17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   INSPRES_ID = dobj.getRetObject("S").firstRecord().get("INSPRES_ID");   //����� ID
        String   BANK_CD = dobj.getRetObject("S1").getRecord().get("BANK_CD");   //���� �ڵ�
        String   DEMD_YRMN = dobj.getRetObject("S").firstRecord().get("REPT_YRMN");   //û�� ���
        String   RECV_DAY = dobj.getRetObject("S1").getRecord().get("RECV_DAY");   //���� ����
        String   PROC_GBN = dobj.getRetObject("S").getRecord().get("PROC_GBN");   //�ڵ� ó�� ����
        String   ACCN_NUM = dobj.getRetObject("S1").getRecord().get("ACCN_NUM");   //���� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_DAY  AS  REPT_DAY  ,  '01'  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  50  AS  COMIS  ,  :RECV_DAY  AS  RECV_DAY  ,  SUBSTR(:BANK_CD,  1,  3)  AS  BANK_CD  ,  (   \n";
        query +=" SELECT  ACCN_NUM  FROM  ACCT.TCAC_ACCOUNT  WHERE  REPLACE(ACCN_NUM,  '-',  '')  LIKE  :ACCN_NUM  )  AS  ACCN_NUM  ,  :INSPRES_ID  AS  INSPRES_ID  ,  :PROC_GBN  AS  PRO_GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("RECV_DAY", RECV_DAY);               //���� ����
        sobj.setString("PROC_GBN", PROC_GBN);               //�ڵ� ó�� ����
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        return sobj;
    }
    // �������� ����
    public DOBJ CALLrept_auto_insert_DEL18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL18");
        VOBJ dvobj = dobj.getRetObject("SEL17");           //OSP �ڷ�������� ������Ų OBJECT�Դϴ�.(CALLrept_auto_insert_SEL17)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_auto_insert_DEL18(dobj, dvobj);
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
    private SQLObject SQLrept_auto_insert_DEL18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_ERR  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // �ڵ���ü �Ա�
    // ���Һ��� �ڵ���ü �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_auto_insert_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL17");         //OSP �ڷ�������� ������Ų OBJECT�Դϴ�.(CALLrept_auto_insert_SEL17)
        dvobj.Println("OSP1");
        String[]  incolumns ={"REPT_DAY","DEMD_YRMN","COMIS","RECV_DAY","BANK_CD","ACCN_NUM","INSPRES_ID"};
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
        String   spname ="GIBU.SP_PROC_REPT_AUTO";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"READ_CNT","INST_CNT","ERR_CNT"};;
        int[]    outtypes ={12, 12, 12};;
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
        robj.setRetcode(1);
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �ڵ���ü �Ա� ��� �˻�
    // ���� �Ǽ�, ó�� �Ǽ�, ���� �Ǽ��� ��ȸ�Ѵ�
    public DOBJ CALLrept_auto_insert_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_auto_insert_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   ERR_CNT = dobj.getRetObject("OSP1").getRecord().getDouble("ERR_CNT");   //���� �Ǽ�
        double   INST_CNT = dobj.getRetObject("OSP1").getRecord().getDouble("INST_CNT");   //�Է�ī��Ʈ
        double   READ_CNT = dobj.getRetObject("OSP1").getRecord().getDouble("READ_CNT");   //���� �Ǽ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :READ_CNT  AS  READ_CNT  ,  :INST_CNT  AS  INST_CNT  ,  :ERR_CNT  AS  ERR_CNT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setDouble("ERR_CNT", ERR_CNT);               //���� �Ǽ�
        sobj.setDouble("INST_CNT", INST_CNT);               //�Է�ī��Ʈ
        sobj.setDouble("READ_CNT", READ_CNT);               //���� �Ǽ�
        return sobj;
    }
    // �ڵ���ü �Ա� ��� ��ȸ
    // �ڵ���ü �Ա� ��� ����Ʈ�� ��ȸ�Ѵ�
    public DOBJ CALLrept_auto_insert_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_auto_insert_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").firstRecord().get("REPT_YRMN");   //�Ա� ���
        String   PROC_GBN = dobj.getRetObject("S").firstRecord().get("PROC_GBN");   //�ڵ� ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  XA.START_YRMN  ||  '01'  START_YRMN  ,  XA.END_YRMN  ||  '01'  END_YRMN  ,  XC.GRAD  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  XA.DEMD_GBN  ,  '1'  PRINT_YN  ,  XA.TRNF_RSLT  ,  TO_CHAR(XA.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  GIBU.TBRA_UPSO  XB  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  APPL_DAY  <=  :REPT_YRMN  ||  '32'  GROUP  BY  UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.BSTYP_CD  =  C.BSTYP_CD   \n";
        query +=" AND  A.UPSO_GRAD  =  C.GRAD_GBN  )  XC  ,  INSA.TCPM_DEPT  XD  WHERE  XA.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  XA.DEMD_YRMN  IN   \n";
        query +=" (SELECT  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  XA.UPSO_CD)   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NULL   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XB.BRAN_CD   \n";
        query +=" AND  XA.PROC_GBN  =  :PROC_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        sobj.setString("PROC_GBN", PROC_GBN);               //�ڵ� ó�� ����
        return sobj;
    }
    // �ڵ���ü�Աݿ��� ����(�ݰ��)
    // �ڵ���ü�Աݿ��� ����(�ݰ��)�� ��ȸ�Ѵ�
    public DOBJ CALLrept_auto_insert_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_auto_insert_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").firstRecord().get("REPT_YRMN");   //�Ա� ���
        String   PROC_GBN = dobj.getRetObject("S").firstRecord().get("PROC_GBN");   //�ڵ� ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.BRAN_CD  ,  XC.DEPT_NM  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.TRNF_RSLT  ,  XD.CODE_NM  ,  TO_CHAR(XA.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_DEPT  XC  ,  FIDU.TENV_CODE  XD  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_DEMD_AUTO  B  WHERE  B.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  B.TRNF_RSLT  IS  NOT  NULL   \n";
        query +=" AND  A.APPL_DAY  <=  :REPT_YRMN  ||  '32'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  C.GRAD_GBN  =  A.UPSO_GRAD   \n";
        query +=" AND  C.BSTYP_CD  =  A.BSTYP_CD  )  XE  WHERE  XA.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NOT  NULL   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GIBU  =  XA.BRAN_CD   \n";
        query +=" AND  XD.HIGH_CD  =  '00206'   \n";
        query +=" AND  XD.CODE_CD  =  XA.TRNF_RSLT   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XA.PROC_GBN  =  :PROC_GBN  ORDER  BY  XB.BRAN_CD,  XB.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        sobj.setString("PROC_GBN", PROC_GBN);               //�ڵ� ó�� ����
        return sobj;
    }
    // �ڵ���ü�Աݿ���(����)
    // �ڵ���ü�Աݿ���(����)�� ��ȸ�Ѵ�
    public DOBJ CALLrept_auto_insert_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_auto_insert_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_YRMN = dobj.getRetObject("S").firstRecord().get("REPT_YRMN");   //�Ա� ���
        String   REPT_GBN ="01";   //�Ա� ����
        String   PROC_GBN = dobj.getRetObject("S").firstRecord().get("PROC_GBN");   //�ڵ� ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XC.UPSO_NM  ,  XB.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XC.MCHNDAESU  ,  XB.MONPRNCFEE  ,  XA.REPT_AMT  ,  XB.TOT_ADDT_AMT  ,  XB.TOT_EADDT_AMT  ,  XB.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.ERR_GBN  ,  XA.ERR_CTENT  ,  TO_CHAR(XB.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_REPT_ERR  XA  ,  GIBU.TBRA_DEMD_AUTO  XB  ,  GIBU.TBRA_UPSO  XC  ,  INSA.TCPM_DEPT  XD  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_REPT_ERR  B  WHERE  B.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  B.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  A.APPL_DAY  <=  :REPT_YRMN  ||  '32'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.UPSO_GRAD  =  C.GRAD_GBN   \n";
        query +=" AND  C.BSTYP_CD  =  A.BSTYP_CD  )  XE  WHERE  XA.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.START_YRMN  =  XA.START_YRMN   \n";
        query +=" AND  XB.END_YRMN  =  XA.END_YRMN   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XC.BRAN_CD   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.PROC_GBN  =  :PROC_GBN  ORDER  BY  XC.BRAN_CD,  XC.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("PROC_GBN", PROC_GBN);               //�ڵ� ó�� ����
        return sobj;
    }
    //##**$$rept_auto_insert
    //##**$$rept_auto_select
    /*
    */
    public DOBJ CTLrept_auto_select(DOBJ dobj)
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
            dobj  = CALLrept_auto_select_SEL3(Conn, dobj);           //  �׸� �ʱ�ȭ
            dobj  = CALLrept_auto_select_SEL1(Conn, dobj);           //  �ڵ���ü �Ա� ��� ��ȸ
            dobj  = CALLrept_auto_select_SEL4(Conn, dobj);           //  �ڵ���ü �Ա� ���� ����(�ݰ��)
            dobj  = CALLrept_auto_select_SEL5(Conn, dobj);           //  �ڵ���ü �Ա� ���� ����(�������)
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
    public DOBJ CTLrept_auto_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_auto_select_SEL3(Conn, dobj);           //  �׸� �ʱ�ȭ
        dobj  = CALLrept_auto_select_SEL1(Conn, dobj);           //  �ڵ���ü �Ա� ��� ��ȸ
        dobj  = CALLrept_auto_select_SEL4(Conn, dobj);           //  �ڵ���ü �Ա� ���� ����(�ݰ��)
        dobj  = CALLrept_auto_select_SEL5(Conn, dobj);           //  �ڵ���ü �Ա� ���� ����(�������)
        return dobj;
    }
    // �׸� �ʱ�ȭ
    public DOBJ CALLrept_auto_select_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_auto_select_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_select_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUBSTR(:REPT_DAY,  1,  6)  REPT_YRMN  ,  :REPT_DAY  AS  REPT_DAY  ,  '01'  AS  REPT_GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // �ڵ���ü �Ա� ��� ��ȸ
    // �ڵ���ü �Ա� ��� ����Ʈ�� ��ȸ�Ѵ�
    public DOBJ CALLrept_auto_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_auto_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("SEL3").getRecord().get("REPT_YRMN");   //�Ա� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  XA.START_YRMN  ||  '01'  START_YRMN  ,  XA.END_YRMN  ||  '01'  END_YRMN  ,  XA.BSTYP_CD  ||  XA.UPSO_GRAD  GRAD  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  XA.DEMD_GBN  ,  '1'  PRINT_YN  ,  XA.TRNF_RSLT  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_DEPT  XD  WHERE  XA.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  XA.DEMD_YRMN  IN   \n";
        query +=" (SELECT  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  XA.UPSO_CD)   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NULL   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XB.BRAN_CD   \n";
        query +=" AND  XA.DEMD_MMCNT  >  0 ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        return sobj;
    }
    // �ڵ���ü �Ա� ���� ����(�ݰ��)
    // �ڵ���ü �Ա� ���� ������ ��ȸ�Ѵ�.  ���� ������ �ݰ������ �߻��� ���� ������ ���� ó�� �� �߻��� ���� ���� ��θ� ��ȸ�Ѵ�
    public DOBJ CALLrept_auto_select_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_auto_select_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.setRetcode(1);
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_select_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("SEL3").getRecord().get("REPT_YRMN");   //�Ա� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.BRAN_CD  ,  XC.DEPT_NM  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.TRNF_RSLT  ,  XD.CODE_NM  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_DEPT  XC  ,  FIDU.TENV_CODE  XD  ,  GIBU.TBRA_BSTYPGRAD  XE  WHERE  XA.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NOT  NULL   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GIBU  =  XA.BRAN_CD   \n";
        query +=" AND  XD.HIGH_CD  =  '00206'   \n";
        query +=" AND  XD.CODE_CD  =  XA.TRNF_RSLT   \n";
        query +=" AND  XE.BSTYP_CD  =  XA.BSTYP_CD   \n";
        query +=" AND  XE.GRAD_GBN  =  XA.UPSO_GRAD  ORDER  BY  XB.BRAN_CD,  XB.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        return sobj;
    }
    // �ڵ���ü �Ա� ���� ����(�������)
    // �ڵ���ü �Ա� ���� ����(�������)
    public DOBJ CALLrept_auto_select_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_auto_select_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_select_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("SEL3").getRecord().get("REPT_YRMN");   //�Ա� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XC.UPSO_NM  ,  XB.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XC.MCHNDAESU  ,  XB.MONPRNCFEE  ,  XA.REPT_AMT  ,  XB.TOT_ADDT_AMT  ,  XB.TOT_EADDT_AMT  ,  XB.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.ERR_GBN  ,  XA.ERR_CTENT  FROM  GIBU.TBRA_REPT_ERR  XA  ,  GIBU.TBRA_DEMD_AUTO  XB  ,  GIBU.TBRA_UPSO  XC  ,  INSA.TCPM_DEPT  XD  ,  GIBU.TBRA_BSTYPGRAD  XE  WHERE  XA.REPT_DAY  BETWEEN  :REPT_YRMN  ||  '01'   \n";
        query +=" AND  :REPT_YRMN  ||  '31'   \n";
        query +=" AND  XA.REPT_GBN  =  '01'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.START_YRMN  =  XA.START_YRMN   \n";
        query +=" AND  XB.END_YRMN  =  XA.END_YRMN   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XC.BRAN_CD   \n";
        query +=" AND  XE.BSTYP_CD  =  XB.BSTYP_CD   \n";
        query +=" AND  XE.GRAD_GBN  =  XB.UPSO_GRAD  ORDER  BY  XC.BRAN_CD,  XC.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        return sobj;
    }
    //##**$$rept_auto_select
    //##**$$rept_auto_report
    /*
    */
    public DOBJ CTLrept_auto_report(DOBJ dobj)
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
            dobj  = CALLrept_auto_report_SEL1(Conn, dobj);           //  �ڵ���ü�Աݿ��� ����(�ݰ��)
            dobj  = CALLrept_auto_report_SEL2(Conn, dobj);           //  �ڵ���ü�Աݿ���(����)
            dobj  = CALLrept_auto_report_SEL3(Conn, dobj);           //  �ڵ���ü �Ա� ��� ��ȸ
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
    public DOBJ CTLrept_auto_report( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_auto_report_SEL1(Conn, dobj);           //  �ڵ���ü�Աݿ��� ����(�ݰ��)
        dobj  = CALLrept_auto_report_SEL2(Conn, dobj);           //  �ڵ���ü�Աݿ���(����)
        dobj  = CALLrept_auto_report_SEL3(Conn, dobj);           //  �ڵ���ü �Ա� ��� ��ȸ
        return dobj;
    }
    // �ڵ���ü�Աݿ��� ����(�ݰ��)
    // �ڵ���ü�Աݿ��� ����(�ݰ��)�� ��ȸ�Ѵ�
    public DOBJ CALLrept_auto_report_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_auto_report_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_report_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //�Ա� ���
        String   PROC_GBN = dobj.getRetObject("S").getRecord().get("PROC_GBN");   //�ڵ� ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.BRAN_CD  ,  XC.DEPT_NM  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.TRNF_RSLT  ,  XD.CODE_NM  ,  TO_CHAR(XA.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_DEPT  XC  ,  FIDU.TENV_CODE  XD  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_DEMD_AUTO  B  WHERE  B.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  B.TRNF_RSLT  IS  NOT  NULL   \n";
        query +=" AND  A.APPL_DAY  <=  :REPT_YRMN  ||  '32'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  C.GRAD_GBN  =  A.UPSO_GRAD   \n";
        query +=" AND  C.BSTYP_CD  =  A.BSTYP_CD  )  XE  WHERE  XA.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NOT  NULL   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GIBU  =  XA.BRAN_CD   \n";
        query +=" AND  XD.HIGH_CD  =  '00206'   \n";
        query +=" AND  XD.CODE_CD  =  XA.TRNF_RSLT   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XA.PROC_GBN  =  :PROC_GBN  ORDER  BY  XB.BRAN_CD,  XB.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        sobj.setString("PROC_GBN", PROC_GBN);               //�ڵ� ó�� ����
        return sobj;
    }
    // �ڵ���ü�Աݿ���(����)
    // �ڵ���ü�Աݿ���(����)�� ��ȸ�Ѵ�
    public DOBJ CALLrept_auto_report_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_auto_report_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_report_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //�Ա� ���
        String   REPT_GBN ="01";   //�Ա� ����
        String   PROC_GBN = dobj.getRetObject("S").getRecord().get("PROC_GBN");   //�ڵ� ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XC.UPSO_NM  ,  XB.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XC.MCHNDAESU  ,  XB.MONPRNCFEE  ,  XA.REPT_AMT  ,  XB.TOT_ADDT_AMT  ,  XB.TOT_EADDT_AMT  ,  XB.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.ERR_GBN  ,  XA.ERR_CTENT  ,  TO_CHAR(XB.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_REPT_ERR  XA  ,  GIBU.TBRA_DEMD_AUTO  XB  ,  GIBU.TBRA_UPSO  XC  ,  INSA.TCPM_DEPT  XD  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_REPT_ERR  B  WHERE  B.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  B.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  A.APPL_DAY  <=  :REPT_YRMN  ||  '32'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.UPSO_GRAD  =  C.GRAD_GBN   \n";
        query +=" AND  C.BSTYP_CD  =  A.BSTYP_CD  )  XE  WHERE  XA.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.START_YRMN  =  XA.START_YRMN   \n";
        query +=" AND  XB.END_YRMN  =  XA.END_YRMN   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XC.BRAN_CD   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.PROC_GBN  =  :PROC_GBN  ORDER  BY  XC.BRAN_CD,  XC.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("PROC_GBN", PROC_GBN);               //�ڵ� ó�� ����
        return sobj;
    }
    // �ڵ���ü �Ա� ��� ��ȸ
    // �ڵ���ü �Ա� ��� ����Ʈ�� ��ȸ�Ѵ�
    public DOBJ CALLrept_auto_report_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_auto_report_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_report_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //�Ա� ���
        String   PROC_GBN = dobj.getRetObject("S").getRecord().get("PROC_GBN");   //�ڵ� ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  XA.START_YRMN  ||  '01'  START_YRMN  ,  XA.END_YRMN  ||  '01'  END_YRMN  ,  XC.GRAD  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  XA.DEMD_GBN  ,  '1'  PRINT_YN  ,  XA.TRNF_RSLT  ,  TO_CHAR(XA.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  GIBU.TBRA_UPSO  XB  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  APPL_DAY  <=  :REPT_YRMN  ||  '32'  GROUP  BY  UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.BSTYP_CD  =  C.BSTYP_CD   \n";
        query +=" AND  A.UPSO_GRAD  =  C.GRAD_GBN  )  XC  ,  INSA.TCPM_DEPT  XD  WHERE  XA.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  XA.DEMD_YRMN  IN   \n";
        query +=" (SELECT  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  XA.UPSO_CD)   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NULL   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XB.BRAN_CD   \n";
        query +=" AND  XA.PROC_GBN  =  :PROC_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        sobj.setString("PROC_GBN", PROC_GBN);               //�ڵ� ó�� ����
        return sobj;
    }
    //##**$$rept_auto_report
    //##**$$end
}
