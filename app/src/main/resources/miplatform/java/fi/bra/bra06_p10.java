
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra06_p10
{
    public bra06_p10()
    {
    }
    //##**$$new_upso_list
    /*
    */
    public DOBJ CTLnew_upso_list(DOBJ dobj)
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
            dobj  = CALLnew_upso_list_SEL1(Conn, dobj);           //  현황
            dobj  = CALLnew_upso_list_SEL2(Conn, dobj);           //  통계
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
    public DOBJ CTLnew_upso_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLnew_upso_list_SEL1(Conn, dobj);           //  현황
        dobj  = CALLnew_upso_list_SEL2(Conn, dobj);           //  통계
        return dobj;
    }
    // 현황
    public DOBJ CALLnew_upso_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLnew_upso_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnew_upso_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '1234567K'  UPSO_CD  ,	'2'  GRADNM  ,	3  MONPRNCFEE  ,	'4'  UPSO_NM  ,	'5'  MNGEMSTR_NM  ,	'6'  UPSO_ADDR  ,	'7'  UPSO_PHON  ,	'200901200910'  NONPY_YRMN  ,	9  NONPY_AMT  ,	'200901200910'  ACCU_SOL_YRMN  ,	11  ACCU_SOL_AMT  ,	'홍'  STAFF_NM  ,	'업,변>  '  BIGO  ,	'길'  BEFORE_MNGEMSTR_NM  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 통계
    public DOBJ CALLnew_upso_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLnew_upso_list_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnew_upso_list_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  1  TOT_AMT  ,  2  CNT  ,  '동'  NAME  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$new_upso_list
    //##**$$end
}
