
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac04_s04
{
    public tac04_s04()
    {
    }
    //##**$$ttac_debtre_save
    /* * 프로그램명 : tac04_s04
    * 작성자 : 하근철
    * 작성일 : 2009/08/12
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLttac_debtre_save(DOBJ dobj)
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
            dobj  = CALLttac_debtre_save_MIUD1(Conn, dobj);           //  채무환수지급보류MIUD
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
    public DOBJ CTLttac_debtre_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttac_debtre_save_MIUD1(Conn, dobj);           //  채무환수지급보류MIUD
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 채무환수지급보류MIUD
    public DOBJ CALLttac_debtre_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLttac_debtre_save_INS5(Conn, dobj);           //  채무환수지급보류저장
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLttac_debtre_save_UPD6(Conn, dobj);           //  채무환수지급보류수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLttac_debtre_save_DEL7(Conn, dobj);           //  채무환수지급보류삭제
            }
        }
        return dobj;
    }
    // 채무환수지급보류삭제
    public DOBJ CALLttac_debtre_save_DEL7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLttac_debtre_save_DEL7(dobj, dvobj);
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
    private SQLObject SQLttac_debtre_save_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPPSUSP_GBN = dvobj.getRecord().get("SUPPSUSP_GBN");   //지급보류 구분
        String   DISTR_YRMN = dvobj.getRecord().get("DISTR_YRMN");   //분배 년월
        String   WRK_YRMN = dvobj.getRecord().get("WRK_YRMN");   //작업 년월
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_DEBTREDEMSUPPSUSP  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and WRK_YRMN=:WRK_YRMN  \n";
        query +=" and DISTR_YRMN=:DISTR_YRMN  \n";
        query +=" and SUPPSUSP_GBN=:SUPPSUSP_GBN";
        sobj.setSql(query);
        sobj.setString("SUPPSUSP_GBN", SUPPSUSP_GBN);               //지급보류 구분
        sobj.setString("DISTR_YRMN", DISTR_YRMN);               //분배 년월
        sobj.setString("WRK_YRMN", WRK_YRMN);               //작업 년월
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    // 채무환수지급보류저장
    public DOBJ CALLttac_debtre_save_INS5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLttac_debtre_save_INS5(dobj, dvobj);
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
    private SQLObject SQLttac_debtre_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   SUPPSUSP_GBN = dvobj.getRecord().get("SUPPSUSP_GBN");   //지급보류 구분
        double   OCC_AMT = dvobj.getRecord().getDouble("OCC_AMT");   //발생 금액
        String   DISTR_YRMN = dvobj.getRecord().get("DISTR_YRMN");   //분배 년월
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   WRK_YRMN = dvobj.getRecord().get("WRK_YRMN");   //작업 년월
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_DEBTREDEMSUPPSUSP (MB_CD, INS_DATE, WRK_YRMN, INSPRES_ID, DISTR_YRMN, OCC_AMT, SUPPSUSP_GBN)  \n";
        query +=" values(:MB_CD , SYSDATE, :WRK_YRMN , :INSPRES_ID , :DISTR_YRMN , :OCC_AMT , :SUPPSUSP_GBN )";
        sobj.setSql(query);
        sobj.setString("SUPPSUSP_GBN", SUPPSUSP_GBN);               //지급보류 구분
        sobj.setDouble("OCC_AMT", OCC_AMT);               //발생 금액
        sobj.setString("DISTR_YRMN", DISTR_YRMN);               //분배 년월
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("WRK_YRMN", WRK_YRMN);               //작업 년월
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    // 채무환수지급보류수정
    public DOBJ CALLttac_debtre_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLttac_debtre_save_UPD6(dobj, dvobj);
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
    private SQLObject SQLttac_debtre_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   SUPPSUSP_GBN = dvobj.getRecord().get("SUPPSUSP_GBN");   //지급보류 구분
        double   OCC_AMT = dvobj.getRecord().getDouble("OCC_AMT");   //발생 금액
        String   DISTR_YRMN = dvobj.getRecord().get("DISTR_YRMN");   //분배 년월
        String   WRK_YRMN = dvobj.getRecord().get("WRK_YRMN");   //작업 년월
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_DEBTREDEMSUPPSUSP  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , OCC_AMT=:OCC_AMT , MOD_DATE=SYSDATE  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and WRK_YRMN=:WRK_YRMN  \n";
        query +=" and DISTR_YRMN=:DISTR_YRMN  \n";
        query +=" and SUPPSUSP_GBN=:SUPPSUSP_GBN";
        sobj.setSql(query);
        sobj.setString("SUPPSUSP_GBN", SUPPSUSP_GBN);               //지급보류 구분
        sobj.setDouble("OCC_AMT", OCC_AMT);               //발생 금액
        sobj.setString("DISTR_YRMN", DISTR_YRMN);               //분배 년월
        sobj.setString("WRK_YRMN", WRK_YRMN);               //작업 년월
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    //##**$$ttac_debtre_save
    //##**$$ttac_debtre_select
    /* * 프로그램명 : tac04_s04
    * 작성자 : 하근철
    * 작성일 : 2009/08/12
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLttac_debtre_select(DOBJ dobj)
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
            dobj  = CALLttac_debtre_select_SEL1(Conn, dobj);           //  채무환수지급보류조회
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
    public DOBJ CTLttac_debtre_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttac_debtre_select_SEL1(Conn, dobj);           //  채무환수지급보류조회
        return dobj;
    }
    // 채무환수지급보류조회
    public DOBJ CALLttac_debtre_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLttac_debtre_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_debtre_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        String   WRK_YRMN = dvobj.getRecord().get("WRK_YRMN");   //작업 년월
        String   DISTR_YRMN = dvobj.getRecord().get("DISTR_YRMN");   //분배 년월
        String   SUPPSUSP_GBN = dvobj.getRecord().get("SUPPSUSP_GBN");   //지급보류 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT DISTR_YRMN, WRK_YRMN, MB_CD, FIDU.GET_MB_NM(FIDU.TTAC_debtredemsuppsusp.MB_CD) AS MB_NM, SUPPSUSP_GBN, OCC_AMT  ";
        query +=" FROM FIDU.TTAC_DEBTREDEMSUPPSUSP  ";
        query +=" WHERE 1=1  ";
        if( !DISTR_YRMN.equals("") )
        {
            query +=" AND DISTR_YRMN = :DISTR_YRMN  ";
        }
        if( !WRK_YRMN.equals("") )
        {
            query +=" AND WRK_YRMN = :WRK_YRMN  ";
        }
        if( !MB_CD.equals("") )
        {
            query +=" AND MB_CD = :MB_CD  ";
        }
        if( !SUPPSUSP_GBN.equals("") )
        {
            query +=" AND SUPPSUSP_GBN = :SUPPSUSP_GBN  ";
        }
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        if(!WRK_YRMN.equals(""))
        {
            sobj.setString("WRK_YRMN", WRK_YRMN);               //작업 년월
        }
        if(!DISTR_YRMN.equals(""))
        {
            sobj.setString("DISTR_YRMN", DISTR_YRMN);               //분배 년월
        }
        if(!SUPPSUSP_GBN.equals(""))
        {
            sobj.setString("SUPPSUSP_GBN", SUPPSUSP_GBN);               //지급보류 구분
        }
        return sobj;
    }
    //##**$$ttac_debtre_select
    //##**$$tac10_p13_select
    /* * 프로그램명 : tac04_s04
    * 작성자 : 999999
    * 작성일 : 2009/08/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac10_p13_select(DOBJ dobj)
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
            dobj  = CALLtac10_p13_select_SEL1(Conn, dobj);           //  회원정보변경내역조회
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
    public DOBJ CTLtac10_p13_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_p13_select_SEL1(Conn, dobj);           //  회원정보변경내역조회
        return dobj;
    }
    // 회원정보변경내역조회
    public DOBJ CALLtac10_p13_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_p13_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_p13_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TABLE_PL = dobj.getRetObject("S").getRecord().get("TABLE_PL");   //TABLE_PL
        String   INS_FR_DATE = dobj.getRetObject("S").getRecord().get("INS_FR_DATE");   //INS_FR_DATE
        String   INS_TO_DATE = dobj.getRetObject("S").getRecord().get("INS_TO_DATE");   //INS_TO_DATE
        String   HANMB_NM = dobj.getRetObject("S").getRecord().get("HANMB_NM");   //회원 거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MNG_NUM,  TO_CHAR(A.INS_DATE,'YYYYMMDD')  AS  INS_DATE,  A.TABLE_PK,  B.HANMB_NM,  A.COL_NM,  A.CHGBFR_CTENT,  A.CHGATR_CTENT  FROM  FIDU.TENV_HISTY  A,  FIDU.TMEM_MB  B  WHERE  A.HISTY_GBN  =  '1'   \n";
        query +=" AND  A.COL_CTENT  =  'SUPPACCN_NUM'   \n";
        query +=" AND  A.TABLE_PK  =  B.MB_CD   \n";
        query +=" AND  A.TABLE_PK  LIKE  :TABLE_PL  ||  '%'   \n";
        query +=" AND  B.HANMB_NM  LIKE  '%'  ||  :HANMB_NM  ||  '%'   \n";
        query +=" AND  TO_DATE(TO_CHAR(A.INS_DATE,'YYYYMMDD'),'YYYYMMDD')  >=  :INS_FR_DATE   \n";
        query +=" AND  TO_DATE(TO_CHAR(A.INS_DATE,'YYYYMMDD'),'YYYYMMDD')  <=  :INS_TO_DATE  ORDER  BY  A.MNG_NUM ";
        sobj.setSql(query);
        sobj.setString("TABLE_PL", TABLE_PL);               //TABLE_PL
        sobj.setString("INS_FR_DATE", INS_FR_DATE);               //INS_FR_DATE
        sobj.setString("INS_TO_DATE", INS_TO_DATE);               //INS_TO_DATE
        sobj.setString("HANMB_NM", HANMB_NM);               //회원 거래처 코드
        return sobj;
    }
    //##**$$tac10_p13_select
    //##**$$end
}
