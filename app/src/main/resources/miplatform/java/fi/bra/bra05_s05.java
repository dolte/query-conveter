
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_s05
{
    public bra05_s05()
    {
    }
    //##**$$mng_bpap
    /*
    */
    public DOBJ CTLmng_bpap(DOBJ dobj)
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
            dobj  = CALLmng_bpap_MIUD1(Conn, dobj);           //  분기
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
    public DOBJ CTLmng_bpap( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_bpap_MIUD1(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLmng_bpap_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLmng_bpap_INS5(Conn, dobj);           //  최고서등록
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_bpap_DEL6(Conn, dobj);           //  최고서삭제
            }
        }
        return dobj;
    }
    // 최고서삭제
    public DOBJ CALLmng_bpap_DEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_bpap_DEL6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bpap_DEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SEND_DAY = dvobj.getRecord().get("SEND_DAY");   //발송일자
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BPAP  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEND_DAY=:SEND_DAY";
        sobj.setSql(query);
        sobj.setString("SEND_DAY", SEND_DAY);               //발송일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 최고서등록
    public DOBJ CALLmng_bpap_INS5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLmng_bpap_INS5(dobj, dvobj);
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
    private SQLObject SQLmng_bpap_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        double   TOT_EADDT_AMT = dvobj.getRecord().getDouble("TOT_EADDT_AMT");   //총 중가산 금액
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //시작년월
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //종료년월
        String   TERM_DAY = dvobj.getRecord().get("TERM_DAY");   //해지 일자
        String   SEND_DAY = dvobj.getRecord().get("SEND_DAY");   //발송일자
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        double   TOT_USE_AMT = dvobj.getRecord().getDouble("TOT_USE_AMT");   //총 사용 금액
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //총 가산 금액
        String   BPAP_DAY = dvobj.getRecord().get("BPAP_DAY");   //최고서 일자
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BPAP (INS_DATE, INSPRES_ID, BPAP_DAY, TOT_ADDT_AMT, TOT_USE_AMT, UPSO_CD, SEND_DAY, TERM_DAY, END_YRMN, REMAK, START_YRMN, TOT_EADDT_AMT)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BPAP_DAY , :TOT_ADDT_AMT , :TOT_USE_AMT , :UPSO_CD , :SEND_DAY , :TERM_DAY , :END_YRMN , :REMAK , :START_YRMN , :TOT_EADDT_AMT )";
        sobj.setSql(query);
        sobj.setDouble("TOT_EADDT_AMT", TOT_EADDT_AMT);               //총 중가산 금액
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("TERM_DAY", TERM_DAY);               //해지 일자
        sobj.setString("SEND_DAY", SEND_DAY);               //발송일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("TOT_USE_AMT", TOT_USE_AMT);               //총 사용 금액
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //총 가산 금액
        sobj.setString("BPAP_DAY", BPAP_DAY);               //최고서 일자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    //##**$$mng_bpap
    //##**$$sel_bpap
    /*
    */
    public DOBJ CTLsel_bpap(DOBJ dobj)
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
            dobj  = CALLsel_bpap_SEL1(Conn, dobj);           //  최고서조회
            if(!dobj.getRetObject("S").getRecord().get("UPSO_CD").equals(""))
            {
                dobj  = CALLsel_bpap_SEL4(Conn, dobj);           //  개별출력용 조회
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
    public DOBJ CTLsel_bpap( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_bpap_SEL1(Conn, dobj);           //  최고서조회
        if(!dobj.getRetObject("S").getRecord().get("UPSO_CD").equals(""))
        {
            dobj  = CALLsel_bpap_SEL4(Conn, dobj);           //  개별출력용 조회
        }
        return dobj;
    }
    // 최고서조회
    public DOBJ CALLsel_bpap_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_bpap_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bpap_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dvobj.getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dvobj.getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.BRAN_CD  ,  GIBU.GET_BRAN_NM(B.BRAN_CD)  AS  BRAN_NM  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.SEND_DAY  ,  A.BPAP_DAY  ,  A.TERM_DAY  ,  C.POST_NUM  AS  BRAN_ZIP  ,  C.ADDR  ||  '  '  ||  C.HNM  AS  BRAN_ADDR  ,   \n";
        query +=" (SELECT  GIBU.FT_GET_PHONE_FORMAT(IPPBX_INPHONE_NUM)  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  B.STAFF_CD)  AS  BRAN_TEL  ,   \n";
        query +=" (SELECT  GIBU.FT_GET_PHONE_FORMAT(IPPBX_USER_ID)  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  B.STAFF_CD)  AS  BRAN_FAX  ,  B.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,  B.MNGEMSTR_NM  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||  B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  AS  ADDR  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  AS  STAFF_NM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.TOT_USE_AMT  ,  A.TOT_ADDT_AMT  ,  A.TOT_EADDT_AMT  ,  (CASE  WHEN  D.JOB_GBN  IS  NOT  NULL  THEN  'Y'  ELSE  'N'  END)  AS  PRINT_YN  ,  B.NEW_DAY  FROM  GIBU.TBRA_BPAP  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_BIPLC  C  ,  GIBU.TBRA_UPSO_VISIT  D  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  C.GIBU   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.SEND_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  B.STAFF_CD  =  NVL(:STAFF_CD,  B.STAFF_CD)   \n";
        query +=" AND  A.UPSO_CD  =  D.UPSO_CD(+)   \n";
        query +=" AND  A.SEND_DAY  =  D.VISIT_DAY(+)   \n";
        query +=" AND  D.JOB_GBN(+)  =  'C'  ORDER  BY  B.BRAN_CD,  FIDU.GET_STAFF_NM(B.STAFF_CD),  B.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 개별출력용 조회
    public DOBJ CALLsel_bpap_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_bpap_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bpap_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.BRAN_CD  ,  GIBU.GET_BRAN_NM(B.BRAN_CD)  AS  BRAN_NM  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.SEND_DAY  AS  DISP_DAY  ,  A.BPAP_DAY  ,  A.TERM_DAY  AS  CONTR_TERM_DAY  ,  C.POST_NUM  AS  BRAN_ZIP  ,  C.ADDR  ||  '  '  ||  C.HNM  AS  BRAN_ADDR  ,   \n";
        query +=" (SELECT  GIBU.FT_GET_PHONE_FORMAT(IPPBX_INPHONE_NUM)  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  B.STAFF_CD)  AS  BRAN_TEL  ,   \n";
        query +=" (SELECT  GIBU.FT_GET_PHONE_FORMAT(IPPBX_USER_ID)  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  B.STAFF_CD)  AS  BRAN_FAX  ,  B.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,  B.MNGEMSTR_NM  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||  B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  AS  ADDR  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  AS  STAFF_NM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.TOT_USE_AMT  ,  A.TOT_ADDT_AMT  ,  A.TOT_EADDT_AMT  ,  (CASE  WHEN  D.JOB_GBN  IS  NOT  NULL  THEN  'Y'  ELSE  'N'  END)  AS  PRINT_YN  ,  B.NEW_DAY  FROM  GIBU.TBRA_BPAP  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_BIPLC  C  ,  GIBU.TBRA_UPSO_VISIT  D  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  C.GIBU   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.SEND_DAY  =   \n";
        query +=" (SELECT  MAX(SEND_DAY)  FROM  GIBU.TBRA_BPAP  WHERE  UPSO_CD  =  :UPSO_CD)   \n";
        query +=" AND  A.UPSO_CD  =  D.UPSO_CD(+)   \n";
        query +=" AND  A.SEND_DAY  =  D.VISIT_DAY(+)   \n";
        query +=" AND  D.JOB_GBN(+)  =  'C'  ORDER  BY  B.BRAN_CD,  FIDU.GET_STAFF_NM(B.STAFF_CD),  B.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$sel_bpap
    //##**$$ins_visit_print
    /*
    */
    public DOBJ CTLins_visit_print(DOBJ dobj)
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
            dobj  = CALLins_visit_print_MPD3(Conn, dobj);           //  멀티처리
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
    public DOBJ CTLins_visit_print( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLins_visit_print_MPD3(Conn, dobj);           //  멀티처리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 멀티처리
    public DOBJ CALLins_visit_print_MPD3(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLins_visit_print_SEL5(Conn, dobj);           //  VISIT_SEQ구하기
                dobj  = CALLins_visit_print_INS1(Conn, dobj);           //  업소방문이력등록
            }
        }
        return dobj;
    }
    // VISIT_SEQ구하기
    public DOBJ CALLins_visit_print_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLins_visit_print_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLins_visit_print_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("SEND_DAY");   //방문 일자
        String   JOB_GBN ="C";   //업무 구분(TENV_CODE TABLE의 HIGH_CD = '00131')
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),  0)  +  1  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분(TENV_CODE TABLE의 HIGH_CD = '00131')
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소방문이력등록
    public DOBJ CALLins_visit_print_INS1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS1");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLins_visit_print_INS1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLins_visit_print_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String VISIT_TIME ="";        //방문 시간
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CONSPRES ="[수신자] "+dobj.getRetObject("R").getRecord().get("MNGEMSTR_NM");   //상담자
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   JOB_GBN ="C";   //업무 구분
        String   REMAK ="최고서 발행(최고서등록및출력)";   //비고
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("SEND_DAY");   //방문 일자
        int   VISIT_SEQ = dobj.getRetObject("SEL5").getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, VISIT_TIME, INSPRES_ID, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, TO_CHAR(SYSDATE, 'HH24MI'), :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CONSPRES", CONSPRES);               //상담자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    //##**$$ins_visit_print
    //##**$$end
}
