
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s22_4
{
    public bra01_s22_4()
    {
    }
    //##**$$get_log_data2
    /*
    */
    public DOBJ CTLget_log_data2(DOBJ dobj)
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
            dobj  = CALLget_log_data2_SEL3(Conn, dobj);           //  청구년월 조회
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("01"))
            {
                dobj  = CALLget_log_data2_SEL7(Conn, dobj);           //  관리업소 조회
                dobj  = CALLget_log_data2_MRG9( dobj);        //  머지
                dobj  = CALLget_log_data2_SEL4(Conn, dobj);           //  지부코드 보내기
            }
            else if( dobj.getRetObject("S").getRecord().get("UPSO_TYPE").equals("02"))
            {
                dobj  = CALLget_log_data2_SEL8(Conn, dobj);           //  개발중업소 조회
                dobj  = CALLget_log_data2_MRG9( dobj);        //  머지
                dobj  = CALLget_log_data2_SEL4(Conn, dobj);           //  지부코드 보내기
            }
            else
            {
                dobj  = CALLget_log_data2_SEL2(Conn, dobj);           //  전체업소조회
                dobj  = CALLget_log_data2_MRG9( dobj);        //  머지
                dobj  = CALLget_log_data2_SEL4(Conn, dobj);           //  지부코드 보내기
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
    public DOBJ CTLget_log_data2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_log_data2_SEL3(Conn, dobj);           //  청구년월 조회
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("01"))
        {
            dobj  = CALLget_log_data2_SEL7(Conn, dobj);           //  관리업소 조회
            dobj  = CALLget_log_data2_MRG9( dobj);        //  머지
            dobj  = CALLget_log_data2_SEL4(Conn, dobj);           //  지부코드 보내기
        }
        else if( dobj.getRetObject("S").getRecord().get("UPSO_TYPE").equals("02"))
        {
            dobj  = CALLget_log_data2_SEL8(Conn, dobj);           //  개발중업소 조회
            dobj  = CALLget_log_data2_MRG9( dobj);        //  머지
            dobj  = CALLget_log_data2_SEL4(Conn, dobj);           //  지부코드 보내기
        }
        else
        {
            dobj  = CALLget_log_data2_SEL2(Conn, dobj);           //  전체업소조회
            dobj  = CALLget_log_data2_MRG9( dobj);        //  머지
            dobj  = CALLget_log_data2_SEL4(Conn, dobj);           //  지부코드 보내기
        }
        return dobj;
    }
    // 청구년월 조회
    public DOBJ CALLget_log_data2_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_log_data2_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_log_data2_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(SYSDATE,  'YYYYMM')  AS  DEMD_YRMN  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 관리업소 조회
    public DOBJ CALLget_log_data2_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_log_data2_SEL7(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        rvobj.Println("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_log_data2_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   DEMD_YRMN = dobj.getRetObject("SEL3").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT DEMD_MM_CNT , BRAN_CD , UPSO_CD , UPSO_NM , MNGEMSTR_NM , BSTYPGRAD_NM , UPSO_ADDR , UPSO_PHON , MNGEMSTR_HPNUM , MCHNDAESU , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 0) AS KY_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 1) AS TJ_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 2) AS ETC_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 3) AS KY_F , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 4) AS TJ_F , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 5) AS ETC_F , ONOFF_DATA_GBN , STAFF_NM , TO_CHAR(LAST_MOD_DATE, 'YYYY-MM-DD') AS LAST_MOD_DATE , COL_MCH_YN  ";
        query +=" FROM (  ";
        query +=" SELECT GIBU.FT_GET_UPSO_MMCNT(A.UPSO_CD, :DEMD_YRMN) AS DEMD_MM_CNT , BRAN_CD , UPSO_CD , UPSO_NM , MNGEMSTR_NM , GIBU.FT_GET_BSTYPGRAD_NM(A.UPSO_CD, '') AS BSTYPGRAD_NM , A.UPSO_NEW_ADDR1 || DECODE(A.UPSO_NEW_ADDR2, '', '', ', '||A.UPSO_NEW_ADDR2) || A.UPSO_REF_INFO UPSO_ADDR , A.UPSO_PHON , A.MNGEMSTR_HPNUM , A.MCHNDAESU , GIBU.FT_GET_UPSO_ONOFF_BSCON(A.UPSO_CD) AS ONOFF_BSCON , GIBU.FT_GET_ONOFF_DATA_GBN(A.UPSO_CD) AS ONOFF_DATA_GBN , FIDU.GET_STAFF_NM (STAFF_CD) AS STAFF_NM , (SELECT MAX(NVL(INS_DATE, MOD_DATE))  ";
        query +=" FROM GIBU.TBRA_UPSO_ONOFF_INFO  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD ) AS LAST_MOD_DATE , A.COL_MCH_YN , GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD) AS BSTYP_CD  ";
        query +=" FROM GIBU.SDB_TBRA_UPSO A  ";
        query +=" WHERE A.UPSO_STAT = '1'  ";
        query +=" AND (A.CLSBS_YRMN IS NULL  ";
        query +=" OR NVL(SUBSTR(A.CLSBS_INS_DAY,1,6), ' ') > :DEMD_YRMN)  ";
        query +=" AND A.NEW_DAY IS NOT NULL  ";
        query +=" AND A.NEW_DAY <= :DEMD_YRMN || '31'  ";
        query +=" AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" AND STAFF_CD = NVL(:STAFF_CD, STAFF_CD) )  ";
        query +=" WHERE (BSTYP_CD='k'  ";
        query +=" OR BSTYP_CD='l'  ";
        query +=" OR BSTYP_CD='o'  ";
        query +=" OR BSTYP_CD='n'  ";
        query +=" OR BSTYP_CD='y')  ";
        if( BSTYP_CD.equals("klo"))
        {
            query +=" AND (BSTYP_CD='k'  ";
            query +=" OR BSTYP_CD='l'  ";
            query +=" OR BSTYP_CD='o')  ";
            query +=" AND DECODE(:BSTYP_CD, 'klo', 1, 0) = 1  ";
        }
        if( BSTYP_CD.equals("ny"))
        {
            query +=" AND (BSTYP_CD='n'  ";
            query +=" OR BSTYP_CD='y')  ";
            query +=" AND DECODE(:BSTYP_CD, 'ny', 1, 0) = 1  ";
        }
        if( BSTYP_CD.equals("k"))
        {
            query +=" AND BSTYP_CD= :BSTYP_CD  ";
        }
        if( BSTYP_CD.equals("l"))
        {
            query +=" AND BSTYP_CD= :BSTYP_CD  ";
        }
        if( BSTYP_CD.equals("o"))
        {
            query +=" AND BSTYP_CD= :BSTYP_CD  ";
        }
        if( BSTYP_CD.equals("n"))
        {
            query +=" AND BSTYP_CD= :BSTYP_CD  ";
        }
        if( BSTYP_CD.equals("y"))
        {
            query +=" AND BSTYP_CD= :BSTYP_CD  ";
        }
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        if( BSTYP_CD.equals("y"))
        {
            sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 머지
    public DOBJ CALLget_log_data2_MRG9(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG9");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL7, SEL8, SEL2","");
        rvobj.setName("MRG9") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 지부코드 보내기
    public DOBJ CALLget_log_data2_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_log_data2_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_log_data2_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GIBU  AS  BRAN_CD  ,  DEPT_NM  AS  BRAN_NM  FROM  INSA.TCPM_DEPT  WHERE  BIPLC_GBN  =  '2'   \n";
        query +=" AND  GIBU  IS  NOT  NULL ";
        sobj.setSql(query);
        return sobj;
    }
    // 개발중업소 조회
    public DOBJ CALLget_log_data2_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_log_data2_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_log_data2_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   DEMD_YRMN = dobj.getRetObject("SEL3").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT DEMD_MM_CNT , BRAN_CD , UPSO_CD , UPSO_NM , MNGEMSTR_NM , BSTYPGRAD_NM , UPSO_ADDR , UPSO_PHON , MNGEMSTR_HPNUM , MCHNDAESU , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 0) AS KY_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 1) AS TJ_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 2) AS ETC_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 3) AS KY_F , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 4) AS TJ_F , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 5) AS ETC_F , ONOFF_DATA_GBN , STAFF_NM , TO_CHAR(LAST_MOD_DATE, 'YYYY-MM-DD') AS LAST_MOD_DATE , COL_MCH_YN  ";
        query +=" FROM (  ";
        query +=" SELECT GIBU.FT_GET_UPSO_MMCNT(A.UPSO_CD, :DEMD_YRMN) AS DEMD_MM_CNT , BRAN_CD , UPSO_CD , UPSO_NM , MNGEMSTR_NM , GIBU.FT_GET_BSTYPGRAD_NM(A.UPSO_CD, '') AS BSTYPGRAD_NM , A.UPSO_NEW_ADDR1 || DECODE(A.UPSO_NEW_ADDR2, '', '', ', '||A.UPSO_NEW_ADDR2) || A.UPSO_REF_INFO UPSO_ADDR , A.UPSO_PHON , A.MNGEMSTR_HPNUM , A.MCHNDAESU , GIBU.FT_GET_UPSO_ONOFF_BSCON(A.UPSO_CD) AS ONOFF_BSCON , GIBU.FT_GET_ONOFF_DATA_GBN(A.UPSO_CD) AS ONOFF_DATA_GBN , FIDU.GET_STAFF_NM (STAFF_CD) AS STAFF_NM , (SELECT MAX(NVL(INS_DATE, MOD_DATE))  ";
        query +=" FROM GIBU.TBRA_UPSO_ONOFF_INFO  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD ) AS LAST_MOD_DATE , A.COL_MCH_YN , GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD) AS BSTYP_CD  ";
        query +=" FROM GIBU.SDB_TBRA_UPSO A  ";
        query +=" WHERE (CLSBS_YRMN IS NULL  ";
        query +=" OR NVL(SUBSTR(CLSBS_INS_DAY,1,6), ' ') > :DEMD_YRMN)  ";
        query +=" AND A.NEW_DAY IS NULL  ";
        query +=" AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" AND STAFF_CD = NVL(:STAFF_CD, STAFF_CD) )  ";
        query +=" WHERE (BSTYP_CD='k'  ";
        query +=" OR BSTYP_CD='l'  ";
        query +=" OR BSTYP_CD='o'  ";
        query +=" OR BSTYP_CD='n'  ";
        query +=" OR BSTYP_CD='y')  ";
        if( !BSTYP_CD.equals("") )
        {
            query +=" AND BSTYP_CD = :BSTYP_CD  ";
        }
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        if(!BSTYP_CD.equals(""))
        {
            sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 전체업소조회
    public DOBJ CALLget_log_data2_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_log_data2_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_log_data2_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   DEMD_YRMN = dobj.getRetObject("SEL3").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT DEMD_MM_CNT , BRAN_CD , UPSO_CD , UPSO_NM , MNGEMSTR_NM , BSTYPGRAD_NM , UPSO_ADDR , UPSO_PHON , MNGEMSTR_HPNUM , MCHNDAESU , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 0) AS KY_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 1) AS TJ_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 2) AS ETC_O , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 3) AS KY_F , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 4) AS TJ_F , GIBU.FT_SPLIT(ONOFF_BSCON, ',', 5) AS ETC_F , ONOFF_DATA_GBN , STAFF_NM , TO_CHAR(LAST_MOD_DATE, 'YYYY-MM-DD') AS LAST_MOD_DATE , COL_MCH_YN  ";
        query +=" FROM (  ";
        query +=" SELECT GIBU.FT_GET_UPSO_MMCNT(A.UPSO_CD, :DEMD_YRMN) AS DEMD_MM_CNT , BRAN_CD , UPSO_CD , UPSO_NM , MNGEMSTR_NM , GIBU.FT_GET_BSTYPGRAD_NM(A.UPSO_CD, '') AS BSTYPGRAD_NM , A.UPSO_NEW_ADDR1 || DECODE(A.UPSO_NEW_ADDR2, '', '', ', '||A.UPSO_NEW_ADDR2) || A.UPSO_REF_INFO UPSO_ADDR , A.UPSO_PHON , A.MNGEMSTR_HPNUM , A.MCHNDAESU , GIBU.FT_GET_UPSO_ONOFF_BSCON(A.UPSO_CD) AS ONOFF_BSCON , GIBU.FT_GET_ONOFF_DATA_GBN(A.UPSO_CD) AS ONOFF_DATA_GBN , FIDU.GET_STAFF_NM (STAFF_CD) AS STAFF_NM , (SELECT MAX(NVL(INS_DATE, MOD_DATE))  ";
        query +=" FROM GIBU.TBRA_UPSO_ONOFF_INFO  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD ) AS LAST_MOD_DATE , A.COL_MCH_YN , GIBU.FT_GET_BSTYP_INFO(A.UPSO_CD) AS BSTYP_CD  ";
        query +=" FROM GIBU.SDB_TBRA_UPSO A  ";
        query +=" WHERE (CLSBS_YRMN IS NULL  ";
        query +=" OR NVL(SUBSTR(CLSBS_INS_DAY,1,6), ' ') > :DEMD_YRMN)  ";
        query +=" AND (NEW_DAY IS NULL  ";
        query +=" OR NEW_DAY <= :DEMD_YRMN || '31')  ";
        query +=" AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" AND STAFF_CD = NVL(:STAFF_CD, STAFF_CD) )  ";
        query +=" WHERE (BSTYP_CD='k'  ";
        query +=" OR BSTYP_CD='l'  ";
        query +=" OR BSTYP_CD='o'  ";
        query +=" OR BSTYP_CD='n'  ";
        query +=" OR BSTYP_CD='y')  ";
        if( !BSTYP_CD.equals("") )
        {
            query +=" AND BSTYP_CD = :BSTYP_CD  ";
        }
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        if(!BSTYP_CD.equals(""))
        {
            sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$get_log_data2
    //##**$$end
}
