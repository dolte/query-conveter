
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s22
{
    public bra01_s22()
    {
    }
    //##**$$save_log_data
    /*
    */
    public DOBJ CTLsave_log_data(DOBJ dobj)
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
            dobj  = CALLsave_log_data_DEL22(Conn, dobj);           //  온오프반주기정보삭제
            dobj  = CALLsave_log_data_MPD24(Conn, dobj);           //  반주기온오프정보
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLsave_log_data_UPD27(Conn, dobj);           //  업소의 수집불가반주기 수정
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
    public DOBJ CTLsave_log_data( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_log_data_DEL22(Conn, dobj);           //  온오프반주기정보삭제
        dobj  = CALLsave_log_data_MPD24(Conn, dobj);           //  반주기온오프정보
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLsave_log_data_UPD27(Conn, dobj);           //  업소의 수집불가반주기 수정
        return dobj;
    }
    // 온오프반주기정보삭제
    // 해당 업소의 기존 온오프 반주기 정보를 삭제한다.
    public DOBJ CALLsave_log_data_DEL22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL22");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_log_data_DEL22(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL22") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_log_data_DEL22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_ONOFF_INFO  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 반주기온오프정보
    public DOBJ CALLsave_log_data_MPD24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD24");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("MPD24");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("UPSO_CD").equals(""))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_log_data_INS16(Conn, dobj);           //  온오프반주기정보등록
                dobj  = CALLsave_log_data_SEL9(Conn, dobj);           //  온오프정보조회
                if( dobj.getRetObject("SEL9").getRecordCnt() == 1)
                {
                    dobj  = CALLsave_log_data_UPD10(Conn, dobj);           //  업소 온오프정보수정
                }
                else if( dobj.getRetObject("SEL9").getRecordCnt() > 1)
                {
                    dobj  = CALLsave_log_data_UPD11(Conn, dobj);           //  업소 온오프정보수정
                }
                else
                {
                    dobj  = CALLsave_log_data_UPD12(Conn, dobj);           //  업소 온오프정보수정
                }
            }
        }
        return dobj;
    }
    // 온오프반주기정보등록
    // 새로운 온오프 반주기 정보를 등록한다.
    public DOBJ CALLsave_log_data_INS16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS16");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_log_data_INS16(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS16") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_log_data_INS16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   ONOFF_GBN = dvobj.getRecord().get("ONOFF_GBN");   //온오프 구분
        int   ACMCN_DAESU = dvobj.getRecord().getInt("ACMCN_DAESU");   //반주기 대수
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //모델 코드
        String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_ONOFF_INFO (INS_DATE, MODEL_CD, INSPRES_ID, ACMCN_DAESU, ONOFF_GBN, UPSO_CD)  \n";
        query +=" values(SYSDATE, :MODEL_CD , :INSPRES_ID , :ACMCN_DAESU , :ONOFF_GBN , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //온오프 구분
        sobj.setInt("ACMCN_DAESU", ACMCN_DAESU);               //반주기 대수
        sobj.setString("MODEL_CD", MODEL_CD);               //모델 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 온오프정보조회
    // 해당 업소의 온오프 정보를 조회한다.
    public DOBJ CALLsave_log_data_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsave_log_data_SEL9(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_log_data_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  ONOFF_GBN  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소 온오프정보수정
    // 온오프 정보가 1개일 경우 해당 온오프 정보로 수정한다.
    public DOBJ CALLsave_log_data_UPD10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_log_data_UPD10(dobj, dvobj);
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
    private SQLObject SQLsave_log_data_UPD10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //수정자 ID
        String   ONOFF_GBN = dobj.getRetObject("SEL19").getRecord().get("ONOFF_GBN");   //온오프 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ONOFF_GBN=:ONOFF_GBN , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //온오프 구분
        return sobj;
    }
    // 업소 온오프정보수정
    public DOBJ CALLsave_log_data_UPD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD11");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_log_data_UPD11(dobj, dvobj);
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
    private SQLObject SQLsave_log_data_UPD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //수정자 ID
        String   ONOFF_GBN ="O";   //온오프 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ONOFF_GBN=:ONOFF_GBN , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //온오프 구분
        return sobj;
    }
    // 업소 온오프정보수정
    public DOBJ CALLsave_log_data_UPD12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_log_data_UPD12(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_log_data_UPD12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //수정자 ID
        String   ONOFF_GBN ="";   //온오프 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ONOFF_GBN=:ONOFF_GBN , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //온오프 구분
        return sobj;
    }
    // 업소의 수집불가반주기 수정
    // 수집불가반주기 사용업소 값 수정
    public DOBJ CALLsave_log_data_UPD27(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD27");
        VOBJ dvobj = dobj.getRetObject("S1");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_log_data_UPD27(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD27") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_log_data_UPD27(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   COL_MCH_YN = dvobj.getRecord().get("COL_MCH_YN");
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.SDB_TBRA_UPSO  \n";
        query +=" set COL_MCH_YN=:COL_MCH_YN  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("COL_MCH_YN", COL_MCH_YN);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$save_log_data
    //##**$$get_log_data
    /*
    */
    public DOBJ CTLget_log_data(DOBJ dobj)
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
            dobj  = CALLget_log_data_SEL3(Conn, dobj);           //  청구년월 조회
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("01"))
            {
                dobj  = CALLget_log_data_SEL7(Conn, dobj);           //  관리업소 조회
                dobj  = CALLget_log_data_MRG9( dobj);        //  머지
                dobj  = CALLget_log_data_SEL4(Conn, dobj);           //  지부코드 보내기
            }
            else if( dobj.getRetObject("S").getRecord().get("UPSO_TYPE").equals("02"))
            {
                dobj  = CALLget_log_data_SEL8(Conn, dobj);           //  개발중업소 조회
                dobj  = CALLget_log_data_MRG9( dobj);        //  머지
                dobj  = CALLget_log_data_SEL4(Conn, dobj);           //  지부코드 보내기
            }
            else
            {
                dobj  = CALLget_log_data_SEL2(Conn, dobj);           //  전체업소조회
                dobj  = CALLget_log_data_MRG9( dobj);        //  머지
                dobj  = CALLget_log_data_SEL4(Conn, dobj);           //  지부코드 보내기
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
    public DOBJ CTLget_log_data( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_log_data_SEL3(Conn, dobj);           //  청구년월 조회
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("01"))
        {
            dobj  = CALLget_log_data_SEL7(Conn, dobj);           //  관리업소 조회
            dobj  = CALLget_log_data_MRG9( dobj);        //  머지
            dobj  = CALLget_log_data_SEL4(Conn, dobj);           //  지부코드 보내기
        }
        else if( dobj.getRetObject("S").getRecord().get("UPSO_TYPE").equals("02"))
        {
            dobj  = CALLget_log_data_SEL8(Conn, dobj);           //  개발중업소 조회
            dobj  = CALLget_log_data_MRG9( dobj);        //  머지
            dobj  = CALLget_log_data_SEL4(Conn, dobj);           //  지부코드 보내기
        }
        else
        {
            dobj  = CALLget_log_data_SEL2(Conn, dobj);           //  전체업소조회
            dobj  = CALLget_log_data_MRG9( dobj);        //  머지
            dobj  = CALLget_log_data_SEL4(Conn, dobj);           //  지부코드 보내기
        }
        return dobj;
    }
    // 청구년월 조회
    public DOBJ CALLget_log_data_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_log_data_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_log_data_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(SYSDATE,  'YYYYMM')  AS  DEMD_YRMN  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 관리업소 조회
    public DOBJ CALLget_log_data_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_log_data_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_log_data_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   DEMD_YRMN = dobj.getRetObject("SEL3").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT DEMD_MM_CNT , BRAN_CD , UPSO_CD , UPSO_NM , MNGEMSTR_NM , BSTYPGRAD_NM , UPSO_ADDR , UPSO_PHON , MNGEMSTR_HPNUM , MCHNDAESU , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 0) AS KY_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 1) AS TJ_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 2) AS ETC_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 3) AS KY_F , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 4) AS TJ_F , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 5) AS ETC_F , ONOFF_DATA_GBN , STAFF_NM , TO_CHAR(LAST_MOD_DATE, 'YYYY-MM-DD') AS LAST_MOD_DATE , COL_MCH_YN  ";
        query +=" FROM (  ";
        query +=" SELECT GIBU.FT_GET_UPSO_MMCNT(A.UPSO_CD, :DEMD_YRMN) AS DEMD_MM_CNT , BRAN_CD , UPSO_CD , UPSO_NM , MNGEMSTR_NM , GIBU.FT_GET_BSTYPGRAD_NM(A.UPSO_CD, '') AS BSTYPGRAD_NM , A.UPSO_NEW_ADDR1 || DECODE(A.UPSO_NEW_ADDR2, '', '', ', '||A.UPSO_NEW_ADDR2) || A.UPSO_REF_INFO UPSO_ADDR , A.UPSO_PHON , A.MNGEMSTR_HPNUM , A.MCHNDAESU , GIBU.FT_GET_UPSO_ONOFF_BSCON(A.UPSO_CD) AS ONOFF_BSCON , GIBU.FT_GET_ONOFF_DATA_GBN(A.UPSO_CD) AS ONOFF_DATA_GBN , FIDU.GET_STAFF_NM (STAFF_CD) AS STAFF_NM , (SELECT MAX(NVL(INS_DATE, MOD_DATE))  ";
        query +=" FROM GIBU.TBRA_UPSO_ONOFF_INFO  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD ) AS LAST_MOD_DATE , A.COL_MCH_YN , GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD) AS BSTYP_CD  ";
        query +=" FROM GIBU.SDB_TBRA_UPSO A  ";
        query +=" WHERE A.UPSO_STAT = '1'  ";
        query +=" AND (A.CLSBS_YRMN IS NULL  ";
        query +=" OR NVL(SUBSTR(A.CLSBS_INS_DAY,1,6), ' ') > :DEMD_YRMN)  ";
        query +=" AND A.NEW_DAY IS NOT NULL  ";
        query +=" AND A.NEW_DAY <= :DEMD_YRMN || '31'  ";
        query +=" AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" AND STAFF_CD = NVL(:STAFF_CD, STAFF_CD) )  ";
        query +=" WHERE (BSTYP_CD='k'  ";
        query +=" OR BSTYP_CD='l'  ";
        query +=" OR BSTYP_CD='o'  ";
        query +=" OR BSTYP_CD='n'  ";
        query +=" OR BSTYP_CD='y')  ";
        if( !BSTYP_CD.equals("") )
        {
            query +=" AND BSTYP_CD = :BSTYP_CD  ";
        }
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        if(!BSTYP_CD.equals(""))
        {
            sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 머지
    public DOBJ CALLget_log_data_MRG9(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG9");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL7, SEL8, SEL2","");
        rvobj.setName("MRG9") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 지부코드 보내기
    public DOBJ CALLget_log_data_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_log_data_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_log_data_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GIBU  AS  BRAN_CD  ,  DEPT_NM  AS  BRAN_NM  FROM  INSA.TCPM_DEPT  WHERE  BIPLC_GBN  =  '2'   \n";
        query +=" AND  GIBU  IS  NOT  NULL ";
        sobj.setSql(query);
        return sobj;
    }
    // 개발중업소 조회
    public DOBJ CALLget_log_data_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_log_data_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_log_data_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   DEMD_YRMN = dobj.getRetObject("SEL3").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT DEMD_MM_CNT , BRAN_CD , UPSO_CD , UPSO_NM , MNGEMSTR_NM , BSTYPGRAD_NM , UPSO_ADDR , UPSO_PHON , MNGEMSTR_HPNUM , MCHNDAESU , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 0) AS KY_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 1) AS TJ_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 2) AS ETC_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 3) AS KY_F , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 4) AS TJ_F , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 5) AS ETC_F , ONOFF_DATA_GBN , STAFF_NM , TO_CHAR(LAST_MOD_DATE, 'YYYY-MM-DD') AS LAST_MOD_DATE , COL_MCH_YN  ";
        query +=" FROM (  ";
        query +=" SELECT GIBU.FT_GET_UPSO_MMCNT(A.UPSO_CD, :DEMD_YRMN) AS DEMD_MM_CNT , BRAN_CD , UPSO_CD , UPSO_NM , MNGEMSTR_NM , GIBU.FT_GET_BSTYPGRAD_NM(A.UPSO_CD, '') AS BSTYPGRAD_NM , A.UPSO_NEW_ADDR1 || DECODE(A.UPSO_NEW_ADDR2, '', '', ', '||A.UPSO_NEW_ADDR2) || A.UPSO_REF_INFO UPSO_ADDR , A.UPSO_PHON , A.MNGEMSTR_HPNUM , A.MCHNDAESU , GIBU.FT_GET_UPSO_ONOFF_BSCON(A.UPSO_CD) AS ONOFF_BSCON , GIBU.FT_GET_ONOFF_DATA_GBN(A.UPSO_CD) AS ONOFF_DATA_GBN , FIDU.GET_STAFF_NM (STAFF_CD) AS STAFF_NM , (SELECT MAX(NVL(INS_DATE, MOD_DATE))  ";
        query +=" FROM GIBU.TBRA_UPSO_ONOFF_INFO  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD ) AS LAST_MOD_DATE , A.COL_MCH_YN , GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD) AS BSTYP_CD  ";
        query +=" FROM GIBU.SDB_TBRA_UPSO A  ";
        query +=" WHERE (CLSBS_YRMN IS NULL  ";
        query +=" OR NVL(SUBSTR(CLSBS_INS_DAY,1,6), ' ') > :DEMD_YRMN)  ";
        query +=" AND A.NEW_DAY IS NULL  ";
        query +=" AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" AND STAFF_CD = NVL(:STAFF_CD, STAFF_CD) )  ";
        query +=" WHERE (BSTYP_CD='k'  ";
        query +=" OR BSTYP_CD='l'  ";
        query +=" OR BSTYP_CD='o'  ";
        query +=" OR BSTYP_CD='n'  ";
        query +=" OR BSTYP_CD='y')  ";
        if( !BSTYP_CD.equals("") )
        {
            query +=" AND BSTYP_CD = :BSTYP_CD  ";
        }
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        if(!BSTYP_CD.equals(""))
        {
            sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 전체업소조회
    public DOBJ CALLget_log_data_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_log_data_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_log_data_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   DEMD_YRMN = dobj.getRetObject("SEL3").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT DEMD_MM_CNT , BRAN_CD , UPSO_CD , UPSO_NM , MNGEMSTR_NM , BSTYPGRAD_NM , UPSO_ADDR , UPSO_PHON , MNGEMSTR_HPNUM , MCHNDAESU , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 0) AS KY_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 1) AS TJ_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 2) AS ETC_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 3) AS KY_F , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 4) AS TJ_F , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 5) AS ETC_F , ONOFF_DATA_GBN , STAFF_NM , TO_CHAR(LAST_MOD_DATE, 'YYYY-MM-DD') AS LAST_MOD_DATE , COL_MCH_YN  ";
        query +=" FROM (  ";
        query +=" SELECT GIBU.FT_GET_UPSO_MMCNT(A.UPSO_CD, :DEMD_YRMN) AS DEMD_MM_CNT , BRAN_CD , UPSO_CD , UPSO_NM , MNGEMSTR_NM , GIBU.FT_GET_BSTYPGRAD_NM(A.UPSO_CD, '') AS BSTYPGRAD_NM , A.UPSO_NEW_ADDR1 || DECODE(A.UPSO_NEW_ADDR2, '', '', ', '||A.UPSO_NEW_ADDR2) || A.UPSO_REF_INFO UPSO_ADDR , A.UPSO_PHON , A.MNGEMSTR_HPNUM , A.MCHNDAESU , GIBU.FT_GET_UPSO_ONOFF_BSCON(A.UPSO_CD) AS ONOFF_BSCON , GIBU.FT_GET_ONOFF_DATA_GBN(A.UPSO_CD) AS ONOFF_DATA_GBN , FIDU.GET_STAFF_NM (STAFF_CD) AS STAFF_NM , (SELECT MAX(NVL(INS_DATE, MOD_DATE))  ";
        query +=" FROM GIBU.TBRA_UPSO_ONOFF_INFO  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD ) AS LAST_MOD_DATE , A.COL_MCH_YN , GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD) AS BSTYP_CD  ";
        query +=" FROM GIBU.SDB_TBRA_UPSO A  ";
        query +=" WHERE (CLSBS_YRMN IS NULL  ";
        query +=" OR NVL(SUBSTR(CLSBS_INS_DAY,1,6), ' ') > :DEMD_YRMN)  ";
        query +=" AND (NEW_DAY IS NULL  ";
        query +=" OR NEW_DAY <= :DEMD_YRMN || '31')  ";
        query +=" AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" AND STAFF_CD = NVL(:STAFF_CD, STAFF_CD) )  ";
        query +=" WHERE (BSTYP_CD='k'  ";
        query +=" OR BSTYP_CD='l'  ";
        query +=" OR BSTYP_CD='o'  ";
        query +=" OR BSTYP_CD='n'  ";
        query +=" OR BSTYP_CD='y')  ";
        if( !BSTYP_CD.equals("") )
        {
            query +=" AND BSTYP_CD = :BSTYP_CD  ";
        }
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        if(!BSTYP_CD.equals(""))
        {
            sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$get_log_data
    //##**$$end
}
