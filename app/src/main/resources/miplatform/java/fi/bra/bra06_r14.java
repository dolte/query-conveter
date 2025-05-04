
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra06_r14
{
    public bra06_r14()
    {
    }
    //##**$$search_sms_list
    /*
    */
    public DOBJ CTLsearch_sms_list(DOBJ dobj)
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
            dobj  = CALLsearch_sms_list_SEL1(Conn, dobj);           //  현황목록 조회
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
    public DOBJ CTLsearch_sms_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_sms_list_SEL1(Conn, dobj);           //  현황목록 조회
        return dobj;
    }
    // 현황목록 조회
    public DOBJ CALLsearch_sms_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_sms_list_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_sms_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TYPE = dobj.getRetObject("S").getRecord().get("TYPE");   //TYPE
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SEND_DATE  ,  DELIVER_DATE  ,  UPSO_CD  ,  UPSO_NM  ,  RESULT  ,  CODE_NM  ,  SUBJECT  ,  SMS_MSG  ,  DEST_NAME  ,  PHONE_NUMBER  ,  STAFF_CD  ,  STAFF_NM  ,  RESERVED4  ,  BRAN_CD  FROM  (   \n";
        query +=" SELECT  A.NOW_DATE  SEND_DATE  ,  B.DELIVER_DATE  ,  D.UPSO_CD  ,  D.UPSO_NM  ,  B.RESULT  ,  C.CODE_NM  ,  A.SUBJECT  ,  A.SMS_MSG  ,  B.DEST_NAME  ,  B.PHONE_NUMBER  ,  A.RESERVED1  STAFF_CD  ,   \n";
        query +=" (SELECT  HAN_NM  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  A.RESERVED1)  STAFF_NM  ,  A.RESERVED4  ,  D.BRAN_CD  FROM  KOMSMS.SDK_SMS_REPORT  A  ,  KOMSMS.SDK_SMS_REPORT_DETAIL  B  ,  FIDU.TENV_CODE  C  ,  GIBU.TBRA_UPSO  D  WHERE  A.USER_ID  =  D.UPSO_CD   \n";
        query +=" AND  B.MSG_ID(+)  =  A.MSG_ID   \n";
        query +=" AND  C.HIGH_CD(+)  =  '00327'   \n";
        query +=" AND  C.CODE_CD(+)  =  RESULT   \n";
        query +=" AND  D.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.RESERVED1  =  NVL(:STAFF_CD,  A.RESERVED1)   \n";
        query +=" AND  A.RESERVED4  =  NVL(:TYPE,  A.RESERVED4)   \n";
        query +=" AND  SUBSTR(A.NOW_DATE,0,8)  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  UNION   \n";
        query +=" SELECT  A.NOW_DATE  SEND_DATE  ,  B.DELIVER_DATE  ,  D.UPSO_CD  ,  D.UPSO_NM  ,  B.RESULT  ,  C.CODE_NM  ,  A.SUBJECT  ,  A.MMS_MSG  AS  SMS_MSG  ,  B.DEST_NAME  ,  B.PHONE_NUMBER  ,  A.RESERVED1  STAFF_CD  ,   \n";
        query +=" (SELECT  HAN_NM  FROM  INSA.TINS_MST01  WHERE  STAFF_NUM  =  A.RESERVED1)  STAFF_NM  ,  A.RESERVED4  ,  D.BRAN_CD  FROM  KOMSMS.SDK_MMS_REPORT  A  ,  KOMSMS.SDK_MMS_REPORT_DETAIL  B  ,  FIDU.TENV_CODE  C  ,  GIBU.TBRA_UPSO  D  WHERE  A.USER_ID  =  D.UPSO_CD   \n";
        query +=" AND  B.MSG_ID(+)  =  A.MSG_ID   \n";
        query +=" AND  C.HIGH_CD(+)  =  '00327'   \n";
        query +=" AND  C.CODE_CD(+)  =  RESULT   \n";
        query +=" AND  D.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.RESERVED1  =  NVL(:STAFF_CD,  A.RESERVED1)   \n";
        query +=" AND  A.RESERVED4  =  NVL(:TYPE,  A.RESERVED4)   \n";
        query +=" AND  SUBSTR(A.NOW_DATE,0,8)  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  )  ORDER  BY  SEND_DATE ";
        sobj.setSql(query);
        sobj.setString("TYPE", TYPE);               //TYPE
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$search_sms_list
    //##**$$log_kakao_msg
    /*
    */
    public DOBJ CTLlog_kakao_msg(DOBJ dobj)
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
            dobj  = CALLlog_kakao_msg_SEL1(Conn, dobj);           //  로그 조회
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
    public DOBJ CTLlog_kakao_msg( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLlog_kakao_msg_SEL1(Conn, dobj);           //  로그 조회
        return dobj;
    }
    // 로그 조회
    public DOBJ CALLlog_kakao_msg_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLlog_kakao_msg_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLlog_kakao_msg_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TYPE = dobj.getRetObject("S").getRecord().get("TYPE");   //TYPE
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT '2' AS ORD , A.CHANNEL , A.SND_TYPE , A.PHONE_NUM , A.TMPL_CD , A.SUBJECT , A.SND_MSG , A.SMS_SND_MSG , A.SMS_SND_NUM , A.REQ_DEPT_CD , A.REQ_USR_ID , A.REQ_DTM , A.SND_DTM , A.RSLT_CD , B.CODE_NM AS RSLT_NM , A.RCPT_DTM , NVL(A.SMS_SND_DTM, A.SND_DTM) AS SMS_SND_DTM , A.SMS_RSLT_CD , NVL(C.CODE_NM, B.CODE_NM) AS SMS_RSLT_NM , A.SMS_RCPT_DTM , (CASE WHEN TRAN_STS = '1' THEN '발송대기' WHEN TRAN_STS = '2' THEN '발송처리중' WHEN TRAN_STS = '3' THEN '발송요청완료' WHEN TRAN_STS = '4' THEN '알림톡결과수신완료' WHEN TRAN_STS = '5'  ";
        query +=" OR TRAN_STS = '6' THEN '문자결과수신완료' END) AS TRAN_STS , A.ATTACHMENT , A.RESERVED1 , A.RESERVED2 , A.RESERVED3 , A.RESERVED4 , A.RESERVED5 , A.RESERVED6 , A.MSG_ID , A.USER_ID , D.UPSO_CD , D.UPSO_NM , FIDU.GET_STAFF_NM(A.REQ_USR_ID) AS STAFF_NM , GIBU.GET_BRAN_NM(A.RESERVED3) AS BRAN_NM , FIDU.GET_STAFF_NM(D.STAFF_CD) AS STAFF_CD  ";
        query +=" FROM KAKAO.MZSENDLOG A , FIDU.TENV_CODE B , FIDU.TENV_CODE C , GIBU.TBRA_UPSO D  ";
        query +=" WHERE 1=1  ";
        query +=" AND B.HIGH_CD = '00458'  ";
        query +=" AND B.USE_YN = 'Y'  ";
        query +=" AND C.HIGH_CD(+) = '00456'  ";
        query +=" AND C.USE_YN(+) = 'Y'  ";
        query +=" AND A.RSLT_CD = B.CODE_CD(+)  ";
        query +=" AND A.SMS_RSLT_CD = C.CODE_CD(+)  ";
        query +=" AND A.USER_ID = D.UPSO_CD  ";
        query +=" AND A.RESERVED3 = DECODE(:BRAN_CD, 'AL', A.RESERVED3, :BRAN_CD)  ";
        query +=" AND A.REQ_DTM BETWEEN :START_DAY || '000000'  ";
        query +=" AND :END_DAY || '235959'  ";
        if( !TYPE.equals("") )
        {
            query +=" AND A.RESERVED6 = :TYPE  ";
        }
        if( !STAFF_CD.equals("") )
        {
            query +=" AND D.STAFF_CD = :STAFF_CD  ";
        }
        if( !UPSO_CD.equals("") )
        {
            query +=" AND A.USER_ID = :UPSO_CD  ";
        }
        query +=" UNION ALL  ";
        query +=" SELECT '1' AS ORD , A.CHANNEL , A.SND_TYPE , A.PHONE_NUM , A.TMPL_CD , A.SUBJECT , A.SND_MSG , A.SMS_SND_MSG , A.SMS_SND_NUM , A.REQ_DEPT_CD , A.REQ_USR_ID , A.REQ_DTM , A.SND_DTM , A.RSLT_CD , '발송중' AS RSLT_NM , A.RCPT_DTM , NVL(A.SMS_SND_DTM, A.SND_DTM) AS SMS_SND_DTM , A.SMS_RSLT_CD , '발송중' AS SMS_RSLT_NM , A.SMS_RCPT_DTM , '발송처리중' AS TRAN_STS , A.ATTACHMENT , A.RESERVED1 , A.RESERVED2 , A.RESERVED3 , A.RESERVED4 , A.RESERVED5 , A.RESERVED6 , A.MSG_ID , A.USER_ID , D.UPSO_CD , D.UPSO_NM , FIDU.GET_STAFF_NM(A.REQ_USR_ID) AS STAFF_NM , GIBU.GET_BRAN_NM(A.RESERVED3) AS BRAN_NM , FIDU.GET_STAFF_NM(D.STAFF_CD) AS STAFF_CD  ";
        query +=" FROM KAKAO.MZSENDTRAN A , GIBU.TBRA_UPSO D  ";
        query +=" WHERE 1=1  ";
        query +=" AND A.USER_ID = D.UPSO_CD  ";
        query +=" AND A.RESERVED3 = DECODE(:BRAN_CD, 'AL', A.RESERVED3, :BRAN_CD)  ";
        query +=" AND A.REQ_DTM BETWEEN :START_DAY || '000000'  ";
        query +=" AND :END_DAY || '235959'  ";
        if( !TYPE.equals("") )
        {
            query +=" AND A.RESERVED6 = :TYPE  ";
        }
        if( !STAFF_CD.equals("") )
        {
            query +=" AND D.STAFF_CD = :STAFF_CD  ";
        }
        if( !UPSO_CD.equals("") )
        {
            query +=" AND A.USER_ID = :UPSO_CD  ";
        }
        query +=" ORDER BY ORD, 19, REQ_DTM  ";
        sobj.setSql(query);
        if(!TYPE.equals(""))
        {
            sobj.setString("TYPE", TYPE);               //TYPE
        }
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        }
        sobj.setString("START_DAY", START_DAY);               //시작일
        if(!UPSO_CD.equals(""))
        {
            sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$log_kakao_msg
    //##**$$end
}
