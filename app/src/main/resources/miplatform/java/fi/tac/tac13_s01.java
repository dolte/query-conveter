
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac13_s01
{
    public tac13_s01()
    {
    }
    //##**$$search_sup
    /*
    */
    public DOBJ CTLsearch_sup(DOBJ dobj)
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
            dobj  = CALLsearch_sup_SEL1(Conn, dobj);           //  해제월별 작가별 보류해제금액
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
    public DOBJ CTLsearch_sup( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_sup_SEL1(Conn, dobj);           //  해제월별 작가별 보류해제금액
        return dobj;
    }
    // 해제월별 작가별 보류해제금액
    public DOBJ CALLsearch_sup_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_sup_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_sup_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.WRK_YRMN  AS  SUBSTR,  FIDU.GET_MDM_NM(A.MDM_CD)  AS  MDM_CD_NM,  A.ORGAU_MB_CD  AS  MB_CD,  FIDU.GET_MB_NM(A.ORGAU_MB_CD)  AS  HANMB_NM,  SUM(A.DISTR_AMT)  AS  RELS_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  A  WHERE  A.SUPP_YRMN  =  :SUPP_YRMN  GROUP  BY  A.WRK_YRMN,  A.MDM_CD,  A.ORGAU_MB_CD  ORDER  BY  SUBSTR,  HANMB_NM,MB_CD ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$search_sup
    //##**$$end
}
