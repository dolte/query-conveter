
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class find_acmcn
{
    public find_acmcn()
    {
    }
    //##**$$acmcnModel_search
    /*
    */
    public DOBJ CTLacmcnModel_searc(DOBJ dobj)
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
            if(!dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("E0003") && !dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("E0006"))
            {
                dobj  = CALLacmcnModel_search_SEL2(Conn, dobj);           //  반주기정보조회
            }
            else
            {
                dobj  = CALLacmcnModel_search_SEL1(Conn, dobj);           //  반주기정보조회
            }
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
    public DOBJ CTLacmcnModel_searc( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if(!dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("E0003") && !dobj.getRetObject("S").getRecord().get("MCHN_COMPY").equals("E0006"))
        {
            dobj  = CALLacmcnModel_search_SEL2(Conn, dobj);           //  반주기정보조회
        }
        else
        {
            dobj  = CALLacmcnModel_search_SEL1(Conn, dobj);           //  반주기정보조회
        }
        return dobj;
    }
    // 반주기정보조회
    public DOBJ CALLacmcnModel_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLacmcnModel_search_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLacmcnModel_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MCHN_COMPY = dobj.getRetObject("S").getRecord().get("MCHN_COMPY");   //기계 회사
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MCHN_COMPY  ,  MODEL_CD  ,  MODEL_NM  ,  GBN  FROM  GIBU.TBRA_ACMCN_MODEL  WHERE  MCHN_COMPY  =  :MCHN_COMPY ";
        sobj.setSql(query);
        sobj.setString("MCHN_COMPY", MCHN_COMPY);               //기계 회사
        return sobj;
    }
    // 반주기정보조회
    public DOBJ CALLacmcnModel_search_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLacmcnModel_search_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLacmcnModel_search_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MCHN_COMPY  ,  MODEL_CD  ,  MODEL_NM  ,  GBN  FROM  GIBU.TBRA_ACMCN_MODEL  WHERE  MCHN_COMPY  NOT  IN  ('E0003',  'E0006') ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$acmcnModel_search
    //##**$$end
}
