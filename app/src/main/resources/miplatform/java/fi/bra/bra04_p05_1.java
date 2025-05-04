
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_p05_1
{
    public bra04_p05_1()
    {
    }
    //##**$$day_rept_list
    /*
    */
    public DOBJ CTLday_rept_list(DOBJ dobj)
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
            dobj  = CALLday_rept_list_SEL1(Conn, dobj);           //  리스트
            dobj  = CALLday_rept_list_SEL2(Conn, dobj);           //  타지부 분배분 요약
            dobj  = CALLday_rept_list_SEL3(Conn, dobj);           //  가상계좌 실입금액 조회
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
    public DOBJ CTLday_rept_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLday_rept_list_SEL1(Conn, dobj);           //  리스트
        dobj  = CALLday_rept_list_SEL2(Conn, dobj);           //  타지부 분배분 요약
        dobj  = CALLday_rept_list_SEL3(Conn, dobj);           //  가상계좌 실입금액 조회
        return dobj;
    }
    // 리스트
    public DOBJ CALLday_rept_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLday_rept_list_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLday_rept_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TO_ZIP = dobj.getRetObject("S").getRecord().get("TO_ZIP");   //우편번호 검색 TO
        String   FROM_ZIP = dobj.getRetObject("S").getRecord().get("FROM_ZIP");   //우편번호 검색 FROM
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   REPT_GBN = dobj.getRetObject("S").getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   START_DATE = dobj.getRetObject("S").getRecord().get("START_DATE");   //협회등록일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DATE = dobj.getRetObject("S").getRecord().get("END_DATE");   //마감 일시
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT --+ optimizer_features_enable('11.1.0.6') \n BRAN_CD , REPT_DAY , REPT_GBN , REPT_NUM , UPSO_CD , UPSO_NM , MNGEMSTR_NM , GRADNM , BSTYP_CD , START_YRMN , END_YRMN , USE_AMT , REPT_GBN_NM , STAFF_NM , REMAK , UPSO_ADDR , GBN , 1 AS AAA , MAPPING_DAY  ";
        query +=" FROM (  ";
        query +=" SELECT XA.BRAN_CD , XA.REPT_DAY , XA.REPT_NUM , XA.REPT_GBN , DECODE(XA.UPSO_CD, NULL, '-', XA.UPSO_CD) UPSO_CD , DECODE(XA.UPSO_CD, NULL, '-', XC.UPSO_NM) UPSO_NM , XC.MNGEMSTR_NM , DECODE(XB.BSTYP_CD, NULL, '-', XB.BSTYP_CD) BSTYP_CD , XB.GRADNM , XC.START_YRMN , XC.END_YRMN , XA.REPT_AMT USE_AMT , XD.CODE_NM REPT_GBN_NM , XC.STAFF_NM , XC.UPSO_ADDR , XA.REMAK , GBN , DECODE(XC.STAFF_CD, NULL, '-', XC.STAFF_CD) STAFF_CD , XA.MAPPING_DAY  ";
        query +=" FROM (  ";
        query +=" SELECT REPT_DAY , REPT_NUM , REPT_GBN , NULL DISTR_SEQ , BRAN_CD , REPT_AMT - COMIS REPT_AMT , UPSO_CD , REMAK , '1' GBN , MAPPING_DAY  ";
        query +=" FROM GIBU.TBRA_REPT  ";
        query +=" WHERE REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if( !START_DATE.equals("") )
        {
            query +=" AND RECV_DAY BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        query +=" AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" AND DISTR_GBN IS NULL  ";
        query +=" UNION ALL  ";
        query +=" SELECT REPT_DAY , REPT_NUM , REPT_GBN , NULL DISTR_SEQ , BRAN_CD , REPT_AMT - COMIS REPT_AMT , UPSO_CD , '지부분배 가수금 (미분배)' REMAK , '2' GBN , MAPPING_DAY  ";
        query +=" FROM GIBU.TBRA_REPT  ";
        query +=" WHERE REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if( !START_DATE.equals("") )
        {
            query +=" AND RECV_DAY BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        query +=" AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" AND DISTR_GBN = '41'  ";
        query +=" AND REPT_DAY || REPT_NUM || REPT_GBN NOT IN (  ";
        query +=" SELECT REPT_DAY || REPT_NUM || REPT_GBN  ";
        query +=" FROM GIBU.TBRA_REPT_DISTR  ";
        query +=" WHERE REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" UNION ALL  ";
        query +=" SELECT REPT_DAY || REPT_NUM || REPT_GBN  ";
        query +=" FROM GIBU.TBRA_REPT_UPSO_DISTR  ";
        query +=" WHERE REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY )  ";
        query +=" UNION ALL  ";
        query +=" SELECT A.REPT_DAY , A.REPT_NUM , A.REPT_GBN , NULL DISTR_SEQ , DECODE(:BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD) , A.DISTR_AMT REPT_AMT , NULL UPSO_CD , C.DEPT_NM || ' 분배분' REMAK , '3' GBN , NULL MAPPING_DAY  ";
        query +=" FROM GIBU.TBRA_REPT_DISTR A , GIBU.TBRA_REPT B , INSA.TCPM_DEPT C  ";
        query +=" WHERE A.REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if( !START_DATE.equals("") )
        {
            query +=" AND B.RECV_DAY BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        query +=" AND A.BRAN_CD <> DECODE(:BRAN_CD, 'AL', B.BRAN_CD, :BRAN_CD)  ";
        query +=" AND B.BRAN_CD = DECODE(:BRAN_CD, 'AL', B.BRAN_CD, :BRAN_CD)  ";
        query +=" AND B.DISTR_GBN = '41'  ";
        query +=" AND A.REPT_DAY = B.REPT_DAY  ";
        query +=" AND A.REPT_NUM = B.REPT_NUM  ";
        query +=" AND A.REPT_GBN = B.REPT_GBN  ";
        query +=" AND C.GIBU = A.BRAN_CD  ";
        query +=" UNION ALL  ";
        query +=" SELECT A.REPT_DAY , A.REPT_NUM , A.REPT_GBN , NULL DISTR_SEQ , A.BRAN_CD , A.DISTR_AMT - NVL(B.REPT_AMT, 0) REPT_AMT , NULL UPSO_CD , DECODE(C.BRAN_CD, A.BRAN_CD, '지부분배 가수금', D.DEPT_NM || '분배금') REMAK , DECODE(C.BRAN_CD, A.BRAN_CD, '4', '5') GBN , NULL MAPPING_DAY  ";
        query +=" FROM GIBU.TBRA_REPT_DISTR A , (  ";
        query +=" SELECT REPT_DAY , REPT_NUM , REPT_GBN , BRAN_CD , SUM(NVL(DISTR_AMT, 0)) REPT_AMT  ";
        query +=" FROM GIBU.TBRA_REPT_UPSO_DISTR  ";
        query +=" WHERE REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" GROUP BY REPT_DAY, REPT_NUM, REPT_GBN, BRAN_CD ) B , GIBU.TBRA_REPT C , INSA.TCPM_DEPT D  ";
        query +=" WHERE A.REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if( !START_DATE.equals("") )
        {
            query +=" AND C.RECV_DAY BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        query +=" AND A.BRAN_CD = DECODE(:BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD)  ";
        query +=" AND B.REPT_DAY(+) = A.REPT_DAY  ";
        query +=" AND B.REPT_NUM(+) = A.REPT_NUM  ";
        query +=" AND B.REPT_GBN(+) = A.REPT_GBN  ";
        query +=" AND B.BRAN_CD(+) = A.BRAN_CD  ";
        query +=" AND C.REPT_DAY = A.REPT_DAY  ";
        query +=" AND C.REPT_NUM = A.REPT_NUM  ";
        query +=" AND C.REPT_GBN = A.REPT_GBN  ";
        query +=" AND D.GIBU = C.BRAN_CD  ";
        query +=" UNION ALL  ";
        query +=" SELECT A.REPT_DAY , A.REPT_NUM , A.REPT_GBN , A.DISTR_SEQ , A.BRAN_CD , A.DISTR_AMT , A.UPSO_CD , A.REMAK , '6' GBN , A.MAPPING_DAY  ";
        query +=" FROM GIBU.TBRA_REPT_UPSO_DISTR A , GIBU.TBRA_REPT_TRANS B  ";
        query +=" WHERE A.REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if( !START_DATE.equals("") )
        {
            query +=" AND B.RECV_DAY BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        query +=" AND A.BRAN_CD = DECODE(:BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD)  ";
        query +=" AND B.REPT_DAY = A.REPT_DAY  ";
        query +=" AND B.REPT_NUM = A.REPT_NUM  ";
        query +=" AND A.REPT_GBN = '03'  ";
        query +=" AND B.DISTR_GBN = '41'  ";
        query +=" UNION ALL  ";
        query +=" SELECT A.REPT_DAY , A.REPT_NUM , A.REPT_GBN , A.DISTR_SEQ , A.BRAN_CD , (CASE WHEN MIN(DISTR_SEQ) OVER(PARTITION BY A.REPT_DAY, A.REPT_NUM, A.REPT_GBN) = DISTR_SEQ THEN A.DISTR_AMT - NVL(C.COMIS, 0) ELSE A.DISTR_AMT END) AS REPT_AMT , A.UPSO_CD , A.REMAK , '6' GBN , A.MAPPING_DAY  ";
        query +=" FROM GIBU.TBRA_REPT_UPSO_DISTR A , GIBU.TBRA_REPT_VIRTUAL B , GIBU.TBRA_REPT C  ";
        query +=" WHERE B.REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if( !START_DATE.equals("") )
        {
            query +=" AND B.RECV_DAY BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        query +=" AND B.REPT_DAY = A.REPT_DAY  ";
        query +=" AND B.REPT_NUM = A.REPT_NUM  ";
        query +=" AND A.REPT_GBN = '08'  ";
        query +=" AND B.DISTR_GBN = '41'  ";
        query +=" AND B.REPT_DAY = C.REPT_DAY  ";
        query +=" AND B.REPT_NUM = C.REPT_NUM  ";
        query +=" AND C.REPT_GBN = '08'  ";
        query +=" UNION ALL  ";
        query +=" SELECT A.REPT_DAY , A.REPT_NUM , A.REPT_GBN , NULL DISTR_SEQ , A.BRAN_CD , (CASE WHEN A.REPT_GBN = '08' THEN (A.REPT_AMT) - NVL(B.REPT_AMT, 0) ELSE (A.REPT_AMT - A.COMIS) - NVL(B.REPT_AMT, 0) END) AS REPT_AMT , NULL UPSO_CD , '업소분배 가수금' REMAK , '7' GBN , A.MAPPING_DAY  ";
        query +=" FROM GIBU.TBRA_REPT A , (  ";
        query +=" SELECT REPT_DAY , REPT_NUM , REPT_GBN , SUM(NVL(DISTR_AMT, 0)) REPT_AMT  ";
        query +=" FROM GIBU.TBRA_REPT_UPSO_DISTR  ";
        query +=" WHERE REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" GROUP BY REPT_DAY, REPT_NUM, REPT_GBN ) B  ";
        query +=" WHERE A.REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if( !START_DATE.equals("") )
        {
            query +=" AND A.RECV_DAY BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        query +=" AND A.BRAN_CD = DECODE(:BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD)  ";
        query +=" AND A.REPT_DAY = B.REPT_DAY(+)  ";
        query +=" AND A.REPT_NUM = B.REPT_NUM(+)  ";
        query +=" AND A.REPT_GBN = B.REPT_GBN(+)  ";
        query +=" AND A.DISTR_GBN = '42'  ";
        query +=" UNION ALL  ";
        query +=" SELECT A.REPT_DAY , A.REPT_NUM , A.REPT_GBN , A.DISTR_SEQ , A.BRAN_CD , NVL(A.DISTR_AMT, 0) REPT_AMT , A.UPSO_CD , A.REMAK , '8' GBN , A.MAPPING_DAY  ";
        query +=" FROM GIBU.TBRA_REPT_UPSO_DISTR A , GIBU.TBRA_REPT_TRANS B  ";
        query +=" WHERE B.REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if( !START_DATE.equals("") )
        {
            query +=" AND B.RECV_DAY BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        query +=" AND B.DISTR_GBN = '42'  ";
        query +=" AND A.BRAN_CD = DECODE(:BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD)  ";
        query +=" AND A.REPT_DAY = B.REPT_DAY  ";
        query +=" AND A.REPT_NUM = B.REPT_NUM  ";
        query +=" AND A.REPT_GBN = '03'  ";
        query +=" UNION ALL  ";
        query +=" SELECT A.REPT_DAY , A.REPT_NUM , A.REPT_GBN , A.DISTR_SEQ , A.BRAN_CD , (CASE WHEN MIN(DISTR_SEQ) OVER(PARTITION BY A.REPT_DAY, A.REPT_NUM, A.REPT_GBN) = DISTR_SEQ THEN A.DISTR_AMT - NVL(C.COMIS, 0) ELSE A.DISTR_AMT END) AS REPT_AMT , A.UPSO_CD , A.REMAK , '8' GBN , A.MAPPING_DAY  ";
        query +=" FROM GIBU.TBRA_REPT_UPSO_DISTR A , GIBU.TBRA_REPT_VIRTUAL B , GIBU.TBRA_REPT C  ";
        query +=" WHERE B.REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if( !START_DATE.equals("") )
        {
            query +=" AND B.RECV_DAY BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        query +=" AND B.DISTR_GBN = '42'  ";
        query +=" AND A.REPT_DAY = B.REPT_DAY  ";
        query +=" AND A.REPT_NUM = B.REPT_NUM  ";
        query +=" AND A.REPT_GBN = '08'  ";
        query +=" AND B.REPT_DAY = C.REPT_DAY  ";
        query +=" AND B.REPT_NUM = C.REPT_NUM  ";
        query +=" AND C.REPT_GBN = '08'  ";
        query +=" UNION ALL  ";
        query +=" SELECT RETURN_DAY , RETURN_NUM , '09' REPT_GBN , NULL , BRAN_CD , RETURN_AMT REPT_AMT , UPSO_CD , REMAK , '9' GBN , RETURN_DAY  ";
        query +=" FROM GIBU.TBRA_REPT_RETURN A  ";
        query +=" WHERE A.RETURN_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" AND A.BRAN_CD = DECODE(:BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD) ) XA , (  ";
        query +=" SELECT ZA.UPSO_CD , ZB.MONPRNCFEE , ZC.GRADNM , ZB.BSTYP_CD , ZB.UPSO_GRAD , ZB.BSTYP_CD || ZB.UPSO_GRAD GRAD , ZB.CHG_NUM  ";
        query +=" FROM (  ";
        query +=" SELECT MAX(JOIN_SEQ) JOIN_SEQ, A.UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND B.BRAN_CD = DECODE(:BRAN_CD, 'AL', B.BRAN_CD, :BRAN_CD)  ";
        query +=" GROUP BY A.UPSO_CD ) ZA , GIBU.TBRA_UPSORTAL_INFO ZB , GIBU.TBRA_BSTYPGRAD ZC  ";
        query +=" WHERE ZA.UPSO_CD = ZB.UPSO_CD  ";
        query +=" AND ZA.JOIN_SEQ = ZB.JOIN_SEQ  ";
        query +=" AND ZC.GRAD_GBN = ZB.BSTYP_CD  ";
        query +=" AND ZC.BSTYP_CD = 'z' ) XB , (  ";
        query +=" SELECT TA.UPSO_CD , TB.UPSO_NM , TB.MNGEMSTR_NM , TB.STAFF_CD , TC.HAN_NM STAFF_NM , TB.UPSO_NEW_ADDR1||' ' ||TB.UPSO_NEW_ADDR2||TB.UPSO_REF_INFO UPSO_ADDR , TA.START_YRMN , TA.END_YRMN , TA.REPT_DAY , TA.REPT_NUM , TA.REPT_GBN  ";
        query +=" FROM (  ";
        query +=" SELECT ZB.UPSO_CD , ZB.REPT_DAY , ZB.REPT_NUM , ZB.REPT_GBN , MIN(ZA.NOTE_YRMN) START_YRMN , MAX(ZA.NOTE_YRMN) END_YRMN  ";
        query +=" FROM GIBU.TBRA_NOTE ZA , (  ";
        query +=" SELECT UPSO_CD , REPT_DAY , REPT_NUM , REPT_GBN  ";
        query +=" FROM GIBU.TBRA_REPT  ";
        query +=" WHERE REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" UNION  ";
        query +=" SELECT UPSO_CD , REPT_DAY , REPT_NUM , REPT_GBN  ";
        query +=" FROM GIBU.TBRA_REPT_UPSO_DISTR  ";
        query +=" WHERE REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD) ) ZB  ";
        query +=" WHERE ZA.UPSO_CD(+) = ZB.UPSO_CD  ";
        query +=" AND ZA.REPT_DAY(+) = ZB.REPT_DAY  ";
        query +=" AND ZA.REPT_NUM(+) = ZB.REPT_NUM  ";
        query +=" AND ZA.REPT_GBN(+) = ZB.REPT_GBN  ";
        query +=" GROUP BY ZB.UPSO_CD, ZB.REPT_DAY, ZB.REPT_NUM, ZB.REPT_GBN  ";
        query +=" UNION ALL  ";
        query +=" SELECT ZB.UPSO_CD , ZB.RETURN_DAY , ZB.RETURN_NUM , '09' REPT_GBN , MIN(ZA.NOTE_YRMN) START_YRMN , MAX(ZA.NOTE_YRMN) END_YRMN  ";
        query +=" FROM GIBU.TBRA_REPT_RETURN_NOTE ZA , GIBU.TBRA_REPT_RETURN ZB  ";
        query +=" WHERE ZB.RETURN_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" AND ZB.BRAN_CD = DECODE(:BRAN_CD, 'AL', ZB.BRAN_CD, :BRAN_CD)  ";
        query +=" AND ZA.RETURN_DAY = ZB.RETURN_DAY  ";
        query +=" AND ZA.RETURN_NUM = ZB.RETURN_NUM  ";
        query +=" GROUP BY ZB.UPSO_CD, ZB.RETURN_DAY, ZB.RETURN_NUM ) TA , GIBU.TBRA_UPSO TB , INSA.TINS_MST01 TC  ";
        query +=" WHERE TA.UPSO_CD = TB.UPSO_CD  ";
        query +=" AND TC.STAFF_NUM(+) = TB.STAFF_CD ) XC , (  ";
        query +=" SELECT CODE_CD , CODE_NM  ";
        query +=" FROM FIDU.TENV_CODE  ";
        query +=" WHERE HIGH_CD = '00141' ) XD  ";
        query +=" WHERE XA.BRAN_CD = DECODE(:BRAN_CD, 'AL', XA.BRAN_CD, :BRAN_CD)  ";
        query +=" AND XB.UPSO_CD(+) = XA.UPSO_CD  ";
        query +=" AND XC.UPSO_CD(+) = XA.UPSO_CD  ";
        query +=" AND XC.REPT_DAY(+) = XA.REPT_DAY  ";
        query +=" AND XC.REPT_NUM(+) = XA.REPT_NUM  ";
        query +=" AND XC.REPT_GBN(+) = XA.REPT_GBN  ";
        query +=" AND XD.CODE_CD = XA.REPT_GBN  ";
        query +=" AND XA.REPT_AMT > 0 )  ";
        query +=" WHERE UPSO_NM IS NOT NULL  ";
        query +=" AND BSTYP_CD = NVL(:BSTYP_CD, BSTYP_CD)  ";
        query +=" AND STAFF_CD = NVL(:STAFF_CD, STAFF_CD)  ";
        query +=" AND UPSO_CD = NVL(:UPSO_CD , UPSO_CD)  ";
        query +=" AND REPT_GBN = NVL(:REPT_GBN, REPT_GBN)  ";
        query +=" AND UPSO_CD IN (  ";
        query +=" SELECT '-' AS UPSO_CD  ";
        query +=" FROM DUAL  ";
        query +=" UNION ALL  ";
        query +=" SELECT UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO  ";
        query +=" WHERE UPSO_NEW_ZIP BETWEEN NVL(:FROM_ZIP, '000000')  ";
        query +=" AND NVL(:TO_ZIP, '999999') )  ";
        query +=" UNION ALL  ";
        query +=" SELECT BRAN_CD , REPT_DAY , REPT_GBN , REPT_NUM , UPSO_CD , UPSO_NM , MNGEMSTR_NM , GRADNM , BSTYP_CD , START_YRMN , END_YRMN , USE_AMT , REPT_GBN_NM , STAFF_NM , REMAK , UPSO_ADDR , GBN , 1 AS AAA , MAPPING_DAY  ";
        query +=" FROM (  ";
        query +=" SELECT XA.BRAN_CD , XA.REPT_DAY , XA.REPT_NUM , XA.REPT_GBN , DECODE(XA.UPSO_CD, NULL, '-', XA.UPSO_CD) UPSO_CD , DECODE(XA.UPSO_CD, NULL, '-', XC.UPSO_NM) UPSO_NM , XC.MNGEMSTR_NM , DECODE(XB.BSTYP_CD, NULL, '-', XB.BSTYP_CD) BSTYP_CD , XB.GRADNM , XC.START_YRMN , XC.END_YRMN , XA.REPT_AMT USE_AMT , XD.CODE_NM REPT_GBN_NM , XC.STAFF_NM , XC.UPSO_ADDR , XA.REMAK , GBN , DECODE(XC.STAFF_CD, NULL, '-', XC.STAFF_CD) STAFF_CD , XA.MAPPING_DAY  ";
        query +=" FROM (  ";
        query +=" SELECT REPT_DAY , REPT_NUM , REPT_GBN , NULL DISTR_SEQ , BRAN_CD , REPT_AMT - COMIS REPT_AMT , UPSO_CD , REMAK , '1' GBN , MAPPING_DAY  ";
        query +=" FROM GIBU.TBRA_REPT  ";
        query +=" WHERE REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if( !START_DATE.equals("") )
        {
            query +=" AND RECV_DAY BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        query +=" AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" AND DISTR_GBN IS NULL  ";
        query +=" AND REPT_AMT - COMIS < 0  ";
        query +=" AND REPT_GBN = '08'  ";
        query +=" UNION ALL  ";
        query +=" SELECT A.REPT_DAY , A.REPT_NUM , A.REPT_GBN , A.DISTR_SEQ , A.BRAN_CD , (CASE WHEN MIN(DISTR_SEQ) OVER(PARTITION BY A.REPT_DAY, A.REPT_NUM, A.REPT_GBN) = DISTR_SEQ THEN A.DISTR_AMT - NVL(C.COMIS, 0) ELSE A.DISTR_AMT END) AS REPT_AMT , A.UPSO_CD , A.REMAK , '6' GBN , A.MAPPING_DAY  ";
        query +=" FROM GIBU.TBRA_REPT_UPSO_DISTR A , GIBU.TBRA_REPT_VIRTUAL B , GIBU.TBRA_REPT C  ";
        query +=" WHERE B.REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if( !START_DATE.equals("") )
        {
            query +=" AND B.RECV_DAY BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        query +=" AND B.REPT_DAY = A.REPT_DAY  ";
        query +=" AND B.REPT_NUM = A.REPT_NUM  ";
        query +=" AND A.REPT_GBN = '08'  ";
        query +=" AND B.DISTR_GBN = '41'  ";
        query +=" AND B.REPT_DAY = C.REPT_DAY  ";
        query +=" AND B.REPT_NUM = C.REPT_NUM  ";
        query +=" AND C.REPT_GBN = '08'  ";
        query +=" UNION ALL  ";
        query +=" SELECT A.REPT_DAY , A.REPT_NUM , A.REPT_GBN , A.DISTR_SEQ , A.BRAN_CD , (CASE WHEN MIN(DISTR_SEQ) OVER(PARTITION BY A.REPT_DAY, A.REPT_NUM, A.REPT_GBN) = DISTR_SEQ THEN A.DISTR_AMT - NVL(C.COMIS, 0) ELSE A.DISTR_AMT END) AS REPT_AMT , A.UPSO_CD , A.REMAK , '8' GBN , A.MAPPING_DAY  ";
        query +=" FROM GIBU.TBRA_REPT_UPSO_DISTR A , GIBU.TBRA_REPT_VIRTUAL B , GIBU.TBRA_REPT C  ";
        query +=" WHERE B.REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if( !START_DATE.equals("") )
        {
            query +=" AND B.RECV_DAY BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        query +=" AND B.DISTR_GBN = '42'  ";
        query +=" AND A.REPT_DAY = B.REPT_DAY  ";
        query +=" AND A.REPT_NUM = B.REPT_NUM  ";
        query +=" AND A.REPT_GBN = '08'  ";
        query +=" AND A.REPT_DAY = C.REPT_DAY(+)  ";
        query +=" AND A.REPT_NUM = C.REPT_NUM(+)  ";
        query +=" AND C.REPT_GBN(+) = '08' ) XA , (  ";
        query +=" SELECT ZA.UPSO_CD , ZB.MONPRNCFEE , ZC.GRADNM , ZB.BSTYP_CD , ZB.UPSO_GRAD , ZB.BSTYP_CD || ZB.UPSO_GRAD GRAD , ZB.CHG_NUM  ";
        query +=" FROM (  ";
        query +=" SELECT MAX(JOIN_SEQ) JOIN_SEQ, A.UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND B.BRAN_CD = DECODE(:BRAN_CD, 'AL', B.BRAN_CD, :BRAN_CD)  ";
        query +=" GROUP BY A.UPSO_CD ) ZA , GIBU.TBRA_UPSORTAL_INFO ZB , GIBU.TBRA_BSTYPGRAD ZC  ";
        query +=" WHERE ZA.UPSO_CD = ZB.UPSO_CD  ";
        query +=" AND ZA.JOIN_SEQ = ZB.JOIN_SEQ  ";
        query +=" AND ZC.GRAD_GBN = ZB.BSTYP_CD  ";
        query +=" AND ZC.BSTYP_CD = 'z' ) XB , (  ";
        query +=" SELECT TA.UPSO_CD , TB.UPSO_NM , TB.MNGEMSTR_NM , TB.STAFF_CD , TC.HAN_NM STAFF_NM , TB.UPSO_NEW_ADDR1||' ' ||TB.UPSO_NEW_ADDR2||TB.UPSO_REF_INFO UPSO_ADDR , TA.START_YRMN , TA.END_YRMN , TA.REPT_DAY , TA.REPT_NUM , TA.REPT_GBN  ";
        query +=" FROM (  ";
        query +=" SELECT ZB.UPSO_CD , ZB.REPT_DAY , ZB.REPT_NUM , ZB.REPT_GBN , MIN(ZA.NOTE_YRMN) START_YRMN , MAX(ZA.NOTE_YRMN) END_YRMN  ";
        query +=" FROM GIBU.TBRA_NOTE ZA , (  ";
        query +=" SELECT UPSO_CD , REPT_DAY , REPT_NUM , REPT_GBN  ";
        query +=" FROM GIBU.TBRA_REPT  ";
        query +=" WHERE REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" UNION  ";
        query +=" SELECT UPSO_CD , REPT_DAY , REPT_NUM , REPT_GBN  ";
        query +=" FROM GIBU.TBRA_REPT_UPSO_DISTR  ";
        query +=" WHERE REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD) ) ZB  ";
        query +=" WHERE ZA.UPSO_CD(+) = ZB.UPSO_CD  ";
        query +=" AND ZA.REPT_DAY(+) = ZB.REPT_DAY  ";
        query +=" AND ZA.REPT_NUM(+) = ZB.REPT_NUM  ";
        query +=" AND ZA.REPT_GBN(+) = ZB.REPT_GBN  ";
        query +=" GROUP BY ZB.UPSO_CD, ZB.REPT_DAY, ZB.REPT_NUM, ZB.REPT_GBN  ";
        query +=" UNION ALL  ";
        query +=" SELECT ZB.UPSO_CD , ZB.RETURN_DAY , ZB.RETURN_NUM , '09' REPT_GBN , MIN(ZA.NOTE_YRMN) START_YRMN , MAX(ZA.NOTE_YRMN) END_YRMN  ";
        query +=" FROM GIBU.TBRA_REPT_RETURN_NOTE ZA , GIBU.TBRA_REPT_RETURN ZB  ";
        query +=" WHERE ZB.RETURN_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" AND ZB.BRAN_CD = DECODE(:BRAN_CD, 'AL', ZB.BRAN_CD, :BRAN_CD)  ";
        query +=" AND ZA.RETURN_DAY = ZB.RETURN_DAY  ";
        query +=" AND ZA.RETURN_NUM = ZB.RETURN_NUM  ";
        query +=" GROUP BY ZB.UPSO_CD, ZB.RETURN_DAY, ZB.RETURN_NUM ) TA , GIBU.TBRA_UPSO TB , INSA.TINS_MST01 TC  ";
        query +=" WHERE TA.UPSO_CD = TB.UPSO_CD  ";
        query +=" AND TC.STAFF_NUM(+) = TB.STAFF_CD ) XC , (  ";
        query +=" SELECT CODE_CD , CODE_NM  ";
        query +=" FROM FIDU.TENV_CODE  ";
        query +=" WHERE HIGH_CD = '00141' ) XD  ";
        query +=" WHERE XA.BRAN_CD = DECODE(:BRAN_CD, 'AL', XA.BRAN_CD, :BRAN_CD)  ";
        query +=" AND XB.UPSO_CD(+) = XA.UPSO_CD  ";
        query +=" AND XC.UPSO_CD(+) = XA.UPSO_CD  ";
        query +=" AND XC.REPT_DAY(+) = XA.REPT_DAY  ";
        query +=" AND XC.REPT_NUM(+) = XA.REPT_NUM  ";
        query +=" AND XC.REPT_GBN(+) = XA.REPT_GBN  ";
        query +=" AND XD.CODE_CD = XA.REPT_GBN  ";
        query +=" AND XA.REPT_AMT < 0 )  ";
        query +=" WHERE UPSO_NM IS NOT NULL  ";
        query +=" AND BSTYP_CD = NVL(:BSTYP_CD, BSTYP_CD)  ";
        query +=" AND STAFF_CD = NVL(:STAFF_CD, STAFF_CD)  ";
        query +=" AND UPSO_CD = NVL(:UPSO_CD , UPSO_CD)  ";
        query +=" AND REPT_GBN = NVL(:REPT_GBN, REPT_GBN)  ";
        query +=" AND UPSO_CD IN (  ";
        query +=" SELECT '-' AS UPSO_CD  ";
        query +=" FROM DUAL  ";
        query +=" UNION ALL  ";
        query +=" SELECT UPSO_CD  ";
        query +=" FROM GIBU.TBRA_UPSO  ";
        query +=" WHERE UPSO_NEW_ZIP BETWEEN NVL(:FROM_ZIP, '000000')  ";
        query +=" AND NVL(:TO_ZIP, '999999') )  ";
        query +=" ORDER BY REPT_DAY, MAPPING_DAY  ";
        sobj.setSql(query);
        sobj.setString("TO_ZIP", TO_ZIP);               //우편번호 검색 TO
        sobj.setString("FROM_ZIP", FROM_ZIP);               //우편번호 검색 FROM
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        if(!START_DATE.equals(""))
        {
            sobj.setString("START_DATE", START_DATE);               //협회등록일
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DATE", END_DATE);               //마감 일시
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 타지부 분배분 요약
    public DOBJ CALLday_rept_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLday_rept_list_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLday_rept_list_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   START_DATE = dobj.getRetObject("S").getRecord().get("START_DATE");   //협회등록일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DATE = dobj.getRetObject("S").getRecord().get("END_DATE");   //마감 일시
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT --+ optimizer_features_enable('11.1.0.6') \n A.BRAN_CD , C.DEPT_NM BRAN_NM , SUM(A.DISTR_AMT) REPT_AMT  ";
        query +=" FROM GIBU.TBRA_REPT_DISTR A , GIBU.TBRA_REPT B , INSA.TCPM_DEPT C  ";
        query +=" WHERE A.REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if( !START_DATE.equals("") )
        {
            query +=" AND B.RECV_DAY BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        query +=" AND A.BRAN_CD <> :BRAN_CD  ";
        query +=" AND B.DISTR_GBN = '41'  ";
        query +=" AND A.REPT_DAY = B.REPT_DAY  ";
        query +=" AND A.REPT_NUM = B.REPT_NUM  ";
        query +=" AND A.REPT_GBN = B.REPT_GBN  ";
        query +=" AND C.GIBU = A.BRAN_CD  ";
        query +=" GROUP BY A.BRAN_CD, C.DEPT_NM  ";
        query +=" ORDER BY BRAN_CD  ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        if(!START_DATE.equals(""))
        {
            sobj.setString("START_DATE", START_DATE);               //협회등록일
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DATE", END_DATE);               //마감 일시
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 가상계좌 실입금액 조회
    public DOBJ CALLday_rept_list_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLday_rept_list_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLday_rept_list_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   START_DATE = dobj.getRetObject("S").getRecord().get("START_DATE");   //협회등록일
        String   END_DATE = dobj.getRetObject("S").getRecord().get("END_DATE");   //마감 일시
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT --+ optimizer_features_enable('11.1.0.6') \n SUM(A.REPT_AMT) AS REPT_AMT , SUM(B.COMIS) AS COMIS , SUM(A.REPT_AMT) - SUM(B.COMIS) AS USE_AMT  ";
        query +=" FROM GIBU.TBRA_REPT_VIRTUAL A , GIBU.TBRA_REPT B  ";
        query +=" WHERE A.REPT_DAY = B.REPT_DAY  ";
        query +=" AND A.REPT_NUM = B.REPT_NUM  ";
        query +=" AND B.REPT_GBN = '08'  ";
        query +=" AND A.REPT_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if( !START_DATE.equals("") )
        {
            query +=" AND A.RECV_DAY BETWEEN :START_DATE  ";
            query +=" AND :END_DATE  ";
        }
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        if(!START_DATE.equals(""))
        {
            sobj.setString("START_DATE", START_DATE);               //협회등록일
        }
        sobj.setString("END_DATE", END_DATE);               //마감 일시
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$day_rept_list
    //##**$$end
}
