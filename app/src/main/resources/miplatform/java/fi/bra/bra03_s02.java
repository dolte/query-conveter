
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s02
{
    public bra03_s02()
    {
    }
    //##**$$indtnpaper_init
    /* * 프로그램명 : bra03_s02
    * 작성자 : 999999
    * 작성일 : 2009/11/02
    * 설명    : 개별지로 청구 등록시 [추가] 이벤트가 발생했을경우 화면에 default로 보여줘야 할 데이타를 조회한다.
    한 화면에서 개별지로와 MICR을 구분인자를 두어서 같이 처리한다.
    Input :
    BRAN_CD (지부 코드)
    UPSO_CD (업소 코드)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLindtnpaper_init(DOBJ dobj)
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
            dobj  = CALLindtnpaper_init_SEL1(Conn, dobj);           //  기본데이타셋팅
            dobj  = CALLindtnpaper_init_SEL3(Conn, dobj);           //  사용료변경정보
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
    public DOBJ CTLindtnpaper_init( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLindtnpaper_init_SEL1(Conn, dobj);           //  기본데이타셋팅
        dobj  = CALLindtnpaper_init_SEL3(Conn, dobj);           //  사용료변경정보
        return dobj;
    }
    // 기본데이타셋팅
    public DOBJ CALLindtnpaper_init_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLindtnpaper_init_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_init_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.MNGEMSTR_NM  ,  XB.BSTYP_CD  ,  XB.UPSO_GRAD  ,  TRIM(XB.BSTYP_CD)  ||  XB.UPSO_GRAD  GRAD  ,  XB.GRADNM  ,  XA.STAFF_CD  ,  XE.HAN_NM  STAFF_NM  ,  XB.MONPRNCFEE  ,  XA.OPBI_DAY  ,  XA.NEW_DAY  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  GIBU.FT_GET_LAST_REPT_YRMN(:UPSO_CD,  8)  LAST_YRMN  ,  XD.ACMCN_DAESU  ,  XC.END_DATE  ,  GIBU.FT_GET_START_REPT_YRMN(:UPSO_CD,  8)  START_YRMN  ,  TO_CHAR(SYSDATE,'YYYYMMDD')  END_YRMN  ,  0  USE_AMT,  0  ADDT_AMT,  0  EXT_ADDT_AMT,  NULL  REMAK  ,  XA.UPSO_STAT  FROM  GIBU.TBRA_UPSO  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XB  ,  (   \n";
        query +=" SELECT  MAX(END_YEAR  ||  END_MON  ||  END_DAY)  END_DATE  FROM  GIBU.TBRA_BRANEND  WHERE  BRAN_CD  =  :BRAN_CD  )  XC  ,  (   \n";
        query +=" SELECT  SUM(A.ACMCN_DAESU)  ACMCN_DAESU  ,  B.UPSO_CD  FROM  GIBU.TBRA_UPSO_ACMCN_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD(+)  GROUP  BY  B.UPSO_CD  )  XD  ,  INSA.TINS_MST01  XE  WHERE  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XE.STAFF_NUM(+)  =  XA.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 사용료변경정보
    public DOBJ CALLindtnpaper_init_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLindtnpaper_init_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_init_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CHG_DAY  ,  CHG_NUM  ,  CHG_BRAN  ,  TRIM(BSTYP_CD)  ||  UPSO_GRAD  GRAD  ,  MONPRNCFEE  ,  APPL_DAY  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =:UPSO_CD  ORDER  BY  APPL_DAY  DESC ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$indtnpaper_init
    //##**$$indtn_demd_amt
    /* * 프로그램명 : bra03_s02
    * 작성자 : 박태현
    * 작성일 : 2009/11/23
    * 설명    : 해당업소의 시작년월, 종료년월에 따른 청구금액을 계산한다.
    Input
    CRET_GBN (OCR/MICR 생성 구분)
    END_YRMN (종료년월)
    START_YRMN (시작년월)
    UPSO_CD (업소 코드)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLindtn_demd_amt(DOBJ dobj)
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
            dobj  = CALLindtn_demd_amt_SEL1(Conn, dobj);           //  청구데이터 조회
            dobj  = CALLindtn_demd_amt_OSP1(Conn, dobj);           //  청구 금액 생성
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
    public DOBJ CTLindtn_demd_amt( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLindtn_demd_amt_SEL1(Conn, dobj);           //  청구데이터 조회
        dobj  = CALLindtn_demd_amt_OSP1(Conn, dobj);           //  청구 금액 생성
        return dobj;
    }
    // 청구데이터 조회
    // 업소코드, 시작년월, 종료년월을 기준으로 청구데이터를 조회한다
    public DOBJ CALLindtn_demd_amt_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLindtn_demd_amt_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtn_demd_amt_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CRET_GBN = dobj.getRetObject("S").getRecord().get("CRET_GBN");   //OCR/MICR 생성 구분
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   RECV_DAY ="";   //영수 일자
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  NVL(:CRET_GBN,  'O')  AS  CRET_GBN  ,  SUBSTR(:START_YRMN,  1,  6)  AS  START_YRMN  ,  SUBSTR(:END_YRMN,  1,  6)  AS  END_YRMN  ,  :RECV_DAY  AS  RECV_DAY  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("CRET_GBN", CRET_GBN);               //OCR/MICR 생성 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("RECV_DAY", RECV_DAY);               //영수 일자
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 청구 금액 생성
    // 해당 업소의 청구 금액를 생성하기 위한 프로시저 (GIBU.SP_GET_USE_AMT) 를 호출한다
    public DOBJ CALLindtn_demd_amt_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL1");         //청구데이터 조회에서 생성시킨 OBJECT입니다.(CALLindtn_demd_amt_SEL1)
        String[]  incolumns ={"UPSO_CD","START_YRMN","END_YRMN","CRET_GBN","RECV_DAY"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CRET_GBN ="O";         //청구 년월
            record.put("CRET_GBN",CRET_GBN);
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_GET_DEMD_AMT";
        int[]    intypes={12, 12, 12, 12, 12};;
        String[] outcolnms={"BSTYP_CD","UPSO_GRAD","MONPRNCFEE","DEMD_GBN","DEMD_MMCNT","TOT_USE_AMT","TOT_ADDT_AMT","TOT_EADDT_AMT","DSCT_AMT","TOT_DEMD_AMT"};;
        int[]    outtypes ={12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP1");
        robj.setRetcode(1);
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$indtn_demd_amt
    //##**$$rept_detail
    /* * 프로그램명 : bra03_s02
    * 작성자 : 박태현
    * 작성일 : 2009/09/08
    * 설명    : 조회년월을 기준으로 해당 업소의 3년간의 원장정보를 조회한다.
    Input
    UPSO_CD (업소 코드)
    YEAR (검색년도)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLrept_detail(DOBJ dobj)
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
            dobj  = CALLrept_detail_SEL1(Conn, dobj);           //  원장조회
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
    public DOBJ CTLrept_detail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_detail_SEL1(Conn, dobj);           //  원장조회
        return dobj;
    }
    // 원장조회
    // 조회년도를 기준으로 3개년도 입금 정보를 조회한다.  1. TENV_CODE 에서 입금구분의 명을 가져온다. 이때 입금구분의 HIGH_CD 값은 00147 이다. SQL 에서 HIGH_CD  값은 CODE_CD라는 컬럼명으로 정의해서 값을 설정한다
    public DOBJ CALLrept_detail_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
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
        query +=" AND  '19'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
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
        query +=" AND  '19'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
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
        query +=" AND  '19'  THEN  REPT_DAY  ELSE  MAPPING_DAY  END  MAPPING_DAY  FROM  (   \n";
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
    //##**$$rept_detail
    //##**$$indtnpaper_detail
    /* * 프로그램명 : bra03_s02
    * 작성자 : 서정재
    * 작성일 : 2009/11/11
    * 설명 : 등록된 개별지로의 상세정보 (업소명, 청구월수, 청구금액,  가산금액 등) 와 사용료 정보를 조회한다.
    Input
    BRAN_CD (지부 코드)
    CRET_GBN (OCR/MICR 생성 구분)
    DEMD_DAY (청구 일자)
    DEMD_NUM (청구 번호)
    UPSO_CD (업소 코드)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLindtnpaper_detail(DOBJ dobj)
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
            dobj  = CALLindtnpaper_detail_SEL4(Conn, dobj);           //  청구정보
            dobj  = CALLindtnpaper_detail_SEL6(Conn, dobj);           //  사용료변경정보
            dobj  = CALLindtnpaper_detail_SEL7(Conn, dobj);           //  출력용데이터 조회
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
    public DOBJ CTLindtnpaper_detail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLindtnpaper_detail_SEL4(Conn, dobj);           //  청구정보
        dobj  = CALLindtnpaper_detail_SEL6(Conn, dobj);           //  사용료변경정보
        dobj  = CALLindtnpaper_detail_SEL7(Conn, dobj);           //  출력용데이터 조회
        return dobj;
    }
    // 청구정보
    public DOBJ CALLindtnpaper_detail_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLindtnpaper_detail_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_detail_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");   //청구 번호
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.DEMD_DAY  ,  XB.DEMD_NUM  ,  XB.BRAN_CD  ,  XA.MNGEMSTR_NM  ,  XC.BSTYP_CD  ,  XC.UPSO_GRAD  ,  TRIM(XC.BSTYP_CD)  ||  XC.UPSO_GRAD  GRAD  ,  XC.GRADNM  ,  XC.MONPRNCFEE  ,  XA.STAFF_CD  ,  XE.HAN_NM  STAFF_NM  ,  XA.OPBI_DAY  ,  XA.NEW_DAY  ,  XA.UPSO_NEW_ADDR1  ||  DECODE(XA.UPSO_NEW_ADDR2,  '',  '',  ',  '||XA.UPSO_NEW_ADDR2)  ||  XA.UPSO_REF_INFO  ADDR  ,  XB.START_YRMN  ||  '01'  START_YRMN  ,  XB.END_YRMN  ||  '01'  END_YRMN  ,  XB.TOT_DEMD_AMT  -  XB.TOT_ADDT_AMT  -  XB.TOT_EADDT_AMT  TOT_USE_AMT  ,  XB.TOT_ADDT_AMT  ,  XB.TOT_DEMD_AMT  ,  XB.REMAK  ,  GIBU.FT_GET_LAST_REPT_YRMN(:UPSO_CD,  8)  LAST_YRMN  ,  XA.MCHNDAESU  ACMCN_DAESU  ,  XB.TOT_EADDT_AMT  ,  XD.END_DATE  ,   \n";
        query +=" (SELECT  DECODE(SUM(NONPY_AMT),  null,  -1,  0,  0,  1)  FROM  GIBU.TBRA_MISU_CHEKWON  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NONPY_DAY  =  :DEMD_DAY)  AS  MISU_CLOSED  FROM  GIBU.TBRA_UPSO  XA  ,  GIBU.TBRA_DEMD_INDTN  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.MONPRNCFEE  ,  ZC.GRADNM  ,  TRIM(ZB.BSTYP_CD)  BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =  :UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XC  ,  (   \n";
        query +=" SELECT  MAX(END_YEAR  ||  END_MON  ||  END_DAY)  END_DATE  FROM  GIBU.TBRA_BRANEND  WHERE  BRAN_CD  =  :BRAN_CD  )  XD  ,  INSA.TINS_MST01  XE  WHERE  XB.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.DEMD_DAY  =  :DEMD_DAY   \n";
        query +=" AND  XB.DEMD_NUM  =  :DEMD_NUM   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XA.STAFF_CD  =  XE.STAFF_NUM(+) ";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구 번호
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 사용료변경정보
    public DOBJ CALLindtnpaper_detail_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLindtnpaper_detail_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_detail_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CHG_DAY  ,  CHG_NUM  ,  CHG_BRAN  ,  TRIM(BSTYP_CD)  ||  UPSO_GRAD  GRAD  ,  MONPRNCFEE  ,  APPL_DAY  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =:UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 출력용데이터 조회
    public DOBJ CALLindtnpaper_detail_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLindtnpaper_detail_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        rvobj.Println("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_detail_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");   //청구서번호
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.UPSO_CD  ,  XB.UPSO_NM  ,  DECODE(XB.PAYPRES_GBN,  'B',  XB.MNGEMSTR_NM,  XB.PERMMSTR_NM)  PAYPRES_NM  ,  DECODE(XB.MAIL_RCPT,  'U',  XB.UPSO_NEW_ADDR1  ||  DECODE(XB.UPSO_NEW_ADDR2,  '',  '',  ',  '||XB.UPSO_NEW_ADDR2)  ||  XB.UPSO_REF_INFO,  'B',  XB.MNGEMSTR_NEW_ADDR1  ||  DECODE(XB.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||XB.MNGEMSTR_NEW_ADDR2)  ||  XB.MNGEMSTR_REF_INFO,  XB.PERMMSTR_NEW_ADDR1  ||  DECODE(XB.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||XB.PERMMSTR_NEW_ADDR2)  ||  XB.PERMMSTR_REF_INFO  )  UPSO_ADDR  ,  DECODE(XB.MAIL_RCPT,  'U',  XB.UPSO_NEW_ZIP,  'B',  XB.MNGEMSTR_NEW_ZIP,  XB.PERMMSTR_NEW_ZIP)  UPSO_ZIP  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  -  (XA.TOT_ADDT_AMT  +  XA.TOT_EADDT_AMT)  -  XA.MONPRNCFEE  NONPY_AMT  ,  XA.TOT_ADDT_AMT  +  XA.TOT_EADDT_AMT  TOT_ADDT_AMT  ,  XA.TOT_DEMD_AMT  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XC.BIPLC_NM  BRAN_NM  ,  XC.ADDR||'  '||XC.HNM  BRAN_ADDR  ,  XC.POST_NUM  ,  XC.PHON_NUM  BRAN_PHON  ,  XC.FAX_NUM  BRAN_FAX  ,  XD.GRADNM  ,  XA.DEMD_DAY  ,  XA.DEMD_NUM  ,  DECODE(XA.PRNT_DAY,  NULL,  0,  1)  PRINTED  ,  XB.MAIL_RCPT  ,  XB.CLIENT_NUM  ,  XA.CRET_GBN  FROM  GIBU.TBRA_DEMD_INDTN  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_BIPLC  XC  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZC.GRADNM  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  XD  WHERE  XB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  XA.DEMD_DAY  =  :DEMD_DAY   \n";
        query +=" AND  XA.DEMD_NUM  =  :DEMD_NUM   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XD.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XC.GIBU  =  XB.BRAN_CD  ORDER  BY  XA.DEMD_DAY,  XA.DEMD_NUM ";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$indtnpaper_detail
    //##**$$p_indtnpaper_select
    /* * 프로그램명 : bra03_s02
    * 작성자 : 서정재
    * 작성일 : 2009/11/11
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLp_indtnpaper_select(DOBJ dobj)
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
            dobj  = CALLp_indtnpaper_select_SEL2(Conn, dobj);           //  체크
            dobj  = CALLp_indtnpaper_select_SEL1(Conn, dobj);           //  전체리스트
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
    public DOBJ CTLp_indtnpaper_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLp_indtnpaper_select_SEL2(Conn, dobj);           //  체크
        dobj  = CALLp_indtnpaper_select_SEL1(Conn, dobj);           //  전체리스트
        return dobj;
    }
    // 체크
    public DOBJ CALLp_indtnpaper_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_indtnpaper_select_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_indtnpaper_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(LENGTH(:DEMD_DAY),8,'1',7,'2',  6,'2',5,'3',4,'3','4')  GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        return sobj;
    }
    // 전체리스트
    public DOBJ CALLp_indtnpaper_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_indtnpaper_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_indtnpaper_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");   //청구 번호
        String   CRET_GBN = dobj.getRetObject("S").getRecord().get("CRET_GBN");   //OCR/MICR 생성 구분
        String   GBN = dobj.getRetObject("SEL2").getRecord().get("GBN");   //구분
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //업소 명
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.UPSO_CD , A.UPSO_NM , B.DEMD_DAY , B.DEMD_NUM , B.BRAN_CD , B.DEMD_DAY || '-' || B.DEMD_NUM || '-' || B.BRAN_CD DEMD , A.MNGEMSTR_NM  ";
        query +=" FROM GIBU.TBRA_UPSO A , GIBU.TBRA_DEMD_INDTN B  ";
        query +=" WHERE A.BRAN_CD = :BRAN_CD  ";
        query +=" AND A.UPSO_NM LIKE '%' || :UPSO_NM || '%'  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND B.CRET_GBN = :CRET_GBN  ";
        if( GBN.equals("1"))
        {
            query +=" AND DEMD_DAY = :DEMD_DAY  ";
            query +=" AND NVL(:GBN, '-') = NVL(:GBN, '-')  ";
        }
        if( GBN.equals("2"))
        {
            query +=" AND DEMD_DAY BETWEEN SUBSTR(:DEMD_DAY,0,6) || '01'  ";
            query +=" AND SUBSTR(:DEMD_DAY,0,6) || '31'  ";
            query +=" AND NVL(:GBN, '-') = NVL(:GBN, '-')  ";
        }
        if( GBN.equals("3"))
        {
            query +=" AND DEMD_DAY LIKE SUBSTR(:DEMD_DAY,0,4) || '%'AND NVL(:GBN, '-') = NVL(:GBN, '-')  ";
        }
        query +=" AND NVL(B.DEMD_NUM, '-') LIKE DECODE(:DEMD_NUM, NULL, NVL(B.DEMD_NUM, '-'), '%' || :DEMD_NUM || '%')  ";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구 번호
        sobj.setString("CRET_GBN", CRET_GBN);               //OCR/MICR 생성 구분
        if( GBN.equals("3"))
        {
            sobj.setString("GBN", GBN);               //구분
        }
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$p_indtnpaper_select
    //##**$$insert_indtn
    /*
    */
    public DOBJ CTLinsert_indtn(DOBJ dobj)
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
            dobj  = CALLinsert_indtn_MPD8(Conn, dobj);           //  분기
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
    public DOBJ CTLinsert_indtn( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLinsert_indtn_MPD8(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLinsert_indtn_MPD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD8");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( 1 == 1)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLinsert_indtn_SEL20(Conn, dobj);           //  DEMD_NUM생성
                dobj  = CALLinsert_indtn_XIUD9(Conn, dobj);           //  등록
                dobj  = CALLinsert_indtn_SEL1(Conn, dobj);           //  저장결과
                dobj  = CALLinsert_indtn_ADD11( dobj);        //  카운트 합
            }
        }
        return dobj;
    }
    // DEMD_NUM생성
    public DOBJ CALLinsert_indtn_SEL20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL20");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL20");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLinsert_indtn_SEL20(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL20");
        rvobj.Println("SEL20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinsert_indtn_SEL20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(DEMD_NUM),  0)  +  1,  4,  '0')  DEMD_NUM  FROM  GIBU.TBRA_DEMD_INDTN  WHERE  DEMD_DAY  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 등록
    public DOBJ CALLinsert_indtn_XIUD9(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLinsert_indtn_XIUD9(dobj, dvobj);
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
    private SQLObject SQLinsert_indtn_XIUD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dobj.getRetObject("SEL20").getRecord().get("DEMD_NUM");   //청구서번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_DEMD_INDTN (DEMD_DAY , DEMD_NUM , UPSO_CD , CRET_GBN , BRAN_CD , START_YRMN , END_YRMN , DEMD_GBN , BSTYP_CD , UPSO_GRAD , MONPRNCFEE , DEMD_MMCNT , TOT_USE_AMT , TOT_ADDT_AMT , TOT_EADDT_AMT , TOT_DEMD_AMT , DSCT_AMT , REMAK , INSPRES_ID , INS_DATE ) SELECT TO_CHAR(SYSDATE, 'YYYYMMDD') AS DEMD_DAY , :DEMD_NUM , XB.UPSO_CD , 'M' AS CRET_GBN , XB.BRAN_CD , XA.START_YRMN , XA.END_YRMN , '33' AS DEMD_GBN , XA.BSTYP_CD , XA.UPSO_GRAD , XA.MONPRNCFEE , XA.DEMD_MMCNT , XA.TOT_USE_AMT , XA.TOT_ADDT_AMT , XA.TOT_EADDT_AMT , XA.TOT_DEMD_AMT , XA.DSCT_AMT , 'SMS' AS REMAK , :INSPRES_ID , SYSDATE FROM GIBU.TBRA_DEMD_OCR XA , GIBU.TBRA_UPSO XB WHERE XB.UPSO_CD = :UPSO_CD AND XA.DEMD_YRMN = (SELECT MAX(DEMD_YRMN) FROM GIBU.TBRA_DEMD_OCR WHERE UPSO_CD = :UPSO_CD) AND XA.UPSO_CD = XB.UPSO_CD AND XA.TOT_DEMD_AMT > 0 ORDER BY XB.BRAN_CD, XB.UPSO_CD";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 저장결과
    public DOBJ CALLinsert_indtn_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLinsert_indtn_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinsert_indtn_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_DEMD_INDTN  WHERE  DEMD_DAY  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  REMAK  =  'SMS'   \n";
        query +=" AND  INSPRES_ID  =  :INSPRES_ID   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 카운트 합
    public DOBJ CALLinsert_indtn_ADD11(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("ADD11");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        VOBJ dvobj = dobj.getRetObject("SEL1");          //사용자 화면에서 발생한 Object입니다.
        rvobj = wutil.getAddedVobj(dobj,"ADD11","CNT", dvobj );
        rvobj.setName("ADD11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    //##**$$insert_indtn
    //##**$$end
}
