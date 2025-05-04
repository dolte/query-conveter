
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class opu_for_cwr_sys
{
    public opu_for_cwr_sys()
    {
    }
    //##**$$CWR_WORKS
    /*
    */
    public DOBJ CTLCWR_WORKS(DOBJ dobj)
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
            dobj  = CALLCWR_WORKS_SEL2(Conn, dobj);           //  OSWR결과
            dobj  = CALLCWR_WORKS_OSP1(Conn, dobj);           //  work프로시져 실행
            dobj  = CALLCWR_WORKS_SEL3(Conn, dobj);           //  Title결과 값 가져오기
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
    public DOBJ CTLCWR_WORKS( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLCWR_WORKS_SEL2(Conn, dobj);           //  OSWR결과
        dobj  = CALLCWR_WORKS_OSP1(Conn, dobj);           //  work프로시져 실행
        dobj  = CALLCWR_WORKS_SEL3(Conn, dobj);           //  Title결과 값 가져오기
        return dobj;
    }
    // OSWR결과
    public DOBJ CALLCWR_WORKS_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLCWR_WORKS_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLCWR_WORKS_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPDATE_DATE = dobj.getRetObject("S").getRecord().get("UPDATE_DATE");   //수정일자
        String   FILE_NM = dobj.getRetObject("S").getRecord().get("FILE_NM");   //파일 명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" select  :UPDATE_DATE  AS  UPDATE_DATE  ,  :FILE_NM  AS  FILE_NM  ,  'WORKS'  AS  TEMPA  from  DUAL ";
        sobj.setSql(query);
        sobj.setString("UPDATE_DATE", UPDATE_DATE);               //수정일자
        sobj.setString("FILE_NM", FILE_NM);               //파일 명
        return sobj;
    }
    // work프로시져 실행
    public DOBJ CALLCWR_WORKS_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("OSP1");
        String[]  incolumns ={"UPDATE_DATE","FILE_NM"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="FIDU.SP_FOR_CWR_WORKS";
        int[]    intypes={12, 12};;
        String[] outcolnms={"O_ERR_MSG","O_INSERTCNT","O_UPDATECNT","TRCNT"};;
        int[]    outtypes ={12, 12, 12, 12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP1");
        robj.setRetcode(1);
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // Title결과 값 가져오기
    public DOBJ CALLCWR_WORKS_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLCWR_WORKS_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLCWR_WORKS_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPDATE_DATE = dobj.getRetObject("S").getRecord().get("UPDATE_DATE");   //수정일자
        String   FILE_NM = dobj.getRetObject("S").getRecord().get("FILE_NM");   //파일 명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" select  count(WORKS_SEQ)  AS  CNT  from  FIDU.TB_FOR_CWR_WRITER  where  1=1   \n";
        query +=" AND  UPDATE_DATE  =  :UPDATE_DATE   \n";
        query +=" AND  FILE_NAME  =  :FILE_NM ";
        sobj.setSql(query);
        sobj.setString("UPDATE_DATE", UPDATE_DATE);               //수정일자
        sobj.setString("FILE_NM", FILE_NM);               //파일 명
        return sobj;
    }
    //##**$$CWR_WORKS
    //##**$$end
}
