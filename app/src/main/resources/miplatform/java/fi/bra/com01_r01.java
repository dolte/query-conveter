
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class com01_r01
{
    public com01_r01()
    {
    }
    //##**$$sel_log_code
    /*
    */
    public DOBJ CTLsel_log_code(DOBJ dobj)
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
            dobj  = CALLsel_log_code_SEL1(Conn, dobj);           //  �����αװ����ڵ���ȸ
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
    public DOBJ CTLsel_log_code( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_log_code_SEL1(Conn, dobj);           //  �����αװ����ڵ���ȸ
        return dobj;
    }
    // �����αװ����ڵ���ȸ
    public DOBJ CALLsel_log_code_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_log_code_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_log_code_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   HIGH_CD = dobj.getRetObject("S").getRecord().get("HIGH_CD");   //�����ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CO_TYPE  ||  CO_CODE  AS  CODE_CD,  CO_NAME  AS  CODE_NM  FROM  LOG.KDS_CODE  WHERE  CO_TYPE  =  :HIGH_CD ";
        sobj.setSql(query);
        sobj.setString("HIGH_CD", HIGH_CD);               //�����ڵ�
        return sobj;
    }
    //##**$$sel_log_code
    //##**$$sel_bran_staff
    /*
    */
    public DOBJ CTLsel_bran_staff(DOBJ dobj)
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
            dobj  = CALLsel_bran_staff_SEL1(Conn, dobj);           //  ���δ������ȸ
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
    public DOBJ CTLsel_bran_staff( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_bran_staff_SEL1(Conn, dobj);           //  ���δ������ȸ
        return dobj;
    }
    // ���δ������ȸ
    // ����� ���� ������
    public DOBJ CALLsel_bran_staff_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_bran_staff_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bran_staff_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  STAFF_CD,  FIDU.GET_STAFF_NM(STAFF_CD)  AS  STAFF_NM  FROM  GIBU.TBRA_STAFF_ZONECHG  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  CHG_DAY  =   \n";
        query +=" (SELECT  MAX(CHG_DAY)  FROM  GIBU.TBRA_STAFF_ZONECHG  WHERE  BRAN_CD  =  :BRAN_CD)  GROUP  BY  BRAN_CD,  STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$sel_bran_staff
    //##**$$end
}
