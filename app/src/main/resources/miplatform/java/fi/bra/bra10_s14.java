
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s14
{
    public bra10_s14()
    {
    }
    //##**$$sel_demd_bscon
    /*
    */
    public DOBJ CTLsel_demd_bscon(DOBJ dobj)
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
            dobj  = CALLsel_demd_bscon_SEL1(Conn, dobj);           //  청구자료조회
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
    public DOBJ CTLsel_demd_bscon( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_demd_bscon_SEL1(Conn, dobj);           //  청구자료조회
        return dobj;
    }
    // 청구자료조회
    public DOBJ CALLsel_demd_bscon_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_demd_bscon_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_demd_bscon_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   MATCH_GBN = dobj.getRetObject("S").getRecord().get("MATCH_GBN");   //매칭 구분
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BSCON_CD , BSCON_UPSO_CD , BSCON_UPSO_NM , UPSO_CD , UPSO_ZIP , UPSO_ADDR1 , UPSO_ADDR2 , MATCH_GBN , DEMD_YRMN , DEMD_AMT , USE_AMT , ATAX_AMT , REMAK , FIDU.GET_STAFF_NM(INSPRES_ID) AS INSPRES_NM , FIDU.GET_STAFF_NM(MODPRES_ID) AS MODPRES_NM  ";
        query +=" FROM GIBU.TBRA_BSCON_DEMD_UPLOAD  ";
        query +=" WHERE DEMD_YRMN = :DEMD_YRMN  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD = :BSCON_CD  ";
        }
        if( !MATCH_GBN.equals("") )
        {
            query +=" AND MATCH_GBN = :MATCH_GBN  ";
        }
        query +=" ORDER BY BSCON_CD, BSCON_UPSO_CD, BSCON_UPSO_NM  ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        if(!MATCH_GBN.equals(""))
        {
            sobj.setString("MATCH_GBN", MATCH_GBN);               //매칭 구분
        }
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        }
        return sobj;
    }
    //##**$$sel_demd_bscon
    //##**$$mng_match_bscon
    /*
    */
    public DOBJ CTLmng_match_bscon(DOBJ dobj)
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
            dobj  = CALLmng_match_bscon_MIUD1(Conn, dobj);           //  분기
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
    public DOBJ CTLmng_match_bscon( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_match_bscon_MIUD1(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLmng_match_bscon_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLmng_match_bscon_SEL5(Conn, dobj);           //  기존매칭내역조회
                if(dobj.getRetObject("SEL5").getRecordCnt() > 0 && !dobj.getRetObject("R").getRecord().get("UPSO_CD").equals(dobj.getRetObject("SEL5").getRecord().get("UPSO_CD")))
                {
                    dobj  = CALLmng_match_bscon_XIUD14(Conn, dobj);           //  매칭내역수정
                    dobj  = CALLmng_match_bscon_SEL11(Conn, dobj);           //  순번채번
                    dobj  = CALLmng_match_bscon_XIUD13(Conn, dobj);           //  매칭내역추가
                    dobj  = CALLmng_match_bscon_XIUD11(Conn, dobj);           //  청구자료업소매칭
                }
                else if( dobj.getRetObject("SEL5").getRecordCnt() < 1)
                {
                    dobj  = CALLmng_match_bscon_UPD15(Conn, dobj);           //  미매칭 매칭으로 변경
                    dobj  = CALLmng_match_bscon_XIUD11(Conn, dobj);           //  청구자료업소매칭
                }
                else
                {
                    dobj  = CALLmng_match_bscon_XIUD11(Conn, dobj);           //  청구자료업소매칭
                }
            }
        }
        return dobj;
    }
    // 기존매칭내역조회
    public DOBJ CALLmng_match_bscon_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_match_bscon_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_match_bscon_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //타단체업소코드
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  BSCON_UPSO_CD  =  :BSCON_UPSO_CD   \n";
        query +=" AND  MATCH_GBN  =  'Y'   \n";
        query +=" AND  USE_YN  =  'Y' ";
        sobj.setSql(query);
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //타단체업소코드
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    // 매칭내역수정
    public DOBJ CALLmng_match_bscon_XIUD14(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLmng_match_bscon_XIUD14(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_match_bscon_XIUD14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //거래처 코드
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //타단체업소코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_BSCON_CONTRINFO  \n";
        query +=" SET MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE , USE_YN = 'N'  \n";
        query +=" WHERE BSCON_CD = :BSCON_CD  \n";
        query +=" AND BSCON_UPSO_CD = :BSCON_UPSO_CD  \n";
        query +=" AND USE_YN = 'Y'  \n";
        query +=" AND MATCH_GBN = 'Y'";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //타단체업소코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 순번채번
    public DOBJ CALLmng_match_bscon_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_match_bscon_SEL11(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_match_bscon_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //타단체업소코드
        String   UPSO_ZIP = dobj.getRetObject("R").getRecord().get("UPSO_ZIP");   //업소 우편번호
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  AS  SEQ  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  BSCON_UPSO_CD  =  :BSCON_UPSO_CD   \n";
        query +=" AND  UPSO_ZIP  =  :UPSO_ZIP ";
        sobj.setSql(query);
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //타단체업소코드
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //업소 우편번호
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    // 매칭내역추가
    public DOBJ CALLmng_match_bscon_XIUD13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD13");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("XIUD13");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_match_bscon_XIUD13(dobj, dvobj);
            qexe.DispSelectSql(sobj);
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
    private SQLObject SQLmng_match_bscon_XIUD13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //거래처 코드
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //타단체업소코드
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //비고
        double   SEQ = dobj.getRetObject("SEL11").getRecord().getDouble("SEQ");   //관리번호
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_ZIP = dobj.getRetObject("R").getRecord().get("UPSO_ZIP");   //업소 우편번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_BSCON_CONTRINFO (BSCON_CD, BSCON_UPSO_CD, BSCON_UPSO_NM, SEQ, UPSO_CD, UPSO_ZIP, UPSO_ADDR1, UPSO_ADDR2, MONPRNCFEE, APPL_DAY, MATCH_GBN, USE_YN, INSPRES_ID, INS_DATE, BSTYP_CD, ATAX_YN, REMAK) SELECT BSCON_CD , BSCON_UPSO_CD , BSCON_UPSO_NM , :SEQ , :UPSO_CD , UPSO_ZIP , UPSO_ADDR1 , UPSO_ADDR2 , MONPRNCFEE , TO_CHAR(SYSDATE, 'YYYYMM') || '01' , (CASE WHEN :UPSO_CD IS NULL THEN 'N' ELSE 'Y' END) , 'Y' , :INSPRES_ID , SYSDATE , BSTYP_CD , ATAX_YN , :REMAK FROM GIBU.TBRA_BSCON_CONTRINFO A WHERE BSCON_CD = :BSCON_CD AND BSCON_UPSO_CD = :BSCON_UPSO_CD AND UPSO_ZIP = :UPSO_ZIP AND SEQ = (SELECT MAX(SEQ) FROM GIBU.TBRA_BSCON_CONTRINFO WHERE BSCON_CD = A.BSCON_CD AND BSCON_UPSO_CD = A.BSCON_UPSO_CD AND UPSO_ZIP = A.UPSO_ZIP)";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //타단체업소코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //업소 우편번호
        return sobj;
    }
    // 청구자료업소매칭
    public DOBJ CALLmng_match_bscon_XIUD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD11");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("XIUD11");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_match_bscon_XIUD11(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_match_bscon_XIUD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //거래처 코드
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //타단체업소코드
        String   DEMD_YRMN = dobj.getRetObject("R").getRecord().get("DEMD_YRMN");   //청구 년월
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //비고
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_BSCON_DEMD_UPLOAD  \n";
        query +=" SET UPSO_CD = :UPSO_CD , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE , REMAK = :REMAK , MATCH_GBN = (CASE WHEN :UPSO_CD IS NULL THEN 'N' ELSE 'Y' END)  \n";
        query +=" WHERE BSCON_CD = :BSCON_CD  \n";
        query +=" AND BSCON_UPSO_CD = :BSCON_UPSO_CD  \n";
        query +=" AND DEMD_YRMN = :DEMD_YRMN";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //타단체업소코드
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 미매칭 매칭으로 변경
    public DOBJ CALLmng_match_bscon_UPD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD15");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("UPD15");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_match_bscon_UPD15(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD15") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_match_bscon_UPD15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   BSCON_UPSO_CD = dvobj.getRecord().get("BSCON_UPSO_CD");   //타단체업소코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   MATCH_GBN = dvobj.getRecord().get("MATCH_GBN");   //매칭 구분
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   USE_YN ="Y";   //사용구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BSCON_CONTRINFO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MATCH_GBN=:MATCH_GBN , UPSO_CD=:UPSO_CD , MOD_DATE=SYSDATE  \n";
        query +=" where USE_YN=:USE_YN  \n";
        query +=" and BSCON_UPSO_CD=:BSCON_UPSO_CD  \n";
        query +=" and BSCON_CD=:BSCON_CD";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //타단체업소코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MATCH_GBN", MATCH_GBN);               //매칭 구분
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("USE_YN", USE_YN);               //사용구분
        return sobj;
    }
    //##**$$mng_match_bscon
    //##**$$end
}
