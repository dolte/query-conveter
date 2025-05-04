
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_r18_1
{
    public bra04_r18_1()
    {
    }
    //##**$$rept_return_list
    /* * ���α׷��� : bra04_r18
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/02
    * ����    : ��ϵ� �Աݹ�ȯ ����Ʈ�� ��ȸ�Ѵ�.
    Input
    BRAN_CD (���� �ڵ�)
    END_YRMN (������)
    START_YRMN (���۳��)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLrept_return_list(DOBJ dobj)
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
            dobj  = CALLrept_return_list_SEL1(Conn, dobj);           //  ��ȯ����Ʈ
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
    public DOBJ CTLrept_return_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_return_list_SEL1(Conn, dobj);           //  ��ȯ����Ʈ
        return dobj;
    }
    // ��ȯ����Ʈ
    public DOBJ CALLrept_return_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_return_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_return_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.RETURN_DAY  ,  XA.RETURN_NUM  ,  XA.UPSO_CD  ,  XB.UPSO_NM  ,  XB.MNGEMSTR_NM  ,  XC.GRADNM  ,  XC.MONPRNCFEE  ,  XA.RETURN_AMT  ,  XA.COMIS_AMT  ,  XA.REMAK  ,  XD.HAN_NM  STAFF_NM  ,  XC.BSTYP_CD  ,  XB.BRAN_CD  FROM  GIBU.TBRA_REPT_RETURN  XA  ,  GIBU.TBRA_UPSO  XB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZA.UPSO_CD  =  ZB.UPSO_CD   \n";
        query +=" AND  ZA.JOIN_SEQ  =  ZB.JOIN_SEQ   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  'z'  )  XC  ,  INSA.TINS_MST01  XD  WHERE  XA.RETURN_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  XA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  XA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XB.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    //##**$$rept_return_list
    //##**$$end
}
