
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac03_s06
{
    public tac03_s06()
    {
    }
    //##**$$ttac_etcdedct_u_insert
    /*
    */
    public DOBJ CTLttac_etcdedct_u_insert(DOBJ dobj)
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
            dobj  = CALLttac_etcdedct_u_insert_XIUD1(Conn, dobj);           //  상호관리단체분 기타공제로 처
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
    public DOBJ CTLttac_etcdedct_u_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttac_etcdedct_u_insert_XIUD1(Conn, dobj);           //  상호관리단체분 기타공제로 처
        return dobj;
    }
    // 상호관리단체분 기타공제로 처
    public DOBJ CALLttac_etcdedct_u_insert_XIUD1(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLttac_etcdedct_u_insert_XIUD1(dobj, dvobj);
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
    private SQLObject SQLttac_etcdedct_u_insert_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO FIDU.TTAC_ETCDEDCT SELECT A.SUPP_YRMN, A.MB_CD, A.TRNSF_GBN, '3' , TOT_REALSUPP_AMT, '외국단체외화송금', '' , sysdate,'','' FROM FIDU.TTAC_COPYRTAL A , FIDU.TMEM_MB B WHERE SUPP_YRMN =:SUPP_YRMN AND A.MB_CD = B.MB_CD AND B.MB_GBN1 ='U'";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$ttac_etcdedct_u_insert
    //##**$$etcdedct_get_realsupp
    /*
    */
    public DOBJ CTLetcdedct_get_realsupp(DOBJ dobj)
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
            dobj  = CALLetcdedct_get_realsupp_SEL1(Conn, dobj);           //  실지급액 조회
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
    public DOBJ CTLetcdedct_get_realsupp( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLetcdedct_get_realsupp_SEL1(Conn, dobj);           //  실지급액 조회
        return dobj;
    }
    // 실지급액 조회
    public DOBJ CALLetcdedct_get_realsupp_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLetcdedct_get_realsupp_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLetcdedct_get_realsupp_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   DEDCT_YRMN = dobj.getRetObject("S").getRecord().get("DEDCT_YRMN");   //공제 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(NVL(TOT_REALSUPP_AMT,0))  AS  TOT_REALSUPP_AMT  FROM  FIDU.TTAC_COPYRTAL  WHERE  SUPP_YRMN  =  :DEDCT_YRMN   \n";
        query +=" AND  MB_CD  =  :MB_CD   \n";
        query +=" AND  TRNSF_GBN  =  :TRNSF_GBN ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("DEDCT_YRMN", DEDCT_YRMN);               //공제 년월
        return sobj;
    }
    //##**$$etcdedct_get_realsupp
    //##**$$saveMemo
    /* * 프로그램명 : tac03_s06
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsaveMemo(DOBJ dobj)
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
            dobj  = CALLsaveMemo_DEL1(Conn, dobj);           //  공지사항삭제
            dobj  = CALLsaveMemo_INS2(Conn, dobj);           //  공지사항저장
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
    public DOBJ CTLsaveMemo( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsaveMemo_DEL1(Conn, dobj);           //  공지사항삭제
        dobj  = CALLsaveMemo_INS2(Conn, dobj);           //  공지사항저장
        return dobj;
    }
    // 공지사항삭제
    public DOBJ CALLsaveMemo_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveMemo_DEL1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveMemo_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_MEMO  \n";
        query +=" where SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 공지사항저장
    public DOBJ CALLsaveMemo_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveMemo_INS2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveMemo_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //등록자 ID
        String INS_DATE ="";        //등록 일시
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_MEMO (REMAK, SUPP_YRMN)  \n";
        query +=" values(:REMAK , :SUPP_YRMN )";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("REMAK", REMAK);               //비고
        return sobj;
    }
    //##**$$saveMemo
    //##**$$ttac_etcdedct_select
    /* * 프로그램명 : tac03_s06
    * 작성자 : 손주환
    * 작성일 : 2009/11/23
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLttac_etcdedct_select(DOBJ dobj)
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
            dobj  = CALLttac_etcdedct_select_SEL1(Conn, dobj);           //  기타공제내역관리
            dobj  = CALLttac_etcdedct_select_SEL2(Conn, dobj);           //  공지사항조회
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
    public DOBJ CTLttac_etcdedct_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttac_etcdedct_select_SEL1(Conn, dobj);           //  기타공제내역관리
        dobj  = CALLttac_etcdedct_select_SEL2(Conn, dobj);           //  공지사항조회
        return dobj;
    }
    // 기타공제내역관리
    // 기타공제내역관리
    public DOBJ CALLttac_etcdedct_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLttac_etcdedct_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_etcdedct_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   DEDCT_YRMN1 = dobj.getRetObject("S").getRecord().get("DEDCT_YRMN1");   //DEDCT_YRMN1
        String   DEDCT_YRMN = dobj.getRetObject("S").getRecord().get("DEDCT_YRMN");   //공제 년월
        String   DEDCTINS_GBN = dobj.getRetObject("S").getRecord().get("DEDCTINS_GBN");   //공제등록 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEDCT_YRMN,  MB_CD,  DEDCTINS_GBN,  TRNSF_GBN,  FIDU.GET_MB_NM(A.MB_CD)  AS  MB_NM,  FIDU.GET_CODE_NM('00228',  A.DEDCTINS_GBN)  AS  DEDCTINS_NM,  OCC_AMT,  REMAK_CTENT,   \n";
        query +=" (SELECT  MAX(NVL(TOT_REALSUPP_AMT,0))  FROM  FIDU.TTAC_COPYRTAL  WHERE  SUPP_YRMN  =  A.DEDCT_YRMN   \n";
        query +=" AND  MB_CD  =  A.MB_CD   \n";
        query +=" AND  TRNSF_GBN  =  A.TRNSF_GBN  )  AS  TOT_REALSUPP_AMT  FROM  FIDU.TTAC_ETCDEDCT  A  WHERE  1=1   \n";
        query +=" AND  MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  DEDCT_YRMN  >=  :DEDCT_YRMN   \n";
        query +=" AND  DEDCT_YRMN  <=  :DEDCT_YRMN1   \n";
        query +=" AND  DEDCTINS_GBN  LIKE  :DEDCTINS_GBN  ||  '%' ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("DEDCT_YRMN1", DEDCT_YRMN1);               //DEDCT_YRMN1
        sobj.setString("DEDCT_YRMN", DEDCT_YRMN);               //공제 년월
        sobj.setString("DEDCTINS_GBN", DEDCTINS_GBN);               //공제등록 구분
        return sobj;
    }
    // 공지사항조회
    public DOBJ CALLttac_etcdedct_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLttac_etcdedct_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_etcdedct_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   DEDCT_YRMN = dobj.getRetObject("S").getRecord().get("DEDCT_YRMN");   //공제 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUPP_YRMN,  REMAK,  B.MB_GBN1  FROM  FIDU.TTAC_MEMO  A,  FIDU.TMEM_MB  B  WHERE  SUPP_YRMN  =:DEDCT_YRMN   \n";
        query +=" AND  B.MB_CD  =:MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("DEDCT_YRMN", DEDCT_YRMN);               //공제 년월
        return sobj;
    }
    //##**$$ttac_etcdedct_select
    //##**$$ttac_etcdedct_save
    /* * 프로그램명 : tac03_s06
    * 작성자 : 하근철
    * 작성일 : 2009/08/13
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLttac_etcdedct_save(DOBJ dobj)
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
            dobj  = CALLttac_etcdedct_save_MIUD1(Conn, dobj);           //  기타공제MUID
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
    public DOBJ CTLttac_etcdedct_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLttac_etcdedct_save_MIUD1(Conn, dobj);           //  기타공제MUID
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 기타공제MUID
    public DOBJ CALLttac_etcdedct_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLttac_etcdedct_save_INS8(Conn, dobj);           //  기타공제저장
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLttac_etcdedct_save_UPD6(Conn, dobj);           //  기타공제수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLttac_etcdedct_save_DEL7(Conn, dobj);           //  기타공제삭제
            }
        }
        return dobj;
    }
    // 기타공제삭제
    public DOBJ CALLttac_etcdedct_save_DEL7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLttac_etcdedct_save_DEL7(dobj, dvobj);
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
    private SQLObject SQLttac_etcdedct_save_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEDCT_YRMN = dvobj.getRecord().get("DEDCT_YRMN");   //공제 년월
        String   DEDCTINS_GBN = dvobj.getRecord().get("DEDCTINS_GBN");   //공제등록 구분
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_ETCDEDCT  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN  \n";
        query +=" and DEDCTINS_GBN=:DEDCTINS_GBN  \n";
        query +=" and DEDCT_YRMN=:DEDCT_YRMN";
        sobj.setSql(query);
        sobj.setString("DEDCT_YRMN", DEDCT_YRMN);               //공제 년월
        sobj.setString("DEDCTINS_GBN", DEDCTINS_GBN);               //공제등록 구분
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    // 기타공제저장
    public DOBJ CALLttac_etcdedct_save_INS8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLttac_etcdedct_save_INS8(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLttac_etcdedct_save_INS8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String REMAK_CTENT ="";        //비고 내용
        String   DEDCT_YRMN = dvobj.getRecord().get("DEDCT_YRMN");   //공제 년월
        String   DEDCTINS_GBN = dvobj.getRecord().get("DEDCTINS_GBN");   //공제등록 구분
        String   REMAK_CTENT_1 = dobj.getRetObject("R").getRecord().get("REMAK_CTENT");   //비고 내용
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        double   OCC_AMT = dvobj.getRecord().getDouble("OCC_AMT");   //발생 금액
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_ETCDEDCT (MB_CD, INS_DATE, INSPRES_ID, OCC_AMT, TRNSF_GBN, REMAK_CTENT, DEDCTINS_GBN, DEDCT_YRMN)  \n";
        query +=" values(:MB_CD , SYSDATE, :INSPRES_ID , :OCC_AMT , :TRNSF_GBN , REPLACE(REPLACE(:REMAK_CTENT_1, CHR(10), ''), CHR(13), ''), :DEDCTINS_GBN , :DEDCT_YRMN )";
        sobj.setSql(query);
        sobj.setString("DEDCT_YRMN", DEDCT_YRMN);               //공제 년월
        sobj.setString("DEDCTINS_GBN", DEDCTINS_GBN);               //공제등록 구분
        sobj.setString("REMAK_CTENT_1", REMAK_CTENT_1);               //비고 내용
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setDouble("OCC_AMT", OCC_AMT);               //발생 금액
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    // 기타공제수정
    public DOBJ CALLttac_etcdedct_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLttac_etcdedct_save_UPD6(dobj, dvobj);
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
    private SQLObject SQLttac_etcdedct_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MODI_DATE ="";        //수정일시
        String REMAK_CTENT ="";        //비고 내용
        String   MOD_DATE = dvobj.getRecord().get("MOD_DATE");   //수정 일시
        String   DEDCT_YRMN = dvobj.getRecord().get("DEDCT_YRMN");   //공제 년월
        String   DEDCTINS_GBN = dvobj.getRecord().get("DEDCTINS_GBN");   //공제등록 구분
        String   REMAK_CTENT_1 = dobj.getRetObject("R").getRecord().get("REMAK_CTENT");   //비고 내용
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        double   OCC_AMT = dvobj.getRecord().getDouble("OCC_AMT");   //발생 금액
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   INS_DATE = dvobj.getRecord().get("INS_DATE");   //등록 일시
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_ETCDEDCT  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , INS_DATE=:INS_DATE , INSPRES_ID=:INSPRES_ID , OCC_AMT=:OCC_AMT , REMAK_CTENT=REPLACE(REPLACE(:REMAK_CTENT_1, CHR(10), ''), CHR(13), '') , MOD_DATE=:MOD_DATE  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN  \n";
        query +=" and DEDCTINS_GBN=:DEDCTINS_GBN  \n";
        query +=" and DEDCT_YRMN=:DEDCT_YRMN";
        sobj.setSql(query);
        sobj.setString("MOD_DATE", MOD_DATE);               //수정 일시
        sobj.setString("DEDCT_YRMN", DEDCT_YRMN);               //공제 년월
        sobj.setString("DEDCTINS_GBN", DEDCTINS_GBN);               //공제등록 구분
        sobj.setString("REMAK_CTENT_1", REMAK_CTENT_1);               //비고 내용
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setDouble("OCC_AMT", OCC_AMT);               //발생 금액
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("INS_DATE", INS_DATE);               //등록 일시
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    //##**$$ttac_etcdedct_save
    //##**$$end
}
