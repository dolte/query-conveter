
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_r16
{
    public bra04_r16()
    {
    }
    //##**$$upso_rept_ack_list
    /* * ���α׷��� : bra04_r16
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/24
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_rept_ack_list(DOBJ dobj)
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
            dobj  = CALLupso_rept_ack_list_SEL1(Conn, dobj);           //  �Ա�Ȯ�μ� �������������
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
    public DOBJ CTLupso_rept_ack_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_rept_ack_list_SEL1(Conn, dobj);           //  �Ա�Ȯ�μ� �������������
        return dobj;
    }
    // �Ա�Ȯ�μ� �������������
    public DOBJ CALLupso_rept_ack_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_rept_ack_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_rept_ack_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YEAR = dvobj.getRecord().get("YEAR");   //�˻��⵵
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.ISS_NUM  ,  A.ISS_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  B.UPSO_NM||'('||A.UPSO_CD||')'  UPSO_FULL_NM  ,  DECODE(B.PAYPRES_GBN,  'B',  B.MNGEMSTR_NM,  'O',  B.PERMMSTR_NM)  MNGEMSTR_NM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.ISS_AMT  ,  A.ISS_AMT_HANNM  ,  B.UPSO_NEW_ZIP  ,  B.BIOWN_NUM  UPSO_BIOWN_NUM  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  UPSO_ADDR  ,  C.BIPLC_NM  BRAN_NM  ,  C.PHON_NUM  ,  C.FAX_NUM  ,  C.POST_NUM  ,  C.ADDR  ||'  '||C.HNM  BRAN_ADDR  ,  D.BIOWN_NUM  ,  D.BIPLC_NM  FROM  GIBU.TBRA_REPT_ACK_ISS  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_BIPLC  C  ,  INSA.TCPM_BIPLC  D  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.ISS_YEAR  =  :YEAR   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  D.BIPLC_CD  =  '100'  ORDER  BY  A.ISS_NUM ";
        sobj.setSql(query);
        sobj.setString("YEAR", YEAR);               //�˻��⵵
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$upso_rept_ack_list
    //##**$$iss_list_per_branch
    /*
    */
    public DOBJ CTLiss_list_per_branch(DOBJ dobj)
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
            dobj  = CALLiss_list_per_branch_SEL1(Conn, dobj);           //  �Ա��� ���� ��� ��ȸ
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
    public DOBJ CTLiss_list_per_branch( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLiss_list_per_branch_SEL1(Conn, dobj);           //  �Ա��� ���� ��� ��ȸ
        return dobj;
    }
    // �Ա��� ���� ��� ��ȸ
    // ���κ� �⵵�� �Ա��� ������
    public DOBJ CALLiss_list_per_branch_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLiss_list_per_branch_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLiss_list_per_branch_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ISS_YEAR = dobj.getRetObject("S").getRecord().get("ISS_YEAR");   //���� �⵵
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BRAN_CD  ,A.ISS_YEAR  ,A.ISS_NUM  ,A.UPSO_CD  ,   \n";
        query +=" (SELECT  UPSO_NM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD)  AS  UPSO_NM  ,A.START_YRMN  ,A.END_YRMN  ,A.ISS_AMT  ,TO_CHAR(A.INS_DATE,  'YYYYMMDD')  INS_DATE  ,A.REQUEST_GBN  ,A.USE_GBN  FROM  GIBU.TBRA_REPT_ACK_ISS  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.ISS_YEAR  =  :ISS_YEAR  ORDER  BY  ISS_YEAR,ISS_NUM ";
        sobj.setSql(query);
        sobj.setString("ISS_YEAR", ISS_YEAR);               //���� �⵵
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$iss_list_per_branch
    //##**$$end
}
