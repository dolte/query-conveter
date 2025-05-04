
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_r11
{
    public bra10_r11()
    {
    }
    //##**$$bra10_r11_select
    /*
    */
    public DOBJ CTLbra10_r11_select(DOBJ dobj)
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
            dobj  = CALLbra10_r11_select_SEL5(Conn, dobj);           //  ¡�� �Ѿ�, ���Ҽ� ��ȸ
            dobj  = CALLbra10_r11_select_SEL6(Conn, dobj);           //  ��ǥ�ݾ� ��ȸ
            dobj  = CALLbra10_r11_select_SEL7(Conn, dobj);           //  �������� ���
            dobj  = CALLbra10_r11_select_SEL8(Conn, dobj);           //  ¡���������� ��ȸ
            dobj  = CALLbra10_r11_select_SEL1(Conn, dobj);           //  ����¡�� �����Ѿ���ȸ
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
    public DOBJ CTLbra10_r11_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra10_r11_select_SEL5(Conn, dobj);           //  ¡�� �Ѿ�, ���Ҽ� ��ȸ
        dobj  = CALLbra10_r11_select_SEL6(Conn, dobj);           //  ��ǥ�ݾ� ��ȸ
        dobj  = CALLbra10_r11_select_SEL7(Conn, dobj);           //  �������� ���
        dobj  = CALLbra10_r11_select_SEL8(Conn, dobj);           //  ¡���������� ��ȸ
        dobj  = CALLbra10_r11_select_SEL1(Conn, dobj);           //  ����¡�� �����Ѿ���ȸ
        return dobj;
    }
    // ¡�� �Ѿ�, ���Ҽ� ��ȸ
    // ¡�� �Ѿ� ��ȸ
    public DOBJ CALLbra10_r11_select_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r11_select_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r11_select_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
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
    public DOBJ CALLbra10_r11_select_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r11_select_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r11_select_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
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
    public DOBJ CALLbra10_r11_select_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r11_select_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        rvobj.Println("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r11_select_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
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
    public DOBJ CALLbra10_r11_select_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r11_select_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        rvobj.Println("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r11_select_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
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
    // ����¡�� �����Ѿ���ȸ
    public DOBJ CALLbra10_r11_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra10_r11_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r11_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");        //���� ���
        String   APPL_YRMN_1 = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //���� ���
        double   BONUS_RATE = dobj.getRetObject("SEL8").getRecord().getDouble("BONUS_RATE");   //���� ����
        double   LEVY_AMT = dobj.getRetObject("SEL5").getRecord().getDouble("LEVY_AMT");   //¡�� �ݾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BRAN_CD  ,  B.DEPT_NM  AS  BRAN_NM  ,  A.PURE_LEVY_AMT  ,  A.LEVY_RATE  ,  A.LEVY_BONUS  ,  TO_CHAR(TRUNC(:LEVY_AMT  *  :BONUS_RATE  /  100  *  0.3),'999,999,999,999,999,999,999')  AS  TOT_BONUS  FROM  GIBU.TBRA_BRAN_BONUS_BRE  A  ,  (   \n";
        query +=" SELECT  XB.GIBU  ,  XB.DEPT_NM  FROM  (   \n";
        query +=" SELECT  DISTINCT  CASE  WHEN  GIBU  IN  ('H',  'I')  THEN  'I'  WHEN  GIBU  IN  ('J',  'K',  'O')  THEN  'K'  WHEN  GIBU  IN  ('B',  'C')  THEN  'B'  ELSE  GIBU  END  GIBU  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  LIKE  '1060%'   \n";
        query +=" AND  GIBU  IS  NOT  NULL  )  XA  ,  INSA.TCPM_DEPT  XB  WHERE  XA.GIBU  =  XB.GIBU  )  B  WHERE  A.BRAN_CD  =  B.GIBU   \n";
        query +=" AND  A.APPL_YRMN  =  SUBSTR(:APPL_YRMN_1,0,6)  ORDER  BY  A.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN_1", APPL_YRMN_1);               //���� ���
        sobj.setDouble("BONUS_RATE", BONUS_RATE);               //���� ����
        sobj.setDouble("LEVY_AMT", LEVY_AMT);               //¡�� �ݾ�
        return sobj;
    }
    //##**$$bra10_r11_select
    //##**$$end
}
