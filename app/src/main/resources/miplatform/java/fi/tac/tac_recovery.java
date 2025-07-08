
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac_recovery
{
    public tac_recovery()
    {
    }
    //##**$$bill_atax_update
    /*
    */
    public DOBJ CTLbill_atax_update(DOBJ dobj)
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
            dobj  = CALLbill_atax_update_SEL1(Conn, dobj);           //  ������꼭 ��ȣ ã��
            dobj  = CALLbill_atax_update_MPD2(Conn, dobj);           //  ��꼭 �������� ��ŭ  LOOP
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
    public DOBJ CTLbill_atax_update( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbill_atax_update_SEL1(Conn, dobj);           //  ������꼭 ��ȣ ã��
        dobj  = CALLbill_atax_update_MPD2(Conn, dobj);           //  ��꼭 �������� ��ŭ  LOOP
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ������꼭 ��ȣ ã��
    // �߸��Ǿ� �ִ� ��꼭��ȣ SELECT
    public DOBJ CALLbill_atax_update_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbill_atax_update_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbill_atax_update_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YYMM = dobj.getRetObject("S").getRecord().get("YYMM");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEMD_NUM,  A.BILL_KND,  A.ISS_DAY,A.BILL_NUM,  A.ATAX_AMT  TAX,  SUM(B.ATAX_AMT)  ATAX_AMT  FROM  FIDU.TTAC_BILL  A,  FIDU.TTAC_USEAPPRVBRE  B  WHERE  A.BILL_NUM  =  B.BILL_NUM   \n";
        query +=" AND  A.ISS_DAY  LIKE  :YYMM  ||'%'  GROUP  BY  A.DEMD_NUM,  A.ISS_DAY,A.BILL_NUM,  A.ATAX_AMT,  A.BILL_KND  HAVING  A.ATAX_AMT  <>  SUM(B.ATAX_AMT) ";
        sobj.setSql(query);
        sobj.setString("YYMM", YYMM);               //���
        return sobj;
    }
    // ��꼭 �������� ��ŭ  LOOP
    public DOBJ CALLbill_atax_update_MPD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD2");
        VOBJ dvobj = dobj.getRetObject("SEL1");         //������꼭 ��ȣ ã�⿡�� ������Ų OBJECT�Դϴ�.(CALLbill_atax_update_SEL1)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbill_atax_update_UNI5(Conn, dobj);           //  ���� ���� ����
            }
        }
        return dobj;
    }
    // ���� ���� ����
    public DOBJ CALLbill_atax_update_UNI5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbill_atax_update_UNI5UPD(dobj, dvobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLbill_atax_update_UNI5INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbill_atax_update_UNI5UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   ATAX_AMT = dvobj.getRecord().getDouble("ATAX_AMT");   //�ΰ��� �ݾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_BILL  \n";
        query +=" set ATAX_AMT=:ATAX_AMT  \n";
        query +=" where";
        sobj.setSql(query);
        sobj.setDouble("ATAX_AMT", ATAX_AMT);               //�ΰ��� �ݾ�
        return sobj;
    }
    private SQLObject SQLbill_atax_update_UNI5INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   ATAX_AMT = dvobj.getRecord().getDouble("ATAX_AMT");   //�ΰ��� �ݾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_BILL (ATAX_AMT)  \n";
        query +=" values(:ATAX_AMT )";
        sobj.setSql(query);
        sobj.setDouble("ATAX_AMT", ATAX_AMT);               //�ΰ��� �ݾ�
        return sobj;
    }
    //##**$$bill_atax_update
    //##**$$end
}
