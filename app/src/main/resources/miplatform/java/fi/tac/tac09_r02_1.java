
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac09_r02_1
{
    public tac09_r02_1()
    {
    }
    //##**$$tac09_r02_selelct_excel
    /*
    */
    public DOBJ CTLtac09_r02_selelct_excel(DOBJ dobj)
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
            dobj  = CALLtac09_r02_selelct_excel_SEL1(Conn, dobj);           //  은행이체파일조회
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
    public DOBJ CTLtac09_r02_selelct_excel( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac09_r02_selelct_excel_SEL1(Conn, dobj);           //  은행이체파일조회
        return dobj;
    }
    // 은행이체파일조회
    public DOBJ CALLtac09_r02_selelct_excel_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac09_r02_selelct_excel_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac09_r02_selelct_excel_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BANK_CD  AS  AA,  NM  AS  BB,  NVL(ACCN_NUM,  '  ')  AS  CC,  SUPP_AMT  AS  DD,  '한음저협저작권료'  AS  EE  FROM  FIDU.TTAC_BANKFILE  WHERE  SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  SUPP_YN  =  'Y'   \n";
        query +=" AND  LENGTH(TRIM(ACCN_NUM))  >  0  ORDER  BY  BANK_CD,  NM ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$tac09_r02_selelct_excel
    //##**$$end
}
