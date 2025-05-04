
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_r02
{
    public bra01_r02()
    {
    }
    //##**$$satn_num_del
    /*
    */
    public DOBJ CTLsatn_num_del(DOBJ dobj)
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
            dobj  = CALLsatn_num_del_XIUD1(Conn, dobj);           //  휴/폐업 결재번호 일괄 삭제
            dobj  = CALLsatn_num_del_XIUD2(Conn, dobj);           //  업변 결재번호 일괄 삭제
            dobj  = CALLsatn_num_del_SEL6(Conn, dobj);           //  결재번호넘기기
            dobj  = CALLsatn_num_del_OBJ5(Conn, dobj);           //  그룹웨어 연동데이터 삭제
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
    public DOBJ CTLsatn_num_del( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsatn_num_del_XIUD1(Conn, dobj);           //  휴/폐업 결재번호 일괄 삭제
        dobj  = CALLsatn_num_del_XIUD2(Conn, dobj);           //  업변 결재번호 일괄 삭제
        dobj  = CALLsatn_num_del_SEL6(Conn, dobj);           //  결재번호넘기기
        dobj  = CALLsatn_num_del_OBJ5(Conn, dobj);           //  그룹웨어 연동데이터 삭제
        return dobj;
    }
    // 휴/폐업 결재번호 일괄 삭제
    public DOBJ CALLsatn_num_del_XIUD1(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsatn_num_del_XIUD1(dobj, dvobj);
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
    private SQLObject SQLsatn_num_del_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   LNK_KEY = dobj.getRetObject("S").getRecord().get("LNK_KEY");   //연동 키
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_CONFIRM_DOC  \n";
        query +=" SET SATN_NUM = ''  \n";
        query +=" WHERE SATN_NUM = :LNK_KEY";
        sobj.setSql(query);
        sobj.setString("LNK_KEY", LNK_KEY);               //연동 키
        return sobj;
    }
    // 업변 결재번호 일괄 삭제
    public DOBJ CALLsatn_num_del_XIUD2(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsatn_num_del_XIUD2(dobj, dvobj);
            qexe.DispSelectSql(sobj);
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
    private SQLObject SQLsatn_num_del_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   LNK_KEY = dobj.getRetObject("S").getRecord().get("LNK_KEY");   //연동 키
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_CLSED  \n";
        query +=" SET SATN_NUM = ''  \n";
        query +=" WHERE SATN_NUM = :LNK_KEY";
        sobj.setSql(query);
        sobj.setString("LNK_KEY", LNK_KEY);               //연동 키
        return sobj;
    }
    // 결재번호넘기기
    public DOBJ CALLsatn_num_del_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsatn_num_del_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsatn_num_del_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   LNK_KEY = dobj.getRetObject("S").getRecord().get("LNK_KEY");   //연동 키
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :LNK_KEY  AS  LNK_KEY  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("LNK_KEY", LNK_KEY);               //연동 키
        return sobj;
    }
    // 그룹웨어 연동데이터 삭제
    public DOBJ CALLsatn_num_del_OBJ5(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OBJ5");
        CommUtil cu = new CommUtil(dobj);
        HashMap   classinfo = new HashMap();
        classinfo.put("OBJECTID","OBJ5");
        classinfo.put("PACKAGE","komca.ga.adm");
        classinfo.put("CLASS","adm01_r01");
        classinfo.put("METHOD","satn_num_del");
        classinfo.put("INMAP","S:SEL6");
        classinfo.put("OUTOBJ","");
        classinfo.put("OUTOBJ1","");
        dobj = cu.callPSInternal(dobj, null, classinfo );
        dobj.getRetObject("OBJ5").Println("OBJ5");
        return dobj;
    }
    //##**$$satn_num_del
    //##**$$apprv_stat
    /*
    */
    public DOBJ CTLapprv_stat(DOBJ dobj)
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
            dobj  = CALLapprv_stat_MPD3(Conn, dobj);           //  분기
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
    public DOBJ CTLapprv_stat( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLapprv_stat_MPD3(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLapprv_stat_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLapprv_stat_SEL2(Conn, dobj);           //  일괄결재상태조회
                if( dobj.getRetObject("SEL2").getRecordCnt() > 0)
                {
                    dobj  = CALLapprv_stat_ADD5( dobj);        //  결재상태목록추가
                }
            }
        }
        return dobj;
    }
    // 일괄결재상태조회
    public DOBJ CALLapprv_stat_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLapprv_stat_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLapprv_stat_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GBN = dobj.getRetObject("R").getRecord().get("GBN");   //구분
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //관리번호
        String   CLSED_GBN = dobj.getRetObject("R").getRecord().get("CLSED_GBN");   //휴업 구분
        String   SATN_NUM = dobj.getRetObject("R").getRecord().get("SATN_NUM");   //결재번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LNK_KEY  ,  DOC_STS  AS  STATUS_CD  ,  :UPSO_CD  AS  UPSO_CD  ,  :SEQ  AS  SEQ  ,  :CLSED_GBN  AS  CLSED_GBN  ,  :GBN  AS  GBN  FROM  TRGW.TEAG_APPDOC  WHERE  LNK_KEY  =  :SATN_NUM   \n";
        query +=" AND  DOC_STS  IN  ('20',  '90') ";
        sobj.setSql(query);
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("CLSED_GBN", CLSED_GBN);               //휴업 구분
        sobj.setString("SATN_NUM", SATN_NUM);               //결재번호
        return sobj;
    }
    // 결재상태목록추가
    public DOBJ CALLapprv_stat_ADD5(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("ADD5");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        VOBJ dvobj = dobj.getRetObject("SEL2");          //사용자 화면에서 발생한 Object입니다.
        rvobj = wutil.getAddedVobj(dobj,"ADD5","", dvobj );
        rvobj.setName("ADD5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    //##**$$apprv_stat
    //##**$$sel_satn_list
    /*
    */
    public DOBJ CTLsel_satn_list(DOBJ dobj)
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
            dobj  = CALLsel_satn_list_SEL1(Conn, dobj);           //  결재리스트조회
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
    public DOBJ CTLsel_satn_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_satn_list_SEL1(Conn, dobj);           //  결재리스트조회
        return dobj;
    }
    // 결재리스트조회
    public DOBJ CALLsel_satn_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_satn_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_satn_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   STAFF_NUM = dobj.getRetObject("S").getRecord().get("STAFF_NUM");   //사원번호
        String   CLSED_GBN = dobj.getRetObject("S").getRecord().get("CLSED_GBN");   //휴업 구분
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '0'  AS  CHK  ,  A.UPSO_CD  ,  A.SEQ  ,  B.UPSO_NM  ,  TO_CHAR(A.INS_DATE,  'YYYY-MM-DD')  AS  INS_DATE  ,  A.GBN  AS  CLSED_GBN  ,  DECODE(A.GBN,  '2',  TO_CHAR(TO_DATE(A.START_YRMN,  'YYYYMM'),  'YYYY/MM'),  '')  AS  CLSBS_YRMN  ,  ''  AS  CLSED_DAY  ,  DECODE(A.GBN,  '1',  NVL(TO_CHAR(TO_DATE(A.START_DAY,  'YYYYMMDD'),  'YYYY/MM/DD'),  TO_CHAR(TO_DATE(A.START_YRMN,  'YYYYMM'),  'YYYY/MM')),  '')  AS  START_DAY  ,  DECODE(A.GBN,  '1',  NVL(TO_CHAR(TO_DATE(A.END_DAY,  'YYYYMMDD'),  'YYYY/MM/DD'),  TO_CHAR(TO_DATE(A.END_YRMN,  'YYYYMM'),  'YYYY/MM')),  '')  AS  END_DAY  ,  A.SATN_NUM  ,  ''  AS  SATN_STAT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_CONFIRM_DOC_ATTCH  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  SEQ  =  A.SEQ   \n";
        query +=" AND  LENGTH(FILES)  >  0)  AS  ATTCH_CHK  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_CONFIRM_DOC_ATTCH  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  SEQ  =  A.SEQ)  AS  ATTCH_CNT  ,  B.STAFF_CD  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  AS  STAFF_NM  ,  'D'  AS  GBN  FROM  GIBU.TBRA_CONFIRM_DOC  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  B.STAFF_CD  =  NVL(:STAFF_NUM,  B.STAFF_CD)   \n";
        query +=" AND  A.GBN  =  NVL(:CLSED_GBN,  A.GBN)   \n";
        query +=" AND  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  UNION  ALL   \n";
        query +=" SELECT  '0'  AS  CHK  ,  TA.UPSO_CD  ,  1  AS  SEQ  ,  TA.UPSO_NM  ,  TO_CHAR(TA.INS_DATE,  'YYYY-MM-DD')  AS  INS_DATE  ,  '3'  AS  CLSED_GBN  ,  TO_CHAR(TO_DATE  (TA.START_YRMN,  'YYYYMM'),  'YYYY/MM')  AS  CLSBS_YRMN  ,  TA.START_YRMN  AS  CLSED_DAY  ,  ''  AS  START_YRMN  ,  ''  AS  END_YRMN  ,  TA.SATN_NUM  ,  ''  AS  SATN_STAT  ,  TO_NUMBER('')  AS  ATTCH_CHK  ,  TO_NUMBER('')  AS  ATTCH_CNT  ,  TA.STAFF_CD  ,  FIDU.GET_STAFF_NM(TA.STAFF_CD)  AS  STAFF_NM  ,  'C'  AS  GBN  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.UPSO_NM  ,  A.CLSBS_YRMN  ,  A.STAFF_CD  ,  B.START_YRMN  ,  B.INS_DATE  ,  B.SATN_NUM  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_UPSO_CLSED  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.CLSED_GBN  IN  ('02',  '03',  '04')   \n";
        query +=" AND  TO_CHAR(B.INS_DATE,  'YYYYMMDD')  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_NUM,  A.STAFF_CD)   \n";
        query +=" AND  (:CLSED_GBN  =  '3'   \n";
        query +=" OR  :CLSED_GBN  IS  NULL)  )  TA  ,  GIBU.TBRA_UPSO  XC  WHERE  XC.BEFORE_UPSO_CD  =  TA.UPSO_CD  ORDER  BY  STAFF_CD,  UPSO_CD,  SEQ ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("STAFF_NUM", STAFF_NUM);               //사원번호
        sobj.setString("CLSED_GBN", CLSED_GBN);               //휴업 구분
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$sel_satn_list
    //##**$$apprv_all_satn
    /*
    */
    public DOBJ CTLapprv_all_satn(DOBJ dobj)
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
            dobj  = CALLapprv_all_satn_SEL1(Conn, dobj);           //  일괄결재번호획득
            dobj  = CALLapprv_all_satn_MPD3(Conn, dobj);           //  분기
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
    public DOBJ CTLapprv_all_satn( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLapprv_all_satn_SEL1(Conn, dobj);           //  일괄결재번호획득
        dobj  = CALLapprv_all_satn_MPD3(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 일괄결재번호획득
    public DOBJ CALLapprv_all_satn_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLapprv_all_satn_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLapprv_all_satn_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(SATN_NUM)  AS  SATN_NUM  FROM  (   \n";
        query +=" SELECT  TO_CHAR(SYSDATE,  'YYYYMMDD')  ||  '-'  ||  :STAFF_CD  ||  '-'  ||  LPAD(NVL(MAX(SUBSTR(SATN_NUM,  18,  3)),  0)  +  1,  3,  '0')  AS  SATN_NUM  FROM  GIBU.TBRA_CONFIRM_DOC  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.SATN_NUM  LIKE  TO_CHAR(SYSDATE,  'YYYYMMDD')  ||  '-'  ||  :STAFF_CD  ||  '%'  UNION  ALL   \n";
        query +=" SELECT  TO_CHAR(SYSDATE,  'YYYYMMDD')  ||  '-'  ||  :STAFF_CD  ||  '-'  ||  LPAD(NVL(MAX(SUBSTR(SATN_NUM,  18,  3)),  0)  +  1,  3,  '0')  AS  SATN_NUM  FROM  GIBU.TBRA_UPSO_CLSED  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.SATN_NUM  LIKE  TO_CHAR(SYSDATE,  'YYYYMMDD')  ||  '-'  ||  :STAFF_CD  ||  '%'  ) ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    // 분기
    public DOBJ CALLapprv_all_satn_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("GBN").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLapprv_all_satn_XIUD1(Conn, dobj);           //  일괄결재번호생성
            }
            else
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLapprv_all_satn_XIUD6(Conn, dobj);           //  일괄결재번호생성
            }
        }
        return dobj;
    }
    // 일괄결재번호생성
    public DOBJ CALLapprv_all_satn_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLapprv_all_satn_XIUD1(dobj, dvobj);
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
    private SQLObject SQLapprv_all_satn_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SATN_NUM = dobj.getRetObject("SEL1").getRecord().get("SATN_NUM");   //결재번호
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //관리번호
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_CONFIRM_DOC  \n";
        query +=" SET SATN_NUM = :SATN_NUM  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND SEQ = :SEQ";
        sobj.setSql(query);
        sobj.setString("SATN_NUM", SATN_NUM);               //결재번호
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 일괄결재번호생성
    public DOBJ CALLapprv_all_satn_XIUD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD6");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLapprv_all_satn_XIUD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLapprv_all_satn_XIUD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLSED_DAY = dobj.getRetObject("R").getRecord().get("CLSED_DAY");   //휴업 일자
        String   SATN_NUM = dobj.getRetObject("SEL1").getRecord().get("SATN_NUM");   //결재번호
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_CLSED  \n";
        query +=" SET SATN_NUM = :SATN_NUM  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND START_YRMN = :CLSED_DAY";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY", CLSED_DAY);               //휴업 일자
        sobj.setString("SATN_NUM", SATN_NUM);               //결재번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$apprv_all_satn
    //##**$$end
}
