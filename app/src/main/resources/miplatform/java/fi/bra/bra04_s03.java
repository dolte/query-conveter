
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s03
{
    public bra04_s03()
    {
    }
    //##**$$rept_ocr_insert
    /* * 프로그램명 : bra04_s03
    * 작성자 : 박태현
    * 작성일 : 2009/11/26
    * 설명    : 입금마감 확인 후 금결원에서 생성된 파일의 내용을 읽어 입금 테이블 (TBRA_REPT, TBRA_REPT_OCR) 과 원장 테이블 (TBRA_NOTE) 에
    입금 정보를 저장한다.
    금결원 생성 내용 중 고객번호 (20 자리) 는 업소 테이블의 고객 번호와 청구기간 정보를 가지고 있다. 이를 활용하여 업소 및 청구정보를
    조회한 후 청구 매핑 정보, 원장정보를 생성한다.
    금결원 정보에 계좌번호 정보가 없기 때문에 회계 계좌번호 테이블 (TCAC_ACCOUNT) 의 자동이체/OCR 계좌번호를 조회한다. (USAGE = '004')
    Input
    CLIENT_NUM (고객 번호)
    COMIS (수수료)
    END_YRMN (종료년월)
    INSPRES_ID (등록자 ID)
    IRN_NO (IRN_NO)
    MAKE_ORG (MAKE_ORG)
    OCR_GBN (OCR_GBN)
    OCR_SEQ (OCR_SEQ) a
    PROC_GBN (자동 처리 구분)
    RECV_BANK_CD (접수 은행)
    RECV_DAY (영수 일자)
    RECV_GBN (RECV_GBN)
    REPT_AMT (입금 금액)
    REPT_DAY (입금일자)
    START_YRMN (시작년월)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLrept_ocr_insert(DOBJ dobj)
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
            dobj  = CALLrept_ocr_insert_SEL1(Conn, dobj);           //  입금마감확인
            if( dobj.getRetObject("SEL1").getRecord().get("BRANEND").equals("0"))
            {
                dobj  = CALLrept_ocr_insert_SEL22(Conn, dobj);           //  자료생성
                dobj  = CALLrept_ocr_insert_SEL10(Conn, dobj);           //  지로 입금 계좌번호 조회
                dobj  = CALLrept_ocr_insert_DEL4(Conn, dobj);           //  입금 내역 (OCR) 삭제
                dobj  = CALLrept_ocr_insert_DEL6(Conn, dobj);           //  입금 내역 삭제
                dobj  = CALLrept_ocr_insert_DEL9(Conn, dobj);           //  청구 입금 매핑 정보 삭제
                dobj  = CALLrept_ocr_insert_DEL10(Conn, dobj);           //  원장 정보 삭제
                dobj  = CALLrept_ocr_insert_DEL11(Conn, dobj);           //  잔고 정보 삭제
                dobj  = CALLrept_ocr_insert_DEL20(Conn, dobj);           //  오류내역 삭제
                dobj  = CALLrept_ocr_insert_MPD12(Conn, dobj);           //  입금정보 처리
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLrept_ocr_insert_SEL2(Conn, dobj);           //  처리 건수 검색
                dobj  = CALLrept_ocr_insert_SEL3(Conn, dobj);           //  정상처리 입금 내역 조회
                dobj  = CALLrept_ocr_insert_SEL4(Conn, dobj);           //  입금 에러 내역 조회
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
    public DOBJ CTLrept_ocr_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_ocr_insert_SEL1(Conn, dobj);           //  입금마감확인
        if( dobj.getRetObject("SEL1").getRecord().get("BRANEND").equals("0"))
        {
            dobj  = CALLrept_ocr_insert_SEL22(Conn, dobj);           //  자료생성
            dobj  = CALLrept_ocr_insert_SEL10(Conn, dobj);           //  지로 입금 계좌번호 조회
            dobj  = CALLrept_ocr_insert_DEL4(Conn, dobj);           //  입금 내역 (OCR) 삭제
            dobj  = CALLrept_ocr_insert_DEL6(Conn, dobj);           //  입금 내역 삭제
            dobj  = CALLrept_ocr_insert_DEL9(Conn, dobj);           //  청구 입금 매핑 정보 삭제
            dobj  = CALLrept_ocr_insert_DEL10(Conn, dobj);           //  원장 정보 삭제
            dobj  = CALLrept_ocr_insert_DEL11(Conn, dobj);           //  잔고 정보 삭제
            dobj  = CALLrept_ocr_insert_DEL20(Conn, dobj);           //  오류내역 삭제
            dobj  = CALLrept_ocr_insert_MPD12(Conn, dobj);           //  입금정보 처리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLrept_ocr_insert_SEL2(Conn, dobj);           //  처리 건수 검색
            dobj  = CALLrept_ocr_insert_SEL3(Conn, dobj);           //  정상처리 입금 내역 조회
            dobj  = CALLrept_ocr_insert_SEL4(Conn, dobj);           //  입금 에러 내역 조회
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
    public DOBJ CALLrept_ocr_insert_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_ocr_insert_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // 자료생성
    public DOBJ CALLrept_ocr_insert_SEL22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL22");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL22");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_ocr_insert_SEL22(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL22");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_SEL22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //입금일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_DAY  AS  REPT_DAY  ,  '04'		AS  REPT_GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        return sobj;
    }
    // 지로 입금 계좌번호 조회
    // 지로 입금 계좌번호를 조회한다
    public DOBJ CALLrept_ocr_insert_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_ocr_insert_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BANK_CD  ,  ACCN_NUM  FROM  ACCT.TCAC_ACCOUNT  WHERE  USE_TYPE  =  '002' ";
        sobj.setSql(query);
        return sobj;
    }
    // 입금 내역 (OCR) 삭제
    // 동일한 입금년월을 가진 입금 정보(TBRA_REPT_OCR)를 삭제한다
    public DOBJ CALLrept_ocr_insert_DEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL4");
        VOBJ dvobj = dobj.getRetObject("SEL22");           //자료생성에서 생성시킨 OBJECT입니다.(CALLrept_ocr_insert_SEL22)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_ocr_insert_DEL4(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL4") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_DEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //입금일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_OCR  \n";
        query +=" where REPT_DAY=:REPT_DAY";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        return sobj;
    }
    // 입금 내역 삭제
    // 동일한 입금년월을 가진 입금 정보 (TRRA_REPT) 를 삭제한다
    public DOBJ CALLrept_ocr_insert_DEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL6");
        VOBJ dvobj = dobj.getRetObject("SEL22");           //자료생성에서 생성시킨 OBJECT입니다.(CALLrept_ocr_insert_SEL22)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_ocr_insert_DEL6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_DEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //입금 구분
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //입금일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        return sobj;
    }
    // 청구 입금 매핑 정보 삭제
    // 입금년월을 기준으로 청구 매핑 정보를 삭제한다
    public DOBJ CALLrept_ocr_insert_DEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL9");
        VOBJ dvobj = dobj.getRetObject("SEL22");           //자료생성에서 생성시킨 OBJECT입니다.(CALLrept_ocr_insert_SEL22)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_ocr_insert_DEL9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_DEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //입금 구분
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //입금일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_DEMD_REPT  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        return sobj;
    }
    // 원장 정보 삭제
    // 입금년월을 기준으로 원장 정보를 삭제한다
    public DOBJ CALLrept_ocr_insert_DEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL10");
        VOBJ dvobj = dobj.getRetObject("SEL22");           //자료생성에서 생성시킨 OBJECT입니다.(CALLrept_ocr_insert_SEL22)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_ocr_insert_DEL10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL10") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_DEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //입금 구분
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //입금일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_NOTE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        return sobj;
    }
    // 잔고 정보 삭제
    // 입금년월을 기준으로 잔고 정보를 삭제한다
    public DOBJ CALLrept_ocr_insert_DEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL11");
        VOBJ dvobj = dobj.getRetObject("SEL22");           //자료생성에서 생성시킨 OBJECT입니다.(CALLrept_ocr_insert_SEL22)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_ocr_insert_DEL11(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL11") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_DEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //입금 구분
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //입금일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_BALANCE  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        return sobj;
    }
    // 오류내역 삭제
    public DOBJ CALLrept_ocr_insert_DEL20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL20");
        VOBJ dvobj = dobj.getRetObject("SEL22");           //자료생성에서 생성시킨 OBJECT입니다.(CALLrept_ocr_insert_SEL22)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_ocr_insert_DEL20(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL20") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_DEL20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //입금 구분
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //입금일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_ERR  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        return sobj;
    }
    // 입금정보 처리
    // 레코드 단위로 입금정보를 처리한다
    public DOBJ CALLrept_ocr_insert_MPD12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD12");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("OCR_GBN").equals("22"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrept_ocr_insert_OSP1(Conn, dobj);           //  OCR 입금
            }
        }
        return dobj;
    }
    // OCR 입금
    // 업소별로 OCR 입금 정보를 저장한다
    public DOBJ CALLrept_ocr_insert_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        String[]  incolumns ={"REPT_DAY","DEMD_GBN","CLIENT_NUM","START_YRMN","END_YRMN","REPT_AMT","COMIS","RECV_DAY","REMAK","BANK_CD","ACCN_NUM","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   ACCN_NUM = dobj.getRetObject("SEL10").getRecord().get("ACCN_NUM");         //계좌 번호
            record.put("ACCN_NUM",ACCN_NUM);
            ////
            String   BANK_CD = dobj.getRetObject("SEL10").getRecord().get("BANK_CD");         //은행 코드
            record.put("BANK_CD",BANK_CD);
            ////
            String   DEMD_GBN ="O";         //청구 구분
            record.put("DEMD_GBN",DEMD_GBN);
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
        String   spname ="GIBU.SP_PROC_REPT_OCR";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"P_CNT_INST"};;
        int[]    outtypes ={12};;
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
        robj.setName("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // 처리 건수 검색
    // 입금에 따른 처리 건수 검색
    public DOBJ CALLrept_ocr_insert_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_ocr_insert_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //입금일자
        String   REPT_GBN ="04";   //입금 구분
        String   PROC_CNT = dobj.getRetObject("S").getRecordCnt()+"";   //PROC_CNT
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :PROC_CNT  AS  PROC_CNT  ,  MAX(NOTE_CNT)  AS  NOTE_CNT  ,  MAX(REPT_CNT)  AS  REPT_CNT  ,  MAX(ERR_CNT)  AS  ERR_CNT  FROM  (   \n";
        query +=" SELECT  COUNT(REPT_NUM)  REPT_CNT  ,  0  ERR_CNT  ,  0  NOTE_CNT  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  UNION  ALL   \n";
        query +=" SELECT  0  ,  COUNT(REPT_NUM)  ERR_CNT  ,  0  FROM  GIBU.TBRA_REPT_ERR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  UNION  ALL   \n";
        query +=" SELECT  0  ,  0  ,  COUNT(NOTE_YRMN)  NOTE_CNT  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  ) ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("PROC_CNT", PROC_CNT);               //PROC_CNT
        return sobj;
    }
    // 정상처리 입금 내역 조회
    // 정상처리된 입금 내역을 조회한다
    public DOBJ CALLrept_ocr_insert_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_ocr_insert_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //입금일자
        String   REPT_GBN = dobj.getRetObject("SEL22").getRecord().get("REPT_GBN");   //입금 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  E.DEPT_NM  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  B.UPSO_CD  ,  D.UPSO_NM  ,  F.HAN_NM  ||  '('  ||  D.STAFF_CD  ||  ')'  STAFF_CD  ,  C.START_YRMN  ,  C.END_YRMN  ,  A.REPT_AMT  ,  A.COMIS  ,  A.RECV_DAY  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.BRAN_CD  ,  C.MONPRNCFEE  ,  C.TOT_DEMD_AMT  -  (C.TOT_ADDT_AMT  +  C.TOT_EADDT_AMT)  TOT_USE_AMT  ,  C.TOT_ADDT_AMT  +  C.TOT_EADDT_AMT  TOT_ADDT_AMT  ,  C.DEMD_YRMN  FROM  GIBU.TBRA_REPT  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  MAX(DEMD_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN  )  B  ,  GIBU.TBRA_DEMD_OCR  C  ,  GIBU.TBRA_UPSO  D  ,  INSA.TCPM_DEPT  E  ,  INSA.TINS_MST01  F  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  B.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  =  A.REPT_GBN   \n";
        query +=" AND  C.DEMD_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  C.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  D.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  E.GIBU  =  D.BRAN_CD   \n";
        query +=" AND  F.STAFF_NUM(+)  =  D.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        return sobj;
    }
    // 입금 에러 내역 조회
    // 입금 처리 진행 중 발생한 오류 내역을 조회한다
    public DOBJ CALLrept_ocr_insert_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_ocr_insert_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_insert_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //입금일자
        String   REPT_GBN ="04";   //입금 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT   \n";
        query +=" (SELECT  DEPT_NM  FROM  INSA.TCPM_DEPT  WHERE  GIBU  =  A.BRAN_CD)  DEPT_NM  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.UPSO_CD  ,  B.UPSO_NM  ,   \n";
        query +=" (SELECT  HAN_NM  ||  '('  ||  B.STAFF_CD  ||  ')'  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  A.STAFF_CD)  STAFF_NM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.REPT_AMT  ,  A.COMIS  ,  A.RECV_DAY  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.BRAN_CD  ,  A.ERR_GBN  ,  A.ERR_CTENT  ,   \n";
        query +=" (SELECT  MONPRNCFEE  FROM  GIBU.TBRA_DEMD_OCR  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  START_YRMN  =  A.START_YRMN   \n";
        query +=" AND  END_YRMN  =  A.END_YRMN)  MONPRNCFEE  ,   \n";
        query +=" (SELECT  TOT_DEMD_AMT  -  COMIS  FROM  GIBU.TBRA_DEMD_OCR  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  START_YRMN  =  A.START_YRMN   \n";
        query +=" AND  END_YRMN  =  A.END_YRMN)  TOT_USE_AMT  ,   \n";
        query +=" (SELECT  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  START_YRMN  =  A.START_YRMN   \n";
        query +=" AND  END_YRMN  =  A.END_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_REPT_ERR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  B.UPSO_CD(+)  =  A.UPSO_CD  ORDER  BY  A.BRAN_CD,  A.REPT_DAY,  A.REPT_NUM ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        return sobj;
    }
    //##**$$rept_ocr_insert
    //##**$$rept_closing
    /*
    */
    public DOBJ CTLrept_closing(DOBJ dobj)
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
            dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  지부별마감체크
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
    public DOBJ CTLrept_closing( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  지부별마감체크
        return dobj;
    }
    // 지부별마감체크
    // 지부별 마감테이블의 해당 년월 정보가 있는지 체크한다
    public DOBJ CALLrept_closing_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_closing_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        String message ="해당기간중 마감내역이 존재합니다. 마감내역을 확인하세요.";
        dobj.setRetmsg(message);
        return dobj;
    }
    private SQLObject SQLrept_closing_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //입금 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(:REPT_DAY,1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(:REPT_DAY,5,2)  =  END_MON ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금 일자
        return sobj;
    }
    //##**$$rept_closing
    //##**$$rept_ocr_select
    /*
    */
    public DOBJ CTLrept_ocr_select(DOBJ dobj)
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
            dobj  = CALLrept_ocr_select_SEL1(Conn, dobj);           //  정상처리 입금 내역 조회
            dobj  = CALLrept_ocr_select_SEL2(Conn, dobj);           //  입금 에러 내역 조회
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
    public DOBJ CTLrept_ocr_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_ocr_select_SEL1(Conn, dobj);           //  정상처리 입금 내역 조회
        dobj  = CALLrept_ocr_select_SEL2(Conn, dobj);           //  입금 에러 내역 조회
        return dobj;
    }
    // 정상처리 입금 내역 조회
    // 정상처리된 입금 내역을 조회한다
    public DOBJ CALLrept_ocr_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_ocr_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_GBN ="04";   //입금 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  E.DEPT_NM  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  B.UPSO_CD  ,  D.UPSO_NM  ,  F.HAN_NM  ||  '('  ||  D.STAFF_CD  ||  ')'  STAFF_CD  ,  C.START_YRMN  ,  C.END_YRMN  ,  A.REPT_AMT  ,  A.COMIS  ,  A.RECV_DAY  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.BRAN_CD  ,  C.MONPRNCFEE  ,  C.TOT_DEMD_AMT  -  (C.TOT_ADDT_AMT  +  C.TOT_EADDT_AMT)  TOT_USE_AMT  ,  C.TOT_ADDT_AMT  +  C.TOT_EADDT_AMT  TOT_ADDT_AMT  ,  C.DEMD_YRMN  FROM  GIBU.TBRA_REPT  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  MAX(DEMD_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN  )  B  ,  GIBU.TBRA_DEMD_OCR  C  ,  GIBU.TBRA_UPSO  D  ,  INSA.TCPM_DEPT  E  ,  INSA.TINS_MST01  F  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  B.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  =  A.REPT_GBN   \n";
        query +=" AND  C.DEMD_YRMN(+)  =  B.DEMD_YRMN   \n";
        query +=" AND  C.UPSO_CD(+)  =  B.UPSO_CD   \n";
        query +=" AND  D.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  E.GIBU  =  D.BRAN_CD   \n";
        query +=" AND  F.STAFF_NUM(+)  =  D.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        return sobj;
    }
    // 입금 에러 내역 조회
    // 입금 처리 진행 중 발생한 오류 내역을 조회한다
    public DOBJ CALLrept_ocr_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_ocr_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_ocr_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_GBN ="04";   //입금 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT   \n";
        query +=" (SELECT  DEPT_NM  FROM  INSA.TCPM_DEPT  WHERE  GIBU  =  A.BRAN_CD)  DEPT_NM  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.UPSO_CD  ,  B.UPSO_NM  ,   \n";
        query +=" (SELECT  HAN_NM  ||  '('  ||  B.STAFF_CD  ||  ')'  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  A.STAFF_CD)  STAFF_NM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.REPT_AMT  ,  A.COMIS  ,  A.RECV_DAY  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.BRAN_CD  ,  A.ERR_GBN  ,  A.ERR_CTENT  ,   \n";
        query +=" (SELECT  MONPRNCFEE  FROM  GIBU.TBRA_DEMD_OCR  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  START_YRMN  =  A.START_YRMN   \n";
        query +=" AND  END_YRMN  =  A.END_YRMN)  MONPRNCFEE  ,   \n";
        query +=" (SELECT  TOT_DEMD_AMT  -  COMIS  FROM  GIBU.TBRA_DEMD_OCR  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  START_YRMN  =  A.START_YRMN   \n";
        query +=" AND  END_YRMN  =  A.END_YRMN)  TOT_USE_AMT  ,   \n";
        query +=" (SELECT  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  START_YRMN  =  A.START_YRMN   \n";
        query +=" AND  END_YRMN  =  A.END_YRMN)  DEMD_YRMN  FROM  GIBU.TBRA_REPT_ERR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  B.UPSO_CD(+)  =  A.UPSO_CD  ORDER  BY  A.BRAN_CD,  A.REPT_DAY,  A.REPT_NUM ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        return sobj;
    }
    //##**$$rept_ocr_select
    //##**$$end
}
