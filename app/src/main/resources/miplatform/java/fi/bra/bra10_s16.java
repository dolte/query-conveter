
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s16
{
    public bra10_s16()
    {
    }
    //##**$$sel_contr_info
    /*
    */
    public DOBJ CTLsel_contr_info(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("DUP_GBN").equals("Y"))
            {
                dobj  = CALLsel_contr_info_SEL6(Conn, dobj);           //  �ߺ�������������ȸ
                dobj  = CALLsel_contr_info_MRG8( dobj);        //  MRG
            }
            else if( dobj.getRetObject("S").getRecord().get("DUP_GBN").equals("N"))
            {
                dobj  = CALLsel_contr_info_SEL7(Conn, dobj);           //  ���ϰ�����������ȸ
                dobj  = CALLsel_contr_info_MRG8( dobj);        //  MRG
            }
            else
            {
                dobj  = CALLsel_contr_info_SEL1(Conn, dobj);           //  ���������ȸ
                dobj  = CALLsel_contr_info_MRG8( dobj);        //  MRG
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
    public DOBJ CTLsel_contr_info( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("DUP_GBN").equals("Y"))
        {
            dobj  = CALLsel_contr_info_SEL6(Conn, dobj);           //  �ߺ�������������ȸ
            dobj  = CALLsel_contr_info_MRG8( dobj);        //  MRG
        }
        else if( dobj.getRetObject("S").getRecord().get("DUP_GBN").equals("N"))
        {
            dobj  = CALLsel_contr_info_SEL7(Conn, dobj);           //  ���ϰ�����������ȸ
            dobj  = CALLsel_contr_info_MRG8( dobj);        //  MRG
        }
        else
        {
            dobj  = CALLsel_contr_info_SEL1(Conn, dobj);           //  ���������ȸ
            dobj  = CALLsel_contr_info_MRG8( dobj);        //  MRG
        }
        return dobj;
    }
    // ���������ȸ
    public DOBJ CALLsel_contr_info_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_contr_info_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_contr_info_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USE_YN = dobj.getRetObject("S").getRecord().get("USE_YN");   //��뱸��
        String   MATCH_GBN = dobj.getRetObject("S").getRecord().get("MATCH_GBN");   //��Ī ����
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BSCON_CD , BSCON_UPSO_CD , BSCON_UPSO_NM , APPL_DAY , SEQ , UPSO_CD , UPSO_ZIP , UPSO_ADDR1 , UPSO_ADDR2 , MONPRNCFEE , MATCH_GBN , USE_YN , INSPRES_ID , INS_DATE , MODPRES_ID , MOD_DATE , BSTYP_CD , ATAX_YN , REMAK , FIDU.GET_STAFF_NM(INSPRES_ID) AS INSPRES_NM , FIDU.GET_STAFF_NM(MODPRES_ID) AS MODPRES_NM  ";
        query +=" FROM GIBU.TBRA_BSCON_CONTRINFO  ";
        query +=" WHERE 1=1  ";
        if( !USE_YN.equals("") )
        {
            query +=" AND USE_YN = :USE_YN  ";
        }
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD = :BSCON_CD  ";
        }
        if( !MATCH_GBN.equals("") )
        {
            query +=" AND MATCH_GBN = :MATCH_GBN  ";
        }
        query +=" ORDER BY BSCON_CD, BSCON_UPSO_CD, BSCON_UPSO_NM, SEQ  ";
        sobj.setSql(query);
        if(!USE_YN.equals(""))
        {
            sobj.setString("USE_YN", USE_YN);               //��뱸��
        }
        if(!MATCH_GBN.equals(""))
        {
            sobj.setString("MATCH_GBN", MATCH_GBN);               //��Ī ����
        }
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        }
        return sobj;
    }
    // MRG
    public DOBJ CALLsel_contr_info_MRG8(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG8");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL6, SEL7","");
        rvobj.setName("MRG8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // �ߺ�������������ȸ
    public DOBJ CALLsel_contr_info_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_contr_info_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_contr_info_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USE_YN = dobj.getRetObject("S").getRecord().get("USE_YN");   //��뱸��
        String   MATCH_GBN = dobj.getRetObject("S").getRecord().get("MATCH_GBN");   //��Ī ����
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BSCON_CD , BSCON_UPSO_CD , BSCON_UPSO_NM , APPL_DAY , SEQ , UPSO_CD , UPSO_ZIP , UPSO_ADDR1 , UPSO_ADDR2 , MONPRNCFEE , MATCH_GBN , USE_YN , INSPRES_ID , INS_DATE , MODPRES_ID , MOD_DATE , BSTYP_CD , ATAX_YN , REMAK , FIDU.GET_STAFF_NM(INSPRES_ID) AS INSPRES_NM , FIDU.GET_STAFF_NM(MODPRES_ID) AS MODPRES_NM  ";
        query +=" FROM GIBU.TBRA_BSCON_CONTRINFO  ";
        query +=" WHERE 1=1  ";
        if( !USE_YN.equals("") )
        {
            query +=" AND USE_YN = :USE_YN  ";
        }
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD = :BSCON_CD  ";
        }
        if( !MATCH_GBN.equals("") )
        {
            query +=" AND MATCH_GBN = :MATCH_GBN  ";
        }
        query +=" AND BSCON_UPSO_CD IN (SELECT BSCON_UPSO_CD  ";
        query +=" FROM GIBU.TBRA_BSCON_CONTRINFO  ";
        query +=" WHERE 1=1  ";
        if( !USE_YN.equals("") )
        {
            query +=" AND USE_YN = :USE_YN  ";
        }
        query +=" GROUP BY BSCON_UPSO_CD HAVING COUNT(1) > 1)  ";
        query +=" ORDER BY BSCON_CD, BSCON_UPSO_CD, BSCON_UPSO_NM, SEQ  ";
        sobj.setSql(query);
        if(!USE_YN.equals(""))
        {
            sobj.setString("USE_YN", USE_YN);               //��뱸��
        }
        if(!MATCH_GBN.equals(""))
        {
            sobj.setString("MATCH_GBN", MATCH_GBN);               //��Ī ����
        }
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        }
        return sobj;
    }
    // ���ϰ�����������ȸ
    public DOBJ CALLsel_contr_info_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_contr_info_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_contr_info_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   USE_YN = dobj.getRetObject("S").getRecord().get("USE_YN");   //��뱸��
        String   MATCH_GBN = dobj.getRetObject("S").getRecord().get("MATCH_GBN");   //��Ī ����
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BSCON_CD , BSCON_UPSO_CD , BSCON_UPSO_NM , APPL_DAY , SEQ , UPSO_CD , UPSO_ZIP , UPSO_ADDR1 , UPSO_ADDR2 , MONPRNCFEE , MATCH_GBN , USE_YN , INSPRES_ID , INS_DATE , MODPRES_ID , MOD_DATE , BSTYP_CD , ATAX_YN , REMAK , FIDU.GET_STAFF_NM(INSPRES_ID) AS INSPRES_NM , FIDU.GET_STAFF_NM(MODPRES_ID) AS MODPRES_NM  ";
        query +=" FROM GIBU.TBRA_BSCON_CONTRINFO  ";
        query +=" WHERE 1=1  ";
        if( !USE_YN.equals("") )
        {
            query +=" AND USE_YN = :USE_YN  ";
        }
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD = :BSCON_CD  ";
        }
        if( !MATCH_GBN.equals("") )
        {
            query +=" AND MATCH_GBN = :MATCH_GBN  ";
        }
        query +=" AND BSCON_UPSO_CD NOT IN (SELECT BSCON_UPSO_CD  ";
        query +=" FROM GIBU.TBRA_BSCON_CONTRINFO  ";
        query +=" WHERE 1=1  ";
        if( !USE_YN.equals("") )
        {
            query +=" AND USE_YN = :USE_YN  ";
        }
        query +=" GROUP BY BSCON_UPSO_CD HAVING COUNT(1) > 1)  ";
        query +=" ORDER BY BSCON_CD, BSCON_UPSO_CD, BSCON_UPSO_NM, SEQ  ";
        sobj.setSql(query);
        if(!USE_YN.equals(""))
        {
            sobj.setString("USE_YN", USE_YN);               //��뱸��
        }
        if(!MATCH_GBN.equals(""))
        {
            sobj.setString("MATCH_GBN", MATCH_GBN);               //��Ī ����
        }
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        }
        return sobj;
    }
    //##**$$sel_contr_info
    //##**$$get_komca_data
    /*
    */
    public DOBJ CTLget_komca_data(DOBJ dobj)
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
            dobj  = CALLget_komca_data_SEL1(Conn, dobj);           //  ������ȸ
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
    public DOBJ CTLget_komca_data( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_komca_data_SEL1(Conn, dobj);           //  ������ȸ
        return dobj;
    }
    // ������ȸ
    public DOBJ CALLget_komca_data_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLget_komca_data_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_komca_data_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ADDR = dobj.getRetObject("S").getRecord().get("ADDR");   //�����ּ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,  UPSO_CD  ,  UPSO_NM  ,  ADDR  ,  OPBI_DAY  ,  SUBSTR(A.BSTYP_CD,  2)  AS  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  UPSO_CD  ,  UPSO_NM  ,  UPSO_NEW_ADDR1  ||  '  '  ||  UPSO_NEW_ADDR2  ||  UPSO_REF_INFO  AS  ADDR  ,  OPBI_DAY  ,   \n";
        query +=" (SELECT  GRAD_GBN  ||  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  GRAD_GBN  =  GIBU.FT_GET_BSTYP_INFO(UPSO_CD)   \n";
        query +=" AND  BSTYP_CD  =  'z')  AS  BSTYP_CD  FROM  GIBU.TBRA_UPSO  A  WHERE  TRIM(UPSO_NEW_ADDR1)  LIKE  TRIM(:ADDR)  ||  '%'   \n";
        query +=" AND  CLSBS_YRMN  IS  NULL  )  A  ,  GIBU.TBRA_BSTYPGRAD  B  WHERE  SUBSTR(A.BSTYP_CD,  1,  1)  =  B.GRAD_GBN   \n";
        query +=" AND  B.BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  IN  ('d','e','n','q','r','w','x','E','G','H','g','K','L','M','N','O')  ORDER  BY  UPSO_NM,  OPBI_DAY ";
        sobj.setSql(query);
        sobj.setString("ADDR", ADDR);               //�����ּ�
        return sobj;
    }
    //##**$$get_komca_data
    //##**$$mng_match_contr
    /*
    */
    public DOBJ CTLmng_match_contr(DOBJ dobj)
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
            dobj  = CALLmng_match_contr_MIUD1(Conn, dobj);           //  �б�
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
    public DOBJ CTLmng_match_contr( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_match_contr_MIUD1(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLmng_match_contr_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_match_contr_SEL5(Conn, dobj);           //  ������Ī������ȸ
                if( dobj.getRetObject("SEL5").getRecord().getDouble("CNT") < 1)
                {
                    dobj  = CALLmng_match_contr_UPD11(Conn, dobj);           //  �̸�Ī ��Ī���� ����
                }
                else
                {
                    dobj  = CALLmng_match_contr_UPD8(Conn, dobj);           //  ��Ī��������
                    dobj  = CALLmng_match_contr_SEL11(Conn, dobj);           //  ����ä��
                    dobj  = CALLmng_match_contr_SEL14(Conn, dobj);           //  SEL
                    dobj  = CALLmng_match_contr_INS7(Conn, dobj);           //  ��Ī�����߰�
                }
            }
        }
        return dobj;
    }
    // ������Ī������ȸ
    public DOBJ CALLmng_match_contr_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_match_contr_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_match_contr_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_ZIP = dobj.getRetObject("R").getRecord().get("UPSO_ZIP");   //���� �����ȣ
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  BSCON_UPSO_CD  =  :BSCON_UPSO_CD   \n";
        query +=" AND  UPSO_ZIP  =  :UPSO_ZIP   \n";
        query +=" AND  MATCH_GBN  =  'Y'   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        return sobj;
    }
    // �̸�Ī ��Ī���� ����
    public DOBJ CALLmng_match_contr_UPD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD11");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD11");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_match_contr_UPD11(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD11") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_match_contr_UPD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   BSCON_UPSO_CD = dvobj.getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   MATCH_GBN = dvobj.getRecord().get("MATCH_GBN");   //��Ī ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BSCON_CONTRINFO  \n";
        query +=" set MATCH_GBN=:MATCH_GBN , UPSO_CD=:UPSO_CD , REMAK=:REMAK  \n";
        query +=" where BSCON_UPSO_CD=:BSCON_UPSO_CD  \n";
        query +=" and SEQ=:SEQ  \n";
        query +=" and BSCON_CD=:BSCON_CD";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("REMAK", REMAK);               //���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("MATCH_GBN", MATCH_GBN);               //��Ī ����
        return sobj;
    }
    // ��Ī��������
    public DOBJ CALLmng_match_contr_UPD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD8");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_match_contr_UPD8(dobj, dvobj);
            qexe.DispSelectSql(sobj);
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
    private SQLObject SQLmng_match_contr_UPD8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //���� �����ȣ
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   BSCON_UPSO_CD = dvobj.getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   USE_YN ="N";   //��뱸��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BSCON_CONTRINFO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , USE_YN=:USE_YN , MOD_DATE=SYSDATE  \n";
        query +=" where BSCON_UPSO_CD=:BSCON_UPSO_CD  \n";
        query +=" and SEQ=:SEQ  \n";
        query +=" and UPSO_ZIP=:UPSO_ZIP  \n";
        query +=" and BSCON_CD=:BSCON_CD";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("USE_YN", USE_YN);               //��뱸��
        return sobj;
    }
    // ����ä��
    public DOBJ CALLmng_match_contr_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_match_contr_SEL11(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_match_contr_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   UPSO_ZIP = dobj.getRetObject("R").getRecord().get("UPSO_ZIP");   //���� �����ȣ
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  AS  SEQ  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  BSCON_UPSO_CD  =  :BSCON_UPSO_CD   \n";
        query +=" AND  UPSO_ZIP  =  :UPSO_ZIP ";
        sobj.setSql(query);
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        return sobj;
    }
    // SEL
    public DOBJ CALLmng_match_contr_SEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL14");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL14");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_match_contr_SEL14(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_match_contr_SEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  (CASE  WHEN  :UPSO_CD  IS  NULL  THEN  'N'  ELSE  'Y'  END)  AS  MATCH_GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��Ī�����߰�
    public DOBJ CALLmng_match_contr_INS7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS7");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_match_contr_INS7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_match_contr_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //���� �����ȣ
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   UPSO_ADDR1 = dvobj.getRecord().get("UPSO_ADDR1");   //���� �ּ�1
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   BSCON_UPSO_NM = dvobj.getRecord().get("BSCON_UPSO_NM");   //Ÿ��ü���Ҹ�
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //������
        String   BSCON_UPSO_CD = dvobj.getRecord().get("BSCON_UPSO_CD");   //Ÿ��ü�����ڵ�
        String   ATAX_YN = dvobj.getRecord().get("ATAX_YN");   //�ΰ��� ����
        String   UPSO_ADDR2 = dvobj.getRecord().get("UPSO_ADDR2");   //���� �ּ�2
        String   APPL_DAY = dvobj.getRecord().get("APPL_DAY");   //���� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   MATCH_GBN = dobj.getRetObject("SEL14").getRecord().get("MATCH_GBN");   //��Ī ����
        double   SEQ = dobj.getRetObject("SEL11").getRecord().getDouble("SEQ");   //������ȣ
        String   USE_YN ="Y";   //��뱸��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BSCON_CONTRINFO (USE_YN, APPL_DAY, INSPRES_ID, UPSO_ADDR2, ATAX_YN, BSCON_UPSO_CD, MONPRNCFEE, BSTYP_CD, BSCON_UPSO_NM, SEQ, BSCON_CD, INS_DATE, UPSO_ADDR1, UPSO_CD, MATCH_GBN, REMAK, UPSO_ZIP)  \n";
        query +=" values(:USE_YN , :APPL_DAY , :INSPRES_ID , :UPSO_ADDR2 , :ATAX_YN , :BSCON_UPSO_CD , :MONPRNCFEE , :BSTYP_CD , :BSCON_UPSO_NM , :SEQ , :BSCON_CD , SYSDATE, :UPSO_ADDR1 , :UPSO_CD , :MATCH_GBN , :REMAK , :UPSO_ZIP )";
        sobj.setSql(query);
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("UPSO_ADDR1", UPSO_ADDR1);               //���� �ּ�1
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("BSCON_UPSO_NM", BSCON_UPSO_NM);               //Ÿ��ü���Ҹ�
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //Ÿ��ü�����ڵ�
        sobj.setString("ATAX_YN", ATAX_YN);               //�ΰ��� ����
        sobj.setString("UPSO_ADDR2", UPSO_ADDR2);               //���� �ּ�2
        sobj.setString("APPL_DAY", APPL_DAY);               //���� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("MATCH_GBN", MATCH_GBN);               //��Ī ����
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("USE_YN", USE_YN);               //��뱸��
        return sobj;
    }
    //##**$$mng_match_contr
    //##**$$end
}
