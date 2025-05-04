
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s04_1
{
    public bra01_s04_1()
    {
    }
    //##**$$closed_detail
    /* * 프로그램명 : bra01_s04
    * 작성자 : 서정재
    * 작성일 : 2009/11/11
    * 설명 : 해당 업소의 휴업/폐업 정보를 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLclosed_detail(DOBJ dobj)
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
            dobj  = CALLclosed_detail_SEL2(Conn, dobj);           //  휴업상세정보
            dobj  = CALLclosed_detail_SEL3(Conn, dobj);           //  폐업상세정보
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
    public DOBJ CTLclosed_detail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLclosed_detail_SEL2(Conn, dobj);           //  휴업상세정보
        dobj  = CALLclosed_detail_SEL3(Conn, dobj);           //  폐업상세정보
        return dobj;
    }
    // 휴업상세정보
    // 휴업구분이 14(폐업) 이 아닌 정보를 조회한다.
    public DOBJ CALLclosed_detail_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_detail_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_detail_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.CLSED_DAY  ,  A.CLSED_NUM  ,  A.CLSED_BRAN  ,  A.CLSED_GBN  ,  NVL(A.START_DAY,  A.START_YRMN  ||  '01')  AS  START_DAY  ,  NVL(A.END_DAY,  A.END_YRMN  ||  '01')  AS  END_DAY  ,  A.STTNT_DAY  ,  A.REMAK  ,  B.BRAN_CD  ,  A.ADJ_AMT  ,  A.ADJ_GBN  FROM  GIBU.TBRA_UPSO_CLSED  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  CLSED_GBN  NOT  IN  ('14',  '01',  '02',  '03',  '04') ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 폐업상세정보
    // 휴업구분이 14인 정보를 조회한다.
    public DOBJ CALLclosed_detail_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLclosed_detail_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_detail_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.CLSED_DAY  ,  A.CLSED_NUM  ,  A.CLSED_BRAN  ,  '14'  AS  CLSED_GBN  ,  A.CLSED_GBN  AS  GBN  ,  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  A.STTNT_DAY  ,  A.REMAK  ,  B.BRAN_CD  ,  A.ADJ_AMT  ,  A.ADJ_GBN  FROM  GIBU.TBRA_UPSO_CLSED  A,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  CLSED_GBN  IN  ('14','01','02','03','04') ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$closed_detail
    //##**$$get_adj_amt_closed
    /*
    */
    public DOBJ CTLget_adj_amt_closed(DOBJ dobj)
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
            dobj  = CALLget_adj_amt_closed_SEL1(Conn, dobj);           //  OCR, INDTN 조회
            if( dobj.getRetObject("SEL1").getRecord().get("GBN").equals("OCR"))
            {
                dobj  = CALLget_adj_amt_closed_SEL21(Conn, dobj);           //  정정금액 획득
                dobj  = CALLget_adj_amt_closed_MRG18( dobj);        //  머지
            }
            else if( dobj.getRetObject("SEL1").getRecord().get("GBN").equals("AUTO"))
            {
                dobj  = CALLget_adj_amt_closed_SEL22(Conn, dobj);           //  정정금액 획득
                dobj  = CALLget_adj_amt_closed_MRG18( dobj);        //  머지
            }
            else if( dobj.getRetObject("SEL1").getRecord().get("GBN").equals("INDTN"))
            {
                dobj  = CALLget_adj_amt_closed_SEL23(Conn, dobj);           //  정정금액 획득
                dobj  = CALLget_adj_amt_closed_MRG18( dobj);        //  머지
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
    public DOBJ CTLget_adj_amt_closed( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_adj_amt_closed_SEL1(Conn, dobj);           //  OCR, INDTN 조회
        if( dobj.getRetObject("SEL1").getRecord().get("GBN").equals("OCR"))
        {
            dobj  = CALLget_adj_amt_closed_SEL21(Conn, dobj);           //  정정금액 획득
            dobj  = CALLget_adj_amt_closed_MRG18( dobj);        //  머지
        }
        else if( dobj.getRetObject("SEL1").getRecord().get("GBN").equals("AUTO"))
        {
            dobj  = CALLget_adj_amt_closed_SEL22(Conn, dobj);           //  정정금액 획득
            dobj  = CALLget_adj_amt_closed_MRG18( dobj);        //  머지
        }
        else if( dobj.getRetObject("SEL1").getRecord().get("GBN").equals("INDTN"))
        {
            dobj  = CALLget_adj_amt_closed_SEL23(Conn, dobj);           //  정정금액 획득
            dobj  = CALLget_adj_amt_closed_MRG18( dobj);        //  머지
        }
        return dobj;
    }
    // OCR, INDTN 조회
    public DOBJ CALLget_adj_amt_closed_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_adj_amt_closed_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_adj_amt_closed_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GBN  ,  DEMD_YRMN  ,  INS_DATE  FROM  (   \n";
        query +=" SELECT  'OCR'  AS  GBN  ,  DEMD_YRMN  ,  INS_DATE  FROM  GIBU.TBRA_DEMD_OCR  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  TO_CHAR(INS_DATE,  'YYYYMM')  >=  TO_CHAR(ADD_MONTHS(SYSDATE,  -1),  'YYYYMM')  UNION  ALL   \n";
        query +=" SELECT  'INDTN'  AS  GBN  ,  DEMD_DAY  AS  DEMD_YRMN  ,  INS_DATE  FROM  GIBU.TBRA_DEMD_INDTN  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SUBSTR(DEMD_DAY,1,6)  =  END_YRMN   \n";
        query +=" AND  TO_CHAR(INS_DATE,  'YYYYMM')  >=  TO_CHAR(ADD_MONTHS(SYSDATE,  -1),  'YYYYMM')  UNION  ALL   \n";
        query +=" SELECT  'AUTO'  AS  GBN  ,  DEMD_YRMN  ,  INS_DATE  FROM  GIBU.TBRA_DEMD_AUTO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  PROC_GBN  IN  ('1','2')   \n";
        query +=" AND  TO_CHAR(INS_DATE,  'YYYYMM')  >=  TO_CHAR(ADD_MONTHS(SYSDATE,  -1),  'YYYYMM')  ORDER  BY  INS_DATE  DESC  )  WHERE  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 정정금액 획득
    public DOBJ CALLget_adj_amt_closed_SEL21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL21");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL21");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_adj_amt_closed_SEL21(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL21");
        rvobj.Println("SEL21");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_adj_amt_closed_SEL21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("SEL1").getRecord().get("DEMD_YRMN");   //청구 년월
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(SUM(DEMD_AMT),0)  AS  ADJ_AMT  FROM  GIBU.TBRA_DEMD_OCR_MM  WHERE  DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  START_YRMN  BETWEEN  SUBSTR(:START_YRMN,1,6)   \n";
        query +=" AND  SUBSTR(:END_YRMN,1,6) ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 머지
    public DOBJ CALLget_adj_amt_closed_MRG18(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG18");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL21, SEL22, SEL23","");
        rvobj.setName("MRG18") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 정정금액 획득
    public DOBJ CALLget_adj_amt_closed_SEL22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL22");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL22");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_adj_amt_closed_SEL22(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL22");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_adj_amt_closed_SEL22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("SEL1").getRecord().get("DEMD_YRMN");   //청구 년월
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(SUM(DEMD_AMT),0)  AS  ADJ_AMT  FROM  GIBU.TBRA_DEMD_AUTO_MM  WHERE  DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  START_YRMN  BETWEEN  SUBSTR(:START_YRMN,1,6)   \n";
        query +=" AND  SUBSTR(:END_YRMN,1,6) ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 정정금액 획득
    public DOBJ CALLget_adj_amt_closed_SEL23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL23");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL23");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_adj_amt_closed_SEL23(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL23");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_adj_amt_closed_SEL23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("SEL1").getRecord().get("DEMD_YRMN");   //청구 년월
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(SUM(DEMD_AMT),0)  AS  ADJ_AMT  FROM  GIBU.TBRA_DEMD_INDTN_MM  WHERE  DEMD_DAY  =  :DEMD_YRMN   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  START_YRMN  BETWEEN  SUBSTR(:START_YRMN,1,6)   \n";
        query +=" AND  SUBSTR(:END_YRMN,1,6) ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    //##**$$get_adj_amt_closed
    //##**$$search_last_demd
    /*
    */
    public DOBJ CTLsearch_last_demd(DOBJ dobj)
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
            dobj  = CALLsearch_last_demd_SEL1(Conn, dobj);           //  마지막 청구월 획
            dobj  = CALLsearch_last_demd_SEL2(Conn, dobj);           //  청구구분 확보
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
    public DOBJ CTLsearch_last_demd( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_last_demd_SEL1(Conn, dobj);           //  마지막 청구월 획
        dobj  = CALLsearch_last_demd_SEL2(Conn, dobj);           //  청구구분 확보
        return dobj;
    }
    // 마지막 청구월 획
    public DOBJ CALLsearch_last_demd_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_last_demd_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_last_demd_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(DEMD_YRMN)  AS  LAST_DEMD_YRMN  FROM  GIBU.TBRA_DEMD_OCR ";
        sobj.setSql(query);
        return sobj;
    }
    // 청구구분 확보
    public DOBJ CALLsearch_last_demd_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_last_demd_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_last_demd_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("SEL1").getRecord().get("LAST_DEMD_YRMN");   //청구 년월
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GBN  ,  INS_DATE,  PRINT_GBN  FROM  (   \n";
        query +=" SELECT  'OCR'  AS  GBN  ,  INS_DATE  ,  NVL(OCR_PRNT,'0')  AS  PRINT_GBN  FROM  GIBU.TBRA_DEMD_OCR  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  :DEMD_YRMN  UNION  ALL   \n";
        query +=" SELECT  'AUTO'  AS  GBN  ,  INS_DATE  ,  '1'  AS  PRINT_GBN  FROM  GIBU.TBRA_DEMD_AUTO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  :DEMD_YRMN  UNION  ALL   \n";
        query +=" SELECT  'INDTN'  AS  GBN  ,  INS_DATE  ,  DECODE(PRNT_DAY,  NULL,  '0',  '1')  AS  PRINT_GBN  FROM  GIBU.TBRA_DEMD_INDTN  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  DEMD_DAY  LIKE  :DEMD_YRMN  ||  '%'  ORDER  BY  PRINT_GBN  DESC,  INS_DATE  DESC  )  WHERE  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$search_last_demd
    //##**$$end
}
