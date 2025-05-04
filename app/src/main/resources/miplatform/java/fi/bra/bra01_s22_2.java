
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s22_2
{
    public bra01_s22_2()
    {
    }
    //##**$$save_acmcn_info
    /*
    */
    public DOBJ CTLsave_acmcn_info(DOBJ dobj)
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
            dobj  = CALLsave_acmcn_info_DEL7(Conn, dobj);           //  ��������
            dobj  = CALLsave_acmcn_info_MPD1(Conn, dobj);           //  ���ֱ������
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLsave_acmcn_info_SEL1(Conn, dobj);           //  ���Һ� ���ֱ���ȸ
            dobj  = CALLsave_acmcn_info_SEL2(Conn, dobj);           //  ������ ����Ѵ��
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
    public DOBJ CTLsave_acmcn_info( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_acmcn_info_DEL7(Conn, dobj);           //  ��������
        dobj  = CALLsave_acmcn_info_MPD1(Conn, dobj);           //  ���ֱ������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLsave_acmcn_info_SEL1(Conn, dobj);           //  ���Һ� ���ֱ���ȸ
        dobj  = CALLsave_acmcn_info_SEL2(Conn, dobj);           //  ������ ����Ѵ��
        return dobj;
    }
    // ��������
    public DOBJ CALLsave_acmcn_info_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_acmcn_info_DEL7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_acmcn_info_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").firstRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_ACMCN_INFO  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ֱ������
    public DOBJ CALLsave_acmcn_info_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("UPSO_CD").equals(""))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_acmcn_info_INS3(Conn, dobj);           //  ���ֱ���������
            }
        }
        return dobj;
    }
    // ���ֱ���������
    // ���� ��ϵ� ���ֱ� �� ������ ����Ѵ�.
    public DOBJ CALLsave_acmcn_info_INS3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS3");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_acmcn_info_INS3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS3") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_acmcn_info_INS3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");        //����� ID
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        int   ACMCN_DAESU = dvobj.getRecord().getInt("ACMCN_DAESU");   //���ֱ� ���
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_ACMCN_INFO (MODEL_CD, ACMCN_DAESU, UPSO_CD)  \n";
        query +=" values(:MODEL_CD , :ACMCN_DAESU , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setInt("ACMCN_DAESU", ACMCN_DAESU);               //���ֱ� ���
        sobj.setString("MODEL_CD", MODEL_CD);               //�� �ڵ�
        return sobj;
    }
    // ���Һ� ���ֱ���ȸ
    // �ش������ ���ֱ� ������ ��ȸ�Ѵ�.
    public DOBJ CALLsave_acmcn_info_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsave_acmcn_info_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_acmcn_info_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").firstRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  XA.MCHN_COMPY  KMCHN_COMPY  ,  XA.MODEL_CD  KMODEL_CD  ,  XA.MODEL_NM  KMODEL_NM  ,  XA.ACMCN_DAESU  KACMCN_DAESU  ,  XA.GBN  KGBN  ,  XB.MCHN_COMPY  TMCHN_COMPY  ,  XB.MODEL_CD  TMODEL_CD  ,  XB.MODEL_NM  TMODEL_NM  ,  XB.ACMCN_DAESU  TACMCN_DAESU  ,  XB.GBN  TGBN  ,  XC.MCHN_COMPY  EMCHN_COMPY  ,  XC.MODEL_CD  EMODEL_CD  ,  XC.MODEL_NM  EMODEL_NM  ,  XC.ACMCN_DAESU  EACMCN_DAESU  ,  XC.GBN  EGBN  FROM  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0006'  )  XA  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0003'  )  XB  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  NOT  IN  ('E0003',  'E0006')  )  XC  WHERE  XA.N  =  XB.N  (+)   \n";
        query +=" AND  XA.N  =  XC.N  (+)  UNION   \n";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  XA.MCHN_COMPY  ,  XA.MODEL_CD  ,  XA.MODEL_NM  ,  XA.ACMCN_DAESU  ,  XA.GBN  ,  XB.MCHN_COMPY  ,  XB.MODEL_CD  ,  XB.MODEL_NM  ,  XB.ACMCN_DAESU  ,  XB.GBN  ,  XC.MCHN_COMPY  ,  XC.MODEL_CD  ,  XC.MODEL_NM  ,  XC.ACMCN_DAESU  ,  XC.GBN  FROM  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0006'  )  XA  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0003'  )  XB  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  NOT  IN  ('E0003',  'E0006')  )  XC  WHERE  XB.N  =  XA.N  (+)   \n";
        query +=" AND  XB.N  =  XC.N  (+)  UNION   \n";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  XA.MCHN_COMPY  ,  XA.MODEL_CD  ,  XA.MODEL_NM  ,  XA.ACMCN_DAESU  ,  XA.GBN  ,  XB.MCHN_COMPY  ,  XB.MODEL_CD  ,  XB.MODEL_NM  ,  XB.ACMCN_DAESU  ,  XB.GBN  ,  XC.MCHN_COMPY  ,  XC.MODEL_CD  ,  XC.MODEL_NM  ,  XC.ACMCN_DAESU  ,  XC.GBN  FROM  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0006'  )  XA  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0003'  )  XB  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  NOT  IN  ('E0003',  'E0006')  )  XC  WHERE  XC.N  =  XA.N  (+)   \n";
        query +=" AND  XC.N  =  XB.N  (+) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������ ����Ѵ��
    public DOBJ CALLsave_acmcn_info_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsave_acmcn_info_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_acmcn_info_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").firstRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  UPSO_NM  ,  MCHNDAESU  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$save_acmcn_info
    //##**$$end
}
