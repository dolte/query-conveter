
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s15
{
    public bra03_s15()
    {
    }
    //##**$$get_list
    /* ī���ڵ���ü��û������ ī��翡 ���� �� ���� ���信�� ������� 01�� ����
    ī��߱��� �Ǿ����� ī�尡 ���������� �ʾƼ� ����û���� �Ұ����� ��Ȳ�̴�.
    �̶� ������ ���ֿ��� ��ȭ���ؼ� �̷� ����� �˷��ְ� ���ִ� ������ �ؾ��Ѵ�.
    1) �׷��� ī��� �ڵ���ü �ϰڴ�. (ī��翡 ���ְ� ��ȭ�ؼ� û���ǵ��� ��ġ�ʿ�)
    2) ī��� �ڵ���ü �����ʰڴ�. (�������̳� �����ڵ���ü�� ����ϰڴ�)
    1)�� ������ ���� ������ ó�� ���ָ� �ȴ�. (rslt.apptn_rslt�� 01 �״�� ������Ų��.)
    2)�� ������ ���� (rslt.err_gbn�� 99�� �ٲٰ� ��û��Ұ��� SI�� �Է�& Ȯ�εǸ� 98�� �����Ų��.)
    */
    public DOBJ CTLget_list(DOBJ dobj)
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
            dobj  = CALLget_list_SEL1(Conn, dobj);           //  ��ó�� 01 �����ȸ
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
    public DOBJ CTLget_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_list_SEL1(Conn, dobj);           //  ��ó�� 01 �����ȸ
        return dobj;
    }
    // ��ó�� 01 �����ȸ
    public DOBJ CALLget_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLget_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.PROC_DAY  ,  A.SEQ_NUM  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.APPTN_DAY  ,  A.APPTN_GBN  ,  A.RESINUM  ,  A.PAYPRES_NM  ,  A.CARD_GBN  ,  A.EXPIRE_DAY  ,  A.CARD_NUM  ,  BIOWN_INSNUM  FROM  GIBU.TBRA_UPSO_AUTORSLT_TEST  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  RECPT_GBN_CD  =  '7'   \n";
        query +=" AND  APPTN_RSLT  =  '01'   \n";
        query +=" AND  (ERR_GBN  IS  NULL   \n";
        query +=" OR  ERR_GBN  =  '99') ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$get_list
    //##**$$end
}
