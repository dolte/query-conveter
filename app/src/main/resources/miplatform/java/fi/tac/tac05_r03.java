
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac05_r03
{
    public tac05_r03()
    {
    }
    //##**$$ttac_welffund_select
    /* * ���α׷��� : tac05_r03
    * �ۼ��� : ����ȯ
    * �ۼ��� : 2009/09/30
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLttac_welffund_select(DOBJ dobj)
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
            dobj  = CALLttac_welffund_select_SEL1(Conn, dobj);           //  ������ݴ������ȸ
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
    public DOBJ CTLttac_welffund_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttac_welffund_select_SEL1(Conn, dobj);           //  ������ݴ������ȸ
        return dobj;
    }
    // ������ݴ������ȸ
    public DOBJ CALLttac_welffund_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLttac_welffund_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_welffund_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   TO = dobj.getRetObject("S").getRecord().get("TO");   //��������
        String   SUPPSTOP_YN = dvobj.getRecord().get("SUPPSTOP_YN");   //�������� ����
        String   FROM = dobj.getRetObject("S").getRecord().get("FROM");   //��������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  A.SUPPSTOP_YN,  A.SUPPSTOP_REAS_CTENT,  A.SUPPSTART_DAY,  A.SUPPCOMPL_DAY,  A.SUPPBANK_CD,  A.SUPPACCN_NUM,  A.NEWACCN_YN,  B.HANMB_NM,  A.INSPRES_ID,  A.INS_DATE,  A.MODPRES_ID,  A.MOD_DATE  FROM  FIDU.TMEM_WELFFUNDOBJPRES  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD=B.MB_CD   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD||'%'   \n";
        query +=" AND  A.SUPPSTART_DAY  <=  :FROM   \n";
        query +=" AND  A.SUPPCOMPL_DAY  >=  :TO   \n";
        query +=" AND  A.SUPPSTOP_YN  LIKE  :SUPPSTOP_YN||'%' ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("TO", TO);               //��������
        sobj.setString("SUPPSTOP_YN", SUPPSTOP_YN);               //�������� ����
        sobj.setString("FROM", FROM);               //��������
        return sobj;
    }
    //##**$$ttac_welffund_select
    //##**$$end
}
