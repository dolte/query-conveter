
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s01
{
    public bra01_s01()
    {
    }
    //##**$$onoff_check_list
    /* * ���α׷��� : bra01_s01
    * �ۼ��� : ������
    * �ۼ��� : 2010/04/12
    * ���� : �������� ���κ� 5������('o','k','l','y','p')�߿��� �¿��� ������ ���� ���� ī��Ʈ �� ����Ʈ
    GBN:1 - �¿��������� ���� ������ ����
    GNB:2 - �¿��������� ���� ���� �󼼸���Ʈ
    * ������1: 2010.06.08
    * ������ :
    * �������� : ����� �����ε� ���߿� �Ա��� ������ ��� �¿��� ������ �ִ��� üũ�Ѵ�.
    */
    public DOBJ CTLonoff_check_list(DOBJ dobj)
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
            dobj  = CALLonoff_check_list_SEL1(Conn, dobj);           //  ��������
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj  = CALLonoff_check_list_SEL2(Conn, dobj);           //  �¿���üũ����Ʈ
                dobj  = CALLonoff_check_list_SEL5(Conn, dobj);           //  �űԾ����� ��� �湮��� �Է¿���
            }
            else
            {
                dobj  = CALLonoff_check_list_SEL3(Conn, dobj);           //  �¿�������
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
    public DOBJ CTLonoff_check_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLonoff_check_list_SEL1(Conn, dobj);           //  ��������
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLonoff_check_list_SEL2(Conn, dobj);           //  �¿���üũ����Ʈ
            dobj  = CALLonoff_check_list_SEL5(Conn, dobj);           //  �űԾ����� ��� �湮��� �Է¿���
        }
        else
        {
            dobj  = CALLonoff_check_list_SEL3(Conn, dobj);           //  �¿�������
        }
        return dobj;
    }
    // ��������
    public DOBJ CALLonoff_check_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLonoff_check_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_check_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :  GBN  AS  GBN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("GBN", GBN);               //����
        return sobj;
    }
    // �¿���üũ����Ʈ
    // ���κ� 5�� ���� ('o','k','l','y','p') �� ���ؼ� �¿��� ������ ���� ����Ʈ�� ��ȸ�Ѵ�
    public DOBJ CALLonoff_check_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLonoff_check_list_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_check_list_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  optimizer_features_enable('11.1.0.6')  */  TA.BRAN_CD  ,  TC.DEPT_NM  BRAN_NM  ,  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.BSTYP_CD  ,  NVL((SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =  TA.BSTYP_CD  ),  TA.BSTYP_CD)  GRADNM  ,  TA.MCHNDAESU  ,  TA.ONOFF_GBN  ,  TA.INS_DATE  ,  NVL((SELECT  HAN_NM  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  TA.STAFF_CD),  TA.STAFF_CD)  STAFF_NM  ,  DECODE(TA.CLSBS_YRMN,  NULL,  '',  TA.CLSBS_YRMN||'01')  CLSBS_YRMN  FROM  (   \n";
        query +=" SELECT  XB.BRAN_CD  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  XA.BSTYP_CD  ,  XA.MCHNDAESU  ,  XB.ONOFF_GBN  ,  XB.INS_DATE  ,  XB.STAFF_CD  ,  XB.CLSBS_YRMN  FROM(   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MCHNDAESU,  ZB.BSTYP_CD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  B.UPSO_STAT='1'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZB.BSTYP_CD  IN  ('o','k','l','y','p')  )  XA  ,  GIBU.TBRA_UPSO  XB  WHERE  XB.UPSO_CD  =  XA.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XB.BSTYP_CD  ,  XA.MCHNDAESU  ,  XA.ONOFF_GBN  ,  XA.INS_DATE  ,  XA.STAFF_CD  ,  XA.CLSBS_YRMN  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD  ,  B.UPSO_CD  ,  B.UPSO_NM  ,  B.MCHNDAESU  ,  B.ONOFF_GBN  ,  B.INS_DATE  ,  B.STAFF_CD  ,  B.CLSBS_YRMN  FROM  GIBU.TBRA_REPT  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.MAPPING_DAY  >=  TO_CHAR(ADD_MONTHS(SYSDATE,-3),'YYYYMM')  ||  '00'   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  B.BRAN_CD  ,  B.UPSO_CD  ,  B.UPSO_NM  ,  B.MCHNDAESU  ,  B.ONOFF_GBN  ,  B.INS_DATE  ,  B.STAFF_CD  ,  B.CLSBS_YRMN  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.MAPPING_DAY  >=  TO_CHAR(ADD_MONTHS(SYSDATE,-3),'YYYYMM')  ||  '00'   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  )  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MCHNDAESU,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NOT  NULL  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZB.BSTYP_CD  IN  ('o','k','l','y','p')  )  XB  WHERE  XB.UPSO_CD  =  XA.UPSO_CD  )  TA  ,  INSA.TCPM_DEPT  TC  WHERE  TC.GIBU  =  TA.BRAN_CD  ORDER  BY  TA.BRAN_CD,  TA.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �űԾ����� ��� �湮��� �Է¿���
    public DOBJ CALLonoff_check_list_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLonoff_check_list_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_check_list_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DATE1 = dobj.getRetObject("S").getRecord().get("DATE1");   //DATE1
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD,  XA.UPSO_NM,  XA.STAFF_CD,  XC.HAN_NM  STAFF_NM  FROM  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  OPBI_DAY  IS  NOT  NULL   \n";
        query +=" AND  INS_DATE  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  IS  NULL   \n";
        query +=" AND  to_char(SYSDATE,  'YYYYMM')  BETWEEN  TO_CHAR(INS_DATE,  'YYYYMM')   \n";
        query +=" AND  to_char(add_months(ins_date,3),  'YYYYMM')  MINUS  (   \n";
        query +=" SELECT  A.USER_ID  AS  UPSO_CD  FROM  KOMSMS.SDK_SMS_REPORT  A  WHERE  A.SEND_DATE  LIKE  substr(:DATE1,1,6)  ||  '%'  UNION   \n";
        query +=" SELECT  A.UPSO_CD  FROM  GIBU.TBRA_UPSO_VISIT  A  WHERE  A.VISIT_DAY  LIKE  substr(:DATE1,1,6)  ||  '%'  ))XB,  GIBU.TBRA_UPSO  XA,  INSA.TINS_MST01  XC  WHERE  XA.UPSO_CD  =  XB.UPSO_CD   \n";
        query +=" AND  XA.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  XA.BRAN_CD,  'AL',  XA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  XC.STAFF_NUM(+)  =  XA.STAFF_CD  ORDER  BY  XA.BRAN_CD,  XA.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("DATE1", DATE1);               //DATE1
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �¿�������
    // �¿��������� ���� ���Ҹ���Ʈ�� ī��Ʈ�� ��ȸ�Ѵ�
    public DOBJ CALLonoff_check_list_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLonoff_check_list_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_check_list_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  optimizer_features_enable('11.1.0.6')  */  SUM(CNT)  AS  CNT  FROM  (   \n";
        query +=" SELECT  COUNT(XA.UPSO_CD)  AS  CNT  FROM  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  AS  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  B.UPSO_STAT  =  '1'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZB.BSTYP_CD  IN  ('o',  'k',  'l',  'y',  'p')  )  XA  ,  GIBU.TBRA_UPSO  XB  WHERE  XB.UPSO_CD  =  XA.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  COUNT(XA.UPSO_CD)  AS  CNT  FROM  (   \n";
        query +=" SELECT  B.UPSO_CD  FROM  GIBU.TBRA_REPT  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.MAPPING_DAY  >=  '20100500'   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  B.UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_UPSO  B  WHERE  SUBSTR(A.MAPPING_DAY,  0,  6)  >=  '201005'   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD)  XA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.BSTYP_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  AS  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.ONOFF_GBN  IS  NULL   \n";
        query +=" AND  B.CLSBS_YRMN  IS  NOT  NULL  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZB.BSTYP_CD  IN  ('o',  'k',  'l',  'y',  'p')  )  XB  WHERE  XB.UPSO_CD  =  XA.UPSO_CD) ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$onoff_check_list
    //##**$$norebang_info
    /* * ���α׷��� : bra01_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/07/17
    * ���� : �ش������ �뷡�� �� ������ ��ȸ�Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLnorebang_info(DOBJ dobj)
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
            dobj  = CALLnorebang_info_SEL1(Conn, dobj);           //  �뷡�������
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
    public DOBJ CTLnorebang_info( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLnorebang_info_SEL1(Conn, dobj);           //  �뷡�������
        return dobj;
    }
    // �뷡�������
    public DOBJ CALLnorebang_info_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLnorebang_info_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnorebang_info_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(A.BSTYP_CD)  ||  A.GRAD_GBN  GRAD  ,  A.AREA  ,  A.MCHNDAESU  ,  A.STNDAMT  ,  B.GRADNM  ,  A.CHG_NUM  ,  C.CHG_DAY  ,  C.CHG_BRAN  FROM  GIBU.TBRA_NOREBANG_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  C.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  A.GRAD_GBN  =  B.GRAD_GBN ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$norebang_info
    //##**$$check_chgowner
    /* * ���α׷��� : bra01_s01
    * �ۼ��� : ������
    * �ۼ��� : 2009/07/17
    * ���� :  �ش� �����ڵ�� ���ֺ����� �Ǿ����� ���θ� üũ�Ѵ�.
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLcheck_chgowner(DOBJ dobj)
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
            dobj  = CALLcheck_chgowner_SEL1(Conn, dobj);           //  ���� ���� ���� üũ
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
    public DOBJ CTLcheck_chgowner( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcheck_chgowner_SEL1(Conn, dobj);           //  ���� ���� ���� üũ
        return dobj;
    }
    // ���� ���� ���� üũ
    // ���� ���ֺ��� ���� �� �ڵ���ü�������� Ȯ��
    public DOBJ CALLcheck_chgowner_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLcheck_chgowner_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcheck_chgowner_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  UPSO_NM  ,  0  CNT  FROM  GIBU.TBRA_UPSO  WHERE  BEFORE_UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  ''  UPSO_CD  ,  ''  UPSO_NM  ,  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$check_chgowner
    //##**$$addr_chk
    /*
    */
    public DOBJ CTLaddr_chk(DOBJ dobj)
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
            dobj  = CALLaddr_chk_SEL1(Conn, dobj);           //  �����ּҰ˻�
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
    public DOBJ CTLaddr_chk( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLaddr_chk_SEL1(Conn, dobj);           //  �����ּҰ˻�
        return dobj;
    }
    // �����ּҰ˻�
    // �� ��ϵ� ������ �������� ����� �ּҰ� �ִ��� üũ�Ѵ�
    public DOBJ CALLaddr_chk_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLaddr_chk_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaddr_chk_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_ADDR1 = dobj.getRetObject("S").getRecord().get("UPSO_ADDR1");   //���� �ּ�1
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  UPSO_NM  ,  MNGEMSTR_NM  ,  UPSO_NEW_ADDR1  ||  DECODE(UPSO_NEW_ADDR2,  '',  '',  ',  '||UPSO_NEW_ADDR2)  ||  UPSO_REF_INFO  UPSO_ADDR  ,  CLSBS_YRMN  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_NEW_ADDR1  =  :UPSO_ADDR1  ORDER  BY  UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_ADDR1", UPSO_ADDR1);               //���� �ּ�1
        return sobj;
    }
    //##**$$addr_chk
    //##**$$upso_regist_ky2
    /*
    */
    public DOBJ CTLupso_regist_ky2(DOBJ dobj)
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
            dobj  = CALLupso_regist_ky2_OSP51(Conn, dobj);           //  �ݿ��α׼����� �̰�
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
    public DOBJ CTLupso_regist_ky2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_regist_ky2_OSP51(Conn, dobj);           //  �ݿ��α׼����� �̰�
        return dobj;
    }
    // �ݿ��α׼����� �̰�
    public DOBJ CALLupso_regist_ky2_OSP51(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP51");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP51");
        String[]  incolumns ={"UPSO_CD","BEFORE_UPSO_CD","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INSPRES_ID ="K095176";         //����� ID
            record.put("INSPRES_ID",INSPRES_ID);
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
        String   spname ="GIBU.SP_PROC_KYLOG_OWNER_TRANS";
        int[]    intypes={12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        robj.setName("OSP51");
        robj.Println("OSP51");
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$upso_regist_ky2
    //##**$$upso_regist_onoff
    /*
    */
    public DOBJ CTLupso_regist_onoff(DOBJ dobj)
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
            dobj  = CALLupso_regist_onoff_MIUD1(Conn, dobj);           //  ���ҵ��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_regist_onoff_MIUD55(Conn, dobj);           //  �ڵ���ü������ �߼۹��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_regist_onoff_SEL17(Conn, dobj);           //  ���һ�������ȸ
            if( dobj.getRetObject("SEL17").getRecordCnt() > 0 && dobj.getRetObject("SEL17").getRecord().get("BSTYP_CD").equals("o"))
            {
                dobj  = CALLupso_regist_onoff_SEL24(Conn, dobj);           //  �뷡���
                dobj  = CALLupso_regist_onoff_MIUD79(Conn, dobj);           //  û���߼۱���
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
            }
            else
            {
                dobj  = CALLupso_regist_onoff_MIUD79(Conn, dobj);           //  û���߼۱���
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
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
    public DOBJ CTLupso_regist_onoff( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_regist_onoff_MIUD1(Conn, dobj);           //  ���ҵ��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupso_regist_onoff_MIUD55(Conn, dobj);           //  �ڵ���ü������ �߼۹��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupso_regist_onoff_SEL17(Conn, dobj);           //  ���һ�������ȸ
        if( dobj.getRetObject("SEL17").getRecordCnt() > 0 && dobj.getRetObject("SEL17").getRecord().get("BSTYP_CD").equals("o"))
        {
            dobj  = CALLupso_regist_onoff_SEL24(Conn, dobj);           //  �뷡���
            dobj  = CALLupso_regist_onoff_MIUD79(Conn, dobj);           //  û���߼۱���
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        else
        {
            dobj  = CALLupso_regist_onoff_MIUD79(Conn, dobj);           //  û���߼۱���
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // ���ҵ��
    public DOBJ CALLupso_regist_onoff_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MIUD1");
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_regist_onoff_SEL44(Conn, dobj);           //  �����ڵ����
                dobj  = CALLupso_regist_onoff_UPD44(Conn, dobj);           //  �Ϸù�ȣ����
                dobj  = CALLupso_regist_onoff_INS6(Conn, dobj);           //  �⺻�����Է�
                if( dobj.getRetObject("R").getRecord().get("UPSO_STAT").equals("1") && ( dobj.getRetObject("R").getRecord().get("BSTYP_CD").equals(dvobj.getRecord().get("NULL")) || dobj.getRetObject("R").getRecord().get("UPSO_GRAD").equals(dvobj.getRecord().get("NULL")) ))
                {
                    dobj.setRtncode(9);
                    if(dobj.getRtncode() == 9)
                    {
                        String message ="���� ���ĵ���� ��� ������ ����� �Է��ϼž� �մϴ�.";
                        dobj.setRetmsg(message);
                        Conn.rollback();
                        return dobj;
                    }
                }
                else if(!dobj.getRetObject("R").getRecord().get("BSTYP_CD").equals("") && !dobj.getRetObject("R").getRecord().get("UPSO_GRAD").equals(""))
                {
                    dobj  = CALLupso_regist_onoff_INS62(Conn, dobj);           //  ���һ��������Է�
                    if( dobj.getRetObject("R").getRecord().get("BSTYP_CD").equals("o"))
                    {
                        dobj  = CALLupso_regist_onoff_INS63(Conn, dobj);           //  �뷡��������Է�
                        dobj  = CALLupso_regist_onoff_OSP74(Conn, dobj);           //  ȸ��������ν���
                        dobj  = CALLupso_regist_onoff_XIUD65(Conn, dobj);           //  Ŀ��
                        dobj  = CALLupso_regist_onoff_OSP66(Conn, dobj);           //  ������ ������ ����
                        dobj  = CALLupso_regist_onoff_INS55(Conn, dobj);           //  �¿������ֱ��������
                        dobj  = CALLupso_regist_onoff_SEL56(Conn, dobj);           //  �¿���������ȸ
                        if( dobj.getRetObject("SEL56").getRecordCnt() == 1)
                        {
                            dobj  = CALLupso_regist_onoff_UPD60(Conn, dobj);           //  ���� �¿�����������
                            dobj  = CALLupso_regist_onoff_DEL113(Conn, dobj);           //  ���� ����
                            dobj  = CALLupso_regist_onoff_DEL114(Conn, dobj);           //  �뷡����������
                            dobj  = CALLupso_regist_onoff_XIUD55(Conn, dobj);           //  Ŀ��
                            dobj  = CALLupso_regist_onoff_OSP52(Conn, dobj);           //  ������ ������ ����
                            if(!dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD").equals(""))
                            {
                                dobj  = CALLupso_regist_onoff_XIUD41(Conn, dobj);           //  ���� �¿������� ����
                                dobj  = CALLupso_regist_onoff_XIUD42(Conn, dobj);           //  ���� ���ֱ����� ����
                                dobj  = CALLupso_regist_onoff_XIUD43(Conn, dobj);           //  ���� ���긮 ���� ����
                                dobj  = CALLupso_regist_onoff_XIUD50(Conn, dobj);           //  ���� �¿��� ���� ����
                                dobj  = CALLupso_regist_onoff_OSP51(Conn, dobj);           //  �ݿ��α׼����� �̰�
                            }
                            else if(!dobj.getRetObject("S4").getRecord().get("UPSO_CD").equals(""))
                            {
                                dobj  = CALLupso_regist_onoff_XIUD68(Conn, dobj);           //  ���ҹ湮���
                                dobj  = CALLupso_regist_onoff_SEL66(Conn, dobj);           //  ÷�����Ͽ���Ȯ��
                                if( dobj.getRetObject("SEL66").getRecord().getDouble("CNT") > 0)
                                {
                                    dobj  = CALLupso_regist_onoff_XIUD69(Conn, dobj);           //  ÷�����ϵ��
                                    dobj  = CALLupso_regist_onoff_XIUD66(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_XIUD70(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_SEL79(Conn, dobj);           //  �ڵ���ü ��û����Ȯ��
                                    if( dobj.getRetObject("SEL79").getRecord().getDouble("CNT") > 0)
                                    {
                                        dobj  = CALLupso_regist_onoff_XIUD84(Conn, dobj);           //  �����ڵ� ����
                                        dobj  = CALLupso_regist_onoff_XIUD81(Conn, dobj);           //  �ڵ���ü��û�� �����ڵ庯��
                                    }
                                }
                                else
                                {
                                    dobj  = CALLupso_regist_onoff_XIUD66(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_XIUD70(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_SEL79(Conn, dobj);           //  �ڵ���ü ��û����Ȯ��
                                    if( dobj.getRetObject("SEL79").getRecord().getDouble("CNT") > 0)
                                    {
                                        dobj  = CALLupso_regist_onoff_XIUD84(Conn, dobj);           //  �����ڵ� ����
                                        dobj  = CALLupso_regist_onoff_XIUD81(Conn, dobj);           //  �ڵ���ü��û�� �����ڵ庯��
                                    }
                                }
                            }
                        }
                        else if( dobj.getRetObject("SEL56").getRecordCnt() > 1)
                        {
                            dobj  = CALLupso_regist_onoff_UPD63(Conn, dobj);           //  ���� �¿�����������
                            dobj  = CALLupso_regist_onoff_DEL113(Conn, dobj);           //  ���� ����
                            dobj  = CALLupso_regist_onoff_DEL114(Conn, dobj);           //  �뷡����������
                            dobj  = CALLupso_regist_onoff_XIUD55(Conn, dobj);           //  Ŀ��
                            dobj  = CALLupso_regist_onoff_OSP52(Conn, dobj);           //  ������ ������ ����
                            if(!dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD").equals(""))
                            {
                                dobj  = CALLupso_regist_onoff_XIUD41(Conn, dobj);           //  ���� �¿������� ����
                                dobj  = CALLupso_regist_onoff_XIUD42(Conn, dobj);           //  ���� ���ֱ����� ����
                                dobj  = CALLupso_regist_onoff_XIUD43(Conn, dobj);           //  ���� ���긮 ���� ����
                                dobj  = CALLupso_regist_onoff_XIUD50(Conn, dobj);           //  ���� �¿��� ���� ����
                                dobj  = CALLupso_regist_onoff_OSP51(Conn, dobj);           //  �ݿ��α׼����� �̰�
                            }
                            else if(!dobj.getRetObject("S4").getRecord().get("UPSO_CD").equals(""))
                            {
                                dobj  = CALLupso_regist_onoff_XIUD68(Conn, dobj);           //  ���ҹ湮���
                                dobj  = CALLupso_regist_onoff_SEL66(Conn, dobj);           //  ÷�����Ͽ���Ȯ��
                                if( dobj.getRetObject("SEL66").getRecord().getDouble("CNT") > 0)
                                {
                                    dobj  = CALLupso_regist_onoff_XIUD69(Conn, dobj);           //  ÷�����ϵ��
                                    dobj  = CALLupso_regist_onoff_XIUD66(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_XIUD70(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_SEL79(Conn, dobj);           //  �ڵ���ü ��û����Ȯ��
                                    if( dobj.getRetObject("SEL79").getRecord().getDouble("CNT") > 0)
                                    {
                                        dobj  = CALLupso_regist_onoff_XIUD84(Conn, dobj);           //  �����ڵ� ����
                                        dobj  = CALLupso_regist_onoff_XIUD81(Conn, dobj);           //  �ڵ���ü��û�� �����ڵ庯��
                                    }
                                }
                                else
                                {
                                    dobj  = CALLupso_regist_onoff_XIUD66(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_XIUD70(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_SEL79(Conn, dobj);           //  �ڵ���ü ��û����Ȯ��
                                    if( dobj.getRetObject("SEL79").getRecord().getDouble("CNT") > 0)
                                    {
                                        dobj  = CALLupso_regist_onoff_XIUD84(Conn, dobj);           //  �����ڵ� ����
                                        dobj  = CALLupso_regist_onoff_XIUD81(Conn, dobj);           //  �ڵ���ü��û�� �����ڵ庯��
                                    }
                                }
                            }
                        }
                        else
                        {
                            dobj  = CALLupso_regist_onoff_UPD62(Conn, dobj);           //  ���� �¿�����������
                            dobj  = CALLupso_regist_onoff_DEL113(Conn, dobj);           //  ���� ����
                            dobj  = CALLupso_regist_onoff_DEL114(Conn, dobj);           //  �뷡����������
                            dobj  = CALLupso_regist_onoff_XIUD55(Conn, dobj);           //  Ŀ��
                            dobj  = CALLupso_regist_onoff_OSP52(Conn, dobj);           //  ������ ������ ����
                            if(!dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD").equals(""))
                            {
                                dobj  = CALLupso_regist_onoff_XIUD41(Conn, dobj);           //  ���� �¿������� ����
                                dobj  = CALLupso_regist_onoff_XIUD42(Conn, dobj);           //  ���� ���ֱ����� ����
                                dobj  = CALLupso_regist_onoff_XIUD43(Conn, dobj);           //  ���� ���긮 ���� ����
                                dobj  = CALLupso_regist_onoff_XIUD50(Conn, dobj);           //  ���� �¿��� ���� ����
                                dobj  = CALLupso_regist_onoff_OSP51(Conn, dobj);           //  �ݿ��α׼����� �̰�
                            }
                            else if(!dobj.getRetObject("S4").getRecord().get("UPSO_CD").equals(""))
                            {
                                dobj  = CALLupso_regist_onoff_XIUD68(Conn, dobj);           //  ���ҹ湮���
                                dobj  = CALLupso_regist_onoff_SEL66(Conn, dobj);           //  ÷�����Ͽ���Ȯ��
                                if( dobj.getRetObject("SEL66").getRecord().getDouble("CNT") > 0)
                                {
                                    dobj  = CALLupso_regist_onoff_XIUD69(Conn, dobj);           //  ÷�����ϵ��
                                    dobj  = CALLupso_regist_onoff_XIUD66(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_XIUD70(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_SEL79(Conn, dobj);           //  �ڵ���ü ��û����Ȯ��
                                    if( dobj.getRetObject("SEL79").getRecord().getDouble("CNT") > 0)
                                    {
                                        dobj  = CALLupso_regist_onoff_XIUD84(Conn, dobj);           //  �����ڵ� ����
                                        dobj  = CALLupso_regist_onoff_XIUD81(Conn, dobj);           //  �ڵ���ü��û�� �����ڵ庯��
                                    }
                                }
                                else
                                {
                                    dobj  = CALLupso_regist_onoff_XIUD66(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_XIUD70(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_SEL79(Conn, dobj);           //  �ڵ���ü ��û����Ȯ��
                                    if( dobj.getRetObject("SEL79").getRecord().getDouble("CNT") > 0)
                                    {
                                        dobj  = CALLupso_regist_onoff_XIUD84(Conn, dobj);           //  �����ڵ� ����
                                        dobj  = CALLupso_regist_onoff_XIUD81(Conn, dobj);           //  �ڵ���ü��û�� �����ڵ庯��
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        dobj  = CALLupso_regist_onoff_OSP74(Conn, dobj);           //  ȸ��������ν���
                        dobj  = CALLupso_regist_onoff_XIUD65(Conn, dobj);           //  Ŀ��
                        dobj  = CALLupso_regist_onoff_OSP66(Conn, dobj);           //  ������ ������ ����
                        dobj  = CALLupso_regist_onoff_INS55(Conn, dobj);           //  �¿������ֱ��������
                        dobj  = CALLupso_regist_onoff_SEL56(Conn, dobj);           //  �¿���������ȸ
                        if( dobj.getRetObject("SEL56").getRecordCnt() == 1)
                        {
                            dobj  = CALLupso_regist_onoff_UPD60(Conn, dobj);           //  ���� �¿�����������
                            dobj  = CALLupso_regist_onoff_DEL113(Conn, dobj);           //  ���� ����
                            dobj  = CALLupso_regist_onoff_DEL114(Conn, dobj);           //  �뷡����������
                            dobj  = CALLupso_regist_onoff_XIUD55(Conn, dobj);           //  Ŀ��
                            dobj  = CALLupso_regist_onoff_OSP52(Conn, dobj);           //  ������ ������ ����
                            if(!dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD").equals(""))
                            {
                                dobj  = CALLupso_regist_onoff_XIUD41(Conn, dobj);           //  ���� �¿������� ����
                                dobj  = CALLupso_regist_onoff_XIUD42(Conn, dobj);           //  ���� ���ֱ����� ����
                                dobj  = CALLupso_regist_onoff_XIUD43(Conn, dobj);           //  ���� ���긮 ���� ����
                                dobj  = CALLupso_regist_onoff_XIUD50(Conn, dobj);           //  ���� �¿��� ���� ����
                                dobj  = CALLupso_regist_onoff_OSP51(Conn, dobj);           //  �ݿ��α׼����� �̰�
                            }
                            else if(!dobj.getRetObject("S4").getRecord().get("UPSO_CD").equals(""))
                            {
                                dobj  = CALLupso_regist_onoff_XIUD68(Conn, dobj);           //  ���ҹ湮���
                                dobj  = CALLupso_regist_onoff_SEL66(Conn, dobj);           //  ÷�����Ͽ���Ȯ��
                                if( dobj.getRetObject("SEL66").getRecord().getDouble("CNT") > 0)
                                {
                                    dobj  = CALLupso_regist_onoff_XIUD69(Conn, dobj);           //  ÷�����ϵ��
                                    dobj  = CALLupso_regist_onoff_XIUD66(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_XIUD70(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_SEL79(Conn, dobj);           //  �ڵ���ü ��û����Ȯ��
                                    if( dobj.getRetObject("SEL79").getRecord().getDouble("CNT") > 0)
                                    {
                                        dobj  = CALLupso_regist_onoff_XIUD84(Conn, dobj);           //  �����ڵ� ����
                                        dobj  = CALLupso_regist_onoff_XIUD81(Conn, dobj);           //  �ڵ���ü��û�� �����ڵ庯��
                                    }
                                }
                                else
                                {
                                    dobj  = CALLupso_regist_onoff_XIUD66(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_XIUD70(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_SEL79(Conn, dobj);           //  �ڵ���ü ��û����Ȯ��
                                    if( dobj.getRetObject("SEL79").getRecord().getDouble("CNT") > 0)
                                    {
                                        dobj  = CALLupso_regist_onoff_XIUD84(Conn, dobj);           //  �����ڵ� ����
                                        dobj  = CALLupso_regist_onoff_XIUD81(Conn, dobj);           //  �ڵ���ü��û�� �����ڵ庯��
                                    }
                                }
                            }
                        }
                        else if( dobj.getRetObject("SEL56").getRecordCnt() > 1)
                        {
                            dobj  = CALLupso_regist_onoff_UPD63(Conn, dobj);           //  ���� �¿�����������
                            dobj  = CALLupso_regist_onoff_DEL113(Conn, dobj);           //  ���� ����
                            dobj  = CALLupso_regist_onoff_DEL114(Conn, dobj);           //  �뷡����������
                            dobj  = CALLupso_regist_onoff_XIUD55(Conn, dobj);           //  Ŀ��
                            dobj  = CALLupso_regist_onoff_OSP52(Conn, dobj);           //  ������ ������ ����
                            if(!dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD").equals(""))
                            {
                                dobj  = CALLupso_regist_onoff_XIUD41(Conn, dobj);           //  ���� �¿������� ����
                                dobj  = CALLupso_regist_onoff_XIUD42(Conn, dobj);           //  ���� ���ֱ����� ����
                                dobj  = CALLupso_regist_onoff_XIUD43(Conn, dobj);           //  ���� ���긮 ���� ����
                                dobj  = CALLupso_regist_onoff_XIUD50(Conn, dobj);           //  ���� �¿��� ���� ����
                                dobj  = CALLupso_regist_onoff_OSP51(Conn, dobj);           //  �ݿ��α׼����� �̰�
                            }
                            else if(!dobj.getRetObject("S4").getRecord().get("UPSO_CD").equals(""))
                            {
                                dobj  = CALLupso_regist_onoff_XIUD68(Conn, dobj);           //  ���ҹ湮���
                                dobj  = CALLupso_regist_onoff_SEL66(Conn, dobj);           //  ÷�����Ͽ���Ȯ��
                                if( dobj.getRetObject("SEL66").getRecord().getDouble("CNT") > 0)
                                {
                                    dobj  = CALLupso_regist_onoff_XIUD69(Conn, dobj);           //  ÷�����ϵ��
                                    dobj  = CALLupso_regist_onoff_XIUD66(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_XIUD70(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_SEL79(Conn, dobj);           //  �ڵ���ü ��û����Ȯ��
                                    if( dobj.getRetObject("SEL79").getRecord().getDouble("CNT") > 0)
                                    {
                                        dobj  = CALLupso_regist_onoff_XIUD84(Conn, dobj);           //  �����ڵ� ����
                                        dobj  = CALLupso_regist_onoff_XIUD81(Conn, dobj);           //  �ڵ���ü��û�� �����ڵ庯��
                                    }
                                }
                                else
                                {
                                    dobj  = CALLupso_regist_onoff_XIUD66(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_XIUD70(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_SEL79(Conn, dobj);           //  �ڵ���ü ��û����Ȯ��
                                    if( dobj.getRetObject("SEL79").getRecord().getDouble("CNT") > 0)
                                    {
                                        dobj  = CALLupso_regist_onoff_XIUD84(Conn, dobj);           //  �����ڵ� ����
                                        dobj  = CALLupso_regist_onoff_XIUD81(Conn, dobj);           //  �ڵ���ü��û�� �����ڵ庯��
                                    }
                                }
                            }
                        }
                        else
                        {
                            dobj  = CALLupso_regist_onoff_UPD62(Conn, dobj);           //  ���� �¿�����������
                            dobj  = CALLupso_regist_onoff_DEL113(Conn, dobj);           //  ���� ����
                            dobj  = CALLupso_regist_onoff_DEL114(Conn, dobj);           //  �뷡����������
                            dobj  = CALLupso_regist_onoff_XIUD55(Conn, dobj);           //  Ŀ��
                            dobj  = CALLupso_regist_onoff_OSP52(Conn, dobj);           //  ������ ������ ����
                            if(!dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD").equals(""))
                            {
                                dobj  = CALLupso_regist_onoff_XIUD41(Conn, dobj);           //  ���� �¿������� ����
                                dobj  = CALLupso_regist_onoff_XIUD42(Conn, dobj);           //  ���� ���ֱ����� ����
                                dobj  = CALLupso_regist_onoff_XIUD43(Conn, dobj);           //  ���� ���긮 ���� ����
                                dobj  = CALLupso_regist_onoff_XIUD50(Conn, dobj);           //  ���� �¿��� ���� ����
                                dobj  = CALLupso_regist_onoff_OSP51(Conn, dobj);           //  �ݿ��α׼����� �̰�
                            }
                            else if(!dobj.getRetObject("S4").getRecord().get("UPSO_CD").equals(""))
                            {
                                dobj  = CALLupso_regist_onoff_XIUD68(Conn, dobj);           //  ���ҹ湮���
                                dobj  = CALLupso_regist_onoff_SEL66(Conn, dobj);           //  ÷�����Ͽ���Ȯ��
                                if( dobj.getRetObject("SEL66").getRecord().getDouble("CNT") > 0)
                                {
                                    dobj  = CALLupso_regist_onoff_XIUD69(Conn, dobj);           //  ÷�����ϵ��
                                    dobj  = CALLupso_regist_onoff_XIUD66(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_XIUD70(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_SEL79(Conn, dobj);           //  �ڵ���ü ��û����Ȯ��
                                    if( dobj.getRetObject("SEL79").getRecord().getDouble("CNT") > 0)
                                    {
                                        dobj  = CALLupso_regist_onoff_XIUD84(Conn, dobj);           //  �����ڵ� ����
                                        dobj  = CALLupso_regist_onoff_XIUD81(Conn, dobj);           //  �ڵ���ü��û�� �����ڵ庯��
                                    }
                                }
                                else
                                {
                                    dobj  = CALLupso_regist_onoff_XIUD66(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_XIUD70(Conn, dobj);           //  �����ڵ� ����
                                    dobj  = CALLupso_regist_onoff_SEL79(Conn, dobj);           //  �ڵ���ü ��û����Ȯ��
                                    if( dobj.getRetObject("SEL79").getRecord().getDouble("CNT") > 0)
                                    {
                                        dobj  = CALLupso_regist_onoff_XIUD84(Conn, dobj);           //  �����ڵ� ����
                                        dobj  = CALLupso_regist_onoff_XIUD81(Conn, dobj);           //  �ڵ���ü��û�� �����ڵ庯��
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_regist_onoff_SEL25(Conn, dobj);           //  ���һ���Ȯ��
                if( dobj.getRetObject("SEL25").getRecord().get("UPSO_STAT").equals("2"))
                {
                    dobj  = CALLupso_regist_onoff_DEL27(Conn, dobj);           //  ������������
                    dobj  = CALLupso_regist_onoff_DEL34(Conn, dobj);           //  �뷡����������
                    dobj  = CALLupso_regist_onoff_UPD28(Conn, dobj);           //  ���ұ⺻��������
                    dobj  = CALLupso_regist_onoff_SEL29(Conn, dobj);           //  CHG_NUM����
                    dobj  = CALLupso_regist_onoff_INS30(Conn, dobj);           //  ���һ�������
                    if(( dobj.getRetObject("R").getRecord().get("BSTYP_CD").equals("o") && dobj.getRetObject("INS30").getRecordCnt() > 0 ) && !dobj.getRetObject("S1").getRecord().get("BSTYP_CD").equals("") && !dobj.getRetObject("S1").getRecord().get("GRAD_GBN").equals(""))
                    {
                        dobj  = CALLupso_regist_onoff_INS32(Conn, dobj);           //  �뷡�������
                    }
                }
                else
                {
                    dobj  = CALLupso_regist_onoff_UPD14(Conn, dobj);           //  ���ұ⺻��������
                    dobj  = CALLupso_regist_onoff_SEL35(Conn, dobj);           //  �������������������
                    dobj  = CALLupso_regist_onoff_UPD34(Conn, dobj);           //  ��������������
                    if(!dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_NM").equals(""))
                    {
                        dobj  = CALLupso_regist_onoff_SEL36(Conn, dobj);           //  ��� ����üũ
                        if( dobj.getRetObject("SEL36").getRecord().getDouble("CNT") == 0)
                        {
                            dobj  = CALLupso_regist_onoff_INS21(Conn, dobj);           //  ���ҹ湮�ű�
                            dobj  = CALLupso_regist_onoff_INS39(Conn, dobj);           //  ���ҹ湮�޸���
                        }
                        else
                        {
                            dobj  = CALLupso_regist_onoff_INS38(Conn, dobj);           //  ���ҹ湮�޸���
                        }
                    }
                }
            }
        }
        return dobj;
    }
    // �����ڵ����
    // ���κ� �����ڵ��� max��
    public DOBJ CALLupso_regist_onoff_SEL44(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL44");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL44");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_onoff_SEL44(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL44");
        rvobj.Println("SEL44");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_SEL44(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(SNUM+1,7,'0')  SNUM  ,  SNUM_TYPE  ,  CDCLASS  ,  LPAD(SNUM+1,7,'0')  ||  CDCLASS  N_UPSO_CD  FROM  GIBU.TBRA_SNUM  WHERE  SNUM_TYPE  =  'U'   \n";
        query +=" AND  CDCLASS  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ���һ���Ȯ��
    // ������ ��� ���¸� Ȯ���Ѵ٤�. 1: ���ĵ�� 2: �����
    public DOBJ CALLupso_regist_onoff_SEL25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL25");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL25");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_onoff_SEL25(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL25");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_SEL25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_STAT  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Ϸù�ȣ����
    // ���κ� �ϷĹ�ȣ(MAX��) ����
    public DOBJ CALLupso_regist_onoff_UPD44(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD44");
        VOBJ dvobj = dobj.getRetObject("SEL44");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_UPD44(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD44") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_UPD44(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   SNUM_TYPE = dvobj.getRecord().get("SNUM_TYPE");
        String   CDCLASS = dvobj.getRecord().get("CDCLASS");
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   SNUM = dobj.getRetObject("SEL44").getRecord().get("SNUM");   //�Ϸù�ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_SNUM  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MOD_DATE=SYSDATE , SNUM=:SNUM  \n";
        query +=" where CDCLASS=:CDCLASS  \n";
        query +=" and SNUM_TYPE=:SNUM_TYPE";
        sobj.setSql(query);
        sobj.setString("SNUM_TYPE", SNUM_TYPE);
        sobj.setString("CDCLASS", CDCLASS);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("SNUM", SNUM);               //�Ϸù�ȣ
        return sobj;
    }
    // �⺻�����Է�
    public DOBJ CALLupso_regist_onoff_INS6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_INS6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_INS6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CLIENT_NUM ="";        //�� ��ȣ
        String EMAIL_ADDR ="";        //�̸��� �ּ�
        String INS_DATE ="";        //��� �Ͻ�
        String UPSO_NM ="";        //���� ��
        String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //���� �����ȣ
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   PERMMSTR_PHONNUM = dvobj.getRecord().get("PERMMSTR_PHONNUM");   //�㰡�� ��ȭ��ȣ
        String   MNGEMSTR_REF_INFO = dvobj.getRecord().get("MNGEMSTR_REF_INFO");
        String   MNGEMSTR_NEW_ZIP = dvobj.getRecord().get("MNGEMSTR_NEW_ZIP");
        String   PERMMSTR_NEW_ADDR2 = dvobj.getRecord().get("PERMMSTR_NEW_ADDR2");
        String   PERMMSTR_NEW_ADDR1 = dvobj.getRecord().get("PERMMSTR_NEW_ADDR1");
        String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //�濵�� �̸�
        String   EMAIL_ADDR_1 = dobj.getRetObject("R").getRecord().get("EMAIL_ADDR");   //�̸��� �ּ�
        String   CORP_NUM = dvobj.getRecord().get("CORP_NUM");   //���� ��ȣ
        String   MNGEMSTR_REMAK = dvobj.getRecord().get("MNGEMSTR_REMAK");   //�濵�� ���
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
        String   PERMMSTR_REF_INFO = dvobj.getRecord().get("PERMMSTR_REF_INFO");
        String   UPSO_NEW_ADDR2 = dvobj.getRecord().get("UPSO_NEW_ADDR2");
        String   PAPER_CANC_YN = dvobj.getRecord().get("PAPER_CANC_YN");   //���� ��� ����
        String   UPSO_NEW_ZIP = dvobj.getRecord().get("UPSO_NEW_ZIP");
        String   UPSO_NM_1 = dobj.getRetObject("R").getRecord().get("UPSO_NM");   //���� ��
        String   CONTR_NM = dvobj.getRecord().get("CONTR_NM");
        String   MNGEMSTR_PHONNUM = dvobj.getRecord().get("MNGEMSTR_PHONNUM");   //�濵�� ��ȭ��ȣ
        String   MNGEMSTR_RESINUM = dvobj.getRecord().get("MNGEMSTR_RESINUM");   //�濵�� �ֹι�ȣ
        String   MNGEMSTR_ZIP = dvobj.getRecord().get("MNGEMSTR_ZIP");   //�濵�� �����ȣ
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   UPSO_REF_INFO = dvobj.getRecord().get("UPSO_REF_INFO");
        String   BILL_ISS_YN = dvobj.getRecord().get("BILL_ISS_YN");   //��꼭 ���� ����
        String   PERMMSTR_NEW_ZIP = dvobj.getRecord().get("PERMMSTR_NEW_ZIP");
        String   UPSO_PHON = dvobj.getRecord().get("UPSO_PHON");   //���� ��ȭ
        String   PERMMSTR_ZIP = dvobj.getRecord().get("PERMMSTR_ZIP");   //�㰡�� �����ȣ
        String   BEFORE_UPSO_CD = dvobj.getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
        String   UPSO_BD_MNG_NUM = dvobj.getRecord().get("UPSO_BD_MNG_NUM");
        String   UPSO_NEW_ZIP1 = dvobj.getRecord().get("UPSO_NEW_ZIP1");
        String   AERO_STUDENT = dvobj.getRecord().get("AERO_STUDENT");
        String   BIOWN_NUM = dvobj.getRecord().get("BIOWN_NUM");   //����� ��ȣ
        String   MNGEMSTR_BD_MNG_NUM = dvobj.getRecord().get("MNGEMSTR_BD_MNG_NUM");
        String   MAIL_RCPT = dvobj.getRecord().get("MAIL_RCPT");   //���� ������
        String   MNG_ZIP = dvobj.getRecord().get("MNG_ZIP");   //���� �����ȣ
        String   MNGEMSTR_NEW_ADDR1 = dvobj.getRecord().get("MNGEMSTR_NEW_ADDR1");
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   UPSO_REMAK = dvobj.getRecord().get("UPSO_REMAK");   //���� ���
        String   PERMMSTR_BD_MNG_NUM = dvobj.getRecord().get("PERMMSTR_BD_MNG_NUM");
        String   MNGEMSTR_NEW_ADDR2 = dvobj.getRecord().get("MNGEMSTR_NEW_ADDR2");
        String   PERMMSTR_RESINUM = dvobj.getRecord().get("PERMMSTR_RESINUM");   //�㰡�� �ֹι�ȣ
        String   PERMMSTR_NM = dvobj.getRecord().get("PERMMSTR_NM");   //�㰡�� �̸�
        String   CLIENT_NUM_1 = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //�� ��ȣ
        String   PERMMSTR_REMAK = dvobj.getRecord().get("PERMMSTR_REMAK");   //�㰡�� ���
        double   AREA = dvobj.getRecord().getDouble("AREA");   //����
        String   UPSO_STAT = dvobj.getRecord().get("UPSO_STAT");   //���� ����
        String   PERMMSTR_HPNUM = dvobj.getRecord().get("PERMMSTR_HPNUM");   //�㰡�� �ڵ�����ȣ
        String   UPSO_NEW_ADDR1 = dvobj.getRecord().get("UPSO_NEW_ADDR1");
        String   OPBI_DAY = dvobj.getRecord().get("OPBI_DAY");   //���� ����
        String   SONG_STUDENT = dvobj.getRecord().get("SONG_STUDENT");
        String   PAYPRES_GBN = dvobj.getRecord().get("PAYPRES_GBN");   //������ ����
        String   MNGEMSTR_HPNUM = dvobj.getRecord().get("MNGEMSTR_HPNUM");   //�濵�� �ڵ�����ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   MNGEMSTR_ADDR1 = dobj.getRetObject("R").getRecord().get("MNGEMSTR_ADDR");   //�濵�� �ּ�1
        String   MNGEMSTR_ADDR2 ="";   //�濵�� �ּ�2
        String   PERMMSTR_ADDR1 = dobj.getRetObject("R").getRecord().get("PREMMSTR_ADDR");   //�㰡�� �ּ�1
        String   PERMMSTR_ADDR2 ="";   //�㰡�� �ּ�2
        String   UPSO_ADDR1 = dobj.getRetObject("R").getRecord().get("UPSO_ADDR");   //���� �ּ�1
        String   UPSO_ADDR2 ="";   //���� �ּ�2
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //TEMP1
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO (MNGEMSTR_HPNUM, PAYPRES_GBN, SONG_STUDENT, OPBI_DAY, UPSO_NEW_ADDR1, MNGEMSTR_ADDR1, INS_DATE, PERMMSTR_HPNUM, UPSO_STAT, AREA, PERMMSTR_REMAK, CLIENT_NUM, PERMMSTR_NM, PERMMSTR_RESINUM, MNGEMSTR_NEW_ADDR2, PERMMSTR_BD_MNG_NUM, UPSO_REMAK, MNGEMSTR_ADDR2, BSCON_CD, MNGEMSTR_NEW_ADDR1, MNG_ZIP, MAIL_RCPT, PERMMSTR_ADDR1, MNGEMSTR_BD_MNG_NUM, BIOWN_NUM, AERO_STUDENT, UPSO_NEW_ZIP1, UPSO_BD_MNG_NUM, BEFORE_UPSO_CD, INSPRES_ID, PERMMSTR_ZIP, UPSO_PHON, PERMMSTR_NEW_ZIP, BILL_ISS_YN, UPSO_REF_INFO, UPSO_ADDR1, STAFF_CD, MNGEMSTR_ZIP, MNGEMSTR_RESINUM, MNGEMSTR_PHONNUM, UPSO_CD, CONTR_NM, UPSO_NM, UPSO_NEW_ZIP, PAPER_CANC_YN, UPSO_NEW_ADDR2, PERMMSTR_REF_INFO, UPSO_ADDR2, MCHNDAESU, MNGEMSTR_REMAK, CORP_NUM, EMAIL_ADDR, PERMMSTR_ADDR2, MNGEMSTR_NM, PERMMSTR_NEW_ADDR1, PERMMSTR_NEW_ADDR2, MNGEMSTR_NEW_ZIP, MNGEMSTR_REF_INFO, PERMMSTR_PHONNUM, BRAN_CD, UPSO_ZIP)  \n";
        query +=" values(:MNGEMSTR_HPNUM , :PAYPRES_GBN , :SONG_STUDENT , :OPBI_DAY , :UPSO_NEW_ADDR1 , :MNGEMSTR_ADDR1 , SYSDATE, :PERMMSTR_HPNUM , :UPSO_STAT , :AREA , :PERMMSTR_REMAK , GIBU.FT_GET_AUTO_CLIENTNUM(:CLIENT_NUM_1), :PERMMSTR_NM , :PERMMSTR_RESINUM , :MNGEMSTR_NEW_ADDR2 , :PERMMSTR_BD_MNG_NUM , :UPSO_REMAK , :MNGEMSTR_ADDR2 , :BSCON_CD , :MNGEMSTR_NEW_ADDR1 , :MNG_ZIP , :MAIL_RCPT , :PERMMSTR_ADDR1 , :MNGEMSTR_BD_MNG_NUM , :BIOWN_NUM , :AERO_STUDENT , :UPSO_NEW_ZIP1 , :UPSO_BD_MNG_NUM , :BEFORE_UPSO_CD , :INSPRES_ID , :PERMMSTR_ZIP , :UPSO_PHON , :PERMMSTR_NEW_ZIP , :BILL_ISS_YN , :UPSO_REF_INFO , :UPSO_ADDR1 , :STAFF_CD , :MNGEMSTR_ZIP , :MNGEMSTR_RESINUM , :MNGEMSTR_PHONNUM , :UPSO_CD , :CONTR_NM , REPLACE(REPLACE(:UPSO_NM_1, CHR(13), ''), CHR(10), ''), :UPSO_NEW_ZIP , :PAPER_CANC_YN , :UPSO_NEW_ADDR2 , :PERMMSTR_REF_INFO , :UPSO_ADDR2 , :MCHNDAESU , :MNGEMSTR_REMAK , :CORP_NUM , REPLACE(REPLACE(:EMAIL_ADDR_1, CHR(13), ''), CHR(10), ''), :PERMMSTR_ADDR2 , :MNGEMSTR_NM , :PERMMSTR_NEW_ADDR1 , :PERMMSTR_NEW_ADDR2 , :MNGEMSTR_NEW_ZIP , :MNGEMSTR_REF_INFO , :PERMMSTR_PHONNUM , :BRAN_CD , :UPSO_ZIP )";
        sobj.setSql(query);
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("PERMMSTR_PHONNUM", PERMMSTR_PHONNUM);               //�㰡�� ��ȭ��ȣ
        sobj.setString("MNGEMSTR_REF_INFO", MNGEMSTR_REF_INFO);
        sobj.setString("MNGEMSTR_NEW_ZIP", MNGEMSTR_NEW_ZIP);
        sobj.setString("PERMMSTR_NEW_ADDR2", PERMMSTR_NEW_ADDR2);
        sobj.setString("PERMMSTR_NEW_ADDR1", PERMMSTR_NEW_ADDR1);
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //�濵�� �̸�
        sobj.setString("EMAIL_ADDR_1", EMAIL_ADDR_1);               //�̸��� �ּ�
        sobj.setString("CORP_NUM", CORP_NUM);               //���� ��ȣ
        sobj.setString("MNGEMSTR_REMAK", MNGEMSTR_REMAK);               //�濵�� ���
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        sobj.setString("PERMMSTR_REF_INFO", PERMMSTR_REF_INFO);
        sobj.setString("UPSO_NEW_ADDR2", UPSO_NEW_ADDR2);
        sobj.setString("PAPER_CANC_YN", PAPER_CANC_YN);               //���� ��� ����
        sobj.setString("UPSO_NEW_ZIP", UPSO_NEW_ZIP);
        sobj.setString("UPSO_NM_1", UPSO_NM_1);               //���� ��
        sobj.setString("CONTR_NM", CONTR_NM);
        sobj.setString("MNGEMSTR_PHONNUM", MNGEMSTR_PHONNUM);               //�濵�� ��ȭ��ȣ
        sobj.setString("MNGEMSTR_RESINUM", MNGEMSTR_RESINUM);               //�濵�� �ֹι�ȣ
        sobj.setString("MNGEMSTR_ZIP", MNGEMSTR_ZIP);               //�濵�� �����ȣ
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("UPSO_REF_INFO", UPSO_REF_INFO);
        sobj.setString("BILL_ISS_YN", BILL_ISS_YN);               //��꼭 ���� ����
        sobj.setString("PERMMSTR_NEW_ZIP", PERMMSTR_NEW_ZIP);
        sobj.setString("UPSO_PHON", UPSO_PHON);               //���� ��ȭ
        sobj.setString("PERMMSTR_ZIP", PERMMSTR_ZIP);               //�㰡�� �����ȣ
        sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
        sobj.setString("UPSO_BD_MNG_NUM", UPSO_BD_MNG_NUM);
        sobj.setString("UPSO_NEW_ZIP1", UPSO_NEW_ZIP1);
        sobj.setString("AERO_STUDENT", AERO_STUDENT);
        sobj.setString("BIOWN_NUM", BIOWN_NUM);               //����� ��ȣ
        sobj.setString("MNGEMSTR_BD_MNG_NUM", MNGEMSTR_BD_MNG_NUM);
        sobj.setString("MAIL_RCPT", MAIL_RCPT);               //���� ������
        sobj.setString("MNG_ZIP", MNG_ZIP);               //���� �����ȣ
        sobj.setString("MNGEMSTR_NEW_ADDR1", MNGEMSTR_NEW_ADDR1);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("UPSO_REMAK", UPSO_REMAK);               //���� ���
        sobj.setString("PERMMSTR_BD_MNG_NUM", PERMMSTR_BD_MNG_NUM);
        sobj.setString("MNGEMSTR_NEW_ADDR2", MNGEMSTR_NEW_ADDR2);
        sobj.setString("PERMMSTR_RESINUM", PERMMSTR_RESINUM);               //�㰡�� �ֹι�ȣ
        sobj.setString("PERMMSTR_NM", PERMMSTR_NM);               //�㰡�� �̸�
        sobj.setString("CLIENT_NUM_1", CLIENT_NUM_1);               //�� ��ȣ
        sobj.setString("PERMMSTR_REMAK", PERMMSTR_REMAK);               //�㰡�� ���
        sobj.setDouble("AREA", AREA);               //����
        sobj.setString("UPSO_STAT", UPSO_STAT);               //���� ����
        sobj.setString("PERMMSTR_HPNUM", PERMMSTR_HPNUM);               //�㰡�� �ڵ�����ȣ
        sobj.setString("UPSO_NEW_ADDR1", UPSO_NEW_ADDR1);
        sobj.setString("OPBI_DAY", OPBI_DAY);               //���� ����
        sobj.setString("SONG_STUDENT", SONG_STUDENT);
        sobj.setString("PAYPRES_GBN", PAYPRES_GBN);               //������ ����
        sobj.setString("MNGEMSTR_HPNUM", MNGEMSTR_HPNUM);               //�濵�� �ڵ�����ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("MNGEMSTR_ADDR1", MNGEMSTR_ADDR1);               //�濵�� �ּ�1
        sobj.setString("MNGEMSTR_ADDR2", MNGEMSTR_ADDR2);               //�濵�� �ּ�2
        sobj.setString("PERMMSTR_ADDR1", PERMMSTR_ADDR1);               //�㰡�� �ּ�1
        sobj.setString("PERMMSTR_ADDR2", PERMMSTR_ADDR2);               //�㰡�� �ּ�2
        sobj.setString("UPSO_ADDR1", UPSO_ADDR1);               //���� �ּ�1
        sobj.setString("UPSO_ADDR2", UPSO_ADDR2);               //���� �ּ�2
        sobj.setString("UPSO_CD", UPSO_CD);               //TEMP1
        return sobj;
    }
    // ������������
    public DOBJ CALLupso_regist_onoff_DEL27(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL27");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_DEL27(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL27") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_DEL27(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �뷡����������
    public DOBJ CALLupso_regist_onoff_DEL34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL34");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_DEL34(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL34") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_DEL34(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ұ⺻��������
    // ���ұ⺻������ �����Ѵ�. �� ���� ������ ���� ó���Ѵ�.
    public DOBJ CALLupso_regist_onoff_UPD28(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD28");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_UPD28(dobj, dvobj);
            qexe.DispSelectSql(sobj);
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
    private SQLObject SQLupso_regist_onoff_UPD28(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String EMAIL_ADDR ="";        //�̸��� �ּ�
        String MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");        //������ ID
        String MOD_DATE ="";        //���� �Ͻ�
        String UPSO_NM ="";        //���� ��
        String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //���� �����ȣ
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   BIOWN_NUM = dvobj.getRecord().get("BIOWN_NUM");   //����� ��ȣ
        String   PERMMSTR_PHONNUM = dvobj.getRecord().get("PERMMSTR_PHONNUM");   //�㰡�� ��ȭ��ȣ
        String   MAIL_RCPT = dvobj.getRecord().get("MAIL_RCPT");   //���� ������
        String   NEW_DAY = dvobj.getRecord().get("NEW_DAY");   //�ű� ����
        String   MNG_ZIP = dvobj.getRecord().get("MNG_ZIP");   //���� �����ȣ
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   MNGEMSTR_ADDR2 = dvobj.getRecord().get("MNGEMSTR_ADDR2");   //�濵�� �ּ�2
        String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //�濵�� �̸�
        String   EMAIL_ADDR_1 = dobj.getRetObject("R").getRecord().get("EMAIL_ADDR");   //�̸��� �ּ�
        String   CORP_NUM = dvobj.getRecord().get("CORP_NUM");   //���� ��ȣ
        String   UPSO_REMAK = dvobj.getRecord().get("UPSO_REMAK");   //���� ���
        String   UPSO_ADDR2 = dvobj.getRecord().get("UPSO_ADDR2");   //���� �ּ�2
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
        String   PAPER_CANC_YN = dvobj.getRecord().get("PAPER_CANC_YN");   //���� ��� ����
        String   PERMMSTR_RESINUM = dvobj.getRecord().get("PERMMSTR_RESINUM");   //�㰡�� �ֹι�ȣ
        String   PERMMSTR_NM = dvobj.getRecord().get("PERMMSTR_NM");   //�㰡�� �̸�
        String   UPSO_NM_1 = dobj.getRetObject("R").getRecord().get("UPSO_NM");   //���� ��
        String   CONTR_NM = dvobj.getRecord().get("CONTR_NM");
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   AREA = dvobj.getRecord().getDouble("AREA");   //����
        String   MNGEMSTR_ZIP = dvobj.getRecord().get("MNGEMSTR_ZIP");   //�濵�� �����ȣ
        String   MNGEMSTR_RESINUM = dvobj.getRecord().get("MNGEMSTR_RESINUM");   //�濵�� �ֹι�ȣ
        String   MNGEMSTR_PHONNUM = dvobj.getRecord().get("MNGEMSTR_PHONNUM");   //�濵�� ��ȭ��ȣ
        String   UPSO_STAT = dvobj.getRecord().get("UPSO_STAT");   //���� ����
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   UPSO_ADDR1 = dvobj.getRecord().get("UPSO_ADDR1");   //���� �ּ�1
        String   MNGEMSTR_ADDR1 = dvobj.getRecord().get("MNGEMSTR_ADDR1");   //�濵�� �ּ�1
        String   BILL_ISS_YN = dvobj.getRecord().get("BILL_ISS_YN");   //��꼭 ���� ����
        String   OPBI_DAY = dvobj.getRecord().get("OPBI_DAY");   //���� ����
        String   SONG_STUDENT = dvobj.getRecord().get("SONG_STUDENT");
        String   MNGEMSTR_HPNUM = dvobj.getRecord().get("MNGEMSTR_HPNUM");   //�濵�� �ڵ�����ȣ
        String   AERO_STUDENT = dvobj.getRecord().get("AERO_STUDENT");
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set AERO_STUDENT=:AERO_STUDENT , MNGEMSTR_HPNUM=:MNGEMSTR_HPNUM , SONG_STUDENT=:SONG_STUDENT , OPBI_DAY=:OPBI_DAY , BILL_ISS_YN=:BILL_ISS_YN , MNGEMSTR_ADDR1=:MNGEMSTR_ADDR1 , UPSO_ADDR1=:UPSO_ADDR1 , STAFF_CD=:STAFF_CD , UPSO_STAT=:UPSO_STAT , MNGEMSTR_PHONNUM=:MNGEMSTR_PHONNUM , MNGEMSTR_RESINUM=:MNGEMSTR_RESINUM , MNGEMSTR_ZIP=:MNGEMSTR_ZIP , AREA=:AREA , CONTR_NM=:CONTR_NM , UPSO_NM=REPLACE(REPLACE(:UPSO_NM_1, CHR(13), ''), CHR(10), '') , PERMMSTR_NM=:PERMMSTR_NM , PERMMSTR_RESINUM=:PERMMSTR_RESINUM , PAPER_CANC_YN=:PAPER_CANC_YN , MCHNDAESU=:MCHNDAESU , UPSO_ADDR2=:UPSO_ADDR2 , UPSO_REMAK=:UPSO_REMAK ,  \n";
        query +=" CORP_NUM=:CORP_NUM , EMAIL_ADDR=REPLACE(REPLACE(:EMAIL_ADDR_1, CHR(13), ''), CHR(10), '') , MNGEMSTR_NM=:MNGEMSTR_NM , MNGEMSTR_ADDR2=:MNGEMSTR_ADDR2 , BSCON_CD=:BSCON_CD , MNG_ZIP=:MNG_ZIP , NEW_DAY=:NEW_DAY , MAIL_RCPT=:MAIL_RCPT , PERMMSTR_PHONNUM=:PERMMSTR_PHONNUM , BIOWN_NUM=:BIOWN_NUM , BRAN_CD=:BRAN_CD , UPSO_ZIP=:UPSO_ZIP  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("BIOWN_NUM", BIOWN_NUM);               //����� ��ȣ
        sobj.setString("PERMMSTR_PHONNUM", PERMMSTR_PHONNUM);               //�㰡�� ��ȭ��ȣ
        sobj.setString("MAIL_RCPT", MAIL_RCPT);               //���� ������
        sobj.setString("NEW_DAY", NEW_DAY);               //�ű� ����
        sobj.setString("MNG_ZIP", MNG_ZIP);               //���� �����ȣ
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("MNGEMSTR_ADDR2", MNGEMSTR_ADDR2);               //�濵�� �ּ�2
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //�濵�� �̸�
        sobj.setString("EMAIL_ADDR_1", EMAIL_ADDR_1);               //�̸��� �ּ�
        sobj.setString("CORP_NUM", CORP_NUM);               //���� ��ȣ
        sobj.setString("UPSO_REMAK", UPSO_REMAK);               //���� ���
        sobj.setString("UPSO_ADDR2", UPSO_ADDR2);               //���� �ּ�2
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        sobj.setString("PAPER_CANC_YN", PAPER_CANC_YN);               //���� ��� ����
        sobj.setString("PERMMSTR_RESINUM", PERMMSTR_RESINUM);               //�㰡�� �ֹι�ȣ
        sobj.setString("PERMMSTR_NM", PERMMSTR_NM);               //�㰡�� �̸�
        sobj.setString("UPSO_NM_1", UPSO_NM_1);               //���� ��
        sobj.setString("CONTR_NM", CONTR_NM);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("AREA", AREA);               //����
        sobj.setString("MNGEMSTR_ZIP", MNGEMSTR_ZIP);               //�濵�� �����ȣ
        sobj.setString("MNGEMSTR_RESINUM", MNGEMSTR_RESINUM);               //�濵�� �ֹι�ȣ
        sobj.setString("MNGEMSTR_PHONNUM", MNGEMSTR_PHONNUM);               //�濵�� ��ȭ��ȣ
        sobj.setString("UPSO_STAT", UPSO_STAT);               //���� ����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("UPSO_ADDR1", UPSO_ADDR1);               //���� �ּ�1
        sobj.setString("MNGEMSTR_ADDR1", MNGEMSTR_ADDR1);               //�濵�� �ּ�1
        sobj.setString("BILL_ISS_YN", BILL_ISS_YN);               //��꼭 ���� ����
        sobj.setString("OPBI_DAY", OPBI_DAY);               //���� ����
        sobj.setString("SONG_STUDENT", SONG_STUDENT);
        sobj.setString("MNGEMSTR_HPNUM", MNGEMSTR_HPNUM);               //�濵�� �ڵ�����ȣ
        sobj.setString("AERO_STUDENT", AERO_STUDENT);
        return sobj;
    }
    // CHG_NUM����
    public DOBJ CALLupso_regist_onoff_SEL29(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL29");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL29");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_onoff_SEL29(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL29");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_SEL29(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(CHG_NUM),  0)  +  1,  4,  '0')  CHG_NUM  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  CHG_BRAN  =  :BRAN_CD   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  CHG_DAY  =  TO_CHAR(SYSDATE,'YYYYMMDD') ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ���һ��������Է�
    public DOBJ CALLupso_regist_onoff_INS62(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS62");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_INS62(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS62") ;
        dobj.setRetObject(rvobj);
        String message ="�뷡���� ��쿡�� �뷡�� �������� �Է��ϼž� �մϴ�.";
        dobj.setRetmsg(message);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_INS62(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CHG_DAY ="";        //���� ����
        String INS_DATE ="";        //��� �Ͻ�
        double JOIN_SEQ = 0;        //�����ε���
        double   MONPRNCFEE2 = dvobj.getRecord().getDouble("MONPRNCFEE2");   //���ؿ�����
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //������
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
        String   UPSO_GRAD = dvobj.getRecord().get("UPSO_GRAD");   //���� ���
        String   APPL_DAY = dobj.getRetObject("R").getRecord().get("OPBI_DAY");   //���� ����
        String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� ��ȣ2
        String   CHG_NUM ="9000";   //���� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSORTAL_INFO (JOIN_SEQ, APPL_DAY, CHG_BRAN, CHG_DAY, INSPRES_ID, UPSO_GRAD, MCHNDAESU, MONPRNCFEE, BSTYP_CD, INS_DATE, MONPRNCFEE2, CHG_NUM, UPSO_CD)  \n";
        query +=" values((SELECT NVL(MAX(JOIN_SEQ), 0) + 1 JOIN_SEQ FROM GIBU.TBRA_UPSORTAL_INFO ), :APPL_DAY , :CHG_BRAN , TO_CHAR(SYSDATE,'YYYYMMDD'), :INSPRES_ID , :UPSO_GRAD , :MCHNDAESU , :MONPRNCFEE , :BSTYP_CD , SYSDATE, :MONPRNCFEE2 , :CHG_NUM , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setDouble("MONPRNCFEE2", MONPRNCFEE2);               //���ؿ�����
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        sobj.setString("UPSO_GRAD", UPSO_GRAD);               //���� ���
        sobj.setString("APPL_DAY", APPL_DAY);               //���� ����
        sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ��ȣ2
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���һ�������
    public DOBJ CALLupso_regist_onoff_INS30(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS30");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_INS30(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS30") ;
        dobj.setRetObject(rvobj);
        String message ="�뷡���� ��쿡�� �뷡�� �������� �Է��ϼž� �մϴ�.";
        dobj.setRetmsg(message);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_INS30(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPL_DAY ="";        //���� ����
        String CHG_DAY ="";        //���� ����
        String INS_DATE ="";        //��� �Ͻ�
        double JOIN_SEQ = 0;        //�����ε���
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //������
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
        String   USE_TIME = dvobj.getRecord().get("USE_TIME");   //���ð�
        String   UPSO_GRAD = dvobj.getRecord().get("UPSO_GRAD");   //���� ���
        String   APPL_DAY_2 = dvobj.getRecord().get("OPBI_DAY");   //���� ����
        String   APPL_DAY_1 = dvobj.getRecord().get("OPBI_DAY");   //���� ����
        String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� ��ȣ2
        String   CHG_NUM = dobj.getRetObject("SEL29").getRecord().get("CHG_NUM");   //���� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSORTAL_INFO (JOIN_SEQ, APPL_DAY, CHG_BRAN, CHG_DAY, INSPRES_ID, UPSO_GRAD, USE_TIME, MCHNDAESU, MONPRNCFEE, BSTYP_CD, INS_DATE, CHG_NUM, UPSO_CD)  \n";
        query +=" values((SELECT NVL(MAX(JOIN_SEQ), 0) + 1 JOIN_SEQ FROM GIBU.TBRA_UPSORTAL_INFO ), DECODE(:APPL_DAY_1,NULL,TO_CHAR(SYSDATE,'YYYYMMDD'),:APPL_DAY_2), :CHG_BRAN , TO_CHAR(SYSDATE,'YYYYMMDD'), :INSPRES_ID , :UPSO_GRAD , :USE_TIME , :MCHNDAESU , :MONPRNCFEE , :BSTYP_CD , SYSDATE, :CHG_NUM , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        sobj.setString("USE_TIME", USE_TIME);               //���ð�
        sobj.setString("UPSO_GRAD", UPSO_GRAD);               //���� ���
        sobj.setString("APPL_DAY_2", APPL_DAY_2);               //���� ����
        sobj.setString("APPL_DAY_1", APPL_DAY_1);               //���� ����
        sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ��ȣ2
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �뷡��������Է�
    public DOBJ CALLupso_regist_onoff_INS63(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS63");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_INS63(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS63") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_INS63(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CHG_DAY ="";        //���� ����
        double GRAD_NUM = 0;        //�뷡�����
        String INS_DATE ="";        //��� �Ͻ�
        String   GRAD_NUM_1 = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //�뷡�����
        String   GRAD_GBN = dvobj.getRecord().get("GRAD_GBN");   //��� ����
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
        double   AREA = dobj.getRetObject("S1").getRecord().getDouble("AREA");   //���� ����
        String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� ����
        String   CHG_NUM ="9000";   //���� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_NOREBANG_INFO (INS_DATE, CHG_BRAN, INSPRES_ID, CHG_NUM, CHG_DAY, MCHNDAESU, AREA, UPSO_CD, BSTYP_CD, GRAD_GBN, GRAD_NUM)  \n";
        query +=" values(SYSDATE, :CHG_BRAN , :INSPRES_ID , :CHG_NUM , TO_CHAR(SYSDATE,'YYYYMMDD'), :MCHNDAESU , :AREA , :UPSO_CD , :BSTYP_CD , :GRAD_GBN , (SELECT NVL(MAX(GRAD_NUM), 0) + 1 GRAD_NUM FROM GIBU.TBRA_NOREBANG_INFO WHERE UPSO_CD = :GRAD_NUM_1))";
        sobj.setSql(query);
        sobj.setString("GRAD_NUM_1", GRAD_NUM_1);               //�뷡�����
        sobj.setString("GRAD_GBN", GRAD_GBN);               //��� ����
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        sobj.setDouble("AREA", AREA);               //���� ����
        sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ����
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �뷡�������
    public DOBJ CALLupso_regist_onoff_INS32(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS32");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_INS32(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS32") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_INS32(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CHG_DAY ="";        //���� ����
        double GRAD_NUM = 0;        //�뷡�����
        String INS_DATE ="";        //��� �Ͻ�
        String   GRAD_GBN = dvobj.getRecord().get("GRAD_GBN");   //��� ����
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        double   AREA = dvobj.getRecord().getDouble("AREA");   //����
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
        double   STNDAMT = dvobj.getRecord().getDouble("STNDAMT");   //���رݾ�
        String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� ����
        String   CHG_NUM = dobj.getRetObject("SEL29").getRecord().get("CHG_NUM");   //���� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_NOREBANG_INFO (INS_DATE, CHG_BRAN, INSPRES_ID, CHG_NUM, CHG_DAY, STNDAMT, MCHNDAESU, AREA, UPSO_CD, BSTYP_CD, GRAD_GBN, GRAD_NUM)  \n";
        query +=" values(SYSDATE, :CHG_BRAN , :INSPRES_ID , :CHG_NUM , TO_CHAR(SYSDATE,'YYYYMMDD'), :STNDAMT , :MCHNDAESU , :AREA , :UPSO_CD , :BSTYP_CD , :GRAD_GBN , (SELECT NVL(MAX(GRAD_NUM), 0) + 1 GRAD_NUM FROM GIBU.TBRA_NOREBANG_INFO WHERE UPSO_CD = :UPSO_CD))";
        sobj.setSql(query);
        sobj.setString("GRAD_GBN", GRAD_GBN);               //��� ����
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setDouble("AREA", AREA);               //����
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        sobj.setDouble("STNDAMT", STNDAMT);               //���رݾ�
        sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ����
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ȸ��������ν���
    public DOBJ CALLupso_regist_onoff_OSP74(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP74");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        String[]  incolumns ={"UPSO_CD","UPSO_NM","INS_STAFF"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INS_STAFF = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //��� ���
            record.put("INS_STAFF",INS_STAFF);
            ////
            String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");         //���� �ڵ�
            record.put("UPSO_CD",UPSO_CD);
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
        String   spname ="GIBU.SP_FMS_SEND_INSMEM";
        int[]    intypes={12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        robj.setName("OSP74");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���ұ⺻��������
    // ���ұ⺻������ �����Ѵ�. �� ���� ������ ���� ó���Ѵ�.
    public DOBJ CALLupso_regist_onoff_UPD14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD14");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_UPD14(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD14") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_UPD14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String EMAIL_ADDR ="";        //�̸��� �ּ�
        String MOD_DATE ="";        //���� �Ͻ�
        String UPSO_NM ="";        //���� ��
        String   PERMMSTR_PHONNUM = dvobj.getRecord().get("PERMMSTR_PHONNUM");   //�㰡�� ��ȭ��ȣ
        String   MNGEMSTR_REF_INFO = dvobj.getRecord().get("MNGEMSTR_REF_INFO");
        String   MNGEMSTR_NEW_ZIP = dvobj.getRecord().get("MNGEMSTR_NEW_ZIP");
        String   PERMMSTR_NEW_ADDR2 = dvobj.getRecord().get("PERMMSTR_NEW_ADDR2");
        String   PERMMSTR_NEW_ADDR1 = dvobj.getRecord().get("PERMMSTR_NEW_ADDR1");
        String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //�濵�� �̸�
        String   EMAIL_ADDR_1 = dobj.getRetObject("R").getRecord().get("EMAIL_ADDR");   //�̸��� �ּ�
        String   CORP_NUM = dvobj.getRecord().get("CORP_NUM");   //���� ��ȣ
        String   MNGEMSTR_REMAK = dvobj.getRecord().get("MNGEMSTR_REMAK");   //�濵�� ���
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
        String   PERMMSTR_REF_INFO = dvobj.getRecord().get("PERMMSTR_REF_INFO");
        String   UPSO_NEW_ADDR2 = dvobj.getRecord().get("UPSO_NEW_ADDR2");
        String   PAPER_CANC_YN = dvobj.getRecord().get("PAPER_CANC_YN");   //���� ��� ����
        String   UPSO_NEW_ZIP = dvobj.getRecord().get("UPSO_NEW_ZIP");
        String   UPSO_NM_1 = dobj.getRetObject("R").getRecord().get("UPSO_NM");   //���� ��
        String   CONTR_NM = dvobj.getRecord().get("CONTR_NM");
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   MNGEMSTR_PHONNUM = dvobj.getRecord().get("MNGEMSTR_PHONNUM");   //�濵�� ��ȭ��ȣ
        String   MNGEMSTR_RESINUM = dvobj.getRecord().get("MNGEMSTR_RESINUM");   //�濵�� �ֹι�ȣ
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   UPSO_REF_INFO = dvobj.getRecord().get("UPSO_REF_INFO");
        String   PERMMSTR_NEW_ZIP = dvobj.getRecord().get("PERMMSTR_NEW_ZIP");
        String   BILL_ISS_YN = dvobj.getRecord().get("BILL_ISS_YN");   //��꼭 ���� ����
        String   UPSO_PHON = dvobj.getRecord().get("UPSO_PHON");   //���� ��ȭ
        String   UPSO_NEW_ZIP1 = dvobj.getRecord().get("UPSO_NEW_ZIP1");
        String   UPSO_BD_MNG_NUM = dvobj.getRecord().get("UPSO_BD_MNG_NUM");
        String   AERO_STUDENT = dvobj.getRecord().get("AERO_STUDENT");
        String   BIOWN_NUM = dvobj.getRecord().get("BIOWN_NUM");   //����� ��ȣ
        String   MNGEMSTR_BD_MNG_NUM = dvobj.getRecord().get("MNGEMSTR_BD_MNG_NUM");
        String   MAIL_RCPT = dvobj.getRecord().get("MAIL_RCPT");   //���� ������
        String   MNG_ZIP = dvobj.getRecord().get("MNG_ZIP");   //���� �����ȣ
        String   NEW_DAY = dvobj.getRecord().get("NEW_DAY");   //�ű� ����
        String   MNGEMSTR_NEW_ADDR1 = dvobj.getRecord().get("MNGEMSTR_NEW_ADDR1");
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   UPSO_REMAK = dvobj.getRecord().get("UPSO_REMAK");   //���� ���
        String   PERMMSTR_BD_MNG_NUM = dvobj.getRecord().get("PERMMSTR_BD_MNG_NUM");
        String   MNGEMSTR_NEW_ADDR2 = dvobj.getRecord().get("MNGEMSTR_NEW_ADDR2");
        String   PERMMSTR_RESINUM = dvobj.getRecord().get("PERMMSTR_RESINUM");   //�㰡�� �ֹι�ȣ
        String   PERMMSTR_NM = dvobj.getRecord().get("PERMMSTR_NM");   //�㰡�� �̸�
        String   PERMMSTR_REMAK = dvobj.getRecord().get("PERMMSTR_REMAK");   //�㰡�� ���
        double   AREA = dvobj.getRecord().getDouble("AREA");   //����
        String   UPSO_STAT = dvobj.getRecord().get("UPSO_STAT");   //���� ����
        String   PERMMSTR_HPNUM = dvobj.getRecord().get("PERMMSTR_HPNUM");   //�㰡�� �ڵ�����ȣ
        String   MNGEMSTR_NEW_ZIP1 = dvobj.getRecord().get("MNGEMSTR_NEW_ZIP1");
        String   UPSO_NEW_ADDR1 = dvobj.getRecord().get("UPSO_NEW_ADDR1");
        String   OPBI_DAY = dvobj.getRecord().get("OPBI_DAY");   //���� ����
        String   SONG_STUDENT = dvobj.getRecord().get("SONG_STUDENT");
        String   PERMMSTR_NEW_ZIP1 = dvobj.getRecord().get("PERMMSTR_NEW_ZIP1");
        String   PAYPRES_GBN = dvobj.getRecord().get("PAYPRES_GBN");   //������ ����
        String   MNGEMSTR_HPNUM = dvobj.getRecord().get("MNGEMSTR_HPNUM");   //�濵�� �ڵ�����ȣ
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MNGEMSTR_HPNUM=:MNGEMSTR_HPNUM , MODPRES_ID=:MODPRES_ID , PAYPRES_GBN=:PAYPRES_GBN , PERMMSTR_NEW_ZIP1=:PERMMSTR_NEW_ZIP1 , SONG_STUDENT=:SONG_STUDENT , OPBI_DAY=:OPBI_DAY , UPSO_NEW_ADDR1=:UPSO_NEW_ADDR1 , MNGEMSTR_NEW_ZIP1=:MNGEMSTR_NEW_ZIP1 , PERMMSTR_HPNUM=:PERMMSTR_HPNUM , UPSO_STAT=:UPSO_STAT , AREA=:AREA , PERMMSTR_REMAK=:PERMMSTR_REMAK , PERMMSTR_NM=:PERMMSTR_NM , PERMMSTR_RESINUM=:PERMMSTR_RESINUM , MNGEMSTR_NEW_ADDR2=:MNGEMSTR_NEW_ADDR2 , PERMMSTR_BD_MNG_NUM=:PERMMSTR_BD_MNG_NUM , UPSO_REMAK=:UPSO_REMAK , BSCON_CD=:BSCON_CD , MNGEMSTR_NEW_ADDR1=:MNGEMSTR_NEW_ADDR1 , NEW_DAY=:NEW_DAY , MNG_ZIP=:MNG_ZIP , MAIL_RCPT=:MAIL_RCPT , MOD_DATE=SYSDATE , MNGEMSTR_BD_MNG_NUM=:MNGEMSTR_BD_MNG_NUM , BIOWN_NUM=:BIOWN_NUM , AERO_STUDENT=:AERO_STUDENT , UPSO_BD_MNG_NUM=:UPSO_BD_MNG_NUM , UPSO_NEW_ZIP1=:UPSO_NEW_ZIP1 , UPSO_PHON=:UPSO_PHON , BILL_ISS_YN=:BILL_ISS_YN , PERMMSTR_NEW_ZIP=:PERMMSTR_NEW_ZIP , UPSO_REF_INFO=:UPSO_REF_INFO , STAFF_CD=:STAFF_CD , MNGEMSTR_RESINUM=:MNGEMSTR_RESINUM , MNGEMSTR_PHONNUM=:MNGEMSTR_PHONNUM , CONTR_NM=:CONTR_NM , UPSO_NM=REPLACE(REPLACE(:UPSO_NM_1, CHR(13), ''), CHR(10), '') , UPSO_NEW_ZIP=:UPSO_NEW_ZIP , PAPER_CANC_YN=:PAPER_CANC_YN , UPSO_NEW_ADDR2=:UPSO_NEW_ADDR2 , PERMMSTR_REF_INFO=:PERMMSTR_REF_INFO , MCHNDAESU=:MCHNDAESU , MNGEMSTR_REMAK=:MNGEMSTR_REMAK ,  \n";
        query +=" CORP_NUM=:CORP_NUM , EMAIL_ADDR=REPLACE(REPLACE(:EMAIL_ADDR_1, CHR(13), ''), CHR(10), '') , MNGEMSTR_NM=:MNGEMSTR_NM , PERMMSTR_NEW_ADDR1=:PERMMSTR_NEW_ADDR1 , PERMMSTR_NEW_ADDR2=:PERMMSTR_NEW_ADDR2 , MNGEMSTR_NEW_ZIP=:MNGEMSTR_NEW_ZIP , MNGEMSTR_REF_INFO=:MNGEMSTR_REF_INFO , PERMMSTR_PHONNUM=:PERMMSTR_PHONNUM  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("PERMMSTR_PHONNUM", PERMMSTR_PHONNUM);               //�㰡�� ��ȭ��ȣ
        sobj.setString("MNGEMSTR_REF_INFO", MNGEMSTR_REF_INFO);
        sobj.setString("MNGEMSTR_NEW_ZIP", MNGEMSTR_NEW_ZIP);
        sobj.setString("PERMMSTR_NEW_ADDR2", PERMMSTR_NEW_ADDR2);
        sobj.setString("PERMMSTR_NEW_ADDR1", PERMMSTR_NEW_ADDR1);
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //�濵�� �̸�
        sobj.setString("EMAIL_ADDR_1", EMAIL_ADDR_1);               //�̸��� �ּ�
        sobj.setString("CORP_NUM", CORP_NUM);               //���� ��ȣ
        sobj.setString("MNGEMSTR_REMAK", MNGEMSTR_REMAK);               //�濵�� ���
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        sobj.setString("PERMMSTR_REF_INFO", PERMMSTR_REF_INFO);
        sobj.setString("UPSO_NEW_ADDR2", UPSO_NEW_ADDR2);
        sobj.setString("PAPER_CANC_YN", PAPER_CANC_YN);               //���� ��� ����
        sobj.setString("UPSO_NEW_ZIP", UPSO_NEW_ZIP);
        sobj.setString("UPSO_NM_1", UPSO_NM_1);               //���� ��
        sobj.setString("CONTR_NM", CONTR_NM);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("MNGEMSTR_PHONNUM", MNGEMSTR_PHONNUM);               //�濵�� ��ȭ��ȣ
        sobj.setString("MNGEMSTR_RESINUM", MNGEMSTR_RESINUM);               //�濵�� �ֹι�ȣ
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("UPSO_REF_INFO", UPSO_REF_INFO);
        sobj.setString("PERMMSTR_NEW_ZIP", PERMMSTR_NEW_ZIP);
        sobj.setString("BILL_ISS_YN", BILL_ISS_YN);               //��꼭 ���� ����
        sobj.setString("UPSO_PHON", UPSO_PHON);               //���� ��ȭ
        sobj.setString("UPSO_NEW_ZIP1", UPSO_NEW_ZIP1);
        sobj.setString("UPSO_BD_MNG_NUM", UPSO_BD_MNG_NUM);
        sobj.setString("AERO_STUDENT", AERO_STUDENT);
        sobj.setString("BIOWN_NUM", BIOWN_NUM);               //����� ��ȣ
        sobj.setString("MNGEMSTR_BD_MNG_NUM", MNGEMSTR_BD_MNG_NUM);
        sobj.setString("MAIL_RCPT", MAIL_RCPT);               //���� ������
        sobj.setString("MNG_ZIP", MNG_ZIP);               //���� �����ȣ
        sobj.setString("NEW_DAY", NEW_DAY);               //�ű� ����
        sobj.setString("MNGEMSTR_NEW_ADDR1", MNGEMSTR_NEW_ADDR1);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("UPSO_REMAK", UPSO_REMAK);               //���� ���
        sobj.setString("PERMMSTR_BD_MNG_NUM", PERMMSTR_BD_MNG_NUM);
        sobj.setString("MNGEMSTR_NEW_ADDR2", MNGEMSTR_NEW_ADDR2);
        sobj.setString("PERMMSTR_RESINUM", PERMMSTR_RESINUM);               //�㰡�� �ֹι�ȣ
        sobj.setString("PERMMSTR_NM", PERMMSTR_NM);               //�㰡�� �̸�
        sobj.setString("PERMMSTR_REMAK", PERMMSTR_REMAK);               //�㰡�� ���
        sobj.setDouble("AREA", AREA);               //����
        sobj.setString("UPSO_STAT", UPSO_STAT);               //���� ����
        sobj.setString("PERMMSTR_HPNUM", PERMMSTR_HPNUM);               //�㰡�� �ڵ�����ȣ
        sobj.setString("MNGEMSTR_NEW_ZIP1", MNGEMSTR_NEW_ZIP1);
        sobj.setString("UPSO_NEW_ADDR1", UPSO_NEW_ADDR1);
        sobj.setString("OPBI_DAY", OPBI_DAY);               //���� ����
        sobj.setString("SONG_STUDENT", SONG_STUDENT);
        sobj.setString("PERMMSTR_NEW_ZIP1", PERMMSTR_NEW_ZIP1);
        sobj.setString("PAYPRES_GBN", PAYPRES_GBN);               //������ ����
        sobj.setString("MNGEMSTR_HPNUM", MNGEMSTR_HPNUM);               //�濵�� �ڵ�����ȣ
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // Ŀ��
    public DOBJ CALLupso_regist_onoff_XIUD65(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD65");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_XIUD65(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD65");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_XIUD65(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" COMMIT ";
        sobj.setSql(query);
        return sobj;
    }
    // �������������������
    public DOBJ CALLupso_regist_onoff_SEL35(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL35");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL35");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_onoff_SEL35(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL35");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_SEL35(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  CHG_DAY  ,  CHG_NUM  ,  CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  JOIN_SEQ  =  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NVL(APPL_DAY,  CHG_DAY)  <=  TO_CHAR(SYSDATE,  'YYYYMMDD')  ) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������ ������ ����
    public DOBJ CALLupso_regist_onoff_OSP66(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP66");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP66");
        String[]  incolumns ={"UPSO_CD","OPBI_DAY","BSTYP_CD"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");         //���� �ڵ�
            record.put("UPSO_CD",UPSO_CD);
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
        String   spname ="GIBU.SP_SET_UPSORTAL";
        int[]    intypes={12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        robj.setName("OSP66");
        robj.Println("OSP66");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ��������������
    public DOBJ CALLupso_regist_onoff_UPD34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD34");
        VOBJ dvobj = dobj.getRetObject("SEL35");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_UPD34(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD34") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_UPD34(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");        //������ ID
        String MOD_DATE ="";        //���� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CHG_DAY = dvobj.getRecord().get("CHG_DAY");   //���� ����
        String   CHG_NUM = dvobj.getRecord().get("CHG_NUM");   //���� ��ȣ
        String   CHG_BRAN = dvobj.getRecord().get("CHG_BRAN");   //���� ����
        double   MCHNDAESU = dobj.getRetObject("R").getRecord().getDouble("MCHNDAESU");   //�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" set MCHNDAESU=:MCHNDAESU  \n";
        query +=" where CHG_BRAN=:CHG_BRAN  \n";
        query +=" and CHG_NUM=:CHG_NUM  \n";
        query +=" and CHG_DAY=:CHG_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CHG_DAY", CHG_DAY);               //���� ����
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ����
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        return sobj;
    }
    // �¿������ֱ��������
    // ���ο� �¿��� ���ֱ� ������ ����Ѵ�.
    public DOBJ CALLupso_regist_onoff_INS55(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS55");
        VOBJ dvobj = dobj.getRetObject("S3");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("INS55");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_INS55(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS55") ;
        rvobj.Println("INS55");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_INS55(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   ONOFF_GBN = dvobj.getRecord().get("ONOFF_GBN");   //�¿��� ����
        int   ACMCN_DAESU = dvobj.getRecord().getInt("ACMCN_DAESU");   //���ֱ� ���
        String   MODEL_CD = dvobj.getRecord().get("MODEL_CD");   //�� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_ONOFF_INFO (INS_DATE, MODEL_CD, INSPRES_ID, ACMCN_DAESU, ONOFF_GBN, UPSO_CD)  \n";
        query +=" values(SYSDATE, :MODEL_CD , :INSPRES_ID , :ACMCN_DAESU , :ONOFF_GBN , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //�¿��� ����
        sobj.setInt("ACMCN_DAESU", ACMCN_DAESU);               //���ֱ� ���
        sobj.setString("MODEL_CD", MODEL_CD);               //�� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �¿���������ȸ
    // �ش� ������ �¿��� ������ ��ȸ�Ѵ�.
    public DOBJ CALLupso_regist_onoff_SEL56(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL56");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL56");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_onoff_SEL56(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL56");
        rvobj.Println("SEL56");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_SEL56(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  ONOFF_GBN  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��� ����üũ
    public DOBJ CALLupso_regist_onoff_SEL36(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL36");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL36");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_onoff_SEL36(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL36");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_SEL36(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String VISIT_DAY ="";        //�湮 ����
        String   JOB_GBN ="U";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  TO_CHAR(SYSDATE,'YYYYMMDD')   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �¿�����������
    // �¿��� ������ 1���� ��� �ش� �¿��� ������ �����Ѵ�.
    public DOBJ CALLupso_regist_onoff_UPD60(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD60");
        VOBJ dvobj = dobj.getRetObject("SEL44");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("UPD60");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_UPD60(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD60") ;
        rvobj.Println("UPD60");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_UPD60(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   ONOFF_GBN = dobj.getRetObject("SEL56").getRecord().get("ONOFF_GBN");   //�¿��� ����
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ONOFF_GBN=:ONOFF_GBN , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //�¿��� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ҹ湮�ű�
    // ���Ҹ��� ����Ǿ��� ��� ���ҹ湮 ���̺� ��� ����
    public DOBJ CALLupso_regist_onoff_INS21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS21");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_INS21(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS21") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_INS21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_DAY ="";        //�湮 ����
        String VISIT_TIME ="";        //�湮 �ð�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CONSPRES ="��Ÿ";   //�����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="U";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="[���Ҹ���]";   //���
        int   VISIT_SEQ = 1;   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, VISIT_TIME, INSPRES_ID, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, TO_CHAR(SYSDATE, 'HH24MI'), :INSPRES_ID , TO_CHAR(SYSDATE,'YYYYMMDD'), :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���� ����
    public DOBJ CALLupso_regist_onoff_DEL113(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL113");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_DEL113(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL113") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_DEL113(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_NUM ="9000";   //���� ��ȣ
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" where CHG_NUM=:CHG_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ҹ湮�޸���
    public DOBJ CALLupso_regist_onoff_INS39(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS39");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_INS39(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS39") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_INS39(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_DAY ="";        //�湮 ����
        int  VISIT_NUM = 0;        //�湮 ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_NUM_1 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //�湮 ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="U";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="[���Ҹ���]"+dvobj.getRecord().get("BEFORE_UPSO_NM")+"-->"+dvobj.getRecord().get("UPSO_NM");   //���
        int   VISIT_SEQ = 1;   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , TO_CHAR(SYSDATE,'YYYYMMDD'), :JOB_GBN , (SELECT NVL(MAX(VISIT_NUM),0)+1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = TO_CHAR(SYSDATE,'YYYYMMDD') AND UPSO_CD = :VISIT_NUM_1 AND JOB_GBN = 'U' ), :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //�湮 ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // �뷡����������
    public DOBJ CALLupso_regist_onoff_DEL114(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL114");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_DEL114(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL114") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_DEL114(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_NUM ="9000";   //���� ��ȣ
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" where CHG_NUM=:CHG_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ҹ湮�޸���
    public DOBJ CALLupso_regist_onoff_INS38(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS38");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_INS38(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS38") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_INS38(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_DAY ="";        //�湮 ����
        int  VISIT_NUM = 0;        //�湮 ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_NUM_1 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //�湮 ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="U";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="[���Ҹ���]"+dvobj.getRecord().get("BEFORE_UPSO_NM")+"-->"+dvobj.getRecord().get("UPSO_NM");   //���
        int   VISIT_SEQ = 1;   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , TO_CHAR(SYSDATE,'YYYYMMDD'), :JOB_GBN , (SELECT NVL(MAX(VISIT_NUM),0)+1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = TO_CHAR(SYSDATE,'YYYYMMDD') AND UPSO_CD = :VISIT_NUM_1 AND JOB_GBN = 'U' ), :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //�湮 ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // Ŀ��
    public DOBJ CALLupso_regist_onoff_XIUD55(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD55");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_XIUD55(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD55");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_XIUD55(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" COMMIT ";
        sobj.setSql(query);
        return sobj;
    }
    // ������ ������ ����
    public DOBJ CALLupso_regist_onoff_OSP52(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP52");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP52");
        String[]  incolumns ={"UPSO_CD"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");         //���� �ڵ�
            record.put("UPSO_CD",UPSO_CD);
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
        String   spname ="GIBU.SP_MISU_DEMD_OPEN";
        int[]    intypes={12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        robj.setName("OSP52");
        robj.Println("OSP52");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �¿������� ����
    public DOBJ CALLupso_regist_onoff_XIUD41(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD41");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_XIUD41(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD41");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_XIUD41(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BEFORE_UPSO_CD = dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_ONOFF_INFO ( UPSO_CD , MODEL_CD , ONOFF_GBN , ACMCN_DAESU , INSPRES_ID , INS_DATE ) SELECT :UPSO_CD , MODEL_CD , ONOFF_GBN , ACMCN_DAESU , :INSPRES_ID , SYSDATE FROM GIBU.TBRA_UPSO_ONOFF_INFO WHERE UPSO_CD = :BEFORE_UPSO_CD";
        sobj.setSql(query);
        sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� ���ֱ����� ����
    public DOBJ CALLupso_regist_onoff_XIUD42(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD42");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_XIUD42(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD42");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_XIUD42(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BEFORE_UPSO_CD = dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_ACMCN_INFO ( UPSO_CD , MODEL_CD , ACMCN_DAESU , INSPRES_ID , INS_DATE ) SELECT :UPSO_CD , MODEL_CD , ACMCN_DAESU , :INSPRES_ID , SYSDATE FROM GIBU.TBRA_UPSO_ACMCN_INFO WHERE UPSO_CD = :BEFORE_UPSO_CD";
        sobj.setSql(query);
        sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� ���긮 ���� ����
    public DOBJ CALLupso_regist_onoff_XIUD43(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD43");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_XIUD43(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD43");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_XIUD43(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BEFORE_UPSO_CD = dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_AUBRY_INFO ( UPSO_CD , MODEL_CD , MODEL_NM , MCHN_COMPY , MCHN_COMPYNM , ACMCN_DAESU , INSPRES_ID , INS_DATE ) SELECT :UPSO_CD , MODEL_CD , MODEL_NM , MCHN_COMPY , MCHN_COMPYNM , ACMCN_DAESU , :INSPRES_ID , SYSDATE FROM GIBU.TBRA_UPSO_AUBRY_INFO WHERE UPSO_CD = :BEFORE_UPSO_CD";
        sobj.setSql(query);
        sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �¿��� ���� ����
    public DOBJ CALLupso_regist_onoff_XIUD50(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD50");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_XIUD50(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD50");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_XIUD50(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BEFORE_UPSO_CD = dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET ONOFF_GBN = ( SELECT ONOFF_GBN FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE UPSO_CD =  \n";
        query +=" :BEFORE_UPSO_CD)  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �ݿ��α׼����� �̰�
    public DOBJ CALLupso_regist_onoff_OSP51(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP51");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP51");
        String[]  incolumns ={"UPSO_CD","BEFORE_UPSO_CD","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //����� ID
            record.put("INSPRES_ID",INSPRES_ID);
            ////
            String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");         //���� �ڵ�
            record.put("UPSO_CD",UPSO_CD);
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
        String   spname ="GIBU.SP_PROC_KYLOG_OWNER_TRANS";
        int[]    intypes={12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        robj.setName("OSP51");
        robj.Println("OSP51");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���ҹ湮���
    public DOBJ CALLupso_regist_onoff_XIUD68(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD68");
        VOBJ dvobj = dobj.getRetObject("S4");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_XIUD68(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD68");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_XIUD68(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NEW_UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //�ű� �����ڵ�
        String   UPSO_CD = dobj.getRetObject("S4").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_VISIT (UPSO_CD, VISIT_DAY, VISIT_SEQ, JOB_GBN, CONSPRES, CONS_DATE, CONS_SEQ, REMAK, INSPRES_ID, INS_DATE, MODPRES_ID, MOD_DATE, VISIT_TIME) SELECT :NEW_UPSO_CD , TO_CHAR(INS_DATE, 'YYYYMMDD') AS VISIT_DAY , '1' AS VISIT_SEQ , 'V' AS JOB_GBN , '' AS CONSPRES , '' AS CONS_DATE , '' AS CONS_SEQ , '�űԾ��� ����(����)' AS REMAK , INSPRES_ID , INS_DATE , '' AS MODPRES_ID , '' AS MOD_DATE , TO_CHAR(INS_DATE, 'HH24MI') AS VISIT_TIME FROM GIBU.TMOB_NEW_UPSO WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("NEW_UPSO_CD", NEW_UPSO_CD);               //�ű� �����ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ÷�����Ͽ���Ȯ��
    public DOBJ CALLupso_regist_onoff_SEL66(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL66");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL66");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_onoff_SEL66(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL66");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_SEL66(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S4").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TMOB_NEW_UPSO_ATTACH  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ÷�����ϵ��
    public DOBJ CALLupso_regist_onoff_XIUD69(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD69");
        VOBJ dvobj = dobj.getRetObject("S4");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("XIUD69");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_XIUD69(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD69");
        rvobj.Println("XIUD69");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_XIUD69(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NEW_UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //�ű� �����ڵ�
        String   UPSO_CD = dobj.getRetObject("S4").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_VISIT_ATTCH (UPSO_CD, VISIT_DAY, VISIT_SEQ, JOB_GBN, SEQ, FILE_NM, FILE_ROUT, FILE_TYPE, FILE_TEMPNM, FILE_SIZE, FILES) SELECT :NEW_UPSO_CD , TO_CHAR(A.INS_DATE, 'YYYYMMDD') AS VISIT_DAY , '1' AS VISIT_SEQ , 'V' AS JOB_GBN , '1' AS SEQ , FILE_NM , FILE_ROUT , FILE_TYPE , FILE_TEMPNM , FILE_SIZE , FILES FROM GIBU.TMOB_NEW_UPSO A , GIBU.TMOB_NEW_UPSO_ATTACH B WHERE A.UPSO_CD = B.UPSO_CD AND A.UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("NEW_UPSO_CD", NEW_UPSO_CD);               //�ű� �����ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����ڵ� ����
    public DOBJ CALLupso_regist_onoff_XIUD66(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD66");
        VOBJ dvobj = dobj.getRetObject("S4");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("XIUD66");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_XIUD66(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD66");
        rvobj.Println("XIUD66");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_XIUD66(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NEW_UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //�ű� �����ڵ�
        String   UPSO_CD = dobj.getRetObject("S4").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TMOB_NEW_UPSO  \n";
        query +=" SET UPSO_CD = :NEW_UPSO_CD  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("NEW_UPSO_CD", NEW_UPSO_CD);               //�ű� �����ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����ڵ� ����
    public DOBJ CALLupso_regist_onoff_XIUD70(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD70");
        VOBJ dvobj = dobj.getRetObject("S4");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("XIUD70");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_XIUD70(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD70");
        rvobj.Println("XIUD70");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_XIUD70(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NEW_UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //�ű� �����ڵ�
        String   UPSO_CD = dobj.getRetObject("S4").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_MAP  \n";
        query +=" SET UPSO_CD = :NEW_UPSO_CD  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("NEW_UPSO_CD", NEW_UPSO_CD);               //�ű� �����ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �ڵ���ü ��û����Ȯ��
    public DOBJ CALLupso_regist_onoff_SEL79(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL79");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL79");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_onoff_SEL79(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL79");
        rvobj.Println("SEL79");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_SEL79(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S4").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_UPSO_AUTO_APPLICATION  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  PROC_DAY  IS  NULL   \n";
        query +=" AND  AUTO_NUM  IS  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �����ڵ� ����
    public DOBJ CALLupso_regist_onoff_XIUD84(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD84");
        VOBJ dvobj = dobj.getRetObject("S4");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("XIUD84");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_XIUD84(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD84");
        rvobj.Println("XIUD84");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_XIUD84(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NEW_UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //�ű� �����ڵ�
        String   UPSO_CD = dobj.getRetObject("S4").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_AUTO_APPLICATION  \n";
        query +=" SET UPSO_CD = :NEW_UPSO_CD  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND PROC_DAY IS NULL  \n";
        query +=" AND AUTO_NUM IS NULL";
        sobj.setSql(query);
        sobj.setString("NEW_UPSO_CD", NEW_UPSO_CD);               //�ű� �����ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �ڵ���ü��û�� �����ڵ庯��
    public DOBJ CALLupso_regist_onoff_XIUD81(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD81");
        VOBJ dvobj = dobj.getRetObject("S4");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_XIUD81(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD81");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_XIUD81(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NEW_UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //�ű� �����ڵ�
        String   UPSO_CD = dobj.getRetObject("S4").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_AUTO_DOC_ATTCH  \n";
        query +=" SET UPSO_CD = :NEW_UPSO_CD  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("NEW_UPSO_CD", NEW_UPSO_CD);               //�ű� �����ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �¿�����������
    // �¿��� ������ 1���� ��� �ش� �¿��� ������ �����Ѵ�.
    public DOBJ CALLupso_regist_onoff_UPD63(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD63");
        VOBJ dvobj = dobj.getRetObject("SEL44");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("UPD63");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_UPD63(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD63") ;
        rvobj.Println("UPD63");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_UPD63(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   ONOFF_GBN ="O";   //�¿��� ����
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ONOFF_GBN=:ONOFF_GBN , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //�¿��� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �¿�����������
    // �¿��� ������ 1���� ��� �ش� �¿��� ������ �����Ѵ�.
    public DOBJ CALLupso_regist_onoff_UPD62(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD62");
        VOBJ dvobj = dobj.getRetObject("SEL44");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("UPD62");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_UPD62(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD62") ;
        rvobj.Println("UPD62");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_UPD62(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   ONOFF_GBN ="";   //�¿��� ����
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ONOFF_GBN=:ONOFF_GBN , MOD_DATE=SYSDATE  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("ONOFF_GBN", ONOFF_GBN);               //�¿��� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �ڵ���ü������ �߼۹��
    public DOBJ CALLupso_regist_onoff_MIUD55(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD55");
        VOBJ dvobj = dobj.getRetObject("S2");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
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
                dobj  = CALLupso_regist_onoff_UPD59(Conn, dobj);           //  �ڵ���ü������ ���Ź��
            }
        }
        return dobj;
    }
    // �ڵ���ü������ ���Ź��
    public DOBJ CALLupso_regist_onoff_UPD59(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD59");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_UPD59(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD59") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_UPD59(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   EMAIL = dvobj.getRecord().get("EMAIL");   //�̸���
        int   AUTO_NUM = dvobj.getRecord().getInt("AUTO_NUM");   //�Ϸ� ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   GBN = dvobj.getRecord().get("GBN");   //����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO  \n";
        query +=" set GBN=:GBN , EMAIL=:EMAIL  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and AUTO_NUM=:AUTO_NUM";
        sobj.setSql(query);
        sobj.setString("EMAIL", EMAIL);               //�̸���
        sobj.setInt("AUTO_NUM", AUTO_NUM);               //�Ϸ� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("GBN", GBN);               //����
        return sobj;
    }
    // ���һ�������ȸ
    // ���ǿ� �´� ���һ������� ��ȸ�Ѵ�.
    public DOBJ CALLupso_regist_onoff_SEL17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL17");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL17");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_onoff_SEL17(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL17");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_SEL17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String  UPSO_CD="";          //���� �ڵ�
        if( ( dobj.getRetObject("S").getRecord().get("IUDFLAG").equals("I") ))
        {
            UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");
        }
        else
        {
            UPSO_CD = dobj.getRetObject("S6").getRecord().get("UPSO_CD");
        }
        String   BRAN_CD = dobj.getRetObject("S6").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.UPSO_NM  ,  A.BIOWN_NUM  ,  A.UPSO_PHON  ,  A.UPSO_ZIP  ,  A.UPSO_ADDR1  ,  A.UPSO_ADDR2  ,  A.MNGEMSTR_NM  ,  A.MNGEMSTR_PHONNUM  ,  A.MNGEMSTR_RESINUM  ,  A.MNGEMSTR_HPNUM  ,  A.MNGEMSTR_ZIP  ,  A.MNGEMSTR_ADDR1  ,  A.MNGEMSTR_ADDR2  ,  A.MNGEMSTR_REMAK  ,  A.PERMMSTR_NM  ,  A.PERMMSTR_PHONNUM  ,  A.PERMMSTR_RESINUM  ,  A.PERMMSTR_HPNUM  ,  A.PERMMSTR_ZIP  ,  A.PERMMSTR_ADDR1  ,  A.PERMMSTR_ADDR2  ,  A.PERMMSTR_REMAK  ,  A.OPBI_DAY  ,  A.PAYPRES_GBN  ,  A.NEW_DAY  ,  A.MAIL_RCPT  ,  A.PAPER_CANC_YN  ,  A.STAFF_CD  ,  D.HAN_NM  STAFF_NM  ,  A.UPSO_STAT  ,  A.BEFORE_UPSO_CD  ,  TRIM(B.BSTYP_CD)  ||  B.UPSO_GRAD  GRAD  ,  TRIM(B.BSTYP_CD)  BSTYP_CD  ,  B.UPSO_GRAD  ,  B.MONPRNCFEE  ,  B.USE_TIME  ,  TRIM(B.B_BSTYP_CD)  ||  B.B_UPSO_GRAD  B_GRAD  ,  B.B_BSTYP_CD  ,  B.B_UPSO_GRAD  ,  B.B_MONPRNCFEE  ,  B.B_USE_TIME  ,  B.GRADNM  ,  B.B_GRADNM  ,  B.CHG_DAY  ,  B.CHG_NUM  ,  B.CHG_BRAN  ,  B.B_CHG_DAY  ,  B.B_CHG_NUM  ,  B.B_CHG_BRAN  ,  TO_CHAR(A.INS_DATE,'YYYYMMDD')  INS_DATE  ,  B.B_UPSO_NM  ,  C.MCHNDAESU  ,  C.B_MCHNDAESU  ,  DECODE(A.CLSBS_YRMN,  NULL,  A.CLSBS_YRMN,  A.CLSBS_YRMN  ||  '01')  CLSBS_YRMN  ,  A.CLIENT_NUM  ,  A.BSCON_CD  ,  E.BSCONHAN_NM  ,  A.BILL_ISS_YN  ,  A.UPSO_REMAK  ,  A.BRAN_CD  ,  A.MNG_ZIP  ,  A.CORP_NUM  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  MAX(DECODE(DUMMY,  '1',  UPSO_CD  ))  UPSO_CD  ,  MAX(DECODE(DUMMY,  '1',  UPSO_GRAD  ))  UPSO_GRAD  ,  MAX(DECODE(DUMMY,  '1',  MONPRNCFEE))  MONPRNCFEE  ,  MAX(DECODE(DUMMY,  '1',  USE_TIME  ))  USE_TIME  ,  MAX(DECODE(DUMMY,  '1',  BSTYP_CD  ))  BSTYP_CD  ,  MAX(DECODE(DUMMY,  '1',  GRADNM  ))  GRADNM  ,  MAX(DECODE(DUMMY,  '1',  CHG_DAY))  CHG_DAY  ,  MAX(DECODE(DUMMY,  '1',  CHG_NUM))  CHG_NUM  ,  MAX(DECODE(DUMMY,  '1',  CHG_BRAN  ))  CHG_BRAN  ,  MAX(DECODE(DUMMY,  '2',  UPSO_CD  ))  B_UPSO_CD  ,  MAX(DECODE(DUMMY,  '2',  UPSO_GRAD  ))  B_UPSO_GRAD  ,  MAX(DECODE(DUMMY,  '2',  MONPRNCFEE))  B_MONPRNCFEE  ,  MAX(DECODE(DUMMY,  '2',  USE_TIME  ))  B_USE_TIME  ,  MAX(DECODE(DUMMY,  '2',  BSTYP_CD  ))  B_BSTYP_CD  ,  MAX(DECODE(DUMMY,  '2',  GRADNM  ))  B_GRADNM  ,  MAX(DECODE(DUMMY,  '2',  UPSO_NM  ))  B_UPSO_NM  ,  MAX(DECODE(DUMMY,  '2',  CHG_DAY))  B_CHG_DAY  ,  MAX(DECODE(DUMMY,  '2',  CHG_NUM))  B_CHG_NUM  ,  MAX(DECODE(DUMMY,  '2',  CHG_BRAN  ))  B_CHG_BRAN  FROM  (   \n";
        query +=" SELECT  *  FROM  (   \n";
        query +=" SELECT  '1'  DUMMY  ,  A.UPSO_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.USE_TIME  ,  A.BSTYP_CD  ,  ''  UPSO_NM  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.BSTYP_CD  =  A.BSTYP_CD   \n";
        query +=" AND  B.GRAD_GBN  =  A.UPSO_GRAD  ORDER  BY  A.CHG_DAY  DESC  ,A.CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  UNION  ALL   \n";
        query +=" SELECT  *  FROM  (   \n";
        query +=" SELECT  '2'  DUMMY  ,  A.UPSO_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.USE_TIME  ,  A.BSTYP_CD  ,  C.UPSO_NM  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  (   \n";
        query +=" SELECT  BEFORE_UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD)   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  B.BSTYP_CD  =  A.BSTYP_CD   \n";
        query +=" AND  B.GRAD_GBN  =  A.UPSO_GRAD  ORDER  BY  A.CHG_DAY  DESC  ,A.CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  )  )  B  ,  (   \n";
        query +=" SELECT  MAX(MCHNDAESU)  MCHNDAESU  ,  MAX(B_MCHNDAESU)  B_MCHNDAESU  ,  MAX(UPSO_CD)  UPSO_CD  FROM  (   \n";
        query +=" SELECT  MCHNDAESU  MCHNDAESU  ,  NULL  B_MCHNDAESU  ,  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  NULL  ,  MCHNDAESU  B_MCHNDAESU  ,  NULL  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  (   \n";
        query +=" SELECT  BEFORE_UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD  )  )  )  C  ,  INSA.TINS_MST01  D  ,  FIDU.TLEV_BSCON  E  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  B.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  C.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  D.STAFF_NUM  (+)  =  A.STAFF_CD   \n";
        query +=" AND  E.BSCON_CD(+)  =  A.BSCON_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �뷡���
    public DOBJ CALLupso_regist_onoff_SEL24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL24");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL24");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_onoff_SEL24(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL24");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_SEL24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_BRAN = dobj.getRetObject("SEL17").getRecord().get("CHG_BRAN");   //���� ����
        String   CHG_NUM = dobj.getRetObject("SEL17").getRecord().get("CHG_NUM");   //���� ��ȣ
        String   CHG_DAY = dobj.getRetObject("SEL17").getRecord().get("CHG_DAY");   //���� ����
        String  UPSO_CD="";          //���� �ڵ�
        if( ( dobj.getRetObject("S").getRecord().get("IUDFLAG").equals("I") ))
        {
            UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");
        }
        else
        {
            UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");
        }
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(A.BSTYP_CD)  ||  A.GRAD_GBN  GRAD  ,  A.AREA  ,  A.MCHNDAESU  ,  B.STNDAMT  ,  B.GRADNM  ,  A.MCHNDAESU  *  B.STNDAMT  AMT  FROM  GIBU.TBRA_NOREBANG_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  C.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  A.GRAD_GBN  =  B.GRAD_GBN  AND	A.CHG_DAY  =  :CHG_DAY  AND	A.CHG_NUM  =  :CHG_NUM  AND	A.CHG_BRAN  =  :CHG_BRAN ";
        sobj.setSql(query);
        sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ����
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("CHG_DAY", CHG_DAY);               //���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // û���߼۱���
    public DOBJ CALLupso_regist_onoff_MIUD79(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD79");
        VOBJ dvobj = dobj.getRetObject("S5");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
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
                dobj  = CALLupso_regist_onoff_XIUD80(Conn, dobj);           //  û���߼۱��� ����
            }
        }
        return dobj;
    }
    // û���߼۱��� ����
    public DOBJ CALLupso_regist_onoff_XIUD80(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD80");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_onoff_XIUD80(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD80");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_onoff_XIUD80(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GBN = dobj.getRetObject("R").getRecord().get("GBN");   //����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TFMS_UPSO  \n";
        query +=" SET GBN = :GBN , MOD_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND USE_YN = 'Y'  \n";
        query +=" AND GBN IS NOT NULL";
        sobj.setSql(query);
        sobj.setString("GBN", GBN);               //����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$upso_regist_onoff
    //##**$$upso_regist_ky
    /*
    */
    public DOBJ CTLupso_regist_ky(DOBJ dobj)
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
            dobj  = CALLupso_regist_ky_MIUD1(Conn, dobj);           //  ���ҵ��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_regist_ky_MIUD55(Conn, dobj);           //  �ڵ���ü������ �߼۹��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_regist_ky_SEL17(Conn, dobj);           //  ���һ�������ȸ
            if( dobj.getRetObject("SEL17").getRecordCnt() > 0 && dobj.getRetObject("SEL17").getRecord().get("BSTYP_CD").equals("o"))
            {
                dobj  = CALLupso_regist_ky_SEL24(Conn, dobj);           //  �뷡���
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
    public DOBJ CTLupso_regist_ky( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_regist_ky_MIUD1(Conn, dobj);           //  ���ҵ��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupso_regist_ky_MIUD55(Conn, dobj);           //  �ڵ���ü������ �߼۹��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupso_regist_ky_SEL17(Conn, dobj);           //  ���һ�������ȸ
        if( dobj.getRetObject("SEL17").getRecordCnt() > 0 && dobj.getRetObject("SEL17").getRecord().get("BSTYP_CD").equals("o"))
        {
            dobj  = CALLupso_regist_ky_SEL24(Conn, dobj);           //  �뷡���
        }
        return dobj;
    }
    // ���ҵ��
    public DOBJ CALLupso_regist_ky_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MIUD1");
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_regist_ky_SEL44(Conn, dobj);           //  �����ڵ����
                dobj  = CALLupso_regist_ky_UPD44(Conn, dobj);           //  �Ϸù�ȣ����
                dobj  = CALLupso_regist_ky_INS6(Conn, dobj);           //  �⺻�����Է�
                if( dobj.getRetObject("R").getRecord().get("UPSO_STAT").equals("1") && ( dobj.getRetObject("R").getRecord().get("BSTYP_CD").equals(dvobj.getRecord().get("NULL")) || dobj.getRetObject("R").getRecord().get("UPSO_GRAD").equals(dvobj.getRecord().get("NULL")) ))
                {
                    dobj.setRtncode(9);
                    if(dobj.getRtncode() == 9)
                    {
                        String message ="���� ���ĵ���� ��� ������ ����� �Է��ϼž� �մϴ�.";
                        dobj.setRetmsg(message);
                        Conn.rollback();
                        return dobj;
                    }
                }
                else if(!dobj.getRetObject("R").getRecord().get("BSTYP_CD").equals("") && !dobj.getRetObject("R").getRecord().get("UPSO_GRAD").equals(""))
                {
                    dobj  = CALLupso_regist_ky_INS62(Conn, dobj);           //  ���һ��������Է�
                    if( dobj.getRetObject("R").getRecord().get("BSTYP_CD").equals("o"))
                    {
                        dobj  = CALLupso_regist_ky_INS63(Conn, dobj);           //  �뷡��������Է�
                        dobj  = CALLupso_regist_ky_XIUD65(Conn, dobj);           //  Ŀ��
                        dobj  = CALLupso_regist_ky_OSP66(Conn, dobj);           //  ������ ������ ����
                        dobj  = CALLupso_regist_ky_DEL113(Conn, dobj);           //  ���� ����
                        dobj  = CALLupso_regist_ky_DEL114(Conn, dobj);           //  �뷡����������
                        dobj  = CALLupso_regist_ky_XIUD55(Conn, dobj);           //  Ŀ��
                        dobj  = CALLupso_regist_ky_OSP52(Conn, dobj);           //  ������ ������ ����
                        if(!dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD").equals(""))
                        {
                            dobj  = CALLupso_regist_ky_XIUD41(Conn, dobj);           //  ���� �¿������� ����
                            dobj  = CALLupso_regist_ky_XIUD42(Conn, dobj);           //  ���� ���ֱ����� ����
                            dobj  = CALLupso_regist_ky_XIUD43(Conn, dobj);           //  ���� ���긮 ���� ����
                            dobj  = CALLupso_regist_ky_XIUD50(Conn, dobj);           //  ���� �¿��� ���� ����
                            dobj  = CALLupso_regist_ky_OSP51(Conn, dobj);           //  �ݿ��α׼����� �̰�
                        }
                    }
                    else
                    {
                        dobj  = CALLupso_regist_ky_XIUD65(Conn, dobj);           //  Ŀ��
                        dobj  = CALLupso_regist_ky_OSP66(Conn, dobj);           //  ������ ������ ����
                        dobj  = CALLupso_regist_ky_DEL113(Conn, dobj);           //  ���� ����
                        dobj  = CALLupso_regist_ky_DEL114(Conn, dobj);           //  �뷡����������
                        dobj  = CALLupso_regist_ky_XIUD55(Conn, dobj);           //  Ŀ��
                        dobj  = CALLupso_regist_ky_OSP52(Conn, dobj);           //  ������ ������ ����
                        if(!dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD").equals(""))
                        {
                            dobj  = CALLupso_regist_ky_XIUD41(Conn, dobj);           //  ���� �¿������� ����
                            dobj  = CALLupso_regist_ky_XIUD42(Conn, dobj);           //  ���� ���ֱ����� ����
                            dobj  = CALLupso_regist_ky_XIUD43(Conn, dobj);           //  ���� ���긮 ���� ����
                            dobj  = CALLupso_regist_ky_XIUD50(Conn, dobj);           //  ���� �¿��� ���� ����
                            dobj  = CALLupso_regist_ky_OSP51(Conn, dobj);           //  �ݿ��α׼����� �̰�
                        }
                    }
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_regist_ky_SEL25(Conn, dobj);           //  ���һ���Ȯ��
                if( dobj.getRetObject("SEL25").getRecord().get("UPSO_STAT").equals("2"))
                {
                    dobj  = CALLupso_regist_ky_DEL27(Conn, dobj);           //  ������������
                    dobj  = CALLupso_regist_ky_DEL34(Conn, dobj);           //  �뷡����������
                    dobj  = CALLupso_regist_ky_UPD28(Conn, dobj);           //  ���ұ⺻��������
                    dobj  = CALLupso_regist_ky_SEL29(Conn, dobj);           //  CHG_NUM����
                    dobj  = CALLupso_regist_ky_INS30(Conn, dobj);           //  ���һ�������
                    if(( dobj.getRetObject("R").getRecord().get("BSTYP_CD").equals("o") && dobj.getRetObject("INS30").getRecordCnt() > 0 ) && !dobj.getRetObject("S1").getRecord().get("BSTYP_CD").equals("") && !dobj.getRetObject("S1").getRecord().get("GRAD_GBN").equals(""))
                    {
                        dobj  = CALLupso_regist_ky_INS32(Conn, dobj);           //  �뷡�������
                    }
                }
                else
                {
                    dobj  = CALLupso_regist_ky_UPD14(Conn, dobj);           //  ���ұ⺻��������
                    dobj  = CALLupso_regist_ky_SEL35(Conn, dobj);           //  �������������������
                    dobj  = CALLupso_regist_ky_UPD34(Conn, dobj);           //  ��������������
                    if(!dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_NM").equals(""))
                    {
                        dobj  = CALLupso_regist_ky_SEL36(Conn, dobj);           //  ��� ����üũ
                        if( dobj.getRetObject("SEL36").getRecord().getDouble("CNT") == 0)
                        {
                            dobj  = CALLupso_regist_ky_INS21(Conn, dobj);           //  ���ҹ湮�ű�
                            dobj  = CALLupso_regist_ky_INS39(Conn, dobj);           //  ���ҹ湮�޸���
                        }
                        else
                        {
                            dobj  = CALLupso_regist_ky_INS38(Conn, dobj);           //  ���ҹ湮�޸���
                        }
                    }
                }
            }
        }
        return dobj;
    }
    // �����ڵ����
    // ���κ� �����ڵ��� max��
    public DOBJ CALLupso_regist_ky_SEL44(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL44");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL44");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_ky_SEL44(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL44");
        rvobj.Println("SEL44");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_SEL44(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(SNUM+1,7,'0')  SNUM  ,  SNUM_TYPE  ,  CDCLASS  ,  LPAD(SNUM+1,7,'0')  ||  CDCLASS  N_UPSO_CD  FROM  GIBU.TBRA_SNUM  WHERE  SNUM_TYPE  =  'U'   \n";
        query +=" AND  CDCLASS  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ���һ���Ȯ��
    // ������ ��� ���¸� Ȯ���Ѵ٤�. 1: ���ĵ�� 2: �����
    public DOBJ CALLupso_regist_ky_SEL25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL25");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL25");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_ky_SEL25(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL25");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_SEL25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_STAT  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �Ϸù�ȣ����
    // ���κ� �ϷĹ�ȣ(MAX��) ����
    public DOBJ CALLupso_regist_ky_UPD44(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD44");
        VOBJ dvobj = dobj.getRetObject("SEL44");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_UPD44(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD44") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_UPD44(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   SNUM_TYPE = dvobj.getRecord().get("SNUM_TYPE");
        String   CDCLASS = dvobj.getRecord().get("CDCLASS");
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   SNUM = dobj.getRetObject("SEL44").getRecord().get("SNUM");   //�Ϸù�ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_SNUM  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MOD_DATE=SYSDATE , SNUM=:SNUM  \n";
        query +=" where CDCLASS=:CDCLASS  \n";
        query +=" and SNUM_TYPE=:SNUM_TYPE";
        sobj.setSql(query);
        sobj.setString("SNUM_TYPE", SNUM_TYPE);
        sobj.setString("CDCLASS", CDCLASS);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("SNUM", SNUM);               //�Ϸù�ȣ
        return sobj;
    }
    // �⺻�����Է�
    public DOBJ CALLupso_regist_ky_INS6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_INS6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_INS6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CLIENT_NUM ="";        //�� ��ȣ
        String INS_DATE ="";        //��� �Ͻ�
        String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //���� �����ȣ
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   PERMMSTR_PHONNUM = dvobj.getRecord().get("PERMMSTR_PHONNUM");   //�㰡�� ��ȭ��ȣ
        String   MNGEMSTR_REF_INFO = dvobj.getRecord().get("MNGEMSTR_REF_INFO");
        String   MNGEMSTR_NEW_ZIP = dvobj.getRecord().get("MNGEMSTR_NEW_ZIP");
        String   PERMMSTR_NEW_ADDR2 = dvobj.getRecord().get("PERMMSTR_NEW_ADDR2");
        String   PERMMSTR_NEW_ADDR1 = dvobj.getRecord().get("PERMMSTR_NEW_ADDR1");
        String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //�濵�� �̸�
        String   EMAIL_ADDR = dvobj.getRecord().get("EMAIL_ADDR");   //�̸��� �ּ�
        String   CORP_NUM = dvobj.getRecord().get("CORP_NUM");   //���� ��ȣ
        String   MNGEMSTR_REMAK = dvobj.getRecord().get("MNGEMSTR_REMAK");   //�濵�� ���
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
        String   PERMMSTR_REF_INFO = dvobj.getRecord().get("PERMMSTR_REF_INFO");
        String   UPSO_NEW_ADDR2 = dvobj.getRecord().get("UPSO_NEW_ADDR2");
        String   PAPER_CANC_YN = dvobj.getRecord().get("PAPER_CANC_YN");   //���� ��� ����
        String   UPSO_NEW_ZIP = dvobj.getRecord().get("UPSO_NEW_ZIP");
        String   UPSO_NM = dvobj.getRecord().get("UPSO_NM");   //���� ��
        String   MNGEMSTR_PHONNUM = dvobj.getRecord().get("MNGEMSTR_PHONNUM");   //�濵�� ��ȭ��ȣ
        String   MNGEMSTR_RESINUM = dvobj.getRecord().get("MNGEMSTR_RESINUM");   //�濵�� �ֹι�ȣ
        String   MNGEMSTR_ZIP = dvobj.getRecord().get("MNGEMSTR_ZIP");   //�濵�� �����ȣ
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   UPSO_REF_INFO = dvobj.getRecord().get("UPSO_REF_INFO");
        String   BILL_ISS_YN = dvobj.getRecord().get("BILL_ISS_YN");   //��꼭 ���� ����
        String   PERMMSTR_NEW_ZIP = dvobj.getRecord().get("PERMMSTR_NEW_ZIP");
        String   UPSO_PHON = dvobj.getRecord().get("UPSO_PHON");   //���� ��ȭ
        String   PERMMSTR_ZIP = dvobj.getRecord().get("PERMMSTR_ZIP");   //�㰡�� �����ȣ
        String   BEFORE_UPSO_CD = dvobj.getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
        String   UPSO_BD_MNG_NUM = dvobj.getRecord().get("UPSO_BD_MNG_NUM");
        String   UPSO_NEW_ZIP1 = dvobj.getRecord().get("UPSO_NEW_ZIP1");
        String   BIOWN_NUM = dvobj.getRecord().get("BIOWN_NUM");   //����� ��ȣ
        String   MNGEMSTR_BD_MNG_NUM = dvobj.getRecord().get("MNGEMSTR_BD_MNG_NUM");
        String   MAIL_RCPT = dvobj.getRecord().get("MAIL_RCPT");   //���� ������
        String   MNG_ZIP = dvobj.getRecord().get("MNG_ZIP");   //���� �����ȣ
        String   MNGEMSTR_NEW_ADDR1 = dvobj.getRecord().get("MNGEMSTR_NEW_ADDR1");
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   UPSO_REMAK = dvobj.getRecord().get("UPSO_REMAK");   //���� ���
        String   PERMMSTR_BD_MNG_NUM = dvobj.getRecord().get("PERMMSTR_BD_MNG_NUM");
        String   MNGEMSTR_NEW_ADDR2 = dvobj.getRecord().get("MNGEMSTR_NEW_ADDR2");
        String   PERMMSTR_RESINUM = dvobj.getRecord().get("PERMMSTR_RESINUM");   //�㰡�� �ֹι�ȣ
        String   PERMMSTR_NM = dvobj.getRecord().get("PERMMSTR_NM");   //�㰡�� �̸�
        String   CLIENT_NUM_1 = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //�� ��ȣ
        String   PERMMSTR_REMAK = dvobj.getRecord().get("PERMMSTR_REMAK");   //�㰡�� ���
        double   AREA = dvobj.getRecord().getDouble("AREA");   //����
        String   UPSO_STAT = dvobj.getRecord().get("UPSO_STAT");   //���� ����
        String   PERMMSTR_HPNUM = dvobj.getRecord().get("PERMMSTR_HPNUM");   //�㰡�� �ڵ�����ȣ
        String   UPSO_NEW_ADDR1 = dvobj.getRecord().get("UPSO_NEW_ADDR1");
        String   OPBI_DAY = dvobj.getRecord().get("OPBI_DAY");   //���� ����
        String   PAYPRES_GBN = dvobj.getRecord().get("PAYPRES_GBN");   //������ ����
        String   MNGEMSTR_HPNUM = dvobj.getRecord().get("MNGEMSTR_HPNUM");   //�濵�� �ڵ�����ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   MNGEMSTR_ADDR1 = dobj.getRetObject("R").getRecord().get("MNGEMSTR_ADDR");   //�濵�� �ּ�1
        String   MNGEMSTR_ADDR2 ="";   //�濵�� �ּ�2
        String   PERMMSTR_ADDR1 = dobj.getRetObject("R").getRecord().get("PREMMSTR_ADDR");   //�㰡�� �ּ�1
        String   PERMMSTR_ADDR2 ="";   //�㰡�� �ּ�2
        String   UPSO_ADDR1 = dobj.getRetObject("R").getRecord().get("UPSO_ADDR");   //���� �ּ�1
        String   UPSO_ADDR2 ="";   //���� �ּ�2
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //TEMP1
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO (MNGEMSTR_HPNUM, PAYPRES_GBN, OPBI_DAY, UPSO_NEW_ADDR1, MNGEMSTR_ADDR1, INS_DATE, PERMMSTR_HPNUM, UPSO_STAT, AREA, PERMMSTR_REMAK, CLIENT_NUM, PERMMSTR_NM, PERMMSTR_RESINUM, MNGEMSTR_NEW_ADDR2, PERMMSTR_BD_MNG_NUM, UPSO_REMAK, MNGEMSTR_ADDR2, BSCON_CD, MNGEMSTR_NEW_ADDR1, MNG_ZIP, MAIL_RCPT, PERMMSTR_ADDR1, MNGEMSTR_BD_MNG_NUM, BIOWN_NUM, UPSO_NEW_ZIP1, UPSO_BD_MNG_NUM, BEFORE_UPSO_CD, INSPRES_ID, PERMMSTR_ZIP, UPSO_PHON, PERMMSTR_NEW_ZIP, BILL_ISS_YN, UPSO_REF_INFO, UPSO_ADDR1, STAFF_CD, MNGEMSTR_ZIP, MNGEMSTR_RESINUM, MNGEMSTR_PHONNUM, UPSO_CD, UPSO_NM, UPSO_NEW_ZIP, PAPER_CANC_YN, UPSO_NEW_ADDR2, PERMMSTR_REF_INFO, UPSO_ADDR2, MCHNDAESU, MNGEMSTR_REMAK, CORP_NUM, EMAIL_ADDR, PERMMSTR_ADDR2, MNGEMSTR_NM, PERMMSTR_NEW_ADDR1, PERMMSTR_NEW_ADDR2, MNGEMSTR_NEW_ZIP, MNGEMSTR_REF_INFO, PERMMSTR_PHONNUM, BRAN_CD, UPSO_ZIP)  \n";
        query +=" values(:MNGEMSTR_HPNUM , :PAYPRES_GBN , :OPBI_DAY , :UPSO_NEW_ADDR1 , :MNGEMSTR_ADDR1 , SYSDATE, :PERMMSTR_HPNUM , :UPSO_STAT , :AREA , :PERMMSTR_REMAK , GIBU.FT_GET_AUTO_CLIENTNUM(:CLIENT_NUM_1), :PERMMSTR_NM , :PERMMSTR_RESINUM , :MNGEMSTR_NEW_ADDR2 , :PERMMSTR_BD_MNG_NUM , :UPSO_REMAK , :MNGEMSTR_ADDR2 , :BSCON_CD , :MNGEMSTR_NEW_ADDR1 , :MNG_ZIP , :MAIL_RCPT , :PERMMSTR_ADDR1 , :MNGEMSTR_BD_MNG_NUM , :BIOWN_NUM , :UPSO_NEW_ZIP1 , :UPSO_BD_MNG_NUM , :BEFORE_UPSO_CD , :INSPRES_ID , :PERMMSTR_ZIP , :UPSO_PHON , :PERMMSTR_NEW_ZIP , :BILL_ISS_YN , :UPSO_REF_INFO , :UPSO_ADDR1 , :STAFF_CD , :MNGEMSTR_ZIP , :MNGEMSTR_RESINUM , :MNGEMSTR_PHONNUM , :UPSO_CD , :UPSO_NM , :UPSO_NEW_ZIP , :PAPER_CANC_YN , :UPSO_NEW_ADDR2 , :PERMMSTR_REF_INFO , :UPSO_ADDR2 , :MCHNDAESU , :MNGEMSTR_REMAK , :CORP_NUM , :EMAIL_ADDR , :PERMMSTR_ADDR2 , :MNGEMSTR_NM , :PERMMSTR_NEW_ADDR1 , :PERMMSTR_NEW_ADDR2 , :MNGEMSTR_NEW_ZIP , :MNGEMSTR_REF_INFO , :PERMMSTR_PHONNUM , :BRAN_CD , :UPSO_ZIP )";
        sobj.setSql(query);
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("PERMMSTR_PHONNUM", PERMMSTR_PHONNUM);               //�㰡�� ��ȭ��ȣ
        sobj.setString("MNGEMSTR_REF_INFO", MNGEMSTR_REF_INFO);
        sobj.setString("MNGEMSTR_NEW_ZIP", MNGEMSTR_NEW_ZIP);
        sobj.setString("PERMMSTR_NEW_ADDR2", PERMMSTR_NEW_ADDR2);
        sobj.setString("PERMMSTR_NEW_ADDR1", PERMMSTR_NEW_ADDR1);
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //�濵�� �̸�
        sobj.setString("EMAIL_ADDR", EMAIL_ADDR);               //�̸��� �ּ�
        sobj.setString("CORP_NUM", CORP_NUM);               //���� ��ȣ
        sobj.setString("MNGEMSTR_REMAK", MNGEMSTR_REMAK);               //�濵�� ���
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        sobj.setString("PERMMSTR_REF_INFO", PERMMSTR_REF_INFO);
        sobj.setString("UPSO_NEW_ADDR2", UPSO_NEW_ADDR2);
        sobj.setString("PAPER_CANC_YN", PAPER_CANC_YN);               //���� ��� ����
        sobj.setString("UPSO_NEW_ZIP", UPSO_NEW_ZIP);
        sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
        sobj.setString("MNGEMSTR_PHONNUM", MNGEMSTR_PHONNUM);               //�濵�� ��ȭ��ȣ
        sobj.setString("MNGEMSTR_RESINUM", MNGEMSTR_RESINUM);               //�濵�� �ֹι�ȣ
        sobj.setString("MNGEMSTR_ZIP", MNGEMSTR_ZIP);               //�濵�� �����ȣ
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("UPSO_REF_INFO", UPSO_REF_INFO);
        sobj.setString("BILL_ISS_YN", BILL_ISS_YN);               //��꼭 ���� ����
        sobj.setString("PERMMSTR_NEW_ZIP", PERMMSTR_NEW_ZIP);
        sobj.setString("UPSO_PHON", UPSO_PHON);               //���� ��ȭ
        sobj.setString("PERMMSTR_ZIP", PERMMSTR_ZIP);               //�㰡�� �����ȣ
        sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
        sobj.setString("UPSO_BD_MNG_NUM", UPSO_BD_MNG_NUM);
        sobj.setString("UPSO_NEW_ZIP1", UPSO_NEW_ZIP1);
        sobj.setString("BIOWN_NUM", BIOWN_NUM);               //����� ��ȣ
        sobj.setString("MNGEMSTR_BD_MNG_NUM", MNGEMSTR_BD_MNG_NUM);
        sobj.setString("MAIL_RCPT", MAIL_RCPT);               //���� ������
        sobj.setString("MNG_ZIP", MNG_ZIP);               //���� �����ȣ
        sobj.setString("MNGEMSTR_NEW_ADDR1", MNGEMSTR_NEW_ADDR1);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("UPSO_REMAK", UPSO_REMAK);               //���� ���
        sobj.setString("PERMMSTR_BD_MNG_NUM", PERMMSTR_BD_MNG_NUM);
        sobj.setString("MNGEMSTR_NEW_ADDR2", MNGEMSTR_NEW_ADDR2);
        sobj.setString("PERMMSTR_RESINUM", PERMMSTR_RESINUM);               //�㰡�� �ֹι�ȣ
        sobj.setString("PERMMSTR_NM", PERMMSTR_NM);               //�㰡�� �̸�
        sobj.setString("CLIENT_NUM_1", CLIENT_NUM_1);               //�� ��ȣ
        sobj.setString("PERMMSTR_REMAK", PERMMSTR_REMAK);               //�㰡�� ���
        sobj.setDouble("AREA", AREA);               //����
        sobj.setString("UPSO_STAT", UPSO_STAT);               //���� ����
        sobj.setString("PERMMSTR_HPNUM", PERMMSTR_HPNUM);               //�㰡�� �ڵ�����ȣ
        sobj.setString("UPSO_NEW_ADDR1", UPSO_NEW_ADDR1);
        sobj.setString("OPBI_DAY", OPBI_DAY);               //���� ����
        sobj.setString("PAYPRES_GBN", PAYPRES_GBN);               //������ ����
        sobj.setString("MNGEMSTR_HPNUM", MNGEMSTR_HPNUM);               //�濵�� �ڵ�����ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("MNGEMSTR_ADDR1", MNGEMSTR_ADDR1);               //�濵�� �ּ�1
        sobj.setString("MNGEMSTR_ADDR2", MNGEMSTR_ADDR2);               //�濵�� �ּ�2
        sobj.setString("PERMMSTR_ADDR1", PERMMSTR_ADDR1);               //�㰡�� �ּ�1
        sobj.setString("PERMMSTR_ADDR2", PERMMSTR_ADDR2);               //�㰡�� �ּ�2
        sobj.setString("UPSO_ADDR1", UPSO_ADDR1);               //���� �ּ�1
        sobj.setString("UPSO_ADDR2", UPSO_ADDR2);               //���� �ּ�2
        sobj.setString("UPSO_CD", UPSO_CD);               //TEMP1
        return sobj;
    }
    // ������������
    public DOBJ CALLupso_regist_ky_DEL27(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL27");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_DEL27(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL27") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_DEL27(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �뷡����������
    public DOBJ CALLupso_regist_ky_DEL34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL34");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_DEL34(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL34") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_DEL34(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ұ⺻��������
    // ���ұ⺻������ �����Ѵ�. �� ���� ������ ���� ó���Ѵ�.
    public DOBJ CALLupso_regist_ky_UPD28(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD28");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_UPD28(dobj, dvobj);
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
    private SQLObject SQLupso_regist_ky_UPD28(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");        //������ ID
        String MOD_DATE ="";        //���� �Ͻ�
        String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //���� �����ȣ
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   BIOWN_NUM = dvobj.getRecord().get("BIOWN_NUM");   //����� ��ȣ
        String   PERMMSTR_PHONNUM = dvobj.getRecord().get("PERMMSTR_PHONNUM");   //�㰡�� ��ȭ��ȣ
        String   MAIL_RCPT = dvobj.getRecord().get("MAIL_RCPT");   //���� ������
        String   NEW_DAY = dvobj.getRecord().get("NEW_DAY");   //�ű� ����
        String   MNG_ZIP = dvobj.getRecord().get("MNG_ZIP");   //���� �����ȣ
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   MNGEMSTR_ADDR2 = dvobj.getRecord().get("MNGEMSTR_ADDR2");   //�濵�� �ּ�2
        String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //�濵�� �̸�
        String   EMAIL_ADDR = dvobj.getRecord().get("EMAIL_ADDR");   //�̸��� �ּ�
        String   CORP_NUM = dvobj.getRecord().get("CORP_NUM");   //���� ��ȣ
        String   UPSO_REMAK = dvobj.getRecord().get("UPSO_REMAK");   //���� ���
        String   UPSO_ADDR2 = dvobj.getRecord().get("UPSO_ADDR2");   //���� �ּ�2
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
        String   PAPER_CANC_YN = dvobj.getRecord().get("PAPER_CANC_YN");   //���� ��� ����
        String   PERMMSTR_RESINUM = dvobj.getRecord().get("PERMMSTR_RESINUM");   //�㰡�� �ֹι�ȣ
        String   PERMMSTR_NM = dvobj.getRecord().get("PERMMSTR_NM");   //�㰡�� �̸�
        String   UPSO_NM = dvobj.getRecord().get("UPSO_NM");   //���� ��
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   AREA = dvobj.getRecord().getDouble("AREA");   //����
        String   MNGEMSTR_ZIP = dvobj.getRecord().get("MNGEMSTR_ZIP");   //�濵�� �����ȣ
        String   MNGEMSTR_RESINUM = dvobj.getRecord().get("MNGEMSTR_RESINUM");   //�濵�� �ֹι�ȣ
        String   MNGEMSTR_PHONNUM = dvobj.getRecord().get("MNGEMSTR_PHONNUM");   //�濵�� ��ȭ��ȣ
        String   UPSO_STAT = dvobj.getRecord().get("UPSO_STAT");   //���� ����
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   UPSO_ADDR1 = dvobj.getRecord().get("UPSO_ADDR1");   //���� �ּ�1
        String   MNGEMSTR_ADDR1 = dvobj.getRecord().get("MNGEMSTR_ADDR1");   //�濵�� �ּ�1
        String   BILL_ISS_YN = dvobj.getRecord().get("BILL_ISS_YN");   //��꼭 ���� ����
        String   OPBI_DAY = dvobj.getRecord().get("OPBI_DAY");   //���� ����
        String   MNGEMSTR_HPNUM = dvobj.getRecord().get("MNGEMSTR_HPNUM");   //�濵�� �ڵ�����ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MNGEMSTR_HPNUM=:MNGEMSTR_HPNUM , OPBI_DAY=:OPBI_DAY , BILL_ISS_YN=:BILL_ISS_YN , MNGEMSTR_ADDR1=:MNGEMSTR_ADDR1 , UPSO_ADDR1=:UPSO_ADDR1 , STAFF_CD=:STAFF_CD , UPSO_STAT=:UPSO_STAT , MNGEMSTR_PHONNUM=:MNGEMSTR_PHONNUM , MNGEMSTR_RESINUM=:MNGEMSTR_RESINUM , MNGEMSTR_ZIP=:MNGEMSTR_ZIP , AREA=:AREA , UPSO_NM=:UPSO_NM , PERMMSTR_NM=:PERMMSTR_NM , PERMMSTR_RESINUM=:PERMMSTR_RESINUM , PAPER_CANC_YN=:PAPER_CANC_YN , MCHNDAESU=:MCHNDAESU , UPSO_ADDR2=:UPSO_ADDR2 , UPSO_REMAK=:UPSO_REMAK ,  \n";
        query +=" CORP_NUM=:CORP_NUM , EMAIL_ADDR=:EMAIL_ADDR , MNGEMSTR_NM=:MNGEMSTR_NM , MNGEMSTR_ADDR2=:MNGEMSTR_ADDR2 , BSCON_CD=:BSCON_CD , MNG_ZIP=:MNG_ZIP , NEW_DAY=:NEW_DAY , MAIL_RCPT=:MAIL_RCPT , PERMMSTR_PHONNUM=:PERMMSTR_PHONNUM , BIOWN_NUM=:BIOWN_NUM , BRAN_CD=:BRAN_CD , UPSO_ZIP=:UPSO_ZIP  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //���� �����ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("BIOWN_NUM", BIOWN_NUM);               //����� ��ȣ
        sobj.setString("PERMMSTR_PHONNUM", PERMMSTR_PHONNUM);               //�㰡�� ��ȭ��ȣ
        sobj.setString("MAIL_RCPT", MAIL_RCPT);               //���� ������
        sobj.setString("NEW_DAY", NEW_DAY);               //�ű� ����
        sobj.setString("MNG_ZIP", MNG_ZIP);               //���� �����ȣ
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("MNGEMSTR_ADDR2", MNGEMSTR_ADDR2);               //�濵�� �ּ�2
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //�濵�� �̸�
        sobj.setString("EMAIL_ADDR", EMAIL_ADDR);               //�̸��� �ּ�
        sobj.setString("CORP_NUM", CORP_NUM);               //���� ��ȣ
        sobj.setString("UPSO_REMAK", UPSO_REMAK);               //���� ���
        sobj.setString("UPSO_ADDR2", UPSO_ADDR2);               //���� �ּ�2
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        sobj.setString("PAPER_CANC_YN", PAPER_CANC_YN);               //���� ��� ����
        sobj.setString("PERMMSTR_RESINUM", PERMMSTR_RESINUM);               //�㰡�� �ֹι�ȣ
        sobj.setString("PERMMSTR_NM", PERMMSTR_NM);               //�㰡�� �̸�
        sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("AREA", AREA);               //����
        sobj.setString("MNGEMSTR_ZIP", MNGEMSTR_ZIP);               //�濵�� �����ȣ
        sobj.setString("MNGEMSTR_RESINUM", MNGEMSTR_RESINUM);               //�濵�� �ֹι�ȣ
        sobj.setString("MNGEMSTR_PHONNUM", MNGEMSTR_PHONNUM);               //�濵�� ��ȭ��ȣ
        sobj.setString("UPSO_STAT", UPSO_STAT);               //���� ����
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("UPSO_ADDR1", UPSO_ADDR1);               //���� �ּ�1
        sobj.setString("MNGEMSTR_ADDR1", MNGEMSTR_ADDR1);               //�濵�� �ּ�1
        sobj.setString("BILL_ISS_YN", BILL_ISS_YN);               //��꼭 ���� ����
        sobj.setString("OPBI_DAY", OPBI_DAY);               //���� ����
        sobj.setString("MNGEMSTR_HPNUM", MNGEMSTR_HPNUM);               //�濵�� �ڵ�����ȣ
        return sobj;
    }
    // CHG_NUM����
    public DOBJ CALLupso_regist_ky_SEL29(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL29");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL29");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_ky_SEL29(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL29");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_SEL29(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(CHG_NUM),  0)  +  1,  4,  '0')  CHG_NUM  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  CHG_BRAN  =  :BRAN_CD   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  CHG_DAY  =  TO_CHAR(SYSDATE,'YYYYMMDD') ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ���һ��������Է�
    public DOBJ CALLupso_regist_ky_INS62(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS62");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_INS62(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS62") ;
        dobj.setRetObject(rvobj);
        String message ="�뷡���� ��쿡�� �뷡�� �������� �Է��ϼž� �մϴ�.";
        dobj.setRetmsg(message);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_INS62(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CHG_DAY ="";        //���� ����
        String INS_DATE ="";        //��� �Ͻ�
        double JOIN_SEQ = 0;        //�����ε���
        double   MONPRNCFEE2 = dvobj.getRecord().getDouble("MONPRNCFEE2");   //���ؿ�����
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //������
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
        String   UPSO_GRAD = dvobj.getRecord().get("UPSO_GRAD");   //���� ���
        String   APPL_DAY = dobj.getRetObject("R").getRecord().get("OPBI_DAY");   //���� ����
        String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� ��ȣ2
        String   CHG_NUM ="9000";   //���� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSORTAL_INFO (JOIN_SEQ, APPL_DAY, CHG_BRAN, CHG_DAY, INSPRES_ID, UPSO_GRAD, MCHNDAESU, MONPRNCFEE, BSTYP_CD, INS_DATE, MONPRNCFEE2, CHG_NUM, UPSO_CD)  \n";
        query +=" values((SELECT NVL(MAX(JOIN_SEQ), 0) + 1 JOIN_SEQ FROM GIBU.TBRA_UPSORTAL_INFO ), :APPL_DAY , :CHG_BRAN , TO_CHAR(SYSDATE,'YYYYMMDD'), :INSPRES_ID , :UPSO_GRAD , :MCHNDAESU , :MONPRNCFEE , :BSTYP_CD , SYSDATE, :MONPRNCFEE2 , :CHG_NUM , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setDouble("MONPRNCFEE2", MONPRNCFEE2);               //���ؿ�����
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        sobj.setString("UPSO_GRAD", UPSO_GRAD);               //���� ���
        sobj.setString("APPL_DAY", APPL_DAY);               //���� ����
        sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ��ȣ2
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���һ�������
    public DOBJ CALLupso_regist_ky_INS30(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS30");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_INS30(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS30") ;
        dobj.setRetObject(rvobj);
        String message ="�뷡���� ��쿡�� �뷡�� �������� �Է��ϼž� �մϴ�.";
        dobj.setRetmsg(message);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_INS30(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPL_DAY ="";        //���� ����
        String CHG_DAY ="";        //���� ����
        String INS_DATE ="";        //��� �Ͻ�
        double JOIN_SEQ = 0;        //�����ε���
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //������
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
        String   USE_TIME = dvobj.getRecord().get("USE_TIME");   //���ð�
        String   UPSO_GRAD = dvobj.getRecord().get("UPSO_GRAD");   //���� ���
        String   APPL_DAY_2 = dvobj.getRecord().get("OPBI_DAY");   //���� ����
        String   APPL_DAY_1 = dvobj.getRecord().get("OPBI_DAY");   //���� ����
        String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� ��ȣ2
        String   CHG_NUM = dobj.getRetObject("SEL29").getRecord().get("CHG_NUM");   //���� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSORTAL_INFO (JOIN_SEQ, APPL_DAY, CHG_BRAN, CHG_DAY, INSPRES_ID, UPSO_GRAD, USE_TIME, MCHNDAESU, MONPRNCFEE, BSTYP_CD, INS_DATE, CHG_NUM, UPSO_CD)  \n";
        query +=" values((SELECT NVL(MAX(JOIN_SEQ), 0) + 1 JOIN_SEQ FROM GIBU.TBRA_UPSORTAL_INFO ), DECODE(:APPL_DAY_1,NULL,TO_CHAR(SYSDATE,'YYYYMMDD'),:APPL_DAY_2), :CHG_BRAN , TO_CHAR(SYSDATE,'YYYYMMDD'), :INSPRES_ID , :UPSO_GRAD , :USE_TIME , :MCHNDAESU , :MONPRNCFEE , :BSTYP_CD , SYSDATE, :CHG_NUM , :UPSO_CD )";
        sobj.setSql(query);
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //������
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        sobj.setString("USE_TIME", USE_TIME);               //���ð�
        sobj.setString("UPSO_GRAD", UPSO_GRAD);               //���� ���
        sobj.setString("APPL_DAY_2", APPL_DAY_2);               //���� ����
        sobj.setString("APPL_DAY_1", APPL_DAY_1);               //���� ����
        sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ��ȣ2
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �뷡��������Է�
    public DOBJ CALLupso_regist_ky_INS63(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS63");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_INS63(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS63") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_INS63(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CHG_DAY ="";        //���� ����
        double GRAD_NUM = 0;        //�뷡�����
        String INS_DATE ="";        //��� �Ͻ�
        String   GRAD_NUM_1 = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //�뷡�����
        String   GRAD_GBN = dvobj.getRecord().get("GRAD_GBN");   //��� ����
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
        double   AREA = dobj.getRetObject("S1").getRecord().getDouble("AREA");   //���� ����
        String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� ����
        String   CHG_NUM ="9000";   //���� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_NOREBANG_INFO (INS_DATE, CHG_BRAN, INSPRES_ID, CHG_NUM, CHG_DAY, MCHNDAESU, AREA, UPSO_CD, BSTYP_CD, GRAD_GBN, GRAD_NUM)  \n";
        query +=" values(SYSDATE, :CHG_BRAN , :INSPRES_ID , :CHG_NUM , TO_CHAR(SYSDATE,'YYYYMMDD'), :MCHNDAESU , :AREA , :UPSO_CD , :BSTYP_CD , :GRAD_GBN , (SELECT NVL(MAX(GRAD_NUM), 0) + 1 GRAD_NUM FROM GIBU.TBRA_NOREBANG_INFO WHERE UPSO_CD = :GRAD_NUM_1))";
        sobj.setSql(query);
        sobj.setString("GRAD_NUM_1", GRAD_NUM_1);               //�뷡�����
        sobj.setString("GRAD_GBN", GRAD_GBN);               //��� ����
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        sobj.setDouble("AREA", AREA);               //���� ����
        sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ����
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �뷡�������
    public DOBJ CALLupso_regist_ky_INS32(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS32");
        VOBJ dvobj = dobj.getRetObject("S1");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_INS32(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS32") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_INS32(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String CHG_DAY ="";        //���� ����
        double GRAD_NUM = 0;        //�뷡�����
        String INS_DATE ="";        //��� �Ͻ�
        String   GRAD_GBN = dvobj.getRecord().get("GRAD_GBN");   //��� ����
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //���� �ڵ�
        double   AREA = dvobj.getRecord().getDouble("AREA");   //����
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
        double   STNDAMT = dvobj.getRecord().getDouble("STNDAMT");   //���رݾ�
        String   CHG_BRAN = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� ����
        String   CHG_NUM = dobj.getRetObject("SEL29").getRecord().get("CHG_NUM");   //���� ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_NOREBANG_INFO (INS_DATE, CHG_BRAN, INSPRES_ID, CHG_NUM, CHG_DAY, STNDAMT, MCHNDAESU, AREA, UPSO_CD, BSTYP_CD, GRAD_GBN, GRAD_NUM)  \n";
        query +=" values(SYSDATE, :CHG_BRAN , :INSPRES_ID , :CHG_NUM , TO_CHAR(SYSDATE,'YYYYMMDD'), :STNDAMT , :MCHNDAESU , :AREA , :UPSO_CD , :BSTYP_CD , :GRAD_GBN , (SELECT NVL(MAX(GRAD_NUM), 0) + 1 GRAD_NUM FROM GIBU.TBRA_NOREBANG_INFO WHERE UPSO_CD = :UPSO_CD))";
        sobj.setSql(query);
        sobj.setString("GRAD_GBN", GRAD_GBN);               //��� ����
        sobj.setString("BSTYP_CD", BSTYP_CD);               //���� �ڵ�
        sobj.setDouble("AREA", AREA);               //����
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        sobj.setDouble("STNDAMT", STNDAMT);               //���رݾ�
        sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ����
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // Ŀ��
    public DOBJ CALLupso_regist_ky_XIUD65(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD65");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_XIUD65(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD65");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_XIUD65(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" COMMIT ";
        sobj.setSql(query);
        return sobj;
    }
    // ���ұ⺻��������
    // ���ұ⺻������ �����Ѵ�. �� ���� ������ ���� ó���Ѵ�.
    public DOBJ CALLupso_regist_ky_UPD14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD14");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_UPD14(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD14") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_UPD14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   PERMMSTR_PHONNUM = dvobj.getRecord().get("PERMMSTR_PHONNUM");   //�㰡�� ��ȭ��ȣ
        String   MNGEMSTR_REF_INFO = dvobj.getRecord().get("MNGEMSTR_REF_INFO");
        String   MNGEMSTR_NEW_ZIP = dvobj.getRecord().get("MNGEMSTR_NEW_ZIP");
        String   PERMMSTR_NEW_ADDR2 = dvobj.getRecord().get("PERMMSTR_NEW_ADDR2");
        String   PERMMSTR_NEW_ADDR1 = dvobj.getRecord().get("PERMMSTR_NEW_ADDR1");
        String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //�濵�� �̸�
        String   EMAIL_ADDR = dvobj.getRecord().get("EMAIL_ADDR");   //�̸��� �ּ�
        String   CORP_NUM = dvobj.getRecord().get("CORP_NUM");   //���� ��ȣ
        String   MNGEMSTR_REMAK = dvobj.getRecord().get("MNGEMSTR_REMAK");   //�濵�� ���
        double   MCHNDAESU = dvobj.getRecord().getDouble("MCHNDAESU");   //�����
        String   PERMMSTR_REF_INFO = dvobj.getRecord().get("PERMMSTR_REF_INFO");
        String   UPSO_NEW_ADDR2 = dvobj.getRecord().get("UPSO_NEW_ADDR2");
        String   PAPER_CANC_YN = dvobj.getRecord().get("PAPER_CANC_YN");   //���� ��� ����
        String   UPSO_NEW_ZIP = dvobj.getRecord().get("UPSO_NEW_ZIP");
        String   UPSO_NM = dvobj.getRecord().get("UPSO_NM");   //���� ��
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   MNGEMSTR_PHONNUM = dvobj.getRecord().get("MNGEMSTR_PHONNUM");   //�濵�� ��ȭ��ȣ
        String   MNGEMSTR_RESINUM = dvobj.getRecord().get("MNGEMSTR_RESINUM");   //�濵�� �ֹι�ȣ
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   UPSO_REF_INFO = dvobj.getRecord().get("UPSO_REF_INFO");
        String   PERMMSTR_NEW_ZIP = dvobj.getRecord().get("PERMMSTR_NEW_ZIP");
        String   BILL_ISS_YN = dvobj.getRecord().get("BILL_ISS_YN");   //��꼭 ���� ����
        String   UPSO_PHON = dvobj.getRecord().get("UPSO_PHON");   //���� ��ȭ
        String   UPSO_NEW_ZIP1 = dvobj.getRecord().get("UPSO_NEW_ZIP1");
        String   UPSO_BD_MNG_NUM = dvobj.getRecord().get("UPSO_BD_MNG_NUM");
        String   BIOWN_NUM = dvobj.getRecord().get("BIOWN_NUM");   //����� ��ȣ
        String   MNGEMSTR_BD_MNG_NUM = dvobj.getRecord().get("MNGEMSTR_BD_MNG_NUM");
        String   MAIL_RCPT = dvobj.getRecord().get("MAIL_RCPT");   //���� ������
        String   MNG_ZIP = dvobj.getRecord().get("MNG_ZIP");   //���� �����ȣ
        String   NEW_DAY = dvobj.getRecord().get("NEW_DAY");   //�ű� ����
        String   MNGEMSTR_NEW_ADDR1 = dvobj.getRecord().get("MNGEMSTR_NEW_ADDR1");
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   UPSO_REMAK = dvobj.getRecord().get("UPSO_REMAK");   //���� ���
        String   PERMMSTR_BD_MNG_NUM = dvobj.getRecord().get("PERMMSTR_BD_MNG_NUM");
        String   MNGEMSTR_NEW_ADDR2 = dvobj.getRecord().get("MNGEMSTR_NEW_ADDR2");
        String   PERMMSTR_RESINUM = dvobj.getRecord().get("PERMMSTR_RESINUM");   //�㰡�� �ֹι�ȣ
        String   PERMMSTR_NM = dvobj.getRecord().get("PERMMSTR_NM");   //�㰡�� �̸�
        String   PERMMSTR_REMAK = dvobj.getRecord().get("PERMMSTR_REMAK");   //�㰡�� ���
        double   AREA = dvobj.getRecord().getDouble("AREA");   //����
        String   UPSO_STAT = dvobj.getRecord().get("UPSO_STAT");   //���� ����
        String   PERMMSTR_HPNUM = dvobj.getRecord().get("PERMMSTR_HPNUM");   //�㰡�� �ڵ�����ȣ
        String   MNGEMSTR_NEW_ZIP1 = dvobj.getRecord().get("MNGEMSTR_NEW_ZIP1");
        String   UPSO_NEW_ADDR1 = dvobj.getRecord().get("UPSO_NEW_ADDR1");
        String   OPBI_DAY = dvobj.getRecord().get("OPBI_DAY");   //���� ����
        String   PERMMSTR_NEW_ZIP1 = dvobj.getRecord().get("PERMMSTR_NEW_ZIP1");
        String   PAYPRES_GBN = dvobj.getRecord().get("PAYPRES_GBN");   //������ ����
        String   MNGEMSTR_HPNUM = dvobj.getRecord().get("MNGEMSTR_HPNUM");   //�濵�� �ڵ�����ȣ
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO  \n";
        query +=" set MNGEMSTR_HPNUM=:MNGEMSTR_HPNUM , MODPRES_ID=:MODPRES_ID , PAYPRES_GBN=:PAYPRES_GBN , PERMMSTR_NEW_ZIP1=:PERMMSTR_NEW_ZIP1 , OPBI_DAY=:OPBI_DAY , UPSO_NEW_ADDR1=:UPSO_NEW_ADDR1 , MNGEMSTR_NEW_ZIP1=:MNGEMSTR_NEW_ZIP1 , PERMMSTR_HPNUM=:PERMMSTR_HPNUM , UPSO_STAT=:UPSO_STAT , AREA=:AREA , PERMMSTR_REMAK=:PERMMSTR_REMAK , PERMMSTR_NM=:PERMMSTR_NM , PERMMSTR_RESINUM=:PERMMSTR_RESINUM , MNGEMSTR_NEW_ADDR2=:MNGEMSTR_NEW_ADDR2 , PERMMSTR_BD_MNG_NUM=:PERMMSTR_BD_MNG_NUM , UPSO_REMAK=:UPSO_REMAK , BSCON_CD=:BSCON_CD , MNGEMSTR_NEW_ADDR1=:MNGEMSTR_NEW_ADDR1 , NEW_DAY=:NEW_DAY , MNG_ZIP=:MNG_ZIP , MAIL_RCPT=:MAIL_RCPT , MOD_DATE=SYSDATE , MNGEMSTR_BD_MNG_NUM=:MNGEMSTR_BD_MNG_NUM , BIOWN_NUM=:BIOWN_NUM , UPSO_BD_MNG_NUM=:UPSO_BD_MNG_NUM , UPSO_NEW_ZIP1=:UPSO_NEW_ZIP1 , UPSO_PHON=:UPSO_PHON , BILL_ISS_YN=:BILL_ISS_YN , PERMMSTR_NEW_ZIP=:PERMMSTR_NEW_ZIP , UPSO_REF_INFO=:UPSO_REF_INFO , STAFF_CD=:STAFF_CD , MNGEMSTR_RESINUM=:MNGEMSTR_RESINUM , MNGEMSTR_PHONNUM=:MNGEMSTR_PHONNUM , UPSO_NM=:UPSO_NM , UPSO_NEW_ZIP=:UPSO_NEW_ZIP , PAPER_CANC_YN=:PAPER_CANC_YN , UPSO_NEW_ADDR2=:UPSO_NEW_ADDR2 , PERMMSTR_REF_INFO=:PERMMSTR_REF_INFO , MCHNDAESU=:MCHNDAESU , MNGEMSTR_REMAK=:MNGEMSTR_REMAK ,  \n";
        query +=" CORP_NUM=:CORP_NUM , EMAIL_ADDR=:EMAIL_ADDR , MNGEMSTR_NM=:MNGEMSTR_NM , PERMMSTR_NEW_ADDR1=:PERMMSTR_NEW_ADDR1 , PERMMSTR_NEW_ADDR2=:PERMMSTR_NEW_ADDR2 , MNGEMSTR_NEW_ZIP=:MNGEMSTR_NEW_ZIP , MNGEMSTR_REF_INFO=:MNGEMSTR_REF_INFO , PERMMSTR_PHONNUM=:PERMMSTR_PHONNUM  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("PERMMSTR_PHONNUM", PERMMSTR_PHONNUM);               //�㰡�� ��ȭ��ȣ
        sobj.setString("MNGEMSTR_REF_INFO", MNGEMSTR_REF_INFO);
        sobj.setString("MNGEMSTR_NEW_ZIP", MNGEMSTR_NEW_ZIP);
        sobj.setString("PERMMSTR_NEW_ADDR2", PERMMSTR_NEW_ADDR2);
        sobj.setString("PERMMSTR_NEW_ADDR1", PERMMSTR_NEW_ADDR1);
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //�濵�� �̸�
        sobj.setString("EMAIL_ADDR", EMAIL_ADDR);               //�̸��� �ּ�
        sobj.setString("CORP_NUM", CORP_NUM);               //���� ��ȣ
        sobj.setString("MNGEMSTR_REMAK", MNGEMSTR_REMAK);               //�濵�� ���
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        sobj.setString("PERMMSTR_REF_INFO", PERMMSTR_REF_INFO);
        sobj.setString("UPSO_NEW_ADDR2", UPSO_NEW_ADDR2);
        sobj.setString("PAPER_CANC_YN", PAPER_CANC_YN);               //���� ��� ����
        sobj.setString("UPSO_NEW_ZIP", UPSO_NEW_ZIP);
        sobj.setString("UPSO_NM", UPSO_NM);               //���� ��
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("MNGEMSTR_PHONNUM", MNGEMSTR_PHONNUM);               //�濵�� ��ȭ��ȣ
        sobj.setString("MNGEMSTR_RESINUM", MNGEMSTR_RESINUM);               //�濵�� �ֹι�ȣ
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("UPSO_REF_INFO", UPSO_REF_INFO);
        sobj.setString("PERMMSTR_NEW_ZIP", PERMMSTR_NEW_ZIP);
        sobj.setString("BILL_ISS_YN", BILL_ISS_YN);               //��꼭 ���� ����
        sobj.setString("UPSO_PHON", UPSO_PHON);               //���� ��ȭ
        sobj.setString("UPSO_NEW_ZIP1", UPSO_NEW_ZIP1);
        sobj.setString("UPSO_BD_MNG_NUM", UPSO_BD_MNG_NUM);
        sobj.setString("BIOWN_NUM", BIOWN_NUM);               //����� ��ȣ
        sobj.setString("MNGEMSTR_BD_MNG_NUM", MNGEMSTR_BD_MNG_NUM);
        sobj.setString("MAIL_RCPT", MAIL_RCPT);               //���� ������
        sobj.setString("MNG_ZIP", MNG_ZIP);               //���� �����ȣ
        sobj.setString("NEW_DAY", NEW_DAY);               //�ű� ����
        sobj.setString("MNGEMSTR_NEW_ADDR1", MNGEMSTR_NEW_ADDR1);
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("UPSO_REMAK", UPSO_REMAK);               //���� ���
        sobj.setString("PERMMSTR_BD_MNG_NUM", PERMMSTR_BD_MNG_NUM);
        sobj.setString("MNGEMSTR_NEW_ADDR2", MNGEMSTR_NEW_ADDR2);
        sobj.setString("PERMMSTR_RESINUM", PERMMSTR_RESINUM);               //�㰡�� �ֹι�ȣ
        sobj.setString("PERMMSTR_NM", PERMMSTR_NM);               //�㰡�� �̸�
        sobj.setString("PERMMSTR_REMAK", PERMMSTR_REMAK);               //�㰡�� ���
        sobj.setDouble("AREA", AREA);               //����
        sobj.setString("UPSO_STAT", UPSO_STAT);               //���� ����
        sobj.setString("PERMMSTR_HPNUM", PERMMSTR_HPNUM);               //�㰡�� �ڵ�����ȣ
        sobj.setString("MNGEMSTR_NEW_ZIP1", MNGEMSTR_NEW_ZIP1);
        sobj.setString("UPSO_NEW_ADDR1", UPSO_NEW_ADDR1);
        sobj.setString("OPBI_DAY", OPBI_DAY);               //���� ����
        sobj.setString("PERMMSTR_NEW_ZIP1", PERMMSTR_NEW_ZIP1);
        sobj.setString("PAYPRES_GBN", PAYPRES_GBN);               //������ ����
        sobj.setString("MNGEMSTR_HPNUM", MNGEMSTR_HPNUM);               //�濵�� �ڵ�����ȣ
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // ������ ������ ����
    public DOBJ CALLupso_regist_ky_OSP66(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP66");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP66");
        String[]  incolumns ={"UPSO_CD","OPBI_DAY","BSTYP_CD"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");         //���� �ڵ�
            record.put("UPSO_CD",UPSO_CD);
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
        String   spname ="GIBU.SP_SET_UPSORTAL";
        int[]    intypes={12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        robj.setName("OSP66");
        robj.Println("OSP66");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �������������������
    public DOBJ CALLupso_regist_ky_SEL35(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL35");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL35");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_ky_SEL35(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL35");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_SEL35(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  CHG_DAY  ,  CHG_NUM  ,  CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  JOIN_SEQ  =  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NVL(APPL_DAY,  CHG_DAY)  <=  TO_CHAR(SYSDATE,  'YYYYMMDD')  ) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� ����
    public DOBJ CALLupso_regist_ky_DEL113(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL113");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_DEL113(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL113") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_DEL113(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_NUM ="9000";   //���� ��ȣ
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" where CHG_NUM=:CHG_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��������������
    public DOBJ CALLupso_regist_ky_UPD34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD34");
        VOBJ dvobj = dobj.getRetObject("SEL35");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_UPD34(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD34") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_UPD34(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");        //������ ID
        String MOD_DATE ="";        //���� �Ͻ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CHG_DAY = dvobj.getRecord().get("CHG_DAY");   //���� ����
        String   CHG_NUM = dvobj.getRecord().get("CHG_NUM");   //���� ��ȣ
        String   CHG_BRAN = dvobj.getRecord().get("CHG_BRAN");   //���� ����
        double   MCHNDAESU = dobj.getRetObject("R").getRecord().getDouble("MCHNDAESU");   //�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update  \n";
        query +=" GIBU.TBRA_UPSORTAL_INFO  \n";
        query +=" set MCHNDAESU=:MCHNDAESU  \n";
        query +=" where CHG_BRAN=:CHG_BRAN  \n";
        query +=" and CHG_NUM=:CHG_NUM  \n";
        query +=" and CHG_DAY=:CHG_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CHG_DAY", CHG_DAY);               //���� ����
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ����
        sobj.setDouble("MCHNDAESU", MCHNDAESU);               //�����
        return sobj;
    }
    // �뷡����������
    public DOBJ CALLupso_regist_ky_DEL114(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL114");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_DEL114(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL114") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_DEL114(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_NUM ="9000";   //���� ��ȣ
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from  \n";
        query +=" GIBU.TBRA_NOREBANG_INFO  \n";
        query +=" where CHG_NUM=:CHG_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // Ŀ��
    public DOBJ CALLupso_regist_ky_XIUD55(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD55");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_XIUD55(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD55");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_XIUD55(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" COMMIT ";
        sobj.setSql(query);
        return sobj;
    }
    // ��� ����üũ
    public DOBJ CALLupso_regist_ky_SEL36(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL36");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL36");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_ky_SEL36(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL36");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_SEL36(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String VISIT_DAY ="";        //�湮 ����
        String   JOB_GBN ="U";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  TO_CHAR(SYSDATE,'YYYYMMDD')   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ������ ������ ����
    public DOBJ CALLupso_regist_ky_OSP52(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP52");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP52");
        String[]  incolumns ={"UPSO_CD"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");         //���� �ڵ�
            record.put("UPSO_CD",UPSO_CD);
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
        String   spname ="GIBU.SP_MISU_DEMD_OPEN";
        int[]    intypes={12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        robj.setName("OSP52");
        robj.Println("OSP52");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���ҹ湮�ű�
    // ���Ҹ��� ����Ǿ��� ��� ���ҹ湮 ���̺� ��� ����
    public DOBJ CALLupso_regist_ky_INS21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS21");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_INS21(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS21") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_INS21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_DAY ="";        //�湮 ����
        String VISIT_TIME ="";        //�湮 �ð�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   CONSPRES ="��Ÿ";   //�����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="U";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="[���Ҹ���]";   //���
        int   VISIT_SEQ = 1;   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, VISIT_TIME, INSPRES_ID, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, TO_CHAR(SYSDATE, 'HH24MI'), :INSPRES_ID , TO_CHAR(SYSDATE,'YYYYMMDD'), :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("CONSPRES", CONSPRES);               //�����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���� �¿������� ����
    public DOBJ CALLupso_regist_ky_XIUD41(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD41");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_XIUD41(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD41");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_XIUD41(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BEFORE_UPSO_CD = dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_ONOFF_INFO ( UPSO_CD , MODEL_CD , ONOFF_GBN , ACMCN_DAESU , INSPRES_ID , INS_DATE ) SELECT :UPSO_CD , MODEL_CD , ONOFF_GBN , ACMCN_DAESU , :INSPRES_ID , SYSDATE FROM GIBU.TBRA_UPSO_ONOFF_INFO WHERE UPSO_CD = :BEFORE_UPSO_CD";
        sobj.setSql(query);
        sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ҹ湮�޸���
    public DOBJ CALLupso_regist_ky_INS39(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS39");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_INS39(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS39") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_INS39(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_DAY ="";        //�湮 ����
        int  VISIT_NUM = 0;        //�湮 ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_NUM_1 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //�湮 ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="U";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="[���Ҹ���]"+dvobj.getRecord().get("BEFORE_UPSO_NM")+"-->"+dvobj.getRecord().get("UPSO_NM");   //���
        int   VISIT_SEQ = 1;   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , TO_CHAR(SYSDATE,'YYYYMMDD'), :JOB_GBN , (SELECT NVL(MAX(VISIT_NUM),0)+1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = TO_CHAR(SYSDATE,'YYYYMMDD') AND UPSO_CD = :VISIT_NUM_1 AND JOB_GBN = 'U' ), :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //�湮 ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���� ���ֱ����� ����
    public DOBJ CALLupso_regist_ky_XIUD42(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD42");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_XIUD42(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD42");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_XIUD42(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BEFORE_UPSO_CD = dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_ACMCN_INFO ( UPSO_CD , MODEL_CD , ACMCN_DAESU , INSPRES_ID , INS_DATE ) SELECT :UPSO_CD , MODEL_CD , ACMCN_DAESU , :INSPRES_ID , SYSDATE FROM GIBU.TBRA_UPSO_ACMCN_INFO WHERE UPSO_CD = :BEFORE_UPSO_CD";
        sobj.setSql(query);
        sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���ҹ湮�޸���
    public DOBJ CALLupso_regist_ky_INS38(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS38");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_INS38(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS38") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_INS38(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String VISIT_DAY ="";        //�湮 ����
        int  VISIT_NUM = 0;        //�湮 ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   VISIT_NUM_1 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //�湮 ��ȣ
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   JOB_GBN ="U";   //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        String   REMAK ="[���Ҹ���]"+dvobj.getRecord().get("BEFORE_UPSO_NM")+"-->"+dvobj.getRecord().get("UPSO_NM");   //���
        int   VISIT_SEQ = 1;   //�湮�ڼ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , TO_CHAR(SYSDATE,'YYYYMMDD'), :JOB_GBN , (SELECT NVL(MAX(VISIT_NUM),0)+1 VISIT_NUM FROM GIBU.TBRA_UPSO_VISIT_BRE WHERE VISIT_DAY = TO_CHAR(SYSDATE,'YYYYMMDD') AND UPSO_CD = :VISIT_NUM_1 AND JOB_GBN = 'U' ), :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("VISIT_NUM_1", VISIT_NUM_1);               //�湮 ��ȣ
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("JOB_GBN", JOB_GBN);               //���� ����(TENV_CODE TABLE�� HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //���
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //�湮�ڼ���
        return sobj;
    }
    // ���� ���긮 ���� ����
    public DOBJ CALLupso_regist_ky_XIUD43(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD43");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_XIUD43(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD43");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_XIUD43(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BEFORE_UPSO_CD = dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_UPSO_AUBRY_INFO ( UPSO_CD , MODEL_CD , MODEL_NM , MCHN_COMPY , MCHN_COMPYNM , ACMCN_DAESU , INSPRES_ID , INS_DATE ) SELECT :UPSO_CD , MODEL_CD , MODEL_NM , MCHN_COMPY , MCHN_COMPYNM , ACMCN_DAESU , :INSPRES_ID , SYSDATE FROM GIBU.TBRA_UPSO_AUBRY_INFO WHERE UPSO_CD = :BEFORE_UPSO_CD";
        sobj.setSql(query);
        sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �¿��� ���� ����
    public DOBJ CALLupso_regist_ky_XIUD50(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD50");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_XIUD50(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD50");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_XIUD50(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BEFORE_UPSO_CD = dobj.getRetObject("R").getRecord().get("BEFORE_UPSO_CD");   //���� ���� �ڵ�
        String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO  \n";
        query +=" SET ONOFF_GBN = ( SELECT ONOFF_GBN FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE UPSO_CD =  \n";
        query +=" :BEFORE_UPSO_CD)  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("BEFORE_UPSO_CD", BEFORE_UPSO_CD);               //���� ���� �ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // �ݿ��α׼����� �̰�
    public DOBJ CALLupso_regist_ky_OSP51(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP51");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("OSP51");
        String[]  incolumns ={"UPSO_CD","BEFORE_UPSO_CD","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //����� ID
            record.put("INSPRES_ID",INSPRES_ID);
            ////
            String   UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");         //���� �ڵ�
            record.put("UPSO_CD",UPSO_CD);
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
        String   spname ="GIBU.SP_PROC_KYLOG_OWNER_TRANS";
        int[]    intypes={12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
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
        robj.setName("OSP51");
        robj.Println("OSP51");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �ڵ���ü������ �߼۹��
    public DOBJ CALLupso_regist_ky_MIUD55(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD55");
        VOBJ dvobj = dobj.getRetObject("S2");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
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
                dobj  = CALLupso_regist_ky_UPD59(Conn, dobj);           //  �ڵ���ü������ ���Ź��
            }
        }
        return dobj;
    }
    // �ڵ���ü������ ���Ź��
    public DOBJ CALLupso_regist_ky_UPD59(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD59");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_regist_ky_UPD59(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD59") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_UPD59(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   EMAIL = dvobj.getRecord().get("EMAIL");   //�̸���
        int   AUTO_NUM = dvobj.getRecord().getInt("AUTO_NUM");   //�Ϸ� ��ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   GBN = dvobj.getRecord().get("GBN");   //����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_AUTO  \n";
        query +=" set GBN=:GBN , EMAIL=:EMAIL  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and AUTO_NUM=:AUTO_NUM";
        sobj.setSql(query);
        sobj.setString("EMAIL", EMAIL);               //�̸���
        sobj.setInt("AUTO_NUM", AUTO_NUM);               //�Ϸ� ��ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("GBN", GBN);               //����
        return sobj;
    }
    // ���һ�������ȸ
    // ���ǿ� �´� ���һ������� ��ȸ�Ѵ�.
    public DOBJ CALLupso_regist_ky_SEL17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL17");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL17");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_ky_SEL17(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL17");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_SEL17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String  UPSO_CD="";          //���� �ڵ�
        if( ( dobj.getRetObject("S").getRecord().get("IUDFLAG").equals("I") ))
        {
            UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");
        }
        else
        {
            UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");
        }
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.UPSO_NM  ,  A.BIOWN_NUM  ,  A.UPSO_PHON  ,  A.UPSO_ZIP  ,  A.UPSO_ADDR1  ,  A.UPSO_ADDR2  ,  A.MNGEMSTR_NM  ,  A.MNGEMSTR_PHONNUM  ,  A.MNGEMSTR_RESINUM  ,  A.MNGEMSTR_HPNUM  ,  A.MNGEMSTR_ZIP  ,  A.MNGEMSTR_ADDR1  ,  A.MNGEMSTR_ADDR2  ,  A.MNGEMSTR_REMAK  ,  A.PERMMSTR_NM  ,  A.PERMMSTR_PHONNUM  ,  A.PERMMSTR_RESINUM  ,  A.PERMMSTR_HPNUM  ,  A.PERMMSTR_ZIP  ,  A.PERMMSTR_ADDR1  ,  A.PERMMSTR_ADDR2  ,  A.PERMMSTR_REMAK  ,  A.OPBI_DAY  ,  A.PAYPRES_GBN  ,  A.NEW_DAY  ,  A.MAIL_RCPT  ,  A.PAPER_CANC_YN  ,  A.STAFF_CD  ,  D.HAN_NM  STAFF_NM  ,  A.UPSO_STAT  ,  A.BEFORE_UPSO_CD  ,  TRIM(B.BSTYP_CD)  ||  B.UPSO_GRAD  GRAD  ,  TRIM(B.BSTYP_CD)  BSTYP_CD  ,  B.UPSO_GRAD  ,  B.MONPRNCFEE  ,  B.USE_TIME  ,  TRIM(B.B_BSTYP_CD)  ||  B.B_UPSO_GRAD  B_GRAD  ,  B.B_BSTYP_CD  ,  B.B_UPSO_GRAD  ,  B.B_MONPRNCFEE  ,  B.B_USE_TIME  ,  B.GRADNM  ,  B.B_GRADNM  ,  B.CHG_DAY  ,  B.CHG_NUM  ,  B.CHG_BRAN  ,  B.B_CHG_DAY  ,  B.B_CHG_NUM  ,  B.B_CHG_BRAN  ,  TO_CHAR(A.INS_DATE,'YYYYMMDD')  INS_DATE  ,  B.B_UPSO_NM  ,  C.MCHNDAESU  ,  C.B_MCHNDAESU  ,  DECODE(A.CLSBS_YRMN,  NULL,  A.CLSBS_YRMN,  A.CLSBS_YRMN  ||  '01')  CLSBS_YRMN  ,  A.CLIENT_NUM  ,  A.BSCON_CD  ,  E.BSCONHAN_NM  ,  A.BILL_ISS_YN  ,  A.UPSO_REMAK  ,  A.BRAN_CD  ,  A.MNG_ZIP  ,  A.CORP_NUM  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  MAX(DECODE(DUMMY,  '1',  UPSO_CD  ))  UPSO_CD  ,  MAX(DECODE(DUMMY,  '1',  UPSO_GRAD  ))  UPSO_GRAD  ,  MAX(DECODE(DUMMY,  '1',  MONPRNCFEE))  MONPRNCFEE  ,  MAX(DECODE(DUMMY,  '1',  USE_TIME  ))  USE_TIME  ,  MAX(DECODE(DUMMY,  '1',  BSTYP_CD  ))  BSTYP_CD  ,  MAX(DECODE(DUMMY,  '1',  GRADNM  ))  GRADNM  ,  MAX(DECODE(DUMMY,  '1',  CHG_DAY))  CHG_DAY  ,  MAX(DECODE(DUMMY,  '1',  CHG_NUM))  CHG_NUM  ,  MAX(DECODE(DUMMY,  '1',  CHG_BRAN  ))  CHG_BRAN  ,  MAX(DECODE(DUMMY,  '2',  UPSO_CD  ))  B_UPSO_CD  ,  MAX(DECODE(DUMMY,  '2',  UPSO_GRAD  ))  B_UPSO_GRAD  ,  MAX(DECODE(DUMMY,  '2',  MONPRNCFEE))  B_MONPRNCFEE  ,  MAX(DECODE(DUMMY,  '2',  USE_TIME  ))  B_USE_TIME  ,  MAX(DECODE(DUMMY,  '2',  BSTYP_CD  ))  B_BSTYP_CD  ,  MAX(DECODE(DUMMY,  '2',  GRADNM  ))  B_GRADNM  ,  MAX(DECODE(DUMMY,  '2',  UPSO_NM  ))  B_UPSO_NM  ,  MAX(DECODE(DUMMY,  '2',  CHG_DAY))  B_CHG_DAY  ,  MAX(DECODE(DUMMY,  '2',  CHG_NUM))  B_CHG_NUM  ,  MAX(DECODE(DUMMY,  '2',  CHG_BRAN  ))  B_CHG_BRAN  FROM  (   \n";
        query +=" SELECT  *  FROM  (   \n";
        query +=" SELECT  '1'  DUMMY  ,  A.UPSO_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.USE_TIME  ,  A.BSTYP_CD  ,  ''  UPSO_NM  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.BSTYP_CD  =  A.BSTYP_CD   \n";
        query +=" AND  B.GRAD_GBN  =  A.UPSO_GRAD  ORDER  BY  A.CHG_DAY  DESC  ,A.CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  UNION  ALL   \n";
        query +=" SELECT  *  FROM  (   \n";
        query +=" SELECT  '2'  DUMMY  ,  A.UPSO_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.USE_TIME  ,  A.BSTYP_CD  ,  C.UPSO_NM  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  (   \n";
        query +=" SELECT  BEFORE_UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD)   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  B.BSTYP_CD  =  A.BSTYP_CD   \n";
        query +=" AND  B.GRAD_GBN  =  A.UPSO_GRAD  ORDER  BY  A.CHG_DAY  DESC  ,A.CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  )  )  B  ,  (   \n";
        query +=" SELECT  MAX(MCHNDAESU)  MCHNDAESU  ,  MAX(B_MCHNDAESU)  B_MCHNDAESU  ,  MAX(UPSO_CD)  UPSO_CD  FROM  (   \n";
        query +=" SELECT  MCHNDAESU  MCHNDAESU  ,  NULL  B_MCHNDAESU  ,  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  NULL  ,  MCHNDAESU  B_MCHNDAESU  ,  NULL  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  (   \n";
        query +=" SELECT  BEFORE_UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD  )  )  )  C  ,  INSA.TINS_MST01  D  ,  FIDU.TLEV_BSCON  E  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  B.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  C.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  D.STAFF_NUM  (+)  =  A.STAFF_CD   \n";
        query +=" AND  E.BSCON_CD(+)  =  A.BSCON_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �뷡���
    public DOBJ CALLupso_regist_ky_SEL24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL24");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL24");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLupso_regist_ky_SEL24(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL24");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_regist_ky_SEL24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_BRAN = dobj.getRetObject("SEL17").getRecord().get("CHG_BRAN");   //���� ����
        String   CHG_NUM = dobj.getRetObject("SEL17").getRecord().get("CHG_NUM");   //���� ��ȣ
        String   CHG_DAY = dobj.getRetObject("SEL17").getRecord().get("CHG_DAY");   //���� ����
        String  UPSO_CD="";          //���� �ڵ�
        if( ( dobj.getRetObject("S").getRecord().get("IUDFLAG").equals("I") ))
        {
            UPSO_CD = dobj.getRetObject("SEL44").getRecord().get("N_UPSO_CD");
        }
        else
        {
            UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");
        }
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(A.BSTYP_CD)  ||  A.GRAD_GBN  GRAD  ,  A.AREA  ,  A.MCHNDAESU  ,  B.STNDAMT  ,  B.GRADNM  ,  A.MCHNDAESU  *  B.STNDAMT  AMT  FROM  GIBU.TBRA_NOREBANG_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  C.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  A.GRAD_GBN  =  B.GRAD_GBN  AND	A.CHG_DAY  =  :CHG_DAY  AND	A.CHG_NUM  =  :CHG_NUM  AND	A.CHG_BRAN  =  :CHG_BRAN ";
        sobj.setSql(query);
        sobj.setString("CHG_BRAN", CHG_BRAN);               //���� ����
        sobj.setString("CHG_NUM", CHG_NUM);               //���� ��ȣ
        sobj.setString("CHG_DAY", CHG_DAY);               //���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$upso_regist_ky
    //##**$$end
}
