
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_s05
{
    public bra05_s05()
    {
    }
    //##**$$mng_bpap
    /*
    */
    public DOBJ CTLmng_bpap(DOBJ dobj)
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
            dobj  = CALLmng_bpap_MIUD1(Conn, dobj);           //  �б�
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
    public DOBJ CTLmng_bpap( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_bpap_MIUD1(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLmng_bpap_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLmng_bpap_INS5(Conn, dobj);           //  �ְ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_bpap_DEL6(Conn, dobj);           //  �ְ�����
            }
        }
        return dobj;
    }
    // �ְ�����
    public DOBJ CALLmng_bpap_DEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_bpap_DEL6(dobj, dvobj);
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
    private SQLObject SQLmng_bpap_DEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SEND_DAY = dvobj.getRecord().get("SEND_DAY");   //�߼�����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BPAP  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and SEND_DAY=:SEND_DAY";
        sobj.setSql(query);
        sobj.setString("SEND_DAY", SEND_DAY);               //�߼�����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �ְ����
    public DOBJ CALLmng_bpap_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_bpap_INS5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_bpap_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        double   TOT_EADDT_AMT = dvobj.getRecord().getDouble("TOT_EADDT_AMT");   //�� �߰��� �ݾ�
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //���۳��
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   TERM_DAY = dvobj.getRecord().get("TERM_DAY");   //���� ����
        String   SEND_DAY = dvobj.getRecord().get("SEND_DAY");   //�߼�����
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   TOT_USE_AMT = dvobj.getRecord().getDouble("TOT_USE_AMT");   //�� ��� �ݾ�
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //�� ���� �ݾ�
        String   BPAP_DAY = dvobj.getRecord().get("BPAP_DAY");   //�ְ� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BPAP (INS_DATE, INSPRES_ID, BPAP_DAY, TOT_ADDT_AMT, TOT_USE_AMT, UPSO_CD, SEND_DAY, TERM_DAY, END_YRMN, REMAK, START_YRMN, TOT_EADDT_AMT)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BPAP_DAY , :TOT_ADDT_AMT , :TOT_USE_AMT , :UPSO_CD , :SEND_DAY , :TERM_DAY , :END_YRMN , :REMAK , :START_YRMN , :TOT_EADDT_AMT )";
        sobj.setSql(query);
        sobj.setDouble("TOT_EADDT_AMT", TOT_EADDT_AMT);               //�� �߰��� �ݾ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("TERM_DAY", TERM_DAY);               //���� ����
        sobj.setString("SEND_DAY", SEND_DAY);               //�߼�����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("TOT_USE_AMT", TOT_USE_AMT);               //�� ��� �ݾ�
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //�� ���� �ݾ�
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    //##**$$mng_bpap
    //##**$$sel_bpap
    /*
    */
    public DOBJ CTLsel_bpap(DOBJ dobj)
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
            dobj  = CALLsel_bpap_SEL1(Conn, dobj);           //  �ְ���ȸ
            if(!dobj.getRetObject("S").getRecord().get("UPSO_CD").equals(""))
            {
                dobj  = CALLsel_bpap_SEL4(Conn, dobj);           //  ������¿� ��ȸ
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
    public DOBJ CTLsel_bpap( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_bpap_SEL1(Conn, dobj);           //  �ְ���ȸ
        if(!dobj.getRetObject("S").getRecord().get("UPSO_CD").equals(""))
        {
            dobj  = CALLsel_bpap_SEL4(Conn, dobj);           //  ������¿� ��ȸ
        }
        return dobj;
    }
    // �ְ���ȸ
    public DOBJ CALLsel_bpap_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_bpap_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bpap_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   START_DAY = dvobj.getRecord().get("START_DAY");   //������
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_DAY = dvobj.getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.BRAN_CD  ,  GIBU.GET_BRAN_NM(B.BRAN_CD)  AS  BRAN_NM  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.SEND_DAY  ,  A.BPAP_DAY  ,  A.TERM_DAY  ,  C.POST_NUM  AS  BRAN_ZIP  ,  C.ADDR  ||  '  '  ||  C.HNM  AS  BRAN_ADDR  ,   \n";
        query +=" (SELECT  GIBU.FT_GET_PHONE_FORMAT(IPPBX_INPHONE_NUM)  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  B.STAFF_CD)  AS  BRAN_TEL  ,   \n";
        query +=" (SELECT  GIBU.FT_GET_PHONE_FORMAT(IPPBX_USER_ID)  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  B.STAFF_CD)  AS  BRAN_FAX  ,  B.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,  B.MNGEMSTR_NM  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||  B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  AS  ADDR  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  AS  STAFF_NM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.TOT_USE_AMT  ,  A.TOT_ADDT_AMT  ,  A.TOT_EADDT_AMT  ,  (CASE  WHEN  D.JOB_GBN  IS  NOT  NULL  THEN  'Y'  ELSE  'N'  END)  AS  PRINT_YN  ,  B.NEW_DAY  FROM  GIBU.TBRA_BPAP  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_BIPLC  C  ,  GIBU.TBRA_UPSO_VISIT  D  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  C.GIBU   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.SEND_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  B.STAFF_CD  =  NVL(:STAFF_CD,  B.STAFF_CD)   \n";
        query +=" AND  A.UPSO_CD  =  D.UPSO_CD(+)   \n";
        query +=" AND  A.SEND_DAY  =  D.VISIT_DAY(+)   \n";
        query +=" AND  D.JOB_GBN(+)  =  'C'  ORDER  BY  B.BRAN_CD,  FIDU.GET_STAFF_NM(B.STAFF_CD),  B.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    // ������¿� ��ȸ
    public DOBJ CALLsel_bpap_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_bpap_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bpap_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.BRAN_CD  ,  GIBU.GET_BRAN_NM(B.BRAN_CD)  AS  BRAN_NM  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.SEND_DAY  AS  DISP_DAY  ,  A.BPAP_DAY  ,  A.TERM_DAY  AS  CONTR_TERM_DAY  ,  C.POST_NUM  AS  BRAN_ZIP  ,  C.ADDR  ||  '  '  ||  C.HNM  AS  BRAN_ADDR  ,   \n";
        query +=" (SELECT  GIBU.FT_GET_PHONE_FORMAT(IPPBX_INPHONE_NUM)  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  B.STAFF_CD)  AS  BRAN_TEL  ,   \n";
        query +=" (SELECT  GIBU.FT_GET_PHONE_FORMAT(IPPBX_USER_ID)  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  B.STAFF_CD)  AS  BRAN_FAX  ,  B.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,  B.MNGEMSTR_NM  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||  B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  AS  ADDR  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  AS  STAFF_NM  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.TOT_USE_AMT  ,  A.TOT_ADDT_AMT  ,  A.TOT_EADDT_AMT  ,  (CASE  WHEN  D.JOB_GBN  IS  NOT  NULL  THEN  'Y'  ELSE  'N'  END)  AS  PRINT_YN  ,  B.NEW_DAY  FROM  GIBU.TBRA_BPAP  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_BIPLC  C  ,  GIBU.TBRA_UPSO_VISIT  D  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  C.GIBU   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.SEND_DAY  =   \n";
        query +=" (SELECT  MAX(SEND_DAY)  FROM  GIBU.TBRA_BPAP  WHERE  UPSO_CD  =  :UPSO_CD)   \n";
        query +=" AND  A.UPSO_CD  =  D.UPSO_CD(+)   \n";
        query +=" AND  A.SEND_DAY  =  D.VISIT_DAY(+)   \n";
        query +=" AND  D.JOB_GBN(+)  =  'C'  ORDER  BY  B.BRAN_CD,  FIDU.GET_STAFF_NM(B.STAFF_CD),  B.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$sel_bpap
    //##**$$ins_visit_print
    /*
    */
    public DOBJ CTLins_visit_print(DOBJ dobj)
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
            dobj  = CALLins_visit_print_MPD3(Conn, dobj);           //  ��Ƽó��
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
    public DOBJ CTLins_visit_print( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLins_visit_print_MPD3(Conn, dobj);           //  ��Ƽó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ��Ƽó��
    public DOBJ CALLins_visit_print_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLins_visit_print_SEL5(Conn, dobj);           //  VISIT_SEQ���ϱ�
                dobj  = CALLins_visit_print_INS1(Conn, dobj);           //  ���ҹ湮�̷µ��
            }
        }
        return dobj;
    }
    // VISIT_SEQ���ϱ�
    public DOBJ CALLins_visit_print_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLins_visit_print_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLins_visit_print_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("SEND_DAY");   //�湮 ����
        String   JOB_GBN ="C";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),  0)  +  1  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ҹ湮�̷µ��
    public DOBJ CALLins_visit_print_INS1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS1");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLins_visit_print_INS1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLins_visit_print_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_TIME ="";        //�湮 �ð�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CONSPRES ="[������] "+dobj.getRetObject("R").getRecord().get("MNGEMSTR_NM");   //�����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����
        String   REMAK ="�ְ� ����(�ְ���Ϲ����)";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("SEND_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL5").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, VISIT_TIME, INSPRES_ID, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, TO_CHAR(SYSDATE, 'HH24MI'), :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    //##**$$ins_visit_print
    //##**$$end
}
