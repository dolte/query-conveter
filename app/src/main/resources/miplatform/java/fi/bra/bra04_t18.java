
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_t18
{
    public bra04_t18()
    {
    }
    //##**$$adj_satn_search
    /* SATN1�� OPT���� Y�̸� ���� ����Ǿ��ִ����� ���� Y/N, OPT���� N�̸� ������ִ� ���� �����Ѵ�.
    */
    public DOBJ CTLadj_satn_search(DOBJ dobj)
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
            dobj  = CALLadj_satn_search_SEL2(Conn, dobj);           //  �μ��ڵ�� ��å�ڵ� ȹ��
            dobj  = CALLadj_satn_search_SEL1(Conn, dobj);           //  �����ݾ���ȸ
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
    public DOBJ CTLadj_satn_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLadj_satn_search_SEL2(Conn, dobj);           //  �μ��ڵ�� ��å�ڵ� ȹ��
        dobj  = CALLadj_satn_search_SEL1(Conn, dobj);           //  �����ݾ���ȸ
        return dobj;
    }
    // �μ��ڵ�� ��å�ڵ� ȹ��
    public DOBJ CALLadj_satn_search_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLadj_satn_search_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLadj_satn_search_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_NUM = dobj.getRetObject("S").getRecord().get("STAFF_NUM");   //�����ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  HAN_NM  ,  DEPT_CD  ,  JOB_CD  FROM  INSA.TINS_MST01  WHERE  RETIRE_DAY  IS  NULL   \n";
        query +=" AND  STAFF_NUM  =  :STAFF_NUM ";
        sobj.setSql(query);
        sobj.setString("STAFF_NUM", STAFF_NUM);               //�����ȣ
        return sobj;
    }
    // �����ݾ���ȸ
    public DOBJ CALLadj_satn_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLadj_satn_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLadj_satn_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEPT_CD = dobj.getRetObject("SEL2").getRecord().get("DEPT_CD");   //�μ� �ڵ�
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        String   JOB_CD = dobj.getRetObject("SEL2").getRecord().get("JOB_CD");   //�����ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NONPY_DAY  ,  SEQ  ,  UPSO_CD  ,  UPSO_NM  ,  ADJ_AMT  ,  ADJ_GBN  ,  ADJ_GBN_NM  ,  CASE  WHEN  (SATN1_OPT  =  'N'   \n";
        query +=" AND  (SATN1  IS  NULL   \n";
        query +=" OR  SATN1  =  ''))  THEN  '-'  ELSE  SATN1_VIEW  END  AS  SATN1_VIEW  ,  CASE  WHEN  (SATN2_OPT  =  'N'   \n";
        query +=" AND  (SATN2  IS  NULL   \n";
        query +=" OR  SATN2  =  ''))  THEN  '-'  ELSE  SATN2_VIEW  END  AS  SATN2_VIEW  ,  CASE  WHEN  (SATN3_OPT  =  'N'   \n";
        query +=" AND  (SATN3  IS  NULL   \n";
        query +=" OR  SATN3  =  ''))  THEN  '-'  ELSE  SATN3_VIEW  END  AS  SATN3_VIEW  ,  SATN1  ,  SATN2  ,  SATN3  ,  INS_DATE  ,  INSPRES_NM  ,  SATN1_OPT  ,  SATN2_OPT  ,  SATN3_OPT  FROM  (   \n";
        query +=" SELECT  A.NONPY_DAY  ,  A.SEQ  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.ADJ_AMT  ,  A.ADJ_GBN  ,  FIDU.GET_CODE_NM  ('00412',  A.ADJ_GBN)  AS  ADJ_GBN_NM  ,  DECODE(:JOB_CD,  null,  FIDU.GET_STAFF_NM  (A.SATN1)  ,  C.SATN1,  DECODE(A.SATN1,  null,  '0',  '1')  ,  FIDU.GET_STAFF_NM  (A.SATN1))  AS  SATN1_VIEW  ,  DECODE(:JOB_CD,  null,  FIDU.GET_STAFF_NM  (A.SATN2)  ,  C.SATN2,  DECODE(A.SATN2,  null,  '0',  '1')  ,  FIDU.GET_STAFF_NM  (A.SATN2))  AS  SATN2_VIEW  ,  DECODE(:DEPT_CD,  null,  FIDU.GET_STAFF_NM  (A.SATN3)  ,  C.SATN3,  DECODE(A.SATN3,  null,  '0',  '1')  ,  FIDU.GET_STAFF_NM  (A.SATN3))  AS  SATN3_VIEW  ,  DECODE(:JOB_CD,  null,  'N',  C.SATN1,  'Y',  'N')  AS  SATN1_OPT  ,  DECODE(:JOB_CD,  null,  'N',  C.SATN2,  'Y',  'N')  AS  SATN2_OPT  ,  DECODE(:DEPT_CD,  null,  'N',  C.SATN3,  'Y',  'N')  AS  SATN3_OPT  ,  A.SATN1  ,  A.SATN2  ,  A.SATN3  ,  TO_CHAR(A.INS_DATE,  'YYYY-MM-DD')  AS  INS_DATE  ,  FIDU.GET_STAFF_NM  (A.INSPRES_ID)  AS  INSPRES_NM  FROM  GIBU.TBRA_MISU_ADJ  A  ,  GIBU.TBRA_UPSO  B  ,  GIBU.TBRA_MISU_ADJ_SATN  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  C.BRAN_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.ADJ_GBN  =  C.ADJ_GBN   \n";
        query +=" AND  A.ADJ_AMT  <>  0   \n";
        query +=" AND  A.NONPY_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  ) ";
        sobj.setSql(query);
        sobj.setString("DEPT_CD", DEPT_CD);               //�μ� �ڵ�
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("JOB_CD", JOB_CD);               //�����ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    //##**$$adj_satn_search
    //##**$$save_satn
    /*
    */
    public DOBJ CTLsave_satn(DOBJ dobj)
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
            dobj  = CALLsave_satn_MIUD9(Conn, dobj);           //  �б�
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
    public DOBJ CTLsave_satn( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_satn_MIUD9(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLsave_satn_MIUD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD9");
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
                dobj  = CALLsave_satn_XIUD7(Conn, dobj);           //  ����
            }
        }
        return dobj;
    }
    // ����
    public DOBJ CALLsave_satn_XIUD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD7");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD7");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_satn_XIUD7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD7");
        rvobj.Println("XIUD7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_satn_XIUD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NONPY_DAY = dobj.getRetObject("R").getRecord().get("NONPY_DAY");   //�̳�����
        String   SATN1 = dobj.getRetObject("R").getRecord().get("SATN1");   //�繫�����
        String   SATN1_VIEW = dobj.getRetObject("R").getRecord().get("SATN1_VIEW");   //�繫�����
        String   SATN2 = dobj.getRetObject("R").getRecord().get("SATN2");   //���������
        String   SATN2_VIEW = dobj.getRetObject("R").getRecord().get("SATN2_VIEW");   //���������
        String   SATN3 = dobj.getRetObject("R").getRecord().get("SATN3");   //���ΰ���
        String   SATN3_VIEW = dobj.getRetObject("R").getRecord().get("SATN3_VIEW");   //�������
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_MISU_ADJ  \n";
        query +=" SET SATN1 = DECODE(:SATN1_VIEW, '0', '', '1', :SATN1, SATN1) , SATN2 = DECODE(:SATN2_VIEW, '0', '', '1', :SATN2, SATN2) , SATN3 = DECODE(:SATN3_VIEW, '0', '', '1', :SATN3, SATN3)  \n";
        query +=" WHERE NONPY_DAY = :NONPY_DAY  \n";
        query +=" AND UPSO_CD = :UPSO_CD  \n";
        query +=" AND SEQ = :SEQ";
        sobj.setSql(query);
        sobj.setString("NONPY_DAY", NONPY_DAY);               //�̳�����
        sobj.setString("SATN1", SATN1);               //�繫�����
        sobj.setString("SATN1_VIEW", SATN1_VIEW);               //�繫�����
        sobj.setString("SATN2", SATN2);               //���������
        sobj.setString("SATN2_VIEW", SATN2_VIEW);               //���������
        sobj.setString("SATN3", SATN3);               //���ΰ���
        sobj.setString("SATN3_VIEW", SATN3_VIEW);               //�������
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$save_satn
    //##**$$end
}
