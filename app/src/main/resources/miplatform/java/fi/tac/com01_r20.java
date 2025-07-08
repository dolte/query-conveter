
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class com01_r20
{
    public com01_r20()
    {
    }
    //##**$$mdm_cd_select
    /*
    */
    public DOBJ CTLmdm_cd_select(DOBJ dobj)
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
            dobj  = CALLmdm_cd_select_SEL1(Conn, dobj);           //  ��ü��ȸ
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
    public DOBJ CTLmdm_cd_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmdm_cd_select_SEL1(Conn, dobj);           //  ��ü��ȸ
        return dobj;
    }
    // ��ü��ȸ
    public DOBJ CALLmdm_cd_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmdm_cd_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmdm_cd_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MDM_CD_NM = dvobj.getRecord().get("MDM_CD_NM");   //��ü�̸�
        String   MDM_CD_ALL = dvobj.getRecord().get("MDM_CD_ALL");   //��ü�ڵ� ��.�ߺз� �˻���
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //��ü �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT MDM_CD, LARGECLASS_CD_NM, AVECLASS_CD_NM, SCLASS_CD_NM, MDM_CD_NM  ";
        query +=" FROM FIDU.TENV_MDMCD  ";
        query +=" WHERE USE_YN = 'Y'  ";
        if( !MDM_CD.equals("") )
        {
            query +=" AND SCLASS_CD= :MDM_CD  ";
        }
        if( !MDM_CD_ALL.equals("") )
        {
            query +=" AND AVECLASS_CD LIKE :MDM_CD_ALL || '%'  ";
        }
        if( !MDM_CD_NM.equals("") )
        {
            query +=" AND MDM_CD_NM= :MDM_CD_NM  ";
        }
        sobj.setSql(query);
        if(!MDM_CD_NM.equals(""))
        {
            sobj.setString("MDM_CD_NM", MDM_CD_NM);               //��ü�̸�
        }
        if(!MDM_CD_ALL.equals(""))
        {
            sobj.setString("MDM_CD_ALL", MDM_CD_ALL);               //��ü�ڵ� ��.�ߺз� �˻���
        }
        if(!MDM_CD.equals(""))
        {
            sobj.setString("MDM_CD", MDM_CD);               //��ü �ڵ�
        }
        return sobj;
    }
    //##**$$mdm_cd_select
    //##**$$banju_select
    /*
    */
    public DOBJ CTLbanju_select(DOBJ dobj)
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
            dobj  = CALLbanju_select_SEL1(Conn, dobj);           //  ���ֱ�� ������ȸ
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
    public DOBJ CTLbanju_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbanju_select_SEL1(Conn, dobj);           //  ���ֱ�� ������ȸ
        return dobj;
    }
    // ���ֱ�� ������ȸ
    public DOBJ CALLbanju_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbanju_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbanju_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USE_TTL = dvobj.getRecord().get("USE_TTL");   //�������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.APPRV_NUM AS APPRV_NUM, A.MDM_CD AS MDM_CD, FIDU.GET_MDM_NM(A.MDM_CD) AS MDM_NM, B.BSCON_CD AS BSCON_CD, FIDU.GET_BSCON_NM(B.BSCON_CD) AS BSCON_NM, A.USE_TTL AS USE_TTL  ";
        query +=" FROM FIDU.TLEV_USEAPPRV A, FIDU.TLEV_APPTNPRESINFO B  ";
        query +=" WHERE A.APPRV_NUM = B.APPRV_NUM  ";
        query +=" AND A.MDM_CD LIKE 'CD%'  ";
        query +=" AND B.APPTNPRES_GBN = '1'  ";
        if( !USE_TTL.equals("") )
        {
            query +=" AND A.USE_TTL LIKE '%' || :USE_TTL || '%'  ";
        }
        query +=" ORDER BY APPRV_NUM DESC  ";
        sobj.setSql(query);
        if(!USE_TTL.equals(""))
        {
            sobj.setString("USE_TTL", USE_TTL);               //�������
        }
        return sobj;
    }
    //##**$$banju_select
    //##**$$end
}
