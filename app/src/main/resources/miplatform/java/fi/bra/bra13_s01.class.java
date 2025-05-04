
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra13_s01.class
{
    public bra13_s01.class()
    {
    }
    //##**$$staff_plan_save
    /*
    */
    public DOBJ CTLstaff_plan_save(DOBJ dobj)
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
            dobj  = CALLstaff_plan_save_MIUD1(Conn, dobj);           //  분기
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
    public DOBJ CTLstaff_plan_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLstaff_plan_save_MIUD1(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLstaff_plan_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLstaff_plan_save_INS5(Conn, dobj);           //  신규
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLstaff_plan_save_UPD6(Conn, dobj);           //  수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLstaff_plan_save_DEL7(Conn, dobj);           //  삭제
            }
        }
        return dobj;
    }
    // 삭제
    public DOBJ CALLstaff_plan_save_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_plan_save_DEL7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_plan_save_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YYYY = dvobj.getRecord().get("YYYY");   //년도
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_COLLECT_PLAN  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and YYYY=:YYYY";
        sobj.setSql(query);
        sobj.setString("YYYY", YYYY);               //년도
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    // 신규
    public DOBJ CALLstaff_plan_save_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_plan_save_INS5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_plan_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   MON7_AMT = dvobj.getRecord().get("MON7_AMT");   //7월
        String   YYYY = dvobj.getRecord().get("YYYY");   //년도
        String   MON11_AMT = dvobj.getRecord().get("MON11_AMT");   //11월
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   MON10_AMT = dvobj.getRecord().get("MON10_AMT");   //10월
        String   MON3_AMT = dvobj.getRecord().get("MON3_AMT");   //3월
        String   MON1_AMT = dvobj.getRecord().get("MON1_AMT");   //1월
        String   MON8_AMT = dvobj.getRecord().get("MON8_AMT");   //8월
        String   MON6_AMT = dvobj.getRecord().get("MON6_AMT");   //6월
        String   MON9_AMT = dvobj.getRecord().get("MON9_AMT");   //9월
        double   TOTAL_AMT = dvobj.getRecord().getDouble("TOTAL_AMT");   //합계 액
        String   MON2_AMT = dvobj.getRecord().get("MON2_AMT");   //2월
        String   MON12_AMT = dvobj.getRecord().get("MON12_AMT");   //12월
        String   MON4_AMT = dvobj.getRecord().get("MON4_AMT");   //4월
        String   MON5_AMT = dvobj.getRecord().get("MON5_AMT");   //5월
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_COLLECT_PLAN (MON5_AMT, INSPRES_ID, MON4_AMT, MON12_AMT, MON2_AMT, TOTAL_AMT, MON9_AMT, MON6_AMT, MON8_AMT, MON1_AMT, MON3_AMT, INS_DATE, MON10_AMT, STAFF_CD, MON11_AMT, YYYY, MON7_AMT)  \n";
        query +=" values(:MON5_AMT , :INSPRES_ID , :MON4_AMT , :MON12_AMT , :MON2_AMT , :TOTAL_AMT , :MON9_AMT , :MON6_AMT , :MON8_AMT , :MON1_AMT , :MON3_AMT , SYSDATE, :MON10_AMT , :STAFF_CD , :MON11_AMT , :YYYY , :MON7_AMT )";
        sobj.setSql(query);
        sobj.setString("MON7_AMT", MON7_AMT);               //7월
        sobj.setString("YYYY", YYYY);               //년도
        sobj.setString("MON11_AMT", MON11_AMT);               //11월
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("MON10_AMT", MON10_AMT);               //10월
        sobj.setString("MON3_AMT", MON3_AMT);               //3월
        sobj.setString("MON1_AMT", MON1_AMT);               //1월
        sobj.setString("MON8_AMT", MON8_AMT);               //8월
        sobj.setString("MON6_AMT", MON6_AMT);               //6월
        sobj.setString("MON9_AMT", MON9_AMT);               //9월
        sobj.setDouble("TOTAL_AMT", TOTAL_AMT);               //합계 액
        sobj.setString("MON2_AMT", MON2_AMT);               //2월
        sobj.setString("MON12_AMT", MON12_AMT);               //12월
        sobj.setString("MON4_AMT", MON4_AMT);               //4월
        sobj.setString("MON5_AMT", MON5_AMT);               //5월
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 수정
    public DOBJ CALLstaff_plan_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_plan_save_UPD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_plan_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   MON7_AMT = dvobj.getRecord().get("MON7_AMT");   //7월
        String   YYYY = dvobj.getRecord().get("YYYY");   //년도
        String   MON11_AMT = dvobj.getRecord().get("MON11_AMT");   //11월
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   MON10_AMT = dvobj.getRecord().get("MON10_AMT");   //10월
        String   MON3_AMT = dvobj.getRecord().get("MON3_AMT");   //3월
        String   MON1_AMT = dvobj.getRecord().get("MON1_AMT");   //1월
        String   MON8_AMT = dvobj.getRecord().get("MON8_AMT");   //8월
        String   MON6_AMT = dvobj.getRecord().get("MON6_AMT");   //6월
        String   MON9_AMT = dvobj.getRecord().get("MON9_AMT");   //9월
        double   TOTAL_AMT = dvobj.getRecord().getDouble("TOTAL_AMT");   //합계 액
        String   MON2_AMT = dvobj.getRecord().get("MON2_AMT");   //2월
        String   MON12_AMT = dvobj.getRecord().get("MON12_AMT");   //12월
        String   MON4_AMT = dvobj.getRecord().get("MON4_AMT");   //4월
        String   MON5_AMT = dvobj.getRecord().get("MON5_AMT");   //5월
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_COLLECT_PLAN  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MON5_AMT=:MON5_AMT , MON4_AMT=:MON4_AMT , MON12_AMT=:MON12_AMT , MON2_AMT=:MON2_AMT , TOTAL_AMT=:TOTAL_AMT , MON9_AMT=:MON9_AMT , MON6_AMT=:MON6_AMT , MON8_AMT=:MON8_AMT , MON1_AMT=:MON1_AMT , MON3_AMT=:MON3_AMT , MON10_AMT=:MON10_AMT , MON11_AMT=:MON11_AMT , MOD_DATE=SYSDATE , MON7_AMT=:MON7_AMT  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and YYYY=:YYYY";
        sobj.setSql(query);
        sobj.setString("MON7_AMT", MON7_AMT);               //7월
        sobj.setString("YYYY", YYYY);               //년도
        sobj.setString("MON11_AMT", MON11_AMT);               //11월
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("MON10_AMT", MON10_AMT);               //10월
        sobj.setString("MON3_AMT", MON3_AMT);               //3월
        sobj.setString("MON1_AMT", MON1_AMT);               //1월
        sobj.setString("MON8_AMT", MON8_AMT);               //8월
        sobj.setString("MON6_AMT", MON6_AMT);               //6월
        sobj.setString("MON9_AMT", MON9_AMT);               //9월
        sobj.setDouble("TOTAL_AMT", TOTAL_AMT);               //합계 액
        sobj.setString("MON2_AMT", MON2_AMT);               //2월
        sobj.setString("MON12_AMT", MON12_AMT);               //12월
        sobj.setString("MON4_AMT", MON4_AMT);               //4월
        sobj.setString("MON5_AMT", MON5_AMT);               //5월
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    //##**$$staff_plan_save
    //##**$$end
}
