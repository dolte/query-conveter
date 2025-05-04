
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s13
{
    public bra03_s13()
    {
    }
    //##**$$search_list
    /*
    */
    public DOBJ CTLsearch_list(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
            Conn.setAutoCommit(false);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLsearch_list_SEL1(Conn, dobj);           //  삼성 카드신청서 조회
            dobj  = CALLsearch_list_SEL2(Conn, dobj);           //  신한 카드신청서 조회
            dobj  = CALLsearch_list_XIUD3(Conn, dobj);           //  신청서에 보낸날짜 기록
            Conn.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
                Conn.rollback();
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
    public DOBJ CTLsearch_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_list_SEL1(Conn, dobj);           //  삼성 카드신청서 조회
        dobj  = CALLsearch_list_SEL2(Conn, dobj);           //  신한 카드신청서 조회
        dobj  = CALLsearch_list_XIUD3(Conn, dobj);           //  신청서에 보낸날짜 기록
        return dobj;
    }
    // 삼성 카드신청서 조회
    public DOBJ CALLsearch_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XC.PROC_DAY  ,  '삼성카드'  AS  GBN  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  ROWNUM  AS  SEQ_NUM  ,  XC.APPLICATION_GBN  AS  APPTN_GBN  ,  XC.APPTN_DAY  ,  XA.CLIENT_NUM  ,  ''  AS  EXPIRE_DAY  ,  ''  AS  CARD_NUM  ,  XC.PAYPRES_NM  ,  XC.PHON_NUM  ,  XC.RESINUM  ,  XC.BIOWN_INSNUM  ,  '00'  APPTN_RSLT  FROM  GIBU.TBRA_UPSO  XA  ,  GIBU.TBRA_UPSO_AUTO_APPLICATION  XC  WHERE  1=1   \n";
        query +=" AND  TO_CHAR(XC.INS_DATE,  'YYYYMMDD')  <=  :PROC_DAY   \n";
        query +=" AND  XC.CONFIRM_ID  IS  NOT  NULL   \n";
        query +=" AND  (XC.PROC_DAY  IS  NULL   \n";
        query +=" OR  XC.PROC_DAY  =  :PROC_DAY)   \n";
        query +=" AND  XA.UPSO_CD  =  XC.UPSO_CD   \n";
        query +=" AND  XC.RECEPTION_GBN  =  '7'   \n";
        query +=" AND  XC.APPLICATION_GBN  IN  ('12')   \n";
        query +=" AND  XC.CARD_GBN  =  'WIN'  --  삼성카드  ORDER  BY  SEQ_NUM  ASC,  APPTN_GBN  DESC ";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    // 신한 카드신청서 조회
    public DOBJ CALLsearch_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_list_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_list_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.BRAN_CD  ,  XC.PROC_DAY  ,  '신한카드'  AS  GBN  ,  XA.UPSO_CD  ,  XA.UPSO_NM  ,  ROWNUM  AS  SEQ_NUM  ,  XC.APPLICATION_GBN  AS  APPTN_GBN  ,  XC.APPTN_DAY  ,  XA.CLIENT_NUM  ,  ''  AS  EXPIRE_DAY  ,  ''  AS  CARD_NUM  ,  XC.PAYPRES_NM  ,  XC.PHON_NUM  ,  XC.RESINUM  ,  XC.BIOWN_INSNUM  ,  '00'  APPTN_RSLT  FROM  GIBU.TBRA_UPSO  XA  ,  GIBU.TBRA_UPSO_AUTO_APPLICATION  XC  WHERE  1=1   \n";
        query +=" AND  TO_CHAR(XC.INS_DATE,  'YYYYMMDD')  <=  :PROC_DAY   \n";
        query +=" AND  XC.CONFIRM_ID  IS  NOT  NULL   \n";
        query +=" AND  (XC.PROC_DAY  IS  NULL   \n";
        query +=" OR  XC.PROC_DAY  =  :PROC_DAY)   \n";
        query +=" AND  XA.UPSO_CD  =  XC.UPSO_CD   \n";
        query +=" AND  XC.RECEPTION_GBN  =  '7'   \n";
        query +=" AND  XC.APPLICATION_GBN  IN  ('12')   \n";
        query +=" AND  XC.CARD_GBN  =  'LGC'  --  신한카드  ORDER  BY  SEQ_NUM  ASC,  APPTN_GBN  DESC ";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    // 신청서에 보낸날짜 기록
    public DOBJ CALLsearch_list_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsearch_list_XIUD3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_list_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_DAY = dobj.getRetObject("S").getRecord().get("PROC_DAY");   //처리 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_AUTO_APPLICATION XC  \n";
        query +=" SET XC.PROC_DAY = :PROC_DAY  \n";
        query +=" WHERE TO_CHAR(XC.INS_DATE, 'YYYYMMDD') <= :PROC_DAY  \n";
        query +=" AND XC.CONFIRM_DATE IS NOT NULL  \n";
        query +=" AND (XC.PROC_DAY IS NULL  \n";
        query +=" OR XC.PROC_DAY = :PROC_DAY)  \n";
        query +=" AND RECEPTION_GBN = '7'  \n";
        query +=" AND XC.APPLICATION_GBN IN ('12')";
        sobj.setSql(query);
        sobj.setString("PROC_DAY", PROC_DAY);               //처리 일자
        return sobj;
    }
    //##**$$search_list
    //##**$$end
}
