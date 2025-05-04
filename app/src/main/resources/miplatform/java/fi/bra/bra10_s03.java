
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s03
{
    public bra10_s03()
    {
    }
    //##**$$staff_zone_chg
    /* * 프로그램명 : bra10_s03
    * 작성자 : 서정재
    * 작성일 : 2009/11/02
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLstaff_zone_chg(DOBJ dobj)
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
            dobj  = CALLstaff_zone_chg_SEL10(Conn, dobj);           //  CHG_NUM생성
            dobj  = CALLstaff_zone_chg_INS6(Conn, dobj);           //  담당구역변경
            if( dobj.getRetObject("S").getRecord().get("REMAK").equals("전체"))
            {
                dobj  = CALLstaff_zone_chg_XIUD8(Conn, dobj);           //  업소사원코드변경
                dobj  = CALLstaff_zone_chg_XIUD12(Conn, dobj);           //  코스지 저장문구 지부변경
            }
            else
            {
                dobj  = CALLstaff_zone_chg_MPD11(Conn, dobj);           //  동명용
                if(dobj.getRtncode() == 9)
                {
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
    public DOBJ CTLstaff_zone_chg( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLstaff_zone_chg_SEL10(Conn, dobj);           //  CHG_NUM생성
        dobj  = CALLstaff_zone_chg_INS6(Conn, dobj);           //  담당구역변경
        if( dobj.getRetObject("S").getRecord().get("REMAK").equals("전체"))
        {
            dobj  = CALLstaff_zone_chg_XIUD8(Conn, dobj);           //  업소사원코드변경
            dobj  = CALLstaff_zone_chg_XIUD12(Conn, dobj);           //  코스지 저장문구 지부변경
        }
        else
        {
            dobj  = CALLstaff_zone_chg_MPD11(Conn, dobj);           //  동명용
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // CHG_NUM생성
    public DOBJ CALLstaff_zone_chg_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_zone_chg_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_zone_chg_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_BRAN = dobj.getRetObject("S").getRecord().get("CHG_BRAN");   //변경 지부
        String   CHG_DAY = dobj.getRetObject("S").getRecord().get("CHG_DAY");   //변경 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(CHG_NUM),  0)  +  1,  4,  '0')  CHG_NUM  FROM  GIBU.TBRA_STAFF_ZONECHG  WHERE  CHG_BRAN  =  :CHG_BRAN   \n";
        query +=" AND  CHG_DAY  =  :CHG_DAY ";
        sobj.setSql(query);
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        return sobj;
    }
    // 담당구역변경
    public DOBJ CALLstaff_zone_chg_INS6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("INS6");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_zone_chg_INS6(dobj, dvobj);
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
    private SQLObject SQLstaff_zone_chg_INS6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   MNG_ZIP = dvobj.getRecord().get("MNG_ZIP");   //관리 우편번호
        String   ZIP_END = dvobj.getRecord().get("ZIP_END");   //우편번호 종료
        String   ZIP_START = dvobj.getRecord().get("ZIP_START");   //우편번호 시작
        String   DSRCCNTY = dvobj.getRecord().get("DSRCCNTY");   //구군
        String   CHG_DAY = dvobj.getRecord().get("CHG_DAY");   //변경 일자
        String   CHG_BRAN = dvobj.getRecord().get("CHG_BRAN");   //변경 지부
        String   ATTE = dvobj.getRecord().get("ATTE");   //시도
        String   CHG_NUM = dobj.getRetObject("SEL10").getRecord().get("CHG_NUM");   //변경 번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_STAFF_ZONECHG (ATTE, CHG_BRAN, CHG_DAY, INSPRES_ID, DSRCCNTY, ZIP_START, ZIP_END, INS_DATE, MNG_ZIP, CHG_NUM, STAFF_CD, BRAN_CD, REMAK)  \n";
        query +=" values(:ATTE , :CHG_BRAN , :CHG_DAY , :INSPRES_ID , :DSRCCNTY , :ZIP_START , :ZIP_END , SYSDATE, :MNG_ZIP , :CHG_NUM , :STAFF_CD , :BRAN_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("ZIP_END", ZIP_END);               //우편번호 종료
        sobj.setString("ZIP_START", ZIP_START);               //우편번호 시작
        sobj.setString("DSRCCNTY", DSRCCNTY);               //구군
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        sobj.setString("ATTE", ATTE);               //시도
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 업소사원코드변경
    public DOBJ CALLstaff_zone_chg_XIUD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD8");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_zone_chg_XIUD8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD8");
        rvobj.Println("XIUD8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_zone_chg_XIUD8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET STAFF_CD = :STAFF_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD = :BRAN_CD  \n";
        query +=" AND UPSO_CD IN( SELECT UPSO_CD FROM FIDU.TENV_POST X , GIBU.TBRA_UPSO Y  \n";
        query +=" WHERE X.BD_MNG_NUM = Y.UPSO_BD_MNG_NUM  \n";
        query +=" AND Y.BRAN_CD = :BRAN_CD  \n";
        query +=" AND SUBSTR(X.JUSO_CD,1,5) = :MNG_ZIP)";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    // 코스지 저장문구 지부변경
    public DOBJ CALLstaff_zone_chg_XIUD12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD12");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_zone_chg_XIUD12(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_zone_chg_XIUD12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TMOB_CONTENT_LIST  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE STAFF_CD = :STAFF_CD  \n";
        query +=" AND BRAN_CD <> :BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    // 동명용
    public DOBJ CALLstaff_zone_chg_MPD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD11");
        VOBJ dvobj = dobj.getRetObject("S1");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLstaff_zone_chg_INS7(Conn, dobj);           //  동지정
                dobj  = CALLstaff_zone_chg_XIUD14(Conn, dobj);           //  업소사원코드변경
                dobj  = CALLstaff_zone_chg_XIUD15(Conn, dobj);           //  커밋
                dobj  = CALLstaff_zone_chg_XIUD13(Conn, dobj);           //  코스지 저장문구 지부변경
            }
        }
        return dobj;
    }
    // 동지정
    public DOBJ CALLstaff_zone_chg_INS7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS7");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_zone_chg_INS7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS7") ;
        rvobj.Println("INS7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_zone_chg_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        double SEQ = 0;        //관리번호
        String   SEQ_3 = dobj.getRetObject("SEL10").getRecord().get("CHG_NUM");   //관리번호
        String   SEQ_2 = dobj.getRetObject("S").getRecord().get("CHG_DAY");   //관리번호
        String   SEQ_1 = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //관리번호
        String   DONG = dvobj.getRecord().get("DONG");   //동
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   CHG_DAY = dobj.getRetObject("S").getRecord().get("CHG_DAY");   //변경 일자
        String   CHG_NUM = dobj.getRetObject("SEL10").getRecord().get("CHG_NUM");   //변경 번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_STAFF_ZONECHG_DONG (INS_DATE, INSPRES_ID, DONG, CHG_NUM, CHG_DAY, SEQ, BRAN_CD)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :DONG , :CHG_NUM , :CHG_DAY , (SELECT LPAD(NVL(MAX(SEQ), 0) + 1, 4, '0') SEQ FROM GIBU.TBRA_STAFF_ZONECHG_DONG WHERE BRAN_CD = :SEQ_1 AND CHG_DAY = :SEQ_2 AND CHG_NUM = :SEQ_3 ), :BRAN_CD )";
        sobj.setSql(query);
        sobj.setString("SEQ_3", SEQ_3);               //관리번호
        sobj.setString("SEQ_2", SEQ_2);               //관리번호
        sobj.setString("SEQ_1", SEQ_1);               //관리번호
        sobj.setString("DONG", DONG);               //동
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 업소사원코드변경
    public DOBJ CALLstaff_zone_chg_XIUD14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD14");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_zone_chg_XIUD14(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD14");
        rvobj.Println("XIUD14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_zone_chg_XIUD14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //시도
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   DONG = dobj.getRetObject("R").getRecord().get("DONG");   //동
        String   DSRCCNTY = dobj.getRetObject("S").getRecord().get("DSRCCNTY");   //구군
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET STAFF_CD = :STAFF_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BRAN_CD = :BRAN_CD  \n";
        query +=" AND UPSO_CD IN (SELECT A.UPSO_CD FROM GIBU.TBRA_UPSO A , FIDU.TENV_POST B  \n";
        query +=" WHERE A.BRAN_CD = :BRAN_CD  \n";
        query +=" AND A.UPSO_BD_MNG_NUM = B.BD_MNG_NUM  \n";
        query +=" AND B.ATTE = :ATTE  \n";
        query +=" AND B.CITYCNTYDSRC = :DSRCCNTY  \n";
        query +=" AND :DONG = DECODE(COURT_NM, NULL, TOWNTWSHP, COURT_NM))";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //시도
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("DONG", DONG);               //동
        sobj.setString("DSRCCNTY", DSRCCNTY);               //구군
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    // 커밋
    public DOBJ CALLstaff_zone_chg_XIUD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD15");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_zone_chg_XIUD15(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD15");
        rvobj.Println("XIUD15");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_zone_chg_XIUD15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" COMMIT ";
        sobj.setSql(query);
        return sobj;
    }
    // 코스지 저장문구 지부변경
    public DOBJ CALLstaff_zone_chg_XIUD13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD13");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_zone_chg_XIUD13(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_zone_chg_XIUD13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TMOB_CONTENT_LIST  \n";
        query +=" SET BRAN_CD = :BRAN_CD  \n";
        query +=" WHERE STAFF_CD = :STAFF_CD  \n";
        query +=" AND BRAN_CD <> :BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    //##**$$staff_zone_chg
    //##**$$list_selectedDong
    /*
    */
    public DOBJ CTLlist_selectedDong(DOBJ dobj)
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
            dobj  = CALLlist_selectedDong_SEL1(Conn, dobj);           //  선택한 동목록 조회
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
    public DOBJ CTLlist_selectedDong( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlist_selectedDong_SEL1(Conn, dobj);           //  선택한 동목록 조회
        return dobj;
    }
    // 선택한 동목록 조회
    public DOBJ CALLlist_selectedDong_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLlist_selectedDong_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlist_selectedDong_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_NUM = dobj.getRetObject("S").getRecord().get("CHG_NUM");   //변경 번호
        String   CHG_DAY = dobj.getRetObject("S").getRecord().get("CHG_DAY");   //변경 일자
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DONG  FROM  GIBU.TBRA_STAFF_ZONECHG_dong  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  CHG_DAY  =  :CHG_DAY   \n";
        query +=" AND  CHG_NUM  =  :CHG_NUM  ORDER  BY  DONG ";
        sobj.setSql(query);
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$list_selectedDong
    //##**$$zone_search
    /* * 프로그램명 : bra10_s03
    * 작성자 : 서정재
    * 작성일 : 2009/08/12
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLzone_search(DOBJ dobj)
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
            if(!dobj.getRetObject("S").getRecord().get("ATTE").equals("") && !dobj.getRetObject("S").getRecord().get("DSRCCNTY").equals(""))
            {
                dobj  = CALLzone_search_SEL1(Conn, dobj);           //  동 정보조회
            }
            else if(!dobj.getRetObject("S").getRecord().get("ATTE").equals(""))
            {
                dobj  = CALLzone_search_SEL5(Conn, dobj);           //  구군 정보 조회
            }
            else
            {
                dobj  = CALLzone_search_SEL6(Conn, dobj);           //  시도 정보 조회
            }
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
    public DOBJ CTLzone_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if(!dobj.getRetObject("S").getRecord().get("ATTE").equals("") && !dobj.getRetObject("S").getRecord().get("DSRCCNTY").equals(""))
        {
            dobj  = CALLzone_search_SEL1(Conn, dobj);           //  동 정보조회
        }
        else if(!dobj.getRetObject("S").getRecord().get("ATTE").equals(""))
        {
            dobj  = CALLzone_search_SEL5(Conn, dobj);           //  구군 정보 조회
        }
        else
        {
            dobj  = CALLzone_search_SEL6(Conn, dobj);           //  시도 정보 조회
        }
        return dobj;
    }
    // 동 정보조회
    public DOBJ CALLzone_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzone_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzone_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //시도
        String   DSRCCNTY = dobj.getRetObject("S").getRecord().get("DSRCCNTY");   //구군
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '전체'  DONG  ,  '000000'  ZIP  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  DONG  ,  ZIP  FROM  (   \n";
        query +=" SELECT  DONG  ||  HNM  DONG  ,  ZIP  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  ATTE  =  :ATTE   \n";
        query +=" AND  DSRCCNTY  =  :DSRCCNTY  ORDER  BY  DONG  ) ";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //시도
        sobj.setString("DSRCCNTY", DSRCCNTY);               //구군
        return sobj;
    }
    // 구군 정보 조회
    public DOBJ CALLzone_search_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzone_search_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.setRetcode(1);
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzone_search_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //시도
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  DSRCCNTY  ,  SUBSTR(ZIP,1,3)  ZIP  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  ATTE  =  :ATTE  ORDER  BY  DSRCCNTY ";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //시도
        return sobj;
    }
    // 시도 정보 조회
    public DOBJ CALLzone_search_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzone_search_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.setRetcode(1);
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzone_search_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  ATTE  FROM  GIBU.TBRA_BRANZIP_MNG  ORDER  BY  ATTE ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$zone_search
    //##**$$staff_zone_chglist
    /* * 프로그램명 : bra10_s03
    * 작성자 : 서정재
    * 작성일 : 2009/11/19
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLstaff_zone_chglist(DOBJ dobj)
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
            dobj  = CALLstaff_zone_chglist_SEL6(Conn, dobj);           //  월기준 구분용
            if( dobj.getRetObject("SEL6").getRecord().get("GBN").equals("0"))
            {
                dobj  = CALLstaff_zone_chglist_SEL01(Conn, dobj);           //  사원담당구역변경리스트
                dobj  = CALLstaff_zone_chglist_SEL1( dobj);        //  취합
            }
            else
            {
                dobj  = CALLstaff_zone_chglist_SEL02(Conn, dobj);           //  사원담당구역변경리스트
                dobj  = CALLstaff_zone_chglist_SEL1( dobj);        //  취합
            }
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
    public DOBJ CTLstaff_zone_chglist( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLstaff_zone_chglist_SEL6(Conn, dobj);           //  월기준 구분용
        if( dobj.getRetObject("SEL6").getRecord().get("GBN").equals("0"))
        {
            dobj  = CALLstaff_zone_chglist_SEL01(Conn, dobj);           //  사원담당구역변경리스트
            dobj  = CALLstaff_zone_chglist_SEL1( dobj);        //  취합
        }
        else
        {
            dobj  = CALLstaff_zone_chglist_SEL02(Conn, dobj);           //  사원담당구역변경리스트
            dobj  = CALLstaff_zone_chglist_SEL1( dobj);        //  취합
        }
        return dobj;
    }
    // 월기준 구분용
    public DOBJ CALLstaff_zone_chglist_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_zone_chglist_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_zone_chglist_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  (CASE  WHEN  :YRMN  >  '201312'  THEN  '1'  ELSE  '0'  END)  GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        return sobj;
    }
    // 사원담당구역변경리스트
    public DOBJ CALLstaff_zone_chglist_SEL01(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL01");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL01");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_zone_chglist_SEL01(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL01");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_zone_chglist_SEL01(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dvobj.getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.CHG_DAY  ,  XA.CHG_NUM  ,  XA.CHG_BRAN  ,  XA.STAFF_CD  ||  '  '  ||  XD.HAN_NM  STAFF  ,  XA.STAFF_CD  ,  XD.HAN_NM  STAFF_NM  ,  XA.ZIP_START  ||  '  ~  '  ||  XA.ZIP_END  ZIP  ,  XA.ZIP_START  ,  XA.ZIP_END  ,  XB.ALLADDR  ||  '  ('  ||  XB.ZIP  ||  ')'  ||  '  ~  '  ||  XC.ALLADDR  ||  '  ('  ||  XC.ZIP  ||  ')'  ADDR  ,  XB.ALLADDR  ADDR_START  ,  XC.  ALLADDR  ADDR_END  ,  XA.REMAK  ,  XA.ATTE  ,  XA.DSRCCNTY  ,  XA.MNG_ZIP  FROM  GIBU.TBRA_STAFF_ZONECHG  XA  ,  GIBU.TBRA_BRANZIP_MNG  XB  ,  GIBU.TBRA_BRANZIP_MNG  XC  ,  INSA.TINS_MST01  XD  ,  (   \n";
        query +=" SELECT  A.CHG_DAY  ,  A.CHG_NUM  ,  MIN(POST_SNUM)  MIN_SNUM  ,  MAX(POST_SNUM)  MAX_SNUM  FROM  GIBU.TBRA_STAFF_ZONECHG  A  ,  GIBU.TBRA_BRANZIP_MNG  B  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.CHG_DAY  BETWEEN  :YRMN  ||  '00'   \n";
        query +=" AND  :YRMN  ||  '32'   \n";
        query +=" AND  B.ZIP  BETWEEN  A.ZIP_START   \n";
        query +=" AND  A.ZIP_END  GROUP  BY  A.CHG_DAY,  A.CHG_NUM  )  XE  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.CHG_DAY  =  XE.CHG_DAY   \n";
        query +=" AND  XA.CHG_NUM  =  XE.CHG_NUM   \n";
        query +=" AND  XB.POST_SNUM  =  XE.MIN_SNUM   \n";
        query +=" AND  XC.POST_SNUM  =  XE.MAX_SNUM   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  ORDER  BY  XA.CHG_DAY,  XA.CHG_NUM ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 취합
    public DOBJ CALLstaff_zone_chglist_SEL1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("SEL1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL01, SEL02","");
        rvobj.setName("SEL1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 사원담당구역변경리스트
    public DOBJ CALLstaff_zone_chglist_SEL02(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL02");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL02");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_zone_chglist_SEL02(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL02");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_zone_chglist_SEL02(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dvobj.getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.CHG_DAY  ,  XA.CHG_NUM  ,  XA.CHG_BRAN  ,  XA.STAFF_CD  ||  '  '  ||  XD.HAN_NM  STAFF  ,  XA.STAFF_CD  ,  XD.HAN_NM  STAFF_NM  ,  ATTE  ||  '  '  ||  DSRCCNTY  ZIP  ,  ''  ZIP_START  ,  ''  ZIP_END  ,  XA.REMAK  ADDR  ,  ''  ADDR_START  ,  ''  ADDR_END  ,  XA.REMAK  ,  XA.ATTE  ,  XA.DSRCCNTY  ,  XA.MNG_ZIP  FROM  GIBU.TBRA_STAFF_ZONECHG  XA  ,  INSA.TINS_MST01  XD  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.CHG_DAY  BETWEEN  :YRMN  ||  '00'   \n";
        query +=" AND  :YRMN  ||  '32'   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD  ORDER  BY  XA.CHG_DAY,  XA.CHG_NUM ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$staff_zone_chglist
    //##**$$search_juso
    /*
    */
    public DOBJ CTLsearch_juso(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                if( dobj.getRetObject("S").getRecord().get("GBN2").equals("U"))
                {
                    dobj  = CALLsearch_juso_SEL21(Conn, dobj);           //  업소 미매칭
                    dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
                }
                else if( dobj.getRetObject("S").getRecord().get("GBN2").equals("M"))
                {
                    dobj  = CALLsearch_juso_SEL22(Conn, dobj);           //  경영주 미매칭
                    dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
                }
                else if( dobj.getRetObject("S").getRecord().get("GBN2").equals("P"))
                {
                    dobj  = CALLsearch_juso_SEL23(Conn, dobj);           //  허가주 미매칭
                    dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
                }
                else
                {
                    dobj  = CALLsearch_juso_SEL24(Conn, dobj);           //  미매칭 목록
                    dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
                }
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
            {
                if( dobj.getRetObject("S").getRecord().get("GBN2").equals("U"))
                {
                    dobj  = CALLsearch_juso_SEL11(Conn, dobj);           //  업소 매칭
                    dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
                }
                else if( dobj.getRetObject("S").getRecord().get("GBN2").equals("M"))
                {
                    dobj  = CALLsearch_juso_SEL12(Conn, dobj);           //  경영주 매칭
                    dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
                }
                else if( dobj.getRetObject("S").getRecord().get("GBN2").equals("P"))
                {
                    dobj  = CALLsearch_juso_SEL13(Conn, dobj);           //  허가주 매칭
                    dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
                }
                else
                {
                    dobj  = CALLsearch_juso_SEL14(Conn, dobj);           //  매칭 목록
                    dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
                }
            }
            else
            {
                if( dobj.getRetObject("S").getRecord().get("GBN2").equals("U"))
                {
                    dobj  = CALLsearch_juso_SEL31(Conn, dobj);           //  업소 전체
                    dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
                }
                else if( dobj.getRetObject("S").getRecord().get("GBN2").equals("M"))
                {
                    dobj  = CALLsearch_juso_SEL32(Conn, dobj);           //  경영주 전체
                    dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
                }
                else if( dobj.getRetObject("S").getRecord().get("GBN2").equals("P"))
                {
                    dobj  = CALLsearch_juso_SEL33(Conn, dobj);           //  허가주 전체
                    dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
                }
                else
                {
                    dobj  = CALLsearch_juso_SEL34(Conn, dobj);           //  전체 목록
                    dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
                }
            }
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
    public DOBJ CTLsearch_juso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            if( dobj.getRetObject("S").getRecord().get("GBN2").equals("U"))
            {
                dobj  = CALLsearch_juso_SEL21(Conn, dobj);           //  업소 미매칭
                dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN2").equals("M"))
            {
                dobj  = CALLsearch_juso_SEL22(Conn, dobj);           //  경영주 미매칭
                dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN2").equals("P"))
            {
                dobj  = CALLsearch_juso_SEL23(Conn, dobj);           //  허가주 미매칭
                dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
            }
            else
            {
                dobj  = CALLsearch_juso_SEL24(Conn, dobj);           //  미매칭 목록
                dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
            }
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
        {
            if( dobj.getRetObject("S").getRecord().get("GBN2").equals("U"))
            {
                dobj  = CALLsearch_juso_SEL11(Conn, dobj);           //  업소 매칭
                dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN2").equals("M"))
            {
                dobj  = CALLsearch_juso_SEL12(Conn, dobj);           //  경영주 매칭
                dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN2").equals("P"))
            {
                dobj  = CALLsearch_juso_SEL13(Conn, dobj);           //  허가주 매칭
                dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
            }
            else
            {
                dobj  = CALLsearch_juso_SEL14(Conn, dobj);           //  매칭 목록
                dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
            }
        }
        else
        {
            if( dobj.getRetObject("S").getRecord().get("GBN2").equals("U"))
            {
                dobj  = CALLsearch_juso_SEL31(Conn, dobj);           //  업소 전체
                dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN2").equals("M"))
            {
                dobj  = CALLsearch_juso_SEL32(Conn, dobj);           //  경영주 전체
                dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN2").equals("P"))
            {
                dobj  = CALLsearch_juso_SEL33(Conn, dobj);           //  허가주 전체
                dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
            }
            else
            {
                dobj  = CALLsearch_juso_SEL34(Conn, dobj);           //  전체 목록
                dobj  = CALLsearch_juso_MRG7( dobj);        //  결과용
            }
        }
        return dobj;
    }
    // 업소 미매칭
    public DOBJ CALLsearch_juso_SEL21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL21");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL21");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_juso_SEL21(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL21");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_juso_SEL21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  '영업장'  AS  GBN2  ,  A.UPSO_ZIP  AS  ZIP  ,  A.UPSO_ADDR1  ||  '  '  ||  A.UPSO_ADDR2  AS  ADDR  ,  A.UPSO_NEW_ZIP  AS  NEW_ZIP  ,  A.UPSO_NEW_ZIP1  AS  NEW_ZIP1  ,  A.UPSO_NEW_ADDR1  AS  NEW_ADDR1  ,  A.UPSO_NEW_ADDR2  AS  NEW_ADDR2  ,  A.UPSO_REF_INFO  AS  REF_INFO  ,  A.UPSO_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A,  FIDU.TENV_POST  B  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_BD_MNG_NUM  =  B.BD_MNG_NUM(+)   \n";
        query +=" AND  (A.UPSO_BD_MNG_NUM  IS  NULL   \n";
        query +=" OR  B.BD_MNG_NUM  IS  NULL)   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 결과용
    public DOBJ CALLsearch_juso_MRG7(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG7");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL11, SEL12, SEL13, SEL14, SEL21, SEL22, SEL23, SEL24, SEL31, SEL32, SEL33, SEL34,","");
        rvobj.setName("MRG7") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 경영주 미매칭
    public DOBJ CALLsearch_juso_SEL22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL22");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL22");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_juso_SEL22(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL22");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_juso_SEL22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  '경영주'  AS  GBN2  ,  A.MNGEMSTR_ZIP  AS  ZIP  ,  A.MNGEMSTR_ADDR1  ||  '  '  ||  A.MNGEMSTR_ADDR2  AS  ADDR  ,  A.MNGEMSTR_NEW_ZIP  AS  NEW_ZIP  ,  A.MNGEMSTR_NEW_ZIP1  AS  NEW_ZIP1  ,  A.MNGEMSTR_NEW_ADDR1  AS  NEW_ADDR1  ,  A.MNGEMSTR_NEW_ADDR2  AS  NEW_ADDR2  ,  A.MNGEMSTR_REF_INFO  AS  REF_INFO  ,  A.MNGEMSTR_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A,  FIDU.TENV_POST  B  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.MNGEMSTR_BD_MNG_NUM  =  B.BD_MNG_NUM(+)   \n";
        query +=" AND  (A.MNGEMSTR_BD_MNG_NUM  IS  NULL   \n";
        query +=" OR  B.BD_MNG_NUM  IS  NULL)   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  trim(A.MNGEMSTR_ADDR1  ||  A.MNGEMSTR_ADDR2)  IS  NOT  NULL   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 허가주 미매칭
    public DOBJ CALLsearch_juso_SEL23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL23");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL23");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_juso_SEL23(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL23");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_juso_SEL23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  '허가주'  AS  GBN2  ,  A.PERMMSTR_ZIP  AS  ZIP  ,  A.PERMMSTR_ADDR1  ||  '  '  ||  A.PERMMSTR_ADDR2  AS  ADDR  ,  A.PERMMSTR_NEW_ZIP  AS  NEW_ZIP  ,  A.PERMMSTR_NEW_ZIP1  AS  NEW_ZIP1  ,  A.PERMMSTR_NEW_ADDR1  AS  NEW_ADDR1  ,  A.PERMMSTR_NEW_ADDR2  AS  NEW_ADDR2  ,  A.PERMMSTR_REF_INFO  AS  REF_INFO  ,  A.PERMMSTR_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A,  FIDU.TENV_POST  B  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.PERMMSTR_BD_MNG_NUM  =  B.BD_MNG_NUM(+)   \n";
        query +=" AND  (A.PERMMSTR_BD_MNG_NUM  IS  NULL   \n";
        query +=" OR  B.BD_MNG_NUM  IS  NULL)   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  trim(A.PERMMSTR_ADDR1  ||  A.PERMMSTR_ADDR2)  IS  NOT  NULL   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 미매칭 목록
    public DOBJ CALLsearch_juso_SEL24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL24");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL24");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_juso_SEL24(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL24");
        rvobj.Println("SEL24");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_juso_SEL24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  '영업장'  AS  GBN2  ,  A.UPSO_ZIP  AS  ZIP  ,  A.UPSO_ADDR1  ||  '  '  ||  A.UPSO_ADDR2  AS  ADDR  ,  A.UPSO_NEW_ZIP  AS  NEW_ZIP  ,  A.UPSO_NEW_ZIP1  AS  NEW_ZIP1  ,  A.UPSO_NEW_ADDR1  AS  NEW_ADDR1  ,  A.UPSO_NEW_ADDR2  AS  NEW_ADDR2  ,  A.UPSO_REF_INFO  AS  REF_INFO  ,  A.UPSO_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A,  FIDU.TENV_POST  B  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_BD_MNG_NUM  =  B.BD_MNG_NUM(+)   \n";
        query +=" AND  (A.UPSO_BD_MNG_NUM  IS  NULL   \n";
        query +=" OR  B.BD_MNG_NUM  IS  NULL)   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)  UNION   \n";
        query +=" SELECT  A.UPSO_CD  ,  '경영주'  AS  GBN2  ,  A.MNGEMSTR_ZIP  AS  ZIP  ,  A.MNGEMSTR_ADDR1  ||  '  '  ||  A.MNGEMSTR_ADDR2  AS  ADDR  ,  A.MNGEMSTR_NEW_ZIP  AS  NEW_ZIP  ,  A.MNGEMSTR_NEW_ZIP1  AS  NEW_ZIP1  ,  A.MNGEMSTR_NEW_ADDR1  AS  NEW_ADDR1  ,  A.MNGEMSTR_NEW_ADDR2  AS  NEW_ADDR2  ,  A.MNGEMSTR_REF_INFO  AS  REF_INFO  ,  A.MNGEMSTR_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A,  FIDU.TENV_POST  B  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.MNGEMSTR_BD_MNG_NUM  =  B.BD_MNG_NUM(+)   \n";
        query +=" AND  (A.MNGEMSTR_BD_MNG_NUM  IS  NULL   \n";
        query +=" OR  B.BD_MNG_NUM  IS  NULL)   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  trim(A.MNGEMSTR_ADDR1  ||  A.MNGEMSTR_ADDR2)  IS  NOT  NULL   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL  UNION   \n";
        query +=" SELECT  A.UPSO_CD  ,  '허가주'  AS  GBN2  ,  A.PERMMSTR_ZIP  AS  ZIP  ,  A.PERMMSTR_ADDR1  ||  '  '  ||  A.PERMMSTR_ADDR2  AS  ADDR  ,  A.PERMMSTR_NEW_ZIP  AS  NEW_ZIP  ,  A.PERMMSTR_NEW_ZIP1  AS  NEW_ZIP1  ,  A.PERMMSTR_NEW_ADDR1  AS  NEW_ADDR1  ,  A.PERMMSTR_NEW_ADDR2  AS  NEW_ADDR2  ,  A.PERMMSTR_REF_INFO  AS  REF_INFO  ,  A.PERMMSTR_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A,  FIDU.TENV_POST  B  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.PERMMSTR_BD_MNG_NUM  =  B.BD_MNG_NUM(+)   \n";
        query +=" AND  (A.PERMMSTR_BD_MNG_NUM  IS  NULL   \n";
        query +=" OR  B.BD_MNG_NUM  IS  NULL)   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  trim(A.PERMMSTR_ADDR1  ||  A.PERMMSTR_ADDR2)  IS  NOT  NULL   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 업소 매칭
    public DOBJ CALLsearch_juso_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_juso_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_juso_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  '영업장'  AS  GBN2  ,  A.UPSO_ZIP  AS  ZIP  ,  A.UPSO_ADDR1  ||  '  '  ||  A.UPSO_ADDR2  AS  ADDR  ,  A.UPSO_NEW_ZIP  AS  NEW_ZIP  ,  A.UPSO_NEW_ZIP1  AS  NEW_ZIP1  ,  A.UPSO_NEW_ADDR1  AS  NEW_ADDR1  ,  A.UPSO_NEW_ADDR2  AS  NEW_ADDR2  ,  A.UPSO_REF_INFO  AS  REF_INFO  ,  A.UPSO_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_BD_MNG_NUM  IS  NOT  NULL   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 경영주 매칭
    public DOBJ CALLsearch_juso_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_juso_SEL12(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_juso_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  '경영주'  AS  GBN2  ,  A.MNGEMSTR_ZIP  AS  ZIP  ,  A.MNGEMSTR_ADDR1  ||  '  '  ||  A.MNGEMSTR_ADDR2  AS  ADDR  ,  A.MNGEMSTR_NEW_ZIP  AS  NEW_ZIP  ,  A.MNGEMSTR_NEW_ZIP1  AS  NEW_ZIP1  ,  A.MNGEMSTR_NEW_ADDR1  AS  NEW_ADDR1  ,  A.MNGEMSTR_NEW_ADDR2  AS  NEW_ADDR2  ,  A.MNGEMSTR_REF_INFO  AS  REF_INFO  ,  A.MNGEMSTR_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.MNGEMSTR_BD_MNG_NUM  IS  NOT  NULL   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  trim(A.MNGEMSTR_ADDR1  ||  A.MNGEMSTR_ADDR2)  IS  NOT  NULL   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 허가주 매칭
    public DOBJ CALLsearch_juso_SEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL13");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_juso_SEL13(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_juso_SEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  '허가주'  AS  GBN2  ,  A.PERMMSTR_ZIP  AS  ZIP  ,  A.PERMMSTR_ADDR1  ||  '  '  ||  A.PERMMSTR_ADDR2  AS  ADDR  ,  A.PERMMSTR_NEW_ZIP  AS  NEW_ZIP  ,  A.PERMMSTR_NEW_ZIP1  AS  NEW_ZIP1  ,  A.PERMMSTR_NEW_ADDR1  AS  NEW_ADDR1  ,  A.PERMMSTR_NEW_ADDR2  AS  NEW_ADDR2  ,  A.PERMMSTR_REF_INFO  AS  REF_INFO  ,  A.PERMMSTR_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.PERMMSTR_BD_MNG_NUM  IS  NOT  NULL   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  trim(A.PERMMSTR_ADDR1  ||  A.PERMMSTR_ADDR2)  IS  NOT  NULL   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 매칭 목록
    public DOBJ CALLsearch_juso_SEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL14");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL14");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_juso_SEL14(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_juso_SEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  '영업장'  AS  GBN2  ,  A.UPSO_ZIP  AS  ZIP  ,  A.UPSO_ADDR1  ||  '  '  ||  A.UPSO_ADDR2  AS  ADDR  ,  A.UPSO_NEW_ZIP  AS  NEW_ZIP  ,  A.UPSO_NEW_ZIP1  AS  NEW_ZIP1  ,  A.UPSO_NEW_ADDR1  AS  NEW_ADDR1  ,  A.UPSO_NEW_ADDR2  AS  NEW_ADDR2  ,  A.UPSO_REF_INFO  AS  REF_INFO  ,  A.UPSO_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_BD_MNG_NUM  IS  NOT  NULL   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)  UNION   \n";
        query +=" SELECT  A.UPSO_CD  ,  '경영주'  AS  GBN2  ,  A.MNGEMSTR_ZIP  AS  ZIP  ,  A.MNGEMSTR_ADDR1  ||  '  '  ||  A.MNGEMSTR_ADDR2  AS  ADDR  ,  A.MNGEMSTR_NEW_ZIP  AS  NEW_ZIP  ,  A.MNGEMSTR_NEW_ZIP1  AS  NEW_ZIP1  ,  A.MNGEMSTR_NEW_ADDR1  AS  NEW_ADDR1  ,  A.MNGEMSTR_NEW_ADDR2  AS  NEW_ADDR2  ,  A.MNGEMSTR_REF_INFO  AS  REF_INFO  ,  A.MNGEMSTR_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.MNGEMSTR_BD_MNG_NUM  IS  NOT  NULL   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  trim(A.MNGEMSTR_ADDR1  ||  A.MNGEMSTR_ADDR2)  IS  NOT  NULL   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL  UNION   \n";
        query +=" SELECT  A.UPSO_CD  ,  '허가주'  AS  GBN2  ,  A.PERMMSTR_ZIP  AS  ZIP  ,  A.PERMMSTR_ADDR1  ||  '  '  ||  A.PERMMSTR_ADDR2  AS  ADDR  ,  A.PERMMSTR_NEW_ZIP  AS  NEW_ZIP  ,  A.PERMMSTR_NEW_ZIP1  AS  NEW_ZIP1  ,  A.PERMMSTR_NEW_ADDR1  AS  NEW_ADDR1  ,  A.PERMMSTR_NEW_ADDR2  AS  NEW_ADDR2  ,  A.PERMMSTR_REF_INFO  AS  REF_INFO  ,  A.PERMMSTR_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.PERMMSTR_BD_MNG_NUM  IS  NOT  NULL   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  trim(A.PERMMSTR_ADDR1  ||  A.PERMMSTR_ADDR2)  IS  NOT  NULL   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 업소 전체
    public DOBJ CALLsearch_juso_SEL31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL31");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL31");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_juso_SEL31(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL31");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_juso_SEL31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  '영업장'  AS  GBN2  ,  A.UPSO_ZIP  AS  ZIP  ,  A.UPSO_ADDR1  ||  '  '  ||  A.UPSO_ADDR2  AS  ADDR  ,  A.UPSO_NEW_ZIP  AS  NEW_ZIP  ,  A.UPSO_NEW_ZIP1  AS  NEW_ZIP1  ,  A.UPSO_NEW_ADDR1  AS  NEW_ADDR1  ,  A.UPSO_NEW_ADDR2  AS  NEW_ADDR2  ,  A.UPSO_REF_INFO  AS  REF_INFO  ,  A.UPSO_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 경영주 전체
    public DOBJ CALLsearch_juso_SEL32(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL32");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL32");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_juso_SEL32(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL32");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_juso_SEL32(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  '경영주'  AS  GBN2  ,  A.MNGEMSTR_ZIP  AS  ZIP  ,  A.MNGEMSTR_ADDR1  ||  '  '  ||  A.MNGEMSTR_ADDR2  AS  ADDR  ,  A.MNGEMSTR_NEW_ZIP  AS  NEW_ZIP  ,  A.MNGEMSTR_NEW_ZIP1  AS  NEW_ZIP1  ,  A.MNGEMSTR_NEW_ADDR1  AS  NEW_ADDR1  ,  A.MNGEMSTR_NEW_ADDR2  AS  NEW_ADDR2  ,  A.MNGEMSTR_REF_INFO  AS  REF_INFO  ,  A.MNGEMSTR_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  trim(A.MNGEMSTR_ADDR1  ||  A.MNGEMSTR_ADDR2)  IS  NOT  NULL   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 허가주 전체
    public DOBJ CALLsearch_juso_SEL33(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL33");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL33");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_juso_SEL33(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL33");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_juso_SEL33(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  '허가주'  AS  GBN2  ,  A.PERMMSTR_ZIP  AS  ZIP  ,  A.PERMMSTR_ADDR1  ||  '  '  ||  A.PERMMSTR_ADDR2  AS  ADDR  ,  A.PERMMSTR_NEW_ZIP  AS  NEW_ZIP  ,  A.PERMMSTR_NEW_ZIP1  AS  NEW_ZIP1  ,  A.PERMMSTR_NEW_ADDR1  AS  NEW_ADDR1  ,  A.PERMMSTR_NEW_ADDR2  AS  NEW_ADDR2  ,  A.PERMMSTR_REF_INFO  AS  REF_INFO  ,  A.PERMMSTR_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  trim(A.PERMMSTR_ADDR1  ||  A.PERMMSTR_ADDR2)  IS  NOT  NULL   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 전체 목록
    public DOBJ CALLsearch_juso_SEL34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL34");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL34");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_juso_SEL34(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL34");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_juso_SEL34(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  '영업장'  AS  GBN2  ,  A.UPSO_ZIP  AS  ZIP  ,  A.UPSO_ADDR1  ||  '  '  ||  A.UPSO_ADDR2  AS  ADDR  ,  A.UPSO_NEW_ZIP  AS  NEW_ZIP  ,  A.UPSO_NEW_ZIP1  AS  NEW_ZIP1  ,  A.UPSO_NEW_ADDR1  AS  NEW_ADDR1  ,  A.UPSO_NEW_ADDR2  AS  NEW_ADDR2  ,  A.UPSO_REF_INFO  AS  REF_INFO  ,  A.UPSO_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)  UNION   \n";
        query +=" SELECT  A.UPSO_CD  ,  '경영주'  AS  GBN2  ,  A.MNGEMSTR_ZIP  AS  ZIP  ,  A.MNGEMSTR_ADDR1  ||  '  '  ||  A.MNGEMSTR_ADDR2  AS  ADDR  ,  A.MNGEMSTR_NEW_ZIP  AS  NEW_ZIP  ,  A.MNGEMSTR_NEW_ZIP1  AS  NEW_ZIP1  ,  A.MNGEMSTR_NEW_ADDR1  AS  NEW_ADDR1  ,  A.MNGEMSTR_NEW_ADDR2  AS  NEW_ADDR2  ,  A.MNGEMSTR_REF_INFO  AS  REF_INFO  ,  A.MNGEMSTR_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  trim(A.MNGEMSTR_ADDR1  ||  A.MNGEMSTR_ADDR2)  IS  NOT  NULL   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL  UNION   \n";
        query +=" SELECT  A.UPSO_CD  ,  '허가주'  AS  GBN2  ,  A.PERMMSTR_ZIP  AS  ZIP  ,  A.PERMMSTR_ADDR1  ||  '  '  ||  A.PERMMSTR_ADDR2  AS  ADDR  ,  A.PERMMSTR_NEW_ZIP  AS  NEW_ZIP  ,  A.PERMMSTR_NEW_ZIP1  AS  NEW_ZIP1  ,  A.PERMMSTR_NEW_ADDR1  AS  NEW_ADDR1  ,  A.PERMMSTR_NEW_ADDR2  AS  NEW_ADDR2  ,  A.PERMMSTR_REF_INFO  AS  REF_INFO  ,  A.PERMMSTR_BD_MNG_NUM  AS  BD_MNG_NUM  ,  A.MNG_ZIP  FROM  GIBU.TBRA_UPSO  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  trim(A.PERMMSTR_ADDR1  ||  A.PERMMSTR_ADDR2)  IS  NOT  NULL   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$search_juso
    //##**$$end
}
