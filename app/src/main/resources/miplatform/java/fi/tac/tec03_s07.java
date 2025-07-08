
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tec03_s07
{
    public tec03_s07()
    {
    }
    //##**$$mngcomis_totalsave
    /* * 프로그램명 : tec03_s07
    * 작성자 : 이세준
    * 작성일 : 2009/10/06
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLmngcomis_totalsave(DOBJ dobj)
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
            dobj  = CALLmngcomis_totalsave_XIUD2(Conn, dobj);           //  수수료율월삭제
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
    public DOBJ CTLmngcomis_totalsave( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmngcomis_totalsave_XIUD2(Conn, dobj);           //  수수료율월삭제
        return dobj;
    }
    // 수수료율월삭제
    public DOBJ CALLmngcomis_totalsave_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmngcomis_totalsave_XIUD2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmngcomis_totalsave_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //적용 년월
        String   AVECLASS_CD = dobj.getRetObject("S").getRecord().get("AVECLASS_CD");   //매체중분류
        String   LARGECLASS_CD = dobj.getRetObject("S").getRecord().get("LARGECLASS_CD");   //대분류 코드
        double   MNGCOMIS_RATE = dobj.getRetObject("S").getRecord().getDouble("MNGCOMIS_RATE");   //요율
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE FIDU.tenv_mngcomis  \n";
        query +=" SET MNGCOMIS_RATE = :MNGCOMIS_RATE  \n";
        query +=" WHERE 1 = 1  \n";
        query +=" AND APPL_YRMN = :APPL_YRMN  \n";
        query +=" AND LARGECLASS_CD = :LARGECLASS_CD  \n";
        query +=" AND AVECLASS_CD = :AVECLASS_CD";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        sobj.setString("AVECLASS_CD", AVECLASS_CD);               //매체중분류
        sobj.setString("LARGECLASS_CD", LARGECLASS_CD);               //대분류 코드
        sobj.setDouble("MNGCOMIS_RATE", MNGCOMIS_RATE);               //요율
        return sobj;
    }
    //##**$$mngcomis_totalsave
    //##**$$mngcomis_save
    /* * 프로그램명 : tec03_s07
    * 작성자 : 이세준
    * 작성일 : 2009/10/06
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLmngcomis_save(DOBJ dobj)
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
            dobj  = CALLmngcomis_save_MIUD1(Conn, dobj);           //  수수료관리
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
    public DOBJ CTLmngcomis_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmngcomis_save_MIUD1(Conn, dobj);           //  수수료관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 수수료관리
    public DOBJ CALLmngcomis_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("MIUD1");
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmngcomis_save_INS5(Conn, dobj);           //  수수료율입력
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmngcomis_save_UPD6(Conn, dobj);           //  수수료율수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmngcomis_save_DEL7(Conn, dobj);           //  수수료율삭제
            }
        }
        return dobj;
    }
    // 수수료율삭제
    public DOBJ CALLmngcomis_save_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("DEL7");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmngcomis_save_DEL7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        rvobj.Println("DEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmngcomis_save_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
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
        query +=" Delete from FIDU.TENV_MNGCOMIS  \n";
        query +=" where LARGECLASS_CD=:LARGECLASS_CD  \n";
        query +=" and APPL_YRMN=:APPL_YRMN  \n";
        query +=" and MDM_CD=:MDM_CD  \n";
        query +=" and AVECLASS_CD=:AVECLASS_CD  \n";
        query +=" and MNGCOMIS_RATE=:MNGCOMIS_RATE  \n";
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
    // 수수료율입력
    public DOBJ CALLmngcomis_save_INS5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLmngcomis_save_INS5(dobj, dvobj);
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
    private SQLObject SQLmngcomis_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
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
        query +=" Insert into FIDU.TENV_MNGCOMIS (LARGECLASS_CD, APPL_YRMN, MDM_CD, AVECLASS_CD, MNGCOMIS_RATE, SCLASS_CD)  \n";
        query +=" values(:LARGECLASS_CD , :APPL_YRMN , :MDM_CD , :AVECLASS_CD , :MNGCOMIS_RATE , :SCLASS_CD )";
        sobj.setSql(query);
        sobj.setString("SCLASS_CD", SCLASS_CD);               //매체대분류코드
        sobj.setDouble("MNGCOMIS_RATE", MNGCOMIS_RATE);               //요율
        sobj.setString("AVECLASS_CD", AVECLASS_CD);               //매체중분류
        sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        sobj.setString("LARGECLASS_CD", LARGECLASS_CD);               //대분류 코드
        return sobj;
    }
    // 수수료율수정
    public DOBJ CALLmngcomis_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("UPD6");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmngcomis_save_UPD6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        rvobj.Println("UPD6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmngcomis_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
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
    //##**$$mngcomis_save
    //##**$$mngcomis_sel
    /* * 프로그램명 : tec03_s07
    * 작성자 : 손주환
    * 작성일 : 2009/11/18
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLmngcomis_sel(DOBJ dobj)
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
            dobj  = CALLmngcomis_sel_SEL1(Conn, dobj);           //  수수료율조회
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
    public DOBJ CTLmngcomis_sel( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmngcomis_sel_SEL1(Conn, dobj);           //  수수료율조회
        return dobj;
    }
    // 수수료율조회
    public DOBJ CALLmngcomis_sel_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmngcomis_sel_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmngcomis_sel_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   LARGECLASS_CD = dobj.getRetObject("S").getRecord().get("LARGECLASS_CD");   //대분류 코드
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //적용 년월
        String   MDM_CD = dobj.getRetObject("S").getRecord().get("MDM_CD");   //매체 코드
        String   AVECLASS_CD = dobj.getRetObject("S").getRecord().get("AVECLASS_CD");   //매체중분류
        String   SCLASS_CD = dobj.getRetObject("S").getRecord().get("SCLASS_CD");   //매체대분류코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.LARGECLASS_CD, A.LARGECLASS_CD_NM, A.AVECLASS_CD, A.AVECLASS_CD_NM, A.SCLASS_CD, A.SCLASS_CD_NM, A.MDM_CD, A.MDM_CD_NM, :APPL_YRMN AS APPL_YRMN, B.MNGCOMIS_RATE  ";
        query +=" FROM FIDU.TENV_MDMCD A ,FIDU.TENV_MNGCOMIS B , fidu.tenv_largeclasscd c, FIDU.TENV_AVECLASSCD d, FIDU.TENV_SCLASSCD f  ";
        query +=" WHERE A.LARGECLASS_CD = B.LARGECLASS_CD  ";
        query +=" AND A.AVECLASS_CD = B.AVECLASS_CD  ";
        query +=" AND A.SCLASS_CD = B.SCLASS_CD  ";
        query +=" AND A.MDM_CD = B.MDM_CD  ";
        query +=" AND B.APPL_YRMN = :APPL_YRMN  ";
        query +=" AND A.LARGECLASS_CD = C.LARGECLASS_CD  ";
        query +=" AND A.AVECLASS_CD = D.AVECLASS_CD  ";
        query +=" AND A.SCLASS_CD = F.SCLASS_CD  ";
        if( !LARGECLASS_CD.equals("0"))
        {
            query +=" AND A.LARGECLASS_CD = :LARGECLASS_CD  ";
        }
        if( !AVECLASS_CD.equals("0"))
        {
            query +=" AND A.AVECLASS_CD = :AVECLASS_CD  ";
        }
        if( !SCLASS_CD.equals("0"))
        {
            query +=" AND A.SCLASS_CD = :SCLASS_CD  ";
        }
        if( !MDM_CD.equals("0"))
        {
            query +=" AND A.MDM_CD = :MDM_CD  ";
        }
        query +=" ORDER BY C.SORT_SEQ, D.SORT_SEQ, F.SORT_SEQ, A.SORT_SEQ  ";
        sobj.setSql(query);
        if( !LARGECLASS_CD.equals("0"))
        {
            sobj.setString("LARGECLASS_CD", LARGECLASS_CD);               //대분류 코드
        }
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        if( !MDM_CD.equals("0"))
        {
            sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        }
        if( !AVECLASS_CD.equals("0"))
        {
            sobj.setString("AVECLASS_CD", AVECLASS_CD);               //매체중분류
        }
        if( !SCLASS_CD.equals("0"))
        {
            sobj.setString("SCLASS_CD", SCLASS_CD);               //매체대분류코드
        }
        return sobj;
    }
    //##**$$mngcomis_sel
    //##**$$end
}
