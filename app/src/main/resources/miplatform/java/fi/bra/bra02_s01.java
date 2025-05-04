
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra02_s01
{
    public bra02_s01()
    {
    }
    //##**$$EventID01
    /*
    */
    public DOBJ CTLcom_search(DOBJ dobj)
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
            dobj  = CALLEventID01_SEL1(Conn, dobj);           //  사용승인
            dobj  = CALLEventID01_SEL2(Conn, dobj);           //  신청자정보
            dobj  = CALLEventID01_SEL3(Conn, dobj);           //  사용저작물
            dobj  = CALLEventID01_SEL4(Conn, dobj);           //  사용청구입금
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
    public DOBJ CTLcom_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLEventID01_SEL1(Conn, dobj);           //  사용승인
        dobj  = CALLEventID01_SEL2(Conn, dobj);           //  신청자정보
        dobj  = CALLEventID01_SEL3(Conn, dobj);           //  사용저작물
        dobj  = CALLEventID01_SEL4(Conn, dobj);           //  사용청구입금
        return dobj;
    }
    // 사용승인
    // 사용승인
    public DOBJ CALLEventID01_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLEventID01_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID01_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" select  APPRV_NUM,    MB_ID,    MDM_CD,    BRAN_CD,    BSCON_CD,    SITE_NM,    TTL,    USE_USAG,    USE_KND,    USE_FORM_ETC_CTENT,    USE_TYPE,    USE_METH,    USE_LOC,    ABR_USE_CTENT,    SALE_KND,    FTR_KND,    FTR_KND_ETC_CTENT,    USE_FREQ,    RTAL_AMT,    SEAT_SALE_RATE,    USE_LOCN_AREA,    USE_CTENT,    USE_LOCN_CTENT,    USETRM_START_DAY,    USETRM_END_DAY,    TOT_MUSICUSE_TM,    APPRVCDTN_GBN,    USEAPPRVPROC_STAT,    CONTRCCLSN_DAY,    RSR_START_DAY,    RSR_END_DAY,    RSR_REAS,    TOT_REBIO_TM,    APPRVDAPVL_REAS,    IFMNT_GBN,    UDTKDEPT_CD,    UDTKPRES_ID,    INSPRES_ID,    INS_DATE,    MODPRES_ID,    MOD_DATE  from  fidu.TLEV_USEAPPRV ";
        sobj.setSql(query);
        return sobj;
    }
    // 신청자정보
    // 신청자정보
    public DOBJ CALLEventID01_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLEventID01_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID01_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" select  APPRV_NUM,    MNG_NUM,    APPTNPRES_GBN,    FNM_NM,    INS_NUM,    REPPRES_NM,    POST_NUM,    ADDR,    ADDR_DETED,    PHON_NUM,    FAX_NUM,    CP_NUM,    EMAIL_ADDR,    HMPG_ADDR,    INSPRES_ID,    INS_DATE,    MODPRES_ID,    MOD_DATE  from  fidu.tlev_apptnpresinfo ";
        sobj.setSql(query);
        return sobj;
    }
    // 사용저작물
    // 사용저작물
    public DOBJ CALLEventID01_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLEventID01_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID01_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" select  APPRV_NUM,    MNG_NUM,    PROD_CD,    PROD_NM,    SINA_NM,    YETINS_YN,    SECT_CD,    LRC_CTENT,    PERFM_TM,    RMK_YN,    RMK_FORM,    RMK_FORM_ETC_CTENT,    COUR_YN,    SWR_MB_CD,    SWR_NM,    SWR_RESIINS_NUM,    CPS_MB_CD,    CPS_NM,    CPS_RESIINS_NUM,    SUCST_AMT,    TUNEUNCO_AMT,    REMAK,    INSPRES_ID,    INS_DATE,    MODPRES_ID,    MOD_DATE  from  fidu.TLEV_USEWRTG ";
        sobj.setSql(query);
        return sobj;
    }
    // 사용청구입금
    // 사용청구입금
    public DOBJ CALLEventID01_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLEventID01_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID01_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  APPRV_NUM,    DEMD_NUM,    DEMD_GBN,    DEMD_YRMN,    UDTKSTAFF_CD,    DEMD_AMT,    ADDT_AMT,    ADDT_CLOS_CTENT,    YETFREQ_AMT,    FREQFREQ_AMT,    REPT_DAY,    REPT_GBN,    REPT_BANK_CD,    REPT_ACCN_NUM,    REPT_AMT,    COMPL_YN,    REPT_ACK_DAY,    REPT_DECPRES_ID,    REMAK,    INSPRES_ID,    INS_DATE,    MODPRES_ID,    MOD_DATE  FROM  FIDU.TLEV_USEDEMDREPT ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$EventID01
    //##**$$end
}
