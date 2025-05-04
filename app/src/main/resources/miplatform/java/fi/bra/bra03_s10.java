
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s10
{
    public bra03_s10()
    {
    }
    //##**$$indtnpaper_prnt_mng
    /* * ���α׷��� : bra03_s10
    * �ۼ��� : ������
    * �ۼ��� : 2009/10/22
    * ����    : ����� �Ϸ�� �׸� ���� ��¿��� flag �� �����Ѵ�.
    Input :
    BRAN_CD (���� �ڵ�)
    CRET_GBN (OCR/MICR ���� ����)
    DEMD_YRMN (û�� ���)
    */
    public DOBJ CTLindtnpaper_prnt_mng(DOBJ dobj)
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
            dobj  = CALLindtnpaper_prnt_mng_MIUD2(Conn, dobj);           //  ����
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
    public DOBJ CTLindtnpaper_prnt_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLindtnpaper_prnt_mng_MIUD2(Conn, dobj);           //  ����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ����
    public DOBJ CALLindtnpaper_prnt_mng_MIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLindtnpaper_prnt_mng_UPD1(Conn, dobj);           //  ��¿�������
            }
        }
        return dobj;
    }
    // ��¿�������
    // ����� �ϰ� �Ǹ� ����� ID, ��� ���ڸ� �α����� ��� ID, ����� ���ڷ� UPDATE �Ѵ�. (���ҿ�����ȸ�� �߼���Ȳ������ ��ȸ�������� �̿�)
    public DOBJ CALLindtnpaper_prnt_mng_UPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLindtnpaper_prnt_mng_UPD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLindtnpaper_prnt_mng_UPD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String PRNT_DAY ="";        //��� ����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   DEMD_DAY = dvobj.getRecord().get("DEMD_DAY");   //û�� ����
        String   CRET_GBN = dvobj.getRecord().get("CRET_GBN");   //OCR/MICR ���� ����
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //û������ȣ
        String   PRNTPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_DEMD_INDTN  \n";
        query +=" set PRNTPRES_ID=:PRNTPRES_ID , PRNT_DAY=TO_CHAR(SYSDATE, 'YYYYMMDD')  \n";
        query +=" where DEMD_NUM=:DEMD_NUM  \n";
        query +=" and CRET_GBN=:CRET_GBN  \n";
        query +=" and DEMD_DAY=:DEMD_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("DEMD_DAY", DEMD_DAY);               //û�� ����
        sobj.setString("CRET_GBN", CRET_GBN);               //OCR/MICR ���� ����
        sobj.setString("DEMD_NUM", DEMD_NUM);               //û������ȣ
        sobj.setString("PRNTPRES_ID", PRNTPRES_ID);               //����� ID
        return sobj;
    }
    //##**$$indtnpaper_prnt_mng
    //##**$$end
}
