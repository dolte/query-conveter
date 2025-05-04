
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra00_r00
{
    public bra00_r00()
    {
    }
    //##**$$sel_udtkpres_dept
    /*
    */
    public DOBJ CTLsel_udtkpres_dept(DOBJ dobj)
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
            dobj  = CALLsel_udtkpres_dept_SEL1(Conn, dobj);           //  지부관리자 담당 지부 조회
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
    public DOBJ CTLsel_udtkpres_dept( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_udtkpres_dept_SEL1(Conn, dobj);           //  지부관리자 담당 지부 조회
        return dobj;
    }
    // 지부관리자 담당 지부 조회
    public DOBJ CALLsel_udtkpres_dept_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_udtkpres_dept_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_udtkpres_dept_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_NUM = dobj.getRetObject("S").getRecord().get("STAFF_NUM");   //사원번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :STAFF_NUM  ORDER  BY  DEPT_CD ";
        sobj.setSql(query);
        sobj.setString("STAFF_NUM", STAFF_NUM);               //사원번호
        return sobj;
    }
    //##**$$sel_udtkpres_dept
    //##**$$end
}
