
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_r04
{
    public bra03_r04()
    {
    }
    //##**$$ocr_send_list
    /* * ���α׷��� : bra03_r04
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/11
    * ����    : �ش���� �߼۵� ���γ����� ��ȸ�Ѵ�.
    Input
    BRAN_CD (���� �ڵ�)
    DEMD_YRMN (û�� ���)
    OCR_PRNT (���� ��±���) : 1 : ����, 2 :����
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLocr_send_list(DOBJ dobj)
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
            dobj  = CALLocr_send_list_SEL1(Conn, dobj);           //  ���ι߼���ȸ
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
    public DOBJ CTLocr_send_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLocr_send_list_SEL1(Conn, dobj);           //  ���ι߼���ȸ
        return dobj;
    }
    // ���ι߼���ȸ
    public DOBJ CALLocr_send_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLocr_send_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLocr_send_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   OCR_PRNT = dobj.getRetObject("S").getRecord().get("OCR_PRNT");   //���� ��±���
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //û�� ���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.UPSO_CD  ,  B.UPSO_NM  ,  B.MNGEMSTR_NM  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ADDR  ,  A.START_YRMN||'  ~  '||A.END_YRMN  YRMN  ,  (A.TOT_DEMD_AMT  -  A.MONPRNCFEE  -  TOT_ADDT_AMT)  NONPY_AMT  ,  A.TOT_ADDT_AMT  ADDT_AMT  ,  A.MONPRNCFEE  USE_AMT  ,  A.TOT_DEMD_AMT  DEMD_AMT  ,  A.DEMD_MMCNT  ,  DECODE(A.OCR_PRNT,'1','����','2','����')  OCR_PRNT  FROM  GIBU.TBRA_DEMD_OCR  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.OCR_PRNT  IS  NOT  NULL   \n";
        query +=" AND  A.OCR_PRNT  =  DECODE(:OCR_PRNT,  NULL,  A.OCR_PRNT,  :OCR_PRNT)  ORDER  BY  A.UPSO_CD,  A.DEMD_MMCNT ";
        sobj.setSql(query);
        sobj.setString("OCR_PRNT", OCR_PRNT);               //���� ��±���
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$ocr_send_list
    //##**$$end
}
