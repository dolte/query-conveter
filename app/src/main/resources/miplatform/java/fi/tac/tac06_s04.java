
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac06_s04
{
    public tac06_s04()
    {
    }
    //##**$$bank_mod_del
    /*
    */
    public DOBJ CTLEventID26(DOBJ dobj)
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
            dobj  = CALLbank_mod_del_MIUD2(Conn, dobj);           //  MIUD
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
    public DOBJ CTLEventID26( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbank_mod_del_MIUD2(Conn, dobj);           //  MIUD
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // MIUD
    // 계좌변경신청 삭제
    public DOBJ CALLbank_mod_del_MIUD2(Connection Conn, DOBJ dobj) throws Exception
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
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbank_mod_del_DEL1(Conn, dobj);           //  DEL
            }
        }
        return dobj;
    }
    // DEL
    // 삭제
    public DOBJ CALLbank_mod_del_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbank_mod_del_DEL1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbank_mod_del_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   APPTNLETTER_MNG_NUM = dvobj.getRecord().getDouble("APPTNLETTER_MNG_NUM");   //신청서 관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from HOMP.THOM_ACCNCHGAPPTNLETTER  \n";
        query +=" where APPTNLETTER_MNG_NUM=:APPTNLETTER_MNG_NUM";
        sobj.setSql(query);
        sobj.setDouble("APPTNLETTER_MNG_NUM", APPTNLETTER_MNG_NUM);               //신청서 관리번호
        return sobj;
    }
    //##**$$bank_mod_del
    //##**$$bank_mod_del2
    /*
    */
    public DOBJ CTLbank_mod_del2(DOBJ dobj)
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
            dobj  = CALLbank_mod_del2_UPD1(Conn, dobj);           //  삭제마킹
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
    public DOBJ CTLbank_mod_del2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbank_mod_del2_UPD1(Conn, dobj);           //  삭제마킹
        return dobj;
    }
    // 삭제마킹
    public DOBJ CALLbank_mod_del2_UPD1(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLbank_mod_del2_UPD1(dobj, dvobj);
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
    private SQLObject SQLbank_mod_del2_UPD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MODDATE ="";        //수정일시
        double   APPTNLETTER_MNG_NUM = dvobj.getRecord().getDouble("APPTNLETTER_MNG_NUM");   //신청서 관리번호
        String   MODPRESID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   STAT_GBN ="9";   //처리상태
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update HOMP.THOM_ACCNCHGAPPTNLETTER  \n";
        query +=" set MODPRESID=:MODPRESID , STAT_GBN=:STAT_GBN , MODDATE=SYSDATE  \n";
        query +=" where APPTNLETTER_MNG_NUM=:APPTNLETTER_MNG_NUM";
        sobj.setSql(query);
        sobj.setDouble("APPTNLETTER_MNG_NUM", APPTNLETTER_MNG_NUM);               //신청서 관리번호
        sobj.setString("MODPRESID", MODPRESID);               //수정자 ID
        sobj.setString("STAT_GBN", STAT_GBN);               //처리상태
        return sobj;
    }
    //##**$$bank_mod_del2
    //##**$$end
}
