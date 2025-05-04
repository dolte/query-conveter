
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s25
{
    public bra04_s25()
    {
    }
    //##**$$sel_virtual_mng
    /*
    */
    public DOBJ CTLsel_virtual_mng(DOBJ dobj)
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
            dobj  = CALLsel_virtual_mng_SEL1(Conn, dobj);           //  ��������Ա�ó��������ȸ
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
    public DOBJ CTLsel_virtual_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_virtual_mng_SEL1(Conn, dobj);           //  ��������Ա�ó��������ȸ
        return dobj;
    }
    // ��������Ա�ó��������ȸ
    public DOBJ CALLsel_virtual_mng_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_virtual_mng_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_virtual_mng_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        String   GBN = dobj.getRetObject("S").getRecord().get("GBN");   //����
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BRAN_NM , BRAN_CD , REPT_DAY , REPT_NUM , MAPPING_DAY , RECV_DAY , UPSO_CD , UPSO_NM , REPTPRES , ACCU_YN , DEMD_YRMN , DEMD_TERM , DEMD_AMT , TOT_USE_AMT , REPT_AMT , COMIS , STAFF_CD , STAFF_NM , REMAK , REPT_GBN , CLSBS_YRMN , BALANCE , CLAIM_CNT , DISTR_GBN , MONPRNCFEE , START_YRMN , END_YRMN , MNGEMSTR_NM , (CASE WHEN CLSED_START1 IS NOT NULL  ";
        query +=" AND LENGTH(CLSED_START1) > 10 THEN CLSED_START1 || CHR(13) || '~ ' || CLSED_END1 WHEN CLSED_START1 IS NOT NULL  ";
        query +=" AND LENGTH(CLSED_START1) = 10 THEN CLSED_START1 || ' ~ ' || CLSED_END1 ELSE '' END) AS CLSED_TERM , (CASE WHEN SUBSTR(:START_DAY, 1, 6) = SUBSTR(CLSED_START, 1, 6)  ";
        query +=" AND LENGTH(CLSED_START) = 8  ";
        query +=" AND SUBSTR(:START_DAY, 1, 6) = SUBSTR(CLSED_END, 1, 6)  ";
        query +=" AND LENGTH(CLSED_START) = 8  ";
        query +=" AND LENGTH(CLSED_END) = 8 THEN (CASE WHEN ((LAST_DAY(TO_DATE(CLSED_START,'YYYYMMDD')) - TO_DATE(SUBSTR(CLSED_START, 1, 6) || '01','YYYYMMDD') + 1) - (TO_DATE(CLSED_END,'YYYYMMDD') - TO_DATE(CLSED_START,'YYYYMMDD') + 1) >= 0)  ";
        query +=" AND ((LAST_DAY(TO_DATE(CLSED_START,'YYYYMMDD')) - TO_DATE(SUBSTR(CLSED_START, 1, 6) || '01','YYYYMMDD') + 1) - (TO_DATE(CLSED_END,'YYYYMMDD') - TO_DATE(CLSED_START,'YYYYMMDD') + 1) < 10) THEN '����1' WHEN ((LAST_DAY(TO_DATE(CLSED_START,'YYYYMMDD')) - TO_DATE(SUBSTR(CLSED_START, 1, 6) || '01','YYYYMMDD') + 1) - (TO_DATE(CLSED_END,'YYYYMMDD') - TO_DATE(CLSED_START,'YYYYMMDD') + 1) >= 10)  ";
        query +=" AND ((LAST_DAY(TO_DATE(CLSED_START,'YYYYMMDD')) - TO_DATE(SUBSTR(CLSED_START, 1, 6) || '01','YYYYMMDD') + 1) - (TO_DATE(CLSED_END,'YYYYMMDD') - TO_DATE(CLSED_START,'YYYYMMDD') + 1) <= 20) THEN '�ݾ�1' ELSE '����1' END) WHEN SUBSTR(:START_DAY, 1, 6) = SUBSTR(CLSED_START, 1, 6)  ";
        query +=" AND LENGTH(CLSED_START) = 8  ";
        query +=" AND LENGTH(CLSED_END) = 8 THEN (CASE WHEN ((LAST_DAY(TO_DATE(CLSED_START,'YYYYMMDD')) - TO_DATE(SUBSTR(CLSED_START, 1, 6) || '01','YYYYMMDD') + 1) - (LAST_DAY(TO_DATE(CLSED_START,'YYYYMMDD')) - TO_DATE(CLSED_START,'YYYYMMDD') + 1) >= 0)  ";
        query +=" AND ((LAST_DAY(TO_DATE(CLSED_START,'YYYYMMDD')) - TO_DATE(SUBSTR(CLSED_START, 1, 6) || '01','YYYYMMDD') + 1) - (LAST_DAY(TO_DATE(CLSED_START,'YYYYMMDD')) - TO_DATE(CLSED_START,'YYYYMMDD') + 1) < 10) THEN '����2' WHEN ((LAST_DAY(TO_DATE(CLSED_START,'YYYYMMDD')) - TO_DATE(SUBSTR(CLSED_START, 1, 6) || '01','YYYYMMDD') + 1) - (LAST_DAY(TO_DATE(CLSED_START,'YYYYMMDD')) - TO_DATE(CLSED_START,'YYYYMMDD') + 1) >= 10)  ";
        query +=" AND ((LAST_DAY(TO_DATE(CLSED_START,'YYYYMMDD')) - TO_DATE(SUBSTR(CLSED_START, 1, 6) || '01','YYYYMMDD') + 1) - (LAST_DAY(TO_DATE(CLSED_START,'YYYYMMDD')) - TO_DATE(CLSED_START,'YYYYMMDD') + 1) <= 20) THEN '�ݾ�2' ELSE '����2' END) WHEN SUBSTR(:START_DAY, 1, 6) = SUBSTR(CLSED_END, 1, 6)  ";
        query +=" AND LENGTH(CLSED_START) = 8  ";
        query +=" AND LENGTH(CLSED_END) = 8 THEN (CASE WHEN ((LAST_DAY(TO_DATE(CLSED_END,'YYYYMMDD')) - TO_DATE(CLSED_END,'YYYYMMDD') + 1) >= 0)  ";
        query +=" AND ((LAST_DAY(TO_DATE(CLSED_END,'YYYYMMDD')) - TO_DATE(CLSED_END,'YYYYMMDD') + 1) < 10) THEN '����3' WHEN ((LAST_DAY(TO_DATE(CLSED_END,'YYYYMMDD')) - TO_DATE(CLSED_END,'YYYYMMDD') + 1) >= 10)  ";
        query +=" AND ((LAST_DAY(TO_DATE(CLSED_END,'YYYYMMDD')) - TO_DATE(CLSED_END,'YYYYMMDD') + 1) <= 20) THEN '�ݾ�3' ELSE '����3' END) WHEN SUBSTR(:START_DAY, 1, 6) > SUBSTR(CLSED_START, 1, 6)  ";
        query +=" AND SUBSTR(:START_DAY, 1, 6) < SUBSTR(CLSED_END, 1, 6)  ";
        query +=" AND LENGTH(CLSED_START) = 8  ";
        query +=" AND LENGTH(CLSED_END) = 8 THEN '����4' ELSE '����4' END) AS DSCT_YN , GIBU.FT_GET_BSTYPGRAD_NM(UPSO_CD, 'NM') AS BSTYP_NM  ";
        query +=" FROM (  ";
        query +=" SELECT GIBU.GET_BRAN_NM(B.BRAN_CD) AS BRAN_NM , B.BRAN_CD , A.REPT_DAY , A.REPT_NUM , A.MAPPING_DAY , A.RECV_DAY , B.UPSO_CD , B.UPSO_NM , D.REMAK AS REPTPRES , (CASE WHEN (SELECT COUNT(1)  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND COMPN_DAY IS NULL) > 0 THEN 'Y' ELSE 'N' END) AS ACCU_YN , C.DEMD_YRMN , TO_CHAR(TO_DATE(C.START_YRMN, 'YYYYMM'), 'YYYY/MM') || '~' || TO_CHAR(TO_DATE(C.END_YRMN, 'YYYYMM'), 'YYYY/MM') AS DEMD_TERM , TOT_DEMD_AMT AS DEMD_AMT , TOT_USE_AMT , A.REPT_AMT , A.COMIS , B.STAFF_CD , FIDU.GET_STAFF_NM(B.STAFF_CD) AS STAFF_NM , A.REMAK , '08' AS REPT_GBN , B.CLSBS_YRMN , (  ";
        query +=" SELECT --+ INDEX_DESC(TBRA_REPT_BALANCE TBRA_REPT_BALANCE_IDX_PK) \n NVL(SUM(BALANCE), 0) AS BALANCE  ";
        query +=" FROM GIBU.TBRA_REPT_BALANCE  ";
        query +=" WHERE UPSO_CD = B.UPSO_CD  ";
        query +=" AND ROWNUM = 1 ) AS BALANCE , (  ";
        query +=" SELECT COUNT(UPSO_CD) CLAIM_CNT  ";
        query +=" FROM GIBU.TBRA_DLGTN_CLAIM  ";
        query +=" WHERE UPSO_CD = B.UPSO_CD  ";
        query +=" AND COMPN_DAY IS NULL ) AS CLAIM_CNT , A.DISTR_GBN , NVL(C.MONPRNCFEE, E.MONPRNCFEE) AS MONPRNCFEE , (CASE WHEN A.MAPPING_DAY IS NOT NULL THEN (SELECT MIN(NOTE_YRMN) || '01'  ";
        query +=" FROM GIBU.TBRA_NOTE  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND REPT_DAY = A.REPT_DAY  ";
        query +=" AND REPT_NUM = A.REPT_NUM  ";
        query +=" AND REPT_GBN = '08') ELSE '' END) AS START_YRMN , (CASE WHEN A.MAPPING_DAY IS NOT NULL THEN (SELECT MAX(NOTE_YRMN) || '01'  ";
        query +=" FROM GIBU.TBRA_NOTE  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND REPT_DAY = A.REPT_DAY  ";
        query +=" AND REPT_NUM = A.REPT_NUM  ";
        query +=" AND REPT_GBN = '08') ELSE '' END) AS END_YRMN , DECODE(B.PAYPRES_GBN, 'B', B.MNGEMSTR_NM, B.PERMMSTR_NM) AS MNGEMSTR_NM , (SELECT LISTAGG(START_DAY, ',') WITHIN  ";
        query +=" GROUP (ORDER BY START_DAY)  ";
        query +=" FROM GIBU.TBRA_UPSO_CLSED  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND (SUBSTR(:START_DAY, 1, 6) BETWEEN SUBSTR(START_DAY, 1, 6)  ";
        query +=" AND SUBSTR(END_DAY, 1, 6)  ";
        query +=" OR SUBSTR(START_DAY, 1, 6) BETWEEN C.START_YRMN  ";
        query +=" AND C.END_YRMN)) AS CLSED_START2 , (SELECT LISTAGG(END_DAY, ',') WITHIN  ";
        query +=" GROUP (ORDER BY END_DAY)  ";
        query +=" FROM GIBU.TBRA_UPSO_CLSED  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND (SUBSTR(:START_DAY, 1, 6) BETWEEN SUBSTR(START_DAY, 1, 6)  ";
        query +=" AND SUBSTR(END_DAY, 1, 6)  ";
        query +=" OR SUBSTR(START_DAY, 1, 6) BETWEEN C.START_YRMN  ";
        query +=" AND C.END_YRMN)) AS CLSED_END2 , (SELECT MAX(START_DAY)  ";
        query +=" FROM GIBU.TBRA_UPSO_CLSED  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND (SUBSTR(:START_DAY, 1, 6) BETWEEN SUBSTR(START_DAY, 1, 6)  ";
        query +=" AND SUBSTR(END_DAY, 1, 6)  ";
        query +=" OR SUBSTR(START_DAY, 1, 6) BETWEEN C.START_YRMN  ";
        query +=" AND C.END_YRMN)) AS CLSED_START , (SELECT MAX(END_DAY)  ";
        query +=" FROM GIBU.TBRA_UPSO_CLSED  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND (SUBSTR(:START_DAY, 1, 6) BETWEEN SUBSTR(START_DAY, 1, 6)  ";
        query +=" AND SUBSTR(END_DAY, 1, 6)  ";
        query +=" OR SUBSTR(START_DAY, 1, 6) BETWEEN C.START_YRMN  ";
        query +=" AND C.END_YRMN)) AS CLSED_END , (SELECT LISTAGG(TO_CHAR(TO_DATE(START_DAY, 'YYYYMMDD'), 'YYYY/MM/DD'), ',') WITHIN  ";
        query +=" GROUP (ORDER BY START_DAY)  ";
        query +=" FROM GIBU.TBRA_UPSO_CLSED  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND (SUBSTR(:START_DAY, 1, 6) BETWEEN SUBSTR(START_DAY, 1, 6)  ";
        query +=" AND SUBSTR(END_DAY, 1, 6)  ";
        query +=" OR SUBSTR(START_DAY, 1, 6) BETWEEN C.START_YRMN  ";
        query +=" AND C.END_YRMN)) AS CLSED_START1 , (SELECT LISTAGG(TO_CHAR(TO_DATE(END_DAY, 'YYYYMMDD'), 'YYYY/MM/DD'), ',') WITHIN  ";
        query +=" GROUP (ORDER BY END_DAY)  ";
        query +=" FROM GIBU.TBRA_UPSO_CLSED  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND (SUBSTR(:START_DAY, 1, 6) BETWEEN SUBSTR(START_DAY, 1, 6)  ";
        query +=" AND SUBSTR(END_DAY, 1, 6)  ";
        query +=" OR SUBSTR(START_DAY, 1, 6) BETWEEN C.START_YRMN  ";
        query +=" AND C.END_YRMN)) AS CLSED_END1  ";
        query +=" FROM GIBU.TBRA_REPT_VIRTUAL A , GIBU.TBRA_UPSO B , (SELECT XA.UPSO_CD , MAX(XB.MONPRNCFEE) AS MONPRNCFEE , MAX(XB.TOT_DEMD_AMT) AS TOT_DEMD_AMT , MAX(XB.TOT_USE_AMT) AS TOT_USE_AMT , MAX(XB.START_YRMN) AS START_YRMN , MAX(XB.END_YRMN) AS END_YRMN , MAX(XB.DEMD_YRMN) AS DEMD_YRMN  ";
        query +=" FROM GIBU.TBRA_REPT_VIRTUAL XA , GIBU.TBRA_DEMD_OCR XB  ";
        query +=" WHERE XA.UPSO_CD = XB.UPSO_CD  ";
        query +=" AND XB.DEMD_YRMN = (SELECT MAX(DEMD_YRMN)  ";
        query +=" FROM GIBU.TBRA_DEMD_OCR  ";
        query +=" WHERE UPSO_CD = XA.UPSO_CD  ";
        query +=" AND DEMD_YRMN >= TO_CHAR(ADD_MONTHS(:START_DAY, -2), 'YYYYMM'))  ";
        query +=" AND XA.RECV_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" AND XA.BRAN_CD = DECODE(:BRAN_CD, 'AL', XA.BRAN_CD, :BRAN_CD)  ";
        query +=" GROUP BY XA.UPSO_CD ) C , GIBU.TBRA_REPT D , (  ";
        query +=" SELECT UPSO_CD, GIBU.FT_SPLIT(GIBU.FT_GET_DEMD_MONPRNCFEE(UPSO_CD, SUBSTR(:START_DAY, 1, 6)), ',', 0) AS MONPRNCFEE  ";
        query +=" FROM GIBU.TBRA_REPT_VIRTUAL  ";
        query +=" WHERE BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" AND RECV_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" GROUP BY UPSO_CD ) E  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.UPSO_CD = C.UPSO_CD(+)  ";
        query +=" AND A.REPT_DAY = D.REPT_DAY  ";
        query +=" AND A.REPT_NUM = D.REPT_NUM  ";
        query +=" AND D.REPT_GBN = '08'  ";
        query +=" AND A.RECV_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" AND A.BRAN_CD = DECODE(:BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD)  ";
        query +=" AND ( (A.MAPPING_DAY IS NULL  ";
        query +=" AND :GBN = 'E')  ";
        query +=" OR (A.MAPPING_DAY IS NOT NULL  ";
        query +=" AND :GBN = 'R')  ";
        query +=" OR :GBN IS NULL)  ";
        query +=" AND A.DISTR_GBN IS NULL  ";
        query +=" AND A.UPSO_CD = E.UPSO_CD  ";
        if( !UPSO_CD.equals("") )
        {
            query +=" AND A.UPSO_CD = :UPSO_CD  ";
        }
        if( !STAFF_CD.equals("") )
        {
            query +=" AND B.STAFF_CD = :STAFF_CD  ";
        }
        query +=" UNION ALL  ";
        query +=" SELECT BRAN_NM , BRAN_CD , REPT_DAY , REPT_NUM , MAPPING_DAY , RECV_DAY , UPSO_CD , UPSO_NM , REPTPRES , ACCU_YN , DEMD_YRMN , DEMD_TERM , DEMD_AMT , TOT_USE_AMT , REPT_AMT , COMIS , STAFF_CD , STAFF_NM , REMAK , REPT_GBN , CLSBS_YRMN , BALANCE , CLAIM_CNT , DISTR_GBN , MONPRNCFEE , START_YRMN , END_YRMN , MNGEMSTR_NM , (SELECT LISTAGG(START_DAY, ',') WITHIN  ";
        query +=" GROUP (ORDER BY START_DAY)  ";
        query +=" FROM GIBU.TBRA_UPSO_CLSED  ";
        query +=" WHERE UPSO_CD = XA.UPSO_CD  ";
        query +=" AND (SUBSTR(:START_DAY, 1, 6) BETWEEN SUBSTR(START_DAY, 1, 6)  ";
        query +=" AND SUBSTR(END_DAY, 1, 6)  ";
        query +=" OR SUBSTR(START_DAY, 1, 6) BETWEEN ST_YRMN1  ";
        query +=" AND ED_YRMN1)) AS CLSED_START2 , (SELECT LISTAGG(END_DAY, ',') WITHIN  ";
        query +=" GROUP (ORDER BY END_DAY)  ";
        query +=" FROM GIBU.TBRA_UPSO_CLSED  ";
        query +=" WHERE UPSO_CD = XA.UPSO_CD  ";
        query +=" AND (SUBSTR(:START_DAY, 1, 6) BETWEEN SUBSTR(START_DAY, 1, 6)  ";
        query +=" AND SUBSTR(END_DAY, 1, 6)  ";
        query +=" OR SUBSTR(START_DAY, 1, 6) BETWEEN ST_YRMN1  ";
        query +=" AND ED_YRMN1)) AS CLSED_END2 , (SELECT MAX(START_DAY)  ";
        query +=" FROM GIBU.TBRA_UPSO_CLSED  ";
        query +=" WHERE UPSO_CD = XA.UPSO_CD  ";
        query +=" AND (SUBSTR(:START_DAY, 1, 6) BETWEEN SUBSTR(START_DAY, 1, 6)  ";
        query +=" AND SUBSTR(END_DAY, 1, 6)  ";
        query +=" OR SUBSTR(START_DAY, 1, 6) BETWEEN ST_YRMN1  ";
        query +=" AND ED_YRMN1)) AS CLSED_START , (SELECT MAX(END_DAY)  ";
        query +=" FROM GIBU.TBRA_UPSO_CLSED  ";
        query +=" WHERE UPSO_CD = XA.UPSO_CD  ";
        query +=" AND (SUBSTR(:START_DAY, 1, 6) BETWEEN SUBSTR(START_DAY, 1, 6)  ";
        query +=" AND SUBSTR(END_DAY, 1, 6)  ";
        query +=" OR SUBSTR(START_DAY, 1, 6) BETWEEN ST_YRMN1  ";
        query +=" AND ED_YRMN1)) AS CLSED_END , (SELECT LISTAGG(TO_CHAR(TO_DATE(START_DAY, 'YYYYMMDD'), 'YYYY/MM/DD'), ',') WITHIN  ";
        query +=" GROUP (ORDER BY START_DAY)  ";
        query +=" FROM GIBU.TBRA_UPSO_CLSED  ";
        query +=" WHERE UPSO_CD = XA.UPSO_CD  ";
        query +=" AND (SUBSTR(:START_DAY, 1, 6) BETWEEN SUBSTR(START_DAY, 1, 6)  ";
        query +=" AND SUBSTR(END_DAY, 1, 6)  ";
        query +=" OR SUBSTR(START_DAY, 1, 6) BETWEEN ST_YRMN1  ";
        query +=" AND ED_YRMN1)) AS CLSED_START1 , (SELECT LISTAGG(TO_CHAR(TO_DATE(END_DAY, 'YYYYMMDD'), 'YYYY/MM/DD'), ',') WITHIN  ";
        query +=" GROUP (ORDER BY END_DAY)  ";
        query +=" FROM GIBU.TBRA_UPSO_CLSED  ";
        query +=" WHERE UPSO_CD = XA.UPSO_CD  ";
        query +=" AND (SUBSTR(:START_DAY, 1, 6) BETWEEN SUBSTR(START_DAY, 1, 6)  ";
        query +=" AND SUBSTR(END_DAY, 1, 6)  ";
        query +=" OR SUBSTR(START_DAY, 1, 6) BETWEEN ST_YRMN1  ";
        query +=" AND ED_YRMN1)) AS CLSED_END1  ";
        query +=" FROM (  ";
        query +=" SELECT GIBU.GET_BRAN_NM(B.BRAN_CD) AS BRAN_NM , B.BRAN_CD , A.REPT_DAY , A.REPT_NUM , (CASE WHEN SUM(NVL(E.DISTR_AMT, 0)) <> A.REPT_AMT THEN '' ELSE A.REPT_DAY END) AS MAPPING_DAY , A.RECV_DAY , B.UPSO_CD , B.UPSO_NM , D.REMAK AS REPTPRES , (CASE WHEN (SELECT COUNT(1)  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD = B.UPSO_CD  ";
        query +=" AND COMPN_DAY IS NULL) > 0 THEN 'Y' ELSE 'N' END) AS ACCU_YN , C.DEMD_YRMN , TO_CHAR(TO_DATE(C.START_YRMN, 'YYYYMM'), 'YYYY/MM') || '~' || TO_CHAR(TO_DATE(C.END_YRMN, 'YYYYMM'), 'YYYY/MM') AS DEMD_TERM , TOT_DEMD_AMT AS DEMD_AMT , TOT_USE_AMT , A.REPT_AMT , A.COMIS , B.STAFF_CD , FIDU.GET_STAFF_NM(B.STAFF_CD) AS STAFF_NM , A.REMAK , '08' AS REPT_GBN , B.CLSBS_YRMN , (  ";
        query +=" SELECT --+ INDEX_DESC(TBRA_REPT_BALANCE TBRA_REPT_BALANCE_IDX_PK) \n NVL(SUM(BALANCE), 0) AS BALANCE  ";
        query +=" FROM GIBU.TBRA_REPT_BALANCE  ";
        query +=" WHERE UPSO_CD = B.UPSO_CD  ";
        query +=" AND ROWNUM = 1 ) AS BALANCE , (  ";
        query +=" SELECT COUNT(UPSO_CD) CLAIM_CNT  ";
        query +=" FROM GIBU.TBRA_DLGTN_CLAIM  ";
        query +=" WHERE UPSO_CD = B.UPSO_CD  ";
        query +=" AND COMPN_DAY IS NULL ) AS CLAIM_CNT , A.DISTR_GBN , C.MONPRNCFEE , (CASE WHEN A.MAPPING_DAY IS NOT NULL THEN (SELECT MIN(NOTE_YRMN) || '01'  ";
        query +=" FROM GIBU.TBRA_NOTE  ";
        query +=" WHERE UPSO_CD = B.UPSO_CD  ";
        query +=" AND REPT_DAY = A.REPT_DAY  ";
        query +=" AND REPT_NUM = A.REPT_NUM  ";
        query +=" AND REPT_GBN = '08') ELSE '' END) AS START_YRMN , (CASE WHEN A.MAPPING_DAY IS NOT NULL THEN (SELECT MAX(NOTE_YRMN) || '01'  ";
        query +=" FROM GIBU.TBRA_NOTE  ";
        query +=" WHERE UPSO_CD = B.UPSO_CD  ";
        query +=" AND REPT_DAY = A.REPT_DAY  ";
        query +=" AND REPT_NUM = A.REPT_NUM  ";
        query +=" AND REPT_GBN = '08') ELSE '' END) AS END_YRMN , SUM(E.DISTR_AMT) AS DISTR_AMT , DECODE(B.PAYPRES_GBN, 'B', B.MNGEMSTR_NM, B.PERMMSTR_NM) AS MNGEMSTR_NM , C.START_YRMN AS ST_YRMN1 , C.END_YRMN AS ED_YRMN1  ";
        query +=" FROM GIBU.TBRA_REPT_VIRTUAL A , GIBU.TBRA_UPSO B , (SELECT XA.UPSO_CD , MAX(XB.MONPRNCFEE) AS MONPRNCFEE , MAX(XB.TOT_DEMD_AMT) AS TOT_DEMD_AMT , MAX(XB.TOT_USE_AMT) AS TOT_USE_AMT , MAX(XB.START_YRMN) AS START_YRMN , MAX(XB.END_YRMN) AS END_YRMN , MAX(XB.DEMD_YRMN) AS DEMD_YRMN  ";
        query +=" FROM GIBU.TBRA_REPT_VIRTUAL XA , GIBU.TBRA_DEMD_OCR XB  ";
        query +=" WHERE XA.UPSO_CD = XB.UPSO_CD  ";
        query +=" AND XB.DEMD_YRMN = (SELECT MAX(DEMD_YRMN)  ";
        query +=" FROM GIBU.TBRA_DEMD_OCR  ";
        query +=" WHERE UPSO_CD = XA.UPSO_CD  ";
        query +=" AND DEMD_YRMN >= TO_CHAR(ADD_MONTHS(:START_DAY, -2), 'YYYYMM'))  ";
        query +=" AND XA.RECV_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" AND XA.BRAN_CD = DECODE(:BRAN_CD, 'AL', XA.BRAN_CD, :BRAN_CD)  ";
        query +=" GROUP BY XA.UPSO_CD ) C , GIBU.TBRA_REPT D , GIBU.TBRA_REPT_UPSO_DISTR E , (  ";
        query +=" SELECT UPSO_CD, GIBU.FT_SPLIT(GIBU.FT_GET_DEMD_MONPRNCFEE(UPSO_CD, SUBSTR(:START_DAY, 1, 6)), ',', 0) AS MONPRNCFEE  ";
        query +=" FROM GIBU.TBRA_REPT_VIRTUAL  ";
        query +=" WHERE BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        query +=" AND RECV_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" GROUP BY UPSO_CD ) F  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.UPSO_CD = C.UPSO_CD(+)  ";
        query +=" AND A.REPT_DAY = D.REPT_DAY  ";
        query +=" AND A.REPT_NUM = D.REPT_NUM  ";
        query +=" AND D.REPT_GBN = '08'  ";
        query +=" AND A.REPT_DAY = E.REPT_DAY(+)  ";
        query +=" AND A.REPT_NUM = E.REPT_NUM(+)  ";
        query +=" AND E.REPT_GBN(+) = '08'  ";
        query +=" AND A.RECV_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" AND A.BRAN_CD = DECODE(:BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD)  ";
        query +=" AND A.DISTR_GBN IS NOT NULL  ";
        query +=" AND A.UPSO_CD = F.UPSO_CD  ";
        query +=" GROUP BY B.BRAN_CD , A.REPT_DAY , A.REPT_NUM , A.MAPPING_DAY , A.RECV_DAY , B.UPSO_CD , B.UPSO_NM , D.REMAK , C.DEMD_YRMN , C.START_YRMN , C.END_YRMN , TOT_DEMD_AMT , TOT_USE_AMT , A.REPT_AMT , A.COMIS , B.STAFF_CD , A.REMAK , B.CLSBS_YRMN , A.DISTR_GBN , C.MONPRNCFEE , DECODE(B.PAYPRES_GBN, 'B', B.MNGEMSTR_NM, B.PERMMSTR_NM) ) XA  ";
        query +=" WHERE ( (MAPPING_DAY IS NULL  ";
        query +=" AND :GBN = 'E')  ";
        query +=" OR (MAPPING_DAY IS NOT NULL  ";
        query +=" AND :GBN = 'R')  ";
        query +=" OR :GBN IS NULL)  ";
        if( !UPSO_CD.equals("") )
        {
            query +=" AND UPSO_CD = :UPSO_CD  ";
        }
        if( !STAFF_CD.equals("") )
        {
            query +=" AND STAFF_CD = :STAFF_CD  ";
        }
        query +=" )  ";
        query +=" ORDER BY RECV_DAY DESC, BRAN_CD, UPSO_CD  ";
        sobj.setSql(query);
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        }
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("GBN", GBN);               //����
        if(!UPSO_CD.equals(""))
        {
            sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    //##**$$sel_virtual_mng
    //##**$$chk_rept_distr_dup
    /*
    */
    public DOBJ CTLchk_rept_distr_dup(DOBJ dobj)
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
            dobj  = CALLchk_rept_distr_dup_SEL1(Conn, dobj);           //  �й��Աݿ��� ��ȸ
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
    public DOBJ CTLchk_rept_distr_dup( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLchk_rept_distr_dup_SEL1(Conn, dobj);           //  �й��Աݿ��� ��ȸ
        return dobj;
    }
    // �й��Աݿ��� ��ȸ
    public DOBJ CALLchk_rept_distr_dup_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLchk_rept_distr_dup_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_rept_distr_dup_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '08'  GROUP  BY  UPSO_CD,  DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        return sobj;
    }
    //##**$$chk_rept_distr_dup
    //##**$$mng_virtual_distr
    /*
    */
    public DOBJ CTLmng_virtual_distr(DOBJ dobj)
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
            dobj  = CALLmng_virtual_distr_MPD1(Conn, dobj);           //  �б�
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
    public DOBJ CTLmng_virtual_distr( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmng_virtual_distr_MPD1(Conn, dobj);           //  �б�
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // �б�
    public DOBJ CALLmng_virtual_distr_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MPD1");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( 1 == 1)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_virtual_distr_XIUD5(Conn, dobj);           //  �й����� ���
                dobj  = CALLmng_virtual_distr_XIUD6(Conn, dobj);           //  �й����� ���
            }
        }
        return dobj;
    }
    // �й����� ���
    public DOBJ CALLmng_virtual_distr_XIUD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD5");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_virtual_distr_XIUD5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD5");
        rvobj.Println("XIUD5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_virtual_distr_XIUD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");   //�Ա� �й� ����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_VIRTUAL  \n";
        query +=" SET DISTR_GBN = :DISTR_GBN , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE REPT_DAY = :REPT_DAY  \n";
        query +=" AND REPT_NUM = :REPT_NUM";
        sobj.setSql(query);
        sobj.setString("DISTR_GBN", DISTR_GBN);               //�Ա� �й� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        return sobj;
    }
    // �й����� ���
    public DOBJ CALLmng_virtual_distr_XIUD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD6");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD6");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_virtual_distr_XIUD6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD6");
        rvobj.Println("XIUD6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_virtual_distr_XIUD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");   //�Ա� �й� ����
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT  \n";
        query +=" SET DISTR_GBN = :DISTR_GBN , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE REPT_DAY = :REPT_DAY  \n";
        query +=" AND REPT_NUM = :REPT_NUM  \n";
        query +=" AND REPT_GBN = :REPT_GBN";
        sobj.setSql(query);
        sobj.setString("DISTR_GBN", DISTR_GBN);               //�Ա� �й� ����
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        return sobj;
    }
    //##**$$mng_virtual_distr
    //##**$$mng_virtual_upso_distr
    /*
    */
    public DOBJ CTLmng_virtual_upso_distr(DOBJ dobj)
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
            ////
            String   TEMP1 ="0";         //�ӽ��÷�1
            dobj.setGVValue("TEMP1",TEMP1);
            dobj  = CALLmng_virtual_upso_distr_MIUD1(Conn, dobj);           //  �ο���� ó��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLmng_virtual_upso_distr_SEL9(Conn, dobj);           //  ���� �й� ���� ��ȸ
            dobj  = CALLmng_virtual_upso_distr_SEL10(Conn, dobj);           //  �������� ��ȸ
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
    public DOBJ CTLmng_virtual_upso_distr( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        ////
        String   TEMP1 ="0";         //�ӽ��÷�1
        dobj.setGVValue("TEMP1",TEMP1);
        dobj  = CALLmng_virtual_upso_distr_MIUD1(Conn, dobj);           //  �ο���� ó��
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLmng_virtual_upso_distr_SEL9(Conn, dobj);           //  ���� �й� ���� ��ȸ
        dobj  = CALLmng_virtual_upso_distr_SEL10(Conn, dobj);           //  �������� ��ȸ
        return dobj;
    }
    // �ο���� ó��
    public DOBJ CALLmng_virtual_upso_distr_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MIUD1");
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_virtual_upso_distr_XIUD1(Conn, dobj);           //  �������� ����
                dobj  = CALLmng_virtual_upso_distr_SEL1(Conn, dobj);           //  �Ա� ������ ����
                dobj  = CALLmng_virtual_upso_distr_SEL3(Conn, dobj);           //  ���Ȯ��
                if(!dobj.getRetObject("SEL3").getRecord().get("ACCU_DAY").equals(""))
                {
                    dobj  = CALLmng_virtual_upso_distr_OSP2(Conn, dobj);           //  ��� ���� �Ա�
                    ////
                    String   TEMP1 = dobj.getRetObject("OSP2").getRecord().get("CNT_INST");         //�ӽ��÷�1
                    dobj.setGVValue("TEMP1",TEMP1);
                }
                else
                {
                    dobj  = CALLmng_virtual_upso_distr_OSP3(Conn, dobj);           //  ���� �Ա�
                    ////
                    String   TEMP1 = dobj.getRetObject("OSP3").getRecord().get("CNT_INST");         //�ӽ��÷�1
                    dobj.setGVValue("TEMP1",TEMP1);
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLmng_virtual_upso_distr_XIUD99(Conn, dobj);           //  �������� ����
                dobj  = CALLmng_virtual_upso_distr_SEL4(Conn, dobj);           //  �Ա� ������ ����
                dobj  = CALLmng_virtual_upso_distr_SEL5(Conn, dobj);           //  ���Ȯ��
                if( dobj.getRetObject("SEL5").getRecord().get("ACCU_GBN").equals("22"))
                {
                    dobj  = CALLmng_virtual_upso_distr_OSP4(Conn, dobj);           //  ��� ���� �Ա�
                    ////
                    String   TEMP1 = dobj.getRetObject("OSP4").getRecord().get("CNT_INST");         //�ӽ��÷�1
                    dobj.setGVValue("TEMP1",TEMP1);
                }
                else
                {
                    dobj  = CALLmng_virtual_upso_distr_OSP5(Conn, dobj);           //  ���� �Ա�
                    ////
                    String   TEMP1 = dobj.getRetObject("OSP5").getRecord().get("CNT_INST");         //�ӽ��÷�1
                    dobj.setGVValue("TEMP1",TEMP1);
                }
            }
        }
        return dobj;
    }
    // �������� ����
    public DOBJ CALLmng_virtual_upso_distr_XIUD99(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD99");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD99");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_virtual_upso_distr_XIUD99(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD99");
        rvobj.Println("XIUD99");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_virtual_upso_distr_XIUD99(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE GIBU.TBRA_REPT_ERR  \n";
        query +=" WHERE REPT_DAY = :REPT_DAY  \n";
        query +=" AND REPT_GBN = :REPT_GBN  \n";
        query +=" AND	BRAN_CD = :BRAN_CD  \n";
        query +=" AND	INSPRES_ID = :INSPRES_ID";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �������� ����
    public DOBJ CALLmng_virtual_upso_distr_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLmng_virtual_upso_distr_XIUD1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        rvobj.Println("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_virtual_upso_distr_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE GIBU.TBRA_REPT_ERR  \n";
        query +=" WHERE REPT_DAY = :REPT_DAY  \n";
        query +=" AND REPT_GBN = :REPT_GBN  \n";
        query +=" AND	BRAN_CD = :BRAN_CD  \n";
        query +=" AND	INSPRES_ID = :INSPRES_ID";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �Ա� ������ ����
    // ���ν����� ȣ���ϱ� ���� �Աݵ����͸� �����Ѵ�
    public DOBJ CALLmng_virtual_upso_distr_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_virtual_upso_distr_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_virtual_upso_distr_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   COMIS = dobj.getRetObject("R").getRecord().getDouble("COMIS");   //������
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");   //�Ա� �й� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   DISTR_AMT = dobj.getRetObject("R").getRecord().getDouble("DISTR_AMT");   //�й� �ݾ�
        double   DEMD_MMCNT = dobj.getRetObject("R").getRecord().getDouble("DEMD_MMCNT");   //û��������
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   START_YRMN = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   DISTR_SEQ = dobj.getRetObject("R").getRecord().get("DISTR_SEQ");   //�й� ����
        String   NOTE_YRMN = dobj.getRetObject("R").getRecord().get("NOTE_YRMN");   //���� ���
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   NESS_AMT = dobj.getRetObject("R").getRecord().getDouble("NESS_AMT");   //�ʿ� �ݾ�
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_YRMN = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        double   BALANCE = dobj.getRetObject("R").getRecord().getDouble("BALANCE");   //�ܾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :NOTE_YRMN  AS  NOTE_YRMN  ,  RECV_DAY  AS  MAPPING_DAY  ,  :DISTR_SEQ  AS  DISTR_SEQ  ,  :DISTR_GBN  AS  DISTR_GBN  ,  :BRAN_CD  AS  BRAN_CD  ,  :UPSO_CD  AS  UPSO_CD  ,  :DEMD_MMCNT  AS  DEMD_MMCNT  ,  SUBSTR(:START_YRMN,  1,  6)  AS  START_YRMN  ,  SUBSTR(:END_YRMN,  1,  6)  AS  END_YRMN  ,  :DISTR_AMT  AS  DISTR_AMT  ,  :COMIS  AS  COMIS  ,  :BALANCE  AS  BALANCE  ,  RECV_DAY  ,  BANK_CD  ,  GIBU.FT_GET_ACCOUNT_FORMAT(ACCN_NUM)  AS  ACCN_NUM  ,  NULL  AS  CLAIM_GBN  ,  :REMAK  AS  REMAK  ,  'D'  AS  CRUD  ,  :INSPRES_ID  AS  INSPRES_ID  ,  :NESS_AMT  AS  NESS_AMT  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("DISTR_GBN", DISTR_GBN);               //�Ա� �й� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("DISTR_AMT", DISTR_AMT);               //�й� �ݾ�
        sobj.setDouble("DEMD_MMCNT", DEMD_MMCNT);               //û��������
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("DISTR_SEQ", DISTR_SEQ);               //�й� ����
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //���� ���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("NESS_AMT", NESS_AMT);               //�ʿ� �ݾ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("REMAK", REMAK);               //���
        sobj.setDouble("BALANCE", BALANCE);               //�ܾ�
        return sobj;
    }
    // �Ա� ������ ����
    // ���ν����� ȣ���ϱ� ���� �Աݵ����͸� �����Ѵ�
    public DOBJ CALLmng_virtual_upso_distr_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_virtual_upso_distr_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_virtual_upso_distr_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   COMIS = dobj.getRetObject("R").getRecord().getDouble("COMIS");   //������
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");   //�Ա� �й� ����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        double   DISTR_AMT = dobj.getRetObject("R").getRecord().getDouble("DISTR_AMT");   //�й� �ݾ�
        double   DEMD_MMCNT = dobj.getRetObject("R").getRecord().getDouble("DEMD_MMCNT");   //û��������
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   START_YRMN = dobj.getRetObject("R").getRecord().get("START_YRMN");   //���۳��
        String   DISTR_SEQ = dobj.getRetObject("R").getRecord().get("DISTR_SEQ");   //�й� ����
        String   NOTE_YRMN = dobj.getRetObject("R").getRecord().get("NOTE_YRMN");   //���� ���
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        double   NESS_AMT = dobj.getRetObject("R").getRecord().getDouble("NESS_AMT");   //�ʿ� �ݾ�
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_YRMN = dobj.getRetObject("R").getRecord().get("END_YRMN");   //������
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //���
        double   BALANCE = dobj.getRetObject("R").getRecord().getDouble("BALANCE");   //�ܾ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :NOTE_YRMN  AS  NOTE_YRMN  ,  RECV_DAY  AS  MAPPING_DAY  ,  :DISTR_SEQ  AS  DISTR_SEQ  ,  :DISTR_GBN  AS  DISTR_GBN  ,  :BRAN_CD  AS  BRAN_CD  ,  :UPSO_CD  AS  UPSO_CD  ,  :DEMD_MMCNT  AS  DEMD_MMCNT  ,  SUBSTR(:START_YRMN,  1,  6)  AS  START_YRMN  ,  SUBSTR(:END_YRMN,  1,  6)  AS  END_YRMN  ,  :DISTR_AMT  AS  DISTR_AMT  ,  :COMIS  AS  COMIS  ,  :BALANCE  AS  BALANCE  ,  RECV_DAY  ,  BANK_CD  ,  GIBU.FT_GET_ACCOUNT_FORMAT(ACCN_NUM)  AS  ACCN_NUM  ,  NULL  AS  CLAIM_GBN  ,  :REMAK  AS  REMAK  ,  'I'  AS  CRUD  ,  :INSPRES_ID  AS  INSPRES_ID  ,  :NESS_AMT  AS  NESS_AMT  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setDouble("COMIS", COMIS);               //������
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("DISTR_GBN", DISTR_GBN);               //�Ա� �й� ����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setDouble("DISTR_AMT", DISTR_AMT);               //�й� �ݾ�
        sobj.setDouble("DEMD_MMCNT", DEMD_MMCNT);               //û��������
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        sobj.setString("DISTR_SEQ", DISTR_SEQ);               //�й� ����
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //���� ���
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        sobj.setDouble("NESS_AMT", NESS_AMT);               //�ʿ� �ݾ�
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_YRMN", END_YRMN);               //������
        sobj.setString("REMAK", REMAK);               //���
        sobj.setDouble("BALANCE", BALANCE);               //�ܾ�
        return sobj;
    }
    // ���Ȯ��
    public DOBJ CALLmng_virtual_upso_distr_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_virtual_upso_distr_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_virtual_upso_distr_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("R").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("R").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_GBN = dobj.getRetObject("R").getRecord().get("REPT_GBN");   //�Ա� ����
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_GBN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ���Ȯ��
    public DOBJ CALLmng_virtual_upso_distr_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_virtual_upso_distr_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_virtual_upso_distr_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCU_DAY  ,  JUDG_CD  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        return sobj;
    }
    // ��� ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLmng_virtual_upso_distr_OSP4(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP4");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL4");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP4");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","DISTR_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN_ACCU";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP4");
        robj.Println("OSP4");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ��� ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLmng_virtual_upso_distr_OSP2(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP2");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL1");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP2");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","DISTR_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN_ACCU";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP2");
        robj.Println("OSP2");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLmng_virtual_upso_distr_OSP5(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP5");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL4");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP5");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","DISTR_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP5");
        robj.Println("OSP5");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �Ա�
    // ���Һ��� �Ա� ������ �����Ѵ�
    public DOBJ CALLmng_virtual_upso_distr_OSP3(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP3");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL1");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("OSP3");
        String[]  incolumns ={"REPT_DAY","REPT_NUM","REPT_GBN","NOTE_YRMN","DISTR_SEQ","BRAN_CD","UPSO_CD","DEMD_MMCNT","START_YRMN","END_YRMN","DISTR_AMT","COMIS","BALANCE","RECV_DAY","BANK_CD","ACCN_NUM","MAPPING_DAY","DISTR_GBN","CLAIM_GBN","REMAK","INSPRES_ID","CRUD","NESS_AMT"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_INDTN";
        int[]    intypes={12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"CNT_INST"};;
        int[]    outtypes ={12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP3");
        robj.Println("OSP3");
        dobj.setRetObject(robj);
        return dobj;
    }
    // ���� �й� ���� ��ȸ
    // �Ա����ڸ� �������� ���� �й� ������ ��ȸ�Ѵ�
    public DOBJ CALLmng_virtual_upso_distr_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_virtual_upso_distr_SEL9(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        rvobj.setRetcode(1);
        rvobj.Println("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_virtual_upso_distr_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").firstRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.DISTR_GBN  ,  A.DISTR_SEQ  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  C.UPSO_NM  ,  DECODE(B.START_YRMN,  NULL,  TO_CHAR(ADD_MONTHS(TO_DATE(D.NOTE_YRMN,  'YYYYMM'),  1),  'YYYYMM')  ||  '01',  B.START_YRMN)  START_YRMN  ,  B.END_YRMN  ,  A.DISTR_AMT  ,  D.NOTE_YRMN  ,  A.MAPPING_DAY  ,  A.REMAK  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(MIN(NOTE_YRMN),  NULL,  NULL,  MIN(NOTE_YRMN)  ||  '01')  START_YRMN  ,  DECODE(MAX(NOTE_YRMN),  NULL,  NULL,  MAX(NOTE_YRMN)  ||  '01')  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '08'  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  B  ,  GIBU.TBRA_UPSO  C  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  ,  MAX(NOTE_YRMN)  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '08'  UNION  ALL   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '08'  )  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  D  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  B.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  B.DISTR_SEQ(+)  =  A.DISTR_SEQ   \n";
        query +=" AND  D.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  D.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  D.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  D.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  D.DISTR_SEQ(+)  =  A.DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // �������� ��ȸ
    public DOBJ CALLmng_virtual_upso_distr_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLmng_virtual_upso_distr_SEL10(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.Println("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmng_virtual_upso_distr_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        String   REPT_GBN = dobj.getRetObject("S").firstRecord().get("REPT_GBN");   //�Ա� ����
        String   CNT_INST = dobj.getGVString("TEMP1");   //CNT_INST
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  UPSO_CD  ,  START_YRMN  ,  END_YRMN  ,  REPT_AMT  ,  COMIS  ,  RECV_DAY  ,  ACCN_NUM  ,  ERR_GBN  ,  ERR_CTENT  FROM  GIBU.TBRA_REPT_ERR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  AND	INSPRES_ID  =  :INSPRES_ID   \n";
        query +=" AND  '1'  =  DECODE(:CNT_INST,  1,  NULL,  '1') ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("CNT_INST", CNT_INST);               //CNT_INST
        return sobj;
    }
    //##**$$mng_virtual_upso_distr
    //##**$$sel_virtual_upso_distr
    /*
    */
    public DOBJ CTLsel_virtual_upso_distr(DOBJ dobj)
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
            dobj  = CALLsel_virtual_upso_distr_SEL1(Conn, dobj);           //  ���� �й� ���� ��ȸ
            if(!dobj.getRetObject("S").getRecord().get("REPT_NUM").equals(""))
            {
                dobj  = CALLsel_virtual_upso_distr_SEL2(Conn, dobj);           //  ���� �й� ���� ��ȸ
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
    public DOBJ CTLsel_virtual_upso_distr( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_virtual_upso_distr_SEL1(Conn, dobj);           //  ���� �й� ���� ��ȸ
        if(!dobj.getRetObject("S").getRecord().get("REPT_NUM").equals(""))
        {
            dobj  = CALLsel_virtual_upso_distr_SEL2(Conn, dobj);           //  ���� �й� ���� ��ȸ
        }
        return dobj;
    }
    // ���� �й� ���� ��ȸ
    // �Ա����ڸ� �������� ���� �й� ������ ��ȸ�Ѵ�
    public DOBJ CALLsel_virtual_upso_distr_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_virtual_upso_distr_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_virtual_upso_distr_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  BANK_CD  ,  GIBU.FT_GET_ACCOUNT_FORMAT(ACCN_NUM)  AS  ACCN_NUM  ,  REPTPRES  ,  REPT_AMT  ,  RECV_DAY  ,  BRAN_CD  ,  REMAK  ,  INSPRES_ID  ,  INS_DATE  ,  COMIS  FROM  GIBU.TBRA_REPT_VIRTUAL  WHERE  RECV_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  DISTR_GBN  =  '42' ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    // ���� �й� ���� ��ȸ
    // �Ա����ڸ� �������� ���� �й� ������ ��ȸ�Ѵ�
    public DOBJ CALLsel_virtual_upso_distr_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_virtual_upso_distr_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_virtual_upso_distr_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.DISTR_GBN  ,  A.DISTR_SEQ  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  C.UPSO_NM  ,  DECODE(B.START_YRMN,  NULL,  DECODE(D.NOTE_YRMN,  NULL,'',  TO_CHAR(ADD_MONTHS(TO_DATE(D.NOTE_YRMN,  'YYYYMM'),  1),  'YYYYMM')  ||  '01'),  B.START_YRMN)  START_YRMN  ,  B.END_YRMN  ,  A.DISTR_AMT  ,  D.NOTE_YRMN  ,  A.MAPPING_DAY  ,  A.REMAK  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(MIN(NOTE_YRMN),  NULL,  NULL,  MIN(NOTE_YRMN)  ||  '01')  START_YRMN  ,  DECODE(MAX(NOTE_YRMN),  NULL,  NULL,  MAX(NOTE_YRMN)  ||  '01')  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '08'  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  B  ,  GIBU.TBRA_UPSO  C  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  ,  MAX(NOTE_YRMN)  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '08'  UNION  ALL   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '08'  )  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  D  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  '08'   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  B.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  B.DISTR_SEQ(+)  =  A.DISTR_SEQ   \n";
        query +=" AND  D.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  D.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  D.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  D.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  D.DISTR_SEQ(+)  =  A.DISTR_SEQ  ORDER  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN,  A.DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$sel_virtual_upso_distr
    //##**$$sel_virtual_err
    /*
    */
    public DOBJ CTLsel_virtual_err(DOBJ dobj)
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
            dobj  = CALLsel_virtual_err_SEL3(Conn, dobj);           //  ��¥Ȯ�ο�
            if( dobj.getRetObject("SEL3").getRecord().getDouble("CNT") == 1)
            {
                dobj  = CALLsel_virtual_err_SEL1(Conn, dobj);           //  ������� �Ա� �̸�Ī��ȸ
                dobj  = CALLsel_virtual_err_MRG6( dobj);        //  ����
            }
            else
            {
                dobj  = CALLsel_virtual_err_SEL2(Conn, dobj);           //  ������� �Ա� �̸�Ī��ȸ
                dobj  = CALLsel_virtual_err_MRG6( dobj);        //  ����
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
    public DOBJ CTLsel_virtual_err( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_virtual_err_SEL3(Conn, dobj);           //  ��¥Ȯ�ο�
        if( dobj.getRetObject("SEL3").getRecord().getDouble("CNT") == 1)
        {
            dobj  = CALLsel_virtual_err_SEL1(Conn, dobj);           //  ������� �Ա� �̸�Ī��ȸ
            dobj  = CALLsel_virtual_err_MRG6( dobj);        //  ����
        }
        else
        {
            dobj  = CALLsel_virtual_err_SEL2(Conn, dobj);           //  ������� �Ա� �̸�Ī��ȸ
            dobj  = CALLsel_virtual_err_MRG6( dobj);        //  ����
        }
        return dobj;
    }
    // ��¥Ȯ�ο�
    public DOBJ CALLsel_virtual_err_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_virtual_err_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_virtual_err_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  (CASE  WHEN  TO_CHAR(SYSDATE,  'YYYYMM')  =  :START_YRMN  THEN  1  ELSE  0  END)  AS  CNT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // ������� �Ա� �̸�Ī��ȸ
    public DOBJ CALLsel_virtual_err_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_virtual_err_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_virtual_err_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT GIBU.GET_BRAN_NM(B.BRAN_CD) AS BRAN_NM , B.BRAN_CD , A.REPT_DAY , A.REPT_NUM , A.RECV_DAY , B.UPSO_CD , B.UPSO_NM , (CASE WHEN (SELECT COUNT(1)  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND COMPN_DAY IS NULL) > 0 THEN 'Y' ELSE 'N' END) AS ACCU_YN , C.DEMD_YRMN , TO_CHAR(TO_DATE(C.START_YRMN, 'YYYYMM'), 'YYYY/MM') || '~' || TO_CHAR(TO_DATE(C.END_YRMN, 'YYYYMM'), 'YYYY/MM') AS DEMD_TERM , TOT_DEMD_AMT AS DEMD_AMT , TOT_USE_AMT , A.REPT_AMT , A.COMIS , B.STAFF_CD , FIDU.GET_STAFF_NM(B.STAFF_CD) AS STAFF_NM , A.REMAK , '08' AS REPT_GBN , B.CLSBS_YRMN , (  ";
        query +=" SELECT --+ INDEX_DESC(TBRA_REPT_BALANCE TBRA_REPT_BALANCE_IDX_PK) \n NVL(SUM(BALANCE), 0) AS BALANCE  ";
        query +=" FROM GIBU.TBRA_REPT_BALANCE  ";
        query +=" WHERE UPSO_CD = B.UPSO_CD  ";
        query +=" AND ROWNUM = 1 ) AS BALANCE , (  ";
        query +=" SELECT COUNT(UPSO_CD) CLAIM_CNT  ";
        query +=" FROM GIBU.TBRA_DLGTN_CLAIM  ";
        query +=" WHERE UPSO_CD = B.UPSO_CD  ";
        query +=" AND COMPN_DAY IS NULL ) AS CLAIM_CNT , A.DISTR_GBN , C.MONPRNCFEE  ";
        query +=" FROM GIBU.TBRA_REPT_VIRTUAL A , GIBU.TBRA_UPSO B , GIBU.TBRA_DEMD_OCR C  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.UPSO_CD = C.UPSO_CD  ";
        query +=" AND A.REPT_DAY LIKE :START_YRMN || '%'  ";
        query +=" AND A.BRAN_CD = DECODE(:BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD)  ";
        query +=" AND A.MAPPING_DAY IS NULL  ";
        query +=" AND C.DEMD_YRMN = (SELECT MAX(DEMD_YRMN)  ";
        query +=" FROM GIBU.TBRA_DEMD_OCR  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD)  ";
        query +=" AND ( (A.DISTR_GBN IS NOT NULL  ";
        query +=" AND A.REPT_AMT <> NVL((SELECT SUM(DISTR_AMT)  ";
        query +=" FROM GIBU.TBRA_REPT_UPSO_DISTR  ";
        query +=" WHERE REPT_DAY = A.REPT_DAY  ";
        query +=" AND REPT_NUM = A.REPT_NUM  ";
        query +=" AND REPT_GBN = '08'), 0))  ";
        query +=" OR A.DISTR_GBN IS NULL)  ";
        if( !UPSO_CD.equals("") )
        {
            query +=" AND A.UPSO_CD = :UPSO_CD  ";
        }
        if( !STAFF_CD.equals("") )
        {
            query +=" AND B.STAFF_CD = :STAFF_CD  ";
        }
        query +=" ORDER BY A.RECV_DAY, B.BRAN_CD, B.UPSO_CD  ";
        sobj.setSql(query);
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        }
        if(!UPSO_CD.equals(""))
        {
            sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    // ����
    public DOBJ CALLsel_virtual_err_MRG6(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG6");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL2","");
        rvobj.setName("MRG6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // ������� �Ա� �̸�Ī��ȸ
    public DOBJ CALLsel_virtual_err_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_virtual_err_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_virtual_err_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //��� �ڵ�
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //���� �ڵ�
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //���۳��
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT GIBU.GET_BRAN_NM(B.BRAN_CD) AS BRAN_NM , B.BRAN_CD , A.REPT_DAY , A.REPT_NUM , A.RECV_DAY , B.UPSO_CD , B.UPSO_NM , (CASE WHEN (SELECT COUNT(1)  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND COMPN_DAY IS NULL) > 0 THEN 'Y' ELSE 'N' END) AS ACCU_YN , C.DEMD_YRMN , TO_CHAR(TO_DATE(C.START_YRMN, 'YYYYMM'), 'YYYY/MM') || '~' || TO_CHAR(TO_DATE(C.END_YRMN, 'YYYYMM'), 'YYYY/MM') AS DEMD_TERM , TOT_DEMD_AMT AS DEMD_AMT , TOT_USE_AMT , A.REPT_AMT , A.COMIS , B.STAFF_CD , FIDU.GET_STAFF_NM(B.STAFF_CD) AS STAFF_NM , A.REMAK , '08' AS REPT_GBN , B.CLSBS_YRMN , (  ";
        query +=" SELECT --+ INDEX_DESC(TBRA_REPT_BALANCE TBRA_REPT_BALANCE_IDX_PK) \n NVL(SUM(BALANCE), 0) AS BALANCE  ";
        query +=" FROM GIBU.TBRA_REPT_BALANCE  ";
        query +=" WHERE UPSO_CD = B.UPSO_CD  ";
        query +=" AND ROWNUM = 1 ) AS BALANCE , (  ";
        query +=" SELECT COUNT(UPSO_CD) CLAIM_CNT  ";
        query +=" FROM GIBU.TBRA_DLGTN_CLAIM  ";
        query +=" WHERE UPSO_CD = B.UPSO_CD  ";
        query +=" AND COMPN_DAY IS NULL ) AS CLAIM_CNT , A.DISTR_GBN , C.MONPRNCFEE  ";
        query +=" FROM GIBU.TBRA_REPT_VIRTUAL A , GIBU.TBRA_UPSO B , GIBU.TBRA_DEMD_OCR C  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.UPSO_CD = C.UPSO_CD  ";
        query +=" AND A.REPT_DAY LIKE :START_YRMN || '%'  ";
        query +=" AND A.BRAN_CD = DECODE(:BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD)  ";
        query +=" AND C.DEMD_YRMN = (SELECT MAX(DEMD_YRMN)  ";
        query +=" FROM GIBU.TBRA_DEMD_OCR  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD)  ";
        if( !UPSO_CD.equals("") )
        {
            query +=" AND A.UPSO_CD = :UPSO_CD  ";
        }
        if( !STAFF_CD.equals("") )
        {
            query +=" AND B.STAFF_CD = :STAFF_CD  ";
        }
        query +=" ORDER BY A.RECV_DAY, B.BRAN_CD, B.UPSO_CD  ";
        sobj.setSql(query);
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //��� �ڵ�
        }
        if(!UPSO_CD.equals(""))
        {
            sobj.setString("UPSO_CD", UPSO_CD);               //���� �ڵ�
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("START_YRMN", START_YRMN);               //���۳��
        return sobj;
    }
    //##**$$sel_virtual_err
    //##**$$proc_auto_rept
    /*
    */
    public DOBJ CTLproc_auto_rept(DOBJ dobj)
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
            dobj  = CALLproc_auto_rept_OSP1(Conn, dobj);           //  �ڵ��Ա� ���ν���
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
    public DOBJ CTLproc_auto_rept( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLproc_auto_rept_OSP1(Conn, dobj);           //  �ڵ��Ա� ���ν���
        return dobj;
    }
    // �ڵ��Ա� ���ν���
    public DOBJ CALLproc_auto_rept_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        String[]  incolumns ={"INSPRES_ID","START_YRMN"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            ////
            String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");         //����� ID
            record.put("INSPRES_ID",INSPRES_ID);
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_FMS_PROC_AUTO_REPT2";
        int[]    intypes={12, 12};;
        String[] outcolnms={};
        int[]    outtypes ={};
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    //##**$$proc_auto_rept
    //##**$$ins_virtual_bran_distr
    /*
    */
    public DOBJ CTLins_virtual_bran_distr(DOBJ dobj)
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
            dobj  = CALLins_virtual_bran_distr_SEL24(Conn, dobj);           //  �Ա����� Ȯ��
            if( dobj.getRetObject("SEL24").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLins_virtual_bran_distr_DEL41(Conn, dobj);           //  ���� �й� ���� ����
                dobj  = CALLins_virtual_bran_distr_INS2(Conn, dobj);           //  ���� �й� ���� ����
                dobj  = CALLins_virtual_bran_distr_SEL10(Conn, dobj);           //  �й� �ݾ� ��ȸ
            }
            else
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="���ε� �Ա������� �ֽ��ϴ�.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
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
    public DOBJ CTLins_virtual_bran_distr( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLins_virtual_bran_distr_SEL24(Conn, dobj);           //  �Ա����� Ȯ��
        if( dobj.getRetObject("SEL24").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLins_virtual_bran_distr_DEL41(Conn, dobj);           //  ���� �й� ���� ����
            dobj  = CALLins_virtual_bran_distr_INS2(Conn, dobj);           //  ���� �й� ���� ����
            dobj  = CALLins_virtual_bran_distr_SEL10(Conn, dobj);           //  �й� �ݾ� ��ȸ
        }
        else
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="���ε� �Ա������� �ֽ��ϴ�.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // �Ա����� Ȯ��
    public DOBJ CALLins_virtual_bran_distr_SEL24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL24");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL24");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLins_virtual_bran_distr_SEL24(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL24");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLins_virtual_bran_distr_SEL24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").firstRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_GBN = dobj.getRetObject("S").firstRecord().get("REPT_GBN");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(UPSO_CD)  CNT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // ���� �й� ���� ����
    // ���� �й� ���� ����
    public DOBJ CALLins_virtual_bran_distr_DEL41(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL41");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLins_virtual_bran_distr_DEL41(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL41") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLins_virtual_bran_distr_DEL41(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_DISTR  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // ���� �й� ���� ����
    // ���� �й� ���� ����
    public DOBJ CALLins_virtual_bran_distr_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLins_virtual_bran_distr_INS2(dobj, dvobj);
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
    private SQLObject SQLins_virtual_bran_distr_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //��� �Ͻ�
        String   REMAK = dvobj.getRecord().get("REMAK");   //���
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        double   DISTR_AMT = dvobj.getRecord().getDouble("DISTR_AMT");   //�й� �ݾ�
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        String   BRAN_CD = dvobj.getRecord().get("RECV_BRAN_CD");   //���� �ڵ�
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //����� ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_DISTR (REPT_DAY, INS_DATE, REPT_NUM, INSPRES_ID, DISTR_AMT, REPT_GBN, BRAN_CD, REMAK)  \n";
        query +=" values(:REPT_DAY , SYSDATE, :REPT_NUM , :INSPRES_ID , :DISTR_AMT , :REPT_GBN , :BRAN_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //���
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setDouble("DISTR_AMT", DISTR_AMT);               //�й� �ݾ�
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        return sobj;
    }
    // �й� �ݾ� ��ȸ
    // �й� �ݾ� ��ȸ
    public DOBJ CALLins_virtual_bran_distr_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLins_virtual_bran_distr_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLins_virtual_bran_distr_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dvobj.firstRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_GBN = dvobj.firstRecord().get("REPT_GBN");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(A.DISTR_AMT)  DISTR_AMT  ,  MAX(B.REPT_AMT)  REPT_AMT  FROM  GIBU.TBRA_REPT_DISTR  A  ,  GIBU.TBRA_REPT_VIRTUAL  B  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  B.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  A.REPT_NUM ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    //##**$$ins_virtual_bran_distr
    //##**$$del_virtual_bran_distr
    /*
    */
    public DOBJ CTLdel_virtual_bran_distr(DOBJ dobj)
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
            dobj  = CALLdel_virtual_bran_distr_SEL1(Conn, dobj);           //  �Ա����� Ȯ��
            if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
            {
                dobj  = CALLdel_virtual_bran_distr_DEL1(Conn, dobj);           //  ���� �й� ���� ����
                dobj  = CALLdel_virtual_bran_distr_SEL2(Conn, dobj);           //  ���� �й� ���� ��ȸ
            }
            else
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="���ε� �Ա������� �ֽ��ϴ�.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
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
    public DOBJ CTLdel_virtual_bran_distr( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdel_virtual_bran_distr_SEL1(Conn, dobj);           //  �Ա����� Ȯ��
        if( dobj.getRetObject("SEL1").getRecord().getDouble("CNT") == 0)
        {
            dobj  = CALLdel_virtual_bran_distr_DEL1(Conn, dobj);           //  ���� �й� ���� ����
            dobj  = CALLdel_virtual_bran_distr_SEL2(Conn, dobj);           //  ���� �й� ���� ��ȸ
        }
        else
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="���ε� �Ա������� �ֽ��ϴ�.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // �Ա����� Ȯ��
    public DOBJ CALLdel_virtual_bran_distr_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLdel_virtual_bran_distr_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdel_virtual_bran_distr_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").firstRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_GBN = dobj.getRetObject("S").firstRecord().get("REPT_GBN");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(UPSO_CD)  CNT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // ���� �й� ���� ����
    // ���� �й� ���� ����
    public DOBJ CALLdel_virtual_bran_distr_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdel_virtual_bran_distr_DEL1(dobj, dvobj);
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
    private SQLObject SQLdel_virtual_bran_distr_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_DISTR  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // ���� �й� ���� ��ȸ
    // ���� �й� ���� ��ȸ
    public DOBJ CALLdel_virtual_bran_distr_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLdel_virtual_bran_distr_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdel_virtual_bran_distr_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").firstRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").firstRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.BANK_CD  ,  GIBU.FT_GET_ACCOUNT_FORMAT(A.ACCN_NUM)  AS  ACCN_NUM  ,  A.REPTPRES  ,  B.DISTR_AMT  ,   \n";
        query +=" (SELECT  --+  INDEX_DESC(TBRA_REPT_BALANCE  TBRA_REPT_BALANCE_IDX_PK)  \n  NVL  (SUM  (BALANCE),  0)  AS  BALANCE  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  ROWNUM  =  1)  AS  BALANCE  ,  A.RECV_DAY  ,  C.DEPT_NM  AS  BRAN_NM  ,  A.BRAN_CD  ,  A.REMAK  ,  A.INSPRES_ID  ,  A.INS_DATE  FROM  GIBU.TBRA_REPT_VIRTUAL  A  ,  GIBU.TBRA_REPT_DISTR  B  ,  INSA.TCPM_DEPT  C  WHERE  B.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  A.BRAN_CD,  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  C.GIBU  =  B.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$del_virtual_bran_distr
    //##**$$sel_virtual_bran_distr
    /*
    */
    public DOBJ CTLsel_virtual_bran_distr(DOBJ dobj)
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
            dobj  = CALLsel_virtual_bran_distr_SEL1(Conn, dobj);           //  ���κй� �Ա����� ��ȸ
            if(!dobj.getRetObject("S").getRecord().get("REPT_NUM").equals(""))
            {
                dobj  = CALLsel_virtual_bran_distr_SEL2(Conn, dobj);           //  ���� �й� ���� ��ȸ
                dobj  = CALLsel_virtual_bran_distr_SEL3(Conn, dobj);           //  ���� �й� ���� ��ȸ
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
    public DOBJ CTLsel_virtual_bran_distr( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_virtual_bran_distr_SEL1(Conn, dobj);           //  ���κй� �Ա����� ��ȸ
        if(!dobj.getRetObject("S").getRecord().get("REPT_NUM").equals(""))
        {
            dobj  = CALLsel_virtual_bran_distr_SEL2(Conn, dobj);           //  ���� �й� ���� ��ȸ
            dobj  = CALLsel_virtual_bran_distr_SEL3(Conn, dobj);           //  ���� �й� ���� ��ȸ
        }
        return dobj;
    }
    // ���κй� �Ա����� ��ȸ
    // ���� �й�� ������ �Ա����� ������ ��ȸ�Ѵ�
    public DOBJ CALLsel_virtual_bran_distr_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_virtual_bran_distr_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_virtual_bran_distr_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //������
        String   BRAN_CD = dobj.getRetObject("S").firstRecord().get("BRAN_CD");   //���� �ڵ�
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.BANK_CD  ,  GIBU.FT_GET_ACCOUNT_FORMAT(A.ACCN_NUM)  AS  ACCN_NUM  ,  A.REPTPRES  ,  A.REPT_AMT  ,  A.RECV_DAY  ,  B.DEPT_NM  BRAN_NM  ,  A.BRAN_CD  ,  A.REMAK  ,  '08'  REPT_GBN  ,  A.COMIS  FROM  GIBU.TBRA_REPT_VIRTUAL  A  ,  INSA.TCPM_DEPT  B  WHERE  A.RECV_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.DISTR_GBN  =  '41'   \n";
        query +=" AND  B.GIBU  =  A.BRAN_CD  UNION   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.BANK_CD  ,  GIBU.FT_GET_ACCOUNT_FORMAT(A.ACCN_NUM)  AS  ACCN_NUM  ,  A.REPTPRES  ,  A.REPT_AMT  ,  A.RECV_DAY  ,  B.DEPT_NM  BRAN_NM  ,  A.BRAN_CD  ,  A.REMAK  ,  '08'  REPT_GBN  ,  A.COMIS  FROM  GIBU.TBRA_REPT_VIRTUAL  A  ,  INSA.TCPM_DEPT  B  ,  GIBU.TBRA_REPT_DISTR  C  WHERE  A.REPT_DAY  =  C.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM   \n";
        query +=" AND  C.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.REPT_GBN  =  '08'   \n";
        query +=" AND  A.RECV_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  B.GIBU  =  A.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //������
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        sobj.setString("END_DAY", END_DAY);               //������
        return sobj;
    }
    // ���� �й� ���� ��ȸ
    // ���ο� �й�� ������ ��ȸ�Ѵ�
    public DOBJ CALLsel_virtual_bran_distr_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_virtual_bran_distr_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_virtual_bran_distr_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.BANK_CD  ,  GIBU.FT_GET_ACCOUNT_FORMAT(A.ACCN_NUM)  AS  ACCN_NUM  ,  A.REPTPRES  ,  B.DISTR_AMT  ,   \n";
        query +=" (SELECT  --+  INDEX_DESC(TBRA_REPT_BALANCE  TBRA_REPT_BALANCE_IDX_PK)  \n  NVL  (SUM  (BALANCE),  0)  AS  BALANCE  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  ROWNUM  =  1)  AS  BALANCE  ,  A.RECV_DAY  ,  B.BRAN_CD  AS  RECV_BRAN_CD  ,  D.DEPT_NM  AS  RECV_BRAN_NM  ,  C.DEPT_NM  AS  BRAN_NM  ,  A.BRAN_CD  ,  B.REMAK  ,  A.INSPRES_ID  ,  A.INS_DATE  ,  '08'  AS  REPT_GBN  ,  :BRAN_CD  AS  S_BRAN_CD  ,  A.COMIS  FROM  GIBU.TBRA_REPT_VIRTUAL  A  ,  GIBU.TBRA_REPT_DISTR  B  ,  INSA.TCPM_DEPT  C  ,  INSA.TCPM_DEPT  D  WHERE  B.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  A.BRAN_CD,  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  C.GIBU  =  B.BRAN_CD   \n";
        query +=" AND  D.GIBU  =  C.GIBU   \n";
        query +=" AND  B.REPT_GBN  =  '08' ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    // ���� �й� ���� ��ȸ
    // �Ա����ڸ� �������� ���� �й� ������ ��ȸ�Ѵ�
    public DOBJ CALLsel_virtual_bran_distr_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLsel_virtual_bran_distr_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_virtual_bran_distr_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_NUM = dobj.getRetObject("S").getRecord().get("REPT_NUM");   //�Ա� ��ȣ
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //���� �ڵ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.DISTR_GBN  ,  A.DISTR_SEQ  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  C.UPSO_NM  ,  DECODE(B.START_YRMN,  NULL,  DECODE(D.NOTE_YRMN,  NULL,'',  TO_CHAR(ADD_MONTHS(TO_DATE(D.NOTE_YRMN,  'YYYYMM'),  1),  'YYYYMM')  ||  '01'),  B.START_YRMN)  START_YRMN  ,  B.END_YRMN  ,  A.DISTR_AMT  ,  D.NOTE_YRMN  ,  A.MAPPING_DAY  ,  A.REMAK  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  DECODE(MIN(NOTE_YRMN),  NULL,  NULL,  MIN(NOTE_YRMN)  ||  '01')  START_YRMN  ,  DECODE(MAX(NOTE_YRMN),  NULL,  NULL,  MAX(NOTE_YRMN)  ||  '01')  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '08'  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  B  ,  GIBU.TBRA_UPSO  C  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  ,  MAX(NOTE_YRMN)  NOTE_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  IN  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_NOTE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '08'  UNION  ALL   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '08'  )  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  D  WHERE  A.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  B.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  B.DISTR_SEQ(+)  =  A.DISTR_SEQ   \n";
        query +=" AND  D.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  D.REPT_DAY  (+)  =  A.REPT_DAY   \n";
        query +=" AND  D.REPT_NUM  (+)  =  A.REPT_NUM   \n";
        query +=" AND  D.REPT_GBN  (+)  =  A.REPT_GBN   \n";
        query +=" AND  D.DISTR_SEQ(+)  =  A.DISTR_SEQ  ORDER  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN,  A.DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_NUM", REPT_NUM);               //�Ա� ��ȣ
        sobj.setString("BRAN_CD", BRAN_CD);               //���� �ڵ�
        return sobj;
    }
    //##**$$sel_virtual_bran_distr
    //##**$$rept_card_report
    /*
    */
    public DOBJ CTLrept_card_report(DOBJ dobj)
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
            dobj  = CALLrept_card_report_SEL3(Conn, dobj);           //  �ڵ���ü �Ա� ��� ��ȸ
            dobj  = CALLrept_card_report_SEL4(Conn, dobj);           //  �ڵ���ü�Աݿ��� ����
            dobj  = CALLrept_card_report_SEL5(Conn, dobj);           //  �ڵ���ü�Աݿ���(����)
            dobj  = CALLrept_card_report_SEL6(Conn, dobj);           //  ����հ� ������1
            dobj  = CALLrept_card_report_SEL7(Conn, dobj);           //  ����հ� ������2
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
    public DOBJ CTLrept_card_report( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_card_report_SEL3(Conn, dobj);           //  �ڵ���ü �Ա� ��� ��ȸ
        dobj  = CALLrept_card_report_SEL4(Conn, dobj);           //  �ڵ���ü�Աݿ��� ����
        dobj  = CALLrept_card_report_SEL5(Conn, dobj);           //  �ڵ���ü�Աݿ���(����)
        dobj  = CALLrept_card_report_SEL6(Conn, dobj);           //  ����հ� ������1
        dobj  = CALLrept_card_report_SEL7(Conn, dobj);           //  ����հ� ������2
        return dobj;
    }
    // �ڵ���ü �Ա� ��� ��ȸ
    // �ڵ���ü �Ա� ��� ����Ʈ�� ��ȸ�Ѵ�
    public DOBJ CALLrept_card_report_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_card_report_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_report_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //û�� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  XA.START_YRMN  ||  '01'  START_YRMN  ,  XA.END_YRMN  ||  '01'  END_YRMN  ,  XC.GRAD  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  XA.DEMD_GBN  ,  '1'  PRINT_YN  ,  XA.TRNF_RSLT  ,  XA.CARD_RTN_CD  ,  XA.NICE_RTN_CD  ,  FIDU.GET_CODE_NM('00401',  XA.CARD_RTN_CD)  as  CARD_RTN_NM  ,  FIDU.GET_CODE_NM('00402',  XA.NICE_RTN_CD)  as  NICE_RTN_NM  ,  TO_CHAR(XA.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_DEMD_CARD  XA  ,  GIBU.TBRA_UPSO  XB  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  APPL_DAY  <=  :DEMD_YRMN  ||  '32'  GROUP  BY  UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.BSTYP_CD  =  C.BSTYP_CD   \n";
        query +=" AND  A.UPSO_GRAD  =  C.GRAD_GBN  )  XC  ,  INSA.TCPM_DEPT  XD  WHERE  XA.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  XA.DEMD_YRMN  IN   \n";
        query +=" (SELECT  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  XA.UPSO_CD)   \n";
        query +=" AND  XA.TRNF_RSLT  =  '60'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XB.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        return sobj;
    }
    // �ڵ���ü�Աݿ��� ����
    // �ڵ���ü�Աݿ��� ����
    public DOBJ CALLrept_card_report_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_card_report_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_report_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REPT_DAY ="";        //�Ա�����
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //�Ա� ���
        String   REPT_GBN ="07";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XC.UPSO_NM  ,  XB.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XC.MCHNDAESU  ,  XB.MONPRNCFEE  ,  XA.REPT_AMT  ,  XB.TOT_ADDT_AMT  ,  XB.TOT_EADDT_AMT  ,  XB.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.ERR_GBN  ,  XA.ERR_CTENT  ,  XB.CARD_RTN_CD  ,  XB.NICE_RTN_CD  ,  FIDU.GET_CODE_NM('00401',  XB.CARD_RTN_CD)  as  CARD_RTN_NM  ,  FIDU.GET_CODE_NM('00402',  XB.NICE_RTN_CD)  as  NICE_RTN_NM  ,  TO_CHAR(XB.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_REPT_ERR  XA  ,  GIBU.TBRA_DEMD_CARD  XB  ,  GIBU.TBRA_UPSO  XC  ,  INSA.TCPM_DEPT  XD  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_REPT_ERR  B  WHERE  B.REPT_DAY  =  TO_CHAR(SYSDATE,'YYYYMMDD')   \n";
        query +=" AND  B.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  A.APPL_DAY  <=  :REPT_YRMN  ||  '32'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.UPSO_GRAD  =  C.GRAD_GBN   \n";
        query +=" AND  C.BSTYP_CD  =  A.BSTYP_CD  )  XE  WHERE  XA.REPT_DAY  =  TO_CHAR(SYSDATE,'YYYYMMDD')   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.START_YRMN  =  XA.START_YRMN   \n";
        query +=" AND  XB.END_YRMN  =  XA.END_YRMN   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XC.BRAN_CD   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD  ORDER  BY  XC.BRAN_CD,  XC.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �ڵ���ü�Աݿ���(����)
    // �ڵ���ü�Աݿ���(����)�� ��ȸ�Ѵ�
    public DOBJ CALLrept_card_report_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_card_report_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_report_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //�Ա� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.BRAN_CD  ,  XC.DEPT_NM  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.TRNF_RSLT  ,  XA.CARD_RTN_CD  ,  XA.NICE_RTN_CD  ,  FIDU.GET_CODE_NM('00401',  XA.CARD_RTN_CD)  as  CARD_RTN_NM  ,  FIDU.GET_CODE_NM('00402',  XA.NICE_RTN_CD)  as  NICE_RTN_NM  ,  TO_CHAR(XA.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_DEMD_CARD  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_DEPT  XC  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_DEMD_CARD  B  WHERE  B.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  B.TRNF_RSLT  <>  '60'   \n";
        query +=" AND  A.APPL_DAY  <=  :REPT_YRMN  ||  '32'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  C.GRAD_GBN  =  A.UPSO_GRAD   \n";
        query +=" AND  C.BSTYP_CD  =  A.BSTYP_CD  )  XE  WHERE  XA.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NOT  NULL   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GIBU  =  XA.BRAN_CD   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD  ORDER  BY  XB.BRAN_CD,  XB.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        return sobj;
    }
    // ����հ� ������1
    public DOBJ CALLrept_card_report_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_card_report_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_report_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //û�� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(MAX(CNT1),  '999,999')  ||  '��'  AS  CNT1  ,  TO_CHAR(MAX(CNT2),  '999,999')  ||  '��'  AS  CNT2  ,  TO_CHAR(MAX(CNT3),  '999,999')  ||  '��'  AS  CNT3  ,  TO_CHAR(MAX(CNT4),  '999,999')  ||  '��'  AS  CNT4  ,  TO_CHAR(MAX(CNT5),  '999,999')  ||  '��'  AS  CNT5  ,  TO_CHAR(MAX(CNT6),  '999,999')  ||  '��'  AS  CNT6  ,  TO_CHAR(MAX(CNT1)  +  MAX(CNT2),  '999,999')  ||  '��'  AS  TOT_CNT12  ,  TO_CHAR(MAX(CNT3)  +  MAX(CNT4),  '999,999')  ||  '��'  AS  TOT_CNT34  ,  TO_CHAR(MAX(CNT5)  +  MAX(CNT6),  '999,999')  ||  '��'  AS  TOT_CNT56  ,  TO_CHAR(MAX(SUM1),  '999,999,999,999')  ||  '��'  AS  SUM1  ,  TO_CHAR(MAX(SUM2),  '999,999,999,999')  ||  '��'  AS  SUM2  ,  TO_CHAR(MAX(SUM3),  '999,999,999,999')  ||  '��'  AS  SUM3  ,  TO_CHAR(MAX(SUM4),  '999,999,999,999')  ||  '��'  AS  SUM4  ,  TO_CHAR(MAX(SUM5),  '999,999,999,999')  ||  '��'  AS  SUM5  ,  TO_CHAR(MAX(SUM6),  '999,999,999,999')  ||  '��'  AS  SUM6  ,  TO_CHAR(MAX(SUM1)  +  MAX(SUM2),  '999,999,999,999')  ||  '��'  AS  TOT_SUM12  ,  TO_CHAR(MAX(SUM3)  +  MAX(SUM4),  '999,999,999,999')  ||  '��'  AS  TOT_SUM34  ,  TO_CHAR(MAX(SUM5)  +  MAX(SUM6),  '999,999,999,999')  ||  '��'  AS  TOT_SUM56  FROM  (   \n";
        query +=" SELECT  DECODE(CARD_GBN,  'WIN',COUNT(0),  0)  AS  CNT1  ,  DECODE(CARD_GBN,  'LGC',COUNT(0),  0)  AS  CNT2  ,  0  AS  CNT3  ,  0  AS  CNT4  ,  0  AS  CNT5  ,  0  AS  CNT6  ,  DECODE(CARD_GBN,  'WIN',SUM(TOT_DEMD_AMT),0)  AS  SUM1  ,  DECODE(CARD_GBN,  'LGC',SUM(TOT_DEMD_AMT),0)  AS  SUM2  ,  0  AS  SUM3  ,  0  AS  SUM4  ,  0  AS  SUM5  ,  0  AS  SUM6  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.TOT_DEMD_AMT  ,   \n";
        query +=" (SELECT  CARD_GBN  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'   \n";
        query +=" AND  RECEPTION_GBN  =  '7'   \n";
        query +=" AND  CARD_GBN  IS  NOT  NULL   \n";
        query +=" AND  UPSO_CD  =  A.UPSO_CD)  AS  CARD_GBN  FROM  GIBU.TBRA_DEMD_CARD  A  WHERE  A.DEMD_YRMN  =  :DEMD_YRMN  )  GROUP  BY  CARD_GBN  UNION  ALL   \n";
        query +=" SELECT  0  AS  CNT1  ,  0  AS  CNT2  ,  DECODE(CARD_GBN,  'WIN',COUNT(0),  0)  AS  CNT3  ,  DECODE(CARD_GBN,  'LGC',COUNT(0),  0)  AS  CNT4  ,  0  AS  CNT5  ,  0  AS  CNT6  ,  0  AS  SUM1  ,  0  AS  SUM2  ,  DECODE(CARD_GBN,  'WIN',SUM(REPT_AMT),0)  AS  SUM3  ,  DECODE(CARD_GBN,  'LGC',SUM(REPT_AMT),0)  AS  SUM4  ,  0  AS  SUM5  ,  0  AS  SUM6  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.TOT_DEMD_AMT  -  A.COMIS_AMT  AS  REPT_AMT  ,   \n";
        query +=" (SELECT  CARD_GBN  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'   \n";
        query +=" AND  RECEPTION_GBN  =  '7'   \n";
        query +=" AND  CARD_GBN  IS  NOT  NULL   \n";
        query +=" AND  UPSO_CD  =  A.UPSO_CD)  AS  CARD_GBN  FROM  GIBU.TBRA_DEMD_CARD  A  WHERE  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  A.NICE_RTN_CD  =  '00'  )  GROUP  BY  CARD_GBN  UNION  ALL   \n";
        query +=" SELECT  0  AS  CNT1  ,  0  AS  CNT2  ,  0  AS  CNT3  ,  0  AS  CNT4  ,  DECODE(CARD_GBN,  'WIN',COUNT(0),  0)  AS  CNT5  ,  DECODE(CARD_GBN,  'LGC',COUNT(0),  0)  AS  CNT6  ,  0  AS  SUM1  ,  0  AS  SUM2  ,  0  AS  SUM3  ,  0  AS  SUM4  ,  DECODE(CARD_GBN,  'WIN',SUM(TOT_DEMD_AMT),0)  AS  SUM5  ,  DECODE(CARD_GBN,  'LGC',SUM(TOT_DEMD_AMT),0)  AS  SUM6  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.TOT_DEMD_AMT  ,   \n";
        query +=" (SELECT  CARD_GBN  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'   \n";
        query +=" AND  RECEPTION_GBN  =  '7'   \n";
        query +=" AND  CARD_GBN  IS  NOT  NULL   \n";
        query +=" AND  UPSO_CD  =  A.UPSO_CD)  AS  CARD_GBN  FROM  GIBU.TBRA_DEMD_CARD  A  WHERE  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  A.NICE_RTN_CD  <>  '00'  )  GROUP  BY  CARD_GBN  ) ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        return sobj;
    }
    // ����հ� ������2
    public DOBJ CALLrept_card_report_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_card_report_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_report_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //û�� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(MAX(CNT1),  '99,999')  ||  '��'  AS  CNT1  ,  TO_CHAR(MAX(CNT2),  '99,999')  ||  '��'  AS  CNT2  ,  TO_CHAR(MAX(CNT1)  +  MAX(CNT2),  '99,999')  ||  '��'  AS  TOT_CNT12  ,  TO_CHAR(MAX(DEMD_AMT1),  '999,999,999')  ||  '��'  AS  DEMD_AMT1  ,  TO_CHAR(MAX(DEMD_AMT2),  '999,999,999')  ||  '��'  AS  DEMD_AMT2  ,  TO_CHAR(MAX(DEMD_AMT1)  +  MAX(DEMD_AMT2),  '999,999,999')  ||  '��'  AS  TOT_DEMD_AMT12  ,  TO_CHAR(MAX(COMIS_AMT1),  '9,999,999')  ||  '��'  AS  COMIS_AMT1  ,  TO_CHAR(MAX(COMIS_AMT2),  '9,999,999')  ||  '��'  AS  COMIS_AMT2  ,  TO_CHAR(MAX(COMIS_AMT1)  +  MAX(COMIS_AMT2),  '9,999,999')  ||  '��'  AS  TOT_COMIS_AMT12  ,  TO_CHAR(MAX(REPT_AMT1),  '999,999,999')  ||  '��'  AS  REPT_AMT1  ,  TO_CHAR(MAX(REPT_AMT2),  '999,999,999')  ||  '��'  AS  REPT_AMT2  ,  TO_CHAR(MAX(REPT_AMT1)  +  MAX(REPT_AMT2),  '999,999,999')  ||  '��'  AS  TOT_REPT_AMT12  ,  MIN(REPT_PLAN_DAY1)  AS  REPT_PLAN_DAY1  ,  MIN(REPT_PLAN_DAY2)  AS  REPT_PLAN_DAY2  FROM  (   \n";
        query +=" SELECT  DECODE(CARD_GBN,  'LGC',  COUNT(0),  0  )  AS  CNT1  ,  DECODE(CARD_GBN,  'WIN',  COUNT(0),  0  )  AS  CNT2  ,  DECODE(CARD_GBN,  'LGC',  SUM(TOT_DEMD_AMT),  0)  AS  DEMD_AMT1  ,  DECODE(CARD_GBN,  'WIN',  SUM(TOT_DEMD_AMT),  0)  AS  DEMD_AMT2  ,  DECODE(CARD_GBN,  'LGC',  SUM(COMIS_AMT),  0)  AS  COMIS_AMT1  ,  DECODE(CARD_GBN,  'WIN',  SUM(COMIS_AMT),  0)  AS  COMIS_AMT2  ,  DECODE(CARD_GBN,  'LGC',  SUM(REPT_AMT),  0)  AS  REPT_AMT1  ,  DECODE(CARD_GBN,  'WIN',  SUM(REPT_AMT),  0)  AS  REPT_AMT2  ,  DECODE(CARD_GBN,  'LGC',  MIN(REPT_PLAN_DAY),  '')  AS  REPT_PLAN_DAY1  ,  DECODE(CARD_GBN,  'WIN',  MIN(REPT_PLAN_DAY),  '')  AS  REPT_PLAN_DAY2  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.TOT_DEMD_AMT  ,  A.COMIS_AMT  ,  A.TOT_DEMD_AMT  -  A.COMIS_AMT  AS  REPT_AMT  ,  A.REPT_PLAN_DAY  ,   \n";
        query +=" (SELECT  CARD_GBN  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'   \n";
        query +=" AND  RECEPTION_GBN  =  '7'   \n";
        query +=" AND  CARD_GBN  IS  NOT  NULL   \n";
        query +=" AND  UPSO_CD  =  A.UPSO_CD)  AS  CARD_GBN  FROM  GIBU.TBRA_DEMD_CARD  A  WHERE  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  A.NICE_RTN_CD  =  '00'  )  GROUP  BY  CARD_GBN  ) ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        return sobj;
    }
    //##**$$rept_card_report
    //##**$$rept_card_insert
    /*
    */
    public DOBJ CTLrept_card_insert(DOBJ dobj)
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
            dobj  = CALLrept_card_insert_SEL1(Conn, dobj);           //  �Աݸ���Ȯ��
            if( dobj.getRetObject("SEL1").getRecord().get("BRANEND").equals("0"))
            {
                dobj  = CALLrept_card_insert_MPD11(Conn, dobj);           //  �Ա����� ó��
                if(dobj.getRtncode() == 9)
                {
                    Conn.rollback();
                    return dobj;
                }
                dobj  = CALLrept_card_insert_XIUD15(Conn, dobj);           //  Ŀ��
                dobj  = CALLrept_card_insert_SEL17(Conn, dobj);           //  OSP �ڷ����
                dobj  = CALLrept_card_insert_DEL18(Conn, dobj);           //  �������� ����
                dobj  = CALLrept_card_insert_OSP1(Conn, dobj);           //  �ڵ���ü �Ա�
                dobj  = CALLrept_card_insert_SEL2(Conn, dobj);           //  �ڵ���ü �Ա� ��� �˻�
                dobj  = CALLrept_card_insert_SEL3(Conn, dobj);           //  �ڵ���ü �Ա� ��� ��ȸ
                dobj  = CALLrept_card_insert_SEL4(Conn, dobj);           //  �ڵ���ü�Աݿ��� ����
                dobj  = CALLrept_card_insert_SEL5(Conn, dobj);           //  �ڵ���ü�Աݿ���(����)
                dobj  = CALLrept_card_insert_SEL16(Conn, dobj);           //  ������ ����
                dobj  = CALLrept_card_insert_SEL31(Conn, dobj);           //  ����հ�-û���Ǽ�
                dobj  = CALLrept_card_insert_SEL32(Conn, dobj);           //  ����հ�-�����Ǽ�
                dobj  = CALLrept_card_insert_SEL33(Conn, dobj);           //  ����հ�-�����ҴɰǼ�
            }
            else
            {
                dobj.setRtncode(9);
                if(dobj.getRtncode() == 9)
                {
                    String message ="�ش� ����� �Աݸ����� �ֽ��ϴ�.";
                    dobj.setRetmsg(message);
                    Conn.rollback();
                    return dobj;
                }
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
    public DOBJ CTLrept_card_insert( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_card_insert_SEL1(Conn, dobj);           //  �Աݸ���Ȯ��
        if( dobj.getRetObject("SEL1").getRecord().get("BRANEND").equals("0"))
        {
            dobj  = CALLrept_card_insert_MPD11(Conn, dobj);           //  �Ա����� ó��
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLrept_card_insert_XIUD15(Conn, dobj);           //  Ŀ��
            dobj  = CALLrept_card_insert_SEL17(Conn, dobj);           //  OSP �ڷ����
            dobj  = CALLrept_card_insert_DEL18(Conn, dobj);           //  �������� ����
            dobj  = CALLrept_card_insert_OSP1(Conn, dobj);           //  �ڵ���ü �Ա�
            dobj  = CALLrept_card_insert_SEL2(Conn, dobj);           //  �ڵ���ü �Ա� ��� �˻�
            dobj  = CALLrept_card_insert_SEL3(Conn, dobj);           //  �ڵ���ü �Ա� ��� ��ȸ
            dobj  = CALLrept_card_insert_SEL4(Conn, dobj);           //  �ڵ���ü�Աݿ��� ����
            dobj  = CALLrept_card_insert_SEL5(Conn, dobj);           //  �ڵ���ü�Աݿ���(����)
            dobj  = CALLrept_card_insert_SEL16(Conn, dobj);           //  ������ ����
            dobj  = CALLrept_card_insert_SEL31(Conn, dobj);           //  ����հ�-û���Ǽ�
            dobj  = CALLrept_card_insert_SEL32(Conn, dobj);           //  ����հ�-�����Ǽ�
            dobj  = CALLrept_card_insert_SEL33(Conn, dobj);           //  ����հ�-�����ҴɰǼ�
        }
        else
        {
            dobj.setRtncode(9);
            if(dobj.getRtncode() == 9)
            {
                String message ="�ش� ����� �Աݸ����� �ֽ��ϴ�.";
                dobj.setRetmsg(message);
                Conn.rollback();
                return dobj;
            }
        }
        return dobj;
    }
    // �Աݸ���Ȯ��
    // ��û�� �Աݳ���� �Աݸ����� �ִ� ��� ���� ó��
    public DOBJ CALLrept_card_insert_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_card_insert_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_insert_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //�Ա� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  BRANEND  FROM  GIBU.TBRA_BRANEND  WHERE  END_YEAR  =  SUBSTR(:REPT_YRMN,  1,  4)   \n";
        query +=" AND  END_MON  =  SUBSTR(:REPT_YRMN,  5,  2) ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        return sobj;
    }
    // �Ա����� ó��
    // ���ڵ� ������ �Ա������� ó���Ѵ�
    public DOBJ CALLrept_card_insert_MPD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD11");
        VOBJ dvobj = dobj.getRetObject("S");         //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        dvobj.Println("MPD11");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("REC_GBN").equals("60") || dvobj.getRecord().get("REC_GBN").equals("61") || dvobj.getRecord().get("REC_GBN").equals("62") || dvobj.getRecord().get("REC_GBN").equals("63"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLrept_card_insert_XIUD13(Conn, dobj);           //  ��ü ��� ����
            }
        }
        return dobj;
    }
    // ��ü ��� ����
    // û�� ������ ���� ��ü����� �����Ѵ�
    public DOBJ CALLrept_card_insert_XIUD13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD13");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-�б�ó��(MPD,MIUD)�� Currect Record Object�Դϴ�.
        dvobj.Println("XIUD13");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_card_insert_XIUD13(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD13");
        rvobj.Println("XIUD13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_insert_XIUD13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPRV_NUM = dobj.getRetObject("R").getRecord().get("APPRV_NUM");   //���� ��ȣ
        String   CARD_RTN_CD = dobj.getRetObject("R").getRecord().get("CARD_RTN_CD");   //CARD_RTN_CD
        String   CLIENT_NUM = dobj.getRetObject("R").getRecord().get("CLIENT_NUM");   //�� ��ȣ
        double   COMIS_AMT = dobj.getRetObject("R").getRecord().getDouble("COMIS_AMT");   //������ �ݾ�
        String   DEMD_YRMN = dobj.getRetObject("R").getRecord().get("DEMD_YRMN");   //û�� ���
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //������ ID
        String   NICE_RTN_CD = dobj.getRetObject("R").getRecord().get("NICE_RTN_CD");   //NICE_RTN_CD
        String   REC_GBN = dobj.getRetObject("R").getRecord().get("REC_GBN");   //���ڵ�����
        String   REPT_PLAN_DAY = dobj.getRetObject("R").getRecord().get("REPT_PLAN_DAY");   //�Աݿ�������
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_DEMD_CARD  \n";
        query +=" SET TRNF_RSLT = :REC_GBN , CARD_RTN_CD =: CARD_RTN_CD , NICE_RTN_CD =: NICE_RTN_CD , COMIS_AMT =: COMIS_AMT , APPRV_NUM =: APPRV_NUM , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE , REPT_PLAN_DAY = :REPT_PLAN_DAY  \n";
        query +=" WHERE DEMD_YRMN = :DEMD_YRMN  \n";
        query +=" AND UPSO_CD = ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE CLIENT_NUM = :CLIENT_NUM )";
        sobj.setSql(query);
        sobj.setString("APPRV_NUM", APPRV_NUM);               //���� ��ȣ
        sobj.setString("CARD_RTN_CD", CARD_RTN_CD);               //CARD_RTN_CD
        sobj.setString("CLIENT_NUM", CLIENT_NUM);               //�� ��ȣ
        sobj.setDouble("COMIS_AMT", COMIS_AMT);               //������ �ݾ�
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("MODPRES_ID", MODPRES_ID);               //������ ID
        sobj.setString("NICE_RTN_CD", NICE_RTN_CD);               //NICE_RTN_CD
        sobj.setString("REC_GBN", REC_GBN);               //���ڵ�����
        sobj.setString("REPT_PLAN_DAY", REPT_PLAN_DAY);               //�Աݿ�������
        return sobj;
    }
    // Ŀ��
    public DOBJ CALLrept_card_insert_XIUD15(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD15");
        VOBJ dvobj = dobj.getRetObject("S");            //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_card_insert_XIUD15(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD15");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_insert_XIUD15(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" commit ";
        sobj.setSql(query);
        return sobj;
    }
    // OSP �ڷ����
    public DOBJ CALLrept_card_insert_SEL17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL17");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL17");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_card_insert_SEL17(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL17");
        rvobj.Println("SEL17");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_insert_SEL17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   INSPRES_ID = dobj.getRetObject("S").firstRecord().get("INSPRES_ID");   //����� ID
        String   DEMD_YRMN = dobj.getRetObject("S").firstRecord().get("DEMD_YRMN");   //û�� ���
        String   RECV_DAY = dobj.getRetObject("S").firstRecord().get("RECV_DAY");   //���� ����
        String   ACCN_NUM = dobj.getRetObject("S2").getRecord().get("ACCN_NUM");   //���� ��ȣ
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(SYSDATE,'YYYYMMDD')  AS  REPT_DAY  ,  '07'  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  ,  :RECV_DAY  AS  RECV_DAY  ,  '004'  AS  BANK_CD  ,  (   \n";
        query +=" SELECT  ACCN_NUM  FROM  ACCT.TCAC_ACCOUNT  WHERE  REPLACE(ACCN_NUM,  '-',  '')  LIKE  nvl(:ACCN_NUM,'69503701001088')  )  AS  ACCN_NUM  ,  :INSPRES_ID  AS  INSPRES_ID  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //����� ID
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        sobj.setString("RECV_DAY", RECV_DAY);               //���� ����
        sobj.setString("ACCN_NUM", ACCN_NUM);               //���� ��ȣ
        return sobj;
    }
    // �������� ����
    public DOBJ CALLrept_card_insert_DEL18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL18");
        VOBJ dvobj = dobj.getRetObject("SEL17");           //OSP �ڷ�������� ������Ų OBJECT�Դϴ�.(CALLrept_card_insert_SEL17)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLrept_card_insert_DEL18(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL18") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_insert_DEL18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //�Ա� ����
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //�Ա�����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_ERR  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        return sobj;
    }
    // �ڵ���ü �Ա�
    // ���Һ��� �ڵ���ü �Ա� ������ �����Ѵ�
    public DOBJ CALLrept_card_insert_OSP1(Connection Conn, DOBJ dobj) throws Exception
    {
        dobj.setRtnname("OSP1");
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        VOBJ dvobj = dobj.getRetObject("SEL17");         //OSP �ڷ�������� ������Ų OBJECT�Դϴ�.(CALLrept_card_insert_SEL17)
        dvobj.Println("OSP1");
        String[]  incolumns ={"REPT_DAY","DEMD_YRMN","RECV_DAY","BANK_CD","ACCN_NUM","INSPRES_ID"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.getHashRecord();
            for(int i=0;i<incolumns.length;i++)
            {
                if(!record.containsKey(incolumns[i]))
                {
                    record.remove(incolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        VOBJ robj = new VOBJ();
        record = null;
        String    tmpval="";
        ArrayList rtn = null;
        ArrayList colnms = dvobj.getColumnNames();
        String   spname ="GIBU.SP_PROC_REPT_CARD";
        int[]    intypes={12, 12, 12, 12, 12, 12};;
        String[] outcolnms={"READ_CNT","INST_CNT","ERR_CNT"};;
        int[]    outtypes ={12, 12, 12};;
        ArrayList inarg = null;
        dvobj.first();
        while(dvobj.next())
        {
            inarg = new ArrayList();
            for(int i=0;i<incolumns.length;i++)
            {
                inarg.add(dvobj.getRecord().get(incolumns[i]));
            }
            rtn =  (ArrayList)qexe.SPCall(Conn, spname, inarg, intypes, outtypes );
            record = new HashMap();
            for(int i=0;i<outcolnms.length;i++)
            {
                if(rtn.get(i) != null) tmpval=rtn.get(i).toString();
                else  tmpval ="";
                record.put(outcolnms[i],tmpval);
            }
            if(record.size() > 0)  robj.addRecord(record);
        }
        robj.setName("OSP1");
        robj.setRetcode(1);
        robj.Println("OSP1");
        dobj.setRetObject(robj);
        return dobj;
    }
    // �ڵ���ü �Ա� ��� �˻�
    // ���� �Ǽ�, ó�� �Ǽ�, ���� �Ǽ��� ��ȸ�Ѵ�
    public DOBJ CALLrept_card_insert_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_card_insert_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_insert_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   ERR_CNT = dobj.getRetObject("OSP1").getRecord().getDouble("ERR_CNT");   //���� �Ǽ�
        double   INST_CNT = dobj.getRetObject("OSP1").getRecord().getDouble("INST_CNT");   //�Է�ī��Ʈ
        double   READ_CNT = dobj.getRetObject("OSP1").getRecord().getDouble("READ_CNT");   //���� �Ǽ�
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :READ_CNT  AS  READ_CNT  ,  :INST_CNT  AS  INST_CNT  ,  :ERR_CNT  AS  ERR_CNT  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setDouble("ERR_CNT", ERR_CNT);               //���� �Ǽ�
        sobj.setDouble("INST_CNT", INST_CNT);               //�Է�ī��Ʈ
        sobj.setDouble("READ_CNT", READ_CNT);               //���� �Ǽ�
        return sobj;
    }
    // �ڵ���ü �Ա� ��� ��ȸ
    // �ڵ���ü �Ա� ��� ����Ʈ�� ��ȸ�Ѵ�
    public DOBJ CALLrept_card_insert_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_card_insert_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_insert_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("SEL17").getRecord().get("DEMD_YRMN");   //û�� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  XA.START_YRMN  ||  '01'  START_YRMN  ,  XA.END_YRMN  ||  '01'  END_YRMN  ,  XC.GRAD  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  XA.DEMD_GBN  ,  '1'  PRINT_YN  ,  XA.TRNF_RSLT  ,  XA.CARD_RTN_CD  ,  XA.NICE_RTN_CD  ,  FIDU.GET_CODE_NM('00401',  XA.CARD_RTN_CD)  as  CARD_RTN_NM  ,  FIDU.GET_CODE_NM('00402',  XA.NICE_RTN_CD)  as  NICE_RTN_NM  ,  TO_CHAR(XA.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_DEMD_CARD  XA  ,  GIBU.TBRA_UPSO  XB  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  APPL_DAY  <=  :DEMD_YRMN  ||  '32'  GROUP  BY  UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.BSTYP_CD  =  C.BSTYP_CD   \n";
        query +=" AND  A.UPSO_GRAD  =  C.GRAD_GBN  )  XC  ,  INSA.TCPM_DEPT  XD  WHERE  XA.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  XA.DEMD_YRMN  IN   \n";
        query +=" (SELECT  DEMD_YRMN  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  XA.UPSO_CD)   \n";
        query +=" AND  XA.TRNF_RSLT  =  '60'   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XB.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        return sobj;
    }
    // �ڵ���ü�Աݿ��� ����
    // �ڵ���ü�Աݿ��� ����
    public DOBJ CALLrept_card_insert_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_card_insert_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_insert_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL17").getRecord().get("REPT_DAY");   //�Ա�����
        String   REPT_YRMN = dobj.getRetObject("SEL17").getRecord().get("DEMD_YRMN");   //�Ա� ���
        String   REPT_GBN ="07";   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.BRAN_CD  ,  XD.DEPT_NM  ,  XA.UPSO_CD  ,  XC.UPSO_NM  ,  XB.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XC.MCHNDAESU  ,  XB.MONPRNCFEE  ,  XA.REPT_AMT  ,  XB.TOT_ADDT_AMT  ,  XB.TOT_EADDT_AMT  ,  XB.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.ERR_GBN  ,  XA.ERR_CTENT  ,  XB.CARD_RTN_CD  ,  XB.NICE_RTN_CD  ,  FIDU.GET_CODE_NM('00401',  XB.CARD_RTN_CD)  as  CARD_RTN_NM  ,  FIDU.GET_CODE_NM('00402',  XB.NICE_RTN_CD)  as  NICE_RTN_NM  ,  TO_CHAR(XB.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_REPT_ERR  XA  ,  GIBU.TBRA_DEMD_CARD  XB  ,  GIBU.TBRA_UPSO  XC  ,  INSA.TCPM_DEPT  XD  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_REPT_ERR  B  WHERE  B.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  B.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  A.APPL_DAY  <=  :REPT_YRMN  ||  '32'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.UPSO_GRAD  =  C.GRAD_GBN   \n";
        query +=" AND  C.BSTYP_CD  =  A.BSTYP_CD  )  XE  WHERE  XA.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XB.START_YRMN  =  XA.START_YRMN   \n";
        query +=" AND  XB.END_YRMN  =  XA.END_YRMN   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XD.GIBU  =  XC.BRAN_CD   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD  ORDER  BY  XC.BRAN_CD,  XC.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա�����
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        sobj.setString("REPT_GBN", REPT_GBN);               //�Ա� ����
        return sobj;
    }
    // �ڵ���ü�Աݿ���(����)
    // �ڵ���ü�Աݿ���(����)�� ��ȸ�Ѵ�
    public DOBJ CALLrept_card_insert_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_card_insert_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_insert_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("SEL17").getRecord().get("DEMD_YRMN");   //�Ա� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XB.BRAN_CD  ,  XC.DEPT_NM  ,  XB.UPSO_CD  ,  XB.UPSO_NM  ,  XA.DEMD_YRMN  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XE.GRADNM  ,  XB.MCHNDAESU  ,  XA.MONPRNCFEE  ,  XA.TOT_DEMD_AMT  ,  XA.TOT_ADDT_AMT  ,  XA.TOT_EADDT_AMT  ,  XA.DEMD_MMCNT  ,  '1'  PRNT_YN  ,  XA.TRNF_RSLT  ,  XA.CARD_RTN_CD  ,  XA.NICE_RTN_CD  ,  FIDU.GET_CODE_NM('00401',  XA.CARD_RTN_CD)  as  CARD_RTN_NM  ,  FIDU.GET_CODE_NM('00402',  XA.NICE_RTN_CD)  as  NICE_RTN_NM  ,  TO_CHAR(XA.MOD_DATE  ,'YYYYMMDD'  )  MOD_DATE  FROM  GIBU.TBRA_DEMD_CARD  XA  ,  GIBU.TBRA_UPSO  XB  ,  INSA.TCPM_DEPT  XC  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  TRIM(C.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  C.GRADNM  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_DEMD_CARD  B  WHERE  B.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  B.TRNF_RSLT  <>  '60'   \n";
        query +=" AND  A.APPL_DAY  <=  :REPT_YRMN  ||  '32'   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  C.GRAD_GBN  =  A.UPSO_GRAD   \n";
        query +=" AND  C.BSTYP_CD  =  A.BSTYP_CD  )  XE  WHERE  XA.DEMD_YRMN  =  :REPT_YRMN   \n";
        query +=" AND  XA.TRNF_RSLT  IS  NOT  NULL   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.GIBU  =  XA.BRAN_CD   \n";
        query +=" AND  XE.UPSO_CD  =  XA.UPSO_CD  ORDER  BY  XB.BRAN_CD,  XB.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //�Ա� ���
        return sobj;
    }
    // ������ ����
    public DOBJ CALLrept_card_insert_SEL16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL16");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL16");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_card_insert_SEL16(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL16");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_insert_SEL16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String YRMNDAY ="";        //����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MIN  (YRMNDAY)  PAY_DAY  FROM  INSA.TDUT_CALENDAR  WHERE  YRMNDAY  BETWEEN  TO_CHAR(SYSDATE,  'YYYYMM')  ||  '20'   \n";
        query +=" AND  TO_CHAR(SYSDATE,  'YYYYMM')  ||  '30'   \n";
        query +=" AND  HOLIDAY_YN  =  '0' ";
        sobj.setSql(query);
        return sobj;
    }
    // ����հ�-û���Ǽ�
    public DOBJ CALLrept_card_insert_SEL31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL31");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL31");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_card_insert_SEL31(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL31");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_insert_SEL31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("SEL17").getRecord().get("DEMD_YRMN");   //û�� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(B.CARD_GBN,'WIN','�Ｚī��','LGC','����ī��')  AS  CARD_GBN_NM  ,  COUNT(0)  AS  DEMD_CNT  ,  SUM(A.TOT_DEMD_AMT)  AS  TOT_DEMD_AMT  FROM  GIBU.TBRA_DEMD_CARD  A,   \n";
        query +=" (SELECT  UPSO_CD,  CARD_GBN  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  ||  AUTO_NUM  IN  (   \n";
        query +=" SELECT  UPSO_CD  ||  MAX(AUTO_NUM)  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'   \n";
        query +=" AND  CARD_NUM  IS  NOT  NULL   \n";
        query +=" AND  RECEPTION_GBN  =  '7'  GROUP  BY  UPSO_CD  ))  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.DEMD_YRMN  =  :DEMD_YRMN  GROUP  BY  B.CARD_GBN  ORDER  BY  B.CARD_GBN ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        return sobj;
    }
    // ����հ�-�����Ǽ�
    public DOBJ CALLrept_card_insert_SEL32(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL32");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL32");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_card_insert_SEL32(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL32");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_insert_SEL32(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("SEL17").getRecord().get("DEMD_YRMN");   //û�� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(B.CARD_GBN,'WIN','�Ｚī��','LGC','����ī��')  AS  CARD_GBN_NM  ,  COUNT(0)  AS  DEMD_CNT  ,  SUM(A.TOT_DEMD_AMT)  AS  TOT_DEMD_AMT  FROM  GIBU.TBRA_DEMD_CARD  A,   \n";
        query +=" (SELECT  UPSO_CD,  CARD_GBN  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  ||  AUTO_NUM  IN  (   \n";
        query +=" SELECT  UPSO_CD  ||  MAX(AUTO_NUM)  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'   \n";
        query +=" AND  CARD_NUM  IS  NOT  NULL   \n";
        query +=" AND  RECEPTION_GBN  =  '7'  GROUP  BY  UPSO_CD  ))  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  A.TRNF_RSLT  IN  ('60','63')  GROUP  BY  B.CARD_GBN  ORDER  BY  B.CARD_GBN ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        return sobj;
    }
    // ����հ�-�����ҴɰǼ�
    public DOBJ CALLrept_card_insert_SEL33(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL33");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL33");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_card_insert_SEL33(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL33");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_card_insert_SEL33(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("SEL17").getRecord().get("DEMD_YRMN");   //û�� ���
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(B.CARD_GBN,'WIN','�Ｚī��','LGC','����ī��')  AS  CARD_GBN_NM  ,  COUNT(0)  AS  DEMD_CNT  ,  SUM(A.TOT_DEMD_AMT)  AS  TOT_DEMD_AMT  FROM  GIBU.TBRA_DEMD_CARD  A,   \n";
        query +=" (SELECT  UPSO_CD,  CARD_GBN  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  UPSO_CD  ||  AUTO_NUM  IN  (   \n";
        query +=" SELECT  UPSO_CD  ||  MAX(AUTO_NUM)  FROM  GIBU.TBRA_UPSO_AUTO  WHERE  TERM_YN  =  'N'   \n";
        query +=" AND  CARD_NUM  IS  NOT  NULL   \n";
        query +=" AND  RECEPTION_GBN  =  '7'  GROUP  BY  UPSO_CD  ))  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  (A.TRNF_RSLT  IS  NULL   \n";
        query +=" OR  A.TRNF_RSLT  IN  ('61','62'))  GROUP  BY  B.CARD_GBN  ORDER  BY  B.CARD_GBN ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //û�� ���
        return sobj;
    }
    //##**$$rept_card_insert
    //##**$$rept_closing
    /*
    */
    public DOBJ CTLrept_closing(DOBJ dobj)
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
            dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  ���κ�����üũ
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
    public DOBJ CTLrept_closing( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  ���κ�����üũ
        return dobj;
    }
    // ���κ�����üũ
    // ���κ� �������̺��� �ش� ��� ������ �ִ��� üũ�Ѵ�
    public DOBJ CALLrept_closing_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //����� ȭ�鿡�� �߻��� Object�Դϴ�.
        SQLObject  sobj = SQLrept_closing_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        String message ="�ش�Ⱓ�� ���������� �����մϴ�. ���������� Ȯ���ϼ���.";
        dobj.setRetmsg(message);
        return dobj;
    }
    private SQLObject SQLrept_closing_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //�Ա� ����
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(:REPT_DAY,1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(:REPT_DAY,5,2)  =  END_MON ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //�Ա� ����
        return sobj;
    }
    //##**$$rept_closing
    //##**$$end
}
