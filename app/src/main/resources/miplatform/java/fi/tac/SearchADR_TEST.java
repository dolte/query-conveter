
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class SearchADR_TEST
{
    public SearchADR_TEST()
    {
    }
    //##**$$SearchADR_TEST
    /*
    */
    public DOBJ CTLSearchADR_TEST(DOBJ dobj)
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
            dobj  = CALLSearchADR_TEST_SEL1(Conn, dobj);           //  지급내역서주소
            dobj  = CALLSearchADR_TEST_SEL2(Conn, dobj);           //  회원별분배통합명부list
            dobj  = CALLSearchADR_TEST_SEL3(Conn, dobj);           //  회원별분배통합명부sum
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
    public DOBJ CTLSearchADR_TEST( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLSearchADR_TEST_SEL1(Conn, dobj);           //  지급내역서주소
        dobj  = CALLSearchADR_TEST_SEL2(Conn, dobj);           //  회원별분배통합명부list
        dobj  = CALLSearchADR_TEST_SEL3(Conn, dobj);           //  회원별분배통합명부sum
        return dobj;
    }
    // 지급내역서주소
    public DOBJ CALLSearchADR_TEST_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLSearchADR_TEST_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLSearchADR_TEST_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //회원코드
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  POST_RCPT_NM,ADDR,  ADDR_DETED,  POST_NUM,  MB_CD  FROM  FIDU.TMEM_ADBK  WHERE  1=1   \n";
        query +=" AND  MB_CD  BETWEEN  :FRMB_CD   \n";
        query +=" AND  :TOMB_CD   \n";
        query +=" AND  MNG_NUM  =  1   \n";
        query +=" AND  ADDR_GBN  =  1  ORDER  BY  MB_CD ";
        sobj.setSql(query);
        sobj.setString("FRMB_CD", FRMB_CD);               //회원코드
        sobj.setString("TOMB_CD", TOMB_CD);               //회원코드
        return sobj;
    }
    // 회원별분배통합명부list
    public DOBJ CALLSearchADR_TEST_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLSearchADR_TEST_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLSearchADR_TEST_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //회원코드
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  FIDU.GET_MB_NM  (F.RIGHTPRES_MB_CD)  AS  RIGHTPRES_NM,  FIDU.GET_SN_NM(F.RIGHTPRES_MB_CD)  AS  SN_NM,  C.APPRV_NUM,   \n";
        query +=" (SELECT  FNM_NM  FROM  FIDU.TLEV_APPTNPRESINFO  AA  WHERE  AA.APPRV_NUM  =  C.APPRV_NUM   \n";
        query +=" AND  AA.APPTNPRES_GBN  =  '1')  AS  FNM_NM,  C.USE_TTL,  FIDU.GET_PROD_NM(F.PROD_CD)  AS  PROD_NM,  C.IFMNT_GBN,  FIDU.GET_CODE_NM(  '00155'  ,  NVL(C.IFMNT_GBN,1)  )  AS  IFMNT_GBN_NM,  DECODE(F.WT_GBN,'A',F.DISTR_AMT)  AS  OCC_AMT_A,  DECODE(F.WT_GBN,'C',F.DISTR_AMT)  AS  OCC_AMT_C,  DECODE(F.WT_GBN,'AR',F.DISTR_AMT)  AS  OCC_AMT_AR,  D.SVC_CD,  FIDU.GET_SVC_NM(D.SVC_CD)  AS  SVC_NM,  D.QTY_PNUM,  C.MDM_CD,  FIDU.GET_MDM_NM(C.MDM_CD)  AS  MDM_NM  FROM  FIDU.TLEV_USEAPPRV  C,  FIDU.TLEV_CLRREC  D,  FIDU.TDIS_DISTRPLANAMT  E,  FIDU.TDIS_DISTR  F  WHERE  C.APPRV_NUM  =  D.APPRV_NUM   \n";
        query +=" AND  C.APPRV_NUM  =  E.APPRV_NUM   \n";
        query +=" AND  D.CLR_NUM  =  E.CLR_NUM   \n";
        query +=" AND  C.CONTRCCLSN_DAY  IS  NOT  NULL   \n";
        query +=" AND  F.DISTR_YRMN  =  E.DISTR_YRMN   \n";
        query +=" AND  F.MDM_CD  =  E.MDM_CD   \n";
        query +=" AND  F.SVC_CD  =  E.SVC_CD   \n";
        query +=" AND  F.BSCON_CD  =  E.BSCON_CD   \n";
        query +=" AND  F.WRK_YRMN  =  E.WRK_YRMN   \n";
        query +=" AND  F.DISTR_NUM  =  E.DISTR_NUM   \n";
        query +=" AND  SUBSTR(F.MDM_CD,1,1)  IN  ('C','D')   \n";
        query +=" AND  NVL(E.DISTR_AMT,0)  >  0   \n";
        query +=" AND  F.RIGHTPRES_MB_CD  BETWEEN  :FRMB_CD   \n";
        query +=" AND  :TOMB_CD   \n";
        query +=" AND  E.DISTR_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6) ";
        sobj.setSql(query);
        sobj.setString("FRMB_CD", FRMB_CD);               //회원코드
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("TOMB_CD", TOMB_CD);               //회원코드
        return sobj;
    }
    // 회원별분배통합명부sum
    public DOBJ CALLSearchADR_TEST_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLSearchADR_TEST_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLSearchADR_TEST_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //회원코드
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(DECODE(WT_GBN,'A',OCC_AMT_CD_SUM))  AS  OCC_AMT_CD_SUM_A,  MAX(DECODE(WT_GBN,'C',OCC_AMT_CD_SUM))  AS  OCC_AMT_CD_SUM_C,  MAX(DECODE(WT_GBN,'A',OCC_AMT_CH_SUM))  AS  OCC_AMT_CH_SUM_A,  MAX(DECODE(WT_GBN,'C',OCC_AMT_CH_SUM))  AS  OCC_AMT_CH_SUM_C,  MAX(DECODE(WT_GBN,'A',OCC_AMT_DA_SUM))  AS  OCC_AMT_DA_SUM_A,  MAX(DECODE(WT_GBN,'C',OCC_AMT_DA_SUM))  AS  OCC_AMT_DA_SUM_C,  MAX(DECODE(WT_GBN,'A',OCC_AMT_CE_SUM))  AS  OCC_AMT_CE_SUM_A,  MAX(DECODE(WT_GBN,'C',OCC_AMT_CE_SUM))  AS  OCC_AMT_CE_SUM_C,  MAX(DECODE(WT_GBN,'A',OCC_AMT_ZZ_SUM))  AS  OCC_AMT_ZZ_SUM_A,  MAX(DECODE(WT_GBN,'C',OCC_AMT_ZZ_SUM))  AS  OCC_AMT_ZZ_SUM_C,  MAX(DECODE(WT_GBN,'A',OCC_AMT_CA_SUM))  AS  OCC_AMT_CA_SUM_A,  MAX(DECODE(WT_GBN,'C',OCC_AMT_CA_SUM))  AS  OCC_AMT_CA_SUM_C,  MAX(DECODE(WT_GBN,'A',OCC_AMT_CG_SUM))  AS  OCC_AMT_CG_SUM_A,  MAX(DECODE(WT_GBN,'C',OCC_AMT_CG_SUM))  AS  OCC_AMT_CG_SUM_C,  MAX(DECODE(WT_GBN,'A',OCC_AMT_CC_SUM))  AS  OCC_AMT_CC_SUM_A,  MAX(DECODE(WT_GBN,'C',OCC_AMT_CC_SUM))  AS  OCC_AMT_CC_SUM_C  FROM   \n";
        query +=" (SELECT  MAX(DECODE(AVECLASS_CD,'CD',  OCC_AMT))  AS  OCC_AMT_CD_SUM,  MAX(DECODE(AVECLASS_CD,'CH',  OCC_AMT))  AS  OCC_AMT_CH_SUM,  MAX(DECODE(AVECLASS_CD,'DA',  OCC_AMT))  AS  OCC_AMT_DA_SUM,  MAX(DECODE(AVECLASS_CD,'CE',  OCC_AMT))  AS  OCC_AMT_CE_SUM,  MAX(DECODE(AVECLASS_CD,'ZZ',  OCC_AMT))  AS  OCC_AMT_ZZ_SUM,  MAX(DECODE(AVECLASS_CD,'CA',  OCC_AMT))  AS  OCC_AMT_CA_SUM,  MAX(DECODE(AVECLASS_CD,'CG',  OCC_AMT))  AS  OCC_AMT_CG_SUM,  MAX(DECODE(AVECLASS_CD,'CC',  OCC_AMT))  AS  OCC_AMT_CC_SUM,  WT_GBN  FROM   \n";
        query +=" (SELECT  E.AVECLASS_CD,  E.AVECLASS_CD_NM,  G.WT_GBN,  SUM(G.DISTR_AMT)  AS  OCC_AMT  FROM  FIDU.TLEV_USEAPPRV  C,  FIDU.TLEV_CLRREC  D,  FIDU.TENV_AVECLASSCD  E,  FIDU.TDIS_DISTRPLANAMT  F,  FIDU.TDIS_DISTR  G  WHERE  C.APPRV_NUM  =  D.APPRV_NUM   \n";
        query +=" AND  C.APPRV_NUM  =  F.APPRV_NUM   \n";
        query +=" AND  F.CLR_NUM  =  D.CLR_NUM   \n";
        query +=" AND  SUBSTR(C.MDM_CD,1,2)  =  E.AVECLASS_CD   \n";
        query +=" AND  C.CONTRCCLSN_DAY  IS  NOT  NULL   \n";
        query +=" AND  SUBSTR(F.MDM_CD,1,1)  IN  ('C','D')   \n";
        query +=" AND  F.DISTR_YRMN  =  G.DISTR_YRMN   \n";
        query +=" AND  F.MDM_CD  =  G.MDM_CD   \n";
        query +=" AND  F.SVC_CD  =  G.SVC_CD   \n";
        query +=" AND  F.BSCON_CD  =  G.BSCON_CD   \n";
        query +=" AND  F.WRK_YRMN  =  G.WRK_YRMN   \n";
        query +=" AND  F.DISTR_NUM  =  G.DISTR_NUM   \n";
        query +=" AND  G.RIGHTPRES_MB_CD  BETWEEN  :FRMB_CD   \n";
        query +=" AND  :TOMB_CD   \n";
        query +=" AND  F.DISTR_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)  GROUP  BY  E.AVECLASS_CD,E.AVECLASS_CD_NM,G.WT_GBN  )  GROUP  BY  WT_GBN  ) ";
        sobj.setSql(query);
        sobj.setString("FRMB_CD", FRMB_CD);               //회원코드
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("TOMB_CD", TOMB_CD);               //회원코드
        return sobj;
    }
    //##**$$SearchADR_TEST
    //##**$$end
}
