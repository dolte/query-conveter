
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac06_s03_1
{
    public tac06_s03_1()
    {
    }
    //##**$$EventID25
    /*
    */
    public DOBJ CTLEventID25(DOBJ dobj)
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
            dobj  = CALLEventID25_XIUD4(Conn, dobj);           //  XIUD
            dobj  = CALLEventID25_XIUD5(Conn, dobj);           //  XIUD
            dobj  = CALLEventID25_XIUD8(Conn, dobj);           //  첨부파일 복사
            dobj  = CALLEventID25_MPD8(Conn, dobj);           //  지급관련변동관리용
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
    public DOBJ CTLEventID25( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLEventID25_XIUD4(Conn, dobj);           //  XIUD
        dobj  = CALLEventID25_XIUD5(Conn, dobj);           //  XIUD
        dobj  = CALLEventID25_XIUD8(Conn, dobj);           //  첨부파일 복사
        dobj  = CALLEventID25_MPD8(Conn, dobj);           //  지급관련변동관리용
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // XIUD
    public DOBJ CALLEventID25_XIUD4(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLEventID25_XIUD4(dobj, dvobj);
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
    private SQLObject SQLEventID25_XIUD4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   APPTNLETTER_MNG_NUM = dobj.getRetObject("S").getRecord().getDouble("APPTNLETTER_MNG_NUM");   //신청서 관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE HOMP.THOM_ACCNCHGAPPTNLETTER  \n";
        query +=" SET STAT_GBN ='2'  \n";
        query +=" WHERE APPTNLETTER_MNG_NUM = :APPTNLETTER_MNG_NUM";
        sobj.setSql(query);
        sobj.setDouble("APPTNLETTER_MNG_NUM", APPTNLETTER_MNG_NUM);               //신청서 관리번호
        return sobj;
    }
    // XIUD
    public DOBJ CALLEventID25_XIUD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD5");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLEventID25_XIUD5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD5");
        rvobj.Println("XIUD5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID25_XIUD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   MODPRES_ID = dvobj.getRecord().get("GNV_USERID");   //수정자 ID
        String   SUPPACCN_NUM = dobj.getRetObject("S").getRecord().get("SUPPACCN_NUM");   //지급계좌 번호
        String   SUPPBANK_CD = dobj.getRetObject("S").getRecord().get("SUPPBANK_CD");   //지급은행 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE FIDU.TMEM_MB  \n";
        query +=" SET SUPPACCN_NUM = :SUPPACCN_NUM, SUPPBANK_CD = :SUPPBANK_CD, MODPRES_ID = :MODPRES_ID, MOD_DATE = SYSDATE  \n";
        query +=" WHERE MB_CD =:MB_CD";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("SUPPACCN_NUM", SUPPACCN_NUM);               //지급계좌 번호
        sobj.setString("SUPPBANK_CD", SUPPBANK_CD);               //지급은행 코드
        return sobj;
    }
    // 첨부파일 복사
    public DOBJ CALLEventID25_XIUD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD8");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD8");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLEventID25_XIUD8(dobj, dvobj);
            qexe.DispSelectSql(sobj);
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
    private SQLObject SQLEventID25_XIUD8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   APPTNLETTER_MNG_NUM = dobj.getRetObject("S").getRecord().getDouble("APPTNLETTER_MNG_NUM");   //신청서 관리번호
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" insert into FIDU.TMEM_ENTRNPROPDOCNT (MB_CD, MNG_NUM, ENTRNDOCNT_GBN, ATTCH_FILE_NM, ATTCH_FILE_CTENT, SVR_FILE_NM, SVR_FILE_ROUT, INSPRES_ID, INS_DATE) SELECT :MB_CD AS MB_CD, (SELECT NVL( MAX( MNG_NUM ),0 ) +1 as MNG_NUM FROM FIDU.TMEM_ENTRNPROPDOCNT WHERE MB_CD = :MB_CD) AS MNG_NUM, '06' AS ENTRNDOCNT_GBN, FILE_NM AS ATTCH_FILE_NM, FILES AS ATTCH_FILE_CTENT, FILE_TEMPNM AS SVR_FILE_NM, FILE_ROUT AS SVR_FILE_ROUT, INSPRES_ID, SYSDATE FROM HOMP.THOM_ACCNCHG_FILE WHERE APPTNLETTER_MNG_NUM = :APPTNLETTER_MNG_NUM";
        sobj.setSql(query);
        sobj.setDouble("APPTNLETTER_MNG_NUM", APPTNLETTER_MNG_NUM);               //신청서 관리번호
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    // 지급관련변동관리용
    // 지급관련변동관리가 문서부와 회원부로 나뉘어있기때문에 MNG_NUM용 MPD가 필요
    public DOBJ CALLEventID25_MPD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD8");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLEventID25_SEL10(Conn, dobj);           //  문서관리MNG_NUM
                dobj  = CALLEventID25_XIUD6(Conn, dobj);           //  지급관련변동관리복사
                dobj  = CALLEventID25_XIUD9(Conn, dobj);           //  지급관련변동관리복사
            }
        }
        return dobj;
    }
    // 문서관리MNG_NUM
    public DOBJ CALLEventID25_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLEventID25_SEL10(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.Println("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID25_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(  MAX(  MNG_NUM  ),0  )  +1  AS  MNG_NUM  FROM  FIDU.TTAC_SUPP_CHGDOC ";
        sobj.setSql(query);
        return sobj;
    }
    // 지급관련변동관리복사
    // 문서관리화면에 내용 복사
    public DOBJ CALLEventID25_XIUD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD6");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("XIUD6");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLEventID25_XIUD6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD6");
        rvobj.Println("XIUD6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID25_XIUD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   APPTNLETTER_MNG_NUM = dobj.getRetObject("R").getRecord().getDouble("APPTNLETTER_MNG_NUM");   //신청서 관리번호
        double   MNG_NUM = dobj.getRetObject("SEL10").getRecord().getDouble("MNG_NUM");   //관리번호
        String   SUPPACCN_NUM = dobj.getRetObject("R").getRecord().get("SUPPACCN_NUM");   //지급계좌 번호
        String   SUPPBANK_CD = dobj.getRetObject("R").getRecord().get("SUPPBANK_CD");   //지급은행 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" insert into FIDU.TTAC_SUPP_CHGDOC (MNG_NUM, INS_DATE, INSPRES_ID, GBN, DOC_TITLE, CONTENT, APPL_YN, APPL_YRMN, ATTCH_FILE_NM, ATTCH_FILE_CTENT, SVR_FILE_NM, SVR_FILE_ROUT) SELECT  :MNG_NUM, INS_DATE, :USERID AS INSPRES_ID, '1' AS GBN, (SELECT TO_CHAR(INSDATE, 'YYYY-MM-DD AM HH:MI:SS') FROM homp.THOM_ACCNCHGAPPTNLETTER  WHERE APPTNLETTER_MNG_NUM = :APPTNLETTER_MNG_NUM) AS DOC_TITLE, (SELECT BANK_NM FROM ACCT.TCAC_BANK WHERE USE_YN='Y'  AND BANK_CD in('004','011','020','088','003','071','081','000') AND BANK_CD = :SUPPBANK_CD) || ' ' || :SUPPACCN_NUM AS CONTENT, '1' AS APPL_YN, TO_CHAR(SYSDATE, 'YYYYMM') ,  FILE_NM AS ATTCH_FILE_NM,  FILES AS ATTCH_FILE_CTENT,  FILE_TEMPNM AS SVR_FILE_NM,  FILE_ROUT AS SVR_FILE_ROUT  FROM HOMP.THOM_ACCNCHG_FILE WHERE APPTNLETTER_MNG_NUM = :APPTNLETTER_MNG_NUM";
        sobj.setSql(query);
        sobj.setDouble("APPTNLETTER_MNG_NUM", APPTNLETTER_MNG_NUM);               //신청서 관리번호
        sobj.setDouble("MNG_NUM", MNG_NUM);               //관리번호
        sobj.setString("SUPPACCN_NUM", SUPPACCN_NUM);               //지급계좌 번호
        sobj.setString("SUPPBANK_CD", SUPPBANK_CD);               //지급은행 코드
        return sobj;
    }
    // 지급관련변동관리복사
    // 문서관리화면에 내용 복사
    public DOBJ CALLEventID25_XIUD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD9");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("XIUD9");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLEventID25_XIUD9(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD9");
        rvobj.Println("XIUD9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID25_XIUD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("R").getRecord().get("MB_CD");   //회원 코드
        double   MNG_NUM = dobj.getRetObject("SEL10").getRecord().getDouble("MNG_NUM");   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" insert into FIDU.TTAC_SUPP_CHGDOC_MB (MNG_NUM, INS_DATE, INSPRES_ID, MB_CD) SELECT :MNG_NUM, SYSDATE, :USERID AS INSPRES_ID, :MB_CD FROM DUAL";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setDouble("MNG_NUM", MNG_NUM);               //관리번호
        return sobj;
    }
    //##**$$EventID25
    //##**$$end
}
