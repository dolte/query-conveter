
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac14_r01
{
    public tac14_r01()
    {
    }
    //##**$$tac14_r01
    /*
    */
    public DOBJ CTLfor_use_dis(DOBJ dobj)
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
            dobj  = CALLtac14_r01_SEL1(Conn, dobj);           //  �ܱ��Աݻ��� �й賻����
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
    public DOBJ CTLfor_use_dis( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac14_r01_SEL1(Conn, dobj);           //  �ܱ��Աݻ��� �й賻����
        return dobj;
    }
    // �ܱ��Աݻ��� �й賻����
    public DOBJ CALLtac14_r01_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtac14_r01_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac14_r01_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GET_BSCON_NM(A.BSCON_CD)  BSCON_CD  ,  A.RIGHTPRES_MB_CD  ,  A.PROD_CD  ,  B.PROD_TTL  ,  SUM(DECODE(SVC_CD,'ZA010101',DISTR_AMT,0))  AS  F2  ,  SUM(DECODE(SVC_CD,'ZA010102',DISTR_AMT,0))  AS  M3  ,  SUM(DECODE(SVC_CD,'ZA010103',DISTR_AMT,0))  AS  E4  FROM  FIDU.TDIS_DISTR  A,  FIDU.TOPU_PROD  B  WHERE  WRK_YRMN  =  :SUPP_YRMN   \n";
        query +=" AND  MDM_CD  =  'ZA0101'   \n";
        query +=" AND  A.PROD_CD  =  B.PROD_CD   \n";
        query +=" AND  A.RIGHTPRES_MB_CD  =  :MB_CD  GROUP  BY  BSCON_CD  ,  RIGHTPRES_MB_CD  ,  A.PROD_CD  ,  B.PROD_TTL  ORDER  BY  BSCON_CD  ASC ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    //##**$$tac14_r01
    //##**$$end
}
