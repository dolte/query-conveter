
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s13_1
{
    public bra04_s13_1()
    {
    }
    //##**$$delete_rept_trans
    /*
    */
    public DOBJ CTLdelete_rept_trans(DOBJ dobj)
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
            dobj  = CALLdelete_rept_trans_SEL1(Conn, dobj);           //  ���� ��ϵ� ���� �ִ��� Ȯ��
            if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLdelete_rept_trans_SEL3(Conn, dobj);           //  ��Ī�� ���� Ȯ��
                if( dobj.getRetObject("SEL3").getRecord().getDouble("CNT") == 0)
                {
                    dobj  = CALLdelete_rept_trans_DEL5(Conn, dobj);           //  �Աݻ���
                    dobj  = CALLdelete_rept_trans_DEL6(Conn, dobj);           //  �������Աݻ���
                    dobj  = CALLdelete_rept_trans_SEL11(Conn, dobj);           //  �����޼���
                    dobj  = CALLdelete_rept_trans_MRG12( dobj);        //  ����
                }
                else
                {
                    dobj  = CALLdelete_rept_trans_SEL9(Conn, dobj);           //  ���и޼���
                    dobj  = CALLdelete_rept_trans_MRG12( dobj);        //  ����
                }
            }
            else
            {
                dobj  = CALLdelete_rept_trans_SEL7(Conn, dobj);           //  ���и޼���
                dobj  = CALLdelete_rept_trans_MRG12( dobj);        //  ����
            }
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
    public DOBJ CTLdelete_rept_trans( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdelete_rept_trans_SEL1(Conn, dobj);           //  ���� ��ϵ� ���� �ִ��� Ȯ��
        if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLdelete_rept_trans_SEL3(Conn, dobj);           //  ��Ī�� ���� Ȯ��
            if( dobj.getRetObject("SEL3").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLdelete_rept_trans_DEL5(Conn, dobj);           //  �Աݻ���
                dobj  = CALLdelete_rept_trans_DEL6(Conn, dobj);           //  �������Աݻ���
                dobj  = CALLdelete_rept_trans_SEL11(Conn, dobj);           //  �����޼���
                dobj  = CALLdelete_rept_trans_MRG12( dobj);        //  ����
            }
            else
            {
                dobj  = CALLdelete_rept_trans_SEL9(Conn, dobj);           //  ���и޼���
                dobj  = CALLdelete_rept_trans_MRG12( dobj);        //  ����
            }
        }
        else
        {
            dobj  = CALLdelete_rept_trans_SEL7(Conn, dobj);           //  ���и޼���
            dobj  = CALLdelete_rept_trans_MRG12( dobj);        //  ����
        }
        return dobj;
    }
    // ���� ��ϵ� ���� �ִ��� Ȯ��
    public DOBJ CALLdelete_rept_trans_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLdelete_rept_trans_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdelete_rept_trans_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //�Ա�����
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //���� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  (   \n";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  GIBU.TBRA_REPT  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  REPT_DAY  >  :REPT_DAY   \n";
        query +=" AND  ACCN_NUM  =  :ACCN_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'  )  +  (   \n";
        query +=" SELECT  COUNT(*)  AS  CNT  FROM  GIBU.TBRA_REPT_TRANS  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  REPT_DAY  >  :REPT_DAY   \n";
        query +=" AND  ACCN_NUM  =  :ACCN_NUM  )  AS  CNT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        return sobj;
    }
    // ��Ī�� ���� Ȯ��
    public DOBJ CALLdelete_rept_trans_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLdelete_rept_trans_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdelete_rept_trans_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //�Ա�����
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //���� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_REPT  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  ACCN_NUM  =  :ACCN_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'   \n";
        query +=" AND  (UPSO_CD  IS  NOT  NULL   \n";
        query +=" OR  MAPPING_DAY  IS  NOT  NULL   \n";
        query +=" OR  DISTR_GBN  IS  NOT  NULL)  )  +  (   \n";
        query +=" SELECT  COUNT(*)  FROM  GIBU.TBRA_REPT_TRANS  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  ACCN_NUM  =  :ACCN_NUM   \n";
        query +=" AND  (UPSO_CD  IS  NOT  NULL   \n";
        query +=" OR  MAPPING_DAY  IS  NOT  NULL   \n";
        query +=" OR  DISTR_GBN  IS  NOT  NULL)  )  AS  CNT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        return sobj;
    }
    // �Աݻ���
    public DOBJ CALLdelete_rept_trans_DEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdelete_rept_trans_DEL5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdelete_rept_trans_DEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCN_NUM = dvobj.getRecord().get("ACCN_NUM");   //���� ��ȣ
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and BRAN_CD=:BRAN_CD  \n";
        query +=" and ACCN_NUM=:ACCN_NUM";
        sobj.setSql(query);
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // �������Աݻ���
    public DOBJ CALLdelete_rept_trans_DEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdelete_rept_trans_DEL6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdelete_rept_trans_DEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCN_NUM = dvobj.getRecord().get("ACCN_NUM");   //���� ��ȣ
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_TRANS  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and BRAN_CD=:BRAN_CD  \n";
        query +=" and ACCN_NUM=:ACCN_NUM";
        sobj.setSql(query);
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // �����޼���
    public DOBJ CALLdelete_rept_trans_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLdelete_rept_trans_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdelete_rept_trans_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '������  ���ε带  �����Ͽ����ϴ�.'  AS  MESSAGE  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // ����
    public DOBJ CALLdelete_rept_trans_MRG12(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG12");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL7, SEL9, SEL11","");
        rvobj.setName("MRG12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ���и޼���
    public DOBJ CALLdelete_rept_trans_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLdelete_rept_trans_SEL9(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdelete_rept_trans_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '����  :  ��Ī��  ���Ұ�  �ֽ��ϴ�.'  AS  MESSAGE  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // ���и޼���
    public DOBJ CALLdelete_rept_trans_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLdelete_rept_trans_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdelete_rept_trans_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '����  :  ����  ���ε��  �������Ա�  ����  �ֽ��ϴ�.'  AS  MESSAGE  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$delete_rept_trans
    //##**$$end
}
