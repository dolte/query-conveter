
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class kakao_test3
{
    public kakao_test3()
    {
    }
    //##**$$kakao_test
    /*
    */
    public DOBJ CTLkakao_test(DOBJ dobj)
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
            dobj  = CALLkakao_test_MIUD3(Conn, dobj);           //  �˸���߼�
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
    public DOBJ CTLkakao_test( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLkakao_test_MIUD3(Conn, dobj);           //  �˸���߼�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �˸���߼�
    public DOBJ CALLkakao_test_MIUD3(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLkakao_test_SEL1(Conn, dobj);           //  �˸������������
                dobj  = CALLkakao_test_XIUD2(Conn, dobj);           //  �߼����̺� ����
            }
        }
        return dobj;
    }
    // �˸������������
    public DOBJ CALLkakao_test_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLkakao_test_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLkakao_test_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  KAKAO.ALIMTALK_SEQ.NEXTVAL  SN,  KAKAO.ALIMTALK_SEQ.NEXTVAL  MSG_ID  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // �߼����̺� ����
    public DOBJ CALLkakao_test_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLkakao_test_XIUD2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLkakao_test_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTACHMENT = dobj.getRetObject("R").getRecord().get("ATTACHMENT");   //�˸����ư��ũ
        String   CHANNEL = dobj.getRetObject("R").getRecord().get("CHANNEL");   //�˸���ä��
        double   MSG_ID = dobj.getRetObject("SEL1").getRecord().getDouble("MSG_ID");   //SMS KEY
        String   PHONE_NUM = dobj.getRetObject("R").getRecord().get("PHONE_NUM");   //�������޴�����ȣ
        String   REQ_DEPT_CD = dobj.getRetObject("GOV").getRecord().get("DEPT_CD");   //�߼ۿ�û�μ��ڵ�
        String   REQ_DTM = dobj.getRetObject("R").getRecord().get("REQ_DTM");   //�߼ۿ�û�Ͻ�
        String   REQ_USR_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //�߼ۿ�û�ڻ��
        String   RESERVED1 = dobj.getRetObject("R").getRecord().get("RESERVED1");   //�׽�Ʈ�÷�1
        String   RESERVED2 = dobj.getRetObject("R").getRecord().get("RESERVED2");   //�׽�Ʈ�÷�2
        String   RESERVED3 = dobj.getRetObject("R").getRecord().get("RESERVED3");   //�׽�Ʈ�÷�1
        String   RESERVED4 = dobj.getRetObject("R").getRecord().get("RESERVED4");   //�׽�Ʈ�÷�4
        String   RESERVED5 = dobj.getRetObject("R").getRecord().get("RESERVED5");   //�׽�Ʈ�÷�2
        String   RESERVED6 = dobj.getRetObject("R").getRecord().get("RESERVED6");   //�׽�Ʈ�÷�2
        String   SLOT1 = dobj.getRetObject("R").getRecord().get("SLOT1");   //�߼ۿ�û�ΰ�����1
        String   SLOT2 = dobj.getRetObject("R").getRecord().get("SLOT2");   //�߼ۿ�û�ΰ�����2
        String   SMS_SND_MSG = dobj.getRetObject("R").getRecord().get("SMS_SND_MSG");   //sms�߼۸޽���
        String   SMS_SND_NUM = dobj.getRetObject("R").getRecord().get("SMS_SND_NUM");   //sms�߽ſ���
        String   SMS_SND_YN = dobj.getRetObject("R").getRecord().get("SMS_SND_YN");   //sms�߼ۿ���
        String   SN = dobj.getRetObject("SEL1").getRecord().get("SN");   //����
        String   SND_MSG = dobj.getRetObject("R").getRecord().get("SND_MSG");   //�˸��峻��
        String   SUBJECT = dobj.getRetObject("R").getRecord().get("SUBJECT");   //����
        String   TMPL_CD = dobj.getRetObject("R").getRecord().get("TMPL_CD");   //�˸������ø��ڵ�
        String   USER_ID = dobj.getRetObject("R").getRecord().get("USER_ID");   //����ڹ�ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.MZSENDTRAN_TJ (SN , SENDER_KEY , CHANNEL , SND_TYPE , PHONE_NUM , TMPL_CD , SUBJECT , SND_MSG , SMS_SND_MSG , SMS_SND_NUM , REQ_DEPT_CD , REQ_USR_ID , REQ_DTM , SMS_SND_YN , TRAN_STS , SLOT1 , SLOT2 , TR_TYPE_CD , ATTACHMENT , RESERVED1 , RESERVED2 , RESERVED3 , RESERVED4 , RESERVED5 , RESERVED6 , MSG_ID , USER_ID) SELECT :SN AS SN , '9bb6b0be43e3d0ae1021d18efd14b8196be7cb8d' AS SENDER_KEY , (CASE WHEN :CHANNEL IS NULL THEN 'S' ELSE :CHANNEL END) AS CHANNEL , 'P' AS SND_TYPE , REGEXP_REPLACE(:PHONE_NUM, '[^0-9]', '') AS PHONE_NUM , (CASE WHEN :TMPL_CD IS NULL THEN 'S' ELSE :TMPL_CD END) AS TMPL_CD , :SUBJECT AS SUBJECT , :SND_MSG AS SND_MSG , :SMS_SND_MSG AS SMS_SND_MSG , REGEXP_REPLACE(:SMS_SND_NUM, '[^0-9]', '') AS SMS_SND_NUM , :REQ_DEPT_CD AS REQ_DEPT_CD , :REQ_USR_ID AS REQ_USR_ID , (CASE WHEN :REQ_DTM IS NULL THEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') ELSE :REQ_DTM END) AS REQ_DTM , (CASE WHEN :SMS_SND_YN IS NULL THEN 'Y' ELSE :SMS_SND_YN END) AS SMS_SND_YN , '1' AS TRAN_STS , :SLOT1 AS SLOT1 , :SLOT2 AS SLOT2 , 'B' AS TR_TYPE_CD , :ATTACHMENT AS ATTACHMENT , :RESERVED1 AS RESERVED1 , :RESERVED2 AS RESERVED2 , :RESERVED3 AS RESERVED3 , :RESERVED4 AS RESERVED4 , :RESERVED5 AS RESERVED5 , :RESERVED6 AS RESERVED6 , :MSG_ID AS MSG_ID , :USER_ID AS USER_ID FROM DUAL";
        sobj.setSql(query);
        sobj.setString("ATTACHMENT", ATTACHMENT);               //�˸����ư��ũ
        sobj.setString("CHANNEL", CHANNEL);               //�˸���ä��
        sobj.setDouble("MSG_ID", MSG_ID);               //SMS KEY
        sobj.setString("PHONE_NUM", PHONE_NUM);               //�������޴�����ȣ
        sobj.setString("REQ_DEPT_CD", REQ_DEPT_CD);               //�߼ۿ�û�μ��ڵ�
        sobj.setString("REQ_DTM", REQ_DTM);               //�߼ۿ�û�Ͻ�
        sobj.setString("REQ_USR_ID", REQ_USR_ID);               //�߼ۿ�û�ڻ��
        sobj.setString("RESERVED1", RESERVED1);               //�׽�Ʈ�÷�1
        sobj.setString("RESERVED2", RESERVED2);               //�׽�Ʈ�÷�2
        sobj.setString("RESERVED3", RESERVED3);               //�׽�Ʈ�÷�1
        sobj.setString("RESERVED4", RESERVED4);               //�׽�Ʈ�÷�4
        sobj.setString("RESERVED5", RESERVED5);               //�׽�Ʈ�÷�2
        sobj.setString("RESERVED6", RESERVED6);               //�׽�Ʈ�÷�2
        sobj.setString("SLOT1", SLOT1);               //�߼ۿ�û�ΰ�����1
        sobj.setString("SLOT2", SLOT2);               //�߼ۿ�û�ΰ�����2
        sobj.setString("SMS_SND_MSG", SMS_SND_MSG);               //sms�߼۸޽���
        sobj.setString("SMS_SND_NUM", SMS_SND_NUM);               //sms�߽ſ���
        sobj.setString("SMS_SND_YN", SMS_SND_YN);               //sms�߼ۿ���
        sobj.setString("SN", SN);               //����
        sobj.setString("SND_MSG", SND_MSG);               //�˸��峻��
        sobj.setString("SUBJECT", SUBJECT);               //����
        sobj.setString("TMPL_CD", TMPL_CD);               //�˸������ø��ڵ�
        sobj.setString("USER_ID", USER_ID);               //����ڹ�ȣ
        return sobj;
    }
    //##**$$kakao_test
    //##**$$end
}
