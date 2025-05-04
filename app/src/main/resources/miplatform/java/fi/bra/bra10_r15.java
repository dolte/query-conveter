
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_r15
{
    public bra10_r15()
    {
    }
    //##**$$bra10_r15_select
    /* * 프로그램명 : bra10_r15
    * 작성자 : 999999
    * 작성일 : 2009/11/24
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbra10_r15_select(DOBJ dobj)
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
            dobj  = CALLbra10_r15_select_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLbra10_r15_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra10_r15_select_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    // 1.직급명을 구할때 BRAN_CD가 O 이면 출장소장 성과급 입력할때 BRAN_CD가 O인것은 K로 입력되는데 지부장만 O로 입력됨 2.정렬순서 정렬순서를 지부장,사무장,경리,공연관리로 하기위해 JOB_CD2 를 구해서 ORDER BY 함
    public DOBJ CALLbra10_r15_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra10_r15_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r15_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");        //적용 년월
        String   APPL_YRMN_1 = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //적용 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.BRAN_CD,'O','K',A.BRAN_CD)  AS  BRAN_CD,  B.DEPT_NM  AS  BRAN_NM,  TRUNC(D.ATTAIN_RATE,2)  AS  ATTAIN_RATE,  A.STAFF_CD,  DECODE(A.BRAN_CD,'O','출장소장',C.FUNC_NM)  AS  FUNC_NM,  C.HAN_NM,  TRUNC(A.TOT_BONUS  /  10)  *  10  AS  TOT_BONUS,  A.BASIC_AMT_BONUS,  A.LEVY_ACTRE_BONUS,  A.BASIC_AMT_BONUS  +  A.LEVY_ACTRE_BONUS  AS  ACTRE_BONUS_SUBTOTAL,  A.NEW_UPSO_CNT  AS  NEW_EXTTN_UPSOFREQ,  A.NEW_UPSO_TOTAMT  AS  NEW_MONPRNCFEE_TOTAMT,  A.NEW_UPSO_BONUS  AS  NEW_EXTTN_BONUS,  A.APPL_YRMN  FROM  GIBU.TBRA_ACTRE_BONUS  A,   \n";
        query +=" (SELECT  GIBU,  DEPT_NM,  DEPT_CD  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  LIKE  '1060%'   \n";
        query +=" AND  GIBU  IS  NOT  NULL  )  B,   \n";
        query +=" (SELECT  AA.STAFF_NUM,  AA.HAN_NM,  AA.JOB_CD,  (CASE  WHEN  JOB_CD  =  '100'  THEN  '1'  WHEN  JOB_CD  =  '120'  THEN  '3'  WHEN  JOB_CD  =  '140'  THEN  '5'  WHEN  JOB_CD  =  '130'  THEN  '7'  END  )  AS  JOB_CD2,   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00145'   \n";
        query +=" AND  CODE_CD  =  AA.JOB_CD  )  FUNC_NM,  (CASE  WHEN  JOB_CD  =  '100'  THEN  130  WHEN  JOB_CD  =  '120'  THEN  120  WHEN  JOB_CD  =  '140'  THEN  50  WHEN  JOB_CD  =  '130'  THEN  100  END  )  RATE  FROM  INSA.TINS_MST01  AA  )  C,  GIBU.TBRA_BRAN_BONUS_BRE  D  WHERE  A.STAFF_CD  =  C.STAFF_NUM   \n";
        query +=" AND  A.APPL_YRMN  =  D.APPL_YRMN(+)   \n";
        query +=" AND  DECODE(A.BRAN_CD,'O','K',A.BRAN_CD)  =  D.BRAN_CD(+)   \n";
        query +=" AND  DECODE(A.BRAN_CD,'O','K',A.BRAN_CD)  =  B.GIBU(+)   \n";
        query +=" AND  A.APPL_YRMN  =  SUBSTR(:APPL_YRMN_1,0,6)  ORDER  BY  DECODE(A.BRAN_CD,'O','K',A.BRAN_CD),DECODE(A.BRAN_CD,'O','4',C.JOB_CD2) ";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN_1", APPL_YRMN_1);               //적용 년월
        return sobj;
    }
    //##**$$bra10_r15_select
    //##**$$bra10_r15_bonus
    /* * 프로그램명 : bra10_r15
    * 작성자 : 999999
    * 작성일 : 2009/12/07
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbra10_r15_bonus(DOBJ dobj)
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
            dobj  = CALLbra10_r15_bonus_OSP1(Conn, dobj);           //  지부별성과급입력
            dobj  = CALLbra10_r15_bonus_DEL2(Conn, dobj);           //  개인별성과급삭제
            dobj  = CALLbra10_r15_bonus_SEL5(Conn, dobj);           //  징수 총액, 업소수 조회
            dobj  = CALLbra10_r15_bonus_SEL6(Conn, dobj);           //  목표금액 조회
            dobj  = CALLbra10_r15_bonus_SEL7(Conn, dobj);           //  성과요율 계산
            dobj  = CALLbra10_r15_bonus_SEL8(Conn, dobj);           //  징수실적요율 조회
            dobj  = CALLbra10_r15_bonus_SEL3(Conn, dobj);           //  개인별성과급조회
            dobj  = CALLbra10_r15_bonus_INS4(Conn, dobj);           //  개인별성과급입력
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
    public DOBJ CTLbra10_r15_bonus( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra10_r15_bonus_OSP1(Conn, dobj);           //  지부별성과급입력
        dobj  = CALLbra10_r15_bonus_DEL2(Conn, dobj);           //  개인별성과급삭제
        dobj  = CALLbra10_r15_bonus_SEL5(Conn, dobj);           //  징수 총액, 업소수 조회
        dobj  = CALLbra10_r15_bonus_SEL6(Conn, dobj);           //  목표금액 조회
        dobj  = CALLbra10_r15_bonus_SEL7(Conn, dobj);           //  성과요율 계산
        dobj  = CALLbra10_r15_bonus_SEL8(Conn, dobj);           //  징수실적요율 조회
        dobj  = CALLbra10_r15_bonus_SEL3(Conn, dobj);           //  개인별성과급조회
        dobj  = CALLbra10_r15_bonus_INS4(Conn, dobj);           //  개인별성과급입력
        return dobj;
    }
    // 지부별성과급입력
    public DOBJ CALLbra10_r15_bonus_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        String[]  incolumns ={"APPL_YRMN","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //등록자 ID
            record.put("INSPRES_ID",INSPRES_ID);
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
        String   spname ="GIBU.SP_GIBU_BONUS";
        int[]    intypes={12, 12};;
        String[] outcolnms={"P_CNT_INST"};;
        int[]    outtypes ={12};;
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
        dobj.setRetObject(robj);
        return dobj;
    }
    // 개인별성과급삭제
    public DOBJ CALLbra10_r15_bonus_DEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra10_r15_bonus_DEL2(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r15_bonus_DEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YRMN = dvobj.getRecord().get("APPL_YRMN");   //적용 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_ACTRE_BONUS  \n";
        query +=" where APPL_YRMN=:APPL_YRMN";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        return sobj;
    }
    // 징수 총액, 업소수 조회
    // 징수 총액 조회
    public DOBJ CALLbra10_r15_bonus_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra10_r15_bonus_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r15_bonus_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //적용 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.PURE_LEVY_AMT  LEVY_AMT  ,  XA.CNT  -  XB.CNT  CNT  FROM  (   \n";
        query +=" SELECT  SUM(A.REPT_AMT)  LEVY_AMT  ,  COUNT(A.UPSO_CD)  CNT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  NVL(REPT_AMT,  0)  -  NVL(COMIS,  0)  REPT_AMT  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  BETWEEN  :APPL_YRMN  ||  '01'   \n";
        query +=" AND  :APPL_YRMN  ||  '31'   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  UPSO_CD  ,  NVL(DISTR_AMT,  0)  REPT_AMT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  REPT_DAY  BETWEEN  :APPL_YRMN  ||  '01'   \n";
        query +=" AND  :APPL_YRMN  ||  '31'  )  A  )  XA  ,  (   \n";
        query +=" SELECT  SUM(A.REPT_AMT)  LEVY_AMT  ,  COUNT(A.UPSO_CD)  CNT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  NVL(REPT_AMT,  0)  -  NVL(COMIS,  0)  REPT_AMT  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  BETWEEN  :APPL_YRMN  ||  '01'   \n";
        query +=" AND  :APPL_YRMN  ||  '31'   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  UPSO_CD  ,  NVL(DISTR_AMT,  0)  REPT_AMT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  REPT_DAY  BETWEEN  :APPL_YRMN  ||  '01'   \n";
        query +=" AND  :APPL_YRMN  ||  '31'  )  A  WHERE  A.UPSO_CD  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  :APPL_YRMN  BETWEEN  START_YRMN   \n";
        query +=" AND  END_YRMN  )  )  XB  ,  (   \n";
        query +=" SELECT  SUM(PURE_LEVY_AMT)  AS  PURE_LEVY_AMT  FROM  GIBU.TBRA_BRAN_BONUS_BRE  WHERE  APPL_YRMN  =  :APPL_YRMN  )  XC ";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        return sobj;
    }
    // 목표금액 조회
    // 목표금액 조회
    public DOBJ CALLbra10_r15_bonus_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra10_r15_bonus_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r15_bonus_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //적용 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(MON_TARGET_AMT)  MON_TARGET_AMT  FROM  GIBU.TBRA_LEVY_TARGET  WHERE  APPL_YRMN  =  :APPL_YRMN ";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        return sobj;
    }
    // 성과요율 계산
    // 성과요율 계산
    public DOBJ CALLbra10_r15_bonus_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra10_r15_bonus_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        rvobj.Println("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r15_bonus_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REVY_AMT = dobj.getRetObject("SEL5").getRecord().get("LEVY_AMT");   //REVY_AMT
        String   MON_TARGET_AMT = dobj.getRetObject("SEL6").getRecord().get("MON_TARGET_AMT");   //월별 목표 금액
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TRUNC((:REVY_AMT  /  :MON_TARGET_AMT),5)  *100  AS  ATTAIN_RATE  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REVY_AMT", REVY_AMT);               //REVY_AMT
        sobj.setString("MON_TARGET_AMT", MON_TARGET_AMT);               //월별 목표 금액
        return sobj;
    }
    // 징수실적요율 조회
    // 징수실적요율 조회
    public DOBJ CALLbra10_r15_bonus_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra10_r15_bonus_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        rvobj.Println("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r15_bonus_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   ATTAIN_RATE = dobj.getRetObject("SEL7").getRecord().getDouble("ATTAIN_RATE");   //달성율
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //적용 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BONUS_RATE  FROM  GIBU.TBRA_BONUS_PLOT  WHERE  APPL_YEAR  =  SUBSTR(:APPL_YRMN,  1,  4)   \n";
        query +=" AND  START_RATE  <=  :ATTAIN_RATE   \n";
        query +=" AND  END_RATE  >  :ATTAIN_RATE ";
        sobj.setSql(query);
        sobj.setDouble("ATTAIN_RATE", ATTAIN_RATE);               //달성율
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        return sobj;
    }
    // 개인별성과급조회
    public DOBJ CALLbra10_r15_bonus_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra10_r15_bonus_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r15_bonus_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   BONUS_RATE = dobj.getRetObject("SEL8").getRecord().getDouble("BONUS_RATE");   //성과 요율
        double   LEVY_AMT = dobj.getRetObject("SEL5").getRecord().getDouble("LEVY_AMT");   //징수 금액
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //적용 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.APPL_YRMN,  DECODE(JOB_CD,'100',B.GIBU2,A.BRAN_CD)  AS  BRAN_CD,  C.STAFF_NUM  AS  STAFF_CD,  C.BASIC_AMT,  TRUNC(C.BASIC_AMT  /  SUM(C.BASIC_AMT)  OVER()  *  100,5)  AS  BASIC_AMT_RATE,  C.BASIC_AMT  /  SUM(C.BASIC_AMT)  OVER()  *  (:LEVY_AMT  *  :BONUS_RATE  /  100  *  0.3)  AS  BASIC_AMT_BONUS,  C.RATE  AS  LEVY_ACTRE_RATE,  C.RATE  /  F.TOTAL_RATE  *  E.TOTAL_BONUS  AS  LEVY_ACTRE_BONUS,  NVL(G.NEW_UPSO_CNT,0)  NEW_UPSO_CNT,  NVL(G.NEW_UPSO_TOTAMT,0)  NEW_UPSO_TOTAMT,  (CASE  WHEN  JOB_CD  =  '100'  THEN  NVL(H.NEW_UPSO_TOTAMT,0)  *  0.1  WHEN  JOB_CD  =  '120'  THEN  NVL(G.NEW_UPSO_TOTAMT,0)  *  0.1  WHEN  JOB_CD  =  '140'  THEN  NVL(H.NEW_UPSO_TOTAMT,0)  *  0.05  WHEN  JOB_CD  =  '130'  THEN  NVL(G.NEW_UPSO_TOTAMT,0)  *  0.1  END  )  AS  NEW_UPSO_BONUS,  (CASE  WHEN  JOB_CD  =  '100'  THEN  (NVL(H.NEW_UPSO_TOTAMT,0)  *  0.1)  +  (C.RATE  /  F.TOTAL_RATE  *  E.TOTAL_BONUS)  +  (C.BASIC_AMT  /  SUM(C.BASIC_AMT)  OVER()  *  (:LEVY_AMT  *  :BONUS_RATE  /  100  *  0.3))  WHEN  JOB_CD  =  '120'  THEN  (NVL(G.NEW_UPSO_TOTAMT,0)  *  0.1)  +  (C.RATE  /  F.TOTAL_RATE  *  E.TOTAL_BONUS)  +  (C.BASIC_AMT  /  SUM(C.BASIC_AMT)  OVER()  *  (:LEVY_AMT  *  :BONUS_RATE  /  100  *  0.3))  WHEN  JOB_CD  =  '140'  THEN  (NVL(H.NEW_UPSO_TOTAMT,0)  *  0.05)  +  (C.RATE  /  F.TOTAL_RATE  *  E.TOTAL_BONUS)  +  (C.BASIC_AMT  /  SUM(C.BASIC_AMT)  OVER()  *  (:LEVY_AMT  *  :BONUS_RATE  /  100  *  0.3))  WHEN  JOB_CD  =  '130'  THEN  (NVL(G.NEW_UPSO_TOTAMT,0)  *  0.1)  +  (C.RATE  /  F.TOTAL_RATE  *  E.TOTAL_BONUS)  +  (C.BASIC_AMT  /  SUM(C.BASIC_AMT)  OVER()  *  (:LEVY_AMT  *  :BONUS_RATE  /  100  *  0.3))  END  )  AS  TOT_BONUS,  SUM(C.BASIC_AMT)  OVER()  BASIC_AMT_ACC  FROM  GIBU.TBRA_BRAN_BONUS_BRE  A,   \n";
        query +=" (SELECT  (CASE  WHEN  GIBU  =  'O'  THEN  'K'  ELSE  GIBU  END  )  AS  GIBU,  GIBU  AS  GIBU2,  DEPT_NM,  DEPT_CD  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  LIKE  '1060%'   \n";
        query +=" AND  GIBU  IS  NOT  NULL  )  B,   \n";
        query +=" (SELECT   \n";
        query +=" (SELECT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  TCPM_DEPT.DEPT_CD  =  A.DEPT_CD)  GIBU,  A.DEPT_CD,   \n";
        query +=" (SELECT  DEPT_NM  FROM  INSA.TCPM_DEPT  WHERE  TCPM_DEPT.DEPT_CD  =  A.DEPT_CD)  DEPT_NM,  A.STAFF_NUM,  A.HAN_NM,  A.JOBGRD_CD,   \n";
        query +=" (SELECT  D.CODE_NM  FROM  FIDU.TENV_CODE  D  WHERE  D.HIGH_CD  =  '00061'   \n";
        query +=" AND  D.CODE_CD  =  A.JOBGRD_CD)  JOBGRD_NM,  A.FUNC_CD,   \n";
        query +=" (SELECT  E.CODE_NM  FROM  FIDU.TENV_CODE  E  WHERE  E.HIGH_CD  =  '00059'   \n";
        query +=" AND  E.CODE_CD  =  A.FUNC_CD)  FUNC_NM,  A.JOB_CD,   \n";
        query +=" (SELECT  F.CODE_NM  FROM  FIDU.TENV_CODE  F  WHERE  F.HIGH_CD  =  '00145'   \n";
        query +=" AND  F.CODE_CD  =  A.JOB_CD)  JOB_NM,  B.BASIC_AMT,  SUM(B.BASIC_AMT)  OVER(ORDER  BY  A.DEPT_CD,A.STAFF_NUM  ROWS  BETWEEN  UNBOUNDED  PRECEDING   \n";
        query +=" AND  CURRENT  ROW)  BASIC_AMT_ACC,  B.MNG_YEAR,  (CASE  WHEN  JOB_CD  =  '100'  THEN  130  WHEN  JOB_CD  =  '120'  THEN  120  WHEN  JOB_CD  =  '140'  THEN  50  WHEN  JOB_CD  =  '130'  THEN  100  END  )  RATE  FROM  INSA.TINS_MST01  A,  INSA.TPAY_HOBONG  B  WHERE  A.BIPLC_GBN  =  '2'   \n";
        query +=" AND  NVL(TO_NUMBER(SUBSTR(A.RETIRE_DAY,1,6)),999999)  >=  TO_NUMBER(:APPL_YRMN)   \n";
        query +=" AND  B.MNG_YEAR  =   \n";
        query +=" (SELECT  MAX(MNG_YEAR)  FROM  INSA.TPAY_HOBONG  WHERE  USE_YN  =  'Y'   \n";
        query +=" AND  MNG_YEAR  >=  TO_NUMBER(SUBSTR(:APPL_YRMN,1,4))  -  1   \n";
        query +=" AND  MNG_YEAR  <=  SUBSTR(:APPL_YRMN,1,4)   \n";
        query +=" AND  SUBSTR(TO_CHAR(MOD_DAY,'YYYYMMDD'),1,6)  <=  :APPL_YRMN  )   \n";
        query +=" AND  B.USE_YN  =  'Y'   \n";
        query +=" AND  A.BIPLC_GBN  =  B.BIPLC_GBN(+)   \n";
        query +=" AND  A.JOBGRD_CD  =  B.JOBGRD_CD(+)   \n";
        query +=" AND  A.HOBONG_CD  =  B.HOBONG_CD(+)  ORDER  BY  A.DEPT_CD,A.STAFF_NUM  )  C,  GIBU.TBRA_LEVY_TARGET  D,   \n";
        query +=" (SELECT  BRAN_CD,  NVL(LEVY_BONUS,0)  +  NVL(EXT_BONUS,0)  +  NVL(UPSO_CNT_BONUS,0)  AS  TOTAL_BONUS  FROM  GIBU.TBRA_BRAN_BONUS_BRE  WHERE  APPL_YRMN  =  :APPL_YRMN  )  E,   \n";
        query +=" (SELECT  BB.GIBU,  (SUM(CASE  WHEN  JOB_CD  =  '100'  THEN  130  WHEN  JOB_CD  =  '120'  THEN  120  WHEN  JOB_CD  =  '140'  THEN  50  WHEN  JOB_CD  =  '130'  THEN  100  END  )  )  TOTAL_RATE  FROM  INSA.TINS_MST01  AA,   \n";
        query +=" (SELECT  GIBU,  DEPT_NM,  DEPT_CD  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  LIKE  '1060%'   \n";
        query +=" AND  GIBU  IS  NOT  NULL  )  BB  WHERE  AA.DUTY_GBN  =  '001'   \n";
        query +=" AND  AA.DEPT_CD  =  BB.DEPT_CD  GROUP  BY  BB.GIBU  )  F,   \n";
        query +=" (SELECT  AA.BRAN_CD,  AA.NEW_YRMN,  AA.STAFF_CD,  AA.NEW_UPSO_CNT,  AA.NEW_UPSO_TOTAMT,  AA.NEW_UPSO_AMT,  AA.RATE  FROM  GIBU.TBRA_NEW_UPSO_BONUS_MNG  AA,  GIBU.TBRA_BRAN_BONUS_BRE  BB  WHERE  AA.NEW_YRMN  =  :APPL_YRMN   \n";
        query +=" AND  AA.NEW_YRMN  =  BB.APPL_YRMN   \n";
        query +=" AND  AA.BRAN_CD  =  BB.BRAN_CD  )  G,   \n";
        query +=" (SELECT  (SUM(CASE  WHEN  JOB_CD  =  '100'  THEN  0  WHEN  JOB_CD  =  '120'  THEN  NVL(BB.NEW_UPSO_TOTAMT,0)  *  0.1  WHEN  JOB_CD  =  '140'  THEN  0  WHEN  JOB_CD  =  '130'  THEN  NVL(BB.NEW_UPSO_TOTAMT,0)  *  0.1  END  )  )  NEW_UPSO_TOTAMT,  CC.GIBU  FROM  INSA.TINS_MST01  AA,   \n";
        query +=" (SELECT  AA.STAFF_CD,  AA.NEW_UPSO_CNT,  AA.NEW_UPSO_TOTAMT,  AA.NEW_UPSO_AMT  FROM  GIBU.TBRA_NEW_UPSO_BONUS_MNG  AA,  GIBU.TBRA_BRAN_BONUS_BRE  BB  WHERE  AA.NEW_YRMN  =  :APPL_YRMN   \n";
        query +=" AND  AA.BRAN_CD  =  BB.BRAN_CD  )  BB,   \n";
        query +=" (SELECT  GIBU,  DEPT_NM,  DEPT_CD  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  LIKE  '1060%'   \n";
        query +=" AND  GIBU  IS  NOT  NULL  )  CC  WHERE  AA.BIPLC_GBN  =  '2'   \n";
        query +=" AND  AA.STAFF_NUM  =  BB.STAFF_CD(+)   \n";
        query +=" AND  AA.DEPT_CD  =  CC.DEPT_CD   \n";
        query +=" AND  NVL(TO_NUMBER(SUBSTR(AA.RETIRE_DAY,1,6)),999999)  >=  TO_NUMBER(:APPL_YRMN)  GROUP  BY  CC.GIBU  )  H  WHERE  A.BRAN_CD  =  B.GIBU   \n";
        query +=" AND  B.DEPT_CD  =  C.DEPT_CD   \n";
        query +=" AND  A.APPL_YRMN  =  D.APPL_YRMN   \n";
        query +=" AND  A.BRAN_CD  =  D.BRAN_CD   \n";
        query +=" AND  A.BRAN_CD  =  E.BRAN_CD   \n";
        query +=" AND  A.BRAN_CD  =  F.GIBU   \n";
        query +=" AND  A.BRAN_CD  =  H.GIBU   \n";
        query +=" AND  C.STAFF_NUM  =  G.STAFF_CD(+)   \n";
        query +=" AND  A.APPL_YRMN  =  :APPL_YRMN ";
        sobj.setSql(query);
        sobj.setDouble("BONUS_RATE", BONUS_RATE);               //성과 요율
        sobj.setDouble("LEVY_AMT", LEVY_AMT);               //징수 금액
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        return sobj;
    }
    // 개인별성과급입력
    public DOBJ CALLbra10_r15_bonus_INS4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS4");
        VOBJ dvobj = dobj.getRetObject("SEL3");           //개인별성과급조회에서 생성시킨 OBJECT입니다.(CALLbra10_r15_bonus_SEL3)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra10_r15_bonus_INS4(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS4") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r15_bonus_INS4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        double   LEVY_ACTRE_BONUS = dvobj.getRecord().getDouble("LEVY_ACTRE_BONUS");   //징수 실적 성과급
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        double   LEVY_ACTRE_RATE = dvobj.getRecord().getDouble("LEVY_ACTRE_RATE");   //징수 실적 비율
        double   NEW_UPSO_TOTAMT = dvobj.getRecord().getDouble("NEW_UPSO_TOTAMT");   //신규 개발 총금액
        double   NEW_UPSO_BONUS = dvobj.getRecord().getDouble("NEW_UPSO_BONUS");   //신규 개발 성과급
        String   BASIC_AMT = dvobj.getRecord().get("BASIC_AMT");   //기본급
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        double   BASIC_AMT_RATE = dvobj.getRecord().getDouble("BASIC_AMT_RATE");   //기본급 비율
        String   APPL_YRMN = dvobj.getRecord().get("APPL_YRMN");   //적용 년월
        double   TOT_BONUS = dvobj.getRecord().getDouble("TOT_BONUS");   //총 성과급
        double   BASIC_AMT_BONUS = dvobj.getRecord().getDouble("BASIC_AMT_BONUS");   //기본급 성과급
        int   NEW_UPSO_CNT = dvobj.getRecord().getInt("NEW_UPSO_CNT");   //신규 개발 업소
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_ACTRE_BONUS (NEW_UPSO_CNT, BASIC_AMT_BONUS, INSPRES_ID, TOT_BONUS, APPL_YRMN, INS_DATE, BASIC_AMT_RATE, STAFF_CD, BASIC_AMT, NEW_UPSO_BONUS, NEW_UPSO_TOTAMT, LEVY_ACTRE_RATE, BRAN_CD, LEVY_ACTRE_BONUS)  \n";
        query +=" values(:NEW_UPSO_CNT , :BASIC_AMT_BONUS , :INSPRES_ID , :TOT_BONUS , :APPL_YRMN , SYSDATE, :BASIC_AMT_RATE , :STAFF_CD , :BASIC_AMT , :NEW_UPSO_BONUS , :NEW_UPSO_TOTAMT , :LEVY_ACTRE_RATE , :BRAN_CD , :LEVY_ACTRE_BONUS )";
        sobj.setSql(query);
        sobj.setDouble("LEVY_ACTRE_BONUS", LEVY_ACTRE_BONUS);               //징수 실적 성과급
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setDouble("LEVY_ACTRE_RATE", LEVY_ACTRE_RATE);               //징수 실적 비율
        sobj.setDouble("NEW_UPSO_TOTAMT", NEW_UPSO_TOTAMT);               //신규 개발 총금액
        sobj.setDouble("NEW_UPSO_BONUS", NEW_UPSO_BONUS);               //신규 개발 성과급
        sobj.setString("BASIC_AMT", BASIC_AMT);               //기본급
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setDouble("BASIC_AMT_RATE", BASIC_AMT_RATE);               //기본급 비율
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        sobj.setDouble("TOT_BONUS", TOT_BONUS);               //총 성과급
        sobj.setDouble("BASIC_AMT_BONUS", BASIC_AMT_BONUS);               //기본급 성과급
        sobj.setInt("NEW_UPSO_CNT", NEW_UPSO_CNT);               //신규 개발 업소
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    //##**$$bra10_r15_bonus
    //##**$$bra10_r15_delete
    /*
    */
    public DOBJ CTLbra10_r15_delete(DOBJ dobj)
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
            dobj  = CALLbra10_r15_delete_MIUD1(Conn, dobj);           //  성과급삭제
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
    public DOBJ CTLbra10_r15_delete( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra10_r15_delete_MIUD1(Conn, dobj);           //  성과급삭제
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 성과급삭제
    public DOBJ CALLbra10_r15_delete_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("MIUD1");
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
                dobj  = CALLbra10_r15_delete_UPD7(Conn, dobj);           //  개인별성과급삭제
            }
        }
        return dobj;
    }
    // 개인별성과급삭제
    public DOBJ CALLbra10_r15_delete_UPD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra10_r15_delete_UPD7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r15_delete_UPD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   APPL_YRMN = dvobj.getRecord().get("APPL_YRMN");   //적용 년월
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   BASIC_AMT ="0";   //기본급
        double   BASIC_AMT_BONUS = 0;   //기본급 성과급
        double   BASIC_AMT_RATE = 0;   //기본급 비율
        double   LEVY_ACTRE_BONUS = 0;   //징수 실적 성과급
        double   LEVY_ACTRE_RATE = 0;   //징수 실적 비율
        double   NEW_UPSO_BONUS = 0;   //신규 개발 성과급
        int   NEW_UPSO_CNT = 0;   //신규 개발 업소
        double   NEW_UPSO_TOTAMT = 0;   //신규 개발 총금액
        double   TOT_BONUS = 0;   //총 성과급
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_ACTRE_BONUS  \n";
        query +=" set NEW_UPSO_CNT=:NEW_UPSO_CNT , BASIC_AMT_BONUS=:BASIC_AMT_BONUS , BASIC_AMT_RATE=:BASIC_AMT_RATE , TOT_BONUS=:TOT_BONUS , BASIC_AMT=:BASIC_AMT , NEW_UPSO_BONUS=:NEW_UPSO_BONUS , NEW_UPSO_TOTAMT=:NEW_UPSO_TOTAMT , LEVY_ACTRE_RATE=:LEVY_ACTRE_RATE , LEVY_ACTRE_BONUS=:LEVY_ACTRE_BONUS  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and APPL_YRMN=:APPL_YRMN  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("APPL_YRMN", APPL_YRMN);               //적용 년월
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BASIC_AMT", BASIC_AMT);               //기본급
        sobj.setDouble("BASIC_AMT_BONUS", BASIC_AMT_BONUS);               //기본급 성과급
        sobj.setDouble("BASIC_AMT_RATE", BASIC_AMT_RATE);               //기본급 비율
        sobj.setDouble("LEVY_ACTRE_BONUS", LEVY_ACTRE_BONUS);               //징수 실적 성과급
        sobj.setDouble("LEVY_ACTRE_RATE", LEVY_ACTRE_RATE);               //징수 실적 비율
        sobj.setDouble("NEW_UPSO_BONUS", NEW_UPSO_BONUS);               //신규 개발 성과급
        sobj.setInt("NEW_UPSO_CNT", NEW_UPSO_CNT);               //신규 개발 업소
        sobj.setDouble("NEW_UPSO_TOTAMT", NEW_UPSO_TOTAMT);               //신규 개발 총금액
        sobj.setDouble("TOT_BONUS", TOT_BONUS);               //총 성과급
        return sobj;
    }
    //##**$$bra10_r15_delete
    //##**$$end
}
