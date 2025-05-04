
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra06_p05
{
    public bra06_p05()
    {
    }
    //##**$$mon_upso_status
    /* * 프로그램명 : bra06_p05
    * 작성자 : 서정재
    * 작성일 : 2009/12/02
    * 설명 : 신규의 조건은 무조건 조회달의 NEW_DAY 가 맞는 갯수의 현황이다. (박창순팀장님 확인 09.10.27일)
    업주변경시의 변경날짜나 전업소코드의 존재유무의 조건은 필요없다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLmon_upso_status(DOBJ dobj)
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
            dobj  = CALLmon_upso_status_SEL1(Conn, dobj);           //  월업소현황
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
    public DOBJ CTLmon_upso_status( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLmon_upso_status_SEL1(Conn, dobj);           //  월업소현황
        return dobj;
    }
    // 월업소현황
    public DOBJ CALLmon_upso_status_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLmon_upso_status_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLmon_upso_status_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BSTYP_NM  ,  (MON_CNT  +  CLOSE_CNT  -  NEW_CNT)  BEFORE_MON_CNT  ,  MON_CNT  ,  NEW_CNT  ,  STOP_CNT  ,  CLOSE_CNT  FROM(   \n";
        query +=" SELECT   \n";
        query +=" (SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =  XA.BSTYP_CD)  AS  BSTYP_NM  ,  XA.MON_CNT  MON_CNT  ,  NVL(XB.NEW_CNT,0)  NEW_CNT  ,  NVL(XC.STOP_CNT,0)  STOP_CNT  ,  NVL(XD.CLOSE_CNT,0)  CLOSE_CNT  FROM  (   \n";
        query +=" SELECT  BSTYP_CD  ,  COUNT(UPSO_CD)  MON_CNT  FROM  (   \n";
        query +=" SELECT  TB.BSTYP_CD  ,  TA.UPSO_CD  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TB  WHERE  TA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  TA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TA.NEW_DAY  <=  :YRMN  ||  '31'   \n";
        query +=" AND  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(TA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD  GROUP  BY  TB.BSTYP_CD,  TA.UPSO_CD  )  GROUP  BY  BSTYP_CD  )  XA  ,  (   \n";
        query +=" SELECT  BSTYP_CD  ,  COUNT(UPSO_CD)  NEW_CNT  FROM  (   \n";
        query +=" SELECT  TB.BSTYP_CD  ,  TA.UPSO_CD  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )TB  WHERE  TA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  TA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  SUBSTR(TA.NEW_DAY,1,6)  =  :YRMN   \n";
        query +=" AND  (BEFORE_UPSO_CD  IS  NULL   \n";
        query +=" OR  NVL(TO_CHAR(INS_DATE,'YYYYMM'),'220000')  >  :YRMN)   \n";
        query +=" AND  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(TA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  TA.UPSO_CD  =  TB.UPSO_CD  GROUP  BY  TB.BSTYP_CD,  TA.UPSO_CD  )  GROUP  BY  BSTYP_CD  )  XB  ,  (   \n";
        query +=" SELECT  BSTYP_CD  ,  COUNT(UPSO_CD)  STOP_CNT  FROM  (   \n";
        query +=" SELECT  TB.BSTYP_CD  ,  TA.UPSO_CD  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )TB  ,  GIBU.TBRA_UPSO_CLSED  TD  WHERE  TA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  TA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  SUBSTR(TA.NEW_DAY,1,6)  <=  :YRMN   \n";
        query +=" AND  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(TA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  TA.UPSO_CD  =  TB.UPSO_CD   \n";
        query +=" AND  TB.UPSO_CD  =  TD.UPSO_CD(+)   \n";
        query +=" AND  SUBSTR(TD.CLSED_DAY,1,6)  =  :YRMN   \n";
        query +=" AND  TD.CLSED_GBN  <>  '14'  GROUP  BY  TB.BSTYP_CD,  TA.UPSO_CD  )  GROUP  BY  BSTYP_CD  )  XC  ,  (   \n";
        query +=" SELECT  BSTYP_CD  ,  COUNT(UPSO_CD)  CLOSE_CNT  FROM  (   \n";
        query +=" SELECT  TB.BSTYP_CD  ,  TA.UPSO_CD  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )TB  ,  GIBU.TBRA_UPSO_CLSED  TD  WHERE  TA.BRAN_CD  =  DECODE(:BRAN_CD,  'AL',  TA.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TA.NEW_DAY  <=  :YRMN  ||  '31'   \n";
        query +=" AND  (TA.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(TA.CLSBS_INS_DAY,1,6),'  ')  >  :YRMN)   \n";
        query +=" AND  TA.UPSO_CD  =  TB.UPSO_CD   \n";
        query +=" AND  TB.UPSO_CD  =  TD.UPSO_CD(+)   \n";
        query +=" AND  SUBSTR(TD.CLSED_DAY,1,6)  =  :YRMN   \n";
        query +=" AND  TD.CLSED_GBN  =  '14'  GROUP  BY  TB.BSTYP_CD,  TA.UPSO_CD  )  GROUP  BY  BSTYP_CD  )XD  WHERE  XA.BSTYP_CD  =  XB.BSTYP_CD(+)   \n";
        query +=" AND  XA.BSTYP_CD  =  XC.BSTYP_CD(+)   \n";
        query +=" AND  XA.BSTYP_CD  =  XD.BSTYP_CD(+)  )  ORDER  BY  MON_CNT  DESC ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$mon_upso_status
    //##**$$end
}
