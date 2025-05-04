
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s08
{
    public bra10_s08()
    {
    }
    //##**$$save_udtkpres
    /*
    */
    public DOBJ CTLsave_udtkpres(DOBJ dobj)
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
            dobj  = CALLsave_udtkpres_MIUD1(Conn, dobj);           //  분기
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
    public DOBJ CTLsave_udtkpres( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_udtkpres_MIUD1(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLsave_udtkpres_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLsave_udtkpres_XIUD11(Conn, dobj);           //  담당자 삽입
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_udtkpres_XIUD10(Conn, dobj);           //  담당자 수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_udtkpres_XIUD7(Conn, dobj);           //  담당자 삭제
            }
        }
        return dobj;
    }
    // 담당자 삭제
    public DOBJ CALLsave_udtkpres_XIUD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD7");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("XIUD7");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_udtkpres_XIUD7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_udtkpres_XIUD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEPT_CD = dobj.getRetObject("R").getRecord().get("DEPT_CD");   //부서 코드
        String   DEPT_CD1 = dobj.getRetObject("R").getRecord().get("DEPT_CD1");   //부서 코드1
        String   STAFF_CD = dobj.getRetObject("R").getRecord().get("STAFF_CD");   //사원 코드
        String   UDTKPRES_ID = dobj.getRetObject("R").getRecord().get("UDTKPRES_ID");   //담당자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_BILL_AUTH  \n";
        query +=" WHERE DEPT_CD = NVL(:DEPT_CD1, :DEPT_CD)  \n";
        query +=" AND GBN = 'B'  \n";
        query +=" AND UDTKPRES_ID = NVL(:STAFF_CD, :UDTKPRES_ID)";
        sobj.setSql(query);
        sobj.setString("DEPT_CD", DEPT_CD);               //부서 코드
        sobj.setString("DEPT_CD1", DEPT_CD1);               //부서 코드1
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("UDTKPRES_ID", UDTKPRES_ID);               //담당자 ID
        return sobj;
    }
    // 담당자 삽입
    public DOBJ CALLsave_udtkpres_XIUD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD11");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("XIUD11");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_udtkpres_XIUD11(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_udtkpres_XIUD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEPT_CD = dobj.getRetObject("R").getRecord().get("DEPT_CD");   //부서 코드
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   UDTKPRES_ID = dobj.getRetObject("R").getRecord().get("UDTKPRES_ID");   //담당자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_BILL_AUTH (DEPT_CD, UDTKPRES_ID, INSPRES_ID, INS_DATE, GBN) SELECT :DEPT_CD , :UDTKPRES_ID , :INSPRES_ID , SYSDATE , 'B' FROM DUAL";
        sobj.setSql(query);
        sobj.setString("DEPT_CD", DEPT_CD);               //부서 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("UDTKPRES_ID", UDTKPRES_ID);               //담당자 ID
        return sobj;
    }
    // 담당자 수정
    public DOBJ CALLsave_udtkpres_XIUD10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD10");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("XIUD10");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_udtkpres_XIUD10(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_udtkpres_XIUD10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEPT_CD = dobj.getRetObject("R").getRecord().get("DEPT_CD");   //부서 코드
        String   DEPT_CD1 = dobj.getRetObject("R").getRecord().get("DEPT_CD1");   //부서 코드1
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   STAFF_CD = dobj.getRetObject("R").getRecord().get("STAFF_CD");   //사원 코드
        String   UDTKPRES_ID = dobj.getRetObject("R").getRecord().get("UDTKPRES_ID");   //담당자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_BILL_AUTH  \n";
        query +=" SET UDTKPRES_ID = :UDTKPRES_ID , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE , DEPT_CD = :DEPT_CD  \n";
        query +=" WHERE DEPT_CD = NVL(:DEPT_CD1, :DEPT_CD)  \n";
        query +=" AND GBN = 'B'  \n";
        query +=" AND UDTKPRES_ID = NVL(:STAFF_CD, :UDTKPRES_ID)";
        sobj.setSql(query);
        sobj.setString("DEPT_CD", DEPT_CD);               //부서 코드
        sobj.setString("DEPT_CD1", DEPT_CD1);               //부서 코드1
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("UDTKPRES_ID", UDTKPRES_ID);               //담당자 ID
        return sobj;
    }
    //##**$$save_udtkpres
    //##**$$sel_udtkpres
    /*
    */
    public DOBJ CTLsel_udtkpres(DOBJ dobj)
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
            dobj  = CALLsel_udtkpres_SEL1(Conn, dobj);           //  지부별 담당자 조회
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
    public DOBJ CTLsel_udtkpres( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_udtkpres_SEL1(Conn, dobj);           //  지부별 담당자 조회
        return dobj;
    }
    // 지부별 담당자 조회
    public DOBJ CALLsel_udtkpres_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_udtkpres_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_udtkpres_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_NUM = dobj.getRetObject("S").getRecord().get("STAFF_NUM");   //사원번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.DEPT_CD AS DEPT_CD , (SELECT DEPT_NM  ";
        query +=" FROM INSA.TCPM_DEPT  ";
        query +=" WHERE DEPT_CD = A.DEPT_CD) AS DEPT_NM , A.UDTKPRES_ID AS UDTKPRES_ID , (SELECT HAN_NM  ";
        query +=" FROM INSA.TINS_MST01  ";
        query +=" WHERE STAFF_NUM = A.UDTKPRES_ID) AS UDTKPRES_NM , A.INSPRES_ID AS INSPRES_ID , (SELECT HAN_NM  ";
        query +=" FROM INSA.TINS_MST01  ";
        query +=" WHERE STAFF_NUM = A.INSPRES_ID) AS INSPRES_NM , A.INS_DATE AS INS_DATE , A.MODPRES_ID AS MODPRES_ID , (SELECT HAN_NM  ";
        query +=" FROM INSA.TINS_MST01  ";
        query +=" WHERE STAFF_NUM = A.MODPRES_ID) AS MODPRES_NM , A.MOD_DATE AS MOD_DATE  ";
        query +=" FROM GIBU.TBRA_BILL_AUTH A  ";
        query +=" WHERE GBN = 'B'  ";
        if( !STAFF_NUM.equals("") )
        {
            query +=" AND UDTKPRES_ID = :STAFF_NUM  ";
        }
        query +=" ORDER BY A.DEPT_CD  ";
        sobj.setSql(query);
        if(!STAFF_NUM.equals(""))
        {
            sobj.setString("STAFF_NUM", STAFF_NUM);               //사원번호
        }
        return sobj;
    }
    //##**$$sel_udtkpres
    //##**$$end
}
