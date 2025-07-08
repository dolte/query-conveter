
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac03_s06
{
    public tac03_s06()
    {
    }
    //##**$$ttac_etcdedct_u_insert
    /*
    */
    public DOBJ CTLttac_etcdedct_u_insert(DOBJ dobj)
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
            dobj  = CALLttac_etcdedct_u_insert_XIUD1(Conn, dobj);           //  ��ȣ������ü�� ��Ÿ������ ó
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
    public DOBJ CTLttac_etcdedct_u_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttac_etcdedct_u_insert_XIUD1(Conn, dobj);           //  ��ȣ������ü�� ��Ÿ������ ó
        return dobj;
    }
    // ��ȣ������ü�� ��Ÿ������ ó
    public DOBJ CALLttac_etcdedct_u_insert_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLttac_etcdedct_u_insert_XIUD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_etcdedct_u_insert_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.TTAC_ETCDEDCT SELECT A.SUPP_YRMN, A.MB_CD, A.TRNSF_GBN, '3' , TOT_REALSUPP_AMT, '�ܱ���ü��ȭ�۱�', '' , sysdate,'','' FROM FIDU.TTAC_COPYRTAL A , FIDU.TMEM_MB B WHERE SUPP_YRMN =:SUPP_YRMN AND A.MB_CD = B.MB_CD AND B.MB_GBN1 ='U'";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    //##**$$ttac_etcdedct_u_insert
    //##**$$etcdedct_get_realsupp
    /*
    */
    public DOBJ CTLetcdedct_get_realsupp(DOBJ dobj)
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
            dobj  = CALLetcdedct_get_realsupp_SEL1(Conn, dobj);           //  �����޾� ��ȸ
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
    public DOBJ CTLetcdedct_get_realsupp( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLetcdedct_get_realsupp_SEL1(Conn, dobj);           //  �����޾� ��ȸ
        return dobj;
    }
    // �����޾� ��ȸ
    public DOBJ CALLetcdedct_get_realsupp_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLetcdedct_get_realsupp_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLetcdedct_get_realsupp_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //�絵 ����
        String   DEDCT_YRMN = dobj.getRetObject("S").getRecord().get("DEDCT_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(NVL(TOT_REALSUPP_AMT,0))  AS  TOT_REALSUPP_AMT  FROM  FIDU.TTAC_COPYRTAL  WHERE  SUPP_YRMN  =  :DEDCT_YRMN   \n";
        query +=" AND  MB_CD  =  :MB_CD   \n";
        query +=" AND  TRNSF_GBN  =  :TRNSF_GBN ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("DEDCT_YRMN", DEDCT_YRMN);               //���� ���
        return sobj;
    }
    //##**$$etcdedct_get_realsupp
    //##**$$saveMemo
    /* * ���α׷��� : tac03_s06
    * �ۼ��� : ����ȯ
    * �ۼ��� : 2009/11/27
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLsaveMemo(DOBJ dobj)
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
            dobj  = CALLsaveMemo_DEL1(Conn, dobj);           //  �������׻���
            dobj  = CALLsaveMemo_INS2(Conn, dobj);           //  ������������
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
    public DOBJ CTLsaveMemo( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsaveMemo_DEL1(Conn, dobj);           //  �������׻���
        dobj  = CALLsaveMemo_INS2(Conn, dobj);           //  ������������
        return dobj;
    }
    // �������׻���
    public DOBJ CALLsaveMemo_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveMemo_DEL1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveMemo_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_MEMO  \n";
        query +=" where SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    // ������������
    public DOBJ CALLsaveMemo_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveMemo_INS2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveMemo_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //����� ID
        String INS_DATE ="";        //��� �Ͻ�
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //���� ���
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_MEMO (REMAK, SUPP_YRMN)  \n";
        query +=" values(:REMAK , :SUPP_YRMN )";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        sobj.setString("REMAK", REMAK);               //���
        return sobj;
    }
    //##**$$saveMemo
    //##**$$ttac_etcdedct_select
    /* * ���α׷��� : tac03_s06
    * �ۼ��� : ����ȯ
    * �ۼ��� : 2009/11/23
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLttac_etcdedct_select(DOBJ dobj)
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
            dobj  = CALLttac_etcdedct_select_SEL1(Conn, dobj);           //  ��Ÿ������������
            dobj  = CALLttac_etcdedct_select_SEL2(Conn, dobj);           //  ����������ȸ
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
    public DOBJ CTLttac_etcdedct_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttac_etcdedct_select_SEL1(Conn, dobj);           //  ��Ÿ������������
        dobj  = CALLttac_etcdedct_select_SEL2(Conn, dobj);           //  ����������ȸ
        return dobj;
    }
    // ��Ÿ������������
    // ��Ÿ������������
    public DOBJ CALLttac_etcdedct_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLttac_etcdedct_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_etcdedct_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   DEDCT_YRMN1 = dobj.getRetObject("S").getRecord().get("DEDCT_YRMN1");   //DEDCT_YRMN1
        String   DEDCT_YRMN = dobj.getRetObject("S").getRecord().get("DEDCT_YRMN");   //���� ���
        String   DEDCTINS_GBN = dobj.getRetObject("S").getRecord().get("DEDCTINS_GBN");   //������� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEDCT_YRMN,  MB_CD,  DEDCTINS_GBN,  TRNSF_GBN,  FIDU.GET_MB_NM(A.MB_CD)  AS  MB_NM,  FIDU.GET_CODE_NM('00228',  A.DEDCTINS_GBN)  AS  DEDCTINS_NM,  OCC_AMT,  REMAK_CTENT,   \n";
        query +=" (SELECT  MAX(NVL(TOT_REALSUPP_AMT,0))  FROM  FIDU.TTAC_COPYRTAL  WHERE  SUPP_YRMN  =  A.DEDCT_YRMN   \n";
        query +=" AND  MB_CD  =  A.MB_CD   \n";
        query +=" AND  TRNSF_GBN  =  A.TRNSF_GBN  )  AS  TOT_REALSUPP_AMT  FROM  FIDU.TTAC_ETCDEDCT  A  WHERE  1=1   \n";
        query +=" AND  MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  DEDCT_YRMN  >=  :DEDCT_YRMN   \n";
        query +=" AND  DEDCT_YRMN  <=  :DEDCT_YRMN1   \n";
        query +=" AND  DEDCTINS_GBN  LIKE  :DEDCTINS_GBN  ||  '%' ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("DEDCT_YRMN1", DEDCT_YRMN1);               //DEDCT_YRMN1
        sobj.setString("DEDCT_YRMN", DEDCT_YRMN);               //���� ���
        sobj.setString("DEDCTINS_GBN", DEDCTINS_GBN);               //������� ����
        return sobj;
    }
    // ����������ȸ
    public DOBJ CALLttac_etcdedct_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLttac_etcdedct_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_etcdedct_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   DEDCT_YRMN = dobj.getRetObject("S").getRecord().get("DEDCT_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUPP_YRMN,  REMAK,  B.MB_GBN1  FROM  FIDU.TTAC_MEMO  A,  FIDU.TMEM_MB  B  WHERE  SUPP_YRMN  =:DEDCT_YRMN   \n";
        query +=" AND  B.MB_CD  =:MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("DEDCT_YRMN", DEDCT_YRMN);               //���� ���
        return sobj;
    }
    //##**$$ttac_etcdedct_select
    //##**$$ttac_etcdedct_save
    /* * ���α׷��� : tac03_s06
    * �ۼ��� : �ϱ�ö
    * �ۼ��� : 2009/08/13
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLttac_etcdedct_save(DOBJ dobj)
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
            dobj  = CALLttac_etcdedct_save_MIUD1(Conn, dobj);           //  ��Ÿ����MUID
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
    public DOBJ CTLttac_etcdedct_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttac_etcdedct_save_MIUD1(Conn, dobj);           //  ��Ÿ����MUID
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ��Ÿ����MUID
    public DOBJ CALLttac_etcdedct_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLttac_etcdedct_save_INS8(Conn, dobj);           //  ��Ÿ��������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLttac_etcdedct_save_UPD6(Conn, dobj);           //  ��Ÿ��������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLttac_etcdedct_save_DEL7(Conn, dobj);           //  ��Ÿ��������
            }
        }
        return dobj;
    }
    // ��Ÿ��������
    public DOBJ CALLttac_etcdedct_save_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLttac_etcdedct_save_DEL7(dobj, dvobj);
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
    private SQLObject SQLttac_etcdedct_save_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEDCT_YRMN = dvobj.getRecord().get("DEDCT_YRMN");   //���� ���
        String   DEDCTINS_GBN = dvobj.getRecord().get("DEDCTINS_GBN");   //������� ����
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_ETCDEDCT  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN  \n";
        query +=" and DEDCTINS_GBN=:DEDCTINS_GBN  \n";
        query +=" and DEDCT_YRMN=:DEDCT_YRMN";
        sobj.setSql(query);
        sobj.setString("DEDCT_YRMN", DEDCT_YRMN);               //���� ���
        sobj.setString("DEDCTINS_GBN", DEDCTINS_GBN);               //������� ����
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    // ��Ÿ��������
    public DOBJ CALLttac_etcdedct_save_INS8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLttac_etcdedct_save_INS8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_etcdedct_save_INS8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String REMAK_CTENT ="";        //��� ����
        String   DEDCT_YRMN = dvobj.getRecord().get("DEDCT_YRMN");   //���� ���
        String   DEDCTINS_GBN = dvobj.getRecord().get("DEDCTINS_GBN");   //������� ����
        String   REMAK_CTENT_1 = dobj.getRetObject("R").getRecord().get("REMAK_CTENT");   //��� ����
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        double   OCC_AMT = dvobj.getRecord().getDouble("OCC_AMT");   //�߻� �ݾ�
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //����� ID
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_ETCDEDCT (MB_CD, INS_DATE, INSPRES_ID, OCC_AMT, TRNSF_GBN, REMAK_CTENT, DEDCTINS_GBN, DEDCT_YRMN)  \n";
        query +=" values(:MB_CD , SYSDATE, :INSPRES_ID , :OCC_AMT , :TRNSF_GBN , REPLACE(REPLACE(:REMAK_CTENT_1, CHR(10), ''), CHR(13), ''), :DEDCTINS_GBN , :DEDCT_YRMN )";
        sobj.setSql(query);
        sobj.setString("DEDCT_YRMN", DEDCT_YRMN);               //���� ���
        sobj.setString("DEDCTINS_GBN", DEDCTINS_GBN);               //������� ����
        sobj.setString("REMAK_CTENT_1", REMAK_CTENT_1);               //��� ����
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setDouble("OCC_AMT", OCC_AMT);               //�߻� �ݾ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    // ��Ÿ��������
    public DOBJ CALLttac_etcdedct_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLttac_etcdedct_save_UPD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_etcdedct_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MODI_DATE ="";        //�����Ͻ�
        String REMAK_CTENT ="";        //��� ����
        String   MOD_DATE = dvobj.getRecord().get("MOD_DATE");   //���� �Ͻ�
        String   DEDCT_YRMN = dvobj.getRecord().get("DEDCT_YRMN");   //���� ���
        String   DEDCTINS_GBN = dvobj.getRecord().get("DEDCTINS_GBN");   //������� ����
        String   REMAK_CTENT_1 = dobj.getRetObject("R").getRecord().get("REMAK_CTENT");   //��� ����
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //�絵 ����
        double   OCC_AMT = dvobj.getRecord().getDouble("OCC_AMT");   //�߻� �ݾ�
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //����� ID
        String   INS_DATE = dvobj.getRecord().get("INS_DATE");   //��� �Ͻ�
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_ETCDEDCT  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , INS_DATE=:INS_DATE , INSPRES_ID=:INSPRES_ID , OCC_AMT=:OCC_AMT , REMAK_CTENT=REPLACE(REPLACE(:REMAK_CTENT_1, CHR(10), ''), CHR(13), '') , MOD_DATE=:MOD_DATE  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN  \n";
        query +=" and DEDCTINS_GBN=:DEDCTINS_GBN  \n";
        query +=" and DEDCT_YRMN=:DEDCT_YRMN";
        sobj.setSql(query);
        sobj.setString("MOD_DATE", MOD_DATE);               //���� �Ͻ�
        sobj.setString("DEDCT_YRMN", DEDCT_YRMN);               //���� ���
        sobj.setString("DEDCTINS_GBN", DEDCTINS_GBN);               //������� ����
        sobj.setString("REMAK_CTENT_1", REMAK_CTENT_1);               //��� ����
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setDouble("OCC_AMT", OCC_AMT);               //�߻� �ݾ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("INS_DATE", INS_DATE);               //��� �Ͻ�
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    //##**$$ttac_etcdedct_save
    //##**$$end
}
