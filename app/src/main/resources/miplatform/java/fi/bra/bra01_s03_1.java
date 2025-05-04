
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s03_1
{
    public bra01_s03_1()
    {
    }
    //##**$$upso_amt_select
    /* * ���α׷��� : bra01_s03
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/03
    * ���� : ������ ���� �����丮�� ��ȸ�Ѵ�. �뷡���� ��� �뷡�� �������� ���� ��ȸ�Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_amt_select(DOBJ dobj)
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
            dobj  = CALLupso_amt_select_SEL1(Conn, dobj);           //  ���һ��Ḯ��Ʈ
            dobj  = CALLupso_amt_select_SEL4(Conn, dobj);           //  ������ ������ �������
            if( dobj.getRetObject("SEL1").firstRecord().get("ROWNUM").equals("1") && dobj.getRetObject("SEL1").firstRecord().get("BSTYP_CD").equals("o"))
            {
                dobj  = CALLupso_amt_select_SEL2(Conn, dobj);           //  �뷡�������
            }
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
    public DOBJ CTLupso_amt_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_amt_select_SEL1(Conn, dobj);           //  ���һ��Ḯ��Ʈ
        dobj  = CALLupso_amt_select_SEL4(Conn, dobj);           //  ������ ������ �������
        if( dobj.getRetObject("SEL1").firstRecord().get("ROWNUM").equals("1") && dobj.getRetObject("SEL1").firstRecord().get("BSTYP_CD").equals("o"))
        {
            dobj  = CALLupso_amt_select_SEL2(Conn, dobj);           //  �뷡�������
        }
        return dobj;
    }
    // ���һ��Ḯ��Ʈ
    // �ش� ������ ���� �����丮�� ��ȸ�Ѵ�.
    public DOBJ CALLupso_amt_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_amt_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ROWNUM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  ,  TRIM(A.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  TRIM(A.BSTYP_CD)  BSTYP_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.MONPRNCFEE2  ,  A.APPL_DAY  ,  A.MCHNDAESU  ,  A.REMAK  ,  A.UPSO_CD  ,  A.USE_TIME  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =:UPSO_CD  ORDER  BY  JOIN_SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������ ������ �������
    public DOBJ CALLupso_amt_select_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_amt_select_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_select_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  APPL_DAY,  RATE  FROM  GIBU.TBRA_FEERATE_HISTY  WHERE  BSTYP_CD  =  GIBU.FT_GET_BSTYP_INFO(:UPSO_CD)  ORDER  BY  APPL_DAY ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �뷡�������
    // ù��° ������ �����ڵ尡 �뷡���� ��� �뷡�� �������� ���� �����ش�
    public DOBJ CALLupso_amt_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_amt_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_BRAN = dobj.getRetObject("SEL1").firstRecord().get("CHG_BRAN");   //���� ����
        String   CHG_NUM = dobj.getRetObject("SEL1").firstRecord().get("CHG_NUM");   //���� ��ȣ
        String   CHG_DAY = dobj.getRetObject("SEL1").firstRecord().get("CHG_DAY");   //���� ����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.BSTYP_CD  ||  A.GRAD_GBN  GRAD  ,  A.BSTYP_CD  BSTYP_CD  ,  A.GRAD_GBN  ,  A.AREA  ,  A.MCHNDAESU  ,  A.STNDAMT  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  ,  A.GRAD_NUM  ,  A.MCHNDAESU  *  A.STNDAMT  AMT  FROM  GIBU.TBRA_NOREBANG_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  A.GRAD_GBN  =  B.GRAD_GBN   \n";
        query +=" AND  A.CHG_DAY  =  :CHG_DAY   \n";
        query +=" AND  A.CHG_NUM  =  :CHG_NUM   \n";
        query +=" AND  A.CHG_BRAN  =  :CHG_BRAN ";
        sobj.setSql(query);
        sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ����
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("CHG_DAY", CHG_DAY);               //���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$upso_amt_select
    //##**$$end
}
