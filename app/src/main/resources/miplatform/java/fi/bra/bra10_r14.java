
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_r14
{
    public bra10_r14()
    {
    }
    //##**$$bra10_r14_select
    /*
    */
    public DOBJ CTLbra10_r14_select(DOBJ dobj)
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
            dobj  = CALLbra10_r14_select_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLbra10_r14_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra10_r14_select_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLbra10_r14_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra10_r14_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra10_r14_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPL_YRMN = dobj.getRetObject("S").getRecord().get("APPL_YRMN");        //적용 년월
        String   APPL_YRMN_1 = dobj.getRetObject("S").getRecord().get("APPL_YRMN");   //적용 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(A.BRAN_CD,'O','K',A.BRAN_CD)  AS  BRAN_CD,  B.DEPT_NM  AS  BRAN_NM,  A.STAFF_CD,  DECODE(A.BRAN_CD,'O','출장소장',C.FUNC_NM)  AS  FUNC_NM,  C.HAN_NM  AS  STAFF_NM,  DECODE(A.TOT_BONUS,0,0,TRUNC(C.RATE  /  E.TOTAL_RATE  *  D.TOTAL_BONUS))  AS  LEVY_ACTRE_BONUS,  DECODE(A.TOT_BONUS,0,0,C.RATE)  AS  LEVY_ACTRE_RATE,  DECODE(A.TOT_BONUS,0,0,TRUNC(C.RATE  /  E.TOTAL_RATE  *  100))  RATE  FROM  GIBU.TBRA_ACTRE_BONUS  A,   \n";
        query +=" (SELECT  GIBU,  DEPT_NM,  DEPT_CD  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  LIKE  '1060%'   \n";
        query +=" AND  GIBU  IS  NOT  NULL  )  B,   \n";
        query +=" (SELECT  AA.STAFF_NUM,  AA.HAN_NM,  AA.JOB_CD,  (CASE  WHEN  AA.JOB_CD  =  '100'  THEN  '1'  WHEN  AA.JOB_CD  =  '120'  THEN  '3'  WHEN  AA.JOB_CD  =  '140'  THEN  '5'  WHEN  AA.JOB_CD  =  '130'  THEN  '7'  END  )  AS  JOB_CD2,   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00145'   \n";
        query +=" AND  CODE_CD  =  AA.JOB_CD  )  FUNC_NM,  (CASE  WHEN  JOB_CD  =  '100'  THEN  130  WHEN  JOB_CD  =  '120'  THEN  120  WHEN  JOB_CD  =  '140'  THEN  50  WHEN  JOB_CD  =  '130'  THEN  100  END  )  RATE  FROM  INSA.TINS_MST01  AA  )  C,   \n";
        query +=" (SELECT  BRAN_CD,  LEVY_BONUS  +  EXT_BONUS  +  UPSO_CNT_BONUS  AS  TOTAL_BONUS  FROM  GIBU.TBRA_BRAN_BONUS_BRE  WHERE  APPL_YRMN  =  SUBSTR(:APPL_YRMN_1,0,6)  )  D,   \n";
        query +=" (SELECT  A.BRAN_CD,  (SUM(CASE  WHEN  JOB_CD  =  '100'  THEN  130  WHEN  JOB_CD  =  '120'  THEN  120  WHEN  JOB_CD  =  '140'  THEN  50  WHEN  JOB_CD  =  '130'  THEN  100  END)  )  TOTAL_RATE  FROM  GIBU.TBRA_ACTRE_BONUS  A,  INSA.TINS_MST01  B  WHERE  A.STAFF_CD  =  B.STAFF_NUM   \n";
        query +=" AND  A.APPL_YRMN  =  SUBSTR(:APPL_YRMN_1,0,6)  GROUP  BY  A.BRAN_CD  )  E  WHERE  DECODE(A.BRAN_CD,'O','K',A.BRAN_CD)  =  B.GIBU(+)   \n";
        query +=" AND  A.STAFF_CD  =  C.STAFF_NUM   \n";
        query +=" AND  DECODE(A.BRAN_CD,'O','K',A.BRAN_CD)  =  D.BRAN_CD(+)   \n";
        query +=" AND  DECODE(A.BRAN_CD,'O','K',A.BRAN_CD)  =  E.BRAN_CD(+)   \n";
        query +=" AND  A.APPL_YRMN  =  SUBSTR(:APPL_YRMN_1,0,6)  ORDER  BY  DECODE(A.BRAN_CD,'O','K',A.BRAN_CD),DECODE(A.BRAN_CD,'O','4',C.JOB_CD2) ";
        sobj.setSql(query);
        sobj.setString("APPL_YRMN_1", APPL_YRMN_1);               //적용 년월
        return sobj;
    }
    //##**$$bra10_r14_select
    //##**$$end
}
