
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r13
{
    public tac10_r13()
    {
    }
    //##**$$mst_send_chk
    /*
    */
    public DOBJ CTLmst_send_chk(DOBJ dobj)
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
            dobj  = CALLmst_send_chk_SEL1(Conn, dobj);           //  지급내역서 이메일 발송현황 조회
            dobj  = CALLmst_send_chk_SEL2(Conn, dobj);           //  지급내역서 MMS 발송현황 조회
            dobj  = CALLmst_send_chk_SEL3(Conn, dobj);           //  지급내역서 MMS 발송현황 조회
            dobj  = CALLmst_send_chk_SEL4(Conn, dobj);           //  지급내역서 MMS 발송현황 조회
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
    public DOBJ CTLmst_send_chk( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmst_send_chk_SEL1(Conn, dobj);           //  지급내역서 이메일 발송현황 조회
        dobj  = CALLmst_send_chk_SEL2(Conn, dobj);           //  지급내역서 MMS 발송현황 조회
        dobj  = CALLmst_send_chk_SEL3(Conn, dobj);           //  지급내역서 MMS 발송현황 조회
        dobj  = CALLmst_send_chk_SEL4(Conn, dobj);           //  지급내역서 MMS 발송현황 조회
        return dobj;
    }
    // 지급내역서 이메일 발송현황 조회
    public DOBJ CALLmst_send_chk_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmst_send_chk_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmst_send_chk_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //회원코드
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT WORKDAY , MEMBER_ID , TO_NAME , TO_EMAIL , REG_DATE , SEND_TIME , DELIVER_TIME , OPEN_TIME , DECODE(SEND_STATE, '40', '발송', '05', '발송실패', SEND_STATE) AS SEND_STATE , (CASE ERROR_CODE WHEN '00' THEN '정상' WHEN '10' THEN '메일형식오류' WHEN '20' THEN 'HOST UNKNOWN' WHEN '30' THEN '발송보류' WHEN '40' THEN 'UNKNOWN USER' WHEN '50' THEN '수신거부' WHEN '60' THEN 'MAILBOX FULL' WHEN '70' THEN 'CONNECTION FAIL' WHEN '80' THEN 'CONNECTION ERROR' WHEN '90' THEN 'TIME OUT' WHEN '95' THEN 'UNKNOWN RESPONSE' WHEN '99' THEN 'NO RESPONSE' END) AS ERROR_CODE  ";
        query +=" FROM AMAIL.AUTO120  ";
        query +=" WHERE SUBSTR(WORKDAY , 1,6) BETWEEN :FROM_YRMN  ";
        query +=" AND :TO_YRMN  ";
        query +=" AND SUBSTR(MAPPING, INSTR(MAPPING, '|') + 1, 2) = '25'  ";
        if( !FRMB_CD.equals("") )
        {
            query +=" AND MEMBER_ID >= :FRMB_CD  ";
        }
        if( !TOMB_CD.equals("") )
        {
            query +=" AND MEMBER_ID <= :TOMB_CD  ";
        }
        sobj.setSql(query);
        if(!FRMB_CD.equals(""))
        {
            sobj.setString("FRMB_CD", FRMB_CD);               //회원코드
        }
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        if(!TOMB_CD.equals(""))
        {
            sobj.setString("TOMB_CD", TOMB_CD);               //회원코드
        }
        return sobj;
    }
    // 지급내역서 MMS 발송현황 조회
    public DOBJ CALLmst_send_chk_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmst_send_chk_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmst_send_chk_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FRMB_CD = dobj.getRetObject("S").getRecord().get("FRMB_CD");   //회원코드
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        String   TOMB_CD = dobj.getRetObject("S").getRecord().get("TOMB_CD");   //회원코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT SUBSTR(NOW_DATE, 1,8) AS NOW_DATE ,USER_ID ,fidu.get_mb_nm(USER_ID) AS TO_NAME ,REVERSE(SUBSTR(REVERSE(DEST_INFO), 1, INSTR(REVERSE(DEST_INFO), '^') -1)) AS PHON_NUM ,CASE WHEN SUCC_COUNT = '1'  ";
        query +=" AND FAIL_COUNT = '0' THEN '성공' WHEN SUCC_COUNT = '0'  ";
        query +=" AND FAIL_COUNT = '1' THEN '실패' ELSE '대기' END AS SEND_STATE  ";
        query +=" FROM KOMSMS.SDK_MMS_REPORT  ";
        query +=" WHERE RESERVED6 = 'SUPP'  ";
        query +=" AND RESERVED4 BETWEEN :FROM_YRMN  ";
        query +=" AND :TO_YRMN  ";
        if( !FRMB_CD.equals("") )
        {
            query +=" AND USER_ID >= :FRMB_CD  ";
        }
        if( !TOMB_CD.equals("") )
        {
            query +=" AND USER_ID <= :TOMB_CD  ";
        }
        sobj.setSql(query);
        if(!FRMB_CD.equals(""))
        {
            sobj.setString("FRMB_CD", FRMB_CD);               //회원코드
        }
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        if(!TOMB_CD.equals(""))
        {
            sobj.setString("TOMB_CD", TOMB_CD);               //회원코드
        }
        return sobj;
    }
    // 지급내역서 MMS 발송현황 조회
    public DOBJ CALLmst_send_chk_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmst_send_chk_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmst_send_chk_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(SEND_OK)  AS  SEND_OK  ,  SUM(SEND_NO)  AS  SEND_NO  ,  SUM(ERROR_OK)  AS  ERROR_OK  ,  SUM(ERROR_NO)  AS  ERROR_NO  ,  COUNT(1)  AS  SEND_COUNT  FROM   \n";
        query +=" (SELECT  DECODE(SEND_STATE,  '40',  1,  0)  AS  SEND_OK  ,  DECODE(SEND_STATE,  '05',  1,  0)  AS  SEND_NO  ,  DECODE(ERROR_CODE,  '00',  1,  0)  AS  ERROR_OK  ,  DECODE(ERROR_CODE,  '00',  0,  1)  AS  ERROR_NO  FROM  AMAIL.AUTO120  WHERE  SUBSTR(WORKDAY,1,6)  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN   \n";
        query +=" AND  SUBSTR(MAPPING,  INSTR(MAPPING,  '|')  +  1,  2)  =  '25') ";
        sobj.setSql(query);
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        return sobj;
    }
    // 지급내역서 MMS 발송현황 조회
    public DOBJ CALLmst_send_chk_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmst_send_chk_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmst_send_chk_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(SEND_OK)  AS  SEND_OK  ,  SUM(SEND_NO)  AS  SEND_NO  ,  SUM(SEND_WAIT)  AS  SEND_WAIT  ,  COUNT(1)  AS  SEND_COUNT  FROM   \n";
        query +=" (SELECT  DECODE(SUCC_COUNT,  '1',  1,  0)  AS  SEND_OK  ,  DECODE(FAIL_COUNT,  '1',  1,  0)  AS  SEND_NO  ,  CASE  WHEN  SUCC_COUNT=0   \n";
        query +=" AND  FAIL_COUNT=0  THEN  1  ELSE  0  END  AS  SEND_WAIT  FROM  KOMSMS.SDK_MMS_REPORT  WHERE  RESERVED6  =  'SUPP'   \n";
        query +=" AND  RESERVED4  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN  ) ";
        sobj.setSql(query);
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        return sobj;
    }
    //##**$$mst_send_chk
    //##**$$end
}
