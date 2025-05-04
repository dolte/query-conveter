
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s01_2
{
    public bra04_s01_2()
    {
    }
    //##**$$rept_detail_select2
    /*
    */
    public DOBJ CTLrept_detail_select2(DOBJ dobj)
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
            dobj  = CALLrept_detail_select2_SEL1(Conn, dobj);           //  업소 정보 조회
            dobj  = CALLrept_detail_select2_SEL2(Conn, dobj);           //  원장정보 조회
            if(!dobj.getRetObject("SEL2").getRecord().get("DISTR_GBN").equals(""))
            {
                if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("22"))
                {
                    dobj  = CALLrept_detail_select2_SEL27(Conn, dobj);           //  고소 청구정보
                    dobj  = CALLrept_detail_select2_SEL28(Conn, dobj);           //  청구 정보 조회
                    dobj  = CALLrept_detail_select2_MRG1( dobj);        //  입금결과 취합
                    dobj  = CALLrept_detail_select2_MRG2( dobj);        //  청구결과 취합
                }
                else if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("06"))
                {
                    dobj  = CALLrept_detail_select2_SEL29(Conn, dobj);           //  채권의뢰
                    dobj  = CALLrept_detail_select2_SEL30(Conn, dobj);           //  청구 정보 조회
                    dobj  = CALLrept_detail_select2_MRG1( dobj);        //  입금결과 취합
                    dobj  = CALLrept_detail_select2_MRG2( dobj);        //  청구결과 취합
                }
                else
                {
                    dobj  = CALLrept_detail_select2_SEL9(Conn, dobj);           //  분배 청구정보
                    dobj  = CALLrept_detail_select2_SEL13(Conn, dobj);           //  청구 정보 조회
                    dobj  = CALLrept_detail_select2_MRG1( dobj);        //  입금결과 취합
                    dobj  = CALLrept_detail_select2_MRG2( dobj);        //  청구결과 취합
                }
            }
            else
            {
                if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("22"))
                {
                    dobj  = CALLrept_detail_select2_SEL4(Conn, dobj);           //  고소 청구정보
                    dobj  = CALLrept_detail_select2_SEL12(Conn, dobj);           //  청구 정보 조회
                    dobj  = CALLrept_detail_select2_MRG1( dobj);        //  입금결과 취합
                    dobj  = CALLrept_detail_select2_MRG2( dobj);        //  청구결과 취합
                }
                else if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("06"))
                {
                    dobj  = CALLrept_detail_select2_SEL8(Conn, dobj);           //  채권의뢰
                    dobj  = CALLrept_detail_select2_SEL19(Conn, dobj);           //  청구 정보 조회
                    dobj  = CALLrept_detail_select2_MRG1( dobj);        //  입금결과 취합
                    dobj  = CALLrept_detail_select2_MRG2( dobj);        //  청구결과 취합
                }
                else if( dobj.getRetObject("S").getRecord().get("REPT_GBN").equals("01"))
                {
                    dobj  = CALLrept_detail_select2_SEL6(Conn, dobj);           //  자동이체 청구정보
                    dobj  = CALLrept_detail_select2_SEL11(Conn, dobj);           //  청구 정보 조회
                    dobj  = CALLrept_detail_select2_MRG1( dobj);        //  입금결과 취합
                    dobj  = CALLrept_detail_select2_MRG2( dobj);        //  청구결과 취합
                }
                else
                {
                    dobj  = CALLrept_detail_select2_SEL10(Conn, dobj);           //  그외 청구정보
                    dobj  = CALLrept_detail_select2_SEL14(Conn, dobj);           //  청구 정보 조회
                    dobj  = CALLrept_detail_select2_MRG1( dobj);        //  입금결과 취합
                    dobj  = CALLrept_detail_select2_MRG2( dobj);        //  청구결과 취합
                }
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
    public DOBJ CTLrept_detail_select2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_detail_select2_SEL1(Conn, dobj);           //  업소 정보 조회
        dobj  = CALLrept_detail_select2_SEL2(Conn, dobj);           //  원장정보 조회
        if(!dobj.getRetObject("SEL2").getRecord().get("DISTR_GBN").equals(""))
        {
            if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("22"))
            {
                dobj  = CALLrept_detail_select2_SEL27(Conn, dobj);           //  고소 청구정보
                dobj  = CALLrept_detail_select2_SEL28(Conn, dobj);           //  청구 정보 조회
                dobj  = CALLrept_detail_select2_MRG1( dobj);        //  입금결과 취합
                dobj  = CALLrept_detail_select2_MRG2( dobj);        //  청구결과 취합
            }
            else if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("06"))
            {
                dobj  = CALLrept_detail_select2_SEL29(Conn, dobj);           //  채권의뢰
                dobj  = CALLrept_detail_select2_SEL30(Conn, dobj);           //  청구 정보 조회
                dobj  = CALLrept_detail_select2_MRG1( dobj);        //  입금결과 취합
                dobj  = CALLrept_detail_select2_MRG2( dobj);        //  청구결과 취합
            }
            else
            {
                dobj  = CALLrept_detail_select2_SEL9(Conn, dobj);           //  분배 청구정보
                dobj  = CALLrept_detail_select2_SEL13(Conn, dobj);           //  청구 정보 조회
                dobj  = CALLrept_detail_select2_MRG1( dobj);        //  입금결과 취합
                dobj  = CALLrept_detail_select2_MRG2( dobj);        //  청구결과 취합
            }
        }
        else
        {
            if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("22"))
            {
                dobj  = CALLrept_detail_select2_SEL4(Conn, dobj);           //  고소 청구정보
                dobj  = CALLrept_detail_select2_SEL12(Conn, dobj);           //  청구 정보 조회
                dobj  = CALLrept_detail_select2_MRG1( dobj);        //  입금결과 취합
                dobj  = CALLrept_detail_select2_MRG2( dobj);        //  청구결과 취합
            }
            else if( dobj.getRetObject("SEL2").getRecord().get("ACCU_GBN").equals("06"))
            {
                dobj  = CALLrept_detail_select2_SEL8(Conn, dobj);           //  채권의뢰
                dobj  = CALLrept_detail_select2_SEL19(Conn, dobj);           //  청구 정보 조회
                dobj  = CALLrept_detail_select2_MRG1( dobj);        //  입금결과 취합
                dobj  = CALLrept_detail_select2_MRG2( dobj);        //  청구결과 취합
            }
            else if( dobj.getRetObject("S").getRecord().get("REPT_GBN").equals("01"))
            {
                dobj  = CALLrept_detail_select2_SEL6(Conn, dobj);           //  자동이체 청구정보
                dobj  = CALLrept_detail_select2_SEL11(Conn, dobj);           //  청구 정보 조회
                dobj  = CALLrept_detail_select2_MRG1( dobj);        //  입금결과 취합
                dobj  = CALLrept_detail_select2_MRG2( dobj);        //  청구결과 취합
            }
            else
            {
                dobj  = CALLrept_detail_select2_SEL10(Conn, dobj);           //  그외 청구정보
                dobj  = CALLrept_detail_select2_SEL14(Conn, dobj);           //  청구 정보 조회
                dobj  = CALLrept_detail_select2_MRG1( dobj);        //  입금결과 취합
                dobj  = CALLrept_detail_select2_MRG2( dobj);        //  청구결과 취합
            }
        }
        return dobj;
    }
    // 업소 정보 조회
    public DOBJ CALLrept_detail_select2_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.MONPRNCFEE  ,  B.BSTYP_CD  ,  B.UPSO_GRAD  ,  B.GRAD  ,  C.GRADNM  ,  A.MNGEMSTR_NM  ,  A.CLSBS_YRMN  ,  A.STAFF_CD  ,  E.HAN_NM  STAFF_NM  ,  A.UPSO_CD,  A.UPSO_NM  ,  GIBU.FT_GET_LAST_REPT_YRMN(:UPSO_CD,  8)  LAST_YRMN  ,  NVL(D.BALANCE,  0)  BALANCE  ,  NVL(F.ACCU_CNT,  0)  ACCU_CNT  ,  F.JUDG_CD  ,  A.BRAN_CD  ,  G.CLAIM_CNT  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  ,  UPSO_GRAD  ,  MONPRNCFEE  ,  GRAD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  ,  UPSO_GRAD  ,  MONPRNCFEE  ,  TRIM(BSTYP_CD)  ||  UPSO_GRAD  GRAD  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  CHG_DAY  DESC,  CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  ,  (   \n";
        query +=" SELECT  :UPSO_CD  UPSO_CD  ,  NVL(SUM(BALANCE),  0)  BALANCE  FROM  (   \n";
        query +=" SELECT  BALANCE  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  PROC_DAY  DESC,  PROC_NUM  DESC  )  WHERE  ROWNUM  =  1  )  D  ,  INSA.TINS_MST01  E  ,  (   \n";
        query +=" SELECT  DECODE(COMPN_DAY,  NULL,  1,  DECODE(JUDG_CD,  2,  1,  4,  1,  0))  ACCU_CNT  ,  JUDG_CD  ,  UPSO_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  COMPN_DAY  ,  JUDG_CD  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  ORDER  BY  ACCU_DAY  DESC  )  WHERE  ROWNUM  =  1  )  F  ,  (   \n";
        query +=" SELECT  COUNT(UPSO_CD)  CLAIM_CNT  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  COMPN_DAY  IS  NULL  )  G  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  C.GRAD_GBN  =  B.UPSO_GRAD   \n";
        query +=" AND  D.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  F.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  E.STAFF_NUM(+)  =  A.STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 원장정보 조회
    public DOBJ CALLrept_detail_select2_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //원장 년월
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  MAX(A.DEMD_YRMN)  DEMD_YRMN  ,  B.DISTR_GBN  ,  A.DISTR_SEQ  ,  A.ACCU_GBN  FROM  GIBU.TBRA_NOTE  A  ,  GIBU.TBRA_REPT  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  A.NOTE_NUM  =  '0001'   \n";
        query +=" AND  B.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  =  A.REPT_GBN  GROUP  BY  A.UPSO_CD,  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN,  A.ACCU_GBN,  B.DISTR_GBN,  A.DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //원장 년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 고소 청구정보
    public DOBJ CALLrept_detail_select2_SEL27(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL27");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL27");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL27(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL27");
        rvobj.Println("SEL27");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL27(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL2").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL2").getRecord().get("REPT_NUM");   //입금 번호
        String   DEMD_YRMN = dobj.getRetObject("SEL2").getRecord().get("DEMD_YRMN");   //청구 년월
        String   REPT_GBN = dobj.getRetObject("SEL2").getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        String   DISTR_SEQ = dobj.getRetObject("SEL2").getRecord().get("DISTR_SEQ");   //분배 순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN  ,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  C.SOL_ORG_AMT  TOT_USE_AMT  ,  C.SOL_ADDT_AMT  TOT_ADDT_AMT  ,  C.SOL_ORG_AMT  +  C.SOL_ADDT_AMT  TOT_DEMD_AMT  ,  B.BRAN_CD  REPT_BRAN  ,  B.RECV_DAY  ,  B.REPT_AMT  ,  B.COMIS  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  D.BANK_NM  ,  B.REMAK  ,  B.DISTR_GBN  ,  B.DISTR_SEQ  ,  C.COMPN_DAY  ,  B.ADJ_AMT  ,  B.ADJ_GBN  ,  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  FROM  (   \n";
        query +=" SELECT  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  DISTR_SEQ  =  :DISTR_SEQ  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  A  ,  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.REPT_DAY  ,  XA.REPT_NUM  ,  XA.REPT_GBN  ,  XA.DISTR_GBN  ,  XA.DISTR_SEQ  ,  XA.DISTR_AMT  AS  REPT_AMT  ,  XB.RECV_DAY  ,  XB.COMIS  ,  XB.BANK_CD  ,  XB.ACCN_NUM  ,  XA.REMAK  ,  XA.ADJ_AMT  ,  XA.ADJ_GBN  FROM  GIBU.TBRA_REPT_UPSO_DISTR  XA  ,  GIBU.TBRA_REPT  XB  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XA.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  XA.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  XA.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  XB.REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XA.REPT_NUM   \n";
        query +=" AND  XB.REPT_GBN  =  XA.REPT_GBN  )  B  ,  (   \n";
        query +=" SELECT  SOL_ORG_AMT  ,  SOL_ADDT_AMT  ,  COMPN_DAY  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  DISTR_SEQ  =  :DISTR_SEQ  )  C  ,  ACCT.TCAC_BANK  D  WHERE  D.BANK_CD  =  B.BANK_CD   \n";
        query +=" AND  A.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  A.DISTR_SEQ(+)  =  B.DISTR_SEQ   \n";
        query +=" AND  C.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  C.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  C.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  C.DISTR_SEQ(+)  =  B.DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("DISTR_SEQ", DISTR_SEQ);               //분배 순번
        return sobj;
    }
    // 청구 정보 조회
    // 입금년월에 해당하는 청구정보를 조회한다
    public DOBJ CALLrept_detail_select2_SEL28(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL28");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL28");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL28(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL28");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL28(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //NOTE_YRMN
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.UPSO_CD  ,  XC.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  DECODE(XC.START_YRMN,  NULL,  NULL,  XC.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(XC.END_YRMN,  NULL,  NULL,  XC.END_YRMN  ||  '01')  END_YRMN  ,  XC.MONPRNCFEE  ,  XC.TOT_DEMD_AMT  ,  XC.TOT_USE_AMT  ,  XC.TOT_ADDT_AMT  ,  XC.TOT_EADDT_AMT  ,  XC.DEMD_GBN  ,  XD.CODE_NM  DEMD_GBN_NM  ,  XA.NOTE_YRMN  FROM  GIBU.TBRA_NOTE  XA  ,  GIBU.TBRA_DEMD_REPT  XB  ,  GIBU.TBRA_DEMD_OCR  XC  ,  FIDU.TENV_CODE  XD  WHERE  XA.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XA.REPT_NUM   \n";
        query +=" AND  XB.REPT_GBN  =  XA.REPT_GBN   \n";
        query +=" AND  XB.DISTR_SEQ  =  XA.DISTR_SEQ   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.DEMD_YRMN  =  XB.DEMD_YRMN   \n";
        query +=" AND  XD.HIGH_CD  =  '00141'   \n";
        query +=" AND  XD.CODE_CD  =  XC.DEMD_GBN  ORDER  BY  XC.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //NOTE_YRMN
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 입금결과 취합
    public DOBJ CALLrept_detail_select2_MRG1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL4, SEL6, SEL8, SEL9, SEL10, SEL27, SEL29","");
        rvobj.setName("MRG1") ;
        rvobj.Println("MRG1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 청구결과 취합
    public DOBJ CALLrept_detail_select2_MRG2(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG2");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL11, SEL12, SEL13, SEL14, SEL19, SEL28, SEL30","");
        rvobj.setName("MRG2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 채권의뢰
    public DOBJ CALLrept_detail_select2_SEL29(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL29");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL29");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL29(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL29");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL29(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL2").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL2").getRecord().get("REPT_NUM");   //입금 번호
        String   DEMD_YRMN = dobj.getRetObject("SEL2").getRecord().get("DEMD_YRMN");   //청구 년월
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        String   DISTR_SEQ = dobj.getRetObject("SEL2").getRecord().get("DISTR_SEQ");   //분배 순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN  ,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  C.SOL_AMT  TOT_USE_AMT  ,  0  TOT_ADDT_AMT  ,  C.SOL_AMT  TOT_DEMD_AMT  ,  B.BRAN_CD  REPT_BRAN  ,  B.RECV_DAY  ,  B.REPT_AMT  ,  B.COMIS  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  D.BANK_NM  ,  B.REMAK  ,  B.DISTR_GBN  ,  B.DISTR_SEQ  ,  B.ADJ_AMT  ,  B.ADJ_GBN  ,  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  FROM  (   \n";
        query +=" SELECT  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  DISTR_SEQ  =  :DISTR_SEQ  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  A  ,  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.REPT_DAY  ,  XA.REPT_NUM  ,  XA.REPT_GBN  ,  XA.DISTR_GBN  ,  XA.DISTR_SEQ  ,  XA.DISTR_AMT  AS  REPT_AMT  ,  XB.RECV_DAY  ,  XB.COMIS  ,  XB.BANK_CD  ,  XB.ACCN_NUM  ,  XA.REMAK  ,  XA.ADJ_AMT  ,  XA.ADJ_GBN  FROM  GIBU.TBRA_REPT_UPSO_DISTR  XA  ,  GIBU.TBRA_REPT  XB  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XA.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  XA.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  XA.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  XB.REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XA.REPT_NUM   \n";
        query +=" AND  XB.REPT_GBN  =  XA.REPT_GBN  )  B  ,  (   \n";
        query +=" SELECT  SOL_AMT  ,  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  :UPSO_CD  )  C  ,  ACCT.TCAC_BANK  D  WHERE  D.BANK_CD  =  B.BANK_CD   \n";
        query +=" AND  A.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  A.DISTR_SEQ(+)  =  B.DISTR_SEQ   \n";
        query +=" AND  C.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  C.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  C.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  C.DISTR_SEQ(+)  =  B.DISTR_SEQ ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("DISTR_SEQ", DISTR_SEQ);               //분배 순번
        return sobj;
    }
    // 청구 정보 조회
    // 입금년월에 해당하는 청구정보를 조회한다
    public DOBJ CALLrept_detail_select2_SEL30(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL30");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL30");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL30(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL30");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL30(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //NOTE_YRMN
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.UPSO_CD  ,  XC.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  DECODE(XC.START_YRMN,  NULL,  NULL,  XC.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(XC.END_YRMN,  NULL,  NULL,  XC.END_YRMN  ||  '01')  END_YRMN  ,  XC.TOT_DEMD_AMT  ,  XC.TOT_USE_AMT  ,  XC.TOT_ADDT_AMT  ,  XC.TOT_EADDT_AMT  ,  XC.DEMD_GBN  ,  XD.CODE_NM  DEMD_GBN_NM  ,  XA.NOTE_YRMN  FROM  GIBU.TBRA_NOTE  XA  ,  GIBU.TBRA_DEMD_REPT  XB  ,  GIBU.TBRA_DEMD_OCR  XC  ,  FIDU.TENV_CODE  XD  WHERE  XA.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XA.REPT_NUM   \n";
        query +=" AND  XB.REPT_GBN  =  XA.REPT_GBN   \n";
        query +=" AND  XB.DISTR_SEQ  =  XA.DISTR_SEQ   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.DEMD_YRMN  =  XB.DEMD_YRMN   \n";
        query +=" AND  XD.HIGH_CD  =  '00141'   \n";
        query +=" AND  XD.CODE_CD  =  XC.DEMD_GBN  ORDER  BY  XC.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //NOTE_YRMN
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 분배 청구정보
    public DOBJ CALLrept_detail_select2_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL9(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL2").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL2").getRecord().get("REPT_NUM");   //입금 번호
        String   DEMD_YRMN = dobj.getRetObject("SEL2").getRecord().get("DEMD_YRMN");   //청구 년월
        String   REPT_GBN = dobj.getRetObject("SEL2").getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        String   DISTR_SEQ = dobj.getRetObject("SEL2").getRecord().get("DISTR_SEQ");   //분배 순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN  ,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  NULL  TOT_USE_AMT  ,  NULL  TOT_ADDT_AMT  ,  NULL  TOT_DEMD_AMT  ,  B.BRAN_CD  REPT_BRAN  ,  B.RECV_DAY  ,  B.DISTR_AMT  AS  REPT_AMT  ,  B.COMIS  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  C.BANK_NM  ,  B.REMAK  ,  B.DISTR_GBN  ,  B.DISTR_SEQ  ,  B.ADJ_AMT  ,  B.ADJ_GBN  ,  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  FROM  (   \n";
        query +=" SELECT  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  DISTR_SEQ  =  :DISTR_SEQ  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ  )  A  ,  (   \n";
        query +=" SELECT  XA.BRAN_CD  ,  XA.REPT_DAY  ,  XA.REPT_NUM  ,  XA.REPT_GBN  ,  XA.DISTR_GBN  ,  XA.DISTR_SEQ  ,  XA.DISTR_AMT  ,  XB.RECV_DAY  ,  XB.COMIS  ,  XB.BANK_CD  ,  XB.ACCN_NUM  ,  XA.REMAK  ,  XA.ADJ_AMT  ,  XA.ADJ_GBN  FROM  GIBU.TBRA_REPT_UPSO_DISTR  XA  ,  GIBU.TBRA_REPT  XB  WHERE  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XA.REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  XA.REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  XA.REPT_GBN  =  :REPT_GBN   \n";
        query +=" AND  XB.REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XA.REPT_NUM   \n";
        query +=" AND  XB.REPT_GBN  =  XA.REPT_GBN  )  B  ,  ACCT.TCAC_BANK  C  WHERE  A.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  A.DISTR_SEQ(+)  =  B.DISTR_SEQ   \n";
        query +=" AND  C.BANK_CD  (+)  =  B.BANK_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("DISTR_SEQ", DISTR_SEQ);               //분배 순번
        return sobj;
    }
    // 청구 정보 조회
    // 입금년월에 해당하는 청구정보를 조회한다
    public DOBJ CALLrept_detail_select2_SEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL13");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL13(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL13");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //NOTE_YRMN
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.UPSO_CD  ,  XC.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  DECODE(XC.START_YRMN,  NULL,  NULL,  XC.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(XC.END_YRMN,  NULL,  NULL,  XC.END_YRMN  ||  '01')  END_YRMN  ,  XC.TOT_DEMD_AMT  ,  XC.TOT_USE_AMT  ,  XC.TOT_ADDT_AMT  ,  XC.TOT_EADDT_AMT  ,  XC.DEMD_GBN  ,  XD.CODE_NM  DEMD_GBN_NM  ,  XA.NOTE_YRMN  FROM  GIBU.TBRA_NOTE  XA  ,  GIBU.TBRA_DEMD_REPT  XB  ,  GIBU.TBRA_DEMD_OCR  XC  ,  FIDU.TENV_CODE  XD  WHERE  XA.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XA.REPT_NUM   \n";
        query +=" AND  XB.REPT_GBN  =  XA.REPT_GBN   \n";
        query +=" AND  XB.DISTR_SEQ  =  XA.DISTR_SEQ   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.DEMD_YRMN  =  XB.DEMD_YRMN   \n";
        query +=" AND  XD.HIGH_CD  =  '00141'   \n";
        query +=" AND  XD.CODE_CD  =  XC.DEMD_GBN  ORDER  BY  XC.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //NOTE_YRMN
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 고소 청구정보
    public DOBJ CALLrept_detail_select2_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL2").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL2").getRecord().get("REPT_NUM");   //입금 번호
        String   DEMD_YRMN = dobj.getRetObject("SEL2").getRecord().get("DEMD_YRMN");   //청구 년월
        String   REPT_GBN = dobj.getRetObject("SEL2").getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN  ,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  C.SOL_ORG_AMT  TOT_USE_AMT  ,  C.SOL_ADDT_AMT  TOT_ADDT_AMT  ,  C.SOL_ORG_AMT  +  C.SOL_ADDT_AMT  TOT_DEMD_AMT  ,  B.BRAN_CD  REPT_BRAN  ,  B.RECV_DAY  ,  B.REPT_AMT  ,  B.COMIS  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  D.BANK_NM  ,  B.REMAK  ,  B.DISTR_GBN  ,  B.DISTR_SEQ  ,  C.COMPN_DAY  ,  B.ADJ_AMT  ,  B.ADJ_GBN  ,  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  FROM  (   \n";
        query +=" SELECT  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN  )  A  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  NULL  DISTR_GBN  ,  NULL  DISTR_SEQ  ,  REPT_AMT  ,  RECV_DAY  ,  COMIS  ,  BANK_CD  ,  ACCN_NUM  ,  REMAK  ,  ADJ_AMT  ,  ADJ_GBN  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  B  ,  (   \n";
        query +=" SELECT  SOL_ORG_AMT  ,  SOL_ADDT_AMT  ,  COMPN_DAY  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_ACCU  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  C  ,  ACCT.TCAC_BANK  D  WHERE  D.BANK_CD  =  B.BANK_CD   \n";
        query +=" AND  A.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  C.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  C.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  C.REPT_GBN(+)  =  B.REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 청구 정보 조회
    // 입금년월에 해당하는 청구정보를 조회한다
    public DOBJ CALLrept_detail_select2_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL12(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL12");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //NOTE_YRMN
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.UPSO_CD  ,  XC.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  DECODE(XC.START_YRMN,  NULL,  NULL,  XC.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(XC.END_YRMN,  NULL,  NULL,  XC.END_YRMN  ||  '01')  END_YRMN  ,  XC.MONPRNCFEE  ,  XC.TOT_DEMD_AMT  ,  XC.TOT_USE_AMT  ,  XC.TOT_ADDT_AMT  ,  XC.TOT_EADDT_AMT  ,  XC.DEMD_GBN  ,  XD.CODE_NM  DEMD_GBN_NM  ,  XA.NOTE_YRMN  FROM  GIBU.TBRA_NOTE  XA  ,  GIBU.TBRA_DEMD_REPT  XB  ,  GIBU.TBRA_DEMD_OCR  XC  ,  FIDU.TENV_CODE  XD  WHERE  XA.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XA.REPT_NUM   \n";
        query +=" AND  XB.REPT_GBN  =  XA.REPT_GBN   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.DEMD_YRMN  =  XB.DEMD_YRMN   \n";
        query +=" AND  XD.HIGH_CD  =  '00141'   \n";
        query +=" AND  XD.CODE_CD  =  XC.DEMD_GBN  ORDER  BY  XC.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //NOTE_YRMN
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 채권의뢰
    public DOBJ CALLrept_detail_select2_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL2").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL2").getRecord().get("REPT_NUM");   //입금 번호
        String   DEMD_YRMN = dobj.getRetObject("SEL2").getRecord().get("DEMD_YRMN");   //청구 년월
        String   REPT_GBN = dvobj.getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN  ,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  C.SOL_AMT  TOT_USE_AMT  ,  0  TOT_ADDT_AMT  ,  C.SOL_AMT  TOT_DEMD_AMT  ,  B.BRAN_CD  REPT_BRAN  ,  B.RECV_DAY  ,  B.REPT_AMT  ,  B.COMIS  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  D.BANK_NM  ,  B.REMAK  ,  B.DISTR_GBN  ,  B.DISTR_SEQ  ,  B.ADJ_AMT  ,  B.ADJ_GBN  ,  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  FROM  (   \n";
        query +=" SELECT  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN  )  A  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  NULL  DISTR_GBN  ,  NULL  DISTR_SEQ  ,  REPT_AMT  ,  RECV_DAY  ,  COMIS  ,  BANK_CD  ,  ACCN_NUM  ,  REMAK  ,  ADJ_AMT  ,  ADJ_GBN  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  B  ,  (   \n";
        query +=" SELECT  SOL_AMT  ,  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_DLGTN_CLAIM  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  C  ,  ACCT.TCAC_BANK  D  WHERE  D.BANK_CD  =  B.BANK_CD   \n";
        query +=" AND  A.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  C.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  C.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  C.REPT_GBN(+)  =  B.REPT_GBN ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 청구 정보 조회
    // 입금년월에 해당하는 청구정보를 조회한다
    public DOBJ CALLrept_detail_select2_SEL19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL19");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL19");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL19(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL19");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //NOTE_YRMN
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.UPSO_CD  ,  XC.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  DECODE(XC.START_YRMN,  NULL,  NULL,  XC.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(XC.END_YRMN,  NULL,  NULL,  XC.END_YRMN  ||  '01')  END_YRMN  ,  XC.TOT_DEMD_AMT  ,  XC.TOT_USE_AMT  ,  XC.TOT_ADDT_AMT  ,  XC.TOT_EADDT_AMT  ,  XC.DEMD_GBN  ,  XD.CODE_NM  DEMD_GBN_NM  ,  XA.NOTE_YRMN  FROM  GIBU.TBRA_NOTE  XA  ,  GIBU.TBRA_DEMD_REPT  XB  ,  GIBU.TBRA_DEMD_OCR  XC  ,  FIDU.TENV_CODE  XD  WHERE  XA.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XA.REPT_NUM   \n";
        query +=" AND  XB.REPT_GBN  =  XA.REPT_GBN   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.DEMD_YRMN  =  XB.DEMD_YRMN   \n";
        query +=" AND  XD.HIGH_CD  =  '00141'   \n";
        query +=" AND  XD.CODE_CD  =  XC.DEMD_GBN  ORDER  BY  XC.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //NOTE_YRMN
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 자동이체 청구정보
    public DOBJ CALLrept_detail_select2_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL2").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL2").getRecord().get("REPT_NUM");   //입금 번호
        String   DEMD_YRMN = dobj.getRetObject("SEL2").getRecord().get("DEMD_YRMN");   //청구 년월
        String   REPT_GBN = dobj.getRetObject("SEL2").getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN  ,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  C.TOT_USE_AMT  ,  C.TOT_ADDT_AMT  ,  C.TOT_DEMD_AMT  ,  B.BRAN_CD  REPT_BRAN  ,  B.RECV_DAY  ,  B.REPT_AMT  ,  B.COMIS  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  D.BANK_NM  ,  B.REMAK  ,  B.DISTR_GBN  ,  B.DISTR_SEQ  ,  B.ADJ_AMT  ,  B.ADJ_GBN  ,  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  FROM  (   \n";
        query +=" SELECT  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN  )  A  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  NULL  DISTR_GBN  ,  NULL  DISTR_SEQ  ,  REPT_AMT  ,  RECV_DAY  ,  COMIS  ,  BANK_CD  ,  ACCN_NUM  ,  REMAK  ,  UPSO_CD  ,  ADJ_AMT  ,  ADJ_GBN  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  B  ,  (   \n";
        query +=" SELECT  TOT_USE_AMT  ,  TOT_ADDT_AMT  ,  TOT_DEMD_AMT  ,  UPSO_CD  FROM  GIBU.TBRA_DEMD_AUTO  WHERE  DEMD_YRMN  ||  UPSO_CD  =  (   \n";
        query +=" SELECT  MAX(DEMD_YRMN)  ||  :UPSO_CD  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  )  C  ,  ACCT.TCAC_BANK  D  WHERE  D.BANK_CD  =  B.BANK_CD   \n";
        query +=" AND  A.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  C.UPSO_CD  (+)  =  B.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 청구 정보 조회
    // 입금년월에 해당하는 청구정보를 조회한다
    public DOBJ CALLrept_detail_select2_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //NOTE_YRMN
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.UPSO_CD  ,  XC.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  DECODE(XC.START_YRMN,  NULL,  NULL,  XC.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(XC.END_YRMN,  NULL,  NULL,  XC.END_YRMN  ||  '01')  END_YRMN  ,  XC.TOT_DEMD_AMT  ,  XC.TOT_USE_AMT  ,  XC.TOT_ADDT_AMT  ,  XC.TOT_EADDT_AMT  ,  XC.DEMD_GBN  ,  XD.CODE_NM  DEMD_GBN_NM  ,  XA.NOTE_YRMN  FROM  GIBU.TBRA_NOTE  XA  ,  GIBU.TBRA_DEMD_REPT  XB  ,  GIBU.TBRA_DEMD_AUTO  XC  ,  FIDU.TENV_CODE  XD  WHERE  XA.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XA.REPT_NUM   \n";
        query +=" AND  XB.REPT_GBN  =  XA.REPT_GBN   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.DEMD_YRMN  =  XB.DEMD_YRMN   \n";
        query +=" AND  XD.HIGH_CD  =  '00141'   \n";
        query +=" AND  XD.CODE_CD  =  XC.DEMD_GBN  ORDER  BY  XC.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //NOTE_YRMN
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 그외 청구정보
    public DOBJ CALLrept_detail_select2_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL10(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("SEL2").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL2").getRecord().get("REPT_NUM");   //입금 번호
        String   DEMD_YRMN = dobj.getRetObject("SEL2").getRecord().get("DEMD_YRMN");   //청구 년월
        String   REPT_GBN = dobj.getRetObject("SEL2").getRecord().get("REPT_GBN");   //입금 구분
        String   UPSO_CD = dobj.getRetObject("SEL2").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.START_YRMN,  NULL,  NULL,  A.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(A.END_YRMN  ,  NULL,  NULL,  A.END_YRMN  ||  '01')  END_YRMN  ,  C.TOT_USE_AMT  ,  C.TOT_ADDT_AMT  ,  C.TOT_DEMD_AMT  ,  B.BRAN_CD  REPT_BRAN  ,  B.RECV_DAY  ,  B.REPT_AMT  ,  B.COMIS  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  D.BANK_NM  ,  B.REMAK  ,  B.DISTR_GBN  ,  B.DISTR_SEQ  ,  B.ADJ_AMT  ,  B.ADJ_GBN  ,  :REPT_DAY  AS  REPT_DAY  ,  :REPT_NUM  AS  REPT_NUM  ,  :REPT_GBN  AS  REPT_GBN  ,  :DEMD_YRMN  AS  DEMD_YRMN  FROM  (   \n";
        query +=" SELECT  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN  )  A  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  NULL  DISTR_GBN  ,  NULL  DISTR_SEQ  ,  REPT_AMT  ,  RECV_DAY  ,  COMIS  ,  BANK_CD  ,  ACCN_NUM  ,  REMAK  ,  UPSO_CD  ,  ADJ_AMT  ,  ADJ_GBN  FROM  GIBU.TBRA_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  B  ,  (   \n";
        query +=" SELECT  TOT_USE_AMT  ,  TOT_ADDT_AMT  ,  TOT_DEMD_AMT  ,  UPSO_CD  FROM  GIBU.TBRA_DEMD_OCR  WHERE  DEMD_YRMN  ||  UPSO_CD  =  (   \n";
        query +=" SELECT  MAX(DEMD_YRMN)  ||  :UPSO_CD  FROM  GIBU.TBRA_DEMD_REPT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  :REPT_GBN  )  )  C  ,  ACCT.TCAC_BANK  D  WHERE  D.BANK_CD(+)  =  B.BANK_CD   \n";
        query +=" AND  A.REPT_DAY(+)  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM(+)  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN(+)  =  B.REPT_GBN   \n";
        query +=" AND  C.UPSO_CD  (+)  =  B.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 청구 정보 조회
    // 입금년월에 해당하는 청구정보를 조회한다
    public DOBJ CALLrept_detail_select2_SEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL14");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL14");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_select2_SEL14(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL14");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_select2_SEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   NOTE_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //NOTE_YRMN
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XC.UPSO_CD  ,  XC.DEMD_YRMN  ||  '01'  DEMD_YRMN  ,  DECODE(XC.START_YRMN,  NULL,  NULL,  XC.START_YRMN  ||  '01')  START_YRMN  ,  DECODE(XC.END_YRMN,  NULL,  NULL,  XC.END_YRMN  ||  '01')  END_YRMN  ,  XC.TOT_DEMD_AMT  ,  XC.TOT_USE_AMT  ,  XC.TOT_ADDT_AMT  ,  XC.TOT_EADDT_AMT  ,  XC.DEMD_GBN  ,  XD.CODE_NM  DEMD_GBN_NM  ,  XA.NOTE_YRMN  FROM  GIBU.TBRA_NOTE  XA  ,  GIBU.TBRA_DEMD_REPT  XB  ,  GIBU.TBRA_DEMD_OCR  XC  ,  FIDU.TENV_CODE  XD  WHERE  XA.NOTE_YRMN  =  :NOTE_YRMN   \n";
        query +=" AND  XA.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  XB.REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XA.REPT_NUM   \n";
        query +=" AND  XB.REPT_GBN  =  XA.REPT_GBN   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.DEMD_YRMN  =  XB.DEMD_YRMN   \n";
        query +=" AND  XD.HIGH_CD  =  '00141'   \n";
        query +=" AND  XD.CODE_CD  =  XC.DEMD_GBN  ORDER  BY  XC.DEMD_YRMN  DESC ";
        sobj.setSql(query);
        sobj.setString("NOTE_YRMN", NOTE_YRMN);               //NOTE_YRMN
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$rept_detail_select2
    //##**$$end
}
