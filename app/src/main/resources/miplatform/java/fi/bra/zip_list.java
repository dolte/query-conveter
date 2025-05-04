
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class zip_list
{
    public zip_list()
    {
    }
    //##**$$zip_list
    /* * ���α׷��� : zip_list
    * �ۼ��� : ������
    * �ۼ��� : 2009/08/11
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLbra10_s04(DOBJ dobj)
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
            dobj  = CALLzip_list_SEL1(Conn, dobj);           //  �����ȣ��ȸ
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
    public DOBJ CTLbra10_s04( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLzip_list_SEL1(Conn, dobj);           //  �����ȣ��ȸ
        return dobj;
    }
    // �����ȣ��ȸ
    public DOBJ CALLzip_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLzip_list_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USE_YN = dobj.getRetObject("S").getRecord().get("USE_YN");   //��뱸��
        String   TO_ZIP = dobj.getRetObject("S").getRecord().get("TO_ZIP");   //�����ȣ �˻� TO
        String   FROM_ZIP = dobj.getRetObject("S").getRecord().get("FROM_ZIP");   //�����ȣ �˻� FROM
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  POST_SNUM  ,  MNG_ZIP  ,  ZIP  ,  ATTE  ,  DSRCCNTY  ,  DONG  ,  HNM  ,  USE_YN  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  ZIP  BETWEEN  :FROM_ZIP   \n";
        query +=" AND  :TO_ZIP   \n";
        query +=" AND  USE_YN  LIKE  :USE_YN||'%'  ORDER  BY  MNG_ZIP ";
        sobj.setSql(query);
        sobj.setString("USE_YN", USE_YN);               //��뱸��
        sobj.setString("TO_ZIP", TO_ZIP);               //�����ȣ �˻� TO
        sobj.setString("FROM_ZIP", FROM_ZIP);               //�����ȣ �˻� FROM
        return sobj;
    }
    //##**$$zip_list
    //##**$$end
}
