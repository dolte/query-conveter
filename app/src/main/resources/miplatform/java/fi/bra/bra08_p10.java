
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra08_p10
{
    public bra08_p10()
    {
    }
    //##**$$gov_stat_search
    /*
    */
    public DOBJ CTLgov_stat_search(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
            {
                dobj  = CALLgov_stat_search_SEL1(Conn, dobj);           //  센터별 조회
                dobj  = CALLgov_stat_search_MRG6( dobj);        //  MRG
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj  = CALLgov_stat_search_SEL2(Conn, dobj);           //  사원별 조회
                dobj  = CALLgov_stat_search_MRG6( dobj);        //  MRG
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
    public DOBJ CTLgov_stat_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
        {
            dobj  = CALLgov_stat_search_SEL1(Conn, dobj);           //  센터별 조회
            dobj  = CALLgov_stat_search_MRG6( dobj);        //  MRG
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLgov_stat_search_SEL2(Conn, dobj);           //  사원별 조회
            dobj  = CALLgov_stat_search_MRG6( dobj);        //  MRG
        }
        return dobj;
    }
    // 센터별 조회
    // 개업 업소 기준
    public DOBJ CALLgov_stat_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLgov_stat_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgov_stat_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XX.BRAN_CD,  XX.BRAN_NM,  ''  STAFF_CD,  ''  STAFF_NM,  STAFF_CNT,  TOT_CNT,  MATCH_CNT_1  +  MATCH_CNT_2  MATCH_CNT,  TOT_CNT  -  MATCH_CNT_1  -  MATCH_CNT_2  N_MATCH_CNT,  ROUND((MATCH_CNT_1  +  MATCH_CNT_2)/TOT_CNT,2)*100  ||  '%'  MATCH_RATE  FROM   \n";
        query +=" (SELECT  X.BRAN_CD  BRAN_CD,  GIBU.GET_BRAN_NM  (X.BRAN_CD)  BRAN_NM,   \n";
        query +=" (SELECT  COUNT(*)  FROM  INSA.TCPM_DEPT  A,  INSA.TINS_MST01  B  WHERE  A.DEPT_CD  =  B.DEPT_CD   \n";
        query +=" AND  A.GIBU  =  X.BRAN_CD   \n";
        query +=" AND  B.RETIRE_DAY  IS  NULL)  STAFF_CNT,  COUNT  (*)  TOT_CNT,  COUNT  (UPSO_CD)  MATCH_CNT_1,  SUM(DECODE(X.STAT_GBN,4,1,0))  MATCH_CNT_2  FROM  GIBU.TGOV_INTE_HISTORY  X  WHERE  1=1   \n";
        query +=" AND  X.DCB_YMD  IS  NULL   \n";
        query +=" AND  X.BRAN_CD=DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TO_CHAR(X.CONFIRM_DATE,'YYYYMM')  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN  GROUP  BY  BRAN_CD)  XX  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // MRG
    public DOBJ CALLgov_stat_search_MRG6(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG6");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1,SEL2","");
        rvobj.setName("MRG6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 사원별 조회
    // 개업 업소 기준
    public DOBJ CALLgov_stat_search_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLgov_stat_search_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgov_stat_search_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XX.BRAN_CD,  XX.BRAN_NM,  STAFF_CD,  STAFF_NM,  ''  STAFF_CNT,  TOT_CNT,  MATCH_CNT_1  +  MATCH_CNT_2  MATCH_CNT,  TOT_CNT  -  MATCH_CNT_1  -  MATCH_CNT_2  N_MATCH_CNT,  ROUND((MATCH_CNT_1  +  MATCH_CNT_2)/TOT_CNT,2)*100  ||  '%'  MATCH_RATE  FROM  (   \n";
        query +=" SELECT  X.BRAN_CD  BRAN_CD,  GIBU.GET_BRAN_NM  (X.BRAN_CD)  BRAN_NM,  X.STAFF_CD  STAFF_CD,  FIDU.GET_STAFF_NM(X.STAFF_CD)  STAFF_NM,  COUNT  (*)  TOT_CNT,  COUNT  (UPSO_CD)  MATCH_CNT_1,  SUM(DECODE(X.STAT_GBN,4,1,0))  MATCH_CNT_2  FROM  GIBU.TGOV_INTE_HISTORY  X  WHERE  1=1   \n";
        query +=" AND  DCB_YMD  IS  NULL   \n";
        query +=" AND  X.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TO_CHAR(X.CONFIRM_DATE,'YYYYMM')  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN  GROUP  BY  BRAN_CD,  STAFF_CD)  XX  ORDER  BY  BRAN_CD,  STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    //##**$$gov_stat_search
    //##**$$end
}
