
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r12_a
{
    public tac10_r12_a()
    {
    }
    //##**$$SearchADR
    /*
    */
    public DOBJ CTLSearchADR(DOBJ dobj)
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
            dobj  = CALLSearchADR_SEL1(Conn, dobj);           //  지급내역서주소
            dobj  = CALLSearchADR_SEL2(Conn, dobj);           //  SEL
            dobj  = CALLSearchADR_SEL_NOTICE(Conn, dobj);           //  월별공지조회
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
    public DOBJ CTLSearchADR( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLSearchADR_SEL1(Conn, dobj);           //  지급내역서주소
        dobj  = CALLSearchADR_SEL2(Conn, dobj);           //  SEL
        dobj  = CALLSearchADR_SEL_NOTICE(Conn, dobj);           //  월별공지조회
        return dobj;
    }
    // 지급내역서주소
    public DOBJ CALLSearchADR_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLSearchADR_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLSearchADR_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  POST_RCPT_NM,  ADDR,  ADDR_DETED,  POST_NUM,  MB_CD,  REF_INFO  FROM  FIDU.TMEM_ADBK  WHERE  MB_CD  =  :MB_CD   \n";
        query +=" AND  MNG_NUM  =   \n";
        query +=" (SELECT  MAX  (MNG_NUM)  FROM  FIDU.TMEM_ADBK  WHERE  ADDR_GBN  =  '2'   \n";
        query +=" AND  NVL(POSTSNDBK_YN,'  ')  <>  '1'   \n";
        query +=" AND  MB_CD  =  :MB_CD)   \n";
        query +=" AND  ADDR_GBN  =  '2'   \n";
        query +=" AND  NVL(POSTSNDBK_YN,'  ')  <>  '1'  ORDER  BY  MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    // SEL
    public DOBJ CALLSearchADR_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLSearchADR_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLSearchADR_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(DECODE(SUBSTR(SUPP_YRMN,5,6),'01',  DISTR_AMT,0))  MON1,  SUM(DECODE(SUBSTR(SUPP_YRMN,5,6),'02',  DISTR_AMT,0))  MON2,  SUM(DECODE(SUBSTR(SUPP_YRMN,5,6),'03',  DISTR_AMT,0))  MON3,  SUM(DECODE(SUBSTR(SUPP_YRMN,5,6),'04',  DISTR_AMT,0))  MON4,  SUM(DECODE(SUBSTR(SUPP_YRMN,5,6),'05',  DISTR_AMT,0))  MON5,  SUM(DECODE(SUBSTR(SUPP_YRMN,5,6),'06',  DISTR_AMT,0))  MON6,  SUM(DECODE(SUBSTR(SUPP_YRMN,5,6),'07',  DISTR_AMT,0))  MON7,  SUM(DECODE(SUBSTR(SUPP_YRMN,5,6),'08',  DISTR_AMT,0))  MON8,  SUM(DECODE(SUBSTR(SUPP_YRMN,5,6),'09',  DISTR_AMT,0))  MON9,  SUM(DECODE(SUBSTR(SUPP_YRMN,5,6),'10',  DISTR_AMT,0))  MON10,  SUM(DECODE(SUBSTR(SUPP_YRMN,5,6),'11',  DISTR_AMT,0))  MON11,  SUM(DECODE(SUBSTR(SUPP_YRMN,5,6),'12',  DISTR_AMT,0))  MON12,  SUM(  DISTR_AMT)  MON_TOT  FROM  (   \n";
        query +=" SELECT  WRK_YRMN  as  SUPP_YRMN,  SUM(NVL(DISTR_AMT,0))  +  SUM(NVL(ETC_AMT,0))  AS  DISTR_AMT  FROM  FIDU.TDIS_DISTRPLANAMT  WHERE  WRK_YRMN  LIKE  SUBSTR(:SUPP_YRMN,1,4)||'%'   \n";
        query +=" AND  WRK_YRMN  >='201007'   \n";
        query +=" AND  WRK_YRMN  <=  :SUPP_YRMN   \n";
        query +=" AND  DISTR_AMT  >  0  GROUP  BY  WRK_YRMN  UNION  ALL   \n";
        query +=" SELECT  WRK_YRMN  SUPP_YRMN,  SUM(DISTR_AMT)  DISTR_AMT  FROM  FIDU.TDIS_REDISTR_AMT  WHERE  WRK_YRMN  LIKE  SUBSTR(:SUPP_YRMN,1,4)||'%'   \n";
        query +=" AND  WRK_YRMN  <=  :SUPP_YRMN  GROUP  BY  WRK_YRMN  ) ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 월별공지조회
    public DOBJ CALLSearchADR_SEL_NOTICE(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL_NOTICE");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL_NOTICE");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLSearchADR_SEL_NOTICE(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL_NOTICE");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLSearchADR_SEL_NOTICE(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  YRMN  ,  FIDU.GET_REPLACE_STR(CONTENTS)  CONTENTS  FROM  HOMP.THOM_PAYMENT_NOTICE  WHERE  YRMN  =  :SUPP_YRMN   \n";
        query +=" AND  USE_YN  =  'Y' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$SearchADR
    //##**$$end
}
