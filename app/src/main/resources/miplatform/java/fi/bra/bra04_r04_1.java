
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_r04_1
{
    public bra04_r04_1()
    {
    }
    //##**$$upso_rept_select
    /* * 프로그램명 : bra04_r04
    * 작성자 : 서정재
    * 작성일 : 2009/09/16
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    2009.12.15 원장조회시 더 넣은 금액 추가
    2010.02.24 구분 표시에 고소분납/고소해결 코딩 추가
    2010.08.09 권남균
    - 고소조회 방식 변경(SEL1)
    */
    public DOBJ CTLupso_rept_select(DOBJ dobj)
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
            dobj  = CALLupso_rept_select_SEL5(Conn, dobj);           //  오프라인데이타설치업소체크
            dobj  = CALLupso_rept_select_SEL1(Conn, dobj);           //  업소기본정보조회
            dobj  = CALLupso_rept_select_SEL4(Conn, dobj);           //  원장조회
            dobj  = CALLupso_rept_select_SEL3(Conn, dobj);           //  업소변경이력조회
            dobj  = CALLupso_rept_select_SEL12(Conn, dobj);           //  더넣은금액조회
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
    public DOBJ CTLupso_rept_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_rept_select_SEL5(Conn, dobj);           //  오프라인데이타설치업소체크
        dobj  = CALLupso_rept_select_SEL1(Conn, dobj);           //  업소기본정보조회
        dobj  = CALLupso_rept_select_SEL4(Conn, dobj);           //  원장조회
        dobj  = CALLupso_rept_select_SEL3(Conn, dobj);           //  업소변경이력조회
        dobj  = CALLupso_rept_select_SEL12(Conn, dobj);           //  더넣은금액조회
        return dobj;
    }
    // 오프라인데이타설치업소체크
    public DOBJ CALLupso_rept_select_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_rept_select_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_rept_select_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CASE  WHEN  (CNT=1   \n";
        query +=" AND  KY_RESULT=1)  THEN  '오프,무선로그  '  ||  BSCON_NM  WHEN  (CNT=1   \n";
        query +=" AND  KY_RESULT=2)  THEN  '오프,Before로그'  WHEN  (CNT=1   \n";
        query +=" AND  (KY_RESULT  IS  NULL   \n";
        query +=" OR  KY_RESULT=0))  THEN  '오프라인'  WHEN  (CNT=0   \n";
        query +=" AND  KY_RESULT=1)  THEN  '무선로그  '  ||  BSCON_NM  WHEN  (CNT=0   \n";
        query +=" AND  KY_RESULT=2)  THEN  'Before로그'  ELSE  ''  END  AS  ONOFF_GBN  FROM  (   \n";
        query +=" SELECT  (   \n";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_OFF_UPSO_MNG  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  ESTAB_YRMN  =  TO_CHAR(SYSDATE,  'YYYYMM')  )  AS  CNT,  (   \n";
        query +=" SELECT  CASE  WHEN  (DSCT_START  IS  NULL  )  THEN  0  WHEN  (DSCT_START  <=  TO_CHAR(SYSDATE,  'YYYYMM')   \n";
        query +=" AND  DSCT_END  IS  NULL   \n";
        query +=" AND  DSCT_YN  =  '1')  THEN  1  WHEN  (DSCT_START  <=  TO_CHAR(SYSDATE,  'YYYYMM')   \n";
        query +=" AND  DSCT_END  >=  TO_CHAR(SYSDATE,  'YYYYMM')   \n";
        query +=" AND  DSCT_YN  =  '1')  THEN  1  WHEN  (DSCT_START  <=  TO_CHAR(SYSDATE,  'YYYYMM')   \n";
        query +=" AND  DSCT_END  <=  TO_CHAR(SYSDATE,  'YYYYMM')   \n";
        query +=" AND  DSCT_YN  =  '1')  THEN  2  ELSE  0  END  FROM  LOG.KDS_SHOP  WHERE  UPSO_CD  =  :UPSO_CD  )  AS  KY_RESULT,  (   \n";
        query +=" SELECT  B.CODE_NM  FROM  LOG.KDS_SHOPROOM  A  ,  FIDU.TENV_CODE  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.HIGH_CD  =  '00429'   \n";
        query +=" AND  A.BSCON_CD  =  B.CODE_CD   \n";
        query +=" AND  A.CO_STATUS  <>  '07005'   \n";
        query +=" AND  ROWNUM  =  1  )  AS  BSCON_NM  FROM  DUAL  ) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소기본정보조회
    public DOBJ CALLupso_rept_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_rept_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_rept_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ONOFF_GBN = dobj.getRetObject("SEL5").getRecord().get("ONOFF_GBN");   //온오프 구분
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.MNGEMSTR_NM  ,  XA.MNGEMSTR_RESINUM  ,  XA.BIOWN_NUM  ,  XA.MNGEMSTR_HPNUM  ,  XA.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  XA.UPSO_PHON  ,  DECODE(XC.CNT,  0,  '',  '자동이체')  AUTO_YN  ,  (   \n";
        query +=" SELECT  DECODE(CHK,0,DECODE(JUDG_CD,  '2',  '기소중지','3',  '기소유예','4','구약식','고소중'),1,  DECODE(JUDG_CD,  '2','기소중지',  '4',  '구약식'),'')  ACCU_YN  FROM(   \n";
        query +=" SELECT  DECODE(COMPN_DAY,NULL,0,1)  CHK  ,  JUDG_CD  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  ORDER  BY  ACCU_DAY,  ACCU_NUM  DESC  )  WHERE  ROWNUM  =  1  )  AS  ACCU_YN  ,  XB.MONPRNCFEE  ,  XB.MONPRNCFEE2  ,  XA.CLIENT_NUM  ,  XD.ACCU_DAY  ,  XD.ACCU_NUM  ,  XD.ACCU_BRAN  ,  XD.ACCU_GBN  ,  (   \n";
        query +=" SELECT  COUNT(ACCU_DAY)  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD  )  ACCU_CNT  ,  :ONOFF_GBN  ONOFF_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(*),  0,'',  '채권의뢰')  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  CLAIM_GBN  ,   \n";
        query +=" (SELECT  NVL(MAX(APPTN_YRMN),'')  APPTN_YRMN  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL)  CLAIM_APPTN_YRMN  ,  DECODE(TRIM(XB.BSTYP_CD),  'o',  GIBU.FT_GET_NOREBANG_GRAD(XA.UPSO_CD),  XB.GRADNM)  BSTYP_NM  ,  XF.PHON_NUM  BRAN_PHON_NUM  ,   \n";
        query +=" (SELECT  DECODE(COL_MCH_YN,  '1','불가','')  FROM  GIBU.SDB_TBRA_UPSO  WHERE  UPSO_CD  =  XA.UPSO_CD)  AS  COL_MCH_YN  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZB.MONPRNCFEE2,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  XB  ,  (   \n";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  TERM_YN  =  'N')  XC  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  ACCU_GBN  ,  DECODE(CHK,0,DECODE(JUDG_CD,  '2',  '기소중지','4','구약식','고소중'),1,  DECODE(JUDG_CD,  '2','기소중지',  '4',  '구약식'),'')  ACCU_YN  FROM(   \n";
        query +=" SELECT  UPSO_CD  ,  ACCU_DAY  ,  ACCU_NUM  ,  ACCU_BRAN  ,  ACCU_GBN  ,  DECODE(COMPN_DAY,NULL,0,1)  CHK  ,  JUDG_CD  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  ACCU_DAY,  ACCU_NUM  DESC  )  WHERE  ROWNUM  =  1  )  XD  ,  INSA.TCPM_BIPLC  XF  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XF.GIBU  =  XA.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //온오프 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 원장조회
    // 조회년도를 기준으로 3개년도 입금 정보를 조회한다.  1. TENV_CODE 에서 입금구분의 명을 가져온다. 이때 입금구분의 HIGH_CD 값은 00147 이다. SQL 에서 HIGH_CD  값은 CODE_CD라는 컬럼명으로 정의해서 값을 설정한다
    public DOBJ CALLupso_rept_select_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_rept_select_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_rept_select_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // 업소변경이력조회
    public DOBJ CALLupso_rept_select_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_rept_select_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_rept_select_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.UPSO_CD  ,  B.UPSO_NM  ,  B.MNGEMSTR_NM  ,  B.OPBI_DAY  ,  (   \n";
        query +=" SELECT  MAX(NOTE_YRMN)  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  REPT_GBN  IN  ('01','02','03','04','05','06')  )  REPT_YRMN  ,  B.CLSBS_YRMN  ,  B.CLSBS_INS_DAY  ,   \n";
        query +=" (SELECT  MAX(STTNT_DAY)  FROM  GIBU.TBRA_UPSO_CLSED  WHERE  CLSED_GBN  IN  ('14','01','02','03','04')   \n";
        query +=" AND  UPSO_CD  =  B.UPSO_CD)  AS  STTNT_DAY  ,   \n";
        query +=" (SELECT  MAX(REMAK)  FROM  GIBU.TBRA_UPSO_CLSED  WHERE  CLSED_GBN  IN  ('14','01','02','03','04')   \n";
        query +=" AND  UPSO_CD  =  B.UPSO_CD)  AS  REMAK  FROM(   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  START  WITH  UPSO_CD  =  :UPSO_CD  CONNECT  BY  PRIOR  BEFORE_UPSO_CD  =  UPSO_CD  UNION   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  START  WITH  UPSO_CD  =  :UPSO_CD  CONNECT  BY  PRIOR  UPSO_CD  =  BEFORE_UPSO_CD  )  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  ORDER  BY  B.OPBI_DAY  DESC,  B.INS_DATE  DESC ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 더넣은금액조회
    public DOBJ CALLupso_rept_select_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_rept_select_SEL12(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL12");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_rept_select_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(BALANCE,0)  BALANCE  FROM  (   \n";
        query +=" SELECT  BALANCE  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  PROC_DAY  DESC,  PROC_NUM  DESC  )  WHERE  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$upso_rept_select
    //##**$$end
}
