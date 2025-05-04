
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra09_p02
{
    public bra09_p02()
    {
    }
    //##**$$mon_collect_list
    /*
    */
    public DOBJ CTLmon_collect_list(DOBJ dobj)
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
            dobj  = CALLmon_collect_list_SEL3(Conn, dobj);           //  분기
            if( dobj.getRetObject("SEL3").getRecord().get("YRMN").equals("1"))
            {
                dobj  = CALLmon_collect_list_SEL7(Conn, dobj);           //  월징수현황
                dobj  = CALLmon_collect_list_SEL8(Conn, dobj);           //  입금구분별현황
                dobj  = CALLmon_collect_list_SEL1( dobj);        //  월징수현황최종
                dobj  = CALLmon_collect_list_SEL2( dobj);        //  입금구분별현황최종
            }
            else if( dobj.getRetObject("SEL3").getRecord().get("YRMN").equals("2"))
            {
                dobj  = CALLmon_collect_list_SEL12(Conn, dobj);           //  월징수현황
                dobj  = CALLmon_collect_list_SEL13(Conn, dobj);           //  입금구분별현황
                dobj  = CALLmon_collect_list_SEL1( dobj);        //  월징수현황최종
                dobj  = CALLmon_collect_list_SEL2( dobj);        //  입금구분별현황최종
            }
            else
            {
                dobj  = CALLmon_collect_list_SEL5(Conn, dobj);           //  월징수현황
                dobj  = CALLmon_collect_list_SEL6(Conn, dobj);           //  입금구분별현황
                dobj  = CALLmon_collect_list_SEL1( dobj);        //  월징수현황최종
                dobj  = CALLmon_collect_list_SEL2( dobj);        //  입금구분별현황최종
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
    public DOBJ CTLmon_collect_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmon_collect_list_SEL3(Conn, dobj);           //  분기
        if( dobj.getRetObject("SEL3").getRecord().get("YRMN").equals("1"))
        {
            dobj  = CALLmon_collect_list_SEL7(Conn, dobj);           //  월징수현황
            dobj  = CALLmon_collect_list_SEL8(Conn, dobj);           //  입금구분별현황
            dobj  = CALLmon_collect_list_SEL1( dobj);        //  월징수현황최종
            dobj  = CALLmon_collect_list_SEL2( dobj);        //  입금구분별현황최종
        }
        else if( dobj.getRetObject("SEL3").getRecord().get("YRMN").equals("2"))
        {
            dobj  = CALLmon_collect_list_SEL12(Conn, dobj);           //  월징수현황
            dobj  = CALLmon_collect_list_SEL13(Conn, dobj);           //  입금구분별현황
            dobj  = CALLmon_collect_list_SEL1( dobj);        //  월징수현황최종
            dobj  = CALLmon_collect_list_SEL2( dobj);        //  입금구분별현황최종
        }
        else
        {
            dobj  = CALLmon_collect_list_SEL5(Conn, dobj);           //  월징수현황
            dobj  = CALLmon_collect_list_SEL6(Conn, dobj);           //  입금구분별현황
            dobj  = CALLmon_collect_list_SEL1( dobj);        //  월징수현황최종
            dobj  = CALLmon_collect_list_SEL2( dobj);        //  입금구분별현황최종
        }
        return dobj;
    }
    // 분기
    public DOBJ CALLmon_collect_list_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmon_collect_list_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmon_collect_list_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  (CASE  WHEN  :FROM_YRMN  >=  '201006'   \n";
        query +=" AND  :FROM_YRMN  <  '201206'  THEN  '1'  WHEN  :FROM_YRMN  >=  '201206'  THEN  '2'  ELSE  '3'  END)  AS  YRMN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        return sobj;
    }
    // 월징수현황
    // SI 오픈후에는 무대공연은 지부에 정보를 등록하지 않기 때문에 징수부에 있는 무대공연 데이타를 가져와서 통계를 보여줘야 한다 수정 : 2010.06.07  권남균 내용 : 징수금액에서 반환금을 제외하고 현황을 생성한다
    public DOBJ CALLmon_collect_list_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmon_collect_list_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmon_collect_list_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        String   REPT_GBN = dobj.getRetObject("S").getRecord().get("REPT_GBN");   //입금 구분
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BSTYP_NM  ,  MNG_CNT  ,  TOT_AMT  ,  COL_UPSO  ,  COL_AMT  ,  MAP_UPSO  ,  MAP_AMT  FROM(   \n";
        query +=" SELECT  NVL(TB.GRADNM,  '기타')  BSTYP_NM  ,  SUM(MNG_CNT)  MNG_CNT  ,  SUM(TOT_AMT)  TOT_AMT  ,  SUM(COL_UPSO)  COL_UPSO  ,  SUM(COL_AMT  -  RETURN_AMT)  COL_AMT  ,  SUM(MAP_UPSO)  MAP_UPSO  ,  SUM(MAP_AMT)  MAP_AMT  ,  SUM(RETURN_AMT)  RETURN_AMT  FROM(   \n";
        query +=" SELECT  TB.BSTYP_CD  ,  COUNT(*)  MNG_CNT  ,  SUM(TB.MONPRNCFEE)  TOT_AMT  ,  0  COL_UPSO  ,  0  COL_AMT  ,  0  MAP_UPSO  ,  0  MAP_AMT  ,  0  RETURN_AMT  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  TB  WHERE  TA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  TA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  TA.NEW_DAY  <=  :TO_YRMN||'31'   \n";
        query +=" AND  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(TA.CLSBS_INS_DAY,1,6),'  ')  >  :TO_YRMN)   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD  GROUP  BY  TB.BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  CC.BSTYP_CD  ,  0  MNG_CNT  ,  0  TOT_AMT  ,  COUNT(DISTINCT  AA.UPSO_CD)  ,  SUM(NVL(AA.REPT_AMT,0)  -  NVL(AA.COMIS,0))  ,  0  MAP_UPSO  ,  0  MAP_AMT  ,  0  RETURN_AMT  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_AMT  ,  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  DISTR_AMT  ,  0  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  AA  ,  GIBU.TBRA_UPSO  BB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  :TO_YRMN||'31'   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.UPSO_CD  =  AA.UPSO_CD  GROUP  BY  CC.BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  CC.BSTYP_CD  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  SUM(RETURN_AMT)  RETURN_AMT  FROM  GIBU.TBRA_REPT_RETURN  A  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.RETURN_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  A.UPSO_CD  =  CC.UPSO_CD  GROUP  BY  CC.BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  CC.BSTYP_CD  ,  0  ,  0  ,  0  ,  0  ,  COUNT(DISTINCT  AA.UPSO_CD)  ,  SUM(NVL(AA.REPT_AMT,0)  -  NVL(AA.COMIS,0))  ,  0  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_AMT  ,  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  SUBSTR(REPT_DAY,  1,  6)  <>  SUBSTR(MAPPING_DAY,  1,  6)  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  DISTR_AMT  ,  0  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  SUBSTR(REPT_DAY,  1,  6)  <>  SUBSTR(MAPPING_DAY,  1,  6)  )  AA  ,  GIBU.TBRA_UPSO  BB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  :TO_YRMN||'31'   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.UPSO_CD  =  AA.UPSO_CD  GROUP  BY  CC.BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  'v'  BSTYP_CD  ,  SUM(CNT)  MNG_CNT  ,  SUM(AMT)  MNG_AMT  ,  SUM(CNT)  COL_CNT  ,  SUM(AMT)  COL_AMT  ,  0  MAP_UPSO  ,  0  MAP_AMT  ,  0  FROM(   \n";
        query +=" SELECT  COUNT(A.DEMD_NUM)  CNT  ,  SUM(NVL(A.REPT_AMT,0)-NVL(A.ATAX_AMT,0))  AMT  FROM  FIDU.TLEV_USEDEMDREPT  A  WHERE  A.REPT_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.MDM_CD  LIKE  'DA%'   \n";
        query +=" AND  DECODE(:REPT_GBN,NULL,'03','03','03','X')  =  '03'  )  )  TA  ,  GIBU.TBRA_BSTYPGRAD  TB  WHERE  TB.BSTYP_CD(+)  =  'z'   \n";
        query +=" AND  TB.GRAD_GBN(+)  =  TA.BSTYP_CD  GROUP  BY  NVL(TB.GRADNM,  '기타')  )  WHERE  MNG_CNT  IS  NOT  NULL   \n";
        query +=" AND  COL_UPSO  IS  NOT  NULL  ORDER  BY  MNG_CNT  DESC ";
        sobj.setSql(query);
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 입금구분별현황
    // SI 오픈후에는 무대공연은 지부에 정보를 등록하지 않기 때문에 징수부에 있는 무대공연 데이타를 가져와서 보여준다 수정 : 2010.06.07  권남균 내용 : 입금매체에서 반환금 생성한다
    public DOBJ CALLmon_collect_list_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmon_collect_list_SEL8(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        rvobj.Println("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmon_collect_list_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        String   REPT_GBN = dobj.getRetObject("S").getRecord().get("REPT_GBN");   //입금 구분
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BSTYP_NM  ,  SUM(COUNT2)  COL_UPSO  ,  SUM(AMT2)  COL_AMT  ,  0  MAP_UPSO  ,  0  MAP_AMT  FROM  (   \n";
        query +=" SELECT  CC.CODE_NM  BSTYP_NM  ,  COUNT(DISTINCT  AA.UPSO_CD)  COUNT2  ,  SUM(NVL(AA.REPT_AMT,0))  -  SUM(NVL(AA.COMIS,0))  AMT2  FROM  (   \n";
        query +=" SELECT  REPT_AMT  ,  COMIS  ,  UPSO_CD  ,  REPT_GBN  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  0  ,  UPSO_CD  ,  REPT_GBN  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  AA  ,  GIBU.TBRA_UPSO  BB  ,  FIDU.TENV_CODE  CC  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  :TO_YRMN||'31'   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.HIGH_CD  =  '00141'   \n";
        query +=" AND  CC.CODE_ETC  =  'A'   \n";
        query +=" AND  CC.CODE_CD  =  AA.REPT_GBN  GROUP  BY  CC.CODE_NM,  AA.REPT_GBN  UNION  ALL   \n";
        query +=" SELECT  AA.CODE_NM  BSTYP_NM  ,  COUNT2  ,  AMT2  FROM  FIDU.TENV_CODE  AA  ,  (   \n";
        query +=" SELECT  '03'  REPT_GBN  ,  COUNT(A.DEMD_NUM)  COUNT2  ,  SUM(NVL(A.REPT_AMT,0)  -  NVL(A.ATAX_AMT,0))  AMT2  FROM  FIDU.TLEV_USEDEMDREPT  A  WHERE  A.REPT_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.MDM_CD  LIKE  'DA%'   \n";
        query +=" AND  DECODE(:REPT_GBN,NULL,'03','03','03','X')  =  '03'  GROUP  BY  '03'  )  BB  WHERE  AA.HIGH_CD  =  '00141'   \n";
        query +=" AND  AA.CODE_ETC  =  'A'   \n";
        query +=" AND  BB.REPT_GBN  =  AA.CODE_CD  UNION  ALL   \n";
        query +=" SELECT  '협회입금반환'  BSTYP_NM  ,  COUNT(DISTINCT  UPSO_CD)  COUNT2  ,  -SUM(RETURN_AMT)  AMT2  FROM  GIBU.TBRA_REPT_RETURN  A  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.RETURN_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'  UNION  ALL  --무대공연  입금반환분  협회  입금반환에서  한번  빼고,  무통장입금내역에서는  더해야함   \n";
        query +=" SELECT  '무통장'  BSTYP_NM  ,  COUNT(DISTINCT  UPSO_CD)  COUNT2  ,  SUM(RETURN_AMT)  AMT2  FROM  GIBU.TBRA_REPT_RETURN  A  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.RETURN_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  A.UPSO_CD  IS  NULL  )  WHERE  AMT2  <>  0  GROUP  BY  BSTYP_NM  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 월징수현황최종
    public DOBJ CALLmon_collect_list_SEL1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("SEL1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL5, SEL7, SEL12","");
        rvobj.setName("SEL1") ;
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 입금구분별현황최종
    public DOBJ CALLmon_collect_list_SEL2(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("SEL2");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL6, SEL8, SEL13","");
        rvobj.setName("SEL2") ;
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 월징수현황
    // 201206월부터 지부영화(포토앨범)은 지부에 정보를 등록하지 않기 때문에 징수부에 있는 지부영화 데이타를 가져와서 통계를 보여줘야 한다 수정 : 2012.06.15  김은정 내용 : 징수금액에서 반환금을 제외하고 현황을 생성한다  RIKA(음산협) => KEPA(연제협) 으로 변경(21.01.인데 22.10. 수정)
    public DOBJ CALLmon_collect_list_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmon_collect_list_SEL12(dobj, dvobj);
        VOBJ rvobj = qexe.executeSql(Conn, sobj);
        rvobj.setName("SEL12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmon_collect_list_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        String   REPT_GBN = dobj.getRetObject("S").getRecord().get("REPT_GBN");   //입금 구분
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  /*+  optimizer_features_enable('11.1.0.6')  */  BSTYP_NM  ,  MNG_CNT  ,  TOT_AMT  ,  COL_UPSO  ,  COL_AMT  ,  MAP_UPSO  ,  MAP_AMT  ,  COL_AMT  -  (KOSCAP_AMT  +  FKMP_AMT  +  RIAK_AMT)  AS  KOMCA_AMT  ,  MAP_AMT  -  (KOSCAP_MAP  +  FKMP_MAP  +  RIAK_MAP)  AS  KOMCA_MAP  ,  KOSCAP_AMT  ,  KOSCAP_MAP  ,  FKMP_AMT  ,  FKMP_MAP  ,  RIAK_AMT  ,  RIAK_MAP  ,  STOMU_AMT  FROM  (   \n";
        query +=" SELECT  NVL(TB.GRADNM,  '기타')  AS  BSTYP_NM  ,  NVL(TB.STNDAREA_START,  99)  AS  ORDER_SEQ1  ,  NVL(TB.MDM_CD,  'Z')  AS  ORDER_SEQ2  ,  NVL(SUM(MNG_CNT),  0)  AS  MNG_CNT  ,  NVL(SUM(TOT_AMT),  0)  AS  TOT_AMT  ,  NVL(SUM(COL_UPSO),  0)  AS  COL_UPSO  ,  NVL(SUM(COL_AMT  -  RETURN_AMT),  0)  AS  COL_AMT  ,  NVL(SUM(MAP_UPSO),  0)  AS  MAP_UPSO  ,  NVL(SUM(MAP_AMT),  0)  AS  MAP_AMT  ,  NVL(SUM(RETURN_AMT),  0)  AS  RETURN_AMT  ,  NVL(SUM(KOSCAP_AMT  -  KOSCAP_RETURN),  0)  AS  KOSCAP_AMT  ,  NVL(SUM(KOSCAP_MAP),  0)  AS  KOSCAP_MAP  ,  NVL(SUM(FKMP_AMT  -  FKMP_RETURN),  0)  AS  FKMP_AMT  ,  NVL(SUM(FKMP_MAP),  0)  AS  FKMP_MAP  ,  NVL(SUM(RIAK_AMT  -  RIAK_RETURN),  0)  AS  RIAK_AMT  ,  NVL(SUM(RIAK_MAP),  0)  AS  RIAK_MAP  ,  NVL(SUM(STOMU_AMT),  0)  AS  STOMU_AMT  FROM  (   \n";
        query +=" SELECT  TB.BSTYP_CD  ,  COUNT(*)  AS  MNG_CNT  ,  SUM(TB.MONPRNCFEE)  AS  TOT_AMT  ,  0  AS  COL_UPSO  ,  0  AS  COL_AMT  ,  0  AS  MAP_UPSO  ,  0  AS  MAP_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  KOSCAP_MAP  ,  0  AS  KOSCAP_RETURN  ,  0  AS  FKMP_AMT  ,  0  AS  FKMP_MAP  ,  0  AS  FKMP_RETURN  ,  0  AS  RIAK_AMT  ,  0  AS  RIAK_MAP  ,  0  AS  RIAK_RETURN  ,  0  AS  STOMU_AMT  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  TB  WHERE  TA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  TA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  TA.NEW_DAY  <=  :TO_YRMN||'31'   \n";
        query +=" AND  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(TA.CLSBS_INS_DAY,1,6),'  ')  >  :TO_YRMN)   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TB.BSTYP_CD  <>  't'  GROUP  BY  TB.BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  CC.BSTYP_CD  ,  0  AS  MNG_CNT  ,  0  AS  TOT_AMT  ,  COUNT(DISTINCT  AA.UPSO_CD)  AS  COL_UPSO  ,  SUM(NVL(AA.REPT_AMT,0)  -  NVL(AA.COMIS,0))  AS  COL_AMT  ,  0  AS  MAP_UPSO  ,  0  AS  MAP_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  KOSCAP_MAP  ,  0  AS  KOSCAP_RETURN  ,  0  AS  FKMP_AMT  ,  0  AS  FKMP_MAP  ,  0  AS  FKMP_RETURN  ,  0  AS  RIAK_AMT  ,  0  AS  RIAK_MAP  ,  0  AS  RIAK_RETURN  ,  0  AS  STOMU_AMT  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_AMT  ,  COMIS  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  DISTR_AMT  ,  0  ,  UPSO_CD  ,  BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  REPT_GBN  <>  '08'  UNION  ALL   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  (CASE  WHEN  MIN(DISTR_SEQ)  OVER(PARTITION  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN)  =  DISTR_SEQ  THEN  A.DISTR_AMT  -  NVL(C.COMIS,  0)  ELSE  A.DISTR_AMT  END)  AS  REPT_AMT  ,  0  ,  A.UPSO_CD  ,  A.BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_REPT_VIRTUAL  B  ,  GIBU.TBRA_REPT  C  WHERE  A.MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  '08'   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  C.REPT_GBN(+)  =  '08'  )  AA  ,  GIBU.TBRA_UPSO  BB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  AA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  AA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  :TO_YRMN||'31'   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.BSTYP_CD  <>  't'  GROUP  BY  CC.BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  CC.BSTYP_CD  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  SUM(RETURN_AMT)  RETURN_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  KOSCAP_MAP  ,  0  AS  KOSCAP_RETURN  ,  0  AS  FKMP_AMT  ,  0  AS  FKMP_MAP  ,  0  AS  FKMP_RETURN  ,  0  AS  RIAK_AMT  ,  0  AS  RIAK_MAP  ,  0  AS  RIAK_RETURN  ,  0  AS  STOMU_AMT  FROM  GIBU.TBRA_REPT_RETURN  A  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.RETURN_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  A.UPSO_CD  =  CC.UPSO_CD   \n";
        query +=" AND  CC.BSTYP_CD  <>  't'  GROUP  BY  CC.BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  CC.BSTYP_CD  ,  0  ,  0  ,  0  ,  0  ,  COUNT(DISTINCT  AA.UPSO_CD)  AS  MAP_UPSO  ,  SUM(NVL(AA.REPT_AMT,0)  -  NVL(AA.COMIS,0))  AS  MAP_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  KOSCAP_MAP  ,  0  AS  KOSCAP_RETURN  ,  0  AS  FKMP_AMT  ,  0  AS  FKMP_MAP  ,  0  AS  FKMP_RETURN  ,  0  AS  RIAK_AMT  ,  0  AS  RIAK_MAP  ,  0  AS  RIAK_RETURN  ,  0  AS  STOMU_AMT  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_AMT  ,  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  '03'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  SUBSTR(REPT_DAY,  1,  6)  <  SUBSTR(MAPPING_DAY,  1,  6)  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  DISTR_AMT  ,  0  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  '03'   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  SUBSTR(REPT_DAY,  1,  6)  <  SUBSTR(MAPPING_DAY,  1,  6)  )  AA  ,  GIBU.TBRA_UPSO  BB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  AS  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  :TO_YRMN||'31'   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.BSTYP_CD  <>  't'  GROUP  BY  CC.BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  't'  AS  BSTYP_CD  ,  SUM(CNT)  AS  MNG_CNT  ,  SUM(AMT)  AS  MNG_AMT  ,  SUM(CNT)  AS  COL_CNT  ,  SUM(AMT)  AS  COL_AMT  ,  0  AS  MAP_UPSO  ,  0  AS  MAP_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  KOSCAP_MAP  ,  0  AS  KOSCAP_RETURN  ,  0  AS  FKMP_AMT  ,  0  AS  FKMP_MAP  ,  0  AS  FKMP_RETURN  ,  0  AS  RIAK_AMT  ,  0  AS  RIAK_MAP  ,  0  AS  RIAK_RETURN  ,  0  AS  STOMU_AMT  FROM  (   \n";
        query +=" SELECT  COUNT(A.DEMD_NUM)  AS  CNT  ,  SUM(NVL(A.REPT_AMT,0)-NVL(A.ATAX_AMT,0))  AS  AMT  FROM  FIDU.TLEV_USEDEMDREPT  A  WHERE  A.REPT_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.MDM_CD  LIKE  'CC04%'   \n";
        query +=" AND  DECODE(:REPT_GBN,NULL,'03','03','03','X')  =  '03'  )  UNION  ALL   \n";
        query +=" SELECT  BSTYP_CD  ,  0  AS  MNG_CNT  ,  0  AS  MNG_AMT  ,  0  AS  COL_CNT  ,  0  AS  COL_AMT  ,  0  AS  MAP_UPSO  ,  0  AS  MAP_AMT  ,  0  AS  RETURN_AMT  ,  SUM(PROC_AMT)  AS  KOSCAP_AMT  ,  0  AS  KOSCAP_MAP  ,  0  AS  KOSCAP_RETURN  ,  0  AS  FKMP_AMT  ,  0  AS  FKMP_MAP  ,  0  AS  FKMP_RETURN  ,  0  AS  RIAK_AMT  ,  0  AS  RIAK_MAP  ,  0  AS  RIAK_RETURN  ,  0  AS  STOMU_AMT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BSCON_CD  =  'T0000001'  GROUP  BY  BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  BSTYP_CD  ,  0  AS  MNG_CNT  ,  0  AS  MNG_AMT  ,  0  AS  COL_CNT  ,  0  AS  COL_AMT  ,  0  AS  MAP_UPSO  ,  0  AS  MAP_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  KOSCAP_AMT  ,  SUM(PROC_AMT)  AS  KOSCAP_MAP  ,  0  AS  KOSCAP_RETURN  ,  0  AS  FKMP_AMT  ,  0  AS  FKMP_MAP  ,  0  AS  FKMP_RETURN  ,  0  AS  RIAK_AMT  ,  0  AS  RIAK_MAP  ,  0  AS  RIAK_RETURN  ,  0  AS  STOMU_AMT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  MAPPING_YRMN  >  REPT_YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000001'   \n";
        query +=" AND  REPT_GBN  =  '03'  GROUP  BY  BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  BSTYP_CD  ,  0  AS  MNG_CNT  ,  0  AS  MNG_AMT  ,  0  AS  COL_CNT  ,  0  AS  COL_AMT  ,  0  AS  MAP_UPSO  ,  0  AS  MAP_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  KOSCAP_MAP  ,  SUM(PROC_AMT)  AS  KOSCAP_RETURN  ,  0  AS  FKMP_AMT  ,  0  AS  FKMP_MAP  ,  0  AS  FKMP_RETURN  ,  0  AS  RIAK_AMT  ,  0  AS  RIAK_MAP  ,  0  AS  RIAK_RETURN  ,  0  AS  STOMU_AMT  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BSCON_CD  =  'T0000001'  GROUP  BY  BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  BSTYP_CD  ,  0  AS  MNG_CNT  ,  0  AS  MNG_AMT  ,  0  AS  COL_CNT  ,  0  AS  COL_AMT  ,  0  AS  MAP_UPSO  ,  0  AS  MAP_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  KOSCAP_MAP  ,  0  AS  KOSCAP_RETURN  ,  SUM(PROC_AMT)  AS  FKMP_AMT  ,  0  AS  FKMP_MAP  ,  0  AS  FKMP_RETURN  ,  0  AS  RIAK_AMT  ,  0  AS  RIAK_MAP  ,  0  AS  RIAK_RETURN  ,  0  AS  STOMU_AMT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BSCON_CD  =  'T0000002'  GROUP  BY  BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  BSTYP_CD  ,  0  AS  MNG_CNT  ,  0  AS  MNG_AMT  ,  0  AS  COL_CNT  ,  0  AS  COL_AMT  ,  0  AS  MAP_UPSO  ,  0  AS  MAP_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  KOSCAP_MAP  ,  0  AS  KOSCAP_RETURN  ,  0  AS  FKMP_AMT  ,  SUM(PROC_AMT)  AS  FKMP_MAP  ,  0  AS  FKMP_RETURN  ,  0  AS  RIAK_AMT  ,  0  AS  RIAK_MAP  ,  0  AS  RIAK_RETURN  ,  0  AS  STOMU_AMT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  MAPPING_YRMN  >  REPT_YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000002'   \n";
        query +=" AND  REPT_GBN  =  '03'  GROUP  BY  BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  BSTYP_CD  ,  0  AS  MNG_CNT  ,  0  AS  MNG_AMT  ,  0  AS  COL_CNT  ,  0  AS  COL_AMT  ,  0  AS  MAP_UPSO  ,  0  AS  MAP_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  KOSCAP_MAP  ,  0  AS  KOSCAP_RETURN  ,  0  AS  FKMP_AMT  ,  0  AS  FKMP_MAP  ,  SUM(PROC_AMT)  AS  FKMP_RETURN  ,  0  AS  RIAK_AMT  ,  0  AS  RIAK_MAP  ,  0  AS  RIAK_RETURN  ,  0  AS  STOMU_AMT  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BSCON_CD  =  'T0000002'  GROUP  BY  BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  BSTYP_CD  ,  0  AS  MNG_CNT  ,  0  AS  MNG_AMT  ,  0  AS  COL_CNT  ,  0  AS  COL_AMT  ,  0  AS  MAP_UPSO  ,  0  AS  MAP_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  KOSCAP_MAP  ,  0  AS  KOSCAP_RETURN  ,  0  AS  FKMP_AMT  ,  0  AS  FKMP_MAP  ,  0  AS  FKMP_RETURN  ,  SUM(PROC_AMT)  AS  RIAK_AMT  ,  0  AS  RIAK_MAP  ,  0  AS  RIAK_RETURN  ,  0  AS  STOMU_AMT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BSCON_CD  =  'T0000003'  GROUP  BY  BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  BSTYP_CD  ,  0  AS  MNG_CNT  ,  0  AS  MNG_AMT  ,  0  AS  COL_CNT  ,  0  AS  COL_AMT  ,  0  AS  MAP_UPSO  ,  0  AS  MAP_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  KOSCAP_MAP  ,  0  AS  KOSCAP_RETURN  ,  0  AS  FKMP_AMT  ,  0  AS  FKMP_MAP  ,  0  AS  FKMP_RETURN  ,  0  AS  RIAK_AMT  ,  SUM(PROC_AMT)  AS  RIAK_MAP  ,  0  AS  RIAK_RETURN  ,  0  AS  STOMU_AMT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  MAPPING_YRMN  >  REPT_YRMN   \n";
        query +=" AND  BSCON_CD  =  'T0000003'   \n";
        query +=" AND  REPT_GBN  =  '03'  GROUP  BY  BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  BSTYP_CD  ,  0  AS  MNG_CNT  ,  0  AS  MNG_AMT  ,  0  AS  COL_CNT  ,  0  AS  COL_AMT  ,  0  AS  MAP_UPSO  ,  0  AS  MAP_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  KOSCAP_MAP  ,  0  AS  KOSCAP_RETURN  ,  0  AS  FKMP_AMT  ,  0  AS  FKMP_MAP  ,  0  AS  FKMP_RETURN  ,  0  AS  RIAK_AMT  ,  0  AS  RIAK_MAP  ,  SUM(PROC_AMT)  AS  RIAK_RETURN  ,  0  AS  STOMU_AMT  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BSCON_CD  =  'T0000003'  GROUP  BY  BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  CC.BSTYP_CD  ,  0  AS  MNG_CNT  ,  0  AS  TOT_AMT  ,  0  AS  COL_UPSO  ,  0  AS  COL_AMT  ,  0  AS  MAP_UPSO  ,  0  AS  MAP_AMT  ,  0  AS  RETURN_AMT  ,  0  AS  KOSCAP_AMT  ,  0  AS  KOSCAP_MAP  ,  0  AS  KOSCAP_RETURN  ,  0  AS  FKMP_AMT  ,  0  AS  FKMP_MAP  ,  0  AS  FKMP_RETURN  ,  0  AS  RIAK_AMT  ,  0  AS  RIAK_MAP  ,  0  AS  RIAK_RETURN  ,  SUM(NVL(AA.REPT_AMT,0)  -  NVL(AA.COMIS,0))  AS  STOMU_AMT  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_AMT  ,  COMIS  ,  UPSO_CD  ,  BRAN_CD  ,  MAPPING_DAY  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  DISTR_AMT  ,  0  ,  UPSO_CD  ,  BRAN_CD  ,  MAPPING_DAY  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  REPT_GBN  <>  '08'  UNION  ALL   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  (CASE  WHEN  MIN(DISTR_SEQ)  OVER(PARTITION  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN)  =  DISTR_SEQ  THEN  A.DISTR_AMT  -  NVL(C.COMIS,  0)  ELSE  A.DISTR_AMT  END)  AS  REPT_AMT  ,  0  ,  A.UPSO_CD  ,  A.BRAN_CD  ,  A.MAPPING_DAY  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_REPT_VIRTUAL  B  ,  GIBU.TBRA_REPT  C  WHERE  A.MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  '08'   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  C.REPT_GBN(+)  =  '08'  )  AA  ,  GIBU.TBRA_UPSO  BB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  ,  ((SELECT  BSCON_CD,  BSCON_UPSO_CD,  UPSO_CD  AS  KOMCA_UPSO_CD,  TRUNC(INS_DATE)  AS  START_DATE,  TO_DATE('9999-12-31',  'YYYY-MM-DD')  AS  END_DATE  FROM  GIBU.TBRA_STOMU_CONTRINFO  WHERE  MOD_DATE  IS  NULL   \n";
        query +=" AND  UPSO_CD  IS  NOT  NULL)  UNION   \n";
        query +=" (SELECT  A.BSCON_CD,  A.BSCON_UPSO_CD,  A.AFT_CNG  AS  KOMCA_UPSO_CD,  A.INS_DATE  AS  START_DATE,  LEAD(A.INS_DATE,  1,  TO_DATE('9999-12-31',  'YYYY-MM-DD'))  OVER  (PARTITION  BY  A.BSCON_CD,  A.BSCON_UPSO_CD  ORDER  BY  A.SEQ)  AS  END_DATE  FROM  (   \n";
        query +=" SELECT  BSCON_CD,  BSCON_UPSO_CD,  SEQ,  CNG_COL,  BEFO_CNG,  AFT_CNG,  TRUNC(INS_DATE)  AS  INS_DATE  FROM  GIBU.TBRA_STOMU_HISTY  WHERE  1=1   \n";
        query +=" AND  CNG_COL  =  '업소코드'  )  A  WHERE  A.AFT_CNG  IS  NOT  NULL  ))  DD  ,   \n";
        query +=" (SELECT  BSCON_CD,  BSCONHAN_NM,  USE_YN  FROM  GIBU.TBRA_BSCON_STOMU  WHERE  USE_YN  =  'Y'   \n";
        query +=" AND  BSCON_CD  IN  ('04225'  ,'22454'  ,'H0596'  ,'H0739'  ,'G0278'  ,'06001'  ,'02316'  ,'G0259'  ,'G0327'  ,'37227'  ,'35194'  ,'23247'))  EE  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  AA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  AA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  :TO_YRMN||'31'   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  DD.KOMCA_UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  AA.MAPPING_DAY  BETWEEN  DD.START_DATE   \n";
        query +=" AND  DD.END_DATE   \n";
        query +=" AND  DD.BSCON_CD  =  EE.BSCON_CD   \n";
        query +=" AND  EE.USE_YN  =  'Y'   \n";
        query +=" AND  CC.BSTYP_CD  <>  't'  GROUP  BY  CC.BSTYP_CD  )  TA  FULL  OUTER  JOIN   \n";
        query +=" (SELECT  BSTYP_CD,  GRAD_GBN,  GRADNM,  STNDAREA_START,  MDM_CD  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z')  TB  ON  (TB.GRAD_GBN  =  TA.BSTYP_CD)  GROUP  BY  NVL(TB.GRADNM,  '기타'),  NVL(TB.STNDAREA_START,  99),  NVL(TB.MDM_CD,  'Z')  )  ORDER  BY  ORDER_SEQ1,  ORDER_SEQ2 ";
        sobj.setSql(query);
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 입금구분별현황
    // 2012.06월부터 지부영화(포토앨범)은 지부에 정보를 등록하지 않기 때문에 징수부에 있는 지부영화 데이타를 가져와서 보여준다 수정 : 2012.06.15  김은정 내용 : 입금매체에서 반환금 생성한다
    public DOBJ CALLmon_collect_list_SEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL13");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL13");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmon_collect_list_SEL13(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL13");
        rvobj.Println("SEL13");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmon_collect_list_SEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        String   REPT_GBN = dobj.getRetObject("S").getRecord().get("REPT_GBN");   //입금 구분
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BSTYP_NM  ,  SUM(COUNT2)  AS  COL_UPSO  ,  SUM(AMT2)  AS  COL_AMT  ,  0  AS  MAP_UPSO  ,  0  AS  MAP_AMT  FROM  (   \n";
        query +=" SELECT  CC.CODE_NM  AS  BSTYP_NM  ,  COUNT(DISTINCT  AA.UPSO_CD)  AS  COUNT2  ,  SUM(NVL(AA.REPT_AMT,0))  -  SUM(NVL(AA.COMIS,0))  AS  AMT2  ,  1  AS  SRT  FROM  (   \n";
        query +=" SELECT  REPT_AMT  ,  COMIS  ,  UPSO_CD  ,  REPT_GBN  ,  BRAN_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  0  ,  UPSO_CD  ,  REPT_GBN  ,  BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  REPT_GBN  <>  '08'  UNION  ALL   \n";
        query +=" SELECT  (CASE  WHEN  MIN(DISTR_SEQ)  OVER(PARTITION  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN)  =  DISTR_SEQ  THEN  A.DISTR_AMT  -  NVL(C.COMIS,  0)  ELSE  A.DISTR_AMT  END)  AS  REPT_AMT  ,  0  ,  A.UPSO_CD  ,  '08'  ,  A.BRAN_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_REPT_VIRTUAL  B  ,  GIBU.TBRA_REPT  C  WHERE  A.MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  '08'   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  C.REPT_GBN(+)  =  '08'  )  AA  ,  GIBU.TBRA_UPSO  BB  ,  FIDU.TENV_CODE  CC  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  AA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  AA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  :TO_YRMN||'31'   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.HIGH_CD  =  '00141'   \n";
        query +=" AND  CC.CODE_ETC  =  'A'   \n";
        query +=" AND  CC.CODE_CD  =  AA.REPT_GBN  GROUP  BY  CC.CODE_NM,  AA.REPT_GBN  UNION  ALL   \n";
        query +=" SELECT  AA.CODE_NM  AS  BSTYP_NM  ,  COUNT2  ,  AMT2  ,  3  AS  SRT  FROM  FIDU.TENV_CODE  AA  ,  (   \n";
        query +=" SELECT  '03'  AS  REPT_GBN  ,  COUNT(A.DEMD_NUM)  AS  COUNT2  ,  SUM(NVL(A.REPT_AMT,0)  -  NVL(A.ATAX_AMT,0))  AS  AMT2  FROM  FIDU.TLEV_USEDEMDREPT  A  WHERE  A.REPT_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.MDM_CD  LIKE  'CC04%'   \n";
        query +=" AND  DECODE(:REPT_GBN,NULL,'03','03','03','X')  =  '03'  GROUP  BY  '03'  )  BB  WHERE  AA.HIGH_CD  =  '00141'   \n";
        query +=" AND  AA.CODE_ETC  =  'A'   \n";
        query +=" AND  BB.REPT_GBN  =  AA.CODE_CD  UNION  ALL   \n";
        query +=" SELECT  '협회입금반환'  AS  BSTYP_NM  ,  COUNT(DISTINCT  UPSO_CD)  AS  COUNT2  ,  -SUM(RETURN_AMT)  AS  AMT2  ,  5  AS  SRT  FROM  GIBU.TBRA_REPT_RETURN  A  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.RETURN_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'  UNION  ALL   \n";
        query +=" SELECT  '무통장'  AS  BSTYP_NM  ,  COUNT(DISTINCT  UPSO_CD)  AS  COUNT2  ,  SUM(RETURN_AMT)  AS  AMT2  ,  4  AS  SRT  FROM  GIBU.TBRA_REPT_RETURN  A  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.RETURN_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  A.UPSO_CD  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  '함저협분배'  AS  BSTYP_NM  ,  COUNT(DISTINCT  UPSO_CD)  AS  COUNT2  ,  -SUM(PROC_AMT)  AS  AMT2  ,  6  AS  SRT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BSCON_CD  =  'T0000001'   \n";
        query +=" AND  MAPPING_YRMN  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN  UNION  ALL   \n";
        query +=" SELECT  '함저협입금반환'  AS  BSTYP_NM  ,  COUNT(DISTINCT  UPSO_CD)  AS  COUNT2  ,  SUM(PROC_AMT)  AS  AMT2  ,  7  AS  SRT  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BSCON_CD  =  'T0000001'   \n";
        query +=" AND  RETURN_YRMN  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN  UNION  ALL   \n";
        query +=" SELECT  '음실련분배'  AS  BSTYP_NM  ,  COUNT(DISTINCT  UPSO_CD)  AS  COUNT2  ,  -SUM(PROC_AMT)  AS  AMT2  ,  8  AS  SRT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BSCON_CD  =  'T0000002'   \n";
        query +=" AND  MAPPING_YRMN  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN  UNION  ALL   \n";
        query +=" SELECT  '음실련입금반환'  AS  BSTYP_NM  ,  COUNT(DISTINCT  UPSO_CD)  AS  COUNT2  ,  SUM(PROC_AMT)  AS  AMT2  ,  9  AS  SRT  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BSCON_CD  =  'T0000002'   \n";
        query +=" AND  RETURN_YRMN  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN  UNION  ALL   \n";
        query +=" SELECT  '음산협분배'  AS  BSTYP_NM  ,  COUNT(DISTINCT  UPSO_CD)  AS  COUNT2  ,  -SUM(PROC_AMT)  AS  AMT2  ,  10  AS  SRT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BSCON_CD  =  'T0000003'   \n";
        query +=" AND  MAPPING_YRMN  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN  UNION  ALL   \n";
        query +=" SELECT  '음산협입금반환'  AS  BSTYP_NM  ,  COUNT(DISTINCT  UPSO_CD)  AS  COUNT2  ,  SUM(PROC_AMT)  AS  AMT2  ,  11  AS  SRT  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BSCON_CD  =  'T0000003'   \n";
        query +=" AND  RETURN_YRMN  BETWEEN  :FROM_YRMN   \n";
        query +=" AND  :TO_YRMN  )  WHERE  AMT2  <>  0  GROUP  BY  BSTYP_NM  ORDER  BY  BSTYP_NM ";
        sobj.setSql(query);
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 월징수현황
    // 지부에 있는 무대공연으로 등록된 업소의 입금 정보를 가져온다
    public DOBJ CALLmon_collect_list_SEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL5");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL5");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmon_collect_list_SEL5(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmon_collect_list_SEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        String   REPT_GBN = dobj.getRetObject("S").getRecord().get("REPT_GBN");   //입금 구분
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BSTYP_NM  ,  MNG_CNT  ,  TOT_AMT  ,  COL_UPSO  ,  COL_AMT  ,  MAP_UPSO  ,  MAP_AMT  FROM(   \n";
        query +=" SELECT  NVL(TB.GRADNM,  '기타')  BSTYP_NM  ,  SUM(MNG_CNT)  MNG_CNT  ,  SUM(TOT_AMT)  TOT_AMT  ,  SUM(COL_UPSO)  COL_UPSO  ,  SUM(COL_AMT  -  RETURN_AMT)  COL_AMT  ,  SUM(MAP_UPSO)  MAP_UPSO  ,  SUM(MAP_AMT)  MAP_AMT  FROM(   \n";
        query +=" SELECT  TB.BSTYP_CD  ,  COUNT(*)  MNG_CNT  ,  SUM(TB.MONPRNCFEE)  TOT_AMT  ,  0  COL_UPSO  ,  0  COL_AMT  ,  0  MAP_UPSO  ,  0  MAP_AMT  ,  0  RETURN_AMT  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  TB  WHERE  TA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  TA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TA.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  TA.NEW_DAY  <=  :TO_YRMN||'31'   \n";
        query +=" AND  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(TA.CLSBS_INS_DAY,1,6),'  ')  >  :TO_YRMN)   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD  GROUP  BY  TB.BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  CC.BSTYP_CD  ,  0  MNG_CNT  ,  0  TOT_AMT  ,  COUNT(DISTINCT  AA.UPSO_CD)  ,  SUM(NVL(AA.REPT_AMT,0)  -  NVL(AA.COMIS,0))  ,  0  MAP_UPSO  ,  0  MAP_AMT  ,  0  RETURN_AMT  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_AMT  ,  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  DISTR_AMT  ,  0  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  AA  ,  GIBU.TBRA_UPSO  BB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  :TO_YRMN||'31'   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.UPSO_CD  =  AA.UPSO_CD  GROUP  BY  CC.BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  CC.BSTYP_CD  ,  0  ,  0  ,  0  ,  0  ,  0  ,  0  ,  SUM(RETURN_AMT)  RETURN_AMT  FROM  GIBU.TBRA_REPT_RETURN  A  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.RETURN_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  A.UPSO_CD  =  CC.UPSO_CD  GROUP  BY  CC.BSTYP_CD  UNION  ALL   \n";
        query +=" SELECT  CC.BSTYP_CD  ,  0  ,  0  ,  0  ,  0  ,  COUNT(DISTINCT  AA.UPSO_CD)  ,  SUM(NVL(AA.REPT_AMT,0)  -  NVL(AA.COMIS,0))  ,  0  RETURN_AMT  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_AMT  ,  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  SUBSTR(REPT_DAY,  1,  6)  <>  SUBSTR(MAPPING_DAY,  1,  6)  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  DISTR_AMT  ,  0  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  SUBSTR(REPT_DAY,  1,  6)  <>  SUBSTR(MAPPING_DAY,  1,  6)  )  AA  ,  GIBU.TBRA_UPSO  BB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  :TO_YRMN||'31'   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.UPSO_CD  =  AA.UPSO_CD  GROUP  BY  CC.BSTYP_CD  )  TA  ,  GIBU.TBRA_BSTYPGRAD  TB  WHERE  TB.BSTYP_CD(+)  =  'z'   \n";
        query +=" AND  TB.GRAD_GBN(+)  =  TA.BSTYP_CD  GROUP  BY  NVL(TB.GRADNM,  '기타')  )  WHERE  MNG_CNT  IS  NOT  NULL   \n";
        query +=" AND  COL_UPSO  IS  NOT  NULL  ORDER  BY  MNG_CNT  DESC ";
        sobj.setSql(query);
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 입금구분별현황
    // 지부에 있는 무대공연으로 등록된 업소의 입금 정보를 가져온다.
    public DOBJ CALLmon_collect_list_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmon_collect_list_SEL6(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmon_collect_list_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        String   REPT_GBN = dobj.getRetObject("S").getRecord().get("REPT_GBN");   //입금 구분
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BSTYP_NM  ,  SUM(COUNT2)  COL_UPSO  ,  SUM(AMT2)  COL_AMT  ,  0  MAP_UPSO  ,  0  MAP_AMT  FROM  (   \n";
        query +=" SELECT  CC.CODE_NM  BSTYP_NM  ,  COUNT(DISTINCT  AA.UPSO_CD)  COUNT2  ,  SUM(NVL(AA.REPT_AMT,0))  -  SUM(NVL(AA.COMIS,0))  AMT2  FROM  (   \n";
        query +=" SELECT  REPT_AMT  ,  COMIS  ,  UPSO_CD  ,  REPT_GBN  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  UNION  ALL   \n";
        query +=" SELECT  DISTR_AMT  ,  0  ,  UPSO_CD  ,  REPT_GBN  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)  )  AA  ,  GIBU.TBRA_UPSO  BB  ,  FIDU.TENV_CODE  CC  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  :TO_YRMN||'31'   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.HIGH_CD  =  '00141'   \n";
        query +=" AND  CC.CODE_ETC  =  'A'   \n";
        query +=" AND  CC.CODE_CD  =  AA.REPT_GBN  GROUP  BY  CC.CODE_NM,  AA.REPT_GBN  UNION  ALL   \n";
        query +=" SELECT  '협회입금반환'  BSTYP_NM  ,  COUNT(DISTINCT  UPSO_CD)  COUNT2  ,  -SUM(RETURN_AMT)  AMT2  FROM  GIBU.TBRA_REPT_RETURN  A  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.RETURN_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'  UNION  ALL  --무대공연  입금반환분  협회  입금반환에서  한번  빼고,  무통장입금내역에서는  더해야함   \n";
        query +=" SELECT  '무통장'  BSTYP_NM  ,  COUNT(DISTINCT  UPSO_CD)  COUNT2  ,  SUM(RETURN_AMT)  AMT2  FROM  GIBU.TBRA_REPT_RETURN  A  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.RETURN_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  A.UPSO_CD  IS  NULL  )  GROUP  BY  BSTYP_NM  ORDER  BY  1 ";
        sobj.setSql(query);
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$mon_collect_list
    //##**$$stomu_list
    /*
    */
    public DOBJ CTLstomu_list(DOBJ dobj)
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
            dobj  = CALLstomu_list_SEL1(Conn, dobj);           //  SEL
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
    public DOBJ CTLstomu_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLstomu_list_SEL1(Conn, dobj);           //  SEL
        return dobj;
    }
    // SEL
    public DOBJ CALLstomu_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLstomu_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLstomu_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        String   REPT_GBN = dobj.getRetObject("S").getRecord().get("REPT_GBN");   //입금 구분
        String   BSTYP_NM = dobj.getRetObject("S").getRecord().get("BSTYP_NM");   //업종명
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BBB.BSCONHAN_NM  ,  NVL(AAA.STOMU_COL,  0)  AS  STOMU_COL  ,  NVL(AAA.STOMU_AMT,  0)  AS  STOMU_AMT  FROM   \n";
        query +=" (SELECT  DD.BSCON_CD  ,  COUNT(NVL(AA.REPT_AMT,0))  AS  STOMU_COL  ,  SUM(NVL(AA.REPT_AMT,0)  -  NVL(AA.COMIS,0))  AS  STOMU_AMT  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_AMT  ,  COMIS  ,  UPSO_CD  ,  BRAN_CD  ,  MAPPING_DAY  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  DISTR_GBN  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  DISTR_AMT  ,  0  ,  UPSO_CD  ,  BRAN_CD  ,  MAPPING_DAY  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  REPT_GBN  =  DECODE(:REPT_GBN,  NULL,  REPT_GBN,  :REPT_GBN)   \n";
        query +=" AND  BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  REPT_GBN  <>  '08'  UNION  ALL   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  (CASE  WHEN  MIN(DISTR_SEQ)  OVER(PARTITION  BY  A.REPT_DAY,  A.REPT_NUM,  A.REPT_GBN)  =  DISTR_SEQ  THEN  A.DISTR_AMT  -  NVL(C.COMIS,  0)  ELSE  A.DISTR_AMT  END)  AS  REPT_AMT  ,  0  ,  A.UPSO_CD  ,  A.BRAN_CD  ,  A.MAPPING_DAY  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_REPT_VIRTUAL  B  ,  GIBU.TBRA_REPT  C  WHERE  A.MAPPING_DAY  BETWEEN  :FROM_YRMN||'01'   \n";
        query +=" AND  :TO_YRMN||'31'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM   \n";
        query +=" AND  A.REPT_GBN  =  '08'   \n";
        query +=" AND  A.REPT_DAY  =  C.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  C.REPT_NUM(+)   \n";
        query +=" AND  C.REPT_GBN(+)  =  '08'  )  AA  ,  GIBU.TBRA_UPSO  BB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  ,  ((SELECT  BSCON_CD,  BSCON_UPSO_CD,  UPSO_CD  AS  KOMCA_UPSO_CD,  TRUNC(INS_DATE)  AS  START_DATE,  TO_DATE('9999-12-31',  'YYYY-MM-DD')  AS  END_DATE  FROM  GIBU.TBRA_STOMU_CONTRINFO  WHERE  MOD_DATE  IS  NULL   \n";
        query +=" AND  UPSO_CD  IS  NOT  NULL)  UNION   \n";
        query +=" (SELECT  A.BSCON_CD,  A.BSCON_UPSO_CD,  A.AFT_CNG  AS  KOMCA_UPSO_CD,  A.INS_DATE  AS  START_DATE,  LEAD(A.INS_DATE,  1,  TO_DATE('9999-12-31',  'YYYY-MM-DD'))  OVER  (PARTITION  BY  A.BSCON_CD,  A.BSCON_UPSO_CD  ORDER  BY  A.SEQ)  AS  END_DATE  FROM  (   \n";
        query +=" SELECT  BSCON_CD,  BSCON_UPSO_CD,  SEQ,  CNG_COL,  BEFO_CNG,  AFT_CNG,  TRUNC(INS_DATE)  AS  INS_DATE  FROM  GIBU.TBRA_STOMU_HISTY  WHERE  1=1   \n";
        query +=" AND  CNG_COL  =  '업소코드'  )  A  WHERE  A.AFT_CNG  IS  NOT  NULL  ))  DD  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  AA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  AA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  :TO_YRMN||'31'   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  DD.KOMCA_UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  AA.MAPPING_DAY  BETWEEN  DD.START_DATE   \n";
        query +=" AND  DD.END_DATE   \n";
        query +=" AND  GIBU.FT_GET_BSTYP_NM(CC.BSTYP_CD)  =  :BSTYP_NM  GROUP  BY  DD.BSCON_CD)  AAA  RIGHT  OUTER  JOIN   \n";
        query +=" (SELECT  BSCON_CD,  BSCONHAN_NM,  USE_YN  FROM  GIBU.TBRA_BSCON_STOMU  WHERE  USE_YN  =  'Y'   \n";
        query +=" AND  BSCON_CD  IN  ('04225'  ,'22454'  ,'H0596'  ,'H0739'  ,'G0278'  ,'06001'  ,'02316'  ,'G0259'  ,'G0327'  ,'37227'  ,'35194'  ,'23247'))  BBB  ON  AAA.BSCON_CD  =  BBB.BSCON_CD  ORDER  BY  BBB.BSCONHAN_NM ";
        sobj.setSql(query);
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("BSTYP_NM", BSTYP_NM);               //업종명
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$stomu_list
    //##**$$end
}
