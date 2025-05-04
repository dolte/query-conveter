
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra14_s03
{
    public bra14_s03()
    {
    }
    //##**$$sel_auto_card_demd
    /*
    */
    public DOBJ CTLsel_auto_card_demd(DOBJ dobj)
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
            dobj  = CALLsel_auto_card_demd_SEL1(Conn, dobj);           //  청구구분
            if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") <= 0)
            {
                dobj  = CALLsel_auto_card_demd_SEL2(Conn, dobj);           //  조회
                dobj  = CALLsel_auto_card_demd_MRG6( dobj);        //  MRG
            }
            else if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") > 0)
            {
                dobj  = CALLsel_auto_card_demd_SEL3(Conn, dobj);           //  청구후 조회
                dobj  = CALLsel_auto_card_demd_MRG6( dobj);        //  MRG
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
    public DOBJ CTLsel_auto_card_demd( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_auto_card_demd_SEL1(Conn, dobj);           //  청구구분
        if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") <= 0)
        {
            dobj  = CALLsel_auto_card_demd_SEL2(Conn, dobj);           //  조회
            dobj  = CALLsel_auto_card_demd_MRG6( dobj);        //  MRG
        }
        else if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") > 0)
        {
            dobj  = CALLsel_auto_card_demd_SEL3(Conn, dobj);           //  청구후 조회
            dobj  = CALLsel_auto_card_demd_MRG6( dobj);        //  MRG
        }
        return dobj;
    }
    // 청구구분
    public DOBJ CALLsel_auto_card_demd_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_auto_card_demd_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_auto_card_demd_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.FMS_CREDIT_REAL_CASE_IF_TBL  WHERE  1=1   \n";
        query +=" AND  SUBSTR(PROC_DT,1,6)=:START_DAY ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        return sobj;
    }
    // 조회
    public DOBJ CALLsel_auto_card_demd_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_auto_card_demd_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_auto_card_demd_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.BRAN_CD, GIBU.GET_BRAN_NM (A.BRAN_CD) AS BRAN_NM, GIBU.FT_GET_BSTYPGRAD_NM (A.UPSO_CD, 'NM') AS BSTYP_NM, A.UPSO_CD, A.UPSO_NM, B.DEMD_YRMN, B.DEMD_MMCNT, B.TOT_USE_AMT, B.MONPRNCFEE, (CASE WHEN (SELECT COUNT (1)  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND COMPN_DAY IS NULL) > 0 THEN 'Y' ELSE 'N' END) AS ACCU_YN, A.CLSBS_YRMN, A.MNGEMSTR_NM, DECODE (C.APP_GBN, 0, '등록', '해지') APP_GBN, C.PAYPRES_NM, A.STAFF_CD, FIDU.GET_STAFF_NM(A.STAFF_CD) STAFF_NM, ''RESULT_MSG, D.REPT_DAY, D.REPT_AMT, D.COMIS  ";
        query +=" FROM GIBU.TBRA_UPSO A, GIBU.TBRA_DEMD_OCR B, GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION C, GIBU.TBRA_REPT D  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.UPSO_CD = C.UPSO_CD  ";
        query +=" AND A.UPSO_CD = D.UPSO_CD(+)  ";
        query +=" AND SUBSTR(D.REPT_DAY(+),1,6) = :START_DAY  ";
        query +=" AND D.REPT_GBN(+) = '10'  ";
        query +=" AND C.CONFIRM_DATE IS NOT NULL  ";
        query +=" AND C.APP_GBN=0  ";
        query +=" AND B.DEMD_MMCNT >0  ";
        query +=" AND A.BRAN_CD = DECODE ( :BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD)  ";
        query +=" AND C.SEQ = (  ";
        query +=" SELECT MAX (SEQ)  ";
        query +=" FROM GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION XX  ";
        query +=" WHERE XX.UPSO_CD = C.UPSO_CD  ";
        query +=" GROUP BY XX.UPSO_CD)  ";
        query +=" AND B.DEMD_YRMN = :START_DAY  ";
        if( !STAFF_CD.equals("") )
        {
            query +=" STAFF_CD =:STAFF_CD  ";
        }
        query +=" ORDER BY BRAN_CD, UPSO_CD  ";
        sobj.setSql(query);
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        }
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // MRG
    public DOBJ CALLsel_auto_card_demd_MRG6(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG6");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL2,SEL3","");
        rvobj.setName("MRG6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 청구후 조회
    public DOBJ CALLsel_auto_card_demd_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_auto_card_demd_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_auto_card_demd_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.BRAN_CD, GIBU.GET_BRAN_NM (A.BRAN_CD) AS BRAN_NM, GIBU.FT_GET_BSTYPGRAD_NM (A.UPSO_CD, 'NM') AS BSTYP_NM, A.UPSO_CD, A.UPSO_NM, B.DEMD_YRMN, B.DEMD_MMCNT, B.TOT_USE_AMT, B.MONPRNCFEE, (CASE WHEN (SELECT COUNT (1)  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND COMPN_DAY IS NULL) > 0 THEN 'Y' ELSE 'N' END) AS ACCU_YN, A.CLSBS_YRMN, A.MNGEMSTR_NM, DECODE (C.APP_GBN, 0, '등록', '해지') APP_GBN, C.PAYPRES_NM, A.STAFF_CD, FIDU.GET_STAFF_NM(A.STAFF_CD) STAFF_NM, (  ";
        query +=" SELECT RESULT_MSG  ";
        query +=" FROM (SELECT PROC_DT, PROC_SEQ, RESULT_MSG  ";
        query +=" FROM GIBU.FMS_CREDIT_REAL_CASE_IF_TBL  ";
        query +=" WHERE 1=1  ";
        query +=" AND MEMBER_ID = A.UPSO_CD  ";
        query +=" AND SUBSTR(PROC_DT,1,6) = B.DEMD_YRMN  ";
        query +=" ORDER BY 1 desc, 2 desc)  ";
        query +=" WHERE rownum=1 ) RESULT_MSG, D.REPT_DAY, D.REPT_AMT, D.COMIS  ";
        query +=" FROM GIBU.TBRA_UPSO A, GIBU.TBRA_DEMD_OCR B, GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION C, GIBU.TBRA_REPT D  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.UPSO_CD = C.UPSO_CD  ";
        query +=" AND A.UPSO_CD = D.UPSO_CD(+)  ";
        query +=" AND SUBSTR(D.REPT_DAY(+),1,6) = :START_DAY  ";
        query +=" AND D.REPT_GBN(+) = '10'  ";
        query +=" AND C.CONFIRM_DATE IS NOT NULL  ";
        query +=" AND A.BRAN_CD = DECODE ( :BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD)  ";
        query +=" AND A.UPSO_CD IN (SELECT MEMBER_ID  ";
        query +=" FROM GIBU.FMS_CREDIT_REAL_CASE_IF_TBL X  ";
        query +=" WHERE SUBSTR(X.PROC_DT,1,6)=:START_DAY)  ";
        query +=" AND B.DEMD_YRMN = :START_DAY  ";
        if( !STAFF_CD.equals("") )
        {
            query +=" STAFF_CD =:STAFF_CD  ";
        }
        query +=" ORDER BY BRAN_CD, UPSO_CD  ";
        sobj.setSql(query);
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        }
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$sel_auto_card_demd
    //##**$$end
}
