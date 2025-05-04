
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra14_s01
{
    public bra14_s01()
    {
    }
    //##**$$auto_card_app_save
    /*
    */
    public DOBJ CTLauto_card_app_save(DOBJ dobj)
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
            dobj  = CALLauto_card_app_save_MIUD1(Conn, dobj);           //  MIUD
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
    public DOBJ CTLauto_card_app_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_card_app_save_MIUD1(Conn, dobj);           //  MIUD
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // MIUD
    public DOBJ CALLauto_card_app_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLauto_card_app_save_SEL1(Conn, dobj);           //  ������ ����
                dobj  = CALLauto_card_app_save_INS5(Conn, dobj);           //  �Է�
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_card_app_save_UPD6(Conn, dobj);           //  ������Ʈ
            }
        }
        return dobj;
    }
    // ������ ����
    public DOBJ CALLauto_card_app_save_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_card_app_save_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_card_app_save_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  SEQ  FROM  GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������Ʈ
    public DOBJ CALLauto_card_app_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLauto_card_app_save_UPD6(dobj, dvobj);
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
    private SQLObject SQLauto_card_app_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   PASSWD = dvobj.getRecord().get("PASSWD");
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   CARD_NUM = dvobj.getRecord().get("CARD_NUM");   //ī�� ��ȣ
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   APP_GBN = dvobj.getRecord().get("APP_GBN");   //��û������
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //��û ����
        String   VAILD_YRMM = dvobj.getRecord().get("VAILD_YRMM");
        String   PAYPRES_PHON_NUM = dvobj.getRecord().get("PAYPRES_PHON_NUM");
        String   CARD_CD = dvobj.getRecord().get("CARD_CD");   //ī��� �ڵ�
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   RELATION_CD = dvobj.getRecord().get("RELATION_CD");
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , RELATION_CD=:RELATION_CD , PAYPRES_NM=:PAYPRES_NM , CARD_CD=:CARD_CD , PAYPRES_PHON_NUM=:PAYPRES_PHON_NUM , VAILD_YRMM=:VAILD_YRMM , APPTN_DAY=:APPTN_DAY , APP_GBN=:APP_GBN , MOD_DATE=SYSDATE , RESINUM=:RESINUM , CARD_NUM=:CARD_NUM , PASSWD=:PASSWD  \n";
        query +=" where SEQ=:SEQ  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("PASSWD", PASSWD);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("CARD_NUM", CARD_NUM);               //ī�� ��ȣ
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("APP_GBN", APP_GBN);               //��û������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("VAILD_YRMM", VAILD_YRMM);
        sobj.setString("PAYPRES_PHON_NUM", PAYPRES_PHON_NUM);
        sobj.setString("CARD_CD", CARD_CD);               //ī��� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("RELATION_CD", RELATION_CD);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // �Է�
    public DOBJ CALLauto_card_app_save_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_card_app_save_INS5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_card_app_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   PASSWD = dvobj.getRecord().get("PASSWD");
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   CARD_NUM = dvobj.getRecord().get("CARD_NUM");   //ī�� ��ȣ
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   APP_GBN = dvobj.getRecord().get("APP_GBN");   //��û������
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //��û ����
        String   VAILD_YRMM = dvobj.getRecord().get("VAILD_YRMM");
        String   PAYPRES_PHON_NUM = dvobj.getRecord().get("PAYPRES_PHON_NUM");
        String   CARD_CD = dvobj.getRecord().get("CARD_CD");   //ī��� �ڵ�
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   RELATION_CD = dvobj.getRecord().get("RELATION_CD");
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   SEQ = dobj.getRetObject("SEL1").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION (RELATION_CD, INSPRES_ID, PAYPRES_NM, SEQ, INS_DATE, CARD_CD, PAYPRES_PHON_NUM, VAILD_YRMM, APPTN_DAY, UPSO_CD, APP_GBN, RESINUM, CARD_NUM, BRAN_CD, PASSWD)  \n";
        query +=" values(:RELATION_CD , :INSPRES_ID , :PAYPRES_NM , :SEQ , SYSDATE, :CARD_CD , :PAYPRES_PHON_NUM , :VAILD_YRMM , :APPTN_DAY , :UPSO_CD , :APP_GBN , :RESINUM , :CARD_NUM , :BRAN_CD , :PASSWD )";
        sobj.setSql(query);
        sobj.setString("PASSWD", PASSWD);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("CARD_NUM", CARD_NUM);               //ī�� ��ȣ
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("APP_GBN", APP_GBN);               //��û������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("VAILD_YRMM", VAILD_YRMM);
        sobj.setString("PAYPRES_PHON_NUM", PAYPRES_PHON_NUM);
        sobj.setString("CARD_CD", CARD_CD);               //ī��� �ڵ�
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("RELATION_CD", RELATION_CD);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    //##**$$auto_card_app_save
    //##**$$auto_card_fileup
    /*
    */
    public DOBJ CTLauto_card_fileup(DOBJ dobj)
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
            dobj  = CALLauto_card_fileup_SEL_FILE(Conn, dobj);           //  ���������
            dobj  = CALLauto_card_fileup_MIUD1(Conn, dobj);           //  ���Ҽ��� ÷������
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
    public DOBJ CTLauto_card_fileup( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_card_fileup_SEL_FILE(Conn, dobj);           //  ���������
        dobj  = CALLauto_card_fileup_MIUD1(Conn, dobj);           //  ���Ҽ��� ÷������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ���������
    public DOBJ CALLauto_card_fileup_SEL_FILE(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL_FILE");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL_FILE");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_card_fileup_SEL_FILE(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL_FILE");
        rvobj.Println("SEL_FILE");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_card_fileup_SEL_FILE(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DAY ="";        //��� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '/upload_file/GIBU/APPLICATION_CARD/'  ||  SUBSTR(TO_CHAR(SYSDATE,'YYYYMM'),  1,  6)  AS  DFILEPATH  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // ���Ҽ��� ÷������
    public DOBJ CALLauto_card_fileup_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLauto_card_fileup_SEL25(Conn, dobj);           //  ��������ο� ���ϸ�
                dobj  = CALLauto_card_fileup_FUT26( dobj);        //  �����̵�
                dobj  = CALLauto_card_fileup_FUT27( dobj);        //  �����̸��ٲٱ�
                dobj  = CALLauto_card_fileup_INS31(Conn, dobj);           //  ���Ͼ��ε���������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_card_fileup_SEL31(Conn, dobj);           //  �������� ��ȸ(�������)
                dobj  = CALLauto_card_fileup_FUT32( dobj);        //  ���ϻ���
                dobj  = CALLauto_card_fileup_SEL33(Conn, dobj);           //  ��������ο� ���ϸ�
                dobj  = CALLauto_card_fileup_FUT34( dobj);        //  �����̵�
                dobj  = CALLauto_card_fileup_FUT35( dobj);        //  �����̸��ٲٱ�
                dobj  = CALLauto_card_fileup_UPD36(Conn, dobj);           //  ���Ͼ��ε����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_card_fileup_FUT39( dobj);        //  ���ϻ���
                dobj  = CALLauto_card_fileup_DEL40(Conn, dobj);           //  ��������
            }
        }
        return dobj;
    }
    // ���ϻ���
    public DOBJ CALLauto_card_fileup_FUT39(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT39");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT39");
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            wutil.delete(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString() );
        }
        dvobj.setName("FUT39") ;
        dvobj.Println("FUT39");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �������� ��ȸ(�������)
    public DOBJ CALLauto_card_fileup_SEL31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL31");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL31");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_card_fileup_SEL31(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL31");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_card_fileup_SEL31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SVR_FILE_NM  ,  SVR_FILE_ROUT  FROM  GIBU.TBRA_UPSO_AUTO_CARD_DOC_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // ��������ο� ���ϸ�
    public DOBJ CALLauto_card_fileup_SEL25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL25");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL25");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_card_fileup_SEL25(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL25");
        rvobj.Println("SEL25");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_card_fileup_SEL25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DAY ="";        //��� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //������ȣ
        String   FILE_NM = dobj.getRetObject("R").getRecord().get("FILE_NM");   //���� ��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  ||  :UPSO_CD  ||  '-'  ||  TO_CHAR(SYSDATE,'yyyymmdd')  ||  '-'  ||  :SEQ  ||  '-'  ||  TO_CHAR  (SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR  (  :FILE_NM,  INSTR  (  :FILE_NM,  '.',  '-1'))  AS  DFILENAME  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        return sobj;
    }
    // ��������
    public DOBJ CALLauto_card_fileup_DEL40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL40");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL40");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_card_fileup_DEL40(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL40") ;
        rvobj.Println("DEL40");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_card_fileup_DEL40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_AUTO_CARD_DOC_ATTCH  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ϻ���
    public DOBJ CALLauto_card_fileup_FUT32(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT32");
        VOBJ dvobj = dobj.getRetObject("SEL31");      //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("FUT32");
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            wutil.delete(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString() );
        }
        dvobj.setName("FUT32") ;
        dvobj.Println("FUT32");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �����̵�
    public DOBJ CALLauto_card_fileup_FUT26(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT26");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT26");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT26") ;
        dvobj.Println("FUT26");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �����̸��ٲٱ�
    public DOBJ CALLauto_card_fileup_FUT27(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT27");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT27");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dobj.getRetObject("SEL25").getRecord().get("DFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.rename(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILENAME").toString());
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT27") ;
        dvobj.Println("FUT27");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ��������ο� ���ϸ�
    public DOBJ CALLauto_card_fileup_SEL33(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL33");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL33");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_card_fileup_SEL33(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL33");
        rvobj.Println("SEL33");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_card_fileup_SEL33(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INS_DAY = dobj.getRetObject("R").getRecord().get("INS_DAY");   //��� ����
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //������ȣ
        String   FILE_NM = dobj.getRetObject("R").getRecord().get("FILE_NM");   //���� ��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  ||  :UPSO_CD  ||  '-'  ||  :INS_DAY  ||  '-'  ||  :SEQ  ||  '-'  ||  TO_CHAR  (SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR  (  :FILE_NM,  INSTR  (  :FILE_NM,  '.',  '-1'))  AS  DFILENAME  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        return sobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLauto_card_fileup_INS31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS31");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS31");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_card_fileup_INS31(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS31") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_card_fileup_INS31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   FILE_NM = dvobj.getRecord().get("FILE_NM");   //���� ��
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   SVR_FILE_NM = dobj.getRetObject("SEL25").getRecord().get("DFILENAME");   //���� ���� ��
        String   SVR_FILE_ROUT = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");   //���� ���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO_CARD_DOC_ATTCH (INS_DATE, INSPRES_ID, UPSO_CD, SVR_FILE_ROUT, SVR_FILE_NM, SEQ, REMAK, FILE_NM)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :UPSO_CD , :SVR_FILE_ROUT , :SVR_FILE_NM , :SEQ , :REMAK , :FILE_NM )";
        sobj.setSql(query);
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        sobj.setString("REMAK", REMAK);               //���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("SVR_FILE_NM", SVR_FILE_NM);               //���� ���� ��
        sobj.setString("SVR_FILE_ROUT", SVR_FILE_ROUT);               //���� ���� ���
        return sobj;
    }
    // �����̵�
    public DOBJ CALLauto_card_fileup_FUT34(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT34");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT34");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT34") ;
        dvobj.Println("FUT34");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �����̸��ٲٱ�
    public DOBJ CALLauto_card_fileup_FUT35(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT35");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT35");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dobj.getRetObject("SEL33").getRecord().get("DFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.rename(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILENAME").toString());
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT35") ;
        dvobj.Println("FUT35");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ͼ��ε����
    public DOBJ CALLauto_card_fileup_UPD36(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD36");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD36");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_card_fileup_UPD36(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD36") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_card_fileup_UPD36(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   FILE_NM = dvobj.getRecord().get("FILE_NM");   //���� ��
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   SVR_FILE_NM = dobj.getRetObject("SEL33").getRecord().get("DFILENAME");   //���� ���� ��
        String   SVR_FILE_ROUT = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");   //���� ���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO_CARD_DOC_ATTCH  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MOD_DATE=SYSDATE , SVR_FILE_ROUT=:SVR_FILE_ROUT , SVR_FILE_NM=:SVR_FILE_NM , REMAK=:REMAK , FILE_NM=:FILE_NM  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        sobj.setString("REMAK", REMAK);               //���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("SVR_FILE_NM", SVR_FILE_NM);               //���� ���� ��
        sobj.setString("SVR_FILE_ROUT", SVR_FILE_ROUT);               //���� ���� ���
        return sobj;
    }
    //##**$$auto_card_fileup
    //##**$$auto_card_file_search
    /*
    */
    public DOBJ CTLauto_card_file_search(DOBJ dobj)
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
            dobj  = CALLauto_card_file_search_SEL1(Conn, dobj);           //  ÷��������ȸ
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
    public DOBJ CTLauto_card_file_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_card_file_search_SEL1(Conn, dobj);           //  ÷��������ȸ
        return dobj;
    }
    // ÷��������ȸ
    public DOBJ CALLauto_card_file_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_card_file_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_card_file_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   SEQ = dobj.getRetObject("S").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.SEQ  ,  A.FILE_NM  ,  A.SVR_FILE_NM  ,  A.SVR_FILE_ROUT  FROM  GIBU.TBRA_UPSO_AUTO_CARD_DOC_ATTCH  A  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.SEQ  =  :SEQ  ORDER  BY  SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    //##**$$auto_card_file_search
    //##**$$search_auto_card
    /*
    */
    public DOBJ CTLsearch_auto_card(DOBJ dobj)
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
            dobj  = CALLsearch_auto_card_SEL1(Conn, dobj);           //  ������ȸ
            dobj  = CALLsearch_auto_card_SEL3(Conn, dobj);           //  ��û���̷���ȸ
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
    public DOBJ CTLsearch_auto_card( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_auto_card_SEL1(Conn, dobj);           //  ������ȸ
        dobj  = CALLsearch_auto_card_SEL3(Conn, dobj);           //  ��û���̷���ȸ
        return dobj;
    }
    // ������ȸ
    public DOBJ CALLsearch_auto_card_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_auto_card_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_auto_card_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  AS  BRAN_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD  ||  XB.UPSO_GRAD  AS  GRAD  ,  XB.GRADNM  ,  XA.STAFF_CD  ,  XC.HAN_NM  AS  STAFF_NM  ,  XB.MONPRNCFEE  ,  XA.UPSO_PHON  ,  XA.MNGEMSTR_NM  ,  XA.MNGEMSTR_RESINUM  ,  SUBSTR(REPLACE(XA.MNGEMSTR_HPNUM,  '-',  ''),  1,  11)  AS  MNGEMSTR_HPNUM  ,  XA.PERMMSTR_NM  ,  XA.PERMMSTR_RESINUM  ,  SUBSTR(REPLACE(XA.PERMMSTR_HPNUM,  '-',  ''),  1,  11)  AS  PERMMSTR_HPNUM  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,'',  '',',  '  ||  XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  AS  ADDR  ,  XA.CLIENT_NUM  ,  XA.CLSBS_YRMN  ||  '01'  AS  CLSBS_YRMN  ,  GIBU.FT_GET_AUTO_BANK_USE(XA.UPSO_CD)  CNT_AUTO_B  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  AS  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  AS  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  INSA.TINS_MST01  XC  ,  INSA.TCPM_DEPT  XD  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.STAFF_NUM(+)  =  XA.STAFF_CD   \n";
        query +=" AND  XD.GIBU  =  XA.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��û���̷���ȸ
    public DOBJ CALLsearch_auto_card_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_auto_card_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_auto_card_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,UPSO_CD  ,SEQ  ,CARD_CD  ,CARD_NUM  ,RESINUM  ,PAYPRES_NM  ,PAYPRES_PHON_NUM  ,RELATION_CD  ,VAILD_YRMM  ,APPTN_DAY  ,PASSWD  ,APP_GBN  ,  NVL2(A.CONFIRM_DATE,'1','0')  CONFIRM_YN  ,FIDU.GET_STAFF_NM(CONFIRM_ID)  CONFIRM_NM  ,TO_CHAR(INS_DATE,'yyyymmdd')  INS_DATE  ,FIDU.GET_STAFF_NM(INSPRES_ID)  INSPRES_NM  ,MOD_DATE  ,MODPRES_ID  FROM  GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION  A  WHERE  1=1   \n";
        query +=" AND  BRAN_CD  =:BRAN_CD   \n";
        query +=" AND  UPSO_CD  =:UPSO_CD  ORDER  BY  1,2,3 ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$search_auto_card
    //##**$$end
}
