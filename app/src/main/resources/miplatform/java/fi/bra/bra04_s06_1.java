
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s06_1
{
    public bra04_s06_1()
    {
    }
    //##**$$rept_bankbook_select
    /* * ���α׷��� : bra04_s06
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/04
    * ����    : ������ �Աݳ����� ��ȸ�Ѵ�.
    ������ �Աݳ��� : �������Ϸ� Upload �� ���� + �����ڰ� �������� �Է��� ����
    Input
    BRAN_CD (���� �ڵ�)
    END_DAY (������)
    START_DAY (������)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLrept_bankbook_select(DOBJ dobj)
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
            dobj  = CALLrept_bankbook_select_SEL1(Conn, dobj);           //  ������ ���� ��ȸ
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
    public DOBJ CTLrept_bankbook_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_bankbook_select_SEL1(Conn, dobj);           //  ������ ���� ��ȸ
        return dobj;
    }
    // ������ ���� ��ȸ
    // �Ա����ڸ� �������� ������ �Ա� ������ ��ȸ�Ѵ�.  ���� �й�, ���� �й�� ������ ������ �����Ѵ�
    public DOBJ CALLrept_bankbook_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_bankbook_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_bankbook_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //���� ��ȣ
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.REPTPRES  ,  A.REPT_AMT  ,  A.RECV_DAY  ,  A.RECEPT_BANK  ,  A.BRAN_CD  ,  A.REMAK  ,  A.OUT_AMT  ,  A.BALANCE  AS  REPT_BALANCE  ,  A.UPSO_CD  ,  C.UPSO_NM  ,  B.START_YRMN  ,  B.END_YRMN  ,  0  COMIS  ,  DISTR_GBN  ,  (   \n";
        query +=" SELECT  DECODE(COUNT(UPSO_CD),0,'N','Y')  ACCU_CNT  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  GOSO_YN  ,  '03'  REPT_GBN  ,  A.MAPPING_DAY  ,  A.BIGO  ,  A.AUTO_UPSO  ,  DECODE(A.UPSO_CD,  NULL,  'N',  'Y')  AS  SAVE_YN  ,  INSA.F_GET_STAFF_NM(C.STAFF_CD)  AS  STAFF_NM  FROM  GIBU.TBRA_REPT_TRANS  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_NUM  ,  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  RECV_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  REPT_GBN  =  '03'  GROUP  BY  UPSO_CD,  REPT_NUM  )  B,  GIBU.TBRA_UPSO  C  WHERE  A.RECV_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.DISTR_GBN  IS  NULL   \n";
        query +=" AND  A.ACCN_NUM  =  :ACCN_NUM   \n";
        query +=" AND  C.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  B.REPT_NUM(+)  =  A.REPT_NUM   \n";
        query +=" AND  B.UPSO_CD(+)  =  A.UPSO_CD  ORDER  BY  A.RECV_DAY,  A.REPT_DAY,  A.REPT_NUM ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    //##**$$rept_bankbook_select
    //##**$$end
}
