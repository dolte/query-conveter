
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac04_s01
{
    public tac04_s01()
    {
    }
    //##**$$searchUpload
    /*
    */
    public DOBJ CTLsearchUpload(DOBJ dobj)
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
            dobj  = CALLsearchUpload_SEL1(Conn, dobj);           //  재분배금조회
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
    public DOBJ CTLsearchUpload( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchUpload_SEL1(Conn, dobj);           //  재분배금조회
        return dobj;
    }
    // 재분배금조회
    public DOBJ CALLsearchUpload_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchUpload_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchUpload_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REDEM_START_YRMN = dobj.getRetObject("S").getRecord().get("REDEM_START_YRMN");   //환수 시작 년월
        String   REDEM_MB_CD = dobj.getRetObject("S").getRecord().get("REDEM_MB_CD");   //환수회원코드
        String   REDEM_END_YRMN = dobj.getRetObject("S").getRecord().get("REDEM_END_YRMN");   //환수 종료 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REDEM_MB_CD,  A.REDEM_YRMN,  A.TRNSF_GBN,  A.REDEM_AMT,  B.HANMB_NM,  A.REMAK  FROM  FIDU.TTAC_REDEMREDISTR_UPLOAD  A,  FIDU.TMEM_MB  B  WHERE  A.REDEM_MB_CD=B.MB_CD   \n";
        query +=" AND  A.REDEM_MB_CD  LIKE  :REDEM_MB_CD  ||  '%'   \n";
        query +=" AND  A.REDEM_YRMN  BETWEEN  SUBSTR(:REDEM_START_YRMN,1,6)   \n";
        query +=" AND  SUBSTR(:REDEM_END_YRMN,1,6)  ORDER  BY  B.HANMB_NM  ,A.REDEM_YRMN  ASC ";
        sobj.setSql(query);
        sobj.setString("REDEM_START_YRMN", REDEM_START_YRMN);               //환수 시작 년월
        sobj.setString("REDEM_MB_CD", REDEM_MB_CD);               //환수회원코드
        sobj.setString("REDEM_END_YRMN", REDEM_END_YRMN);               //환수 종료 년월
        return sobj;
    }
    //##**$$searchUpload
    //##**$$ttac_redem_save
    /* * 프로그램명 : tac04_s01
    * 작성자 : 성낙문
    * 작성일 : 2009/10/06
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLttac_redem_save(DOBJ dobj)
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
            dobj  = CALLttac_redem_save_MIUD1(Conn, dobj);           //  환수금재분배MIUD
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
    public DOBJ CTLttac_redem_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttac_redem_save_MIUD1(Conn, dobj);           //  환수금재분배MIUD
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 환수금재분배MIUD
    public DOBJ CALLttac_redem_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLttac_redem_save_INS14(Conn, dobj);           //  지급내역 저장
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLttac_redem_save_UPD6(Conn, dobj);           //  환수금재분배수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLttac_redem_save_DEL7(Conn, dobj);           //  환수금재분배삭제
            }
        }
        return dobj;
    }
    // 환수금재분배삭제
    public DOBJ CALLttac_redem_save_DEL7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLttac_redem_save_DEL7(dobj, dvobj);
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
    private SQLObject SQLttac_redem_save_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REDEM_MB_CD = dvobj.getRecord().get("REDEM_MB_CD");   //환수회원코드
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        String   REDEM_YRMN = dvobj.getRecord().get("REDEM_YRMN");
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_REDEMREDISTR  \n";
        query +=" where REDEM_YRMN=:REDEM_YRMN  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN  \n";
        query +=" and REDEM_MB_CD=:REDEM_MB_CD";
        sobj.setSql(query);
        sobj.setString("REDEM_MB_CD", REDEM_MB_CD);               //환수회원코드
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("REDEM_YRMN", REDEM_YRMN);
        return sobj;
    }
    // 지급내역 저장
    // 지급내역 저장
    public DOBJ CALLttac_redem_save_INS14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS14");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLttac_redem_save_INS14(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS14") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_redem_save_INS14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //등록자 ID
        String INS_DATE ="";        //등록 일시
        double   REDEM_AMT = dvobj.getRecord().getDouble("REDEM_AMT");   //환수 금액
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   REDEM_RATE = dvobj.getRecord().get("REDEM_RATE");   //환수율
        String   REDEM_BALANCE_AMT = dvobj.getRecord().get("REDEM_BALANCE_AMT");
        String   REDEM_MB_CD = dvobj.getRecord().get("REDEM_MB_CD");   //환수회원코드
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        String   REDEM_YRMN = dvobj.getRecord().get("REDEM_YRMN");
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_REDEMREDISTR (REDEM_YRMN, TRNSF_GBN, REDEM_MB_CD, REDEM_BALANCE_AMT, REDEM_RATE, REMAK, REDEM_AMT)  \n";
        query +=" values(:REDEM_YRMN , :TRNSF_GBN , :REDEM_MB_CD , :REDEM_BALANCE_AMT , :REDEM_RATE , :REMAK , :REDEM_AMT )";
        sobj.setSql(query);
        sobj.setDouble("REDEM_AMT", REDEM_AMT);               //환수 금액
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("REDEM_RATE", REDEM_RATE);               //환수율
        sobj.setString("REDEM_BALANCE_AMT", REDEM_BALANCE_AMT);
        sobj.setString("REDEM_MB_CD", REDEM_MB_CD);               //환수회원코드
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("REDEM_YRMN", REDEM_YRMN);
        return sobj;
    }
    // 환수금재분배수정
    public DOBJ CALLttac_redem_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLttac_redem_save_UPD6(dobj, dvobj);
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
    private SQLObject SQLttac_redem_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //수정자 ID
        String MOD_DATE = dvobj.getRecord().get("SYSDATE");        //수정 일시
        double   REDEM_AMT = dvobj.getRecord().getDouble("REDEM_AMT");   //환수 금액
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   REDEM_RATE = dvobj.getRecord().get("REDEM_RATE");   //환수율
        String   REDEM_BALANCE_AMT = dvobj.getRecord().get("REDEM_BALANCE_AMT");
        String   REDEM_MB_CD = dvobj.getRecord().get("REDEM_MB_CD");   //환수회원코드
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        String   REDEM_YRMN = dvobj.getRecord().get("REDEM_YRMN");
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_REDEMREDISTR  \n";
        query +=" set REDEM_BALANCE_AMT=:REDEM_BALANCE_AMT , REDEM_RATE=:REDEM_RATE , REMAK=:REMAK , REDEM_AMT=:REDEM_AMT  \n";
        query +=" where REDEM_YRMN=:REDEM_YRMN  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN  \n";
        query +=" and REDEM_MB_CD=:REDEM_MB_CD";
        sobj.setSql(query);
        sobj.setDouble("REDEM_AMT", REDEM_AMT);               //환수 금액
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("REDEM_RATE", REDEM_RATE);               //환수율
        sobj.setString("REDEM_BALANCE_AMT", REDEM_BALANCE_AMT);
        sobj.setString("REDEM_MB_CD", REDEM_MB_CD);               //환수회원코드
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("REDEM_YRMN", REDEM_YRMN);
        return sobj;
    }
    //##**$$ttac_redem_save
    //##**$$redemredistr_balance_select
    /* * 프로그램명 : tac04_s01
    * 작성자 : 성낙문
    * 작성일 : 2009/11/17
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLredemredistr_balance_select(DOBJ dobj)
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
            dobj  = CALLredemredistr_balance_select_SEL1(Conn, dobj);           //  환수금 기초 잔액 조회
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
    public DOBJ CTLredemredistr_balance_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLredemredistr_balance_select_SEL1(Conn, dobj);           //  환수금 기초 잔액 조회
        return dobj;
    }
    // 환수금 기초 잔액 조회
    public DOBJ CALLredemredistr_balance_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLredemredistr_balance_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLredemredistr_balance_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   REDEM_MB_CD = dobj.getRetObject("S").getRecord().get("REDEM_MB_CD");   //환수회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REDEM_MB_CD,  A.REDEM_START_YRMN,  A.REDEM_END_YRMN,  A.REDEM_AMT,  A.REDEM_TAMT,  A.TRNSF_GBN,  A.INSPRES_ID,  A.INS_DATE,  A.MODPRES_ID,  A.MOD_DATE,  B.HANMB_NM  FROM  FIDU.TTAC_REDEMREDISTR_BALANCE  A,  FIDU.TMEM_MB  B  WHERE  A.REDEM_MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.REDEM_MB_CD  LIKE  :REDEM_MB_CD||  '%'   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%' ";
        sobj.setSql(query);
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("REDEM_MB_CD", REDEM_MB_CD);               //환수회원코드
        return sobj;
    }
    //##**$$redemredistr_balance_select
    //##**$$redem_balance_amt_select
    /* * 프로그램명 : tac04_s01
    * 작성자 : 성낙문
    * 작성일 : 2009/10/01
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLredem_balance_amt_select(DOBJ dobj)
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
            dobj  = CALLredem_balance_amt_select_SEL1(Conn, dobj);           //  환수금 잔액 조회
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
    public DOBJ CTLredem_balance_amt_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLredem_balance_amt_select_SEL1(Conn, dobj);           //  환수금 잔액 조회
        return dobj;
    }
    // 환수금 잔액 조회
    public DOBJ CALLredem_balance_amt_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLredem_balance_amt_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLredem_balance_amt_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REDEM_MB_CD,  MIN(REDEM_BALANCE_AMT)  REDEM_BALANCE_AMT  FROM  FIDU.TTAC_REDEMREDISTR  WHERE  REDEM_MB_CD  =  :MB_CD  GROUP  BY  REDEM_MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    //##**$$redem_balance_amt_select
    //##**$$ttac_redem_select
    /* * 프로그램명 : tac04_s01
    * 작성자 : 손주환
    * 작성일 : 2009/11/25
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLttac_redem_select(DOBJ dobj)
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
            dobj  = CALLttac_redem_select_SEL1(Conn, dobj);           //  환수금조회
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
    public DOBJ CTLttac_redem_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttac_redem_select_SEL1(Conn, dobj);           //  환수금조회
        return dobj;
    }
    // 환수금조회
    public DOBJ CALLttac_redem_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLttac_redem_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_redem_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REDEM_START_YRMN = dobj.getRetObject("S").getRecord().get("REDEM_START_YRMN");   //환수 시작 년월
        String   REDEM_MB_CD = dobj.getRetObject("S").getRecord().get("REDEM_MB_CD");   //환수회원코드
        String   REDEM_END_YRMN = dobj.getRetObject("S").getRecord().get("REDEM_END_YRMN");   //환수 종료 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ROWNUM  AS  RMUN,  C.REDEM_MB_CD,  C.TRNSF_GBN,  C.REDEM_AMT,  D.TOT_REALSUPP_AMT,  C.REDEM_RATE,  C.REDEM_YRMN,  C.REDEM_BALANCE_AMT,  C.REMAK,  A.MB_CD,  A.HANMB_NM,  A.SEX_GBN,  A.INS_NUM,  A.PHON_NUM,  A.NATY_CD,  A.MB_GBN1,  A.OFFCL_GBN,  B.POST_NUM,  TRIM(B.ADDR||'  '||B.ADDR_DETED)  AS  JUSO,  E.REDEM_START_YRMN  AS  START_YRMN  ,  E.REDEM_END_YRMN  AS  END_YRMN,  E.REDEM_AMT  AS  BALANCE_AMT,  F.CODE_NM  FROM  FIDU.TTAC_REDEMREDISTR  C,  FIDU.TMEM_MB  A,  FIDU.TMEM_ADBK  B,  FIDU.TTAC_COPYRTAL  D,  FIDU.TTAC_REDEMREDISTR_BALANCE  E  ,  FIDU.TENV_CODE  F  WHERE  C.REDEM_MB_CD  =  A.MB_CD   \n";
        query +=" AND  C.REDEM_MB_CD  =  B.MB_CD(+)   \n";
        query +=" AND  C.REDEM_MB_CD  =  D.MB_CD(+)   \n";
        query +=" AND  C.REDEM_YRMN  =  D.SUPP_YRMN(+)   \n";
        query +=" AND  C.TRNSF_GBN  =  D.TRNSF_GBN(+)   \n";
        query +=" AND  C.REDEM_MB_CD  =  E.REDEM_MB_CD(+)   \n";
        query +=" AND  C.TRNSF_GBN  =  E.TRNSF_GBN(+)   \n";
        query +=" AND  '2'  =  B.ADDR_GBN(+)   \n";
        query +=" AND  C.REDEM_MB_CD  LIKE  :REDEM_MB_CD  ||  '%'   \n";
        query +=" AND  C.REDEM_YRMN  BETWEEN  SUBSTR(:REDEM_START_YRMN,1,6)   \n";
        query +=" AND  SUBSTR(:REDEM_END_YRMN,1,6)   \n";
        query +=" AND  C.TRNSF_GBN  =  F.CODE_CD   \n";
        query +=" AND  F.HIGH_CD  =  '00294'  ORDER  BY  C.REDEM_MB_CD,  C.REDEM_YRMN ";
        sobj.setSql(query);
        sobj.setString("REDEM_START_YRMN", REDEM_START_YRMN);               //환수 시작 년월
        sobj.setString("REDEM_MB_CD", REDEM_MB_CD);               //환수회원코드
        sobj.setString("REDEM_END_YRMN", REDEM_END_YRMN);               //환수 종료 년월
        return sobj;
    }
    //##**$$ttac_redem_select
    //##**$$tot_realsupp_amt_select
    /* * 프로그램명 : tac04_s01
    * 작성자 : 성낙문
    * 작성일 : 2009/10/06
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtot_realsupp_amt_select(DOBJ dobj)
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
            dobj  = CALLtot_realsupp_amt_select_SEL1(Conn, dobj);           //  회원 실지급액 조회
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
    public DOBJ CTLtot_realsupp_amt_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtot_realsupp_amt_select_SEL1(Conn, dobj);           //  회원 실지급액 조회
        return dobj;
    }
    // 회원 실지급액 조회
    public DOBJ CALLtot_realsupp_amt_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtot_realsupp_amt_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtot_realsupp_amt_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  C.MB_CD,  C.TOT_REALSUPP_AMT,  A.INS_NUM,  A.PHON_NUM,  A.NATY_CD,  A.MB_GBN1,  A.OFFCL_GBN,  B.POST_NUM,  TRIM(B.ADDR||'  '||B.ADDR_DETED)  AS  JUSO,  D.REDEM_START_YRMN  AS  START_YRMN  ,  D.REDEM_END_YRMN  AS  END_YRMN,  D.REDEM_AMT  AS  BALANCE_AMT  FROM  FIDU.TTAC_COPYRTAL  C,  FIDU.TMEM_MB  A,  FIDU.TMEM_ADBK  B,  FIDU.TTAC_REDEMREDISTR_BALANCE  D  WHERE  C.MB_CD  =  A.MB_CD   \n";
        query +=" AND  C.MB_CD  =  B.MB_CD(+)   \n";
        query +=" AND  C.MB_CD  =  D.REDEM_MB_CD(+)   \n";
        query +=" AND  '2'  =  B.ADDR_GBN(+)   \n";
        query +=" AND  C.MB_CD  =  :MB_CD   \n";
        query +=" AND  SUPP_YRMN  =  :SUPP_YRMN ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$tot_realsupp_amt_select
    //##**$$end
}
