
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra_temp1
{
    public bra_temp1()
    {
    }
    //##**$$upso_regist_temp
    /*
    */
    public DOBJ CTLupso_regist_temp(DOBJ dobj)
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
            dobj  = CALLupso_regist_temp_MIUD1(Conn, dobj);           //  ���ҵ��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_regist_temp_SEL17(Conn, dobj);           //  ���һ�������ȸ
            if( dobj.getRetObject("SEL17").getRecordCnt() > 0 && dobj.getRetObject("SEL17").getRecord().get("BSTYP_CD").equals("o"))
            {
                dobj  = CALLupso_regist_temp_SEL24(Conn, dobj);           //  �뷡���
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
    public DOBJ CTLupso_regist_temp( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_regist_temp_MIUD1(Conn, dobj);           //  ���ҵ��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupso_regist_temp_SEL17(Conn, dobj);           //  ���һ�������ȸ
        if( dobj.getRetObject("SEL17").getRecordCnt() > 0 && dobj.getRetObject("SEL17").getRecord().get("BSTYP_CD").equals("o"))
        {
            dobj  = CALLupso_regist_temp_SEL24(Conn, dobj);           //  �뷡���
        }
        return dobj;
    }
    // ���ҵ��
    public DOBJ CALLupso_regist_temp_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj.setRetObject(dvobj.getRecVobj("R"));// �����ڵ����
                // ���κ� �����ڵ��� max��
                public DOBJ CALLupso_regist_temp_SEL44(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    dobj.setRtnname("SEL44");
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("SEL44");
                    VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
                    SQLObject  sobj = SQLupso_regist_temp_SEL44(dobj, dvobj);
                    qexe.DispSelectSql(sobj);
                    VOBJ rvobj = qexe.executeQuery(Conn, sobj);
                    rvobj.setName("SEL44");
                    rvobj.Println("SEL44");
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_SEL44(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" SELECT  LPAD(SNUM+1,7,'0')  SNUM  ,  SNUM_TYPE  ,  CDCLASS  ,  LPAD(SNUM+1,7,'0')  ||  CDCLASS  N_UPSO_CD  FROM  GIBU.TBRA_SNUM  WHERE  SNUM_TYPE  =  'U'   \n";
                    query +=" AND  CDCLASS  =  :BRAN_CD ";
                    sobj.setSql(query);
                    sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
                    return sobj;
                }
                // ���һ���Ȯ��
                // ������ ��� ���¸� Ȯ���Ѵ٤�. 1: ���ĵ�� 2: �����
                public DOBJ CALLupso_regist_temp_SEL25(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    dobj.setRtnname("SEL25");
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("SEL25");
                    VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
                    SQLObject  sobj = SQLupso_regist_temp_SEL25(dobj, dvobj);
                    VOBJ rvobj = qexe.executeQuery(Conn, sobj);
                    rvobj.setName("SEL25");
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_SEL25(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" SELECT  UPSO_STAT  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD ";
                    sobj.setSql(query);
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    return sobj;
                }
                // �Ϸù�ȣ����
                // ���κ� �ϷĹ�ȣ(MAX��) ����
                public DOBJ CALLupso_regist_temp_UPD44(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("UPD44");
                    VOBJ dvobj = dobj.getRetObject("SEL44");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_UPD44(dobj, dvobj);
                        qexe.DispSelectSql(sobj);
                        updcnt += qexe.executeUpdate(Conn, sobj);
                    }
                    rvobj = new VOBJ();
                    HashMap recordx = new HashMap();
                    recordx.put("UPDCNT",updcnt+"");
                    rvobj.addRecord(recordx);
                    rvobj.setName("UPD44") ;
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_UPD44(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String MOD_DATE ="";        //���� �Ͻ�
                    String   SNUM_TYPE = dvobj.getRecord().get("SNUM_TYPE");
                    String   CDCLASS = dvobj.getRecord().get("CDCLASS");
                    String   MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //������ ID
                    String   SNUM = dobj.getRetObject("SEL44").getRecord().get("SNUM");   //�Ϸù�ȣ
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" Update GIBU.TBRA_SNUM  \n";
                    query +=" set MODPRES_ID=:MODPRES_ID , MOD_DATE=SYSDATE , SNUM=:SNUM  \n";
                    query +=" where CDCLASS=:CDCLASS  \n";
                    query +=" and SNUM_TYPE=:SNUM_TYPE";
                    sobj.setSql(query);
                    sobj.setString("SNUM_TYPE", SNUM_TYPE);
                    sobj.setString("CDCLASS", CDCLASS);
                    sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
                    sobj.setString("SNUM", SNUM);               //�Ϸù�ȣ
                    return sobj;
                }
                // �⺻�����Է�
                public DOBJ CALLupso_regist_temp_INS6(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("INS6");
                    VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_INS6(dobj, dvobj);
                        qexe.DispSelectSql(sobj);
                        updcnt += qexe.executeUpdate(Conn, sobj);
                    }
                    rvobj = new VOBJ();
                    HashMap recordx = new HashMap();
                    recordx.put("UPDCNT",updcnt+"");
                    rvobj.addRecord(recordx);
                    rvobj.setName("INS6") ;
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_INS6(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String CLIENT_NUM ="";        //�� ��ȣ
                    String INS_DATE ="";        //��� �Ͻ�
                    String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //���� �����ȣ
                    String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
                    String   BIOWN_NUM = dvobj.getRecord().get("BIOWN_NUM");   //����� ��ȣ
                    String   PERMMSTR_PHONNUM = dvobj.getRecord().get("PERMMSTR_PHONNUM");   //�㰡�� ��ȭ��ȣ
                    String   PERMMSTR_ADDR1 = dvobj.getRecord().get("PERMMSTR_ADDR1");   //�㰡�� �ּ�1
                    String   MAIL_RCPT = dvobj.getRecord().get("MAIL_RCPT");   //���� ������
                    String   MNG_ZIP = dvobj.getRecord().get("MNG_ZIP");   //���� �����ȣ
                    String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
                    String   MNGEMSTR_ADDR2 = dvobj.getRecord().get("MNGEMSTR_ADDR2");   //�濵�� �ּ�2
                    String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //�濵�� �̸�
                    String   PERMMSTR_ADDR2 = dvobj.getRecord().get("PERMMSTR_ADDR2");   //�㰡�� �ּ�2
                    String   CORP_NUM = dvobj.getRecord().get("CORP_NUM");   //���� ��ȣ
                    String   MNGEMSTR_REMAK = dvobj.getRecord().get("MNGEMSTR_REMAK");   //�濵�� ���
                    String   UPSO_REMAK = dvobj.getRecord().get("UPSO_REMAK");   //���� ���
                    String   UPSO_ADDR2 = dvobj.getRecord().get("UPSO_ADDR2");   //���� �ּ�2
                    double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
                    String   PAPER_CANC_YN = dvobj.getRecord().get("PAPER_CANC_YN");   //���� ��� ����
                    String   PERMMSTR_RESINUM = dvobj.getRecord().get("PERMMSTR_RESINUM");   //�㰡�� �ֹι�ȣ
                    String   PERMMSTR_NM = dvobj.getRecord().get("PERMMSTR_NM");   //�㰡�� �̸�
                    String   UPSO_NM = dvobj.getRecord().get("UPSO_NM");   //���� ��
                    String   CLIENT_NUM_1 = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //�� ��ȣ
                    String   PERMMSTR_REMAK = dvobj.getRecord().get("PERMMSTR_REMAK");   //�㰡�� ���
                    String   MNGEMSTR_ZIP = dvobj.getRecord().get("MNGEMSTR_ZIP");   //�濵�� �����ȣ
                    String   MNGEMSTR_RESINUM = dvobj.getRecord().get("MNGEMSTR_RESINUM");   //�濵�� �ֹι�ȣ
                    String   MNGEMSTR_PHONNUM = dvobj.getRecord().get("MNGEMSTR_PHONNUM");   //�濵�� ��ȭ��ȣ
                    String   UPSO_STAT = dvobj.getRecord().get("UPSO_STAT");   //���� ����
                    String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
                    String   PERMMSTR_HPNUM = dvobj.getRecord().get("PERMMSTR_HPNUM");   //�㰡�� �ڵ�����ȣ
                    String   UPSO_ADDR1 = dvobj.getRecord().get("UPSO_ADDR1");   //���� �ּ�1
                    String   MNGEMSTR_ADDR1 = dvobj.getRecord().get("MNGEMSTR_ADDR1");   //�濵�� �ּ�1
                    String   BILL_ISS_YN = dvobj.getRecord().get("BILL_ISS_YN");   //��꼭 ���� ����
                    String   UPSO_PHON = dvobj.getRecord().get("UPSO_PHON");   //���� ��ȭ
                    String   PERMMSTR_ZIP = dvobj.getRecord().get("PERMMSTR_ZIP");   //�㰡�� �����ȣ
                    String   OPBI_DAY = dvobj.getRecord().get("OPBI_DAY");   //���� ����
                    String   BEFORE_UPSO_CD = dvobj.getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
                    String   PAYPRES_GBN = dvobj.getRecord().get("PAYPRES_GBN");   //������ ����
                    String   MNGEMSTR_HPNUM = dvobj.getRecord().get("MNGEMSTR_HPNUM");   //�濵�� �ڵ�����ȣ
                    String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //����� ID
                    String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //TEMP1
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" Insert into GIBU.TBRA_UPSO (MNGEMSTR_HPNUM, PAYPRES_GBN, INSPRES_ID, BEFORE_UPSO_CD, OPBI_DAY, PERMMSTR_ZIP, UPSO_PHON, BILL_ISS_YN, INS_DATE, MNGEMSTR_ADDR1, UPSO_ADDR1, PERMMSTR_HPNUM, STAFF_CD, UPSO_STAT, MNGEMSTR_PHONNUM, MNGEMSTR_RESINUM, MNGEMSTR_ZIP, PERMMSTR_REMAK, UPSO_CD, CLIENT_NUM, UPSO_NM, PERMMSTR_NM, PERMMSTR_RESINUM, PAPER_CANC_YN, MCHNDAESU, UPSO_ADDR2, UPSO_REMAK, MNGEMSTR_REMAK, CORP_NUM, PERMMSTR_ADDR2, MNGEMSTR_NM, MNGEMSTR_ADDR2, BSCON_CD, MNG_ZIP, MAIL_RCPT, PERMMSTR_ADDR1, PERMMSTR_PHONNUM, BIOWN_NUM, BRAN_CD, UPSO_ZIP)  \n";
                    query +=" values(:MNGEMSTR_HPNUM , :PAYPRES_GBN , :INSPRES_ID , :BEFORE_UPSO_CD , :OPBI_DAY , :PERMMSTR_ZIP , :UPSO_PHON , :BILL_ISS_YN , SYSDATE, :MNGEMSTR_ADDR1 , :UPSO_ADDR1 , :PERMMSTR_HPNUM , :STAFF_CD , :UPSO_STAT , :MNGEMSTR_PHONNUM , :MNGEMSTR_RESINUM , :MNGEMSTR_ZIP , :PERMMSTR_REMAK , :UPSO_CD , GIBU.FT_GET_AUTO_CLIENTNUM(:CLIENT_NUM_1), :UPSO_NM , :PERMMSTR_NM , :PERMMSTR_RESINUM , :PAPER_CANC_YN , :MCHNDAESU , :UPSO_ADDR2 , :UPSO_REMAK , :MNGEMSTR_REMAK , :CORP_NUM , :PERMMSTR_ADDR2 , :MNGEMSTR_NM , :MNGEMSTR_ADDR2 , :BSCON_CD , :MNG_ZIP , :MAIL_RCPT , :PERMMSTR_ADDR1 , :PERMMSTR_PHONNUM , :BIOWN_NUM , :BRAN_CD , :UPSO_ZIP )";
                    sobj.setSql(query);
                    sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
                    sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
                    sobj.setString("BIOWN_NUM", BIOWN_NUM);               //����� ��ȣ
                    sobj.setString("PERMMSTR_PHONNUM", PERMMSTR_PHONNUM);               //�㰡�� ��ȭ��ȣ
                    sobj.setString("PERMMSTR_ADDR1", PERMMSTR_ADDR1);               //�㰡�� �ּ�1
                    sobj.setString("MAIL_RCPT", MAIL_RCPT);               //���� ������
                    sobj.setString("MNG_ZIP", MNG_ZIP);               //���� �����ȣ
                    sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
                    sobj.setString("MNGEMSTR_ADDR2", MNGEMSTR_ADDR2);               //�濵�� �ּ�2
                    sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //�濵�� �̸�
                    sobj.setString("PERMMSTR_ADDR2", PERMMSTR_ADDR2);               //�㰡�� �ּ�2
                    sobj.setString("CORP_NUM", CORP_NUM);               //���� ��ȣ
                    sobj.setString("MNGEMSTR_REMAK", MNGEMSTR_REMAK);               //�濵�� ���
                    sobj.setString("UPSO_REMAK", UPSO_REMAK);               //���� ���
                    sobj.setString("UPSO_ADDR2", UPSO_ADDR2);               //���� �ּ�2
                    sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
                    sobj.setString("PAPER_CANC_YN", PAPER_CANC_YN);               //���� ��� ����
                    sobj.setString("PERMMSTR_RESINUM", PERMMSTR_RESINUM);               //�㰡�� �ֹι�ȣ
                    sobj.setString("PERMMSTR_NM", PERMMSTR_NM);               //�㰡�� �̸�
                    sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
                    sobj.setString("CLIENT_NUM_1", CLIENT_NUM_1);               //�� ��ȣ
                    sobj.setString("PERMMSTR_REMAK", PERMMSTR_REMAK);               //�㰡�� ���
                    sobj.setString("MNGEMSTR_ZIP", MNGEMSTR_ZIP);               //�濵�� �����ȣ
                    sobj.setString("MNGEMSTR_RESINUM", MNGEMSTR_RESINUM);               //�濵�� �ֹι�ȣ
                    sobj.setString("MNGEMSTR_PHONNUM", MNGEMSTR_PHONNUM);               //�濵�� ��ȭ��ȣ
                    sobj.setString("UPSO_STAT", UPSO_STAT);               //���� ����
                    sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
                    sobj.setString("PERMMSTR_HPNUM", PERMMSTR_HPNUM);               //�㰡�� �ڵ�����ȣ
                    sobj.setString("UPSO_ADDR1", UPSO_ADDR1);               //���� �ּ�1
                    sobj.setString("MNGEMSTR_ADDR1", MNGEMSTR_ADDR1);               //�濵�� �ּ�1
                    sobj.setString("BILL_ISS_YN", BILL_ISS_YN);               //��꼭 ���� ����
                    sobj.setString("UPSO_PHON", UPSO_PHON);               //���� ��ȭ
                    sobj.setString("PERMMSTR_ZIP", PERMMSTR_ZIP);               //�㰡�� �����ȣ
                    sobj.setString("OPBI_DAY", OPBI_DAY);               //���� ����
                    sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
                    sobj.setString("PAYPRES_GBN", PAYPRES_GBN);               //������ ����
                    sobj.setString("MNGEMSTR_HPNUM", MNGEMSTR_HPNUM);               //�濵�� �ڵ�����ȣ
                    sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
                    sobj.setString("UPSO_CD", UPSO_CD);               //TEMP1
                    return sobj;
                }
                // ������������
                public DOBJ CALLupso_regist_temp_DEL27(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("DEL27");
                    VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_DEL27(dobj, dvobj);
                        updcnt += qexe.executeUpdate(Conn, sobj);
                    }
                    rvobj = new VOBJ();
                    HashMap recordx = new HashMap();
                    recordx.put("UPDCNT",updcnt+"");
                    rvobj.addRecord(recordx);
                    rvobj.setName("DEL27") ;
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_DEL27(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" Delete from  \n";
                    query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
                    query +=" where UPSO_CD=:UPSO_CD";
                    sobj.setSql(query);
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    return sobj;
                }
                // test
                public DOBJ CALLupso_regist_temp_SEL45(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    dobj.setRtnname("SEL45");
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("SEL45");
                    VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
                    SQLObject  sobj = SQLupso_regist_temp_SEL45(dobj, dvobj);
                    qexe.DispSelectSql(sobj);
                    VOBJ rvobj = qexe.executeQuery(Conn, sobj);
                    rvobj.setName("SEL45");
                    rvobj.Println("SEL45");
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_SEL45(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" SELECT  COUNT(UPSO_CD)  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD ";
                    sobj.setSql(query);
                    sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
                    return sobj;
                }
                // �뷡����������
                public DOBJ CALLupso_regist_temp_DEL34(Connection Conn, DOBJ dobj) throws Exception
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
                        sobj = SQLupso_regist_temp_DEL34(dobj, dvobj);
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
                private SQLObject SQLupso_regist_temp_DEL34(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" Delete from  \n";
                    query +=" GIBU.TBRA_NOREBANG_INFO  \n";
                    query +=" where UPSO_CD=:UPSO_CD";
                    sobj.setSql(query);
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    return sobj;
                }
                // ���ұ⺻��������
                // ���ұ⺻������ �����Ѵ�. �� ���� ������ ���� ó���Ѵ�.
                public DOBJ CALLupso_regist_temp_UPD28(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("UPD28");
                    VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_UPD28(dobj, dvobj);
                        updcnt += qexe.executeUpdate(Conn, sobj);
                    }
                    rvobj = new VOBJ();
                    HashMap recordx = new HashMap();
                    recordx.put("UPDCNT",updcnt+"");
                    rvobj.addRecord(recordx);
                    rvobj.setName("UPD28") ;
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_UPD28(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //������ ID
                    String MOD_DATE ="";        //���� �Ͻ�
                    String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //���� �����ȣ
                    String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
                    String   BIOWN_NUM = dvobj.getRecord().get("BIOWN_NUM");   //����� ��ȣ
                    String   PERMMSTR_PHONNUM = dvobj.getRecord().get("PERMMSTR_PHONNUM");   //�㰡�� ��ȭ��ȣ
                    String   MAIL_RCPT = dvobj.getRecord().get("MAIL_RCPT");   //���� ������
                    String   NEW_DAY = dvobj.getRecord().get("NEW_DAY");   //�ű� ����
                    String   MNG_ZIP = dvobj.getRecord().get("MNG_ZIP");   //���� �����ȣ
                    String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
                    String   MNGEMSTR_ADDR2 = dvobj.getRecord().get("MNGEMSTR_ADDR2");   //�濵�� �ּ�2
                    String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //�濵�� �̸�
                    String   CORP_NUM = dvobj.getRecord().get("CORP_NUM");   //���� ��ȣ
                    String   UPSO_REMAK = dvobj.getRecord().get("UPSO_REMAK");   //���� ���
                    String   UPSO_ADDR2 = dvobj.getRecord().get("UPSO_ADDR2");   //���� �ּ�2
                    double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
                    String   PAPER_CANC_YN = dvobj.getRecord().get("PAPER_CANC_YN");   //���� ��� ����
                    String   PERMMSTR_RESINUM = dvobj.getRecord().get("PERMMSTR_RESINUM");   //�㰡�� �ֹι�ȣ
                    String   PERMMSTR_NM = dvobj.getRecord().get("PERMMSTR_NM");   //�㰡�� �̸�
                    String   UPSO_NM = dvobj.getRecord().get("UPSO_NM");   //���� ��
                    String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
                    String   MNGEMSTR_ZIP = dvobj.getRecord().get("MNGEMSTR_ZIP");   //�濵�� �����ȣ
                    String   MNGEMSTR_RESINUM = dvobj.getRecord().get("MNGEMSTR_RESINUM");   //�濵�� �ֹι�ȣ
                    String   MNGEMSTR_PHONNUM = dvobj.getRecord().get("MNGEMSTR_PHONNUM");   //�濵�� ��ȭ��ȣ
                    String   UPSO_STAT = dvobj.getRecord().get("UPSO_STAT");   //���� ����
                    String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
                    String   UPSO_ADDR1 = dvobj.getRecord().get("UPSO_ADDR1");   //���� �ּ�1
                    String   MNGEMSTR_ADDR1 = dvobj.getRecord().get("MNGEMSTR_ADDR1");   //�濵�� �ּ�1
                    String   BILL_ISS_YN = dvobj.getRecord().get("BILL_ISS_YN");   //��꼭 ���� ����
                    String   OPBI_DAY = dvobj.getRecord().get("OPBI_DAY");   //���� ����
                    String   MNGEMSTR_HPNUM = dvobj.getRecord().get("MNGEMSTR_HPNUM");   //�濵�� �ڵ�����ȣ
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" Update GIBU.TBRA_UPSO  \n";
                    query +=" set MNGEMSTR_HPNUM=:MNGEMSTR_HPNUM , OPBI_DAY=:OPBI_DAY , BILL_ISS_YN=:BILL_ISS_YN , MNGEMSTR_ADDR1=:MNGEMSTR_ADDR1 , UPSO_ADDR1=:UPSO_ADDR1 , STAFF_CD=:STAFF_CD , UPSO_STAT=:UPSO_STAT , MNGEMSTR_PHONNUM=:MNGEMSTR_PHONNUM , MNGEMSTR_RESINUM=:MNGEMSTR_RESINUM , MNGEMSTR_ZIP=:MNGEMSTR_ZIP , UPSO_NM=:UPSO_NM , PERMMSTR_NM=:PERMMSTR_NM , PERMMSTR_RESINUM=:PERMMSTR_RESINUM , PAPER_CANC_YN=:PAPER_CANC_YN , MCHNDAESU=:MCHNDAESU , UPSO_ADDR2=:UPSO_ADDR2 , UPSO_REMAK=:UPSO_REMAK ,  \n";
                    query +=" CORP_NUM=:CORP_NUM , MNGEMSTR_NM=:MNGEMSTR_NM , MNGEMSTR_ADDR2=:MNGEMSTR_ADDR2 , BSCON_CD=:BSCON_CD , MNG_ZIP=:MNG_ZIP , NEW_DAY=:NEW_DAY , MAIL_RCPT=:MAIL_RCPT , PERMMSTR_PHONNUM=:PERMMSTR_PHONNUM , BIOWN_NUM=:BIOWN_NUM , BRAN_CD=:BRAN_CD , UPSO_ZIP=:UPSO_ZIP  \n";
                    query +=" where UPSO_CD=:UPSO_CD";
                    sobj.setSql(query);
                    sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
                    sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
                    sobj.setString("BIOWN_NUM", BIOWN_NUM);               //����� ��ȣ
                    sobj.setString("PERMMSTR_PHONNUM", PERMMSTR_PHONNUM);               //�㰡�� ��ȭ��ȣ
                    sobj.setString("MAIL_RCPT", MAIL_RCPT);               //���� ������
                    sobj.setString("NEW_DAY", NEW_DAY);               //�ű� ����
                    sobj.setString("MNG_ZIP", MNG_ZIP);               //���� �����ȣ
                    sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
                    sobj.setString("MNGEMSTR_ADDR2", MNGEMSTR_ADDR2);               //�濵�� �ּ�2
                    sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //�濵�� �̸�
                    sobj.setString("CORP_NUM", CORP_NUM);               //���� ��ȣ
                    sobj.setString("UPSO_REMAK", UPSO_REMAK);               //���� ���
                    sobj.setString("UPSO_ADDR2", UPSO_ADDR2);               //���� �ּ�2
                    sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
                    sobj.setString("PAPER_CANC_YN", PAPER_CANC_YN);               //���� ��� ����
                    sobj.setString("PERMMSTR_RESINUM", PERMMSTR_RESINUM);               //�㰡�� �ֹι�ȣ
                    sobj.setString("PERMMSTR_NM", PERMMSTR_NM);               //�㰡�� �̸�
                    sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    sobj.setString("MNGEMSTR_ZIP", MNGEMSTR_ZIP);               //�濵�� �����ȣ
                    sobj.setString("MNGEMSTR_RESINUM", MNGEMSTR_RESINUM);               //�濵�� �ֹι�ȣ
                    sobj.setString("MNGEMSTR_PHONNUM", MNGEMSTR_PHONNUM);               //�濵�� ��ȭ��ȣ
                    sobj.setString("UPSO_STAT", UPSO_STAT);               //���� ����
                    sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
                    sobj.setString("UPSO_ADDR1", UPSO_ADDR1);               //���� �ּ�1
                    sobj.setString("MNGEMSTR_ADDR1", MNGEMSTR_ADDR1);               //�濵�� �ּ�1
                    sobj.setString("BILL_ISS_YN", BILL_ISS_YN);               //��꼭 ���� ����
                    sobj.setString("OPBI_DAY", OPBI_DAY);               //���� ����
                    sobj.setString("MNGEMSTR_HPNUM", MNGEMSTR_HPNUM);               //�濵�� �ڵ�����ȣ
                    return sobj;
                }
                // CHG_NUM����
                public DOBJ CALLupso_regist_temp_SEL29(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    dobj.setRtnname("SEL29");
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("SEL29");
                    VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
                    SQLObject  sobj = SQLupso_regist_temp_SEL29(dobj, dvobj);
                    VOBJ rvobj = qexe.executeQuery(Conn, sobj);
                    rvobj.setName("SEL29");
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_SEL29(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
                    String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" SELECT  LPAD(NVL(MAX(CHG_NUM),  0)  +  1,  4,  '0')  CHG_NUM  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  CHG_BRAN  =  :BRAN_CD   \n";
                    query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
                    query +=" AND  CHG_DAY  =  TO_CHAR(SYSDATE,'YYYYMMDD') ";
                    sobj.setSql(query);
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
                    return sobj;
                }
                // ���һ�������
                public DOBJ CALLupso_regist_temp_INS30(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("INS30");
                    VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_INS30(dobj, dvobj);
                        updcnt += qexe.executeUpdate(Conn, sobj);
                    }
                    rvobj = new VOBJ();
                    HashMap recordx = new HashMap();
                    recordx.put("UPDCNT",updcnt+"");
                    rvobj.addRecord(recordx);
                    rvobj.setName("INS30") ;
                    dobj.setRetObject(rvobj);
                    String message ="�뷡���� ��쿡�� �뷡�� �������� �Է��ϼž� �մϴ�.";
                    dobj.setRetmsg(message);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_INS30(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String APPL_DAY ="";        //���� ����
                    String CHG_DAY ="";        //���� ����
                    String INS_DATE ="";        //��� �Ͻ�
                    double JOIN_SEQ = 0;        //�����ε���
                    String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
                    double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //������
                    double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
                    String   USE_TIME = dvobj.getRecord().get("USE_TIME");   //���ð�
                    String   UPSO_GRAD = dvobj.getRecord().get("UPSO_GRAD");   //���� ���
                    String   APPL_DAY_2 = dvobj.getRecord().get("OPBI_DAY");   //���� ����
                    String   APPL_DAY_1 = dvobj.getRecord().get("OPBI_DAY");   //���� ����
                    String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� ��ȣ2
                    String   CHG_NUM = dobj.getRetObject("SEL29").getRecord().get("CHG_NUM");   //���� ��ȣ
                    String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //����� ID
                    String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" Insert into GIBU.TBRA_UPSORTAL_INFO (JOIN_SEQ, APPL_DAY, CHG_BRAN, CHG_DAY, INSPRES_ID, UPSO_GRAD, USE_TIME, MCHNDAESU, MONPRNCFEE, BSTYP_CD, INS_DATE, CHG_NUM, UPSO_CD)  \n";
                    query +=" values((SELECT NVL(MAX(JOIN_SEQ), 0) + 1 JOIN_SEQ FROM GIBU.TBRA_UPSORTAL_INFO ), DECODE(:APPL_DAY_1,NULL,TO_CHAR(SYSDATE,'YYYYMMDD'),:APPL_DAY_2), :CHG_BRAN , TO_CHAR(SYSDATE,'YYYYMMDD'), :INSPRES_ID , :UPSO_GRAD , :USE_TIME , :MCHNDAESU , :MONPRNCFEE , :BSTYP_CD , SYSDATE, :CHG_NUM , :UPSO_CD )";
                    sobj.setSql(query);
                    sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
                    sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
                    sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
                    sobj.setString("USE_TIME", USE_TIME);               //���ð�
                    sobj.setString("UPSO_GRAD", UPSO_GRAD);               //���� ���
                    sobj.setString("APPL_DAY_2", APPL_DAY_2);               //���� ����
                    sobj.setString("APPL_DAY_1", APPL_DAY_1);               //���� ����
                    sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ��ȣ2
                    sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
                    sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    return sobj;
                }
                // CHG_NUM����
                // ���� ���� ��ȣ�� �����Ѵ�
                public DOBJ CALLupso_regist_temp_SEL22(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    dobj.setRtnname("SEL22");
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("SEL22");
                    VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
                    SQLObject  sobj = SQLupso_regist_temp_SEL22(dobj, dvobj);
                    qexe.DispSelectSql(sobj);
                    VOBJ rvobj = qexe.executeQuery(Conn, sobj);
                    rvobj.setName("SEL22");
                    rvobj.Println("SEL22");
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_SEL22(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
                    String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" SELECT  LPAD(NVL(MAX(CHG_NUM),  0)  +  1,  4,  '0')  CHG_NUM  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  CHG_BRAN  =  :BRAN_CD   \n";
                    query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
                    query +=" AND  CHG_DAY  =  TO_CHAR(SYSDATE,'YYYYMMDD') ";
                    sobj.setSql(query);
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
                    return sobj;
                }
                // ���һ��������Է�
                public DOBJ CALLupso_regist_temp_INS10(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("INS10");
                    VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
                    dvobj.Println("INS10");
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_INS10(dobj, dvobj);
                        qexe.DispSelectSql(sobj);
                        updcnt += qexe.executeUpdate(Conn, sobj);
                    }
                    rvobj = new VOBJ();
                    HashMap recordx = new HashMap();
                    recordx.put("UPDCNT",updcnt+"");
                    rvobj.addRecord(recordx);
                    rvobj.setName("INS10") ;
                    rvobj.Println("INS10");
                    dobj.setRetObject(rvobj);
                    String message ="�뷡���� ��쿡�� �뷡�� �������� �Է��ϼž� �մϴ�.";
                    dobj.setRetmsg(message);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_INS10(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String APPL_DAY ="";        //���� ����
                    String CHG_DAY ="";        //���� ����
                    String INS_DATE ="";        //��� �Ͻ�
                    double JOIN_SEQ = 0;        //�����ε���
                    String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
                    double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //������
                    double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
                    String   USE_TIME = dvobj.getRecord().get("USE_TIME");   //���ð�
                    String   UPSO_GRAD = dvobj.getRecord().get("UPSO_GRAD");   //���� ���
                    String   APPL_DAY_2 = dvobj.getRecord().get("OPBI_DAY");   //���� ����
                    String   APPL_DAY_1 = dvobj.getRecord().get("OPBI_DAY");   //���� ����
                    String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� ��ȣ2
                    String   CHG_NUM = dobj.getRetObject("SEL22").getRecord().get("CHG_NUM");   //���� ��ȣ
                    String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //����� ID
                    String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" Insert into GIBU.TBRA_UPSORTAL_INFO (JOIN_SEQ, APPL_DAY, CHG_BRAN, CHG_DAY, INSPRES_ID, UPSO_GRAD, USE_TIME, MCHNDAESU, MONPRNCFEE, BSTYP_CD, INS_DATE, CHG_NUM, UPSO_CD)  \n";
                    query +=" values((SELECT NVL(MAX(JOIN_SEQ), 0) + 1 JOIN_SEQ FROM GIBU.TBRA_UPSORTAL_INFO ), DECODE(:APPL_DAY_1,NULL,TO_CHAR(SYSDATE,'YYYYMMDD'),:APPL_DAY_2), :CHG_BRAN , TO_CHAR(SYSDATE,'YYYYMMDD'), :INSPRES_ID , :UPSO_GRAD , :USE_TIME , :MCHNDAESU , :MONPRNCFEE , :BSTYP_CD , SYSDATE, :CHG_NUM , :UPSO_CD )";
                    sobj.setSql(query);
                    sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
                    sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
                    sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
                    sobj.setString("USE_TIME", USE_TIME);               //���ð�
                    sobj.setString("UPSO_GRAD", UPSO_GRAD);               //���� ���
                    sobj.setString("APPL_DAY_2", APPL_DAY_2);               //���� ����
                    sobj.setString("APPL_DAY_1", APPL_DAY_1);               //���� ����
                    sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ��ȣ2
                    sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
                    sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    return sobj;
                }
                // �뷡�������
                public DOBJ CALLupso_regist_temp_INS32(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("INS32");
                    VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_INS32(dobj, dvobj);
                        qexe.DispSelectSql(sobj);
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
                private SQLObject SQLupso_regist_temp_INS32(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String CHG_DAY ="";        //���� ����
                    double GRAD_NUM = 0;        //�뷡�����
                    String INS_DATE ="";        //��� �Ͻ�
                    String   GRAD_GBN = dvobj.getRecord().get("GRAD_GBN");   //��� ����
                    String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
                    double   AREA = dvobj.getRecord().getDouble("AREA");   //����
                    double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
                    String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� ����
                    String   CHG_NUM = dobj.getRetObject("SEL29").getRecord().get("CHG_NUM");   //���� ��ȣ
                    String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //����� ID
                    String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" Insert into GIBU.TBRA_NOREBANG_INFO (INS_DATE, CHG_BRAN, INSPRES_ID, CHG_NUM, CHG_DAY, MCHNDAESU, AREA, UPSO_CD, BSTYP_CD, GRAD_GBN, GRAD_NUM)  \n";
                    query +=" values(SYSDATE, :CHG_BRAN , :INSPRES_ID , :CHG_NUM , TO_CHAR(SYSDATE,'YYYYMMDD'), :MCHNDAESU , :AREA , :UPSO_CD , :BSTYP_CD , :GRAD_GBN , (SELECT NVL(MAX(GRAD_NUM), 0) + 1 GRAD_NUM FROM GIBU.TBRA_NOREBANG_INFO WHERE UPSO_CD = :UPSO_CD))";
                    sobj.setSql(query);
                    sobj.setString("GRAD_GBN", GRAD_GBN);               //��� ����
                    sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
                    sobj.setDouble("AREA", AREA);               //����
                    sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
                    sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ����
                    sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
                    sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    return sobj;
                }
                // ���ұ⺻��������
                // ���ұ⺻������ �����Ѵ�. �� ���� ������ ���� ó���Ѵ�.
                public DOBJ CALLupso_regist_temp_UPD14(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("UPD14");
                    VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_UPD14(dobj, dvobj);
                        updcnt += qexe.executeUpdate(Conn, sobj);
                    }
                    rvobj = new VOBJ();
                    HashMap recordx = new HashMap();
                    recordx.put("UPDCNT",updcnt+"");
                    rvobj.addRecord(recordx);
                    rvobj.setName("UPD14") ;
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_UPD14(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String MOD_DATE ="";        //���� �Ͻ�
                    String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //���� �����ȣ
                    String   BIOWN_NUM = dvobj.getRecord().get("BIOWN_NUM");   //����� ��ȣ
                    String   PERMMSTR_PHONNUM = dvobj.getRecord().get("PERMMSTR_PHONNUM");   //�㰡�� ��ȭ��ȣ
                    String   PERMMSTR_ADDR1 = dvobj.getRecord().get("PERMMSTR_ADDR1");   //�㰡�� �ּ�1
                    String   MAIL_RCPT = dvobj.getRecord().get("MAIL_RCPT");   //���� ������
                    String   NEW_DAY = dvobj.getRecord().get("NEW_DAY");   //�ű� ����
                    String   MNG_ZIP = dvobj.getRecord().get("MNG_ZIP");   //���� �����ȣ
                    String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
                    String   MNGEMSTR_ADDR2 = dvobj.getRecord().get("MNGEMSTR_ADDR2");   //�濵�� �ּ�2
                    String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //�濵�� �̸�
                    String   PERMMSTR_ADDR2 = dvobj.getRecord().get("PERMMSTR_ADDR2");   //�㰡�� �ּ�2
                    String   CORP_NUM = dvobj.getRecord().get("CORP_NUM");   //���� ��ȣ
                    String   MNGEMSTR_REMAK = dvobj.getRecord().get("MNGEMSTR_REMAK");   //�濵�� ���
                    String   UPSO_REMAK = dvobj.getRecord().get("UPSO_REMAK");   //���� ���
                    String   UPSO_ADDR2 = dvobj.getRecord().get("UPSO_ADDR2");   //���� �ּ�2
                    double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
                    String   PAPER_CANC_YN = dvobj.getRecord().get("PAPER_CANC_YN");   //���� ��� ����
                    String   PERMMSTR_RESINUM = dvobj.getRecord().get("PERMMSTR_RESINUM");   //�㰡�� �ֹι�ȣ
                    String   PERMMSTR_NM = dvobj.getRecord().get("PERMMSTR_NM");   //�㰡�� �̸�
                    String   UPSO_NM = dvobj.getRecord().get("UPSO_NM");   //���� ��
                    String   PERMMSTR_REMAK = dvobj.getRecord().get("PERMMSTR_REMAK");   //�㰡�� ���
                    String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
                    String   MNGEMSTR_ZIP = dvobj.getRecord().get("MNGEMSTR_ZIP");   //�濵�� �����ȣ
                    String   MNGEMSTR_RESINUM = dvobj.getRecord().get("MNGEMSTR_RESINUM");   //�濵�� �ֹι�ȣ
                    String   MNGEMSTR_PHONNUM = dvobj.getRecord().get("MNGEMSTR_PHONNUM");   //�濵�� ��ȭ��ȣ
                    String   UPSO_STAT = dvobj.getRecord().get("UPSO_STAT");   //���� ����
                    String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
                    String   PERMMSTR_HPNUM = dvobj.getRecord().get("PERMMSTR_HPNUM");   //�㰡�� �ڵ�����ȣ
                    String   UPSO_ADDR1 = dvobj.getRecord().get("UPSO_ADDR1");   //���� �ּ�1
                    String   MNGEMSTR_ADDR1 = dvobj.getRecord().get("MNGEMSTR_ADDR1");   //�濵�� �ּ�1
                    String   BILL_ISS_YN = dvobj.getRecord().get("BILL_ISS_YN");   //��꼭 ���� ����
                    String   UPSO_PHON = dvobj.getRecord().get("UPSO_PHON");   //���� ��ȭ
                    String   PERMMSTR_ZIP = dvobj.getRecord().get("PERMMSTR_ZIP");   //�㰡�� �����ȣ
                    String   OPBI_DAY = dvobj.getRecord().get("OPBI_DAY");   //���� ����
                    String   PAYPRES_GBN = dvobj.getRecord().get("PAYPRES_GBN");   //������ ����
                    String   MNGEMSTR_HPNUM = dvobj.getRecord().get("MNGEMSTR_HPNUM");   //�濵�� �ڵ�����ȣ
                    String   MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //������ ID
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" Update GIBU.TBRA_UPSO  \n";
                    query +=" set MODPRES_ID=:MODPRES_ID , MNGEMSTR_HPNUM=:MNGEMSTR_HPNUM , PAYPRES_GBN=:PAYPRES_GBN , OPBI_DAY=:OPBI_DAY , PERMMSTR_ZIP=:PERMMSTR_ZIP , UPSO_PHON=:UPSO_PHON , BILL_ISS_YN=:BILL_ISS_YN , MNGEMSTR_ADDR1=:MNGEMSTR_ADDR1 , UPSO_ADDR1=:UPSO_ADDR1 , PERMMSTR_HPNUM=:PERMMSTR_HPNUM , STAFF_CD=:STAFF_CD , UPSO_STAT=:UPSO_STAT , MNGEMSTR_PHONNUM=:MNGEMSTR_PHONNUM , MNGEMSTR_RESINUM=:MNGEMSTR_RESINUM , MNGEMSTR_ZIP=:MNGEMSTR_ZIP , PERMMSTR_REMAK=:PERMMSTR_REMAK , UPSO_NM=:UPSO_NM , PERMMSTR_NM=:PERMMSTR_NM , PERMMSTR_RESINUM=:PERMMSTR_RESINUM , PAPER_CANC_YN=:PAPER_CANC_YN , MCHNDAESU=:MCHNDAESU , UPSO_ADDR2=:UPSO_ADDR2 , UPSO_REMAK=:UPSO_REMAK , MNGEMSTR_REMAK=:MNGEMSTR_REMAK ,  \n";
                    query +=" CORP_NUM=:CORP_NUM , PERMMSTR_ADDR2=:PERMMSTR_ADDR2 , MNGEMSTR_NM=:MNGEMSTR_NM , MNGEMSTR_ADDR2=:MNGEMSTR_ADDR2 , BSCON_CD=:BSCON_CD , MNG_ZIP=:MNG_ZIP , NEW_DAY=:NEW_DAY , MAIL_RCPT=:MAIL_RCPT , PERMMSTR_ADDR1=:PERMMSTR_ADDR1 , MOD_DATE=SYSDATE , PERMMSTR_PHONNUM=:PERMMSTR_PHONNUM , BIOWN_NUM=:BIOWN_NUM , UPSO_ZIP=:UPSO_ZIP  \n";
                    query +=" where UPSO_CD=:UPSO_CD";
                    sobj.setSql(query);
                    sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
                    sobj.setString("BIOWN_NUM", BIOWN_NUM);               //����� ��ȣ
                    sobj.setString("PERMMSTR_PHONNUM", PERMMSTR_PHONNUM);               //�㰡�� ��ȭ��ȣ
                    sobj.setString("PERMMSTR_ADDR1", PERMMSTR_ADDR1);               //�㰡�� �ּ�1
                    sobj.setString("MAIL_RCPT", MAIL_RCPT);               //���� ������
                    sobj.setString("NEW_DAY", NEW_DAY);               //�ű� ����
                    sobj.setString("MNG_ZIP", MNG_ZIP);               //���� �����ȣ
                    sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
                    sobj.setString("MNGEMSTR_ADDR2", MNGEMSTR_ADDR2);               //�濵�� �ּ�2
                    sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //�濵�� �̸�
                    sobj.setString("PERMMSTR_ADDR2", PERMMSTR_ADDR2);               //�㰡�� �ּ�2
                    sobj.setString("CORP_NUM", CORP_NUM);               //���� ��ȣ
                    sobj.setString("MNGEMSTR_REMAK", MNGEMSTR_REMAK);               //�濵�� ���
                    sobj.setString("UPSO_REMAK", UPSO_REMAK);               //���� ���
                    sobj.setString("UPSO_ADDR2", UPSO_ADDR2);               //���� �ּ�2
                    sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
                    sobj.setString("PAPER_CANC_YN", PAPER_CANC_YN);               //���� ��� ����
                    sobj.setString("PERMMSTR_RESINUM", PERMMSTR_RESINUM);               //�㰡�� �ֹι�ȣ
                    sobj.setString("PERMMSTR_NM", PERMMSTR_NM);               //�㰡�� �̸�
                    sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
                    sobj.setString("PERMMSTR_REMAK", PERMMSTR_REMAK);               //�㰡�� ���
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    sobj.setString("MNGEMSTR_ZIP", MNGEMSTR_ZIP);               //�濵�� �����ȣ
                    sobj.setString("MNGEMSTR_RESINUM", MNGEMSTR_RESINUM);               //�濵�� �ֹι�ȣ
                    sobj.setString("MNGEMSTR_PHONNUM", MNGEMSTR_PHONNUM);               //�濵�� ��ȭ��ȣ
                    sobj.setString("UPSO_STAT", UPSO_STAT);               //���� ����
                    sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
                    sobj.setString("PERMMSTR_HPNUM", PERMMSTR_HPNUM);               //�㰡�� �ڵ�����ȣ
                    sobj.setString("UPSO_ADDR1", UPSO_ADDR1);               //���� �ּ�1
                    sobj.setString("MNGEMSTR_ADDR1", MNGEMSTR_ADDR1);               //�濵�� �ּ�1
                    sobj.setString("BILL_ISS_YN", BILL_ISS_YN);               //��꼭 ���� ����
                    sobj.setString("UPSO_PHON", UPSO_PHON);               //���� ��ȭ
                    sobj.setString("PERMMSTR_ZIP", PERMMSTR_ZIP);               //�㰡�� �����ȣ
                    sobj.setString("OPBI_DAY", OPBI_DAY);               //���� ����
                    sobj.setString("PAYPRES_GBN", PAYPRES_GBN);               //������ ����
                    sobj.setString("MNGEMSTR_HPNUM", MNGEMSTR_HPNUM);               //�濵�� �ڵ�����ȣ
                    sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
                    return sobj;
                }
                // �뷡��������Է�
                public DOBJ CALLupso_regist_temp_INS11(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("INS11");
                    VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_INS11(dobj, dvobj);
                        qexe.DispSelectSql(sobj);
                        updcnt += qexe.executeUpdate(Conn, sobj);
                    }
                    rvobj = new VOBJ();
                    HashMap recordx = new HashMap();
                    recordx.put("UPDCNT",updcnt+"");
                    rvobj.addRecord(recordx);
                    rvobj.setName("INS11") ;
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_INS11(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String CHG_DAY ="";        //���� ����
                    double GRAD_NUM = 0;        //�뷡�����
                    String INS_DATE ="";        //��� �Ͻ�
                    String   GRAD_NUM_1 = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //�뷡�����
                    String   GRAD_GBN = dvobj.getRecord().get("GRAD_GBN");   //��� ����
                    String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
                    double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
                    double   AREA = dobj.getRetObject("S1").getRecord().getDouble("AREA");   //���� ����
                    String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� ����
                    String   CHG_NUM = dobj.getRetObject("SEL22").getRecord().get("CHG_NUM");   //���� ��ȣ
                    String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //����� ID
                    String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" Insert into GIBU.TBRA_NOREBANG_INFO (INS_DATE, CHG_BRAN, INSPRES_ID, CHG_NUM, CHG_DAY, MCHNDAESU, AREA, UPSO_CD, BSTYP_CD, GRAD_GBN, GRAD_NUM)  \n";
                    query +=" values(SYSDATE, :CHG_BRAN , :INSPRES_ID , :CHG_NUM , TO_CHAR(SYSDATE,'YYYYMMDD'), :MCHNDAESU , :AREA , :UPSO_CD , :BSTYP_CD , :GRAD_GBN , (SELECT NVL(MAX(GRAD_NUM), 0) + 1 GRAD_NUM FROM GIBU.TBRA_NOREBANG_INFO WHERE UPSO_CD = :GRAD_NUM_1))";
                    sobj.setSql(query);
                    sobj.setString("GRAD_NUM_1", GRAD_NUM_1);               //�뷡�����
                    sobj.setString("GRAD_GBN", GRAD_GBN);               //��� ����
                    sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
                    sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
                    sobj.setDouble("AREA", AREA);               //���� ����
                    sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ����
                    sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
                    sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    return sobj;
                }
                // �������������������
                public DOBJ CALLupso_regist_temp_SEL35(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    dobj.setRtnname("SEL35");
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("SEL35");
                    VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
                    SQLObject  sobj = SQLupso_regist_temp_SEL35(dobj, dvobj);
                    VOBJ rvobj = qexe.executeQuery(Conn, sobj);
                    rvobj.setName("SEL35");
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_SEL35(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" SELECT  UPSO_CD  ,  CHG_DAY  ,  CHG_NUM  ,  CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
                    query +=" AND  JOIN_SEQ  =  (   \n";
                    query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
                    query +=" AND  NVL(APPL_DAY,  CHG_DAY)  <=  TO_CHAR(SYSDATE,  'YYYYMMDD')  ) ";
                    sobj.setSql(query);
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    return sobj;
                }
                // ��������������
                public DOBJ CALLupso_regist_temp_UPD34(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("UPD34");
                    VOBJ dvobj = dobj.getRetObject("SEL35");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_UPD34(dobj, dvobj);
                        updcnt += qexe.executeUpdate(Conn, sobj);
                    }
                    rvobj = new VOBJ();
                    HashMap recordx = new HashMap();
                    recordx.put("UPDCNT",updcnt+"");
                    rvobj.addRecord(recordx);
                    rvobj.setName("UPD34") ;
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_UPD34(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //������ ID
                    String MOD_DATE ="";        //���� �Ͻ�
                    String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
                    String   CHG_DAY = dvobj.getRecord().get("CHG_DAY");   //���� ����
                    String   CHG_NUM = dvobj.getRecord().get("CHG_NUM");   //���� ��ȣ
                    String   CHG_BRAN = dvobj.getRecord().get("CHG_BRAN");   //���� ����
                    double   MCHNDAESU = dobj.getRetObject("R").getRecord().getDouble("MCHNDAESU");   //�����
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" Update  \n";
                    query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
                    query +=" set MCHNDAESU=:MCHNDAESU  \n";
                    query +=" where CHG_BRAN=:CHG_BRAN  \n";
                    query +=" and CHG_NUM=:CHG_NUM  \n";
                    query +=" and CHG_DAY=:CHG_DAY  \n";
                    query +=" and UPSO_CD=:UPSO_CD";
                    sobj.setSql(query);
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    sobj.setString("CHG_DAY", CHG_DAY);               //���� ����
                    sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
                    sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ����
                    sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
                    return sobj;
                }
                // ���� �¿������� ����
                public DOBJ CALLupso_regist_temp_XIUD41(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("XIUD41");
                    VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_XIUD41(dobj, dvobj);
                        qexe.DispSelectSql(sobj);
                        updcnt += qexe.executeUpdate(Conn, sobj);
                    }
                    rvobj = new VOBJ();
                    HashMap recordx = new HashMap();
                    recordx.put("UPDCNT",updcnt+"");
                    rvobj.addRecord(recordx);
                    rvobj.setName("XIUD41");
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_XIUD41(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String   BEFORE_UPSO_CD = dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
                    String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //����� ID
                    String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" INSERT INTO GIBU.TBRA_UPSO_ONOFF_INFO ( UPSO_CD , MODEL_CD , ONOFF_GBN , ACMCN_DAESU , INSPRES_ID , INS_DATE ) SELECT :UPSO_CD , MODEL_CD , ONOFF_GBN , ACMCN_DAESU , :INSPRES_ID , SYSDATE FROM GIBU.TBRA_UPSO_ONOFF_INFO WHERE UPSO_CD = :BEFORE_UPSO_CD";
                    sobj.setSql(query);
                    sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
                    sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    return sobj;
                }
                // ���� ���ֱ����� ����
                public DOBJ CALLupso_regist_temp_XIUD42(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("XIUD42");
                    VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_XIUD42(dobj, dvobj);
                        qexe.DispSelectSql(sobj);
                        updcnt += qexe.executeUpdate(Conn, sobj);
                    }
                    rvobj = new VOBJ();
                    HashMap recordx = new HashMap();
                    recordx.put("UPDCNT",updcnt+"");
                    rvobj.addRecord(recordx);
                    rvobj.setName("XIUD42");
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_XIUD42(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String   BEFORE_UPSO_CD = dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
                    String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //����� ID
                    String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" INSERT INTO GIBU.TBRA_UPSO_ACMCN_INFO ( UPSO_CD , MODEL_CD , ACMCN_DAESU , INSPRES_ID , INS_DATE ) SELECT :UPSO_CD , MODEL_CD , ACMCN_DAESU , :INSPRES_ID , SYSDATE FROM GIBU.TBRA_UPSO_ACMCN_INFO WHERE UPSO_CD = :BEFORE_UPSO_CD";
                    sobj.setSql(query);
                    sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
                    sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    return sobj;
                }
                // ��� ����üũ
                public DOBJ CALLupso_regist_temp_SEL36(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    dobj.setRtnname("SEL36");
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("SEL36");
                    VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
                    SQLObject  sobj = SQLupso_regist_temp_SEL36(dobj, dvobj);
                    VOBJ rvobj = qexe.executeQuery(Conn, sobj);
                    rvobj.setName("SEL36");
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_SEL36(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String VISIT_DAY ="";        //�湮 ����
                    String   JOB_GBN ="U";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
                    String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
                    query +=" AND  VISIT_DAY  =  TO_CHAR(SYSDATE,'YYYYMMDD')   \n";
                    query +=" AND  JOB_GBN  =  :JOB_GBN ";
                    sobj.setSql(query);
                    sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    return sobj;
                }
                // ���� ���긮 ���� ����
                public DOBJ CALLupso_regist_temp_XIUD43(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("XIUD43");
                    VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_XIUD43(dobj, dvobj);
                        qexe.DispSelectSql(sobj);
                        updcnt += qexe.executeUpdate(Conn, sobj);
                    }
                    rvobj = new VOBJ();
                    HashMap recordx = new HashMap();
                    recordx.put("UPDCNT",updcnt+"");
                    rvobj.addRecord(recordx);
                    rvobj.setName("XIUD43");
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_XIUD43(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String   BEFORE_UPSO_CD = dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
                    String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //����� ID
                    String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" INSERT INTO GIBU.TBRA_UPSO_AUBRY_INFO ( UPSO_CD , MODEL_CD , MODEL_NM , MCHN_COMPY , MCHN_COMPYNM , ACMCN_DAESU , INSPRES_ID , INS_DATE ) SELECT :UPSO_CD , MODEL_CD , MODEL_NM , MCHN_COMPY , MCHN_COMPYNM , ACMCN_DAESU , :INSPRES_ID , SYSDATE FROM GIBU.TBRA_UPSO_AUBRY_INFO WHERE UPSO_CD = :BEFORE_UPSO_CD";
                    sobj.setSql(query);
                    sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
                    sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    return sobj;
                }
                // ���� �¿��� ���� ����
                public DOBJ CALLupso_regist_temp_XIUD50(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("XIUD50");
                    VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_XIUD50(dobj, dvobj);
                        qexe.DispSelectSql(sobj);
                        updcnt += qexe.executeUpdate(Conn, sobj);
                    }
                    rvobj = new VOBJ();
                    HashMap recordx = new HashMap();
                    recordx.put("UPDCNT",updcnt+"");
                    rvobj.addRecord(recordx);
                    rvobj.setName("XIUD50");
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_XIUD50(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String   BEFORE_UPSO_CD = dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
                    String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" UPDATE GIBU.TBRA_UPSO  \n";
                    query +=" SET ONOFF_GBN = ( SELECT ONOFF_GBN FROM GIBU.TBRA_UPSO  \n";
                    query +=" WHERE UPSO_CD =  \n";
                    query +=" :BEFORE_UPSO_CD )  \n";
                    query +=" WHERE UPSO_CD = :UPSO_CD";
                    sobj.setSql(query);
                    sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    return sobj;
                }
                // ���ҹ湮�ű�
                // ���Ҹ��� ����Ǿ��� ��� ���ҹ湮 ���̺� ��� ����
                public DOBJ CALLupso_regist_temp_INS21(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("INS21");
                    VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_INS21(dobj, dvobj);
                        updcnt += qexe.executeUpdate(Conn, sobj);
                    }
                    rvobj = new VOBJ();
                    HashMap recordx = new HashMap();
                    recordx.put("UPDCNT",updcnt+"");
                    rvobj.addRecord(recordx);
                    rvobj.setName("INS21") ;
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_INS21(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String INS_DATE ="";        //��� �Ͻ�
                    String VISIT_DAY ="";        //�湮 ����
                    String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
                    String   CONSPRES ="��Ÿ";   //�����
                    String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //����� ID
                    String   JOB_GBN ="U";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
                    String   REMAK ="[���Ҹ���]";   //���
                    int   VISIT_SEQ = 1;   //�湮�ڼ���
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
                    query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , TO_CHAR(SYSDATE,'YYYYMMDD'), :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
                    sobj.setSql(query);
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    sobj.setString("CONSPRES", CONSPRES);               //�����
                    sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
                    sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
                    sobj.setString("REMAK", REMAK);               //���
                    sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
                    return sobj;
                }
                // ���ҹ湮�޸���
                public DOBJ CALLupso_regist_temp_INS39(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("INS39");
                    VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_INS39(dobj, dvobj);
                        updcnt += qexe.executeUpdate(Conn, sobj);
                    }
                    rvobj = new VOBJ();
                    HashMap recordx = new HashMap();
                    recordx.put("UPDCNT",updcnt+"");
                    rvobj.addRecord(recordx);
                    rvobj.setName("INS39") ;
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_INS39(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String INS_DATE ="";        //��� �Ͻ�
                    String VISIT_DAY ="";        //�湮 ����
                    int  VISIT_NUM = 0;        //�湮 ��ȣ
                    String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
                    String   VISIT_NUM_1 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //�湮 ��ȣ
                    String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //����� ID
                    String   JOB_GBN ="U";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
                    String   REMAK ="[���Ҹ���]"+dvobj.getRecord().get("BEFORE_UPSO_NM")+"-->"+dvobj.getRecord().get("UPSO_NM");   //���
                    int   VISIT_SEQ = 1;   //�湮�ڼ���
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
                    query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , TO_CHAR(SYSDATE,'YYYYMMDD'), :JOB_GBN , (SELECT NVL(MAX(VISIT_NUM),0)+1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = TO_CHAR(SYSDATE,'YYYYMMDD') AND UPSO_CD = :VISIT_NUM_1 AND JOB_GBN = 'U' ), :UPSO_CD , :REMAK )";
                    sobj.setSql(query);
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //�湮 ��ȣ
                    sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
                    sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
                    sobj.setString("REMAK", REMAK);               //���
                    sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
                    return sobj;
                }
                // ���ҹ湮�޸���
                public DOBJ CALLupso_regist_temp_INS38(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("INS38");
                    VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
                    SQLObject  sobj = null;
                    VOBJ       rvobj= null;
                    int        updcnt =0;
                    dvobj.first();
                    while(dvobj.next())
                    {
                        sobj = SQLupso_regist_temp_INS38(dobj, dvobj);
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
                private SQLObject SQLupso_regist_temp_INS38(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String INS_DATE ="";        //��� �Ͻ�
                    String VISIT_DAY ="";        //�湮 ����
                    int  VISIT_NUM = 0;        //�湮 ��ȣ
                    String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
                    String   VISIT_NUM_1 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //�湮 ��ȣ
                    String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //����� ID
                    String   JOB_GBN ="U";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
                    String   REMAK ="[���Ҹ���]"+dvobj.getRecord().get("BEFORE_UPSO_NM")+"-->"+dvobj.getRecord().get("UPSO_NM");   //���
                    int   VISIT_SEQ = 1;   //�湮�ڼ���
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
                    query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , TO_CHAR(SYSDATE,'YYYYMMDD'), :JOB_GBN , (SELECT NVL(MAX(VISIT_NUM),0)+1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = TO_CHAR(SYSDATE,'YYYYMMDD') AND UPSO_CD = :VISIT_NUM_1 AND JOB_GBN = 'U' ), :UPSO_CD , :REMAK )";
                    sobj.setSql(query);
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //�湮 ��ȣ
                    sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
                    sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
                    sobj.setString("REMAK", REMAK);               //���
                    sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
                    return sobj;
                }
                // ���һ�������ȸ
                // ���ǿ� �´� ���һ������� ��ȸ�Ѵ�.
                public DOBJ CALLupso_regist_temp_SEL17(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    dobj.setRtnname("SEL17");
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("SEL17");
                    VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
                    SQLObject  sobj = SQLupso_regist_temp_SEL17(dobj, dvobj);
                    qexe.DispSelectSql(sobj);
                    VOBJ rvobj = qexe.executeQuery(Conn, sobj);
                    rvobj.setName("SEL17");
                    rvobj.setRetcode(1);
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_SEL17(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String  UPSO_CD="";          //���� �ڵ�
                    if( ( dobj.getRetObject("S").getRecord().get("IUDFLAG").equals("I") ))
                    {
                        UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");
                    }
                    else
                    {
                        UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");
                    }
                    String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" SELECT  A.UPSO_CD  ,  A.UPSO_NM  ,  A.BIOWN_NUM  ,  A.UPSO_PHON  ,  A.UPSO_ZIP  ,  A.UPSO_ADDR1  ,  A.UPSO_ADDR2  ,  A.MNGEMSTR_NM  ,  A.MNGEMSTR_PHONNUM  ,  A.MNGEMSTR_RESINUM  ,  A.MNGEMSTR_HPNUM  ,  A.MNGEMSTR_ZIP  ,  A.MNGEMSTR_ADDR1  ,  A.MNGEMSTR_ADDR2  ,  A.MNGEMSTR_REMAK  ,  A.PERMMSTR_NM  ,  A.PERMMSTR_PHONNUM  ,  A.PERMMSTR_RESINUM  ,  A.PERMMSTR_HPNUM  ,  A.PERMMSTR_ZIP  ,  A.PERMMSTR_ADDR1  ,  A.PERMMSTR_ADDR2  ,  A.PERMMSTR_REMAK  ,  A.OPBI_DAY  ,  A.PAYPRES_GBN  ,  A.NEW_DAY  ,  A.MAIL_RCPT  ,  A.PAPER_CANC_YN  ,  A.STAFF_CD  ,  D.HAN_NM  STAFF_NM  ,  A.UPSO_STAT  ,  A.BEFORE_UPSO_CD  ,  TRIM(B.BSTYP_CD)  ||  B.UPSO_GRAD  GRAD  ,  TRIM(B.BSTYP_CD)  BSTYP_CD  ,  B.UPSO_GRAD  ,  B.MONPRNCFEE  ,  B.USE_TIME  ,  TRIM(B.B_BSTYP_CD)  ||  B.B_UPSO_GRAD  B_GRAD  ,  B.B_BSTYP_CD  ,  B.B_UPSO_GRAD  ,  B.B_MONPRNCFEE  ,  B.B_USE_TIME  ,  B.GRADNM  ,  B.B_GRADNM  ,  B.CHG_DAY  ,  B.CHG_NUM  ,  B.CHG_BRAN  ,  B.B_CHG_DAY  ,  B.B_CHG_NUM  ,  B.B_CHG_BRAN  ,  TO_CHAR(A.INS_DATE,'YYYYMMDD')  INS_DATE  ,  B.B_UPSO_NM  ,  C.MCHNDAESU  ,  C.B_MCHNDAESU  ,  DECODE(A.CLSBS_YRMN,  NULL,  A.CLSBS_YRMN,  A.CLSBS_YRMN  ||  '01')  CLSBS_YRMN  ,  A.CLIENT_NUM  ,  A.BSCON_CD  ,  E.BSCONHAN_NM  ,  A.BILL_ISS_YN  ,  A.UPSO_REMAK  ,  A.BRAN_CD  ,  A.MNG_ZIP  ,  A.CORP_NUM  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
                    query +=" SELECT  MAX(DECODE(DUMMY,  '1',  UPSO_CD  ))  UPSO_CD  ,  MAX(DECODE(DUMMY,  '1',  UPSO_GRAD  ))  UPSO_GRAD  ,  MAX(DECODE(DUMMY,  '1',  MONPRNCFEE))  MONPRNCFEE  ,  MAX(DECODE(DUMMY,  '1',  USE_TIME  ))  USE_TIME  ,  MAX(DECODE(DUMMY,  '1',  BSTYP_CD  ))  BSTYP_CD  ,  MAX(DECODE(DUMMY,  '1',  GRADNM  ))  GRADNM  ,  MAX(DECODE(DUMMY,  '1',  CHG_DAY))  CHG_DAY  ,  MAX(DECODE(DUMMY,  '1',  CHG_NUM))  CHG_NUM  ,  MAX(DECODE(DUMMY,  '1',  CHG_BRAN  ))  CHG_BRAN  ,  MAX(DECODE(DUMMY,  '2',  UPSO_CD  ))  B_UPSO_CD  ,  MAX(DECODE(DUMMY,  '2',  UPSO_GRAD  ))  B_UPSO_GRAD  ,  MAX(DECODE(DUMMY,  '2',  MONPRNCFEE))  B_MONPRNCFEE  ,  MAX(DECODE(DUMMY,  '2',  USE_TIME  ))  B_USE_TIME  ,  MAX(DECODE(DUMMY,  '2',  BSTYP_CD  ))  B_BSTYP_CD  ,  MAX(DECODE(DUMMY,  '2',  GRADNM  ))  B_GRADNM  ,  MAX(DECODE(DUMMY,  '2',  UPSO_NM  ))  B_UPSO_NM  ,  MAX(DECODE(DUMMY,  '2',  CHG_DAY))  B_CHG_DAY  ,  MAX(DECODE(DUMMY,  '2',  CHG_NUM))  B_CHG_NUM  ,  MAX(DECODE(DUMMY,  '2',  CHG_BRAN  ))  B_CHG_BRAN  FROM  (   \n";
                    query +=" SELECT  *  FROM  (   \n";
                    query +=" SELECT  '1'  DUMMY  ,  A.UPSO_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.USE_TIME  ,  A.BSTYP_CD  ,  ''  UPSO_NM  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
                    query +=" AND  B.BSTYP_CD  =  A.BSTYP_CD   \n";
                    query +=" AND  B.GRAD_GBN  =  A.UPSO_GRAD  ORDER  BY  A.CHG_DAY  DESC  ,A.CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  UNION  ALL   \n";
                    query +=" SELECT  *  FROM  (   \n";
                    query +=" SELECT  '2'  DUMMY  ,  A.UPSO_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.USE_TIME  ,  A.BSTYP_CD  ,  C.UPSO_NM  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  (   \n";
                    query +=" SELECT  BEFORE_UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD)   \n";
                    query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
                    query +=" AND  B.BSTYP_CD  =  A.BSTYP_CD   \n";
                    query +=" AND  B.GRAD_GBN  =  A.UPSO_GRAD  ORDER  BY  A.CHG_DAY  DESC  ,A.CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  )  )  B  ,  (   \n";
                    query +=" SELECT  MAX(MCHNDAESU)  MCHNDAESU  ,  MAX(B_MCHNDAESU)  B_MCHNDAESU  ,  MAX(UPSO_CD)  UPSO_CD  FROM  (   \n";
                    query +=" SELECT  MCHNDAESU  MCHNDAESU  ,  NULL  B_MCHNDAESU  ,  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
                    query +=" SELECT  NULL  ,  MCHNDAESU  B_MCHNDAESU  ,  NULL  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  (   \n";
                    query +=" SELECT  BEFORE_UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD  )  )  )  C  ,  INSA.TINS_MST01  D  ,  FIDU.TLEV_BSCON  E  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
                    query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
                    query +=" AND  B.UPSO_CD(+)  =  A.UPSO_CD   \n";
                    query +=" AND  C.UPSO_CD(+)  =  A.UPSO_CD   \n";
                    query +=" AND  D.STAFF_NUM  (+)  =  A.STAFF_CD   \n";
                    query +=" AND  E.BSCON_CD(+)  =  A.BSCON_CD ";
                    sobj.setSql(query);
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
                    return sobj;
                }
                // �뷡���
                public DOBJ CALLupso_regist_temp_SEL24(Connection Conn, DOBJ dobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    dobj.setRtnname("SEL24");
                    QExecutor qexe = new QExecutor(dobj);
                    dobj.setRtnname("SEL24");
                    VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
                    SQLObject  sobj = SQLupso_regist_temp_SEL24(dobj, dvobj);
                    VOBJ rvobj = qexe.executeQuery(Conn, sobj);
                    rvobj.setName("SEL24");
                    rvobj.setRetcode(1);
                    dobj.setRetObject(rvobj);
                    return dobj;
                }
                private SQLObject SQLupso_regist_temp_SEL24(DOBJ dobj, VOBJ dvobj) throws Exception
                {
                    WizUtil wutil = new WizUtil(dobj);
                    String   CHG_BRAN = dobj.getRetObject("SEL17").getRecord().get("CHG_BRAN");   //���� ����
                    String   CHG_NUM = dobj.getRetObject("SEL17").getRecord().get("CHG_NUM");   //���� ��ȣ
                    String   CHG_DAY = dobj.getRetObject("SEL17").getRecord().get("CHG_DAY");   //���� ����
                    String  UPSO_CD="";          //���� �ڵ�
                    if( ( dobj.getRetObject("S").getRecord().get("IUDFLAG").equals("I") ))
                    {
                        UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");
                    }
                    else
                    {
                        UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");
                    }
                    String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
                    SQLObject sobj = new SQLObject();
                    String    query="";
                    query +=" SELECT  A.UPSO_CD  ,  TRIM(A.BSTYP_CD)  ||  A.GRAD_GBN  GRAD  ,  A.AREA  ,  A.MCHNDAESU  ,  B.STNDAMT  ,  B.GRADNM  ,  A.MCHNDAESU  *  B.STNDAMT  AMT  FROM  GIBU.TBRA_NOREBANG_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
                    query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
                    query +=" AND  C.BRAN_CD  =  :BRAN_CD   \n";
                    query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD   \n";
                    query +=" AND  A.GRAD_GBN  =  B.GRAD_GBN  AND	A.CHG_DAY  =  :CHG_DAY  AND	A.CHG_NUM  =  :CHG_NUM  AND	A.CHG_BRAN  =  :CHG_BRAN ";
                    sobj.setSql(query);
                    sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ����
                    sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
                    sobj.setString("CHG_DAY", CHG_DAY);               //���� ����
                    sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
                    sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
                    return sobj;
                }
                //##**$$upso_regist_temp
                //##**$$end
            }
