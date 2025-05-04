
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s16
{
    public bra01_s16()
    {
    }
    //##**$$upso_list_kylog
    /*
    */
    public DOBJ CTLupso_list_kylog(DOBJ dobj)
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
            dobj  = CALLupso_list_kylog_SEL1(Conn, dobj);           //  업소목록
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
    public DOBJ CTLupso_list_kylog( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_list_kylog_SEL1(Conn, dobj);           //  업소목록
        return dobj;
    }
    // 업소목록
    public DOBJ CALLupso_list_kylog_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_list_kylog_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_list_kylog_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   CO_STATUS = dobj.getRetObject("S").getRecord().get("CO_STATUS");   //운영상태
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(A.INS_DATE,  'YYYY-MM-DD')  AS  INS_DATE  ,  A.UPSO_CD  ,  A.BRAN_CD  ,  AA.DEPT_NM  AS  BRAN_NM  ,  A.UPSO_NM  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,3)  AS  GRAD_CD  ,  AAA.GRADNM  AS  GRAD_NM  ,  A.MNGEMSTR_NM  ,  A.PERMMSTR_NM  ,   \n";
        query +=" (SELECT  /*+  INDEX_DESC  (KDS_SHOPROOM  PK_KDS_SM_SEQ)  */  SERIAL_NO  FROM  LOG.KDS_SHOPROOM  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  CO_STATUS  =  '07001'   \n";
        query +=" AND  ROWNUM  =  1)  AS  SERIAL_NO  ,  LOG.FT_GET_UPSO_STATUS_NM(A.UPSO_CD)  AS  CO_STATUS_NM  ,  GIBU.FT_GET_UPSO_HYUPAEUP(A.UPSO_CD)  AS  HYUPAEUP_GBN  ,  A.STAFF_CD  ,  DECODE(B.DSCT_START,  null,  null,  B.DSCT_START  ||'01')  AS  DSCT_START  ,  DECODE(B.DSCT_END,  null,  null,  B.DSCT_END  ||'01')  AS  DSCT_END  ,  B.DSCT_YN  ,   \n";
        query +=" (SELECT  CASE  WHEN(COUNT(*)  =  1)  THEN  '오프라인'  ELSE  ''  END  FROM  GIBU.TBRA_OFF_UPSO_MNG  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  ESTAB_YRMN  =  TO_CHAR(SYSDATE,  'YYYYMM')  )  AS  OFF_GBN  FROM  GIBU.TBRA_UPSO  A  ,  INSA.TCPM_DEPT  AA  ,  GIBU.TBRA_BSTYPGRAD  AAA  ,  LOG.KDS_SHOP  B  WHERE  A.BRAN_CD  =  AA.GIBU   \n";
        query +=" AND  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,3)  =  AAA.BSTYP_CD  ||  AAA.GRAD_GBN   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.STAFF_CD  =  DECODE(:STAFF_CD,  NULL,  A.STAFF_CD,  :STAFF_CD)   \n";
        query +=" AND  LOG.FT_GET_UPSO_STATUS_NM(A.UPSO_CD)  =  DECODE(:CO_STATUS,  '전체',  LOG.FT_GET_UPSO_STATUS_NM(A.UPSO_CD),  :CO_STATUS) ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("CO_STATUS", CO_STATUS);               //운영상태
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$upso_list_kylog
    //##**$$upso_save_kylog
    /*
    */
    public DOBJ CTLupso_save_kylog(DOBJ dobj)
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
            dobj  = CALLupso_save_kylog_MIUD1(Conn, dobj);           //  루핑
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
    public DOBJ CTLupso_save_kylog( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_save_kylog_MIUD1(Conn, dobj);           //  루핑
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 루핑
    public DOBJ CALLupso_save_kylog_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_save_kylog_XIUD7(Conn, dobj);           //  수정
            }
        }
        return dobj;
    }
    // 수정
    public DOBJ CALLupso_save_kylog_XIUD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD7");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_save_kylog_XIUD7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_save_kylog_XIUD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DSCT_END = dobj.getRetObject("R").getRecord().get("DSCT_END");   //할인종료월
        String   DSCT_START = dobj.getRetObject("R").getRecord().get("DSCT_START");   //할인시작월
        String   DSCT_YN = dobj.getRetObject("R").getRecord().get("DSCT_YN");   //할인적용여부
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE LOG.KDS_SHOP  \n";
        query +=" SET DSCT_START = SUBSTR(:DSCT_START, 1,6) , DSCT_END = SUBSTR(:DSCT_END, 1,6) , DSCT_YN = :DSCT_YN  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("DSCT_END", DSCT_END);               //할인종료월
        sobj.setString("DSCT_START", DSCT_START);               //할인시작월
        sobj.setString("DSCT_YN", DSCT_YN);               //할인적용여부
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$upso_save_kylog
    //##**$$select_co_status
    /*
    */
    public DOBJ CTLselect_co_status(DOBJ dobj)
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
            dobj  = CALLselect_co_status_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLselect_co_status( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLselect_co_status_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLselect_co_status_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_co_status_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_co_status_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CO_CODE  ,  CO_NAME  FROM  (   \n";
        query +=" SELECT  '000'  AS  CO_CODE  ,  '전체'  AS  CO_NAME  FROM  DUAL  UNION   \n";
        query +=" SELECT  CO_CODE,  CO_NAME  FROM  LOG.KDS_CODE  WHERE  CO_TYPE=  '07'  )  ORDER  BY  CO_CODE ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$select_co_status
    //##**$$end
}
