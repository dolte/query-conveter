
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_r15
{
    public bra01_r15()
    {
    }
    //##**$$upso_info
    /* * ���α׷��� : bra01_r15
    * �ۼ��� : ������
    * �ۼ��� : 2010/03/10
    * ���� : ��ƼĿ�� ��µ� ������ ������ ��ȸ�Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_info(DOBJ dobj)
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
            dobj  = CALLupso_info_SEL1(Conn, dobj);           //  ����������ȸ
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
    public DOBJ CTLupso_info( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_info_SEL1(Conn, dobj);           //  ����������ȸ
        return dobj;
    }
    // ����������ȸ
    public DOBJ CALLupso_info_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_info_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_info_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  UPSO_NM  ,  DECODE(MAIL_RCPT,  'U',  UPSO_NM,  '')  R_UPSO_NM  ,  DECODE(PAYPRES_GBN,  'B',  MNGEMSTR_NM,  PERMMSTR_NM)  RECV_NM  ,  DECODE(MAIL_RCPT,  'U',  UPSO_NEW_ZIP,  'B',  MNGEMSTR_NEW_ZIP,  'O',  PERMMSTR_NEW_ZIP)  POST_NO  ,  DECODE(MAIL_RCPT,  'U',  UPSO_NEW_ADDR1  ||  DECODE(UPSO_NEW_ADDR2,  '',  '',  ',  '||UPSO_NEW_ADDR2)  ||  UPSO_REF_INFO,  'B',  MNGEMSTR_NEW_ADDR1  ||  DECODE(MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||MNGEMSTR_NEW_ADDR2)  ||  MNGEMSTR_REF_INFO,    'O',  PERMMSTR_NEW_ADDR1  ||  DECODE(PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||PERMMSTR_NEW_ADDR2)  ||  PERMMSTR_REF_INFO)  ADDR  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$upso_info
    //##**$$end
}
