
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_r07
{
    public bra04_r07()
    {
    }
    //##**$$sel_nonpy_history
    /*
    */
    public DOBJ CTLsel_nonpy_history(DOBJ dobj)
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
            dobj  = CALLsel_nonpy_history_SEL1(Conn, dobj);           //  �������Ȳ���� ��ȸ
            dobj  = CALLsel_nonpy_history_SEL2(Conn, dobj);           //  ����ڹ�¡����
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
    public DOBJ CTLsel_nonpy_history( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_nonpy_history_SEL1(Conn, dobj);           //  �������Ȳ���� ��ȸ
        dobj  = CALLsel_nonpy_history_SEL2(Conn, dobj);           //  ����ڹ�¡����
        return dobj;
    }
    // �������Ȳ���� ��ȸ
    public DOBJ CALLsel_nonpy_history_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_nonpy_history_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_nonpy_history_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_ZIP = dobj.getRetObject("S").getRecord().getDouble("START_ZIP");   //���� �����ȣ
        double   START_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("START_CNT_MON");   //���� ����
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //����
        double   END_ZIP = dobj.getRetObject("S").getRecord().getDouble("END_ZIP");   //���� �����ȣ
        double   END_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("END_CNT_MON");   //���� ����
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  PRCON_YRMN  ,  GBN  ,  DEMD_MMCNT  ,  UPSO_CD  ,  UPSO_NM  ,  CHK_RESINUM  ,  MNGEMSTR_NM  ,  GRADNM  ,  BOND  ,  MONPRNCFEE  ,  MONPRNCFEE2  ,  UPSO_ADDR  ,  UPSO_PHON  ,  MNGEMSTR_HPNUM  ,  TOT_USE_AMT  ,  TOT_ADDT_AMT  ,  TOT_AMT  ,  REMAK  ,  UPSO_ZIP  ,  SIGUGUN  ,  START_YRMN  ,  END_YRMN  ,  BRAN_PHON_NUM  ,  AUTO_YN  ,  COL_CHECK  ,  DONG  ,  CLIENT_NUM  ,  BRAN_CD  ,  STAFF_CD  ,  STAFF_NM  ,  VISIT_YN5  ,  VISIT_YN6  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  0)  AS  ACCOUNT_NM  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  2)  AS  BANK_NM1  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  3)  AS  ACCN_NUM1  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  5)  AS  BANK_NM2  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  6)  AS  ACCN_NUM2  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  8)  AS  BANK_NM3  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  9)  AS  ACCN_NUM3  ,  STOMU_YN  ,  SMS_CNT  ,  SMS_TOTCNT  ,  VISIT_TOTCNT  ,  CALL_TOTCNT  FROM  (   \n";
        query +=" SELECT  PRCON_YRMN  ,  GBN  ,  DEMD_MMCNT  ,  UPSO_CD  ,  UPSO_NM  ,  CHK_RESINUM  ,  MNGEMSTR_NM  ,  GRADNM  ,  BOND  ,  MONPRNCFEE  ,  MONPRNCFEE2  ,  UPSO_ADDR  ,  UPSO_PHON  ,  MNGEMSTR_HPNUM  ,  TOT_USE_AMT  ,  TOT_ADDT_AMT  ,  TOT_AMT  ,  REMAK  ,  UPSO_ZIP  ,  SIGUGUN  ,  START_YRMN  ,  END_YRMN  ,  BRAN_PHON_NUM  ,  AUTO_YN  ,  COL_CHECK  ,  DONG  ,  CLIENT_NUM  ,  BRAN_CD  ,  STAFF_CD  ,  STAFF_NM  ,  VISIT_YN5  ,  VISIT_YN6  ,  STOMU_YN  ,  SMS_CNT  ,  SMS_TOTCNT  ,  VISIT_TOTCNT  ,  CALL_TOTCNT  ,  GIBU.FT_GET_VIRTUAL_ACCOUNT(UPSO_CD)  AS  BANK_RTN  FROM  GIBU.TBRA_NONPY_PRCON  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  GBN  =  DECODE(:GBN,  '3',  GBN,  :GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_ZIP  BETWEEN  NVL(:START_ZIP,  UPSO_ZIP)   \n";
        query +=" AND  NVL(:END_ZIP,  UPSO_ZIP)   \n";
        query +=" AND  DEMD_MMCNT  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON   \n";
        query +=" AND  (:BSTYP_CD  IS  NULL   \n";
        query +=" OR  INSTR(:BSTYP_CD,  BSTYP_CD)  >  0)  ) ";
        sobj.setSql(query);
        sobj.setDouble("START_ZIP", START_ZIP);               //���� �����ȣ
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //���� ����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("GBN", GBN);               //����
        sobj.setDouble("END_ZIP", END_ZIP);               //���� �����ȣ
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //���� ����
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ����ڹ�¡����
    public DOBJ CALLsel_nonpy_history_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_nonpy_history_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_nonpy_history_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  FIDU.GET_GIBU_NM(BRAN_CD)  AS  BRAN_NM  ,  FIDU.GET_STAFF_NM(STAFF_CD)  AS  STAFF_NM  ,  CNT_UPSO  ,  CNT_3MONTH  ,  TRUNC(CNT_3MONTH  /  DECODE(CNT_UPSO,  0,  1,  CNT_UPSO)  *  100,  1)  AS  RATE_3MONTH  ,  CNT_6MONTH  ,  CNT_6MONTH_NT  ,  TRUNC(CNT_6MONTH_NT  /  DECODE(CNT_UPSO,  0,  1,  CNT_UPSO)  *  100,  1)  AS  RATE_6MONTH  ,  CNT_ACCU  ,  TRUNC(CNT_ACCU  /  DECODE(CNT_8MONTH,  0,  1,  CNT_8MONTH)  *  100,  1)  AS  RATE_ACCU  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  STAFF_CD  ,  COUNT(1)  AS  CNT_UPSO  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  3  THEN  1  ELSE  0  END))  AS  CNT_3MONTH  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  6  THEN  1  ELSE  0  END))  AS  CNT_6MONTH  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  6   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(ACCU_DAY)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL)  <  1  THEN  1  ELSE  0  END))  AS  CNT_6MONTH_NT  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  8  THEN  1  ELSE  0  END))  AS  CNT_8MONTH  ,  SUM((CASE  WHEN   \n";
        query +=" (SELECT  COUNT(ACCU_DAY)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL)  =  1  THEN  1  ELSE  0  END))  AS  CNT_ACCU  FROM  GIBU.TBRA_NONPY_PRCON  A  WHERE  PRCON_YRMN  =  :PRCON_YRMN  GROUP  BY  BRAN_CD,  STAFF_CD  ORDER  BY  BRAN_CD,  STAFF_CD  ) ";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    //##**$$sel_nonpy_history
    //##**$$nonpy_select
    /* 2018.01.29 �̴ټ� ����
    2017.12.14�� �׷����� �����ϸ鼭 ����ذ� ������ �ٲ�.
    ���� ������ JUDG_CD�� �ɷ����� �κо��� COMPN_DAY�� ����� ���� ǥ��
    */
    public DOBJ CTLnonpy_select(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("UPSO_TYPE").equals("01"))
            {
                dobj  = CALLnonpy_select_SEL1(Conn, dobj);           //  ��¡�� ������ȸ
                dobj  = CALLnonpy_select_MRG7( dobj);        //  ��� ��ġ��
            }
            else if( dobj.getRetObject("S").getRecord().get("UPSO_TYPE").equals("02"))
            {
                dobj  = CALLnonpy_select_SEL5(Conn, dobj);           //  ��¡�� ������ȸ
                dobj  = CALLnonpy_select_MRG7( dobj);        //  ��� ��ġ��
            }
            else
            {
                dobj  = CALLnonpy_select_SEL6(Conn, dobj);           //  ��¡�� ������ȸ
                dobj  = CALLnonpy_select_MRG7( dobj);        //  ��� ��ġ��
            }
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
    public DOBJ CTLnonpy_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("UPSO_TYPE").equals("01"))
        {
            dobj  = CALLnonpy_select_SEL1(Conn, dobj);           //  ��¡�� ������ȸ
            dobj  = CALLnonpy_select_MRG7( dobj);        //  ��� ��ġ��
        }
        else if( dobj.getRetObject("S").getRecord().get("UPSO_TYPE").equals("02"))
        {
            dobj  = CALLnonpy_select_SEL5(Conn, dobj);           //  ��¡�� ������ȸ
            dobj  = CALLnonpy_select_MRG7( dobj);        //  ��� ��ġ��
        }
        else
        {
            dobj  = CALLnonpy_select_SEL6(Conn, dobj);           //  ��¡�� ������ȸ
            dobj  = CALLnonpy_select_MRG7( dobj);        //  ��� ��ġ��
        }
        return dobj;
    }
    // ��¡�� ������ȸ
    // �������� ������ ��¡�� ������ ��ȸ�Ѵ�
    public DOBJ CALLnonpy_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLnonpy_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnonpy_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_ZIP = dvobj.getRecord().getDouble("START_ZIP");   //���� �����ȣ
        double   START_CNT_MON = dvobj.getRecord().getDouble("START_CNT_MON");   //���� ����
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        double   END_ZIP = dvobj.getRecord().getDouble("END_ZIP");   //���� �����ȣ
        double   END_CNT_MON = dvobj.getRecord().getDouble("END_CNT_MON");   //���� ����
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //û�� ���
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  optimizer_features_enable('11.1.0.6')  */  XA.DEMD_MMCNT  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  DECODE(XB.MNGEMSTR_RESINUM  ,  NULL,  '',  'OK')  CHK_RESINUM  ,  XB.MNGEMSTR_NM  ,  DECODE(TRIM(XA.BSTYP_CD),  'o',  GIBU.FT_GET_NOREBANG_GRAD(XB.UPSO_CD),  XC.GRADNM)  GRADNM  ,  DECODE  ((SELECT  NVL(COUNT(UPSO_CD),  0)  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL),  0,  '',  'ä��')  BOND  ,  XA.MONPRNCFEE  ,  XA.MONPRNCFEE2  ,  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO  UPSO_ADDR  ,  XB.UPSO_PHON  ,  XB.MNGEMSTR_HPNUM  ,  XA.TOT_USE_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_USE_AMT  +  XA.TOT_ADDT_AMT  AS  TOT_AMT  ,  DECODE(XB.BEFORE_UPSO_CD,  NULL,  '',  '��'  ||  TO_CHAR(XB.INS_DATE,  'YYYYMMDD'))  ||  '  '  ||  NVL(XD.GOSO_YN,  '')  REMAK  ,  XB.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,   \n";
        query +=" (SELECT  ATTE||'  '||CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =XB.UPSO_BD_MNG_NUM)  AS  SIGUGUN  ,  SUBSTR(XA.START_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.START_YRMN,  5,  2)  START_YRMN  ,  SUBSTR(XA.END_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.END_YRMN,  5,  2)  END_YRMN  ,   \n";
        query +=" (SELECT  IPPBX_INPHONE_NUM  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  XB.STAFF_CD)  AS  BRAN_PHON_NUM  ,  DECODE((SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  TERM_YN  =  'N'),0,'','OK')  AUTO_YN  ,  '0'  AS  COL_CHECK  ,   \n";
        query +=" (SELECT  DECODE(COURT_NM,  NULL,  TOWNTWSHP,  COURT_NM)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XB.UPSO_BD_MNG_NUM  )  AS  DONG  ,  XB.CLIENT_NUM  ,  XB.BRAN_CD  ,  XB.STAFF_CD  ,  FIDU.GET_STAFF_NM(XB.STAFF_CD)  AS  STAFF_NM  ,  (CASE  WHEN  XA.DEMD_MMCNT  >=  6  THEN  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  JOB_GBN  IN  ('V',  'T')   \n";
        query +=" AND  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  XA.START_YRMN  ||  '01'   \n";
        query +=" AND  TO_CHAR(ADD_MONTHS(TO_DATE(XA.START_YRMN,  'YYYYMM'),  4),  'YYYYMM')  ||  '31')  <  1  THEN  'N'  ELSE  'Y'  END)  ELSE  ''  END)  AS  VISIT_YN5  ,  (CASE  WHEN  XA.DEMD_MMCNT  >=  6  THEN  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  JOB_GBN  IN  ('V',  'T')   \n";
        query +=" AND  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  TO_CHAR(ADD_MONTHS(TO_DATE(XA.START_YRMN,  'YYYYMM'),  5),  'YYYYMM')  ||  '01'   \n";
        query +=" AND  TO_CHAR(SYSDATE,  'YYYYMMDD'))  <  1  THEN  'N'  ELSE  'Y'  END)  ELSE  ''  END)  AS  VISIT_YN6  ,  ACCOUNT_NM  ,  BANK_NM1  ,  ACCN_NUM1  ,  BANK_NM2  ,  ACCN_NUM2  ,  BANK_NM3  ,  ACCN_NUM3  ,  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_STOMU_CONTRINFO  WHERE  UPSO_CD  =  XB.UPSO_CD)  >  0  THEN  'Y'  ELSE  'N'  END)  AS  STOMU_YN  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  KAKAO.MZSENDLOG  WHERE  USER_ID  =  XA.UPSO_CD   \n";
        query +=" AND  REQ_DTM  LIKE  :DEMD_YRMN  ||  '%'   \n";
        query +=" AND  RESERVED6  NOT  IN  ('bra03_r11',  'bra04_s25',  'bra05_s02',  'bra03_s01','HOMP'))  AS  SMS_CNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  KAKAO.MZSENDLOG  WHERE  USER_ID  =  XA.UPSO_CD   \n";
        query +=" AND  REQ_DTM  BETWEEN  XA.START_YRMN  ||  '01000000'   \n";
        query +=" and  XA.END_YRMN  ||  '31235959'   \n";
        query +=" AND  RESERVED6  NOT  IN  ('bra03_r11',  'bra04_s25',  'bra05_s02',  'bra03_s01','HOMP'))  AS  SMS_TOTCNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  XA.START_YRMN  ||  '01'   \n";
        query +=" and  XA.END_YRMN  ||  '31'   \n";
        query +=" AND  JOB_GBN  IN  ('V','T'))  AS  VISIT_TOTCNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  XA.START_YRMN  ||  '01'   \n";
        query +=" and  XA.END_YRMN  ||  '31'   \n";
        query +=" AND  INSPRES_ID  =  XB.STAFF_CD   \n";
        query +=" AND  JOB_GBN  =  'P')  AS  CALL_TOTCNT  FROM  (   \n";
        query +=" SELECT  DEMD_GBN  ,  UPSO_CD  ,  DEMD_YRMN  ,  MONPRNCFEE  ,  MONPRNCFEE2  ,  BSTYP_CD  ,  UPSO_GRAD  ,  DEMD_MMCNT  ,  START_YRMN  ,  END_YRMN  ,  TOT_USE_AMT  ,  TOT_ADDT_AMT  ,  ACCOUNT_NM  ,  BANK_NM1  ,  ACCN_NUM1  ,  BANK_NM2  ,  ACCN_NUM2  ,  BANK_NM3  ,  ACCN_NUM3  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  5)  MONPRNCFEE2  ,  GIBU.FT_SPLIT(DEMDS,  ',',  6)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  0)  AS  ACCOUNT_NM  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  2)  AS  BANK_NM1  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  3)  AS  ACCN_NUM1  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  5)  AS  BANK_NM2  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  6)  AS  ACCN_NUM2  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  8)  AS  BANK_NM3  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  9)  AS  ACCN_NUM3  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :DEMD_YRMN,  :DEMD_YRMN,  'O')  AS  DEMDS  ,  GIBU.FT_GET_VIRTUAL_ACCOUNT(A.UPSO_CD)  AS  BANK_RTN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  MAX(DEMD_MMCNT)  AS  DEMD_MMCNT  ,  MIN(START_YRMN)  AS  START_YRMN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  :DEMD_YRMN  ||  '31'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_NEW_ZIP  BETWEEN  NVL(:START_ZIP,  UPSO_NEW_ZIP)   \n";
        query +=" AND  NVL(:END_ZIP,  UPSO_NEW_ZIP)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :DEMD_YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  TO_NUMBER(:START_CNT_MON)   \n";
        query +=" AND  TO_NUMBER(:END_CNT_MON)  UNION  ALL   \n";
        query +=" SELECT  TB.UPSO_CD  ,  TB.OPBI_YRMN  ,  TB.OPBI_CNT  -  COUNT(TA.NOTE_YRMN)  DEMD_MMCNT  ,  :DEMD_YRMN  START_YRMN  FROM  GIBU.TBRA_NOTE  TA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  SUBSTR(A.OPBI_DAY,  1,  6)  OPBI_YRMN  ,  MONTHS_BETWEEN(TO_DATE(:DEMD_YRMN,  'YYYYMM'),  TO_DATE(SUBSTR(A.OPBI_DAY,  1,  6),  'YYYYMM'))  +  1  OPBI_CNT  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_NOTE  B  WHERE  A.UPSO_STAT  =  '1'   \n";
        query +=" AND  (A.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(A.CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  A.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NEW_DAY  <=  :DEMD_YRMN  ||  '31'   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  A.UPSO_NEW_ZIP  BETWEEN  NVL(:START_ZIP,  A.UPSO_NEW_ZIP)   \n";
        query +=" AND  NVL(:END_ZIP,  A.UPSO_NEW_ZIP)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.NOTE_YRMN  =  :DEMD_YRMN  )  TB  WHERE  TA.UPSO_CD  =  TB.UPSO_CD  GROUP  BY  TB.UPSO_CD,  TB.OPBI_YRMN,  TB.OPBI_CNT  HAVING  (TB.OPBI_CNT  -  COUNT(TA.NOTE_YRMN))  BETWEEN  TO_NUMBER(:START_CNT_MON)   \n";
        query +=" AND  TO_NUMBER(:END_CNT_MON)  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XA  ,  GIBU.TBRA_UPSO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(GOSO_YN)  GOSO_YN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(COMPN_DAY,  NULL,  '�����',  DECODE(JUDG_CD,  '2',  '�������',  '4',  '�����',  NULL))  GOSO_YN  FROM  GIBU.TBRA_ACCU  )  GROUP  BY  UPSO_CD  )  XD  ,  INSA.TCPM_BIPLC  XF  WHERE  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XA.UPSO_GRAD   \n";
        query +=" AND  XC.BSTYP_CD  =  XA.BSTYP_CD   \n";
        query +=" AND  XD.UPSO_CD  (+)  =  XA.UPSO_CD   \n";
        query +=" AND  XF.GIBU  =  XB.BRAN_CD   \n";
        query +=" AND  (:BSTYP_CD  IS  NULL   \n";
        query +=" OR  INSTR(:BSTYP_CD,  XC.BSTYP_CD)  >  0)   \n";
        query +=" AND  XA.DEMD_MMCNT  BETWEEN  TO_NUMBER(:START_CNT_MON)   \n";
        query +=" AND  TO_NUMBER(:END_CNT_MON)  ORDER  BY  SUBSTR(XB.UPSO_BD_MNG_NUM,1,5),  24,  XB.UPSO_NM ";
        sobj.setSql(query);
        sobj.setDouble("START_ZIP", START_ZIP);               //���� �����ȣ
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //���� ����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setDouble("END_ZIP", END_ZIP);               //���� �����ȣ
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //���� ����
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ��� ��ġ��
    // ���ǿ� �´� ��� ����Ÿ�� ��������
    public DOBJ CALLnonpy_select_MRG7(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG7");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL5, SEL6","");
        rvobj.setName("MRG7") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ��¡�� ������ȸ
    // ������ ������ ��¡�� ������ ��ȸ�Ѵ�
    public DOBJ CALLnonpy_select_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLnonpy_select_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.setRetcode(1);
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnonpy_select_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_ZIP = dvobj.getRecord().getDouble("START_ZIP");   //���� �����ȣ
        double   START_CNT_MON = dvobj.getRecord().getDouble("START_CNT_MON");   //���� ����
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        double   END_ZIP = dvobj.getRecord().getDouble("END_ZIP");   //���� �����ȣ
        double   END_CNT_MON = dvobj.getRecord().getDouble("END_CNT_MON");   //���� ����
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //û�� ���
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  optimizer_features_enable('11.1.0.6')  */  XA.DEMD_MMCNT  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  DECODE(XB.MNGEMSTR_RESINUM  ,  NULL,  '',  'OK')  CHK_RESINUM  ,  XB.MNGEMSTR_NM  ,  DECODE(TRIM(XA.BSTYP_CD),  'o',  GIBU.FT_GET_NOREBANG_GRAD(XB.UPSO_CD),  XC.GRADNM)  GRADNM  ,  DECODE  ((SELECT  NVL(COUNT(UPSO_CD),  0)  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL),  0,  '',  'ä��')  BOND  ,  XA.MONPRNCFEE  ,  XA.MONPRNCFEE2  ,  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO  UPSO_ADDR  ,  XB.UPSO_PHON  ,  XB.MNGEMSTR_HPNUM  ,  XA.TOT_USE_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_USE_AMT  +  XA.TOT_ADDT_AMT  AS  TOT_AMT  ,  DECODE(XB.BEFORE_UPSO_CD,  NULL,  '',  '��'  ||  TO_CHAR(XB.INS_DATE,  'YYYYMMDD'))  ||  '  '  ||  NVL(XD.GOSO_YN,  '')  REMAK  ,  XB.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,   \n";
        query +=" (SELECT  ATTE||'  '||CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =XB.UPSO_BD_MNG_NUM)  AS  SIGUGUN  ,  SUBSTR(XA.START_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.START_YRMN,  5,  2)  START_YRMN  ,  SUBSTR(XA.END_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.END_YRMN,  5,  2)  END_YRMN  ,   \n";
        query +=" (SELECT  IPPBX_INPHONE_NUM  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  XB.STAFF_CD)  AS  BRAN_PHON_NUM  ,  DECODE((SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  TERM_YN  =  'N'),0,'','OK')  AUTO_YN  ,  '0'  AS  COL_CHECK  ,   \n";
        query +=" (SELECT  DECODE(COURT_NM,  NULL,  TOWNTWSHP,  COURT_NM)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XB.UPSO_BD_MNG_NUM  )  AS  DONG  ,  XB.CLIENT_NUM  ,  XB.BRAN_CD  ,  XB.STAFF_CD  ,  FIDU.GET_STAFF_NM(XB.STAFF_CD)  AS  STAFF_NM  ,  (CASE  WHEN  XA.DEMD_MMCNT  >=  6  THEN  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  JOB_GBN  IN  ('V',  'T')   \n";
        query +=" AND  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  XA.START_YRMN  ||  '01'   \n";
        query +=" AND  TO_CHAR(ADD_MONTHS(TO_DATE(XA.START_YRMN,  'YYYYMM'),  4),  'YYYYMM')  ||  '31')  <  1  THEN  'N'  ELSE  'Y'  END)  ELSE  ''  END)  AS  VISIT_YN5  ,  (CASE  WHEN  XA.DEMD_MMCNT  >=  6  THEN  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  JOB_GBN  IN  ('V',  'T')   \n";
        query +=" AND  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  TO_CHAR(ADD_MONTHS(TO_DATE(XA.START_YRMN,  'YYYYMM'),  5),  'YYYYMM')  ||  '01'   \n";
        query +=" AND  TO_CHAR(SYSDATE,  'YYYYMMDD'))  <  1  THEN  'N'  ELSE  'Y'  END)  ELSE  ''  END)  AS  VISIT_YN6  ,  ACCOUNT_NM  ,  BANK_NM1  ,  ACCN_NUM1  ,  BANK_NM2  ,  ACCN_NUM2  ,  BANK_NM3  ,  ACCN_NUM3  ,  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_STOMU_CONTRINFO  WHERE  UPSO_CD  =  XB.UPSO_CD)  >  0  THEN  'Y'  ELSE  'N'  END)  AS  STOMU_YN  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  KAKAO.MZSENDLOG  WHERE  USER_ID  =  XA.UPSO_CD   \n";
        query +=" AND  REQ_DTM  LIKE  :DEMD_YRMN  ||  '%'   \n";
        query +=" AND  RESERVED6  NOT  IN  ('bra03_r11',  'bra04_s25',  'bra05_s02',  'bra03_s01','HOMP'))  AS  SMS_CNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  KAKAO.MZSENDLOG  WHERE  USER_ID  =  XA.UPSO_CD   \n";
        query +=" AND  REQ_DTM  BETWEEN  XA.START_YRMN  ||  '01000000'   \n";
        query +=" and  XA.END_YRMN  ||  '31235959'   \n";
        query +=" AND  RESERVED6  NOT  IN  ('bra03_r11',  'bra04_s25',  'bra05_s02',  'bra03_s01','HOMP'))  AS  SMS_TOTCNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  XA.START_YRMN  ||  '01'   \n";
        query +=" and  XA.END_YRMN  ||  '31'   \n";
        query +=" AND  JOB_GBN  IN  ('V','T'))  AS  VISIT_TOTCNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  XA.START_YRMN  ||  '01'   \n";
        query +=" and  XA.END_YRMN  ||  '31'   \n";
        query +=" AND  INSPRES_ID  =  XB.STAFF_CD   \n";
        query +=" AND  JOB_GBN  =  'P')  AS  CALL_TOTCNT  FROM  (   \n";
        query +=" SELECT  DEMD_GBN  ,  UPSO_CD  ,  DEMD_YRMN  ,  MONPRNCFEE  ,  MONPRNCFEE2  ,  BSTYP_CD  ,  UPSO_GRAD  ,  DEMD_MMCNT  ,  START_YRMN  ,  END_YRMN  ,  TOT_USE_AMT  ,  TOT_ADDT_AMT  ,  ACCOUNT_NM  ,  BANK_NM1  ,  ACCN_NUM1  ,  BANK_NM2  ,  ACCN_NUM2  ,  BANK_NM3  ,  ACCN_NUM3  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  5)  MONPRNCFEE2  ,  GIBU.FT_SPLIT(DEMDS,  ',',  6)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  0)  AS  ACCOUNT_NM  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  2)  AS  BANK_NM1  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  3)  AS  ACCN_NUM1  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  5)  AS  BANK_NM2  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  6)  AS  ACCN_NUM2  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  8)  AS  BANK_NM3  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  9)  AS  ACCN_NUM3  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :DEMD_YRMN,  :DEMD_YRMN,  'O')  DEMDS  ,  GIBU.FT_GET_VIRTUAL_ACCOUNT(A.UPSO_CD)  AS  BANK_RTN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  MAX(DEMD_MMCNT)  AS  DEMD_MMCNT  ,  MIN(START_YRMN)  AS  START_YRMN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_NEW_ZIP  BETWEEN  NVL(:START_ZIP,  UPSO_NEW_ZIP)   \n";
        query +=" AND  NVL(:END_ZIP,  UPSO_NEW_ZIP)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :DEMD_YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  TO_NUMBER(:START_CNT_MON)   \n";
        query +=" AND  TO_NUMBER(:END_CNT_MON)  UNION  ALL   \n";
        query +=" SELECT  TB.UPSO_CD  ,  TB.OPBI_YRMN  ,  TB.OPBI_CNT  -  COUNT(TA.NOTE_YRMN)  DEMD_MMCNT  ,  :DEMD_YRMN  START_YRMN  FROM  GIBU.TBRA_NOTE  TA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  SUBSTR(A.OPBI_DAY,  1,  6)  OPBI_YRMN  ,  MONTHS_BETWEEN(TO_DATE(:DEMD_YRMN,  'YYYYMM'),  TO_DATE(SUBSTR(A.OPBI_DAY,  1,  6),  'YYYYMM'))  +  1  OPBI_CNT  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_NOTE  B  WHERE  (A.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(A.CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  A.NEW_DAY  IS  NULL   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  A.UPSO_NEW_ZIP  BETWEEN  NVL(:START_ZIP,  A.UPSO_NEW_ZIP)   \n";
        query +=" AND  NVL(:END_ZIP,  A.UPSO_NEW_ZIP)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.NOTE_YRMN  =  :DEMD_YRMN  )  TB  WHERE  TA.UPSO_CD  =  TB.UPSO_CD  GROUP  BY  TB.UPSO_CD,  TB.OPBI_YRMN,  TB.OPBI_CNT  HAVING  (TB.OPBI_CNT  -  COUNT(TA.NOTE_YRMN))  BETWEEN  TO_NUMBER(:START_CNT_MON)   \n";
        query +=" AND  TO_NUMBER(:END_CNT_MON)  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XA  ,  GIBU.TBRA_UPSO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(GOSO_YN)  GOSO_YN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(COMPN_DAY,  NULL,  '�����',  DECODE(JUDG_CD,  '2',  '�������',  '4',  '�����',  NULL))  GOSO_YN  FROM  GIBU.TBRA_ACCU  )  GROUP  BY  UPSO_CD  )  XD  ,  INSA.TCPM_BIPLC  XF  WHERE  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XA.UPSO_GRAD   \n";
        query +=" AND  XC.BSTYP_CD  =  XA.BSTYP_CD   \n";
        query +=" AND  XD.UPSO_CD  (+)  =  XA.UPSO_CD   \n";
        query +=" AND  XF.GIBU  =  XB.BRAN_CD   \n";
        query +=" AND  (:BSTYP_CD  IS  NULL   \n";
        query +=" OR  INSTR(:BSTYP_CD,  XC.BSTYP_CD)  >  0)   \n";
        query +=" AND  XA.DEMD_MMCNT  BETWEEN  TO_NUMBER(:START_CNT_MON)   \n";
        query +=" AND  TO_NUMBER(:END_CNT_MON)  ORDER  BY  SUBSTR(XB.UPSO_BD_MNG_NUM,1,5),  24,  XB.UPSO_NM ";
        sobj.setSql(query);
        sobj.setDouble("START_ZIP", START_ZIP);               //���� �����ȣ
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //���� ����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setDouble("END_ZIP", END_ZIP);               //���� �����ȣ
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //���� ����
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ��¡�� ������ȸ
    // ��� ������ ��¡�� ������ ��ȸ�Ѵ�
    public DOBJ CALLnonpy_select_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLnonpy_select_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.setRetcode(1);
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnonpy_select_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_ZIP = dvobj.getRecord().getDouble("START_ZIP");   //���� �����ȣ
        double   START_CNT_MON = dvobj.getRecord().getDouble("START_CNT_MON");   //���� ����
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        double   END_ZIP = dvobj.getRecord().getDouble("END_ZIP");   //���� �����ȣ
        double   END_CNT_MON = dvobj.getRecord().getDouble("END_CNT_MON");   //���� ����
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //û�� ���
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  optimizer_features_enable('11.1.0.6')  */  XA.DEMD_MMCNT  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  DECODE(XB.MNGEMSTR_RESINUM  ,  NULL,  '',  'OK')  CHK_RESINUM  ,  XB.MNGEMSTR_NM  ,  DECODE(TRIM(XA.BSTYP_CD),  'o',  GIBU.FT_GET_NOREBANG_GRAD(XB.UPSO_CD),  XC.GRADNM)  GRADNM  ,  DECODE  ((SELECT  NVL(COUNT(UPSO_CD),  0)  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL),  0,  '',  'ä��')  BOND  ,  XA.MONPRNCFEE  ,  XA.MONPRNCFEE2  ,  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO  UPSO_ADDR  ,  XB.UPSO_PHON  ,  XB.MNGEMSTR_HPNUM  ,  XA.TOT_USE_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_USE_AMT  +  XA.TOT_ADDT_AMT  AS  TOT_AMT  ,  DECODE(XB.BEFORE_UPSO_CD,  NULL,  '',  '��'  ||  TO_CHAR(XB.INS_DATE,  'YYYYMMDD'))  ||  '  '  ||  NVL(XD.GOSO_YN,  '')  REMAK  ,  XB.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,   \n";
        query +=" (SELECT  ATTE||'  '||CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =XB.UPSO_BD_MNG_NUM)  AS  SIGUGUN  ,  SUBSTR(XA.START_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.START_YRMN,  5,  2)  START_YRMN  ,  SUBSTR(XA.END_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.END_YRMN,  5,  2)  END_YRMN  ,   \n";
        query +=" (SELECT  IPPBX_INPHONE_NUM  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  XB.STAFF_CD)  AS  BRAN_PHON_NUM  ,  DECODE((SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  TERM_YN  =  'N'),0,'','OK')  AUTO_YN  ,  '0'  AS  COL_CHECK  ,   \n";
        query +=" (SELECT  DECODE(COURT_NM,  NULL,  TOWNTWSHP,  COURT_NM)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XB.UPSO_BD_MNG_NUM  )  AS  DONG  ,  XB.CLIENT_NUM  ,  XB.BRAN_CD  ,  XB.STAFF_CD  ,  FIDU.GET_STAFF_NM(XB.STAFF_CD)  AS  STAFF_NM  ,  (CASE  WHEN  XA.DEMD_MMCNT  >=  6  THEN  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  JOB_GBN  IN  ('V',  'T')   \n";
        query +=" AND  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  XA.START_YRMN  ||  '01'   \n";
        query +=" AND  TO_CHAR(ADD_MONTHS(TO_DATE(XA.START_YRMN,  'YYYYMM'),  4),  'YYYYMM')  ||  '31')  <  1  THEN  'N'  ELSE  'Y'  END)  ELSE  ''  END)  AS  VISIT_YN5  ,  (CASE  WHEN  XA.DEMD_MMCNT  >=  6  THEN  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  JOB_GBN  IN  ('V',  'T')   \n";
        query +=" AND  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  TO_CHAR(ADD_MONTHS(TO_DATE(XA.START_YRMN,  'YYYYMM'),  5),  'YYYYMM')  ||  '01'   \n";
        query +=" AND  TO_CHAR(SYSDATE,  'YYYYMMDD'))  <  1  THEN  'N'  ELSE  'Y'  END)  ELSE  ''  END)  AS  VISIT_YN6  ,  ACCOUNT_NM  ,  BANK_NM1  ,  ACCN_NUM1  ,  BANK_NM2  ,  ACCN_NUM2  ,  BANK_NM3  ,  ACCN_NUM3  ,  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_STOMU_CONTRINFO  WHERE  UPSO_CD  =  XB.UPSO_CD)  >  0  THEN  'Y'  ELSE  'N'  END)  AS  STOMU_YN  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  KAKAO.MZSENDLOG  WHERE  USER_ID  =  XA.UPSO_CD   \n";
        query +=" AND  REQ_DTM  LIKE  :DEMD_YRMN  ||  '%'   \n";
        query +=" AND  RESERVED6  NOT  IN  ('bra03_r11',  'bra04_s25',  'bra05_s02',  'bra03_s01','HOMP'))  AS  SMS_CNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  KAKAO.MZSENDLOG  WHERE  USER_ID  =  XA.UPSO_CD   \n";
        query +=" AND  REQ_DTM  BETWEEN  XA.START_YRMN  ||  '01000000'   \n";
        query +=" and  XA.END_YRMN  ||  '31235959'   \n";
        query +=" AND  RESERVED6  NOT  IN  ('bra03_r11',  'bra04_s25',  'bra05_s02',  'bra03_s01','HOMP'))  AS  SMS_TOTCNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  XA.START_YRMN  ||  '01'   \n";
        query +=" and  XA.END_YRMN  ||  '31'   \n";
        query +=" AND  JOB_GBN  IN  ('V','T'))  AS  VISIT_TOTCNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  XA.START_YRMN  ||  '01'   \n";
        query +=" and  XA.END_YRMN  ||  '31'   \n";
        query +=" AND  INSPRES_ID  =  XB.STAFF_CD   \n";
        query +=" AND  JOB_GBN  =  'P')  AS  CALL_TOTCNT  FROM  (   \n";
        query +=" SELECT  DEMD_GBN  ,  UPSO_CD  ,  DEMD_YRMN  ,  MONPRNCFEE  ,  MONPRNCFEE2  ,  BSTYP_CD  ,  UPSO_GRAD  ,  DEMD_MMCNT  ,  START_YRMN  ,  END_YRMN  ,  TOT_USE_AMT  ,  TOT_ADDT_AMT  ,  ACCOUNT_NM  ,  BANK_NM1  ,  ACCN_NUM1  ,  BANK_NM2  ,  ACCN_NUM2  ,  BANK_NM3  ,  ACCN_NUM3  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  5)  MONPRNCFEE2  ,  GIBU.FT_SPLIT(DEMDS,  ',',  6)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  0)  AS  ACCOUNT_NM  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  2)  AS  BANK_NM1  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  3)  AS  ACCN_NUM1  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  5)  AS  BANK_NM2  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  6)  AS  ACCN_NUM2  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  8)  AS  BANK_NM3  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  9)  AS  ACCN_NUM3  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :DEMD_YRMN,  :DEMD_YRMN,  'O')  DEMDS  ,  GIBU.FT_GET_VIRTUAL_ACCOUNT(A.UPSO_CD)  AS  BANK_RTN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  MAX(DEMD_MMCNT)  AS  DEMD_MMCNT  ,  MIN(START_YRMN)  AS  START_YRMN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  NEW_DAY  <=  :DEMD_YRMN  ||  '31')   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_NEW_ZIP  BETWEEN  NVL(:START_ZIP,  UPSO_NEW_ZIP)   \n";
        query +=" AND  NVL(:END_ZIP,  UPSO_NEW_ZIP)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :DEMD_YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  TO_NUMBER(:START_CNT_MON)   \n";
        query +=" AND  TO_NUMBER(:END_CNT_MON)  UNION  ALL   \n";
        query +=" SELECT  TB.UPSO_CD  ,  TB.OPBI_YRMN  ,  TB.OPBI_CNT  -  COUNT(TA.NOTE_YRMN)  DEMD_MMCNT  ,  :DEMD_YRMN  START_YRMN  FROM  GIBU.TBRA_NOTE  TA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  SUBSTR(A.OPBI_DAY,  1,  6)  OPBI_YRMN  ,  MONTHS_BETWEEN(TO_DATE(:DEMD_YRMN,  'YYYYMM'),  TO_DATE(SUBSTR(A.OPBI_DAY,  1,  6),  'YYYYMM'))  +  1  OPBI_CNT  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_NOTE  B  WHERE  (A.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(A.CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  (A.NEW_DAY  IS  NULL   \n";
        query +=" OR  A.NEW_DAY  <=  :DEMD_YRMN  ||  '31')   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  A.UPSO_NEW_ZIP  BETWEEN  NVL(:START_ZIP,  A.UPSO_NEW_ZIP)   \n";
        query +=" AND  NVL(:END_ZIP,  A.UPSO_NEW_ZIP)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.NOTE_YRMN  =  :DEMD_YRMN  )  TB  WHERE  TA.UPSO_CD  =  TB.UPSO_CD  GROUP  BY  TB.UPSO_CD,  TB.OPBI_YRMN,  TB.OPBI_CNT  HAVING  (TB.OPBI_CNT  -  COUNT(TA.NOTE_YRMN))  BETWEEN  TO_NUMBER(:START_CNT_MON)   \n";
        query +=" AND  TO_NUMBER(:END_CNT_MON)  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XA  ,  GIBU.TBRA_UPSO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(GOSO_YN)  GOSO_YN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(COMPN_DAY,  NULL,  '�����',  DECODE(JUDG_CD,  '2',  '�������',  '4',  '�����',  NULL))  GOSO_YN  FROM  GIBU.TBRA_ACCU  )  GROUP  BY  UPSO_CD  )  XD  ,  INSA.TCPM_BIPLC  XF  WHERE  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XA.UPSO_GRAD   \n";
        query +=" AND  XC.BSTYP_CD  =  XA.BSTYP_CD   \n";
        query +=" AND  XD.UPSO_CD  (+)  =  XA.UPSO_CD   \n";
        query +=" AND  XF.GIBU  =  XB.BRAN_CD   \n";
        query +=" AND  (:BSTYP_CD  IS  NULL   \n";
        query +=" OR  INSTR(:BSTYP_CD,  XC.BSTYP_CD)  >  0)   \n";
        query +=" AND  XA.DEMD_MMCNT  BETWEEN  TO_NUMBER(:START_CNT_MON)   \n";
        query +=" AND  TO_NUMBER(:END_CNT_MON)  ORDER  BY  SUBSTR(XB.UPSO_BD_MNG_NUM,1,5),  24,  XB.UPSO_NM ";
        sobj.setSql(query);
        sobj.setDouble("START_ZIP", START_ZIP);               //���� �����ȣ
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //���� ����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setDouble("END_ZIP", END_ZIP);               //���� �����ȣ
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //���� ����
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$nonpy_select
    //##**$$nonpy_dispList_save
    /* * ���α׷��� : bra04_r07
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/11
    * ����    : �ܺι߼� ����Ʈ�� �����Ѵ�.
    ��¡�� ������ [�ܺι߼�] ��ư Ŭ�� �� �߻��ϴ� �̺�Ʈ ó��
    Input
    BSTYP_CD (���� �ڵ� )
    YRMN (���)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLnonpy_dispList_save(DOBJ dobj)
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
            dobj  = CALLnonpy_dispList_save_XIUD3(Conn, dobj);           //  �ش��� ����Ÿ ����
            dobj  = CALLnonpy_dispList_save_XIUD7(Conn, dobj);           //  �ܺ� �߼� ����Ʈ ����
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
    public DOBJ CTLnonpy_dispList_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLnonpy_dispList_save_XIUD3(Conn, dobj);           //  �ش��� ����Ÿ ����
        dobj  = CALLnonpy_dispList_save_XIUD7(Conn, dobj);           //  �ܺ� �߼� ����Ʈ ����
        return dobj;
    }
    // �ش��� ����Ÿ ����
    public DOBJ CALLnonpy_dispList_save_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLnonpy_dispList_save_XIUD3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnonpy_dispList_save_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE GIBU.TBRA_NOLEV_DISP_LIST  \n";
        query +=" WHERE DISP_DAY = TO_CHAR(SYSDATE, 'YYYYMMDD')";
        sobj.setSql(query);
        return sobj;
    }
    // �ܺ� �߼� ����Ʈ ����
    public DOBJ CALLnonpy_dispList_save_XIUD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD7");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLnonpy_dispList_save_XIUD7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnonpy_dispList_save_XIUD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        double   END_CNT_MON = dvobj.getRecord().getDouble("END_CNT_MON");   //���� ����
        double   END_ZIP = dvobj.getRecord().getDouble("END_ZIP");   //���� �����ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        double   START_CNT_MON = dvobj.getRecord().getDouble("START_CNT_MON");   //���� ����
        double   START_ZIP = dvobj.getRecord().getDouble("START_ZIP");   //���� �����ȣ
        String   YRMN = dvobj.getRecord().get("YRMN");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_NOLEV_DISP_LIST ( DISP_DAY , UPSO_CD , DEMD_YRMN , START_YRMN , END_YRMN , DEMD_MMCNT , DEMD_AMT , ADDT_AMT , INSPRES_ID , INS_DATE ) SELECT TO_CHAR(SYSDATE,'YYYYMMDD') , XB.UPSO_CD , XA.DEMD_YRMN , XA.START_YRMN , XA.END_YRMN , XA.DEMD_MMCNT , XA.TOT_DEMD_AMT , XA.TOT_ADDT_AMT , :INSPRES_ID , SYSDATE FROM ( SELECT A.DEMD_GBN , A.UPSO_CD , A.DEMD_YRMN , A.TOT_DEMD_AMT - (A.TOT_ADDT_AMT + A.TOT_EADDT_AMT) TOT_DEMD_AMT , A.TOT_ADDT_AMT + A.TOT_EADDT_AMT TOT_ADDT_AMT , A.DEMD_MMCNT , A.MONPRNCFEE , A.START_YRMN , A.END_YRMN FROM GIBU.TBRA_DEMD_OCR A WHERE UPSO_CD IN (SELECT UPSO_CD FROM GIBU.TBRA_UPSO WHERE BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)) AND DEMD_YRMN > ( SELECT NVL(MAX(DEMD_YRMN), '190001') FROM GIBU.TBRA_DEMD_REPT WHERE UPSO_CD = A.UPSO_CD ) AND DEMD_YRMN = :YRMN ORDER BY DEMD_YRMN DESC ) XA , GIBU.TBRA_UPSO XB , ( SELECT ZA.UPSO_CD , ZB.MONPRNCFEE , ZC.GRADNM , ZB.BSTYP_CD , ZB.UPSO_GRAD , TRIM(ZB.BSTYP_CD) || ZB.UPSO_GRAD GRAD , ZB.CHG_NUM FROM ( SELECT MAX(JOIN_SEQ) JOIN_SEQ, UPSO_CD FROM GIBU.TBRA_UPSORTAL_INFO WHERE UPSO_CD IN ( SELECT UPSO_CD FROM GIBU.TBRA_DEMD_OCR A WHERE UPSO_CD IN (SELECT UPSO_CD FROM GIBU.TBRA_UPSO WHERE BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)) AND DEMD_YRMN > ( SELECT NVL(MAX(DEMD_YRMN), '190001') FROM GIBU.TBRA_DEMD_REPT WHERE UPSO_CD = A.UPSO_CD ) AND DEMD_YRMN = :YRMN ) AND APPL_DAY <= :YRMN || '32' GROUP BY UPSO_CD ) ZA , GIBU.TBRA_UPSORTAL_INFO ZB , GIBU.TBRA_BSTYPGRAD ZC WHERE ZA.UPSO_CD = ZB.UPSO_CD AND ZA.JOIN_SEQ = ZB.JOIN_SEQ AND ZC.GRAD_GBN = ZB.BSTYP_CD AND ZC.BSTYP_CD = 'z' ) XC , ( SELECT COUNT(UPSO_CD) ACCU_CNT , UPSO_CD FROM GIBU.TBRA_ACCU WHERE COMPN_DAY IS NULL GROUP BY UPSO_CD ) XD , ( SELECT DISTINCT ZIP , ATTE , DSRCCNTY FROM GIBU.TBRA_BRANZIP_MNG ) XE WHERE XA.DEMD_MMCNT BETWEEN :START_CNT_MON AND :END_CNT_MON AND XB.UPSO_CD = XA.UPSO_CD AND XB.BRAN_CD = DECODE(:BRAN_CD, 'AL', XB.BRAN_CD, :BRAN_CD) AND XB.STAFF_CD = NVL(:STAFF_CD, XB.STAFF_CD) AND XB.UPSO_ZIP BETWEEN NVL(:START_ZIP, XB.UPSO_ZIP) AND NVL(:END_ZIP, XB.UPSO_ZIP) AND XC.UPSO_CD = XA.UPSO_CD AND TRIM(XC.BSTYP_CD) = DECODE(NVL(:BSTYP_CD, 'A'), 'A', TRIM(XC.BSTYP_CD), :BSTYP_CD) AND XD.UPSO_CD (+) = XA.UPSO_CD AND XE.ZIP = XB.UPSO_ZIP";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //���� ����
        sobj.setDouble("END_ZIP", END_ZIP);               //���� �����ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //���� ����
        sobj.setDouble("START_ZIP", START_ZIP);               //���� �����ȣ
        sobj.setString("YRMN", YRMN);               //���
        return sobj;
    }
    //##**$$nonpy_dispList_save
    //##**$$nonpy_dispList_save2
    /* * ���α׷��� : bra04_r07
    * �ۼ��� : �ǳ���
    * �ۼ��� : 2010/07/21
    * ����    : ����(�ܺ� �߼� ����Ʈ ����)�� ��� ��ȸ�� ����Ÿ���� �����ϴ°� �ƴ϶�,
    TBRA_DEMD_OCR�� �̿��Ͽ� �ű� �߻��ϹǷ� ��ȸ�� ȭ��� ����Ÿ ���̰� �߻�
    �Ҽ� �����Ƿ� ������.
    �ܺι߼� ����Ʈ�� �����Ѵ�.
    ��¡�� ������ [�ܺι߼�] ��ư Ŭ�� �� �߻��ϴ� �̺�Ʈ ó��
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLnonpy_dispList_save2(DOBJ dobj)
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
            dobj  = CALLnonpy_dispList_save2_SEL4(Conn, dobj);           //  �߻����� �߹�
            dobj  = CALLnonpy_dispList_save2_MPD2(Conn, dobj);           //  �ο� ���� ó��
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
    public DOBJ CTLnonpy_dispList_save2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLnonpy_dispList_save2_SEL4(Conn, dobj);           //  �߻����� �߹�
        dobj  = CALLnonpy_dispList_save2_MPD2(Conn, dobj);           //  �ο� ���� ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �߻����� �߹�
    public DOBJ CALLnonpy_dispList_save2_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLnonpy_dispList_save2_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnonpy_dispList_save2_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(SYSDATE,'YYYYMMDD')  AS  SYS_DATE  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // �ο� ���� ó��
    public DOBJ CALLnonpy_dispList_save2_MPD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD2");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLnonpy_dispList_save2_DEL3(Conn, dobj);           //  ���� �ڷ� ����
                dobj  = CALLnonpy_dispList_save2_INS8(Conn, dobj);           //  �߼۳��� ����
            }
        }
        return dobj;
    }
    // ���� �ڷ� ����
    public DOBJ CALLnonpy_dispList_save2_DEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL3");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLnonpy_dispList_save2_DEL3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL3") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnonpy_dispList_save2_DEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DISP_DAY = dobj.getRetObject("SEL4").getRecord().get("SYS_DATE");   //�߼� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_NOLEV_DISP_LIST  \n";
        query +=" where DISP_DAY=:DISP_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("DISP_DAY", DISP_DAY);               //�߼� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �߼۳��� ����
    public DOBJ CALLnonpy_dispList_save2_INS8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLnonpy_dispList_save2_INS8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnonpy_dispList_save2_INS8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   DEMD_MMCNT = dvobj.getRecord().getDouble("DEMD_MMCNT");   //û��������
        double   ADDT_AMT = dobj.getRetObject("R").getRecord().getDouble("TOT_ADDT_AMT");   //���� �ݾ�
        double   DEMD_AMT = dobj.getRetObject("R").getRecord().getDouble("TOT_USE_AMT");   //û�� �ݾ�
        String   DEMD_YRMN = wutil.getStringFormatDel(dobj.getRetObject("R").getRecord().get("END_YRMN"),"/");   //û�� ���
        String   DISP_DAY = dobj.getRetObject("SEL4").getRecord().get("SYS_DATE");   //�߼� ����
        String   END_YRMN = wutil.getStringFormatDel(dobj.getRetObject("R").getRecord().get("END_YRMN"),"/");   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   START_YRMN = wutil.getStringFormatDel(dobj.getRetObject("R").getRecord().get("START_YRMN"),"/");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_NOLEV_DISP_LIST (INS_DATE, INSPRES_ID, DISP_DAY, DEMD_YRMN, DEMD_MMCNT, UPSO_CD, ADDT_AMT, DEMD_AMT, END_YRMN, START_YRMN)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :DISP_DAY , :DEMD_YRMN , :DEMD_MMCNT , :UPSO_CD , :ADDT_AMT , :DEMD_AMT , :END_YRMN , :START_YRMN )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("DEMD_MMCNT", DEMD_MMCNT);               //û��������
        sobj.setDouble("ADDT_AMT", ADDT_AMT);               //���� �ݾ�
        sobj.setDouble("DEMD_AMT", DEMD_AMT);               //û�� �ݾ�
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("DISP_DAY", DISP_DAY);               //�߼� ����
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    //##**$$nonpy_dispList_save2
    //##**$$sel_history_list
    /*
    */
    public DOBJ CTLsel_history_list(DOBJ dobj)
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
            dobj  = CALLsel_history_list_SEL1(Conn, dobj);           //  �̷���ȸ
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
    public DOBJ CTLsel_history_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_history_list_SEL1(Conn, dobj);           //  �̷���ȸ
        return dobj;
    }
    // �̷���ȸ
    public DOBJ CALLsel_history_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_history_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_history_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  PRCON_YRMN  ,  INSPRES_ID  ,  FIDU.GET_STAFF_NM(INSPRES_ID)  AS  INSPRES_NM  ,  INS_DATE  FROM  GIBU.TBRA_NONPY_PRCON_HISTORY  ORDER  BY  PRCON_YRMN  DESC ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$sel_history_list
    //##**$$chk_is_history
    /*
    */
    public DOBJ CTLchk_ishistory(DOBJ dobj)
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
            dobj  = CALLchk_is_history_SEL1(Conn, dobj);           //  �����̷����翩��Ȯ��
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
    public DOBJ CTLchk_ishistory( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLchk_is_history_SEL1(Conn, dobj);           //  �����̷����翩��Ȯ��
        return dobj;
    }
    // �����̷����翩��Ȯ��
    public DOBJ CALLchk_is_history_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLchk_is_history_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_is_history_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_NONPY_PRCON_HISTORY  WHERE  PRCON_YRMN  =  :PRCON_YRMN ";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    //##**$$chk_is_history
    //##**$$save_nonpy_history
    /*
    */
    public DOBJ CTLsave_nonpy_history(DOBJ dobj)
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
            dobj  = CALLsave_nonpy_history_DEL5(Conn, dobj);           //  ������ ����� �̷� ����
            dobj  = CALLsave_nonpy_history_DEL6(Conn, dobj);           //  ������ ����� ��Ȳ ����
            dobj  = CALLsave_nonpy_history_INS7(Conn, dobj);           //  �̷µ��
            dobj  = CALLsave_nonpy_history_SEL1(Conn, dobj);           //  �¿�������Ÿ¡���ݾ���Ȳ
            dobj  = CALLsave_nonpy_history_INS2(Conn, dobj);           //  ����
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
    public DOBJ CTLsave_nonpy_history( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_nonpy_history_DEL5(Conn, dobj);           //  ������ ����� �̷� ����
        dobj  = CALLsave_nonpy_history_DEL6(Conn, dobj);           //  ������ ����� ��Ȳ ����
        dobj  = CALLsave_nonpy_history_INS7(Conn, dobj);           //  �̷µ��
        dobj  = CALLsave_nonpy_history_SEL1(Conn, dobj);           //  �¿�������Ÿ¡���ݾ���Ȳ
        dobj  = CALLsave_nonpy_history_INS2(Conn, dobj);           //  ����
        return dobj;
    }
    // ������ ����� �̷� ����
    public DOBJ CALLsave_nonpy_history_DEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_nonpy_history_DEL5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_nonpy_history_DEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dvobj.getRecord().get("PRCON_YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_NONPY_PRCON_HISTORY  \n";
        query +=" where PRCON_YRMN=:PRCON_YRMN";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    // ������ ����� ��Ȳ ����
    public DOBJ CALLsave_nonpy_history_DEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_nonpy_history_DEL6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_nonpy_history_DEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dvobj.getRecord().get("PRCON_YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_NONPY_PRCON  \n";
        query +=" where PRCON_YRMN=:PRCON_YRMN";
        sobj.setSql(query);
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    // �̷µ��
    public DOBJ CALLsave_nonpy_history_INS7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("INS7");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_nonpy_history_INS7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
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
    private SQLObject SQLsave_nonpy_history_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_NONPY_PRCON_HISTORY (INS_DATE, INSPRES_ID, PRCON_YRMN)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :PRCON_YRMN )";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    // �¿�������Ÿ¡���ݾ���Ȳ
    public DOBJ CALLsave_nonpy_history_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsave_nonpy_history_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_nonpy_history_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_ZIP = dobj.getRetObject("S").getRecord().getDouble("START_ZIP");   //���� �����ȣ
        double   START_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("START_CNT_MON");   //���� ����
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        double   END_ZIP = dobj.getRetObject("S").getRecord().getDouble("END_ZIP");   //���� �����ȣ
        double   END_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("END_CNT_MON");   //���� ����
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //û�� ���
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '1'  AS  GBN  ,  XA.DEMD_MMCNT  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  DECODE(XB.MNGEMSTR_RESINUM  ,  NULL,  '',  'OK')  AS  CHK_RESINUM  ,  XB.MNGEMSTR_NM  ,  DECODE(TRIM(XA.BSTYP_CD),  'o',  GIBU.FT_GET_NOREBANG_GRAD(XB.UPSO_CD),  XC.GRADNM)  AS  GRADNM  ,  DECODE  ((SELECT  NVL(COUNT(UPSO_CD),  0)  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL),  0,  '',  'ä��')  AS  BOND  ,  XA.MONPRNCFEE  ,  XA.MONPRNCFEE2  ,  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO  AS  UPSO_ADDR  ,  XB.UPSO_PHON  ,  XB.MNGEMSTR_HPNUM  ,  XA.TOT_USE_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_USE_AMT  +  XA.TOT_ADDT_AMT  AS  TOT_AMT  ,  DECODE(XB.BEFORE_UPSO_CD,  NULL,  '',  '��'  ||  TO_CHAR(XB.INS_DATE,  'YYYYMMDD'))  ||  '  '  ||  NVL(XD.GOSO_YN,  '')  AS  REMAK  ,  XB.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,   \n";
        query +=" (SELECT  ATTE||'  '||CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =XB.UPSO_BD_MNG_NUM)  AS  SIGUGUN  ,  SUBSTR(XA.START_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.START_YRMN,  5,  2)  AS  START_YRMN  ,  SUBSTR(XA.END_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.END_YRMN,  5,  2)  AS  END_YRMN  ,  XF.PHON_NUM  AS  BRAN_PHON_NUM  ,  DECODE((SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  TERM_YN  =  'N'),0,'','OK')  AS  AUTO_YN  ,  '0'  AS  COL_CHECK  ,   \n";
        query +=" (SELECT  DECODE(COURT_NM,  NULL,  TOWNTWSHP,  COURT_NM)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XB.UPSO_BD_MNG_NUM  )  AS  DONG  ,  XB.CLIENT_NUM  ,  XB.BRAN_CD  ,  XB.STAFF_CD  ,  FIDU.GET_STAFF_NM(XB.STAFF_CD)  AS  STAFF_NM  ,  XA.BSTYP_CD  ,  (CASE  WHEN  XA.DEMD_MMCNT  >=  6  THEN  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  JOB_GBN  IN  ('V',  'T')   \n";
        query +=" AND  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  XA.START_YRMN  ||  '01'   \n";
        query +=" AND  TO_CHAR(ADD_MONTHS(TO_DATE(XA.START_YRMN,  'YYYYMM'),  4),  'YYYYMM')  ||  '31')  <  1  THEN  'N'  ELSE  'Y'  END)  ELSE  ''  END)  AS  VISIT_YN5  ,  (CASE  WHEN  XA.DEMD_MMCNT  >=  6  THEN  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  JOB_GBN  IN  ('V',  'T')   \n";
        query +=" AND  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  TO_CHAR(ADD_MONTHS(TO_DATE(XA.START_YRMN,  'YYYYMM'),  5),  'YYYYMM')  ||  '01'   \n";
        query +=" AND  TO_CHAR(SYSDATE,  'YYYYMMDD'))  <  1  THEN  'N'  ELSE  'Y'  END)  ELSE  ''  END)  AS  VISIT_YN6  ,  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_STOMU_CONTRINFO  WHERE  UPSO_CD  =  XB.UPSO_CD)  >  0  THEN  'Y'  ELSE  'N'  END)  AS  STOMU_YN  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  KAKAO.MZSENDLOG  WHERE  USER_ID  =  XA.UPSO_CD   \n";
        query +=" AND  REQ_DTM  BETWEEN  TO_CHAR(ADD_MONTHS(TO_DATE(:DEMD_YRMN,  'YYYYMM'),  -3),  'YYYYMM')   \n";
        query +=" AND  :DEMD_YRMN   \n";
        query +=" AND  RESERVED6  NOT  IN  ('bra03_r11',  'bra04_s25',  'bra05_s02',  'bra03_s01','HOMP'))  AS  SMS_CNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  KAKAO.MZSENDLOG  WHERE  USER_ID  =  XA.UPSO_CD   \n";
        query +=" AND  REQ_DTM  BETWEEN  XA.START_YRMN  ||  '01000000'   \n";
        query +=" and  XA.END_YRMN  ||  '31235959'   \n";
        query +=" AND  RESERVED6  NOT  IN  ('bra03_r11',  'bra04_s25',  'bra05_s02',  'bra03_s01','HOMP'))  AS  SMS_TOTCNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  XA.START_YRMN  ||  '01'   \n";
        query +=" and  XA.END_YRMN  ||  '31'   \n";
        query +=" AND  JOB_GBN  IN  ('V','T'))  AS  VISIT_TOTCNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  XA.START_YRMN  ||  '01'   \n";
        query +=" and  XA.END_YRMN  ||  '31'   \n";
        query +=" AND  INSPRES_ID  =  XB.STAFF_CD   \n";
        query +=" AND  JOB_GBN  =  'P')  AS  CALL_TOTCNT  FROM  (   \n";
        query +=" SELECT  DEMD_GBN  ,  UPSO_CD  ,  DEMD_YRMN  ,  MONPRNCFEE  ,  MONPRNCFEE2  ,  BSTYP_CD  ,  UPSO_GRAD  ,  DEMD_MMCNT  ,  START_YRMN  ,  END_YRMN  ,  TOT_USE_AMT  ,  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  5)  MONPRNCFEE2  ,  GIBU.FT_SPLIT(DEMDS,  ',',  6)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :DEMD_YRMN,  :DEMD_YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  MAX(DEMD_MMCNT)  AS  DEMD_MMCNT  ,  MIN(START_YRMN)  AS  START_YRMN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  :DEMD_YRMN  ||  '31'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_NEW_ZIP  BETWEEN  NVL(:START_ZIP,  UPSO_NEW_ZIP)   \n";
        query +=" AND  NVL(:END_ZIP,  UPSO_NEW_ZIP)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :DEMD_YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  TO_NUMBER(:START_CNT_MON)   \n";
        query +=" AND  TO_NUMBER(:END_CNT_MON)  UNION  ALL   \n";
        query +=" SELECT  TB.UPSO_CD  ,  TB.OPBI_YRMN  ,  TB.OPBI_CNT  -  COUNT(TA.NOTE_YRMN)  DEMD_MMCNT  ,  :DEMD_YRMN  START_YRMN  FROM  GIBU.TBRA_NOTE  TA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  SUBSTR(A.OPBI_DAY,  1,  6)  OPBI_YRMN  ,  MONTHS_BETWEEN(TO_DATE(:DEMD_YRMN,  'YYYYMM'),  TO_DATE(SUBSTR(A.OPBI_DAY,  1,  6),  'YYYYMM'))  +  1  OPBI_CNT  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_NOTE  B  WHERE  A.UPSO_STAT  =  '1'   \n";
        query +=" AND  (A.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(A.CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  A.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NEW_DAY  <=  :DEMD_YRMN  ||  '31'   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  A.UPSO_NEW_ZIP  BETWEEN  NVL(:START_ZIP,  A.UPSO_NEW_ZIP)   \n";
        query +=" AND  NVL(:END_ZIP,  A.UPSO_NEW_ZIP)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.NOTE_YRMN  =  :DEMD_YRMN  )  TB  WHERE  TA.UPSO_CD  =  TB.UPSO_CD  GROUP  BY  TB.UPSO_CD,  TB.OPBI_YRMN,  TB.OPBI_CNT  HAVING  (TB.OPBI_CNT  -  COUNT(TA.NOTE_YRMN))  BETWEEN  TO_NUMBER(:START_CNT_MON)   \n";
        query +=" AND  TO_NUMBER(:END_CNT_MON)  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XA  ,  GIBU.TBRA_UPSO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(GOSO_YN)  GOSO_YN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(COMPN_DAY,  NULL,  '�����',  DECODE(JUDG_CD,  '2',  '�������',  '4',  '�����',  NULL))  GOSO_YN  FROM  GIBU.TBRA_ACCU  WHERE  (JUDG_CD  NOT  IN  ('1','3','5','6','7','8','9','41','99')   \n";
        query +=" OR  JUDG_CD  IS  NULL)  )  GROUP  BY  UPSO_CD  )  XD  ,  INSA.TCPM_BIPLC  XF  WHERE  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XA.UPSO_GRAD   \n";
        query +=" AND  XC.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  XC.BSTYP_CD,  'A',  XC.BSTYP_CD,  :BSTYP_CD)   \n";
        query +=" AND  XC.BSTYP_CD  =  XA.BSTYP_CD   \n";
        query +=" AND  XD.UPSO_CD  (+)  =  XA.UPSO_CD   \n";
        query +=" AND  XF.GIBU  =  XB.BRAN_CD   \n";
        query +=" AND  XA.DEMD_MMCNT  BETWEEN  TO_NUMBER(:START_CNT_MON)   \n";
        query +=" AND  TO_NUMBER(:END_CNT_MON)  UNION  ALL   \n";
        query +=" SELECT  '2'  AS  GBN  ,  XA.DEMD_MMCNT  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  DECODE(XB.MNGEMSTR_RESINUM  ,  NULL,  '',  'OK')  AS  CHK_RESINUM  ,  XB.MNGEMSTR_NM  ,  DECODE(TRIM(XA.BSTYP_CD),  'o',  GIBU.FT_GET_NOREBANG_GRAD(XB.UPSO_CD),  XC.GRADNM)  AS  GRADNM  ,  DECODE  ((SELECT  NVL(COUNT(UPSO_CD),  0)  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL),  0,  '',  'ä��')  AS  BOND  ,  XA.MONPRNCFEE  ,  XA.MONPRNCFEE2  ,  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO  AS  UPSO_ADDR  ,  XB.UPSO_PHON  ,  XB.MNGEMSTR_HPNUM  ,  XA.TOT_USE_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_USE_AMT  +  XA.TOT_ADDT_AMT  AS  TOT_AMT  ,  DECODE(XB.BEFORE_UPSO_CD,  NULL,  '',  '��'  ||  TO_CHAR(XB.INS_DATE,  'YYYYMMDD'))  ||  '  '  ||  NVL(XD.GOSO_YN,  '')  AS  REMAK  ,  XB.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,   \n";
        query +=" (SELECT  ATTE||'  '||CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =XB.UPSO_BD_MNG_NUM)  AS  SIGUGUN  ,  SUBSTR(XA.START_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.START_YRMN,  5,  2)  AS  START_YRMN  ,  SUBSTR(XA.END_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.END_YRMN,  5,  2)  AS  END_YRMN  ,  XF.PHON_NUM  AS  BRAN_PHON_NUM  ,  DECODE((SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  TERM_YN  =  'N'),0,'','OK')  AS  AUTO_YN  ,  '0'  AS  COL_CHECK  ,   \n";
        query +=" (SELECT  DECODE(COURT_NM,  NULL,  TOWNTWSHP,  COURT_NM)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XB.UPSO_BD_MNG_NUM  )  AS  DONG  ,  XB.CLIENT_NUM  ,  XB.BRAN_CD  ,  XB.STAFF_CD  ,  FIDU.GET_STAFF_NM(XB.STAFF_CD)  AS  STAFF_NM  ,  XA.BSTYP_CD  ,  (CASE  WHEN  XA.DEMD_MMCNT  >=  6  THEN  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  JOB_GBN  IN  ('V',  'T')   \n";
        query +=" AND  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  XA.START_YRMN  ||  '01'   \n";
        query +=" AND  TO_CHAR(ADD_MONTHS(TO_DATE(XA.START_YRMN,  'YYYYMM'),  4),  'YYYYMM')  ||  '31')  <  1  THEN  'N'  ELSE  'Y'  END)  ELSE  ''  END)  AS  VISIT_YN5  ,  (CASE  WHEN  XA.DEMD_MMCNT  >=  6  THEN  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  JOB_GBN  IN  ('V',  'T')   \n";
        query +=" AND  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  TO_CHAR(ADD_MONTHS(TO_DATE(XA.START_YRMN,  'YYYYMM'),  5),  'YYYYMM')  ||  '01'   \n";
        query +=" AND  TO_CHAR(SYSDATE,  'YYYYMMDD'))  <  1  THEN  'N'  ELSE  'Y'  END)  ELSE  ''  END)  AS  VISIT_YN6  ,  (CASE  WHEN   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_STOMU_CONTRINFO  WHERE  UPSO_CD  =  XB.UPSO_CD)  >  0  THEN  'Y'  ELSE  'N'  END)  AS  STOMU_YN  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  KAKAO.MZSENDLOG  WHERE  USER_ID  =  XA.UPSO_CD   \n";
        query +=" AND  REQ_DTM  BETWEEN  TO_CHAR(ADD_MONTHS(TO_DATE(:DEMD_YRMN,  'YYYYMM'),  -3),  'YYYYMM')   \n";
        query +=" AND  :DEMD_YRMN   \n";
        query +=" AND  RESERVED6  NOT  IN  ('bra03_r11',  'bra04_s25',  'bra05_s02',  'bra03_s01','HOMP'))  AS  SMS_CNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  KAKAO.MZSENDLOG  WHERE  USER_ID  =  XA.UPSO_CD   \n";
        query +=" AND  REQ_DTM  BETWEEN  XA.START_YRMN  ||  '01000000'   \n";
        query +=" and  XA.END_YRMN  ||  '31235959'   \n";
        query +=" AND  RESERVED6  NOT  IN  ('bra03_r11',  'bra04_s25',  'bra05_s02',  'bra03_s01','HOMP'))  AS  SMS_TOTCNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  XA.START_YRMN  ||  '01'   \n";
        query +=" and  XA.END_YRMN  ||  '31'   \n";
        query +=" AND  JOB_GBN  IN  ('V','T'))  AS  VISIT_TOTCNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  BETWEEN  XA.START_YRMN  ||  '01'   \n";
        query +=" and  XA.END_YRMN  ||  '31'   \n";
        query +=" AND  INSPRES_ID  =  XB.STAFF_CD   \n";
        query +=" AND  JOB_GBN  =  'P')  AS  CALL_TOTCNT  FROM  (   \n";
        query +=" SELECT  DEMD_GBN  ,  UPSO_CD  ,  DEMD_YRMN  ,  MONPRNCFEE  ,  MONPRNCFEE2  ,  BSTYP_CD  ,  UPSO_GRAD  ,  DEMD_MMCNT  ,  START_YRMN  ,  END_YRMN  ,  TOT_USE_AMT  ,  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  5)  MONPRNCFEE2  ,  GIBU.FT_SPLIT(DEMDS,  ',',  6)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT2(A.UPSO_CD,  A.START_YRMN,  :DEMD_YRMN,  :DEMD_YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  MAX(DEMD_MMCNT)  AS  DEMD_MMCNT  ,  MIN(START_YRMN)  AS  START_YRMN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  OPBI_YRMN  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  YRMN  ,  UPSO_CD  ,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_NEW_ZIP  BETWEEN  NVL(:START_ZIP,  UPSO_NEW_ZIP)   \n";
        query +=" AND  NVL(:END_ZIP,  UPSO_NEW_ZIP)  )  B  WHERE  YRMN  NOT  IN  (   \n";
        query +=" SELECT  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  B.UPSO_CD  )   \n";
        query +=" AND  YRMN  BETWEEN  B.OPBI_YRMN   \n";
        query +=" AND  :DEMD_YRMN  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  HAVING  COUNT(YRMN)  BETWEEN  TO_NUMBER(:START_CNT_MON)   \n";
        query +=" AND  TO_NUMBER(:END_CNT_MON)  UNION  ALL   \n";
        query +=" SELECT  TB.UPSO_CD  ,  TB.OPBI_YRMN  ,  TB.OPBI_CNT  -  COUNT(TA.NOTE_YRMN)  DEMD_MMCNT  ,  :DEMD_YRMN  START_YRMN  FROM  GIBU.TBRA_NOTE  TA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  SUBSTR(A.OPBI_DAY,  1,  6)  OPBI_YRMN  ,  MONTHS_BETWEEN(TO_DATE(:DEMD_YRMN,  'YYYYMM'),  TO_DATE(SUBSTR(A.OPBI_DAY,  1,  6),  'YYYYMM'))  +  1  OPBI_CNT  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_NOTE  B  WHERE  (A.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(A.CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  A.NEW_DAY  IS  NULL   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  A.UPSO_NEW_ZIP  BETWEEN  NVL(:START_ZIP,  A.UPSO_NEW_ZIP)   \n";
        query +=" AND  NVL(:END_ZIP,  A.UPSO_NEW_ZIP)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.NOTE_YRMN  =  :DEMD_YRMN  )  TB  WHERE  TA.UPSO_CD  =  TB.UPSO_CD  GROUP  BY  TB.UPSO_CD,  TB.OPBI_YRMN,  TB.OPBI_CNT  HAVING  (TB.OPBI_CNT  -  COUNT(TA.NOTE_YRMN))  BETWEEN  TO_NUMBER(:START_CNT_MON)   \n";
        query +=" AND  TO_NUMBER(:END_CNT_MON)  )  GROUP  BY  UPSO_CD,  OPBI_YRMN  )  A  )  )  )  XA  ,  GIBU.TBRA_UPSO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(GOSO_YN)  GOSO_YN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(COMPN_DAY,  NULL,  '�����',  DECODE(JUDG_CD,  '2',  '�������',  '4',  '�����',  NULL))  GOSO_YN  FROM  GIBU.TBRA_ACCU  WHERE  (JUDG_CD  NOT  IN  ('1','3','5','6','7','8','9','41','99')   \n";
        query +=" OR  JUDG_CD  IS  NULL)  )  GROUP  BY  UPSO_CD  )  XD  ,  INSA.TCPM_BIPLC  XF  WHERE  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XA.UPSO_GRAD   \n";
        query +=" AND  XC.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  XC.BSTYP_CD,  'A',  XC.BSTYP_CD,  :BSTYP_CD)   \n";
        query +=" AND  XC.BSTYP_CD  =  XA.BSTYP_CD   \n";
        query +=" AND  XD.UPSO_CD  (+)  =  XA.UPSO_CD   \n";
        query +=" AND  XF.GIBU  =  XB.BRAN_CD   \n";
        query +=" AND  XA.DEMD_MMCNT  BETWEEN  TO_NUMBER(:START_CNT_MON)   \n";
        query +=" AND  TO_NUMBER(:END_CNT_MON) ";
        sobj.setSql(query);
        sobj.setDouble("START_ZIP", START_ZIP);               //���� �����ȣ
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //���� ����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setDouble("END_ZIP", END_ZIP);               //���� �����ȣ
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //���� ����
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ����
    // ��Ȳ������ ��ȸ������� ������ ������ �ٲ�� ��Ȳ����Ÿ�� �ٲ�����Ƿ� ������� ���� ����Ÿ�� �� 1ȸ ������ �д�.
    public DOBJ CALLsave_nonpy_history_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("SEL1");           //�¿�������Ÿ¡���ݾ���Ȳ���� ������Ų OBJECT�Դϴ�.(CALLsave_nonpy_history_SEL1)
        dvobj.Println("INS2");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_nonpy_history_INS2(dobj, dvobj);
            qexe.DispSelectSql(sobj);
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
    private SQLObject SQLsave_nonpy_history_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //���� �����ȣ
        double   TOT_AMT = dvobj.getRecord().getDouble("TOT_AMT");   //�� �ݾ�
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   UPSO_ADDR = dvobj.getRecord().get("UPSO_ADDR");   //���� �ּ�
        String   STOMU_YN = dvobj.getRecord().get("STOMU_YN");
        String   CHK_RESINUM = dvobj.getRecord().get("CHK_RESINUM");
        double   VISIT_TOTCNT = dvobj.getRecord().getDouble("VISIT_TOTCNT");   //����湮�����Ǽ�
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //���۳��
        String   GRADNM = dvobj.getRecord().get("GRADNM");   //��޸�
        String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //�濵�� �̸�
        String   STAFF_NM = dvobj.getRecord().get("STAFF_NM");   //��� ��
        String   AUTO_YN = dvobj.getRecord().get("AUTO_YN");   //�ڵ�ó������
        String   SMS_CNT = dvobj.getRecord().get("SMS_CNT");
        String   SIGUGUN = dvobj.getRecord().get("SIGUGUN");
        String   VISIT_YN5 = dvobj.getRecord().get("VISIT_YN5");
        String   VISIT_YN6 = dvobj.getRecord().get("VISIT_YN6");
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   UPSO_NM = dvobj.getRecord().get("UPSO_NM");   //���� ��
        String   CLIENT_NUM = dvobj.getRecord().get("CLIENT_NUM");   //�� ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   TOT_USE_AMT = dvobj.getRecord().getDouble("TOT_USE_AMT");   //�� ��� �ݾ�
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //�� ���� �ݾ�
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        double   MONPRNCFEE2 = dvobj.getRecord().getDouble("MONPRNCFEE2");   //���ؿ�����
        double   CALL_TOTCNT = dvobj.getRecord().getDouble("CALL_TOTCNT");   //��ȭ�����Ǽ�
        double   SMS_TOTCNT = dvobj.getRecord().getDouble("SMS_TOTCNT");   //���ڹ߼۴����Ǽ�
        String   UPSO_PHON = dvobj.getRecord().get("UPSO_PHON");   //���� ��ȭ
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   COL_CHECK = dvobj.getRecord().get("COL_CHECK");
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //������
        String   BOND = dvobj.getRecord().get("BOND");
        double   DEMD_MMCNT = dvobj.getRecord().getDouble("DEMD_MMCNT");   //û��������
        String   GBN = dvobj.getRecord().get("GBN");   //����
        String   DONG = dvobj.getRecord().get("DONG");   //��
        String   MNGEMSTR_HPNUM = dvobj.getRecord().get("MNGEMSTR_HPNUM");   //�濵�� �ڵ�����ȣ
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_NONPY_PRCON (MNGEMSTR_HPNUM, DONG, GBN, DEMD_MMCNT, BOND, MONPRNCFEE, COL_CHECK, BSTYP_CD, UPSO_PHON, SMS_TOTCNT, CALL_TOTCNT, MONPRNCFEE2, STAFF_CD, TOT_ADDT_AMT, TOT_USE_AMT, UPSO_CD, PRCON_YRMN, CLIENT_NUM, UPSO_NM, END_YRMN, REMAK, VISIT_YN6, VISIT_YN5, SIGUGUN, SMS_CNT, AUTO_YN, STAFF_NM, MNGEMSTR_NM, GRADNM, START_YRMN, VISIT_TOTCNT, CHK_RESINUM, STOMU_YN, UPSO_ADDR, BRAN_CD, TOT_AMT, UPSO_ZIP)  \n";
        query +=" values(:MNGEMSTR_HPNUM , :DONG , :GBN , :DEMD_MMCNT , :BOND , :MONPRNCFEE , :COL_CHECK , :BSTYP_CD , :UPSO_PHON , :SMS_TOTCNT , :CALL_TOTCNT , :MONPRNCFEE2 , :STAFF_CD , :TOT_ADDT_AMT , :TOT_USE_AMT , :UPSO_CD , :PRCON_YRMN , :CLIENT_NUM , :UPSO_NM , :END_YRMN , :REMAK , :VISIT_YN6 , :VISIT_YN5 , :SIGUGUN , :SMS_CNT , :AUTO_YN , :STAFF_NM , :MNGEMSTR_NM , :GRADNM , :START_YRMN , :VISIT_TOTCNT , :CHK_RESINUM , :STOMU_YN , :UPSO_ADDR , :BRAN_CD , :TOT_AMT , :UPSO_ZIP )";
        sobj.setSql(query);
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
        sobj.setDouble("TOT_AMT", TOT_AMT);               //�� �ݾ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("UPSO_ADDR", UPSO_ADDR);               //���� �ּ�
        sobj.setString("STOMU_YN", STOMU_YN);
        sobj.setString("CHK_RESINUM", CHK_RESINUM);
        sobj.setDouble("VISIT_TOTCNT", VISIT_TOTCNT);               //����湮�����Ǽ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("GRADNM", GRADNM);               //��޸�
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //�濵�� �̸�
        sobj.setString("STAFF_NM", STAFF_NM);               //��� ��
        sobj.setString("AUTO_YN", AUTO_YN);               //�ڵ�ó������
        sobj.setString("SMS_CNT", SMS_CNT);
        sobj.setString("SIGUGUN", SIGUGUN);
        sobj.setString("VISIT_YN5", VISIT_YN5);
        sobj.setString("VISIT_YN6", VISIT_YN6);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
        sobj.setString("CLIENT_NUM", CLIENT_NUM);               //�� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("TOT_USE_AMT", TOT_USE_AMT);               //�� ��� �ݾ�
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //�� ���� �ݾ�
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setDouble("MONPRNCFEE2", MONPRNCFEE2);               //���ؿ�����
        sobj.setDouble("CALL_TOTCNT", CALL_TOTCNT);               //��ȭ�����Ǽ�
        sobj.setDouble("SMS_TOTCNT", SMS_TOTCNT);               //���ڹ߼۴����Ǽ�
        sobj.setString("UPSO_PHON", UPSO_PHON);               //���� ��ȭ
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("COL_CHECK", COL_CHECK);
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
        sobj.setString("BOND", BOND);
        sobj.setDouble("DEMD_MMCNT", DEMD_MMCNT);               //û��������
        sobj.setString("GBN", GBN);               //����
        sobj.setString("DONG", DONG);               //��
        sobj.setString("MNGEMSTR_HPNUM", MNGEMSTR_HPNUM);               //�濵�� �ڵ�����ȣ
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    //##**$$save_nonpy_history
    //##**$$end
}
