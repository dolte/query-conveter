
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra14_s02
{
    public bra14_s02()
    {
    }
    //##**$$search_auto_card_mng
    /*
    */
    public DOBJ CTLsearch_auto_card_mng(DOBJ dobj)
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
            dobj  = CALLsearch_auto_card_mng_SEL1(Conn, dobj);           //  신청서관리
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
    public DOBJ CTLsearch_auto_card_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_auto_card_mng_SEL1(Conn, dobj);           //  신청서관리
        return dobj;
    }
    // 신청서관리
    public DOBJ CALLsearch_auto_card_mng_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_auto_card_mng_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_auto_card_mng_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   CONFIRM_GBN = dobj.getRetObject("S").getRecord().get("CONFIRM_GBN");   //확인 구분
        String   APP_GBN = dobj.getRetObject("S").getRecord().get("APP_GBN");   //신청서구분
        String   START_DATE = dobj.getRetObject("S").getRecord().get("START_DATE");   //협회등록일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DATE = dobj.getRetObject("S").getRecord().get("END_DATE");   //마감 일시
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT TO_CHAR(A.INS_DATE,'yyyymmdd') INS_DATE, A.BRAN_CD, GIBU.GET_BRAN_NM(A.BRAN_CD) BRAN_NM, A.UPSO_CD, A.SEQ, B.UPSO_NM, DECODE(A.APP_GBN,0,'등록','해지') APP_GBN, A.CARD_CD, A.CARD_NUM, A.RESINUM, A.PAYPRES_NM, A.PAYPRES_PHON_NUM, A.APPTN_DAY, B.STAFF_CD, A.INSPRES_ID, C.SVR_FILE_NM, C.SVR_FILE_ROUT, C.FILE_NM, NVL(A.CONFIRM_DATE,'') CONFIRM_YN, A.CONFIRM_ID, (  ";
        query +=" SELECT RESULT_MSG  ";
        query +=" FROM (  ";
        query +=" SELECT PROC_DT||'-'||RESULT_MSG RESULT_MSG  ";
        query +=" FROM GIBU.FMS_CREDIT_REAL_CASE_IF_TBL X  ";
        query +=" WHERE X.MEMBER_ID = B.UPSO_CD  ";
        query +=" ORDER BY PROC_DT DESC, PROC_SEQ DESC )  ";
        query +=" WHERE ROWNUM=1 ) REMAK  ";
        query +=" FROM GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION A, GIBU.TBRA_UPSO B, GIBU.TBRA_UPSO_AUTO_CARD_DOC_ATTCH C  ";
        query +=" WHERE A.UPSO_CD=B.UPSO_CD  ";
        query +=" AND A.UPSO_CD = C.UPSO_CD(+)  ";
        query +=" AND A.SEQ = C.SEQ(+)  ";
        query +=" AND B.CLSBS_YRMN IS NULL  ";
        query +=" AND B.BRAN_CD = DECODE(:BRAN_CD, 'AL', B.BRAN_CD, :BRAN_CD)  ";
        if( !START_DATE.equals("")  && !END_DATE.equals("") )
        {
            query +=" AND TO_CHAR(A.INS_DATE, 'YYYYMMDD') BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        if( !STAFF_CD.equals("") )
        {
            query +=" AND B.STAFF_CD = :STAFF_CD  ";
        }
        if( !APP_GBN.equals("") )
        {
            query +=" AND A.APP_GBN = :APP_GBN  ";
        }
        if( !CONFIRM_GBN.equals("") )
        {
            query +=" AND NVL2(A.CONFIRM_DATE,'Y','N') = :CONFIRM_GBN  ";
        }
        query +=" ORDER BY B.BRAN_CD, A.UPSO_CD, A.SEQ, A.INS_DATE  ";
        sobj.setSql(query);
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        }
        if(!CONFIRM_GBN.equals(""))
        {
            sobj.setString("CONFIRM_GBN", CONFIRM_GBN);               //확인 구분
        }
        if(!APP_GBN.equals(""))
        {
            sobj.setString("APP_GBN", APP_GBN);               //신청서구분
        }
        if(!START_DATE.equals(""))
        {
            sobj.setString("START_DATE", START_DATE);               //협회등록일
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        if(!END_DATE.equals(""))
        {
            sobj.setString("END_DATE", END_DATE);               //마감 일시
        }
        return sobj;
    }
    //##**$$search_auto_card_mng
    //##**$$auto_card_conf_upd
    /*
    */
    public DOBJ CTLauto_card_conf_upd(DOBJ dobj)
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
            dobj  = CALLauto_card_conf_upd_MPD1(Conn, dobj);           //  분기용
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
    public DOBJ CTLauto_card_conf_upd( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_card_conf_upd_MPD1(Conn, dobj);           //  분기용
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기용
    public DOBJ CALLauto_card_conf_upd_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLauto_card_conf_upd_XIUD2(Conn, dobj);           //  확인업데이트
            }
        }
        return dobj;
    }
    // 확인업데이트
    public DOBJ CALLauto_card_conf_upd_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLauto_card_conf_upd_XIUD2(dobj, dvobj);
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
    private SQLObject SQLauto_card_conf_upd_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CONFIRM_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //계산 일자
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //비고
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //관리번호
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_AUTO_CARD_APPLICATION A  \n";
        query +=" SET A.CONFIRM_DATE = SYSDATE, A.CONFIRM_ID = :CONFIRM_ID, A.REMAK = :REMAK  \n";
        query +=" WHERE A.UPSO_CD = :UPSO_CD  \n";
        query +=" AND A.SEQ = :SEQ";
        sobj.setSql(query);
        sobj.setString("CONFIRM_ID", CONFIRM_ID);               //계산 일자
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$auto_card_conf_upd
    //##**$$end
}
