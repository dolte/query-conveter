
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r12
{
    public tac10_r12()
    {
    }
    //##**$$for_use_dis
    /*
    */
    public DOBJ CTLfor_use_dis(DOBJ dobj)
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
            dobj  = CALLfor_use_dis_SEL1(Conn, dobj);           //  외국입금사용료 분배내역서
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
    public DOBJ CTLfor_use_dis( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLfor_use_dis_SEL1(Conn, dobj);           //  외국입금사용료 분배내역서
        return dobj;
    }
    // 외국입금사용료 분배내역서
    public DOBJ CALLfor_use_dis_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLfor_use_dis_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLfor_use_dis_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  FIDU.GET_BSCON_NM(BSCON_CD)  AS  BSCON_CD  ,  RIGHTPRES_MB_CD  ,  PROD_CD  ,  FIDU.GET_PROD_NM(PROD_CD)  AS  PROD_TTL  ,  ROUND(F2)  AS  F2  ,  ROUND(M3)  AS  M3  ,  ROUND(E4)  AS  E4  ,  ROUND(CRD)  AS  CRD  FROM  (   \n";
        query +=" SELECT  A.BSCON_CD  ,  A.RIGHTPRES_MB_CD  ,  A.PROD_CD  ,  FIDU.GET_PROD_NM(A.PROD_CD)  AS  PROD_TTL  ,  SUM(DECODE(SVC_CD,'ZA010101',DISTR_AMT,0))  AS  F2  ,  SUM(DECODE(SVC_CD,'ZA010102',DISTR_AMT,0))  AS  M3  ,  SUM(DECODE(SVC_CD,'ZA010103',DISTR_AMT,0))  AS  E4  ,  SUM(DECODE(SVC_CD,'ZA010104',DISTR_AMT,0))  AS  CRD  FROM  FIDU.TDIS_DISTR  A  WHERE  WRK_YRMN  =  :SUPP_YRMN   \n";
        query +=" AND  MDM_CD  =  'ZA0101'   \n";
        query +=" AND  A.RIGHTPRES_MB_CD  =  :MB_CD  GROUP  BY  BSCON_CD  ,  RIGHTPRES_MB_CD  ,  A.PROD_CD  )  WHERE  ROUND(F2)  +  ROUND(M3)  +  ROUND(E4)  +  ROUND(CRD)  >=  1  ORDER  BY  BSCON_CD  ASC ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$for_use_dis
    //##**$$sub_detail
    /*
    */
    public DOBJ CTLsub_detail(DOBJ dobj)
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
            dobj  = CALLsub_detail_SEL3(Conn, dobj);           //  방송
            dobj  = CALLsub_detail_SEL5(Conn, dobj);           //  복사
            dobj  = CALLsub_detail_SEL6(Conn, dobj);           //  공연
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
    public DOBJ CTLsub_detail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsub_detail_SEL3(Conn, dobj);           //  방송
        dobj  = CALLsub_detail_SEL5(Conn, dobj);           //  복사
        dobj  = CALLsub_detail_SEL6(Conn, dobj);           //  공연
        return dobj;
    }
    // 방송
    public DOBJ CALLsub_detail_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsub_detail_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsub_detail_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   AS_TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //AS_TRNSF_GBN
        String   AS_SUPP_YRMN = dobj.getRetObject("S").getRecord().get("FROMDATE");   //AS_SUPP_YRMN
        String   AS_MB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //AS_MB_CD
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.WRK_YRMN  AS  SUPP_YRMN,  A.RIGHTPRES_MB_CD  AS  MB_CD,  SUBSTR(A.MDM_CD,1,1)  AS  LARGECLASS,  SUBSTR(A.MDM_CD,1,2)  AS  AVECLASS,  (   \n";
        query +=" SELECT  SCLASS_CD_NM  FROM  FIDU.TENV_MDMCD  WHERE  MDM_CD  =  A.MDM_CD  )  AS  TAXCLASS,  (   \n";
        query +=" SELECT  MDM_CD_NM  FROM  FIDU.TENV_MDMCD  WHERE  MDM_CD  =  A.MDM_CD  )  AS  TAXTAXCLASS,  (   \n";
        query +=" SELECT  SVC_NM  FROM  FIDU.TENV_SVCCD  WHERE  SVC_CD  =  A.SVC_CD  )  AS  TAXTAXTAXCLASS,  (   \n";
        query +=" SELECT  BSCONHAN_NM  FROM  FIDU.TLEV_BSCON  WHERE  BSCON_CD  =  A.BSCON_CD  )  AS  BSCON,  SUM(DISTR_AMT)  AS  AMT,  CASE  WHEN  SUBSTR(MDM_CD,1,1)  =  'A'  THEN  'SUB_A'  WHEN  SUBSTR(MDM_CD,1,1)  =  'B'  THEN  'SUB_B'  WHEN  SUBSTR(MDM_CD,1,1)  =  'C'  THEN  'SUB_C'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DA'  THEN  'SUB_C'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DB'  THEN  'SUB_D1'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DC'  THEN  'SUB_D1'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DD'  THEN  'SUB_D2'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DE'  THEN  'SUB_D2'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DF'  THEN  'SUB_D2'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DG'  THEN  'SUB_D2'  WHEN  SUBSTR(MDM_CD,1,2)  =  'ZA'  THEN  'SUB_ZA'  ELSE  'SUB_ZA'  END  POPUP_GB,  A.MDM_CD,  A.SVC_CD,  A.BSCON_CD,  :AS_TRNSF_GBN  AS  AS_TRNSF_GBN  FROM  FIDU.TDIS_DISTR  A  WHERE  A.WRK_YRMN  =  :AS_SUPP_YRMN   \n";
        query +=" AND  A.RIGHTPRES_MB_CD  =  :AS_MB_CD   \n";
        query +=" AND  A.SUSP_GBN  IS  NULL   \n";
        query +=" AND  (  (:AS_TRNSF_GBN  =  1   \n";
        query +=" AND  A.ORGAU_MB_CD  =  A.RIGHTPRES_MB_CD)   \n";
        query +=" OR  (:AS_TRNSF_GBN  =  2   \n";
        query +=" AND  A.ORGAU_MB_CD  <>  A.RIGHTPRES_MB_CD)  )   \n";
        query +=" AND  SUBSTR(A.MDM_CD,1,1)  =  'A'  GROUP  BY  A.WRK_YRMN,A.RIGHTPRES_MB_CD,A.MDM_CD,A.SVC_CD,A.BSCON_CD  ORDER  BY  SUBSTR(A.MDM_CD,1,2),  TAXCLASS,TAXTAXCLASS,TAXTAXTAXCLASS,BSCON ";
        sobj.setSql(query);
        sobj.setString("AS_TRNSF_GBN", AS_TRNSF_GBN);               //AS_TRNSF_GBN
        sobj.setString("AS_SUPP_YRMN", AS_SUPP_YRMN);               //AS_SUPP_YRMN
        sobj.setString("AS_MB_CD", AS_MB_CD);               //AS_MB_CD
        return sobj;
    }
    // 복사
    public DOBJ CALLsub_detail_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsub_detail_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsub_detail_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   AS_TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //AS_TRNSF_GBN
        String   AS_SUPP_YRMN = dobj.getRetObject("S").getRecord().get("FROMDATE");   //AS_SUPP_YRMN
        String   AS_MB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //AS_MB_CD
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.WRK_YRMN  AS  SUPP_YRMN,  A.RIGHTPRES_MB_CD  AS  MB_CD,  SUBSTR(A.MDM_CD,1,1)  AS  LARGECLASS,  SUBSTR(A.MDM_CD,1,2)  AS  AVECLASS,  (   \n";
        query +=" SELECT  SCLASS_CD_NM  FROM  FIDU.TENV_MDMCD  WHERE  MDM_CD  =  A.MDM_CD  )  AS  TAXCLASS,  (   \n";
        query +=" SELECT  MDM_CD_NM  FROM  FIDU.TENV_MDMCD  WHERE  MDM_CD  =  A.MDM_CD  )  AS  TAXTAXCLASS,  (   \n";
        query +=" SELECT  SVC_NM  FROM  FIDU.TENV_SVCCD  WHERE  SVC_CD  =  A.SVC_CD  )  AS  TAXTAXTAXCLASS,  (   \n";
        query +=" SELECT  BSCONHAN_NM  FROM  FIDU.TLEV_BSCON  WHERE  BSCON_CD  =  A.BSCON_CD  )  AS  BSCON,  SUM(DISTR_AMT)  AS  AMT,  CASE  WHEN  SUBSTR(MDM_CD,1,1)  =  'A'  THEN  'SUB_A'  WHEN  SUBSTR(MDM_CD,1,1)  =  'B'  THEN  'SUB_B'  WHEN  SUBSTR(MDM_CD,1,1)  =  'C'  THEN  'SUB_C'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DA'  THEN  'SUB_C'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DB'  THEN  'SUB_D1'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DC'  THEN  'SUB_D1'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DD'  THEN  'SUB_D2'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DE'  THEN  'SUB_D2'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DF'  THEN  'SUB_D2'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DG'  THEN  'SUB_D2'  WHEN  SUBSTR(MDM_CD,1,2)  =  'ZA'  THEN  'SUB_ZA'  ELSE  'SUB_ZA'  END  POPUP_GB,  A.MDM_CD,  A.SVC_CD,  A.BSCON_CD,  :AS_TRNSF_GBN  AS  AS_TRNSF_GBN  FROM  FIDU.TDIS_DISTR  A  WHERE  A.WRK_YRMN  =  :AS_SUPP_YRMN   \n";
        query +=" AND  A.RIGHTPRES_MB_CD  =  :AS_MB_CD   \n";
        query +=" AND  A.SUSP_GBN  IS  NULL   \n";
        query +=" AND  (  (:AS_TRNSF_GBN  =  1   \n";
        query +=" AND  A.ORGAU_MB_CD  =  A.RIGHTPRES_MB_CD)   \n";
        query +=" OR  (:AS_TRNSF_GBN  =  2   \n";
        query +=" AND  A.ORGAU_MB_CD  <>  A.RIGHTPRES_MB_CD)  )   \n";
        query +=" AND  SUBSTR(A.MDM_CD,1,1)  =  'C'  GROUP  BY  A.WRK_YRMN,A.RIGHTPRES_MB_CD,A.MDM_CD,A.SVC_CD,A.BSCON_CD  ORDER  BY  SUBSTR(A.MDM_CD,1,2),  TAXCLASS,TAXTAXCLASS,TAXTAXTAXCLASS,BSCON ";
        sobj.setSql(query);
        sobj.setString("AS_TRNSF_GBN", AS_TRNSF_GBN);               //AS_TRNSF_GBN
        sobj.setString("AS_SUPP_YRMN", AS_SUPP_YRMN);               //AS_SUPP_YRMN
        sobj.setString("AS_MB_CD", AS_MB_CD);               //AS_MB_CD
        return sobj;
    }
    // 공연
    public DOBJ CALLsub_detail_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsub_detail_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsub_detail_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   AS_TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //AS_TRNSF_GBN
        String   AS_SUPP_YRMN = dobj.getRetObject("S").getRecord().get("FROMDATE");   //AS_SUPP_YRMN
        String   AS_MB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //AS_MB_CD
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.WRK_YRMN  AS  SUPP_YRMN,  A.RIGHTPRES_MB_CD  AS  MB_CD,  SUBSTR(A.MDM_CD,1,1)  AS  LARGECLASS,  SUBSTR(A.MDM_CD,1,2)  AS  AVECLASS,  (   \n";
        query +=" SELECT  SCLASS_CD_NM  FROM  FIDU.TENV_MDMCD  WHERE  MDM_CD  =  A.MDM_CD  )  AS  TAXCLASS,  (   \n";
        query +=" SELECT  MDM_CD_NM  FROM  FIDU.TENV_MDMCD  WHERE  MDM_CD  =  A.MDM_CD  )  AS  TAXTAXCLASS,  (   \n";
        query +=" SELECT  SVC_NM  FROM  FIDU.TENV_SVCCD  WHERE  SVC_CD  =  A.SVC_CD  )  AS  TAXTAXTAXCLASS,  (   \n";
        query +=" SELECT  BSCONHAN_NM  FROM  FIDU.TLEV_BSCON  WHERE  BSCON_CD  =  A.BSCON_CD  )  AS  BSCON,  SUM(DISTR_AMT)  AS  AMT,  CASE  WHEN  SUBSTR(MDM_CD,1,1)  =  'A'  THEN  'SUB_A'  WHEN  SUBSTR(MDM_CD,1,1)  =  'B'  THEN  'SUB_B'  WHEN  SUBSTR(MDM_CD,1,1)  =  'C'  THEN  'SUB_C'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DA'  THEN  'SUB_C'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DB'  THEN  'SUB_D1'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DC'  THEN  'SUB_D1'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DD'  THEN  'SUB_D2'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DE'  THEN  'SUB_D2'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DF'  THEN  'SUB_D2'  WHEN  SUBSTR(MDM_CD,1,2)  =  'DG'  THEN  'SUB_D2'  WHEN  SUBSTR(MDM_CD,1,2)  =  'ZA'  THEN  'SUB_ZA'  ELSE  'SUB_ZA'  END  POPUP_GB,  A.MDM_CD,  A.SVC_CD,  A.BSCON_CD,  :AS_TRNSF_GBN  AS  AS_TRNSF_GBN  FROM  FIDU.TDIS_DISTR  A  WHERE  A.WRK_YRMN  =  :AS_SUPP_YRMN   \n";
        query +=" AND  A.RIGHTPRES_MB_CD  =  :AS_MB_CD   \n";
        query +=" AND  A.SUSP_GBN  IS  NULL   \n";
        query +=" AND  (  (:AS_TRNSF_GBN  =  1   \n";
        query +=" AND  A.ORGAU_MB_CD  =  A.RIGHTPRES_MB_CD)   \n";
        query +=" OR  (:AS_TRNSF_GBN  =  2   \n";
        query +=" AND  A.ORGAU_MB_CD  <>  A.RIGHTPRES_MB_CD)  )   \n";
        query +=" AND  SUBSTR(A.MDM_CD,1,1)  =  'D'  GROUP  BY  A.WRK_YRMN,A.RIGHTPRES_MB_CD,A.MDM_CD,A.SVC_CD,A.BSCON_CD  ORDER  BY  SUBSTR(A.MDM_CD,1,2),  TAXCLASS,TAXTAXCLASS,TAXTAXTAXCLASS,BSCON ";
        sobj.setSql(query);
        sobj.setString("AS_TRNSF_GBN", AS_TRNSF_GBN);               //AS_TRNSF_GBN
        sobj.setString("AS_SUPP_YRMN", AS_SUPP_YRMN);               //AS_SUPP_YRMN
        sobj.setString("AS_MB_CD", AS_MB_CD);               //AS_MB_CD
        return sobj;
    }
    //##**$$sub_detail
    //##**$$search_bun_distr
    /*
    */
    public DOBJ CTLsearch_bun_distr(DOBJ dobj)
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
            dobj  = CALLsearch_bun_distr_SEL1(Conn, dobj);           //  회원별분배금액
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
    public DOBJ CTLsearch_bun_distr( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_bun_distr_SEL1(Conn, dobj);           //  회원별분배금액
        return dobj;
    }
    // 회원별분배금액
    public DOBJ CALLsearch_bun_distr_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_bun_distr_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_bun_distr_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  RIGHTPRES_MB_CD  MB_CD,  FIDU.GET_MB_NM  (RIGHTPRES_MB_CD)  MB_NM,  FIDU.GET_SN_NM  (RIGHTPRES_MB_CD)  SN,  FIDU.GET_SVC_NM(TRIM(A.SVC_CD))  SVC_NM,  SUM  (DECODE  (A.BRDCS_GBN,  '1',  A.DISTR_AMT,  0))  RADIO_AMT,  SUM  (DECODE  (A.BRDCS_GBN,  '1',  A.BRDCS_FREQ,  0))  RADIO_FREQ,  SUM  (DECODE  (A.BRDCS_GBN,  '2',  A.DISTR_AMT,  0))  TV_AMT,  SUM  (DECODE  (A.BRDCS_GBN,  '2',  A.BRDCS_FREQ,  0))  TV_FREQ,  SUM  (A.DISTR_AMT)  TOT_AMT,  SUM  (A.BRDCS_FREQ)  TOT_FREQ  FROM  FIDU.TDIS_BRDCS_FREQ_SUM  A  WHERE  WRK_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  RIGHTPRES_MB_CD  =  :MB_CD  GROUP  BY  RIGHTPRES_MB_CD,  A.SVC_CD  ORDER  BY  SVC_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$search_bun_distr
    //##**$$select_distr_msg
    /*
    */
    public DOBJ CTLselect_distr_msg(DOBJ dobj)
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
            dobj  = CALLselect_distr_msg_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLselect_distr_msg( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLselect_distr_msg_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLselect_distr_msg_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_distr_msg_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_distr_msg_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  YRMN,  CONTENT1  FROM  HOMP.THOM_PAYMENT_NOTICE  WHERE  YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  USE_YN  =  'Y'  ORDER  BY  YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        return sobj;
    }
    //##**$$select_distr_msg
    //##**$$email_target_select
    /*
    */
    public DOBJ CTLemail_target_select(DOBJ dobj)
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
            dobj  = CALLemail_target_select_SEL1(Conn, dobj);           //  이메일 적용
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
    public DOBJ CTLemail_target_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLemail_target_select_SEL1(Conn, dobj);           //  이메일 적용
        return dobj;
    }
    // 이메일 적용
    public DOBJ CALLemail_target_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLemail_target_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLemail_target_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_GBN = dvobj.getRecord().get("MB_GBN");   //회원구분
        String   FRMB_CD = dvobj.getRecord().get("FRMB_CD");   //회원코드
        String   FROM_YRMN = dvobj.getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dvobj.getRecord().get("TO_YRMN");   //종료년월
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        String   TOMB_CD = dvobj.getRecord().get("TOMB_CD");   //회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.EMAIL_ADDR  TO_EMAIL,  B.HANMB_NM  TO_NAME,  'acc@komca.or.kr'  FROM_EMAIL,  '한국음악저작권협회'  FROM_NAME,  '저작권사용료  지급내역서'  SUBJECT,  'N'  TARGET_FLAG,  DECODE(LENGTH(B.INS_NUM),13,SUBSTR(INS_NUM,7,7),INS_NUM)  PASS_NUM,  A.TRNSF_GBN  TRNSF_GBN,  A.SUPP_YRMN  SUPP_YRMN,  A.MB_CD  MB_CD  FROM  FIDU.TTAC_COPYRTAL  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  >=:FROM_YRMN   \n";
        query +=" AND  A.SUPP_YRMN  <=:TO_YRMN   \n";
        query +=" AND  B.EMAIL_ADDR  IS  NOT  NULL   \n";
        query +=" AND  A.MB_CD  >=  :FRMB_CD   \n";
        query +=" AND  A.MB_CD  <=  :TOMB_CD   \n";
        query +=" AND  B.MB_GBN1  LIKE  :MB_GBN  ||'%'   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  ||'%'   \n";
        query +=" AND  NVL(B.SUPPBRE_POST_RECV_YN,'D')  !='D'   \n";
        query +=" AND  A.TOT_ORGDISTR_AMT  >  0  ORDER  BY  A.SUPP_YRMN,  A.MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_GBN", MB_GBN);               //회원구분
        sobj.setString("FRMB_CD", FRMB_CD);               //회원코드
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("TOMB_CD", TOMB_CD);               //회원코드
        return sobj;
    }
    //##**$$email_target_select
    //##**$$email_send_test
    /*
    */
    public DOBJ CTLemail_send_test(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
            Conn.setAutoCommit(false);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLemail_send_test_XIUD1(Conn, dobj);           //  이메일 전송 적용
            dobj  = CALLemail_send_test_SEL2(Conn, dobj);           //  MMS발송대상조회_원본
            dobj  = CALLemail_send_test_MPD1(Conn, dobj);           //  MMS저장LOOP
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            Conn.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
                Conn.rollback();
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
    public DOBJ CTLemail_send_test( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLemail_send_test_XIUD1(Conn, dobj);           //  이메일 전송 적용
        dobj  = CALLemail_send_test_SEL2(Conn, dobj);           //  MMS발송대상조회_원본
        dobj  = CALLemail_send_test_MPD1(Conn, dobj);           //  MMS저장LOOP
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 이메일 전송 적용
    public DOBJ CALLemail_send_test_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLemail_send_test_XIUD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLemail_send_test_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //회원코드
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   MB_GBN = dobj.getRetObject("S").getRecord().get("MB_GBN");   //회원구분
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //회원코드
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO amail.ems_mailqueue (seq, mail_code, to_email, to_name, from_email, from_name, subject, target_flag, map1, map2, map3, map4, map5, map_content, reg_date ) SELECT amail.ems_mailqueue_seq.nextval,'25',B.EMAIL_ADDR, B.HANMB_NM , 'acc1@komca.or.kr' , '한국음악저작권협회' , SUBSTR(A.SUPP_YRMN,1,4)||'년'||SUBSTR(A.SUPP_YRMN,5,2) ||'월'||' 저작권사용료 지급내역서' , 'N' , DECODE(NVL(BRYR_MONDAY,' '),' ','I', 'B') , A.TRNSF_GBN , A.SUPP_YRMN , A.MB_CD , SUBSTR(A.SUPP_YRMN,1,4)||'년 '||SUBSTR(A.SUPP_YRMN,5,2)||'월', 'TEMP' , SYSDATE FROM FIDU.TTAC_COPYRTAL A, FIDU.TMEM_MB B WHERE A.MB_CD = B.MB_CD AND A.SUPP_YRMN >=:FROM_YRMN AND A.SUPP_YRMN <= :TO_YRMN AND B.EMAIL_ADDR IS NOT NULL AND A.MB_CD >= :FRMB_CD AND A.MB_CD <= :TOMB_CD AND B.MB_GBN1 LIKE :MB_GBN AND A.TRNSF_GBN LIKE :TRNSF_GBN AND NVL(B.SUPPBRE_POST_RECV_YN,'D') !='D'";
        sobj.setSql(query);
        sobj.setString("FRMB_CD", FRMB_CD);               //회원코드
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("MB_GBN", MB_GBN);               //회원구분
        sobj.setString("TOMB_CD", TOMB_CD);               //회원코드
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        return sobj;
    }
    // MMS발송대상조회_원본
    public DOBJ CALLemail_send_test_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLemail_send_test_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLemail_send_test_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_GBN = dobj.getRetObject("S").getRecord().get("MB_GBN");   //회원구분
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //회원코드
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  KAKAO.ALIMTALK_SEQ.NEXTVAL  SN,  KAKAO.ALIMTALK_SEQ.NEXTVAL  MSG_ID,  SENDER_KEY,  CHANNEL,  SND_TYPE,  TMPL_CD,  USER_ID,  SUBJECT,  SMS_SND_NUM,  PHONE_NUM,  SND_MSG,  ATTACHMENT,  REQ_DTM,  SMS_SND_YN,  RESERVED1,  RESERVED3,  RESERVED4,  RESERVED5,  'SUPP'  RESERVED6  FROM   \n";
        query +=" (SELECT  '9bb6b0be43e3d0ae1021d18efd14b8196be7cb8d'  SENDER_KEY,  'A'  CHANNEL,  'P'  SND_TYPE,  'JIGEUB_01'  TMPL_CD,  A.MB_CD  USER_ID,  TO_NUMBER  (SUBSTR  (SUPP_YRMN,  5))  ||  '월  스마트  지급내역서'  SUBJECT,  '0226600483'  SMS_SND_NUM,  TRANSLATE(CP_NUM,  '0123456789'||CP_NUM,  '0123456789')  PHONE_NUM,  B.HANMB_NM  ||  '회원님  안녕하십니까'  ||  CHR  (63)  ||  CHR  (13)  ||  CHR  (10)  ||  '한국음악저작권협회입니다.'  ||  CHR  (13)  ||  CHR  (10)  ||  TO_NUMBER  (SUBSTR  (SUPP_YRMN,  1,  4))  ||  '년  '  ||  TO_NUMBER  (SUBSTR  (SUPP_YRMN,  5))  ||  '월  저작권료  지급내역을  다음과  같이  알려드립니다.'  ||  CHR  (13)  ||  CHR  (10)  ||  CHR  (13)  ||  CHR  (10)  ||  '['  ||  TO_NUMBER  (SUBSTR  (SUPP_YRMN,  5))  ||  '월  스마트  지급내역서]'  ||  CHR  (13)  ||  CHR  (10)  ||  '  ♬  지급일  :  '  ||  TO_CHAR  (SYSDATE,  'YYYY')  ||  '년  '  ||  TO_NUMBER  (TO_CHAR  (SYSDATE,  'MM'))  ||  '월  '  ||  TO_NUMBER  (TO_CHAR  (SYSDATE,  'DD'))  ||  '일  지급되었습니다.'  ||  CHR  (13)  ||  CHR  (10)  ||  '  ♬  내역서  :  https:/'  ||  '/m.komca.or.kr:8700/mobile2/navigation.jsp'  ||  CHR  (63)  ||  'm=2&seq=#SEQ#'  ||  CHR  (13)  ||  CHR  (10)  ||  '자세한  사항은  내역서를  누르세요'  SND_MSG,  CHR  (123)  ||  CHR(34)||'button'||CHR(34)||'  :  '||  CHR  (91)  ||  CHR  (123)  ||  CHR(34)||'name'||CHR(34)||'  :  '||CHR(34)||'지급내역서'||CHR(34)||',  '||CHR(34)||'type'||CHR(34)||'  :  '||CHR(34)||'WL'||CHR(34)||',  '||CHR(34)||'url_mobile'||CHR(34)||'  :  '||CHR(34)||'https:/'  ||  '/m.komca.or.kr:8700/mobile2/navigation.jsp'  ||  CHR  (63)  ||  'm=2&seq=#SEQ#'||CHR(34)  ||  CHR  (125)  ||  CHR  (93)  ||  CHR  (125)  ATTACHMENT,  TO_CHAR  (SYSDATE,  'yyyymmddhh24miss')  REQ_DTM,  'Y'  SMS_SND_YN,  B.HANMB_NM  RESERVED1,  B.MB_GBN1  RESERVED3,  A.SUPP_YRMN  RESERVED4,  A.TRNSF_GBN  RESERVED5  FROM  FIDU.TTAC_COPYRTAL  A,  FIDU.TMEM_MB  B,  ACCT.TCAC_BANK  C  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  B.SUPPBANK_CD  =  C.BANK_CD   \n";
        query +=" AND  A.SUPP_YRMN  >=  :FROM_YRMN   \n";
        query +=" AND  A.SUPP_YRMN  <=  :TO_YRMN   \n";
        query +=" AND  REGEXP_REPLACE  (LENGTH  (NVL  (B.CP_NUM,  B.CP_NUM2)),  '[^0-9]',  '')  >=  10   \n";
        query +=" AND  A.MB_CD  >=  :FRMB_CD   \n";
        query +=" AND  A.MB_CD  <=  :TOMB_CD   \n";
        query +=" AND  B.MB_GBN1  LIKE  :MB_GBN  ||  '%'   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'   \n";
        query +=" AND  NVL  (B.SUPPBRE_POST_RECV_YN,  'D')  !=  'D') ";
        sobj.setSql(query);
        sobj.setString("MB_GBN", MB_GBN);               //회원구분
        sobj.setString("FRMB_CD", FRMB_CD);               //회원코드
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("TOMB_CD", TOMB_CD);               //회원코드
        return sobj;
    }
    // MMS저장LOOP
    public DOBJ CALLemail_send_test_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("SEL2");         //MMS발송대상조회_원본에서 생성시킨 OBJECT입니다.(CALLemail_send_test_SEL2)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLemail_send_test_INS7(Conn, dobj);           //  발송대상입력
            }
        }
        return dobj;
    }
    // 발송대상입력
    public DOBJ CALLemail_send_test_INS7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS7");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLemail_send_test_INS7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS7") ;
        rvobj.Println("INS7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLemail_send_test_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ATTACHMENT = dobj.getRetObject("R").getRecord().get("ATTACHMENT");        //알림톡버튼
        String SND_MSG = dobj.getRetObject("R").getRecord().get("SND_MSG");        //멀티 문자
        String   RESERVED4 = dvobj.getRecord().get("RESERVED4");   //테스트컬럼4
        String   RESERVED5 = dvobj.getRecord().get("RESERVED5");   //테스트컬럼2
        String   REQ_DTM = dvobj.getRecord().get("REQ_DTM");   //발송요청일시
        String   USER_ID = dvobj.getRecord().get("USER_ID");   //사용자번호
        String   TMPL_CD = dvobj.getRecord().get("TMPL_CD");   //알림톡탬플릿코드
        String   CHANNEL = dvobj.getRecord().get("CHANNEL");   //알림톡채널
        String   SMS_SND_YN = dvobj.getRecord().get("SMS_SND_YN");   //sms발송여부
        String   SND_TYPE = dvobj.getRecord().get("SND_TYPE");   //알림톡발송방식
        String   ATTACHMENT_2 = dobj.getRetObject("SEL2").getRecord().get("MSG_ID");   //알림톡버튼링크
        String   ATTACHMENT_1 = dvobj.getRecord().get("ATTACHMENT");   //알림톡버튼링크
        String   SMS_SND_NUM = dvobj.getRecord().get("SMS_SND_NUM");   //sms발신여부
        String   RESERVED3 = dvobj.getRecord().get("RESERVED3");   //테스트컬럼1
        String   SND_MSG_2 = dobj.getRetObject("SEL2").getRecord().get("MSG_ID");   //알림톡내용
        String   SND_MSG_1 = dvobj.getRecord().get("SND_MSG");   //알림톡내용
        String   RESERVED6 = dvobj.getRecord().get("RESERVED6");   //테스트컬럼2
        String   RESERVED1 = dvobj.getRecord().get("RESERVED1");   //테스트컬럼1
        String   SUBJECT = dvobj.getRecord().get("SUBJECT");   //제목
        String   PHONE_NUM = dvobj.getRecord().get("PHONE_NUM");   //수신자휴대폰번호
        String   SN = dvobj.getRecord().get("SN");   //예명
        String   RESERVED2 = dvobj.getRecord().get("RESERVED2");   //테스트컬럼2
        String   SENDER_KEY = dvobj.getRecord().get("SENDER_KEY");   //알림톡키
        double   MSG_ID = dobj.getRetObject("SEL2").getRecord().getDouble("MSG_ID");   //SMS KEY
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into KAKAO.MZSENDTRAN (SENDER_KEY, RESERVED2, SN, PHONE_NUM, SUBJECT, RESERVED1, RESERVED6, SND_MSG, RESERVED3, SMS_SND_NUM, MSG_ID, ATTACHMENT, SND_TYPE, SMS_SND_YN, CHANNEL, TMPL_CD, USER_ID, REQ_DTM, RESERVED5, RESERVED4)  \n";
        query +=" values(:SENDER_KEY , :RESERVED2 , :SN , :PHONE_NUM , :SUBJECT , :RESERVED1 , :RESERVED6 , REPLACE(:SND_MSG_1,'#SEQ#',:SND_MSG_2), :RESERVED3 , :SMS_SND_NUM , :MSG_ID , REPLACE(:ATTACHMENT_1,'#SEQ#',:ATTACHMENT_2), :SND_TYPE , :SMS_SND_YN , :CHANNEL , :TMPL_CD , :USER_ID , :REQ_DTM , :RESERVED5 , :RESERVED4 )";
        sobj.setSql(query);
        sobj.setString("RESERVED4", RESERVED4);               //테스트컬럼4
        sobj.setString("RESERVED5", RESERVED5);               //테스트컬럼2
        sobj.setString("REQ_DTM", REQ_DTM);               //발송요청일시
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        sobj.setString("TMPL_CD", TMPL_CD);               //알림톡탬플릿코드
        sobj.setString("CHANNEL", CHANNEL);               //알림톡채널
        sobj.setString("SMS_SND_YN", SMS_SND_YN);               //sms발송여부
        sobj.setString("SND_TYPE", SND_TYPE);               //알림톡발송방식
        sobj.setString("ATTACHMENT_2", ATTACHMENT_2);               //알림톡버튼링크
        sobj.setString("ATTACHMENT_1", ATTACHMENT_1);               //알림톡버튼링크
        sobj.setString("SMS_SND_NUM", SMS_SND_NUM);               //sms발신여부
        sobj.setString("RESERVED3", RESERVED3);               //테스트컬럼1
        sobj.setString("SND_MSG_2", SND_MSG_2);               //알림톡내용
        sobj.setString("SND_MSG_1", SND_MSG_1);               //알림톡내용
        sobj.setString("RESERVED6", RESERVED6);               //테스트컬럼2
        sobj.setString("RESERVED1", RESERVED1);               //테스트컬럼1
        sobj.setString("SUBJECT", SUBJECT);               //제목
        sobj.setString("PHONE_NUM", PHONE_NUM);               //수신자휴대폰번호
        sobj.setString("SN", SN);               //예명
        sobj.setString("RESERVED2", RESERVED2);               //테스트컬럼2
        sobj.setString("SENDER_KEY", SENDER_KEY);               //알림톡키
        sobj.setDouble("MSG_ID", MSG_ID);               //SMS KEY
        return sobj;
    }
    //##**$$email_send_test
    //##**$$email_send_stat
    /*
    */
    public DOBJ CTLemail_send_stat(DOBJ dobj)
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
            dobj  = CALLemail_send_stat_SEL1(Conn, dobj);           //  SEL
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
    public DOBJ CTLemail_send_stat( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLemail_send_stat_SEL1(Conn, dobj);           //  SEL
        return dobj;
    }
    // SEL
    public DOBJ CALLemail_send_stat_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLemail_send_stat_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLemail_send_stat_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_GBN = dobj.getRetObject("S").getRecord().get("MB_GBN");   //회원구분
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //회원코드
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  seq,  mail_code,  to_email,  to_name,  REG_DATE,  from_email,  from_name,  subject,  target_flag,  map1,  map2,  map3,  map4,  map5,  map_content  FROM  amail.ems_mailqueue  A,  FIDU.TMEM_MB  B  WHERE  MAIL_CODE  =  '25'   \n";
        query +=" AND  MAP3  >=  :FROM_YRMN   \n";
        query +=" AND  MAP3  <=  :TO_YRMN   \n";
        query +=" AND  MAP4  >=  :FRMB_CD   \n";
        query +=" AND  MAP4  <=  :TOMB_CD   \n";
        query +=" AND  MAP2  LIKE  :TRNSF_GBN   \n";
        query +=" AND  A.MAP4  =  B.MB_CD   \n";
        query +=" AND  B.MB_GBN1  LIKE  :MB_GBN ";
        sobj.setSql(query);
        sobj.setString("MB_GBN", MB_GBN);               //회원구분
        sobj.setString("FRMB_CD", FRMB_CD);               //회원코드
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("TOMB_CD", TOMB_CD);               //회원코드
        return sobj;
    }
    //##**$$email_send_stat
    //##**$$new_report_print
    /*
    */
    public DOBJ CTLnew_report_print(DOBJ dobj)
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
            dobj  = CALLnew_report_print_SEL1(Conn, dobj);           //  저작권료 조회
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
    public DOBJ CTLnew_report_print( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLnew_report_print_SEL1(Conn, dobj);           //  저작권료 조회
        return dobj;
    }
    // 저작권료 조회
    public DOBJ CALLnew_report_print_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLnew_report_print_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnew_report_print_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //회원코드
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.MB_CD, B.HANMB_NM, SUM (ORGDISTR_AMT) ORGDISTR_AMT, SUM (COMIS_AMT) AS COMIS_AMT  ";
        query +=" FROM (SELECT MB_CD, ORGDISTR_AMT, TRUNC ( (ORGDISTR_AMT - ATAX_AMT) * 0.125) COMIS_AMT  ";
        query +=" FROM FIDU.TTAC_MDMCLASSSUPPAMT  ";
        query +=" WHERE SUPP_YRMN = '201104'  ";
        query +=" AND MDM_CD = 'AB0109'  ";
        if( !FRMB_CD.equals("")  && !TOMB_CD.equals("") )
        {
            query +=" AND MB_CD BETWEEN :FRMB_CD  ";
            query +=" AND :TOMB_CD  ";
        }
        query +=" ) A, FIDU.TMEM_MB B  ";
        query +=" WHERE A.MB_CD = B.MB_CD  ";
        query +=" GROUP BY A.MB_CD, B.HANMB_NM  ";
        query +=" ORDER BY A.MB_CD  ";
        sobj.setSql(query);
        if(!FRMB_CD.equals(""))
        {
            sobj.setString("FRMB_CD", FRMB_CD);               //회원코드
        }
        if(!TOMB_CD.equals(""))
        {
            sobj.setString("TOMB_CD", TOMB_CD);               //회원코드
        }
        return sobj;
    }
    //##**$$new_report_print
    //##**$$search_mb1
    /*
    */
    public DOBJ CTLsearch_mb1(DOBJ dobj)
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
            dobj  = CALLsearch_mb1_SEL1(Conn, dobj);           //  지급
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
    public DOBJ CTLsearch_mb1( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_mb1_SEL1(Conn, dobj);           //  지급
        return dobj;
    }
    // 지급
    public DOBJ CALLsearch_mb1_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_mb1_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_mb1_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_GBN = dobj.getRetObject("S").getRecord().get("MB_GBN");   //회원구분
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //회원코드
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT MB_CD, MB_GBN1, SUPP_YRMN, DISTR_STOT, DISTR_AMT, MB_NM, SS_NUM  ";
        query +=" FROM (  ";
        query +=" SELECT X.MB_CD, X.SUPP_YRMN, SUM(NVL(X.TOT_REALSUPP_AMT,0)) AS DISTR_STOT , SUM(X.TOT_ORGDISTR_AMT) AS DISTR_AMT, Z.MB_GBN1 MB_GBN1, FIDU.GET_MB_NM ( X.MB_CD ) AS MB_NM, Z.INS_NUM AS SS_NUM  ";
        query +=" FROM FIDU.TTAC_COPYRTAL X , (SELECT MB_CD, POSTSNDBK_YN  ";
        query +=" FROM fidu.TMEM_ADBK  ";
        query +=" WHERE ADDR_GBN = 2) Y , FIDU.TMEM_MB Z  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND X.MB_CD = Y.MB_CD  ";
        query +=" AND X.MB_CD = Z.MB_CD  ";
        query +=" AND X.SUPP_YRMN BETWEEN SUBSTR(:FROMDATE,1,6)  ";
        query +=" AND SUBSTR(:TODATE,1,6)  ";
        query +=" AND X.MB_CD BETWEEN :FRMB_CD  ";
        query +=" AND :TOMB_CD  ";
        if( !MB_GBN.equals("") )
        {
            query +=" AND Z.MB_GBN1 = :MB_GBN  ";
        }
        query +=" AND NVL(Y.POSTSNDBK_YN,' ') <> '1'  ";
        query +=" GROUP BY X.MB_CD, X.SUPP_YRMN, Z.MB_GBN1, Z.INS_NUM  ";
        query +=" ORDER BY X.MB_CD )  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND DISTR_AMT > 0  ";
        sobj.setSql(query);
        if(!MB_GBN.equals(""))
        {
            sobj.setString("MB_GBN", MB_GBN);               //회원구분
        }
        sobj.setString("FRMB_CD", FRMB_CD);               //회원코드
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("TOMB_CD", TOMB_CD);               //회원코드
        return sobj;
    }
    //##**$$search_mb1
    //##**$$searchMst
    /* * 프로그램명 : tac10_r12
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
            dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  지급
            dobj  = CALLsearchMst_SEL2(Conn, dobj);           //  공제
            dobj  = CALLsearchMst_SEL3(Conn, dobj);           //  출판사계산서금액
            dobj  = CALLsearchMst_SEL4(Conn, dobj);           //  재분배금조회
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
        dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  지급
        dobj  = CALLsearchMst_SEL2(Conn, dobj);           //  공제
        dobj  = CALLsearchMst_SEL3(Conn, dobj);           //  출판사계산서금액
        dobj  = CALLsearchMst_SEL4(Conn, dobj);           //  재분배금조회
        return dobj;
    }
    // 지급
    // *22.10.31. <온라인공연 추가>  - DISTR_AMT31 DK 기타 <사용안하는 목록>  - DISTR_AMT8 BC 영상및기타전송  - DISTR_AMT25 DI 레스토랑  - DISTR_AMT29 ZC 신탁단체입금  - DISTR_AMT30 ZZ 기타
    public DOBJ CALLsearchMst_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   MB_GBN = dobj.getRetObject("S").getRecord().get("MB_GBN");   //회원구분
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //FRMB_CD
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //TOMB_CD
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT Z.SUPP_YRMN, (SELECT CONTENT1  ";
        query +=" FROM HOMP.THOM_PAYMENT_NOTICE  ";
        query +=" WHERE YRMN = Z.SUPP_YRMN  ";
        query +=" AND USE_YN = 'Y') AS DIV_COMMENT, Z.MB_CD, Z.HANMB_NM, (SELECT SUM (RELS_AMT)  ";
        query +=" FROM FIDU.TTAC_PRODSUPPSUSPRELS  ";
        query +=" WHERE SUPP_YRMN = Z.SUPP_YRMN  ";
        query +=" AND SUPP_MB_CD = Z.MB_CD) PROD_RELS_AMT, SUM (Z.DISTR_AMT1) AS DISTR_AMT1, SUM (Z.DISTR_AMT2) AS DISTR_AMT2, SUM (Z.DISTR_AMT3) AS DISTR_AMT3, SUM (Z.DISTR_AMT4) AS DISTR_AMT4, SUM (Z.DISTR_AMT5) AS DISTR_AMT5, SUM (Z.DISTR_AMT1) + SUM (Z.DISTR_AMT2) + SUM (Z.DISTR_AMT3) + SUM (Z.DISTR_AMT4) + SUM (Z.DISTR_AMT5) SUB_TOT1, SUM (Z.DISTR_AMT6) AS DISTR_AMT6, SUM (Z.DISTR_AMT7) AS DISTR_AMT7, SUM (Z.DISTR_AMT6) + SUM (Z.DISTR_AMT7) SUB_TOT2, SUM (Z.DISTR_AMT8) AS DISTR_AMT8, SUM (Z.DISTR_AMT9) AS DISTR_AMT9, SUM (Z.DISTR_AMT10) AS DISTR_AMT10, SUM (Z.DISTR_AMT11) AS DISTR_AMT11, SUM (Z.DISTR_AMT12) AS DISTR_AMT12, SUM (Z.DISTR_AMT13) AS DISTR_AMT13, SUM (Z.DISTR_AMT14) AS DISTR_AMT14, SUM (Z.DISTR_AMT15) AS DISTR_AMT15, SUM (Z.DISTR_AMT16) AS DISTR_AMT16, SUM (Z.DISTR_AMT8) + SUM (Z.DISTR_AMT9) + SUM (Z.DISTR_AMT10) + SUM (Z.DISTR_AMT11) + SUM (Z.DISTR_AMT12) + SUM (Z.DISTR_AMT13) + SUM (Z.DISTR_AMT14) + SUM (Z.DISTR_AMT15) + SUM (Z.DISTR_AMT16) SUB_TOT3, SUM (Z.DISTR_AMT17) AS DISTR_AMT17, SUM (Z.DISTR_AMT18) AS DISTR_AMT18, SUM (Z.DISTR_AMT19) AS DISTR_AMT19, SUM (Z.DISTR_AMT20) AS DISTR_AMT20, SUM (Z.DISTR_AMT21) AS DISTR_AMT21, SUM (Z.DISTR_AMT22) AS DISTR_AMT22, SUM (Z.DISTR_AMT23) AS DISTR_AMT23, SUM (Z.DISTR_AMT24) AS DISTR_AMT24, SUM (Z.DISTR_AMT26) AS DISTR_AMT26, SUM (Z.DISTR_AMT31) AS DISTR_AMT31, SUM (Z.DISTR_AMT17) + SUM (Z.DISTR_AMT18) + SUM (Z.DISTR_AMT19) + SUM (Z.DISTR_AMT20) + SUM (Z.DISTR_AMT21) + SUM (Z.DISTR_AMT22) + SUM (Z.DISTR_AMT23) + SUM (Z.DISTR_AMT24) + SUM (Z.DISTR_AMT26) + SUM (Z.DISTR_AMT31) SUB_TOT4, SUM (Z.DISTR_AMT27) AS DISTR_AMT27, SUM (Z.DISTR_AMT28) AS DISTR_AMT28, SUM (Z.DISTR_AMT27) + SUM (Z.DISTR_AMT28) SUB_TOT5, SUM (Z.COM1) COM1, SUM (Z.COM2) COM2, SUM (Z.COM3) COM3, SUM (Z.COM4) COM4, SUM (Z.COM5) COM5, SUM (Z.COM6) COM6, SUM (Z.COM7) COM7, SUM (Z.COM6) + SUM (Z.COM7) COM_TOT2, NVL (SUM (Z.COM8), 0) COM8, SUM (Z.COM9) COM9, SUM (Z.COM10) COM10, SUM (Z.COM11) COM11, SUM (Z.COM12) COM12, SUM (Z.COM13) COM13, SUM (Z.COM14) COM14, SUM (Z.COM15) COM15, SUM (Z.COM16) COM16, SUM (Z.COM17) COM17, SUM (Z.COM18) COM18, SUM (Z.COM19) COM19, SUM (Z.COM20) COM20, SUM (Z.COM21) COM21, SUM (Z.COM22) COM22, SUM (Z.COM23) COM23, SUM (Z.COM24) COM24, SUM (Z.COM25) COM25, SUM (Z.COM26) COM26, SUM (Z.COM27) COM27, SUM (Z.COM28) COM28, SUM (Z.COM29) COM29, SUM (Z.COM30) COM30, SUM (Z.COM31) COM31, SUM (Z.ATAX1) ATAX1, SUM (Z.ATAX2) ATAX2, SUM (Z.ATAX3) ATAX3, SUM (Z.ATAX4) ATAX4, SUM (Z.ATAX5) ATAX5, SUM (Z.ATAX6) ATAX6, SUM (Z.ATAX7) ATAX7, SUM (Z.ATAX8) ATAX8, SUM (Z.ATAX9) ATAX9, SUM (Z.ATAX10) ATAX10, SUM (Z.ATAX11) ATAX11, SUM (Z.ATAX12) ATAX12, SUM (Z.ATAX13) ATAX13, SUM (Z.ATAX14) ATAX14, SUM (Z.ATAX15) ATAX15, SUM (Z.ATAX16) ATAX16, SUM (Z.ATAX17) ATAX17, SUM (Z.ATAX18) ATAX18, SUM (Z.ATAX19) ATAX19, SUM (Z.ATAX20) ATAX20, SUM (Z.ATAX21) ATAX21, SUM (Z.ATAX22) ATAX22, SUM (Z.ATAX23) ATAX23, SUM (Z.ATAX24) ATAX24, SUM (Z.ATAX25) ATAX25, SUM (Z.ATAX26) ATAX26, SUM (Z.ATAX27) ATAX27, SUM (Z.ATAX28) ATAX28, SUM (Z.ATAX29) ATAX29, SUM (Z.ATAX30) ATAX30, SUM (Z.ATAX31) ATAX31, MAX (Z.RATE1) RATE1, MAX (Z.RATE2) RATE2, MAX (Z.RATE3) RATE3, MAX (Z.RATE4) RATE4, MAX (Z.RATE5) RATE5, MAX (Z.RATE6) RATE6, MAX (Z.RATE7) RATE7, MAX (Z.RATE8) RATE8, MAX (Z.RATE9) RATE9, MAX (Z.RATE10) RATE10, MAX (Z.RATE11) RATE11, MAX (Z.RATE12) RATE12, MAX (Z.RATE13) RATE13, MAX (Z.RATE14) RATE14, MAX (Z.RATE15) RATE15, MAX (Z.RATE16) RATE16, MAX (Z.RATE17) RATE17, MAX (Z.RATE18) RATE18, MAX (Z.RATE19) RATE19, MAX (Z.RATE20) RATE20, MAX (Z.RATE21) RATE21, MAX (Z.RATE22) RATE22, MAX (Z.RATE23) RATE23, MAX (Z.RATE24) RATE24, MAX (Z.RATE25) RATE25, MAX (Z.RATE26) RATE26, MAX (Z.RATE27) RATE27, MAX (Z.RATE28) RATE28, MAX (Z.RATE29) RATE29, MAX (Z.RATE30) RATE30, MAX (Z.RATE31) RATE31, MAX (D.DISTR_TOT) AS DISTR_TOT, MAX (E.DISTR_STOT) AS DISTR_STOT  ";
        query +=" FROM (SELECT A.MB_CD, C.HANMB_NM, A.SUPP_YRMN, NVL(SUM(DECODE(B.AVECLASS_CD,'AA',A.ORGDISTR_AMT, 0)),0) DISTR_AMT1, NVL(SUM(DECODE(B.AVECLASS_CD,'AB',A.ORGDISTR_AMT, 0)),0) DISTR_AMT2, NVL(SUM(DECODE(B.AVECLASS_CD,'AC',A.ORGDISTR_AMT, 0)),0) DISTR_AMT3, NVL(SUM(DECODE(B.AVECLASS_CD,'AD',A.ORGDISTR_AMT, 0)),0) DISTR_AMT4, NVL(SUM(DECODE(B.AVECLASS_CD,'AE',A.ORGDISTR_AMT, 0)),0) DISTR_AMT5, NVL(SUM(DECODE(B.AVECLASS_CD,'BA',A.ORGDISTR_AMT, 0)),0) DISTR_AMT6, NVL(SUM(DECODE(B.AVECLASS_CD,'BB',A.ORGDISTR_AMT, 0)),0) DISTR_AMT7, NVL(SUM(DECODE(B.AVECLASS_CD,'BC',A.ORGDISTR_AMT, 0)),0) DISTR_AMT8, NVL(SUM(DECODE(B.AVECLASS_CD,'CA',A.ORGDISTR_AMT, 0)),0) DISTR_AMT9, NVL(SUM(DECODE(B.AVECLASS_CD,'CB',A.ORGDISTR_AMT, 0)),0) DISTR_AMT10, NVL(SUM(DECODE(B.AVECLASS_CD,'CC',A.ORGDISTR_AMT, 0)),0) DISTR_AMT11, NVL(SUM(DECODE(B.AVECLASS_CD,'CD',A.ORGDISTR_AMT, 0)),0) DISTR_AMT12, NVL(SUM(DECODE(B.AVECLASS_CD,'CE',A.ORGDISTR_AMT, 0)),0) DISTR_AMT13, NVL(SUM(DECODE(B.AVECLASS_CD,'CF',A.ORGDISTR_AMT, 0)),0) DISTR_AMT14, NVL(SUM(DECODE(B.AVECLASS_CD,'CG',A.ORGDISTR_AMT, 0)),0) DISTR_AMT15, NVL(SUM(DECODE(B.AVECLASS_CD,'CH',A.ORGDISTR_AMT, 0)),0) DISTR_AMT16, NVL(SUM(DECODE(B.AVECLASS_CD,'DA',A.ORGDISTR_AMT, 0)),0) DISTR_AMT17, NVL(SUM(DECODE(B.AVECLASS_CD,'DB',A.ORGDISTR_AMT, 0)),0) DISTR_AMT18, NVL(SUM(DECODE(B.AVECLASS_CD,'DC',A.ORGDISTR_AMT, 0)),0) DISTR_AMT19, NVL(SUM(DECODE(B.AVECLASS_CD,'DD',A.ORGDISTR_AMT, 0)),0) DISTR_AMT20, NVL(SUM(DECODE(B.AVECLASS_CD,'DE',A.ORGDISTR_AMT, 0)),0) DISTR_AMT21, NVL(SUM(DECODE(B.AVECLASS_CD,'DF',A.ORGDISTR_AMT, 0)),0) DISTR_AMT22, NVL(SUM(DECODE(B.AVECLASS_CD,'DG',A.ORGDISTR_AMT, 0)),0) DISTR_AMT23, NVL(SUM(DECODE(B.AVECLASS_CD,'DH',A.ORGDISTR_AMT, 0)),0) DISTR_AMT24, NVL(SUM(DECODE(B.AVECLASS_CD,'DI',A.ORGDISTR_AMT, 0)),0) DISTR_AMT25, NVL(SUM(DECODE(B.AVECLASS_CD,'DJ',A.ORGDISTR_AMT, 0)),0) DISTR_AMT26, NVL(SUM(DECODE(B.AVECLASS_CD,'ZA',A.ORGDISTR_AMT, 0)),0) DISTR_AMT27, NVL(SUM(DECODE(B.AVECLASS_CD,'ZB',A.ORGDISTR_AMT, 0)),0) DISTR_AMT28, NVL(SUM(DECODE(B.AVECLASS_CD,'ZC',A.ORGDISTR_AMT, 0)),0) DISTR_AMT29, NVL(SUM(DECODE(B.AVECLASS_CD,'ZZ',A.ORGDISTR_AMT, 0)),0) DISTR_AMT30, NVL(SUM(DECODE(B.AVECLASS_CD,'DK',A.ORGDISTR_AMT, 0)),0) DISTR_AMT31, NVL(SUM(DECODE(B.AVECLASS_CD,'AA',A.MNGCOMIS_AMT, 0)),0) COM1, NVL(SUM(DECODE(B.AVECLASS_CD,'AB',A.MNGCOMIS_AMT, 0)),0) COM2, NVL(SUM(DECODE(B.AVECLASS_CD,'AC',A.MNGCOMIS_AMT, 0)),0) COM3, NVL(SUM(DECODE(B.AVECLASS_CD,'AD',A.MNGCOMIS_AMT, 0)),0) COM4, NVL(SUM(DECODE(B.AVECLASS_CD,'AE',A.MNGCOMIS_AMT, 0)),0) COM5, NVL(SUM(DECODE(B.AVECLASS_CD,'BA',A.MNGCOMIS_AMT, 0)),0) COM6, NVL(SUM(DECODE(B.AVECLASS_CD,'BB',A.MNGCOMIS_AMT, 0)),0) COM7, NVL(SUM(DECODE(B.AVECLASS_CD,'BC',A.MNGCOMIS_AMT, 0)),0) COM8, NVL(SUM(DECODE(B.AVECLASS_CD,'CA',A.MNGCOMIS_AMT, 0)),0) COM9, NVL(SUM(DECODE(B.AVECLASS_CD,'CB',A.MNGCOMIS_AMT, 0)),0) COM10, NVL(SUM(DECODE(B.AVECLASS_CD,'CC',A.MNGCOMIS_AMT, 0)),0) COM11, NVL(SUM(DECODE(B.AVECLASS_CD,'CD',A.MNGCOMIS_AMT, 0)),0) COM12, NVL(SUM(DECODE(B.AVECLASS_CD,'CE',A.MNGCOMIS_AMT, 0)),0) COM13, NVL(SUM(DECODE(B.AVECLASS_CD,'CF',A.MNGCOMIS_AMT, 0)),0) COM14, NVL(SUM(DECODE(B.AVECLASS_CD,'CG',A.MNGCOMIS_AMT, 0)),0) COM15, NVL(SUM(DECODE(B.AVECLASS_CD,'CH',A.MNGCOMIS_AMT, 0)),0) COM16, NVL(SUM(DECODE(B.AVECLASS_CD,'DA',A.MNGCOMIS_AMT, 0)),0) COM17, NVL(SUM(DECODE(B.AVECLASS_CD,'DB',A.MNGCOMIS_AMT, 0)),0) COM18, NVL(SUM(DECODE(B.AVECLASS_CD,'DC',A.MNGCOMIS_AMT, 0)),0) COM19, NVL(SUM(DECODE(B.AVECLASS_CD,'DD',A.MNGCOMIS_AMT, 0)),0) COM20, NVL(SUM(DECODE(B.AVECLASS_CD,'DE',A.MNGCOMIS_AMT, 0)),0) COM21, NVL(SUM(DECODE(B.AVECLASS_CD,'DF',A.MNGCOMIS_AMT, 0)),0) COM22, NVL(SUM(DECODE(B.AVECLASS_CD,'DG',A.MNGCOMIS_AMT, 0)),0) COM23, NVL(SUM(DECODE(B.AVECLASS_CD,'DH',A.MNGCOMIS_AMT, 0)),0) COM24, NVL(SUM(DECODE(B.AVECLASS_CD,'DI',A.MNGCOMIS_AMT, 0)),0) COM25, NVL(SUM(DECODE(B.AVECLASS_CD,'DJ',A.MNGCOMIS_AMT, 0)),0) COM26, NVL(SUM(DECODE(B.AVECLASS_CD,'ZA',A.MNGCOMIS_AMT, 0)),0) COM27, NVL(SUM(DECODE(B.AVECLASS_CD,'ZB',A.MNGCOMIS_AMT, 0)),0) COM28, NVL(SUM(DECODE(B.AVECLASS_CD,'ZC',A.MNGCOMIS_AMT, 0)),0) COM29, NVL(SUM(DECODE(B.AVECLASS_CD,'ZZ',A.MNGCOMIS_AMT, 0)),0) COM30, NVL(SUM(DECODE(B.AVECLASS_CD,'DK',A.MNGCOMIS_AMT, 0)),0) COM31, NVL(MAX(DECODE(B.AVECLASS_CD,'AA',D.MNGCOMIS_RATE, 0)),0) RATE1, NVL(MAX(DECODE(B.AVECLASS_CD,'AB',D.MNGCOMIS_RATE, 0)),0) RATE2, NVL(MAX(DECODE(B.AVECLASS_CD,'AC',D.MNGCOMIS_RATE, 0)),0) RATE3, NVL(MAX(DECODE(B.AVECLASS_CD,'AD',D.MNGCOMIS_RATE, 0)),0) RATE4, NVL(MAX(DECODE(B.AVECLASS_CD,'AE',D.MNGCOMIS_RATE, 0)),0) RATE5, NVL(MAX(DECODE(B.AVECLASS_CD,'BA',D.MNGCOMIS_RATE, 0)),0) RATE6, NVL(MAX(DECODE(B.AVECLASS_CD,'BB',D.MNGCOMIS_RATE, 0)),0) RATE7, NVL(MAX(DECODE(B.AVECLASS_CD,'BC',D.MNGCOMIS_RATE, 0)),0) RATE8, NVL(MAX(DECODE(B.AVECLASS_CD,'CA',D.MNGCOMIS_RATE, 0)),0) RATE9, NVL(MAX(DECODE(B.AVECLASS_CD,'CB',D.MNGCOMIS_RATE, 0)),0) RATE10, NVL(MAX(DECODE(B.AVECLASS_CD,'CC',D.MNGCOMIS_RATE, 0)),0) RATE11, NVL(MAX(DECODE(B.AVECLASS_CD,'CD',D.MNGCOMIS_RATE, 0)),0) RATE12, NVL(MAX(DECODE(B.AVECLASS_CD,'CE',D.MNGCOMIS_RATE, 0)),0) RATE13, NVL(MAX(DECODE(B.AVECLASS_CD,'CF',D.MNGCOMIS_RATE, 0)),0) RATE14, NVL(MAX(DECODE(B.AVECLASS_CD,'CG',D.MNGCOMIS_RATE, 0)),0) RATE15, NVL(MAX(DECODE(B.AVECLASS_CD,'CH',D.MNGCOMIS_RATE, 0)),0) RATE16, NVL(MAX(DECODE(B.AVECLASS_CD,'DA',D.MNGCOMIS_RATE, 0)),0) RATE17, NVL(MAX(DECODE(B.AVECLASS_CD,'DB',D.MNGCOMIS_RATE, 0)),0) RATE18, NVL(MAX(DECODE(B.AVECLASS_CD,'DC',D.MNGCOMIS_RATE, 0)),0) RATE19, NVL(MAX(DECODE(B.AVECLASS_CD,'DD',D.MNGCOMIS_RATE, 0)),0) RATE20, NVL(MAX(DECODE(B.AVECLASS_CD,'DE',D.MNGCOMIS_RATE, 0)),0) RATE21, NVL(MAX(DECODE(B.AVECLASS_CD,'DF',D.MNGCOMIS_RATE, 0)),0) RATE22, NVL(MAX(DECODE(B.AVECLASS_CD,'DG',D.MNGCOMIS_RATE, 0)),0) RATE23, NVL(MAX(DECODE(B.AVECLASS_CD,'DH',D.MNGCOMIS_RATE, 0)),0) RATE24, NVL(MAX(DECODE(B.AVECLASS_CD,'DI',D.MNGCOMIS_RATE, 0)),0) RATE25, NVL(MAX(DECODE(B.AVECLASS_CD,'DJ',D.MNGCOMIS_RATE, 0)),0) RATE26, NVL(MAX(DECODE(B.AVECLASS_CD,'ZA',D.MNGCOMIS_RATE, 0)),0) RATE27, NVL(MAX(DECODE(B.AVECLASS_CD,'ZB',D.MNGCOMIS_RATE, 0)),0) RATE28, NVL(MAX(DECODE(B.AVECLASS_CD,'ZC',D.MNGCOMIS_RATE, 0)),0) RATE29, NVL(MAX(DECODE(B.AVECLASS_CD,'ZZ',D.MNGCOMIS_RATE, 0)),0) RATE30, NVL(MAX(DECODE(B.AVECLASS_CD,'DK',D.MNGCOMIS_RATE, 0)),0) RATE31, NVL(DECODE(B.AVECLASS_CD,'AA',SUM(A.ATAX_AMT), 0),0) ATAX1, NVL(DECODE(B.AVECLASS_CD,'AB',SUM(A.ATAX_AMT), 0),0) ATAX2, NVL(DECODE(B.AVECLASS_CD,'AC',SUM(A.ATAX_AMT), 0),0) ATAX3, NVL(DECODE(B.AVECLASS_CD,'AD',SUM(A.ATAX_AMT), 0),0) ATAX4, NVL(DECODE(B.AVECLASS_CD,'AE',SUM(A.ATAX_AMT), 0),0) ATAX5, NVL(DECODE(B.AVECLASS_CD,'BA',SUM(A.ATAX_AMT), 0),0) ATAX6, NVL(DECODE(B.AVECLASS_CD,'BB',SUM(A.ATAX_AMT), 0),0) ATAX7, NVL(DECODE(B.AVECLASS_CD,'BC',SUM(A.ATAX_AMT), 0),0) ATAX8, NVL(DECODE(B.AVECLASS_CD,'CA',SUM(A.ATAX_AMT), 0),0) ATAX9, NVL(DECODE(B.AVECLASS_CD,'CB',SUM(A.ATAX_AMT), 0),0) ATAX10, NVL(DECODE(B.AVECLASS_CD,'CC',NVL(SUM (A.ATAX_AMT),0)+NVL(TRUNC(SUM(E.DISTR_AMT)/11),0),0),0) ATAX11, NVL(DECODE(B.AVECLASS_CD,'CD',NVL(SUM (A.ATAX_AMT),0)+NVL(TRUNC(SUM(E.DISTR_AMT)/11),0),0),0) ATAX12, NVL(DECODE(B.AVECLASS_CD,'CE',SUM(A.ATAX_AMT), 0),0) ATAX13, NVL(DECODE(B.AVECLASS_CD,'CF',SUM(A.ATAX_AMT), 0),0) ATAX14, NVL(DECODE(B.AVECLASS_CD,'CG',SUM(A.ATAX_AMT), 0),0) ATAX15, NVL(DECODE(B.AVECLASS_CD,'CH',SUM(A.ATAX_AMT), 0),0) ATAX16, NVL(DECODE(B.AVECLASS_CD,'DA',NVL(SUM (A.ATAX_AMT),0)+NVL(TRUNC(SUM(E.DISTR_AMT)/11),0),0),0) ATAX17, NVL(DECODE(B.AVECLASS_CD,'DB',SUM(A.ATAX_AMT), 0),0) ATAX18, NVL(DECODE(B.AVECLASS_CD,'DC',SUM(A.ATAX_AMT), 0),0) ATAX19, NVL(DECODE(B.AVECLASS_CD,'DD',SUM(A.ATAX_AMT), 0),0) ATAX20, NVL(DECODE(B.AVECLASS_CD,'DE',SUM(A.ATAX_AMT), 0),0) ATAX21, NVL(DECODE(B.AVECLASS_CD,'DF',SUM(A.ATAX_AMT), 0),0) ATAX22, NVL(DECODE(B.AVECLASS_CD,'DG',SUM(A.ATAX_AMT), 0),0) ATAX23, NVL(DECODE(B.AVECLASS_CD,'DH',SUM(A.ATAX_AMT), 0),0) ATAX24, NVL(DECODE(B.AVECLASS_CD,'DI',SUM(A.ATAX_AMT), 0),0) ATAX25, NVL(DECODE(B.AVECLASS_CD,'DJ',SUM(A.ATAX_AMT), 0),0) ATAX26, NVL(DECODE(B.AVECLASS_CD,'ZA',SUM(A.ATAX_AMT), 0),0) ATAX27, NVL(DECODE(B.AVECLASS_CD,'ZB',SUM(A.ATAX_AMT), 0),0) ATAX28, NVL(DECODE(B.AVECLASS_CD,'ZC',SUM(A.ATAX_AMT), 0),0) ATAX29, NVL(DECODE(B.AVECLASS_CD,'ZZ',SUM(A.ATAX_AMT), 0),0) ATAX30, NVL(DECODE(B.AVECLASS_CD,'DK',SUM(A.ATAX_AMT), 0),0) ATAX31  ";
        query +=" FROM FIDU.TTAC_MDMCLASSSUPPAMT A, FIDU.TENV_AVECLASSCD B, FIDU.TMEM_MB C, FIDU.TENV_MNGCOMIS D, FIDU.TTAC_MDM_ATAX_MINUS E  ";
        query +=" WHERE SUBSTR (A.MDM_CD, 1, 2) = B.AVECLASS_CD  ";
        query +=" AND A.MB_CD = C.MB_CD  ";
        query +=" AND A.MDM_CD = E.MDM_CD(+)  ";
        query +=" AND A.SUPP_YRMN = E.SUPP_YRMN(+)  ";
        query +=" AND A.MB_cD=E.MB_CD(+)  ";
        query +=" AND A.SUPP_YRMN BETWEEN SUBSTR (:FROMDATE, 1, 6)  ";
        query +=" AND SUBSTR (:TODATE, 1, 6)  ";
        query +=" AND A.SUPP_YRMN = D.APPL_YRMN(+)  ";
        query +=" AND A.MDM_CD = D.MDM_CD(+)  ";
        if( !SUPP_YRMN.equals("") )
        {
            query +=" AND A.SUPP_YRMN = :SUPP_YRMN  ";
        }
        if( !MB_CD.equals("") )
        {
            query +=" AND A.MB_CD = :MB_CD  ";
        }
        if( !MB_GBN.equals("") )
        {
            query +=" AND C.MB_GBN1 = :MB_GBN  ";
        }
        query +=" AND A.MB_CD BETWEEN :FRMB_CD  ";
        query +=" AND :TOMB_CD  ";
        query +=" AND A.TRNSF_GBN LIKE :TRNSF_GBN || '%'  ";
        query +=" GROUP BY A.SUPP_YRMN, A.MB_CD, C.HANMB_NM, B.AVECLASS_CD )Z, (SELECT X.MB_CD,X.SUPP_YRMN ,NVL(SUM(X.ORGDISTR_AMT),0) DISTR_TOT  ";
        query +=" FROM FIDU.TTAC_MDMCLASSSUPPAMT X, FIDU.TENV_AVECLASSCD B  ";
        query +=" WHERE SUBSTR(X.MDM_CD,1,2) = B.AVECLASS_CD  ";
        query +=" AND X.SUPP_YRMN BETWEEN SUBSTR(:FROMDATE,1,6)  ";
        query +=" AND SUBSTR(:TODATE,1,6)  ";
        query +=" AND X.MB_CD BETWEEN :FRMB_CD  ";
        query +=" AND :TOMB_CD  ";
        query +=" AND X.TRNSF_GBN LIKE :TRNSF_GBN || '%'  ";
        query +=" GROUP BY X.MB_CD,X.SUPP_YRMN ) D, (  ";
        query +=" SELECT MB_CD,SUPP_YRMN ,NVL(SUM(DISTR_STOT),0) DISTR_STOT  ";
        query +=" FROM (  ";
        query +=" SELECT X.MB_CD,X.SUPP_YRMN ,NVL(SUM(X.TOT_REALSUPP_AMT),0) DISTR_STOT  ";
        query +=" FROM FIDU.TTAC_COPYRTAL X  ";
        query +=" WHERE X.SUPP_YRMN BETWEEN SUBSTR(:FROMDATE,1,6)  ";
        query +=" AND SUBSTR(:TODATE,1,6)  ";
        query +=" AND X.TRNSF_GBN LIKE :TRNSF_GBN || '%'  ";
        query +=" AND X.MB_CD BETWEEN :FRMB_CD  ";
        query +=" AND :TOMB_CD  ";
        query +=" GROUP BY X.MB_CD,X.SUPP_YRMN  ";
        query +=" UNION ALL  ";
        query +=" SELECT X.MB_CD,X.SUPP_YRMN ,NVL(X.TOT_AMT,0) DISTR_STOT  ";
        query +=" FROM FIDU.TTAC_SUPPAMT_ADJ X  ";
        query +=" WHERE X.SUPP_YRMN BETWEEN SUBSTR(:FROMDATE,1,6)  ";
        query +=" AND SUBSTR(:TODATE,1,6)  ";
        query +=" AND X.TRNSF_GBN LIKE :TRNSF_GBN || '%'  ";
        query +=" AND X.MB_CD BETWEEN :FRMB_CD  ";
        query +=" AND :TOMB_CD )  ";
        query +=" GROUP BY MB_CD,SUPP_YRMN) E  ";
        query +=" WHERE Z.MB_CD = D.MB_CD  ";
        query +=" AND Z.MB_CD = E.MB_CD  ";
        query +=" AND Z.SUPP_YRMN = D.SUPP_YRMN  ";
        query +=" AND Z.SUPP_YRMN = E.SUPP_YRMN  ";
        query +=" AND D.DISTR_TOT > 0  ";
        query +=" GROUP BY Z.MB_CD, Z.HANMB_NM, Z.SUPP_YRMN  ";
        query +=" ORDER BY Z.MB_CD, Z.SUPP_YRMN  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        if(!MB_GBN.equals(""))
        {
            sobj.setString("MB_GBN", MB_GBN);               //회원구분
        }
        sobj.setString("FRMB_CD", FRMB_CD);               //FRMB_CD
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("TOMB_CD", TOMB_CD);               //TOMB_CD
        if(!SUPP_YRMN.equals(""))
        {
            sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        }
        return sobj;
    }
    // 공제
    public DOBJ CALLsearchMst_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   MB_GBN = dobj.getRetObject("S").getRecord().get("MB_GBN");   //회원구분
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //FRMB_CD
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //TOMB_CD
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT SUPP_YRMN, MB_CD, HANMB_NM, SUM(X.DISTR_AMT28) AS DISTR_AMT28, SUM(X.DISTR_AMT29) AS DISTR_AMT29, SUM(X.DISTR_AMT30) AS DISTR_AMT30, SUM(X.DISTR_AMT31) AS DISTR_AMT31, SUM(X.DISTR_AMT32) AS DISTR_AMT32, SUM(X.DISTR_AMT33) AS DISTR_AMT33, SUM(X.DISTR_AMT34) AS DISTR_AMT34, SUM(X.DISTR_AMT28) + SUM(X.DISTR_AMT29) + SUM(X.DISTR_AMT30) + SUM(X.DISTR_AMT31) + SUM(X.DISTR_AMT32) + SUM(X.DISTR_AMT33) + SUM(X.DISTR_AMT45) + SUM(X.DISTR_AMT34) SUB_TOT6, SUM(X.DISTR_AMT35) AS DISTR_AMT35, SUM(X.DISTR_AMT36) AS DISTR_AMT36, SUM(X.DISTR_AMT37) AS DISTR_AMT37, SUM(X.DISTR_AMT38) AS DISTR_AMT38, SUM(X.DISTR_AMT39) AS DISTR_AMT39, SUM(X.DISTR_AMT40) AS DISTR_AMT40, SUM(X.DISTR_AMT41) AS DISTR_AMT41, SUM(X.DISTR_AMT42) AS DISTR_AMT42, SUM(X.DISTR_AMT43) AS DISTR_AMT43, SUM(X.DISTR_AMT44) AS DISTR_AMT44, SUM(X.DISTR_AMT46) AS DISTR_AMT46, SUM(X.DISTR_AMT47) AS DISTR_AMT47, SUM(X.DISTR_AMT48) AS DISTR_AMT48, SUM(X.DISTR_AMT35) + SUM(X.DISTR_AMT36) + SUM(X.DISTR_AMT37) + SUM(X.DISTR_AMT38) + SUM(X.DISTR_AMT39) + SUM(X.DISTR_AMT40) + SUM(X.DISTR_AMT41) + SUM(X.DISTR_AMT42) + SUM(X.DISTR_AMT43) + SUM(X.DISTR_AMT46) + SUM(X.DISTR_AMT44) + SUM(X.DISTR_AMT47) SUB_TOT7, SUM(X.DISTR_AMT45) AS DISTR_AMT45, MAX(X.DISTR_GTOT) AS DISTR_GTOT  ";
        query +=" FROM (  ";
        query +=" SELECT D.MB_CD, E.HANMB_NM, D.SUPP_YRMN, NVL(DECODE(D.DEDCT_GBNONE, '01', DECODE(D.DEDCT_GBNTWO, '01', DEDCT_AMT)),0) DISTR_AMT28, NVL(DECODE(D.DEDCT_GBNONE, '01', DECODE(D.DEDCT_GBNTWO, '02', DEDCT_AMT)),0) DISTR_AMT29, NVL(DECODE(D.DEDCT_GBNONE, '01', DECODE(D.DEDCT_GBNTWO, '03', DEDCT_AMT)),0) DISTR_AMT30, NVL(DECODE(D.DEDCT_GBNONE, '01', DECODE(D.DEDCT_GBNTWO, '04', DEDCT_AMT)),0) DISTR_AMT31, NVL(DECODE(D.DEDCT_GBNONE, '01', DECODE(D.DEDCT_GBNTWO, '05', DEDCT_AMT)),0) DISTR_AMT32, NVL(DECODE(D.DEDCT_GBNONE, '01', DECODE(D.DEDCT_GBNTWO, '06', DEDCT_AMT)),0) DISTR_AMT33, NVL(DECODE(D.DEDCT_GBNONE, '01', DECODE(D.DEDCT_GBNTWO, '07', DEDCT_AMT)),0) DISTR_AMT34, NVL(DECODE(D.DEDCT_GBNONE, '02', DECODE(D.DEDCT_GBNTWO, '01', DEDCT_AMT)),0) DISTR_AMT35, NVL(DECODE(D.DEDCT_GBNONE, '02', DECODE(D.DEDCT_GBNTWO, '02', DEDCT_AMT)),0) DISTR_AMT36, NVL(DECODE(D.DEDCT_GBNONE, '02', DECODE(D.DEDCT_GBNTWO, '03', DEDCT_AMT)),0) DISTR_AMT37, NVL(DECODE(D.DEDCT_GBNONE, '02', DECODE(D.DEDCT_GBNTWO, '04', DEDCT_AMT)),0) DISTR_AMT38, NVL(DECODE(D.DEDCT_GBNONE, '02', DECODE(D.DEDCT_GBNTWO, '05', DEDCT_AMT)),0) DISTR_AMT39, NVL(DECODE(D.DEDCT_GBNONE, '02', DECODE(D.DEDCT_GBNTWO, '06', DEDCT_AMT)),0) DISTR_AMT40, NVL(DECODE(D.DEDCT_GBNONE, '02', DECODE(D.DEDCT_GBNTWO, '07', DEDCT_AMT)),0) DISTR_AMT41, NVL(DECODE(D.DEDCT_GBNONE, '02', DECODE(D.DEDCT_GBNTWO, '08', DEDCT_AMT)),0) DISTR_AMT42, NVL(DECODE(D.DEDCT_GBNONE, '02', DECODE(D.DEDCT_GBNTWO, '09', DEDCT_AMT)),0) DISTR_AMT43, NVL(DECODE(D.DEDCT_GBNONE, '02', DECODE(D.DEDCT_GBNTWO, '10', DEDCT_AMT)),0) DISTR_AMT44, NVL(DECODE(D.DEDCT_GBNONE, '02', DECODE(D.DEDCT_GBNTWO, '11', DEDCT_AMT)),0) DISTR_AMT46, NVL(DECODE(D.DEDCT_GBNONE, '02', DECODE(D.DEDCT_GBNTWO, '12', DEDCT_AMT)),0) DISTR_AMT47, NVL(DECODE(D.DEDCT_GBNONE, '02', DECODE(D.DEDCT_GBNTWO, '13', DEDCT_AMT)),0) DISTR_AMT48, NVL(DECODE(D.DEDCT_GBNONE, '01', DECODE(D.DEDCT_GBNTWO, '08', DEDCT_AMT)),0) DISTR_AMT45, (SELECT NVL(SUM(X.DEDCT_AMT),0) DEDCT_AMT  ";
        query +=" FROM FIDU.TTAC_DEDCTAMT X, FIDU.TMEM_MB Z  ";
        query +=" WHERE X.MB_CD = Z.MB_CD  ";
        query +=" AND X.MB_CD = D.MB_CD  ";
        query +=" AND X.SUPP_YRMN = D.SUPP_YRMN  ";
        query +=" AND X.TRNSF_GBN LIKE :TRNSF_GBN || '%' ) AS DISTR_GTOT  ";
        query +=" FROM FIDU.TTAC_DEDCTAMT D, FIDU.TMEM_MB E  ";
        query +=" WHERE D.MB_CD = E.MB_CD  ";
        query +=" AND D.TRNSF_GBN LIKE :TRNSF_GBN || '%'  ";
        query +=" AND D.SUPP_YRMN BETWEEN SUBSTR(:FROMDATE,1,6)  ";
        query +=" AND SUBSTR(:TODATE,1,6)  ";
        query +=" AND D.MB_CD BETWEEN :FRMB_CD  ";
        query +=" AND :TOMB_CD  ";
        if( !SUPP_YRMN.equals("") )
        {
            query +=" AND D.SUPP_YRMN = :SUPP_YRMN  ";
        }
        if( !MB_CD.equals("") )
        {
            query +=" AND D.MB_CD = :MB_CD  ";
        }
        if( !MB_GBN.equals("") )
        {
            query +=" AND E.MB_GBN1 = :MB_GBN  ";
        }
        query +=" )X  ";
        query +=" GROUP BY X.MB_CD, X.HANMB_NM, X.SUPP_YRMN  ";
        query +=" ORDER BY X.MB_CD, X.SUPP_YRMN  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        if(!MB_GBN.equals(""))
        {
            sobj.setString("MB_GBN", MB_GBN);               //회원구분
        }
        sobj.setString("FRMB_CD", FRMB_CD);               //FRMB_CD
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("TOMB_CD", TOMB_CD);               //TOMB_CD
        if(!SUPP_YRMN.equals(""))
        {
            sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        }
        return sobj;
    }
    // 출판사계산서금액
    public DOBJ CALLsearchMst_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //회원코드
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //회원코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT MB_CD, SUPP_YRMN, SUM(ST_GONG_AMT) ST_GONG_AMT, SUM(ST_ATAX_AMT) ST_ATAX_AMT, SUM(P_GONG_AMT) P_GONG_AMT, SUM(P_ATAX_AMT) P_ATAX_AMT  ";
        query +=" FROM (  ";
        query +=" SELECT MB_CD, SUPP_YRMN, ST_GONG_AMT, ST_ATAX_AMT, P_GONG_AMT - ST_GONG_AMT + ST_ATAX_AMT P_GONG_AMT, P_ATAX_AMT - ST_ATAX_AMT P_ATAX_AMT  ";
        query +=" FROM (  ";
        query +=" SELECT A.MB_CD, A.SUPP_YRMN, SUM(CASE WHEN MB_GBN1 = 'F'  ";
        query +=" AND FIDU.GET_FOR_ATAX_YN(A.MDM_CD) <> '1' THEN ORGDISTR_AMT - NVL(C.DISTR_AMT,0) ELSE 0 END) ST_GONG_AMT, SUM(CASE WHEN MB_GBN1 = 'F'  ";
        query +=" AND FIDU.GET_FOR_ATAX_YN(A.MDM_CD) <> '1' THEN ATAX_AMT ELSE 0 END) ST_ATAX_AMT, SUM(CASE WHEN A.MB_CD <> '10012619'  ";
        query +=" AND (MB_GBN1 = 'F'  ";
        query +=" OR MB_GBN1 = 'P'  ";
        query +=" OR MB_GBN1 = 'B'  ";
        query +=" OR MB_GBN1 = 'C') THEN ORGDISTR_AMT - NVL(C.DISTR_AMT,0) - ATAX_AMT ELSE 0 END) P_GONG_AMT, SUM(CASE WHEN (MB_GBN1 = 'F'  ";
        query +=" OR MB_GBN1 = 'P'  ";
        query +=" OR MB_GBN1 = 'B'  ";
        query +=" OR MB_GBN1 = 'C') THEN ATAX_AMT ELSE 0 END) P_ATAX_AMT , SUM(NVL(C.DISTR_AMT,0)) DISTR_AMT  ";
        query +=" FROM FIDU.TTAC_MDMCLASSSUPPAMT A, FIDU.TMEM_MB B,FIDU.TTAC_MDM_ATAX_MINUS C  ";
        query +=" WHERE A.MB_CD = B.MB_CD  ";
        query +=" AND A.SUPP_YRMN=C.SUPP_YRMN(+)  ";
        query +=" AND A.MDM_CD=C.MDM_CD(+)  ";
        query +=" AND A.MB_CD=C.MB_CD(+)  ";
        query +=" AND A.SUPP_YRMN BETWEEN SUBSTR(:FROMDATE,1,6)  ";
        query +=" AND SUBSTR(:TODATE,1,6)  ";
        query +=" AND A.MB_CD BETWEEN :FRMB_CD  ";
        query +=" AND :TOMB_CD  ";
        if( !SUPP_YRMN.equals("") )
        {
            query +=" AND A.SUPP_YRMN = :SUPP_YRMN  ";
        }
        if( !MB_CD.equals("") )
        {
            query +=" AND A.MB_CD = :MB_CD  ";
        }
        query +=" AND A.SUPP_YRMN BETWEEN SUBSTR(:FROMDATE,1,6)  ";
        query +=" AND SUBSTR(:TODATE,1,6)  ";
        query +=" AND A.TRNSF_GBN LIKE :TRNSF_GBN||'%'  ";
        query +=" GROUP BY A.MB_CD, A.SUPP_YRMN )  ";
        query +=" UNION ALL  ";
        query +=" SELECT MB_CD, SUPP_YRMN, SUM (ST_GONG_AMT) ST_GONG_AMT, SUM (ST_ATAX_AMT) ST_ATAX_AMT, SUM (P_GONG_AMT) P_GONG_AMT, SUM (P_ATAX_AMT) P_ATAX_AMT  ";
        query +=" FROM FIDU.TTAC_SUPPAMT_ADJ A  ";
        query +=" WHERE A.SUPP_YRMN BETWEEN SUBSTR (:FROMDATE, 1, 6)  ";
        query +=" AND SUBSTR (:TODATE, 1, 6)  ";
        query +=" AND A.MB_CD BETWEEN :FRMB_CD  ";
        query +=" AND :TOMB_CD  ";
        if( !SUPP_YRMN.equals("") )
        {
            query +=" AND A.SUPP_YRMN = :SUPP_YRMN  ";
        }
        if( !MB_CD.equals("") )
        {
            query +=" AND A.MB_CD = :MB_CD  ";
        }
        query +=" AND A.TRNSF_GBN LIKE :TRNSF_GBN||'%'  ";
        query +=" AND A.SUPP_YRMN BETWEEN SUBSTR(:FROMDATE,1,6)  ";
        query +=" AND SUBSTR(:TODATE,1,6)  ";
        query +=" GROUP BY A.MB_CD, A.SUPP_YRMN )  ";
        query +=" GROUP BY MB_CD, SUPP_YRMN  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        sobj.setString("FRMB_CD", FRMB_CD);               //회원코드
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("TOMB_CD", TOMB_CD);               //회원코드
        if(!SUPP_YRMN.equals(""))
        {
            sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        }
        return sobj;
    }
    // 재분배금조회
    public DOBJ CALLsearchMst_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   MB_GBN = dobj.getRetObject("S").getRecord().get("MB_GBN");   //회원구분
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //FRMB_CD
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //TOMB_CD
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT REDEM_YRMN,REDEM_MB_CD,REDEM_AMT  ";
        query +=" FROM FIDU.TTAC_REDEMREDISTR_UPLOAD A, FIDU.TMEM_MB B  ";
        query +=" WHERE REDEM_YRMN BETWEEN SUBSTR(:FROMDATE,1,6)  ";
        query +=" AND SUBSTR(:TODATE,1,6)  ";
        query +=" AND REDEM_MB_CD BETWEEN :FRMB_CD  ";
        query +=" AND :TOMB_CD  ";
        query +=" AND TRNSF_GBN LIKE :TRNSF_GBN || '%'  ";
        query +=" AND B.MB_CD = A.REDEM_MB_CD  ";
        if( !SUPP_YRMN.equals("") )
        {
            query +=" AND REDEM_YRMN = :SUPP_YRMN  ";
        }
        if( !MB_GBN.equals("") )
        {
            query +=" AND MB_GBN1 = :MB_GBN  ";
        }
        if( !MB_CD.equals("") )
        {
            query +=" AND REDEM_MB_CD = :MB_CD  ";
        }
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        if(!MB_GBN.equals(""))
        {
            sobj.setString("MB_GBN", MB_GBN);               //회원구분
        }
        sobj.setString("FRMB_CD", FRMB_CD);               //FRMB_CD
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("TOMB_CD", TOMB_CD);               //TOMB_CD
        if(!SUPP_YRMN.equals(""))
        {
            sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        }
        return sobj;
    }
    //##**$$searchMst
    //##**$$searchMst_test
    /*
    */
    public DOBJ CTLsearchMst_test(DOBJ dobj)
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
            dobj  = CALLsearchMst_test_SEL5(Conn, dobj);           //  외국입금사용료 분배내역서
            if( 1 == 2)
            {
                dobj  = CALLsearchMst_test_SEL1(Conn, dobj);           //  지급
                dobj  = CALLsearchMst_test_SEL2(Conn, dobj);           //  공제
                dobj  = CALLsearchMst_test_SEL3(Conn, dobj);           //  출판사계산서금액
                dobj  = CALLsearchMst_test_SEL4(Conn, dobj);           //  재분배금조회
            }
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
    public DOBJ CTLsearchMst_test( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchMst_test_SEL5(Conn, dobj);           //  외국입금사용료 분배내역서
        if( 1 == 2)
        {
            dobj  = CALLsearchMst_test_SEL1(Conn, dobj);           //  지급
            dobj  = CALLsearchMst_test_SEL2(Conn, dobj);           //  공제
            dobj  = CALLsearchMst_test_SEL3(Conn, dobj);           //  출판사계산서금액
            dobj  = CALLsearchMst_test_SEL4(Conn, dobj);           //  재분배금조회
        }
        return dobj;
    }
    // 외국입금사용료 분배내역서
    public DOBJ CALLsearchMst_test_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_test_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_test_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //회원코드
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.RIGHTPRES_MB_CD  ,  A.WRK_YRMN  ,  A.PROD_CD  ,  B.PROD_TTL  ,  SUM(DECODE(SVC_CD,'ZA010103',DISTR_AMT,0))  AS  E4  ,  SUM(DECODE(SVC_CD,'ZA010101',DISTR_AMT,0))  AS  F2  ,  SUM(DECODE(SVC_CD,'ZA010102',DISTR_AMT,0))  AS  M3  FROM  FIDU.TDIS_DISTR  A,  FIDU.TOPU_PROD  B  WHERE  A.WRK_YRMN  between  :FROMDATE   \n";
        query +=" AND  :TODATE   \n";
        query +=" AND  MDM_CD  =  'ZA0101'   \n";
        query +=" AND  A.PROD_CD  =  B.PROD_CD   \n";
        query +=" AND  A.RIGHTPRES_MB_CD  between  :FRMB_CD   \n";
        query +=" AND  :TOMB_CD  GROUP  BY  BSCON_CD  ,  RIGHTPRES_MB_CD  ,  A.PROD_CD  ,  B.PROD_TTL  ,  A.WRK_YRMN  ORDER  BY  BSCON_CD  ASC ";
        sobj.setSql(query);
        sobj.setString("FRMB_CD", FRMB_CD);               //회원코드
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("TOMB_CD", TOMB_CD);               //회원코드
        return sobj;
    }
    // 지급
    public DOBJ CALLsearchMst_test_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_test_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_test_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //FRMB_CD
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //TOMB_CD
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  Z.SUPP_YRMN,  Z.MB_CD,  Z.HANMB_NM,  SUM(Z.DISTR_AMT1)  AS  DISTR_AMT1,  SUM(Z.DISTR_AMT2)  AS  DISTR_AMT2,  SUM(Z.DISTR_AMT3)  AS  DISTR_AMT3,  SUM(Z.DISTR_AMT4)  AS  DISTR_AMT4,  SUM(Z.DISTR_AMT5)  AS  DISTR_AMT5,  SUM(Z.DISTR_AMT6)  AS  DISTR_AMT6,  SUM(Z.DISTR_AMT7)  AS  DISTR_AMT7,  SUM(Z.DISTR_AMT8)  AS  DISTR_AMT8,  SUM(Z.DISTR_AMT9)  AS  DISTR_AMT9,  SUM(Z.DISTR_AMT10)  AS  DISTR_AMT10,  SUM(Z.DISTR_AMT11)  AS  DISTR_AMT11,  SUM(Z.DISTR_AMT12)  AS  DISTR_AMT12,  SUM(Z.DISTR_AMT13)  AS  DISTR_AMT13,  SUM(Z.DISTR_AMT14)  AS  DISTR_AMT14,  SUM(Z.DISTR_AMT15)  AS  DISTR_AMT15,  SUM(Z.DISTR_AMT16)  AS  DISTR_AMT16,  SUM(Z.DISTR_AMT17)  AS  DISTR_AMT17,  SUM(Z.DISTR_AMT18)  AS  DISTR_AMT18,  SUM(Z.DISTR_AMT19)  AS  DISTR_AMT19,  SUM(Z.DISTR_AMT20)  AS  DISTR_AMT20,  SUM(Z.DISTR_AMT21)  AS  DISTR_AMT21,  SUM(Z.DISTR_AMT22)  AS  DISTR_AMT22,  SUM(Z.DISTR_AMT23)  AS  DISTR_AMT23,  SUM(Z.DISTR_AMT24)  AS  DISTR_AMT24,  SUM(Z.DISTR_AMT25)  AS  DISTR_AMT25,  SUM(Z.DISTR_AMT26)  AS  DISTR_AMT26,  SUM(Z.DISTR_AMT27)  AS  DISTR_AMT27,  MAX(D.DISTR_TOT)  AS  DISTR_TOT,  MAX(E.DISTR_STOT)  AS  DISTR_STOT  FROM  (   \n";
        query +=" SELECT  A.MB_CD,  C.HANMB_NM,  A.SUPP_YRMN,  NVL(DECODE(B.PRNT_SEQ,  '1',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT1,  NVL(DECODE(B.PRNT_SEQ,  '2',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT2,  NVL(DECODE(B.PRNT_SEQ,  '3',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT3,  NVL(DECODE(B.PRNT_SEQ,  '4',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT4,  NVL(DECODE(B.PRNT_SEQ,  '5',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT5,  NVL(DECODE(B.PRNT_SEQ,  '6',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT6,  NVL(DECODE(B.PRNT_SEQ,  '7',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT7,  NVL(DECODE(B.PRNT_SEQ,  '8',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT8,  NVL(DECODE(B.PRNT_SEQ,  '9',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT9,  NVL(DECODE(B.PRNT_SEQ,  '10',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT10,  NVL(DECODE(B.PRNT_SEQ,  '11',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT11,  NVL(DECODE(B.PRNT_SEQ,  '12',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT12,  NVL(DECODE(B.PRNT_SEQ,  '13',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT13,  NVL(DECODE(B.PRNT_SEQ,  '14',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT14,  NVL(DECODE(B.PRNT_SEQ,  '15',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT15,  NVL(DECODE(B.PRNT_SEQ,  '16',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT16,  NVL(DECODE(B.PRNT_SEQ,  '17',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT17,  NVL(DECODE(B.PRNT_SEQ,  '18',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT18,  NVL(DECODE(B.PRNT_SEQ,  '19',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT19,  NVL(DECODE(B.PRNT_SEQ,  '20',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT20,  NVL(DECODE(B.PRNT_SEQ,  '21',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT21,  NVL(DECODE(B.PRNT_SEQ,  '22',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT22,  NVL(DECODE(B.PRNT_SEQ,  '23',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT23,  NVL(DECODE(B.PRNT_SEQ,  '24',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT24,  NVL(DECODE(B.PRNT_SEQ,  '25',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT25,  NVL(DECODE(B.PRNT_SEQ,  '26',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT26,  NVL(DECODE(B.PRNT_SEQ,  '27',  SUM(A.ORGDISTR_AMT)),0)  DISTR_AMT27  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B,  FIDU.TMEM_MB  C  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  A.MB_CD  =  C.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  BETWEEN  :FRMB_CD   \n";
        query +=" AND  :TOMB_CD   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'  GROUP  BY  A.SUPP_YRMN,  A.MB_CD,  C.HANMB_NM,  B.PRNT_SEQ  )Z,   \n";
        query +=" (SELECT  X.MB_CD,X.SUPP_YRMN  ,NVL(SUM(X.ORGDISTR_AMT),0)  DISTR_TOT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  X,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(X.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  X.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  X.MB_CD  BETWEEN  :FRMB_CD   \n";
        query +=" AND  :TOMB_CD   \n";
        query +=" AND  X.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'  GROUP  BY  X.MB_CD,X.SUPP_YRMN  )  D,   \n";
        query +=" (SELECT  X.MB_CD,X.SUPP_YRMN  ,NVL(SUM(X.TOT_REALSUPP_AMT),0)  DISTR_STOT  FROM  FIDU.TTAC_COPYRTAL  X  WHERE  X.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  X.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'   \n";
        query +=" AND  X.MB_CD  BETWEEN  :FRMB_CD   \n";
        query +=" AND  :TOMB_CD  GROUP  BY  X.MB_CD,X.SUPP_YRMN  )  E  WHERE  Z.MB_CD  =  D.MB_CD   \n";
        query +=" AND  Z.MB_CD  =  E.MB_CD   \n";
        query +=" AND  Z.SUPP_YRMN  =  D.SUPP_YRMN   \n";
        query +=" AND  Z.SUPP_YRMN  =  E.SUPP_YRMN  GROUP  BY  Z.MB_CD,  Z.HANMB_NM,  Z.SUPP_YRMN  ORDER  BY  Z.MB_CD,  Z.SUPP_YRMN ";
        sobj.setSql(query);
        sobj.setString("FRMB_CD", FRMB_CD);               //FRMB_CD
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("TOMB_CD", TOMB_CD);               //TOMB_CD
        return sobj;
    }
    // 공제
    public DOBJ CALLsearchMst_test_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_test_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_test_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //FRMB_CD
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //TOMB_CD
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUPP_YRMN,  MB_CD,  HANMB_NM,  SUM(X.DISTR_AMT28)  AS  DISTR_AMT28,  SUM(X.DISTR_AMT29)  AS  DISTR_AMT29,  SUM(X.DISTR_AMT30)  AS  DISTR_AMT30,  SUM(X.DISTR_AMT31)  AS  DISTR_AMT31,  SUM(X.DISTR_AMT32)  AS  DISTR_AMT32,  SUM(X.DISTR_AMT33)  AS  DISTR_AMT33,  SUM(X.DISTR_AMT34)  AS  DISTR_AMT34,  SUM(X.DISTR_AMT35)  AS  DISTR_AMT35,  SUM(X.DISTR_AMT36)  AS  DISTR_AMT36,  SUM(X.DISTR_AMT37)  AS  DISTR_AMT37,  SUM(X.DISTR_AMT38)  AS  DISTR_AMT38,  SUM(X.DISTR_AMT39)  AS  DISTR_AMT39,  SUM(X.DISTR_AMT40)  AS  DISTR_AMT40,  SUM(X.DISTR_AMT41)  AS  DISTR_AMT41,  SUM(X.DISTR_AMT42)  AS  DISTR_AMT42,  SUM(X.DISTR_AMT43)  AS  DISTR_AMT43,  SUM(X.DISTR_AMT44)  AS  DISTR_AMT44,  MAX(X.DISTR_GTOT)  AS  DISTR_GTOT  FROM  (   \n";
        query +=" SELECT  D.MB_CD,  E.HANMB_NM,  D.SUPP_YRMN,  NVL(DECODE(D.DEDCT_GBNONE,  '01',  DECODE(D.DEDCT_GBNTWO,  '01',  DEDCT_AMT)),0)  DISTR_AMT28,  NVL(DECODE(D.DEDCT_GBNONE,  '01',  DECODE(D.DEDCT_GBNTWO,  '02',  DEDCT_AMT)),0)  DISTR_AMT29,  NVL(DECODE(D.DEDCT_GBNONE,  '01',  DECODE(D.DEDCT_GBNTWO,  '03',  DEDCT_AMT)),0)  DISTR_AMT30,  NVL(DECODE(D.DEDCT_GBNONE,  '01',  DECODE(D.DEDCT_GBNTWO,  '04',  DEDCT_AMT)),0)  DISTR_AMT31,  NVL(DECODE(D.DEDCT_GBNONE,  '01',  DECODE(D.DEDCT_GBNTWO,  '05',  DEDCT_AMT)),0)  DISTR_AMT32,  NVL(DECODE(D.DEDCT_GBNONE,  '01',  DECODE(D.DEDCT_GBNTWO,  '06',  DEDCT_AMT)),0)  DISTR_AMT33,  NVL(DECODE(D.DEDCT_GBNONE,  '01',  DECODE(D.DEDCT_GBNTWO,  '07',  DEDCT_AMT)),0)  DISTR_AMT34,  NVL(DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '01',  DEDCT_AMT)),0)  DISTR_AMT35,  NVL(DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '02',  DEDCT_AMT)),0)  DISTR_AMT36,  NVL(DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '03',  DEDCT_AMT)),0)  DISTR_AMT37,  NVL(DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '04',  DEDCT_AMT)),0)  DISTR_AMT38,  NVL(DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '05',  DEDCT_AMT)),0)  DISTR_AMT39,  NVL(DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '06',  DEDCT_AMT)),0)  DISTR_AMT40,  NVL(DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '07',  DEDCT_AMT)),0)  DISTR_AMT41,  NVL(DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '08',  DEDCT_AMT)),0)  DISTR_AMT42,  NVL(DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '09',  DEDCT_AMT)),0)  DISTR_AMT43,  NVL(DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '10',  DEDCT_AMT)),0)  DISTR_AMT44,   \n";
        query +=" (SELECT  NVL(SUM(X.DEDCT_AMT),0)  DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  X,  FIDU.TMEM_MB  Z  WHERE  X.MB_CD  =  Z.MB_CD   \n";
        query +=" AND  X.MB_CD  =  D.MB_CD   \n";
        query +=" AND  X.SUPP_YRMN  =  D.SUPP_YRMN   \n";
        query +=" AND  X.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'  )  AS  DISTR_GTOT  FROM  FIDU.TTAC_DEDCTAMT  D,  FIDU.TMEM_MB  E  WHERE  D.MB_CD  =  E.MB_CD   \n";
        query +=" AND  D.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'   \n";
        query +=" AND  D.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  D.MB_CD  BETWEEN  :FRMB_CD   \n";
        query +=" AND  :TOMB_CD  )X  GROUP  BY  X.MB_CD,  X.HANMB_NM,  X.SUPP_YRMN  ORDER  BY  X.MB_CD,  X.SUPP_YRMN ";
        sobj.setSql(query);
        sobj.setString("FRMB_CD", FRMB_CD);               //FRMB_CD
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("TOMB_CD", TOMB_CD);               //TOMB_CD
        return sobj;
    }
    // 출판사계산서금액
    public DOBJ CALLsearchMst_test_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_test_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_test_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //회원코드
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUPP_YRMN,  MB_CD,  SUM(CASE  WHEN  MB_GBN1  =  'F'   \n";
        query +=" AND  SUBSTR(MDM_CD,1,2)  IN  ('CA','CB','CC','CD','CE','CF','DA')   \n";
        query +=" AND  MDM_CD  NOT  IN  ('CF0101','CC0301','CC0302')  THEN  ORGDISTR_AMT  ELSE  0  END  )  AS  SUPPTEMP_AMT1,  SUM(CASE  WHEN  MB_GBN1  =  'F'   \n";
        query +=" AND  SUBSTR(MDM_CD,1,2)  IN  ('CA','CB','CC','CD','CE','CF','DA')   \n";
        query +=" AND  MDM_CD  NOT  IN  ('CF0101','CC0301','CC0302')  THEN  ATAX_AMT  ELSE  0  END  )  AS  ATAX_AMT1,  SUM(CASE  WHEN  MB_GBN1  =  'F'   \n";
        query +=" AND  SUBSTR(MDM_CD,1,2)  NOT  IN  ('CA','CB','CC','CD','CE','CF','DA')   \n";
        query +=" OR  MDM_CD  IN  ('CF0101','CC0301','CC0302')  THEN  ORGDISTR_AMT  WHEN  MB_GBN1  =  'P'  THEN  ORGDISTR_AMT  ELSE  0  END  )  AS  SUPPTEMP_AMT2,  SUM(CASE  WHEN  MB_GBN1  =  'F'   \n";
        query +=" AND  SUBSTR(MDM_CD,1,2)  NOT  IN  ('CA','CB','CC','CD','CE','CF','DA')   \n";
        query +=" OR  MDM_CD  IN  ('CF0101','CC0301','CC0302')  THEN  ATAX_AMT  WHEN  MB_GBN1  =  'P'  THEN  ATAX_AMT  ELSE  0  END  )  AS  ATAX_AMT2  FROM  (   \n";
        query +=" SELECT  MDM_CD,  ORGDISTR_AMT,  ATAX_AMT,  B.MB_GBN1,  A.SUPP_YRMN,  A.MB_CD  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TMEM_MB  B  WHERE  A.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  BETWEEN  :FRMB_CD   \n";
        query +=" AND  :TOMB_CD   \n";
        query +=" AND  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'   \n";
        query +=" AND  B.MB_GBN1  IN  ('F','P')  )  GROUP  BY  SUPP_YRMN,  MB_CD ";
        sobj.setSql(query);
        sobj.setString("FRMB_CD", FRMB_CD);               //회원코드
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("TOMB_CD", TOMB_CD);               //회원코드
        return sobj;
    }
    // 재분배금조회
    public DOBJ CALLsearchMst_test_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_test_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_test_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //FRMB_CD
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //TOMB_CD
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REDEM_YRMN,REDEM_MB_CD,REDEM_AMT  FROM  FIDU.TTAC_REDEMREDISTR_UPLOAD  WHERE  REDEM_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  REDEM_MB_CD  BETWEEN  :FRMB_CD   \n";
        query +=" AND  :TOMB_CD   \n";
        query +=" AND  TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%' ";
        sobj.setSql(query);
        sobj.setString("FRMB_CD", FRMB_CD);               //FRMB_CD
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("TOMB_CD", TOMB_CD);               //TOMB_CD
        return sobj;
    }
    //##**$$searchMst_test
    //##**$$searchclaim
    /* * 프로그램명 : tac10_r12
    * 작성자 : 성낙문
    * 작성일 : 2009/12/01
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchclaim(DOBJ dobj)
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
            dobj  = CALLsearchclaim_SEL1(Conn, dobj);           //  채권질권자정보조회
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
    public DOBJ CTLsearchclaim( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchclaim_SEL1(Conn, dobj);           //  채권질권자정보조회
        return dobj;
    }
    // 채권질권자정보조회
    public DOBJ CALLsearchclaim_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchclaim_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchclaim_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD  AS  MB_CD,  H.REPAY_YRMN  AS  REPAY_YRMN,  A.MNG_NUM  AS  MNG_NUM,  A.CLAIMPRES_MB_CD  AS  CLAIMPRES_MB_CD,  A.CLAIMPRES_NM  AS  CLAIMPRES_NM,  E.BANK_NM  AS  CLAIMPRES_BANK_CD,  A.CLAIMPRES_ACCN_NUM  AS  CLAIMPRES_ACCN_NUM,  A.CLAIM_PTTNRATE  AS  CLAIM_PTTNRATE,  A.REPAYPROC_START_DAY  AS  REPAYPROC_START_DAY,  A.REPAYPROC_END_DAY  AS  REPAYPROC_END_DAY,  H.REPAY_AMT  AS  REPAY_AMT,  A.REMAK  AS  REMAK  FROM  FIDU.TMEM_CLAIMDEBT  A,  FIDU.TMEM_MB  B,  ACCT.TCAC_BANK  E,   \n";
        query +=" (SELECT  MB_CD,  MNG_NUM,  REPAY_YRMN,  REPAY_AMT  FROM  FIDU.TMEM_CLAIMDEBTREPAY  WHERE  1=1   \n";
        query +=" AND  SUBSTR(REPAY_YRMN,1,6)  =SUBSTR(:TODATE,1,6)  )  H  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.MB_CD  =  H.MB_CD(+)   \n";
        query +=" AND  A.MNG_NUM  =  H.MNG_NUM(+)   \n";
        query +=" AND  TRIM(A.CLAIMPRES_BANK_CD)  =  E.BANK_CD(+)   \n";
        query +=" AND  A.MB_CD  =  :MB_CD   \n";
        query +=" AND  H.REPAY_AMT  <>  0  UNION  ALL   \n";
        query +=" SELECT  A.MB_CD  AS  MB_CD,  A.SUPP_YRMN  AS  REPAY_YRMN,  B.MNG_NUM  AS  MNG_NUM,  MAX(SUPP_MB_CD)  AS  CLAIMPRES_MB_CD,  MAX(PLEDPRES_NM)  AS  CLAIMPRES_NM,  MAX(BANK_NM)  AS  CLAIMPRES_BANK_CD,  MAX(SUPP_ACCN_NUM)  AS  CLAIMPRES_ACCN_NUM,  0  AS  CLAIM_PTTNRATE,  MAX(PLEDTRM_START_DAY)  AS  REPAYPROC_START_DAY,  MAX(PLEDTRM_END_DAY)  AS  REPAYPROC_END_DAY,  SUM(OCC_AMT)  AS  REPAY_AMT,  ''  AS  REMAK  FROM  FIDU.TTAC_PLEDAMT  A,  FIDU.TMEM_PLED  B,  ACCT.TCAC_BANK  C  WHERE  1  =  1   \n";
        query +=" AND  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  B.PLEDPRES_BANK_CD  =  C.BANK_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  =  :MB_CD  GROUP  BY  A.MB_CD,  A.SUPP_YRMN,  MNG_NUM  ORDER  BY  MB_CD,  MNG_NUM  DESC ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("TODATE", TODATE);               //종료일자
        return sobj;
    }
    //##**$$searchclaim
    //##**$$search_mb
    /*
    */
    public DOBJ CTLsearch_mb(DOBJ dobj)
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
            dobj  = CALLsearch_mb_SEL1(Conn, dobj);           //  지급
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
    public DOBJ CTLsearch_mb( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_mb_SEL1(Conn, dobj);           //  지급
        return dobj;
    }
    // 지급
    public DOBJ CALLsearch_mb_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_mb_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_mb_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_GBN = dobj.getRetObject("S").getRecord().get("MB_GBN");   //회원구분
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //회원코드
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   RADIO_RATE = dobj.getRetObject("S").getRecord().get("RADIO_RATE");   //RADIO_RATE
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT MB_CD, MB_GBN1, SUPP_YRMN, DISTR_STOT, DISTR_AMT, MB_NM, SS_NUM  ";
        query +=" FROM (  ";
        query +=" SELECT X.MB_CD, X.SUPP_YRMN, SUM(NVL(X.TOT_REALSUPP_AMT,0)) AS DISTR_STOT , SUM(X.TOT_ORGDISTR_AMT) AS DISTR_AMT, Z.MB_GBN1 MB_GBN1, FIDU.GET_MB_NM ( X.MB_CD ) AS MB_NM, Z.INS_NUM AS SS_NUM  ";
        query +=" FROM FIDU.TTAC_COPYRTAL X , (SELECT MB_CD, POSTSNDBK_YN  ";
        query +=" FROM fidu.TMEM_ADBK  ";
        query +=" WHERE ADDR_GBN = 2) Y , FIDU.TMEM_MB Z  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND X.MB_CD = Y.MB_CD  ";
        query +=" AND X.MB_CD = Z.MB_CD  ";
        query +=" AND Z.SUPPBRE_POST_RECV_YN ='D'  ";
        query +=" AND X.SUPP_YRMN BETWEEN SUBSTR(:FROMDATE,1,6)  ";
        query +=" AND SUBSTR(:TODATE,1,6)  ";
        query +=" AND X.MB_CD BETWEEN :FRMB_CD  ";
        query +=" AND :TOMB_CD  ";
        if( !MB_GBN.equals("") )
        {
            query +=" AND Z.MB_GBN1 = :MB_GBN  ";
        }
        query +=" AND NVL(Y.POSTSNDBK_YN,' ') <> '1'  ";
        query +=" GROUP BY X.MB_CD, X.SUPP_YRMN, Z.MB_GBN1, Z.INS_NUM  ";
        query +=" ORDER BY X.MB_CD )  ";
        query +=" WHERE 1 = 1  ";
        query +=" AND ((:RADIO_RATE = '1'  ";
        query +=" AND DISTR_STOT >= 10000 ) OR( NVL(:RADIO_RATE,'2') <>'1')  ";
        query +=" OR (MB_GBN1 <>'W'  ";
        query +=" AND DISTR_STOT < 10000 ))  ";
        query +=" AND DISTR_AMT > 0  ";
        sobj.setSql(query);
        if(!MB_GBN.equals(""))
        {
            sobj.setString("MB_GBN", MB_GBN);               //회원구분
        }
        sobj.setString("FRMB_CD", FRMB_CD);               //회원코드
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("RADIO_RATE", RADIO_RATE);               //RADIO_RATE
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("TOMB_CD", TOMB_CD);               //회원코드
        return sobj;
    }
    //##**$$search_mb
    //##**$$mb_gbn1
    /*
    */
    public DOBJ CTLmb_gbn1(DOBJ dobj)
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
            dobj  = CALLmb_gbn1_SEL1(Conn, dobj);           //  SEL
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
    public DOBJ CTLmb_gbn1( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmb_gbn1_SEL1(Conn, dobj);           //  SEL
        return dobj;
    }
    // SEL
    public DOBJ CALLmb_gbn1_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmb_gbn1_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmb_gbn1_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MB_GBN1  FROM  FIDU.TMEM_MB  WHERE  MB_CD  =  :MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    //##**$$mb_gbn1
    //##**$$search_mb_distr
    /*
    */
    public DOBJ CTLsearch_mb_distr(DOBJ dobj)
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
            dobj  = CALLsearch_mb_distr_SEL1(Conn, dobj);           //  회원별분배금액
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
    public DOBJ CTLsearch_mb_distr( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_mb_distr_SEL1(Conn, dobj);           //  회원별분배금액
        return dobj;
    }
    // 회원별분배금액
    public DOBJ CALLsearch_mb_distr_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_mb_distr_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_mb_distr_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  FIDU.GET_MB_NM  (F.RIGHTPRES_MB_CD)  AS  RIGHTPRES_NM,  FIDU.GET_SN_NM  (F.RIGHTPRES_MB_CD)  AS  SN_NM,   \n";
        query +=" (SELECT  AVECLASS_CD_NM  FROM  FIDU.TENV_MDMCD  WHERE  MDM_CD  =  F.MDM_CD)  AS  AVECLASS_CD_NM,  FIDU.GET_MDM_NM  (F.MDM_CD)  AS  MDM_NM,  C.APPRV_NUM,  C.CONTRCCLSN_DAY,   \n";
        query +=" (SELECT  FNM_NM  FROM  FIDU.TLEV_APPTNPRESINFO  AA  WHERE  AA.APPRV_NUM  =  C.APPRV_NUM   \n";
        query +=" AND  AA.APPTNPRES_GBN  =  '1')  AS  FNM_NM,  MAX  (C.USE_TTL)  AS  USE_TTL,  FIDU.GET_PROD_NM  (F.PROD_CD)  AS  PROD_NM,  MAX  (D.QTY_PNUM)  AS  QTY_PNUM,  DECODE  (SUBSTR  (F.MDM_CD,  1,  2),  'CA',   \n";
        query +=" (SELECT  SCLASS_CD_NM  FROM  FIDU.TENV_MDMCD  WHERE  MDM_CD  =  F.MDM_CD),  '')  AS  GBN,  C.IFMNT_GBN,  FIDU.GET_CODE_NM  ('00155',  NVL  (C.IFMNT_GBN,  1))  AS  IFMNT_GBN_NM,  SUM  (DECODE  (F.WT_GBN,  'A',  F.DISTR_AMT,  0))  AS  OCC_AMT_A,  SUM  (DECODE  (F.WT_GBN,  'C',  F.DISTR_AMT,  0))  AS  OCC_AMT_C,  SUM  (CASE  WHEN  F.WT_GBN  NOT  IN  ('A',  'C')  THEN  F.DISTR_AMT  ELSE  0  END)  AS  OCC_AMT_ETC,  SUM  (F.DISTR_AMT)  AS  DISTR_AMT,  DECODE  (NVL  (F.SUSP_GBN,  '0'),  '0',  '',  'V')  AS  SUSP_GBN,  MAX  (  CASE  WHEN  CASE  WHEN  F.RIGHTPRES_MB_CD  =  F.ORGAU_MB_CD  THEN  '1'  ELSE   \n";
        query +=" (SELECT  ORGAU_GBN  FROM  FIDU.TOPU_PRODRIGHTPRES  WHERE  RIGHTPRES_MNG_NUM  =  F.RIGHTPRES_MNG_NUM   \n";
        query +=" AND  RIGHTPRES_SEQ  =  F.RIGHTPRES_SEQ   \n";
        query +=" AND  PROD_CD  =  F.PROD_CD)  END  IN  ('2',  '4',  '5')  THEN  FIDU.GET_MB_NM  (F.RIGHTPRES_MB_CD)  ELSE  ''  END)  AS  ORGAU_GBN,  F.ORGAU_NM  FROM  FIDU.TLEV_USEAPPRV  C,  FIDU.TLEV_CLRREC  D,   \n";
        query +=" (SELECT  /*+  INDEX(E  TDIS_DISTRPLANAMT_IDX_PK)*/  WRK_YRMN,  APPRV_NUM,  CLR_NUM,  DISTR_YRMN,  MDM_CD,  SVC_CD,  BSCON_CD,  DISTR_NUM  FROM  FIDU.TDIS_DISTRPLANAMT  WHERE  DISTR_AMT  >  0   \n";
        query +=" AND  MDM_CD  >=  'CA0101'   \n";
        query +=" AND  MDM_CD  <=  'DA9999')  E,  FIDU.TDIS_DISTR  F  WHERE  C.APPRV_NUM  =  D.APPRV_NUM   \n";
        query +=" AND  C.APPRV_NUM  =  E.APPRV_NUM   \n";
        query +=" AND  D.CLR_NUM  =  E.CLR_NUM   \n";
        query +=" AND  D.CONTRCCLSN_DAY  IS  NOT  NULL   \n";
        query +=" AND  F.DISTR_YRMN  =  E.DISTR_YRMN   \n";
        query +=" AND  F.MDM_CD  =  E.MDM_CD   \n";
        query +=" AND  F.SVC_CD  =  E.SVC_CD   \n";
        query +=" AND  F.BSCON_CD  =  E.BSCON_CD   \n";
        query +=" AND  F.WRK_YRMN  =  E.WRK_YRMN   \n";
        query +=" AND  F.DISTR_NUM  =  E.DISTR_NUM   \n";
        query +=" AND  F.WRK_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)   \n";
        query +=" AND  F.RIGHTPRES_MB_CD  =  :MB_CD  GROUP  BY  F.RIGHTPRES_MB_CD,  F.MDM_CD,  C.APPRV_NUM,  C.CONTRCCLSN_DAY,  F.PROD_CD,  C.IFMNT_GBN,  F.SUSP_GBN,  F.ORGAU_NM  ORDER  BY  F.MDM_CD,  C.APPRV_NUM,  F.PROD_CD,  F.ORGAU_NM ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$search_mb_distr
    //##**$$end
}
