
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s01
{
    public bra03_s01()
    {
    }
    //##**$$demd_ocr_select
    /* * ���α׷��� : bra03_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/22
    * ����    : ���ο��� ����� ���� ���γ����� ��ȸ�Ѵ�.
    �Էµ� û�������� ���� ���� ��� / ���� ������� ���еȴ�.
    �ű� ������ ���� ���� ��� ���� 3���� �̻� �̳� �� �ְ��� �߼۵ȴ�.
    Input
    BRAN_CD (���� �ڵ�)
    DEMD_YRMN (û�� ���)
    DMND_PRT_YN (���� ��� ����)
    END_CNT_MON (���� ����)
    START_CNT_MON (���� ����)
    * ������ : 2010/06/10
    * ������ : �ǳ���
    * �������� : �޾� ������ ���Ͽ� û�������� �߻��ǰ�, û���ݾ��� �߻����� �ʴ� ������ �����ϱ� ���Ͽ�
    û���ݾ��� 0���� Ŭ ��츸 ��ȸ�ǰ� ������.
    */
    public DOBJ CTLdemd_ocr_select(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("DMND_PRT_YN").equals("Y"))
            {
                dobj  = CALLdemd_ocr_select_SEL1(Conn, dobj);           //  ���� û����� ��ȸ
                dobj  = CALLdemd_ocr_select_MRG5( dobj);        //  ��� ����
            }
            else
            {
                dobj  = CALLdemd_ocr_select_SEL6(Conn, dobj);           //  �Ϲ� û����� ��ȸ
                dobj  = CALLdemd_ocr_select_MRG5( dobj);        //  ��� ����
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
    public DOBJ CTLdemd_ocr_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("DMND_PRT_YN").equals("Y"))
        {
            dobj  = CALLdemd_ocr_select_SEL1(Conn, dobj);           //  ���� û����� ��ȸ
            dobj  = CALLdemd_ocr_select_MRG5( dobj);        //  ��� ����
        }
        else
        {
            dobj  = CALLdemd_ocr_select_SEL6(Conn, dobj);           //  �Ϲ� û����� ��ȸ
            dobj  = CALLdemd_ocr_select_MRG5( dobj);        //  ��� ����
        }
        return dobj;
    }
    // ���� û����� ��ȸ
    // �ش� ����� ��ȸ���� (��ü��, �������) �� ���� û������� ��ȸ�Ѵ�  �űԾ��Ҵ� ���� ��¿��� ���ܵȴ�. �űԾ����� ��� ���� ��� ���� �ְ��� �߼۵ȴ�. �űԾ��� : �������� ������ �ű����� ���� ���� (�ѹ��� �Աݵ��� ���� ����)
    public DOBJ CALLdemd_ocr_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLdemd_ocr_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdemd_ocr_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("START_CNT_MON");   //���� ����
        double   END_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("END_CNT_MON");   //���� ����
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //û�� ���
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BRAN_CD  ,  B.DEMD_YRMN  ,  A.UPSO_CD  ,  DECODE(A.MAIL_RCPT,  'U',  REPLACE(REPLACE(A.UPSO_NM,  CHR(13),  ''),  CHR(10),  ''),  '')  AS  UPSO_NM  ,  REPLACE(REPLACE(A.UPSO_NM,  CHR(13),  ''),  CHR(10),  '')  AS  B_UPSO_NM  ,  A.MCHNDAESU  ,  B.START_YRMN  ,  B.END_YRMN  ,  (CASE  WHEN  B.TOT_DEMD_AMT  -  (B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT)  -  (B.MONPRNCFEE  +  NVL((SELECT  SUM(NVL(DEMD_AMT,  0))  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  B.DEMD_YRMN),  0))  <  0  THEN  0  ELSE  B.TOT_DEMD_AMT  -  (B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT)  -  (B.MONPRNCFEE  +  NVL((SELECT  SUM(NVL(DEMD_AMT,  0))  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  B.DEMD_YRMN),  0))  END)  AS  NONPY_AMT  ,  B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT  AS  ADDT_AMT  ,  B.MONPRNCFEE  +  NVL((SELECT  SUM(NVL(DEMD_AMT,  0))  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  B.DEMD_YRMN),  0)  AS  MONPRNCFEE  ,  (CASE  MAIL_RCPT  WHEN  'U'  THEN  A.UPSO_NEW_ZIP  WHEN  'B'  THEN  A.MNGEMSTR_NEW_ZIP  ELSE  A.PERMMSTR_NEW_ZIP  END)  AS  UPSO_ZIP  ,  (CASE  MAIL_RCPT  WHEN  'U'  THEN  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  WHEN  'B'  THEN  A.MNGEMSTR_NEW_ADDR1  ||  DECODE(A.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||A.MNGEMSTR_NEW_ADDR2)  ||  A.MNGEMSTR_REF_INFO  ELSE  A.PERMMSTR_NEW_ADDR1  ||  DECODE(A.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||A.PERMMSTR_NEW_ADDR2)  ||  A.PERMMSTR_REF_INFO  END)  AS  UPSO_ADDR  ,  A.CLIENT_NUM  ,  C.DEPT_NM  ,  D.POST_NUM  AS  BRAN_POST_NUM  ,  D.ADDR  ||  '  '  ||  HNM  AS  BRAN_ADDR  ,  D.PHON_NUM  AS  BRAN_PHON_NUM  ,  D.FAX_NUM  AS  BRAN_FAX_NUM  ,  ''  AS  OCR_NO  ,  '1'  AS  CHK_PRT  ,  DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_NM,  A.PERMMSTR_NM)  AS  MNGEMSTR_NM  ,  B.DEMD_MMCNT  ,  B.TOT_DEMD_AMT  AS  DEMD_AMT  ,  B.DSCT_AMT  ,  DECODE(DSCT_AMT,  0,  '',  '<������  30%����  ����>')  AS  REMAK  ,   \n";
        query +=" (SELECT  ACCOUNT_NM  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  BANK_CODE  =  '0'  ||   \n";
        query +=" (SELECT  BANK_CD  FROM  GIBU.TFMS_BANK  WHERE  BRAN_CD  IS  NULL))  AS  REPTPRES  ,   \n";
        query +=" (SELECT  LISTAGG((SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  BANK_CD  =  BANK_CODE)  ||  '  :  '  ||  GIBU.FT_GET_ACCOUNT_FORMAT(VIRTUAL_ACCOUNT_NUM),  CHR(13)  ||  CHR(10))  WITHIN  GROUP(ORDER  BY  1)  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y')  AS  VIRTUAL_ACCN  ,  REPLACE(REPLACE(DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_HPNUM,  NVL(A.PERMMSTR_HPNUM,  A.MNGEMSTR_HPNUM)),  '-',  ''),  '  ',  '')  AS  CP_NUM  ,   \n";
        query +=" (SELECT  GBN  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  GBN  IS  NOT  NULL)  AS  SEND_TYPE  ,  REPLACE(REPLACE(TRIM(A.EMAIL_ADDR),  CHR(13),  ''),  CHR(10),  '')  AS  EMAIL_ADDR  ,  A.PAPER_CANC_YN  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_DEMD_OCR  B  ,  INSA.TCPM_DEPT  C  ,  INSA.TCPM_BIPLC  D  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  NVL(B.TOT_DEMD_AMT,0)  >  0   \n";
        query +=" AND  B.DEMD_MMCNT  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON   \n";
        query +=" AND  C.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  D.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  B.DEMD_GBN  =  '32'   \n";
        query +=" AND  A.PAPER_CANC_YN  =  'N'   \n";
        query +=" AND  A.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  B.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  B.BSTYP_CD,  :BSTYP_CD)  ORDER  BY  UPSO_NEW_ZIP ";
        sobj.setSql(query);
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //���� ����
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //���� ����
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ��� ����
    // �Ϲ����� ���, ���� ����� ����� �����Ѵ�
    public DOBJ CALLdemd_ocr_select_MRG5(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG5");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL6","");
        rvobj.setName("MRG5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // �Ϲ� û����� ��ȸ
    // �ش� ����� ��ȸ���� (��ü��, �������, �������) �� ���� û������� ��ȸ�Ѵ�
    public DOBJ CALLdemd_ocr_select_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLdemd_ocr_select_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdemd_ocr_select_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("START_CNT_MON");   //���� ����
        double   END_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("END_CNT_MON");   //���� ����
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //û�� ���
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.BRAN_CD  AS  BRAN_CD  ,  DEMD_YRMN  ,  TA.UPSO_CD  ,  REPLACE(REPLACE(UPSO_NM,  CHR(13),  ''),  CHR(10),  '')  AS  UPSO_NM  ,  REPLACE(REPLACE(B_UPSO_NM,  CHR(13),  ''),  CHR(10),  '')  AS  B_UPSO_NM  ,  MCHNDAESU  ,  START_YRMN  ,  END_YRMN  ,  CASE  WHEN  NONPY_AMT  <0  THEN  0  ELSE  NONPY_AMT  END  NONPY_AMT  ,  ADDT_AMT  ,  MONPRNCFEE  ,  UPSO_ZIP  ,  UPSO_ADDR  ,  CLIENT_NUM  ,  DEPT_NM  ,  BRAN_POST_NUM  ,  BRAN_ADDR  ,  BRAN_PHON_NUM  ,  BRAN_FAX_NUM  ,  OCR_NO  ,  CHK_PRT  ,  MNGEMSTR_NM  ,  DEMD_MMCNT  ,  DEMD_AMT  ,  DSCT_AMT  ,  DECODE(DSCT_AMT,  0,  '',  '<������  30%����  ����>')  REMAK  ,   \n";
        query +=" (SELECT  ACCOUNT_NM  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  BANK_CODE  =  '0'  ||   \n";
        query +=" (SELECT  BANK_CD  FROM  GIBU.TFMS_BANK  WHERE  BRAN_CD  IS  NULL   \n";
        query +=" AND  REMAK  =  'Y'))  AS  REPTPRES  ,   \n";
        query +=" (SELECT  LISTAGG((SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  BANK_CD  =  BANK_CODE)  ||  '  :  '  ||  GIBU.FT_GET_ACCOUNT_FORMAT(VIRTUAL_ACCOUNT_NUM),  CHR(13)  ||  CHR(10))  WITHIN  GROUP(ORDER  BY  1)  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y')  AS  VIRTUAL_ACCN  ,  CP_NUM  ,   \n";
        query +=" (SELECT  GBN  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  GBN  IS  NOT  NULL)  AS  SEND_TYPE  ,  REPLACE(REPLACE(TRIM(EMAIL_ADDR),  CHR(13),  ''),  CHR(10),  '')  AS  EMAIL_ADDR  ,  PAPER_CANC_YN  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  B.DEMD_YRMN  ,  A.UPSO_CD  ,  DECODE(A.MAIL_RCPT,  'U',  A.UPSO_NM,  '')  UPSO_NM  ,  A.UPSO_NM  B_UPSO_NM  ,  A.MCHNDAESU  ,  B.START_YRMN  ,  B.END_YRMN  ,  B.TOT_DEMD_AMT  -  (B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT)  -  (B.MONPRNCFEE  +  NVL((SELECT  SUM(NVL(DEMD_AMT,  0))  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  B.DEMD_YRMN),  0))  AS  NONPY_AMT  ,  B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT  ADDT_AMT  ,  B.MONPRNCFEE  +  NVL((SELECT  SUM(NVL(DEMD_AMT,  0))  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  B.DEMD_YRMN),  0)  AS  MONPRNCFEE  ,  CASE  MAIL_RCPT  WHEN  'U'  THEN  A.UPSO_NEW_ZIP  WHEN  'B'  THEN  A.MNGEMSTR_NEW_ZIP  ELSE  A.PERMMSTR_NEW_ZIP  END  UPSO_ZIP  ,  CASE  MAIL_RCPT  WHEN  'U'  THEN  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  WHEN  'B'  THEN  A.MNGEMSTR_NEW_ADDR1  ||  DECODE(A.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||A.MNGEMSTR_NEW_ADDR2)  ||  A.MNGEMSTR_REF_INFO  ELSE  A.PERMMSTR_NEW_ADDR1  ||  DECODE(A.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||A.PERMMSTR_NEW_ADDR2)  ||  A.PERMMSTR_REF_INFO  END  UPSO_ADDR  ,  A.CLIENT_NUM  ,  C.DEPT_NM  ,  D.POST_NUM  BRAN_POST_NUM  ,  D.ADDR  ||  '  '||  HNM  BRAN_ADDR  ,  D.PHON_NUM  BRAN_PHON_NUM  ,  D.FAX_NUM  BRAN_FAX_NUM  ,  ''  OCR_NO  ,  '1'  CHK_PRT  ,  DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_NM,  A.PERMMSTR_NM)  MNGEMSTR_NM  ,  REPLACE(REPLACE(DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_HPNUM,  NVL(A.PERMMSTR_HPNUM,  A.MNGEMSTR_HPNUM)),  '-',  ''),  '  ',  '')  AS  CP_NUM  ,  B.DEMD_MMCNT  ,  B.TOT_DEMD_AMT  DEMD_AMT  ,  B.DSCT_AMT  ,  A.EMAIL_ADDR  ,  A.PAPER_CANC_YN  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_DEMD_OCR  B  ,  INSA.TCPM_DEPT  C  ,  INSA.TCPM_BIPLC  D  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  NVL(B.TOT_DEMD_AMT,0)  >  0   \n";
        query +=" AND  B.DEMD_MMCNT  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON   \n";
        query +=" AND  C.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  D.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  B.DEMD_GBN  =  '32'   \n";
        query +=" AND  A.PAPER_CANC_YN  =  'N'   \n";
        query +=" AND  A.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  B.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  B.BSTYP_CD,  :BSTYP_CD)  UNION  ALL   \n";
        query +=" SELECT  A.BRAN_CD  ,  B.DEMD_YRMN  ,  A.UPSO_CD  ,  DECODE(A.MAIL_RCPT,  'U',  A.UPSO_NM,  '')  UPSO_NM  ,  A.UPSO_NM  B_UPSO_NM  ,  A.MCHNDAESU  ,  B.START_YRMN  ,  B.END_YRMN  ,  B.TOT_DEMD_AMT  -  (B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT)  -  (B.MONPRNCFEE  +  NVL((SELECT  SUM(NVL(DEMD_AMT,  0))  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  B.DEMD_YRMN),  0))  AS  NONPY_AMT  ,  B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT  ADDT_AMT  ,  B.MONPRNCFEE  +  NVL((SELECT  SUM(NVL(DEMD_AMT,  0))  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  B.DEMD_YRMN),  0)  AS  MONPRNCFEE  ,  CASE  MAIL_RCPT  WHEN  'U'  THEN  A.UPSO_NEW_ZIP  WHEN  'B'  THEN  A.MNGEMSTR_NEW_ZIP  ELSE  A.PERMMSTR_NEW_ZIP  END  UPSO_ZIP  ,  CASE  MAIL_RCPT  WHEN  'U'  THEN  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  WHEN  'B'  THEN  A.MNGEMSTR_NEW_ADDR1  ||  DECODE(A.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||A.MNGEMSTR_NEW_ADDR2)  ||  A.MNGEMSTR_REF_INFO  ELSE  A.PERMMSTR_NEW_ADDR1  ||  DECODE(A.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||A.PERMMSTR_NEW_ADDR2)  ||  A.PERMMSTR_REF_INFO  END  UPSO_ADDR  ,  A.CLIENT_NUM  ,  C.DEPT_NM  ,  D.POST_NUM  BRAN_POST_NUM  ,  D.ADDR  ||  '  '  ||  HNM  BRAN_ADDR  ,  D.PHON_NUM  BRAN_PHON_NUM  ,  D.FAX_NUM  BRAN_FAX_NUM  ,  ''  OCR_NO  ,  '1'  CHK_PRT  ,  DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_NM,  A.PERMMSTR_NM)  MNGEMSTR_NM  ,  REPLACE(REPLACE(DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_HPNUM,  NVL(A.PERMMSTR_HPNUM,  A.MNGEMSTR_HPNUM)),  '-',  ''),  '  ',  '')  AS  CP_NUM  ,  B.DEMD_MMCNT  ,  B.TOT_DEMD_AMT  ,  B.DSCT_AMT  ,  A.EMAIL_ADDR  ,  A.PAPER_CANC_YN  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  UPSO_CD  ,  UPSO_NM  ,  BEFORE_UPSO_CD  ,  MCHNDAESU  ,  UPSO_NEW_ZIP  ,  UPSO_NEW_ADDR1  ,  UPSO_NEW_ADDR2  ,  UPSO_REF_INFO  ,  MNGEMSTR_NEW_ZIP  ,  MNGEMSTR_NEW_ADDR1  ,  MNGEMSTR_NEW_ADDR2  ,  MNGEMSTR_REF_INFO  ,  MNGEMSTR_HPNUM  ,  PERMMSTR_NEW_ZIP  ,  PERMMSTR_NEW_ADDR1  ,  PERMMSTR_NEW_ADDR2  ,  PERMMSTR_REF_INFO  ,  PERMMSTR_HPNUM  ,  CLIENT_NUM  ,  MNGEMSTR_NM  ,  PERMMSTR_NM  ,  MAIL_RCPT  ,  PAYPRES_GBN  ,  MONTHS_BETWEEN(TO_DATE(:DEMD_YRMN,  'YYYYMM'),  TO_DATE(TO_CHAR(INS_DATE,  'YYYYMM'),  'YYYYMM'))  INS_MMCNT  ,  PAPER_CANC_YN  ,  EMAIL_ADDR  FROM  GIBU.TBRA_UPSO  WHERE  NEW_DAY  IS  NULL  )  A  ,  GIBU.TBRA_DEMD_OCR  B  ,  INSA.TCPM_DEPT  C  ,  INSA.TCPM_BIPLC  D  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  NVL(B.TOT_DEMD_AMT,0)  >  0   \n";
        query +=" AND  A.INS_MMCNT  BETWEEN  0   \n";
        query +=" AND  2   \n";
        query +=" AND  C.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  D.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  B.DEMD_GBN  =  '32'   \n";
        query +=" AND  A.PAPER_CANC_YN  =  'N'   \n";
        query +=" AND  B.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  B.BSTYP_CD,  :BSTYP_CD)  )  TA  ORDER  BY  UPSO_ZIP ";
        sobj.setSql(query);
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //���� ����
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //���� ����
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$demd_ocr_select
    //##**$$demd_ocr_paperprnt
    /* * ���α׷��� : bra03_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/10/13
    * ����    : ����/������ ��� �� ��� ��°���� �����Ѵ�.
    Input
    ADDT_AMT (���� �ݾ�)
    BEFORE_UPSO_NM (���������Ҹ�)
    BRAN_ADDR (���� �ּ�)
    BRAN_CD (���� �ڵ�)
    BRAN_FAX_NUM (���� �ѽ���ȣ)
    BRAN_PHON_NUM (���� ��ȭ��ȣ)
    BRAN_POST_NUM (���� �����ȣ)
    CHK_PRT (��� ����)
    CLIENT_NUM (�� ��ȣ)
    DEMD_AMT (û�� �ݾ�)
    DEMD_YRMN (û�� ���)
    DEPT_NM (�μ� ��)
    END_YRMN (������)
    IUDFLAG (���ڵ���±���)
    MNGEMSTR_NM (�濵�� �̸�)
    MONPRNCFEE (������)
    NONPY_AMT (�̳� �ݾ�)
    OCR_GBN (OCR_GBN)
    OCR_NO (���ι�ȣ)
    START_YRMN (���۳��)
    UPSO_ADDR (UPSO_ADDR)
    UPSO_CD (���� �ڵ�)
    UPSO_NM (���� ��)
    UPSO_ZIP (���� �����ȣ)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLdemd_ocr_paperprnt(DOBJ dobj)
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
            dobj  = CALLdemd_ocr_paperprnt_MIUD6(Conn, dobj);           //  ����
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
    public DOBJ CTLdemd_ocr_paperprnt( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdemd_ocr_paperprnt_MIUD6(Conn, dobj);           //  ����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ����
    public DOBJ CALLdemd_ocr_paperprnt_MIUD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD6");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLdemd_ocr_paperprnt_SEL12(Conn, dobj);           //  NLL
                if( dobj.getRetObject("S").getRecord().get("CHK_PRT").equals("1"))
                {
                    dobj  = CALLdemd_ocr_paperprnt_UNI15(Conn, dobj);           //  ��� ����
                    dobj  = CALLdemd_ocr_paperprnt_UPD16(Conn, dobj);           //  ����/���� ����
                }
            }
        }
        return dobj;
    }
    // NLL
    // ���� �б⸦ ���� select
    public DOBJ CALLdemd_ocr_paperprnt_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLdemd_ocr_paperprnt_SEL12(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdemd_ocr_paperprnt_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '1'  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // ��� ����
    // OCR ��µ� ����� �����Ѵ�
    public DOBJ CALLdemd_ocr_paperprnt_UNI15(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLdemd_ocr_paperprnt_UNI15UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLdemd_ocr_paperprnt_UNI15INS(dobj, dvobj);
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
    private SQLObject SQLdemd_ocr_paperprnt_UNI15UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String DEMD_YRMN ="";        //û�� ��
        String INS_DATE ="";        //��� �Ͻ�
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //�̳� �ݾ�
        String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //���� �����ȣ
        String   BRAN_ADDR = dvobj.getRecord().get("BRAN_ADDR");   //���� �ּ�
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   UPSO_NM = dvobj.getRecord().get("UPSO_NM");   //���� ��
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   CLIENT_NUM = dvobj.getRecord().get("CLIENT_NUM");   //�� ��ȣ
        String   BRAN_NM = dvobj.getRecord().get("BRAN_NM");   //���� �̸�
        String   OCR_NO = dvobj.getRecord().get("OCR_NO");   //���ι�ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   UPSO_ADDR = dvobj.getRecord().get("UPSO_ADDR");   //���� �ּ�
        double   TOT_DEMD_AMT = dvobj.getRecord().getDouble("TOT_DEMD_AMT");   //�� û�� �ݾ�
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //�� ���� �ݾ�
        String   DEMD_YRMN_1 = dobj.getRetObject("R").getRecord().get("DEMD_YRMN");   //û�� ���
        String   OCR_GBN = dvobj.getRecord().get("OCR_GBN");
        String   BRAN_FAX = dvobj.getRecord().get("BRAN_FAX");
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //���۳��
        String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //�濵�� �̸�
        String   BEFORE_UPSO_NM = dvobj.getRecord().get("BEFORE_UPSO_NM");   //���������Ҹ�
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //������
        String   BRAN_ZIP = dvobj.getRecord().get("BRAN_ZIP");   //���� �����ȣ
        String   BRAN_PHON = dvobj.getRecord().get("BRAN_PHON");   //���� ��ȭ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_OCR_PRNT_RSLT  \n";
        query +=" set INSPRES_ID=:INSPRES_ID , BRAN_PHON=:BRAN_PHON , BRAN_ZIP=:BRAN_ZIP , MONPRNCFEE=:MONPRNCFEE ,  \n";
        query +=" BEFORE_UPSO_NM=:BEFORE_UPSO_NM , MNGEMSTR_NM=:MNGEMSTR_NM , START_YRMN=:START_YRMN , INS_DATE=SYSDATE , BRAN_FAX=:BRAN_FAX , OCR_GBN=:OCR_GBN , TOT_ADDT_AMT=:TOT_ADDT_AMT , TOT_DEMD_AMT=:TOT_DEMD_AMT , UPSO_ADDR=:UPSO_ADDR , OCR_NO=:OCR_NO , BRAN_NM=:BRAN_NM , CLIENT_NUM=:CLIENT_NUM , BRAN_CD=:BRAN_CD , UPSO_NM=:UPSO_NM , END_YRMN=:END_YRMN , BRAN_ADDR=:BRAN_ADDR , UPSO_ZIP=:UPSO_ZIP , NONPY_AMT=:NONPY_AMT  \n";
        query +=" where DEMD_YRMN=SUBSTR(:DEMD_YRMN_1, 1, 6)  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //�̳� �ݾ�
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
        sobj.setString("BRAN_ADDR", BRAN_ADDR);               //���� �ּ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("CLIENT_NUM", CLIENT_NUM);               //�� ��ȣ
        sobj.setString("BRAN_NM", BRAN_NM);               //���� �̸�
        sobj.setString("OCR_NO", OCR_NO);               //���ι�ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("UPSO_ADDR", UPSO_ADDR);               //���� �ּ�
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //�� û�� �ݾ�
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //�� ���� �ݾ�
        sobj.setString("DEMD_YRMN_1", DEMD_YRMN_1);               //û�� ���
        sobj.setString("OCR_GBN", OCR_GBN);
        sobj.setString("BRAN_FAX", BRAN_FAX);
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //�濵�� �̸�
        sobj.setString("BEFORE_UPSO_NM", BEFORE_UPSO_NM);               //���������Ҹ�
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
        sobj.setString("BRAN_ZIP", BRAN_ZIP);               //���� �����ȣ
        sobj.setString("BRAN_PHON", BRAN_PHON);               //���� ��ȭ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    private SQLObject SQLdemd_ocr_paperprnt_UNI15INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String DEMD_YRMN ="";        //û�� ��
        String INS_DATE ="";        //��� �Ͻ�
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //�̳� �ݾ�
        String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //���� �����ȣ
        String   BRAN_ADDR = dvobj.getRecord().get("BRAN_ADDR");   //���� �ּ�
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   UPSO_NM = dvobj.getRecord().get("UPSO_NM");   //���� ��
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   CLIENT_NUM = dvobj.getRecord().get("CLIENT_NUM");   //�� ��ȣ
        String   BRAN_NM = dvobj.getRecord().get("BRAN_NM");   //���� �̸�
        String   OCR_NO = dvobj.getRecord().get("OCR_NO");   //���ι�ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   UPSO_ADDR = dvobj.getRecord().get("UPSO_ADDR");   //���� �ּ�
        double   TOT_DEMD_AMT = dvobj.getRecord().getDouble("TOT_DEMD_AMT");   //�� û�� �ݾ�
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //�� ���� �ݾ�
        String   DEMD_YRMN_1 = dobj.getRetObject("R").getRecord().get("DEMD_YRMN");   //û�� ���
        String   OCR_GBN = dvobj.getRecord().get("OCR_GBN");
        String   BRAN_FAX = dvobj.getRecord().get("BRAN_FAX");
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //���۳��
        String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //�濵�� �̸�
        String   BEFORE_UPSO_NM = dvobj.getRecord().get("BEFORE_UPSO_NM");   //���������Ҹ�
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //������
        String   BRAN_ZIP = dvobj.getRecord().get("BRAN_ZIP");   //���� �����ȣ
        String   BRAN_PHON = dvobj.getRecord().get("BRAN_PHON");   //���� ��ȭ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_OCR_PRNT_RSLT (INSPRES_ID, BRAN_PHON, BRAN_ZIP, MONPRNCFEE, BEFORE_UPSO_NM, MNGEMSTR_NM, START_YRMN, INS_DATE, BRAN_FAX, OCR_GBN, DEMD_YRMN, TOT_ADDT_AMT, TOT_DEMD_AMT, UPSO_ADDR, UPSO_CD, OCR_NO, BRAN_NM, CLIENT_NUM, BRAN_CD, UPSO_NM, END_YRMN, BRAN_ADDR, UPSO_ZIP, NONPY_AMT)  \n";
        query +=" values(:INSPRES_ID , :BRAN_PHON , :BRAN_ZIP , :MONPRNCFEE , :BEFORE_UPSO_NM , :MNGEMSTR_NM , :START_YRMN , SYSDATE, :BRAN_FAX , :OCR_GBN , SUBSTR(:DEMD_YRMN_1, 1, 6), :TOT_ADDT_AMT , :TOT_DEMD_AMT , :UPSO_ADDR , :UPSO_CD , :OCR_NO , :BRAN_NM , :CLIENT_NUM , :BRAN_CD , :UPSO_NM , :END_YRMN , :BRAN_ADDR , :UPSO_ZIP , :NONPY_AMT )";
        sobj.setSql(query);
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //�̳� �ݾ�
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
        sobj.setString("BRAN_ADDR", BRAN_ADDR);               //���� �ּ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("CLIENT_NUM", CLIENT_NUM);               //�� ��ȣ
        sobj.setString("BRAN_NM", BRAN_NM);               //���� �̸�
        sobj.setString("OCR_NO", OCR_NO);               //���ι�ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("UPSO_ADDR", UPSO_ADDR);               //���� �ּ�
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //�� û�� �ݾ�
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //�� ���� �ݾ�
        sobj.setString("DEMD_YRMN_1", DEMD_YRMN_1);               //û�� ���
        sobj.setString("OCR_GBN", OCR_GBN);
        sobj.setString("BRAN_FAX", BRAN_FAX);
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //�濵�� �̸�
        sobj.setString("BEFORE_UPSO_NM", BEFORE_UPSO_NM);               //���������Ҹ�
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
        sobj.setString("BRAN_ZIP", BRAN_ZIP);               //���� �����ȣ
        sobj.setString("BRAN_PHON", BRAN_PHON);               //���� ��ȭ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // ����/���� ����
    // û�� ���̺� �������� �������� �����Ѵ�
    public DOBJ CALLdemd_ocr_paperprnt_UPD16(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLdemd_ocr_paperprnt_UPD16(dobj, dvobj);
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
    private SQLObject SQLdemd_ocr_paperprnt_UPD16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String DEMD_YRMN ="";        //û�� ��
        String MOD_DATE ="";        //���� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   DEMD_YRMN_1 = dobj.getRetObject("R").getRecord().get("DEMD_YRMN");   //û�� ���
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   OCR_PRNT = dobj.getRetObject("R").getRecord().get("OCR_GBN");   //���� ��±���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_DEMD_OCR  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , OCR_PRNT=:OCR_PRNT , MOD_DATE=SYSDATE  \n";
        query +=" where DEMD_YRMN=SUBSTR(:DEMD_YRMN_1, 1, 6)  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("DEMD_YRMN_1", DEMD_YRMN_1);               //û�� ���
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("OCR_PRNT", OCR_PRNT);               //���� ��±���
        return sobj;
    }
    //##**$$demd_ocr_paperprnt
    //##**$$send_mail_demd
    /*
    */
    public DOBJ CTLsend_mail_demd(DOBJ dobj)
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
            dobj  = CALLsend_mail_demd_MPD1(Conn, dobj);           //  �б�
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
    public DOBJ CTLsend_mail_demd( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsend_mail_demd_MPD1(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLsend_mail_demd_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("EMAIL_ADDR").equals(""))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsend_mail_demd_XIUD4(Conn, dobj);           //  ���� ť ����
            }
        }
        return dobj;
    }
    // ���� ť ����
    public DOBJ CALLsend_mail_demd_XIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD4");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsend_mail_demd_XIUD4(dobj, dvobj);
            qexe.DispSelectSql(sobj);
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
    private SQLObject SQLsend_mail_demd_XIUD4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("R").getRecord().get("DEMD_YRMN");   //û�� ���
        String   DMND_PRT_YN = dobj.getRetObject("R").getRecord().get("DMND_PRT_YN");   //���� ��� ����
        String   EMAIL_ADDR = dobj.getRetObject("R").getRecord().get("EMAIL_ADDR");   //�̸��� �ּ�
        String   MAP_CONTENT = dobj.getRetObject("R").getRecord().get("MAP_CONTENT");   //������
        String   NAME = dobj.getRetObject("R").getRecord().get("B_UPSO_NM");   //�̸�
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   UPSO_NM = dobj.getRetObject("R").getRecord().get("UPSO_NM");   //���� ��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO AMAIL.EMS_MAILQUEUE (SEQ, MAIL_CODE, TO_EMAIL, TO_NAME, FROM_EMAIL, FROM_NAME, SUBJECT, TARGET_FLAG, REG_DATE, MAP1, MAP2, MAP3, MAP4, MAP_CONTENT) SELECT AMAIL.EMS_MAILQUEUE_SEQ.NEXTVAL , '45' , :EMAIL_ADDR , (CASE WHEN :UPSO_NM IS NULL THEN :NAME ELSE :UPSO_NM END) , 'webmaster@komca.or.kr' , '(��)�ѱ��������۱���ȸ' , DECODE(:DMND_PRT_YN, 'Y', '[����] ', '') || SUBSTR(:DEMD_YRMN, 1, 4) || '�� ' || SUBSTR(:DEMD_YRMN, 5, 2) || '�� ' || :UPSO_NM || ' �������۱ǻ��� û����' AS SUBJECT , 'N' , SYSDATE , :UPSO_CD , :DEMD_YRMN , SUBSTR(:DEMD_YRMN, 1, 4) || '�� ' || SUBSTR(:DEMD_YRMN, 5, 2) || '�� ' || :UPSO_NM AS SUB_NAME , DECODE(:DMND_PRT_YN, 'Y', '[����] ', '') , :MAP_CONTENT FROM DUAL";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("DMND_PRT_YN", DMND_PRT_YN);               //���� ��� ����
        sobj.setString("EMAIL_ADDR", EMAIL_ADDR);               //�̸��� �ּ�
        sobj.setString("MAP_CONTENT", MAP_CONTENT);               //������
        sobj.setString("NAME", NAME);               //�̸�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
        return sobj;
    }
    //##**$$send_mail_demd
    //##**$$end
}
