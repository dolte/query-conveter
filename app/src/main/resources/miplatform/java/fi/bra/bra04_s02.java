
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s02
{
    public bra04_s02()
    {
    }
    //##**$$rept_closing
    /* * 프로그램명 : bra04_s02
    * 작성자 : 서정재
    * 작성일 : 2009/11/11
    * 설명    : 입금월의 입금마감 내역을 확인한다.
    Input
    BRAN_CD (지부 코드)
    REPT_DAY (입금일자)
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
        String message ="해당기간중 마감내역이 존재합니다. 마감내역을 확인하세요.";
        dobj.setRetmsg(message);
        return dobj;
    }
    private SQLObject SQLrept_closing_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //입금 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(:REPT_DAY,1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(:REPT_DAY,5,2)  =  END_MON ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금 일자
        return sobj;
    }
    //##**$$rept_closing
    //##**$$rept_auto_insert
    /* * 프로그램명 : bra04_s02
    * 작성자 : 박태현
    * 작성일 : 2009/11/26
    * 설명    : 자동이체 입금내역을 저장한다.
    1) 금결원에서 전송되는 자동이체 결과 내역은 '오류가 발생한' 내역이 전송됨
    2) 오류가 발생한 내역에 대해 검색 후 오류 저장 (고객번호와 청구시작년월, 청구종료년월 정보를 이용하여 저장함)
    3) 청구 목록에서 오류가 발생하지 않은 항목을 조회하여 입금테이블 (TBRA_REPT, TBRA_REPT_AUTO) 에 저장
    4) 원장 정보 및 입금청구 매핑 정보 저장
    5) 입금 결과 조회 / 오류 결과 조회 후 리턴
    MODIFY : 2010.07.07  권남균
    - 은행코드를 파일 헤더의 7자리 코드에서 '004'로 입력되도록 강제 변경
    Input
    ACCN_NUM (계좌 번호)
    ACCN_NUM (계좌 번호)
    AUTO_GBN (AUTO_GBN)
    BANK_CD (은행 코드)
    CLIENT_NUM (고객 번호)
    INSPRES_ID (등록자 ID)
    RECPT_CD (접수처 코드)
    RECV_BANK_CD (RECV_BANK_CD)
    RECV_DAY (영수 일자)
    REMAK (비고)
    REPT_AMT (입금 금액)
    REPT_DAY (입금일자)
    REPT_YRMN (입금 년월)
    RESI_NUM (주민 번호)
    SEQ_NUM (일련 번호)
    TRNF_RSLT (이체 결과)
    * 수정일 : 2010.03.02
    * 수정자 :
    * 수정내용 SEL4, SEL5: 정렬추가: 지부, 업소코드순
    */
    public DOBJ CTLrept_auto_insert(DOBJ dobj)
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
            dobj  = CALLrept_auto_insert_SEL1(Conn, dobj);           //  입금마감확인
            if( dobj.getRetObject("SEL1").getRecord().get("BRANEND").equals("0"))
            {
                dobj  = CALLrept_auto_insert_MPD11(Conn, dobj);           //  입금정보 처리
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLrept_auto_insert_SEL17(Conn, dobj);           //  OSP 자료생성
                dobj  = CALLrept_auto_insert_DEL18(Conn, dobj);           //  오류내역 삭제
                dobj  = CALLrept_auto_insert_OSP1(Conn, dobj);           //  자동이체 입금
                dobj  = CALLrept_auto_insert_SEL2(Conn, dobj);           //  자동이체 입금 결과 검색
                dobj  = CALLrept_auto_insert_SEL3(Conn, dobj);           //  자동이체 입금 결과 조회
                dobj  = CALLrept_auto_insert_SEL4(Conn, dobj);           //  자동이체입금오류 내역(금결원)
                dobj  = CALLrept_auto_insert_SEL5(Conn, dobj);           //  자동이체입금오류(원장)
            }
            else
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="해당 년월에 입금마감이 있습니다.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
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
    public DOBJ CTLrept_auto_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_auto_insert_SEL1(Conn, dobj);           //  입금마감확인
        if( dobj.getRetObject("SEL1").getRecord().get("BRANEND").equals("0"))
        {
            dobj  = CALLrept_auto_insert_MPD11(Conn, dobj);           //  입금정보 처리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLrept_auto_insert_SEL17(Conn, dobj);           //  OSP 자료생성
            dobj  = CALLrept_auto_insert_DEL18(Conn, dobj);           //  오류내역 삭제
            dobj  = CALLrept_auto_insert_OSP1(Conn, dobj);           //  자동이체 입금
            dobj  = CALLrept_auto_insert_SEL2(Conn, dobj);           //  자동이체 입금 결과 검색
            dobj  = CALLrept_auto_insert_SEL3(Conn, dobj);           //  자동이체 입금 결과 조회
            dobj  = CALLrept_auto_insert_SEL4(Conn, dobj);           //  자동이체입금오류 내역(금결원)
            dobj  = CALLrept_auto_insert_SEL5(Conn, dobj);           //  자동이체입금오류(원장)
        }
        else
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="해당 년월에 입금마감이 있습니다.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // 입금마감확인
    // 요청된 입금년월의 입금마감이 있는 경우 에러 처리
    public DOBJ CALLrept_auto_insert_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //입금 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  BRANEND  FROM  GIBU.TBRA_BRANEND  WHERE  END_YEAR  =  SUBSTR(:REPT_YRMN,  1,  4)   \n";
        query +=" AND  END_MON  =  SUBSTR(:REPT_YRMN,  5,  2) ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        return sobj;
    }
    // 입금정보 처리
    // 레코드 단위로 입금정보를 처리한다
    public DOBJ CALLrept_auto_insert_MPD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD11");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("MPD11");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("AUTO_GBN").equals("22"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrept_auto_insert_XIUD13(Conn, dobj);           //  이체 결과 저장
            }
        }
        return dobj;
    }
    // 이체 결과 저장
    // 청구 정보에 대한 이체결과를 저장한다
    public DOBJ CALLrept_auto_insert_XIUD13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD13");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_auto_insert_XIUD13(dobj, dvobj);
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
    private SQLObject SQLrept_auto_insert_XIUD13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLIENT_NUM = dobj.getRetObject("R").getRecord().get("CLIENT_NUM");   //고객 번호
        String   DEMD_YRMN = dobj.getRetObject("R").getRecord().get("REPT_YRMN");   //청구 년월
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   TRNF_RSLT = dobj.getRetObject("R").getRecord().get("TRNF_RSLT");   //이체 결과
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_AUTO  \n";
        query +=" SET TRNF_RSLT = :TRNF_RSLT , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE	DEMD_YRMN = :DEMD_YRMN  \n";
        query +=" AND UPSO_CD = ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE CLIENT_NUM = :CLIENT_NUM )";
        sobj.setSql(query);
        sobj.setString("CLIENT_NUM", CLIENT_NUM);               //고객 번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("TRNF_RSLT", TRNF_RSLT);               //이체 결과
        return sobj;
    }
    // OSP 자료생성
    // MODIFY : 2010.07.07  권남균 - 은행코드를 파일 헤더의 7자리 코드에서 '004'로 입력되도록 강제 변경
    public DOBJ CALLrept_auto_insert_SEL17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL17");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL17");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert_SEL17(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL17");
        rvobj.Println("SEL17");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert_SEL17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //입금일자
        String   INSPRES_ID = dobj.getRetObject("S").firstRecord().get("INSPRES_ID");   //등록자 ID
        String   BANK_CD = dobj.getRetObject("S1").getRecord().get("BANK_CD");   //은행 코드
        String   DEMD_YRMN = dobj.getRetObject("S").firstRecord().get("REPT_YRMN");   //청구 년월
        String   RECV_DAY = dobj.getRetObject("S1").getRecord().get("RECV_DAY");   //영수 일자
        String   PROC_GBN = dobj.getRetObject("S").getRecord().get("PROC_GBN");   //자동 처리 구분
        String   ACCN_NUM = dobj.getRetObject("S1").getRecord().get("ACCN_NUM");   //계좌 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_DAY  AS  REPT_DAY  ,  '01'  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  50  AS  COMIS  ,  :RECV_DAY  AS  RECV_DAY  ,  SUBSTR(:BANK_CD,  1,  3)  AS  BANK_CD  ,  (   \n";
        query +=" SELECT  ACCN_NUM  FROM  ACCT.TCAC_ACCOUNT  WHERE  REPLACE(ACCN_NUM,  '-',  '')  LIKE  :ACCN_NUM  )  AS  ACCN_NUM  ,  :INSPRES_ID  AS  INSPRES_ID  ,  :PROC_GBN  AS  PRO_GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("RECV_DAY", RECV_DAY);               //영수 일자
        sobj.setString("PROC_GBN", PROC_GBN);               //자동 처리 구분
        sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        return sobj;
    }
    // 오류내역 삭제
    public DOBJ CALLrept_auto_insert_DEL18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL18");
        VOBJ dvobj = dobj.getRetObject("SEL17");           //OSP 자료생성에서 생성시킨 OBJECT입니다.(CALLrept_auto_insert_SEL17)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_auto_insert_DEL18(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL18") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert_DEL18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //입금 구분
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //입금일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_ERR  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        return sobj;
    }
    // 자동이체 입금
    // 업소별로 자동이체 입금 정보를 저장한다
    public DOBJ CALLrept_auto_insert_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL17");         //OSP 자료생성에서 생성시킨 OBJECT입니다.(CALLrept_auto_insert_SEL17)
        dvobj.Println("OSP1");
        String[]  incolumns ={"REPT_DAY","DEMD_YRMN","COMIS","RECV_DAY","BANK_CD","ACCN_NUM","INSPRES_ID"};
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
        String   spname ="GIBU.SP_PROC_REPT_AUTO";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"READ_CNT","INST_CNT","ERR_CNT"};;
        int[]    outtypes ={12, 12, 12};;
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
    // 자동이체 입금 결과 검색
    // 읽은 건수, 처리 건수, 오류 건수를 조회한다
    public DOBJ CALLrept_auto_insert_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   ERR_CNT = dobj.getRetObject("OSP1").getRecord().getDouble("ERR_CNT");   //에러 건수
        double   INST_CNT = dobj.getRetObject("OSP1").getRecord().getDouble("INST_CNT");   //입력카운트
        double   READ_CNT = dobj.getRetObject("OSP1").getRecord().getDouble("READ_CNT");   //읽은 건수
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :READ_CNT  AS  READ_CNT  ,  :INST_CNT  AS  INST_CNT  ,  :ERR_CNT  AS  ERR_CNT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setDouble("ERR_CNT", ERR_CNT);               //에러 건수
        sobj.setDouble("INST_CNT", INST_CNT);               //입력카운트
        sobj.setDouble("READ_CNT", READ_CNT);               //읽은 건수
        return sobj;
    }
    // 자동이체 입금 결과 조회
    // 자동이체 입금 결과 리스트를 조회한다
    public DOBJ CALLrept_auto_insert_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").firstRecord().get("REPT_YRMN");   //입금 년월
        String   PROC_GBN = dobj.getRetObject("S").firstRecord().get("PROC_GBN");   //자동 처리 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  XA.START_YRMN  ||  '01'  START_YRMN  ,  XA.END_YRMN  ||  '01'  END_YRMN  ,  XC.GRAD  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  XA.DEMD_GBN  ,  '1'  PRINT_YN  ,  XA.TRNF_RSLT  ,  TO_CHAR(XA.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  GIBU.TBRA_UPSO  XB  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  APPL_DAY  <=  :REPT_YRMN  ||  '32'  GROUP  BY  UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.BSTYP_CD  =  C.BSTYP_CD   \n";
        query +=" AND  A.UPSO_GRAD  =  C.GRAD_GBN  )  XC  ,  INSA.TCPM_DEPT  XD  WHERE  XA.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  XA.DEMD_YRMN  IN   \n";
        query +=" (SELECT  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  XA.UPSO_CD)   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NULL   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XB.BRAN_CD   \n";
        query +=" AND  XA.PROC_GBN  =  :PROC_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("PROC_GBN", PROC_GBN);               //자동 처리 구분
        return sobj;
    }
    // 자동이체입금오류 내역(금결원)
    // 자동이체입금오류 내역(금결원)을 조회한다
    public DOBJ CALLrept_auto_insert_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").firstRecord().get("REPT_YRMN");   //입금 년월
        String   PROC_GBN = dobj.getRetObject("S").firstRecord().get("PROC_GBN");   //자동 처리 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.BRAN_CD  ,  XC.DEPT_NM  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.TRNF_RSLT  ,  XD.CODE_NM  ,  TO_CHAR(XA.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_DEPT  XC  ,  FIDU.TENV_CODE  XD  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_DEMD_AUTO  B  WHERE  B.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  B.TRNF_RSLT  IS  NOT  NULL   \n";
        query +=" AND  A.APPL_DAY  <=  :REPT_YRMN  ||  '32'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  C.GRAD_GBN  =  A.UPSO_GRAD   \n";
        query +=" AND  C.BSTYP_CD  =  A.BSTYP_CD  )  XE  WHERE  XA.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NOT  NULL   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GIBU  =  XA.BRAN_CD   \n";
        query +=" AND  XD.HIGH_CD  =  '00206'   \n";
        query +=" AND  XD.CODE_CD  =  XA.TRNF_RSLT   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XA.PROC_GBN  =  :PROC_GBN  ORDER  BY  XB.BRAN_CD,  XB.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("PROC_GBN", PROC_GBN);               //자동 처리 구분
        return sobj;
    }
    // 자동이체입금오류(원장)
    // 자동이체입금오류(원장)을 조회한다
    public DOBJ CALLrept_auto_insert_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_insert_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_insert_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //입금일자
        String   REPT_YRMN = dobj.getRetObject("S").firstRecord().get("REPT_YRMN");   //입금 년월
        String   REPT_GBN ="01";   //입금 구분
        String   PROC_GBN = dobj.getRetObject("S").firstRecord().get("PROC_GBN");   //자동 처리 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XC.UPSO_NM  ,  XB.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XC.MCHNDAESU  ,  XB.MONPRNCFEE  ,  XA.REPT_AMT  ,  XB.TOT_ADDT_AMT  ,  XB.TOT_EADDT_AMT  ,  XB.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.ERR_GBN  ,  XA.ERR_CTENT  ,  TO_CHAR(XB.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_REPT_ERR  XA  ,  GIBU.TBRA_DEMD_AUTO  XB  ,  GIBU.TBRA_UPSO  XC  ,  INSA.TCPM_DEPT  XD  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_REPT_ERR  B  WHERE  B.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  B.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  A.APPL_DAY  <=  :REPT_YRMN  ||  '32'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.UPSO_GRAD  =  C.GRAD_GBN   \n";
        query +=" AND  C.BSTYP_CD  =  A.BSTYP_CD  )  XE  WHERE  XA.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.START_YRMN  =  XA.START_YRMN   \n";
        query +=" AND  XB.END_YRMN  =  XA.END_YRMN   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XC.BRAN_CD   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.PROC_GBN  =  :PROC_GBN  ORDER  BY  XC.BRAN_CD,  XC.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("PROC_GBN", PROC_GBN);               //자동 처리 구분
        return sobj;
    }
    //##**$$rept_auto_insert
    //##**$$rept_auto_select
    /*
    */
    public DOBJ CTLrept_auto_select(DOBJ dobj)
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
            dobj  = CALLrept_auto_select_SEL3(Conn, dobj);           //  항목 초기화
            dobj  = CALLrept_auto_select_SEL1(Conn, dobj);           //  자동이체 입금 결과 조회
            dobj  = CALLrept_auto_select_SEL4(Conn, dobj);           //  자동이체 입금 오류 내역(금결원)
            dobj  = CALLrept_auto_select_SEL5(Conn, dobj);           //  자동이체 입금 오류 내역(원장재건)
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
    public DOBJ CTLrept_auto_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_auto_select_SEL3(Conn, dobj);           //  항목 초기화
        dobj  = CALLrept_auto_select_SEL1(Conn, dobj);           //  자동이체 입금 결과 조회
        dobj  = CALLrept_auto_select_SEL4(Conn, dobj);           //  자동이체 입금 오류 내역(금결원)
        dobj  = CALLrept_auto_select_SEL5(Conn, dobj);           //  자동이체 입금 오류 내역(원장재건)
        return dobj;
    }
    // 항목 초기화
    public DOBJ CALLrept_auto_select_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_select_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_select_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //입금일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUBSTR(:REPT_DAY,  1,  6)  REPT_YRMN  ,  :REPT_DAY  AS  REPT_DAY  ,  '01'  AS  REPT_GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        return sobj;
    }
    // 자동이체 입금 결과 조회
    // 자동이체 입금 결과 리스트를 조회한다
    public DOBJ CALLrept_auto_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("SEL3").getRecord().get("REPT_YRMN");   //입금 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  XA.START_YRMN  ||  '01'  START_YRMN  ,  XA.END_YRMN  ||  '01'  END_YRMN  ,  XA.BSTYP_CD  ||  XA.UPSO_GRAD  GRAD  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  XA.DEMD_GBN  ,  '1'  PRINT_YN  ,  XA.TRNF_RSLT  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_DEPT  XD  WHERE  XA.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  XA.DEMD_YRMN  IN   \n";
        query +=" (SELECT  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  XA.UPSO_CD)   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NULL   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XB.BRAN_CD   \n";
        query +=" AND  XA.DEMD_MMCNT  >  0 ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        return sobj;
    }
    // 자동이체 입금 오류 내역(금결원)
    // 자동이체 입금 오류 내역을 조회한다.  오류 내역은 금결원에서 발생한 오류 내역과 내부 처리 중 발생한 오류 내역 모두를 조회한다
    public DOBJ CALLrept_auto_select_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_select_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.setRetcode(1);
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_select_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("SEL3").getRecord().get("REPT_YRMN");   //입금 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.BRAN_CD  ,  XC.DEPT_NM  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.TRNF_RSLT  ,  XD.CODE_NM  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_DEPT  XC  ,  FIDU.TENV_CODE  XD  ,  GIBU.TBRA_BSTYPGRAD  XE  WHERE  XA.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NOT  NULL   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GIBU  =  XA.BRAN_CD   \n";
        query +=" AND  XD.HIGH_CD  =  '00206'   \n";
        query +=" AND  XD.CODE_CD  =  XA.TRNF_RSLT   \n";
        query +=" AND  XE.BSTYP_CD  =  XA.BSTYP_CD   \n";
        query +=" AND  XE.GRAD_GBN  =  XA.UPSO_GRAD  ORDER  BY  XB.BRAN_CD,  XB.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        return sobj;
    }
    // 자동이체 입금 오류 내역(원장재건)
    // 자동이체 입금 오류 내역(원장재건)
    public DOBJ CALLrept_auto_select_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_select_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_select_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("SEL3").getRecord().get("REPT_YRMN");   //입금 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XC.UPSO_NM  ,  XB.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XC.MCHNDAESU  ,  XB.MONPRNCFEE  ,  XA.REPT_AMT  ,  XB.TOT_ADDT_AMT  ,  XB.TOT_EADDT_AMT  ,  XB.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.ERR_GBN  ,  XA.ERR_CTENT  FROM  GIBU.TBRA_REPT_ERR  XA  ,  GIBU.TBRA_DEMD_AUTO  XB  ,  GIBU.TBRA_UPSO  XC  ,  INSA.TCPM_DEPT  XD  ,  GIBU.TBRA_BSTYPGRAD  XE  WHERE  XA.REPT_DAY  BETWEEN  :REPT_YRMN  ||  '01'   \n";
        query +=" AND  :REPT_YRMN  ||  '31'   \n";
        query +=" AND  XA.REPT_GBN  =  '01'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.START_YRMN  =  XA.START_YRMN   \n";
        query +=" AND  XB.END_YRMN  =  XA.END_YRMN   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XC.BRAN_CD   \n";
        query +=" AND  XE.BSTYP_CD  =  XB.BSTYP_CD   \n";
        query +=" AND  XE.GRAD_GBN  =  XB.UPSO_GRAD  ORDER  BY  XC.BRAN_CD,  XC.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        return sobj;
    }
    //##**$$rept_auto_select
    //##**$$rept_auto_report
    /*
    */
    public DOBJ CTLrept_auto_report(DOBJ dobj)
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
            dobj  = CALLrept_auto_report_SEL1(Conn, dobj);           //  자동이체입금오류 내역(금결원)
            dobj  = CALLrept_auto_report_SEL2(Conn, dobj);           //  자동이체입금오류(원장)
            dobj  = CALLrept_auto_report_SEL3(Conn, dobj);           //  자동이체 입금 결과 조회
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
    public DOBJ CTLrept_auto_report( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_auto_report_SEL1(Conn, dobj);           //  자동이체입금오류 내역(금결원)
        dobj  = CALLrept_auto_report_SEL2(Conn, dobj);           //  자동이체입금오류(원장)
        dobj  = CALLrept_auto_report_SEL3(Conn, dobj);           //  자동이체 입금 결과 조회
        return dobj;
    }
    // 자동이체입금오류 내역(금결원)
    // 자동이체입금오류 내역(금결원)을 조회한다
    public DOBJ CALLrept_auto_report_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_report_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_report_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //입금 년월
        String   PROC_GBN = dobj.getRetObject("S").getRecord().get("PROC_GBN");   //자동 처리 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.BRAN_CD  ,  XC.DEPT_NM  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.TRNF_RSLT  ,  XD.CODE_NM  ,  TO_CHAR(XA.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_DEPT  XC  ,  FIDU.TENV_CODE  XD  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_DEMD_AUTO  B  WHERE  B.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  B.TRNF_RSLT  IS  NOT  NULL   \n";
        query +=" AND  A.APPL_DAY  <=  :REPT_YRMN  ||  '32'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  C.GRAD_GBN  =  A.UPSO_GRAD   \n";
        query +=" AND  C.BSTYP_CD  =  A.BSTYP_CD  )  XE  WHERE  XA.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NOT  NULL   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GIBU  =  XA.BRAN_CD   \n";
        query +=" AND  XD.HIGH_CD  =  '00206'   \n";
        query +=" AND  XD.CODE_CD  =  XA.TRNF_RSLT   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XA.PROC_GBN  =  :PROC_GBN  ORDER  BY  XB.BRAN_CD,  XB.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("PROC_GBN", PROC_GBN);               //자동 처리 구분
        return sobj;
    }
    // 자동이체입금오류(원장)
    // 자동이체입금오류(원장)을 조회한다
    public DOBJ CALLrept_auto_report_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_report_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_report_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //입금 년월
        String   REPT_GBN ="01";   //입금 구분
        String   PROC_GBN = dobj.getRetObject("S").getRecord().get("PROC_GBN");   //자동 처리 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XC.UPSO_NM  ,  XB.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XC.MCHNDAESU  ,  XB.MONPRNCFEE  ,  XA.REPT_AMT  ,  XB.TOT_ADDT_AMT  ,  XB.TOT_EADDT_AMT  ,  XB.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.ERR_GBN  ,  XA.ERR_CTENT  ,  TO_CHAR(XB.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_REPT_ERR  XA  ,  GIBU.TBRA_DEMD_AUTO  XB  ,  GIBU.TBRA_UPSO  XC  ,  INSA.TCPM_DEPT  XD  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_REPT_ERR  B  WHERE  B.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  B.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  A.APPL_DAY  <=  :REPT_YRMN  ||  '32'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.UPSO_GRAD  =  C.GRAD_GBN   \n";
        query +=" AND  C.BSTYP_CD  =  A.BSTYP_CD  )  XE  WHERE  XA.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.START_YRMN  =  XA.START_YRMN   \n";
        query +=" AND  XB.END_YRMN  =  XA.END_YRMN   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XC.BRAN_CD   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.PROC_GBN  =  :PROC_GBN  ORDER  BY  XC.BRAN_CD,  XC.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("PROC_GBN", PROC_GBN);               //자동 처리 구분
        return sobj;
    }
    // 자동이체 입금 결과 조회
    // 자동이체 입금 결과 리스트를 조회한다
    public DOBJ CALLrept_auto_report_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_auto_report_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_auto_report_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //입금 년월
        String   PROC_GBN = dobj.getRetObject("S").getRecord().get("PROC_GBN");   //자동 처리 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  XA.START_YRMN  ||  '01'  START_YRMN  ,  XA.END_YRMN  ||  '01'  END_YRMN  ,  XC.GRAD  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  XA.DEMD_GBN  ,  '1'  PRINT_YN  ,  XA.TRNF_RSLT  ,  TO_CHAR(XA.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_DEMD_AUTO  XA  ,  GIBU.TBRA_UPSO  XB  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  APPL_DAY  <=  :REPT_YRMN  ||  '32'  GROUP  BY  UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.BSTYP_CD  =  C.BSTYP_CD   \n";
        query +=" AND  A.UPSO_GRAD  =  C.GRAD_GBN  )  XC  ,  INSA.TCPM_DEPT  XD  WHERE  XA.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  XA.DEMD_YRMN  IN   \n";
        query +=" (SELECT  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  XA.UPSO_CD)   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NULL   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XB.BRAN_CD   \n";
        query +=" AND  XA.PROC_GBN  =  :PROC_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("PROC_GBN", PROC_GBN);               //자동 처리 구분
        return sobj;
    }
    //##**$$rept_auto_report
    //##**$$end
}
