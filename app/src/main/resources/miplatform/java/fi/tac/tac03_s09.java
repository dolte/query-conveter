
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac03_s09
{
    public tac03_s09()
    {
    }
    //##**$$calc_realsupp
    /* SEL4는 월공제 마지막 프로시저를 참조하였다.
    */
    public DOBJ CTLcalc_realsupp(DOBJ dobj)
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
            dobj  = CALLcalc_realsupp_XIUD10(Conn, dobj);           //  사전 몽땅삭제
            dobj  = CALLcalc_realsupp_INS6(Conn, dobj);           //  임시테이블에 기록
            dobj  = CALLcalc_realsupp_SEL1(Conn, dobj);           //  전체조회
            dobj  = CALLcalc_realsupp_SEL4(Conn, dobj);           //  최종
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
    public DOBJ CTLcalc_realsupp( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcalc_realsupp_XIUD10(Conn, dobj);           //  사전 몽땅삭제
        dobj  = CALLcalc_realsupp_INS6(Conn, dobj);           //  임시테이블에 기록
        dobj  = CALLcalc_realsupp_SEL1(Conn, dobj);           //  전체조회
        dobj  = CALLcalc_realsupp_SEL4(Conn, dobj);           //  최종
        return dobj;
    }
    // 사전 몽땅삭제
    public DOBJ CALLcalc_realsupp_XIUD10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD10");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcalc_realsupp_XIUD10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcalc_realsupp_XIUD10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FIDU.TTAC_CALCSUPPAMT_IMSI";
        sobj.setSql(query);
        return sobj;
    }
    // 임시테이블에 기록
    public DOBJ CALLcalc_realsupp_INS6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("INS6");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcalc_realsupp_INS6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS6") ;
        rvobj.Println("INS6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcalc_realsupp_INS6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INHABTAX_YN ="";        //주빈세포함여부
        String INS_DATE ="";        //등록 일시
        double MNGCOMIS_RATE = 0;        //요율
        double TAXRATE = 0;        //세율
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        String   MNGCOMIS_RATE_2 = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //요율
        String   MNGCOMIS_RATE_1 = dobj.getRetObject("S").getRecord().get("MDM_CD");   //요율
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //매체 코드
        String   INHABTAX_YN_1 = dobj.getRetObject("S").getRecord().get("MB_CD");   //주빈세포함여부
        String   TAXRATE_1 = dobj.getRetObject("S").getRecord().get("MB_CD");   //세율
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //관리번호
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        double   DISTR_AMT = dvobj.getRecord().getDouble("DISTR_AMT");   //분배 금액
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_CALCSUPPAMT_IMSI (MB_CD, INS_DATE, INSPRES_ID, DISTR_AMT, TRNSF_GBN, MNG_NUM, TAXRATE, INHABTAX_YN, MDM_CD, MNGCOMIS_RATE, SUPP_YRMN)  \n";
        query +=" values(:MB_CD , SYSDATE, :INSPRES_ID , :DISTR_AMT , :TRNSF_GBN , :MNG_NUM , (SELECT TAXRATE FROM FIDU.TTAC_ORGTAXRATE_MNG WHERE CTRY_CD = (SELECT HOUSE_CD FROM FIDU.TMEM_MB WHERE MB_CD = :TAXRATE_1)), (SELECT INHABTAX_YN FROM FIDU.TTAC_ORGTAXRATE_MNG WHERE CTRY_CD = (SELECT HOUSE_CD FROM FIDU.TMEM_MB WHERE MB_CD = :INHABTAX_YN_1)), :MDM_CD , (SELECT MNGCOMIS_RATE FROM FIDU.TENV_MNGCOMIS WHERE MDM_CD = :MNGCOMIS_RATE_1 AND APPL_YRMN = :MNGCOMIS_RATE_2), :SUPP_YRMN )";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("MNGCOMIS_RATE_2", MNGCOMIS_RATE_2);               //요율
        sobj.setString("MNGCOMIS_RATE_1", MNGCOMIS_RATE_1);               //요율
        sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        sobj.setString("INHABTAX_YN_1", INHABTAX_YN_1);               //주빈세포함여부
        sobj.setString("TAXRATE_1", TAXRATE_1);               //세율
        sobj.setDouble("MNG_NUM", MNG_NUM);               //관리번호
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setDouble("DISTR_AMT", DISTR_AMT);               //분배 금액
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 전체조회
    public DOBJ CALLcalc_realsupp_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcalc_realsupp_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcalc_realsupp_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MNG_NUM,  MB_CD,  HANMB_NM,  MB_GBN1,  TRNSF_GBN,  MDM_CD,  MDM_NM_ALL,  MDM_CD_NM,  SUPP_YRMN,  DISTR_AMT,  MNGCOMIS_RATE,  TAXRATE,  INHABTAX_YN,  TRUNC(DISTR_AMT  *  MNGCOMIS_RATE  *  0.01)  AS  MNGCOMIS_AMT  FROM  (   \n";
        query +=" SELECT  A.MNG_NUM,  A.MB_CD,  B.HANMB_NM,  B.MB_GBN1,  A.TRNSF_GBN,  A.MDM_CD,   \n";
        query +=" (SELECT  LARGECLASS_CD_NM  ||  '<'  ||  AVECLASS_CD_NM  ||  '<'  ||  SCLASS_CD_NM  ||  '<'  ||  MDM_CD_NM  FROM  FIDU.TENV_MDMCD  WHERE  MDM_CD=A.MDM_CD)  AS  MDM_NM_ALL,   \n";
        query +=" (SELECT  MDM_CD_NM  FROM  FIDU.TENV_MDMCD  WHERE  MDM_CD=A.MDM_CD)  AS  MDM_CD_NM,  A.SUPP_YRMN,  A.DISTR_AMT,  A.MNGCOMIS_RATE,  A.TAXRATE,  A.INHABTAX_YN  FROM  FIDU.TTAC_CALCSUPPAMT_IMSI  A,  FIDU.TMEM_MB  B  WHERE  1=1   \n";
        query +=" AND  A.MB_CD  =  B.MB_CD  )  ORDER  BY  TO_NUMBER(MNG_NUM) ";
        sobj.setSql(query);
        return sobj;
    }
    // 최종
    public DOBJ CALLcalc_realsupp_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcalc_realsupp_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcalc_realsupp_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MB_CD,  FIDU.GET_MB_NM(MB_CD)  AS  HANMB_NM,   \n";
        query +=" (SELECT  MB_GBN1  FROM  FIDU.TMEM_MB  WHERE  MB_CD  =  X.MB_CD)  AS  MB_GBN1,  TRNSF_GBN,  SUPP_YRMN,  TAXRATE,  INHABTAX_YN,  HOUSE_CD,  DISTR_AMT  AS  DISTR_AMT,  MNGCOMIS_AMT  AS  MNGCOMIS_AMT,  TAX1_AMT,  TAX2_AMT,  (DISTR_AMT  -  MNGCOMIS_AMT  -  TAX1_AMT  -  TAX2_AMT)  AS  REALSUPP_AMT  FROM  (   \n";
        query +=" SELECT  MB_CD,  TRNSF_GBN,  SUPP_YRMN,  TAXRATE,  INHABTAX_YN,  HOUSE_CD,  SUM(DISTR_AMT)  AS  DISTR_AMT,  SUM(MNGCOMIS_AMT)  AS  MNGCOMIS_AMT,  (CASE  WHEN  TRNSF_GBN  =  '1'   \n";
        query +=" AND  HOUSE_CD  =  '082'  THEN  TRUNC(SUM(DISTR_AMT)  *  3  *  0.01,  -1)  WHEN  TRNSF_GBN  =  '1'   \n";
        query +=" AND  HOUSE_CD  <>  '082'  THEN  (CASE  WHEN  INHABTAX_YN  =  0  THEN  TRUNC(SUM(DISTR_AMT)  *  TAXRATE  *  0.01,  -1)  ELSE  TRUNC((SUM(DISTR_AMT)  *  TAXRATE  *  0.01)  /  1.1,  -1)  END)  WHEN  TRNSF_GBN  =  '2'   \n";
        query +=" AND  HOUSE_CD  =  '082'  THEN  TRUNC((SUM(DISTR_AMT)  -  SUM(MNGCOMIS_AMT))  *  0.2,  -1)  WHEN  TRNSF_GBN  =  '2'   \n";
        query +=" AND  HOUSE_CD  <>  '082'  THEN  (CASE  WHEN  INHABTAX_YN  =  0  THEN  TRUNC(SUM(DISTR_AMT)  *  TAXRATE  *  0.01,  -1)  ELSE  TRUNC(((SUM(DISTR_AMT)  -  SUM(MNGCOMIS_AMT))  *  TAXRATE  *  0.01)  /  1.1,  -1)  END)  END)  AS  TAX1_AMT,  (CASE  WHEN  TRNSF_GBN  =  '1'   \n";
        query +=" AND  HOUSE_CD  =  '082'  THEN  TRUNC(SUM(DISTR_AMT)  *  3  *  0.01  *  0.1,  -1)  WHEN  TRNSF_GBN  =  '1'   \n";
        query +=" AND  HOUSE_CD  <>  '082'  THEN  (CASE  WHEN  INHABTAX_YN  =  0  THEN  TRUNC(SUM(DISTR_AMT)  *  TAXRATE  *  0.01  *  0.1,  -1)  ELSE  TRUNC((SUM(DISTR_AMT)  *  TAXRATE  *  0.01)  /  1.1  *  0.1,  -1)  END)  WHEN  TRNSF_GBN  =  '2'   \n";
        query +=" AND  HOUSE_CD  =  '082'  THEN  TRUNC((SUM(DISTR_AMT)  -  SUM(MNGCOMIS_AMT))  *  0.2  *  0.1,  -1)  WHEN  TRNSF_GBN  =  '2'   \n";
        query +=" AND  HOUSE_CD  <>  '082'  THEN  (CASE  WHEN  INHABTAX_YN  =  0  THEN  TRUNC(SUM(DISTR_AMT)  *  TAXRATE  *  0.01  *  0.1,  -1)  ELSE  TRUNC(((SUM(DISTR_AMT)  -  SUM(MNGCOMIS_AMT))  *  TAXRATE  *  0.01)  /  1.1  *  0.1,  -1)  END)  END)  AS  TAX2_AMT  FROM  (   \n";
        query +=" SELECT  A.MB_CD,  A.TRNSF_GBN,  A.SUPP_YRMN,  A.TAXRATE,  A.INHABTAX_YN,   \n";
        query +=" (SELECT  HOUSE_CD  FROM  FIDU.TMEM_MB  WHERE  MB_CD  =  A.MB_CD)  AS  HOUSE_CD,  A.DISTR_AMT,  TRUNC(A.DISTR_AMT  *  A.MNGCOMIS_RATE  *  0.01)  AS  MNGCOMIS_AMT  FROM  FIDU.TTAC_CALCSUPPAMT_IMSI  A  WHERE  1=1  )  GROUP  BY  MB_CD,  TRNSF_GBN,  SUPP_YRMN,  TAXRATE,  INHABTAX_YN,  HOUSE_CD  )  X  ORDER  BY  MB_CD,  TRNSF_GBN,  SUPP_YRMN ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$calc_realsupp
    //##**$$end
}
