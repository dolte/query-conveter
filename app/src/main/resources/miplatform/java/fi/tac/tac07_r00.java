
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac07_r00
{
    public tac07_r00()
    {
    }
    //##**$$tac07_r00_select
    /* * ���α׷��� : tac07_r00
    * �ۼ��� : ����ȯ
    * �ۼ��� : 2009/10/13
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLtac07_r00_select(DOBJ dobj)
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
            dobj  = CALLtac07_r00_select_SEL1(Conn, dobj);           //  �ŷ�ó�˻�
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
    public DOBJ CTLtac07_r00_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac07_r00_select_SEL1(Conn, dobj);           //  �ŷ�ó�˻�
        return dobj;
    }
    // �ŷ�ó�˻�
    // �ŷ�ó�˻�
    public DOBJ CALLtac07_r00_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLtac07_r00_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac07_r00_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPPRES_NM = dobj.getRetObject("S").getRecord().get("REPPRES_NM");   //��ǥ�� ��
        String   BIOWN_GBN = dobj.getRetObject("S").getRecord().get("BIOWN_GBN");   //����� ����
        String   BSCONHAN_NM = dobj.getRetObject("S").getRecord().get("BSCONHAN_NM");   //�ŷ�ó�ѱ� ��
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BSCON_CD, BSCONHAN_NM, REPPRES_NM, CONTRCCLSN_YN, BIOWN_GBN, BANK_CD, ACCN_NUM, (SELECT DISTINCT LARGECLASS_CD_NM  ";
        query +=" FROM FIDU.TENV_MDMCD  ";
        query +=" WHERE LARGECLASS_CD = MSTR_MDM_CD) AS MSTR_MDM_CD_NM  ";
        query +=" FROM FIDU.TLEV_BSCON  ";
        query +=" WHERE BSCON_GBN = '008'  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD LIKE '%' || :BSCON_CD || '%'  ";
        }
        if( !BSCONHAN_NM.equals("") )
        {
            query +=" AND BSCONHAN_NM LIKE '%' || :BSCONHAN_NM || '%'  ";
        }
        if( !REPPRES_NM.equals("") )
        {
            query +=" AND REPPRES_NM LIKE '%' || :REPPRES_NM || '%'  ";
        }
        if( !BIOWN_GBN.equals("") )
        {
            query +=" AND BIOWN_GBN LIKE '%' || :BIOWN_GBN || '%'  ";
        }
        query +=" ORDER BY BSCON_CD  ";
        sobj.setSql(query);
        if(!REPPRES_NM.equals(""))
        {
            sobj.setString("REPPRES_NM", REPPRES_NM);               //��ǥ�� ��
        }
        if(!BIOWN_GBN.equals(""))
        {
            sobj.setString("BIOWN_GBN", BIOWN_GBN);               //����� ����
        }
        if(!BSCONHAN_NM.equals(""))
        {
            sobj.setString("BSCONHAN_NM", BSCONHAN_NM);               //�ŷ�ó�ѱ� ��
        }
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        }
        return sobj;
    }
    //##**$$tac07_r00_select
    //##**$$end
}
