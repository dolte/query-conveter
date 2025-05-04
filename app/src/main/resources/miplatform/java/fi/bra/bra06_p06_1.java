
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra06_p06_1
{
    public bra06_p06_1()
    {
    }
    //##**$$upso_list
    /* * 프로그램명 : bra06_p16
    * 작성자 : 서정재
    * 작성일 : 2009/12/02
    * 설명 :
    gbn:1 - 신규현황 조회
    gbn:2 - 휴업현황 조회
    gbn-3 - 폐업현황 조회
    * 수정일1: 2010.06.03
    * 수정자 : 권남균
    * 수정내용 : 폐업리스트 상에 신규일자 체크조건 삭제
    */
    public DOBJ CTLupso_list(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
            {
                dobj  = CALLupso_list_SEL1(Conn, dobj);           //  신규입금현황
                dobj  = CALLupso_list_SEL2(Conn, dobj);           //  통계
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj  = CALLupso_list_SEL3(Conn, dobj);           //  휴업현황
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("4"))
            {
                dobj  = CALLupso_list_SEL4(Conn, dobj);           //  신규등록현황
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("3"))
            {
                dobj  = CALLupso_list_SEL7(Conn, dobj);           //  폐업현황
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
    public DOBJ CTLupso_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
        {
            dobj  = CALLupso_list_SEL1(Conn, dobj);           //  신규입금현황
            dobj  = CALLupso_list_SEL2(Conn, dobj);           //  통계
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLupso_list_SEL3(Conn, dobj);           //  휴업현황
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("4"))
        {
            dobj  = CALLupso_list_SEL4(Conn, dobj);           //  신규등록현황
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("3"))
        {
            dobj  = CALLupso_list_SEL7(Conn, dobj);           //  폐업현황
        }
        return dobj;
    }
    // 신규입금현황
    public DOBJ CALLupso_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.UPSO_CD  ,  TA.UPSO_NEW_ADDR1  ||  DECODE(TA.UPSO_NEW_ADDR2,  '',  '',  ',  '||TA.UPSO_NEW_ADDR2)  ||  TA.UPSO_REF_INFO  UPSO_ADDR  ,  TE.HAN_NM  STAFF_NM  ,  TC.GRADNM  GRADNM  ,  TC.MONPRNCFEE  MONPRNCFEE  ,  TA.UPSO_NM  UPSO_NM  ,  TA.MNGEMSTR_NM  MNGEMSTR_NM  ,  TA.UPSO_PHON  UPSO_PHON  ,  SUBSTR(TA.OPBI_DAY,1,6)  NONPY_S_YRMN  ,  SUBSTR(TA.NEW_DAY,1,6)  NONPY_E_YRMN  ,  SUBSTR(TA.OPBI_DAY,1,4)||'/'||SUBSTR(TA.OPBI_DAY,5,2)||'~'||  SUBSTR(TA.NEW_DAY,1,4)||'/'||SUBSTR(TA.NEW_DAY,5,2)  AS  NONPY_YRMN  ,  TD.NONPY_AMT  ,  TF.START_YRMN  ,  TF.END_YRMN  ,  SUBSTR(TF.START_YRMN,1,4)||'/'||SUBSTR(TF.START_YRMN,5,2)||'~'||SUBSTR(TF.END_YRMN,1,4)||'/'||SUBSTR(TF.END_YRMN,5,2)  AS  YRMN  ,  TF.TOT_AMT  ,  (CASE  WHEN   \n";
        query +=" (SELECT  NVL(TO_CHAR(INS_DATE,  'YYYYMM'),  '000000')  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  TA.BEFORE_UPSO_CD)  <=  :END_YRMN  THEN  '업,변<'  ELSE  ''  END)  ||   \n";
        query +=" (SELECT  MNGEMSTR_NM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  TA.BEFORE_UPSO_CD)  BIGO  ,  TO_CHAR(TA.INS_DATE,  'YYYYMMDD')  AS  INS_DATE  ,  TA.BRAN_CD  ,  GIBU.GET_BRAN_NM(TA.BRAN_CD)  AS  BRAN_NM  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  GIBU.FT_SPLIT(A.DEMDS,  ',',  4)  NONPY_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  GIBU.FT_GET_NONPY_AMT(UPSO_CD,  OPBI_DAY,  :END_YRMN)  DEMDS  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  NEW_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  CLSBS_YRMN  IS  NULL  )  A  )  TD  ,  INSA.TINS_MST01  TE  ,  (   \n";
        query +=" SELECT  MIN(A.NOTE_YRMN)  START_YRMN  ,  MAX(A.NOTE_YRMN)  END_YRMN  ,  A.UPSO_CD  ,  (B.REPT_AMT-NVL(B.COMIS,0))  TOT_AMT  ,  B.MAPPING_DAY  FROM  GIBU.TBRA_NOTE  A  ,  GIBU.TBRA_REPT  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.MAPPING_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  B.DISTR_GBN  IS  NULL  GROUP  BY  A.UPSO_CD,  B.MAPPING_DAY,  B.REPT_AMT,  B.COMIS,  A.REPT_NUM,  A.REPT_DAY  UNION  ALL   \n";
        query +=" SELECT  MIN(A.NOTE_YRMN)  START_YRMN  ,  MAX(A.NOTE_YRMN)  END_YRMN  ,  A.UPSO_CD  ,  B.DISTR_AMT  TOT_AMT  ,  B.MAPPING_DAY  FROM  GIBU.TBRA_NOTE  A  ,  GIBU.TBRA_REPT_UPSO_DISTR  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.MAPPING_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM  GROUP  BY  A.UPSO_CD,  B.MAPPING_DAY,  B.DISTR_AMT  ,  A.REPT_NUM,  A.REPT_DAY  )  TF  WHERE  TA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  TA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TA.NEW_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  TC.BSTYP_CD  <>  'v'   \n";
        query +=" AND  TC.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TD.UPSO_CD(+)  =  TA.UPSO_CD   \n";
        query +=" AND  TF.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TE.STAFF_NUM(+)  =  TA.STAFF_CD   \n";
        query +=" AND  TF.MAPPING_DAY  =  TA.NEW_DAY  ORDER  BY  BRAN_CD,  STAFF_NM,  INS_DATE,  UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 통계
    public DOBJ CALLupso_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_list_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_list_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(TF.TOT_AMT)  TOT_AMT  ,  COUNT(TE.HAN_NM)  STAFF_NM  ,  TE.HAN_NM  NAME  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  ,  INSA.TINS_MST01  TE  ,  (   \n";
        query +=" SELECT  REPT_AMT-NVL(COMIS,0)  TOT_AMT  ,  MAPPING_DAY  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  TOT_AMT  ,  MAPPING_DAY  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  TF  WHERE  TA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  TA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TA.NEW_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  TF.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TE.STAFF_NUM(+)  =  TA.STAFF_CD   \n";
        query +=" AND  TF.MAPPING_DAY  =  TA.NEW_DAY   \n";
        query +=" AND  TC.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TC.BSTYP_CD  <>  'v'  GROUP  BY  TE.HAN_NM ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 휴업현황
    public DOBJ CALLupso_list_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_list_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_list_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REAS_GBN = dobj.getRetObject("S").getRecord().get("REAS_GBN");   //사유구분
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT TA.UPSO_CD , TA.UPSO_NEW_ADDR1 || DECODE(TA.UPSO_NEW_ADDR2, '', '', ', '||TA.UPSO_NEW_ADDR2) || TA.UPSO_REF_INFO UPSO_ADDR , FIDU.GET_STAFF_NM(TA.STAFF_CD) AS STAFF_NM , TC.GRADNM , TC.MONPRNCFEE , TA.UPSO_NM , TA.MNGEMSTR_NM , TA.UPSO_PHON , TC.MONPRNCFEE * GIBU.FT_GET_CLSED_MONTHS(TB.START_DAY, TB.END_DAY) TOT_AMT , TB.START_YRMN||'01' START_YRMN , TB.END_YRMN ||'01' END_YRMN , TB.CLSED_DAY , TB.CLSED_NUM , TB.CLSED_BRAN , TB.SATN_YN , FIDU.GET_STAFF_NM(TB.SATNPRES_ID) AS SATNPRES_NM , TB.SATN_DATE , TA.BRAN_CD , GIBU.GET_BRAN_NM(TA.BRAN_CD) AS BRAN_NM , NVL(TB.START_DAY, TB.START_YRMN||'01') AS START_DAY , NVL(TB.END_DAY, TB.END_YRMN ||'31') AS END_DAY , TF.REMAK AS CLSED_REASON , TF.REAS_GBN , (SELECT LISTAGG(XB.CODE_NM, ',') WITHIN  ";
        query +=" GROUP (ORDER BY XA.MNG_NUM)  ";
        query +=" FROM GIBU.TBRA_CONFIRM_DOC_ATTCH XA, FIDU.TENV_CODE XB  ";
        query +=" WHERE XA.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND XA.SEQ = TF.SEQ  ";
        query +=" AND XA.FILE_TYPE = XB.CODE_CD  ";
        query +=" AND XB.HIGH_CD = '00198'  ";
        query +=" AND XB.CODE_ETC = 'FC') AS FILE_TYPE  ";
        query +=" FROM GIBU.TBRA_UPSO TA , GIBU.TBRA_UPSO_CLSED TB , (  ";
        query +=" SELECT ZA.UPSO_CD, ZB.MONPRNCFEE, ZC.GRADNM, TRIM(ZB.BSTYP_CD) BSTYP_CD, ZB.UPSO_GRAD FROM(  ";
        query +=" SELECT A.UPSO_CD, MAX(A.JOIN_SEQ) JOIN_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE B.BRAN_CD = DECODE(:BRAN_CD, 'AL', B.BRAN_CD, :BRAN_CD)  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD  ";
        query +=" GROUP BY A.UPSO_CD ) ZA , GIBU.TBRA_UPSORTAL_INFO ZB , GIBU.TBRA_BSTYPGRAD ZC  ";
        query +=" WHERE ZB.JOIN_SEQ = ZA.JOIN_SEQ  ";
        query +=" AND ZB.UPSO_CD = ZA.UPSO_CD  ";
        query +=" AND ZC.BSTYP_CD = ZB.BSTYP_CD  ";
        query +=" AND ZC.GRAD_GBN = ZB.UPSO_GRAD ) TC , GIBU.TBRA_CONFIRM_DOC TF  ";
        query +=" WHERE TA.BRAN_CD = DECODE(:BRAN_CD, 'AL', TA.BRAN_CD, :BRAN_CD)  ";
        query +=" AND TB.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND TB.CLSED_DAY BETWEEN :START_YRMN  ";
        query +=" AND :END_YRMN  ";
        query +=" AND TB.CLSED_GBN NOT IN ('14','01','02','03','04')  ";
        query +=" AND TC.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND TF.UPSO_CD = TA.UPSO_CD  ";
        query +=" AND TF.START_DAY = TB.START_DAY  ";
        query +=" AND TF.GBN = '1'  ";
        if( !REAS_GBN.equals("") )
        {
            query +=" AND TF.REAS_GBN LIKE :REAS_GBN  ";
        }
        query +=" ORDER BY BRAN_CD, STAFF_NM, CLSED_DAY, UPSO_CD  ";
        sobj.setSql(query);
        if(!REAS_GBN.equals(""))
        {
            sobj.setString("REAS_GBN", REAS_GBN);               //사유구분
        }
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 신규등록현황
    public DOBJ CALLupso_list_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_list_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_list_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  ORDERED  */  TA.UPSO_CD  ,  TA.UPSO_NEW_ADDR1  ||  DECODE(TA.UPSO_NEW_ADDR2,  '',  '',  ',  '  ||  TA.UPSO_NEW_ADDR2)  ||  TA.UPSO_REF_INFO  AS  UPSO_ADDR  ,  FIDU.GET_STAFF_NM(TA.STAFF_CD)  AS  STAFF_NM  ,  TC.GRADNM  AS  GRADNM  ,  TC.MONPRNCFEE  AS  MONPRNCFEE  ,  TA.UPSO_NM  AS  UPSO_NM  ,  TA.MNGEMSTR_NM  AS  MNGEMSTR_NM  ,  TA.UPSO_PHON  AS  UPSO_PHON  ,  SUBSTR(TA.OPBI_DAY,  1,  6)  AS  NONPY_S_YRMN  ,  SUBSTR(TA.NEW_DAY,  1,  6)  AS  NONPY_E_YRMN  ,  SUBSTR(TA.OPBI_DAY,  1,  4)  ||  '/'  ||  SUBSTR(TA.OPBI_DAY,  5,  2)  ||  '~'  ||  SUBSTR(TA.NEW_DAY,  1,  4)  ||  '/'  ||  SUBSTR(TA.NEW_DAY,  5,  2)  AS  NONPY_YRMN  ,   \n";
        query +=" (SELECT  GIBU.FT_SPLIT(GIBU.FT_GET_NONPY_AMT(UPSO_CD,  OPBI_DAY,  SUBSTR(:END_YRMN,  1,  6)),  ',',  4)  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  TA.BRAN_CD   \n";
        query +=" AND  UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  CLSBS_YRMN  IS  NULL)  AS  NONPY_AMT  ,  (CASE  WHEN   \n";
        query +=" (SELECT  NVL(TO_CHAR(INS_DATE,  'YYYYMMDD'),  '00000000')  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  TA.BEFORE_UPSO_CD)  <=  :END_YRMN  THEN  '업,변<'  ELSE  ''  END)  ||   \n";
        query +=" (SELECT  MNGEMSTR_NM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  TA.BEFORE_UPSO_CD)  AS  BIGO  ,  TO_CHAR(TA.INS_DATE,  'YYYYMMDD')  AS  INS_DATE  ,  TA.BRAN_CD  ,  GIBU.GET_BRAN_NM(TA.BRAN_CD)  AS  BRAN_NM  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.INS_DATE  BETWEEN  TO_DATE(:START_YRMN,  'YYYYMMDD')   \n";
        query +=" AND  TO_DATE(:END_YRMN,  'YYYYMMDD')+0.99999  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  WHERE  TA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  TA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TA.INS_DATE  BETWEEN  TO_DATE(:START_YRMN,  'YYYYMMDD')   \n";
        query +=" AND  TO_DATE(:END_YRMN,  'YYYYMMDD')+0.99999   \n";
        query +=" AND  TC.BSTYP_CD  <>  'v'   \n";
        query +=" AND  TC.UPSO_CD  =  TA.UPSO_CD  ORDER  BY  BRAN_CD,  STAFF_NM,  INS_DATE,  UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 폐업현황
    // CS 에서는 폐업년월을 기준으로 월사용료, 가산금을 재계산한다. SI 에서는 청구정보를 기준으로 월사용료, 가산금을 조회한다.  금액이 밀렸다가 분납한 경우 SI 의 가산금이 더 많이 계산된다
    public DOBJ CALLupso_list_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_list_SEL7(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_list_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REAS_GBN = dobj.getRetObject("S").getRecord().get("REAS_GBN");   //사유구분
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT UPSO_CD , UPSO_ADDR , STAFF_NM , GRADNM , MONPRNCFEE , UPSO_NM , UPSO_PHON , DECODE(START_YRMN, NULL, '', START_YRMN ||'01') START_YRMN , DECODE(END_YRMN, NULL, '', END_YRMN || '01') END_YRMN , NONPY_AMT , ADDT_AMT , TOT_AMT , CLSBS_YRMN||'01' CLSBS_YRMN , ACCU_CHK , DECODE(ACCU_CHK, '1', '고소', '') ACCU_CHK_NM , MNGEMSTR_NM , REMAK , CLAIM_CHK , DECODE(CLAIM_CHK, '1','채권','') CLAIM_CHK_NM , CLSED_DAY , CLSED_NUM , CLSED_BRAN , SATNPRES_NM , SATN_YN , SATN_DATE , (SELECT MAX(NONPY_PLAN)  ";
        query +=" FROM GIBU.TBRA_CONFIRM_DOC  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND GBN = '2') NONPY_PLAN , BRAN_CD , GIBU.GET_BRAN_NM(BRAN_CD) AS BRAN_NM , CLSED_REASON , FILE_TYPE , REAS_GBN  ";
        query +=" FROM (  ";
        query +=" SELECT XA.UPSO_CD , XA.CLSED_DAY , XA.CLSED_NUM , XA.CLSED_BRAN , XB.GRADNM , XA.MONPRNCFEE , XA.UPSO_NM , XA.UPSO_ADDR , XA.UPSO_PHON , XA.START_YRMN , XA.END_YRMN , XA.NONPY_AMT , XA.ADDT_AMT , XA.TOT_AMT , XA.CLSBS_YRMN , XA.STAFF_NM , XA.MNGEMSTR_NM , DECODE(XC.BEFORE_UPSO_CD,'','','업변') REMAK , DECODE(XD.UPSO_CD, NULL, NULL, 1) ACCU_CHK , XD.REQ_ORG_AMT , XD.REQ_ADDT_AMT , DECODE(XE.UPSO_CD, NULL, NULL, 1) CLAIM_CHK , XA.SATNPRES_NM , XA.SATN_YN , XA.SATN_DATE , XA.BRAN_CD , XA.CLSED_REASON , XA.FILE_TYPE , XA.REAS_GBN  ";
        query +=" FROM (  ";
        query +=" SELECT UPSO_CD , UPSO_NM , UPSO_ADDR , UPSO_PHON , CLSBS_YRMN , STAFF_NM , MNGEMSTR_NM , CLSED_DAY , CLSED_NUM , CLSED_BRAN , GIBU.FT_SPLIT(DEMDS, ',', 0) START_YRMN , GIBU.FT_SPLIT(DEMDS, ',', 1) END_YRMN , GIBU.FT_SPLIT(DEMDS, ',', 2) BSTYP_CD , GIBU.FT_SPLIT(DEMDS, ',', 3) UPSO_GRAD , GIBU.FT_SPLIT(DEMDS, ',', 4) MONPRNCFEE , GIBU.FT_SPLIT(DEMDS, ',', 7) NONPY_AMT , GIBU.FT_SPLIT(DEMDS, ',', 8) + GIBU.FT_SPLIT(DEMDS, ',', 9) ADDT_AMT , GIBU.FT_SPLIT(DEMDS, ',', 11) TOT_AMT , SATNPRES_NM , SATN_YN , SATN_DATE , BRAN_CD , CLSED_REASON , FILE_TYPE , REAS_GBN  ";
        query +=" FROM (  ";
        query +=" SELECT TA.UPSO_CD , TA.UPSO_NM , TA.UPSO_ADDR , TA.UPSO_PHON , TA.CLSBS_YRMN , TA.STAFF_CD , TA.MNGEMSTR_NM , TA.CLSED_DAY , TA.CLSED_NUM , TA.CLSED_BRAN , GIBU.FT_GET_CLSED_DEMD_AMT(TA.UPSO_CD, 'O') DEMDS , TB.HAN_NM STAFF_NM , TC.HAN_NM SATNPRES_NM , TA.SATN_YN , TA.SATN_DATE , TA.BRAN_CD , TA.CLSED_REASON , TA.FILE_TYPE , TA.REAS_GBN  ";
        query +=" FROM (  ";
        query +=" SELECT A.UPSO_CD , A.UPSO_NM , A.UPSO_NEW_ADDR1 || DECODE(A.UPSO_NEW_ADDR2, '', '', ', '||A.UPSO_NEW_ADDR2) || A.UPSO_REF_INFO UPSO_ADDR , A.UPSO_PHON , A.CLSBS_YRMN , A.STAFF_CD , A.MNGEMSTR_NM , B.CLSED_DAY , B.CLSED_NUM , B.CLSED_BRAN , B.SATNPRES_ID , B.SATN_YN , TO_CHAR(B.SATN_DATE, 'YYYYMMDD') AS SATN_DATE , A.BRAN_CD , NVL(C.REMAK, B.REMAK) AS CLSED_REASON , (SELECT LISTAGG(TB.CODE_NM, ',') WITHIN  ";
        query +=" GROUP (ORDER BY TA.MNG_NUM)  ";
        query +=" FROM GIBU.TBRA_CONFIRM_DOC_ATTCH TA, FIDU.TENV_CODE TB  ";
        query +=" WHERE TA.UPSO_CD = A.UPSO_CD  ";
        query +=" AND TA.SEQ = C.SEQ  ";
        query +=" AND TA.FILE_TYPE = TB.CODE_CD  ";
        query +=" AND TB.HIGH_CD = '00198'  ";
        query +=" AND TB.CODE_ETC = 'FE') AS FILE_TYPE , C.REAS_GBN  ";
        query +=" FROM GIBU.TBRA_UPSO A , GIBU.TBRA_UPSO_CLSED B , GIBU.TBRA_CONFIRM_DOC C  ";
        query +=" WHERE A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.UPSO_CD = C.UPSO_CD(+)  ";
        query +=" AND B.CLSED_GBN IN ('14','01','02','03','04')  ";
        query +=" AND B.CLSED_DAY BETWEEN :START_YRMN  ";
        query +=" AND :END_YRMN  ";
        query +=" AND C.GBN(+) = '2'  ";
        query +=" AND B.START_YRMN = C.START_YRMN(+)  ";
        query +=" AND A.BRAN_CD = DECODE(:BRAN_CD, 'AL', A.BRAN_CD, :BRAN_CD)  ";
        if( !REAS_GBN.equals("") )
        {
            query +=" AND C.REAS_GBN LIKE :REAS_GBN  ";
        }
        query +=" ) TA , INSA.TINS_MST01 TB , INSA.TINS_MST01 TC  ";
        query +=" WHERE TA.STAFF_CD = TB.STAFF_NUM(+)  ";
        query +=" AND TA.SATNPRES_ID = TC.STAFF_NUM(+) ) ) XA , GIBU.TBRA_BSTYPGRAD XB , GIBU.TBRA_UPSO XC , (  ";
        query +=" SELECT UPSO_CD , REQ_ORG_AMT , REQ_ADDT_AMT  ";
        query +=" FROM (  ";
        query +=" SELECT UPSO_CD , REQ_ORG_AMT , REQ_ADDT_AMT , RANK() OVER (PARTITION BY UPSO_CD  ";
        query +=" ORDER BY ACCU_DAY DESC, ACCU_NUM DESC) RNK  ";
        query +=" FROM GIBU.TBRA_ACCU  ";
        query +=" WHERE COMPN_DAY IS NULL )  ";
        query +=" WHERE RNK = 1 ) XD , (  ";
        query +=" SELECT DISTINCT UPSO_CD  ";
        query +=" FROM GIBU.TBRA_DLGTN_CLAIM  ";
        query +=" WHERE COMPN_DAY IS NULL ) XE  ";
        query +=" WHERE XB.BSTYP_CD = XA.BSTYP_CD  ";
        query +=" AND XB.GRAD_GBN = XA.UPSO_GRAD  ";
        query +=" AND XC.BEFORE_UPSO_CD(+) = XA.UPSO_CD  ";
        query +=" AND XD.UPSO_CD(+) = XA.UPSO_CD  ";
        query +=" AND XE.UPSO_CD(+) = XA.UPSO_CD ) A  ";
        query +=" ORDER BY BRAN_CD, STAFF_NM, CLSED_DAY, UPSO_CD  ";
        sobj.setSql(query);
        if(!REAS_GBN.equals(""))
        {
            sobj.setString("REAS_GBN", REAS_GBN);               //사유구분
        }
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    //##**$$upso_list
    //##**$$end
}
