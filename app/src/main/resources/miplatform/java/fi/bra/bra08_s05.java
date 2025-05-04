
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra08_s05
{
    public bra08_s05()
    {
    }
    //##**$$legal_list
    /* * ���α׷��� : bra08_s05
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/27
    * ���� :
    [�׽�Ʈ ����Ÿ]
    -��������(A), 2006.02
    * ������1: 2010/03/09
    * ������ :
    * �������� : �޴»�� �ּҴ� ������ �����ּҷ� ��µɼ� �ְ� (��â��T)
    */
    public DOBJ CTLlegal_list(DOBJ dobj)
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
            dobj  = CALLlegal_list_SEL1(Conn, dobj);           //  �������������뺸����Ʈ
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
    public DOBJ CTLlegal_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlegal_list_SEL1(Conn, dobj);           //  �������������뺸����Ʈ
        return dobj;
    }
    // �������������뺸����Ʈ
    public DOBJ CALLlegal_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLlegal_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BPAP_DAY  ,  A.UPSO_CD  ,  A.BPAP_GBN  ,  B.UPSO_NM  ,  DECODE(B.PAYPRES_GBN,  'B',  B.MNGEMSTR_NM,  B.PERMMSTR_NM)  AS  RECV_NM  ,  A.START_YRMN  ||  '01'  AS  START_YRMN  ,  A.END_YRMN  ||  '01'  AS  END_YRMN  ,  A.NONPY_AMT  ,  A.TOT_ADDT_AMT  +  A.TOT_EADDT_AMT  AS  TOT_ADDT_AMT  ,  A.TOT_DEMD_AMT  ,  LEGAL_PRNT_YN  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  AS  RECV_ADDR  ,  TO_DATE(A.BPAP_DAY)+  4  AS  DEADLINE  ,  C.BIPLC_NM  AS  BRAN_NM  ,   \n";
        query +=" (SELECT  IPPBX_INPHONE_NUM  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  B.STAFF_CD)  AS  BRAN_PHON  ,   \n";
        query +=" (SELECT  IPPBX_USER_ID  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  B.STAFF_CD)  AS  BRAN_FAX  ,  DECODE(B.BRAN_CD,  'A',  '��������',  'B',  '��������',  'C',  '��������',  'E'  ,  '��������'  ,  'F'  ,  '��������'  ,  'G'  ,  '����'  ,  'H'  ,  '����'  ,  'I'  ,  '����'  ,  'J'  ,  '����'  ,  'K'  ,  '����'  ,  'L'  ,  '��������'  ,  'M'  ,  '����'  ,  'N'  ,  '�λ�����'  ,  'O'  ,  '����')  BANK  ,  DECODE(B.BRAN_CD,  'A'  ,  '695037-01-001228'  ,  'B'  ,  '695037-01-001257'  ,  'C'  ,  '695037-01-001231'  ,  'E'  ,  '695037-01-001260'  ,  'F'  ,  '695037-01-001244'  ,  'G'  ,  '209-01-581021'  ,  'H'  ,  '311-01-155951'  ,  'I'  ,  '311-01-155951'  ,  'J'  ,  '511-01-073417'  ,  'K'  ,  '661-01-033882'  ,  'L'  ,  '632-01-0046-816'  ,  'M'  ,  '815135-51-018283'  ,  'N'  ,  '131-01-000342-2'  ,  'O'  ,  '901017-51-011928')  AS  ACCT_NO  ,  C.ADDR||'  '||C.HNM  AS  BRAN_ADDR  ,  DECODE(B.MAIL_RCPT,  'U',  B.UPSO_NM,  '')  AS  R_UPSO_NM  ,  DECODE(B.MAIL_RCPT,  'U',  B.UPSO_NEW_ZIP,  'B',  B.MNGEMSTR_NEW_ZIP,  'O',  B.PERMMSTR_NEW_ZIP)  AS  POST_NO  ,  DECODE(B.MAIL_RCPT,  'U',  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ,  'B',  B.MNGEMSTR_NEW_ADDR1  ||  DECODE(B.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||B.MNGEMSTR_NEW_ADDR2)  ||  B.MNGEMSTR_REF_INFO    ,  'O',  B.PERMMSTR_NEW_ADDR1  ||  DECODE(B.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||B.PERMMSTR_NEW_ADDR2)  ||  B.PERMMSTR_REF_INFO)  AS  ADDR  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_BIPLC  C  WHERE  A.BPAP_GBN  =  '4'   \n";
        query +=" AND  A.BPAP_DAY  BETWEEN  :YRMN||'01'   \n";
        query +=" AND  :YRMN||'31'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.GIBU(+)  =  B.BRAN_CD  ORDER  BY  B.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$legal_list
    //##**$$legal_delete
    /* * ���α׷��� : bra08_s05
    * �ۼ��� : ������
    * �ۼ��� : 2009/08/28
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLlegal_delete(DOBJ dobj)
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
            dobj  = CALLlegal_delete_DEL1(Conn, dobj);           //  ����
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
    public DOBJ CTLlegal_delete( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlegal_delete_DEL1(Conn, dobj);           //  ����
        return dobj;
    }
    // ����
    public DOBJ CALLlegal_delete_DEL1(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLlegal_delete_DEL1(dobj, dvobj);
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
    private SQLObject SQLlegal_delete_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BPAP_GBN = dvobj.getRecord().get("BPAP_GBN");   //�ְ� ����
        String   BPAP_DAY = dvobj.getRecord().get("BPAP_DAY");   //�ְ� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BPAP_PRNT_HISTY  \n";
        query +=" where BPAP_DAY=:BPAP_DAY  \n";
        query +=" and BPAP_GBN=:BPAP_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BPAP_GBN", BPAP_GBN);               //�ְ� ����
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        return sobj;
    }
    //##**$$legal_delete
    //##**$$legal_insert
    /* * ���α׷��� : bra08_s05
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/28
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLlegal_insert(DOBJ dobj)
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
            dobj  = CALLlegal_insert_MIUD2(Conn, dobj);           //  �ο�ó��
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
    public DOBJ CTLlegal_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlegal_insert_MIUD2(Conn, dobj);           //  �ο�ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �ο�ó��
    public DOBJ CALLlegal_insert_MIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MIUD2");
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLlegal_insert_SEL8(Conn, dobj);           //  �ߺ�üũ
                if( dobj.getRetObject("SEL8").getRecord().getDouble("CNT") == 0)
                {
                    dobj  = CALLlegal_insert_INS1(Conn, dobj);           //  �����������
                }
            }
        }
        return dobj;
    }
    // �ߺ�üũ
    public DOBJ CALLlegal_insert_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLlegal_insert_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_insert_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BPAP_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //�ְ� ����
        String   BPAP_GBN = dobj.getRetObject("R").getRecord().get("BPAP_GBN");   //�ְ� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  BPAP_DAY  =  :BPAP_DAY   \n";
        query +=" AND  BPAP_GBN  =  :BPAP_GBN ";
        sobj.setSql(query);
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setString("BPAP_GBN", BPAP_GBN);               //�ְ� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����������
    public DOBJ CALLlegal_insert_INS1(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLlegal_insert_INS1(dobj, dvobj);
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
    private SQLObject SQLlegal_insert_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String INS_DATE ="";        //��� �Ͻ�
        String PAY_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");        //���� ����
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //�̳� �ݾ�
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BPAP_GBN = dvobj.getRecord().get("BPAP_GBN");   //�ְ� ����
        double   TOT_DEMD_AMT = dvobj.getRecord().getDouble("TOT_DEMD_AMT");   //�� û�� �ݾ�
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //�� ���� �ݾ�
        String   BPAP_DAY = dvobj.getRecord().get("BPAP_DAY");   //�ְ� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        int   LEGAL_PRNT_YN = 0;   //����������¿���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BPAP_PRNT_HISTY (INS_DATE, INSPRES_ID, BPAP_DAY, LEGAL_PRNT_YN, TOT_ADDT_AMT, TOT_DEMD_AMT, BPAP_GBN, UPSO_CD, END_YRMN, NONPY_AMT, START_YRMN)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BPAP_DAY , :LEGAL_PRNT_YN , :TOT_ADDT_AMT , :TOT_DEMD_AMT , :BPAP_GBN , :UPSO_CD , SUBSTR(:END_YRMN_1,1,6), :NONPY_AMT , SUBSTR(:START_YRMN_1,1,6))";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //�̳� �ݾ�
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BPAP_GBN", BPAP_GBN);               //�ְ� ����
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //�� û�� �ݾ�
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //�� ���� �ݾ�
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setInt("LEGAL_PRNT_YN", LEGAL_PRNT_YN);               //����������¿���
        return sobj;
    }
    //##**$$legal_insert
    //##**$$legal_prnt_insert
    /* * ���α׷��� : bra08_s05
    * �ۼ��� : ������
    * �ۼ��� : 2009/09/28
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLlegal_prnt_insert(DOBJ dobj)
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
            dobj  = CALLlegal_prnt_insert_MPD4(Conn, dobj);           //  �Ǻ�ó��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLlegal_prnt_insert_SEL10(Conn, dobj);           //  �������������뺸����Ʈ
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
    public DOBJ CTLlegal_prnt_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlegal_prnt_insert_MPD4(Conn, dobj);           //  �Ǻ�ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLlegal_prnt_insert_SEL10(Conn, dobj);           //  �������������뺸����Ʈ
        return dobj;
    }
    // �Ǻ�ó��
    public DOBJ CALLlegal_prnt_insert_MPD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD4");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MPD4");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().getInt("LEGAL_PRNT_YN") == 0)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLlegal_prnt_insert_SEL6(Conn, dobj);           //  ��¿���Ȯ��
                if( dobj.getRetObject("SEL6").getRecord().getDouble("CNT") == 1)
                {
                    dobj  = CALLlegal_prnt_insert_UPD9(Conn, dobj);           //  �����������
                    dobj  = CALLlegal_prnt_insert_SEL20(Conn, dobj);           //  �湮����� SEQȹ��
                    dobj  = CALLlegal_prnt_insert_INS18(Conn, dobj);           //  ���ҹ湮��� ���
                    dobj  = CALLlegal_prnt_insert_INS19(Conn, dobj);           //  ���ҹ湮 �޸���
                }
                else
                {
                    dobj  = CALLlegal_prnt_insert_INS1(Conn, dobj);           //  �����������
                    dobj  = CALLlegal_prnt_insert_SEL11(Conn, dobj);           //  �湮����� SEQȹ��
                    dobj  = CALLlegal_prnt_insert_INS10(Conn, dobj);           //  ���ҹ湮��� ���
                    dobj  = CALLlegal_prnt_insert_INS13(Conn, dobj);           //  ���ҹ湮 �޸���
                }
            }
            else
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLlegal_prnt_insert_SEL15(Conn, dobj);           //  �湮��� ���ã��
                dobj  = CALLlegal_prnt_insert_INS14(Conn, dobj);           //  ���ҹ湮 �޸���
            }
        }
        return dobj;
    }
    // ��¿���Ȯ��
    public DOBJ CALLlegal_prnt_insert_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLlegal_prnt_insert_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BPAP_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //�ְ� ����
        String   BPAP_GBN = dobj.getRetObject("R").getRecord().get("BPAP_GBN");   //�ְ� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  BPAP_DAY  =  :BPAP_DAY   \n";
        query +=" AND  BPAP_GBN  =  :BPAP_GBN ";
        sobj.setSql(query);
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setString("BPAP_GBN", BPAP_GBN);               //�ְ� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �湮��� ���ã��
    public DOBJ CALLlegal_prnt_insert_SEL15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL15");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL15");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLlegal_prnt_insert_SEL15(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL15");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_SEL15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //�湮 ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(VISIT_SEQ)  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  'L' ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ҹ湮 �޸���
    public DOBJ CALLlegal_prnt_insert_INS14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS14");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlegal_prnt_insert_INS14(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS14") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_INS14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");        //����� ID
        String INS_DATE ="";        //��� �Ͻ�
        String REMAK ="";        //���
        int  VISIT_NUM = 0;        //�湮 ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_NUM_3 = dobj.getRetObject("SEL15").getRecord().get("VISIT_SEQ");   //�湮 ��ȣ
        String   VISIT_NUM_2 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //�湮 ��ȣ
        String   VISIT_NUM_1 = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //�湮 ��ȣ
        String   JOB_GBN ="L";   //���� ����
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL15").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , :VISIT_DAY , :JOB_GBN , (SELECT NVL(MAX(VISIT_NUM), 0) + 1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = :VISIT_NUM_1 AND UPSO_CD = :VISIT_NUM_2 AND VISIT_SEQ = :VISIT_NUM_3 AND JOB_GBN = 'L'), :UPSO_CD , '����� : ' || to_char(SYSDATE, 'YYYYMMDD'))";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_NUM_3", VISIT_NUM_3);               //�湮 ��ȣ
        sobj.setString("VISIT_NUM_2", VISIT_NUM_2);               //�湮 ��ȣ
        sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //�湮 ��ȣ
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // �����������
    public DOBJ CALLlegal_prnt_insert_UPD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlegal_prnt_insert_UPD9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_UPD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BPAP_GBN = dvobj.getRecord().get("BPAP_GBN");   //�ְ� ����
        String   BPAP_DAY = dvobj.getRecord().get("BPAP_DAY");   //�ְ� ����
        int   LEGAL_PRNT_YN = 1;   //����������¿���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BPAP_PRNT_HISTY  \n";
        query +=" set LEGAL_PRNT_YN=:LEGAL_PRNT_YN  \n";
        query +=" where BPAP_DAY=:BPAP_DAY  \n";
        query +=" and BPAP_GBN=:BPAP_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BPAP_GBN", BPAP_GBN);               //�ְ� ����
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setInt("LEGAL_PRNT_YN", LEGAL_PRNT_YN);               //����������¿���
        return sobj;
    }
    // �湮����� SEQȹ��
    public DOBJ CALLlegal_prnt_insert_SEL20(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL20");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL20");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLlegal_prnt_insert_SEL20(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL20");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_SEL20(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("S").getRecord().get("BPAP_DAY");   //�湮 ����
        String   JOB_GBN ="E";   //���� ����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),  0)  +  1  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ҹ湮��� ���
    public DOBJ CALLlegal_prnt_insert_INS18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS18");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlegal_prnt_insert_INS18(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS18") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_INS18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_TIME ="";        //�湮 �ð�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CONSPRES ="[������] "+dobj.getRetObject("R").getRecord().get("RECV_NM");   //�����
        String   JOB_GBN ="L";   //���� ����
        String   REMAK ="�������������뺸";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL20").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, VISIT_TIME, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, TO_CHAR(SYSDATE, 'HH24MI'), :VISIT_DAY , :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���ҹ湮 �޸���
    public DOBJ CALLlegal_prnt_insert_INS19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS19");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlegal_prnt_insert_INS19(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS19") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_INS19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");        //����� ID
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   JOB_GBN ="L";   //���� ����
        String   REMAK ="�������� : "+dobj.getRetObject("R").getRecord().get("ADD_DATE");   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //�湮 ����
        int   VISIT_NUM = 1;   //�湮 ��ȣ
        int   VISIT_SEQ = dobj.getRetObject("SEL20").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , :VISIT_DAY , :JOB_GBN , :VISIT_NUM , :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //�湮 ��ȣ
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // �����������
    public DOBJ CALLlegal_prnt_insert_INS1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlegal_prnt_insert_INS1(dobj, dvobj);
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
    private SQLObject SQLlegal_prnt_insert_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //������
        String INS_DATE ="";        //��� �Ͻ�
        String START_YRMN ="";        //���۳��
        String   START_YRMN_1 = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //�̳� �ݾ�
        String   END_YRMN_1 = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BPAP_GBN = dvobj.getRecord().get("BPAP_GBN");   //�ְ� ����
        double   TOT_DEMD_AMT = dvobj.getRecord().getDouble("TOT_DEMD_AMT");   //�� û�� �ݾ�
        double   TOT_ADDT_AMT = dvobj.getRecord().getDouble("TOT_ADDT_AMT");   //�� ���� �ݾ�
        String   BPAP_DAY = dvobj.getRecord().get("BPAP_DAY");   //�ְ� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        int   LEGAL_PRNT_YN = 1;   //����������¿���
        String   PAY_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //���� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BPAP_PRNT_HISTY (INS_DATE, INSPRES_ID, BPAP_DAY, LEGAL_PRNT_YN, TOT_ADDT_AMT, TOT_DEMD_AMT, BPAP_GBN, UPSO_CD, PAY_DAY, END_YRMN, NONPY_AMT, START_YRMN)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :BPAP_DAY , :LEGAL_PRNT_YN , :TOT_ADDT_AMT , :TOT_DEMD_AMT , :BPAP_GBN , :UPSO_CD , :PAY_DAY , SUBSTR(:END_YRMN_1,1,6), :NONPY_AMT , SUBSTR(:START_YRMN_1,1,6))";
        sobj.setSql(query);
        sobj.setString("START_YRMN_1", START_YRMN_1);               //���۳��
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //�̳� �ݾ�
        sobj.setString("END_YRMN_1", END_YRMN_1);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BPAP_GBN", BPAP_GBN);               //�ְ� ����
        sobj.setDouble("TOT_DEMD_AMT", TOT_DEMD_AMT);               //�� û�� �ݾ�
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //�� ���� �ݾ�
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setInt("LEGAL_PRNT_YN", LEGAL_PRNT_YN);               //����������¿���
        sobj.setString("PAY_DAY", PAY_DAY);               //���� ����
        return sobj;
    }
    // �湮����� SEQȹ��
    public DOBJ CALLlegal_prnt_insert_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLlegal_prnt_insert_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("S").getRecord().get("BPAP_DAY");   //�湮 ����
        String   JOB_GBN ="E";   //���� ����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),  0)  +  1  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ҹ湮��� ���
    public DOBJ CALLlegal_prnt_insert_INS10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlegal_prnt_insert_INS10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS10") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_INS10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_TIME ="";        //�湮 �ð�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CONSPRES ="[������] "+dobj.getRetObject("R").getRecord().get("RECV_NM");   //�����
        String   JOB_GBN ="L";   //���� ����
        String   REMAK ="�������������뺸";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //�湮 ����
        int   VISIT_SEQ = dobj.getRetObject("SEL11").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, VISIT_TIME, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, TO_CHAR(SYSDATE, 'HH24MI'), :VISIT_DAY , :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���ҹ湮 �޸���
    public DOBJ CALLlegal_prnt_insert_INS13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS13");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlegal_prnt_insert_INS13(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS13") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_INS13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");        //����� ID
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   JOB_GBN ="L";   //���� ����
        String   REMAK ="�������� : "+dobj.getRetObject("R").getRecord().get("ADD_DATE");   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("BPAP_DAY");   //�湮 ����
        int   VISIT_NUM = 1;   //�湮 ��ȣ
        int   VISIT_SEQ = dobj.getRetObject("SEL11").getRecord().getInt("VISIT_SEQ");   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , :VISIT_DAY , :JOB_GBN , :VISIT_NUM , :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("VISIT_DAY", VISIT_DAY);               //�湮 ����
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //�湮 ��ȣ
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // �������������뺸����Ʈ
    public DOBJ CALLlegal_prnt_insert_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLlegal_prnt_insert_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_prnt_insert_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BPAP_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  DECODE(B.PAYPRES_GBN,  'B',  B.MNGEMSTR_NM,  B.PERMMSTR_NM)  RECV_NM  ,  A.START_YRMN||'01'  START_YRMN  ,  A.END_YRMN||'01'  END_YRMN  ,  A.NONPY_AMT  ,  A.TOT_ADDT_AMT  +  A.TOT_EADDT_AMT  TOT_ADDT_AMT  ,  A.TOT_DEMD_AMT  ,  LEGAL_PRNT_YN  ,  DECODE(B.MAIL_RCPT,  'U',  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ,  'B',  B.MNGEMSTR_NEW_ADDR1  ||  DECODE(B.MNGEMSTR_NEW_ADDR2,  '',  '',  ',  '||B.MNGEMSTR_NEW_ADDR2)  ||  B.MNGEMSTR_REF_INFO    ,  B.PERMMSTR_NEW_ADDR1  ||  DECODE(B.PERMMSTR_NEW_ADDR2,  '',  '',  ',  '||B.PERMMSTR_NEW_ADDR2)  ||  B.PERMMSTR_REF_INFO  )  RECV_ADDR  ,  TO_DATE(A.BPAP_DAY)+  4  PRNT_DAY  ,  C.BIPLC_NM  BRAN_NM  ,  C.PHON_NUM  BRAN_PHON  ,  C.FAX_NUM  BRAN_FAX  ,  DECODE(B.BRAN_CD,  'A',  '��������','B',  '��������','C',  '��������',  'E'  ,  '��������'  ,  'F'  ,  '��������'  ,  'G'  ,  '��������'  ,  'H'  ,  '����'  ,  'I'  ,  '����'  ,  'J'  ,  '����'  ,  'K'  ,  '����'  ,  'L'  ,  '��������'  ,  'M'  ,  '����'  ,  'N'  ,  '�λ�����'  ,  'O'  ,  '����')  BANK  ,  DECODE(B.BRAN_CD,  'A'  ,  '695037-01-001228'  ,  'B'  ,  '695037-01-001257'  ,  'C'  ,  '695037-01-001231'  ,  'E'  ,  '695037-01-001260'  ,  'F'  ,  '695037-01-001244'  ,  'G'  ,  '100-022-587700'  ,  'H'  ,  '311-01-155951'  ,  'I'  ,  '311-01-155951'  ,  'J'  ,  '511-01-073417'  ,  'K'  ,  '661-01-033882'  ,  'L'  ,  '632-01-0046-816'  ,  'M'  ,  '815135-51-018283'  ,  'N'  ,  '131-01-000342-2'  ,  'O'  ,  '901017-51-011928')  ACCT_NO  ,  C.ADDR||'  '||C.HNM  BRAN_ADDR  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_BIPLC  C  WHERE  A.BPAP_GBN  =  '4'   \n";
        query +=" AND  A.BPAP_DAY  BETWEEN  :YRMN||'01'   \n";
        query +=" AND  :YRMN||'31'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.GIBU(+)  =  B.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$legal_prnt_insert
    //##**$$legal_add_init
    /* * ���α׷��� : bra08_s05
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/24
    * ���� :
    * ������1: 2010/03/09
    * ������ :
    * �������� : �޴»�� �ּҴ� ������ �����ּҷ� ��µɼ� �ְ� (��â��T)
    */
    public DOBJ CTLlegal_add_init(DOBJ dobj)
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
            dobj  = CALLlegal_add_init_SEL1(Conn, dobj);           //  �������Ȯ��
            if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 1000)
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="����Ⱦ����Դϴ�.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
            }
            else
            {
                dobj  = CALLlegal_add_init_SEL7(Conn, dobj);           //  û���Ⱓ���ϱ�
                dobj  = CALLlegal_add_init_OSP1(Conn, dobj);           //  û�� �ݾ� ����
                dobj  = CALLlegal_add_init_SEL4(Conn, dobj);           //  �߰�����������Ÿ
                if( dobj.getRetObject("SEL4").getRecord().getDouble("NONPY_AMT") == 0)
                {
                    dobj.setRtncode(9);
                    if(dobj.getRtncode() == 9)
                    {
                        String message ="�̳����� ���� �����Դϴ�.";
                        dobj.setRetmsg(message);
                        Conn.rollback();
                        return dobj;
                    }
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
    public DOBJ CTLlegal_add_init( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlegal_add_init_SEL1(Conn, dobj);           //  �������Ȯ��
        if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 1000)
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="����Ⱦ����Դϴ�.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        else
        {
            dobj  = CALLlegal_add_init_SEL7(Conn, dobj);           //  û���Ⱓ���ϱ�
            dobj  = CALLlegal_add_init_OSP1(Conn, dobj);           //  û�� �ݾ� ����
            dobj  = CALLlegal_add_init_SEL4(Conn, dobj);           //  �߰�����������Ÿ
            if( dobj.getRetObject("SEL4").getRecord().getDouble("NONPY_AMT") == 0)
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="�̳����� ���� �����Դϴ�.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
            }
        }
        return dobj;
    }
    // �������Ȯ��
    public DOBJ CALLlegal_add_init_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLlegal_add_init_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_add_init_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  CLSBS_YRMN  IS  NOT  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // û���Ⱓ���ϱ�
    public DOBJ CALLlegal_add_init_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLlegal_add_init_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_add_init_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GIBU.FT_GET_START_REPT_YRMN(:UPSO_CD,6)  START_YRMN  ,  DECODE(CLSBS_YRMN,  NULL,  TO_CHAR(SYSDATE,  'YYYYMM'),  CLSBS_YRMN)  END_YRMN  ,  UPSO_CD  ,  'O'  AS  CRET_GBN  ,  ''  AS  RECV_DAY  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD=  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // û�� �ݾ� ����
    // �ش� ������ û�� �ݾ׸� �����ϱ� ���� ���ν��� (GIBU.SP_GET_USE_AMT) �� ȣ���Ѵ�
    public DOBJ CALLlegal_add_init_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL7");         //û���Ⱓ���ϱ⿡�� ������Ų OBJECT�Դϴ�.(CALLlegal_add_init_SEL7)
        String[]  incolumns ={"UPSO_CD","START_YRMN","END_YRMN","CRET_GBN","RECV_DAY"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_GET_DEMD_AMT";
        int[]    intypes={12, 12, 12, 12, 12};;
        String[] outcolnms={"P_BSTYP_CD","P_UPSO_GRAD","P_MONPRNCFEE","P_DEMD_GBN","P_DEMD_MMCNT","P_TUSE_AMT","P_TADDT_AMT","P_TEADDT_AMT","P_DSCT_AMT","P_TDEMD_AMT"};;
        int[]    outtypes ={12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP1");
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �߰�����������Ÿ
    public DOBJ CALLlegal_add_init_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLlegal_add_init_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.setRetcode(1);
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlegal_add_init_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BPAP_DAY = dobj.getRetObject("S").getRecord().get("BPAP_DAY");   //�ְ� ����
        double   TOT_ADDT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("P_TADDT_AMT");   //�� ���� �ݾ�
        double   USE_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("P_TUSE_AMT");   //��� �ݾ�
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   END_YRMN = dobj.getRetObject("SEL7").getRecord().get("END_YRMN");   //������
        double   TOT_EADDT_AMT = dobj.getRetObject("OSP1").getRecord().getDouble("P_TEADDT_AMT");   //�� �߰��� �ݾ�
        String   START_YRMN = dobj.getRetObject("SEL7").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :BPAP_DAY  AS  BPAP_DAY  ,  A.UPSO_CD  ,  A.UPSO_NM  ,  DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_NM,  A.PERMMSTR_NM)  RECV_NM  ,  :START_YRMN  AS  START_YRMN  ,  :END_YRMN  AS  END_YRMN  ,  :USE_AMT  AS  NONPY_AMT  ,  (:TOT_ADDT_AMT  +  :TOT_EADDT_AMT)  AS  TOT_ADDT_AMT  ,  (:USE_AMT  +  :TOT_ADDT_AMT  +  :TOT_EADDT_AMT)  AS  TOT_DEMD_AMT  ,  0  AS  LEGAL_PRNT_YN  ,  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  RECV_ADDR  ,  TO_DATE(:BPAP_DAY)  +  4  AS  DEADLINE  ,  B.BIPLC_NM  BRAN_NM  ,  B.PHON_NUM  BRAN_PHON  ,  B.FAX_NUM  BRAN_FAX  ,  DECODE(A.BRAN_CD,  'A',  '��������','B',  '��������','C',  '��������',  'E'  ,  '��������'  ,  'F'  ,  '��������'  ,  'G'  ,  '��������'  ,  'H'  ,  '����'  ,  'I'  ,  '��ü��'  ,  'J'  ,  '����'  ,  'K'  ,  '����'  ,  'L'  ,  '��������'  ,  'M'  ,  '����'  ,  'N'  ,  '�λ�����'  ,  'O'  ,  '����')  BANK  ,  DECODE(A.BRAN_CD,  'A'  ,  '695037-01-001228'  ,  'B'  ,  '695037-01-001257'  ,  'C'  ,  '695037-01-001231'  ,  'E'  ,  '695037-01-001260'  ,  'F'  ,  '695037-01-001244'  ,  'G'  ,  '100-022-587700'  ,  'H'  ,  '311-01-155951'  ,  'I'  ,  '310037-01-003561'  ,  'J'  ,  '511-01-073417'  ,  'K'  ,  '661-01-033882'  ,  'L'  ,  '632-01-0046-816'  ,  'M'  ,  '815135-51-018283'  ,  'N'  ,  '131-01-000342-2'  ,  'O'  ,  '901017-51-011928')  ACCT_NO  ,  B.ADDR||'  '||B.HNM  BRAN_ADDR  FROM  GIBU.TBRA_UPSO  A  ,  INSA.TCPM_BIPLC  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NVL(A.PAPER_CANC_YN,  '  ')  <>  'Y'   \n";
        query +=" AND  B.GIBU(+)  =  A.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("BPAP_DAY", BPAP_DAY);               //�ְ� ����
        sobj.setDouble("TOT_ADDT_AMT", TOT_ADDT_AMT);               //�� ���� �ݾ�
        sobj.setDouble("USE_AMT", USE_AMT);               //��� �ݾ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setDouble("TOT_EADDT_AMT", TOT_EADDT_AMT);               //�� �߰��� �ݾ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    //##**$$legal_add_init
    //##**$$end
}
