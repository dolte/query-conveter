
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class lev05_p05
{
    public lev05_p05()
    {
    }
    //##**$$tlev_ctstnum_select
    /*
    */
    public DOBJ CTLtlev_ctstnum_select(DOBJ dobj)
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
            dobj  = CALLtlev_ctstnum_select_SEL1(Conn, dobj);           //  증지발행현황
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
    public DOBJ CTLtlev_ctstnum_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtlev_ctstnum_select_SEL1(Conn, dobj);           //  증지발행현황
        return dobj;
    }
    // 증지발행현황
    public DOBJ CALLtlev_ctstnum_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtlev_ctstnum_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtlev_ctstnum_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT ISSUE_DAY, SVC_NUM, FIDU.GET_BSCON_NM(FIDU.TLEV_CTSTNUMISSUEBRE.BSCON_CD) AS BSCON_NM, CTST_GBN, CTST_START_NUM, CTST_END_NUM, REMAK_CTENT, PRCON_GBN, ISSUE_QTY  ";
        query +=" FROM FIDU.TLEV_CTSTNUMISSUEBRE  ";
        query +=" WHERE 1=1  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD= :BSCON_CD  ";
        }
        sobj.setSql(query);
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        }
        return sobj;
    }
    //##**$$tlev_ctstnum_select
    //##**$$end
}
