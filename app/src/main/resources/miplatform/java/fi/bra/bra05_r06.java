
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_r06
{
    public bra05_r06()
    {
    }
    //##**$$visit_bpap_list
    /* * ���α׷��� : bra05_r06
    * �ۼ��� : ������
    * �ۼ��� : 2009/08/20
    * ���� :
    �ְ� ��½� �ʿ��� ������ ��ȸ�Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLvisit_bpap_list(DOBJ dobj)
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
            dobj  = CALLvisit_bpap_list_SEL1(Conn, dobj);           //  �ְ��������
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
    public DOBJ CTLvisit_bpap_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLvisit_bpap_list_SEL1(Conn, dobj);           //  �ְ��������
        return dobj;
    }
    // �ְ��������
    public DOBJ CALLvisit_bpap_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLvisit_bpap_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLvisit_bpap_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.BIPLC_NM  AS  BRAN_NM  ,  B.POST_NUM  AS  BRAN_ZIP  ,  B.ADDR  ||  '  '||  B.HNM  AS  BRAN_ADDR  ,   \n";
        query +=" (SELECT  IPPBX_INPHONE_NUM  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  A.STAFF_CD)  AS  BRAN_TEL  ,   \n";
        query +=" (SELECT  IPPBX_USER_ID  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  A.STAFF_CD)  AS  BRAN_FAX  ,  A.MNGEMSTR_NM  ,  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  AS  ADDR  ,  A.UPSO_CD  ,  A.UPSO_NM  ,  A.UPSO_NEW_ZIP  AS  UPSO_ZIP  ,  A.UPSO_NEW_ZIP  AS  UPSO_NEW_ZIP  FROM  GIBU.TBRA_UPSO  A  ,  INSA.TCPM_BIPLC  B  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.GIBU(+)  =  A.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$visit_bpap_list
    //##**$$visit_insert
    /* * ���α׷��� : bra05_r06
    * �ۼ��� : ������
    * �ۼ��� : 2010/02/16
    * ���� :
    ����� �ְ� ��½� ���ҹ湮 ��Ͽ� ����Ѵ�.
    �űԾ����߰�: 2010/02/16
    ���� :�ְ�(C)
    �������: �������
    ����: "������ְ� ����"
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLvisit_insert(DOBJ dobj)
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
            dobj  = CALLvisit_insert_MPD3(Conn, dobj);           //  ��Ƽó��
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
    public DOBJ CTLvisit_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLvisit_insert_MPD3(Conn, dobj);           //  ��Ƽó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ��Ƽó��
    public DOBJ CALLvisit_insert_MPD3(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLvisit_insert_SEL5(Conn, dobj);           //  VISIT_SEQ���ϱ�
                dobj  = CALLvisit_insert_INS1(Conn, dobj);           //  ���ҹ湮�̷µ��
            }
        }
        return dobj;
    }
    // VISIT_SEQ���ϱ�
    public DOBJ CALLvisit_insert_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLvisit_insert_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLvisit_insert_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
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
    public DOBJ CALLvisit_insert_INS1(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLvisit_insert_INS1(dobj, dvobj);
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
    private SQLObject SQLvisit_insert_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_TIME ="";        //�湮 �ð�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CONSPRES ="[������] "+dobj.getRetObject("R").getRecord().get("MNGEMSTR_NM");   //�����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="C";   //���� ����
        String   REMAK ="������ְ� ����";   //���
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("DISP_DAY");   //�湮 ����
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
    //##**$$visit_insert
    //##**$$end
}
