
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s14_2
{
    public bra01_s14_2()
    {
    }
    //##**$$select_offlog_save
    /* 1. 최초등록일때 : 무조건 영업만 가능 INS16으로 간다.
    2. 두번째 이상의 등록일때 MIUD53으로 간다.
    2-1 MIUD53은 영업정보 데이터셋
    2-2 MIUD 27은 로그기정보 데이터셋이다.
    영업상태가 영업일때만 수정이 가능하다.
    로그기정보 데이터셋의 로그기상태는 방의상태가 아니라 로그기의 실제상태참조다.
    2-3 UPD57은 비고 저장용이다.
    */
    public DOBJ CTLselect_offlog_save(DOBJ dobj)
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
            dobj  = CALLselect_offlog_save_SEL1(Conn, dobj);           //  최초등록인지 확인
            if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLselect_offlog_save_INS16(Conn, dobj);           //  업소정보
                dobj  = CALLselect_offlog_save_UPD50(Conn, dobj);           //  비고수정
                dobj  = CALLselect_offlog_save_INS17(Conn, dobj);           //  업소상태 이력
                dobj  = CALLselect_offlog_save_INS18(Conn, dobj);           //  방이력
                dobj  = CALLselect_offlog_save_INS19(Conn, dobj);           //  접근이력
                dobj  = CALLselect_offlog_save_XIUD52(Conn, dobj);           //  할인정보수정
            }
            else if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") > 0)
            {
                dobj  = CALLselect_offlog_save_MIUD57(Conn, dobj);           //  영업정보
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLselect_offlog_save_XIUD213(Conn, dobj);           //  백업
                dobj  = CALLselect_offlog_save_UPD57(Conn, dobj);           //  비고수정
                dobj  = CALLselect_offlog_save_UPD49(Conn, dobj);           //  비고수정
                dobj  = CALLselect_offlog_save_XIUD54(Conn, dobj);           //  할인정보수정
                dobj  = CALLselect_offlog_save_SEL51(Conn, dobj);           //  저장이후 마지막영업정보획득
                if( dobj.getRetObject("SEL51").getRecord().get("CO_STATUS").equals("07001"))
                {
                    dobj  = CALLselect_offlog_save_MIUD27(Conn, dobj);           //  로그기정보
                    if(dobj.getRtncode() == 9)
                    {
                        Conn.rollback();
                        return dobj;
                    }
                }
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
    public DOBJ CTLselect_offlog_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLselect_offlog_save_SEL1(Conn, dobj);           //  최초등록인지 확인
        if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLselect_offlog_save_INS16(Conn, dobj);           //  업소정보
            dobj  = CALLselect_offlog_save_UPD50(Conn, dobj);           //  비고수정
            dobj  = CALLselect_offlog_save_INS17(Conn, dobj);           //  업소상태 이력
            dobj  = CALLselect_offlog_save_INS18(Conn, dobj);           //  방이력
            dobj  = CALLselect_offlog_save_INS19(Conn, dobj);           //  접근이력
            dobj  = CALLselect_offlog_save_XIUD52(Conn, dobj);           //  할인정보수정
        }
        else if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") > 0)
        {
            dobj  = CALLselect_offlog_save_MIUD57(Conn, dobj);           //  영업정보
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLselect_offlog_save_XIUD213(Conn, dobj);           //  백업
            dobj  = CALLselect_offlog_save_UPD57(Conn, dobj);           //  비고수정
            dobj  = CALLselect_offlog_save_UPD49(Conn, dobj);           //  비고수정
            dobj  = CALLselect_offlog_save_XIUD54(Conn, dobj);           //  할인정보수정
            dobj  = CALLselect_offlog_save_SEL51(Conn, dobj);           //  저장이후 마지막영업정보획득
            if( dobj.getRetObject("SEL51").getRecord().get("CO_STATUS").equals("07001"))
            {
                dobj  = CALLselect_offlog_save_MIUD27(Conn, dobj);           //  로그기정보
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
            }
        }
        return dobj;
    }
    // 최초등록인지 확인
    public DOBJ CALLselect_offlog_save_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_save_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  LOG.KDS_SHOP  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소정보
    public DOBJ CALLselect_offlog_save_INS16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS16");
        VOBJ dvobj = dobj.getRetObject("S3");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS16(dobj, dvobj);
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
    private SQLObject SQLselect_offlog_save_INS16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   COMMENTS = dvobj.getRecord().get("COMMENTS");
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOP (COMMENTS, UPSO_CD)  \n";
        query +=" values(:COMMENTS , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("COMMENTS", COMMENTS);
        return sobj;
    }
    // 비고수정
    public DOBJ CALLselect_offlog_save_UPD50(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD50");
        VOBJ dvobj = dobj.getRetObject("S4");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD50(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD50") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD50(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // 업소상태 이력
    public DOBJ CALLselect_offlog_save_INS17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS17");
        VOBJ dvobj = dobj.getRetObject("S1");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS17(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS17") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        double SEQ = 0;        //관리번호
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //사용자명
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CO_STATUS = dvobj.getRecord().get("CO_STATUS");   //운영상태
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOP_STATUSHISTORY (CO_STATUS, UPSO_CD, SEQ, REG_DATE, USER_NAME)  \n";
        query +=" values(:CO_STATUS , :UPSO_CD , LOG.IMT_KDSSH_SEQ.NEXTVAL, SYSDATE, :USER_NAME )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //사용자명
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CO_STATUS", CO_STATUS);               //운영상태
        return sobj;
    }
    // 방이력
    // 화면에서 INSERT한 대로 넣는다
    public DOBJ CALLselect_offlog_save_INS18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS18");
        VOBJ dvobj = dobj.getRetObject("S2");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS18(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS18") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        double SEQ = 0;        //관리번호
        String   ROOM_NAME = dvobj.getRecord().get("ROOM_NAME");   //방 이름
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //제품번호
        String   CO_STATUS = dobj.getRetObject("S1").getRecord().get("CO_STATUS");   //임시컬럼2
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOPROOM (CO_STATUS, SERIAL_NO, UPSO_CD, SEQ, REG_DATE, BSCON_CD, ROOM_NAME)  \n";
        query +=" values(:CO_STATUS , :SERIAL_NO , :UPSO_CD , LOG.IMT_KDSSR_SEQ.NEXTVAL, SYSDATE, :BSCON_CD , :ROOM_NAME )";
        sobj.setSql(query);
        sobj.setString("ROOM_NAME", ROOM_NAME);               //방 이름
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        sobj.setString("CO_STATUS", CO_STATUS);               //임시컬럼2
        return sobj;
    }
    // 접근이력
    public DOBJ CALLselect_offlog_save_INS19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS19");
        VOBJ dvobj = dobj.getRetObject("S3");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS19(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS19") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        double SEQ = 0;        //관리번호
        String USER_NM ="";        //사용자명
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //사용자명
        String   CO_MENU ="05002";   //메뉴정보코드
        String   CO_PROC ="06001";   //변경이력코드
        String   REMOTE_IP = dvobj.getRecord().get("IPADDRESS");   //아이피
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사용자번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_ACCESS_HISTORY (REMOTE_IP, CO_PROC, USER_ID, SEQ, REG_DATE, USER_NAME, CO_MENU)  \n";
        query +=" values(:REMOTE_IP , :CO_PROC , :USER_ID , LOG.IMT_KDSAH_SEQ.NEXTVAL, SYSDATE, :USER_NAME , :CO_MENU )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //사용자명
        sobj.setString("CO_MENU", CO_MENU);               //메뉴정보코드
        sobj.setString("CO_PROC", CO_PROC);               //변경이력코드
        sobj.setString("REMOTE_IP", REMOTE_IP);               //아이피
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        return sobj;
    }
    // 할인정보수정
    public DOBJ CALLselect_offlog_save_XIUD52(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD52");
        VOBJ dvobj = dobj.getRetObject("S5");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_XIUD52(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD52");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_XIUD52(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DSCT_END = dobj.getRetObject("S5").getRecord().get("DSCT_END");   //할인종료월
        String   DSCT_START = dobj.getRetObject("S5").getRecord().get("DSCT_START");   //할인시작월
        String   DSCT_YN = dobj.getRetObject("S5").getRecord().get("DSCT_YN");   //할인적용여부
        String   MODPRES_ID = dobj.getRetObject("S5").getRecord().get("MODPRES_ID");   //수정자 ID
        String   UPSO_CD = dobj.getRetObject("S3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE LOG.KDS_SHOP  \n";
        query +=" SET DSCT_START = SUBSTR(:DSCT_START, 1,6) , DSCT_END = SUBSTR(:DSCT_END, 1,6) , DSCT_YN = :DSCT_YN , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("DSCT_END", DSCT_END);               //할인종료월
        sobj.setString("DSCT_START", DSCT_START);               //할인시작월
        sobj.setString("DSCT_YN", DSCT_YN);               //할인적용여부
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 영업정보
    // 영업정보는 추가만 가능 (수정,삭제 불가능)
    public DOBJ CALLselect_offlog_save_MIUD57(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD57");
        VOBJ dvobj = dobj.getRetObject("S1");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLselect_offlog_save_SEL15(Conn, dobj);           //  변경전의 영업정보
                if( dobj.getRetObject("S1").getRecord().get("CO_STATUS").equals("07001"))
                {
                    dobj  = CALLselect_offlog_save_INS48(Conn, dobj);           //  업소상태 이력
                    dobj  = CALLselect_offlog_save_XIUD49(Conn, dobj);           //  방이력수정
                    dobj  = CALLselect_offlog_save_INS50(Conn, dobj);           //  접근이력
                }
                else if( dobj.getRetObject("S1").getRecord().get("CO_STATUS").equals("07002") || dobj.getRetObject("S1").getRecord().get("CO_STATUS").equals("07003"))
                {
                    dobj  = CALLselect_offlog_save_INS40(Conn, dobj);           //  업소상태 이력
                    dobj  = CALLselect_offlog_save_XIUD45(Conn, dobj);           //  방이력수정
                    dobj  = CALLselect_offlog_save_SEL46(Conn, dobj);           //  LOGHISTORY 저장용자료
                    dobj  = CALLselect_offlog_save_INS23(Conn, dobj);           //  로그수집기이력
                    dobj  = CALLselect_offlog_save_UPD25(Conn, dobj);           //  로그기정보
                    dobj  = CALLselect_offlog_save_INS47(Conn, dobj);           //  접근이력
                }
            }
        }
        return dobj;
    }
    // 변경전의 영업정보
    public DOBJ CALLselect_offlog_save_SEL15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL15");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL15");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_save_SEL15(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL15");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_SEL15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  INDEX_DESC  (KDS_SHOP_STATUSHISTORY  PK_KDS_SH_SEQ)  */  A.CO_STATUS  FROM  LOG.KDS_SHOP_STATUSHISTORY  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소상태 이력
    public DOBJ CALLselect_offlog_save_INS48(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS48");
        VOBJ dvobj = dobj.getRetObject("S1");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS48(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS48") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS48(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        double SEQ = 0;        //관리번호
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //사용자명
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CO_STATUS = dvobj.getRecord().get("CO_STATUS");   //운영상태
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOP_STATUSHISTORY (CO_STATUS, UPSO_CD, SEQ, REG_DATE, USER_NAME)  \n";
        query +=" values(:CO_STATUS , :UPSO_CD , LOG.IMT_KDSSH_SEQ.NEXTVAL, SYSDATE, :USER_NAME )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //사용자명
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CO_STATUS", CO_STATUS);               //운영상태
        return sobj;
    }
    // 방이력수정
    // 삭제(07005)를 빼고 몽땅 UPDATE
    public DOBJ CALLselect_offlog_save_XIUD49(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD49");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_XIUD49(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD49");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_XIUD49(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CO_STATUS = dobj.getRetObject("R").getRecord().get("CO_STATUS");   //CO_STATUS
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE LOG.KDS_SHOPROOM  \n";
        query +=" SET CO_STATUS = :CO_STATUS  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND CO_STATUS <> '07005'";
        sobj.setSql(query);
        sobj.setString("CO_STATUS", CO_STATUS);               //CO_STATUS
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 접근이력
    public DOBJ CALLselect_offlog_save_INS50(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS50");
        VOBJ dvobj = dobj.getRetObject("S3");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS50(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS50") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS50(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        double SEQ = 0;        //관리번호
        String USER_NM ="";        //사용자명
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //사용자명
        String   CO_MENU ="05002";   //메뉴정보코드
        String   CO_PROC ="06001";   //변경이력코드
        String   REMOTE_IP = dvobj.getRecord().get("IPADDRESS");   //아이피
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사용자번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_ACCESS_HISTORY (REMOTE_IP, CO_PROC, USER_ID, SEQ, REG_DATE, USER_NAME, CO_MENU)  \n";
        query +=" values(:REMOTE_IP , :CO_PROC , :USER_ID , LOG.IMT_KDSAH_SEQ.NEXTVAL, SYSDATE, :USER_NAME , :CO_MENU )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //사용자명
        sobj.setString("CO_MENU", CO_MENU);               //메뉴정보코드
        sobj.setString("CO_PROC", CO_PROC);               //변경이력코드
        sobj.setString("REMOTE_IP", REMOTE_IP);               //아이피
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        return sobj;
    }
    // 업소상태 이력
    public DOBJ CALLselect_offlog_save_INS40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS40");
        VOBJ dvobj = dobj.getRetObject("S1");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS40(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS40") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        double SEQ = 0;        //관리번호
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //사용자명
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CO_STATUS = dvobj.getRecord().get("CO_STATUS");   //운영상태
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOP_STATUSHISTORY (CO_STATUS, UPSO_CD, SEQ, REG_DATE, USER_NAME)  \n";
        query +=" values(:CO_STATUS , :UPSO_CD , LOG.IMT_KDSSH_SEQ.NEXTVAL, SYSDATE, :USER_NAME )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //사용자명
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CO_STATUS", CO_STATUS);               //운영상태
        return sobj;
    }
    // 방이력수정
    // 삭제(07005)를 빼고 몽땅 UPDATE
    public DOBJ CALLselect_offlog_save_XIUD45(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD45");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_XIUD45(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD45");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_XIUD45(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CO_STATUS = dobj.getRetObject("R").getRecord().get("CO_STATUS");   //CO_STATUS
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE LOG.KDS_SHOPROOM  \n";
        query +=" SET CO_STATUS = :CO_STATUS  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND CO_STATUS <> '07005'";
        sobj.setSql(query);
        sobj.setString("CO_STATUS", CO_STATUS);               //CO_STATUS
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // LOGHISTORY 저장용자료
    public DOBJ CALLselect_offlog_save_SEL46(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL46");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL46");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_save_SEL46(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL46");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_SEL46(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SEQ  AS  REF_SEQ_SHOPROOM  ,  SERIAL_NO  ,  LOG.FT_GET_CO_AUTH(A.SERIAL_NO)  AS  CO_AUTH  ,   \n";
        query +=" (SELECT  CO_PARING  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  A.SERIAL_NO)  AS  CO_PARING  ,  CO_STATUS  ,  '11001'  AS  CO_SERIAL_MODIFY  FROM  LOG.KDS_SHOPROOM  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  CO_STATUS  <>  '07005' ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 로그수집기이력
    public DOBJ CALLselect_offlog_save_INS23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS23");
        VOBJ dvobj = dobj.getRetObject("SEL46");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS23(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS23") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        double SEQ = 0;        //관리번호
        String USER_NM ="";        //사용자명
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //사용자명
        String   CO_PARING = dvobj.getRecord().get("CO_PARING");   //개통상태코드
        String   CO_AUTH = dvobj.getRecord().get("CO_AUTH");
        String   REF_SEQ_SHOPROOM = dvobj.getRecord().get("REF_SEQ_SHOPROOM");
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //제품번호
        String   CO_STATUS = dvobj.getRecord().get("CO_STATUS");   //운영상태
        String   CO_SERIAL_MODIFY = dvobj.getRecord().get("CO_SERIAL_MODIFY");
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사용자번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_LOGHISTORY (CO_SERIAL_MODIFY, CO_STATUS, SERIAL_NO, REF_SEQ_SHOPROOM, USER_ID, CO_AUTH, SEQ, REG_DATE, CO_PARING, USER_NAME)  \n";
        query +=" values(:CO_SERIAL_MODIFY , :CO_STATUS , :SERIAL_NO , :REF_SEQ_SHOPROOM , :USER_ID , :CO_AUTH , LOG.IMT_KDSLH_SEQ.NEXTVAL, SYSDATE, :CO_PARING , :USER_NAME )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //사용자명
        sobj.setString("CO_PARING", CO_PARING);               //개통상태코드
        sobj.setString("CO_AUTH", CO_AUTH);
        sobj.setString("REF_SEQ_SHOPROOM", REF_SEQ_SHOPROOM);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        sobj.setString("CO_STATUS", CO_STATUS);               //운영상태
        sobj.setString("CO_SERIAL_MODIFY", CO_SERIAL_MODIFY);
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        return sobj;
    }
    // 로그기정보
    // 인증정보 초기화
    public DOBJ CALLselect_offlog_save_UPD25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD25");
        VOBJ dvobj = dobj.getRetObject("SEL46");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD25(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD25") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //제품번호
        String   AUTH_DATE = dvobj.getRecord().get("NULL");   //인증날짜
        String   CO_PARING ="08001";   //개통상태코드
        String   PARING_DATE = dvobj.getRecord().get("NULL");   //개통날짜
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update  \n";
        query +=" LOG.KDS_LOGCOLLECTOR  \n";
        query +=" set AUTH_DATE=:AUTH_DATE , MOD_DATE=SYSDATE , PARING_DATE=:PARING_DATE , CO_PARING=:CO_PARING  \n";
        query +=" where SERIAL_NO=:SERIAL_NO";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        sobj.setString("AUTH_DATE", AUTH_DATE);               //인증날짜
        sobj.setString("CO_PARING", CO_PARING);               //개통상태코드
        sobj.setString("PARING_DATE", PARING_DATE);               //개통날짜
        return sobj;
    }
    // 접근이력
    public DOBJ CALLselect_offlog_save_INS47(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS47");
        VOBJ dvobj = dobj.getRetObject("S3");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS47(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS47") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS47(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        double SEQ = 0;        //관리번호
        String USER_NM ="";        //사용자명
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //사용자명
        String   CO_MENU ="05002";   //메뉴정보코드
        String   CO_PROC ="06002";   //변경이력코드
        String   REMOTE_IP = dvobj.getRecord().get("IPADDRESS");   //아이피
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사용자번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_ACCESS_HISTORY (REMOTE_IP, CO_PROC, USER_ID, SEQ, REG_DATE, USER_NAME, CO_MENU)  \n";
        query +=" values(:REMOTE_IP , :CO_PROC , :USER_ID , LOG.IMT_KDSAH_SEQ.NEXTVAL, SYSDATE, :USER_NAME , :CO_MENU )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //사용자명
        sobj.setString("CO_MENU", CO_MENU);               //메뉴정보코드
        sobj.setString("CO_PROC", CO_PROC);               //변경이력코드
        sobj.setString("REMOTE_IP", REMOTE_IP);               //아이피
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        return sobj;
    }
    // 백업
    public DOBJ CALLselect_offlog_save_XIUD213(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD213");
        VOBJ dvobj = dobj.getRetObject("S3");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_XIUD213(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD213");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_XIUD213(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO LOG.KDS_SHOP_HIST( SEQ , UPSO_CD , COMMENTS , DSCT_START , DSCT_YN , DSCT_END , MODPRES_ID , MOD_DATE ) SELECT (SELECT NVL(MAX(SEQ), 0) + 1 FROM LOG.KDS_SHOP_HIST WHERE UPSO_CD = :UPSO_CD) , UPSO_CD , COMMENTS , DSCT_START , DSCT_YN , DSCT_END , MODPRES_ID , MOD_DATE FROM LOG.KDS_SHOP WHERE UPSO_CD = :UPSO_CD AND DSCT_START = (SELECT MAX(DSCT_START) FROM LOG.KDS_SHOP WHERE UPSO_CD = :UPSO_CD)";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 비고수정
    public DOBJ CALLselect_offlog_save_UPD57(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD57");
        VOBJ dvobj = dobj.getRetObject("S3");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD57(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD57") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD57(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   COMMENTS = dvobj.getRecord().get("COMMENTS");
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update LOG.KDS_SHOP  \n";
        query +=" set COMMENTS=:COMMENTS  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("COMMENTS", COMMENTS);
        return sobj;
    }
    // 비고수정
    public DOBJ CALLselect_offlog_save_UPD49(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD49");
        VOBJ dvobj = dobj.getRetObject("S4");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD49(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD49") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD49(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // 할인정보수정
    public DOBJ CALLselect_offlog_save_XIUD54(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD54");
        VOBJ dvobj = dobj.getRetObject("S5");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_XIUD54(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD54");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_XIUD54(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DSCT_END = dobj.getRetObject("S5").getRecord().get("DSCT_END");   //할인종료월
        String   DSCT_START = dobj.getRetObject("S5").getRecord().get("DSCT_START");   //할인시작월
        String   DSCT_YN = dobj.getRetObject("S5").getRecord().get("DSCT_YN");   //할인적용여부
        String   MODPRES_ID = dobj.getRetObject("S5").getRecord().get("MODPRES_ID");   //수정자 ID
        String   UPSO_CD = dobj.getRetObject("S3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE LOG.KDS_SHOP  \n";
        query +=" SET DSCT_START = SUBSTR(:DSCT_START, 1,6) , DSCT_END = SUBSTR(:DSCT_END, 1,6) , DSCT_YN = :DSCT_YN , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("DSCT_END", DSCT_END);               //할인종료월
        sobj.setString("DSCT_START", DSCT_START);               //할인시작월
        sobj.setString("DSCT_YN", DSCT_YN);               //할인적용여부
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 저장이후 마지막영업정보획득
    public DOBJ CALLselect_offlog_save_SEL51(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL51");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL51");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_save_SEL51(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL51");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_SEL51(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  INDEX_DESC  (KDS_SHOP_STATUSHISTORY  PK_KDS_SH_SEQ)  */  A.CO_STATUS  FROM  LOG.KDS_SHOP_STATUSHISTORY  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 로그기정보
    public DOBJ CALLselect_offlog_save_MIUD27(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD27");
        VOBJ dvobj = dobj.getRetObject("S2");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLselect_offlog_save_INS99(Conn, dobj);           //  방이력
                dobj  = CALLselect_offlog_save_INS100(Conn, dobj);           //  접근이력
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLselect_offlog_save_UPD147(Conn, dobj);           //  방이력수정
                dobj  = CALLselect_offlog_save_SEL33(Conn, dobj);           //  변경전의 SERIAL_NO
                if(!dobj.getRetObject("R").getRecord().get("SERIAL_NO").equals(dobj.getRetObject("SEL33").getRecord().get("SERIAL_NO")))
                {
                    dobj  = CALLselect_offlog_save_INS35(Conn, dobj);           //  로그수집기이력
                    dobj  = CALLselect_offlog_save_UPD150(Conn, dobj);           //  로그기정보
                    dobj  = CALLselect_offlog_save_UPD37(Conn, dobj);           //  방이력수정
                    dobj  = CALLselect_offlog_save_INS151(Conn, dobj);           //  접근이력
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLselect_offlog_save_SEL101(Conn, dobj);           //  LOGHISTORY 저장용자료
                dobj  = CALLselect_offlog_save_INS102(Conn, dobj);           //  로그수집기이력
                dobj  = CALLselect_offlog_save_UPD145(Conn, dobj);           //  로그기정보
                dobj  = CALLselect_offlog_save_UPD41(Conn, dobj);           //  방이력수정
                dobj  = CALLselect_offlog_save_INS146(Conn, dobj);           //  접근이력
            }
        }
        return dobj;
    }
    // LOGHISTORY 저장용자료
    public DOBJ CALLselect_offlog_save_SEL101(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL101");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL101");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_save_SEL101(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL101");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_SEL101(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SEQ  AS  REF_SEQ_SHOPROOM  ,  SERIAL_NO  ,  LOG.FT_GET_CO_AUTH(A.SERIAL_NO)  AS  CO_AUTH  ,   \n";
        query +=" (SELECT  CO_PARING  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  A.SERIAL_NO)  AS  CO_PARING  ,  CO_STATUS  ,  '11003'  AS  CO_SERIAL_MODIFY  FROM  LOG.KDS_SHOPROOM  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("SEQ", SEQ);               //관리번호
        return sobj;
    }
    // 방이력수정
    // 변경전의 SERIAL_NO를 새로 UPDATE XIUD사용해야함
    public DOBJ CALLselect_offlog_save_UPD147(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD147");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD147(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD147") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD147(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ROOM_NAME = dvobj.getRecord().get("ROOM_NAME");   //방 이름
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update LOG.KDS_SHOPROOM  \n";
        query +=" set BSCON_CD=:BSCON_CD , ROOM_NAME=:ROOM_NAME  \n";
        query +=" where SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("ROOM_NAME", ROOM_NAME);               //방 이름
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setDouble("SEQ", SEQ);               //관리번호
        return sobj;
    }
    // 방이력
    // 화면에서 INSERT한 대로 넣는다
    public DOBJ CALLselect_offlog_save_INS99(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS99");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS99(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS99") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS99(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        double SEQ = 0;        //관리번호
        String   ROOM_NAME = dvobj.getRecord().get("ROOM_NAME");   //방 이름
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //제품번호
        String   CO_STATUS = dobj.getRetObject("SEL51").getRecord().get("CO_STATUS");   //임시컬럼2
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOPROOM (CO_STATUS, SERIAL_NO, UPSO_CD, SEQ, REG_DATE, BSCON_CD, ROOM_NAME)  \n";
        query +=" values(:CO_STATUS , :SERIAL_NO , :UPSO_CD , LOG.IMT_KDSSR_SEQ.NEXTVAL, SYSDATE, :BSCON_CD , :ROOM_NAME )";
        sobj.setSql(query);
        sobj.setString("ROOM_NAME", ROOM_NAME);               //방 이름
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        sobj.setString("CO_STATUS", CO_STATUS);               //임시컬럼2
        return sobj;
    }
    // 로그수집기이력
    public DOBJ CALLselect_offlog_save_INS102(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS102");
        VOBJ dvobj = dobj.getRetObject("SEL101");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS102(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS102") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS102(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        double SEQ = 0;        //관리번호
        String USER_NM ="";        //사용자명
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //사용자명
        String   CO_PARING = dvobj.getRecord().get("CO_PARING");   //개통상태코드
        String   CO_AUTH = dvobj.getRecord().get("CO_AUTH");
        String   REF_SEQ_SHOPROOM = dvobj.getRecord().get("REF_SEQ_SHOPROOM");
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //제품번호
        String   CO_STATUS = dvobj.getRecord().get("CO_STATUS");   //운영상태
        String   CO_SERIAL_MODIFY = dvobj.getRecord().get("CO_SERIAL_MODIFY");
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사용자번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_LOGHISTORY (CO_SERIAL_MODIFY, CO_STATUS, SERIAL_NO, REF_SEQ_SHOPROOM, USER_ID, CO_AUTH, SEQ, REG_DATE, CO_PARING, USER_NAME)  \n";
        query +=" values(:CO_SERIAL_MODIFY , :CO_STATUS , :SERIAL_NO , :REF_SEQ_SHOPROOM , :USER_ID , :CO_AUTH , LOG.IMT_KDSLH_SEQ.NEXTVAL, SYSDATE, :CO_PARING , :USER_NAME )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //사용자명
        sobj.setString("CO_PARING", CO_PARING);               //개통상태코드
        sobj.setString("CO_AUTH", CO_AUTH);
        sobj.setString("REF_SEQ_SHOPROOM", REF_SEQ_SHOPROOM);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        sobj.setString("CO_STATUS", CO_STATUS);               //운영상태
        sobj.setString("CO_SERIAL_MODIFY", CO_SERIAL_MODIFY);
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        return sobj;
    }
    // 변경전의 SERIAL_NO
    // 이것저것 자료획득
    public DOBJ CALLselect_offlog_save_SEL33(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL33");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL33");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_save_SEL33(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL33");
        rvobj.Println("SEL33");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_SEL33(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SEQ  AS  REF_SEQ_SHOPROOM  ,  SERIAL_NO  ,  LOG.FT_GET_CO_AUTH(A.SERIAL_NO)  AS  CO_AUTH  ,   \n";
        query +=" (SELECT  CO_PARING  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  A.SERIAL_NO)  AS  CO_PARING  ,  CO_STATUS  ,  '11002'  AS  CO_SERIAL_MODIFY  FROM  LOG.KDS_SHOPROOM  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("SEQ", SEQ);               //관리번호
        return sobj;
    }
    // 접근이력
    public DOBJ CALLselect_offlog_save_INS100(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS100");
        VOBJ dvobj = dobj.getRetObject("S3");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS100(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS100") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS100(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        double SEQ = 0;        //관리번호
        String USER_NM ="";        //사용자명
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //사용자명
        String   CO_MENU ="05002";   //메뉴정보코드
        String   CO_PROC ="06001";   //변경이력코드
        String   REMOTE_IP = dvobj.getRecord().get("IPADDRESS");   //아이피
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사용자번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_ACCESS_HISTORY (REMOTE_IP, CO_PROC, USER_ID, SEQ, REG_DATE, USER_NAME, CO_MENU)  \n";
        query +=" values(:REMOTE_IP , :CO_PROC , :USER_ID , LOG.IMT_KDSAH_SEQ.NEXTVAL, SYSDATE, :USER_NAME , :CO_MENU )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //사용자명
        sobj.setString("CO_MENU", CO_MENU);               //메뉴정보코드
        sobj.setString("CO_PROC", CO_PROC);               //변경이력코드
        sobj.setString("REMOTE_IP", REMOTE_IP);               //아이피
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        return sobj;
    }
    // 로그기정보
    // 인증정보 초기화
    public DOBJ CALLselect_offlog_save_UPD145(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD145");
        VOBJ dvobj = dobj.getRetObject("SEL101");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD145(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD145") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD145(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //제품번호
        String   AUTH_DATE = dvobj.getRecord().get("NULL");   //인증날짜
        String   CO_PARING ="08001";   //개통상태코드
        String   PARING_DATE = dvobj.getRecord().get("NULL");   //개통날짜
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update  \n";
        query +=" LOG.KDS_LOGCOLLECTOR  \n";
        query +=" set AUTH_DATE=:AUTH_DATE , MOD_DATE=SYSDATE , PARING_DATE=:PARING_DATE , CO_PARING=:CO_PARING  \n";
        query +=" where SERIAL_NO=:SERIAL_NO";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        sobj.setString("AUTH_DATE", AUTH_DATE);               //인증날짜
        sobj.setString("CO_PARING", CO_PARING);               //개통상태코드
        sobj.setString("PARING_DATE", PARING_DATE);               //개통날짜
        return sobj;
    }
    // 방이력수정
    // 변경전의 SERIAL_NO를 새로 UPDATE XIUD사용해야함
    public DOBJ CALLselect_offlog_save_UPD41(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD41");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD41(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD41") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD41(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //관리번호
        String   CO_STATUS ="07005";   //운영상태
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update LOG.KDS_SHOPROOM  \n";
        query +=" set CO_STATUS=:CO_STATUS  \n";
        query +=" where SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("CO_STATUS", CO_STATUS);               //운영상태
        return sobj;
    }
    // 로그수집기이력
    // 이전 로그기 시리얼번호를 이력에 등록
    public DOBJ CALLselect_offlog_save_INS35(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS35");
        VOBJ dvobj = dobj.getRetObject("SEL33");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("INS35");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS35(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS35") ;
        rvobj.Println("INS35");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS35(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        double SEQ = 0;        //관리번호
        String USER_NM ="";        //사용자명
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //사용자명
        String   CO_PARING = dvobj.getRecord().get("CO_PARING");   //개통상태코드
        String   CO_AUTH = dvobj.getRecord().get("CO_AUTH");
        String   REF_SEQ_SHOPROOM = dvobj.getRecord().get("REF_SEQ_SHOPROOM");
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //제품번호
        String   CO_STATUS = dvobj.getRecord().get("CO_STATUS");   //운영상태
        String   CO_SERIAL_MODIFY = dvobj.getRecord().get("CO_SERIAL_MODIFY");
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사용자번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_LOGHISTORY (CO_SERIAL_MODIFY, CO_STATUS, SERIAL_NO, REF_SEQ_SHOPROOM, USER_ID, CO_AUTH, SEQ, REG_DATE, CO_PARING, USER_NAME)  \n";
        query +=" values(:CO_SERIAL_MODIFY , :CO_STATUS , :SERIAL_NO , :REF_SEQ_SHOPROOM , :USER_ID , :CO_AUTH , LOG.IMT_KDSLH_SEQ.NEXTVAL, SYSDATE, :CO_PARING , :USER_NAME )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //사용자명
        sobj.setString("CO_PARING", CO_PARING);               //개통상태코드
        sobj.setString("CO_AUTH", CO_AUTH);
        sobj.setString("REF_SEQ_SHOPROOM", REF_SEQ_SHOPROOM);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        sobj.setString("CO_STATUS", CO_STATUS);               //운영상태
        sobj.setString("CO_SERIAL_MODIFY", CO_SERIAL_MODIFY);
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        return sobj;
    }
    // 접근이력
    public DOBJ CALLselect_offlog_save_INS146(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS146");
        VOBJ dvobj = dobj.getRetObject("S3");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS146(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS146") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS146(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        double SEQ = 0;        //관리번호
        String USER_NM ="";        //사용자명
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //사용자명
        String   CO_MENU ="05002";   //메뉴정보코드
        String   CO_PROC ="06003";   //변경이력코드
        String   REMOTE_IP = dvobj.getRecord().get("IPADDRESS");   //아이피
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사용자번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_ACCESS_HISTORY (REMOTE_IP, CO_PROC, USER_ID, SEQ, REG_DATE, USER_NAME, CO_MENU)  \n";
        query +=" values(:REMOTE_IP , :CO_PROC , :USER_ID , LOG.IMT_KDSAH_SEQ.NEXTVAL, SYSDATE, :USER_NAME , :CO_MENU )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //사용자명
        sobj.setString("CO_MENU", CO_MENU);               //메뉴정보코드
        sobj.setString("CO_PROC", CO_PROC);               //변경이력코드
        sobj.setString("REMOTE_IP", REMOTE_IP);               //아이피
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        return sobj;
    }
    // 로그기정보
    // 인증정보 초기화
    public DOBJ CALLselect_offlog_save_UPD150(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD150");
        VOBJ dvobj = dobj.getRetObject("SEL33");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("UPD150");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD150(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD150") ;
        rvobj.Println("UPD150");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD150(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //제품번호
        String   AUTH_DATE = dvobj.getRecord().get("NULL");   //인증날짜
        String   CO_PARING ="08001";   //개통상태코드
        String   PARING_DATE = dvobj.getRecord().get("NULL");   //개통날짜
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update  \n";
        query +=" LOG.KDS_LOGCOLLECTOR  \n";
        query +=" set AUTH_DATE=:AUTH_DATE , MOD_DATE=SYSDATE , PARING_DATE=:PARING_DATE , CO_PARING=:CO_PARING  \n";
        query +=" where SERIAL_NO=:SERIAL_NO";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        sobj.setString("AUTH_DATE", AUTH_DATE);               //인증날짜
        sobj.setString("CO_PARING", CO_PARING);               //개통상태코드
        sobj.setString("PARING_DATE", PARING_DATE);               //개통날짜
        return sobj;
    }
    // 방이력수정
    // 변경전의 SERIAL_NO를 새로 UPDATE XIUD사용해야함
    public DOBJ CALLselect_offlog_save_UPD37(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD37");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("UPD37");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_UPD37(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD37") ;
        rvobj.Println("UPD37");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_UPD37(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //관리번호
        String   SERIAL_NO = dvobj.getRecord().get("SERIAL_NO");   //제품번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update LOG.KDS_SHOPROOM  \n";
        query +=" set SERIAL_NO=:SERIAL_NO  \n";
        query +=" where SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        return sobj;
    }
    // 접근이력
    public DOBJ CALLselect_offlog_save_INS151(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS151");
        VOBJ dvobj = dobj.getRetObject("S3");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("INS151");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLselect_offlog_save_INS151(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS151") ;
        rvobj.Println("INS151");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_save_INS151(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        double SEQ = 0;        //관리번호
        String USER_NM ="";        //사용자명
        String   USER_NAME = dvobj.getRecord().get("USER_NAME");   //사용자명
        String   CO_MENU ="05002";   //메뉴정보코드
        String   CO_PROC ="06002";   //변경이력코드
        String   REMOTE_IP = dvobj.getRecord().get("IPADDRESS");   //아이피
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사용자번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_ACCESS_HISTORY (REMOTE_IP, CO_PROC, USER_ID, SEQ, REG_DATE, USER_NAME, CO_MENU)  \n";
        query +=" values(:REMOTE_IP , :CO_PROC , :USER_ID , LOG.IMT_KDSAH_SEQ.NEXTVAL, SYSDATE, :USER_NAME , :CO_MENU )";
        sobj.setSql(query);
        sobj.setString("USER_NAME", USER_NAME);               //사용자명
        sobj.setString("CO_MENU", CO_MENU);               //메뉴정보코드
        sobj.setString("CO_PROC", CO_PROC);               //변경이력코드
        sobj.setString("REMOTE_IP", REMOTE_IP);               //아이피
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        return sobj;
    }
    //##**$$select_offlog_save
    //##**$$sel_log_dsct_hist
    /*
    */
    public DOBJ CTLsel_log_dsct_hist(DOBJ dobj)
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
            dobj  = CALLsel_log_dsct_hist_SEL1(Conn, dobj);           //  이력조회
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
    public DOBJ CTLsel_log_dsct_hist( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_log_dsct_hist_SEL1(Conn, dobj);           //  이력조회
        return dobj;
    }
    // 이력조회
    public DOBJ CALLsel_log_dsct_hist_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_log_dsct_hist_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_log_dsct_hist_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SEQ  ,  UPSO_CD  ,  COMMENTS  ,  DSCT_START  ,  DSCT_YN  ,  DSCT_END  ,  MODPRES_ID  ,  MOD_DATE  FROM  LOG.KDS_SHOP_HIST  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$sel_log_dsct_hist
    //##**$$end
}
