
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_t19_2
{
    public bra04_t19_2()
    {
    }
    //##**$$misu_sum_select
    /*
    */
    public DOBJ CTLmisu_sum_select(DOBJ dobj)
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
            dobj  = CALLmisu_sum_select_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLmisu_sum_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmisu_sum_select_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLmisu_sum_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmisu_sum_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmisu_sum_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NONPY_DAY  ,  SUM(REPT_AMT)  AS  REPT_AMT  ,  SUM(COMIS_AMT)  AS  COMIS_AMT  ,  SUM(RETURN_AMT)  AS  RETURN_AMT  ,  SUM(ADJ_AMT)  AS  ADJ_AMT  ,  SUM(CNT_SATN)  AS  CNT_SATN  ,  SUM(BEFORE_NONPY_AMT)  AS  BEFORE_NONPY_AMT  ,  SUM(NONPY_AMT)  AS  NONPY_AMT  ,  CASE  WHEN  SUM(BEFORE_NONPY_AMT)  =  0   \n";
        query +=" AND  SUM(NONPY_AMT)  =  0  THEN  '-'  WHEN  SUM(NONPY_AMT)  =  0  THEN  '마감실행'  ELSE  '마감해제'  END  as  BTN_EXECUTE  FROM  (   \n";
        query +=" SELECT  AA.MAPPING_DAY  AS  NONPY_DAY  ,  SUM(NVL(AA.REPT_AMT,0))  AS  REPT_AMT  ,  SUM(NVL(AA.COMIS,0))  AS  COMIS_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  ADJ_AMT  ,  0  AS  CNT_SATN  ,  0  AS  BEFORE_NONPY_AMT  ,  0  AS  NONPY_AMT  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_AMT  ,  COMIS  ,  UPSO_CD  ,  MAPPING_DAY  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  DISTR_AMT  ,  0  ,  UPSO_CD  ,  MAPPING_DAY  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  )  AA  ,  GIBU.TBRA_UPSO  BB  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  AA.MAPPING_DAY   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(AA.UPSO_CD),1,1)  <>  't'  GROUP  BY  AA.MAPPING_DAY  UNION  ALL   \n";
        query +=" SELECT  A.RETURN_DAY  AS  NONPY_DAY  ,  0  AS  REPT_AMT  ,  0  AS  COMIS_AMT  ,  SUM(RETURN_AMT)  RETURN_AMT  ,  0  AS  ADJ_AMT  ,  0  AS  CNT_SATN  ,  0  AS  BEFORE_NONPY_AMT  ,  0  AS  NONPY_AMT  FROM  GIBU.TBRA_REPT_RETURN  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.RETURN_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,1)  <>  't'  GROUP  BY  A.RETURN_DAY  UNION  ALL   \n";
        query +=" SELECT  NONPY_DAY  ,  0  AS  REPT_AMT  ,  0  AS  COMIS_AMT  ,  0  AS  RETURN_AMT  ,  SUM(ADJ_AMT)  AS  ADJ_AMT  ,  0  AS  CNT_SATN  ,  0  AS  BEFORE_NONPY_AMT  ,  0  AS  NONPY_AMT  FROM  GIBU.TBRA_MISU_ADJ  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.NONPY_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,1)  <>  't'  GROUP  BY  NONPY_DAY  UNION  ALL   \n";
        query +=" SELECT  NONPY_DAY  ,  0  AS  REPT_AMT  ,  0  AS  COMIS_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  ADJ_AMT  ,  SUM(CNT_SATN1  +  CNT_SATN2  +  CNT_SATN3)  AS  CNT_SATN  ,  0  AS  BEFORE_NONPY_AMT  ,  0  AS  NONPY_AMT  FROM  (   \n";
        query +=" SELECT  A.NONPY_DAY  ,  A.UPSO_CD  ,  A.SEQ  ,  A.ADJ_AMT  ,  A.ADJ_GBN  ,  A.SATN1  ,  A.SATN2  ,  A.SATN3  ,  C.SATN1  ,  C.SATN2  ,  C.SATN3  ,  CASE  WHEN  C.SATN1  IS  NOT  NULL   \n";
        query +=" AND  A.SATN1  IS  NULL  THEN  1  ELSE  0  END  CNT_SATN1  ,  CASE  WHEN  C.SATN2  IS  NOT  NULL   \n";
        query +=" AND  A.SATN2  IS  NULL  THEN  1  ELSE  0  END  CNT_SATN2  ,  CASE  WHEN  C.SATN3  IS  NOT  NULL   \n";
        query +=" AND  A.SATN3  IS  NULL  THEN  1  ELSE  0  END  CNT_SATN3  FROM  GIBU.TBRA_MISU_ADJ  A  ,  GIBU.TBRA_UPSO  B  ,  GIBU.TBRA_MISU_ADJ_SATN  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.BRAN_CD  =  C.BRAN_CD   \n";
        query +=" AND  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.ADJ_GBN  =  C.ADJ_GBN   \n";
        query +=" AND  A.ADJ_AMT  <>  0   \n";
        query +=" AND  A.NONPY_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,1)  <>  't'   \n";
        query +=" AND  ADJ_AMT  <>  0  )  GROUP  BY  NONPY_DAY  UNION  ALL   \n";
        query +=" SELECT  NONPY_DAY  ,  0  AS  REPT_AMT  ,  0  AS  COMIS_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  ADJ_AMT  ,  0  AS  CNT_SATN  ,  SUM(BEFORE_NONPY_AMT)  AS  BEFORE_NONPY_AMT  ,  SUM(NONPY_AMT)  AS  NONPY_AMT  FROM  GIBU.TBRA_MISU  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NONPY_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(UPSO_CD),1,1)  <>  't'  GROUP  BY  NONPY_DAY  )  GROUP  BY  NONPY_DAY  ORDER  BY  NONPY_DAY ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$misu_sum_select
    //##**$$end
}
