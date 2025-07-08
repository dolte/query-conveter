
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac04_s02
{
    public tac04_s02()
    {
    }
    //##**$$searchMcAll
    /* * 프로그램명 : tac04_s02
    * 작성자 : 손주환
    * 작성일 : 2009/11/26
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchMcAll(DOBJ dobj)
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
            dobj  = CALLsearchMcAll_SEL1(Conn, dobj);           //  매체일괄조회
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
    public DOBJ CTLsearchMcAll( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchMcAll_SEL1(Conn, dobj);           //  매체일괄조회
        return dobj;
    }
    // 매체일괄조회
    public DOBJ CALLsearchMcAll_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMcAll_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMcAll_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.SUPP_YRMN,  A.MDM_CD,  A.MB_CD  AS  OBJ_MB_CD,  ''  AS  OBJ_GBN,  '  '  SUPP_MB_CD,  '  '  ADJ_GBN,  '  '  ADJ_REAS_CTENT,  SUM(A.ORGDISTR_AMT)  AS  ADJ_AMT,  SUM(A.ATAX_AMT)  ATAX_AMT,  '  '  AS  SUPP_MB_CD_NM,  FIDU.GET_MDM_NM(A.MDM_CD)AS  MDM_CD_NM  ,  B.HANMB_NM  AS  OBJ_MB_CD_NM  FROM  FIDU.TTAC_MDMCLASSSUPPAMT_TMP  A,  FIDU.TMEM_MB  B  WHERE  1  =  1   \n";
        query +=" AND  A.MB_CD=B.MB_CD   \n";
        query +=" AND  A.MB_CD=  :MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  :SUPP_YRMN   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  GROUP  BY  A.SUPP_YRMN,  A.MDM_CD,  A.MB_CD  ,  B.HANMB_NM ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$searchMcAll
    //##**$$saveMst
    /* * 프로그램명 : tac04_s02
    * 작성자 : 손주환
    * 작성일 : 2009/11/26
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsaveMst(DOBJ dobj)
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
            dobj  = CALLsaveMst_MIUD1(Conn, dobj);           //  회원분배저장
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
    public DOBJ CTLsaveMst( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsaveMst_MIUD1(Conn, dobj);           //  회원분배저장
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 회원분배저장
    public DOBJ CALLsaveMst_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLsaveMst_INS5(Conn, dobj);           //  저장
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsaveMst_DEL9(Conn, dobj);           //  삭제
                dobj  = CALLsaveMst_INS10(Conn, dobj);           //  저장
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsaveMst_DEL7(Conn, dobj);           //  삭제
            }
        }
        return dobj;
    }
    // 삭제
    public DOBJ CALLsaveMst_DEL7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsaveMst_DEL7(dobj, dvobj);
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
    private SQLObject SQLsaveMst_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        String   SUPP_MB_CD = dvobj.getRecord().get("SUPP_MB_CD");   //지급 회원 코드
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //매체 코드
        String   ADJ_GBN = dvobj.getRecord().get("ADJ_GBN");   //조정 구분
        String   OBJ_MB_CD = dvobj.getRecord().get("OBJ_MB_CD");   //대상 회원 코드
        String   OBJ_GBN = dvobj.getRecord().get("OBJ_GBN");
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_MBDISTRAMTADJ  \n";
        query +=" where OBJ_GBN=:OBJ_GBN  \n";
        query +=" and OBJ_MB_CD=:OBJ_MB_CD  \n";
        query +=" and ADJ_GBN=:ADJ_GBN  \n";
        query +=" and MDM_CD=:MDM_CD  \n";
        query +=" and SUPP_MB_CD=:SUPP_MB_CD  \n";
        query +=" and SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("SUPP_MB_CD", SUPP_MB_CD);               //지급 회원 코드
        sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        sobj.setString("ADJ_GBN", ADJ_GBN);               //조정 구분
        sobj.setString("OBJ_MB_CD", OBJ_MB_CD);               //대상 회원 코드
        sobj.setString("OBJ_GBN", OBJ_GBN);
        return sobj;
    }
    // 저장
    public DOBJ CALLsaveMst_INS5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsaveMst_INS5(dobj, dvobj);
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
    private SQLObject SQLsaveMst_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String MOD_DATE ="";        //수정 일시
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        String   SUPP_MB_CD = dvobj.getRecord().get("SUPP_MB_CD");   //지급 회원 코드
        String   ADJ_GBN = dvobj.getRecord().get("ADJ_GBN");   //조정 구분
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //매체 코드
        String   OBJ_GBN = dvobj.getRecord().get("OBJ_GBN");
        double   ATAX_AMT = dvobj.getRecord().getDouble("ATAX_AMT");   //부가세 금액
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //조정 금액
        String   OBJ_MB_CD = dvobj.getRecord().get("OBJ_MB_CD");   //대상 회원 코드
        String   ADJ_REAS_CTENT = dvobj.getRecord().get("ADJ_REAS_CTENT");   //조정 사유 내용
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_MBDISTRAMTADJ (MODPRES_ID, INSPRES_ID, ADJ_REAS_CTENT, OBJ_MB_CD, ADJ_AMT, ATAX_AMT, OBJ_GBN, INS_DATE, MDM_CD, ADJ_GBN, MOD_DATE, SUPP_MB_CD, SUPP_YRMN)  \n";
        query +=" values(:MODPRES_ID , :INSPRES_ID , :ADJ_REAS_CTENT , :OBJ_MB_CD , :ADJ_AMT , :ATAX_AMT , :OBJ_GBN , TO_CHAR(SYSDATE,'YYYYMMDD'), :MDM_CD , :ADJ_GBN , TO_CHAR(SYSDATE,'YYYYMMDD'), :SUPP_MB_CD , :SUPP_YRMN )";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("SUPP_MB_CD", SUPP_MB_CD);               //지급 회원 코드
        sobj.setString("ADJ_GBN", ADJ_GBN);               //조정 구분
        sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        sobj.setString("OBJ_GBN", OBJ_GBN);
        sobj.setDouble("ATAX_AMT", ATAX_AMT);               //부가세 금액
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //조정 금액
        sobj.setString("OBJ_MB_CD", OBJ_MB_CD);               //대상 회원 코드
        sobj.setString("ADJ_REAS_CTENT", ADJ_REAS_CTENT);               //조정 사유 내용
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 삭제
    public DOBJ CALLsaveMst_DEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("DEL9");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveMst_DEL9(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeSqlUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL9") ;
        rvobj.Println("DEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveMst_DEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        String   SUPP_MB_CD = dvobj.getRecord().get("SUPP_MB_CD");   //지급 회원 코드
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //매체 코드
        String   ADJ_GBN = dvobj.getRecord().get("ADJ_GBN");   //조정 구분
        String   OBJ_MB_CD = dvobj.getRecord().get("OBJ_MB_CD");   //대상 회원 코드
        String   OBJ_GBN = dvobj.getRecord().get("OBJ_GBN");
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_MBDISTRAMTADJ  \n";
        query +=" where OBJ_GBN=:OBJ_GBN  \n";
        query +=" and OBJ_MB_CD=:OBJ_MB_CD  \n";
        query +=" and ADJ_GBN=:ADJ_GBN  \n";
        query +=" and MDM_CD=:MDM_CD  \n";
        query +=" and SUPP_MB_CD=:SUPP_MB_CD  \n";
        query +=" and SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("SUPP_MB_CD", SUPP_MB_CD);               //지급 회원 코드
        sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        sobj.setString("ADJ_GBN", ADJ_GBN);               //조정 구분
        sobj.setString("OBJ_MB_CD", OBJ_MB_CD);               //대상 회원 코드
        sobj.setString("OBJ_GBN", OBJ_GBN);
        return sobj;
    }
    // 저장
    public DOBJ CALLsaveMst_INS10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS10");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveMst_INS10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS10") ;
        rvobj.Println("INS10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveMst_INS10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String MOD_DATE ="";        //수정 일시
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        String   SUPP_MB_CD = dvobj.getRecord().get("SUPP_MB_CD");   //지급 회원 코드
        String   ADJ_GBN = dvobj.getRecord().get("ADJ_GBN");   //조정 구분
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //매체 코드
        String   OBJ_GBN = dvobj.getRecord().get("OBJ_GBN");
        double   ATAX_AMT = dvobj.getRecord().getDouble("ATAX_AMT");   //부가세 금액
        double   ADJ_AMT = dvobj.getRecord().getDouble("ADJ_AMT");   //조정 금액
        String   OBJ_MB_CD = dvobj.getRecord().get("OBJ_MB_CD");   //대상 회원 코드
        String   ADJ_REAS_CTENT = dvobj.getRecord().get("ADJ_REAS_CTENT");   //조정 사유 내용
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_MBDISTRAMTADJ (MODPRES_ID, INSPRES_ID, ADJ_REAS_CTENT, OBJ_MB_CD, ADJ_AMT, ATAX_AMT, OBJ_GBN, INS_DATE, MDM_CD, ADJ_GBN, MOD_DATE, SUPP_MB_CD, SUPP_YRMN)  \n";
        query +=" values(:MODPRES_ID , :INSPRES_ID , :ADJ_REAS_CTENT , :OBJ_MB_CD , :ADJ_AMT , :ATAX_AMT , :OBJ_GBN , TO_CHAR(SYSDATE,'YYYYMMDD'), :MDM_CD , :ADJ_GBN , TO_CHAR(SYSDATE,'YYYYMMDD'), :SUPP_MB_CD , :SUPP_YRMN )";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("SUPP_MB_CD", SUPP_MB_CD);               //지급 회원 코드
        sobj.setString("ADJ_GBN", ADJ_GBN);               //조정 구분
        sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        sobj.setString("OBJ_GBN", OBJ_GBN);
        sobj.setDouble("ATAX_AMT", ATAX_AMT);               //부가세 금액
        sobj.setDouble("ADJ_AMT", ADJ_AMT);               //조정 금액
        sobj.setString("OBJ_MB_CD", OBJ_MB_CD);               //대상 회원 코드
        sobj.setString("ADJ_REAS_CTENT", ADJ_REAS_CTENT);               //조정 사유 내용
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    //##**$$saveMst
    //##**$$searchDetail
    /* * 프로그램명 : tac04_s02
    * 작성자 : 손주환
    * 작성일 : 2009/11/26
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchDetail(DOBJ dobj)
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
            dobj  = CALLsearchDetail_SEL1(Conn, dobj);           //  회원분배금액조회디테일
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
    public DOBJ CTLsearchDetail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchDetail_SEL1(Conn, dobj);           //  회원분배금액조회디테일
        return dobj;
    }
    // 회원분배금액조회디테일
    public DOBJ CALLsearchDetail_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchDetail_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchDetail_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   MDM_CD = dobj.getRetObject("S").getRecord().get("MDM_CD");   //매체 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.SUPP_YRMN,  A.MDM_CD,  A.OBJ_MB_CD,  A.SUPP_MB_CD,  A.ADJ_GBN,  A.OBJ_GBN,  A.ADJ_AMT,  A.ATAX_AMT,  A.ADJ_REAS_CTENT,  FIDU.GET_MDM_NM(A.MDM_CD)  AS  MDM_CD_NM,  FIDU.GET_MB_NM(A.OBJ_MB_CD)  AS  OBJ_MB_CD_NM,  FIDU.GET_MB_NM(A.SUPP_MB_CD)  AS  SUPP_MB_CD_NM,  A.INSPRES_ID,  A.MODPRES_ID,  A.INS_DATE,  A.MOD_DATE  FROM  FIDU.TTAC_MBDISTRAMTADJ  A  WHERE  A.MDM_CD=:MDM_CD   \n";
        query +=" AND  A.SUPP_YRMN=:SUPP_YRMN   \n";
        query +=" AND  A.OBJ_MB_CD=:MB_CD  ORDER  BY  A.SUPP_MB_CD  ASC ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$searchDetail
    //##**$$searchMst
    /* * 프로그램명 : tac04_s02
    * 작성자 : 손주환
    * 작성일 : 2009/11/26
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchMst(DOBJ dobj)
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
            dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  회원분배금액조회마스타
            dobj  = CALLsearchMst_SEL2(Conn, dobj);           //  분배금액조정내역 조회
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
    public DOBJ CTLsearchMst( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  회원분배금액조회마스타
        dobj  = CALLsearchMst_SEL2(Conn, dobj);           //  분배금액조정내역 조회
        return dobj;
    }
    // 회원분배금액조회마스타
    public DOBJ CALLsearchMst_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   MDM_CD_ALL = dobj.getRetObject("S").getRecord().get("MDM_CD_ALL");   //매체코드 대.중분류 검색값
        String   MDM_CD = dobj.getRetObject("S").getRecord().get("MDM_CD");   //매체 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.SUPP_YRMN, A.MB_CD AS RIGHTPRES_MB_CD, B.HANMB_NM, A.MDM_CD, C.MDM_CD_NM, SUM(A.ORGDISTR_AMT) AS DISTR_AMT, SUM(A.ATAX_AMT) ATAX_AMT , A.TRNSF_GBN  ";
        query +=" FROM FIDU.TTAC_MDMCLASSSUPPAMT_TMP A, FIDU.TMEM_MB B, FIDU.TENV_MDMCD C  ";
        query +=" WHERE A.MB_CD=B.MB_CD  ";
        query +=" AND A.MDM_CD=C.MDM_CD  ";
        query +=" AND A.SUPP_YRMN = :SUPP_YRMN  ";
        if( !TRNSF_GBN.equals("%"))
        {
            query +=" AND A.TRNSF_GBN = :TRNSF_GBN  ";
        }
        if( !MDM_CD.equals("%"))
        {
            query +=" AND A.MDM_CD = :MDM_CD  ";
        }
        if( !MDM_CD_ALL.equals("%"))
        {
            query +=" AND A.MDM_CD LIKE :MDM_CD_ALL||'%'  ";
        }
        if( !MB_CD.equals("%"))
        {
            query +=" AND A.MB_CD = :MB_CD  ";
        }
        query +=" GROUP BY A.MDM_CD, A.SUPP_YRMN, A.MB_CD, B.HANMB_NM, C.MDM_CD_NM, A.TRNSF_GBN  ";
        query +=" ORDER BY B.HANMB_NM ,A.MDM_CD  ";
        sobj.setSql(query);
        if( !MB_CD.equals("%"))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        if( !TRNSF_GBN.equals("%"))
        {
            sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        }
        if( !MDM_CD_ALL.equals("%"))
        {
            sobj.setString("MDM_CD_ALL", MDM_CD_ALL);               //매체코드 대.중분류 검색값
        }
        if( !MDM_CD.equals("%"))
        {
            sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        }
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 분배금액조정내역 조회
    public DOBJ CALLsearchMst_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MDM_CD,  OBJ_MB_CD,  OBJ_GBN,  B.HANMB_NM  OBJ_MB_CD_NM,  SUPP_MB_CD,  ADJ_GBN,  C.HANMB_NM  SUPP_MB_CD_NM,  ADJ_AMT,  ADJ_REAS_CTENT,  ATAX_AMT,  D.MDM_CD_NM  FROM  FIDU.TTAC_MBDISTRAMTADJ  A,  FIDU.TMEM_MB  B,  FIDU.TMEM_MB  C,  FIDU.TENV_MDMCD  D  WHERE  SUPP_YRMN  =  :SUPP_YRMN   \n";
        query +=" AND  OBJ_MB_CD  =:MB_CD   \n";
        query +=" AND  OBJ_GBN  =  :TRNSF_GBN   \n";
        query +=" AND  A.OBJ_MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_MB_CD  =  C.MB_CD   \n";
        query +=" AND  A.MDM_CD  =  D.MDM_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$searchMst
    //##**$$insert_jungpark
    /*
    */
    public DOBJ CTLinsert_jungpark(DOBJ dobj)
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
            dobj  = CALLinsert_jungpark_SEL1(Conn, dobj);           //  중복확인
            if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLinsert_jungpark_XIUD3(Conn, dobj);           //  등록
                dobj  = CALLinsert_jungpark_SEL4(Conn, dobj);           //  중복확인
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
    public DOBJ CTLinsert_jungpark( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLinsert_jungpark_SEL1(Conn, dobj);           //  중복확인
        if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLinsert_jungpark_XIUD3(Conn, dobj);           //  등록
            dobj  = CALLinsert_jungpark_SEL4(Conn, dobj);           //  중복확인
        }
        return dobj;
    }
    // 중복확인
    public DOBJ CALLinsert_jungpark_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLinsert_jungpark_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinsert_jungpark_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  FIDU.TTAC_MBDISTRAMTADJ  WHERE  OBJ_MB_CD  =  'W0060400'   \n";
        query +=" AND  SUPP_MB_CD  =  'W0144100'   \n";
        query +=" AND  ADJ_REAS_CTENT  =  '회계팀-6966(2018.09.19.)'   \n";
        query +=" AND  SUPP_YRMN  =  :SUPP_YRMN ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 등록
    public DOBJ CALLinsert_jungpark_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD3");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLinsert_jungpark_XIUD3(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        rvobj.Println("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinsert_jungpark_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.TTAC_MBDISTRAMTADJ (SUPP_YRMN, MDM_CD, OBJ_MB_CD, OBJ_GBN, SUPP_MB_CD, ADJ_GBN, ADJ_AMT, ADJ_REAS_CTENT, INSPRES_ID, INS_DATE, MODPRES_ID, MOD_DATE, ATAX_AMT) SELECT :SUPP_YRMN, MDM_CD, 'W0060400', '1', 'W0144100', '1', TRUNC(SUM(DISTR_AMT)), '회계팀-6966(2018.09.19.)', :INSPRES_ID, SYSDATE, :INSPRES_ID, SYSDATE, 0 from FIDU.TDIS_DISTR A, (SELECT '100000372105' AS PROD_CD FROM DUAL UNION ALL SELECT '100000681161' AS PROD_CD FROM DUAL UNION ALL SELECT '100001601393' AS PROD_CD FROM DUAL UNION ALL SELECT '100000372107' AS PROD_CD FROM DUAL UNION ALL SELECT '001000072328' AS PROD_CD FROM DUAL UNION ALL SELECT '100001601392' AS PROD_CD FROM DUAL UNION ALL SELECT '001001135699' AS PROD_CD FROM DUAL UNION ALL SELECT '001001074505' AS PROD_CD FROM DUAL ) B where A.PROD_CD = B.PROD_CD AND WRK_YRMN = :SUPP_YRMN AND RIGHTPRES_MB_CD = 'W0060400' AND ORGAU_MB_CD = 'W0060400' AND SUBSTR(MDM_CD,1,1) in ('B', 'C') GROUP BY MDM_CD";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 중복확인
    public DOBJ CALLinsert_jungpark_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLinsert_jungpark_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinsert_jungpark_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  FIDU.TTAC_MBDISTRAMTADJ  WHERE  OBJ_MB_CD  =  'W0060400'   \n";
        query +=" AND  SUPP_MB_CD  =  'W0144100'   \n";
        query +=" AND  ADJ_REAS_CTENT  =  '회계팀-6966(2018.09.19.)'   \n";
        query +=" AND  SUPP_YRMN  =  :SUPP_YRMN ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$insert_jungpark
    //##**$$tac10_p01_select01
    /* * 프로그램명 : tac04_s02
    * 작성자 : 999999
    * 작성일 : 2009/08/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac10_p01_select01(DOBJ dobj)
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
            dobj  = CALLtac10_p01_select01_SEL1(Conn, dobj);           //  회원미지급내역(월단위)
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
    public DOBJ CTLtac10_p01_select01( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_p01_select01_SEL1(Conn, dobj);           //  회원미지급내역(월단위)
        return dobj;
    }
    // 회원미지급내역(월단위)
    public DOBJ CALLtac10_p01_select01_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_p01_select01_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_p01_select01_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_FR_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_FR_YRMN");   //SUPP_FR_YRMN
        String   HANMB_NM = dobj.getRetObject("S").getRecord().get("HANMB_NM");   //회원 거래처 코드
        String   SUPP_TO_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_TO_YRMN");   //SUPP_TO_YRMN
        String   RIGHTPRES_MB_CD = dobj.getRetObject("S").getRecord().get("RIGHTPRES_MB_CD");   //권리자 회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.SUPP_YRMN,  A.RIGHTPRES_MB_CD,  B.HANMB_NM,  C.SN,  A.MDM_CD,  D.MDM_CD_NM,  SUM(A.DISTR_AMT)AS  DISTR_AMT  FROM  FIDU.TDIS_DISTR  A,  FIDU.TMEM_MB  B,  FIDU.TMEM_SN  C,  FIDU.TENV_MDMCD  D  WHERE  A.SUSP_GBN  =  '2'   \n";
        query +=" AND  A.SUPP_GBN  =  '2'   \n";
        query +=" AND  A.RIGHTPRES_MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.RIGHTPRES_MB_CD  =  C.MB_CD   \n";
        query +=" AND  '03'  =  C.SN_MNG_NUM   \n";
        query +=" AND  A.MDM_CD  =  D.MDM_CD   \n";
        query +=" AND  A.RIGHTPRES_MB_CD  LIKE  :RIGHTPRES_MB_CD  ||  '%'   \n";
        query +=" AND  B.HANMB_NM  LIKE  '%'  ||  :HANMB_NM  ||  '%'   \n";
        query +=" AND  A.SUPP_YRMN  >=  :SUPP_FR_YRMN   \n";
        query +=" AND  A.SUPP_YRMN  <=  :SUPP_TO_YRMN  GROUP  BY  A.SUPP_YRMN,  A.RIGHTPRES_MB_CD,  B.HANMB_NM,  C.SN,  A.MDM_CD,  D.MDM_CD_NM  ORDER  BY  A.SUPP_YRMN,  A.RIGHTPRES_MB_CD ";
        sobj.setSql(query);
        sobj.setString("SUPP_FR_YRMN", SUPP_FR_YRMN);               //SUPP_FR_YRMN
        sobj.setString("HANMB_NM", HANMB_NM);               //회원 거래처 코드
        sobj.setString("SUPP_TO_YRMN", SUPP_TO_YRMN);               //SUPP_TO_YRMN
        sobj.setString("RIGHTPRES_MB_CD", RIGHTPRES_MB_CD);               //권리자 회원코드
        return sobj;
    }
    //##**$$tac10_p01_select01
    //##**$$tac10_p01_select02
    /* * 프로그램명 : tac04_s02
    * 작성자 : 999999
    * 작성일 : 2009/08/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac10_p01_select02(DOBJ dobj)
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
            dobj  = CALLtac10_p01_select02_SEL1(Conn, dobj);           //  회원미지급내역조회(합계)
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
    public DOBJ CTLtac10_p01_select02( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_p01_select02_SEL1(Conn, dobj);           //  회원미지급내역조회(합계)
        return dobj;
    }
    // 회원미지급내역조회(합계)
    public DOBJ CALLtac10_p01_select02_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_p01_select02_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_p01_select02_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   HANMB_NM = dobj.getRetObject("S").getRecord().get("HANMB_NM");   //회원 거래처 코드
        String   RIGHTPRES_MB_CD = dobj.getRetObject("S").getRecord().get("RIGHTPRES_MB_CD");   //권리자 회원코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  'A.권리자회원코드'  AS  RIGHTPRES_MB_CD,  B.HANMB_NM,  C.SN,  A.MDM_CD,  D.MDM_CD_NM,  SUM(A.DISTR_AMT)AS  DISTR_AMT  FROM  FIDU.TDIS_DISTR  A,  FIDU.TMEM_MB  B,  FIDU.TMEM_SN  C,  FIDU.TENV_MDMCD  D  WHERE  A.SUSP_GBN  =  '2'   \n";
        query +=" AND  A.SUPP_GBN  =  '2'   \n";
        query +=" AND  A.RIGHTPRES_MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.RIGHTPRES_MB_CD  =  C.MB_CD   \n";
        query +=" AND  '03'  =  C.SN_MNG_NUM   \n";
        query +=" AND  A.MDM_CD  =  D.MDM_CD   \n";
        query +=" AND  A.RIGHTPRES_MB_CD  LIKE  :RIGHTPRES_MB_CD  ||  '%'   \n";
        query +=" AND  B.HANMB_NM  LIKE  '%'  ||  :HANMB_NM  ||  '%'   \n";
        query +=" AND  SUBSTR(A.SUPP_YRMN,1,4)  =  :SUPP_YRMN  GROUP  BY  A.RIGHTPRES_MB_CD,  B.HANMB_NM,  C.SN,  A.MDM_CD,  D.MDM_CD_NM  ORDER  BY  A.RIGHTPRES_MB_CD ";
        sobj.setSql(query);
        sobj.setString("HANMB_NM", HANMB_NM);               //회원 거래처 코드
        sobj.setString("RIGHTPRES_MB_CD", RIGHTPRES_MB_CD);               //권리자 회원코드
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$tac10_p01_select02
    //##**$$insert_yunkim
    /*
    */
    public DOBJ CTLinsert_yunkim(DOBJ dobj)
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
            dobj  = CALLinsert_yunkim_SEL1(Conn, dobj);           //  중복확인
            if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLinsert_yunkim_XIUD3(Conn, dobj);           //  등록
                dobj  = CALLinsert_yunkim_SEL4(Conn, dobj);           //  중복확인
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
    public DOBJ CTLinsert_yunkim( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLinsert_yunkim_SEL1(Conn, dobj);           //  중복확인
        if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLinsert_yunkim_XIUD3(Conn, dobj);           //  등록
            dobj  = CALLinsert_yunkim_SEL4(Conn, dobj);           //  중복확인
        }
        return dobj;
    }
    // 중복확인
    public DOBJ CALLinsert_yunkim_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLinsert_yunkim_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinsert_yunkim_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  FIDU.TTAC_MBDISTRAMTADJ  WHERE  OBJ_MB_CD  =  'W0537700'   \n";
        query +=" AND  SUPP_MB_CD  =  'W0436600'   \n";
        query +=" AND  ADJ_REAS_CTENT  =  '회계팀-9445(2020.09.24.)'   \n";
        query +=" AND  SUPP_YRMN  =  :SUPP_YRMN ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 등록
    public DOBJ CALLinsert_yunkim_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD3");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLinsert_yunkim_XIUD3(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        rvobj.Println("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinsert_yunkim_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.TTAC_MBDISTRAMTADJ (SUPP_YRMN, MDM_CD, OBJ_MB_CD, OBJ_GBN, SUPP_MB_CD, ADJ_GBN, ADJ_AMT, ADJ_REAS_CTENT, INSPRES_ID, INS_DATE, MODPRES_ID, MOD_DATE, ATAX_AMT) SELECT :SUPP_YRMN, MDM_CD, 'W0537700', '1', 'W0436600', '2', TRUNC(SUM(DISTR_AMT)), '회계팀-9445(2020.09.24.)', :INSPRES_ID, SYSDATE, :INSPRES_ID, SYSDATE, 0 from FIDU.TDIS_DISTR A, (SELECT PROD_CD FROM FIDU.TOPU_PROD WHERE PROD_CD IN ( '001001071322', '001001077555', '001001077634', '001001077635', '001001077636', '001001078089', '001001085905', '001001088301', '001001103714', '001001115951', '001001115955', '001001115957', '001001115958', '001001116831', '001001132213', '001001132215', '001001135082', '001001143612', '001001143623', '001001143624', '007000005283', '007000005284', '007000005286', '007000005288', '007000005290', '007000012189', '007000012192', '100000100891', '100000100895', '100000194129', '100000198995', '100000198998', '100000199001', '100000323373', '100000323376', '100000323383', '100000575505', '100000575513', '100000614857', '100000623203', '100000623206', '100000623211', '100000849176', '100001121893', '100001168735', '100001340306', '100001348222', '100001348223', '100001348224', '100001348300', '100001425719', '100001581540', '100001632398', '100002321291', '100002360803', '100002364547', '100002370437', '100002392317', '100002417442', '100002417446', '100002511836', '100002514511', '100002525733', '100002754657', '100002364544') ) B WHERE A.PROD_CD = B.PROD_CD AND WRK_YRMN = :SUPP_YRMN AND RIGHTPRES_MB_CD = 'W0537700' AND ORGAU_MB_CD = 'W0537700' GROUP BY MDM_CD";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 중복확인
    public DOBJ CALLinsert_yunkim_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLinsert_yunkim_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinsert_yunkim_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  FIDU.TTAC_MBDISTRAMTADJ  WHERE  OBJ_MB_CD  =  'W0537700'   \n";
        query +=" AND  SUPP_MB_CD  =  'W0436600'   \n";
        query +=" AND  ADJ_REAS_CTENT  =  '회계팀-9445(2020.09.24.)'   \n";
        query +=" AND  SUPP_YRMN  =  :SUPP_YRMN ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$insert_yunkim
    //##**$$insert_kanglee
    /*
    */
    public DOBJ CTLinsert_kanglee(DOBJ dobj)
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
            dobj  = CALLinsert_kanglee_SEL1(Conn, dobj);           //  중복확인
            if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLinsert_kanglee_XIUD3(Conn, dobj);           //  등록
                dobj  = CALLinsert_kanglee_SEL4(Conn, dobj);           //  중복확인
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
    public DOBJ CTLinsert_kanglee( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLinsert_kanglee_SEL1(Conn, dobj);           //  중복확인
        if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLinsert_kanglee_XIUD3(Conn, dobj);           //  등록
            dobj  = CALLinsert_kanglee_SEL4(Conn, dobj);           //  중복확인
        }
        return dobj;
    }
    // 중복확인
    public DOBJ CALLinsert_kanglee_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLinsert_kanglee_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinsert_kanglee_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  FIDU.TTAC_MBDISTRAMTADJ  WHERE  OBJ_MB_CD  =  'W0003000'   \n";
        query +=" AND  SUPP_MB_CD  =  '10029092'   \n";
        query +=" AND  ADJ_REAS_CTENT  =  '회계팀-2266(2021.03.12.)'   \n";
        query +=" AND  SUPP_YRMN  =  :SUPP_YRMN ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 등록
    public DOBJ CALLinsert_kanglee_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD3");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLinsert_kanglee_XIUD3(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        rvobj.Println("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinsert_kanglee_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.TTAC_MBDISTRAMTADJ (SUPP_YRMN, MDM_CD, OBJ_MB_CD, OBJ_GBN, SUPP_MB_CD, ADJ_GBN, ADJ_AMT, ADJ_REAS_CTENT, INSPRES_ID, INS_DATE, MODPRES_ID, MOD_DATE, ATAX_AMT) SELECT :SUPP_YRMN, MDM_CD, 'W0003000', '1', '10029092', '2', TRUNC(SUM(DISTR_AMT)), '회계팀-2266(2021.03.12.)', :INSPRES_ID, SYSDATE, :INSPRES_ID, SYSDATE, 0 from FIDU.TDIS_DISTR A, ( SELECT '001000001738' AS PROD_CD FROM DUAL UNION ALL SELECT '001000003233' AS PROD_CD FROM DUAL UNION ALL SELECT '001000003630' AS PROD_CD FROM DUAL UNION ALL SELECT '001000050165' AS PROD_CD FROM DUAL UNION ALL SELECT '001000004349' AS PROD_CD FROM DUAL UNION ALL SELECT '001001082306' AS PROD_CD FROM DUAL UNION ALL SELECT '001000004640' AS PROD_CD FROM DUAL UNION ALL SELECT '001000004738' AS PROD_CD FROM DUAL UNION ALL SELECT '001000004841' AS PROD_CD FROM DUAL UNION ALL SELECT '001001186008' AS PROD_CD FROM DUAL UNION ALL SELECT '001000005914' AS PROD_CD FROM DUAL UNION ALL SELECT '001000006524' AS PROD_CD FROM DUAL UNION ALL SELECT '001000006544' AS PROD_CD FROM DUAL UNION ALL SELECT '001001097817' AS PROD_CD FROM DUAL UNION ALL SELECT '001000006545' AS PROD_CD FROM DUAL UNION ALL SELECT '001000072629' AS PROD_CD FROM DUAL UNION ALL SELECT '001000006643' AS PROD_CD FROM DUAL UNION ALL SELECT '001000082971' AS PROD_CD FROM DUAL UNION ALL SELECT '001000006793' AS PROD_CD FROM DUAL UNION ALL SELECT '001000006807' AS PROD_CD FROM DUAL UNION ALL SELECT '001001096223' AS PROD_CD FROM DUAL UNION ALL SELECT '001001014004' AS PROD_CD FROM DUAL UNION ALL SELECT '001001102243' AS PROD_CD FROM DUAL UNION ALL SELECT '001001102245' AS PROD_CD FROM DUAL UNION ALL SELECT '001000008258' AS PROD_CD FROM DUAL UNION ALL SELECT '001000008257' AS PROD_CD FROM DUAL UNION ALL SELECT '001000008300' AS PROD_CD FROM DUAL UNION ALL SELECT '001001096233' AS PROD_CD FROM DUAL UNION ALL SELECT '005000016292' AS PROD_CD FROM DUAL UNION ALL SELECT '001000011054' AS PROD_CD FROM DUAL UNION ALL SELECT '001000011474' AS PROD_CD FROM DUAL UNION ALL SELECT '001001096229' AS PROD_CD FROM DUAL UNION ALL SELECT '005000016293' AS PROD_CD FROM DUAL UNION ALL SELECT '001000012598' AS PROD_CD FROM DUAL UNION ALL SELECT '100000796326' AS PROD_CD FROM DUAL UNION ALL SELECT '001000012972' AS PROD_CD FROM DUAL UNION ALL SELECT '001000013257' AS PROD_CD FROM DUAL UNION ALL SELECT '001000064151' AS PROD_CD FROM DUAL UNION ALL SELECT '001001094656' AS PROD_CD FROM DUAL UNION ALL SELECT '100003070323' AS PROD_CD FROM DUAL UNION ALL SELECT '100000003704' AS PROD_CD FROM DUAL UNION ALL SELECT '001000064159' AS PROD_CD FROM DUAL UNION ALL SELECT '001000015956' AS PROD_CD FROM DUAL UNION ALL SELECT '001001096231' AS PROD_CD FROM DUAL UNION ALL SELECT '001000016097' AS PROD_CD FROM DUAL UNION ALL SELECT '100000796323' AS PROD_CD FROM DUAL UNION ALL SELECT '001000020692' AS PROD_CD FROM DUAL UNION ALL SELECT '001000064158' AS PROD_CD FROM DUAL UNION ALL SELECT '100000536789' AS PROD_CD FROM DUAL UNION ALL SELECT '001000021469' AS PROD_CD FROM DUAL UNION ALL SELECT '001000021466' AS PROD_CD FROM DUAL UNION ALL SELECT '001000021467' AS PROD_CD FROM DUAL UNION ALL SELECT '001000021468' AS PROD_CD FROM DUAL UNION ALL SELECT '001000021470' AS PROD_CD FROM DUAL UNION ALL SELECT '001000021471' AS PROD_CD FROM DUAL UNION ALL SELECT '001000021723' AS PROD_CD FROM DUAL UNION ALL SELECT '001000064154' AS PROD_CD FROM DUAL UNION ALL SELECT '001000023882' AS PROD_CD FROM DUAL UNION ALL SELECT '001001092303' AS PROD_CD FROM DUAL UNION ALL SELECT '001000023900' AS PROD_CD FROM DUAL UNION ALL SELECT '100002238338' AS PROD_CD FROM DUAL UNION ALL SELECT '100003340359' AS PROD_CD FROM DUAL UNION ALL SELECT '001000023959' AS PROD_CD FROM DUAL UNION ALL SELECT '100000002839' AS PROD_CD FROM DUAL UNION ALL SELECT '001000024849' AS PROD_CD FROM DUAL UNION ALL SELECT '001000064155' AS PROD_CD FROM DUAL UNION ALL SELECT '001000025101' AS PROD_CD FROM DUAL UNION ALL SELECT '100000003707' AS PROD_CD FROM DUAL UNION ALL SELECT '001001096218' AS PROD_CD FROM DUAL UNION ALL SELECT '001000025180' AS PROD_CD FROM DUAL UNION ALL SELECT '001000025181' AS PROD_CD FROM DUAL UNION ALL SELECT '001000025442' AS PROD_CD FROM DUAL UNION ALL SELECT '001000026093' AS PROD_CD FROM DUAL UNION ALL SELECT '001000026414' AS PROD_CD FROM DUAL UNION ALL SELECT '100000536787' AS PROD_CD FROM DUAL UNION ALL SELECT '001001096547' AS PROD_CD FROM DUAL UNION ALL SELECT '100000003710' AS PROD_CD FROM DUAL UNION ALL SELECT '100001693552' AS PROD_CD FROM DUAL UNION ALL SELECT '001000026877' AS PROD_CD FROM DUAL UNION ALL SELECT '001000026879' AS PROD_CD FROM DUAL UNION ALL SELECT '100000536791' AS PROD_CD FROM DUAL UNION ALL SELECT '100000536775' AS PROD_CD FROM DUAL UNION ALL SELECT '001000028086' AS PROD_CD FROM DUAL UNION ALL SELECT '001000028626' AS PROD_CD FROM DUAL UNION ALL SELECT '001000029118' AS PROD_CD FROM DUAL UNION ALL SELECT '001001096227' AS PROD_CD FROM DUAL UNION ALL SELECT '001000030224' AS PROD_CD FROM DUAL UNION ALL SELECT '001000031765' AS PROD_CD FROM DUAL UNION ALL SELECT '001000033398' AS PROD_CD FROM DUAL UNION ALL SELECT '001001094655' AS PROD_CD FROM DUAL UNION ALL SELECT '001001096225' AS PROD_CD FROM DUAL UNION ALL SELECT '001000034807' AS PROD_CD FROM DUAL UNION ALL SELECT '001000034806' AS PROD_CD FROM DUAL UNION ALL SELECT '001001096234' AS PROD_CD FROM DUAL UNION ALL SELECT '001000035474' AS PROD_CD FROM DUAL UNION ALL SELECT '001001082313' AS PROD_CD FROM DUAL UNION ALL SELECT '001000064153' AS PROD_CD FROM DUAL UNION ALL SELECT '100000003709' AS PROD_CD FROM DUAL UNION ALL SELECT '001000036103' AS PROD_CD FROM DUAL UNION ALL SELECT '001001096222' AS PROD_CD FROM DUAL UNION ALL SELECT '001000100083' AS PROD_CD FROM DUAL UNION ALL SELECT '001001081846' AS PROD_CD FROM DUAL UNION ALL SELECT '100000536792' AS PROD_CD FROM DUAL UNION ALL SELECT '001000037599' AS PROD_CD FROM DUAL UNION ALL SELECT '001000037722' AS PROD_CD FROM DUAL UNION ALL SELECT '001000037778' AS PROD_CD FROM DUAL UNION ALL SELECT '001000038605' AS PROD_CD FROM DUAL UNION ALL SELECT '001001096235' AS PROD_CD FROM DUAL UNION ALL SELECT '001001096230' AS PROD_CD FROM DUAL UNION ALL SELECT '001000039131' AS PROD_CD FROM DUAL UNION ALL SELECT '001000064157' AS PROD_CD FROM DUAL UNION ALL SELECT '001000040262' AS PROD_CD FROM DUAL UNION ALL SELECT '001000040299' AS PROD_CD FROM DUAL UNION ALL SELECT '100000536778' AS PROD_CD FROM DUAL UNION ALL SELECT '100002433232' AS PROD_CD FROM DUAL UNION ALL SELECT '001000040830' AS PROD_CD FROM DUAL UNION ALL SELECT '001000041592' AS PROD_CD FROM DUAL UNION ALL SELECT '100000003708' AS PROD_CD FROM DUAL UNION ALL SELECT '001000041593' AS PROD_CD FROM DUAL UNION ALL SELECT '100001204932' AS PROD_CD FROM DUAL UNION ALL SELECT '001000041817' AS PROD_CD FROM DUAL UNION ALL SELECT '001000041818' AS PROD_CD FROM DUAL UNION ALL SELECT '005000016294' AS PROD_CD FROM DUAL UNION ALL SELECT '005000016296' AS PROD_CD FROM DUAL UNION ALL SELECT '001000042123' AS PROD_CD FROM DUAL UNION ALL SELECT '001001096226' AS PROD_CD FROM DUAL UNION ALL SELECT '001000060758' AS PROD_CD FROM DUAL UNION ALL SELECT '001000043344' AS PROD_CD FROM DUAL UNION ALL SELECT '001001096171' AS PROD_CD FROM DUAL UNION ALL SELECT '001000043343' AS PROD_CD FROM DUAL UNION ALL SELECT '001001097815' AS PROD_CD FROM DUAL UNION ALL SELECT '001001097816' AS PROD_CD FROM DUAL UNION ALL SELECT '100000536788' AS PROD_CD FROM DUAL UNION ALL SELECT '001000044441' AS PROD_CD FROM DUAL UNION ALL SELECT '001000045503' AS PROD_CD FROM DUAL UNION ALL SELECT '001000064156' AS PROD_CD FROM DUAL UNION ALL SELECT '001000047332' AS PROD_CD FROM DUAL UNION ALL SELECT '001001096217' AS PROD_CD FROM DUAL UNION ALL SELECT '001000047333' AS PROD_CD FROM DUAL UNION ALL SELECT '001000047495' AS PROD_CD FROM DUAL UNION ALL SELECT '100003070324' AS PROD_CD FROM DUAL UNION ALL SELECT '100000003713' AS PROD_CD FROM DUAL UNION ALL SELECT '100000003711' AS PROD_CD FROM DUAL UNION ALL SELECT '001000047623' AS PROD_CD FROM DUAL ) B where A.PROD_CD = B.PROD_CD AND WRK_YRMN = :SUPP_YRMN AND RIGHTPRES_MB_CD = 'W0003000' AND ORGAU_MB_CD = 'W0003000' GROUP BY MDM_CD";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 중복확인
    // SEL1과 동일
    public DOBJ CALLinsert_kanglee_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLinsert_kanglee_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLinsert_kanglee_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  FIDU.TTAC_MBDISTRAMTADJ  WHERE  OBJ_MB_CD  =  'W0003000'   \n";
        query +=" AND  SUPP_MB_CD  =  '10029092'   \n";
        query +=" AND  ADJ_REAS_CTENT  =  '회계팀-2266(2021.03.12.)'   \n";
        query +=" AND  SUPP_YRMN  =  :SUPP_YRMN ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$insert_kanglee
    //##**$$end
}
