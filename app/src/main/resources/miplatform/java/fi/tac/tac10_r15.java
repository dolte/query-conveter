
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r15
{
    public tac10_r15()
    {
    }
    //##**$$searchMst06
    /* * 프로그램명 : tac10_r15
    * 작성자 : 성낙문
    * 작성일 : 2009/12/03
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchMst06(DOBJ dobj)
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
            dobj  = CALLsearchMst06_SEL2(Conn, dobj);           //  공제(소득세,주민세,관리수수료)
            dobj  = CALLsearchMst06_SEL3(Conn, dobj);           //  매체별 분배현황
            dobj  = CALLsearchMst06_SEL4(Conn, dobj);           //  작품보류, 지급보류 해제,보류 금액
            dobj  = CALLsearchMst06_SEL5(Conn, dobj);           //  은행파일지급일자조회
            dobj  = CALLsearchMst06_SEL6(Conn, dobj);           //  재분배금액
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
    public DOBJ CTLsearchMst06( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchMst06_SEL2(Conn, dobj);           //  공제(소득세,주민세,관리수수료)
        dobj  = CALLsearchMst06_SEL3(Conn, dobj);           //  매체별 분배현황
        dobj  = CALLsearchMst06_SEL4(Conn, dobj);           //  작품보류, 지급보류 해제,보류 금액
        dobj  = CALLsearchMst06_SEL5(Conn, dobj);           //  은행파일지급일자조회
        dobj  = CALLsearchMst06_SEL6(Conn, dobj);           //  재분배금액
        return dobj;
    }
    // 공제(소득세,주민세,관리수수료)
    public DOBJ CALLsearchMst06_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM  (TOT_DISTRAMT)  DISTR_TOT,  SUM  (MNGCOMIS_AMT)  +  SUM  (MNGCOMIS_AMT_CAL2)  MNGCOMIS_AMT,  SUM  (MNGCOMIS_AMT_BORYU)  MNGCOMIS_AMT_BORYU,  SUM  (MNGCOMIS_AMT_SUPP_KIGONG)  +  SUM  (MNGCOMIS_AMT_CAL1)  MNGCOMIS_AMT_SUPP_KIGONG,  SUM  (MNGCOMIS_AMT_SUPP)  MNGCOMIS_AMT_SUPP,  SUM  (MNGCOMIS_AMT_CAL1)  MNGCOMIS_AMT_CAL1,  SUM  (MNGCOMIS_AMT_CAL2)  MNGCOMIS_AMT_CAL2,  SUM  (CNT_TOT)  CNT_TOT,  SUM  (SODEK_AMT)  SODEK_AMT,  SUM  (JUMIN_AMT)  JUMIN_AMT,  SUM  (ATAX_AMT)  ATAX_AMT  FROM  (   \n";
        query +=" SELECT  SUM  (ORGDISTR_AMT)  TOT_DISTRAMT,  0  MNGCOMIS_AMT,  0  MNGCOMIS_AMT_BORYU,  0  MNGCOMIS_AMT_SUPP_KIGONG,  0  MNGCOMIS_AMT_SUPP,  0  MNGCOMIS_AMT_CAL1  ,  0  MNGCOMIS_AMT_CAL2  ,  0  CNT_TOT,  0  SODEK_AMT,  0  JUMIN_AMT,  0  ATAX_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A  WHERE  A.SUPP_YRMN  =:SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  0  TOT_DISTRAMT,  SUM(MNGCOMIS_AMT)  MNGCOMIS_AMT,  0  MNGCOMIS_AMT_BORYU,  0  MNGCOMIS_AMT_SUPP_KIGONG,  0  MNGCOMIS_AMT_SUPP,  0  MNGCOMIS_AMT_CAL1  ,  0  MNGCOMIS_AMT_CAL2  ,  0  CNT_TOT,  0  SODEK_AMT,  0  JUMIN_AMT,  0  ATAX_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A  WHERE  1=1   \n";
        query +=" AND  SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  (SUPPSUSP_RELS_AMT=0   \n";
        query +=" AND  PRODSUSP_RELS_AMT  =0   \n";
        query +=" AND  MDMSUSP_RELS_AMT=0  )  UNION  ALL   \n";
        query +=" SELECT  0  TOT_DISTRAMT,  0  MNGCOMIS_AMT,  SUM(MNGCOMIS_AMT_BORYU)  MNGCOMIS_AMT_BORYU,  0  MNGCOMIS_AMT_SUPP_KIGONG,  0  MNGCOMIS_AMT_SUPP,  0  MNGCOMIS_AMT_CAL1  ,  0  MNGCOMIS_AMT_CAL2  ,  0  CNT_TOT,  0  SODEK_AMT,  0  JUMIN_AMT,  0  ATAX_AMT  FROM  (   \n";
        query +=" SELECT  SUM(MNGCOMIS_AMT_SUPP)  MNGCOMIS_AMT_BORYU  FROM  FIDU.TTAC_MBSUPPSUSPAMT  A  WHERE  1=1   \n";
        query +=" AND  SUPP_YRMN  =:SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  SUM(MNGCOMIS_AMT_SUPP)  MNGCOMIS_AMT_BORYU  FROM  FIDU.TTAC_MDMSUPPSUSPAMT  A  WHERE  1=1   \n";
        query +=" AND  SUPP_YRMN  =:SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  SUM(MNGCOMIS_AMT_SUPP)  MNGCOMIS_AMT_BORYU  FROM  FIDU.TTAC_PRODSUPPSUSPAMT  A  WHERE  1=1   \n";
        query +=" AND  SUPP_YRMN  =:SUPP_YRMN  )  UNION  ALL   \n";
        query +=" SELECT  0  TOT_DISTRAMT,  0  MNGCOMIS_AMT,  0  MNGCOMIS_AMT_BORYU,  SUM(MNGCOMIS_AMT)  MNGCOMIS_AMT_SUPP_KIGONG,  0  MNGCOMIS_AMT_SUPP,  0  MNGCOMIS_AMT_CAL1  ,  0  MNGCOMIS_AMT_CAL2  ,  0  CNT_TOT,  0  SODEK_AMT,  0  JUMIN_AMT,  0  ATAX_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A  WHERE  1=1   \n";
        query +=" AND  SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  (SUPPSUSP_RELS_AMT>0   \n";
        query +=" OR  PRODSUSP_RELS_AMT  >0   \n";
        query +=" OR  MDMSUSP_RELS_AMT>0  )   \n";
        query +=" AND  ORGDISTR_AMT  =  SUPPSUSP_RELS_AMT  +  PRODSUSP_RELS_AMT  +  MDMSUSP_RELS_AMT  UNION  ALL   \n";
        query +=" SELECT  0  TOT_DISTRAMT,  0  MNGCOMIS_AMT,  0  MNGCOMIS_AMT_BORYU,  0  MNGCOMIS_AMT_SUPP_KIGONG,  0  MNGCOMIS_AMT_SUPP,  SUM(MNGCOMIS_AMT_CAL)  MNGCOMIS_AMT_CAL1  ,  SUM(A.MNGCOMIS_AMT)  -  SUM(MNGCOMIS_AMT_CAL)  MNGCOMIS_AMT_CAL2  ,  0  CNT_TOT,  0  SODEK_AMT,  0  JUMIN_AMT,  0  ATAX_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  (   \n";
        query +=" SELECT  X.MB_CD,  X.TRNSF_GBN,  X.MDM_CD,  SUM(MNGCOMIS_AMT_CAL)  MNGCOMIS_AMT_CAL  FROM  (   \n";
        query +=" SELECT  X.MB_CD,  X.TRNSF_GBN,  X.MDM_CD,  X.MNGCOMIS_AMT_SUPP  MNGCOMIS_AMT_CAL  FROM  FIDU.TTAC_MBSUPPSUSPAMT  X  WHERE  1=1   \n";
        query +=" AND  RELS_YRMN=:SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  X.MB_CD,  X.TRNSF_GBN,  X.MDM_CD,  X.MNGCOMIS_AMT_SUPP  MNGCOMIS_AMT_CAL  FROM  FIDU.TTAC_MDMSUPPSUSPAMT  X  WHERE  1=1   \n";
        query +=" AND  RELS_YRMN=:SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  X.MB_CD,  X.TRNSF_GBN,  X.MDM_CD,  X.MNGCOMIS_AMT_SUPP  MNGCOMIS_AMT_CAL  FROM  FIDU.TTAC_PRODSUPPSUSPAMT  X  WHERE  1=1   \n";
        query +=" AND  RELS_YRMN=:SUPP_YRMN)  X  GROUP  BY  X.MB_CD,  X.TRNSF_GBN,  X.MDM_CD)  B  WHERE  1=1   \n";
        query +=" AND  A.SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  (SUPPSUSP_RELS_AMT>0   \n";
        query +=" OR  PRODSUSP_RELS_AMT  >0   \n";
        query +=" OR  MDMSUSP_RELS_AMT>0  )   \n";
        query +=" AND  ORGDISTR_AMT  <>  SUPPSUSP_RELS_AMT  +  PRODSUSP_RELS_AMT  +  MDMSUSP_RELS_AMT   \n";
        query +=" AND  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.TRNSF_GBN  =  B.TRNSF_GBN   \n";
        query +=" AND  A.MDM_CD  =  B.MDM_CD  UNION  ALL   \n";
        query +=" SELECT  0  TOT_DISTRAMT,  0  MNGCOMIS_AMT,  0  MNGCOMIS_AMT_BORYU,  0  MNGCOMIS_AMT_SUPP_KIGONG,  SUM(MNGCOMIS_AMT_SUPP)  MNGCOMIS_AMT_SUPP,  0  MNGCOMIS_AMT_CAL1  ,  0  MNGCOMIS_AMT_CAL2  ,  0  CNT_TOT,  0  SODEK_AMT,  0  JUMIN_AMT,  0  ATAX_AMT  FROM  (   \n";
        query +=" SELECT  SUM(MNGCOMIS_AMT_SUPP)  MNGCOMIS_AMT_SUPP  FROM  FIDU.TTAC_MBSUPPSUSPAMT  A  WHERE  1=1   \n";
        query +=" AND  A.MNGCOMIS_SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  NVL(A.RELS_YRMN,'299912')  <>  :SUPP_YRMN   \n";
        query +=" AND  A.SUPP_YRMN  <>:SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  SUM(MNGCOMIS_AMT_SUPP)  MNGCOMIS_AMT_SUPP  FROM  FIDU.TTAC_MDMSUPPSUSPAMT  A  WHERE  1=1   \n";
        query +=" AND  A.MNGCOMIS_SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  NVL(A.RELS_YRMN,'299912')  <>  :SUPP_YRMN   \n";
        query +=" AND  A.SUPP_YRMN  <>:SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  SUM(MNGCOMIS_AMT_SUPP)  MNGCOMIS_AMT_SUPP  FROM  FIDU.TTAC_PRODSUPPSUSPAMT  A  WHERE  1=1   \n";
        query +=" AND  A.MNGCOMIS_SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  NVL(A.RELS_YRMN,'299912')  <>  :SUPP_YRMN   \n";
        query +=" AND  A.SUPP_YRMN  <>:SUPP_YRMN  )  UNION  ALL   \n";
        query +=" SELECT  0  TOT_DISTRAMT,  0  MNGCOMIS_AMT,  0  MNGCOMIS_AMT_BORYU,  0  MNGCOMIS_AMT_SUPP_KIGONG,  0  MNGCOMIS_AMT_SUPP,  0  MNGCOMIS_AMT_CAL1  ,  0  MNGCOMIS_AMT_CAL2  ,  COUNT  (DISTINCT  MB_CD)  CNT_TOT,  0  SODEK_AMT,  0  JUMIN_AMT,  0  ATAX_AMT  FROM  FIDU.TTAC_COPYRTAL  WHERE  SUPP_YRMN  =  :SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  0  TOT_DISTRAMT,  0  MNGCOMIS_AMT,  0  MNGCOMIS_AMT_BORYU,  0  MNGCOMIS_AMT_SUPP_KIGONG,  0  MNGCOMIS_AMT_SUPP,  0  MNGCOMIS_AMT_CAL1  ,  0  MNGCOMIS_AMT_CAL2  ,  0  CNT_TOT,  SUM  (  DECODE  (DEDCT_GBNONE,  '01',  DECODE  (DEDCT_GBNTWO,  '02',  DEDCT_AMT)))  SODEK_AMT,  SUM  (DECODE  (DEDCT_GBNONE,'01',  DECODE  (DEDCT_GBNTWO,  '03',  DEDCT_AMT)))  JUMIN_AMT,  0  ATAX_AMT  FROM  FIDU.TTAC_DEDCTAMT  B  WHERE  B.SUPP_YRMN  =  :SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  0  TOT_DISTRAMT,  0  MNGCOMIS_AMT,  0  MNGCOMIS_AMT_BORYU,  0  MNGCOMIS_AMT_SUPP_KIGONG,  0  MNGCOMIS_AMT_SUPP,  0  MNGCOMIS_AMT_CAL1  ,  0  MNGCOMIS_AMT_CAL2  ,  0  CNT_TOT,  0  SODEK_AMT,  0  JUMIN_AMT,  DECODE  (C.ATAX_AMT,  0,  D.ATAX_AMT,  C.ATAX_AMT)  FROM   \n";
        query +=" (SELECT  SUM  (ATAX_AMT)  ATAX_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TMEM_MB  B  WHERE  SUPP_YRMN  =  :SUPP_YRMN   \n";
        query +=" AND  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  B.MB_GBN1  =  'F'   \n";
        query +=" AND  NVL  (FIDU.GET_FOR_ATAX_YN  (MDM_CD),  '0')  <>  '1')  C,   \n";
        query +=" (SELECT  SUM  (ATAX_AMT)  ATAX_AMT  FROM  FIDU.TTAC_RPDCLEVYATAXAMT  WHERE  SUPP_YRMN  =  :SUPP_YRMN)  D  ) ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 매체별 분배현황
    public DOBJ CALLsearchMst06_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MDM_CD,  AVECLASS_CD_NM,  ORGDISTR_AMT,  DISTR_YRMN,  REALSUPP_AMT,  ETC_AMT  FROM  (   \n";
        query +=" SELECT  A.MDM_CD  AS  MDM_CD,  MAX(B.AVECLASS_CD_NM)  as  AVECLASS_CD_NM,  SUM(C.DISTR_AMT)  +  NVL(SUM(REDISTR_AMT),0)  ORGDISTR_AMT,  CASE  B.AVECLASS_CD  WHEN  'CA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CB'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CC'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CG'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CH'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DF'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'AA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'YYYY.')  ||  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'Q')||'분기'  ELSE  ''  END  AS  DISTR_YRMN,  TRUNC(SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0))  AS  REALSUPP_AMT,  SUM(F.RIGHTPRES_DISTR_MAGP)  AS  ETC_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(ORGDISTR_AMT)  AS  ORGDISTR_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  NOT  IN  ('T0000001'  ,  'T0000002'  ,  'T0000003')  GROUP  BY  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  )  A,  FIDU.TENV_AVECLASSCD  B,  (   \n";
        query +=" SELECT  WRK_YRMN  as  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD  ,  NVL(SUM(DISTR_AMT),0)  AS  DISTR_AMT  FROM  FIDU.TDIS_DISTRPLANAMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  DISTR_AMT  >  0   \n";
        query +=" AND  WRK_YRMN  >  '201006'   \n";
        query +=" AND  (ASS_GBN  IS  NULL   \n";
        query +=" OR  SUBSTR(ASS_GBN  ,  0  ,1)  =  'K')  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  WRK_YRMN  )  C  ,   \n";
        query +=" (SELECT  RELS_YRMN  AS  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  NVL(SUM(SUSP_RELS_AMT),0)  AS  SUPPSUSP_RELS_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  RELS_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  RELS_YRMN,SUBSTR(MDM_CD,1,2)  )  D,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM  (RELS_AMT)  AS  PROD_RELS_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN  AS  SUPP_YRMN,  MDM_CD,  NVL(SUM(DISTR_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN,  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  SUPP_YRMN,  MDM_CD,  NVL(SUM(OCC_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN,  MDM_CD)  GROUP  BY  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  )  E,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(RIGHTPRES_DISTR_MAGP)  as  RIGHTPRES_DISTR_MAGP  FROM  FIDU.TTAC_MDMCLASSDISTRAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  NOT  IN  ('T0000001'  ,  'T0000002'  ,  'T0000003')  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  SUPP_YRMN  )  F,   \n";
        query +=" (SELECT  WRK_YRMN  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(DISTR_AMT)  REDISTR_AMT  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)  GROUP  BY  WRK_YRMN,  SUBSTR(MDM_CD,1,2)  )  G  WHERE  A.MDM_CD  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  C.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  C.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  D.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  D.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  E.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  E.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  F.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  F.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  G.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  G.MDM_CD(+)  GROUP  BY  A.MDM_CD,  A.SUPP_YRMN,  B.AVECLASS_CD  HAVING  SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0)  >  0  UNION  ALL   \n";
        query +=" SELECT  ''  AS  MDM_CD  ,  '함저협'  AS  AVECLASS_CD_NM  ,  SUM(ORGDISTR_AMT)  AS  ORGDISTR_AMT  ,  DISTR_YRMN  ,  SUM(REALSUPP_AMT)  AS  REALSUPP_AMT  ,  SUM(ETC_AMT)  AS  ETC_AMT  FROM  (   \n";
        query +=" SELECT  A.MDM_CD  AS  MDM_CD,  MAX(B.AVECLASS_CD_NM)  as  AVECLASS_CD_NM,  SUM(C.DISTR_AMT)  +  NVL(SUM(REDISTR_AMT),0)  ORGDISTR_AMT,  CASE  B.AVECLASS_CD  WHEN  'CA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CB'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CC'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CG'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CH'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DF'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'AA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'YYYY.')  ||  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'Q')||'분기'  ELSE  ''  END  AS  DISTR_YRMN,  TRUNC(SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0))  AS  REALSUPP_AMT,  SUM(F.RIGHTPRES_DISTR_MAGP)  AS  ETC_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(ORGDISTR_AMT)  AS  ORGDISTR_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  =  'T0000001'  GROUP  BY  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  )  A,  FIDU.TENV_AVECLASSCD  B,  (   \n";
        query +=" SELECT  WRK_YRMN  as  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD  ,  NVL(SUM(DISTR_AMT),0)  AS  DISTR_AMT  FROM  FIDU.TDIS_DISTRPLANAMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  DISTR_AMT  >  0   \n";
        query +=" AND  WRK_YRMN  >  '201006'   \n";
        query +=" AND  ASS_GBN  =  'T0000001'  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  WRK_YRMN  )  C  ,   \n";
        query +=" (SELECT  RELS_YRMN  AS  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  NVL(SUM(SUSP_RELS_AMT),0)  AS  SUPPSUSP_RELS_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  RELS_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)   \n";
        query +=" AND  MB_CD  =  'T0000001'  GROUP  BY  RELS_YRMN,SUBSTR(MDM_CD,1,2)  )  D,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM  (RELS_AMT)  AS  PROD_RELS_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN  AS  SUPP_YRMN,  MDM_CD,  NVL(SUM(DISTR_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN,  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  SUPP_YRMN,  MDM_CD,  NVL(SUM(OCC_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)   \n";
        query +=" AND  MB_CD  =  'T0000001'  GROUP  BY  SUPP_YRMN,  MDM_CD)  GROUP  BY  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  )  E,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(RIGHTPRES_DISTR_MAGP)  as  RIGHTPRES_DISTR_MAGP  FROM  FIDU.TTAC_MDMCLASSDISTRAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  =  'T0000001'  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  SUPP_YRMN  )  F,   \n";
        query +=" (SELECT  WRK_YRMN  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(DISTR_AMT)  REDISTR_AMT  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)  GROUP  BY  WRK_YRMN,  SUBSTR(MDM_CD,1,2)  )  G  WHERE  A.MDM_CD  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  C.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  C.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  D.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  D.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  E.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  E.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  F.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  F.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  G.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  G.MDM_CD(+)  GROUP  BY  A.MDM_CD,  A.SUPP_YRMN,  B.AVECLASS_CD  HAVING  SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0)  >  0  )  GROUP  BY  DISTR_YRMN  UNION  ALL   \n";
        query +=" SELECT  ''  AS  MDM_CD  ,  '음실연'  AS  AVECLASS_CD_NM  ,  SUM(ORGDISTR_AMT)  AS  ORGDISTR_AMT  ,  DISTR_YRMN  ,  SUM(REALSUPP_AMT)  AS  REALSUPP_AMT  ,  SUM(ETC_AMT)  AS  ETC_AMT  FROM  (   \n";
        query +=" SELECT  A.MDM_CD  AS  MDM_CD,  MAX(B.AVECLASS_CD_NM)  as  AVECLASS_CD_NM,  SUM(C.DISTR_AMT)  +  NVL(SUM(REDISTR_AMT),0)  ORGDISTR_AMT,  CASE  B.AVECLASS_CD  WHEN  'CA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CB'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CC'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CG'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CH'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DF'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'AA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'YYYY.')  ||  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'Q')||'분기'  ELSE  ''  END  AS  DISTR_YRMN,  TRUNC(SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0))  AS  REALSUPP_AMT,  SUM(F.RIGHTPRES_DISTR_MAGP)  AS  ETC_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(ORGDISTR_AMT)  AS  ORGDISTR_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  =  'T0000002'  GROUP  BY  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  )  A,  FIDU.TENV_AVECLASSCD  B,  (   \n";
        query +=" SELECT  WRK_YRMN  as  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD  ,  NVL(SUM(DISTR_AMT),0)  AS  DISTR_AMT  FROM  FIDU.TDIS_DISTRPLANAMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  DISTR_AMT  >  0   \n";
        query +=" AND  WRK_YRMN  >  '201006'   \n";
        query +=" AND  ASS_GBN  =  'T0000002'  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  WRK_YRMN  )  C  ,   \n";
        query +=" (SELECT  RELS_YRMN  AS  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  NVL(SUM(SUSP_RELS_AMT),0)  AS  SUPPSUSP_RELS_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  RELS_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)   \n";
        query +=" AND  MB_CD  =  'T0000002'  GROUP  BY  RELS_YRMN,SUBSTR(MDM_CD,1,2)  )  D,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM  (RELS_AMT)  AS  PROD_RELS_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN  AS  SUPP_YRMN,  MDM_CD,  NVL(SUM(DISTR_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN,  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  SUPP_YRMN,  MDM_CD,  NVL(SUM(OCC_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)   \n";
        query +=" AND  MB_CD  =  'T0000002'  GROUP  BY  SUPP_YRMN,  MDM_CD)  GROUP  BY  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  )  E,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(RIGHTPRES_DISTR_MAGP)  as  RIGHTPRES_DISTR_MAGP  FROM  FIDU.TTAC_MDMCLASSDISTRAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  =  'T0000002'  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  SUPP_YRMN  )  F,   \n";
        query +=" (SELECT  WRK_YRMN  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(DISTR_AMT)  REDISTR_AMT  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)  GROUP  BY  WRK_YRMN,  SUBSTR(MDM_CD,1,2)  )  G  WHERE  A.MDM_CD  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  C.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  C.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  D.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  D.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  E.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  E.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  F.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  F.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  G.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  G.MDM_CD(+)  GROUP  BY  A.MDM_CD,  A.SUPP_YRMN,  B.AVECLASS_CD  HAVING  SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0)  >  0  )  GROUP  BY  DISTR_YRMN  UNION  ALL   \n";
        query +=" SELECT  ''  AS  MDM_CD  ,  '음산협'  AS  AVECLASS_CD_NM  ,  SUM(ORGDISTR_AMT)  AS  ORGDISTR_AMT  ,  DISTR_YRMN  ,  SUM(REALSUPP_AMT)  AS  REALSUPP_AMT  ,  SUM(ETC_AMT)  AS  ETC_AMT  FROM  (   \n";
        query +=" SELECT  A.MDM_CD  AS  MDM_CD,  MAX(B.AVECLASS_CD_NM)  as  AVECLASS_CD_NM,  SUM(C.DISTR_AMT)  +  NVL(SUM(REDISTR_AMT),0)  ORGDISTR_AMT,  CASE  B.AVECLASS_CD  WHEN  'CA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CB'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CC'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CG'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CH'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DF'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'AA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'YYYY.')  ||  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'Q')||'분기'  ELSE  ''  END  AS  DISTR_YRMN,  TRUNC(SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0))  AS  REALSUPP_AMT,  SUM(F.RIGHTPRES_DISTR_MAGP)  AS  ETC_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(ORGDISTR_AMT)  AS  ORGDISTR_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  =  'T0000003'  GROUP  BY  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  )  A,  FIDU.TENV_AVECLASSCD  B,  (   \n";
        query +=" SELECT  WRK_YRMN  as  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD  ,  NVL(SUM(DISTR_AMT),0)  AS  DISTR_AMT  FROM  FIDU.TDIS_DISTRPLANAMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  DISTR_AMT  >  0   \n";
        query +=" AND  WRK_YRMN  >  '201006'   \n";
        query +=" AND  ASS_GBN  =  'T0000003'  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  WRK_YRMN  )  C  ,   \n";
        query +=" (SELECT  RELS_YRMN  AS  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  NVL(SUM(SUSP_RELS_AMT),0)  AS  SUPPSUSP_RELS_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  RELS_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)   \n";
        query +=" AND  MB_CD  =  'T0000003'  GROUP  BY  RELS_YRMN,SUBSTR(MDM_CD,1,2)  )  D,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM  (RELS_AMT)  AS  PROD_RELS_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN  AS  SUPP_YRMN,  MDM_CD,  NVL(SUM(DISTR_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN,  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  SUPP_YRMN,  MDM_CD,  NVL(SUM(OCC_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)   \n";
        query +=" AND  MB_CD  =  'T0000003'  GROUP  BY  SUPP_YRMN,  MDM_CD)  GROUP  BY  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  )  E,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(RIGHTPRES_DISTR_MAGP)  as  RIGHTPRES_DISTR_MAGP  FROM  FIDU.TTAC_MDMCLASSDISTRAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  =  'T0000003'  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  SUPP_YRMN  )  F,   \n";
        query +=" (SELECT  WRK_YRMN  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(DISTR_AMT)  REDISTR_AMT  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)  GROUP  BY  WRK_YRMN,  SUBSTR(MDM_CD,1,2)  )  G  WHERE  A.MDM_CD  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  C.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  C.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  D.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  D.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  E.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  E.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  F.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  F.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  G.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  G.MDM_CD(+)  GROUP  BY  A.MDM_CD,  A.SUPP_YRMN,  B.AVECLASS_CD  HAVING  SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0)  >  0  )  GROUP  BY  DISTR_YRMN  )  ORDER  BY  MDM_CD  ,  DISTR_YRMN ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 작품보류, 지급보류 해제,보류 금액
    public DOBJ CALLsearchMst06_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TRUNC(SUM(C.SUSP_AMT))  AS  SUSP_BAMT,  TRUNC(SUM(C2.SUSP_AMT))  AS  SUSP_MAMT,  NVL(TRUNC(SUM(D.PRODSUSP_AMT)),0)  AS  SUSP_DAMT,  NVL(TRUNC(SUM(E.SUPPSUSP_RELS_AMT),0),0)  +  NVL(TRUNC(SUM(H.SUPPSUSP_RELS_AMT),0),0)  AS  SUSP_HJAMT,  TRUNC(SUM(F.PROD_RELS_AMT),0)  AS  SUSP_HBAMT,  SUM(B.DISTR_AMT)  +SUM(G.DISTR_AMT)  AS  TOT_BAMT,  TRUNC(SUM(A.ORGDISTR_AMT))  AS  TOT_JAMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN,  NVL(SUM(ORGDISTR_AMT),0)  AS  ORGDISTR_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)  GROUP  BY  SUPP_YRMN  )  A,   \n";
        query +=" (SELECT  WRK_YRMN  AS  SUPP_YRMN,  NVL(SUM(DISTR_AMT),0)  AS  DISTR_AMT  FROM  FIDU.TDIS_DISTRPLANAMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  DISTR_AMT  >  0   \n";
        query +=" AND  WRK_YRMN  >  '201004'  GROUP  BY  WRK_YRMN  )  B,   \n";
        query +=" (SELECT  SUPP_YRMN,  NVL(SUM(SUSP_AMT),0)  AS  SUSP_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN  )  C,   \n";
        query +=" (SELECT  SUPP_YRMN,  NVL(SUM(SUSP_AMT),0)  AS  SUSP_AMT  FROM  FIDU.TTAC_MDMSUPPSUSPAMT  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN  )  C2,   \n";
        query +=" (SELECT  SUPP_YRMN,  SUM  (SUSP_AMT)  AS  PRODSUSP_AMT  FROM   \n";
        query +=" (SELECT  WRK_YRMN  AS  SUPP_YRMN,  NVL(SUM(DISTR_AMT),0)  AS  SUSP_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  WRK_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  WRK_YRMN  UNION  ALL   \n";
        query +=" SELECT  SUPP_YRMN,  NVL(SUM(OCC_AMT),0)  AS  SUSP_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN)  GROUP  BY  SUPP_YRMN  )  D,   \n";
        query +=" (SELECT  RELS_YRMN  AS  SUPP_YRMN,  NVL(SUM(SUSP_RELS_AMT),0)  AS  SUPPSUSP_RELS_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  RELS_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  RELS_YRMN  )  E,   \n";
        query +=" (SELECT  RELS_YRMN  AS  SUPP_YRMN,  NVL(SUM(SUSP_RELS_AMT),0)  AS  SUPPSUSP_RELS_AMT  FROM  FIDU.TTAC_MDMSUPPSUSPAMT  WHERE  RELS_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  RELS_YRMN  )  H,   \n";
        query +=" (SELECT  SUPP_YRMN,  SUM  (RELS_AMT)  AS  PROD_RELS_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN  AS  SUPP_YRMN,  NVL(SUM(DISTR_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  SUPP_YRMN,  NVL(SUM(OCC_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN)  GROUP  BY  SUPP_YRMN  )  F,   \n";
        query +=" (SELECT  WRK_YRMN  AS  SUPP_YRMN,  NVL(SUM(DISTR_AMT),0)  AS  DISTR_AMT  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)  GROUP  BY  WRK_YRMN  )  G  WHERE  A.SUPP_YRMN  =  B.SUPP_YRMN(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  C.SUPP_YRMN(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  C2.SUPP_YRMN(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  D.SUPP_YRMN(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  E.SUPP_YRMN(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  F.SUPP_YRMN(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  G.SUPP_YRMN(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  H.SUPP_YRMN(+)  HAVING  SUM(A.ORGDISTR_AMT)-SUM(NVL(E.SUPPSUSP_RELS_AMT,0))-SUM(NVL(F.PROD_RELS_AMT,0))  <>  0 ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 은행파일지급일자조회
    public DOBJ CALLsearchMst06_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  A.SUPP_DAY  FROM  FIDU.TTAC_BANKFILE  A  WHERE  A.SUPP_YRMN  =  :SUPP_YRMN ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 재분배금액
    public DOBJ CALLsearchMst06_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REDISTR_AMT,  B.REDISTR_AMT1  FROM  (   \n";
        query +=" SELECT  SUM(DISTR_AMT)  REDISTR_AMT  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  DISTR_AMT  <  0)  A,  (   \n";
        query +=" SELECT  SUM(DISTR_AMT)  REDISTR_AMT1  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  DISTR_AMT  >  0)  B ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$searchMst06
    //##**$$searchMst06_2
    /*
    */
    public DOBJ CTLsearchMst06_2(DOBJ dobj)
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
            dobj  = CALLsearchMst06_2_SEL2(Conn, dobj);           //  공제(소득세,주민세,관리수수료)
            dobj  = CALLsearchMst06_2_SEL3(Conn, dobj);           //  매체별 분배현황
            dobj  = CALLsearchMst06_2_SEL4(Conn, dobj);           //  작품보류, 지급보류 해제,보류 금액
            dobj  = CALLsearchMst06_2_SEL5(Conn, dobj);           //  은행파일지급일자조회
            dobj  = CALLsearchMst06_2_SEL6(Conn, dobj);           //  재분배금액
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
    public DOBJ CTLsearchMst06_2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchMst06_2_SEL2(Conn, dobj);           //  공제(소득세,주민세,관리수수료)
        dobj  = CALLsearchMst06_2_SEL3(Conn, dobj);           //  매체별 분배현황
        dobj  = CALLsearchMst06_2_SEL4(Conn, dobj);           //  작품보류, 지급보류 해제,보류 금액
        dobj  = CALLsearchMst06_2_SEL5(Conn, dobj);           //  은행파일지급일자조회
        dobj  = CALLsearchMst06_2_SEL6(Conn, dobj);           //  재분배금액
        return dobj;
    }
    // 공제(소득세,주민세,관리수수료)
    public DOBJ CALLsearchMst06_2_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_2_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_2_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(TOT_DISTRAMT)  DISTR_TOT,  SUM(MNGCOMIS_AMT)  MNGCOMIS_AMT  ,  SUM(CNT_TOT)  CNT_TOT,  SUM(SODEK_AMT)  SODEK_AMT,  SUM(JUMIN_AMT)  JUMIN_AMT,  SUM(ATAX_AMT)  ATAX_AMT  FROM(   \n";
        query +=" SELECT  SUM(ORGDISTR_AMT)  TOT_DISTRAMT,  SUM(MNGCOMIS_AMT)  MNGCOMIS_AMT,  0  CNT_TOT,  0  SODEK_AMT,  0  JUMIN_AMT,  0  ATAX_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A  WHERE  A.SUPP_YRMN  =:SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  0  TOT_DISTRAMT,  0  MNGCOMIS_AMT,  COUNT(DISTINCT  MB_CD)  CNT_TOT,  0  SODEK_AMT,  0  JUMIN_AMT,  0  ATAX_AMT  FROM  FIDU.TTAC_COPYRTAL  WHERE  SUPP_YRMN  =:SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  0  TOT_DISTRAMT,  0  MNGCOMIS_AMT,  0  CNT_TOT,  SUM(  DECODE(DEDCT_GBNONE,  '01',  DECODE(DEDCT_GBNTWO,  '02',  DEDCT_AMT)))  SODEK_AMT,  SUM(DECODE(DEDCT_GBNONE,  '01',  DECODE(DEDCT_GBNTWO,  '03',  DEDCT_AMT)))  JUMIN_AMT,  0  ATAX_AMT  FROM  FIDU.TTAC_DEDCTAMT  B  WHERE  B.SUPP_YRMN  =:SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  0  TOT_DISTRAMT,  0  MNGCOMIS_AMT,  0  CNT_TOT,  0  SODEK_AMT,  0  JUMIN_AMT,  DECODE(C.ATAX_AMT,0,  D.ATAX_AMT,  C.ATAX_AMT)  FROM   \n";
        query +=" (SELECT  SUM(ATAX_AMT)  ATAX_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TMEM_MB  B  WHERE  SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  B.MB_GBN1  ='F'   \n";
        query +=" AND  NVL(FIDU.GET_FOR_ATAX_YN(MDM_CD),'0')  <>  '1')  C,   \n";
        query +=" (SELECT  SUM(ATAX_AMT)  ATAX_AMT  FROM  FIDU.TTAC_RPDCLEVYATAXAMT  WHERE  SUPP_YRMN  =:SUPP_YRMN)  D) ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 매체별 분배현황
    public DOBJ CALLsearchMst06_2_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_2_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_2_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MDM_CD,  AVECLASS_CD_NM,  ORGDISTR_AMT,  DISTR_YRMN,  REALSUPP_AMT,  ETC_AMT  FROM  (   \n";
        query +=" SELECT  A.MDM_CD  AS  MDM_CD,  MAX(B.AVECLASS_CD_NM)  as  AVECLASS_CD_NM,  SUM(C.DISTR_AMT)  +  NVL(SUM(REDISTR_AMT),0)  ORGDISTR_AMT,  CASE  B.AVECLASS_CD  WHEN  'CA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CB'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CC'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CG'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CH'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DF'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'AA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'YYYY.')  ||  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'Q')||'분기'  ELSE  ''  END  AS  DISTR_YRMN,  TRUNC(SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0))  AS  REALSUPP_AMT,  SUM(F.RIGHTPRES_DISTR_MAGP)  AS  ETC_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(ORGDISTR_AMT)  AS  ORGDISTR_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  NOT  IN  ('T0000001'  ,  'T0000002'  ,  'T0000003')  GROUP  BY  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  )  A,  FIDU.TENV_AVECLASSCD  B,  (   \n";
        query +=" SELECT  WRK_YRMN  as  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD  ,  NVL(SUM(DISTR_AMT),0)  AS  DISTR_AMT  FROM  FIDU.TDIS_DISTRPLANAMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  DISTR_AMT  >  0   \n";
        query +=" AND  WRK_YRMN  >  '201006'   \n";
        query +=" AND  (ASS_GBN  IS  NULL   \n";
        query +=" OR  SUBSTR(ASS_GBN  ,  0  ,1)  =  'K')  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  WRK_YRMN  )  C  ,   \n";
        query +=" (SELECT  RELS_YRMN  AS  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  NVL(SUM(SUSP_RELS_AMT),0)  AS  SUPPSUSP_RELS_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  RELS_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  RELS_YRMN,SUBSTR(MDM_CD,1,2)  )  D,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM  (RELS_AMT)  AS  PROD_RELS_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN  AS  SUPP_YRMN,  MDM_CD,  NVL(SUM(DISTR_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN,  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  SUPP_YRMN,  MDM_CD,  NVL(SUM(OCC_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN,  MDM_CD)  GROUP  BY  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  )  E,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(RIGHTPRES_DISTR_MAGP)  as  RIGHTPRES_DISTR_MAGP  FROM  FIDU.TTAC_MDMCLASSDISTRAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  NOT  IN  ('T0000001'  ,  'T0000002'  ,  'T0000003')  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  SUPP_YRMN  )  F,   \n";
        query +=" (SELECT  WRK_YRMN  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(DISTR_AMT)  REDISTR_AMT  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)  GROUP  BY  WRK_YRMN,  SUBSTR(MDM_CD,1,2)  )  G  WHERE  A.MDM_CD  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  C.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  C.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  D.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  D.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  E.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  E.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  F.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  F.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  G.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  G.MDM_CD(+)  GROUP  BY  A.MDM_CD,  A.SUPP_YRMN,  B.AVECLASS_CD  HAVING  SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0)  >  0  UNION  ALL   \n";
        query +=" SELECT  ''  AS  MDM_CD  ,  '함저협'  AS  AVECLASS_CD_NM  ,  SUM(ORGDISTR_AMT)  AS  ORGDISTR_AMT  ,  DISTR_YRMN  ,  SUM(REALSUPP_AMT)  AS  REALSUPP_AMT  ,  SUM(ETC_AMT)  AS  ETC_AMT  FROM  (   \n";
        query +=" SELECT  A.MDM_CD  AS  MDM_CD,  MAX(B.AVECLASS_CD_NM)  as  AVECLASS_CD_NM,  SUM(C.DISTR_AMT)  +  NVL(SUM(REDISTR_AMT),0)  ORGDISTR_AMT,  CASE  B.AVECLASS_CD  WHEN  'CA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CB'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CC'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CG'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CH'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DF'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'AA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'YYYY.')  ||  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'Q')||'분기'  ELSE  ''  END  AS  DISTR_YRMN,  TRUNC(SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0))  AS  REALSUPP_AMT,  SUM(F.RIGHTPRES_DISTR_MAGP)  AS  ETC_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(ORGDISTR_AMT)  AS  ORGDISTR_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  =  'T0000001'  GROUP  BY  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  )  A,  FIDU.TENV_AVECLASSCD  B,  (   \n";
        query +=" SELECT  WRK_YRMN  as  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD  ,  NVL(SUM(DISTR_AMT),0)  AS  DISTR_AMT  FROM  FIDU.TDIS_DISTRPLANAMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  DISTR_AMT  >  0   \n";
        query +=" AND  WRK_YRMN  >  '201006'   \n";
        query +=" AND  ASS_GBN  =  'T0000001'  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  WRK_YRMN  )  C  ,   \n";
        query +=" (SELECT  RELS_YRMN  AS  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  NVL(SUM(SUSP_RELS_AMT),0)  AS  SUPPSUSP_RELS_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  RELS_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)   \n";
        query +=" AND  MB_CD  =  'T0000001'  GROUP  BY  RELS_YRMN,SUBSTR(MDM_CD,1,2)  )  D,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM  (RELS_AMT)  AS  PROD_RELS_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN  AS  SUPP_YRMN,  MDM_CD,  NVL(SUM(DISTR_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN,  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  SUPP_YRMN,  MDM_CD,  NVL(SUM(OCC_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)   \n";
        query +=" AND  MB_CD  =  'T0000001'  GROUP  BY  SUPP_YRMN,  MDM_CD)  GROUP  BY  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  )  E,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(RIGHTPRES_DISTR_MAGP)  as  RIGHTPRES_DISTR_MAGP  FROM  FIDU.TTAC_MDMCLASSDISTRAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  =  'T0000001'  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  SUPP_YRMN  )  F,   \n";
        query +=" (SELECT  WRK_YRMN  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(DISTR_AMT)  REDISTR_AMT  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)  GROUP  BY  WRK_YRMN,  SUBSTR(MDM_CD,1,2)  )  G  WHERE  A.MDM_CD  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  C.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  C.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  D.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  D.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  E.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  E.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  F.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  F.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  G.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  G.MDM_CD(+)  GROUP  BY  A.MDM_CD,  A.SUPP_YRMN,  B.AVECLASS_CD  HAVING  SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0)  >  0  )  GROUP  BY  DISTR_YRMN  UNION  ALL   \n";
        query +=" SELECT  ''  AS  MDM_CD  ,  '음실연'  AS  AVECLASS_CD_NM  ,  SUM(ORGDISTR_AMT)  AS  ORGDISTR_AMT  ,  DISTR_YRMN  ,  SUM(REALSUPP_AMT)  AS  REALSUPP_AMT  ,  SUM(ETC_AMT)  AS  ETC_AMT  FROM  (   \n";
        query +=" SELECT  A.MDM_CD  AS  MDM_CD,  MAX(B.AVECLASS_CD_NM)  as  AVECLASS_CD_NM,  SUM(C.DISTR_AMT)  +  NVL(SUM(REDISTR_AMT),0)  ORGDISTR_AMT,  CASE  B.AVECLASS_CD  WHEN  'CA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CB'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CC'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CG'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CH'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DF'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'AA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'YYYY.')  ||  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'Q')||'분기'  ELSE  ''  END  AS  DISTR_YRMN,  TRUNC(SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0))  AS  REALSUPP_AMT,  SUM(F.RIGHTPRES_DISTR_MAGP)  AS  ETC_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(ORGDISTR_AMT)  AS  ORGDISTR_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  =  'T0000002'  GROUP  BY  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  )  A,  FIDU.TENV_AVECLASSCD  B,  (   \n";
        query +=" SELECT  WRK_YRMN  as  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD  ,  NVL(SUM(DISTR_AMT),0)  AS  DISTR_AMT  FROM  FIDU.TDIS_DISTRPLANAMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  DISTR_AMT  >  0   \n";
        query +=" AND  WRK_YRMN  >  '201006'   \n";
        query +=" AND  ASS_GBN  =  'T0000002'  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  WRK_YRMN  )  C  ,   \n";
        query +=" (SELECT  RELS_YRMN  AS  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  NVL(SUM(SUSP_RELS_AMT),0)  AS  SUPPSUSP_RELS_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  RELS_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)   \n";
        query +=" AND  MB_CD  =  'T0000002'  GROUP  BY  RELS_YRMN,SUBSTR(MDM_CD,1,2)  )  D,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM  (RELS_AMT)  AS  PROD_RELS_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN  AS  SUPP_YRMN,  MDM_CD,  NVL(SUM(DISTR_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN,  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  SUPP_YRMN,  MDM_CD,  NVL(SUM(OCC_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)   \n";
        query +=" AND  MB_CD  =  'T0000002'  GROUP  BY  SUPP_YRMN,  MDM_CD)  GROUP  BY  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  )  E,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(RIGHTPRES_DISTR_MAGP)  as  RIGHTPRES_DISTR_MAGP  FROM  FIDU.TTAC_MDMCLASSDISTRAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  =  'T0000002'  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  SUPP_YRMN  )  F,   \n";
        query +=" (SELECT  WRK_YRMN  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(DISTR_AMT)  REDISTR_AMT  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)  GROUP  BY  WRK_YRMN,  SUBSTR(MDM_CD,1,2)  )  G  WHERE  A.MDM_CD  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  C.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  C.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  D.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  D.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  E.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  E.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  F.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  F.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  G.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  G.MDM_CD(+)  GROUP  BY  A.MDM_CD,  A.SUPP_YRMN,  B.AVECLASS_CD  HAVING  SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0)  >  0  )  GROUP  BY  DISTR_YRMN  UNION  ALL   \n";
        query +=" SELECT  ''  AS  MDM_CD  ,  '음산협'  AS  AVECLASS_CD_NM  ,  SUM(ORGDISTR_AMT)  AS  ORGDISTR_AMT  ,  DISTR_YRMN  ,  SUM(REALSUPP_AMT)  AS  REALSUPP_AMT  ,  SUM(ETC_AMT)  AS  ETC_AMT  FROM  (   \n";
        query +=" SELECT  A.MDM_CD  AS  MDM_CD,  MAX(B.AVECLASS_CD_NM)  as  AVECLASS_CD_NM,  SUM(C.DISTR_AMT)  +  NVL(SUM(REDISTR_AMT),0)  ORGDISTR_AMT,  CASE  B.AVECLASS_CD  WHEN  'CA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CB'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CC'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CG'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CH'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'CE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-1),'YYYY.MM')  WHEN  'DF'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DD'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'DE'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-2),'YYYY.MM')  WHEN  'AA'  THEN  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'YYYY.')  ||  TO_CHAR(ADD_MONTHS(TO_DATE(A.SUPP_YRMN,'YYYYMM'),-3),'Q')||'분기'  ELSE  ''  END  AS  DISTR_YRMN,  TRUNC(SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0))  AS  REALSUPP_AMT,  SUM(F.RIGHTPRES_DISTR_MAGP)  AS  ETC_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(ORGDISTR_AMT)  AS  ORGDISTR_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  =  'T0000003'  GROUP  BY  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  )  A,  FIDU.TENV_AVECLASSCD  B,  (   \n";
        query +=" SELECT  WRK_YRMN  as  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD  ,  NVL(SUM(DISTR_AMT),0)  AS  DISTR_AMT  FROM  FIDU.TDIS_DISTRPLANAMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  DISTR_AMT  >  0   \n";
        query +=" AND  WRK_YRMN  >  '201006'   \n";
        query +=" AND  ASS_GBN  =  'T0000003'  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  WRK_YRMN  )  C  ,   \n";
        query +=" (SELECT  RELS_YRMN  AS  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  NVL(SUM(SUSP_RELS_AMT),0)  AS  SUPPSUSP_RELS_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  RELS_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)   \n";
        query +=" AND  MB_CD  =  'T0000003'  GROUP  BY  RELS_YRMN,SUBSTR(MDM_CD,1,2)  )  D,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM  (RELS_AMT)  AS  PROD_RELS_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN  AS  SUPP_YRMN,  MDM_CD,  NVL(SUM(DISTR_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN,  MDM_CD  UNION  ALL   \n";
        query +=" SELECT  SUPP_YRMN,  MDM_CD,  NVL(SUM(OCC_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)   \n";
        query +=" AND  MB_CD  =  'T0000003'  GROUP  BY  SUPP_YRMN,  MDM_CD)  GROUP  BY  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  )  E,   \n";
        query +=" (SELECT  SUPP_YRMN,SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(RIGHTPRES_DISTR_MAGP)  as  RIGHTPRES_DISTR_MAGP  FROM  FIDU.TTAC_MDMCLASSDISTRAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  MB_CD  =  'T0000003'  GROUP  BY  SUBSTR(MDM_CD,1,2)  ,  SUPP_YRMN  )  F,   \n";
        query +=" (SELECT  WRK_YRMN  SUPP_YRMN,  SUBSTR(MDM_CD,1,2)  MDM_CD,  SUM(DISTR_AMT)  REDISTR_AMT  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)  GROUP  BY  WRK_YRMN,  SUBSTR(MDM_CD,1,2)  )  G  WHERE  A.MDM_CD  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  C.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  C.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  D.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  D.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  E.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  E.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  F.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  F.MDM_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  G.SUPP_YRMN(+)   \n";
        query +=" AND  A.MDM_CD  =  G.MDM_CD(+)  GROUP  BY  A.MDM_CD,  A.SUPP_YRMN,  B.AVECLASS_CD  HAVING  SUM(A.ORGDISTR_AMT)-NVL(SUM(D.SUPPSUSP_RELS_AMT),0)-NVL(SUM(E.PROD_RELS_AMT),0)  >  0  )  GROUP  BY  DISTR_YRMN  )  ORDER  BY  MDM_CD  ,  DISTR_YRMN ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 작품보류, 지급보류 해제,보류 금액
    public DOBJ CALLsearchMst06_2_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_2_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_2_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TRUNC(SUM(C.SUSP_AMT))  AS  SUSP_BAMT,  NVL(TRUNC(SUM(D.PRODSUSP_AMT)),0)  AS  SUSP_DAMT,  TRUNC(SUM(E.SUPPSUSP_RELS_AMT),0)  AS  SUSP_HJAMT,  TRUNC(SUM(F.PROD_RELS_AMT),0)  AS  SUSP_HBAMT,  SUM(B.DISTR_AMT)  +SUM(G.DISTR_AMT)  AS  TOT_BAMT,  TRUNC(SUM(A.ORGDISTR_AMT))  AS  TOT_JAMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN,  NVL(SUM(ORGDISTR_AMT),0)  AS  ORGDISTR_AMT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  WHERE  SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)  GROUP  BY  SUPP_YRMN  )  A,   \n";
        query +=" (SELECT  WRK_YRMN  AS  SUPP_YRMN,  NVL(SUM(DISTR_AMT),0)  AS  DISTR_AMT  FROM  FIDU.TDIS_DISTRPLANAMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  DISTR_AMT  >  0   \n";
        query +=" AND  WRK_YRMN  >  '201004'  GROUP  BY  WRK_YRMN  )  B,   \n";
        query +=" (SELECT  SUPP_YRMN,  NVL(SUM(SUSP_AMT),0)  AS  SUSP_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN  )  C,   \n";
        query +=" (SELECT  SUPP_YRMN,  SUM  (SUSP_AMT)  AS  PRODSUSP_AMT  FROM   \n";
        query +=" (SELECT  WRK_YRMN  AS  SUPP_YRMN,  NVL(SUM(DISTR_AMT),0)  AS  SUSP_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  WRK_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  WRK_YRMN  UNION  ALL   \n";
        query +=" SELECT  SUPP_YRMN,  NVL(SUM(OCC_AMT),0)  AS  SUSP_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN)  GROUP  BY  SUPP_YRMN  )  D,   \n";
        query +=" (SELECT  RELS_YRMN  AS  SUPP_YRMN,  NVL(SUM(SUSP_RELS_AMT),0)  AS  SUPPSUSP_RELS_AMT  FROM  FIDU.TTAC_MBSUPPSUSPAMT  WHERE  RELS_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  RELS_YRMN  )  E,   \n";
        query +=" (SELECT  SUPP_YRMN,  SUM  (RELS_AMT)  AS  PROD_RELS_AMT  FROM   \n";
        query +=" (SELECT  SUPP_YRMN  AS  SUPP_YRMN,  NVL(SUM(DISTR_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PRODSUPPSUSP  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN  UNION  ALL   \n";
        query +=" SELECT  SUPP_YRMN,  NVL(SUM(OCC_AMT),0)  AS  RELS_AMT  FROM  FIDU.TTAC_PLEDAMT  WHERE  SUPP_YRMN  =  SUBSTR  (:SUPP_YRMN,  1,  6)  GROUP  BY  SUPP_YRMN)  GROUP  BY  SUPP_YRMN  )  F,   \n";
        query +=" (SELECT  WRK_YRMN  AS  SUPP_YRMN,  NVL(SUM(DISTR_AMT),0)  AS  DISTR_AMT  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)  GROUP  BY  WRK_YRMN  )  G  WHERE  A.SUPP_YRMN  =  B.SUPP_YRMN(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  C.SUPP_YRMN(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  D.SUPP_YRMN(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  E.SUPP_YRMN(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  F.SUPP_YRMN(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  G.SUPP_YRMN(+)  HAVING  SUM(A.ORGDISTR_AMT)-SUM(NVL(E.SUPPSUSP_RELS_AMT,0))-SUM(NVL(F.PROD_RELS_AMT,0))  <>  0 ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 은행파일지급일자조회
    public DOBJ CALLsearchMst06_2_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_2_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_2_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  A.SUPP_DAY  FROM  FIDU.TTAC_BANKFILE  A  WHERE  A.SUPP_YRMN  =  :SUPP_YRMN ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 재분배금액
    public DOBJ CALLsearchMst06_2_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_2_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_2_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REDISTR_AMT,  B.REDISTR_AMT1  FROM  (   \n";
        query +=" SELECT  SUM(DISTR_AMT)  REDISTR_AMT  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  DISTR_AMT  <  0)  A,  (   \n";
        query +=" SELECT  SUM(DISTR_AMT)  REDISTR_AMT1  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  DISTR_AMT  >  0)  B ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$searchMst06_2
    //##**$$end
}
