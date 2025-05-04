
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s21_1
{
    public bra01_s21_1()
    {
    }
    //##**$$list_same_position_upso
    /*
    */
    public DOBJ CTLlist_same_position_upso(DOBJ dobj)
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
            dobj  = CALLlist_same_position_upso_SEL1(Conn, dobj);           //  자동생성 실패 업소목록
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
    public DOBJ CTLlist_same_position_upso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlist_same_position_upso_SEL1(Conn, dobj);           //  자동생성 실패 업소목록
        return dobj;
    }
    // 자동생성 실패 업소목록
    public DOBJ CALLlist_same_position_upso_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLlist_same_position_upso_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlist_same_position_upso_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.UPSO_NM  ,  A.UPSO_NEW_ADDR1  ,  A.UPSO_NEW_ADDR2  ,  B.LAT  ,  B.LNG  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_UPSO_MAP  B  ,  (   \n";
        query +=" SELECT  B.LAT,  B.LNG  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_UPSO_MAP  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL  GROUP  BY  B.LAT,  B.LNG  HAVING  COUNT(*)  >  1  )  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  B.LAT  =  C.LAT   \n";
        query +=" AND  B.LNG  =  C.LNG   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL  ORDER  BY  LAT,  LNG,  UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$list_same_position_upso
    //##**$$end
}
