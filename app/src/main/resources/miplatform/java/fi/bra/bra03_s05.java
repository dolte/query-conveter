
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s05
{
    public bra03_s05()
    {
    }
    //##**$$auto_apptn_upload
    /*
    */
    public DOBJ CTLauto_apptn_upload(DOBJ dobj)
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
            dobj  = CALLauto_apptn_upload_XIUD21(Conn, dobj);           //  은행접수신청서삭제
            dobj  = CALLauto_apptn_upload_XIUD16(Conn, dobj);           //  은행접수신청서삭제
            dobj  = CALLauto_apptn_upload_MPD2(Conn, dobj);           //  건별처리
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
    public DOBJ CTLauto_apptn_upload( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_apptn_upload_XIUD21(Conn, dobj);           //  은행접수신청서삭제
        dobj  = CALLauto_apptn_upload_XIUD16(Conn, dobj);           //  은행접수신청서삭제
        dobj  = CALLauto_apptn_upload_MPD2(Conn, dobj);           //  건별처리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 은행접수신청서삭제
    // 처리일자에 해당하는 은행접수신청서를 삭제한다.
    public DOBJ CALLauto_apptn_upload_XIUD21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD21");
        VOBJ dvobj = dobj.getRetObject("S1");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_upload_XIUD21(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD21");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload_XIUD21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE  \n";
        query +=" GIBU.TBRA_UPSO_AUTORSLT A  \n";
        query +=" WHERE A.PROC_DAY = :PROC_DAY  \n";
        query +=" AND A.SEQ_NUM LIKE '0%'  \n";
        query +=" AND A.GBN = '62'";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    // 은행접수신청서삭제
    // 처리일자에 해당하는 은행접수신청서를 삭제한다.
    public DOBJ CALLauto_apptn_upload_XIUD16(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLauto_apptn_upload_XIUD16(dobj, dvobj);
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
    private SQLObject SQLauto_apptn_upload_XIUD16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE GIBU.TBRA_UPSO_AUTO_APPLICATION A  \n";
        query +=" WHERE A.PROC_DAY = :PROC_DAY  \n";
        query +=" AND A.RECEPTION_GBN <> '5'";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    // 건별처리
    public DOBJ CALLauto_apptn_upload_MPD2(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLauto_apptn_upload_SEL3(Conn, dobj);           //  업소체크
                if(!dobj.getRetObject("SEL3").getRecord().get("UPSO_CD").equals("") && !dobj.getRetObject("R").getRecord().get("RECPT_GBN_CD").equals("5"))
                {
                    dobj  = CALLauto_apptn_upload_SEL5(Conn, dobj);           //  주민번호체크
                    if(( !dobj.getRetObject("SEL3").getRecord().get("RESINUM").equals(dobj.getRetObject("R").getRecord().get("RESINUM")) ) && ( dobj.getRetObject("SEL5").getRecord().getDouble("CNT") == 0 ))
                    {
                        dobj  = CALLauto_apptn_upload_INS6(Conn, dobj);           //  주민번호에러
                        dobj  = CALLauto_apptn_upload_INS32(Conn, dobj);           //  신청서 등록
                    }
                    else
                    {
                        dobj  = CALLauto_apptn_upload_SEL7(Conn, dobj);           //  은행정보체크
                        if( dobj.getRetObject("SEL7").getRecord().getDouble("CNT") > 0)
                        {
                            dobj  = CALLauto_apptn_upload_INS9(Conn, dobj);           //  이력먼저 등록
                            dobj  = CALLauto_apptn_upload_OSP14(Conn, dobj);           //  자동이체업소로 등록
                        }
                        else
                        {
                            dobj  = CALLauto_apptn_upload_INS8(Conn, dobj);           //  은행정보에러
                            dobj  = CALLauto_apptn_upload_INS33(Conn, dobj);           //  신청서 등록
                        }
                    }
                }
                else if(!dobj.getRetObject("SEL3").getRecord().get("UPSO_CD").equals("") && dobj.getRetObject("R").getRecord().get("RECPT_GBN_CD").equals("5"))
                {
                    dobj  = CALLauto_apptn_upload_INS43(Conn, dobj);           //  이력먼저 등록
                    dobj  = CALLauto_apptn_upload_OSP44(Conn, dobj);           //  자동이체업소로 등록
                }
                else
                {
                    dobj  = CALLauto_apptn_upload_INS42(Conn, dobj);           //  업소조회에러
                }
            }
        }
        return dobj;
    }
    // 업소체크
    public DOBJ CALLauto_apptn_upload_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_apptn_upload_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // 주민번호체크
    public DOBJ CALLauto_apptn_upload_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_apptn_upload_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
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
    public DOBJ CALLauto_apptn_upload_INS6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_upload_INS6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload_INS6(DOBJ dobj, VOBJ dvobj) throws Exception
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
        String   SEQ_NUM = dvobj.getRecord().get("SEQ_NUM");   //일련 번호
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //최초 개시일
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //출금 계좌 번호
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //변경후 납부자 번호
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //접수점 코드
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //납부자 전화번호
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //신청 일자
        String   APPTN_RSLT ="03";   //신청 결과
        String   ERR_GBN ="0002";   //에러 구분
        String   GBN ="62";   //구분
        String   PROC_DAY = dobj.getRetObject("R").getRecord().get("PROC_DAY");   //처리 일자
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
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
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
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 신청서 등록
    // 주민번호 에러가 났음을 기록하자
    public DOBJ CALLauto_apptn_upload_INS32(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS32");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_upload_INS32(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS32") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload_INS32(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String BANK_CD ="";        //은행 코드
        String CONFIRM_DATE ="";        //확인 일자
        String INS_DATE ="";        //등록 일시
        double SEQ = 0;        //관리번호
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
        String   PROC_DAY = dvobj.getRecord().get("PROC_DAY");   //처리 일자
        String   SEQ_1 = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //관리번호
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   BANK_CD_1 = dobj.getRetObject("R").getRecord().get("PAY_BANK_CD");   //은행 코드
        String   APPLICATION_GBN = dobj.getRetObject("R").getRecord().get("APPTN_GBN");   //신청 일자
        String   AUTO_ACCNNUM = dobj.getRetObject("R").getRecord().get("PAY_ACCN_NUM");   //출금 계좌번호
        String   CONFIRM_ID ="AUTO";   //계산 일자
        String   INSPRES_ID ="AUTO";   //등록자 ID
        String   PHON_NUM = dobj.getRetObject("R").getRecord().get("PAYER_PHONNUM");   //전화 번호
        String   RECEPTION_GBN = dobj.getRetObject("R").getRecord().get("RECPT_GBN_CD");   //접수처
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
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("SEQ_1", SEQ_1);               //관리번호
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("BANK_CD_1", BANK_CD_1);               //은행 코드
        sobj.setString("APPLICATION_GBN", APPLICATION_GBN);               //신청 일자
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //출금 계좌번호
        sobj.setString("CONFIRM_ID", CONFIRM_ID);               //계산 일자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("PHON_NUM", PHON_NUM);               //전화 번호
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //접수처
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 은행정보체크
    // 납부자계좌개설점코드 (7)  - 7자리의 숫자이나, 은행코드 3자리만 수록하고 뒤의 4자리는"0000"이 가능하므로 앞의 3자리고 LIKE 검색을 한다
    public DOBJ CALLauto_apptn_upload_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_apptn_upload_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        rvobj.Println("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
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
    public DOBJ CALLauto_apptn_upload_INS9(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLauto_apptn_upload_INS9(dobj, dvobj);
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
    private SQLObject SQLauto_apptn_upload_INS9(DOBJ dobj, VOBJ dvobj) throws Exception
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
        String   SEQ_NUM = dvobj.getRecord().get("SEQ_NUM");   //일련 번호
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //최초 개시일
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //출금 계좌 번호
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //변경후 납부자 번호
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //접수점 코드
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //납부자 전화번호
        String   APPTN_RSLT = dvobj.getRecord().get("APPTN_RSLT");   //신청 결과
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //신청 일자
        String   ERR_GBN ="";   //에러 구분
        String   GBN ="62";   //구분
        String   PROC_DAY = dobj.getRetObject("R").getRecord().get("PROC_DAY");   //처리 일자
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
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
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
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 자동이체업소로 등록
    public DOBJ CALLauto_apptn_upload_OSP14(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP14");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("OSP14");
        String[]  incolumns ={"UPSO_CD","PROC_DAY","SEQ_NUM","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
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
        robj.setName("OSP14");
        robj.Println("OSP14");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 은행정보에러
    public DOBJ CALLauto_apptn_upload_INS8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS8");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_upload_INS8(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS8") ;
        rvobj.Println("INS8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload_INS8(DOBJ dobj, VOBJ dvobj) throws Exception
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
        String   SEQ_NUM = dvobj.getRecord().get("SEQ_NUM");   //일련 번호
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //최초 개시일
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //출금 계좌 번호
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //변경후 납부자 번호
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //접수점 코드
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //납부자 전화번호
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //신청 일자
        String   APPTN_RSLT ="99";   //신청 결과
        String   ERR_GBN ="0003";   //에러 구분
        String   GBN ="62";   //구분
        String   PROC_DAY = dobj.getRetObject("R").getRecord().get("PROC_DAY");   //처리 일자
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
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
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
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 신청서 등록
    // 주민번호 에러가 났음을 기록하자
    public DOBJ CALLauto_apptn_upload_INS33(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS33");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_upload_INS33(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS33") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload_INS33(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String BANK_CD ="";        //은행 코드
        String CONFIRM_DATE ="";        //확인 일자
        String INS_DATE ="";        //등록 일시
        double SEQ = 0;        //관리번호
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
        String   PROC_DAY = dvobj.getRecord().get("PROC_DAY");   //처리 일자
        String   SEQ_1 = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //관리번호
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   BANK_CD_1 = dobj.getRetObject("R").getRecord().get("PAY_BANK_CD");   //은행 코드
        String   APPLICATION_GBN = dobj.getRetObject("R").getRecord().get("APPTN_GBN");   //신청 일자
        String   AUTO_ACCNNUM = dobj.getRetObject("R").getRecord().get("PAY_ACCN_NUM");   //출금 계좌번호
        String   CONFIRM_ID ="AUTO";   //계산 일자
        String   INSPRES_ID ="AUTO";   //등록자 ID
        String   PHON_NUM = dobj.getRetObject("R").getRecord().get("PAYER_PHONNUM");   //전화 번호
        String   RECEPTION_GBN = dobj.getRetObject("R").getRecord().get("RECPT_GBN_CD");   //접수처
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
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("SEQ_1", SEQ_1);               //관리번호
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("BANK_CD_1", BANK_CD_1);               //은행 코드
        sobj.setString("APPLICATION_GBN", APPLICATION_GBN);               //신청 일자
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //출금 계좌번호
        sobj.setString("CONFIRM_ID", CONFIRM_ID);               //계산 일자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("PHON_NUM", PHON_NUM);               //전화 번호
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //접수처
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 이력먼저 등록
    public DOBJ CALLauto_apptn_upload_INS43(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS43");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS43");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_upload_INS43(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS43") ;
        rvobj.Println("INS43");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload_INS43(DOBJ dobj, VOBJ dvobj) throws Exception
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
        String   SEQ_NUM = dvobj.getRecord().get("SEQ_NUM");   //일련 번호
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //최초 개시일
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //출금 계좌 번호
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //변경후 납부자 번호
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //접수점 코드
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //납부자 전화번호
        String   APPTN_RSLT = dvobj.getRecord().get("APPTN_RSLT");   //신청 결과
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //신청 일자
        String   ERR_GBN ="";   //에러 구분
        String   GBN ="62";   //구분
        String   PROC_DAY = dobj.getRetObject("R").getRecord().get("PROC_DAY");   //처리 일자
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
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
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
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 자동이체업소로 등록
    public DOBJ CALLauto_apptn_upload_OSP44(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP44");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("OSP44");
        String[]  incolumns ={"UPSO_CD","PROC_DAY","SEQ_NUM","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
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
        robj.setName("OSP44");
        robj.Println("OSP44");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 업소조회에러
    // 납부자번호 구성쳬게가 틀린 경우
    public DOBJ CALLauto_apptn_upload_INS42(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS42");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_upload_INS42(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS42") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload_INS42(DOBJ dobj, VOBJ dvobj) throws Exception
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
        String   SEQ_NUM = dvobj.getRecord().get("SEQ_NUM");   //일련 번호
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //최초 개시일
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //출금 계좌 번호
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //변경후 납부자 번호
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //접수점 코드
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //납부자 전화번호
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //신청 일자
        String   APPTN_RSLT ="02";   //신청 결과
        String   ERR_GBN ="0001";   //에러 구분
        String   GBN ="62";   //구분
        String   PROC_DAY = dobj.getRetObject("R").getRecord().get("PROC_DAY");   //처리 일자
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
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
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
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$auto_apptn_upload
    //##**$$auto_err_handling_init
    /* * 프로그램명 : bra03_s05
    * 작성자 : 서정재
    * 작성일 : 2009/09/30
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLauto_err_handling_init(DOBJ dobj)
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
            dobj  = CALLauto_err_handling_init_SEL1(Conn, dobj);           //  정보조회
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
    public DOBJ CTLauto_err_handling_init( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_err_handling_init_SEL1(Conn, dobj);           //  정보조회
        return dobj;
    }
    // 정보조회
    // 자동이체 업로드에서 실패한 업소를 수동으로 정보를 입력해야 할 경우 초기 데이타 로드시켜줌
    public DOBJ CALLauto_err_handling_init_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_err_handling_init_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_err_handling_init_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   PROC_DAY = dvobj.getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD||XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XB.MONPRNCFEE  ,  XA.MNGEMSTR_NM  ,  XA.MNGEMSTR_RESINUM  ,  XA.UPSO_PHON  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  XC.PAY_BANK_CD  ,  XE.BANK_NM||XE.SHOP_NM  BANK_NM  ,  XC.PAY_ACCN_NUM  ,  XC.RESINUM  ,  XC.APPTN_DAY  ,  XC.PAYPRES_NM  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  GIBU.TBRA_UPSO_AUTORSLT  XC  ,  INSA.TCPM_DEPT  XD  ,  ACCT.TCAC_BANK_7  XE  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  XC.GBN  =  '62'   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XA.BRAN_CD   \n";
        query +=" AND  XE.BANK_CD  =  XC.PAY_BANK_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    //##**$$auto_err_handling_init
    //##**$$auto_apptn_upload80
    /*
    */
    public DOBJ CTLauto_apptn_upload80(DOBJ dobj)
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
            dobj  = CALLauto_apptn_upload80_XIUD21(Conn, dobj);           //  은행접수신청서삭제
            dobj  = CALLauto_apptn_upload80_MPD2(Conn, dobj);           //  건별처리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLauto_apptn_upload80_SEL24(Conn, dobj);           //  80결과
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
    public DOBJ CTLauto_apptn_upload80( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_apptn_upload80_XIUD21(Conn, dobj);           //  은행접수신청서삭제
        dobj  = CALLauto_apptn_upload80_MPD2(Conn, dobj);           //  건별처리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLauto_apptn_upload80_SEL24(Conn, dobj);           //  80결과
        return dobj;
    }
    // 은행접수신청서삭제
    // 처리일자에 해당하는 은행접수신청서를 삭제한다.
    public DOBJ CALLauto_apptn_upload80_XIUD21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD21");
        VOBJ dvobj = dobj.getRetObject("S1");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_upload80_XIUD21(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD21");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload80_XIUD21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE  \n";
        query +=" GIBU.TBRA_UPSO_AUTORSLT A  \n";
        query +=" WHERE A.PROC_DAY = :PROC_DAY  \n";
        query +=" AND A.SEQ_NUM LIKE '9%'  \n";
        query +=" AND A.GBN = '62'";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    // 건별처리
    public DOBJ CALLauto_apptn_upload80_MPD2(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLauto_apptn_upload80_SEL3(Conn, dobj);           //  업소체크
                dobj  = CALLauto_apptn_upload80_SEL22(Conn, dobj);           //  MAX 시퀀스 획득
                dobj  = CALLauto_apptn_upload80_INS9(Conn, dobj);           //  이력먼저 등록
                dobj  = CALLauto_apptn_upload80_XIUD23(Conn, dobj);           //  신청서 수정
            }
        }
        return dobj;
    }
    // 업소체크
    public DOBJ CALLauto_apptn_upload80_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_apptn_upload80_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload80_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHGATR_PAYPRES_NUM = dobj.getRetObject("R").getRecord().get("CHGATR_PAYPRES_NUM");   //변경후 납부자 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  FROM  (   \n";
        query +=" SELECT  NVL(MAX(UPSO_CD),'')  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  CLIENT_NUM  =  :CHGATR_PAYPRES_NUM  ) ";
        sobj.setSql(query);
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //변경후 납부자 번호
        return sobj;
    }
    // MAX 시퀀스 획득
    public DOBJ CALLauto_apptn_upload80_SEL22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL22");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL22");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_apptn_upload80_SEL22(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL22");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload80_SEL22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //신청 일자
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //업소 코드
        String   PAY_ACCN_NUM = dobj.getRetObject("R").getRecord().get("PAY_ACCN_NUM");   //출금 계좌 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(SEQ)  AS  SEQ_NUM  FROM  GIBU.TBRA_UPSO_AUTO_APPLICATION  WHERE  PROC_DAY  IS  NOT  NULL   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  APPTN_DAY  =  :APPTN_DAY   \n";
        query +=" AND  AUTO_ACCNNUM  =  :PAY_ACCN_NUM   \n";
        query +=" AND  APPLICATION_GBN  IN(  '02',  '08',  '09') ";
        sobj.setSql(query);
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("PAY_ACCN_NUM", PAY_ACCN_NUM);               //출금 계좌 번호
        return sobj;
    }
    // 이력먼저 등록
    public DOBJ CALLauto_apptn_upload80_INS9(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLauto_apptn_upload80_INS9(dobj, dvobj);
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
    private SQLObject SQLauto_apptn_upload80_INS9(DOBJ dobj, VOBJ dvobj) throws Exception
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
        String   SEQ_NUM = dvobj.getRecord().get("SEQ_NUM");   //일련 번호
        String   BEGNG_RELSDAY = dvobj.getRecord().get("BEGNG_RELSDAY");   //최초 개시일
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //출금 계좌 번호
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //변경후 납부자 번호
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //접수점 코드
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //납부자 전화번호
        String   APPTN_RSLT = dvobj.getRecord().get("APPTN_RSLT");   //신청 결과
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //신청 일자
        String   ERR_GBN ="";   //에러 구분
        String   GBN ="62";   //구분
        String   PROC_DAY = dobj.getRetObject("R").getRecord().get("PROC_DAY");   //처리 일자
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
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
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
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 신청서 수정
    // 결과코드 추가
    public DOBJ CALLauto_apptn_upload80_XIUD23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD23");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_upload80_XIUD23(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD23");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload80_XIUD23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_RSLT = dobj.getRetObject("R").getRecord().get("APPTN_RSLT");   //신청 결과
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" SET AUTO_NUM = '' , REMAK = ( SELECT CODE_CD || ':' || CODE_NM FROM FIDU.TENV_CODE  \n";
        query +=" WHERE HIGH_CD IN ('00388', '00417')  \n";
        query +=" AND CODE_CD = :APPTN_RSLT)  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND SEQ = :APPLIC_SEQ";
        sobj.setSql(query);
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //신청 결과
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 80결과
    public DOBJ CALLauto_apptn_upload80_SEL24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL24");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL24");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_apptn_upload80_SEL24(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL24");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload80_SEL24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD||XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XB.MONPRNCFEE  ,  XA.MNGEMSTR_NM  ,  DECODE(LENGTH(XA.MNGEMSTR_RESINUM),13,  SUBSTR(XA.MNGEMSTR_RESINUM,0,6)||'0000000',  XA.MNGEMSTR_RESINUM)  MNGEMSTR_RESINUM  ,  XA.UPSO_PHON  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  XE.BANK_NM||XE.SHOP_NM  BANK_NM  ,  XC.PROC_DAY  ,  XC.GBN  ,  XC.SEQ_NUM  ,  XC.APPTN_DAY  ,  XC.APPTN_GBN  ,   \n";
        query +=" (SELECT  CODE_CD  ||  ':'  ||  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  IN  ('00388',  '00417')   \n";
        query +=" AND  CODE_CD  =  XC.APPTN_RSLT)  AS  APPTN_RSLT  ,  XC.CHGBFR_PAYPRES_NUM  ,  XC.CHGATR_PAYPRES_NUM  ,  XC.PAY_REQDAY  ,  XC.PAY_KND  ,  XC.PAY_BANK_CD  ,  XC.PAY_ACCN_NUM  ,  DECODE(LENGTH(XC.RESINUM),13,  SUBSTR(XC.RESINUM,0,6)||'0000000',  XC.RESINUM)  RESINUM  ,  XC.PAYER_PHONNUM  ,  XC.RECPT_BANK_CD  ,  XC.BEGNG_RELSDAY  ,  XC.PAYPRES_NM  ,  XC.ERR_GBN  ,  XC.RECPTNUM  ,  XC.RECPT_GBN_CD  FROM  GIBU.TBRA_UPSO  XA,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  GIBU.TBRA_UPSO_AUTORSLT  XC  ,  INSA.TCPM_DEPT  XD  ,  ACCT.TCAC_BANK  XE  WHERE  XC.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  XC.GBN  =  '62'   \n";
        query +=" AND  XA.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XD.GIBU(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XE.BANK_CD(+)  =  SUBSTR(XC.PAY_BANK_CD  ,1,3)   \n";
        query +=" AND  XC.SEQ_NUM  LIKE  '9%'  ORDER  BY  XC.SEQ_NUM ";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    //##**$$auto_apptn_upload80
    //##**$$auto_err_handling
    /*
    */
    public DOBJ CTLauto_err_handling(DOBJ dobj)
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
            dobj  = CALLauto_err_handling_MPD3(Conn, dobj);           //  건별처리
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
    public DOBJ CTLauto_err_handling( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_err_handling_MPD3(Conn, dobj);           //  건별처리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 건별처리
    public DOBJ CALLauto_err_handling_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("MPD3");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("CHK_YN").equals("1") && ( dvobj.getRecord().get("APPTN_GBN").equals("01") || dvobj.getRecord().get("APPTN_GBN").equals("03") || dvobj.getRecord().get("APPTN_GBN").equals("04") ))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_err_handling_SEL7(Conn, dobj);           //  중복검사
                if( dobj.getRetObject("SEL7").getRecord().getDouble("CNT") == 0)
                {
                    dobj  = CALLauto_err_handling_INS6(Conn, dobj);           //  자동이체신청수동등록
                    dobj  = CALLauto_err_handling_INS35(Conn, dobj);           //  신규등록
                    if( dobj.getRetObject("R").getRecord().get("GUBUN").equals("U"))
                    {
                        dobj  = CALLauto_err_handling_UPD19(Conn, dobj);           //  업소정보변경
                    }
                }
                else
                {
                    dobj.setRtncode(9);
                    if(dobj.getRtncode() == 9)
                    {
                        Conn.rollback();
                        return dobj;
                    }
                }
            }
            else if( dvobj.getRecord().get("CHK_YN").equals("1") && ( dvobj.getRecord().get("APPTN_GBN").equals("02") || dvobj.getRecord().get("APPTN_GBN").equals("08") || dvobj.getRecord().get("APPTN_GBN").equals("09") ))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_err_handling_SEL46(Conn, dobj);           //  신청서를 찾아라
                if( dobj.getRetObject("SEL46").getRecord().getDouble("SEQ") > 0)
                {
                    dobj  = CALLauto_err_handling_INS20(Conn, dobj);           //  자동이체신청수동등록
                    dobj  = CALLauto_err_handling_INS48(Conn, dobj);           //  신규등록
                    if( dobj.getRetObject("R").getRecord().get("GUBUN").equals("U"))
                    {
                        dobj  = CALLauto_err_handling_UPD20(Conn, dobj);           //  업소정보변경
                    }
                }
                else
                {
                    dobj.setRtncode(9);
                    if(dobj.getRtncode() == 9)
                    {
                        Conn.rollback();
                        return dobj;
                    }
                }
            }
        }
        return dobj;
    }
    // 중복검사
    // 수동으로 중복등록 안되게
    public DOBJ CALLauto_err_handling_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_err_handling_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_err_handling_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BANK_CD = dobj.getRetObject("R").getRecord().get("PAY_BANK_CD");   //은행 코드
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("PROC_DAY");   //신청 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   AUTO_ACCNNUM = dobj.getRetObject("R").getRecord().get("PAY_ACCN_NUM");   //출금 계좌번호
        String   RESINUM = dobj.getRetObject("R").getRecord().get("RESINUM");   //주민번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  BANK_CD  =  :BANK_CD   \n";
        query +=" AND  AUTO_ACCNNUM  =  :AUTO_ACCNNUM   \n";
        query +=" AND  APPTN_DAY  =  :APPTN_DAY   \n";
        query +=" AND  RESINUM  =  :RESINUM   \n";
        query +=" AND  TERM_YN  =  'N'   \n";
        query +=" AND  PROC_GBN  =  'N' ";
        sobj.setSql(query);
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //출금 계좌번호
        sobj.setString("RESINUM", RESINUM);               //주민번호
        return sobj;
    }
    // 신청서를 찾아라
    public DOBJ CALLauto_err_handling_SEL46(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL46");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL46");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_err_handling_SEL46(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL46");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_err_handling_SEL46(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //신청 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   AUTO_ACCNNUM = dobj.getRetObject("R").getRecord().get("PAY_ACCN_NUM");   //출금 계좌번호
        String   APPTN_GBN = dobj.getRetObject("R").getRecord().get("APPTN_GBN");   //신청 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  -1)  AS  SEQ  FROM  GIBU.TBRA_UPSO_AUTO_APPLICATION  WHERE  PROC_DAY  IS  NOT  NULL   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  APPTN_DAY  =  :APPTN_DAY   \n";
        query +=" AND  AUTO_ACCNNUM  =  :AUTO_ACCNNUM   \n";
        query +=" AND  APPLICATION_GBN  =  :APPTN_GBN   \n";
        query +=" AND  RECEPTION_GBN  IN  ('4','6') ";
        sobj.setSql(query);
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //출금 계좌번호
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        return sobj;
    }
    // 자동이체신청수동등록
    // 자동이체신청 UPLOAD 시 에러난 업소를 수동으로 등록한다. 이때 처리구분을 수동으로 한다.(PROC_GBN=="N")
    public DOBJ CALLauto_err_handling_INS6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_err_handling_INS6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS6") ;
        rvobj.Println("INS6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_err_handling_INS6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int  AUTO_NUM = 0;        //일련 번호
        String INS_DATE ="";        //등록 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   AUTO_NUM_1 = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //일련 번호
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("PROC_DAY");   //신청 일자
        String   AUTO_ACCNNUM = dobj.getRetObject("R").getRecord().get("PAY_ACCN_NUM");   //출금 계좌번호
        String   BANK_CD = dobj.getRetObject("R").getRecord().get("PAY_BANK_CD");   //은행 코드
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   PROC_GBN ="N";   //처리 구분
        String   RECEPTION_GBN = dobj.getRetObject("R").getRecord().get("RECPT_GBN_CD");   //접수처
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO (INS_DATE, INSPRES_ID, BANK_CD, PAYPRES_NM, APPTN_DAY, AUTO_ACCNNUM, UPSO_CD, RECEPTION_GBN, RESINUM, AUTO_NUM, PROC_GBN, REMAK)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BANK_CD , :PAYPRES_NM , :APPTN_DAY , :AUTO_ACCNNUM , :UPSO_CD , :RECEPTION_GBN , :RESINUM , (SELECT NVL(MAX(AUTO_NUM), 0) + 1 AUTO_NUM FROM GIBU.TBRA_UPSO_AUTO WHERE UPSO_CD = :AUTO_NUM_1), :PROC_GBN , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("AUTO_NUM_1", AUTO_NUM_1);               //일련 번호
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //출금 계좌번호
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("PROC_GBN", PROC_GBN);               //처리 구분
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //접수처
        return sobj;
    }
    // 자동이체신청수동등록
    // 자동이체신청 UPLOAD 시 에러난 업소를 수동으로 등록한다. 이때 처리구분을 수동으로 한다.(PROC_GBN=="N")
    public DOBJ CALLauto_err_handling_INS20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS20");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_err_handling_INS20(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS20") ;
        rvobj.Println("INS20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_err_handling_INS20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int  AUTO_NUM = 0;        //일련 번호
        String INS_DATE ="";        //등록 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   AUTO_NUM_1 = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //일련 번호
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("PROC_DAY");   //신청 일자
        String   AUTO_ACCNNUM = dobj.getRetObject("R").getRecord().get("PAY_ACCN_NUM");   //출금 계좌번호
        String   BANK_CD = dobj.getRetObject("R").getRecord().get("PAY_BANK_CD");   //은행 코드
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   PROC_GBN ="N";   //처리 구분
        String   RECEPTION_GBN = dobj.getRetObject("R").getRecord().get("RECPT_GBN_CD");   //접수처
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO (INS_DATE, INSPRES_ID, BANK_CD, PAYPRES_NM, APPTN_DAY, AUTO_ACCNNUM, UPSO_CD, RECEPTION_GBN, RESINUM, AUTO_NUM, PROC_GBN, REMAK)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BANK_CD , :PAYPRES_NM , :APPTN_DAY , :AUTO_ACCNNUM , :UPSO_CD , :RECEPTION_GBN , :RESINUM , (SELECT NVL(MAX(AUTO_NUM), 0) + 1 AUTO_NUM FROM GIBU.TBRA_UPSO_AUTO WHERE UPSO_CD = :AUTO_NUM_1), :PROC_GBN , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("AUTO_NUM_1", AUTO_NUM_1);               //일련 번호
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //출금 계좌번호
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("PROC_GBN", PROC_GBN);               //처리 구분
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //접수처
        return sobj;
    }
    // 신규등록
    public DOBJ CALLauto_err_handling_INS35(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS35");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_err_handling_INS35(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS35") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_err_handling_INS35(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int  AUTO_NUM = dobj.getRetObject("SEL31").getRecord().getInt("SEQ");        //일련 번호
        String BANK_CD ="";        //은행 코드
        String CONFIRM_DATE ="";        //확인 일자
        String INS_DATE ="";        //등록 일시
        double SEQ = 0;        //관리번호
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
        String   PROC_DAY = dvobj.getRecord().get("PROC_DAY");   //처리 일자
        String   SEQ_1 = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //관리번호
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   BANK_CD_1 = dobj.getRetObject("R").getRecord().get("PAY_BANK_CD");   //은행 코드
        String   APPLICATION_GBN = dobj.getRetObject("R").getRecord().get("APPTN_GBN");   //신청 일자
        String   AUTO_ACCNNUM = dobj.getRetObject("R").getRecord().get("PAY_ACCN_NUM");   //출금 계좌번호
        String   CONFIRM_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //계산 일자
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   RECEPTION_GBN = dobj.getRetObject("R").getRecord().get("RECPT_GBN_CD");   //접수처
        String   REMAK =" 00:정상처리";   //비고
        String   STAFF_CD = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO_APPLICATION (CONFIRM_ID, INSPRES_ID, BANK_CD, PAYPRES_NM, APPLICATION_GBN, SEQ, PROC_DAY, INS_DATE, STAFF_CD, APPTN_DAY, UPSO_CD, AUTO_ACCNNUM, RECEPTION_GBN, RESINUM, CONFIRM_DATE, REMAK)  \n";
        query +=" values(:CONFIRM_ID , :INSPRES_ID , (SELECT SUBSTR(:BANK_CD_1,1,3) FROM DUAL), :PAYPRES_NM , :APPLICATION_GBN , (SELECT NVL(MAX(SEQ), 0) + 1 SEQ FROM GIBU.TBRA_UPSO_AUTO_APPLICATION WHERE UPSO_CD = :SEQ_1), :PROC_DAY , SYSDATE, :STAFF_CD , :APPTN_DAY , :UPSO_CD , :AUTO_ACCNNUM , :RECEPTION_GBN , :RESINUM , SYSDATE, :REMAK )";
        sobj.setSql(query);
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("SEQ_1", SEQ_1);               //관리번호
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("BANK_CD_1", BANK_CD_1);               //은행 코드
        sobj.setString("APPLICATION_GBN", APPLICATION_GBN);               //신청 일자
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //출금 계좌번호
        sobj.setString("CONFIRM_ID", CONFIRM_ID);               //계산 일자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //접수처
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    // 신규등록
    public DOBJ CALLauto_err_handling_INS48(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS48");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_err_handling_INS48(dobj, dvobj);
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
    private SQLObject SQLauto_err_handling_INS48(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int  AUTO_NUM = dobj.getRetObject("SEL49").getRecord().getInt("AUTO_NUM");        //일련 번호
        String BANK_CD ="";        //은행 코드
        String CONFIRM_DATE ="";        //확인 일자
        String INS_DATE ="";        //등록 일시
        double SEQ = 0;        //관리번호
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
        String   SEQ_1 = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //관리번호
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   BANK_CD_1 = dobj.getRetObject("R").getRecord().get("PAY_BANK_CD");   //은행 코드
        String   APPLICATION_GBN = dobj.getRetObject("R").getRecord().get("APPTN_GBN");   //신청 일자
        String   AUTO_ACCNNUM = dobj.getRetObject("R").getRecord().get("PAY_ACCN_NUM");   //출금 계좌번호
        String   BEFORE_APPLICATION_SEQ = dobj.getRetObject("SEL46").getRecord().get("SEQ");   //이전신청정보
        String   CONFIRM_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //계산 일자
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   RECEPTION_GBN = dobj.getRetObject("R").getRecord().get("RECPT_GBN_CD");   //접수처
        String   STAFF_CD = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO_APPLICATION (CONFIRM_ID, INSPRES_ID, BANK_CD, PAYPRES_NM, APPLICATION_GBN, SEQ, INS_DATE, STAFF_CD, APPTN_DAY, UPSO_CD, AUTO_ACCNNUM, RECEPTION_GBN, RESINUM, CONFIRM_DATE, BEFORE_APPLICATION_SEQ)  \n";
        query +=" values(:CONFIRM_ID , :INSPRES_ID , (SELECT SUBSTR(:BANK_CD_1,1,3) FROM DUAL), :PAYPRES_NM , :APPLICATION_GBN , (SELECT NVL(MAX(SEQ), 0) + 1 SEQ FROM GIBU.TBRA_UPSO_AUTO_APPLICATION WHERE UPSO_CD = :SEQ_1), SYSDATE, :STAFF_CD , :APPTN_DAY , :UPSO_CD , :AUTO_ACCNNUM , :RECEPTION_GBN , :RESINUM , SYSDATE, :BEFORE_APPLICATION_SEQ )";
        sobj.setSql(query);
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("SEQ_1", SEQ_1);               //관리번호
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("BANK_CD_1", BANK_CD_1);               //은행 코드
        sobj.setString("APPLICATION_GBN", APPLICATION_GBN);               //신청 일자
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //출금 계좌번호
        sobj.setString("BEFORE_APPLICATION_SEQ", BEFORE_APPLICATION_SEQ);               //이전신청정보
        sobj.setString("CONFIRM_ID", CONFIRM_ID);               //계산 일자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //접수처
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    // 업소정보변경
    // 경영주의 주민번호 변경
    public DOBJ CALLauto_err_handling_UPD19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD19");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_err_handling_UPD19(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD19") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_err_handling_UPD19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   MNGEMSTR_RESINUM = dvobj.getRecord().get("RESINUM");   //경영주 주민번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MNGEMSTR_RESINUM=:MNGEMSTR_RESINUM , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MNGEMSTR_RESINUM", MNGEMSTR_RESINUM);               //경영주 주민번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 업소정보변경
    // 경영주의 주민번호 변경
    public DOBJ CALLauto_err_handling_UPD20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD20");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_err_handling_UPD20(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD20") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_err_handling_UPD20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   MNGEMSTR_RESINUM = dvobj.getRecord().get("RESINUM");   //경영주 주민번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MNGEMSTR_RESINUM=:MNGEMSTR_RESINUM , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MNGEMSTR_RESINUM", MNGEMSTR_RESINUM);               //경영주 주민번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    //##**$$auto_err_handling
    //##**$$change_flag
    /*
    */
    public DOBJ CTLchange_flag(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("PROC_STAT").equals("1"))
            {
                dobj  = CALLchange_flag_XIUD3(Conn, dobj);           //  진행중으로 변경
            }
            else
            {
                dobj  = CALLchange_flag_XIUD4(Conn, dobj);           //  미진행으로 변경
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
    public DOBJ CTLchange_flag( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("PROC_STAT").equals("1"))
        {
            dobj  = CALLchange_flag_XIUD3(Conn, dobj);           //  진행중으로 변경
        }
        else
        {
            dobj  = CALLchange_flag_XIUD4(Conn, dobj);           //  미진행으로 변경
        }
        return dobj;
    }
    // 진행중으로 변경
    public DOBJ CALLchange_flag_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLchange_flag_XIUD3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchange_flag_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_AUTO_FLAG  \n";
        query +=" SET PROC_STAT = '1'  \n";
        query +=" WHERE SEQ = 1";
        sobj.setSql(query);
        return sobj;
    }
    // 미진행으로 변경
    public DOBJ CALLchange_flag_XIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD4");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLchange_flag_XIUD4(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchange_flag_XIUD4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_AUTO_FLAG  \n";
        query +=" SET PROC_STAT = '0'  \n";
        query +=" WHERE SEQ = 1";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$change_flag
    //##**$$chk_flag
    /*
    */
    public DOBJ CTLchk_flag(DOBJ dobj)
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
            dobj  = CALLchk_flag_SEL1(Conn, dobj);           //  flag 조회
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
    public DOBJ CTLchk_flag( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLchk_flag_SEL1(Conn, dobj);           //  flag 조회
        return dobj;
    }
    // flag 조회
    public DOBJ CALLchk_flag_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLchk_flag_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_flag_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  PROC_STAT  FROM  GIBU.TBRA_UPSO_AUTO_FLAG  WHERE  SEQ  =  1 ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$chk_flag
    //##**$$auto_apptn_result_t3
    /*
    */
    public DOBJ CTLauto_apptn_result_t3(DOBJ dobj)
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
            dobj  = CALLauto_apptn_result_t3_SEL1(Conn, dobj);           //  전체리스트
            dobj  = CALLauto_apptn_result_t3_SEL2(Conn, dobj);           //  에러리스트
            dobj  = CALLauto_apptn_result_t3_SEL4(Conn, dobj);           //  82생성목록
            dobj  = CALLauto_apptn_result_t3_XIUD5(Conn, dobj);           //  신청서에 보낸날짜 기록
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
    public DOBJ CTLauto_apptn_result_t3( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_apptn_result_t3_SEL1(Conn, dobj);           //  전체리스트
        dobj  = CALLauto_apptn_result_t3_SEL2(Conn, dobj);           //  에러리스트
        dobj  = CALLauto_apptn_result_t3_SEL4(Conn, dobj);           //  82생성목록
        dobj  = CALLauto_apptn_result_t3_XIUD5(Conn, dobj);           //  신청서에 보낸날짜 기록
        return dobj;
    }
    // 전체리스트
    public DOBJ CALLauto_apptn_result_t3_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_apptn_result_t3_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_result_t3_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
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
        query +=" AND  XC.GBN  =  '62'   \n";
        query +=" AND  XA.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XD.GIBU(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XE.BANK_CD(+)  =  SUBSTR(XC.PAY_BANK_CD  ,1,3)  ORDER  BY  XC.SEQ_NUM ";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    // 에러리스트
    public DOBJ CALLauto_apptn_result_t3_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_apptn_result_t3_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_result_t3_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
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
        query +=" AND  XC.GBN  =  '62'   \n";
        query +=" AND  XA.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XE.BANK_CD(+)  =  XC.PAY_BANK_CD   \n";
        query +=" AND  XF.HIGH_CD  =  '00324'   \n";
        query +=" AND  XF.CODE_CD  =  XC.ERR_GBN   \n";
        query +=" AND  XC.APPTN_RSLT  !='00'  ORDER  BY  XC.SEQ_NUM ";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    // 82생성목록
    public DOBJ CALLauto_apptn_result_t3_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_apptn_result_t3_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_result_t3_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  *  FROM   \n";
        query +=" (SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD||XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XB.MONPRNCFEE  ,  XA.MNGEMSTR_NM  ,  DECODE(LENGTH(XA.MNGEMSTR_RESINUM),13,  SUBSTR(XA.MNGEMSTR_RESINUM,0,6)||'0000000',  XA.MNGEMSTR_RESINUM)  MNGEMSTR_RESINUM  ,  XA.UPSO_PHON  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  XE.BANK_NM||XE.SHOP_NM  BANK_NM  ,  XC.PROC_DAY  ,  XC.GBN  ,  XC.SEQ_NUM  ,  XC.APPTN_DAY  ,  XC.APPTN_GBN  ,  XC.APPTN_RSLT  ,  XC.CHGBFR_PAYPRES_NUM  ,  XC.CHGATR_PAYPRES_NUM  ,  XC.PAY_REQDAY  ,  XC.PAY_KND  ,  XC.PAY_BANK_CD  ,  XC.PAY_ACCN_NUM  ,  DECODE(LENGTH(XC.RESINUM),13,  SUBSTR(XC.RESINUM,0,6)||'0000000',  XC.RESINUM)  RESINUM  ,  XC.PAYER_PHONNUM  ,  XC.RECPT_BANK_CD  ,  XC.BEGNG_RELSDAY  ,  XC.PAYPRES_NM  ,  XC.ERR_GBN  ,  XC.RECPTNUM  ,  XC.RECPT_GBN_CD  FROM  GIBU.TBRA_UPSO  XA,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  GIBU.TBRA_UPSO_AUTORSLT  XC  ,  INSA.TCPM_DEPT  XD  ,  ACCT.TCAC_BANK  XE  WHERE  XC.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  XC.GBN  =  '62'   \n";
        query +=" AND  XA.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XD.GIBU(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XE.BANK_CD(+)  =  SUBSTR(XC.PAY_BANK_CD  ,1,3)   \n";
        query +=" AND  XC.RECPT_GBN_CD  IN  ('4',  '6')  UNION  ALL   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD||XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XB.MONPRNCFEE  ,  XA.MNGEMSTR_NM  ,  DECODE(LENGTH(XA.MNGEMSTR_RESINUM),13,  SUBSTR(XA.MNGEMSTR_RESINUM,0,6)||'0000000',  XA.MNGEMSTR_RESINUM)  MNGEMSTR_RESINUM  ,  XA.UPSO_PHON  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  XE.BANK_NM||XE.SHOP_NM  BANK_NM  ,  :PROC_DAY  AS  PROC_DAY  ,  '62'  GBN  ,  ''  AS  SEQ_NUM  ,  XC.APPTN_DAY  ,  XC.APPLICATION_GBN  AS  APPTN_GBN  ,  '00'  APPTN_RSLT  ,  ''  AS  CHGBFR_PAYPRES_NUM  ,   \n";
        query +=" (SELECT  CLIENT_NUM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  XA.UPSO_CD  )  CHGATR_PAYPRES_NUM  ,  '00'  AS  PAY_REQDAY  ,  '00'  AS  PAY_KND  ,  XC.BANK_CD  ||  '0000'  AS  PAY_BANK_CD  ,  XC.AUTO_ACCNNUM  AS  PAY_ACCN_NUM  ,  DECODE(LENGTH(XC.RESINUM),13,  SUBSTR(XC.RESINUM,0,6)||'0000000',  XC.RESINUM)  RESINUM  ,  XC.PHON_NUM  AS  PAYER_PHONNUM  ,  XC.BANK_CD  ||  '0000'  AS  RECPT_BANK_CD  ,  :PROC_DAY  AS  BEGNG_RELSDAY  ,  XC.PAYPRES_NM  ,  '00'  AS  ERR_GBN  ,  '000000000'  AS  RECPTNUM  ,  XC.RECEPTION_GBN  FROM  GIBU.TBRA_UPSO  XA,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  GIBU.TBRA_UPSO_AUTO_APPLICATION  XC  ,  INSA.TCPM_DEPT  XD  ,  ACCT.TCAC_BANK  XE  WHERE  TO_CHAR(XC.INS_DATE,  'YYYYMMDD')  <=  :PROC_DAY   \n";
        query +=" AND  XC.CONFIRM_ID  IS  NOT  NULL   \n";
        query +=" AND  (XC.PROC_DAY  IS  NULL   \n";
        query +=" OR  XC.PROC_DAY  =  :PROC_DAY)   \n";
        query +=" AND  XA.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD(+)  =  XC.UPSO_CD   \n";
        query +=" AND  XD.GIBU(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XE.BANK_CD(+)  =  SUBSTR(XC.BANK_CD  ,1,3)   \n";
        query +=" AND  XC.RECEPTION_GBN  =  '5')  ORDER  BY  SEQ_NUM  ASC,  APPTN_GBN  DESC ";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    // 신청서에 보낸날짜 기록
    public DOBJ CALLauto_apptn_result_t3_XIUD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD5");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_result_t3_XIUD5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_result_t3_XIUD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" SET PROC_DAY = :PROC_DAY  \n";
        query +=" WHERE TO_CHAR(INS_DATE, 'YYYYMMDD') <= :PROC_DAY  \n";
        query +=" AND CONFIRM_DATE IS NOT NULL  \n";
        query +=" AND RECEPTION_GBN = '5'  \n";
        query +=" AND PROC_DAY IS NULL";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    //##**$$auto_apptn_result_t3
    //##**$$rept_auto_insert1
    /* * 프로그램명 : bra03_s05
    * 작성자 : 서정재
    * 작성일 : 2009/11/11
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLrept_auto_insert(DOBJ dobj)
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
            dobj  = CALLrept_auto_insert1_SEL19(Conn, dobj);           //  입금마감확인
            if( dobj.getRetObject("SEL19").getRecord().get("BRANEND").equals("0"))
            {
                dobj  = CALLrept_auto_insert1_SEL45(Conn, dobj);           //  입금정보 초기화 정보설정
                dobj  = CALLrept_auto_insert1_UPD38(Conn, dobj);           //  자동이체 결과 초기화
                dobj  = CALLrept_auto_insert1_DEL40(Conn, dobj);           //  자동이체 오류결과 초기화
                dobj  = CALLrept_auto_insert1_MPD1(Conn, dobj);           //  오류내역처리
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLrept_auto_insert1_DEL43(Conn, dobj);           //  자동이체 입금정보 초기화
                dobj  = CALLrept_auto_insert1_SEL9(Conn, dobj);           //  자동이체 청구 데이터 조회
                if( dobj.getRetObject("SEL9").getRecordCnt() > 0)
                {
                    dobj  = CALLrept_auto_insert1_MPD35(Conn, dobj);           //  입금 리스트 처리
                    if(dobj.getRtncode() == 9)
                    {
                        Conn.rollback();
                        return dobj;
                    }
                    dobj  = CALLrept_auto_insert1_SEL12(Conn, dobj);           //  출금 건수/금액 확인
                    dobj  = CALLrept_auto_insert1_SEL39(Conn, dobj);           //  자동이체 입금 결과 조회
                }
            }
            else
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="해당 년월에 입금마감이 있습니다.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
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
    public DOBJ CTLrept_auto_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_auto_insert1_SEL19(Conn, dobj);           //  입금마감확인
        if( dobj.getRetObject("SEL19").getRecord().get("BRANEND").equals("0"))
        {
            dobj  = CALLrept_auto_insert1_SEL45(Conn, dobj);           //  입금정보 초기화 정보설정
            dobj  = CALLrept_auto_insert1_UPD38(Conn, dobj);           //  자동이체 결과 초기화
            dobj  = CALLrept_auto_insert1_DEL40(Conn, dobj);           //  자동이체 오류결과 초기화
            dobj  = CALLrept_auto_insert1_MPD1(Conn, dobj);           //  오류내역처리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLrept_auto_insert1_DEL43(Conn, dobj);           //  자동이체 입금정보 초기화
            dobj  = CALLrept_auto_insert1_SEL9(Conn, dobj);           //  자동이체 청구 데이터 조회
            if( dobj.getRetObject("SEL9").getRecordCnt() > 0)
            {
                dobj  = CALLrept_auto_insert1_MPD35(Conn, dobj);           //  입금 리스트 처리
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLrept_auto_insert1_SEL12(Conn, dobj);           //  출금 건수/금액 확인
                dobj  = CALLrept_auto_insert1_SEL39(Conn, dobj);           //  자동이체 입금 결과 조회
            }
        }
        else
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="해당 년월에 입금마감이 있습니다.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // 입금마감확인
    // 요청된 입금년월의 입금마감이 있는 경우 에러 처리
    public DOBJ CALLrept_auto_insert1_SEL19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL19");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL19");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert1_SEL19(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_SEL19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //입금 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  BRANEND  FROM  GIBU.TBRA_BRANEND  WHERE  END_YEAR  =  SUBSTR(:REPT_YRMN,  1,  4)   \n";
        query +=" AND  END_MON  =  SUBSTR(:REPT_YRMN,  5,  2) ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        return sobj;
    }
    // 입금정보 초기화 정보설정
    // 입금테이블을 초기화하기 위한 입금테이블 키 값을 설정한다.  ProcessBuilder 가 S 를 입력object 로 설정하는 경우 S 의 DataSet 수 만큼 Delete 명령을 수행하기 때문에 이 프로세스를 삽입한다
    public DOBJ CALLrept_auto_insert1_SEL45(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL45");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL45");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert1_SEL45(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL45");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_SEL45(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //조회카운트
        String   REPT_YRMN = dobj.getRetObject("S").firstRecord().get("REPT_YRMN");   //입금 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_DAY  AS  REPT_DAY  ,  :REPT_YRMN  AS  REPT_YRMN  ,  '01'  AS  REPT_GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //조회카운트
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        return sobj;
    }
    // 자동이체 결과 초기화
    // 해당년월의 자동이체 결과를 초기화 한다
    public DOBJ CALLrept_auto_insert1_UPD38(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD38");
        VOBJ dvobj = dobj.getRetObject("SEL45");           //입금정보 초기화 정보설정에서 생성시킨 OBJECT입니다.(CALLrept_auto_insert1_SEL45)
        dvobj.Println("UPD38");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_auto_insert1_UPD38(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD38") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_UPD38(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String DEMD_MON ="";        //청구 월
        String DEMD_YEAR ="";        //청구 년
        String   TRNF_RSLT ="";   //이체 결과
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_DEMD_AUTO  \n";
        query +=" set TRNF_RSLT=:TRNF_RSLT  \n";
        query +=" where";
        sobj.setSql(query);
        sobj.setString("TRNF_RSLT", TRNF_RSLT);               //이체 결과
        return sobj;
    }
    // 자동이체 오류결과 초기화
    // 해당년월의 자동이체 오류결과를 초기화 한다
    public DOBJ CALLrept_auto_insert1_DEL40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL40");
        VOBJ dvobj = dobj.getRetObject("SEL45");           //입금정보 초기화 정보설정에서 생성시킨 OBJECT입니다.(CALLrept_auto_insert1_SEL45)
        dvobj.Println("DEL40");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_auto_insert1_DEL40(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL40") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_DEL40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REPT_YRMN = dvobj.firstRecord().get("REPT_YRMN");        //입금 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_REPT_AUTO_ERR  \n";
        query +=" where";
        sobj.setSql(query);
        return sobj;
    }
    // 오류내역처리
    public DOBJ CALLrept_auto_insert1_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("AUTO_GBN").equals("22"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrept_auto_insert1_SEL2(Conn, dobj);           //  업소정보 조회
                if( dobj.getRetObject("SEL2").getRecordCnt() > 0)
                {
                    dobj  = CALLrept_auto_insert1_UPD5(Conn, dobj);           //  청구결과 저장
                    dobj  = CALLrept_auto_insert1_XIUD47(Conn, dobj);           //  업소 코드 오류 검사
                }
                else
                {
                    dobj  = CALLrept_auto_insert1_XIUD46(Conn, dobj);           //  업소 코드 오류 검사
                }
            }
        }
        return dobj;
    }
    // 업소정보 조회
    // 계좌번호로 업소정보를 조회한다
    public DOBJ CALLrept_auto_insert1_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert1_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("R").getRecord().get("REPT_YRMN");   //입금 년월
        String   AUTO_CLIENTNUM = dobj.getRetObject("R").getRecord().get("PAYPRES_NUM");   //자동이체 고객번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BRAN_CD  ,  A.UPSO_CD  ,  A.STAFF_CD  ,  A.CLSBS_DAY  ,  B.DEMD_AMT  ,  B.DEMD_GBN  ,  B.START_YRMN  ,  B.END_YRMN  ,  DEMD_MMCNT  ,  SUBSTR(:REPT_YRMN,  1,  4)  DEMD_YEAR  ,  SUBSTR(:REPT_YRMN,  5,  2)  DEMD_MON  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_DEMD_AUTO  B  WHERE  A.AUTO_CLIENTNUM  =  :AUTO_CLIENTNUM   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.DEMD_YEAR  =  SUBSTR(:REPT_YRMN,  1,  4)   \n";
        query +=" AND  B.DEMD_MON  =  SUBSTR(:REPT_YRMN,  5,  2) ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("AUTO_CLIENTNUM", AUTO_CLIENTNUM);               //자동이체 고객번호
        return sobj;
    }
    // 청구결과 저장
    // 오류가 발생한 자동이체 청구 결과를 자동이체 청구테이블에 저장한다
    public DOBJ CALLrept_auto_insert1_UPD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD5");
        VOBJ dvobj = dobj.getRetObject("SEL2");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("UPD5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_auto_insert1_UPD5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_UPD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   TRNF_RSLT = dobj.getRetObject("R").getRecord().get("APPTN_RSLT");   //이체 결과
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_DEMD_AUTO  \n";
        query +=" set TRNF_RSLT=:TRNF_RSLT  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("TRNF_RSLT", TRNF_RSLT);               //이체 결과
        return sobj;
    }
    // 업소 코드 오류 검사
    // 오류가 발생한 자동이체 정보를 입금 오류 테이블에 저장한다
    public DOBJ CALLrept_auto_insert1_XIUD47(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD47");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_auto_insert1_XIUD47(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD47");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_XIUD47(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double SEQ = 0;        //관리번호
        String   ACCN_NUM = dobj.getRetObject("R").getRecord().get("ACCN_NUM");   //계좌 번호
        String   BANK_CD = dobj.getRetObject("R").getRecord().get("BANK_CD");   //은행 코드
        String   ERR_CD = dobj.getRetObject("R").getRecord().get("APPTN_RSLT");   //에러 코드
        String   INSPRES_ID = dobj.getRetObject("R").getRecord().get("INSPRES_ID");   //등록자 ID
        String   PAYPRES_NUM = dobj.getRetObject("R").getRecord().get("PAYPRES_NUM");   //납부자 번호
        String   RECPT_CD = dobj.getRetObject("R").getRecord().get("RECPT_CD");   //접수처 코드
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //비고
        double   REPT_AMT = dobj.getRetObject("R").getRecord().getDouble("REPT_AMT");   //입금 금액
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //조회카운트
        String   REPT_YRMN = dobj.getRetObject("R").getRecord().get("REPT_YRMN");   //입금 년월
        String   RESI_NUM = dobj.getRetObject("R").getRecord().get("RESI_NUM");   //주민 번호
        String   SEQ_NUM = dobj.getRetObject("R").getRecord().get("SEQ_NUM");   //일련 번호
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_REPT_AUTO_ERR ( SEQ , UPSO_CD , REPT_DAY , SEQ_NUM , REPT_YRMN , PAYPRES_NUM , BANK_CD , ACCN_NUM , RESI_NUM , ERR_CD , REMAK , REPT_AMT , RECPT_CD , INSPRES_ID , INS_DATE )  \n";
        query +=" VALUES ( (SELECT NVL(MAX(SEQ), 0) + 1 FROM GIBU.TBRA_UPSO_REPT_AUTO_ERR) , :UPSO_CD , :REPT_DAY , :SEQ_NUM , :REPT_YRMN , :PAYPRES_NUM , :BANK_CD , :ACCN_NUM , :RESI_NUM , :ERR_CD , :REMAK , :REPT_AMT , :RECPT_CD , :INSPRES_ID , SYSDATE )";
        sobj.setSql(query);
        sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("ERR_CD", ERR_CD);               //에러 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("PAYPRES_NUM", PAYPRES_NUM);               //납부자 번호
        sobj.setString("RECPT_CD", RECPT_CD);               //접수처 코드
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setDouble("REPT_AMT", REPT_AMT);               //입금 금액
        sobj.setString("REPT_DAY", REPT_DAY);               //조회카운트
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("RESI_NUM", RESI_NUM);               //주민 번호
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소 코드 오류 검사
    // 자동이체 업소 코드 오류
    public DOBJ CALLrept_auto_insert1_XIUD46(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD46");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_auto_insert1_XIUD46(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD46");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_XIUD46(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double SEQ = 0;        //관리번호
        String   ACCN_NUM = dobj.getRetObject("R").getRecord().get("ACCN_NUM");   //계좌 번호
        String   BANK_CD = dobj.getRetObject("R").getRecord().get("BANK_CD");   //은행 코드
        String   INSPRES_ID = dobj.getRetObject("R").getRecord().get("INSPRES_ID");   //등록자 ID
        String   PAYPRES_NUM = dobj.getRetObject("R").getRecord().get("PAYPRES_NUM");   //납부자 번호
        String   RECPT_CD = dobj.getRetObject("R").getRecord().get("RECPT_CD");   //접수처 코드
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //비고
        double   REPT_AMT = dobj.getRetObject("R").getRecord().getDouble("REPT_AMT");   //입금 금액
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //조회카운트
        String   REPT_YRMN = dobj.getRetObject("R").getRecord().get("REPT_YRMN");   //입금 년월
        String   RESI_NUM = dobj.getRetObject("R").getRecord().get("RESI_NUM");   //주민 번호
        String   SEQ_NUM = dobj.getRetObject("R").getRecord().get("SEQ_NUM");   //일련 번호
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_REPT_AUTO_ERR ( SEQ , UPSO_CD , REPT_DAY , SEQ_NUM , REPT_YRMN , PAYPRES_NUM , BANK_CD , ACCN_NUM , RESI_NUM , ERR_CD , REMAK , REPT_AMT , RECPT_CD , INSPRES_ID , INS_DATE )  \n";
        query +=" VALUES ( (SELECT NVL(MAX(SEQ), 0) + 1 FROM GIBU.TBRA_UPSO_REPT_AUTO_ERR) , :UPSO_CD , :REPT_DAY , :SEQ_NUM , :REPT_YRMN , :PAYPRES_NUM , :BANK_CD , :ACCN_NUM , :RESI_NUM , '51' , :REMAK , :REPT_AMT , :RECPT_CD , :INSPRES_ID , SYSDATE )";
        sobj.setSql(query);
        sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("PAYPRES_NUM", PAYPRES_NUM);               //납부자 번호
        sobj.setString("RECPT_CD", RECPT_CD);               //접수처 코드
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setDouble("REPT_AMT", REPT_AMT);               //입금 금액
        sobj.setString("REPT_DAY", REPT_DAY);               //조회카운트
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("RESI_NUM", RESI_NUM);               //주민 번호
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 자동이체 입금정보 초기화
    // 입력된 자동이체 입력정보를 초기화한다
    public DOBJ CALLrept_auto_insert1_DEL43(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL43");
        VOBJ dvobj = dobj.getRetObject("SEL45");           //입금정보 초기화 정보설정에서 생성시킨 OBJECT입니다.(CALLrept_auto_insert1_SEL45)
        dvobj.Println("DEL43");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_auto_insert1_DEL43(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL43") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_DEL43(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_REPT  \n";
        query +=" where";
        sobj.setSql(query);
        return sobj;
    }
    // 자동이체 청구 데이터 조회
    // 자동이체 청구 데이터를 조회한다
    public DOBJ CALLrept_auto_insert1_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert1_SEL9(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //입금 일자
        String   INSPRES_ID = dobj.getRetObject("S").firstRecord().get("INSPRES_ID");   //등록자 ID
        String   REPT_YRMN = dobj.getRetObject("S").firstRecord().get("REPT_YRMN");   //입금 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  REPT_YRMN  ,  REPT_DAY  ,  REPT_BRAN  ,  STAFF_CD  ,  REPT_GBN  ,  USE_AMT  -  COMIS  AS  USE_AMT  ,  ADDT_AMT  ,  EXT_ADDT_AMT  ,  COMIS  ,  ACCN_CD  ,  RECV_DAY  ,  INSPRES_ID  ,  CLSBS_DAY  ,  NEW_DAY  ,  OPBI_DAY  ,  OPBI_CHECK  ,  CLSBS_CHECK  ,  AUTO_CLIENTNUM  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  AS  UPSO_CD  ,  B.YYYY  ||  B.MM  AS  REPT_YRMN  ,  :REPT_DAY  AS  REPT_DAY  ,  C.BRAN_CD  AS  REPT_BRAN  ,  A.DEMD_GBN  AS  REPT_GBN  ,  TRUNC(E.C_USE_AMT,  -1)  AS  USE_AMT  ,  DECODE(A.DEMD_MMCNT,  0,  0,  1,  A.ADDT_AMT,  DECODE(  B.YYYY  ||  B.MM,  A.START_YRMN,  A.ADDT_AMT  -  TRUNC(A.ADDT_AMT  /  A.DEMD_MMCNT)  *  (A.DEMD_MMCNT  -  1),  TRUNC(A.ADDT_AMT  /  A.DEMD_MMCNT)))  AS  ADDT_AMT  ,  DECODE(A.DEMD_MMCNT,  0,  0,  1,  A.EXT_ADDT_AMT,  DECODE(  B.YYYY  ||  B.MM,  A.START_YRMN,  A.EXT_ADDT_AMT  -  TRUNC(A.EXT_ADDT_AMT  /  A.DEMD_MMCNT)  *  (A.DEMD_MMCNT  -  1),  TRUNC(A.EXT_ADDT_AMT  /  A.DEMD_MMCNT)))  AS  EXT_ADDT_AMT  ,  DECODE(A.DEMD_MMCNT,  1,  50,  DECODE(  B.YYYY  ||  B.MM,  A.START_YRMN,  50  -  TRUNC(50  /  A.DEMD_MMCNT),  TRUNC(50  /  A.DEMD_MMCNT)))  AS  COMIS  ,  D.BANK_CD  AS  ACCN_CD  ,  :REPT_DAY  AS  RECV_DAY  ,  :INSPRES_ID  AS  INSPRES_ID  ,  C.CLSBS_DAY  AS  CLSBS_DAY  ,  C.NEW_DAY  AS  NEW_DAY  ,  C.OPBI_DAY  AS  OPBI_DAY  ,  C.STAFF_CD  AS  STAFF_CD  ,  C.AUTO_CLIENTNUM  AS  AUTO_CLIENTNUM  ,  CASE  WHEN  (SUBSTR(OPBI_DAY,  1,  4)  >  A.START_YRMN)  THEN  'N'  ELSE  'Y'  END  AS  OPBI_CHECK  ,  CASE  WHEN  (CLSBS_DAY  IS  NULL)  THEN  'Y'  WHEN  (SUBSTR(CLSBS_DAY,  1,  4)  >  A.END_YRMN)  THEN  'Y'  ELSE  'N'  END  AS  CLSBS_CHECK  FROM  GIBU.TBRA_DEMD_AUTO  A  ,  (   \n";
        query +=" SELECT  YYYY||MM  YYMM,  YYYY,  MM  FROM  GIBU.COPY_YY  ,  GIBU.COPY_MM  )  B  ,  GIBU.TBRA_UPSO  C  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.BANK_CD  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(AUTO_NUM)  AUTO_NUM  FROM  GIBU.TBRA_UPSO_AUTO  GROUP  BY  UPSO_CD  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.AUTO_NUM  =  B.AUTO_NUM  )  D  ,  GIBU.TBRA_DEMD_AUTO  E  WHERE  A.START_YRMN  <=  B.YYMM   \n";
        query +=" AND  A.END_YRMN  >=  B.YYMM   \n";
        query +=" AND  A.DEMD_YEAR  =  SUBSTR(:REPT_YRMN,  1,  4)   \n";
        query +=" AND  A.DEMD_MON  =  SUBSTR(:REPT_YRMN,  5,  2)   \n";
        query +=" AND  A.TRNF_RSLT  IS  NULL   \n";
        query +=" AND  C.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  D.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  E.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  E.DEMD_YEAR  =  B.YYYY   \n";
        query +=" AND  E.DEMD_MON  =  B.MM   \n";
        query +=" AND  E.C_USE_AMT  >  0  )  XA ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금 일자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        return sobj;
    }
    // 입금 리스트 처리
    // 자동이체가 정상적으로 처리된 입금 데이터를 레코드 단위로 저장한다
    public DOBJ CALLrept_auto_insert1_MPD35(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD35");
        VOBJ dvobj = dobj.getRetObject("SEL9");         //자동이체 청구 데이터 조회에서 생성시킨 OBJECT입니다.(CALLrept_auto_insert1_SEL9)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrept_auto_insert1_SEL26(Conn, dobj);           //  고소중 업소확인
                if( dobj.getRetObject("SEL26").getRecord().getDouble("CNT") > 0)
                {
                    dobj  = CALLrept_auto_insert1_XIUD48(Conn, dobj);           //  업소 코드 오류 검사
                }
                else if( dobj.getRetObject("R").getRecord().get("CLSBS_CHECK").equals("N") || dobj.getRetObject("R").getRecord().get("OPBI_CHECK").equals("N"))
                {
                    dobj  = CALLrept_auto_insert1_XIUD49(Conn, dobj);           //  업소 코드 오류 검사
                }
                else
                {
                    dobj  = CALLrept_auto_insert1_SEL36(Conn, dobj);           //  REPT_NUM 값 구하기
                    dobj  = CALLrept_auto_insert1_INS10(Conn, dobj);           //  자동이체 입금 데이터 저장
                    if( dobj.getRetObject("R").getRecord().get("NEW_DAY").equals(""))
                    {
                        dobj  = CALLrept_auto_insert1_UPD43(Conn, dobj);           //  최초입금일 저장
                        dobj  = CALLrept_auto_insert1_SEL44(Conn, dobj);           //  잔고 확인
                        if( dobj.getRetObject("SEL44").getRecordCnt() == 1 && dobj.getRetObject("SEL44").getRecord().getDouble("BALANCE") > 0)
                        {
                            dobj  = CALLrept_auto_insert1_SEL46(Conn, dobj);           //  잔액테이블 순번
                            dobj  = CALLrept_auto_insert1_INS47(Conn, dobj);           //  잔액 정보 저장
                            ////
                            double   INST_CNT = dobj.getGVNumber("INST_CNT")+dobj.getRetObject("INS10").getRecord().getDouble("UPDCNT");         //입력카운트
                            dobj.setGVValue("INST_CNT",INST_CNT+"");
                        }
                    }
                    else
                    {
                        ////
                        double   INST_CNT = dobj.getGVNumber("INST_CNT")+dobj.getRetObject("INS10").getRecord().getDouble("UPDCNT");         //입력카운트
                        dobj.setGVValue("INST_CNT",INST_CNT+"");
                    }
                }
            }
        }
        return dobj;
    }
    // 고소중 업소확인
    // 고소중인 업체인지 확인한다
    public DOBJ CALLrept_auto_insert1_SEL26(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL26");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL26");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert1_SEL26(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL26");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_SEL26(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소 코드 오류 검사
    // 오류가 발생한 자동이체 정보를 입금 오류 테이블에 저장한다
    public DOBJ CALLrept_auto_insert1_XIUD48(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD48");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_auto_insert1_XIUD48(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD48");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_XIUD48(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double SEQ = 0;        //관리번호
        String   BANK_CD = dobj.getRetObject("R").getRecord().get("ACCN_CD");   //은행 코드
        String   ERR_CD ="52";   //에러 코드
        String   INSPRES_ID = dobj.getRetObject("R").getRecord().get("INSPRES_ID");   //등록자 ID
        String   PAYPRES_NUM = dobj.getRetObject("R").getRecord().get("AUTO_CLIENTNUM");   //납부자 번호
        double   REPT_AMT = dobj.getRetObject("R").getRecord().getDouble("USE_AMT");   //입금 금액
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //조회카운트
        String   REPT_YRMN = dobj.getRetObject("R").getRecord().get("REPT_YRMN");   //입금 년월
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_REPT_AUTO_ERR ( SEQ , UPSO_CD , REPT_DAY , REPT_YRMN , PAYPRES_NUM , BANK_CD , ERR_CD , REPT_AMT , INSPRES_ID , INS_DATE )  \n";
        query +=" VALUES ( (SELECT NVL(MAX(SEQ), 0) + 1 FROM GIBU.TBRA_UPSO_REPT_AUTO_ERR) , :UPSO_CD , :REPT_DAY , :REPT_YRMN , :PAYPRES_NUM , :BANK_CD , :ERR_CD , :REPT_AMT , :INSPRES_ID , SYSDATE )";
        sobj.setSql(query);
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("ERR_CD", ERR_CD);               //에러 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("PAYPRES_NUM", PAYPRES_NUM);               //납부자 번호
        sobj.setDouble("REPT_AMT", REPT_AMT);               //입금 금액
        sobj.setString("REPT_DAY", REPT_DAY);               //조회카운트
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소 코드 오류 검사
    // 오류가 발생한 자동이체 정보를 입금 오류 테이블에 저장한다
    public DOBJ CALLrept_auto_insert1_XIUD49(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLrept_auto_insert1_XIUD49(dobj, dvobj);
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
    private SQLObject SQLrept_auto_insert1_XIUD49(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double SEQ = 0;        //관리번호
        String   BANK_CD = dobj.getRetObject("R").getRecord().get("ACCN_CD");   //은행 코드
        String   ERR_CD ="53";   //에러 코드
        String   INSPRES_ID = dobj.getRetObject("R").getRecord().get("INSPRES_ID");   //등록자 ID
        String   PAYPRES_NUM = dobj.getRetObject("R").getRecord().get("AUTO_CLIENTNUM");   //납부자 번호
        double   REPT_AMT = dobj.getRetObject("R").getRecord().getDouble("USE_AMT");   //입금 금액
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //조회카운트
        String   REPT_YRMN = dobj.getRetObject("R").getRecord().get("REPT_YRMN");   //입금 년월
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_REPT_AUTO_ERR ( SEQ , UPSO_CD , REPT_DAY , REPT_YRMN , PAYPRES_NUM , BANK_CD , ERR_CD , REPT_AMT , INSPRES_ID , INS_DATE )  \n";
        query +=" VALUES ( (SELECT NVL(MAX(SEQ), 0) + 1 FROM GIBU.TBRA_UPSO_REPT_AUTO_ERR) , :UPSO_CD , :REPT_DAY , :REPT_YRMN , :PAYPRES_NUM , :BANK_CD , :ERR_CD , :REPT_AMT , :INSPRES_ID , SYSDATE )";
        sobj.setSql(query);
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("ERR_CD", ERR_CD);               //에러 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("PAYPRES_NUM", PAYPRES_NUM);               //납부자 번호
        sobj.setDouble("REPT_AMT", REPT_AMT);               //입금 금액
        sobj.setString("REPT_DAY", REPT_DAY);               //조회카운트
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // REPT_NUM 값 구하기
    // TBRA_UPSO_REPT 테이블의 해당 년월의 다음 순번을 구한다
    public DOBJ CALLrept_auto_insert1_SEL36(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL36");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL36");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert1_SEL36(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL36");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_SEL36(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("R").getRecord().get("REPT_YRMN");   //입금 년월
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(REPT_NUM),  0)  +  1,  4,  '0')  REPT_NUM  FROM  GIBU.TBRA_UPSO_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_YRMN  =  :REPT_YRMN ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 자동이체 입금 데이터 저장
    // 오류가 발생하지 않은 자동이체 청구 데이터를 입금테이블로 저장한다
    public DOBJ CALLrept_auto_insert1_INS10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_auto_insert1_INS10(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS10") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_INS10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String DEMD_YRMN = dobj.getRetObject("R").getRecord().get("REPT_YRMN");        //청구 년월
        String INSPRES_ID = dobj.getRetObject("S").firstRecord().get("INSPRES_ID");        //등록자 ID
        String INS_DATE ="";        //등록 일시
        String REPT_GBN ="01";        //입금 구분
        String REPT_NUM = dobj.getRetObject("SEL36").getRecord().get("REPT_NUM");        //입금 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_REPT ( values(";
        sobj.setSql(query);
        return sobj;
    }
    // 최초입금일 저장
    // 최초 입금일을 저장한다
    public DOBJ CALLrept_auto_insert1_UPD43(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD43");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_auto_insert1_UPD43(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD43") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_UPD43(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NEW_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //신규 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set NEW_DAY=:NEW_DAY  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("NEW_DAY", NEW_DAY);               //신규 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 잔고 확인
    // 선납금이 있는 경우 선납금이 제외된 금액이 청구된다. 입금 시 선납금을 초기화한다.  선납금은 청구액보다 클 수 없다
    public DOBJ CALLrept_auto_insert1_SEL44(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL44");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL44");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert1_SEL44(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL44");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_SEL44(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BALANCE  FROM  (   \n";
        query +=" SELECT  NVL(BALANCE,  0)  BALANCE  FROM  GIBU.TBRA_UPSO_TEMPAMT  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  REPT_DAY  DESC,  SEQ  DESC  )  WHERE  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 잔액테이블 순번
    // 잔액 테이블의 다음 순번을 구한다
    public DOBJ CALLrept_auto_insert1_SEL46(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL46");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL46");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert1_SEL46(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL46");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_SEL46(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   RECV_DAY = dobj.getRetObject("R").getRecord().get("RECV_DAY");   //영수 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  SEQ  FROM  GIBU.TBRA_UPSO_TEMPAMT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :RECV_DAY ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("RECV_DAY", RECV_DAY);               //영수 일자
        return sobj;
    }
    // 잔액 정보 저장
    // 선납금 테이블에 청구 시 반영된 선납금 정보를 수정한다
    public DOBJ CALLrept_auto_insert1_INS47(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS47");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_auto_insert1_INS47(dobj, dvobj);
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
    private SQLObject SQLrept_auto_insert1_INS47(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double BALANCE = 0;        //잔액
        String INSDATE ="";        //등록일시
        String INSPRES_ID = dobj.getRetObject("R").getRecord().get("INSPRES_ID");        //등록자 ID
        double REPTAMT = -1*dobj.getRetObject("SEL44").getRecord().getDouble("BALANCE");        //입금금액
        String REPT_DAY = dobj.getRetObject("R").getRecord().get("RECV_DAY");        //조회카운트
        double SEQ = dobj.getRetObject("SEL46").getRecord().getDouble("SEQ");        //관리번호
        String UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");        //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_REPT ( values(";
        sobj.setSql(query);
        return sobj;
    }
    // 출금 건수/금액 확인
    // 오류없는 청구건수와 청구금액 (= 전체 입금금액) 을 조회한다
    public DOBJ CALLrept_auto_insert1_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert1_SEL12(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL12");
        rvobj.Println("SEL12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").firstRecord().get("REPT_YRMN");   //입금 년월
        double   INST_CNT = dobj.getGVNumber("INST_CNT");   //입력카운트
        double   READ_CNT = dobj.getRetObject("SEL9").getRecordCnt();   //입금 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(DEMD_CNT)  AS  DEMD_CNT  ,  MAX(DEMD_AMT)  AS  DEMD_AMT  ,  MAX(REPT_CNT)  AS  REPT_CNT  ,  MAX(REPT_AMT)  AS  REPT_AMT  ,  MAX(COMIT)  AS  COMIT  ,  MAX(ERR_CNT)  AS  ERR_CNT  ,  MAX(ERR_AMT)  AS  ERR_AMT  ,  MAX(REPT_AMT)  -  MAX(ERR_AMT)  AS  FINAL_AMT  ,  :READ_CNT  AS  READ_CNT  ,  :INST_CNT  AS  INST_CNT  FROM  (   \n";
        query +=" SELECT  COUNT(UPSO_CD)  DEMD_CNT  ,  NVL(SUM(DEMD_AMT),  0)  DEMD_AMT  ,  0  REPT_CNT  ,  0  REPT_AMT  ,  0  COMIT  ,  0  ERR_CNT  ,  0  ERR_AMT  FROM  GIBU.TBRA_DEMD_AUTO  WHERE  DEMD_YEAR  =  SUBSTR(:REPT_YRMN,  1,  4)   \n";
        query +=" AND  DEMD_MON  =  SUBSTR(:REPT_YRMN,  5,  2)  UNION  ALL   \n";
        query +=" SELECT  0  ,  0  ,  COUNT(UPSO_CD)  REPT_CNT  ,  NVL(SUM(USE_AMT  +  ADDT_AMT  +  EXT_ADDT_AMT),  0)  REPT_AMT  ,  NVL(SUM(COMIS),  0)  COMIS  ,  0  ,  0  FROM  GIBU.TBRA_UPSO_REPT  WHERE  REPT_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  REPT_GBN  =  '01'  UNION  ALL   \n";
        query +=" SELECT  0  ,  0  ,  0  ,  0  ,  0  ,  COUNT(UPSO_CD)  ERR_CNT  ,  NVL(SUM(REPT_AMT),  0)  ERR_AMT  FROM  GIBU.TBRA_UPSO_REPT_AUTO_ERR  WHERE  REPT_YRMN  =  :REPT_YRMN  ) ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setDouble("INST_CNT", INST_CNT);               //입력카운트
        sobj.setDouble("READ_CNT", READ_CNT);               //입금 일자
        return sobj;
    }
    // 자동이체 입금 결과 조회
    // 자동이체 입금 결과 리스트를 조회한다
    public DOBJ CALLrept_auto_insert1_SEL39(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL39");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL39");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert1_SEL39(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL39");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert1_SEL39(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //입금 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YEAR  ||  XA.DEMD_MON  DEMD_YEAR  ,  XA.START_YRMN  ,  XC.GRAD  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.DEMD_AMT  ,  XA.ADDT_AMT  ,  XA.EXT_ADDT_AMT  ,  XA.DEMD_MMCNT  ,  XA.DEMD_GBN  ,  'Y'  PRINT_YN  ,  XA.TRNF_RSLT  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  GIBU.TBRA_UPSO  XB  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD,  MAX(APPL_DAY)  APPL_DAY  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  APPL_DAY  <=  :REPT_YRMN  ||  '32'  GROUP  BY  UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.APPL_DAY  =  B.APPL_DAY   \n";
        query +=" AND  A.BSTYP_CD  =  C.BSTYP_CD   \n";
        query +=" AND  A.UPSO_GRAD  =  C.GRAD_GBN  )  XC  ,  INSA.TCPM_DEPT  XD  WHERE  XA.DEMD_YEAR  =  SUBSTR(:REPT_YRMN,  1,  4)   \n";
        query +=" AND  XA.DEMD_MON  =  SUBSTR(:REPT_YRMN,  5,  2)   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NULL   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XB.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        return sobj;
    }
    //##**$$rept_auto_insert1
    //##**$$end
}
