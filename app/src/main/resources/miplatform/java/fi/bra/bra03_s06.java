
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s06
{
    public bra03_s06()
    {
    }
    //##**$$auto_demd_init
    /* * 프로그램명 : bra03_s06
    * 작성자 : 박태현
    * 작성일 : 2009/08/28
    * 설명    : 청구월의 지부별 자동이체 청구자료 생성여부를 조회한다.
    Input
    DEMD_YRMN (DEMD_YRMN)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLauto_data_init(DOBJ dobj)
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
            dobj  = CALLauto_demd_init_SEL1(Conn, dobj);           //  지부별 청구 완료정보 조회
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
    public DOBJ CTLauto_data_init( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_demd_init_SEL1(Conn, dobj);           //  지부별 청구 완료정보 조회
        return dobj;
    }
    // 지부별 청구 완료정보 조회
    // 지부별로 자동이체 진행정보를 조회한다
    public DOBJ CALLauto_demd_init_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_demd_init_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_demd_init_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dvobj.getRecord().get("DEMD_YRMN");   //청구 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.GIBU  BRAN_CD  ,  XA.DEPT_NM  ,  DECODE(XB.DEMD_YRMN,  NULL,  NULL,  '완료')  BRAN_END  ,  XB.INS_DAY  FROM  INSA.TCPM_DEPT  XA  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  MAX(DEMD_YRMN)  DEMD_YRMN  ,  TO_CHAR(MAX(INS_DATE),  'YYYYMMDD')  INS_DAY  FROM  GIBU.TBRA_DEMD_AUTO  WHERE  DEMD_YRMN  =  :DEMD_YRMN  GROUP  BY  BRAN_CD  )  XB  WHERE  XA.DEPT_CD  BETWEEN  '106010100'   \n";
        query +=" AND  '106019999'   \n";
        query +=" AND  XA.GIBU  =  XB.BRAN_CD  (+)  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        return sobj;
    }
    //##**$$auto_demd_init
    //##**$$auto_data_make
    /* 마감관리의 업소별일일마감을 실행한후 처리되어야 한다.
    현업 (sp_m_auto 프로시져로 작업/ 아래 내용은 프로시져 분석내용임)
    1. tb_ziro 삭제(조건:gibu,yymm)
    2. tb_svauto  삭제(조건:gibu,yymm)
    3. tb_minab 삭제(조건:지부)
    [필요한 데이타 생성]
    cnt            number := 0;          -- 전체 Read Count
    cnt2           number := 0;          -- 생성 Count
    minab_wol      number := 0;          -- 미납개월
    minab_wol1     number := 0;          -- 미납개월[MONTHS_BETWEEN(TO_DATE(i_yymm,'YYYYMM'), TO_DATE(s_yymm,'YYYYMM')) + 1]
    minab_wol2     number := 0;          -- 미납개월(i_yynn - Max(Jango.yymm) + 1)
    check_wol      number := 0;          -- 등록월로부터 가산금적용월
    start_wol      varchar2(06);         -- 지로시작월
    c_yymm         varchar2(06);         -- 계산년월
    j_yymm         varchar2(06);         -- 년월(yyyymm) => TB_JANGO.yymm용
    s_yymm         varchar2(06);         -- 최종잔고 다음월 => MAX(TB_JANGO) + 1달
    m_sil_date     varchar2(08);         -- TB_MODI.sil_date
    m_split        varchar2(01);         -- TB_MINAB.split
    ziro_gubun     varchar2(01);
    ziro_wol_kum   number := 0;
    i              number := 0;
    check_days     number := 0;          -- 영업일 체크
    modi_kum       number := 0;
    modi_kum_o     number := 0;
    kum            number := 0;
    gasan_kum      number := 0;
    dc_kum         number := 0;          -- 할인액(원금*0.01)
    u_kum          number := 0;          -- 업소별 월정료
    u_gasan_kum    number := 0;          -- 업소별 가산금
    u_dc_kum       number := 0;          -- 업소별 할인액
    u_minab_wol    number := 0;          -- 업소별 총미납월
    ck_goso        number := 0;          -- 고소카운트(고소유무)
    ck_jango       number := 0;          -- 잔고카운트(잔고유무)
    j_cnt          number := 0;          -- 잔고카운트(입금개월수)
    gasan_yul      number := 0.1;        -- 가산율(2005/08이전은 원금의0.1, 이후는 원금의0.05로 적용)
    dc_yul         number := 0.01;       -- 자동이체 할인율(2006/09부터 자동이체시 1%할인 적용)
    4. 고소체크
    -.고소건수가 있으면 ziro_gubun: 1
    5. 최종납일월 체크
    -.TB_JANGO 테이블에서 해당 업소의 최종납입월을 조회한다.
    -.조회된 최종납입월+1한 값을 구한다.
    -.만약 납입월 정보가 없을경우에는 업소의 OPEN_DATE를 활용한다.
    6. 미납생성
    -.미납개월수 조회
    -.잔고카운드 GET
    -.
    */
    public DOBJ CTLauto_data_make(DOBJ dobj)
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
            dobj  = CALLauto_data_make_OSP1(Conn, dobj);           //  청구 데이터 생성
            dobj  = CALLauto_data_make_SEL2(Conn, dobj);           //  청구 데이터 오류 내역
            dobj  = CALLauto_data_make_SEL3(Conn, dobj);           //  지부별 청구 완료 정보 조회
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
    public DOBJ CTLauto_data_make( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_data_make_OSP1(Conn, dobj);           //  청구 데이터 생성
        dobj  = CALLauto_data_make_SEL2(Conn, dobj);           //  청구 데이터 오류 내역
        dobj  = CALLauto_data_make_SEL3(Conn, dobj);           //  지부별 청구 완료 정보 조회
        return dobj;
    }
    // 청구 데이터 생성
    // 청구 데이터를 생성하기 위한 프로시저 (GIBU.SP_PROC_DEMDGIRO) 를 호출한다
    public DOBJ CALLauto_data_make_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        String[]  incolumns ={"BRAN_CD","DEMD_YRMN","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
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
        String   spname ="GIBU.SP_PROC_DEMD_AUTO";
        int[]    intypes={12, 12, 12};;
        String[] outcolnms={"P_CNT_READ","P_CNT_INST","P_CNT_OCR","P_CNT_PREPAY","P_CNT_ERR"};;
        int[]    outtypes ={12, 12, 12, 12, 12};;
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
        dobj.setRetObject(robj);
        return dobj;
    }
    // 청구 데이터 오류 내역
    // 청구 데이터 생성 시 발생한 오류 내역
    public DOBJ CALLauto_data_make_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_data_make_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_data_make_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEMD_YRMN  ,  A.SNUM  ,  C.CODE_NM  DEMD_NM  ,  A.DEMD_GBN  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.ERR_GBN  ,  A.ERR_CTENT  FROM  GIBU.TBRA_DEMD_ERR  A  ,  GIBU.TBRA_UPSO  B  ,  FIDU.TENV_CODE  C  WHERE  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  A.CRET_GBN  =  'A'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  A.DEMD_GBN  =  C.CODE_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 지부별 청구 완료 정보 조회
    // 지부별로 자동이체 진행정보를 조회한다
    public DOBJ CALLauto_data_make_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_data_make_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_data_make_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dvobj.getRecord().get("DEMD_YRMN");   //청구 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.GIBU  BRAN_CD  ,  XA.DEPT_NM  ,  DECODE(XB.DEMD_YRMN,  NULL,  NULL,  '완료')  BRAN_END  FROM  INSA.TCPM_DEPT  XA  ,  (   \n";
        query +=" SELECT  DISTINCT  B.BRAN_CD  ,  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD  )  XB  WHERE  XA.DEPT_CD  BETWEEN  '106010100'   \n";
        query +=" AND  '106019999'   \n";
        query +=" AND  XA.GIBU  =  XB.BRAN_CD  (+)  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        return sobj;
    }
    //##**$$auto_data_make
    //##**$$rept_closing
    /*
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
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        String message ="해당기간중 마감내역이 존재합니다. 마감내역을 확인하세요.";
        dobj.setRetmsg(message);
        return dobj;
    }
    private SQLObject SQLrept_closing_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  END_YEAR  ||  END_MON  =  :DEMD_YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD) ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$rept_closing
    //##**$$auto_data_make_test
    /*
    */
    public DOBJ CTLauto_data_make_test(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("A"))
            {
                dobj  = CALLauto_data_make_test_OSP1(Conn, dobj);           //  자동이체 청구 데이터 생성
                dobj  = CALLauto_data_make_test_MRG1( dobj);        //  결과숫자 조회
                dobj  = CALLauto_data_make_test_SEL2(Conn, dobj);           //  청구 데이터 오류 내역
                dobj  = CALLauto_data_make_test_SEL3(Conn, dobj);           //  지부별 자동이체청구 완료 정보 조회
                dobj  = CALLauto_data_make_test_SEL4(Conn, dobj);           //  지부별 카드이체청구 완료 정보 조회
            }
            else
            {
                dobj  = CALLauto_data_make_test_OSP5(Conn, dobj);           //  카드자동 청구 데이터 생성
                dobj  = CALLauto_data_make_test_MRG1( dobj);        //  결과숫자 조회
                dobj  = CALLauto_data_make_test_SEL2(Conn, dobj);           //  청구 데이터 오류 내역
                dobj  = CALLauto_data_make_test_SEL3(Conn, dobj);           //  지부별 자동이체청구 완료 정보 조회
                dobj  = CALLauto_data_make_test_SEL4(Conn, dobj);           //  지부별 카드이체청구 완료 정보 조회
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
    public DOBJ CTLauto_data_make_test( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("A"))
        {
            dobj  = CALLauto_data_make_test_OSP1(Conn, dobj);           //  자동이체 청구 데이터 생성
            dobj  = CALLauto_data_make_test_MRG1( dobj);        //  결과숫자 조회
            dobj  = CALLauto_data_make_test_SEL2(Conn, dobj);           //  청구 데이터 오류 내역
            dobj  = CALLauto_data_make_test_SEL3(Conn, dobj);           //  지부별 자동이체청구 완료 정보 조회
            dobj  = CALLauto_data_make_test_SEL4(Conn, dobj);           //  지부별 카드이체청구 완료 정보 조회
        }
        else
        {
            dobj  = CALLauto_data_make_test_OSP5(Conn, dobj);           //  카드자동 청구 데이터 생성
            dobj  = CALLauto_data_make_test_MRG1( dobj);        //  결과숫자 조회
            dobj  = CALLauto_data_make_test_SEL2(Conn, dobj);           //  청구 데이터 오류 내역
            dobj  = CALLauto_data_make_test_SEL3(Conn, dobj);           //  지부별 자동이체청구 완료 정보 조회
            dobj  = CALLauto_data_make_test_SEL4(Conn, dobj);           //  지부별 카드이체청구 완료 정보 조회
        }
        return dobj;
    }
    // 자동이체 청구 데이터 생성
    // 청구 데이터를 생성하기 위한 프로시저 (GIBU.SP_PROC_DEMDGIRO) 를 호출한다
    public DOBJ CALLauto_data_make_test_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        String[]  incolumns ={"BRAN_CD","DEMD_YRMN","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
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
        String   spname ="GIBU.SP_PROC_DEMD_AUTO";
        int[]    intypes={12, 12, 12};;
        String[] outcolnms={"P_CNT_READ","P_CNT_INST","P_CNT_OCR","P_CNT_PREPAY","P_CNT_ERR"};;
        int[]    outtypes ={12, 12, 12, 12, 12};;
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
        dobj.setRetObject(robj);
        return dobj;
    }
    // 결과숫자 조회
    public DOBJ CALLauto_data_make_test_MRG1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"OSP1, OSP5","");
        rvobj.setName("MRG1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 청구 데이터 오류 내역
    // 청구 데이터 생성 시 발생한 오류 내역
    public DOBJ CALLauto_data_make_test_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_data_make_test_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_data_make_test_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEMD_YRMN  ,  A.SNUM  ,  C.CODE_NM  DEMD_NM  ,  A.DEMD_GBN  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.ERR_GBN  ,  A.ERR_CTENT  FROM  GIBU.TBRA_DEMD_ERR  A  ,  GIBU.TBRA_UPSO  B  ,  FIDU.TENV_CODE  C  WHERE  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  A.CRET_GBN  =  'A'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  A.DEMD_GBN  =  C.CODE_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 지부별 자동이체청구 완료 정보 조회
    // 지부별로 자동이체 진행정보를 조회한다
    public DOBJ CALLauto_data_make_test_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_data_make_test_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_data_make_test_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dvobj.getRecord().get("DEMD_YRMN");   //청구 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.GIBU  BRAN_CD  ,  XA.DEPT_NM  ,  DECODE(XB.DEMD_YRMN,  NULL,  NULL,  '완료')  BRAN_END  FROM  INSA.TCPM_DEPT  XA  ,  (   \n";
        query +=" SELECT  DISTINCT  B.BRAN_CD  ,  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_AUTO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD  )  XB  WHERE  XA.DEPT_CD  BETWEEN  '106010100'   \n";
        query +=" AND  '106019999'   \n";
        query +=" AND  XA.GIBU  =  XB.BRAN_CD  (+)  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        return sobj;
    }
    // 지부별 카드이체청구 완료 정보 조회
    public DOBJ CALLauto_data_make_test_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_data_make_test_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_data_make_test_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dvobj.getRecord().get("DEMD_YRMN");   //청구 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.GIBU  BRAN_CD  ,  XA.DEPT_NM  ,  DECODE(XB.DEMD_YRMN,  NULL,  NULL,  '완료')  BRAN_END  FROM  INSA.TCPM_DEPT  XA  ,  (   \n";
        query +=" SELECT  DISTINCT  B.BRAN_CD  ,  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_CARD  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD  )  XB  WHERE  XA.DEPT_CD  BETWEEN  '106010100'   \n";
        query +=" AND  '106019999'   \n";
        query +=" AND  XA.GIBU  =  XB.BRAN_CD  (+)  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        return sobj;
    }
    // 카드자동 청구 데이터 생성
    public DOBJ CALLauto_data_make_test_OSP5(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP5");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        String[]  incolumns ={"BRAN_CD","DEMD_YRMN","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
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
        String   spname ="GIBU.SP_PROC_DEMD_CARD";
        int[]    intypes={12, 12, 12};;
        String[] outcolnms={"P_CNT_READ","P_CNT_INST","P_CNT_OCR","P_CNT_PREPAY","P_CNT_ERR"};;
        int[]    outtypes ={12, 12, 12, 12, 12};;
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
        robj.setName("OSP5");
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$auto_data_make_test
    //##**$$auto_card_demd_init
    /*
    */
    public DOBJ CTLauto_card_demd_init(DOBJ dobj)
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
            dobj  = CALLauto_card_demd_init_SEL1(Conn, dobj);           //  지부별 카드자동청구 완료정보 조회
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
    public DOBJ CTLauto_card_demd_init( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_card_demd_init_SEL1(Conn, dobj);           //  지부별 카드자동청구 완료정보 조회
        return dobj;
    }
    // 지부별 카드자동청구 완료정보 조회
    public DOBJ CALLauto_card_demd_init_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_card_demd_init_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_card_demd_init_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dvobj.getRecord().get("DEMD_YRMN");   //청구 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.GIBU  BRAN_CD  ,  XA.DEPT_NM  ,  DECODE(XB.DEMD_YRMN,  NULL,  NULL,  '완료')  BRAN_END  ,  XB.INS_DAY  FROM  INSA.TCPM_DEPT  XA  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  MAX(DEMD_YRMN)  DEMD_YRMN  ,  TO_CHAR(MAX(INS_DATE),  'YYYYMMDD')  INS_DAY  FROM  GIBU.TBRA_DEMD_CARD  WHERE  DEMD_YRMN  =  :DEMD_YRMN  GROUP  BY  BRAN_CD  )  XB  WHERE  XA.DEPT_CD  BETWEEN  '106010100'   \n";
        query +=" AND  '106019999'   \n";
        query +=" AND  XA.GIBU  =  XB.BRAN_CD  (+)  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        return sobj;
    }
    //##**$$auto_card_demd_init
    //##**$$end
}
