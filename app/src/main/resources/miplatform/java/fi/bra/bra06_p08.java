
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra06_p08
{
    public bra06_p08()
    {
    }
    //##**$$satn_num_del
    /*
    */
    public DOBJ CTLsatn_num_del(DOBJ dobj)
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
            dobj  = CALLsatn_num_del_XIUD1(Conn, dobj);           //  휴/폐업 결재번호 일괄 삭제
            dobj  = CALLsatn_num_del_OBJ7(Conn, dobj);           //  그룹웨어 연동데이터 삭제
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
    public DOBJ CTLsatn_num_del( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsatn_num_del_XIUD1(Conn, dobj);           //  휴/폐업 결재번호 일괄 삭제
        dobj  = CALLsatn_num_del_OBJ7(Conn, dobj);           //  그룹웨어 연동데이터 삭제
        return dobj;
    }
    // 휴/폐업 결재번호 일괄 삭제
    public DOBJ CALLsatn_num_del_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsatn_num_del_XIUD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsatn_num_del_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   LNK_KEY = dobj.getRetObject("S").getRecord().get("LNK_KEY");   //연동 키
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_VISIT  \n";
        query +=" SET SATN_NUM = ''  \n";
        query +=" WHERE SATN_NUM = :LNK_KEY";
        sobj.setSql(query);
        sobj.setString("LNK_KEY", LNK_KEY);               //연동 키
        return sobj;
    }
    // 그룹웨어 연동데이터 삭제
    public DOBJ CALLsatn_num_del_OBJ7(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OBJ7");
        CommUtil cu = new CommUtil(dobj);
        HashMap   classinfo = new HashMap();
        classinfo.put("OBJECTID","OBJ7");
        classinfo.put("PACKAGE","komca.ga.adm");
        classinfo.put("CLASS","adm01_r01");
        classinfo.put("METHOD","satn_num_del");
        classinfo.put("INMAP","S:S");
        classinfo.put("OUTOBJ","");
        classinfo.put("OUTOBJ1","");
        dobj = cu.callPSInternal(dobj, Conn, classinfo );
        dobj.getRetObject("OBJ7").Println("OBJ7");
        return dobj;
    }
    //##**$$satn_num_del
    //##**$$apprv_all_satn
    /*
    */
    public DOBJ CTLapprv_all_satn(DOBJ dobj)
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
            dobj  = CALLapprv_all_satn_SEL1(Conn, dobj);           //  일괄결재번호획득
            dobj  = CALLapprv_all_satn_MPD3(Conn, dobj);           //  분기
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
    public DOBJ CTLapprv_all_satn( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLapprv_all_satn_SEL1(Conn, dobj);           //  일괄결재번호획득
        dobj  = CALLapprv_all_satn_MPD3(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 일괄결재번호획득
    public DOBJ CALLapprv_all_satn_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLapprv_all_satn_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLapprv_all_satn_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("S").getRecord().get("INSPRES_ID");   //등록자 ID
        String   VISIT_DAY = dobj.getRetObject("S").getRecord().get("VISIT_DAY");   //방문 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :INSPRES_ID  ||  '-'  ||  :VISIT_DAY  ||  '-'  ||  LPAD(NVL(MAX(SUBSTR(SATN_NUM,  18,  3)),  0)  +  1,  3,  '0')  AS  SATN_NUM  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  INSPRES_ID  =  :INSPRES_ID   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY ";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        return sobj;
    }
    // 분기
    public DOBJ CALLapprv_all_satn_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLapprv_all_satn_XIUD1(Conn, dobj);           //  일괄결재번호생성
            }
        }
        return dobj;
    }
    // 일괄결재번호생성
    public DOBJ CALLapprv_all_satn_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLapprv_all_satn_XIUD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLapprv_all_satn_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SATN_NUM = dobj.getRetObject("SEL1").getRecord().get("SATN_NUM");   //결재번호
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("VISIT_DAY");   //방문 일자
        int   VISIT_SEQ = dobj.getRetObject("R").getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_VISIT  \n";
        query +=" SET SATN_NUM = :SATN_NUM  \n";
        query +=" WHERE VISIT_DAY = :VISIT_DAY  \n";
        query +=" AND VISIT_SEQ = :VISIT_SEQ  \n";
        query +=" AND UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("SATN_NUM", SATN_NUM);               //결재번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    //##**$$apprv_all_satn
    //##**$$sel_list_excel
    /*
    */
    public DOBJ CTLsel_list_excel(DOBJ dobj)
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
            dobj  = CALLsel_list_excel_SEL1(Conn, dobj);           //  엑셀출력용 조회
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
    public DOBJ CTLsel_list_excel( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_list_excel_SEL1(Conn, dobj);           //  엑셀출력용 조회
        return dobj;
    }
    // 엑셀출력용 조회
    public DOBJ CALLsel_list_excel_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_list_excel_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_list_excel_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT GIBU.GET_BRAN_NM(B.BRAN_CD) AS BRAN_NM , B.UPSO_CD , B.UPSO_NM , C.CODE_NM AS JOB_GBN , A.VISIT_DAY , SUBSTR(A.VISIT_TIME, 1, 2) || ':' || SUBSTR(A.VISIT_TIME, 3, 2) AS VISIT_TIME , FIDU.GET_STAFF_NM(B.STAFF_CD) AS STAFF_NM , A.REMAK  ";
        query +=" FROM GIBU.TBRA_UPSO_VISIT A , GIBU.TBRA_UPSO B , FIDU.TENV_CODE C  ";
        query +=" WHERE A.VISIT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.INSPRES_ID = B.STAFF_CD  ";
        query +=" AND A.JOB_GBN = C.CODE_CD  ";
        query +=" AND C.HIGH_CD = '00198'  ";
        if( !BRAN_CD.equals("") )
        {
            query +=" AND B.BRAN_CD = :BRAN_CD  ";
        }
        query +=" ORDER BY B.BRAN_CD, B.STAFF_CD, A.VISIT_DAY, A.VISIT_TIME, B.UPSO_CD  ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        if(!BRAN_CD.equals(""))
        {
            sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        }
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$sel_list_excel
    //##**$$bran_visit_list
    /* * 프로그램명 : bra06_p08
    * 작성자 : 서정재
    * 작성일 : 2009/12/02
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbran_visit_list(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("ORDER_GBN").equals("1") && dobj.getRetObject("S").getRecord().get("JOB_GBN").equals("VT"))
            {
                dobj  = CALLbran_visit_list_SEL11(Conn, dobj);           //  업소방문 리스트
                dobj  = CALLbran_visit_list_SEL21(Conn, dobj);           //  담당자별통계, 방문출장검색
                dobj  = CALLbran_visit_list_SEL1( dobj);        //  최종결과
                dobj  = CALLbran_visit_list_SEL2( dobj);        //  최종결과
            }
            else if( dobj.getRetObject("S").getRecord().get("ORDER_GBN").equals("1") && dobj.getRetObject("S").getRecord().get("JOB_GBN").equals("PE"))
            {
                dobj  = CALLbran_visit_list_SEL12(Conn, dobj);           //  업소방문 리스트
                dobj  = CALLbran_visit_list_SEL22(Conn, dobj);           //  담당자별통계, 전화기타검색
                dobj  = CALLbran_visit_list_SEL1( dobj);        //  최종결과
                dobj  = CALLbran_visit_list_SEL2( dobj);        //  최종결과
            }
            else if( dobj.getRetObject("S").getRecord().get("ORDER_GBN").equals("1"))
            {
                dobj  = CALLbran_visit_list_SEL13(Conn, dobj);           //  업소방문 리스트
                dobj  = CALLbran_visit_list_SEL23(Conn, dobj);           //  담당자별통계,검색조건 한가지
                dobj  = CALLbran_visit_list_SEL1( dobj);        //  최종결과
                dobj  = CALLbran_visit_list_SEL2( dobj);        //  최종결과
            }
            else if(!dobj.getRetObject("S").getRecord().get("ORDER_GBN").equals("1") && dobj.getRetObject("S").getRecord().get("JOB_GBN").equals("VT"))
            {
                dobj  = CALLbran_visit_list_SEL14(Conn, dobj);           //  업소방문 리스트
                dobj  = CALLbran_visit_list_SEL21(Conn, dobj);           //  담당자별통계, 방문출장검색
                dobj  = CALLbran_visit_list_SEL1( dobj);        //  최종결과
                dobj  = CALLbran_visit_list_SEL2( dobj);        //  최종결과
            }
            else if(!dobj.getRetObject("S").getRecord().get("ORDER_GBN").equals("1") && dobj.getRetObject("S").getRecord().get("JOB_GBN").equals("PE"))
            {
                dobj  = CALLbran_visit_list_SEL15(Conn, dobj);           //  업소방문 리스트
                dobj  = CALLbran_visit_list_SEL22(Conn, dobj);           //  담당자별통계, 전화기타검색
                dobj  = CALLbran_visit_list_SEL1( dobj);        //  최종결과
                dobj  = CALLbran_visit_list_SEL2( dobj);        //  최종결과
            }
            else if(!dobj.getRetObject("S").getRecord().get("ORDER_GBN").equals("1"))
            {
                dobj  = CALLbran_visit_list_SEL16(Conn, dobj);           //  업소방문 리스트
                dobj  = CALLbran_visit_list_SEL23(Conn, dobj);           //  담당자별통계,검색조건 한가지
                dobj  = CALLbran_visit_list_SEL1( dobj);        //  최종결과
                dobj  = CALLbran_visit_list_SEL2( dobj);        //  최종결과
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
    public DOBJ CTLbran_visit_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("ORDER_GBN").equals("1") && dobj.getRetObject("S").getRecord().get("JOB_GBN").equals("VT"))
        {
            dobj  = CALLbran_visit_list_SEL11(Conn, dobj);           //  업소방문 리스트
            dobj  = CALLbran_visit_list_SEL21(Conn, dobj);           //  담당자별통계, 방문출장검색
            dobj  = CALLbran_visit_list_SEL1( dobj);        //  최종결과
            dobj  = CALLbran_visit_list_SEL2( dobj);        //  최종결과
        }
        else if( dobj.getRetObject("S").getRecord().get("ORDER_GBN").equals("1") && dobj.getRetObject("S").getRecord().get("JOB_GBN").equals("PE"))
        {
            dobj  = CALLbran_visit_list_SEL12(Conn, dobj);           //  업소방문 리스트
            dobj  = CALLbran_visit_list_SEL22(Conn, dobj);           //  담당자별통계, 전화기타검색
            dobj  = CALLbran_visit_list_SEL1( dobj);        //  최종결과
            dobj  = CALLbran_visit_list_SEL2( dobj);        //  최종결과
        }
        else if( dobj.getRetObject("S").getRecord().get("ORDER_GBN").equals("1"))
        {
            dobj  = CALLbran_visit_list_SEL13(Conn, dobj);           //  업소방문 리스트
            dobj  = CALLbran_visit_list_SEL23(Conn, dobj);           //  담당자별통계,검색조건 한가지
            dobj  = CALLbran_visit_list_SEL1( dobj);        //  최종결과
            dobj  = CALLbran_visit_list_SEL2( dobj);        //  최종결과
        }
        else if(!dobj.getRetObject("S").getRecord().get("ORDER_GBN").equals("1") && dobj.getRetObject("S").getRecord().get("JOB_GBN").equals("VT"))
        {
            dobj  = CALLbran_visit_list_SEL14(Conn, dobj);           //  업소방문 리스트
            dobj  = CALLbran_visit_list_SEL21(Conn, dobj);           //  담당자별통계, 방문출장검색
            dobj  = CALLbran_visit_list_SEL1( dobj);        //  최종결과
            dobj  = CALLbran_visit_list_SEL2( dobj);        //  최종결과
        }
        else if(!dobj.getRetObject("S").getRecord().get("ORDER_GBN").equals("1") && dobj.getRetObject("S").getRecord().get("JOB_GBN").equals("PE"))
        {
            dobj  = CALLbran_visit_list_SEL15(Conn, dobj);           //  업소방문 리스트
            dobj  = CALLbran_visit_list_SEL22(Conn, dobj);           //  담당자별통계, 전화기타검색
            dobj  = CALLbran_visit_list_SEL1( dobj);        //  최종결과
            dobj  = CALLbran_visit_list_SEL2( dobj);        //  최종결과
        }
        else if(!dobj.getRetObject("S").getRecord().get("ORDER_GBN").equals("1"))
        {
            dobj  = CALLbran_visit_list_SEL16(Conn, dobj);           //  업소방문 리스트
            dobj  = CALLbran_visit_list_SEL23(Conn, dobj);           //  담당자별통계,검색조건 한가지
            dobj  = CALLbran_visit_list_SEL1( dobj);        //  최종결과
            dobj  = CALLbran_visit_list_SEL2( dobj);        //  최종결과
        }
        return dobj;
    }
    // 업소방문 리스트
    // 일자별정렬,방문출장검색
    public DOBJ CALLbran_visit_list_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_visit_list_SEL11(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_visit_list_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.VISIT_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ADDR  ,   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00198'   \n";
        query +=" AND  CODE_CD  =  A.JOB_GBN)  JOB_GBN  ,  A.REMAK  ,  B.STAFF_CD  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  STAFF_NM  ,  A.INSPRES_ID  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  INSPRES_NM  ,  DECODE(LENGTH(A.VISIT_TIME),4,  SUBSTR(A.VISIT_TIME,1,2)||':'||SUBSTR(A.VISIT_TIME,3,2),  '')  AS  VISIT_TIME  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.JOB_GBN  IN  ('V','T')  ORDER  BY  A.VISIT_DAY,  A.VISIT_TIME ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 담당자별통계, 방문출장검색
    public DOBJ CALLbran_visit_list_SEL21(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL21");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL21");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_visit_list_SEL21(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL21");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_visit_list_SEL21(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("S").getRecord().get("INSPRES_ID");   //등록자 ID
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.INSPRES_ID  AS  STAFF_CD  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  STAFF_NM  ,  COUNT(A.INSPRES_ID)  CNT  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  B.STAFF_CD  =  DECODE(:STAFF_CD,  NULL,  B.STAFF_CD,  :STAFF_CD)   \n";
        query +=" AND  (A.INSPRES_ID  =  :INSPRES_ID   \n";
        query +=" OR  :INSPRES_ID  IS  NULL)   \n";
        query +=" AND  A.JOB_GBN  IN  ('V','T')  GROUP  BY  A.INSPRES_ID ";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 최종결과
    public DOBJ CALLbran_visit_list_SEL1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("SEL1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL11, SEL12, SEL13, SEL14, SEL15, SEL16","");
        rvobj.setName("SEL1") ;
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 최종결과
    public DOBJ CALLbran_visit_list_SEL2(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("SEL2");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL21, SEL22, SEL23","");
        rvobj.setName("SEL2") ;
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 업소방문 리스트
    // 일자별정렬,방문출장검색
    public DOBJ CALLbran_visit_list_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_visit_list_SEL12(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_visit_list_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.VISIT_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ADDR  ,   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00198'   \n";
        query +=" AND  CODE_CD  =  A.JOB_GBN)  JOB_GBN  ,  A.REMAK  ,  B.STAFF_CD  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  STAFF_NM  ,  A.INSPRES_ID  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  INSPRES_NM  ,  DECODE(LENGTH(A.VISIT_TIME),4,  SUBSTR(A.VISIT_TIME,1,2)||':'||SUBSTR(A.VISIT_TIME,3,2),  '')  AS  VISIT_TIME  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.JOB_GBN  IN  ('P','E')  ORDER  BY  A.VISIT_DAY,  A.VISIT_TIME ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 담당자별통계, 전화기타검색
    public DOBJ CALLbran_visit_list_SEL22(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL22");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL22");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_visit_list_SEL22(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL22");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_visit_list_SEL22(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("S").getRecord().get("INSPRES_ID");   //등록자 ID
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.INSPRES_ID  AS  STAFF_CD  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  STAFF_NM  ,  COUNT(A.INSPRES_ID)  CNT  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  B.STAFF_CD  =  DECODE(:STAFF_CD,  NULL,  B.STAFF_CD,  :STAFF_CD)   \n";
        query +=" AND  (A.INSPRES_ID  =  :INSPRES_ID   \n";
        query +=" OR  :INSPRES_ID  IS  NULL)   \n";
        query +=" AND  A.JOB_GBN  IN  ('P','E')  GROUP  BY  A.INSPRES_ID ";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 업소방문 리스트
    // 일자별정렬,검색조건 한가지
    public DOBJ CALLbran_visit_list_SEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL13");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_visit_list_SEL13(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_visit_list_SEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   JOB_GBN = dobj.getRetObject("S").getRecord().get("JOB_GBN");   //업무 구분
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.VISIT_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ADDR  ,   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00198'   \n";
        query +=" AND  CODE_CD  =  A.JOB_GBN)  JOB_GBN  ,  A.REMAK  ,  B.STAFF_CD  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  STAFF_NM  ,  A.INSPRES_ID  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  INSPRES_NM  ,  DECODE(LENGTH(A.VISIT_TIME),4,  SUBSTR(A.VISIT_TIME,1,2)||':'||SUBSTR(A.VISIT_TIME,3,2),  '')  AS  VISIT_TIME  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.JOB_GBN  =  DECODE(:JOB_GBN,  'ALL',  A.JOB_GBN,  :JOB_GBN)   \n";
        query +=" AND  A.JOB_GBN  IN  ('V','P','T','E')  ORDER  BY  A.VISIT_DAY,  A.VISIT_TIME ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 담당자별통계,검색조건 한가지
    public DOBJ CALLbran_visit_list_SEL23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL23");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL23");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_visit_list_SEL23(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL23");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_visit_list_SEL23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("S").getRecord().get("INSPRES_ID");   //등록자 ID
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   JOB_GBN = dobj.getRetObject("S").getRecord().get("JOB_GBN");   //업무 구분
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.INSPRES_ID  AS  STAFF_CD  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  STAFF_NM  ,  COUNT(A.INSPRES_ID)  CNT  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  B.STAFF_CD  =  DECODE(:STAFF_CD,  NULL,  B.STAFF_CD,  :STAFF_CD)   \n";
        query +=" AND  (A.INSPRES_ID  =  :INSPRES_ID   \n";
        query +=" OR  :INSPRES_ID  IS  NULL)   \n";
        query +=" AND  A.JOB_GBN  =  DECODE(:JOB_GBN,  'ALL',  A.JOB_GBN,  :JOB_GBN)   \n";
        query +=" AND  A.JOB_GBN  IN  ('V','P','T','E')  GROUP  BY  A.INSPRES_ID ";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 업소방문 리스트
    // 담당자별정렬,방문출장검색
    public DOBJ CALLbran_visit_list_SEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL14");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL14");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_visit_list_SEL14(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_visit_list_SEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("S").getRecord().get("INSPRES_ID");   //등록자 ID
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.VISIT_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ADDR  ,   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00198'   \n";
        query +=" AND  CODE_CD  =  A.JOB_GBN)  JOB_GBN  ,  A.REMAK  ,  B.STAFF_CD  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  STAFF_NM  ,  A.INSPRES_ID  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  INSPRES_NM  ,  DECODE(LENGTH(A.VISIT_TIME),4,  SUBSTR(A.VISIT_TIME,1,2)||':'||SUBSTR(A.VISIT_TIME,3,2),  '')  AS  VISIT_TIME  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  B.STAFF_CD  =  DECODE(:STAFF_CD,  NULL,  B.STAFF_CD,  :STAFF_CD)   \n";
        query +=" AND  (A.INSPRES_ID  =  :INSPRES_ID   \n";
        query +=" OR  :INSPRES_ID  IS  NULL)   \n";
        query +=" AND  A.JOB_GBN  IN  ('V','T')  ORDER  BY  STAFF_NM,  A.VISIT_DAY,  A.VISIT_TIME ";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 업소방문 리스트
    // 담당자별정렬,방문출장검색
    public DOBJ CALLbran_visit_list_SEL15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL15");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL15");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_visit_list_SEL15(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL15");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_visit_list_SEL15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("S").getRecord().get("INSPRES_ID");   //등록자 ID
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.VISIT_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ADDR  ,   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00198'   \n";
        query +=" AND  CODE_CD  =  A.JOB_GBN)  JOB_GBN  ,  A.REMAK  ,  B.STAFF_CD  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  STAFF_NM  ,  A.INSPRES_ID  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  INSPRES_NM  ,  DECODE(LENGTH(A.VISIT_TIME),4,  SUBSTR(A.VISIT_TIME,1,2)||':'||SUBSTR(A.VISIT_TIME,3,2),  '')  AS  VISIT_TIME  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  B.STAFF_CD  =  DECODE(:STAFF_CD,  NULL,  B.STAFF_CD,  :STAFF_CD)   \n";
        query +=" AND  (A.INSPRES_ID  =  :INSPRES_ID   \n";
        query +=" OR  :INSPRES_ID  IS  NULL)   \n";
        query +=" AND  A.JOB_GBN  IN  ('P','E')  ORDER  BY  STAFF_NM,  A.VISIT_DAY,  A.VISIT_TIME ";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 업소방문 리스트
    // 담당자별정렬,검색조건 한가지
    public DOBJ CALLbran_visit_list_SEL16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL16");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL16");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_visit_list_SEL16(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL16");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_visit_list_SEL16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("S").getRecord().get("INSPRES_ID");   //등록자 ID
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   JOB_GBN = dobj.getRetObject("S").getRecord().get("JOB_GBN");   //업무 구분
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.VISIT_DAY  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ADDR  ,   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00198'   \n";
        query +=" AND  CODE_CD  =  A.JOB_GBN)  JOB_GBN  ,  A.REMAK  ,  B.STAFF_CD  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  STAFF_NM  ,  A.INSPRES_ID  ,  FIDU.GET_STAFF_NM(A.INSPRES_ID)  INSPRES_NM  ,  DECODE(LENGTH(A.VISIT_TIME),4,  SUBSTR(A.VISIT_TIME,1,2)||':'||SUBSTR(A.VISIT_TIME,3,2),  '')  AS  VISIT_TIME  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  B.STAFF_CD  =  DECODE(:STAFF_CD,  NULL,  B.STAFF_CD,  :STAFF_CD)   \n";
        query +=" AND  (A.INSPRES_ID  =  :INSPRES_ID   \n";
        query +=" OR  :INSPRES_ID  IS  NULL)   \n";
        query +=" AND  A.JOB_GBN  =  DECODE(:JOB_GBN,  'ALL',  A.JOB_GBN,  :JOB_GBN)   \n";
        query +=" AND  A.JOB_GBN  IN  ('V','P','T','E')  ORDER  BY  STAFF_NM,  A.VISIT_DAY,  A.VISIT_TIME ";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$bran_visit_list
    //##**$$sel_satn_list
    /*
    */
    public DOBJ CTLsel_satn_list(DOBJ dobj)
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
            dobj  = CALLsel_satn_list_SEL1(Conn, dobj);           //  결재리스트 조회
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
    public DOBJ CTLsel_satn_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_satn_list_SEL1(Conn, dobj);           //  결재리스트 조회
        return dobj;
    }
    // 결재리스트 조회
    public DOBJ CALLsel_satn_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_satn_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_satn_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("S").getRecord().get("INSPRES_ID");   //등록자 ID
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.VISIT_DAY  ,  A.VISIT_SEQ  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  B.UPSO_NEW_ADDR1  ||  DECODE  (B.UPSO_NEW_ADDR2,  '',  '',  ',  '  ||  B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  ADDR  ,   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00198'   \n";
        query +=" AND  CODE_CD  =  A.JOB_GBN)  AS  JOB_GBN  ,  A.REMAK  ,  B.STAFF_CD  ,  FIDU.GET_STAFF_NM  (B.STAFF_CD)  AS  STAFF_NM  ,  A.INSPRES_ID  ,  FIDU.GET_STAFF_NM  (A.INSPRES_ID)  AS  INSPRES_NM  ,  DECODE  (LENGTH  (A.VISIT_TIME),  4,  SUBSTR  (A.VISIT_TIME,  1,  2)  ||  ':'  ||  SUBSTR  (A.VISIT_TIME,  3,  2),  '')  AS  VISIT_TIME  ,  A.SATN_NUM  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  NVL  (:BRAN_CD,  B.BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.VISIT_DAY  =  :START_DAY   \n";
        query +=" AND  A.INSPRES_ID  =  :INSPRES_ID   \n";
        query +=" AND  A.JOB_GBN  IN  ('V',  'T')  ORDER  BY  A.VISIT_DAY,  A.VISIT_TIME ";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$sel_satn_list
    //##**$$regi_select
    /*
    */
    public DOBJ CTLregi_select(DOBJ dobj)
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
            dobj  = CALLregi_select_SEL1(Conn, dobj);           //  등록업소 조회
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
    public DOBJ CTLregi_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLregi_select_SEL1(Conn, dobj);           //  등록업소 조회
        return dobj;
    }
    // 등록업소 조회
    public DOBJ CALLregi_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLregi_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLregi_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT ROW_NUMBER() OVER(ORDER BY A.VISIT_DAY, B.STAFF_CD, A.VISIT_TIME) AS RRR, A.VISIT_DAY, SUBSTR(B.UPSO_NEW_ADDR1, 1, INSTR(B.UPSO_NEW_ADDR1, CHR(32), 1, 2) - 1) AS VISIT_LOC1, INSA.F_GET_STAFF_NM(B.STAFF_CD) AS STAFF_NM, A.UPSO_CD, GIBU.FT_GET_UPSO_NM(A.UPSO_CD) AS UPSO_NM, GIBU.FT_GET_BSTYP_NM(GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD)) AS BSTYP_NM, A.JOB_GBN, SUBSTR(A.VISIT_TIME, 1, 2) || ':' || SUBSTR(A.VISIT_TIME, 3, 2) AS VISIT_TIME, C.ARRR_MONTH AS YEON_MONTH, A.REMAK, A.CLOSED_YN, A.VISIT_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSO_VISIT A JOIN GIBU.TBRA_UPSO B ON A.UPSO_CD = B.UPSO_CD LEFT OUTER JOIN GIBU.MV_TBRA_UPSO_ARRR C ON A.UPSO_CD = C.UPSO_CD  ";
        query +=" WHERE A.JOB_GBN IN ('T', 'V')  ";
        query +=" AND :START_DAY <= A.VISIT_DAY  ";
        query +=" AND :END_DAY >= A.VISIT_DAY  ";
        query +=" AND B.STAFF_CD IN (SELECT BB.STAFF_NUM  ";
        query +=" FROM INSA.TCPM_DEPT AA JOIN INSA.TINS_MST01 BB ON AA.DEPT_CD = BB.DEPT_CD  ";
        query +=" WHERE AA.GIBU = DECODE(:BRAN_CD, 'AL', AA.GIBU, :BRAN_CD)  ";
        query +=" AND BB.RETIRE_DAY IS NULL)  ";
        if( !STAFF_CD.equals("") )
        {
            query +=" AND B.STAFF_CD LIKE :STAFF_CD  ";
        }
        if( !BSTYP_CD.equals("") )
        {
            query +=" AND ((:BSTYP_CD = 'YDN'  ";
            query +=" AND GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD) IN ('k', 'l', 'o'))  ";
            query +=" OR (:BSTYP_CD != 'YDN'  ";
            query +=" AND GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD) = :BSTYP_CD))  ";
        }
        query +=" ORDER BY A.VISIT_DAY, B.STAFF_CD, A.VISIT_TIME  ";
        sobj.setSql(query);
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        }
        sobj.setString("START_DAY", START_DAY);               //시작일
        if(!BSTYP_CD.equals(""))
        {
            sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$regi_select
    //##**$$stat_select
    /*
    */
    public DOBJ CTLstat_select(DOBJ dobj)
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
            dobj  = CALLstat_select_SEL1(Conn, dobj);           //  등록업소 조회
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
    public DOBJ CTLstat_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLstat_select_SEL1(Conn, dobj);           //  등록업소 조회
        return dobj;
    }
    // 등록업소 조회
    public DOBJ CALLstat_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstat_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstat_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ROW_NUMBER()  OVER(ORDER  BY  A.VISIT_LOC1)  AS  RRR,  INSA.F_GET_STAFF_NM(B.STAFF_CD)  AS  STAFF_NM,  A.VISIT_LOC1,  A.BSTYP_NM,  A.START_TIME,  A.END_TIME,  GIBU.FT_COUNT_VISIT_BY_DATE(A.STAFF_CD,  :START_DAY,  'V')  AS  VISIT_COUNT,  GIBU.FT_COUNT_VISIT_BY_DATE(A.STAFF_CD,  :START_DAY,  'T')  AS  TRIP_COUNT,  '0'  AS  TOTAL_COUNT,  B.NOT_VISIT_UPSO_COUNT,   \n";
        query +=" (SELECT  COUNT(DISTINCT  DD.VISIT_DAY)  FROM  GIBU.TBRA_UPSO_VISIT  DD  JOIN  GIBU.TBRA_UPSO  CC  ON  DD.UPSO_CD  =  CC.UPSO_CD  WHERE  CC.STAFF_CD  =  A.STAFF_CD   \n";
        query +=" AND  SUBSTR(VISIT_DAY,  1,  6)  =  SUBSTR(:START_DAY,  1,  6))  AS  MONTH_SUM  FROM   \n";
        query +=" (SELECT  BB.STAFF_CD,  STATS_MODE(SUBSTR(BB.UPSO_NEW_ADDR1,  1,  INSTR(BB.UPSO_NEW_ADDR1,  CHR(32),  1,  2)  -  1))  AS  VISIT_LOC1,  REGEXP_REPLACE(LISTAGG(GIBU.FT_GET_BSTYP_NM(GIBU.FT_GET_BSTYP_INFO(AA.UPSO_CD)),  ',')  WITHIN  GROUP(ORDER  BY  INSA.F_GET_STAFF_NM(BB.STAFF_CD)),  '([^,]+)(,'  ||  CHR(92)  ||'1)*(,|$)',  CHR(92)  ||  '1'  ||  CHR(92)  ||  '3')  AS  BSTYP_NM,  SUBSTR(MIN(AA.VISIT_TIME),  1,  2)  ||  ':'  ||  SUBSTR(MIN(AA.VISIT_TIME),  3,  2)  AS  START_TIME,  SUBSTR(MAX(AA.VISIT_TIME),  1,  2)  ||  ':'  ||  SUBSTR(MAX(AA.VISIT_TIME),  3,  2)  AS  END_TIME  FROM  GIBU.TBRA_UPSO_VISIT  AA  JOIN  GIBU.TBRA_UPSO  BB  ON  AA.UPSO_CD  =  BB.UPSO_CD  WHERE  AA.JOB_GBN  IN  ('T',  'V')   \n";
        query +=" AND  AA.VISIT_DAY  =  :START_DAY   \n";
        query +=" AND  BB.STAFF_CD  IN   \n";
        query +=" (SELECT  E.STAFF_NUM  FROM  INSA.TCPM_DEPT  D  JOIN  INSA.TINS_MST01  E  ON  D.DEPT_CD  =  E.DEPT_CD  WHERE  D.GIBU  =  DECODE(:BRAN_CD,  'AL',  D.GIBU,  :BRAN_CD)   \n";
        query +=" AND  E.RETIRE_DAY  IS  NULL)  GROUP  BY  BB.STAFF_CD)  A  JOIN  (   \n";
        query +=" SELECT  STAFF_CD,  COUNT(UPSO_CD)  NOT_VISIT_UPSO_COUNT  FROM  (   \n";
        query +=" SELECT  SIX_MONTH.UPSO_CD,  SIX_MONTH.STAFF_CD,  SIX_MONTH.ARRR_MONTH,  NOT_VISIT.LAST_VISIT_DAY  FROM  (   \n";
        query +=" SELECT  UPSO_CD,  STAFF_CD,  ARRR_MONTH  FROM  GIBU.MV_TBRA_UPSO_ARRR  WHERE  ARRR_MONTH  >=  6  )  SIX_MONTH  JOIN  (   \n";
        query +=" SELECT  UPSO_CD,  MAX(VISIT_DAY)  LAST_VISIT_DAY  FROM  GIBU.TBRA_UPSO_VISIT  GROUP  BY  UPSO_CD  HAVING  MONTHS_BETWEEN(TO_DATE(:START_DAY,  'YYYYMMDD'),  TO_DATE(MAX(VISIT_DAY),  'YYYYMMDD'))  >=  6  )  NOT_VISIT  ON  SIX_MONTH.UPSO_CD  =  NOT_VISIT.UPSO_CD  )  GROUP  BY  STAFF_CD  )  B  ON  A.STAFF_CD  =  B.STAFF_CD  ORDER  BY  A.VISIT_LOC1 ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$stat_select
    //##**$$not_regi_select
    /*
    */
    public DOBJ CTLnot_regi_select(DOBJ dobj)
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
            dobj  = CALLnot_regi_select_SEL1(Conn, dobj);           //  등록업소 조회
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
    public DOBJ CTLnot_regi_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLnot_regi_select_SEL1(Conn, dobj);           //  등록업소 조회
        return dobj;
    }
    // 등록업소 조회
    public DOBJ CALLnot_regi_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLnot_regi_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnot_regi_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT ROW_NUMBER() OVER(ORDER BY A.VISIT_DAY, A.STAFF_CD, A.VISIT_TIME) AS RRR, A.UPSO_SEQ, A.VISIT_DAY, A.STAFF_CD, A.UPSO_NM, A.BSTYP_CD, A.UPSO_ADDR, A.MNGEMSTR_NM, A.JOB_GBN, A.VISIT_TIME, A.CERT_YN, A.REMAK, A.CLOSE_YN  ";
        query +=" FROM GIBU.TBRA_UPSO_VISIT_NOT_REGI A  ";
        query +=" WHERE :START_DAY <= A.VISIT_DAY  ";
        query +=" AND :END_DAY >= A.VISIT_DAY  ";
        query +=" AND A.STAFF_CD IN (SELECT BB.STAFF_NUM  ";
        query +=" FROM INSA.TCPM_DEPT AA JOIN INSA.TINS_MST01 BB ON AA.DEPT_CD = BB.DEPT_CD  ";
        query +=" WHERE AA.GIBU = DECODE(:BRAN_CD, 'AL', AA.GIBU, :BRAN_CD)  ";
        query +=" AND BB.RETIRE_DAY IS NULL)  ";
        if( !STAFF_CD.equals("") )
        {
            query +=" AND A.STAFF_CD LIKE :STAFF_CD  ";
        }
        if( !BSTYP_CD.equals("") )
        {
            query +=" AND ((:BSTYP_CD = 'YDN'  ";
            query +=" AND A.BSTYP_CD IN ('k', 'l', 'o'))  ";
            query +=" OR (:BSTYP_CD != 'YDN'  ";
            query +=" AND A.BSTYP_CD = :BSTYP_CD))  ";
        }
        query +=" ORDER BY A.VISIT_DAY, A.STAFF_CD, A.VISIT_TIME  ";
        sobj.setSql(query);
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        }
        sobj.setString("START_DAY", START_DAY);               //시작일
        if(!BSTYP_CD.equals(""))
        {
            sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$not_regi_select
    //##**$$not_regi_save
    /*
    */
    public DOBJ CTLnot_regi_save(DOBJ dobj)
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
            dobj  = CALLnot_regi_save_MIUD2(Conn, dobj);           //  미등록업소 저장
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
    public DOBJ CTLnot_regi_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLnot_regi_save_MIUD2(Conn, dobj);           //  미등록업소 저장
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 미등록업소 저장
    public DOBJ CALLnot_regi_save_MIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLnot_regi_save_SEL23(Conn, dobj);           //  업소 순번 생성
                dobj  = CALLnot_regi_save_INS6(Conn, dobj);           //  저장
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLnot_regi_save_UPD8(Conn, dobj);           //  수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLnot_regi_save_DEL7(Conn, dobj);           //  삭제
            }
        }
        return dobj;
    }
    // 삭제
    public DOBJ CALLnot_regi_save_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLnot_regi_save_DEL7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnot_regi_save_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_SEQ = dvobj.getRecord().get("UPSO_SEQ");   //업소 순번
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_VISIT_NOT_REGI  \n";
        query +=" where STAFF_CD=:STAFF_CD  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and UPSO_SEQ=:UPSO_SEQ";
        sobj.setSql(query);
        sobj.setString("UPSO_SEQ", UPSO_SEQ);               //업소 순번
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        return sobj;
    }
    // 수정
    public DOBJ CALLnot_regi_save_UPD8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLnot_regi_save_UPD8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnot_regi_save_UPD8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   UPSO_NM = dvobj.getRecord().get("UPSO_NM");   //업소 명
        String   UPSO_ADDR = dvobj.getRecord().get("UPSO_ADDR");   //업소 주소
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   CLOSE_YN = dvobj.getRecord().get("CLOSE_YN");   //마감여부
        String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //경영주 이름
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //업종 코드
        String   UPSO_SEQ = dvobj.getRecord().get("UPSO_SEQ");   //업소 순번
        String   CERT_YN = dvobj.getRecord().get("CERT_YN");   //사업증 제출 여부
        String   VISIT_TIME = dvobj.getRecord().get("VISIT_TIME");   //방문 시간
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT_NOT_REGI  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , VISIT_TIME=:VISIT_TIME , CERT_YN=:CERT_YN , BSTYP_CD=:BSTYP_CD , MNGEMSTR_NM=:MNGEMSTR_NM , CLOSE_YN=:CLOSE_YN , JOB_GBN=:JOB_GBN , UPSO_ADDR=:UPSO_ADDR , MOD_DATE=SYSDATE , UPSO_NM=:UPSO_NM , REMAK=:REMAK  \n";
        query +=" where UPSO_SEQ=:UPSO_SEQ  \n";
        query +=" and STAFF_CD=:STAFF_CD  \n";
        query +=" and VISIT_DAY=:VISIT_DAY";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        sobj.setString("UPSO_ADDR", UPSO_ADDR);               //업소 주소
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("CLOSE_YN", CLOSE_YN);               //마감여부
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //경영주 이름
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("UPSO_SEQ", UPSO_SEQ);               //업소 순번
        sobj.setString("CERT_YN", CERT_YN);               //사업증 제출 여부
        sobj.setString("VISIT_TIME", VISIT_TIME);               //방문 시간
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 업소 순번 생성
    public DOBJ CALLnot_regi_save_SEL23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL23");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL23");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLnot_regi_save_SEL23(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL23");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnot_regi_save_SEL23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   VISIT_DAY = dobj.getRetObject("S").getRecord().get("VISIT_DAY");   //방문 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(UPSO_SEQ),0)  +  1  AS  UPSO_SEQ  FROM  GIBU.TBRA_UPSO_VISIT_NOT_REGI  WHERE  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  STAFF_CD  =  :STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        return sobj;
    }
    // 저장
    public DOBJ CALLnot_regi_save_INS6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLnot_regi_save_INS6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnot_regi_save_INS6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   UPSO_NM = dvobj.getRecord().get("UPSO_NM");   //업소 명
        String   UPSO_ADDR = dvobj.getRecord().get("UPSO_ADDR");   //업소 주소
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   CLOSE_YN = dvobj.getRecord().get("CLOSE_YN");   //마감여부
        String   MNGEMSTR_NM = dvobj.getRecord().get("MNGEMSTR_NM");   //경영주 이름
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //업종 코드
        String   CERT_YN = dvobj.getRecord().get("CERT_YN");   //사업증 제출 여부
        String   VISIT_TIME = dvobj.getRecord().get("VISIT_TIME");   //방문 시간
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   UPSO_SEQ = dobj.getRetObject("SEL23").getRecord().get("UPSO_SEQ");   //업소 순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_NOT_REGI (VISIT_TIME, INSPRES_ID, CERT_YN, UPSO_SEQ, BSTYP_CD, MNGEMSTR_NM, CLOSE_YN, INS_DATE, STAFF_CD, VISIT_DAY, JOB_GBN, UPSO_ADDR, UPSO_NM, REMAK)  \n";
        query +=" values(:VISIT_TIME , :INSPRES_ID , :CERT_YN , :UPSO_SEQ , :BSTYP_CD , :MNGEMSTR_NM , :CLOSE_YN , SYSDATE, :STAFF_CD , :VISIT_DAY , :JOB_GBN , :UPSO_ADDR , :UPSO_NM , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        sobj.setString("UPSO_ADDR", UPSO_ADDR);               //업소 주소
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("CLOSE_YN", CLOSE_YN);               //마감여부
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //경영주 이름
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("CERT_YN", CERT_YN);               //사업증 제출 여부
        sobj.setString("VISIT_TIME", VISIT_TIME);               //방문 시간
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("UPSO_SEQ", UPSO_SEQ);               //업소 순번
        return sobj;
    }
    //##**$$not_regi_save
    //##**$$bran_by_upso_select
    /*
    */
    public DOBJ CTLbran_by_upso_select(DOBJ dobj)
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
            dobj  = CALLbran_by_upso_select_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLbran_by_upso_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbran_by_upso_select_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLbran_by_upso_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_by_upso_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_by_upso_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD,  GIBU.FT_GET_BRAN_CD(UPSO_CD)  AS  BRAN_CD,  UPSO_NM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$bran_by_upso_select
    //##**$$sel_bstyp_grad
    /*
    */
    public DOBJ CTLsel_bstyp_grad(DOBJ dobj)
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
            dobj  = CALLsel_bstyp_grad_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLsel_bstyp_grad( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_bstyp_grad_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLsel_bstyp_grad_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_bstyp_grad_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bstyp_grad_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GRAD_GBN,  GRADNM,  MDM_CD  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z' ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$sel_bstyp_grad
    //##**$$closed_yn_save
    /*
    */
    public DOBJ CTLclosed_yn_save(DOBJ dobj)
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
            dobj  = CALLclosed_yn_save_MIUD1(Conn, dobj);           //  폐문 여부 저장
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
    public DOBJ CTLclosed_yn_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLclosed_yn_save_MIUD1(Conn, dobj);           //  폐문 여부 저장
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 폐문 여부 저장
    public DOBJ CALLclosed_yn_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLclosed_yn_save_UPD6(Conn, dobj);           //  수정
            }
        }
        return dobj;
    }
    // 수정
    public DOBJ CALLclosed_yn_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLclosed_yn_save_UPD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLclosed_yn_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLOSED_YN = dvobj.getRecord().get("CLOSED_YN");   //폐문 여부
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT  \n";
        query +=" set CLOSED_YN=:CLOSED_YN  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("CLOSED_YN", CLOSED_YN);               //폐문 여부
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    //##**$$closed_yn_save
    //##**$$end
}
