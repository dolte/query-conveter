
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s14_1
{
    public bra01_s14_1()
    {
    }
    //##**$$select_offlog_info
    /*
    */
    public DOBJ CTLselect_offlog_info(DOBJ dobj)
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
            dobj  = CALLselect_offlog_info_SEL1(Conn, dobj);           //  영업정보 조회
            dobj  = CALLselect_offlog_info_SEL2(Conn, dobj);           //  로그기정보 조회
            dobj  = CALLselect_offlog_info_SEL7(Conn, dobj);           //  비고
            dobj  = CALLselect_offlog_info_SEL9(Conn, dobj);           //  설치 불가능 여부
            dobj  = CALLselect_offlog_info_SEL11(Conn, dobj);           //  사원코드
            dobj  = CALLselect_offlog_info_SEL12(Conn, dobj);           //  영업정보코드
            dobj  = CALLselect_offlog_info_SEL13(Conn, dobj);           //  로그기상태코드코드
            dobj  = CALLselect_offlog_info_SEL8(Conn, dobj);           //  대기중인 로그기 정보
            dobj  = CALLselect_offlog_info_SEL14(Conn, dobj);           //  유심정보 조회
            dobj  = CALLselect_offlog_info_SEL15(Conn, dobj);           //  업소목록
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
    public DOBJ CTLselect_offlog_info( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLselect_offlog_info_SEL1(Conn, dobj);           //  영업정보 조회
        dobj  = CALLselect_offlog_info_SEL2(Conn, dobj);           //  로그기정보 조회
        dobj  = CALLselect_offlog_info_SEL7(Conn, dobj);           //  비고
        dobj  = CALLselect_offlog_info_SEL9(Conn, dobj);           //  설치 불가능 여부
        dobj  = CALLselect_offlog_info_SEL11(Conn, dobj);           //  사원코드
        dobj  = CALLselect_offlog_info_SEL12(Conn, dobj);           //  영업정보코드
        dobj  = CALLselect_offlog_info_SEL13(Conn, dobj);           //  로그기상태코드코드
        dobj  = CALLselect_offlog_info_SEL8(Conn, dobj);           //  대기중인 로그기 정보
        dobj  = CALLselect_offlog_info_SEL14(Conn, dobj);           //  유심정보 조회
        dobj  = CALLselect_offlog_info_SEL15(Conn, dobj);           //  업소목록
        return dobj;
    }
    // 영업정보 조회
    public DOBJ CALLselect_offlog_info_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_info_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_info_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.CO_STATUS  ,  A.USER_NAME  AS  USER_ID  ,  FIDU.GET_STAFF_NM(A.USER_NAME)  AS  USER_NAME  ,  TO_CHAR(REG_DATE,  'YYYYMMDD')  AS  REG_DATE  FROM  LOG.KDS_SHOP_STATUSHISTORY  A  ,  LOG.KDS_SHOP  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD  ORDER  BY  SEQ  DESC ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 로그기정보 조회
    public DOBJ CALLselect_offlog_info_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_info_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_info_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.SEQ  ,  A.UPSO_CD  ,  A.ROOM_NAME  ,  A.SERIAL_NO  ,  A.CO_STATUS  ,  A.BSCON_CD  ,   \n";
        query +=" (SELECT  CO_PARING  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  SERIAL_NO  =  A.SERIAL_NO)  AS  CO_PARING  FROM  LOG.KDS_SHOPROOM  A  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.CO_STATUS  !=  '07005'  ORDER  BY  A.SEQ  DESC ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 비고
    public DOBJ CALLselect_offlog_info_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_info_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_info_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  COMMENTS  FROM  LOG.KDS_SHOP  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 설치 불가능 여부
    public DOBJ CALLselect_offlog_info_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_info_SEL9(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_info_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  COL_MCH_YN  FROM  GIBU.SDB_TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 사원코드
    public DOBJ CALLselect_offlog_info_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_info_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_info_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.STAFF_NUM  ,  B.HAN_NM  FROM  INSA.TCPM_DEPT  A  ,  INSA.TINS_MST01  B  WHERE  A.DEPT_CD  =  B.DEPT_CD   \n";
        query +=" AND  B.RETIRE_DAY  IS  NULL   \n";
        query +=" AND  A.GIBU  =   \n";
        query +=" (SELECT  BRAN_CD  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 영업정보코드
    public DOBJ CALLselect_offlog_info_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_info_SEL12(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_info_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CO_TYPE  ||  CO_CODE  AS  CO_CODE  ,  CO_NAME  FROM  LOG.KDS_CODE  WHERE  CO_TYPE  =  '07' ";
        sobj.setSql(query);
        return sobj;
    }
    // 로그기상태코드코드
    public DOBJ CALLselect_offlog_info_SEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL13");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_info_SEL13(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_info_SEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CO_TYPE  ||  CO_CODE  AS  CO_CODE  ,  CO_NAME  FROM  LOG.KDS_CODE  WHERE  CO_TYPE  =  '08' ";
        sobj.setSql(query);
        return sobj;
    }
    // 대기중인 로그기 정보
    // 대기상태의 전체 로그기 - 업소와 매칭된 로그기
    public DOBJ CALLselect_offlog_info_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_info_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_info_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SERIAL_NO  FROM  LOG.KDS_LOGCOLLECTOR  WHERE  CO_PARING  =  '08001'  MINUS   \n";
        query +=" SELECT  B.SERIAL_NO  FROM  LOG.KDS_SHOPROOM  A,  LOG.KDS_LOGCOLLECTOR  B  WHERE  A.SERIAL_NO  =  B.SERIAL_NO ";
        sobj.setSql(query);
        return sobj;
    }
    // 유심정보 조회
    // 가장최신의 유심정보를 제공한다
    public DOBJ CALLselect_offlog_info_SEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL14");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL14");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_info_SEL14(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL14");
        rvobj.Println("SEL14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_info_SEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SERIAL_NO = dobj.getRetObject("SEL2").firstRecord().get("SERIAL_NO");   //제품번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.SERIAL_NO  ,  A.TEL_NO  ,  A.MODEM_NO  ,  A.USIM_NO  ,  A.USIM_MODEL  ,  A.MODEM_KIND  ,  A.BIGO  FROM  LOG.KDS_USIM  A  ,  (   \n";
        query +=" SELECT  SERIAL_NO,  MAX(SEQ)  AS  SEQ  FROM  LOG.KDS_USIM  WHERE  SERIAL_NO  =  :SERIAL_NO  GROUP  BY  SERIAL_NO  )  B  WHERE  A.SERIAL_NO  =  B.SERIAL_NO   \n";
        query +=" AND  A.SEQ  =  B.SEQ ";
        sobj.setSql(query);
        sobj.setString("SERIAL_NO", SERIAL_NO);               //제품번호
        return sobj;
    }
    // 업소목록
    public DOBJ CALLselect_offlog_info_SEL15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL15");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL15");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_offlog_info_SEL15(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL15");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_offlog_info_SEL15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(A.INS_DATE,  'YYYY-MM-DD')  AS  INS_DATE  ,  A.UPSO_CD  ,  A.BRAN_CD  ,  AA.DEPT_NM  AS  BRAN_NM  ,  A.UPSO_NM  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,3)  AS  GRAD_CD  ,  AAA.GRADNM  AS  GRAD_NM  ,  A.MNGEMSTR_NM  ,  A.PERMMSTR_NM  ,   \n";
        query +=" (SELECT  --+  INDEX_DESC  (KDS_SHOPROOM  PK_KDS_SM_SEQ)  \n  SERIAL_NO  FROM  LOG.KDS_SHOPROOM  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  CO_STATUS  =  '07001'   \n";
        query +=" AND  ROWNUM  =  1)  AS  SERIAL_NO  ,   \n";
        query +=" (SELECT  --+  INDEX_DESC  (KDS_SHOPROOM  PK_KDS_SM_SEQ)  \n  BSCON_CD  FROM  LOG.KDS_SHOPROOM  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  CO_STATUS  =  '07001'   \n";
        query +=" AND  ROWNUM  =  1)  AS  BSCON_CD  ,  LOG.FT_GET_UPSO_STATUS_NM(A.UPSO_CD)  AS  CO_STATUS_NM  ,  GIBU.FT_GET_UPSO_HYUPAEUP(A.UPSO_CD)  AS  HYUPAEUP_GBN  ,  A.STAFF_CD  ,  DECODE(B.DSCT_START,  null,  null,  B.DSCT_START  ||'01')  AS  DSCT_START  ,  DECODE(B.DSCT_END,  null,  null,  B.DSCT_END  ||'01')  AS  DSCT_END  ,  B.DSCT_YN  ,   \n";
        query +=" (SELECT  CASE  WHEN(COUNT(*)  =  1)  THEN  '오프라인'  ELSE  ''  END  FROM  GIBU.TBRA_OFF_UPSO_MNG  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  ESTAB_YRMN  =  TO_CHAR(SYSDATE,  'YYYYMM')  )  AS  OFF_GBN  ,  TO_CHAR(B.MOD_DATE,  'YYYY-MM-DD')  AS  MOD_DATE  FROM  GIBU.TBRA_UPSO  A  ,  INSA.TCPM_DEPT  AA  ,  GIBU.TBRA_BSTYPGRAD  AAA  ,  LOG.KDS_SHOP  B  WHERE  A.BRAN_CD  =  AA.GIBU   \n";
        query +=" AND  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,3)  =  AAA.BSTYP_CD  ||  AAA.GRAD_GBN   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$select_offlog_info
    //##**$$end
}
