
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra06_p02
{
    public bra06_p02()
    {
    }
    //##**$$zip_mng_upsolist
    /*
    */
    public DOBJ CTLzip_mng_upsolist(DOBJ dobj)
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
                dobj  = CALLzip_mng_upsolist_SEL1(Conn, dobj);           //  관리&시군구별 현황
                dobj  = CALLzip_mng_upsolist_MRG1( dobj);        //  시군구별현황 최종결과
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN1").equals("2") && dobj.getRetObject("S").getRecord().get("GBN2").equals("A"))
            {
                dobj  = CALLzip_mng_upsolist_SEL2(Conn, dobj);           //  개발중&시군구별 현황
                dobj  = CALLzip_mng_upsolist_MRG1( dobj);        //  시군구별현황 최종결과
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN1").equals("3") && dobj.getRetObject("S").getRecord().get("GBN2").equals("A"))
            {
                dobj  = CALLzip_mng_upsolist_SEL3(Conn, dobj);           //  전체&시군구별현황
                dobj  = CALLzip_mng_upsolist_MRG1( dobj);        //  시군구별현황 최종결과
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN1").equals("1") && dobj.getRetObject("S").getRecord().get("GBN2").equals("B"))
            {
                dobj  = CALLzip_mng_upsolist_SEL4(Conn, dobj);           //  관리업소&읍면동별현황
                dobj  = CALLzip_mng_upsolist_MRG2( dobj);        //  읍면동별현황최종결과
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN1").equals("2") && dobj.getRetObject("S").getRecord().get("GBN2").equals("B"))
            {
                dobj  = CALLzip_mng_upsolist_SEL5(Conn, dobj);           //  개발중&읍면동현황
                dobj  = CALLzip_mng_upsolist_MRG2( dobj);        //  읍면동별현황최종결과
            }
            else
            {
                dobj  = CALLzip_mng_upsolist_SEL6(Conn, dobj);           //  전체&읍면동별현황
                dobj  = CALLzip_mng_upsolist_MRG2( dobj);        //  읍면동별현황최종결과
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
    public DOBJ CTLzip_mng_upsolist( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN1").equals("1") && dobj.getRetObject("S").getRecord().get("GBN2").equals("A"))
        {
            dobj  = CALLzip_mng_upsolist_SEL1(Conn, dobj);           //  관리&시군구별 현황
            dobj  = CALLzip_mng_upsolist_MRG1( dobj);        //  시군구별현황 최종결과
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN1").equals("2") && dobj.getRetObject("S").getRecord().get("GBN2").equals("A"))
        {
            dobj  = CALLzip_mng_upsolist_SEL2(Conn, dobj);           //  개발중&시군구별 현황
            dobj  = CALLzip_mng_upsolist_MRG1( dobj);        //  시군구별현황 최종결과
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN1").equals("3") && dobj.getRetObject("S").getRecord().get("GBN2").equals("A"))
        {
            dobj  = CALLzip_mng_upsolist_SEL3(Conn, dobj);           //  전체&시군구별현황
            dobj  = CALLzip_mng_upsolist_MRG1( dobj);        //  시군구별현황 최종결과
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN1").equals("1") && dobj.getRetObject("S").getRecord().get("GBN2").equals("B"))
        {
            dobj  = CALLzip_mng_upsolist_SEL4(Conn, dobj);           //  관리업소&읍면동별현황
            dobj  = CALLzip_mng_upsolist_MRG2( dobj);        //  읍면동별현황최종결과
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN1").equals("2") && dobj.getRetObject("S").getRecord().get("GBN2").equals("B"))
        {
            dobj  = CALLzip_mng_upsolist_SEL5(Conn, dobj);           //  개발중&읍면동현황
            dobj  = CALLzip_mng_upsolist_MRG2( dobj);        //  읍면동별현황최종결과
        }
        else
        {
            dobj  = CALLzip_mng_upsolist_SEL6(Conn, dobj);           //  전체&읍면동별현황
            dobj  = CALLzip_mng_upsolist_MRG2( dobj);        //  읍면동별현황최종결과
        }
        return dobj;
    }
    // 관리&시군구별 현황
    public DOBJ CALLzip_mng_upsolist_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzip_mng_upsolist_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_mng_upsolist_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String YRMN ="";        //년월
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.GUGUN  ,  FIDU.GET_STAFF_NM(TA.STAFF_CD)  AS  STAFF_NM  ,  COUNT(TA.UPSO_CD)  TOTAL  ,  SUM(DECODE(TA.BSTYP_CD,'o',1,0))  L_o  ,  SUM(DECODE(TA.BSTYP_CD,'k',1,0))  L_k  ,  SUM(DECODE(TA.BSTYP_CD,'l',1,0))  L_l  ,  SUM(DECODE(TA.BSTYP_CD,'q',1,0))  L_q  ,  SUM(DECODE(TA.BSTYP_CD,'r',1,0))  L_r  ,  SUM(DECODE(TA.BSTYP_CD,'u',1,0))  L_u  ,  SUM(DECODE(TA.BSTYP_CD,'s',1,0))  L_s  ,  SUM(DECODE(TA.BSTYP_CD,'n',1,0))  L_n  ,  SUM(DECODE(TA.BSTYP_CD,'m',1,0))  L_m  ,  SUM(DECODE(TA.BSTYP_CD,'p',1,0))  L_p  ,  SUM(DECODE(TA.BSTYP_CD,'w',1,0))  L_w  ,  SUM(DECODE(TA.BSTYP_CD,'x',1,0))  L_x  ,  SUM(DECODE(TA.BSTYP_CD,'v',1,0))  L_v  ,  SUM(DECODE(TA.BSTYP_CD,'y',1,0))  L_y  ,  SUM(DECODE(TA.BSTYP_CD,'J',1,0))  H_J  ,  SUM(DECODE(TA.BSTYP_CD,'f',1,0))  L_f  ,  SUM(DECODE(TA.BSTYP_CD,'I',1,0))  H_I  ,  SUM(DECODE(TA.BSTYP_CD,'K',1,0))  H_K  ,  SUM(DECODE(TA.BSTYP_CD,'M',1,0))  H_M  ,  SUM(DECODE(TA.BSTYP_CD,'N',1,0))  H_N  ,  SUM(DECODE(TA.BSTYP_CD,'O',1,0))  H_O  ,  SUM(DECODE(TA.BSTYP_CD,'L',1,0))  H_L  ,  SUM(DECODE(TA.BSTYP_CD,'t',1,0))  L_t  ,  SUM(CASE  WHEN  TA.BSTYP_CD  IN  ('G',  'H',  'g')  THEN  1  ELSE  0  END)  DC  ,  SUM(CASE  WHEN  TA.BSTYP_CD  IN  ('A',  'B',  'C',  'D',  'E')  THEN  1  ELSE  0  END)  DB  ,  SUM(CASE  WHEN  (  TA.BSTYP_CD  NOT  IN  ('o','y','J','f','l','k','p','n','m','r','q','w','G','H','g','I','K','M','N','O','L','u','s','x','A','B','C','D','E','t')   \n";
        query +=" OR  TA.BSTYP_CD  IS  NULL)  THEN  1  ELSE  0  END)  etc  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,   \n";
        query +=" (SELECT  DECODE(ATTE,  '세종특별자치시',  ATTE,  CITYCNTYDSRC)  AS  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  GUGUN  ,  GIBU.GET_MNG_ZIP(UPSO_CD)  GUGUN_CD  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(UPSO_CD),1,1)  AS  BSTYP_CD  ,  STAFF_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NVL(STAFF_CD,'  ')  LIKE  :STAFF_CD||'%'   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  TO_CHAR(SYSDATE,'YYYYMM')||'31'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  TO_CHAR(SYSDATE,'YYYYMM'))  )  TA  GROUP  BY  TA.GUGUN,  TA.GUGUN_CD,  TA.STAFF_CD  ORDER  BY  TA.GUGUN_CD ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 시군구별현황 최종결과
    public DOBJ CALLzip_mng_upsolist_MRG1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL2, SEL3","");
        rvobj.setName("MRG1") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 개발중&시군구별 현황
    public DOBJ CALLzip_mng_upsolist_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzip_mng_upsolist_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_mng_upsolist_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String YRMN ="";        //년월
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.GUGUN  ,  FIDU.GET_STAFF_NM(TA.STAFF_CD)  AS  STAFF_NM  ,  COUNT(TA.UPSO_CD)  TOTAL  ,  SUM(DECODE(TA.BSTYP_CD,'o',1,0))  L_o  ,  SUM(DECODE(TA.BSTYP_CD,'k',1,0))  L_k  ,  SUM(DECODE(TA.BSTYP_CD,'l',1,0))  L_l  ,  SUM(DECODE(TA.BSTYP_CD,'q',1,0))  L_q  ,  SUM(DECODE(TA.BSTYP_CD,'r',1,0))  L_r  ,  SUM(DECODE(TA.BSTYP_CD,'u',1,0))  L_u  ,  SUM(DECODE(TA.BSTYP_CD,'s',1,0))  L_s  ,  SUM(DECODE(TA.BSTYP_CD,'n',1,0))  L_n  ,  SUM(DECODE(TA.BSTYP_CD,'m',1,0))  L_m  ,  SUM(DECODE(TA.BSTYP_CD,'p',1,0))  L_p  ,  SUM(DECODE(TA.BSTYP_CD,'w',1,0))  L_w  ,  SUM(DECODE(TA.BSTYP_CD,'x',1,0))  L_x  ,  SUM(DECODE(TA.BSTYP_CD,'v',1,0))  L_v  ,  SUM(DECODE(TA.BSTYP_CD,'y',1,0))  L_y  ,  SUM(DECODE(TA.BSTYP_CD,'J',1,0))  H_J  ,  SUM(DECODE(TA.BSTYP_CD,'f',1,0))  L_f  ,  SUM(DECODE(TA.BSTYP_CD,'I',1,0))  H_I  ,  SUM(DECODE(TA.BSTYP_CD,'K',1,0))  H_K  ,  SUM(DECODE(TA.BSTYP_CD,'M',1,0))  H_M  ,  SUM(DECODE(TA.BSTYP_CD,'N',1,0))  H_N  ,  SUM(DECODE(TA.BSTYP_CD,'O',1,0))  H_O  ,  SUM(DECODE(TA.BSTYP_CD,'L',1,0))  H_L  ,  SUM(DECODE(TA.BSTYP_CD,'t',1,0))  L_t  ,  SUM(CASE  WHEN  TA.BSTYP_CD  IN  ('G',  'H',  'g')  THEN  1  ELSE  0  END)  DC  ,  SUM(CASE  WHEN  TA.BSTYP_CD  IN  ('A',  'B',  'C',  'D',  'E')  THEN  1  ELSE  0  END)  DB  ,  SUM(CASE  WHEN  (  TA.BSTYP_CD  NOT  IN  ('o','y','J','f','l','k','p','n','m','r','q','w','G','H','g','I','K','M','N','O','L','u','s','x','A','B','C','D','E','t')   \n";
        query +=" OR  TA.BSTYP_CD  IS  NULL)  THEN  1  ELSE  0  END)  etc  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,   \n";
        query +=" (SELECT  DECODE(ATTE,  '세종특별자치시',  ATTE,  CITYCNTYDSRC)  AS  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  GUGUN  ,  GIBU.GET_MNG_ZIP(UPSO_CD)  GUGUN_CD  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(UPSO_CD),1,1)  AS  BSTYP_CD  ,  STAFF_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NVL(STAFF_CD,'  ')  LIKE  :STAFF_CD||'%'   \n";
        query +=" AND  NEW_DAY  IS  NULL   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  TO_CHAR(SYSDATE,'YYYYMM'))  )  TA  GROUP  BY  TA.GUGUN,  TA.GUGUN_CD,  TA.STAFF_CD  ORDER  BY  TA.GUGUN_CD ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 전체&시군구별현황
    public DOBJ CALLzip_mng_upsolist_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzip_mng_upsolist_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_mng_upsolist_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String YRMN ="";        //년월
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.GUGUN  ,  FIDU.GET_STAFF_NM(TA.STAFF_CD)  AS  STAFF_NM  ,  COUNT(TA.UPSO_CD)  TOTAL  ,  SUM(DECODE(TA.BSTYP_CD,'o',1,0))  L_o  ,  SUM(DECODE(TA.BSTYP_CD,'k',1,0))  L_k  ,  SUM(DECODE(TA.BSTYP_CD,'l',1,0))  L_l  ,  SUM(DECODE(TA.BSTYP_CD,'q',1,0))  L_q  ,  SUM(DECODE(TA.BSTYP_CD,'r',1,0))  L_r  ,  SUM(DECODE(TA.BSTYP_CD,'u',1,0))  L_u  ,  SUM(DECODE(TA.BSTYP_CD,'s',1,0))  L_s  ,  SUM(DECODE(TA.BSTYP_CD,'n',1,0))  L_n  ,  SUM(DECODE(TA.BSTYP_CD,'m',1,0))  L_m  ,  SUM(DECODE(TA.BSTYP_CD,'p',1,0))  L_p  ,  SUM(DECODE(TA.BSTYP_CD,'w',1,0))  L_w  ,  SUM(DECODE(TA.BSTYP_CD,'x',1,0))  L_x  ,  SUM(DECODE(TA.BSTYP_CD,'v',1,0))  L_v  ,  SUM(DECODE(TA.BSTYP_CD,'y',1,0))  L_y  ,  SUM(DECODE(TA.BSTYP_CD,'J',1,0))  H_J  ,  SUM(DECODE(TA.BSTYP_CD,'f',1,0))  L_f  ,  SUM(DECODE(TA.BSTYP_CD,'I',1,0))  H_I  ,  SUM(DECODE(TA.BSTYP_CD,'K',1,0))  H_K  ,  SUM(DECODE(TA.BSTYP_CD,'M',1,0))  H_M  ,  SUM(DECODE(TA.BSTYP_CD,'N',1,0))  H_N  ,  SUM(DECODE(TA.BSTYP_CD,'O',1,0))  H_O  ,  SUM(DECODE(TA.BSTYP_CD,'L',1,0))  H_L  ,  SUM(DECODE(TA.BSTYP_CD,'t',1,0))  L_t  ,  SUM(CASE  WHEN  TA.BSTYP_CD  IN  ('G',  'H',  'g')  THEN  1  ELSE  0  END)  DC  ,  SUM(CASE  WHEN  TA.BSTYP_CD  IN  ('A',  'B',  'C',  'D',  'E')  THEN  1  ELSE  0  END)  DB  ,  SUM(CASE  WHEN  (  TA.BSTYP_CD  NOT  IN  ('o','y','J','f','l','k','p','n','m','r','q','w','G','H','g','I','K','M','N','O','L','u','s','x','A','B','C','D','E','t')   \n";
        query +=" OR  TA.BSTYP_CD  IS  NULL)  THEN  1  ELSE  0  END)  etc  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,   \n";
        query +=" (SELECT  DECODE(ATTE,  '세종특별자치시',  ATTE,  CITYCNTYDSRC)  AS  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  GUGUN  ,  GIBU.GET_MNG_ZIP(UPSO_CD)  GUGUN_CD  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(UPSO_CD),1,1)  AS  BSTYP_CD  ,  STAFF_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NVL(STAFF_CD,'  ')  LIKE  :STAFF_CD||'%'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  TO_CHAR(SYSDATE,'YYYYMM'))  )  TA  GROUP  BY  TA.GUGUN,  TA.GUGUN_CD,  TA.STAFF_CD  ORDER  BY  TA.GUGUN_CD ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 관리업소&읍면동별현황
    public DOBJ CALLzip_mng_upsolist_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzip_mng_upsolist_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_mng_upsolist_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String YRMN ="";        //년월
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.GUGUN_CD  AS  MNG_ZIP  ,  TA.GUGUN,  TA.DONG  ,  FIDU.GET_STAFF_NM(TA.STAFF_CD)  AS  STAFF_NM  ,  COUNT(TA.UPSO_CD)  TOTAL  ,  SUM(DECODE(TA.BSTYP_CD,'o',1,0))  L_o  ,  SUM(DECODE(TA.BSTYP_CD,'k',1,0))  L_k  ,  SUM(DECODE(TA.BSTYP_CD,'l',1,0))  L_l  ,  SUM(DECODE(TA.BSTYP_CD,'q',1,0))  L_q  ,  SUM(DECODE(TA.BSTYP_CD,'r',1,0))  L_r  ,  SUM(DECODE(TA.BSTYP_CD,'u',1,0))  L_u  ,  SUM(DECODE(TA.BSTYP_CD,'s',1,0))  L_s  ,  SUM(DECODE(TA.BSTYP_CD,'n',1,0))  L_n  ,  SUM(DECODE(TA.BSTYP_CD,'m',1,0))  L_m  ,  SUM(DECODE(TA.BSTYP_CD,'p',1,0))  L_p  ,  SUM(DECODE(TA.BSTYP_CD,'w',1,0))  L_w  ,  SUM(DECODE(TA.BSTYP_CD,'x',1,0))  L_x  ,  SUM(DECODE(TA.BSTYP_CD,'v',1,0))  L_v  ,  SUM(DECODE(TA.BSTYP_CD,'y',1,0))  L_y  ,  SUM(DECODE(TA.BSTYP_CD,'J',1,0))  H_J  ,  SUM(DECODE(TA.BSTYP_CD,'f',1,0))  L_f  ,  SUM(DECODE(TA.BSTYP_CD,'I',1,0))  H_I  ,  SUM(DECODE(TA.BSTYP_CD,'K',1,0))  H_K  ,  SUM(DECODE(TA.BSTYP_CD,'M',1,0))  H_M  ,  SUM(DECODE(TA.BSTYP_CD,'N',1,0))  H_N  ,  SUM(DECODE(TA.BSTYP_CD,'O',1,0))  H_O  ,  SUM(DECODE(TA.BSTYP_CD,'L',1,0))  H_L  ,  SUM(DECODE(TA.BSTYP_CD,'t',1,0))  L_t  ,  SUM(CASE  WHEN  TA.BSTYP_CD  IN  ('G',  'H',  'g')  THEN  1  ELSE  0  END)  DC  ,  SUM(CASE  WHEN  TA.BSTYP_CD  IN  ('A',  'B',  'C',  'D',  'E')  THEN  1  ELSE  0  END)  DB  ,  SUM(CASE  WHEN  (  TA.BSTYP_CD  NOT  IN  ('o','y','J','f','l','k','p','n','m','r','q','w','G','H','g','I','K','M','N','O','L','u','s','x','A','B','C','D','E','t')   \n";
        query +=" OR  TA.BSTYP_CD  IS  NULL)  THEN  1  ELSE  0  END)  etc  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,   \n";
        query +=" (SELECT  DECODE(ATTE,  '세종특별자치시',  ATTE,  CITYCNTYDSRC)  AS  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  GUGUN  ,   \n";
        query +=" (SELECT  DECODE(TOWNTWSHP,  '',  COURT_NM,  TOWNTWSHP)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  DONG  ,  GIBU.GET_MNG_ZIP(UPSO_CD)  GUGUN_CD  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(UPSO_CD),1,1)  AS  BSTYP_CD  ,  STAFF_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NVL(STAFF_CD,'  ')  LIKE  :STAFF_CD||'%'   \n";
        query +=" AND  NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  NEW_DAY  <=  TO_CHAR(SYSDATE,'YYYYMM')||'31'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  TO_CHAR(SYSDATE,'YYYYMM'))  )  TA  GROUP  BY  TA.GUGUN,  TA.DONG,  TA.GUGUN_CD,  TA.STAFF_CD  ORDER  BY  TA.GUGUN_CD,  TA.DONG ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 읍면동별현황최종결과
    public DOBJ CALLzip_mng_upsolist_MRG2(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG2");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL4, SEL5, SEL6","");
        rvobj.setName("MRG2") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 개발중&읍면동현황
    public DOBJ CALLzip_mng_upsolist_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzip_mng_upsolist_SEL5(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_mng_upsolist_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String YRMN ="";        //년월
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.GUGUN_CD  AS  MNG_ZIP  ,  TA.GUGUN,  TA.DONG  ,  FIDU.GET_STAFF_NM(TA.STAFF_CD)  AS  STAFF_NM  ,  COUNT(TA.UPSO_CD)  TOTAL  ,  SUM(DECODE(TA.BSTYP_CD,'o',1,0))  L_o  ,  SUM(DECODE(TA.BSTYP_CD,'k',1,0))  L_k  ,  SUM(DECODE(TA.BSTYP_CD,'l',1,0))  L_l  ,  SUM(DECODE(TA.BSTYP_CD,'q',1,0))  L_q  ,  SUM(DECODE(TA.BSTYP_CD,'r',1,0))  L_r  ,  SUM(DECODE(TA.BSTYP_CD,'u',1,0))  L_u  ,  SUM(DECODE(TA.BSTYP_CD,'s',1,0))  L_s  ,  SUM(DECODE(TA.BSTYP_CD,'n',1,0))  L_n  ,  SUM(DECODE(TA.BSTYP_CD,'m',1,0))  L_m  ,  SUM(DECODE(TA.BSTYP_CD,'p',1,0))  L_p  ,  SUM(DECODE(TA.BSTYP_CD,'w',1,0))  L_w  ,  SUM(DECODE(TA.BSTYP_CD,'x',1,0))  L_x  ,  SUM(DECODE(TA.BSTYP_CD,'v',1,0))  L_v  ,  SUM(DECODE(TA.BSTYP_CD,'y',1,0))  L_y  ,  SUM(DECODE(TA.BSTYP_CD,'J',1,0))  H_J  ,  SUM(DECODE(TA.BSTYP_CD,'f',1,0))  L_f  ,  SUM(DECODE(TA.BSTYP_CD,'I',1,0))  H_I  ,  SUM(DECODE(TA.BSTYP_CD,'K',1,0))  H_K  ,  SUM(DECODE(TA.BSTYP_CD,'M',1,0))  H_M  ,  SUM(DECODE(TA.BSTYP_CD,'N',1,0))  H_N  ,  SUM(DECODE(TA.BSTYP_CD,'O',1,0))  H_O  ,  SUM(DECODE(TA.BSTYP_CD,'L',1,0))  H_L  ,  SUM(DECODE(TA.BSTYP_CD,'t',1,0))  L_t  ,  SUM(CASE  WHEN  TA.BSTYP_CD  IN  ('G',  'H',  'g')  THEN  1  ELSE  0  END)  DC  ,  SUM(CASE  WHEN  TA.BSTYP_CD  IN  ('A',  'B',  'C',  'D',  'E')  THEN  1  ELSE  0  END)  DB  ,  SUM(CASE  WHEN  (  TA.BSTYP_CD  NOT  IN  ('o','y','J','f','l','k','p','n','m','r','q','w','G','H','g','I','K','M','N','O','L','u','s','x','A','B','C','D','E','t')   \n";
        query +=" OR  TA.BSTYP_CD  IS  NULL)  THEN  1  ELSE  0  END)  etc  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,   \n";
        query +=" (SELECT  DECODE(ATTE,  '세종특별자치시',  ATTE,  CITYCNTYDSRC)  AS  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  GUGUN  ,   \n";
        query +=" (SELECT  DECODE(TOWNTWSHP,  '',  COURT_NM,  TOWNTWSHP)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  DONG  ,  GIBU.GET_MNG_ZIP(UPSO_CD)  GUGUN_CD  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(UPSO_CD),1,1)  AS  BSTYP_CD  ,  STAFF_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NVL(STAFF_CD,'  ')  LIKE  :STAFF_CD||'%'   \n";
        query +=" AND  NEW_DAY  IS  NULL   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  TO_CHAR(SYSDATE,'YYYYMM'))  )  TA  GROUP  BY  TA.GUGUN,  TA.DONG,  TA.GUGUN_CD,  TA.STAFF_CD  ORDER  BY  TA.GUGUN_CD,  TA.DONG ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 전체&읍면동별현황
    public DOBJ CALLzip_mng_upsolist_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLzip_mng_upsolist_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLzip_mng_upsolist_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String YRMN ="";        //년월
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.GUGUN_CD  AS  MNG_ZIP  ,  TA.GUGUN,  TA.DONG  ,  FIDU.GET_STAFF_NM(TA.STAFF_CD)  AS  STAFF_NM  ,  COUNT(TA.UPSO_CD)  TOTAL  ,  SUM(DECODE(TA.BSTYP_CD,'o',1,0))  L_o  ,  SUM(DECODE(TA.BSTYP_CD,'k',1,0))  L_k  ,  SUM(DECODE(TA.BSTYP_CD,'l',1,0))  L_l  ,  SUM(DECODE(TA.BSTYP_CD,'q',1,0))  L_q  ,  SUM(DECODE(TA.BSTYP_CD,'r',1,0))  L_r  ,  SUM(DECODE(TA.BSTYP_CD,'u',1,0))  L_u  ,  SUM(DECODE(TA.BSTYP_CD,'s',1,0))  L_s  ,  SUM(DECODE(TA.BSTYP_CD,'n',1,0))  L_n  ,  SUM(DECODE(TA.BSTYP_CD,'m',1,0))  L_m  ,  SUM(DECODE(TA.BSTYP_CD,'p',1,0))  L_p  ,  SUM(DECODE(TA.BSTYP_CD,'w',1,0))  L_w  ,  SUM(DECODE(TA.BSTYP_CD,'x',1,0))  L_x  ,  SUM(DECODE(TA.BSTYP_CD,'v',1,0))  L_v  ,  SUM(DECODE(TA.BSTYP_CD,'y',1,0))  L_y  ,  SUM(DECODE(TA.BSTYP_CD,'J',1,0))  H_J  ,  SUM(DECODE(TA.BSTYP_CD,'f',1,0))  L_f  ,  SUM(DECODE(TA.BSTYP_CD,'I',1,0))  H_I  ,  SUM(DECODE(TA.BSTYP_CD,'K',1,0))  H_K  ,  SUM(DECODE(TA.BSTYP_CD,'M',1,0))  H_M  ,  SUM(DECODE(TA.BSTYP_CD,'N',1,0))  H_N  ,  SUM(DECODE(TA.BSTYP_CD,'O',1,0))  H_O  ,  SUM(DECODE(TA.BSTYP_CD,'L',1,0))  H_L  ,  SUM(DECODE(TA.BSTYP_CD,'t',1,0))  L_t  ,  SUM(CASE  WHEN  TA.BSTYP_CD  IN  ('G',  'H',  'g')  THEN  1  ELSE  0  END)  DC  ,  SUM(CASE  WHEN  TA.BSTYP_CD  IN  ('A',  'B',  'C',  'D',  'E')  THEN  1  ELSE  0  END)  DB  ,  SUM(CASE  WHEN  (  TA.BSTYP_CD  NOT  IN  ('o','y','J','f','l','k','p','n','m','r','q','w','G','H','g','I','K','M','N','O','L','u','s','x','A','B','C','D','E','t')   \n";
        query +=" OR  TA.BSTYP_CD  IS  NULL)  THEN  1  ELSE  0  END)  etc  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,   \n";
        query +=" (SELECT  DECODE(ATTE,  '세종특별자치시',  ATTE,  CITYCNTYDSRC)  AS  CITYCNTYDSRC  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  GUGUN  ,   \n";
        query +=" (SELECT  DECODE(TOWNTWSHP,  '',  COURT_NM,  TOWNTWSHP)  FROM  FIDU.TENV_POST  WHERE  BD_MNG_NUM  =  UPSO_BD_MNG_NUM)  DONG  ,  GIBU.GET_MNG_ZIP(UPSO_CD)  GUGUN_CD  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(UPSO_CD),1,1)  AS  BSTYP_CD  ,  STAFF_CD  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NVL(STAFF_CD,'  ')  LIKE  :STAFF_CD||'%'   \n";
        query +=" AND  (CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(CLSBS_INS_DAY,1,6),'  ')  >  TO_CHAR(SYSDATE,'YYYYMM'))  )  TA  GROUP  BY  TA.GUGUN,  TA.DONG,  TA.GUGUN_CD,  TA.STAFF_CD  ORDER  BY  TA.GUGUN_CD,  TA.DONG ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$zip_mng_upsolist
    //##**$$end
}
