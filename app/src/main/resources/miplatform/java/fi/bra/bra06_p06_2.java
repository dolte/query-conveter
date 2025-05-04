
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra06_p06_2
{
    public bra06_p06_2()
    {
    }
    //##**$$save_clsed_satn
    /*
    */
    public DOBJ CTLsave_clsed_satn(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
            Conn.setAutoCommit(false);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLsave_clsed_satn_MIUD1(Conn, dobj);           //  ��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            Conn.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
                Conn.rollback();
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
    public DOBJ CTLsave_clsed_satn( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_clsed_satn_MIUD1(Conn, dobj);           //  ��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ��
    public DOBJ CALLsave_clsed_satn_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_clsed_satn_SEL8(Conn, dobj);           //  ��
                dobj  = CALLsave_clsed_satn_UPD5(Conn, dobj);           //  Ȯ���� ���
            }
        }
        return dobj;
    }
    // ��
    public DOBJ CALLsave_clsed_satn_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsave_clsed_satn_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_clsed_satn_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SYSDATE  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // Ȯ���� ���
    public DOBJ CALLsave_clsed_satn_UPD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_clsed_satn_UPD5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_clsed_satn_UPD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String SATN_DATE ="";        //���� ����
        String   CLSED_DAY = dvobj.getRecord().get("CLSED_DAY");   //�޾� ����
        String   SATNPRES_ID = dvobj.getRecord().get("SATNPRES_ID");   //������ ���
        String   SATN_YN = dvobj.getRecord().get("SATN_YN");   //���� ����(Y,N)
        String   CLSED_NUM = dvobj.getRecord().get("CLSED_NUM");   //�޾� ��ȣ
        String   CLSED_BRAN = dvobj.getRecord().get("CLSED_BRAN");   //�޾� ����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_CLSED  \n";
        query +=" set SATN_DATE=SYSDATE , SATN_YN=:SATN_YN , SATNPRES_ID=:SATNPRES_ID  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and CLSED_BRAN=:CLSED_BRAN  \n";
        query +=" and CLSED_NUM=:CLSED_NUM  \n";
        query +=" and CLSED_DAY=:CLSED_DAY";
        sobj.setSql(query);
        sobj.setString("CLSED_DAY", CLSED_DAY);               //�޾� ����
        sobj.setString("SATNPRES_ID", SATNPRES_ID);               //������ ���
        sobj.setString("SATN_YN", SATN_YN);               //���� ����(Y,N)
        sobj.setString("CLSED_NUM", CLSED_NUM);               //�޾� ��ȣ
        sobj.setString("CLSED_BRAN", CLSED_BRAN);               //�޾� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$save_clsed_satn
    //##**$$end
}
