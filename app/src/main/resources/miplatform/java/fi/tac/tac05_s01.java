
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac05_s01
{
    public tac05_s01()
    {
    }
    //##**$$tmem_welffund_select
    /*
    */
    public DOBJ CTLtmem_welffund_select(DOBJ dobj)
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
            dobj  = CALLtmem_welffund_select_SEL1(Conn, dobj);           //  ������ݴ���ڵ��
            dobj  = CALLtmem_welffund_select_SEL2(Conn, dobj);           //  ������ݳ�����ȸ
            dobj  = CALLtmem_welffund_select_SEL3(Conn, dobj);           //  ��ü����
            dobj  = CALLtmem_welffund_select_SEL4(Conn, dobj);           //  ������ݸ޸�
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
    public DOBJ CTLtmem_welffund_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtmem_welffund_select_SEL1(Conn, dobj);           //  ������ݴ���ڵ��
        dobj  = CALLtmem_welffund_select_SEL2(Conn, dobj);           //  ������ݳ�����ȸ
        dobj  = CALLtmem_welffund_select_SEL3(Conn, dobj);           //  ��ü����
        dobj  = CALLtmem_welffund_select_SEL4(Conn, dobj);           //  ������ݸ޸�
        return dobj;
    }
    // ������ݴ���ڵ��
    // ������ݴ���ڵ��
    public DOBJ CALLtmem_welffund_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtmem_welffund_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtmem_welffund_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MB_CD,  HANMB_NM,  INS_NUM,  PHON_NUM  FROM  FIDU.TMEM_MB  WHERE  MB_CD  =  :MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    // ������ݳ�����ȸ
    // ������ݳ�����ȸ
    public DOBJ CALLtmem_welffund_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtmem_welffund_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtmem_welffund_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUPPSTOP_YN,  SUPPSTOP_REAS_CTENT,  SUPPSTART_DAY,  SUPPCOMPL_DAY,  SUPPBANK_CD,  SUPPACCN_NUM,  NEWACCN_YN  FROM  FIDU.TMEM_WELFFUNDOBJPRES  WHERE  MB_CD  =  :MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    // ��ü����
    // ��ü����
    public DOBJ CALLtmem_welffund_select_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtmem_welffund_select_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtmem_welffund_select_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TRNF_DAY,  SUPPBANK_CD,  SUPPACCN_NUM,  SOGB_AMT,  TRNF_AMT  FROM  FIDU.TMEM_TRNFBRE  WHERE  MB_CD  =  :MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    // ������ݸ޸�
    // ������ݸ޸�
    public DOBJ CALLtmem_welffund_select_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtmem_welffund_select_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtmem_welffund_select_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MNG_NUM,  MENO_DAY,  MENO_CTENT  FROM  FIDU.TMEM_WELFFUNDMENO  WHERE  MB_CD  =  :MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    //##**$$tmem_welffund_select
    //##**$$end
}
