
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac07_s03
{
    public tac07_s03()
    {
    }
    //##**$$searchTac07_s03
    /* * ���α׷��� : tac07_s03
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/04
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLsearchTac07_s03(DOBJ dobj)
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
            dobj  = CALLsearchTac07_s03_SEL1(Conn, dobj);           //  ä������ȸ
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
    public DOBJ CTLsearchTac07_s03( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchTac07_s03_SEL1(Conn, dobj);           //  ä������ȸ
        return dobj;
    }
    // ä������ȸ
    // ä���� ��ȸ �ŷ�ó���� �� 003�ΰ� �� ��ȸ�Ѵ�
    public DOBJ CALLsearchTac07_s03_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearchTac07_s03_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchTac07_s03_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCONHAN_NM = dobj.getRetObject("S").getRecord().get("BSCONHAN_NM");   //�ŷ�ó�ѱ� ��
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BSCON_CD,  A.BIOWN_GBN,  A.BSCON_GBN,  A.BANK_CD,  A.ACCN_NUM,  A.DEPOSITOR_NM,  A.BSCONHAN_NM,  A.BSCONENGST_NM,  A.BSTYP_CTENT,  A.BSCDTN_CTENT,  A.MB_ID,  A.REPPRES_NM,  A.INS_NUM,  A.FUND_AMT,  A.POST_NUM,  A.ADDR,  A.ADDR_DETED,  A.PHON_NUM,  A.EMAIL_ADDR,  A.FAX_NUM,  A.CONTR_GBN,  A.CONTRCCLSN_YN,  A.CONTRTRM_START_DAY,  A.CONTRTRM_END_DAY,  A.IFMNTMNGOBJ_YN,  A.USE_YN,  A.REMAK,  A.INS_DAY,  A.INSPRES_ID,  A.INS_DATE,  A.MODPRES_ID,  A.MOD_DATE,  A.MNG_RATE,  A.MSTR_MDM_CD,  A.MAIL_SNDBK_YN  FROM  FIDU.TLEV_BSCON  A  WHERE  A.BSCON_GBN='008'   \n";
        query +=" AND  A.BSCONHAN_NM  LIKE  :BSCONHAN_NM  ||'%'   \n";
        query +=" AND  A.BSCON_CD  LIKE  :BSCON_CD  ||'%'  ORDER  BY  A.BSCONHAN_NM  ASC ";
        sobj.setSql(query);
        sobj.setString("BSCONHAN_NM", BSCONHAN_NM);               //�ŷ�ó�ѱ� ��
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        return sobj;
    }
    //##**$$searchTac07_s03
    //##**$$end
}
