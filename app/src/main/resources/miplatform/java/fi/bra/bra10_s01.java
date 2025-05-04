
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s01
{
    public bra10_s01()
    {
    }
    //##**$$search_upso_juso
    /*
    */
    public DOBJ CTLsearch_upso_juso(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj  = CALLsearch_upso_juso_SEL1(Conn, dobj);           //  미매칭 목록
                dobj  = CALLsearch_upso_juso_MRG7( dobj);        //  결과용
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
            {
                dobj  = CALLsearch_upso_juso_SEL5(Conn, dobj);           //  매칭 목록
                dobj  = CALLsearch_upso_juso_MRG7( dobj);        //  결과용
            }
            else
            {
                dobj  = CALLsearch_upso_juso_SEL6(Conn, dobj);           //  전체 목록
                dobj  = CALLsearch_upso_juso_MRG7( dobj);        //  결과용
            }
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
    public DOBJ CTLsearch_upso_juso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLsearch_upso_juso_SEL1(Conn, dobj);           //  미매칭 목록
            dobj  = CALLsearch_upso_juso_MRG7( dobj);        //  결과용
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
        {
            dobj  = CALLsearch_upso_juso_SEL5(Conn, dobj);           //  매칭 목록
            dobj  = CALLsearch_upso_juso_MRG7( dobj);        //  결과용
        }
        else
        {
            dobj  = CALLsearch_upso_juso_SEL6(Conn, dobj);           //  전체 목록
            dobj  = CALLsearch_upso_juso_MRG7( dobj);        //  결과용
        }
        return dobj;
    }
    // 미매칭 목록
    public DOBJ CALLsearch_upso_juso_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_upso_juso_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_upso_juso_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  X.UPSO_CD  ,  X.UPSO_ZIP  ,  X.UPSO_ADDR1  ||  '  '  ||  X.UPSO_ADDR2  AS  UPSO_ADDR  ,  X.N_UPSO_ZIP  ,  X.N_UPSO_ADDR1  ,  X.N_UPSO_ADDR2  ,  X.N_UPSO_ADDR3  ,  X.N_UPSO_BD_MNG_NUM  FROM  (   \n";
        query +=" SELECT  A.LAST_UPSO_CD  AS  UPSO_CD  FROM  GIBU.EUNDORI_TRANS_JUSO2  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.N_UPSO_BD_MNG_NUM  IS  NULL   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)  GROUP  BY  A.LAST_UPSO_CD  )  Y  ,  GIBU.EUNDORI_TRANS_JUSO2  X  WHERE  X.UPSO_CD  =  Y.UPSO_CD  ORDER  BY  UPSO_ZIP ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 결과용
    public DOBJ CALLsearch_upso_juso_MRG7(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG7");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL5, SEL6","");
        rvobj.setName("MRG7") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 매칭 목록
    public DOBJ CALLsearch_upso_juso_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_upso_juso_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_upso_juso_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  X.UPSO_CD  ,  X.UPSO_ZIP  ,  X.UPSO_ADDR1  ||  '  '  ||  X.UPSO_ADDR2  AS  UPSO_ADDR  ,  X.N_UPSO_ZIP  ,  X.N_UPSO_ADDR1  ,  X.N_UPSO_ADDR2  ,  X.N_UPSO_ADDR3  ,  X.N_UPSO_BD_MNG_NUM  FROM  (   \n";
        query +=" SELECT  A.LAST_UPSO_CD  AS  UPSO_CD  FROM  GIBU.EUNDORI_TRANS_JUSO2  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.N_UPSO_BD_MNG_NUM  IS  NOT  NULL   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)  GROUP  BY  A.LAST_UPSO_CD  )  Y  ,  GIBU.EUNDORI_TRANS_JUSO2  X  WHERE  X.UPSO_CD  =  Y.UPSO_CD  ORDER  BY  UPSO_ZIP ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 전체 목록
    public DOBJ CALLsearch_upso_juso_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_upso_juso_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_upso_juso_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  X.UPSO_CD  ,  X.UPSO_ZIP  ,  X.UPSO_ADDR1  ||  '  '  ||  X.UPSO_ADDR2  AS  UPSO_ADDR  ,  X.N_UPSO_ZIP  ,  X.N_UPSO_ADDR1  ,  X.N_UPSO_ADDR2  ,  X.N_UPSO_ADDR3  ,  X.N_UPSO_BD_MNG_NUM  FROM  (   \n";
        query +=" SELECT  A.LAST_UPSO_CD  AS  UPSO_CD  FROM  GIBU.EUNDORI_TRANS_JUSO2  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.STAFF_CD  =  NVL(:STAFF_CD,  A.STAFF_CD)  GROUP  BY  A.LAST_UPSO_CD  )  Y  ,  GIBU.EUNDORI_TRANS_JUSO2  X  WHERE  X.UPSO_CD  =  Y.UPSO_CD  ORDER  BY  UPSO_ZIP ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$search_upso_juso
    //##**$$search_before_upso_juso
    /*
    */
    public DOBJ CTLsearch_before_upso_juso(DOBJ dobj)
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
            dobj  = CALLsearch_before_upso_juso_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLsearch_before_upso_juso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_before_upso_juso_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLsearch_before_upso_juso_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_before_upso_juso_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_before_upso_juso_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  X.UPSO_CD  ,  X.UPSO_ZIP  ,  X.UPSO_ADDR1  ||  '  '  ||  X.UPSO_ADDR2  AS  UPSO_ADDR  ,  X.N_UPSO_ZIP  ,  X.N_UPSO_ADDR1  ,  X.N_UPSO_ADDR2  ,  X.N_UPSO_ADDR3  ,  X.N_UPSO_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X  WHERE  LAST_UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$search_before_upso_juso
    //##**$$save_upso_juso
    /*
    */
    public DOBJ CTLsave_upso_juso(DOBJ dobj)
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
            dobj  = CALLsave_upso_juso_MIUD4(Conn, dobj);           //  수정된 행만 저장하도록
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
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
    public DOBJ CTLsave_upso_juso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_upso_juso_MIUD4(Conn, dobj);           //  수정된 행만 저장하도록
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 수정된 행만 저장하도록
    public DOBJ CALLsave_upso_juso_MIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD4");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_upso_juso_XIUD2(Conn, dobj);           //  저장하기
            }
        }
        return dobj;
    }
    // 저장하기
    public DOBJ CALLsave_upso_juso_XIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_upso_juso_XIUD2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_upso_juso_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   N_UPSO_ADDR1 = dobj.getRetObject("S").getRecord().get("N_UPSO_ADDR1");   //도로명주소 주소1
        String   N_UPSO_ADDR2 = dobj.getRetObject("S").getRecord().get("N_UPSO_ADDR2");   //도로명주소 주소2
        String   N_UPSO_ADDR3 = dobj.getRetObject("S").getRecord().get("N_UPSO_ADDR3");   //도로명주소 주소3
        String   N_UPSO_BD_MNG_NUM = dobj.getRetObject("S").getRecord().get("N_UPSO_BD_MNG_NUM");   //도로명주소 빌딩관리번호
        String   N_UPSO_ZIP = dobj.getRetObject("S").getRecord().get("N_UPSO_ZIP");   //도로명주소 우편번호
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE  \n";
        query +=" GIBU.EUNDORI_TRANS_JUSO2  \n";
        query +=" SET N_UPSO_ZIP = :N_UPSO_ZIP , N_UPSO_ADDR1 = :N_UPSO_ADDR1 , N_UPSO_ADDR2 = :N_UPSO_ADDR2 , N_UPSO_ADDR3 = :N_UPSO_ADDR3 , N_UPSO_BD_MNG_NUM = :N_UPSO_BD_MNG_NUM , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE  \n";
        query +=" WHERE LAST_UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("N_UPSO_ADDR1", N_UPSO_ADDR1);               //도로명주소 주소1
        sobj.setString("N_UPSO_ADDR2", N_UPSO_ADDR2);               //도로명주소 주소2
        sobj.setString("N_UPSO_ADDR3", N_UPSO_ADDR3);               //도로명주소 주소3
        sobj.setString("N_UPSO_BD_MNG_NUM", N_UPSO_BD_MNG_NUM);               //도로명주소 빌딩관리번호
        sobj.setString("N_UPSO_ZIP", N_UPSO_ZIP);               //도로명주소 우편번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$save_upso_juso
    //##**$$end
}
