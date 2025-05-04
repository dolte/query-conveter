
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_p01
{
    public bra10_p01()
    {
    }
    //##**$$mon_col_graph
    /* * 프로그램명 : bra10_p01
    * 작성자 : 서정재
    * 작성일 : 2009/10/09
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLmon_col_graph(DOBJ dobj)
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
            dobj  = CALLmon_col_graph_SEL2(Conn, dobj);           //  지부이름구하기
            dobj  = CALLmon_col_graph_SEL1(Conn, dobj);           //  징수통계
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
    public DOBJ CTLmon_col_graph( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmon_col_graph_SEL2(Conn, dobj);           //  지부이름구하기
        dobj  = CALLmon_col_graph_SEL1(Conn, dobj);           //  징수통계
        return dobj;
    }
    // 지부이름구하기
    public DOBJ CALLmon_col_graph_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmon_col_graph_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmon_col_graph_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEPT_NM  BRAN_NM  FROM  INSA.TCPM_DEPT  WHERE  GIBU  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 징수통계
    public DOBJ CALLmon_col_graph_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmon_col_graph_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmon_col_graph_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_NM = dobj.getRetObject("SEL2").getRecord().get("BRAN_NM");   //지부 이름
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :BRAN_NM  ,  DECODE(YRMN,  NULL,  '',  SUBSTR(YRMN,1,4)||'/'||SUBSTR(YRMN,5,2))  YRMN  ,  GRADNM  BSTYP_NM  ,  COL_AMT  ,  GRAD_GBN  FROM  (   \n";
        query +=" SELECT  :YRMN  YRMN  ,  NVL(TA.COL_AMT,0)  COL_AMT  ,  TB.GRADNM  ,  TB.GRAD_GBN  FROM  (   \n";
        query +=" SELECT  SUM(NVL((AA.REPT_AMT-AA.COMIS),0))  COL_AMT  ,  CC.BSTYP_CD  FROM(   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_AMT  ,  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  DISTR_AMT  ,  0  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  )  AA  ,  GIBU.TBRA_UPSO  BB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  :YRMN||'31'   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.UPSO_CD  =  AA.UPSO_CD  GROUP  BY  CC.BSTYP_CD  )TA  ,  GIBU.TBRA_BSTYPGRAD  TB  WHERE  TB.BSTYP_CD  =  'z'   \n";
        query +=" AND  TB.GRAD_GBN  =  TA.BSTYP_CD(+)  UNION  ALL   \n";
        query +=" SELECT  TO_CHAR(ADD_MONTHS(TO_DATE(:YRMN,'YYYYMM'),  -1),  'YYYYMM')  YRMN  ,  NVL(TA.COL_AMT,0)  COL_AMT  ,  TB.GRADNM  ,  TB.GRAD_GBN  FROM  (   \n";
        query +=" SELECT  SUM(NVL((AA.REPT_AMT-AA.COMIS),0))  COL_AMT  ,  CC.BSTYP_CD  FROM(   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_AMT  ,  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  TO_CHAR(ADD_MONTHS(TO_DATE(:YRMN,'YYYYMM'),  -1),  'YYYYMM')||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  DISTR_AMT  ,  0  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  TO_CHAR(ADD_MONTHS(TO_DATE(:YRMN,'YYYYMM'),  -1),  'YYYYMM')||'%'   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  )  AA  ,  GIBU.TBRA_UPSO  BB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  TO_CHAR(ADD_MONTHS(TO_DATE(:YRMN,'YYYYMM'),  -1),  'YYYYMM')||'31'   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.UPSO_CD  =  AA.UPSO_CD  GROUP  BY  CC.BSTYP_CD  )TA  ,  GIBU.TBRA_BSTYPGRAD  TB  WHERE  TB.BSTYP_CD  =  'z'   \n";
        query +=" AND  TB.GRAD_GBN  =  TA.BSTYP_CD(+)  )  WHERE  GRAD_GBN  NOT  IN  ('h','i','j')  ORDER  BY  GRAD_GBN,  YRMN  ASC,  COL_AMT  DESC ";
        sobj.setSql(query);
        sobj.setString("BRAN_NM", BRAN_NM);               //지부 이름
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$mon_col_graph
    //##**$$end
}
