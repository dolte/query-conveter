
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s17
{
    public bra10_s17()
    {
    }
    //##**$$sel_gps_record
    /*
    */
    public DOBJ CTLsel_gps_record(DOBJ dobj)
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
            dobj  = CALLsel_gps_record_SEL1(Conn, dobj);           //  방문현황조회
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
    public DOBJ CTLsel_gps_record( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_gps_record_SEL1(Conn, dobj);           //  방문현황조회
        return dobj;
    }
    // 방문현황조회
    public DOBJ CALLsel_gps_record_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_gps_record_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_gps_record_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사용자번호
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  STAFF_CD  ,  STAFF_NM  ,  BRAN_CD  ,  BRAN_NM  ,  VISIT_DAY  ,  UPSO_CD  ,  UPSO_NM  ,  VISIT_YN  ,  LAT  ,  LNG  ,  ADDR  ,  INSPRES_ID  ,  INSPRES_NM  ,  INS_DATE  ,  LEAVE_TIME  ,  (CASE  WHEN  RK  =  0  THEN  '퇴근'  WHEN  RK  =  MAX(RK)  OVER  (PARTITION  BY  STAFF_CD,  VISIT_DAY)  THEN  'Y'  ELSE  ''  END)  AS  LAST_VISIT  ,  JOB_GBN  FROM  (   \n";
        query +=" SELECT  A.STAFF_CD  ,  FIDU.GET_STAFF_NM(A.STAFF_CD)  AS  STAFF_NM  ,  A.BRAN_CD  ,  GIBU.GET_BRAN_NM(A.BRAN_CD)  AS  BRAN_NM  ,  A.VISIT_DAY  ,  A.UPSO_CD  ,  (CASE  WHEN  LENGTH(A.UPSO_CD)  =  8  THEN   \n";
        query +=" (SELECT  UPSO_NM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD)  ELSE   \n";
        query +=" (SELECT  UPSO_NM  FROM  GIBU.TMOB_NEW_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD)  END)  AS  UPSO_NM  ,  DECODE(A.VISIT_YN,  '',  'N',  'Y')  AS  VISIT_YN  ,  A.LAT  ,  A.LNG  ,  A.ADDR  ,  A.INSPRES_ID  ,  DECODE(FIDU.GET_STAFF_NM(A.INSPRES_ID),  '',  A.INSPRES_ID,  FIDU.GET_STAFF_NM(A.INSPRES_ID))  AS  INSPRES_NM  ,  NVL(A.MOD_DATE,  A.INS_DATE)  AS  INS_DATE  ,  ''  AS  LEAVE_TIME  ,  RANK()  OVER  (PARTITION  BY  A.STAFF_CD,  A.VISIT_DAY  ORDER  BY  NVL(A.MOD_DATE,  A.INS_DATE)  ASC)  AS  RK  ,  DECODE(B.JOB_GBN,  'V',  '방문',  '폐문')  AS  JOB_GBN  FROM  GIBU.TMOB_VISIT_PLAN  A  ,  GIBU.TBRA_UPSO_VISIT  B  WHERE  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  A.BRAN_CD  =  NVL(:BRAN_CD,  A.BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  A.LAT  IS  NOT  NULL   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  =  B.VISIT_DAY   \n";
        query +=" AND  A.JOB_GBN  =  'V'  UNION  ALL   \n";
        query +=" SELECT  A.INSPRES_ID  AS  STAFF_CD  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  AS  STAFF_NM  ,   \n";
        query +=" (SELECT  BRAN_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD)  AS  BRAN_CD  ,  GIBU.GET_BRAN_NM((SELECT  BRAN_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD))  AS  BRAN_NM  ,  A.VISIT_DAY  ,  A.UPSO_CD  ,   \n";
        query +=" (SELECT  UPSO_NM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD)  AS  UPSO_NM  ,  '코스모스등록'  AS  VISIT_YN  ,  ''  AS  LAT  ,  ''  AS  LNG  ,  ''  AS  ADDR  ,  A.INSPRES_ID  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  AS  INSPRES_NM  ,  A.INS_DATE  ,  ''  AS  LEAVE_TIME  ,  TO_NUMBER(NULL)  AS  RK  ,  DECODE(A.JOB_GBN,  'V',  '방문',  '폐문')  AS  JOB_GBN  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  B.BRAN_CD  =  NVL(:BRAN_CD,  B.BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  B.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (B.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))   \n";
        query +=" AND  A.INSPRES_ID  =  NVL(:STAFF_CD,  A.INSPRES_ID)   \n";
        query +=" AND  LENGTH(A.INSPRES_ID)  =  7   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TMOB_VISIT_PLAN  WHERE  STAFF_CD  =  A.INSPRES_ID   \n";
        query +=" AND  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  A.VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  'V'   \n";
        query +=" AND  (VISIT_YN  =  'Y'   \n";
        query +=" OR  VISIT_YN  IS  NULL))  <  1   \n";
        query +=" AND  A.JOB_GBN  IN  ('V',  'T')  UNION  ALL   \n";
        query +=" SELECT  A.STAFF_CD  ,  FIDU.GET_STAFF_NM(A.STAFF_CD)  AS  STAFF_NM  ,  A.BRAN_CD  ,  GIBU.GET_BRAN_NM(A.BRAN_CD)  AS  BRAN_NM  ,  A.VISIT_DAY  ,  ''  AS  UPSO_CD  ,  ''  AS  UPSO_NM  ,  '퇴근'  AS  VISIT_YN  ,  A.R_LAT  AS  LAT  ,  A.R_LNG  AS  LNG  ,  A.ADDR  ,  A.STAFF_CD  AS  INSPRES_ID  ,  FIDU.GET_STAFF_NM(A.STAFF_CD)  AS  INSPRES_NM  ,  A.INS_DATE  ,  TO_CHAR(A.INS_DATE,  'HH24MI')  AS  LEAVE_TIME  ,  0  AS  RK  ,  ''  AS  JOB_GBN  FROM  GIBU.TMOB_LEAVE_WORK  A  WHERE  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  A.BRAN_CD  =  NVL(:BRAN_CD,  A.BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)  )  ORDER  BY  VISIT_DAY  ASC,  BRAN_CD  ASC,  STAFF_CD  ASC,  INS_DATE  DESC ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$sel_gps_record
    //##**$$mng_gps_record
    /*
    */
    public DOBJ CTLmng_gps_record(DOBJ dobj)
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
            dobj  = CALLmng_gps_record_MIUD1(Conn, dobj);           //  분기
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
    public DOBJ CTLmng_gps_record( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_gps_record_MIUD1(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLmng_gps_record_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_gps_record_SEL7(Conn, dobj);           //  분기
                if( dobj.getRetObject("R").getRecord().get("VISIT_YN").equals("Y") || dobj.getRetObject("R").getRecord().get("VISIT_YN").equals("N"))
                {
                    dobj  = CALLmng_gps_record_UPD5(Conn, dobj);           //  변환주소저장
                }
                else if( dobj.getRetObject("R").getRecord().get("VISIT_YN").equals("퇴근"))
                {
                    dobj  = CALLmng_gps_record_UPD6(Conn, dobj);           //  변환주소저장
                }
            }
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLmng_gps_record_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmng_gps_record_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_gps_record_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '1'  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 변환주소저장
    public DOBJ CALLmng_gps_record_UPD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_gps_record_UPD5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_gps_record_UPD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   ADDR = dvobj.getRecord().get("ADDR");   //거주주소
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TMOB_VISIT_PLAN  \n";
        query +=" set ADDR=:ADDR  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("ADDR", ADDR);               //거주주소
        return sobj;
    }
    // 변환주소저장
    public DOBJ CALLmng_gps_record_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_gps_record_UPD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_gps_record_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   ADDR = dvobj.getRecord().get("ADDR");   //거주주소
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update  \n";
        query +=" GIBU.TMOB_LEAVE_WORK  \n";
        query +=" set ADDR=:ADDR  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("ADDR", ADDR);               //거주주소
        return sobj;
    }
    //##**$$mng_gps_record
    //##**$$sel_duty_list
    /*
    */
    public DOBJ CTLsel_duty_list(DOBJ dobj)
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
            dobj  = CALLsel_duty_list_SEL1(Conn, dobj);           //  지부근태현황
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
    public DOBJ CTLsel_duty_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_duty_list_SEL1(Conn, dobj);           //  지부근태현황
        return dobj;
    }
    // 지부근태현황
    public DOBJ CALLsel_duty_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_duty_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_duty_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사용자번호
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  E.YRMNDAY  ,  DECODE(E.DAY  ,'1','일요일','2','월요일','3','화요일','4','수요일','5','목요일','6','금요일','7','토요일')  AS  DAY  ,  A.GIBU  AS  BRAN_CD  ,  A.DEPT_NM  ,  A.STAFF_NUM  ,  A.HAN_NM  ,  (CASE  WHEN  D.PETI_GBN  IS  NULL  THEN  '정상출/퇴근'  ELSE   \n";
        query +=" (SELECT  DILNLAZ_NM  FROM  INSA.TCPM_PETITION  WHERE  DILNLAZ_CD  =  D.PETI_GBN)  END)  AS  DILNLAZ_PLAN  ,  TO_CHAR(B.INS_DATE,  'HH24:MI:SS')  AS  AOFFICE_TM  ,  B.LAT  AS  A_LAT  ,  B.LNG  AS  A_LNG  ,  B.ADDR  AS  A_ADDR  ,  TO_CHAR(F.INS_DATE,  'HH24:MI:SS')  AS  SVISIT_TM  ,  F.LAT  AS  S_LAT  ,  F.LNG  AS  S_LNG  ,  F.ADDR  AS  S_ADDR  ,  TO_CHAR(C.INS_DATE,  'HH24:MI:SS')  AS  LOFFICE_TM  ,  C.LAT  AS  L_LAT  ,  C.LNG  AS  L_LNG  ,  C.ADDR  AS  L_ADDR  FROM  (   \n";
        query +=" SELECT  STAFF_NUM  ,  HAN_NM  ,  GIBU  ,  DEPT_NM  FROM  INSA.TINS_MST01  TA  ,  INSA.TCPM_DEPT  TB  WHERE  TA.DUTY_GBN  =  '001'   \n";
        query +=" AND  TA.DEPT_CD  =  TB.DEPT_CD   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  TA.DEPT_CD  =  NVL((SELECT  DEPT_CD  FROM  INSA.TCPM_DEPT  WHERE  GIBU  =  :BRAN_CD),  TA.DEPT_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  TA.DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (TA.DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))  )  A  ,  GIBU.TMOB_START_WORK  B  ,  GIBU.TMOB_START_VISIT  F  ,  GIBU.TMOB_LEAVE_WORK  C  ,  INSA.TDUT_PETITION  D  ,  INSA.TDUT_CALENDAR  E  WHERE  A.STAFF_NUM  =  B.STAFF_CD(+)   \n";
        query +=" AND  A.STAFF_NUM  =  C.STAFF_CD(+)   \n";
        query +=" AND  A.STAFF_NUM  =  D.STAFF_NUM(+)   \n";
        query +=" AND  A.STAFF_NUM  =  F.STAFF_CD(+)   \n";
        query +=" AND  A.STAFF_NUM  =  NVL(:STAFF_CD,  A.STAFF_NUM)   \n";
        query +=" AND  E.YRMNDAY  =  B.WORK_DAY(+)   \n";
        query +=" AND  E.YRMNDAY  =  C.VISIT_DAY(+)   \n";
        query +=" AND  E.YRMNDAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  E.YRMNDAY  =  B.WORK_DAY(+)   \n";
        query +=" AND  E.YRMNDAY  =  C.VISIT_DAY(+)   \n";
        query +=" AND  E.YRMNDAY  BETWEEN  D.START_DAY(+)   \n";
        query +=" AND  D.END_DAY(+)   \n";
        query +=" AND  E.YRMNDAY  =  F.WORK_DAY(+)   \n";
        query +=" AND  D.APPRV_YN(+)  <>  '3'  ORDER  BY  A.GIBU,  E.YRMNDAY,  A.HAN_NM ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$sel_duty_list
    //##**$$sel_work_stat
    /*
    */
    public DOBJ CTLsel_work_stat(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("JOB_GBN").equals("V"))
            {
                dobj  = CALLsel_work_stat_SEL1(Conn, dobj);           //  출장 업무상세 조회
                dobj  = CALLsel_work_stat_MRG5( dobj);        //  조회합계
                dobj  = CALLsel_work_stat_SEL8(Conn, dobj);           //  업무통계 조회
            }
            else
            {
                dobj  = CALLsel_work_stat_SEL6(Conn, dobj);           //  전화 업무상세 조회
                dobj  = CALLsel_work_stat_MRG5( dobj);        //  조회합계
                dobj  = CALLsel_work_stat_SEL8(Conn, dobj);           //  업무통계 조회
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
    public DOBJ CTLsel_work_stat( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("JOB_GBN").equals("V"))
        {
            dobj  = CALLsel_work_stat_SEL1(Conn, dobj);           //  출장 업무상세 조회
            dobj  = CALLsel_work_stat_MRG5( dobj);        //  조회합계
            dobj  = CALLsel_work_stat_SEL8(Conn, dobj);           //  업무통계 조회
        }
        else
        {
            dobj  = CALLsel_work_stat_SEL6(Conn, dobj);           //  전화 업무상세 조회
            dobj  = CALLsel_work_stat_MRG5( dobj);        //  조회합계
            dobj  = CALLsel_work_stat_SEL8(Conn, dobj);           //  업무통계 조회
        }
        return dobj;
    }
    // 출장 업무상세 조회
    public DOBJ CALLsel_work_stat_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_work_stat_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_work_stat_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   JOB_GBN = dobj.getRetObject("S").getRecord().get("JOB_GBN");   //업무 구분
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사용자번호
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  STAFF_CD  ,  FIDU.GET_STAFF_NM(STAFF_CD)  AS  STAFF_NM  ,  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  SEQ  ,  VISIT_DAY  ,  UPSO_CD  ,  UPSO_NM  ,  ENFRC_TM  ,  SPLMT_TM  ,  NEW_TM  ,  NOVISIT_YN  FROM  (   \n";
        query +=" SELECT  STAFF_CD  ,  BRAN_CD  ,  ROW_NUMBER()  OVER(PARTITION  BY  STAFF_CD,  BRAN_CD  ORDER  BY  SEQ_TM)  AS  SEQ  ,  TO_CHAR(TO_DATE(VISIT_DAY,  'YYYYMMDD'),  'YYYY-MM-DD')  AS  VISIT_DAY  ,  UPSO_CD  ,  UPSO_NM  ,  ENFRC_TM  ,  SPLMT_TM  ,  NEW_TM  ,  NOVISIT_YN  FROM  (   \n";
        query +=" SELECT  A.STAFF_CD  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.VISIT_DAY  ,  DECODE(A.VISIT_YN,  'Y',  TO_CHAR(A.MOD_DATE,  'HH24:MI'),  '')  AS  ENFRC_TM  ,  (CASE  WHEN  A.VISIT_YN  IS  NULL  THEN  TO_CHAR(A.INS_DATE,  'HH24:MI')  ELSE  ''  END)  AS  SPLMT_TM  ,  ''  AS  NEW_TM  ,  (CASE  WHEN  A.VISIT_YN  =  'N'  THEN  '1'  ELSE  '0'  END)  AS  NOVISIT_YN  ,  (CASE  WHEN  A.VISIT_YN  =  'Y'  THEN  TO_CHAR(A.MOD_DATE,  'HH24:MI')  WHEN  A.VISIT_YN  IS  NULL  THEN  TO_CHAR(A.INS_DATE,  'HH24:MI')  ELSE  ''  END)  AS  SEQ_TM  FROM  GIBU.TMOB_VISIT_PLAN  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  A.BRAN_CD  =  NVL(:BRAN_CD,  A.BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  A.JOB_GBN  =  NVL(:JOB_GBN,  A.JOB_GBN)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  A.STAFF_CD  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  A.UPSO_NM  ,  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  AS  VISIT_DAY  ,  ''  AS  ENFRC_TM  ,  ''  AS  SPLMT_TM  ,  TO_CHAR(A.INS_DATE,  'HH24:MI')  AS  NEW_TM  ,  '0'  AS  NOVISIT_YN  ,  TO_CHAR(A.INS_DATE,  'HH24:MI')  AS  SEQ_TM  FROM  GIBU.TMOB_NEW_UPSO  A  WHERE  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  A.BRAN_CD  =  NVL(:BRAN_CD,  A.BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)  )  UNION  ALL   \n";
        query +=" SELECT  STAFF_CD  ,  BRAN_CD  ,  SEQ  ,  '전체  :  '  ||  TOTAL_TM  AS  VISIT_DAY  ,  '총실시  :  '  ||  (ENFRC_TM  +  SPLMT_TM)  AS  UPSO_CD  ,  '예정  :  '  ||  PLAN_TM  AS  UPSO_NM  ,  '실시  :  '  ||  ENFRC_TM  AS  ENFRC_TM  ,  '추가  :  '  ||  SPLMT_TM  AS  SPLMT_TM  ,  '신규  :  '  ||  NEW_TM  AS  NEW_TM  ,  ''  AS  NOVISIT_YN  FROM  (   \n";
        query +=" SELECT  STAFF_CD  ,  BRAN_CD  ,  999  AS  SEQ  ,  SUM(TOTAL_CNT)  AS  TOTAL_TM  ,  SUM(PLAN_CNT)  AS  PLAN_TM  ,  SUM(ENFRC_CNT)  AS  ENFRC_TM  ,  SUM(SPLMT_CNT)  AS  SPLMT_TM  ,  SUM(NEW_CNT)  AS  NEW_TM  FROM  (   \n";
        query +=" SELECT  A.STAFF_CD  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.VISIT_DAY  ,  1  AS  TOTAL_CNT  ,  (CASE  WHEN  MAX(A.VISIT_YN)  KEEP  (DENSE_RANK  FIRST  ORDER  BY  A.INS_DATE  DESC)  IS  NOT  NULL  THEN  1  ELSE  0  END)  AS  PLAN_CNT  ,  DECODE(MAX(A.VISIT_YN)  KEEP  (DENSE_RANK  FIRST  ORDER  BY  A.INS_DATE  DESC),  'Y',  1,  0)  AS  ENFRC_CNT  ,  (CASE  WHEN  MAX(A.VISIT_YN)  KEEP  (DENSE_RANK  FIRST  ORDER  BY  A.INS_DATE  DESC)  IS  NULL  THEN  1  ELSE  0  END)  AS  SPLMT_CNT  ,  0  AS  NEW_CNT  FROM  GIBU.TMOB_VISIT_PLAN  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  A.BRAN_CD  =  NVL(:BRAN_CD,  A.BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  A.JOB_GBN  =  NVL(:JOB_GBN,  A.JOB_GBN)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.STAFF_CD,  A.BRAN_CD,  A.UPSO_CD,  B.UPSO_NM,  A.VISIT_DAY  UNION  ALL   \n";
        query +=" SELECT  A.STAFF_CD  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  A.UPSO_NM  ,  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  AS  VISIT_DAY  ,  0  AS  TOTAL_CNT  ,  0  AS  PLAN_CNT  ,  0  AS  ENFRC_CNT  ,  0  AS  SPLMT_CNT  ,  1  AS  NEW_CNT  FROM  GIBU.TMOB_NEW_UPSO  A  WHERE  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  A.BRAN_CD  =  NVL(:BRAN_CD,  A.BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)  )  GROUP  BY  STAFF_CD,  BRAN_CD  )  )  ORDER  BY  BRAN_CD,  STAFF_CD,  SEQ ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 조회합계
    public DOBJ CALLsel_work_stat_MRG5(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG5");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL6","");
        rvobj.setName("MRG5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 업무통계 조회
    public DOBJ CALLsel_work_stat_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_work_stat_SEL8(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_work_stat_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사용자번호
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TB.BRAN_CD  AS  BRAN_CD  ,  GIBU.GET_BRAN_NM(TB.BRAN_CD)  AS  BRAN_NM  ,  TB.STAFF_CD  ,  FIDU.GET_STAFF_NM(TB.STAFF_CD)  AS  STAFF_NM  ,  SUM(TA.PLAN_V)  AS  PLAN_V  ,  SUM(TA.ENFRC_V)  AS  ENFRC_V  ,  SUM(TA.SPLMT_V)  AS  SPLMT_V  ,  SUM(TA.NEW_V)  AS  NEW_V  ,  SUM(TA.CNT_V)  AS  CNT_V  ,  SUM(TA.CNT_T)  AS  CNT_T  ,  SUM(TA.CNT_V)  +  SUM(TA.CNT_T)  AS  CNT_TOT_V  ,  SUM(TA.PLAN_P)  AS  PLAN_P  ,  SUM(TA.ENFRC_P)  AS  ENFRC_P  ,  SUM(TA.SPLMT_P)  AS  SPLMT_P  ,  SUM(TA.NEW_P)  AS  NEW_P  ,  SUM(TA.CNT_P)  AS  CNT_P  ,  SUM(TA.CNT_E)  AS  CNT_E  ,  SUM(TA.CNT_P)  +  SUM(TA.CNT_E)  AS  CNT_TOT_P  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  A.STAFF_CD  ,  (CASE  WHEN  MAX(A.VISIT_YN)  KEEP  (DENSE_RANK  FIRST  ORDER  BY  A.INS_DATE  DESC)  IS  NOT  NULL  THEN  1  ELSE  0  END)  AS  PLAN_V  ,  (CASE  WHEN  MAX(A.VISIT_YN)  KEEP  (DENSE_RANK  FIRST  ORDER  BY  A.INS_DATE  DESC)  =  'Y'  THEN  1  ELSE  0  END)  AS  ENFRC_V  ,  (CASE  WHEN  MAX(A.VISIT_YN)  KEEP  (DENSE_RANK  FIRST  ORDER  BY  A.INS_DATE  DESC)  IS  NULL  THEN  1  ELSE  0  END)  AS  SPLMT_V  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TMOB_NEW_UPSO  WHERE  STAFF_CD  =  A.STAFF_CD   \n";
        query +=" AND  TO_CHAR(INS_DATE,  'YYYYMM')  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY)  AS  NEW_V  ,  0  AS  CNT_V  ,  0  AS  CNT_T  ,  0  AS  PLAN_P  ,  0  AS  ENFRC_P  ,  0  AS  SPLMT_P  ,  0  AS  NEW_P  ,  0  AS  CNT_P  ,  0  AS  CNT_E  FROM  GIBU.TMOB_VISIT_PLAN  A  WHERE  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  A.BRAN_CD  =  NVL(:BRAN_CD,  A.BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))   \n";
        query +=" AND  A.JOB_GBN  =  'V'  GROUP  BY  A.BRAN_CD,  A.STAFF_CD,  A.VISIT_DAY,  A.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  B.BRAN_CD  ,  A.INSPRES_ID  AS  STAFF_CD  ,  0  AS  PLAN_V  ,  0  AS  ENFRC_V  ,  0  AS  SPLMT_V  ,  0  AS  NEW_V  ,  (CASE  WHEN  MAX(A.JOB_GBN)  KEEP  (DENSE_RANK  FIRST  ORDER  BY  A.INS_DATE  DESC)  =  'V'  THEN  1  ELSE  0  END)  AS  CNT_V  ,  (CASE  WHEN  MAX(A.JOB_GBN)  KEEP  (DENSE_RANK  FIRST  ORDER  BY  A.INS_DATE  DESC)  =  'T'  THEN  1  ELSE  0  END)  AS  CNT_T  ,  0  AS  PLAN_P  ,  0  AS  ENFRC_P  ,  0  AS  SPLMT_P  ,  0  AS  NEW_P  ,  0  AS  CNT_P  ,  0  AS  CNT_E  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  B.BRAN_CD  =  NVL(:BRAN_CD,  B.BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  B.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (B.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))   \n";
        query +=" AND  A.JOB_GBN  IN  ('V',  'T')  GROUP  BY  B.BRAN_CD,  A.INSPRES_ID,  A.VISIT_DAY,  A.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  A.BRAN_CD  ,  A.STAFF_CD  ,  0  AS  PLAN_V  ,  0  AS  ENFRC_V  ,  0  AS  SPLMT_V  ,  0  AS  NEW_V  ,  0  AS  CNT_V  ,  0  AS  CNT_T  ,  (CASE  WHEN  MAX(A.VISIT_YN)  KEEP  (DENSE_RANK  FIRST  ORDER  BY  A.INS_DATE  DESC)  IS  NOT  NULL  THEN  1  ELSE  0  END)  AS  PLAN_P  ,  (CASE  WHEN  MAX(A.VISIT_YN)  KEEP  (DENSE_RANK  FIRST  ORDER  BY  A.INS_DATE  DESC)  =  'Y'  THEN  1  ELSE  0  END)  AS  ENFRC_P  ,  (CASE  WHEN  MAX(A.VISIT_YN)  KEEP  (DENSE_RANK  FIRST  ORDER  BY  A.INS_DATE  DESC)  IS  NULL  THEN  1  ELSE  0  END)  AS  SPLMT_P  ,  0  AS  NEW_P  ,  0  AS  CNT_P  ,  0  AS  CNT_E  FROM  GIBU.TMOB_VISIT_PLAN  A  WHERE  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  A.BRAN_CD  =  NVL(:BRAN_CD,  A.BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))   \n";
        query +=" AND  A.JOB_GBN  =  'P'  GROUP  BY  A.BRAN_CD,  A.STAFF_CD,  A.VISIT_DAY,  A.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  B.BRAN_CD  ,  A.INSPRES_ID  AS  STAFF_CD  ,  0  AS  PLAN_V  ,  0  AS  ENFRC_V  ,  0  AS  SPLMT_V  ,  0  AS  NEW_V  ,  0  AS  CNT_V  ,  0  AS  CNT_T  ,  0  AS  PLAN_P  ,  0  AS  ENFRC_P  ,  0  AS  SPLMT_P  ,  (CASE  WHEN  MAX(A.JOB_GBN)  KEEP  (DENSE_RANK  FIRST  ORDER  BY  A.INS_DATE  DESC)  =  'E'  THEN  1  ELSE  0  END)  AS  NEW_P  ,  (CASE  WHEN  MAX(A.JOB_GBN)  KEEP  (DENSE_RANK  FIRST  ORDER  BY  A.INS_DATE  DESC)  =  'P'  THEN  1  ELSE  0  END)  AS  CNT_P  ,  (CASE  WHEN  MAX(A.JOB_GBN)  KEEP  (DENSE_RANK  FIRST  ORDER  BY  A.INS_DATE  DESC)  =  'E'  THEN  1  ELSE  0  END)  AS  CNT_E  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  B.BRAN_CD  =  NVL(:BRAN_CD,  B.BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  B.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (B.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))   \n";
        query +=" AND  A.JOB_GBN  IN  ('P',  'E')  GROUP  BY  B.BRAN_CD,  A.INSPRES_ID,  A.VISIT_DAY,  A.UPSO_CD  )  TA  ,  (   \n";
        query +=" SELECT  BRAN_CD,  STAFF_CD  FROM  GIBU.TBRA_NONPY_PRCON  WHERE  PRCON_YRMN  BETWEEN  SUBSTR(:START_DAY,  1,  6)   \n";
        query +=" AND  SUBSTR(:END_DAY,  1,  6)   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  BRAN_CD  =  NVL(:BRAN_CD,  BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))  GROUP  BY  BRAN_CD,  STAFF_CD  UNION   \n";
        query +=" SELECT  BRAN_CD,  STAFF_CD  FROM  GIBU.TMOB_VISIT_PLAN  A  WHERE  VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  BRAN_CD  =  NVL(:BRAN_CD,  BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))  GROUP  BY  BRAN_CD,  STAFF_CD  )  TB  WHERE  TA.STAFF_CD(+)  =  TB.STAFF_CD  GROUP  BY  TB.BRAN_CD,  TB.STAFF_CD  ORDER  BY  TB.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 전화 업무상세 조회
    public DOBJ CALLsel_work_stat_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_work_stat_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_work_stat_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   JOB_GBN = dobj.getRetObject("S").getRecord().get("JOB_GBN");   //업무 구분
        String   USER_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //사용자번호
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  STAFF_CD  ,  FIDU.GET_STAFF_NM(STAFF_CD)  AS  STAFF_NM  ,  BRAN_CD  ,  GIBU.GET_BRAN_NM(BRAN_CD)  AS  BRAN_NM  ,  SEQ  ,  VISIT_DAY  ,  UPSO_CD  ,  UPSO_NM  ,  ENFRC_TM  ,  SPLMT_TM  ,  NEW_TM  ,  NOVISIT_YN  FROM  (   \n";
        query +=" SELECT  STAFF_CD  ,  BRAN_CD  ,  ROW_NUMBER()  OVER(PARTITION  BY  STAFF_CD,  BRAN_CD  ORDER  BY  SEQ_TM)  AS  SEQ  ,  TO_CHAR(TO_DATE(VISIT_DAY,  'YYYYMMDD'),  'YYYY-MM-DD')  AS  VISIT_DAY  ,  UPSO_CD  ,  UPSO_NM  ,  ENFRC_TM  ,  SPLMT_TM  ,  NEW_TM  ,  NOVISIT_YN  FROM  (   \n";
        query +=" SELECT  A.STAFF_CD  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.VISIT_DAY  ,  DECODE(A.VISIT_YN,  'Y',  TO_CHAR(A.MOD_DATE,  'HH24:MI'),  '')  AS  ENFRC_TM  ,  (CASE  WHEN  A.VISIT_YN  IS  NULL  THEN  TO_CHAR(A.INS_DATE,  'HH24:MI')  ELSE  ''  END)  AS  SPLMT_TM  ,  ''  AS  NEW_TM  ,  (CASE  WHEN  A.VISIT_YN  =  'N'  THEN  '1'  ELSE  '0'  END)  AS  NOVISIT_YN  ,  (CASE  WHEN  A.VISIT_YN  =  'Y'  THEN  TO_CHAR(A.MOD_DATE,  'HH24:MI')  WHEN  A.VISIT_YN  IS  NULL  THEN  TO_CHAR(A.INS_DATE,  'HH24:MI')  ELSE  ''  END)  AS  SEQ_TM  FROM  GIBU.TMOB_VISIT_PLAN  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  A.BRAN_CD  =  NVL(:BRAN_CD,  A.BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  A.JOB_GBN  =  NVL(:JOB_GBN,  A.JOB_GBN)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  A.INSPRES_ID  AS  STAFF_CD  ,  B.BRAN_CD  ,  B.UPSO_CD  ,  B.UPSO_NM  ,  A.VISIT_DAY  ,  ''  AS  ENFRC_TM  ,  ''  AS  SPLMT_TM  ,  SUBSTR(VISIT_TIME,  1,  2)  ||  ':'  ||  SUBSTR(VISIT_TIME,  3,  2)  AS  NEW_TM  ,  '0'  AS  NOVISIT_YN  ,  SUBSTR(VISIT_TIME,  1,  2)  ||  ':'  ||  SUBSTR(VISIT_TIME,  3,  2)  AS  SEQ_TM  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  B.BRAN_CD  =  NVL(:BRAN_CD,  B.BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  B.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (B.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))   \n";
        query +=" AND  A.JOB_GBN  =  'E'   \n";
        query +=" AND  A.INSPRES_ID  =  NVL(:STAFF_CD,  A.INSPRES_ID)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  )  UNION  ALL   \n";
        query +=" SELECT  STAFF_CD  ,  BRAN_CD  ,  SEQ  ,  '전체  :  '  ||  TOTAL_TM  AS  VISIT_DAY  ,  '총실시  :  '  ||  (ENFRC_TM  +  SPLMT_TM)  AS  UPSO_CD  ,  '예정  :  '  ||  PLAN_TM  AS  UPSO_NM  ,  '실시  :  '  ||  ENFRC_TM  AS  ENFRC_TM  ,  '추가  :  '  ||  SPLMT_TM  AS  SPLMT_TM  ,  '기타  :  '  ||  NEW_TM  AS  NEW_TM  ,  ''  AS  NOVISIT_YN  FROM  (   \n";
        query +=" SELECT  STAFF_CD  ,  BRAN_CD  ,  999  AS  SEQ  ,  SUM(TOTAL_CNT)  AS  TOTAL_TM  ,  SUM(PLAN_CNT)  AS  PLAN_TM  ,  SUM(ENFRC_CNT)  AS  ENFRC_TM  ,  SUM(SPLMT_CNT)  AS  SPLMT_TM  ,  SUM(NEW_CNT)  AS  NEW_TM  FROM  (   \n";
        query +=" SELECT  A.STAFF_CD  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.VISIT_DAY  ,  1  AS  TOTAL_CNT  ,  (CASE  WHEN  A.VISIT_YN  IS  NOT  NULL  THEN  1  ELSE  0  END)  AS  PLAN_CNT  ,  DECODE(A.VISIT_YN,  'Y',  1,  0)  AS  ENFRC_CNT  ,  (CASE  WHEN  A.VISIT_YN  IS  NULL  THEN  1  ELSE  0  END)  AS  SPLMT_CNT  ,  0  AS  NEW_CNT  FROM  GIBU.TMOB_VISIT_PLAN  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  A.BRAN_CD  =  NVL(:BRAN_CD,  A.BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  A.JOB_GBN  =  NVL(:JOB_GBN,  A.JOB_GBN)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  A.STAFF_CD  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  A.UPSO_NM  ,  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  ,  0  AS  TOTAL_CNT  ,  0  AS  PLAN_CNT  ,  0  AS  ENFRC_CNT  ,  0  AS  SPLMT_CNT  ,  1  AS  NEW_CNT  FROM  GIBU.TMOB_NEW_UPSO  A  WHERE  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  (  (:BRAN_CD  <>  'AL'   \n";
        query +=" AND  A.BRAN_CD  =  NVL(:BRAN_CD,  A.BRAN_CD))   \n";
        query +=" OR  (:BRAN_CD  =  'AL'   \n";
        query +=" AND  A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)))   \n";
        query +=" OR  (  :BRAN_CD  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  =   \n";
        query +=" (SELECT  DEPT_CD  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  :USER_ID))  =  'AL'   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID)  =  0   \n";
        query +=" AND  (A.BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  =  '121020000'))))   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)  )  GROUP  BY  STAFF_CD,  BRAN_CD  )  )  ORDER  BY  BRAN_CD,  STAFF_CD,  SEQ ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$sel_work_stat
    //##**$$end
}
