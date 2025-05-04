
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra08_s09
{
    public bra08_s09()
    {
    }
    //##**$$get_komca_data
    /*
    */
    public DOBJ CTLget_komca_data(DOBJ dobj)
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
            dobj  = CALLget_komca_data_SEL1(Conn, dobj);           //  업소조회
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
    public DOBJ CTLget_komca_data( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_komca_data_SEL1(Conn, dobj);           //  업소조회
        return dobj;
    }
    // 업소조회
    public DOBJ CALLget_komca_data_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_komca_data_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_komca_data_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ADDR = dobj.getRetObject("S").getRecord().get("ADDR");   //거주주소
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  UPSO_CD  ,  UPSO_NM  ,  UPSO_NEW_ADDR1  ||  '  '  ||  UPSO_NEW_ADDR2  ||  UPSO_REF_INFO  AS  ADDR  ,  OPBI_DAY  ,  CLSBS_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_NEW_ADDR1  LIKE  :ADDR  ||  '%'  ORDER  BY  UPSO_NM,  OPBI_DAY ";
        sobj.setSql(query);
        sobj.setString("ADDR", ADDR);               //거주주소
        return sobj;
    }
    //##**$$get_komca_data
    //##**$$end
}
