
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac01_s05
{
    public tac01_s05()
    {
    }
    //##**$$rcvb_susps_excel_save
    /*
    */
    public DOBJ CTLrcvb_susps_excel_save(DOBJ dobj)
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
            dobj  = CALLrcvb_susps_excel_save_SEL11(Conn, dobj);           //  미수가수카운트조회
            if( dobj.getRetObject("SEL11").getRecord().get("COUNT").equals("0"))
            {
                dobj  = CALLrcvb_susps_excel_save_INS15(Conn, dobj);           //  미수가수엑셀저장
            }
            else
            {
                dobj  = CALLrcvb_susps_excel_save_XIUD17(Conn, dobj);           //  데이터삭제
                dobj  = CALLrcvb_susps_excel_save_INS14(Conn, dobj);           //  미수가수엑셀저장
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
    public DOBJ CTLrcvb_susps_excel_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrcvb_susps_excel_save_SEL11(Conn, dobj);           //  미수가수카운트조회
        if( dobj.getRetObject("SEL11").getRecord().get("COUNT").equals("0"))
        {
            dobj  = CALLrcvb_susps_excel_save_INS15(Conn, dobj);           //  미수가수엑셀저장
        }
        else
        {
            dobj  = CALLrcvb_susps_excel_save_XIUD17(Conn, dobj);           //  데이터삭제
            dobj  = CALLrcvb_susps_excel_save_INS14(Conn, dobj);           //  미수가수엑셀저장
        }
        return dobj;
    }
    // 미수가수카운트조회
    // 미수가수카운트조회
    public DOBJ CALLrcvb_susps_excel_save_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrcvb_susps_excel_save_SEL11(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrcvb_susps_excel_save_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GUBUN = dobj.getRetObject("S").getRecord().get("GUBUN");   //구분
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_NUMBER(COUNT(1))  AS  COUNT  FROM  FIDU.TTAC_RCVB_SUSPS_EXCEL  WHERE  YRMN  =  :YRMN   \n";
        query +=" AND  GUBUN  =  :GUBUN ";
        sobj.setSql(query);
        sobj.setString("GUBUN", GUBUN);               //구분
        sobj.setString("YRMN", YRMN);               //년월
        return sobj;
    }
    // 미수가수엑셀저장
    // 미수가수엑셀저장
    public DOBJ CALLrcvb_susps_excel_save_INS15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS15");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrcvb_susps_excel_save_INS15(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS15") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrcvb_susps_excel_save_INS15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   YRMN = dvobj.getRecord().get("YRMN");   //년월
        String   REG_DATE = dvobj.getRecord().get("REG_DATE");   //등록일시
        String   BSCON_NM = dvobj.getRecord().get("BSCON_NM");   //거래처 명
        String   CONTENT = dvobj.getRecord().get("CONTENT");   //게시물 내용
        String   GUBUN = dvobj.getRecord().get("GUBUN");   //구분
        String   GUBUN1 = dvobj.getRecord().get("GUBUN1");   //구분1
        double   AMT = dvobj.getRecord().getDouble("AMT");   //금액
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_RCVB_SUSPS_EXCEL (INS_DATE, AMT, INSPRES_ID, GUBUN1, GUBUN, CONTENT, BSCON_NM, REG_DATE, YRMN)  \n";
        query +=" values(SYSDATE, :AMT , :INSPRES_ID , :GUBUN1 , :GUBUN , :CONTENT , :BSCON_NM , :REG_DATE , :YRMN )";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("REG_DATE", REG_DATE);               //등록일시
        sobj.setString("BSCON_NM", BSCON_NM);               //거래처 명
        sobj.setString("CONTENT", CONTENT);               //게시물 내용
        sobj.setString("GUBUN", GUBUN);               //구분
        sobj.setString("GUBUN1", GUBUN1);               //구분1
        sobj.setDouble("AMT", AMT);               //금액
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 데이터삭제
    // 데이터삭제
    public DOBJ CALLrcvb_susps_excel_save_XIUD17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD17");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrcvb_susps_excel_save_XIUD17(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD17");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrcvb_susps_excel_save_XIUD17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GUBUN = dobj.getRetObject("S").getRecord().get("GUBUN");   //구분
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM FIDU.TTAC_RCVB_SUSPS_EXCEL  \n";
        query +=" WHERE YRMN = :YRMN  \n";
        query +=" AND GUBUN = :GUBUN";
        sobj.setSql(query);
        sobj.setString("GUBUN", GUBUN);               //구분
        sobj.setString("YRMN", YRMN);               //년월
        return sobj;
    }
    // 미수가수엑셀저장
    // 미수가수엑셀저장
    public DOBJ CALLrcvb_susps_excel_save_INS14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS14");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrcvb_susps_excel_save_INS14(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS14") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrcvb_susps_excel_save_INS14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   YRMN = dvobj.getRecord().get("YRMN");   //년월
        String   REG_DATE = dvobj.getRecord().get("REG_DATE");   //등록일시
        String   BSCON_NM = dvobj.getRecord().get("BSCON_NM");   //거래처 명
        String   CONTENT = dvobj.getRecord().get("CONTENT");   //게시물 내용
        String   GUBUN = dvobj.getRecord().get("GUBUN");   //구분
        String   GUBUN1 = dvobj.getRecord().get("GUBUN1");   //구분1
        double   AMT = dvobj.getRecord().getDouble("AMT");   //금액
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_RCVB_SUSPS_EXCEL (INS_DATE, AMT, INSPRES_ID, GUBUN1, GUBUN, CONTENT, BSCON_NM, REG_DATE, YRMN)  \n";
        query +=" values(SYSDATE, :AMT , :INSPRES_ID , :GUBUN1 , :GUBUN , :CONTENT , :BSCON_NM , :REG_DATE , :YRMN )";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("REG_DATE", REG_DATE);               //등록일시
        sobj.setString("BSCON_NM", BSCON_NM);               //거래처 명
        sobj.setString("CONTENT", CONTENT);               //게시물 내용
        sobj.setString("GUBUN", GUBUN);               //구분
        sobj.setString("GUBUN1", GUBUN1);               //구분1
        sobj.setDouble("AMT", AMT);               //금액
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    //##**$$rcvb_susps_excel_save
    //##**$$rcvb_susps_excel_sel
    /*
    */
    public DOBJ CTLrcvb_susps_excel_sel(DOBJ dobj)
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
            dobj  = CALLrcvb_susps_excel_sel_SEL1(Conn, dobj);           //  미수가수엑셀조회
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
    public DOBJ CTLrcvb_susps_excel_sel( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrcvb_susps_excel_sel_SEL1(Conn, dobj);           //  미수가수엑셀조회
        return dobj;
    }
    // 미수가수엑셀조회
    // 미수가수엑셀조회
    public DOBJ CALLrcvb_susps_excel_sel_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrcvb_susps_excel_sel_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrcvb_susps_excel_sel_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GUBUN1 = dobj.getRetObject("S").getRecord().get("GUBUN1");   //구분1
        String   GUBUN = dobj.getRetObject("S").getRecord().get("GUBUN");   //구분
        String   BSCON_NM = dobj.getRetObject("S").getRecord().get("BSCON_NM");   //거래처 명
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT GUBUN ,REG_DATE ,BSCON_NM ,CONTENT ,GUBUN1 ,AMT  ";
        query +=" FROM FIDU.TTAC_RCVB_SUSPS_EXCEL  ";
        query +=" WHERE YRMN = :YRMN  ";
        query +=" AND GUBUN = :GUBUN  ";
        if( !GUBUN1.equals("") )
        {
            query +=" AND GUBUN1 LIKE '%'||:GUBUN1||'%'  ";
        }
        if( !BSCON_NM.equals("") )
        {
            query +=" AND BSCON_NM LIKE '%'||:BSCON_NM||'%'  ";
        }
        sobj.setSql(query);
        if(!GUBUN1.equals(""))
        {
            sobj.setString("GUBUN1", GUBUN1);               //구분1
        }
        sobj.setString("GUBUN", GUBUN);               //구분
        if(!BSCON_NM.equals(""))
        {
            sobj.setString("BSCON_NM", BSCON_NM);               //거래처 명
        }
        sobj.setString("YRMN", YRMN);               //년월
        return sobj;
    }
    //##**$$rcvb_susps_excel_sel
    //##**$$end
}
