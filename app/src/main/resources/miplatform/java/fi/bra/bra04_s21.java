
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s21
{
    public bra04_s21()
    {
    }
    //##**$$sel_card_req
    /*
    */
    public DOBJ CTLsel_card_req(DOBJ dobj)
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
            dobj  = CALLsel_card_req_SEL1(Conn, dobj);           //  카드결제요청내역조회
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
    public DOBJ CTLsel_card_req( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_card_req_SEL1(Conn, dobj);           //  카드결제요청내역조회
        return dobj;
    }
    // 카드결제요청내역조회
    public DOBJ CALLsel_card_req_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_card_req_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_card_req_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_TO = dvobj.getRecord().get("REPT_TO");   //입금종료일
        String   CARD_GBN = dvobj.getRecord().get("CARD_GBN");   //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   INS_END = dvobj.getRecord().get("INS_END");   //등록종료일자
        String   PAYTRM_START_DAY = dvobj.getRecord().get("PAYTRM_START_DAY");   //납부기간 시작 일자
        String   PAYTRM_END_DAY = dvobj.getRecord().get("PAYTRM_END_DAY");   //납부기간 종료 일자
        String   INS_START = dvobj.getRecord().get("INS_START");   //등록시작일자
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   REPT_FROM = dvobj.getRecord().get("REPT_FROM");   //입금시작일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.BRAN_CD , GIBU.GET_BRAN_NM(A.BRAN_CD) AS BRAN_NM , A.STAFF_CD , FIDU.GET_STAFF_NM(A.STAFF_CD) AS STAFF_NM , A.INS_DAY , A.INS_NUM , A.UPSO_CD , B.UPSO_NM , A.NONPY_TERM , A.NONPY_AMT , A.CARD_GBN , A.CARD_NUM , A.TERM_YRMN , A.INSTP_MONTH_FREQ , A.START_YRMN || '01' AS START_YRMN , A.END_YRMN || '01' AS END_YRMN , A.PAY_AMT , A.PAY_DAY , A.APPRV_NUM , A.REPT_DAY , A.REPT_AMT , A.COMIS , A.REMAK , (CASE WHEN C.UPSO_CD IS NOT NULL THEN 'Y' ELSE 'N' END) AS REPT_YN  ";
        query +=" FROM GIBU.TBRA_REPT_CARD_REQ A , GIBU.TBRA_UPSO B , GIBU.TBRA_REPT_CARD C  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.BRAN_CD = DECODE(:BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD)  ";
        query +=" AND A.STAFF_CD = NVL(:STAFF_CD, A.STAFF_CD)  ";
        query +=" AND A.CARD_GBN = NVL(:CARD_GBN, A.CARD_GBN)  ";
        query +=" AND A.INS_DAY = SUBSTR(C.REMAK(+), 1, 8)  ";
        query +=" AND A.INS_NUM = SUBSTR(C.REMAK(+), 10, 4)  ";
        if( !INS_START.equals("")  && !INS_END.equals("") )
        {
            query +=" AND A.INS_DAY BETWEEN :INS_START  ";
            query +=" AND :INS_END  ";
        }
        if( !PAYTRM_START_DAY.equals("")  && !PAYTRM_END_DAY.equals("") )
        {
            query +=" AND A.PAY_DAY BETWEEN :PAYTRM_START_DAY  ";
            query +=" AND :PAYTRM_END_DAY  ";
        }
        if( !REPT_FROM.equals("")  && !REPT_TO.equals("") )
        {
            query +=" AND A.REPT_DAY BETWEEN :REPT_FROM  ";
            query +=" AND :REPT_TO  ";
        }
        query +=" ORDER BY INS_DAY, INS_NUM  ";
        sobj.setSql(query);
        if(!REPT_TO.equals(""))
        {
            sobj.setString("REPT_TO", REPT_TO);               //입금종료일
        }
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        if(!INS_END.equals(""))
        {
            sobj.setString("INS_END", INS_END);               //등록종료일자
        }
        if(!PAYTRM_START_DAY.equals(""))
        {
            sobj.setString("PAYTRM_START_DAY", PAYTRM_START_DAY);               //납부기간 시작 일자
        }
        if(!PAYTRM_END_DAY.equals(""))
        {
            sobj.setString("PAYTRM_END_DAY", PAYTRM_END_DAY);               //납부기간 종료 일자
        }
        if(!INS_START.equals(""))
        {
            sobj.setString("INS_START", INS_START);               //등록시작일자
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        if(!REPT_FROM.equals(""))
        {
            sobj.setString("REPT_FROM", REPT_FROM);               //입금시작일
        }
        return sobj;
    }
    //##**$$sel_card_req
    //##**$$mng_card_req
    /*
    */
    public DOBJ CTLmng_card_req(DOBJ dobj)
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
            dobj  = CALLmng_card_req_MIUD1(Conn, dobj);           //  IUD분기
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
    public DOBJ CTLmng_card_req( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_card_req_MIUD1(Conn, dobj);           //  IUD분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // IUD분기
    public DOBJ CALLmng_card_req_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_card_req_SEL8(Conn, dobj);           //  INS_NUM채번
                dobj  = CALLmng_card_req_INS5(Conn, dobj);           //  입력
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_card_req_UPD6(Conn, dobj);           //  수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_card_req_DEL7(Conn, dobj);           //  삭제
            }
        }
        return dobj;
    }
    // 삭제
    public DOBJ CALLmng_card_req_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_card_req_DEL7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_card_req_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //등록 일자
        String   INS_NUM = dvobj.getRecord().get("INS_NUM");   //등록 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_CARD_REQ  \n";
        query +=" where INS_NUM=:INS_NUM  \n";
        query +=" and INS_DAY=:INS_DAY";
        sobj.setSql(query);
        sobj.setString("INS_DAY", INS_DAY);               //등록 일자
        sobj.setString("INS_NUM", INS_NUM);               //등록 번호
        return sobj;
    }
    // 수정
    public DOBJ CALLmng_card_req_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_card_req_UPD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_card_req_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //미납 금액
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //승인 번호
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        double   PAY_AMT = dvobj.getRecord().getDouble("PAY_AMT");   //납부금액
        String   CARD_NUM = dvobj.getRecord().get("CARD_NUM");   //카드 번호
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //입금 금액
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   INS_NUM = dvobj.getRecord().get("INS_NUM");   //등록 번호
        String   NONPY_TERM = dvobj.getRecord().get("NONPY_TERM");
        String   INSTP_MONTH_FREQ = dvobj.getRecord().get("INSTP_MONTH_FREQ");   //분납 개월 수
        String   CARD_GBN = dvobj.getRecord().get("CARD_GBN");   //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        String   PAY_DAY = dvobj.getRecord().get("PAY_DAY");   //납부 일자
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //등록 일자
        String   TERM_YRMN = dvobj.getRecord().get("TERM_YRMN");   //해지 일자
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //입금일자
        double   COMIS = dvobj.getRecord().getDouble("COMIS");   //수수료
        String   END_YRMN = wutil.substr(dobj.getRetObject("R").getRecord().get("END_YRMN"),0,6);   //종료년월
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   START_YRMN = wutil.substr(dobj.getRetObject("R").getRecord().get("START_YRMN"),0,6);   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT_CARD_REQ  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , COMIS=:COMIS , REPT_DAY=:REPT_DAY , TERM_YRMN=:TERM_YRMN , PAY_DAY=:PAY_DAY , START_YRMN=:START_YRMN , CARD_GBN=:CARD_GBN , INSTP_MONTH_FREQ=:INSTP_MONTH_FREQ , NONPY_TERM=:NONPY_TERM , UPSO_CD=:UPSO_CD , MOD_DATE=SYSDATE , REPT_AMT=:REPT_AMT , CARD_NUM=:CARD_NUM , END_YRMN=:END_YRMN , PAY_AMT=:PAY_AMT , REMAK=:REMAK , APPRV_NUM=:APPRV_NUM , NONPY_AMT=:NONPY_AMT  \n";
        query +=" where INS_DAY=:INS_DAY  \n";
        query +=" and INS_NUM=:INS_NUM";
        sobj.setSql(query);
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //미납 금액
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setDouble("PAY_AMT", PAY_AMT);               //납부금액
        sobj.setString("CARD_NUM", CARD_NUM);               //카드 번호
        sobj.setDouble("REPT_AMT", REPT_AMT);               //입금 금액
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("INS_NUM", INS_NUM);               //등록 번호
        sobj.setString("NONPY_TERM", NONPY_TERM);
        sobj.setString("INSTP_MONTH_FREQ", INSTP_MONTH_FREQ);               //분납 개월 수
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        sobj.setString("PAY_DAY", PAY_DAY);               //납부 일자
        sobj.setString("INS_DAY", INS_DAY);               //등록 일자
        sobj.setString("TERM_YRMN", TERM_YRMN);               //해지 일자
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setDouble("COMIS", COMIS);               //수수료
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // INS_NUM채번
    public DOBJ CALLmng_card_req_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_card_req_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_card_req_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(TO_NUMBER(INS_NUM)),  0)  +  1,  4,  '0')  AS  INS_NUM  FROM  GIBU.TBRA_REPT_CARD_REQ  WHERE  INS_DAY  =  TO_CHAR(SYSDATE,  'YYYYMMDD') ";
        sobj.setSql(query);
        return sobj;
    }
    // 입력
    public DOBJ CALLmng_card_req_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_card_req_INS5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_card_req_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //미납 금액
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //승인 번호
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        double   PAY_AMT = dvobj.getRecord().getDouble("PAY_AMT");   //납부금액
        String   CARD_NUM = dvobj.getRecord().get("CARD_NUM");   //카드 번호
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //입금 금액
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   NONPY_TERM = dvobj.getRecord().get("NONPY_TERM");
        String   INSTP_MONTH_FREQ = dvobj.getRecord().get("INSTP_MONTH_FREQ");   //분납 개월 수
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   CARD_GBN = dvobj.getRecord().get("CARD_GBN");   //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        String   PAY_DAY = dvobj.getRecord().get("PAY_DAY");   //납부 일자
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //등록 일자
        String   TERM_YRMN = dvobj.getRecord().get("TERM_YRMN");   //해지 일자
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //입금일자
        double   COMIS = dvobj.getRecord().getDouble("COMIS");   //수수료
        String   END_YRMN = wutil.substr(dobj.getRetObject("R").getRecord().get("END_YRMN"),0,6);   //종료년월
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   INS_NUM = dobj.getRetObject("SEL8").getRecord().get("INS_NUM");   //등록 번호
        String   START_YRMN = wutil.substr(dobj.getRetObject("R").getRecord().get("START_YRMN"),0,6);   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_CARD_REQ (COMIS, REPT_DAY, INSPRES_ID, TERM_YRMN, INS_DAY, PAY_DAY, START_YRMN, INS_DATE, CARD_GBN, STAFF_CD, INSTP_MONTH_FREQ, NONPY_TERM, INS_NUM, UPSO_CD, REPT_AMT, CARD_NUM, PAY_AMT, BRAN_CD, END_YRMN, REMAK, APPRV_NUM, NONPY_AMT)  \n";
        query +=" values(:COMIS , :REPT_DAY , :INSPRES_ID , :TERM_YRMN , :INS_DAY , :PAY_DAY , :START_YRMN , SYSDATE, :CARD_GBN , :STAFF_CD , :INSTP_MONTH_FREQ , :NONPY_TERM , :INS_NUM , :UPSO_CD , :REPT_AMT , :CARD_NUM , :PAY_AMT , :BRAN_CD , :END_YRMN , :REMAK , :APPRV_NUM , :NONPY_AMT )";
        sobj.setSql(query);
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //미납 금액
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setDouble("PAY_AMT", PAY_AMT);               //납부금액
        sobj.setString("CARD_NUM", CARD_NUM);               //카드 번호
        sobj.setDouble("REPT_AMT", REPT_AMT);               //입금 금액
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("NONPY_TERM", NONPY_TERM);
        sobj.setString("INSTP_MONTH_FREQ", INSTP_MONTH_FREQ);               //분납 개월 수
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        sobj.setString("PAY_DAY", PAY_DAY);               //납부 일자
        sobj.setString("INS_DAY", INS_DAY);               //등록 일자
        sobj.setString("TERM_YRMN", TERM_YRMN);               //해지 일자
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setDouble("COMIS", COMIS);               //수수료
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("INS_NUM", INS_NUM);               //등록 번호
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    //##**$$mng_card_req
    //##**$$end
}
