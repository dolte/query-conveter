
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra08_s09_2
{
    public bra08_s09_2()
    {
    }
    //##**$$get_gov_danran
    /*
    */
    public DOBJ CTLget_gov_danran(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("OPEN"))
            {
                dobj  = CALLget_gov_danran_SEL1(Conn, dobj);           //  개업조회
                dobj  = CALLget_gov_danran_MRG6( dobj);        //  머지
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("CLOSE"))
            {
                dobj  = CALLget_gov_danran_SEL2(Conn, dobj);           //  폐업조회
                dobj  = CALLget_gov_danran_MRG6( dobj);        //  머지
            }
            else
            {
                dobj  = CALLget_gov_danran_SEL3(Conn, dobj);           //  전체조회
                dobj  = CALLget_gov_danran_MRG6( dobj);        //  머지
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
    public DOBJ CTLget_gov_danran( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("OPEN"))
        {
            dobj  = CALLget_gov_danran_SEL1(Conn, dobj);           //  개업조회
            dobj  = CALLget_gov_danran_MRG6( dobj);        //  머지
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("CLOSE"))
        {
            dobj  = CALLget_gov_danran_SEL2(Conn, dobj);           //  폐업조회
            dobj  = CALLget_gov_danran_MRG6( dobj);        //  머지
        }
        else
        {
            dobj  = CALLget_gov_danran_SEL3(Conn, dobj);           //  전체조회
            dobj  = CALLget_gov_danran_MRG6( dobj);        //  머지
        }
        return dobj;
    }
    // 개업조회
    public DOBJ CALLget_gov_danran_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_gov_danran_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_gov_danran_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   SI_GUN_GU = dobj.getRetObject("S").getRecord().get("SI_GUN_GU");   //시군구
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT ROW_NUM , 'OPEN' GBN , SUBSTR(APV_PERM_YMD,0,6) AS YRMN , BPLC_NM , NVL2(RDN_WHL_ADDR,RDN_WHL_ADDR,SITE_WHL_ADDR) RDN_WHL_ADDR , APV_PERM_YMD , FACIL_TOT_SCP , STAT_GBN , BIGO , INSPRES_ID , ADDR1  ";
        query +=" FROM GIBU.TGOV_DANRAN  ";
        query +=" WHERE SUBSTR(APV_PERM_YMD,0,6) BETWEEN :START_YRMN  ";
        query +=" AND :END_YRMN  ";
        query +=" AND DCB_YMD IS NULL  ";
        if( !SI_GUN_GU.equals("") )
        {
            query +=" AND (RDN_WHL_ADDR LIKE :SI_GUN_GU || '%'  ";
            query +=" OR SITE_WHL_ADDR LIKE :SI_GUN_GU || '%' )  ";
        }
        query +=" ORDER BY RDN_WHL_ADDR  ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        if(!SI_GUN_GU.equals(""))
        {
            sobj.setString("SI_GUN_GU", SI_GUN_GU);               //시군구
        }
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 머지
    public DOBJ CALLget_gov_danran_MRG6(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG6");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL2, SEL3","");
        rvobj.setName("MRG6") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 폐업조회
    public DOBJ CALLget_gov_danran_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_gov_danran_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_gov_danran_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   SI_GUN_GU = dobj.getRetObject("S").getRecord().get("SI_GUN_GU");   //시군구
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT ROW_NUM , 'CLOSE' GBN , SUBSTR(APV_PERM_YMD,0,6) AS YRMN , BPLC_NM , NVL2(RDN_WHL_ADDR,RDN_WHL_ADDR,SITE_WHL_ADDR) RDN_WHL_ADDR , APV_PERM_YMD , FACIL_TOT_SCP , STAT_GBN , BIGO , INSPRES_ID , ADDR1  ";
        query +=" FROM GIBU.TGOV_DANRAN  ";
        query +=" WHERE SUBSTR(APV_PERM_YMD,0,6) BETWEEN :START_YRMN  ";
        query +=" AND :END_YRMN  ";
        query +=" AND DCB_YMD IS NOT NULL  ";
        if( !SI_GUN_GU.equals("") )
        {
            query +=" AND (RDN_WHL_ADDR LIKE :SI_GUN_GU || '%'  ";
            query +=" OR SITE_WHL_ADDR LIKE :SI_GUN_GU || '%' )  ";
        }
        query +=" ORDER BY RDN_WHL_ADDR  ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        if(!SI_GUN_GU.equals(""))
        {
            sobj.setString("SI_GUN_GU", SI_GUN_GU);               //시군구
        }
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    // 전체조회
    // 개업과 폐업 조회
    public DOBJ CALLget_gov_danran_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_gov_danran_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_gov_danran_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   SI_GUN_GU = dobj.getRetObject("S").getRecord().get("SI_GUN_GU");   //시군구
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT ROW_NUM , NVL2(DCB_YMD,'CLOSE','OPEN') GBN , SUBSTR(APV_PERM_YMD,0,6) AS YRMN , BPLC_NM , NVL2(RDN_WHL_ADDR,RDN_WHL_ADDR,SITE_WHL_ADDR) RDN_WHL_ADDR , APV_PERM_YMD , FACIL_TOT_SCP , STAT_GBN , BIGO , INSPRES_ID , ADDR1  ";
        query +=" FROM GIBU.TGOV_DANRAN  ";
        query +=" WHERE SUBSTR(APV_PERM_YMD,0,6) BETWEEN :START_YRMN  ";
        query +=" AND :END_YRMN  ";
        if( !SI_GUN_GU.equals("") )
        {
            query +=" AND (RDN_WHL_ADDR LIKE :SI_GUN_GU || '%'  ";
            query +=" OR SITE_WHL_ADDR LIKE :SI_GUN_GU || '%' )  ";
        }
        query +=" ORDER BY RDN_WHL_ADDR  ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        if(!SI_GUN_GU.equals(""))
        {
            sobj.setString("SI_GUN_GU", SI_GUN_GU);               //시군구
        }
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    //##**$$get_gov_danran
    //##**$$save_stat_bigo_danran
    /*
    */
    public DOBJ CTLsave_stat_bigo_danran(DOBJ dobj)
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
            dobj  = CALLsave_stat_bigo_danran_MIUD1(Conn, dobj);           //  분기
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
    public DOBJ CTLsave_stat_bigo_danran( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsave_stat_bigo_danran_MIUD1(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLsave_stat_bigo_danran_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLsave_stat_bigo_danran_UPD5(Conn, dobj);           //  상태, 비고수
            }
        }
        return dobj;
    }
    // 상태, 비고수
    public DOBJ CALLsave_stat_bigo_danran_UPD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsave_stat_bigo_danran_UPD5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsave_stat_bigo_danran_UPD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   STAT_GBN = dvobj.getRecord().get("STAT_GBN");   //처리상태
        double   ROW_NUM = dvobj.getRecord().getDouble("ROW_NUM");   //ROW순번
        String   BIGO = dvobj.getRecord().get("BIGO");   //비고사항
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TGOV_DANRAN  \n";
        query +=" set INS_DATE=SYSDATE , INSPRES_ID=:INSPRES_ID , BIGO=:BIGO , STAT_GBN=:STAT_GBN  \n";
        query +=" where ROW_NUM=:ROW_NUM";
        sobj.setSql(query);
        sobj.setString("STAT_GBN", STAT_GBN);               //처리상태
        sobj.setDouble("ROW_NUM", ROW_NUM);               //ROW순번
        sobj.setString("BIGO", BIGO);               //비고사항
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    //##**$$save_stat_bigo_danran
    //##**$$end
}
