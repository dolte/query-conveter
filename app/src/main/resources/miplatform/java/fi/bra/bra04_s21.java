
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s21
{
    public bra04_s21()
    {
    }
    //##**$$sel_card_req
    /*
    */
    public DOBJ CTLsel_card_req(DOBJ dobj)
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
            dobj  = CALLsel_card_req_SEL1(Conn, dobj);           //  ī�������û������ȸ
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
    public DOBJ CTLsel_card_req( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_card_req_SEL1(Conn, dobj);           //  ī�������û������ȸ
        return dobj;
    }
    // ī�������û������ȸ
    public DOBJ CALLsel_card_req_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_card_req_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_card_req_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_TO = dvobj.getRecord().get("REPT_TO");   //�Ա�������
        String   CARD_GBN = dvobj.getRecord().get("CARD_GBN");   //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   INS_END = dvobj.getRecord().get("INS_END");   //�����������
        String   PAYTRM_START_DAY = dvobj.getRecord().get("PAYTRM_START_DAY");   //���αⰣ ���� ����
        String   PAYTRM_END_DAY = dvobj.getRecord().get("PAYTRM_END_DAY");   //���αⰣ ���� ����
        String   INS_START = dvobj.getRecord().get("INS_START");   //��Ͻ�������
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        String   REPT_FROM = dvobj.getRecord().get("REPT_FROM");   //�Աݽ�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.BRAN_CD , GIBU.GET_BRAN_NM(A.BRAN_CD) AS BRAN_NM , A.STAFF_CD , FIDU.GET_STAFF_NM(A.STAFF_CD) AS STAFF_NM , A.INS_DAY , A.INS_NUM , A.UPSO_CD , B.UPSO_NM , A.NONPY_TERM , A.NONPY_AMT , A.CARD_GBN , A.CARD_NUM , A.TERM_YRMN , A.INSTP_MONTH_FREQ , A.START_YRMN || '01' AS START_YRMN , A.END_YRMN || '01' AS END_YRMN , A.PAY_AMT , A.PAY_DAY , A.APPRV_NUM , A.REPT_DAY , A.REPT_AMT , A.COMIS , A.REMAK , (CASE WHEN C.UPSO_CD IS NOT NULL THEN 'Y' ELSE 'N' END) AS REPT_YN  ";
        query +=" FROM GIBU.TBRA_REPT_CARD_REQ A , GIBU.TBRA_UPSO B , GIBU.TBRA_REPT_CARD C  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.BRAN_CD = DECODE(:BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD)  ";
        query +=" AND A.STAFF_CD = NVL(:STAFF_CD, A.STAFF_CD)  ";
        query +=" AND A.CARD_GBN = NVL(:CARD_GBN, A.CARD_GBN)  ";
        query +=" AND A.INS_DAY = SUBSTR(C.REMAK(+), 1, 8)  ";
        query +=" AND A.INS_NUM = SUBSTR(C.REMAK(+), 10, 4)  ";
        if( !INS_START.equals("")  && !INS_END.equals("") )
        {
            query +=" AND A.INS_DAY BETWEEN :INS_START  ";
            query +=" AND :INS_END  ";
        }
        if( !PAYTRM_START_DAY.equals("")  && !PAYTRM_END_DAY.equals("") )
        {
            query +=" AND A.PAY_DAY BETWEEN :PAYTRM_START_DAY  ";
            query +=" AND :PAYTRM_END_DAY  ";
        }
        if( !REPT_FROM.equals("")  && !REPT_TO.equals("") )
        {
            query +=" AND A.REPT_DAY BETWEEN :REPT_FROM  ";
            query +=" AND :REPT_TO  ";
        }
        query +=" ORDER BY INS_DAY, INS_NUM  ";
        sobj.setSql(query);
        if(!REPT_TO.equals(""))
        {
            sobj.setString("REPT_TO", REPT_TO);               //�Ա�������
        }
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        if(!INS_END.equals(""))
        {
            sobj.setString("INS_END", INS_END);               //�����������
        }
        if(!PAYTRM_START_DAY.equals(""))
        {
            sobj.setString("PAYTRM_START_DAY", PAYTRM_START_DAY);               //���αⰣ ���� ����
        }
        if(!PAYTRM_END_DAY.equals(""))
        {
            sobj.setString("PAYTRM_END_DAY", PAYTRM_END_DAY);               //���αⰣ ���� ����
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
    //##**$$sel_card_req
    //##**$$mng_card_req
    /*
    */
    public DOBJ CTLmng_card_req(DOBJ dobj)
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
            dobj  = CALLmng_card_req_MIUD1(Conn, dobj);           //  IUD�б�
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
    public DOBJ CTLmng_card_req( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_card_req_MIUD1(Conn, dobj);           //  IUD�б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // IUD�б�
    public DOBJ CALLmng_card_req_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLmng_card_req_SEL8(Conn, dobj);           //  INS_NUMä��
                dobj  = CALLmng_card_req_INS5(Conn, dobj);           //  �Է�
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_card_req_UPD6(Conn, dobj);           //  ����
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_card_req_DEL7(Conn, dobj);           //  ����
            }
        }
        return dobj;
    }
    // ����
    public DOBJ CALLmng_card_req_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_card_req_DEL7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_card_req_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //��� ����
        String   INS_NUM = dvobj.getRecord().get("INS_NUM");   //��� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_CARD_REQ  \n";
        query +=" where INS_NUM=:INS_NUM  \n";
        query +=" and INS_DAY=:INS_DAY";
        sobj.setSql(query);
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setString("INS_NUM", INS_NUM);               //��� ��ȣ
        return sobj;
    }
    // ����
    public DOBJ CALLmng_card_req_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLmng_card_req_UPD6(dobj, dvobj);
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
    private SQLObject SQLmng_card_req_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //���� �Ͻ�
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //�̳� �ݾ�
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //���� ��ȣ
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        double   PAY_AMT = dvobj.getRecord().getDouble("PAY_AMT");   //���αݾ�
        String   CARD_NUM = dvobj.getRecord().get("CARD_NUM");   //ī�� ��ȣ
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //�Ա� �ݾ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   INS_NUM = dvobj.getRecord().get("INS_NUM");   //��� ��ȣ
        String   NONPY_TERM = dvobj.getRecord().get("NONPY_TERM");
        String   INSTP_MONTH_FREQ = dvobj.getRecord().get("INSTP_MONTH_FREQ");   //�г� ���� ��
        String   CARD_GBN = dvobj.getRecord().get("CARD_GBN");   //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        String   PAY_DAY = dvobj.getRecord().get("PAY_DAY");   //���� ����
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //��� ����
        String   TERM_YRMN = dvobj.getRecord().get("TERM_YRMN");   //���� ����
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        double   COMIS = dvobj.getRecord().getDouble("COMIS");   //������
        String   END_YRMN = wutil.substr(dobj.getRetObject("R").getRecord().get("END_YRMN"),0,6);   //������
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   START_YRMN = wutil.substr(dobj.getRetObject("R").getRecord().get("START_YRMN"),0,6);   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT_CARD_REQ  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , COMIS=:COMIS , REPT_DAY=:REPT_DAY , TERM_YRMN=:TERM_YRMN , PAY_DAY=:PAY_DAY , START_YRMN=:START_YRMN , CARD_GBN=:CARD_GBN , INSTP_MONTH_FREQ=:INSTP_MONTH_FREQ , NONPY_TERM=:NONPY_TERM , UPSO_CD=:UPSO_CD , MOD_DATE=SYSDATE , REPT_AMT=:REPT_AMT , CARD_NUM=:CARD_NUM , END_YRMN=:END_YRMN , PAY_AMT=:PAY_AMT , REMAK=:REMAK , APPRV_NUM=:APPRV_NUM , NONPY_AMT=:NONPY_AMT  \n";
        query +=" where INS_DAY=:INS_DAY  \n";
        query +=" and INS_NUM=:INS_NUM";
        sobj.setSql(query);
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //�̳� �ݾ�
        sobj.setString("APPRV_NUM", APPRV_NUM);               //���� ��ȣ
        sobj.setString("REMAK", REMAK);               //���
        sobj.setDouble("PAY_AMT", PAY_AMT);               //���αݾ�
        sobj.setString("CARD_NUM", CARD_NUM);               //ī�� ��ȣ
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("INS_NUM", INS_NUM);               //��� ��ȣ
        sobj.setString("NONPY_TERM", NONPY_TERM);
        sobj.setString("INSTP_MONTH_FREQ", INSTP_MONTH_FREQ);               //�г� ���� ��
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        sobj.setString("PAY_DAY", PAY_DAY);               //���� ����
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setString("TERM_YRMN", TERM_YRMN);               //���� ����
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // INS_NUMä��
    public DOBJ CALLmng_card_req_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_card_req_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_card_req_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(TO_NUMBER(INS_NUM)),  0)  +  1,  4,  '0')  AS  INS_NUM  FROM  GIBU.TBRA_REPT_CARD_REQ  WHERE  INS_DAY  =  TO_CHAR(SYSDATE,  'YYYYMMDD') ";
        sobj.setSql(query);
        return sobj;
    }
    // �Է�
    public DOBJ CALLmng_card_req_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_card_req_INS5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_card_req_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        double   NONPY_AMT = dvobj.getRecord().getDouble("NONPY_AMT");   //�̳� �ݾ�
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //���� ��ȣ
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //���� �ڵ�
        double   PAY_AMT = dvobj.getRecord().getDouble("PAY_AMT");   //���αݾ�
        String   CARD_NUM = dvobj.getRecord().get("CARD_NUM");   //ī�� ��ȣ
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //�Ա� �ݾ�
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //���� �ڵ�
        String   NONPY_TERM = dvobj.getRecord().get("NONPY_TERM");
        String   INSTP_MONTH_FREQ = dvobj.getRecord().get("INSTP_MONTH_FREQ");   //�г� ���� ��
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //��� �ڵ�
        String   CARD_GBN = dvobj.getRecord().get("CARD_GBN");   //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        String   PAY_DAY = dvobj.getRecord().get("PAY_DAY");   //���� ����
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //��� ����
        String   TERM_YRMN = dvobj.getRecord().get("TERM_YRMN");   //���� ����
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        double   COMIS = dvobj.getRecord().getDouble("COMIS");   //������
        String   END_YRMN = wutil.substr(dobj.getRetObject("R").getRecord().get("END_YRMN"),0,6);   //������
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   INS_NUM = dobj.getRetObject("SEL8").getRecord().get("INS_NUM");   //��� ��ȣ
        String   START_YRMN = wutil.substr(dobj.getRetObject("R").getRecord().get("START_YRMN"),0,6);   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_CARD_REQ (COMIS, REPT_DAY, INSPRES_ID, TERM_YRMN, INS_DAY, PAY_DAY, START_YRMN, INS_DATE, CARD_GBN, STAFF_CD, INSTP_MONTH_FREQ, NONPY_TERM, INS_NUM, UPSO_CD, REPT_AMT, CARD_NUM, PAY_AMT, BRAN_CD, END_YRMN, REMAK, APPRV_NUM, NONPY_AMT)  \n";
        query +=" values(:COMIS , :REPT_DAY , :INSPRES_ID , :TERM_YRMN , :INS_DAY , :PAY_DAY , :START_YRMN , SYSDATE, :CARD_GBN , :STAFF_CD , :INSTP_MONTH_FREQ , :NONPY_TERM , :INS_NUM , :UPSO_CD , :REPT_AMT , :CARD_NUM , :PAY_AMT , :BRAN_CD , :END_YRMN , :REMAK , :APPRV_NUM , :NONPY_AMT )";
        sobj.setSql(query);
        sobj.setDouble("NONPY_AMT", NONPY_AMT);               //�̳� �ݾ�
        sobj.setString("APPRV_NUM", APPRV_NUM);               //���� ��ȣ
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setDouble("PAY_AMT", PAY_AMT);               //���αݾ�
        sobj.setString("CARD_NUM", CARD_NUM);               //ī�� ��ȣ
        sobj.setDouble("REPT_AMT", REPT_AMT);               //�Ա� �ݾ�
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setString("NONPY_TERM", NONPY_TERM);
        sobj.setString("INSTP_MONTH_FREQ", INSTP_MONTH_FREQ);               //�г� ���� ��
        sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        sobj.setString("CARD_GBN", CARD_GBN);               //ī�� ����(TENV_CODE TABLE�� HIGH_CD = '00112')
        sobj.setString("PAY_DAY", PAY_DAY);               //���� ����
        sobj.setString("INS_DAY", INS_DAY);               //��� ����
        sobj.setString("TERM_YRMN", TERM_YRMN);               //���� ����
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("INS_NUM", INS_NUM);               //��� ��ȣ
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    //##**$$mng_card_req
    //##**$$end
}
