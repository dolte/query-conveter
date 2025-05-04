
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_s15
{
    public bra05_s15()
    {
    }
    //##**$$iss_list_save
    /*
    */
    public DOBJ CTLiss_list_save(DOBJ dobj)
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
            dobj  = CALLiss_list_save_MIUD2(Conn, dobj);           //  분기
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
    public DOBJ CTLiss_list_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLiss_list_save_MIUD2(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLiss_list_save_MIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLiss_list_save_SEL1(Conn, dobj);           //  ISS_NUM 계산
                dobj  = CALLiss_list_save_SEL2(Conn, dobj);           //  입금금액 계산
                dobj  = CALLiss_list_save_INS8(Conn, dobj);           //  등록
            }
        }
        return dobj;
    }
    // ISS_NUM 계산
    public DOBJ CALLiss_list_save_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLiss_list_save_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLiss_list_save_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(ISS_NUM),0)+1,4,'0')  ISS_NUM  FROM  GIBU.TBRA_REPT_ACK_ISS  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  ISS_YEAR  =  TO_CHAR(SYSDATE,'YYYY') ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 입금금액 계산
    public DOBJ CALLiss_list_save_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLiss_list_save_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLiss_list_save_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(B.REPT_AMT,0)-  NVL(E.RETURN_AMT,0)  AS  REPT_AMT  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  SUM(REPT_AMT)  REPT_AMT  ,  UPSO_CD  FROM(   \n";
        query +=" SELECT  REPT_AMT  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  RECV_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  B.DISTR_AMT  ,  B.UPSO_CD  FROM  GIBU.TBRA_REPT  A  ,  GIBU.TBRA_REPT_UPSO_DISTR  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.DISTR_GBN  IS  NOT  NULL   \n";
        query +=" AND  A.RECV_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  B.REPT_GBN   \n";
        query +=" AND  A.DISTR_GBN  =  B.DISTR_GBN  )  GROUP  BY  UPSO_CD  )  B  ,  INSA.TCPM_BIPLC  C  ,  INSA.TCPM_BIPLC  D  ,  (   \n";
        query +=" SELECT  SUM(NVL(RETURN_AMT,0))  RETURN_AMT  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_RETURN  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  RETURN_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'  GROUP  BY  UPSO_CD  )  E  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  D.BIPLC_CD  =  '100'   \n";
        query +=" AND  E.UPSO_CD(+)  =  A.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 등록
    public DOBJ CALLiss_list_save_INS8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS8");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLiss_list_save_INS8(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS8") ;
        rvobj.Println("INS8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLiss_list_save_INS8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String ISS_DAY ="";        //발행 일자
        String ISS_YEAR ="";        //발행 년도
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //시작년월
        String   REQUEST_GBN = dvobj.getRecord().get("REQUEST_GBN");   //신청자
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //종료년월
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   USE_GBN = dvobj.getRecord().get("USE_GBN");   //사용 구분
        double   ISS_AMT = dobj.getRetObject("SEL2").getRecord().getDouble("REPT_AMT");   //발행 금액
        String   ISS_NUM = dobj.getRetObject("SEL1").getRecord().get("ISS_NUM");   //발행 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_ACK_ISS (INS_DATE, ISS_YEAR, ISS_AMT, USE_GBN, ISS_DAY, UPSO_CD, ISS_NUM, END_YRMN, BRAN_CD, REQUEST_GBN, START_YRMN)  \n";
        query +=" values(sysdate, TO_CHAR(SYSDATE,'YYYY'), :ISS_AMT , :USE_GBN , TO_CHAR(SYSDATE,'YYYYMMDD'), :UPSO_CD , :ISS_NUM , :END_YRMN , :BRAN_CD , :REQUEST_GBN , :START_YRMN )";
        sobj.setSql(query);
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        sobj.setString("REQUEST_GBN", REQUEST_GBN);               //신청자
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("USE_GBN", USE_GBN);               //사용 구분
        sobj.setDouble("ISS_AMT", ISS_AMT);               //발행 금액
        sobj.setString("ISS_NUM", ISS_NUM);               //발행 번호
        return sobj;
    }
    //##**$$iss_list_save
    //##**$$end
}
