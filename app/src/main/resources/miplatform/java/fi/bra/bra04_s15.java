
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s15
{
    public bra04_s15()
    {
    }
    //##**$$upso_rept_ack_ins
    /* * ���α׷��� : bra04_s15
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/19
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_rept_ack_ins(DOBJ dobj)
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
            dobj  = CALLupso_rept_ack_ins_INS1(Conn, dobj);           //  �����������
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
    public DOBJ CTLupso_rept_ack_ins( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_rept_ack_ins_INS1(Conn, dobj);           //  �����������
        return dobj;
    }
    // �����������
    public DOBJ CALLupso_rept_ack_ins_INS1(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLupso_rept_ack_ins_INS1(dobj, dvobj);
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
    private SQLObject SQLupso_rept_ack_ins_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //���۳��
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   ISS_NUM = dvobj.getRecord().get("ISS_NUM");   //���� ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   ISS_AMT_HANNM = dvobj.getRecord().get("ISS_AMT_HANNM");   //���� �ݾ� �ѱ۸�
        String   ISS_DAY = dvobj.getRecord().get("ISS_DAY");   //���� ����
        double   ISS_AMT = dvobj.getRecord().getDouble("ISS_AMT");   //���� �ݾ�
        String   ISS_YEAR = dvobj.getRecord().get("ISS_YEAR");   //���� �⵵
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_ACK_ISS (INS_DATE, ISS_YEAR, ISS_AMT, INSPRES_ID, ISS_DAY, ISS_AMT_HANNM, UPSO_CD, ISS_NUM, END_YRMN, BRAN_CD, START_YRMN)  \n";
        query +=" values(SYSDATE, :ISS_YEAR , :ISS_AMT , :INSPRES_ID , :ISS_DAY , :ISS_AMT_HANNM , :UPSO_CD , :ISS_NUM , :END_YRMN , :BRAN_CD , :START_YRMN )";
        sobj.setSql(query);
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("ISS_NUM", ISS_NUM);               //���� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("ISS_AMT_HANNM", ISS_AMT_HANNM);               //���� �ݾ� �ѱ۸�
        sobj.setString("ISS_DAY", ISS_DAY);               //���� ����
        sobj.setDouble("ISS_AMT", ISS_AMT);               //���� �ݾ�
        sobj.setString("ISS_YEAR", ISS_YEAR);               //���� �⵵
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    //##**$$upso_rept_ack_ins
    //##**$$upso_rept_ack
    /* * ���α׷��� : bra04_s15
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/01
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLupso_rept_ack(DOBJ dobj)
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
            dobj  = CALLupso_rept_ack_SEL4(Conn, dobj);           //  �����ȣ����
            dobj  = CALLupso_rept_ack_SEL1(Conn, dobj);           //  �Ա�������ȸ
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
    public DOBJ CTLupso_rept_ack( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_rept_ack_SEL4(Conn, dobj);           //  �����ȣ����
        dobj  = CALLupso_rept_ack_SEL1(Conn, dobj);           //  �Ա�������ȸ
        return dobj;
    }
    // �����ȣ����
    public DOBJ CALLupso_rept_ack_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_rept_ack_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_rept_ack_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(ISS_NUM),0)+1,4,'0')  ISS_NUM  FROM  GIBU.TBRA_REPT_ACK_ISS  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  ISS_YEAR  =  TO_CHAR(SYSDATE,'YYYY') ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �Ա�������ȸ
    // ��ȸ�ϰ��� �ϴ� ������ �Ⱓ�� ���� �Ա� ���� �� ���� ������ �����´�
    public DOBJ CALLupso_rept_ack_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_rept_ack_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_rept_ack_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ISS_DAY ="";        //���� ����
        String ISS_YEAR ="";        //���� �⵵
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   ISS_NUM = dobj.getRetObject("SEL4").getRecord().get("ISS_NUM");   //���� ��ȣ
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_NM  ,  A.UPSO_CD  ,  A.UPSO_NM||'('||A.UPSO_CD||')'  UPSO_FULL_NM  ,  DECODE(A.PAYPRES_GBN,  'B',  A.MNGEMSTR_NM,  'O',  A.PERMMSTR_NM)  MNGEMSTR_NM  ,  A.BIOWN_NUM  UPSO_BIOWN_NUM  ,  B.REPT_AMT-  NVL(E.RETURN_AMT,0)  REPT_AMT  ,  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  UPSO_ADDR  ,  A.UPSO_NEW_ZIP  ,  C.BIPLC_NM  BRAN_NM  ,  C.PHON_NUM  ,  C.FAX_NUM  ,  C.POST_NUM  ,  C.ADDR  ||'  '||C.HNM  BRAN_ADDR  ,  D.BIOWN_NUM  ,  D.BIPLC_NM  ,  :ISS_NUM  AS  ISS_NUM  ,  TO_CHAR(SYSDATE,'YYYY')  AS  ISS_YEAR  ,  TO_CHAR(SYSDATE,  'YYYYMMDD')  AS  ISS_DAY  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  SUM(REPT_AMT)  REPT_AMT  ,  UPSO_CD  FROM(   \n";
        query +=" SELECT  REPT_AMT  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  RECV_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  B.DISTR_AMT  ,  B.UPSO_CD  FROM  GIBU.TBRA_REPT  A  ,  GIBU.TBRA_REPT_UPSO_DISTR  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.DISTR_GBN  IS  NOT  NULL   \n";
        query +=" AND  A.RECV_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  B.REPT_GBN   \n";
        query +=" AND  A.DISTR_GBN  =  B.DISTR_GBN  )  GROUP  BY  UPSO_CD  )  B  ,  INSA.TCPM_BIPLC  C  ,  INSA.TCPM_BIPLC  D  ,  (   \n";
        query +=" SELECT  SUM(NVL(RETURN_AMT,0))  RETURN_AMT  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_RETURN  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  RETURN_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'  GROUP  BY  UPSO_CD  )  E  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  D.BIPLC_CD  =  '100'   \n";
        query +=" AND  E.UPSO_CD(+)  =  A.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("ISS_NUM", ISS_NUM);               //���� ��ȣ
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    //##**$$upso_rept_ack
    //##**$$upso_rept_iss_dup
    /*
    */
    public DOBJ CTLupso_rept_iss_dup(DOBJ dobj)
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
            dobj  = CALLupso_rept_iss_dup_SEL1(Conn, dobj);           //  �ߺ�Ȯ��
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
    public DOBJ CTLupso_rept_iss_dup( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_rept_iss_dup_SEL1(Conn, dobj);           //  �ߺ�Ȯ��
        return dobj;
    }
    // �ߺ�Ȯ��
    // ��������, ���� �Ⱓ�� ���� �Ա� Ȯ���� �ߺ����� ��û�Ǿ����� Ȯ��
    public DOBJ CALLupso_rept_iss_dup_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_rept_iss_dup_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_rept_iss_dup_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_REPT_ACK_ISS  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  START_YRMN  =  :START_YRMN   \n";
        query +=" AND  END_YRMN  =  :END_YRMN ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    //##**$$upso_rept_iss_dup
    //##**$$iss_list_search
    /*
    */
    public DOBJ CTLiss_list_search(DOBJ dobj)
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
            dobj  = CALLiss_list_search_SEL1(Conn, dobj);           //  ������ ��ȸ
            dobj  = CALLiss_list_search_SEL2(Conn, dobj);           //  ADD_ROW�� �⺻�� ��ȸ
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
    public DOBJ CTLiss_list_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLiss_list_search_SEL1(Conn, dobj);           //  ������ ��ȸ
        dobj  = CALLiss_list_search_SEL2(Conn, dobj);           //  ADD_ROW�� �⺻�� ��ȸ
        return dobj;
    }
    // ������ ��ȸ
    public DOBJ CALLiss_list_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLiss_list_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLiss_list_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,ISS_NUM  ,UPSO_CD  ,   \n";
        query +=" (SELECT  UPSO_NM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD)  AS  UPSO_NM  ,START_YRMN  ,END_YRMN  ,ISS_AMT  ,TO_CHAR(INS_DATE,  'YYYYMMDD')  INS_DATE  ,REQUEST_GBN  ,USE_GBN  FROM  GIBU.TBRA_REPT_ACK_ISS  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD  ORDER  BY  ISS_YEAR,ISS_NUM ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ADD_ROW�� �⺻�� ��ȸ
    public DOBJ CALLiss_list_search_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLiss_list_search_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLiss_list_search_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD  ,UPSO_CD  ,UPSO_NM  ,PAYPRES_GBN  AS  REQUEST_GBN  ,'1'  AS  USE_GBN  ,TO_CHAR(SYSDATE,  'YYYYMMDD')  AS  INS_DATE  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$iss_list_search
    //##**$$iss_list_save
    /*
    */
    public DOBJ CTLiss_list_save(DOBJ dobj)
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
            dobj  = CALLiss_list_save_MIUD2(Conn, dobj);           //  �б�
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
    public DOBJ CTLiss_list_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLiss_list_save_MIUD2(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLiss_list_save_MIUD2(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLiss_list_save_SEL1(Conn, dobj);           //  ISS_NUM ���
                dobj  = CALLiss_list_save_SEL2(Conn, dobj);           //  �Աݱݾ� ���
                dobj  = CALLiss_list_save_INS8(Conn, dobj);           //  ���
            }
        }
        return dobj;
    }
    // ISS_NUM ���
    public DOBJ CALLiss_list_save_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLiss_list_save_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLiss_list_save_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(ISS_NUM),0)+1,4,'0')  ISS_NUM  FROM  GIBU.TBRA_REPT_ACK_ISS  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  ISS_YEAR  =  TO_CHAR(SYSDATE,'YYYY') ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �Աݱݾ� ���
    public DOBJ CALLiss_list_save_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLiss_list_save_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLiss_list_save_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //������
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(B.REPT_AMT,0)-  NVL(E.RETURN_AMT,0)  AS  REPT_AMT  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  SUM(REPT_AMT)  REPT_AMT  ,  UPSO_CD  FROM(   \n";
        query +=" SELECT  REPT_AMT  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  RECV_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  B.DISTR_AMT  ,  B.UPSO_CD  FROM  GIBU.TBRA_REPT  A  ,  GIBU.TBRA_REPT_UPSO_DISTR  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.DISTR_GBN  IS  NOT  NULL   \n";
        query +=" AND  A.RECV_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  B.REPT_GBN   \n";
        query +=" AND  A.DISTR_GBN  =  B.DISTR_GBN  )  GROUP  BY  UPSO_CD  )  B  ,  INSA.TCPM_BIPLC  C  ,  INSA.TCPM_BIPLC  D  ,  (   \n";
        query +=" SELECT  SUM(NVL(RETURN_AMT,0))  RETURN_AMT  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_RETURN  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  RETURN_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'  GROUP  BY  UPSO_CD  )  E  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  D.BIPLC_CD  =  '100'   \n";
        query +=" AND  E.UPSO_CD(+)  =  A.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // ���
    public DOBJ CALLiss_list_save_INS8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS8");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLiss_list_save_INS8(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS8") ;
        rvobj.Println("INS8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLiss_list_save_INS8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");        //����� ID
        String INS_DATE ="";        //��� �Ͻ�
        String ISS_DAY ="";        //���� ����
        String ISS_YEAR ="";        //���� �⵵
        String   START_YRMN = dvobj.getRecord().get("START_YRMN");   //���۳��
        String   REQUEST_GBN = dvobj.getRecord().get("REQUEST_GBN");   //��û��
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_YRMN = dvobj.getRecord().get("END_YRMN");   //������
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   USE_GBN = dvobj.getRecord().get("USE_GBN");   //��� ����
        double   ISS_AMT = dobj.getRetObject("SEL2").getRecord().getDouble("REPT_AMT");   //���� �ݾ�
        String   ISS_NUM = dobj.getRetObject("SEL1").getRecord().get("ISS_NUM");   //���� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_ACK_ISS (INS_DATE, ISS_YEAR, ISS_AMT, USE_GBN, ISS_DAY, UPSO_CD, ISS_NUM, END_YRMN, BRAN_CD, REQUEST_GBN, START_YRMN)  \n";
        query +=" values(sysdate, TO_CHAR(SYSDATE,'YYYY'), :ISS_AMT , :USE_GBN , TO_CHAR(SYSDATE,'YYYYMMDD'), :UPSO_CD , :ISS_NUM , :END_YRMN , :BRAN_CD , :REQUEST_GBN , :START_YRMN )";
        sobj.setSql(query);
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("REQUEST_GBN", REQUEST_GBN);               //��û��
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("USE_GBN", USE_GBN);               //��� ����
        sobj.setDouble("ISS_AMT", ISS_AMT);               //���� �ݾ�
        sobj.setString("ISS_NUM", ISS_NUM);               //���� ��ȣ
        return sobj;
    }
    //##**$$iss_list_save
    //##**$$end
}
