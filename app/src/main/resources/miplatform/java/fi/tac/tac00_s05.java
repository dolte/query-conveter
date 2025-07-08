
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac00_s05
{
    public tac00_s05()
    {
    }
    //##**$$saveMst
    /* * ���α׷��� : tac00_s05
    * �ۼ��� : �̼���
    * �ۼ��� : 2009/11/16
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLsaveMst(DOBJ dobj)
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
            dobj  = CALLsaveMst_DEL5(Conn, dobj);           //  ��������������
            dobj  = CALLsaveMst_INS6(Conn, dobj);           //  ��������������
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
    public DOBJ CTLsaveMst( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsaveMst_DEL5(Conn, dobj);           //  ��������������
        dobj  = CALLsaveMst_INS6(Conn, dobj);           //  ��������������
        return dobj;
    }
    // ��������������
    public DOBJ CALLsaveMst_DEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveMst_DEL5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveMst_DEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CTRY_CD = dvobj.getRecord().get("CTRY_CD");   //�����ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" FIDU.TTAC_ORGTAXRATE_MNG  \n";
        query +=" where CTRY_CD=:CTRY_CD";
        sobj.setSql(query);
        sobj.setString("CTRY_CD", CTRY_CD);               //�����ڵ�
        return sobj;
    }
    // ��������������
    public DOBJ CALLsaveMst_INS6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveMst_INS6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveMst_INS6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   CTRY_NM = dvobj.getRecord().get("CTRY_NM");   //������
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   INHABTAX_YN = dvobj.getRecord().get("INHABTAX_YN");   //�ֺ����Կ���
        double   TAXRATE = dvobj.getRecord().getDouble("TAXRATE");   //����
        String   CTRY_CD = dvobj.getRecord().get("CTRY_CD");   //�����ڵ�
        String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_ORGTAXRATE_MNG (INS_DATE, INSPRES_ID, CTRY_CD, TAXRATE, INHABTAX_YN, REMAK, CTRY_NM)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :CTRY_CD , :TAXRATE , :INHABTAX_YN , :REMAK , :CTRY_NM )";
        sobj.setSql(query);
        sobj.setString("CTRY_NM", CTRY_NM);               //������
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("INHABTAX_YN", INHABTAX_YN);               //�ֺ����Կ���
        sobj.setDouble("TAXRATE", TAXRATE);               //����
        sobj.setString("CTRY_CD", CTRY_CD);               //�����ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    //##**$$saveMst
    //##**$$end
}
