
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac03_s07
{
    public tac03_s07()
    {
    }
    //##**$$ttac_yearfeed_save
    /* * 프로그램명 : tac03_s07
    * 작성자 : 하근철
    * 작성일 : 2009/08/17
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLttac_yearfeed_save(DOBJ dobj)
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
            dobj  = CALLttac_yearfeed_save_MIUD1(Conn, dobj);           //  년회비공제MIUD
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
    public DOBJ CTLttac_yearfeed_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttac_yearfeed_save_MIUD1(Conn, dobj);           //  년회비공제MIUD
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 년회비공제MIUD
    public DOBJ CALLttac_yearfeed_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLttac_yearfeed_save_INS5(Conn, dobj);           //  년회비공제저장
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLttac_yearfeed_save_UPD6(Conn, dobj);           //  년회비공제수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLttac_yearfeed_save_DEL7(Conn, dobj);           //  년회비공제삭제
            }
        }
        return dobj;
    }
    // 년회비공제삭제
    public DOBJ CALLttac_yearfeed_save_DEL7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLttac_yearfeed_save_DEL7(dobj, dvobj);
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
    private SQLObject SQLttac_yearfeed_save_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_YEARFEEDEDCT  \n";
        query +=" where";
        sobj.setSql(query);
        return sobj;
    }
    // 년회비공제저장
    public DOBJ CALLttac_yearfeed_save_INS5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLttac_yearfeed_save_INS5(dobj, dvobj);
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
    private SQLObject SQLttac_yearfeed_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_YEARFEEDEDCT ( values(";
        sobj.setSql(query);
        return sobj;
    }
    // 년회비공제수정
    public DOBJ CALLttac_yearfeed_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLttac_yearfeed_save_UPD6(dobj, dvobj);
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
    private SQLObject SQLttac_yearfeed_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_YEARFEEDEDCT  \n";
        query +=" set  \n";
        query +=" where";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$ttac_yearfeed_save
    //##**$$ttac_yearfeed_select
    /* * 프로그램명 : tac03_s07
    * 작성자 : 하근철
    * 작성일 : 2009/08/17
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLttac_yearfeed_select(DOBJ dobj)
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
            dobj  = CALLttac_yearfeed_select_SEL1(Conn, dobj);           //  년회비공제조회
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
    public DOBJ CTLttac_yearfeed_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttac_yearfeed_select_SEL1(Conn, dobj);           //  년회비공제조회
        return dobj;
    }
    // 년회비공제조회
    public DOBJ CALLttac_yearfeed_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLttac_yearfeed_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_yearfeed_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEDCT_YRMN = dvobj.getRecord().get("DEDCT_YRMN");   //공제 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEDCT_YRMN,  GENRMB_DEDCT_AMT,  PUBCMD_DEDCT_AMT  FROM  FIDU.TTAC_YEARFEEDEDCT  WHERE  DEDCT_YRMN  =  :DEDCT_YRMN ";
        sobj.setSql(query);
        sobj.setString("DEDCT_YRMN", DEDCT_YRMN);               //공제 년월
        return sobj;
    }
    //##**$$ttac_yearfeed_select
    //##**$$mngcomis_transfer
    /* * 프로그램명 : tec03_s07
    * 작성자 : 손주환
    * 작성일 : 2009/11/05
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLmngcomis_transfer(DOBJ dobj)
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
            dobj  = CALLmngcomis_transfer_XIUD3(Conn, dobj);           //  수수료율월삭제
            dobj  = CALLmngcomis_transfer_XIUD1(Conn, dobj);           //  수수료율이월
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
    public DOBJ CTLmngcomis_transfer( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmngcomis_transfer_XIUD3(Conn, dobj);           //  수수료율월삭제
        dobj  = CALLmngcomis_transfer_XIUD1(Conn, dobj);           //  수수료율이월
        return dobj;
    }
    // 수수료율월삭제
    public DOBJ CALLmngcomis_transfer_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmngcomis_transfer_XIUD3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmngcomis_transfer_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //적용 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM FIDU.tenv_mngcomis  \n";
        query +=" WHERE APPL_YRMN = TO_CHAR(ADD_MONTHS(TO_DATE(:APPL_YRMN,'YYYYMM'),1),'YYYYMM')";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        return sobj;
    }
    // 수수료율이월
    public DOBJ CALLmngcomis_transfer_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmngcomis_transfer_XIUD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        rvobj.Println("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmngcomis_transfer_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //적용 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.tenv_mngcomis( SELECT A.LARGECLASS_CD, A.AVECLASS_CD, A.SCLASS_CD, A.MDM_CD, TO_CHAR(ADD_MONTHS(TO_DATE(:APPL_YRMN,'YYYYMM'),1),'YYYYMM'), NVL(B.MNGCOMIS_RATE,0) MNGCOMIS_RATE FROM FIDU.TENV_MDMCD A, (SELECT MDM_CD, MNGCOMIS_RATE FROM FIDU.tenv_mngcomis WHERE APPL_YRMN = :APPL_YRMN )B WHERE A.MDM_CD = B.MDM_CD(+) AND (A.USE_YN ='Y' OR A.MDM_CD ='CD0999'))";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        return sobj;
    }
    //##**$$mngcomis_transfer
    //##**$$saveMst07
    /* * 프로그램명 : tac03_s07
    * 작성자 : 이세준
    * 작성일 : 2010/05/13
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsaveMst07(DOBJ dobj)
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
            dobj  = CALLsaveMst07_MIUD4(Conn, dobj);           //  수수료중분류입력
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
    public DOBJ CTLsaveMst07( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsaveMst07_MIUD4(Conn, dobj);           //  수수료중분류입력
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 수수료중분류입력
    public DOBJ CALLsaveMst07_MIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD4");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("MIUD4");
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsaveMst07_INS2(Conn, dobj);           //  수수료율저장
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsaveMst07_UPD7(Conn, dobj);           //  중분류저장
            }
        }
        return dobj;
    }
    // 수수료율저장
    public DOBJ CALLsaveMst07_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS2");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveMst07_INS2(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveMst07_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SCLASS_CD = dvobj.getRecord().get("SCLASS_CD");   //매체대분류코드
        double   MNGCOMIS_RATE = dvobj.getRecord().getDouble("MNGCOMIS_RATE");   //요율
        String   AVECLASS_CD = dvobj.getRecord().get("AVECLASS_CD");   //매체중분류
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //매체 코드
        String   LARGECLASS_CD = dvobj.getRecord().get("LARGECLASS_CD");   //대분류 코드
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //적용 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TENV_MNGCOMIS (LARGECLASS_CD, APPL_YRMN, MDM_CD, AVECLASS_CD, MNGCOMIS_RATE, SCLASS_CD)  \n";
        query +=" values(:LARGECLASS_CD , :APPL_YRMN , :MDM_CD , :AVECLASS_CD , :MNGCOMIS_RATE , :SCLASS_CD )";
        sobj.setSql(query);
        sobj.setString("SCLASS_CD", SCLASS_CD);               //매체대분류코드
        sobj.setDouble("MNGCOMIS_RATE", MNGCOMIS_RATE);               //요율
        sobj.setString("AVECLASS_CD", AVECLASS_CD);               //매체중분류
        sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        sobj.setString("LARGECLASS_CD", LARGECLASS_CD);               //대분류 코드
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        return sobj;
    }
    // 중분류저장
    public DOBJ CALLsaveMst07_UPD7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsaveMst07_UPD7(dobj, dvobj);
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
    private SQLObject SQLsaveMst07_UPD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SCLASS_CD = dvobj.getRecord().get("SCLASS_CD");   //매체대분류코드
        double   MNGCOMIS_RATE = dvobj.getRecord().getDouble("MNGCOMIS_RATE");   //요율
        String   AVECLASS_CD = dvobj.getRecord().get("AVECLASS_CD");   //매체중분류
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //매체 코드
        String   APPL_YRMN = dvobj.getRecord().get("APPL_YRMN");   //적용 년월
        String   LARGECLASS_CD = dvobj.getRecord().get("LARGECLASS_CD");   //대분류 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TENV_MNGCOMIS  \n";
        query +=" set MNGCOMIS_RATE=:MNGCOMIS_RATE  \n";
        query +=" where LARGECLASS_CD=:LARGECLASS_CD  \n";
        query +=" and APPL_YRMN=:APPL_YRMN  \n";
        query +=" and MDM_CD=:MDM_CD  \n";
        query +=" and AVECLASS_CD=:AVECLASS_CD  \n";
        query +=" and SCLASS_CD=:SCLASS_CD";
        sobj.setSql(query);
        sobj.setString("SCLASS_CD", SCLASS_CD);               //매체대분류코드
        sobj.setDouble("MNGCOMIS_RATE", MNGCOMIS_RATE);               //요율
        sobj.setString("AVECLASS_CD", AVECLASS_CD);               //매체중분류
        sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        sobj.setString("LARGECLASS_CD", LARGECLASS_CD);               //대분류 코드
        return sobj;
    }
    //##**$$saveMst07
    //##**$$selAveClass
    /*
    */
    public DOBJ CTLselAveClass(DOBJ dobj)
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
            dobj  = CALLselAveClass_SEL1(Conn, dobj);           //  매체중분조회
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
    public DOBJ CTLselAveClass( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLselAveClass_SEL1(Conn, dobj);           //  매체중분조회
        return dobj;
    }
    // 매체중분조회
    public DOBJ CALLselAveClass_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselAveClass_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselAveClass_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   AVECLASS_CD = dobj.getRetObject("S").getRecord().get("AVECLASS_CD");   //매체중분류
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LARGECLASS_CD,  LARGECLASS_CD_NM,  AVECLASS_CD,  AVECLASS_CD_NM,  SCLASS_CD,  SCLASS_CD_NM,  MDM_CD,  MDM_CD_NM  FROM  FIDU.TENV_MDMCD  WHERE  AVECLASS_CD  =  :AVECLASS_CD ";
        sobj.setSql(query);
        sobj.setString("AVECLASS_CD", AVECLASS_CD);               //매체중분류
        return sobj;
    }
    //##**$$selAveClass
    //##**$$end
}
