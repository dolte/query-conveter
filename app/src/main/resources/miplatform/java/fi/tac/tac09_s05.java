
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac09_s05
{
    public tac09_s05()
    {
    }
    //##**$$searchMst
    /* * ���α׷��� : tac09_s05
    * �ۼ��� : �̼���
    * �ۼ��� : 2009/11/16
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLsearchMst(DOBJ dobj)
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
            dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  ������������ȸ
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
    public DOBJ CTLsearchMst( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchMst_SEL1(Conn, dobj);           //  ������������ȸ
        return dobj;
    }
    // ������������ȸ
    public DOBJ CALLsearchMst_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearchMst_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INHABTAX_YN = dobj.getRetObject("S").getRecord().get("INHABTAX_YN");   //�ֺ����Կ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.CODE_NM CTRY_NM, A.CODE_CD CTRY_CD, A.BIGO, B.TAXRATE, B.INHABTAX_YN, B.REMAK, A.USE_YN  ";
        query +=" FROM FIDU.TENV_CODE A, FIDU.TTAC_ORGTAXRATE_MNG B  ";
        query +=" WHERE A.CODE_CD=B.CTRY_CD(+)  ";
        query +=" AND A.HIGH_CD='00003'  ";
        query +=" AND A.USE_YN='Y'  ";
        if( !INHABTAX_YN.equals("") )
        {
            query +=" AND NVL(B.INHABTAX_YN,' ') like :INHABTAX_YN ||'%'  ";
        }
        query +=" ORDER BY A.SORT_CD ASC  ";
        sobj.setSql(query);
        if(!INHABTAX_YN.equals(""))
        {
            sobj.setString("INHABTAX_YN", INHABTAX_YN);               //�ֺ����Կ���
        }
        return sobj;
    }
    //##**$$searchMst
    //##**$$end
}
