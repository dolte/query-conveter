
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_r09
{
    public bra10_r09()
    {
    }
    //##**$$bra10_r09_select
    /* * 프로그램명 : bra10_r09
    * 작성자 : 999999
    * 작성일 : 2009/10/20
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbra10_r09_select(DOBJ dobj)
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
            dobj  = CALLbra10_r09_select_SEL1(Conn, dobj);           //  전국지부 월별 징수금액
            dobj  = CALLbra10_r09_select_SEL3(Conn, dobj);           //  전국지부 월별 징수 성과금액
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
    public DOBJ CTLbra10_r09_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra10_r09_select_SEL1(Conn, dobj);           //  전국지부 월별 징수금액
        dobj  = CALLbra10_r09_select_SEL3(Conn, dobj);           //  전국지부 월별 징수 성과금액
        return dobj;
    }
    // 전국지부 월별 징수금액
    public DOBJ CALLbra10_r09_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra10_r09_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r09_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //적용 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :APPL_YRMN  AS  APPL_YRMN,  B.BRAN_CD,  C.DEPT_NM  AS  BRAN_NM,  A.MON_TARGET_AMT,  NVL(D.LEVY_AMT,B.LEVY_AMT)  AS  LEVY_AMT,  NVL(D.CLAIM_AMT,B.CLAIM_AMT)  AS  CLAIM_AMT,  NVL(D.PURE_LEVY_AMT,B.PURE_LEVY_AMT)  AS  PURE_LEVY_AMT,  NVL(D.VARIATION_AMT,B.VARIATION_AMT)  AS  VARIATION_AMT,  NVL(D.ATTAIN_RATE,B.ATTAIN_RATE)  AS  ATTAIN_RATE,  NVL(D.TOT_UPSO_CNT,B.TOT_UPSO_CNT)  AS  TOT_UPSO_CNT,  B.LEVY_UPSO_CNT,  B.EXT_AMT,   \n";
        query +=" (SELECT  DECODE(COUNT(*),0,'I','U')  FROM  GIBU.TBRA_BRAN_BONUS_BRE  WHERE  APPL_YRMN  =  :APPL_YRMN)  AS  CRUD  FROM  GIBU.TBRA_LEVY_TARGET  A,   \n";
        query +=" (SELECT  :APPL_YRMN  APPL_YRMN  ,  XA.BRAN_CD  ,  NVL(XA.MON_TARGET_AMT,  0)  MON_TARGET_AMT  ,  NVL(XB.REPT_AMT,  0)  LEVY_AMT  ,  NVL(XC.REPT_AMT,  0)  CLAIM_AMT  ,  NVL(XB.REPT_AMT,  0)  -  NVL(XC.REPT_AMT,  0)  PURE_LEVY_AMT  ,  NVL(XB.REPT_AMT,  0)  -  NVL(XA.MON_TARGET_AMT,  0)  VARIATION_AMT  ,  TRUNC(NVL(XB.REPT_AMT,  0)  /  NVL(XA.MON_TARGET_AMT,  1)  *  100,  5)  ATTAIN_RATE  ,  NVL(XB.REPT_CNT,  0)  TOT_UPSO_CNT  ,  NVL(XB.REPT_CNT,  0)  -  NVL(XC.REPT_CNT,  0)  LEVY_UPSO_CNT  ,  NVL(XB.REPT_AMT,  0)  -  NVL(XC.REPT_AMT,  0)  -  NVL(XA.MON_TARGET_AMT,  0)  EXT_AMT  FROM  (   \n";
        query +=" SELECT  APPL_YRMN  ,  BRAN_CD  ,  SUM(MON_TARGET_AMT)  MON_TARGET_AMT  FROM  (   \n";
        query +=" SELECT  APPL_YRMN  ,  CASE  WHEN  BRAN_CD  IN  ('H',  'I')  THEN  'I'  WHEN  BRAN_CD  IN  ('J',  'K',  'O')  THEN  'K'  WHEN  BRAN_CD  IN  ('B',  'C')  THEN  'B'  ELSE  BRAN_CD  END  BRAN_CD  ,  MON_TARGET_AMT  FROM  GIBU.TBRA_LEVY_TARGET  WHERE  APPL_YRMN  =  :APPL_YRMN  )  GROUP  BY  APPL_YRMN,  BRAN_CD  )  XA  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  SUM(REPT_AMT)  REPT_AMT  ,  COUNT(DISTINCT  UPSO_CD)  REPT_CNT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  NVL(REPT_AMT,  0)  -  NVL(COMIS,  0)  REPT_AMT  ,  CASE  WHEN  BRAN_CD  IN  ('H',  'I')  THEN  'I'  WHEN  BRAN_CD  IN  ('J',  'K',  'O')  THEN  'K'  WHEN  BRAN_CD  IN  ('B',  'C')  THEN  'B'  ELSE  BRAN_CD  END  BRAN_CD  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  BETWEEN  :APPL_YRMN  ||  '01'   \n";
        query +=" AND  :APPL_YRMN  ||  '31'   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  A.UPSO_CD  ,  NVL(A.DISTR_AMT,  0)  DISTR_AMT  ,  B.BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.REPT_DAY  BETWEEN  :APPL_YRMN  ||  '01'   \n";
        query +=" AND  :APPL_YRMN  ||  '31'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  )  GROUP  BY  BRAN_CD  )  XB  ,  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  SUM(B.REPT_AMT)  REPT_AMT  ,  COUNT(B.UPSO_CD)  REPT_CNT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  CASE  WHEN  BRAN_CD  IN  ('H',  'I')  THEN  'I'  WHEN  BRAN_CD  IN  ('J',  'K',  'O')  THEN  'K'  WHEN  BRAN_CD  IN  ('B',  'C')  THEN  'B'  ELSE  BRAN_CD  END  BRAN_CD  FROM  GIBU.TBRA_UPSO  WHERE  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <  :APPL_YRMN||'31'  )  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  NVL(REPT_AMT,  0)  -  NVL(COMIS,  0)  REPT_AMT  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  BETWEEN  :APPL_YRMN  ||  '01'   \n";
        query +=" AND  :APPL_YRMN  ||  '31'   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  UPSO_CD  ,  NVL(DISTR_AMT,  0)  DISTR_AMT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  REPT_DAY  BETWEEN  :APPL_YRMN  ||  '01'   \n";
        query +=" AND  :APPL_YRMN  ||  '31'  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  :APPL_YRMN  BETWEEN  START_YRMN   \n";
        query +=" AND  END_YRMN  )  GROUP  BY  A.BRAN_CD  )  XC  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  COUNT(UPSO_CD)  UPSO_CNT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  CASE  WHEN  BRAN_CD  IN  ('H',  'I')  THEN  'I'  WHEN  BRAN_CD  IN  ('J',  'K',  'O')  THEN  'K'  WHEN  BRAN_CD  IN  ('B',  'C')  THEN  'B'  ELSE  BRAN_CD  END  BRAN_CD  FROM  GIBU.TBRA_UPSO  WHERE  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <  :APPL_YRMN  ||  '31'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,  1,  6),  '  ')  >=  :APPL_YRMN)  )  GROUP  BY  BRAN_CD  ORDER  BY  BRAN_CD  )  XD  WHERE  XA.APPL_YRMN  =  :APPL_YRMN   \n";
        query +=" AND  XB.BRAN_CD(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XC.BRAN_CD(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XD.BRAN_CD(+)  =  XA.BRAN_CD  )  B,   \n";
        query +=" (SELECT  GIBU,  DEPT_NM,  DEPT_CD  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  LIKE  '1060%'   \n";
        query +=" AND  GIBU  IS  NOT  NULL  )  C,  GIBU.TBRA_BRAN_BONUS_BRE  D  WHERE  A.APPL_YRMN  =  B.APPL_YRMN   \n";
        query +=" AND  A.BRAN_CD  =  B.BRAN_CD   \n";
        query +=" AND  A.APPL_YRMN  =  D.APPL_YRMN(+)   \n";
        query +=" AND  A.BRAN_CD  =  D.BRAN_CD(+)   \n";
        query +=" AND  A.APPL_YRMN  =  :APPL_YRMN   \n";
        query +=" AND  B.BRAN_CD  =  C.GIBU  ORDER  BY  B.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        return sobj;
    }
    // 전국지부 월별 징수 성과금액
    public DOBJ CALLbra10_r09_select_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra10_r09_select_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r09_select_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");        //적용 년월
        String   APPL_YRMN_1 = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //적용 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MON_TARGET_AMT,  B.LEVY_AMT,  B.CLAIM_AMT,  B.PURE_LEVY_AMT,  B.VARIATION_AMT,  B.ATTAIN_RATE,  B.TOT_UPSO_CNT,  B.APPL_YRMN,  B.BRAN_CD,  C.DEPT_NM  AS  BRAN_NM,  B.LEVY_RATE,  B.LEVY_BONUS,  B.EXT_RATE,  B.EXT_BONUS,  B.EXT_AMT,  B.UPSO_CNT_RATE,  B.UPSO_CNT_BONUS  FROM  GIBU.TBRA_LEVY_TARGET  A,  GIBU.TBRA_BRAN_BONUS_BRE  B,   \n";
        query +=" (SELECT  GIBU,  DEPT_NM,  DEPT_CD  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  LIKE  '1060%'   \n";
        query +=" AND  GIBU  IS  NOT  NULL  )  C  WHERE  A.APPL_YRMN  =  B.APPL_YRMN   \n";
        query +=" AND  A.BRAN_CD  =  B.BRAN_CD   \n";
        query +=" AND  A.APPL_YRMN  =  SUBSTR(:APPL_YRMN_1,0,6)   \n";
        query +=" AND  B.BRAN_CD  =  C.GIBU ";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN_1", APPL_YRMN_1);               //적용 년월
        return sobj;
    }
    //##**$$bra10_r09_select
    //##**$$bra10_r09_save
    /* * 프로그램명 : bra10_r09
    * 작성자 : 999999
    * 작성일 : 2009/10/20
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbra10_r09_save(DOBJ dobj)
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
            dobj  = CALLbra10_r09_save_MPD1(Conn, dobj);           //  월별전국지부징수금액저장
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
    public DOBJ CTLbra10_r09_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra10_r09_save_MPD1(Conn, dobj);           //  월별전국지부징수금액저장
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 월별전국지부징수금액저장
    public DOBJ CALLbra10_r09_save_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("CRUD").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra10_r09_save_INS5(Conn, dobj);           //  월별전국지부징수금액입력
            }
            else if( dvobj.getRecord().get("CRUD").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra10_r09_save_UPD6(Conn, dobj);           //  월별전국지부징수금액수정
            }
        }
        return dobj;
    }
    // 월별전국지부징수금액입력
    public DOBJ CALLbra10_r09_save_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra10_r09_save_INS5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        rvobj.Println("INS5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r09_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double ATTAIN_RATE = 0;        //달성율
        String INS_DATE ="";        //등록 일시
        double PURE_LEVY_AMT = 0;        //순 징수 금액
        double VARIATION_AMT = 0;        //증감 금액
        String   VARIATION_AMT_2 = dobj.getRetObject("R").getRecord().get("MON_TARGET_AMT");   //증감 금액
        String   VARIATION_AMT_1 = dobj.getRetObject("R").getRecord().get("LEVY_AMT");   //증감 금액
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        double   CLAIM_AMT = dvobj.getRecord().getDouble("CLAIM_AMT");   //채권 금액
        String   PURE_LEVY_AMT_2 = dobj.getRetObject("R").getRecord().get("CLAIM_AMT");   //순 징수 금액
        String   PURE_LEVY_AMT_1 = dobj.getRetObject("R").getRecord().get("LEVY_AMT");   //순 징수 금액
        String   APPL_YRMN = dvobj.getRecord().get("APPL_YRMN");   //적용 년월
        double   LEVY_AMT = dvobj.getRecord().getDouble("LEVY_AMT");   //징수 금액
        double   TOT_UPSO_CNT = dvobj.getRecord().getDouble("TOT_UPSO_CNT");   //총 업소 수
        String   ATTAIN_RATE_2 = dobj.getRetObject("R").getRecord().get("MON_TARGET_AMT");   //달성율
        String   ATTAIN_RATE_1 = dobj.getRetObject("R").getRecord().get("LEVY_AMT");   //달성율
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        double   LEVY_UPSO_CNT = dobj.getRetObject("R").getRecord().getDouble("TOT_UPSO_CNT");   //징수 업소 수
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BRAN_BONUS_BRE (INS_DATE, ATTAIN_RATE, INSPRES_ID, LEVY_UPSO_CNT, TOT_UPSO_CNT, LEVY_AMT, APPL_YRMN, PURE_LEVY_AMT, CLAIM_AMT, BRAN_CD, VARIATION_AMT)  \n";
        query +=" values(SYSDATE, (TRUNC(:ATTAIN_RATE_1 / :ATTAIN_RATE_2 * 100,5)), :INSPRES_ID , :LEVY_UPSO_CNT , :TOT_UPSO_CNT , :LEVY_AMT , :APPL_YRMN , (:PURE_LEVY_AMT_1 - :PURE_LEVY_AMT_2), :CLAIM_AMT , :BRAN_CD , (:VARIATION_AMT_1 - :VARIATION_AMT_2))";
        sobj.setSql(query);
        sobj.setString("VARIATION_AMT_2", VARIATION_AMT_2);               //증감 금액
        sobj.setString("VARIATION_AMT_1", VARIATION_AMT_1);               //증감 금액
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setDouble("CLAIM_AMT", CLAIM_AMT);               //채권 금액
        sobj.setString("PURE_LEVY_AMT_2", PURE_LEVY_AMT_2);               //순 징수 금액
        sobj.setString("PURE_LEVY_AMT_1", PURE_LEVY_AMT_1);               //순 징수 금액
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        sobj.setDouble("LEVY_AMT", LEVY_AMT);               //징수 금액
        sobj.setDouble("TOT_UPSO_CNT", TOT_UPSO_CNT);               //총 업소 수
        sobj.setString("ATTAIN_RATE_2", ATTAIN_RATE_2);               //달성율
        sobj.setString("ATTAIN_RATE_1", ATTAIN_RATE_1);               //달성율
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setDouble("LEVY_UPSO_CNT", LEVY_UPSO_CNT);               //징수 업소 수
        return sobj;
    }
    // 월별전국지부징수금액수정
    public DOBJ CALLbra10_r09_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLbra10_r09_save_UPD6(dobj, dvobj);
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
    private SQLObject SQLbra10_r09_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double ATTAIN_RATE = 0;        //달성율
        double PURE_LEVY_AMT = 0;        //순 징수 금액
        double VARIATION_AMT = 0;        //증감 금액
        String   VARIATION_AMT_2 = dobj.getRetObject("R").getRecord().get("MON_TARGET_AMT");   //증감 금액
        String   VARIATION_AMT_1 = dobj.getRetObject("R").getRecord().get("LEVY_AMT");   //증감 금액
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        double   CLAIM_AMT = dvobj.getRecord().getDouble("CLAIM_AMT");   //채권 금액
        String   PURE_LEVY_AMT_2 = dobj.getRetObject("R").getRecord().get("CLAIM_AMT");   //순 징수 금액
        String   PURE_LEVY_AMT_1 = dobj.getRetObject("R").getRecord().get("LEVY_AMT");   //순 징수 금액
        String   APPL_YRMN = dvobj.getRecord().get("APPL_YRMN");   //적용 년월
        double   LEVY_AMT = dvobj.getRecord().getDouble("LEVY_AMT");   //징수 금액
        double   TOT_UPSO_CNT = dvobj.getRecord().getDouble("TOT_UPSO_CNT");   //총 업소 수
        String   ATTAIN_RATE_2 = dobj.getRetObject("R").getRecord().get("MON_TARGET_AMT");   //달성율
        String   ATTAIN_RATE_1 = dobj.getRetObject("R").getRecord().get("LEVY_AMT");   //달성율
        double   LEVY_UPSO_CNT = dobj.getRetObject("R").getRecord().getDouble("TOT_UPSO_CNT");   //징수 업소 수
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BRAN_BONUS_BRE  \n";
        query +=" set ATTAIN_RATE=(TRUNC(:ATTAIN_RATE_1 / :ATTAIN_RATE_2 * 100,5)) , LEVY_UPSO_CNT=:LEVY_UPSO_CNT , TOT_UPSO_CNT=:TOT_UPSO_CNT , LEVY_AMT=:LEVY_AMT , PURE_LEVY_AMT=(:PURE_LEVY_AMT_1 - :PURE_LEVY_AMT_2) , CLAIM_AMT=:CLAIM_AMT , VARIATION_AMT=(:VARIATION_AMT_1 - :VARIATION_AMT_2)  \n";
        query +=" where APPL_YRMN=:APPL_YRMN  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("VARIATION_AMT_2", VARIATION_AMT_2);               //증감 금액
        sobj.setString("VARIATION_AMT_1", VARIATION_AMT_1);               //증감 금액
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setDouble("CLAIM_AMT", CLAIM_AMT);               //채권 금액
        sobj.setString("PURE_LEVY_AMT_2", PURE_LEVY_AMT_2);               //순 징수 금액
        sobj.setString("PURE_LEVY_AMT_1", PURE_LEVY_AMT_1);               //순 징수 금액
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        sobj.setDouble("LEVY_AMT", LEVY_AMT);               //징수 금액
        sobj.setDouble("TOT_UPSO_CNT", TOT_UPSO_CNT);               //총 업소 수
        sobj.setString("ATTAIN_RATE_2", ATTAIN_RATE_2);               //달성율
        sobj.setString("ATTAIN_RATE_1", ATTAIN_RATE_1);               //달성율
        sobj.setDouble("LEVY_UPSO_CNT", LEVY_UPSO_CNT);               //징수 업소 수
        return sobj;
    }
    //##**$$bra10_r09_save
    //##**$$end
}
