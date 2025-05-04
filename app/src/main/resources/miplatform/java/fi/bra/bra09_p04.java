
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra09_p04
{
    public bra09_p04()
    {
    }
    //##**$$zip_mng_colupso
    /*
    */
    public DOBJ CTLzip_mng_colupso(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN1").equals("1") && dobj.getRetObject("S").getRecord().get("GBN2").equals("A"))
            {
                dobj  = CALLzip_mng_colupso_SEL1(Conn, dobj);           //  관리&시군구별통계
                dobj  = CALLzip_mng_colupso_MRG1( dobj);        //  시군구별통계결과취합
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN1").equals("1") && dobj.getRetObject("S").getRecord().get("GBN2").equals("B"))
            {
                dobj  = CALLzip_mng_colupso_SEL3(Conn, dobj);           //  관리&읍면동별통계
                dobj  = CALLzip_mng_colupso_MRG2( dobj);        //  읍면동별통계결과취합
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN1").equals("2") && dobj.getRetObject("S").getRecord().get("GBN2").equals("A"))
            {
                dobj  = CALLzip_mng_colupso_SEL2(Conn, dobj);           //  개발&시군구별통계
                dobj  = CALLzip_mng_colupso_MRG1( dobj);        //  시군구별통계결과취합
            }
            else
            {
                dobj  = CALLzip_mng_colupso_SEL4(Conn, dobj);           //  개발&읍면동별통계
                dobj  = CALLzip_mng_colupso_MRG2( dobj);        //  읍면동별통계결과취합
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
    public DOBJ CTLzip_mng_colupso( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN1").equals("1") && dobj.getRetObject("S").getRecord().get("GBN2").equals("A"))
        {
            dobj  = CALLzip_mng_colupso_SEL1(Conn, dobj);           //  관리&시군구별통계
            dobj  = CALLzip_mng_colupso_MRG1( dobj);        //  시군구별통계결과취합
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN1").equals("1") && dobj.getRetObject("S").getRecord().get("GBN2").equals("B"))
        {
            dobj  = CALLzip_mng_colupso_SEL3(Conn, dobj);           //  관리&읍면동별통계
            dobj  = CALLzip_mng_colupso_MRG2( dobj);        //  읍면동별통계결과취합
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN1").equals("2") && dobj.getRetObject("S").getRecord().get("GBN2").equals("A"))
        {
            dobj  = CALLzip_mng_colupso_SEL2(Conn, dobj);           //  개발&시군구별통계
            dobj  = CALLzip_mng_colupso_MRG1( dobj);        //  시군구별통계결과취합
        }
        else
        {
            dobj  = CALLzip_mng_colupso_SEL4(Conn, dobj);           //  개발&읍면동별통계
            dobj  = CALLzip_mng_colupso_MRG2( dobj);        //  읍면동별통계결과취합
        }
        return dobj;
    }
    // 관리&시군구별통계
    public DOBJ CALLzip_mng_colupso_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzip_mng_colupso_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_mng_colupso_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GUGUN  ,  SUM(DECODE(GBN,  'TOTAL',  1,  0))  TOTAL  ,  SUM(DECODE(GBN,  'COL_UPSO'  ,  1,  0))  COL_UPSO  ,  SUM(DECODE(BSTYP_CD,'o',  DECODE(GBN,  'COL_UPSO',1,0)))  o  ,  SUM(DECODE(BSTYP_CD,'k',  DECODE(GBN,  'COL_UPSO',1,0)))  k  ,  SUM(DECODE(BSTYP_CD,'l',  DECODE(GBN,  'COL_UPSO',1,0)))  l  ,  SUM(DECODE(BSTYP_CD,'q',  DECODE(GBN,  'COL_UPSO',1,0)))  q  ,  SUM(DECODE(BSTYP_CD,'r',  DECODE(GBN,  'COL_UPSO',1,0)))  r  ,  SUM(DECODE(BSTYP_CD,'u',  DECODE(GBN,  'COL_UPSO',1,0)))  u  ,  SUM(DECODE(BSTYP_CD,'s',  DECODE(GBN,  'COL_UPSO',1,0)))  s  ,  SUM(DECODE(BSTYP_CD,'n',  DECODE(GBN,  'COL_UPSO',1,0)))  n  ,  SUM(DECODE(BSTYP_CD,'m',  DECODE(GBN,  'COL_UPSO',1,0)))  m  ,  SUM(DECODE(BSTYP_CD,'p',  DECODE(GBN,  'COL_UPSO',1,0)))  p  ,  SUM(DECODE(BSTYP_CD,'w',  DECODE(GBN,  'COL_UPSO',1,0)))  w  ,  SUM(DECODE(BSTYP_CD,'x',  DECODE(GBN,  'COL_UPSO',1,0)))  x  ,  SUM(DECODE(BSTYP_CD,'v',  DECODE(GBN,  'COL_UPSO',1,0)))  v  ,  SUM(DECODE(BSTYP_CD,'y',  DECODE(GBN,  'COL_UPSO',1,0)))  y  ,  SUM(CASE  WHEN  ((  BSTYP_CD  <>  'o'   \n";
        query +=" AND  BSTYP_CD  <>  'k'   \n";
        query +=" AND  BSTYP_CD  <>  'l'   \n";
        query +=" AND  BSTYP_CD  <>  'q'   \n";
        query +=" AND  BSTYP_CD  <>  'r'   \n";
        query +=" AND  BSTYP_CD  <>  'u'   \n";
        query +=" AND  BSTYP_CD  <>  's'   \n";
        query +=" AND  BSTYP_CD  <>  'n'   \n";
        query +=" AND  BSTYP_CD  <>  'm'   \n";
        query +=" AND  BSTYP_CD  <>  'p'   \n";
        query +=" AND  BSTYP_CD  <>  'w'   \n";
        query +=" AND  BSTYP_CD  <>  'x'   \n";
        query +=" AND  BSTYP_CD  <>  'v'   \n";
        query +=" AND  BSTYP_CD  <>  'y')   \n";
        query +=" OR  BSTYP_CD  IS  NULL  )   \n";
        query +=" AND  GBN  =  'COL_UPSO'  THEN  1  ELSE  0  END)  ETC  FROM(   \n";
        query +=" SELECT  DISTINCT  BSTYP_CD  ,  UPSO_ZIP  ,  GBN  ,  MNG_ZIP  ,  GUGUN  ,  DONG  ,  UPSO_CD  FROM(   \n";
        query +=" SELECT  TA.UPSO_CD  ,  TA.BSTYP_CD  BSTYP_CD  ,  TA.UPSO_ZIP  UPSO_ZIP  ,  'COL_UPSO'  GBN  ,  TA.MNG_ZIP  MNG_ZIP  ,  TA.GUGUN  GUGUN  ,  TA.DONG  DONG  FROM  (   \n";
        query +=" SELECT  UPSO_CD,  UPSO_NEW_ZIP  UPSO_ZIP  ,   \n";
        query +=" (SELECT  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  GUGUN  ,   \n";
        query +=" (SELECT  DECODE(TOWNTWSHP,  '',  COURT_NM,  TOWNTWSHP)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  DONG  ,  GIBU.GET_MNG_ZIP(UPSO_CD)  AS  MNG_ZIP  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(UPSO_CD),1,1)  BSTYP_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  SUBSTR(NEW_DAY,1,6)  <=  :YRMN   \n";
        query +=" AND  STAFF_CD  =  DECODE(:STAFF_CD,  NULL,  STAFF_CD,  :STAFF_CD)  )  TA  ,  (   \n";
        query +=" SELECT  REPT_AMT  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  REPT_AMT  >  0  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  DISTR_AMT  >  0   \n";
        query +=" AND  REPT_GBN  <>  '08'  UNION  ALL   \n";
        query +=" SELECT  (CASE  WHEN  MIN(DISTR_SEQ)  OVER(PARTITION  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN)  =  DISTR_SEQ  THEN  A.DISTR_AMT  -  NVL(C.COMIS,  0)  ELSE  A.DISTR_AMT  END)  AS  REPT_AMT  ,  A.UPSO_CD  ,  A.BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_REPT_VIRTUAL  B  ,  GIBU.TBRA_REPT  C  WHERE  A.MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  '08'   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  C.REPT_GBN(+)  =  '08'  )  TB  WHERE  TB.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TB.BRAN_CD  =  :BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  TA.UPSO_CD  ,  TA.BSTYP_CD  ,  TA.UPSO_ZIP  ,  'TOTAL'  GBN  ,  TA.MNG_ZIP  ,  TA.GUGUN  ,  TA.DONG  FROM   \n";
        query +=" (SELECT  UPSO_CD,  UPSO_NEW_ZIP  UPSO_ZIP  ,   \n";
        query +=" (SELECT  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  GUGUN  ,   \n";
        query +=" (SELECT  DECODE(TOWNTWSHP,  '',  COURT_NM,  TOWNTWSHP)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  DONG  ,  GIBU.GET_MNG_ZIP(UPSO_CD)  AS  MNG_ZIP  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(UPSO_CD),1,1)  BSTYP_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  STAFF_CD  =  DECODE(:STAFF_CD,  NULL,  STAFF_CD,  :STAFF_CD)  )  TA  )  )  GROUP  BY  MNG_ZIP,  GUGUN  ORDER  BY  MNG_ZIP ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 시군구별통계결과취합
    public DOBJ CALLzip_mng_colupso_MRG1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL2","");
        rvobj.setName("MRG1") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 관리&읍면동별통계
    public DOBJ CALLzip_mng_colupso_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzip_mng_colupso_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_mng_colupso_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MNG_ZIP  ,  GUGUN  ,  DONG  ,  SUM(DECODE(GBN,  'TOTAL',  1,  0))  TOTAL  ,  SUM(DECODE(GBN,  'COL_UPSO'  ,  1,  0))  COL_UPSO  ,  SUM(DECODE(BSTYP_CD,'o',  DECODE(GBN,  'COL_UPSO',1,0)))  o  ,  SUM(DECODE(BSTYP_CD,'k',  DECODE(GBN,  'COL_UPSO',1,0)))  k  ,  SUM(DECODE(BSTYP_CD,'l',  DECODE(GBN,  'COL_UPSO',1,0)))  l  ,  SUM(DECODE(BSTYP_CD,'q',  DECODE(GBN,  'COL_UPSO',1,0)))  q  ,  SUM(DECODE(BSTYP_CD,'r',  DECODE(GBN,  'COL_UPSO',1,0)))  r  ,  SUM(DECODE(BSTYP_CD,'u',  DECODE(GBN,  'COL_UPSO',1,0)))  u  ,  SUM(DECODE(BSTYP_CD,'s',  DECODE(GBN,  'COL_UPSO',1,0)))  s  ,  SUM(DECODE(BSTYP_CD,'n',  DECODE(GBN,  'COL_UPSO',1,0)))  n  ,  SUM(DECODE(BSTYP_CD,'m',  DECODE(GBN,  'COL_UPSO',1,0)))  m  ,  SUM(DECODE(BSTYP_CD,'p',  DECODE(GBN,  'COL_UPSO',1,0)))  p  ,  SUM(DECODE(BSTYP_CD,'w',  DECODE(GBN,  'COL_UPSO',1,0)))  w  ,  SUM(DECODE(BSTYP_CD,'x',  DECODE(GBN,  'COL_UPSO',1,0)))  x  ,  SUM(DECODE(BSTYP_CD,'v',  DECODE(GBN,  'COL_UPSO',1,0)))  v  ,  SUM(DECODE(BSTYP_CD,'y',  DECODE(GBN,  'COL_UPSO',1,0)))  y  ,  SUM(CASE  WHEN  ((  BSTYP_CD  <>  'o'   \n";
        query +=" AND  BSTYP_CD  <>  'k'   \n";
        query +=" AND  BSTYP_CD  <>  'l'   \n";
        query +=" AND  BSTYP_CD  <>  'q'   \n";
        query +=" AND  BSTYP_CD  <>  'r'   \n";
        query +=" AND  BSTYP_CD  <>  'u'   \n";
        query +=" AND  BSTYP_CD  <>  's'   \n";
        query +=" AND  BSTYP_CD  <>  'n'   \n";
        query +=" AND  BSTYP_CD  <>  'm'   \n";
        query +=" AND  BSTYP_CD  <>  'p'   \n";
        query +=" AND  BSTYP_CD  <>  'w'   \n";
        query +=" AND  BSTYP_CD  <>  'x'   \n";
        query +=" AND  BSTYP_CD  <>  'v'   \n";
        query +=" AND  BSTYP_CD  <>  'y')   \n";
        query +=" OR  BSTYP_CD  IS  NULL  )   \n";
        query +=" AND  GBN  =  'COL_UPSO'  THEN  1  ELSE  0  END)  ETC  FROM(   \n";
        query +=" SELECT  DISTINCT  BSTYP_CD  ,  UPSO_ZIP  ,  GBN  ,  MNG_ZIP  ,  GUGUN  ,  DONG  ,  UPSO_CD  FROM(   \n";
        query +=" SELECT  TA.UPSO_CD  ,  TA.BSTYP_CD  BSTYP_CD  ,  TA.UPSO_ZIP  UPSO_ZIP  ,  'COL_UPSO'  GBN  ,  TA.MNG_ZIP  MNG_ZIP  ,  TA.GUGUN  GUGUN  ,  TA.DONG  DONG  FROM  (   \n";
        query +=" SELECT  UPSO_CD,  UPSO_NEW_ZIP  UPSO_ZIP  ,   \n";
        query +=" (SELECT  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  GUGUN  ,   \n";
        query +=" (SELECT  DECODE(TOWNTWSHP,  '',  COURT_NM,  TOWNTWSHP)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  DONG  ,  GIBU.GET_MNG_ZIP(UPSO_CD)  AS  MNG_ZIP  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(UPSO_CD),1,1)  BSTYP_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  SUBSTR(NEW_DAY,1,6)  <=  :YRMN   \n";
        query +=" AND  STAFF_CD  =  DECODE(:STAFF_CD,  NULL,  STAFF_CD,  :STAFF_CD)  )  TA  ,  (   \n";
        query +=" SELECT  REPT_AMT  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  REPT_AMT  >  0  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  DISTR_AMT  >  0   \n";
        query +=" AND  REPT_GBN  <>  '08'  UNION  ALL   \n";
        query +=" SELECT  (CASE  WHEN  MIN(DISTR_SEQ)  OVER(PARTITION  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN)  =  DISTR_SEQ  THEN  A.DISTR_AMT  -  NVL(C.COMIS,  0)  ELSE  A.DISTR_AMT  END)  AS  REPT_AMT  ,  A.UPSO_CD  ,  A.BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_REPT_VIRTUAL  B  ,  GIBU.TBRA_REPT  C  WHERE  A.MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  '08'   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  C.REPT_GBN(+)  =  '08'  )  TB  WHERE  TB.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TB.BRAN_CD  =  :BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  TA.UPSO_CD  ,  TA.BSTYP_CD  ,  TA.UPSO_ZIP  ,  'TOTAL'  GBN  ,  TA.MNG_ZIP  ,  TA.GUGUN  ,  TA.DONG  FROM   \n";
        query +=" (SELECT  UPSO_CD,  UPSO_NEW_ZIP  UPSO_ZIP  ,   \n";
        query +=" (SELECT  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  GUGUN  ,   \n";
        query +=" (SELECT  DECODE(TOWNTWSHP,  '',  COURT_NM,  TOWNTWSHP)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  DONG  ,  GIBU.GET_MNG_ZIP(UPSO_CD)  AS  MNG_ZIP  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(UPSO_CD),1,1)  BSTYP_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  STAFF_CD  =  DECODE(:STAFF_CD,  NULL,  STAFF_CD,  :STAFF_CD)  )  TA  )  )  GROUP  BY  MNG_ZIP,  GUGUN,  DONG  ORDER  BY  MNG_ZIP,  DONG ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 읍면동별통계결과취합
    public DOBJ CALLzip_mng_colupso_MRG2(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG2");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL3, SEL4","");
        rvobj.setName("MRG2") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 개발&시군구별통계
    public DOBJ CALLzip_mng_colupso_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzip_mng_colupso_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_mng_colupso_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.GUGUN  ,  COUNT(TA.UPSO_CD)  TOTAL  ,  SUM(DECODE(SUBSTR(TA.NEW_DAY,  1,6),  :YRMN,  1,0))  COL_UPSO  ,  SUM(DECODE(TA.BSTYP_CD,'o',1,0))  o  ,  SUM(DECODE(TA.BSTYP_CD,'k',1,0))  k  ,  SUM(DECODE(TA.BSTYP_CD,'l',1,0))  l  ,  SUM(DECODE(TA.BSTYP_CD,'q',1,0))  q  ,  SUM(DECODE(TA.BSTYP_CD,'r',1,0))  r  ,  SUM(DECODE(TA.BSTYP_CD,'u',1,0))  u  ,  SUM(DECODE(TA.BSTYP_CD,'s',1,0))  s  ,  SUM(DECODE(TA.BSTYP_CD,'n',1,0))  n  ,  SUM(DECODE(TA.BSTYP_CD,'m',1,0))  m  ,  SUM(DECODE(TA.BSTYP_CD,'p',1,0))  p  ,  SUM(DECODE(TA.BSTYP_CD,'w',1,0))  w  ,  SUM(DECODE(TA.BSTYP_CD,'x',1,0))  x  ,  SUM(DECODE(TA.BSTYP_CD,'v',1,0))  v  ,  SUM(DECODE(TA.BSTYP_CD,'y',1,0))  y  ,  SUM(CASE  WHEN  (TA.BSTYP_CD  <>  'o'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'k'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'l'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'q'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'r'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'u'   \n";
        query +=" AND  TA.BSTYP_CD  <>  's'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'n'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'm'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'p'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'w'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'x'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'v'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'y')   \n";
        query +=" OR  TA.BSTYP_CD  IS  NULL  THEN  1  ELSE  0  END)  etc  FROM  (   \n";
        query +=" SELECT  UPSO_CD,  UPSO_NEW_ZIP  UPSO_ZIP,  NEW_DAY  ,   \n";
        query +=" (SELECT  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  GUGUN  ,   \n";
        query +=" (SELECT  DECODE(TOWNTWSHP,  '',  COURT_NM,  TOWNTWSHP)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  DONG  ,  GIBU.GET_MNG_ZIP(UPSO_CD)  AS  MNG_ZIP  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(UPSO_CD),1,1)  BSTYP_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  SUBSTR(OPBI_DAY,  1,  6)  <=  :YRMN   \n";
        query +=" AND  STAFF_CD  =  DECODE(:STAFF_CD,  NULL,  STAFF_CD,  :STAFF_CD)  )TA  GROUP  BY  TA.MNG_ZIP,  TA.GUGUN  ORDER  BY  TA.MNG_ZIP ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 개발&읍면동별통계
    public DOBJ CALLzip_mng_colupso_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzip_mng_colupso_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_mng_colupso_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.MNG_ZIP  ,  TA.GUGUN  ,  TA.DONG  ,  COUNT(TA.UPSO_CD)  TOTAL  ,  SUM(DECODE(SUBSTR(TA.NEW_DAY,  1,6),  :YRMN,  1,0))  COL_UPSO  ,  SUM(DECODE(TA.BSTYP_CD,'o',1,0))  o  ,  SUM(DECODE(TA.BSTYP_CD,'k',1,0))  k  ,  SUM(DECODE(TA.BSTYP_CD,'l',1,0))  l  ,  SUM(DECODE(TA.BSTYP_CD,'q',1,0))  q  ,  SUM(DECODE(TA.BSTYP_CD,'r',1,0))  r  ,  SUM(DECODE(TA.BSTYP_CD,'u',1,0))  u  ,  SUM(DECODE(TA.BSTYP_CD,'s',1,0))  s  ,  SUM(DECODE(TA.BSTYP_CD,'n',1,0))  n  ,  SUM(DECODE(TA.BSTYP_CD,'m',1,0))  m  ,  SUM(DECODE(TA.BSTYP_CD,'p',1,0))  p  ,  SUM(DECODE(TA.BSTYP_CD,'w',1,0))  w  ,  SUM(DECODE(TA.BSTYP_CD,'x',1,0))  x  ,  SUM(DECODE(TA.BSTYP_CD,'v',1,0))  v  ,  SUM(DECODE(TA.BSTYP_CD,'y',1,0))  y  ,  SUM(CASE  WHEN  (TA.BSTYP_CD  <>  'o'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'k'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'l'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'q'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'r'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'u'   \n";
        query +=" AND  TA.BSTYP_CD  <>  's'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'n'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'm'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'p'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'w'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'x'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'v'   \n";
        query +=" AND  TA.BSTYP_CD  <>  'y')   \n";
        query +=" OR  TA.BSTYP_CD  IS  NULL  THEN  1  ELSE  0  END)  etc  FROM  (   \n";
        query +=" SELECT  UPSO_CD,  UPSO_NEW_ZIP  UPSO_ZIP,  NEW_DAY  ,   \n";
        query +=" (SELECT  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  GUGUN  ,   \n";
        query +=" (SELECT  DECODE(TOWNTWSHP,  '',  COURT_NM,  TOWNTWSHP)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  DONG  ,  GIBU.GET_MNG_ZIP(UPSO_CD)  AS  MNG_ZIP  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(UPSO_CD),1,1)  BSTYP_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  (NEW_DAY  IS  NULL   \n";
        query +=" OR  SUBSTR(NEW_DAY,1,6)  >  :YRMN)   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  SUBSTR(OPBI_DAY,  1,  6)  <=  :YRMN   \n";
        query +=" AND  STAFF_CD  =  DECODE(:STAFF_CD,  NULL,  STAFF_CD,  :STAFF_CD)  )TA  GROUP  BY  TA.MNG_ZIP,  TA.GUGUN,  TA.DONG  ORDER  BY  TA.MNG_ZIP,  TA.DONG ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$zip_mng_colupso
    //##**$$end
}
