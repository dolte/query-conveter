
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_t16_1
{
    public bra04_t16_1()
    {
    }
    //##**$$adj_search
    /*
    */
    public DOBJ CTLadj_search(DOBJ dobj)
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
            dobj  = CALLadj_search_SEL1(Conn, dobj);           //  정정금액조회
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
    public DOBJ CTLadj_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLadj_search_SEL1(Conn, dobj);           //  정정금액조회
        return dobj;
    }
    // 정정금액조회
    public DOBJ CALLadj_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLadj_search_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLadj_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.NONPY_DAY  ,  A.SEQ  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.ADJ_AMT  ,  A.BEFORE_ADJ_AMT  ,  A.COMIS_AMT  ,  A.BEFORE_COMIS_AMT  ,  A.ADJ_GBN  ,  FIDU.GET_CODE_NM  ('00412',  A.ADJ_GBN)  AS  ADJ_GBN_NM  ,  DECODE(C.SATN1,  null,  '-',  FIDU.GET_STAFF_NM  (A.SATN1))  AS  SATN1  ,  DECODE(C.SATN2,  null,  '-',  FIDU.GET_STAFF_NM  (A.SATN2))  AS  SATN2  ,  DECODE(C.SATN3,  null,  '-',  FIDU.GET_STAFF_NM  (A.SATN3))  AS  SATN3  ,  TO_CHAR(A.INS_DATE,  'YYYY-MM-DD')  AS  INS_DATE  ,  FIDU.GET_STAFF_NM  (A.INSPRES_ID)  AS  INSPRES_NM  ,  A.BIGO  FROM  GIBU.TBRA_MISU_ADJ  A  ,  GIBU.TBRA_UPSO  B  ,  GIBU.TBRA_MISU_ADJ_SATN  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  C.BRAN_CD  =  B.BRAN_CD   \n";
        query +=" AND  C.ADJ_GBN  =  A.ADJ_GBN  ORDER  BY  NONPY_DAY  DESC,  SEQ  DESC ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$adj_search
    //##**$$end
}
