
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s22
{
    public bra04_s22()
    {
    }
    //##**$$sel_return_attch
    /*
    */
    public DOBJ CTLsel_return_attch(DOBJ dobj)
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
            dobj  = CALLsel_return_attch_SEL1(Conn, dobj);           //  ÷��������ȸ
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
    public DOBJ CTLsel_return_attch( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_return_attch_SEL1(Conn, dobj);           //  ÷��������ȸ
        return dobj;
    }
    // ÷��������ȸ
    public DOBJ CALLsel_return_attch_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_return_attch_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_return_attch_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INS_DAY = dobj.getRetObject("S").getRecord().get("INS_DAY");   //��� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.INS_DAY  ,  B.SEQ  ,  B.FILE_NM  ,  B.SVR_FILE_NM  ,  B.SVR_FILE_ROUT  ,  B.REMAK  FROM  GIBU.TBRA_REPT_RETURN_REQ  A  ,  GIBU.TBRA_REPT_RETURN_REQ_ATTCH  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.INS_DAY  =  B.INS_DAY   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.INS_DAY  =  :INS_DAY  ORDER  BY  SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        return sobj;
    }
    //##**$$sel_return_attch
    //##**$$mng_return_req
    /*
    */
    public DOBJ CTLmng_return_req(DOBJ dobj)
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
            dobj  = CALLmng_return_req_MIUD1(Conn, dobj);           //  ���Ҽ��� ÷������
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
    public DOBJ CTLmng_return_req( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_return_req_MIUD1(Conn, dobj);           //  ���Ҽ��� ÷������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ���Ҽ��� ÷������
    public DOBJ CALLmng_return_req_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_return_req_INS31(Conn, dobj);           //  ��ȯ��û ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_return_req_UPD36(Conn, dobj);           //  ��ȯ��û ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_return_req_DEL40(Conn, dobj);           //  ��ȯ��û ����
            }
        }
        return dobj;
    }
    // ��ȯ��û ����
    public DOBJ CALLmng_return_req_DEL40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL40");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL40");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_return_req_DEL40(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL40") ;
        rvobj.Println("DEL40");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_return_req_DEL40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //��� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_RETURN_REQ  \n";
        query +=" where INS_DAY=:INS_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        return sobj;
    }
    // ��ȯ��û ����
    public DOBJ CALLmng_return_req_INS31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS31");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS31");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_return_req_INS31(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS31") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_return_req_INS31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   CONFIRM1_DATE = dvobj.getRecord().get("CONFIRM1_DATE");   //Ȯ�� ����
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //�Ա� �ݾ�
        String   REPTPRES = dvobj.getRecord().get("REPTPRES");   //�Ա���
        String   REPT_ACCN_NUM = dvobj.getRecord().get("REPT_ACCN_NUM");   //�Ա� ���� ��ȣ
        String   RETURN_AMT = dvobj.getRecord().get("RETURN_AMT");   //��ȯ �ݾ�
        String   CONFIRM_ID1 = dvobj.getRecord().get("CONFIRM_ID1");   //Ȯ�� ����
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //��� ����
        String   CONFIRM_ID2 = dvobj.getRecord().get("CONFIRM_ID2");   //Ȯ�� ����
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_BANK_CD = dvobj.getRecord().get("REPT_BANK_CD");   //�Ա� ���� �ڵ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   REAL_AMT = dvobj.getRecord().getDouble("REAL_AMT");   //�������޾�
        String   ACCOUNT_NM = dvobj.getRecord().get("ACCOUNT_NM");   //������� �����ָ�
        double   KOMCA_AMT = dvobj.getRecord().getDouble("KOMCA_AMT");   //��ȸ �ݾ�
        String   REAS = dvobj.getRecord().get("REAS");   //����
        String   RETURN_BANK_CD = dvobj.getRecord().get("RETURN_BANK_CD");   //��ȯ ����
        String   RETURN_DAY = dvobj.getRecord().get("RETURN_DAY");   //����(��ȯ) ����
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        String   CONFIRM2_DATE = dvobj.getRecord().get("CONFIRM2_DATE");   //Ȯ�� ����
        double   REPT_CNT = dvobj.getRecord().getDouble("REPT_CNT");   //�Ա� ������ ��
        String   RETURN_ACCN_NUM = dvobj.getRecord().get("RETURN_ACCN_NUM");   //��ȯ ���¹�ȣ
        double   COMIS = dvobj.getRecord().getDouble("COMIS");   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_RETURN_REQ (COMIS, RETURN_ACCN_NUM, INSPRES_ID, REPT_CNT, CONFIRM2_DATE, REPT_GBN, RETURN_DAY, RETURN_BANK_CD, REAS, KOMCA_AMT, ACCOUNT_NM, INS_DATE, REAL_AMT, UPSO_CD, REMAK, REPT_BANK_CD, REPT_DAY, CONFIRM_ID2, INS_DAY, CONFIRM_ID1, RETURN_AMT, REPT_ACCN_NUM, REPTPRES, REPT_AMT, CONFIRM1_DATE)  \n";
        query +=" values(:COMIS , :RETURN_ACCN_NUM , :INSPRES_ID , :REPT_CNT , :CONFIRM2_DATE , :REPT_GBN , :RETURN_DAY , :RETURN_BANK_CD , :REAS , :KOMCA_AMT , :ACCOUNT_NM , SYSDATE, :REAL_AMT , :UPSO_CD , :REMAK , :REPT_BANK_CD , :REPT_DAY , :CONFIRM_ID2 , :INS_DAY , :CONFIRM_ID1 , :RETURN_AMT , :REPT_ACCN_NUM , :REPTPRES , :REPT_AMT , :CONFIRM1_DATE )";
        sobj.setSql(query);
        sobj.setString("CONFIRM1_DATE", CONFIRM1_DATE);               //Ȯ�� ����
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setString("REPTPRES", REPTPRES);               //�Ա���
        sobj.setString("REPT_ACCN_NUM", REPT_ACCN_NUM);               //�Ա� ���� ��ȣ
        sobj.setString("RETURN_AMT", RETURN_AMT);               //��ȯ �ݾ�
        sobj.setString("CONFIRM_ID1", CONFIRM_ID1);               //Ȯ�� ����
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setString("CONFIRM_ID2", CONFIRM_ID2);               //Ȯ�� ����
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_BANK_CD", REPT_BANK_CD);               //�Ա� ���� �ڵ�
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("REAL_AMT", REAL_AMT);               //�������޾�
        sobj.setString("ACCOUNT_NM", ACCOUNT_NM);               //������� �����ָ�
        sobj.setDouble("KOMCA_AMT", KOMCA_AMT);               //��ȸ �ݾ�
        sobj.setString("REAS", REAS);               //����
        sobj.setString("RETURN_BANK_CD", RETURN_BANK_CD);               //��ȯ ����
        sobj.setString("RETURN_DAY", RETURN_DAY);               //����(��ȯ) ����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("CONFIRM2_DATE", CONFIRM2_DATE);               //Ȯ�� ����
        sobj.setDouble("REPT_CNT", REPT_CNT);               //�Ա� ������ ��
        sobj.setString("RETURN_ACCN_NUM", RETURN_ACCN_NUM);               //��ȯ ���¹�ȣ
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // ��ȯ��û ����
    public DOBJ CALLmng_return_req_UPD36(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD36");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD36");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_return_req_UPD36(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD36") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_return_req_UPD36(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   CONFIRM1_DATE = dvobj.getRecord().get("CONFIRM1_DATE");   //Ȯ�� ����
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //�Ա� �ݾ�
        String   REPTPRES = dvobj.getRecord().get("REPTPRES");   //�Ա���
        String   REPT_ACCN_NUM = dvobj.getRecord().get("REPT_ACCN_NUM");   //�Ա� ���� ��ȣ
        String   RETURN_AMT = dvobj.getRecord().get("RETURN_AMT");   //��ȯ �ݾ�
        String   CONFIRM_ID1 = dvobj.getRecord().get("CONFIRM_ID1");   //Ȯ�� ����
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //��� ����
        String   CONFIRM_ID2 = dvobj.getRecord().get("CONFIRM_ID2");   //Ȯ�� ����
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_BANK_CD = dvobj.getRecord().get("REPT_BANK_CD");   //�Ա� ���� �ڵ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        double   REAL_AMT = dvobj.getRecord().getDouble("REAL_AMT");   //�������޾�
        String   ACCOUNT_NM = dvobj.getRecord().get("ACCOUNT_NM");   //������� �����ָ�
        double   KOMCA_AMT = dvobj.getRecord().getDouble("KOMCA_AMT");   //��ȸ �ݾ�
        String   REAS = dvobj.getRecord().get("REAS");   //����
        String   RETURN_BANK_CD = dvobj.getRecord().get("RETURN_BANK_CD");   //��ȯ ����
        String   RETURN_DAY = dvobj.getRecord().get("RETURN_DAY");   //����(��ȯ) ����
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        String   CONFIRM2_DATE = dvobj.getRecord().get("CONFIRM2_DATE");   //Ȯ�� ����
        double   REPT_CNT = dvobj.getRecord().getDouble("REPT_CNT");   //�Ա� ������ ��
        String   RETURN_ACCN_NUM = dvobj.getRecord().get("RETURN_ACCN_NUM");   //��ȯ ���¹�ȣ
        double   COMIS = dvobj.getRecord().getDouble("COMIS");   //������
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT_RETURN_REQ  \n";
        query +=" set COMIS=:COMIS , MODPRES_ID=:MODPRES_ID , RETURN_ACCN_NUM=:RETURN_ACCN_NUM , REPT_CNT=:REPT_CNT , CONFIRM2_DATE=:CONFIRM2_DATE , REPT_GBN=:REPT_GBN , RETURN_DAY=:RETURN_DAY , RETURN_BANK_CD=:RETURN_BANK_CD , REAS=:REAS , KOMCA_AMT=:KOMCA_AMT , ACCOUNT_NM=:ACCOUNT_NM , REAL_AMT=:REAL_AMT , REMAK=:REMAK , REPT_BANK_CD=:REPT_BANK_CD , REPT_DAY=:REPT_DAY , CONFIRM_ID2=:CONFIRM_ID2 , CONFIRM_ID1=:CONFIRM_ID1 , RETURN_AMT=:RETURN_AMT , REPT_ACCN_NUM=:REPT_ACCN_NUM , REPTPRES=:REPTPRES , REPT_AMT=:REPT_AMT , MOD_DATE=SYSDATE , CONFIRM1_DATE=:CONFIRM1_DATE  \n";
        query +=" where UPSO_CD=:UPSO_CD  \n";
        query +=" and INS_DAY=:INS_DAY";
        sobj.setSql(query);
        sobj.setString("CONFIRM1_DATE", CONFIRM1_DATE);               //Ȯ�� ����
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setString("REPTPRES", REPTPRES);               //�Ա���
        sobj.setString("REPT_ACCN_NUM", REPT_ACCN_NUM);               //�Ա� ���� ��ȣ
        sobj.setString("RETURN_AMT", RETURN_AMT);               //��ȯ �ݾ�
        sobj.setString("CONFIRM_ID1", CONFIRM_ID1);               //Ȯ�� ����
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setString("CONFIRM_ID2", CONFIRM_ID2);               //Ȯ�� ����
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_BANK_CD", REPT_BANK_CD);               //�Ա� ���� �ڵ�
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("REAL_AMT", REAL_AMT);               //�������޾�
        sobj.setString("ACCOUNT_NM", ACCOUNT_NM);               //������� �����ָ�
        sobj.setDouble("KOMCA_AMT", KOMCA_AMT);               //��ȸ �ݾ�
        sobj.setString("REAS", REAS);               //����
        sobj.setString("RETURN_BANK_CD", RETURN_BANK_CD);               //��ȯ ����
        sobj.setString("RETURN_DAY", RETURN_DAY);               //����(��ȯ) ����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("CONFIRM2_DATE", CONFIRM2_DATE);               //Ȯ�� ����
        sobj.setDouble("REPT_CNT", REPT_CNT);               //�Ա� ������ ��
        sobj.setString("RETURN_ACCN_NUM", RETURN_ACCN_NUM);               //��ȯ ���¹�ȣ
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    //##**$$mng_return_req
    //##**$$sel_return_req
    /*
    */
    public DOBJ CTLsel_return_req(DOBJ dobj)
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
            dobj  = CALLsel_return_req_SEL1(Conn, dobj);           //  ��ȯ��û��ȸ
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
    public DOBJ CTLsel_return_req( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_return_req_SEL1(Conn, dobj);           //  ��ȯ��û��ȸ
        return dobj;
    }
    // ��ȯ��û��ȸ
    public DOBJ CALLsel_return_req_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_return_req_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_return_req_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_TO = dobj.getRetObject("S").getRecord().get("REPT_TO");   //�Ա�������
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   INS_END = dobj.getRetObject("S").getRecord().get("INS_END");   //�����������
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INS_START = dobj.getRetObject("S").getRecord().get("INS_START");   //��Ͻ�������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   REPT_FROM = dobj.getRetObject("S").getRecord().get("REPT_FROM");   //�Աݽ�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT B.BRAN_CD , GIBU.GET_BRAN_NM(B.BRAN_CD) AS BRAN_NM , B.STAFF_CD , FIDU.GET_STAFF_NM(B.STAFF_CD) AS STAFF_NM , A.INS_DAY , A.UPSO_CD , B.UPSO_NM , A.REPT_GBN , A.REPT_DAY , A.REPT_BANK_CD , A.REPT_ACCN_NUM , A.REPTPRES , A.REPT_CNT , A.REPT_AMT , A.COMIS , A.KOMCA_AMT , A.RETURN_AMT , A.RETURN_BANK_CD , A.RETURN_ACCN_NUM , A.ACCOUNT_NM , A.REAS , A.REMAK , A.REAL_AMT , (CASE WHEN A.CONFIRM_ID1 IS NULL THEN '0' ELSE '1' END) AS CONFIRM1_CHK , A.CONFIRM_ID1 , FIDU.GET_STAFF_NM(A.CONFIRM_ID1) AS CONFIRM1_NM , A.CONFIRM1_DATE , (CASE WHEN A.CONFIRM_ID2 IS NULL THEN '0' ELSE '1' END) AS CONFIRM2_CHK , A.CONFIRM_ID2 , FIDU.GET_STAFF_NM(A.CONFIRM_ID2) AS CONFIRM2_NM , A.CONFIRM2_DATE , A.RETURN_DAY  ";
        query +=" FROM GIBU.TBRA_REPT_RETURN_REQ A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND B.BRAN_CD = DECODE(:BRAN_CD, 'AL', B.BRAN_CD, :BRAN_CD)  ";
        query +=" AND B.STAFF_CD = NVL(:STAFF_CD, B.STAFF_CD)  ";
        if( !UPSO_CD.equals("") )
        {
            query +=" AND A.UPSO_CD = :UPSO_CD  ";
        }
        if( !INS_START.equals("") )
        {
            query +=" AND A.INS_DAY BETWEEN :INS_START  ";
            query +=" AND :INS_END  ";
        }
        if( !REPT_FROM.equals("") )
        {
            query +=" AND A.CONFIRM2_DATE BETWEEN TO_DATE(:REPT_FROM, 'YYYYMMDD')  ";
            query +=" AND TO_DATE(:REPT_TO, 'YYYYMMDD')  ";
        }
        if( !GBN.equals("") )
        {
            query +=" AND ((:GBN = 1  ";
            query +=" AND A.CONFIRM_ID1 IS NULL)  ";
            query +=" OR (:GBN = 2  ";
            query +=" AND A.CONFIRM_ID1 IS NOT NULL  ";
            query +=" AND A.CONFIRM_ID2 IS NULL)  ";
            query +=" OR (:GBN = 3  ";
            query +=" AND A.CONFIRM_ID2 IS NOT NULL  ";
            query +=" AND A.RETURN_DAY IS NULL)  ";
            query +=" OR (:GBN = 4  ";
            query +=" AND A.RETURN_DAY IS NOT NULL))  ";
        }
        sobj.setSql(query);
        sobj.setString("REPT_TO", REPT_TO);               //�Ա�������
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("INS_END", INS_END);               //�����������
        if(!GBN.equals(""))
        {
            sobj.setString("GBN", GBN);               //����
        }
        if(!UPSO_CD.equals(""))
        {
            sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        }
        if(!INS_START.equals(""))
        {
            sobj.setString("INS_START", INS_START);               //��Ͻ�������
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        if(!REPT_FROM.equals(""))
        {
            sobj.setString("REPT_FROM", REPT_FROM);               //�Աݽ�����
        }
        return sobj;
    }
    //##**$$sel_return_req
    //##**$$mng_confirm
    /*
    */
    public DOBJ CTLmng_confirm(DOBJ dobj)
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
            if( dobj.getRetObject("S1").getRecord().get("GBN").equals("1"))
            {
                dobj  = CALLmng_confirm_MIUD3(Conn, dobj);           //  ������б�
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
            }
            else
            {
                dobj  = CALLmng_confirm_MIUD7(Conn, dobj);           //  ����ںб�
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
    public DOBJ CTLmng_confirm( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S1").getRecord().get("GBN").equals("1"))
        {
            dobj  = CALLmng_confirm_MIUD3(Conn, dobj);           //  ������б�
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        else
        {
            dobj  = CALLmng_confirm_MIUD7(Conn, dobj);           //  ����ںб�
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // ������б�
    public DOBJ CALLmng_confirm_MIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD3");
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
                dobj  = CALLmng_confirm_XIUD13(Conn, dobj);           //  ������Ȯ��
            }
        }
        return dobj;
    }
    // ������Ȯ��
    public DOBJ CALLmng_confirm_XIUD13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD13");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_confirm_XIUD13(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_confirm_XIUD13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CONFIRM_ID1 = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //Ȯ�� ����
        String   INS_DAY = dobj.getRetObject("R").getRecord().get("INS_DAY");   //��� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_RETURN_REQ  \n";
        query +=" SET CONFIRM_ID1 = :CONFIRM_ID1 , CONFIRM1_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND INS_DAY = :INS_DAY";
        sobj.setSql(query);
        sobj.setString("CONFIRM_ID1", CONFIRM_ID1);               //Ȯ�� ����
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ����ںб�
    public DOBJ CALLmng_confirm_MIUD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD7");
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
                dobj  = CALLmng_confirm_XIUD15(Conn, dobj);           //  �����Ȯ��
            }
        }
        return dobj;
    }
    // �����Ȯ��
    public DOBJ CALLmng_confirm_XIUD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD15");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_confirm_XIUD15(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD15");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_confirm_XIUD15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CONFIRM_ID2 = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //Ȯ�� ����
        String   INS_DAY = dobj.getRetObject("R").getRecord().get("INS_DAY");   //��� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_RETURN_REQ  \n";
        query +=" SET CONFIRM_ID2 = :CONFIRM_ID2 , CONFIRM2_DATE = SYSDATE  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND INS_DAY = :INS_DAY";
        sobj.setSql(query);
        sobj.setString("CONFIRM_ID2", CONFIRM_ID2);               //Ȯ�� ����
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$mng_confirm
    //##**$$mng_return_attch
    /*
    */
    public DOBJ CTLmng_return_attch(DOBJ dobj)
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
            dobj  = CALLmng_return_attch_SEL_FILE(Conn, dobj);           //  ���������
            dobj  = CALLmng_return_attch_MIUD1(Conn, dobj);           //  ���Ҽ��� ÷������
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
    public DOBJ CTLmng_return_attch( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_return_attch_SEL_FILE(Conn, dobj);           //  ���������
        dobj  = CALLmng_return_attch_MIUD1(Conn, dobj);           //  ���Ҽ��� ÷������
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ���������
    public DOBJ CALLmng_return_attch_SEL_FILE(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL_FILE");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL_FILE");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_return_attch_SEL_FILE(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL_FILE");
        rvobj.Println("SEL_FILE");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_return_attch_SEL_FILE(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INS_DAY = dobj.getRetObject("S").getRecord().get("INS_DAY");   //��� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  '/upload_file/GIBU/RETURN/'  ||  SUBSTR(:INS_DAY,  1,  6)  AS  DFILEPATH  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        return sobj;
    }
    // ���Ҽ��� ÷������
    public DOBJ CALLmng_return_attch_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_return_attch_SEL25(Conn, dobj);           //  ��������ο� ���ϸ�
                dobj  = CALLmng_return_attch_FUT26( dobj);        //  �����̵�
                dobj  = CALLmng_return_attch_FUT27( dobj);        //  �����̸��ٲٱ�
                dobj  = CALLmng_return_attch_INS31(Conn, dobj);           //  ���Ͼ��ε���������
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_return_attch_SEL31(Conn, dobj);           //  �������� ��ȸ(�������)
                dobj  = CALLmng_return_attch_FUT32( dobj);        //  ���ϻ���
                dobj  = CALLmng_return_attch_SEL33(Conn, dobj);           //  ��������ο� ���ϸ�
                dobj  = CALLmng_return_attch_FUT34( dobj);        //  �����̵�
                dobj  = CALLmng_return_attch_FUT35( dobj);        //  �����̸��ٲٱ�
                dobj  = CALLmng_return_attch_UPD36(Conn, dobj);           //  ���Ͼ��ε����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_return_attch_FUT39( dobj);        //  ���ϻ���
                dobj  = CALLmng_return_attch_DEL40(Conn, dobj);           //  ��������
            }
        }
        return dobj;
    }
    // ���ϻ���
    public DOBJ CALLmng_return_attch_FUT39(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT39");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT39");
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            wutil.delete(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString() );
        }
        dvobj.setName("FUT39") ;
        dvobj.Println("FUT39");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �������� ��ȸ(�������)
    public DOBJ CALLmng_return_attch_SEL31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL31");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL31");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_return_attch_SEL31(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL31");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_return_attch_SEL31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INS_DAY = dobj.getRetObject("R").getRecord().get("INS_DAY");   //��� ����
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SVR_FILE_NM  ,  SVR_FILE_ROUT  FROM  GIBU.TBRA_REPT_RETURN_REQ_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  INS_DAY  =  :INS_DAY   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // ��������ο� ���ϸ�
    public DOBJ CALLmng_return_attch_SEL25(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL25");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL25");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_return_attch_SEL25(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL25");
        rvobj.Println("SEL25");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_return_attch_SEL25(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INS_DAY = dobj.getRetObject("R").getRecord().get("INS_DAY");   //��� ����
        String   FILE_NM = dobj.getRetObject("R").getRecord().get("FILE_NM");   //���� ��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  ||  :UPSO_CD  ||  '-'  ||  :INS_DAY  ||  '-'  ||   \n";
        query +=" (SELECT  NVL(MAX(SEQ),  0)  +  1  FROM  GIBU.TBRA_REPT_RETURN_REQ_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  INS_DAY  =  :INS_DAY)  ||  '-'  ||  TO_CHAR(SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR(:FILE_NM,  INSTR(:FILE_NM,  '.',  '-1'))  AS  DFILENAME  ,   \n";
        query +=" (SELECT  NVL(MAX(SEQ),  0)  +  1  FROM  GIBU.TBRA_REPT_RETURN_REQ_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  INS_DAY  =  :INS_DAY)  AS  SEQ  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        return sobj;
    }
    // ��������
    public DOBJ CALLmng_return_attch_DEL40(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL40");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("DEL40");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_return_attch_DEL40(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL40") ;
        rvobj.Println("DEL40");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_return_attch_DEL40(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //��� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_RETURN_REQ_ATTCH  \n";
        query +=" where INS_DAY=:INS_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        return sobj;
    }
    // ���ϻ���
    public DOBJ CALLmng_return_attch_FUT32(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT32");
        VOBJ dvobj = dobj.getRetObject("SEL31");      //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("FUT32");
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            wutil.delete(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString() );
        }
        dvobj.setName("FUT32") ;
        dvobj.Println("FUT32");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �����̵�
    public DOBJ CALLmng_return_attch_FUT26(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT26");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT26");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT26") ;
        dvobj.Println("FUT26");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �����̸��ٲٱ�
    public DOBJ CALLmng_return_attch_FUT27(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT27");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT27");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dobj.getRetObject("SEL25").getRecord().get("DFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.rename(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILENAME").toString());
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT27") ;
        dvobj.Println("FUT27");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ��������ο� ���ϸ�
    public DOBJ CALLmng_return_attch_SEL33(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL33");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL33");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_return_attch_SEL33(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL33");
        rvobj.Println("SEL33");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_return_attch_SEL33(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INS_DAY = dobj.getRetObject("R").getRecord().get("INS_DAY");   //��� ����
        String   FILE_NM = dobj.getRetObject("R").getRecord().get("FILE_NM");   //���� ��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  ||  :UPSO_CD  ||  '-'  ||  :INS_DAY  ||  '-'  ||   \n";
        query +=" (SELECT  NVL(MAX(SEQ),  0)  +  1  FROM  GIBU.TBRA_REPT_RETURN_REQ_ATTCH  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  INS_DAY  =  :INS_DAY)  ||  '-'  ||  TO_CHAR(SYSTIMESTAMP,  'YYYYMMDDHH24MISSFF3')  ||  SUBSTR(:FILE_NM,  INSTR(:FILE_NM,  '.',  '-1'))  AS  DFILENAME  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        return sobj;
    }
    // ���Ͼ��ε���������
    public DOBJ CALLmng_return_attch_INS31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS31");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS31");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_return_attch_INS31(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS31") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_return_attch_INS31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   FILE_NM = dvobj.getRecord().get("FILE_NM");   //���� ��
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //��� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   SEQ = dobj.getRetObject("SEL25").getRecord().getDouble("SEQ");   //������ȣ
        String   SVR_FILE_NM = dobj.getRetObject("SEL25").getRecord().get("DFILENAME");   //���� ���� ��
        String   SVR_FILE_ROUT = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");   //���� ���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_RETURN_REQ_ATTCH (INS_DATE, INSPRES_ID, INS_DAY, UPSO_CD, SVR_FILE_ROUT, SVR_FILE_NM, SEQ, REMAK, FILE_NM)  \n";
        query +=" values(SYSDATE, :INSPRES_ID , :INS_DAY , :UPSO_CD , :SVR_FILE_ROUT , :SVR_FILE_NM , :SEQ , :REMAK , :FILE_NM )";
        sobj.setSql(query);
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("SVR_FILE_NM", SVR_FILE_NM);               //���� ���� ��
        sobj.setString("SVR_FILE_ROUT", SVR_FILE_ROUT);               //���� ���� ���
        return sobj;
    }
    // �����̵�
    public DOBJ CALLmng_return_attch_FUT34(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT34");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT34");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("SVR_FILE_ROUT");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT34") ;
        dvobj.Println("FUT34");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // �����̸��ٲٱ�
    public DOBJ CALLmng_return_attch_FUT35(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT35");
        VOBJ dvobj = dobj.getRetObject("R");      //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("FUT35");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dobj.getRetObject("SEL33").getRecord().get("DFILENAME");         //�������ϸ�
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�������ϰ��
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("SVR_FILE_NM");         //�ҽ����ϸ�
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");         //�ҽ����ϰ��
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.rename(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILENAME").toString());
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT35") ;
        dvobj.Println("FUT35");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // ���Ͼ��ε����
    public DOBJ CALLmng_return_attch_UPD36(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD36");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("UPD36");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_return_attch_UPD36(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD36") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_return_attch_UPD36(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        String   FILE_NM = dvobj.getRecord().get("FILE_NM");   //���� ��
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //��� ����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        double   SEQ = dobj.getRetObject("S").getRecord().getDouble("SEQ");   //������ȣ
        String   SVR_FILE_NM = dobj.getRetObject("SEL33").getRecord().get("DFILENAME");   //���� ���� ��
        String   SVR_FILE_ROUT = dobj.getRetObject("SEL_FILE").getRecord().get("DFILEPATH");   //���� ���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT_RETURN_REQ_ATTCH  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MOD_DATE=SYSDATE , SVR_FILE_ROUT=:SVR_FILE_ROUT , SVR_FILE_NM=:SVR_FILE_NM , REMAK=:REMAK , FILE_NM=:FILE_NM  \n";
        query +=" where INS_DAY=:INS_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("FILE_NM", FILE_NM);               //���� ��
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("SVR_FILE_NM", SVR_FILE_NM);               //���� ���� ��
        sobj.setString("SVR_FILE_ROUT", SVR_FILE_ROUT);               //���� ���� ���
        return sobj;
    }
    //##**$$mng_return_attch
    //##**$$mng_return_attch_remak
    /*
    */
    public DOBJ CTLmng_return_attch_remak(DOBJ dobj)
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
            dobj  = CALLmng_return_attch_remak_UPD6(Conn, dobj);           //  ��������
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
    public DOBJ CTLmng_return_attch_remak( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_return_attch_remak_UPD6(Conn, dobj);           //  ��������
        return dobj;
    }
    // ��������
    public DOBJ CALLmng_return_attch_remak_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_return_attch_remak_UPD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_return_attch_remak_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //��� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT_RETURN_REQ_ATTCH  \n";
        query +=" set REMAK=:REMAK  \n";
        query +=" where INS_DAY=:INS_DAY  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        return sobj;
    }
    //##**$$mng_return_attch_remak
    //##**$$end
}
