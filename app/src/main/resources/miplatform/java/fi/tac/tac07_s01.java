
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac07_s01
{
    public tac07_s01()
    {
    }
    //##**$$tac07_s01_select
    /* * ���α׷��� : tac07_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/18
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLtac07_s01_select(DOBJ dobj)
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
            dobj  = CALLtac07_s01_select_SEL1(Conn, dobj);           //  ä���� �����Ȳ
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
    public DOBJ CTLtac07_s01_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac07_s01_select_SEL1(Conn, dobj);           //  ä���� �����Ȳ
        return dobj;
    }
    // ä���� �����Ȳ
    public DOBJ CALLtac07_s01_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtac07_s01_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac07_s01_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '��  ��:  '  ||  ADDR  ||  ADDR_DETED  AS  ADDR,  '��ȭ��ȣ:  '  ||  PHON_NUM  AS  PHON_NUM,  '�ѽ���ȣ:  '  ||  FAX_NUM  AS  FAX_NUM,  '��  ��  ��:  '  ||  EMAIL_ADDR  AS  EMAIL_ADDR,  '��  ��:  '  ||  REMAK  AS  REMAK  FROM  FIDU.TLEV_BSCON  WHERE  BSCON_CD  =  :MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    //##**$$tac07_s01_select
    //##**$$tac07_s01_memo
    /* * ���α׷��� : tac07_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/17
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLtac07_s01_memo(DOBJ dobj)
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
            dobj  = CALLtac07_s01_memo_SEL1(Conn, dobj);           //  ä��ä�� ȸ���޸�
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
    public DOBJ CTLtac07_s01_memo( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac07_s01_memo_SEL1(Conn, dobj);           //  ä��ä�� ȸ���޸�
        return dobj;
    }
    // ä��ä�� ȸ���޸�
    public DOBJ CALLtac07_s01_memo_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtac07_s01_memo_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac07_s01_memo_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MNG_NUM,  MB_CD,  MEMO_DAY,  MEMO_CTENT,  MEMO_GBN  FROM  FIDU.TMEM_MBMEMO  WHERE  MB_CD  =  :MB_CD   \n";
        query +=" AND  MEMO_GBN  ='2'  ORDER  BY  MNG_NUM ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    //##**$$tac07_s01_memo
    //##**$$tmem_claimdebt_save
    /* * ���α׷��� : tac07_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/26
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLtmem_claimdebt_save(DOBJ dobj)
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
            dobj  = CALLtmem_claimdebt_save_MIUD1(Conn, dobj);           //  ä��ä��MIUD
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
    public DOBJ CTLtmem_claimdebt_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtmem_claimdebt_save_MIUD1(Conn, dobj);           //  ä��ä��MIUD
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ä��ä��MIUD
    public DOBJ CALLtmem_claimdebt_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtmem_claimdebt_save_INS5(Conn, dobj);           //  ä��ä������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtmem_claimdebt_save_UPD6(Conn, dobj);           //  ä��ä������
            }
        }
        return dobj;
    }
    // ä��ä������
    public DOBJ CALLtmem_claimdebt_save_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtmem_claimdebt_save_INS5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        rvobj.Println("INS5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtmem_claimdebt_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        double MNG_NUM = 0;        //������ȣ
        String   FIXGRD_CTENT = dvobj.getRecord().get("FIXGRD_CTENT");   //�����ٰ� ����
        String   CLAIMPRES_MB_CD = dvobj.getRecord().get("CLAIMPRES_MB_CD");   //ä���� ȸ�� �ڵ�
        String   CLAIM_RFND_AMT = dvobj.getRecord().get("CLAIM_RFND_AMT");   //ä�ǻ�ȯ�ݾ�
        String   CLAIMPRES_BANK_CD = dvobj.getRecord().get("CLAIMPRES_BANK_CD");   //ä���� ���� �ڵ�
        double   CLAIM_BST_AMT = dvobj.getRecord().getDouble("CLAIM_BST_AMT");   //ä�� �ְ� �ݾ�
        double   CLAIM_PTTNRATE = dvobj.getRecord().getDouble("CLAIM_PTTNRATE");   //ä�� ������
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   CLAIMPRES_ACCN_NUM = dvobj.getRecord().get("CLAIMPRES_ACCN_NUM");   //ä���� ���� ��ȣ
        String   CLAIMPRES_MEMO = dvobj.getRecord().get("CLAIMPRES_MEMO");
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   DEBTPRES_GBN = dvobj.getRecord().get("DEBTPRES_GBN");
        String   CLAIMPRES_NM = dvobj.getRecord().get("CLAIMPRES_NM");   //ä���� ��
        String   REPAYPROC_START_DAY = dvobj.getRecord().get("REPAYPROC_START_DAY");   //����ó�� ���� ����
        String   COMPL_YN = dvobj.getRecord().get("COMPL_YN");   //�Ϸ� ����
        String   REPAYPROC_END_YRMN = dvobj.getRecord().get("REPAYPROC_END_YRMN");   //����ó�� ������
        String   REPAYPROC_END_DAY = dvobj.getRecord().get("REPAYPROC_END_DAY");   //����ó�� ���� ����
        String   CLAIM_KND = dvobj.getRecord().get("CLAIM_KND");   //ä�� ����
        String   REPTACCN_MEMO = dvobj.getRecord().get("REPTACCN_MEMO");   //�Աݰ��¸޸�
        String   REPAYPROC_START_YRMN = dvobj.getRecord().get("REPAYPROC_START_YRMN");   //����ó�� ���۳��
        String   SUPP_GBN = dvobj.getRecord().get("SUPP_GBN");   //���� ����
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //����� ID
        String   CLAIMDEBT_GBN = dvobj.getRecord().get("CLAIMDEBT_GBN");   //ä��ä�� ����
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TMEM_CLAIMDEBT (MODPRES_ID, CLAIMDEBT_GBN, INSPRES_ID, SUPP_GBN, REPAYPROC_START_YRMN, REPTACCN_MEMO, CLAIM_KND, REPAYPROC_END_DAY, INS_DATE, REPAYPROC_END_YRMN, COMPL_YN, REPAYPROC_START_DAY, CLAIMPRES_NM, DEBTPRES_GBN, REMAK, MNG_NUM, CLAIMPRES_MEMO, CLAIMPRES_ACCN_NUM, MB_CD, CLAIM_PTTNRATE, CLAIM_BST_AMT, CLAIMPRES_BANK_CD, CLAIM_RFND_AMT, CLAIMPRES_MB_CD, FIXGRD_CTENT)  \n";
        query +=" values(:MODPRES_ID , :CLAIMDEBT_GBN , :INSPRES_ID , :SUPP_GBN , :REPAYPROC_START_YRMN , :REPTACCN_MEMO , :CLAIM_KND , :REPAYPROC_END_DAY , SYSDATE, :REPAYPROC_END_YRMN , :COMPL_YN , :REPAYPROC_START_DAY , :CLAIMPRES_NM , :DEBTPRES_GBN , :REMAK , (SELECT NVL(MAX(MNG_NUM),0) + 1 AS MNG_NUM from FIDU.TMEM_CLAIMDEBT), :CLAIMPRES_MEMO , :CLAIMPRES_ACCN_NUM , :MB_CD , :CLAIM_PTTNRATE , :CLAIM_BST_AMT , :CLAIMPRES_BANK_CD , :CLAIM_RFND_AMT , :CLAIMPRES_MB_CD , :FIXGRD_CTENT )";
        sobj.setSql(query);
        sobj.setString("FIXGRD_CTENT", FIXGRD_CTENT);               //�����ٰ� ����
        sobj.setString("CLAIMPRES_MB_CD", CLAIMPRES_MB_CD);               //ä���� ȸ�� �ڵ�
        sobj.setString("CLAIM_RFND_AMT", CLAIM_RFND_AMT);               //ä�ǻ�ȯ�ݾ�
        sobj.setString("CLAIMPRES_BANK_CD", CLAIMPRES_BANK_CD);               //ä���� ���� �ڵ�
        sobj.setDouble("CLAIM_BST_AMT", CLAIM_BST_AMT);               //ä�� �ְ� �ݾ�
        sobj.setDouble("CLAIM_PTTNRATE", CLAIM_PTTNRATE);               //ä�� ������
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("CLAIMPRES_ACCN_NUM", CLAIMPRES_ACCN_NUM);               //ä���� ���� ��ȣ
        sobj.setString("CLAIMPRES_MEMO", CLAIMPRES_MEMO);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("DEBTPRES_GBN", DEBTPRES_GBN);
        sobj.setString("CLAIMPRES_NM", CLAIMPRES_NM);               //ä���� ��
        sobj.setString("REPAYPROC_START_DAY", REPAYPROC_START_DAY);               //����ó�� ���� ����
        sobj.setString("COMPL_YN", COMPL_YN);               //�Ϸ� ����
        sobj.setString("REPAYPROC_END_YRMN", REPAYPROC_END_YRMN);               //����ó�� ������
        sobj.setString("REPAYPROC_END_DAY", REPAYPROC_END_DAY);               //����ó�� ���� ����
        sobj.setString("CLAIM_KND", CLAIM_KND);               //ä�� ����
        sobj.setString("REPTACCN_MEMO", REPTACCN_MEMO);               //�Աݰ��¸޸�
        sobj.setString("REPAYPROC_START_YRMN", REPAYPROC_START_YRMN);               //����ó�� ���۳��
        sobj.setString("SUPP_GBN", SUPP_GBN);               //���� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("CLAIMDEBT_GBN", CLAIMDEBT_GBN);               //ä��ä�� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // ä��ä������
    public DOBJ CALLtmem_claimdebt_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLtmem_claimdebt_save_UPD6(dobj, dvobj);
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
    private SQLObject SQLtmem_claimdebt_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FIXGRD_CTENT = dvobj.getRecord().get("FIXGRD_CTENT");   //�����ٰ� ����
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   CLAIMPRES_MB_CD = dvobj.getRecord().get("CLAIMPRES_MB_CD");   //ä���� ȸ�� �ڵ�
        String   DEBTPRES_GBN = dvobj.getRecord().get("DEBTPRES_GBN");
        String   CLAIMPRES_NM = dvobj.getRecord().get("CLAIMPRES_NM");   //ä���� ��
        String   REPAYPROC_START_DAY = dvobj.getRecord().get("REPAYPROC_START_DAY");   //����ó�� ���� ����
        String   CLAIMPRES_BANK_CD = dvobj.getRecord().get("CLAIMPRES_BANK_CD");   //ä���� ���� �ڵ�
        String   CLAIM_RFND_AMT = dvobj.getRecord().get("CLAIM_RFND_AMT");   //ä�ǻ�ȯ�ݾ�
        String   COMPL_YN = dvobj.getRecord().get("COMPL_YN");   //�Ϸ� ����
        double   CLAIM_BST_AMT = dvobj.getRecord().getDouble("CLAIM_BST_AMT");   //ä�� �ְ� �ݾ�
        String   REPAYPROC_END_YRMN = dvobj.getRecord().get("REPAYPROC_END_YRMN");   //����ó�� ������
        double   CLAIM_PTTNRATE = dvobj.getRecord().getDouble("CLAIM_PTTNRATE");   //ä�� ������
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   CLAIMPRES_ACCN_NUM = dvobj.getRecord().get("CLAIMPRES_ACCN_NUM");   //ä���� ���� ��ȣ
        String   REPAYPROC_END_DAY = dvobj.getRecord().get("REPAYPROC_END_DAY");   //����ó�� ���� ����
        String   CLAIM_KND = dvobj.getRecord().get("CLAIM_KND");   //ä�� ����
        String   REPAYPROC_START_YRMN = dvobj.getRecord().get("REPAYPROC_START_YRMN");   //����ó�� ���۳��
        String   REPTACCN_MEMO = dvobj.getRecord().get("REPTACCN_MEMO");   //�Աݰ��¸޸�
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   CLAIMPRES_MEMO = dvobj.getRecord().get("CLAIMPRES_MEMO");
        String   SUPP_GBN = dvobj.getRecord().get("SUPP_GBN");   //���� ����
        String   CLAIMDEBT_GBN = dvobj.getRecord().get("CLAIMDEBT_GBN");   //ä��ä�� ����
        String   MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //������ ID
        String   MOD_DATE = dobj.getRetObject("G").getRecord().get("MOD_DATE");   //���� �Ͻ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TMEM_CLAIMDEBT  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , CLAIMDEBT_GBN=:CLAIMDEBT_GBN , SUPP_GBN=:SUPP_GBN , CLAIMPRES_MEMO=:CLAIMPRES_MEMO , REPTACCN_MEMO=:REPTACCN_MEMO , REPAYPROC_START_YRMN=:REPAYPROC_START_YRMN , CLAIM_KND=:CLAIM_KND , REPAYPROC_END_DAY=:REPAYPROC_END_DAY , CLAIMPRES_ACCN_NUM=:CLAIMPRES_ACCN_NUM , CLAIM_PTTNRATE=:CLAIM_PTTNRATE , REPAYPROC_END_YRMN=:REPAYPROC_END_YRMN , CLAIM_BST_AMT=:CLAIM_BST_AMT , COMPL_YN=:COMPL_YN , CLAIM_RFND_AMT=:CLAIM_RFND_AMT , CLAIMPRES_BANK_CD=:CLAIMPRES_BANK_CD , REPAYPROC_START_DAY=:REPAYPROC_START_DAY , MOD_DATE=:MOD_DATE , CLAIMPRES_NM=:CLAIMPRES_NM , DEBTPRES_GBN=:DEBTPRES_GBN , CLAIMPRES_MB_CD=:CLAIMPRES_MB_CD , REMAK=:REMAK , FIXGRD_CTENT=:FIXGRD_CTENT  \n";
        query +=" where MNG_NUM=:MNG_NUM  \n";
        query +=" and MB_CD=:MB_CD";
        sobj.setSql(query);
        sobj.setString("FIXGRD_CTENT", FIXGRD_CTENT);               //�����ٰ� ����
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("CLAIMPRES_MB_CD", CLAIMPRES_MB_CD);               //ä���� ȸ�� �ڵ�
        sobj.setString("DEBTPRES_GBN", DEBTPRES_GBN);
        sobj.setString("CLAIMPRES_NM", CLAIMPRES_NM);               //ä���� ��
        sobj.setString("REPAYPROC_START_DAY", REPAYPROC_START_DAY);               //����ó�� ���� ����
        sobj.setString("CLAIMPRES_BANK_CD", CLAIMPRES_BANK_CD);               //ä���� ���� �ڵ�
        sobj.setString("CLAIM_RFND_AMT", CLAIM_RFND_AMT);               //ä�ǻ�ȯ�ݾ�
        sobj.setString("COMPL_YN", COMPL_YN);               //�Ϸ� ����
        sobj.setDouble("CLAIM_BST_AMT", CLAIM_BST_AMT);               //ä�� �ְ� �ݾ�
        sobj.setString("REPAYPROC_END_YRMN", REPAYPROC_END_YRMN);               //����ó�� ������
        sobj.setDouble("CLAIM_PTTNRATE", CLAIM_PTTNRATE);               //ä�� ������
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("CLAIMPRES_ACCN_NUM", CLAIMPRES_ACCN_NUM);               //ä���� ���� ��ȣ
        sobj.setString("REPAYPROC_END_DAY", REPAYPROC_END_DAY);               //����ó�� ���� ����
        sobj.setString("CLAIM_KND", CLAIM_KND);               //ä�� ����
        sobj.setString("REPAYPROC_START_YRMN", REPAYPROC_START_YRMN);               //����ó�� ���۳��
        sobj.setString("REPTACCN_MEMO", REPTACCN_MEMO);               //�Աݰ��¸޸�
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("CLAIMPRES_MEMO", CLAIMPRES_MEMO);
        sobj.setString("SUPP_GBN", SUPP_GBN);               //���� ����
        sobj.setString("CLAIMDEBT_GBN", CLAIMDEBT_GBN);               //ä��ä�� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("MOD_DATE", MOD_DATE);               //���� �Ͻ�
        return sobj;
    }
    //##**$$tmem_claimdebt_save
    //##**$$tmem_claimdebtpay_save
    /* * ���α׷��� : tac07_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/26
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLtmem_claimdebtpay_save(DOBJ dobj)
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
            dobj  = CALLtmem_claimdebtpay_save_MIUD1(Conn, dobj);           //  ä��ä���������
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
    public DOBJ CTLtmem_claimdebtpay_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtmem_claimdebtpay_save_MIUD1(Conn, dobj);           //  ä��ä���������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ä��ä���������
    public DOBJ CALLtmem_claimdebtpay_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtmem_claimdebtpay_save_INS5(Conn, dobj);           //  ä��ä������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtmem_claimdebtpay_save_UPD6(Conn, dobj);           //  ä��ä������
            }
        }
        return dobj;
    }
    // ä��ä������
    public DOBJ CALLtmem_claimdebtpay_save_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtmem_claimdebtpay_save_INS5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        rvobj.Println("INS5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtmem_claimdebtpay_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   CLAIMPRES_ACCN_NUM = dvobj.getRecord().get("CLAIMPRES_ACCN_NUM");   //ä���� ���� ��ȣ
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   CLAIMPRES_MB_CD = dvobj.getRecord().get("CLAIMPRES_MB_CD");   //ä���� ȸ�� �ڵ�
        double   REPAY_AMT = dvobj.getRecord().getDouble("REPAY_AMT");   //���� �ݾ�
        String   CLAIMPRES_BANK_CD = dvobj.getRecord().get("CLAIMPRES_BANK_CD");   //ä���� ���� �ڵ�
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        String   REPAY_YRMN = dvobj.getRecord().get("REPAY_YRMN");   //���� ���
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TMEM_CLAIMDEBTREPAY (MB_CD, REPAY_YRMN, TRNSF_GBN, MNG_NUM, CLAIMPRES_BANK_CD, REPAY_AMT, CLAIMPRES_MB_CD, REMAK, CLAIMPRES_ACCN_NUM)  \n";
        query +=" values(:MB_CD , :REPAY_YRMN , :TRNSF_GBN , :MNG_NUM , :CLAIMPRES_BANK_CD , :REPAY_AMT , :CLAIMPRES_MB_CD , :REMAK , :CLAIMPRES_ACCN_NUM )";
        sobj.setSql(query);
        sobj.setString("CLAIMPRES_ACCN_NUM", CLAIMPRES_ACCN_NUM);               //ä���� ���� ��ȣ
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("CLAIMPRES_MB_CD", CLAIMPRES_MB_CD);               //ä���� ȸ�� �ڵ�
        sobj.setDouble("REPAY_AMT", REPAY_AMT);               //���� �ݾ�
        sobj.setString("CLAIMPRES_BANK_CD", CLAIMPRES_BANK_CD);               //ä���� ���� �ڵ�
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("REPAY_YRMN", REPAY_YRMN);               //���� ���
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    // ä��ä������
    public DOBJ CALLtmem_claimdebtpay_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLtmem_claimdebtpay_save_UPD6(dobj, dvobj);
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
    private SQLObject SQLtmem_claimdebtpay_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //������ ID
        String MOD_DATE = dobj.getRetObject("G").getRecord().get("MOD_DATE");        //���� �Ͻ�
        String   CLAIMPRES_ACCN_NUM = dvobj.getRecord().get("CLAIMPRES_ACCN_NUM");   //ä���� ���� ��ȣ
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   CLAIMPRES_MB_CD = dvobj.getRecord().get("CLAIMPRES_MB_CD");   //ä���� ȸ�� �ڵ�
        double   REPAY_AMT = dvobj.getRecord().getDouble("REPAY_AMT");   //���� �ݾ�
        String   CLAIMPRES_BANK_CD = dvobj.getRecord().get("CLAIMPRES_BANK_CD");   //ä���� ���� �ڵ�
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        String   REPAY_YRMN = dvobj.getRecord().get("REPAY_YRMN");   //���� ���
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TMEM_CLAIMDEBTREPAY  \n";
        query +=" set CLAIMPRES_BANK_CD=:CLAIMPRES_BANK_CD , REPAY_AMT=:REPAY_AMT , REMAK=:REMAK , CLAIMPRES_ACCN_NUM=:CLAIMPRES_ACCN_NUM  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and REPAY_YRMN=:REPAY_YRMN  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN  \n";
        query +=" and MNG_NUM=:MNG_NUM  \n";
        query +=" and CLAIMPRES_MB_CD=:CLAIMPRES_MB_CD";
        sobj.setSql(query);
        sobj.setString("CLAIMPRES_ACCN_NUM", CLAIMPRES_ACCN_NUM);               //ä���� ���� ��ȣ
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("CLAIMPRES_MB_CD", CLAIMPRES_MB_CD);               //ä���� ȸ�� �ڵ�
        sobj.setDouble("REPAY_AMT", REPAY_AMT);               //���� �ݾ�
        sobj.setString("CLAIMPRES_BANK_CD", CLAIMPRES_BANK_CD);               //ä���� ���� �ڵ�
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("REPAY_YRMN", REPAY_YRMN);               //���� ���
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    //##**$$tmem_claimdebtpay_save
    //##**$$tac07_s01paydelete
    /* * ���α׷��� : tac07_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/26
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLtac07_s01paydelete(DOBJ dobj)
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
            dobj  = CALLtac07_s01paydelete_DEL1(Conn, dobj);           //  ä��ä��������
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
    public DOBJ CTLtac07_s01paydelete( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac07_s01paydelete_DEL1(Conn, dobj);           //  ä��ä��������
        return dobj;
    }
    // ä��ä��������
    public DOBJ CALLtac07_s01paydelete_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtac07_s01paydelete_DEL1(dobj, dvobj);
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
    private SQLObject SQLtac07_s01paydelete_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLAIMPRES_MB_CD = dvobj.getRecord().get("CLAIMPRES_MB_CD");   //ä���� ȸ�� �ڵ�
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        String   REPAY_YRMN = dvobj.getRecord().get("REPAY_YRMN");   //���� ���
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TMEM_CLAIMDEBTREPAY  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and REPAY_YRMN=:REPAY_YRMN  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN  \n";
        query +=" and MNG_NUM=:MNG_NUM  \n";
        query +=" and CLAIMPRES_MB_CD=:CLAIMPRES_MB_CD";
        sobj.setSql(query);
        sobj.setString("CLAIMPRES_MB_CD", CLAIMPRES_MB_CD);               //ä���� ȸ�� �ڵ�
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("REPAY_YRMN", REPAY_YRMN);               //���� ���
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    //##**$$tac07_s01paydelete
    //##**$$tac07_s01select
    /* * ���α׷��� : tac07_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/26
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLtac07_s01select(DOBJ dobj)
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
            dobj  = CALLtac07_s01select_SEL1(Conn, dobj);           //  ä��ä����ȸ
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
    public DOBJ CTLtac07_s01select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac07_s01select_SEL1(Conn, dobj);           //  ä��ä����ȸ
        return dobj;
    }
    // ä��ä����ȸ
    public DOBJ CALLtac07_s01select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtac07_s01select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac07_s01select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        double   MNG_NUM = dobj.getRetObject("S").getRecord().getDouble("MNG_NUM");   //������ȣ
        String   CLAIMPRES_MB_CD = dobj.getRetObject("S").getRecord().get("CLAIMPRES_MB_CD");   //ä���� ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  A.MNG_NUM,  A.REPAY_YRMN,  A.REPAY_AMT,  A.CLAIMPRES_BANK_CD,  A.CLAIMPRES_ACCN_NUM,  A.REMAK,  A.TRNSF_GBN,  A.DEDCTPROC_YN,  A.CLAIMPRES_MB_CD  FROM  FIDU.TMEM_CLAIMDEBTREPAY  A,  FIDU.TMEM_CLAIMDEBT  B  WHERE  A.MB_CD  =  :MB_CD   \n";
        query +=" AND  A.MNG_NUM  =  :MNG_NUM   \n";
        query +=" AND  A.CLAIMPRES_MB_CD  =  :CLAIMPRES_MB_CD   \n";
        query +=" AND  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.MNG_NUM  =  B.MNG_NUM  ORDER  BY  REPAY_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("CLAIMPRES_MB_CD", CLAIMPRES_MB_CD);               //ä���� ȸ�� �ڵ�
        return sobj;
    }
    //##**$$tac07_s01select
    //##**$$tac07_s01delete
    /* * ���α׷��� : tac07_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/24
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLtac07_s01delete(DOBJ dobj)
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
            dobj  = CALLtac07_s01delete_DEL1(Conn, dobj);           //  ä��ä���󼼻���
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
    public DOBJ CTLtac07_s01delete( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac07_s01delete_DEL1(Conn, dobj);           //  ä��ä���󼼻���
        return dobj;
    }
    // ä��ä���󼼻���
    public DOBJ CALLtac07_s01delete_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtac07_s01delete_DEL1(dobj, dvobj);
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
    private SQLObject SQLtac07_s01delete_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TMEM_CLAIMDEBT  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    //##**$$tac07_s01delete
    //##**$$tac07_s01dtdelete
    /* * ���α׷��� : tac07_s01
    * �ۼ��� : �賲��
    * �ۼ��� : 2009/08/25
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLtac07_s01dtdelete(DOBJ dobj)
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
            dobj  = CALLtac07_s01dtdelete_DEL1(Conn, dobj);           //  ä��ä���󼼻���
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
    public DOBJ CTLtac07_s01dtdelete( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac07_s01dtdelete_DEL1(Conn, dobj);           //  ä��ä���󼼻���
        return dobj;
    }
    // ä��ä���󼼻���
    public DOBJ CALLtac07_s01dtdelete_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtac07_s01dtdelete_DEL1(dobj, dvobj);
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
    private SQLObject SQLtac07_s01dtdelete_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TMEM_CLAIMDEBT  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    //##**$$tac07_s01dtdelete
    //##**$$tac07_s01dtselect
    /* * ���α׷��� : tac07_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/26
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLtac07_s01dtselect(DOBJ dobj)
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
            dobj  = CALLtac07_s01dtselect_SEL1(Conn, dobj);           //  ä��ä������ȸ
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
    public DOBJ CTLtac07_s01dtselect( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac07_s01dtselect_SEL1(Conn, dobj);           //  ä��ä������ȸ
        return dobj;
    }
    // ä��ä������ȸ
    // ä��ä������ȸ
    public DOBJ CALLtac07_s01dtselect_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtac07_s01dtselect_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac07_s01dtselect_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  A.MNG_NUM,  A.CLAIMDEBT_GBN,  A.CLAIM_KND,  A.FIXGRD_CTENT,  A.CLAIMPRES_MB_CD,  A.CLAIMPRES_NM,  A.CLAIMPRES_BANK_CD,  A.CLAIMPRES_ACCN_NUM,  A.CLAIM_BST_AMT,  A.CLAIM_PTTNRATE,  A.CLAIM_RFND_AMT,  A.REPAYPROC_START_DAY,  A.REPAYPROC_END_DAY,  A.COMPL_YN,  A.REMAK,  A.CLAIMPRES_MEMO,  A.DEBTPRES_GBN,  A.SUPP_GBN,  A.REPAYPROC_START_YRMN,  A.REPAYPROC_END_YRMN,  A.REPTACCN_MEMO  FROM  FIDU.TMEM_CLAIMDEBT  A  WHERE  A.MB_CD  =  :MB_CD  ORDER  BY  A.MNG_NUM ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    //##**$$tac07_s01dtselect
    //##**$$tac07_s01mbselect
    /* * ���α׷��� : tac07_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/04
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLtac07_s01mbselect(DOBJ dobj)
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
            dobj  = CALLtac07_s01mbselect_SEL1(Conn, dobj);           //  ä��ä������ȸ
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
    public DOBJ CTLtac07_s01mbselect( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac07_s01mbselect_SEL1(Conn, dobj);           //  ä��ä������ȸ
        return dobj;
    }
    // ä��ä������ȸ
    // ä��ä������ȸ
    public DOBJ CALLtac07_s01mbselect_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtac07_s01mbselect_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac07_s01mbselect_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  A.HANMB_NM,  A.SEX_GBN,  A.INS_NUM,  A.PHON_NUM,  FIDU.GET_MB_START_DAY(A.MB_CD,'E')  AS  ENTRN_DAY,  A.NATY_CD,  A.MB_GBN1,  A.MB_GBN2,  A.OFFCL_GBN,  B.POST_NUM,  TRIM(B.ADDR||'  '||B.ADDR_DETED)  AS  JUSO  FROM  FIDU.TMEM_MB  A,  FIDU.TMEM_ADBK  B  WHERE  A.MB_CD  =  B.MB_CD(+)   \n";
        query +=" AND  '2'  =  B.ADDR_GBN(+)   \n";
        query +=" AND  A.MB_CD  =  :MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    //##**$$tac07_s01mbselect
    //##**$$tmem_claimdebtmemo_save
    /* * ���α׷��� : tac07_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/17
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLtmem_claimdebtmemo_save(DOBJ dobj)
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
            dobj  = CALLtmem_claimdebtmemo_save_MIUD3(Conn, dobj);           //  ä��ä��ȸ���޸�
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
    public DOBJ CTLtmem_claimdebtmemo_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtmem_claimdebtmemo_save_MIUD3(Conn, dobj);           //  ä��ä��ȸ���޸�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ä��ä��ȸ���޸�
    public DOBJ CALLtmem_claimdebtmemo_save_MIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtmem_claimdebtmemo_save_SEL9(Conn, dobj);           //  SEL
                dobj  = CALLtmem_claimdebtmemo_save_INS2(Conn, dobj);           //  ä��ä��ȸ���޸� ���
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtmem_claimdebtmemo_save_UPD7(Conn, dobj);           //  ä��ä��ȸ���޸� ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtmem_claimdebtmemo_save_DEL1(Conn, dobj);           //  ä��ä��ȸ���޸� ����
            }
        }
        return dobj;
    }
    // ä��ä��ȸ���޸� ����
    public DOBJ CALLtmem_claimdebtmemo_save_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtmem_claimdebtmemo_save_DEL1(dobj, dvobj);
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
    private SQLObject SQLtmem_claimdebtmemo_save_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MEMO_GBN = dvobj.getRecord().get("MEMO_GBN");   //�޸� ����
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TMEM_MBMEMO  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM  \n";
        query +=" and MEMO_GBN=:MEMO_GBN";
        sobj.setSql(query);
        sobj.setString("MEMO_GBN", MEMO_GBN);               //�޸� ����
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    // ä��ä��ȸ���޸� ����
    public DOBJ CALLtmem_claimdebtmemo_save_UPD7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLtmem_claimdebtmemo_save_UPD7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtmem_claimdebtmemo_save_UPD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MEMO_CTENT = dvobj.getRecord().get("MEMO_CTENT");   //�޸� ����
        String   MEMO_GBN = dvobj.getRecord().get("MEMO_GBN");   //�޸� ����
        String   MEMO_DAY = dvobj.getRecord().get("MEMO_DAY");   //�޸� ����
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TMEM_MBMEMO  \n";
        query +=" set MEMO_DAY=:MEMO_DAY , MEMO_GBN=:MEMO_GBN , MEMO_CTENT=:MEMO_CTENT  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM";
        sobj.setSql(query);
        sobj.setString("MEMO_CTENT", MEMO_CTENT);               //�޸� ����
        sobj.setString("MEMO_GBN", MEMO_GBN);               //�޸� ����
        sobj.setString("MEMO_DAY", MEMO_DAY);               //�޸� ����
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    // SEL
    public DOBJ CALLtmem_claimdebtmemo_save_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtmem_claimdebtmemo_save_SEL9(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtmem_claimdebtmemo_save_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(MNG_NUM),0)+1  MNG_NUM  FROM  FIDU.TMEM_MBMEMO  WHERE  MB_CD  =  :MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    // ä��ä��ȸ���޸� ���
    public DOBJ CALLtmem_claimdebtmemo_save_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtmem_claimdebtmemo_save_INS2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtmem_claimdebtmemo_save_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //����� ID
        String INS_DATE ="";        //��� �Ͻ�
        String   MEMO_CTENT = dvobj.getRecord().get("MEMO_CTENT");   //�޸� ����
        String   MEMO_GBN = dvobj.getRecord().get("MEMO_GBN");   //�޸� ����
        String   MEMO_DAY = dvobj.getRecord().get("MEMO_DAY");   //�޸� ����
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        double   MNG_NUM = dobj.getRetObject("SEL9").getRecord().getDouble("MNG_NUM");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TMEM_MBMEMO (MB_CD, MNG_NUM, MEMO_DAY, MEMO_GBN, MEMO_CTENT)  \n";
        query +=" values(:MB_CD , :MNG_NUM , :MEMO_DAY , :MEMO_GBN , :MEMO_CTENT )";
        sobj.setSql(query);
        sobj.setString("MEMO_CTENT", MEMO_CTENT);               //�޸� ����
        sobj.setString("MEMO_GBN", MEMO_GBN);               //�޸� ����
        sobj.setString("MEMO_DAY", MEMO_DAY);               //�޸� ����
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        return sobj;
    }
    //##**$$tmem_claimdebtmemo_save
    //##**$$end
}
