
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac01_s04
{
    public tac01_s04()
    {
    }
    //##**$$tac01_s04_save
    /*
    */
    public DOBJ CTLtac01_s04_save(DOBJ dobj)
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
            dobj  = CALLtac01_s04_save_MPD1(Conn, dobj);           //  전자세금계산서에러,재전송삭제
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
    public DOBJ CTLtac01_s04_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac01_s04_save_MPD1(Conn, dobj);           //  전자세금계산서에러,재전송삭제
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 전자세금계산서에러,재전송삭제
    public DOBJ CALLtac01_s04_save_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtac01_s04_save_SEL3(Conn, dobj);           //  MPD
                if( dobj.getRetObject("R").getRecord().get("CHECK").equals("1"))
                {
                    dobj  = CALLtac01_s04_save_SEL12(Conn, dobj);           //  MPD
                    if( dobj.getRetObject("R").getRecord().get("TRANSYN").equals("Y"))
                    {
                        dobj  = CALLtac01_s04_save_UPD14(Conn, dobj);           //  계산서 상태 변경
                    }
                    else
                    {
                        dobj  = CALLtac01_s04_save_DEL11(Conn, dobj);           //  전자세금계산서 에러 삭제
                        dobj  = CALLtac01_s04_save_DEL12(Conn, dobj);           //  전자세금계산서 상세 삭제
                        dobj  = CALLtac01_s04_save_DEL13(Conn, dobj);           //  전자세금계산서 item 삭제
                        dobj  = CALLtac01_s04_save_UPD16(Conn, dobj);           //  계산서 상태 변경
                    }
                }
            }
        }
        return dobj;
    }
    // MPD
    public DOBJ CALLtac01_s04_save_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac01_s04_save_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac01_s04_save_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  1  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // MPD
    public DOBJ CALLtac01_s04_save_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac01_s04_save_SEL12(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac01_s04_save_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  1  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 계산서 상태 변경
    public DOBJ CALLtac01_s04_save_UPD14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD14");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtac01_s04_save_UPD14(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD14") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac01_s04_save_UPD14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILLSEQ = dvobj.getRecord().get("BILLSEQ");   //세금계산서 고유번호
        String   RETRANSYN ="Y";   //재전송여부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.BILL_STAT  \n";
        query +=" set RETRANSYN=:RETRANSYN  \n";
        query +=" where BILLSEQ=:BILLSEQ";
        sobj.setSql(query);
        sobj.setString("BILLSEQ", BILLSEQ);               //세금계산서 고유번호
        sobj.setString("RETRANSYN", RETRANSYN);               //재전송여부
        return sobj;
    }
    // 전자세금계산서 에러 삭제
    public DOBJ CALLtac01_s04_save_DEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL11");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtac01_s04_save_DEL11(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL11") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac01_s04_save_DEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILLSEQ = dvobj.getRecord().get("BILLSEQ");   //세금계산서 고유번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.BILL_TRANS  \n";
        query +=" where BILLSEQ=:BILLSEQ";
        sobj.setSql(query);
        sobj.setString("BILLSEQ", BILLSEQ);               //세금계산서 고유번호
        return sobj;
    }
    // 전자세금계산서 상세 삭제
    public DOBJ CALLtac01_s04_save_DEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtac01_s04_save_DEL12(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac01_s04_save_DEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILLSEQ = dvobj.getRecord().get("BILLSEQ");   //세금계산서 고유번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.BILL_LOG  \n";
        query +=" where BILLSEQ=:BILLSEQ";
        sobj.setSql(query);
        sobj.setString("BILLSEQ", BILLSEQ);               //세금계산서 고유번호
        return sobj;
    }
    // 전자세금계산서 item 삭제
    public DOBJ CALLtac01_s04_save_DEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL13");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtac01_s04_save_DEL13(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL13") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac01_s04_save_DEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILLSEQ = dvobj.getRecord().get("BILLSEQ");   //세금계산서 고유번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.BILL_TRANS_ITEM  \n";
        query +=" where BILLSEQ=:BILLSEQ";
        sobj.setSql(query);
        sobj.setString("BILLSEQ", BILLSEQ);               //세금계산서 고유번호
        return sobj;
    }
    // 계산서 상태 변경
    public DOBJ CALLtac01_s04_save_UPD16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD16");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtac01_s04_save_UPD16(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD16") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac01_s04_save_UPD16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_NUM = dobj.getRetObject("R").getRecord().get("BILLSEQ");   //계산서 번호
        String   ELEC_SEND_YN ="0";   //전자계산서 발행유무
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_BILL  \n";
        query +=" set ELEC_SEND_YN=:ELEC_SEND_YN  \n";
        query +=" where BILL_NUM=:BILL_NUM";
        sobj.setSql(query);
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("ELEC_SEND_YN", ELEC_SEND_YN);               //전자계산서 발행유무
        return sobj;
    }
    //##**$$tac01_s04_save
    //##**$$mstSearchTac01_s04
    /*
    */
    public DOBJ CTLmstSearchTac01_s04(DOBJ dobj)
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
            dobj  = CALLmstSearchTac01_s04_SEL1(Conn, dobj);           //  전자세금계산서 에러,재전송 조회
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
    public DOBJ CTLmstSearchTac01_s04( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmstSearchTac01_s04_SEL1(Conn, dobj);           //  전자세금계산서 에러,재전송 조회
        return dobj;
    }
    // 전자세금계산서 에러,재전송 조회
    public DOBJ CALLmstSearchTac01_s04_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmstSearchTac01_s04_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmstSearchTac01_s04_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TO_DAY = dobj.getRetObject("S").getRecord().get("TO_DAY");   //종료일자
        String   FROM_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //등록일
        String   TRANSYN = dobj.getRetObject("S").getRecord().get("TRANSYN");   //전송여부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BILLSEQ, DT, SUPMONEY, SCOMPANY, SEMAIL, RCOMPANY, RCEONAME, REMAIL, TRANSYN  ";
        query +=" FROM FIDU.BILL_TRANS  ";
        query +=" WHERE 1 = 1  ";
        if( !FROM_DAY.equals("") )
        {
            query +=" AND REPLACE(DT,'-','') >= :FROM_DAY  ";
        }
        if( !TO_DAY.equals("") )
        {
            query +=" AND REPLACE(DT,'-','') <= :TO_DAY  ";
        }
        if( !TRANSYN.equals("") )
        {
            query +=" AND TRANSYN = :TRANSYN  ";
        }
        sobj.setSql(query);
        if(!TO_DAY.equals(""))
        {
            sobj.setString("TO_DAY", TO_DAY);               //종료일자
        }
        if(!FROM_DAY.equals(""))
        {
            sobj.setString("FROM_DAY", FROM_DAY);               //등록일
        }
        if(!TRANSYN.equals(""))
        {
            sobj.setString("TRANSYN", TRANSYN);               //전송여부
        }
        return sobj;
    }
    //##**$$mstSearchTac01_s04
    //##**$$subSearchTac01_s04
    /*
    */
    public DOBJ CTLsubSearchTac01_s04(DOBJ dobj)
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
            dobj  = CALLsubSearchTac01_s04_SEL1(Conn, dobj);           //  전자세금계산서 에러 로그조회
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
    public DOBJ CTLsubSearchTac01_s04( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsubSearchTac01_s04_SEL1(Conn, dobj);           //  전자세금계산서 에러 로그조회
        return dobj;
    }
    // 전자세금계산서 에러 로그조회
    public DOBJ CALLsubSearchTac01_s04_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsubSearchTac01_s04_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsubSearchTac01_s04_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILLSEQ = dobj.getRetObject("S").getRecord().get("BILLSEQ");   //세금계산서 고유번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BILLSEQ,  ERR_MESG,  ERR_DATE  FROM  FIDU.BILL_LOG  WHERE  BILLSEQ  =  :BILLSEQ ";
        sobj.setSql(query);
        sobj.setString("BILLSEQ", BILLSEQ);               //세금계산서 고유번호
        return sobj;
    }
    //##**$$subSearchTac01_s04
    //##**$$end
}
