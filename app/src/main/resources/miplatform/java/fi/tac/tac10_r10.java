
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r10
{
    public tac10_r10()
    {
    }
    //##**$$searchTac10_r10
    /* * 프로그램명 : tac10_r10
    * 작성자 : 손주환
    * 작성일 : 2009/11/20
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchTac10_r10(DOBJ dobj)
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
            dobj  = CALLsearchTac10_r10_SEL1(Conn, dobj);           //  내역조회
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
    public DOBJ CTLsearchTac10_r10( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchTac10_r10_SEL1(Conn, dobj);           //  내역조회
        return dobj;
    }
    // 내역조회
    public DOBJ CALLsearchTac10_r10_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchTac10_r10_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchTac10_r10_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   WRK_YRMN = dobj.getRetObject("S").getRecord().get("WRK_YRMN");   //작업 년월
        String   ADJ_GBN = dobj.getRetObject("S").getRecord().get("ADJ_GBN");   //ADJ_GBN
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MDM_CD,  A.SUPP_YRMN  as  WRK_YRMN,  A.OBJ_MB_CD,  A.SUPP_MB_CD,  A.ADJ_GBN,  A.ADJ_AMT,  A.ADJ_REAS_CTENT,  C.MDM_CD_NM,  D.HANMB_NM  OBJ_MB_CD_NM,  E.HANMB_NM  AS  SUPP_MB_CD_NM  ,  C.AVECLASS_CD_NM,  A.INSPRES_ID,  A.MODPRES_ID,  A.INS_DATE,  A.MOD_DATE  FROM  FIDU.TTAC_MBDISTRAMTADJ  A,  FIDU.TENV_MDMCD  C,  FIDU.TMEM_MB  D,  FIDU.TMEM_MB  E  WHERE  A.MDM_CD=C.MDM_CD   \n";
        query +=" AND  A.OBJ_MB_CD=D.MB_CD   \n";
        query +=" AND  A.SUPP_MB_CD=E.MB_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN=  :WRK_YRMN   \n";
        query +=" AND  A.ADJ_GBN  LIKE  :ADJ_GBN||'%'  ORDER  BY  A.OBJ_MB_CD  ASC  ,A.SUPP_MB_CD  ASC  ,A.MDM_CD  ASC ";
        sobj.setSql(query);
        sobj.setString("WRK_YRMN", WRK_YRMN);               //작업 년월
        sobj.setString("ADJ_GBN", ADJ_GBN);               //ADJ_GBN
        return sobj;
    }
    //##**$$searchTac10_r10
    //##**$$end
}
