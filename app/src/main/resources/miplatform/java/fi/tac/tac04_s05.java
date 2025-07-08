
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac04_s05
{
    public tac04_s05()
    {
    }
    //##**$$searchMst
    /*
    */
    public DOBJ CTLsearchMst(DOBJ dobj)
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
            dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLsearchMst( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLsearchMst_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   HANMB_NM = dobj.getRetObject("S").getRecord().get("HANMB_NM");   //회원 거래처 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.SUPP_YRMN  ,  E.AVECLASS_CD_NM,  A.PROD_CD  ,  A.MB_CD  ,  A.TRNSF_GBN  ,  SUM(A.OCC_AMT)  OCC_AMT  ,  D.HANMB_NM  AS  OBJPRES_NM  ,  B.CODE_NM  ,  C.PROD_TTL  FROM  FIDU.ttac_pledamt  A,  FIDU.TENV_CODE  B  ,  FIDU.TOPU_PROD  C,  FIDU.TMEM_MB  D,  FIDU.TENV_MDMCD  E  WHERE  1=1   \n";
        query +=" AND  B.HIGH_CD  =  '00294'   \n";
        query +=" AND  A.MB_CD  =  D.MB_CD(+)   \n";
        query +=" AND  A.TRNSF_GBN  =  B.CODE_CD   \n";
        query +=" AND  A.PROD_CD  =  C.PROD_CD   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD||'%'   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN||'%'   \n";
        query +=" AND  A.SUPP_YRMN  =  :SUPP_YRMN   \n";
        query +=" AND  D.HANMB_NM  LIKE  :HANMB_NM  ||'%'   \n";
        query +=" AND  A.MDM_CD  =  E.MDM_CD  GROUP  by  A.SUPP_YRMN  ,  E.AVECLASS_CD_NM,  A.PROD_CD  ,  A.MB_CD  ,  A.TRNSF_GBN  ,  D.HANMB_NM  ,  B.CODE_NM  ,  C.PROD_TTL  ORDER  BY  A.SUPP_YRMN  ,  OBJPRES_NM ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("HANMB_NM", HANMB_NM);               //회원 거래처 코드
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$searchMst
    //##**$$end
}
