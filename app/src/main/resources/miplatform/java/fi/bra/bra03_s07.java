
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s07
{
    public bra03_s07()
    {
    }
    //##**$$auto_upso_regist
    /* * ���α׷��� : bra03_s07
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/27
    * ����    :
    Input
    APPTN_DAY (��û ����)
    AUTO_ACCNNUM (��� ���¹�ȣ)
    AUTO_NUM (�Ϸ� ��ȣ)
    BANK_CD (���� �ڵ�)
    IUDFLAG (IUDFLAG)
    REMAK (���)
    RESINUM (�ֹι�ȣ)
    UPSO_CD (���� �ڵ�)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLauto_upso_regist(DOBJ dobj)
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
            dobj  = CALLauto_upso_regist_MIUD2(Conn, dobj);           //  ����
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
    public DOBJ CTLauto_upso_regist( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_upso_regist_MIUD2(Conn, dobj);           //  ����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ����
    public DOBJ CALLauto_upso_regist_MIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_upso_regist_SEL10(Conn, dobj);           //  ����
                if(!dobj.getRetObject("S").getRecord().get("RECEPTION_GBN").equals("7"))
                {
                    dobj  = CALLauto_upso_regist_INS6(Conn, dobj);           //  �ڵ���ü��û���
                }
                else
                {
                    dobj  = CALLauto_upso_regist_INS12(Conn, dobj);           //  �ڵ���ü��û���
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_upso_regist_SEL13(Conn, dobj);           //  ����
                if(!dobj.getRetObject("S").getRecord().get("RECEPTION_GBN").equals("7"))
                {
                    dobj  = CALLauto_upso_regist_UPD7(Conn, dobj);           //  �ڵ���ü��û��������
                }
                else
                {
                    dobj  = CALLauto_upso_regist_UPD15(Conn, dobj);           //  �ڵ���ü��û��������
                }
            }
        }
        return dobj;
    }
    // ����
    public DOBJ CALLauto_upso_regist_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_upso_regist_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_upso_regist_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SYSDATE  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // ����
    public DOBJ CALLauto_upso_regist_SEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL13");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_upso_regist_SEL13(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_upso_regist_SEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SYSDATE  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // �ڵ���ü��û���
    // ������ ���
    public DOBJ CALLauto_upso_regist_INS6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLauto_upso_regist_INS6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS6") ;
        rvobj.Println("INS6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_upso_regist_INS6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int  AUTO_NUM = 0;        //�Ϸ� ��ȣ
        String INS_DATE ="";        //��� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   RECEPTION_GBN = dvobj.getRecord().get("RECEPTION_GBN");   //����ó
        String   AUTO_ACCNNUM = dvobj.getRecord().get("AUTO_ACCNNUM");   //��� ���¹�ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //��û ����
        String   AUTO_NUM_1 = dvobj.getRecord().get("UPSO_CD");   //�Ϸ� ��ȣ
        String   TERM_YN = dvobj.getRecord().get("TERM_YN");   //���� ����
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   TERM_YRMN = dvobj.getRecord().get("TERM_YRMN");   //���� ����
        String   BANK_CD = dvobj.getRecord().get("BANK_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   PROC_GBN ="N";   //�ڵ� ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO (INSPRES_ID, BANK_CD, TERM_YRMN, PAYPRES_NM, TERM_YN, AUTO_NUM, PROC_GBN, INS_DATE, APPTN_DAY, UPSO_CD, AUTO_ACCNNUM, RECEPTION_GBN, RESINUM, REMAK)  \n";
        query +=" values(:INSPRES_ID , :BANK_CD , :TERM_YRMN , :PAYPRES_NM , :TERM_YN , (SELECT NVL(MAX(AUTO_NUM), 0) + 1 AUTO_NUM FROM GIBU.TBRA_UPSO_AUTO WHERE UPSO_CD = :AUTO_NUM_1), :PROC_GBN , SYSDATE, :APPTN_DAY , :UPSO_CD , :AUTO_ACCNNUM , :RECEPTION_GBN , :RESINUM , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //����ó
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //��� ���¹�ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("AUTO_NUM_1", AUTO_NUM_1);               //�Ϸ� ��ȣ
        sobj.setString("TERM_YN", TERM_YN);               //���� ����
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("TERM_YRMN", TERM_YRMN);               //���� ����
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PROC_GBN", PROC_GBN);               //�ڵ� ó�� ����
        return sobj;
    }
    // �ڵ���ü��û��������
    // ������ ���
    public DOBJ CALLauto_upso_regist_UPD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_upso_regist_UPD7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD7") ;
        rvobj.Println("UPD7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_upso_regist_UPD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   RECEPTION_GBN = dvobj.getRecord().get("RECEPTION_GBN");   //����ó
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   AUTO_ACCNNUM = dvobj.getRecord().get("AUTO_ACCNNUM");   //��� ���¹�ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //��û ����
        int   AUTO_NUM = dvobj.getRecord().getInt("AUTO_NUM");   //�Ϸ� ��ȣ
        String   TERM_YN = dvobj.getRecord().get("TERM_YN");   //���� ����
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   TERM_YRMN = dvobj.getRecord().get("TERM_YRMN");   //���� ����
        String   BANK_CD = dvobj.getRecord().get("BANK_CD");   //���� �ڵ�
        String   CARD_GBN ="";   //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        String   CARD_NUM ="";   //ī�� ��ȣ
        String   EXPIRE_DAY ="";   //���� ��
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , BANK_CD=:BANK_CD , TERM_YRMN=:TERM_YRMN , PAYPRES_NM=:PAYPRES_NM , TERM_YN=:TERM_YN , EXPIRE_DAY=:EXPIRE_DAY , CARD_GBN=:CARD_GBN , APPTN_DAY=:APPTN_DAY , AUTO_ACCNNUM=:AUTO_ACCNNUM , RESINUM=:RESINUM , MOD_DATE=SYSDATE , RECEPTION_GBN=:RECEPTION_GBN , CARD_NUM=:CARD_NUM , REMAK=:REMAK  \n";
        query +=" where AUTO_NUM=:AUTO_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //����ó
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //��� ���¹�ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setInt("AUTO_NUM", AUTO_NUM);               //�Ϸ� ��ȣ
        sobj.setString("TERM_YN", TERM_YN);               //���� ����
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("TERM_YRMN", TERM_YRMN);               //���� ����
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        sobj.setString("CARD_NUM", CARD_NUM);               //ī�� ��ȣ
        sobj.setString("EXPIRE_DAY", EXPIRE_DAY);               //���� ��
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // �ڵ���ü��û���
    // �ſ�ī���� ���
    public DOBJ CALLauto_upso_regist_INS12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_upso_regist_INS12(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS12") ;
        rvobj.Println("INS12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_upso_regist_INS12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int  AUTO_NUM = 0;        //�Ϸ� ��ȣ
        String INS_DATE ="";        //��� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   RECEPTION_GBN = dvobj.getRecord().get("RECEPTION_GBN");   //����ó
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //��û ����
        String   AUTO_NUM_1 = dvobj.getRecord().get("UPSO_CD");   //�Ϸ� ��ȣ
        String   EXPIRE_DAY = dvobj.getRecord().get("EXPIRE_DAY");   //���� ��
        String   TERM_YN = dvobj.getRecord().get("TERM_YN");   //���� ����
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   TERM_YRMN = dvobj.getRecord().get("TERM_YRMN");   //���� ����
        String   CARD_GBN = dobj.getRetObject("S").getRecord().get("BANK_CD");   //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        String   CARD_NUM = dobj.getRetObject("S").getRecord().get("AUTO_ACCNNUM");   //ī�� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   PROC_GBN ="N";   //�ڵ� ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO (INSPRES_ID, TERM_YRMN, PAYPRES_NM, TERM_YN, EXPIRE_DAY, AUTO_NUM, PROC_GBN, INS_DATE, CARD_GBN, APPTN_DAY, UPSO_CD, RESINUM, RECEPTION_GBN, CARD_NUM, REMAK)  \n";
        query +=" values(:INSPRES_ID , :TERM_YRMN , :PAYPRES_NM , :TERM_YN , :EXPIRE_DAY , (SELECT NVL(MAX(AUTO_NUM), 0) + 1 AUTO_NUM FROM GIBU.TBRA_UPSO_AUTO WHERE UPSO_CD = :AUTO_NUM_1), :PROC_GBN , SYSDATE, :CARD_GBN , :APPTN_DAY , :UPSO_CD , :RESINUM , :RECEPTION_GBN , :CARD_NUM , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //����ó
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("AUTO_NUM_1", AUTO_NUM_1);               //�Ϸ� ��ȣ
        sobj.setString("EXPIRE_DAY", EXPIRE_DAY);               //���� ��
        sobj.setString("TERM_YN", TERM_YN);               //���� ����
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("TERM_YRMN", TERM_YRMN);               //���� ����
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        sobj.setString("CARD_NUM", CARD_NUM);               //ī�� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PROC_GBN", PROC_GBN);               //�ڵ� ó�� ����
        return sobj;
    }
    // �ڵ���ü��û��������
    // ������ ���
    public DOBJ CALLauto_upso_regist_UPD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD15");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_upso_regist_UPD15(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD15") ;
        rvobj.Println("UPD15");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_upso_regist_UPD15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   RECEPTION_GBN = dvobj.getRecord().get("RECEPTION_GBN");   //����ó
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //��û ����
        int   AUTO_NUM = dvobj.getRecord().getInt("AUTO_NUM");   //�Ϸ� ��ȣ
        String   EXPIRE_DAY = dvobj.getRecord().get("EXPIRE_DAY");   //���� ��
        String   TERM_YN = dvobj.getRecord().get("TERM_YN");   //���� ����
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   TERM_YRMN = dvobj.getRecord().get("TERM_YRMN");   //���� ����
        String   AUTO_ACCNNUM ="";   //��� ���¹�ȣ
        String   BANK_CD ="";   //���� �ڵ�
        String   CARD_GBN = dobj.getRetObject("S").getRecord().get("BANK_CD");   //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        String   CARD_NUM = dobj.getRetObject("S").getRecord().get("AUTO_ACCNNUM");   //ī�� ��ȣ
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , BANK_CD=:BANK_CD , TERM_YRMN=:TERM_YRMN , PAYPRES_NM=:PAYPRES_NM , TERM_YN=:TERM_YN , EXPIRE_DAY=:EXPIRE_DAY , CARD_GBN=:CARD_GBN , APPTN_DAY=:APPTN_DAY , AUTO_ACCNNUM=:AUTO_ACCNNUM , RESINUM=:RESINUM , MOD_DATE=SYSDATE , RECEPTION_GBN=:RECEPTION_GBN , CARD_NUM=:CARD_NUM , REMAK=:REMAK  \n";
        query +=" where AUTO_NUM=:AUTO_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //����ó
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setInt("AUTO_NUM", AUTO_NUM);               //�Ϸ� ��ȣ
        sobj.setString("EXPIRE_DAY", EXPIRE_DAY);               //���� ��
        sobj.setString("TERM_YN", TERM_YN);               //���� ����
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("TERM_YRMN", TERM_YRMN);               //���� ����
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //��� ���¹�ȣ
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        sobj.setString("CARD_NUM", CARD_NUM);               //ī�� ��ȣ
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    //##**$$auto_upso_regist
    //##**$$end
}
