
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s21
{
    public bra10_s21()
    {
    }
    //##**$$sel_stomu_upso
    /*
    */
    public DOBJ CTLsel_stomu_upso(DOBJ dobj)
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
            dobj  = CALLsel_stomu_upso_SEL1(Conn, dobj);           //  매장음악사업자 업소조회
            dobj  = CALLsel_stomu_upso_SEL2(Conn, dobj);           //  업소통계
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
    public DOBJ CTLsel_stomu_upso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_stomu_upso_SEL1(Conn, dobj);           //  매장음악사업자 업소조회
        dobj  = CALLsel_stomu_upso_SEL2(Conn, dobj);           //  업소통계
        return dobj;
    }
    // 매장음악사업자 업소조회
    public DOBJ CALLsel_stomu_upso_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_stomu_upso_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_stomu_upso_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   STAT_GBN = dobj.getRetObject("S").getRecord().get("STAT_GBN");   //처리상태
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BRAN_CD , UPSO_CD , UPSO_NM , BSTYP_CD , UPSO_BRA , UPSO_ADDR , UPSO_NEW_ADDR1 , UPSO_NEW_ADDR2 , UPSO_REF_INFO , STAT_GBN , NEW_DAY , CLSED_DAY , REPPRES_NM , UPSO_PHON , BIOWN_NUM , SITE_AREA , GIBU.FT_GET_STOMU_MONPRNCFEE(A.UPSO_CD) AS MONPRNCFEE , GIBU.FT_GET_STOMU_LOCATION(UPSO_CD) AS LOC_GBN , BSCON_CD  ";
        query +=" FROM GIBU.TBRA_STOMU_UPSO A  ";
        query +=" WHERE BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD = :BSCON_CD  ";
        }
        if( !STAT_GBN.equals("") )
        {
            query +=" AND STAT_GBN = :STAT_GBN  ";
        }
        if( !UPSO_CD.equals("") )
        {
            query +=" AND UPSO_CD = :UPSO_CD  ";
        }
        query +=" ORDER BY BSCON_CD, BRAN_CD, BSTYP_CD, UPSO_CD  ";
        sobj.setSql(query);
        if(!UPSO_CD.equals(""))
        {
            sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        }
        if(!STAT_GBN.equals(""))
        {
            sobj.setString("STAT_GBN", STAT_GBN);               //처리상태
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        }
        return sobj;
    }
    // 업소통계
    public DOBJ CALLsel_stomu_upso_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_stomu_upso_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_stomu_upso_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT '영업' AS STAT_GBN , SUM(K_CNT) AS K_CNT , SUM(M_CNT) AS M_CNT , SUM(N_CNT) AS N_CNT , SUM(O_CNT) AS O_CNT , SUM(L_CNT) AS L_CNT , SUM(K_CNT) + SUM(M_CNT) + SUM(N_CNT) + SUM(O_CNT) + SUM(L_CNT) AS TOT_CNT , SUM(K_AMT) AS K_AMT , SUM(M_AMT) AS M_AMT , SUM(N_AMT) AS N_AMT , SUM(O_AMT) AS O_AMT , SUM(L_AMT) AS L_AMT , SUM(K_AMT) + SUM(M_AMT) + SUM(N_AMT) + SUM(O_AMT) + SUM(L_AMT) AS TOT_AMT  ";
        query +=" FROM (  ";
        query +=" SELECT DECODE(BSTYP_CD, 'K', 1, 0) AS K_CNT , DECODE(BSTYP_CD, 'M', 1, 0) AS M_CNT , DECODE(BSTYP_CD, 'N', 1, 0) AS N_CNT , DECODE(BSTYP_CD, 'O', 1, 0) AS O_CNT , DECODE(BSTYP_CD, 'L', 1, 0) AS L_CNT , 0 AS K_AMT , 0 AS M_AMT , 0 AS N_AMT , 0 AS O_AMT , 0 AS L_AMT  ";
        query +=" FROM GIBU.TBRA_STOMU_UPSO  ";
        query +=" WHERE BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" AND STAT_GBN = 'OPEN'  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD = :BSCON_CD  ";
        }
        query +=" UNION ALL  ";
        query +=" SELECT 0 AS K_CNT , 0 AS M_CNT , 0 AS N_CNT , 0 AS O_CNT , 0 AS L_CNT , DECODE(BSTYP_CD, 'K', GIBU.FT_GET_STOMU_MONPRNCFEE(UPSO_CD), 0) AS K_AMT , DECODE(BSTYP_CD, 'M', GIBU.FT_GET_STOMU_MONPRNCFEE(UPSO_CD), 0) AS M_AMT , DECODE(BSTYP_CD, 'N', GIBU.FT_GET_STOMU_MONPRNCFEE(UPSO_CD), 0) AS N_AMT , DECODE(BSTYP_CD, 'O', GIBU.FT_GET_STOMU_MONPRNCFEE(UPSO_CD), 0) AS O_AMT , DECODE(BSTYP_CD, 'L', GIBU.FT_GET_STOMU_MONPRNCFEE(UPSO_CD), 0) AS L_AMT  ";
        query +=" FROM GIBU.TBRA_STOMU_UPSO  ";
        query +=" WHERE BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" AND STAT_GBN = 'OPEN'  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD = :BSCON_CD  ";
        }
        query +=" )  ";
        query +=" UNION ALL  ";
        query +=" SELECT '폐업' AS STAT_GBN , SUM(K_CNT) AS K_CNT , SUM(M_CNT) AS M_CNT , SUM(N_CNT) AS N_CNT , SUM(O_CNT) AS O_CNT , SUM(L_CNT) AS L_CNT , SUM(K_CNT) + SUM(M_CNT) + SUM(N_CNT) + SUM(O_CNT) + SUM(L_CNT) AS TOT_CNT , SUM(K_AMT) AS K_AMT , SUM(M_AMT) AS M_AMT , SUM(N_AMT) AS N_AMT , SUM(O_AMT) AS O_AMT , SUM(L_AMT) AS L_AMT , SUM(K_AMT) + SUM(M_AMT) + SUM(N_AMT) + SUM(O_AMT) + SUM(L_AMT) AS TOT_AMT  ";
        query +=" FROM (  ";
        query +=" SELECT DECODE(BSTYP_CD, 'K', 1, 0) AS K_CNT , DECODE(BSTYP_CD, 'M', 1, 0) AS M_CNT , DECODE(BSTYP_CD, 'N', 1, 0) AS N_CNT , DECODE(BSTYP_CD, 'O', 1, 0) AS O_CNT , DECODE(BSTYP_CD, 'L', 1, 0) AS L_CNT , 0 AS K_AMT , 0 AS M_AMT , 0 AS N_AMT , 0 AS O_AMT , 0 AS L_AMT  ";
        query +=" FROM GIBU.TBRA_STOMU_UPSO  ";
        query +=" WHERE BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" AND STAT_GBN = 'CLOSE'  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD = :BSCON_CD  ";
        }
        query +=" UNION ALL  ";
        query +=" SELECT 0 AS K_CNT , 0 AS M_CNT , 0 AS N_CNT , 0 AS O_CNT , 0 AS L_CNT , DECODE(BSTYP_CD, 'K', GIBU.FT_GET_STOMU_MONPRNCFEE(UPSO_CD), 0) AS K_AMT , DECODE(BSTYP_CD, 'M', GIBU.FT_GET_STOMU_MONPRNCFEE(UPSO_CD), 0) AS M_AMT , DECODE(BSTYP_CD, 'N', GIBU.FT_GET_STOMU_MONPRNCFEE(UPSO_CD), 0) AS N_AMT , DECODE(BSTYP_CD, 'O', GIBU.FT_GET_STOMU_MONPRNCFEE(UPSO_CD), 0) AS O_AMT , DECODE(BSTYP_CD, 'L', GIBU.FT_GET_STOMU_MONPRNCFEE(UPSO_CD), 0) AS L_AMT  ";
        query +=" FROM GIBU.TBRA_STOMU_UPSO  ";
        query +=" WHERE BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" AND STAT_GBN = 'CLOSE'  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD = :BSCON_CD  ";
        }
        query +=" )  ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        }
        return sobj;
    }
    //##**$$sel_stomu_upso
    //##**$$mng_stomu_upso
    /*
    */
    public DOBJ CTLmng_stomu_upso(DOBJ dobj)
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
            dobj  = CALLmng_stomu_upso_MIUD1(Conn, dobj);           //  분기
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
    public DOBJ CTLmng_stomu_upso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_stomu_upso_MIUD1(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLmng_stomu_upso_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLmng_stomu_upso_SEL5(Conn, dobj);           //  업소중복조회
                if( dobj.getRetObject("SEL5").getRecord().getDouble("CNT") > 0)
                {
                    dobj.setRtncode(9);
                    if(dobj.getRtncode() == 9)
                    {
                        String message ="업소가 중복되었습니다. 작업을 취소합니다.";
                        dobj.setRetmsg(message);
                        Conn.rollback();
                        return dobj;
                    }
                }
                else
                {
                    dobj  = CALLmng_stomu_upso_INS8(Conn, dobj);           //  업소정보등록
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_stomu_upso_UPD11(Conn, dobj);           //  업소정보 수정
            }
        }
        return dobj;
    }
    // 업소중복조회
    public DOBJ CALLmng_stomu_upso_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_stomu_upso_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_stomu_upso_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_STOMU_UPSO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    // 업소정보 수정
    public DOBJ CALLmng_stomu_upso_UPD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD11");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_stomu_upso_UPD11(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD11") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_stomu_upso_UPD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   BIOWN_NUM = dvobj.getRecord().get("BIOWN_NUM");   //사업자 번호
        String   STAT_GBN = dvobj.getRecord().get("STAT_GBN");   //처리상태
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   UPSO_NM = dvobj.getRecord().get("UPSO_NM");   //업소 명
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_ADDR = dvobj.getRecord().get("UPSO_ADDR");   //업소 주소
        String   SITE_AREA = dvobj.getRecord().get("SITE_AREA");
        String   REPPRES_NM = dvobj.getRecord().get("REPPRES_NM");   //대표자 명
        String   NEW_DAY = dvobj.getRecord().get("NEW_DAY");   //신규 일자
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   UPSO_REF_INFO = dvobj.getRecord().get("UPSO_REF_INFO");
        String   CLSED_DAY = dvobj.getRecord().get("CLSED_DAY");   //휴업 일자
        String   UPSO_NEW_ADDR1 = dvobj.getRecord().get("UPSO_NEW_ADDR1");
        String   UPSO_PHON = dvobj.getRecord().get("UPSO_PHON");   //업소 전화
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //업종 코드
        String   UPSO_BRA = dvobj.getRecord().get("UPSO_BRA");   //지점명
        String   UPSO_NEW_ADDR2 = dvobj.getRecord().get("UPSO_NEW_ADDR2");
        String   UPSO_NEW_ZIP = dvobj.getRecord().get("UPSO_NEW_ZIP");
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_STOMU_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , UPSO_NEW_ZIP=:UPSO_NEW_ZIP , UPSO_NEW_ADDR2=:UPSO_NEW_ADDR2 , UPSO_BRA=:UPSO_BRA , BSTYP_CD=:BSTYP_CD , UPSO_PHON=:UPSO_PHON , UPSO_NEW_ADDR1=:UPSO_NEW_ADDR1 , CLSED_DAY=:CLSED_DAY , UPSO_REF_INFO=:UPSO_REF_INFO , NEW_DAY=:NEW_DAY , REPPRES_NM=:REPPRES_NM , SITE_AREA=:SITE_AREA , UPSO_ADDR=:UPSO_ADDR , MOD_DATE=SYSDATE , UPSO_NM=:UPSO_NM , BRAN_CD=:BRAN_CD , STAT_GBN=:STAT_GBN , BIOWN_NUM=:BIOWN_NUM  \n";
        query +=" where BSCON_CD=:BSCON_CD  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("BIOWN_NUM", BIOWN_NUM);               //사업자 번호
        sobj.setString("STAT_GBN", STAT_GBN);               //처리상태
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("UPSO_ADDR", UPSO_ADDR);               //업소 주소
        sobj.setString("SITE_AREA", SITE_AREA);
        sobj.setString("REPPRES_NM", REPPRES_NM);               //대표자 명
        sobj.setString("NEW_DAY", NEW_DAY);               //신규 일자
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("UPSO_REF_INFO", UPSO_REF_INFO);
        sobj.setString("CLSED_DAY", CLSED_DAY);               //휴업 일자
        sobj.setString("UPSO_NEW_ADDR1", UPSO_NEW_ADDR1);
        sobj.setString("UPSO_PHON", UPSO_PHON);               //업소 전화
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("UPSO_BRA", UPSO_BRA);               //지점명
        sobj.setString("UPSO_NEW_ADDR2", UPSO_NEW_ADDR2);
        sobj.setString("UPSO_NEW_ZIP", UPSO_NEW_ZIP);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 업소정보등록
    public DOBJ CALLmng_stomu_upso_INS8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_stomu_upso_INS8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_stomu_upso_INS8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   BIOWN_NUM = dvobj.getRecord().get("BIOWN_NUM");   //사업자 번호
        String   STAT_GBN = dvobj.getRecord().get("STAT_GBN");   //처리상태
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   UPSO_NM = dvobj.getRecord().get("UPSO_NM");   //업소 명
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_ADDR = dvobj.getRecord().get("UPSO_ADDR");   //업소 주소
        String   SITE_AREA = dvobj.getRecord().get("SITE_AREA");
        String   REPPRES_NM = dvobj.getRecord().get("REPPRES_NM");   //대표자 명
        String   NEW_DAY = dvobj.getRecord().get("NEW_DAY");   //신규 일자
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   UPSO_REF_INFO = dvobj.getRecord().get("UPSO_REF_INFO");
        String   CLSED_DAY = dvobj.getRecord().get("CLSED_DAY");   //휴업 일자
        String   UPSO_NEW_ADDR1 = dvobj.getRecord().get("UPSO_NEW_ADDR1");
        String   UPSO_PHON = dvobj.getRecord().get("UPSO_PHON");   //업소 전화
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //업종 코드
        String   UPSO_BRA = dvobj.getRecord().get("UPSO_BRA");   //지점명
        String   UPSO_NEW_ADDR2 = dvobj.getRecord().get("UPSO_NEW_ADDR2");
        String   UPSO_NEW_ZIP = dvobj.getRecord().get("UPSO_NEW_ZIP");
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_STOMU_UPSO (UPSO_NEW_ZIP, INSPRES_ID, UPSO_NEW_ADDR2, UPSO_BRA, BSTYP_CD, UPSO_PHON, UPSO_NEW_ADDR1, CLSED_DAY, UPSO_REF_INFO, BSCON_CD, INS_DATE, NEW_DAY, REPPRES_NM, SITE_AREA, UPSO_ADDR, UPSO_CD, UPSO_NM, BRAN_CD, STAT_GBN, BIOWN_NUM)  \n";
        query +=" values(:UPSO_NEW_ZIP , :INSPRES_ID , :UPSO_NEW_ADDR2 , :UPSO_BRA , :BSTYP_CD , :UPSO_PHON , :UPSO_NEW_ADDR1 , :CLSED_DAY , :UPSO_REF_INFO , :BSCON_CD , SYSDATE, :NEW_DAY , :REPPRES_NM , :SITE_AREA , :UPSO_ADDR , :UPSO_CD , :UPSO_NM , :BRAN_CD , :STAT_GBN , :BIOWN_NUM )";
        sobj.setSql(query);
        sobj.setString("BIOWN_NUM", BIOWN_NUM);               //사업자 번호
        sobj.setString("STAT_GBN", STAT_GBN);               //처리상태
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("UPSO_ADDR", UPSO_ADDR);               //업소 주소
        sobj.setString("SITE_AREA", SITE_AREA);
        sobj.setString("REPPRES_NM", REPPRES_NM);               //대표자 명
        sobj.setString("NEW_DAY", NEW_DAY);               //신규 일자
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("UPSO_REF_INFO", UPSO_REF_INFO);
        sobj.setString("CLSED_DAY", CLSED_DAY);               //휴업 일자
        sobj.setString("UPSO_NEW_ADDR1", UPSO_NEW_ADDR1);
        sobj.setString("UPSO_PHON", UPSO_PHON);               //업소 전화
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("UPSO_BRA", UPSO_BRA);               //지점명
        sobj.setString("UPSO_NEW_ADDR2", UPSO_NEW_ADDR2);
        sobj.setString("UPSO_NEW_ZIP", UPSO_NEW_ZIP);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    //##**$$mng_stomu_upso
    //##**$$sel_upso_memo
    /*
    */
    public DOBJ CTLsel_upso_memo(DOBJ dobj)
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
            dobj  = CALLsel_upso_memo_SEL1(Conn, dobj);           //  메모조회
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
    public DOBJ CTLsel_upso_memo( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_upso_memo_SEL1(Conn, dobj);           //  메모조회
        return dobj;
    }
    // 메모조회
    public DOBJ CALLsel_upso_memo_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_upso_memo_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_upso_memo_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MEMO_DAY  ,  MEMO_NUM  ,  MEMO  ,  FIDU.GET_STAFF_NM(INSPRES_ID)  AS  INSPRES_NM  FROM  GIBU.TBRA_STOMU_UPSO_MEMO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    //##**$$sel_upso_memo
    //##**$$mng_upso_memo
    /*
    */
    public DOBJ CTLmng_upso_memo(DOBJ dobj)
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
            dobj  = CALLmng_upso_memo_MIUD1(Conn, dobj);           //  분기
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
    public DOBJ CTLmng_upso_memo( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_upso_memo_MIUD1(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLmng_upso_memo_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLmng_upso_memo_SEL5(Conn, dobj);           //  memo_num 채번
                dobj  = CALLmng_upso_memo_XIUD9(Conn, dobj);           //  메모등록
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_upso_memo_UPD7(Conn, dobj);           //  메모수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_upso_memo_DEL8(Conn, dobj);           //  메모삭제
            }
        }
        return dobj;
    }
    // 메모삭제
    public DOBJ CALLmng_upso_memo_DEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_upso_memo_DEL8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_upso_memo_DEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MEMO_DAY = dvobj.getRecord().get("MEMO_DAY");   //메모 일자
        String   MEMO_NUM = dvobj.getRecord().get("MEMO_NUM");   //메모 번호
        String   BSCON_CD = dobj.getRetObject("S1").getRecord().get("BSCON_CD");   //거래처 코드
        String   UPSO_CD = dobj.getRetObject("S1").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_STOMU_UPSO_MEMO  \n";
        query +=" where MEMO_NUM=:MEMO_NUM  \n";
        query +=" and MEMO_DAY=:MEMO_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and BSCON_CD=:BSCON_CD";
        sobj.setSql(query);
        sobj.setString("MEMO_DAY", MEMO_DAY);               //메모 일자
        sobj.setString("MEMO_NUM", MEMO_NUM);               //메모 번호
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 메모수정
    public DOBJ CALLmng_upso_memo_UPD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_upso_memo_UPD7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_upso_memo_UPD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   MEMO_DAY = dvobj.getRecord().get("MEMO_DAY");   //메모 일자
        String   MEMO_NUM = dvobj.getRecord().get("MEMO_NUM");   //메모 번호
        String   MEMO = dvobj.getRecord().get("MEMO");   //메모
        String   BSCON_CD = dobj.getRetObject("S1").getRecord().get("BSCON_CD");   //거래처 코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   UPSO_CD = dobj.getRetObject("S1").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_STOMU_UPSO_MEMO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MEMO=:MEMO , MOD_DATE=SYSDATE  \n";
        query +=" where MEMO_NUM=:MEMO_NUM  \n";
        query +=" and MEMO_DAY=:MEMO_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and BSCON_CD=:BSCON_CD";
        sobj.setSql(query);
        sobj.setString("MEMO_DAY", MEMO_DAY);               //메모 일자
        sobj.setString("MEMO_NUM", MEMO_NUM);               //메모 번호
        sobj.setString("MEMO", MEMO);               //메모
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // memo_num 채번
    public DOBJ CALLmng_upso_memo_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_upso_memo_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_upso_memo_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S1").getRecord().get("UPSO_CD");   //업소 코드
        String   BSCON_CD = dobj.getRetObject("S1").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(MEMO_NUM),  0)  +  1  AS  MEMO_NUM  FROM  GIBU.TBRA_STOMU_UPSO_MEMO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  MEMO_DAY  =  TO_DATE(SYSDATE,  'YYYYMMDD') ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    // 메모등록
    public DOBJ CALLmng_upso_memo_XIUD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD9");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_upso_memo_XIUD9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_upso_memo_XIUD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("S1").getRecord().get("BSCON_CD");   //거래처 코드
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   MEMO = dobj.getRetObject("R").getRecord().get("MEMO");   //메모
        String   MEMO_NUM = dobj.getRetObject("SEL5").getRecord().get("MEMO_NUM");   //메모 번호
        String   UPSO_CD = dobj.getRetObject("S1").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_STOMU_UPSO_MEMO( BSCON_CD , UPSO_CD , MEMO_DAY , MEMO_NUM , MEMO , INSPRES_ID , INS_DATE ) SELECT :BSCON_CD , :UPSO_CD , TO_CHAR(SYSDATE, 'YYYYMMDD') , :MEMO_NUM , :MEMO , :INSPRES_ID , SYSDATE FROM DUAL";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("MEMO", MEMO);               //메모
        sobj.setString("MEMO_NUM", MEMO_NUM);               //메모 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$mng_upso_memo
    //##**$$end
}
