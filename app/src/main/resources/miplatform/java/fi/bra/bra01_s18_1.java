
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s18_1
{
    public bra01_s18_1()
    {
    }
    //##**$$search_prod_upso
    /*
    */
    public DOBJ CTLsearch_prod_upso(DOBJ dobj)
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
            dobj  = CALLsearch_prod_upso_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLsearch_prod_upso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_prod_upso_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLsearch_prod_upso_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_prod_upso_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_prod_upso_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROD_CD = dobj.getRetObject("S").getRecord().get("PROD_CD");   //작품 코드
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("PROD_CD");   //업소 코드
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT X.SONG_ID , Y.PROD_NM , Y.SINA_NM , Y.JAKSAPRES_NM , Y.PRDUCPRES_NM , DECODE(Y.RM_YN, null, 'N', Y.RM_YN) AS RM_YN , (SELECT DECODE(COUNT(*), 0, 'N', 'Y')  ";
        query +=" FROM FIDU.TDIS_MEDYPRODINFO  ";
        query +=" WHERE MEDY_PROD_CD = Y.BSCON_PROD_CD  ";
        query +=" AND BSCON_CD = Y.BSCON_CD) AS MEDY_YN , X.CNT , X.BRAN_CD , X.UPSO_CD , (SELECT UPSO_NM  ";
        query +=" FROM GIBU.TBRA_UPSO  ";
        query +=" WHERE UPSO_CD = X.UPSO_CD) AS UPSO_NM , GIBU.FT_GET_BSTYP_INFO(X.UPSO_CD) AS BSTYP_CD , (SELECT STAFF_CD  ";
        query +=" FROM GIBU.TBRA_UPSO  ";
        query +=" WHERE UPSO_CD = X.UPSO_CD) AS STAFF_CD , X.BSCON_CD  ";
        query +=" FROM (  ";
        query +=" SELECT SONG_ID , UPSO_CD, BRAN_CD , COUNT(*) AS CNT , (SELECT --+ INDEX_DESC (KDS_SHOPROOM PK_KDS_SM_SEQ) \n BSCON_CD  ";
        query +=" FROM LOG.KDS_SHOPROOM  ";
        query +=" WHERE UPSO_CD = A.UPSO_CD  ";
        query +=" AND CO_STATUS = '07001'  ";
        query +=" AND ROWNUM = 1) AS BSCON_CD  ";
        query +=" FROM LOG.KDS_STATISTICS A  ";
        query +=" WHERE PLAY_SDATE BETWEEN TO_DATE(:START_YRMN||'000000', 'YYYYMMDDHH24MISS')  ";
        query +=" AND TO_DATE(:END_YRMN||'235959', 'YYYYMMDDHH24MISS')  ";
        query +=" AND BRAN_CD = DECODE(:BRAN_CD, 'AL', BRAN_CD, :BRAN_CD)  ";
        if( !PROD_CD.equals("") )
        {
            query +=" AND SONG_ID = :PROD_CD  ";
        }
        if( !UPSO_CD.equals("") )
        {
            query +=" AND UPSO_CD = :UPSO_CD  ";
        }
        query +=" GROUP BY SONG_ID, UPSO_CD, BRAN_CD ) X , (  ";
        query +=" SELECT BSCON_CD , BSCON_PROD_CD , PROD_NM , SINA_NM , JAKSAPRES_NM , PRDUCPRES_NM , RM_YN  ";
        query +=" FROM FIDU.TDIS_ACMCNRECTUNEINFO  ";
        query +=" WHERE BSCON_CD = 'E0006'  ";
        if( !PROD_CD.equals("") )
        {
            query +=" AND BSCON_PROD_CD = :PROD_CD  ";
        }
        query +=" ) Y  ";
        query +=" WHERE X.SONG_ID = Y.BSCON_PROD_CD  ";
        query +=" AND X.BSCON_CD = Y.BSCON_CD  ";
        sobj.setSql(query);
        if(!PROD_CD.equals(""))
        {
            sobj.setString("PROD_CD", PROD_CD);               //작품 코드
        }
        if(!UPSO_CD.equals(""))
        {
            sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        }
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    //##**$$search_prod_upso
    //##**$$end
}
