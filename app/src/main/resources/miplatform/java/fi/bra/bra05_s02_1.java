
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_s02_1
{
    public bra05_s02_1()
    {
    }
    //##**$$bpap_list
    /* * ���α׷��� : bra05_s02
    * �ۼ��� : ������
    * �ۼ��� : 2009/10/08
    * ���� :
    ��¡���������� ���� �ְ� ��� ���Ҹ� ��ȸ�Ѵ�.
    GBN�� �´� ������ �����Ѵ�.
    GBN=1: �Ϲ��ְ�
    GBN=2: �����ְ�
    GBN=3: �ְ�߼�LIST
    1.NLL : ���μ������������� S ���� �ٷ� �б�(���Ǵޱ�)�� �������� �ʱ� ������ ���ǿ� ���� �бⰡ���ϵ��� ����
    3.SEL.SEL1 / 6.SEL.SEL2 : ������ �����̸�(BANK_NM / BANK), ���¹�ȣ(ACCT_NO)�� ���� CS���� ������ �� �״�� ����ϱ�� ��
    [�׽�Ʈ ����]
    1. �Ϲ��ְ���� : E(��õ)/20090330
    3. �ְ� ��¸���Ʈ �׽�Ʈ : �Ϲ��ְ� ��� ���ǰ� ����
    2.�����ְ� ��� �׽�Ʈ
    A(����)/20060912
    N(�λ�)/20090310
    * ������1: 2010/02/17
    * ������ :
    * �������� :
    1. CS�� ������� ���̾ƿ��� SI�� ������� ���̾ƿ��� �޶� ��� ����Ÿ ���� �� ������ CS���ؿ� ����
    (���డ���� ���� ����� ��ȸ������ CS(dw_gibu706 ����) ���ǰ� ����)
    1) ��ȸ��� �÷� �߰�
    DISP_DAY : �߼�����
    PAY_DAY  : ��������
    2) ���μ����������� �÷��� �÷����̿� ' '�� ������ �������Ÿ�� Ȯ������ ��� ��ĭ�� �ƴ϶�
    ��ĭ�� ������� ������ �ּҶ��� ' '��� :BLANK��� �Ķ���͸� �־ ��ĭ�� ������� �ذ���.
    2. ������� ���� ���� SELECT�� �������ڸ� �������� ���������� QUERY �ӵ��� �ذ��ϰ��� ���� �ֱ� ��ϵ� ���� ������ �������°ɷ� ����
    */
    public DOBJ CTLbpap_list(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
            {
                dobj  = CALLbpap_list_SEL1(Conn, dobj);           //  �ְ����
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("3"))
            {
                dobj  = CALLbpap_list_SEL3(Conn, dobj);           //  �ְ�߼�LIST
            }
            else
            {
                dobj  = CALLbpap_list_SEL2(Conn, dobj);           //  �����ְ����
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
    public DOBJ CTLbpap_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
        {
            dobj  = CALLbpap_list_SEL1(Conn, dobj);           //  �ְ����
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("3"))
        {
            dobj  = CALLbpap_list_SEL3(Conn, dobj);           //  �ְ�߼�LIST
        }
        else
        {
            dobj  = CALLbpap_list_SEL2(Conn, dobj);           //  �����ְ����
        }
        return dobj;
    }
    // �ְ����
    public DOBJ CALLbpap_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbpap_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("START_CNT_MON");   //���� ����
        String   TO_ZIP = dobj.getRetObject("S").getRecord().get("TO_ZIP");   //�����ȣ �˻� TO
        String   BLANK =" ";   //BLANK
        String   FROM_ZIP = dobj.getRetObject("S").getRecord().get("FROM_ZIP");   //�����ȣ �˻� FROM
        double   END_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("END_CNT_MON");   //���� ����
        String   DISP_DAY = dobj.getRetObject("S").getRecord().get("DISP_DAY");   //�߼� ����
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   PAY_DAY = dobj.getRetObject("S").getRecord().get("PAY_DAY");   //���� ����
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  BRAN_CD  ,  BRAN_NM  ,  UPSO_CD  ,  UPSO_NM  ,  RECV_NM  ,  RECV_ZIP  ,  RECV_ADDR  ,  MONPRNCFEE  ,  START_YRMN  ,  END_YRMN  ,  NONPY_AMT  ,  TOT_ADDT_AMT  ,  TOT_DEMD_AMT  ,  DSCT_AMT  ,  BRAN_ADDR  ,  BRAN_ZIP  ,   \n";
        query +=" (SELECT  IPPBX_INPHONE_NUM  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  XA.STAFF_CD)  AS  BRAN_PHON  ,  DISP_DAY  ,  PAY_DAY  ,   \n";
        query +=" (SELECT  ACCOUNT_NM  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  BANK_CODE  =  '0'  ||   \n";
        query +=" (SELECT  BANK_CD  FROM  GIBU.TFMS_BANK  WHERE  BRAN_CD  IS  NULL   \n";
        query +=" AND  REMAK  =  'Y'))  AS  REPTPRES  ,   \n";
        query +=" (SELECT  LISTAGG((SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  BANK_CD  =  BANK_CODE)  ||  '  :  '  ||  GIBU.FT_GET_ACCOUNT_FORMAT(VIRTUAL_ACCOUNT_NUM),  CHR(13)  ||  CHR(10))  WITHIN  GROUP(ORDER  BY  BANK_CODE)  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y')  AS  VIRTUAL_ACCN  ,  BIPLC_SNM  AS  DEPT_NM  ,  CP_NUM  FROM  (   \n";
        query +=" SELECT  TA.BRAN_CD  ,  MAX(TC.BIPLC_NM)  AS  BRAN_NM  ,  TA.UPSO_CD  ,  MAX(DECODE(TA.MAIL_RCPT,  'U',  TA.UPSO_NM,  ''))  AS  UPSO_NM  ,  MAX(DECODE(TA.PAYPRES_GBN,  'B',  TA.MNGEMSTR_NM,  TA.PERMMSTR_NM))  AS  RECV_NM  ,  MAX(REGEXP_REPLACE(DECODE(TA.PAYPRES_GBN,  'B',  TA.MNGEMSTR_HPNUM,  NVL(TA.PERMMSTR_HPNUM,  TA.MNGEMSTR_HPNUM)),  '[^0-9]',  ''))  AS  CP_NUM  ,  MAX(DECODE(TA.MAIL_RCPT,  'U',  TA.UPSO_NEW_ZIP,  'B',  TA.MNGEMSTR_NEW_ZIP,  TA.PERMMSTR_NEW_ZIP))  AS  RECV_ZIP  ,  MAX(DECODE(TA.MAIL_RCPT,  'U',  TA.UPSO_NEW_ADDR1  ||  DECODE(TA.UPSO_NEW_ADDR2,  '',  '',  ',  '  ||  TA.UPSO_NEW_ADDR2)  ||  TA.UPSO_REF_INFO  ,  'B',  TA.MNGEMSTR_NEW_ADDR1  ||  DECODE(TA.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  TA.MNGEMSTR_NEW_ADDR2)  ||  TA.MNGEMSTR_REF_INFO  ,  TA.PERMMSTR_NEW_ADDR1  ||  DECODE(TA.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  TA.PERMMSTR_NEW_ADDR2)  ||  TA.PERMMSTR_REF_INFO))  AS  RECV_ADDR  ,  MAX(TD.MONPRNCFEE)  AS  MONPRNCFEE  ,  MIN(TB.START_YRMN)  AS  START_YRMN  ,  MAX(TB.END_YRMN)  AS  END_YRMN  ,  MAX(TB.TOT_DEMD_AMT  -  TB.TOT_ADDT_AMT  -  TB.TOT_EADDT_AMT)  AS  NONPY_AMT  ,  MAX(TB.TOT_ADDT_AMT  +  TB.TOT_EADDT_AMT)  AS  TOT_ADDT_AMT  ,  MAX(TB.TOT_DEMD_AMT)  AS  TOT_DEMD_AMT  ,  MAX(TB.DSCT_AMT)  AS  DSCT_AMT  ,  MAX(TC.ADDR  ||  :BLANK  ||  TC.HNM)  AS  BRAN_ADDR  ,  MAX(TC.POST_NUM)  AS  BRAN_ZIP  ,  MAX(TC.PHON_NUM)  AS  BRAN_PHON  ,  :DISP_DAY  AS  DISP_DAY  ,  :PAY_DAY  AS  PAY_DAY  ,  TC.BIPLC_SNM  ,  TA.STAFF_CD  FROM  GIBU.TBRA_UPSO  TA  ,  GIBU.TBRA_DEMD_OCR  TB  ,  INSA.TCPM_BIPLC  TC  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  ZB.BSTYP_CD  IN  ('k',  'l',  'o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NOT  NULL  THEN  ZB.MONPRNCFEE2  +  NVL  (ZD.MONPRNCFEE,  0)  WHEN  ZB.BSTYP_CD  IN  ('k',  'l',  'o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NULL  THEN  ZB.MONPRNCFEE2  WHEN  ZB.BSTYP_CD  NOT  IN  ('k',  'l',  'o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NOT  NULL  THEN  ZB.MONPRNCFEE  +  NVL  (ZD.MONPRNCFEE,  0)  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  ,  GIBU.TBRA_BSCON_CONTRINFO  ZD  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD   \n";
        query +=" AND  ZB.UPSO_CD  =  ZD.UPSO_CD(+)  )  TD  WHERE  TA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  TA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TA.UPSO_NEW_ZIP  BETWEEN  NVL(:FROM_ZIP,  '000000')   \n";
        query +=" AND  NVL(:TO_ZIP,  '999999')   \n";
        query +=" AND  TA.OPBI_DAY  IS  NOT  NULL   \n";
        query +=" AND  TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.PAPER_CANC_YN  <>  'Y'   \n";
        query +=" AND  TA.UPSO_CD  NOT  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_AUTO  WHERE  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  DEMD_YRMN  =  SUBSTR(:DISP_DAY,  1,  6))   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TB.BRAN_CD  =  TA.BRAN_CD   \n";
        query +=" AND  TB.DEMD_YRMN  =  SUBSTR(:DISP_DAY,  1,  6)   \n";
        query +=" AND  TC.GIBU  =  TA.BRAN_CD   \n";
        query +=" AND  TD.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TB.DEMD_MMCNT  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON   \n";
        query +=" AND  TB.DEMD_GBN  =  '32'   \n";
        query +=" AND  TB.OCR_PRNT  IS  NULL   \n";
        query +=" AND  TB.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  TB.BSTYP_CD,  :BSTYP_CD)  GROUP  BY  TA.BRAN_CD,  TA.UPSO_CD,  TC.BIPLC_SNM,  TA.STAFF_CD  UNION  ALL   \n";
        query +=" SELECT  TA.BRAN_CD  ,  MAX(TC.BIPLC_NM)  AS  BRAN_NM  ,  TA.UPSO_CD  ,  MAX(DECODE(TA.MAIL_RCPT,  'U',  TA.UPSO_NM,  ''))  AS  UPSO_NM  ,  MAX(DECODE(TA.PAYPRES_GBN,  'B',  TA.MNGEMSTR_NM,  TA.PERMMSTR_NM))  AS  RECV_NM  ,  MAX(REGEXP_REPLACE(DECODE(TA.PAYPRES_GBN,  'B',  TA.MNGEMSTR_HPNUM,  NVL(TA.PERMMSTR_HPNUM,  TA.MNGEMSTR_HPNUM)),  '[^0-9]',  ''))  AS  CP_NUM  ,  MAX(DECODE(TA.MAIL_RCPT,  'U',  TA.UPSO_NEW_ZIP,  'B',  TA.MNGEMSTR_NEW_ZIP,  TA.PERMMSTR_NEW_ZIP))  AS  RECV_ZIP  ,  MAX(DECODE(TA.MAIL_RCPT,  'U',  TA.UPSO_NEW_ADDR1  ||  DECODE(TA.UPSO_NEW_ADDR2,  '',  '',  ',  '  ||  TA.UPSO_NEW_ADDR2)  ||  TA.UPSO_REF_INFO  ,  'B',  TA.MNGEMSTR_NEW_ADDR1  ||  DECODE(TA.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  TA.MNGEMSTR_NEW_ADDR2)  ||  TA.MNGEMSTR_REF_INFO  ,  TA.PERMMSTR_NEW_ADDR1  ||  DECODE(TA.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  TA.PERMMSTR_NEW_ADDR2)  ||  TA.PERMMSTR_REF_INFO))  AS  RECV_ADDR  ,  MAX(TD.MONPRNCFEE)  AS  MONPRNCFEE  ,  MIN(TB.START_YRMN)  AS  START_YRMN  ,  MAX(TB.END_YRMN)  AS  END_YRMN  ,  MAX(TB.TOT_DEMD_AMT  -  TB.TOT_ADDT_AMT  -  TB.TOT_EADDT_AMT)  AS  NONPY_AMT  ,  MAX(TB.TOT_ADDT_AMT  +  TB.TOT_EADDT_AMT)  AS  TOT_ADDT_AMT  ,  MAX(TB.TOT_DEMD_AMT)  AS  TOT_DEMD_AMT  ,  MAX(TB.DSCT_AMT)  AS  DSCT_AMT  ,  MAX(TC.ADDR  ||  :BLANK  ||  TC.HNM)  AS  BRAN_ADDR  ,  MAX(TC.POST_NUM)  AS  BRAN_ZIP  ,  MAX(TC.PHON_NUM)  AS  BRAN_PHON  ,  :DISP_DAY  AS  DISP_DAY  ,  :PAY_DAY  AS  PAY_DAY  ,  TC.BIPLC_SNM  ,  TA.STAFF_CD  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  UPSO_CD  ,  UPSO_NM  ,  BEFORE_UPSO_CD  ,  MCHNDAESU  ,  UPSO_NEW_ZIP  ,  UPSO_NEW_ADDR1  ,  UPSO_NEW_ADDR2  ,  UPSO_REF_INFO  ,  MNGEMSTR_HPNUM  ,  MNGEMSTR_NEW_ZIP  ,  MNGEMSTR_NEW_ADDR1  ,  MNGEMSTR_NEW_ADDR2  ,  MNGEMSTR_REF_INFO  ,  PERMMSTR_HPNUM  ,  PERMMSTR_NEW_ZIP  ,  PERMMSTR_NEW_ADDR1  ,  PERMMSTR_NEW_ADDR2  ,  PERMMSTR_REF_INFO  ,  CLIENT_NUM  ,  MNGEMSTR_NM  ,  PERMMSTR_NM  ,  MAIL_RCPT  ,  PAYPRES_GBN  ,  MONTHS_BETWEEN(TO_DATE(SUBSTR(:DISP_DAY,  1,  6),  'YYYYMM'),  TO_DATE(TO_CHAR(INS_DATE,  'YYYYMM'),  'YYYYMM'))  AS  INS_MMCNT  ,  STAFF_CD  FROM  GIBU.TBRA_UPSO  WHERE  NEW_DAY  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  UPSO_NEW_ZIP  BETWEEN  NVL(:FROM_ZIP,  '000000')   \n";
        query +=" AND  NVL(:TO_ZIP,  '999999')   \n";
        query +=" AND  OPBI_DAY  IS  NOT  NULL   \n";
        query +=" AND  CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  PAPER_CANC_YN  <>  'Y'  )  TA  ,  GIBU.TBRA_DEMD_OCR  TB  ,  INSA.TCPM_BIPLC  TC  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  ZB.BSTYP_CD  IN  ('k',  'l',  'o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NOT  NULL  THEN  ZB.MONPRNCFEE2  +  NVL  (ZD.MONPRNCFEE,  0)  WHEN  ZB.BSTYP_CD  IN  ('k',  'l',  'o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NULL  THEN  ZB.MONPRNCFEE2  WHEN  ZB.BSTYP_CD  NOT  IN  ('k',  'l',  'o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NOT  NULL  THEN  ZB.MONPRNCFEE  +  NVL  (ZD.MONPRNCFEE,  0)  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX  (A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  ,  GIBU.TBRA_BSCON_CONTRINFO  ZD  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD   \n";
        query +=" AND  ZB.UPSO_CD  =  ZD.UPSO_CD(+)  )  TD  WHERE  TA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  TA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TD.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TC.GIBU  =  TA.BRAN_CD   \n";
        query +=" AND  TA.INS_MMCNT  =  3   \n";
        query +=" AND  TA.UPSO_CD  NOT  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_AUTO  WHERE  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  DEMD_YRMN  =  SUBSTR(:DISP_DAY,  1,  6))   \n";
        query +=" AND  TB.DEMD_GBN  =  '32'   \n";
        query +=" AND  TB.OCR_PRNT  IS  NULL   \n";
        query +=" AND  TB.DEMD_YRMN  =  SUBSTR(:DISP_DAY,  1,  6)   \n";
        query +=" AND  TB.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  TB.BSTYP_CD,  :BSTYP_CD)  GROUP  BY  TA.BRAN_CD,  TA.UPSO_CD,  TC.BIPLC_SNM,  TA.STAFF_CD)  XA  WHERE  TOT_ADDT_AMT  <>  0   \n";
        query +=" AND  TOT_DEMD_AMT  <>  0  ORDER  BY  UPSO_CD ";
        sobj.setSql(query);
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //���� ����
        sobj.setString("TO_ZIP", TO_ZIP);               //�����ȣ �˻� TO
        sobj.setString("BLANK", BLANK);               //BLANK
        sobj.setString("FROM_ZIP", FROM_ZIP);               //�����ȣ �˻� FROM
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //���� ����
        sobj.setString("DISP_DAY", DISP_DAY);               //�߼� ����
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("PAY_DAY", PAY_DAY);               //���� ����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �ְ�߼�LIST
    // ���� ��ȸ����: ���۳���� �ٲ� ��ȸ����: ���۳�� ============================> �� ���� ����ϰ� ��ȸ�ϱ�� ����. ���� �ش���� �������� �ְ� LIST�� �˻��ϴ°ɷ� ���� : 2009.12.10
    public DOBJ CALLbpap_list_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbpap_list_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_list_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BPAP_DAY = dobj.getRetObject("S").getRecord().get("DISP_DAY");   //�ְ� ����
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XB.UPSO_NM  ,  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO  AS  ADDR  ,  XB.MNGEMSTR_NM  ,  GIBU.FT_GET_BSTYP_NM(GIBU.FT_GET_BSTYP_INFO(XA.UPSO_CD))  AS  GRADNM  ,  XB.UPSO_PHON  ,  DECODE(XA.START_YRMN,  NULL,  '',  XA.START_YRMN)  AS  START_YRMN  ,  DECODE(XA.END_YRMN,  NULL,  '',  XA.END_YRMN)  AS  END_YRMN  ,  XA.TOT_DEMD_AMT  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  XA  ,  GIBU.TBRA_UPSO  XB  WHERE  XB.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  XB.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.BPAP_DAY  LIKE  SUBSTR(:BPAP_DAY,1,6)||'%'   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �����ְ����
    public DOBJ CALLbpap_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbpap_list_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_list_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DISP_DAY = dobj.getRetObject("S").getRecord().get("DISP_DAY");   //�߼� ����
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XB.UPSO_NM  ,  DECODE(XB.PAYPRES_GBN,  'B',  XB.MNGEMSTR_NM,  XB.PERMMSTR_NM)  AS  RECV_NM  ,  DECODE(XB.MAIL_RCPT,  'U',  XB.UPSO_NEW_ZIP,  'B',  XB.MNGEMSTR_NEW_ZIP,  XB.PERMMSTR_NEW_ZIP)  AS  RECV_ZIP  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XD.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  -  (XA.TOT_ADDT_AMT  +  XA.TOT_EADDT_AMT)  AS  NONPY_AMT  ,  XA.TOT_ADDT_AMT  +  XA.TOT_EADDT_AMT  AS  TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.TOT_DEMD_AMT  ,  XC.BIPLC_NM  AS  BRAN_NM  ,  XC.ADDR  ||  '  '  ||  XC.HNM  AS  BRAN_ADDR  ,  XC.POST_NUM  AS  BRAN_ZIP  ,   \n";
        query +=" (SELECT  IPPBX_INPHONE_NUM  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  XB.STAFF_CD)  AS  BRAN_PHON_NUM  ,  DECODE(XB.MAIL_RCPT,  'U',  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '  ||  XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO  ,  'B',  XB.MNGEMSTR_NEW_ADDR1  ||  DECODE(XB.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  XB.MNGEMSTR_NEW_ADDR2)  ||  XB.MNGEMSTR_REF_INFO  ,  XB.PERMMSTR_NEW_ADDR1  ||  DECODE(XB.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  XB.PERMMSTR_NEW_ADDR2)  ||  XB.PERMMSTR_REF_INFO)  AS  RECV_ADDR  ,  DECODE(XB.BRAN_CD,  'A',  '��������',  'B',  '��������',  'C',  '��������',  'E',  '��������',  'F',  '��������',  'G',  '����',  'H',  '����',  'I',  '����',  'J',  '����',  'K',  '����',  'L',  '��������',  'M',  '����',  'N',  '�λ�����',  'O',  '����')  AS  BANK  ,  DECODE(XB.BRAN_CD,  'A',  '695037-01-001228',  'B',  '695037-01-001257',  'C',  '695037-01-001231',  'E',  '695037-01-001260',  'F',  '695037-01-001244',  'G',  '209-01-581021',  'H',  '311-01-155951',  'I',  '311-01-155951',  'J',  '511-01-073417',  'K',  '661-01-033882',  'L',  '632-01-0046-816',  'M',  '815135-51-018283',  'N',  '131-01-000342-2',  'O',  '901017-51-011928')  AS  ACCT_NO  ,  XA.BPAP_DAY  ,  '1'  AS  PRINT_YN  FROM  GIBU.TBRA_BPAP_MNG  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_BIPLC  XC  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(ZA.UPSO_CD,  SUBSTR(:DISP_DAY,  1,  6))  >  0)  THEN  ZB.MONPRNCFEE2  WHEN  (GIBU.FT_GET_IS_BSCON(ZA.UPSO_CD,  SUBSTR(:DISP_DAY,  1,  6))  =  0)   \n";
        query +=" AND  ZD.UPSO_CD  IS  NOT  NULL  THEN  ZB.MONPRNCFEE  +  NVL(ZD.MONPRNCFEE,  0)  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  AS  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD)  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  ,  GIBU.TBRA_BSCON_CONTRINFO  ZD  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD   \n";
        query +=" AND  ZB.UPSO_CD  =  ZD.UPSO_CD(+)   \n";
        query +=" AND  ZB.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  ZB.BSTYP_CD,  :BSTYP_CD)  )  XD  WHERE  XB.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  XB.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XA.BPAP_GBN  =  '2'   \n";
        query +=" AND  XA.BPAP_DAY  =  :DISP_DAY   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XD.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XC.GIBU(+)  =  XB.BRAN_CD  ORDER  BY  XA.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("DISP_DAY", DISP_DAY);               //�߼� ����
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$bpap_list
    //##**$$end
}
