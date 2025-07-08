
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac09_r02_22
{
    public tac09_r02_22()
    {
    }
    //##**$$trans_result
    /*
    */
    public DOBJ CTLtrans_result(DOBJ dobj)
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
            dobj  = CALLtrans_result_SEL1(Conn, dobj);           //  은행별내역
            dobj  = CALLtrans_result_SEL5(Conn, dobj);           //  상세내역
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
    public DOBJ CTLtrans_result( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtrans_result_SEL1(Conn, dobj);           //  은행별내역
        dobj  = CALLtrans_result_SEL5(Conn, dobj);           //  상세내역
        return dobj;
    }
    // 은행별내역
    public DOBJ CALLtrans_result_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtrans_result_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtrans_result_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.IN_BANK_CD  BANK_CD,  NVL  (B.BANK_NM,  '협회에  등록되지  않은  은행코드-'  ||  A.IN_BANK_CD)  BANK_NM,  COUNT  (*)  TOT_CNT,  SUM  (TRAN_AMT)  TOT_AMT,  SUM  (DECODE  (PROC_STS,  '0000',  0,  1))  F_CNT,  SUM  (DECODE  (PROC_STS,  '0000',  0,  TRAN_AMT))  F_AMT,  SUM  (DECODE  (PROC_STS,  '0000',  1,  0))  S_CNT,  SUM  (DECODE  (PROC_STS,  '0000',  TRAN_AMT,  0))  S_AMT  FROM  ACCT.IF_FILE_DETAIL  A,  ACCT.TCAC_BANK  B  WHERE  1  =  1   \n";
        query +=" AND  A.IN_BANK_CD  =  B.BANK_CD(+)   \n";
        query +=" AND  A.OUT_RMK  =  :YRMN  GROUP  BY  A.IN_BANK_CD,  B.BANK_NM  ORDER  BY  A.IN_BANK_CD,  B.BANK_NM ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        return sobj;
    }
    // 상세내역
    public DOBJ CALLtrans_result_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtrans_result_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtrans_result_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(FIDU.GET_CODE_NM  (  '00509',  CASE  WHEN  PROC_STS='0000'  THEN  '0000'  WHEN  PROC_STS  <>  '0000'   \n";
        query +=" AND  PROC_STS<>'XXXX'  THEN  A.PROC_STS  WHEN  PROC_STS  ='XXXX'  THEN  A.REF_PROC_STS  END),'예금주조회  취소')  PROC_STS,  A.TRAN_DATE,  A.OUT_ACCT_NO,  '대량이체'  GBN,  A.IN_BANK_CD,  B.BANK_NM,  A.IN_ACCT_NO,  A.REGI_REF_NM,  A.TRAN_AMT,  CASE  WHEN  PROC_STS  =  '0000'  THEN  A.TRAN_AMT  ELSE  0  END  TOT_AMT,  CASE  WHEN  PROC_STS  <>  '0000'THEN  A.TRAN_AMT  ELSE  0  END  ERR_AMT,  '0'  COMIS,  OUT_RMK,  IN_RMK  FROM  ACCT.IF_FILE_DETAIL  A,  ACCT.TCAC_BANK  B  WHERE  1  =  1   \n";
        query +=" AND  A.IN_BANK_CD  =  B.BANK_CD(+)   \n";
        query +=" AND  A.OUT_RMK  =  :YRMN  ORDER  BY  A.PROC_STS,A.REGI_REF_NM ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        return sobj;
    }
    //##**$$trans_result
    //##**$$end
}
