
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra13_s01
{
    public bra13_s01()
    {
    }
    //##**$$plan_vs_col_select
    /*
    */
    public DOBJ CTLplan_vs_col_select(DOBJ dobj)
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
            dobj  = CALLplan_vs_col_select_SEL1(Conn, dobj);           //  사원별 조회
            dobj  = CALLplan_vs_col_select_SEL2(Conn, dobj);           //  지부별 현황
            dobj  = CALLplan_vs_col_select_SEL3(Conn, dobj);           //  관리자별통계
            dobj  = CALLplan_vs_col_select_SEL6(Conn, dobj);           //  관리자별통계(유단노)
            dobj  = CALLplan_vs_col_select_SEL4(Conn, dobj);           //  사원별조회(유단노)
            dobj  = CALLplan_vs_col_select_SEL5(Conn, dobj);           //  지부별조회(유단노)
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
    public DOBJ CTLplan_vs_col_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLplan_vs_col_select_SEL1(Conn, dobj);           //  사원별 조회
        dobj  = CALLplan_vs_col_select_SEL2(Conn, dobj);           //  지부별 현황
        dobj  = CALLplan_vs_col_select_SEL3(Conn, dobj);           //  관리자별통계
        dobj  = CALLplan_vs_col_select_SEL6(Conn, dobj);           //  관리자별통계(유단노)
        dobj  = CALLplan_vs_col_select_SEL4(Conn, dobj);           //  사원별조회(유단노)
        dobj  = CALLplan_vs_col_select_SEL5(Conn, dobj);           //  지부별조회(유단노)
        return dobj;
    }
    // 사원별 조회
    public DOBJ CALLplan_vs_col_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLplan_vs_col_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLplan_vs_col_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USER_ID = dobj.getRetObject("S").getRecord().get("USER_ID");   //사용자번호
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(A.BRAN_CD,  B.BRAN_CD)  BRAN_CD  ,  NVL(A.BRAN_NM,  B.BRAN_NM)  BRAN_NM  ,  NVL(A.STAFF_CD,  B.STAFF_CD)  STAFF_CD  ,  NVL(A.STAFF_NM,  B.STAFF_NM)  STAFF_NM  ,  A.PLAN_AMT  ,  B.COL_AMT  ,  DECODE(A.PLAN_AMT,  0,  0,  NULL,  0,  TRUNC(B.COL_AMT/A.PLAN_AMT*100,1))||'%'  C_RATE  ,  A.CNT_UPSO  ,  A.CNT_3MONTH  ,  A.RATE_3MONTH  ,  A.CNT_6MONTH  ,  A.CNT_6MONTH_NT  ,  A.RATE_6MONTH  ,  A.CNT_8MONTH  ,  A.CNT_ACCU  ,  A.RATE_ACCU  ,  B.MON_AMT  ,  B.UPSO_CNT  ,  B.NONPY_AMT  ,  B.NONPY_CNT  ,  B.COL_UPSO_CNT  ,  B.STAFF_AMT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TMOB_VISIT_PLAN  WHERE  VISIT_DAY  LIKE  :PRCON_YRMN  ||  '%'   \n";
        query +=" AND  JOB_GBN  =  'V'   \n";
        query +=" AND  (VISIT_YN  =  'Y'   \n";
        query +=" OR  VISIT_YN  IS  NULL)   \n";
        query +=" AND  STAFF_CD  =  NVL(A.STAFF_CD,  B.STAFF_CD))  AS  VISIT_CNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TMOB_VISIT_PLAN  WHERE  VISIT_DAY  LIKE  :PRCON_YRMN  ||  '%'   \n";
        query +=" AND  JOB_GBN  =  'P'   \n";
        query +=" AND  (VISIT_YN  =  'Y'   \n";
        query +=" OR  VISIT_YN  IS  NULL)   \n";
        query +=" AND  STAFF_CD  =  NVL(A.STAFF_CD,  B.STAFF_CD))  AS  PHONE_CNT  ,  TRUNC(A.CNT_AUTO  /  A.CNT_UPSO  *  100,  1)  AS  AUTO_RATE  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  FIDU.GET_GIBU_NM(A.BRAN_CD)  AS  BRAN_NM  ,  FIDU.GET_STAFF_NM(A.STAFF_CD)  AS  STAFF_NM  ,  A.STAFF_CD  ,  A.CNT_UPSO  ,  A.CNT_3MONTH  ,  TRUNC(A.CNT_3MONTH  /  DECODE(A.CNT_UPSO,  0,  1,  A.CNT_UPSO)  *  100,  1)  AS  RATE_3MONTH  ,  A.CNT_6MONTH  ,  A.CNT_6MONTH_NT  ,  TRUNC(A.CNT_6MONTH_NT  /  DECODE(A.CNT_UPSO,  0,  1,  A.CNT_UPSO)  *  100,  1)  AS  RATE_6MONTH  ,  A.CNT_ACCU  ,  TRUNC(A.CNT_ACCU  /  DECODE(A.CNT_8MONTH,  0,  1,  A.CNT_8MONTH)  *  100,  1)  AS  RATE_ACCU  ,  A.CNT_8MONTH  ,  B.PLAN_AMT  ,  A.CNT_AUTO  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  STAFF_CD  ,  COUNT(1)  AS  CNT_UPSO  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  3  THEN  1  ELSE  0  END))  AS  CNT_3MONTH  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  6  THEN  1  ELSE  0  END))  AS  CNT_6MONTH  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  6   \n";
        query +=" AND  INSTR(REMAK,  '고소중')  =  0  THEN  1  ELSE  0  END))  AS  CNT_6MONTH_NT  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  8  THEN  1  ELSE  0  END))  AS  CNT_8MONTH  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  8   \n";
        query +=" AND  INSTR(REMAK,  '고소중')  >  0  THEN  1  ELSE  0  END))  AS  CNT_ACCU  ,  SUM((CASE  WHEN  AUTO_YN  =  'OK'  THEN  1  ELSE  0  END))  AS  CNT_AUTO  FROM  GIBU.TBRA_NONPY_PRCON  A  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  (  (:USER_ID  IS  NULL)   \n";
        query +=" OR  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID))))  GROUP  BY  BRAN_CD,  STAFF_CD  )  A  ,  (   \n";
        query +=" SELECT  STAFF_CD  ,  BRAN_CD  ,  DECODE(SUBSTR(:PRCON_YRMN,5,2),'01',MON1_AMT,'02',MON2_AMT,'03',MON3_AMT,'04',MON4_AMT,'05',MON5_AMT,'06',MON6_AMT,'07',MON7_AMT,'08',MON8_AMT,'09',MON9_AMT,'10',MON10_AMT,'11',MON11_AMT,'12',MON12_AMT)  PLAN_AMT  FROM  GIBU.TBRA_COLLECT_PLAN  WHERE  YYYY  =  SUBSTR(:PRCON_YRMN,1,4)   \n";
        query +=" AND  GBN  =  '1'  )  B  WHERE  A.STAFF_CD  =  B.STAFF_CD(+)   \n";
        query +=" AND  A.BRAN_CD  =  B.BRAN_CD(+)  )  A  FULL  OUTER  JOIN  (   \n";
        query +=" SELECT  PRCON_YRMN  ,  BRAN_CD  ,  BRAN_NM  ,  STAFF_CD  ,  SUM(MON_AMT)  AS  MON_AMT  ,  SUM(UPSO_CNT)  AS  UPSO_CNT  ,  SUM(NONPY_AMT)  AS  NONPY_AMT  ,  SUM(NONPY_CNT)  AS  NONPY_CNT  ,  SUM(COL_AMT)  AS  COL_AMT  ,  SUM(COL_UPSO_CNT)  AS  COL_UPSO_CNT  ,  STAFF_NM  ,  SUM(STAFF_AMT)  AS  STAFF_AMT  FROM  GIBU.TBRA_STAFF_CLCT_PRCON  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  SEQ  =  '1'   \n";
        query +=" AND  (  (:USER_ID  IS  NULL)   \n";
        query +=" OR  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID))))   \n";
        query +=" AND  YDN_GBN  =  '0'  GROUP  BY  PRCON_YRMN,  SEQ,  BRAN_CD,  STAFF_CD,  STAFF_NM,  BRAN_NM  )  B  ON  A.STAFF_CD  =  B.STAFF_CD   \n";
        query +=" AND  A.BRAN_CD  =  B.BRAN_CD  ORDER  BY  1,  3 ";
        sobj.setSql(query);
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    // 지부별 현황
    public DOBJ CALLplan_vs_col_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLplan_vs_col_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLplan_vs_col_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USER_ID = dobj.getRetObject("S").getRecord().get("USER_ID");   //사용자번호
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(A.BRAN_CD,  B.BRAN_CD)  BRAN_CD  ,  NVL(A.BRAN_NM,  B.BRAN_NM)  BRAN_NM  ,  SUM(A.PLAN_AMT)  PLAN_AMT  ,  SUM(B.COL_AMT)  COL_AMT  ,  DECODE(SUM(A.PLAN_AMT),  0,  0,  NULL,  0,  TRUNC(SUM(B.COL_AMT)/SUM(A.PLAN_AMT)*100,2))||'%'  C_RATE  ,  SUM(A.CNT_UPSO)  CNT_UPSO  ,  SUM(A.CNT_3MONTH)  CNT_3MONTH  ,  TRUNC(SUM(A.CNT_3MONTH)  /  DECODE(SUM(A.CNT_UPSO),  0,  1,  SUM(A.CNT_UPSO))  *  100,  1)  RATE_3MONTH  ,  SUM(A.CNT_6MONTH)  CNT_6MONTH  ,  SUM(A.CNT_6MONTH_NT)  CNT_6MONTH_NT  ,  TRUNC(SUM(A.CNT_6MONTH_NT)  /  DECODE(SUM(A.CNT_UPSO),  0,  1,  SUM(A.CNT_UPSO))  *  100,  1)  RATE_6MONTH  ,  SUM(A.CNT_ACCU)  CNT_ACCU  ,  TRUNC(SUM(A.CNT_ACCU)  /  DECODE(SUM(A.CNT_8MONTH),  0,  1,  SUM(A.CNT_8MONTH))  *  100,  1)  RATE_ACCU  ,  SUM(A.CNT_8MONTH)  CNT_8MONTH  ,  SUM(B.MON_AMT)  MON_AMT  ,  SUM(B.UPSO_CNT)  UPSO_CNT  ,  SUM(B.NONPY_AMT)  NONPY_AMT  ,  SUM(B.NONPY_CNT)  NONPY_CNT  ,  SUM(B.COL_UPSO_CNT)  COL_UPSO_CNT  ,  SUM(B.STAFF_AMT)  STAFF_AMT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TMOB_VISIT_PLAN  WHERE  VISIT_DAY  LIKE  :PRCON_YRMN  ||  '%'   \n";
        query +=" AND  JOB_GBN  =  'V'   \n";
        query +=" AND  (VISIT_YN  =  'Y'   \n";
        query +=" OR  VISIT_YN  IS  NULL)   \n";
        query +=" AND  BRAN_CD  =  NVL(A.BRAN_CD,  B.BRAN_CD))  AS  VISIT_CNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TMOB_VISIT_PLAN  WHERE  VISIT_DAY  LIKE  :PRCON_YRMN  ||  '%'   \n";
        query +=" AND  JOB_GBN  =  'P'   \n";
        query +=" AND  (VISIT_YN  =  'Y'   \n";
        query +=" OR  VISIT_YN  IS  NULL)   \n";
        query +=" AND  BRAN_CD  =  NVL(A.BRAN_CD,  B.BRAN_CD))  AS  PHONE_CNT  ,  TRUNC(SUM(A.CNT_AUTO)  /  SUM(A.CNT_UPSO)  *  100,  1)  AS  AUTO_RATE  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  FIDU.GET_GIBU_NM(A.BRAN_CD)  AS  BRAN_NM  ,  FIDU.GET_STAFF_NM(A.STAFF_CD)  AS  STAFF_NM  ,  A.STAFF_CD  ,  A.CNT_UPSO  ,  A.CNT_3MONTH  ,  TRUNC(A.CNT_3MONTH  /  DECODE(A.CNT_UPSO,  0,  1,  A.CNT_UPSO)  *  100,  1)  AS  RATE_3MONTH  ,  A.CNT_6MONTH  ,  A.CNT_6MONTH_NT  ,  TRUNC(A.CNT_6MONTH_NT  /  DECODE(A.CNT_UPSO,  0,  1,  A.CNT_UPSO)  *  100,  1)  AS  RATE_6MONTH  ,  A.CNT_ACCU  ,  TRUNC(A.CNT_ACCU  /  DECODE(A.CNT_8MONTH,  0,  1,  A.CNT_8MONTH)  *  100,  1)  AS  RATE_ACCU  ,  A.CNT_8MONTH  ,  B.PLAN_AMT  ,  A.CNT_AUTO  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  STAFF_CD  ,  COUNT(1)  AS  CNT_UPSO  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  3  THEN  1  ELSE  0  END))  AS  CNT_3MONTH  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  6  THEN  1  ELSE  0  END))  AS  CNT_6MONTH  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  6   \n";
        query +=" AND  INSTR(REMAK,  '고소중')  =  0  THEN  1  ELSE  0  END))  AS  CNT_6MONTH_NT  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  8  THEN  1  ELSE  0  END))  AS  CNT_8MONTH  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  8   \n";
        query +=" AND  INSTR(REMAK,  '고소중')  >  0  THEN  1  ELSE  0  END))  AS  CNT_ACCU  ,  SUM((CASE  WHEN  AUTO_YN  =  'OK'  THEN  1  ELSE  0  END))  AS  CNT_AUTO  FROM  GIBU.TBRA_NONPY_PRCON  A  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  (  (:USER_ID  IS  NULL)   \n";
        query +=" OR  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID))))  GROUP  BY  BRAN_CD,  STAFF_CD  )  A  ,  (   \n";
        query +=" SELECT  STAFF_CD  ,  BRAN_CD  ,  DECODE(SUBSTR(:PRCON_YRMN,5,2),'01',MON1_AMT,'02',MON2_AMT,'03',MON3_AMT,'04',MON4_AMT,'05',MON5_AMT,'06',MON6_AMT,'07',MON7_AMT,'08',MON8_AMT,'09',MON9_AMT,'10',MON10_AMT,'11',MON11_AMT,'12',MON12_AMT)  PLAN_AMT  FROM  GIBU.TBRA_COLLECT_PLAN  WHERE  YYYY  =  SUBSTR(:PRCON_YRMN,1,4)   \n";
        query +=" AND  GBN  =  '1'  )  B  WHERE  A.STAFF_CD  =  B.STAFF_CD(+)   \n";
        query +=" AND  A.BRAN_CD  =  B.BRAN_CD(+)  )  A  FULL  OUTER  JOIN  (   \n";
        query +=" SELECT  PRCON_YRMN  ,  BRAN_CD  ,  BRAN_NM  ,  STAFF_CD  ,  SUM(MON_AMT)  AS  MON_AMT  ,  SUM(UPSO_CNT)  AS  UPSO_CNT  ,  SUM(NONPY_AMT)  AS  NONPY_AMT  ,  SUM(NONPY_CNT)  AS  NONPY_CNT  ,  SUM(COL_AMT)  AS  COL_AMT  ,  SUM(COL_UPSO_CNT)  AS  COL_UPSO_CNT  ,  STAFF_NM  ,  SUM(STAFF_AMT)  AS  STAFF_AMT  FROM  GIBU.TBRA_STAFF_CLCT_PRCON  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  SEQ  =  '1'   \n";
        query +=" AND  (  (:USER_ID  IS  NULL)   \n";
        query +=" OR  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID))))   \n";
        query +=" AND  YDN_GBN  =  '0'  GROUP  BY  PRCON_YRMN,  SEQ,  BRAN_CD,  STAFF_CD,  STAFF_NM,  BRAN_NM  )  B  ON  A.STAFF_CD  =  B.STAFF_CD   \n";
        query +=" AND  A.BRAN_CD  =  B.BRAN_CD  GROUP  BY  NVL(A.BRAN_CD,  B.BRAN_CD),  NVL(A.BRAN_NM,  B.BRAN_NM)  ORDER  BY  1,  3 ";
        sobj.setSql(query);
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    // 관리자별통계
    public DOBJ CALLplan_vs_col_select_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLplan_vs_col_select_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLplan_vs_col_select_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USER_ID = dobj.getRetObject("S").getRecord().get("USER_ID");   //사용자번호
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UDTKPRES_ID  ,  FIDU.GET_STAFF_NM(UDTKPRES_ID)  AS  UDTKPRES_NM  ,  SUM(PLAN_AMT)  AS  PLAN_AMT  ,  SUM(COL_AMT)  AS  COL_AMT  ,  TRUNC(DECODE(NVL(SUM(COL_AMT),  0),  0,  1,  SUM(COL_AMT))  /  DECODE(NVL(SUM(PLAN_AMT),  0),  0,  1,  SUM(PLAN_AMT))  *  100,  1)  ||  '%'  AS  C_RATE  ,  '달성율  :  '  ||  DECODE(NVL(SUM(PLAN_AMT),  0),  0,  0,  (TRUNC(DECODE(NVL(SUM(COL_AMT),  0),  0,  1,  SUM(COL_AMT))  /  DECODE(NVL(SUM(PLAN_AMT),  0),  0,  1,  SUM(PLAN_AMT))  *  100,  1)))  ||  '%'  ||  CHR(10)  ||  '징수금액  :  '  ||  TO_CHAR(SUM(COL_AMT),'FM999,999,999,999,999')  ||  CHR(10)  ||  '징수예산  :  '  ||  TO_CHAR(SUM(PLAN_AMT),'FM999,999,999,999,999')  AS  COL_TEXT  ,  COUNT(1)  AS  MAN_STAFF  ,  SUM(CNT_UPSO)  AS  MAN_UPSO  ,  SUM(YDN_UPSO)  AS  YDN_UPSO  ,  (   \n";
        query +=" SELECT  LISTAGG(SUBSTR(DEPT_NM,  1,  2),  ',  ')  WITHIN  GROUP  (ORDER  BY  ROWNUM)  ||  '지역'  AS  RESULT  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  TB.UDTKPRES_ID)  )  AS  MAN_LOC  ,   \n";
        query +=" (SELECT  MIN(DEPT_CD)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  TB.UDTKPRES_ID)  AS  SORT_F  FROM  (   \n";
        query +=" SELECT  NVL(A.BRAN_CD,  B.BRAN_CD)  AS  BRAN_CD  ,  NVL(A.STAFF_CD,  B.STAFF_CD)  AS  STAFF_CD  ,  A.CNT_UPSO  ,  A.YDN_UPSO  ,  B.COL_AMT  ,  C.PLAN_AMT  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  STAFF_CD  ,  COUNT(1)  AS  CNT_UPSO  ,  SUM((CASE  WHEN  GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p')  THEN  1  ELSE  0  END))  AS  YDN_UPSO  FROM  GIBU.TBRA_NONPY_PRCON  A  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  (  (:USER_ID  IS  NULL)   \n";
        query +=" OR  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID))))  GROUP  BY  BRAN_CD,  STAFF_CD  )  A  ,  (   \n";
        query +=" SELECT  PRCON_YRMN  ,  BRAN_CD  ,  STAFF_CD  ,  SUM(COL_AMT)  AS  COL_AMT  FROM  GIBU.TBRA_STAFF_CLCT_PRCON  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  SEQ  =  '1'   \n";
        query +=" AND  (  (:USER_ID  IS  NULL)   \n";
        query +=" OR  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID))))   \n";
        query +=" AND  YDN_GBN  =  '0'  GROUP  BY  PRCON_YRMN  ,  SEQ  ,  BRAN_CD  ,  STAFF_CD  ,  STAFF_NM  ,  BRAN_NM  )  B  ,  (   \n";
        query +=" SELECT  STAFF_CD  ,  BRAN_CD  ,  DECODE(SUBSTR(:PRCON_YRMN,  5,  2),  '01',  MON1_AMT,  '02',  MON2_AMT,  '03',  MON3_AMT,  '04',  MON4_AMT,  '05',  MON5_AMT,  '06',  MON6_AMT,  '07',  MON7_AMT,  '08',  MON8_AMT,  '09',  MON9_AMT,  '10',  MON10_AMT,  '11',  MON11_AMT,  '12',  MON12_AMT)  AS  PLAN_AMT  FROM  GIBU.TBRA_COLLECT_PLAN  WHERE  YYYY  =  SUBSTR(:PRCON_YRMN,  1,  4)   \n";
        query +=" AND  (  (:USER_ID  IS  NULL)   \n";
        query +=" OR  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID))))   \n";
        query +=" AND  GBN  =  '1'  )  C  WHERE  A.STAFF_CD  =  B.STAFF_CD   \n";
        query +=" AND  A.BRAN_CD  =  B.BRAN_CD   \n";
        query +=" AND  A.STAFF_CD  =  C.STAFF_CD(+)   \n";
        query +=" AND  A.BRAN_CD  =  C.BRAN_CD(+)  )  TA  ,  (   \n";
        query +=" SELECT  GIBU  ,  UDTKPRES_ID  FROM  GIBU.TBRA_BILL_AUTH  A  ,  INSA.TCPM_DEPT  B  WHERE  A.GBN  =  'B'   \n";
        query +=" AND  A.DEPT_CD  =  B.DEPT_CD  )  TB  WHERE  TA.BRAN_CD  =  TB.GIBU  GROUP  BY  UDTKPRES_ID  ORDER  BY  11 ";
        sobj.setSql(query);
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    // 관리자별통계(유단노)
    public DOBJ CALLplan_vs_col_select_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLplan_vs_col_select_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLplan_vs_col_select_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USER_ID = dobj.getRetObject("S").getRecord().get("USER_ID");   //사용자번호
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UDTKPRES_ID  ,  FIDU.GET_STAFF_NM(UDTKPRES_ID)  AS  UDTKPRES_NM  ,  SUM(PLAN_AMT)  AS  PLAN_AMT  ,  SUM(COL_AMT)  AS  COL_AMT  ,  TRUNC(DECODE(NVL(SUM(COL_AMT),  0),  0,  1,  SUM(COL_AMT))  /  DECODE(NVL(SUM(PLAN_AMT),  0),  0,  1,  SUM(PLAN_AMT))  *  100,  1)  ||  '%'  AS  C_RATE  ,  '달성율  :  '  ||  DECODE(NVL(SUM(PLAN_AMT),  0),  0,  0,  (TRUNC(DECODE(NVL(SUM(COL_AMT),  0),  0,  1,  SUM(COL_AMT))  /  DECODE(NVL(SUM(PLAN_AMT),  0),  0,  1,  SUM(PLAN_AMT))  *  100,  1)))  ||  '%'  ||  CHR(10)  ||  '징수금액  :  '  ||  TO_CHAR(SUM(COL_AMT),'FM999,999,999,999,999')  ||  CHR(10)  ||  '징수예산  :  '  ||  TO_CHAR(SUM(PLAN_AMT),'FM999,999,999,999,999')  AS  COL_TEXT  ,  COUNT(1)  AS  MAN_STAFF  ,  SUM(CNT_UPSO)  AS  MAN_UPSO  ,  SUM(YDN_UPSO)  AS  YDN_UPSO  ,  (   \n";
        query +=" SELECT  LISTAGG(SUBSTR(DEPT_NM,  1,  2),  ',  ')  WITHIN  GROUP  (ORDER  BY  ROWNUM)  ||  '지역'  AS  RESULT  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  TB.UDTKPRES_ID)  )  AS  MAN_LOC  ,   \n";
        query +=" (SELECT  MIN(DEPT_CD)  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  TB.UDTKPRES_ID)  AS  SORT_F  FROM  (   \n";
        query +=" SELECT  NVL(A.BRAN_CD,  B.BRAN_CD)  AS  BRAN_CD  ,  NVL(A.STAFF_CD,  B.STAFF_CD)  AS  STAFF_CD  ,  A.CNT_UPSO  ,  A.YDN_UPSO  ,  B.COL_AMT  ,  C.PLAN_AMT  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  STAFF_CD  ,  COUNT(1)  AS  CNT_UPSO  ,  SUM((CASE  WHEN  GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD)  IN  ('J','f','o','y','l','k','m','n','p')  THEN  1  ELSE  0  END))  AS  YDN_UPSO  FROM  GIBU.TBRA_NONPY_PRCON  A  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  (  (:USER_ID  IS  NULL)   \n";
        query +=" OR  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID))))  GROUP  BY  BRAN_CD,  STAFF_CD  )  A  ,  (   \n";
        query +=" SELECT  PRCON_YRMN  ,  BRAN_CD  ,  STAFF_CD  ,  SUM(COL_AMT)  AS  COL_AMT  FROM  GIBU.TBRA_STAFF_CLCT_PRCON  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  SEQ  =  '1'   \n";
        query +=" AND  (  (:USER_ID  IS  NULL)   \n";
        query +=" OR  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID))))   \n";
        query +=" AND  YDN_GBN  =  '1'  GROUP  BY  PRCON_YRMN  ,  SEQ  ,  BRAN_CD  ,  STAFF_CD  ,  STAFF_NM  ,  BRAN_NM  )  B  ,  (   \n";
        query +=" SELECT  STAFF_CD  ,  BRAN_CD  ,  DECODE(SUBSTR(:PRCON_YRMN,  5,  2),  '01',  MON1_AMT,  '02',  MON2_AMT,  '03',  MON3_AMT,  '04',  MON4_AMT,  '05',  MON5_AMT,  '06',  MON6_AMT,  '07',  MON7_AMT,  '08',  MON8_AMT,  '09',  MON9_AMT,  '10',  MON10_AMT,  '11',  MON11_AMT,  '12',  MON12_AMT)  AS  PLAN_AMT  FROM  GIBU.TBRA_COLLECT_PLAN  WHERE  YYYY  =  SUBSTR(:PRCON_YRMN,  1,  4)   \n";
        query +=" AND  (  (:USER_ID  IS  NULL)   \n";
        query +=" OR  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID))))   \n";
        query +=" AND  GBN  =  '2'  )  C  WHERE  A.STAFF_CD  =  B.STAFF_CD   \n";
        query +=" AND  A.BRAN_CD  =  B.BRAN_CD   \n";
        query +=" AND  A.STAFF_CD  =  C.STAFF_CD(+)   \n";
        query +=" AND  A.BRAN_CD  =  C.BRAN_CD(+)  )  TA  ,  (   \n";
        query +=" SELECT  GIBU  ,  UDTKPRES_ID  FROM  GIBU.TBRA_BILL_AUTH  A  ,  INSA.TCPM_DEPT  B  WHERE  A.GBN  =  'B'   \n";
        query +=" AND  A.DEPT_CD  =  B.DEPT_CD  )  TB  WHERE  TA.BRAN_CD  =  TB.GIBU  GROUP  BY  UDTKPRES_ID  ORDER  BY  11 ";
        sobj.setSql(query);
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    // 사원별조회(유단노)
    public DOBJ CALLplan_vs_col_select_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLplan_vs_col_select_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLplan_vs_col_select_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USER_ID = dobj.getRetObject("S").getRecord().get("USER_ID");   //사용자번호
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(A.BRAN_CD,  B.BRAN_CD)  BRAN_CD  ,  NVL(A.BRAN_NM,  B.BRAN_NM)  BRAN_NM  ,  NVL(A.STAFF_CD,  B.STAFF_CD)  STAFF_CD  ,  NVL(A.STAFF_NM,  B.STAFF_NM)  STAFF_NM  ,  A.PLAN_AMT  ,  B.COL_AMT  ,  DECODE(A.PLAN_AMT,  0,  0,  NULL,  0,  TRUNC(B.COL_AMT/A.PLAN_AMT*100,1))||'%'  C_RATE  ,  A.CNT_UPSO  ,  A.CNT_3MONTH  ,  A.RATE_3MONTH  ,  A.CNT_6MONTH  ,  A.CNT_6MONTH_NT  ,  A.RATE_6MONTH  ,  A.CNT_8MONTH  ,  A.CNT_ACCU  ,  A.RATE_ACCU  ,  B.MON_AMT  ,  B.UPSO_CNT  ,  B.NONPY_AMT  ,  B.NONPY_CNT  ,  B.COL_UPSO_CNT  ,  B.STAFF_AMT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TMOB_VISIT_PLAN  WHERE  VISIT_DAY  LIKE  :PRCON_YRMN  ||  '%'   \n";
        query +=" AND  JOB_GBN  =  'V'   \n";
        query +=" AND  (VISIT_YN  =  'Y'   \n";
        query +=" OR  VISIT_YN  IS  NULL)   \n";
        query +=" AND  STAFF_CD  =  NVL(A.STAFF_CD,  B.STAFF_CD))  AS  VISIT_CNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TMOB_VISIT_PLAN  WHERE  VISIT_DAY  LIKE  :PRCON_YRMN  ||  '%'   \n";
        query +=" AND  JOB_GBN  =  'P'   \n";
        query +=" AND  (VISIT_YN  =  'Y'   \n";
        query +=" OR  VISIT_YN  IS  NULL)   \n";
        query +=" AND  STAFF_CD  =  NVL(A.STAFF_CD,  B.STAFF_CD))  AS  PHONE_CNT  ,  TRUNC(A.CNT_AUTO  /  A.CNT_UPSO  *  100,  1)  AS  AUTO_RATE  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  FIDU.GET_GIBU_NM(A.BRAN_CD)  AS  BRAN_NM  ,  FIDU.GET_STAFF_NM(A.STAFF_CD)  AS  STAFF_NM  ,  A.STAFF_CD  ,  A.CNT_UPSO  ,  A.CNT_3MONTH  ,  TRUNC(A.CNT_3MONTH  /  DECODE(A.CNT_UPSO,  0,  1,  A.CNT_UPSO)  *  100,  1)  AS  RATE_3MONTH  ,  A.CNT_6MONTH  ,  A.CNT_6MONTH_NT  ,  TRUNC(A.CNT_6MONTH_NT  /  DECODE(A.CNT_UPSO,  0,  1,  A.CNT_UPSO)  *  100,  1)  AS  RATE_6MONTH  ,  A.CNT_ACCU  ,  TRUNC(A.CNT_ACCU  /  DECODE(A.CNT_8MONTH,  0,  1,  A.CNT_8MONTH)  *  100,  1)  AS  RATE_ACCU  ,  A.CNT_8MONTH  ,  B.PLAN_AMT  ,  A.CNT_AUTO  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  STAFF_CD  ,  COUNT(1)  AS  CNT_UPSO  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  3  THEN  1  ELSE  0  END))  AS  CNT_3MONTH  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  6  THEN  1  ELSE  0  END))  AS  CNT_6MONTH  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  6   \n";
        query +=" AND  INSTR(REMAK,  '고소중')  =  0  THEN  1  ELSE  0  END))  AS  CNT_6MONTH_NT  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  8  THEN  1  ELSE  0  END))  AS  CNT_8MONTH  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  8   \n";
        query +=" AND  INSTR(REMAK,  '고소중')  >  0  THEN  1  ELSE  0  END))  AS  CNT_ACCU  ,  SUM((CASE  WHEN  AUTO_YN  =  'OK'  THEN  1  ELSE  0  END))  AS  CNT_AUTO  FROM  GIBU.TBRA_NONPY_PRCON  A  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  BSTYP_CD  IN  ('J','f','o','y','l','k','m','n','p')   \n";
        query +=" AND  (  (:USER_ID  IS  NULL)   \n";
        query +=" OR  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID))))  GROUP  BY  BRAN_CD,  STAFF_CD  )  A  ,  (   \n";
        query +=" SELECT  STAFF_CD  ,  BRAN_CD  ,  DECODE(SUBSTR(:PRCON_YRMN,5,2),'01',MON1_AMT,'02',MON2_AMT,'03',MON3_AMT,'04',MON4_AMT,'05',MON5_AMT,'06',MON6_AMT,'07',MON7_AMT,'08',MON8_AMT,'09',MON9_AMT,'10',MON10_AMT,'11',MON11_AMT,'12',MON12_AMT)  PLAN_AMT  FROM  GIBU.TBRA_COLLECT_PLAN  WHERE  YYYY  =  SUBSTR(:PRCON_YRMN,1,4)   \n";
        query +=" AND  GBN  =  '2'  )  B  WHERE  A.STAFF_CD  =  B.STAFF_CD(+)   \n";
        query +=" AND  A.BRAN_CD  =  B.BRAN_CD(+)  )  A  FULL  OUTER  JOIN  (   \n";
        query +=" SELECT  PRCON_YRMN  ,  BRAN_CD  ,  BRAN_NM  ,  STAFF_CD  ,  SUM(MON_AMT)  AS  MON_AMT  ,  SUM(UPSO_CNT)  AS  UPSO_CNT  ,  SUM(NONPY_AMT)  AS  NONPY_AMT  ,  SUM(NONPY_CNT)  AS  NONPY_CNT  ,  SUM(COL_AMT)  AS  COL_AMT  ,  SUM(COL_UPSO_CNT)  AS  COL_UPSO_CNT  ,  STAFF_NM  ,  SUM(STAFF_AMT)  AS  STAFF_AMT  FROM  GIBU.TBRA_STAFF_CLCT_PRCON  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  SEQ  =  '1'   \n";
        query +=" AND  (  (:USER_ID  IS  NULL)   \n";
        query +=" OR  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID))))   \n";
        query +=" AND  YDN_GBN  =  '1'  GROUP  BY  PRCON_YRMN,  SEQ,  BRAN_CD,  STAFF_CD,  STAFF_NM,  BRAN_NM  )  B  ON  A.STAFF_CD  =  B.STAFF_CD   \n";
        query +=" AND  A.BRAN_CD  =  B.BRAN_CD  ORDER  BY  1,  3 ";
        sobj.setSql(query);
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    // 지부별조회(유단노)
    public DOBJ CALLplan_vs_col_select_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLplan_vs_col_select_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLplan_vs_col_select_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USER_ID = dobj.getRetObject("S").getRecord().get("USER_ID");   //사용자번호
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("PRCON_YRMN");   //현황 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(A.BRAN_CD,  B.BRAN_CD)  BRAN_CD  ,  NVL(A.BRAN_NM,  B.BRAN_NM)  BRAN_NM  ,  SUM(A.PLAN_AMT)  PLAN_AMT  ,  SUM(B.COL_AMT)  COL_AMT  ,  DECODE(SUM(A.PLAN_AMT),  0,  0,  NULL,  0,  TRUNC(SUM(B.COL_AMT)/SUM(A.PLAN_AMT)*100,2))||'%'  C_RATE  ,  SUM(A.CNT_UPSO)  CNT_UPSO  ,  SUM(A.CNT_3MONTH)  CNT_3MONTH  ,  TRUNC(SUM(A.CNT_3MONTH)  /  DECODE(SUM(A.CNT_UPSO),  0,  1,  SUM(A.CNT_UPSO))  *  100,  1)  RATE_3MONTH  ,  SUM(A.CNT_6MONTH)  CNT_6MONTH  ,  SUM(A.CNT_6MONTH_NT)  CNT_6MONTH_NT  ,  TRUNC(SUM(A.CNT_6MONTH_NT)  /  DECODE(SUM(A.CNT_UPSO),  0,  1,  SUM(A.CNT_UPSO))  *  100,  1)  RATE_6MONTH  ,  SUM(A.CNT_ACCU)  CNT_ACCU  ,  TRUNC(SUM(A.CNT_ACCU)  /  DECODE(SUM(A.CNT_8MONTH),  0,  1,  SUM(A.CNT_8MONTH))  *  100,  1)  RATE_ACCU  ,  SUM(A.CNT_8MONTH)  CNT_8MONTH  ,  SUM(B.MON_AMT)  MON_AMT  ,  SUM(B.UPSO_CNT)  UPSO_CNT  ,  SUM(B.NONPY_AMT)  NONPY_AMT  ,  SUM(B.NONPY_CNT)  NONPY_CNT  ,  SUM(B.COL_UPSO_CNT)  COL_UPSO_CNT  ,  SUM(B.STAFF_AMT)  STAFF_AMT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TMOB_VISIT_PLAN  WHERE  VISIT_DAY  LIKE  :PRCON_YRMN  ||  '%'   \n";
        query +=" AND  JOB_GBN  =  'V'   \n";
        query +=" AND  (VISIT_YN  =  'Y'   \n";
        query +=" OR  VISIT_YN  IS  NULL)   \n";
        query +=" AND  BRAN_CD  =  NVL(A.BRAN_CD,  B.BRAN_CD))  AS  VISIT_CNT  ,   \n";
        query +=" (SELECT  COUNT(1)  FROM  GIBU.TMOB_VISIT_PLAN  WHERE  VISIT_DAY  LIKE  :PRCON_YRMN  ||  '%'   \n";
        query +=" AND  JOB_GBN  =  'P'   \n";
        query +=" AND  (VISIT_YN  =  'Y'   \n";
        query +=" OR  VISIT_YN  IS  NULL)   \n";
        query +=" AND  BRAN_CD  =  NVL(A.BRAN_CD,  B.BRAN_CD))  AS  PHONE_CNT  ,  TRUNC(SUM(A.CNT_AUTO)  /  SUM(A.CNT_UPSO)  *  100,  1)  AS  AUTO_RATE  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  FIDU.GET_GIBU_NM(A.BRAN_CD)  AS  BRAN_NM  ,  FIDU.GET_STAFF_NM(A.STAFF_CD)  AS  STAFF_NM  ,  A.STAFF_CD  ,  A.CNT_UPSO  ,  A.CNT_3MONTH  ,  TRUNC(A.CNT_3MONTH  /  DECODE(A.CNT_UPSO,  0,  1,  A.CNT_UPSO)  *  100,  1)  AS  RATE_3MONTH  ,  A.CNT_6MONTH  ,  A.CNT_6MONTH_NT  ,  TRUNC(A.CNT_6MONTH_NT  /  DECODE(A.CNT_UPSO,  0,  1,  A.CNT_UPSO)  *  100,  1)  AS  RATE_6MONTH  ,  A.CNT_ACCU  ,  TRUNC(A.CNT_ACCU  /  DECODE(A.CNT_8MONTH,  0,  1,  A.CNT_8MONTH)  *  100,  1)  AS  RATE_ACCU  ,  A.CNT_8MONTH  ,  B.PLAN_AMT  ,  A.CNT_AUTO  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  STAFF_CD  ,  COUNT(1)  AS  CNT_UPSO  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  3  THEN  1  ELSE  0  END))  AS  CNT_3MONTH  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  6  THEN  1  ELSE  0  END))  AS  CNT_6MONTH  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  6   \n";
        query +=" AND  INSTR(REMAK,  '고소중')  =  0  THEN  1  ELSE  0  END))  AS  CNT_6MONTH_NT  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  8  THEN  1  ELSE  0  END))  AS  CNT_8MONTH  ,  SUM((CASE  WHEN  DEMD_MMCNT  >=  8   \n";
        query +=" AND  INSTR(REMAK,  '고소중')  >  0  THEN  1  ELSE  0  END))  AS  CNT_ACCU  ,  SUM((CASE  WHEN  AUTO_YN  =  'OK'  THEN  1  ELSE  0  END))  AS  CNT_AUTO  FROM  GIBU.TBRA_NONPY_PRCON  A  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  BSTYP_CD  IN  ('J','f','o','y','l','k','m','n','p')   \n";
        query +=" AND  (  (:USER_ID  IS  NULL)   \n";
        query +=" OR  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID))))  GROUP  BY  BRAN_CD,  STAFF_CD  )  A  ,  (   \n";
        query +=" SELECT  STAFF_CD  ,  BRAN_CD  ,  DECODE(SUBSTR(:PRCON_YRMN,5,2),'01',MON1_AMT,'02',MON2_AMT,'03',MON3_AMT,'04',MON4_AMT,'05',MON5_AMT,'06',MON6_AMT,'07',MON7_AMT,'08',MON8_AMT,'09',MON9_AMT,'10',MON10_AMT,'11',MON11_AMT,'12',MON12_AMT)  PLAN_AMT  FROM  GIBU.TBRA_COLLECT_PLAN  WHERE  YYYY  =  SUBSTR(:PRCON_YRMN,1,4)   \n";
        query +=" AND  GBN  =  '2'  )  B  WHERE  A.STAFF_CD  =  B.STAFF_CD(+)   \n";
        query +=" AND  A.BRAN_CD  =  B.BRAN_CD(+)  )  A  FULL  OUTER  JOIN  (   \n";
        query +=" SELECT  PRCON_YRMN  ,  BRAN_CD  ,  BRAN_NM  ,  STAFF_CD  ,  SUM(MON_AMT)  AS  MON_AMT  ,  SUM(UPSO_CNT)  AS  UPSO_CNT  ,  SUM(NONPY_AMT)  AS  NONPY_AMT  ,  SUM(NONPY_CNT)  AS  NONPY_CNT  ,  SUM(COL_AMT)  AS  COL_AMT  ,  SUM(COL_UPSO_CNT)  AS  COL_UPSO_CNT  ,  STAFF_NM  ,  SUM(STAFF_AMT)  AS  STAFF_AMT  FROM  GIBU.TBRA_STAFF_CLCT_PRCON  WHERE  PRCON_YRMN  =  :PRCON_YRMN   \n";
        query +=" AND  SEQ  =  '1'   \n";
        query +=" AND  (  (:USER_ID  IS  NULL)   \n";
        query +=" OR  (BRAN_CD  IN   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  DEPT_CD  IN   \n";
        query +=" (SELECT  DEPT_CD  FROM  GIBU.TBRA_BILL_AUTH  WHERE  GBN  =  'B'   \n";
        query +=" AND  UDTKPRES_ID  =  :USER_ID))))   \n";
        query +=" AND  YDN_GBN  =  '1'  GROUP  BY  PRCON_YRMN,  SEQ,  BRAN_CD,  STAFF_CD,  STAFF_NM,  BRAN_NM  )  B  ON  A.STAFF_CD  =  B.STAFF_CD   \n";
        query +=" AND  A.BRAN_CD  =  B.BRAN_CD  GROUP  BY  NVL(A.BRAN_CD,  B.BRAN_CD),  NVL(A.BRAN_NM,  B.BRAN_NM)  ORDER  BY  1,  3 ";
        sobj.setSql(query);
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //현황 년월
        return sobj;
    }
    //##**$$plan_vs_col_select
    //##**$$staff_plan_search
    /*
    */
    public DOBJ CTLstaff_plan_search(DOBJ dobj)
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
            dobj  = CALLstaff_plan_search_SEL1(Conn, dobj);           //  예산 조회
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
    public DOBJ CTLstaff_plan_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLstaff_plan_search_SEL1(Conn, dobj);           //  예산 조회
        return dobj;
    }
    // 예산 조회
    public DOBJ CALLstaff_plan_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstaff_plan_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_plan_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YYYY = dobj.getRetObject("S").getRecord().get("YYYY");   //년도
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.YYYY , B.HAN_NM AS STAFF_NM , A.STAFF_CD	AS STAFF_CD , A.GBN , A.BRAN_CD , C.DEPT_NM AS GIBU_NM , A.MON1_AMT , A.MON2_AMT , A.MON3_AMT , A.MON4_AMT , A.MON5_AMT , A.MON6_AMT , A.MON7_AMT , A.MON8_AMT , A.MON9_AMT , A.MON10_AMT , A.MON11_AMT , A.MON12_AMT , A.TOTAL_AMT  ";
        query +=" FROM GIBU.TBRA_COLLECT_PLAN A , INSA.TINS_MST01 B , (  ";
        query +=" SELECT 'AL' AS GIBU , '지부사업국' AS DEPT_NM , '106010000' AS DEPT_CD  ";
        query +=" FROM DUAL  ";
        query +=" UNION ALL  ";
        query +=" SELECT GIBU, DEPT_NM, DEPT_CD  ";
        query +=" FROM INSA.TCPM_DEPT  ";
        query +=" WHERE GIBU IS NOT NULL  ";
        query +=" AND USE_YN = 'Y'  ";
        query +=" AND GIBU <> 'AL' ) C  ";
        query +=" WHERE A.YYYY = :YYYY  ";
        if( !BRAN_CD.equals("") )
        {
            query +=" AND A.BRAN_CD = DECODE(:BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD)  ";
        }
        query +=" AND A.STAFF_CD = B.STAFF_NUM  ";
        query +=" AND A.BRAN_CD = C.GIBU  ";
        query +=" ORDER BY A.BRAN_CD, A.STAFF_CD, A.GBN  ";
        sobj.setSql(query);
        sobj.setString("YYYY", YYYY);               //년도
        if(!BRAN_CD.equals(""))
        {
            sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        }
        return sobj;
    }
    //##**$$staff_plan_search
    //##**$$staff_plan_save
    /*
    */
    public DOBJ CTLstaff_plan_save(DOBJ dobj)
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
            dobj  = CALLstaff_plan_save_MIUD1(Conn, dobj);           //  분기
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
    public DOBJ CTLstaff_plan_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLstaff_plan_save_MIUD1(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLstaff_plan_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLstaff_plan_save_INS5(Conn, dobj);           //  신규
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLstaff_plan_save_UPD6(Conn, dobj);           //  수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLstaff_plan_save_DEL7(Conn, dobj);           //  삭제
            }
        }
        return dobj;
    }
    // 삭제
    public DOBJ CALLstaff_plan_save_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLstaff_plan_save_DEL7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstaff_plan_save_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   YYYY = dvobj.getRecord().get("YYYY");   //년도
        String   GBN = dvobj.getRecord().get("GBN");   //구분
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_COLLECT_PLAN  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and GBN=:GBN  \n";
        query +=" and YYYY=:YYYY  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("YYYY", YYYY);               //년도
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    // 신규
    public DOBJ CALLstaff_plan_save_INS5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLstaff_plan_save_INS5(dobj, dvobj);
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
    private SQLObject SQLstaff_plan_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   MON7_AMT = dvobj.getRecord().get("MON7_AMT");   //7월
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   YYYY = dvobj.getRecord().get("YYYY");   //년도
        String   MON11_AMT = dvobj.getRecord().get("MON11_AMT");   //11월
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   MON10_AMT = dvobj.getRecord().get("MON10_AMT");   //10월
        String   MON3_AMT = dvobj.getRecord().get("MON3_AMT");   //3월
        String   MON1_AMT = dvobj.getRecord().get("MON1_AMT");   //1월
        String   MON8_AMT = dvobj.getRecord().get("MON8_AMT");   //8월
        String   MON6_AMT = dvobj.getRecord().get("MON6_AMT");   //6월
        String   MON9_AMT = dvobj.getRecord().get("MON9_AMT");   //9월
        double   TOTAL_AMT = dvobj.getRecord().getDouble("TOTAL_AMT");   //합계 액
        String   MON2_AMT = dvobj.getRecord().get("MON2_AMT");   //2월
        String   MON12_AMT = dvobj.getRecord().get("MON12_AMT");   //12월
        String   GBN = dvobj.getRecord().get("GBN");   //구분
        String   MON4_AMT = dvobj.getRecord().get("MON4_AMT");   //4월
        String   MON5_AMT = dvobj.getRecord().get("MON5_AMT");   //5월
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_COLLECT_PLAN (MON5_AMT, INSPRES_ID, MON4_AMT, GBN, MON12_AMT, MON2_AMT, TOTAL_AMT, MON9_AMT, MON6_AMT, MON8_AMT, MON1_AMT, MON3_AMT, INS_DATE, MON10_AMT, STAFF_CD, MON11_AMT, YYYY, BRAN_CD, MON7_AMT)  \n";
        query +=" values(:MON5_AMT , :INSPRES_ID , :MON4_AMT , :GBN , :MON12_AMT , :MON2_AMT , :TOTAL_AMT , :MON9_AMT , :MON6_AMT , :MON8_AMT , :MON1_AMT , :MON3_AMT , SYSDATE, :MON10_AMT , :STAFF_CD , :MON11_AMT , :YYYY , :BRAN_CD , :MON7_AMT )";
        sobj.setSql(query);
        sobj.setString("MON7_AMT", MON7_AMT);               //7월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("YYYY", YYYY);               //년도
        sobj.setString("MON11_AMT", MON11_AMT);               //11월
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("MON10_AMT", MON10_AMT);               //10월
        sobj.setString("MON3_AMT", MON3_AMT);               //3월
        sobj.setString("MON1_AMT", MON1_AMT);               //1월
        sobj.setString("MON8_AMT", MON8_AMT);               //8월
        sobj.setString("MON6_AMT", MON6_AMT);               //6월
        sobj.setString("MON9_AMT", MON9_AMT);               //9월
        sobj.setDouble("TOTAL_AMT", TOTAL_AMT);               //합계 액
        sobj.setString("MON2_AMT", MON2_AMT);               //2월
        sobj.setString("MON12_AMT", MON12_AMT);               //12월
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("MON4_AMT", MON4_AMT);               //4월
        sobj.setString("MON5_AMT", MON5_AMT);               //5월
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 수정
    public DOBJ CALLstaff_plan_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLstaff_plan_save_UPD6(dobj, dvobj);
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
    private SQLObject SQLstaff_plan_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   MON7_AMT = dvobj.getRecord().get("MON7_AMT");   //7월
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   YYYY = dvobj.getRecord().get("YYYY");   //년도
        String   MON11_AMT = dvobj.getRecord().get("MON11_AMT");   //11월
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   MON10_AMT = dvobj.getRecord().get("MON10_AMT");   //10월
        String   MON3_AMT = dvobj.getRecord().get("MON3_AMT");   //3월
        String   MON1_AMT = dvobj.getRecord().get("MON1_AMT");   //1월
        String   MON8_AMT = dvobj.getRecord().get("MON8_AMT");   //8월
        String   MON6_AMT = dvobj.getRecord().get("MON6_AMT");   //6월
        String   MON9_AMT = dvobj.getRecord().get("MON9_AMT");   //9월
        double   TOTAL_AMT = dvobj.getRecord().getDouble("TOTAL_AMT");   //합계 액
        String   MON2_AMT = dvobj.getRecord().get("MON2_AMT");   //2월
        String   MON12_AMT = dvobj.getRecord().get("MON12_AMT");   //12월
        String   GBN = dvobj.getRecord().get("GBN");   //구분
        String   MON4_AMT = dvobj.getRecord().get("MON4_AMT");   //4월
        String   MON5_AMT = dvobj.getRecord().get("MON5_AMT");   //5월
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_COLLECT_PLAN  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MON5_AMT=:MON5_AMT , MON4_AMT=:MON4_AMT , MON12_AMT=:MON12_AMT , MON2_AMT=:MON2_AMT , TOTAL_AMT=:TOTAL_AMT , MON9_AMT=:MON9_AMT , MON6_AMT=:MON6_AMT , MON8_AMT=:MON8_AMT , MON1_AMT=:MON1_AMT , MON3_AMT=:MON3_AMT , MON10_AMT=:MON10_AMT , MON11_AMT=:MON11_AMT , MOD_DATE=SYSDATE , MON7_AMT=:MON7_AMT  \n";
        query +=" where GBN=:GBN  \n";
        query +=" and STAFF_CD=:STAFF_CD  \n";
        query +=" and YYYY=:YYYY  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("MON7_AMT", MON7_AMT);               //7월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("YYYY", YYYY);               //년도
        sobj.setString("MON11_AMT", MON11_AMT);               //11월
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("MON10_AMT", MON10_AMT);               //10월
        sobj.setString("MON3_AMT", MON3_AMT);               //3월
        sobj.setString("MON1_AMT", MON1_AMT);               //1월
        sobj.setString("MON8_AMT", MON8_AMT);               //8월
        sobj.setString("MON6_AMT", MON6_AMT);               //6월
        sobj.setString("MON9_AMT", MON9_AMT);               //9월
        sobj.setDouble("TOTAL_AMT", TOTAL_AMT);               //합계 액
        sobj.setString("MON2_AMT", MON2_AMT);               //2월
        sobj.setString("MON12_AMT", MON12_AMT);               //12월
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("MON4_AMT", MON4_AMT);               //4월
        sobj.setString("MON5_AMT", MON5_AMT);               //5월
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    //##**$$staff_plan_save
    //##**$$gibu_staff_reg
    /*
    */
    public DOBJ CTLgibu_staff_reg(DOBJ dobj)
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
            dobj  = CALLgibu_staff_reg_XIUD1(Conn, dobj);           //  년도별 사원생성
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
    public DOBJ CTLgibu_staff_reg( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLgibu_staff_reg_XIUD1(Conn, dobj);           //  년도별 사원생성
        return dobj;
    }
    // 년도별 사원생성
    public DOBJ CALLgibu_staff_reg_XIUD1(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLgibu_staff_reg_XIUD1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        rvobj.Println("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgibu_staff_reg_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   YYYY = dobj.getRetObject("S").getRecord().get("YYYY");   //년도
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_COLLECT_PLAN (YYYY, BRAN_CD, STAFF_CD, GBN, INSPRES_ID, INS_DATE) SELECT :YYYY , A.GIBU , B.STAFF_NUM , '1' AS GBN , :INSPRES_ID , SYSDATE FROM INSA.TCPM_DEPT A , INSA.TINS_MST01 B WHERE A.PAR_DEPT_CD LIKE '121020000' AND A.GIBU IS NOT NULL AND A.LVL = '4' AND A.DEPT_CD = B.DEPT_CD AND B.RETIRE_DAY IS NULL AND B.JOB_CD <> '140' AND A.GIBU = DECODE(NVL(:BRAN_CD,'AL'), 'AL', A.GIBU, :BRAN_CD) UNION ALL SELECT :YYYY , A.GIBU , B.STAFF_NUM , '2' AS GBN , :INSPRES_ID , SYSDATE FROM INSA.TCPM_DEPT A , INSA.TINS_MST01 B WHERE A.PAR_DEPT_CD LIKE '121020000' AND A.GIBU IS NOT NULL AND A.LVL = '4' AND A.DEPT_CD = B.DEPT_CD AND B.RETIRE_DAY IS NULL AND B.JOB_CD <> '140' AND A.GIBU = DECODE(NVL(:BRAN_CD,'AL'), 'AL', A.GIBU, :BRAN_CD)";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("YYYY", YYYY);               //년도
        return sobj;
    }
    //##**$$gibu_staff_reg
    //##**$$end
}
