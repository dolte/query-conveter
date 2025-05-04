
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
    /* * 프로그램명 : bra03_s01
    * 작성자 : 박태현
    * 작성일 : 2009/09/22
    * 설명    : 지로용지 출력을 위한 지로내역을 조회한다.
    입력된 청구월수에 따라 지로 출력 / 독촉 출력으로 구분된다.
    신규 업소의 경우는 독촉 출력 없이 3개월 이상 미납 시 최고서가 발송된다.
    Input
    BRAN_CD (지부 코드)
    DEMD_YRMN (청구 년월)
    DMND_PRT_YN (독촉 출력 여부)
    END_CNT_MON (종료 월수)
    START_CNT_MON (시작 월수)
    * 수정일 : 2010/06/10
    * 수정자 : 권남균
    * 수정내용 : 휴업 등으로 인하여 청구내역은 발생되고, 청구금액은 발생되지 않는 내역을 제외하기 위하여
    청구금액이 0보다 클 경우만 조회되게 수정함.
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
                dobj  = CALLdemd_ocr_select_SEL1(Conn, dobj);           //  독촉 청구결과 조회
                dobj  = CALLdemd_ocr_select_MRG5( dobj);        //  결과 취합
            }
            else
            {
                dobj  = CALLdemd_ocr_select_SEL6(Conn, dobj);           //  일반 청구결과 조회
                dobj  = CALLdemd_ocr_select_MRG5( dobj);        //  결과 취합
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
            dobj  = CALLdemd_ocr_select_SEL1(Conn, dobj);           //  독촉 청구결과 조회
            dobj  = CALLdemd_ocr_select_MRG5( dobj);        //  결과 취합
        }
        else
        {
            dobj  = CALLdemd_ocr_select_SEL6(Conn, dobj);           //  일반 청구결과 조회
            dobj  = CALLdemd_ocr_select_MRG5( dobj);        //  결과 취합
        }
        return dobj;
    }
    // 독촉 청구결과 조회
    // 해당 년월의 조회조건 (연체월, 독촉출력) 에 따라 청구결과를 조회한다  신규업소는 독촉 출력에서 제외된다. 신규업소의 경우 독촉 출력 없이 최고서가 발송된다. 신규업소 : 개업일은 있으나 신규일이 없는 업소 (한번도 입금되지 않은 업소)
    public DOBJ CALLdemd_ocr_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
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
        double   START_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("START_CNT_MON");   //시작 월수
        double   END_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("END_CNT_MON");   //종료 월수
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BRAN_CD  ,  B.DEMD_YRMN  ,  A.UPSO_CD  ,  DECODE(A.MAIL_RCPT,  'U',  REPLACE(REPLACE(A.UPSO_NM,  CHR(13),  ''),  CHR(10),  ''),  '')  AS  UPSO_NM  ,  REPLACE(REPLACE(A.UPSO_NM,  CHR(13),  ''),  CHR(10),  '')  AS  B_UPSO_NM  ,  A.MCHNDAESU  ,  B.START_YRMN  ,  B.END_YRMN  ,  (CASE  WHEN  B.TOT_DEMD_AMT  -  (B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT)  -  (B.MONPRNCFEE  +  NVL((SELECT  SUM(NVL(DEMD_AMT,  0))  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  B.DEMD_YRMN),  0))  <  0  THEN  0  ELSE  B.TOT_DEMD_AMT  -  (B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT)  -  (B.MONPRNCFEE  +  NVL((SELECT  SUM(NVL(DEMD_AMT,  0))  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  B.DEMD_YRMN),  0))  END)  AS  NONPY_AMT  ,  B.TOT_ADDT_AMT  +  B.TOT_EADDT_AMT  AS  ADDT_AMT  ,  B.MONPRNCFEE  +  NVL((SELECT  SUM(NVL(DEMD_AMT,  0))  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  B.DEMD_YRMN),  0)  AS  MONPRNCFEE  ,  (CASE  MAIL_RCPT  WHEN  'U'  THEN  A.UPSO_NEW_ZIP  WHEN  'B'  THEN  A.MNGEMSTR_NEW_ZIP  ELSE  A.PERMMSTR_NEW_ZIP  END)  AS  UPSO_ZIP  ,  (CASE  MAIL_RCPT  WHEN  'U'  THEN  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  WHEN  'B'  THEN  A.MNGEMSTR_NEW_ADDR1  ||  DECODE(A.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||A.MNGEMSTR_NEW_ADDR2)  ||  A.MNGEMSTR_REF_INFO  ELSE  A.PERMMSTR_NEW_ADDR1  ||  DECODE(A.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||A.PERMMSTR_NEW_ADDR2)  ||  A.PERMMSTR_REF_INFO  END)  AS  UPSO_ADDR  ,  A.CLIENT_NUM  ,  C.DEPT_NM  ,  D.POST_NUM  AS  BRAN_POST_NUM  ,  D.ADDR  ||  '  '  ||  HNM  AS  BRAN_ADDR  ,  D.PHON_NUM  AS  BRAN_PHON_NUM  ,  D.FAX_NUM  AS  BRAN_FAX_NUM  ,  ''  AS  OCR_NO  ,  '1'  AS  CHK_PRT  ,  DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_NM,  A.PERMMSTR_NM)  AS  MNGEMSTR_NM  ,  B.DEMD_MMCNT  ,  B.TOT_DEMD_AMT  AS  DEMD_AMT  ,  B.DSCT_AMT  ,  DECODE(DSCT_AMT,  0,  '',  '<월정료  30%할인  업소>')  AS  REMAK  ,   \n";
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
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //시작 월수
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //종료 월수
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 결과 취합
    // 일반지로 출력, 독촉 출력의 결과를 취합한다
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
    // 일반 청구결과 조회
    // 해당 년월의 조회조건 (연체월, 독촉출력, 개업출력) 에 따라 청구결과를 조회한다
    public DOBJ CALLdemd_ocr_select_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
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
        double   START_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("START_CNT_MON");   //시작 월수
        double   END_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("END_CNT_MON");   //종료 월수
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.BRAN_CD  AS  BRAN_CD  ,  DEMD_YRMN  ,  TA.UPSO_CD  ,  REPLACE(REPLACE(UPSO_NM,  CHR(13),  ''),  CHR(10),  '')  AS  UPSO_NM  ,  REPLACE(REPLACE(B_UPSO_NM,  CHR(13),  ''),  CHR(10),  '')  AS  B_UPSO_NM  ,  MCHNDAESU  ,  START_YRMN  ,  END_YRMN  ,  CASE  WHEN  NONPY_AMT  <0  THEN  0  ELSE  NONPY_AMT  END  NONPY_AMT  ,  ADDT_AMT  ,  MONPRNCFEE  ,  UPSO_ZIP  ,  UPSO_ADDR  ,  CLIENT_NUM  ,  DEPT_NM  ,  BRAN_POST_NUM  ,  BRAN_ADDR  ,  BRAN_PHON_NUM  ,  BRAN_FAX_NUM  ,  OCR_NO  ,  CHK_PRT  ,  MNGEMSTR_NM  ,  DEMD_MMCNT  ,  DEMD_AMT  ,  DSCT_AMT  ,  DECODE(DSCT_AMT,  0,  '',  '<월정료  30%할인  업소>')  REMAK  ,   \n";
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
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //시작 월수
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //종료 월수
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$demd_ocr_select
    //##**$$demd_ocr_paperprnt
    /* * 프로그램명 : bra03_s01
    * 작성자 : 박태현
    * 작성일 : 2009/10/13
    * 설명    : 지로/독촉이 출력 된 경우 출력결과를 저장한다.
    Input
    ADDT_AMT (가산 금액)
    BEFORE_UPSO_NM (변경전업소명)
    BRAN_ADDR (지부 주소)
    BRAN_CD (지부 코드)
    BRAN_FAX_NUM (지부 팩스번호)
    BRAN_PHON_NUM (지부 전화번호)
    BRAN_POST_NUM (지부 우편번호)
    CHK_PRT (출력 여부)
    CLIENT_NUM (고객 번호)
    DEMD_AMT (청구 금액)
    DEMD_YRMN (청구 년월)
    DEPT_NM (부서 명)
    END_YRMN (종료년월)
    IUDFLAG (레코드상태구분)
    MNGEMSTR_NM (경영주 이름)
    MONPRNCFEE (월정료)
    NONPY_AMT (미납 금액)
    OCR_GBN (OCR_GBN)
    OCR_NO (지로번호)
    START_YRMN (시작년월)
    UPSO_ADDR (UPSO_ADDR)
    UPSO_CD (업소 코드)
    UPSO_NM (업소 명)
    UPSO_ZIP (업소 우편번호)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
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
            dobj  = CALLdemd_ocr_paperprnt_MIUD6(Conn, dobj);           //  관리
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
        dobj  = CALLdemd_ocr_paperprnt_MIUD6(Conn, dobj);           //  관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 관리
    public DOBJ CALLdemd_ocr_paperprnt_MIUD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD6");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
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
                    dobj  = CALLdemd_ocr_paperprnt_UNI15(Conn, dobj);           //  결과 저장
                    dobj  = CALLdemd_ocr_paperprnt_UPD16(Conn, dobj);           //  지로/독촉 저장
                }
            }
        }
        return dobj;
    }
    // NLL
    // 조건 분기를 위한 select
    public DOBJ CALLdemd_ocr_paperprnt_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
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
    // 결과 저장
    // OCR 출력된 결과를 저장한다
    public DOBJ CALLdemd_ocr_paperprnt_UNI15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI15");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
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
        String DEMD_YRMN ="";        //청구 년
        String INS_DATE ="";        //등록 일시
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //미납 금액
        String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //업소 우편번호
        String   BRAN_ADDR = dvobj.getRecord().get("BRAN_ADDR");   //지부 주소
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //종료년월
        String   UPSO_NM = dvobj.getRecord().get("UPSO_NM");   //업소 명
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   CLIENT_NUM = dvobj.getRecord().get("CLIENT_NUM");   //고객 번호
        String   BRAN_NM = dvobj.getRecord().get("BRAN_NM");   //지부 이름
        String   OCR_NO = dvobj.getRecord().get("OCR_NO");   //지로번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_ADDR = dvobj.getRecord().get("UPSO_ADDR");   //업소 주소
        double   TOT_DEMD_AMT = dvobj.getRecord().getDouble("TOT_DEMD_AMT");   //총 청구 금액
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //총 가산 금액
        String   DEMD_YRMN_1 = dobj.getRetObject("R").getRecord().get("DEMD_YRMN");   //청구 년월
        String   OCR_GBN = dvobj.getRecord().get("OCR_GBN");
        String   BRAN_FAX = dvobj.getRecord().get("BRAN_FAX");
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //시작년월
        String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //경영주 이름
        String   BEFORE_UPSO_NM = dvobj.getRecord().get("BEFORE_UPSO_NM");   //변경전업소명
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //월정료
        String   BRAN_ZIP = dvobj.getRecord().get("BRAN_ZIP");   //지부 우편번호
        String   BRAN_PHON = dvobj.getRecord().get("BRAN_PHON");   //지부 전화
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_OCR_PRNT_RSLT  \n";
        query +=" set INSPRES_ID=:INSPRES_ID , BRAN_PHON=:BRAN_PHON , BRAN_ZIP=:BRAN_ZIP , MONPRNCFEE=:MONPRNCFEE ,  \n";
        query +=" BEFORE_UPSO_NM=:BEFORE_UPSO_NM , MNGEMSTR_NM=:MNGEMSTR_NM , START_YRMN=:START_YRMN , INS_DATE=SYSDATE , BRAN_FAX=:BRAN_FAX , OCR_GBN=:OCR_GBN , TOT_ADDT_AMT=:TOT_ADDT_AMT , TOT_DEMD_AMT=:TOT_DEMD_AMT , UPSO_ADDR=:UPSO_ADDR , OCR_NO=:OCR_NO , BRAN_NM=:BRAN_NM , CLIENT_NUM=:CLIENT_NUM , BRAN_CD=:BRAN_CD , UPSO_NM=:UPSO_NM , END_YRMN=:END_YRMN , BRAN_ADDR=:BRAN_ADDR , UPSO_ZIP=:UPSO_ZIP , NONPY_AMT=:NONPY_AMT  \n";
        query +=" where DEMD_YRMN=SUBSTR(:DEMD_YRMN_1, 1, 6)  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //미납 금액
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //업소 우편번호
        sobj.setString("BRAN_ADDR", BRAN_ADDR);               //지부 주소
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("CLIENT_NUM", CLIENT_NUM);               //고객 번호
        sobj.setString("BRAN_NM", BRAN_NM);               //지부 이름
        sobj.setString("OCR_NO", OCR_NO);               //지로번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("UPSO_ADDR", UPSO_ADDR);               //업소 주소
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //총 청구 금액
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //총 가산 금액
        sobj.setString("DEMD_YRMN_1", DEMD_YRMN_1);               //청구 년월
        sobj.setString("OCR_GBN", OCR_GBN);
        sobj.setString("BRAN_FAX", BRAN_FAX);
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //경영주 이름
        sobj.setString("BEFORE_UPSO_NM", BEFORE_UPSO_NM);               //변경전업소명
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //월정료
        sobj.setString("BRAN_ZIP", BRAN_ZIP);               //지부 우편번호
        sobj.setString("BRAN_PHON", BRAN_PHON);               //지부 전화
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    private SQLObject SQLdemd_ocr_paperprnt_UNI15INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String DEMD_YRMN ="";        //청구 년
        String INS_DATE ="";        //등록 일시
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //미납 금액
        String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //업소 우편번호
        String   BRAN_ADDR = dvobj.getRecord().get("BRAN_ADDR");   //지부 주소
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //종료년월
        String   UPSO_NM = dvobj.getRecord().get("UPSO_NM");   //업소 명
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   CLIENT_NUM = dvobj.getRecord().get("CLIENT_NUM");   //고객 번호
        String   BRAN_NM = dvobj.getRecord().get("BRAN_NM");   //지부 이름
        String   OCR_NO = dvobj.getRecord().get("OCR_NO");   //지로번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_ADDR = dvobj.getRecord().get("UPSO_ADDR");   //업소 주소
        double   TOT_DEMD_AMT = dvobj.getRecord().getDouble("TOT_DEMD_AMT");   //총 청구 금액
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //총 가산 금액
        String   DEMD_YRMN_1 = dobj.getRetObject("R").getRecord().get("DEMD_YRMN");   //청구 년월
        String   OCR_GBN = dvobj.getRecord().get("OCR_GBN");
        String   BRAN_FAX = dvobj.getRecord().get("BRAN_FAX");
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //시작년월
        String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //경영주 이름
        String   BEFORE_UPSO_NM = dvobj.getRecord().get("BEFORE_UPSO_NM");   //변경전업소명
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //월정료
        String   BRAN_ZIP = dvobj.getRecord().get("BRAN_ZIP");   //지부 우편번호
        String   BRAN_PHON = dvobj.getRecord().get("BRAN_PHON");   //지부 전화
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_OCR_PRNT_RSLT (INSPRES_ID, BRAN_PHON, BRAN_ZIP, MONPRNCFEE, BEFORE_UPSO_NM, MNGEMSTR_NM, START_YRMN, INS_DATE, BRAN_FAX, OCR_GBN, DEMD_YRMN, TOT_ADDT_AMT, TOT_DEMD_AMT, UPSO_ADDR, UPSO_CD, OCR_NO, BRAN_NM, CLIENT_NUM, BRAN_CD, UPSO_NM, END_YRMN, BRAN_ADDR, UPSO_ZIP, NONPY_AMT)  \n";
        query +=" values(:INSPRES_ID , :BRAN_PHON , :BRAN_ZIP , :MONPRNCFEE , :BEFORE_UPSO_NM , :MNGEMSTR_NM , :START_YRMN , SYSDATE, :BRAN_FAX , :OCR_GBN , SUBSTR(:DEMD_YRMN_1, 1, 6), :TOT_ADDT_AMT , :TOT_DEMD_AMT , :UPSO_ADDR , :UPSO_CD , :OCR_NO , :BRAN_NM , :CLIENT_NUM , :BRAN_CD , :UPSO_NM , :END_YRMN , :BRAN_ADDR , :UPSO_ZIP , :NONPY_AMT )";
        sobj.setSql(query);
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //미납 금액
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //업소 우편번호
        sobj.setString("BRAN_ADDR", BRAN_ADDR);               //지부 주소
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("CLIENT_NUM", CLIENT_NUM);               //고객 번호
        sobj.setString("BRAN_NM", BRAN_NM);               //지부 이름
        sobj.setString("OCR_NO", OCR_NO);               //지로번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("UPSO_ADDR", UPSO_ADDR);               //업소 주소
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //총 청구 금액
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //총 가산 금액
        sobj.setString("DEMD_YRMN_1", DEMD_YRMN_1);               //청구 년월
        sobj.setString("OCR_GBN", OCR_GBN);
        sobj.setString("BRAN_FAX", BRAN_FAX);
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //경영주 이름
        sobj.setString("BEFORE_UPSO_NM", BEFORE_UPSO_NM);               //변경전업소명
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //월정료
        sobj.setString("BRAN_ZIP", BRAN_ZIP);               //지부 우편번호
        sobj.setString("BRAN_PHON", BRAN_PHON);               //지부 전화
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 지로/독촉 저장
    // 청구 테이블에 지로인지 독촉인지 저장한다
    public DOBJ CALLdemd_ocr_paperprnt_UPD16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD16");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
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
        String DEMD_YRMN ="";        //청구 년
        String MOD_DATE ="";        //수정 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   DEMD_YRMN_1 = dobj.getRetObject("R").getRecord().get("DEMD_YRMN");   //청구 년월
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   OCR_PRNT = dobj.getRetObject("R").getRecord().get("OCR_GBN");   //지로 출력구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_DEMD_OCR  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , OCR_PRNT=:OCR_PRNT , MOD_DATE=SYSDATE  \n";
        query +=" where DEMD_YRMN=SUBSTR(:DEMD_YRMN_1, 1, 6)  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("DEMD_YRMN_1", DEMD_YRMN_1);               //청구 년월
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("OCR_PRNT", OCR_PRNT);               //지로 출력구분
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
            dobj  = CALLsend_mail_demd_MPD1(Conn, dobj);           //  분기
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
        dobj  = CALLsend_mail_demd_MPD1(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLsend_mail_demd_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("EMAIL_ADDR").equals(""))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsend_mail_demd_XIUD4(Conn, dobj);           //  메일 큐 삽입
            }
        }
        return dobj;
    }
    // 메일 큐 삽입
    public DOBJ CALLsend_mail_demd_XIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD4");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
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
        String   DEMD_YRMN = dobj.getRetObject("R").getRecord().get("DEMD_YRMN");   //청구 년월
        String   DMND_PRT_YN = dobj.getRetObject("R").getRecord().get("DMND_PRT_YN");   //독촉 출력 여부
        String   EMAIL_ADDR = dobj.getRetObject("R").getRecord().get("EMAIL_ADDR");   //이메일 주소
        String   MAP_CONTENT = dobj.getRetObject("R").getRecord().get("MAP_CONTENT");   //컨텐츠
        String   NAME = dobj.getRetObject("R").getRecord().get("B_UPSO_NM");   //이름
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_NM = dobj.getRetObject("R").getRecord().get("UPSO_NM");   //업소 명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO AMAIL.EMS_MAILQUEUE (SEQ, MAIL_CODE, TO_EMAIL, TO_NAME, FROM_EMAIL, FROM_NAME, SUBJECT, TARGET_FLAG, REG_DATE, MAP1, MAP2, MAP3, MAP4, MAP_CONTENT) SELECT AMAIL.EMS_MAILQUEUE_SEQ.NEXTVAL , '45' , :EMAIL_ADDR , (CASE WHEN :UPSO_NM IS NULL THEN :NAME ELSE :UPSO_NM END) , 'webmaster@komca.or.kr' , '(사)한국음악저작권협회' , DECODE(:DMND_PRT_YN, 'Y', '[독촉] ', '') || SUBSTR(:DEMD_YRMN, 1, 4) || '년 ' || SUBSTR(:DEMD_YRMN, 5, 2) || '월 ' || :UPSO_NM || ' 음악저작권사용료 청구서' AS SUBJECT , 'N' , SYSDATE , :UPSO_CD , :DEMD_YRMN , SUBSTR(:DEMD_YRMN, 1, 4) || '년 ' || SUBSTR(:DEMD_YRMN, 5, 2) || '월 ' || :UPSO_NM AS SUB_NAME , DECODE(:DMND_PRT_YN, 'Y', '[독촉] ', '') , :MAP_CONTENT FROM DUAL";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("DMND_PRT_YN", DMND_PRT_YN);               //독촉 출력 여부
        sobj.setString("EMAIL_ADDR", EMAIL_ADDR);               //이메일 주소
        sobj.setString("MAP_CONTENT", MAP_CONTENT);               //컨텐츠
        sobj.setString("NAME", NAME);               //이름
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        return sobj;
    }
    //##**$$send_mail_demd
    //##**$$end
}
