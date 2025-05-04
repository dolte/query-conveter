
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s05_2
{
    public bra03_s05_2()
    {
    }
    //##**$$auto_apptn_upload2
    /*
    */
    public DOBJ CTLauto_apptn_upload2(DOBJ dobj)
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
            dobj  = CALLauto_apptn_upload2_XIUD21(Conn, dobj);           //  은행접수신청서삭제
            dobj  = CALLauto_apptn_upload2_XIUD16(Conn, dobj);           //  은행접수신청서삭제
            dobj  = CALLauto_apptn_upload2_INS21(Conn, dobj);           //  이력먼저 등록
            dobj  = CALLauto_apptn_upload2_XIUD32(Conn, dobj);           //  커밋
            dobj  = CALLauto_apptn_upload2_MPD2(Conn, dobj);           //  건별처리
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
    public DOBJ CTLauto_apptn_upload2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_apptn_upload2_XIUD21(Conn, dobj);           //  은행접수신청서삭제
        dobj  = CALLauto_apptn_upload2_XIUD16(Conn, dobj);           //  은행접수신청서삭제
        dobj  = CALLauto_apptn_upload2_INS21(Conn, dobj);           //  이력먼저 등록
        dobj  = CALLauto_apptn_upload2_XIUD32(Conn, dobj);           //  커밋
        dobj  = CALLauto_apptn_upload2_MPD2(Conn, dobj);           //  건별처리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 은행접수신청서삭제
    // 처리일자에 해당하는 은행접수신청서를 삭제한다.
    public DOBJ CALLauto_apptn_upload2_XIUD21(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLauto_apptn_upload2_XIUD21(dobj, dvobj);
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
    private SQLObject SQLauto_apptn_upload2_XIUD21(DOBJ dobj, VOBJ dvobj) throws Exception
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
    public DOBJ CALLauto_apptn_upload2_XIUD16(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLauto_apptn_upload2_XIUD16(dobj, dvobj);
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
    private SQLObject SQLauto_apptn_upload2_XIUD16(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // 이력먼저 등록
    public DOBJ CALLauto_apptn_upload2_INS21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS21");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("INS21");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_upload2_INS21(dobj, dvobj);
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
    private SQLObject SQLauto_apptn_upload2_INS21(DOBJ dobj, VOBJ dvobj) throws Exception
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
        String   PROC_DAY = dvobj.getRecord().get("PROC_DAY");   //처리 일자
        String   PAY_ACCN_NUM = dvobj.getRecord().get("PAY_ACCN_NUM");   //출금 계좌 번호
        String   CHGATR_PAYPRES_NUM = dvobj.getRecord().get("CHGATR_PAYPRES_NUM");   //변경후 납부자 번호
        String   RECPT_BANK_CD = dvobj.getRecord().get("RECPT_BANK_CD");   //접수점 코드
        String   PAYPRES_NM = dvobj.getRecord().get("PAYPRES_NM");   //납부자 명
        String   PAYER_PHONNUM = dvobj.getRecord().get("PAYER_PHONNUM");   //납부자 전화번호
        String   APPTN_RSLT = dvobj.getRecord().get("APPTN_RSLT");   //신청 결과
        String   ERR_GBN ="";   //에러 구분
        String   GBN ="62";   //구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_AUTORSLT (APPTN_RSLT, PAYER_PHONNUM, GBN, PAYPRES_NM, RECPT_BANK_CD, CHGATR_PAYPRES_NUM, PAY_ACCN_NUM, PROC_DAY, BEGNG_RELSDAY, SEQ_NUM, CHGBFR_PAYPRES_NUM, PAY_REQDAY, PAY_KND, APPTN_DAY, PAY_BANK_CD, RECPTNUM, UPSO_CD, RESINUM, ERR_GBN, APPTN_GBN, RECPT_GBN_CD)  \n";
        query +=" values(:APPTN_RSLT , :PAYER_PHONNUM , :GBN , :PAYPRES_NM , :RECPT_BANK_CD , :CHGATR_PAYPRES_NUM , :PAY_ACCN_NUM , :PROC_DAY , :BEGNG_RELSDAY , :SEQ_NUM , :CHGBFR_PAYPRES_NUM , :PAY_REQDAY , :PAY_KND , :APPTN_DAY , :PAY_BANK_CD , :RECPTNUM , :UPSO_CD , :RESINUM , :ERR_GBN , :APPTN_GBN , :RECPT_GBN_CD )";
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
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("PAY_ACCN_NUM", PAY_ACCN_NUM);               //출금 계좌 번호
        sobj.setString("CHGATR_PAYPRES_NUM", CHGATR_PAYPRES_NUM);               //변경후 납부자 번호
        sobj.setString("RECPT_BANK_CD", RECPT_BANK_CD);               //접수점 코드
        sobj.setString("PAYPRES_NM", PAYPRES_NM);               //납부자 명
        sobj.setString("PAYER_PHONNUM", PAYER_PHONNUM);               //납부자 전화번호
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //신청 결과
        sobj.setString("ERR_GBN", ERR_GBN);               //에러 구분
        sobj.setString("GBN", GBN);               //구분
        return sobj;
    }
    // 커밋
    public DOBJ CALLauto_apptn_upload2_XIUD32(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD32");
        VOBJ dvobj = dobj.getRetObject("INS21");            //이력먼저 등록에서 생성시킨 OBJECT입니다.(CALLauto_apptn_upload2_INS21)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_upload2_XIUD32(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD32");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload2_XIUD32(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" COMMIT ";
        sobj.setSql(query);
        return sobj;
    }
    // 건별처리
    public DOBJ CALLauto_apptn_upload2_MPD2(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLauto_apptn_upload2_SEL3(Conn, dobj);           //  업소체크
                if(!dobj.getRetObject("SEL3").getRecord().get("UPSO_CD").equals("") && !dobj.getRetObject("R").getRecord().get("APPTN_GBN").equals("11"))
                {
                    dobj  = CALLauto_apptn_upload2_UPD58(Conn, dobj);           //  업소조회에러
                    dobj  = CALLauto_apptn_upload2_OSP50(Conn, dobj);           //  자동이체업소로 등록
                }
            }
        }
        return dobj;
    }
    // 업소체크
    public DOBJ CALLauto_apptn_upload2_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_apptn_upload2_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload2_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
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
    public DOBJ CALLauto_apptn_upload2_UPD58(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD58");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_apptn_upload2_UPD58(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD58") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_apptn_upload2_UPD58(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SEQ_NUM = dvobj.getRecord().get("SEQ_NUM");   //일련 번호
        String   PROC_DAY = dvobj.getRecord().get("PROC_DAY");   //처리 일자
        String   APPTN_RSLT ="03";   //신청 결과
        String   ERR_GBN ="0002";   //에러 구분
        String   UPSO_CD = dobj.getRetObject("SEL3").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update  \n";
        query +=" GIBU.TBRA_UPSO_AUTORSLT  \n";
        query +=" set APPTN_RSLT=:APPTN_RSLT , UPSO_CD=:UPSO_CD , ERR_GBN=:ERR_GBN  \n";
        query +=" where PROC_DAY=:PROC_DAY  \n";
        query +=" and SEQ_NUM=:SEQ_NUM";
        sobj.setSql(query);
        sobj.setString("SEQ_NUM", SEQ_NUM);               //일련 번호
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        sobj.setString("APPTN_RSLT", APPTN_RSLT);               //신청 결과
        sobj.setString("ERR_GBN", ERR_GBN);               //에러 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 자동이체업소로 등록
    public DOBJ CALLauto_apptn_upload2_OSP50(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP50");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("OSP50");
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
        robj.setName("OSP50");
        robj.Println("OSP50");
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$auto_apptn_upload2
    //##**$$end
}
