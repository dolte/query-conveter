
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class search_acncmInfo
{
    public search_acncmInfo()
    {
    }
    //##**$$ACNCMINFO_SEARCH
    /*
    */
    public DOBJ CTLACNCMINFO_SEARCH(DOBJ dobj)
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
            dobj  = CALLACNCMINFO_SEARCH_SEL7(Conn, dobj);           //  온/오프기기 정보조회
            dobj  = CALLACNCMINFO_SEARCH_SEL3(Conn, dobj);           //  오브리 정보조회
            dobj  = CALLACNCMINFO_SEARCH_SEL4(Conn, dobj);           //  금영_반주기정보
            dobj  = CALLACNCMINFO_SEARCH_SEL5(Conn, dobj);           //  태진_반주기정보
            dobj  = CALLACNCMINFO_SEARCH_SEL6(Conn, dobj);           //  기타_반주기정보
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
    public DOBJ CTLACNCMINFO_SEARCH( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLACNCMINFO_SEARCH_SEL7(Conn, dobj);           //  온/오프기기 정보조회
        dobj  = CALLACNCMINFO_SEARCH_SEL3(Conn, dobj);           //  오브리 정보조회
        dobj  = CALLACNCMINFO_SEARCH_SEL4(Conn, dobj);           //  금영_반주기정보
        dobj  = CALLACNCMINFO_SEARCH_SEL5(Conn, dobj);           //  태진_반주기정보
        dobj  = CALLACNCMINFO_SEARCH_SEL6(Conn, dobj);           //  기타_반주기정보
        return dobj;
    }
    // 온/오프기기 정보조회
    // 상단 온/오프기기 조회
    public DOBJ CALLACNCMINFO_SEARCH_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLACNCMINFO_SEARCH_SEL7(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLACNCMINFO_SEARCH_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S5").getRecord().get("UPSO_CD");   //업소코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ONOFF_GBN,  MODEL_CD,  ACMCN_DAESU  FROM  GIBU.TBRA_UPSOCLASS_ONOFF_INFO  WHERE  UPSO_CD=:UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소코드
        return sobj;
    }
    // 오브리 정보조회
    // 중단 오브리정보조회
    public DOBJ CALLACNCMINFO_SEARCH_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLACNCMINFO_SEARCH_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLACNCMINFO_SEARCH_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S5").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MODEL_CD  ,MODEL_NM  ,MCHN_COMPYNM  ,MCHN_COMPY  FROM  GIBU.TBRA_UPSOCLASS_AUBRY_INFO  WHERE  UPSO_CD=:UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 금영_반주기정보
    // 하단_금영_반주기정보
    public DOBJ CALLACNCMINFO_SEARCH_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLACNCMINFO_SEARCH_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLACNCMINFO_SEARCH_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S5").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MODEL_CD  ,A.ACMCN_DAESU  ,B.MCHN_COMPY  FROM  GIBU.TBRA_UPSOCLASS_ACMCN_INFO  A,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.UPSO_CD  =:UPSO_CD   \n";
        query +=" AND  B.MCHN_COMPY  =  'E0006' ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 태진_반주기정보
    // 하단_태진_반주기정보
    public DOBJ CALLACNCMINFO_SEARCH_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLACNCMINFO_SEARCH_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLACNCMINFO_SEARCH_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S5").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MODEL_CD  ,A.ACMCN_DAESU  ,B.MCHN_COMPY  FROM  GIBU.TBRA_UPSOCLASS_ACMCN_INFO  A,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.UPSO_CD  =:UPSO_CD   \n";
        query +=" AND  B.MCHN_COMPY  =  'E0003' ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 기타_반주기정보
    // 하단_기타_반주기정보
    public DOBJ CALLACNCMINFO_SEARCH_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLACNCMINFO_SEARCH_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLACNCMINFO_SEARCH_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S5").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MODEL_CD  ,A.ACMCN_DAESU  ,B.MCHN_COMPY  FROM  GIBU.TBRA_UPSOCLASS_ACMCN_INFO  A,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.UPSO_CD  =:UPSO_CD   \n";
        query +=" AND  B.MCHN_COMPY  NOT  IN  ('E0006','E0003') ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$ACNCMINFO_SEARCH
    //##**$$end
}
