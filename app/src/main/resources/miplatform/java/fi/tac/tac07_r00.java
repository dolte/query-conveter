
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac07_r00
{
    public tac07_r00()
    {
    }
    //##**$$tac07_r00_select
    /* * 프로그램명 : tac07_r00
    * 작성자 : 손주환
    * 작성일 : 2009/10/13
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac07_r00_select(DOBJ dobj)
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
            dobj  = CALLtac07_r00_select_SEL1(Conn, dobj);           //  거래처검색
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
    public DOBJ CTLtac07_r00_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac07_r00_select_SEL1(Conn, dobj);           //  거래처검색
        return dobj;
    }
    // 거래처검색
    // 거래처검색
    public DOBJ CALLtac07_r00_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac07_r00_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac07_r00_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPPRES_NM = dobj.getRetObject("S").getRecord().get("REPPRES_NM");   //대표자 명
        String   BIOWN_GBN = dobj.getRetObject("S").getRecord().get("BIOWN_GBN");   //사업자 구분
        String   BSCONHAN_NM = dobj.getRetObject("S").getRecord().get("BSCONHAN_NM");   //거래처한글 명
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BSCON_CD, BSCONHAN_NM, REPPRES_NM, CONTRCCLSN_YN, BIOWN_GBN, BANK_CD, ACCN_NUM, (SELECT DISTINCT LARGECLASS_CD_NM  ";
        query +=" FROM FIDU.TENV_MDMCD  ";
        query +=" WHERE LARGECLASS_CD = MSTR_MDM_CD) AS MSTR_MDM_CD_NM  ";
        query +=" FROM FIDU.TLEV_BSCON  ";
        query +=" WHERE BSCON_GBN = '008'  ";
        if( !BSCON_CD.equals("") )
        {
            query +=" AND BSCON_CD LIKE '%' || :BSCON_CD || '%'  ";
        }
        if( !BSCONHAN_NM.equals("") )
        {
            query +=" AND BSCONHAN_NM LIKE '%' || :BSCONHAN_NM || '%'  ";
        }
        if( !REPPRES_NM.equals("") )
        {
            query +=" AND REPPRES_NM LIKE '%' || :REPPRES_NM || '%'  ";
        }
        if( !BIOWN_GBN.equals("") )
        {
            query +=" AND BIOWN_GBN LIKE '%' || :BIOWN_GBN || '%'  ";
        }
        query +=" ORDER BY BSCON_CD  ";
        sobj.setSql(query);
        if(!REPPRES_NM.equals(""))
        {
            sobj.setString("REPPRES_NM", REPPRES_NM);               //대표자 명
        }
        if(!BIOWN_GBN.equals(""))
        {
            sobj.setString("BIOWN_GBN", BIOWN_GBN);               //사업자 구분
        }
        if(!BSCONHAN_NM.equals(""))
        {
            sobj.setString("BSCONHAN_NM", BSCONHAN_NM);               //거래처한글 명
        }
        if(!BSCON_CD.equals(""))
        {
            sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        }
        return sobj;
    }
    //##**$$tac07_r00_select
    //##**$$end
}
