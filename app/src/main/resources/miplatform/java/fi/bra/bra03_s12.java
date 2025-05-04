
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s12
{
    public bra03_s12()
    {
    }
    //##**$$search
    /*
    */
    public DOBJ CTLsearch(DOBJ dobj)
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
            dobj  = CALLsearch_SEL1(Conn, dobj);           //  목록조회
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
    public DOBJ CTLsearch( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_SEL1(Conn, dobj);           //  목록조회
        return dobj;
    }
    // 목록조회
    public DOBJ CALLsearch_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DATE = dobj.getRetObject("S").getRecord().get("START_DATE");   //협회등록일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DATE = dobj.getRetObject("S").getRecord().get("END_DATE");   //마감 일시
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT TO_CHAR(A.INS_DATE, 'YYYYMMDD') AS INS_DATE , A.UPSO_CD , B.UPSO_NM , A.SEQ , A.APPLICATION_GBN , DECODE(A.RECEPTION_GBN, '5', A.BANK_CD, '7', CARD_GBN) AS BANK_CD , DECODE(A.RECEPTION_GBN, '5', (SELECT BANK_NM  ";
        query +=" FROM ACCT.TCAC_BANK  ";
        query +=" WHERE USE_YN = 'Y'  ";
        query +=" AND BANK_CD = A.BANK_CD), '7', DECODE(A.CARD_GBN, 'WIN', '삼성카드', 'LGC', '신한카드', 'AMX', '롯데카드')) AS BANK_NM , DECODE(A.RECEPTION_GBN, '5', A.AUTO_ACCNNUM, '7', A.CARD_NUM) AS AUTO_ACCNNUM , A.PAYPRES_NM , DECODE(A.CONFIRM_ID, NULL, A.RESINUM, SUBSTR(A.RESINUM, 1, 7) || '______') AS RESINUM , A.RELATION , A.APPTN_DAY , A.INSPRES_ID , A.STAFF_CD , A.CONFIRM_ID , A.RECEPTION_GBN , A.PHON_NUM , A.REMAK , DECODE(A.CONFIRM_ID, NULL, '', DECODE(A.PROC_DAY, NULL, '1', A.PROC_DAY)) AS CONFIRM_YN , A.PROC_DAY , A.EXPIRE_DAY , (CASE WHEN C.FILE_NM IS NULL THEN 'N' ELSE 'Y' END) AS ATTCH_YN , B.BRAN_CD , GIBU.GET_BRAN_NM(B.BRAN_CD) AS BRAN_NM , B.CLIENT_NUM  ";
        query +=" FROM GIBU.TBRA_UPSO_AUTO_APPLICATION A , GIBU.TBRA_UPSO B , GIBU.TBRA_UPSO_AUTO_DOC_ATTCH C  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.UPSO_CD = C.UPSO_CD(+)  ";
        query +=" AND A.SEQ = C.SEQ(+)  ";
        query +=" AND A.RECEPTION_GBN IN ('5', '7')  ";
        query +=" AND B.BRAN_CD = DECODE(:BRAN_CD, 'AL', B.BRAN_CD, :BRAN_CD)  ";
        if( !START_DATE.equals("")  && !END_DATE.equals("") )
        {
            query +=" AND TO_CHAR(A.INS_DATE, 'YYYYMMDD') BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        if( !STAFF_CD.equals("") )
        {
            query +=" AND A.STAFF_CD = :STAFF_CD  ";
        }
        query +=" ORDER BY B.BRAN_CD, A.UPSO_CD, A.INS_DATE  ";
        sobj.setSql(query);
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        }
        if(!START_DATE.equals(""))
        {
            sobj.setString("START_DATE", START_DATE);               //협회등록일
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        if(!END_DATE.equals(""))
        {
            sobj.setString("END_DATE", END_DATE);               //마감 일시
        }
        return sobj;
    }
    //##**$$search
    //##**$$save
    /*
    */
    public DOBJ CTLsave(DOBJ dobj)
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
            dobj  = CALLsave_MIUD1(Conn, dobj);           //  신청확인 관리
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
    public DOBJ CTLsave( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_MIUD1(Conn, dobj);           //  신청확인 관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 신청확인 관리
    public DOBJ CALLsave_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_SEL13(Conn, dobj);           //  저장할 해지일자 값 획득
                if( dobj.getRetObject("R").getRecord().get("APPLICATION_GBN").equals("12"))
                {
                    dobj  = CALLsave_INS15(Conn, dobj);           //  자동이체신청등록
                    dobj  = CALLsave_UPD16(Conn, dobj);           //  확인된 내용수정
                }
                else if( dobj.getRetObject("R").getRecord().get("APPLICATION_GBN").equals("18"))
                {
                    dobj  = CALLsave_SEL8(Conn, dobj);           //  내역확인
                    if( dobj.getRetObject("SEL8").getRecord().getDouble("CNT") == 1)
                    {
                        dobj  = CALLsave_UPD11(Conn, dobj);           //  해제등록
                        dobj  = CALLsave_UPD14(Conn, dobj);           //  확인된 내용수정
                    }
                }
                else
                {
                    dobj  = CALLsave_UPD6(Conn, dobj);           //  확인된 내용수정
                }
            }
        }
        return dobj;
    }
    // 저장할 해지일자 값 획득
    public DOBJ CALLsave_SEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL13");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsave_SEL13(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_SEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(SYSDATE,  'YYYYMMDD')  AS  TERM_YRMN  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 자동이체신청등록
    public DOBJ CALLsave_INS15(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsave_INS15(dobj, dvobj);
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
    private SQLObject SQLsave_INS15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int  AUTO_NUM = 0;        //일련 번호
        String INS_DATE ="";        //등록 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
        String   AUTO_NUM_1 = dvobj.getRecord().get("UPSO_CD");   //일련 번호
        String   EXPIRE_DAY = dvobj.getRecord().get("EXPIRE_DAY");   //만기 일
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   TERM_YRMN = dvobj.getRecord().get("TERM_YRMN");   //해지 일자
        String   CARD_GBN = dobj.getRetObject("R").getRecord().get("BANK_CD");   //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        String   CARD_NUM = dobj.getRetObject("R").getRecord().get("AUTO_ACCNNUM");   //카드 번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   PROC_GBN ="N";   //자동 처리 구분
        String   RECEPTION_GBN ="7";   //접수처
        String   TERM_YN ="N";   //해지 여부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO (INSPRES_ID, TERM_YRMN, PAYPRES_NM, TERM_YN, EXPIRE_DAY, AUTO_NUM, PROC_GBN, INS_DATE, CARD_GBN, APPTN_DAY, UPSO_CD, RESINUM, RECEPTION_GBN, CARD_NUM, REMAK)  \n";
        query +=" values(:INSPRES_ID , :TERM_YRMN , :PAYPRES_NM , :TERM_YN , :EXPIRE_DAY , (SELECT NVL(MAX(AUTO_NUM), 0) + 1 AUTO_NUM FROM GIBU.TBRA_UPSO_AUTO WHERE UPSO_CD = :AUTO_NUM_1), :PROC_GBN , SYSDATE, :CARD_GBN , :APPTN_DAY , :UPSO_CD , :RESINUM , :RECEPTION_GBN , :CARD_NUM , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("AUTO_NUM_1", AUTO_NUM_1);               //일련 번호
        sobj.setString("EXPIRE_DAY", EXPIRE_DAY);               //만기 일
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("TERM_YRMN", TERM_YRMN);               //해지 일자
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        sobj.setString("CARD_NUM", CARD_NUM);               //카드 번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("PROC_GBN", PROC_GBN);               //자동 처리 구분
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //접수처
        sobj.setString("TERM_YN", TERM_YN);               //해지 여부
        return sobj;
    }
    // 확인된 내용수정
    public DOBJ CALLsave_UPD16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD16");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_UPD16(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD16") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_UPD16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CONFIRM_DATE ="";        //등록 일시
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //관리번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CONFIRM_ID = dvobj.getRecord().get("CONFIRM_ID");   //계산 일자
        String   PROC_DAY = dobj.getRetObject("SEL13").getRecord().get("TERM_YRMN");   //임시컬럼1
        String   REMAK ="00:정상처리";   //비고
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" set CONFIRM_ID=:CONFIRM_ID , PROC_DAY=:PROC_DAY , CONFIRM_DATE=SYSDATE , REMAK=:REMAK  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CONFIRM_ID", CONFIRM_ID);               //계산 일자
        sobj.setString("PROC_DAY", PROC_DAY);               //임시컬럼1
        sobj.setString("REMAK", REMAK);               //비고
        return sobj;
    }
    // 내역확인
    public DOBJ CALLsave_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsave_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CARD_GBN = dobj.getRetObject("R").getRecord().get("BANK_CD");   //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   RESINUM = dobj.getRetObject("R").getRecord().get("RESINUM");   //주민번호
        String   CARD_NUM = dobj.getRetObject("R").getRecord().get("AUTO_ACCNNUM");   //카드 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  CARD_GBN  =  :CARD_GBN   \n";
        query +=" AND  CARD_NUM  =  :CARD_NUM   \n";
        query +=" AND  RESINUM  =  :RESINUM ";
        sobj.setSql(query);
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("CARD_NUM", CARD_NUM);               //카드 번호
        return sobj;
    }
    // 해제등록
    public DOBJ CALLsave_UPD11(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsave_UPD11(dobj, dvobj);
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
    private SQLObject SQLsave_UPD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CARD_GBN = dobj.getRetObject("R").getRecord().get("BANK_CD");   //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        String   CARD_NUM = dobj.getRetObject("R").getRecord().get("AUTO_ACCNNUM");   //카드 번호
        String   TERM_YN ="Y";   //해지 여부
        String   TERM_YRMN = dobj.getRetObject("SEL13").getRecord().get("TERM_YRMN");   //해지 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO  \n";
        query +=" set TERM_YRMN=:TERM_YRMN , TERM_YN=:TERM_YN  \n";
        query +=" where CARD_GBN=:CARD_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and RESINUM=:RESINUM  \n";
        query +=" and CARD_NUM=:CARD_NUM";
        sobj.setSql(query);
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        sobj.setString("CARD_NUM", CARD_NUM);               //카드 번호
        sobj.setString("TERM_YN", TERM_YN);               //해지 여부
        sobj.setString("TERM_YRMN", TERM_YRMN);               //해지 일자
        return sobj;
    }
    // 확인된 내용수정
    public DOBJ CALLsave_UPD14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD14");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_UPD14(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD14") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_UPD14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CONFIRM_DATE ="";        //등록 일시
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //관리번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CONFIRM_ID = dvobj.getRecord().get("CONFIRM_ID");   //계산 일자
        String   PROC_DAY = dobj.getRetObject("SEL13").getRecord().get("TERM_YRMN");   //임시컬럼1
        String   REMAK ="00:정상처리";   //비고
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" set CONFIRM_ID=:CONFIRM_ID , PROC_DAY=:PROC_DAY , CONFIRM_DATE=SYSDATE , REMAK=:REMAK  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CONFIRM_ID", CONFIRM_ID);               //계산 일자
        sobj.setString("PROC_DAY", PROC_DAY);               //임시컬럼1
        sobj.setString("REMAK", REMAK);               //비고
        return sobj;
    }
    // 확인된 내용수정
    public DOBJ CALLsave_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsave_UPD6(dobj, dvobj);
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
    private SQLObject SQLsave_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CONFIRM_DATE ="";        //등록 일시
        String PAYPRES_NM ="";        //납부자 명
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //관리번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CONFIRM_ID = dvobj.getRecord().get("CONFIRM_ID");   //계산 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" set CONFIRM_ID=:CONFIRM_ID , CONFIRM_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CONFIRM_ID", CONFIRM_ID);               //계산 일자
        return sobj;
    }
    //##**$$save
    //##**$$chk_dup
    /*
    */
    public DOBJ CTLchk_dup(DOBJ dobj)
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
            dobj  = CALLchk_dup_MPD1(Conn, dobj);           //  루프
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
    public DOBJ CTLchk_dup( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLchk_dup_MPD1(Conn, dobj);           //  루프
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 루프
    public DOBJ CALLchk_dup_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( 1 == 1)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLchk_dup_SEL4(Conn, dobj);           //  미처리내역 조회
                dobj  = CALLchk_dup_ADD5( dobj);        //  결과취합
            }
        }
        return dobj;
    }
    // 미처리내역 조회
    public DOBJ CALLchk_dup_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLchk_dup_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_dup_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPLICATION_GBN = dobj.getRetObject("R").getRecord().get("APPLICATION_GBN");   //신청 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO_APPLICATION  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  APPLICATION_GBN  =  :APPLICATION_GBN   \n";
        query +=" AND  REMAK  IS  NULL  GROUP  BY  UPSO_CD  HAVING  COUNT(1)  >  1 ";
        sobj.setSql(query);
        sobj.setString("APPLICATION_GBN", APPLICATION_GBN);               //신청 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 결과취합
    public DOBJ CALLchk_dup_ADD5(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("ADD5");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        VOBJ dvobj = dobj.getRetObject("SEL4");          //사용자 화면에서 발생한 Object입니다.
        rvobj = wutil.getAddedVobj(dobj,"ADD5","", dvobj );
        rvobj.setName("ADD5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    //##**$$chk_dup
    //##**$$end
}
