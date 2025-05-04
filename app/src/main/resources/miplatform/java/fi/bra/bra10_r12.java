
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_r12
{
    public bra10_r12()
    {
    }
    //##**$$bra10_r12_select
    /*
    */
    public DOBJ CTLbra10_r12_select(DOBJ dobj)
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
            dobj  = CALLbra10_r12_select_SEL5(Conn, dobj);           //  ¡�� �Ѿ�, ���Ҽ� ��ȸ
            dobj  = CALLbra10_r12_select_SEL6(Conn, dobj);           //  ��ǥ�ݾ� ��ȸ
            dobj  = CALLbra10_r12_select_SEL7(Conn, dobj);           //  �������� ���
            dobj  = CALLbra10_r12_select_SEL8(Conn, dobj);           //  ¡���������� ��ȸ
            dobj  = CALLbra10_r12_select_SEL1(Conn, dobj);           //  ��ȸ
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
    public DOBJ CTLbra10_r12_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra10_r12_select_SEL5(Conn, dobj);           //  ¡�� �Ѿ�, ���Ҽ� ��ȸ
        dobj  = CALLbra10_r12_select_SEL6(Conn, dobj);           //  ��ǥ�ݾ� ��ȸ
        dobj  = CALLbra10_r12_select_SEL7(Conn, dobj);           //  �������� ���
        dobj  = CALLbra10_r12_select_SEL8(Conn, dobj);           //  ¡���������� ��ȸ
        dobj  = CALLbra10_r12_select_SEL1(Conn, dobj);           //  ��ȸ
        return dobj;
    }
    // ¡�� �Ѿ�, ���Ҽ� ��ȸ
    // ¡�� �Ѿ� ��ȸ
    public DOBJ CALLbra10_r12_select_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r12_select_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r12_select_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(PURE_LEVY_AMT)  LEVY_AMT  ,  SUM(LEVY_UPSO_CNT)  CNT  FROM  GIBU.TBRA_BRAN_BONUS_BRE  WHERE  APPL_YRMN  =  :APPL_YRMN ";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        return sobj;
    }
    // ��ǥ�ݾ� ��ȸ
    // ��ǥ�ݾ� ��ȸ
    public DOBJ CALLbra10_r12_select_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r12_select_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r12_select_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(MON_TARGET_AMT)  MON_TARGET_AMT  FROM  GIBU.TBRA_LEVY_TARGET  WHERE  APPL_YRMN  =  :APPL_YRMN ";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        return sobj;
    }
    // �������� ���
    // �������� ���
    public DOBJ CALLbra10_r12_select_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r12_select_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        rvobj.Println("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r12_select_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REVY_AMT = dobj.getRetObject("SEL5").getRecord().get("LEVY_AMT");   //REVY_AMT
        String   MON_TARGET_AMT = dobj.getRetObject("SEL6").getRecord().get("MON_TARGET_AMT");   //���� ��ǥ �ݾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TRUNC((:REVY_AMT  /  :MON_TARGET_AMT),5)  *100  AS  ATTAIN_RATE  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REVY_AMT", REVY_AMT);               //REVY_AMT
        sobj.setString("MON_TARGET_AMT", MON_TARGET_AMT);               //���� ��ǥ �ݾ�
        return sobj;
    }
    // ¡���������� ��ȸ
    // ¡���������� ��ȸ
    public DOBJ CALLbra10_r12_select_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r12_select_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        rvobj.Println("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r12_select_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   ATTAIN_RATE = dobj.getRetObject("SEL7").getRecord().getDouble("ATTAIN_RATE");   //�޼���
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BONUS_RATE  FROM  GIBU.TBRA_BONUS_PLOT  WHERE  APPL_YEAR  =  SUBSTR(:APPL_YRMN,  1,  4)   \n";
        query +=" AND  START_RATE  <=  :ATTAIN_RATE   \n";
        query +=" AND  END_RATE  >  :ATTAIN_RATE ";
        sobj.setSql(query);
        sobj.setDouble("ATTAIN_RATE", ATTAIN_RATE);               //�޼���
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        return sobj;
    }
    // ��ȸ
    public DOBJ CALLbra10_r12_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r12_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r12_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");        //���� ���
        String   APPL_YRMN_1 = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //���� ���
        double   BONUS_RATE = dobj.getRetObject("SEL8").getRecord().getDouble("BONUS_RATE");   //���� ����
        double   LEVY_AMT = dobj.getRetObject("SEL5").getRecord().getDouble("LEVY_AMT");   //¡�� �ݾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BRAN_CD,  B.DEPT_NM  AS  BRAN_NM,  C.MON_TARGET_AMT  AS  BG_AMT_YRMN,  A.PURE_LEVY_AMT,  A.EXT_AMT,  A.EXT_RATE,  A.EXT_BONUS,  TO_CHAR(TRUNC(:LEVY_AMT  *  :BONUS_RATE  /  100  *  0.2),'999,999,999,999,999,999,999')  AS  TOT_BONUS  FROM  GIBU.TBRA_BRAN_BONUS_BRE  A,   \n";
        query +=" (SELECT  GIBU,  DEPT_NM,  DEPT_CD  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  LIKE  '1060%'   \n";
        query +=" AND  GIBU  IS  NOT  NULL  )  B,  GIBU.TBRA_LEVY_TARGET  C  WHERE  A.BRAN_CD  =  B.GIBU   \n";
        query +=" AND  A.BRAN_CD  =  C.BRAN_CD   \n";
        query +=" AND  A.APPL_YRMN  =  C.APPL_YRMN   \n";
        query +=" AND  A.APPL_YRMN  =  SUBSTR(:APPL_YRMN_1,0,6) ";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN_1", APPL_YRMN_1);               //���� ���
        sobj.setDouble("BONUS_RATE", BONUS_RATE);               //���� ����
        sobj.setDouble("LEVY_AMT", LEVY_AMT);               //¡�� �ݾ�
        return sobj;
    }
    //##**$$bra10_r12_select
    //##**$$bra10_r12_search
    /* * ���α׷��� : bra10_r12
    * �ۼ��� : ������
    * �ۼ��� : 2009/11/18
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLbonus_bre_search(DOBJ dobj)
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
            dobj  = CALLbra10_r12_search_SEL1(Conn, dobj);           //  �������� ��ȸ
            dobj  = CALLbra10_r12_search_SEL2(Conn, dobj);           //  ¡�� �Ѿ�, ���Ҽ� ��ȸ
            dobj  = CALLbra10_r12_search_SEL3(Conn, dobj);           //  ��ǥ�ݾ� ��ȸ
            dobj  = CALLbra10_r12_search_SEL4(Conn, dobj);           //  �������� ���
            dobj  = CALLbra10_r12_search_SEL5(Conn, dobj);           //  ¡���������� ��ȸ
            dobj  = CALLbra10_r12_search_SEL7(Conn, dobj);           //  �ű԰��߾��� ������
            dobj  = CALLbra10_r12_search_SEL6(Conn, dobj);           //  ��� ����
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
    public DOBJ CTLbonus_bre_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra10_r12_search_SEL1(Conn, dobj);           //  �������� ��ȸ
        dobj  = CALLbra10_r12_search_SEL2(Conn, dobj);           //  ¡�� �Ѿ�, ���Ҽ� ��ȸ
        dobj  = CALLbra10_r12_search_SEL3(Conn, dobj);           //  ��ǥ�ݾ� ��ȸ
        dobj  = CALLbra10_r12_search_SEL4(Conn, dobj);           //  �������� ���
        dobj  = CALLbra10_r12_search_SEL5(Conn, dobj);           //  ¡���������� ��ȸ
        dobj  = CALLbra10_r12_search_SEL7(Conn, dobj);           //  �ű԰��߾��� ������
        dobj  = CALLbra10_r12_search_SEL6(Conn, dobj);           //  ��� ����
        return dobj;
    }
    // �������� ��ȸ
    // �������� ��ȸ
    public DOBJ CALLbra10_r12_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r12_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r12_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  START_RATE  ,  END_RATE  ,  BONUS_RATE  FROM  GIBU.TBRA_BONUS_PLOT  WHERE  APPL_YEAR  =  SUBSTR(:APPL_YRMN,  1,  4) ";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        return sobj;
    }
    // ¡�� �Ѿ�, ���Ҽ� ��ȸ
    // ¡�� �Ѿ� ��ȸ
    public DOBJ CALLbra10_r12_search_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r12_search_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r12_search_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TRUNC(XC.PURE_LEVY_AMT,5)  LEVY_AMT  ,  XA.CNT  -  XB.CNT  CNT  FROM  (   \n";
        query +=" SELECT  SUM(A.REPT_AMT)  LEVY_AMT  ,  COUNT(A.UPSO_CD)  CNT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  NVL(REPT_AMT,  0)  -  NVL(COMIS,  0)  REPT_AMT  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  BETWEEN  :APPL_YRMN  ||  '01'   \n";
        query +=" AND  :APPL_YRMN  ||  '31'   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  UPSO_CD  ,  NVL(DISTR_AMT,  0)  REPT_AMT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  REPT_DAY  BETWEEN  :APPL_YRMN  ||  '01'   \n";
        query +=" AND  :APPL_YRMN  ||  '31'  )  A  )  XA  ,  (   \n";
        query +=" SELECT  SUM(A.REPT_AMT)  LEVY_AMT  ,  COUNT(A.UPSO_CD)  CNT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  NVL(REPT_AMT,  0)  -  NVL(COMIS,  0)  REPT_AMT  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  BETWEEN  :APPL_YRMN  ||  '01'   \n";
        query +=" AND  :APPL_YRMN  ||  '31'   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  UPSO_CD  ,  NVL(DISTR_AMT,  0)  REPT_AMT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  REPT_DAY  BETWEEN  :APPL_YRMN  ||  '01'   \n";
        query +=" AND  :APPL_YRMN  ||  '31'  )  A  WHERE  A.UPSO_CD  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  :APPL_YRMN  BETWEEN  START_YRMN   \n";
        query +=" AND  END_YRMN  )  )  XB  ,  (   \n";
        query +=" SELECT  SUM(PURE_LEVY_AMT)  AS  PURE_LEVY_AMT  FROM  GIBU.TBRA_BRAN_BONUS_BRE  WHERE  APPL_YRMN  =  :APPL_YRMN  )  XC ";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        return sobj;
    }
    // ��ǥ�ݾ� ��ȸ
    // ��ǥ�ݾ� ��ȸ
    public DOBJ CALLbra10_r12_search_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r12_search_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r12_search_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TRUNC(SUM(MON_TARGET_AMT),5)  MON_TARGET_AMT  FROM  GIBU.TBRA_LEVY_TARGET  WHERE  APPL_YRMN  =  :APPL_YRMN ";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        return sobj;
    }
    // �������� ���
    // �������� ���
    public DOBJ CALLbra10_r12_search_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r12_search_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r12_search_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REVY_AMT = dobj.getRetObject("SEL2").getRecord().get("LEVY_AMT");   //REVY_AMT
        String   MON_TARGET_AMT = dobj.getRetObject("SEL3").getRecord().get("MON_TARGET_AMT");   //���� ��ǥ �ݾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TRUNC((:REVY_AMT  /  :MON_TARGET_AMT),5)  *100  AS  ATTAIN_RATE  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REVY_AMT", REVY_AMT);               //REVY_AMT
        sobj.setString("MON_TARGET_AMT", MON_TARGET_AMT);               //���� ��ǥ �ݾ�
        return sobj;
    }
    // ¡���������� ��ȸ
    // ¡���������� ��ȸ
    public DOBJ CALLbra10_r12_search_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r12_search_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r12_search_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   ATTAIN_RATE = dobj.getRetObject("SEL4").getRecord().getDouble("ATTAIN_RATE");   //�޼���
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BONUS_RATE  FROM  GIBU.TBRA_BONUS_PLOT  WHERE  APPL_YEAR  =  SUBSTR(:APPL_YRMN,  1,  4)   \n";
        query +=" AND  START_RATE  <=  :ATTAIN_RATE   \n";
        query +=" AND  END_RATE  >  :ATTAIN_RATE ";
        sobj.setSql(query);
        sobj.setDouble("ATTAIN_RATE", ATTAIN_RATE);               //�޼���
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        return sobj;
    }
    // �ű԰��߾��� ������
    public DOBJ CALLbra10_r12_search_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r12_search_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        rvobj.Println("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r12_search_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  NEW_UPSO_CNT  ,  SUM(NVL(TD.MONPRNCFEE,0))  NEW_UPSO_TOTAMT  ,  TRUNC(SUM(NVL(TD.MONPRNCFEE,0))  *  0.1,  0)  NEW_UPSO_AMT  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TD  WHERE  TA.NEW_DAY  LIKE  :APPL_YRMN||'%'   \n";
        query +=" AND  TA.BEFORE_UPSO_CD  IS  NULL   \n";
        query +=" AND  TD.BSTYP_CD  NOT  IN  ('v')   \n";
        query +=" AND  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(TA.CLSBS_INS_DAY,1,6),'  ')  >=  :APPL_YRMN)   \n";
        query +=" AND  TD.UPSO_CD  =  TA.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        return sobj;
    }
    // ��� ����
    public DOBJ CALLbra10_r12_search_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r12_search_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r12_search_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int   NEW_UPSO_CNT = dobj.getRetObject("SEL7").getRecord().getInt("NEW_UPSO_CNT");   //�ű� ���� ����
        double   ATTAIN_RATE = dobj.getRetObject("SEL4").getRecord().getDouble("ATTAIN_RATE");   //�޼���
        double   BONUS_RATE = dobj.getRetObject("SEL5").getRecord().getDouble("BONUS_RATE");   //���� ����
        double   NEW_UPSO_TOTAMT = dobj.getRetObject("SEL7").getRecord().getDouble("NEW_UPSO_TOTAMT");   //�ű� ���� �ѱݾ�
        double   LEVY_AMT = dobj.getRetObject("SEL2").getRecord().getDouble("LEVY_AMT");   //¡�� �ݾ�
        String   MON_TARGET_AMT = dobj.getRetObject("SEL3").getRecord().get("MON_TARGET_AMT");   //���� ��ǥ �ݾ�
        double   CNT = dobj.getRetObject("SEL2").getRecord().getDouble("CNT");   //ī��Ʈ
        double   NEW_UPSO_AMT = dobj.getRetObject("SEL7").getRecord().getDouble("NEW_UPSO_AMT");   //�ű� ���� �ݾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :LEVY_AMT  AS  LEVY_AMT  ,  :CNT  AS  CNT  ,  :MON_TARGET_AMT  AS  MON_TARGET_AMT  ,  TRUNC(:ATTAIN_RATE,2)  AS  ATTAIN_RATE  ,  :BONUS_RATE  AS  BONUS_RATE  ,  (:LEVY_AMT  *  :BONUS_RATE  /  100)  AS  LEVY_BONUS  ,  :NEW_UPSO_CNT  AS  NEW_UPSO_CNT  ,  :NEW_UPSO_TOTAMT  AS  NEW_UPSO_TOTAMT  ,  :NEW_UPSO_AMT  AS  NEW_UPSO_AMT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setInt("NEW_UPSO_CNT", NEW_UPSO_CNT);               //�ű� ���� ����
        sobj.setDouble("ATTAIN_RATE", ATTAIN_RATE);               //�޼���
        sobj.setDouble("BONUS_RATE", BONUS_RATE);               //���� ����
        sobj.setDouble("NEW_UPSO_TOTAMT", NEW_UPSO_TOTAMT);               //�ű� ���� �ѱݾ�
        sobj.setDouble("LEVY_AMT", LEVY_AMT);               //¡�� �ݾ�
        sobj.setString("MON_TARGET_AMT", MON_TARGET_AMT);               //���� ��ǥ �ݾ�
        sobj.setDouble("CNT", CNT);               //ī��Ʈ
        sobj.setDouble("NEW_UPSO_AMT", NEW_UPSO_AMT);               //�ű� ���� �ݾ�
        return sobj;
    }
    //##**$$bra10_r12_search
    //##**$$end
}
