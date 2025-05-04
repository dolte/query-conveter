
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s22
{
    public bra03_s22()
    {
    }
    //##**$$send_mail
    /*
    */
    public DOBJ CTLsend_mail(DOBJ dobj)
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
            dobj  = CALLsend_mail_MPD1(Conn, dobj);           //  분기
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
    public DOBJ CTLsend_mail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsend_mail_MPD1(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLsend_mail_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(!dvobj.getRecord().get("EMAIL_ADDR").equals(""))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsend_mail_XIUD4(Conn, dobj);           //  메일 큐 삽입
            }
        }
        return dobj;
    }
    // 메일 큐 삽입
    public DOBJ CALLsend_mail_XIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD4");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsend_mail_XIUD4(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsend_mail_XIUD4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        String   FROM_EMAIL = dobj.getRetObject("R").getRecord().get("SENDER_MAIL");   //발신자 이메일주소
        String   FROM_NAME = dobj.getRetObject("R").getRecord().get("SENDER_NM");   //발신자 이름
        String   MAP1 = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //메일 MAP1
        String   MAP2 = dobj.getRetObject("R").getRecord().get("DEMD_YRMN");   //메일 MAP2
        String   MAP3 = dobj.getRetObject("R").getRecord().get("SUB_NAME");   //메일 MAP3
        String   MAP_CONTENT = dobj.getRetObject("R").getRecord().get("MAP_CONTENT");   //컨텐츠
        String   SUBJECT = dobj.getRetObject("R").getRecord().get("SUBJECT");   //제목
        String   TO_EMAIL = dobj.getRetObject("R").getRecord().get("EMAIL_ADDR");   //수신자 이메일
        String   TO_NAME = dobj.getRetObject("R").getRecord().get("UPSO_NM");   //수신자 이름
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO AMAIL.EMS_MAILQUEUE (SEQ, MAIL_CODE, TO_EMAIL, TO_NAME, FROM_EMAIL, FROM_NAME, SUBJECT, TARGET_FLAG, REG_DATE, MAP1, MAP2, MAP3, MAP_CONTENT) SELECT AMAIL.EMS_MAILQUEUE_SEQ.NEXTVAL , '41' , :TO_EMAIL , :TO_NAME , :FROM_EMAIL , :FROM_NAME , :SUBJECT , 'N' , SYSDATE , :MAP1 , :MAP2 , :MAP3 , :MAP_CONTENT FROM DUAL";
        sobj.setSql(query);
        sobj.setString("FROM_EMAIL", FROM_EMAIL);               //발신자 이메일주소
        sobj.setString("FROM_NAME", FROM_NAME);               //발신자 이름
        sobj.setString("MAP1", MAP1);               //메일 MAP1
        sobj.setString("MAP2", MAP2);               //메일 MAP2
        sobj.setString("MAP3", MAP3);               //메일 MAP3
        sobj.setString("MAP_CONTENT", MAP_CONTENT);               //컨텐츠
        sobj.setString("SUBJECT", SUBJECT);               //제목
        sobj.setString("TO_EMAIL", TO_EMAIL);               //수신자 이메일
        sobj.setString("TO_NAME", TO_NAME);               //수신자 이름
        return sobj;
    }
    //##**$$send_mail
    //##**$$end
}
