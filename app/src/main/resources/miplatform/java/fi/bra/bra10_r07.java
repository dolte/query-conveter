
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_r07
{
    public bra10_r07()
    {
    }
    //##**$$search_satn
    /*
    */
    public DOBJ CTLsearch_satn(DOBJ dobj)
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
            dobj  = CALLsearch_satn_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLsearch_satn( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_satn_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLsearch_satn_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_satn_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_satn_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  X.BRAN_CD  ,  X.BRAN_NM  ,  X.CODE_CD  AS  ADJ_GBN  ,  X.CODE_NM  AS  ADJ_GBN_NM  ,  Y.SATN1  ,  Y.SATN2  ,  Y.SATN3  FROM  (   \n";
        query +=" SELECT  BRAN_CD  ,  BRAN_NM  ,  CODE_CD  ,  CODE_NM  ,  SORT_CD  FROM  (   \n";
        query +=" SELECT  GIBU  AS  BRAN_CD  ,  DEPT_NM  AS  BRAN_NM  FROM  INSA.TCPM_DEPT  WHERE  PAR_DEPT_CD  LIKE  '106010%'   \n";
        query +=" AND  GIBU  IS  NOT  NULL  )  A,  (   \n";
        query +=" SELECT  CODE_CD,  CODE_NM,  SORT_CD  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00412'   \n";
        query +=" AND  USE_YN  =  'Y'  )  B  )X  ,  GIBU.TBRA_MISU_ADJ_SATN  Y  WHERE  1=1   \n";
        query +=" AND  Y.BRAN_CD(+)  =  X.BRAN_CD   \n";
        query +=" AND  Y.ADJ_GBN(+)  =  X.CODE_CD   \n";
        query +=" AND  X.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  X.BRAN_CD,  :BRAN_CD)  ORDER  BY  X.BRAN_CD,  X.SORT_CD ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$search_satn
    //##**$$save_satn
    /*
    */
    public DOBJ CTLsave_satn(DOBJ dobj)
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
            dobj  = CALLsave_satn_UNI5(Conn, dobj);           //  저장
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
    public DOBJ CTLsave_satn( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_satn_UNI5(Conn, dobj);           //  저장
        return dobj;
    }
    // 저장
    public DOBJ CALLsave_satn_UNI5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UNI5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("UNI5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0 , rtncnt=0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_satn_UNI5UPD(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            if((updcnt= qexe.executeUpdate(Conn, sobj)) < 1)
            {
                sobj = SQLsave_satn_UNI5INS(dobj, dvobj);
                rtncnt += qexe.executeUpdate(Conn, sobj);
            }
            else
            {
                rtncnt += updcnt;
            }
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UNI5") ;
        rvobj.Println("UNI5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_satn_UNI5UPD(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   ADJ_GBN = dvobj.getRecord().get("ADJ_GBN");   //조정 구분
        String   SATN1 = dvobj.getRecord().get("SATN1");   //사무장결재
        String   SATN3 = dvobj.getRecord().get("SATN3");   //본부결재
        String   SATN2 = dvobj.getRecord().get("SATN2");   //지부장결재
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_MISU_ADJ_SATN  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , SATN2=:SATN2 , SATN3=:SATN3 , SATN1=:SATN1 , MOD_DATE=SYSDATE  \n";
        query +=" where ADJ_GBN=:ADJ_GBN  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ADJ_GBN", ADJ_GBN);               //조정 구분
        sobj.setString("SATN1", SATN1);               //사무장결재
        sobj.setString("SATN3", SATN3);               //본부결재
        sobj.setString("SATN2", SATN2);               //지부장결재
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    private SQLObject SQLsave_satn_UNI5INS(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   ADJ_GBN = dvobj.getRecord().get("ADJ_GBN");   //조정 구분
        String   SATN1 = dvobj.getRecord().get("SATN1");   //사무장결재
        String   SATN3 = dvobj.getRecord().get("SATN3");   //본부결재
        String   SATN2 = dvobj.getRecord().get("SATN2");   //지부장결재
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_MISU_ADJ_SATN (MODPRES_ID, SATN2, SATN3, SATN1, MOD_DATE, ADJ_GBN, BRAN_CD)  \n";
        query +=" values(:MODPRES_ID , :SATN2 , :SATN3 , :SATN1 , SYSDATE, :ADJ_GBN , :BRAN_CD )";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ADJ_GBN", ADJ_GBN);               //조정 구분
        sobj.setString("SATN1", SATN1);               //사무장결재
        sobj.setString("SATN3", SATN3);               //본부결재
        sobj.setString("SATN2", SATN2);               //지부장결재
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    //##**$$save_satn
    //##**$$end
}
