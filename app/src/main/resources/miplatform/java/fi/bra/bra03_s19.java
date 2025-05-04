
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s19
{
    public bra03_s19()
    {
    }
    //##**$$upload_gw87
    /*
    */
    public DOBJ CTLupload_gw87(DOBJ dobj)
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
            dobj  = CALLupload_gw87_DEL1(Conn, dobj);           //  기존데이타삭제
            dobj  = CALLupload_gw87_XIUD16(Conn, dobj);           //  은행접수신청서삭제
            dobj  = CALLupload_gw87_MPD2(Conn, dobj);           //  건별처리
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
    public DOBJ CTLupload_gw87( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupload_gw87_DEL1(Conn, dobj);           //  기존데이타삭제
        dobj  = CALLupload_gw87_XIUD16(Conn, dobj);           //  은행접수신청서삭제
        dobj  = CALLupload_gw87_MPD2(Conn, dobj);           //  건별처리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 기존데이타삭제
    // 처리일자에 해당하는 기존데이타들을 삭제한다.
    public DOBJ CALLupload_gw87_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("S1");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("DEL1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_gw87_DEL1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_gw87_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dvobj.getRecord().get("PROC_DAY");   //처리 일자
        String   GBN ="22";   //구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_UPSO_AUTORSLT  \n";
        query +=" where GBN=:GBN  \n";
        query +=" and PROC_DAY=:PROC_DAY";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("GBN", GBN);               //구분
        return sobj;
    }
    // 은행접수신청서삭제
    // 처리일자에 해당하는 은행접수신청서를 삭제한다.
    public DOBJ CALLupload_gw87_XIUD16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD16");
        VOBJ dvobj = dobj.getRetObject("S1");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_gw87_XIUD16(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD16");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_gw87_XIUD16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE GIBU.TBRA_UPSO_AUTO_APPLICATION A  \n";
        query +=" WHERE A.PROC_DAY = :PROC_DAY  \n";
        query +=" AND A.RECEPTION_GBN IN ('9')";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    // 건별처리
    public DOBJ CALLupload_gw87_MPD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD2");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("MPD2");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupload_gw87_SEL70(Conn, dobj);           //  SEQ_NUM 획득
                dobj  = CALLupload_gw87_SEL3(Conn, dobj);           //  업소체크
                if( dobj.getRetObject("R").getRecord().get("APPTN_GBN").equals("01"))
                {
                    dobj  = CALLupload_gw87_INS42(Conn, dobj);           //  업소조회에러
                }
                else if(!dobj.getRetObject("SEL3").getRecord().get("UPSO_CD").equals("") && ( dobj.getRetObject("R").getRecord().get("APPTN_GBN").equals("03") || dobj.getRetObject("R").getRecord().get("APPTN_GBN").equals("04") ))
                {
                    dobj  = CALLupload_gw87_SEL13(Conn, dobj);           //  주민번호체크
                    if(( !dobj.getRetObject("SEL3").getRecord().get("RESINUM").equals(dobj.getRetObject("R").getRecord().get("RESINUM")) ) && ( dobj.getRetObject("SEL13").getRecord().getDouble("CNT") == 0 ))
                    {
                        dobj  = CALLupload_gw87_INS15(Conn, dobj);           //  주민번호에러
                        dobj  = CALLupload_gw87_INS16(Conn, dobj);           //  신청서 등록
                    }
                    else
                    {
                        dobj  = CALLupload_gw87_SEL17(Conn, dobj);           //  은행정보체크
                        if( dobj.getRetObject("SEL17").getRecord().getDouble("CNT") > 0)
                        {
                            dobj  = CALLupload_gw87_INS19(Conn, dobj);           //  이력먼저 등록
                            dobj  = CALLupload_gw87_OSP20(Conn, dobj);           //  청구 데이터 생성
                        }
                        else
                        {
                            dobj  = CALLupload_gw87_INS21(Conn, dobj);           //  은행정보에러
                            dobj  = CALLupload_gw87_INS22(Conn, dobj);           //  신청서 등록
                        }
                    }
                }
                else if(!dobj.getRetObject("SEL3").getRecord().get("UPSO_CD").equals("") && ( dobj.getRetObject("R").getRecord().get("APPTN_GBN").equals("02") || dobj.getRetObject("R").getRecord().get("APPTN_GBN").equals("08") || dobj.getRetObject("R").getRecord().get("APPTN_GBN").equals("09") ))
                {
                    dobj  = CALLupload_gw87_INS45(Conn, dobj);           //  이력먼저 등록
                    dobj  = CALLupload_gw87_OSP46(Conn, dobj);           //  청구 데이터 생성
                }
                else
                {
                    dobj  = CALLupload_gw87_INS9(Conn, dobj);           //  업소조회에러
                }
            }
        }
        return dobj;
    }
    // SEQ_NUM 획득
    public DOBJ CALLupload_gw87_SEL70(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL70");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL70");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupload_gw87_SEL70(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL70");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_gw87_SEL70(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SEQ_NUM = dobj.getRetObject("R").getRecord().get("SEQ_NUM");   //일련 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(TO_NUMBER(:SEQ_NUM)  +  90000000)  AS  SEQ_NUM  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
        return sobj;
    }
    // 업소체크
    public DOBJ CALLupload_gw87_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupload_gw87_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_gw87_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHGATR_PAYPRES_NUM = dobj.getRetObject("R").getRecord().get("CHGATR_PAYPRES_NUM");   //변경후 납부자 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  DECODE(LENGTH(RESINUM),13,  SUBSTR(RESINUM,0,6)||'0000000',  RESINUM)  RESINUM  FROM  (   \n";
        query +=" SELECT  NVL(MAX(UPSO_CD),'')  UPSO_CD  ,  NVL(MAX(MNGEMSTR_RESINUM),'')  RESINUM  FROM  GIBU.TBRA_UPSO  WHERE  CLIENT_NUM  =  :CHGATR_PAYPRES_NUM  ) ";
        sobj.setSql(query);
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //변경후 납부자 번호
        return sobj;
    }
    // 업소조회에러
    // 납부자번호 구성쳬게가 틀린 경우
    public DOBJ CALLupload_gw87_INS42(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS42");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS42");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_gw87_INS42(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS42") ;
        rvobj.Println("INS42");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_gw87_INS42(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //접수처 구분
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //신청 구분
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   RECPTNUM = dvobj.getRecord().get("RECPTNUM");   //접수번호
        String   PAY_BANK_CD = dvobj.getRecord().get("PAY_BANK_CD");   //납부 은행 코드
        String   PAY_KND = dvobj.getRecord().get("PAY_KND");   //요금 종류
        String   PAY_REQDAY = dvobj.getRecord().get("PAY_REQDAY");   //납부 희망일자
        String   CHGBFR_PAYPRES_NUM = dvobj.getRecord().get("CHGBFR_PAYPRES_NUM");   //변경전 납부자 번호
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //최초 개시일
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //출금 계좌 번호
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //변경후 납부자 번호
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //접수점 코드
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //납부자 전화번호
        String   APPTN_RSLT = dvobj.getRecord().get("APPTN_RSLT");   //신청 결과
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //신청 일자
        String   ERR_GBN ="";   //에러 구분
        String   GBN ="22";   //구분
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");   //관리번호
        String   UPSO_CD ="";   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYER_PHONNUM, GBN, PAYPRES_NM, RECPT_BANK_CD, CHGATR_PAYPRES_NUM, PAY_ACCN_NUM, PROC_DAY, BEGNG_RELSDAY, SEQ_NUM, CHGBFR_PAYPRES_NUM, PAY_REQDAY, PAY_KND, APPTN_DAY, PAY_BANK_CD, RECPTNUM, UPSO_CD, RESINUM, ERR_GBN, APPTN_GBN, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYER_PHONNUM , :GBN , :PAYPRES_NM , :RECPT_BANK_CD , :CHGATR_PAYPRES_NUM , :PAY_ACCN_NUM , :PROC_DAY , :BEGNG_RELSDAY , :SEQ_NUM , :CHGBFR_PAYPRES_NUM , :PAY_REQDAY , :PAY_KND , :APPTN_DAY , :PAY_BANK_CD , :RECPTNUM , :UPSO_CD , :RESINUM , :ERR_GBN , :APPTN_GBN , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //접수처 구분
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("RECPTNUM", RECPTNUM);               //접수번호
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //납부 은행 코드
        sobj.setString("PAY_KND", PAY_KND);               //요금 종류
        sobj.setString("PAY_REQDAY", PAY_REQDAY);               //납부 희망일자
        sobj.setString("CHGBFR_PAYPRES_NUM", CHGBFR_PAYPRES_NUM);               //변경전 납부자 번호
        sobj.setString("BEGNG_RELSDAY", BEGNG_RELSDAY);               //최초 개시일
        sobj.setString("PAY_ACCN_NUM", PAY_ACCN_NUM);               //출금 계좌 번호
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //변경후 납부자 번호
        sobj.setString("RECPT_BANK_CD", RECPT_BANK_CD);               //접수점 코드
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("PAYER_PHONNUM", PAYER_PHONNUM);               //납부자 전화번호
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //신청 결과
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("ERR_GBN", ERR_GBN);               //에러 구분
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("SEQ_NUM", SEQ_NUM);               //관리번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 주민번호체크
    public DOBJ CALLupload_gw87_SEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL13");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupload_gw87_SEL13(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL13");
        rvobj.Println("SEL13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_gw87_SEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //업소 코드
        String   RESINUM = dobj.getRetObject("R").getRecord().get("RESINUM");   //주민번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  DECODE(LENGTH(RESINUM),  13,  SUBSTR(RESINUM,0,6)||'0000000',  RESINUM)  =  :RESINUM   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("RESINUM", RESINUM);               //주민번호
        return sobj;
    }
    // 주민번호에러
    // APPTN_RSLT = ERR_GBN 오류코드 동일시 한다."03"대상납부자 없음
    public DOBJ CALLupload_gw87_INS15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS15");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_gw87_INS15(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS15") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_gw87_INS15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //접수처 구분
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //신청 구분
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   RECPTNUM = dvobj.getRecord().get("RECPTNUM");   //접수번호
        String   PAY_BANK_CD = dvobj.getRecord().get("PAY_BANK_CD");   //납부 은행 코드
        String   PAY_KND = dvobj.getRecord().get("PAY_KND");   //요금 종류
        String   PAY_REQDAY = dvobj.getRecord().get("PAY_REQDAY");   //납부 희망일자
        String   CHGBFR_PAYPRES_NUM = dvobj.getRecord().get("CHGBFR_PAYPRES_NUM");   //변경전 납부자 번호
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //최초 개시일
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //출금 계좌 번호
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //변경후 납부자 번호
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //접수점 코드
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //납부자 전화번호
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //신청 일자
        String   APPTN_RSLT ="03";   //신청 결과
        String   ERR_GBN ="0002";   //에러 구분
        String   GBN ="22";   //구분
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");   //일련 번호
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYER_PHONNUM, GBN, PAYPRES_NM, RECPT_BANK_CD, CHGATR_PAYPRES_NUM, PAY_ACCN_NUM, PROC_DAY, BEGNG_RELSDAY, SEQ_NUM, CHGBFR_PAYPRES_NUM, PAY_REQDAY, PAY_KND, APPTN_DAY, PAY_BANK_CD, RECPTNUM, UPSO_CD, RESINUM, ERR_GBN, APPTN_GBN, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYER_PHONNUM , :GBN , :PAYPRES_NM , :RECPT_BANK_CD , :CHGATR_PAYPRES_NUM , :PAY_ACCN_NUM , :PROC_DAY , :BEGNG_RELSDAY , :SEQ_NUM , :CHGBFR_PAYPRES_NUM , :PAY_REQDAY , :PAY_KND , :APPTN_DAY , :PAY_BANK_CD , :RECPTNUM , :UPSO_CD , :RESINUM , :ERR_GBN , :APPTN_GBN , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //접수처 구분
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("RECPTNUM", RECPTNUM);               //접수번호
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //납부 은행 코드
        sobj.setString("PAY_KND", PAY_KND);               //요금 종류
        sobj.setString("PAY_REQDAY", PAY_REQDAY);               //납부 희망일자
        sobj.setString("CHGBFR_PAYPRES_NUM", CHGBFR_PAYPRES_NUM);               //변경전 납부자 번호
        sobj.setString("BEGNG_RELSDAY", BEGNG_RELSDAY);               //최초 개시일
        sobj.setString("PAY_ACCN_NUM", PAY_ACCN_NUM);               //출금 계좌 번호
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //변경후 납부자 번호
        sobj.setString("RECPT_BANK_CD", RECPT_BANK_CD);               //접수점 코드
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("PAYER_PHONNUM", PAYER_PHONNUM);               //납부자 전화번호
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //신청 결과
        sobj.setString("ERR_GBN", ERR_GBN);               //에러 구분
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 신청서 등록
    // 주민번호 에러가 났음을 기록하자
    public DOBJ CALLupload_gw87_INS16(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLupload_gw87_INS16(dobj, dvobj);
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
    private SQLObject SQLupload_gw87_INS16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String BANK_CD ="";        //은행 코드
        String CONFIRM_DATE ="";        //확인 일자
        String INS_DATE ="";        //등록 일시
        double SEQ = 0;        //관리번호
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
        String   SEQ_1 = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //관리번호
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   BANK_CD_1 = dobj.getRetObject("R").getRecord().get("PAY_BANK_CD");   //은행 코드
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   APPLICATION_GBN ="71";   //신청 일자
        String   AUTO_ACCNNUM = dobj.getRetObject("R").getRecord().get("PAY_ACCN_NUM");   //출금 계좌번호
        String   CONFIRM_ID = dobj.getRetObject("R").getRecord().get("INSPRES_ID");   //계산 일자
        String   PHON_NUM = dobj.getRetObject("R").getRecord().get("PAYER_PHONNUM");   //전화 번호
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        String   RECEPTION_GBN ="9";   //접수처
        String   REMAK =" 03:대상납부자 없음";   //비고
        String   STAFF_CD = dobj.getRetObject("R").getRecord().get("INSPRES_ID");   //사원 코드
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO_APPLICATION (CONFIRM_ID, INSPRES_ID, BANK_CD, PAYPRES_NM, APPLICATION_GBN, PHON_NUM, SEQ, PROC_DAY, INS_DATE, STAFF_CD, APPTN_DAY, UPSO_CD, AUTO_ACCNNUM, RECEPTION_GBN, RESINUM, CONFIRM_DATE, REMAK)  \n";
        query +=" values(:CONFIRM_ID , :INSPRES_ID , SUBSTR(:BANK_CD_1, 1,3), :PAYPRES_NM , :APPLICATION_GBN , :PHON_NUM , (SELECT NVL(MAX(SEQ), 0) + 1 SEQ FROM GIBU.TBRA_UPSO_AUTO_APPLICATION WHERE UPSO_CD = :SEQ_1), :PROC_DAY , SYSDATE, :STAFF_CD , :APPTN_DAY , :UPSO_CD , :AUTO_ACCNNUM , :RECEPTION_GBN , :RESINUM , SYSDATE, :REMAK )";
        sobj.setSql(query);
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("SEQ_1", SEQ_1);               //관리번호
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("BANK_CD_1", BANK_CD_1);               //은행 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("APPLICATION_GBN", APPLICATION_GBN);               //신청 일자
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //출금 계좌번호
        sobj.setString("CONFIRM_ID", CONFIRM_ID);               //계산 일자
        sobj.setString("PHON_NUM", PHON_NUM);               //전화 번호
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //접수처
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 은행정보체크
    // 납부자계좌개설점코드 (7)  - 7자리의 숫자이나, 은행코드 3자리만 수록하고 뒤의 4자리는"0000"이 가능하므로 앞의 3자리고 LIKE 검색을 한다
    public DOBJ CALLupload_gw87_SEL17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL17");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL17");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupload_gw87_SEL17(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL17");
        rvobj.Println("SEL17");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_gw87_SEL17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PAY_BANK_CD = dobj.getRetObject("R").getRecord().get("PAY_BANK_CD");   //납부 은행 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  ACCT.TCAC_BANK_7  WHERE  BANK_CD  LIKE  SUBSTR(:PAY_BANK_CD,1,3)||'%' ";
        sobj.setSql(query);
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //납부 은행 코드
        return sobj;
    }
    // 이력먼저 등록
    public DOBJ CALLupload_gw87_INS19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS19");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS19");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_gw87_INS19(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS19") ;
        rvobj.Println("INS19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_gw87_INS19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //접수처 구분
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //신청 구분
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   RECPTNUM = dvobj.getRecord().get("RECPTNUM");   //접수번호
        String   PAY_BANK_CD = dvobj.getRecord().get("PAY_BANK_CD");   //납부 은행 코드
        String   PAY_KND = dvobj.getRecord().get("PAY_KND");   //요금 종류
        String   PAY_REQDAY = dvobj.getRecord().get("PAY_REQDAY");   //납부 희망일자
        String   CHGBFR_PAYPRES_NUM = dvobj.getRecord().get("CHGBFR_PAYPRES_NUM");   //변경전 납부자 번호
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //최초 개시일
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //출금 계좌 번호
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //변경후 납부자 번호
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //접수점 코드
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //납부자 전화번호
        String   APPTN_RSLT = dvobj.getRecord().get("APPTN_RSLT");   //신청 결과
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //신청 일자
        String   ERR_GBN ="";   //에러 구분
        String   GBN ="22";   //구분
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");   //일련 번호
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYER_PHONNUM, GBN, PAYPRES_NM, RECPT_BANK_CD, CHGATR_PAYPRES_NUM, PAY_ACCN_NUM, PROC_DAY, BEGNG_RELSDAY, SEQ_NUM, CHGBFR_PAYPRES_NUM, PAY_REQDAY, PAY_KND, APPTN_DAY, PAY_BANK_CD, RECPTNUM, UPSO_CD, RESINUM, ERR_GBN, APPTN_GBN, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYER_PHONNUM , :GBN , :PAYPRES_NM , :RECPT_BANK_CD , :CHGATR_PAYPRES_NUM , :PAY_ACCN_NUM , :PROC_DAY , :BEGNG_RELSDAY , :SEQ_NUM , :CHGBFR_PAYPRES_NUM , :PAY_REQDAY , :PAY_KND , :APPTN_DAY , :PAY_BANK_CD , :RECPTNUM , :UPSO_CD , :RESINUM , :ERR_GBN , :APPTN_GBN , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //접수처 구분
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("RECPTNUM", RECPTNUM);               //접수번호
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //납부 은행 코드
        sobj.setString("PAY_KND", PAY_KND);               //요금 종류
        sobj.setString("PAY_REQDAY", PAY_REQDAY);               //납부 희망일자
        sobj.setString("CHGBFR_PAYPRES_NUM", CHGBFR_PAYPRES_NUM);               //변경전 납부자 번호
        sobj.setString("BEGNG_RELSDAY", BEGNG_RELSDAY);               //최초 개시일
        sobj.setString("PAY_ACCN_NUM", PAY_ACCN_NUM);               //출금 계좌 번호
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //변경후 납부자 번호
        sobj.setString("RECPT_BANK_CD", RECPT_BANK_CD);               //접수점 코드
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("PAYER_PHONNUM", PAYER_PHONNUM);               //납부자 전화번호
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //신청 결과
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("ERR_GBN", ERR_GBN);               //에러 구분
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 청구 데이터 생성
    // 청구 데이터를 생성하기 위한 프로시저 (GIBU.SP_PROC_DEMDGIRO) 를 호출한다
    public DOBJ CALLupload_gw87_OSP20(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP20");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("OSP20");
        String[]  incolumns ={"UPSO_CD","PROC_DAY","SEQ_NUM","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");         //처리 일자
            record.put("PROC_DAY",PROC_DAY);
            ////
            String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");         //일련 번호
            record.put("SEQ_NUM",SEQ_NUM);
            ////
            String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");         //업소 코드
            record.put("UPSO_CD",UPSO_CD);
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_GW81_CHECK";
        int[]    intypes={12, 12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP20");
        robj.Println("OSP20");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 은행정보에러
    public DOBJ CALLupload_gw87_INS21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS21");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS21");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_gw87_INS21(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS21") ;
        rvobj.Println("INS21");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_gw87_INS21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //접수처 구분
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //신청 구분
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   RECPTNUM = dvobj.getRecord().get("RECPTNUM");   //접수번호
        String   PAY_BANK_CD = dvobj.getRecord().get("PAY_BANK_CD");   //납부 은행 코드
        String   PAY_KND = dvobj.getRecord().get("PAY_KND");   //요금 종류
        String   PAY_REQDAY = dvobj.getRecord().get("PAY_REQDAY");   //납부 희망일자
        String   CHGBFR_PAYPRES_NUM = dvobj.getRecord().get("CHGBFR_PAYPRES_NUM");   //변경전 납부자 번호
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //최초 개시일
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //출금 계좌 번호
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //변경후 납부자 번호
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //접수점 코드
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //납부자 전화번호
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //신청 일자
        String   APPTN_RSLT ="99";   //신청 결과
        String   ERR_GBN ="0003";   //에러 구분
        String   GBN ="22";   //구분
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");   //일련 번호
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYER_PHONNUM, GBN, PAYPRES_NM, RECPT_BANK_CD, CHGATR_PAYPRES_NUM, PAY_ACCN_NUM, PROC_DAY, BEGNG_RELSDAY, SEQ_NUM, CHGBFR_PAYPRES_NUM, PAY_REQDAY, PAY_KND, APPTN_DAY, PAY_BANK_CD, RECPTNUM, UPSO_CD, RESINUM, ERR_GBN, APPTN_GBN, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYER_PHONNUM , :GBN , :PAYPRES_NM , :RECPT_BANK_CD , :CHGATR_PAYPRES_NUM , :PAY_ACCN_NUM , :PROC_DAY , :BEGNG_RELSDAY , :SEQ_NUM , :CHGBFR_PAYPRES_NUM , :PAY_REQDAY , :PAY_KND , :APPTN_DAY , :PAY_BANK_CD , :RECPTNUM , :UPSO_CD , :RESINUM , :ERR_GBN , :APPTN_GBN , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //접수처 구분
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("RECPTNUM", RECPTNUM);               //접수번호
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //납부 은행 코드
        sobj.setString("PAY_KND", PAY_KND);               //요금 종류
        sobj.setString("PAY_REQDAY", PAY_REQDAY);               //납부 희망일자
        sobj.setString("CHGBFR_PAYPRES_NUM", CHGBFR_PAYPRES_NUM);               //변경전 납부자 번호
        sobj.setString("BEGNG_RELSDAY", BEGNG_RELSDAY);               //최초 개시일
        sobj.setString("PAY_ACCN_NUM", PAY_ACCN_NUM);               //출금 계좌 번호
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //변경후 납부자 번호
        sobj.setString("RECPT_BANK_CD", RECPT_BANK_CD);               //접수점 코드
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("PAYER_PHONNUM", PAYER_PHONNUM);               //납부자 전화번호
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //신청 결과
        sobj.setString("ERR_GBN", ERR_GBN);               //에러 구분
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 신청서 등록
    // 주민번호 에러가 났음을 기록하자
    public DOBJ CALLupload_gw87_INS22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS22");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_gw87_INS22(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS22") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_gw87_INS22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String BANK_CD ="";        //은행 코드
        String CONFIRM_DATE ="";        //확인 일자
        String INS_DATE ="";        //등록 일시
        double SEQ = 0;        //관리번호
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
        String   SEQ_1 = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //관리번호
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   BANK_CD_1 = dobj.getRetObject("R").getRecord().get("PAY_BANK_CD");   //은행 코드
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   APPLICATION_GBN ="71";   //신청 일자
        String   AUTO_ACCNNUM = dobj.getRetObject("R").getRecord().get("PAY_ACCN_NUM");   //출금 계좌번호
        String   CONFIRM_ID = dobj.getRetObject("R").getRecord().get("INSPRES_ID");   //계산 일자
        String   PHON_NUM = dobj.getRetObject("R").getRecord().get("PAYER_PHONNUM");   //전화 번호
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        String   RECEPTION_GBN ="9";   //접수처
        String   REMAK =" 99:은행정보 에러";   //비고
        String   STAFF_CD = dobj.getRetObject("R").getRecord().get("INSPRES_ID");   //사원 코드
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO_APPLICATION (CONFIRM_ID, INSPRES_ID, BANK_CD, PAYPRES_NM, APPLICATION_GBN, PHON_NUM, SEQ, PROC_DAY, INS_DATE, STAFF_CD, APPTN_DAY, UPSO_CD, AUTO_ACCNNUM, RECEPTION_GBN, RESINUM, CONFIRM_DATE, REMAK)  \n";
        query +=" values(:CONFIRM_ID , :INSPRES_ID , SUBSTR(:BANK_CD_1, 1,3), :PAYPRES_NM , :APPLICATION_GBN , :PHON_NUM , (SELECT NVL(MAX(SEQ), 0) + 1 SEQ FROM GIBU.TBRA_UPSO_AUTO_APPLICATION WHERE UPSO_CD = :SEQ_1), :PROC_DAY , SYSDATE, :STAFF_CD , :APPTN_DAY , :UPSO_CD , :AUTO_ACCNNUM , :RECEPTION_GBN , :RESINUM , SYSDATE, :REMAK )";
        sobj.setSql(query);
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("SEQ_1", SEQ_1);               //관리번호
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("BANK_CD_1", BANK_CD_1);               //은행 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("APPLICATION_GBN", APPLICATION_GBN);               //신청 일자
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //출금 계좌번호
        sobj.setString("CONFIRM_ID", CONFIRM_ID);               //계산 일자
        sobj.setString("PHON_NUM", PHON_NUM);               //전화 번호
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //접수처
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 이력먼저 등록
    public DOBJ CALLupload_gw87_INS45(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS45");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS45");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_gw87_INS45(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS45") ;
        rvobj.Println("INS45");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_gw87_INS45(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //접수처 구분
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //신청 구분
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   RECPTNUM = dvobj.getRecord().get("RECPTNUM");   //접수번호
        String   PAY_BANK_CD = dvobj.getRecord().get("PAY_BANK_CD");   //납부 은행 코드
        String   PAY_KND = dvobj.getRecord().get("PAY_KND");   //요금 종류
        String   PAY_REQDAY = dvobj.getRecord().get("PAY_REQDAY");   //납부 희망일자
        String   CHGBFR_PAYPRES_NUM = dvobj.getRecord().get("CHGBFR_PAYPRES_NUM");   //변경전 납부자 번호
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //최초 개시일
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //출금 계좌 번호
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //변경후 납부자 번호
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //접수점 코드
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //납부자 전화번호
        String   APPTN_RSLT = dvobj.getRecord().get("APPTN_RSLT");   //신청 결과
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //신청 일자
        String   ERR_GBN ="";   //에러 구분
        String   GBN ="22";   //구분
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");   //일련 번호
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYER_PHONNUM, GBN, PAYPRES_NM, RECPT_BANK_CD, CHGATR_PAYPRES_NUM, PAY_ACCN_NUM, PROC_DAY, BEGNG_RELSDAY, SEQ_NUM, CHGBFR_PAYPRES_NUM, PAY_REQDAY, PAY_KND, APPTN_DAY, PAY_BANK_CD, RECPTNUM, UPSO_CD, RESINUM, ERR_GBN, APPTN_GBN, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYER_PHONNUM , :GBN , :PAYPRES_NM , :RECPT_BANK_CD , :CHGATR_PAYPRES_NUM , :PAY_ACCN_NUM , :PROC_DAY , :BEGNG_RELSDAY , :SEQ_NUM , :CHGBFR_PAYPRES_NUM , :PAY_REQDAY , :PAY_KND , :APPTN_DAY , :PAY_BANK_CD , :RECPTNUM , :UPSO_CD , :RESINUM , :ERR_GBN , :APPTN_GBN , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //접수처 구분
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("RECPTNUM", RECPTNUM);               //접수번호
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //납부 은행 코드
        sobj.setString("PAY_KND", PAY_KND);               //요금 종류
        sobj.setString("PAY_REQDAY", PAY_REQDAY);               //납부 희망일자
        sobj.setString("CHGBFR_PAYPRES_NUM", CHGBFR_PAYPRES_NUM);               //변경전 납부자 번호
        sobj.setString("BEGNG_RELSDAY", BEGNG_RELSDAY);               //최초 개시일
        sobj.setString("PAY_ACCN_NUM", PAY_ACCN_NUM);               //출금 계좌 번호
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //변경후 납부자 번호
        sobj.setString("RECPT_BANK_CD", RECPT_BANK_CD);               //접수점 코드
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("PAYER_PHONNUM", PAYER_PHONNUM);               //납부자 전화번호
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //신청 결과
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("ERR_GBN", ERR_GBN);               //에러 구분
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 청구 데이터 생성
    // 청구 데이터를 생성하기 위한 프로시저 (GIBU.SP_PROC_DEMDGIRO) 를 호출한다
    public DOBJ CALLupload_gw87_OSP46(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP46");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("OSP46");
        String[]  incolumns ={"UPSO_CD","PROC_DAY","SEQ_NUM","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");         //처리 일자
            record.put("PROC_DAY",PROC_DAY);
            ////
            String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");         //일련 번호
            record.put("SEQ_NUM",SEQ_NUM);
            ////
            String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");         //업소 코드
            record.put("UPSO_CD",UPSO_CD);
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_GW81_CHECK";
        int[]    intypes={12, 12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP46");
        robj.Println("OSP46");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 업소조회에러
    // 납부자번호 구성쳬게가 틀린 경우
    public DOBJ CALLupload_gw87_INS9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS9");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_gw87_INS9(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS9") ;
        rvobj.Println("INS9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_gw87_INS9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //접수처 구분
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //신청 구분
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   RECPTNUM = dvobj.getRecord().get("RECPTNUM");   //접수번호
        String   PAY_BANK_CD = dvobj.getRecord().get("PAY_BANK_CD");   //납부 은행 코드
        String   PAY_KND = dvobj.getRecord().get("PAY_KND");   //요금 종류
        String   PAY_REQDAY = dvobj.getRecord().get("PAY_REQDAY");   //납부 희망일자
        String   CHGBFR_PAYPRES_NUM = dvobj.getRecord().get("CHGBFR_PAYPRES_NUM");   //변경전 납부자 번호
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //최초 개시일
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //출금 계좌 번호
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //변경후 납부자 번호
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //접수점 코드
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //납부자 전화번호
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //신청 일자
        String   APPTN_RSLT ="02";   //신청 결과
        String   ERR_GBN ="0001";   //에러 구분
        String   GBN ="22";   //구분
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        String   SEQ_NUM = dobj.getRetObject("SEL70").getRecord().get("SEQ_NUM");   //관리번호
        String   UPSO_CD ="";   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYER_PHONNUM, GBN, PAYPRES_NM, RECPT_BANK_CD, CHGATR_PAYPRES_NUM, PAY_ACCN_NUM, PROC_DAY, BEGNG_RELSDAY, SEQ_NUM, CHGBFR_PAYPRES_NUM, PAY_REQDAY, PAY_KND, APPTN_DAY, PAY_BANK_CD, RECPTNUM, UPSO_CD, RESINUM, ERR_GBN, APPTN_GBN, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYER_PHONNUM , :GBN , :PAYPRES_NM , :RECPT_BANK_CD , :CHGATR_PAYPRES_NUM , :PAY_ACCN_NUM , :PROC_DAY , :BEGNG_RELSDAY , :SEQ_NUM , :CHGBFR_PAYPRES_NUM , :PAY_REQDAY , :PAY_KND , :APPTN_DAY , :PAY_BANK_CD , :RECPTNUM , :UPSO_CD , :RESINUM , :ERR_GBN , :APPTN_GBN , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //접수처 구분
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("RECPTNUM", RECPTNUM);               //접수번호
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //납부 은행 코드
        sobj.setString("PAY_KND", PAY_KND);               //요금 종류
        sobj.setString("PAY_REQDAY", PAY_REQDAY);               //납부 희망일자
        sobj.setString("CHGBFR_PAYPRES_NUM", CHGBFR_PAYPRES_NUM);               //변경전 납부자 번호
        sobj.setString("BEGNG_RELSDAY", BEGNG_RELSDAY);               //최초 개시일
        sobj.setString("PAY_ACCN_NUM", PAY_ACCN_NUM);               //출금 계좌 번호
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //변경후 납부자 번호
        sobj.setString("RECPT_BANK_CD", RECPT_BANK_CD);               //접수점 코드
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("PAYER_PHONNUM", PAYER_PHONNUM);               //납부자 전화번호
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //신청 결과
        sobj.setString("ERR_GBN", ERR_GBN);               //에러 구분
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("SEQ_NUM", SEQ_NUM);               //관리번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$upload_gw87
    //##**$$result_gw87
    /*
    */
    public DOBJ CTLresult_gw87(DOBJ dobj)
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
            dobj  = CALLresult_gw87_SEL1(Conn, dobj);           //  전체리스트
            dobj  = CALLresult_gw87_SEL2(Conn, dobj);           //  에러리스트
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
    public DOBJ CTLresult_gw87( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLresult_gw87_SEL1(Conn, dobj);           //  전체리스트
        dobj  = CALLresult_gw87_SEL2(Conn, dobj);           //  에러리스트
        return dobj;
    }
    // 전체리스트
    public DOBJ CALLresult_gw87_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLresult_gw87_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLresult_gw87_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD||XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XB.MONPRNCFEE  ,  XA.MNGEMSTR_NM  ,  DECODE(LENGTH(XA.MNGEMSTR_RESINUM),13,  SUBSTR(XA.MNGEMSTR_RESINUM,0,6)||'0000000',  XA.MNGEMSTR_RESINUM)  MNGEMSTR_RESINUM  ,  XA.UPSO_PHON  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  XE.BANK_NM||XE.SHOP_NM  BANK_NM  ,  XC.PROC_DAY  ,  XC.GBN  ,  XC.SEQ_NUM  ,  XC.APPTN_DAY  ,  XC.APPTN_GBN  ,  XC.APPTN_RSLT  ,  XC.CHGBFR_PAYPRES_NUM  ,  XC.CHGATR_PAYPRES_NUM  ,  XC.PAY_REQDAY  ,  XC.PAY_KND  ,  XC.PAY_BANK_CD  ,  XC.PAY_ACCN_NUM  ,  DECODE(LENGTH(XC.RESINUM),13,  SUBSTR(XC.RESINUM,0,6)||'0000000',  XC.RESINUM)  RESINUM  ,  XC.PAYER_PHONNUM  ,  XC.RECPT_BANK_CD  ,  XC.BEGNG_RELSDAY  ,  XC.PAYPRES_NM  ,  XC.ERR_GBN  ,  XC.RECPTNUM  ,  XC.RECPT_GBN_CD  FROM  GIBU.TBRA_UPSO  XA,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  GIBU.TBRA_UPSO_AUTORSLT  XC  ,  INSA.TCPM_DEPT  XD  ,  ACCT.TCAC_BANK  XE  WHERE  XC.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  XC.GBN  =  '22'   \n";
        query +=" AND  XA.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XD.GIBU(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XE.BANK_CD(+)  =  SUBSTR(XC.PAY_BANK_CD  ,1,3)  ORDER  BY  XC.SEQ_NUM ";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    // 에러리스트
    public DOBJ CALLresult_gw87_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLresult_gw87_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLresult_gw87_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD||XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XB.MONPRNCFEE  ,  XA.MNGEMSTR_NM  ,  DECODE(LENGTH(XA.MNGEMSTR_RESINUM),13,  SUBSTR(XA.MNGEMSTR_RESINUM,0,6)||'0000000',  XA.MNGEMSTR_RESINUM)  MNGEMSTR_RESINUM  ,  XA.UPSO_PHON  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  XE.BANK_NM||'  '||XE.SHOP_NM  BANK_NM  ,  XC.PROC_DAY  ,  XC.GBN  ,  XC.SEQ_NUM  ,  XC.APPTN_DAY  ,  XC.APPTN_GBN  ,  XC.APPTN_RSLT  ,  XF.CODE_NM  ,  XC.CHGBFR_PAYPRES_NUM  ,  XC.CHGATR_PAYPRES_NUM  ,  XC.PAY_REQDAY  ,  XC.PAY_KND  ,  XC.PAY_BANK_CD  ,  XC.PAY_ACCN_NUM  ,  DECODE(LENGTH(XC.RESINUM),13,  SUBSTR(XC.RESINUM,0,6)||'0000000',  XC.RESINUM)  RESINUM  ,  XC.PAYER_PHONNUM  ,  XC.RECPT_BANK_CD  ,  XC.BEGNG_RELSDAY  ,  XC.PAYPRES_NM  ,  XC.ERR_GBN  ,  XC.RECPTNUM  ,  XC.RECPT_GBN_CD  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  GIBU.TBRA_UPSO_AUTORSLT  XC  ,  INSA.TCPM_DEPT  XD  ,  ACCT.TCAC_BANK_7  XE  ,  FIDU.TENV_CODE  XF  WHERE  XC.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  XC.GBN  =  '22'   \n";
        query +=" AND  XA.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XE.BANK_CD(+)  =  XC.PAY_BANK_CD   \n";
        query +=" AND  XF.HIGH_CD  =  '00324'   \n";
        query +=" AND  XF.CODE_CD  =  XC.ERR_GBN  ORDER  BY  XC.SEQ_NUM ";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    //##**$$result_gw87
    //##**$$end
}
