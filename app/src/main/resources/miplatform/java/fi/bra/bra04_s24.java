
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s24
{
    public bra04_s24()
    {
    }
    //##**$$req_virtual_info
    /*
    */
    public DOBJ CTLreq_virtual_info(DOBJ dobj)
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
            dobj  = CALLreq_virtual_info_SEL2(Conn, dobj);           //  �Աݳ��� ��û���� Ȯ��
            if( dobj.getRetObject("SEL2").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLreq_virtual_info_OSP1(Conn, dobj);           //  ������û ���ν���
            }
            else
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="������ ������Ʈ�� �������Դϴ�. �Ϸ� �� �ٽ� ��û�Ͽ��ֽñ� �ٶ��ϴ�.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
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
    public DOBJ CTLreq_virtual_info( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLreq_virtual_info_SEL2(Conn, dobj);           //  �Աݳ��� ��û���� Ȯ��
        if( dobj.getRetObject("SEL2").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLreq_virtual_info_OSP1(Conn, dobj);           //  ������û ���ν���
        }
        else
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="������ ������Ʈ�� �������Դϴ�. �Ϸ� �� �ٽ� ��û�Ͽ��ֽñ� �ٶ��ϴ�.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // �Աݳ��� ��û���� Ȯ��
    public DOBJ CALLreq_virtual_info_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLreq_virtual_info_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLreq_virtual_info_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(CNT)  AS  CNT  FROM   \n";
        query +=" (SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_COMMAND_TBL  WHERE  COMMAND_WORK  =  'MEM'   \n";
        query +=" AND  COMMAND_LINE  =  'SEARCH'   \n";
        query +=" AND  COMMAND_STATUS  =  'B'   \n";
        query +=" AND  COMMAND_NO  =   \n";
        query +=" (SELECT  COMMAND_NO  FROM  GIBU.FMS_VIRACC_MEM_SH_TBL  WHERE  MEMBER_ID  =  :UPSO_CD   \n";
        query +=" AND  TRAN_STATUS  =  'B')   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_VIRACC_MEM_SH_TBL  WHERE  MEMBER_ID  =  :UPSO_CD   \n";
        query +=" AND  TRAN_STATUS  =  'B'   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDYYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������û ���ν���
    public DOBJ CALLreq_virtual_info_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"UPSO_CD","INS_STAFF"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INS_STAFF = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //��� ���
            record.put("INS_STAFF",INS_STAFF);
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
        String   spname ="GIBU.SP_FMS_SEND_SELMEM";
        int[]    intypes={12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$req_virtual_info
    //##**$$chg_virtual_upso
    /*
    */
    public DOBJ CTLchg_virtual_upso(DOBJ dobj)
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
            dobj  = CALLchg_virtual_upso_SEL2(Conn, dobj);           //  �Աݳ��� ��û���� Ȯ��
            if( dobj.getRetObject("SEL2").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLchg_virtual_upso_OSP1(Conn, dobj);           //  ȸ���������� ���ν���
            }
            else
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="�Ա��ڸ� ������ �������Դϴ�. �Ϸ� �� �ٽ� ��û�Ͽ��ֽñ� �ٶ��ϴ�.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
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
    public DOBJ CTLchg_virtual_upso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLchg_virtual_upso_SEL2(Conn, dobj);           //  �Աݳ��� ��û���� Ȯ��
        if( dobj.getRetObject("SEL2").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLchg_virtual_upso_OSP1(Conn, dobj);           //  ȸ���������� ���ν���
        }
        else
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="�Ա��ڸ� ������ �������Դϴ�. �Ϸ� �� �ٽ� ��û�Ͽ��ֽñ� �ٶ��ϴ�.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // �Աݳ��� ��û���� Ȯ��
    public DOBJ CALLchg_virtual_upso_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLchg_virtual_upso_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchg_virtual_upso_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(CNT)  AS  CNT  FROM   \n";
        query +=" (SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_COMMAND_TBL  WHERE  COMMAND_WORK  =  'MEM'   \n";
        query +=" AND  COMMAND_LINE  =  'CHANGE'   \n";
        query +=" AND  COMMAND_STATUS  =  'B'   \n";
        query +=" AND  COMMAND_NO  =   \n";
        query +=" (SELECT  COMMAND_NO  FROM  GIBU.FMS_VIRACC_MEM_CH_TBL  WHERE  MEMBER_ID  =  :UPSO_CD   \n";
        query +=" AND  TRAN_STATUS  =  'B')   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_VIRACC_MEM_CH_TBL  WHERE  MEMBER_ID  =  :UPSO_CD   \n";
        query +=" AND  TRAN_STATUS  =  'B'   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ȸ���������� ���ν���
    public DOBJ CALLchg_virtual_upso_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"UPSO_CD","NEW_REPTPRES","INS_STAFF"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INS_STAFF = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //��� ���
            record.put("INS_STAFF",INS_STAFF);
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
        String   spname ="GIBU.SP_FMS_SEND_CHGMEM";
        int[]    intypes={12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$chg_virtual_upso
    //##**$$del_virtual_upso
    /*
    */
    public DOBJ CTLdel_virtual_upso(DOBJ dobj)
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
            dobj  = CALLdel_virtual_upso_SEL2(Conn, dobj);           //  �Աݳ��� ��û���� Ȯ��
            if( dobj.getRetObject("SEL2").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLdel_virtual_upso_OSP1(Conn, dobj);           //  ȸ������ ���ν���
            }
            else
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="������� ȸ���� �������Դϴ�. �Ϸ� �� �ٽ� ��û�Ͽ��ֽñ� �ٶ��ϴ�.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
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
    public DOBJ CTLdel_virtual_upso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdel_virtual_upso_SEL2(Conn, dobj);           //  �Աݳ��� ��û���� Ȯ��
        if( dobj.getRetObject("SEL2").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLdel_virtual_upso_OSP1(Conn, dobj);           //  ȸ������ ���ν���
        }
        else
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="������� ȸ���� �������Դϴ�. �Ϸ� �� �ٽ� ��û�Ͽ��ֽñ� �ٶ��ϴ�.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // �Աݳ��� ��û���� Ȯ��
    public DOBJ CALLdel_virtual_upso_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLdel_virtual_upso_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdel_virtual_upso_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(CNT)  AS  CNT  FROM   \n";
        query +=" (SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_COMMAND_TBL  WHERE  COMMAND_WORK  =  'MEM'   \n";
        query +=" AND  COMMAND_LINE  =  'CANCEL'   \n";
        query +=" AND  COMMAND_STATUS  =  'B'   \n";
        query +=" AND  COMMAND_NO  =   \n";
        query +=" (SELECT  COMMAND_NO  FROM  GIBU.FMS_VIRACC_MEM_DEL_TBL  WHERE  MEMBER_ID  =  :UPSO_CD   \n";
        query +=" AND  TRAN_STATUS  =  'B')   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_VIRACC_MEM_DEL_TBL  WHERE  MEMBER_ID  =  :UPSO_CD   \n";
        query +=" AND  TRAN_STATUS  =  'B'   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ȸ������ ���ν���
    public DOBJ CALLdel_virtual_upso_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"UPSO_CD","INS_STAFF"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INS_STAFF = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //��� ���
            record.put("INS_STAFF",INS_STAFF);
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
        String   spname ="GIBU.SP_FMS_SEND_DELMEM";
        int[]    intypes={12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$del_virtual_upso
    //##**$$ins_virtual_upso
    /*
    */
    public DOBJ CTLins_virtual_upso(DOBJ dobj)
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
            dobj  = CALLins_virtual_upso_SEL2(Conn, dobj);           //  �Աݳ��� ��û���� Ȯ��
            if( dobj.getRetObject("SEL2").getRecord().getDouble("CNT") < 2)
            {
                dobj  = CALLins_virtual_upso_OSP1(Conn, dobj);           //  ȸ��������ν���
            }
            else
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="������� �߱��� �������Դϴ�. �Ϸ� �� �ٽ� ��û�Ͽ��ֽñ� �ٶ��ϴ�.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
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
    public DOBJ CTLins_virtual_upso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLins_virtual_upso_SEL2(Conn, dobj);           //  �Աݳ��� ��û���� Ȯ��
        if( dobj.getRetObject("SEL2").getRecord().getDouble("CNT") < 2)
        {
            dobj  = CALLins_virtual_upso_OSP1(Conn, dobj);           //  ȸ��������ν���
        }
        else
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="������� �߱��� �������Դϴ�. �Ϸ� �� �ٽ� ��û�Ͽ��ֽñ� �ٶ��ϴ�.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // �Աݳ��� ��û���� Ȯ��
    public DOBJ CALLins_virtual_upso_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLins_virtual_upso_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLins_virtual_upso_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(CNT)  AS  CNT  FROM   \n";
        query +=" (SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_COMMAND_TBL  WHERE  COMMAND_WORK  =  'MEM'   \n";
        query +=" AND  COMMAND_LINE  =  'APPLY'   \n";
        query +=" AND  COMMAND_STATUS  =  'B'   \n";
        query +=" AND  COMMAND_NO  =   \n";
        query +=" (SELECT  MAX(COMMAND_NO)  FROM  GIBU.FMS_VIRACC_MEM_IF_TBL  WHERE  MEMBER_ID  =  :UPSO_CD   \n";
        query +=" AND  TRAN_STATUS  =  'B')   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_VIRACC_MEM_IF_TBL  WHERE  MEMBER_ID  =  :UPSO_CD   \n";
        query +=" AND  TRAN_STATUS  =  'B'   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ȸ��������ν���
    public DOBJ CALLins_virtual_upso_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"UPSO_CD","UPSO_NM","INS_STAFF"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INS_STAFF = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //��� ���
            record.put("INS_STAFF",INS_STAFF);
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
        String   spname ="GIBU.SP_FMS_SEND_INSMEM";
        int[]    intypes={12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$ins_virtual_upso
    //##**$$mng_virtual_bank
    /*
    */
    public DOBJ CTLmng_virtual_bank(DOBJ dobj)
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
            dobj  = CALLmng_virtual_bank_SEL5(Conn, dobj);           //  �Աݳ��� ��û���� Ȯ��
            if( dobj.getRetObject("SEL2").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLmng_virtual_bank_MPD2(Conn, dobj);           //  �б�
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
            }
            else
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="������� ���ຯ���� �������Դϴ�. �Ϸ� �� �ٽ� ��û�Ͽ��ֽñ� �ٶ��ϴ�.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
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
    public DOBJ CTLmng_virtual_bank( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_virtual_bank_SEL5(Conn, dobj);           //  �Աݳ��� ��û���� Ȯ��
        if( dobj.getRetObject("SEL2").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLmng_virtual_bank_MPD2(Conn, dobj);           //  �б�
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        else
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="������� ���ຯ���� �������Դϴ�. �Ϸ� �� �ٽ� ��û�Ͽ��ֽñ� �ٶ��ϴ�.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // �Աݳ��� ��û���� Ȯ��
    public DOBJ CALLmng_virtual_bank_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_virtual_bank_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_virtual_bank_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(CNT)  AS  CNT  FROM   \n";
        query +=" (SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_COMMAND_TBL  WHERE  COMMAND_WORK  =  'MEM'   \n";
        query +=" AND  COMMAND_LINE  =  'BCHANGE'   \n";
        query +=" AND  COMMAND_STATUS  =  'B'   \n";
        query +=" AND  COMMAND_NO  IN   \n";
        query +=" (SELECT  COMMAND_NO  FROM  GIBU.FMS_VIRACC_MEM_BCH_TBL  WHERE  MEMBER_ID  =  :UPSO_CD   \n";
        query +=" AND  TRAN_STATUS  =  'B')   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_VIRACC_MEM_BCH_TBL  WHERE  MEMBER_ID  =  :UPSO_CD   \n";
        query +=" AND  TRAN_STATUS  =  'B'   \n";
        query +=" AND  TO_CHAR(INSERT_DT,  'YYYYMMDDHH24')  =  TO_CHAR(SYSDATE,  'YYYYMMDDYYYYMMDDHH24')) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �б�
    public DOBJ CALLmng_virtual_bank_MPD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD2");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("GBN").equals("S"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_virtual_bank_OSP1(Conn, dobj);           //  �����߰����� ���ν���
            }
        }
        return dobj;
    }
    // �����߰����� ���ν���
    public DOBJ CALLmng_virtual_bank_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String[]  incolumns ={"UPSO_CD","BANK_CD","ACCN_NUM","GBN","INS_STAFF"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INS_STAFF = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //��� ���
            record.put("INS_STAFF",INS_STAFF);
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
        String   spname ="GIBU.SP_FMS_SEND_CHGBNK";
        int[]    intypes={12, 12, 12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$mng_virtual_bank
    //##**$$mng_virtual_send_gbn
    /*
    */
    public DOBJ CTLmng_virtual_send_gbn(DOBJ dobj)
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
            dobj  = CALLmng_virtual_send_gbn_SEL6(Conn, dobj);           //  û�������߼۱��� ��ȸ
            dobj  = CALLmng_virtual_send_gbn_SEL10(Conn, dobj);           //  �������� ��뿩�� ��ȸ
            dobj  = CALLmng_virtual_send_gbn_XIUD8(Conn, dobj);           //  û�������߼۱��� �Է�
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
    public DOBJ CTLmng_virtual_send_gbn( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_virtual_send_gbn_SEL6(Conn, dobj);           //  û�������߼۱��� ��ȸ
        dobj  = CALLmng_virtual_send_gbn_SEL10(Conn, dobj);           //  �������� ��뿩�� ��ȸ
        dobj  = CALLmng_virtual_send_gbn_XIUD8(Conn, dobj);           //  û�������߼۱��� �Է�
        return dobj;
    }
    // û�������߼۱��� ��ȸ
    // ������� �����÷��� û�������߼۱����� �ִ��� Ȯ���ؼ� ������ UPDATE ���°�� INSERT�Ѵ�. ���ڴ� ����� ���������� Ȥ�ø� ����ó��
    public DOBJ CALLmng_virtual_send_gbn_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_virtual_send_gbn_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_virtual_send_gbn_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  GBN  IS  NOT  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �������� ��뿩�� ��ȸ
    public DOBJ CALLmng_virtual_send_gbn_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_virtual_send_gbn_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_virtual_send_gbn_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  BANK_CODE  =   \n";
        query +=" (SELECT  '0'  ||  BANK_CD  FROM  GIBU.TFMS_BANK  WHERE  BRAN_CD  IS  NULL   \n";
        query +=" AND  REMAK  =  'Y') ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // û�������߼۱��� �Է�
    // ���������� ���°��
    public DOBJ CALLmng_virtual_send_gbn_XIUD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD8");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_virtual_send_gbn_XIUD8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_virtual_send_gbn_XIUD8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TFMS_UPSO  \n";
        query +=" SET GBN = :GBN  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND USE_YN = 'Y'  \n";
        query +=" AND BANK_CODE = (SELECT MIN(BANK_CODE) FROM GIBU.TFMS_UPSO  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND USE_YN = 'Y')";
        sobj.setSql(query);
        sobj.setString("GBN", GBN);               //����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$mng_virtual_send_gbn
    //##**$$sel_virtual_list
    /*
    */
    public DOBJ CTLsel_virtual_list(DOBJ dobj)
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
            dobj  = CALLsel_virtual_list_SEL1(Conn, dobj);           //  ����ں� ���Ҹ��
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
    public DOBJ CTLsel_virtual_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_virtual_list_SEL1(Conn, dobj);           //  ����ں� ���Ҹ��
        return dobj;
    }
    // ����ں� ���Ҹ��
    public DOBJ CALLsel_virtual_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_virtual_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_virtual_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT UPSO_CD , UPSO_NM , OPBI_DAY , NEW_DAY , CLSBS_YRMN , VIRTUAL_YN , VIRTUAL_CNT , STAFF_CD , FIDU.GET_STAFF_NM(STAFF_CD) AS STAFF_NM  ";
        query +=" FROM (  ";
        query +=" SELECT B.UPSO_CD , UPSO_NM , OPBI_DAY , NEW_DAY , CLSBS_YRMN , (CASE WHEN COUNT(A.BANK_CODE) > 0 THEN 'Y' ELSE 'N' END) AS VIRTUAL_YN , COUNT(A.BANK_CODE) AS VIRTUAL_CNT , B.STAFF_CD  ";
        query +=" FROM GIBU.TFMS_UPSO A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE A.UPSO_CD(+) = B.UPSO_CD  ";
        query +=" AND A.USE_YN(+) = 'Y'  ";
        query +=" AND B.BRAN_CD = :BRAN_CD  ";
        if( !STAFF_CD.equals("") )
        {
            query +=" AND B.STAFF_CD = :STAFF_CD  ";
        }
        query +=" GROUP BY B.UPSO_CD, UPSO_NM, OPBI_DAY, NEW_DAY, CLSBS_YRMN, B.STAFF_CD )  ";
        query +=" WHERE NOT (VIRTUAL_YN = 'N'  ";
        query +=" AND CLSBS_YRMN IS NOT NULL)  ";
        query +=" ORDER BY VIRTUAL_CNT DESC, CLSBS_YRMN DESC  ";
        sobj.setSql(query);
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$sel_virtual_list
    //##**$$sel_virtual_temp
    /*
    */
    public DOBJ CTLsel_virtual_temp(DOBJ dobj)
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
            dobj  = CALLsel_virtual_temp_SEL1(Conn, dobj);           //  ������� �ӽ����� ��ȸ
            dobj  = CALLsel_virtual_temp_SEL2(Conn, dobj);           //  û������߼۱��� ��ȸ
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
    public DOBJ CTLsel_virtual_temp( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_virtual_temp_SEL1(Conn, dobj);           //  ������� �ӽ����� ��ȸ
        dobj  = CALLsel_virtual_temp_SEL2(Conn, dobj);           //  û������߼۱��� ��ȸ
        return dobj;
    }
    // ������� �ӽ����� ��ȸ
    public DOBJ CALLsel_virtual_temp_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_virtual_temp_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_virtual_temp_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  BANK_CODE  AS  BANK_CD  ,   \n";
        query +=" (SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  BANK_CD  =  A.BANK_CODE)  AS  BANK_NM  ,  ACCOUNT_NM  AS  REPTPRES  ,  GIBU.FT_GET_ACCOUNT_FORMAT(VIRTUAL_ACCOUNT_NUM)  AS  ACCN_NUM  ,  PAY_CHECK_YN  ,  DEPOSIT  ,  ON_TIME_YN  ,  END_DAY  ,  USE_YN  ,  REMAK  ,  NVL(MOD_DATE,  INS_DATE)  AS  MOD_DATE  FROM  GIBU.TFMS_UPSO  A  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y'  ORDER  BY  USE_YN  DESC,  BANK_CODE ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // û������߼۱��� ��ȸ
    public DOBJ CALLsel_virtual_temp_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_virtual_temp_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_virtual_temp_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GBN  ,  UPSO_CD  FROM  GIBU.TFMS_UPSO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  GBN  IS  NOT  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$sel_virtual_temp
    //##**$$sel_virtual_real
    /*
    */
    public DOBJ CTLsel_virtual_real(DOBJ dobj)
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
            dobj  = CALLsel_virtual_real_SEL1(Conn, dobj);           //  ������� �ֱ�������ȸ
            dobj  = CALLsel_virtual_real_SEL2(Conn, dobj);           //  ������� �ǽð�������ȸ
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
    public DOBJ CTLsel_virtual_real( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_virtual_real_SEL1(Conn, dobj);           //  ������� �ֱ�������ȸ
        dobj  = CALLsel_virtual_real_SEL2(Conn, dobj);           //  ������� �ǽð�������ȸ
        return dobj;
    }
    // ������� �ֱ�������ȸ
    public DOBJ CALLsel_virtual_real_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_virtual_real_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_virtual_real_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  BANK_CODE  AS  BANK_CD  ,   \n";
        query +=" (SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  BANK_CD  =  '0'  ||  A.BANK_CODE)  AS  BANK_NM  ,  ACCOUNT_NM  AS  REPTPRES  ,  GIBU.FT_GET_ACCOUNT_FORMAT(VIRTUAL_ACCOUNT_NUM)  AS  ACCN_NUM  ,  'S'  AS  GBN  ,  NVL(UPDATE_DT,  INSERT_DT)  AS  UPDATE_DT  FROM  GIBU.FMS_VIRACC_MEM_SH_RESULT_TBL  A  WHERE  MEMBER_ID  =  :UPSO_CD   \n";
        query +=" AND  COMMAND_NO  =   \n";
        query +=" (SELECT  MAX(COMMAND_NO)  FROM  GIBU.FMS_VIRACC_MEM_SH_RESULT_TBL  WHERE  MEMBER_ID  =  :UPSO_CD)  ORDER  BY  BANK_CODE ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������� �ǽð�������ȸ
    public DOBJ CALLsel_virtual_real_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_virtual_real_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_virtual_real_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  BANK_CODE  AS  BANK_CD  ,   \n";
        query +=" (SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  BANK_CD  =  '0'  ||  A.BANK_CODE)  AS  BANK_NM  ,  ACCOUNT_NM  AS  REPTPRES  ,  GIBU.FT_GET_ACCOUNT_FORMAT(VIRTUAL_ACCOUNT_NUM)  AS  ACCN_NUM  ,  'S'  AS  GBN  ,  NVL(UPDATE_DT,  INSERT_DT)  AS  UPDATE_DT  FROM  GIBU.FMS_VIRACC_MEM_SH_RESULT_TBL  A  WHERE  MEMBER_ID  =  :UPSO_CD   \n";
        query +=" AND  PROC_DT  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  COMMAND_NO  =   \n";
        query +=" (SELECT  MAX(COMMAND_NO)  FROM  GIBU.FMS_VIRACC_MEM_SH_RESULT_TBL  WHERE  MEMBER_ID  =  :UPSO_CD   \n";
        query +=" AND  PROC_DT  =  TO_CHAR(SYSDATE,  'YYYYMMDD'))  ORDER  BY  BANK_CODE ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$sel_virtual_real
    //##**$$req_virtual_rept
    /*
    */
    public DOBJ CTLreq_virtual_rept(DOBJ dobj)
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
            dobj  = CALLreq_virtual_rept_SEL2(Conn, dobj);           //  �Աݳ��� ��û���� Ȯ��
            if( dobj.getRetObject("SEL2").getRecord().getDouble("CNT") < 2)
            {
                dobj  = CALLreq_virtual_rept_OSP1(Conn, dobj);           //  �Աݳ�����û ���ν���
            }
            else
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="���� �Աݳ����� ��û���Դϴ�. ��� �� �ٽ� ��ȸ �� ��û���ֽñ� �ٶ��ϴ�.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
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
    public DOBJ CTLreq_virtual_rept( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLreq_virtual_rept_SEL2(Conn, dobj);           //  �Աݳ��� ��û���� Ȯ��
        if( dobj.getRetObject("SEL2").getRecord().getDouble("CNT") < 2)
        {
            dobj  = CALLreq_virtual_rept_OSP1(Conn, dobj);           //  �Աݳ�����û ���ν���
        }
        else
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="���� �Աݳ����� ��û���Դϴ�. ��� �� �ٽ� ��ȸ �� ��û���ֽñ� �ٶ��ϴ�.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // �Աݳ��� ��û���� Ȯ��
    public DOBJ CALLreq_virtual_rept_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLreq_virtual_rept_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLreq_virtual_rept_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(CNT)  AS  CNT  FROM   \n";
        query +=" (SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_COMMAND_TBL  WHERE  COMMAND_WORK  =  'DEP'   \n";
        query +=" AND  COMMAND_LINE  =  'RESULT'   \n";
        query +=" AND  COMMAND_STATUS  =  'B'  UNION  ALL   \n";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.FMS_VIRACC_DEPOSIT_IF_TBL  WHERE  RECV_TYPE  =  'A'   \n";
        query +=" AND  TRAN_STATUS  =  'B') ";
        sobj.setSql(query);
        return sobj;
    }
    // �Աݳ�����û ���ν���
    public DOBJ CALLreq_virtual_rept_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL2");         //�Աݳ��� ��û���� Ȯ�ο��� ������Ų OBJECT�Դϴ�.(CALLreq_virtual_rept_SEL2)
        dvobj.Println("OSP1");
        String[]  incolumns ={"TYPE","INS_STAFF"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INS_STAFF = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //��� ���
            record.put("INS_STAFF",INS_STAFF);
            ////
            String   TYPE ="M";         //TYPE
            record.put("TYPE",TYPE);
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
        String   spname ="GIBU.SP_FMS_SEND_REPT";
        int[]    intypes={12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$req_virtual_rept
    //##**$$sel_virtual_rept
    /*
    */
    public DOBJ CTLsel_virtual_rept(DOBJ dobj)
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
            dobj  = CALLsel_virtual_rept_SEL1(Conn, dobj);           //  �ֱ�6���� �Աݳ�����ȸ
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
    public DOBJ CTLsel_virtual_rept( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_virtual_rept_SEL1(Conn, dobj);           //  �ֱ�6���� �Աݳ�����ȸ
        return dobj;
    }
    // �ֱ�6���� �Աݳ�����ȸ
    public DOBJ CALLsel_virtual_rept_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_virtual_rept_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_virtual_rept_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  BANK_CD  ,   \n";
        query +=" (SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  BANK_CD  =  A.BANK_CD)  AS  BANK_NM  ,  GIBU.FT_GET_ACCOUNT_FORMAT(ACCN_NUM)  AS  ACCN_NUM  ,  REPTPRES  ,  REPT_AMT  ,  MAPPING_DAY  ,  RECV_DAY  ,  NOTE_YRMN  ,  GBN  ,  GBN2  FROM  (   \n";
        query +=" SELECT  0  AS  R_NUM  ,  ''  AS  REPT_DAY  ,  ''  AS  REPT_NUM  ,  '0'  ||  BANK_CODE  AS  BANK_CD  ,  VIRTUAL_ACCOUNT_NUM  AS  ACCN_NUM  ,  DEPOSIT_NAME  AS  REPTPRES  ,  TO_NUMBER(DEPOSIT)  AS  REPT_AMT  ,  ''  AS  MAPPING_DAY  ,  DEPOSIT_DAY  AS  RECV_DAY  ,  '  ~  '  AS  NOTE_YRMN  ,  DECODE(TRANSACTION_KIND,  'D',  '�Ա�',  '���')  AS  GBN  ,  '�̸���'  AS  GBN2  FROM  GIBU.FMS_VIRACC_DEPOSIT_RESULT_TBL  WHERE  MEMBER_ID  =  :UPSO_CD   \n";
        query +=" AND  DEPOSIT_DAY  =  TO_CHAR(SYSDATE,  'YYYYMMDD')   \n";
        query +=" AND  PROC_DT  =  TO_CHAR(SYSDATE,  'YYYYMMDD')  UNION  ALL   \n";
        query +=" SELECT  ROWNUM  AS  R_NUM  ,  REPT_DAY  ,  REPT_NUM  ,  BANK_CD  ,  ACCN_NUM  ,  REPTPRES  ,  REPT_AMT  ,  MAPPING_DAY  ,  RECV_DAY  ,  NOTE_YRMN  ,  '�Ա�'  AS  GBN  ,  DECODE(GBN,  '-',  '�̸���',  '����')  AS  GBN2  FROM  (   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.REPTPRES  ,  A.REPT_AMT  ,  A.MAPPING_DAY  ,  A.RECV_DAY  ,  MIN(B.NOTE_YRMN)  ||  '  ~  '  ||  MAX(B.NOTE_YRMN)  AS  NOTE_YRMN  ,  NVL(NVL(B.UPSO_CD,  C.UPSO_CD),  '-')  AS  GBN  FROM  GIBU.TBRA_REPT_VIRTUAL  A  ,  GIBU.TBRA_NOTE  B  ,  GIBU.TBRA_REPT_BALANCE  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD(+)   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM(+)   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD(+)   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  A.RECV_DAY  >=  TO_CHAR(ADD_MONTHS(SYSDATE,  -6),  'YYYYMM')  ||  '01'  GROUP  BY  A.REPT_DAY,  A.REPT_NUM,  A.BANK_CD,  A.ACCN_NUM,  A.REPT_AMT,  A.MAPPING_DAY,  A.RECV_DAY,  A.REPTPRES  ,  B.UPSO_CD,  C.UPSO_CD  ORDER  BY  RECV_DAY  DESC  )  )  A  ORDER  BY  R_NUM ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$sel_virtual_rept
    //##**$$end
}
