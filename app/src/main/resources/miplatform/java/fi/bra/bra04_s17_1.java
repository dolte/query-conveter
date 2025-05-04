
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s17_1
{
    public bra04_s17_1()
    {
    }
    //##**$$upso_rept_return
    /* * ���α׷��� : bra04_s17
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/04
    * ����    : �Աݹ�ȯ ó�� ���μ���, ��ȯ�� ó���� �� �� ����� �����Ѵ�.
    ��ȯ �߻� �� ���� �ֱ��� ������������ ��ȯ �ݾ׸�ŭ ������
    ��ȯ �߻� ������ ���� ��������, ��ȯ������ backup �Ͽ� ������ (TBRA_REPT_RETURN, TBRA_REPT_RETURN_NOTE)
    Input
    BRAN_CD (���� �ڵ�)
    CRUD (CRUD)
    REMAK (���)
    RETURN_AMT (��ȯ �ݾ�)
    RETURN_DAY (���� ����)
    RETURN_NUM (��ȯ ��ȣ)
    UPSO_CD (���� �ڵ�)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_rept_return(DOBJ dobj)
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
            dobj  = CALLupso_rept_return_OSP1(Conn, dobj);           //  ��ȯó��
            dobj  = CALLupso_rept_return_SEL1(Conn, dobj);           //  �������� ��ȸ
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
    public DOBJ CTLupso_rept_return( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_rept_return_OSP1(Conn, dobj);           //  ��ȯó��
        dobj  = CALLupso_rept_return_SEL1(Conn, dobj);           //  �������� ��ȸ
        return dobj;
    }
    // ��ȯó��
    public DOBJ CALLupso_rept_return_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"UPSO_CD","BRAN_CD","RETURN_DAY","RETURN_NUM","START_YRMN","END_YRMN","RETURN_AMT","COMIS_AMT","REMAK","INSPRES_ID","CRUD","RETURN_GBN","KOSCAP_AMT","FKMP_AMT","RIAK_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //����� ID
            record.put("INSPRES_ID",INSPRES_ID);
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
        String   spname ="GIBU.SP_PROC_REPT_RETURN2";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
        int[]    outtypes ={12};;
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
    // �������� ��ȸ
    public DOBJ CALLupso_rept_return_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_rept_return_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_rept_return_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RETURN_DAY = dobj.getRetObject("S").getRecord().get("RETURN_DAY");   //���� ����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  UPSO_CD  ,  REPT_AMT  ,  ERR_GBN  ,  ERR_CTENT  FROM  GIBU.TBRA_REPT_ERR  WHERE  REPT_DAY  =  :RETURN_DAY   \n";
        query +=" AND  REPT_GBN  =  '09'   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("RETURN_DAY", RETURN_DAY);               //���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$upso_rept_return
    //##**$$end
}
