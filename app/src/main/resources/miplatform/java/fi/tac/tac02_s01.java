
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac02_s01
{
    public tac02_s01()
    {
    }
    //##**$$UpdateMst
    /* * 프로그램명 : tac02_s01
    * 작성자 : 손주환
    * 작성일 : 2009/09/23
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLUpdateMst(DOBJ dobj)
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
            dobj  = CALLUpdateMst_UPD1(Conn, dobj);           //  분매마감해제
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
    public DOBJ CTLUpdateMst( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLUpdateMst_UPD1(Conn, dobj);           //  분매마감해제
        return dobj;
    }
    // 분매마감해제
    public DOBJ CALLUpdateMst_UPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLUpdateMst_UPD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD1") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLUpdateMst_UPD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   END_YN = dvobj.getRecord().get("END_YN");   //마감 여부
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //매체 코드
        String   SVC_CD = dvobj.getRecord().get("SVC_CD");   //서비스 코드
        String   DISTR_YRMN = dvobj.getRecord().get("DISTR_YRMN");   //분배 년월
        double   DISTR_NUM = dvobj.getRecord().getDouble("DISTR_NUM");   //분배 번호
        String   WRK_YRMN = dvobj.getRecord().get("WRK_YRMN");   //작업 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TDIS_DISTRPLANAMT  \n";
        query +=" set END_YN=:END_YN  \n";
        query +=" where WRK_YRMN=:WRK_YRMN  \n";
        query +=" and DISTR_NUM=:DISTR_NUM  \n";
        query +=" and DISTR_YRMN=:DISTR_YRMN  \n";
        query +=" and SVC_CD=:SVC_CD  \n";
        query +=" and MDM_CD=:MDM_CD  \n";
        query +=" and BSCON_CD=:BSCON_CD";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("END_YN", END_YN);               //마감 여부
        sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        sobj.setString("SVC_CD", SVC_CD);               //서비스 코드
        sobj.setString("DISTR_YRMN", DISTR_YRMN);               //분배 년월
        sobj.setDouble("DISTR_NUM", DISTR_NUM);               //분배 번호
        sobj.setString("WRK_YRMN", WRK_YRMN);               //작업 년월
        return sobj;
    }
    //##**$$UpdateMst
    //##**$$searchMst
    /* * 프로그램명 : tac02_s01
    * 작성자 : 손주환
    * 작성일 : 2009/09/23
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchMst(DOBJ dobj)
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
            dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  분배대상금액조회
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
    public DOBJ CTLsearchMst( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  분배대상금액조회
        return dobj;
    }
    // 분배대상금액조회
    // 분배대상금액조회
    public DOBJ CALLsearchMst_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   WRK_YRMN = dobj.getRetObject("S").getRecord().get("WRK_YRMN");   //작업 년월
        String   SVC_CD = dobj.getRetObject("S").getRecord().get("SVC_CD");   //서비스 코드
        String   MDM_CD_ALL = dobj.getRetObject("S").getRecord().get("MDM_CD_ALL");   //매체코드 대.중분류 검색값
        String   MDM_CD = dobj.getRetObject("S").getRecord().get("MDM_CD");   //매체 코드
        String   END_YN = dobj.getRetObject("S").getRecord().get("END_YN");   //마감 여부
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.DISTR_YRMN, B.AVECLASS_CD_NM, B.SCLASS_CD_NM, B.MDM_CD_NM, FIDU.GET_SVC_NM(A.SVC_CD) AS SVC_CD_NM, A.MDM_CD, A.SVC_CD, A.BSCON_CD, FIDU.GET_BSCON_NM(A.BSCON_CD) AS BSCON_CD_NM, A.WRK_YRMN, A.DISTR_NUM, A.DEMD_NUM, A.SUSP_NUM, A.LOG_START_YRMN, A.LOG_END_YRMN, A.SOGB_YN, A.SOGB_METH, FIDU.GET_CODE_NM('00256', TRIM(A.SOGB_METH)) AS SOGB_METH_NM, A.DISTR_PLAN_AMT, A.SUSPRATE_GBN AS SUSPRATE_GBN_NM, A.SUSPRATE_GBN, A.DISTR_AMT, A.SUSP_AMT, A.ETC_AMT, A.ABRTUNE_DISTR_AMT, A.DOMTUNE_DISTR_AMT, A.ABRPUBC_DISTR_AMT, A.DOMPUBC_DISTR_AMT, A.RIGHTPARTY_DISTR_AMT, A.PERUNCO_AMT, A.TOT_POINT, A.END_YN, A.DISTRPRES_ID, A.DISTR_DATE, A.ENDPRES_ID, A.END_DATE  ";
        query +=" FROM FIDU.TDIS_DISTRPLANAMT A, FIDU.TENV_MDMCD B  ";
        query +=" WHERE A.MDM_CD = B.MDM_CD  ";
        query +=" AND B.USE_YN = 'Y'  ";
        query +=" AND A.WRK_YRMN = :WRK_YRMN  ";
        query +=" AND A.MDM_CD LIKE :MDM_CD||'%'  ";
        query +=" AND A.MDM_CD LIKE :MDM_CD_ALL || '%'  ";
        query +=" AND A.SVC_CD LIKE :SVC_CD||'%'  ";
        query +=" AND A.BSCON_CD LIKE :BSCON_CD||'%'  ";
        if( !END_YN.equals("%"))
        {
            query +=" AND ( A.END_YN = :END_YN )  ";
        }
        query +=" ORDER BY DISTR_YRMN, WRK_YRMN DESC  ";
        sobj.setSql(query);
        sobj.setString("WRK_YRMN", WRK_YRMN);               //작업 년월
        sobj.setString("SVC_CD", SVC_CD);               //서비스 코드
        sobj.setString("MDM_CD_ALL", MDM_CD_ALL);               //매체코드 대.중분류 검색값
        sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        if( !END_YN.equals("%"))
        {
            sobj.setString("END_YN", END_YN);               //마감 여부
        }
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    //##**$$searchMst
    //##**$$end
}
