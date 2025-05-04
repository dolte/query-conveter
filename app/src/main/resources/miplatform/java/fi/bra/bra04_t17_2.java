
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_t17_2
{
    public bra04_t17_2()
    {
    }
    //##**$$misu_select_2
    /*
    */
    public DOBJ CTLmisu_select_2(DOBJ dobj)
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
            dobj  = CALLmisu_select_2_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLmisu_select_2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmisu_select_2_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLmisu_select_2_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmisu_select_2_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmisu_select_2_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.NONPY_DAY , A.BRAN_CD , FIDU.GET_GIBU_NM(A.BRAN_CD) AS BRAN_NM , A.UPSO_CD , B.UPSO_NM , B.STAFF_CD , FIDU.GET_STAFF_NM(B.STAFF_CD) AS STAFF_NM , A.BEFORE_NONPY_AMT , A.DEMD_AMT , A.ADDT_AMT , A.EADDT_AMT , A.DEMD_MMCNT , A.REPT_AMT , A.COMIS_AMT , A.RETURN_AMT , A.RETURN_COMIS_AMT , (A.ADJ_CLSED_AMT + A.ADJ_INDTN_AMT + A.ADJ_REPT_R_AMT + A.ADJ_REPT_C_AMT + A.ADJ_RETURN_R_AMT + A.ADJ_RETURN_C_AMT + A.ADJ_ETC_AMT) AS ADJ_AMT , A.NONPY_AMT , TO_CHAR(A.COMPN_DATE, 'YYYYMMDD') AS INS_DATE  ";
        query +=" FROM GIBU.TBRA_MISU A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE A.BRAN_CD = B.BRAN_CD  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD  ";
        query +=" AND A.BRAN_CD = :BRAN_CD  ";
        query +=" AND A.NONPY_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if( !UPSO_CD.equals("") )
        {
            query +=" AND A.UPSO_CD = :UPSO_CD  ";
        }
        if( !STAFF_CD.equals("") )
        {
            query +=" AND B.STAFF_CD = :STAFF_CD  ";
        }
        query +=" ORDER BY A.DEMD_MMCNT DESC, A.BEFORE_NONPY_AMT DESC  ";
        sobj.setSql(query);
        if(!STAFF_CD.equals(""))
        {
            sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        }
        sobj.setString("START_DAY", START_DAY);               //시작일
        if(!UPSO_CD.equals(""))
        {
            sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        }
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$misu_select_2
    //##**$$end
}
