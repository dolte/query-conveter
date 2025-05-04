
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_s03_test
{
    public bra05_s03_test()
    {
    }
    //##**$$use_amt_test
    /*
    */
    public DOBJ CTLuse_amt_test(DOBJ dobj)
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
            dobj  = CALLuse_amt_test_SEL3(Conn, dobj);           //  RECV_DAY ����
            dobj  = CALLuse_amt_test_OSP1(Conn, dobj);           //  û�� �ݾ� ����
            dobj  = CALLuse_amt_test_SEL2(Conn, dobj);           //  ��������Ƿڿ���,�����
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
    public DOBJ CTLuse_amt_test( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLuse_amt_test_SEL3(Conn, dobj);           //  RECV_DAY ����
        dobj  = CALLuse_amt_test_OSP1(Conn, dobj);           //  û�� �ݾ� ����
        dobj  = CALLuse_amt_test_SEL2(Conn, dobj);           //  ��������Ƿڿ���,�����
        return dobj;
    }
    // RECV_DAY ����
    //""���� �ٷ� �ѱ�� ������ ���� �߰��ܰ踦 ��
    public DOBJ CALLuse_amt_test_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLuse_amt_test_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLuse_amt_test_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  AS  RECV_DAY  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // û�� �ݾ� ����
    // �ش� ������ û�� �ݾ׸� �����ϱ� ���� ���ν��� (GIBU.SP_GET_USE_AMT) �� ȣ���Ѵ�
    public DOBJ CALLuse_amt_test_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"UPSO_CD","START_YRMN","END_YRMN","CRET_GBN","RECV_DAY"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   CRET_GBN ="O";         //û�� ���
            record.put("CRET_GBN",CRET_GBN);
            ////
            String   RECV_DAY = dobj.getRetObject("SEL3").getRecord().get("RECV_DAY");         //���� ����
            record.put("RECV_DAY",RECV_DAY);
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
        String   spname ="GIBU.SP_GET_ACCU_AMT";
        int[]    intypes={12, 12, 12, 12, 12};;
        String[] outcolnms={"P_BSTYP_CD","P_UPSO_GRAD","P_MONPRNCFEE","P_DEMD_GBN","P_DEMD_MMCNT","P_TUSE_AMT","P_TADDT_AMT","P_TEADDT_AMT","P_DSCT_AMT","P_TDEMD_AMT"};;
        int[]    outtypes ={12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
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
    // ��������Ƿڿ���,�����
    public DOBJ CALLuse_amt_test_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLuse_amt_test_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLuse_amt_test_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   USE_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("P_TUSE_AMT");   //��� �ݾ�
        double   ADDT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("P_TUSE_AMT")*0.3;   //���� �ݾ�
        double   TOT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("P_TUSE_AMT")+(dobj.getRetObject("OSP1").getRecord().getDouble("P_TUSE_AMT")*0.3);   //�� �ݾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :USE_AMT  AS  REQ_ORG_AMT  ,  :ADDT_AMT  AS  REQ_ADDT_AMT  ,  :TOT_AMT  AS  REQ_TOT_AMT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setDouble("USE_AMT", USE_AMT);               //��� �ݾ�
        sobj.setDouble("ADDT_AMT", ADDT_AMT);               //���� �ݾ�
        sobj.setDouble("TOT_AMT", TOT_AMT);               //�� �ݾ�
        return sobj;
    }
    //##**$$use_amt_test
    //##**$$end
}
