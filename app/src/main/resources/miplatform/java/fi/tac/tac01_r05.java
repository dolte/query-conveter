
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac01_r05
{
    public tac01_r05()
    {
    }
    //##**$$EventID30
    /*
    */
    public DOBJ CTLEventID30(DOBJ dobj)
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
            dobj  = CALLEventID30_SEL1(Conn, dobj);           //  SEL
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
    public DOBJ CTLEventID30( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLEventID30_SEL1(Conn, dobj);           //  SEL
        return dobj;
    }
    // SEL
    public DOBJ CALLEventID30_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLEventID30_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID30_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   A_TO = dobj.getRetObject("S").getRecord().get("A_TO");   //종료일자
        String   A_FROM = dobj.getRetObject("S").getRecord().get("A_FROM");   //시작일자
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT REPT_DAY, (SELECT CODE_NM  ";
        query +=" FROM FIDU.TENV_CODE  ";
        query +=" WHERE CODE_CD = DEMD_GBN  ";
        query +=" AND HIGH_CD ='00042') DEMD_GBN, FIDU.GET_BSCON_NM(BSCON_CD) BSCON_NM, BSCON_CD , SUM(LEVY_AMT) LEVY_AMT, SUM(ATAX_AMT) ATAX_AMT, SUM(LEVY_AMT + ATAX_AMT) TOT_AMT, FIDU.GET_APPRV_REMAK(DEMD_NUM) REMAK  ";
        query +=" FROM FIDU.TLEV_USEDEMDREPT  ";
        query +=" WHERE DEMD_DAY >= :A_FROM  ";
        query +=" AND DEMD_DAY <= :A_TO  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD =:BSCON_CD  ";
        }
        query +=" AND SUBSTR(MDM_CD,1,2) <> 'DA'  ";
        query +=" GROUP BY REPT_DAY, DEMD_GBN, BSCON_CD, FIDU.GET_APPRV_REMAK(DEMD_NUM), BSCON_CD  ";
        sobj.setSql(query);
        sobj.setString("A_TO", A_TO);               //종료일자
        sobj.setString("A_FROM", A_FROM);               //시작일자
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        }
        return sobj;
    }
    //##**$$EventID30
    //##**$$end
}
