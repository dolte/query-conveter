
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra06_r11
{
    public bra06_r11()
    {
    }
    //##**$$btrip_list
    /*
    */
    public DOBJ CTLbtrip_list(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("ORDER_GBN").equals("1"))
            {
                dobj  = CALLbtrip_list_SEL11(Conn, dobj);           //  목록조회
                dobj  = CALLbtrip_list_SEL1( dobj);        //  머지
            }
            else
            {
                dobj  = CALLbtrip_list_SEL12(Conn, dobj);           //  목록조회
                dobj  = CALLbtrip_list_SEL1( dobj);        //  머지
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
    public DOBJ CTLbtrip_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("ORDER_GBN").equals("1"))
        {
            dobj  = CALLbtrip_list_SEL11(Conn, dobj);           //  목록조회
            dobj  = CALLbtrip_list_SEL1( dobj);        //  머지
        }
        else
        {
            dobj  = CALLbtrip_list_SEL12(Conn, dobj);           //  목록조회
            dobj  = CALLbtrip_list_SEL1( dobj);        //  머지
        }
        return dobj;
    }
    // 목록조회
    public DOBJ CALLbtrip_list_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbtrip_list_SEL11(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        rvobj.Println("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_list_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD,  TO_CHAR  (INS_DATE,  'YYYYMMDD')  AS  INS_DATE,  BTRIP_DAY,  STAFF_CD,  INSA.F_GET_STAFF_NM  (STAFF_CD)  AS  STAFF_NM,  DECODE(GBN,  '1','업소출장',  '2','고소출장',  '3','동행출장',  '4','단체출장',  '5','교육출장',  '6','무대공연출장','7','로고송출장','8','기타',  GBN)  AS  GBN,  TOTAL_FEE,  BTRIP_STAFF_NM,  BTRIP_PROVCITY_NM,  SUBSTR(LPAD(BTRIP_START_TIME,6,'0'),1,2)  ||  ':'  ||  SUBSTR(LPAD(BTRIP_START_TIME,6,'0'),3,2)  ||  ':'  ||  SUBSTR(LPAD(BTRIP_START_TIME,6,'0'),5,2)  AS  BTRIP_START_TIME,  SUBSTR(LPAD(BTRIP_USE_TIME,6,'0'),1,2)  ||  ':'  ||  SUBSTR(LPAD(BTRIP_USE_TIME,6,'0'),3,2)  ||  ':'  ||  SUBSTR(LPAD(BTRIP_USE_TIME,6,'0'),5,2)  AS  BTRIP_USE_TIME,  BTRIP_USE_KILO  FROM  GIBU.TBRA_BTRIP  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  BTRIP_DAY  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  GBN  =  DECODE  (:GBN,  '',  GBN,  :GBN)  ORDER  BY  BTRIP_DAY ";
        sobj.setSql(query);
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 머지
    public DOBJ CALLbtrip_list_SEL1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("SEL1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL11, SEL12","");
        rvobj.setName("SEL1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 목록조회
    public DOBJ CALLbtrip_list_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbtrip_list_SEL12(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL12");
        rvobj.Println("SEL12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbtrip_list_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //구분
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD,  TO_CHAR  (INS_DATE,  'YYYYMMDD')  AS  INS_DATE,  BTRIP_DAY,  STAFF_CD,  INSA.F_GET_STAFF_NM  (STAFF_CD)  AS  STAFF_NM,  DECODE(GBN,  '1','업소출장',  '2','고소출장',  '3','동행출장',  '4','단체출장',  '5','교육출장',  '6','무대공연출장','7','로고송출장','8','기타',  GBN)  AS  GBN,  TOTAL_FEE,  BTRIP_STAFF_NM,  BTRIP_PROVCITY_NM,  SUBSTR(LPAD(BTRIP_START_TIME,6,'0'),1,2)  ||  ':'  ||  SUBSTR(LPAD(BTRIP_START_TIME,6,'0'),3,2)  ||  ':'  ||  SUBSTR(LPAD(BTRIP_START_TIME,6,'0'),5,2)  AS  BTRIP_START_TIME,  SUBSTR(LPAD(BTRIP_USE_TIME,6,'0'),1,2)  ||  ':'  ||  SUBSTR(LPAD(BTRIP_USE_TIME,6,'0'),3,2)  ||  ':'  ||  SUBSTR(LPAD(BTRIP_USE_TIME,6,'0'),5,2)  AS  BTRIP_USE_TIME,  BTRIP_USE_KILO  FROM  GIBU.TBRA_BTRIP  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  BTRIP_DAY  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  GBN  =  DECODE  (:GBN,  '',  GBN,  :GBN)  ORDER  BY  STAFF_NM ";
        sobj.setSql(query);
        sobj.setString("GBN", GBN);               //구분
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    //##**$$btrip_list
    //##**$$upso_list_kylog
    /*
    */
    public DOBJ CTLupso_list_kylog(DOBJ dobj)
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
            dobj  = CALLupso_list_kylog_SEL1(Conn, dobj);           //  업소목록
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
    public DOBJ CTLupso_list_kylog( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_list_kylog_SEL1(Conn, dobj);           //  업소목록
        return dobj;
    }
    // 업소목록
    public DOBJ CALLupso_list_kylog_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_list_kylog_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_list_kylog_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  UPSO_NM  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  'A' ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$upso_list_kylog
    //##**$$end
}
