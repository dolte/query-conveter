
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r22
{
    public tac10_r22()
    {
    }
    //##**$$tac10_r22_select
    /*
    */
    public DOBJ CTLtac10_r22_select(DOBJ dobj)
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
            dobj  = CALLtac10_r22_select_SEL1(Conn, dobj);           //  ��ǰ���� ������ȸ
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
    public DOBJ CTLtac10_r22_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r22_select_SEL1(Conn, dobj);           //  ��ǰ���� ������ȸ
        return dobj;
    }
    // ��ǰ���� ������ȸ
    public DOBJ CALLtac10_r22_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtac10_r22_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r22_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT Z.ORGAU_MB_CD, FIDU.GET_MB_NM (Z.ORGAU_MB_CD) AS ORGAU_MB_NM, (SELECT AVECLASS_CD_NM  ";
        query +=" FROM FIDU.TENV_AVECLASSCD  ";
        query +=" WHERE AVECLASS_CD = Z.AVECLASS_CD) AS AVECLASS_CD_NM, Z.AVECLASS_CD, Z.DISTR_YRMN, TRUNC(SUM (Z.DISTR_AMT),2) AS DISTR_AMT  ";
        query +=" FROM (  ";
        query +=" SELECT A.DISTR_YRMN, SUBSTR (A.MDM_CD, 1, 2) AS AVECLASS_CD, A.ORGAU_MB_CD, SUM (DISTR_AMT) AS DISTR_AMT  ";
        query +=" FROM FIDU.TTAC_PRODSUPPSUSP A  ";
        query +=" WHERE 1 = 1  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND A.ORGAU_MB_CD = :MB_CD  ";
        }
        query +=" AND SUPP_YRMN IS NULL  ";
        query +=" GROUP BY A.DISTR_YRMN, A.MDM_CD, A.ORGAU_MB_CD) Z  ";
        query +=" GROUP BY Z.ORGAU_MB_CD, Z.AVECLASS_CD, Z.DISTR_YRMN  ";
        query +=" ORDER BY Z.ORGAU_MB_CD, ORGAU_MB_NM, Z.AVECLASS_CD, Z.DISTR_YRMN  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        }
        return sobj;
    }
    //##**$$tac10_r22_select
    //##**$$end
}
