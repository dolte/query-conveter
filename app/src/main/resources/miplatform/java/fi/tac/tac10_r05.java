
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r05
{
    public tac10_r05()
    {
    }
    //##**$$searchMst
    /* * 프로그램명 : tac10_r05
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
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
            dobj  = CALLsearchMst_SEL2(Conn, dobj);           //  조회
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
        dobj  = CALLsearchMst_SEL2(Conn, dobj);           //  조회
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
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT MB_CD, HANMB_NM, TRNSF_GBN,REMAK, DIS_SUSP_AMT, SUSP_AMT, SUPP_AMT, REST_AMT FROM(  ";
        query +=" SELECT MB_CD, HANMB_NM,TRNSF_GBN, REMAK, DIS_SUSP_AMT, SUSP_AMT, SUPP_AMT, REST_AMT, (NVL(DIS_SUSP_AMT,0) + NVL(SUSP_AMT,0) + NVL(SUPP_AMT,0) + NVL(REST_AMT,0)) AS SUM_AMT FROM(  ";
        query +=" SELECT A.MB_CD, B.HANMB_NM, A.TRNSF_GBN, (SELECT SUPPSUSP_ORGMAN_CTENT  ";
        query +=" FROM FIDU.TMEM_SUPPSUSP  ";
        query +=" WHERE MB_CD = A.MB_CD  ";
        query +=" AND TRNSF_GBN = A.TRNSF_GBN  ";
        query +=" AND MNG_NUM = (SELECT MAX(MNG_NUM)  ";
        query +=" FROM FIDU.TMEM_SUPPSUSP  ";
        query +=" WHERE MB_CD = A.MB_CD) ) AS REMAK, (JSUSP_AMT - JSUPP_AMT) AS DIS_SUSP_AMT, DSUSP_AMT AS SUSP_AMT, DSUPP_AMT AS SUPP_AMT, ((JSUSP_AMT - JSUPP_AMT) + DSUSP_AMT - DSUPP_AMT) AS REST_AMT FROM(  ";
        query +=" SELECT MB_CD, TRNSF_GBN, SUM(JSUSP_AMT) AS JSUSP_AMT, SUM(JSUPP_AMT) AS JSUPP_AMT, SUM(DSUSP_AMT) AS DSUSP_AMT, SUM(DSUPP_AMT) AS DSUPP_AMT  ";
        query +=" FROM (SELECT MB_CD, TRNSF_GBN, SUM(SUSP_AMT) AS JSUSP_AMT, 0 AS JSUPP_AMT, 0 AS DSUSP_AMT, 0 AS DSUPP_AMT  ";
        query +=" FROM FIDU.TTAC_MBSUPPSUSPAMT  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND SUPP_YRMN < :SUPP_YRMN  ";
        query +=" GROUP BY MB_CD, TRNSF_GBN  ";
        query +=" UNION ALL  ";
        query +=" SELECT MB_CD, TRNSF_GBN, 0 AS JSUSP_AMT, SUM(SUPP_AMT) AS JSUPP_AMT, 0 AS DSUSP_AMT, 0 AS DSUPP_AMT  ";
        query +=" FROM FIDU.TTAC_MBSUPPSUSPAMT  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND NVL(RELS_YRMN,'999999') < :SUPP_YRMN  ";
        query +=" GROUP BY MB_CD, TRNSF_GBN  ";
        query +=" UNION ALL  ";
        query +=" SELECT MB_CD, TRNSF_GBN, 0 AS JSUSP_AMT, 0 AS JSUPP_AMT, SUM(SUSP_AMT) AS DSUSP_AMT, 0 AS DSUPP_AMT  ";
        query +=" FROM FIDU.TTAC_MBSUPPSUSPAMT  ";
        query +=" WHERE SUPP_YRMN = :SUPP_YRMN  ";
        query +=" GROUP BY MB_CD, TRNSF_GBN  ";
        query +=" UNION ALL  ";
        query +=" SELECT MB_CD, TRNSF_GBN, 0 AS JSUSP_AMT, 0 AS JSUPP_AMT, 0 AS DSUSP_AMT, SUM(SUPP_AMT) AS DSUPP_AMT  ";
        query +=" FROM FIDU.TTAC_MBSUPPSUSPAMT  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND NVL(RELS_YRMN,'999999') = :SUPP_YRMN  ";
        query +=" GROUP BY MB_CD, TRNSF_GBN )  ";
        query +=" WHERE 1 = 1  ";
        query +=" GROUP BY MB_CD, TRNSF_GBN ) A, FIDU.TMEM_MB B  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND A.MB_CD = B.MB_CD  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND A.MB_CD = :MB_CD  ";
        }
        query +=" ) )  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND SUM_AMT <> 0  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 조회
    public DOBJ CALLsearchMst_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT MB_CD, HANMB_NM, TRNSF_GBN, CLASS_CD, FIDU.GET_MDM_NM_EX (CLASS_CD, LENGTH(CLASS_CD)) AS CLASS_NM, REMAK, DIS_SUSP_AMT, SUSP_AMT, SUPP_AMT, REST_AMT  ";
        query +=" FROM (SELECT MB_CD, HANMB_NM, TRNSF_GBN, CLASS_CD, REMAK, DIS_SUSP_AMT, SUSP_AMT, SUPP_AMT, REST_AMT, ( NVL (DIS_SUSP_AMT, 0) + NVL (SUSP_AMT, 0) + NVL (SUPP_AMT, 0) + NVL (REST_AMT, 0)) AS SUM_AMT  ";
        query +=" FROM (  ";
        query +=" SELECT A.MB_CD, B.HANMB_NM, A.TRNSF_GBN, A.CLASS_CD, (SELECT SUPPSUSP_ORGMAN_CTENT  ";
        query +=" FROM FIDU.TTAC_MDMSUPPSUSP  ";
        query +=" WHERE MB_CD = A.MB_CD  ";
        query +=" AND TRNSF_GBN = A.TRNSF_GBN  ";
        query +=" AND CLASS_CD = A.CLASS_CD  ";
        query +=" AND MNG_NUM = (SELECT MAX (MNG_NUM)  ";
        query +=" FROM FIDU.TTAC_MDMSUPPSUSP  ";
        query +=" WHERE MB_CD = A.MB_CD  ";
        query +=" AND TRNSF_GBN = A.TRNSF_GBN  ";
        query +=" AND CLASS_CD = A.CLASS_CD )) AS REMAK, (JSUSP_AMT - JSUPP_AMT) AS DIS_SUSP_AMT, DSUSP_AMT AS SUSP_AMT, DSUPP_AMT AS SUPP_AMT, ( (JSUSP_AMT - JSUPP_AMT) + DSUSP_AMT - DSUPP_AMT) AS REST_AMT  ";
        query +=" FROM (  ";
        query +=" SELECT MB_CD, TRNSF_GBN, SUBSTR(MDM_CD,1,1) AS CLASS_CD, SUM (JSUSP_AMT) AS JSUSP_AMT, SUM (JSUPP_AMT) AS JSUPP_AMT, SUM (DSUSP_AMT) AS DSUSP_AMT, SUM (DSUPP_AMT) AS DSUPP_AMT  ";
        query +=" FROM (  ";
        query +=" SELECT MB_CD, TRNSF_GBN, MDM_CD, SUM (SUSP_AMT) AS JSUSP_AMT, 0 AS JSUPP_AMT, 0 AS DSUSP_AMT, 0 AS DSUPP_AMT  ";
        query +=" FROM FIDU.TTAC_MDMSUPPSUSPAMT  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND SUPP_YRMN < :SUPP_YRMN  ";
        query +=" GROUP BY MB_CD, TRNSF_GBN, MDM_CD  ";
        query +=" UNION ALL  ";
        query +=" SELECT MB_CD, TRNSF_GBN, MDM_CD, 0 AS JSUSP_AMT, SUM (SUPP_AMT) AS JSUPP_AMT, 0 AS DSUSP_AMT, 0 AS DSUPP_AMT  ";
        query +=" FROM FIDU.TTAC_MDMSUPPSUSPAMT  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND NVL (RELS_YRMN, '999999') < :SUPP_YRMN  ";
        query +=" GROUP BY MB_CD, TRNSF_GBN, MDM_CD  ";
        query +=" UNION ALL  ";
        query +=" SELECT MB_CD, TRNSF_GBN, MDM_CD, 0 AS JSUSP_AMT, 0 AS JSUPP_AMT, SUM (SUSP_AMT) AS DSUSP_AMT, 0 AS DSUPP_AMT  ";
        query +=" FROM FIDU.TTAC_MDMSUPPSUSPAMT  ";
        query +=" WHERE SUPP_YRMN = :SUPP_YRMN  ";
        query +=" GROUP BY MB_CD, TRNSF_GBN, MDM_CD  ";
        query +=" UNION ALL  ";
        query +=" SELECT MB_CD, TRNSF_GBN, MDM_CD, 0 AS JSUSP_AMT, 0 AS JSUPP_AMT, 0 AS DSUSP_AMT, SUM (SUPP_AMT) AS DSUPP_AMT  ";
        query +=" FROM FIDU.TTAC_MDMSUPPSUSPAMT  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND NVL (RELS_YRMN, '999999') = :SUPP_YRMN  ";
        query +=" GROUP BY MB_CD, TRNSF_GBN, MDM_CD)  ";
        query +=" WHERE 1 = 1  ";
        query +=" GROUP BY MB_CD, TRNSF_GBN, SUBSTR(MDM_CD,1,1)) A, FIDU.TMEM_MB B  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND A.MB_CD = B.MB_CD  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND A.MB_CD = :MB_CD  ";
        }
        query +=" ))  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND SUM_AMT <> 0  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$searchMst
    //##**$$search_mb_hist
    /*
    */
    public DOBJ CTLsearch_mb_hist(DOBJ dobj)
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
            dobj  = CALLsearch_mb_hist_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLsearch_mb_hist( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_mb_hist_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLsearch_mb_hist_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_mb_hist_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_mb_hist_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUPP_YRMN  ,  SUSP_AMT  ,  SUPP_AMT  ,MNGCOMIS_AMT  FROM  (   \n";
        query +=" SELECT  SUPP_YRMN  ,  SUM(SUSP_AMT)  SUSP_AMT  ,  SUM(SUPP_AMT)  SUPP_AMT  ,  SUM(MNGCOMIS_AMT)  MNGCOMIS_AMT  FROM  (   \n";
        query +=" SELECT  SUPP_YRMN  ,  SUM(NVL(SUSP_AMT,0))  AS  SUSP_AMT  ,  0  AS  SUPP_AMT  ,  SUM(NVL(MNGCOMIS_AMT_SUPP,0))  AS  MNGCOMIS_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  MB_CD  =  :MB_CD   \n";
        query +=" AND  TRNSF_GBN  =  :TRNSF_GBN  GROUP  BY  SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  NVL(RELS_YRMN,'999999')  AS  SUPP_YRMN  ,  0  AS  SUSP_AMT  ,  SUM(NVL(SUPP_AMT,0))  AS  SUPP_AMT  ,  0  AS  MNGCOMIS_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  MB_CD  =  :MB_CD   \n";
        query +=" AND  TRNSF_GBN  =  :TRNSF_GBN  GROUP  BY  RELS_YRMN  )  GROUP  BY  SUPP_YRMN  ORDER  BY  SUPP_YRMN  DESC  )  WHERE  NOT(SUSP_AMT  =  0   \n";
        query +=" AND  SUPP_AMT  =  0) ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        return sobj;
    }
    //##**$$search_mb_hist
    //##**$$search_mdm_hist
    /*
    */
    public DOBJ CTLsearch_mdm_hist(DOBJ dobj)
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
            dobj  = CALLsearch_mdm_hist_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLsearch_mdm_hist( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_mdm_hist_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLsearch_mdm_hist_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_mdm_hist_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_mdm_hist_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   CLASS_CD = dobj.getRetObject("S").getRecord().get("CLASS_CD");   //분류 코드
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUPP_YRMN  ,  SUSP_AMT  ,  SUPP_AMT  ,MNGCOMIS_AMT  FROM  (   \n";
        query +=" SELECT  SUPP_YRMN  ,  SUM(SUSP_AMT)  SUSP_AMT  ,  SUM(SUPP_AMT)  SUPP_AMT  ,  SUM(MNGCOMIS_AMT)  MNGCOMIS_AMT  FROM  (   \n";
        query +=" SELECT  SUPP_YRMN  ,  SUM(NVL(SUSP_AMT,0))  AS  SUSP_AMT  ,  0  AS  SUPP_AMT  ,  SUM(NVL(MNGCOMIS_AMT_SUPP,0))  AS  MNGCOMIS_AMT  FROM  FIDU.TTAC_MDMSUPPSUSPAMT  WHERE  MB_CD  =  :MB_CD   \n";
        query +=" AND  TRNSF_GBN  =  :TRNSF_GBN   \n";
        query +=" AND  MDM_CD  LIKE  :CLASS_CD  ||  '%'  GROUP  BY  SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  NVL(RELS_YRMN,'999999')  AS  SUPP_YRMN  ,  0  AS  SUSP_AMT  ,  SUM(NVL(SUPP_AMT,0))  AS  SUPP_AMT  ,  0  AS  MNGCOMIS_AMT  FROM  FIDU.TTAC_MDMSUPPSUSPAMT  WHERE  MB_CD  =  :MB_CD   \n";
        query +=" AND  TRNSF_GBN  =  :TRNSF_GBN   \n";
        query +=" AND  MDM_CD  LIKE  :CLASS_CD  ||  '%'  GROUP  BY  RELS_YRMN  )  GROUP  BY  SUPP_YRMN  ORDER  BY  SUPP_YRMN  DESC  )  WHERE  NOT(SUSP_AMT  =  0   \n";
        query +=" AND  SUPP_AMT  =  0) ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("CLASS_CD", CLASS_CD);               //분류 코드
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        return sobj;
    }
    //##**$$search_mdm_hist
    //##**$$end
}
