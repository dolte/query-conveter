
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class com01_r02
{
    public com01_r02()
    {
    }
    //##**$$sel_bstyp
    /*
    */
    public DOBJ CTLsel_bstyp(DOBJ dobj)
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
            dobj  = CALLsel_bstyp_SEL1(Conn, dobj);           //  �����з���ȸ
            dobj  = CALLsel_bstyp_MPD4(Conn, dobj);           //  �б�
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
    public DOBJ CTLsel_bstyp( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_bstyp_SEL1(Conn, dobj);           //  �����з���ȸ
        dobj  = CALLsel_bstyp_MPD4(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �����з���ȸ
    public DOBJ CALLsel_bstyp_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_bstyp_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bstyp_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GRAD_GBN  ,  GRADNM  ,  MDM_CD  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  IN   \n";
        query +=" (SELECT  DISTINCT  BSTYP_CD  FROM  GIBU.TBRA_BSTYPGRAD)  ORDER  BY  GRAD_GBN ";
        sobj.setSql(query);
        return sobj;
    }
    // �б�
    public DOBJ CALLsel_bstyp_MPD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD4");
        VOBJ dvobj = dobj.getRetObject("SEL1");         //�����з���ȸ���� ������Ų OBJECT�Դϴ�.(CALLsel_bstyp_SEL1)
        dvobj.Println("MPD4");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("MDM_CD").equals(""))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsel_bstyp_SEL5(Conn, dobj);           //  ��ü�ڵ����ȸ
                dobj  = CALLsel_bstyp_ADD8( dobj);        //  ��ü�ڵ������
            }
            else
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsel_bstyp_SEL7(Conn, dobj);           //  ���ξ�����ȸ
                dobj  = CALLsel_bstyp_ADD9( dobj);        //  ���ξ�������
            }
        }
        return dobj;
    }
    // ��ü�ڵ����ȸ
    public DOBJ CALLsel_bstyp_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_bstyp_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bstyp_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MDM_CD = dobj.getRetObject("R").getRecord().get("MDM_CD");   //��ü �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MDM_CD  ,  MDM_CD_NM  FROM  FIDU.TENV_MDMCD  WHERE  MDM_CD  =  :MDM_CD ";
        sobj.setSql(query);
        sobj.setString("MDM_CD", MDM_CD);               //��ü �ڵ�
        return sobj;
    }
    // ���ξ�����ȸ
    public DOBJ CALLsel_bstyp_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_bstyp_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bstyp_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSTYP_CD = dobj.getRetObject("R").getRecord().get("GRAD_GBN");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BSTYP_CD  ||  A.GRAD_GBN  GRAD  ,  A.GRADNM  ,  A.STNDAMT  FROM  GIBU.TBRA_BSTYPGRAD  A  WHERE  A.BSTYP_CD  =  :BSTYP_CD  ORDER  BY  A.BSTYP_CD  ||  A.GRAD_GBN  ASC ";
        sobj.setSql(query);
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        return sobj;
    }
    // ��ü�ڵ������
    public DOBJ CALLsel_bstyp_ADD8(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("ADD8");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        VOBJ dvobj = dobj.getRetObject("SEL5");          //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("ADD8");
        rvobj = wutil.getAddedVobj(dobj,"ADD8","MDM_CD, MDM_CD_NM", dvobj );
        rvobj.setName("ADD8");
        rvobj.Println("ADD8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ���ξ�������
    public DOBJ CALLsel_bstyp_ADD9(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("ADD9");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        VOBJ dvobj = dobj.getRetObject("SEL7");          //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        rvobj = wutil.getAddedVobj(dobj,"ADD9","GRAD, GRADNM, STNDAMT", dvobj );
        rvobj.setName("ADD9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    //##**$$sel_bstyp
    //##**$$sel_bran_staff
    /*
    */
    public DOBJ CTLsel_bran_staff(DOBJ dobj)
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
            dobj  = CALLsel_bran_staff_SEL1(Conn, dobj);           //  ���δ������ȸ
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
    public DOBJ CTLsel_bran_staff( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_bran_staff_SEL1(Conn, dobj);           //  ���δ������ȸ
        return dobj;
    }
    // ���δ������ȸ
    // ����� ���� ������
    public DOBJ CALLsel_bran_staff_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_bran_staff_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bran_staff_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  STAFF_CD,  FIDU.GET_STAFF_NM(STAFF_CD)  AS  STAFF_NM  FROM  GIBU.TBRA_STAFF_CLCT_PRCON  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  PRCON_YRMN  =   \n";
        query +=" (SELECT  MAX(PRCON_YRMN)  FROM  GIBU.TBRA_STAFF_CLCT_PRCON  WHERE  BRAN_CD  =  :BRAN_CD)  GROUP  BY  BRAN_CD,  STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$sel_bran_staff
    //##**$$end
}
