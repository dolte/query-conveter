
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac09_r02
{
    public tac09_r02()
    {
    }
    //##**$$transFile1
    /* * 프로그램명 : tac09_r02
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtransFile1(DOBJ dobj)
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
            dobj  = CALLtransFile1_DEL2(Conn, dobj);           //  국민은행파일 삭제
            dobj  = CALLtransFile1_XIUD3(Conn, dobj);           //  국민은행 생성
            dobj  = CALLtransFile1_SEL1(Conn, dobj);           //  은행이체파일조회
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
    public DOBJ CTLtransFile1( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtransFile1_DEL2(Conn, dobj);           //  국민은행파일 삭제
        dobj  = CALLtransFile1_XIUD3(Conn, dobj);           //  국민은행 생성
        dobj  = CALLtransFile1_SEL1(Conn, dobj);           //  은행이체파일조회
        return dobj;
    }
    // 국민은행파일 삭제
    public DOBJ CALLtransFile1_DEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtransFile1_DEL2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtransFile1_DEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_BANKFILE  \n";
        query +=" where SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 국민은행 생성
    public DOBJ CALLtransFile1_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD3");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtransFile1_XIUD3(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        rvobj.Println("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtransFile1_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INS_STAFF = dobj.getRetObject("G").getRecord().get("USERID");   //등록 사원
        String   SUPP_DAY = dobj.getRetObject("S").getRecord().get("SUPP_DAY");   //지급 일자
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.TTAC_BANKFILE SELECT G.SUPP_YRMN , G.TRNSF_GBN , G.MB_CD , ROWNUM AS SEQ , G.SUPP_DAY , G.NM , G.ACCN_NUM , G.BANK_CD , G.DEPOSITOR_NM , G.SUPP_AMT , G.CLAIMPRES_CD , G.CLAIMPRES_NM , G.PTTN_RATE , G.SUPP_YN , G.INSPRES_ID , G.INS_DATE , G.MODPRES_ID , G.MOD_DATE, NULL FROM( SELECT distinct A.SUPP_YRMN AS SUPP_YRMN, A.TRNSF_GBN AS TRNSF_GBN, A.MB_CD AS MB_CD, :SUPP_DAY AS SUPP_DAY, B.HANMB_NM AS NM, B.SUPPACCN_NUM AS ACCN_NUM, B.SUPPBANK_CD AS BANK_CD, B.HANMB_NM AS DEPOSITOR_NM, TRUNC(A.TOT_REALSUPP_AMT - NVL(A.TOT_REPAY_AMT,0)) AS SUPP_AMT, '' AS CLAIMPRES_CD , '' AS CLAIMPRES_NM , 0 AS PTTN_RATE, 'Y' AS SUPP_YN, :INS_STAFF AS INSPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS INS_DATE, :INS_STAFF AS MODPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS MOD_DATE FROM FIDU.TTAC_COPYRTAL A, FIDU.TMEM_MB B WHERE A.MB_CD=B.MB_CD AND A.SUPP_YRMN = :SUPP_YRMN UNION ALL SELECT E.SUPP_YRMN AS SUPP_YRMN, E.TRNSF_GBN AS TRNSF_GBN, E.CLAIMPRES_CD AS MB_CD, :SUPP_DAY AS SUPP_DAY, E.NM AS NM, E.CLAIMPRES_ACCN_NUM AS ACCN_NUM, E.CLAIMPRES_BANK_CD AS BANK_CD, E.DEPOSITOR_NM AS DEPOSITOR_NM, E.OCC_AMT AS SUPP_AMT, E.MB_CD AS CLAIMPRES_CD , E.CLAIMPRES_NM , E.CLAIM_PTTNRATE AS PTTN_RATE, 'Y' AS SUPP_YN, E.INSPRES_ID, E.INS_DATE, E.MODPRES_ID, E.MOD_DATE FROM ( SELECT SUBSTR(A.REPAY_YRMN,1,6) AS SUPP_YRMN, A.TRNSF_GBN, A.MB_CD, C.BSCONHAN_NM AS NM, C.BSCONHAN_NM AS DEPOSITOR_NM , B.CLAIMPRES_MB_CD AS CLAIMPRES_CD, D.HANMB_NM AS CLAIMPRES_NM, SUM(A.REPAY_AMT) AS OCC_AMT, A.REMAK, B.CLAIMPRES_ACCN_NUM , B.CLAIMPRES_BANK_CD, B.CLAIM_PTTNRATE, 'Y' AS SUPP_YN, :INS_STAFF AS INSPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS INS_DATE, :INS_STAFF AS MODPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS MOD_DATE FROM FIDU.TMEM_CLAIMDEBTREPAY A ,FIDU.TMEM_CLAIMDEBT B ,FIDU.TLEV_BSCON C ,FIDU.TMEM_MB D WHERE 1=1 AND A.MB_CD = B.MB_CD AND A.MNG_NUM = B.MNG_NUM AND A.MB_CD = D.MB_CD AND LENGTH(B.CLAIMPRES_MB_CD) > 0 AND B.CLAIMPRES_MB_CD = C.BSCON_CD AND A.DEDCTPROC_YN = 'Y' AND SUBSTR(A.REPAY_YRMN,1,6) = :SUPP_YRMN GROUP BY SUBSTR(A.REPAY_YRMN,1,6),A.TRNSF_GBN,A.MB_CD ,C.BSCONHAN_NM,B.CLAIMPRES_MB_CD, D.HANMB_NM,A.REMAK ,B.CLAIMPRES_ACCN_NUM , B.CLAIMPRES_BANK_CD ,B.CLAIM_PTTNRATE ) E UNION ALL SELECT F.SUPP_YRMN AS SUPP_YRMN, F.TRNSF_GBN AS TRNSF_GBN, F.CLAIMPRES_CD AS MB_CD, :SUPP_DAY AS SUPP_DAY, F.NM AS NM, F.ACCN_NUM, F.BANK_CD, F.DEPOSITOR_NM AS DEPOSITOR_NM, F.OCC_AMT AS SUPP_AMT, F.MB_CD AS CLAIMPRES_CD , F.CLAIMPRES_NM , 0 AS PTTN_RATE, 'Y' AS SUPP_YN, F.INSPRES_ID, F.INS_DATE, F.MODPRES_ID, F.MOD_DATE FROM (SELECT SUBSTR(A.SUPP_YRMN,1,6) AS SUPP_YRMN, A.TRNSF_GBN , A.MB_CD , B.HANMB_NM AS NM, B.HANMB_NM AS DEPOSITOR_NM , A.SUPP_MB_CD AS CLAIMPRES_CD, C.BSCONHAN_NM AS CLAIMPRES_NM, SUM(OCC_AMT) AS OCC_AMT , A.SUPP_MB_BANK_CD AS BANK_CD , A.SUPP_ACCN_NUM AS ACCN_NUM, :INS_STAFF AS INSPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS INS_DATE, :INS_STAFF AS MODPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS MOD_DATE FROM FIDU.TTAC_PLEDAMT A ,FIDU.TMEM_MB B , FIDU.TLEV_BSCON C WHERE 1=1 AND A.MB_CD = B.MB_CD AND A.SUPP_MB_CD = C.BSCON_CD AND LENGTH(A.SUPP_MB_CD) > 0 AND SUBSTR(A.SUPP_YRMN,1,6) = :SUPP_YRMN GROUP BY SUBSTR(A.SUPP_YRMN,1,6), A.TRNSF_GBN , A.MB_CD ,B.HANMB_NM, A.SUPP_MB_CD, C.BSCONHAN_NM ,A.SUPP_MB_BANK_CD, A.SUPP_ACCN_NUM) F ) G WHERE SUPP_AMT <> 0 ORDER BY G.NM";
        sobj.setSql(query);
        sobj.setString("INS_STAFF", INS_STAFF);               //등록 사원
        sobj.setString("SUPP_DAY", SUPP_DAY);               //지급 일자
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 은행이체파일조회
    public DOBJ CALLtransFile1_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtransFile1_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtransFile1_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //은행 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BANK_CD  AS  AA,  NM  AS  BB,  NVL(ACCN_NUM,  '  ')  AS  CC,  SUPP_AMT  AS  DD,  '한음저협저작권료'  AS  EE  FROM  FIDU.TTAC_BANKFILE  WHERE  SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  SUPP_YN  =  'Y'   \n";
        query +=" AND  BANK_CD  =  :BANK_CD   \n";
        query +=" AND  LENGTH(TRIM(ACCN_NUM))  >  0  ORDER  BY  NM ";
        sobj.setSql(query);
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$transFile1
    //##**$$transFile3
    /* * 프로그램명 : tac09_r02
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtransFile3(DOBJ dobj)
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
            dobj  = CALLtransFile3_DEL2(Conn, dobj);           //  신한농협외환삭제
            dobj  = CALLtransFile3_XIUD3(Conn, dobj);           //  신한농협외환저장
            dobj  = CALLtransFile3_SEL1(Conn, dobj);           //  은행이체파일조회
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
    public DOBJ CTLtransFile3( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtransFile3_DEL2(Conn, dobj);           //  신한농협외환삭제
        dobj  = CALLtransFile3_XIUD3(Conn, dobj);           //  신한농협외환저장
        dobj  = CALLtransFile3_SEL1(Conn, dobj);           //  은행이체파일조회
        return dobj;
    }
    // 신한농협외환삭제
    public DOBJ CALLtransFile3_DEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtransFile3_DEL2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtransFile3_DEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        String   BANK_CD = dvobj.getRecord().get("BANK_CD");   //은행 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_BANKFILE  \n";
        query +=" where BANK_CD=:BANK_CD  \n";
        query +=" and SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        return sobj;
    }
    // 신한농협외환저장
    public DOBJ CALLtransFile3_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtransFile3_XIUD3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtransFile3_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //은행 코드
        String   INS_STAFF = dobj.getRetObject("G").getRecord().get("USERID");   //등록 사원
        String   SUPP_DAY = dobj.getRetObject("S").getRecord().get("SUPP_DAY");   //지급 일자
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.TTAC_BANKFILE SELECT G.SUPP_YRMN , G.TRNSF_GBN , G.MB_CD , ROWNUM AS SEQ , G.SUPP_DAY , G.NM , G.ACCN_NUM , G.BANK_CD , G.DEPOSITOR_NM , G.SUPP_AMT , G.CLAIMPRES_CD , G.CLAIMPRES_NM , G.PTTN_RATE , G.SUPP_YN , G.INSPRES_ID , G.INS_DATE , G.MODPRES_ID , G.MOD_DATE, 'N' FROM( SELECT distinct A.SUPP_YRMN AS SUPP_YRMN, A.TRNSF_GBN AS TRNSF_GBN, A.MB_CD AS MB_CD, :SUPP_DAY AS SUPP_DAY, B.HANMB_NM AS NM, B.SUPPACCN_NUM AS ACCN_NUM, B.SUPPBANK_CD AS BANK_CD, B.HANMB_NM AS DEPOSITOR_NM, trunc(A.TOT_REALSUPP_AMT - NVL(A.TOT_REPAY_AMT,0)) AS SUPP_AMT, '' AS CLAIMPRES_CD , '' AS CLAIMPRES_NM , 0 AS PTTN_RATE, 'Y' AS SUPP_YN, :INS_STAFF AS INSPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS INS_DATE, :INS_STAFF AS MODPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS MOD_DATE FROM FIDU.TTAC_COPYRTAL A, FIDU.TMEM_MB B WHERE A.MB_CD=B.MB_CD AND A.SUPP_YRMN = :SUPP_YRMN AND B.SUPPBANK_CD = :BANK_CD UNION ALL SELECT E.SUPP_YRMN AS SUPP_YRMN, E.TRNSF_GBN AS TRNSF_GBN, E.CLAIMPRES_CD AS MB_CD, :SUPP_DAY AS SUPP_DAY, E.NM AS NM, E.CLAIMPRES_ACCN_NUM AS ACCN_NUM, E.CLAIMPRES_BANK_CD AS BANK_CD, E.DEPOSITOR_NM AS DEPOSITOR_NM, E.OCC_AMT AS SUPP_AMT, E.MB_CD AS CLAIMPRES_CD , E.CLAIMPRES_NM , E.CLAIM_PTTNRATE AS PTTN_RATE, 'Y' AS SUPP_YN, E.INSPRES_ID, E.INS_DATE, E.MODPRES_ID, E.MOD_DATE FROM ( SELECT SUBSTR(A.REPAY_YRMN,1,6) AS SUPP_YRMN, A.TRNSF_GBN, A.MB_CD, C.BSCONHAN_NM AS NM, C.BSCONHAN_NM AS DEPOSITOR_NM , B.CLAIMPRES_MB_CD AS CLAIMPRES_CD, D.HANMB_NM AS CLAIMPRES_NM, SUM(A.REPAY_AMT) AS OCC_AMT, A.REMAK, B.CLAIMPRES_ACCN_NUM , B.CLAIMPRES_BANK_CD, B.CLAIM_PTTNRATE, 'Y' AS SUPP_YN, :INS_STAFF AS INSPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS INS_DATE, :INS_STAFF AS MODPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS MOD_DATE FROM FIDU.TMEM_CLAIMDEBTREPAY A ,FIDU.TMEM_CLAIMDEBT B ,FIDU.TLEV_BSCON C ,FIDU.TMEM_MB D WHERE 1=1 AND A.MB_CD = B.MB_CD AND A.MNG_NUM = B.MNG_NUM AND A.MB_CD = D.MB_CD AND LENGTH(B.CLAIMPRES_MB_CD) > 0 AND B.CLAIMPRES_MB_CD = C.BSCON_CD AND B.CLAIMPRES_BANK_CD = :BANK_CD AND A.DEDCTPROC_YN = 'Y' AND SUBSTR(A.REPAY_YRMN,1,6) = :SUPP_YRMN GROUP BY SUBSTR(A.REPAY_YRMN,1,6),A.TRNSF_GBN,A.MB_CD ,C.BSCONHAN_NM,B.CLAIMPRES_MB_CD, D.HANMB_NM,A.REMAK ,B.CLAIMPRES_ACCN_NUM , B.CLAIMPRES_BANK_CD ,B.CLAIM_PTTNRATE ) E UNION ALL SELECT F.SUPP_YRMN AS SUPP_YRMN, F.TRNSF_GBN AS TRNSF_GBN, F.CLAIMPRES_CD AS MB_CD, :SUPP_DAY AS SUPP_DAY, F.NM AS NM, F.ACCN_NUM, F.BANK_CD, F.DEPOSITOR_NM AS DEPOSITOR_NM, F.OCC_AMT AS SUPP_AMT, F.MB_CD AS CLAIMPRES_CD , F.CLAIMPRES_NM , 0 AS PTTN_RATE, 'Y' AS SUPP_YN, F.INSPRES_ID, F.INS_DATE, F.MODPRES_ID, F.MOD_DATE FROM ( SELECT SUBSTR(A.SUPP_YRMN,1,6) AS SUPP_YRMN, A.TRNSF_GBN , A.MB_CD , B.HANMB_NM AS NM, B.HANMB_NM AS DEPOSITOR_NM , A.SUPP_MB_CD AS CLAIMPRES_CD, C.BSCONHAN_NM AS CLAIMPRES_NM, SUM(OCC_AMT) AS OCC_AMT , A.SUPP_MB_BANK_CD AS BANK_CD , A.SUPP_ACCN_NUM AS ACCN_NUM, :INS_STAFF AS INSPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS INS_DATE, :INS_STAFF AS MODPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS MOD_DATE FROM FIDU.TTAC_PLEDAMT A ,FIDU.TMEM_MB B , FIDU.TLEV_BSCON C WHERE 1=1 AND A.MB_CD = B.MB_CD AND A.SUPP_MB_CD = C.BSCON_CD AND LENGTH(A.SUPP_MB_CD) > 0 AND A.SUPP_MB_BANK_CD = :BANK_CD AND SUBSTR(A.SUPP_YRMN,1,6) = :SUPP_YRMN GROUP BY SUBSTR(A.SUPP_YRMN,1,6), A.TRNSF_GBN , A.MB_CD ,B.HANMB_NM, A.SUPP_MB_CD,C.BSCONHAN_NM ,A.SUPP_MB_BANK_CD, A.SUPP_ACCN_NUM) F ) G WHERE SUPP_AMT <> 0 ORDER BY G.NM";
        sobj.setSql(query);
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("INS_STAFF", INS_STAFF);               //등록 사원
        sobj.setString("SUPP_DAY", SUPP_DAY);               //지급 일자
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 은행이체파일조회
    public DOBJ CALLtransFile3_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtransFile3_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtransFile3_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //은행 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(BANK_CD,'088','088','005','005','011','011','081','081','')  AS  AA,  NM  AS  BB,  NVL(ACCN_NUM,  '  ')  AS  CC,  SUPP_AMT  AS  DD,  '한음저협저작권료'  AS  EE  FROM  FIDU.TTAC_BANKFILE  WHERE  SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  SUPP_YN  =  'Y'   \n";
        query +=" AND  BANK_CD  =  :BANK_CD   \n";
        query +=" AND  LENGTH(TRIM(ACCN_NUM))  >  0  ORDER  BY  NM ";
        sobj.setSql(query);
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$transFile3
    //##**$$CYBER_BRANCH
    /*
    */
    public DOBJ CTLCYBER_BRANCH(DOBJ dobj)
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
            dobj  = CALLCYBER_BRANCH_SEL2(Conn, dobj);           //  SEL
            dobj  = CALLCYBER_BRANCH_XIUD1(Conn, dobj);           //  XIUD
            dobj  = CALLCYBER_BRANCH_XIUD3(Conn, dobj);           //  XIUD
            dobj  = CALLCYBER_BRANCH_XIUD5(Conn, dobj);           //  XIUD
            dobj  = CALLCYBER_BRANCH_SEL4(Conn, dobj);           //  SEL
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
    public DOBJ CTLCYBER_BRANCH( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLCYBER_BRANCH_SEL2(Conn, dobj);           //  SEL
        dobj  = CALLCYBER_BRANCH_XIUD1(Conn, dobj);           //  XIUD
        dobj  = CALLCYBER_BRANCH_XIUD3(Conn, dobj);           //  XIUD
        dobj  = CALLCYBER_BRANCH_XIUD5(Conn, dobj);           //  XIUD
        dobj  = CALLCYBER_BRANCH_SEL4(Conn, dobj);           //  SEL
        return dobj;
    }
    // SEL
    public DOBJ CALLCYBER_BRANCH_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLCYBER_BRANCH_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLCYBER_BRANCH_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FILE_DATE = dobj.getRetObject("S").getRecord().get("FILE_DATE");   //파일생성일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(FILE_CNT),0)  +1  FILE_CNT  FROM  KB.PAY_FILE_H  WHERE  FILE_DATE  =  :FILE_DATE ";
        sobj.setSql(query);
        sobj.setString("FILE_DATE", FILE_DATE);               //파일생성일
        return sobj;
    }
    // XIUD
    public DOBJ CALLCYBER_BRANCH_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLCYBER_BRANCH_XIUD1(dobj, dvobj);
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
    private SQLObject SQLCYBER_BRANCH_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FILE_CNT = dobj.getRetObject("SEL2").getRecord().get("FILE_CNT");   //파일 갯수
        String   FILE_DATE = dobj.getRetObject("S").getRecord().get("FILE_DATE");   //파일생성일
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO KB.PAY_FILE_H SELECT '2118207744' ,'0000' , :FILE_DATE , :FILE_CNT , SUPP_YRMN ||'저작권료' , COUNT(*) , SUM(SUPP_AMT), 'EE', NULL,NULL,NULL,NULL FROM FIDU.TTAC_BANKFILE WHERE SUPP_YRMN =:SUPP_YRMN AND BANK_CD <> '000' GROUP BY SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("FILE_CNT", FILE_CNT);               //파일 갯수
        sobj.setString("FILE_DATE", FILE_DATE);               //파일생성일
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // XIUD
    public DOBJ CALLCYBER_BRANCH_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLCYBER_BRANCH_XIUD3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLCYBER_BRANCH_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FILE_CNT = dobj.getRetObject("SEL2").getRecord().get("FILE_CNT");   //파일 갯수
        String   FILE_DATE = dobj.getRetObject("S").getRecord().get("FILE_DATE");   //파일생성일
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO KB.PAY_FILE_REQ_D SELECT '2118207744' ,'0000' , :FILE_DATE,:FILE_CNT , ROWNUM, BANK_CD , REPLACE(ACCN_NUM,'-',''), SUPP_AMT, NM , :SUPP_YRMN || ' 한음저협 저작권료' REMARK FROM FIDU.TTAC_BANKFILE WHERE SUPP_YRMN = :SUPP_YRMN AND BANK_CD <> '000'";
        sobj.setSql(query);
        sobj.setString("FILE_CNT", FILE_CNT);               //파일 갯수
        sobj.setString("FILE_DATE", FILE_DATE);               //파일생성일
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // XIUD
    public DOBJ CALLCYBER_BRANCH_XIUD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD5");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLCYBER_BRANCH_XIUD5(dobj, dvobj);
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
    private SQLObject SQLCYBER_BRANCH_XIUD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE FIDU.TTAC_BANKFILE  \n";
        query +=" SET CB_YN ='Y'  \n";
        query +=" WHERE SUPP_YRMN =:SUPP_YRMN  \n";
        query +=" AND BANK_CD <> '000'";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // SEL
    public DOBJ CALLCYBER_BRANCH_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLCYBER_BRANCH_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLCYBER_BRANCH_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  FILE_CNT  FROM  KB.PAY_FILE_REQ_D  WHERE  FILE_GB  ='0000'   \n";
        query +=" AND  FILE_DATE  LIKE  :SUPP_YRMN||'%' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$CYBER_BRANCH
    //##**$$cb_inq
    /*
    */
    public DOBJ CTLcb_inq(DOBJ dobj)
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
            dobj  = CALLcb_inq_SEL1(Conn, dobj);           //  SEL
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
    public DOBJ CTLcb_inq( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcb_inq_SEL1(Conn, dobj);           //  SEL
        return dobj;
    }
    // SEL
    public DOBJ CALLcb_inq_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcb_inq_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcb_inq_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  FILE_CNT  FROM  FIDU.TTAC_BANKFILE  WHERE  SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  CB_YN  ='Y'   \n";
        query +=" AND  BANK_CD  <>  '000' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$cb_inq
    //##**$$transFile4
    /*
    */
    public DOBJ CTLtransFile4(DOBJ dobj)
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
            dobj  = CALLtransFile4_DEL2(Conn, dobj);           //  신한농협외환삭제
            dobj  = CALLtransFile4_XIUD3(Conn, dobj);           //  신한농협외환저장
            dobj  = CALLtransFile4_SEL1(Conn, dobj);           //  은행이체파일조회
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
    public DOBJ CTLtransFile4( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtransFile4_DEL2(Conn, dobj);           //  신한농협외환삭제
        dobj  = CALLtransFile4_XIUD3(Conn, dobj);           //  신한농협외환저장
        dobj  = CALLtransFile4_SEL1(Conn, dobj);           //  은행이체파일조회
        return dobj;
    }
    // 신한농협외환삭제
    public DOBJ CALLtransFile4_DEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtransFile4_DEL2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtransFile4_DEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        String   BANK_CD = dvobj.getRecord().get("BANK_CD");   //은행 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_BANKFILE  \n";
        query +=" where BANK_CD=:BANK_CD  \n";
        query +=" and SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        return sobj;
    }
    // 신한농협외환저장
    public DOBJ CALLtransFile4_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtransFile4_XIUD3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtransFile4_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //은행 코드
        String   INS_STAFF = dobj.getRetObject("G").getRecord().get("USERID");   //등록 사원
        String   SUPP_DAY = dobj.getRetObject("S").getRecord().get("SUPP_DAY");   //지급 일자
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.TTAC_BANKFILE SELECT G.SUPP_YRMN , G.TRNSF_GBN , G.MB_CD , ROWNUM AS SEQ , G.SUPP_DAY , G.NM , G.ACCN_NUM , G.BANK_CD , G.DEPOSITOR_NM , G.SUPP_AMT , G.CLAIMPRES_CD , G.CLAIMPRES_NM , G.PTTN_RATE , G.SUPP_YN , G.INSPRES_ID , G.INS_DATE , G.MODPRES_ID , G.MOD_DATE , 'N' FROM( SELECT distinct A.SUPP_YRMN AS SUPP_YRMN, A.TRNSF_GBN AS TRNSF_GBN, A.MB_CD AS MB_CD, :SUPP_DAY AS SUPP_DAY, B.HANMB_NM AS NM, B.SUPPACCN_NUM AS ACCN_NUM, B.SUPPBANK_CD AS BANK_CD, B.HANMB_NM AS DEPOSITOR_NM, trunc(A.TOT_REALSUPP_AMT - NVL(A.TOT_REPAY_AMT,0)) AS SUPP_AMT, '' AS CLAIMPRES_CD , '' AS CLAIMPRES_NM , 0 AS PTTN_RATE, 'Y' AS SUPP_YN, :INS_STAFF AS INSPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS INS_DATE, :INS_STAFF AS MODPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS MOD_DATE FROM FIDU.TTAC_COPYRTAL A, FIDU.TMEM_MB B WHERE A.MB_CD=B.MB_CD AND A.SUPP_YRMN = :SUPP_YRMN AND B.SUPPBANK_CD = :BANK_CD UNION ALL SELECT E.SUPP_YRMN AS SUPP_YRMN, E.TRNSF_GBN AS TRNSF_GBN, E.CLAIMPRES_CD AS MB_CD, :SUPP_DAY AS SUPP_DAY, E.NM AS NM, E.CLAIMPRES_ACCN_NUM AS ACCN_NUM, E.CLAIMPRES_BANK_CD AS BANK_CD, E.DEPOSITOR_NM AS DEPOSITOR_NM, E.OCC_AMT AS SUPP_AMT, E.MB_CD AS CLAIMPRES_CD , E.CLAIMPRES_NM , E.CLAIM_PTTNRATE AS PTTN_RATE, 'Y' AS SUPP_YN, E.INSPRES_ID, E.INS_DATE, E.MODPRES_ID, E.MOD_DATE FROM ( SELECT SUBSTR(A.REPAY_YRMN,1,6) AS SUPP_YRMN, A.TRNSF_GBN, A.MB_CD, C.BSCONHAN_NM AS NM, C.BSCONHAN_NM AS DEPOSITOR_NM , B.CLAIMPRES_MB_CD AS CLAIMPRES_CD, D.HANMB_NM AS CLAIMPRES_NM, SUM(A.REPAY_AMT) AS OCC_AMT, A.REMAK, B.CLAIMPRES_ACCN_NUM , B.CLAIMPRES_BANK_CD, B.CLAIM_PTTNRATE, 'Y' AS SUPP_YN, :INS_STAFF AS INSPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS INS_DATE, :INS_STAFF AS MODPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS MOD_DATE FROM FIDU.TMEM_CLAIMDEBTREPAY A ,FIDU.TMEM_CLAIMDEBT B ,FIDU.TLEV_BSCON C ,FIDU.TMEM_MB D WHERE 1=1 AND A.MB_CD = B.MB_CD AND A.MNG_NUM = B.MNG_NUM AND A.MB_CD = D.MB_CD AND LENGTH(B.CLAIMPRES_MB_CD) > 0 AND B.CLAIMPRES_MB_CD = C.BSCON_CD AND B.CLAIMPRES_BANK_CD = :BANK_CD AND A.DEDCTPROC_YN = 'Y' AND SUBSTR(A.REPAY_YRMN,1,6) = :SUPP_YRMN GROUP BY SUBSTR(A.REPAY_YRMN,1,6),A.TRNSF_GBN,A.MB_CD ,C.BSCONHAN_NM,B.CLAIMPRES_MB_CD, D.HANMB_NM,A.REMAK ,B.CLAIMPRES_ACCN_NUM , B.CLAIMPRES_BANK_CD ,B.CLAIM_PTTNRATE ) E UNION ALL SELECT F.SUPP_YRMN AS SUPP_YRMN, F.TRNSF_GBN AS TRNSF_GBN, F.CLAIMPRES_CD AS MB_CD, :SUPP_DAY AS SUPP_DAY, F.NM AS NM, F.ACCN_NUM, F.BANK_CD, F.DEPOSITOR_NM AS DEPOSITOR_NM, F.OCC_AMT AS SUPP_AMT, F.MB_CD AS CLAIMPRES_CD , F.CLAIMPRES_NM , 0 AS PTTN_RATE, 'Y' AS SUPP_YN, F.INSPRES_ID, F.INS_DATE, F.MODPRES_ID, F.MOD_DATE FROM ( SELECT SUBSTR(A.SUPP_YRMN,1,6) AS SUPP_YRMN, A.TRNSF_GBN , A.MB_CD , B.HANMB_NM AS NM, B.HANMB_NM AS DEPOSITOR_NM , A.SUPP_MB_CD AS CLAIMPRES_CD, C.BSCONHAN_NM AS CLAIMPRES_NM, SUM(OCC_AMT) AS OCC_AMT , A.SUPP_MB_BANK_CD AS BANK_CD , A.SUPP_ACCN_NUM AS ACCN_NUM, :INS_STAFF AS INSPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS INS_DATE, :INS_STAFF AS MODPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS MOD_DATE FROM FIDU.TTAC_PLEDAMT A ,FIDU.TMEM_MB B , FIDU.TLEV_BSCON C WHERE 1=1 AND A.MB_CD = B.MB_CD AND A.SUPP_MB_CD = C.BSCON_CD AND LENGTH(A.SUPP_MB_CD) > 0 AND A.SUPP_MB_BANK_CD = :BANK_CD AND SUBSTR(A.SUPP_YRMN,1,6) = :SUPP_YRMN GROUP BY SUBSTR(A.SUPP_YRMN,1,6), A.TRNSF_GBN , A.MB_CD ,B.HANMB_NM, A.SUPP_MB_CD,C.BSCONHAN_NM ,A.SUPP_MB_BANK_CD, A.SUPP_ACCN_NUM) F ) G WHERE SUPP_AMT <> 0 ORDER BY G.NM";
        sobj.setSql(query);
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("INS_STAFF", INS_STAFF);               //등록 사원
        sobj.setString("SUPP_DAY", SUPP_DAY);               //지급 일자
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 은행이체파일조회
    public DOBJ CALLtransFile4_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtransFile4_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtransFile4_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //은행 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(BANK_CD,'088','088','005','005','011','011','')  AS  AA,  NVL(ACCN_NUM,  '  ')  AS  BB,  SUPP_AMT  AS  CC,  NM  AS  DD,  '한음저협저작권료'  AS  EE  FROM  FIDU.TTAC_BANKFILE  WHERE  SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  SUPP_YN  =  'Y'   \n";
        query +=" AND  BANK_CD  =  :BANK_CD   \n";
        query +=" AND  LENGTH(TRIM(ACCN_NUM))  >  0  ORDER  BY  NM ";
        sobj.setSql(query);
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$transFile4
    //##**$$tac09_r02_selelct_excel
    /*
    */
    public DOBJ CTLtac09_r02_selelct_excel(DOBJ dobj)
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
            dobj  = CALLtac09_r02_selelct_excel_SEL1(Conn, dobj);           //  은행이체파일조회
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
    public DOBJ CTLtac09_r02_selelct_excel( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac09_r02_selelct_excel_SEL1(Conn, dobj);           //  은행이체파일조회
        return dobj;
    }
    // 은행이체파일조회
    public DOBJ CALLtac09_r02_selelct_excel_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac09_r02_selelct_excel_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac09_r02_selelct_excel_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BANK_CD  AS  AA,  NM  AS  BB,  NVL(ACCN_NUM,  '  ')  AS  CC,  SUPP_AMT  AS  DD,  '한음저협저작권료'  AS  EE  FROM  FIDU.TTAC_BANKFILE  WHERE  SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  SUPP_YN  =  'Y'   \n";
        query +=" AND  LENGTH(TRIM(ACCN_NUM))  >  0  ORDER  BY  BANK_CD,  NM ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$tac09_r02_selelct_excel
    //##**$$transFile2
    /* * 프로그램명 : tac09_r02
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtransFile2(DOBJ dobj)
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
            dobj  = CALLtransFile2_DEL2(Conn, dobj);           //  우리은행삭제
            dobj  = CALLtransFile2_XIUD3(Conn, dobj);           //  우리은행삭제
            dobj  = CALLtransFile2_SEL1(Conn, dobj);           //  은행이체파일조회
            dobj  = CALLtransFile2_SEL4(Conn, dobj);           //  신규 은행이체파일조회
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
    public DOBJ CTLtransFile2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtransFile2_DEL2(Conn, dobj);           //  우리은행삭제
        dobj  = CALLtransFile2_XIUD3(Conn, dobj);           //  우리은행삭제
        dobj  = CALLtransFile2_SEL1(Conn, dobj);           //  은행이체파일조회
        dobj  = CALLtransFile2_SEL4(Conn, dobj);           //  신규 은행이체파일조회
        return dobj;
    }
    // 우리은행삭제
    public DOBJ CALLtransFile2_DEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtransFile2_DEL2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtransFile2_DEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        String   BANK_CD = dvobj.getRecord().get("BANK_CD");   //은행 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_BANKFILE  \n";
        query +=" where BANK_CD=:BANK_CD  \n";
        query +=" and SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        return sobj;
    }
    // 우리은행삭제
    public DOBJ CALLtransFile2_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtransFile2_XIUD3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtransFile2_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //은행 코드
        String   INS_STAFF = dobj.getRetObject("G").getRecord().get("USERID");   //등록 사원
        String   SUPP_DAY = dobj.getRetObject("S").getRecord().get("SUPP_DAY");   //지급 일자
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.TTAC_BANKFILE SELECT G.SUPP_YRMN , G.TRNSF_GBN , G.MB_CD , ROWNUM AS SEQ , G.SUPP_DAY , G.NM , G.ACCN_NUM , G.BANK_CD , G.DEPOSITOR_NM , G.SUPP_AMT , G.CLAIMPRES_CD , G.CLAIMPRES_NM , G.PTTN_RATE , G.SUPP_YN , G.INSPRES_ID , G.INS_DATE , G.MODPRES_ID , G.MOD_DATE, 'N' FROM( SELECT distinct A.SUPP_YRMN AS SUPP_YRMN, A.TRNSF_GBN AS TRNSF_GBN, A.MB_CD AS MB_CD, :SUPP_DAY AS SUPP_DAY, B.HANMB_NM AS NM, B.SUPPACCN_NUM AS ACCN_NUM, B.SUPPBANK_CD AS BANK_CD, B.HANMB_NM AS DEPOSITOR_NM, trunc(A.TOT_REALSUPP_AMT - NVL(A.TOT_REPAY_AMT,0)) AS SUPP_AMT, '' AS CLAIMPRES_CD , '' AS CLAIMPRES_NM , 0 AS PTTN_RATE, 'Y' AS SUPP_YN, :INS_STAFF AS INSPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS INS_DATE, :INS_STAFF AS MODPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS MOD_DATE FROM FIDU.TTAC_COPYRTAL A, FIDU.TMEM_MB B WHERE A.MB_CD=B.MB_CD AND A.SUPP_YRMN = :SUPP_YRMN AND B.SUPPBANK_CD = :BANK_CD UNION ALL SELECT E.SUPP_YRMN AS SUPP_YRMN, E.TRNSF_GBN AS TRNSF_GBN, E.CLAIMPRES_CD AS MB_CD, :SUPP_DAY AS SUPP_DAY, E.NM AS NM, E.CLAIMPRES_ACCN_NUM AS ACCN_NUM, E.CLAIMPRES_BANK_CD AS BANK_CD, E.DEPOSITOR_NM AS DEPOSITOR_NM, E.OCC_AMT AS SUPP_AMT, E.MB_CD AS CLAIMPRES_CD , E.CLAIMPRES_NM , E.CLAIM_PTTNRATE AS PTTN_RATE, 'Y' AS SUPP_YN, E.INSPRES_ID, E.INS_DATE, E.MODPRES_ID, E.MOD_DATE FROM ( SELECT SUBSTR(A.REPAY_YRMN,1,6) AS SUPP_YRMN, A.TRNSF_GBN, A.MB_CD, C.BSCONHAN_NM AS NM, C.BSCONHAN_NM AS DEPOSITOR_NM , B.CLAIMPRES_MB_CD AS CLAIMPRES_CD, D.HANMB_NM AS CLAIMPRES_NM, SUM(A.REPAY_AMT) AS OCC_AMT, A.REMAK, B.CLAIMPRES_ACCN_NUM , B.CLAIMPRES_BANK_CD, B.CLAIM_PTTNRATE, 'Y' AS SUPP_YN, :INS_STAFF AS INSPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS INS_DATE, :INS_STAFF AS MODPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS MOD_DATE FROM FIDU.TMEM_CLAIMDEBTREPAY A ,FIDU.TMEM_CLAIMDEBT B ,FIDU.TLEV_BSCON C ,FIDU.TMEM_MB D WHERE 1=1 AND A.MB_CD = B.MB_CD AND A.MNG_NUM = B.MNG_NUM AND A.MB_CD = D.MB_CD AND LENGTH(B.CLAIMPRES_MB_CD) > 0 AND B.CLAIMPRES_MB_CD = C.BSCON_CD AND A.DEDCTPROC_YN = 'Y' AND B.CLAIMPRES_BANK_CD = :BANK_CD AND SUBSTR(A.REPAY_YRMN,1,6) = :SUPP_YRMN GROUP BY SUBSTR(A.REPAY_YRMN,1,6),A.TRNSF_GBN,A.MB_CD ,C.BSCONHAN_NM,B.CLAIMPRES_MB_CD, D.HANMB_NM,A.REMAK ,B.CLAIMPRES_ACCN_NUM , B.CLAIMPRES_BANK_CD ,B.CLAIM_PTTNRATE ) E UNION ALL SELECT F.SUPP_YRMN AS SUPP_YRMN, F.TRNSF_GBN AS TRNSF_GBN, F.CLAIMPRES_CD AS MB_CD, :SUPP_DAY AS SUPP_DAY, F.NM AS NM, F.ACCN_NUM, F.BANK_CD, F.DEPOSITOR_NM AS DEPOSITOR_NM, F.OCC_AMT AS SUPP_AMT, F.MB_CD AS CLAIMPRES_CD , F.CLAIMPRES_NM , 0 AS PTTN_RATE, 'Y' AS SUPP_YN, F.INSPRES_ID, F.INS_DATE, F.MODPRES_ID, F.MOD_DATE FROM (SELECT SUBSTR(A.SUPP_YRMN,1,6) AS SUPP_YRMN, A.TRNSF_GBN , A.MB_CD , B.HANMB_NM AS NM, B.HANMB_NM AS DEPOSITOR_NM , A.SUPP_MB_CD AS CLAIMPRES_CD, C.BSCONHAN_NM AS CLAIMPRES_NM, SUM(OCC_AMT) AS OCC_AMT , A.SUPP_MB_BANK_CD AS BANK_CD , A.SUPP_ACCN_NUM AS ACCN_NUM, :INS_STAFF AS INSPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS INS_DATE, :INS_STAFF AS MODPRES_ID, TO_CHAR(SYSDATE,'YYYYMMDD') AS MOD_DATE FROM FIDU.TTAC_PLEDAMT A ,FIDU.TMEM_MB B , FIDU.TLEV_BSCON C WHERE 1=1 AND A.MB_CD = B.MB_CD AND A.SUPP_MB_CD = C.BSCON_CD AND LENGTH(A.SUPP_MB_CD) > 0 AND A.SUPP_MB_BANK_CD = :BANK_CD AND SUBSTR(A.SUPP_YRMN,1,6) = :SUPP_YRMN GROUP BY SUBSTR(A.SUPP_YRMN,1,6), A.TRNSF_GBN , A.MB_CD ,B.HANMB_NM, A.SUPP_MB_CD,C.BSCONHAN_NM ,A.SUPP_MB_BANK_CD, A.SUPP_ACCN_NUM) F ) G WHERE SUPP_AMT <> 0 ORDER BY G.NM";
        sobj.setSql(query);
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("INS_STAFF", INS_STAFF);               //등록 사원
        sobj.setString("SUPP_DAY", SUPP_DAY);               //지급 일자
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 은행이체파일조회
    public DOBJ CALLtransFile2_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtransFile2_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtransFile2_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //은행 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  'D20'||  RPAD(REPLACE(NVL(ACCN_NUM,  '  '),'-',''),  15,  '  ')  ||  '40'||LPAD(SUPP_AMT,  11,  0)  ||'2016513001'||LPAD('  ',39,'  ')  AS  AA  FROM  FIDU.TTAC_BANKFILE  WHERE  SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  SUPP_YN  =  'Y'   \n";
        query +=" AND  BANK_CD  =  :BANK_CD   \n";
        query +=" AND  LENGTH(TRIM(ACCN_NUM))  >  0  UNION  ALL   \n";
        query +=" SELECT  'E'||  LPAD(COUNT(ROWNUM)+2,  7,  0)  ||  LPAD(COUNT(ROWNUM),  7,  0)  ||  LPAD(SUM(SUPP_AMT),  13,  0)||  LPAD('  ',52,'  ')  AS  AA  FROM  FIDU.TTAC_BANKFILE  WHERE  SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  SUPP_YN  =  'Y'   \n";
        query +=" AND  BANK_CD  =  :BANK_CD   \n";
        query +=" AND  LENGTH(TRIM(ACCN_NUM))  >  0 ";
        sobj.setSql(query);
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 신규 은행이체파일조회
    public DOBJ CALLtransFile2_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtransFile2_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtransFile2_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //은행 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BANK_CD  AS  AA,  NM  AS  BB,  NVL(ACCN_NUM,  '  ')  AS  CC,  SUPP_AMT  AS  DD,  '한음저협저작권료'  AS  EE  FROM  FIDU.TTAC_BANKFILE  WHERE  SUPP_YRMN  =:SUPP_YRMN   \n";
        query +=" AND  SUPP_YN  =  'Y'   \n";
        query +=" AND  BANK_CD  =  :BANK_CD   \n";
        query +=" AND  LENGTH(TRIM(ACCN_NUM))  >  0  ORDER  BY  NM ";
        sobj.setSql(query);
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$transFile2
    //##**$$searchBank
    /* * 프로그램명 : tac09_r02
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchBank(DOBJ dobj)
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
            dobj  = CALLsearchBank_SEL1(Conn, dobj);           //  회계은행조회
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
    public DOBJ CTLsearchBank( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchBank_SEL1(Conn, dobj);           //  회계은행조회
        return dobj;
    }
    // 회계은행조회
    public DOBJ CALLsearchBank_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchBank_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchBank_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BANK_NM  ,  A.BANK_CD  FROM  ACCT.TCAC_BANK  A  WHERE  A.USE_YN='Y'   \n";
        query +=" AND  A.BANK_CD  in('004','011','020','088','003','071','081','000',  '032','090','045','007','023','027','031')  ORDER  BY  A.BANK_CD  ASC ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$searchBank
    //##**$$ttac_bankfile_select
    /* * 프로그램명 : tac09_r02
    * 작성자 : 손주환
    * 작성일 : 2009/11/23
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLttac_bankfile_select(DOBJ dobj)
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
            dobj  = CALLttac_bankfile_select_SEL1(Conn, dobj);           //  은행파일조회1
            dobj  = CALLttac_bankfile_select_SEL2(Conn, dobj);           //  은행파일조회2
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
    public DOBJ CTLttac_bankfile_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttac_bankfile_select_SEL1(Conn, dobj);           //  은행파일조회1
        dobj  = CALLttac_bankfile_select_SEL2(Conn, dobj);           //  은행파일조회2
        return dobj;
    }
    // 은행파일조회1
    // 계좌번호 있는 사원
    public DOBJ CALLttac_bankfile_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLttac_bankfile_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_bankfile_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BANK_CD = dvobj.getRecord().get("BANK_CD");   //은행 코드
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.SUPP_YRMN,  A.TRNSF_GBN,  A.MB_CD,  A.NM,  A.ACCN_NUM,  A.BANK_CD,  A.SUPP_AMT,  A.SUPP_YN,  B.CODE_NM,  C.BANK_NM,  A.CLAIMPRES_CD,  A.CLAIMPRES_NM,  A.PTTN_RATE  FROM  FIDU.TTAC_BANKFILE  A,  FIDU.TENV_CODE  B,  ACCT.TCAC_BANK  C  WHERE  A.TRNSF_GBN=B.CODE_CD   \n";
        query +=" AND  A.BANK_CD=C.BANK_CD   \n";
        query +=" AND  A.SUPP_YN='Y'   \n";
        query +=" AND  B.HIGH_CD(+)='00294'   \n";
        query +=" AND  A.SUPP_YRMN  =  :SUPP_YRMN   \n";
        query +=" AND  A.BANK_CD  =  :BANK_CD   \n";
        query +=" AND  LENGTH(NVL(A.ACCN_NUM,''))  >  0 ";
        sobj.setSql(query);
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 은행파일조회2
    // 계좌번호없는사원
    public DOBJ CALLttac_bankfile_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLttac_bankfile_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_bankfile_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.SUPP_YRMN,  A.TRNSF_GBN,  A.MB_CD,  A.NM,  A.ACCN_NUM,  A.BANK_CD,  A.SUPP_AMT,  A.SUPP_YN,  B.CODE_NM,  C.BANK_NM  FROM  FIDU.TTAC_BANKFILE  A,  FIDU.TENV_CODE  B,  ACCT.TCAC_BANK  C,  FIDU.TMEM_MB  D  WHERE  A.TRNSF_GBN=B.CODE_CD(+)   \n";
        query +=" AND  A.BANK_CD=C.BANK_CD(+)   \n";
        query +=" AND  B.HIGH_CD(+)='00294'   \n";
        query +=" AND  A.SUPP_YRMN  =  :SUPP_YRMN   \n";
        query +=" AND  A.MB_CD  =  D.MB_CD   \n";
        query +=" AND  SUPPACCN_NUM  IS  NULL   \n";
        query +=" AND  CLAIMPRES_CD  IS  NULL ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$ttac_bankfile_select
    //##**$$tac09_r02_save
    /* * 프로그램명 : tac09_r02
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac09_r02_save(DOBJ dobj)
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
            dobj  = CALLtac09_r02_save_MIUD2(Conn, dobj);           //  수취여부 저장
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
    public DOBJ CTLtac09_r02_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac09_r02_save_MIUD2(Conn, dobj);           //  수취여부 저장
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 수취여부 저장
    public DOBJ CALLtac09_r02_save_MIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD2");
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
                dobj  = CALLtac09_r02_save_UPD1(Conn, dobj);           //  출판사부가세 수취여부저장
            }
        }
        return dobj;
    }
    // 출판사부가세 수취여부저장
    public DOBJ CALLtac09_r02_save_UPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD1");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtac09_r02_save_UPD1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac09_r02_save_UPD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String RECVNACK_YN ="";        //수취확인
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        String   RECVNACK_YN_1 = dobj.getRetObject("R").getRecord().get("RECVNACK_YN");   //수취확인
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_COPYRTAL  \n";
        query +=" set RECVNACK_YN=trunc(:RECVNACK_YN_1) , TRNSF_GBN=:TRNSF_GBN  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("RECVNACK_YN_1", RECVNACK_YN_1);               //수취확인
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    //##**$$tac09_r02_save
    //##**$$trans_inside_bk
    /*
    */
    public DOBJ CTLtrans_inside_bk(DOBJ dobj)
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
            dobj  = CALLtrans_inside_bk_XIUD3(Conn, dobj);           //  이체테이블 초기
            dobj  = CALLtrans_inside_bk_MIUD4(Conn, dobj);           //  분기
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
    public DOBJ CTLtrans_inside_bk( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtrans_inside_bk_XIUD3(Conn, dobj);           //  이체테이블 초기
        dobj  = CALLtrans_inside_bk_MIUD4(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 이체테이블 초기
    public DOBJ CALLtrans_inside_bk_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtrans_inside_bk_XIUD3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtrans_inside_bk_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM ACCT.IB_BULK_TRAN";
        sobj.setSql(query);
        return sobj;
    }
    // 분기
    public DOBJ CALLtrans_inside_bk_MIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD4");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtrans_inside_bk_INS8(Conn, dobj);           //  입력
            }
        }
        return dobj;
    }
    // 입력
    public DOBJ CALLtrans_inside_bk_INS8(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLtrans_inside_bk_INS8(dobj, dvobj);
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
    private SQLObject SQLtrans_inside_bk_INS8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TRAN_DT = dvobj.getRecord().get("TRAN_DT");
        String   TRAN_AMT_REQ = dvobj.getRecord().get("TRAN_AMT_REQ");
        String   TRAN_JI_NAEYONG = dvobj.getRecord().get("TRAN_JI_NAEYONG");
        String   TRAN_IP_ACCT_NB = dvobj.getRecord().get("TRAN_IP_ACCT_NB");
        String   GROUP_NM = dvobj.getRecord().get("GROUP_NM");
        String   LIST_NM = dvobj.getRecord().get("LIST_NM");
        String   TRAN_IP_BANK_ID = dvobj.getRecord().get("TRAN_IP_BANK_ID");
        String   TRAN_REMITTEE_NM = dvobj.getRecord().get("TRAN_REMITTEE_NM");
        String   TRAN_DT_SEQ = dvobj.getRecord().get("TRAN_DT_SEQ");
        String   TRAN_IP_NAEYONG = dvobj.getRecord().get("TRAN_IP_NAEYONG");
        String   TRAN_REMITTEE_REALNM = dvobj.getRecord().get("TRAN_REMITTEE_REALNM");
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into ACCT.IB_BULK_TRAN (TRAN_REMITTEE_REALNM, TRAN_IP_NAEYONG, TRAN_DT_SEQ, TRAN_REMITTEE_NM, TRAN_IP_BANK_ID, LIST_NM, GROUP_NM, TRAN_IP_ACCT_NB, TRAN_JI_NAEYONG, TRAN_AMT_REQ, TRAN_DT)  \n";
        query +=" values(:TRAN_REMITTEE_REALNM , :TRAN_IP_NAEYONG , :TRAN_DT_SEQ , :TRAN_REMITTEE_NM , :TRAN_IP_BANK_ID , :LIST_NM , :GROUP_NM , :TRAN_IP_ACCT_NB , :TRAN_JI_NAEYONG , :TRAN_AMT_REQ , :TRAN_DT )";
        sobj.setSql(query);
        sobj.setString("TRAN_DT", TRAN_DT);
        sobj.setString("TRAN_AMT_REQ", TRAN_AMT_REQ);
        sobj.setString("TRAN_JI_NAEYONG", TRAN_JI_NAEYONG);
        sobj.setString("TRAN_IP_ACCT_NB", TRAN_IP_ACCT_NB);
        sobj.setString("GROUP_NM", GROUP_NM);
        sobj.setString("LIST_NM", LIST_NM);
        sobj.setString("TRAN_IP_BANK_ID", TRAN_IP_BANK_ID);
        sobj.setString("TRAN_REMITTEE_NM", TRAN_REMITTEE_NM);
        sobj.setString("TRAN_DT_SEQ", TRAN_DT_SEQ);
        sobj.setString("TRAN_IP_NAEYONG", TRAN_IP_NAEYONG);
        sobj.setString("TRAN_REMITTEE_REALNM", TRAN_REMITTEE_REALNM);
        return sobj;
    }
    //##**$$trans_inside_bk
    //##**$$end
}
