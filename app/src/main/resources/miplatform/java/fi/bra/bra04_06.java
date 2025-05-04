
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_06
{
    public bra04_06()
    {
    }
    //##**$$search
    /*
    */
    public DOBJ CTLsearch(DOBJ dobj)
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
            dobj  = CALLsearch_SEL1(Conn, dobj);           //  원장조회
            dobj  = CALLsearch_SEL2(Conn, dobj);           //  업소 정보 조회
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
    public DOBJ CTLsearch( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_SEL1(Conn, dobj);           //  원장조회
        dobj  = CALLsearch_SEL2(Conn, dobj);           //  업소 정보 조회
        return dobj;
    }
    // 원장조회
    // 조회년도를 기준으로 3개년도 입금 정보를 조회한다.  1. TENV_CODE 에서 입금구분의 명을 가져온다. 이때 입금구분의 HIGH_CD 값은 00147 이다. SQL 에서 HIGH_CD  값은 CODE_CD라는 컬럼명으로 정의해서 값을 설정한다
    public DOBJ CALLsearch_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String B_YEAR ="99";        //작년
        String   B_YEAR_1 = dobj.getRetObject("S").getRecord().get("YEAR");   //작년
        String   YEAR = dobj.getRetObject("S").getRecord().get("YEAR");   //검색년도
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BB.B_UPSO_CD,  BB.B_REPT_YRMN,  BB.B_MM,  BB.B_REPT_GBN,  BB.B_CODE_NM,  BB.B_USE_AMT,  BB.B_REPT_DAY,  BB.B_RECV_DAY  ,CC.UPSO_CD,  CC.REPT_YRMN,  CC.MM,  CC.REPT_GBN,  CC.CODE_NM,  CC.USE_AMT,  CC.REPT_DAY,  CC.RECV_DAY  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  B_UPSO_CD,  B.YYYYMM,  SUBSTR(B.YYYYMM,5,2)  B_MM,  A.REPT_YRMN  B_REPT_YRMN,  A.REPT_GBN  B_REPT_GBN,  C.CODE_NM  B_CODE_NM,  A.USE_AMT  B_USE_AMT,  A.REPT_DAY  B_REPT_DAY,  A.RECV_DAY  B_RECV_DAY  FROM  GIBU.TBRA_UPSO_REPT  A  ,  (   \n";
        query +=" SELECT  TO_NUMBER(:B_YEAR_1)  -  1  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B  ,  FIDU.TENV_CODE  C  WHERE  A.REPT_YRMN  (+)  =  B.YYYYMM   \n";
        query +=" AND  A.UPSO_CD(+)  =  :UPSO_CD   \n";
        query +=" AND  C.HIGH_CD(+)  =  '00141'   \n";
        query +=" AND  A.REPT_GBN  =  C.CODE_CD  (+)  )  BB  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  UPSO_CD,  B.YYYYMM,  SUBSTR(B.YYYYMM,5,2)  MM,  A.REPT_YRMN,  A.REPT_GBN,  C.CODE_NM  ,  A.USE_AMT  ,  A.REPT_DAY  ,  A.RECV_DAY  FROM  GIBU.TBRA_UPSO_REPT  A,  (   \n";
        query +=" SELECT  :YEAR  ||  MM  YYYYMM  FROM  GIBU.COPY_MM  )  B,  FIDU.TENV_CODE  C  WHERE  A.REPT_YRMN  (+)  =  B.YYYYMM   \n";
        query +=" AND  A.UPSO_CD(+)  =  :UPSO_CD   \n";
        query +=" AND  C.HIGH_CD(+)  =  '00141'   \n";
        query +=" AND  A.REPT_GBN  =  C.CODE_CD  (+)  )  CC  WHERE  SUBSTR(BB.YYYYMM,5,2)  =  SUBSTR(CC.YYYYMM,5,2)  ORDER  BY  BB.YYYYMM ";
        sobj.setSql(query);
        sobj.setString("B_YEAR_1", B_YEAR_1);               //작년
        sobj.setString("YEAR", YEAR);               //검색년도
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소 정보 조회
    public DOBJ CALLsearch_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.MONPRNCFEE  ,  B.BSTYP_CD  ,  B.UPSO_GRAD  ,  B.GRAD  ,  C.GRADNM  ,  A.UPSO_ADDR1  ,  A.UPSO_ADDR2  ,  A.NEW_DAY  ,  A.OPBI_DAY  ,  A.BIOWN_NUM  ,  A.MNGEMSTR_NM  ,  A.STAFF_CD  ,  F.HAN_NM  STAFF_NM  ,  A.UPSO_CD,  A.UPSO_NM  ,  E.LAST_YRMN  ,  G.DEPT_NM  FROM  GIBU.TBRA_UPSO  A  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  ,  UPSO_GRAD  ,  MONPRNCFEE  ,  GRAD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  BSTYP_CD  ,  UPSO_GRAD  ,  MONPRNCFEE  ,  TRIM(BSTYP_CD)  ||  UPSO_GRAD  GRAD  FROM  GIBU.TBRA_UPSORTAL_INFO  WHERE  UPSO_CD  =  :UPSO_CD  ORDER  BY  CHG_DAY,  CHG_NUM  DESC  )  WHERE  ROWNUM  =  1  )  B  ,  GIBU.TBRA_BSTYPGRAD  C  ,  (   \n";
        query +=" SELECT  MAX(REPT_YRMN)  ||  '01'  LAST_YRMN  FROM  GIBU.TBRA_UPSO_REPT  WHERE  UPSO_CD  =  :UPSO_CD  )  E  ,  INSA.TINS_MST01  F  ,  INSA.TCPM_DEPT  G  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  C.GRAD_GBN  =  B.UPSO_GRAD   \n";
        query +=" AND  F.STAFF_NUM(+)  =  A.STAFF_CD   \n";
        query +=" AND  A.BRAN_CD  =  G.GIBU ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$search
    //##**$$end
}
