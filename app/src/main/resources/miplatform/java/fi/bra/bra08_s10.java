
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra08_s10
{
    public bra08_s10()
    {
    }
    //##**$$gov_apprv_search
    /*
    */
    public DOBJ CTLgov_apprv_search(DOBJ dobj)
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
            dobj  = CALLgov_apprv_search_SEL1(Conn, dobj);           //  정부3.0데이터조회
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
    public DOBJ CTLgov_apprv_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLgov_apprv_search_SEL1(Conn, dobj);           //  정부3.0데이터조회
        return dobj;
    }
    // 정부3.0데이터조회
    public DOBJ CALLgov_apprv_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLgov_apprv_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgov_apprv_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT GBN, OPN_SVC_ID, OPN_SF_TEAM_CODE, NVL2(DCB_YMD,'폐업','개업') APV_DCB, MGT_NO, BRAN_CD, GIBU.GET_BRAN_NM(BRAN_CD) BRAN_NM, UPSO_CD, BPLC_NM, STAT_GBN, STAFF_CD, FIDU.GET_STAFF_NM(STAFF_CD) STAFF_NM, NVL(RDN_WHL_ADDR,SITE_WHL_ADDR) SITE_WHL_ADDR, SITE_POST_NO, RDN_WHL_ADDR, RDN_POST_NO, APV_PERM_YMD, DCB_YMD, LAT, LNG, REMAK  ";
        query +=" FROM (  ";
        query +=" SELECT 'A01' GBN, OPN_SVC_ID, OPN_SF_TEAM_CODE, MGT_NO, CASE WHEN STAT_GBN='1'  ";
        query +=" AND LENGTHB(REPLACE(REPLACE(BIGO,CHR(10),''),CHR(13),''))=8 THEN BIGO ELSE '' END UPSO_CD, STAT_GBN, X.BPLC_NM, GIBU.F_GET_TGOV_STAFF_CD(SITE_WHL_ADDR) STAFF_CD, GIBU.FT_GET_STAFF_BRAN_CD(GIBU.F_GET_TGOV_STAFF_CD(SITE_WHL_ADDR)) BRAN_CD, X.SITE_WHL_ADDR, X.SITE_POST_NO, X.RDN_WHL_ADDR, X.RDN_POST_NO, X.APV_PERM_YMD, X.DCB_YMD, X.LAT, X.LNG, CASE WHEN STAT_GBN <>'1' THEN BIGO WHEN STAT_GBN ='1'  ";
        query +=" AND LENGTHB(REPLACE(REPLACE(BIGO,CHR(10),''),CHR(13),''))<> 8 THEN BIGO ELSE '' END REMAK  ";
        query +=" FROM GIBU.TGOV_NOREBANG X) XX  ";
        query +=" WHERE 1=1  ";
        if( !BRAN_CD.equals("") )
        {
            query +=" AND XX.BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        }
        if( !STAFF_CD.equals("") )
        {
            query +=" AND XX.STAFF_CD =:STAFF_CD  ";
        }
        if( !GBN.equals("") )
        {
            query +=" AND GBN =:GBN  ";
        }
        query +=" UNION ALL  ";
        query +=" SELECT GBN, OPN_SVC_ID, OPN_SF_TEAM_CODE, NVL2(DCB_YMD,'폐업','개업') APV_DCB, MGT_NO, BRAN_CD, GIBU.GET_BRAN_NM(BRAN_CD) BRAN_NM, UPSO_CD, BPLC_NM, STAT_GBN, STAFF_CD, FIDU.GET_STAFF_NM(STAFF_CD) STAFF_NM, SITE_WHL_ADDR, SITE_POST_NO, RDN_WHL_ADDR, RDN_POST_NO, APV_PERM_YMD, DCB_YMD, LAT, LNG, REMAK  ";
        query +=" FROM (  ";
        query +=" SELECT 'A02' GBN, OPN_SVC_ID, OPN_SF_TEAM_CODE, MGT_NO, CASE WHEN STAT_GBN='1'  ";
        query +=" AND LENGTHB(REPLACE(REPLACE(BIGO,CHR(10),''),CHR(13),''))=8 THEN BIGO ELSE '' END UPSO_CD, STAT_GBN, X.BPLC_NM, GIBU.F_GET_TGOV_STAFF_CD(SITE_WHL_ADDR) STAFF_CD, GIBU.FT_GET_STAFF_BRAN_CD(GIBU.F_GET_TGOV_STAFF_CD(SITE_WHL_ADDR)) BRAN_CD, X.SITE_WHL_ADDR, X.SITE_POST_NO, X.RDN_WHL_ADDR, X.RDN_POST_NO, X.APV_PERM_YMD, X.DCB_YMD, X.LAT, X.LNG, CASE WHEN STAT_GBN <>'1' THEN BIGO WHEN STAT_GBN ='1'  ";
        query +=" AND LENGTHB(REPLACE(REPLACE(BIGO,CHR(10),''),CHR(13),''))<> 8 THEN BIGO ELSE '' END REMAK  ";
        query +=" FROM GIBU.TGOV_YUHEONG X) XX  ";
        query +=" WHERE 1=1  ";
        if( !BRAN_CD.equals("") )
        {
            query +=" AND XX.BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        }
        if( !STAFF_CD.equals("") )
        {
            query +=" AND XX.STAFF_CD =:STAFF_CD  ";
        }
        if( !GBN.equals("") )
        {
            query +=" AND GBN =:GBN  ";
        }
        query +=" UNION ALL  ";
        query +=" SELECT GBN, OPN_SVC_ID, OPN_SF_TEAM_CODE, NVL2(DCB_YMD,'폐업','개업') APV_DCB, MGT_NO, BRAN_CD, GIBU.GET_BRAN_NM(BRAN_CD) BRAN_NM, UPSO_CD, BPLC_NM, STAT_GBN, STAFF_CD, FIDU.GET_STAFF_NM(STAFF_CD) STAFF_NM, SITE_WHL_ADDR, SITE_POST_NO, RDN_WHL_ADDR, RDN_POST_NO, APV_PERM_YMD, DCB_YMD, LAT, LNG, REMAK  ";
        query +=" FROM (  ";
        query +=" SELECT 'A03' GBN, OPN_SVC_ID, OPN_SF_TEAM_CODE, MGT_NO, CASE WHEN STAT_GBN='1'  ";
        query +=" AND LENGTHB(REPLACE(REPLACE(BIGO,CHR(10),''),CHR(13),''))=8 THEN BIGO ELSE '' END UPSO_CD, STAT_GBN, X.BPLC_NM, GIBU.F_GET_TGOV_STAFF_CD(SITE_WHL_ADDR) STAFF_CD, GIBU.FT_GET_STAFF_BRAN_CD(GIBU.F_GET_TGOV_STAFF_CD(SITE_WHL_ADDR)) BRAN_CD, X.SITE_WHL_ADDR, X.SITE_POST_NO, X.RDN_WHL_ADDR, X.RDN_POST_NO, X.APV_PERM_YMD, X.DCB_YMD, X.LAT, X.LNG, CASE WHEN STAT_GBN <>'1' THEN BIGO WHEN STAT_GBN ='1'  ";
        query +=" AND LENGTHB(REPLACE(REPLACE(BIGO,CHR(10),''),CHR(13),''))<> 8 THEN BIGO ELSE '' END REMAK  ";
        query +=" FROM GIBU.TGOV_DANRAN X) XX  ";
        query +=" WHERE 1=1  ";
        if( !BRAN_CD.equals("") )
        {
            query +=" AND XX.BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        }
        if( !STAFF_CD.equals("") )
        {
            query +=" AND XX.STAFF_CD =:STAFF_CD  ";
        }
        if( !GBN.equals("") )
        {
            query +=" AND GBN =:GBN  ";
        }
        query +=" ORDER BY GBN, BPLC_NM  ";
        sobj.setSql(query);
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        }
        if(!GBN.equals(""))
        {
            sobj.setString("GBN", GBN);               //구분
        }
        if(!BRAN_CD.equals(""))
        {
            sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        }
        return sobj;
    }
    //##**$$gov_apprv_search
    //##**$$gov_history_search
    /*
    */
    public DOBJ CTLgov_history_search(DOBJ dobj)
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
            dobj  = CALLgov_history_search_SEL1(Conn, dobj);           //  저장된데이터 조회
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
    public DOBJ CTLgov_history_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLgov_history_search_SEL1(Conn, dobj);           //  저장된데이터 조회
        return dobj;
    }
    // 저장된데이터 조회
    public DOBJ CALLgov_history_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLgov_history_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgov_history_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CONFIRM_YRMN = dobj.getRetObject("S").getRecord().get("CONFIRM_YRMN");
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT X.GBN , GIBU.GET_BRAN_NM(X.BRAN_CD) BRAN_NM, X.UPSO_CD , X.BPLC_NM , X.SITE_WHL_ADDR, NVL2(X.DCB_YMD,'개업','폐업') APV_DCB, X.APV_PERM_YMD , X.DCB_YMD, X.STAT_GBN, X.REMAK  ";
        query +=" FROM GIBU.TGOV_INTE_HISTORY X  ";
        query +=" WHERE TO_CHAR(X.CONFIRM_DATE,'YYYYMM') = :CONFIRM_YRMN  ";
        if( !BRAN_CD.equals("") )
        {
            query +=" AND X.BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        }
        if( !STAFF_CD.equals("") )
        {
            query +=" AND X.STAFF_CD =:STAFF_CD  ";
        }
        if( !GBN.equals("") )
        {
            query +=" AND X.GBN =:GBN  ";
        }
        query +=" ORDER BY GBN, BPLC_NM  ";
        sobj.setSql(query);
        sobj.setString("CONFIRM_YRMN", CONFIRM_YRMN);
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        }
        if(!GBN.equals(""))
        {
            sobj.setString("GBN", GBN);               //구분
        }
        if(!BRAN_CD.equals(""))
        {
            sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        }
        return sobj;
    }
    //##**$$gov_history_search
    //##**$$gov_end_save
    /*
    */
    public DOBJ CTLgov_end_save(DOBJ dobj)
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
            dobj  = CALLgov_end_save_XIUD2(Conn, dobj);           //  히스토리삭제
            dobj  = CALLgov_end_save_SEL3(Conn, dobj);           //  저장데이터조회
            dobj  = CALLgov_end_save_INS4(Conn, dobj);           //  마감데이터저장
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
    public DOBJ CTLgov_end_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLgov_end_save_XIUD2(Conn, dobj);           //  히스토리삭제
        dobj  = CALLgov_end_save_SEL3(Conn, dobj);           //  저장데이터조회
        dobj  = CALLgov_end_save_INS4(Conn, dobj);           //  마감데이터저장
        return dobj;
    }
    // 히스토리삭제
    public DOBJ CALLgov_end_save_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLgov_end_save_XIUD2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgov_end_save_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String SYSDATE ="";
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM  \n";
        query +=" GIBU.TGOV_INTE_HISTORY  \n";
        query +=" WHERE TO_CHAR(CONFIRM_DATE,'YYYYMM') = TO_CHAR(SYSDATE,'YYYYMM')";
        sobj.setSql(query);
        return sobj;
    }
    // 저장데이터조회
    public DOBJ CALLgov_end_save_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLgov_end_save_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgov_end_save_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  'A01'  GBN,  OPN_SVC_ID,  OPN_SF_TEAM_CODE,  MGT_NO,  CASE  WHEN  STAT_GBN='1'   \n";
        query +=" AND  LENGTHB(REPLACE(REPLACE(BIGO,CHR(10),''),CHR(13),''))=8  THEN  BIGO  ELSE  ''  END  UPSO_CD,  STAT_GBN,  X.BPLC_NM,  GIBU.F_GET_TGOV_STAFF_CD(SITE_WHL_ADDR)  STAFF_CD,  GIBU.FT_GET_STAFF_BRAN_CD(GIBU.F_GET_TGOV_STAFF_CD(SITE_WHL_ADDR))  BRAN_CD,  X.SITE_WHL_ADDR,  X.SITE_POST_NO,  X.RDN_WHL_ADDR,  X.RDN_POST_NO,  X.APV_PERM_YMD,  X.DCB_YMD,  X.LAT,  X.LNG,  CASE  WHEN  STAT_GBN  <>'1'  THEN  BIGO  WHEN  STAT_GBN  ='1'   \n";
        query +=" AND  LENGTHB(REPLACE(REPLACE(BIGO,CHR(10),''),CHR(13),''))<>  8  THEN  BIGO  ELSE  ''  END  REMAK  FROM  GIBU.TGOV_NOREBANG  X  UNION  ALL   \n";
        query +=" SELECT  'A02'  GBN,  OPN_SVC_ID,  OPN_SF_TEAM_CODE,  MGT_NO,  CASE  WHEN  STAT_GBN='1'   \n";
        query +=" AND  LENGTHB(REPLACE(REPLACE(BIGO,CHR(10),''),CHR(13),''))=8  THEN  BIGO  ELSE  ''  END  UPSO_CD,  STAT_GBN,  X.BPLC_NM,  GIBU.F_GET_TGOV_STAFF_CD(SITE_WHL_ADDR)  STAFF_CD,  GIBU.FT_GET_STAFF_BRAN_CD(GIBU.F_GET_TGOV_STAFF_CD(SITE_WHL_ADDR))  BRAN_CD,  X.SITE_WHL_ADDR,  X.SITE_POST_NO,  X.RDN_WHL_ADDR,  X.RDN_POST_NO,  X.APV_PERM_YMD,  X.DCB_YMD,  X.LAT,  X.LNG,  CASE  WHEN  STAT_GBN  <>'1'  THEN  BIGO  WHEN  STAT_GBN  ='1'   \n";
        query +=" AND  LENGTHB(REPLACE(REPLACE(BIGO,CHR(10),''),CHR(13),''))<>  8  THEN  BIGO  ELSE  ''  END  REMAK  FROM  GIBU.TGOV_YUHEONG  X  UNION  ALL   \n";
        query +=" SELECT  'A03'  GBN,  OPN_SVC_ID,  OPN_SF_TEAM_CODE,  MGT_NO,  CASE  WHEN  STAT_GBN='1'   \n";
        query +=" AND  LENGTHB(REPLACE(REPLACE(BIGO,CHR(10),''),CHR(13),''))=8  THEN  BIGO  ELSE  ''  END  UPSO_CD,  STAT_GBN,  X.BPLC_NM,  GIBU.F_GET_TGOV_STAFF_CD(SITE_WHL_ADDR)  STAFF_CD,  GIBU.FT_GET_STAFF_BRAN_CD(GIBU.F_GET_TGOV_STAFF_CD(SITE_WHL_ADDR))  BRAN_CD,  X.SITE_WHL_ADDR,  X.SITE_POST_NO,  X.RDN_WHL_ADDR,  X.RDN_POST_NO,  X.APV_PERM_YMD,  X.DCB_YMD,  X.LAT,  X.LNG,  CASE  WHEN  STAT_GBN  <>'1'  THEN  BIGO  WHEN  STAT_GBN  ='1'   \n";
        query +=" AND  LENGTHB(REPLACE(REPLACE(BIGO,CHR(10),''),CHR(13),''))<>  8  THEN  BIGO  ELSE  ''  END  REMAK  FROM  GIBU.TGOV_DANRAN  X  ORDER  BY  GBN,BPLC_NM ";
        sobj.setSql(query);
        return sobj;
    }
    // 마감데이터저장
    public DOBJ CALLgov_end_save_INS4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS4");
        VOBJ dvobj = dobj.getRetObject("SEL3");           //저장데이터조회에서 생성시킨 OBJECT입니다.(CALLgov_end_save_SEL3)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLgov_end_save_INS4(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS4") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgov_end_save_INS4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CONFIRM_DATE ="";        //확인 일자
        String INS_DATE ="";        //등록 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   STAT_GBN = dvobj.getRecord().get("STAT_GBN");   //처리상태
        String   LNG = dvobj.getRecord().get("LNG");   //경도
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   RDN_WHL_ADDR = dvobj.getRecord().get("RDN_WHL_ADDR");
        String   DCB_YMD = dvobj.getRecord().get("DCB_YMD");
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   SITE_POST_NO = dvobj.getRecord().get("SITE_POST_NO");
        String   OPN_SF_TEAM_CODE = dvobj.getRecord().get("OPN_SF_TEAM_CODE");
        String   SITE_WHL_ADDR = dvobj.getRecord().get("SITE_WHL_ADDR");
        String   OPN_SVC_ID = dvobj.getRecord().get("OPN_SVC_ID");
        String   APV_PERM_YMD = dvobj.getRecord().get("APV_PERM_YMD");
        String   RDN_POST_NO = dvobj.getRecord().get("RDN_POST_NO");
        String   GBN = dvobj.getRecord().get("GBN");   //구분
        String   BPLC_NM = dvobj.getRecord().get("BPLC_NM");   //업소명
        String   MGT_NO = dvobj.getRecord().get("MGT_NO");
        String   LAT = dvobj.getRecord().get("LAT");   //위도
        String   CONFIRM_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //계산 일자
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TGOV_INTE_HISTORY (CONFIRM_ID, LAT, INSPRES_ID, MGT_NO, BPLC_NM, GBN, RDN_POST_NO, APV_PERM_YMD, OPN_SVC_ID, SITE_WHL_ADDR, OPN_SF_TEAM_CODE, SITE_POST_NO, INS_DATE, STAFF_CD, DCB_YMD, RDN_WHL_ADDR, UPSO_CD, LNG, STAT_GBN, BRAN_CD, REMAK, CONFIRM_DATE)  \n";
        query +=" values(:CONFIRM_ID , :LAT , :INSPRES_ID , :MGT_NO , :BPLC_NM , :GBN , :RDN_POST_NO , :APV_PERM_YMD , :OPN_SVC_ID , :SITE_WHL_ADDR , :OPN_SF_TEAM_CODE , :SITE_POST_NO , SYSDATE, :STAFF_CD , :DCB_YMD , :RDN_WHL_ADDR , :UPSO_CD , :LNG , :STAT_GBN , :BRAN_CD , :REMAK , SYSDATE)";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("STAT_GBN", STAT_GBN);               //처리상태
        sobj.setString("LNG", LNG);               //경도
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("RDN_WHL_ADDR", RDN_WHL_ADDR);
        sobj.setString("DCB_YMD", DCB_YMD);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("SITE_POST_NO", SITE_POST_NO);
        sobj.setString("OPN_SF_TEAM_CODE", OPN_SF_TEAM_CODE);
        sobj.setString("SITE_WHL_ADDR", SITE_WHL_ADDR);
        sobj.setString("OPN_SVC_ID", OPN_SVC_ID);
        sobj.setString("APV_PERM_YMD", APV_PERM_YMD);
        sobj.setString("RDN_POST_NO", RDN_POST_NO);
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("BPLC_NM", BPLC_NM);               //업소명
        sobj.setString("MGT_NO", MGT_NO);
        sobj.setString("LAT", LAT);               //위도
        sobj.setString("CONFIRM_ID", CONFIRM_ID);               //계산 일자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    //##**$$gov_end_save
    //##**$$gov_history_list
    /*
    */
    public DOBJ CTLgov_history_list(DOBJ dobj)
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
            dobj  = CALLgov_history_list_SEL1(Conn, dobj);           //  이력조회
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
    public DOBJ CTLgov_history_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLgov_history_list_SEL1(Conn, dobj);           //  이력조회
        return dobj;
    }
    // 이력조회
    public DOBJ CALLgov_history_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLgov_history_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgov_history_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  TO_CHAR(CONFIRM_DATE,'YYYYMM')  CONFIRM_YRMN  ,CONFIRM_ID  ,FIDU.GET_STAFF_NM(CONFIRM_ID)  INSPRES_NM  FROM  GIBU.TGOV_INTE_HISTORY  ORDER  BY  TO_CHAR(CONFIRM_DATE,'YYYYMM')  DESC ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$gov_history_list
    //##**$$gov_update
    /*
    */
    public DOBJ CTLgov_update(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN_1").equals("1"))
            {
                if( dobj.getRetObject("S").getRecord().get("GBN_2").equals("A01"))
                {
                    dobj  = CALLgov_update_UPD8(Conn, dobj);           //  노래방 수정
                }
                else if( dobj.getRetObject("S").getRecord().get("GBN_2").equals("A02"))
                {
                    dobj  = CALLgov_update_UPD9(Conn, dobj);           //  유흥 수정
                }
                else if( dobj.getRetObject("S").getRecord().get("GBN_2").equals("A03"))
                {
                    dobj  = CALLgov_update_UPD10(Conn, dobj);           //  단란 수정
                }
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN_1").equals("2"))
            {
                dobj  = CALLgov_update_UPD11(Conn, dobj);           //  마감데이터수정
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
    public DOBJ CTLgov_update( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN_1").equals("1"))
        {
            if( dobj.getRetObject("S").getRecord().get("GBN_2").equals("A01"))
            {
                dobj  = CALLgov_update_UPD8(Conn, dobj);           //  노래방 수정
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN_2").equals("A02"))
            {
                dobj  = CALLgov_update_UPD9(Conn, dobj);           //  유흥 수정
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN_2").equals("A03"))
            {
                dobj  = CALLgov_update_UPD10(Conn, dobj);           //  단란 수정
            }
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN_1").equals("2"))
        {
            dobj  = CALLgov_update_UPD11(Conn, dobj);           //  마감데이터수정
        }
        return dobj;
    }
    // 노래방 수정
    public DOBJ CALLgov_update_UPD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLgov_update_UPD8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgov_update_UPD8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   OPN_SF_TEAM_CODE = dvobj.getRecord().get("OPN_SF_TEAM_CODE");
        String   STAT_GBN = dvobj.getRecord().get("STAT_GBN");   //처리상태
        String   OPN_SVC_ID = dvobj.getRecord().get("OPN_SVC_ID");
        String   MGT_NO = dvobj.getRecord().get("MGT_NO");
        String   BIGO = dobj.getRetObject("S").getRecord().get("REMAK");   //비고사항
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update  \n";
        query +=" GIBU.TGOV_NOREBANG  \n";
        query +=" set BIGO=:BIGO , STAT_GBN=:STAT_GBN  \n";
        query +=" where MGT_NO=:MGT_NO  \n";
        query +=" and OPN_SVC_ID=:OPN_SVC_ID  \n";
        query +=" and OPN_SF_TEAM_CODE=:OPN_SF_TEAM_CODE";
        sobj.setSql(query);
        sobj.setString("OPN_SF_TEAM_CODE", OPN_SF_TEAM_CODE);
        sobj.setString("STAT_GBN", STAT_GBN);               //처리상태
        sobj.setString("OPN_SVC_ID", OPN_SVC_ID);
        sobj.setString("MGT_NO", MGT_NO);
        sobj.setString("BIGO", BIGO);               //비고사항
        return sobj;
    }
    // 유흥 수정
    public DOBJ CALLgov_update_UPD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLgov_update_UPD9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgov_update_UPD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   OPN_SF_TEAM_CODE = dvobj.getRecord().get("OPN_SF_TEAM_CODE");
        String   STAT_GBN = dvobj.getRecord().get("STAT_GBN");   //처리상태
        String   OPN_SVC_ID = dvobj.getRecord().get("OPN_SVC_ID");
        String   MGT_NO = dvobj.getRecord().get("MGT_NO");
        String   BIGO = dobj.getRetObject("S").getRecord().get("REMAK");   //비고사항
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TGOV_YUHEONG  \n";
        query +=" set BIGO=:BIGO , STAT_GBN=:STAT_GBN  \n";
        query +=" where MGT_NO=:MGT_NO  \n";
        query +=" and OPN_SVC_ID=:OPN_SVC_ID  \n";
        query +=" and OPN_SF_TEAM_CODE=:OPN_SF_TEAM_CODE";
        sobj.setSql(query);
        sobj.setString("OPN_SF_TEAM_CODE", OPN_SF_TEAM_CODE);
        sobj.setString("STAT_GBN", STAT_GBN);               //처리상태
        sobj.setString("OPN_SVC_ID", OPN_SVC_ID);
        sobj.setString("MGT_NO", MGT_NO);
        sobj.setString("BIGO", BIGO);               //비고사항
        return sobj;
    }
    // 단란 수정
    public DOBJ CALLgov_update_UPD10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLgov_update_UPD10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD10") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgov_update_UPD10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   OPN_SF_TEAM_CODE = dvobj.getRecord().get("OPN_SF_TEAM_CODE");
        String   STAT_GBN = dvobj.getRecord().get("STAT_GBN");   //처리상태
        String   OPN_SVC_ID = dvobj.getRecord().get("OPN_SVC_ID");
        String   MGT_NO = dvobj.getRecord().get("MGT_NO");
        String   BIGO = dobj.getRetObject("S").getRecord().get("REMAK");   //비고사항
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TGOV_DANRAN  \n";
        query +=" set BIGO=:BIGO , STAT_GBN=:STAT_GBN  \n";
        query +=" where MGT_NO=:MGT_NO  \n";
        query +=" and OPN_SVC_ID=:OPN_SVC_ID  \n";
        query +=" and OPN_SF_TEAM_CODE=:OPN_SF_TEAM_CODE";
        sobj.setSql(query);
        sobj.setString("OPN_SF_TEAM_CODE", OPN_SF_TEAM_CODE);
        sobj.setString("STAT_GBN", STAT_GBN);               //처리상태
        sobj.setString("OPN_SVC_ID", OPN_SVC_ID);
        sobj.setString("MGT_NO", MGT_NO);
        sobj.setString("BIGO", BIGO);               //비고사항
        return sobj;
    }
    // 마감데이터수정
    public DOBJ CALLgov_update_UPD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLgov_update_UPD11(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD11") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgov_update_UPD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   OPN_SF_TEAM_CODE = dvobj.getRecord().get("OPN_SF_TEAM_CODE");
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   STAT_GBN = dvobj.getRecord().get("STAT_GBN");   //처리상태
        String   OPN_SVC_ID = dvobj.getRecord().get("OPN_SVC_ID");
        String   MGT_NO = dvobj.getRecord().get("MGT_NO");
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update  \n";
        query +=" GIBU.TGOV_INTE_HISTORY  \n";
        query +=" set STAT_GBN=:STAT_GBN , REMAK=:REMAK  \n";
        query +=" where MGT_NO=:MGT_NO  \n";
        query +=" and OPN_SVC_ID=:OPN_SVC_ID  \n";
        query +=" and OPN_SF_TEAM_CODE=:OPN_SF_TEAM_CODE";
        sobj.setSql(query);
        sobj.setString("OPN_SF_TEAM_CODE", OPN_SF_TEAM_CODE);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("STAT_GBN", STAT_GBN);               //처리상태
        sobj.setString("OPN_SVC_ID", OPN_SVC_ID);
        sobj.setString("MGT_NO", MGT_NO);
        return sobj;
    }
    //##**$$gov_update
    //##**$$end
}
