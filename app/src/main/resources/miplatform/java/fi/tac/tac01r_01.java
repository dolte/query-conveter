
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac01r_01
{
    public tac01r_01()
    {
    }
    //##**$$searchBill_num
    /* * ���α׷��� : tac01r_01
    * �ۼ��� : ����ȯ
    * �ۼ��� : 2009/10/21
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLsearchBill_num(DOBJ dobj)
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
            dobj  = CALLsearchBill_num_SEL1(Conn, dobj);           //  ��꼭�� ��꼭��ȣ�� ��ȸ
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
    public DOBJ CTLsearchBill_num( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchBill_num_SEL1(Conn, dobj);           //  ��꼭�� ��꼭��ȣ�� ��ȸ
        return dobj;
    }
    // ��꼭�� ��꼭��ȣ�� ��ȸ
    public DOBJ CALLsearchBill_num_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearchBill_num_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchBill_num_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dobj.getRetObject("S").getRecord().get("DEMD_NUM");   //û������ȣ
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //��꼭 ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEMD_NUM,  A.BILL_NUM,  A.ISS_DAY,  A.BILL_KND,  A.BILL_GBN,  A.BSCON_CD,  A.BIPLC_GBN,  A.BRAN_CD,  A.BSCON_NM,  A.SUPPPRES_NM,  A.BSCON_INS_NUM,  A.BSCON_FNM_NM,  A.BSCON_REPPRES_NM,  A.BSCON_POST_NUM,  A.BSCON_ADDR,  A.BSCON_BSCDTN,  A.BSCON_BSTYP,  A.SUPPPRES_CD,  A.SUPPPRES_INS_NUM,  A.SUPPPRES_FNM_NM,  A.SUPPPRES_REPPRES_NM,  A.SUPPPRES_ADDR,  A.SUPPPRES_BSCDTN,  A.SUPPPRES_BSTYP,  A.SUPPTEMP_AMT,  A.ATAX_AMT,  A.REMAK,  A.ISS_YN,  A.INSPRES_ID,  A.INS_DATE,  A.MODPRES_ID,  A.MOD_DATE  FROM  FIDU.TTAC_BILL  A  WHERE  1  =  1   \n";
        query +=" AND  A.BILL_NUM  =  :BILL_NUM   \n";
        query +=" AND  A.DEMD_NUM  LIKE  :DEMD_NUM||'%' ";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //û������ȣ
        sobj.setString("BILL_NUM", BILL_NUM);               //��꼭 ��ȣ
        return sobj;
    }
    //##**$$searchBill_num
    //##**$$end
}
