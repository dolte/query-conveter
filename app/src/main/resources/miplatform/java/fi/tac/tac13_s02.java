
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac13_s02
{
    public tac13_s02()
    {
    }
    //##**$$search_right
    /*
    */
    public DOBJ CTLsearch_right(DOBJ dobj)
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
            dobj  = CALLsearch_right_SEL1(Conn, dobj);           //  작품보류 해제내역
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
    public DOBJ CTLsearch_right( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_right_SEL1(Conn, dobj);           //  작품보류 해제내역
        return dobj;
    }
    // 작품보류 해제내역
    public DOBJ CALLsearch_right_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_right_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_right_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   PROD_CD = dobj.getRetObject("S").getRecord().get("PROD_CD");   //작품 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT FIDU.GET_MDM_NM(A.MDM_CD) AS MDM_CD_NM, A.PROD_CD, F.PROD_TTL, A.SUPP_MB_CD MB_CD, E.HANMB_NM RIGHTPRES_NM , C.ORGAU_MB_CD,C.ORGAU_NM, D.PRODSUSP_START_DAY, SUM(A.RELS_AMT) RELS_AMT  ";
        query +=" FROM FIDU.TTAC_PRODSUPPSUSPRELS A, (SELECT DISTINCT PROD_CD, MDM_CD, RIGHTPRES_MNG_NUM, RIGHTPRES_SEQ  ";
        query +=" FROM FIDU.TTAC_PRODSUPPSUSP  ";
        query +=" WHERE SUPP_YRMN =:SUPP_YRMN) B, FIDU.TOPU_PRODRIGHTPRES C , (SELECT PROD_CD,RIGHTPRES_MNG_NUM,RIGHTPRES_SEQ, MIN(PRODSUSP_START_DAY) PRODSUSP_START_DAY  ";
        query +=" FROM FIDU.TOPU_PRODSUSP  ";
        query +=" GROUP BY PROD_CD,RIGHTPRES_MNG_NUM,RIGHTPRES_SEQ) D, FIDU.TMEM_MB E, FIDU.TOPU_PROD F  ";
        query +=" WHERE A.SUPP_YRMN =:SUPP_YRMN  ";
        query +=" AND A.PROD_CD = B.PROD_CD  ";
        query +=" AND A.MDM_CD = B.MDM_CD  ";
        query +=" AND A.RIGHTPRES_MNG_NUM = B.RIGHTPRES_MNG_NUM  ";
        query +=" AND A.RIGHTPRES_SEQ = B.RIGHTPRES_SEQ  ";
        query +=" AND A.PROD_CD = C.PROD_CD(+)  ";
        query +=" AND A.PROD_CD = F.PROD_CD  ";
        query +=" AND A.SUPP_MB_CD = E.MB_CD  ";
        query +=" AND A.RIGHTPRES_MNG_NUM = C.RIGHTPRES_MNG_NUM(+)  ";
        query +=" AND A.RIGHTPRES_SEQ = C.RIGHTPRES_SEQ(+)  ";
        query +=" AND A.PROD_CD = D.PROD_CD(+)  ";
        query +=" AND A.RIGHTPRES_MNG_NUM = D.RIGHTPRES_MNG_NUM(+)  ";
        query +=" AND A.RIGHTPRES_SEQ = D.RIGHTPRES_SEQ(+)  ";
        if( !PROD_CD.equals("") )
        {
            query +=" AND A.PROD_CD = :PROD_CD  ";
        }
        if( !MB_CD.equals("") )
        {
            query +=" AND A.SUPP_MB_CD = :MB_CD  ";
        }
        query +=" GROUP BY A.MDM_CD, A.PROD_CD, F.PROD_TTL, A.SUPP_MB_CD,E.HANMB_NM, C.ORGAU_MB_CD,C.ORGAU_NM, D.PRODSUSP_START_DAY  ";
        query +=" ORDER BY A.PROD_CD, A.SUPP_MB_CD  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        if(!PROD_CD.equals(""))
        {
            sobj.setString("PROD_CD", PROD_CD);               //작품 코드
        }
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$search_right
    //##**$$end
}
