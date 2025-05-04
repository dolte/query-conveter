
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s06
{
    public bra10_s06()
    {
    }
    //##**$$levy_target_save
    /*
    */
    public DOBJ CTLlevy_target_save(DOBJ dobj)
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
            dobj  = CALLlevy_target_save_XIUD14(Conn, dobj);           //  �������� ����
            dobj  = CALLlevy_target_save_MPD6(Conn, dobj);           //  �ο� ���� ó��
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
    public DOBJ CTLlevy_target_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlevy_target_save_XIUD14(Conn, dobj);           //  �������� ����
        dobj  = CALLlevy_target_save_MPD6(Conn, dobj);           //  �ο� ���� ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �������� ����
    public DOBJ CALLlevy_target_save_XIUD14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD14");
        VOBJ dvobj = dobj.getRetObject("S1");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("XIUD14");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlevy_target_save_XIUD14(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD14");
        rvobj.Println("XIUD14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlevy_target_save_XIUD14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YEAR = dobj.getRetObject("S1").getRecord().get("APPL_YEAR");   //���� �⵵
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_LEVY_TARGET  \n";
        query +=" WHERE SUBSTR(APPL_YRMN, 1, 4) = :APPL_YEAR";
        sobj.setSql(query);
        sobj.setString("APPL_YEAR", APPL_YEAR);               //���� �⵵
        return sobj;
    }
    // �ο� ���� ó��
    public DOBJ CALLlevy_target_save_MPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD6");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().getDouble("MON_TARGET_AMT") > 0)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLlevy_target_save_XIUD8(Conn, dobj);           //  ��ǥ �ݾ� ����
            }
        }
        return dobj;
    }
    // ��ǥ �ݾ� ����
    public DOBJ CALLlevy_target_save_XIUD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD8");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD8");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLlevy_target_save_XIUD8(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD8");
        rvobj.Println("XIUD8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlevy_target_save_XIUD8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YEAR = dobj.getRetObject("S1").getRecord().get("APPL_YEAR");   //���� �⵵
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("R").getRecord().get("INSPRES_ID");   //����� ID
        double   MON_TARGET_AMT = dobj.getRetObject("R").getRecord().getDouble("MON_TARGET_AMT");   //���� ��ǥ �ݾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_LEVY_TARGET ( APPL_YRMN , BRAN_CD , MON_TARGET_AMT , INSPRES_ID , INS_DATE ) SELECT XA.APPL_YRMN , :BRAN_CD , TRUNC((:MON_TARGET_AMT / 12), 0) , :INSPRES_ID , SYSDATE FROM ( SELECT YYYY || MM APPL_YRMN FROM GIBU.COPY_YY A , GIBU.COPY_MM B WHERE A.YYYY = :APPL_YEAR ) XA";
        sobj.setSql(query);
        sobj.setString("APPL_YEAR", APPL_YEAR);               //���� �⵵
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("MON_TARGET_AMT", MON_TARGET_AMT);               //���� ��ǥ �ݾ�
        return sobj;
    }
    //##**$$levy_target_save
    //##**$$levy_target_confirm
    /*
    */
    public DOBJ CTLlevy_target_confirm(DOBJ dobj)
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
            dobj  = CALLlevy_target_confirm_SEL1(Conn, dobj);           //  ��������ȸ
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
    public DOBJ CTLlevy_target_confirm( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlevy_target_confirm_SEL1(Conn, dobj);           //  ��������ȸ
        return dobj;
    }
    // ��������ȸ
    public DOBJ CALLlevy_target_confirm_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLlevy_target_confirm_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlevy_target_confirm_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YEAR = dobj.getRetObject("S").getRecord().get("APPL_YEAR");   //���� �⵵
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(BRAN_CD)  CNT  FROM  GIBU.TBRA_LEVY_TARGET  WHERE  SUBSTR(APPL_YRMN,  1,  4)  =  :APPL_YEAR ";
        sobj.setSql(query);
        sobj.setString("APPL_YEAR", APPL_YEAR);               //���� �⵵
        return sobj;
    }
    //##**$$levy_target_confirm
    //##**$$levy_target_select
    /*
    */
    public DOBJ CTLlevy_target_select(DOBJ dobj)
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
            dobj  = CALLlevy_target_select_SEL1(Conn, dobj);           //  ���� ��ǥ�ݾ� ��ȸ
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
    public DOBJ CTLlevy_target_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlevy_target_select_SEL1(Conn, dobj);           //  ���� ��ǥ�ݾ� ��ȸ
        return dobj;
    }
    // ���� ��ǥ�ݾ� ��ȸ
    public DOBJ CALLlevy_target_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLlevy_target_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlevy_target_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.APPL_YRMN  ,  A.BRAN_CD  ,  A.MON_TARGET_AMT  ,  A.INSPRES_ID  ,  TO_CHAR(A.INS_DATE,  'YYYYMMDD')  INS_DATE  ,  B.DEPT_NM  ,  C.HAN_NM  STAFF_NM  FROM  GIBU.TBRA_LEVY_TARGET  A  ,  INSA.TCPM_DEPT  B  ,  INSA.TINS_MST01  C  WHERE  A.APPL_YRMN  =  :APPL_YRMN   \n";
        query +=" AND  B.GIBU  =  A.BRAN_CD   \n";
        query +=" AND  B.DEPT_CD  BETWEEN  '106010000'   \n";
        query +=" AND  '106099999'   \n";
        query +=" AND  C.STAFF_NUM(+)  =  A.INSPRES_ID  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN", APPL_YRMN);               //���� ���
        return sobj;
    }
    //##**$$levy_target_select
    //##**$$end
}
