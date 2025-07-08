
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r06
{
    public tac10_r06()
    {
    }
    //##**$$searchMst06
    /*
    */
    public DOBJ CTLsearchMst06(DOBJ dobj)
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
            dobj  = CALLsearchMst06_SEL1(Conn, dobj);           //  기타공제
            dobj  = CALLsearchMst06_SEL2(Conn, dobj);           //  작가협회
            dobj  = CALLsearchMst06_SEL3(Conn, dobj);           //  창작분과
            dobj  = CALLsearchMst06_SEL4(Conn, dobj);           //  작가연대
            dobj  = CALLsearchMst06_SEL5(Conn, dobj);           //  하모니
            dobj  = CALLsearchMst06_SEL6(Conn, dobj);           //  영화음악
            dobj  = CALLsearchMst06_SEL7(Conn, dobj);           //  별빛마을
            dobj  = CALLsearchMst06_SEL8(Conn, dobj);           //  대중음악인
            dobj  = CALLsearchMst06_SEL9(Conn, dobj);           //  여류작가
            dobj  = CALLsearchMst06_SEL10(Conn, dobj);           //  환수금
            dobj  = CALLsearchMst06_SEL11(Conn, dobj);           //  합   계
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
    public DOBJ CTLsearchMst06( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchMst06_SEL1(Conn, dobj);           //  기타공제
        dobj  = CALLsearchMst06_SEL2(Conn, dobj);           //  작가협회
        dobj  = CALLsearchMst06_SEL3(Conn, dobj);           //  창작분과
        dobj  = CALLsearchMst06_SEL4(Conn, dobj);           //  작가연대
        dobj  = CALLsearchMst06_SEL5(Conn, dobj);           //  하모니
        dobj  = CALLsearchMst06_SEL6(Conn, dobj);           //  영화음악
        dobj  = CALLsearchMst06_SEL7(Conn, dobj);           //  별빛마을
        dobj  = CALLsearchMst06_SEL8(Conn, dobj);           //  대중음악인
        dobj  = CALLsearchMst06_SEL9(Conn, dobj);           //  여류작가
        dobj  = CALLsearchMst06_SEL10(Conn, dobj);           //  환수금
        dobj  = CALLsearchMst06_SEL11(Conn, dobj);           //  합   계
        return dobj;
    }
    // 기타공제
    public DOBJ CALLsearchMst06_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '01'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '03' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 작가협회
    public DOBJ CALLsearchMst06_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '01' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 창작분과
    public DOBJ CALLsearchMst06_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '02' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 작가연대
    public DOBJ CALLsearchMst06_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '03' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 하모니
    public DOBJ CALLsearchMst06_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '04' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 영화음악
    public DOBJ CALLsearchMst06_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '05' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 별빛마을
    public DOBJ CALLsearchMst06_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '06' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 대중음악인
    public DOBJ CALLsearchMst06_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '07' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 여류작가
    public DOBJ CALLsearchMst06_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL9(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '08' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 환수금
    public DOBJ CALLsearchMst06_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '01'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '05' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 합   계
    public DOBJ CALLsearchMst06_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst06_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst06_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(DEDCT_AMT1)  AS  DEDCT_AMT1,  SUM(DEDCT_AMT2)  AS  DEDCT_AMT2,  SUM(DEDCT_AMT3)  AS  DEDCT_AMT3,  SUM(DEDCT_AMT4)  AS  DEDCT_AMT4,  SUM(DEDCT_AMT5)  AS  DEDCT_AMT5,  SUM(DEDCT_AMT6)  AS  DEDCT_AMT6,  SUM(DEDCT_AMT7)  AS  DEDCT_AMT7,  SUM(DEDCT_AMT8)  AS  DEDCT_AMT8,  SUM(DEDCT_AMT9)  AS  DEDCT_AMT9,  SUM(DEDCT_AMT10)	AS  DEDCT_AMT10  FROM  (   \n";
        query +=" SELECT  A.SUPP_YRMN,    NVL(DECODE(A.DEDCT_GBNONE,  '01',  DECODE(A.DEDCT_GBNTWO,  '06',  DEDCT_AMT)),0)  AS  DEDCT_AMT1,    NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '01',  DEDCT_AMT)),0)  AS  DEDCT_AMT2,    NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '02',  DEDCT_AMT)),0)  AS  DEDCT_AMT3,    NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '03',  DEDCT_AMT)),0)  AS  DEDCT_AMT4,    NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '04',  DEDCT_AMT)),0)  AS  DEDCT_AMT5,    NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '05',  DEDCT_AMT)),0)  AS  DEDCT_AMT6,    NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '06',  DEDCT_AMT)),0)  AS  DEDCT_AMT7,    NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '07',  DEDCT_AMT)),0)  AS  DEDCT_AMT8,    NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '08',  DEDCT_AMT)),0)  AS  DEDCT_AMT9,    NVL(DECODE(A.DEDCT_GBNONE,  '01',  DECODE(A.DEDCT_GBNTWO,  '05',  DEDCT_AMT)),0)  AS  DEDCT_AMT10    FROM  FIDU.TTAC_DEDCTAMT  A    WHERE  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)  )X  GROUP  BY  SUPP_YRMN ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$searchMst06
    //##**$$end
}
