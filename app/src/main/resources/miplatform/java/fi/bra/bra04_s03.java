
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s03
{
    public bra04_s03()
    {
    }
    //##**$$rept_ocr_insert
    /* * ���α׷��� : bra04_s03
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/26
    * ����    : �Աݸ��� Ȯ�� �� �ݰ������ ������ ������ ������ �о� �Ա� ���̺� (TBRA_REPT, TBRA_REPT_OCR) �� ���� ���̺� (TBRA_NOTE) ��
    �Ա� ������ �����Ѵ�.
    �ݰ�� ���� ���� �� ����ȣ (20 �ڸ�) �� ���� ���̺��� �� ��ȣ�� û���Ⱓ ������ ������ �ִ�. �̸� Ȱ���Ͽ� ���� �� û��������
    ��ȸ�� �� û�� ���� ����, ���������� �����Ѵ�.
    �ݰ�� ������ ���¹�ȣ ������ ���� ������ ȸ�� ���¹�ȣ ���̺� (TCAC_ACCOUNT) �� �ڵ���ü/OCR ���¹�ȣ�� ��ȸ�Ѵ�. (USAGE = '004')
    Input
    CLIENT_NUM (�� ��ȣ)
    COMIS (������)
    END_YRMN (������)
    INSPRES_ID (����� ID)
    IRN_NO (IRN_NO)
    MAKE_ORG (MAKE_ORG)
    OCR_GBN (OCR_GBN)
    OCR_SEQ (OCR_SEQ) a
    PROC_GBN (�ڵ� ó�� ����)
    RECV_BANK_CD (���� ����)
    RECV_DAY (���� ����)
    RECV_GBN (RECV_GBN)
    REPT_AMT (�Ա� �ݾ�)
    REPT_DAY (�Ա�����)
    START_YRMN (���۳��)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLrept_ocr_insert(DOBJ dobj)
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
            dobj  = CALLrept_ocr_insert_SEL1(Conn, dobj);           //  �Աݸ���Ȯ��
            if( dobj.getRetObject("SEL1").getRecord().get("BRANEND").equals("0"))
            {
                dobj  = CALLrept_ocr_insert_SEL22(Conn, dobj);           //  �ڷ����
                dobj  = CALLrept_ocr_insert_SEL10(Conn, dobj);           //  ���� �Ա� ���¹�ȣ ��ȸ
                dobj  = CALLrept_ocr_insert_DEL4(Conn, dobj);           //  �Ա� ���� (OCR) ����
                dobj  = CALLrept_ocr_insert_DEL6(Conn, dobj);           //  �Ա� ���� ����
                dobj  = CALLrept_ocr_insert_DEL9(Conn, dobj);           //  û�� �Ա� ���� ���� ����
                dobj  = CALLrept_ocr_insert_DEL10(Conn, dobj);           //  ���� ���� ����
                dobj  = CALLrept_ocr_insert_DEL11(Conn, dobj);           //  �ܰ� ���� ����
                dobj  = CALLrept_ocr_insert_DEL20(Conn, dobj);           //  �������� ����
                dobj  = CALLrept_ocr_insert_MPD12(Conn, dobj);           //  �Ա����� ó��
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLrept_ocr_insert_SEL2(Conn, dobj);           //  ó�� �Ǽ� �˻�
                dobj  = CALLrept_ocr_insert_SEL3(Conn, dobj);           //  ����ó�� �Ա� ���� ��ȸ
                dobj  = CALLrept_ocr_insert_SEL4(Conn, dobj);           //  �Ա� ���� ���� ��ȸ
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
    public DOBJ CTLrept_ocr_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_ocr_insert_SEL1(Conn, dobj);           //  �Աݸ���Ȯ��
        if( dobj.getRetObject("SEL1").getRecord().get("BRANEND").equals("0"))
        {
            dobj  = CALLrept_ocr_insert_SEL22(Conn, dobj);           //  �ڷ����
            dobj  = CALLrept_ocr_insert_SEL10(Conn, dobj);           //  ���� �Ա� ���¹�ȣ ��ȸ
            dobj  = CALLrept_ocr_insert_DEL4(Conn, dobj);           //  �Ա� ���� (OCR) ����
            dobj  = CALLrept_ocr_insert_DEL6(Conn, dobj);           //  �Ա� ���� ����
            dobj  = CALLrept_ocr_insert_DEL9(Conn, dobj);           //  û�� �Ա� ���� ���� ����
            dobj  = CALLrept_ocr_insert_DEL10(Conn, dobj);           //  ���� ���� ����
            dobj  = CALLrept_ocr_insert_DEL11(Conn, dobj);           //  �ܰ� ���� ����
            dobj  = CALLrept_ocr_insert_DEL20(Conn, dobj);           //  �������� ����
            dobj  = CALLrept_ocr_insert_MPD12(Conn, dobj);           //  �Ա����� ó��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLrept_ocr_insert_SEL2(Conn, dobj);           //  ó�� �Ǽ� �˻�
            dobj  = CALLrept_ocr_insert_SEL3(Conn, dobj);           //  ����ó�� �Ա� ���� ��ȸ
            dobj  = CALLrept_ocr_insert_SEL4(Conn, dobj);           //  �Ա� ���� ���� ��ȸ
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
    public DOBJ CALLrept_ocr_insert_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_ocr_insert_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // �ڷ����
    public DOBJ CALLrept_ocr_insert_SEL22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL22");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL22");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_ocr_insert_SEL22(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL22");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_SEL22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_DAY  AS  REPT_DAY  ,  '04'		AS  REPT_GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // ���� �Ա� ���¹�ȣ ��ȸ
    // ���� �Ա� ���¹�ȣ�� ��ȸ�Ѵ�
    public DOBJ CALLrept_ocr_insert_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_ocr_insert_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BANK_CD  ,  ACCN_NUM  FROM  ACCT.TCAC_ACCOUNT  WHERE  USE_TYPE  =  '002' ";
        sobj.setSql(query);
        return sobj;
    }
    // �Ա� ���� (OCR) ����
    // ������ �Աݳ���� ���� �Ա� ����(TBRA_REPT_OCR)�� �����Ѵ�
    public DOBJ CALLrept_ocr_insert_DEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL4");
        VOBJ dvobj = dobj.getRetObject("SEL22");           //�ڷ�������� ������Ų OBJECT�Դϴ�.(CALLrept_ocr_insert_SEL22)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_ocr_insert_DEL4(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL4") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_DEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_OCR  \n";
        query +=" where REPT_DAY=:REPT_DAY";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // �Ա� ���� ����
    // ������ �Աݳ���� ���� �Ա� ���� (TRRA_REPT) �� �����Ѵ�
    public DOBJ CALLrept_ocr_insert_DEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL6");
        VOBJ dvobj = dobj.getRetObject("SEL22");           //�ڷ�������� ������Ų OBJECT�Դϴ�.(CALLrept_ocr_insert_SEL22)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_ocr_insert_DEL6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_DEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // û�� �Ա� ���� ���� ����
    // �Աݳ���� �������� û�� ���� ������ �����Ѵ�
    public DOBJ CALLrept_ocr_insert_DEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL9");
        VOBJ dvobj = dobj.getRetObject("SEL22");           //�ڷ�������� ������Ų OBJECT�Դϴ�.(CALLrept_ocr_insert_SEL22)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_ocr_insert_DEL9(dobj, dvobj);
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
    private SQLObject SQLrept_ocr_insert_DEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_DEMD_REPT  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // ���� ���� ����
    // �Աݳ���� �������� ���� ������ �����Ѵ�
    public DOBJ CALLrept_ocr_insert_DEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL10");
        VOBJ dvobj = dobj.getRetObject("SEL22");           //�ڷ�������� ������Ų OBJECT�Դϴ�.(CALLrept_ocr_insert_SEL22)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_ocr_insert_DEL10(dobj, dvobj);
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
    private SQLObject SQLrept_ocr_insert_DEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_NOTE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // �ܰ� ���� ����
    // �Աݳ���� �������� �ܰ� ������ �����Ѵ�
    public DOBJ CALLrept_ocr_insert_DEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL11");
        VOBJ dvobj = dobj.getRetObject("SEL22");           //�ڷ�������� ������Ų OBJECT�Դϴ�.(CALLrept_ocr_insert_SEL22)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_ocr_insert_DEL11(dobj, dvobj);
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
    private SQLObject SQLrept_ocr_insert_DEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_BALANCE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // �������� ����
    public DOBJ CALLrept_ocr_insert_DEL20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL20");
        VOBJ dvobj = dobj.getRetObject("SEL22");           //�ڷ�������� ������Ų OBJECT�Դϴ�.(CALLrept_ocr_insert_SEL22)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_ocr_insert_DEL20(dobj, dvobj);
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
    private SQLObject SQLrept_ocr_insert_DEL20(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // �Ա����� ó��
    // ���ڵ� ������ �Ա������� ó���Ѵ�
    public DOBJ CALLrept_ocr_insert_MPD12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD12");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("OCR_GBN").equals("22"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrept_ocr_insert_OSP1(Conn, dobj);           //  OCR �Ա�
            }
        }
        return dobj;
    }
    // OCR �Ա�
    // ���Һ��� OCR �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_ocr_insert_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String[]  incolumns ={"REPT_DAY","DEMD_GBN","CLIENT_NUM","START_YRMN","END_YRMN","REPT_AMT","COMIS","RECV_DAY","REMAK","BANK_CD","ACCN_NUM","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   ACCN_NUM = dobj.getRetObject("SEL10").getRecord().get("ACCN_NUM");         //���� ��ȣ
            record.put("ACCN_NUM",ACCN_NUM);
            ////
            String   BANK_CD = dobj.getRetObject("SEL10").getRecord().get("BANK_CD");         //���� �ڵ�
            record.put("BANK_CD",BANK_CD);
            ////
            String   DEMD_GBN ="O";         //û�� ����
            record.put("DEMD_GBN",DEMD_GBN);
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
        String   spname ="GIBU.SP_PROC_REPT_OCR";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"P_CNT_INST"};;
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
    // ó�� �Ǽ� �˻�
    // �Աݿ� ���� ó�� �Ǽ� �˻�
    public DOBJ CALLrept_ocr_insert_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_ocr_insert_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="04";   //�Ա� ����
        String   PROC_CNT = dobj.getRetObject("S").getRecordCnt()+"";   //PROC_CNT
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :PROC_CNT  AS  PROC_CNT  ,  MAX(NOTE_CNT)  AS  NOTE_CNT  ,  MAX(REPT_CNT)  AS  REPT_CNT  ,  MAX(ERR_CNT)  AS  ERR_CNT  FROM  (   \n";
        query +=" SELECT  COUNT(REPT_NUM)  REPT_CNT  ,  0  ERR_CNT  ,  0  NOTE_CNT  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  UNION  ALL   \n";
        query +=" SELECT  0  ,  COUNT(REPT_NUM)  ERR_CNT  ,  0  FROM  GIBU.TBRA_REPT_ERR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  UNION  ALL   \n";
        query +=" SELECT  0  ,  0  ,  COUNT(NOTE_YRMN)  NOTE_CNT  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  ) ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("PROC_CNT", PROC_CNT);               //PROC_CNT
        return sobj;
    }
    // ����ó�� �Ա� ���� ��ȸ
    // ����ó���� �Ա� ������ ��ȸ�Ѵ�
    public DOBJ CALLrept_ocr_insert_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_ocr_insert_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("SEL22").getRecord().get("REPT_GBN");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  E.DEPT_NM  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  B.UPSO_CD  ,  D.UPSO_NM  ,  F.HAN_NM  ||  '('  ||  D.STAFF_CD  ||  ')'  STAFF_CD  ,  C.START_YRMN  ,  C.END_YRMN  ,  A.REPT_AMT  ,  A.COMIS  ,  A.RECV_DAY  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.BRAN_CD  ,  C.MONPRNCFEE  ,  C.TOT_DEMD_AMT  -  (C.TOT_ADDT_AMT  +  C.TOT_EADDT_AMT)  TOT_USE_AMT  ,  C.TOT_ADDT_AMT  +  C.TOT_EADDT_AMT  TOT_ADDT_AMT  ,  C.DEMD_YRMN  FROM  GIBU.TBRA_REPT  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  MAX(DEMD_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN  )  B  ,  GIBU.TBRA_DEMD_OCR  C  ,  GIBU.TBRA_UPSO  D  ,  INSA.TCPM_DEPT  E  ,  INSA.TINS_MST01  F  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  B.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  =  A.REPT_GBN   \n";
        query +=" AND  C.DEMD_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  C.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  D.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  E.GIBU  =  D.BRAN_CD   \n";
        query +=" AND  F.STAFF_NUM(+)  =  D.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �Ա� ���� ���� ��ȸ
    // �Ա� ó�� ���� �� �߻��� ���� ������ ��ȸ�Ѵ�
    public DOBJ CALLrept_ocr_insert_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_ocr_insert_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="04";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT   \n";
        query +=" (SELECT  DEPT_NM  FROM  INSA.TCPM_DEPT  WHERE  GIBU  =  A.BRAN_CD)  DEPT_NM  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.UPSO_CD  ,  B.UPSO_NM  ,   \n";
        query +=" (SELECT  HAN_NM  ||  '('  ||  B.STAFF_CD  ||  ')'  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  A.STAFF_CD)  STAFF_NM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.REPT_AMT  ,  A.COMIS  ,  A.RECV_DAY  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.BRAN_CD  ,  A.ERR_GBN  ,  A.ERR_CTENT  ,   \n";
        query +=" (SELECT  MONPRNCFEE  FROM  GIBU.TBRA_DEMD_OCR  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  START_YRMN  =  A.START_YRMN   \n";
        query +=" AND  END_YRMN  =  A.END_YRMN)  MONPRNCFEE  ,   \n";
        query +=" (SELECT  TOT_DEMD_AMT  -  COMIS  FROM  GIBU.TBRA_DEMD_OCR  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  START_YRMN  =  A.START_YRMN   \n";
        query +=" AND  END_YRMN  =  A.END_YRMN)  TOT_USE_AMT  ,   \n";
        query +=" (SELECT  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  START_YRMN  =  A.START_YRMN   \n";
        query +=" AND  END_YRMN  =  A.END_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_REPT_ERR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  B.UPSO_CD(+)  =  A.UPSO_CD  ORDER  BY  A.BRAN_CD,  A.REPT_DAY,  A.REPT_NUM ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    //##**$$rept_ocr_insert
    //##**$$rept_closing
    /*
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
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
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
    //##**$$rept_ocr_select
    /*
    */
    public DOBJ CTLrept_ocr_select(DOBJ dobj)
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
            dobj  = CALLrept_ocr_select_SEL1(Conn, dobj);           //  ����ó�� �Ա� ���� ��ȸ
            dobj  = CALLrept_ocr_select_SEL2(Conn, dobj);           //  �Ա� ���� ���� ��ȸ
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
    public DOBJ CTLrept_ocr_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_ocr_select_SEL1(Conn, dobj);           //  ����ó�� �Ա� ���� ��ȸ
        dobj  = CALLrept_ocr_select_SEL2(Conn, dobj);           //  �Ա� ���� ���� ��ȸ
        return dobj;
    }
    // ����ó�� �Ա� ���� ��ȸ
    // ����ó���� �Ա� ������ ��ȸ�Ѵ�
    public DOBJ CALLrept_ocr_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_ocr_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="04";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  E.DEPT_NM  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  B.UPSO_CD  ,  D.UPSO_NM  ,  F.HAN_NM  ||  '('  ||  D.STAFF_CD  ||  ')'  STAFF_CD  ,  C.START_YRMN  ,  C.END_YRMN  ,  A.REPT_AMT  ,  A.COMIS  ,  A.RECV_DAY  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.BRAN_CD  ,  C.MONPRNCFEE  ,  C.TOT_DEMD_AMT  -  (C.TOT_ADDT_AMT  +  C.TOT_EADDT_AMT)  TOT_USE_AMT  ,  C.TOT_ADDT_AMT  +  C.TOT_EADDT_AMT  TOT_ADDT_AMT  ,  C.DEMD_YRMN  FROM  GIBU.TBRA_REPT  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  MAX(DEMD_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN  )  B  ,  GIBU.TBRA_DEMD_OCR  C  ,  GIBU.TBRA_UPSO  D  ,  INSA.TCPM_DEPT  E  ,  INSA.TINS_MST01  F  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  B.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  =  A.REPT_GBN   \n";
        query +=" AND  C.DEMD_YRMN(+)  =  B.DEMD_YRMN   \n";
        query +=" AND  C.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  D.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  E.GIBU  =  D.BRAN_CD   \n";
        query +=" AND  F.STAFF_NUM(+)  =  D.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �Ա� ���� ���� ��ȸ
    // �Ա� ó�� ���� �� �߻��� ���� ������ ��ȸ�Ѵ�
    public DOBJ CALLrept_ocr_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_ocr_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="04";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT   \n";
        query +=" (SELECT  DEPT_NM  FROM  INSA.TCPM_DEPT  WHERE  GIBU  =  A.BRAN_CD)  DEPT_NM  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.UPSO_CD  ,  B.UPSO_NM  ,   \n";
        query +=" (SELECT  HAN_NM  ||  '('  ||  B.STAFF_CD  ||  ')'  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  A.STAFF_CD)  STAFF_NM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.REPT_AMT  ,  A.COMIS  ,  A.RECV_DAY  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.BRAN_CD  ,  A.ERR_GBN  ,  A.ERR_CTENT  ,   \n";
        query +=" (SELECT  MONPRNCFEE  FROM  GIBU.TBRA_DEMD_OCR  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  START_YRMN  =  A.START_YRMN   \n";
        query +=" AND  END_YRMN  =  A.END_YRMN)  MONPRNCFEE  ,   \n";
        query +=" (SELECT  TOT_DEMD_AMT  -  COMIS  FROM  GIBU.TBRA_DEMD_OCR  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  START_YRMN  =  A.START_YRMN   \n";
        query +=" AND  END_YRMN  =  A.END_YRMN)  TOT_USE_AMT  ,   \n";
        query +=" (SELECT  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  START_YRMN  =  A.START_YRMN   \n";
        query +=" AND  END_YRMN  =  A.END_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_REPT_ERR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  B.UPSO_CD(+)  =  A.UPSO_CD  ORDER  BY  A.BRAN_CD,  A.REPT_DAY,  A.REPT_NUM ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    //##**$$rept_ocr_select
    //##**$$end
}
