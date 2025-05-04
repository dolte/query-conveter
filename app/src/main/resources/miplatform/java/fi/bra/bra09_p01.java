
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra09_p01
{
    public bra09_p01()
    {
    }
    //##**$$tbran_col_list
    /* * 프로그램명 : bra09_p01
    * 작성자 : 서정재
    * 작성일 : 2009/11/17
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtbran_col_list(DOBJ dobj)
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
            dobj  = CALLtbran_col_list_SEL1(Conn, dobj);           //  전국지부징수내역서
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
    public DOBJ CTLtbran_col_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtbran_col_list_SEL1(Conn, dobj);           //  전국지부징수내역서
        return dobj;
    }
    // 전국지부징수내역서
    public DOBJ CALLtbran_col_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtbran_col_list_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtbran_col_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BB.DEPT_NM  BRAN_NM  ,  AA.BRAN_CD  ,  MAX(TO_CHAR(ADD_MONTHS(TO_DATE(:YRMN,'YYYYMM'),  -1),  'YYYYMM'))  B_YRMN  ,  MAX(:YRMN)  YRMN  ,  CC.GRADNM  BSTYP_NM  ,  SUM(B_MNG_CNT)  B_MNG_CNT  ,  SUM(MNG_CNT)  MNG_CNT  ,  SUM(B_USE_AMT)  B_USE_AMT  ,  SUM(USE_AMT)  USE_AMT  ,  SUM(B_COL_CNT)  B_COL_CNT  ,  SUM(COL_CNT)  COL_CNT  ,  SUM(B_COL_AMT)  B_COL_AMT  ,  SUM(COL_AMT)  COL_AMT  FROM  (   \n";
        query +=" SELECT  TA.BRAN_CD  ,  TB.BSTYP_CD  BSTYP_CD  ,  COUNT(*)  MNG_CNT  ,  SUM(NVL(TB.MONPRNCFEE,0))  USE_AMT  ,  0  COL_CNT  ,  0  COL_AMT  ,  0  B_MNG_CNT  ,  0  B_USE_AMT  ,  0  B_COL_CNT  ,  0  B_COL_AMT  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  XA.UPSO_CD,  XB.MONPRNCFEE,  XC.GRADNM,  TRIM(XB.BSTYP_CD)  BSTYP_CD,  XB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  XA  ,  GIBU.TBRA_UPSORTAL_INFO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  WHERE  XB.JOIN_SEQ  =  XA.JOIN_SEQ   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.BSTYP_CD(+)  =  XB.BSTYP_CD   \n";
        query +=" AND  XC.GRAD_GBN(+)  =  XB.UPSO_GRAD  )  TB  WHERE  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  TA.NEW_DAY  <=  :YRMN||'31'   \n";
        query +=" AND  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(TA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD  GROUP  BY  TA.BRAN_CD,  TB.BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  TA.BRAN_CD  ,  TB.BSTYP_CD  BSTYP_CD  ,  0  MNG_CNT  ,  0  USE_AMT  ,  COUNT(DISTINCT  TC.UPSO_CD)  COL_CNT  ,  SUM(NVL(TC.REPT_AMT,0)-NVL(TC.COMIS,0))  COL_AMT  ,  0  B_MNG_CNT  ,  0  B_USE_AMT  ,  0  B_COL_CNT  ,  0  B_COL_AMT  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  XA.UPSO_CD,  XB.MONPRNCFEE,  XC.GRADNM,  TRIM(XB.BSTYP_CD)  BSTYP_CD,  XB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  XA  ,  GIBU.TBRA_UPSORTAL_INFO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  WHERE  XB.JOIN_SEQ  =  XA.JOIN_SEQ   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.BSTYP_CD(+)  =  XB.BSTYP_CD   \n";
        query +=" AND  XC.GRAD_GBN(+)  =  XB.UPSO_GRAD  )  TB  ,  (   \n";
        query +=" SELECT  REPT_AMT  ,  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  0  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  REPT_GBN  <>  '08'  UNION  ALL   \n";
        query +=" SELECT  (CASE  WHEN  MIN(DISTR_SEQ)  OVER(PARTITION  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN)  =  DISTR_SEQ  THEN  A.DISTR_AMT  -  NVL(C.COMIS,  0)  ELSE  A.DISTR_AMT  END)  AS  REPT_AMT  ,  0  ,  A.UPSO_CD  ,  A.BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_REPT_VIRTUAL  B  ,  GIBU.TBRA_REPT  C  WHERE  A.MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  '08'   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  C.REPT_GBN(+)  =  '08'  )  TC  WHERE  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  TC.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  TA.NEW_DAY  <=  :YRMN||'31'   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TC.UPSO_CD  =  TA.UPSO_CD  GROUP  BY  TA.BRAN_CD,  TB.BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  TA.BRAN_CD  ,  TB.BSTYP_CD  BSTYP_CD  ,  0  MNG_CNT  ,  0  USE_AMT  ,  0  COL_CNT  ,  0  COL_AMT  ,  COUNT(*)  B_MNG_CNT  ,  SUM(NVL(TB.MONPRNCFEE,0))  B_USE_AMT  ,  0  B_COL_CNT  ,  0  B_COL_AMT  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  XA.UPSO_CD,  XB.MONPRNCFEE,  XC.GRADNM,  TRIM(XB.BSTYP_CD)  BSTYP_CD,  XB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  XA  ,  GIBU.TBRA_UPSORTAL_INFO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  WHERE  XB.JOIN_SEQ  =  XA.JOIN_SEQ   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.BSTYP_CD(+)  =  XB.BSTYP_CD   \n";
        query +=" AND  XC.GRAD_GBN(+)  =  XB.UPSO_GRAD  )  TB  WHERE  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  TA.NEW_DAY  <=  TO_CHAR(ADD_MONTHS(TO_DATE(:YRMN,'YYYYMM'),  -1),  'YYYYMM')||'31'   \n";
        query +=" AND  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(TA.CLSBS_INS_DAY,1,6),'  ')  >  TO_CHAR(ADD_MONTHS(TO_DATE(:YRMN,  'YYYYMM'),  -1),  'YYYYMM'))   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD  GROUP  BY  TA.BRAN_CD,  TB.BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  TA.BRAN_CD  ,  TB.BSTYP_CD  BSTYP_CD  ,  0  MNG_CNT  ,  0  USE_AMT  ,  0  COL_CNT  ,  0  COL_AMT  ,  0  B_MNG_CNT  ,  0  B_USE_AMT  ,  COUNT(DISTINCT  TC.UPSO_CD)  B_COL_CNT  ,  SUM(NVL(TC.REPT_AMT,0)-NVL(TC.COMIS,0))  B_COL_AMT  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  XA.UPSO_CD,  XB.MONPRNCFEE,  XC.GRADNM,  TRIM(XB.BSTYP_CD)  BSTYP_CD,  XB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  XA  ,  GIBU.TBRA_UPSORTAL_INFO  XB  ,  GIBU.TBRA_BSTYPGRAD  XC  WHERE  XB.JOIN_SEQ  =  XA.JOIN_SEQ   \n";
        query +=" AND  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.BSTYP_CD(+)  =  XB.BSTYP_CD   \n";
        query +=" AND  XC.GRAD_GBN(+)  =  XB.UPSO_GRAD  )  TB  ,  (   \n";
        query +=" SELECT  REPT_AMT  ,  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  TO_CHAR(ADD_MONTHS(TO_DATE(:YRMN,'YYYYMM'),  -1),  'YYYYMM')||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  0  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  TO_CHAR(ADD_MONTHS(TO_DATE(:YRMN,'YYYYMM'),  -1),  'YYYYMM')||'%'   \n";
        query +=" AND  REPT_GBN  <>  '08'  UNION  ALL   \n";
        query +=" SELECT  (CASE  WHEN  MIN(DISTR_SEQ)  OVER(PARTITION  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN)  =  DISTR_SEQ  THEN  A.DISTR_AMT  -  NVL(C.COMIS,  0)  ELSE  A.DISTR_AMT  END)  AS  REPT_AMT  ,  0  ,  A.UPSO_CD  ,  A.BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_REPT_VIRTUAL  B  ,  GIBU.TBRA_REPT  C  WHERE  A.MAPPING_DAY  LIKE  TO_CHAR(ADD_MONTHS(TO_DATE(:YRMN,'YYYYMM'),  -1),  'YYYYMM')||'%'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  '08'   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  C.REPT_GBN(+)  =  '08'  UNION  ALL   \n";
        query +=" SELECT  (RETURN_AMT*  -1)  REPT_AMT  ,  0  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT_RETURN  WHERE  RETURN_DAY  LIKE  :YRMN||'%'  )  TC  WHERE  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  TC.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  TA.NEW_DAY  <=  TO_CHAR(ADD_MONTHS(TO_DATE(:YRMN,'YYYYMM'),  -1),  'YYYYMM')||'31'   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TC.UPSO_CD  =  TA.UPSO_CD  GROUP  BY  TA.BRAN_CD,  TB.BSTYP_CD  )  AA  ,  INSA.TCPM_DEPT  BB  ,  GIBU.TBRA_BSTYPGRAD  CC  WHERE  AA.BRAN_CD  =  BB.GIBU   \n";
        query +=" AND  CC.BSTYP_CD(+)  =  'z'   \n";
        query +=" AND  CC.GRAD_GBN(+)  =  AA.BSTYP_CD  GROUP  BY  BB.DEPT_NM,  AA.BRAN_CD,  CC.GRADNM  ORDER  BY  AA.BRAN_CD,  CC.GRADNM ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        return sobj;
    }
    //##**$$tbran_col_list
    //##**$$end
}
