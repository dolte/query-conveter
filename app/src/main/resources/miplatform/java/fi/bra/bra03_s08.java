
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s08
{
    public bra03_s08()
    {
    }
    //##**$$auto_demd_select
    /* * 프로그램명 : bra03_s08
    * 작성자 : 박태현
    * 작성일 : 2009/11/24
    * 설명    : [자동이체 청구자료 생성]에서 생성된 자료에서 금결원으로 넘겨줄 데이터 항목을 조회한다.
    자동이체 납부일은 매월 23 일이나 23일이 휴일인 경우 23일 이후의 휴일이 아닌 첫일로 납부일을 조회한다.
    휴일관련 조회는 인사테이블의 INSA.TDUT_CALENDAR 를 조회한다.
    Input
    DEMD_YRMN (청구 년월)
    ** 수정내용. 2012/06/13
    SEL5, XIUD10을 대상으로 함
    자동이체 청구파일을 23일에 생성하는 경우는 청구데이터 생성 직후 실행하므로 원장기록 내용과 갭이 없다.
    그러나 3일에 청구파일을 재생성하는 경우는 원장기록 내용과 갭이 발생한다. 휴업, 정지등...  (폐업과 입금의 경우는 반영해주고있다.)
    만약 3개월치 자동이체 청구의 경우 1일에 3개월 모두 휴업처리를 했다면 이 업소의 자동이체 재청구하지 않는다.
    만약 3개월치 자동이체 청구의 경우 1일에 1개월만 휴업처리를 했다면 이 업소의 자동이체는 재청구된다. (청구금액은 변경되지않는다. 금액변경되려면 청구를 다시돌려야..)
    */
    public DOBJ CTLauto_demd_select(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("PROC_GBN").equals("2"))
            {
                dobj  = CALLauto_demd_select_SEL5(Conn, dobj);           //  청구내역 조회
                dobj  = CALLauto_demd_select_SEL6(Conn, dobj);           //  납기일 조회
                dobj  = CALLauto_demd_select_XIUD10(Conn, dobj);           //  3일청구확인저장
            }
            else
            {
                dobj  = CALLauto_demd_select_SEL1(Conn, dobj);           //  청구내역 조회
                dobj  = CALLauto_demd_select_SEL2(Conn, dobj);           //  납기일 조회
                dobj  = CALLauto_demd_select_SEL10(Conn, dobj);           //  납기일 조회
                dobj  = CALLauto_demd_select_XIUD14(Conn, dobj);           //  3일청구확인저장
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
    public DOBJ CTLauto_demd_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("PROC_GBN").equals("2"))
        {
            dobj  = CALLauto_demd_select_SEL5(Conn, dobj);           //  청구내역 조회
            dobj  = CALLauto_demd_select_SEL6(Conn, dobj);           //  납기일 조회
            dobj  = CALLauto_demd_select_XIUD10(Conn, dobj);           //  3일청구확인저장
        }
        else
        {
            dobj  = CALLauto_demd_select_SEL1(Conn, dobj);           //  청구내역 조회
            dobj  = CALLauto_demd_select_SEL2(Conn, dobj);           //  납기일 조회
            dobj  = CALLauto_demd_select_SEL10(Conn, dobj);           //  납기일 조회
            dobj  = CALLauto_demd_select_XIUD14(Conn, dobj);           //  3일청구확인저장
        }
        return dobj;
    }
    // 청구내역 조회
    // 청구년월에 따른 청구내역을 조회한다
    public DOBJ CALLauto_demd_select_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_demd_select_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_demd_select_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.BRAN_CD  ,  XB.UPSO_CD  ,  XC.UPSO_NM  ,  XC.CLIENT_NUM  ,  XB.BANK_CD  ,  XB.AUTO_ACCNNUM  ,  XB.RESINUM  ,  XA.START_YRMN  ,  XC.MCHNDAESU  ,  XC.MNGEMSTR_NM  ,  XA.BSTYP_CD  ||  XA.UPSO_GRAD  UPSO_GRAD  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DSCT_AMT  ,  XA.DEMD_MMCNT  ,  XA.MONPRNCFEE  ,  XE.BANK_NM  ,  XF.DEPT_NM  ,  XB.RECEPTION_GBN  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  SUBSTR(A.BANK_CD  ,  1,  3)  BANK_CD  ,  A.AUTO_ACCNNUM  ,  DECODE(LENGTH(A.RESINUM),  13,  SUBSTR(A.RESINUM,0,6)||'0000000',  A.RESINUM)  AS  RESINUM  ,  A.RECEPTION_GBN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(AUTO_NUM)  AUTO_NUM  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'  GROUP  BY  UPSO_CD  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.AUTO_NUM  =  B.AUTO_NUM   \n";
        query +=" AND  A.AUTO_ACCNNUM  IS  NOT  NULL   \n";
        query +=" AND  A.RECEPTION_GBN  <>  '7'  )  XB  ,  GIBU.TBRA_UPSO  XC  ,  ACCT.TCAC_BANK  XE  ,  INSA.TCPM_DEPT  XF  WHERE  XA.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XE.BANK_CD  =  XB.BANK_CD   \n";
        query +=" AND  XF.GIBU  =  XC.BRAN_CD   \n";
        query +=" AND  XA.DEMD_GBN  =  '31'   \n";
        query +=" AND  XA.TOT_DEMD_AMT  >  0   \n";
        query +=" AND  XA.UPSO_CD  NOT  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_DEMD_REPT  WHERE  DEMD_YRMN  =  :DEMD_YRMN  )   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NOT  NULL   \n";
        query +=" AND  XC.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  MONTHS_BETWEEN(XA.END_YRMN||'01',  XA.START_YRMN||'01')+1  >  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  BETWEEN  XA.START_YRMN   \n";
        query +=" AND  XA.END_YRMN  )  ORDER  BY  XC.BRAN_CD,  XB.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        return sobj;
    }
    // 납기일 조회
    // 납기일을 조회한다. 납기일이 휴일인 경우 그 다음 일자를 조회한다
    public DOBJ CALLauto_demd_select_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_demd_select_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_demd_select_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMNDAY = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //휴일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MIN  (YRMNDAY)  PAY_DAY  FROM  INSA.TDUT_CALENDAR  WHERE  YRMNDAY  BETWEEN  TO_CHAR(ADD_MONTHS(TO_DATE(:YRMNDAY,'YYYYMM'),1),'YYYYMM')  ||  '03'   \n";
        query +=" AND  TO_CHAR(ADD_MONTHS(TO_DATE(:YRMNDAY,'YYYYMM'),1),'YYYYMM')  ||  '10'   \n";
        query +=" AND  HOLIDAY_YN  =  '0' ";
        sobj.setSql(query);
        sobj.setString("YRMNDAY", YRMNDAY);               //휴일
        return sobj;
    }
    // 3일청구확인저장
    public DOBJ CALLauto_demd_select_XIUD10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD10");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_demd_select_XIUD10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_demd_select_XIUD10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   PROC_GBN = dobj.getRetObject("S").getRecord().get("PROC_GBN");   //자동 처리 구분
        String   TRNF_RSLT ="";   //이체 결과
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_AUTO  \n";
        query +=" SET TRNF_RSLT = :TRNF_RSLT , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE , PROC_GBN = :PROC_GBN , SEND_DATE2 = SYSDATE  \n";
        query +=" WHERE DEMD_YRMN = :DEMD_YRMN  \n";
        query +=" AND UPSO_CD IN (SELECT DISTINCT XB.UPSO_CD FROM GIBU.TBRA_DEMD_AUTO XA , ( SELECT A.UPSO_CD FROM GIBU.TBRA_UPSO_AUTO A , ( SELECT UPSO_CD , MAX(AUTO_NUM) AUTO_NUM FROM GIBU.TBRA_UPSO_AUTO  \n";
        query +=" WHERE TERM_YN = 'N' GROUP BY UPSO_CD ) B  \n";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  \n";
        query +=" AND A.AUTO_NUM = B.AUTO_NUM  \n";
        query +=" AND A.AUTO_ACCNNUM IS NOT NULL ) XB , GIBU.TBRA_UPSO XC  \n";
        query +=" WHERE XA.DEMD_YRMN = :DEMD_YRMN  \n";
        query +=" AND XB.UPSO_CD = XA.UPSO_CD  \n";
        query +=" AND XC.UPSO_CD = XA.UPSO_CD  \n";
        query +=" AND XA.DEMD_GBN = '31'  \n";
        query +=" AND XA.TOT_DEMD_AMT > 0  \n";
        query +=" AND XA.UPSO_CD NOT IN ( SELECT UPSO_CD FROM GIBU.TBRA_DEMD_REPT  \n";
        query +=" WHERE DEMD_YRMN = :DEMD_YRMN )  \n";
        query +=" AND XA.TRNF_RSLT IS NOT NULL  \n";
        query +=" AND XC.CLSBS_YRMN IS NULL  \n";
        query +=" AND MONTHS_BETWEEN(XA.END_YRMN||'01', XA.START_YRMN||'01')+1 > ( SELECT COUNT(*) FROM GIBU.TBRA_NOTE  \n";
        query +=" WHERE UPSO_CD = XB.UPSO_CD  \n";
        query +=" AND NOTE_YRMN BETWEEN XA.START_YRMN  \n";
        query +=" AND XA.END_YRMN ) )";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("PROC_GBN", PROC_GBN);               //자동 처리 구분
        sobj.setString("TRNF_RSLT", TRNF_RSLT);               //이체 결과
        return sobj;
    }
    // 청구내역 조회
    // 청구년월에 따른 청구내역을 조회한다
    public DOBJ CALLauto_demd_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_demd_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_demd_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.BRAN_CD  ,  XB.UPSO_CD  ,  XC.UPSO_NM  ,  XC.CLIENT_NUM  ,  XB.BANK_CD  ,  XB.AUTO_ACCNNUM  ,  XB.RESINUM  ,  XA.START_YRMN  ,  XC.MCHNDAESU  ,  XC.MNGEMSTR_NM  ,  XA.BSTYP_CD  ||  XA.UPSO_GRAD  UPSO_GRAD  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DSCT_AMT  ,  XA.DEMD_MMCNT  ,  XA.MONPRNCFEE  ,  XE.BANK_NM  ,  XF.DEPT_NM  ,  XB.RECEPTION_GBN  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  SUBSTR(A.BANK_CD  ,  1,  3)  BANK_CD  ,  A.AUTO_ACCNNUM  ,  DECODE(LENGTH(A.RESINUM),  13,  SUBSTR(A.RESINUM,0,6)||'0000000',  A.RESINUM)  AS  RESINUM  ,  A.RECEPTION_GBN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(AUTO_NUM)  AUTO_NUM  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'  GROUP  BY  UPSO_CD  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.AUTO_NUM  =  B.AUTO_NUM   \n";
        query +=" AND  A.AUTO_ACCNNUM  IS  NOT  NULL   \n";
        query +=" AND  A.RECEPTION_GBN  <>  '7'  )  XB  ,  GIBU.TBRA_UPSO  XC  ,  ACCT.TCAC_BANK  XE  ,  INSA.TCPM_DEPT  XF  WHERE  XA.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XE.BANK_CD  =  XB.BANK_CD   \n";
        query +=" AND  XF.GIBU  =  XC.BRAN_CD   \n";
        query +=" AND  XA.DEMD_GBN  =  '31'   \n";
        query +=" AND  XA.TOT_DEMD_AMT  >  0  ORDER  BY  XC.BRAN_CD,  XB.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        return sobj;
    }
    // 납기일 조회
    // 납기일을 조회한다. 납기일이 휴일인 경우 그 다음 일자를 조회한다
    public DOBJ CALLauto_demd_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_demd_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_demd_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMNDAY = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //휴일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MIN  (YRMNDAY)  PAY_DAY  FROM  INSA.TDUT_CALENDAR  WHERE  YRMNDAY  BETWEEN  :YRMNDAY  ||  '23'   \n";
        query +=" AND  SUBSTR(:YRMNDAY,  1,  6)  ||  '31'   \n";
        query +=" AND  HOLIDAY_YN  =  '0' ";
        sobj.setSql(query);
        sobj.setString("YRMNDAY", YRMNDAY);               //휴일
        return sobj;
    }
    // 납기일 조회
    // 납기일을 조회한다. 납기일이 휴일인 경우 그 다음 일자를 조회한다
    public DOBJ CALLauto_demd_select_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_demd_select_SEL10(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_demd_select_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MIN  (YRMNDAY)  PAY_DAY  FROM  INSA.TDUT_CALENDAR  WHERE  YRMNDAY  BETWEEN  TO_CHAR(ADD_MONTHS(TO_DATE(:FROMDATE,'YYYYMMDD'),1),'YYYYMM')  ||  SUBSTR(:FROMDATE,7,2)   \n";
        query +=" AND  TO_CHAR(ADD_MONTHS(TO_DATE(:FROMDATE,'YYYYMMDD'),1),'YYYYMM')  ||  SUBSTR(:TODATE,7,2)   \n";
        query +=" AND  HOLIDAY_YN  =  '0' ";
        sobj.setSql(query);
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        return sobj;
    }
    // 3일청구확인저장
    public DOBJ CALLauto_demd_select_XIUD14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD14");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_demd_select_XIUD14(dobj, dvobj);
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
    private SQLObject SQLauto_demd_select_XIUD14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   PROC_GBN = dobj.getRetObject("S").getRecord().get("PROC_GBN");   //자동 처리 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_AUTO  \n";
        query +=" SET PROC_GBN = :PROC_GBN , SEND_DATE1 = SYSDATE  \n";
        query +=" WHERE DEMD_YRMN = :DEMD_YRMN  \n";
        query +=" AND UPSO_CD IN (SELECT XB.UPSO_CD FROM GIBU.TBRA_DEMD_AUTO XA , ( SELECT A.UPSO_CD , SUBSTR(A.BANK_CD , 1, 3) BANK_CD , A.AUTO_ACCNNUM , A.RESINUM FROM GIBU.TBRA_UPSO_AUTO A , ( SELECT UPSO_CD , MAX(AUTO_NUM) AUTO_NUM FROM GIBU.TBRA_UPSO_AUTO  \n";
        query +=" WHERE TERM_YN = 'N' GROUP BY UPSO_CD ) B  \n";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  \n";
        query +=" AND A.AUTO_NUM = B.AUTO_NUM  \n";
        query +=" AND A.AUTO_ACCNNUM IS NOT NULL ) XB , GIBU.TBRA_UPSO XC , ACCT.TCAC_BANK XE , INSA.TCPM_DEPT XF  \n";
        query +=" WHERE XA.DEMD_YRMN = :DEMD_YRMN  \n";
        query +=" AND XB.UPSO_CD = XA.UPSO_CD  \n";
        query +=" AND XC.UPSO_CD = XA.UPSO_CD  \n";
        query +=" AND XE.BANK_CD = XB.BANK_CD  \n";
        query +=" AND XF.GIBU = XC.BRAN_CD  \n";
        query +=" AND XA.DEMD_GBN = '31'  \n";
        query +=" AND XA.TOT_DEMD_AMT > 0 )";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("PROC_GBN", PROC_GBN);               //자동 처리 구분
        return sobj;
    }
    //##**$$auto_demd_select
    //##**$$auto_demd_append
    /*
    */
    public DOBJ CTLauto_demd_append(DOBJ dobj)
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
            dobj  = CALLauto_demd_append_MPD2(Conn, dobj);           //  조회
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
    public DOBJ CTLauto_demd_append( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_demd_append_MPD2(Conn, dobj);           //  조회
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 조회
    public DOBJ CALLauto_demd_append_MPD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD2");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( 1 == 2)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
            }
            else
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_demd_append_SEL6(Conn, dobj);           //  조회
                dobj  = CALLauto_demd_append_ADD7( dobj);        //  추가
            }
        }
        return dobj;
    }
    // 조회
    public DOBJ CALLauto_demd_append_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_demd_append_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_demd_append_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("R").getRecord().get("DEMD_YRMN");   //청구 년월
        String   CLIENT_NUM = dobj.getRetObject("R").getRecord().get("CLIENT_NUM");   //고객 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  CLIENT_NUM  =  :CLIENT_NUM)  UPSO_CD  ,  :CLIENT_NUM  CLIENT_NUM,   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(AUTO_NUM)  AUTO_NUM  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'   \n";
        query +=" AND  UPSO_CD  =   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  CLIENT_NUM  =  :CLIENT_NUM)  GROUP  BY  UPSO_CD  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.AUTO_NUM  =  B.AUTO_NUM)  UPSO_CNT,   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  CLIENT_NUM  =  :CLIENT_NUM)   \n";
        query +=" AND  DEMD_YRMN  =  :DEMD_YRMN  )  DEMD_CNT  ,   \n";
        query +=" (SELECT  DECODE(CLSBS_YRMN,NULL,  0,  1)  FROM  GIBU.TBRA_UPSO  WHERE  CLIENT_NUM  =  :CLIENT_NUM)  CLSBS_CHK  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("CLIENT_NUM", CLIENT_NUM);               //고객 번호
        return sobj;
    }
    // 추가
    public DOBJ CALLauto_demd_append_ADD7(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("ADD7");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        VOBJ dvobj = dobj.getRetObject("SEL6");          //사용자 화면에서 발생한 Object입니다.
        rvobj = wutil.getAddedVobj(dobj,"ADD7","", dvobj );
        rvobj.setName("ADD7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    //##**$$auto_demd_append
    //##**$$end
}
