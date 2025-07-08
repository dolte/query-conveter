
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r11
{
    public tac10_r11()
    {
    }
    //##**$$ded_mem_search
    /*
    */
    public DOBJ CTLded_mem_search(DOBJ dobj)
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
            dobj  = CALLded_mem_search_SEL3(Conn, dobj);           //  작고회원_NEW
            dobj  = CALLded_mem_search_SEL2(Conn, dobj);           //  매체별 관리수수료현황(신규)
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
    public DOBJ CTLded_mem_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLded_mem_search_SEL3(Conn, dobj);           //  작고회원_NEW
        dobj  = CALLded_mem_search_SEL2(Conn, dobj);           //  매체별 관리수수료현황(신규)
        return dobj;
    }
    // 작고회원_NEW
    public DOBJ CALLded_mem_search_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLded_mem_search_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLded_mem_search_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  B.DEAD_DAY,  A.TOT_ORGDISTR_AMT  ,  C.MB_CD  AS  SUCC_MB_CD  ,  C.HANMB_NM  AS  SUCC_HANMB_NM  ,  FIDU.GET_DAEPYO_GIBUN(B.MB_CD,  C.MB_CD)  AS  GIBUN  ,  C.REP_SUCCNPRES_YN  FROM  FIDU.TTAC_COPYRTAL  A  ,  FIDU.TMEM_MB  B  LEFT  JOIN  FIDU.TMEM_MB  C  ON  C.SUCCNMB_CD  =  B.MB_CD  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  TOT_ORGDISTR_AMT  <>  0   \n";
        query +=" AND  SUBSTR(B.DEAD_DAY,1,6)  <=  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  B.DEAD_DAY  IS  NOT  NULL  ORDER  BY  2 ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 매체별 관리수수료현황(신규)
    public DOBJ CALLded_mem_search_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLded_mem_search_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLded_mem_search_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   WRK_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //작업 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  C.AVECLASS_CD  MDM_CD,  C.AVECLASS_CD_NM  AVECLASS_CD_NM,  SUM(A.DISTR_PLAN_AMT)  +  NVL(SUM(I.DISTR_AMT),0)  DISTR_PLAN_AMT,  SUM(A.SUPP_SUSP_AMT)  SUPP_SUSP_AMT,  SUM(NVL(A.ETC_AMT,0))  +  SUM(D.RIGHTPRES_DISTR_MAGP)  ETC_AMT,  SUM(A.DISTR_AMT)  +  SUM(NVL(A.ETC_AMT,0))  +  NVL(SUM(I.DISTR_AMT),0)  DISTR_AMT,  SUM(E.BORYU_AMT)  SUSP_AMT,  SUM(F.RES_AMT)SUSP_HAMT,  SUM(A.ORGDISTR_AMT)  DISTR_JAMT,  SUM(G.ATAX_AMT)  ATAX_AMT_DIFF  ,SUM(A.ATAX_AMT)  -  SUM(NVL(G.ATAX_AMT,0))  ATAX_AMT,  MNGCOMIS_RATE,  SUM(A.MNGCOMIS_AMT)  MNGCOMIS_AMT  FROM   \n";
        query +=" (SELECT  MDM_CD,  SUM(ORGDISTR_AMT)  ORGDISTR_AMT,  SUM(MNGCOMIS_AMT)  MNGCOMIS_AMT,  SUM(ATAX_AMT)  ATAX_AMT,  SUM(DISTR_PLAN_AMT)  DISTR_PLAN_AMT,  SUM(SUPP_SUSP_AMT)  SUPP_SUSP_AMT,  SUM(ETC_AMT)  ETC_AMT,  SUM(DISTR_AMT)  DISTR_AMT  FROM   \n";
        query +=" (SELECT  MDM_CD,  ORGDISTR_AMT,  MNGCOMIS_AMT,  ATAX_AMT,  0  DISTR_PLAN_AMT,  0  SUPP_SUSP_AMT,0  ETC_AMT,0  DISTR_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  WHERE  SUPP_YRMN  =:WRK_YRMN  UNION  ALL   \n";
        query +=" SELECT  MDM_CD,  0  ORGDISTR_AMT,0  MNGCOMIS_AMT,0  ATAX_AMT,  DISTR_PLAN_AMT,  SUPP_SUSP_AMT,  ETC_AMT,  DISTR_AMT  FROM  FIDU.TDIS_DISTRPLANAMT  WHERE  WRK_YRMN  =:WRK_YRMN)  GROUP  BY  MDM_CD)  A,  FIDU.TENV_MDMCD  C,   \n";
        query +=" (SELECT  MDM_CD,  MAX(MNGCOMIS_RATE)  MNGCOMIS_RATE  FROM  FIDU.TENV_MNGCOMIS  WHERE  APPL_YRMN  =  :WRK_YRMN  GROUP  BY  MDM_CD  )  H,   \n";
        query +=" (SELECT  MDM_CD,  SUM(RIGHTPRES_DISTR_MAGP)  RIGHTPRES_DISTR_MAGP  FROM  FIDU.TTAC_MDMCLASSDISTRAMT  WHERE  SUPP_YRMN  =:WRK_YRMN  GROUP  BY  MDM_CD)  D,  (   \n";
        query +=" SELECT  MDM_CD,  SUM(BORYU_AMT)  BORYU_AMT  FROM   \n";
        query +=" (SELECT  MDM_CD,  SUM(NVL(SUSP_AMT,0))  BORYU_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  SUPP_YRMN  =:WRK_YRMN  GROUP  BY  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  MDM_CD,  SUM(NVL(DISTR_AMT,0))  BORYU_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  WRK_YRMN  =:WRK_YRMN  GROUP  BY  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  MDM_CD,  SUM(NVL(OCC_AMT,0))  AS  BORYU_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  :WRK_YRMN  GROUP  BY  MDM_CD)  GROUP  BY  MDM_CD)  E,  (   \n";
        query +=" SELECT  MDM_CD,  SUM(RES_AMT)RES_AMT  FROM   \n";
        query +=" (SELECT  MDM_CD,  SUM(NVL(SUPP_AMT,0))  RES_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  RELS_YRMN  =:WRK_YRMN  GROUP  BY  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  MDM_CD,  SUM(NVL(DISTR_AMT,0))  RES_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  SUPP_YRMN  =:WRK_YRMN  GROUP  BY  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  MDM_CD,  SUM(NVL(OCC_AMT,0))  AS  RES_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  :WRK_YRMN  GROUP  BY  MDM_CD)  GROUP  BY  MDM_CD)  F,   \n";
        query +=" (SELECT  MDM_CD,  SUM(ATAX_AMT)  ATAX_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TMEM_MB  B  WHERE  SUPP_YRMN  =:WRK_YRMN   \n";
        query +=" AND  NVL(FIDU.GET_FOR_ATAX_YN(MDM_CD),'0')  <>  '1'   \n";
        query +=" AND  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  B.MB_GBN1  =  'F'  GROUP  BY  MDM_CD  )  G  ,   \n";
        query +=" (SELECT  MDM_CD,  SUM(DISTR_AMT)  DISTR_AMT  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =:WRK_YRMN  GROUP  BY  MDM_CD)  I  WHERE  C.MDM_CD  =  A.MDM_CD(+)   \n";
        query +=" AND  C.MDM_CD  =  D.MDM_CD(+)   \n";
        query +=" AND  C.MDM_CD  =  E.MDM_CD(+)   \n";
        query +=" AND  C.MDM_CD  =  F.MDM_CD(+)   \n";
        query +=" AND  C.MDM_CD  =  G.MDM_CD(+)   \n";
        query +=" AND  C.MDM_CD  =  H.MDM_CD(+)   \n";
        query +=" AND  C.MDM_CD  =  I.MDM_CD(+)  GROUP  BY  C.AVECLASS_CD,  C.AVECLASS_CD_NM  ,  MNGCOMIS_RATE  HAVING  SUM(A.ORGDISTR_AMT)  >  0  ORDER  BY  C.AVECLASS_CD ";
        sobj.setSql(query);
        sobj.setString("WRK_YRMN", WRK_YRMN);               //작업 년월
        return sobj;
    }
    //##**$$ded_mem_search
    //##**$$mdm_search
    /*
    */
    public DOBJ CTLmdm_search(DOBJ dobj)
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
            dobj  = CALLmdm_search_SEL1(Conn, dobj);           //  매체별 관리수수료현황(신규)
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
    public DOBJ CTLmdm_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmdm_search_SEL1(Conn, dobj);           //  매체별 관리수수료현황(신규)
        return dobj;
    }
    // 매체별 관리수수료현황(신규)
    public DOBJ CALLmdm_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmdm_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmdm_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   WRK_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //작업 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  C.AVECLASS_CD  MDM_CD,  C.AVECLASS_CD_NM  AVECLASS_CD_NM,  H.MNGCOMIS_RATE,  SUM(A.DISTR_AMT)  +  SUM(NVL(A.ETC_AMT,0))  +  NVL(SUM(I.DISTR_AMT),0)  DISTR_AMT,  SUM(G.ATAX_AMT)  ATAX_AMT_DIFF,  SUM(A.ATAX_AMT)  -  SUM(NVL(G.ATAX_AMT,0))  ATAX_AMT,  SUM(A.DISTR_PLAN_AMT)  +  NVL(SUM(I.DISTR_AMT),0)  DISTR_PLAN_AMT,  SUM(A.SUPP_SUSP_AMT)  SUPP_SUSP_AMT,  SUM(NVL(A.ETC_AMT,0))  +  SUM(D.RIGHTPRES_DISTR_MAGP)  ETC_AMT,  SUM(E.BORYU_AMT)  SUSP_AMT,  SUM(F.RES_AMT)  SUSP_HAMT,  SUM(A.ORGDISTR_AMT)  DISTR_JAMT,  SUM(A.MNGCOMIS_AMT)  MNGCOMIS_AMT,  SUM(CASE  WHEN  J.MB_GBN1  =  'F'   \n";
        query +=" AND  NVL(FIDU.GET_FOR_ATAX_YN(A.MDM_CD),  '0')  <>  '1'  THEN  A.ORGDISTR_AMT  ELSE  A.ORGDISTR_AMT  -  A.ATAX_AMT  END)  AS  SUSU_COM,  SUM(CASE  WHEN  SUBSTR(J.MB_CD,1,1)  =  'U'  THEN  0  ELSE  A.MNGCOMIS_AMT  END)  AS  MNGCOMIS_AMT_DOM,  SUM(CASE  WHEN  SUBSTR(J.MB_CD,1,1)  =  'U'  THEN  A.MNGCOMIS_AMT  ELSE  0  END)  AS  MNGCOMIS_AMT_FOR  FROM  (   \n";
        query +=" SELECT  MDM_CD,  MB_CD,  SUM(ORGDISTR_AMT)  ORGDISTR_AMT,  SUM(MNGCOMIS_AMT)  MNGCOMIS_AMT,  SUM(ATAX_AMT)  ATAX_AMT,  0  DISTR_PLAN_AMT,  0  SUPP_SUSP_AMT,  0  ETC_AMT,  0  DISTR_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  WHERE  SUPP_YRMN  =  :WRK_YRMN  GROUP  BY  MDM_CD,  MB_CD  UNION  ALL   \n";
        query +=" SELECT  MDM_CD,  NULL  AS  MB_CD,  0  ORGDISTR_AMT,  0  MNGCOMIS_AMT,  0  ATAX_AMT,  SUM(DISTR_PLAN_AMT)  DISTR_PLAN_AMT,  SUM(SUPP_SUSP_AMT)  SUPP_SUSP_AMT,  SUM(ETC_AMT)  ETC_AMT,  SUM(DISTR_AMT)  DISTR_AMT  FROM  FIDU.TDIS_DISTRPLANAMT  WHERE  WRK_YRMN  =  :WRK_YRMN  GROUP  BY  MDM_CD  )  A  LEFT  JOIN  FIDU.TMEM_MB  J  ON  A.MB_CD  =  J.MB_CD  JOIN  FIDU.TENV_MDMCD  C  ON  C.MDM_CD  =  A.MDM_CD  LEFT  JOIN  (   \n";
        query +=" SELECT  MDM_CD,  MAX(MNGCOMIS_RATE)  MNGCOMIS_RATE  FROM  FIDU.TENV_MNGCOMIS  WHERE  APPL_YRMN  =  :WRK_YRMN  GROUP  BY  MDM_CD  )  H  ON  C.MDM_CD  =  H.MDM_CD  LEFT  JOIN  (   \n";
        query +=" SELECT  MDM_CD,  SUM(RIGHTPRES_DISTR_MAGP)  RIGHTPRES_DISTR_MAGP  FROM  FIDU.TTAC_MDMCLASSDISTRAMT  WHERE  SUPP_YRMN  =  :WRK_YRMN  GROUP  BY  MDM_CD  )  D  ON  C.MDM_CD  =  D.MDM_CD  LEFT  JOIN  (   \n";
        query +=" SELECT  MDM_CD,  SUM(BORYU_AMT)  BORYU_AMT  FROM  (   \n";
        query +=" SELECT  MDM_CD,  SUM(NVL(SUSP_AMT,0))  BORYU_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  SUPP_YRMN  =  :WRK_YRMN  GROUP  BY  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  MDM_CD,  SUM(NVL(DISTR_AMT,0))  BORYU_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  WRK_YRMN  =  :WRK_YRMN  GROUP  BY  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  MDM_CD,  SUM(NVL(OCC_AMT,0))  AS  BORYU_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  :WRK_YRMN  GROUP  BY  MDM_CD  )  GROUP  BY  MDM_CD  )  E  ON  C.MDM_CD  =  E.MDM_CD  LEFT  JOIN  (   \n";
        query +=" SELECT  MDM_CD,  SUM(RES_AMT)  RES_AMT  FROM  (   \n";
        query +=" SELECT  MDM_CD,  SUM(NVL(SUPP_AMT,0))  AS  RES_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  RELS_YRMN  =  :WRK_YRMN  GROUP  BY  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  MDM_CD,  SUM(NVL(DISTR_AMT,0))  AS  RES_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  SUPP_YRMN  =  :WRK_YRMN  GROUP  BY  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  MDM_CD,  SUM(NVL(OCC_AMT,0))  AS  RES_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  :WRK_YRMN  GROUP  BY  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  MDM_CD,  SUM(NVL(SUPP_AMT,0))  AS  RES_AMT  FROM  FIDU.TTAC_MDMSUPPSUSPAMT  WHERE  SUPP_YRMN  =  :WRK_YRMN  GROUP  BY  MDM_CD  )  GROUP  BY  MDM_CD  )  F  ON  C.MDM_CD  =  F.MDM_CD  LEFT  JOIN  (   \n";
        query +=" SELECT  MDM_CD,  SUM(ATAX_AMT)  ATAX_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A  JOIN  FIDU.TMEM_MB  B  ON  A.MB_CD  =  B.MB_CD  WHERE  SUPP_YRMN  =  :WRK_YRMN   \n";
        query +=" AND  NVL(FIDU.GET_FOR_ATAX_YN(MDM_CD),'0')  <>  '1'   \n";
        query +=" AND  B.MB_GBN1  =  'F'  GROUP  BY  MDM_CD  )  G  ON  C.MDM_CD  =  G.MDM_CD  LEFT  JOIN  (   \n";
        query +=" SELECT  MDM_CD,  SUM(DISTR_AMT)  DISTR_AMT  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =  :WRK_YRMN  GROUP  BY  MDM_CD  )  I  ON  C.MDM_CD  =  I.MDM_CD  GROUP  BY  C.AVECLASS_CD,  C.AVECLASS_CD_NM,  H.MNGCOMIS_RATE  HAVING  SUM(A.ORGDISTR_AMT)  >  0  ORDER  BY  C.AVECLASS_CD ";
        sobj.setSql(query);
        sobj.setString("WRK_YRMN", WRK_YRMN);               //작업 년월
        return sobj;
    }
    //##**$$mdm_search
    //##**$$end
}
