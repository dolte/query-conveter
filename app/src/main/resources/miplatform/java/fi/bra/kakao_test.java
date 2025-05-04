
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
            dobj  = CALLkakao_test_MIUD3(Conn, dobj);           //  알림톡발송
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
        dobj  = CALLkakao_test_MIUD3(Conn, dobj);           //  알림톡발송
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 알림톡발송
    public DOBJ CALLkakao_test_MIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLkakao_test_SEL1(Conn, dobj);           //  알림톡시퀀스증가
                dobj  = CALLkakao_test_INS2(Conn, dobj);           //  발송대상입력
            }
        }
        return dobj;
    }
    // 알림톡시퀀스증가
    public DOBJ CALLkakao_test_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
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
    // 발송대상입력
    public DOBJ CALLkakao_test_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
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
        String   RESERVED4 = dvobj.getRecord().get("RESERVED4");   //테스트컬럼4
        String   RESERVED5 = dvobj.getRecord().get("RESERVED5");   //테스트컬럼2
        String   REQ_DTM = dvobj.getRecord().get("REQ_DTM");   //발송요청일시
        String   USER_ID = dvobj.getRecord().get("USER_ID");   //사용자번호
        String   TMPL_CD = dvobj.getRecord().get("TMPL_CD");   //알림톡탬플릿코드
        String   CHANNEL = dvobj.getRecord().get("CHANNEL");   //알림톡채널
        String   ATTACHMENT = dvobj.getRecord().get("ATTACHMENT");   //알림톡버튼링크
        String   SMS_SND_NUM = dvobj.getRecord().get("SMS_SND_NUM");   //sms발신여부
        String   RESERVED3 = dvobj.getRecord().get("RESERVED3");   //테스트컬럼1
        String   SND_MSG = dvobj.getRecord().get("SND_MSG");   //알림톡내용
        String   RESERVED6 = dvobj.getRecord().get("RESERVED6");   //테스트컬럼2
        String   RESERVED1 = dvobj.getRecord().get("RESERVED1");   //테스트컬럼1
        String   SUBJECT = dvobj.getRecord().get("SUBJECT");   //제목
        String   PHONE_NUM = dvobj.getRecord().get("PHONE_NUM");   //수신자휴대폰번호
        String   RESERVED2 = dvobj.getRecord().get("RESERVED2");   //테스트컬럼2
        String   SENDER_KEY = dvobj.getRecord().get("SENDER_KEY");   //알림톡키
        double   MSG_ID = dobj.getRetObject("SEL1").getRecord().getDouble("MSG_ID");   //SMS KEY
        String   SMS_SND_YN ="Y";   //sms발송여부
        String   SN = dobj.getRetObject("SEL1").getRecord().get("SN");   //예명
        String   SND_TYPE ="P";   //알림톡발송방식
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into KAKAO.MZSENDTRAN (SENDER_KEY, RESERVED2, SN, PHONE_NUM, SUBJECT, RESERVED1, RESERVED6, SND_MSG, RESERVED3, SMS_SND_NUM, MSG_ID, ATTACHMENT, SND_TYPE, SMS_SND_YN, CHANNEL, TMPL_CD, USER_ID, REQ_DTM, RESERVED5, RESERVED4)  \n";
        query +=" values(:SENDER_KEY , :RESERVED2 , :SN , :PHONE_NUM , :SUBJECT , :RESERVED1 , :RESERVED6 , :SND_MSG , :RESERVED3 , :SMS_SND_NUM , :MSG_ID , :ATTACHMENT , :SND_TYPE , :SMS_SND_YN , :CHANNEL , :TMPL_CD , :USER_ID , :REQ_DTM , :RESERVED5 , :RESERVED4 )";
        sobj.setSql(query);
        sobj.setString("RESERVED4", RESERVED4);               //테스트컬럼4
        sobj.setString("RESERVED5", RESERVED5);               //테스트컬럼2
        sobj.setString("REQ_DTM", REQ_DTM);               //발송요청일시
        sobj.setString("USER_ID", USER_ID);               //사용자번호
        sobj.setString("TMPL_CD", TMPL_CD);               //알림톡탬플릿코드
        sobj.setString("CHANNEL", CHANNEL);               //알림톡채널
        sobj.setString("ATTACHMENT", ATTACHMENT);               //알림톡버튼링크
        sobj.setString("SMS_SND_NUM", SMS_SND_NUM);               //sms발신여부
        sobj.setString("RESERVED3", RESERVED3);               //테스트컬럼1
        sobj.setString("SND_MSG", SND_MSG);               //알림톡내용
        sobj.setString("RESERVED6", RESERVED6);               //테스트컬럼2
        sobj.setString("RESERVED1", RESERVED1);               //테스트컬럼1
        sobj.setString("SUBJECT", SUBJECT);               //제목
        sobj.setString("PHONE_NUM", PHONE_NUM);               //수신자휴대폰번호
        sobj.setString("RESERVED2", RESERVED2);               //테스트컬럼2
        sobj.setString("SENDER_KEY", SENDER_KEY);               //알림톡키
        sobj.setDouble("MSG_ID", MSG_ID);               //SMS KEY
        sobj.setString("SMS_SND_YN", SMS_SND_YN);               //sms발송여부
        sobj.setString("SN", SN);               //예명
        sobj.setString("SND_TYPE", SND_TYPE);               //알림톡발송방식
        return sobj;
    }
    //##**$$kakao_test
    //##**$$end
}
