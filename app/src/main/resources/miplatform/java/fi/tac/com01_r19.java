
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class com01_r19
{
    public com01_r19()
    {
    }
    //##**$$brod_select
    /*
    */
    public DOBJ CTLbrod_select(DOBJ dobj)
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
            dobj  = CALLbrod_select_SEL1(Conn, dobj);           //  방송정산을위한승인검색
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
    public DOBJ CTLbrod_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbrod_select_SEL1(Conn, dobj);           //  방송정산을위한승인검색
        return dobj;
    }
    // 방송정산을위한승인검색
    public DOBJ CALLbrod_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbrod_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbrod_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USE_TTL = dvobj.getRecord().get("USE_TTL");   //사용제목
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.APPRV_NUM AS APPRV_NUM, A.MDM_CD AS MDM_CD, FIDU.GET_MDM_NM(A.MDM_CD) AS MDM_NM, B.BSCON_CD AS BSCON_CD, FIDU.GET_BSCON_NM(B.BSCON_CD) AS BSCON_NM, A.USE_TTL AS USE_TTL  ";
        query +=" FROM FIDU.TLEV_USEAPPRV A, FIDU.TLEV_APPTNPRESINFO B  ";
        query +=" WHERE A.APPRV_NUM = B.APPRV_NUM  ";
        query +=" AND A.MDM_CD LIKE 'A%'  ";
        query +=" AND B.APPTNPRES_GBN = '1'  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND B.BSCON_CD = :BSCON_CD  ";
        }
        if( !USE_TTL.equals("") )
        {
            query +=" AND A.USE_TTL LIKE '%' || :USE_TTL || '%'  ";
        }
        query +=" ORDER BY APPRV_NUM DESC  ";
        sobj.setSql(query);
        if(!USE_TTL.equals(""))
        {
            sobj.setString("USE_TTL", USE_TTL);               //사용제목
        }
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        }
        return sobj;
    }
    //##**$$brod_select
    //##**$$end
}
