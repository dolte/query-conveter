
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s22
{
    public bra10_s22()
    {
    }
    //##**$$up_stomu_rept
    /*
    */
    public DOBJ CTLup_stomu_rept(DOBJ dobj)
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
            dobj  = CALLup_stomu_rept_MPD2(Conn, dobj);           //  분기
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
    public DOBJ CTLup_stomu_rept( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLup_stomu_rept_MPD2(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLup_stomu_rept_MPD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD2");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( 1 == 1)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLup_stomu_rept_SEL5(Conn, dobj);           //  업소등록여부 확인
                if( dobj.getRetObject("SEL5").getRecord().getDouble("CNT") > 0)
                {
                    dobj  = CALLup_stomu_rept_XIUD1(Conn, dobj);           //  엑셀업로드
                }
                else
                {
                    dobj.setRtncode(9);
                    if(dobj.getRtncode() == 9)
                    {
                        String message ="업소가 등록되지 않았습니다. 확인해주시기바랍니다. 작업을 취소합니다.";
                        dobj.setRetmsg(message);
                        Conn.rollback();
                        return dobj;
                    }
                }
            }
        }
        return dobj;
    }
    // 업소등록여부 확인
    public DOBJ CALLup_stomu_rept_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLup_stomu_rept_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLup_stomu_rept_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   BSCON_CD = dobj.getRetObject("S1").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_STOMU_UPSO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    // 엑셀업로드
    public DOBJ CALLup_stomu_rept_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLup_stomu_rept_XIUD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLup_stomu_rept_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("S1").getRecord().get("BSCON_CD");   //거래처 코드
        double   DEMD_AMT = dobj.getRetObject("R").getRecord().getDouble("DEMD_AMT");   //청구 금액
        String   DEMD_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");   //청구 일자
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //비고
        double   REPT_AMT = dobj.getRetObject("R").getRecord().getDouble("REPT_AMT");   //입금 금액
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //입금일자
        String   UPLOD_DAY = dobj.getRetObject("S1").getRecord().get("UPLOD_DAY");   //업로드 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_STOMU_REPT( BSCON_CD , UPSO_CD , BRAN_CD , DEMD_YRMN , DEMD_DAY , DEMD_AMT , REPT_YRMN , REPT_DAY , REPT_AMT , REMAK , UPLOD_DAY , INSPRES_ID , INS_DATE ) SELECT :BSCON_CD , :UPSO_CD , (SELECT BRAN_CD FROM GIBU.TBRA_STOMU_UPSO WHERE BSCON_CD = :BSCON_CD AND UPSO_CD = :UPSO_CD) , SUBSTR(:DEMD_DAY, 1, 6) , :DEMD_DAY , :DEMD_AMT , SUBSTR(:REPT_DAY, 1, 6) , :REPT_DAY , :REPT_AMT , :REMAK , :UPLOD_DAY , :INSPRES_ID , SYSDATE FROM DUAL";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setDouble("DEMD_AMT", DEMD_AMT);               //청구 금액
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setDouble("REPT_AMT", REPT_AMT);               //입금 금액
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("UPLOD_DAY", UPLOD_DAY);               //업로드 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$up_stomu_rept
    //##**$$mng_stomu_rept
    /*
    */
    public DOBJ CTLmng_stomu_rept(DOBJ dobj)
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
            dobj  = CALLmng_stomu_rept_MIUD1(Conn, dobj);           //  분기
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
    public DOBJ CTLmng_stomu_rept( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_stomu_rept_MIUD1(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLmng_stomu_rept_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLmng_stomu_rept_XIUD5(Conn, dobj);           //  입금내역(비고)수정
            }
        }
        return dobj;
    }
    // 입금내역(비고)수정
    public DOBJ CALLmng_stomu_rept_XIUD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD5");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_stomu_rept_XIUD5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_stomu_rept_XIUD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //거래처 코드
        String   DEMD_YRMN = dobj.getRetObject("R").getRecord().get("DEMD_YRMN");   //청구 년월
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //비고
        String   REPT_YRMN = dobj.getRetObject("R").getRecord().get("REPT_YRMN");   //입금 년월
        String   UPLOD_DAY = dobj.getRetObject("R").getRecord().get("UPLOD_DAY");   //업로드 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_STOMU_REPT  \n";
        query +=" SET REMAK = :REMAK , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE BSCON_CD = :BSCON_CD  \n";
        query +=" AND UPSO_CD = :UPSO_CD  \n";
        query +=" AND DEMD_YRMN = :DEMD_YRMN  \n";
        query +=" AND REPT_YRMN = :REPT_YRMN  \n";
        query +=" AND UPLOD_DAY = :UPLOD_DAY";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("UPLOD_DAY", UPLOD_DAY);               //업로드 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$mng_stomu_rept
    //##**$$del_stomu_rept
    /*
    */
    public DOBJ CTLdel_stomu_rept(DOBJ dobj)
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
            dobj  = CALLdel_stomu_rept_DEL1(Conn, dobj);           //  입금내역 삭제
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
    public DOBJ CTLdel_stomu_rept( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdel_stomu_rept_DEL1(Conn, dobj);           //  입금내역 삭제
        return dobj;
    }
    // 입금내역 삭제
    public DOBJ CALLdel_stomu_rept_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdel_stomu_rept_DEL1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdel_stomu_rept_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPLOD_DAY = dvobj.getRecord().get("UPLOD_DAY");   //업로드 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_STOMU_REPT  \n";
        query +=" where UPLOD_DAY=:UPLOD_DAY";
        sobj.setSql(query);
        sobj.setString("UPLOD_DAY", UPLOD_DAY);               //업로드 일자
        return sobj;
    }
    //##**$$del_stomu_rept
    //##**$$sel_stomu_rept
    /*
    */
    public DOBJ CTLsel_stomu_rept(DOBJ dobj)
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
            dobj  = CALLsel_stomu_rept_SEL1(Conn, dobj);           //  입금내역조회
            dobj  = CALLsel_stomu_rept_SEL2(Conn, dobj);           //  입금내역통계
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
    public DOBJ CTLsel_stomu_rept( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_stomu_rept_SEL1(Conn, dobj);           //  입금내역조회
        dobj  = CALLsel_stomu_rept_SEL2(Conn, dobj);           //  입금내역통계
        return dobj;
    }
    // 입금내역조회
    public DOBJ CALLsel_stomu_rept_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_stomu_rept_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_stomu_rept_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.BRAN_CD , A.UPSO_CD , A.DEMD_YRMN , B.BSTYP_CD , B.UPSO_NM , B.UPSO_BRA , B.REPPRES_NM , B.UPSO_PHON , A.DEMD_DAY , A.DEMD_AMT , A.REPT_YRMN , A.REPT_DAY , A.REPT_AMT , A.REMAK , A.BSCON_CD , A.UPLOD_DAY  ";
        query +=" FROM GIBU.TBRA_STOMU_REPT A , GIBU.TBRA_STOMU_UPSO B  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND ( (:GBN = 'D'  ";
        query +=" AND A.DEMD_YRMN BETWEEN :START_YRMN  ";
        query +=" AND :END_YRMN)  ";
        query +=" OR (:GBN = 'R'  ";
        query +=" AND A.REPT_YRMN BETWEEN :START_YRMN  ";
        query +=" AND :END_YRMN))  ";
        query +=" AND A.BRAN_CD = DECODE(:BRAN_CD, 'AL', B.BRAN_CD, :BRAN_CD)  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND B.BSCON_CD = :BSCON_CD  ";
        }
        if( !UPSO_CD.equals("") )
        {
            query +=" AND B.UPSO_CD = :UPSO_CD  ";
        }
        query +=" ORDER BY A.DEMD_DAY, A.BSCON_CD, A.BRAN_CD, B.BSTYP_CD, A.UPSO_CD  ";
        sobj.setSql(query);
        sobj.setString("GBN", GBN);               //구분
        if(!UPSO_CD.equals(""))
        {
            sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        }
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        }
        return sobj;
    }
    // 입금내역통계
    public DOBJ CALLsel_stomu_rept_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_stomu_rept_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_stomu_rept_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT REPT_YRMN , SUM(K_CNT) AS K_CNT , SUM(M_CNT) AS M_CNT , SUM(N_CNT) AS N_CNT , SUM(O_CNT) AS O_CNT , SUM(L_CNT) AS L_CNT , SUM(K_CNT) + SUM(M_CNT) + SUM(N_CNT) + SUM(O_CNT) + SUM(L_CNT) AS TOT_CNT , SUM(K_AMT) AS K_AMT , SUM(M_AMT) AS M_AMT , SUM(N_AMT) AS N_AMT , SUM(O_AMT) AS O_AMT , SUM(L_AMT) AS L_AMT , SUM(K_AMT) + SUM(M_AMT) + SUM(N_AMT) + SUM(O_AMT) + SUM(L_AMT) AS TOT_AMT  ";
        query +=" FROM (  ";
        query +=" SELECT A.REPT_YRMN , DECODE(BSTYP_CD, 'K', 1, 0) AS K_CNT , DECODE(BSTYP_CD, 'M', 1, 0) AS M_CNT , DECODE(BSTYP_CD, 'N', 1, 0) AS N_CNT , DECODE(BSTYP_CD, 'O', 1, 0) AS O_CNT , DECODE(BSTYP_CD, 'L', 1, 0) AS L_CNT , 0 AS K_AMT , 0 AS M_AMT , 0 AS N_AMT , 0 AS O_AMT , 0 AS L_AMT  ";
        query +=" FROM GIBU.TBRA_STOMU_REPT A , GIBU.TBRA_STOMU_UPSO B  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND B.BRAN_CD = DECODE(:BRAN_CD, 'AL', B.BRAN_CD, :BRAN_CD)  ";
        query +=" AND A.REPT_YRMN BETWEEN :START_YRMN  ";
        query +=" AND :END_YRMN  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND B.BSCON_CD = :BSCON_CD  ";
        }
        query +=" UNION ALL  ";
        query +=" SELECT A.REPT_YRMN , 0 AS K_CNT , 0 AS M_CNT , 0 AS N_CNT , 0 AS O_CNT , 0 AS L_CNT , DECODE(BSTYP_CD, 'K', REPT_AMT, 0) AS K_AMT , DECODE(BSTYP_CD, 'M', REPT_AMT, 0) AS M_AMT , DECODE(BSTYP_CD, 'N', REPT_AMT, 0) AS N_AMT , DECODE(BSTYP_CD, 'O', REPT_AMT, 0) AS O_AMT , DECODE(BSTYP_CD, 'L', REPT_AMT, 0) AS L_AMT  ";
        query +=" FROM GIBU.TBRA_STOMU_REPT A , GIBU.TBRA_STOMU_UPSO B  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND B.BRAN_CD = DECODE(:BRAN_CD, 'AL', B.BRAN_CD, :BRAN_CD)  ";
        query +=" AND A.REPT_YRMN BETWEEN :START_YRMN  ";
        query +=" AND :END_YRMN  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND B.BSCON_CD = :BSCON_CD  ";
        }
        query +=" )  ";
        query +=" GROUP BY REPT_YRMN  ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        }
        return sobj;
    }
    //##**$$sel_stomu_rept
    //##**$$chk_stomu_rept
    /*
    */
    public DOBJ CTLchk_stomu_rept(DOBJ dobj)
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
            dobj  = CALLchk_stomu_rept_SEL1(Conn, dobj);           //  중복확인
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
    public DOBJ CTLchk_stomu_rept( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLchk_stomu_rept_SEL1(Conn, dobj);           //  중복확인
        return dobj;
    }
    // 중복확인
    public DOBJ CALLchk_stomu_rept_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLchk_stomu_rept_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_stomu_rept_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPLOD_DAY = dobj.getRetObject("S").getRecord().get("UPLOD_DAY");   //업로드 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_STOMU_REPT  WHERE  UPLOD_DAY  =  :UPLOD_DAY ";
        sobj.setSql(query);
        sobj.setString("UPLOD_DAY", UPLOD_DAY);               //업로드 일자
        return sobj;
    }
    //##**$$chk_stomu_rept
    //##**$$end
}
