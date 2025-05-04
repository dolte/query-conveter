
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra06_p06
{
    public bra06_p06()
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
                dobj  = CALLupso_list_SEL1(Conn, dobj);           //  신규현황
                dobj  = CALLupso_list_SEL2(Conn, dobj);           //  통계
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj  = CALLupso_list_SEL3(Conn, dobj);           //  휴업현황
            }
            else
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
            dobj  = CALLupso_list_SEL1(Conn, dobj);           //  신규현황
            dobj  = CALLupso_list_SEL2(Conn, dobj);           //  통계
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLupso_list_SEL3(Conn, dobj);           //  휴업현황
        }
        else
        {
            dobj  = CALLupso_list_SEL7(Conn, dobj);           //  폐업현황
        }
        return dobj;
    }
    // 신규현황
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
        query +=" (SELECT  MNGEMSTR_NM  FROM  GIBU.TBRA_UPSO  WHERE  UPSO_CD  =  TA.BEFORE_UPSO_CD)  BIGO  ,  TO_CHAR(TA.INS_DATE,  'YYYYMMDD')  AS  INS_DATE  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  GIBU.FT_SPLIT(A.DEMDS,  ',',  4)  NONPY_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  GIBU.FT_GET_NONPY_AMT(UPSO_CD,  OPBI_DAY,  :END_YRMN)  DEMDS  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NEW_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  CLSBS_YRMN  IS  NULL  )  A  )  TD  ,  INSA.TINS_MST01  TE  ,  (   \n";
        query +=" SELECT  MIN(A.NOTE_YRMN)  START_YRMN  ,  MAX(A.NOTE_YRMN)  END_YRMN  ,  A.UPSO_CD  ,  (B.REPT_AMT-NVL(B.COMIS,0))  TOT_AMT  ,  B.MAPPING_DAY  FROM  GIBU.TBRA_NOTE  A  ,  GIBU.TBRA_REPT  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.MAPPING_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  B.DISTR_GBN  IS  NULL  GROUP  BY  A.UPSO_CD,  B.MAPPING_DAY,  B.REPT_AMT,  B.COMIS,  A.REPT_NUM,  A.REPT_DAY  UNION  ALL   \n";
        query +=" SELECT  MIN(A.NOTE_YRMN)  START_YRMN  ,  MAX(A.NOTE_YRMN)  END_YRMN  ,  A.UPSO_CD  ,  B.DISTR_AMT  TOT_AMT  ,  B.MAPPING_DAY  FROM  GIBU.TBRA_NOTE  A  ,  GIBU.TBRA_REPT_UPSO_DISTR  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.MAPPING_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM  GROUP  BY  A.UPSO_CD,  B.MAPPING_DAY,  B.DISTR_AMT  ,  A.REPT_NUM,  A.REPT_DAY  )  TF  WHERE  TA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TA.NEW_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  TC.BSTYP_CD  <>  'v'   \n";
        query +=" AND  TC.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TD.UPSO_CD(+)  =  TA.UPSO_CD   \n";
        query +=" AND  TF.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TE.STAFF_NUM(+)  =  TA.STAFF_CD   \n";
        query +=" AND  TF.MAPPING_DAY  =  TA.NEW_DAY ";
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
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  ,  INSA.TINS_MST01  TE  ,  (   \n";
        query +=" SELECT  REPT_AMT-NVL(COMIS,0)  TOT_AMT  ,  MAPPING_DAY  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  TOT_AMT  ,  MAPPING_DAY  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  )  TF  WHERE  TA.BRAN_CD  =  :BRAN_CD   \n";
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
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.UPSO_CD  ,  TA.UPSO_NEW_ADDR1  ||  DECODE(TA.UPSO_NEW_ADDR2,  '',  '',  ',  '||TA.UPSO_NEW_ADDR2)  ||  TA.UPSO_REF_INFO  UPSO_ADDR  ,  TD.HAN_NM  STAFF_NM  ,  TC.GRADNM  ,  TC.MONPRNCFEE  ,  TA.UPSO_NM  ,  TA.MNGEMSTR_NM  ,  TA.UPSO_PHON  ,  TC.MONPRNCFEE  *  (ABS(MONTHS_BETWEEN(TO_DATE(TB.END_YRMN,'YYYYMM'),TO_DATE(TB.START_YRMN,'YYYYMM')))  +  1)  TOT_AMT  ,  TB.START_YRMN||'01'  START_YRMN  ,  TB.END_YRMN  ||'01'  END_YRMN  ,  TB.CLSED_DAY  ,  TB.SATN_YN  ,  TE.HAN_NM  SATNPRES_NM  ,  TB.SATN_DATE  FROM  GIBU.TBRA_UPSO  TA  ,  GIBU.TBRA_UPSO_CLSED  TB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  ,  INSA.TINS_MST01  TD  ,  INSA.TINS_MST01  TE  WHERE  TA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TB.CLSED_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  TB.CLSED_GBN  NOT  IN  ('14','01','02','03','04')   \n";
        query +=" AND  TC.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TD.STAFF_NUM(+)  =  TA.STAFF_CD   \n";
        query +=" AND  TE.STAFF_NUM(+)  =  TB.SATNPRES_ID ";
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
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_list_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  UPSO_ADDR  ,  STAFF_NM  ,  GRADNM  ,  MONPRNCFEE  ,  UPSO_NM  ,  UPSO_PHON  ,  DECODE(START_YRMN,  NULL,  '',  START_YRMN  ||'01')  START_YRMN  ,  DECODE(END_YRMN,  NULL,  '',  END_YRMN  ||  '01')  END_YRMN  ,  DECODE(ACCU_CHK,1,REQ_ORG_AMT,NONPY_AMT)  NONPY_AMT  ,  DECODE(ACCU_CHK,1,REQ_ADDT_AMT,ADDT_AMT)  ADDT_AMT  ,  DECODE(ACCU_CHK,1,REQ_ORG_AMT+REQ_ADDT_AMT,TOT_AMT)  TOT_AMT  ,  CLSBS_YRMN||'01'  CLSBS_YRMN  ,  ACCU_CHK  ,  DECODE(ACCU_CHK,  '1',  '고³?소†Œ',  '')  ACCU_CHK_NM  ,  MNGEMSTR_NM  ,  REMAK  ,  CLAIM_CHK  ,  DECODE(CLAIM_CHK,  '1','채±?권¶Œ','')  CLAIM_CHK_NM  ,  CLSED_DAY  ,  SATNPRES_NM  ,  SATN_YN  ,  SATN_DATE  FROM  (   \n";
        query +=" SELECT  XA.UPSO_CD  ,  XA.CLSED_DAY  ,  XB.GRADNM  ,  XA.MONPRNCFEE  ,  XA.UPSO_NM  ,  XA.UPSO_ADDR  ,  XA.UPSO_PHON  ,  XA.START_YRMN  ,  XA.END_YRMN  ,  XA.NONPY_AMT  ,  XA.ADDT_AMT  ,  XA.TOT_AMT  ,  XA.CLSBS_YRMN  ,  XA.STAFF_NM  ,  XA.MNGEMSTR_NM  ,  DECODE(XC.BEFORE_UPSO_CD,'','','업?…변³?')  REMAK  ,  DECODE(XD.UPSO_CD,  NULL,  NULL,  1)  ACCU_CHK  ,  XD.REQ_ORG_AMT  ,  XD.REQ_ADDT_AMT  ,  DECODE(XE.UPSO_CD,  NULL,  NULL,  1)  CLAIM_CHK  ,  XA.SATNPRES_NM  ,  XA.SATN_YN  ,  XA.SATN_DATE  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  UPSO_NM  ,  UPSO_ADDR  ,  UPSO_PHON  ,  CLSBS_YRMN  ,  STAFF_NM  ,  MNGEMSTR_NM  ,  CLSED_DAY  ,  GIBU.FT_SPLIT(DEMDS,  ',',  0)  START_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  1)  END_YRMN  ,  GIBU.FT_SPLIT(DEMDS,  ',',  2)  BSTYP_CD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  3)  UPSO_GRAD  ,  GIBU.FT_SPLIT(DEMDS,  ',',  4)  MONPRNCFEE  ,  GIBU.FT_SPLIT(DEMDS,  ',',  7)  NONPY_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  8)  +  GIBU.FT_SPLIT(DEMDS,  ',',  9)  ADDT_AMT  ,  GIBU.FT_SPLIT(DEMDS,  ',',  11)  TOT_AMT  ,  SATNPRES_NM  ,  SATN_YN  ,  SATN_DATE  FROM  (   \n";
        query +=" SELECT  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.UPSO_ADDR  ,  TA.UPSO_PHON  ,  TA.CLSBS_YRMN  ,  TA.STAFF_CD  ,  TA.MNGEMSTR_NM  ,  TA.CLSED_DAY  ,  GIBU.FT_GET_CLSED_DEMD_AMT(TA.UPSO_CD,  'O')  DEMDS  ,  TB.HAN_NM  STAFF_NM  ,  TC.HAN_NM  SATNPRES_NM  ,  TA.SATN_YN  ,  TA.SATN_DATE  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.UPSO_NM  ,  A.UPSO_NEW_ADDR1  ||  DECODE(A.UPSO_NEW_ADDR2,  '',  '',  ',  '||A.UPSO_NEW_ADDR2)  ||  A.UPSO_REF_INFO  UPSO_ADDR  ,  A.UPSO_PHON  ,  A.CLSBS_YRMN  ,  A.STAFF_CD  ,  A.MNGEMSTR_NM  ,  B.CLSED_DAY  ,  B.SATNPRES_ID  ,  B.SATN_YN  ,  TO_CHAR(B.SATN_DATE,  'YYYYMMDD')  AS  SATN_DATE  FROM  GIBU.TBRA_UPSO  A  ,  GIBU.TBRA_UPSO_CLSED  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.CLSED_GBN  IN  ('14','01','02','03','04')   \n";
        query +=" AND  B.CLSED_DAY  BETWEEN  :START_YRMN||'01'   \n";
        query +=" AND  :END_YRMN||'31'   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD  )  TA  ,  INSA.TINS_MST01  TB  ,  INSA.TINS_MST01  TC  WHERE  TA.STAFF_CD  =  TB.STAFF_NUM(+)   \n";
        query +=" AND  TA.SATNPRES_ID  =  TC.STAFF_NUM(+)  )  )  XA  ,  GIBU.TBRA_BSTYPGRAD  XB  ,  GIBU.TBRA_UPSO  XC  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REQ_ORG_AMT  ,  REQ_ADDT_AMT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  REQ_ORG_AMT  ,  REQ_ADDT_AMT  ,  RANK()  OVER  (PARTITION  BY  UPSO_CD  ORDER  BY  ACCU_DAY  DESC,  ACCU_NUM  DESC)  RNK  FROM  GIBU.TBRA_ACCU  WHERE  COMPN_DAY  IS  NULL  )  WHERE  RNK  =  1  )  XD  ,  (   \n";
        query +=" SELECT  DISTINCT  UPSO_CD  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  COMPN_DAY  IS  NULL  )  XE  WHERE  XB.BSTYP_CD  =  XA.BSTYP_CD   \n";
        query +=" AND  XB.GRAD_GBN  =  XA.UPSO_GRAD   \n";
        query +=" AND  XC.BEFORE_UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XD.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XE.UPSO_CD(+)  =  XA.UPSO_CD  ) ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    //##**$$upso_list
    //##**$$end
}
