
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s16_1
{
    public bra01_s16_1()
    {
    }
    //##**$$upso_list_kylog
    /*
    */
    public DOBJ CTLupso_list_kylog(DOBJ dobj)
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
            dobj  = CALLupso_list_kylog_SEL1(Conn, dobj);           //  ���Ҹ��
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
    public DOBJ CTLupso_list_kylog( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_list_kylog_SEL1(Conn, dobj);           //  ���Ҹ��
        return dobj;
    }
    // ���Ҹ��
    public DOBJ CALLupso_list_kylog_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_list_kylog_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_list_kylog_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   CO_STATUS = dobj.getRetObject("S").getRecord().get("CO_STATUS");   //�����
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(A.INS_DATE,  'YYYY-MM-DD')  AS  INS_DATE  ,  A.UPSO_CD  ,  A.BRAN_CD  ,  AA.DEPT_NM  AS  BRAN_NM  ,  A.UPSO_NM  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,3)  AS  GRAD_CD  ,  AAA.GRADNM  AS  GRAD_NM  ,  A.MNGEMSTR_NM  ,  A.PERMMSTR_NM  ,   \n";
        query +=" (SELECT  --+  INDEX_DESC  (KDS_SHOPROOM  PK_KDS_SM_SEQ)  \n  SERIAL_NO  FROM  LOG.KDS_SHOPROOM  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  CO_STATUS  =  '07001'   \n";
        query +=" AND  ROWNUM  =  1)  AS  SERIAL_NO  ,   \n";
        query +=" (SELECT  --+  INDEX_DESC  (KDS_SHOPROOM  PK_KDS_SM_SEQ)  \n  BSCON_CD  FROM  LOG.KDS_SHOPROOM  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  CO_STATUS  =  '07001'   \n";
        query +=" AND  ROWNUM  =  1)  AS  BSCON_CD  ,  LOG.FT_GET_UPSO_STATUS_NM(A.UPSO_CD)  AS  CO_STATUS_NM  ,  GIBU.FT_GET_UPSO_HYUPAEUP(A.UPSO_CD)  AS  HYUPAEUP_GBN  ,  A.STAFF_CD  ,  DECODE(B.DSCT_START,  null,  null,  B.DSCT_START  ||'01')  AS  DSCT_START  ,  DECODE(B.DSCT_END,  null,  null,  B.DSCT_END  ||'01')  AS  DSCT_END  ,  B.DSCT_YN  ,   \n";
        query +=" (SELECT  CASE  WHEN(COUNT(*)  =  1)  THEN  '��������'  ELSE  ''  END  FROM  GIBU.TBRA_OFF_UPSO_MNG  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  ESTAB_YRMN  =  TO_CHAR(SYSDATE,  'YYYYMM')  )  AS  OFF_GBN  FROM  GIBU.TBRA_UPSO  A  ,  INSA.TCPM_DEPT  AA  ,  GIBU.TBRA_BSTYPGRAD  AAA  ,  LOG.KDS_SHOP  B  WHERE  A.BRAN_CD  =  AA.GIBU   \n";
        query +=" AND  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,3)  =  AAA.BSTYP_CD  ||  AAA.GRAD_GBN   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.STAFF_CD  =  DECODE(:STAFF_CD,  NULL,  A.STAFF_CD,  :STAFF_CD)   \n";
        query +=" AND  LOG.FT_GET_UPSO_STATUS_NM(A.UPSO_CD)  =  DECODE(:CO_STATUS,  '��ü',  LOG.FT_GET_UPSO_STATUS_NM(A.UPSO_CD),  :CO_STATUS) ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("CO_STATUS", CO_STATUS);               //�����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$upso_list_kylog
    //##**$$end
}
