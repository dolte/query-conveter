
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s07
{
    public bra10_s07()
    {
    }
    //##**$$bra10_s07_select01
    /*
    */
    public DOBJ CTLbra10_s07_select01(DOBJ dobj)
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
            dobj  = CALLbra10_s07_select01_SEL1(Conn, dobj);           //  성과급조회
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
    public DOBJ CTLbra10_s07_select01( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra10_s07_select01_SEL1(Conn, dobj);           //  성과급조회
        return dobj;
    }
    // 성과급조회
    public DOBJ CALLbra10_s07_select01_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra10_s07_select01_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_s07_select01_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YEAR = dobj.getRetObject("S").getRecord().get("APPL_YEAR");   //적용 년도
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  APPL_YEAR,  APPL_SEQ,  START_RATE,  END_RATE,  BONUS_RATE  FROM  GIBU.TBRA_BONUS_PLOT  WHERE  APPL_YEAR  =  :APPL_YEAR  ORDER  BY  APPL_SEQ ";
        sobj.setSql(query);
        sobj.setString("APPL_YEAR", APPL_YEAR);               //적용 년도
        return sobj;
    }
    //##**$$bra10_s07_select01
    //##**$$bra10_s07_save01
    /* * 프로그램명 : bra10_s07
    * 작성자 : 999999
    * 작성일 : 2009/11/16
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbra10_s07_save01(DOBJ dobj)
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
            dobj  = CALLbra10_s07_save01_MIUD1(Conn, dobj);           //  성과급저장
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
    public DOBJ CTLbra10_s07_save01( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra10_s07_save01_MIUD1(Conn, dobj);           //  성과급저장
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 성과급저장
    public DOBJ CALLbra10_s07_save01_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLbra10_s07_save01_INS5(Conn, dobj);           //  성과급지급저장
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra10_s07_save01_UPD7(Conn, dobj);           //  성과급지급수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra10_s07_save01_DEL6(Conn, dobj);           //  성과급지정삭제
            }
        }
        return dobj;
    }
    // 성과급지정삭제
    public DOBJ CALLbra10_s07_save01_DEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("DEL6");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra10_s07_save01_DEL6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL6") ;
        rvobj.Println("DEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_s07_save01_DEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int   APPL_SEQ = dvobj.getRecord().getInt("APPL_SEQ");   //적용 순번
        String   APPL_YEAR = dvobj.getRecord().get("APPL_YEAR");   //적용 년도
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BONUS_PLOT  \n";
        query +=" where APPL_YEAR=:APPL_YEAR  \n";
        query +=" and APPL_SEQ=:APPL_SEQ";
        sobj.setSql(query);
        sobj.setInt("APPL_SEQ", APPL_SEQ);               //적용 순번
        sobj.setString("APPL_YEAR", APPL_YEAR);               //적용 년도
        return sobj;
    }
    // 성과급지급저장
    public DOBJ CALLbra10_s07_save01_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra10_s07_save01_INS5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        rvobj.Println("INS5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_s07_save01_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int  APPL_SEQ = 0;        //적용 순번
        String INS_DATE ="";        //등록 일시
        double   END_RATE = dvobj.getRecord().getDouble("END_RATE");   //종료 비율
        double   START_RATE = dvobj.getRecord().getDouble("START_RATE");   //시작 비율
        String   APPL_SEQ_1 = dobj.getRetObject("R").getRecord().get("APPL_YEAR");   //적용 순번
        String   APPL_YEAR = dvobj.getRecord().get("APPL_YEAR");   //적용 년도
        double   BONUS_RATE = dvobj.getRecord().getDouble("BONUS_RATE");   //성과 요율
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BONUS_PLOT (INS_DATE, INSPRES_ID, BONUS_RATE, APPL_YEAR, APPL_SEQ, START_RATE, END_RATE)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BONUS_RATE , :APPL_YEAR , (SELECT NVL(MAX(APPL_SEQ),0) + 1 FROM GIBU.TBRA_BONUS_PLOT WHERE APPL_YEAR = :APPL_SEQ_1), :START_RATE , :END_RATE )";
        sobj.setSql(query);
        sobj.setDouble("END_RATE", END_RATE);               //종료 비율
        sobj.setDouble("START_RATE", START_RATE);               //시작 비율
        sobj.setString("APPL_SEQ_1", APPL_SEQ_1);               //적용 순번
        sobj.setString("APPL_YEAR", APPL_YEAR);               //적용 년도
        sobj.setDouble("BONUS_RATE", BONUS_RATE);               //성과 요율
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 성과급지급수정
    public DOBJ CALLbra10_s07_save01_UPD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("UPD7");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra10_s07_save01_UPD7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD7") ;
        rvobj.Println("UPD7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_s07_save01_UPD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   END_RATE = dvobj.getRecord().getDouble("END_RATE");   //종료 비율
        double   START_RATE = dvobj.getRecord().getDouble("START_RATE");   //시작 비율
        int   APPL_SEQ = dvobj.getRecord().getInt("APPL_SEQ");   //적용 순번
        String   APPL_YEAR = dvobj.getRecord().get("APPL_YEAR");   //적용 년도
        double   BONUS_RATE = dvobj.getRecord().getDouble("BONUS_RATE");   //성과 요율
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BONUS_PLOT  \n";
        query +=" set BONUS_RATE=:BONUS_RATE , START_RATE=:START_RATE , END_RATE=:END_RATE  \n";
        query +=" where APPL_YEAR=:APPL_YEAR  \n";
        query +=" and APPL_SEQ=:APPL_SEQ";
        sobj.setSql(query);
        sobj.setDouble("END_RATE", END_RATE);               //종료 비율
        sobj.setDouble("START_RATE", START_RATE);               //시작 비율
        sobj.setInt("APPL_SEQ", APPL_SEQ);               //적용 순번
        sobj.setString("APPL_YEAR", APPL_YEAR);               //적용 년도
        sobj.setDouble("BONUS_RATE", BONUS_RATE);               //성과 요율
        return sobj;
    }
    //##**$$bra10_s07_save01
    //##**$$bra10_s07_copyYN
    /*
    */
    public DOBJ CTLbra10_s07_copyYN(DOBJ dobj)
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
            dobj  = CALLbra10_s07_copyYN_SEL1(Conn, dobj);           //  복사데이터유무
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
    public DOBJ CTLbra10_s07_copyYN( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra10_s07_copyYN_SEL1(Conn, dobj);           //  복사데이터유무
        return dobj;
    }
    // 복사데이터유무
    public DOBJ CALLbra10_s07_copyYN_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra10_s07_copyYN_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_s07_copyYN_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YEAR = dobj.getRetObject("S").getRecord().get("APPL_COPYYEAR");   //적용 년도
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  GIBU.TBRA_BONUS_PLOT  WHERE  APPL_YEAR  =  :APPL_YEAR ";
        sobj.setSql(query);
        sobj.setString("APPL_YEAR", APPL_YEAR);               //적용 년도
        return sobj;
    }
    //##**$$bra10_s07_copyYN
    //##**$$bra10_s07_copy
    /* * 프로그램명 : bra10_s07
    * 작성자 : 999999
    * 작성일 : 2009/09/25
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbra10_s07_copy(DOBJ dobj)
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
            dobj  = CALLbra10_s07_copy_DEL4(Conn, dobj);           //  성과급지급복사대상삭제
            dobj  = CALLbra10_s07_copy_SEL2(Conn, dobj);           //  성과급조회
            dobj  = CALLbra10_s07_copy_INS1(Conn, dobj);           //  성과급지급복사
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
    public DOBJ CTLbra10_s07_copy( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra10_s07_copy_DEL4(Conn, dobj);           //  성과급지급복사대상삭제
        dobj  = CALLbra10_s07_copy_SEL2(Conn, dobj);           //  성과급조회
        dobj  = CALLbra10_s07_copy_INS1(Conn, dobj);           //  성과급지급복사
        return dobj;
    }
    // 성과급지급복사대상삭제
    public DOBJ CALLbra10_s07_copy_DEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra10_s07_copy_DEL4(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL4") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_s07_copy_DEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YEAR = dobj.getRetObject("S").getRecord().get("APPL_COPYYEAR");   //적용 년도
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BONUS_PLOT  \n";
        query +=" where APPL_YEAR=:APPL_YEAR";
        sobj.setSql(query);
        sobj.setString("APPL_YEAR", APPL_YEAR);               //적용 년도
        return sobj;
    }
    // 성과급조회
    public DOBJ CALLbra10_s07_copy_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra10_s07_copy_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_s07_copy_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YEAR = dobj.getRetObject("S").getRecord().get("APPL_FROMCOPYYEAR");   //적용 년도
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  APPL_YEAR,  APPL_SEQ,  START_RATE,  END_RATE,  BONUS_RATE  FROM  GIBU.TBRA_BONUS_PLOT  WHERE  APPL_YEAR  =  :APPL_YEAR  ORDER  BY  APPL_SEQ ";
        sobj.setSql(query);
        sobj.setString("APPL_YEAR", APPL_YEAR);               //적용 년도
        return sobj;
    }
    // 성과급지급복사
    public DOBJ CALLbra10_s07_copy_INS1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS1");
        VOBJ dvobj = dobj.getRetObject("SEL2");           //성과급조회에서 생성시킨 OBJECT입니다.(CALLbra10_s07_copy_SEL2)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra10_s07_copy_INS1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_s07_copy_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        double   END_RATE = dvobj.getRecord().getDouble("END_RATE");   //종료 비율
        double   START_RATE = dvobj.getRecord().getDouble("START_RATE");   //시작 비율
        int   APPL_SEQ = dvobj.getRecord().getInt("APPL_SEQ");   //적용 순번
        double   BONUS_RATE = dvobj.getRecord().getDouble("BONUS_RATE");   //성과 요율
        String   APPL_YEAR = dobj.getRetObject("S").getRecord().get("APPL_COPYYEAR");   //적용 년도
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BONUS_PLOT (INS_DATE, INSPRES_ID, BONUS_RATE, APPL_YEAR, APPL_SEQ, START_RATE, END_RATE)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BONUS_RATE , :APPL_YEAR , :APPL_SEQ , :START_RATE , :END_RATE )";
        sobj.setSql(query);
        sobj.setDouble("END_RATE", END_RATE);               //종료 비율
        sobj.setDouble("START_RATE", START_RATE);               //시작 비율
        sobj.setInt("APPL_SEQ", APPL_SEQ);               //적용 순번
        sobj.setDouble("BONUS_RATE", BONUS_RATE);               //성과 요율
        sobj.setString("APPL_YEAR", APPL_YEAR);               //적용 년도
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    //##**$$bra10_s07_copy
    //##**$$end
}
