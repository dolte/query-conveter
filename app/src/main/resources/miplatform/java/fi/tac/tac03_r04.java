
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac03_r04
{
    public tac03_r04()
    {
    }
    //##**$$thom_feededct_select
    /* * 프로그램명 : tac03_r04
    * 작성자 : 성낙문
    * 작성일 : 2009/12/01
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLthom_feededct_select(DOBJ dobj)
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
            dobj  = CALLthom_feededct_select_SEL1(Conn, dobj);           //  동호회비공제관리조회
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
    public DOBJ CTLthom_feededct_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLthom_feededct_select_SEL1(Conn, dobj);           //  동호회비공제관리조회
        return dobj;
    }
    // 동호회비공제관리조회
    public DOBJ CALLthom_feededct_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLthom_feededct_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLthom_feededct_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   CLUB_GBN = dobj.getRetObject("S").getRecord().get("CLUB_GBN");   //동호회 구분
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   APPTN_GBN = dobj.getRetObject("S").getRecord().get("APPTN_GBN");   //신청 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.HANMB_NM,  A.MB_CD,  B.MNG_NUM,  A.INS_NUM,  B.CLUB_GBN,  B.DEDCTTRM_START_YRMN,  B.DEDCTTRM_END_YRMN,  B.APPTN_GBN,  B.DEDCT_AMT,  B.APPTN_DAY,  B.RECPT_FORM,  B.INSPRES_ID,  TO_CHAR(B.INS_DATE,  'YYYYMMDD')  AS  INS_DATE,  B.MODPRES_ID,  B.MOD_DATE  ,  B.PROG_STAT,  C.CODE_NM  CLUB_GBN_NM,  D.CODE_NM  APPTN_GBN_NM,  E.CODE_NM  RECPT_FORM_NM  FROM  FIDU.TMEM_MB  A  ,  FIDU.TMEM_CLUBEXPSDEDCTBRE  B  ,FIDU.TENV_CODE  C,FIDU.TENV_CODE  D,FIDU.TENV_CODE  E  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  C.HIGH_CD(+)  =  '00040'   \n";
        query +=" AND  D.HIGH_CD(+)  =  '00293'   \n";
        query +=" AND  E.HIGH_CD(+)  =  '00292'   \n";
        query +=" AND  C.USE_YN(+)  =  'Y'   \n";
        query +=" AND  D.USE_YN(+)  =  'Y'   \n";
        query +=" AND  E.USE_YN(+)  =  'Y'   \n";
        query +=" AND  C.CODE_CD(+)  =  B.CLUB_GBN   \n";
        query +=" AND  D.CODE_CD(+)  =  B.APPTN_GBN   \n";
        query +=" AND  E.CODE_CD(+)  =  B.RECPT_FORM   \n";
        query +=" AND  B.CLUB_GBN  LIKE  :CLUB_GBN  ||  '%'   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||'%'   \n";
        query +=" AND  B.APPTN_GBN  LIKE  :APPTN_GBN  ||'%'   \n";
        query +=" AND  SUBSTR(B.APPTN_DAY,1,6)  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)  ORDER  BY  A.HANMB_NM,B.CLUB_GBN ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("CLUB_GBN", CLUB_GBN);               //동호회 구분
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        return sobj;
    }
    //##**$$thom_feededct_select
    //##**$$thom_dedctamt_select
    /* * 프로그램명 : tac03_r04
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLthom_dedctamt_select(DOBJ dobj)
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
            dobj  = CALLthom_dedctamt_select_SEL1(Conn, dobj);           //  동호회별회비조회
            dobj  = CALLthom_dedctamt_select_SEL2(Conn, dobj);           //  동호회별회비조회
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
    public DOBJ CTLthom_dedctamt_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLthom_dedctamt_select_SEL1(Conn, dobj);           //  동호회별회비조회
        dobj  = CALLthom_dedctamt_select_SEL2(Conn, dobj);           //  동호회별회비조회
        return dobj;
    }
    // 동호회별회비조회
    public DOBJ CALLthom_dedctamt_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLthom_dedctamt_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLthom_dedctamt_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   DEDCT_GBNTWO = dobj.getRetObject("S").getRecord().get("DEDCT_GBNTWO");   //공제구분2
        String   SUPP_YRMN = dobj.getRetObject("S").getRecord().get("SUPP_YRMN");   //지급 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.DEDCT_GBNTWO,  B.SUPP_YRMN,   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  USE_YN  =  'Y'   \n";
        query +=" AND  HIGH_CD  =  '00040'   \n";
        query +=" AND  CODE_CD  =  B.DEDCT_GBNTWO)  DEDCT_GBNTWO_NM,  C.HANMB_NM,  B.MB_CD,  NVL(A.DEDCT_AMT,0)  DEDCT_AMT1,  NVL(B.DEDCT_AMT,0)  DEDCT_AMT2,  1  CNT  ,  A.DEDCTTRM_START_YRMN,  A.DEDCTTRM_END_YRMN,  A.PROG_STAT  FROM  FIDU.TMEM_CLUBEXPSDEDCTBRE  A  ,  FIDU.TTAC_DEDCTAMT  B,  FIDU.TMEM_MB  C  WHERE  1=1   \n";
        query +=" AND  B.MB_CD  =  A.MB_CD(+)   \n";
        query +=" AND  B.MB_CD  =  C.MB_CD(+)   \n";
        query +=" AND  B.DEDCT_GBNTWO  =  A.CLUB_GBN(+)   \n";
        query +=" AND  B.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  B.MB_CD  LIKE  :MB_CD  ||'%'   \n";
        query +=" AND  B.DEDCT_GBNTWO  LIKE  :DEDCT_GBNTWO  ||  '%'   \n";
        query +=" AND  B.SUPP_YRMN  =  :SUPP_YRMN   \n";
        query +=" AND  :SUPP_YRMN  BETWEEN  A.DEDCTTRM_START_YRMN   \n";
        query +=" AND  A.DEDCTTRM_END_YRMN ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("DEDCT_GBNTWO", DEDCT_GBNTWO);               //공제구분2
        sobj.setString("SUPP_YRMN", SUPP_YRMN);               //지급 년월
        return sobj;
    }
    // 동호회별회비조회
    public DOBJ CALLthom_dedctamt_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLthom_dedctamt_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLthom_dedctamt_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   DEDCT_GBNTWO = dobj.getRetObject("S").getRecord().get("DEDCT_GBNTWO");   //공제구분2
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  T2.DEDCT_GBNTWO,  T2.SUPP_YRMN,   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  USE_YN  =  'Y'   \n";
        query +=" AND  HIGH_CD  =  '00040'   \n";
        query +=" AND  CODE_CD  =  T2.DEDCT_GBNTWO)  DEDCT_GBNTWO_NM,  T3.HANMB_NM,  T2.MB_CD,  NVL(T1.DEDCT_AMT,0)  DEDCT_AMT1,  NVL(T1.DEDCT_AMT,0)  DEDCT_AMT2,  1  CNT  FROM  FIDU.TMEM_CLUBEXPSDEDCTBRE  T1  ,  FIDU.TTAC_DEDCTAMT  T2  ,  FIDU.TMEM_MB  T3  WHERE  T1.MB_CD  =  T2.MB_CD   \n";
        query +=" AND  T1.MB_CD  =  T3.MB_CD(+)   \n";
        query +=" AND  T2.SUPP_YRMN  >=190001   \n";
        query +=" AND  T2.SUPP_YRMN  <=299901   \n";
        query +=" AND  T2.DEDCT_GBNONE  =  '02'   \n";
        query +=" AND  T2.SUPP_YRMN  BETWEEN  T1.DEDCTTRM_START_YRMN   \n";
        query +=" AND  T1.DEDCTTRM_END_YRMN   \n";
        query +=" AND  T1.CLUB_GBN  =  T2.DEDCT_GBNTWO   \n";
        query +=" AND  T2.DEDCT_GBNTWO  LIKE  :DEDCT_GBNTWO  ||  '%'   \n";
        query +=" AND  T2.MB_CD  LIKE  :MB_CD  ||'%'  ORDER  BY  SUPP_YRMN ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("DEDCT_GBNTWO", DEDCT_GBNTWO);               //공제구분2
        return sobj;
    }
    //##**$$thom_dedctamt_select
    //##**$$end
}
