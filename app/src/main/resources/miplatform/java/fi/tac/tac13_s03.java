
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac13_s03
{
    public tac13_s03()
    {
    }
    //##**$$SEARCH_PROD
    /*
    */
    public DOBJ CTLSEARCH_PROD(DOBJ dobj)
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
            dobj  = CALLSEARCH_PROD_SEL1(Conn, dobj);           //  작가별 보류해제금액LIST
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
    public DOBJ CTLSEARCH_PROD( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLSEARCH_PROD_SEL1(Conn, dobj);           //  작가별 보류해제금액LIST
        return dobj;
    }
    // 작가별 보류해제금액LIST
    public DOBJ CALLSEARCH_PROD_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLSEARCH_PROD_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLSEARCH_PROD_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROD_CD = dobj.getRetObject("S").getRecord().get("PROD_CD");   //작품 코드
        String   SUPP_MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //지급 회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.SUPP_YRMN AS SUBSTR, A.PROD_CD, D.PROD_TTL, A.MDM_CD, B.MB_CD, B.RIGHTPRES_NM, (SELECT CODE_NM  ";
        query +=" FROM FIDU.TENV_CODE  ";
        query +=" WHERE HIGH_CD = '00027'  ";
        query +=" AND CODE_CD = SUBSTR (A.MDM_CD, 5, 2)) AS MDM_CD_NM, NVL (SUM (DECODE (TRIM (A.WT_GBN), 'A', TRUNC(RELS_AMT,2))), 0) AS WRITE, NVL (SUM (DECODE (TRIM (A.WT_GBN), 'C', TRUNC(RELS_AMT,2))), 0) AS COMPOSE, NVL (SUM (DECODE (TRIM (A.WT_GBN), 'AR', TRUNC(RELS_AMT,2))), 0) AS ARRANGE, NVL (SUM (DECODE (TRIM (A.WT_GBN), 'SA', TRUNC(RELS_AMT,2))), 0) AS SA, A.REMAK  ";
        query +=" FROM FIDU.TTAC_PRODSUPPSUSPRELS A, FIDU.TOPU_PRODRIGHTPRES B, FIDU.TOPU_PRODSUSP C, FIDU.TOPU_PROD D  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND A.PROD_CD = C.PROD_CD(+)  ";
        query +=" AND A.RIGHTPRES_MNG_NUM = C.RIGHTPRES_MNG_NUM(+)  ";
        query +=" AND A.RIGHTPRES_SEQ = C.RIGHTPRES_SEQ (+)  ";
        query +=" AND A.PROD_CD = B.PROD_CD  ";
        query +=" AND A.RIGHTPRES_MNG_NUM = B.RIGHTPRES_MNG_NUM  ";
        query +=" AND A.RIGHTPRES_SEQ = B.RIGHTPRES_SEQ  ";
        query +=" AND A.PROD_CD = D.PROD_CD  ";
        if( !PROD_CD.equals("") )
        {
            query +=" AND A.PROD_CD = :PROD_CD  ";
        }
        if( !SUPP_MB_CD.equals("") )
        {
            query +=" AND A.SUPP_MB_CD = :SUPP_MB_CD  ";
        }
        query +=" GROUP BY A.SUPP_YRMN , A.PROD_CD, D.PROD_TTL, A.MDM_CD, B.MB_CD, B.RIGHTPRES_NM, A.REMAK  ";
        sobj.setSql(query);
        if(!PROD_CD.equals(""))
        {
            sobj.setString("PROD_CD", PROD_CD);               //작품 코드
        }
        if(!SUPP_MB_CD.equals(""))
        {
            sobj.setString("SUPP_MB_CD", SUPP_MB_CD);               //지급 회원 코드
        }
        return sobj;
    }
    //##**$$SEARCH_PROD
    //##**$$end
}
