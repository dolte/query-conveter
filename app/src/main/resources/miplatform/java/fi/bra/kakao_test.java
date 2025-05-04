
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class kakao_test
{
    public kakao_test()
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
                dobj  = CALLkakao_test_INS2(Conn, dobj);           //  �߼۴���Է�
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
    // �߼۴���Է�
    public DOBJ CALLkakao_test_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("INS2");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLkakao_test_INS2(dobj, dvobj);
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
    private SQLObject SQLkakao_test_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   RESERVED4 = dvobj.getRecord().get("RESERVED4");   //�׽�Ʈ�÷�4
        String   RESERVED5 = dvobj.getRecord().get("RESERVED5");   //�׽�Ʈ�÷�2
        String   REQ_DTM = dvobj.getRecord().get("REQ_DTM");   //�߼ۿ�û�Ͻ�
        String   USER_ID = dvobj.getRecord().get("USER_ID");   //����ڹ�ȣ
        String   TMPL_CD = dvobj.getRecord().get("TMPL_CD");   //�˸������ø��ڵ�
        String   CHANNEL = dvobj.getRecord().get("CHANNEL");   //�˸���ä��
        String   ATTACHMENT = dvobj.getRecord().get("ATTACHMENT");   //�˸����ư��ũ
        String   SMS_SND_NUM = dvobj.getRecord().get("SMS_SND_NUM");   //sms�߽ſ���
        String   RESERVED3 = dvobj.getRecord().get("RESERVED3");   //�׽�Ʈ�÷�1
        String   SND_MSG = dvobj.getRecord().get("SND_MSG");   //�˸��峻��
        String   RESERVED6 = dvobj.getRecord().get("RESERVED6");   //�׽�Ʈ�÷�2
        String   RESERVED1 = dvobj.getRecord().get("RESERVED1");   //�׽�Ʈ�÷�1
        String   SUBJECT = dvobj.getRecord().get("SUBJECT");   //����
        String   PHONE_NUM = dvobj.getRecord().get("PHONE_NUM");   //�������޴�����ȣ
        String   RESERVED2 = dvobj.getRecord().get("RESERVED2");   //�׽�Ʈ�÷�2
        String   SENDER_KEY = dvobj.getRecord().get("SENDER_KEY");   //�˸���Ű
        double   MSG_ID = dobj.getRetObject("SEL1").getRecord().getDouble("MSG_ID");   //SMS KEY
        String   SMS_SND_YN ="Y";   //sms�߼ۿ���
        String   SN = dobj.getRetObject("SEL1").getRecord().get("SN");   //����
        String   SND_TYPE ="P";   //�˸���߼۹��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into KAKAO.MZSENDTRAN (SENDER_KEY, RESERVED2, SN, PHONE_NUM, SUBJECT, RESERVED1, RESERVED6, SND_MSG, RESERVED3, SMS_SND_NUM, MSG_ID, ATTACHMENT, SND_TYPE, SMS_SND_YN, CHANNEL, TMPL_CD, USER_ID, REQ_DTM, RESERVED5, RESERVED4)  \n";
        query +=" values(:SENDER_KEY , :RESERVED2 , :SN , :PHONE_NUM , :SUBJECT , :RESERVED1 , :RESERVED6 , :SND_MSG , :RESERVED3 , :SMS_SND_NUM , :MSG_ID , :ATTACHMENT , :SND_TYPE , :SMS_SND_YN , :CHANNEL , :TMPL_CD , :USER_ID , :REQ_DTM , :RESERVED5 , :RESERVED4 )";
        sobj.setSql(query);
        sobj.setString("RESERVED4", RESERVED4);               //�׽�Ʈ�÷�4
        sobj.setString("RESERVED5", RESERVED5);               //�׽�Ʈ�÷�2
        sobj.setString("REQ_DTM", REQ_DTM);               //�߼ۿ�û�Ͻ�
        sobj.setString("USER_ID", USER_ID);               //����ڹ�ȣ
        sobj.setString("TMPL_CD", TMPL_CD);               //�˸������ø��ڵ�
        sobj.setString("CHANNEL", CHANNEL);               //�˸���ä��
        sobj.setString("ATTACHMENT", ATTACHMENT);               //�˸����ư��ũ
        sobj.setString("SMS_SND_NUM", SMS_SND_NUM);               //sms�߽ſ���
        sobj.setString("RESERVED3", RESERVED3);               //�׽�Ʈ�÷�1
        sobj.setString("SND_MSG", SND_MSG);               //�˸��峻��
        sobj.setString("RESERVED6", RESERVED6);               //�׽�Ʈ�÷�2
        sobj.setString("RESERVED1", RESERVED1);               //�׽�Ʈ�÷�1
        sobj.setString("SUBJECT", SUBJECT);               //����
        sobj.setString("PHONE_NUM", PHONE_NUM);               //�������޴�����ȣ
        sobj.setString("RESERVED2", RESERVED2);               //�׽�Ʈ�÷�2
        sobj.setString("SENDER_KEY", SENDER_KEY);               //�˸���Ű
        sobj.setDouble("MSG_ID", MSG_ID);               //SMS KEY
        sobj.setString("SMS_SND_YN", SMS_SND_YN);               //sms�߼ۿ���
        sobj.setString("SN", SN);               //����
        sobj.setString("SND_TYPE", SND_TYPE);               //�˸���߼۹��
        return sobj;
    }
    //##**$$kakao_test
    //##**$$end
}
