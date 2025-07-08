
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac07_s02
{
    public tac07_s02()
    {
    }
    //##**$$tac07_s02select
    /*
    */
    public DOBJ CTLtac07_s02select(DOBJ dobj)
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
            dobj  = CALLtac07_s02select_SEL1(Conn, dobj);           //  ä��ä����ȸ
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
    public DOBJ CTLtac07_s02select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac07_s02select_SEL1(Conn, dobj);           //  ä��ä����ȸ
        return dobj;
    }
    // ä��ä����ȸ
    // ä��ä����ȸ
    public DOBJ CALLtac07_s02select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtac07_s02select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac07_s02select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   CLAIMDEBT_GBN = dobj.getRetObject("S").getRecord().get("CLAIMDEBT_GBN");   //ä��ä�� ����
        String   HANMB_NM = dobj.getRetObject("S").getRecord().get("HANMB_NM");   //ȸ�� �ŷ�ó �ڵ�
        String   COMPL_YN = dobj.getRetObject("S").getRecord().get("COMPL_YN");   //�Ϸ� ����
        String   CLAIM_KND = dobj.getRetObject("S").getRecord().get("CLAIM_KND");   //ä�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.MNG_NUM  ,  A.CLAIMDEBT_GBN,  A.CLAIM_KND,  A.FIXGRD_CTENT,  A.CLAIMPRES_MB_CD,  A.CLAIMPRES_NM,  A.CLAIMPRES_BANK_CD,  A.CLAIMPRES_ACCN_NUM,  A.CLAIM_BST_AMT,  A.CLAIM_PTTNRATE,  A.CLAIM_RFND_AMT,  A.REPAYPROC_START_DAY,  A.REPAYPROC_END_DAY,  A.COMPL_YN,  A.REMAK  FROM  FIDU.TMEM_CLAIMDEBT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.MB_CD  LIKE  '%'||:MB_CD   \n";
        query +=" AND  B.HANMB_NM  LIKE  '%'||:HANMB_NM||'%'   \n";
        query +=" AND  A.CLAIMDEBT_GBN  LIKE  '%'||:CLAIMDEBT_GBN   \n";
        query +=" AND  A.CLAIM_KND  LIKE  '%'||:CLAIM_KND   \n";
        query +=" AND  A.COMPL_YN  LIKE  '%'||:COMPL_YN  ORDER  BY  A.MB_CD,  A.MNG_NUM ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("CLAIMDEBT_GBN", CLAIMDEBT_GBN);               //ä��ä�� ����
        sobj.setString("HANMB_NM", HANMB_NM);               //ȸ�� �ŷ�ó �ڵ�
        sobj.setString("COMPL_YN", COMPL_YN);               //�Ϸ� ����
        sobj.setString("CLAIM_KND", CLAIM_KND);               //ä�� ����
        return sobj;
    }
    //##**$$tac07_s02select
    //##**$$end
}
