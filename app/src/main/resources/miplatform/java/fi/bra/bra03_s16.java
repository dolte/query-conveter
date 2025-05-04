
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s16
{
    public bra03_s16()
    {
    }
    //##**$$card_demd_select
    /*
    */
    public DOBJ CTLcard_demd_select(DOBJ dobj)
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
            dobj  = CALLcard_demd_select_SEL1(Conn, dobj);           //  û������ ��ȸ
            dobj  = CALLcard_demd_select_XIUD2(Conn, dobj);           //  û��Ȯ������
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
    public DOBJ CTLcard_demd_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcard_demd_select_SEL1(Conn, dobj);           //  û������ ��ȸ
        dobj  = CALLcard_demd_select_XIUD2(Conn, dobj);           //  û��Ȯ������
        return dobj;
    }
    // û������ ��ȸ
    // û������� ���� û�������� ��ȸ�Ѵ�
    public DOBJ CALLcard_demd_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLcard_demd_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcard_demd_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //û�� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.BRAN_CD  ,  XB.UPSO_CD  ,  XC.UPSO_NM  ,  XC.CLIENT_NUM  ,  XB.CARD_GBN  ,  XB.CARD_NUM  ,  XB.EXPIRE_DAY  ,  XB.RESINUM  ,  XA.START_YRMN  ,  XC.MCHNDAESU  ,  XC.MNGEMSTR_NM  ,  XA.BSTYP_CD  ||  XA.UPSO_GRAD  UPSO_GRAD  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DSCT_AMT  ,  XA.DEMD_MMCNT  ,  XA.MONPRNCFEE  ,  DECODE(XB.CARD_GBN,  'WIN','�Ｚī��','LGC','����ī��',  'AMX','�Ե�ī��')  BANK_NM  ,  XF.DEPT_NM  ,  XB.RECEPTION_GBN  FROM  GIBU.TBRA_DEMD_CARD  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.CARD_GBN  ,  A.CARD_NUM  ,  A.EXPIRE_DAY  ,  A.RESINUM  ,  A.RECEPTION_GBN  FROM  GIBU.TBRA_UPSO_AUTO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(AUTO_NUM)  AUTO_NUM  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'  GROUP  BY  UPSO_CD  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.AUTO_NUM  =  B.AUTO_NUM   \n";
        query +=" AND  A.CARD_NUM  IS  NOT  NULL   \n";
        query +=" AND  A.RECEPTION_GBN  =  '7'  )  XB  ,  GIBU.TBRA_UPSO  XC  ,  INSA.TCPM_DEPT  XF  WHERE  XA.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XF.GIBU  =  XC.BRAN_CD   \n";
        query +=" AND  XA.DEMD_GBN  =  '40'   \n";
        query +=" AND  XA.TOT_DEMD_AMT  >  0  ORDER  BY  XC.BRAN_CD,  XB.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        return sobj;
    }
    // û��Ȯ������
    public DOBJ CALLcard_demd_select_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcard_demd_select_XIUD2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcard_demd_select_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //û�� ���
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   PROC_GBN = dobj.getRetObject("S").getRecord().get("PROC_GBN");   //�ڵ� ó�� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_CARD  \n";
        query +=" SET PROC_GBN= :PROC_GBN , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE , PROC_GBN = :PROC_GBN  \n";
        query +=" AND UPSO_CD IN (SELECT XB.UPSO_CD FROM GIBU.TBRA_DEMD_CARD XA , ( SELECT A.UPSO_CD , A.CARD_GBN , A.CARD_NUM , A.RESINUM FROM GIBU.TBRA_UPSO_AUTO A , ( SELECT UPSO_CD , MAX(AUTO_NUM) AUTO_NUM FROM GIBU.TBRA_UPSO_AUTO  \n";
        query +=" WHERE TERM_YN = 'N' GROUP BY UPSO_CD ) B  \n";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  \n";
        query +=" AND A.AUTO_NUM = B.AUTO_NUM  \n";
        query +=" AND A.CARD_NUM IS NOT NULL  \n";
        query +=" AND A.RECEPTION_GBN = '7' ) XB , GIBU.TBRA_UPSO XC , INSA.TCPM_DEPT XF  \n";
        query +=" WHERE XA.DEMD_YRMN = :DEMD_YRMN  \n";
        query +=" AND XB.UPSO_CD = XA.UPSO_CD  \n";
        query +=" AND XC.UPSO_CD = XA.UPSO_CD  \n";
        query +=" AND XF.GIBU = XC.BRAN_CD  \n";
        query +=" AND XA.DEMD_GBN = '40'  \n";
        query +=" AND XA.TOT_DEMD_AMT > 0 )";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("PROC_GBN", PROC_GBN);               //�ڵ� ó�� ����
        return sobj;
    }
    //##**$$card_demd_select
    //##**$$end
}
