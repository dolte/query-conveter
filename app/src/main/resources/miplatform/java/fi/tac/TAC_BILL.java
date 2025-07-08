
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class TAC_BILL
{
    public TAC_BILL()
    {
    }
    //##**$$DEL_BILL_SEARCH
    /*
    */
    public DOBJ CTLDEL_BILL_SEARCH(DOBJ dobj)
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
            dobj  = CALLDEL_BILL_SEARCH_SEL1(Conn, dobj);           //  계산서삭제
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
    public DOBJ CTLDEL_BILL_SEARCH( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLDEL_BILL_SEARCH_SEL1(Conn, dobj);           //  계산서삭제
        return dobj;
    }
    // 계산서삭제
    public DOBJ CALLDEL_BILL_SEARCH_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLDEL_BILL_SEARCH_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLDEL_BILL_SEARCH_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ISS_DAY = dobj.getRetObject("S").getRecord().get("ISS_DAY");   //발행 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEMD_NUM,  A.BILL_NUM,  A.ISS_DAY,  A.BILL_KND,  B.CODE_NM  AS  BILL_KND_NM,  A.BILL_GBN,  C.CODE_NM  AS  BILL_GBN_NM,  A.BSCON_CD,  A.BIPLC_GBN,  A.BRAN_CD,  A.BSCON_NM,  A.SUPPPRES_NM,  A.BSCON_INS_NUM,  A.BSCON_FNM_NM,  A.BSCON_REPPRES_NM,  A.BSCON_POST_NUM,  A.BSCON_ADDR,  A.BSCON_BSCDTN,  A.BSCON_BSTYP,  A.SUPPPRES_CD,  A.SUPPPRES_INS_NUM,  A.SUPPPRES_FNM_NM,  A.SUPPPRES_REPPRES_NM,  A.SUPPPRES_ADDR,  A.SUPPPRES_BSCDTN,  A.SUPPPRES_BSTYP,  A.SUPPTEMP_AMT,  A.ATAX_AMT,  A.REMAK,  A.ISS_YN  FROM  FIDU.TTAC_BILL  A  ,FIDU.TENV_CODE  B  ,  FIDU.TENV_CODE  C  WHERE  1=1   \n";
        query +=" AND  B.HIGH_CD  =  '00283'   \n";
        query +=" AND  C.HIGH_CD  =  '00216'   \n";
        query +=" AND  A.BILL_KND  =  B.CODE_CD   \n";
        query +=" AND  A.BILL_GBN  =  C.CODE_CD   \n";
        query +=" AND  A.DEL_YN  =  'Y'   \n";
        query +=" AND  A.BILL_KND  IN(3,4)   \n";
        query +=" AND  SUBSTR(A.ISS_DAY,1,6)  =:ISS_DAY  ORDER  BY  A.ISS_DAY,  A.DEMD_NUM ";
        sobj.setSql(query);
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        return sobj;
    }
    //##**$$DEL_BILL_SEARCH
    //##**$$end
}
