
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s01_1
{
    public bra03_s01_1()
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
        query +=" SELECT  A.BRAN_CD  ,  B.DEMD_YRMN  ,  A.UPSO_CD  ,  DECODE(A.MAIL_RCPT,  'U',  REPLACE(REPLACE(A.UPSO_NM,  CHR(13),  ''),  CHR(10),  ''),  '')  AS  UPSO_NM  ,  REPLACE(REPLACE(A.UPSO_NM,  CHR(13),  ''),  CHR(10),  '')  AS  B_UPSO_NM  ,  A.MCHNDAESU  ,  B.START_YRMN  ,  B.END_YRMN  ,  (CASE  WHEN  B.TOT_DEMD_AMT  -  (B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT)  -  (NVL((SELECT  USE_AMT  FROM  GIBU.TBRA_DEMD_OCR_MM  WHERE  DEMD_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  START_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  UPSO_CD  =  A.UPSO_CD),  0))  <  0  THEN  0  ELSE  B.TOT_DEMD_AMT  -  (B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT)  -  (NVL((SELECT  USE_AMT  FROM  GIBU.TBRA_DEMD_OCR_MM  WHERE  DEMD_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  START_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  UPSO_CD  =  A.UPSO_CD),  0))  END)  AS  NONPY_AMT  ,  B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT  AS  ADDT_AMT  ,  NVL((SELECT  USE_AMT  FROM  GIBU.TBRA_DEMD_OCR_MM  WHERE  DEMD_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  START_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  UPSO_CD  =  A.UPSO_CD),  0)  AS  MONPRNCFEE  ,  (CASE  MAIL_RCPT  WHEN  'U'  THEN  A.UPSO_NEW_ZIP  WHEN  'B'  THEN  A.MNGEMSTR_NEW_ZIP  ELSE  A.PERMMSTR_NEW_ZIP  END)  AS  UPSO_ZIP  ,  (CASE  MAIL_RCPT  WHEN  'U'  THEN  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '  ||  A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  WHEN  'B'  THEN  A.MNGEMSTR_NEW_ADDR1  ||  DECODE(A.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  A.MNGEMSTR_NEW_ADDR2)  ||  A.MNGEMSTR_REF_INFO  ELSE  A.PERMMSTR_NEW_ADDR1  ||  DECODE(A.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  A.PERMMSTR_NEW_ADDR2)  ||  A.PERMMSTR_REF_INFO  END)  AS  UPSO_ADDR  ,  A.CLIENT_NUM  ,  C.DEPT_NM  ,  D.POST_NUM  AS  BRAN_POST_NUM  ,  D.ADDR  ||  '  '  ||  HNM  AS  BRAN_ADDR  ,  GIBU.FT_GET_PHONE_FORMAT(E.IPPBX_INPHONE_NUM)  AS  BRAN_PHON_NUM  ,  GIBU.FT_GET_PHONE_FORMAT(E.IPPBX_USER_ID)  AS  BRAN_FAX_NUM  ,  ''  AS  OCR_NO  ,  '1'  AS  CHK_PRT  ,  DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_NM,  A.PERMMSTR_NM)  AS  MNGEMSTR_NM  ,  B.DEMD_MMCNT  ,  B.TOT_DEMD_AMT  AS  DEMD_AMT  ,  B.DSCT_AMT  ,  DECODE(DSCT_AMT,  0,  '',  '<������  30%����  ����>')  AS  REMAK  ,   \n";
        query +=" (SELECT  ACCOUNT_NM  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  BANK_CODE  =  '0'  ||   \n";
        query +=" (SELECT  BANK_CD  FROM  GIBU.TFMS_BANK  WHERE  BRAN_CD  IS  NULL))  AS  REPTPRES  ,   \n";
        query +=" (SELECT  LISTAGG((SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  BANK_CD  =  BANK_CODE)  ||  '  :  '  ||  GIBU.FT_GET_ACCOUNT_FORMAT(VIRTUAL_ACCOUNT_NUM),  CHR(13)  ||  CHR(10))  WITHIN  GROUP  (ORDER  BY  BANK_CODE)  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y')  AS  VIRTUAL_ACCN  ,  REPLACE(REPLACE(DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_HPNUM,  NVL(A.PERMMSTR_HPNUM,  A.MNGEMSTR_HPNUM)),  '-',  ''),  '  ',  '')  AS  CP_NUM  ,   \n";
        query +=" (SELECT  GBN  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  GBN  IS  NOT  NULL)  AS  SEND_TYPE  ,  REPLACE(REPLACE(TRIM(A.EMAIL_ADDR),  CHR(13),  ''),  CHR(10),  '')  AS  EMAIL_ADDR  ,  A.PAPER_CANC_YN  ,  GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD)  AS  BSTYP_CD  ,   \n";
        query +=" (SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =  GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD))  AS  BSTYP_NM  ,  GIBU.FT_GET_AUTO_CARD_USE(A.UPSO_CD)  AUTO_CARD_USE  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_DEMD_OCR  B  ,  INSA.TCPM_DEPT  C  ,  INSA.TCPM_BIPLC  D  ,  FIDU.TENV_MEMBER  E  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  NVL(B.TOT_DEMD_AMT,  0)  >  0   \n";
        query +=" AND  B.DEMD_MMCNT  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON   \n";
        query +=" AND  C.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  D.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  B.DEMD_GBN  =  '32'   \n";
        query +=" AND  A.PAPER_CANC_YN  =  'N'   \n";
        query +=" AND  A.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  B.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  B.BSTYP_CD,  :BSTYP_CD)   \n";
        query +=" AND  A.STAFF_CD  =  E.USER_ID  ORDER  BY  UPSO_NEW_ZIP ";
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
        query +=" SELECT  TA.BRAN_CD  AS  BRAN_CD  ,  DEMD_YRMN  ,  TA.UPSO_CD  ,  REPLACE(REPLACE(UPSO_NM,  CHR(13),  ''),  CHR(10),  '')  AS  UPSO_NM  ,  REPLACE(REPLACE(B_UPSO_NM,  CHR(13),  ''),  CHR(10),  '')  AS  B_UPSO_NM  ,  MCHNDAESU  ,  START_YRMN  ,  END_YRMN  ,  (CASE  WHEN  NONPY_AMT  <  0  THEN  0  ELSE  NONPY_AMT  END)  AS  NONPY_AMT  ,  ADDT_AMT  ,  MONPRNCFEE  ,  UPSO_ZIP  ,  UPSO_ADDR  ,  CLIENT_NUM  ,  DEPT_NM  ,  BRAN_POST_NUM  ,  BRAN_ADDR  ,  BRAN_PHON_NUM  ,  BRAN_FAX_NUM  ,  OCR_NO  ,  CHK_PRT  ,  MNGEMSTR_NM  ,  DEMD_MMCNT  ,  DEMD_AMT  ,  DSCT_AMT  ,  DECODE(DSCT_AMT,  0,  '',  '<������  30%����  ����>')  AS  REMAK  ,   \n";
        query +=" (SELECT  ACCOUNT_NM  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  BANK_CODE  =  '0'  ||   \n";
        query +=" (SELECT  BANK_CD  FROM  GIBU.TFMS_BANK  WHERE  BRAN_CD  IS  NULL   \n";
        query +=" AND  REMAK  =  'Y'))  AS  REPTPRES  ,   \n";
        query +=" (SELECT  LISTAGG((SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  BANK_CD  =  BANK_CODE)  ||  '  :  '  ||  GIBU.FT_GET_ACCOUNT_FORMAT(VIRTUAL_ACCOUNT_NUM),  CHR(13)  ||  CHR(10))  WITHIN  GROUP  (ORDER  BY  BANK_CODE)  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y')  AS  VIRTUAL_ACCN  ,  CP_NUM  ,   \n";
        query +=" (SELECT  GBN  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  GBN  IS  NOT  NULL)  AS  SEND_TYPE  ,  REPLACE(REPLACE(TRIM(EMAIL_ADDR),  CHR(13),  ''),  CHR(10),  '')  AS  EMAIL_ADDR  ,  PAPER_CANC_YN  ,  GIBU.FT_GET_BSTYP_INFO(TA.UPSO_CD)  AS  BSTYP_CD  ,   \n";
        query +=" (SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =  GIBU.FT_GET_BSTYP_INFO(TA.UPSO_CD))  AS  BSTYP_NM  ,  AUTO_CARD_USE  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  B.DEMD_YRMN  ,  A.UPSO_CD  ,  DECODE(A.MAIL_RCPT,  'U',  A.UPSO_NM,  '')  AS  UPSO_NM  ,  A.UPSO_NM  AS  B_UPSO_NM  ,  A.MCHNDAESU  ,  B.START_YRMN  ,  B.END_YRMN  ,  B.TOT_DEMD_AMT  -  (B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT)  -  (NVL((SELECT  USE_AMT  FROM  GIBU.TBRA_DEMD_OCR_MM  WHERE  DEMD_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  START_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  UPSO_CD  =  A.UPSO_CD),  0))  AS  NONPY_AMT  ,  B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT  AS  ADDT_AMT  ,  NVL((SELECT  USE_AMT  FROM  GIBU.TBRA_DEMD_OCR_MM  WHERE  DEMD_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  START_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  UPSO_CD  =  A.UPSO_CD),  0)  AS  MONPRNCFEE  ,  (CASE  MAIL_RCPT  WHEN  'U'  THEN  A.UPSO_NEW_ZIP  WHEN  'B'  THEN  A.MNGEMSTR_NEW_ZIP  ELSE  A.PERMMSTR_NEW_ZIP  END)  AS  UPSO_ZIP  ,  (CASE  MAIL_RCPT  WHEN  'U'  THEN  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '  ||  A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  WHEN  'B'  THEN  A.MNGEMSTR_NEW_ADDR1  ||  DECODE(A.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  A.MNGEMSTR_NEW_ADDR2)  ||  A.MNGEMSTR_REF_INFO  ELSE  A.PERMMSTR_NEW_ADDR1  ||  DECODE(A.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  A.PERMMSTR_NEW_ADDR2)  ||  A.PERMMSTR_REF_INFO  END)  AS  UPSO_ADDR  ,  A.CLIENT_NUM  ,  C.DEPT_NM  ,  D.POST_NUM  AS  BRAN_POST_NUM  ,  D.ADDR  ||  '  '  ||  HNM  AS  BRAN_ADDR  ,  GIBU.FT_GET_PHONE_FORMAT(E.IPPBX_INPHONE_NUM)  AS  BRAN_PHON_NUM  ,  GIBU.FT_GET_PHONE_FORMAT(E.IPPBX_USER_ID)  AS  BRAN_FAX_NUM  ,  ''  AS  OCR_NO  ,  '1'  AS  CHK_PRT  ,  DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_NM,  A.PERMMSTR_NM)  AS  MNGEMSTR_NM  ,  REPLACE(REPLACE(DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_HPNUM,  NVL(A.PERMMSTR_HPNUM,  A.MNGEMSTR_HPNUM)),  '-',  ''),  '  ',  '')  AS  CP_NUM  ,  B.DEMD_MMCNT  ,  B.TOT_DEMD_AMT  AS  DEMD_AMT  ,  B.DSCT_AMT  ,  A.EMAIL_ADDR  ,  A.PAPER_CANC_YN  ,  A.STAFF_CD  ,  GIBU.FT_GET_AUTO_CARD_USE(A.UPSO_CD)  AUTO_CARD_USE  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_DEMD_OCR  B  ,  INSA.TCPM_DEPT  C  ,  INSA.TCPM_BIPLC  D  ,  FIDU.TENV_MEMBER  E  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  NVL(B.TOT_DEMD_AMT,  0)  >  0   \n";
        query +=" AND  B.DEMD_MMCNT  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON   \n";
        query +=" AND  C.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  D.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  B.DEMD_GBN  =  '32'   \n";
        query +=" AND  A.PAPER_CANC_YN  =  'N'   \n";
        query +=" AND  A.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  B.BSTYP_CD  =  DECODE(  :BSTYP_CD,  NULL,  B.BSTYP_CD,  :BSTYP_CD)   \n";
        query +=" AND  A.STAFF_CD  =  E.USER_ID  UNION  ALL   \n";
        query +=" SELECT  A.BRAN_CD  ,  B.DEMD_YRMN  ,  A.UPSO_CD  ,  DECODE(A.MAIL_RCPT,  'U',  A.UPSO_NM,  '')  AS  UPSO_NM  ,  A.UPSO_NM  AS  B_UPSO_NM  ,  A.MCHNDAESU  ,  B.START_YRMN  ,  B.END_YRMN  ,  B.TOT_DEMD_AMT  -  (B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT)  -  (NVL((SELECT  USE_AMT  FROM  GIBU.TBRA_DEMD_OCR_MM  WHERE  DEMD_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  START_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  UPSO_CD  =  A.UPSO_CD),  0))  AS  NONPY_AMT  ,  B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT  AS  ADDT_AMT  ,  NVL((SELECT  USE_AMT  FROM  GIBU.TBRA_DEMD_OCR_MM  WHERE  DEMD_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  START_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  UPSO_CD  =  A.UPSO_CD),  0)  AS  MONPRNCFEE  ,  (CASE  MAIL_RCPT  WHEN  'U'  THEN  A.UPSO_NEW_ZIP  WHEN  'B'  THEN  A.MNGEMSTR_NEW_ZIP  ELSE  A.PERMMSTR_NEW_ZIP  END)  AS  UPSO_ZIP  ,  (CASE  MAIL_RCPT  WHEN  'U'  THEN  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '  ||  A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  WHEN  'B'  THEN  A.MNGEMSTR_NEW_ADDR1  ||  DECODE(A.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  A.MNGEMSTR_NEW_ADDR2)  ||  A.MNGEMSTR_REF_INFO  ELSE  A.PERMMSTR_NEW_ADDR1  ||  DECODE(A.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '  ||  A.PERMMSTR_NEW_ADDR2)  ||  A.PERMMSTR_REF_INFO  END)  AS  UPSO_ADDR  ,  A.CLIENT_NUM  ,  C.DEPT_NM  ,  D.POST_NUM  AS  BRAN_POST_NUM  ,  D.ADDR  ||  '  '  ||  HNM  AS  BRAN_ADDR  ,  GIBU.FT_GET_PHONE_FORMAT(E.IPPBX_INPHONE_NUM)  AS  BRAN_PHON_NUM  ,  GIBU.FT_GET_PHONE_FORMAT(E.IPPBX_USER_ID)  AS  BRAN_FAX_NUM  ,  ''  AS  OCR_NO  ,  '1'  AS  CHK_PRT  ,  DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_NM,  A.PERMMSTR_NM)  AS  MNGEMSTR_NM  ,  REPLACE(REPLACE(DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_HPNUM,  NVL(A.PERMMSTR_HPNUM,  A.MNGEMSTR_HPNUM)),  '-',  ''),  '  ',  '')  AS  CP_NUM  ,  B.DEMD_MMCNT  ,  B.TOT_DEMD_AMT  ,  B.DSCT_AMT  ,  A.EMAIL_ADDR  ,  A.PAPER_CANC_YN  ,  A.STAFF_CD  ,  GIBU.FT_GET_AUTO_CARD_USE(A.UPSO_CD)  AUTO_CARD_USE  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  UPSO_CD  ,  UPSO_NM  ,  BEFORE_UPSO_CD  ,  MCHNDAESU  ,  UPSO_NEW_ZIP  ,  UPSO_NEW_ADDR1  ,  UPSO_NEW_ADDR2  ,  UPSO_REF_INFO  ,  MNGEMSTR_NEW_ZIP  ,  MNGEMSTR_NEW_ADDR1  ,  MNGEMSTR_NEW_ADDR2  ,  MNGEMSTR_REF_INFO  ,  MNGEMSTR_HPNUM  ,  PERMMSTR_NEW_ZIP  ,  PERMMSTR_NEW_ADDR1  ,  PERMMSTR_NEW_ADDR2  ,  PERMMSTR_REF_INFO  ,  PERMMSTR_HPNUM  ,  CLIENT_NUM  ,  MNGEMSTR_NM  ,  PERMMSTR_NM  ,  MAIL_RCPT  ,  PAYPRES_GBN  ,  MONTHS_BETWEEN(TO_DATE(  :DEMD_YRMN,  'YYYYMM'),  TO_DATE(TO_CHAR(INS_DATE,  'YYYYMM'),  'YYYYMM'))  AS  INS_MMCNT  ,  PAPER_CANC_YN  ,  EMAIL_ADDR  ,  STAFF_CD  FROM  GIBU.TBRA_UPSO  WHERE  NEW_DAY  IS  NULL  )  A  ,  GIBU.TBRA_DEMD_OCR  B  ,  INSA.TCPM_DEPT  C  ,  INSA.TCPM_BIPLC  D  ,  FIDU.TENV_MEMBER  E  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  NVL(B.TOT_DEMD_AMT,  0)  >  0   \n";
        query +=" AND  A.INS_MMCNT  BETWEEN  0   \n";
        query +=" AND  2   \n";
        query +=" AND  C.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  D.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  B.DEMD_GBN  =  '32'   \n";
        query +=" AND  A.PAPER_CANC_YN  =  'N'   \n";
        query +=" AND  B.BSTYP_CD  =  DECODE(  :BSTYP_CD,  NULL,  B.BSTYP_CD,  :BSTYP_CD)   \n";
        query +=" AND  A.STAFF_CD  =  E.USER_ID  )  TA  ORDER  BY  UPSO_CD ";
        sobj.setSql(query);
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //���� ����
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //���� ����
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$demd_ocr_select
    //##**$$end
}
