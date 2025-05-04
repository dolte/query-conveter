
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class sms_test1
{
    public sms_test1()
    {
    }
    //##**$$sms_test
    /*
    */
    public DOBJ CTLsms_test(DOBJ dobj)
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
            dobj  = CALLsms_test_MIUD3(Conn, dobj);           //  알림톡발송
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
    public DOBJ CTLsms_test( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsms_test_MIUD3(Conn, dobj);           //  알림톡발송
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 알림톡발송
    public DOBJ CALLsms_test_MIUD3(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLsms_test_SEL1(Conn, dobj);           //  알림톡시퀀스증가
                dobj  = CALLsms_test_OSP15(Conn, dobj);           //  입력
            }
        }
        return dobj;
    }
    // 알림톡시퀀스증가
    public DOBJ CALLsms_test_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsms_test_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsms_test_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  KAKAO.ALIMTALK_SEQ.NEXTVAL  SN,  KAKAO.ALIMTALK_SEQ.NEXTVAL  MSG_ID  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 입력
    public DOBJ CALLsms_test_OSP15(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP15");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("R");         //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        String[]  incolumns ={"SN","CHANNEL","PHONE_NUM","TMPL_CD","SUBJECT","SND_MSG","SMS_SND_MSG","SMS_SND_NUM","REQ_DEPT_CD","REQ_USR_ID","REQ_DTM","SMS_SND_YN","SLOT1","SLOT2","ATTACHMENT","RESERVED1","RESERVED2","RESERVED3","RESERVED4","RESERVED5","RESERVED6","MSG_ID","USER_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   ATTACHMENT = dobj.getRetObject("R").getRecord().get("ATTACHMENT");         //알림톡버튼링크
            record.put("ATTACHMENT",ATTACHMENT);
            ////
            String   CHANNEL = dobj.getRetObject("R").getRecord().get("CHANNEL");         //알림톡채널
            record.put("CHANNEL",CHANNEL);
            ////
            double   MSG_ID = dobj.getRetObject("SEL1").getRecord().getDouble("SN");         //SMS KEY
            record.put("MSG_ID",MSG_ID+"");
            ////
            String   PHONE_NUM = dobj.getRetObject("R").getRecord().get("PHONE_NUM");         //수신자휴대폰번호
            record.put("PHONE_NUM",PHONE_NUM);
            ////
            String   REQ_DEPT_CD = dobj.getRetObject("GOV").getRecord().get("DEPT_CD");         //발송요청부서코드
            record.put("REQ_DEPT_CD",REQ_DEPT_CD);
            ////
            String   REQ_DTM = dobj.getRetObject("R").getRecord().get("REQ_DTM");         //발송요청일시
            record.put("REQ_DTM",REQ_DTM);
            ////
            String   REQ_USR_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //발송요청자사번
            record.put("REQ_USR_ID",REQ_USR_ID);
            ////
            String   RESERVED1 = dobj.getRetObject("R").getRecord().get("RESERVED1");         //테스트컬럼1
            record.put("RESERVED1",RESERVED1);
            ////
            String   RESERVED2 = dobj.getRetObject("R").getRecord().get("RESERVED2");         //테스트컬럼2
            record.put("RESERVED2",RESERVED2);
            ////
            String   RESERVED3 = dobj.getRetObject("R").getRecord().get("RESERVED3");         //테스트컬럼1
            record.put("RESERVED3",RESERVED3);
            ////
            String   RESERVED4 = dobj.getRetObject("R").getRecord().get("RESERVED4");         //테스트컬럼4
            record.put("RESERVED4",RESERVED4);
            ////
            String   RESERVED5 = dobj.getRetObject("R").getRecord().get("RESERVED5");         //테스트컬럼2
            record.put("RESERVED5",RESERVED5);
            ////
            String   RESERVED6 = dobj.getRetObject("R").getRecord().get("RESERVED6");         //테스트컬럼2
            record.put("RESERVED6",RESERVED6);
            ////
            String   SLOT1 = dobj.getRetObject("R").getRecord().get("SLOT1");         //발송요청부가정보1
            record.put("SLOT1",SLOT1);
            ////
            String   SLOT2 = dobj.getRetObject("R").getRecord().get("SLOT2");         //발송요청부가정보2
            record.put("SLOT2",SLOT2);
            ////
            String   SMS_SND_MSG = dobj.getRetObject("R").getRecord().get("SMS_SND_MSG");         //sms발송메시지
            record.put("SMS_SND_MSG",SMS_SND_MSG);
            ////
            String   SMS_SND_NUM = dobj.getRetObject("R").getRecord().get("SMS_SND_NUM");         //sms발신여부
            record.put("SMS_SND_NUM",SMS_SND_NUM);
            ////
            String   SMS_SND_YN = dobj.getRetObject("R").getRecord().get("SMS_SND_YN");         //sms발송여부
            record.put("SMS_SND_YN",SMS_SND_YN);
            ////
            String   SN = dobj.getRetObject("SEL1").getRecord().get("SN");         //예명
            record.put("SN",SN);
            ////
            String   SND_MSG = dobj.getRetObject("R").getRecord().get("SND_MSG");         //알림톡내용
            record.put("SND_MSG",SND_MSG);
            ////
            String   SUBJECT = dobj.getRetObject("R").getRecord().get("SUBJECT");         //제목
            record.put("SUBJECT",SUBJECT);
            ////
            String   TMPL_CD = dobj.getRetObject("R").getRecord().get("TMPL_CD");         //알림톡탬플릿코드
            record.put("TMPL_CD",TMPL_CD);
            ////
            String   USER_ID = dobj.getRetObject("R").getRecord().get("USER_ID");         //사용자번호
            record.put("USER_ID",USER_ID);
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_SEND_MSG";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP15");
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$sms_test
    //##**$$end
}
