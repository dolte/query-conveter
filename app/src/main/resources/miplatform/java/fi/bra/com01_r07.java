
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class com01_r07
{
    public com01_r07()
    {
    }
    //##**$$p_upso_select
    /*
    */
    public DOBJ CTLp_upso_select(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("UPSO_STAT").equals("1"))
            {
                dobj  = CALLp_upso_select_SEL5(Conn, dobj);           //  관리업소
                dobj  = CALLp_upso_select_SEL1( dobj);        //  결과 합
            }
            else if( dobj.getRetObject("S").getRecord().get("UPSO_STAT").equals("2"))
            {
                dobj  = CALLp_upso_select_SEL6(Conn, dobj);           //  개발중업소
                dobj  = CALLp_upso_select_SEL1( dobj);        //  결과 합
            }
            else if( dobj.getRetObject("S").getRecord().get("UPSO_STAT").equals("3"))
            {
                dobj  = CALLp_upso_select_SEL7(Conn, dobj);           //  폐업업소
                dobj  = CALLp_upso_select_SEL1( dobj);        //  결과 합
            }
            else
            {
                dobj  = CALLp_upso_select_SEL8(Conn, dobj);           //  모든업소
                dobj  = CALLp_upso_select_SEL1( dobj);        //  결과 합
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
    public DOBJ CTLp_upso_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("UPSO_STAT").equals("1"))
        {
            dobj  = CALLp_upso_select_SEL5(Conn, dobj);           //  관리업소
            dobj  = CALLp_upso_select_SEL1( dobj);        //  결과 합
        }
        else if( dobj.getRetObject("S").getRecord().get("UPSO_STAT").equals("2"))
        {
            dobj  = CALLp_upso_select_SEL6(Conn, dobj);           //  개발중업소
            dobj  = CALLp_upso_select_SEL1( dobj);        //  결과 합
        }
        else if( dobj.getRetObject("S").getRecord().get("UPSO_STAT").equals("3"))
        {
            dobj  = CALLp_upso_select_SEL7(Conn, dobj);           //  폐업업소
            dobj  = CALLp_upso_select_SEL1( dobj);        //  결과 합
        }
        else
        {
            dobj  = CALLp_upso_select_SEL8(Conn, dobj);           //  모든업소
            dobj  = CALLp_upso_select_SEL1( dobj);        //  결과 합
        }
        return dobj;
    }
    // 관리업소
    public DOBJ CALLp_upso_select_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_upso_select_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_upso_select_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   DONG = dobj.getRetObject("S").getRecord().get("DONG");   //동
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_PHON = dobj.getRetObject("S").getRecord().get("UPSO_PHON");   //업소 전화
        String   CLIENT_NUM = dobj.getRetObject("S").getRecord().get("CLIENT_NUM");   //고객 번호
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //업소 명
        String   MNGEMSTR_NM = dobj.getRetObject("S").getRecord().get("MNGEMSTR_NM");   //경영주 이름
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   BIOWN_NUM = dobj.getRetObject("S").getRecord().get("BIOWN_NUM");   //사업자 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT TA.UPSO_CD , TA.UPSO_NM , TA.UPSO_STAT , TA.MNGEMSTR_NM , TA.PERMMSTR_NM , TA.UPSO_PHON , TA.MNGEMSTR_PHONNUM , TA.STAFF_CD , TA.STAFF_NM , TA.ADDR , TA.MONPRNCFEE , TA.GRAD , TA.GRADNM , TA.NEW_DAY , CASE WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '관리중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '관리|고소' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '개발중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '개발|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL  ";
        query +=" AND TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN '폐업|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL) THEN '폐업' END UPSO_STAT_NM , CASE WHEN (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN 'Y' ELSE 'N' END GOSO_YN , TB.ACCU_DAY , TB.ACCU_NUM , TB.ACCU_BRAN , TB.ACCU_GBN , TB.JUDG_CD , DECODE((SELECT COUNT(1)  ";
        query +=" FROM LOG.KDS_SHOPROOM  ";
        query +=" WHERE CO_STATUS = '07001'  ";
        query +=" AND UPSO_CD = TA.UPSO_CD), 1, '수집', '비수집') AS LOG_GET  ";
        query +=" FROM (  ";
        query +=" SELECT XA.UPSO_CD , XA.UPSO_NM , XA.UPSO_STAT , XA.MNGEMSTR_NM , XA.PERMMSTR_NM , XA.UPSO_PHON , XA.MNGEMSTR_PHONNUM , XA.STAFF_CD , XD.HAN_NM STAFF_NM , XA.UPSO_NEW_ADDR1 ||' '|| XA.UPSO_NEW_ADDR2 || XA.UPSO_REF_INFO AS ADDR , XB.MONPRNCFEE , TRIM(XC.BSTYP_CD) || TRIM(XC.GRAD_GBN) GRAD , XC.GRADNM , XA.NEW_DAY , XE.ACCU , XA.UPSO_NEW_ZIP AS UPSO_ZIP , XA.CLSBS_YRMN , GIBU.GET_MNG_ZIP(XA.UPSO_CD) AS MNG_ZIP , (SELECT NVL(COURT_NM, TOWNTWSHP)  ";
        query +=" FROM FIDU.TENV_POST  ";
        query +=" WHERE BD_MNG_NUM = XA.UPSO_BD_MNG_NUM) DONG  ";
        query +=" FROM GIBU.TBRA_UPSO XA , GIBU.TBRA_UPSORTAL_INFO XB , GIBU.TBRA_BSTYPGRAD XC , INSA.TINS_MST01 XD , (  ";
        query +=" SELECT UPSO_CD , MAX(ACCU_DAY || ACCU_NUM) ACCU  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD IN (SELECT UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO  ";
        query +=" WHERE BRAN_CD = :BRAN_CD)  ";
        query +=" GROUP BY UPSO_CD ) XE , (  ";
        query +=" SELECT MAX(JOIN_SEQ) JOIN_SEQ , UPSO_CD  ";
        query +=" FROM (  ";
        query +=" SELECT XB.UPSO_CD , XA.JOIN_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO XA , (  ";
        query +=" SELECT A.UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO A  ";
        query +=" WHERE A.UPSO_CD = NVL(:UPSO_CD, A.UPSO_CD)  ";
        query +=" AND A.BRAN_CD = :BRAN_CD  ";
        query +=" AND A.UPSO_NM LIKE DECODE( :UPSO_NM , NULL, A.UPSO_NM, '%' || :UPSO_NM || '%')  ";
        if( !UPSO_PHON.equals("") )
        {
            query +=" AND ( REPLACE(A.UPSO_PHON, '-', '') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.MNGEMSTR_PHONNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.MNGEMSTR_HPNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.PERMMSTR_PHONNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.PERMMSTR_HPNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%')  ";
        }
        if( !MNGEMSTR_NM.equals("") )
        {
            query +=" AND ( A.MNGEMSTR_NM LIKE '%' || :MNGEMSTR_NM || '%'  ";
            query +=" OR A.PERMMSTR_NM LIKE '%' || :MNGEMSTR_NM || '%')  ";
        }
        if( !BIOWN_NUM.equals("") )
        {
            query +=" AND A.BIOWN_NUM = :BIOWN_NUM  ";
        }
        if( !CLIENT_NUM.equals("") )
        {
            query +=" AND A.CLIENT_NUM = :CLIENT_NUM  ";
        }
        query +=" AND A.CLSBS_YRMN IS NULL  ";
        query +=" AND A.NEW_DAY IS NOT NULL ) XB  ";
        query +=" WHERE XA.UPSO_CD = XB.UPSO_CD ) ZA  ";
        query +=" GROUP BY ZA.UPSO_CD ) XF  ";
        query +=" WHERE XA.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.JOIN_SEQ = XF.JOIN_SEQ  ";
        query +=" AND XC.BSTYP_CD = XB.BSTYP_CD  ";
        query +=" AND XC.GRAD_GBN = XB.UPSO_GRAD  ";
        query +=" AND XD.STAFF_NUM(+) = XA.STAFF_CD  ";
        query +=" AND XE.UPSO_CD (+) = XA.UPSO_CD ) TA , GIBU.TBRA_ACCU TB  ";
        query +=" WHERE TB.UPSO_CD (+) = TA.UPSO_CD  ";
        query +=" AND TB.ACCU_DAY(+) = SUBSTR(TA.ACCU, 1, 8)  ";
        query +=" AND TB.ACCU_NUM(+) = SUBSTR(TA.ACCU, 9, 4)  ";
        query +=" AND TA.MNG_ZIP = NVL(:MNG_ZIP, TA.MNG_ZIP)  ";
        query +=" AND TA.DONG = NVL(:DONG, TA.DONG)  ";
        query +=" ORDER BY TA.UPSO_NM  ";
        sobj.setSql(query);
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("DONG", DONG);               //동
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        if(!UPSO_PHON.equals(""))
        {
            sobj.setString("UPSO_PHON", UPSO_PHON);               //업소 전화
        }
        if(!CLIENT_NUM.equals(""))
        {
            sobj.setString("CLIENT_NUM", CLIENT_NUM);               //고객 번호
        }
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        if(!MNGEMSTR_NM.equals(""))
        {
            sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //경영주 이름
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        if(!BIOWN_NUM.equals(""))
        {
            sobj.setString("BIOWN_NUM", BIOWN_NUM);               //사업자 번호
        }
        return sobj;
    }
    // 결과 합
    public DOBJ CALLp_upso_select_SEL1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("SEL1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL5, SEL6, SEL7, SEL8","");
        rvobj.setName("SEL1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 개발중업소
    public DOBJ CALLp_upso_select_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_upso_select_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_upso_select_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   DONG = dobj.getRetObject("S").getRecord().get("DONG");   //동
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_PHON = dobj.getRetObject("S").getRecord().get("UPSO_PHON");   //업소 전화
        String   CLIENT_NUM = dobj.getRetObject("S").getRecord().get("CLIENT_NUM");   //고객 번호
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //업소 명
        String   MNGEMSTR_NM = dobj.getRetObject("S").getRecord().get("MNGEMSTR_NM");   //경영주 이름
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   BIOWN_NUM = dobj.getRetObject("S").getRecord().get("BIOWN_NUM");   //사업자 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT TA.UPSO_CD , TA.UPSO_NM , TA.UPSO_STAT , TA.MNGEMSTR_NM , TA.PERMMSTR_NM , TA.UPSO_PHON , TA.MNGEMSTR_PHONNUM , TA.STAFF_CD , TA.STAFF_NM , TA.ADDR , TA.MONPRNCFEE , TA.GRAD , TA.GRADNM , TA.NEW_DAY , CASE WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '관리중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '관리|고소' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '개발중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '개발|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL  ";
        query +=" AND TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN '폐업|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL) THEN '폐업' END UPSO_STAT_NM , CASE WHEN (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN 'Y' ELSE 'N' END GOSO_YN , TB.ACCU_DAY , TB.ACCU_NUM , TB.ACCU_BRAN , TB.ACCU_GBN , TB.JUDG_CD , DECODE((SELECT COUNT(1)  ";
        query +=" FROM LOG.KDS_SHOPROOM  ";
        query +=" WHERE CO_STATUS = '07001'  ";
        query +=" AND UPSO_CD = TA.UPSO_CD), 1, '수집', '비수집') AS LOG_GET  ";
        query +=" FROM (  ";
        query +=" SELECT XA.UPSO_CD , XA.UPSO_NM , XA.UPSO_STAT , XA.MNGEMSTR_NM , XA.PERMMSTR_NM , XA.UPSO_PHON , XA.MNGEMSTR_PHONNUM , XA.STAFF_CD , XD.HAN_NM STAFF_NM , XA.UPSO_NEW_ADDR1 ||' '|| XA.UPSO_NEW_ADDR2 || XA.UPSO_REF_INFO AS ADDR , XB.MONPRNCFEE , TRIM(XC.BSTYP_CD) || TRIM(XC.GRAD_GBN) GRAD , XC.GRADNM , XA.NEW_DAY , XE.ACCU , XA.UPSO_NEW_ZIP AS UPSO_ZIP , XA.CLSBS_YRMN , GIBU.GET_MNG_ZIP(XA.UPSO_CD) AS MNG_ZIP , (SELECT NVL(COURT_NM, TOWNTWSHP)  ";
        query +=" FROM FIDU.TENV_POST  ";
        query +=" WHERE BD_MNG_NUM = XA.UPSO_BD_MNG_NUM) DONG  ";
        query +=" FROM GIBU.TBRA_UPSO XA , GIBU.TBRA_UPSORTAL_INFO XB , GIBU.TBRA_BSTYPGRAD XC , INSA.TINS_MST01 XD , (  ";
        query +=" SELECT UPSO_CD , MAX(ACCU_DAY || ACCU_NUM) ACCU  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD IN (SELECT UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO  ";
        query +=" WHERE BRAN_CD = :BRAN_CD)  ";
        query +=" GROUP BY UPSO_CD ) XE , (  ";
        query +=" SELECT MAX(JOIN_SEQ) JOIN_SEQ , UPSO_CD  ";
        query +=" FROM (  ";
        query +=" SELECT XB.UPSO_CD , XA.JOIN_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO XA , (  ";
        query +=" SELECT A.UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO A  ";
        query +=" WHERE A.UPSO_CD = NVL(:UPSO_CD, A.UPSO_CD)  ";
        query +=" AND A.BRAN_CD = :BRAN_CD  ";
        query +=" AND A.UPSO_NM LIKE DECODE( :UPSO_NM , NULL, A.UPSO_NM, '%' || :UPSO_NM || '%')  ";
        if( !UPSO_PHON.equals("") )
        {
            query +=" AND ( REPLACE(A.UPSO_PHON, '-', '') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.MNGEMSTR_PHONNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.MNGEMSTR_HPNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.PERMMSTR_PHONNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.PERMMSTR_HPNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%')  ";
        }
        if( !MNGEMSTR_NM.equals("") )
        {
            query +=" AND ( A.MNGEMSTR_NM LIKE '%' || :MNGEMSTR_NM || '%'  ";
            query +=" OR A.PERMMSTR_NM LIKE '%' || :MNGEMSTR_NM || '%')  ";
        }
        if( !BIOWN_NUM.equals("") )
        {
            query +=" AND A.BIOWN_NUM = :BIOWN_NUM  ";
        }
        if( !CLIENT_NUM.equals("") )
        {
            query +=" AND A.CLIENT_NUM = :CLIENT_NUM  ";
        }
        query +=" AND A.CLSBS_YRMN IS NULL  ";
        query +=" AND A.NEW_DAY IS NULL ) XB  ";
        query +=" WHERE XA.UPSO_CD = XB.UPSO_CD ) ZA  ";
        query +=" GROUP BY ZA.UPSO_CD ) XF  ";
        query +=" WHERE XA.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.JOIN_SEQ = XF.JOIN_SEQ  ";
        query +=" AND XC.BSTYP_CD = XB.BSTYP_CD  ";
        query +=" AND XC.GRAD_GBN = XB.UPSO_GRAD  ";
        query +=" AND XD.STAFF_NUM(+) = XA.STAFF_CD  ";
        query +=" AND XE.UPSO_CD (+) = XA.UPSO_CD ) TA , GIBU.TBRA_ACCU TB  ";
        query +=" WHERE TB.UPSO_CD (+) = TA.UPSO_CD  ";
        query +=" AND TB.ACCU_DAY(+) = SUBSTR(TA.ACCU, 1, 8)  ";
        query +=" AND TB.ACCU_NUM(+) = SUBSTR(TA.ACCU, 9, 4)  ";
        query +=" AND TA.MNG_ZIP = NVL(:MNG_ZIP, TA.MNG_ZIP)  ";
        query +=" AND TA.DONG = NVL(:DONG, TA.DONG)  ";
        query +=" ORDER BY TA.UPSO_NM  ";
        sobj.setSql(query);
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("DONG", DONG);               //동
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        if(!UPSO_PHON.equals(""))
        {
            sobj.setString("UPSO_PHON", UPSO_PHON);               //업소 전화
        }
        if(!CLIENT_NUM.equals(""))
        {
            sobj.setString("CLIENT_NUM", CLIENT_NUM);               //고객 번호
        }
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        if(!MNGEMSTR_NM.equals(""))
        {
            sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //경영주 이름
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        if(!BIOWN_NUM.equals(""))
        {
            sobj.setString("BIOWN_NUM", BIOWN_NUM);               //사업자 번호
        }
        return sobj;
    }
    // 폐업업소
    public DOBJ CALLp_upso_select_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_upso_select_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_upso_select_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   DONG = dobj.getRetObject("S").getRecord().get("DONG");   //동
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_PHON = dobj.getRetObject("S").getRecord().get("UPSO_PHON");   //업소 전화
        String   CLIENT_NUM = dobj.getRetObject("S").getRecord().get("CLIENT_NUM");   //고객 번호
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //업소 명
        String   MNGEMSTR_NM = dobj.getRetObject("S").getRecord().get("MNGEMSTR_NM");   //경영주 이름
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   BIOWN_NUM = dobj.getRetObject("S").getRecord().get("BIOWN_NUM");   //사업자 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT TA.UPSO_CD , TA.UPSO_NM , TA.UPSO_STAT , TA.MNGEMSTR_NM , TA.PERMMSTR_NM , TA.UPSO_PHON , TA.MNGEMSTR_PHONNUM , TA.STAFF_CD , TA.STAFF_NM , TA.ADDR , TA.MONPRNCFEE , TA.GRAD , TA.GRADNM , TA.NEW_DAY , CASE WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '관리중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '관리|고소' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '개발중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '개발|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL  ";
        query +=" AND TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN '폐업|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL) THEN '폐업' END UPSO_STAT_NM , CASE WHEN (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN 'Y' ELSE 'N' END GOSO_YN , TB.ACCU_DAY , TB.ACCU_NUM , TB.ACCU_BRAN , TB.ACCU_GBN , TB.JUDG_CD , DECODE((SELECT COUNT(1)  ";
        query +=" FROM LOG.KDS_SHOPROOM  ";
        query +=" WHERE CO_STATUS = '07001'  ";
        query +=" AND UPSO_CD = TA.UPSO_CD), 1, '수집', '비수집') AS LOG_GET  ";
        query +=" FROM (  ";
        query +=" SELECT XA.UPSO_CD , XA.UPSO_NM , XA.UPSO_STAT , XA.MNGEMSTR_NM , XA.PERMMSTR_NM , XA.UPSO_PHON , XA.MNGEMSTR_PHONNUM , XA.STAFF_CD , XD.HAN_NM STAFF_NM , XA.UPSO_NEW_ADDR1 ||' '|| XA.UPSO_NEW_ADDR2 || XA.UPSO_REF_INFO AS ADDR , XB.MONPRNCFEE , TRIM(XC.BSTYP_CD) || TRIM(XC.GRAD_GBN) GRAD , XC.GRADNM , XA.NEW_DAY , XE.ACCU , XA.UPSO_NEW_ZIP AS UPSO_ZIP , XA.CLSBS_YRMN , GIBU.GET_MNG_ZIP(XA.UPSO_CD) AS MNG_ZIP , (SELECT NVL(COURT_NM, TOWNTWSHP)  ";
        query +=" FROM FIDU.TENV_POST  ";
        query +=" WHERE BD_MNG_NUM = XA.UPSO_BD_MNG_NUM) DONG  ";
        query +=" FROM GIBU.TBRA_UPSO XA , GIBU.TBRA_UPSORTAL_INFO XB , GIBU.TBRA_BSTYPGRAD XC , INSA.TINS_MST01 XD , (  ";
        query +=" SELECT UPSO_CD , MAX(ACCU_DAY || ACCU_NUM) ACCU  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD IN (SELECT UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO  ";
        query +=" WHERE BRAN_CD = :BRAN_CD)  ";
        query +=" GROUP BY UPSO_CD ) XE , (  ";
        query +=" SELECT MAX(JOIN_SEQ) JOIN_SEQ , UPSO_CD  ";
        query +=" FROM (  ";
        query +=" SELECT XB.UPSO_CD , XA.JOIN_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO XA , (  ";
        query +=" SELECT A.UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO A  ";
        query +=" WHERE A.UPSO_CD = NVL(:UPSO_CD, A.UPSO_CD)  ";
        query +=" AND A.BRAN_CD = :BRAN_CD  ";
        query +=" AND A.UPSO_NM LIKE DECODE( :UPSO_NM , NULL, A.UPSO_NM, '%' || :UPSO_NM || '%')  ";
        if( !UPSO_PHON.equals("") )
        {
            query +=" AND ( REPLACE(A.UPSO_PHON, '-', '') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.MNGEMSTR_PHONNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.MNGEMSTR_HPNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.PERMMSTR_PHONNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.PERMMSTR_HPNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%')  ";
        }
        if( !MNGEMSTR_NM.equals("") )
        {
            query +=" AND ( A.MNGEMSTR_NM LIKE '%' || :MNGEMSTR_NM || '%'  ";
            query +=" OR A.PERMMSTR_NM LIKE '%' || :MNGEMSTR_NM || '%')  ";
        }
        if( !BIOWN_NUM.equals("") )
        {
            query +=" AND A.BIOWN_NUM = :BIOWN_NUM  ";
        }
        if( !CLIENT_NUM.equals("") )
        {
            query +=" AND A.CLIENT_NUM = :CLIENT_NUM  ";
        }
        query +=" AND A.CLSBS_YRMN IS NOT NULL ) XB  ";
        query +=" WHERE XA.UPSO_CD = XB.UPSO_CD ) ZA  ";
        query +=" GROUP BY ZA.UPSO_CD ) XF  ";
        query +=" WHERE XA.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.JOIN_SEQ = XF.JOIN_SEQ  ";
        query +=" AND XC.BSTYP_CD = XB.BSTYP_CD  ";
        query +=" AND XC.GRAD_GBN = XB.UPSO_GRAD  ";
        query +=" AND XD.STAFF_NUM(+) = XA.STAFF_CD  ";
        query +=" AND XE.UPSO_CD (+) = XA.UPSO_CD ) TA , GIBU.TBRA_ACCU TB  ";
        query +=" WHERE TB.UPSO_CD (+) = TA.UPSO_CD  ";
        query +=" AND TB.ACCU_DAY(+) = SUBSTR(TA.ACCU, 1, 8)  ";
        query +=" AND TB.ACCU_NUM(+) = SUBSTR(TA.ACCU, 9, 4)  ";
        query +=" AND (TA.MNG_ZIP IS NULL  ";
        query +=" OR TA.MNG_ZIP = NVL(:MNG_ZIP, TA.MNG_ZIP))  ";
        query +=" AND (TA.DONG IS NULL  ";
        query +=" OR TA.DONG = NVL(:DONG, TA.DONG))  ";
        query +=" ORDER BY TA.UPSO_NM  ";
        sobj.setSql(query);
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("DONG", DONG);               //동
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        if(!UPSO_PHON.equals(""))
        {
            sobj.setString("UPSO_PHON", UPSO_PHON);               //업소 전화
        }
        if(!CLIENT_NUM.equals(""))
        {
            sobj.setString("CLIENT_NUM", CLIENT_NUM);               //고객 번호
        }
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        if(!MNGEMSTR_NM.equals(""))
        {
            sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //경영주 이름
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        if(!BIOWN_NUM.equals(""))
        {
            sobj.setString("BIOWN_NUM", BIOWN_NUM);               //사업자 번호
        }
        return sobj;
    }
    // 모든업소
    public DOBJ CALLp_upso_select_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_upso_select_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_upso_select_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   DONG = dobj.getRetObject("S").getRecord().get("DONG");   //동
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_PHON = dobj.getRetObject("S").getRecord().get("UPSO_PHON");   //업소 전화
        String   CLIENT_NUM = dobj.getRetObject("S").getRecord().get("CLIENT_NUM");   //고객 번호
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //업소 명
        String   MNGEMSTR_NM = dobj.getRetObject("S").getRecord().get("MNGEMSTR_NM");   //경영주 이름
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   BIOWN_NUM = dobj.getRetObject("S").getRecord().get("BIOWN_NUM");   //사업자 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT TA.UPSO_CD , TA.UPSO_NM , TA.UPSO_STAT , TA.MNGEMSTR_NM , TA.PERMMSTR_NM , TA.UPSO_PHON , TA.MNGEMSTR_PHONNUM , TA.STAFF_CD , TA.STAFF_NM , TA.ADDR , TA.MONPRNCFEE , TA.GRAD , TA.GRADNM , TA.NEW_DAY , CASE WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '관리중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NOT NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '관리|고소' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NULL  ";
        query +=" OR TB.COMPN_DAY IS NOT NULL)) THEN '개발중' WHEN (TA.CLSBS_YRMN IS NULL  ";
        query +=" AND TA.NEW_DAY IS NULL  ";
        query +=" AND (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL)) THEN '개발|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL  ";
        query +=" AND TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN '폐업|고소' WHEN (TA.CLSBS_YRMN IS NOT NULL) THEN '폐업' END UPSO_STAT_NM , CASE WHEN (TB.ACCU_DAY IS NOT NULL  ";
        query +=" AND TB.COMPN_DAY IS NULL) THEN 'Y' ELSE 'N' END GOSO_YN , TB.ACCU_DAY , TB.ACCU_NUM , TB.ACCU_BRAN , TB.ACCU_GBN , TB.JUDG_CD , DECODE((SELECT COUNT(1)  ";
        query +=" FROM LOG.KDS_SHOPROOM  ";
        query +=" WHERE CO_STATUS = '07001'  ";
        query +=" AND UPSO_CD = TA.UPSO_CD), 1, '수집', '비수집') AS LOG_GET  ";
        query +=" FROM (  ";
        query +=" SELECT XA.UPSO_CD , XA.UPSO_NM , XA.UPSO_STAT , XA.MNGEMSTR_NM , XA.PERMMSTR_NM , XA.UPSO_PHON , XA.MNGEMSTR_PHONNUM , XA.STAFF_CD , XD.HAN_NM STAFF_NM , XA.UPSO_NEW_ADDR1 ||' '|| XA.UPSO_NEW_ADDR2 || XA.UPSO_REF_INFO AS ADDR , XB.MONPRNCFEE , TRIM(XC.BSTYP_CD) || TRIM(XC.GRAD_GBN) GRAD , XC.GRADNM , XA.NEW_DAY , XE.ACCU , XA.UPSO_NEW_ZIP AS UPSO_ZIP , XA.CLSBS_YRMN , GIBU.GET_MNG_ZIP(XA.UPSO_CD) AS MNG_ZIP , (SELECT NVL(COURT_NM, TOWNTWSHP)  ";
        query +=" FROM FIDU.TENV_POST  ";
        query +=" WHERE BD_MNG_NUM = XA.UPSO_BD_MNG_NUM) DONG  ";
        query +=" FROM GIBU.TBRA_UPSO XA , GIBU.TBRA_UPSORTAL_INFO XB , GIBU.TBRA_BSTYPGRAD XC , INSA.TINS_MST01 XD , (  ";
        query +=" SELECT UPSO_CD , MAX(ACCU_DAY || ACCU_NUM) ACCU  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD IN (SELECT UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO  ";
        query +=" WHERE BRAN_CD = :BRAN_CD)  ";
        query +=" GROUP BY UPSO_CD ) XE , (  ";
        query +=" SELECT MAX(JOIN_SEQ) JOIN_SEQ , UPSO_CD  ";
        query +=" FROM (  ";
        query +=" SELECT XB.UPSO_CD , XA.JOIN_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO XA , (  ";
        query +=" SELECT A.UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO A  ";
        query +=" WHERE A.UPSO_CD = NVL(:UPSO_CD, A.UPSO_CD)  ";
        query +=" AND A.BRAN_CD = :BRAN_CD  ";
        query +=" AND A.UPSO_NM LIKE DECODE( :UPSO_NM , NULL, A.UPSO_NM, '%' || :UPSO_NM || '%')  ";
        if( !UPSO_PHON.equals("") )
        {
            query +=" AND ( REPLACE(A.UPSO_PHON, '-', '') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.MNGEMSTR_PHONNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.MNGEMSTR_HPNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.PERMMSTR_PHONNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%'  ";
            query +=" OR REPLACE(A.PERMMSTR_HPNUM, '-' ,'') LIKE '%' || :UPSO_PHON || '%')  ";
        }
        if( !MNGEMSTR_NM.equals("") )
        {
            query +=" AND ( A.MNGEMSTR_NM LIKE '%' || :MNGEMSTR_NM || '%'  ";
            query +=" OR A.PERMMSTR_NM LIKE '%' || :MNGEMSTR_NM || '%')  ";
        }
        if( !BIOWN_NUM.equals("") )
        {
            query +=" AND A.BIOWN_NUM = :BIOWN_NUM  ";
        }
        if( !CLIENT_NUM.equals("") )
        {
            query +=" AND A.CLIENT_NUM = :CLIENT_NUM  ";
        }
        query +=" ) XB  ";
        query +=" WHERE XA.UPSO_CD = XB.UPSO_CD ) ZA  ";
        query +=" GROUP BY ZA.UPSO_CD ) XF  ";
        query +=" WHERE XA.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.UPSO_CD = XF.UPSO_CD  ";
        query +=" AND XB.JOIN_SEQ = XF.JOIN_SEQ  ";
        query +=" AND XC.BSTYP_CD = XB.BSTYP_CD  ";
        query +=" AND XC.GRAD_GBN = XB.UPSO_GRAD  ";
        query +=" AND XD.STAFF_NUM(+) = XA.STAFF_CD  ";
        query +=" AND XE.UPSO_CD (+) = XA.UPSO_CD ) TA , GIBU.TBRA_ACCU TB  ";
        query +=" WHERE TB.UPSO_CD (+) = TA.UPSO_CD  ";
        query +=" AND TB.ACCU_DAY(+) = SUBSTR(TA.ACCU, 1, 8)  ";
        query +=" AND TB.ACCU_NUM(+) = SUBSTR(TA.ACCU, 9, 4)  ";
        query +=" AND (TA.MNG_ZIP IS NULL  ";
        query +=" OR TA.MNG_ZIP = NVL(:MNG_ZIP, TA.MNG_ZIP))  ";
        query +=" AND (TA.DONG IS NULL  ";
        query +=" OR TA.DONG = NVL(:DONG, TA.DONG))  ";
        query +=" ORDER BY TA.UPSO_NM  ";
        sobj.setSql(query);
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("DONG", DONG);               //동
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        if(!UPSO_PHON.equals(""))
        {
            sobj.setString("UPSO_PHON", UPSO_PHON);               //업소 전화
        }
        if(!CLIENT_NUM.equals(""))
        {
            sobj.setString("CLIENT_NUM", CLIENT_NUM);               //고객 번호
        }
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        if(!MNGEMSTR_NM.equals(""))
        {
            sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //경영주 이름
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        if(!BIOWN_NUM.equals(""))
        {
            sobj.setString("BIOWN_NUM", BIOWN_NUM);               //사업자 번호
        }
        return sobj;
    }
    //##**$$p_upso_select
    //##**$$p_demd_select
    /* * 프로그램명 : com01_r07
    * 작성자 : 박태현
    * 작성일 : 2009/11/24
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLp_demd_select(DOBJ dobj)
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
            dobj  = CALLp_demd_select_SEL1(Conn, dobj);           //  청구데이터조회
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
    public DOBJ CTLp_demd_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLp_demd_select_SEL1(Conn, dobj);           //  청구데이터조회
        return dobj;
    }
    // 청구데이터조회
    public DOBJ CALLp_demd_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_demd_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_demd_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEMD_YRMN  ,  A.UPSO_CD  ,  D.UPSO_NM  ,  A.BRAN_CD  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.DEMD_GBN  ,  E.CODE_NM  DEMD_GBN_NM  ,  A.MONPRNCFEE  ,  A.DEMD_MMCNT  ,  A.DSCT_AMT  ,  A.TOT_ADDT_AMT  ,  A.TOT_EADDT_AMT  ,  A.TOT_DEMD_AMT  ,  GIBU.FT_GET_LAST_REPT_YRMN(:UPSO_CD,  6)  LAST_REPT_YRMN  FROM  GIBU.TBRA_DEMD_OCR  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  :UPSO_CD  )  B  ,  (   \n";
        query +=" SELECT  UPSO_NM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD  )  D  ,  (   \n";
        query +=" SELECT  HIGH_CD  ,  CODE_CD  ,  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00141'  )  E  WHERE  A.UPSO_CD  =  B.UPSO_CD(+)   \n";
        query +=" AND  A.DEMD_YRMN  =  B.DEMD_YRMN(+)   \n";
        query +=" AND  A.DEMD_GBN  =  E.CODE_CD   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD  ORDER  BY  DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$p_demd_select
    //##**$$p_upso_select_old
    /*
    */
    public DOBJ CTLp_upso_select_old(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("UPSO_STAT").equals("1"))
            {
                dobj  = CALLp_upso_select_old_SEL2(Conn, dobj);           //  관리업소조회
                dobj  = CALLp_upso_select_old_SEL1( dobj);        //  결과 합치기
            }
            else if( dobj.getRetObject("S").getRecord().get("UPSO_STAT").equals("2"))
            {
                dobj  = CALLp_upso_select_old_SEL3(Conn, dobj);           //  개발중업소조회
                dobj  = CALLp_upso_select_old_SEL1( dobj);        //  결과 합치기
            }
            else if( dobj.getRetObject("S").getRecord().get("UPSO_STAT").equals("3"))
            {
                dobj  = CALLp_upso_select_old_SEL4(Conn, dobj);           //  폐업업소조회
                dobj  = CALLp_upso_select_old_SEL1( dobj);        //  결과 합치기
            }
            else if( dobj.getRetObject("S").getRecord().get("UPSO_STAT").equals("4"))
            {
                dobj  = CALLp_upso_select_old_SEL26(Conn, dobj);           //  가업소조회
                dobj  = CALLp_upso_select_old_SEL1( dobj);        //  결과 합치기
            }
            else
            {
                dobj  = CALLp_upso_select_old_SEL6(Conn, dobj);           //  모든업소조회
                dobj  = CALLp_upso_select_old_SEL1( dobj);        //  결과 합치기
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
    public DOBJ CTLp_upso_select_old( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("UPSO_STAT").equals("1"))
        {
            dobj  = CALLp_upso_select_old_SEL2(Conn, dobj);           //  관리업소조회
            dobj  = CALLp_upso_select_old_SEL1( dobj);        //  결과 합치기
        }
        else if( dobj.getRetObject("S").getRecord().get("UPSO_STAT").equals("2"))
        {
            dobj  = CALLp_upso_select_old_SEL3(Conn, dobj);           //  개발중업소조회
            dobj  = CALLp_upso_select_old_SEL1( dobj);        //  결과 합치기
        }
        else if( dobj.getRetObject("S").getRecord().get("UPSO_STAT").equals("3"))
        {
            dobj  = CALLp_upso_select_old_SEL4(Conn, dobj);           //  폐업업소조회
            dobj  = CALLp_upso_select_old_SEL1( dobj);        //  결과 합치기
        }
        else if( dobj.getRetObject("S").getRecord().get("UPSO_STAT").equals("4"))
        {
            dobj  = CALLp_upso_select_old_SEL26(Conn, dobj);           //  가업소조회
            dobj  = CALLp_upso_select_old_SEL1( dobj);        //  결과 합치기
        }
        else
        {
            dobj  = CALLp_upso_select_old_SEL6(Conn, dobj);           //  모든업소조회
            dobj  = CALLp_upso_select_old_SEL1( dobj);        //  결과 합치기
        }
        return dobj;
    }
    // 관리업소조회
    public DOBJ CALLp_upso_select_old_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_upso_select_old_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_upso_select_old_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //시도
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   DSRCCNTY = dobj.getRetObject("S").getRecord().get("DSRCCNTY");   //구군
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_PHON = dobj.getRetObject("S").getRecord().get("UPSO_PHON");   //업소 전화
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //업소 명
        String   MNGEMSTR_NM = dobj.getRetObject("S").getRecord().get("MNGEMSTR_NM");   //경영주 이름
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.UPSO_STAT  ,  TA.MNGEMSTR_NM  ,  TA.PERMMSTR_NM  ,  TA.UPSO_PHON  ,  TA.MNGEMSTR_PHONNUM  ,  TA.STAFF_CD  ,  TA.STAFF_NM  ,  TA.ADDR  ,  TA.MONPRNCFEE  ,  TA.GRAD  ,  TA.GRADNM  ,  TA.NEW_DAY  ,  CASE  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '관리중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '관리|고소'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '개발중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '개발|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  '폐업|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL)  THEN  '폐업'  END  UPSO_STAT_NM  ,  CASE  WHEN  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  'Y'  ELSE  'N'  END  GOSO_YN  ,  TB.ACCU_DAY  ,  TB.ACCU_NUM  ,  TB.ACCU_BRAN  ,  TB.ACCU_GBN  ,  TB.JUDG_CD  FROM  (   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.UPSO_STAT  ,  XA.MNGEMSTR_NM  ,  XA.PERMMSTR_NM  ,  XA.UPSO_PHON  ,  XA.MNGEMSTR_PHONNUM  ,  XA.STAFF_CD  ,  XD.HAN_NM  STAFF_NM  ,  XA.UPSO_ADDR1  ||  XA.UPSO_ADDR2  ADDR  ,  XB.MONPRNCFEE  ,  TRIM(XC.BSTYP_CD)  ||  TRIM(XC.GRAD_GBN)  GRAD  ,  XC.GRADNM  ,  XA.NEW_DAY  ,  XE.ACCU  ,  XA.UPSO_ZIP  ,  XA.MNG_ZIP  ,  XA.CLSBS_YRMN  FROM  GIBU.TBRA_UPSO  XA  ,  GIBU.TBRA_UPSORTAL_INFO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  INSA.TINS_MST01  XD  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(ACCU_DAY  ||  ACCU_NUM)  ACCU  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD)  GROUP  BY  UPSO_CD  )  XE  ,  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  ,  UPSO_CD  FROM  (   \n";
        query +=" SELECT  XB.UPSO_CD  ,  XA.JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  FROM  GIBU.TBRA_UPSO  A  WHERE  A.UPSO_CD  =  NVL(:UPSO_CD,  A.UPSO_CD)   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_NM  LIKE  DECODE(  :UPSO_NM  ,  NULL,  A.UPSO_NM,  '%'  ||  :UPSO_NM  ||  '%')   \n";
        query +=" AND  (NVL(A.UPSO_PHON,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.UPSO_PHON,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_PHONNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.MNGEMSTR_PHONNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_HPNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.MNGEMSTR_HPNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_PHONNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.PERMMSTR_PHONNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_HPNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.PERMMSTR_HPNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%'))   \n";
        query +=" AND  (NVL(A.MNGEMSTR_NM,  '-')  LIKE  DECODE(  :MNGEMSTR_NM  ,  NULL,  NVL(A.MNGEMSTR_NM,  '-'),  '%'  ||  :MNGEMSTR_NM  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_NM,  '-')  LIKE  DECODE(  :MNGEMSTR_NM  ,  NULL,  NVL(A.PERMMSTR_NM,  '-'),  '%'  ||  :MNGEMSTR_NM  ||  '%'))   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  A.NEW_DAY  IS  NOT  NULL  )  XB  WHERE  XA.UPSO_CD  =  XB.UPSO_CD  )  ZA  GROUP  BY  ZA.UPSO_CD  )  XF  WHERE  XA.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.JOIN_SEQ  =  XF.JOIN_SEQ   \n";
        query +=" AND  XC.BSTYP_CD  =  XB.BSTYP_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XB.UPSO_GRAD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD   \n";
        query +=" AND  XE.UPSO_CD  (+)  =  XA.UPSO_CD  )  TA  ,  GIBU.TBRA_ACCU  TB  WHERE  TB.UPSO_CD  (+)  =  TA.UPSO_CD   \n";
        query +=" AND  TB.ACCU_DAY(+)  =  SUBSTR(TA.ACCU,  1,  8)   \n";
        query +=" AND  TB.ACCU_NUM(+)  =  SUBSTR(TA.ACCU,  9,  4)   \n";
        query +=" AND  TA.MNG_ZIP  IN  (   \n";
        query +=" SELECT  MNG_ZIP  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  MNG_ZIP  =  NVL(:MNG_ZIP,  MNG_ZIP)   \n";
        query +=" AND  ATTE  =  NVL(:ATTE,  ATTE)   \n";
        query +=" AND  DSRCCNTY  =  NVL(:DSRCCNTY,  DSRCCNTY)  )  ORDER  BY  TA.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //시도
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("DSRCCNTY", DSRCCNTY);               //구군
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("UPSO_PHON", UPSO_PHON);               //업소 전화
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //경영주 이름
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 결과 합치기
    // 조건에 맞는 결과 데이타를 내보낸다
    public DOBJ CALLp_upso_select_old_SEL1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("SEL1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL2, SEL3, SEL4, SEL26, SEL6","");
        rvobj.setName("SEL1") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 개발중업소조회
    public DOBJ CALLp_upso_select_old_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_upso_select_old_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_upso_select_old_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //시도
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   DSRCCNTY = dobj.getRetObject("S").getRecord().get("DSRCCNTY");   //구군
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_PHON = dobj.getRetObject("S").getRecord().get("UPSO_PHON");   //업소 전화
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //업소 명
        String   MNGEMSTR_NM = dobj.getRetObject("S").getRecord().get("MNGEMSTR_NM");   //경영주 이름
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.UPSO_STAT  ,  TA.MNGEMSTR_NM  ,  TA.PERMMSTR_NM  ,  TA.UPSO_PHON  ,  TA.MNGEMSTR_PHONNUM  ,  TA.STAFF_CD  ,  TA.STAFF_NM  ,  TA.ADDR  ,  TA.MONPRNCFEE  ,  TA.GRAD  ,  TA.GRADNM  ,  TA.NEW_DAY  ,  CASE  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '관리중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '관리|고소'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '개발중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '개발|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  '폐업|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL)  THEN  '폐업'  END  UPSO_STAT_NM  ,  CASE  WHEN  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  'Y'  ELSE  'N'  END  GOSO_YN  ,  TB.ACCU_DAY  ,  TB.ACCU_NUM  ,  TB.ACCU_BRAN  ,  TB.ACCU_GBN  ,  TB.JUDG_CD  FROM  (   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.UPSO_STAT  ,  XA.MNGEMSTR_NM  ,  XA.PERMMSTR_NM  ,  XA.UPSO_PHON  ,  XA.MNGEMSTR_PHONNUM  ,  XA.STAFF_CD  ,  XD.HAN_NM  STAFF_NM  ,  XA.UPSO_ADDR1  ||  XA.UPSO_ADDR2  ADDR  ,  XB.MONPRNCFEE  ,  TRIM(XC.BSTYP_CD)  ||  TRIM(XC.GRAD_GBN)  GRAD  ,  XC.GRADNM  ,  XA.CLSBS_YRMN  ,  XA.NEW_DAY  ,  XE.ACCU  ,  XA.UPSO_ZIP  ,  XA.MNG_ZIP  FROM  GIBU.TBRA_UPSO  XA  ,  GIBU.TBRA_UPSORTAL_INFO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  INSA.TINS_MST01  XD  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(ACCU_DAY  ||  ACCU_NUM)  ACCU  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD)  GROUP  BY  UPSO_CD  )  XE  ,  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  ,  UPSO_CD  FROM  (   \n";
        query +=" SELECT  XB.UPSO_CD  ,  XA.JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  FROM  GIBU.TBRA_UPSO  A  WHERE  A.UPSO_CD  =  NVL(:UPSO_CD,  A.UPSO_CD)   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_NM  LIKE  DECODE(  :UPSO_NM  ,  NULL,  A.UPSO_NM,  '%'  ||  :UPSO_NM  ||  '%')   \n";
        query +=" AND  (NVL(A.UPSO_PHON,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.UPSO_PHON,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_PHONNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.MNGEMSTR_PHONNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_HPNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.MNGEMSTR_HPNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_PHONNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.PERMMSTR_PHONNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_HPNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.PERMMSTR_HPNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%'))   \n";
        query +=" AND  (NVL(A.MNGEMSTR_NM,  '-')  LIKE  DECODE(  :MNGEMSTR_NM  ,  NULL,  NVL(A.MNGEMSTR_NM,  '-'),  '%'  ||  :MNGEMSTR_NM  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_NM,  '-')  LIKE  DECODE(  :MNGEMSTR_NM  ,  NULL,  NVL(A.PERMMSTR_NM,  '-'),  '%'  ||  :MNGEMSTR_NM  ||  '%'))   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  A.NEW_DAY  IS  NULL  )  XB  WHERE  XA.UPSO_CD  =  XB.UPSO_CD  )  ZA  GROUP  BY  ZA.UPSO_CD  )  XF  WHERE  XA.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.JOIN_SEQ  =  XF.JOIN_SEQ   \n";
        query +=" AND  XC.BSTYP_CD  =  XB.BSTYP_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XB.UPSO_GRAD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD   \n";
        query +=" AND  XE.UPSO_CD  (+)  =  XA.UPSO_CD  )  TA  ,  GIBU.TBRA_ACCU  TB  WHERE  TB.UPSO_CD  (+)  =  TA.UPSO_CD   \n";
        query +=" AND  TB.ACCU_DAY(+)  =  SUBSTR(TA.ACCU,  1,  8)   \n";
        query +=" AND  TB.ACCU_NUM(+)  =  SUBSTR(TA.ACCU,  9,  4)   \n";
        query +=" AND  TA.MNG_ZIP  IN  (   \n";
        query +=" SELECT  MNG_ZIP  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  MNG_ZIP  =  NVL(:MNG_ZIP,  MNG_ZIP)   \n";
        query +=" AND  ATTE  =  NVL(:ATTE,  ATTE)   \n";
        query +=" AND  DSRCCNTY  =  NVL(:DSRCCNTY,  DSRCCNTY)  )  ORDER  BY  TA.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //시도
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("DSRCCNTY", DSRCCNTY);               //구군
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("UPSO_PHON", UPSO_PHON);               //업소 전화
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //경영주 이름
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 폐업업소조회
    public DOBJ CALLp_upso_select_old_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_upso_select_old_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_upso_select_old_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //시도
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   DSRCCNTY = dobj.getRetObject("S").getRecord().get("DSRCCNTY");   //구군
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_PHON = dobj.getRetObject("S").getRecord().get("UPSO_PHON");   //업소 전화
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //업소 명
        String   MNGEMSTR_NM = dobj.getRetObject("S").getRecord().get("MNGEMSTR_NM");   //경영주 이름
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.UPSO_STAT  ,  TA.MNGEMSTR_NM  ,  TA.PERMMSTR_NM  ,  TA.UPSO_PHON  ,  TA.MNGEMSTR_PHONNUM  ,  TA.STAFF_CD  ,  TA.STAFF_NM  ,  TA.ADDR  ,  TA.MONPRNCFEE  ,  TA.GRAD  ,  TA.GRADNM  ,  TA.NEW_DAY  ,  CASE  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '관리중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '관리|고소'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '개발중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '개발|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  '폐업|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL)  THEN  '폐업'  END  UPSO_STAT_NM  ,  CASE  WHEN  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  'Y'  ELSE  'N'  END  GOSO_YN  ,  TB.ACCU_DAY  ,  TB.ACCU_NUM  ,  TB.ACCU_BRAN  ,  TB.ACCU_GBN  ,  TB.JUDG_CD  FROM  (   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.UPSO_STAT  ,  XA.MNGEMSTR_NM  ,  XA.PERMMSTR_NM  ,  XA.UPSO_PHON  ,  XA.MNGEMSTR_PHONNUM  ,  XA.STAFF_CD  ,  XD.HAN_NM  STAFF_NM  ,  XA.UPSO_ADDR1  ||  XA.UPSO_ADDR2  ADDR  ,  XB.MONPRNCFEE  ,  TRIM(XC.BSTYP_CD)  ||  TRIM(XC.GRAD_GBN)  GRAD  ,  XC.GRADNM  ,  XA.NEW_DAY  ,  XE.ACCU  ,  XA.UPSO_ZIP  ,  XA.MNG_ZIP  ,  XA.CLSBS_YRMN  FROM  GIBU.TBRA_UPSO  XA  ,  GIBU.TBRA_UPSORTAL_INFO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  INSA.TINS_MST01  XD  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(ACCU_DAY  ||  ACCU_NUM)  ACCU  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD)  GROUP  BY  UPSO_CD  )  XE  ,  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  ,  UPSO_CD  FROM  (   \n";
        query +=" SELECT  XB.UPSO_CD  ,  XA.JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  FROM  GIBU.TBRA_UPSO  A  WHERE  A.UPSO_CD  =  NVL(:UPSO_CD,  A.UPSO_CD)   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_NM  LIKE  DECODE(  :UPSO_NM  ,  NULL,  A.UPSO_NM,  '%'  ||  :UPSO_NM  ||  '%')   \n";
        query +=" AND  (NVL(A.UPSO_PHON,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.UPSO_PHON,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_PHONNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.MNGEMSTR_PHONNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_HPNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.MNGEMSTR_HPNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_PHONNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.PERMMSTR_PHONNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_HPNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.PERMMSTR_HPNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%'))   \n";
        query +=" AND  (NVL(A.MNGEMSTR_NM,  '-')  LIKE  DECODE(  :MNGEMSTR_NM  ,  NULL,  NVL(A.MNGEMSTR_NM,  '-'),  '%'  ||  :MNGEMSTR_NM  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_NM,  '-')  LIKE  DECODE(  :MNGEMSTR_NM  ,  NULL,  NVL(A.PERMMSTR_NM,  '-'),  '%'  ||  :MNGEMSTR_NM  ||  '%'))   \n";
        query +=" AND  A.CLSBS_YRMN  IS  NOT  NULL  )  XB  WHERE  XA.UPSO_CD  =  XB.UPSO_CD  )  ZA  GROUP  BY  ZA.UPSO_CD  )  XF  WHERE  XA.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.JOIN_SEQ  =  XF.JOIN_SEQ   \n";
        query +=" AND  XC.BSTYP_CD  =  XB.BSTYP_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XB.UPSO_GRAD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD   \n";
        query +=" AND  XE.UPSO_CD  (+)  =  XA.UPSO_CD  )  TA  ,  GIBU.TBRA_ACCU  TB  WHERE  TB.UPSO_CD  (+)  =  TA.UPSO_CD   \n";
        query +=" AND  TB.ACCU_DAY(+)  =  SUBSTR(TA.ACCU,  1,  8)   \n";
        query +=" AND  TB.ACCU_NUM(+)  =  SUBSTR(TA.ACCU,  9,  4)   \n";
        query +=" AND  TA.MNG_ZIP  IN  (   \n";
        query +=" SELECT  MNG_ZIP  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  MNG_ZIP  =  NVL(:MNG_ZIP,  MNG_ZIP)   \n";
        query +=" AND  ATTE  =  NVL(:ATTE,  ATTE)   \n";
        query +=" AND  DSRCCNTY  =  NVL(:DSRCCNTY,  DSRCCNTY)  )  ORDER  BY  TA.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //시도
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("DSRCCNTY", DSRCCNTY);               //구군
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("UPSO_PHON", UPSO_PHON);               //업소 전화
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //경영주 이름
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 가업소조회
    public DOBJ CALLp_upso_select_old_SEL26(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL26");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL26");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_upso_select_old_SEL26(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL26");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_upso_select_old_SEL26(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //업소 명
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.UPSO_STAT  ,  TA.MNGEMSTR_NM  ,  TA.PERMMSTR_NM  ,  TA.UPSO_PHON  ,  TA.MNGEMSTR_PHONNUM  ,  TA.STAFF_CD  ,  TC.HAN_NM  STAFF_NM  ,  TA.UPSO_ADDR1||'  '||TA.UPSO_ADDR2  ADDR  ,  TB.MONPRNCFEE  ,  TRIM(TB.BSTYP_CD)  ||  TRIM(TB.UPSO_GRAD)  GRAD  ,  TD.GRADNM  ,  ''  NEW_DAY  ,  '가업소'  UPSO_STAT_NM  ,  'N'  GOSO_YN  ,  ''  ACCU_DAY  ,  ''  ACCU_NUM  ,  ''  ACCU_BRAN  ,  ''  ACCU_GBN  ,  ''  JUDG_CD  FROM  GIBU.TBRA_UPSO  TA  ,  GIBU.TBRA_UPSORTAL_INFO  TB  ,  INSA.TINS_MST01  TC  ,  GIBU.TBRA_BSTYPGRAD  TD  WHERE  TA.UPSO_STAT  =  '2'   \n";
        query +=" AND  TA.UPSO_CD  =  NVL(:UPSO_CD,  TA.UPSO_CD)   \n";
        query +=" AND  TA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TA.UPSO_NM  LIKE  DECODE(  :UPSO_NM  ,  NULL,  TA.UPSO_NM,  '%'  ||  :UPSO_NM  ||  '%')   \n";
        query +=" AND  TB.UPSO_CD(+)  =  TA.UPSO_CD   \n";
        query +=" AND  TC.STAFF_NUM(+)  =  TA.STAFF_CD   \n";
        query +=" AND  TD.GRAD_GBN(+)  =  TB.BSTYP_CD  ORDER  BY  TA.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 모든업소조회
    public DOBJ CALLp_upso_select_old_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_upso_select_old_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_upso_select_old_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ATTE = dobj.getRetObject("S").getRecord().get("ATTE");   //시도
        String   MNG_ZIP = dobj.getRetObject("S").getRecord().get("MNG_ZIP");   //관리 우편번호
        String   DSRCCNTY = dobj.getRetObject("S").getRecord().get("DSRCCNTY");   //구군
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_PHON = dobj.getRetObject("S").getRecord().get("UPSO_PHON");   //업소 전화
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //업소 명
        String   MNGEMSTR_NM = dobj.getRetObject("S").getRecord().get("MNGEMSTR_NM");   //경영주 이름
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.UPSO_STAT  ,  TA.MNGEMSTR_NM  ,  TA.PERMMSTR_NM  ,  TA.UPSO_PHON  ,  TA.MNGEMSTR_PHONNUM  ,  TA.STAFF_CD  ,  TA.STAFF_NM  ,  TA.ADDR  ,  TA.MONPRNCFEE  ,  TA.GRAD  ,  TA.GRADNM  ,  TA.NEW_DAY  ,  CASE  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '관리중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '관리|고소'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NULL   \n";
        query +=" OR  TB.COMPN_DAY  IS  NOT  NULL))  THEN  '개발중'  WHEN  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  TA.NEW_DAY  IS  NULL   \n";
        query +=" AND  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL))  THEN  '개발|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL   \n";
        query +=" AND  TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  '폐업|고소'  WHEN  (TA.CLSBS_YRMN  IS  NOT  NULL)  THEN  '폐업'  END  UPSO_STAT_NM  ,  CASE  WHEN  (TB.ACCU_DAY  IS  NOT  NULL   \n";
        query +=" AND  TB.COMPN_DAY  IS  NULL)  THEN  'Y'  ELSE  'N'  END  GOSO_YN  ,  TB.ACCU_DAY  ,  TB.ACCU_NUM  ,  TB.ACCU_BRAN  ,  TB.ACCU_GBN  ,  TB.JUDG_CD  FROM  (   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.UPSO_NM  ,  XA.UPSO_STAT  ,  XA.MNGEMSTR_NM  ,  XA.PERMMSTR_NM  ,  XA.UPSO_PHON  ,  XA.MNGEMSTR_PHONNUM  ,  XA.STAFF_CD  ,  XD.HAN_NM  STAFF_NM  ,  XA.UPSO_ADDR1  ||  XA.UPSO_ADDR2  ADDR  ,  XB.MONPRNCFEE  ,  TRIM(XC.BSTYP_CD)  ||  TRIM(XC.GRAD_GBN)  GRAD  ,  XC.GRADNM  ,  XA.NEW_DAY  ,  XE.ACCU  ,  XA.UPSO_ZIP  ,  XA.CLSBS_YRMN  ,  XA.MNG_ZIP  FROM  GIBU.TBRA_UPSO  XA  ,  GIBU.TBRA_UPSORTAL_INFO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  ,  INSA.TINS_MST01  XD  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(ACCU_DAY  ||  ACCU_NUM)  ACCU  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD)  GROUP  BY  UPSO_CD  )  XE  ,  (   \n";
        query +=" SELECT  MAX(JOIN_SEQ)  JOIN_SEQ  ,  UPSO_CD  FROM  (   \n";
        query +=" SELECT  XB.UPSO_CD  ,  XA.JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  XA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  FROM  GIBU.TBRA_UPSO  A  WHERE  A.UPSO_CD  =  NVL(:UPSO_CD,  A.UPSO_CD)   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_NM  LIKE  DECODE(  :UPSO_NM  ,  NULL,  A.UPSO_NM,  '%'  ||  :UPSO_NM  ||  '%')   \n";
        query +=" AND  (NVL(A.UPSO_PHON,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.UPSO_PHON,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_PHONNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.MNGEMSTR_PHONNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.MNGEMSTR_HPNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.MNGEMSTR_HPNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_PHONNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.PERMMSTR_PHONNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_HPNUM,  '-')  LIKE  DECODE(:UPSO_PHON,  NULL,  NVL(A.PERMMSTR_HPNUM,  '-'),  '%'  ||  :UPSO_PHON  ||  '%'))   \n";
        query +=" AND  (NVL(A.MNGEMSTR_NM,  '-')  LIKE  DECODE(  :MNGEMSTR_NM  ,  NULL,  NVL(A.MNGEMSTR_NM,  '-'),  '%'  ||  :MNGEMSTR_NM  ||  '%')   \n";
        query +=" OR  NVL(A.PERMMSTR_NM,  '-')  LIKE  DECODE(  :MNGEMSTR_NM  ,  NULL,  NVL(A.PERMMSTR_NM,  '-'),  '%'  ||  :MNGEMSTR_NM  ||  '%'))  )  XB  WHERE  XA.UPSO_CD  =  XB.UPSO_CD  )  ZA  GROUP  BY  ZA.UPSO_CD  )  XF  WHERE  XA.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.UPSO_CD  =  XF.UPSO_CD   \n";
        query +=" AND  XB.JOIN_SEQ  =  XF.JOIN_SEQ   \n";
        query +=" AND  XC.BSTYP_CD  =  XB.BSTYP_CD   \n";
        query +=" AND  XC.GRAD_GBN  =  XB.UPSO_GRAD   \n";
        query +=" AND  XD.STAFF_NUM(+)  =  XA.STAFF_CD   \n";
        query +=" AND  XE.UPSO_CD  (+)  =  XA.UPSO_CD  )  TA  ,  GIBU.TBRA_ACCU  TB  WHERE  TB.UPSO_CD  (+)  =  TA.UPSO_CD   \n";
        query +=" AND  TB.ACCU_DAY(+)  =  SUBSTR(TA.ACCU,  1,  8)   \n";
        query +=" AND  TB.ACCU_NUM(+)  =  SUBSTR(TA.ACCU,  9,  4)   \n";
        query +=" AND  TA.MNG_ZIP  IN  (   \n";
        query +=" SELECT  MNG_ZIP  FROM  GIBU.TBRA_BRANZIP_MNG  WHERE  MNG_ZIP  =  NVL(:MNG_ZIP,  MNG_ZIP)   \n";
        query +=" AND  ATTE  =  NVL(:ATTE,  ATTE)   \n";
        query +=" AND  DSRCCNTY  =  NVL(:DSRCCNTY,  DSRCCNTY)  )  ORDER  BY  TA.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("ATTE", ATTE);               //시도
        sobj.setString("MNG_ZIP", MNG_ZIP);               //관리 우편번호
        sobj.setString("DSRCCNTY", DSRCCNTY);               //구군
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("UPSO_PHON", UPSO_PHON);               //업소 전화
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        sobj.setString("MNGEMSTR_NM", MNGEMSTR_NM);               //경영주 이름
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$p_upso_select_old
    //##**$$sel_sigu
    /*
    */
    public DOBJ CTLsel_sigu(DOBJ dobj)
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
            dobj  = CALLsel_sigu_SEL1(Conn, dobj);           //  SEL
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
    public DOBJ CTLsel_sigu( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_sigu_SEL1(Conn, dobj);           //  SEL
        return dobj;
    }
    // SEL
    public DOBJ CALLsel_sigu_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_sigu_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_sigu_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  index_ffs(FIDU.TENV_POST)  */  atte  ,  citycntydsrc  FROM  FIDU.TENV_POST  group  by  citycntydsrc,  atte  order  by  atte,  citycntydsrc ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$sel_sigu
    //##**$$end
}
