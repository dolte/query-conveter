
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s02
{
    public bra10_s02()
    {
    }
    //##**$$search_manper_juso
    /*
    */
    public DOBJ CTLsearch_manper_juso(DOBJ dobj)
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
                dobj  = CALLsearch_manper_juso_SEL1(Conn, dobj);           //  미매칭 목록
                dobj  = CALLsearch_manper_juso_MRG7( dobj);        //  결과용
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
            {
                dobj  = CALLsearch_manper_juso_SEL5(Conn, dobj);           //  매칭 목록
                dobj  = CALLsearch_manper_juso_MRG7( dobj);        //  결과용
            }
            else
            {
                dobj  = CALLsearch_manper_juso_SEL6(Conn, dobj);           //  전체 목록
                dobj  = CALLsearch_manper_juso_MRG7( dobj);        //  결과용
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
    public DOBJ CTLsearch_manper_juso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLsearch_manper_juso_SEL1(Conn, dobj);           //  미매칭 목록
            dobj  = CALLsearch_manper_juso_MRG7( dobj);        //  결과용
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
        {
            dobj  = CALLsearch_manper_juso_SEL5(Conn, dobj);           //  매칭 목록
            dobj  = CALLsearch_manper_juso_MRG7( dobj);        //  결과용
        }
        else
        {
            dobj  = CALLsearch_manper_juso_SEL6(Conn, dobj);           //  전체 목록
            dobj  = CALLsearch_manper_juso_MRG7( dobj);        //  결과용
        }
        return dobj;
    }
    // 미매칭 목록
    public DOBJ CALLsearch_manper_juso_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_manper_juso_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_manper_juso_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  X.UPSO_CD  ,  '경영'  AS  GBN2  ,  X.MNGEMSTR_ZIP  AS  ZIP  ,  X.MNGEMSTR_ADDR1  ||  '  '  ||  X.MNGEMSTR_ADDR2  AS  ADDR  ,  X.N_MNGEMSTR_ZIP  AS  N_ZIP  ,  X.N_MNGEMSTR_ADDR1  AS  N_ADDR1  ,  X.N_MNGEMSTR_ADDR2  AS  N_ADDR2  ,  X.N_MNGEMSTR_ADDR3  AS  N_ADDR3  ,  X.N_MNGEMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X,  GIBU.TBRA_UPSO  Y  WHERE  X.UPSO_CD  =  Y.UPSO_CD   \n";
        query +=" AND  X.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  X.STAFF_CD  =  NVL(:STAFF_CD,  X.STAFF_CD)   \n";
        query +=" AND  Y.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  X.MNGEMSTR_ADDR1  ||  X.MNGEMSTR_ADDR2  IS  NOT  NULL   \n";
        query +=" AND  X.N_MNGEMSTR_BD_MNG_NUM  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  X.UPSO_CD  ,  '허가'  AS  GBN2  ,  X.PERMMSTR_ZIP  AS  ZIP  ,  X.PERMMSTR_ADDR1  ||  '  '  ||  X.PERMMSTR_ADDR2  AS  ADDR  ,  X.N_PERMMSTR_ZIP  AS  N_ZIP  ,  X.N_PERMMSTR_ADDR1  AS  N_ADDR1  ,  X.N_PERMMSTR_ADDR2  AS  N_ADDR2  ,  X.N_PERMMSTR_ADDR3  AS  N_ADDR3  ,  X.N_PERMMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X,  GIBU.TBRA_UPSO  Y  WHERE  X.UPSO_CD  =  Y.UPSO_CD   \n";
        query +=" AND  X.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  X.STAFF_CD  =  NVL(:STAFF_CD,  X.STAFF_CD)   \n";
        query +=" AND  Y.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  X.MNGEMSTR_ADDR1  ||  X.MNGEMSTR_ADDR2  IS  NOT  NULL   \n";
        query +=" AND  X.N_PERMMSTR_BD_MNG_NUM  IS  NULL  ORDER  BY  UPSO_CD,  ZIP ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 결과용
    public DOBJ CALLsearch_manper_juso_MRG7(DOBJ dobj) throws Exception
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
    public DOBJ CALLsearch_manper_juso_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_manper_juso_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_manper_juso_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  X.UPSO_CD  ,  '경영'  AS  GBN2  ,  X.MNGEMSTR_ZIP  AS  ZIP  ,  X.MNGEMSTR_ADDR1  ||  '  '  ||  X.MNGEMSTR_ADDR2  AS  ADDR  ,  X.N_MNGEMSTR_ZIP  AS  N_ZIP  ,  X.N_MNGEMSTR_ADDR1  AS  N_ADDR1  ,  X.N_MNGEMSTR_ADDR2  AS  N_ADDR2  ,  X.N_MNGEMSTR_ADDR3  AS  N_ADDR3  ,  X.N_MNGEMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X,  GIBU.TBRA_UPSO  Y  WHERE  X.UPSO_CD  =  Y.UPSO_CD   \n";
        query +=" AND  X.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  X.STAFF_CD  =  NVL(:STAFF_CD,  X.STAFF_CD)   \n";
        query +=" AND  Y.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  X.MNGEMSTR_ADDR1  ||  X.MNGEMSTR_ADDR2  IS  NOT  NULL   \n";
        query +=" AND  X.N_MNGEMSTR_BD_MNG_NUM  IS  NOT  NULL  UNION  ALL   \n";
        query +=" SELECT  X.UPSO_CD  ,  '허가'  AS  GBN2  ,  X.PERMMSTR_ZIP  AS  ZIP  ,  X.PERMMSTR_ADDR1  ||  '  '  ||  X.PERMMSTR_ADDR2  AS  ADDR  ,  X.N_PERMMSTR_ZIP  AS  N_ZIP  ,  X.N_PERMMSTR_ADDR1  AS  N_ADDR1  ,  X.N_PERMMSTR_ADDR2  AS  N_ADDR2  ,  X.N_PERMMSTR_ADDR3  AS  N_ADDR3  ,  X.N_PERMMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X,  GIBU.TBRA_UPSO  Y  WHERE  X.UPSO_CD  =  Y.UPSO_CD   \n";
        query +=" AND  X.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  X.STAFF_CD  =  NVL(:STAFF_CD,  X.STAFF_CD)   \n";
        query +=" AND  Y.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  X.MNGEMSTR_ADDR1  ||  X.MNGEMSTR_ADDR2  IS  NOT  NULL   \n";
        query +=" AND  X.N_PERMMSTR_BD_MNG_NUM  IS  NOT  NULL  ORDER  BY  UPSO_CD,  ZIP ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 전체 목록
    public DOBJ CALLsearch_manper_juso_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_manper_juso_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_manper_juso_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  X.UPSO_CD  ,  '경영'  AS  GBN2  ,  X.MNGEMSTR_ZIP  AS  ZIP  ,  X.MNGEMSTR_ADDR1  ||  '  '  ||  X.MNGEMSTR_ADDR2  AS  ADDR  ,  X.N_MNGEMSTR_ZIP  AS  N_ZIP  ,  X.N_MNGEMSTR_ADDR1  AS  N_ADDR1  ,  X.N_MNGEMSTR_ADDR2  AS  N_ADDR2  ,  X.N_MNGEMSTR_ADDR3  AS  N_ADDR3  ,  X.N_MNGEMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X,  GIBU.TBRA_UPSO  Y  WHERE  X.UPSO_CD  =  Y.UPSO_CD   \n";
        query +=" AND  X.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  X.STAFF_CD  =  NVL(:STAFF_CD,  X.STAFF_CD)   \n";
        query +=" AND  Y.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  X.MNGEMSTR_ADDR1  ||  X.MNGEMSTR_ADDR2  IS  NOT  NULL  UNION  ALL   \n";
        query +=" SELECT  X.UPSO_CD  ,  '허가'  AS  GBN2  ,  X.PERMMSTR_ZIP  AS  ZIP  ,  X.PERMMSTR_ADDR1  ||  '  '  ||  X.PERMMSTR_ADDR2  AS  ADDR  ,  X.N_PERMMSTR_ZIP  AS  N_ZIP  ,  X.N_PERMMSTR_ADDR1  AS  N_ADDR1  ,  X.N_PERMMSTR_ADDR2  AS  N_ADDR2  ,  X.N_PERMMSTR_ADDR3  AS  N_ADDR3  ,  X.N_PERMMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X,  GIBU.TBRA_UPSO  Y  WHERE  X.UPSO_CD  =  Y.UPSO_CD   \n";
        query +=" AND  X.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  X.STAFF_CD  =  NVL(:STAFF_CD,  X.STAFF_CD)   \n";
        query +=" AND  Y.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  X.MNGEMSTR_ADDR1  ||  X.MNGEMSTR_ADDR2  IS  NOT  NULL  ORDER  BY  UPSO_CD,  ZIP ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$search_manper_juso
    //##**$$search_rel_juso
    /*
    */
    public DOBJ CTLsearch_rel_juso(DOBJ dobj)
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
            dobj  = CALLsearch_rel_juso_SEL1(Conn, dobj);           //  조회
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
    public DOBJ CTLsearch_rel_juso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_rel_juso_SEL1(Conn, dobj);           //  조회
        return dobj;
    }
    // 조회
    public DOBJ CALLsearch_rel_juso_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_rel_juso_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_rel_juso_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  X.UPSO_CD  ,  '업소'  AS  GBN2  ,  X.UPSO_ZIP  AS  ZIP  ,  X.UPSO_ADDR1  ||  '  '  ||  X.UPSO_ADDR2  AS  ADDR  ,  X.N_UPSO_ZIP  AS  N_ZIP  ,  X.N_UPSO_ADDR1  AS  N_ADDR1  ,  X.N_UPSO_ADDR2  AS  N_ADDR2  ,  X.N_UPSO_ADDR3  AS  N_ADDR3  ,  X.N_UPSO_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X  WHERE  X.UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  X.UPSO_CD  ,  '경영'  AS  GBN2  ,  X.MNGEMSTR_ZIP  AS  ZIP  ,  X.MNGEMSTR_ADDR1  ||  '  '  ||  X.MNGEMSTR_ADDR2  AS  ADDR  ,  X.N_MNGEMSTR_ZIP  AS  N_ZIP  ,  X.N_MNGEMSTR_ADDR1  AS  N_ADDR1  ,  X.N_MNGEMSTR_ADDR2  AS  N_ADDR2  ,  X.N_MNGEMSTR_ADDR3  AS  N_ADDR3  ,  X.N_MNGEMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X  WHERE  X.UPSO_CD  =  :UPSO_CD  UNION  ALL   \n";
        query +=" SELECT  X.UPSO_CD  ,  '허가'  AS  GBN2  ,  X.PERMMSTR_ZIP  AS  ZIP  ,  X.PERMMSTR_ADDR1  ||  '  '  ||  X.PERMMSTR_ADDR2  AS  ADDR  ,  X.N_PERMMSTR_ZIP  AS  N_ZIP  ,  X.N_PERMMSTR_ADDR1  AS  N_ADDR1  ,  X.N_PERMMSTR_ADDR2  AS  N_ADDR2  ,  X.N_PERMMSTR_ADDR3  AS  N_ADDR3  ,  X.N_PERMMSTR_BD_MNG_NUM  AS  N_BD_MNG_NUM  FROM  GIBU.EUNDORI_TRANS_JUSO2  X  WHERE  X.UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$search_rel_juso
    //##**$$save_manper_juso
    /*
    */
    public DOBJ CTLsave_manper_juso(DOBJ dobj)
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
            dobj  = CALLsave_manper_juso_MIUD4(Conn, dobj);           //  수정된 행만 저장하도록
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
    public DOBJ CTLsave_manper_juso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_manper_juso_MIUD4(Conn, dobj);           //  수정된 행만 저장하도록
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 수정된 행만 저장하도록
    public DOBJ CALLsave_manper_juso_MIUD4(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLsave_manper_juso_SEL11(Conn, dobj);           //  더미
                if( dobj.getRetObject("S").getRecord().get("GBN2").equals("경영"))
                {
                    dobj  = CALLsave_manper_juso_XIUD2(Conn, dobj);           //  저장하기
                }
                else
                {
                    dobj  = CALLsave_manper_juso_XIUD12(Conn, dobj);           //  저장하기
                }
            }
        }
        return dobj;
    }
    // 더미
    public DOBJ CALLsave_manper_juso_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsave_manper_juso_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_manper_juso_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SYSDATE  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 저장하기
    public DOBJ CALLsave_manper_juso_XIUD2(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLsave_manper_juso_XIUD2(dobj, dvobj);
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
    private SQLObject SQLsave_manper_juso_XIUD2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE  \n";
        query +=" GIBU.EUNDORI_TRANS_JUSO2  \n";
        query +=" SET N_MNGERSTR_ZIP = :N_ZIP , N_MNGERSTR_ADDR1 = :N_ADDR1 , N_MNGERSTR_ADDR2 = :N_ADDR2 , N_MNGERSTR_ADDR3 = :N_ADDR3 , N_MNGERSTR_BD_MNG_NUM = :N_BD_MNG_NUM  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 저장하기
    public DOBJ CALLsave_manper_juso_XIUD12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD12");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_manper_juso_XIUD12(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_manper_juso_XIUD12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE  \n";
        query +=" GIBU.EUNDORI_TRANS_JUSO2  \n";
        query +=" SET N_PERMMSTR_ZIP = :N_ZIP , N_PERMMSTR_ADDR1 = :N_ADDR1 , N_PERMMSTR_ADDR2 = :N_ADDR2 , N_PERMMSTR_ADDR3 = :N_ADDR3 , N_PERMMSTR_BD_MNG_NUM = :N_BD_MNG_NUM  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$save_manper_juso
    //##**$$end
}
