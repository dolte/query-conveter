
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s15
{
    public bra10_s15()
    {
    }
    //##**$$contr_match_upload
    /*
    */
    public DOBJ CTLcontr_match_upload(DOBJ dobj)
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
            dobj  = CALLcontr_match_upload_SEL1(Conn, dobj);           //  ���ε��ڷ���ȸ
            dobj  = CALLcontr_match_upload_MPD3(Conn, dobj);           //  �б�
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLcontr_match_upload_XIUD10(Conn, dobj);           //  TEMP���̺����
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
    public DOBJ CTLcontr_match_upload( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcontr_match_upload_SEL1(Conn, dobj);           //  ���ε��ڷ���ȸ
        dobj  = CALLcontr_match_upload_MPD3(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLcontr_match_upload_XIUD10(Conn, dobj);           //  TEMP���̺����
        return dobj;
    }
    // ���ε��ڷ���ȸ
    public DOBJ CALLcontr_match_upload_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLcontr_match_upload_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_match_upload_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BSCON_CD  ,  BSCON_UPSO_CD  ,  BSCON_UPSO_NM  ,  APPL_DAY  ,  UPSO_CD  ,  UPSO_ZIP  ,  UPSO_ADDR1  ,  UPSO_ADDR2  ,  MONPRNCFEE  ,  INSPRES_ID  ,  INS_DATE  ,  FILE_NM  ,  BSTYP_CD  ,  ATAX_YN  FROM  GIBU.TBRA_BSCON_CONTRINFO_TEMP ";
        sobj.setSql(query);
        return sobj;
    }
    // �б�
    public DOBJ CALLcontr_match_upload_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("SEL1");         //���ε��ڷ���ȸ���� ������Ų OBJECT�Դϴ�.(CALLcontr_match_upload_SEL1)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLcontr_match_upload_SEL4(Conn, dobj);           //  ������Ͽ�����ȸ
                if( dobj.getRetObject("SEL4").getRecord().getDouble("CNT") > 0)
                {
                    dobj  = CALLcontr_match_upload_XIUD11(Conn, dobj);           //  ����������� ��
                    dobj  = CALLcontr_match_upload_SEL8(Conn, dobj);           //  ����ä��
                    dobj  = CALLcontr_match_upload_INS9(Conn, dobj);           //  ��������Է�
                }
                else
                {
                    dobj  = CALLcontr_match_upload_SEL8(Conn, dobj);           //  ����ä��
                    dobj  = CALLcontr_match_upload_INS9(Conn, dobj);           //  ��������Է�
                }
            }
        }
        return dobj;
    }
    // ������Ͽ�����ȸ
    public DOBJ CALLcontr_match_upload_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLcontr_match_upload_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_match_upload_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   UPSO_ZIP = dobj.getRetObject("R").getRecord().get("UPSO_ZIP");   //���� �����ȣ
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  BSCON_UPSO_CD  =  :BSCON_UPSO_CD   \n";
        query +=" AND  UPSO_ZIP  =  :UPSO_ZIP ";
        sobj.setSql(query);
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        return sobj;
    }
    // ����������� ��
    // �������� ��뱸�� N���� ����
    public DOBJ CALLcontr_match_upload_XIUD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD11");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcontr_match_upload_XIUD11(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_match_upload_XIUD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   UPSO_ZIP = dobj.getRetObject("R").getRecord().get("UPSO_ZIP");   //���� �����ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_BSCON_CONTRINFO  \n";
        query +=" SET MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE , USE_YN = 'N'  \n";
        query +=" WHERE BSCON_CD = :BSCON_CD  \n";
        query +=" AND BSCON_UPSO_CD = :BSCON_UPSO_CD  \n";
        query +=" AND UPSO_ZIP = :UPSO_ZIP  \n";
        query +=" AND USE_YN = 'Y'";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
        return sobj;
    }
    // ����ä��
    public DOBJ CALLcontr_match_upload_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLcontr_match_upload_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_match_upload_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   UPSO_ZIP = dobj.getRetObject("R").getRecord().get("UPSO_ZIP");   //���� �����ȣ
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  AS  SEQ  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  BSCON_UPSO_CD  =  :BSCON_UPSO_CD   \n";
        query +=" AND  UPSO_ZIP  =  :UPSO_ZIP ";
        sobj.setSql(query);
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        return sobj;
    }
    // ��������Է�
    public DOBJ CALLcontr_match_upload_INS9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcontr_match_upload_INS9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_match_upload_INS9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //���� �����ȣ
        String   UPSO_ADDR1 = dvobj.getRecord().get("UPSO_ADDR1");   //���� �ּ�1
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   BSCON_UPSO_NM = dvobj.getRecord().get("BSCON_UPSO_NM");   //Ÿ��ü���Ҹ�
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //������
        String   BSCON_UPSO_CD = dvobj.getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   ATAX_YN = dvobj.getRecord().get("ATAX_YN");   //�ΰ��� ����
        String   UPSO_ADDR2 = dvobj.getRecord().get("UPSO_ADDR2");   //���� �ּ�2
        String   APPL_DAY = dvobj.getRecord().get("APPL_DAY");   //���� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   MATCH_GBN ="N";   //��Ī ����
        double   SEQ = dobj.getRetObject("SEL8").getRecord().getDouble("SEQ");   //������ȣ
        String   USE_YN ="Y";   //��뱸��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BSCON_CONTRINFO (USE_YN, APPL_DAY, INSPRES_ID, UPSO_ADDR2, ATAX_YN, BSCON_UPSO_CD, MONPRNCFEE, BSTYP_CD, BSCON_UPSO_NM, SEQ, BSCON_CD, INS_DATE, UPSO_ADDR1, MATCH_GBN, UPSO_ZIP)  \n";
        query +=" values(:USE_YN , :APPL_DAY , :INSPRES_ID , :UPSO_ADDR2 , :ATAX_YN , :BSCON_UPSO_CD , :MONPRNCFEE , :BSTYP_CD , :BSCON_UPSO_NM , :SEQ , :BSCON_CD , SYSDATE, :UPSO_ADDR1 , :MATCH_GBN , :UPSO_ZIP )";
        sobj.setSql(query);
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
        sobj.setString("UPSO_ADDR1", UPSO_ADDR1);               //���� �ּ�1
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("BSCON_UPSO_NM", BSCON_UPSO_NM);               //Ÿ��ü���Ҹ�
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("ATAX_YN", ATAX_YN);               //�ΰ��� ����
        sobj.setString("UPSO_ADDR2", UPSO_ADDR2);               //���� �ּ�2
        sobj.setString("APPL_DAY", APPL_DAY);               //���� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("MATCH_GBN", MATCH_GBN);               //��Ī ����
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("USE_YN", USE_YN);               //��뱸��
        return sobj;
    }
    // TEMP���̺����
    public DOBJ CALLcontr_match_upload_XIUD10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD10");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcontr_match_upload_XIUD10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_match_upload_XIUD10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" TRUNCATE  TABLE  GIBU.TBRA_BSCON_CONTRINFO_TEMP ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$contr_match_upload
    //##**$$end
}
