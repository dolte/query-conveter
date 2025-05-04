
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s14
{
    public bra03_s14()
    {
    }
    //##**$$upload_file
    /*
    */
    public DOBJ CTLupload_file(DOBJ dobj)
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
            dobj  = CALLupload_file_XIUD16(Conn, dobj);           //  ����������û������
            dobj  = CALLupload_file_MPD2(Conn, dobj);           //  �Ǻ�ó��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupload_file_SEL1(Conn, dobj);           //  ��ü���
            dobj  = CALLupload_file_SEL2(Conn, dobj);           //  ����������
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
    public DOBJ CTLupload_file( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupload_file_XIUD16(Conn, dobj);           //  ����������û������
        dobj  = CALLupload_file_MPD2(Conn, dobj);           //  �Ǻ�ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupload_file_SEL1(Conn, dobj);           //  ��ü���
        dobj  = CALLupload_file_SEL2(Conn, dobj);           //  ����������
        return dobj;
    }
    // ����������û������
    // ó�����ڿ� �ش��ϴ� ����������û���� �����Ѵ�.
    public DOBJ CALLupload_file_XIUD16(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLupload_file_XIUD16(dobj, dvobj);
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
    private SQLObject SQLupload_file_XIUD16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CARD_GBN = dobj.getRetObject("S1").getRecord().get("CARD_GBN");   //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE  \n";
        query +=" GIBU.TBRA_UPSO_AUTORSLT A  \n";
        query +=" WHERE A.PROC_DAY = :PROC_DAY  \n";
        query +=" AND A.CARD_GBN = :CARD_GBN  \n";
        query +=" AND A.RECPT_GBN_CD = '07'";
        sobj.setSql(query);
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        return sobj;
    }
    // �Ǻ�ó��
    public DOBJ CALLupload_file_MPD2(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLupload_file_SEL3(Conn, dobj);           //  ��û�� üũ
                if(!dobj.getRetObject("SEL3").getRecord().get("UPSO_CD").equals(""))
                {
                    dobj  = CALLupload_file_INS43(Conn, dobj);           //  ������
                    if( dobj.getRetObject("S").getRecord().get("APPTN_RSLT").equals("00"))
                    {
                        dobj  = CALLupload_file_INS44(Conn, dobj);           //  ������
                        dobj  = CALLupload_file_UPD45(Conn, dobj);           //  ��û���� ��� ����
                    }
                    else if( dobj.getRetObject("S").getRecord().get("APPTN_RSLT").equals("01"))
                    {
                        dobj  = CALLupload_file_UPD16(Conn, dobj);           //  ��û���� ��� ����
                    }
                    else if( dobj.getRetObject("S").getRecord().get("APPTN_RSLT").equals("02"))
                    {
                        dobj  = CALLupload_file_UPD14(Conn, dobj);           //  ��û���� ��� ����
                    }
                }
                else
                {
                    dobj  = CALLupload_file_INS13(Conn, dobj);           //  ������
                }
            }
        }
        return dobj;
    }
    // ��û�� üũ
    public DOBJ CALLupload_file_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupload_file_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RESINUM = dobj.getRetObject("R").getRecord().get("RESINUM");   //�ֹι�ȣ
        String   CLIENT_NUM = dobj.getRetObject("R").getRecord().get("CLIENT_NUM");   //�� ��ȣ
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD,  A.SEQ  FROM  GIBU.TBRA_UPSO_AUTO_APPLICATION  A  WHERE  1=1   \n";
        query +=" AND  A.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  A.APPLICATION_GBN  IN  ('12','16')   \n";
        query +=" AND  A.RESINUM  =  :RESINUM   \n";
        query +=" AND  A.UPSO_CD  =   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  CLIENT_NUM  =  :CLIENT_NUM) ";
        sobj.setSql(query);
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("CLIENT_NUM", CLIENT_NUM);               //�� ��ȣ
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        return sobj;
    }
    // ������
    public DOBJ CALLupload_file_INS43(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS43");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS43");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_file_INS43(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS43") ;
        rvobj.Println("INS43");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_INS43(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //����ó ����
        String   CARD_NUM = dvobj.getRecord().get("CARD_NUM");   //ī�� ��ȣ
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //��û ����
        String   SEQ_NUM = dvobj.getRecord().get("SEQ_NUM");   //�Ϸ� ��ȣ
        String   BIOWN_INSNUM = dvobj.getRecord().get("BIOWN_INSNUM");   //����� ��Ϲ�ȣ
        String   EXPIRE_DAY = dvobj.getRecord().get("EXPIRE_DAY");   //���� ��
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   APPTN_RSLT = dvobj.getRecord().get("APPTN_RSLT");   //��û ���
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //��û ����
        String   CARD_GBN = dobj.getRetObject("S1").getRecord().get("CARD_GBN");   //ī�� ����
        String   CHGATR_PAYPRES_NUM = dobj.getRetObject("R").getRecord().get("CLIENT_NUM");   //������ ������ ��ȣ
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //ó�� ����
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYPRES_NM, EXPIRE_DAY, CHGATR_PAYPRES_NUM, PROC_DAY, BIOWN_INSNUM, SEQ_NUM, CARD_GBN, APPTN_DAY, UPSO_CD, APPTN_GBN, CARD_NUM, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYPRES_NM , :EXPIRE_DAY , :CHGATR_PAYPRES_NUM , :PROC_DAY , :BIOWN_INSNUM , :SEQ_NUM , :CARD_GBN , :APPTN_DAY , :UPSO_CD , :APPTN_GBN , :CARD_NUM , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //����ó ����
        sobj.setString("CARD_NUM", CARD_NUM);               //ī�� ��ȣ
        sobj.setString("APPTN_GBN", APPTN_GBN);               //��û ����
        sobj.setString("SEQ_NUM", SEQ_NUM);               //�Ϸ� ��ȣ
        sobj.setString("BIOWN_INSNUM", BIOWN_INSNUM);               //����� ��Ϲ�ȣ
        sobj.setString("EXPIRE_DAY", EXPIRE_DAY);               //���� ��
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //��û ���
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������
    public DOBJ CALLupload_file_INS44(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS44");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_file_INS44(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS44") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_INS44(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int  AUTO_NUM = 0;        //�Ϸ� ��ȣ
        String INS_DATE ="";        //��� �Ͻ�
        String   CARD_NUM = dvobj.getRecord().get("CARD_NUM");   //ī�� ��ȣ
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //��û ����
        String   BIOWN_INSNUM = dvobj.getRecord().get("BIOWN_INSNUM");   //����� ��Ϲ�ȣ
        String   AUTO_NUM_1 = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //�Ϸ� ��ȣ
        String   EXPIRE_DAY = dvobj.getRecord().get("EXPIRE_DAY");   //���� ��
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   CARD_GBN = dobj.getRetObject("S1").getRecord().get("CARD_GBN");   //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   PROC_GBN ="Y";   //�ڵ� ó�� ����
        String   RECEPTION_GBN ="7";   //����ó
        String   TERM_YN ="N";   //���� ����
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO (INSPRES_ID, PAYPRES_NM, TERM_YN, EXPIRE_DAY, AUTO_NUM, PROC_GBN, BIOWN_INSNUM, INS_DATE, CARD_GBN, APPTN_DAY, UPSO_CD, RESINUM, RECEPTION_GBN, CARD_NUM)  \n";
        query +=" values(:INSPRES_ID , :PAYPRES_NM , :TERM_YN , :EXPIRE_DAY , (SELECT NVL(MAX(AUTO_NUM), 0) + 1 AUTO_NUM FROM GIBU.TBRA_UPSO_AUTO_TEST2 WHERE UPSO_CD = :AUTO_NUM_1), :PROC_GBN , :BIOWN_INSNUM , SYSDATE, :CARD_GBN , :APPTN_DAY , :UPSO_CD , :RESINUM , :RECEPTION_GBN , :CARD_NUM )";
        sobj.setSql(query);
        sobj.setString("CARD_NUM", CARD_NUM);               //ī�� ��ȣ
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("BIOWN_INSNUM", BIOWN_INSNUM);               //����� ��Ϲ�ȣ
        sobj.setString("AUTO_NUM_1", AUTO_NUM_1);               //�Ϸ� ��ȣ
        sobj.setString("EXPIRE_DAY", EXPIRE_DAY);               //���� ��
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PROC_GBN", PROC_GBN);               //�ڵ� ó�� ����
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //����ó
        sobj.setString("TERM_YN", TERM_YN);               //���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��û���� ��� ����
    public DOBJ CALLupload_file_UPD45(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD45");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_file_UPD45(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD45") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_UPD45(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REMAK ="00:����ó��";   //���
        double   SEQ = dobj.getRetObject("SEL3").getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" set REMAK=:REMAK  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��û���� ��� ����
    public DOBJ CALLupload_file_UPD16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD16");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_file_UPD16(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD16") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_UPD16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REMAK ="01";   //���
        double   SEQ = dobj.getRetObject("SEL3").getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" set REMAK=:REMAK  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��û���� ��� ����
    public DOBJ CALLupload_file_UPD14(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLupload_file_UPD14(dobj, dvobj);
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
    private SQLObject SQLupload_file_UPD14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY ="";   //ó�� ����
        String   REMAK ="02:��߼�";   //���
        double   SEQ = dobj.getRetObject("SEL3").getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" set PROC_DAY=:PROC_DAY , REMAK=:REMAK  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        sobj.setString("REMAK", REMAK);               //���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������
    public DOBJ CALLupload_file_INS13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS13");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS13");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_file_INS13(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS13") ;
        rvobj.Println("INS13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_INS13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //����ó ����
        String   CARD_NUM = dvobj.getRecord().get("CARD_NUM");   //ī�� ��ȣ
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //��û ����
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //��û ����
        String   SEQ_NUM = dvobj.getRecord().get("SEQ_NUM");   //�Ϸ� ��ȣ
        String   BIOWN_INSNUM = dvobj.getRecord().get("BIOWN_INSNUM");   //����� ��Ϲ�ȣ
        String   EXPIRE_DAY = dvobj.getRecord().get("EXPIRE_DAY");   //���� ��
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   APPTN_RSLT = dvobj.getRecord().get("APPTN_RSLT");   //��û ���
        String   CARD_GBN = dobj.getRetObject("S1").getRecord().get("CARD_GBN");   //ī�� ����
        String   CHGATR_PAYPRES_NUM = dobj.getRetObject("R").getRecord().get("CLIENT_NUM");   //������ ������ ��ȣ
        String   ERR_GBN ="99";   //���� ����
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //ó�� ����
        String   UPSO_CD ="1";   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYPRES_NM, EXPIRE_DAY, CHGATR_PAYPRES_NUM, PROC_DAY, BIOWN_INSNUM, SEQ_NUM, CARD_GBN, APPTN_DAY, UPSO_CD, APPTN_GBN, ERR_GBN, CARD_NUM, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYPRES_NM , :EXPIRE_DAY , :CHGATR_PAYPRES_NUM , :PROC_DAY , :BIOWN_INSNUM , :SEQ_NUM , :CARD_GBN , :APPTN_DAY , :UPSO_CD , :APPTN_GBN , :ERR_GBN , :CARD_NUM , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //����ó ����
        sobj.setString("CARD_NUM", CARD_NUM);               //ī�� ��ȣ
        sobj.setString("APPTN_GBN", APPTN_GBN);               //��û ����
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("SEQ_NUM", SEQ_NUM);               //�Ϸ� ��ȣ
        sobj.setString("BIOWN_INSNUM", BIOWN_INSNUM);               //����� ��Ϲ�ȣ
        sobj.setString("EXPIRE_DAY", EXPIRE_DAY);               //���� ��
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //��û ���
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("ERR_GBN", ERR_GBN);               //���� ����
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��ü���
    public DOBJ CALLupload_file_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupload_file_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CARD_GBN = dobj.getRetObject("S1").getRecord().get("CARD_GBN");   //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.PROC_DAY  ,  A.SEQ_NUM  ,  A.APPTN_GBN  ,  A.APPTN_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.EXPIRE_DAY  ,  A.CARD_NUM  ,  A.PAYPRES_NM  ,  A.PAYER_PHONNUM  ,  A.RESINUM  ,  A.BIOWN_INSNUM  ,  A.APPTN_RSLT  ,  A.CARD_GBN  ,  A.RECPT_GBN_CD  ,  A.CHGATR_PAYPRES_NUM  AS  CLIENT_NUM  FROM  GIBU.TBRA_UPSO_AUTORSLT  A  ,  GIBU.TBRA_UPSO  B  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  A.CARD_GBN  =  :CARD_GBN   \n";
        query +=" AND  A.RECPT_GBN_CD  =  '7'   \n";
        query +=" AND  A.APPTN_RSLT  =  '00' ";
        sobj.setSql(query);
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        return sobj;
    }
    // ����������
    public DOBJ CALLupload_file_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupload_file_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CARD_GBN = dobj.getRetObject("S1").getRecord().get("CARD_GBN");   //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.PROC_DAY  ,  A.SEQ_NUM  ,  A.APPTN_GBN  ,  A.APPTN_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.EXPIRE_DAY  ,  A.CARD_NUM  ,  A.PAYPRES_NM  ,  A.PAYER_PHONNUM  ,  A.RESINUM  ,  A.BIOWN_INSNUM  ,  A.APPTN_RSLT  ,  A.CARD_GBN  ,  A.RECPT_GBN_CD  ,  A.CHGATR_PAYPRES_NUM  AS  CLIENT_NUM  FROM  GIBU.TBRA_UPSO_AUTORSLT  A  ,  GIBU.TBRA_UPSO  B  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  A.CARD_GBN  =  :CARD_GBN   \n";
        query +=" AND  A.RECPT_GBN_CD  =  '7'   \n";
        query +=" AND  A.APPTN_RSLT  <>  '00' ";
        sobj.setSql(query);
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        return sobj;
    }
    //##**$$upload_file
    //##**$$upload_result
    /*
    */
    public DOBJ CTLupload_result(DOBJ dobj)
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
            dobj  = CALLupload_result_SEL1(Conn, dobj);           //  ��ü���
            dobj  = CALLupload_result_SEL2(Conn, dobj);           //  �������
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
    public DOBJ CTLupload_result( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupload_result_SEL1(Conn, dobj);           //  ��ü���
        dobj  = CALLupload_result_SEL2(Conn, dobj);           //  �������
        return dobj;
    }
    // ��ü���
    public DOBJ CALLupload_result_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupload_result_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_result_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CARD_GBN = dobj.getRetObject("S").getRecord().get("CARD_GBN");   //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.PROC_DAY  ,  A.SEQ_NUM  ,  A.APPTN_GBN  ,  A.APPTN_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.EXPIRE_DAY  ,  A.CARD_NUM  ,  A.PAYPRES_NM  ,  A.PAYER_PHONNUM  ,  A.RESINUM  ,  A.BIOWN_INSNUM  ,  A.APPTN_RSLT  ,  A.CARD_GBN  ,  A.RECPT_GBN_CD  ,  A.CHGATR_PAYPRES_NUM  AS  CLIENT_NUM  FROM  GIBU.TBRA_UPSO_AUTORSLT  A  ,  GIBU.TBRA_UPSO  B  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  A.CARD_GBN  =  :CARD_GBN   \n";
        query +=" AND  A.RECPT_GBN_CD  =  '7'   \n";
        query +=" AND  A.APPTN_RSLT  =  '00' ";
        sobj.setSql(query);
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        return sobj;
    }
    // �������
    public DOBJ CALLupload_result_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupload_result_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_result_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CARD_GBN = dobj.getRetObject("S").getRecord().get("CARD_GBN");   //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.PROC_DAY  ,  A.SEQ_NUM  ,  A.APPTN_GBN  ,  A.APPTN_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.EXPIRE_DAY  ,  A.CARD_NUM  ,  A.PAYPRES_NM  ,  A.PAYER_PHONNUM  ,  A.RESINUM  ,  A.BIOWN_INSNUM  ,  A.APPTN_RSLT  ,  A.CARD_GBN  ,  A.RECPT_GBN_CD  ,  A.CHGATR_PAYPRES_NUM  AS  CLIENT_NUM  FROM  GIBU.TBRA_UPSO_AUTORSLT  A  ,  GIBU.TBRA_UPSO  B  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD(+)   \n";
        query +=" AND  A.PROC_DAY  =  :PROC_DAY  --AND  A.CARD_GBN  =  :CARD_GBN   \n";
        query +=" AND  A.RECPT_GBN_CD  =  '7'   \n";
        query +=" AND  APPTN_RSLT  <>  '00' ";
        sobj.setSql(query);
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        return sobj;
    }
    //##**$$upload_result
    //##**$$end
}
