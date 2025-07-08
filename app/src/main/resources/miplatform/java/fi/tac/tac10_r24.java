
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r24
{
    public tac10_r24()
    {
    }
    //##**$$tac10_r24_select
    /*
    */
    public DOBJ CTLtac10_r24_select(DOBJ dobj)
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
            dobj  = CALLtac10_r24_select_SEL1(Conn, dobj);           //  회원별 작품보류잔액 내역서조회
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
    public DOBJ CTLtac10_r24_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r24_select_SEL1(Conn, dobj);           //  회원별 작품보류잔액 내역서조회
        return dobj;
    }
    // 회원별 작품보류잔액 내역서조회
    public DOBJ CALLtac10_r24_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r24_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r24_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT MB_CD, HANMB_NM, SUM(BFRMON_REMD) AS BFRMON_REMD, SUM(PREMON_OCC_AMT) AS PREMON_OCC_AMT, SUM(PREMON_RELS_AMT) AS PREMON_RELS_AMT, SUM(MOD_AMT) AS MOD_AMT, SUM(PREMON_REMD) AS PREMON_REMD FROM(  ";
        query +=" SELECT MB_CD, FIDU.GET_MB_NM (MB_CD) AS HANMB_NM, BFRMON_REMD, PREMON_OCC_AMT, PREMON_RELS_AMT, NVL(MOD_AMT,0) MOD_AMT, PREMON_REMD  ";
        query +=" FROM FIDU.TTAC_PRODSUSPREMD  ";
        query +=" WHERE SUPP_YRMN = :SUPP_YRMN  ";
        query +=" UNION ALL  ";
        query +=" SELECT MB_CD, FIDU.GET_MB_NM (MB_CD) AS HANMB_NM, 0 BFRMON_REMD, SUM(OCC_AMT) AS PREMON_OCC_AMT, SUM(OCC_AMT) AS PREMON_RELS_AMT, 0 MOD_AMT, 0 PREMON_REMD  ";
        query +=" FROM FIDU.TTAC_PLEDAMT  ";
        query +=" WHERE SUPP_YRMN = :SUPP_YRMN  ";
        query +=" AND SUPP_YRMN > '201006'  ";
        query +=" GROUP BY SUPP_YRMN, MB_CD )  ";
        query +=" WHERE 1 = 1  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND MB_CD = :MB_CD  ";
        }
        query +=" GROUP BY MB_CD, HANMB_NM HAVING SUM(BFRMON_REMD) + SUM(PREMON_OCC_AMT) + SUM(PREMON_RELS_AMT) + SUM(NVL(MOD_AMT,0)) + SUM(PREMON_REMD) <> 0  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$tac10_r24_select
    //##**$$tac10_r24_save
    /*
    */
    public DOBJ CTLtac10_r24_save(DOBJ dobj)
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
            dobj  = CALLtac10_r24_save_MIUD5(Conn, dobj);           //  분기
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
    public DOBJ CTLtac10_r24_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r24_save_MIUD5(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLtac10_r24_save_MIUD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD5");
        VOBJ dvobj = dobj.getRetObject("S1");            //사용자 화면에서 발생한 Object입니다.
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
                dobj  = CALLtac10_r24_save_UPD9(Conn, dobj);           //  회원별 작품보류잔액 수정
            }
        }
        return dobj;
    }
    // 회원별 작품보류잔액 수정
    public DOBJ CALLtac10_r24_save_UPD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtac10_r24_save_UPD9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r24_save_UPD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        double   MOD_AMT = dvobj.getRecord().getDouble("MOD_AMT");   //수정 금액
        double   PREMON_REMD = dvobj.getRecord().getDouble("PREMON_REMD");   //당월 잔액
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        String   MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //수정자 ID
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_PRODSUSPREMD  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , PREMON_REMD=:PREMON_REMD , MOD_AMT=:MOD_AMT , MOD_DATE=SYSDATE  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setDouble("MOD_AMT", MOD_AMT);               //수정 금액
        sobj.setDouble("PREMON_REMD", PREMON_REMD);               //당월 잔액
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$tac10_r24_save
    //##**$$end
}
