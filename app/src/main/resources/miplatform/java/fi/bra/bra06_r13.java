
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra06_r13
{
    public bra06_r13()
    {
    }
    //##**$$bran_progress
    /*
    */
    public DOBJ CTLbran_progress(DOBJ dobj)
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
            dobj  = CALLbran_progress_SEL1(Conn, dobj);           //  전국지부 업소현황 리스트
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
    public DOBJ CTLbran_progress( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbran_progress_SEL1(Conn, dobj);           //  전국지부 업소현황 리스트
        return dobj;
    }
    // 전국지부 업소현황 리스트
    public DOBJ CALLbran_progress_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbran_progress_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbran_progress_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEPT_NM  ,  B_UPSO_CNT  AS  B_UPSO_CNT  ,  N_UPSO_CNT  AS  N_UPSO_CNT  ,  C_UPSO_CNT  AS  C_UPSO_CNT  ,  N_UPSO_CNT  -  C_UPSO_CNT  AS  PM_UPSO_CNT  ,  B_UPSO_CNT  +  N_UPSO_CNT  -  C_UPSO_CNT  AS  T_UPSO_CNT  ,  I_UPSO_CNT  AS  I_UPSO_CNT  ,  B_UPSO_CNT  +  N_UPSO_CNT  -  C_UPSO_CNT  -  I_UPSO_CNT  AS  D_UPSO_CNT  FROM  (   \n";
        query +=" SELECT  TA.BRAN_CD  ,  TB.DEPT_NM  ,  SUM(B_UPSO_CNT)  B_UPSO_CNT  ,  SUM(N_UPSO_CNT)  N_UPSO_CNT  ,  SUM(C_UPSO_CNT)  C_UPSO_CNT  ,  SUM(I_UPSO_CNT)  I_UPSO_CNT  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD  ,  COUNT(*)  B_UPSO_CNT  ,  0  N_UPSO_CNT  ,  0  C_UPSO_CNT  ,  0  I_UPSO_CNT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  TO_CHAR(INS_DATE,'YYYYMMDD')  <  :START_DAY   \n";
        query +=" AND  UPSO_STAT='1'  MINUS   \n";
        query +=" SELECT  A.UPSO_CD  FROM  GIBU.TBRA_UPSO_CLSED  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.CLSED_DAY  >=  '20090101'   \n";
        query +=" AND  A.CLSED_DAY  <  :START_DAY   \n";
        query +=" AND  A.CLSED_GBN  =  '14'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD  MINUS   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  CLSBS_INS_DAY  <  '200901'  )  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  A.UPSO_CD  GROUP  BY  B.BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  BRAN_CD  ,  0  B_UPSO_CNT  ,  COUNT(*)  N_UPSO_CNT  ,  0  C_UPSO_CNT  ,  0  I_UPSO_CNT  FROM  GIBU.TBRA_UPSO  WHERE  TO_CHAR(INS_DATE,'YYYYMMDD')  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  UPSO_STAT='1'  GROUP  BY  BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  B.BRAN_CD  ,  0  B_UPSO_CNT  ,  0  N_UPSO_CNT  ,  COUNT(A.UPSO_CD)  C_UPSO_CNT  ,  0  I_UPSO_CNT  FROM  GIBU.TBRA_UPSO_CLSED  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.CLSED_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.CLSED_GBN  =  '14'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD  GROUP  BY  B.BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  B.BRAN_CD  ,  0  B_UPSO_CNT  ,  0  N_UPSO_CNT  ,  0  C_UPSO_CNT  ,  COUNT(*)  I_UPSO_CNT  FROM  (   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  TO_CHAR(INS_DATE,'YYYYMMDD')  <=  :END_DAY   \n";
        query +=" AND  UPSO_STAT='1'  MINUS   \n";
        query +=" SELECT  A.UPSO_CD  FROM  GIBU.TBRA_UPSO_CLSED  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.CLSED_DAY  BETWEEN  '20090101'   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.CLSED_GBN  =  '14'   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD  MINUS   \n";
        query +=" SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO  WHERE  CLSBS_INS_DAY  <  '200901'  )  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.UPSO_CD  =  A.UPSO_CD  GROUP  BY  B.BRAN_CD  )  TA  ,  INSA.TCPM_DEPT  TB  WHERE  TB.GIBU  =  TA.BRAN_CD  GROUP  BY  TA.BRAN_CD,  TB.DEPT_NM  )  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$bran_progress
    //##**$$end
}
