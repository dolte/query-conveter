
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_r13
{
    public bra01_r13()
    {
    }
    //##**$$btrip_list
    /*
    */
    public DOBJ CTLbtrip_list(DOBJ dobj)
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
            dobj  = CALLbtrip_list_SEL3(Conn, dobj);           //  지부코드 획득
            dobj  = CALLbtrip_list_SEL1(Conn, dobj);           //  목록조회
            dobj  = CALLbtrip_list_SEL4(Conn, dobj);           //  매칭안된 전표목록
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
    public DOBJ CTLbtrip_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbtrip_list_SEL3(Conn, dobj);           //  지부코드 획득
        dobj  = CALLbtrip_list_SEL1(Conn, dobj);           //  목록조회
        dobj  = CALLbtrip_list_SEL4(Conn, dobj);           //  매칭안된 전표목록
        return dobj;
    }
    // 지부코드 획득
    public DOBJ CALLbtrip_list_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbtrip_list_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_list_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEPT_CD  ,  A.DEPT_NM,  A.GIBU  FROM  INSA.TCPM_DEPT  A  WHERE  1=1   \n";
        query +=" AND  A.GIBU  <>  'AL'   \n";
        query +=" AND  A.GIBU  =  :BRAN_CD   \n";
        query +=" AND  A.USE_YN  =  'Y' ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 목록조회
    public DOBJ CALLbtrip_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbtrip_list_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  TO_CHAR(INS_DATE,  'YYYYMMDD')  AS  INS_DATE  ,  BTRIP_DAY  ,  STAFF_CD  ,  INSA.F_GET_STAFF_NM(STAFF_CD)  AS  STAFF_NM  ,  GBN  ,  FIDU.GET_CODE_NM('00415',  GBN)  AS  GBN_NM  ,  TOTAL_FEE  ,  '  '||BTRIP_STAFF_NM  AS  BTRIP_STAFF_NM  ,  BTRIP_PROVCITY_NM  ,  SUBSTR(LPAD(BTRIP_USE_TIME,6,'0'),1,2)  ||  ':'  ||  SUBSTR(LPAD(BTRIP_USE_TIME,6,'0'),3,2)  ||  ':'  ||  SUBSTR(LPAD(BTRIP_USE_TIME,6,'0'),5,2)  AS  BTRIP_USE_TIME  ,  BTRIP_USE_KILO  ,  CHIT_CD  ,  CHIT_SEQ  ,  decode((   \n";
        query +=" SELECT  B.CHIT_CD  ||  B.CHIT_SEQ  FROM  ACCT.TBIL_SLIP  A,  ACCT.TBIL_SLIP_D  B,  ACCT.TBIL_SLIP_REM  C  WHERE  A.CHIT_CD  =  B.CHIT_CD   \n";
        query +=" AND  B.CHIT_CD  =  C.CHIT_CD   \n";
        query +=" AND  B.CHIT_SEQ  =  C.CHIT_SEQ   \n";
        query +=" AND  A.USE_YN  =  'Y'   \n";
        query +=" AND  B.USE_YN  =  'Y'   \n";
        query +=" AND  B.CHIT_CD  =  X.CHIT_CD   \n";
        query +=" AND  B.CHIT_SEQ  =  X.CHIT_SEQ   \n";
        query +=" AND  LENGTH(C.MNG_ITEM_CD)  =  7  )  ,  null,  '전표없음',  '',  '전표없음',  ''  )  AS  CHIT_GBN  FROM  GIBU.TBRA_BTRIP  X  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  BTRIP_DAY  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  GBN  =  DECODE(:GBN,  '',  GBN,  :GBN)   \n";
        query +=" AND  STAFF_CD  =  DECODE(:STAFF_CD,  '',  STAFF_CD,  :STAFF_CD) ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 매칭안된 전표목록
    public DOBJ CALLbtrip_list_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbtrip_list_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_list_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEPT_CD = dobj.getRetObject("SEL3").getRecord().get("DEPT_CD");   //부서 코드
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.CHIT_CD,  B.CHIT_SEQ,  '  '||B.SYNOP  SYNOP,  B.DR_AMT,  C.MNG_ITEM_CD  FROM  ACCT.TBIL_SLIP  A,  ACCT.TBIL_SLIP_D  B,  ACCT.TBIL_SLIP_REM  C  WHERE  A.CHIT_CD  =  B.CHIT_CD   \n";
        query +=" AND  B.CHIT_CD  =  C.CHIT_CD   \n";
        query +=" AND  B.CHIT_SEQ  =  C.CHIT_SEQ   \n";
        query +=" AND  A.USE_YN  =  'Y'   \n";
        query +=" AND  B.USE_YN  =  'Y'   \n";
        query +=" AND  (B.ACCT_CD  =  '0460'   \n";
        query +=" OR  B.ACCT_CD  =  '0464')   \n";
        query +=" AND  A.DEPT_CD  =  :DEPT_CD   \n";
        query +=" AND  A.CHIT_DAY  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  LENGTH(C.MNG_ITEM_CD)  =  7   \n";
        query +=" AND  C.MNG_ITEM_CD  =  DECODE(:STAFF_CD,  '',  C.MNG_ITEM_CD,  :STAFF_CD)  minus   \n";
        query +=" SELECT  Y.CHIT_CD  ,  Y.CHIT_SEQ  ,  Y.SYNOP  ,  Y.DR_AMT  ,  Y.MNG_ITEM_CD  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  STAFF_CD  ,  CHIT_CD  ,  CHIT_SEQ  FROM  GIBU.TBRA_BTRIP  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  BTRIP_DAY  BETWEEN  SUBSTR(:START_YRMN,  1,  4)  ||  '01'   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  STAFF_CD  =  DECODE(:STAFF_CD,  '',  STAFF_CD,  :STAFF_CD)  )X,   \n";
        query +=" (SELECT  B.CHIT_CD,  B.CHIT_SEQ,  A.CHIT_DAY,  '  '||B.SYNOP  SYNOP,  C.MNG_ITEM_CD,  B.DR_AMT  FROM  ACCT.TBIL_SLIP  A,  ACCT.TBIL_SLIP_D  B,  ACCT.TBIL_SLIP_REM  C  WHERE  A.CHIT_CD  =  B.CHIT_CD   \n";
        query +=" AND  B.CHIT_CD  =  C.CHIT_CD   \n";
        query +=" AND  B.CHIT_SEQ  =  C.CHIT_SEQ   \n";
        query +=" AND  A.USE_YN  =  'Y'   \n";
        query +=" AND  B.USE_YN  =  'Y'   \n";
        query +=" AND  (B.ACCT_CD  =  '0460'   \n";
        query +=" OR  B.ACCT_CD  =  '0464')   \n";
        query +=" AND  A.DEPT_CD  =  :DEPT_CD   \n";
        query +=" AND  A.CHIT_DAY  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  LENGTH(C.MNG_ITEM_CD)  =  7   \n";
        query +=" AND  C.MNG_ITEM_CD  =  DECODE(:STAFF_CD,  '',  C.MNG_ITEM_CD,  :STAFF_CD))  Y  WHERE  X.CHIT_CD  =  Y.CHIT_CD   \n";
        query +=" AND  X.CHIT_SEQ  =  Y.CHIT_SEQ   \n";
        query +=" AND  X.STAFF_CD  =  Y.MNG_ITEM_CD ";
        sobj.setSql(query);
        sobj.setString("DEPT_CD", DEPT_CD);               //부서 코드
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    //##**$$btrip_list
    //##**$$btrip_detail
    /*
    */
    public DOBJ CTLbtrip_detail(DOBJ dobj)
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
            dobj  = CALLbtrip_detail_SEL1(Conn, dobj);           //  상세조회
            if(!dobj.getRetObject("SEL1").getRecord().get("FILE_TEMPNM").equals(""))
            {
                dobj  = CALLbtrip_detail_SEL2(Conn, dobj);           //  첨부파일 조회
            }
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
    public DOBJ CTLbtrip_detail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbtrip_detail_SEL1(Conn, dobj);           //  상세조회
        if(!dobj.getRetObject("SEL1").getRecord().get("FILE_TEMPNM").equals(""))
        {
            dobj  = CALLbtrip_detail_SEL2(Conn, dobj);           //  첨부파일 조회
        }
        return dobj;
    }
    // 상세조회
    public DOBJ CALLbtrip_detail_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbtrip_detail_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_detail_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        String   BTRIP_DAY = dobj.getRetObject("S").getRecord().get("BTRIP_DAY");   //출장 일자
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BRAN_CD  ,  A.BTRIP_DAY  ,  A.STAFF_CD  ,  A.GBN  ,  A.TOTAL_FEE  ,  A.BTRIP_PROVCITY_NM  ,  A.BTRIP_STAFF_NM  ,  LPAD(A.BTRIP_START_TIME,6,'0')  BTRIP_START_TIME  ,  LPAD(A.BTRIP_USE_TIME,6,'0')  BTRIP_USE_TIME  ,  A.BTRIP_USE_KILO  ,  A.CHIT_CD  ,  A.CHIT_SEQ  ,  A.BIGO  ,  A.INSPRES_ID  ,  TO_CHAR(A.INS_DATE,'YYYYMMDD')  AS  INS_DATE  ,  B.FILE_TEMPNM  ,  B.FILE_ROUT  FROM  GIBU.TBRA_BTRIP  A  ,  GIBU.TBRA_BTRIP_DOC_ATTCH  B  WHERE  A.BRAN_CD  =  B.BRAN_CD(+)   \n";
        query +=" AND  A.BTRIP_DAY  =  B.BTRIP_DAY(+)   \n";
        query +=" AND  A.  STAFF_CD  =  B.STAFF_CD(+)   \n";
        query +=" AND  A.  GBN  =  B.GBN(+)   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.BTRIP_DAY  =  :BTRIP_DAY   \n";
        query +=" AND  A.STAFF_CD  =:STAFF_CD   \n";
        query +=" AND  A.GBN  =  :GBN ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //출장 일자
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 첨부파일 조회
    public DOBJ CALLbtrip_detail_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbtrip_detail_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        String fullname="";
        rvobj.first();
        while(rvobj.next())
        {
            wutil.makeFileOverwirte( dobj.getRetObject("SEL1").getRecord().get("FILE_ROUT"), dobj.getRetObject("SEL1").getRecord().get("FILE_TEMPNM"),rvobj.getRecord().getBytes("FILES"));
        }
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_detail_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        String   BTRIP_DAY = dobj.getRetObject("S").getRecord().get("BTRIP_DAY");   //출장 일자
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BRAN_CD  ,  A.BTRIP_DAY  ,  A.STAFF_CD  ,  A.GBN  ,  A.FILE_NM  ,  A.FILE_ROUT  ,  A.FILE_TYPE  ,  A.FILE_TEMPNM  ,  A.FILE_SIZE  ,  A.FILES  FROM  GIBU.TBRA_BTRIP_DOC_ATTCH  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.BTRIP_DAY  =  :BTRIP_DAY   \n";
        query +=" AND  A.STAFF_CD  =:STAFF_CD   \n";
        query +=" AND  A.GBN  =  :GBN ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //출장 일자
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$btrip_detail
    //##**$$btrip_file_save
    /*
    */
    public DOBJ CTLbtrip_file_save(DOBJ dobj)
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
            dobj  = CALLbtrip_file_save_DEL21(Conn, dobj);           //  이전 첨부파일 삭제
            dobj  = CALLbtrip_file_save_FUT11( dobj);        //  파일무브
            dobj  = CALLbtrip_file_save_CVT12( dobj);        //  파일무브이동정보
            dobj  = CALLbtrip_file_save_INS19(Conn, dobj);           //  파일업로드정보저장
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
    public DOBJ CTLbtrip_file_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbtrip_file_save_DEL21(Conn, dobj);           //  이전 첨부파일 삭제
        dobj  = CALLbtrip_file_save_FUT11( dobj);        //  파일무브
        dobj  = CALLbtrip_file_save_CVT12( dobj);        //  파일무브이동정보
        dobj  = CALLbtrip_file_save_INS19(Conn, dobj);           //  파일업로드정보저장
        return dobj;
    }
    // 이전 첨부파일 삭제
    public DOBJ CALLbtrip_file_save_DEL21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL21");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("DEL21");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbtrip_file_save_DEL21(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL21") ;
        rvobj.Println("DEL21");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_file_save_DEL21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   BTRIP_DAY = dvobj.getRecord().get("BTRIP_DAY");   //출장 일자
        String   GBN = dvobj.getRecord().get("GBN");   //구분
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BTRIP_DOC_ATTCH  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and GBN=:GBN  \n";
        query +=" and BTRIP_DAY=:BTRIP_DAY  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //출장 일자
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    // 파일무브
    public DOBJ CALLbtrip_file_save_FUT11(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT11");
        VOBJ dvobj = dobj.getRetObject("S1");      //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("FUT11");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("UNIFILENAME");         //목적파일명
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //목적파일경로
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("UNIFILENAME");         //소스파일명
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("PATH");         //소스파일경로
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT11") ;
        dvobj.setRetcode(1);
        dvobj.Println("FUT11");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // 파일무브이동정보
    public DOBJ CALLbtrip_file_save_CVT12(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT12");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("S1");        //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("CVT12");
        String[] outcolumns ={"UNIFILENAME","FILESIZE","PATH","UPFILENAME","DFILEPATH"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.cpRecord();
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //목적파일경로
            record.put("DFILEPATH",DFILEPATH);
            for(int i=0;i<outcolumns.length;i++)
            {
                if(!record.containsKey(outcolumns[i]))
                {
                    record.remove(outcolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        dvobj.setName("CVT12") ;
        dvobj.setRetcode(1);
        dvobj.Println("CVT12");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // 파일업로드정보저장
    public DOBJ CALLbtrip_file_save_INS19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS19");
        VOBJ dvobj = dobj.getRetObject("S1");           //사용자 화면에서 발생한 Object입니다.
        String fullname="";
        dvobj.first();
        while(dvobj.next())
        {
            byte[] binFILES=wutil.inputFileStream("/home/jeus/komca_web/upload", dobj.getRetObject("S1").getRecord().get("UNIFILENAME"));
            dvobj.getRecord().put("FILES", binFILES);
        }
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbtrip_file_save_INS19(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS19") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_file_save_INS19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FILE_TYPE = dvobj.getRecord().get("FILE_TYPE");   //파일 타입
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //관리번호
        String   BTRIP_DAY = dobj.getRetObject("S").getRecord().get("BTRIP_DAY");   //업소 코드
        byte[]   FILES = dvobj.getRecord().getBytes("FILES");   //파일
        String   FILE_NM = dobj.getRetObject("S1").getRecord().get("UPFILENAME");   //파일명
        String   FILE_ROUT = dobj.getRetObject("CVT12").getRecord().get("DFILEPATH");   //파일경로
        double   FILE_SIZE = dobj.getRetObject("S1").getRecord().getDouble("FILESIZE");   //파일 크기
        String   FILE_TEMPNM = dobj.getRetObject("S1").getRecord().get("UNIFILENAME");   //변환 파일명
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BTRIP_DOC_ATTCH (FILE_SIZE, STAFF_CD, GBN, FILE_TEMPNM, FILE_ROUT, FILE_TYPE, BTRIP_DAY, BRAN_CD, FILE_NM, FILES)  \n";
        query +=" values(:FILE_SIZE , :STAFF_CD , :GBN , :FILE_TEMPNM , :FILE_ROUT , :FILE_TYPE , :BTRIP_DAY , :BRAN_CD , :FILE_NM , :FILES )";
        sobj.setSql(query);
        sobj.setString("FILE_TYPE", FILE_TYPE);               //파일 타입
        sobj.setString("BRAN_CD", BRAN_CD);               //관리번호
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //업소 코드
        sobj.setBlob("FILES", FILES);               //파일
        sobj.setString("FILE_NM", FILE_NM);               //파일명
        sobj.setString("FILE_ROUT", FILE_ROUT);               //파일경로
        sobj.setDouble("FILE_SIZE", FILE_SIZE);               //파일 크기
        sobj.setString("FILE_TEMPNM", FILE_TEMPNM);               //변환 파일명
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    //##**$$btrip_file_save
    //##**$$btrip_save
    /*
    */
    public DOBJ CTLbtrip_save(DOBJ dobj)
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
            dobj  = CALLbtrip_save_MIUD1(Conn, dobj);           //  신청서관리
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
    public DOBJ CTLbtrip_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbtrip_save_MIUD1(Conn, dobj);           //  신청서관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 신청서관리
    public DOBJ CALLbtrip_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLbtrip_save_INS5(Conn, dobj);           //  신규등록
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbtrip_save_UPD6(Conn, dobj);           //  수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbtrip_save_DEL11(Conn, dobj);           //  삭제
                dobj  = CALLbtrip_save_DEL12(Conn, dobj);           //  첨부파일삭제
            }
        }
        return dobj;
    }
    // 삭제
    public DOBJ CALLbtrip_save_DEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL11");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbtrip_save_DEL11(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL11") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_save_DEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   BTRIP_DAY = dvobj.getRecord().get("BTRIP_DAY");   //출장 일자
        String   GBN = dvobj.getRecord().get("GBN");   //구분
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BTRIP  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and GBN=:GBN  \n";
        query +=" and BTRIP_DAY=:BTRIP_DAY  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //출장 일자
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    // 수정
    public DOBJ CALLbtrip_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("UPD6");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbtrip_save_UPD6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        rvobj.Println("UPD6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //등록 일시
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   CHIT_CD = dvobj.getRecord().get("CHIT_CD");   //전표 코드(전표일자(8) + 순번(4))
        String   BTRIP_USE_KILO = dvobj.getRecord().get("BTRIP_USE_KILO");   //총 출장거리
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   BTRIP_USE_TIME = dvobj.getRecord().get("BTRIP_USE_TIME");   //총 소요시간
        String   BTRIP_PROVCITY_NM = dvobj.getRecord().get("BTRIP_PROVCITY_NM");   //출장 도시 명
        String   BTRIP_DAY = dvobj.getRecord().get("BTRIP_DAY");   //출장 일자
        String   TOTAL_FEE = dvobj.getRecord().get("TOTAL_FEE");   //전체 부담금
        String   BTRIP_STAFF_NM = dvobj.getRecord().get("BTRIP_STAFF_NM");   //출장자
        String   BIGO = dvobj.getRecord().get("BIGO");   //비고사항
        String   BTRIP_START_TIME = dvobj.getRecord().get("BTRIP_START_TIME");   //출장 시작시간
        String   GBN = dvobj.getRecord().get("GBN");   //구분
        String   CHIT_SEQ = dvobj.getRecord().get("CHIT_SEQ");   //전표 순번
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BTRIP  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , CHIT_SEQ=:CHIT_SEQ , BTRIP_START_TIME=:BTRIP_START_TIME , BIGO=:BIGO , BTRIP_STAFF_NM=:BTRIP_STAFF_NM , TOTAL_FEE=:TOTAL_FEE , BTRIP_PROVCITY_NM=:BTRIP_PROVCITY_NM , BTRIP_USE_TIME=:BTRIP_USE_TIME , BTRIP_USE_KILO=:BTRIP_USE_KILO , CHIT_CD=:CHIT_CD , MOD_DATE=SYSDATE  \n";
        query +=" where GBN=:GBN  \n";
        query +=" and BTRIP_DAY=:BTRIP_DAY  \n";
        query +=" and STAFF_CD=:STAFF_CD  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("CHIT_CD", CHIT_CD);               //전표 코드(전표일자(8) + 순번(4))
        sobj.setString("BTRIP_USE_KILO", BTRIP_USE_KILO);               //총 출장거리
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BTRIP_USE_TIME", BTRIP_USE_TIME);               //총 소요시간
        sobj.setString("BTRIP_PROVCITY_NM", BTRIP_PROVCITY_NM);               //출장 도시 명
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //출장 일자
        sobj.setString("TOTAL_FEE", TOTAL_FEE);               //전체 부담금
        sobj.setString("BTRIP_STAFF_NM", BTRIP_STAFF_NM);               //출장자
        sobj.setString("BIGO", BIGO);               //비고사항
        sobj.setString("BTRIP_START_TIME", BTRIP_START_TIME);               //출장 시작시간
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("CHIT_SEQ", CHIT_SEQ);               //전표 순번
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 신규등록
    public DOBJ CALLbtrip_save_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbtrip_save_INS5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   CHIT_CD = dvobj.getRecord().get("CHIT_CD");   //전표 코드(전표일자(8) + 순번(4))
        String   BTRIP_USE_KILO = dvobj.getRecord().get("BTRIP_USE_KILO");   //총 출장거리
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   BTRIP_USE_TIME = dvobj.getRecord().get("BTRIP_USE_TIME");   //총 소요시간
        String   BTRIP_PROVCITY_NM = dvobj.getRecord().get("BTRIP_PROVCITY_NM");   //출장 도시 명
        String   BTRIP_DAY = dvobj.getRecord().get("BTRIP_DAY");   //출장 일자
        String   TOTAL_FEE = dvobj.getRecord().get("TOTAL_FEE");   //전체 부담금
        String   BTRIP_STAFF_NM = dvobj.getRecord().get("BTRIP_STAFF_NM");   //출장자
        String   BIGO = dvobj.getRecord().get("BIGO");   //비고사항
        String   BTRIP_START_TIME = dvobj.getRecord().get("BTRIP_START_TIME");   //출장 시작시간
        String   GBN = dvobj.getRecord().get("GBN");   //구분
        String   CHIT_SEQ = dvobj.getRecord().get("CHIT_SEQ");   //전표 순번
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BTRIP (CHIT_SEQ, INSPRES_ID, GBN, BTRIP_START_TIME, BIGO, BTRIP_STAFF_NM, TOTAL_FEE, BTRIP_DAY, BTRIP_PROVCITY_NM, INS_DATE, BTRIP_USE_TIME, STAFF_CD, BTRIP_USE_KILO, CHIT_CD, BRAN_CD)  \n";
        query +=" values(:CHIT_SEQ , :INSPRES_ID , :GBN , :BTRIP_START_TIME , :BIGO , :BTRIP_STAFF_NM , :TOTAL_FEE , :BTRIP_DAY , :BTRIP_PROVCITY_NM , SYSDATE, :BTRIP_USE_TIME , :STAFF_CD , :BTRIP_USE_KILO , :CHIT_CD , :BRAN_CD )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("CHIT_CD", CHIT_CD);               //전표 코드(전표일자(8) + 순번(4))
        sobj.setString("BTRIP_USE_KILO", BTRIP_USE_KILO);               //총 출장거리
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BTRIP_USE_TIME", BTRIP_USE_TIME);               //총 소요시간
        sobj.setString("BTRIP_PROVCITY_NM", BTRIP_PROVCITY_NM);               //출장 도시 명
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //출장 일자
        sobj.setString("TOTAL_FEE", TOTAL_FEE);               //전체 부담금
        sobj.setString("BTRIP_STAFF_NM", BTRIP_STAFF_NM);               //출장자
        sobj.setString("BIGO", BIGO);               //비고사항
        sobj.setString("BTRIP_START_TIME", BTRIP_START_TIME);               //출장 시작시간
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("CHIT_SEQ", CHIT_SEQ);               //전표 순번
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 첨부파일삭제
    public DOBJ CALLbtrip_save_DEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbtrip_save_DEL12(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_save_DEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   BTRIP_DAY = dvobj.getRecord().get("BTRIP_DAY");   //출장 일자
        String   GBN = dvobj.getRecord().get("GBN");   //구분
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BTRIP_DOC_ATTCH  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and GBN=:GBN  \n";
        query +=" and BTRIP_DAY=:BTRIP_DAY  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("BTRIP_DAY", BTRIP_DAY);               //출장 일자
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    //##**$$btrip_save
    //##**$$end
}
