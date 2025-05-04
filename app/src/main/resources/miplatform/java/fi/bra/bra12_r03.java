
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra12_r03
{
    public bra12_r03()
    {
    }
    //##**$$p_popup_sigudong
    /*
    */
    public DOBJ CTLp_popup_sigudong(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("ATTE").equals(""))
            {
                dobj  = CALLp_popup_sigudong_SEL1(Conn, dobj);           //  �õ� ��ȸ
            }
            else if(!dobj.getRetObject("S").getRecord().get("ATTE").equals("") && dobj.getRetObject("S").getRecord().get("MNG_ZIP").equals(""))
            {
                dobj  = CALLp_popup_sigudong_SEL5(Conn, dobj);           //  ���� ��ȸ
            }
            else
            {
                dobj  = CALLp_popup_sigudong_SEL6(Conn, dobj);           //  ��
            }
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
    public DOBJ CTLp_popup_sigudong( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("ATTE").equals(""))
        {
            dobj  = CALLp_popup_sigudong_SEL1(Conn, dobj);           //  �õ� ��ȸ
        }
        else if(!dobj.getRetObject("S").getRecord().get("ATTE").equals("") && dobj.getRetObject("S").getRecord().get("MNG_ZIP").equals(""))
        {
            dobj  = CALLp_popup_sigudong_SEL5(Conn, dobj);           //  ���� ��ȸ
        }
        else
        {
            dobj  = CALLp_popup_sigudong_SEL6(Conn, dobj);           //  ��
        }
        return dobj;
    }
    // �õ� ��ȸ
    public DOBJ CALLp_popup_sigudong_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLp_popup_sigudong_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_popup_sigudong_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CODE_NM  AS  ATTE  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00396'  ORDER  BY  CODE_NM ";
        sobj.setSql(query);
        return sobj;
    }
    // ���� ��ȸ
    public DOBJ CALLp_popup_sigudong_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLp_popup_sigudong_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_popup_sigudong_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //�õ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(MNG_ZIP,  '36110',  '  ',DSRCCNTY)  DSRCCNTY,  MNG_ZIP  FROM  (   \n";
        query +=" SELECT  DISTINCT  CITYCNTYDSRC  AS  DSRCCNTY  ,  SUBSTR(JUSO_CD,1,5)  AS  MNG_ZIP  FROM  FIDU.TENV_POST  WHERE  ATTE  =  :ATTE  ORDER  BY  CITYCNTYDSRC  ) ";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //�õ�
        return sobj;
    }
    // ��
    public DOBJ CALLp_popup_sigudong_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLp_popup_sigudong_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_popup_sigudong_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //�õ�
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //���� �����ȣ
        String   DSRCCNTY = dobj.getRetObject("S").getRecord().get("DSRCCNTY");   //����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  NVL(COURT_NM,  TOWNTWSHP)  AS  DONG  ,  ''  AS  MNG_ZIP  FROM  FIDU.TENV_POST  WHERE  ATTE  =  :ATTE   \n";
        query +=" AND  (CITYCNTYDSRC  IS  NULL   \n";
        query +=" OR  CITYCNTYDSRC  =  :DSRCCNTY)   \n";
        query +=" AND  SUBSTR(JUSO_CD,1,5)  =  :MNG_ZIP  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //�õ�
        sobj.setString("MNG_ZIP", MNG_ZIP);               //���� �����ȣ
        sobj.setString("DSRCCNTY", DSRCCNTY);               //����
        return sobj;
    }
    //##**$$p_popup_sigudong
    //##**$$p_popup_sigudong_old
    /* * ���α׷��� : bra12_r01
    * �ۼ��� : ������
    * �ۼ��� : 2009/08/24
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLp_popup_sigudong_old(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("ATTE").equals(""))
            {
                dobj  = CALLp_popup_sigudong_old_SEL1(Conn, dobj);           //  �õ� ��ȸ
            }
            else if(!dobj.getRetObject("S").getRecord().get("ATTE").equals("") && dobj.getRetObject("S").getRecord().get("DSRCCNTY").equals(""))
            {
                dobj  = CALLp_popup_sigudong_old_SEL5(Conn, dobj);           //  ���� ��ȸ
            }
            else
            {
                dobj  = CALLp_popup_sigudong_old_SEL6(Conn, dobj);           //  ��
            }
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
    public DOBJ CTLp_popup_sigudong_old( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("ATTE").equals(""))
        {
            dobj  = CALLp_popup_sigudong_old_SEL1(Conn, dobj);           //  �õ� ��ȸ
        }
        else if(!dobj.getRetObject("S").getRecord().get("ATTE").equals("") && dobj.getRetObject("S").getRecord().get("DSRCCNTY").equals(""))
        {
            dobj  = CALLp_popup_sigudong_old_SEL5(Conn, dobj);           //  ���� ��ȸ
        }
        else
        {
            dobj  = CALLp_popup_sigudong_old_SEL6(Conn, dobj);           //  ��
        }
        return dobj;
    }
    // �õ� ��ȸ
    // �����ȣ�� ���� ���� ���̺��� �õ� ������ ��ȸ�Ѵ�
    public DOBJ CALLp_popup_sigudong_old_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLp_popup_sigudong_old_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_popup_sigudong_old_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  ATTE  FROM  GIBU.TBRA_BRANZIP_MNG  ORDER  BY  ATTE ";
        sobj.setSql(query);
        return sobj;
    }
    // ���� ��ȸ
    // �����ȣ�� ���� ���� ���̺��� ���� ������ ��ȸ�Ѵ�
    public DOBJ CALLp_popup_sigudong_old_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLp_popup_sigudong_old_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_popup_sigudong_old_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //�õ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  DSRCCNTY  ,DSRCCNTY  as  MNG_ZIP  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  ATTE  =  :ATTE  ORDER  BY  DSRCCNTY ";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //�õ�
        return sobj;
    }
    // ��
    // �����ȣ�� ���� ���� ���̺��� �� ������ ��ȸ�Ѵ�
    public DOBJ CALLp_popup_sigudong_old_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLp_popup_sigudong_old_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_popup_sigudong_old_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //�õ�
        String   DSRCCNTY = dobj.getRetObject("S").getRecord().get("DSRCCNTY");   //����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MIN(DONG)  DONG  ,  MNG_ZIP  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  ATTE  =  :ATTE   \n";
        query +=" AND  DSRCCNTY  =  :DSRCCNTY  GROUP  BY  MNG_ZIP  ORDER  BY  MNG_ZIP ";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //�õ�
        sobj.setString("DSRCCNTY", DSRCCNTY);               //����
        return sobj;
    }
    //##**$$p_popup_sigudong_old
    //##**$$end
}
