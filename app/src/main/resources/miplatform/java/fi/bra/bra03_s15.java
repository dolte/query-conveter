
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra03_s15
{
    public bra03_s15()
    {
    }
    //##**$$get_list
    /* 카드자동이체신청정보를 카드사에 보낸 후 받은 응답에서 결과값이 01인 경우는
    카드발급은 되었으나 카드가 정상적이지 않아서 승인청구가 불가능한 상황이다.
    이때 직원은 업주에게 전화를해서 이런 사실을 알려주고 업주는 선택을 해야한다.
    1) 그래도 카드로 자동이체 하겠다. (카드사에 업주가 전화해서 청구되도록 조치필요)
    2) 카드로 자동이체 하지않겠다. (무통장이나 계좌자동이체를 사용하겠다)
    1)을 선택한 경우는 정상등록 처리 해주면 된다. (rslt.apptn_rslt는 01 그대로 유지시킨다.)
    2)를 선택한 경우는 (rslt.err_gbn을 99로 바꾸고 신청취소건이 SI에 입력& 확인되면 98로 변경시킨다.)
    */
    public DOBJ CTLget_list(DOBJ dobj)
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
            dobj  = CALLget_list_SEL1(Conn, dobj);           //  미처리 01 목록조회
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
    public DOBJ CTLget_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_list_SEL1(Conn, dobj);           //  미처리 01 목록조회
        return dobj;
    }
    // 미처리 01 목록조회
    public DOBJ CALLget_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.PROC_DAY  ,  A.SEQ_NUM  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.APPTN_DAY  ,  A.APPTN_GBN  ,  A.RESINUM  ,  A.PAYPRES_NM  ,  A.CARD_GBN  ,  A.EXPIRE_DAY  ,  A.CARD_NUM  ,  BIOWN_INSNUM  FROM  GIBU.TBRA_UPSO_AUTORSLT_TEST  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  RECPT_GBN_CD  =  '7'   \n";
        query +=" AND  APPTN_RSLT  =  '01'   \n";
        query +=" AND  (ERR_GBN  IS  NULL   \n";
        query +=" OR  ERR_GBN  =  '99') ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$get_list
    //##**$$end
}
