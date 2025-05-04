
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class test
{
    public test()
    {
    }
    //##**$$EventID10
    /*
    */
    public DOBJ CTLEventID10(DOBJ dobj)
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
            dobj  = CALLEventID10_SEL5(Conn, dobj);           //  GE12생성목록
            dobj  = CALLEventID10_MPD2(Conn, dobj);           //  1
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
    public DOBJ CTLEventID10( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLEventID10_SEL5(Conn, dobj);           //  GE12생성목록
        dobj  = CALLEventID10_MPD2(Conn, dobj);           //  1
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // GE12생성목록
    public DOBJ CALLEventID10_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLEventID10_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.setRetcode(1);
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID10_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  PROC_DAY  ,  SEQ_NUM  ,  A.UPSO_CD  ,  GBN  ,  APPTN_DAY  ,  APPTN_GBN  ,  CHGATR_PAYPRES_NUM  ,  SUBSTR(PAY_BANK_CD,  0,  3)  AS  BANK_CD  ,  PAY_ACCN_NUM  ,  SUBSTR(FILE_TEMPNM,  INSTR(FILE_TEMPNM,  '.',  LENGTH(FILE_TEMPNM)  -  5)  +  1)  AS  FILE_EXTENTION  ,  FILE_SIZE  ,  FILES  ,  FILE_ROUT  ,  FILE_NM  ,  SEQ  FROM  GIBU.TBRA_UPSO_AUTORSLT  A  ,  GIBU.TBRA_UPSO_AUTO_DOC_ATTCH  B  WHERE  A.UPSO_CD  =  B.UPSO_CD(+)   \n";
        query +=" AND  A.APPTN_GBN  =  '02'   \n";
        query +=" AND  A.PROC_DAY  =  '20170418'   \n";
        query +=" AND  SEQ  =   \n";
        query +=" (SELECT  MAX(SEQ)  FROM  GIBU.TBRA_UPSO_AUTO_DOC_ATTCH  WHERE  UPSO_CD  =  A.UPSO_CD) ";
        sobj.setSql(query);
        return sobj;
    }
    // 1
    public DOBJ CALLEventID10_MPD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD2");
        VOBJ dvobj = dobj.getRetObject("SEL5");         //GE12생성목록에서 생성시킨 OBJECT입니다.(CALLEventID10_SEL5)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( 1 == 1)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLEventID10_SEL4(Conn, dobj);           //  2
            }
        }
        return dobj;
    }
    // 2
    public DOBJ CALLEventID10_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLEventID10_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        String fullname="";
        rvobj.first();
        while(rvobj.next())
        {
            wutil.makeFileOverwirte( dobj.getRetObject("R").getRecord().get("FILE_ROUT"), dobj.getRetObject("R").getRecord().get("FILE_NM"),rvobj.getRecord().getBytes("FILES"));
        }
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID10_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //관리번호
        String   PROC_DAY = dobj.getRetObject("R").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  PROC_DAY  ,  SEQ_NUM  ,  A.UPSO_CD  ,  GBN  ,  APPTN_DAY  ,  APPTN_GBN  ,  CHGATR_PAYPRES_NUM  ,  SUBSTR(PAY_BANK_CD,  0,  3)  AS  BANK_CD  ,  PAY_ACCN_NUM  ,  SUBSTR(FILE_TEMPNM,  INSTR(FILE_TEMPNM,  '.',  LENGTH(FILE_TEMPNM)  -  5)  +  1)  AS  FILE_EXTENTION  ,  FILE_SIZE  ,  FILES  ,  FILE_ROUT  ,  FILE_NM  FROM  GIBU.TBRA_UPSO_AUTORSLT  A  ,  GIBU.TBRA_UPSO_AUTO_DOC_ATTCH  B  WHERE  A.UPSO_CD  =  B.UPSO_CD(+)   \n";
        query +=" AND  A.APPTN_GBN  =  '02'   \n";
        query +=" AND  A.PROC_DAY  =  :PROC_DAY   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    //##**$$EventID10
    //##**$$end
}
