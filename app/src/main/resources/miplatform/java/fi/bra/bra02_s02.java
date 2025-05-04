
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra02_s02
{
    public bra02_s02()
    {
    }
    //##**$$recog_regist
    /*
    */
    public DOBJ CTLrecog_regist(DOBJ dobj)
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
            dobj  = CALLrecog_regist_MIUD1(Conn, dobj);           //  ����������ΰ���
            if(dobj.getRtncode() == 9)
            {
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
    public DOBJ CTLrecog_regist( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrecog_regist_MIUD1(Conn, dobj);           //  ����������ΰ���
        if(dobj.getRtncode() == 9)
        {
            return dobj;
        }
        return dobj;
    }
    // ����������ΰ���
    // ����������ΰ���
    public DOBJ CALLrecog_regist_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MIUD1");
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrecog_regist_INS5(Conn, dobj);           //  ������ε��
            }
        }
        return dobj;
    }
    // ������ε��
    // ������ε��
    public DOBJ CALLrecog_regist_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrecog_regist_INS5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap record = new HashMap();
        record.put("UPDCNT",updcnt+"");
        rvobj.addRecord(record);
        rvobj.setName("INS5") ;
        rvobj.Println("INS5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrecog_regist_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USETRM_START_DAY = dvobj.getRecord().get("USETRM_START_DAY");   //���Ⱓ ���� ����
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //���� ��ȣ
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //��ü �ڵ�
        String   APPRVDAPVL_REAS = dvobj.getRecord().get("APPRVDAPVL_REAS");   //���κҰ� ����
        String   USE_LOCN_CTENT = dvobj.getRecord().get("USE_LOCN_CTENT");   //��� ��� ����
        String   UDTKPRES_ID = dvobj.getRecord().get("UDTKPRES_ID");   //����� ID
        String   IFMNT_GBN = dvobj.getRecord().get("IFMNT_GBN");
        String   USE_LOC = dvobj.getRecord().get("USE_LOC");
        String   USE_METH = dvobj.getRecord().get("USE_METH");   //��� ���
        String   TTL = dvobj.getRecord().get("TTL");   //����
        String   USETRM_END_DAY = dvobj.getRecord().get("USETRM_END_DAY");   //���Ⱓ ���� ����
        String   USEAPPRVPROC_STAT = dvobj.getRecord().get("USEAPPRVPROC_STAT");   //������ó�� ����
        String   UDTKDEPT_CD = dvobj.getRecord().get("UDTKDEPT_CD");   //���μ� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TLEV_USEAPPRV (UDTKDEPT_CD, USEAPPRVPROC_STAT, USETRM_END_DAY, TTL, USE_METH, USE_LOC, IFMNT_GBN, UDTKPRES_ID, USE_LOCN_CTENT, APPRVDAPVL_REAS, MDM_CD, BRAN_CD, APPRV_NUM, USETRM_START_DAY)  \n";
        query +=" values(:UDTKDEPT_CD , :USEAPPRVPROC_STAT , :USETRM_END_DAY , :TTL , :USE_METH , :USE_LOC , :IFMNT_GBN , :UDTKPRES_ID , :USE_LOCN_CTENT , :APPRVDAPVL_REAS , :MDM_CD , :BRAN_CD , :APPRV_NUM , :USETRM_START_DAY )";
        sobj.setSql(query);
        sobj.setString("USETRM_START_DAY", USETRM_START_DAY);               //���Ⱓ ���� ����
        sobj.setString("APPRV_NUM", APPRV_NUM);               //���� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("MDM_CD", MDM_CD);               //��ü �ڵ�
        sobj.setString("APPRVDAPVL_REAS", APPRVDAPVL_REAS);               //���κҰ� ����
        sobj.setString("USE_LOCN_CTENT", USE_LOCN_CTENT);               //��� ��� ����
        sobj.setString("UDTKPRES_ID", UDTKPRES_ID);               //����� ID
        sobj.setString("IFMNT_GBN", IFMNT_GBN);
        sobj.setString("USE_LOC", USE_LOC);
        sobj.setString("USE_METH", USE_METH);               //��� ���
        sobj.setString("TTL", TTL);               //����
        sobj.setString("USETRM_END_DAY", USETRM_END_DAY);               //���Ⱓ ���� ����
        sobj.setString("USEAPPRVPROC_STAT", USEAPPRVPROC_STAT);               //������ó�� ����
        sobj.setString("UDTKDEPT_CD", UDTKDEPT_CD);               //���μ� �ڵ�
        return sobj;
    }
    //##**$$recog_regist
    //##**$$end
}
