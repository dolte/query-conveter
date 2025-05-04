
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s13
{
    public bra04_s13()
    {
    }
    //##**$$bra04_s13_save01
    /* * ���α׷��� : bra04_s13
    * �ۼ��� : 999999
    * �ۼ��� : 2009/11/26
    * ����    : ���� ���¹�ȣ���� Excel �� �ٿ���� �Աݳ��� ����Ʈ�� �Աݵ� �Աݳ����� �����Ѵ�.
    ���� Excel �� upload �� �Աݳ����� ����/������ �Ұ����ϴ�.
    (DB ���̺��� ���� ����/�����ؾ� ��, ����/���� �� ���ε� ������ �ִ� ��츦 Ȯ���ϰ� ������ ��)
    Input
    ACCN_NUM (���� ��ȣ)
    BALANCE (�ܾ�)
    BANK_CD (���� �ڵ�)
    BRAN_CD (���� �ڵ�)
    DISTR_GBN (�Ա� �й� ����)
    END_OPBI_DAY (��������)
    EXCEL_YN (����UPLOAD�����Ϳ���)
    FROM_DAY (�����)
    OUT_AMT (��� �ݾ�)
    RECEPT_BANK (�����)
    REMAK (���)
    REPTPRES (�Ա���)
    REPT_AMT (�Ա� �ݾ�)
    REPT_DAY (�Ա�����)
    START_DAY (������)
    START_OPBI_DAY (��������)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLbra04_s13_save01(DOBJ dobj)
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
            dobj  = CALLbra04_s13_save01_SEL8(Conn, dobj);           //  �������ε��Ϸù�ȣ����
            dobj  = CALLbra04_s13_save01_MPD5(Conn, dobj);           //  �ο� ���� ó��
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
    public DOBJ CTLbra04_s13_save01( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra04_s13_save01_SEL8(Conn, dobj);           //  �������ε��Ϸù�ȣ����
        dobj  = CALLbra04_s13_save01_MPD5(Conn, dobj);           //  �ο� ���� ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �������ε��Ϸù�ȣ����
    public DOBJ CALLbra04_s13_save01_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra04_s13_save01_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save01_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //�����
        String   EXCEL_YN = dobj.getRetObject("S1").firstRecord().get("EXCEL_YN");   //����UPLOAD�����Ϳ���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(SUBSTR(MAX(INP_SEQ),1,8),NULL,TO_CHAR(SYSDATE,'YYYYMMDD')  ||  '01',TO_CHAR(SYSDATE,'YYYYMMDD')  ||  LPAD(TO_NUMBER(SUBSTR(MAX(INP_SEQ),9,2))  +  1  ,2,'0'))  AS  INP_SEQ  ,  DECODE(:EXCEL_YN,  'Y',  :FROM_DAY,  TO_CHAR(SYSDATE,  'YYYYMMDD'))  REPT_DAY  FROM  GIBU.TBRA_REPT_TRANS  A  WHERE  SUBSTR(INP_SEQ,1,8)  =  TO_CHAR(SYSDATE,'YYYYMMDD')   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("FROM_DAY", FROM_DAY);               //�����
        sobj.setString("EXCEL_YN", EXCEL_YN);               //����UPLOAD�����Ϳ���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �ο� ���� ó��
    public DOBJ CALLbra04_s13_save01_MPD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD5");
        VOBJ dvobj = dobj.getRetObject("S1");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra04_s13_save01_SEL4(Conn, dobj);           //  �Աݹ�ȣ �߹�
                if( dobj.getRetObject("R").getRecord().get("EXCEL_YN").equals("Y"))
                {
                    dobj  = CALLbra04_s13_save01_INS10(Conn, dobj);           //  �������Ա�����UPLOAD
                    dobj  = CALLbra04_s13_save01_INS3(Conn, dobj);           //  �������Ա�����UPLOAD
                    dobj  = CALLbra04_s13_save01_XIUD9(Conn, dobj);           //  �Ա��� ����
                }
                else
                {
                    dobj  = CALLbra04_s13_save01_INS1(Conn, dobj);           //  �������Ա�����UPLOAD
                    dobj  = CALLbra04_s13_save01_INS3(Conn, dobj);           //  �������Ա�����UPLOAD
                    dobj  = CALLbra04_s13_save01_XIUD9(Conn, dobj);           //  �Ա��� ����
                }
            }
        }
        return dobj;
    }
    // �Աݹ�ȣ �߹�
    // �̰����� ����  �߹��� TBRA_REPT ���� �����Ѵ�. (���� �����ʹ� TBRA_REPT ���� �̰��ϱ� ����)
    public DOBJ CALLbra04_s13_save01_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra04_s13_save01_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save01_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(TO_NUMBER(REPT_NUM)),0)+1,5,'0')  AS  REPT_NUM  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  '03' ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // �������Ա�����UPLOAD
    public DOBJ CALLbra04_s13_save01_INS10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra04_s13_save01_INS10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS10") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save01_INS10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String RECV_DAY ="";        //���� ����
        double   BALANCE = dvobj.getRecord().getDouble("BALANCE");   //�ܾ�
        double   OUT_AMT = dvobj.getRecord().getDouble("OUT_AMT");   //��� �ݾ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //���� ��ȣ
        String   RECV_DAY_1 = dobj.getRetObject("R").getRecord().get("RECV_DAY");   //���� ����
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //�Ա� �ݾ�
        String   REPTPRES = dvobj.getRecord().get("REPTPRES");   //�Ա���
        String   RECEPT_BANK = dvobj.getRecord().get("RECEPT_BANK");   //�����
        String   EXCEL_YN = dvobj.getRecord().get("EXCEL_YN");   //����UPLOAD�����Ϳ���
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //���� ��ȣ
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String  DISTR_GBN="";          //�Ա� �й� ����
        if( dobj.getRetObject("R").getRecord().get("DISTR_GBN").equals("00"))
        {
            DISTR_GBN ="";
        }
        else
        {
            DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");
        }
        String   INP_SEQ = dobj.getRetObject("SEL8").getRecord().get("INP_SEQ");   //�������ε��Ϸù�ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REPT_DAY = dobj.getRetObject("SEL8").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("SEL4").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_TRANS (REPT_DAY, INP_SEQ, REPT_NUM, DISTR_GBN, INSPRES_ID, BANK_CD, EXCEL_YN, RECEPT_BANK, INS_DATE, REPTPRES, REPT_AMT, RECV_DAY, BRAN_CD, APPRV_NUM, REMAK, ACCN_NUM, OUT_AMT, BALANCE)  \n";
        query +=" values(:REPT_DAY , :INP_SEQ , :REPT_NUM , :DISTR_GBN , :INSPRES_ID , :BANK_CD , :EXCEL_YN , :RECEPT_BANK , SYSDATE, :REPTPRES , :REPT_AMT , REPLACE(:RECV_DAY_1,'-',''), :BRAN_CD , :APPRV_NUM , :REMAK , :ACCN_NUM , :OUT_AMT , :BALANCE )";
        sobj.setSql(query);
        sobj.setDouble("BALANCE", BALANCE);               //�ܾ�
        sobj.setDouble("OUT_AMT", OUT_AMT);               //��� �ݾ�
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("APPRV_NUM", APPRV_NUM);               //���� ��ȣ
        sobj.setString("RECV_DAY_1", RECV_DAY_1);               //���� ����
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setString("REPTPRES", REPTPRES);               //�Ա���
        sobj.setString("RECEPT_BANK", RECEPT_BANK);               //�����
        sobj.setString("EXCEL_YN", EXCEL_YN);               //����UPLOAD�����Ϳ���
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("DISTR_GBN", DISTR_GBN);               //�Ա� �й� ����
        sobj.setString("INP_SEQ", INP_SEQ);               //�������ε��Ϸù�ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        return sobj;
    }
    // �������Ա�����UPLOAD
    public DOBJ CALLbra04_s13_save01_INS3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS3");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra04_s13_save01_INS3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS3") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save01_INS3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String RECV_DAY ="";        //���� ����
        String REMAK ="";        //���
        String   REMAK_1 = dobj.getRetObject("R").getRecord().get("REPTPRES");   //���
        String   RECV_DAY_1 = dobj.getRetObject("R").getRecord().get("RECV_DAY");   //���� ����
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //�Ա� �ݾ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   EXCEL_YN = dvobj.getRecord().get("EXCEL_YN");   //����UPLOAD�����Ϳ���
        double   COMIS = dvobj.getRecord().getDouble("COMIS");   //������
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //���� ��ȣ
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String  DISTR_GBN="";          //�Ա� �й� ����
        if( dobj.getRetObject("R").getRecord().get("DISTR_GBN").equals("00"))
        {
            DISTR_GBN ="";
        }
        else
        {
            DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");
        }
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REPT_DAY = dobj.getRetObject("SEL8").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="03";   //�Ա� ����
        String   REPT_NUM = dobj.getRetObject("SEL4").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT (REPT_DAY, COMIS, REPT_NUM, DISTR_GBN, INSPRES_ID, BANK_CD, EXCEL_YN, REPT_GBN, INS_DATE, UPSO_CD, REPT_AMT, RECV_DAY, BRAN_CD, ACCN_NUM, REMAK)  \n";
        query +=" values(:REPT_DAY , :COMIS , :REPT_NUM , :DISTR_GBN , :INSPRES_ID , :BANK_CD , :EXCEL_YN , :REPT_GBN , SYSDATE, :UPSO_CD , :REPT_AMT , REPLACE(:RECV_DAY_1,'-',''), :BRAN_CD , :ACCN_NUM , REPLACE(:REMAK_1, chr(49824), ''))";
        sobj.setSql(query);
        sobj.setString("REMAK_1", REMAK_1);               //���
        sobj.setString("RECV_DAY_1", RECV_DAY_1);               //���� ����
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("EXCEL_YN", EXCEL_YN);               //����UPLOAD�����Ϳ���
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("DISTR_GBN", DISTR_GBN);               //�Ա� �й� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        return sobj;
    }
    // �Ա��� ����
    // chr(49824) Ư�����ڰ� ���� ���ε� �� �� �پ���� ����
    public DOBJ CALLbra04_s13_save01_XIUD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD9");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra04_s13_save01_XIUD9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save01_XIUD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   REPT_DAY = dobj.getRetObject("SEL8").getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_TRANS  \n";
        query +=" SET REPTPRES = REPLACE(REPTPRES, chr(49824), '')  \n";
        query +=" WHERE BRAN_CD = :BRAN_CD  \n";
        query +=" AND REPT_DAY = :REPT_DAY  \n";
        query +=" AND REPTPRES LIKE '%' || chr(49824) || '%'";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // �������Ա�����UPLOAD
    public DOBJ CALLbra04_s13_save01_INS1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS1");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra04_s13_save01_INS1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save01_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String RECV_DAY ="";        //���� ����
        double   BALANCE = dvobj.getRecord().getDouble("BALANCE");   //�ܾ�
        double   OUT_AMT = dvobj.getRecord().getDouble("OUT_AMT");   //��� �ݾ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //���� ��ȣ
        String   RECV_DAY_1 = dobj.getRetObject("R").getRecord().get("RECV_DAY");   //���� ����
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //�Ա� �ݾ�
        String   REPTPRES = dvobj.getRecord().get("REPTPRES");   //�Ա���
        String   RECEPT_BANK = dvobj.getRecord().get("RECEPT_BANK");   //�����
        String   EXCEL_YN = dvobj.getRecord().get("EXCEL_YN");   //����UPLOAD�����Ϳ���
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //���� ��ȣ
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String  DISTR_GBN="";          //�Ա� �й� ����
        if( dobj.getRetObject("R").getRecord().get("DISTR_GBN").equals("00"))
        {
            DISTR_GBN ="";
        }
        else
        {
            DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");
        }
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REPT_DAY = dobj.getRetObject("SEL8").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("SEL4").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_TRANS (REPT_DAY, REPT_NUM, DISTR_GBN, INSPRES_ID, BANK_CD, EXCEL_YN, RECEPT_BANK, INS_DATE, REPTPRES, REPT_AMT, RECV_DAY, BRAN_CD, APPRV_NUM, REMAK, ACCN_NUM, OUT_AMT, BALANCE)  \n";
        query +=" values(:REPT_DAY , :REPT_NUM , :DISTR_GBN , :INSPRES_ID , :BANK_CD , :EXCEL_YN , :RECEPT_BANK , SYSDATE, :REPTPRES , :REPT_AMT , REPLACE(:RECV_DAY_1,'-',''), :BRAN_CD , :APPRV_NUM , :REMAK , :ACCN_NUM , :OUT_AMT , :BALANCE )";
        sobj.setSql(query);
        sobj.setDouble("BALANCE", BALANCE);               //�ܾ�
        sobj.setDouble("OUT_AMT", OUT_AMT);               //��� �ݾ�
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("APPRV_NUM", APPRV_NUM);               //���� ��ȣ
        sobj.setString("RECV_DAY_1", RECV_DAY_1);               //���� ����
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setString("REPTPRES", REPTPRES);               //�Ա���
        sobj.setString("RECEPT_BANK", RECEPT_BANK);               //�����
        sobj.setString("EXCEL_YN", EXCEL_YN);               //����UPLOAD�����Ϳ���
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("DISTR_GBN", DISTR_GBN);               //�Ա� �й� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        return sobj;
    }
    //##**$$bra04_s13_save01
    //##**$$bra04_s13_search01
    /* * ���α׷��� : bra04_s13
    * �ۼ��� : 999999
    * �ۼ��� : 2009/12/04
    * ����    : ���º��� ������ �Աݳ����� ��ȸ�Ѵ�.
    Input
    ACCN_NUM (���� ��ȣ)
    BANK_CD (���� �ڵ�)
    BRAN_CD (���� �ڵ�)
    END_DAY (������)
    FROM_DAY (�����)
    REPT_DAY (�Ա�����)
    START_DAY (������)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLbra04_s13_search01(DOBJ dobj)
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
            dobj  = CALLbra04_s13_search01_SEL1(Conn, dobj);           //  �������Ա�������ȸ
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
    public DOBJ CTLbra04_s13_search01( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra04_s13_search01_SEL1(Conn, dobj);           //  �������Ա�������ȸ
        return dobj;
    }
    // �������Ա�������ȸ
    public DOBJ CALLbra04_s13_search01_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra04_s13_search01_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_search01_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //���� ��ȣ
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY,  A.REPT_NUM,  A.BANK_CD,  A.ACCN_NUM,  A.REPTPRES,  A.REPT_AMT,  A.OUT_AMT,  A.BALANCE,  NVL(A.DISTR_GBN,'00')  AS  DISTR_GBN,  A.RECV_DAY,  A.RECEPT_BANK,  A.BRAN_CD,  A.REMAK,  A.UPSO_CD,  A.EXCEL_YN,   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'   \n";
        query +=" AND  UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  DISTR_GBN  IS  NULL)+   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'   \n";
        query +=" AND  DISTR_GBN  =  A.DISTR_GBN)+   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TBRA_REPT_DISTR  WHERE  REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  DISTR_GBN  =  A.DISTR_GBN   \n";
        query +=" AND  BRAN_CD  =  A.BRAN_CD   \n";
        query +=" AND  REPT_GBN  =  '03')  AS  DISTR_YN  ,  A.APPRV_NUM  FROM  GIBU.TBRA_REPT_TRANS  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.REPT_DAY  >=  :START_DAY   \n";
        query +=" AND  A.REPT_DAY  <=  :END_DAY   \n";
        query +=" AND  A.ACCN_NUM  =  NVL(:ACCN_NUM,A.ACCN_NUM)  ORDER  BY  REPT_DAY,REPT_NUM ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    //##**$$bra04_s13_search01
    //##**$$bra04_s13_save02
    /* * ���α׷��� : bra04_s13
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/27
    * ����    : ���� ���¹�ȣ���� �����ڰ� �������� �Աݳ����� �����Ѵ�.
    �������� ����� ������ �Ա� ������ �߻����� ���� ��� ����/������ �����ϴ�.
    Input
    ACCN_NUM (���� ��ȣ)
    BALANCE (�ܾ�)
    BANK_CD (���� �ڵ�)
    BRAN_CD (���� �ڵ�)
    FROM_DAY (�����)
    OUT_AMT (��� �ݾ�)
    RECEPT_BANK (�����)
    REMAK (���)
    REPTPRES (�Ա���)
    REPT_AMT (�Ա� �ݾ�)
    REPT_DAY (�Ա�����)
    START_DAY (������)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLbra04_s13_save02(DOBJ dobj)
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
            dobj  = CALLbra04_s13_save02_MIUD1(Conn, dobj);           //  �������Ա���ȸ����
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
    public DOBJ CTLbra04_s13_save02( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra04_s13_save02_MIUD1(Conn, dobj);           //  �������Ա���ȸ����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �������Ա���ȸ����
    public DOBJ CALLbra04_s13_save02_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S1");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra04_s13_save02_SEL8(Conn, dobj);           //  �Աݹ�ȣ �߹�
                dobj  = CALLbra04_s13_save02_INS14(Conn, dobj);           //  �������Ա���������
                dobj  = CALLbra04_s13_save02_INS7(Conn, dobj);           //  �������Ա���������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra04_s13_save02_UPD6(Conn, dobj);           //  �������Ա���������
                dobj  = CALLbra04_s13_save02_UPD11(Conn, dobj);           //  �������Ա�������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra04_s13_save02_DEL11(Conn, dobj);           //  �������Ա���������
                dobj  = CALLbra04_s13_save02_DEL12(Conn, dobj);           //  �������Ա���������
            }
        }
        return dobj;
    }
    // �������Ա���������
    public DOBJ CALLbra04_s13_save02_DEL11(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLbra04_s13_save02_DEL11(dobj, dvobj);
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
    private SQLObject SQLbra04_s13_save02_DEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_TRANS  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // �Աݹ�ȣ �߹�
    public DOBJ CALLbra04_s13_save02_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra04_s13_save02_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save02_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(TO_NUMBER(REPT_NUM)),0)+1,5,'0')  AS  REPT_NUM  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  '03' ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // �������Ա���������
    public DOBJ CALLbra04_s13_save02_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra04_s13_save02_UPD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save02_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //���� ��ȣ
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String  DISTR_GBN="";          //�Ա� �й� ����
        if( dobj.getRetObject("R").getRecord().get("DISTR_GBN").equals("00"))
        {
            DISTR_GBN ="";
        }
        else
        {
            DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");
        }
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT_TRANS  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , DISTR_GBN=:DISTR_GBN , MOD_DATE=SYSDATE , APPRV_NUM=:APPRV_NUM  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM";
        sobj.setSql(query);
        sobj.setString("APPRV_NUM", APPRV_NUM);               //���� ��ȣ
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("DISTR_GBN", DISTR_GBN);               //�Ա� �й� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // �������Ա���������
    public DOBJ CALLbra04_s13_save02_DEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra04_s13_save02_DEL12(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save02_DEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN ="03";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �������Ա���������
    public DOBJ CALLbra04_s13_save02_INS14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS14");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra04_s13_save02_INS14(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS14") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save02_INS14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String RECV_DAY ="";        //���� ����
        double   BALANCE = dvobj.getRecord().getDouble("BALANCE");   //�ܾ�
        double   OUT_AMT = dvobj.getRecord().getDouble("OUT_AMT");   //��� �ݾ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //���� ��ȣ
        String   RECV_DAY_1 = dobj.getRetObject("R").getRecord().get("RECV_DAY");   //���� ����
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //�Ա� �ݾ�
        String   REPTPRES = dvobj.getRecord().get("REPTPRES");   //�Ա���
        String   RECEPT_BANK = dvobj.getRecord().get("RECEPT_BANK");   //�����
        String   EXCEL_YN = dvobj.getRecord().get("EXCEL_YN");   //����UPLOAD�����Ϳ���
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //���� ��ȣ
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String  DISTR_GBN="";          //�Ա� �й� ����
        if( dobj.getRetObject("R").getRecord().get("DISTR_GBN").equals("00"))
        {
            DISTR_GBN ="";
        }
        else
        {
            DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");
        }
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("SEL8").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_TRANS (REPT_DAY, REPT_NUM, DISTR_GBN, INSPRES_ID, BANK_CD, EXCEL_YN, RECEPT_BANK, INS_DATE, REPTPRES, REPT_AMT, RECV_DAY, BRAN_CD, APPRV_NUM, REMAK, ACCN_NUM, OUT_AMT, BALANCE)  \n";
        query +=" values(:REPT_DAY , :REPT_NUM , :DISTR_GBN , :INSPRES_ID , :BANK_CD , :EXCEL_YN , :RECEPT_BANK , SYSDATE, :REPTPRES , :REPT_AMT , REPLACE(:RECV_DAY_1,'-',''), :BRAN_CD , :APPRV_NUM , :REMAK , :ACCN_NUM , :OUT_AMT , :BALANCE )";
        sobj.setSql(query);
        sobj.setDouble("BALANCE", BALANCE);               //�ܾ�
        sobj.setDouble("OUT_AMT", OUT_AMT);               //��� �ݾ�
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("APPRV_NUM", APPRV_NUM);               //���� ��ȣ
        sobj.setString("RECV_DAY_1", RECV_DAY_1);               //���� ����
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setString("REPTPRES", REPTPRES);               //�Ա���
        sobj.setString("RECEPT_BANK", RECEPT_BANK);               //�����
        sobj.setString("EXCEL_YN", EXCEL_YN);               //����UPLOAD�����Ϳ���
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("DISTR_GBN", DISTR_GBN);               //�Ա� �й� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        return sobj;
    }
    // �������Ա�������
    public DOBJ CALLbra04_s13_save02_UPD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD11");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra04_s13_save02_UPD11(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD11") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save02_UPD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String  DISTR_GBN="";          //�Ա� �й� ����
        if( dobj.getRetObject("R").getRecord().get("DISTR_GBN").equals("00"))
        {
            DISTR_GBN ="";
        }
        else
        {
            DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");
        }
        String   REPT_GBN ="03";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT  \n";
        query +=" set DISTR_GBN=:DISTR_GBN  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("DISTR_GBN", DISTR_GBN);               //�Ա� �й� ����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �������Ա���������
    public DOBJ CALLbra04_s13_save02_INS7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLbra04_s13_save02_INS7(dobj, dvobj);
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
    private SQLObject SQLbra04_s13_save02_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String RECV_DAY ="";        //���� ����
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   RECV_DAY_1 = dobj.getRetObject("R").getRecord().get("RECV_DAY");   //���� ����
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //�Ա� �ݾ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   COMIS = dvobj.getRecord().getDouble("COMIS");   //������
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //���� ��ȣ
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String  DISTR_GBN="";          //�Ա� �й� ����
        if( dobj.getRetObject("R").getRecord().get("DISTR_GBN").equals("00"))
        {
            DISTR_GBN ="";
        }
        else
        {
            DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");
        }
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //�Ա�����
        String   REPT_GBN ="03";   //�Ա� ����
        String   REPT_NUM = dobj.getRetObject("SEL8").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT (REPT_DAY, COMIS, REPT_NUM, DISTR_GBN, INSPRES_ID, BANK_CD, REPT_GBN, INS_DATE, UPSO_CD, REPT_AMT, RECV_DAY, BRAN_CD, ACCN_NUM, REMAK)  \n";
        query +=" values(:REPT_DAY , :COMIS , :REPT_NUM , :DISTR_GBN , :INSPRES_ID , :BANK_CD , :REPT_GBN , SYSDATE, :UPSO_CD , :REPT_AMT , REPLACE(:RECV_DAY_1,'-',''), :BRAN_CD , :ACCN_NUM , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("RECV_DAY_1", RECV_DAY_1);               //���� ����
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("DISTR_GBN", DISTR_GBN);               //�Ա� �й� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        return sobj;
    }
    //##**$$bra04_s13_save02
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
        return dobj;
    }
    private SQLObject SQLrept_closing_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա� ����
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(:REPT_DAY,1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(:REPT_DAY,5,2)  =  END_MON   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա� ����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$rept_closing
    //##**$$bra04_s13_balanceCheck
    /* * ���α׷��� : bra04_s13
    * �ۼ��� : 999999
    * �ۼ��� : 2009/11/16
    * ����    : ������ �Աݳ��� ���� �� ���º� ������ row �� �����ܾװ� �����ϰ��� �ϴ� ������ ���� �ܾ��� ��ġ�ϴ��� Ȯ��
    -> ��� ������ �Աݳ����� ����Ǵ��� Ȯ���ϱ� ���� ���.
    Input
    ACCN_NUM (���� ��ȣ)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLbra04_s13_balanceCheck(DOBJ dobj)
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
            dobj  = CALLbra04_s13_balanceCheck_SEL1(Conn, dobj);           //  �ܾ����ռ�üũ
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
    public DOBJ CTLbra04_s13_balanceCheck( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra04_s13_balanceCheck_SEL1(Conn, dobj);           //  �ܾ����ռ�üũ
        return dobj;
    }
    // �ܾ����ռ�üũ
    public DOBJ CALLbra04_s13_balanceCheck_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra04_s13_balanceCheck_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_balanceCheck_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //���� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.ACCN_NUM  ,  B.REPT_NUM  ,  B.BALANCE  ,  B.RECV_DAY  ,  B.REPT_DAY  ,  B.REPT_AMT  FROM  (   \n";
        query +=" SELECT  MAX(REPT_DAY  ||  REPT_NUM)  REPT_DAYS  FROM  GIBU.TBRA_REPT_TRANS  WHERE  ACCN_NUM  =  :ACCN_NUM  )  A  ,  GIBU.TBRA_REPT_TRANS  B  WHERE  B.REPT_DAY  ||  REPT_NUM  =  A.REPT_DAYS   \n";
        query +=" AND  B.ACCN_NUM  =  :ACCN_NUM ";
        sobj.setSql(query);
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        return sobj;
    }
    //##**$$bra04_s13_balanceCheck
    //##**$$bra04_s13_delCheck
    /* * ���α׷��� : bra04_s13
    * �ۼ��� : 999999
    * �ۼ��� : 2009/12/04
    * ����    : ������ �Աݳ����� ���� ���ɿ��θ� �˻��Ѵ�.
    - �Էµ� �Աݳ����� ���ҿ� ������ �߻��� ��� ����/���� �Ұ��� -> ���� û������/�ܾ������� ������ ��ħ
    - �Էµ� �Աݳ��� ���Ŀ� �ٽ� �Է³����� �ִ� ��� ����/���� �Ұ��� -> ��ü �ܾ׿� ������ ��ħ
    Input
    BRAN_CD (���� �ڵ�)
    REPT_DAY (�Ա�����)
    REPT_NUM (�Ա� ��ȣ)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLbra04_s13_delCheck(DOBJ dobj)
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
            dobj  = CALLbra04_s13_delCheck_SEL1(Conn, dobj);           //  ����üũ
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
    public DOBJ CTLbra04_s13_delCheck( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra04_s13_delCheck_SEL1(Conn, dobj);           //  ����üũ
        return dobj;
    }
    // ����üũ
    public DOBJ CALLbra04_s13_delCheck_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra04_s13_delCheck_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_delCheck_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  REPT_GBN  =  '03')+   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  DISTR_GBN  =  A.DISTR_GBN   \n";
        query +=" AND  REPT_GBN  =  '03')+   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TBRA_REPT_DISTR  WHERE  REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  DISTR_GBN  =  A.DISTR_GBN   \n";
        query +=" AND  BRAN_CD  =  A.BRAN_CD   \n";
        query +=" AND  REPT_GBN  =  '03')  AS  UPSO_CD,   \n";
        query +=" (SELECT  MAX(REPT_DAY)  FROM  GIBU.TBRA_REPT_TRANS  WHERE  INS_DATE  =   \n";
        query +=" (SELECT  MAX(INS_DATE)  FROM  GIBU.TBRA_REPT_TRANS  WHERE  BRAN_CD  =  :BRAN_CD  )   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  )  AS  REPT_DAY,   \n";
        query +=" (SELECT  MAX(REPT_NUM)  FROM  GIBU.TBRA_REPT_TRANS  WHERE  INS_DATE  =   \n";
        query +=" (SELECT  MAX(INS_DATE)  FROM  GIBU.TBRA_REPT_TRANS  WHERE  BRAN_CD  =  :BRAN_CD  )   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  )  AS  REPT_NUM  FROM  GIBU.TBRA_REPT_TRANS  A  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$bra04_s13_delCheck
    //##**$$get_Last_balance
    /*
    */
    public DOBJ CTLget_Last_balance(DOBJ dobj)
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
            dobj  = CALLget_Last_balance_SEL1(Conn, dobj);           //  ������ ���� �ܰ� ��ȸ
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
    public DOBJ CTLget_Last_balance( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_Last_balance_SEL1(Conn, dobj);           //  ������ ���� �ܰ� ��ȸ
        return dobj;
    }
    // ������ ���� �ܰ� ��ȸ
    public DOBJ CALLget_Last_balance_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLget_Last_balance_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_Last_balance_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //���� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BALANCE  FROM  (   \n";
        query +=" SELECT  NVL(BALANCE,  0)  BALANCE  FROM  GIBU.TBRA_REPT_TRANS  WHERE  ACCN_NUM  =  :ACCN_NUM  ORDER  BY  REPT_DAY  DESC,  REPT_NUM  DESC  )  WHERE  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        return sobj;
    }
    //##**$$get_Last_balance
    //##**$$end
}
