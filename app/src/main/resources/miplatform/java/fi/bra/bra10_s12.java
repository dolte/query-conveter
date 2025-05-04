
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s12
{
    public bra10_s12()
    {
    }
    //##**$$sel_bscon_rate
    /*
    */
    public DOBJ CTLsel_bscon_rate(DOBJ dobj)
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
            dobj  = CALLsel_bscon_rate_SEL1(Conn, dobj);           //  Ÿ��ü ����������ȸ
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
    public DOBJ CTLsel_bscon_rate( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_bscon_rate_SEL1(Conn, dobj);           //  Ÿ��ü ����������ȸ
        return dobj;
    }
    // Ÿ��ü ����������ȸ
    public DOBJ CALLsel_bscon_rate_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_bscon_rate_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bscon_rate_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SEQ  ,  BSCON_CD  ,  BSCON_NM  ,  BSTYP_CD  ,  MDM_CD  ,  MNG_RATE  ,  APPL_DAY  ,  INSPRES_ID  ,  FIDU.GET_STAFF_NM(INSPRES_ID)  AS  INSPRES_NM  ,  INS_DATE  ,  MODPRES_ID  ,  FIDU.GET_STAFF_NM(MODPRES_ID)  AS  MODPRES_NM  ,  MOD_DATE  FROM  GIBU.TBRA_BSCON_MNG_RATE  ORDER  BY  BSCON_CD  ASC,  APPL_DAY  DESC ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$sel_bscon_rate
    //##**$$mng_bscon_rate
    /*
    */
    public DOBJ CTLmng_bscon_rate(DOBJ dobj)
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
            dobj  = CALLmng_bscon_rate_MIUD1(Conn, dobj);           //  �б�
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
    public DOBJ CTLmng_bscon_rate( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_bscon_rate_MIUD1(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLmng_bscon_rate_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLmng_bscon_rate_SEL8(Conn, dobj);           //  SEQ��ȣ �߰�
                dobj  = CALLmng_bscon_rate_INS5(Conn, dobj);           //  Ÿ��ü �������� ���
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_bscon_rate_UPD7(Conn, dobj);           //  Ÿ��ü �������� ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_bscon_rate_DEL6(Conn, dobj);           //  Ÿ��ü �������� ����
            }
        }
        return dobj;
    }
    // Ÿ��ü �������� ����
    public DOBJ CALLmng_bscon_rate_DEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_bscon_rate_DEL6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bscon_rate_DEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BSCON_MNG_RATE  \n";
        query +=" where SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // Ÿ��ü �������� ����
    public DOBJ CALLmng_bscon_rate_UPD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_bscon_rate_UPD7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bscon_rate_UPD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   BSCON_NM = dvobj.getRecord().get("BSCON_NM");   //�ŷ�ó ��
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //��ü �ڵ�
        double   MNG_RATE = dvobj.getRecord().getDouble("MNG_RATE");   //������
        String   APPL_DAY = dvobj.getRecord().get("APPL_DAY");   //���� ����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BSCON_MNG_RATE  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , APPL_DAY=:APPL_DAY , MNG_RATE=:MNG_RATE , MOD_DATE=SYSDATE , MDM_CD=:MDM_CD , BSTYP_CD=:BSTYP_CD , BSCON_NM=:BSCON_NM , BSCON_CD=:BSCON_CD  \n";
        query +=" where SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("BSCON_NM", BSCON_NM);               //�ŷ�ó ��
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("MDM_CD", MDM_CD);               //��ü �ڵ�
        sobj.setDouble("MNG_RATE", MNG_RATE);               //������
        sobj.setString("APPL_DAY", APPL_DAY);               //���� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // SEQ��ȣ �߰�
    public DOBJ CALLmng_bscon_rate_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_bscon_rate_SEL8(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bscon_rate_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  AS  SEQ  FROM  GIBU.TBRA_BSCON_MNG_RATE ";
        sobj.setSql(query);
        return sobj;
    }
    // Ÿ��ü �������� ���
    public DOBJ CALLmng_bscon_rate_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_bscon_rate_INS5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bscon_rate_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   BSCON_NM = dvobj.getRecord().get("BSCON_NM");   //�ŷ�ó ��
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //��ü �ڵ�
        double   MNG_RATE = dvobj.getRecord().getDouble("MNG_RATE");   //������
        String   APPL_DAY = dvobj.getRecord().get("APPL_DAY");   //���� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   SEQ = dobj.getRetObject("SEL8").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BSCON_MNG_RATE (INS_DATE, APPL_DAY, INSPRES_ID, MNG_RATE, MDM_CD, BSTYP_CD, BSCON_NM, SEQ, BSCON_CD)  \n";
        query +=" values(SYSDATE, :APPL_DAY , :INSPRES_ID , :MNG_RATE , :MDM_CD , :BSTYP_CD , :BSCON_NM , :SEQ , :BSCON_CD )";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("BSCON_NM", BSCON_NM);               //�ŷ�ó ��
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("MDM_CD", MDM_CD);               //��ü �ڵ�
        sobj.setDouble("MNG_RATE", MNG_RATE);               //������
        sobj.setString("APPL_DAY", APPL_DAY);               //���� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    //##**$$mng_bscon_rate
    //##**$$end
}
