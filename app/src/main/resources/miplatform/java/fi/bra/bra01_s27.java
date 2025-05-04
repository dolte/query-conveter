
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s27
{
    public bra01_s27()
    {
    }
    //##**$$sel_visit_plan
    /*
    */
    public DOBJ CTLsel_visit_plan(DOBJ dobj)
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
            dobj  = CALLsel_visit_plan_SEL1(Conn, dobj);           //  담당자별방문예정내역
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
    public DOBJ CTLsel_visit_plan( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_visit_plan_SEL1(Conn, dobj);           //  담당자별방문예정내역
        return dobj;
    }
    // 담당자별방문예정내역
    public DOBJ CALLsel_visit_plan_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_visit_plan_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_visit_plan_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   VISIT_DAY = dobj.getRetObject("S").getRecord().get("VISIT_DAY");   //방문 일자
        String   JOB_GBN = dobj.getRetObject("S").getRecord().get("JOB_GBN");   //업무 구분
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.STAFF_CD  ,  FIDU.GET_STAFF_NM(A.STAFF_CD)  AS  STAFF_NM  ,  A.BRAN_CD  ,  FIDU.GET_BSCON_NM(A.BRAN_CD)  AS  BRAN_NM  ,  A.VISIT_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  B.UPSO_NEW_ADDR1  ||'  '  ||  B.UPSO_NEW_ADDR2  AS  UPSO_ADDR  ,  NVL(A.VISIT_YN,  'Y')  AS  VISIT_YN  ,  A.LAT  ,  A.LNG  ,  A.INSPRES_ID  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  AS  INSPRES_NM  ,  A.INS_DATE  ,  DECODE(A.VISIT_YN,  NULL,  'N',  'Y')  AS  PLAN_YN  ,  A.JOB_GBN  ,  GIBU.FT_GET_UPSO_MMCNT(A.UPSO_CD,  SUBSTR(:VISIT_DAY,  1,  6))  AS  DEMD_MMCNT  FROM  GIBU.TMOB_VISIT_PLAN  A  ,  GIBU.TBRA_UPSO  B  WHERE  1=1   \n";
        query +=" AND  A.BRAN_CD  =  B.BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)   \n";
        query +=" AND  A.JOB_GBN  =  NVL(:JOB_GBN,  A.JOB_GBN)  UNION  ALL   \n";
        query +=" SELECT  A.INSPRES_ID  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  AS  STAFF_NM  ,  B.BRAN_CD  AS  BRAN_CD  ,  GIBU.GET_BRAN_NM(  B.BRAN_CD)  AS  BRAN_NM  ,  A.VISIT_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  B.UPSO_NEW_ADDR1  ||'  '  ||  B.UPSO_NEW_ADDR2  AS  UPSO_ADDR  ,  'Y'  ,  ''  ,  ''  ,  A.INSPRES_ID  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  AS  INSPRES_NM  ,  A.INS_DATE  ,  '코스모스등록'  AS  PLAN_YN  ,  (CASE  WHEN  A.JOB_GBN  IN  ('V',  'T')  THEN  'V'  ELSE  A.JOB_GBN  END)  AS  JOB_GBN  ,  GIBU.FT_GET_UPSO_MMCNT(A.UPSO_CD,  SUBSTR(:VISIT_DAY,  1,  6))  AS  DEMD_MMCNT  FROM  GIBU.TBRA_UPSO_VISIT  A,  GIBU.TBRA_UPSO  B  WHERE  1=1   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND   \n";
        query +=" (SELECT  BRAN_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD)  =  DECODE(:BRAN_CD,  'AL',   \n";
        query +=" (SELECT  BRAN_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD),  :BRAN_CD)   \n";
        query +=" AND  A.INSPRES_ID  =  NVL(:STAFF_CD,  A.INSPRES_ID)   \n";
        query +=" AND  LENGTH(A.INSPRES_ID)  =  7   \n";
        query +=" AND   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TMOB_VISIT_PLAN  WHERE  STAFF_CD  =  A.INSPRES_ID   \n";
        query +=" AND  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY)  <  1   \n";
        query +=" AND  (  (A.JOB_GBN  IN  ('V',  'T')   \n";
        query +=" AND  NVL(:JOB_GBN,  A.JOB_GBN)  =  'V')   \n";
        query +=" OR  (A.JOB_GBN  =  'P'   \n";
        query +=" AND  NVL(:JOB_GBN,  A.JOB_GBN)  =  'P'))  ORDER  BY  INS_DATE  DESC ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$sel_visit_plan
    //##**$$mng_visit_plan
    /*
    */
    public DOBJ CTLmng_visit_plan(DOBJ dobj)
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
            dobj  = CALLmng_visit_plan_MIUD1(Conn, dobj);           //  분기
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
    public DOBJ CTLmng_visit_plan( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_visit_plan_MIUD1(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLmng_visit_plan_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S1");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_visit_plan_INS7(Conn, dobj);           //  방문예정등록
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_visit_plan_UPD6(Conn, dobj);           //  방문예정수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_visit_plan_DEL5(Conn, dobj);           //  방문예정삭제
            }
        }
        return dobj;
    }
    // 방문예정삭제
    public DOBJ CALLmng_visit_plan_DEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_visit_plan_DEL5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_visit_plan_DEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TMOB_VISIT_PLAN  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    // 방문예정등록
    public DOBJ CALLmng_visit_plan_INS7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_visit_plan_INS7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_visit_plan_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   VISIT_YN ="N";   //방문여부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TMOB_VISIT_PLAN (INS_DATE, INSPRES_ID, VISIT_YN, STAFF_CD, VISIT_DAY, JOB_GBN, UPSO_CD, BRAN_CD)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :VISIT_YN , :STAFF_CD , :VISIT_DAY , :JOB_GBN , :UPSO_CD , :BRAN_CD )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("VISIT_YN", VISIT_YN);               //방문여부
        return sobj;
    }
    // 방문예정수정
    public DOBJ CALLmng_visit_plan_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLmng_visit_plan_UPD6(dobj, dvobj);
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
    private SQLObject SQLmng_visit_plan_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TMOB_VISIT_PLAN  \n";
        query +=" set JOB_GBN=:JOB_GBN  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    //##**$$mng_visit_plan
    //##**$$kosg_upso_update
    /*
    */
    public DOBJ CTLkosg_upso_update(DOBJ dobj)
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
            dobj  = CALLkosg_upso_update_XIUD1(Conn, dobj);           //  기존업소목록 삭제
            dobj  = CALLkosg_upso_update_XIUD2(Conn, dobj);           //  업소목록 업데이트
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
    public DOBJ CTLkosg_upso_update( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLkosg_upso_update_XIUD1(Conn, dobj);           //  기존업소목록 삭제
        dobj  = CALLkosg_upso_update_XIUD2(Conn, dobj);           //  업소목록 업데이트
        return dobj;
    }
    // 기존업소목록 삭제
    public DOBJ CALLkosg_upso_update_XIUD1(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLkosg_upso_update_XIUD1(dobj, dvobj);
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
    private SQLObject SQLkosg_upso_update_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TMOB_UPSO_LIST  \n";
        query +=" WHERE STAFF_CD = :STAFF_CD";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    // 업소목록 업데이트
    public DOBJ CALLkosg_upso_update_XIUD2(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLkosg_upso_update_XIUD2(dobj, dvobj);
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
    private SQLObject SQLkosg_upso_update_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TMOB_UPSO_LIST (DEMD_MMCNT, UPSO_CD, UPSO_NM, BIOWN_NUM, MNGEMSTR_NM, PERMMSTR_NM, MONPRNCFEE, CLSBS_YRMN, NEW_DAY, UPSO_ZIP, SIGUNGU, DONG, UPSO_ADDR, UPSO_NEW_ADDR1, UPSO_PHON, MNGEMSTR_HPNUM, TOT_USE_AMT, TOT_ADDT_AMT, TOT_AMT, GOSO_YN, BSCON, BRAN_CD, STAFF_CD, MOD_DATE) SELECT DEMD_MMCNT , UPSO_CD , UPSO_NM , BIOWN_NUM , MNGEMSTR_NM , PERMMSTR_NM , MONPRNCFEE , CLSBS_YRMN , NEW_DAY , UPSO_ZIP , SIGUNGU , DONG , NVL(TRIM(TEMP_ADDR1), UPSO_NEW_ADDR1) || DECODE(UPSO_NEW_ADDR2, '', ' ', ', ' || UPSO_NEW_ADDR2) || UPSO_REF_INFO AS UPSO_ADDR , NVL(TRIM(TEMP_ADDR1), UPSO_NEW_ADDR1) AS UPSO_NEW_ADDR1 , UPSO_PHON , MNGEMSTR_HPNUM , TOT_USE_AMT , TOT_ADDT_AMT , TOT_AMT , GOSO_YN , BSCON , BRAN_CD , STAFF_CD , SYSDATE FROM ( SELECT TO_NUMBER(CASE WHEN XA.DEMD_MMCNT = '0' THEN NVL(TO_CHAR(MONTHS_BETWEEN(TO_DATE(TO_CHAR(SYSDATE, 'YYYYMM'), 'YYYYMM'), TO_DATE((SELECT MAX(NOTE_YRMN) FROM GIBU.TBRA_NOTE WHERE UPSO_CD = XB.UPSO_CD), 'YYYYMM'))), 9999) ELSE XA.DEMD_MMCNT END) AS DEMD_MMCNT , XB.UPSO_CD , XB.UPSO_NM , XB.BIOWN_NUM , XB.MNGEMSTR_NM , XB.PERMMSTR_NM , XA.MONPRNCFEE , XB.CLSBS_YRMN , XB.NEW_DAY , XB.UPSO_NEW_ZIP AS UPSO_ZIP , (SELECT ATTE||' '||CITYCNTYDSRC FROM FIDU.TENV_POST WHERE BD_MNG_NUM = XB.UPSO_BD_MNG_NUM) AS SIGUNGU , (SELECT DECODE(COURT_NM, NULL, TOWNTWSHP, COURT_NM) FROM FIDU.TENV_POST WHERE BD_MNG_NUM = XB.UPSO_BD_MNG_NUM) AS DONG , XB.UPSO_NEW_ADDR2 , XB.UPSO_REF_INFO , XB.UPSO_NEW_ADDR1 , XB.UPSO_PHON , XB.MNGEMSTR_HPNUM , XA.TOT_USE_AMT , XA.TOT_ADDT_AMT , XA.TOT_USE_AMT + XA.TOT_ADDT_AMT AS TOT_AMT , XD.GOSO_YN AS GOSO_YN , DECODE((SELECT BSCON_CD FROM LOG.KDS_SHOPROOM WHERE UPSO_CD = XB.UPSO_CD AND CO_STATUS = '07001'), 'E0003', 'TJ', 'E0006', 'KY', '') AS BSCON , XB.BRAN_CD , XB.STAFF_CD , XE.ATTE || CHR(32) || XE.CITYCNTYDSRC || CHR(32) || XE.TOWNTWSHP || DECODE(XE.TOWNTWSHP, NULL, '', CHR(32)) || XE.JUSO_NM || CHR(32) || XE.BD_NO || DECODE(XE.BD_NO2, NULL, '', 0, '', '-' || XE.BD_NO2) AS TEMP_ADDR1 FROM ( SELECT UPSO_CD , GIBU.FT_SPLIT(DEMDS, ',', 0) AS START_YRMN , GIBU.FT_SPLIT(DEMDS, ',', 1) AS END_YRMN , GIBU.FT_SPLIT(DEMDS, ',', 2) AS BSTYP_CD , GIBU.FT_SPLIT(DEMDS, ',', 3) AS UPSO_GRAD , GIBU.FT_SPLIT(DEMDS, ',', 4) AS MONPRNCFEE , GIBU.FT_SPLIT(DEMDS, ',', 5) AS MONPRNCFEE2 , GIBU.FT_SPLIT(DEMDS, ',', 6) AS DEMD_GBN , GIBU.FT_SPLIT(DEMDS, ',', 7) AS DEMD_MMCNT , GIBU.FT_SPLIT(DEMDS, ',', 8) AS TOT_USE_AMT , GIBU.FT_SPLIT(DEMDS, ',', 9) + GIBU.FT_SPLIT(DEMDS, ',', 10) AS TOT_ADDT_AMT FROM ( SELECT (CASE WHEN YRMN > TO_CHAR(ADD_MONTHS(SYSDATE, 1), 'YYYYMM') THEN GIBU.FT_GET_DEMD_AMT_KOSG(UPSO_CD, TO_CHAR(SYSDATE, 'YYYYMM'), TO_CHAR(SYSDATE, 'YYYYMM'), TO_CHAR(SYSDATE, 'YYYYMM'), 'O') ELSE GIBU.FT_GET_DEMD_AMT_KOSG(UPSO_CD, YRMN, TO_CHAR(SYSDATE, 'YYYYMM'), TO_CHAR(SYSDATE, 'YYYYMM'), 'O') END) AS DEMDS , A.UPSO_CD FROM ( SELECT NVL(MAX(ZA.NOTE_YRMN), MAX(OPBI_DAY)) AS YRMN, ZB.UPSO_CD FROM ( SELECT UPSO_CD, OPBI_DAY FROM GIBU.TBRA_UPSO WHERE (CLSBS_YRMN IS NULL OR NVL(SUBSTR(CLSBS_INS_DAY,1,6), ' ') > TO_CHAR(SYSDATE, 'YYYYMM')) AND (NEW_DAY IS NULL OR NEW_DAY <= TO_CHAR(SYSDATE, 'YYYYMM') || '31') AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD) AND STAFF_CD = NVL(:STAFF_CD, STAFF_CD) ) ZB , ( SELECT NOTE_YRMN, UPSO_CD FROM GIBU.TBRA_NOTE WHERE REPT_GBN IN (SELECT CODE_CD FROM FIDU.TENV_CODE WHERE HIGH_CD = '00141' AND CODE_ETC = 'A') ) ZA WHERE ZA.UPSO_CD(+) = ZB.UPSO_CD GROUP BY ZB.UPSO_CD ) A ) ) XA , GIBU.TBRA_UPSO XB , GIBU.TBRA_BSTYPGRAD XC , ( SELECT UPSO_CD , MAX(GOSO_YN) GOSO_YN FROM ( SELECT UPSO_CD , DECODE(COMPN_DAY, NULL, '고소중', NULL) GOSO_YN FROM GIBU.TBRA_ACCU WHERE (JUDG_CD IN ('2','4','10','11','12','13','14') OR JUDG_CD IS NULL) ) GROUP BY UPSO_CD ) XD , FIDU.TENV_POST XE WHERE XB.UPSO_CD = XA.UPSO_CD AND XC.GRAD_GBN = XA.UPSO_GRAD AND XC.BSTYP_CD = XA.BSTYP_CD AND XD.UPSO_CD(+) = XB.UPSO_CD AND XB.UPSO_BD_MNG_NUM = XE.BD_MNG_NUM(+) )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    //##**$$kosg_upso_update
    //##**$$sel_plan_dup
    /*
    */
    public DOBJ CTLsel_plan_dup(DOBJ dobj)
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
            dobj  = CALLsel_plan_dup_SEL1(Conn, dobj);           //  등록된 데이터 확인
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
    public DOBJ CTLsel_plan_dup( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_plan_dup_SEL1(Conn, dobj);           //  등록된 데이터 확인
        return dobj;
    }
    // 등록된 데이터 확인
    public DOBJ CALLsel_plan_dup_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_plan_dup_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_plan_dup_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  VISIT_DAY  ,  VISIT_YN  FROM  GIBU.TMOB_VISIT_PLAN  WHERE  STAFF_CD  =  :INSPRES_ID   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  (  (:JOB_GBN  =  'P'   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN   \n";
        query +=" AND  VISIT_YN  IS  NOT  NULL)   \n";
        query +=" OR  (:JOB_GBN  =  'V'   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN)) ";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$sel_plan_dup
    //##**$$end
}
