
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s05_4
{
    public bra03_s05_4()
    {
    }
    //##**$$auto_apptn_save_temp
    /*
    */
    public DOBJ CTLauto_apptn_save_temp(DOBJ dobj)
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
            dobj  = CALLauto_apptn_save_temp_XIUD2(Conn, dobj);           //  테이블TRUNCATE
            dobj  = CALLauto_apptn_save_temp_INS3(Conn, dobj);           //  82파일정보 저장
            dobj  = CALLauto_apptn_save_temp_XIUD4(Conn, dobj);           //  커밋
            dobj  = CALLauto_apptn_save_temp_SEL1(Conn, dobj);           //  GE12파일생성정보조회
            dobj  = CALLauto_apptn_save_temp_MPD6(Conn, dobj);           //  첨부파일생성분기
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
    public DOBJ CTLauto_apptn_save_temp( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_apptn_save_temp_XIUD2(Conn, dobj);           //  테이블TRUNCATE
        dobj  = CALLauto_apptn_save_temp_INS3(Conn, dobj);           //  82파일정보 저장
        dobj  = CALLauto_apptn_save_temp_XIUD4(Conn, dobj);           //  커밋
        dobj  = CALLauto_apptn_save_temp_SEL1(Conn, dobj);           //  GE12파일생성정보조회
        dobj  = CALLauto_apptn_save_temp_MPD6(Conn, dobj);           //  첨부파일생성분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 테이블TRUNCATE
    public DOBJ CALLauto_apptn_save_temp_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_save_temp_XIUD2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_save_temp_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" TRUNCATE  TABLE  GIBU.TBRA_UPSO_AUTO_82TEMP ";
        sobj.setSql(query);
        return sobj;
    }
    // 82파일정보 저장
    public DOBJ CALLauto_apptn_save_temp_INS3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_save_temp_INS3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS3") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_save_temp_INS3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RECPT_GBN_CD = dvobj.getRecord().get("RECPT_GBN_CD");   //접수처 구분
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //신청 구분
        String   RESINUM = dvobj.getRecord().get("RESINUM");   //주민번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   RECPTNUM = dvobj.getRecord().get("RECPTNUM");   //접수번호
        String   PAY_BANK_CD = dvobj.getRecord().get("PAY_BANK_CD");   //납부 은행 코드
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
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
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTO_82TEMP (APPTN_RSLT, PAYER_PHONNUM, PAYPRES_NM, RECPT_BANK_CD, CHGATR_PAYPRES_NUM, PAY_ACCN_NUM, BEGNG_RELSDAY, SEQ_NUM, CHGBFR_PAYPRES_NUM, PAY_REQDAY, PAY_KND, APPTN_DAY, PAY_BANK_CD, RECPTNUM, UPSO_CD, RESINUM, APPTN_GBN, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYER_PHONNUM , :PAYPRES_NM , :RECPT_BANK_CD , :CHGATR_PAYPRES_NUM , :PAY_ACCN_NUM , :BEGNG_RELSDAY , :SEQ_NUM , :CHGBFR_PAYPRES_NUM , :PAY_REQDAY , :PAY_KND , :APPTN_DAY , :PAY_BANK_CD , :RECPTNUM , :UPSO_CD , :RESINUM , :APPTN_GBN , :RECPT_GBN_CD )";
        sobj.setSql(query);
        sobj.setString("RECPT_GBN_CD", RECPT_GBN_CD);               //접수처 구분
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        sobj.setString("RESINUM", RESINUM);               //주민번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("RECPTNUM", RECPTNUM);               //접수번호
        sobj.setString("PAY_BANK_CD", PAY_BANK_CD);               //납부 은행 코드
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
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
        return sobj;
    }
    // 커밋
    public DOBJ CALLauto_apptn_save_temp_XIUD4(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLauto_apptn_save_temp_XIUD4(dobj, dvobj);
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
    private SQLObject SQLauto_apptn_save_temp_XIUD4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" COMMIT ";
        sobj.setSql(query);
        return sobj;
    }
    // GE12파일생성정보조회
    public DOBJ CALLauto_apptn_save_temp_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_apptn_save_temp_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_save_temp_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(SEQ_NUM,  8,  '0')  AS  SEQ_NUM  ,  CHGATR_PAYPRES_NUM  ,  SUBSTR(PAY_BANK_CD,  0,  3)  AS  BANK_CD  ,  PAY_ACCN_NUM  ,  APPTN_DAY  ,  SUBSTR(FILE_TEMPNM,  INSTR(FILE_TEMPNM,  '.',  LENGTH(FILE_TEMPNM)  -  5)  +  1)  AS  FILE_EXTENTION  ,  FILE_ROUT  ,  FILE_NM  ,  SEQ  ,  A.UPSO_CD  AS  UPSO_CD  FROM  GIBU.TBRA_UPSO_AUTO_82TEMP  A  ,  GIBU.TBRA_UPSO_AUTO_DOC_ATTCH  B  WHERE  A.UPSO_CD  =  B.UPSO_CD(+)   \n";
        query +=" AND  A.APPTN_GBN  =  '02'   \n";
        query +=" AND  B.SEQ  =   \n";
        query +=" (SELECT  mAX(SEQ)  FROM  GIBU.TBRA_UPSO_AUTO_DOC_ATTCH  WHERE  UPSO_CD  =  A.UPSO_CD)  ORDER  BY  SEQ_NUM  ASC ";
        sobj.setSql(query);
        return sobj;
    }
    // 첨부파일생성분기
    public DOBJ CALLauto_apptn_save_temp_MPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD6");
        VOBJ dvobj = dobj.getRetObject("SEL1");         //GE12파일생성정보조회에서 생성시킨 OBJECT입니다.(CALLauto_apptn_save_temp_SEL1)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( 1 == 1)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_apptn_save_temp_SEL7(Conn, dobj);           //  첨부파일생성
            }
        }
        return dobj;
    }
    // 첨부파일생성
    public DOBJ CALLauto_apptn_save_temp_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_apptn_save_temp_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        String fullname="";
        rvobj.first();
        while(rvobj.next())
        {
            wutil.makeFileOverwirte( dobj.getRetObject("R").getRecord().get("FILE_ROUT"), dobj.getRetObject("R").getRecord().get("FILE_NM"),rvobj.getRecord().getBytes("FILES"));
        }
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_save_temp_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  FILE_ROUT  ,  FILE_NM  ,  FILES  FROM  GIBU.TBRA_UPSO_AUTO_DOC_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("SEQ", SEQ);               //관리번호
        return sobj;
    }
    //##**$$auto_apptn_save_temp
    //##**$$end
}
