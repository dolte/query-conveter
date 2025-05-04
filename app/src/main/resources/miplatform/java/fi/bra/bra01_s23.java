
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s23
{
    public bra01_s23()
    {
    }
    //##**$$get_diff_bscon
    /*
    */
    public DOBJ CTLget_diff_bscon(DOBJ dobj)
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
            dobj  = CALLget_diff_bscon_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLget_diff_bscon( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_diff_bscon_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLget_diff_bscon_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_diff_bscon_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_diff_bscon_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BRAN_CD  ,   \n";
        query +=" (SELECT  DEPT_NM  FROM  INSA.TCPM_DEPT  WHERE  GIBU  =  A.BRAN_CD)  AS  BRAN_NM  ,  B.UPSO_CD  ,  B.UPSO_NM  ,  GIBU.FT_GET_BSTYPGRAD_NM(B.UPSO_CD,  'NM')  AS  BSTYP_NM  ,  B.MNGEMSTR_NM  ,  B.STAFF_CD  ,  A.SERIAL_NO  ,  A.BSCON_CD  ,  A.BSCON_CD_SHOPROOM  ,  TO_CHAR(A.PLAY_SDATE,  'YYYY-MM-DD  HH24:MI')  AS  PLAY_SDATE  ,  FIX_DATE  FROM  (   \n";
        query +=" SELECT  SERIAL_NO,  MIN(PLAY_SDATE)  AS  PLAY_SDATE,  UPSO_CD,  BRAN_CD,  BSCON_CD,  BSCON_CD_SHOPROOM,  ''  AS  FIX_DATE  FROM  (   \n";
        query +=" SELECT  SERIAL_NO,  PLAY_SDATE,  UPSO_CD,  BRAN_CD,  BSCON_CD  ,   \n";
        query +=" (SELECT  --+  INDEX_DESC  (KDS_SHOPROOM  PK_KDS_SM_SEQ)  \n  BSCON_CD  FROM  LOG.KDS_SHOPROOM  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  CO_STATUS  =  '07001'   \n";
        query +=" AND  ROWNUM  =  1)  AS  BSCON_CD_SHOPROOM  FROM  LOG.KDS_STATISTICS  A  WHERE  PLAY_SDATE  BETWEEN  :START_YRMN   \n";
        query +=" AND  :END_YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  WHERE  BSCON_CD  <>  BSCON_CD_SHOPROOM  GROUP  BY  SERIAL_NO,  UPSO_CD,  BRAN_CD,  BSCON_CD,  BSCON_CD_SHOPROOM  UNION  ALL   \n";
        query +=" SELECT  SERIAL_NO,  MIN(PLAY_SDATE)  AS  PLAY_SDATE  ,  UPSO_CD,  BRAN_CD,  BSCON_CD,  BSCON_CD_SHOPROOM  ,  TO_CHAR(MIN(FIX_DATE),  'YYYY-MM-DD  HH24:MI')  AS  FIX_DATE  FROM  (   \n";
        query +=" SELECT  SERIAL_NO,  PLAY_SDATE,  UPSO_CD,  BRAN_CD,  BSCON_CD,  FIX_DATE  ,   \n";
        query +=" (SELECT  --+  INDEX_DESC  (KDS_SHOPROOM  PK_KDS_SM_SEQ)  \n  BSCON_CD  FROM  LOG.KDS_SHOPROOM  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  CO_STATUS  =  '07001'   \n";
        query +=" AND  ROWNUM  =  1)  AS  BSCON_CD_SHOPROOM  FROM  LOG.KDS_STATISTICS_DEL  A  WHERE  PLAY_SDATE  BETWEEN  '20160301'   \n";
        query +=" AND  '20160331'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  WHERE  BSCON_CD  <>  BSCON_CD_SHOPROOM  GROUP  BY  SERIAL_NO,  UPSO_CD,  BRAN_CD,  BSCON_CD,  BSCON_CD_SHOPROOM  )  A,  (   \n";
        query +=" SELECT  A.UPSO_CD,  A.UPSO_NM,  A.MNGEMSTR_NM,  A.STAFF_CD  FROM  GIBU.TBRA_UPSO  A  ,  LOG.KDS_SHOP  B  WHERE  A.UPSO_CD  =  B.UPSO_CD  )  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  B.STAFF_CD  =  DECODE(:STAFF_CD,  '',  B.STAFF_CD,  :STAFF_CD) ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    //##**$$get_diff_bscon
    //##**$$end
}
