
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s14
{
    public bra03_s14()
    {
    }
    //##**$$upload_file
    /*
    */
    public DOBJ CTLupload_file(DOBJ dobj)
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
            dobj  = CALLupload_file_XIUD16(Conn, dobj);           //  은행접수신청서삭제
            dobj  = CALLupload_file_MPD2(Conn, dobj);           //  건별처리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupload_file_SEL1(Conn, dobj);           //  전체결과
            dobj  = CALLupload_file_SEL2(Conn, dobj);           //  에러결과결과
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
    public DOBJ CTLupload_file( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupload_file_XIUD16(Conn, dobj);           //  은행접수신청서삭제
        dobj  = CALLupload_file_MPD2(Conn, dobj);           //  건별처리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupload_file_SEL1(Conn, dobj);           //  전체결과
        dobj  = CALLupload_file_SEL2(Conn, dobj);           //  에러결과결과
        return dobj;
    }
    // 은행접수신청서삭제
    // 처리일자에 해당하는 은행접수신청서를 삭제한다.
    public DOBJ CALLupload_file_XIUD16(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLupload_file_XIUD16(dobj, dvobj);
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
    private SQLObject SQLupload_file_XIUD16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CARD_GBN = dobj.getRetObject("S1").getRecord().get("CARD_GBN");   //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE  \n";
        query +=" GIBU.TBRA_UPSO_AUTORSLT A  \n";
        query +=" WHERE A.PROC_DAY = :PROC_DAY  \n";
        query +=" AND A.CARD_GBN = :CARD_GBN  \n";
        query +=" AND A.RECPT_GBN_CD = '07'";
        sobj.setSql(query);
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    // 건별처리
    public DOBJ CALLupload_file_MPD2(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLupload_file_SEL3(Conn, dobj);           //  신청서 체크
                if(!dobj.getRetObject("SEL3").getRecord().get("UPSO_CD").equals(""))
                {
                    dobj  = CALLupload_file_INS43(Conn, dobj);           //  응답등록
                    if( dobj.getRetObject("S").getRecord().get("APPTN_RSLT").equals("00"))
                    {
                        dobj  = CALLupload_file_INS44(Conn, dobj);           //  정상등록
                        dobj  = CALLupload_file_UPD45(Conn, dobj);           //  신청서에 결과 수정
                    }
                    else if( dobj.getRetObject("S").getRecord().get("APPTN_RSLT").equals("01"))
                    {
                        dobj  = CALLupload_file_UPD16(Conn, dobj);           //  신청서에 결과 수정
                    }
                    else if( dobj.getRetObject("S").getRecord().get("APPTN_RSLT").equals("02"))
                    {
                        dobj  = CALLupload_file_UPD14(Conn, dobj);           //  신청서에 결과 수정
                    }
                }
                else
                {
                    dobj  = CALLupload_file_INS13(Conn, dobj);           //  응답등록
                }
            }
        }
        return dobj;
    }
    // 신청서 체크
    public DOBJ CALLupload_file_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupload_file_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RESINUM = dobj.getRetObject("R").getRecord().get("RESINUM");   //주민번호
        String   CLIENT_NUM = dobj.getRetObject("R").getRecord().get("CLIENT_NUM");   //고객 번호
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD,  A.SEQ  FROM  GIBU.TBRA_UPSO_AUTO_APPLICATION  A  WHERE  1=1   \n";
        query +=" AND  A.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  A.APPLICATION_GBN  IN  ('12','16')   \n";
        query +=" AND  A.RESINUM  =  :RESINUM   \n";
        query +=" AND  A.UPSO_CD  =   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  CLIENT_NUM  =  :CLIENT_NUM) ";
        sobj.setSql(query);
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("CLIENT_NUM", CLIENT_NUM);               //고객 번호
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    // 응답등록
    public DOBJ CALLupload_file_INS43(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLupload_file_INS43(dobj, dvobj);
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
    private SQLObject SQLupload_file_INS43(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //접수처 구분
        String   CARD_NUM = dvobj.getRecord().get("CARD_NUM");   //카드 번호
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //신청 구분
        String   SEQ_NUM = dvobj.getRecord().get("SEQ_NUM");   //일련 번호
        String   BIOWN_INSNUM = dvobj.getRecord().get("BIOWN_INSNUM");   //사업자 등록번호
        String   EXPIRE_DAY = dvobj.getRecord().get("EXPIRE_DAY");   //만기 일
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   APPTN_RSLT = dvobj.getRecord().get("APPTN_RSLT");   //신청 결과
        String   APPTN_DAY = dobj.getRetObject("R").getRecord().get("APPTN_DAY");   //신청 일자
        String   CARD_GBN = dobj.getRetObject("S1").getRecord().get("CARD_GBN");   //카드 구분
        String   CHGATR_PAYPRES_NUM = dobj.getRetObject("R").getRecord().get("CLIENT_NUM");   //변경후 납부자 번호
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYPRES_NM, EXPIRE_DAY, CHGATR_PAYPRES_NUM, PROC_DAY, BIOWN_INSNUM, SEQ_NUM, CARD_GBN, APPTN_DAY, UPSO_CD, APPTN_GBN, CARD_NUM, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYPRES_NM , :EXPIRE_DAY , :CHGATR_PAYPRES_NUM , :PROC_DAY , :BIOWN_INSNUM , :SEQ_NUM , :CARD_GBN , :APPTN_DAY , :UPSO_CD , :APPTN_GBN , :CARD_NUM , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //접수처 구분
        sobj.setString("CARD_NUM", CARD_NUM);               //카드 번호
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
        sobj.setString("BIOWN_INSNUM", BIOWN_INSNUM);               //사업자 등록번호
        sobj.setString("EXPIRE_DAY", EXPIRE_DAY);               //만기 일
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //신청 결과
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //변경후 납부자 번호
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 정상등록
    public DOBJ CALLupload_file_INS44(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS44");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_file_INS44(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS44") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_INS44(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int  AUTO_NUM = 0;        //일련 번호
        String INS_DATE ="";        //등록 일시
        String   CARD_NUM = dvobj.getRecord().get("CARD_NUM");   //카드 번호
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
        String   BIOWN_INSNUM = dvobj.getRecord().get("BIOWN_INSNUM");   //사업자 등록번호
        String   AUTO_NUM_1 = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //일련 번호
        String   EXPIRE_DAY = dvobj.getRecord().get("EXPIRE_DAY");   //만기 일
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   CARD_GBN = dobj.getRetObject("S1").getRecord().get("CARD_GBN");   //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   PROC_GBN ="Y";   //자동 처리 구분
        String   RECEPTION_GBN ="7";   //접수처
        String   TERM_YN ="N";   //해지 여부
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO (INSPRES_ID, PAYPRES_NM, TERM_YN, EXPIRE_DAY, AUTO_NUM, PROC_GBN, BIOWN_INSNUM, INS_DATE, CARD_GBN, APPTN_DAY, UPSO_CD, RESINUM, RECEPTION_GBN, CARD_NUM)  \n";
        query +=" values(:INSPRES_ID , :PAYPRES_NM , :TERM_YN , :EXPIRE_DAY , (SELECT NVL(MAX(AUTO_NUM), 0) + 1 AUTO_NUM FROM GIBU.TBRA_UPSO_AUTO_TEST2 WHERE UPSO_CD = :AUTO_NUM_1), :PROC_GBN , :BIOWN_INSNUM , SYSDATE, :CARD_GBN , :APPTN_DAY , :UPSO_CD , :RESINUM , :RECEPTION_GBN , :CARD_NUM )";
        sobj.setSql(query);
        sobj.setString("CARD_NUM", CARD_NUM);               //카드 번호
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("BIOWN_INSNUM", BIOWN_INSNUM);               //사업자 등록번호
        sobj.setString("AUTO_NUM_1", AUTO_NUM_1);               //일련 번호
        sobj.setString("EXPIRE_DAY", EXPIRE_DAY);               //만기 일
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("PROC_GBN", PROC_GBN);               //자동 처리 구분
        sobj.setString("RECEPTION_GBN", RECEPTION_GBN);               //접수처
        sobj.setString("TERM_YN", TERM_YN);               //해지 여부
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 신청서에 결과 수정
    public DOBJ CALLupload_file_UPD45(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD45");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_file_UPD45(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD45") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_UPD45(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REMAK ="00:정상처리";   //비고
        double   SEQ = dobj.getRetObject("SEL3").getRecord().getDouble("SEQ");   //관리번호
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" set REMAK=:REMAK  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 신청서에 결과 수정
    public DOBJ CALLupload_file_UPD16(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLupload_file_UPD16(dobj, dvobj);
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
    private SQLObject SQLupload_file_UPD16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REMAK ="01";   //비고
        double   SEQ = dobj.getRetObject("SEL3").getRecord().getDouble("SEQ");   //관리번호
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" set REMAK=:REMAK  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 신청서에 결과 수정
    public DOBJ CALLupload_file_UPD14(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLupload_file_UPD14(dobj, dvobj);
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
    private SQLObject SQLupload_file_UPD14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY ="";   //처리 일자
        String   REMAK ="02:재발송";   //비고
        double   SEQ = dobj.getRetObject("SEL3").getRecord().getDouble("SEQ");   //관리번호
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" set PROC_DAY=:PROC_DAY , REMAK=:REMAK  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 응답등록
    public DOBJ CALLupload_file_INS13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS13");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS13");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupload_file_INS13(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS13") ;
        rvobj.Println("INS13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_INS13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //접수처 구분
        String   CARD_NUM = dvobj.getRecord().get("CARD_NUM");   //카드 번호
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //신청 구분
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
        String   SEQ_NUM = dvobj.getRecord().get("SEQ_NUM");   //일련 번호
        String   BIOWN_INSNUM = dvobj.getRecord().get("BIOWN_INSNUM");   //사업자 등록번호
        String   EXPIRE_DAY = dvobj.getRecord().get("EXPIRE_DAY");   //만기 일
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   APPTN_RSLT = dvobj.getRecord().get("APPTN_RSLT");   //신청 결과
        String   CARD_GBN = dobj.getRetObject("S1").getRecord().get("CARD_GBN");   //카드 구분
        String   CHGATR_PAYPRES_NUM = dobj.getRetObject("R").getRecord().get("CLIENT_NUM");   //변경후 납부자 번호
        String   ERR_GBN ="99";   //에러 구분
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        String   UPSO_CD ="1";   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYPRES_NM, EXPIRE_DAY, CHGATR_PAYPRES_NUM, PROC_DAY, BIOWN_INSNUM, SEQ_NUM, CARD_GBN, APPTN_DAY, UPSO_CD, APPTN_GBN, ERR_GBN, CARD_NUM, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYPRES_NM , :EXPIRE_DAY , :CHGATR_PAYPRES_NUM , :PROC_DAY , :BIOWN_INSNUM , :SEQ_NUM , :CARD_GBN , :APPTN_DAY , :UPSO_CD , :APPTN_GBN , :ERR_GBN , :CARD_NUM , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //접수처 구분
        sobj.setString("CARD_NUM", CARD_NUM);               //카드 번호
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
        sobj.setString("BIOWN_INSNUM", BIOWN_INSNUM);               //사업자 등록번호
        sobj.setString("EXPIRE_DAY", EXPIRE_DAY);               //만기 일
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //신청 결과
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //변경후 납부자 번호
        sobj.setString("ERR_GBN", ERR_GBN);               //에러 구분
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 전체결과
    public DOBJ CALLupload_file_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupload_file_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CARD_GBN = dobj.getRetObject("S1").getRecord().get("CARD_GBN");   //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.PROC_DAY  ,  A.SEQ_NUM  ,  A.APPTN_GBN  ,  A.APPTN_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.EXPIRE_DAY  ,  A.CARD_NUM  ,  A.PAYPRES_NM  ,  A.PAYER_PHONNUM  ,  A.RESINUM  ,  A.BIOWN_INSNUM  ,  A.APPTN_RSLT  ,  A.CARD_GBN  ,  A.RECPT_GBN_CD  ,  A.CHGATR_PAYPRES_NUM  AS  CLIENT_NUM  FROM  GIBU.TBRA_UPSO_AUTORSLT  A  ,  GIBU.TBRA_UPSO  B  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  A.CARD_GBN  =  :CARD_GBN   \n";
        query +=" AND  A.RECPT_GBN_CD  =  '7'   \n";
        query +=" AND  A.APPTN_RSLT  =  '00' ";
        sobj.setSql(query);
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    // 에러결과결과
    public DOBJ CALLupload_file_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupload_file_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_file_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CARD_GBN = dobj.getRetObject("S1").getRecord().get("CARD_GBN");   //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        String   PROC_DAY = dobj.getRetObject("S1").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.PROC_DAY  ,  A.SEQ_NUM  ,  A.APPTN_GBN  ,  A.APPTN_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.EXPIRE_DAY  ,  A.CARD_NUM  ,  A.PAYPRES_NM  ,  A.PAYER_PHONNUM  ,  A.RESINUM  ,  A.BIOWN_INSNUM  ,  A.APPTN_RSLT  ,  A.CARD_GBN  ,  A.RECPT_GBN_CD  ,  A.CHGATR_PAYPRES_NUM  AS  CLIENT_NUM  FROM  GIBU.TBRA_UPSO_AUTORSLT  A  ,  GIBU.TBRA_UPSO  B  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  A.CARD_GBN  =  :CARD_GBN   \n";
        query +=" AND  A.RECPT_GBN_CD  =  '7'   \n";
        query +=" AND  A.APPTN_RSLT  <>  '00' ";
        sobj.setSql(query);
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    //##**$$upload_file
    //##**$$upload_result
    /*
    */
    public DOBJ CTLupload_result(DOBJ dobj)
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
            dobj  = CALLupload_result_SEL1(Conn, dobj);           //  전체결과
            dobj  = CALLupload_result_SEL2(Conn, dobj);           //  에러결과
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
    public DOBJ CTLupload_result( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupload_result_SEL1(Conn, dobj);           //  전체결과
        dobj  = CALLupload_result_SEL2(Conn, dobj);           //  에러결과
        return dobj;
    }
    // 전체결과
    public DOBJ CALLupload_result_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupload_result_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_result_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CARD_GBN = dobj.getRetObject("S").getRecord().get("CARD_GBN");   //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.PROC_DAY  ,  A.SEQ_NUM  ,  A.APPTN_GBN  ,  A.APPTN_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.EXPIRE_DAY  ,  A.CARD_NUM  ,  A.PAYPRES_NM  ,  A.PAYER_PHONNUM  ,  A.RESINUM  ,  A.BIOWN_INSNUM  ,  A.APPTN_RSLT  ,  A.CARD_GBN  ,  A.RECPT_GBN_CD  ,  A.CHGATR_PAYPRES_NUM  AS  CLIENT_NUM  FROM  GIBU.TBRA_UPSO_AUTORSLT  A  ,  GIBU.TBRA_UPSO  B  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  A.CARD_GBN  =  :CARD_GBN   \n";
        query +=" AND  A.RECPT_GBN_CD  =  '7'   \n";
        query +=" AND  A.APPTN_RSLT  =  '00' ";
        sobj.setSql(query);
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    // 에러결과
    public DOBJ CALLupload_result_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupload_result_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupload_result_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CARD_GBN = dobj.getRetObject("S").getRecord().get("CARD_GBN");   //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.PROC_DAY  ,  A.SEQ_NUM  ,  A.APPTN_GBN  ,  A.APPTN_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.EXPIRE_DAY  ,  A.CARD_NUM  ,  A.PAYPRES_NM  ,  A.PAYER_PHONNUM  ,  A.RESINUM  ,  A.BIOWN_INSNUM  ,  A.APPTN_RSLT  ,  A.CARD_GBN  ,  A.RECPT_GBN_CD  ,  A.CHGATR_PAYPRES_NUM  AS  CLIENT_NUM  FROM  GIBU.TBRA_UPSO_AUTORSLT  A  ,  GIBU.TBRA_UPSO  B  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD(+)   \n";
        query +=" AND  A.PROC_DAY  =  :PROC_DAY  --AND  A.CARD_GBN  =  :CARD_GBN   \n";
        query +=" AND  A.RECPT_GBN_CD  =  '7'   \n";
        query +=" AND  APPTN_RSLT  <>  '00' ";
        sobj.setSql(query);
        sobj.setString("CARD_GBN", CARD_GBN);               //카드 구분(TENV_CODE TABLE의 HIGH_CD = '00112')
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    //##**$$upload_result
    //##**$$end
}
