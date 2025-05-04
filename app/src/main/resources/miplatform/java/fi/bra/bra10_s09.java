
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s09
{
    public bra10_s09()
    {
    }
    //##**$$sel_imei
    /*
    */
    public DOBJ CTLsel_imei(DOBJ dobj)
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
            dobj  = CALLsel_imei_SEL1(Conn, dobj);           //  imei ��û������ ��ȸ
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
    public DOBJ CTLsel_imei( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_imei_SEL1(Conn, dobj);           //  imei ��û������ ��ȸ
        return dobj;
    }
    // imei ��û������ ��ȸ
    public DOBJ CALLsel_imei_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_imei_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_imei_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   APPRV_GBN = dobj.getRetObject("S").getRecord().get("APPRV_GBN");   //���� ����
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BRAN_CD , DEPT_CD , SEQ , STAFF_CD , FIDU.GET_STAFF_NM(STAFF_CD) AS STAFF_NM , IMEI_NUM , MODEL_NO , APPRV_GBN , APPRV_STAFF , FIDU.GET_STAFF_NM(APPRV_STAFF) AS APPRV_STAFF_NM , APPRV_DAY , INS_DATE , INSPRES_ID , MOD_DATE , MODPRES_ID  ";
        query +=" FROM GIBU.TMOB_IMEI_APPRV  ";
        query +=" WHERE BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        if( !STAFF_CD.equals("") )
        {
            query +=" AND STAFF_CD = :STAFF_CD  ";
        }
        if( !APPRV_GBN.equals("") )
        {
            query +=" AND APPRV_GBN = :APPRV_GBN  ";
        }
        query +=" ORDER BY APPRV_GBN ASC, BRAN_CD ASC, INS_DATE DESC, APPRV_DAY DESC  ";
        sobj.setSql(query);
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        }
        if(!APPRV_GBN.equals(""))
        {
            sobj.setString("APPRV_GBN", APPRV_GBN);               //���� ����
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$sel_imei
    //##**$$mng_imei
    /*
    */
    public DOBJ CTLmng_imei(DOBJ dobj)
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
            dobj  = CALLmng_imei_MIUD2(Conn, dobj);           //  �б�
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
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
    public DOBJ CTLmng_imei( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_imei_MIUD2(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLmng_imei_MIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_imei_UPD8(Conn, dobj);           //  ���ο��� ����
            }
        }
        return dobj;
    }
    // ���ο��� ����
    public DOBJ CALLmng_imei_UPD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_imei_UPD8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_imei_UPD8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPRV_DAY ="";        //���� ����
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   APPRV_GBN = dvobj.getRecord().get("APPRV_GBN");   //���� ����
        String   DEPT_CD = dvobj.getRecord().get("DEPT_CD");   //�μ� �ڵ�
        String   IMEI_NUM = dvobj.getRecord().get("IMEI_NUM");   //IMEI��ȣ
        String   APPRV_STAFF = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TMOB_IMEI_APPRV  \n";
        query +=" set DEPT_CD=:DEPT_CD , APPRV_GBN=:APPRV_GBN , APPRV_STAFF=:APPRV_STAFF , APPRV_DAY=SYSDATE , BRAN_CD=:BRAN_CD  \n";
        query +=" where IMEI_NUM=:IMEI_NUM  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("APPRV_GBN", APPRV_GBN);               //���� ����
        sobj.setString("DEPT_CD", DEPT_CD);               //�μ� �ڵ�
        sobj.setString("IMEI_NUM", IMEI_NUM);               //IMEI��ȣ
        sobj.setString("APPRV_STAFF", APPRV_STAFF);               //���� ���
        return sobj;
    }
    //##**$$mng_imei
    //##**$$ins_imei
    /*
    */
    public DOBJ CTLins_imei(DOBJ dobj)
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
            dobj  = CALLins_imei_SEL1(Conn, dobj);           //  IMEI �ߺ� Ȯ��
            if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") > 0)
            {
                dobj  = CALLins_imei_SEL4(Conn, dobj);           //  ���ϸ޽���
                dobj  = CALLins_imei_MRG12( dobj);        //  �޽���
            }
            else if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLins_imei_SEL7(Conn, dobj);           //  IMEI ������ȸ
                if( dobj.getRetObject("SEL7").getRecord().getDouble("CNT") == 0)
                {
                    dobj  = CALLins_imei_XIUD11(Conn, dobj);           //  IMEI ��û �Է�
                    dobj  = CALLins_imei_SEL5(Conn, dobj);           //  ���ϸ޽���
                    dobj  = CALLins_imei_MRG12( dobj);        //  �޽���
                }
                else if( dobj.getRetObject("SEL7").getRecord().getDouble("CNT") > 0)
                {
                    dobj  = CALLins_imei_SEL10(Conn, dobj);           //  ���ϸ޽���
                    dobj  = CALLins_imei_MRG12( dobj);        //  �޽���
                }
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
    public DOBJ CTLins_imei( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLins_imei_SEL1(Conn, dobj);           //  IMEI �ߺ� Ȯ��
        if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") > 0)
        {
            dobj  = CALLins_imei_SEL4(Conn, dobj);           //  ���ϸ޽���
            dobj  = CALLins_imei_MRG12( dobj);        //  �޽���
        }
        else if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLins_imei_SEL7(Conn, dobj);           //  IMEI ������ȸ
            if( dobj.getRetObject("SEL7").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLins_imei_XIUD11(Conn, dobj);           //  IMEI ��û �Է�
                dobj  = CALLins_imei_SEL5(Conn, dobj);           //  ���ϸ޽���
                dobj  = CALLins_imei_MRG12( dobj);        //  �޽���
            }
            else if( dobj.getRetObject("SEL7").getRecord().getDouble("CNT") > 0)
            {
                dobj  = CALLins_imei_SEL10(Conn, dobj);           //  ���ϸ޽���
                dobj  = CALLins_imei_MRG12( dobj);        //  �޽���
            }
        }
        return dobj;
    }
    // IMEI �ߺ� Ȯ��
    public DOBJ CALLins_imei_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLins_imei_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLins_imei_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   IMEI_NUM = dobj.getRetObject("S").getRecord().get("IMEI_NUM");   //IMEI��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TMOB_IMEI_APPRV  WHERE  IMEI_NUM  =  :IMEI_NUM   \n";
        query +=" AND  APPRV_GBN  =  '2' ";
        sobj.setSql(query);
        sobj.setString("IMEI_NUM", IMEI_NUM);               //IMEI��ȣ
        return sobj;
    }
    // ���ϸ޽���
    public DOBJ CALLins_imei_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLins_imei_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLins_imei_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '�̹�  ���εǾ��ִ�  IMEI��ȣ�Դϴ�.'  AS  MSG  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // �޽���
    public DOBJ CALLins_imei_MRG12(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG12");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL4, SEL5, SEL10","MSG");
        rvobj.setName("MRG12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // IMEI ������ȸ
    public DOBJ CALLins_imei_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLins_imei_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLins_imei_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   IMEI_NUM = dobj.getRetObject("S").getRecord().get("IMEI_NUM");   //IMEI��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TMOB_IMEI_APPRV  WHERE  IMEI_NUM  =  :IMEI_NUM   \n";
        query +=" AND  APPRV_GBN  =  '1' ";
        sobj.setSql(query);
        sobj.setString("IMEI_NUM", IMEI_NUM);               //IMEI��ȣ
        return sobj;
    }
    // IMEI ��û �Է�
    public DOBJ CALLins_imei_XIUD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD11");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLins_imei_XIUD11(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLins_imei_XIUD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   IMEI_NUM = dobj.getRetObject("S").getRecord().get("IMEI_NUM");   //IMEI��ȣ
        String   MODEL_NO = dobj.getRetObject("S").getRecord().get("MODEL_NO");
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TMOB_IMEI_APPRV (BRAN_CD, DEPT_CD, STAFF_CD, SEQ, IMEI_NUM, MODEL_NO, APPRV_GBN, APPRV_STAFF, APPRV_DAY, INS_DATE, INSPRES_ID, MOD_DATE, MODPRES_ID) SELECT B.GIBU AS BRAN_CD , B.DEPT_CD AS DEPT_CD , :STAFF_CD AS STAFF_CD , (SELECT NVL(MAX(SEQ), 0) + 1 FROM GIBU.TMOB_IMEI_APPRV WHERE STAFF_CD = :STAFF_CD) AS SEQ , :IMEI_NUM AS IMEI_NUM , :MODEL_NO AS MODEL_NO , '1' AS APPRV_GBN , '' AS APPRV_STAFF , '' AS APPRV_DAY , SYSDATE AS INS_DATE , :STAFF_CD AS INSPRES_ID , '' AS MOD_DATE , '' AS MODPRES_ID FROM INSA.TINS_MST01 A , INSA.TCPM_DEPT B WHERE A.STAFF_NUM = :STAFF_CD AND A.DEPT_CD = B.DEPT_CD";
        sobj.setSql(query);
        sobj.setString("IMEI_NUM", IMEI_NUM);               //IMEI��ȣ
        sobj.setString("MODEL_NO", MODEL_NO);
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        return sobj;
    }
    // ���ϸ޽���
    public DOBJ CALLins_imei_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLins_imei_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLins_imei_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  'IMEI  ������  ��û�Ǿ����ϴ�.  ������  Ȯ��  ��  �α�����  �����մϴ�.'  AS  MSG  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // ���ϸ޽���
    public DOBJ CALLins_imei_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLins_imei_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLins_imei_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '����  IMEI  ����  ������Դϴ�.'  AS  MSG  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$ins_imei
    //##**$$end
}
