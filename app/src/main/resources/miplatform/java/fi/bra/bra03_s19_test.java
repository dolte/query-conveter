
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s19_test
{
    public bra03_s19_test()
    {
    }
    //##**$$upload_test
    /*
    */
    public DOBJ CTLupload_test(DOBJ dobj)
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
            dobj  = CALLupload_test_DEL1(Conn, dobj);           //  ��������Ÿ����
            dobj  = CALLupload_test_XIUD16(Conn, dobj);           //  ����������û������
            dobj  = CALLupload_test_MPD2(Conn, dobj);           //  �Ǻ�ó��
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
    public DOBJ CTLupload_test( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupload_test_DEL1(Conn, dobj);           //  ��������Ÿ����
        dobj  = CALLupload_test_XIUD16(Conn, dobj);           //  ����������û������
        dobj  = CALLupload_test_MPD2(Conn, dobj);           //  �Ǻ�ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ��������Ÿ����
    // ó�����ڿ� �ش��ϴ� ��������Ÿ���� �����Ѵ�.
    public DOBJ CALLupload_test_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("DEL1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_test_DEL1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_test_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dvobj.getRecord().get("PROC_DAY");   //ó�� ����
        String   GBN ="22";   //����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_UPSO_AUTORSLT  \n";
        query +=" where GBN=:GBN  \n";
        query +=" and PROC_DAY=:PROC_DAY";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        sobj.setString("GBN", GBN);               //����
        return sobj;
    }
    // ����������û������
    // ó�����ڿ� �ش��ϴ� ����������û���� �����Ѵ�.
    public DOBJ CALLupload_test_XIUD16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD16");
        VOBJ dvobj = dobj.getRetObject("S1");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_test_XIUD16(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD16");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_test_XIUD16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE GIBU.TBRA_UPSO_AUTO_APPLICATION A  \n";
        query +=" WHERE A.PROC_DAY = :PROC_DAY  \n";
        query +=" AND A.RECEPTION_GBN IN ('9')";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        return sobj;
    }
    // �Ǻ�ó��
    public DOBJ CALLupload_test_MPD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD2");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MPD2");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupload_test_SEL70(Conn, dobj);           //  SEQ_NUM ȹ��
                dobj  = CALLupload_test_SEL3(Conn, dobj);           //  ����üũ
                if( dobj.getRetObject("R").getRecord().get("APPTN_GBN").equals("01"))
                {
                    dobj  = CALLupload_test_INS42(Conn, dobj);           //  ������ȸ����
                }
                else if(!dobj.getRetObject("SEL3").getRecord().get("UPSO_CD").equals("") && ( dobj.getRetObject("R").getRecord().get("APPTN_GBN").equals("03") || dobj.getRetObject("R").getRecord().get("APPTN_GBN").equals("04") ))
                {
                    dobj  = CALLupload_test_SEL13(Conn, dobj);           //  �ֹι�ȣüũ
                    if(( !dobj.getRetObject("SEL3").getRecord().get("RESINUM").equals(dobj.getRetObject("R").getRecord().get("RESINUM")) ) && ( !dobj.getRetObject("SEL13").getRecord().get("RESINUM").equals(dobj.getRetObject("R").getRecord().get("RESINUM")) ))
                    {
                        dobj  = CALLupload_test_INS15(Conn, dobj);           //  �ֹι�ȣ����
                        dobj  = CALLupload_test_INS16(Conn, dobj);           //  ��û�� ���
                    }
                    else
                    {
                        dobj  = CALLupload_test_SEL17(Conn, dobj);           //  ��������üũ
                        if( dobj.getRetObject("SEL17").getRecord().getDouble("CNT") > 0)
                        {
                            dobj  = CALLupload_test_INS19(Conn, dobj);           //  �̷¸��� ���
                            dobj  = CALLupload_test_OSP20(Conn, dobj);           //  û�� ������ ����
                        }
                        else
                        {
                            dobj  = CALLupload_test_INS21(Conn, dobj);           //  ������������
                            dobj  = CALLupload_test_INS22(Conn, dobj);           //  ��û�� ���
                        }
                    }
                }
                else if(!dobj.getRetObject("SEL3").getRecord().get("UPSO_CD").equals("") && ( dobj.getRetObject("R").getRecord().get("APPTN_GBN").equals("02") || dobj.getRetObject("R").getRecord().get("APPTN_GBN").equals("08") || dobj.getRetObject("R").getRecord().get("APPTN_GBN").equals("09") ))
                {
                    dobj  = CALLupload_test_INS45(Conn, dobj);           //  �̷¸��� ���
                    dobj  = CALLupload_test_OSP46(Conn, dobj);           //  û�� ������ ����
                }
                else
                {
                    dobj  = CALLupload_test_INS9(Conn, dobj);           //  ������ȸ����
                }
            }
        }
        return dobj;
    }
    // SEQ_NUM ȹ��
    public DOBJ CALLupload_test_SEL70(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL70");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL70");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupload_test_SEL70(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL70");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_test_SEL70(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SEQ_NUM = dobj.getRetObject("R").getRecord().get("SEQ_NUM");   //�Ϸ� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(TO_NUMBER(:SEQ_NUM)  +  90000000)  AS  SEQ_NUM  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("SEQ_NUM", SEQ_NUM);               //�Ϸ� ��ȣ
        return sobj;
    }
    // ����üũ
    public DOBJ CALLupload_test_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupload_test_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_test_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHGATR_PAYPRES_NUM = dobj.getRetObject("R").getRecord().get("CHGATR_PAYPRES_NUM");   //������ ������ ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(UPSO_CD),'')  UPSO_CD  ,  NVL(MAX(MNGEMSTR_RESINUM),'')  RESINUM  FROM  GIBU.TBRA_UPSO  WHERE  CLIENT_NUM  =  :CHGATR_PAYPRES_NUM ";
        sobj.setSql(query);
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //������ ������ ��ȣ
        return sobj;
    }
    // ������ȸ����
    // �����ڹ�ȣ �����ǰ԰� Ʋ�� ���
    public DOBJ CALLupload_test_INS42(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS42");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS42");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_test_INS42(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS42") ;
        rvobj.Println("INS42");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_test_INS42(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //����ó ����
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //��û ����
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   RECPTNUM = dvobj.getRecord().get("RECPTNUM");   //������ȣ
        String   PAY_BANK_CD = dvobj.getRecord().get("PAY_BANK_CD");   //���� ���� �ڵ�
        String   PAY_KND = dvobj.getRecord().get("PAY_KND");   //��� ����
        String   PAY_REQDAY = dvobj.getRecord().get("PAY_REQDAY");   //���� �������
        String   CHGBFR_PAYPRES_NUM = dvobj.getRecord().get("CHGBFR_PAYPRES_NUM");   //������ ������ ��ȣ
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //���� ������
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //��� ���� ��ȣ
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //������ ������ ��ȣ
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //������ �ڵ�
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //������ ��ȭ��ȣ
        String   APPTN_RSLT = dvobj.getRecord().get("APPTN_RSLT");   //��û ���
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //��û ����
        String   ERR_GBN ="";   //���� ����
        String   GBN ="22";   //����
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //ó�� ����
        String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");   //������ȣ
        String   UPSO_CD ="";   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYER_PHONNUM, GBN, PAYPRES_NM, RECPT_BANK_CD, CHGATR_PAYPRES_NUM, PAY_ACCN_NUM, PROC_DAY, BEGNG_RELSDAY, SEQ_NUM, CHGBFR_PAYPRES_NUM, PAY_REQDAY, PAY_KND, APPTN_DAY, PAY_BANK_CD, RECPTNUM, UPSO_CD, RESINUM, ERR_GBN, APPTN_GBN, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYER_PHONNUM , :GBN , :PAYPRES_NM , :RECPT_BANK_CD , :CHGATR_PAYPRES_NUM , :PAY_ACCN_NUM , :PROC_DAY , :BEGNG_RELSDAY , :SEQ_NUM , :CHGBFR_PAYPRES_NUM , :PAY_REQDAY , :PAY_KND , :APPTN_DAY , :PAY_BANK_CD , :RECPTNUM , :UPSO_CD , :RESINUM , :ERR_GBN , :APPTN_GBN , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //����ó ����
        sobj.setString("APPTN_GBN", APPTN_GBN);               //��û ����
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("RECPTNUM", RECPTNUM);               //������ȣ
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //���� ���� �ڵ�
        sobj.setString("PAY_KND", PAY_KND);               //��� ����
        sobj.setString("PAY_REQDAY", PAY_REQDAY);               //���� �������
        sobj.setString("CHGBFR_PAYPRES_NUM", CHGBFR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("BEGNG_RELSDAY", BEGNG_RELSDAY);               //���� ������
        sobj.setString("PAY_ACCN_NUM", PAY_ACCN_NUM);               //��� ���� ��ȣ
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("RECPT_BANK_CD", RECPT_BANK_CD);               //������ �ڵ�
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("PAYER_PHONNUM", PAYER_PHONNUM);               //������ ��ȭ��ȣ
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //��û ���
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("ERR_GBN", ERR_GBN);               //���� ����
        sobj.setString("GBN", GBN);               //����
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        sobj.setString("SEQ_NUM", SEQ_NUM);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �ֹι�ȣüũ
    public DOBJ CALLupload_test_SEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL13");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupload_test_SEL13(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL13");
        rvobj.Println("SEL13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_test_SEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RESINUM = dobj.getRetObject("R").getRecord().get("RESINUM");   //�ֹι�ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(RESINUM),  '  ')  RESINUM  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  RESINUM  =  :RESINUM ";
        sobj.setSql(query);
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        return sobj;
    }
    // �ֹι�ȣ����
    // APPTN_RSLT = ERR_GBN �����ڵ� ���Ͻ� �Ѵ�."03"��󳳺��� ����
    public DOBJ CALLupload_test_INS15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS15");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_test_INS15(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS15") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_test_INS15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //����ó ����
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //��û ����
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   RECPTNUM = dvobj.getRecord().get("RECPTNUM");   //������ȣ
        String   PAY_BANK_CD = dvobj.getRecord().get("PAY_BANK_CD");   //���� ���� �ڵ�
        String   PAY_KND = dvobj.getRecord().get("PAY_KND");   //��� ����
        String   PAY_REQDAY = dvobj.getRecord().get("PAY_REQDAY");   //���� �������
        String   CHGBFR_PAYPRES_NUM = dvobj.getRecord().get("CHGBFR_PAYPRES_NUM");   //������ ������ ��ȣ
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //���� ������
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //��� ���� ��ȣ
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //������ ������ ��ȣ
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //������ �ڵ�
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //������ ��ȭ��ȣ
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //��û ����
        String   APPTN_RSLT ="03";   //��û ���
        String   ERR_GBN ="0002";   //���� ����
        String   GBN ="22";   //����
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //ó�� ����
        String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");   //�Ϸ� ��ȣ
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYER_PHONNUM, GBN, PAYPRES_NM, RECPT_BANK_CD, CHGATR_PAYPRES_NUM, PAY_ACCN_NUM, PROC_DAY, BEGNG_RELSDAY, SEQ_NUM, CHGBFR_PAYPRES_NUM, PAY_REQDAY, PAY_KND, APPTN_DAY, PAY_BANK_CD, RECPTNUM, UPSO_CD, RESINUM, ERR_GBN, APPTN_GBN, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYER_PHONNUM , :GBN , :PAYPRES_NM , :RECPT_BANK_CD , :CHGATR_PAYPRES_NUM , :PAY_ACCN_NUM , :PROC_DAY , :BEGNG_RELSDAY , :SEQ_NUM , :CHGBFR_PAYPRES_NUM , :PAY_REQDAY , :PAY_KND , :APPTN_DAY , :PAY_BANK_CD , :RECPTNUM , :UPSO_CD , :RESINUM , :ERR_GBN , :APPTN_GBN , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //����ó ����
        sobj.setString("APPTN_GBN", APPTN_GBN);               //��û ����
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("RECPTNUM", RECPTNUM);               //������ȣ
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //���� ���� �ڵ�
        sobj.setString("PAY_KND", PAY_KND);               //��� ����
        sobj.setString("PAY_REQDAY", PAY_REQDAY);               //���� �������
        sobj.setString("CHGBFR_PAYPRES_NUM", CHGBFR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("BEGNG_RELSDAY", BEGNG_RELSDAY);               //���� ������
        sobj.setString("PAY_ACCN_NUM", PAY_ACCN_NUM);               //��� ���� ��ȣ
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("RECPT_BANK_CD", RECPT_BANK_CD);               //������ �ڵ�
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("PAYER_PHONNUM", PAYER_PHONNUM);               //������ ��ȭ��ȣ
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //��û ���
        sobj.setString("ERR_GBN", ERR_GBN);               //���� ����
        sobj.setString("GBN", GBN);               //����
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        sobj.setString("SEQ_NUM", SEQ_NUM);               //�Ϸ� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��û�� ���
    // �ֹι�ȣ ������ ������ �������
    public DOBJ CALLupload_test_INS16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS16");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_test_INS16(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS16") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_test_INS16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String BANK_CD ="";        //���� �ڵ�
        String CONFIRM_DATE ="";        //Ȯ�� ����
        String INS_DATE ="";        //��� �Ͻ�
        double SEQ = 0;        //������ȣ
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //��û ����
        String   SEQ_1 = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //������ȣ
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   BANK_CD_1 = dobj.getRetObject("R").getRecord().get("PAY_BANK_CD");   //���� �ڵ�
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //����� ID
        String   APPLICATION_GBN ="71";   //��û ����
        String   AUTO_ACCNNUM = dobj.getRetObject("R").getRecord().get("PAY_ACCN_NUM");   //��� ���¹�ȣ
        String   CONFIRM_ID = dobj.getRetObject("R").getRecord().get("INSPRES_ID");   //��� ����
        String   PHON_NUM = dobj.getRetObject("R").getRecord().get("PAYER_PHONNUM");   //��ȭ ��ȣ
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //ó�� ����
        String   RECEPTION_GBN ="9";   //����ó
        String   REMAK =" 03:��󳳺��� ����";   //���
        String   STAFF_CD = dobj.getRetObject("R").getRecord().get("INSPRES_ID");   //��� �ڵ�
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO_APPLICATION (CONFIRM_ID, INSPRES_ID, BANK_CD, PAYPRES_NM, APPLICATION_GBN, PHON_NUM, SEQ, PROC_DAY, INS_DATE, STAFF_CD, APPTN_DAY, UPSO_CD, AUTO_ACCNNUM, RECEPTION_GBN, RESINUM, CONFIRM_DATE, REMAK)  \n";
        query +=" values(:CONFIRM_ID , :INSPRES_ID , SUBSTR(:BANK_CD_1, 1,3), :PAYPRES_NM , :APPLICATION_GBN , :PHON_NUM , (SELECT NVL(MAX(SEQ), 0) + 1 SEQ FROM GIBU.TBRA_UPSO_AUTO_APPLICATION WHERE UPSO_CD = :SEQ_1), :PROC_DAY , SYSDATE, :STAFF_CD , :APPTN_DAY , :UPSO_CD , :AUTO_ACCNNUM , :RECEPTION_GBN , :RESINUM , SYSDATE, :REMAK )";
        sobj.setSql(query);
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("SEQ_1", SEQ_1);               //������ȣ
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("BANK_CD_1", BANK_CD_1);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("APPLICATION_GBN", APPLICATION_GBN);               //��û ����
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //��� ���¹�ȣ
        sobj.setString("CONFIRM_ID", CONFIRM_ID);               //��� ����
        sobj.setString("PHON_NUM", PHON_NUM);               //��ȭ ��ȣ
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //����ó
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��������üũ
    // �����ڰ��°������ڵ� (7)  - 7�ڸ��� �����̳�, �����ڵ� 3�ڸ��� �����ϰ� ���� 4�ڸ���"0000"�� �����ϹǷ� ���� 3�ڸ��� LIKE �˻��� �Ѵ�
    public DOBJ CALLupload_test_SEL17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL17");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL17");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupload_test_SEL17(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL17");
        rvobj.Println("SEL17");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_test_SEL17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PAY_BANK_CD = dobj.getRetObject("R").getRecord().get("PAY_BANK_CD");   //���� ���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  ACCT.TCAC_BANK_7  WHERE  BANK_CD  LIKE  SUBSTR(:PAY_BANK_CD,1,3)||'%' ";
        sobj.setSql(query);
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //���� ���� �ڵ�
        return sobj;
    }
    // �̷¸��� ���
    public DOBJ CALLupload_test_INS19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS19");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS19");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_test_INS19(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS19") ;
        rvobj.Println("INS19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_test_INS19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //����ó ����
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //��û ����
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   RECPTNUM = dvobj.getRecord().get("RECPTNUM");   //������ȣ
        String   PAY_BANK_CD = dvobj.getRecord().get("PAY_BANK_CD");   //���� ���� �ڵ�
        String   PAY_KND = dvobj.getRecord().get("PAY_KND");   //��� ����
        String   PAY_REQDAY = dvobj.getRecord().get("PAY_REQDAY");   //���� �������
        String   CHGBFR_PAYPRES_NUM = dvobj.getRecord().get("CHGBFR_PAYPRES_NUM");   //������ ������ ��ȣ
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //���� ������
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //��� ���� ��ȣ
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //������ ������ ��ȣ
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //������ �ڵ�
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //������ ��ȭ��ȣ
        String   APPTN_RSLT = dvobj.getRecord().get("APPTN_RSLT");   //��û ���
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //��û ����
        String   ERR_GBN ="";   //���� ����
        String   GBN ="22";   //����
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //ó�� ����
        String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");   //�Ϸ� ��ȣ
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYER_PHONNUM, GBN, PAYPRES_NM, RECPT_BANK_CD, CHGATR_PAYPRES_NUM, PAY_ACCN_NUM, PROC_DAY, BEGNG_RELSDAY, SEQ_NUM, CHGBFR_PAYPRES_NUM, PAY_REQDAY, PAY_KND, APPTN_DAY, PAY_BANK_CD, RECPTNUM, UPSO_CD, RESINUM, ERR_GBN, APPTN_GBN, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYER_PHONNUM , :GBN , :PAYPRES_NM , :RECPT_BANK_CD , :CHGATR_PAYPRES_NUM , :PAY_ACCN_NUM , :PROC_DAY , :BEGNG_RELSDAY , :SEQ_NUM , :CHGBFR_PAYPRES_NUM , :PAY_REQDAY , :PAY_KND , :APPTN_DAY , :PAY_BANK_CD , :RECPTNUM , :UPSO_CD , :RESINUM , :ERR_GBN , :APPTN_GBN , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //����ó ����
        sobj.setString("APPTN_GBN", APPTN_GBN);               //��û ����
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("RECPTNUM", RECPTNUM);               //������ȣ
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //���� ���� �ڵ�
        sobj.setString("PAY_KND", PAY_KND);               //��� ����
        sobj.setString("PAY_REQDAY", PAY_REQDAY);               //���� �������
        sobj.setString("CHGBFR_PAYPRES_NUM", CHGBFR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("BEGNG_RELSDAY", BEGNG_RELSDAY);               //���� ������
        sobj.setString("PAY_ACCN_NUM", PAY_ACCN_NUM);               //��� ���� ��ȣ
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("RECPT_BANK_CD", RECPT_BANK_CD);               //������ �ڵ�
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("PAYER_PHONNUM", PAYER_PHONNUM);               //������ ��ȭ��ȣ
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //��û ���
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("ERR_GBN", ERR_GBN);               //���� ����
        sobj.setString("GBN", GBN);               //����
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        sobj.setString("SEQ_NUM", SEQ_NUM);               //�Ϸ� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // û�� ������ ����
    // û�� �����͸� �����ϱ� ���� ���ν��� (GIBU.SP_PROC_DEMDGIRO) �� ȣ���Ѵ�
    public DOBJ CALLupload_test_OSP20(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP20");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP20");
        String[]  incolumns ={"UPSO_CD","PROC_DAY","SEQ_NUM","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");         //ó�� ����
            record.put("PROC_DAY",PROC_DAY);
            ////
            String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");         //�Ϸ� ��ȣ
            record.put("SEQ_NUM",SEQ_NUM);
            ////
            String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");         //���� �ڵ�
            record.put("UPSO_CD",UPSO_CD);
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
        String   spname ="GIBU.SP_PROC_GW81_CHECK";
        int[]    intypes={12, 12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        robj.setName("OSP20");
        robj.Println("OSP20");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ������������
    public DOBJ CALLupload_test_INS21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS21");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS21");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_test_INS21(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS21") ;
        rvobj.Println("INS21");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_test_INS21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //����ó ����
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //��û ����
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   RECPTNUM = dvobj.getRecord().get("RECPTNUM");   //������ȣ
        String   PAY_BANK_CD = dvobj.getRecord().get("PAY_BANK_CD");   //���� ���� �ڵ�
        String   PAY_KND = dvobj.getRecord().get("PAY_KND");   //��� ����
        String   PAY_REQDAY = dvobj.getRecord().get("PAY_REQDAY");   //���� �������
        String   CHGBFR_PAYPRES_NUM = dvobj.getRecord().get("CHGBFR_PAYPRES_NUM");   //������ ������ ��ȣ
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //���� ������
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //��� ���� ��ȣ
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //������ ������ ��ȣ
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //������ �ڵ�
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //������ ��ȭ��ȣ
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //��û ����
        String   APPTN_RSLT ="99";   //��û ���
        String   ERR_GBN ="0003";   //���� ����
        String   GBN ="22";   //����
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //ó�� ����
        String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");   //�Ϸ� ��ȣ
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYER_PHONNUM, GBN, PAYPRES_NM, RECPT_BANK_CD, CHGATR_PAYPRES_NUM, PAY_ACCN_NUM, PROC_DAY, BEGNG_RELSDAY, SEQ_NUM, CHGBFR_PAYPRES_NUM, PAY_REQDAY, PAY_KND, APPTN_DAY, PAY_BANK_CD, RECPTNUM, UPSO_CD, RESINUM, ERR_GBN, APPTN_GBN, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYER_PHONNUM , :GBN , :PAYPRES_NM , :RECPT_BANK_CD , :CHGATR_PAYPRES_NUM , :PAY_ACCN_NUM , :PROC_DAY , :BEGNG_RELSDAY , :SEQ_NUM , :CHGBFR_PAYPRES_NUM , :PAY_REQDAY , :PAY_KND , :APPTN_DAY , :PAY_BANK_CD , :RECPTNUM , :UPSO_CD , :RESINUM , :ERR_GBN , :APPTN_GBN , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //����ó ����
        sobj.setString("APPTN_GBN", APPTN_GBN);               //��û ����
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("RECPTNUM", RECPTNUM);               //������ȣ
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //���� ���� �ڵ�
        sobj.setString("PAY_KND", PAY_KND);               //��� ����
        sobj.setString("PAY_REQDAY", PAY_REQDAY);               //���� �������
        sobj.setString("CHGBFR_PAYPRES_NUM", CHGBFR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("BEGNG_RELSDAY", BEGNG_RELSDAY);               //���� ������
        sobj.setString("PAY_ACCN_NUM", PAY_ACCN_NUM);               //��� ���� ��ȣ
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("RECPT_BANK_CD", RECPT_BANK_CD);               //������ �ڵ�
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("PAYER_PHONNUM", PAYER_PHONNUM);               //������ ��ȭ��ȣ
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //��û ���
        sobj.setString("ERR_GBN", ERR_GBN);               //���� ����
        sobj.setString("GBN", GBN);               //����
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        sobj.setString("SEQ_NUM", SEQ_NUM);               //�Ϸ� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��û�� ���
    // �ֹι�ȣ ������ ������ �������
    public DOBJ CALLupload_test_INS22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS22");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_test_INS22(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS22") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_test_INS22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String BANK_CD ="";        //���� �ڵ�
        String CONFIRM_DATE ="";        //Ȯ�� ����
        String INS_DATE ="";        //��� �Ͻ�
        double SEQ = 0;        //������ȣ
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //��û ����
        String   SEQ_1 = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //������ȣ
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   BANK_CD_1 = dobj.getRetObject("R").getRecord().get("PAY_BANK_CD");   //���� �ڵ�
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //����� ID
        String   APPLICATION_GBN ="71";   //��û ����
        String   AUTO_ACCNNUM = dobj.getRetObject("R").getRecord().get("PAY_ACCN_NUM");   //��� ���¹�ȣ
        String   CONFIRM_ID = dobj.getRetObject("R").getRecord().get("INSPRES_ID");   //��� ����
        String   PHON_NUM = dobj.getRetObject("R").getRecord().get("PAYER_PHONNUM");   //��ȭ ��ȣ
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //ó�� ����
        String   RECEPTION_GBN ="9";   //����ó
        String   REMAK =" 99:�������� ����";   //���
        String   STAFF_CD = dobj.getRetObject("R").getRecord().get("INSPRES_ID");   //��� �ڵ�
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO_APPLICATION (CONFIRM_ID, INSPRES_ID, BANK_CD, PAYPRES_NM, APPLICATION_GBN, PHON_NUM, SEQ, PROC_DAY, INS_DATE, STAFF_CD, APPTN_DAY, UPSO_CD, AUTO_ACCNNUM, RECEPTION_GBN, RESINUM, CONFIRM_DATE, REMAK)  \n";
        query +=" values(:CONFIRM_ID , :INSPRES_ID , SUBSTR(:BANK_CD_1, 1,3), :PAYPRES_NM , :APPLICATION_GBN , :PHON_NUM , (SELECT NVL(MAX(SEQ), 0) + 1 SEQ FROM GIBU.TBRA_UPSO_AUTO_APPLICATION WHERE UPSO_CD = :SEQ_1), :PROC_DAY , SYSDATE, :STAFF_CD , :APPTN_DAY , :UPSO_CD , :AUTO_ACCNNUM , :RECEPTION_GBN , :RESINUM , SYSDATE, :REMAK )";
        sobj.setSql(query);
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("SEQ_1", SEQ_1);               //������ȣ
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("BANK_CD_1", BANK_CD_1);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("APPLICATION_GBN", APPLICATION_GBN);               //��û ����
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //��� ���¹�ȣ
        sobj.setString("CONFIRM_ID", CONFIRM_ID);               //��� ����
        sobj.setString("PHON_NUM", PHON_NUM);               //��ȭ ��ȣ
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //����ó
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �̷¸��� ���
    public DOBJ CALLupload_test_INS45(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS45");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS45");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_test_INS45(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS45") ;
        rvobj.Println("INS45");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_test_INS45(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //����ó ����
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //��û ����
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   RECPTNUM = dvobj.getRecord().get("RECPTNUM");   //������ȣ
        String   PAY_BANK_CD = dvobj.getRecord().get("PAY_BANK_CD");   //���� ���� �ڵ�
        String   PAY_KND = dvobj.getRecord().get("PAY_KND");   //��� ����
        String   PAY_REQDAY = dvobj.getRecord().get("PAY_REQDAY");   //���� �������
        String   CHGBFR_PAYPRES_NUM = dvobj.getRecord().get("CHGBFR_PAYPRES_NUM");   //������ ������ ��ȣ
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //���� ������
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //��� ���� ��ȣ
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //������ ������ ��ȣ
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //������ �ڵ�
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //������ ��ȭ��ȣ
        String   APPTN_RSLT = dvobj.getRecord().get("APPTN_RSLT");   //��û ���
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //��û ����
        String   ERR_GBN ="";   //���� ����
        String   GBN ="22";   //����
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //ó�� ����
        String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");   //�Ϸ� ��ȣ
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYER_PHONNUM, GBN, PAYPRES_NM, RECPT_BANK_CD, CHGATR_PAYPRES_NUM, PAY_ACCN_NUM, PROC_DAY, BEGNG_RELSDAY, SEQ_NUM, CHGBFR_PAYPRES_NUM, PAY_REQDAY, PAY_KND, APPTN_DAY, PAY_BANK_CD, RECPTNUM, UPSO_CD, RESINUM, ERR_GBN, APPTN_GBN, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYER_PHONNUM , :GBN , :PAYPRES_NM , :RECPT_BANK_CD , :CHGATR_PAYPRES_NUM , :PAY_ACCN_NUM , :PROC_DAY , :BEGNG_RELSDAY , :SEQ_NUM , :CHGBFR_PAYPRES_NUM , :PAY_REQDAY , :PAY_KND , :APPTN_DAY , :PAY_BANK_CD , :RECPTNUM , :UPSO_CD , :RESINUM , :ERR_GBN , :APPTN_GBN , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //����ó ����
        sobj.setString("APPTN_GBN", APPTN_GBN);               //��û ����
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("RECPTNUM", RECPTNUM);               //������ȣ
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //���� ���� �ڵ�
        sobj.setString("PAY_KND", PAY_KND);               //��� ����
        sobj.setString("PAY_REQDAY", PAY_REQDAY);               //���� �������
        sobj.setString("CHGBFR_PAYPRES_NUM", CHGBFR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("BEGNG_RELSDAY", BEGNG_RELSDAY);               //���� ������
        sobj.setString("PAY_ACCN_NUM", PAY_ACCN_NUM);               //��� ���� ��ȣ
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("RECPT_BANK_CD", RECPT_BANK_CD);               //������ �ڵ�
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("PAYER_PHONNUM", PAYER_PHONNUM);               //������ ��ȭ��ȣ
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //��û ���
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("ERR_GBN", ERR_GBN);               //���� ����
        sobj.setString("GBN", GBN);               //����
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        sobj.setString("SEQ_NUM", SEQ_NUM);               //�Ϸ� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // û�� ������ ����
    // û�� �����͸� �����ϱ� ���� ���ν��� (GIBU.SP_PROC_DEMDGIRO) �� ȣ���Ѵ�
    public DOBJ CALLupload_test_OSP46(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP46");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP46");
        String[]  incolumns ={"UPSO_CD","PROC_DAY","SEQ_NUM","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");         //ó�� ����
            record.put("PROC_DAY",PROC_DAY);
            ////
            String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");         //�Ϸ� ��ȣ
            record.put("SEQ_NUM",SEQ_NUM);
            ////
            String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");         //���� �ڵ�
            record.put("UPSO_CD",UPSO_CD);
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
        String   spname ="GIBU.SP_PROC_GW81_CHECK";
        int[]    intypes={12, 12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        robj.setName("OSP46");
        robj.Println("OSP46");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ������ȸ����
    // �����ڹ�ȣ �����ǰ԰� Ʋ�� ���
    public DOBJ CALLupload_test_INS9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS9");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_test_INS9(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS9") ;
        rvobj.Println("INS9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_test_INS9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //����ó ����
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //��û ����
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   RECPTNUM = dvobj.getRecord().get("RECPTNUM");   //������ȣ
        String   PAY_BANK_CD = dvobj.getRecord().get("PAY_BANK_CD");   //���� ���� �ڵ�
        String   PAY_KND = dvobj.getRecord().get("PAY_KND");   //��� ����
        String   PAY_REQDAY = dvobj.getRecord().get("PAY_REQDAY");   //���� �������
        String   CHGBFR_PAYPRES_NUM = dvobj.getRecord().get("CHGBFR_PAYPRES_NUM");   //������ ������ ��ȣ
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //���� ������
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //��� ���� ��ȣ
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //������ ������ ��ȣ
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //������ �ڵ�
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //������ ��ȭ��ȣ
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //��û ����
        String   APPTN_RSLT ="02";   //��û ���
        String   ERR_GBN ="0001";   //���� ����
        String   GBN ="22";   //����
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //ó�� ����
        String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");   //������ȣ
        String   UPSO_CD ="";   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYER_PHONNUM, GBN, PAYPRES_NM, RECPT_BANK_CD, CHGATR_PAYPRES_NUM, PAY_ACCN_NUM, PROC_DAY, BEGNG_RELSDAY, SEQ_NUM, CHGBFR_PAYPRES_NUM, PAY_REQDAY, PAY_KND, APPTN_DAY, PAY_BANK_CD, RECPTNUM, UPSO_CD, RESINUM, ERR_GBN, APPTN_GBN, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYER_PHONNUM , :GBN , :PAYPRES_NM , :RECPT_BANK_CD , :CHGATR_PAYPRES_NUM , :PAY_ACCN_NUM , :PROC_DAY , :BEGNG_RELSDAY , :SEQ_NUM , :CHGBFR_PAYPRES_NUM , :PAY_REQDAY , :PAY_KND , :APPTN_DAY , :PAY_BANK_CD , :RECPTNUM , :UPSO_CD , :RESINUM , :ERR_GBN , :APPTN_GBN , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //����ó ����
        sobj.setString("APPTN_GBN", APPTN_GBN);               //��û ����
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("RECPTNUM", RECPTNUM);               //������ȣ
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //���� ���� �ڵ�
        sobj.setString("PAY_KND", PAY_KND);               //��� ����
        sobj.setString("PAY_REQDAY", PAY_REQDAY);               //���� �������
        sobj.setString("CHGBFR_PAYPRES_NUM", CHGBFR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("BEGNG_RELSDAY", BEGNG_RELSDAY);               //���� ������
        sobj.setString("PAY_ACCN_NUM", PAY_ACCN_NUM);               //��� ���� ��ȣ
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("RECPT_BANK_CD", RECPT_BANK_CD);               //������ �ڵ�
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("PAYER_PHONNUM", PAYER_PHONNUM);               //������ ��ȭ��ȣ
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //��û ���
        sobj.setString("ERR_GBN", ERR_GBN);               //���� ����
        sobj.setString("GBN", GBN);               //����
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        sobj.setString("SEQ_NUM", SEQ_NUM);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$upload_test
    //##**$$end
}
