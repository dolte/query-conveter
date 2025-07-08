
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac01_s05
{
    public tac01_s05()
    {
    }
    //##**$$rcvb_susps_excel_save
    /*
    */
    public DOBJ CTLrcvb_susps_excel_save(DOBJ dobj)
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
            dobj  = CALLrcvb_susps_excel_save_SEL11(Conn, dobj);           //  �̼�����ī��Ʈ��ȸ
            if( dobj.getRetObject("SEL11").getRecord().get("COUNT").equals("0"))
            {
                dobj  = CALLrcvb_susps_excel_save_INS15(Conn, dobj);           //  �̼�������������
            }
            else
            {
                dobj  = CALLrcvb_susps_excel_save_XIUD17(Conn, dobj);           //  �����ͻ���
                dobj  = CALLrcvb_susps_excel_save_INS14(Conn, dobj);           //  �̼�������������
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
    public DOBJ CTLrcvb_susps_excel_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrcvb_susps_excel_save_SEL11(Conn, dobj);           //  �̼�����ī��Ʈ��ȸ
        if( dobj.getRetObject("SEL11").getRecord().get("COUNT").equals("0"))
        {
            dobj  = CALLrcvb_susps_excel_save_INS15(Conn, dobj);           //  �̼�������������
        }
        else
        {
            dobj  = CALLrcvb_susps_excel_save_XIUD17(Conn, dobj);           //  �����ͻ���
            dobj  = CALLrcvb_susps_excel_save_INS14(Conn, dobj);           //  �̼�������������
        }
        return dobj;
    }
    // �̼�����ī��Ʈ��ȸ
    // �̼�����ī��Ʈ��ȸ
    public DOBJ CALLrcvb_susps_excel_save_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrcvb_susps_excel_save_SEL11(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrcvb_susps_excel_save_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GUBUN = dobj.getRetObject("S").getRecord().get("GUBUN");   //����
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_NUMBER(COUNT(1))  AS  COUNT  FROM  FIDU.TTAC_RCVB_SUSPS_EXCEL  WHERE  YRMN  =  :YRMN   \n";
        query +=" AND  GUBUN  =  :GUBUN ";
        sobj.setSql(query);
        sobj.setString("GUBUN", GUBUN);               //����
        sobj.setString("YRMN", YRMN);               //���
        return sobj;
    }
    // �̼�������������
    // �̼�������������
    public DOBJ CALLrcvb_susps_excel_save_INS15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS15");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrcvb_susps_excel_save_INS15(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS15") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrcvb_susps_excel_save_INS15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   YRMN = dvobj.getRecord().get("YRMN");   //���
        String   REG_DATE = dvobj.getRecord().get("REG_DATE");   //����Ͻ�
        String   BSCON_NM = dvobj.getRecord().get("BSCON_NM");   //�ŷ�ó ��
        String   CONTENT = dvobj.getRecord().get("CONTENT");   //�Խù� ����
        String   GUBUN = dvobj.getRecord().get("GUBUN");   //����
        String   GUBUN1 = dvobj.getRecord().get("GUBUN1");   //����1
        double   AMT = dvobj.getRecord().getDouble("AMT");   //�ݾ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_RCVB_SUSPS_EXCEL (INS_DATE, AMT, INSPRES_ID, GUBUN1, GUBUN, CONTENT, BSCON_NM, REG_DATE, YRMN)  \n";
        query +=" values(SYSDATE, :AMT , :INSPRES_ID , :GUBUN1 , :GUBUN , :CONTENT , :BSCON_NM , :REG_DATE , :YRMN )";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        sobj.setString("REG_DATE", REG_DATE);               //����Ͻ�
        sobj.setString("BSCON_NM", BSCON_NM);               //�ŷ�ó ��
        sobj.setString("CONTENT", CONTENT);               //�Խù� ����
        sobj.setString("GUBUN", GUBUN);               //����
        sobj.setString("GUBUN1", GUBUN1);               //����1
        sobj.setDouble("AMT", AMT);               //�ݾ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // �����ͻ���
    // �����ͻ���
    public DOBJ CALLrcvb_susps_excel_save_XIUD17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD17");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrcvb_susps_excel_save_XIUD17(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD17");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrcvb_susps_excel_save_XIUD17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GUBUN = dobj.getRetObject("S").getRecord().get("GUBUN");   //����
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM FIDU.TTAC_RCVB_SUSPS_EXCEL  \n";
        query +=" WHERE YRMN = :YRMN  \n";
        query +=" AND GUBUN = :GUBUN";
        sobj.setSql(query);
        sobj.setString("GUBUN", GUBUN);               //����
        sobj.setString("YRMN", YRMN);               //���
        return sobj;
    }
    // �̼�������������
    // �̼�������������
    public DOBJ CALLrcvb_susps_excel_save_INS14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS14");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrcvb_susps_excel_save_INS14(dobj, dvobj);
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
    private SQLObject SQLrcvb_susps_excel_save_INS14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   YRMN = dvobj.getRecord().get("YRMN");   //���
        String   REG_DATE = dvobj.getRecord().get("REG_DATE");   //����Ͻ�
        String   BSCON_NM = dvobj.getRecord().get("BSCON_NM");   //�ŷ�ó ��
        String   CONTENT = dvobj.getRecord().get("CONTENT");   //�Խù� ����
        String   GUBUN = dvobj.getRecord().get("GUBUN");   //����
        String   GUBUN1 = dvobj.getRecord().get("GUBUN1");   //����1
        double   AMT = dvobj.getRecord().getDouble("AMT");   //�ݾ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_RCVB_SUSPS_EXCEL (INS_DATE, AMT, INSPRES_ID, GUBUN1, GUBUN, CONTENT, BSCON_NM, REG_DATE, YRMN)  \n";
        query +=" values(SYSDATE, :AMT , :INSPRES_ID , :GUBUN1 , :GUBUN , :CONTENT , :BSCON_NM , :REG_DATE , :YRMN )";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //���
        sobj.setString("REG_DATE", REG_DATE);               //����Ͻ�
        sobj.setString("BSCON_NM", BSCON_NM);               //�ŷ�ó ��
        sobj.setString("CONTENT", CONTENT);               //�Խù� ����
        sobj.setString("GUBUN", GUBUN);               //����
        sobj.setString("GUBUN1", GUBUN1);               //����1
        sobj.setDouble("AMT", AMT);               //�ݾ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    //##**$$rcvb_susps_excel_save
    //##**$$rcvb_susps_excel_sel
    /*
    */
    public DOBJ CTLrcvb_susps_excel_sel(DOBJ dobj)
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
            dobj  = CALLrcvb_susps_excel_sel_SEL1(Conn, dobj);           //  �̼�����������ȸ
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
    public DOBJ CTLrcvb_susps_excel_sel( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrcvb_susps_excel_sel_SEL1(Conn, dobj);           //  �̼�����������ȸ
        return dobj;
    }
    // �̼�����������ȸ
    // �̼�����������ȸ
    public DOBJ CALLrcvb_susps_excel_sel_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrcvb_susps_excel_sel_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrcvb_susps_excel_sel_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GUBUN1 = dobj.getRetObject("S").getRecord().get("GUBUN1");   //����1
        String   GUBUN = dobj.getRetObject("S").getRecord().get("GUBUN");   //����
        String   BSCON_NM = dobj.getRetObject("S").getRecord().get("BSCON_NM");   //�ŷ�ó ��
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT GUBUN ,REG_DATE ,BSCON_NM ,CONTENT ,GUBUN1 ,AMT  ";
        query +=" FROM FIDU.TTAC_RCVB_SUSPS_EXCEL  ";
        query +=" WHERE YRMN = :YRMN  ";
        query +=" AND GUBUN = :GUBUN  ";
        if( !GUBUN1.equals("") )
        {
            query +=" AND GUBUN1 LIKE '%'||:GUBUN1||'%'  ";
        }
        if( !BSCON_NM.equals("") )
        {
            query +=" AND BSCON_NM LIKE '%'||:BSCON_NM||'%'  ";
        }
        sobj.setSql(query);
        if(!GUBUN1.equals(""))
        {
            sobj.setString("GUBUN1", GUBUN1);               //����1
        }
        sobj.setString("GUBUN", GUBUN);               //����
        if(!BSCON_NM.equals(""))
        {
            sobj.setString("BSCON_NM", BSCON_NM);               //�ŷ�ó ��
        }
        sobj.setString("YRMN", YRMN);               //���
        return sobj;
    }
    //##**$$rcvb_susps_excel_sel
    //##**$$end
}
