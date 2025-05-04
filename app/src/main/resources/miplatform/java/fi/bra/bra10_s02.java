
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s02
{
    public bra10_s02()
    {
    }
    //##**$$search_manper_juso
    /*
    */
    public DOBJ CTLsearch_manper_juso(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj  = CALLsearch_manper_juso_SEL1(Conn, dobj);           //  �̸�Ī ���
                dobj  = CALLsearch_manper_juso_MRG7( dobj);        //  �����
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
            {
                dobj  = CALLsearch_manper_juso_SEL5(Conn, dobj);           //  ��Ī ���
                dobj  = CALLsearch_manper_juso_MRG7( dobj);        //  �����
            }
            else
            {
                dobj  = CALLsearch_manper_juso_SEL6(Conn, dobj);           //  ��ü ���
                dobj  = CALLsearch_manper_juso_MRG7( dobj);        //  �����
            }
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
    public DOBJ CTLsearch_manper_juso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLsearch_manper_juso_SEL1(Conn, dobj);           //  �̸�Ī ���
            dobj  = CALLsearch_manper_juso_MRG7( dobj);        //  �����
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
        {
            dobj  = CALLsearch_manper_juso_SEL5(Conn, dobj);           //  ��Ī ���
            dobj  = CALLsearch_manper_juso_MRG7( dobj);        //  �����
        }
        else
        {
            dobj  = CALLsearch_manper_juso_SEL6(Conn, dobj);           //  ��ü ���
            dobj  = CALLsearch_manper_juso_MRG7( dobj);        //  �����
        }
        return dobj;
    }
    // �̸�Ī ���
    public DOBJ CALLsearch_manper_juso_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_manper_juso_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_manper_juso_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  X.UPSO_CD  ,  '�濵'  AS  GBN2  ,  X.MNGEMSTR_ZIP  AS  ZIP  ,  X.MNGEMSTR_ADDR1  ||  '  '  ||  X.MNGEMSTR_ADDR2  AS  ADDR  ,  X.N_MNGEMSTR_ZIP  AS  N_ZIP  ,  X.N_MNGEMSTR_ADDR1  AS  N_ADDR1  ,  X.N_MNGEMSTR_ADDR2  AS  N_ADDR2  ,  X.N_MNGEMSTR_ADDR3  AS  N_ADDR3  ,  X.N_MNGEMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X,  GIBU.TBRA_UPSO  Y  WHERE  X.UPSO_CD  =  Y.UPSO_CD   \n";
        query +=" AND  X.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  X.STAFF_CD  =  NVL(:STAFF_CD,  X.STAFF_CD)   \n";
        query +=" AND  Y.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  X.MNGEMSTR_ADDR1  ||  X.MNGEMSTR_ADDR2  IS  NOT  NULL   \n";
        query +=" AND  X.N_MNGEMSTR_BD_MNG_NUM  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  X.UPSO_CD  ,  '�㰡'  AS  GBN2  ,  X.PERMMSTR_ZIP  AS  ZIP  ,  X.PERMMSTR_ADDR1  ||  '  '  ||  X.PERMMSTR_ADDR2  AS  ADDR  ,  X.N_PERMMSTR_ZIP  AS  N_ZIP  ,  X.N_PERMMSTR_ADDR1  AS  N_ADDR1  ,  X.N_PERMMSTR_ADDR2  AS  N_ADDR2  ,  X.N_PERMMSTR_ADDR3  AS  N_ADDR3  ,  X.N_PERMMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X,  GIBU.TBRA_UPSO  Y  WHERE  X.UPSO_CD  =  Y.UPSO_CD   \n";
        query +=" AND  X.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  X.STAFF_CD  =  NVL(:STAFF_CD,  X.STAFF_CD)   \n";
        query +=" AND  Y.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  X.MNGEMSTR_ADDR1  ||  X.MNGEMSTR_ADDR2  IS  NOT  NULL   \n";
        query +=" AND  X.N_PERMMSTR_BD_MNG_NUM  IS  NULL  ORDER  BY  UPSO_CD,  ZIP ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �����
    public DOBJ CALLsearch_manper_juso_MRG7(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG7");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL5, SEL6","");
        rvobj.setName("MRG7") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ��Ī ���
    public DOBJ CALLsearch_manper_juso_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_manper_juso_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_manper_juso_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  X.UPSO_CD  ,  '�濵'  AS  GBN2  ,  X.MNGEMSTR_ZIP  AS  ZIP  ,  X.MNGEMSTR_ADDR1  ||  '  '  ||  X.MNGEMSTR_ADDR2  AS  ADDR  ,  X.N_MNGEMSTR_ZIP  AS  N_ZIP  ,  X.N_MNGEMSTR_ADDR1  AS  N_ADDR1  ,  X.N_MNGEMSTR_ADDR2  AS  N_ADDR2  ,  X.N_MNGEMSTR_ADDR3  AS  N_ADDR3  ,  X.N_MNGEMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X,  GIBU.TBRA_UPSO  Y  WHERE  X.UPSO_CD  =  Y.UPSO_CD   \n";
        query +=" AND  X.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  X.STAFF_CD  =  NVL(:STAFF_CD,  X.STAFF_CD)   \n";
        query +=" AND  Y.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  X.MNGEMSTR_ADDR1  ||  X.MNGEMSTR_ADDR2  IS  NOT  NULL   \n";
        query +=" AND  X.N_MNGEMSTR_BD_MNG_NUM  IS  NOT  NULL  UNION  ALL   \n";
        query +=" SELECT  X.UPSO_CD  ,  '�㰡'  AS  GBN2  ,  X.PERMMSTR_ZIP  AS  ZIP  ,  X.PERMMSTR_ADDR1  ||  '  '  ||  X.PERMMSTR_ADDR2  AS  ADDR  ,  X.N_PERMMSTR_ZIP  AS  N_ZIP  ,  X.N_PERMMSTR_ADDR1  AS  N_ADDR1  ,  X.N_PERMMSTR_ADDR2  AS  N_ADDR2  ,  X.N_PERMMSTR_ADDR3  AS  N_ADDR3  ,  X.N_PERMMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X,  GIBU.TBRA_UPSO  Y  WHERE  X.UPSO_CD  =  Y.UPSO_CD   \n";
        query +=" AND  X.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  X.STAFF_CD  =  NVL(:STAFF_CD,  X.STAFF_CD)   \n";
        query +=" AND  Y.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  X.MNGEMSTR_ADDR1  ||  X.MNGEMSTR_ADDR2  IS  NOT  NULL   \n";
        query +=" AND  X.N_PERMMSTR_BD_MNG_NUM  IS  NOT  NULL  ORDER  BY  UPSO_CD,  ZIP ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ��ü ���
    public DOBJ CALLsearch_manper_juso_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_manper_juso_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_manper_juso_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  X.UPSO_CD  ,  '�濵'  AS  GBN2  ,  X.MNGEMSTR_ZIP  AS  ZIP  ,  X.MNGEMSTR_ADDR1  ||  '  '  ||  X.MNGEMSTR_ADDR2  AS  ADDR  ,  X.N_MNGEMSTR_ZIP  AS  N_ZIP  ,  X.N_MNGEMSTR_ADDR1  AS  N_ADDR1  ,  X.N_MNGEMSTR_ADDR2  AS  N_ADDR2  ,  X.N_MNGEMSTR_ADDR3  AS  N_ADDR3  ,  X.N_MNGEMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X,  GIBU.TBRA_UPSO  Y  WHERE  X.UPSO_CD  =  Y.UPSO_CD   \n";
        query +=" AND  X.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  X.STAFF_CD  =  NVL(:STAFF_CD,  X.STAFF_CD)   \n";
        query +=" AND  Y.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  X.MNGEMSTR_ADDR1  ||  X.MNGEMSTR_ADDR2  IS  NOT  NULL  UNION  ALL   \n";
        query +=" SELECT  X.UPSO_CD  ,  '�㰡'  AS  GBN2  ,  X.PERMMSTR_ZIP  AS  ZIP  ,  X.PERMMSTR_ADDR1  ||  '  '  ||  X.PERMMSTR_ADDR2  AS  ADDR  ,  X.N_PERMMSTR_ZIP  AS  N_ZIP  ,  X.N_PERMMSTR_ADDR1  AS  N_ADDR1  ,  X.N_PERMMSTR_ADDR2  AS  N_ADDR2  ,  X.N_PERMMSTR_ADDR3  AS  N_ADDR3  ,  X.N_PERMMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X,  GIBU.TBRA_UPSO  Y  WHERE  X.UPSO_CD  =  Y.UPSO_CD   \n";
        query +=" AND  X.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  X.STAFF_CD  =  NVL(:STAFF_CD,  X.STAFF_CD)   \n";
        query +=" AND  Y.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  X.MNGEMSTR_ADDR1  ||  X.MNGEMSTR_ADDR2  IS  NOT  NULL  ORDER  BY  UPSO_CD,  ZIP ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$search_manper_juso
    //##**$$search_rel_juso
    /*
    */
    public DOBJ CTLsearch_rel_juso(DOBJ dobj)
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
            dobj  = CALLsearch_rel_juso_SEL1(Conn, dobj);           //  ��ȸ
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
    public DOBJ CTLsearch_rel_juso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_rel_juso_SEL1(Conn, dobj);           //  ��ȸ
        return dobj;
    }
    // ��ȸ
    public DOBJ CALLsearch_rel_juso_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsearch_rel_juso_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_rel_juso_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  X.UPSO_CD  ,  '����'  AS  GBN2  ,  X.UPSO_ZIP  AS  ZIP  ,  X.UPSO_ADDR1  ||  '  '  ||  X.UPSO_ADDR2  AS  ADDR  ,  X.N_UPSO_ZIP  AS  N_ZIP  ,  X.N_UPSO_ADDR1  AS  N_ADDR1  ,  X.N_UPSO_ADDR2  AS  N_ADDR2  ,  X.N_UPSO_ADDR3  AS  N_ADDR3  ,  X.N_UPSO_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X  WHERE  X.UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  X.UPSO_CD  ,  '�濵'  AS  GBN2  ,  X.MNGEMSTR_ZIP  AS  ZIP  ,  X.MNGEMSTR_ADDR1  ||  '  '  ||  X.MNGEMSTR_ADDR2  AS  ADDR  ,  X.N_MNGEMSTR_ZIP  AS  N_ZIP  ,  X.N_MNGEMSTR_ADDR1  AS  N_ADDR1  ,  X.N_MNGEMSTR_ADDR2  AS  N_ADDR2  ,  X.N_MNGEMSTR_ADDR3  AS  N_ADDR3  ,  X.N_MNGEMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X  WHERE  X.UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  X.UPSO_CD  ,  '�㰡'  AS  GBN2  ,  X.PERMMSTR_ZIP  AS  ZIP  ,  X.PERMMSTR_ADDR1  ||  '  '  ||  X.PERMMSTR_ADDR2  AS  ADDR  ,  X.N_PERMMSTR_ZIP  AS  N_ZIP  ,  X.N_PERMMSTR_ADDR1  AS  N_ADDR1  ,  X.N_PERMMSTR_ADDR2  AS  N_ADDR2  ,  X.N_PERMMSTR_ADDR3  AS  N_ADDR3  ,  X.N_PERMMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X  WHERE  X.UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$search_rel_juso
    //##**$$save_manper_juso
    /*
    */
    public DOBJ CTLsave_manper_juso(DOBJ dobj)
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
            dobj  = CALLsave_manper_juso_MIUD4(Conn, dobj);           //  ������ �ุ �����ϵ���
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
    public DOBJ CTLsave_manper_juso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_manper_juso_MIUD4(Conn, dobj);           //  ������ �ุ �����ϵ���
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ������ �ุ �����ϵ���
    public DOBJ CALLsave_manper_juso_MIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD4");
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
                dobj  = CALLsave_manper_juso_SEL11(Conn, dobj);           //  ����
                if( dobj.getRetObject("S").getRecord().get("GBN2").equals("�濵"))
                {
                    dobj  = CALLsave_manper_juso_XIUD2(Conn, dobj);           //  �����ϱ�
                }
                else
                {
                    dobj  = CALLsave_manper_juso_XIUD12(Conn, dobj);           //  �����ϱ�
                }
            }
        }
        return dobj;
    }
    // ����
    public DOBJ CALLsave_manper_juso_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsave_manper_juso_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_manper_juso_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SYSDATE  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // �����ϱ�
    public DOBJ CALLsave_manper_juso_XIUD2(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsave_manper_juso_XIUD2(dobj, dvobj);
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
    private SQLObject SQLsave_manper_juso_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE  \n";
        query +=" GIBU.EUNDORI_TRANS_JUSO2  \n";
        query +=" SET N_MNGERSTR_ZIP = :N_ZIP , N_MNGERSTR_ADDR1 = :N_ADDR1 , N_MNGERSTR_ADDR2 = :N_ADDR2 , N_MNGERSTR_ADDR3 = :N_ADDR3 , N_MNGERSTR_BD_MNG_NUM = :N_BD_MNG_NUM  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����ϱ�
    public DOBJ CALLsave_manper_juso_XIUD12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD12");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_manper_juso_XIUD12(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_manper_juso_XIUD12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE  \n";
        query +=" GIBU.EUNDORI_TRANS_JUSO2  \n";
        query +=" SET N_PERMMSTR_ZIP = :N_ZIP , N_PERMMSTR_ADDR1 = :N_ADDR1 , N_PERMMSTR_ADDR2 = :N_ADDR2 , N_PERMMSTR_ADDR3 = :N_ADDR3 , N_PERMMSTR_BD_MNG_NUM = :N_BD_MNG_NUM  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$save_manper_juso
    //##**$$end
}
