
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra06_p04
{
    public bra06_p04()
    {
    }
    //##**$$enternet_upso
    /*
    */
    public DOBJ CTLenternet_upso(DOBJ dobj)
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
            dobj  = CALLenternet_upso_SEL1(Conn, dobj);           //  인터넷반주기
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
    public DOBJ CTLenternet_upso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLenternet_upso_SEL1(Conn, dobj);           //  인터넷반주기
        return dobj;
    }
    // 인터넷반주기
    public DOBJ CALLenternet_upso_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLenternet_upso_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLenternet_upso_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  'UPSO_CD'  UPSO_CD  ,  'UPSO_NM'  UPSO_NM  ,  'MNGEMSTR_ADDRONE'  MNGEMSTR_ADDRONE  ,  'MNGEMSTR_PHONNUM'  MNGEMSTR_PHONNUM  ,  'MNGEMSTR_HPNUM'  MNGEMSTR_HPNUM  ,  'MDM_CD'  MDM_CD  ,  'ACMCN_TOTAL'  ACMCN_TOTAL  ,  'ACMCN_DAESU'  ACMCN_DAESU  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$enternet_upso
    //##**$$end
}
