
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_r05
{
    public bra05_r05()
    {
    }
    //##**$$bpap_reprint
    /* * ���α׷��� : bra05_r05
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/30
    * ���� :
    GIBU.TBRA_BPAP_PRNT_HISTY�� BPAP_GBN = '2'�� ������ ��ȸ�Ѵ�.
    1.SEL.SEL1 :
    - ������ �����̸�(BANK), ���¹�ȣ(ACCT_NO)�� ���� CS���� ������ �� �״�� ����ϱ�� ��
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLbpap_reprint(DOBJ dobj)
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
            dobj  = CALLbpap_reprint_SEL1(Conn, dobj);           //  �ְ� �����
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
    public DOBJ CTLbpap_reprint( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbpap_reprint_SEL1(Conn, dobj);           //  �ְ� �����
        return dobj;
    }
    // �ְ� �����
    public DOBJ CALLbpap_reprint_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbpap_reprint_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_reprint_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  B.UPSO_NM  ,  B.MNGEMSTR_NM  AS  RECVPRES  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.MONPRNCFEE  ,  A.NONPY_AMT  ,  A.TOT_ADDT_AMT  +  A.TOT_EADDT_AMT  AS  ADDT_AMT  ,  A.TOT_EADDT_AMT  AS  EXT_ADDT_AMT  ,  A.TOT_DEMD_AMT  AS  TOT_AMT  ,  C.BIPLC_NM  AS  BRAN_NM  ,  C.ADDR||'  '||C.HNM  AS  BRAN_ADDR  ,  C.POST_NUM  AS  BRAN_ZIP  ,   \n";
        query +=" (SELECT  IPPBX_INPHONE_NUM  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  B.STAFF_CD)  AS  BRAN_PHON  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  AS  RECV_ADDR  ,  B.UPSO_NEW_ZIP  AS  RECV_ZIP  ,  A.PAY_DAY  ,  DECODE(B.BRAN_CD,  'A',  '��������','B',  '��������','C',  '��������',  'E'  ,  '��������'  ,  'F'  ,  '��������'  ,  'G'  ,  '����'  ,  'H'  ,  '����'  ,  'I'  ,  '����'  ,  'J'  ,  '����'  ,  'K'  ,  '����'  ,  'L'  ,  '��������'  ,  'M'  ,  '����'  ,  'N'  ,  '�λ�����'  ,  'O'  ,  '����')  AS  BANK  ,  DECODE(B.BRAN_CD,  'A'  ,  '695037-01-001228'  ,  'B'  ,  '695037-01-001257'  ,  'C'  ,  '695037-01-001231'  ,  'E'  ,  '695037-01-001260'  ,  'F'  ,  '695037-01-001244'  ,  'G'  ,  '209-01-581021'  ,  'H'  ,  '311-01-155951'  ,  'I'  ,  '311-01-155951'  ,  'J'  ,  '511-01-073417'  ,  'K'  ,  '661-01-033882'  ,  'L'  ,  '632-01-0046-816'  ,  'M'  ,  '815135-51-018283'  ,  'N'  ,  '131-01-000342-2'  ,  'O'  ,  '901017-51-011928')  AS  ACCT_NO  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  AS  STAFF_NM  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_BIPLC  C  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  B.UPSO_CD  =  DECODE(:UPSO_CD,NULL,B.UPSO_CD,:UPSO_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.BPAP_DAY  LIKE  :YRMN  ||'%'   \n";
        query +=" AND  A.BPAP_GBN  =  '2'   \n";
        query +=" AND  C.GIBU  =  B.BRAN_CD   \n";
        query +=" AND  B.STAFF_CD  =  NVL(:STAFF_CD,  B.STAFF_CD)  ORDER  BY  B.STAFF_CD,  B.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("YRMN", YRMN);               //���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$bpap_reprint
    //##**$$end
}
