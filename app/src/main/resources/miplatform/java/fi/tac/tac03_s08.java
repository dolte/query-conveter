
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac03_s08
{
    public tac03_s08()
    {
    }
    //##**$$save_except
    /*
    */
    public DOBJ CTLsave_except(DOBJ dobj)
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
            dobj  = CALLsave_except_MIUD1(Conn, dobj);           //  MIUD
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
    public DOBJ CTLsave_except( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_except_MIUD1(Conn, dobj);           //  MIUD
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // MIUD
    public DOBJ CALLsave_except_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_except_INS5(Conn, dobj);           //  협회공제등록
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_except_UPD6(Conn, dobj);           //  협회공제 수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_except_DEL7(Conn, dobj);           //  협회공제 삭지
            }
        }
        return dobj;
    }
    // 협회공제 삭지
    public DOBJ CALLsave_except_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_except_DEL7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_except_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        String   EXCEPT_GBN = dvobj.getRecord().get("EXCEPT_GBN");   //협회공제구분
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_EXCEPTAMT  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and EXCEPT_GBN=:EXCEPT_GBN  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN  \n";
        query +=" and SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("EXCEPT_GBN", EXCEPT_GBN);               //협회공제구분
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    // 협회공제등록
    public DOBJ CALLsave_except_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_except_INS5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_except_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //등록자 ID
        String INS_DATE ="";        //등록 일시
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        String   REMAK_CTENT = dvobj.getRecord().get("REMAK_CTENT");   //비고 내용
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        double   OCC_AMT = dvobj.getRecord().getDouble("OCC_AMT");   //발생 금액
        String   EXCEPT_GBN = dvobj.getRecord().get("EXCEPT_GBN");   //협회공제구분
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_EXCEPTAMT (MB_CD, EXCEPT_GBN, OCC_AMT, TRNSF_GBN, REMAK_CTENT, SUPP_YRMN)  \n";
        query +=" values(:MB_CD , :EXCEPT_GBN , :OCC_AMT , :TRNSF_GBN , :REMAK_CTENT , :SUPP_YRMN )";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("REMAK_CTENT", REMAK_CTENT);               //비고 내용
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setDouble("OCC_AMT", OCC_AMT);               //발생 금액
        sobj.setString("EXCEPT_GBN", EXCEPT_GBN);               //협회공제구분
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    // 협회공제 수정
    public DOBJ CALLsave_except_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_except_UPD6(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_except_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");        //수정자 ID
        String MOD_DATE ="";        //수정 일시
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        String   REMAK_CTENT = dvobj.getRecord().get("REMAK_CTENT");   //비고 내용
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        double   OCC_AMT = dvobj.getRecord().getDouble("OCC_AMT");   //발생 금액
        String   EXCEPT_GBN = dvobj.getRecord().get("EXCEPT_GBN");   //협회공제구분
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_EXCEPTAMT  \n";
        query +=" set OCC_AMT=:OCC_AMT , REMAK_CTENT=:REMAK_CTENT  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and EXCEPT_GBN=:EXCEPT_GBN  \n";
        query +=" and TRNSF_GBN=:TRNSF_GBN  \n";
        query +=" and SUPP_YRMN=:SUPP_YRMN";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        sobj.setString("REMAK_CTENT", REMAK_CTENT);               //비고 내용
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setDouble("OCC_AMT", OCC_AMT);               //발생 금액
        sobj.setString("EXCEPT_GBN", EXCEPT_GBN);               //협회공제구분
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    //##**$$save_except
    //##**$$search_except
    /*
    */
    public DOBJ CTLsearch_except(DOBJ dobj)
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
            dobj  = CALLsearch_except_SEL1(Conn, dobj);           //  협회공제구분별조회
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
    public DOBJ CTLsearch_except( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearch_except_SEL1(Conn, dobj);           //  협회공제구분별조회
        return dobj;
    }
    // 협회공제구분별조회
    public DOBJ CALLsearch_except_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearch_except_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearch_except_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        String   EXCEPT_GBN = dvobj.getRecord().get("EXCEPT_GBN");   //협회공제구분
        String   SUPP_YRMN = dvobj.getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT SUPP_YRMN , A.MB_CD , B.HANMB_NM MB_NM, TRNSF_GBN, EXCEPT_GBN , OCC_AMT, A.REMAK_CTENT  ";
        query +=" FROM FIDU.TTAC_EXCEPTAMT A, FIDU.TMEM_MB B  ";
        query +=" WHERE SUPP_YRMN =:SUPP_YRMN  ";
        query +=" AND EXCEPT_GBN =:EXCEPT_GBN  ";
        query +=" AND A.MB_CD = B.MB_CD  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND (A.MB_CD =:MB_CD)  ";
        }
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        sobj.setString("EXCEPT_GBN", EXCEPT_GBN);               //협회공제구분
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$search_except
    //##**$$end
}
