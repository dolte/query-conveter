
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class save_acncmInfo
{
    public save_acncmInfo()
    {
    }
    //##**$$ACNCMINFO_SAVE
    /*
    */
    public DOBJ CTLACNCMINFO_SAVE(DOBJ dobj)
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
            dobj  = CALLACNCMINFO_SAVE_DEL33(Conn, dobj);           //  반주기정보삭제
            dobj  = CALLACNCMINFO_SAVE_INS34(Conn, dobj);           //  반주기정보입력
            dobj  = CALLACNCMINFO_SAVE_MIUD18(Conn, dobj);           //  업소별오프리관리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLACNCMINFO_SAVE_DEL23(Conn, dobj);           //  금영_삭제
            dobj  = CALLACNCMINFO_SAVE_INS24(Conn, dobj);           //  금영_삽입
            dobj  = CALLACNCMINFO_SAVE_DEL25(Conn, dobj);           //  태진_삭제
            dobj  = CALLACNCMINFO_SAVE_INS27(Conn, dobj);           //  태진_저장
            dobj  = CALLACNCMINFO_SAVE_DEL26(Conn, dobj);           //  기타삭제
            dobj  = CALLACNCMINFO_SAVE_INS28(Conn, dobj);           //  기타_저장
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
    public DOBJ CTLACNCMINFO_SAVE( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLACNCMINFO_SAVE_DEL33(Conn, dobj);           //  반주기정보삭제
        dobj  = CALLACNCMINFO_SAVE_INS34(Conn, dobj);           //  반주기정보입력
        dobj  = CALLACNCMINFO_SAVE_MIUD18(Conn, dobj);           //  업소별오프리관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLACNCMINFO_SAVE_DEL23(Conn, dobj);           //  금영_삭제
        dobj  = CALLACNCMINFO_SAVE_INS24(Conn, dobj);           //  금영_삽입
        dobj  = CALLACNCMINFO_SAVE_DEL25(Conn, dobj);           //  태진_삭제
        dobj  = CALLACNCMINFO_SAVE_INS27(Conn, dobj);           //  태진_저장
        dobj  = CALLACNCMINFO_SAVE_DEL26(Conn, dobj);           //  기타삭제
        dobj  = CALLACNCMINFO_SAVE_INS28(Conn, dobj);           //  기타_저장
        return dobj;
    }
    // 반주기정보삭제
    public DOBJ CALLACNCMINFO_SAVE_DEL33(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL33");
        VOBJ dvobj = dobj.getRetObject("S3");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("DEL33");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLACNCMINFO_SAVE_DEL33(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL33") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLACNCMINFO_SAVE_DEL33(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S5").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSOCLASS_ONOFF_INFO  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 반주기정보입력
    public DOBJ CALLACNCMINFO_SAVE_INS34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS34");
        VOBJ dvobj = dobj.getRetObject("S3");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("INS34");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLACNCMINFO_SAVE_INS34(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS34") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLACNCMINFO_SAVE_INS34(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ONOFF_GBN = dvobj.getRecord().get("ONOFF_GBN");   //온오프 구분
        int   ACMCN_DAESU = dvobj.getRecord().getInt("ACMCN_DAESU");   //반주기 대수
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //모델 코드
        String   UPSO_CD = dobj.getRetObject("S5").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSOCLASS_ONOFF_INFO (MODEL_CD, ACMCN_DAESU, ONOFF_GBN, UPSO_CD)  \n";
        query +=" values(:MODEL_CD , :ACMCN_DAESU , :ONOFF_GBN , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //온오프 구분
        sobj.setInt("ACMCN_DAESU", ACMCN_DAESU);               //반주기 대수
        sobj.setString("MODEL_CD", MODEL_CD);               //모델 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소별오프리관리
    public DOBJ CALLACNCMINFO_SAVE_MIUD18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD18");
        VOBJ dvobj = dobj.getRetObject("S4");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLACNCMINFO_SAVE_INS15(Conn, dobj);           //  저장
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLACNCMINFO_SAVE_UPD16(Conn, dobj);           //  수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLACNCMINFO_SAVE_DEL17(Conn, dobj);           //  삭제
            }
        }
        return dobj;
    }
    // 삭제
    public DOBJ CALLACNCMINFO_SAVE_DEL17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL17");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("DEL17");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLACNCMINFO_SAVE_DEL17(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL17") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLACNCMINFO_SAVE_DEL17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //모델 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSOCLASS_AUBRY_INFO  \n";
        query +=" where MODEL_CD=:MODEL_CD  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MODEL_CD", MODEL_CD);               //모델 코드
        return sobj;
    }
    // 저장
    public DOBJ CALLACNCMINFO_SAVE_INS15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS15");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS15");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLACNCMINFO_SAVE_INS15(dobj, dvobj);
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
    private SQLObject SQLACNCMINFO_SAVE_INS15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   MCHN_COMPY = dvobj.getRecord().get("MCHN_COMPY");   //기계 회사
        String   MODEL_NM = dvobj.getRecord().get("MODEL_NM");   //모델 명
        String   MCHN_COMPYNM = dvobj.getRecord().get("MCHN_COMPYNM");   //기계 회사명
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //모델 코드
        String   UPSO_CD = dobj.getRetObject("S5").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSOCLASS_AUBRY_INFO (INS_DATE, MODEL_CD, INSPRES_ID, MCHN_COMPYNM, UPSO_CD, MODEL_NM, MCHN_COMPY)  \n";
        query +=" values(SYSDATE, :MODEL_CD , :INSPRES_ID , :MCHN_COMPYNM , :UPSO_CD , :MODEL_NM , :MCHN_COMPY )";
        sobj.setSql(query);
        sobj.setString("MCHN_COMPY", MCHN_COMPY);               //기계 회사
        sobj.setString("MODEL_NM", MODEL_NM);               //모델 명
        sobj.setString("MCHN_COMPYNM", MCHN_COMPYNM);               //기계 회사명
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("MODEL_CD", MODEL_CD);               //모델 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 수정
    public DOBJ CALLACNCMINFO_SAVE_UPD16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD16");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("UPD16");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLACNCMINFO_SAVE_UPD16(dobj, dvobj);
            qexe.DispSelectSql(sobj);
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
    private SQLObject SQLACNCMINFO_SAVE_UPD16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   MCHN_COMPY = dvobj.getRecord().get("MCHN_COMPY");   //기계 회사
        String   MODEL_NM = dvobj.getRecord().get("MODEL_NM");   //모델 명
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   MCHN_COMPYNM = dvobj.getRecord().get("MCHN_COMPYNM");   //기계 회사명
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //모델 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSOCLASS_AUBRY_INFO  \n";
        query +=" set INSPRES_ID=:INSPRES_ID , MCHN_COMPYNM=:MCHN_COMPYNM , MODEL_NM=:MODEL_NM , MCHN_COMPY=:MCHN_COMPY  \n";
        query +=" where MODEL_CD=:MODEL_CD  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MCHN_COMPY", MCHN_COMPY);               //기계 회사
        sobj.setString("MODEL_NM", MODEL_NM);               //모델 명
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MCHN_COMPYNM", MCHN_COMPYNM);               //기계 회사명
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("MODEL_CD", MODEL_CD);               //모델 코드
        return sobj;
    }
    // 금영_삭제
    public DOBJ CALLACNCMINFO_SAVE_DEL23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL23");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("DEL23");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLACNCMINFO_SAVE_DEL23(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL23") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLACNCMINFO_SAVE_DEL23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S5").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSOCLASS_ACMCN_INFO  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 금영_삽입
    public DOBJ CALLACNCMINFO_SAVE_INS24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS24");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("INS24");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLACNCMINFO_SAVE_INS24(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS24") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLACNCMINFO_SAVE_INS24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int   ACMCN_DAESU = dvobj.getRecord().getInt("ACMCN_DAESU");   //반주기 대수
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //모델 코드
        String   UPSO_CD = dobj.getRetObject("S5").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSOCLASS_ACMCN_INFO (MODEL_CD, INSPRES_ID, ACMCN_DAESU, UPSO_CD)  \n";
        query +=" values(:MODEL_CD , :INSPRES_ID , :ACMCN_DAESU , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setInt("ACMCN_DAESU", ACMCN_DAESU);               //반주기 대수
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("MODEL_CD", MODEL_CD);               //모델 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 태진_삭제
    public DOBJ CALLACNCMINFO_SAVE_DEL25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL25");
        VOBJ dvobj = dobj.getRetObject("S1");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("DEL25");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLACNCMINFO_SAVE_DEL25(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL25") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLACNCMINFO_SAVE_DEL25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //모델 코드
        String   UPSO_CD = dobj.getRetObject("S5").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSOCLASS_ACMCN_INFO  \n";
        query +=" where MODEL_CD=:MODEL_CD  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MODEL_CD", MODEL_CD);               //모델 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 태진_저장
    public DOBJ CALLACNCMINFO_SAVE_INS27(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS27");
        VOBJ dvobj = dobj.getRetObject("S1");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("INS27");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLACNCMINFO_SAVE_INS27(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS27") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLACNCMINFO_SAVE_INS27(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int   ACMCN_DAESU = dvobj.getRecord().getInt("ACMCN_DAESU");   //반주기 대수
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //모델 코드
        String   UPSO_CD = dobj.getRetObject("S5").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSOCLASS_ACMCN_INFO (MODEL_CD, INSPRES_ID, ACMCN_DAESU, UPSO_CD)  \n";
        query +=" values(:MODEL_CD , :INSPRES_ID , :ACMCN_DAESU , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setInt("ACMCN_DAESU", ACMCN_DAESU);               //반주기 대수
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("MODEL_CD", MODEL_CD);               //모델 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 기타삭제
    public DOBJ CALLACNCMINFO_SAVE_DEL26(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL26");
        VOBJ dvobj = dobj.getRetObject("S2");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("DEL26");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLACNCMINFO_SAVE_DEL26(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL26") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLACNCMINFO_SAVE_DEL26(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //모델 코드
        String   UPSO_CD = dobj.getRetObject("S5").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSOCLASS_ACMCN_INFO  \n";
        query +=" where MODEL_CD=:MODEL_CD  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MODEL_CD", MODEL_CD);               //모델 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 기타_저장
    public DOBJ CALLACNCMINFO_SAVE_INS28(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS28");
        VOBJ dvobj = dobj.getRetObject("S2");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("INS28");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLACNCMINFO_SAVE_INS28(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS28") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLACNCMINFO_SAVE_INS28(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int   ACMCN_DAESU = dvobj.getRecord().getInt("ACMCN_DAESU");   //반주기 대수
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //모델 코드
        String   UPSO_CD = dobj.getRetObject("S5").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSOCLASS_ACMCN_INFO (MODEL_CD, INSPRES_ID, ACMCN_DAESU, UPSO_CD)  \n";
        query +=" values(:MODEL_CD , :INSPRES_ID , :ACMCN_DAESU , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setInt("ACMCN_DAESU", ACMCN_DAESU);               //반주기 대수
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("MODEL_CD", MODEL_CD);               //모델 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$ACNCMINFO_SAVE
    //##**$$end
}
