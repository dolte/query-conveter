
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r12_00
{
    public tac10_r12_00()
    {
    }
    //##**$$send_email_tmp
    /*
    */
    public DOBJ CTLsend_email_tmp(DOBJ dobj)
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
            dobj  = CALLsend_email_tmp_XIUD8(Conn, dobj);           //  �̸��ϵ����� �ӽõ����� ����
            dobj  = CALLsend_email_tmp_XIUD15(Conn, dobj);           //  �̸��ϵ����� �ӽ�����
            dobj  = CALLsend_email_tmp_XIUD9(Conn, dobj);           //  ���ڹ߼۵����� �ӽõ����� ����
            dobj  = CALLsend_email_tmp_XIUD14(Conn, dobj);           //  ���ڹ߼۵����� �ӽ�����
            dobj  = CALLsend_email_tmp_SEL2(Conn, dobj);           //  �߼۴�� URL����
            dobj  = CALLsend_email_tmp_MPD1(Conn, dobj);           //  MMS����LOOP
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
    public DOBJ CTLsend_email_tmp( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsend_email_tmp_XIUD8(Conn, dobj);           //  �̸��ϵ����� �ӽõ����� ����
        dobj  = CALLsend_email_tmp_XIUD15(Conn, dobj);           //  �̸��ϵ����� �ӽ�����
        dobj  = CALLsend_email_tmp_XIUD9(Conn, dobj);           //  ���ڹ߼۵����� �ӽõ����� ����
        dobj  = CALLsend_email_tmp_XIUD14(Conn, dobj);           //  ���ڹ߼۵����� �ӽ�����
        dobj  = CALLsend_email_tmp_SEL2(Conn, dobj);           //  �߼۴�� URL����
        dobj  = CALLsend_email_tmp_MPD1(Conn, dobj);           //  MMS����LOOP
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �̸��ϵ����� �ӽõ����� ����
    public DOBJ CALLsend_email_tmp_XIUD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD8");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsend_email_tmp_XIUD8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsend_email_tmp_XIUD8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM AMAIL.EMS_MAILQUEUE_TMP";
        sobj.setSql(query);
        return sobj;
    }
    // �̸��ϵ����� �ӽ�����
    public DOBJ CALLsend_email_tmp_XIUD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD15");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsend_email_tmp_XIUD15(dobj, dvobj);
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
    private SQLObject SQLsend_email_tmp_XIUD15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO AMAIL.EMS_MAILQUEUE_TMP (SEQ, MAIL_CODE, TO_EMAIL, TO_NAME, FROM_EMAIL, FROM_NAME, SUBJECT, TARGET_FLAG, MAP1, MAP2, MAP3, MAP4, MAP5, MAP_CONTENT, REG_DATE ) SELECT AMAIL.EMS_MAILQUEUE_SEQ.NEXTVAL,'25',B.EMAIL_ADDR, B.HANMB_NM , 'acc1@komca.or.kr' , '�ѱ��������۱���ȸ' , SUBSTR(A.SUPP_YRMN,1,4)||'��'||SUBSTR(A.SUPP_YRMN,5,2) ||'��'||' ���۱ǻ��� ���޳�����' , 'N' , DECODE(NVL(BRYR_MONDAY,' '),' ','I', 'B') , A.TRNSF_GBN , A.SUPP_YRMN , A.MB_CD , SUBSTR(A.SUPP_YRMN,1,4)||'�� '||SUBSTR(A.SUPP_YRMN,5,2)||'��', 'TEMP' , SYSDATE FROM FIDU.TTAC_COPYRTAL A, FIDU.TMEM_MB B WHERE A.MB_CD = B.MB_CD AND A.SUPP_YRMN =:SUPP_YRMN AND B.EMAIL_ADDR IS NOT NULL AND A.MB_CD >= '00000000' AND A.MB_CD <= 'ZZZZZZZZ' AND NVL(B.SUPPBRE_POST_RECV_YN,'D') !='D' AND A.TOT_REALSUPP_AMT > 0";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    // ���ڹ߼۵����� �ӽõ����� ����
    public DOBJ CALLsend_email_tmp_XIUD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD9");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsend_email_tmp_XIUD9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsend_email_tmp_XIUD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM KAKAO.MZSENDTRAN_TMP";
        sobj.setSql(query);
        return sobj;
    }
    // ���ڹ߼۵����� �ӽ�����
    public DOBJ CALLsend_email_tmp_XIUD14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD14");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsend_email_tmp_XIUD14(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsend_email_tmp_XIUD14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //���� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO KAKAO.MZSENDTRAN_TMP (SN, MSG_ID, SENDER_KEY, CHANNEL, SND_TYPE, TMPL_CD, USER_ID, SUBJECT, SMS_SND_NUM, PHONE_NUM, SND_MSG, ATTACHMENT, REQ_DTM, SMS_SND_YN, RESERVED1, RESERVED2, RESERVED3, RESERVED4, RESERVED5, RESERVED6) SELECT KAKAO.ALIMTALK_SEQ.NEXTVAL SN, KAKAO.ALIMTALK_SEQ.NEXTVAL MSG_ID, SENDER_KEY, CHANNEL, SND_TYPE, TMPL_CD, USER_ID, SUBJECT, SMS_SND_NUM, PHONE_NUM, SND_MSG, ATTACHMENT, REQ_DTM, SMS_SND_YN, RESERVED1, RESERVED2, RESERVED3, RESERVED4, RESERVED5, 'SUPP' RESERVED6 FROM (SELECT '9bb6b0be43e3d0ae1021d18efd14b8196be7cb8d' SENDER_KEY, 'A' AS CHANNEL, 'P' AS SND_TYPE, 'JIGEUB_04' AS TMPL_CD, A.MB_CD AS USER_ID, TO_NUMBER (SUBSTR (SUPP_YRMN, 5)) || '�� ����Ʈ ���޳�����' SUBJECT, '0226600483' AS SMS_SND_NUM, TRANSLATE (CP_NUM, '0123456789' || CP_NUM, '0123456789') AS PHONE_NUM, B.HANMB_NM || 'ȸ���� �ȳ��Ͻʴϱ�' || CHR (63) || CHR (13) || CHR (10) || '�ѱ��������۱���ȸ�Դϴ�.' || CHR (13) || CHR (10) || TO_NUMBER (SUBSTR (SUPP_YRMN, 1, 4)) || '�� ' || TO_NUMBER (SUBSTR (SUPP_YRMN, 5)) || '�� ���۱Ƿ� ���޳����� ������ ���� �˷��帳�ϴ�.' || CHR (13) || CHR (10) || CHR (13) || CHR (10) || '[' || TO_NUMBER (SUBSTR (SUPP_YRMN, 5)) || '�� ����Ʈ ���޳�����]' || CHR (13) || CHR (10) || '�� ������ : #SUPP_DAY#' ||' ���޵Ǿ����ϴ�.' || CHR (13) || CHR (10) || '�� ������ ' || CHR (13) || CHR (10) || CHR (13) || CHR (10) || '���÷� �����ϱ�' || CHR (13) || CHR (10) || '#URL#' || CHR (13) || CHR (10) || CHR (13) || CHR (10) || '����� ������ �����ϱ�' || CHR (13) || CHR (10) || '#MURL#' || CHR (13) || CHR (10) || CHR (13) || CHR (10) || '���ø����̼� �α����� �����Ͻ� ȸ���Ե鲲���� ����� ��ũ�� �̿��Ͻø� �˴ϴ�.' || CHR (13) || CHR (10) || '�ڼ��� ������ �������� ��������.' SND_MSG, '' ATTACHMENT, TO_CHAR (SYSDATE, 'yyyymmddhh24miss') REQ_DTM, 'Y' SMS_SND_YN, SUBSTRB (B.HANMB_NM, 1, 50) RESERVED1, B.MB_GBN1 RESERVED2, A.MB_CD RESERVED3, A.SUPP_YRMN RESERVED4, A.TRNSF_GBN RESERVED5 FROM FIDU.TTAC_COPYRTAL A, FIDU.TMEM_MB B, ACCT.TCAC_BANK C WHERE A.MB_CD = B.MB_CD AND B.SUPPBANK_CD = C.BANK_CD AND A.SUPP_YRMN = :SUPP_YRMN AND B.CP_NUM IS NOT NULL AND REGEXP_REPLACE (LENGTH (B.CP_NUM), '[^0-9]', '') >= 10 AND A.MB_CD >= '00000000' AND A.MB_CD <= 'ZZZZZZZZ' AND NVL (B.SUPPBRE_POST_RECV_YN, 'D') != 'D' AND A.TOT_ORGDISTR_AMT > 0 )";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //���� ���
        return sobj;
    }
    // �߼۴�� URL����
    public DOBJ CALLsend_email_tmp_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsend_email_tmp_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsend_email_tmp_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  'https:/'  ||  '/komca.page.link'  ||  CHR  (63)  ||  'LNK_KEY=PAYMENT'  ||  CHR  (38)  ||  'S_MB_CD='  ||  MB_CD  ||  CHR  (38)  ||  'S_TRNSF_GBN='  ||  TRNSF_GBN  ||  CHR  (38)  ||  'S_SUPP_YRMN='  ||  SUPP_YRMN  AS  LINK_URL,  CASE  WHEN  SMS_SEQ  IS  NULL  THEN  'https:/'  ||  '/www.komca.or.kr/e_mail/25_10_check_pwd.jsp'  ||  CHR  (63)  ||  'SEQ='  ||  MAIL_SEQ  ||  ''  ||  CHR  (38)  ||  'EMS_M_PWGBN=B'  ELSE  'https:/'  ||  '/m.komca.or.kr:8700/mobile2/navigation.jsp'  ||  CHR  (63)  ||  'm=2'  ||  CHR  (38)  ||  'seq='  ||  SMS_SEQ  END  AS  FALLBACK_URL,  CASE  WHEN  MAIL_SEQ  IS  NULL  THEN  'https:/'  ||  '/m.komca.or.kr:8700/mobile2/navigation.jsp'  ||  CHR  (63)  ||  'm=2'||  CHR(38)  ||'seq='||SMS_SEQ  ELSE  'https:/'  ||  '/www.komca.or.kr/e_mail/25_10_check_pwd.jsp'  ||  CHR  (63)  ||  'SEQ='||MAIL_SEQ||''||  CHR(38)  ||'EMS_M_PWGBN=B'  END  AS  DESKTOP_URL,  'https:/'  ||  '/m.komca.or.kr:8700/mobile2/navigation.jsp'  ||  CHR  (63)  ||  'm=2'  ||  CHR  (38)  ||  'seq='  ||  SMS_SEQ  AS  MURL,  MB_CD,  SUPP_YRMN,  TRNSF_GBN  FROM   \n";
        query +=" (SELECT  SEQ  MAIL_SEQ,  B.MSG_ID  SMS_SEQ,  NVL(RESERVED3,MAP4)  MB_CD,  NVL(RESERVED4,MAP3)  SUPP_YRMN,  NVL(RESERVED5,MAP2)  TRNSF_GBN  FROM  AMAIL.EMS_MAILQUEUE_TMP  A  FULL  OUTER  JOIN  KAKAO.MZSENDTRAN_TMP  B  ON  A.MAP3  =  B.RESERVED4   \n";
        query +=" AND  A.MAP4  =  B.RESERVED3   \n";
        query +=" AND  A.MAP2  =  B.RESERVED5  ) ";
        sobj.setSql(query);
        return sobj;
    }
    // MMS����LOOP
    public DOBJ CALLsend_email_tmp_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("SEL2");         //�߼۴�� URL���⿡�� ������Ų OBJECT�Դϴ�.(CALLsend_email_tmp_SEL2)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsend_email_tmp_OBJ1(Conn, dobj);           //  �ܺ�ȣ��
                dobj  = CALLsend_email_tmp_UPD16(Conn, dobj);           //  SMS���� URL UPDATE
            }
        }
        return dobj;
    }
    // �ܺ�ȣ��
    public DOBJ CALLsend_email_tmp_OBJ1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OBJ1");
        CommUtil cu = new CommUtil(dobj);
        HashMap   classinfo = new HashMap();
        classinfo.put("OBJECTID","OBJ1");
        classinfo.put("PACKAGE","komca.util.fcm");
        classinfo.put("CLASS","DynamicLink");
        classinfo.put("METHOD","getDirectLink");
        classinfo.put("INMAP","R:K");
        classinfo.put("OUTOBJ","SEL99");
        classinfo.put("OUTOBJ1","");
        dobj = cu.callPSInternal(dobj, Conn, classinfo );
        return dobj;
    }
    // SMS���� URL UPDATE
    public DOBJ CALLsend_email_tmp_UPD16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD16");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsend_email_tmp_UPD16(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD16") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsend_email_tmp_UPD16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ATTACHMENT ="";        //�˸����ư��ũ
        String SND_MSG ="";        //�˸��峻��
        String   SND_MSG_2 = dobj.getRetObject("R").getRecord().get("MURL");   //�˸��峻��
        String   SND_MSG_1 = dobj.getRetObject("OBJ1").getRecord().get("DYNAMIC_LINK");   //�˸��峻��
        String   ATTACHMENT_1 = dobj.getRetObject("OBJ1").getRecord().get("DYNAMIC_LINK");   //�˸����ư��ũ
        String   RESERVED3 = dobj.getRetObject("R").getRecord().get("MB_CD");   //�׽�Ʈ�÷�1
        String   RESERVED4 = dobj.getRetObject("R").getRecord().get("SUPP_YRMN");   //�׽�Ʈ�÷�4
        String   RESERVED5 = dobj.getRetObject("R").getRecord().get("TRNSF_GBN");   //�׽�Ʈ�÷�2
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update KAKAO.MZSENDTRAN_TMP  \n";
        query +=" set ATTACHMENT=REPLACE(ATTACHMENT,'#URL#',:ATTACHMENT_1) , SND_MSG=REPLACE(REPLACE(SND_MSG,'#URL#',:SND_MSG_1),'#MURL#',:SND_MSG_2)  \n";
        query +=" where RESERVED3=:RESERVED3  \n";
        query +=" and RESERVED5=:RESERVED5  \n";
        query +=" and RESERVED4=:RESERVED4";
        sobj.setSql(query);
        sobj.setString("SND_MSG_2", SND_MSG_2);               //�˸��峻��
        sobj.setString("SND_MSG_1", SND_MSG_1);               //�˸��峻��
        sobj.setString("ATTACHMENT_1", ATTACHMENT_1);               //�˸����ư��ũ
        sobj.setString("RESERVED3", RESERVED3);               //�׽�Ʈ�÷�1
        sobj.setString("RESERVED4", RESERVED4);               //�׽�Ʈ�÷�4
        sobj.setString("RESERVED5", RESERVED5);               //�׽�Ʈ�÷�2
        return sobj;
    }
    //##**$$send_email_tmp
    //##**$$end
}
