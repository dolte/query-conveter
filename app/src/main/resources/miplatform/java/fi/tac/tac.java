
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac
{
    public tac()
    {
    }
    //##**$$search_sup
    /*
    */
    public DOBJ CTLsearch_sup(DOBJ dobj)
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
            dobj  = CALLsearch_sup_SEL1(Conn, dobj);           //  �������� �۰��� ���������ݾ�
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
    public DOBJ CTLsearch_sup( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_sup_SEL1(Conn, dobj);           //  �������� �۰��� ���������ݾ�
        return dobj;
    }
    // �������� �۰��� ���������ݾ�
    public DOBJ CALLsearch_sup_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_sup_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_sup_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" select  PRODSUSP_START_DAY  as  SUBSTR  ,  SUBSTR(A.MDM_CD,5,2)  as  MDM_CD  ,  b.ORGAU_MB_CD  ,  B.ORGAU_NM  ,  A.RELS_AMT  from  FIDU.TTAC_SUPPSUSPRELS  A,  FIDU.TOPU_PRODRIGHTPRES  B  ,  FIDU.TOPU_PRODSUSP  C  where  1  =  1   \n";
        query +=" and  A.PROD_CD  =  B.PROD_CD   \n";
        query +=" and  A.RIGHTPRES_MNG_NUM  =  B.RIGHTPRES_MNG_NUM   \n";
        query +=" and  A.RIGHTPRES_SEQ  =  B.RIGHTPRES_SEQ   \n";
        query +=" and  A.RIGHTPRES_MNG_NUM  =  C.RIGHTPRES_MNG_NUM   \n";
        query +=" and  A.RIGHTPRES_SEQ  =  C.RIGHTPRES_SEQ   \n";
        query +=" and  A.PROD_CD  =  C.PROD_CD   \n";
        query +=" and  A.supp_yrmn  =  SUBSTR(C.PRODSUSP_END_DAY,1,6)   \n";
        query +=" and  A.SUPP_YRMN  =:SUPP_YRMN ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    //##**$$search_sup
    //##**$$end
}
