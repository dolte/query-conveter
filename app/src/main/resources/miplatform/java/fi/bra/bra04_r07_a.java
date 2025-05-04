
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_r07_a
{
    public bra04_r07_a()
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
            dobj  = CALLsel_nonpy_history_SEL1(Conn, dobj);           //  저장된현황에서 조회
            dobj  = CALLsel_nonpy_history_SEL2(Conn, dobj);           //  담당자미징수율
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
        dobj  = CALLsel_nonpy_history_SEL1(Conn, dobj);           //  저장된현황에서 조회
        dobj  = CALLsel_nonpy_history_SEL2(Conn, dobj);           //  담당자미징수율
        return dobj;
    }
    // 저장된현황에서 조회
    public DOBJ CALLsel_nonpy_history_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_nonpy_history_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_nonpy_history_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   START_ZIP = dobj.getRetObject("S").getRecord().getDouble("START_ZIP");   //시작 우편번호
        double   START_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("START_CNT_MON");   //시작 월수
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        double   END_ZIP = dobj.getRetObject("S").getRecord().getDouble("END_ZIP");   //종료 우편번호
        double   END_CNT_MON = dobj.getRetObject("S").getRecord().getDouble("END_CNT_MON");   //종료 월수
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //현황 년월
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  PRCON_YRMN  ,  GBN  ,  DEMD_MMCNT  ,  UPSO_CD  ,  UPSO_NM  ,  CHK_RESINUM  ,  MNGEMSTR_NM  ,  GRADNM  ,  BOND  ,  MONPRNCFEE  ,  MONPRNCFEE2  ,  UPSO_ADDR  ,  UPSO_PHON  ,  MNGEMSTR_HPNUM  ,  TOT_USE_AMT  ,  TOT_ADDT_AMT  ,  TOT_AMT  ,  REMAK  ,  UPSO_ZIP  ,  SIGUGUN  ,  START_YRMN  ,  END_YRMN  ,  BRAN_PHON_NUM  ,  AUTO_YN  ,  COL_CHECK  ,  DONG  ,  CLIENT_NUM  ,  BRAN_CD  ,  STAFF_CD  ,  STAFF_NM  ,  VISIT_YN5  ,  VISIT_YN6  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  0)  AS  ACCOUNT_NM  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  2)  AS  BANK_NM1  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  3)  AS  ACCN_NUM1  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  5)  AS  BANK_NM2  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  6)  AS  ACCN_NUM2  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  8)  AS  BANK_NM3  ,  GIBU.FT_SPLIT(BANK_RTN,  ',',  9)  AS  ACCN_NUM3  ,  STOMU_YN  ,  SMS_CNT  ,  SMS_TOTCNT  ,  VISIT_TOTCNT  ,  CALL_TOTCNT  FROM  (   \n";
        query +=" SELECT  PRCON_YRMN  ,  GBN  ,  DEMD_MMCNT  ,  UPSO_CD  ,  UPSO_NM  ,  CHK_RESINUM  ,  MNGEMSTR_NM  ,  GRADNM  ,  BOND  ,  MONPRNCFEE  ,  MONPRNCFEE2  ,  UPSO_ADDR  ,  UPSO_PHON  ,  MNGEMSTR_HPNUM  ,  TOT_USE_AMT  ,  TOT_ADDT_AMT  ,  TOT_AMT  ,  REMAK  ,  UPSO_ZIP  ,  SIGUGUN  ,  START_YRMN  ,  END_YRMN  ,  BRAN_PHON_NUM  ,  AUTO_YN  ,  COL_CHECK  ,  DONG  ,  CLIENT_NUM  ,  BRAN_CD  ,  STAFF_CD  ,  STAFF_NM  ,  VISIT_YN5  ,  VISIT_YN6  ,  STOMU_YN  ,  SMS_CNT  ,  SMS_TOTCNT  ,  VISIT_TOTCNT  ,  CALL_TOTCNT  ,  GIBU.FT_GET_VIRTUAL_ACCOUNT(A.UPSO_CD)  AS  BANK_RTN  FROM  GIBU.TBRA_NONPY_PRCON  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  GBN  =  DECODE(:GBN,  '3',  GBN,  :GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  STAFF_CD  =  NVL(:STAFF_CD,  STAFF_CD)   \n";
        query +=" AND  UPSO_ZIP  BETWEEN  NVL(:START_ZIP,  UPSO_ZIP)   \n";
        query +=" AND  NVL(:END_ZIP,  UPSO_ZIP)   \n";
        query +=" AND  DEMD_MMCNT  BETWEEN  :START_CNT_MON   \n";
        query +=" AND  :END_CNT_MON   \n";
        query +=" AND  BSTYP_CD  =  DECODE(:BSTYP_CD,  NULL,  BSTYP_CD,  'A',  BSTYP_CD,  :BSTYP_CD)  ) ";
        sobj.setSql(query);
        sobj.setDouble("START_ZIP", START_ZIP);               //시작 우편번호
        sobj.setDouble("START_CNT_MON", START_CNT_MON);               //시작 월수
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("GBN", GBN);               //구분
        sobj.setDouble("END_ZIP", END_ZIP);               //종료 우편번호
        sobj.setDouble("END_CNT_MON", END_CNT_MON);               //종료 월수
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 담당자미징수율
    public DOBJ CALLsel_nonpy_history_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_nonpy_history_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_nonpy_history_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //현황 년월
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
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    //##**$$sel_nonpy_history
    //##**$$end
}
