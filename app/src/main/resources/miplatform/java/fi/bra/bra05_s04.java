
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_s04
{
    public bra05_s04()
    {
    }
    //##**$$sel_accu_sol
    /*
    */
    public DOBJ CTLsel_accu_sol(DOBJ dobj)
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
            dobj  = CALLsel_accu_sol_SEL1(Conn, dobj);           //  고소해결확정 리스트
            dobj  = CALLsel_accu_sol_MPD3(Conn, dobj);           //  그룹웨어랑 연결하기위한 분기
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
    public DOBJ CTLsel_accu_sol( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_accu_sol_SEL1(Conn, dobj);           //  고소해결확정 리스트
        dobj  = CALLsel_accu_sol_MPD3(Conn, dobj);           //  그룹웨어랑 연결하기위한 분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 고소해결확정 리스트
    public DOBJ CALLsel_accu_sol_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_accu_sol_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_accu_sol_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SATN_STAT = dobj.getRetObject("S").getRecord().get("SATN_STAT");   //결재상태
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   STAFF_NUM = dobj.getRetObject("S").getRecord().get("STAFF_NUM");   //사원번호
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT '0' AS CHK , A.ACCU_DAY , A.ACCU_NUM , A.ACCU_BRAN , A.UPSO_CD , B.UPSO_NM , A.STAFF_CD , FIDU.GET_STAFF_NM(A.STAFF_CD) AS STAFF_NM , SUBSTR(SOL_START_YRMN, 1, 4) || '/' || SUBSTR(SOL_START_YRMN, 5, 2) || '~' || SUBSTR(SOL_END_YRMN, 1, 4) || '/' || SUBSTR(SOL_END_YRMN, 5, 2) AS SOL_START_YRMN , A.SOL_ORG_AMT , A.SOL_ADDT_AMT , A.SOL_ORG_AMT + A.SOL_ADDT_AMT AS SOL_TOT_AMT , A.COMIS , A.COMPN_DAY2 AS COMPN_DAY , DECODE(A.COMPN_DAY, NULL, 'N', 'Y') AS SATN_STAT  ";
        query +=" FROM GIBU.TBRA_ACCU A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE 1=1  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.COMPN_DAY2 IS NOT NULL  ";
        query +=" AND A.ACCU_BRAN = DECODE(:BRAN_CD, 'AL', A.ACCU_BRAN, :BRAN_CD)  ";
        query +=" AND B.STAFF_CD = NVL(:STAFF_NUM, B.STAFF_CD)  ";
        query +=" AND A.COMPN_DAY2 BETWEEN :START_YRMN || '01'  ";
        query +=" AND :END_YRMN || '31'  ";
        if( !SATN_STAT.equals("") )
        {
            query +=" AND DECODE(A.COMPN_DAY, NULL, 'N', 'Y') = :SATN_STAT  ";
        }
        sobj.setSql(query);
        if(!SATN_STAT.equals(""))
        {
            sobj.setString("SATN_STAT", SATN_STAT);               //결재상태
        }
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("STAFF_NUM", STAFF_NUM);               //사원번호
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 그룹웨어랑 연결하기위한 분기
    public DOBJ CALLsel_accu_sol_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("SEL1");         //고소해결확정 리스트에서 생성시킨 OBJECT입니다.(CALLsel_accu_sol_SEL1)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsel_accu_sol_SEL2(Conn, dobj);           //  결재완료만 불러오기
                if( dobj.getRetObject("SEL2").getRecordCnt() > 0)
                {
                    dobj  = CALLsel_accu_sol_ADD6( dobj);        //  리스트화 할 목록
                }
            }
        }
        return dobj;
    }
    // 결재완료만 불러오기
    public DOBJ CALLsel_accu_sol_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_accu_sol_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_accu_sol_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("R").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("R").getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_BRAN = dobj.getRetObject("R").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LNK_KEY  FROM  TRGW.TEAG_APPDOC  WHERE  DOC_STS  =  '90'   \n";
        query +=" AND  EAP_TYPE  =  'FB0000'   \n";
        query +=" AND  LNK_KEY  =  :ACCU_DAY  ||  '-'  ||  :ACCU_NUM  ||  '-'  ||  :ACCU_BRAN ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    // 리스트화 할 목록
    public DOBJ CALLsel_accu_sol_ADD6(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("ADD6");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        VOBJ dvobj = dobj.getRetObject("R");          //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        rvobj = wutil.getAddedVobj(dobj,"ADD6","", dvobj );
        rvobj.setName("ADD6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    //##**$$sel_accu_sol
    //##**$$mng_accu_sol
    /*
    */
    public DOBJ CTLmng_accu_sol(DOBJ dobj)
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
            dobj  = CALLmng_accu_sol_MIUD1(Conn, dobj);           //  분기
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
    public DOBJ CTLmng_accu_sol( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_accu_sol_MIUD1(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLmng_accu_sol_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("MIUD1");
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
                dobj  = CALLmng_accu_sol_SEL7(Conn, dobj);           //  판결내역
                if( dobj.getRetObject("SEL7").getRecord().get("JUDG_CD").equals("1"))
                {
                    dobj  = CALLmng_accu_sol_XIUD9(Conn, dobj);           //  기존입금정보삭제
                    dobj  = CALLmng_accu_sol_SEL10(Conn, dobj);           //  입금정보조회
                    if( dobj.getRetObject("SEL10").getRecord().get("BRAN").equals("N"))
                    {
                        dobj  = CALLmng_accu_sol_SEL20(Conn, dobj);           //  원장등록 개수확인
                        if( dobj.getRetObject("SEL20").getRecord().getDouble("CNT") == 0)
                        {
                            dobj  = CALLmng_accu_sol_SEL61(Conn, dobj);           //  고소저장 입금내역
                            dobj  = CALLmng_accu_sol_XIUD19(Conn, dobj);           //  고소해결 확정
                        }
                        else
                        {
                            dobj.setRtncode(9);
                            if(dobj.getRtncode() == 9)
                            {
                                String message ="해결기간에 입금이 모두 있는지 확인하세요";
                                dobj.setRetmsg(message);
                                Conn.rollback();
                                return dobj;
                            }
                        }
                    }
                    else if( dobj.getRetObject("SEL10").getRecord().get("BRAN").equals("Y"))
                    {
                        dobj.setRtncode(9);
                        if(dobj.getRtncode() == 9)
                        {
                            String message ="입금내역 정보가 없습니다. 입금정보를 확인해 주세요.";
                            dobj.setRetmsg(message);
                            Conn.rollback();
                            return dobj;
                        }
                    }
                    else if( dobj.getRetObject("SEL10").getRecord().get("BRAN").equals("0"))
                    {
                        dobj  = CALLmng_accu_sol_SEL13(Conn, dobj);           //  업소업종조회
                        if( dobj.getRetObject("SEL13").getRecord().get("GRAD").equals("v01"))
                        {
                            dobj  = CALLmng_accu_sol_XIUD5(Conn, dobj);           //  고소해결 확정
                        }
                        else
                        {
                            dobj.setRtncode(9);
                            if(dobj.getRtncode() == 9)
                            {
                                String message ="입금내역 정보가 없습니다. 입금정보를 확인해 주세요.";
                                dobj.setRetmsg(message);
                                Conn.rollback();
                                return dobj;
                            }
                        }
                    }
                }
                else
                {
                    dobj  = CALLmng_accu_sol_XIUD5(Conn, dobj);           //  고소해결 확정
                }
            }
        }
        return dobj;
    }
    // 판결내역
    public DOBJ CALLmng_accu_sol_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_accu_sol_SEL7(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_accu_sol_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("R").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("R").getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_BRAN = dobj.getRetObject("R").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  JUDG_CD  FROM  GIBU.TBRA_ACCU  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    // 기존입금정보삭제
    // 기존의 고소번호로 입금정보가 있을 경우 입금정보를 삭제한다
    public DOBJ CALLmng_accu_sol_XIUD9(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLmng_accu_sol_XIUD9(dobj, dvobj);
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
    private SQLObject SQLmng_accu_sol_XIUD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_BRAN = dobj.getRetObject("R").getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_DAY = dobj.getRetObject("R").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("R").getRecord().get("ACCU_NUM");   //고소 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_ACCU  \n";
        query +=" SET REPT_DAY = '' , REPT_NUM = '' , REPT_GBN = '' , DISTR_SEQ = ''  \n";
        query +=" WHERE ACCU_DAY = :ACCU_DAY  \n";
        query +=" AND ACCU_NUM = :ACCU_NUM  \n";
        query +=" AND ACCU_BRAN = :ACCU_BRAN";
        sobj.setSql(query);
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        return sobj;
    }
    // 입금정보조회
    // 입금취하일 경우 입금정보가 먼저 등록되어 있어야 저장이 된다
    public DOBJ CALLmng_accu_sol_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_accu_sol_SEL10(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.Println("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_accu_sol_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SOL_START_YRMN = dobj.getRetObject("S").getRecord().get("SOL_START_YRMN");   //해결기간 시작년월
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CASE  WHEN  COUNT(XA.DUP_YN)  =  0  THEN  '0'  WHEN  MAX(XA.DUP_YN)  =  'Y'  THEN  'Y'  ELSE  'N'  END  BRAN  FROM   \n";
        query +=" (SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.DISTR_GBN  ,  B.DISTR_SEQ  ,  DECODE  ((SELECT  COUNT(*)  FROM  GIBU.TBRA_ACCU  IA  WHERE  IA.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  IA.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  IA.REPT_GBN  =  A.REPT_GBN  ),  0,  'N',  'Y'  ||  A.DISTR_GBN)  DUP_YN  FROM  GIBU.TBRA_REPT  A  ,  GIBU.TBRA_NOTE  B  WHERE  B.NOTE_YRMN  BETWEEN  REPLACE(SUBSTR(:SOL_START_YRMN,  0,  7),  '/',  '')   \n";
        query +=" AND  REPLACE(SUBSTR(:SOL_START_YRMN,  9,  7),  '/',  '')   \n";
        query +=" AND  (B.ACCU_GBN  IN  ('06',  '22')   \n";
        query +=" OR  B.REPT_GBN  =  '09')   \n";
        query +=" AND  B.NOTE_NUM  =  '0001'   \n";
        query +=" AND  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  B.REPT_GBN  GROUP  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN,  A.DISTR_GBN,  B.DISTR_SEQ  )  XA ";
        sobj.setSql(query);
        sobj.setString("SOL_START_YRMN", SOL_START_YRMN);               //해결기간 시작년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 원장등록 개수확인
    // 해결시작~끝년월에 원장이 모두 있는지 개수로 확인 휴폐업 제외(REPT_GBN 11-19)
    public DOBJ CALLmng_accu_sol_SEL20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL20");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL20");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_accu_sol_SEL20(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL20");
        rvobj.Println("SEL20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_accu_sol_SEL20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("R").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("R").getRecord().get("ACCU_NUM");   //고소 번호
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   ACCU_BRAN = dobj.getRetObject("R").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.CNT  -  B.MMCNT  AS  CNT  FROM  (   \n";
        query +=" SELECT  TO_CHAR(COUNT(NOTE_YRMN))  CNT  FROM  GIBU.TBRA_NOTE  A  ,  GIBU.TBRA_ACCU  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  B.ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  B.ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  B.SOL_START_YRMN   \n";
        query +=" AND  B.SOL_END_YRMN   \n";
        query +=" AND  (A.ACCU_GBN  IN  ('06',  '22')   \n";
        query +=" OR  A.REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'   \n";
        query +=" OR  A.REPT_GBN  =  '09'))  A,  (   \n";
        query +=" SELECT  TO_CHAR(trunc(MONTHS_BETWEEN(TO_DATE(SOL_END_YRMN,'YYYYMM'),  TO_DATE(SOL_START_YRMN,  'YYYYMM'))  +  1))  AS  MMCNT  FROM  GIBU.TBRA_ACCU  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN)  B ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    // 고소저장 입금내역
    // 고소테이블에 저장할 입금내역 조회
    public DOBJ CALLmng_accu_sol_SEL61(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL61");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL61");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_accu_sol_SEL61(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL61");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_accu_sol_SEL61(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DISTR_SEQ  ,  B.REPT_DAY  ,  B.REPT_GBN  ,  B.REPT_NUM  FROM  GIBU.TBRA_REPT  B  ,  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  NOTE_YRMN  =  (   \n";
        query +=" SELECT  MAX(NOTE_YRMN)  FROM  GIBU.TBRA_NOTE  A  WHERE  UPSO_CD  =:UPSO_CD   \n";
        query +=" AND  ACCU_GBN  IN  ('06','22')  )   \n";
        query +=" AND  NOTE_NUM  =  '0001'   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD  )  A  WHERE  1=1   \n";
        query +=" AND  B.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  =  A.REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 고소해결 확정
    public DOBJ CALLmng_accu_sol_XIUD19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD19");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("XIUD19");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_accu_sol_XIUD19(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_accu_sol_XIUD19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_BRAN = dobj.getRetObject("R").getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_DAY = dobj.getRetObject("R").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("R").getRecord().get("ACCU_NUM");   //고소 번호
        String   DISTR_SEQ = dobj.getRetObject("SEL61").getRecord().get("DISTR_SEQ");   //분배 순번
        String   REPT_DAY = dobj.getRetObject("SEL61").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_GBN = dobj.getRetObject("SEL61").getRecord().get("REPT_GBN");   //입금 구분
        String   REPT_NUM = dobj.getRetObject("SEL61").getRecord().get("REPT_NUM");   //입금 번호
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_ACCU  \n";
        query +=" SET COMPN_DAY = COMPN_DAY2 , DISTR_SEQ = :DISTR_SEQ , REPT_DAY = :REPT_DAY , REPT_GBN = :REPT_GBN , REPT_NUM = :REPT_NUM  \n";
        query +=" WHERE ACCU_DAY = :ACCU_DAY  \n";
        query +=" AND ACCU_NUM = :ACCU_NUM  \n";
        query +=" AND ACCU_BRAN = :ACCU_BRAN  \n";
        query +=" AND UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("DISTR_SEQ", DISTR_SEQ);               //분배 순번
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소업종조회
    // 업종이 무대공연일때 입금과 상관없이 수정되어야 한다
    public DOBJ CALLmng_accu_sol_SEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL13");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_accu_sol_SEL13(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL13");
        rvobj.Println("SEL13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_accu_sol_SEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TRIM(BSTYP_CD)  ||  UPSO_GRAD  AS  GRAD  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD  order  by  CHG_DAY  desc ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 고소해결 확정
    public DOBJ CALLmng_accu_sol_XIUD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD5");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("XIUD5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_accu_sol_XIUD5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
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
    private SQLObject SQLmng_accu_sol_XIUD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_BRAN = dobj.getRetObject("R").getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_DAY = dobj.getRetObject("R").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("R").getRecord().get("ACCU_NUM");   //고소 번호
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_ACCU  \n";
        query +=" SET COMPN_DAY = COMPN_DAY2  \n";
        query +=" WHERE ACCU_DAY = :ACCU_DAY  \n";
        query +=" AND ACCU_NUM = :ACCU_NUM  \n";
        query +=" AND ACCU_BRAN = :ACCU_BRAN  \n";
        query +=" AND UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$mng_accu_sol
    //##**$$del_accu_sol
    /*
    */
    public DOBJ CTLdel_accu_sol(DOBJ dobj)
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
            dobj  = CALLdel_accu_sol_XIUD2(Conn, dobj);           //  고소해결확정풀기
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
    public DOBJ CTLdel_accu_sol( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdel_accu_sol_XIUD2(Conn, dobj);           //  고소해결확정풀기
        return dobj;
    }
    // 고소해결확정풀기
    // 고소해결확정풀기
    public DOBJ CALLdel_accu_sol_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdel_accu_sol_XIUD2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdel_accu_sol_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_ACCU  \n";
        query +=" SET COMPN_DAY = ''  \n";
        query +=" WHERE ACCU_DAY = :ACCU_DAY  \n";
        query +=" AND ACCU_NUM = :ACCU_NUM  \n";
        query +=" AND ACCU_BRAN = :ACCU_BRAN";
        sobj.setSql(query);
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        return sobj;
    }
    //##**$$del_accu_sol
    //##**$$del_accu_appr
    /*
    */
    public DOBJ CTLdel_accu_appr(DOBJ dobj)
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
            dobj  = CALLdel_accu_appr_SEL7(Conn, dobj);           //  해결확정확인
            if( dobj.getRetObject("SEL7").getRecordCnt() > 0)
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="해결확정을 먼저 풀고 결재내역을 삭제해주세요";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
            }
            else
            {
                dobj  = CALLdel_accu_appr_XIUD1(Conn, dobj);           //  고소완결내역삭제
                dobj  = CALLdel_accu_appr_SEL6(Conn, dobj);           //  연동번호넘기기
                dobj  = CALLdel_accu_appr_OBJ7(Conn, dobj);           //  그룹웨어 연동데이터 삭제
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
    public DOBJ CTLdel_accu_appr( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdel_accu_appr_SEL7(Conn, dobj);           //  해결확정확인
        if( dobj.getRetObject("SEL7").getRecordCnt() > 0)
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="해결확정을 먼저 풀고 결재내역을 삭제해주세요";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        else
        {
            dobj  = CALLdel_accu_appr_XIUD1(Conn, dobj);           //  고소완결내역삭제
            dobj  = CALLdel_accu_appr_SEL6(Conn, dobj);           //  연동번호넘기기
            dobj  = CALLdel_accu_appr_OBJ7(Conn, dobj);           //  그룹웨어 연동데이터 삭제
        }
        return dobj;
    }
    // 해결확정확인
    // 해결확정이 되어있는지 확인
    public DOBJ CALLdel_accu_appr_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLdel_accu_appr_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdel_accu_appr_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_ACCU  WHERE  ACCU_DAY  =  :ACCU_DAY   \n";
        query +=" AND  ACCU_NUM  =  :ACCU_NUM   \n";
        query +=" AND  ACCU_BRAN  =  :ACCU_BRAN   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    // 고소완결내역삭제
    // 고소완결날짜2 삭제
    public DOBJ CALLdel_accu_appr_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdel_accu_appr_XIUD1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
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
    private SQLObject SQLdel_accu_appr_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_ACCU  \n";
        query +=" SET COMPN_DAY2 = '', JUDG_CD = ''  \n";
        query +=" WHERE ACCU_DAY = :ACCU_DAY  \n";
        query +=" AND ACCU_NUM = :ACCU_NUM  \n";
        query +=" AND ACCU_BRAN = :ACCU_BRAN";
        sobj.setSql(query);
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        return sobj;
    }
    // 연동번호넘기기
    public DOBJ CALLdel_accu_appr_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLdel_accu_appr_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdel_accu_appr_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCU_DAY = dobj.getRetObject("S").getRecord().get("ACCU_DAY");   //고소 일자
        String   ACCU_NUM = dobj.getRetObject("S").getRecord().get("ACCU_NUM");   //고소 번호
        String   ACCU_BRAN = dobj.getRetObject("S").getRecord().get("ACCU_BRAN");   //고소 지부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :ACCU_DAY  ||  '-'  ||  :ACCU_NUM  ||  '-'  ||  :ACCU_BRAN  AS  LNK_KEY  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("ACCU_DAY", ACCU_DAY);               //고소 일자
        sobj.setString("ACCU_NUM", ACCU_NUM);               //고소 번호
        sobj.setString("ACCU_BRAN", ACCU_BRAN);               //고소 지부
        return sobj;
    }
    // 그룹웨어 연동데이터 삭제
    public DOBJ CALLdel_accu_appr_OBJ7(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OBJ7");
        CommUtil cu = new CommUtil(dobj);
        HashMap   classinfo = new HashMap();
        classinfo.put("OBJECTID","OBJ7");
        classinfo.put("PACKAGE","komca.ga.adm");
        classinfo.put("CLASS","adm01_r01");
        classinfo.put("METHOD","satn_num_del");
        classinfo.put("INMAP","S:S");
        classinfo.put("OUTOBJ","");
        classinfo.put("OUTOBJ1","");
        dobj = cu.callPSInternal(dobj, Conn, classinfo );
        dobj.getRetObject("OBJ7").Println("OBJ7");
        return dobj;
    }
    //##**$$del_accu_appr
    //##**$$end
}
