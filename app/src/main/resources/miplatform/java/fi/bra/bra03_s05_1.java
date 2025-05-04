
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s05_1
{
    public bra03_s05_1()
    {
    }
    //##**$$auto_apptn_result_bak
    /* * ���α׷��� : bra03_s05
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/30
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLauto_apptn_result_bak(DOBJ dobj)
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
            dobj  = CALLauto_apptn_result_bak_SEL1(Conn, dobj);           //  ��ü����Ʈ
            dobj  = CALLauto_apptn_result_bak_SEL2(Conn, dobj);           //  ��������Ʈ
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
    public DOBJ CTLauto_apptn_result_bak( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_apptn_result_bak_SEL1(Conn, dobj);           //  ��ü����Ʈ
        dobj  = CALLauto_apptn_result_bak_SEL2(Conn, dobj);           //  ��������Ʈ
        return dobj;
    }
    // ��ü����Ʈ
    public DOBJ CALLauto_apptn_result_bak_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_apptn_result_bak_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_result_bak_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD||XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XB.MONPRNCFEE  ,  XA.MNGEMSTR_NM  ,  XA.MNGEMSTR_RESINUM  ,  XA.UPSO_PHON  ,  XA.UPSO_ADDR1||'  '||XA.UPSO_ADDR2  ADDR  ,  XE.BANK_NM||XE.SHOP_NM  BANK_NM  ,  XC.PROC_DAY  ,  XC.GBN  ,  XC.SEQ_NUM  ,  XC.APPTN_DAY  ,  XC.APPTN_GBN  ,  XC.APPTN_RSLT  ,  XC.CHGBFR_PAYPRES_NUM  ,  XC.CHGATR_PAYPRES_NUM  ,  XC.PAY_REQDAY  ,  XC.PAY_KND  ,  XC.PAY_BANK_CD  ,  XC.PAY_ACCN_NUM  ,  XC.RESINUM  ,  XC.PAYER_PHONNUM  ,  XC.RECPT_BANK_CD  ,  XC.BEGNG_RELSDAY  ,  XC.PAYPRES_NM  ,  XC.ERR_GBN  ,  XC.RECPTNUM  ,  XC.RECPT_GBN_CD  FROM  GIBU.TBRA_UPSO  XA,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  GIBU.TBRA_UPSO_AUTORSLT  XC  ,  INSA.TCPM_DEPT  XD  ,  ACCT.TCAC_BANK  XE  WHERE  XC.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  XA.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XD.GIBU(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XE.BANK_CD(+)  =  SUBSTR(XC.PAY_BANK_CD  ,1,3)  ORDER  BY  XC.SEQ_NUM ";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        return sobj;
    }
    // ��������Ʈ
    public DOBJ CALLauto_apptn_result_bak_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_apptn_result_bak_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_result_bak_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD||XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XB.MONPRNCFEE  ,  XA.MNGEMSTR_NM  ,  XA.MNGEMSTR_RESINUM  ,  XA.UPSO_PHON  ,  XA.UPSO_ADDR1||'  '||XA.UPSO_ADDR2  ADDR  ,  XE.BANK_NM||'  '||XE.SHOP_NM  BANK_NM  ,  XC.PROC_DAY  ,  XC.GBN  ,  XC.SEQ_NUM  ,  XC.APPTN_DAY  ,  XC.APPTN_GBN  ,  XC.APPTN_RSLT  ,  XF.CODE_NM  ,  XC.CHGBFR_PAYPRES_NUM  ,  XC.CHGATR_PAYPRES_NUM  ,  XC.PAY_REQDAY  ,  XC.PAY_KND  ,  XC.PAY_BANK_CD  ,  XC.PAY_ACCN_NUM  ,  XC.RESINUM  ,  XC.PAYER_PHONNUM  ,  XC.RECPT_BANK_CD  ,  XC.BEGNG_RELSDAY  ,  XC.PAYPRES_NM  ,  XC.ERR_GBN  ,  XC.RECPTNUM  ,  XC.RECPT_GBN_CD  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  GIBU.TBRA_UPSO_AUTORSLT  XC  ,  INSA.TCPM_DEPT  XD  ,  ACCT.TCAC_BANK_7  XE  ,  FIDU.TENV_CODE  XF  WHERE  XC.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  XA.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XE.BANK_CD(+)  =  XC.PAY_BANK_CD   \n";
        query +=" AND  XF.HIGH_CD  =  '00324'   \n";
        query +=" AND  XF.CODE_CD  =  XC.ERR_GBN   \n";
        query +=" AND  XC.APPTN_RSLT  !='00'  ORDER  BY  XC.SEQ_NUM ";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        return sobj;
    }
    //##**$$auto_apptn_result_bak
    //##**$$auto_err_handling_bak
    /* * ���α׷��� : bra03_s05
    * �ۼ��� : ������
    * �ۼ��� : 2009/10/13
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLauto_err_handling_bak(DOBJ dobj)
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
            dobj  = CALLauto_err_handling_bak_MPD3(Conn, dobj);           //  �Ǻ�ó��
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
    public DOBJ CTLauto_err_handling_bak( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_err_handling_bak_MPD3(Conn, dobj);           //  �Ǻ�ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �Ǻ�ó��
    public DOBJ CALLauto_err_handling_bak_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MPD3");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("CHK_YN").equals("1"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_err_handling_bak_SEL7(Conn, dobj);           //  �ߺ��˻�
                if( dobj.getRetObject("SEL7").getRecord().getDouble("CNT") == 0)
                {
                    dobj  = CALLauto_err_handling_bak_INS6(Conn, dobj);           //  �ڵ���ü��û�������
                    if( dobj.getRetObject("R").getRecord().get("GUBUN").equals("U"))
                    {
                        dobj  = CALLauto_err_handling_bak_UPD10(Conn, dobj);           //  ������������
                    }
                }
                else
                {
                    dobj.setRtncode(9);
                    if(dobj.getRtncode() == 9)
                    {
                        Conn.rollback();
                        return dobj;
                    }
                }
            }
        }
        return dobj;
    }
    // �ߺ��˻�
    // �������� �ߺ���� �ȵǰ�
    public DOBJ CALLauto_err_handling_bak_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_err_handling_bak_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_err_handling_bak_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BANK_CD = dobj.getRetObject("R").getRecord().get("PAY_BANK_CD");   //���� �ڵ�
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("PROC_DAY");   //��û ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   AUTO_ACCNNUM = dobj.getRetObject("R").getRecord().get("PAY_ACCN_NUM");   //��� ���¹�ȣ
        String   RESINUM = dobj.getRetObject("R").getRecord().get("RESINUM");   //�ֹι�ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  BANK_CD  =  :BANK_CD   \n";
        query +=" AND  AUTO_ACCNNUM  =  :AUTO_ACCNNUM   \n";
        query +=" AND  APPTN_DAY  =  :APPTN_DAY   \n";
        query +=" AND  RESINUM  =  :RESINUM   \n";
        query +=" AND  TERM_YN  =  'N'   \n";
        query +=" AND  PROC_GBN  =  'N' ";
        sobj.setSql(query);
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //��� ���¹�ȣ
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        return sobj;
    }
    // �ڵ���ü��û�������
    // �ڵ���ü��û UPLOAD �� ������ ���Ҹ� �������� ����Ѵ�. �̶� ó�������� �������� �Ѵ�.(PROC_GBN=="N")
    public DOBJ CALLauto_err_handling_bak_INS6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLauto_err_handling_bak_INS6(dobj, dvobj);
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
    private SQLObject SQLauto_err_handling_bak_INS6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int  AUTO_NUM = 0;        //�Ϸ� ��ȣ
        String INS_DATE ="";        //��� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   AUTO_NUM_1 = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //�Ϸ� ��ȣ
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //�ֹι�ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //������ ��
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("PROC_DAY");   //��û ����
        String   AUTO_ACCNNUM = dobj.getRetObject("R").getRecord().get("PAY_ACCN_NUM");   //��� ���¹�ȣ
        String   BANK_CD = dobj.getRetObject("R").getRecord().get("PAY_BANK_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   PROC_GBN ="N";   //ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO (INS_DATE, INSPRES_ID, BANK_CD, PAYPRES_NM, APPTN_DAY, AUTO_ACCNNUM, UPSO_CD, RESINUM, AUTO_NUM, PROC_GBN, REMAK)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BANK_CD , :PAYPRES_NM , :APPTN_DAY , :AUTO_ACCNNUM , :UPSO_CD , :RESINUM , (SELECT NVL(MAX(AUTO_NUM), 0) + 1 AUTO_NUM FROM GIBU.TBRA_UPSO_AUTO WHERE UPSO_CD = :AUTO_NUM_1), :PROC_GBN , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("AUTO_NUM_1", AUTO_NUM_1);               //�Ϸ� ��ȣ
        sobj.setString("RESINUM", RESINUM);               //�ֹι�ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //������ ��
        sobj.setString("APPTN_DAY", APPTN_DAY);               //��û ����
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //��� ���¹�ȣ
        sobj.setString("BANK_CD", BANK_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PROC_GBN", PROC_GBN);               //ó�� ����
        return sobj;
    }
    // ������������
    // �濵���� �ֹι�ȣ ����
    public DOBJ CALLauto_err_handling_bak_UPD10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_err_handling_bak_UPD10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD10") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_err_handling_bak_UPD10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   MNGEMSTR_RESINUM = dvobj.getRecord().get("RESINUM");   //�濵�� �ֹι�ȣ
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MNGEMSTR_RESINUM=:MNGEMSTR_RESINUM , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("MNGEMSTR_RESINUM", MNGEMSTR_RESINUM);               //�濵�� �ֹι�ȣ
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    //##**$$auto_err_handling_bak
    //##**$$auto_apptn_result
    /*
    */
    public DOBJ CTLauto_apptn_result(DOBJ dobj)
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
            dobj  = CALLauto_apptn_result_SEL1(Conn, dobj);           //  ��ü����Ʈ
            dobj  = CALLauto_apptn_result_SEL2(Conn, dobj);           //  ��������Ʈ
            dobj  = CALLauto_apptn_result_SEL4(Conn, dobj);           //  82�������
            dobj  = CALLauto_apptn_result_XIUD5(Conn, dobj);           //  ��û���� ������¥ ���
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
    public DOBJ CTLauto_apptn_result( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_apptn_result_SEL1(Conn, dobj);           //  ��ü����Ʈ
        dobj  = CALLauto_apptn_result_SEL2(Conn, dobj);           //  ��������Ʈ
        dobj  = CALLauto_apptn_result_SEL4(Conn, dobj);           //  82�������
        dobj  = CALLauto_apptn_result_XIUD5(Conn, dobj);           //  ��û���� ������¥ ���
        return dobj;
    }
    // ��ü����Ʈ
    public DOBJ CALLauto_apptn_result_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_apptn_result_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_result_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD||XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XB.MONPRNCFEE  ,  XA.MNGEMSTR_NM  ,  DECODE(LENGTH(XA.MNGEMSTR_RESINUM),13,  SUBSTR(XA.MNGEMSTR_RESINUM,0,6)||'0000000',  XA.MNGEMSTR_RESINUM)  MNGEMSTR_RESINUM  ,  XA.UPSO_PHON  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  XE.BANK_NM||XE.SHOP_NM  BANK_NM  ,  XC.PROC_DAY  ,  XC.GBN  ,  XC.SEQ_NUM  ,  XC.APPTN_DAY  ,  XC.APPTN_GBN  ,  XC.APPTN_RSLT  ,  XC.CHGBFR_PAYPRES_NUM  ,  XC.CHGATR_PAYPRES_NUM  ,  XC.PAY_REQDAY  ,  XC.PAY_KND  ,  XC.PAY_BANK_CD  ,  XC.PAY_ACCN_NUM  ,  DECODE(LENGTH(XC.RESINUM),13,  SUBSTR(XC.RESINUM,0,6)||'0000000',  XC.RESINUM)  RESINUM  ,  XC.PAYER_PHONNUM  ,  XC.RECPT_BANK_CD  ,  XC.BEGNG_RELSDAY  ,  XC.PAYPRES_NM  ,  XC.ERR_GBN  ,  XC.RECPTNUM  ,  XC.RECPT_GBN_CD  FROM  GIBU.TBRA_UPSO  XA,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  GIBU.TBRA_UPSO_AUTORSLT  XC  ,  INSA.TCPM_DEPT  XD  ,  ACCT.TCAC_BANK  XE  WHERE  XC.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  XC.GBN  =  '62'   \n";
        query +=" AND  XA.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XD.GIBU(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XE.BANK_CD(+)  =  SUBSTR(XC.PAY_BANK_CD  ,1,3)   \n";
        query +=" AND  XC.SEQ_NUM  LIKE  '0%'  ORDER  BY  XC.SEQ_NUM ";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        return sobj;
    }
    // ��������Ʈ
    public DOBJ CALLauto_apptn_result_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_apptn_result_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_result_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD||XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XB.MONPRNCFEE  ,  XA.MNGEMSTR_NM  ,  DECODE(LENGTH(XA.MNGEMSTR_RESINUM),13,  SUBSTR(XA.MNGEMSTR_RESINUM,0,6)||'0000000',  XA.MNGEMSTR_RESINUM)  MNGEMSTR_RESINUM  ,  XA.UPSO_PHON  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  XE.BANK_NM||'  '||XE.SHOP_NM  BANK_NM  ,  XC.PROC_DAY  ,  XC.GBN  ,  XC.SEQ_NUM  ,  XC.APPTN_DAY  ,  XC.APPTN_GBN  ,  XC.APPTN_RSLT  ,  XF.CODE_NM  ,  XC.CHGBFR_PAYPRES_NUM  ,  XC.CHGATR_PAYPRES_NUM  ,  XC.PAY_REQDAY  ,  XC.PAY_KND  ,  XC.PAY_BANK_CD  ,  XC.PAY_ACCN_NUM  ,  DECODE(LENGTH(XC.RESINUM),13,  SUBSTR(XC.RESINUM,0,6)||'0000000',  XC.RESINUM)  RESINUM  ,  XC.PAYER_PHONNUM  ,  XC.RECPT_BANK_CD  ,  XC.BEGNG_RELSDAY  ,  XC.PAYPRES_NM  ,  XC.ERR_GBN  ,  XC.RECPTNUM  ,  XC.RECPT_GBN_CD  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  GIBU.TBRA_UPSO_AUTORSLT  XC  ,  INSA.TCPM_DEPT  XD  ,  ACCT.TCAC_BANK_7  XE  ,  FIDU.TENV_CODE  XF  WHERE  XC.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  XC.GBN  =  '62'   \n";
        query +=" AND  XA.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XE.BANK_CD(+)  =  XC.PAY_BANK_CD   \n";
        query +=" AND  XF.HIGH_CD  =  '00324'   \n";
        query +=" AND  XF.CODE_CD  =  XC.ERR_GBN   \n";
        query +=" AND  XC.APPTN_RSLT  !='00'   \n";
        query +=" AND  XC.SEQ_NUM  LIKE  '0%'  ORDER  BY  XC.SEQ_NUM ";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        return sobj;
    }
    // 82�������
    public DOBJ CALLauto_apptn_result_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLauto_apptn_result_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_result_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  *  FROM   \n";
        query +=" (SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD||XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XB.MONPRNCFEE  ,  XA.MNGEMSTR_NM  ,  DECODE(LENGTH(XA.MNGEMSTR_RESINUM),13,  SUBSTR(XA.MNGEMSTR_RESINUM,0,6)||'0000000',  XA.MNGEMSTR_RESINUM)  MNGEMSTR_RESINUM  ,  XA.UPSO_PHON  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  XE.BANK_NM||XE.SHOP_NM  BANK_NM  ,  XC.PROC_DAY  ,  XC.GBN  ,  XC.SEQ_NUM  ,  XC.APPTN_DAY  ,  XC.APPTN_GBN  ,  XC.APPTN_RSLT  ,  XC.CHGBFR_PAYPRES_NUM  ,  XC.CHGATR_PAYPRES_NUM  ,  XC.PAY_REQDAY  ,  XC.PAY_KND  ,  XC.PAY_BANK_CD  ,  XC.PAY_ACCN_NUM  ,  DECODE(LENGTH(XC.RESINUM),13,  SUBSTR(XC.RESINUM,0,6)||'0000000',  XC.RESINUM)  RESINUM  ,  XC.PAYER_PHONNUM  ,  XC.RECPT_BANK_CD  ,  XC.BEGNG_RELSDAY  ,  TRIM(SUBSTRB(XC.PAYPRES_NM,  0,  15))  AS  PAYPRES_NM  ,  XC.ERR_GBN  ,  XC.RECPTNUM  ,  XC.RECPT_GBN_CD  FROM  GIBU.TBRA_UPSO  XA,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  GIBU.TBRA_UPSO_AUTORSLT  XC  ,  INSA.TCPM_DEPT  XD  ,  ACCT.TCAC_BANK  XE  WHERE  XC.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  XC.GBN  =  '62'   \n";
        query +=" AND  XA.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XD.GIBU(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XE.BANK_CD(+)  =  SUBSTR(XC.PAY_BANK_CD  ,1,3)   \n";
        query +=" AND  XC.RECPT_GBN_CD  IN  ('4',  '6',  '7')   \n";
        query +=" AND  XC.SEQ_NUM  LIKE  '0%'  UNION  ALL   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD||XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XB.MONPRNCFEE  ,  XA.MNGEMSTR_NM  ,  DECODE(LENGTH(XA.MNGEMSTR_RESINUM),13,  SUBSTR(XA.MNGEMSTR_RESINUM,0,6)||'0000000',  XA.MNGEMSTR_RESINUM)  MNGEMSTR_RESINUM  ,  XA.UPSO_PHON  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  XE.BANK_NM||XE.SHOP_NM  BANK_NM  ,  :PROC_DAY  AS  PROC_DAY  ,  '62'  GBN  ,  ''  AS  SEQ_NUM  ,  XC.APPTN_DAY  ,  XC.APPLICATION_GBN  AS  APPTN_GBN  ,  '00'  APPTN_RSLT  ,  ''  AS  CHGBFR_PAYPRES_NUM  ,   \n";
        query +=" (SELECT  CLIENT_NUM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  XA.UPSO_CD  )  CHGATR_PAYPRES_NUM  ,  '00'  AS  PAY_REQDAY  ,  '00'  AS  PAY_KND  ,  XC.BANK_CD  ||  '0000'  AS  PAY_BANK_CD  ,  XC.AUTO_ACCNNUM  AS  PAY_ACCN_NUM  ,  DECODE(LENGTH(XC.RESINUM),13,  SUBSTR(XC.RESINUM,0,6)||'0000000',  XC.RESINUM)  RESINUM  ,  XC.PHON_NUM  AS  PAYER_PHONNUM  ,  XC.BANK_CD  ||  '0000'  AS  RECPT_BANK_CD  ,  :PROC_DAY  AS  BEGNG_RELSDAY  ,  XC.PAYPRES_NM  ,  '00'  AS  ERR_GBN  ,  '000000000'  AS  RECPTNUM  ,  XC.RECEPTION_GBN  FROM  GIBU.TBRA_UPSO  XA,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  GIBU.TBRA_UPSO_AUTO_APPLICATION  XC  ,  INSA.TCPM_DEPT  XD  ,  ACCT.TCAC_BANK  XE  WHERE  TO_CHAR(XC.INS_DATE,  'YYYYMMDD')  <=  :PROC_DAY   \n";
        query +=" AND  XC.CONFIRM_ID  IS  NOT  NULL   \n";
        query +=" AND  (XC.PROC_DAY  IS  NULL   \n";
        query +=" OR  XC.PROC_DAY  =  :PROC_DAY)   \n";
        query +=" AND  XA.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XD.GIBU(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XE.BANK_CD(+)  =  SUBSTR(XC.BANK_CD  ,1,3)   \n";
        query +=" AND  XC.RECEPTION_GBN  =  '5')  ORDER  BY  SEQ_NUM  ASC,  APPTN_GBN  DESC ";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        return sobj;
    }
    // ��û���� ������¥ ���
    public DOBJ CALLauto_apptn_result_XIUD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD5");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_result_XIUD5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_result_XIUD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" SET PROC_DAY = :PROC_DAY  \n";
        query +=" WHERE TO_CHAR(INS_DATE, 'YYYYMMDD') <= :PROC_DAY  \n";
        query +=" AND CONFIRM_ID IS NOT NULL  \n";
        query +=" AND RECEPTION_GBN = '5'  \n";
        query +=" AND PROC_DAY IS NULL";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //ó�� ����
        return sobj;
    }
    //##**$$auto_apptn_result
    //##**$$end
}
