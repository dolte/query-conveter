
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac06_s05
{
    public tac06_s05()
    {
    }
    //##**$$supp_chgdoc_select
    /*
    */
    public DOBJ CTLsupp_chgdoc_select(DOBJ dobj)
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
            dobj  = CALLsupp_chgdoc_select_SEL1(Conn, dobj);           //  ��ȸ
            dobj  = CALLsupp_chgdoc_select_MPD2(Conn, dobj);           //  ÷�����Ͽ�
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLsupp_chgdoc_select_MPD11(Conn, dobj);           //  ȸ����
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLsupp_chgdoc_select_SEL10(Conn, dobj);           //  ������ȸ��
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
    public DOBJ CTLsupp_chgdoc_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsupp_chgdoc_select_SEL1(Conn, dobj);           //  ��ȸ
        dobj  = CALLsupp_chgdoc_select_MPD2(Conn, dobj);           //  ÷�����Ͽ�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLsupp_chgdoc_select_MPD11(Conn, dobj);           //  ȸ����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLsupp_chgdoc_select_SEL10(Conn, dobj);           //  ������ȸ��
        return dobj;
    }
    // ��ȸ
    public DOBJ CALLsupp_chgdoc_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsupp_chgdoc_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsupp_chgdoc_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   A_TO = dobj.getRetObject("S").getRecord().get("A_TO");   //��������
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //����
        String   A_FROM = dobj.getRetObject("S").getRecord().get("A_FROM");   //��������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT MNG_NUM, TO_CHAR(INS_DATE, 'YYYY-MM-DD AM HH:MI:SS') AS INS_DATE, GBN, DOC_TITLE, CONTENT, APPL_YN, APPL_YRMN, SVR_FILE_ROUT AS PATH, SVR_FILE_NM AS UNIFILENAME, ATTCH_FILE_NM AS UPFILENAME, ATTCH_FILE_CTENT, NVL(LENGTH(ATTCH_FILE_NM),0) AS PROF_CHG_FILENM_LEN, NVL(LENGTH(ATTCH_FILE_CTENT),0) AS ATTCH_FILE_CTENT_LEN  ";
        query +=" FROM FIDU.TTAC_SUPP_CHGDOC A  ";
        query +=" WHERE APPL_YRMN IS NULL  ";
        query +=" UNION ALL  ";
        query +=" SELECT MNG_NUM, TO_CHAR(INS_DATE, 'YYYY-MM-DD AM HH:MI:SS') AS INS_DATE, GBN, DOC_TITLE, CONTENT, APPL_YN, APPL_YRMN, SVR_FILE_ROUT AS PATH, SVR_FILE_NM AS UNIFILENAME, ATTCH_FILE_NM AS UPFILENAME, ATTCH_FILE_CTENT, NVL(LENGTH(ATTCH_FILE_NM),0) AS PROF_CHG_FILENM_LEN, NVL(LENGTH(ATTCH_FILE_CTENT),0) AS ATTCH_FILE_CTENT_LEN  ";
        query +=" FROM FIDU.TTAC_SUPP_CHGDOC A  ";
        query +=" WHERE APPL_YRMN BETWEEN :A_FROM  ";
        query +=" AND :A_TO  ";
        if( !GBN.equals("") )
        {
            query +=" AND GBN LIKE :GBN  ";
        }
        if( !MB_CD.equals("") )
        {
            query +=" AND MNG_NUM IN (SELECT A.MNG_NUM  ";
            query +=" FROM FIDU.TTAC_SUPP_CHGDOC A, FIDU.TTAC_SUPP_CHGDOC_MB B  ";
            query +=" WHERE A.MNG_NUM = B.MNG_NUM(+)  ";
            query +=" AND A.APPL_YRMN BETWEEN :A_FROM  ";
            query +=" AND :A_TO  ";
            query +=" AND B.MB_CD = :MB_CD )  ";
        }
        query +=" ORDER BY MNG_NUM  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        }
        sobj.setString("A_TO", A_TO);               //��������
        if(!GBN.equals(""))
        {
            sobj.setString("GBN", GBN);               //����
        }
        sobj.setString("A_FROM", A_FROM);               //��������
        return sobj;
    }
    // ÷�����Ͽ�
    public DOBJ CALLsupp_chgdoc_select_MPD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD2");
        VOBJ dvobj = dobj.getRetObject("SEL1");         //��ȸ���� ������Ų OBJECT�Դϴ�.(CALLsupp_chgdoc_select_SEL1)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("PROF_CHG_FILENM_LEN").equals("0") && !dvobj.getRecord().get("ATTCH_FILE_CTENT_LEN").equals("0"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsupp_chgdoc_select_SEL5(Conn, dobj);           //  ÷������ ����
            }
        }
        return dobj;
    }
    // ÷������ ����
    public DOBJ CALLsupp_chgdoc_select_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsupp_chgdoc_select_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        String fullname="";
        rvobj.first();
        while(rvobj.next())
        {
            wutil.makeFileOverwirte( dobj.getRetObject("R").getRecord().get("PATH"), dobj.getRetObject("R").getRecord().get("UNIFILENAME"),rvobj.getRecord().getBytes("FILES"));
        }
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsupp_chgdoc_select_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   MNG_NUM = dobj.getRetObject("R").getRecord().getDouble("MNG_NUM");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MNG_NUM,  ATTCH_FILE_NM,  ATTCH_FILE_CTENT  AS  FILES,  SVR_FILE_NM,  SVR_FILE_ROUT  FROM  FIDU.TTAC_SUPP_CHGDOC  WHERE  MNG_NUM  =  :MNG_NUM ";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        return sobj;
    }
    // ȸ����
    public DOBJ CALLsupp_chgdoc_select_MPD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD11");
        VOBJ dvobj = dobj.getRetObject("SEL1");         //��ȸ���� ������Ų OBJECT�Դϴ�.(CALLsupp_chgdoc_select_SEL1)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsupp_chgdoc_select_SEL7(Conn, dobj);           //  ȸ���ڵ� ȹ��
                dobj  = CALLsupp_chgdoc_select_ADD9( dobj);        //  �߰�
            }
        }
        return dobj;
    }
    // ȸ���ڵ� ȹ��
    public DOBJ CALLsupp_chgdoc_select_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsupp_chgdoc_select_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsupp_chgdoc_select_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   MNG_NUM = dobj.getRetObject("R").getRecord().getDouble("MNG_NUM");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MNG_NUM,  MB_CD,  FIDU.GET_MB_NM(A.MB_CD)  AS  HANMB_NM,   \n";
        query +=" (SELECT   \n";
        query +=" (SELECT  BANK_NM  FROM  ACCT.TCAC_BANK  WHERE  BANK_CD  =  X.SUPPBANK_CD)  ||  '  '  ||  SUPPACCN_NUM  FROM  FIDU.TMEM_MB  X  WHERE  X.MB_CD  =  A.MB_CD)  AS  SUPPACCN_INFO  FROM  FIDU.TTAC_SUPP_CHGDOC_MB  A  WHERE  MNG_NUM  =  :MNG_NUM ";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        return sobj;
    }
    // �߰�
    public DOBJ CALLsupp_chgdoc_select_ADD9(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("ADD9");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        VOBJ dvobj = dobj.getRetObject("SEL7");          //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        rvobj = wutil.getAddedVobj(dobj,"ADD9","", dvobj );
        rvobj.setName("ADD9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ������ȸ��
    public DOBJ CALLsupp_chgdoc_select_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsupp_chgdoc_select_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsupp_chgdoc_select_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   A_TO = dobj.getRetObject("S").getRecord().get("A_TO");   //��������
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //����
        String   A_FROM = dobj.getRetObject("S").getRecord().get("A_FROM");   //��������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.MNG_NUM, TO_CHAR(A.INS_DATE, 'YYYY-MM-DD AM HH:MI:SS') AS INS_DATE, A.GBN, A.DOC_TITLE, A.CONTENT, A.APPL_YN, A.APPL_YRMN, B.MB_CD, FIDU.GET_MB_NM(B.MB_CD) AS HANMB_NM  ";
        query +=" FROM FIDU.TTAC_SUPP_CHGDOC A, FIDU.TTAC_SUPP_CHGDOC_MB B  ";
        query +=" WHERE A.MNG_NUM = B.MNG_NUM  ";
        query +=" AND APPL_YRMN IS NULL  ";
        query +=" UNION ALL  ";
        query +=" SELECT A.MNG_NUM, TO_CHAR(A.INS_DATE, 'YYYY-MM-DD AM HH:MI:SS') AS INS_DATE, A.GBN, A.DOC_TITLE, A.CONTENT, A.APPL_YN, A.APPL_YRMN, B.MB_CD, FIDU.GET_MB_NM(B.MB_CD) AS HANMB_NM  ";
        query +=" FROM FIDU.TTAC_SUPP_CHGDOC A, FIDU.TTAC_SUPP_CHGDOC_MB B  ";
        query +=" WHERE A.MNG_NUM = B.MNG_NUM  ";
        query +=" AND APPL_YRMN BETWEEN :A_FROM  ";
        query +=" AND :A_TO  ";
        if( !GBN.equals("") )
        {
            query +=" AND A.GBN LIKE :GBN  ";
        }
        if( !MB_CD.equals("") )
        {
            query +=" AND B.MB_CD = :MB_CD  ";
        }
        query +=" ORDER BY MNG_NUM, MB_CD  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        }
        sobj.setString("A_TO", A_TO);               //��������
        if(!GBN.equals(""))
        {
            sobj.setString("GBN", GBN);               //����
        }
        sobj.setString("A_FROM", A_FROM);               //��������
        return sobj;
    }
    //##**$$supp_chgdoc_select
    //##**$$supp_chgdoc_getoid
    /*
    */
    public DOBJ CTLsupp_chgdoc_getoid(DOBJ dobj)
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
            dobj  = CALLsupp_chgdoc_getoid_SEL1(Conn, dobj);           //  ����ȹ��
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
    public DOBJ CTLsupp_chgdoc_getoid( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsupp_chgdoc_getoid_SEL1(Conn, dobj);           //  ����ȹ��
        return dobj;
    }
    // ����ȹ��
    public DOBJ CALLsupp_chgdoc_getoid_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsupp_chgdoc_getoid_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsupp_chgdoc_getoid_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(MNG_NUM),0)  +  1  AS  MNG_NUM  from  FIDU.TTAC_SUPP_CHGDOC ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$supp_chgdoc_getoid
    //##**$$supp_chgdoc_save
    /*
    */
    public DOBJ CTLsupp_chgdoc_save(DOBJ dobj)
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
            dobj  = CALLsupp_chgdoc_save_MIUD15(Conn, dobj);           //  �����б�
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLsupp_chgdoc_save_MIUD17(Conn, dobj);           //  ȸ���б�
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
    public DOBJ CTLsupp_chgdoc_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsupp_chgdoc_save_MIUD15(Conn, dobj);           //  �����б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLsupp_chgdoc_save_MIUD17(Conn, dobj);           //  ȸ���б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �����б�
    public DOBJ CALLsupp_chgdoc_save_MIUD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD15");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MIUD15");
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsupp_chgdoc_save_INS10(Conn, dobj);           //  ���ϻ��� ������ ���� ���
                if( dobj.getRetObject("R").getRecord().get("ATTCH_YN").equals("1"))
                {
                    dobj  = CALLsupp_chgdoc_save_FUT1( dobj);        //  ���Ϲ���
                    dobj  = CALLsupp_chgdoc_save_CVT2( dobj);        //  ���Ϲ����̵�����
                    dobj  = CALLsupp_chgdoc_save_UPD12(Conn, dobj);           //  ÷������ ���
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsupp_chgdoc_save_UPD24(Conn, dobj);           //  ���ϻ��� ������ ����
                if( dobj.getRetObject("R").getRecord().get("ATTCH_YN").equals("1"))
                {
                    dobj  = CALLsupp_chgdoc_save_FUT26( dobj);        //  ���Ϲ���
                    dobj  = CALLsupp_chgdoc_save_CVT27( dobj);        //  ���Ϲ����̵�����
                    dobj  = CALLsupp_chgdoc_save_UPD28(Conn, dobj);           //  ÷������ ���
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsupp_chgdoc_save_DEL8(Conn, dobj);           //  ����
                dobj  = CALLsupp_chgdoc_save_DEL26(Conn, dobj);           //  ����
            }
        }
        return dobj;
    }
    // ����
    public DOBJ CALLsupp_chgdoc_save_DEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL8");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsupp_chgdoc_save_DEL8(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL8") ;
        rvobj.Println("DEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsupp_chgdoc_save_DEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_SUPP_CHGDOC  \n";
        query +=" where MNG_NUM=:MNG_NUM";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        return sobj;
    }
    // ���ϻ��� ������ ����
    public DOBJ CALLsupp_chgdoc_save_UPD24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD24");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsupp_chgdoc_save_UPD24(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD24") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsupp_chgdoc_save_UPD24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DOC_TITLE = dvobj.getRecord().get("DOC_TITLE");   //��������
        String   MOD_DATE = dvobj.getRecord().get("MOD_DATE");   //���� �Ͻ�
        String   APPL_YN = dvobj.getRecord().get("APPL_YN");   //��ü�ڵ� ��.�ߺз� �˻���
        String   CONTENT = dvobj.getRecord().get("CONTENT");   //�Խù� ����
        String   APPL_YRMN = dvobj.getRecord().get("APPL_YRMN");   //���� ���
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   GBN = dvobj.getRecord().get("GBN");   //����
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_SUPP_CHGDOC  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , GBN=:GBN , APPL_YRMN=:APPL_YRMN , CONTENT=:CONTENT , APPL_YN=:APPL_YN , MOD_DATE=:MOD_DATE , DOC_TITLE=:DOC_TITLE  \n";
        query +=" where MNG_NUM=:MNG_NUM";
        sobj.setSql(query);
        sobj.setString("DOC_TITLE", DOC_TITLE);               //��������
        sobj.setString("MOD_DATE", MOD_DATE);               //���� �Ͻ�
        sobj.setString("APPL_YN", APPL_YN);               //��ü�ڵ� ��.�ߺз� �˻���
        sobj.setString("CONTENT", CONTENT);               //�Խù� ����
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("GBN", GBN);               //����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // ���ϻ��� ������ ���� ���
    public DOBJ CALLsupp_chgdoc_save_INS10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS10");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsupp_chgdoc_save_INS10(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS10") ;
        rvobj.Println("INS10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsupp_chgdoc_save_INS10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   DOC_TITLE = dvobj.getRecord().get("DOC_TITLE");   //��������
        String   APPL_YN = dvobj.getRecord().get("APPL_YN");   //��ü�ڵ� ��.�ߺз� �˻���
        String   CONTENT = dvobj.getRecord().get("CONTENT");   //�Խù� ����
        String   APPL_YRMN = dvobj.getRecord().get("APPL_YRMN");   //���� ���
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   GBN = dvobj.getRecord().get("GBN");   //����
        String   INSPRES_ID = dvobj.getRecord().get("GNV_USERID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_SUPP_CHGDOC (INS_DATE, INSPRES_ID, GBN, MNG_NUM, APPL_YRMN, CONTENT, APPL_YN, DOC_TITLE)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :GBN , :MNG_NUM , :APPL_YRMN , :CONTENT , :APPL_YN , :DOC_TITLE )";
        sobj.setSql(query);
        sobj.setString("DOC_TITLE", DOC_TITLE);               //��������
        sobj.setString("APPL_YN", APPL_YN);               //��ü�ڵ� ��.�ߺз� �˻���
        sobj.setString("CONTENT", CONTENT);               //�Խù� ����
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("GBN", GBN);               //����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // ����
    public DOBJ CALLsupp_chgdoc_save_DEL26(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL26");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsupp_chgdoc_save_DEL26(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL26") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsupp_chgdoc_save_DEL26(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_SUPP_CHGDOC_MB  \n";
        query +=" where MNG_NUM=:MNG_NUM";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        return sobj;
    }
    // ���Ϲ���
    public DOBJ CALLsupp_chgdoc_save_FUT1(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT1");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT1");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("PATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT1") ;
        dvobj.Println("FUT1");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ϲ���
    public DOBJ CALLsupp_chgdoc_save_FUT26(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT26");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT26");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("UNIFILENAME");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("PATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT26") ;
        dvobj.Println("FUT26");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ϲ����̵�����
    public DOBJ CALLsupp_chgdoc_save_CVT2(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT2");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("R");        //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("CVT2");
        String[] outcolumns ={"UNIFILENAME","FILESIZE","PATH","UPFILENAME","DFILEPATH"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.cpRecord();
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            for(int i=0;i<outcolumns.length;i++)
            {
                if(!record.containsKey(outcolumns[i]))
                {
                    record.remove(outcolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        dvobj.setName("CVT2") ;
        dvobj.Println("CVT2");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ϲ����̵�����
    public DOBJ CALLsupp_chgdoc_save_CVT27(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT27");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("R");        //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("CVT27");
        String[] outcolumns ={"UNIFILENAME","FILESIZE","PATH","UPFILENAME","DFILEPATH"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.cpRecord();
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            for(int i=0;i<outcolumns.length;i++)
            {
                if(!record.containsKey(outcolumns[i]))
                {
                    record.remove(outcolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        dvobj.setName("CVT27") ;
        dvobj.Println("CVT27");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ÷������ ���
    public DOBJ CALLsupp_chgdoc_save_UPD12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String fullname="";
        dvobj.first();
        while(dvobj.next())
        {
            byte[] binATTCH_FILE_CTENT=wutil.inputFileStream("/home/jeus/komca_web/upload", dobj.getRetObject("R").getRecord().get("UNIFILENAME"));
            dvobj.getRecord().put("ATTCH_FILE_CTENT", binATTCH_FILE_CTENT);
        }
        dvobj.Println("UPD12");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsupp_chgdoc_save_UPD12(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD12") ;
        rvobj.Println("UPD12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsupp_chgdoc_save_UPD12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        byte[]   ATTCH_FILE_CTENT = dvobj.getRecord().getBytes("ATTCH_FILE_CTENT");   //÷�����ϳ���
        String   ATTCH_FILE_NM = dobj.getRetObject("R").getRecord().get("UPFILENAME");   //÷�����ϸ�
        String   SVR_FILE_NM = dobj.getRetObject("R").getRecord().get("UNIFILENAME");   //���� ���� ��
        String   SVR_FILE_ROUT = dobj.getRetObject("CVT2").getRecord().get("DFILEPATH");   //���� ���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_SUPP_CHGDOC  \n";
        query +=" set ATTCH_FILE_CTENT=:ATTCH_FILE_CTENT , ATTCH_FILE_NM=:ATTCH_FILE_NM , SVR_FILE_ROUT=:SVR_FILE_ROUT , SVR_FILE_NM=:SVR_FILE_NM  \n";
        query +=" where MNG_NUM=:MNG_NUM";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setBlob("ATTCH_FILE_CTENT", ATTCH_FILE_CTENT);               //÷�����ϳ���
        sobj.setString("ATTCH_FILE_NM", ATTCH_FILE_NM);               //÷�����ϸ�
        sobj.setString("SVR_FILE_NM", SVR_FILE_NM);               //���� ���� ��
        sobj.setString("SVR_FILE_ROUT", SVR_FILE_ROUT);               //���� ���� ���
        return sobj;
    }
    // ÷������ ���
    public DOBJ CALLsupp_chgdoc_save_UPD28(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD28");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String fullname="";
        dvobj.first();
        while(dvobj.next())
        {
            byte[] binATTCH_FILE_CTENT=wutil.inputFileStream("/home/jeus/komca_web/upload", dobj.getRetObject("R").getRecord().get("UNIFILENAME"));
            dvobj.getRecord().put("ATTCH_FILE_CTENT", binATTCH_FILE_CTENT);
        }
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsupp_chgdoc_save_UPD28(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD28") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsupp_chgdoc_save_UPD28(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        byte[]   ATTCH_FILE_CTENT = dvobj.getRecord().getBytes("ATTCH_FILE_CTENT");   //÷�����ϳ���
        String   ATTCH_FILE_NM = dobj.getRetObject("R").getRecord().get("UPFILENAME");   //÷�����ϸ�
        String   SVR_FILE_NM = dobj.getRetObject("R").getRecord().get("UNIFILENAME");   //���� ���� ��
        String   SVR_FILE_ROUT = dobj.getRetObject("CVT27").getRecord().get("DFILEPATH");   //���� ���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_SUPP_CHGDOC  \n";
        query +=" set ATTCH_FILE_CTENT=:ATTCH_FILE_CTENT , ATTCH_FILE_NM=:ATTCH_FILE_NM , SVR_FILE_ROUT=:SVR_FILE_ROUT , SVR_FILE_NM=:SVR_FILE_NM  \n";
        query +=" where MNG_NUM=:MNG_NUM";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setBlob("ATTCH_FILE_CTENT", ATTCH_FILE_CTENT);               //÷�����ϳ���
        sobj.setString("ATTCH_FILE_NM", ATTCH_FILE_NM);               //÷�����ϸ�
        sobj.setString("SVR_FILE_NM", SVR_FILE_NM);               //���� ���� ��
        sobj.setString("SVR_FILE_ROUT", SVR_FILE_ROUT);               //���� ���� ���
        return sobj;
    }
    // ȸ���б�
    public DOBJ CALLsupp_chgdoc_save_MIUD17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD17");
        VOBJ dvobj = dobj.getRetObject("S1");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsupp_chgdoc_save_INS22(Conn, dobj);           //  ȸ�����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsupp_chgdoc_save_UPD23(Conn, dobj);           //  ��
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsupp_chgdoc_save_DEL24(Conn, dobj);           //  ����
            }
        }
        return dobj;
    }
    // ����
    public DOBJ CALLsupp_chgdoc_save_DEL24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL24");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsupp_chgdoc_save_DEL24(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL24") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsupp_chgdoc_save_DEL24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_SUPP_CHGDOC_MB  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        return sobj;
    }
    // ȸ�����
    public DOBJ CALLsupp_chgdoc_save_INS22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS22");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsupp_chgdoc_save_INS22(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS22") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsupp_chgdoc_save_INS22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   INSPRES_ID = dvobj.getRecord().get("GNV_USERID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_SUPP_CHGDOC_MB (MB_CD, INS_DATE, INSPRES_ID, MNG_NUM)  \n";
        query +=" values(:MB_CD , SYSDATE, :INSPRES_ID , :MNG_NUM )";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // ��
    public DOBJ CALLsupp_chgdoc_save_UPD23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD23");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsupp_chgdoc_save_UPD23(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD23") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsupp_chgdoc_save_UPD23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //������ȣ
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //ȸ�� �ڵ�
        String   INSPRES_ID = dvobj.getRecord().get("GNV_USERID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_SUPP_CHGDOC_MB  \n";
        query +=" set INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //������ȣ
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    //##**$$supp_chgdoc_save
    //##**$$end
}
