
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s17
{
    public bra04_s17()
    {
    }
    //##**$$sel_bscon_amt
    /*
    */
    public DOBJ CTLsel_bscon_amt(DOBJ dobj)
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
            dobj  = CALLsel_bscon_amt_SEL1(Conn, dobj);           //  기준금액조회
            dobj  = CALLsel_bscon_amt_SEL2(Conn, dobj);           //  청구금액조회
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
    public DOBJ CTLsel_bscon_amt( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_bscon_amt_SEL1(Conn, dobj);           //  기준금액조회
        dobj  = CALLsel_bscon_amt_SEL2(Conn, dobj);           //  청구금액조회
        return dobj;
    }
    // 기준금액조회
    public DOBJ CALLsel_bscon_amt_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_bscon_amt_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bscon_amt_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.BSCON_CD  ,  XB.MONPRNCFEE  FROM  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  (CASE  WHEN  GIBU.FT_GET_IS_BSCON(ZA.UPSO_CD,  :END_YRMN)  >  0  THEN  TRUNC(ZB.MONPRNCFEE2  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  ZB.BSTYP_CD))  WHEN  (GIBU.FT_GET_IS_BSCON(ZA.UPSO_CD,  :END_YRMN)  =  0)   \n";
        query +=" AND  ZD.UPSO_CD  IS  NOT  NULL  THEN  NVL(ZD.MONPRNCFEE,  0)  ELSE  TO_NUMBER(NULL)  END)  AS  MONPRNCFEE  ,  (CASE  WHEN  ZD.UPSO_CD  IS  NOT  NULL  THEN  ZD.BSCON_CD  ELSE  'T0000001'  END)  AS  BSCON_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  ,  GIBU.TBRA_BSCON_CONTRINFO  ZD  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD   \n";
        query +=" AND  ZB.UPSO_CD  =  ZD.UPSO_CD(+)   \n";
        query +=" AND  ZD.APPL_DAY(+)  <  :END_YRMN  ||  '32'  )  XB  WHERE  XB.UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        return sobj;
    }
    // 청구금액조회
    public DOBJ CALLsel_bscon_amt_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_bscon_amt_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bscon_amt_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.BSCON_CD  ,  SUM(B.PROC_AMT)  AS  PROC_AMT  FROM  (   \n";
        query +=" SELECT  DEMD_YRMN  ,  BSCON_CD  ,  UPSO_CD  ,  MAX(SEQ)  AS  SEQ  ,  MAPPING_YRMN  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  REPT_YRMN  IS  NOT  NULL  GROUP  BY  DEMD_YRMN,  BSCON_CD,  UPSO_CD,  MAPPING_YRMN  )  A  ,  GIBU.TBRA_DEMD_REPT_BSCON  B  WHERE  A.DEMD_YRMN  =  B.DEMD_YRMN   \n";
        query +=" AND  A.BSCON_CD  =  B.BSCON_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.SEQ  =  B.SEQ   \n";
        query +=" AND  A.MAPPING_YRMN  =  B.MAPPING_YRMN   \n";
        query +=" AND  NOT  EXISTS   \n";
        query +=" (SELECT  1  FROM  GIBU.TBRA_BSCON_RETURN_NOTE  C  WHERE  1=1   \n";
        query +=" AND  A.DEMD_YRMN  =  C.DEMD_YRMN   \n";
        query +=" AND  A.BSCON_CD  =  C.BSCON_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.SEQ  =  C.SEQ)  GROUP  BY  B.BSCON_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    //##**$$sel_bscon_amt
    //##**$$sel_bscon_upso
    /*
    */
    public DOBJ CTLsel_bscon_upso(DOBJ dobj)
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
            dobj  = CALLsel_bscon_upso_SEL1(Conn, dobj);           //  통합징수대상업소여부조회
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
    public DOBJ CTLsel_bscon_upso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_bscon_upso_SEL1(Conn, dobj);           //  통합징수대상업소여부조회
        return dobj;
    }
    // 통합징수대상업소여부조회
    public DOBJ CALLsel_bscon_upso_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_bscon_upso_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bscon_upso_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$sel_bscon_upso
    //##**$$rept_closing
    /* * 프로그램명 : bra04_s17
    * 작성자 : 박태현
    * 작성일 : 2009/12/02
    * 설명    : 입금월의 입금마감 내역을 확인한다.
    Input
    BRAN_CD (지부 코드)
    RETURN_DAY (복직(반환) 일자)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLrept_closing(DOBJ dobj)
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
            dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  지부별마감체크
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
    public DOBJ CTLrept_closing( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  지부별마감체크
        return dobj;
    }
    // 지부별마감체크
    // 지부별 마감테이블의 해당 년월 정보가 있는지 체크한다
    public DOBJ CALLrept_closing_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_closing_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_closing_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RETURN_DAY = dobj.getRetObject("S").getRecord().get("RETURN_DAY");   //복직 일자
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(:RETURN_DAY,1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(:RETURN_DAY,5,2)  =  END_MON   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("RETURN_DAY", RETURN_DAY);               //복직 일자
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$rept_closing
    //##**$$upso_info_select
    /* * 프로그램명 : bra04_s17
    * 작성자 : 박태현
    * 작성일 : 2009/12/02
    * 설명    : 업소 기본 정보와 조회년도를 기준으로 3개년도 입금 정보를 조회한다.
    Input
    UPSO_CD (업소 코드)
    YEAR (검색년도)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLupso_info_select(DOBJ dobj)
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
            dobj  = CALLupso_info_select_SEL1(Conn, dobj);           //  원장조회
            dobj  = CALLupso_info_select_SEL2(Conn, dobj);           //  업소기본정보조회
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
    public DOBJ CTLupso_info_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_info_select_SEL1(Conn, dobj);           //  원장조회
        dobj  = CALLupso_info_select_SEL2(Conn, dobj);           //  업소기본정보조회
        return dobj;
    }
    // 원장조회
    // 조회년도를 기준으로 3개년도 입금 정보를 조회한다.  1. TENV_CODE 에서 입금구분의 명을 가져온다. 이때 입금구분의 HIGH_CD 값은 00147 이다. SQL 에서 HIGH_CD  값은 CODE_CD라는 컬럼명으로 정의해서 값을 설정한다
    public DOBJ CALLupso_info_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_info_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_info_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String BB_YEAR ="99";        //재작년
        String B_YEAR ="99";        //작년
        String   BB_YEAR_1 = dobj.getRetObject("S").getRecord().get("YEAR");   //재작년
        String   B_YEAR_1 = dobj.getRetObject("S").getRecord().get("YEAR");   //작년
        String   YEAR = dobj.getRetObject("S").getRecord().get("YEAR");   //검색년도
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  AA.UPSO_CD  BB_UPSO_CD  ,  AA.YRMN  BB_REPT_YRMN  ,  AA.MM  BB_MM  ,  AA.REPT_GBN  BB_REPT_GBN  ,  AA.CODE_NM  BB_CODE_NM  ,  AA.USE_AMT  BB_USE_AMT  ,  AA.REPT_DAY  BB_REPT_DAY  ,  AA.REPT_NUM  BB_REPT_NUM  ,  AA.RECV_DAY  BB_RECV_DAY  ,  AA.ACCU_GBN  BB_ACCU_GBN  ,  AA.DISTR_SEQ  BB_DISTR_SEQ  ,  AA.MAPPING_DAY  BB_MAPPING_DAY  ,  BB.UPSO_CD  B_UPSO_CD  ,  BB.YRMN  B_REPT_YRMN  ,  BB.MM  B_MM  ,  BB.REPT_GBN  B_REPT_GBN  ,  BB.CODE_NM  B_CODE_NM  ,  BB.USE_AMT  B_USE_AMT  ,  BB.REPT_DAY  B_REPT_DAY  ,  BB.REPT_NUM  B_REPT_NUM  ,  BB.RECV_DAY  B_RECV_DAY  ,  BB.ACCU_GBN  B_ACCU_GBN  ,  BB.DISTR_SEQ  B_DISTR_SEQ  ,  BB.MAPPING_DAY  B_MAPPING_DAY  ,  CC.UPSO_CD  ,  CC.YRMN  REPT_YRMN  ,  CC.MM  ,  CC.REPT_GBN  ,  CC.CODE_NM  ,  CC.USE_AMT  ,  CC.REPT_DAY  ,  CC.REPT_NUM  ,  CC.RECV_DAY  ,  CC.ACCU_GBN  ,  CC.DISTR_SEQ  ,  CC.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  '채권',  '22',  DECODE(ACCU_SOLCNT,  1,  '고소해결',  '고소분납'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  WHEN  REPT_GBN  =  '04'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  TO_NUMBER(:BB_YEAR_1)  -  2  ||  '01'   \n";
        query +=" AND  TO_NUMBER(:BB_YEAR_1)  -  2  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  TO_NUMBER(:BB_YEAR_1)  -  2  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  AA  ,  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  '채권',  '22',  DECODE(ACCU_SOLCNT,  1,  '고소해결',  '고소분납'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  WHEN  REPT_GBN  =  '04'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  TO_NUMBER(:B_YEAR_1)  -  1  ||  '01'   \n";
        query +=" AND  TO_NUMBER(:B_YEAR_1)  -  1  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  TO_NUMBER(:B_YEAR_1)  -  1  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  BB  ,  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  DECODE(ZA.ACCU_GBN,  NULL,  ZA.CODE_NM,  '06',  '채권',  '22',  DECODE(ACCU_SOLCNT,  1,  '고소해결',  '고소분납'))  CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.DISTR_SEQ  ,  CASE  WHEN  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  THEN  REPT_DAY  WHEN  REPT_GBN  =  '04'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NOT  NULL   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  SOL_START_YRMN   \n";
        query +=" AND  SOL_END_YRMN  )  ACCU_SOLCNT  ,  D.ACCU_CNT  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  A.DISTR_SEQ  ,  A.MAPPING_DAY  ,  C.CODE_NM  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),  0,  0,  1)  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  D  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  :YEAR  ||  '01'   \n";
        query +=" AND  :YEAR  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  :YEAR  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  CC  WHERE  BB.MM  =  AA.MM   \n";
        query +=" AND  CC.MM  =  AA.MM  ORDER  BY  AA.MM ";
        sobj.setSql(query);
        sobj.setString("BB_YEAR_1", BB_YEAR_1);               //재작년
        sobj.setString("B_YEAR_1", B_YEAR_1);               //작년
        sobj.setString("YEAR", YEAR);               //검색년도
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소기본정보조회
    public DOBJ CALLupso_info_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_info_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_info_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.UPSO_NM  ,  A.MNGEMSTR_NM  ,  B.BSTYP_CD||B.UPSO_GRAD  UPSO_GRAD  ,  C.GRADNM  ,  B.MONPRNCFEE  ,  A.STAFF_CD  ,  G.HAN_NM  STAFF_NM  ,  E.LAST_YRMN  ,  NVL(F.BALANCE,  0)  BALANCE  ,  A.OPBI_DAY  ,  A.NEW_DAY  ,  A.CLSBS_YRMN  ,  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  UPSO_ADDR  ,  A.UPSO_PHON  ,  A.BRAN_CD  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  ,  UPSO_GRAD  ,  MONPRNCFEE  ,  GRAD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  ,  UPSO_GRAD  ,  MONPRNCFEE  ,  TRIM(BSTYP_CD)  ||  UPSO_GRAD  GRAD  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  CHG_DAY  DESC,  CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  ,  (   \n";
        query +=" SELECT  DECODE(MAX(NOTE_YRMN),  NULL,  NULL,  MAX(NOTE_YRMN)  ||  '01')  LAST_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD  )  E  ,  (   \n";
        query +=" SELECT  :UPSO_CD  UPSO_CD  ,  NVL(SUM(BALANCE),  0)  BALANCE  FROM  (   \n";
        query +=" SELECT  BALANCE  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  PROC_DAY  DESC,  PROC_NUM  DESC  )  WHERE  ROWNUM  =  1  )  F  ,  INSA.TINS_MST01  G  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  C.GRAD_GBN  =  B.UPSO_GRAD   \n";
        query +=" AND  F.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  G.STAFF_NUM(+)  =  A.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$upso_info_select
    //##**$$end
}
