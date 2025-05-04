
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class zip_list
{
    public zip_list()
    {
    }
    //##**$$zip_list
    /* * 프로그램명 : zip_list
    * 작성자 : 서정재
    * 작성일 : 2009/08/11
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbra10_s04(DOBJ dobj)
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
            dobj  = CALLzip_list_SEL1(Conn, dobj);           //  우편번호조회
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
    public DOBJ CTLbra10_s04( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLzip_list_SEL1(Conn, dobj);           //  우편번호조회
        return dobj;
    }
    // 우편번호조회
    public DOBJ CALLzip_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzip_list_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USE_YN = dobj.getRetObject("S").getRecord().get("USE_YN");   //사용구분
        String   TO_ZIP = dobj.getRetObject("S").getRecord().get("TO_ZIP");   //우편번호 검색 TO
        String   FROM_ZIP = dobj.getRetObject("S").getRecord().get("FROM_ZIP");   //우편번호 검색 FROM
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  POST_SNUM  ,  MNG_ZIP  ,  ZIP  ,  ATTE  ,  DSRCCNTY  ,  DONG  ,  HNM  ,  USE_YN  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  ZIP  BETWEEN  :FROM_ZIP   \n";
        query +=" AND  :TO_ZIP   \n";
        query +=" AND  USE_YN  LIKE  :USE_YN||'%'  ORDER  BY  MNG_ZIP ";
        sobj.setSql(query);
        sobj.setString("USE_YN", USE_YN);               //사용구분
        sobj.setString("TO_ZIP", TO_ZIP);               //우편번호 검색 TO
        sobj.setString("FROM_ZIP", FROM_ZIP);               //우편번호 검색 FROM
        return sobj;
    }
    //##**$$zip_list
    //##**$$end
}
