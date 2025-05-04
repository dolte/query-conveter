
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s23
{
    public bra04_s23()
    {
    }
    //##**$$rept_detail
    /*
    */
    public DOBJ CTLrept_detail(DOBJ dobj)
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
            dobj  = CALLrept_detail_SEL2(Conn, dobj);           //  원장조회
            dobj  = CALLrept_detail_SEL3(Conn, dobj);           //  원장조회
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
    public DOBJ CTLrept_detail( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_detail_SEL2(Conn, dobj);           //  원장조회
        dobj  = CALLrept_detail_SEL3(Conn, dobj);           //  원장조회
        return dobj;
    }
    // 원장조회
    // 조회년도를 기준으로 3개년도 입금 정보를 조회한다.  1. TENV_CODE 에서 입금구분의 명을 가져온다. 이때 입금구분의 HIGH_CD 값은 00147 이다. SQL 에서 HIGH_CD  값은 CODE_CD라는 컬럼명으로 정의해서 값을 설정한다
    public DOBJ CALLrept_detail_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String B_YEAR ="99";        //작년
        String   B_YEAR_1 = dobj.getRetObject("S").getRecord().get("YEAR");   //작년
        String   YEAR = dobj.getRetObject("S").getRecord().get("YEAR");   //검색년도
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  AA.UPSO_CD  B_UPSO_CD  ,  AA.YRMN  B_REPT_YRMN  ,  AA.MM  B_MM  ,  AA.REPT_GBN  B_REPT_GBN  ,  AA.CODE_NM  B_CODE_NM  ,  AA.USE_AMT  B_USE_AMT  ,  AA.REPT_DAY  B_REPT_DAY  ,  AA.REPT_NUM  B_REPT_NUM  ,  AA.RECV_DAY  B_RECV_DAY  ,  AA.ACCU_GBN  B_ACCU_GBN  ,  AA.MAPPING_DAY  B_MAPPING_DAY  ,  BB.UPSO_CD  ,  BB.YRMN  REPT_YRMN  ,  BB.MM  ,  BB.REPT_GBN  ,  BB.CODE_NM  ,  BB.USE_AMT  ,  BB.REPT_DAY  ,  BB.REPT_NUM  ,  BB.RECV_DAY  ,  BB.ACCU_GBN  ,  BB.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  ZA.CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  DECODE(A.ACCU_GBN,  NULL,  C.CODE_NM,  DECODE(E.ACCU_CNT,  0,  '고소해결',  '고소분납'))  CODE_NM  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  D.MAPPING_DAY  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  GIBU.TBRA_REPT  D  ,  (   \n";
        query +=" SELECT  YRMN  ,  DECODE(XA.COMPN_DAY,  NULL,  1,  0)  ACCU_CNT  FROM  (   \n";
        query +=" SELECT  DECODE(COMPN_DAY,  NULL,  START_YRMN,  SOL_START_YRMN)  START_YRMN  ,  DECODE(COMPN_DAY,  NULL,  END_YRMN,  SOL_END_YRMN)  END_YRMN  ,  COMPN_DAY  FROM  (   \n";
        query +=" SELECT  START_YRMN  ,  END_YRMN  ,  SOL_START_YRMN  ,  SOL_END_YRMN  ,  COMPN_DAY  FROM  GIBU.TBRA_ACCU  XA  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  ACCU_DAY  DESC,  ACCU_NUM  DESC  )  WHERE  ROWNUM  =  1  )  XA  ,  GIBU.COPY_YRMN  XB  WHERE  XB.YRMN  BETWEEN  XA.START_YRMN   \n";
        query +=" AND  XA.END_YRMN   \n";
        query +=" AND  XB.YYYY  =  TO_NUMBER(:B_YEAR_1)  -  1  )  E  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  TO_NUMBER(:B_YEAR_1)  -  1  ||  '01'   \n";
        query +=" AND  TO_NUMBER(:B_YEAR_1)  -  1  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD   \n";
        query +=" AND  D.REPT_DAY(+)  =  A.REPT_DAY   \n";
        query +=" AND  D.REPT_NUM(+)  =  A.REPT_NUM   \n";
        query +=" AND  D.REPT_GBN(+)  =  A.REPT_GBN   \n";
        query +=" AND  E.YRMN(+)  =  A.NOTE_YRMN  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  TO_NUMBER(:B_YEAR_1)  -  1  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  BB  ,  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  ZA.CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  DECODE(A.ACCU_GBN,  NULL,  C.CODE_NM,  DECODE(E.ACCU_CNT,  0,  '고소해결',  '고소분납'))  CODE_NM  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  D.MAPPING_DAY  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  GIBU.TBRA_REPT  D  ,  (   \n";
        query +=" SELECT  YRMN  ,  DECODE(XA.COMPN_DAY,  NULL,  1,  0)  ACCU_CNT  FROM  (   \n";
        query +=" SELECT  DECODE(COMPN_DAY,  NULL,  START_YRMN,  SOL_START_YRMN)  START_YRMN  ,  DECODE(COMPN_DAY,  NULL,  END_YRMN,  SOL_END_YRMN)  END_YRMN  ,  COMPN_DAY  FROM  (   \n";
        query +=" SELECT  START_YRMN  ,  END_YRMN  ,  SOL_START_YRMN  ,  SOL_END_YRMN  ,  COMPN_DAY  FROM  GIBU.TBRA_ACCU  XA  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  ACCU_DAY  DESC,  ACCU_NUM  DESC  )  WHERE  ROWNUM  =  1  )  XA  ,  GIBU.COPY_YRMN  XB  WHERE  XB.YRMN  BETWEEN  XA.START_YRMN   \n";
        query +=" AND  XA.END_YRMN   \n";
        query +=" AND  XB.YYYY  =  :YEAR  )  E  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  :YEAR  ||  '01'   \n";
        query +=" AND  :YEAR  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD   \n";
        query +=" AND  D.REPT_DAY(+)  =  A.REPT_DAY   \n";
        query +=" AND  D.REPT_NUM(+)  =  A.REPT_NUM   \n";
        query +=" AND  D.REPT_GBN(+)  =  A.REPT_GBN   \n";
        query +=" AND  E.YRMN(+)  =  A.NOTE_YRMN  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  :YEAR  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  CC  WHERE  AA.MM  =  BB.MM  ORDER  BY  AA.MM ";
        sobj.setSql(query);
        sobj.setString("B_YEAR_1", B_YEAR_1);               //작년
        sobj.setString("YEAR", YEAR);               //검색년도
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 원장조회
    // 조회년도를 기준으로 3개년도 입금 정보를 조회한다.  1. TENV_CODE 에서 입금구분의 명을 가져온다. 이때 입금구분의 HIGH_CD 값은 00147 이다. SQL 에서 HIGH_CD  값은 CODE_CD라는 컬럼명으로 정의해서 값을 설정한다
    public DOBJ CALLrept_detail_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_detail_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_detail_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String B_YEAR ="99";        //작년
        String   B_YEAR_1 = dobj.getRetObject("S").getRecord().get("ORG_YEAR");   //작년
        String   YEAR = dobj.getRetObject("S").getRecord().get("ORG_YEAR");   //검색년도
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("ORG_UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  AA.UPSO_CD  B_UPSO_CD  ,  AA.YRMN  B_REPT_YRMN  ,  AA.MM  B_MM  ,  AA.REPT_GBN  B_REPT_GBN  ,  AA.CODE_NM  B_CODE_NM  ,  AA.USE_AMT  B_USE_AMT  ,  AA.REPT_DAY  B_REPT_DAY  ,  AA.REPT_NUM  B_REPT_NUM  ,  AA.RECV_DAY  B_RECV_DAY  ,  AA.ACCU_GBN  B_ACCU_GBN  ,  AA.MAPPING_DAY  B_MAPPING_DAY  ,  BB.UPSO_CD  ,  BB.YRMN  REPT_YRMN  ,  BB.MM  ,  BB.REPT_GBN  ,  BB.CODE_NM  ,  BB.USE_AMT  ,  BB.REPT_DAY  ,  BB.REPT_NUM  ,  BB.RECV_DAY  ,  BB.ACCU_GBN  ,  BB.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  ZA.CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  DECODE(A.ACCU_GBN,  NULL,  C.CODE_NM,  DECODE(E.ACCU_CNT,  0,  '고소해결',  '고소분납'))  CODE_NM  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  D.MAPPING_DAY  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  GIBU.TBRA_REPT  D  ,  (   \n";
        query +=" SELECT  YRMN  ,  DECODE(XA.COMPN_DAY,  NULL,  1,  0)  ACCU_CNT  FROM  (   \n";
        query +=" SELECT  DECODE(COMPN_DAY,  NULL,  START_YRMN,  SOL_START_YRMN)  START_YRMN  ,  DECODE(COMPN_DAY,  NULL,  END_YRMN,  SOL_END_YRMN)  END_YRMN  ,  COMPN_DAY  FROM  (   \n";
        query +=" SELECT  START_YRMN  ,  END_YRMN  ,  SOL_START_YRMN  ,  SOL_END_YRMN  ,  COMPN_DAY  FROM  GIBU.TBRA_ACCU  XA  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  ACCU_DAY  DESC,  ACCU_NUM  DESC  )  WHERE  ROWNUM  =  1  )  XA  ,  GIBU.COPY_YRMN  XB  WHERE  XB.YRMN  BETWEEN  XA.START_YRMN   \n";
        query +=" AND  XA.END_YRMN   \n";
        query +=" AND  XB.YYYY  =  TO_NUMBER(:B_YEAR_1)  -  1  )  E  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  TO_NUMBER(:B_YEAR_1)  -  1  ||  '01'   \n";
        query +=" AND  TO_NUMBER(:B_YEAR_1)  -  1  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD   \n";
        query +=" AND  D.REPT_DAY(+)  =  A.REPT_DAY   \n";
        query +=" AND  D.REPT_NUM(+)  =  A.REPT_NUM   \n";
        query +=" AND  D.REPT_GBN(+)  =  A.REPT_GBN   \n";
        query +=" AND  E.YRMN(+)  =  A.NOTE_YRMN  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  TO_NUMBER(:B_YEAR_1)  -  1  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  BB  ,  (   \n";
        query +=" SELECT  ZB.YRMN  ,  ZB.MM  ,  ZA.UPSO_CD  ,  ZA.NOTE_YRMN  ,  ZA.REPT_GBN  ,  ZA.CODE_NM  ,  ZA.USE_AMT  ,  ZA.REPT_DAY  ,  ZA.REPT_NUM  ,  ZA.RECV_DAY  ,  ZA.ACCU_GBN  ,  ZA.MAPPING_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  A.NOTE_YRMN  ,  A.REPT_GBN  ,  DECODE(A.ACCU_GBN,  NULL,  C.CODE_NM,  DECODE(E.ACCU_CNT,  0,  '고소해결',  '고소분납'))  CODE_NM  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.REPT_NUM  ,  A.RECV_DAY  ,  A.ACCU_GBN  ,  D.MAPPING_DAY  FROM  GIBU.TBRA_NOTE  A  ,  FIDU.TENV_CODE  C  ,  GIBU.TBRA_REPT  D  ,  (   \n";
        query +=" SELECT  YRMN  ,  DECODE(XA.COMPN_DAY,  NULL,  1,  0)  ACCU_CNT  FROM  (   \n";
        query +=" SELECT  DECODE(COMPN_DAY,  NULL,  START_YRMN,  SOL_START_YRMN)  START_YRMN  ,  DECODE(COMPN_DAY,  NULL,  END_YRMN,  SOL_END_YRMN)  END_YRMN  ,  COMPN_DAY  FROM  (   \n";
        query +=" SELECT  START_YRMN  ,  END_YRMN  ,  SOL_START_YRMN  ,  SOL_END_YRMN  ,  COMPN_DAY  FROM  GIBU.TBRA_ACCU  XA  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  ACCU_DAY  DESC,  ACCU_NUM  DESC  )  WHERE  ROWNUM  =  1  )  XA  ,  GIBU.COPY_YRMN  XB  WHERE  XB.YRMN  BETWEEN  XA.START_YRMN   \n";
        query +=" AND  XA.END_YRMN   \n";
        query +=" AND  XB.YYYY  =  :YEAR  )  E  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.NOTE_YRMN  BETWEEN  :YEAR  ||  '01'   \n";
        query +=" AND  :YEAR  ||  '12'   \n";
        query +=" AND  C.HIGH_CD  =  '00141'   \n";
        query +=" AND  DECODE(A.ACCU_GBN,  NULL,  A.REPT_GBN,  A.ACCU_GBN)  =  C.CODE_CD   \n";
        query +=" AND  D.REPT_DAY(+)  =  A.REPT_DAY   \n";
        query +=" AND  D.REPT_NUM(+)  =  A.REPT_NUM   \n";
        query +=" AND  D.REPT_GBN(+)  =  A.REPT_GBN   \n";
        query +=" AND  E.YRMN(+)  =  A.NOTE_YRMN  )  ZA  ,  (   \n";
        query +=" SELECT  YRMN  ,  MM  FROM  GIBU.COPY_YRMN  WHERE  YYYY  =  :YEAR  )  ZB  WHERE  ZA.NOTE_YRMN(+)  =  ZB.YRMN  )  CC  WHERE  AA.MM  =  BB.MM  ORDER  BY  AA.MM ";
        sobj.setSql(query);
        sobj.setString("B_YEAR_1", B_YEAR_1);               //작년
        sobj.setString("YEAR", YEAR);               //검색년도
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$rept_detail
    //##**$$end
}
