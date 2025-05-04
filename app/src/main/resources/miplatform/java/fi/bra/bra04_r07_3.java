
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_r07_3
{
    public bra04_r07_3()
    {
    }
    //##**$$nonpy_select_test1
    /*
    */
    public DOBJ CTLnonpy_select_test1(DOBJ dobj)
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
                dobj  = CALLnonpy_select_test1_SEL1(Conn, dobj);           //  미징수 내역조회
                dobj  = CALLnonpy_select_test1_MRG7( dobj);        //  결과 합치기
            }
            else if( dobj.getRetObject("S").getRecord().get("UPSO_TYPE").equals("02"))
            {
                dobj  = CALLnonpy_select_test1_SEL5(Conn, dobj);           //  미징수 내역조회
                dobj  = CALLnonpy_select_test1_MRG7( dobj);        //  결과 합치기
            }
            else
            {
                dobj  = CALLnonpy_select_test1_SEL6(Conn, dobj);           //  미징수 내역조회
                dobj  = CALLnonpy_select_test1_MRG7( dobj);        //  결과 합치기
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
    public DOBJ CTLnonpy_select_test1( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("UPSO_TYPE").equals("01"))
        {
            dobj  = CALLnonpy_select_test1_SEL1(Conn, dobj);           //  미징수 내역조회
            dobj  = CALLnonpy_select_test1_MRG7( dobj);        //  결과 합치기
        }
        else if( dobj.getRetObject("S").getRecord().get("UPSO_TYPE").equals("02"))
        {
            dobj  = CALLnonpy_select_test1_SEL5(Conn, dobj);           //  미징수 내역조회
            dobj  = CALLnonpy_select_test1_MRG7( dobj);        //  결과 합치기
        }
        else
        {
            dobj  = CALLnonpy_select_test1_SEL6(Conn, dobj);           //  미징수 내역조회
            dobj  = CALLnonpy_select_test1_MRG7( dobj);        //  결과 합치기
        }
        return dobj;
    }
    // 미징수 내역조회
    // 관리중인 업소의 미징수 내역을 조회한다
    public DOBJ CALLnonpy_select_test1_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLnonpy_select_test1_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnonpy_select_test1_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_ZIP = dvobj.getRecord().getDouble("START_ZIP");   //시작 우편번호
        double   START_CNT_MON = dvobj.getRecord().getDouble("START_CNT_MON");   //시작 월수
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        double   END_ZIP = dvobj.getRecord().getDouble("END_ZIP");   //종료 우편번호
        double   END_CNT_MON = dvobj.getRecord().getDouble("END_CNT_MON");   //종료 월수
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //청구 년월
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.DEMD_MMCNT  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  DECODE(XB.MNGEMSTR_RESINUM  ,  NULL,  '',  'OK')  CHK_RESINUM  ,  XB.MNGEMSTR_NM  ,  DECODE(TRIM(XA.BSTYP_CD),  'o',  GIBU.FT_GET_NOREBANG_GRAD(XB.UPSO_CD),  XC.GRADNM)  GRADNM  ,  DECODE  ((SELECT  NVL(COUNT(UPSO_CD),  0)  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL),  0,  '',  '채권')  BOND  ,  XA.MONPRNCFEE  ,  XA.MONPRNCFEE2  ,  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO  UPSO_ADDR  ,  XB.UPSO_PHON  ,  XB.MNGEMSTR_HPNUM  ,  XA.TOT_USE_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_USE_AMT  +  XA.TOT_ADDT_AMT  AS  TOT_AMT  ,  DECODE(XB.BEFORE_UPSO_CD,  NULL,  '',  '재'  ||  TO_CHAR(XB.INS_DATE,  'YYYYMMDD'))  ||  '  '  ||  NVL(XD.GOSO_YN,  '')  REMAK  ,  XB.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,   \n";
        query +=" (SELECT  ATTE||'  '||CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =XB.UPSO_BD_MNG_NUM)  AS  SIGUGUN  ,  SUBSTR(XA.START_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.START_YRMN,  5,  2)  START_YRMN  ,  SUBSTR(XA.END_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.END_YRMN,  5,  2)  END_YRMN  ,  XF.PHON_NUM  BRAN_PHON_NUM  ,  DECODE((SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  TERM_YN  =  'N'),0,'','OK')  AUTO_YN  ,  '0'  AS  COL_CHECK  ,   \n";
        query +=" (SELECT  DECODE(COURT_NM,  NULL,  TOWNTWSHP,  COURT_NM)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XB.UPSO_BD_MNG_NUM  )  AS  DONG  ,  XB.CLIENT_NUM  FROM  (   \n";
        query +=" SELECT  DEMD_GBN  ,  UPSO_CD  ,  DEMD_YRMN  ,  MONPRNCFEE  ,  MONPRNCFEE2  ,  BSTYP_CD  ,  UPSO_GRAD  ,  DEMD_MMCNT  ,  START_YRMN  ,  END_YRMN  ,  TOT_USE_AMT  ,  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  5)  MONPRNCFEE2  ,  GIBU.FT_SPLIT(DEMDS,  ',',  6)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT3(A.UPSO_CD,  :DEMD_YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  A.YRMN,  A.UPSO_CD  FROM  (   \n";
        query +=" SELECT  YRMN,  UPSO_CD,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_STAT  =  '1'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  :DEMD_YRMN  ||  '31'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_NEW_ZIP  BETWEEN  NVL(:START_ZIP,  UPSO_NEW_ZIP)   \n";
        query +=" AND  NVL(:END_ZIP,  UPSO_NEW_ZIP)  )  WHERE  YRMN  BETWEEN  OPBI_YRMN   \n";
        query +=" AND  to_char(add_months(to_date(:DEMD_YRMN,  'YYYYMM'),  1),  'YYYYMM')   \n";
        query +=" AND  OPBI_YRMN  <=  :DEMD_YRMN)  A  LEFT  JOIN  GIBU.TBRA_NOTE  B  ON  (A.YRMN  =  B.NOTE_YRMN   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD)  WHERE  B.NOTE_YRMN  IS  NULL  )  GROUP  BY  UPSO_CD  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  +1  )  A  )  )  )  XA  ,  GIBU.TBRA_UPSO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(GOSO_YN)  GOSO_YN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(COMPN_DAY,  NULL,  '고소중',  DECODE(JUDG_CD,  '2',  '기소중지',  '4',  '구약식',  NULL))  GOSO_YN  FROM  GIBU.TBRA_ACCU  WHERE  (JUDG_CD  NOT  IN  ('1','3','5','6','7','8','9','41','99')   \n";
        query +=" OR  JUDG_CD  IS  NULL)  )  GROUP  BY  UPSO_CD  )  XD  ,  INSA.TCPM_BIPLC  XF  WHERE  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XA.UPSO_GRAD   \n";
        query +=" AND  XC.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  XC.BSTYP_CD,  'A',  XC.BSTYP_CD,  :BSTYP_CD)   \n";
        query +=" AND  XC.BSTYP_CD  =  XA.BSTYP_CD   \n";
        query +=" AND  XD.UPSO_CD  (+)  =  XA.UPSO_CD   \n";
        query +=" AND  XF.GIBU  =  XB.BRAN_CD   \n";
        query +=" AND  XA.DEMD_MMCNT  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  ORDER  BY  SUBSTR(XB.UPSO_BD_MNG_NUM,1,5),  24,  XB.UPSO_NM ";
        sobj.setSql(query);
        sobj.setDouble("START_ZIP", START_ZIP);               //시작 우편번호
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //시작 월수
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setDouble("END_ZIP", END_ZIP);               //종료 우편번호
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //종료 월수
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 결과 합치기
    // 조건에 맞는 결과 데이타를 내보낸다
    public DOBJ CALLnonpy_select_test1_MRG7(DOBJ dobj) throws Exception
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
    // 미징수 내역조회
    // 개발중 업소의 미징수 내역을 조회한다
    public DOBJ CALLnonpy_select_test1_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLnonpy_select_test1_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.setRetcode(1);
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnonpy_select_test1_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_ZIP = dvobj.getRecord().getDouble("START_ZIP");   //시작 우편번호
        double   START_CNT_MON = dvobj.getRecord().getDouble("START_CNT_MON");   //시작 월수
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        double   END_ZIP = dvobj.getRecord().getDouble("END_ZIP");   //종료 우편번호
        double   END_CNT_MON = dvobj.getRecord().getDouble("END_CNT_MON");   //종료 월수
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //청구 년월
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.DEMD_MMCNT  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  DECODE(XB.MNGEMSTR_RESINUM  ,  NULL,  '',  'OK')  CHK_RESINUM  ,  XB.MNGEMSTR_NM  ,  DECODE(TRIM(XA.BSTYP_CD),  'o',  GIBU.FT_GET_NOREBANG_GRAD(XB.UPSO_CD),  XC.GRADNM)  GRADNM  ,  DECODE  ((SELECT  NVL(COUNT(UPSO_CD),  0)  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL),  0,  '',  '채권')  BOND  ,  XA.MONPRNCFEE  ,  XA.MONPRNCFEE2  ,  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO  UPSO_ADDR  ,  XB.UPSO_PHON  ,  XB.MNGEMSTR_HPNUM  ,  XA.TOT_USE_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_USE_AMT  +  XA.TOT_ADDT_AMT  AS  TOT_AMT  ,  DECODE(XB.BEFORE_UPSO_CD,  NULL,  '',  '재'  ||  TO_CHAR(XB.INS_DATE,  'YYYYMMDD'))  ||  '  '  ||  NVL(XD.GOSO_YN,  '')  REMAK  ,  XB.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,   \n";
        query +=" (SELECT  ATTE||'  '||CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =XB.UPSO_BD_MNG_NUM)  AS  SIGUGUN  ,  SUBSTR(XA.START_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.START_YRMN,  5,  2)  START_YRMN  ,  SUBSTR(XA.END_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.END_YRMN,  5,  2)  END_YRMN  ,  XF.PHON_NUM  BRAN_PHON_NUM  ,  DECODE((SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  TERM_YN  =  'N'),0,'','OK')  AUTO_YN  ,  '0'  AS  COL_CHECK  ,   \n";
        query +=" (SELECT  DECODE(COURT_NM,  NULL,  TOWNTWSHP,  COURT_NM)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XB.UPSO_BD_MNG_NUM  )  AS  DONG  ,  XB.CLIENT_NUM  FROM  (   \n";
        query +=" SELECT  DEMD_GBN  ,  UPSO_CD  ,  DEMD_YRMN  ,  MONPRNCFEE  ,  MONPRNCFEE2  ,  BSTYP_CD  ,  UPSO_GRAD  ,  DEMD_MMCNT  ,  START_YRMN  ,  END_YRMN  ,  TOT_USE_AMT  ,  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  5)  MONPRNCFEE2  ,  GIBU.FT_SPLIT(DEMDS,  ',',  6)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT3(A.UPSO_CD,  :DEMD_YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  A.YRMN,  A.UPSO_CD  FROM  (   \n";
        query +=" SELECT  YRMN,  UPSO_CD,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  NEW_DAY  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_NEW_ZIP  BETWEEN  NVL(:START_ZIP,  UPSO_NEW_ZIP)   \n";
        query +=" AND  NVL(:END_ZIP,  UPSO_NEW_ZIP)  )  WHERE  YRMN  BETWEEN  OPBI_YRMN   \n";
        query +=" AND  to_char(add_months(to_date(:DEMD_YRMN,  'YYYYMM'),  1),  'YYYYMM')   \n";
        query +=" AND  OPBI_YRMN  <=  :DEMD_YRMN)  A  LEFT  JOIN  GIBU.TBRA_NOTE  B  ON  (A.YRMN  =  B.NOTE_YRMN   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD)  WHERE  B.NOTE_YRMN  IS  NULL  )  GROUP  BY  UPSO_CD  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  +1  )  A  )  )  )  XA  ,  GIBU.TBRA_UPSO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(GOSO_YN)  GOSO_YN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(COMPN_DAY,  NULL,  '고소중',  DECODE(JUDG_CD,  '2',  '기소중지',  '4',  '구약식',  NULL))  GOSO_YN  FROM  GIBU.TBRA_ACCU  WHERE  (JUDG_CD  NOT  IN  ('1','3','5','6','7','8','9','41','99')   \n";
        query +=" OR  JUDG_CD  IS  NULL)  )  GROUP  BY  UPSO_CD  )  XD  ,  INSA.TCPM_BIPLC  XF  WHERE  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XA.UPSO_GRAD   \n";
        query +=" AND  XC.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  XC.BSTYP_CD,  'A',  XC.BSTYP_CD,  :BSTYP_CD)   \n";
        query +=" AND  XC.BSTYP_CD  =  XA.BSTYP_CD   \n";
        query +=" AND  XD.UPSO_CD  (+)  =  XA.UPSO_CD   \n";
        query +=" AND  XF.GIBU  =  XB.BRAN_CD   \n";
        query +=" AND  XA.DEMD_MMCNT  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  ORDER  BY  SUBSTR(XB.UPSO_BD_MNG_NUM,1,5),  24,  XB.UPSO_NM ";
        sobj.setSql(query);
        sobj.setDouble("START_ZIP", START_ZIP);               //시작 우편번호
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //시작 월수
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setDouble("END_ZIP", END_ZIP);               //종료 우편번호
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //종료 월수
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 미징수 내역조회
    // 모든 업소의 미징수 내역을 조회한다
    public DOBJ CALLnonpy_select_test1_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLnonpy_select_test1_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.setRetcode(1);
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnonpy_select_test1_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_ZIP = dvobj.getRecord().getDouble("START_ZIP");   //시작 우편번호
        double   START_CNT_MON = dvobj.getRecord().getDouble("START_CNT_MON");   //시작 월수
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        double   END_ZIP = dvobj.getRecord().getDouble("END_ZIP");   //종료 우편번호
        double   END_CNT_MON = dvobj.getRecord().getDouble("END_CNT_MON");   //종료 월수
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //청구 년월
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.DEMD_MMCNT  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  DECODE(XB.MNGEMSTR_RESINUM  ,  NULL,  '',  'OK')  CHK_RESINUM  ,  XB.MNGEMSTR_NM  ,  DECODE(TRIM(XA.BSTYP_CD),  'o',  GIBU.FT_GET_NOREBANG_GRAD(XB.UPSO_CD),  XC.GRADNM)  GRADNM  ,  DECODE  ((SELECT  NVL(COUNT(UPSO_CD),  0)  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL),  0,  '',  '채권')  BOND  ,  XA.MONPRNCFEE  ,  XA.MONPRNCFEE2  ,  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO  UPSO_ADDR  ,  XB.UPSO_PHON  ,  XB.MNGEMSTR_HPNUM  ,  XA.TOT_USE_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_USE_AMT  +  XA.TOT_ADDT_AMT  AS  TOT_AMT  ,  DECODE(XB.BEFORE_UPSO_CD,  NULL,  '',  '재'  ||  TO_CHAR(XB.INS_DATE,  'YYYYMMDD'))  ||  '  '  ||  NVL(XD.GOSO_YN,  '')  REMAK  ,  XB.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,   \n";
        query +=" (SELECT  ATTE||'  '||CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =XB.UPSO_BD_MNG_NUM)  AS  SIGUGUN  ,  SUBSTR(XA.START_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.START_YRMN,  5,  2)  START_YRMN  ,  SUBSTR(XA.END_YRMN  ,  1,  4)  ||  '/'  ||  SUBSTR(XA.END_YRMN,  5,  2)  END_YRMN  ,  XF.PHON_NUM  BRAN_PHON_NUM  ,  DECODE((SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  TERM_YN  =  'N'),0,'','OK')  AUTO_YN  ,  '0'  AS  COL_CHECK  ,   \n";
        query +=" (SELECT  DECODE(COURT_NM,  NULL,  TOWNTWSHP,  COURT_NM)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  XB.UPSO_BD_MNG_NUM  )  AS  DONG  ,  XB.CLIENT_NUM  FROM  (   \n";
        query +=" SELECT  DEMD_GBN  ,  UPSO_CD  ,  DEMD_YRMN  ,  MONPRNCFEE  ,  MONPRNCFEE2  ,  BSTYP_CD  ,  UPSO_GRAD  ,  DEMD_MMCNT  ,  START_YRMN  ,  END_YRMN  ,  TOT_USE_AMT  ,  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  5)  MONPRNCFEE2  ,  GIBU.FT_SPLIT(DEMDS,  ',',  6)  DEMD_GBN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  DEMD_MMCNT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  TOT_USE_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  9)  +  GIBU.FT_SPLIT(DEMDS,  ',',  10)  TOT_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  GIBU.FT_GET_DEMD_AMT3(A.UPSO_CD,  :DEMD_YRMN,  'O')  DEMDS  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  COUNT(YRMN)  DEMD_MMCNT  ,  MIN(YRMN)  START_YRMN  FROM  (   \n";
        query +=" SELECT  A.YRMN,  A.UPSO_CD  FROM  (   \n";
        query +=" SELECT  YRMN,  UPSO_CD,  OPBI_YRMN  FROM  GIBU.COPY_YRMN  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUBSTR(OPBI_DAY,  1,  6)  OPBI_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),  '  ')  >  :DEMD_YRMN)   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  NEW_DAY  <=  :DEMD_YRMN  ||  '31')   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_NEW_ZIP  BETWEEN  NVL(:START_ZIP,  UPSO_NEW_ZIP)   \n";
        query +=" AND  NVL(:END_ZIP,  UPSO_NEW_ZIP)  )  WHERE  YRMN  BETWEEN  OPBI_YRMN   \n";
        query +=" AND  to_char(add_months(to_date(:DEMD_YRMN,  'YYYYMM'),  1),  'YYYYMM')   \n";
        query +=" AND  OPBI_YRMN  <=  :DEMD_YRMN)  A  LEFT  JOIN  GIBU.TBRA_NOTE  B  ON  (A.YRMN  =  B.NOTE_YRMN   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD)  WHERE  B.NOTE_YRMN  IS  NULL  )  GROUP  BY  UPSO_CD  HAVING  COUNT(YRMN)  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  +1  )  A  )  )  )  XA  ,  GIBU.TBRA_UPSO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(GOSO_YN)  GOSO_YN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(COMPN_DAY,  NULL,  '고소중',  DECODE(JUDG_CD,  '2',  '기소중지',  '4',  '구약식',  NULL))  GOSO_YN  FROM  GIBU.TBRA_ACCU  WHERE  (JUDG_CD  NOT  IN  ('1','3','5','6','7','8','9','41','99')   \n";
        query +=" OR  JUDG_CD  IS  NULL)  )  GROUP  BY  UPSO_CD  )  XD  ,  INSA.TCPM_BIPLC  XF  WHERE  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XA.UPSO_GRAD   \n";
        query +=" AND  XC.BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  XC.BSTYP_CD,  'A',  XC.BSTYP_CD,  :BSTYP_CD)   \n";
        query +=" AND  XC.BSTYP_CD  =  XA.BSTYP_CD   \n";
        query +=" AND  XD.UPSO_CD  (+)  =  XA.UPSO_CD   \n";
        query +=" AND  XF.GIBU  =  XB.BRAN_CD   \n";
        query +=" AND  XA.DEMD_MMCNT  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON  ORDER  BY  SUBSTR(XB.UPSO_BD_MNG_NUM,1,5),  24,  XB.UPSO_NM ";
        sobj.setSql(query);
        sobj.setDouble("START_ZIP", START_ZIP);               //시작 우편번호
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //시작 월수
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setDouble("END_ZIP", END_ZIP);               //종료 우편번호
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //종료 월수
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$nonpy_select_test1
    //##**$$end
}
