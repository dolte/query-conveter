
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s07
{
    public bra03_s07()
    {
    }
    //##**$$auto_upso_regist
    /* * 프로그램명 : bra03_s07
    * 작성자 : 서정재
    * 작성일 : 2009/09/27
    * 설명    :
    Input
    APPTN_DAY (신청 일자)
    AUTO_ACCNNUM (출금 계좌번호)
    AUTO_NUM (일련 번호)
    BANK_CD (은행 코드)
    IUDFLAG (IUDFLAG)
    REMAK (비고)
    RESINUM (주민번호)
    UPSO_CD (업소 코드)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLauto_upso_regist(DOBJ dobj)
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
            dobj  = CALLauto_upso_regist_MIUD2(Conn, dobj);           //  관리
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
    public DOBJ CTLauto_upso_regist( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_upso_regist_MIUD2(Conn, dobj);           //  관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 관리
    public DOBJ CALLauto_upso_regist_MIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_upso_regist_SEL10(Conn, dobj);           //  더미
                if(!dobj.getRetObject("S").getRecord().get("RECEPTION_GBN").equals("7"))
                {
                    dobj  = CALLauto_upso_regist_INS6(Conn, dobj);           //  자동이체신청등록
                }
                else
                {
                    dobj  = CALLauto_upso_regist_INS12(Conn, dobj);           //  자동이체신청등록
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_upso_regist_SEL13(Conn, dobj);           //  더미
                if(!dobj.getRetObject("S").getRecord().get("RECEPTION_GBN").equals("7"))
                {
                    dobj  = CALLauto_upso_regist_UPD7(Conn, dobj);           //  자동이체신청정보수정
                }
                else
                {
                    dobj  = CALLauto_upso_regist_UPD15(Conn, dobj);           //  자동이체신청정보수정
                }
            }
        }
        return dobj;
    }
    // 더미
    public DOBJ CALLauto_upso_regist_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_upso_regist_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_upso_regist_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SYSDATE  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 더미
    public DOBJ CALLauto_upso_regist_SEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL13");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_upso_regist_SEL13(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_upso_regist_SEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SYSDATE  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 자동이체신청등록
    // 계좌의 경우
    public DOBJ CALLauto_upso_regist_INS6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLauto_upso_regist_INS6(dobj, dvobj);
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
    private SQLObject SQLauto_upso_regist_INS6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int  AUTO_NUM = 0;        //일련 번호
        String INS_DATE ="";        //등록 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   RECEPTION_GBN = dvobj.getRecord().get("RECEPTION_GBN");   //접수처
        String   AUTO_ACCNNUM = dvobj.getRecord().get("AUTO_ACCNNUM");   //출금 계좌번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
        String   AUTO_NUM_1 = dvobj.getRecord().get("UPSO_CD");   //일련 번호
        String   TERM_YN = dvobj.getRecord().get("TERM_YN");   //해지 여부
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   TERM_YRMN = dvobj.getRecord().get("TERM_YRMN");   //해지 일자
        String   BANK_CD = dvobj.getRecord().get("BANK_CD");   //은행 코드
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   PROC_GBN ="N";   //자동 처리 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO (INSPRES_ID, BANK_CD, TERM_YRMN, PAYPRES_NM, TERM_YN, AUTO_NUM, PROC_GBN, INS_DATE, APPTN_DAY, UPSO_CD, AUTO_ACCNNUM, RECEPTION_GBN, RESINUM, REMAK)  \n";
        query +=" values(:INSPRES_ID , :BANK_CD , :TERM_YRMN , :PAYPRES_NM , :TERM_YN , (SELECT NVL(MAX(AUTO_NUM), 0) + 1 AUTO_NUM FROM GIBU.TBRA_UPSO_AUTO WHERE UPSO_CD = :AUTO_NUM_1), :PROC_GBN , SYSDATE, :APPTN_DAY , :UPSO_CD , :AUTO_ACCNNUM , :RECEPTION_GBN , :RESINUM , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //접수처
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //출금 계좌번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("AUTO_NUM_1", AUTO_NUM_1);               //일련 번호
        sobj.setString("TERM_YN", TERM_YN);               //해지 여부
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("TERM_YRMN", TERM_YRMN);               //해지 일자
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("PROC_GBN", PROC_GBN);               //자동 처리 구분
        return sobj;
    }
    // 자동이체신청정보수정
    // 계좌의 경우
    public DOBJ CALLauto_upso_regist_UPD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_upso_regist_UPD7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD7") ;
        rvobj.Println("UPD7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_upso_regist_UPD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   RECEPTION_GBN = dvobj.getRecord().get("RECEPTION_GBN");   //접수처
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   AUTO_ACCNNUM = dvobj.getRecord().get("AUTO_ACCNNUM");   //출금 계좌번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
        int   AUTO_NUM = dvobj.getRecord().getInt("AUTO_NUM");   //일련 번호
        String   TERM_YN = dvobj.getRecord().get("TERM_YN");   //해지 여부
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   TERM_YRMN = dvobj.getRecord().get("TERM_YRMN");   //해지 일자
        String   BANK_CD = dvobj.getRecord().get("BANK_CD");   //은행 코드
        String   CARD_GBN ="";   //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        String   CARD_NUM ="";   //카드 번호
        String   EXPIRE_DAY ="";   //만기 일
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , BANK_CD=:BANK_CD , TERM_YRMN=:TERM_YRMN , PAYPRES_NM=:PAYPRES_NM , TERM_YN=:TERM_YN , EXPIRE_DAY=:EXPIRE_DAY , CARD_GBN=:CARD_GBN , APPTN_DAY=:APPTN_DAY , AUTO_ACCNNUM=:AUTO_ACCNNUM , RESINUM=:RESINUM , MOD_DATE=SYSDATE , RECEPTION_GBN=:RECEPTION_GBN , CARD_NUM=:CARD_NUM , REMAK=:REMAK  \n";
        query +=" where AUTO_NUM=:AUTO_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //접수처
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //출금 계좌번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setInt("AUTO_NUM", AUTO_NUM);               //일련 번호
        sobj.setString("TERM_YN", TERM_YN);               //해지 여부
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("TERM_YRMN", TERM_YRMN);               //해지 일자
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        sobj.setString("CARD_NUM", CARD_NUM);               //카드 번호
        sobj.setString("EXPIRE_DAY", EXPIRE_DAY);               //만기 일
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 자동이체신청등록
    // 신용카드의 경우
    public DOBJ CALLauto_upso_regist_INS12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_upso_regist_INS12(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS12") ;
        rvobj.Println("INS12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_upso_regist_INS12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int  AUTO_NUM = 0;        //일련 번호
        String INS_DATE ="";        //등록 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   RECEPTION_GBN = dvobj.getRecord().get("RECEPTION_GBN");   //접수처
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
        String   AUTO_NUM_1 = dvobj.getRecord().get("UPSO_CD");   //일련 번호
        String   EXPIRE_DAY = dvobj.getRecord().get("EXPIRE_DAY");   //만기 일
        String   TERM_YN = dvobj.getRecord().get("TERM_YN");   //해지 여부
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   TERM_YRMN = dvobj.getRecord().get("TERM_YRMN");   //해지 일자
        String   CARD_GBN = dobj.getRetObject("S").getRecord().get("BANK_CD");   //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        String   CARD_NUM = dobj.getRetObject("S").getRecord().get("AUTO_ACCNNUM");   //카드 번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   PROC_GBN ="N";   //자동 처리 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO (INSPRES_ID, TERM_YRMN, PAYPRES_NM, TERM_YN, EXPIRE_DAY, AUTO_NUM, PROC_GBN, INS_DATE, CARD_GBN, APPTN_DAY, UPSO_CD, RESINUM, RECEPTION_GBN, CARD_NUM, REMAK)  \n";
        query +=" values(:INSPRES_ID , :TERM_YRMN , :PAYPRES_NM , :TERM_YN , :EXPIRE_DAY , (SELECT NVL(MAX(AUTO_NUM), 0) + 1 AUTO_NUM FROM GIBU.TBRA_UPSO_AUTO WHERE UPSO_CD = :AUTO_NUM_1), :PROC_GBN , SYSDATE, :CARD_GBN , :APPTN_DAY , :UPSO_CD , :RESINUM , :RECEPTION_GBN , :CARD_NUM , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //접수처
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("AUTO_NUM_1", AUTO_NUM_1);               //일련 번호
        sobj.setString("EXPIRE_DAY", EXPIRE_DAY);               //만기 일
        sobj.setString("TERM_YN", TERM_YN);               //해지 여부
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("TERM_YRMN", TERM_YRMN);               //해지 일자
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        sobj.setString("CARD_NUM", CARD_NUM);               //카드 번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("PROC_GBN", PROC_GBN);               //자동 처리 구분
        return sobj;
    }
    // 자동이체신청정보수정
    // 계좌의 경우
    public DOBJ CALLauto_upso_regist_UPD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD15");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_upso_regist_UPD15(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD15") ;
        rvobj.Println("UPD15");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_upso_regist_UPD15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   RECEPTION_GBN = dvobj.getRecord().get("RECEPTION_GBN");   //접수처
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
        int   AUTO_NUM = dvobj.getRecord().getInt("AUTO_NUM");   //일련 번호
        String   EXPIRE_DAY = dvobj.getRecord().get("EXPIRE_DAY");   //만기 일
        String   TERM_YN = dvobj.getRecord().get("TERM_YN");   //해지 여부
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   TERM_YRMN = dvobj.getRecord().get("TERM_YRMN");   //해지 일자
        String   AUTO_ACCNNUM ="";   //출금 계좌번호
        String   BANK_CD ="";   //은행 코드
        String   CARD_GBN = dobj.getRetObject("S").getRecord().get("BANK_CD");   //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        String   CARD_NUM = dobj.getRetObject("S").getRecord().get("AUTO_ACCNNUM");   //카드 번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , BANK_CD=:BANK_CD , TERM_YRMN=:TERM_YRMN , PAYPRES_NM=:PAYPRES_NM , TERM_YN=:TERM_YN , EXPIRE_DAY=:EXPIRE_DAY , CARD_GBN=:CARD_GBN , APPTN_DAY=:APPTN_DAY , AUTO_ACCNNUM=:AUTO_ACCNNUM , RESINUM=:RESINUM , MOD_DATE=SYSDATE , RECEPTION_GBN=:RECEPTION_GBN , CARD_NUM=:CARD_NUM , REMAK=:REMAK  \n";
        query +=" where AUTO_NUM=:AUTO_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //접수처
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setInt("AUTO_NUM", AUTO_NUM);               //일련 번호
        sobj.setString("EXPIRE_DAY", EXPIRE_DAY);               //만기 일
        sobj.setString("TERM_YN", TERM_YN);               //해지 여부
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("TERM_YRMN", TERM_YRMN);               //해지 일자
        sobj.setString("AUTO_ACCNNUM", AUTO_ACCNNUM);               //출금 계좌번호
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        sobj.setString("CARD_NUM", CARD_NUM);               //카드 번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    //##**$$auto_upso_regist
    //##**$$end
}
