
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s29_1
{
    public bra01_s29_1()
    {
    }
    //##**$$update_letter
    /*
    */
    public DOBJ CTLupdate_letter(DOBJ dobj)
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
            dobj  = CALLupdate_letter_MPD3(Conn, dobj);           //  루핑
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
    public DOBJ CTLupdate_letter( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupdate_letter_MPD3(Conn, dobj);           //  루핑
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 루핑
    public DOBJ CALLupdate_letter_MPD3(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLupdate_letter_SEL2(Conn, dobj);           //  STAT_GBN 조
                dobj  = CALLupdate_letter_UPD1(Conn, dobj);           //  수정
            }
        }
        return dobj;
    }
    // STAT_GBN 조
    public DOBJ CALLupdate_letter_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupdate_letter_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupdate_letter_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAT_GBN = dobj.getRetObject("R").getRecord().get("STAT_GBN");   //처리상태
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //비고
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(:REMAK,  '',  :STAT_GBN,  DECODE(:STAT_GBN,  '4',  '4',  '3'))  --REMAK가  NULL이  아니고  STAT_GBN이  4면  4  나머지는  3  AS  STAT_GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("STAT_GBN", STAT_GBN);               //처리상태
        sobj.setString("REMAK", REMAK);               //비고
        return sobj;
    }
    // 수정
    public DOBJ CALLupdate_letter_UPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupdate_letter_UPD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupdate_letter_UPD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String COMPL_DAY ="";        //완료 일자
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   LETTER_NUM = dvobj.getRecord().get("LETTER_NUM");   //신청서 번호
        String   STAT_GBN = dobj.getRetObject("SEL2").getRecord().get("STAT_GBN");   //처리상태
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update HOMP.THOM_UPSO_LETTER  \n";
        query +=" set COMPL_DAY=SYSDATE , STAT_GBN=:STAT_GBN , REMAK=:REMAK  \n";
        query +=" where LETTER_NUM=:LETTER_NUM";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("LETTER_NUM", LETTER_NUM);               //신청서 번호
        sobj.setString("STAT_GBN", STAT_GBN);               //처리상태
        return sobj;
    }
    //##**$$update_letter
    //##**$$end
}
