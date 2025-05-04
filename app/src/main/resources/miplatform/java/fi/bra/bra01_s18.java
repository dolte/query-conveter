
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s18
{
    public bra01_s18()
    {
    }
    //##**$$get_code
    /*
    */
    public DOBJ CTLget_code(DOBJ dobj)
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
            dobj  = CALLget_code_SEL2(Conn, dobj);           //  업종코드
            dobj  = CALLget_code_SEL3(Conn, dobj);           //  사원조회
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
    public DOBJ CTLget_code( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_code_SEL2(Conn, dobj);           //  업종코드
        dobj  = CALLget_code_SEL3(Conn, dobj);           //  사원조회
        return dobj;
    }
    // 업종코드
    public DOBJ CALLget_code_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_code_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_code_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  optimizer_features_enable('11.1.0.6')  */  TRIM(GRAD_GBN)  AS  GRADCD  ,  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z' ";
        sobj.setSql(query);
        return sobj;
    }
    // 사원조회
    public DOBJ CALLget_code_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_code_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_code_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.STAFF_NUM  STAFF_CD  ,  B.HAN_NM  STAFF_NM  FROM  INSA.TCPM_DEPT  A  ,  INSA.TINS_MST01  B  WHERE  A.DEPT_CD  =  B.DEPT_CD   \n";
        query +=" AND  B.RETIRE_DAY  IS  NULL ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$get_code
    //##**$$ky_prod_simple_search
    /*
    */
    public DOBJ CTLky_prod_simple_search(DOBJ dobj)
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
            dobj  = CALLky_prod_simple_search_SEL8(Conn, dobj);           //  정보확인
            if( dobj.getRetObject("SEL8").getRecord().getDouble("CNT") == 1)
            {
                dobj  = CALLky_prod_simple_search_SEL1(Conn, dobj);           //  업소간단조회
                dobj  = CALLky_prod_simple_search_MRG1( dobj);        //  업소간단조회
            }
            else
            {
                dobj  = CALLky_prod_simple_search_SEL2(Conn, dobj);           //  조회결과가없을경우
                dobj  = CALLky_prod_simple_search_MRG1( dobj);        //  업소간단조회
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
    public DOBJ CTLky_prod_simple_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLky_prod_simple_search_SEL8(Conn, dobj);           //  정보확인
        if( dobj.getRetObject("SEL8").getRecord().getDouble("CNT") == 1)
        {
            dobj  = CALLky_prod_simple_search_SEL1(Conn, dobj);           //  업소간단조회
            dobj  = CALLky_prod_simple_search_MRG1( dobj);        //  업소간단조회
        }
        else
        {
            dobj  = CALLky_prod_simple_search_SEL2(Conn, dobj);           //  조회결과가없을경우
            dobj  = CALLky_prod_simple_search_MRG1( dobj);        //  업소간단조회
        }
        return dobj;
    }
    // 정보확인
    public DOBJ CALLky_prod_simple_search_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLky_prod_simple_search_SEL8(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        rvobj.Println("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLky_prod_simple_search_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROD_CD = dobj.getRetObject("S").getRecord().get("PROD_CD");   //작품 코드
        String   PROD_NM = dobj.getRetObject("S").getRecord().get("PROD_NM");   //작품 명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  FIDU.TDIS_ACMCNRECTUNEINFO  WHERE  BSCON_CD  =  'E0006'   \n";
        query +=" AND  BSCON_PROD_CD  =  NVL(:PROD_CD,BSCON_PROD_CD)   \n";
        query +=" AND  PROD_NM  LIKE  '%'||:PROD_NM||'%' ";
        sobj.setSql(query);
        sobj.setString("PROD_CD", PROD_CD);               //작품 코드
        sobj.setString("PROD_NM", PROD_NM);               //작품 명
        return sobj;
    }
    // 업소간단조회
    // 업소코드나 명으로 검색시 해당 정보를 조회한다
    public DOBJ CALLky_prod_simple_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLky_prod_simple_search_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLky_prod_simple_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROD_CD = dobj.getRetObject("S").getRecord().get("PROD_CD");   //작품 코드
        String   PROD_NM = dobj.getRetObject("S").getRecord().get("PROD_NM");   //작품 명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BSCON_PROD_CD  AS  PROD_CD  ,  PROD_NM  FROM  FIDU.TDIS_ACMCNRECTUNEINFO  WHERE  BSCON_CD  =  'E0006'   \n";
        query +=" AND  BSCON_PROD_CD  =  NVL(:PROD_CD,BSCON_PROD_CD)   \n";
        query +=" AND  PROD_NM  LIKE  '%'||:PROD_NM||'%' ";
        sobj.setSql(query);
        sobj.setString("PROD_CD", PROD_CD);               //작품 코드
        sobj.setString("PROD_NM", PROD_NM);               //작품 명
        return sobj;
    }
    // 업소간단조회
    public DOBJ CALLky_prod_simple_search_MRG1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL2","");
        rvobj.setName("MRG1") ;
        rvobj.setRetcode(1);
        rvobj.Println("MRG1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 조회결과가없을경우
    public DOBJ CALLky_prod_simple_search_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLky_prod_simple_search_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLky_prod_simple_search_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  PROD_CD  ,  ''  PROD_NM  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$ky_prod_simple_search
    //##**$$ky_prod_popup_search
    /*
    */
    public DOBJ CTLky_prod_popup_search(DOBJ dobj)
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
            dobj  = CALLky_prod_popup_search_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLky_prod_popup_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLky_prod_popup_search_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLky_prod_popup_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLky_prod_popup_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLky_prod_popup_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PRDUCPRES_NM = dobj.getRetObject("S").getRecord().get("PRDUCPRES_NM");   //작곡자 명
        String   JAKSAPRES_NM = dobj.getRetObject("S").getRecord().get("JAKSAPRES_NM");   //작사자명
        String   PROD_CD = dobj.getRetObject("S").getRecord().get("PROD_CD");   //작품 코드
        String   SINA_NM = dobj.getRetObject("S").getRecord().get("SINA_NM");   //가수 명
        String   PROD_NM = dobj.getRetObject("S").getRecord().get("PROD_NM");   //작품 명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BSCON_CD , BSCON_PROD_CD AS PROD_CD , PROD_NM , SINA_NM , JAKSAPRES_NM , PRDUCPRES_NM , RM_YN  ";
        query +=" FROM FIDU.TDIS_ACMCNRECTUNEINFO  ";
        query +=" WHERE BSCON_CD = 'E0006'  ";
        query +=" AND BSCON_PROD_CD = NVL(:PROD_CD,BSCON_PROD_CD)  ";
        if( !PROD_NM.equals("") )
        {
            query +=" AND PROD_NM LIKE '%'||:PROD_NM||'%'  ";
        }
        if( !SINA_NM.equals("") )
        {
            query +=" AND SINA_NM LIKE '%'||:SINA_NM||'%'  ";
        }
        if( !JAKSAPRES_NM.equals("") )
        {
            query +=" AND JAKSAPRES_NM LIKE '%'||:JAKSAPRES_NM||'%'  ";
        }
        if( !PRDUCPRES_NM.equals("") )
        {
            query +=" AND PRDUCPRES_NM LIKE '%'||:PRDUCPRES_NM||'%'  ";
        }
        query +=" ORDER BY PROD_NM  ";
        sobj.setSql(query);
        if(!PRDUCPRES_NM.equals(""))
        {
            sobj.setString("PRDUCPRES_NM", PRDUCPRES_NM);               //작곡자 명
        }
        if(!JAKSAPRES_NM.equals(""))
        {
            sobj.setString("JAKSAPRES_NM", JAKSAPRES_NM);               //작사자명
        }
        sobj.setString("PROD_CD", PROD_CD);               //작품 코드
        if(!SINA_NM.equals(""))
        {
            sobj.setString("SINA_NM", SINA_NM);               //가수 명
        }
        if(!PROD_NM.equals(""))
        {
            sobj.setString("PROD_NM", PROD_NM);               //작품 명
        }
        return sobj;
    }
    //##**$$ky_prod_popup_search
    //##**$$search_prod_upso
    /*
    */
    public DOBJ CTLsearch_prod_upso(DOBJ dobj)
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
            dobj  = CALLsearch_prod_upso_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLsearch_prod_upso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_prod_upso_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLsearch_prod_upso_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_prod_upso_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_prod_upso_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROD_CD = dobj.getRetObject("S").getRecord().get("PROD_CD");   //작품 코드
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT X.SONG_ID , Y.PROD_NM , Y.SINA_NM , Y.JAKSAPRES_NM , Y.PRDUCPRES_NM , DECODE(Y.RM_YN, null, 'N', Y.RM_YN) AS RM_YN , (SELECT DECODE(COUNT(*), 0, 'N', 'Y')  ";
        query +=" FROM FIDU.TDIS_MEDYPRODINFO  ";
        query +=" WHERE MEDY_PROD_CD = Y.BSCON_PROD_CD  ";
        query +=" AND BSCON_CD = Y.BSCON_CD) AS MEDY_YN , X.CNT , X.BRAN_CD , X.UPSO_CD , (SELECT UPSO_NM  ";
        query +=" FROM GIBU.TBRA_UPSO  ";
        query +=" WHERE UPSO_CD = X.UPSO_CD) AS UPSO_NM , GIBU.FT_GET_BSTYP_INFO(X.UPSO_CD) AS BSTYP_CD , (SELECT STAFF_CD  ";
        query +=" FROM GIBU.TBRA_UPSO  ";
        query +=" WHERE UPSO_CD = X.UPSO_CD) AS STAFF_CD , X.BSCON_CD  ";
        query +=" FROM (  ";
        query +=" SELECT SONG_ID , UPSO_CD, BRAN_CD , COUNT(*) AS CNT , (SELECT --+ INDEX_DESC (KDS_SHOPROOM PK_KDS_SM_SEQ) \n BSCON_CD  ";
        query +=" FROM LOG.KDS_SHOPROOM  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND CO_STATUS = '07001'  ";
        query +=" AND ROWNUM = 1) AS BSCON_CD  ";
        query +=" FROM LOG.KDS_STATISTICS A  ";
        query +=" WHERE PLAY_SDATE BETWEEN TO_DATE(:START_YRMN||'000000', 'YYYYMMDDHH24MISS')  ";
        query +=" AND TO_DATE(:END_YRMN||'235959', 'YYYYMMDDHH24MISS')  ";
        query +=" AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        if( !PROD_CD.equals("") )
        {
            query +=" AND SONG_ID = :PROD_CD  ";
        }
        if( !UPSO_CD.equals("") )
        {
            query +=" AND UPSO_CD = :UPSO_CD  ";
        }
        query +=" GROUP BY SONG_ID, UPSO_CD, BRAN_CD ) X , (  ";
        query +=" SELECT BSCON_CD , BSCON_PROD_CD , PROD_NM , SINA_NM , JAKSAPRES_NM , PRDUCPRES_NM , RM_YN  ";
        query +=" FROM FIDU.TDIS_ACMCNRECTUNEINFO  ";
        query +=" WHERE 1=1  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD = :BSCON_CD  ";
        }
        if( !PROD_CD.equals("") )
        {
            query +=" AND BSCON_PROD_CD = :PROD_CD  ";
        }
        query +=" ) Y  ";
        query +=" WHERE X.SONG_ID = Y.BSCON_PROD_CD  ";
        query +=" AND X.BSCON_CD = Y.BSCON_CD  ";
        sobj.setSql(query);
        if(!PROD_CD.equals(""))
        {
            sobj.setString("PROD_CD", PROD_CD);               //작품 코드
        }
        if(!UPSO_CD.equals(""))
        {
            sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        }
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        }
        return sobj;
    }
    //##**$$search_prod_upso
    //##**$$end
}
