
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac13_s04
{
    public tac13_s04()
    {
    }
    //##**$$search_clear
    /*
    */
    public DOBJ CTLsearch_clear(DOBJ dobj)
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
            dobj  = CALLsearch_clear_SEL1(Conn, dobj);           //  작품별 보류현황
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
    public DOBJ CTLsearch_clear( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_clear_SEL1(Conn, dobj);           //  작품별 보류현황
        return dobj;
    }
    // 작품별 보류현황
    public DOBJ CALLsearch_clear_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_clear_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_clear_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROD_CD = dobj.getRetObject("S").getRecord().get("PROD_CD");   //작품 코드
        String   ORGAU_MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //원작자회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT DISTR_YRMN, WRK_YRMN, PROD_CD, PROD_TTL, ORGAU_MB_CD, ORGAU_NM, MDM_CD_NM, TRUNC (SUM (WRITE), 2) AS WRITE, TRUNC (SUM (COMPOSE), 2) AS COMPOSE, TRUNC (SUM (ARRANGE), 2) AS ARRANGE, TRUNC (SUM (SA), 2) AS SA, TRUNC (SUM (T_SUM), 2) AS T_SUM  ";
        query +=" FROM (SELECT A.DISTR_YRMN AS DISTR_YRMN, A.WRK_YRMN, A.PROD_CD, B.PROD_TTL, C.ORGAU_MB_CD, C.ORGAU_NM, (SELECT AVECLASS_CD_NM  ";
        query +=" FROM FIDU.TENV_MDMCD  ";
        query +=" WHERE MDM_CD = A.MDM_CD) AS MDM_CD_NM, NVL (DECODE (TRIM (A.WT_GBN), 'A', DISTR_AMT), 0) AS WRITE, NVL (DECODE (TRIM (A.WT_GBN), 'C', DISTR_AMT), 0) AS COMPOSE, NVL (DECODE (TRIM (A.WT_GBN), 'AR', DISTR_AMT), 0) AS ARRANGE, NVL (DECODE (TRIM (A.WT_GBN), 'SA', DISTR_AMT), 0) AS SA, A.DISTR_AMT AS T_SUM  ";
        query +=" FROM FIDU.TTAC_PRODSUPPSUSP A, FIDU.TOPU_PROD B, FIDU.TOPU_PRODRIGHTPRES C  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND A.SUPP_GBN IS NULL  ";
        query +=" AND A.PROD_CD = B.PROD_CD  ";
        query +=" AND (SELECT MB_CD  ";
        query +=" FROM FIDU.TOPU_PRODRIGHTPRES  ";
        query +=" WHERE PROD_CD = A.PROD_CD  ";
        query +=" AND RIGHTPRES_MNG_NUM = A.RIGHTPRES_MNG_NUM  ";
        query +=" AND RIGHTPRES_SEQ = A.RIGHTPRES_SEQ) = C.MB_CD  ";
        query +=" AND A.PROD_CD = C.PROD_CD  ";
        query +=" AND A.RIGHTPRES_MNG_NUM = C.RIGHTPRES_MNG_NUM  ";
        query +=" AND A.RIGHTPRES_SEQ = C.RIGHTPRES_SEQ  ";
        if( !PROD_CD.equals("") )
        {
            query +=" AND A.PROD_CD = :PROD_CD  ";
        }
        if( !ORGAU_MB_CD.equals("") )
        {
            query +=" AND A.ORGAU_MB_CD = :ORGAU_MB_CD  ";
        }
        query +=" )  ";
        query +=" GROUP BY DISTR_YRMN, WRK_YRMN, PROD_CD, PROD_TTL, ORGAU_MB_CD, ORGAU_NM, MDM_CD_NM  ";
        query +=" ORDER BY DISTR_YRMN  ";
        sobj.setSql(query);
        if(!PROD_CD.equals(""))
        {
            sobj.setString("PROD_CD", PROD_CD);               //작품 코드
        }
        if(!ORGAU_MB_CD.equals(""))
        {
            sobj.setString("ORGAU_MB_CD", ORGAU_MB_CD);               //원작자회원코드
        }
        return sobj;
    }
    //##**$$search_clear
    //##**$$end
}
