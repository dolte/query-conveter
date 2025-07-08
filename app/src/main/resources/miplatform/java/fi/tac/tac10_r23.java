
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r23
{
    public tac10_r23()
    {
    }
    //##**$$tac10_r23_select
    /*
    */
    public DOBJ CTLtac10_r23_select(DOBJ dobj)
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
            dobj  = CALLtac10_r23_select_SEL1(Conn, dobj);           //  매체별 작가별 보류현황조회
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
    public DOBJ CTLtac10_r23_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r23_select_SEL1(Conn, dobj);           //  매체별 작가별 보류현황조회
        return dobj;
    }
    // 매체별 작가별 보류현황조회
    public DOBJ CALLtac10_r23_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r23_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r23_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT Z.ORGAU_MB_CD, FIDU.GET_MB_NM (Z.ORGAU_MB_CD) AS ORGAU_MB_NM, (SELECT AVECLASS_CD_NM  ";
        query +=" FROM FIDU.TENV_AVECLASSCD  ";
        query +=" WHERE AVECLASS_CD = Z.AVECLASS_CD) AS AVECLASS_CD_NM, Z.AVECLASS_CD, SUM (Z.DISTR_AMT) AS DISTR_AMT  ";
        query +=" FROM (  ";
        query +=" SELECT A.DISTR_YRMN, SUBSTR (A.MDM_CD, 1, 2) AS AVECLASS_CD, A.ORGAU_MB_CD, SUM (DISTR_AMT) AS DISTR_AMT  ";
        query +=" FROM FIDU.TTAC_PRODSUPPSUSP A  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND SUPP_YRMN IS NULL  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND A.ORGAU_MB_CD = :MB_CD  ";
        }
        query +=" GROUP BY A.DISTR_YRMN, A.MDM_CD, A.ORGAU_MB_CD) Z  ";
        query +=" GROUP BY Z.ORGAU_MB_CD, Z.AVECLASS_CD  ";
        query +=" ORDER BY Z.ORGAU_MB_CD, ORGAU_MB_NM, Z.AVECLASS_CD  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        return sobj;
    }
    //##**$$tac10_r23_select
    //##**$$end
}
