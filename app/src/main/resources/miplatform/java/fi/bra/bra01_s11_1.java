
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s11_1
{
    public bra01_s11_1()
    {
    }
    //##**$$bra01_s11_save01
    /* * ���α׷��� : bra01_s11
    * �ۼ��� : 999999
    * �ۼ��� : 2009/10/16
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLbra01_s11_save01(DOBJ dobj)
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
            dobj  = CALLbra01_s11_save01_MPD1(Conn, dobj);           //  ��꼭�߱޵�����
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
    public DOBJ CTLbra01_s11_save01( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_save01_MPD1(Conn, dobj);           //  ��꼭�߱޵�����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ��꼭�߱޵�����
    public DOBJ CALLbra01_s11_save01_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MPD1");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("CRUD").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra01_s11_save01_SEL7(Conn, dobj);           //  �߱��Ϻ��濩��
                if( dobj.getRetObject("SEL7").getRecord().get("UPD_YN").equals("1"))
                {
                    dobj  = CALLbra01_s11_save01_UPD9(Conn, dobj);           //  ��꼭�߱޼���
                }
                else
                {
                    dobj  = CALLbra01_s11_save01_UPD6(Conn, dobj);           //  ��꼭�߱޼���
                }
            }
            else if( dvobj.getRecord().get("CRUD").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra01_s11_save01_SEL11(Conn, dobj);           //  SEQ����
                dobj  = CALLbra01_s11_save01_INS5(Conn, dobj);           //  ��꼭�߱��Է�
            }
        }
        return dobj;
    }
    // �߱��Ϻ��濩��
    public DOBJ CALLbra01_s11_save01_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra01_s11_save01_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_save01_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_YRMN = dobj.getRetObject("R").getRecord().get("APPTN_YRMN");   //��û �Ͻ�
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("ISS_DAY");   //���� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //������ȣ
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(ISS_DAY,:ISS_DAY,'1','0')  AS  UPD_YN  FROM  GIBU.TBRA_BILL_ISS_MNG  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  APPTN_YRMN  =  :APPTN_YRMN   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //��û �Ͻ�
        sobj.setString("ISS_DAY", ISS_DAY);               //���� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // SEQ����
    public DOBJ CALLbra01_s11_save01_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra01_s11_save01_SEL11(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        rvobj.Println("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_save01_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPTN_YRMN ="";        //��û �Ͻ�
        String   APPTN_YRMN_1 = dobj.getRetObject("S1").getRecord().get("ISSUE_YRMN");   //��û �Ͻ�
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(to_number(MAX(SEQ)),  0)  +1  AS  SEQ  FROM  GIBU.TBRA_BILL_ISS_MNG  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  APPTN_YRMN  =  SUBSTR(  :APPTN_YRMN_1,1,6  )   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN_1", APPTN_YRMN_1);               //��û �Ͻ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ��꼭�߱��Է�
    public DOBJ CALLbra01_s11_save01_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra01_s11_save01_INS5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        rvobj.Println("INS5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_save01_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPRV_NUM ="";        //���� ��ȣ
        String APPTN_YRMN ="";        //��û �Ͻ�
        String INS_DATE ="";        //��� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   APPRV_NUM_2 = dobj.getRetObject("R").getRecord().get("ISS_DAY");   //���� ��ȣ
        String   APPRV_NUM_1 = dobj.getRetObject("R").getRecord().get("ISS_DAY");   //���� ��ȣ
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   ISS_DAY = dvobj.getRecord().get("ISS_DAY");   //���� ����
        String   ISS_BRE = dvobj.getRecord().get("ISS_BRE");   //���� ����
        double   ISS_AMT = dvobj.getRecord().getDouble("ISS_AMT");   //���� �ݾ�
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        String   APPTN_YRMN_1 = dobj.getRetObject("S1").getRecord().get("ISSUE_YRMN");   //��û �Ͻ�
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //��꼭 ����
        String  APPTN_GBN="";          //��û ����
        if( ( dobj.getRetObject("R").getRecord().get("APPTN_GBN").equals("") ))
        {
            APPTN_GBN ="1";
        }
        else
        {
            APPTN_GBN = dobj.getRetObject("R").getRecord().get("APPTN_GBN");
        }
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   ISS_COMPL_YN = dobj.getRetObject("R").getRecord().get("ISSADD_YN");   //���� �Ϸ� ����
        double   SEQ = dobj.getRetObject("SEL11").getRecord().getDouble("SEQ");   //������ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BILL_ISS_MNG (ISS_COMPL_YN, INSPRES_ID, BILL_GBN, APPTN_YRMN, SEQ, BSCON_CD, INS_DATE, ISS_AMT, ISS_BRE, ISS_DAY, UPSO_CD, APPTN_GBN, BRAN_CD, APPRV_NUM, REMAK)  \n";
        query +=" values(:ISS_COMPL_YN , :INSPRES_ID , :BILL_GBN , SUBSTR( :APPTN_YRMN_1,1,6 ), :SEQ , :BSCON_CD , SYSDATE, :ISS_AMT , :ISS_BRE , :ISS_DAY , :UPSO_CD , :APPTN_GBN , :BRAN_CD , (SELECT :APPRV_NUM_1 || LPAD(NVL(SUBSTR(MAX(APPRV_NUM),9,3) + 1,1),3,'0') FROM GIBU.TBRA_BILL_ISS_MNG WHERE ISS_DAY = :APPRV_NUM_2), :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("APPRV_NUM_2", APPRV_NUM_2);               //���� ��ȣ
        sobj.setString("APPRV_NUM_1", APPRV_NUM_1);               //���� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("ISS_DAY", ISS_DAY);               //���� ����
        sobj.setString("ISS_BRE", ISS_BRE);               //���� ����
        sobj.setDouble("ISS_AMT", ISS_AMT);               //���� �ݾ�
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setString("APPTN_YRMN_1", APPTN_YRMN_1);               //��û �Ͻ�
        sobj.setString("BILL_GBN", BILL_GBN);               //��꼭 ����
        sobj.setString("APPTN_GBN", APPTN_GBN);               //��û ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("ISS_COMPL_YN", ISS_COMPL_YN);               //���� �Ϸ� ����
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        return sobj;
    }
    // ��꼭�߱޼���
    public DOBJ CALLbra01_s11_save01_UPD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra01_s11_save01_UPD9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_save01_UPD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPTN_YRMN = dobj.getRetObject("S1").getRecord().get("ISSUE_YRMN");        //��û �Ͻ�
        String MOD_DATE ="";        //���� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   ISS_DAY = dvobj.getRecord().get("ISS_DAY");   //���� ����
        String   ISS_BRE = dvobj.getRecord().get("ISS_BRE");   //���� ����
        double   ISS_AMT = dvobj.getRecord().getDouble("ISS_AMT");   //���� �ݾ�
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   APPTN_YRMN_1 = dobj.getRetObject("S1").getRecord().get("ISSUE_YRMN");   //��û �Ͻ�
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //��꼭 ����
        String   ISS_COMPL_YN = dobj.getRetObject("R").getRecord().get("ISSADD_YN");   //���� �Ϸ� ����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BILL_ISS_MNG  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ISS_COMPL_YN=:ISS_COMPL_YN , BILL_GBN=:BILL_GBN , BSCON_CD=:BSCON_CD , ISS_AMT=:ISS_AMT , ISS_BRE=:ISS_BRE , ISS_DAY=:ISS_DAY , MOD_DATE=SYSDATE , REMAK=:REMAK  \n";
        query +=" where APPTN_YRMN=SUBSTR(:APPTN_YRMN_1,0,6)  \n";
        query +=" and SEQ=:SEQ  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("ISS_DAY", ISS_DAY);               //���� ����
        sobj.setString("ISS_BRE", ISS_BRE);               //���� ����
        sobj.setDouble("ISS_AMT", ISS_AMT);               //���� �ݾ�
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("APPTN_YRMN_1", APPTN_YRMN_1);               //��û �Ͻ�
        sobj.setString("BILL_GBN", BILL_GBN);               //��꼭 ����
        sobj.setString("ISS_COMPL_YN", ISS_COMPL_YN);               //���� �Ϸ� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    // ��꼭�߱޼���
    public DOBJ CALLbra01_s11_save01_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra01_s11_save01_UPD6(dobj, dvobj);
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
    private SQLObject SQLbra01_s11_save01_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPRV_NUM ="";        //���� ��ȣ
        String APPTN_YRMN = dobj.getRetObject("S1").getRecord().get("ISSUE_YRMN");        //��û �Ͻ�
        String MOD_DATE ="";        //���� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   APPRV_NUM_2 = dobj.getRetObject("R").getRecord().get("ISS_DAY");   //���� ��ȣ
        String   APPRV_NUM_1 = dobj.getRetObject("R").getRecord().get("ISS_DAY");   //���� ��ȣ
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   ISS_DAY = dvobj.getRecord().get("ISS_DAY");   //���� ����
        String   ISS_BRE = dvobj.getRecord().get("ISS_BRE");   //���� ����
        double   ISS_AMT = dvobj.getRecord().getDouble("ISS_AMT");   //���� �ݾ�
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //�ŷ�ó �ڵ�
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   APPTN_YRMN_1 = dobj.getRetObject("S1").getRecord().get("ISSUE_YRMN");   //��û �Ͻ�
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //��꼭 ����
        String   ISS_COMPL_YN = dobj.getRetObject("R").getRecord().get("ISSADD_YN");   //���� �Ϸ� ����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BILL_ISS_MNG  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ISS_COMPL_YN=:ISS_COMPL_YN , BILL_GBN=:BILL_GBN , BSCON_CD=:BSCON_CD , ISS_AMT=:ISS_AMT , ISS_BRE=:ISS_BRE , ISS_DAY=:ISS_DAY , MOD_DATE=SYSDATE , APPRV_NUM=(SELECT :APPRV_NUM_1 || LPAD(NVL(SUBSTR(MAX(APPRV_NUM),9,3) + 1,1),3,'0') FROM GIBU.TBRA_BILL_ISS_MNG  \n";
        query +=" WHERE ISS_DAY = :APPRV_NUM_2) , REMAK=:REMAK  \n";
        query +=" where APPTN_YRMN=SUBSTR(:APPTN_YRMN_1,0,6)  \n";
        query +=" and SEQ=:SEQ  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("APPRV_NUM_2", APPRV_NUM_2);               //���� ��ȣ
        sobj.setString("APPRV_NUM_1", APPRV_NUM_1);               //���� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("ISS_DAY", ISS_DAY);               //���� ����
        sobj.setString("ISS_BRE", ISS_BRE);               //���� ����
        sobj.setDouble("ISS_AMT", ISS_AMT);               //���� �ݾ�
        sobj.setString("BSCON_CD", BSCON_CD);               //�ŷ�ó �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("APPTN_YRMN_1", APPTN_YRMN_1);               //��û �Ͻ�
        sobj.setString("BILL_GBN", BILL_GBN);               //��꼭 ����
        sobj.setString("ISS_COMPL_YN", ISS_COMPL_YN);               //���� �Ϸ� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        return sobj;
    }
    //##**$$bra01_s11_save01
    //##**$$bra01_s11_delete
    /* * ���α׷��� : bra01_s11
    * �ۼ��� : 999999
    * �ۼ��� : 2009/10/08
    * ���� :
    * ������1:
    * ������ :
    * �������� :
    */
    public DOBJ CTLbra01_s11_delete(DOBJ dobj)
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
            dobj  = CALLbra01_s11_delete_MIUD1(Conn, dobj);           //  ����
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
    public DOBJ CTLbra01_s11_delete( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_delete_MIUD1(Conn, dobj);           //  ����
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // ����
    public DOBJ CALLbra01_s11_delete_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra01_s11_delete_DEL5(Conn, dobj);           //  ��꼭�߱޻���
            }
        }
        return dobj;
    }
    // ��꼭�߱޻���
    public DOBJ CALLbra01_s11_delete_DEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra01_s11_delete_DEL5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_delete_DEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //������ȣ
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   APPTN_YRMN = dvobj.getRecord().get("APPTN_YRMN");   //��û �Ͻ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BILL_ISS_MNG  \n";
        query +=" where APPTN_YRMN=:APPTN_YRMN  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setDouble("SEQ", SEQ);               //������ȣ
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //��û �Ͻ�
        return sobj;
    }
    //##**$$bra01_s11_delete
    //##**$$bra01_s11_select01
    /* * ���α׷��� : bra01_s11
    * �ۼ��� : 999999
    * �ۼ��� : 2009/10/13
    * ���� :
    * ������1: 2010/02/10
    * ������ :
    * �������� : �������� �������°� ����
    * ������1: 2010/06/10
    * ������ : �ǳ���
    * �������� : '1' AS ISSADD_YN => NVL(A.ISS_COMPL_YN,0) AS ISSADD_YN
    */
    public DOBJ CTLbra01_s11_select01(DOBJ dobj)
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
            dobj  = CALLbra01_s11_select01_SEL1(Conn, dobj);           //  ��꼭�߱���ȸ
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
    public DOBJ CTLbra01_s11_select01( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_select01_SEL1(Conn, dobj);           //  ��꼭�߱���ȸ
        return dobj;
    }
    // ��꼭�߱���ȸ
    public DOBJ CALLbra01_s11_select01_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLbra01_s11_select01_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_select01_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPTN_YRMN ="";        //��û �Ͻ�
        String   APPTN_YRMN_1 = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");   //��û �Ͻ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  B.UPSO_NM  ,  A.BSCON_CD  ,  A.SEQ  ,  C.BSCONHAN_NM  ,  A.BRAN_CD  ,  D.DEPT_NM  AS  BRAN_NM  ,  A.APPTN_YRMN  ,  A.ISS_BRE  ,  A.ISS_AMT  ,  A.BILL_GBN  ,  A.REMAK  ,  A.ISS_DAY  ,  NVL(A.ISS_COMPL_YN,0)  AS  ISS_COMPL_YN  ,  'U'  AS  CRUD  ,  NVL(A.ISS_COMPL_YN,0)  AS  ISSADD_YN  ,  A.BILL_NUM  ,  DECODE(A.APPTN_GBN,'1','��ü','2','����')  APPTN_GBN  FROM  GIBU.TBRA_BILL_ISS_MNG  A  ,  GIBU.TBRA_UPSO  B  ,  FIDU.TLEV_BSCON  C  ,  INSA.TCPM_DEPT  D  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  A.BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.APPTN_YRMN  =  SUBSTR(:APPTN_YRMN_1,0,6)   \n";
        query +=" AND  B.BRAN_CD  =  A.BRAN_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.BSCON_CD  =  A.BSCON_CD   \n";
        query +=" AND  D.GIBU  =  A.BRAN_CD  ORDER  BY  B.BRAN_CD,  A.INS_DATE,  A.BSCON_CD ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN_1", APPTN_YRMN_1);               //��û �Ͻ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$bra01_s11_select01
    //##**$$end
}
