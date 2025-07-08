
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac01_r06
{
    public tac01_r06()
    {
    }
    //##**$$EventID38
    /*
    */
    public DOBJ CTLEventID38(DOBJ dobj)
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
            dobj  = CALLEventID38_SEL1(Conn, dobj);           //  ���������꼭�߱ް�����ȸ
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
    public DOBJ CTLEventID38( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLEventID38_SEL1(Conn, dobj);           //  ���������꼭�߱ް�����ȸ
        return dobj;
    }
    // ���������꼭�߱ް�����ȸ
    // ���������꼭�߱ް�����ȸ
    public DOBJ CALLEventID38_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLEventID38_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID38_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   A_TO = dobj.getRetObject("S").getRecord().get("A_TO");   //��������
        String   A_FROM = dobj.getRetObject("S").getRecord().get("A_FROM");   //��������
        String   USE_TTL = dobj.getRetObject("S").getRecord().get("USE_TTL");   //�������
        String   APPRV_NUM = dobj.getRetObject("S").getRecord().get("APPRV_NUM");   //���� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT T4.BSCON_CD ,T4.BSCONHAN_NM ,T3.USE_TTL ,T3.USETRM_START_DAY||'~'||T3.USETRM_END_DAY AS DAY ,T1.APPRV_NUM ,DECODE(T2.DOMTUNE_RTAL ,'',0 , T2.DOMTUNE_RTAL) AS DOMTUNE_RTAL ,DECODE(T2.ABRTUNE_RTAL ,'',0 , T2.ABRTUNE_RTAL) AS ABRTUNE_RTAL ,DECODE(T2.ATAX_AMT ,'',0 , T2.ATAX_AMT) AS ATAX_AMT ,T2.LEVY_AMT + T2.ATAX_AMT AS TOT_AMT ,T1.DEMD_DAY ,DECODE(T1.BILL_GBN , '2' , 'V' ,'') AS DEMD_YN ,DECODE(T1.BILL_GBN , '1' , 'V' ,'') AS RECV_YN ,DECODE(T2.DEMD_NUM , '' , '' ,'�Ϸ�') AS BILL_ISS_YN ,DECODE((SELECT COUNT(*)  ";
        query +=" FROM FIDU.TTAC_BILL A  ";
        query +=" WHERE T2.DEMD_NUM = A.DEMD_NUM  ";
        query +=" AND A.ELEC_SEND_YN = '1') , '0', '' ,'�Ϸ�') AS COMPL_GBN  ";
        query +=" FROM FIDU.TLEV_CLRREC_BILL_SEND T1 , FIDU.TLEV_CLRREC T2 , FIDU.TLEV_USEAPPRV T3 , FIDU.TLEV_BSCON T4 , FIDU.TLEV_APPTNPRESINFO T5  ";
        query +=" WHERE T1.APPRV_NUM = T2.APPRV_NUM  ";
        query +=" AND T2.APPRV_NUM = T3.APPRV_NUM  ";
        query +=" AND T1.APPRV_NUM = T5.APPRV_NUM  ";
        query +=" AND T5.BSCON_CD = T4.BSCON_CD  ";
        if( !APPRV_NUM.equals("") )
        {
            query +=" AND T1.APPRV_NUM = :APPRV_NUM  ";
        }
        query +=" AND T1.DEMD_DAY BETWEEN :A_FROM  ";
        query +=" AND :A_TO  ";
        query +=" AND T3.USE_TTL LIKE '%' || :USE_TTL|| '%'  ";
        query +=" ORDER BY T1.DEMD_DAY DESC  ";
        sobj.setSql(query);
        sobj.setString("A_TO", A_TO);               //��������
        sobj.setString("A_FROM", A_FROM);               //��������
        sobj.setString("USE_TTL", USE_TTL);               //�������
        if(!APPRV_NUM.equals(""))
        {
            sobj.setString("APPRV_NUM", APPRV_NUM);               //���� ��ȣ
        }
        return sobj;
    }
    //##**$$EventID38
    //##**$$end
}
