
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r21
{
    public tac10_r21()
    {
    }
    //##**$$tac10_r21_select
    /*
    */
    public DOBJ CTLtac10_r21_select(DOBJ dobj)
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
            dobj  = CALLtac10_r21_select_SEL1(Conn, dobj);           //  작품보류 정보조회
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
    public DOBJ CTLtac10_r21_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r21_select_SEL1(Conn, dobj);           //  작품보류 정보조회
        return dobj;
    }
    // 작품보류 정보조회
    public DOBJ CALLtac10_r21_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r21_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r21_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        String   PROD_CD = dobj.getRetObject("S").getRecord().get("PROD_CD");   //작품 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT Z.DISTR_YRMN, Z.WRK_YRMN, Z.SUPP_YRMN, Z.WT_GBN, Z.ORGAU_MB_CD, FIDU.GET_MB_NM (Z.ORGAU_MB_CD) AS ORGAU_MB_NM, (SELECT AVECLASS_CD_NM  ";
        query +=" FROM FIDU.TENV_AVECLASSCD  ";
        query +=" WHERE AVECLASS_CD = Z.AVECLASS_CD) AS AVECLASS_CD_NM, Z.AVECLASS_CD, Z.PROD_CD, FIDU.GET_PROD_NM (Z.PROD_CD) AS PROD_NM, SUM (Z.DISTR_AMT) AS DISTR_AMT, Z.PRODSUSP_START_DAY, Z.SUSPRELS_REAS  ";
        query +=" FROM (  ";
        query +=" SELECT A.DISTR_YRMN, A.WRK_YRMN, A.SUPP_YRMN, A.WT_GBN, A.ORGAU_MB_CD, SUBSTR (A.MDM_CD, 1, 2) AS AVECLASS_CD, A.PROD_CD, B.PRODSUSP_START_DAY, B.SUSPRELS_REAS, SUM (DISTR_AMT) AS DISTR_AMT  ";
        query +=" FROM FIDU.TTAC_PRODSUPPSUSP A, (SELECT PROD_CD, RIGHTPRES_MNG_NUM, RIGHTPRES_SEQ, MAX(SEQ), MAX(PRODSUSP_START_DAY) AS PRODSUSP_START_DAY, MAX(SUSPRELS_REAS) AS SUSPRELS_REAS  ";
        query +=" FROM FIDU.TOPU_PRODSUSP  ";
        query +=" GROUP BY PROD_CD, RIGHTPRES_MNG_NUM, RIGHTPRES_SEQ) B  ";
        query +=" WHERE A.WRK_YRMN BETWEEN :FROM_YRMN  ";
        query +=" AND :TO_YRMN  ";
        query +=" AND (A.SUPP_YRMN IS NULL  ";
        query +=" OR SUPP_YRMN > :TO_YRMN)  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND A.ORGAU_MB_CD = :MB_CD  ";
        }
        if( !PROD_CD.equals("") )
        {
            query +=" AND A.PROD_CD = :PROD_CD  ";
        }
        query +=" AND A.PROD_CD = B.PROD_CD(+)  ";
        query +=" AND A.RIGHTPRES_MNG_NUM = B.RIGHTPRES_MNG_NUM(+)  ";
        query +=" AND A.RIGHTPRES_SEQ = B.RIGHTPRES_SEQ(+)  ";
        query +=" GROUP BY A.DISTR_YRMN, A.WT_GBN, A.SUPP_YRMN, A.WRK_YRMN, A.ORGAU_MB_CD, A.MDM_CD, A.PROD_CD, B.PRODSUSP_START_DAY, B.SUSPRELS_REAS ) Z  ";
        query +=" GROUP BY Z.DISTR_YRMN, Z.WRK_YRMN, Z.SUPP_YRMN, Z.WT_GBN, Z.ORGAU_MB_CD, Z.AVECLASS_CD, Z.PROD_CD, Z.PRODSUSP_START_DAY, Z.SUSPRELS_REAS  ";
        query +=" ORDER BY Z.ORGAU_MB_CD  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        if(!PROD_CD.equals(""))
        {
            sobj.setString("PROD_CD", PROD_CD);               //작품 코드
        }
        return sobj;
    }
    //##**$$tac10_r21_select
    //##**$$tac10_r21_select1
    /*
    */
    public DOBJ CTLtac10_r21_select1(DOBJ dobj)
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
            dobj  = CALLtac10_r21_select1_SEL1(Conn, dobj);           //  작품보류 정보조회
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
    public DOBJ CTLtac10_r21_select1( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r21_select1_SEL1(Conn, dobj);           //  작품보류 정보조회
        return dobj;
    }
    // 작품보류 정보조회
    public DOBJ CALLtac10_r21_select1_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r21_select1_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r21_select1_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        String   PROD_CD = dobj.getRetObject("S").getRecord().get("PROD_CD");   //작품 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT Z.DISTR_YRMN, Z.WRK_YRMN, Z.SUPP_YRMN, Z.WT_GBN, Z.ORGAU_MB_CD, FIDU.GET_MB_NM (Z.ORGAU_MB_CD) AS ORGAU_MB_NM, (SELECT AVECLASS_CD_NM  ";
        query +=" FROM FIDU.TENV_AVECLASSCD  ";
        query +=" WHERE AVECLASS_CD = Z.AVECLASS_CD) AS AVECLASS_CD_NM, Z.AVECLASS_CD, Z.PROD_CD, FIDU.GET_PROD_NM (Z.PROD_CD) AS PROD_NM, SUM (Z.DISTR_AMT) AS DISTR_AMT, Z.PRODSUSP_START_DAY, Z.SUSPRELS_REAS  ";
        query +=" FROM (  ";
        query +=" SELECT A.DISTR_YRMN, A.SUPP_YRMN, A.WRK_YRMN, A.WT_GBN, A.ORGAU_MB_CD, SUBSTR (A.MDM_CD, 1, 2) AS AVECLASS_CD, A.PROD_CD, B.PRODSUSP_START_DAY, B.SUSPRELS_REAS, SUM (DISTR_AMT) AS DISTR_AMT  ";
        query +=" FROM FIDU.TTAC_PRODSUPPSUSP A, (SELECT PROD_CD, RIGHTPRES_MNG_NUM, RIGHTPRES_SEQ, MAX(SEQ), MAX(PRODSUSP_START_DAY) AS PRODSUSP_START_DAY, MAX(SUSPRELS_REAS) AS SUSPRELS_REAS  ";
        query +=" FROM FIDU.TOPU_PRODSUSP  ";
        query +=" GROUP BY PROD_CD, RIGHTPRES_MNG_NUM, RIGHTPRES_SEQ) B  ";
        query +=" WHERE A.WRK_YRMN BETWEEN :FROM_YRMN  ";
        query +=" AND :TO_YRMN  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND A.ORGAU_MB_CD = :MB_CD  ";
        }
        if( !PROD_CD.equals("") )
        {
            query +=" AND A.PROD_CD = :PROD_CD  ";
        }
        query +=" AND A.PROD_CD = B.PROD_CD(+)  ";
        query +=" AND A.RIGHTPRES_MNG_NUM = B.RIGHTPRES_MNG_NUM(+)  ";
        query +=" AND A.RIGHTPRES_SEQ = B.RIGHTPRES_SEQ(+)  ";
        query +=" GROUP BY A.DISTR_YRMN, A.WRK_YRMN, A.SUPP_YRMN, A.WT_GBN, A.ORGAU_MB_CD, A.MDM_CD, A.PROD_CD, B.PRODSUSP_START_DAY, B.SUSPRELS_REAS ) Z  ";
        query +=" GROUP BY Z.DISTR_YRMN, Z.SUPP_YRMN, Z.WRK_YRMN, Z.WT_GBN, Z.ORGAU_MB_CD, Z.AVECLASS_CD, Z.PROD_CD, Z.PRODSUSP_START_DAY, Z.SUSPRELS_REAS  ";
        query +=" ORDER BY Z.ORGAU_MB_CD  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        if(!PROD_CD.equals(""))
        {
            sobj.setString("PROD_CD", PROD_CD);               //작품 코드
        }
        return sobj;
    }
    //##**$$tac10_r21_select1
    //##**$$end
}
