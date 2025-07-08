
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r08
{
    public tac10_r08()
    {
    }
    //##**$$tac10_r08_select
    /* * 프로그램명 : tac10_r08
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac10_r08_select(DOBJ dobj)
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
            dobj  = CALLtac10_r08_select_SEL1(Conn, dobj);           //  출판사 저작권 사용료현황
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
    public DOBJ CTLtac10_r08_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r08_select_SEL1(Conn, dobj);           //  출판사 저작권 사용료현황
        return dobj;
    }
    // 출판사 저작권 사용료현황
    public DOBJ CALLtac10_r08_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r08_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r08_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.MB_CD, A.SUPP_YRMN, C.HANMB_NM, C.INS_NUM, SUM(ORGDISTR_AMT) TOT_ORGDISTR_AMT, SUM(ORGDISTR_AMT) - SUM(ATAX_AMT) TOT_GAMT, SUM(ATAX_AMT) TOT_ATAX_AMT, SUM(MNGCOMIS_AMT) TOT_MNGCOMIS_AMT, D.TOT_REALSUPP_AMT TOT_REALSUPP_AMT  ";
        query +=" FROM FIDU.TTAC_MDMCLASSSUPPAMT A , FIDU.TENV_MDMCD B, FIDU.TMEM_MB C, FIDU.TTAC_COPYRTAL D  ";
        query +=" WHERE A.MDM_CD = B.MDM_CD  ";
        query +=" AND A.MB_CD = C.MB_CD  ";
        query +=" AND C.MB_GBN1 ='F'  ";
        query +=" AND NVL(B.ATAX_YN, ' ') ='1'  ";
        query +=" AND A.SUPP_YRMN = D.SUPP_YRMN  ";
        query +=" AND A.MB_CD = D.MB_CD  ";
        query +=" AND A.TRNSF_GBN = D.TRNSF_GBN  ";
        query +=" AND A.SUPP_YRMN BETWEEN SUBSTR(:FROMDATE,1,6)  ";
        query +=" AND SUBSTR(:TODATE,1,6)  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND A.MB_CD = :MB_CD  ";
        }
        query +=" GROUP BY A.SUPP_YRMN, A.MB_CD, C.HANMB_NM, C.INS_NUM, D.TOT_REALSUPP_AMT  ";
        query +=" UNION ALL  ";
        query +=" SELECT A.MB_CD, A.SUPP_YRMN, B.HANMB_NM, B.INS_NUM, SUM(ORGDISTR_AMT) TOT_ORGDISTR_AMT, SUM(ORGDISTR_AMT) - SUM(ATAX_AMT) TOT_GAMT, SUM(ATAX_AMT) TOT_ATAX_AMT, SUM(MNGCOMIS_AMT) TOT_MNGCOMIS_AMT, D.TOT_REALSUPP_AMT TOT_REALSUPP_AMT  ";
        query +=" FROM FIDU.TTAC_MDMCLASSSUPPAMT A, FIDU.TMEM_MB B , FIDU.TTAC_COPYRTAL D  ";
        query +=" WHERE A.MB_CD = B.MB_CD  ";
        query +=" AND B.MB_GBN1 IN ('P','B','C' )  ";
        query +=" AND A.SUPP_YRMN = D.SUPP_YRMN  ";
        query +=" AND A.MB_CD = D.MB_CD  ";
        query +=" AND A.TRNSF_GBN = D.TRNSF_GBN  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND A.MB_CD = :MB_CD  ";
        }
        query +=" AND A.SUPP_YRMN BETWEEN SUBSTR(:FROMDATE,1,6)  ";
        query +=" AND SUBSTR(:TODATE,1,6)  ";
        query +=" GROUP BY A.MB_CD, B.HANMB_NM, A.SUPP_YRMN, D.TOT_REALSUPP_AMT, B.INS_NUM  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        return sobj;
    }
    //##**$$tac10_r08_select
    //##**$$end
}
