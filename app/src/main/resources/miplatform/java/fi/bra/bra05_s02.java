
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_s02
{
    public bra05_s02()
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
        query +=" SELECT  DISTINCT  BRAN_CD  ,  BRAN_NM  ,  UPSO_CD  ,  UPSO_NM  ,  RECV_NM  ,  RECV_ZIP  ,  RECV_ADDR  ,  MONPRNCFEE  ,  START_YRMN  ,  END_YRMN  ,  NONPY_AMT  ,  TOT_ADDT_AMT  ,  TOT_DEMD_AMT  ,  BRAN_ADDR  ,  BRAN_ZIP  ,  REGEXP_REPLACE(BRAN_PHON,  '[^0-9]',  '')  AS  BRAN_PHON  ,  DISP_DAY  ,  PAY_DAY  ,   \n";
        query +=" (SELECT  ACCOUNT_NM  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  BANK_CODE  =  '0'  ||   \n";
        query +=" (SELECT  BANK_CD  FROM  GIBU.TFMS_BANK  WHERE  BRAN_CD  IS  NULL   \n";
        query +=" AND  REMAK  =  'Y'))  AS  REPTPRES  ,   \n";
        query +=" (SELECT  LISTAGG((SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  BANK_CD  =  BANK_CODE)  ||  '  :  '  ||  GIBU.FT_GET_ACCOUNT_FORMAT(VIRTUAL_ACCOUNT_NUM),  CHR(13)  ||  CHR(10))  WITHIN  GROUP(ORDER  BY  1)  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y')  AS  VIRTUAL_ACCN  ,  BIPLC_SNM  AS  DEPT_NM  ,  CP_NUM  FROM  (   \n";
        query +=" SELECT  TA.BRAN_CD  ,  MAX(TC.BIPLC_NM)  AS  BRAN_NM  ,  TA.UPSO_CD  ,  MAX(DECODE(TA.MAIL_RCPT,  'U',  TA.UPSO_NM,  ''))  AS  UPSO_NM  ,  MAX(DECODE(TA.PAYPRES_GBN,  'B',  TA.MNGEMSTR_NM,  TA.PERMMSTR_NM))  AS  RECV_NM  ,  MAX(REGEXP_REPLACE(DECODE(TA.PAYPRES_GBN,  'B',  TA.MNGEMSTR_HPNUM,  NVL(TA.PERMMSTR_HPNUM,  TA.MNGEMSTR_HPNUM)),  '[^0-9]',  ''))  AS  CP_NUM  ,  MAX(DECODE(TA.MAIL_RCPT,  'U',  TA.UPSO_NEW_ZIP,  'B',  TA.MNGEMSTR_NEW_ZIP,  TA.PERMMSTR_NEW_ZIP))  AS  RECV_ZIP  ,  MAX(DECODE(TA.MAIL_RCPT,  'U',  TA.UPSO_NEW_ADDR1  ||  DECODE(TA.UPSO_NEW_ADDR2,  '',  '',  ',  '  ||  TA.UPSO_NEW_ADDR2)  ||  TA.UPSO_REF_INFO  ,  'B',  TA.MNGEMSTR_NEW_ADDR1  ||  DECODE(TA.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  TA.MNGEMSTR_NEW_ADDR2)  ||  TA.MNGEMSTR_REF_INFO  ,  TA.PERMMSTR_NEW_ADDR1  ||  DECODE(TA.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  TA.PERMMSTR_NEW_ADDR2)  ||  TA.PERMMSTR_REF_INFO))  AS  RECV_ADDR  ,  MAX(TD.MONPRNCFEE)  AS  MONPRNCFEE  ,  MIN(TB.START_YRMN)  AS  START_YRMN  ,  MAX(TB.END_YRMN)  AS  END_YRMN  ,  MAX(TB.TOT_DEMD_AMT  -  TB.TOT_ADDT_AMT  -  TB.TOT_EADDT_AMT)  AS  NONPY_AMT  ,  MAX(TB.TOT_ADDT_AMT  +  TB.TOT_EADDT_AMT)  AS  TOT_ADDT_AMT  ,  MAX(TB.TOT_DEMD_AMT)  AS  TOT_DEMD_AMT  ,  MAX(TC.ADDR  ||  :BLANK  ||  TC.HNM)  AS  BRAN_ADDR  ,  MAX(TC.POST_NUM)  AS  BRAN_ZIP  ,  MAX(TC.PHON_NUM)  AS  BRAN_PHON  ,  :DISP_DAY  AS  DISP_DAY  ,  :PAY_DAY  AS  PAY_DAY  ,  TC.BIPLC_SNM  FROM  GIBU.TBRA_UPSO  TA  ,  GIBU.TBRA_DEMD_OCR  TB  ,  INSA.TCPM_BIPLC  TC  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  ZB.BSTYP_CD  IN  ('k',  'l',  'o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NOT  NULL  THEN  ZB.MONPRNCFEE2  +  NVL  (ZD.MONPRNCFEE,  0)  WHEN  ZB.BSTYP_CD  IN  ('k',  'l',  'o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NULL  THEN  ZB.MONPRNCFEE2  WHEN  ZB.BSTYP_CD  NOT  IN  ('k',  'l',  'o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NOT  NULL  THEN  ZB.MONPRNCFEE  +  NVL  (ZD.MONPRNCFEE,  0)  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  ,  GIBU.TBRA_BSCON_CONTRINFO  ZD  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD   \n";
        query +=" AND  ZB.UPSO_CD  =  ZD.UPSO_CD(+)  )  TD  WHERE  TA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TA.UPSO_NEW_ZIP  BETWEEN  NVL(:FROM_ZIP,  '000000')   \n";
        query +=" AND  NVL(:TO_ZIP,  '999999')   \n";
        query +=" AND  TA.OPBI_DAY  IS  NOT  NULL   \n";
        query +=" AND  TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.PAPER_CANC_YN  <>  'Y'   \n";
        query +=" AND  TA.UPSO_CD  NOT  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_AUTO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
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
        query +=" AND  TB.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  TB.BSTYP_CD,  :BSTYP_CD)  GROUP  BY  TA.BRAN_CD,  TA.UPSO_CD,  TC.BIPLC_SNM  UNION  ALL   \n";
        query +=" SELECT  TA.BRAN_CD  ,  MAX(TC.BIPLC_NM)  AS  BRAN_NM  ,  TA.UPSO_CD  ,  MAX(DECODE(TA.MAIL_RCPT,  'U',  TA.UPSO_NM,  ''))  AS  UPSO_NM  ,  MAX(DECODE(TA.PAYPRES_GBN,  'B',  TA.MNGEMSTR_NM,  TA.PERMMSTR_NM))  AS  RECV_NM  ,  MAX(REGEXP_REPLACE(DECODE(TA.PAYPRES_GBN,  'B',  TA.MNGEMSTR_HPNUM,  NVL(TA.PERMMSTR_HPNUM,  TA.MNGEMSTR_HPNUM)),  '[^0-9]',  ''))  AS  CP_NUM  ,  MAX(DECODE(TA.MAIL_RCPT,  'U',  TA.UPSO_NEW_ZIP,  'B',  TA.MNGEMSTR_NEW_ZIP,  TA.PERMMSTR_NEW_ZIP))  AS  RECV_ZIP  ,  MAX(DECODE(TA.MAIL_RCPT,  'U',  TA.UPSO_NEW_ADDR1  ||  DECODE(TA.UPSO_NEW_ADDR2,  '',  '',  ',  '  ||  TA.UPSO_NEW_ADDR2)  ||  TA.UPSO_REF_INFO  ,  'B',  TA.MNGEMSTR_NEW_ADDR1  ||  DECODE(TA.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  TA.MNGEMSTR_NEW_ADDR2)  ||  TA.MNGEMSTR_REF_INFO  ,  TA.PERMMSTR_NEW_ADDR1  ||  DECODE(TA.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  TA.PERMMSTR_NEW_ADDR2)  ||  TA.PERMMSTR_REF_INFO))  AS  RECV_ADDR  ,  MAX(TD.MONPRNCFEE)  AS  MONPRNCFEE  ,  MIN(TB.START_YRMN)  AS  START_YRMN  ,  MAX(TB.END_YRMN)  AS  END_YRMN  ,  MAX(TB.TOT_DEMD_AMT  -  TB.TOT_ADDT_AMT  -  TB.TOT_EADDT_AMT)  AS  NONPY_AMT  ,  MAX(TB.TOT_ADDT_AMT  +  TB.TOT_EADDT_AMT)  AS  TOT_ADDT_AMT  ,  MAX(TB.TOT_DEMD_AMT)  AS  TOT_DEMD_AMT  ,  MAX(TC.ADDR  ||  :BLANK  ||  TC.HNM)  AS  BRAN_ADDR  ,  MAX(TC.POST_NUM)  AS  BRAN_ZIP  ,  MAX(TC.PHON_NUM)  AS  BRAN_PHON  ,  :DISP_DAY  AS  DISP_DAY  ,  :PAY_DAY  AS  PAY_DAY  ,  TC.BIPLC_SNM  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  UPSO_CD  ,  UPSO_NM  ,  BEFORE_UPSO_CD  ,  MCHNDAESU  ,  UPSO_NEW_ZIP  ,  UPSO_NEW_ADDR1  ,  UPSO_NEW_ADDR2  ,  UPSO_REF_INFO  ,  MNGEMSTR_HPNUM  ,  MNGEMSTR_NEW_ZIP  ,  MNGEMSTR_NEW_ADDR1  ,  MNGEMSTR_NEW_ADDR2  ,  MNGEMSTR_REF_INFO  ,  PERMMSTR_HPNUM  ,  PERMMSTR_NEW_ZIP  ,  PERMMSTR_NEW_ADDR1  ,  PERMMSTR_NEW_ADDR2  ,  PERMMSTR_REF_INFO  ,  CLIENT_NUM  ,  MNGEMSTR_NM  ,  PERMMSTR_NM  ,  MAIL_RCPT  ,  PAYPRES_GBN  ,  MONTHS_BETWEEN(TO_DATE(SUBSTR(:DISP_DAY,  1,  6),  'YYYYMM'),  TO_DATE(TO_CHAR(INS_DATE,  'YYYYMM'),  'YYYYMM'))  AS  INS_MMCNT  FROM  GIBU.TBRA_UPSO  WHERE  NEW_DAY  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  UPSO_NEW_ZIP  BETWEEN  NVL(:FROM_ZIP,  '000000')   \n";
        query +=" AND  NVL(:TO_ZIP,  '999999')   \n";
        query +=" AND  OPBI_DAY  IS  NOT  NULL   \n";
        query +=" AND  CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  PAPER_CANC_YN  <>  'Y'  )  TA  ,  GIBU.TBRA_DEMD_OCR  TB  ,  INSA.TCPM_BIPLC  TC  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  (CASE  WHEN  ZB.BSTYP_CD  IN  ('k',  'l',  'o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NOT  NULL  THEN  ZB.MONPRNCFEE2  +  NVL  (ZD.MONPRNCFEE,  0)  WHEN  ZB.BSTYP_CD  IN  ('k',  'l',  'o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NULL  THEN  ZB.MONPRNCFEE2  WHEN  ZB.BSTYP_CD  NOT  IN  ('k',  'l',  'o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NOT  NULL  THEN  ZB.MONPRNCFEE  +  NVL  (ZD.MONPRNCFEE,  0)  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX  (A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  ,  GIBU.TBRA_BSCON_CONTRINFO  ZD  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD   \n";
        query +=" AND  ZB.UPSO_CD  =  ZD.UPSO_CD(+)  )  TD  WHERE  TA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TD.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TC.GIBU  =  TA.BRAN_CD   \n";
        query +=" AND  TA.INS_MMCNT  =  3   \n";
        query +=" AND  TA.UPSO_CD  NOT  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_AUTO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  DEMD_YRMN  =  SUBSTR(:DISP_DAY,  1,  6))   \n";
        query +=" AND  TB.DEMD_GBN  =  '32'   \n";
        query +=" AND  TB.OCR_PRNT  IS  NULL   \n";
        query +=" AND  TB.DEMD_YRMN  =  SUBSTR(:DISP_DAY,  1,  6)   \n";
        query +=" AND  TB.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  TB.BSTYP_CD,  :BSTYP_CD)  GROUP  BY  TA.BRAN_CD,  TA.UPSO_CD,  TC.BIPLC_SNM)  XA  WHERE  TOT_ADDT_AMT  <>  0   \n";
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
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XB.UPSO_NM  ,  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO  ADDR  ,  XB.MNGEMSTR_NM  ,  XC.GRADNM  ,  XB.UPSO_PHON  ,  DECODE(XA.START_YRMN,NULL,'',XA.START_YRMN)  START_YRMN  ,  DECODE(XA.END_YRMN,NULL,'',XA.END_YRMN)  END_YRMN  ,  XA.TOT_DEMD_AMT  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  XA  ,  GIBU.TBRA_UPSO  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  (CASE  WHEN  ZB.BSTYP_CD  IN  ('k','l','o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NOT  NULL  THEN  ZB.MONPRNCFEE2  +  NVL(ZD.MONPRNCFEE,  0)  WHEN  ZB.BSTYP_CD  IN  ('k','l','o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NULL  THEN  ZB.MONPRNCFEE2  WHEN  ZB.BSTYP_CD  NOT  IN  ('k','l','o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NOT  NULL  THEN  ZB.MONPRNCFEE  +  NVL(ZD.MONPRNCFEE,  0)  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  ,  GIBU.TBRA_BSCON_CONTRINFO  ZD  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD   \n";
        query +=" AND  ZB.UPSO_CD  =  ZD.UPSO_CD(+)   \n";
        query +=" AND  ZB.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  ZB.BSTYP_CD,  :BSTYP_CD)  )  XC  WHERE  XB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.BPAP_DAY  LIKE  SUBSTR(:BPAP_DAY,1,6)||'%'   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XB.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
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
        query +=" SELECT  XA.UPSO_CD  ,  XB.UPSO_NM  ,  DECODE(XB.PAYPRES_GBN,  'B',  XB.MNGEMSTR_NM,  XB.PERMMSTR_NM)  RECV_NM  ,  DECODE(XB.MAIL_RCPT,  'U',XB.UPSO_NEW_ZIP,  'B',  XB.MNGEMSTR_NEW_ZIP,  XB.PERMMSTR_NEW_ZIP)  RECV_ZIP  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XD.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  -  (XA.TOT_ADDT_AMT  +  XA.TOT_EADDT_AMT)  NONPY_AMT  ,  XA.TOT_ADDT_AMT  +  XA.TOT_EADDT_AMT  TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.TOT_DEMD_AMT  ,  XC.BIPLC_NM  BRAN_NM  ,  XC.ADDR||  '  '||XC.HNM  BRAN_ADDR  ,  XC.POST_NUM  BRAN_ZIP  ,  XC.PHON_NUM  BRAN_PHON  ,  DECODE(XB.MAIL_RCPT,  'U',  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO,  'B',  XB.MNGEMSTR_NEW_ADDR1  ||  DECODE(XB.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||XB.MNGEMSTR_NEW_ADDR2)  ||  XB.MNGEMSTR_REF_INFO,    XB.PERMMSTR_NEW_ADDR1  ||  DECODE(XB.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||XB.PERMMSTR_NEW_ADDR2)  ||  XB.PERMMSTR_REF_INFO  )  RECV_ADDR  ,  DECODE(XB.BRAN_CD,  'A',  '��������',  'B',  '��������',  'C',  '��������',  'E'  ,  '��������'  ,  'F'  ,  '��������'  ,  'G'  ,  '����'  ,  'H'  ,  '����'  ,  'I'  ,  '����'  ,  'J'  ,  '����'  ,  'K'  ,  '����'  ,  'L'  ,  '��������'  ,  'M'  ,  '����'  ,  'N'  ,  '�λ�����'  ,  'O'  ,  '����')  BANK  ,  DECODE(XB.BRAN_CD,  'A'  ,  '695037-01-001228'  ,  'B'  ,  '695037-01-001257'  ,  'C'  ,  '695037-01-001231'  ,  'E'  ,  '695037-01-001260'  ,  'F'  ,  '695037-01-001244'  ,  'G'  ,  '209-01-581021'  ,  'H'  ,  '311-01-155951'  ,  'I'  ,  '311-01-155951'  ,  'J'  ,  '511-01-073417'  ,  'K'  ,  '661-01-033882'  ,  'L'  ,  '632-01-0046-816'  ,  'M'  ,  '815135-51-018283'  ,  'N'  ,  '131-01-000342-2'  ,  'O'  ,  '901017-51-011928')  ACCT_NO  ,  XA.BPAP_DAY  ,  '1'  PRINT_YN  FROM  GIBU.TBRA_BPAP_MNG  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_BIPLC  XC  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  (CASE  WHEN  ZB.BSTYP_CD  IN  ('k','l','o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NOT  NULL  THEN  ZB.MONPRNCFEE2  +  NVL(ZD.MONPRNCFEE,  0)  WHEN  ZB.BSTYP_CD  IN  ('k','l','o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NULL  THEN  ZB.MONPRNCFEE2  WHEN  ZB.BSTYP_CD  NOT  IN  ('k','l','o')   \n";
        query +=" AND  ZD.UPSO_CD  IS  NOT  NULL  THEN  ZB.MONPRNCFEE  +  NVL(ZD.MONPRNCFEE,  0)  ELSE  ZB.MONPRNCFEE  END)  AS  MONPRNCFEE  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  ,  GIBU.TBRA_BSCON_CONTRINFO  ZD  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD   \n";
        query +=" AND  ZB.UPSO_CD  =  ZD.UPSO_CD(+)   \n";
        query +=" AND  ZB.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  ZB.BSTYP_CD,  :BSTYP_CD)  )  XD  WHERE  XB.BRAN_CD  =  :BRAN_CD   \n";
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
    //##**$$bpap_prnt_mng
    /* * ���α׷��� : bra05_s02
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/27
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLbpap_prnt_mng(DOBJ dobj)
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
            dobj  = CALLbpap_prnt_mng_MPD1(Conn, dobj);           //  �Ǻ�ó��
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
    public DOBJ CTLbpap_prnt_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbpap_prnt_mng_MPD1(Conn, dobj);           //  �Ǻ�ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �Ǻ�ó��
    public DOBJ CALLbpap_prnt_mng_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MPD1");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbpap_prnt_mng_UNI13(Conn, dobj);           //  ����̷�
                dobj  = CALLbpap_prnt_mng_SEL18(Conn, dobj);           //  VISIT_SEQ���ϱ�
                dobj  = CALLbpap_prnt_mng_UNI14(Conn, dobj);           //  ���ҹ湮�̷�
                dobj  = CALLbpap_prnt_mng_UNI15(Conn, dobj);           //  ���ҹ湮�޸��̷�
                dobj  = CALLbpap_prnt_mng_UNI16(Conn, dobj);           //  ���ҹ湮�޸��̷�2
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbpap_prnt_mng_UNI7(Conn, dobj);           //  ����̷�
                dobj  = CALLbpap_prnt_mng_SEL19(Conn, dobj);           //  VISIT_SEQ���ϱ�
                dobj  = CALLbpap_prnt_mng_UNI10(Conn, dobj);           //  ���ҹ湮�̷�
                dobj  = CALLbpap_prnt_mng_UNI11(Conn, dobj);           //  ���ҹ湮�޸��̷�
                dobj  = CALLbpap_prnt_mng_UNI12(Conn, dobj);           //  ���ҹ湮�޸��̷�2
            }
        }
        return dobj;
    }
    // ����̷�
    // ����Ǵ¼���
    public DOBJ CALLbpap_prnt_mng_UNI13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI13");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_prnt_mng_UNI13UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLbpap_prnt_mng_UNI13INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI13") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI13UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String SNUM ="";        //�Ϸù�ȣ
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //�̳� �ݾ�
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   TOT_DEMD_AMT = dvobj.getRecord().getDouble("TOT_DEMD_AMT");   //�� û�� �ݾ�
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //�� ���� �ݾ�
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //���۳��
        String   SNUM_1 = dvobj.getRecord().get("DISP_DAY");   //�Ϸù�ȣ
        String   PAY_DAY = dvobj.getRecord().get("PAY_DAY");   //���� ����
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //������
        String   BPAP_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�ְ� ����
        String   BPAP_GBN ="2";   //�ְ� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BPAP_PRNT_HISTY  \n";
        query +=" set INSPRES_ID=:INSPRES_ID , MONPRNCFEE=:MONPRNCFEE , PAY_DAY=:PAY_DAY , SNUM=(SELECT NVL(MAX(SNUM), 0) + 1 SNUM FROM GIBU.TBRA_BPAP_PRNT_HISTY  \n";
        query +=" WHERE BPAP_DAY = :SNUM_1) , START_YRMN=:START_YRMN , INS_DATE=SYSDATE , TOT_ADDT_AMT=:TOT_ADDT_AMT , TOT_DEMD_AMT=:TOT_DEMD_AMT , END_YRMN=:END_YRMN , NONPY_AMT=:NONPY_AMT  \n";
        query +=" where BPAP_DAY=:BPAP_DAY  \n";
        query +=" and BPAP_GBN=:BPAP_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //�̳� �ݾ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //�� û�� �ݾ�
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //�� ���� �ݾ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("SNUM_1", SNUM_1);               //�Ϸù�ȣ
        sobj.setString("PAY_DAY", PAY_DAY);               //���� ����
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setString("BPAP_GBN", BPAP_GBN);               //�ְ� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI13INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String SNUM ="";        //�Ϸù�ȣ
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //�̳� �ݾ�
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   TOT_DEMD_AMT = dvobj.getRecord().getDouble("TOT_DEMD_AMT");   //�� û�� �ݾ�
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //�� ���� �ݾ�
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //���۳��
        String   SNUM_1 = dvobj.getRecord().get("DISP_DAY");   //�Ϸù�ȣ
        String   PAY_DAY = dvobj.getRecord().get("PAY_DAY");   //���� ����
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //������
        String   BPAP_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�ְ� ����
        String   BPAP_GBN ="2";   //�ְ� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BPAP_PRNT_HISTY (INSPRES_ID, MONPRNCFEE, PAY_DAY, SNUM, START_YRMN, INS_DATE, BPAP_DAY, TOT_ADDT_AMT, TOT_DEMD_AMT, BPAP_GBN, UPSO_CD, END_YRMN, NONPY_AMT)  \n";
        query +=" values(:INSPRES_ID , :MONPRNCFEE , :PAY_DAY , (SELECT NVL(MAX(SNUM), 0) + 1 SNUM FROM GIBU.TBRA_BPAP_PRNT_HISTY WHERE BPAP_DAY = :SNUM_1), :START_YRMN , SYSDATE, :BPAP_DAY , :TOT_ADDT_AMT , :TOT_DEMD_AMT , :BPAP_GBN , :UPSO_CD , :END_YRMN , :NONPY_AMT )";
        sobj.setSql(query);
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //�̳� �ݾ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //�� û�� �ݾ�
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //�� ���� �ݾ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("SNUM_1", SNUM_1);               //�Ϸù�ȣ
        sobj.setString("PAY_DAY", PAY_DAY);               //���� ����
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setString("BPAP_GBN", BPAP_GBN);               //�ְ� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // ����̷�
    // ����Ǵ¼���
    public DOBJ CALLbpap_prnt_mng_UNI7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_prnt_mng_UNI7UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLbpap_prnt_mng_UNI7INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI7UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String SNUM ="";        //�Ϸù�ȣ
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //�̳� �ݾ�
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   TOT_DEMD_AMT = dvobj.getRecord().getDouble("TOT_DEMD_AMT");   //�� û�� �ݾ�
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //�� ���� �ݾ�
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //���۳��
        String   SNUM_1 = dvobj.getRecord().get("DISP_DAY");   //�Ϸù�ȣ
        String   PAY_DAY = dvobj.getRecord().get("PAY_DAY");   //���� ����
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //������
        String   BPAP_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�ְ� ����
        String   BPAP_GBN ="3";   //�ְ� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BPAP_PRNT_HISTY  \n";
        query +=" set INSPRES_ID=:INSPRES_ID , MONPRNCFEE=:MONPRNCFEE , PAY_DAY=:PAY_DAY , SNUM=(SELECT NVL(MAX(SNUM), 0) + 1 SNUM FROM GIBU.TBRA_BPAP_PRNT_HISTY  \n";
        query +=" WHERE BPAP_DAY = :SNUM_1) , START_YRMN=:START_YRMN , INS_DATE=SYSDATE , TOT_ADDT_AMT=:TOT_ADDT_AMT , TOT_DEMD_AMT=:TOT_DEMD_AMT , END_YRMN=:END_YRMN , NONPY_AMT=:NONPY_AMT  \n";
        query +=" where BPAP_DAY=:BPAP_DAY  \n";
        query +=" and BPAP_GBN=:BPAP_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //�̳� �ݾ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //�� û�� �ݾ�
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //�� ���� �ݾ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("SNUM_1", SNUM_1);               //�Ϸù�ȣ
        sobj.setString("PAY_DAY", PAY_DAY);               //���� ����
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setString("BPAP_GBN", BPAP_GBN);               //�ְ� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI7INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String SNUM ="";        //�Ϸù�ȣ
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //�̳� �ݾ�
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   TOT_DEMD_AMT = dvobj.getRecord().getDouble("TOT_DEMD_AMT");   //�� û�� �ݾ�
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //�� ���� �ݾ�
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //���۳��
        String   SNUM_1 = dvobj.getRecord().get("DISP_DAY");   //�Ϸù�ȣ
        String   PAY_DAY = dvobj.getRecord().get("PAY_DAY");   //���� ����
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //������
        String   BPAP_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�ְ� ����
        String   BPAP_GBN ="3";   //�ְ� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BPAP_PRNT_HISTY (INSPRES_ID, MONPRNCFEE, PAY_DAY, SNUM, START_YRMN, INS_DATE, BPAP_DAY, TOT_ADDT_AMT, TOT_DEMD_AMT, BPAP_GBN, UPSO_CD, END_YRMN, NONPY_AMT)  \n";
        query +=" values(:INSPRES_ID , :MONPRNCFEE , :PAY_DAY , (SELECT NVL(MAX(SNUM), 0) + 1 SNUM FROM GIBU.TBRA_BPAP_PRNT_HISTY WHERE BPAP_DAY = :SNUM_1), :START_YRMN , SYSDATE, :BPAP_DAY , :TOT_ADDT_AMT , :TOT_DEMD_AMT , :BPAP_GBN , :UPSO_CD , :END_YRMN , :NONPY_AMT )";
        sobj.setSql(query);
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //�̳� �ݾ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //�� û�� �ݾ�
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //�� ���� �ݾ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("SNUM_1", SNUM_1);               //�Ϸù�ȣ
        sobj.setString("PAY_DAY", PAY_DAY);               //���� ����
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setString("BPAP_GBN", BPAP_GBN);               //�ְ� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // VISIT_SEQ���ϱ�
    public DOBJ CALLbpap_prnt_mng_SEL18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL18");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL18");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbpap_prnt_mng_SEL18(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL18");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_prnt_mng_SEL18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),  0)  +  1  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // VISIT_SEQ���ϱ�
    public DOBJ CALLbpap_prnt_mng_SEL19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL19");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL19");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbpap_prnt_mng_SEL19(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_prnt_mng_SEL19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),  0)  +  1  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ҹ湮�̷�
    // ��϶Ǵ¼���
    public DOBJ CALLbpap_prnt_mng_UNI14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI14");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_prnt_mng_UNI14UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLbpap_prnt_mng_UNI14INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI14") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI14UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_TIME ="";        //�湮 �ð�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CONSPRES ="[������] "+dobj.getRetObject("R").getRecord().get("RECV_NM");   //�����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL18").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT  \n";
        query +=" set INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and CONSPRES=:CONSPRES  \n";
        query +=" and REMAK=:REMAK";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI14INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_TIME ="";        //�湮 �ð�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CONSPRES ="[������] "+dobj.getRetObject("R").getRecord().get("RECV_NM");   //�����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL18").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���ҹ湮�̷�
    // ��϶Ǵ¼���
    public DOBJ CALLbpap_prnt_mng_UNI10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_prnt_mng_UNI10UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLbpap_prnt_mng_UNI10INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI10") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI10UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_TIME ="";        //�湮 �ð�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CONSPRES ="[������] "+dobj.getRetObject("R").getRecord().get("RECV_NM");   //�����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT  \n";
        query +=" set INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and CONSPRES=:CONSPRES  \n";
        query +=" and REMAK=:REMAK";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI10INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_TIME ="";        //�湮 �ð�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CONSPRES ="[������] "+dobj.getRetObject("R").getRecord().get("RECV_NM");   //�����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���ҹ湮�޸��̷�
    // ��϶Ǵ¼���
    public DOBJ CALLbpap_prnt_mng_UNI15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI15");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_prnt_mng_UNI15UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLbpap_prnt_mng_UNI15INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI15") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI15UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_NUM = 1;   //�湮 ��ȣ
        int   VISIT_SEQ = dobj.getRetObject("SEL18").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" set INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID , REMAK=:REMAK  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and VISIT_NUM=:VISIT_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //�湮 ��ȣ
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI15INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_NUM = 1;   //�湮 ��ȣ
        int   VISIT_SEQ = dobj.getRetObject("SEL18").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :VISIT_NUM , :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //�湮 ��ȣ
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���ҹ湮�޸��̷�
    // ��϶Ǵ¼���
    public DOBJ CALLbpap_prnt_mng_UNI11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI11");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_prnt_mng_UNI11UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLbpap_prnt_mng_UNI11INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI11") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI11UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_NUM = 1;   //�湮 ��ȣ
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" set INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID , REMAK=:REMAK  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and VISIT_NUM=:VISIT_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //�湮 ��ȣ
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI11INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_NUM = 1;   //�湮 ��ȣ
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :VISIT_NUM , :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //�湮 ��ȣ
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���ҹ湮�޸��̷�2
    // ��϶Ǵ¼���
    public DOBJ CALLbpap_prnt_mng_UNI16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI16");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_prnt_mng_UNI16UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLbpap_prnt_mng_UNI16INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI16") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI16UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        int  VISIT_NUM = 0;        //�湮 ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_NUM_1 = dvobj.getRecord().get("UPSO_CD");   //�湮 ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� �������� "+dobj.getRetObject("R").getRecord().get("PAY_DAY");   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL18").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" set INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID , REMAK=:REMAK  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and VISIT_NUM=(SELECT NVL(MAX(VISIT_NUM), 0) + 1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" WHERE VISIT_DAY = :DISP_DAY  \n";
        query +=" AND UPSO_CD = :VISIT_NUM_1  \n";
        query +=" AND JOB_GBN = 'C')  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //�湮 ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI16INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        int  VISIT_NUM = 0;        //�湮 ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_NUM_1 = dvobj.getRecord().get("UPSO_CD");   //�湮 ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� �������� "+dobj.getRetObject("R").getRecord().get("PAY_DAY");   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL18").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , (SELECT NVL(MAX(VISIT_NUM), 0) + 1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = :DISP_DAY AND UPSO_CD = :VISIT_NUM_1 AND JOB_GBN = 'C'), :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //�湮 ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���ҹ湮�޸��̷�2
    // ��϶Ǵ¼���
    public DOBJ CALLbpap_prnt_mng_UNI12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_prnt_mng_UNI12UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLbpap_prnt_mng_UNI12INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI12UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        int  VISIT_NUM = 0;        //�湮 ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_NUM_1 = dvobj.getRecord().get("UPSO_CD");   //�湮 ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� �������� "+dobj.getRetObject("R").getRecord().get("PAY_DAY");   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" set INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID , REMAK=:REMAK  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and VISIT_NUM=(SELECT NVL(MAX(VISIT_NUM), 0) + 1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" WHERE VISIT_DAY = :DISP_DAY  \n";
        query +=" AND UPSO_CD = :VISIT_NUM_1  \n";
        query +=" AND JOB_GBN = 'C')  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //�湮 ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    private SQLObject SQLbpap_prnt_mng_UNI12INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        int  VISIT_NUM = 0;        //�湮 ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_NUM_1 = dvobj.getRecord().get("UPSO_CD");   //�湮 ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� �������� "+dobj.getRetObject("R").getRecord().get("PAY_DAY");   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , (SELECT NVL(MAX(VISIT_NUM), 0) + 1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = :DISP_DAY AND UPSO_CD = :VISIT_NUM_1 AND JOB_GBN = 'C'), :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //�湮 ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    //##**$$bpap_prnt_mng
    //##**$$bpap_visit_mng
    /*
    */
    public DOBJ CTLbpap_visit_mng(DOBJ dobj)
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
            dobj  = CALLbpap_visit_mng_MPD1(Conn, dobj);           //  �Ǻ�ó��
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
    public DOBJ CTLbpap_visit_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbpap_visit_mng_MPD1(Conn, dobj);           //  �Ǻ�ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �Ǻ�ó��
    public DOBJ CALLbpap_visit_mng_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MPD1");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbpap_visit_mng_SEL18(Conn, dobj);           //  VISIT_SEQ���ϱ�
                dobj  = CALLbpap_visit_mng_UNI14(Conn, dobj);           //  ���ҹ湮�̷�
                dobj  = CALLbpap_visit_mng_UNI15(Conn, dobj);           //  ���ҹ湮�޸��̷�
                dobj  = CALLbpap_visit_mng_UNI16(Conn, dobj);           //  ���ҹ湮�޸��̷�2
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbpap_visit_mng_SEL19(Conn, dobj);           //  VISIT_SEQ���ϱ�
                dobj  = CALLbpap_visit_mng_UNI10(Conn, dobj);           //  ���ҹ湮�̷�
                dobj  = CALLbpap_visit_mng_UNI11(Conn, dobj);           //  ���ҹ湮�޸��̷�
                dobj  = CALLbpap_visit_mng_UNI12(Conn, dobj);           //  ���ҹ湮�޸��̷�2
            }
        }
        return dobj;
    }
    // VISIT_SEQ���ϱ�
    public DOBJ CALLbpap_visit_mng_SEL18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL18");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL18");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbpap_visit_mng_SEL18(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL18");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_visit_mng_SEL18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),  0)  +  1  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // VISIT_SEQ���ϱ�
    public DOBJ CALLbpap_visit_mng_SEL19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL19");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL19");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbpap_visit_mng_SEL19(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_visit_mng_SEL19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),  0)  +  1  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ҹ湮�̷�
    // ��϶Ǵ¼���
    public DOBJ CALLbpap_visit_mng_UNI14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI14");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_visit_mng_UNI14UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLbpap_visit_mng_UNI14INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI14") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_visit_mng_UNI14UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_TIME ="";        //�湮 �ð�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CONSPRES ="[������] "+dobj.getRetObject("R").getRecord().get("RECV_NM");   //�����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL18").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT  \n";
        query +=" set INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and CONSPRES=:CONSPRES  \n";
        query +=" and REMAK=:REMAK";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    private SQLObject SQLbpap_visit_mng_UNI14INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_TIME ="";        //�湮 �ð�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CONSPRES ="[������] "+dobj.getRetObject("R").getRecord().get("RECV_NM");   //�����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL18").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���ҹ湮�̷�
    // ��϶Ǵ¼���
    public DOBJ CALLbpap_visit_mng_UNI10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_visit_mng_UNI10UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLbpap_visit_mng_UNI10INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI10") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_visit_mng_UNI10UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_TIME ="";        //�湮 �ð�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CONSPRES ="[������] "+dobj.getRetObject("R").getRecord().get("RECV_NM");   //�����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT  \n";
        query +=" set INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and CONSPRES=:CONSPRES  \n";
        query +=" and REMAK=:REMAK";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    private SQLObject SQLbpap_visit_mng_UNI10INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_TIME ="";        //�湮 �ð�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CONSPRES ="[������] "+dobj.getRetObject("R").getRecord().get("RECV_NM");   //�����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���ҹ湮�޸��̷�
    // ��϶Ǵ¼���
    public DOBJ CALLbpap_visit_mng_UNI15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI15");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_visit_mng_UNI15UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLbpap_visit_mng_UNI15INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI15") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_visit_mng_UNI15UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_NUM = 1;   //�湮 ��ȣ
        int   VISIT_SEQ = dobj.getRetObject("SEL18").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" set INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID , REMAK=:REMAK  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and VISIT_NUM=:VISIT_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //�湮 ��ȣ
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    private SQLObject SQLbpap_visit_mng_UNI15INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_NUM = 1;   //�湮 ��ȣ
        int   VISIT_SEQ = dobj.getRetObject("SEL18").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :VISIT_NUM , :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //�湮 ��ȣ
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���ҹ湮�޸��̷�
    // ��϶Ǵ¼���
    public DOBJ CALLbpap_visit_mng_UNI11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI11");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_visit_mng_UNI11UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLbpap_visit_mng_UNI11INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI11") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_visit_mng_UNI11UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_NUM = 1;   //�湮 ��ȣ
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" set INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID , REMAK=:REMAK  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and VISIT_NUM=:VISIT_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //�湮 ��ȣ
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    private SQLObject SQLbpap_visit_mng_UNI11INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� ����.";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_NUM = 1;   //�湮 ��ȣ
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :VISIT_NUM , :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //�湮 ��ȣ
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���ҹ湮�޸��̷�2
    // ��϶Ǵ¼���
    public DOBJ CALLbpap_visit_mng_UNI16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI16");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_visit_mng_UNI16UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLbpap_visit_mng_UNI16INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI16") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_visit_mng_UNI16UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        int  VISIT_NUM = 0;        //�湮 ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_NUM_1 = dvobj.getRecord().get("UPSO_CD");   //�湮 ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� �������� "+dobj.getRetObject("R").getRecord().get("PAY_DAY");   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL18").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" set INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID , REMAK=:REMAK  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and VISIT_NUM=(SELECT NVL(MAX(VISIT_NUM), 0) + 1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" WHERE VISIT_DAY = :DISP_DAY  \n";
        query +=" AND UPSO_CD = :VISIT_NUM_1  \n";
        query +=" AND JOB_GBN = 'C')  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //�湮 ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    private SQLObject SQLbpap_visit_mng_UNI16INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        int  VISIT_NUM = 0;        //�湮 ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_NUM_1 = dvobj.getRecord().get("UPSO_CD");   //�湮 ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� �������� "+dobj.getRetObject("R").getRecord().get("PAY_DAY");   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL18").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , (SELECT NVL(MAX(VISIT_NUM), 0) + 1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = :DISP_DAY AND UPSO_CD = :VISIT_NUM_1 AND JOB_GBN = 'C'), :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //�湮 ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���ҹ湮�޸��̷�2
    // ��϶Ǵ¼���
    public DOBJ CALLbpap_visit_mng_UNI12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbpap_visit_mng_UNI12UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLbpap_visit_mng_UNI12INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_visit_mng_UNI12UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        int  VISIT_NUM = 0;        //�湮 ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_NUM_1 = dvobj.getRecord().get("UPSO_CD");   //�湮 ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� �������� "+dobj.getRetObject("R").getRecord().get("PAY_DAY");   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" set INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID , REMAK=:REMAK  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and VISIT_NUM=(SELECT NVL(MAX(VISIT_NUM), 0) + 1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" WHERE VISIT_DAY = :DISP_DAY  \n";
        query +=" AND UPSO_CD = :VISIT_NUM_1  \n";
        query +=" AND JOB_GBN = 'C')  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //�湮 ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    private SQLObject SQLbpap_visit_mng_UNI12INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        int  VISIT_NUM = 0;        //�湮 ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_NUM_1 = dvobj.getRecord().get("UPSO_CD");   //�湮 ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="�ְ� �������� "+dobj.getRetObject("R").getRecord().get("PAY_DAY");   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , (SELECT NVL(MAX(VISIT_NUM), 0) + 1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = :DISP_DAY AND UPSO_CD = :VISIT_NUM_1 AND JOB_GBN = 'C'), :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //�湮 ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    //##**$$bpap_visit_mng
    //##**$$end
}
