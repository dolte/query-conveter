
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra09_p11
{
    public bra09_p11()
    {
    }
    //##**$$onoff_col_list
    /* * ���α׷��� : bra09_p11
    * �ۼ��� : ������
    * �ۼ��� : 2009/12/01
    * ����    :  UI ���� ����ڰ� ��Ȳ�� ������ ��� ����Ǿ� �ִ� ��Ȳ ������ ��ȸ�ϰ� ����Ǿ� ���� ���� ���
    ��������, �ܶ�����, �뷡��, �������, ���ӹ��� ¡���ݾ� (�Ա� ���� = TBRA_REPT) �� �¶���/�������κ��� �����Ͽ� ��Ȳ�� �����Ѵ�.
    ���� �� �¶��� ��谡 1�� �̻� �ִ� ��� �¶��� ���ҷ� �� �ܴ� ��� �������� ���ҷ� ó���Ѵ�.
    Input
    YRMN (���)
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLonoff_col_list(DOBJ dobj)
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
            dobj  = CALLonoff_col_list_SEL3(Conn, dobj);           //  ����ȵ���Ÿ����üũ
            if( dobj.getRetObject("SEL3").getRecord().get("GBN").equals("2"))
            {
                dobj  = CALLonoff_col_list_SEL5(Conn, dobj);           //  ����ȵ���Ÿ��ȸ
                dobj  = CALLonoff_col_list_SEL1( dobj);        //  �������
            }
            else
            {
                dobj  = CALLonoff_col_list_SEL2(Conn, dobj);           //  �¿�������Ÿ¡���ݾ�
                dobj  = CALLonoff_col_list_SEL1( dobj);        //  �������
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
    public DOBJ CTLonoff_col_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLonoff_col_list_SEL3(Conn, dobj);           //  ����ȵ���Ÿ����üũ
        if( dobj.getRetObject("SEL3").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLonoff_col_list_SEL5(Conn, dobj);           //  ����ȵ���Ÿ��ȸ
            dobj  = CALLonoff_col_list_SEL1( dobj);        //  �������
        }
        else
        {
            dobj  = CALLonoff_col_list_SEL2(Conn, dobj);           //  �¿�������Ÿ¡���ݾ�
            dobj  = CALLonoff_col_list_SEL1( dobj);        //  �������
        }
        return dobj;
    }
    // ����ȵ���Ÿ����üũ
    public DOBJ CALLonoff_col_list_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLonoff_col_list_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_col_list_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :GBN  AS  GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("GBN", GBN);               //����
        return sobj;
    }
    // ����ȵ���Ÿ��ȸ
    public DOBJ CALLonoff_col_list_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLonoff_col_list_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_col_list_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_NM  ,  BRAN_CD  ,  BSTYP_CD  ,  GRADNM  ,  ONOFF_GBN  ,  SUM(REPT_AMT)  AS  REPT_AMT  ,  SUM(REPT_AMT)  -  (SUM(KOSCAP_AMT)  +  SUM(FKMP_AMT)  +  SUM(RIAK_AMT))  AS  KOMCA_AMT  ,  SUM(KOSCAP_AMT)  AS  KOSCAP_AMT  ,  SUM(FKMP_AMT)  AS  FKMP_AMT  ,  SUM(RIAK_AMT)  AS  RIAK_AMT  FROM  (   \n";
        query +=" SELECT  B.DEPT_NM  AS  BRAN_NM  ,  A.BRAN_CD  ,  A.BSTYP_CD  ,  C.GRADNM  ,  DECODE(A.ONOFF_GBN,  'O',  '�¶���',  '��������')  AS  ONOFF_GBN  ,  DECODE(A.ASS_GBN,  'K',  A.LEVY_AMT,  TO_NUMBER(NULL))  AS  REPT_AMT  ,  DECODE(A.ASS_GBN,  'T0000001',  A.LEVY_AMT,  0)  AS  KOSCAP_AMT  ,  DECODE(A.ASS_GBN,  'T0000002',  A.LEVY_AMT,  0)  AS  FKMP_AMT  ,  DECODE(A.ASS_GBN,  'T0000003',  A.LEVY_AMT,  0)  AS  RIAK_AMT  FROM  GIBU.TBRA_ONOFF_LEVY_PRCON  A  ,  INSA.TCPM_DEPT  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.PRCON_YRMN  =  :YRMN   \n";
        query +=" AND  B.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  C.BSTYP_CD  =  'z'   \n";
        query +=" AND  C.GRAD_GBN  =  A.BSTYP_CD   \n";
        query +=" AND  A.ONOFF_GBN  IS  NOT  NULL  )  GROUP  BY  BRAN_NM,  BRAN_CD,  BSTYP_CD,  GRADNM,  ONOFF_GBN  ORDER  BY  BRAN_CD,  BSTYP_CD,  ONOFF_GBN  DESC ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        return sobj;
    }
    // �������
    public DOBJ CALLonoff_col_list_SEL1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("SEL1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL2, SEL5","");
        rvobj.setName("SEL1") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // �¿�������Ÿ¡���ݾ�
    // ����� ����Ÿ�� �ƴ� ��������� ¡���ݾ� ��Ȳ�� ��ȸ�Ѵ�.
    public DOBJ CALLonoff_col_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLonoff_col_list_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_col_list_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TC.DEPT_NM  AS  BRAN_NM  ,  TB.BRAN_CD  ,  TB.BSTYP_CD  ,  TB.GRADNM  ,  DECODE(TB.ONOFF_GBN,  'ON',  '�¶���','��������')  AS  ONOFF_GBN  ,  DECODE(TA.BRAN_CD,  NULL,  SUM(TB.REPT_AMT),  SUM(TA.REPT_AMT))  AS  REPT_AMT  ,  DECODE(TA.BRAN_CD,  NULL,  SUM(TB.REPT_AMT),  SUM(TA.REPT_AMT))  -  (NVL(SUM(TA.KOSCAP_AMT),  0)  +  NVL(SUM(TA.FKMP_AMT),  0)  +  NVL(SUM(TA.RIAK_AMT),  0))  AS  KOMCA_AMT  ,  NVL(SUM(TA.KOSCAP_AMT),  0)  AS  KOSCAP_AMT  ,  NVL(SUM(TA.FKMP_AMT),  0)  AS  FKMP_AMT  ,  NVL(SUM(TA.RIAK_AMT),  0)  AS  RIAK_AMT  FROM  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.BSTYP_CD  ,  XA.GRADNM  ,  XA.ONOFF_GBN  ,  XB.REPT_AMT  ,  XB.KOSCAP_AMT  ,  XB.FKMP_AMT  ,  XB.RIAK_AMT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.BRAN_CD  ,  B.BSTYP_CD  ,  D.GRADNM  ,  (CASE  B.BSTYP_CD  WHEN  'o'  THEN  DECODE(A.ONOFF_GBN,  'O',  'ON',  'F',  'OFF')  WHEN  'k'  THEN  DECODE(A.ONOFF_GBN,  'O',  'ON',  'F',  'OFF')  WHEN  'l'  THEN  DECODE(A.ONOFF_GBN,  'O',  'ON',  'F',  'OFF')  WHEN  'p'  THEN  DECODE(A.ONOFF_GBN,  'O',  'ON',  'F',  'OFF')  WHEN  'y'  THEN  DECODE(A.ONOFF_GBN,  'O',  'ON',  'F',  'OFF')  WHEN  'm'  THEN  'OFF'  WHEN  'n'  THEN  'OFF'  WHEN  'f'  THEN  'OFF'  ELSE  'OFF'  END)  AS  ONOFF_GBN  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_UPSORTAL_INFO  B  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  AS  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  GROUP  BY  A.UPSO_CD  )  C  ,  GIBU.TBRA_BSTYPGRAD  D  WHERE  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  D.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  D.GRAD_GBN  =  B.UPSO_GRAD   \n";
        query +=" AND  B.BSTYP_CD  IN  ('k','l','o','p','y','m','n','J','f')  )  XA  ,  (   \n";
        query +=" SELECT  REPT_DAY  ,  UPSO_CD  ,  REPT_AMT  -  COMIS  AS  REPT_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  FKMP_AMT  ,  0  AS  RIAK_AMT  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  UPSO_CD  ,  DISTR_AMT  AS  REPT_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  FKMP_AMT  ,  0  AS  RIAK_AMT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  REPT_GBN  <>  '08'  UNION  ALL   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.UPSO_CD  ,  (CASE  WHEN  MIN(DISTR_SEQ)  OVER(PARTITION  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN)  =  DISTR_SEQ  THEN  A.DISTR_AMT  -  NVL(C.COMIS,  0)  ELSE  A.DISTR_AMT  END)  AS  REPT_AMT  ,  0  ,  0  ,  0  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_REPT_VIRTUAL  B  ,  GIBU.TBRA_REPT  C  WHERE  A.MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  '08'   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  C.REPT_GBN(+)  =  '08'  UNION  ALL   \n";
        query +=" SELECT  RETURN_DAY  ,  UPSO_CD  ,  (RETURN_AMT*  -1)  AS  REPT_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  FKMP_AMT  ,  0  AS  RIAK_AMT  FROM  GIBU.TBRA_REPT_RETURN  WHERE  RETURN_DAY  LIKE  :YRMN||'%'  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  UPSO_CD  ,  0  AS  REPT_AMT  ,  PROC_AMT  AS  KOSCAP_AMT  ,  0  AS  FKMP_AMT  ,  0  AS  RIAK_AMT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000001'  UNION  ALL   \n";
        query +=" SELECT  RETURN_DAY  ,  UPSO_CD  ,  0  AS  REPT_AMT  ,  (PROC_AMT  *  -1)  AS  KOSCAP_AMT  ,  0  AS  FKMP_AMT  ,  0  AS  RIAK_AMT  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000001'  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  UPSO_CD  ,  0  AS  REPT_AMT  ,  0  AS  KOSCAP_AMT  ,  PROC_AMT  AS  FKMP_AMT  ,  0  AS  RIAK_AMT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000002'  UNION  ALL   \n";
        query +=" SELECT  RETURN_DAY  ,  UPSO_CD  ,  0  AS  REPT_AMT  ,  0  AS  KOSCAP_AMT  ,  (PROC_AMT  *  -1)  AS  FKMP_AMT  ,  0  AS  RIAK_AMT  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000002'  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  UPSO_CD  ,  0  AS  REPT_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  FKMP_AMT  ,  PROC_AMT  AS  RIAK_AMT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000003'  UNION  ALL   \n";
        query +=" SELECT  RETURN_DAY  ,  UPSO_CD  ,  0  AS  REPT_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  FKMP_AMT  ,  (PROC_AMT  *  -1)  AS  RIAK_AMT  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000003'  )  XB  WHERE  XA.UPSO_CD  =  XB.UPSO_CD  )  TA  ,  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XB.GRAD_GBN  AS  BSTYP_CD  ,  XB.GRADNM  ,  XC.ONOFF_GBN  ,  0  AS  REPT_AMT  FROM  (   \n";
        query +=" SELECT  GIBU  AS  BRAN_CD  FROM  INSA.TCPM_DEPT  WHERE  GIBU  IS  NOT  NULL   \n";
        query +=" AND  GIBU  <>  'AL'  )  XA  ,  (   \n";
        query +=" SELECT  GRAD_GBN  ,  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  IN  ('k','l','o','p','y','m','n','J','f')  )  XB  ,  (   \n";
        query +=" SELECT  'ON'  AS  ONOFF_GBN  FROM  DUAL  )  XC  UNION  ALL   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XB.GRAD_GBN  AS  BSTYP_CD  ,  XB.GRADNM  ,  XC.ONOFF_GBN  ,  0  AS  REPT_AMT  FROM  (   \n";
        query +=" SELECT  GIBU  AS  BRAN_CD  FROM  INSA.TCPM_DEPT  WHERE  GIBU  IS  NOT  NULL   \n";
        query +=" AND  GIBU  <>  'AL'  )  XA  ,  (   \n";
        query +=" SELECT  GRAD_GBN  ,  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  IN  ('k','l','o','p','y','m','n','J','f')  )  XB  ,  (   \n";
        query +=" SELECT  'OFF'  AS  ONOFF_GBN  FROM  DUAL  )  XC  )  TB  ,  INSA.TCPM_DEPT  TC  WHERE  TB.BRAN_CD  =  TA.BRAN_CD(+)   \n";
        query +=" AND  TB.BSTYP_CD  =  TA.BSTYP_CD(+)   \n";
        query +=" AND  TB.ONOFF_GBN  =  TA.ONOFF_GBN(+)   \n";
        query +=" AND  TC.GIBU  =  TB.BRAN_CD  GROUP  BY  TB.BRAN_CD,  TB.BSTYP_CD,  TB.ONOFF_GBN,  TA.BRAN_CD,  TC.DEPT_NM,  TB.GRADNM  ORDER  BY  TB.BRAN_CD,  TB.BSTYP_CD  ASC,  TB.ONOFF_GBN  DESC ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        return sobj;
    }
    //##**$$onoff_col_list
    //##**$$onoff_history
    /*
    */
    public DOBJ CTLonoff_history(DOBJ dobj)
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
            dobj  = CALLonoff_history_SEL1(Conn, dobj);           //  �̷� ��ȸ
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
    public DOBJ CTLonoff_history( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLonoff_history_SEL1(Conn, dobj);           //  �̷� ��ȸ
        return dobj;
    }
    // �̷� ��ȸ
    public DOBJ CALLonoff_history_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLonoff_history_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_history_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" select  PRCON_YRMN,  INSPRES_ID,  INS_DATE  from  GIBU.TBRA_ONOFF_LEVY_PRCON_HISTORY  order  by  prcon_yrmn  desc,  ins_date  desc ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$onoff_history
    //##**$$onoff_col_save
    /* * ���α׷��� : �¿�������Ÿ¡���ݾ���Ȳ
    * �ۼ��� : ������
    * �ۼ��� : 2009/10/20
    * ����    : ��������, �ܶ�����, �뷡��, �������, ���ӹ��� ¡���ݾ��� �¶��� �������� ���Һ��� �����Ѵ�.
    * ������ :
    * ������ :
    * �������� :
    */
    public DOBJ CTLonoff_col_save(DOBJ dobj)
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
            dobj  = CALLonoff_col_save_SEL6(Conn, dobj);           //  ����ȵ���Ÿ����üũ
            if( dobj.getRetObject("SEL6").getRecord().getDouble("CNT") > 0)
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="�̹� ����Ÿ�� ����Ǿ����ϴ�. �����Ҽ� �����ϴ�.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
            }
            else
            {
                dobj  = CALLonoff_col_save_INS7(Conn, dobj);           //  �̷µ��
                dobj  = CALLonoff_col_save_SEL1(Conn, dobj);           //  �¿�������Ÿ¡���ݾ���Ȳ
                dobj  = CALLonoff_col_save_INS2(Conn, dobj);           //  ����
                dobj  = CALLonoff_col_save_OSP8(Conn, dobj);           //  �й����� �ش���ǥ ����
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
    public DOBJ CTLonoff_col_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLonoff_col_save_SEL6(Conn, dobj);           //  ����ȵ���Ÿ����üũ
        if( dobj.getRetObject("SEL6").getRecord().getDouble("CNT") > 0)
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="�̹� ����Ÿ�� ����Ǿ����ϴ�. �����Ҽ� �����ϴ�.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        else
        {
            dobj  = CALLonoff_col_save_INS7(Conn, dobj);           //  �̷µ��
            dobj  = CALLonoff_col_save_SEL1(Conn, dobj);           //  �¿�������Ÿ¡���ݾ���Ȳ
            dobj  = CALLonoff_col_save_INS2(Conn, dobj);           //  ����
            dobj  = CALLonoff_col_save_OSP8(Conn, dobj);           //  �й����� �ش���ǥ ����
        }
        return dobj;
    }
    // ����ȵ���Ÿ����üũ
    public DOBJ CALLonoff_col_save_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLonoff_col_save_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_col_save_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_ONOFF_LEVY_PRCON  WHERE  PRCON_YRMN  =  :YRMN ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        return sobj;
    }
    // �̷µ��
    public DOBJ CALLonoff_col_save_INS7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("INS7");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLonoff_col_save_INS7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS7") ;
        rvobj.Println("INS7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_col_save_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        double SEQ = 0;        //������ȣ
        String   SEQ_1 = dobj.getRetObject("S").getRecord().get("YRMN");   //������ȣ
        String   INSPRES_ID = dobj.getRetObject("S").getRecord().get("INSPRES_ID");   //����� ID
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_ONOFF_LEVY_PRCON_HISTORY (INS_DATE, INSPRES_ID, PRCON_YRMN, SEQ)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :PRCON_YRMN , (SELECT NVL(MAX(SEQ), 0) + 1 SEQ FROM GIBU.TBRA_ONOFF_LEVY_PRCON_HISTORY WHERE PRCON_YRMN = :SEQ_1))";
        sobj.setSql(query);
        sobj.setString("SEQ_1", SEQ_1);               //������ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    // �¿�������Ÿ¡���ݾ���Ȳ
    public DOBJ CALLonoff_col_save_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLonoff_col_save_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_col_save_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_NM  ,  BRAN_CD  ,  GRAD_GBN  BSTYP_CD  ,  GRADNM  ,  ONOFF_GBN  ,  SUM(REPT_AMT)  AS  REPT_AMT  ,  DECODE(ASS_GBN,  NULL,  'K',  ASS_GBN)  AS  ASS_GBN  FROM  (   \n";
        query +=" SELECT  ZA.BRAN_NM  ,  ZA.BRAN_CD  ,  ZA.GRAD_GBN  ,  ZA.GRADNM  ,  DECODE(ZA.ONOFF_GBN,  '-',  NULL,  ZA.ONOFF_GBN)  ONOFF_GBN  ,  NVL(ZB.REPT_AMT,  0)  REPT_AMT  ,  ZA.SRT  ,  ASS_GBN  FROM  (   \n";
        query +=" SELECT  XB.BRAN_CD  ,  XB.BRAN_NM  ,  XA.GRAD_GBN  ,  XA.GRADNM  ,  XA.SRT  ,  DECODE(XA.ONOFF_GBN,  '1',  '-',  XA.ONOFF_GBN)  ONOFF_GBN  FROM  (   \n";
        query +=" SELECT  A.GRAD_GBN  ,  A.GRADNM  ,  (CASE  A.GRAD_GBN  WHEN  'k'  THEN  DECODE(B.SRT,  '1',  1,  2)  WHEN  'l'  THEN  DECODE(B.SRT,  '1',  3,  4)  WHEN  'o'  THEN  DECODE(B.SRT,  '1',  5,  6)  WHEN  'p'  THEN  DECODE(B.SRT,  '1',  7,  8)  WHEN  'y'  THEN  DECODE(B.SRT,  '1',  9,  10)  WHEN  'n'  THEN  DECODE(B.SRT,  '1',11,  12)  WHEN  'm'  THEN  DECODE(B.SRT,  '1',13,  14)  WHEN  'f'  THEN  DECODE(B.SRT,  '1',15,  16)  ELSE  17  END)  AS  SRT  ,  (CASE  WHEN  A.GRAD_GBN  IN  ('o',  'k',  'l',  'p',  'y',  'm',  'n',  'J',  'f')  THEN  DECODE  (B.SRT,  '1',  'O',  'F')  ELSE  DECODE  (B.SRT,  '1',  '1',  NULL)  END)  AS  ONOFF_GBN  FROM  GIBU.TBRA_BSTYPGRAD  A  ,  (   \n";
        query +=" SELECT  '1'  SRT  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '2'  SRT  FROM  DUAL  )  B  WHERE  BSTYP_CD  =  'z'  )  XA  ,  (   \n";
        query +=" SELECT  GIBU  AS  BRAN_CD  ,  DEPT_NM  AS  BRAN_NM  FROM  INSA.TCPM_DEPT  WHERE  GIBU  IN  (   \n";
        query +=" SELECT  DISTINCT  GIBU  FROM  INSA.TCPM_DEPT  WHERE  GIBU  <>  'AL'  )  )  XB  WHERE  XA.ONOFF_GBN  IS  NOT  NULL  ORDER  BY  BRAN_CD,  SRT  )  ZA  ,  (   \n";
        query +=" SELECT  TA.BRAN_CD  ,  TA.BSTYP_CD  ,  TA.ONOFF_GBN  ,  SUM(TA.REPT_AMT)  AS  REPT_AMT  ,  TA.ASS_GBN  FROM  (   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.BRAN_CD  ,  XB.BSTYP_CD  ,  (CASE  WHEN  XB.BSTYP_CD  IN  ('o',  'k',  'l',  'p',  'y')  THEN  XA.ONOFF_GBN  WHEN  XB.BSTYP_CD  IN  ('m',  'n',  'J','f')  THEN  'F'  ELSE  '-'  END)  AS  ONOFF_GBN  ,  XA.REPT_AMT  ,  XA.ASS_GBN  FROM  (   \n";
        query +=" SELECT  A.BRAN_CD  ,  A.UPSO_CD  ,  A.ONOFF_GBN  ,  B.REPT_DAY  ,  B.REPT_AMT  ,  B.ASS_GBN  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  AA.REPT_DAY  ,  AA.UPSO_CD  ,  AA.REPT_AMT  -  AA.COMIS  AS  REPT_AMT  ,  'K'  AS  ASS_GBN  FROM  GIBU.TBRA_REPT  AA  ,  GIBU.TBRA_UPSO  AB  WHERE  AA.MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  AA.DISTR_GBN  IS  NULL   \n";
        query +=" AND  AB.UPSO_CD  =  AA.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  AA.REPT_DAY  ,  AA.UPSO_CD  ,  AA.DISTR_AMT  AS  REPT_AMT  ,  'K'  AS  ASS_GBN  FROM  GIBU.TBRA_REPT_UPSO_DISTR  AA  ,  GIBU.TBRA_REPT  AB  ,  GIBU.TBRA_UPSO  AC  WHERE  AA.MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  AB.REPT_DAY  =  AA.REPT_DAY   \n";
        query +=" AND  AB.REPT_NUM  =  AA.REPT_NUM   \n";
        query +=" AND  AB.REPT_GBN  =  AA.REPT_GBN   \n";
        query +=" AND  AC.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  AA.REPT_GBN  <>  '08'  UNION  ALL   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.UPSO_CD  ,  (CASE  WHEN  MIN(DISTR_SEQ)  OVER(PARTITION  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN)  =  DISTR_SEQ  THEN  A.DISTR_AMT  -  NVL(C.COMIS,  0)  ELSE  A.DISTR_AMT  END)  AS  REPT_AMT  ,  'K'  AS  ASS_GBN  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_REPT_VIRTUAL  B  ,  GIBU.TBRA_REPT  C  WHERE  A.MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  '08'   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  C.REPT_GBN(+)  =  '08'  UNION  ALL   \n";
        query +=" SELECT  RETURN_DAY  ,  UPSO_CD  ,  (RETURN_AMT*  -1)  AS  REPT_AMT  ,  'K'  AS  ASS_GBN  FROM  GIBU.TBRA_REPT_RETURN  WHERE  RETURN_DAY  LIKE  :YRMN||'%'  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  UPSO_CD  ,  PROC_AMT  AS  REPT_AMT  ,  BSCON_CD  AS  ASS_GBN  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000001'  UNION  ALL   \n";
        query +=" SELECT  RETURN_DAY  ,  UPSO_CD  ,  (PROC_AMT  *  -1)  AS  REPT_AMT  ,  BSCON_CD  AS  ASS_GBN  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000001'  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  UPSO_CD  ,  PROC_AMT  AS  REPT_AMT  ,  BSCON_CD  AS  ASS_GBN  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000002'  UNION  ALL   \n";
        query +=" SELECT  RETURN_DAY  ,  UPSO_CD  ,  (PROC_AMT  *  -1)  AS  REPT_AMT  ,  BSCON_CD  AS  ASS_GBN  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000002'  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  UPSO_CD  ,  PROC_AMT  AS  REPT_AM  ,  BSCON_CD  AS  ASS_GBN  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000003'  UNION  ALL   \n";
        query +=" SELECT  RETURN_DAY  ,  UPSO_CD  ,  (PROC_AMT  *  -1)  AS  REPT_AMT  ,  BSCON_CD  AS  ASS_GBN  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000003'  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  )  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.BSTYP_CD  ,  A.UPSO_GRAD  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  GROUP  BY  UPSO_CD  )  B  WHERE  A.JOIN_SEQ  =  B.JOIN_SEQ  )  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  WHERE  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XC.BSTYP_CD  =  XB.BSTYP_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XB.UPSO_GRAD  )  TA  GROUP  BY  TA.BRAN_CD,  TA.BSTYP_CD,  TA.ONOFF_GBN,  TA.ASS_GBN  )  ZB  WHERE  ZA.BRAN_CD  =  ZB.BRAN_CD  (+)   \n";
        query +=" AND  ZA.GRAD_GBN  =  ZB.BSTYP_CD  (+)   \n";
        query +=" AND  ZA.ONOFF_GBN  =  ZB.ONOFF_GBN  (+)  )  GROUP  BY  BRAN_NM,  BRAN_CD,  GRAD_GBN,  GRADNM,  ONOFF_GBN  ,  ASS_GBN  ORDER  BY  BRAN_CD,  BSTYP_CD,  ONOFF_GBN,  ASS_GBN ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        return sobj;
    }
    // ����
    // ��Ȳ������ ��ȸ������� ������ ������ �ٲ�� ��Ȳ����Ÿ�� �ٲ�����Ƿ� ������� ���� ����Ÿ�� �� 1ȸ ������ �д�.
    public DOBJ CALLonoff_col_save_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("SEL1");           //�¿�������Ÿ¡���ݾ���Ȳ���� ������Ų OBJECT�Դϴ�.(CALLonoff_col_save_SEL1)
        dvobj.Println("INS2");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLonoff_col_save_INS2(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS2") ;
        rvobj.Println("INS2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_col_save_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        double SEQ = 0;        //������ȣ
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   SEQ_1 = dobj.getRetObject("S").getRecord().get("YRMN");   //������ȣ
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        String   ASS_GBN = dvobj.getRecord().get("ASS_GBN");   //��ü����(�ڵ�)
        String   ONOFF_GBN = dvobj.getRecord().get("ONOFF_GBN");   //�¿��� ����
        String   INSPRES_ID = dobj.getRetObject("S").getRecord().get("INSPRES_ID");   //����� ID
        double   LEVY_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //¡�� �ݾ�
        String   PRCON_YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //��Ȳ ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_ONOFF_LEVY_PRCON (INS_DATE, INSPRES_ID, ONOFF_GBN, ASS_GBN, LEVY_AMT, PRCON_YRMN, BSTYP_CD, SEQ, BRAN_CD)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :ONOFF_GBN , :ASS_GBN , :LEVY_AMT , :PRCON_YRMN , :BSTYP_CD , (SELECT NVL(MAX(SEQ), 0) + 1 SEQ FROM GIBU.TBRA_ONOFF_LEVY_PRCON WHERE PRCON_YRMN = :SEQ_1), :BRAN_CD )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("SEQ_1", SEQ_1);               //������ȣ
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setString("ASS_GBN", ASS_GBN);               //��ü����(�ڵ�)
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //�¿��� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("LEVY_AMT", LEVY_AMT);               //¡�� �ݾ�
        sobj.setString("PRCON_YRMN", PRCON_YRMN);               //��Ȳ ���
        return sobj;
    }
    // �й����� �ش���ǥ ����
    // �й����� ����,�ܶ�, �뷡�� ��ǥ �����Ͽ� ����
    public DOBJ CALLonoff_col_save_OSP8(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP8");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"YRMN","INSPRES_ID"};
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
        String   spname ="GIBU.P_INTERFACE_YUDANNO";
        int[]    intypes={12, 12};;
        String[] outcolnms={"TEMP1","TEMP2","TEMP3","TEMP4","TEMP5"};;
        int[]    outtypes ={12, 12, 12, 12, 12};;
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
        robj.setName("OSP8");
        dobj.setRetObject(robj);
        String message =  dobj.getRetObject("OSP8").getRecord().get("TEMP1");
        dobj.setRetmsg(message);
        return dobj;
    }
    //##**$$onoff_col_save
    //##**$$end
}
