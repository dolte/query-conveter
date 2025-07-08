
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r12_2
{
    public tac10_r12_2()
    {
    }
    //##**$$mms_send_old
    /*
    */
    public DOBJ CTLmms_send_old(DOBJ dobj)
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
            dobj  = CALLmms_send_old_SEL2(Conn, dobj);           //  MMS�߼۴����ȸ_����
            dobj  = CALLmms_send_old_MPD1(Conn, dobj);           //  MMS����LOOP
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
    public DOBJ CTLmms_send_old( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmms_send_old_SEL2(Conn, dobj);           //  MMS�߼۴����ȸ_����
        dobj  = CALLmms_send_old_MPD1(Conn, dobj);           //  MMS����LOOP
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // MMS�߼۴����ȸ_����
    public DOBJ CALLmms_send_old_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmms_send_old_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmms_send_old_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_GBN = dobj.getRetObject("S").getRecord().get("MB_GBN");   //ȸ������
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //ȸ���ڵ�
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //���۳��
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //������
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //�絵 ����
        String   USER_ID ="test";   //����ڹ�ȣ
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //ȸ���ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  USER_ID,  SUBJECT,  NOW_DATE,  NOW_DATE  SEND_DATE,  CALLBACK,  DEST_COUNT,  MSG_TYPE,  DEST_INFO,  MMS_MSG,  CONTENT_COUNT,  CONTENT_DATA,  RESERVED1,  RESERVED2,  RESERVED3,  RESERVED4,  RESERVED5  ,  'SUPP'  RESERVED6  FROM   \n";
        query +=" (SELECT  A.MB_CD  USER_ID,  TO_NUMBER  (SUBSTR  (SUPP_YRMN,  5))  ||  '��  ����Ʈ  ���޳�����'  SUBJECT,  TO_CHAR  (SYSDATE,  'YYYYMMDDHH24MM')  NOW_DATE,  '0226600483'  CALLBACK,  1  DEST_COUNT,  '0'  MSG_TYPE,  TRANSLATE(CP_NUM,  '0123456789'||CP_NUM,  '0123456789')||'^'||TRANSLATE(CP_NUM,  '0123456789'||CP_NUM,  '0123456789')  DEST_INFO,  B.HANMB_NM  ||  'ȸ����  �ȳ��Ͻʴϱ�'  ||  chr(63)  ||  CHR  (13)  ||  CHR  (10)  ||  '�ѱ��������۱���ȸ�Դϴ�.'  ||  CHR  (13)  ||  CHR  (10)  ||  TO_NUMBER  (SUBSTR  (SUPP_YRMN,  1,  4))  ||  '��  '  ||  TO_NUMBER  (SUBSTR  (SUPP_YRMN,  5))  ||  '��  ���۱Ƿ�  ���޳�����  ������  ����  �˷��帳�ϴ�.'  ||  CHR  (13)  ||  CHR  (10)  ||  CHR  (13)  ||  CHR  (10)  ||  '['  ||  TO_NUMBER  (SUBSTR  (SUPP_YRMN,  5))  ||  '��  ����Ʈ  ���޳�����]'  ||  CHR  (13)  ||  CHR  (10)  ||  '  ��  ������  :  '  ||  TO_CHAR  (SYSDATE,  'YYYY')  ||  '��  '  ||  TO_NUMBER  (TO_CHAR  (SYSDATE,  'MM'))  ||  '��  '  ||  TO_NUMBER  (TO_CHAR  (SYSDATE,  'DD'))  ||  '��  ���޵Ǿ����ϴ�.'  ||  CHR  (13)  ||  CHR  (10)  ||  '  ��  ������  :  https:/'  ||  '/m.komca.or.kr:8700/mobile2/navigation.jsp'  ||  chr(63)  ||  'm=2&seq=#SEQ#'  ||  CHR  (13)  ||  CHR  (10)  ||  '�ڼ���  ������  ��������  ��������'  MMS_MSG,  0  CONTENT_COUNT,  ''  CONTENT_DATA,  :USER_ID  RESERVED1,  B.MB_GBN1  RESERVED2,  A.MB_CD  RESERVED3,  A.SUPP_YRMN  RESERVED4,  A.TRNSF_GBN  RESERVED5  FROM  FIDU.TTAC_COPYRTAL  A,  FIDU.TMEM_MB  B,  ACCT.TCAC_BANK  C  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  B.SUPPBANK_CD  =  C.BANK_CD   \n";
        query +=" AND  A.SUPP_YRMN  >=  :FROM_YRMN   \n";
        query +=" AND  A.SUPP_YRMN  <=  :TO_YRMN   \n";
        query +=" AND  REGEXP_REPLACE  (LENGTH(NVL  (B.CP_NUM,  B.CP_NUM2)),'[^0-9]','')>=  10   \n";
        query +=" AND  A.MB_CD  >=  :FRMB_CD   \n";
        query +=" AND  A.MB_CD  <=  :TOMB_CD   \n";
        query +=" AND  B.MB_GBN1  LIKE  :MB_GBN  ||  '%'   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'   \n";
        query +=" AND  NVL(B.SUPPBRE_POST_RECV_YN,'D')  !='D'  ) ";
        sobj.setSql(query);
        sobj.setString("MB_GBN", MB_GBN);               //ȸ������
        sobj.setString("FRMB_CD", FRMB_CD);               //ȸ���ڵ�
        sobj.setString("FROM_YRMN", FROM_YRMN);               //���۳��
        sobj.setString("TO_YRMN", TO_YRMN);               //������
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("USER_ID", USER_ID);               //����ڹ�ȣ
        sobj.setString("TOMB_CD", TOMB_CD);               //ȸ���ڵ�
        return sobj;
    }
    // MMS����LOOP
    public DOBJ CALLmms_send_old_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("SEL2");         //MMS�߼۴����ȸ_�������� ������Ų OBJECT�Դϴ�.(CALLmms_send_old_SEL2)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmms_send_old_SEL3(Conn, dobj);           //  ��������ȣ����
                dobj  = CALLmms_send_old_INS7(Conn, dobj);           //  �߼۴���Է�
            }
        }
        return dobj;
    }
    // ��������ȣ����
    public DOBJ CALLmms_send_old_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmms_send_old_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmms_send_old_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  KOMSMS.SDK_MMS_SEQ.NEXTVAL  MSG_ID  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // �߼۴���Է�
    public DOBJ CALLmms_send_old_INS7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmms_send_old_INS7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmms_send_old_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MMS_MSG = dobj.getRetObject("R").getRecord().get("MMS_MSG");        //��Ƽ ����
        String   MMS_MSG_2 = dobj.getRetObject("SEL3").getRecord().get("MSG_ID");   //��Ƽ ����
        String   MMS_MSG_1 = dvobj.getRecord().get("MMS_MSG");   //��Ƽ ����
        String   DEST_INFO = dvobj.getRecord().get("DEST_INFO");
        String   RESERVED4 = dvobj.getRecord().get("RESERVED4");   //�׽�Ʈ�÷�4
        String   RESERVED5 = dvobj.getRecord().get("RESERVED5");   //�׽�Ʈ�÷�2
        double   CONTENT_COUNT = dvobj.getRecord().getDouble("CONTENT_COUNT");   //������ ����
        String   USER_ID = dvobj.getRecord().get("USER_ID");   //����ڹ�ȣ
        String   SEND_DATE = dvobj.getRecord().get("SEND_DATE");   //�߼�����
        String   CONTENT_DATA = dvobj.getRecord().get("CONTENT_DATA");   //����Ʈ ������
        String   RESERVED3 = dvobj.getRecord().get("RESERVED3");   //�׽�Ʈ�÷�1
        String   RESERVED6 = dvobj.getRecord().get("RESERVED6");   //�׽�Ʈ�÷�2
        String   RESERVED1 = dvobj.getRecord().get("RESERVED1");   //�׽�Ʈ�÷�1
        String   SUBJECT = dvobj.getRecord().get("SUBJECT");   //����
        double   MSG_TYPE = dvobj.getRecord().getDouble("MSG_TYPE");   //�޼��� Ÿ��
        String   NOW_DATE = dvobj.getRecord().get("NOW_DATE");   //��������
        String   CALLBACK = dvobj.getRecord().get("CALLBACK");
        String   RESERVED2 = dvobj.getRecord().get("RESERVED2");   //�׽�Ʈ�÷�2
        String   DEST_COUNT = dvobj.getRecord().get("DEST_COUNT");
        double   MSG_ID = dobj.getRetObject("SEL3").getRecord().getDouble("MSG_ID");   //SMS KEY
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into KOMSMS.SDK_MMS_SEND (DEST_COUNT, RESERVED2, CALLBACK, NOW_DATE, MSG_TYPE, SUBJECT, RESERVED1, RESERVED6, RESERVED3, MSG_ID, CONTENT_DATA, SEND_DATE, USER_ID, CONTENT_COUNT, RESERVED5, RESERVED4, DEST_INFO, MMS_MSG)  \n";
        query +=" values(:DEST_COUNT , :RESERVED2 , :CALLBACK , :NOW_DATE , :MSG_TYPE , :SUBJECT , :RESERVED1 , :RESERVED6 , :RESERVED3 , :MSG_ID , :CONTENT_DATA , :SEND_DATE , :USER_ID , :CONTENT_COUNT , :RESERVED5 , :RESERVED4 , :DEST_INFO , REPLACE(:MMS_MSG_1,'#SEQ#',:MMS_MSG_2))";
        sobj.setSql(query);
        sobj.setString("MMS_MSG_2", MMS_MSG_2);               //��Ƽ ����
        sobj.setString("MMS_MSG_1", MMS_MSG_1);               //��Ƽ ����
        sobj.setString("DEST_INFO", DEST_INFO);
        sobj.setString("RESERVED4", RESERVED4);               //�׽�Ʈ�÷�4
        sobj.setString("RESERVED5", RESERVED5);               //�׽�Ʈ�÷�2
        sobj.setDouble("CONTENT_COUNT", CONTENT_COUNT);               //������ ����
        sobj.setString("USER_ID", USER_ID);               //����ڹ�ȣ
        sobj.setString("SEND_DATE", SEND_DATE);               //�߼�����
        sobj.setString("CONTENT_DATA", CONTENT_DATA);               //����Ʈ ������
        sobj.setString("RESERVED3", RESERVED3);               //�׽�Ʈ�÷�1
        sobj.setString("RESERVED6", RESERVED6);               //�׽�Ʈ�÷�2
        sobj.setString("RESERVED1", RESERVED1);               //�׽�Ʈ�÷�1
        sobj.setString("SUBJECT", SUBJECT);               //����
        sobj.setDouble("MSG_TYPE", MSG_TYPE);               //�޼��� Ÿ��
        sobj.setString("NOW_DATE", NOW_DATE);               //��������
        sobj.setString("CALLBACK", CALLBACK);
        sobj.setString("RESERVED2", RESERVED2);               //�׽�Ʈ�÷�2
        sobj.setString("DEST_COUNT", DEST_COUNT);
        sobj.setDouble("MSG_ID", MSG_ID);               //SMS KEY
        return sobj;
    }
    //##**$$mms_send_old
    //##**$$email_send_all
    /*
    */
    public DOBJ CTLemail_send_all(DOBJ dobj)
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
            dobj  = CALLemail_send_all_XIUD1(Conn, dobj);           //  �̸��Ϲ߼�
            dobj  = CALLemail_send_all_XIUD2(Conn, dobj);           //  �˸���߼�
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
    public DOBJ CTLemail_send_all( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLemail_send_all_XIUD1(Conn, dobj);           //  �̸��Ϲ߼�
        dobj  = CALLemail_send_all_XIUD2(Conn, dobj);           //  �˸���߼�
        return dobj;
    }
    // �̸��Ϲ߼�
    public DOBJ CALLemail_send_all_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLemail_send_all_XIUD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLemail_send_all_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO AMAIL.EMS_MAILQUEUE (SEQ, MAIL_CODE, TO_EMAIL, TO_NAME, FROM_EMAIL, FROM_NAME, SUBJECT, TARGET_FLAG, MAP1, MAP2, MAP3, MAP4, MAP5, MAP_CONTENT, REG_DATE) SELECT SEQ, MAIL_CODE, TO_EMAIL, TO_NAME, FROM_EMAIL, FROM_NAME, SUBJECT, TARGET_FLAG, MAP1, MAP2, MAP3, MAP4, MAP5, 'TEMP' MAP_CONTENT, SYSDATE FROM AMAIL.EMS_MAILQUEUE_TMP WHERE MAP3=:FROM_YRMN";
        sobj.setSql(query);
        sobj.setString("FROM_YRMN", FROM_YRMN);               //���۳��
        return sobj;
    }
    // �˸���߼�
    public DOBJ CALLemail_send_all_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLemail_send_all_XIUD2(dobj, dvobj);
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
    private SQLObject SQLemail_send_all_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO KAKAO.MZSENDTRAN (SN, MSG_ID, SENDER_KEY, CHANNEL, SND_TYPE, TMPL_CD, USER_ID, SUBJECT, SMS_SND_NUM, PHONE_NUM, SND_MSG, ATTACHMENT, REQ_DTM, SMS_SND_YN, RESERVED1, RESERVED2, RESERVED3, RESERVED4, RESERVED5, RESERVED6) SELECT SN, MSG_ID, SENDER_KEY, CHANNEL, SND_TYPE, TMPL_CD, USER_ID, SUBJECT, SMS_SND_NUM, PHONE_NUM, REPLACE(REPLACE ( SND_MSG, '#SUPP_DAY#', TO_CHAR (SYSDATE, 'YYYY') || '�� ' || TO_CHAR (SYSDATE, 'MM') || '�� ' || TO_CHAR (SYSDATE, 'DD') || '��'),'#HTTPS#','https:/'), ATTACHMENT, TO_CHAR (SYSDATE, 'yyyymmddhh24miss') REQ_DTM, SMS_SND_YN, RESERVED1, RESERVED2, RESERVED3, RESERVED4, RESERVED5, RESERVED6 FROM KAKAO.MZSENDTRAN_TMP WHERE RESERVED4=:FROM_YRMN";
        sobj.setSql(query);
        sobj.setString("FROM_YRMN", FROM_YRMN);               //���۳��
        return sobj;
    }
    //##**$$email_send_all
    //##**$$email_send
    /*
    */
    public DOBJ CTLemail_send(DOBJ dobj)
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
            dobj  = CALLemail_send_XIUD1(Conn, dobj);           //  �̸��� ���� ����
            dobj  = CALLemail_send_SEL2(Conn, dobj);           //  MMS�߼۴����ȸ_����
            dobj  = CALLemail_send_MPD1(Conn, dobj);           //  MMS����LOOP
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
    public DOBJ CTLemail_send( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLemail_send_XIUD1(Conn, dobj);           //  �̸��� ���� ����
        dobj  = CALLemail_send_SEL2(Conn, dobj);           //  MMS�߼۴����ȸ_����
        dobj  = CALLemail_send_MPD1(Conn, dobj);           //  MMS����LOOP
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �̸��� ���� ����
    public DOBJ CALLemail_send_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("XIUD1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLemail_send_XIUD1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        rvobj.Println("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLemail_send_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //ȸ���ڵ�
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //���۳��
        String   MB_GBN = dobj.getRetObject("S").getRecord().get("MB_GBN");   //ȸ������
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //ȸ���ڵ�
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //������
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //�絵 ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO amail.ems_mailqueue (seq, mail_code, to_email, to_name, from_email, from_name, subject, target_flag, map1, map2, map3, map4, map5, map_content, reg_date ) SELECT amail.ems_mailqueue_seq.nextval,'25',B.EMAIL_ADDR, B.HANMB_NM , 'acc1@komca.or.kr' , '�ѱ��������۱���ȸ' , SUBSTR(A.SUPP_YRMN,1,4)||'��'||SUBSTR(A.SUPP_YRMN,5,2) ||'��'||' ���۱ǻ��� ���޳�����' , 'N' , DECODE(NVL(BRYR_MONDAY,' '),' ','I', 'B') , A.TRNSF_GBN , A.SUPP_YRMN , A.MB_CD , SUBSTR(A.SUPP_YRMN,1,4)||'�� '||SUBSTR(A.SUPP_YRMN,5,2)||'��', 'TEMP' , SYSDATE FROM FIDU.TTAC_COPYRTAL A, FIDU.TMEM_MB B WHERE A.MB_CD = B.MB_CD AND A.SUPP_YRMN >=:FROM_YRMN AND A.SUPP_YRMN <= :TO_YRMN AND B.EMAIL_ADDR IS NOT NULL AND A.MB_CD >= :FRMB_CD AND A.MB_CD <= :TOMB_CD AND B.MB_GBN1 LIKE :MB_GBN AND A.TRNSF_GBN LIKE :TRNSF_GBN AND NVL(B.SUPPBRE_POST_RECV_YN,'D') !='D' AND A.TOT_REALSUPP_AMT > 0";
        sobj.setSql(query);
        sobj.setString("FRMB_CD", FRMB_CD);               //ȸ���ڵ�
        sobj.setString("FROM_YRMN", FROM_YRMN);               //���۳��
        sobj.setString("MB_GBN", MB_GBN);               //ȸ������
        sobj.setString("TOMB_CD", TOMB_CD);               //ȸ���ڵ�
        sobj.setString("TO_YRMN", TO_YRMN);               //������
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        return sobj;
    }
    // MMS�߼۴����ȸ_����
    public DOBJ CALLemail_send_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLemail_send_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLemail_send_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_GBN = dobj.getRetObject("S").getRecord().get("MB_GBN");   //ȸ������
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //ȸ���ڵ�
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //���۳��
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //������
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //�絵 ����
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //ȸ���ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  KAKAO.ALIMTALK_SEQ.NEXTVAL  SN,  KAKAO.ALIMTALK_SEQ.NEXTVAL  MSG_ID,  SENDER_KEY,  CHANNEL,  SND_TYPE,  TMPL_CD,  USER_ID,  SUBJECT,  SMS_SND_NUM,  PHONE_NUM,  SND_MSG,  ATTACHMENT,  REQ_DTM,  SMS_SND_YN,  RESERVED1,  RESERVED2,  RESERVED3,  RESERVED4,  RESERVED5,  'SUPP'  RESERVED6,   \n";
        query +=" (SELECT  MAX(SEQ)  FROM  AMAIL.EMS_MAILQUEUE  X  WHERE  X.MAP3=RESERVED4   \n";
        query +=" AND  X.MAP4=RESERVED3   \n";
        query +=" AND  X.MAP2=RESERVED5  )  MAIL_SEQ  FROM   \n";
        query +=" (SELECT  '9bb6b0be43e3d0ae1021d18efd14b8196be7cb8d'  SENDER_KEY,  'A'  CHANNEL,  'P'  SND_TYPE,  'JIGEUB_04'  TMPL_CD,  A.MB_CD  USER_ID,  TO_NUMBER  (SUBSTR  (SUPP_YRMN,  5))  ||  '��  ����Ʈ  ���޳�����'  SUBJECT,  '0226600483'  SMS_SND_NUM,  TRANSLATE  (CP_NUM,  '0123456789'  ||  CP_NUM,  '0123456789')  PHONE_NUM,  B.HANMB_NM  ||  'ȸ����  �ȳ��Ͻʴϱ�'  ||  CHR  (63)  ||  CHR  (13)  ||  CHR  (10)  ||  '�ѱ��������۱���ȸ�Դϴ�.'  ||  CHR  (13)  ||  CHR  (10)  ||  TO_NUMBER  (SUBSTR  (SUPP_YRMN,  1,  4))  ||  '��  '  ||  TO_NUMBER  (SUBSTR  (SUPP_YRMN,  5))  ||  '��  ���۱Ƿ�  ���޳�����  ������  ����  �˷��帳�ϴ�.'  ||  CHR  (13)  ||  CHR  (10)  ||  CHR  (13)  ||  CHR  (10)  ||  '['  ||  TO_NUMBER  (SUBSTR  (SUPP_YRMN,  5))  ||  '��  ����Ʈ  ���޳�����]'  ||  CHR  (13)  ||  CHR  (10)  ||  '��  ������  :  #SUPP_DAY#'  ||'  ���޵Ǿ����ϴ�.'  ||  CHR  (13)  ||  CHR  (10)  ||  '��  ������  '  ||  CHR  (13)  ||  CHR  (10)  ||  CHR  (13)  ||  CHR  (10)  ||  '���÷�  �����ϱ�'  ||  CHR  (13)  ||  CHR  (10)  ||  '#URL#'  ||  CHR  (13)  ||  CHR  (10)  ||  CHR  (13)  ||  CHR  (10)  ||  '�����  ������  �����ϱ�'  ||  CHR  (13)  ||  CHR  (10)  ||  '#MURL#'  ||  CHR  (13)  ||  CHR  (10)  ||  CHR  (13)  ||  CHR  (10)  ||  '���ø����̼�  �α�����  �����Ͻ�  ȸ���Ե鲲����  �����  ��ũ��  �̿��Ͻø�  �˴ϴ�.'  ||  CHR  (13)  ||  CHR  (10)  ||  '�ڼ���  ������  ��������  ��������.'  AS  SND_MSG,  ''  AS  ATTACHMENT,  TO_CHAR  (SYSDATE,  'yyyymmddhh24miss')  REQ_DTM,  'Y'  SMS_SND_YN,  SUBSTRB  (B.HANMB_NM,  1,  50)  RESERVED1,  B.MB_GBN1  RESERVED2,  A.MB_CD  RESERVED3,  A.SUPP_YRMN  RESERVED4,  A.TRNSF_GBN  RESERVED5  FROM  FIDU.TTAC_COPYRTAL  A,  FIDU.TMEM_MB  B,  ACCT.TCAC_BANK  C  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  B.SUPPBANK_CD  =  C.BANK_CD   \n";
        query +=" AND  A.SUPP_YRMN  >=  :FROM_YRMN   \n";
        query +=" AND  A.SUPP_YRMN  <=  :TO_YRMN   \n";
        query +=" AND  B.CP_NUM  IS  NOT  NULL   \n";
        query +=" AND  REGEXP_REPLACE  (LENGTH  (B.CP_NUM),  '[^0-9]',  '')  >=  10   \n";
        query +=" AND  A.MB_CD  >=  :FRMB_CD   \n";
        query +=" AND  A.MB_CD  <=  :TOMB_CD   \n";
        query +=" AND  B.MB_GBN1  LIKE  :MB_GBN  ||  '%'   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'   \n";
        query +=" AND  NVL  (B.SUPPBRE_POST_RECV_YN,  'D')  !=  'D'   \n";
        query +=" AND  A.TOT_ORGDISTR_AMT  >  0) ";
        sobj.setSql(query);
        sobj.setString("MB_GBN", MB_GBN);               //ȸ������
        sobj.setString("FRMB_CD", FRMB_CD);               //ȸ���ڵ�
        sobj.setString("FROM_YRMN", FROM_YRMN);               //���۳��
        sobj.setString("TO_YRMN", TO_YRMN);               //������
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("TOMB_CD", TOMB_CD);               //ȸ���ڵ�
        return sobj;
    }
    // MMS����LOOP
    public DOBJ CALLemail_send_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("SEL2");         //MMS�߼۴����ȸ_�������� ������Ų OBJECT�Դϴ�.(CALLemail_send_SEL2)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLemail_send_SEL1(Conn, dobj);           //  SEL1
                dobj  = CALLemail_send_OBJ1(Conn, dobj);           //  �ܺ�ȣ��
                dobj  = CALLemail_send_INS8(Conn, dobj);           //  �߼۴���Է�
            }
        }
        return dobj;
    }
    // SEL1
    // DYNAMIC LINK �ʿ��� URL��ȸ
    public DOBJ CALLemail_send_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLemail_send_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLemail_send_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("R").getRecord().get("RESERVED3");   //ȸ�� �ڵ�
        String   TRNSF_GBN = dobj.getRetObject("R").getRecord().get("RESERVED5");   //�絵 ����
        String   LNK_KEY ="PAYMENT";   //���� Ű
        String   SUPP_YRMN = dobj.getRetObject("R").getRecord().get("RESERVED4");   //���� ���
        String   SEQ_NO = dobj.getRetObject("R").getRecord().get("MSG_ID");   //�Ϸ� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  'https:/'  ||  '/komca.page.link'  ||  CHR  (63)  ||  'LNK_KEY='||:LNK_KEY||  CHR(38)  ||'S_MB_CD='||:MB_CD||CHR(38)  ||'S_TRNSF_GBN='||:TRNSF_GBN  ||  CHR(38)  ||'S_SUPP_YRMN='||:SUPP_YRMN  AS  LINK_URL,  'https:/'  ||  '/m.komca.or.kr:8700/mobile2/navigation.jsp'  ||  CHR  (63)  ||  'm=2'||  CHR(38)  ||'seq='||:SEQ_NO  AS  FALLBACK_URL,  CASE  WHEN  :MAIL_SEQ  IS  NULL  THEN  'https:/'  ||  '/m.komca.or.kr:8700/mobile2/navigation.jsp'  ||  CHR  (63)  ||  'm=2'||  CHR(38)  ||'seq='||:SEQ_NO  ELSE  'https:/'  ||  '/www.komca.or.kr/e_mail/25_10_check_pwd.jsp'  ||  CHR  (63)  ||  'SEQ='||:MAIL_SEQ||''||  CHR(38)  ||'EMS_M_PWGBN=B'  END  AS  DESKTOP_URL  ,  'https:/'  ||  '/m.komca.or.kr:8700/mobile2/navigation.jsp'  ||  CHR  (63)  ||  'm=2'||  CHR(38)  ||'seq='||:SEQ_NO  AS  MURL  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //ȸ�� �ڵ�
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //�絵 ����
        sobj.setString("LNK_KEY", LNK_KEY);               //���� Ű
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        sobj.setString("SEQ_NO", SEQ_NO);               //�Ϸ� ��ȣ
        return sobj;
    }
    // �ܺ�ȣ��
    public DOBJ CALLemail_send_OBJ1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OBJ1");
        CommUtil cu = new CommUtil(dobj);
        HashMap   classinfo = new HashMap();
        classinfo.put("OBJECTID","OBJ1");
        classinfo.put("PACKAGE","komca.util.fcm");
        classinfo.put("CLASS","DynamicLink");
        classinfo.put("METHOD","getDirectLink");
        classinfo.put("INMAP","SEL1:K");
        classinfo.put("OUTOBJ","SEL99");
        classinfo.put("OUTOBJ1","");
        dobj = cu.callPSInternal(dobj, Conn, classinfo );
        return dobj;
    }
    // �߼۴���Է�
    public DOBJ CALLemail_send_INS8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLemail_send_INS8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLemail_send_INS8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ATTACHMENT = dobj.getRetObject("R").getRecord().get("ATTACHMENT");        //�˸����ư
        String SND_MSG = dobj.getRetObject("R").getRecord().get("SND_MSG");        //��Ƽ ����
        String   RESERVED4 = dvobj.getRecord().get("RESERVED4");   //�׽�Ʈ�÷�4
        String   RESERVED5 = dvobj.getRecord().get("RESERVED5");   //�׽�Ʈ�÷�2
        String   REQ_DTM = dvobj.getRecord().get("REQ_DTM");   //�߼ۿ�û�Ͻ�
        String   USER_ID = dvobj.getRecord().get("USER_ID");   //����ڹ�ȣ
        String   TMPL_CD = dvobj.getRecord().get("TMPL_CD");   //�˸������ø��ڵ�
        String   CHANNEL = dvobj.getRecord().get("CHANNEL");   //�˸���ä��
        String   SMS_SND_YN = dvobj.getRecord().get("SMS_SND_YN");   //sms�߼ۿ���
        String   SND_TYPE = dvobj.getRecord().get("SND_TYPE");   //�˸���߼۹��
        String   ATTACHMENT_2 = dobj.getRetObject("OBJ1").getRecord().get("DYNAMIC_LINK");   //�˸����ư��ũ
        String   ATTACHMENT_1 = dvobj.getRecord().get("ATTACHMENT");   //�˸����ư��ũ
        String   SMS_SND_NUM = dvobj.getRecord().get("SMS_SND_NUM");   //sms�߽ſ���
        String   RESERVED3 = dvobj.getRecord().get("RESERVED3");   //�׽�Ʈ�÷�1
        String   SND_MSG_2 = dobj.getRetObject("OBJ1").getRecord().get("DYNAMIC_LINK");   //�˸��峻��
        String   SND_MSG_1 = dvobj.getRecord().get("SND_MSG");   //�˸��峻��
        String   RESERVED6 = dvobj.getRecord().get("RESERVED6");   //�׽�Ʈ�÷�2
        String   RESERVED1 = dvobj.getRecord().get("RESERVED1");   //�׽�Ʈ�÷�1
        String   SUBJECT = dvobj.getRecord().get("SUBJECT");   //����
        String   PHONE_NUM = dvobj.getRecord().get("PHONE_NUM");   //�������޴�����ȣ
        String   SN = dvobj.getRecord().get("SN");   //����
        String   RESERVED2 = dvobj.getRecord().get("RESERVED2");   //�׽�Ʈ�÷�2
        String   SENDER_KEY = dvobj.getRecord().get("SENDER_KEY");   //�˸���Ű
        double   MSG_ID = dobj.getRetObject("SEL2").getRecord().getDouble("MSG_ID");   //SMS KEY
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into KAKAO.MZSENDTRAN (SENDER_KEY, RESERVED2, SN, PHONE_NUM, SUBJECT, RESERVED1, RESERVED6, SND_MSG, RESERVED3, SMS_SND_NUM, MSG_ID, ATTACHMENT, SND_TYPE, SMS_SND_YN, CHANNEL, TMPL_CD, USER_ID, REQ_DTM, RESERVED5, RESERVED4)  \n";
        query +=" values(:SENDER_KEY , :RESERVED2 , :SN , :PHONE_NUM , :SUBJECT , :RESERVED1 , :RESERVED6 , REPLACE(REPLACE(:SND_MSG_1,'#URL#',:SND_MSG_2),'#MURL#',SEL1.MURL), :RESERVED3 , :SMS_SND_NUM , :MSG_ID , REPLACE(:ATTACHMENT_1,'#URL#',:ATTACHMENT_2), :SND_TYPE , :SMS_SND_YN , :CHANNEL , :TMPL_CD , :USER_ID , :REQ_DTM , :RESERVED5 , :RESERVED4 )";
        sobj.setSql(query);
        sobj.setString("RESERVED4", RESERVED4);               //�׽�Ʈ�÷�4
        sobj.setString("RESERVED5", RESERVED5);               //�׽�Ʈ�÷�2
        sobj.setString("REQ_DTM", REQ_DTM);               //�߼ۿ�û�Ͻ�
        sobj.setString("USER_ID", USER_ID);               //����ڹ�ȣ
        sobj.setString("TMPL_CD", TMPL_CD);               //�˸������ø��ڵ�
        sobj.setString("CHANNEL", CHANNEL);               //�˸���ä��
        sobj.setString("SMS_SND_YN", SMS_SND_YN);               //sms�߼ۿ���
        sobj.setString("SND_TYPE", SND_TYPE);               //�˸���߼۹��
        sobj.setString("ATTACHMENT_2", ATTACHMENT_2);               //�˸����ư��ũ
        sobj.setString("ATTACHMENT_1", ATTACHMENT_1);               //�˸����ư��ũ
        sobj.setString("SMS_SND_NUM", SMS_SND_NUM);               //sms�߽ſ���
        sobj.setString("RESERVED3", RESERVED3);               //�׽�Ʈ�÷�1
        sobj.setString("SND_MSG_2", SND_MSG_2);               //�˸��峻��
        sobj.setString("SND_MSG_1", SND_MSG_1);               //�˸��峻��
        sobj.setString("RESERVED6", RESERVED6);               //�׽�Ʈ�÷�2
        sobj.setString("RESERVED1", RESERVED1);               //�׽�Ʈ�÷�1
        sobj.setString("SUBJECT", SUBJECT);               //����
        sobj.setString("PHONE_NUM", PHONE_NUM);               //�������޴�����ȣ
        sobj.setString("SN", SN);               //����
        sobj.setString("RESERVED2", RESERVED2);               //�׽�Ʈ�÷�2
        sobj.setString("SENDER_KEY", SENDER_KEY);               //�˸���Ű
        sobj.setDouble("MSG_ID", MSG_ID);               //SMS KEY
        return sobj;
    }
    //##**$$email_send
    //##**$$email_send_sel
    /*
    */
    public DOBJ CTLemail_send_sel(DOBJ dobj)
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
            dobj  = CALLemail_send_sel_XIUD1(Conn, dobj);           //  �̸��� �߼�
            dobj  = CALLemail_send_sel_XIUD2(Conn, dobj);           //  �˸���/���� �߼�
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
    public DOBJ CTLemail_send_sel( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLemail_send_sel_XIUD1(Conn, dobj);           //  �̸��� �߼�
        dobj  = CALLemail_send_sel_XIUD2(Conn, dobj);           //  �˸���/���� �߼�
        return dobj;
    }
    // �̸��� �߼�
    public DOBJ CALLemail_send_sel_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLemail_send_sel_XIUD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLemail_send_sel_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //ȸ���ڵ�
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //���۳��
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //ȸ���ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO AMAIL.EMS_MAILQUEUE (SEQ, MAIL_CODE, TO_EMAIL, TO_NAME, FROM_EMAIL, FROM_NAME, SUBJECT, TARGET_FLAG, MAP1, MAP2, MAP3, MAP4, MAP5, MAP_CONTENT, REG_DATE) SELECT SEQ, MAIL_CODE, TO_EMAIL, TO_NAME, FROM_EMAIL, FROM_NAME, SUBJECT, TARGET_FLAG, MAP1, MAP2, MAP3, MAP4, MAP5, 'TEMP' MAP_CONTENT, SYSDATE FROM AMAIL.EMS_MAILQUEUE_TMP WHERE MAP3=:FROM_YRMN AND MAP4 >= :FRMB_CD AND MAP4 <= :TOMB_CD";
        sobj.setSql(query);
        sobj.setString("FRMB_CD", FRMB_CD);               //ȸ���ڵ�
        sobj.setString("FROM_YRMN", FROM_YRMN);               //���۳��
        sobj.setString("TOMB_CD", TOMB_CD);               //ȸ���ڵ�
        return sobj;
    }
    // �˸���/���� �߼�
    public DOBJ CALLemail_send_sel_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLemail_send_sel_XIUD2(dobj, dvobj);
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
    private SQLObject SQLemail_send_sel_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //ȸ���ڵ�
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //���۳��
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //ȸ���ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO KAKAO.MZSENDTRAN (SN, MSG_ID, SENDER_KEY, CHANNEL, SND_TYPE, TMPL_CD, USER_ID, SUBJECT, SMS_SND_NUM, PHONE_NUM, SND_MSG, ATTACHMENT, REQ_DTM, SMS_SND_YN, RESERVED1, RESERVED2, RESERVED3, RESERVED4, RESERVED5, RESERVED6) SELECT SN, MSG_ID, SENDER_KEY, CHANNEL, SND_TYPE, TMPL_CD, USER_ID, SUBJECT, SMS_SND_NUM, PHONE_NUM, REPLACE(REPLACE ( SND_MSG, '#SUPP_DAY#', TO_CHAR (SYSDATE, 'YYYY') || '�� ' || TO_CHAR (SYSDATE, 'MM') || '�� ' || TO_CHAR (SYSDATE, 'DD') || '��'),'#HTTPS#','https:/'), ATTACHMENT, TO_CHAR (SYSDATE, 'yyyymmddhh24miss') REQ_DTM, SMS_SND_YN, RESERVED1, RESERVED2, RESERVED3, RESERVED4, RESERVED5, RESERVED6 FROM KAKAO.MZSENDTRAN_TMP WHERE RESERVED4=:FROM_YRMN AND RESERVED3 >= :FRMB_CD AND RESERVED3 <= :TOMB_CD";
        sobj.setSql(query);
        sobj.setString("FRMB_CD", FRMB_CD);               //ȸ���ڵ�
        sobj.setString("FROM_YRMN", FROM_YRMN);               //���۳��
        sobj.setString("TOMB_CD", TOMB_CD);               //ȸ���ڵ�
        return sobj;
    }
    //##**$$email_send_sel
    //##**$$end
}
