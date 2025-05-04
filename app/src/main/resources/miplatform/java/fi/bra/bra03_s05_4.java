
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s05_4
{
    public bra03_s05_4()
    {
    }
    //##**$$auto_apptn_save_temp
    /*
    */
    public DOBJ CTLauto_apptn_save_temp(DOBJ dobj)
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
            dobj  = CALLauto_apptn_save_temp_XIUD2(Conn, dobj);           //  ���̺�TRUNCATE
            dobj  = CALLauto_apptn_save_temp_INS3(Conn, dobj);           //  82�������� ����
            dobj  = CALLauto_apptn_save_temp_XIUD4(Conn, dobj);           //  Ŀ��
            dobj  = CALLauto_apptn_save_temp_SEL1(Conn, dobj);           //  GE12���ϻ���������ȸ
            dobj  = CALLauto_apptn_save_temp_MPD6(Conn, dobj);           //  ÷�����ϻ����б�
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
    public DOBJ CTLauto_apptn_save_temp( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_apptn_save_temp_XIUD2(Conn, dobj);           //  ���̺�TRUNCATE
        dobj  = CALLauto_apptn_save_temp_INS3(Conn, dobj);           //  82�������� ����
        dobj  = CALLauto_apptn_save_temp_XIUD4(Conn, dobj);           //  Ŀ��
        dobj  = CALLauto_apptn_save_temp_SEL1(Conn, dobj);           //  GE12���ϻ���������ȸ
        dobj  = CALLauto_apptn_save_temp_MPD6(Conn, dobj);           //  ÷�����ϻ����б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ���̺�TRUNCATE
    public DOBJ CALLauto_apptn_save_temp_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_save_temp_XIUD2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_save_temp_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" TRUNCATE  TABLE  GIBU.TBRA_UPSO_AUTO_82TEMP ";
        sobj.setSql(query);
        return sobj;
    }
    // 82�������� ����
    public DOBJ CALLauto_apptn_save_temp_INS3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_save_temp_INS3(dobj, dvobj);
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
    private SQLObject SQLauto_apptn_save_temp_INS3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //����ó ����
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //��û ����
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   RECPTNUM = dvobj.getRecord().get("RECPTNUM");   //������ȣ
        String   PAY_BANK_CD = dvobj.getRecord().get("PAY_BANK_CD");   //���� ���� �ڵ�
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //��û ����
        String   PAY_KND = dvobj.getRecord().get("PAY_KND");   //��� ����
        String   PAY_REQDAY = dvobj.getRecord().get("PAY_REQDAY");   //���� �������
        String   CHGBFR_PAYPRES_NUM = dvobj.getRecord().get("CHGBFR_PAYPRES_NUM");   //������ ������ ��ȣ
        String   SEQ_NUM = dvobj.getRecord().get("SEQ_NUM");   //�Ϸ� ��ȣ
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //���� ������
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //��� ���� ��ȣ
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //������ ������ ��ȣ
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //������ �ڵ�
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //������ ��ȭ��ȣ
        String   APPTN_RSLT = dvobj.getRecord().get("APPTN_RSLT");   //��û ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO_82TEMP (APPTN_RSLT, PAYER_PHONNUM, PAYPRES_NM, RECPT_BANK_CD, CHGATR_PAYPRES_NUM, PAY_ACCN_NUM, BEGNG_RELSDAY, SEQ_NUM, CHGBFR_PAYPRES_NUM, PAY_REQDAY, PAY_KND, APPTN_DAY, PAY_BANK_CD, RECPTNUM, UPSO_CD, RESINUM, APPTN_GBN, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYER_PHONNUM , :PAYPRES_NM , :RECPT_BANK_CD , :CHGATR_PAYPRES_NUM , :PAY_ACCN_NUM , :BEGNG_RELSDAY , :SEQ_NUM , :CHGBFR_PAYPRES_NUM , :PAY_REQDAY , :PAY_KND , :APPTN_DAY , :PAY_BANK_CD , :RECPTNUM , :UPSO_CD , :RESINUM , :APPTN_GBN , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //����ó ����
        sobj.setString("APPTN_GBN", APPTN_GBN);               //��û ����
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("RECPTNUM", RECPTNUM);               //������ȣ
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //���� ���� �ڵ�
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("PAY_KND", PAY_KND);               //��� ����
        sobj.setString("PAY_REQDAY", PAY_REQDAY);               //���� �������
        sobj.setString("CHGBFR_PAYPRES_NUM", CHGBFR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("SEQ_NUM", SEQ_NUM);               //�Ϸ� ��ȣ
        sobj.setString("BEGNG_RELSDAY", BEGNG_RELSDAY);               //���� ������
        sobj.setString("PAY_ACCN_NUM", PAY_ACCN_NUM);               //��� ���� ��ȣ
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //������ ������ ��ȣ
        sobj.setString("RECPT_BANK_CD", RECPT_BANK_CD);               //������ �ڵ�
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("PAYER_PHONNUM", PAYER_PHONNUM);               //������ ��ȭ��ȣ
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //��û ���
        return sobj;
    }
    // Ŀ��
    public DOBJ CALLauto_apptn_save_temp_XIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD4");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_save_temp_XIUD4(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_save_temp_XIUD4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" COMMIT ";
        sobj.setSql(query);
        return sobj;
    }
    // GE12���ϻ���������ȸ
    public DOBJ CALLauto_apptn_save_temp_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_apptn_save_temp_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_save_temp_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(SEQ_NUM,  8,  '0')  AS  SEQ_NUM  ,  CHGATR_PAYPRES_NUM  ,  SUBSTR(PAY_BANK_CD,  0,  3)  AS  BANK_CD  ,  PAY_ACCN_NUM  ,  APPTN_DAY  ,  SUBSTR(FILE_TEMPNM,  INSTR(FILE_TEMPNM,  '.',  LENGTH(FILE_TEMPNM)  -  5)  +  1)  AS  FILE_EXTENTION  ,  FILE_ROUT  ,  FILE_NM  ,  SEQ  ,  A.UPSO_CD  AS  UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO_82TEMP  A  ,  GIBU.TBRA_UPSO_AUTO_DOC_ATTCH  B  WHERE  A.UPSO_CD  =  B.UPSO_CD(+)   \n";
        query +=" AND  A.APPTN_GBN  =  '02'   \n";
        query +=" AND  B.SEQ  =   \n";
        query +=" (SELECT  mAX(SEQ)  FROM  GIBU.TBRA_UPSO_AUTO_DOC_ATTCH  WHERE  UPSO_CD  =  A.UPSO_CD)  ORDER  BY  SEQ_NUM  ASC ";
        sobj.setSql(query);
        return sobj;
    }
    // ÷�����ϻ����б�
    public DOBJ CALLauto_apptn_save_temp_MPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD6");
        VOBJ dvobj = dobj.getRetObject("SEL1");         //GE12���ϻ���������ȸ���� ������Ų OBJECT�Դϴ�.(CALLauto_apptn_save_temp_SEL1)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( 1 == 1)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_apptn_save_temp_SEL7(Conn, dobj);           //  ÷�����ϻ���
            }
        }
        return dobj;
    }
    // ÷�����ϻ���
    public DOBJ CALLauto_apptn_save_temp_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_apptn_save_temp_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        String fullname="";
        rvobj.first();
        while(rvobj.next())
        {
            wutil.makeFileOverwirte( dobj.getRetObject("R").getRecord().get("FILE_ROUT"), dobj.getRetObject("R").getRecord().get("FILE_NM"),rvobj.getRecord().getBytes("FILES"));
        }
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_save_temp_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  FILE_ROUT  ,  FILE_NM  ,  FILES  FROM  GIBU.TBRA_UPSO_AUTO_DOC_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    //##**$$auto_apptn_save_temp
    //##**$$end
}
