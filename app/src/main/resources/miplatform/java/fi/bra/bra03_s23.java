
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s23
{
    public bra03_s23()
    {
    }
    //##**$$sel_mail_target
    /*
    */
    public DOBJ CTLsel_mail_target(DOBJ dobj)
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
            if(!dobj.getRetObject("S").getRecord().get("UPSO_CD").equals(""))
            {
                dobj  = CALLsel_mail_target_SEL4(Conn, dobj);           //  단일업소조회
            }
            else
            {
                dobj  = CALLsel_mail_target_SEL1(Conn, dobj);           //  메일발송대상조회
            }
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
    public DOBJ CTLsel_mail_target( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if(!dobj.getRetObject("S").getRecord().get("UPSO_CD").equals(""))
        {
            dobj  = CALLsel_mail_target_SEL4(Conn, dobj);           //  단일업소조회
        }
        else
        {
            dobj  = CALLsel_mail_target_SEL1(Conn, dobj);           //  메일발송대상조회
        }
        return dobj;
    }
    // 단일업소조회
    public DOBJ CALLsel_mail_target_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_mail_target_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_mail_target_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ROWNUM  AS  R_NUM  ,  BRAN_CD  ,  UPSO_CD  ,  DEMD_YRMN  ,  UPSO_NM  ,  EMAIL_ADDR  ,  'cswehh@komca.or.kr'  AS  SENDER_MAIL  ,  '(사)한국음악저작권협회'  AS  SENDER_NM  ,  SUBSTR(DEMD_YRMN,  1,  4)  ||  '년  '  ||  SUBSTR(DEMD_YRMN,  5,  2)  ||  '월  '  ||  UPSO_NM  ||  '  통합징수  저작권사용료  상세  내역  안내의  건'  AS  SUBJECT  ,  TO_CHAR(TOT_USE_AMT,  'FM999,999,999,999,999')  AS  TOTAL_AMT  ,  TO_CHAR(NVL(TOT_USE_AMT  -  KOSCAP_AMT  -  FKMP_AMT  -  RIAK_AMT,  0),  'FM999,999,999,999,999')  AS  KOMCA_AMT  ,  TO_CHAR(KOSCAP_AMT,  'FM999,999,999,999,999')  AS  KOSCAP_AMT  ,  TO_CHAR(FKMP_AMT,  'FM999,999,999,999,999')  AS  FKMP_AMT  ,  TO_CHAR(RIAK_AMT,  'FM999,999,999,999,999')  AS  RIAK_AMT  ,  B.TARGET_FLAG  ,  B.SEND_TIME  ,  B.DELIVER_TIME  ,  B.OPEN_TIME  ,  DECODE(SEND_STATE,  '40',  '발송',  '05',  '발송실패',  SEND_STATE)  AS  SEND_STATE  ,  (CASE  ERROR_CODE  WHEN  '00'  THEN  '정상'  WHEN  '10'  THEN  '메일형식오류'  WHEN  '20'  THEN  'HOST  UNKNOWN'  WHEN  '30'  THEN  '발송보류'  WHEN  '40'  THEN  'UNKNOWN  USER'  WHEN  '50'  THEN  '수신거부'  WHEN  '60'  THEN  'MAILBOX  FULL'  WHEN  '70'  THEN  'CONNECTION  FAIL'  WHEN  '80'  THEN  'CONNECTION  ERROR'  WHEN  '90'  THEN  'TIME  OUT'  WHEN  '95'  THEN  'UNKNOWN  RESPONSE'  WHEN  '99'  THEN  'NO  RESPONSE'  END)  AS  ERROR_CODE  ,  SUBSTR(DEMD_YRMN,  1,  4)  ||  '년  '  ||  SUBSTR(DEMD_YRMN,  5,  2)  ||  '월  '  ||  UPSO_NM  AS  SUB_NAME  ,  SUBSTR(DEMD_YRMN,  1,  4)  ||  '년  '  ||  SUBSTR(DEMD_YRMN,  5,  2)  ||  '월  '  ||  UPSO_NM  ||  '|'  ||  TO_CHAR(TOT_USE_AMT,  'FM999,999,999,999,999')  ||  '|'  ||  TO_CHAR(NVL(TOT_USE_AMT  -  KOSCAP_AMT  -  RIAK_AMT  -  FKMP_AMT,  0),  'FM999,999,999,999,999')  ||  '|'  ||  TO_CHAR(KOSCAP_AMT,  'FM999,999,999,999,999')  ||  '|'  ||  TO_CHAR(FKMP_AMT,  'FM999,999,999,999,999')  ||  '|'  ||  TO_CHAR(RIAK_AMT,  'FM999,999,999,999,999')  AS  MAP_CONTENT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.DEMD_YRMN  ,  A.TOT_USE_AMT  ,  A.BRAN_CD  ,   \n";
        query +=" (SELECT  UPSO_NM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD)  AS  UPSO_NM  ,   \n";
        query +=" (SELECT  EMAIL_ADDR  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD)  AS  EMAIL_ADDR  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(A.UPSO_CD,  A.DEMD_YRMN)  >  0)  THEN  TRUNC(MONPRNCFEE  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  A.BSTYP_CD))  ELSE  0  END)  AS  KOSCAP_AMT  ,  NVL((SELECT  SUM(DEMD_AMT)  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  A.DEMD_YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000002'),  0)  AS  FKMP_AMT  ,  NVL((SELECT  SUM(DEMD_AMT)  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  A.DEMD_YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000003'),  0)  AS  RIAK_AMT  FROM  GIBU.TBRA_DEMD_OCR  A  WHERE  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD  ORDER  BY  A.BRAN_CD,  A.UPSO_CD  )  A  ,  (   \n";
        query +=" SELECT  B.MAP1  ,  B.MAP2  ,  B.TARGET_FLAG  ,  C.SEND_TIME  ,  C.DELIVER_TIME  ,  C.OPEN_TIME  ,  C.SEND_STATE  ,  C.ERROR_CODE  FROM  AMAIL.EMS_MAILQUEUE  B  ,  AMAIL.AUTO120  C  WHERE  B.MAIL_CODE  =  '41'   \n";
        query +=" AND  B.MAP2  =  :DEMD_YRMN   \n";
        query +=" AND  B.SEQ  =  SUBSTR(C.MAPPING,  1,  INSTR(C.MAPPING,  '|',  1))  )  B  WHERE  A.UPSO_CD  =  B.MAP1(+)   \n";
        query +=" AND  A.TOT_USE_AMT  >  0   \n";
        query +=" AND  (A.KOSCAP_AMT  >  0   \n";
        query +=" OR  A.RIAK_AMT  >  0   \n";
        query +=" OR  A.FKMP_AMT  >  0)  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 메일발송대상조회
    public DOBJ CALLsel_mail_target_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_mail_target_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_mail_target_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ROWNUM  AS  R_NUM  ,  BRAN_CD  ,  UPSO_CD  ,  DEMD_YRMN  ,  UPSO_NM  ,  EMAIL_ADDR  ,  'cswehh@komca.or.kr'  AS  SENDER_MAIL  ,  '(사)한국음악저작권협회'  AS  SENDER_NM  ,  SUBSTR(DEMD_YRMN,  1,  4)  ||  '년  '  ||  SUBSTR(DEMD_YRMN,  5,  2)  ||  '월  '  ||  UPSO_NM  ||  '  통합징수  저작권사용료  상세  내역  안내의  건'  AS  SUBJECT  ,  TO_CHAR(TOT_USE_AMT,  'FM999,999,999,999,999')  AS  TOTAL_AMT  ,  TO_CHAR(NVL(TOT_USE_AMT  -  KOSCAP_AMT  -  FKMP_AMT  -  RIAK_AMT,  0),  'FM999,999,999,999,999')  AS  KOMCA_AMT  ,  TO_CHAR(KOSCAP_AMT,  'FM999,999,999,999,999')  AS  KOSCAP_AMT  ,  TO_CHAR(FKMP_AMT,  'FM999,999,999,999,999')  AS  FKMP_AMT  ,  TO_CHAR(RIAK_AMT,  'FM999,999,999,999,999')  AS  RIAK_AMT  ,  B.TARGET_FLAG  ,  B.SEND_TIME  ,  B.DELIVER_TIME  ,  B.OPEN_TIME  ,  DECODE(SEND_STATE,  '40',  '발송',  '05',  '발송실패',  SEND_STATE)  AS  SEND_STATE  ,  (CASE  ERROR_CODE  WHEN  '00'  THEN  '정상'  WHEN  '10'  THEN  '메일형식오류'  WHEN  '20'  THEN  'HOST  UNKNOWN'  WHEN  '30'  THEN  '발송보류'  WHEN  '40'  THEN  'UNKNOWN  USER'  WHEN  '50'  THEN  '수신거부'  WHEN  '60'  THEN  'MAILBOX  FULL'  WHEN  '70'  THEN  'CONNECTION  FAIL'  WHEN  '80'  THEN  'CONNECTION  ERROR'  WHEN  '90'  THEN  'TIME  OUT'  WHEN  '95'  THEN  'UNKNOWN  RESPONSE'  WHEN  '99'  THEN  'NO  RESPONSE'  END)  AS  ERROR_CODE  ,  SUBSTR(DEMD_YRMN,  1,  4)  ||  '년  '  ||  SUBSTR(DEMD_YRMN,  5,  2)  ||  '월  '  ||  UPSO_NM  AS  SUB_NAME  ,  SUBSTR(DEMD_YRMN,  1,  4)  ||  '년  '  ||  SUBSTR(DEMD_YRMN,  5,  2)  ||  '월  '  ||  UPSO_NM  ||  '|'  ||  TO_CHAR(TOT_USE_AMT,  'FM999,999,999,999,999')  ||  '|'  ||  TO_CHAR(NVL(TOT_USE_AMT  -  KOSCAP_AMT  -  RIAK_AMT  -  FKMP_AMT,  0),  'FM999,999,999,999,999')  ||  '|'  ||  TO_CHAR(KOSCAP_AMT,  'FM999,999,999,999,999')  ||  '|'  ||  TO_CHAR(FKMP_AMT,  'FM999,999,999,999,999')  ||  '|'  ||  TO_CHAR(RIAK_AMT,  'FM999,999,999,999,999')  AS  MAP_CONTENT  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.DEMD_YRMN  ,  A.TOT_USE_AMT  ,  A.BRAN_CD  ,   \n";
        query +=" (SELECT  UPSO_NM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD)  AS  UPSO_NM  ,   \n";
        query +=" (SELECT  EMAIL_ADDR  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  A.UPSO_CD)  AS  EMAIL_ADDR  ,  (CASE  WHEN  (GIBU.FT_GET_IS_BSCON(A.UPSO_CD,  A.DEMD_YRMN)  >  0)  THEN  TRUNC(MONPRNCFEE  *   \n";
        query +=" (SELECT  MNG_RATE  /  100  FROM  GIBU.TBRA_BSCON_MNG_RATE  WHERE  BSTYP_CD  =  A.BSTYP_CD))  ELSE  0  END)  AS  KOSCAP_AMT  ,  NVL((SELECT  SUM(DEMD_AMT)  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000002'),  0)  AS  FKMP_AMT  ,  NVL((SELECT  SUM(DEMD_AMT)  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000003'),  0)  AS  RIAK_AMT  FROM  GIBU.TBRA_DEMD_OCR  A  WHERE  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)  ORDER  BY  A.BRAN_CD,  A.UPSO_CD  )  A  ,   \n";
        query +=" (SELECT  B.MAP1  ,  B.MAP2  ,  B.TARGET_FLAG  ,  C.SEND_TIME  ,  C.DELIVER_TIME  ,  C.OPEN_TIME  ,  C.SEND_STATE  ,  C.ERROR_CODE  FROM  AMAIL.EMS_MAILQUEUE  B  ,  AMAIL.AUTO120  C  WHERE  B.MAIL_CODE  =  '41'   \n";
        query +=" AND  B.MAP2  =  :DEMD_YRMN   \n";
        query +=" AND  B.SEQ  =  SUBSTR(C.MAPPING,  1,  INSTR(C.MAPPING,  '|',  1))  )  B  WHERE  A.UPSO_CD  =  B.MAP1(+)   \n";
        query +=" AND  EMAIL_ADDR  IS  NOT  NULL   \n";
        query +=" AND  A.TOT_USE_AMT  >  0   \n";
        query +=" AND  (A.KOSCAP_AMT  >  0   \n";
        query +=" OR  A.RIAK_AMT  >  0   \n";
        query +=" OR  A.FKMP_AMT  >  0)  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$sel_mail_target
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
            qexe.DispSelectSql(sobj);
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
