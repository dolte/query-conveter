
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r16
{
    public tac10_r16()
    {
    }
    //##**$$searchMst16
    /* * 프로그램명 : tac10_r16
    * 작성자 : 성낙문
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchMst16(DOBJ dobj)
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
            dobj  = CALLsearchMst16_SEL1(Conn, dobj);           //  기타공제
            dobj  = CALLsearchMst16_SEL2(Conn, dobj);           //  작가협회
            dobj  = CALLsearchMst16_SEL3(Conn, dobj);           //  창작분과
            dobj  = CALLsearchMst16_SEL4(Conn, dobj);           //  작가연대
            dobj  = CALLsearchMst16_SEL5(Conn, dobj);           //  하모니
            dobj  = CALLsearchMst16_SEL6(Conn, dobj);           //  영화음악
            dobj  = CALLsearchMst16_SEL7(Conn, dobj);           //  별빛마을
            dobj  = CALLsearchMst16_SEL8(Conn, dobj);           //  뮤직엔젤
            dobj  = CALLsearchMst16_SEL9(Conn, dobj);           //  한국실용음악작곡가협회
            dobj  = CALLsearchMst16_SEL10(Conn, dobj);           //  환수금
            dobj  = CALLsearchMst16_SEL11(Conn, dobj);           //  합   계
            dobj  = CALLsearchMst16_SEL12(Conn, dobj);           //  협회추가공제(수수료미부과금)
            dobj  = CALLsearchMst16_SEL13(Conn, dobj);           //  하이노트
            dobj  = CALLsearchMst16_SEL14(Conn, dobj);           //  범작가포럼
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
    public DOBJ CTLsearchMst16( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchMst16_SEL1(Conn, dobj);           //  기타공제
        dobj  = CALLsearchMst16_SEL2(Conn, dobj);           //  작가협회
        dobj  = CALLsearchMst16_SEL3(Conn, dobj);           //  창작분과
        dobj  = CALLsearchMst16_SEL4(Conn, dobj);           //  작가연대
        dobj  = CALLsearchMst16_SEL5(Conn, dobj);           //  하모니
        dobj  = CALLsearchMst16_SEL6(Conn, dobj);           //  영화음악
        dobj  = CALLsearchMst16_SEL7(Conn, dobj);           //  별빛마을
        dobj  = CALLsearchMst16_SEL8(Conn, dobj);           //  뮤직엔젤
        dobj  = CALLsearchMst16_SEL9(Conn, dobj);           //  한국실용음악작곡가협회
        dobj  = CALLsearchMst16_SEL10(Conn, dobj);           //  환수금
        dobj  = CALLsearchMst16_SEL11(Conn, dobj);           //  합   계
        dobj  = CALLsearchMst16_SEL12(Conn, dobj);           //  협회추가공제(수수료미부과금)
        dobj  = CALLsearchMst16_SEL13(Conn, dobj);           //  하이노트
        dobj  = CALLsearchMst16_SEL14(Conn, dobj);           //  범작가포럼
        return dobj;
    }
    // 기타공제
    public DOBJ CALLsearchMst16_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst16_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst16_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '01'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '07' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 작가협회
    public DOBJ CALLsearchMst16_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst16_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst16_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '01'  ORDER  BY  B.HANMB_NM ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 창작분과
    public DOBJ CALLsearchMst16_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst16_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst16_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '02'  ORDER  BY  B.HANMB_NM ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 작가연대
    public DOBJ CALLsearchMst16_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst16_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst16_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '03'  ORDER  BY  B.HANMB_NM ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 하모니
    public DOBJ CALLsearchMst16_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst16_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        rvobj.Println("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst16_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  (  (  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '04'  )  )  ORDER  BY  B.HANMB_NM ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 영화음악
    public DOBJ CALLsearchMst16_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst16_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst16_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '05'  ORDER  BY  B.HANMB_NM ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 별빛마을
    public DOBJ CALLsearchMst16_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst16_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        rvobj.Println("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst16_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '06'  ORDER  BY  B.HANMB_NM ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 뮤직엔젤
    public DOBJ CALLsearchMst16_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst16_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        rvobj.Println("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst16_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '10'  ORDER  BY  B.HANMB_NM ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 한국실용음악작곡가협회
    public DOBJ CALLsearchMst16_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst16_SEL9(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        rvobj.Println("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst16_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '11'  ORDER  BY  B.HANMB_NM ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 환수금
    public DOBJ CALLsearchMst16_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst16_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.Println("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst16_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '01'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '05'  ORDER  BY  B.HANMB_NM ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 합   계
    public DOBJ CALLsearchMst16_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst16_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst16_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  SUM(DEDCT_AMT1)  AS  DEDCT_AMT1,  SUM(DEDCT_AMT2)  AS  DEDCT_AMT2,  SUM(DEDCT_AMT3)  AS  DEDCT_AMT3,  SUM(DEDCT_AMT4)  AS  DEDCT_AMT4,  SUM(DEDCT_AMT5)  AS  DEDCT_AMT5,  SUM(DEDCT_AMT6)  AS  DEDCT_AMT6,  SUM(DEDCT_AMT7)  AS  DEDCT_AMT7,  SUM(DEDCT_AMT8)  AS  DEDCT_AMT8,  SUM(DEDCT_AMT9)  AS  DEDCT_AMT9,  SUM(DEDCT_AMT10)  AS  DEDCT_AMT10,  SUM(DEDCT_AMT11)  AS  DEDCT_AMT11,  SUM(DEDCT_AMT12)  AS  DEDCT_AMT12,  SUM(DEDCT_AMT13)  AS  DEDCT_AMT13  FROM  (   \n";
        query +=" SELECT  A.SUPP_YRMN,  NVL(DECODE(A.DEDCT_GBNONE,  '01',  DECODE(A.DEDCT_GBNTWO,  '07',  DEDCT_AMT)),0)  AS  DEDCT_AMT1,  NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '01',  DEDCT_AMT)),0)  AS  DEDCT_AMT2,  NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '02',  DEDCT_AMT)),0)  AS  DEDCT_AMT3,  NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '03',  DEDCT_AMT)),0)  AS  DEDCT_AMT4,  NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '04',  DEDCT_AMT)),0)  AS  DEDCT_AMT5,  NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '05',  DEDCT_AMT)),0)  AS  DEDCT_AMT6,  NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '07',  DEDCT_AMT)),0)  AS  DEDCT_AMT7,  NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '06',  DEDCT_AMT)),0)  AS  DEDCT_AMT8,  NVL(DECODE(A.DEDCT_GBNONE,  '01',  DECODE(A.DEDCT_GBNTWO,  '05',  DEDCT_AMT)),0)  AS  DEDCT_AMT9,  NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '10',  DEDCT_AMT)),0)  AS  DEDCT_AMT10,  NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '09',  DEDCT_AMT)),0)  AS  DEDCT_AMT12,  NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '11',  DEDCT_AMT)),0)  AS  DEDCT_AMT11,  NVL(DECODE(A.DEDCT_GBNONE,  '02',  DECODE(A.DEDCT_GBNTWO,  '12',  DEDCT_AMT)),0)  AS  DEDCT_AMT13  FROM  FIDU.TTAC_DEDCTAMT  A  WHERE  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)  )X  GROUP  BY  SUPP_YRMN ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 협회추가공제(수수료미부과금)
    public DOBJ CALLsearchMst16_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst16_SEL12(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst16_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '01'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '08' ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 하이노트
    public DOBJ CALLsearchMst16_SEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL13");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst16_SEL13(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst16_SEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '09'  ORDER  BY  B.HANMB_NM ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 범작가포럼
    public DOBJ CALLsearchMst16_SEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL14");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL14");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst16_SEL14(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst16_SEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.HANMB_NM,  A.DEDCT_AMT  FROM  FIDU.TTAC_DEDCTAMT  A,  FIDU.TMEM_MB  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  A.DEDCT_GBNTWO  =  '12'  ORDER  BY  B.HANMB_NM ";
        sobj.setSql(query);
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$searchMst16
    //##**$$searchMst_16
    /* * 프로그램명 : tac10_r16
    * 작성자 : 성낙문
    * 작성일 : 2009/12/01
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchMst_16(DOBJ dobj)
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
            dobj  = CALLsearchMst_16_SEL1(Conn, dobj);           //  매체별 저작권사용료 분배내역서
            dobj  = CALLsearchMst_16_SEL2(Conn, dobj);           //  채권 채무
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
    public DOBJ CTLsearchMst_16( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchMst_16_SEL1(Conn, dobj);           //  매체별 저작권사용료 분배내역서
        dobj  = CALLsearchMst_16_SEL2(Conn, dobj);           //  채권 채무
        return dobj;
    }
    // 매체별 저작권사용료 분배내역서
    public DOBJ CALLsearchMst_16_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_16_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_16_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  C.MB_CD,  C.HANMB_NM,  C.SN,  C.INS_NUM,  C.BANK_NM,  C.SUPPACCN_NUM,  C.CODE_NM,  C.DISTR_AMT1  AS  DISTR_AMT1,  C.DISTR_AMT2  AS  DISTR_AMT2,  C.DISTR_AMT3  AS  DISTR_AMT3,  C.DISTR_AMT4  AS  DISTR_AMT4,  C.DISTR_AMT5  AS  DISTR_AMT5,  C.DISTR_AMT6  AS  DISTR_AMT6,  C.DISTR_AMT7  AS  DISTR_AMT7,  C.DISTR_AMT8  AS  DISTR_AMT8,  C.DISTR_AMT9  AS  DISTR_AMT9,  C.DISTR_AMT10  AS  DISTR_AMT10,  C.DISTR_AMT11  AS  DISTR_AMT11,  C.DISTR_AMT12  AS  DISTR_AMT12,  C.DISTR_AMT13  AS  DISTR_AMT13,  C.DISTR_AMT14  AS  DISTR_AMT14,  C.DISTR_AMT15  AS  DISTR_AMT15,  C.DISTR_AMT16  AS  DISTR_AMT16,  C.DISTR_AMT17  AS  DISTR_AMT17,  C.DISTR_AMT18  AS  DISTR_AMT18,  C.DISTR_AMT19  AS  DISTR_AMT19,  C.DISTR_AMT20  AS  DISTR_AMT20,  C.DISTR_AMT21  AS  DISTR_AMT21,  C.DISTR_AMT22  AS  DISTR_AMT22,  C.DISTR_AMT23  AS  DISTR_AMT23,  C.DISTR_AMT24  AS  DISTR_AMT24,  C.DISTR_AMT25  AS  DISTR_AMT25,  C.DISTR_AMT26  AS  DISTR_AMT26,  C.DISTR_AMT27  AS  DISTR_AMT27,  C.DISTR_TOT  AS  DISTR_TOT,  C.CNT,  D.DISTR_AMT28  AS  DISTR_AMT28,  D.DISTR_AMT29  AS  DISTR_AMT29,  D.DISTR_AMT30  AS  DISTR_AMT30,  C.ATAX_AMT  AS  DISTR_AMT31,  D.DISTR_AMT32  AS  DISTR_AMT32,  D.DISTR_AMT33  AS  DISTR_AMT33,  D.DISTR_AMT34  AS  DISTR_AMT34,  D.DISTR_AMT35  AS  DISTR_AMT35,  D.DISTR_AMT36  AS  DISTR_AMT36,  D.DISTR_AMT37  AS  DISTR_AMT37,  D.DISTR_AMT38  AS  DISTR_AMT38,  D.DISTR_AMT39  AS  DISTR_AMT39,  D.DISTR_AMT40  AS  DISTR_AMT40,  D.DISTR_AMT41  AS  DISTR_AMT41,  D.DISTR_AMT42  AS  DISTR_AMT42,  D.DISTR_AMT43  AS  DISTR_AMT43,  D.DISTR_AMT44  AS  DISTR_AMT44,  D.DISTR_AMT45  AS  DISTR_AMT45,  D.DISTR_AMT46  AS  DISTR_AMT46,  D.DISTR_GTOT,  (nvl(C.DISTR_TOT,0)  -  nvl(D.DISTR_GTOT,0)  +  C.ST_ATAX_AMT)  AS  DISTR_STOT,  E.DISTR_TAMT1  AS  DISTR_TAMT1  ,  E.DISTR_TAMT2  AS  DISTR_TAMT2  ,  E.DISTR_TAMT3  AS  DISTR_TAMT3  ,  E.DISTR_TAMT4  AS  DISTR_TAMT4  ,  E.DISTR_TAMT5  AS  DISTR_TAMT5  ,  E.DISTR_TAMT6  AS  DISTR_TAMT6  ,  E.DISTR_TAMT7  AS  DISTR_TAMT7  ,  E.DISTR_TAMT8  AS  DISTR_TAMT8  ,  E.DISTR_TAMT9  AS  DISTR_TAMT9  ,  E.DISTR_TAMT10  AS  DISTR_TAMT10,  E.DISTR_TAMT11  AS  DISTR_TAMT11,  E.DISTR_TAMT12  AS  DISTR_TAMT12,  E.DISTR_TAMT13  AS  DISTR_TAMT13,  E.DISTR_TAMT14  AS  DISTR_TAMT14,  E.DISTR_TAMT15  AS  DISTR_TAMT15,  E.DISTR_TAMT16  AS  DISTR_TAMT16,  E.DISTR_TAMT17  AS  DISTR_TAMT17,  E.DISTR_TAMT18  AS  DISTR_TAMT18,  E.DISTR_TAMT19  AS  DISTR_TAMT19,  E.DISTR_TAMT20  AS  DISTR_TAMT20,  E.DISTR_TAMT21  AS  DISTR_TAMT21,  E.DISTR_TAMT22  AS  DISTR_TAMT22,  E.DISTR_TAMT23  AS  DISTR_TAMT23,  E.DISTR_TAMT24  AS  DISTR_TAMT24,  E.DISTR_TAMT25  AS  DISTR_TAMT25,  E.DISTR_TAMT26  AS  DISTR_TAMT26,  E.DISTR_TAMT27  AS  DISTR_TAMT27,  F.DISTR_TAMT28  AS  DISTR_TAMT28,  F.DISTR_TAMT29  AS  DISTR_TAMT29,  F.DISTR_TAMT30  AS  DISTR_TAMT30,  F.DISTR_TAMT31  AS  DISTR_TAMT31,  F.DISTR_TAMT32  AS  DISTR_TAMT32,  F.DISTR_TAMT33  AS  DISTR_TAMT33,  F.DISTR_TAMT34  AS  DISTR_TAMT34,  F.DISTR_TAMT35  AS  DISTR_TAMT35,  F.DISTR_TAMT36  AS  DISTR_TAMT36,  F.DISTR_TAMT37  AS  DISTR_TAMT37,  F.DISTR_TAMT38  AS  DISTR_TAMT38,  F.DISTR_TAMT39  AS  DISTR_TAMT39,  F.DISTR_TAMT40  AS  DISTR_TAMT40,  F.DISTR_TAMT41  AS  DISTR_TAMT41,  F.DISTR_TAMT42  AS  DISTR_TAMT42,  F.DISTR_TAMT43  AS  DISTR_TAMT43,  F.DISTR_TAMT44  AS  DISTR_TAMT44,  F.DISTR_TAMT45  AS  DISTR_TAMT45,  F.DISTR_TAMT46  AS  DISTR_TAMT46  FROM  (   \n";
        query +=" SELECT  Z.MB_CD,  Z.TRNSF_GBN  ,  Z.HANMB_NM,  Z.SN,  Z.INS_NUM,  MAX(Z.BANK_NM)  AS  BANK_NM,  Z.SUPPACCN_NUM,  Z.CODE_NM,  SUM(Z.DISTR_AMT1)  AS  DISTR_AMT1,  SUM(Z.DISTR_AMT2)  AS  DISTR_AMT2,  SUM(Z.DISTR_AMT3)  AS  DISTR_AMT3,  SUM(Z.DISTR_AMT4)  AS  DISTR_AMT4,  SUM(Z.DISTR_AMT5)  AS  DISTR_AMT5,  SUM(Z.DISTR_AMT6)  AS  DISTR_AMT6,  SUM(Z.DISTR_AMT7)  AS  DISTR_AMT7,  SUM(Z.DISTR_AMT8)  AS  DISTR_AMT8,  SUM(Z.DISTR_AMT9)  AS  DISTR_AMT9,  SUM(Z.DISTR_AMT10)  AS  DISTR_AMT10,  SUM(Z.DISTR_AMT11)  AS  DISTR_AMT11,  SUM(Z.DISTR_AMT12)  AS  DISTR_AMT12,  SUM(Z.DISTR_AMT13)  AS  DISTR_AMT13,  SUM(Z.DISTR_AMT14)  AS  DISTR_AMT14,  SUM(Z.DISTR_AMT15)  AS  DISTR_AMT15,  SUM(Z.DISTR_AMT16)  AS  DISTR_AMT16,  SUM(Z.DISTR_AMT17)  AS  DISTR_AMT17,  SUM(Z.DISTR_AMT18)  AS  DISTR_AMT18,  SUM(Z.DISTR_AMT19)  AS  DISTR_AMT19,  SUM(Z.DISTR_AMT20)  AS  DISTR_AMT20,  SUM(Z.DISTR_AMT21)  AS  DISTR_AMT21,  SUM(Z.DISTR_AMT22)  AS  DISTR_AMT22,  SUM(Z.DISTR_AMT23)  AS  DISTR_AMT23,  SUM(Z.DISTR_AMT24)  AS  DISTR_AMT24,  SUM(Z.DISTR_AMT25)  AS  DISTR_AMT25,  SUM(Z.DISTR_AMT26)  AS  DISTR_AMT26,  SUM(Z.DISTR_AMT27)  AS  DISTR_AMT27,  SUM(Z.DISTR_AMT1)  +  SUM(Z.DISTR_AMT2)  +  SUM(Z.DISTR_AMT3)  +  SUM(Z.DISTR_AMT4)  +  SUM(Z.DISTR_AMT5)  +  SUM(Z.DISTR_AMT6)  +  SUM(Z.DISTR_AMT7)  +  SUM(Z.DISTR_AMT8)  +  SUM(Z.DISTR_AMT9)  +  SUM(Z.DISTR_AMT10)+  SUM(Z.DISTR_AMT11)+  SUM(Z.DISTR_AMT12)+  SUM(Z.DISTR_AMT13)+  SUM(Z.DISTR_AMT14)+  SUM(Z.DISTR_AMT15)+  SUM(Z.DISTR_AMT16)+  SUM(Z.DISTR_AMT17)+  SUM(Z.DISTR_AMT18)+  SUM(Z.DISTR_AMT19)+  SUM(Z.DISTR_AMT20)+  SUM(Z.DISTR_AMT21)+  SUM(Z.DISTR_AMT22)+  SUM(Z.DISTR_AMT23)+  SUM(Z.DISTR_AMT24)+  SUM(Z.DISTR_AMT25)+  SUM(Z.DISTR_AMT26)+  SUM(Z.DISTR_AMT27)  AS  DISTR_TOT,  SUM(Z.ATAX_AMT)  AS  ATAX_AMT,  SUM(Z.ST_ATAX_AMT)  AS  ST_ATAX_AMT,  MAX(Z.CNT)  CNT  FROM  (   \n";
        query +=" SELECT  A.MB_CD,  C.HANMB_NM,  (   \n";
        query +=" SELECT  SN  FROM  FIDU.TMEM_SN  WHERE  MB_CD  =  A.MB_CD   \n";
        query +=" AND  SN_MNG_NUM  =  '03'  )  AS  SN,  C.INS_NUM,  MAX(H.BANK_NM)  AS  BANK_NM,  C.SUPPACCN_NUM,  A.TRNSF_GBN,  (   \n";
        query +=" SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00294'   \n";
        query +=" AND  CODE_CD  =  A.TRNSF_GBN  )  AS  CODE_NM,  DECODE(B.PRNT_SEQ,  '1',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '2',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '3',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '4',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '5',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '6',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '7',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '8',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '9',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT9,  DECODE(B.PRNT_SEQ,  '10',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT10,  DECODE(B.PRNT_SEQ,  '11',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT11,  DECODE(B.PRNT_SEQ,  '12',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT12,  DECODE(B.PRNT_SEQ,  '13',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT13,  DECODE(B.PRNT_SEQ,  '14',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT14,  DECODE(B.PRNT_SEQ,  '15',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT15,  DECODE(B.PRNT_SEQ,  '16',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT16,  DECODE(B.PRNT_SEQ,  '17',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT17,  DECODE(B.PRNT_SEQ,  '18',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT18,  DECODE(B.PRNT_SEQ,  '19',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT19,  DECODE(B.PRNT_SEQ,  '20',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT20,  DECODE(B.PRNT_SEQ,  '21',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT21,  DECODE(B.PRNT_SEQ,  '22',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT22,  DECODE(B.PRNT_SEQ,  '23',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT23,  DECODE(B.PRNT_SEQ,  '24',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT24,  DECODE(B.PRNT_SEQ,  '25',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT25,  DECODE(B.PRNT_SEQ,  '26',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT26,  DECODE(B.PRNT_SEQ,  '27',  SUM(A.ORGDISTR_AMT),0)  DISTR_AMT27,  SUM(A.ATAX_AMT)  AS  ATAX_AMT,  SUM(CASE  WHEN  C.MB_GBN1=  'F'   \n";
        query +=" AND  FIDU.GET_FOR_ATAX_YN(MDM_CD)  <>  '1'  THEN  ATAX_AMT  ELSE  0  END)  ST_ATAX_AMT,  NVL(  (   \n";
        query +=" SELECT  CASE  WHEN  COUNT(F.MB_CD)  >  0  THEN  'Y'  ELSE  'N'END  FROM  FIDU.TMEM_CLAIMDEBT  F,  FIDU.TMEM_CLAIMDEBTREPAY  G  WHERE  A.MB_CD  =  F.MB_CD   \n";
        query +=" AND  F.CLAIMPRES_MB_CD  =  G.MB_CD(+)   \n";
        query +=" AND  F.MNG_NUM  =  G.MNG_NUM(+)  ),  'N'  )  AS  CNT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B,  FIDU.TMEM_MB  C,  ACCT.TCAC_BANK  H  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  A.MB_CD  =  C.MB_CD   \n";
        query +=" AND  C.SUPPBANK_CD  =  H.BANK_CD(+)   \n";
        query +=" AND  A.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||  '%'  GROUP  BY  A.MB_CD,  C.HANMB_NM,  A.TRNSF_GBN,  C.INS_NUM,  C.SUPPACCN_NUM,  B.PRNT_SEQ,  A.SUPP_YRMN  )Z  GROUP  BY  Z.MB_CD,  Z.HANMB_NM,  Z.SN,  Z.INS_NUM,  Z.SUPPACCN_NUM,  Z.CODE_NM  ,  Z.TRNSF_GBN  )C,  (   \n";
        query +=" SELECT  X.MB_CD,  X.TRNSF_GBN,  SUM(X.DISTR_AMT28)  AS  DISTR_AMT28,  SUM(X.DISTR_AMT29)  AS  DISTR_AMT29,  SUM(X.DISTR_AMT30)  AS  DISTR_AMT30,  SUM(X.DISTR_AMT31)  AS  DISTR_AMT31,  SUM(X.DISTR_AMT32)  AS  DISTR_AMT32,  SUM(X.DISTR_AMT33)  AS  DISTR_AMT33,  SUM(X.DISTR_AMT34)  AS  DISTR_AMT34,  SUM(X.DISTR_AMT35)  AS  DISTR_AMT35,  SUM(X.DISTR_AMT36)  AS  DISTR_AMT36,  SUM(X.DISTR_AMT37)  AS  DISTR_AMT37,  SUM(X.DISTR_AMT38)  AS  DISTR_AMT38,  SUM(X.DISTR_AMT39)  AS  DISTR_AMT39,  SUM(X.DISTR_AMT40)  AS  DISTR_AMT40,  SUM(X.DISTR_AMT41)  AS  DISTR_AMT41,  SUM(X.DISTR_AMT42)  AS  DISTR_AMT42,  SUM(X.DISTR_AMT43)  AS  DISTR_AMT43,  SUM(X.DISTR_AMT44)  AS  DISTR_AMT44,  SUM(X.DISTR_AMT45)  AS  DISTR_AMT45,  SUM(X.DISTR_AMT46)  AS  DISTR_AMT46,  SUM(X.DISTR_AMT28)+  SUM(X.DISTR_AMT29)+  SUM(X.DISTR_AMT30)+  SUM(X.DISTR_AMT32)+  SUM(X.DISTR_AMT33)+  SUM(X.DISTR_AMT34)+  SUM(X.DISTR_AMT35)+  SUM(X.DISTR_AMT36)+  SUM(X.DISTR_AMT37)+  SUM(X.DISTR_AMT38)+  SUM(X.DISTR_AMT39)+  SUM(X.DISTR_AMT40)+  SUM(X.DISTR_AMT41)+  SUM(X.DISTR_AMT42)+  SUM(X.DISTR_AMT43)+  SUM(X.DISTR_AMT44)+  SUM(X.DISTR_AMT45)+  SUM(X.DISTR_AMT46)  AS  DISTR_GTOT  FROM  (   \n";
        query +=" SELECT  D.MB_CD,  D.TRNSF_GBN,  DECODE(D.DEDCT_GBNONE,  '01',  DECODE(D.DEDCT_GBNTWO,  '01',  DEDCT_AMT,0),0)  DISTR_AMT28,  DECODE(D.DEDCT_GBNONE,  '01',  DECODE(D.DEDCT_GBNTWO,  '02',  DEDCT_AMT,0),0)  DISTR_AMT29,  DECODE(D.DEDCT_GBNONE,  '01',  DECODE(D.DEDCT_GBNTWO,  '03',  DEDCT_AMT,0),0)  DISTR_AMT30,  0  AS  DISTR_AMT31,  DECODE(D.DEDCT_GBNONE,  '01',  DECODE(D.DEDCT_GBNTWO,  '04',  DEDCT_AMT,0),0)  DISTR_AMT32,  DECODE(D.DEDCT_GBNONE,  '01',  DECODE(D.DEDCT_GBNTWO,  '05',  DEDCT_AMT,0),0)  DISTR_AMT33,  DECODE(D.DEDCT_GBNONE,  '01',  DECODE(D.DEDCT_GBNTWO,  '06',  DEDCT_AMT,0),0)  DISTR_AMT34,  DECODE(D.DEDCT_GBNONE,  '01',  DECODE(D.DEDCT_GBNTWO,  '07',  DEDCT_AMT,0),0)  DISTR_AMT35,  DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '01',  DEDCT_AMT,0),0)  DISTR_AMT36,  DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '02',  DEDCT_AMT,0),0)  DISTR_AMT37,  DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '03',  DEDCT_AMT,0),0)  DISTR_AMT38,  DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '04',  DEDCT_AMT,0),0)  DISTR_AMT39,  DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '05',  DEDCT_AMT,0),0)  DISTR_AMT40,  DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '06',  DEDCT_AMT,0),0)  DISTR_AMT41,  DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '10',  DEDCT_AMT,0),0)  DISTR_AMT42,  DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '11',  DEDCT_AMT,0),0)  DISTR_AMT43,  DECODE(D.DEDCT_GBNONE,  '01',  DECODE(D.DEDCT_GBNTWO,  '08',  DEDCT_AMT,0),0)  DISTR_AMT44,  DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '09',  DEDCT_AMT,0),0)  DISTR_AMT45,  DECODE(D.DEDCT_GBNONE,  '02',  DECODE(D.DEDCT_GBNTWO,  '12',  DEDCT_AMT,0),0)  DISTR_AMT46  FROM  FIDU.TTAC_DEDCTAMT  D,  FIDU.TMEM_MB  E  WHERE  D.SUPP_YRMN  =  SUBSTR(:SUPP_YRMN,1,6)   \n";
        query +=" AND  D.MB_CD  =  E.MB_CD   \n";
        query +=" AND  D.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'   \n";
        query +=" AND  D.MB_CD  LIKE  :MB_CD  ||  '%'  )X  GROUP  BY  X.MB_CD,  X.TRNSF_GBN  )D,  (   \n";
        query +=" SELECT  MAX(X.DISTR_TAMT1  )  DISTR_TAMT1  ,  MAX(X.DISTR_TAMT2  )  DISTR_TAMT2  ,  MAX(X.DISTR_TAMT3  )  DISTR_TAMT3  ,  MAX(X.DISTR_TAMT4  )  DISTR_TAMT4  ,  MAX(X.DISTR_TAMT5  )  DISTR_TAMT5  ,  MAX(X.DISTR_TAMT6  )  DISTR_TAMT6  ,  MAX(X.DISTR_TAMT7  )  DISTR_TAMT7  ,  MAX(X.DISTR_TAMT8  )  DISTR_TAMT8  ,  MAX(X.DISTR_TAMT9  )  DISTR_TAMT9  ,  MAX(X.DISTR_TAMT10)  DISTR_TAMT10,  MAX(X.DISTR_TAMT11)  DISTR_TAMT11,  MAX(X.DISTR_TAMT12)  DISTR_TAMT12,  MAX(X.DISTR_TAMT13)  DISTR_TAMT13,  MAX(X.DISTR_TAMT14)  DISTR_TAMT14,  MAX(X.DISTR_TAMT15)  DISTR_TAMT15,  MAX(X.DISTR_TAMT16)  DISTR_TAMT16,  MAX(X.DISTR_TAMT17)  DISTR_TAMT17,  MAX(X.DISTR_TAMT18)  DISTR_TAMT18,  MAX(X.DISTR_TAMT19)  DISTR_TAMT19,  MAX(X.DISTR_TAMT20)  DISTR_TAMT20,  MAX(X.DISTR_TAMT21)  DISTR_TAMT21,  MAX(X.DISTR_TAMT22)  DISTR_TAMT22,  MAX(X.DISTR_TAMT23)  DISTR_TAMT23,  MAX(X.DISTR_TAMT24)  DISTR_TAMT24,  MAX(X.DISTR_TAMT25)  DISTR_TAMT25,  MAX(X.DISTR_TAMT26)  DISTR_TAMT26,  MAX(X.DISTR_TAMT27)  DISTR_TAMT27  FROM  (   \n";
        query +=" SELECT  NVL(  DECODE(PRNT_SEQ,  '1',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT1,  NVL(  DECODE(PRNT_SEQ,  '2',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT2,  NVL(  DECODE(PRNT_SEQ,  '3',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT3,  NVL(  DECODE(PRNT_SEQ,  '4',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT4,  NVL(  DECODE(PRNT_SEQ,  '5',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT5,  NVL(  DECODE(PRNT_SEQ,  '6',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT6,  NVL(  DECODE(PRNT_SEQ,  '7',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT7,  NVL(  DECODE(PRNT_SEQ,  '8',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT8,  NVL(  DECODE(PRNT_SEQ,  '9',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT9,  NVL(  DECODE(PRNT_SEQ,  '10',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT10,  NVL(  DECODE(PRNT_SEQ,  '11',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT11,  NVL(  DECODE(PRNT_SEQ,  '12',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT12,  NVL(  DECODE(PRNT_SEQ,  '13',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT13,  NVL(  DECODE(PRNT_SEQ,  '14',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT14,  NVL(  DECODE(PRNT_SEQ,  '15',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT15,  NVL(  DECODE(PRNT_SEQ,  '16',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT16,  NVL(  DECODE(PRNT_SEQ,  '17',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT17,  NVL(  DECODE(PRNT_SEQ,  '18',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT18,  NVL(  DECODE(PRNT_SEQ,  '19',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT19,  NVL(  DECODE(PRNT_SEQ,  '20',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT20,  NVL(  DECODE(PRNT_SEQ,  '21',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT21,  NVL(  DECODE(PRNT_SEQ,  '22',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT22,  NVL(  DECODE(PRNT_SEQ,  '23',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT23,  NVL(  DECODE(PRNT_SEQ,  '24',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT24,  NVL(  DECODE(PRNT_SEQ,  '25',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT25,  NVL(  DECODE(PRNT_SEQ,  '26',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT26,  NVL(  DECODE(PRNT_SEQ,  '27',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT27  FROM  FIDU.TENV_AVECLASSCD  )  X  )  E,  (   \n";
        query +=" SELECT  MAX(DISTR_TAMT28)  DISTR_TAMT28,  MAX(DISTR_TAMT29)  DISTR_TAMT29,  MAX(DISTR_TAMT30)  DISTR_TAMT30,  MAX(DISTR_TAMT31)  DISTR_TAMT31,  MAX(DISTR_TAMT32)  DISTR_TAMT32,  MAX(DISTR_TAMT33)  DISTR_TAMT33,  MAX(DISTR_TAMT34)  DISTR_TAMT34,  MAX(DISTR_TAMT35)  DISTR_TAMT35,  MAX(DISTR_TAMT36)  DISTR_TAMT36,  MAX(DISTR_TAMT37)  DISTR_TAMT37,  MAX(DISTR_TAMT38)  DISTR_TAMT38,  MAX(DISTR_TAMT39)  DISTR_TAMT39,  MAX(DISTR_TAMT40)  DISTR_TAMT40,  MAX(DISTR_TAMT41)  DISTR_TAMT41,  MAX(DISTR_TAMT42)  DISTR_TAMT42,  MAX(DISTR_TAMT43)  DISTR_TAMT43,  MAX(DISTR_TAMT44)  DISTR_TAMT44,  MAX(DISTR_TAMT45)  DISTR_TAMT45,  MAX(DISTR_TAMT46)  DISTR_TAMT46  FROM  (   \n";
        query +=" SELECT  '1'  AS  RW  ,  A.HIGH_CD  HIGH_CD,  B.HIGH_CD  HIGH_CD2,  DECODE(A.CODE_CD,  '01',  A.CODE_NM)  DISTR_TAMT28,  DECODE(A.CODE_CD,  '02',  A.CODE_NM)  DISTR_TAMT29,  DECODE(A.CODE_CD,  '03',  A.CODE_NM)  DISTR_TAMT30,  '부가가치세'  DISTR_TAMT31,  DECODE(A.CODE_CD,  '04',  A.CODE_NM)  DISTR_TAMT32,  DECODE(A.CODE_CD,  '05',  A.CODE_NM)  DISTR_TAMT33,  DECODE(A.CODE_CD,  '06',  A.CODE_NM)  DISTR_TAMT34,  DECODE(A.CODE_CD,  '07',  A.CODE_NM)  DISTR_TAMT35,  DECODE(B.CODE_CD,  '01',  B.CODE_NM)  DISTR_TAMT36,  DECODE(B.CODE_CD,  '02',  B.CODE_NM)  DISTR_TAMT37,  DECODE(B.CODE_CD,  '03',  B.CODE_NM)  DISTR_TAMT38,  DECODE(B.CODE_CD,  '04',  B.CODE_NM)  DISTR_TAMT39,  DECODE(B.CODE_CD,  '05',  B.CODE_NM)  DISTR_TAMT40,  DECODE(B.CODE_CD,  '06',  B.CODE_NM)  DISTR_TAMT41,  DECODE(B.CODE_CD,  '10',  B.CODE_NM)  DISTR_TAMT42,  DECODE(B.CODE_CD,  '11',  B.CODE_NM)  DISTR_TAMT43,  DECODE(A.CODE_CD,  '08',  A.CODE_NM)  DISTR_TAMT44,  DECODE(B.CODE_CD,  '09',  B.CODE_NM)  DISTR_TAMT45,  DECODE(B.CODE_CD,  '12',  B.CODE_NM)  DISTR_TAMT46  FROM  FIDU.TENV_CODE  A,  FIDU.TENV_CODE  B  WHERE  A.HIGH_CD  =  '00254'   \n";
        query +=" AND  B.HIGH_CD  =  '00040'  )X  GROUP  BY  HIGH_CD,  HIGH_CD2  )F  WHERE  C.MB_CD  =  D.MB_CD(+)   \n";
        query +=" AND  C.TRNSF_GBN  =  D.TRNSF_GBN(+)  ORDER  BY  C.MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 채권 채무
    public DOBJ CALLsearchMst_16_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchMst_16_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchMst_16_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  B.REPAY_YRMN,  B.TRNSF_GBN,  A.CLAIMPRES_NM,  D.CODE_NM,  A.CLAIM_PTTNRATE  ||  '(%)'  AS  CLAIM_PTTNRATE,  E.BANK_NM,  B.CLAIMPRES_ACCN_NUM,  B.REPAY_AMT  FROM  FIDU.TMEM_CLAIMDEBT  A,  FIDU.TMEM_CLAIMDEBTREPAY  B,  FIDU.TENV_CODE  D,  ACCT.TCAC_BANK  E  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  A.MNG_NUM  =  B.MNG_NUM   \n";
        query +=" AND  A.CLAIMDEBT_GBN  =  D.CODE_CD   \n";
        query +=" AND  B.CLAIMPRES_BANK_CD  =  E.BANK_CD(+)   \n";
        query +=" AND  SUBSTR(B.REPAY_YRMN,1,6)  =  :SUPP_YRMN   \n";
        query +=" AND  D.HIGH_CD  =  '00238'   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||  '%' ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    //##**$$searchMst_16
    //##**$$end
}
