
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra06_p11
{
    public bra06_p11()
    {
    }
    //##**$$onoff_info
    /* * 프로그램명 : bra06_p11
    * 작성자 : 서정재
    * 작성일 : 2009/12/02
    * 설명 : 온오프라인 반주기 정보를 가지고 있는 업종에 대한 현황을 파악한다.
    * 수정일1: 2010/04/14
    * 수정자 :
    * 수정내용 : 온오프라인 반주기 5개 업종에 대해서 데이타를 조회한다.
    기존:노래방/유흥/단란
    추가:게임방/레스토랑
    */
    public DOBJ CTLonoff_info(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
            {
                dobj  = CALLonoff_info_SEL8(Conn, dobj);           //  온오프라인업소현황
            }
            else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
            {
                dobj  = CALLonoff_info_SEL3(Conn, dobj);           //  온오프라인반주기현황
            }
            else if( 1 == 2)
            {
                dobj  = CALLonoff_info_SEL1(Conn, dobj);           //  온오프라인업소현황-사용안함
                dobj  = CALLonoff_info_SEL10(Conn, dobj);           //  사용안
            }
            else
            {
                dobj  = CALLonoff_info_SEL2(Conn, dobj);           //  미파악업소현황
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
    public DOBJ CTLonoff_info( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
        {
            dobj  = CALLonoff_info_SEL8(Conn, dobj);           //  온오프라인업소현황
        }
        else if( dobj.getRetObject("S").getRecord().get("GBN").equals("2"))
        {
            dobj  = CALLonoff_info_SEL3(Conn, dobj);           //  온오프라인반주기현황
        }
        else if( 1 == 2)
        {
            dobj  = CALLonoff_info_SEL1(Conn, dobj);           //  온오프라인업소현황-사용안함
            dobj  = CALLonoff_info_SEL10(Conn, dobj);           //  사용안
        }
        else
        {
            dobj  = CALLonoff_info_SEL2(Conn, dobj);           //  미파악업소현황
        }
        return dobj;
    }
    // 온오프라인업소현황
    public DOBJ CALLonoff_info_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLonoff_info_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_info_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEPT_NM  BRAN_NM  ,  DECODE  (GBN,  'K',  '금영',  'T',  '태진',  'E',  '기타',  'M',  '혼합')  MCHN_COMPY  ,  DECODE  (GBN,  'K',  K_O_ON  ,  'T',  T_O_ON  ,  'E',  E_O_ON  ,  'M',  O_ON_SUM  )  DAESU_ON_o  ,  DECODE  (GBN,  'K',  K_K_ON  ,  'T',  T_K_ON  ,  'E',  E_K_ON  ,  'M',  K_ON_SUM  )  DAESU_ON_k  ,  DECODE  (GBN,  'K',  K_L_ON  ,  'T',  T_L_ON  ,  'E',  E_L_ON  ,  'M',  L_ON_SUM  )  DAESU_ON_l  ,  DECODE  (GBN,  'K',  K_P_ON  ,  'T',  T_P_ON  ,  'E',  E_P_ON  ,  'M',  P_ON_SUM  )  DAESU_ON_p  ,  DECODE  (GBN,  'K',  K_Y_ON  ,  'T',  T_Y_ON  ,  'E',  E_Y_ON  ,  'M',  Y_ON_SUM  )  DAESU_ON_y  ,  DECODE  (GBN,  'K',  K_O_OFF  ,  'T',  T_O_OFF  ,  'E',  E_O_OFF  ,  'M',  O_OFF_SUM  )  DAESU_OFF_o  ,  DECODE  (GBN,  'K',  K_K_OFF  ,  'T',  T_K_OFF  ,  'E',  E_K_OFF  ,  'M',  K_OFF_SUM  )  DAESU_OFF_k  ,  DECODE  (GBN,  'K',  K_L_OFF  ,  'T',  T_L_OFF  ,  'E',  E_L_OFF  ,  'M',  L_OFF_SUM  )  DAESU_OFF_l  ,  DECODE  (GBN,  'K',  K_P_OFF  ,  'T',  T_P_OFF  ,  'E',  E_P_OFF  ,  'M',  P_OFF_SUM  )  DAESU_OFF_p  ,  DECODE  (GBN,  'K',  K_Y_OFF  ,  'T',  T_Y_OFF  ,  'E',  E_Y_OFF  ,  'M',  Y_OFF_SUM  )  DAESU_OFF_y  ,  DECODE  (GBN,  'K',  K_O_ONOFF,  'T',  T_O_ONOFF,  'E',  E_O_ONOFF,  'M',  O_ONOFF_SUM)  DAESU_ONOFF_o  ,  DECODE  (GBN,  'K',  K_K_ONOFF,  'T',  T_K_ONOFF,  'E',  E_K_ONOFF,  'M',  K_ONOFF_SUM)  DAESU_ONOFF_k  ,  DECODE  (GBN,  'K',  K_L_ONOFF,  'T',  T_L_ONOFF,  'E',  E_L_ONOFF,  'M',  L_ONOFF_SUM)  DAESU_ONOFF_l  ,  DECODE  (GBN,  'K',  K_P_ONOFF,  'T',  T_P_ONOFF,  'E',  E_P_ONOFF,  'M',  P_ONOFF_SUM)  DAESU_ONOFF_p  ,  DECODE  (GBN,  'K',  K_Y_ONOFF,  'T',  T_Y_ONOFF,  'E',  E_Y_ONOFF,  'M',  Y_ONOFF_SUM)  DAESU_ONOFF_y  ,  BRAN_CD  FROM  (   \n";
        query +=" SELECT  'K'  GBN,  1  SRT  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  'T'  GBN,  2  SRT  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  'E'  GBN,  3  SRT  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  'M'  GBN,  4  SRT  FROM  DUAL  )  TA  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GBN='KO'  THEN  1  ELSE  0  END)  K_O_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GBN='KO'  THEN  1  ELSE  0  END)  K_L_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GBN='KO'  THEN  1  ELSE  0  END)  K_K_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GBN='KO'  THEN  1  ELSE  0  END)  K_P_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GBN='KO'  THEN  1  ELSE  0  END)  K_Y_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GBN='TO'  THEN  1  ELSE  0  END)  T_O_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GBN='TO'  THEN  1  ELSE  0  END)  T_L_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GBN='TO'  THEN  1  ELSE  0  END)  T_K_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GBN='TO'  THEN  1  ELSE  0  END)  T_P_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GBN='TO'  THEN  1  ELSE  0  END)  T_Y_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GBN='EO'  THEN  1  ELSE  0  END)  E_O_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GBN='EO'  THEN  1  ELSE  0  END)  E_L_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GBN='EO'  THEN  1  ELSE  0  END)  E_K_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GBN='EO'  THEN  1  ELSE  0  END)  E_P_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GBN='EO'  THEN  1  ELSE  0  END)  E_Y_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GBN='KF'  THEN  1  ELSE  0  END)  K_O_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GBN='KF'  THEN  1  ELSE  0  END)  K_L_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GBN='KF'  THEN  1  ELSE  0  END)  K_K_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GBN='KF'  THEN  1  ELSE  0  END)  K_P_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GBN='KF'  THEN  1  ELSE  0  END)  K_Y_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GBN='TF'  THEN  1  ELSE  0  END)  T_O_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GBN='TF'  THEN  1  ELSE  0  END)  T_L_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GBN='TF'  THEN  1  ELSE  0  END)  T_K_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GBN='TF'  THEN  1  ELSE  0  END)  T_P_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GBN='TF'  THEN  1  ELSE  0  END)  T_Y_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GBN='EF'  THEN  1  ELSE  0  END)  E_O_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GBN='EF'  THEN  1  ELSE  0  END)  E_L_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GBN='EF'  THEN  1  ELSE  0  END)  E_K_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GBN='EF'  THEN  1  ELSE  0  END)  E_P_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GBN='EF'  THEN  1  ELSE  0  END)  E_Y_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GBN='KOF'  THEN  1  ELSE  0  END)  K_O_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GBN='KOF'  THEN  1  ELSE  0  END)  K_L_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GBN='KOF'  THEN  1  ELSE  0  END)  K_K_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GBN='KOF'  THEN  1  ELSE  0  END)  K_P_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GBN='KOF'  THEN  1  ELSE  0  END)  K_Y_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GBN='TOF'  THEN  1  ELSE  0  END)  T_O_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GBN='TOF'  THEN  1  ELSE  0  END)  T_L_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GBN='TOF'  THEN  1  ELSE  0  END)  T_K_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GBN='TOF'  THEN  1  ELSE  0  END)  T_P_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GBN='TOF'  THEN  1  ELSE  0  END)  T_Y_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GBN='EOF'  THEN  1  ELSE  0  END)  E_O_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GBN='EOF'  THEN  1  ELSE  0  END)  E_L_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GBN='EOF'  THEN  1  ELSE  0  END)  E_K_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GBN='EOF'  THEN  1  ELSE  0  END)  E_P_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GBN='EOF'  THEN  1  ELSE  0  END)  E_Y_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GBN='KTEO'  THEN  1  ELSE  0  END)  O_ON_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GBN='KTEO'  THEN  1  ELSE  0  END)  L_ON_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GBN='KTEO'  THEN  1  ELSE  0  END)  K_ON_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GBN='KTEO'  THEN  1  ELSE  0  END)  P_ON_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GBN='KTEO'  THEN  1  ELSE  0  END)  Y_ON_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GBN='KTEF'  THEN  1  ELSE  0  END)  O_OFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GBN='KTEF'  THEN  1  ELSE  0  END)  L_OFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GBN='KTEF'  THEN  1  ELSE  0  END)  K_OFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GBN='KTEF'  THEN  1  ELSE  0  END)  P_OFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GBN='KTEF'  THEN  1  ELSE  0  END)  Y_OFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GBN='KTEOF'  THEN  1  ELSE  0  END)  O_ONOFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GBN='KTEOF'  THEN  1  ELSE  0  END)  L_ONOFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GBN='KTEOF'  THEN  1  ELSE  0  END)  K_ONOFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GBN='KTEOF'  THEN  1  ELSE  0  END)  P_ONOFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GBN='KTEOF'  THEN  1  ELSE  0  END)  Y_ONOFF_SUM  ,  SUM(CASE  WHEN  GBN='ERR'  THEN  1  ELSE  0  END)  Y_ERR_SUM  FROM  (   \n";
        query +=" SELECT  ZA.BRAN_CD  ,  ZA.UPSO_CD  ,  BSTYP_CD  ,  ZB.GBN  FROM  (   \n";
        query +=" SELECT  XC.BRAN_CD  BRAN_CD  ,  XA.UPSO_CD  ,  XB.GRADNM  ,  TRIM(XA.BSTYP_CD)  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  B.BSTYP_CD  ,  B.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  GROUP  BY  UPSO_CD  )  A  ,  GIBU.TBRA_UPSORTAL_INFO  B  WHERE  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  )  XA  ,  GIBU.TBRA_BSTYPGRAD  XB  ,  GIBU.TBRA_UPSO  XC  WHERE  XB.BSTYP_CD  =  'z'   \n";
        query +=" AND  XB.GRAD_GBN  IN  ('o','k','l','p','y')   \n";
        query +=" AND  XA.BSTYP_CD  =  XB.GRAD_GBN   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  (XC.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XC.CLSBS_INS_DAY,1,6),'  ')  >  TO_CHAR(SYSDATE,'YYYYMM'))   \n";
        query +=" AND  XC.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  XC.NEW_DAY  <=  TO_CHAR(SYSDATE,'YYYYMM')  ||  '31'  )  ZA  ,  (   \n";
        query +=" SELECT  UPSO_CD,  CASE  WHEN  NVL(K_ON_CNT,0)  >  0   \n";
        query +=" AND  NVL(K_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_OFF_CNT,0)  =  0  THEN  'KO'  WHEN  NVL(K_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(K_OFF_CNT,0)  >  0   \n";
        query +=" AND  NVL(T_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_OFF_CNT,0)  =  0  THEN  'KF'  WHEN  NVL(K_ON_CNT,0)  >  0   \n";
        query +=" AND  NVL(K_OFF_CNT,0)  >  0   \n";
        query +=" AND  NVL(T_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_OFF_CNT,0)  =  0  THEN  'KOF'  WHEN  NVL(K_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(K_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_ON_CNT,0)  >  0   \n";
        query +=" AND  NVL(T_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_OFF_CNT,0)  =  0  THEN  'TO'  WHEN  NVL(K_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(K_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_OFF_CNT,0)  >  0   \n";
        query +=" AND  NVL(E_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_OFF_CNT,0)  =  0  THEN  'TF'  WHEN  NVL(K_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(K_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_ON_CNT,0)  >  0   \n";
        query +=" AND  NVL(T_OFF_CNT,0)  >  0   \n";
        query +=" AND  NVL(E_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_OFF_CNT,0)  =  0  THEN  'TOF'  WHEN  NVL(K_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(K_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_ON_CNT,0)  >  0   \n";
        query +=" AND  NVL(E_OFF_CNT,0)  =  0  THEN  'EO'  WHEN  NVL(K_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(K_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_OFF_CNT,0)  >  0  THEN  'EF'  WHEN  NVL(K_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(K_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_ON_CNT,0)  >  0   \n";
        query +=" AND  NVL(E_OFF_CNT,0)  >  0  THEN  'EOF'  WHEN  (  NVL((SELECT  DISTINCT  1  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.ONOFF_GBN  =  'O'   \n";
        query +=" AND  B.MCHN_COMPY  =  'E0006'),0)+  NVL((SELECT  DISTINCT  1  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.ONOFF_GBN  =  'O'   \n";
        query +=" AND  B.MCHN_COMPY  =  'E0003'),0)+  NVL((SELECT  DISTINCT  1  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.ONOFF_GBN  =  'O'   \n";
        query +=" AND  B.MCHN_COMPY  NOT  IN  ('E0006',  'E0003')),0))  >1  THEN  'KTEO'  WHEN  (  NVL((SELECT  DISTINCT  1  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.ONOFF_GBN  =  'F'   \n";
        query +=" AND  B.MCHN_COMPY  =  'E0006'),0)+  NVL((SELECT  DISTINCT  1  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.ONOFF_GBN  =  'F'   \n";
        query +=" AND  B.MCHN_COMPY  =  'E0003'),0)+  NVL((SELECT  DISTINCT  1  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.ONOFF_GBN  =  'F'   \n";
        query +=" AND  B.MCHN_COMPY  NOT  IN  ('E0006',  'E0003')),0))  >1  THEN  'KTEF'  WHEN  (NVL((SELECT  DISTINCT  1  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A  ,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.ONOFF_GBN  =  'O'),0)  +  NVL((SELECT  DISTINCT  1  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A  ,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.ONOFF_GBN  =  'F'),0)  >=  2  )  THEN  'KTEOF'  WHEN  NVL(K_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(K_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(T_OFF_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_ON_CNT,0)  =  0   \n";
        query +=" AND  NVL(E_OFF_CNT,0)  =  0  THEN  'ERR'  END  GBN  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUM(DECODE(MCHN_COMPY,'E0006',DECODE(ONOFF_GBN,'O',ACMCN_DAESU,0),0))  K_ON_CNT  ,  SUM(DECODE(MCHN_COMPY,'E0006',DECODE(ONOFF_GBN,'F',ACMCN_DAESU,0),0))  K_OFF_CNT  ,  SUM(DECODE(MCHN_COMPY,'E0003',DECODE(ONOFF_GBN,'O',ACMCN_DAESU,0),0))  T_ON_CNT  ,  SUM(DECODE(MCHN_COMPY,'E0003',DECODE(ONOFF_GBN,'F',ACMCN_DAESU,0),0))  T_OFF_CNT  ,  SUM(DECODE(MCHN_COMPY,'E0003',0,'E0006',0,DECODE(ONOFF_GBN,'O',ACMCN_DAESU,0)))  E_ON_CNT  ,  SUM(DECODE(MCHN_COMPY,'E0003',0,'E0006',0,DECODE(ONOFF_GBN,'F',ACMCN_DAESU,0)))  E_OFF_CNT  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A  ,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  A.MODEL_CD  =  B.MODEL_CD  GROUP  BY  A.UPSO_CD)  C  )  ZB  WHERE  ZA.UPSO_CD  =  ZB.UPSO_CD  )  GROUP  BY  BRAN_CD  )  TB  ,  INSA.TCPM_DEPT  TC  WHERE  TB.BRAN_CD  =  TC.GIBU  ORDER  BY  BRAN_CD,  SRT ";
        sobj.setSql(query);
        return sobj;
    }
    // 온오프라인반주기현황
    public DOBJ CALLonoff_info_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLonoff_info_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_info_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEPT_NM  BRAN_NM  ,  DECODE  (GBN,  'K',  '금영',  'T',  '태진',  'E',  '기타')  MCHN_COMPY  ,  DECODE  (GBN,  'K',  K_O_ON  ,  'T',  T_O_ON  ,  'E',  E_O_ON)  DAESU_ON_o  ,  DECODE  (GBN,  'K',  K_K_ON  ,  'T',  T_K_ON  ,  'E',  E_K_ON)  DAESU_ON_k  ,  DECODE  (GBN,  'K',  K_L_ON  ,  'T',  T_L_ON  ,  'E',  E_L_ON)  DAESU_ON_l  ,  DECODE  (GBN,  'K',  K_P_ON  ,  'T',  T_P_ON  ,  'E',  E_P_ON)  DAESU_ON_p  ,  DECODE  (GBN,  'K',  K_Y_ON  ,  'T',  T_Y_ON  ,  'E',  E_Y_ON)  DAESU_ON_y  ,  DECODE  (GBN,  'K',  K_O_OFF  ,  'T',  T_O_OFF  ,  'E',  E_O_OFF)  DAESU_OFF_o  ,  DECODE  (GBN,  'K',  K_K_OFF  ,  'T',  T_K_OFF  ,  'E',  E_K_OFF)  DAESU_OFF_k  ,  DECODE  (GBN,  'K',  K_L_OFF  ,  'T',  T_L_OFF  ,  'E',  E_L_OFF)  DAESU_OFF_l  ,  DECODE  (GBN,  'K',  K_P_OFF  ,  'T',  T_P_OFF  ,  'E',  E_P_OFF)  DAESU_OFF_p  ,  DECODE  (GBN,  'K',  K_Y_OFF  ,  'T',  T_Y_OFF  ,  'E',  E_Y_OFF)  DAESU_OFF_y  ,  BRAN_CD  FROM  (   \n";
        query +=" SELECT  'K'  GBN,  1  SRT  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  'T'  GBN,  2  SRT  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  'E'  GBN,  3  SRT  FROM  DUAL  )  TA  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'  THEN  K_ON  ELSE  0  END)  K_O_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'  THEN  K_ON  ELSE  0  END)  K_L_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'  THEN  K_ON  ELSE  0  END)  K_K_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'  THEN  K_ON  ELSE  0  END)  K_P_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'  THEN  K_ON  ELSE  0  END)  K_Y_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'  THEN  T_ON  ELSE  0  END)  T_O_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'  THEN  T_ON  ELSE  0  END)  T_L_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'  THEN  T_ON  ELSE  0  END)  T_K_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'  THEN  T_ON  ELSE  0  END)  T_P_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'  THEN  T_ON  ELSE  0  END)  T_Y_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'  THEN  E_ON  ELSE  0  END)  E_O_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'  THEN  E_ON  ELSE  0  END)  E_L_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'  THEN  E_ON  ELSE  0  END)  E_K_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'  THEN  E_ON  ELSE  0  END)  E_P_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'  THEN  E_ON  ELSE  0  END)  E_Y_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'  THEN  K_OFF  ELSE  0  END)  K_O_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'  THEN  K_OFF  ELSE  0  END)  K_L_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'  THEN  K_OFF  ELSE  0  END)  K_K_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'  THEN  K_OFF  ELSE  0  END)  K_P_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'  THEN  K_OFF  ELSE  0  END)  K_Y_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'  THEN  T_OFF  ELSE  0  END)  T_O_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'  THEN  T_OFF  ELSE  0  END)  T_L_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'  THEN  T_OFF  ELSE  0  END)  T_K_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'  THEN  T_OFF  ELSE  0  END)  T_P_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'  THEN  T_OFF  ELSE  0  END)  T_Y_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'  THEN  E_OFF  ELSE  0  END)  E_O_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'  THEN  E_OFF  ELSE  0  END)  E_L_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'  THEN  E_OFF  ELSE  0  END)  E_K_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'  THEN  E_OFF  ELSE  0  END)  E_P_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'  THEN  E_OFF  ELSE  0  END)  E_Y_OFF  FROM  (   \n";
        query +=" SELECT  ZA.BRAN_CD  ,  ZB.UPSO_CD  ,  BSTYP_CD  ,  ZB.K_ON  ,  ZB.K_OFF  ,  ZB.T_ON  ,  ZB.T_OFF  ,  ZB.E_ON  ,  ZB.E_OFF  ,  ZB.S_ON  ,  ZB.S_OFF  FROM  (   \n";
        query +=" SELECT  XC.BRAN_CD  ,  XA.UPSO_CD  ,  XB.GRADNM  ,  TRIM(XA.BSTYP_CD)  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  DECODE(B.BSTYP_CD,  '1',  'o',  '2',  'k',  '3',  'l',  BSTYP_CD)  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  GROUP  BY  UPSO_CD  )  A  ,  GIBU.TBRA_UPSORTAL_INFO  B  WHERE  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  )  XA  ,  GIBU.TBRA_BSTYPGRAD  XB  ,  GIBU.TBRA_UPSO  XC  WHERE  XB.BSTYP_CD  =  'z'   \n";
        query +=" AND  XB.GRAD_GBN  IN  ('o','k','l','p','y')   \n";
        query +=" AND  XA.BSTYP_CD  =  XB.GRAD_GBN   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.CLSBS_YRMN  IS  NULL   \n";
        query +=" AND  XC.UPSO_STAT  =  '1'  )  ZA  ,  (   \n";
        query +=" SELECT  XA.UPSO_CD  ,  SUM(NVL(XA.K_ON  ,  0))  K_ON  ,  SUM(NVL(XA.K_OFF,  0))  K_OFF  ,  SUM(NVL(XA.T_ON  ,  0))  T_ON  ,  SUM(NVL(XA.T_OFF,  0))  T_OFF  ,  SUM(NVL(XA.E_ON  ,  0))  E_ON  ,  SUM(NVL(XA.E_OFF,  0))  E_OFF  ,  SUM(NVL(XA.S_ON  ,  0))  S_ON  ,  SUM(NVL(XA.S_OFF,  0))  S_OFF  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  CASE  WHEN  (B.MCHN_COMPY  =  'E0006')  THEN  DECODE(A.ONOFF_GBN,  'O',  ACMCN_DAESU,  0)  END  K_ON  ,  CASE  WHEN  (B.MCHN_COMPY  =  'E0006')  THEN  DECODE(A.ONOFF_GBN,  'F',  ACMCN_DAESU,  0)  END  K_OFF  ,  CASE  WHEN  (B.MCHN_COMPY  =  'E0003')  THEN  DECODE(A.ONOFF_GBN,  'O',  ACMCN_DAESU,  0)  END  T_ON  ,  CASE  WHEN  (B.MCHN_COMPY  =  'E0003')  THEN  DECODE(A.ONOFF_GBN,  'F',  ACMCN_DAESU,  0)  END  T_OFF  ,  CASE  WHEN  (B.MCHN_COMPY  NOT  IN  ('E0003',  'E0006'))  THEN  DECODE(A.ONOFF_GBN,  'O',  ACMCN_DAESU,  0)  END  E_ON  ,  CASE  WHEN  (B.MCHN_COMPY  NOT  IN  ('E0003',  'E0006'))  THEN  DECODE(A.ONOFF_GBN,  'F',  ACMCN_DAESU,  0)  END  E_OFF  ,  DECODE(A.ONOFF_GBN,  'O',  ACMCN_DAESU,  0)  S_ON  ,  DECODE(A.ONOFF_GBN,  'F',  ACMCN_DAESU,  0)  S_OFF  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A  ,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  B.MODEL_CD  =  A.MODEL_CD  )  XA  GROUP  BY  XA.UPSO_CD  )  ZB  WHERE  ZA.UPSO_CD  =  ZB.UPSO_CD  )  GROUP  BY  BRAN_CD  )  TB  ,  INSA.TCPM_DEPT  TC  WHERE  TB.BRAN_CD  =  TC.GIBU  ORDER  BY  BRAN_CD,  SRT ";
        sobj.setSql(query);
        return sobj;
    }
    // 온오프라인업소현황-사용안함
    public DOBJ CALLonoff_info_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLonoff_info_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_info_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(BRAN_CD,  'O',  SUBSTR(DEPT_NM,  6,  LENGTH(DEPT_NM)),  DEPT_NM)  BRAN_NM  ,  DECODE  (GBN,  'K',  '금영',  'T',  '태진',  'E',  '기타',  'M',  '혼합')  MCHN_COMPY  ,  DECODE  (GBN,  'K',  K_O_ON  ,  'T',  T_O_ON  ,  'E',  E_O_ON  ,  'M',  O_ON_SUM  -  (K_O_ON  +  T_O_ON  +  E_O_ON  ),  O_ON_SUM  )  DAESU_ON_o  ,  DECODE  (GBN,  'K',  K_K_ON  ,  'T',  T_K_ON  ,  'E',  E_K_ON  ,  'M',  K_ON_SUM  -  (K_K_ON  +  T_K_ON  +  E_K_ON  ),  K_ON_SUM  )  DAESU_ON_k  ,  DECODE  (GBN,  'K',  K_L_ON  ,  'T',  T_L_ON  ,  'E',  E_L_ON  ,  'M',  L_ON_SUM  -  (K_L_ON  +  T_L_ON  +  E_L_ON  ),  L_ON_SUM  )  DAESU_ON_l  ,  DECODE  (GBN,  'K',  K_K_ON  ,  'T',  T_K_ON  ,  'E',  E_K_ON  ,  'M',  P_ON_SUM  -  (K_P_ON  +  T_P_ON  +  E_P_ON  ),  P_ON_SUM  )  DAESU_ON_p  ,  DECODE  (GBN,  'K',  K_L_ON  ,  'T',  T_L_ON  ,  'E',  E_L_ON  ,  'M',  Y_ON_SUM  -  (K_Y_ON  +  T_Y_ON  +  E_Y_ON  ),  Y_ON_SUM  )  DAESU_ON_y  ,  DECODE  (GBN,  'K',  K_O_OFF  ,  'T',  T_O_OFF  ,  'E',  E_O_OFF  ,  'M',  O_OFF_SUM  -  (K_O_OFF  +  T_O_OFF  +  E_O_OFF  ),  O_OFF_SUM  )  DAESU_OFF_o  ,  DECODE  (GBN,  'K',  K_K_OFF  ,  'T',  T_K_OFF  ,  'E',  E_K_OFF  ,  'M',  K_OFF_SUM  -  (K_K_OFF  +  T_K_OFF  +  E_K_OFF  ),  K_OFF_SUM  )  DAESU_OFF_k  ,  DECODE  (GBN,  'K',  K_L_OFF  ,  'T',  T_L_OFF  ,  'E',  E_L_OFF  ,  'M',  L_OFF_SUM  -  (K_L_OFF  +  T_L_OFF  +  E_L_OFF  ),  L_OFF_SUM  )  DAESU_OFF_l  ,  DECODE  (GBN,  'K',  K_K_OFF  ,  'T',  T_K_OFF  ,  'E',  E_K_OFF  ,  'M',  P_OFF_SUM  -  (K_P_OFF  +  T_P_OFF  +  E_P_OFF  ),  P_OFF_SUM  )  DAESU_OFF_p  ,  DECODE  (GBN,  'K',  K_L_OFF  ,  'T',  T_L_OFF  ,  'E',  E_L_OFF  ,  'M',  Y_OFF_SUM  -  (K_Y_OFF  +  T_Y_OFF  +  E_Y_OFF  ),  Y_OFF_SUM  )  DAESU_OFF_y  ,  DECODE  (GBN,  'K',  K_O_ONOFF,  'T',  T_O_ONOFF,  'E',  E_O_ONOFF,  'M',  O_ONOFF_SUM  -  (K_O_ONOFF  +  T_O_ONOFF  +  E_O_ONOFF),  O_ONOFF_SUM)  DAESU_ONOFF_o  ,  DECODE  (GBN,  'K',  K_K_ONOFF,  'T',  T_K_ONOFF,  'E',  E_K_ONOFF,  'M',  K_ONOFF_SUM  -  (K_K_ONOFF  +  T_K_ONOFF  +  E_K_ONOFF),  K_ONOFF_SUM)  DAESU_ONOFF_k  ,  DECODE  (GBN,  'K',  K_L_ONOFF,  'T',  T_L_ONOFF,  'E',  E_L_ONOFF,  'M',  L_ONOFF_SUM  -  (K_L_ONOFF  +  T_L_ONOFF  +  E_L_ONOFF),  L_ONOFF_SUM)  DAESU_ONOFF_l  ,  DECODE  (GBN,  'K',  K_P_ONOFF,  'T',  T_P_ONOFF,  'E',  E_P_ONOFF,  'M',  P_ONOFF_SUM  -  (K_P_ONOFF  +  T_P_ONOFF  +  E_P_ONOFF),  P_ONOFF_SUM)  DAESU_ONOFF_p  ,  DECODE  (GBN,  'K',  K_Y_ONOFF,  'T',  T_Y_ONOFF,  'E',  E_Y_ONOFF,  'M',  Y_ONOFF_SUM  -  (K_Y_ONOFF  +  T_Y_ONOFF  +  E_Y_ONOFF),  Y_ONOFF_SUM)  DAESU_ONOFF_y  ,  BRAN_CD  FROM  (   \n";
        query +=" SELECT  'K'  GBN,  1  SRT  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  'T'  GBN,  2  SRT  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  'E'  GBN,  3  SRT  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  'M'  GBN,  4  SRT  FROM  DUAL  )  TA  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  (K_ON  >  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  K_O_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  (K_ON  >  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  K_L_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  (K_ON  >  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  K_K_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  (K_ON  >  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  K_P_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  (K_ON  >  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  K_Y_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  >  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  T_O_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  >  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  T_L_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  >  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  T_K_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  >  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  T_P_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  >  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  T_Y_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  >  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  E_O_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  >  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  E_L_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  >  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  E_K_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  >  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  E_P_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  >  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  E_Y_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  >  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  K_O_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  >  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  K_L_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  >  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  K_K_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  >  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  K_P_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  >  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  K_Y_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  >  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  T_O_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  >  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  T_L_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  >  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  T_K_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  >  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  T_P_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  >  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  T_Y_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  >  0)  THEN  1  ELSE  0  END)  E_O_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  >  0)  THEN  1  ELSE  0  END)  E_L_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  >  0)  THEN  1  ELSE  0  END)  E_K_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  >  0)  THEN  1  ELSE  0  END)  E_P_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  >  0)  THEN  1  ELSE  0  END)  E_Y_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  (K_ON  >  0   \n";
        query +=" AND  K_OFF  >  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  K_O_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  (K_ON  >  0   \n";
        query +=" AND  K_OFF  >  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  K_L_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  (K_ON  >  0   \n";
        query +=" AND  K_OFF  >  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  K_K_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  (K_ON  >  0   \n";
        query +=" AND  K_OFF  >  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  K_P_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  (K_ON  >  0   \n";
        query +=" AND  K_OFF  >  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  K_Y_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  >  0   \n";
        query +=" AND  T_OFF  >  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  T_O_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  >  0   \n";
        query +=" AND  T_OFF  >  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  T_L_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  >  0   \n";
        query +=" AND  T_OFF  >  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  T_K_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  >  0   \n";
        query +=" AND  T_OFF  >  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  T_P_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  >  0   \n";
        query +=" AND  T_OFF  >  0   \n";
        query +=" AND  E_ON  =  0   \n";
        query +=" AND  E_OFF  =  0)  THEN  1  ELSE  0  END)  T_Y_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  >  0   \n";
        query +=" AND  E_OFF  >  0)  THEN  1  ELSE  0  END)  E_O_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  >  0   \n";
        query +=" AND  E_OFF  >  0)  THEN  1  ELSE  0  END)  E_L_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  >  0   \n";
        query +=" AND  E_OFF  >  0)  THEN  1  ELSE  0  END)  E_K_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  >  0   \n";
        query +=" AND  E_OFF  >  0)  THEN  1  ELSE  0  END)  E_P_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  (K_ON  =  0   \n";
        query +=" AND  K_OFF  =  0   \n";
        query +=" AND  T_ON  =  0   \n";
        query +=" AND  T_OFF  =  0   \n";
        query +=" AND  E_ON  >  0   \n";
        query +=" AND  E_OFF  >  0)  THEN  1  ELSE  0  END)  E_Y_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  (S_ON  >  0   \n";
        query +=" AND  S_OFF  =  0)  THEN  1  ELSE  0  END)  O_ON_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  (S_ON  >  0   \n";
        query +=" AND  S_OFF  =  0)  THEN  1  ELSE  0  END)  L_ON_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  (S_ON  >  0   \n";
        query +=" AND  S_OFF  =  0)  THEN  1  ELSE  0  END)  K_ON_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  (S_ON  >  0   \n";
        query +=" AND  S_OFF  =  0)  THEN  1  ELSE  0  END)  P_ON_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  (S_ON  >  0   \n";
        query +=" AND  S_OFF  =  0)  THEN  1  ELSE  0  END)  Y_ON_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  (S_ON  =  0   \n";
        query +=" AND  S_OFF  >  0)  THEN  1  ELSE  0  END)  O_OFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  (S_ON  =  0   \n";
        query +=" AND  S_OFF  >  0)  THEN  1  ELSE  0  END)  L_OFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  (S_ON  =  0   \n";
        query +=" AND  S_OFF  >  0)  THEN  1  ELSE  0  END)  K_OFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  (S_ON  =  0   \n";
        query +=" AND  S_OFF  >  0)  THEN  1  ELSE  0  END)  P_OFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  (S_ON  =  0   \n";
        query +=" AND  S_OFF  >  0)  THEN  1  ELSE  0  END)  Y_OFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  (S_ON  >  0   \n";
        query +=" AND  S_OFF  >  0)  THEN  1  ELSE  0  END)  O_ONOFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  (S_ON  >  0   \n";
        query +=" AND  S_OFF  >  0)  THEN  1  ELSE  0  END)  L_ONOFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  (S_ON  >  0   \n";
        query +=" AND  S_OFF  >  0)  THEN  1  ELSE  0  END)  K_ONOFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  (S_ON  >  0   \n";
        query +=" AND  S_OFF  >  0)  THEN  1  ELSE  0  END)  P_ONOFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  (S_ON  >  0   \n";
        query +=" AND  S_OFF  >  0)  THEN  1  ELSE  0  END)  Y_ONOFF_SUM  FROM  (   \n";
        query +=" SELECT  ZA.BRAN_CD  ,  ZB.UPSO_CD  ,  BSTYP_CD  ,  ZB.K_ON  ,  ZB.K_OFF  ,  ZB.T_ON  ,  ZB.T_OFF  ,  ZB.E_ON  ,  ZB.E_OFF  ,  ZB.S_ON  ,  ZB.S_OFF  FROM  (   \n";
        query +=" SELECT  XC.BRAN_CD  ,  XA.UPSO_CD  ,  XB.GRADNM  ,  TRIM(XA.BSTYP_CD)  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  DECODE(B.BSTYP_CD,  '1',  'o',  '2',  'k',  '3',  'l',  BSTYP_CD)  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  GROUP  BY  UPSO_CD  )  A  ,  GIBU.TBRA_UPSORTAL_INFO  B  WHERE  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  )  XA  ,  GIBU.TBRA_BSTYPGRAD  XB  ,  GIBU.TBRA_UPSO  XC  WHERE  XB.BSTYP_CD  =  'z'   \n";
        query +=" AND  XB.GRAD_GBN  IN  ('o','k','l','p','y')   \n";
        query +=" AND  XA.BSTYP_CD  =  XB.GRAD_GBN   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  (XC.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XC.CLSBS_INS_DAY,1,6),'  ')  >  TO_CHAR(SYSDATE,'YYYYMM'))   \n";
        query +=" AND  XC.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  XC.NEW_DAY  <=  TO_CHAR(SYSDATE,'YYYYMM')  ||  '31'  )  ZA  ,  (   \n";
        query +=" SELECT  XA.UPSO_CD  ,  SUM(NVL(XA.K_ON  ,  0))  K_ON  ,  SUM(NVL(XA.K_OFF,  0))  K_OFF  ,  SUM(NVL(XA.T_ON  ,  0))  T_ON  ,  SUM(NVL(XA.T_OFF,  0))  T_OFF  ,  SUM(NVL(XA.E_ON  ,  0))  E_ON  ,  SUM(NVL(XA.E_OFF,  0))  E_OFF  ,  SUM(NVL(XA.S_ON  ,  0))  S_ON  ,  SUM(NVL(XA.S_OFF,  0))  S_OFF  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  CASE  WHEN  (B.MCHN_COMPY  =  'E0006')  THEN  DECODE(A.ONOFF_GBN,  'O',  ACMCN_DAESU,  0)  END  K_ON  ,  CASE  WHEN  (B.MCHN_COMPY  =  'E0006')  THEN  DECODE(A.ONOFF_GBN,  'F',  ACMCN_DAESU,  0)  END  K_OFF  ,  CASE  WHEN  (B.MCHN_COMPY  =  'E0003')  THEN  DECODE(A.ONOFF_GBN,  'O',  ACMCN_DAESU,  0)  END  T_ON  ,  CASE  WHEN  (B.MCHN_COMPY  =  'E0003')  THEN  DECODE(A.ONOFF_GBN,  'F',  ACMCN_DAESU,  0)  END  T_OFF  ,  CASE  WHEN  (B.MCHN_COMPY  NOT  IN  ('E0003',  'E0006'))  THEN  DECODE(A.ONOFF_GBN,  'O',  ACMCN_DAESU,  0)  END  E_ON  ,  CASE  WHEN  (B.MCHN_COMPY  NOT  IN  ('E0003',  'E0006'))  THEN  DECODE(A.ONOFF_GBN,  'F',  ACMCN_DAESU,  0)  END  E_OFF  ,  DECODE(A.ONOFF_GBN,  'O',  ACMCN_DAESU,  0)  S_ON  ,  DECODE(A.ONOFF_GBN,  'F',  ACMCN_DAESU,  0)  S_OFF  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A  ,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  B.MODEL_CD  =  A.MODEL_CD  )  XA  GROUP  BY  XA.UPSO_CD  )  ZB  WHERE  ZA.UPSO_CD  =  ZB.UPSO_CD  )  GROUP  BY  BRAN_CD  )  TB  ,  INSA.TCPM_DEPT  TC  WHERE  TB.BRAN_CD  =  TC.GIBU  ORDER  BY  BRAN_CD,  SRT ";
        sobj.setSql(query);
        return sobj;
    }
    // 사용안
    public DOBJ CALLonoff_info_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLonoff_info_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_info_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEPT_NM  BRAN_NM  ,  DECODE  (GBN,  'K',  '금영',  'T',  '태진',  'E',  '기타',  'M',  '혼합')  MCHN_COMPY  ,  DECODE  (GBN,  'K',  K_O_ON  ,  'T',  T_O_ON  ,  'E',  E_O_ON  ,  'M',  O_ON_SUM  )  DAESU_ON_o  ,  DECODE  (GBN,  'K',  K_K_ON  ,  'T',  T_K_ON  ,  'E',  E_K_ON  ,  'M',  K_ON_SUM  )  DAESU_ON_k  ,  DECODE  (GBN,  'K',  K_L_ON  ,  'T',  T_L_ON  ,  'E',  E_L_ON  ,  'M',  L_ON_SUM  )  DAESU_ON_l  ,  DECODE  (GBN,  'K',  K_P_ON  ,  'T',  T_P_ON  ,  'E',  E_P_ON  ,  'M',  P_ON_SUM  )  DAESU_ON_p  ,  DECODE  (GBN,  'K',  K_Y_ON  ,  'T',  T_Y_ON  ,  'E',  E_Y_ON  ,  'M',  Y_ON_SUM  )  DAESU_ON_y  ,  DECODE  (GBN,  'K',  K_O_OFF  ,  'T',  T_O_OFF  ,  'E',  E_O_OFF  ,  'M',  O_OFF_SUM  )  DAESU_OFF_o  ,  DECODE  (GBN,  'K',  K_K_OFF  ,  'T',  T_K_OFF  ,  'E',  E_K_OFF  ,  'M',  K_OFF_SUM  )  DAESU_OFF_k  ,  DECODE  (GBN,  'K',  K_L_OFF  ,  'T',  T_L_OFF  ,  'E',  E_L_OFF  ,  'M',  L_OFF_SUM  )  DAESU_OFF_l  ,  DECODE  (GBN,  'K',  K_P_OFF  ,  'T',  T_P_OFF  ,  'E',  E_P_OFF  ,  'M',  P_OFF_SUM  )  DAESU_OFF_p  ,  DECODE  (GBN,  'K',  K_Y_OFF  ,  'T',  T_Y_OFF  ,  'E',  E_Y_OFF  ,  'M',  Y_OFF_SUM  )  DAESU_OFF_y  ,  DECODE  (GBN,  'K',  K_O_ONOFF,  'T',  T_O_ONOFF,  'E',  E_O_ONOFF,  'M',  O_ONOFF_SUM)  DAESU_ONOFF_o  ,  DECODE  (GBN,  'K',  K_K_ONOFF,  'T',  T_K_ONOFF,  'E',  E_K_ONOFF,  'M',  K_ONOFF_SUM)  DAESU_ONOFF_k  ,  DECODE  (GBN,  'K',  K_L_ONOFF,  'T',  T_L_ONOFF,  'E',  E_L_ONOFF,  'M',  L_ONOFF_SUM)  DAESU_ONOFF_l  ,  DECODE  (GBN,  'K',  K_P_ONOFF,  'T',  T_P_ONOFF,  'E',  E_P_ONOFF,  'M',  P_ONOFF_SUM)  DAESU_ONOFF_p  ,  DECODE  (GBN,  'K',  K_Y_ONOFF,  'T',  T_Y_ONOFF,  'E',  E_Y_ONOFF,  'M',  Y_ONOFF_SUM)  DAESU_ONOFF_y  ,  BRAN_CD  FROM  (   \n";
        query +=" SELECT  'K'  GBN,  1  SRT  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  'T'  GBN,  2  SRT  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  'E'  GBN,  3  SRT  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  'M'  GBN,  4  SRT  FROM  DUAL  )  TA  ,  (   \n";
        query +=" SELECT  BRAN_CD  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KO'  THEN  1  ELSE  0  END)  K_O_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KO'  THEN  1  ELSE  0  END)  K_L_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KO'  THEN  1  ELSE  0  END)  K_K_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KO'  THEN  1  ELSE  0  END)  K_P_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KO'  THEN  1  ELSE  0  END)  K_Y_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='TO'  THEN  1  ELSE  0  END)  T_O_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='TO'  THEN  1  ELSE  0  END)  T_L_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='TO'  THEN  1  ELSE  0  END)  T_K_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='TO'  THEN  1  ELSE  0  END)  T_P_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='TO'  THEN  1  ELSE  0  END)  T_Y_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='EO'  THEN  1  ELSE  0  END)  E_O_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='EO'  THEN  1  ELSE  0  END)  E_L_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='EO'  THEN  1  ELSE  0  END)  E_K_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='EO'  THEN  1  ELSE  0  END)  E_P_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='EO'  THEN  1  ELSE  0  END)  E_Y_ON  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KF'  THEN  1  ELSE  0  END)  K_O_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KF'  THEN  1  ELSE  0  END)  K_L_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KF'  THEN  1  ELSE  0  END)  K_K_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KF'  THEN  1  ELSE  0  END)  K_P_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KF'  THEN  1  ELSE  0  END)  K_Y_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='TF'  THEN  1  ELSE  0  END)  T_O_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='TF'  THEN  1  ELSE  0  END)  T_L_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='TF'  THEN  1  ELSE  0  END)  T_K_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='TF'  THEN  1  ELSE  0  END)  T_P_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='TF'  THEN  1  ELSE  0  END)  T_Y_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='EF'  THEN  1  ELSE  0  END)  E_O_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='EF'  THEN  1  ELSE  0  END)  E_L_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='EF'  THEN  1  ELSE  0  END)  E_K_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='EF'  THEN  1  ELSE  0  END)  E_P_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='EF'  THEN  1  ELSE  0  END)  E_Y_OFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KOF'  THEN  1  ELSE  0  END)  K_O_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KOF'  THEN  1  ELSE  0  END)  K_L_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KOF'  THEN  1  ELSE  0  END)  K_K_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KOF'  THEN  1  ELSE  0  END)  K_P_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KOF'  THEN  1  ELSE  0  END)  K_Y_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='TOF'  THEN  1  ELSE  0  END)  T_O_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='TOF'  THEN  1  ELSE  0  END)  T_L_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='TOF'  THEN  1  ELSE  0  END)  T_K_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='TOF'  THEN  1  ELSE  0  END)  T_P_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='TOF'  THEN  1  ELSE  0  END)  T_Y_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='EOF'  THEN  1  ELSE  0  END)  E_O_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='EOF'  THEN  1  ELSE  0  END)  E_L_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='EOF'  THEN  1  ELSE  0  END)  E_K_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='EOF'  THEN  1  ELSE  0  END)  E_P_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='EOF'  THEN  1  ELSE  0  END)  E_Y_ONOFF  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KTEO'  THEN  1  ELSE  0  END)  O_ON_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KTEO'  THEN  1  ELSE  0  END)  L_ON_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KTEO'  THEN  1  ELSE  0  END)  K_ON_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KTEO'  THEN  1  ELSE  0  END)  P_ON_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KTEO'  THEN  1  ELSE  0  END)  Y_ON_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KTEF'  THEN  1  ELSE  0  END)  O_OFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KTEF'  THEN  1  ELSE  0  END)  L_OFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KTEF'  THEN  1  ELSE  0  END)  K_OFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KTEF'  THEN  1  ELSE  0  END)  P_OFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KTEF'  THEN  1  ELSE  0  END)  Y_OFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'o'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KTEOF'  THEN  1  ELSE  0  END)  O_ONOFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'l'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KTEOF'  THEN  1  ELSE  0  END)  L_ONOFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'k'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KTEOF'  THEN  1  ELSE  0  END)  K_ONOFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'p'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KTEOF'  THEN  1  ELSE  0  END)  P_ONOFF_SUM  ,  SUM(CASE  WHEN  BSTYP_CD  =  'y'   \n";
        query +=" AND  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='KTEOF'  THEN  1  ELSE  0  END)  Y_ONOFF_SUM  ,  SUM(CASE  WHEN  GIBU.FT_GET_TK_ONOFF_INFO(UPSO_CD)='ERR'  THEN  1  ELSE  0  END)  Y_ERR_SUM  FROM  (   \n";
        query +=" SELECT  ZA.BRAN_CD  ,  ZA.UPSO_CD  ,  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  XC.BRAN_CD  BRAN_CD  ,  XA.UPSO_CD  ,  XB.GRADNM  ,  TRIM(XA.BSTYP_CD)  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  B.BSTYP_CD  ,  B.UPSO_GRAD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  GROUP  BY  UPSO_CD  )  A  ,  GIBU.TBRA_UPSORTAL_INFO  B  WHERE  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  )  XA  ,  GIBU.TBRA_BSTYPGRAD  XB  ,  GIBU.TBRA_UPSO  XC  WHERE  XB.BSTYP_CD  =  'z'   \n";
        query +=" AND  XB.GRAD_GBN  IN  ('o','k','l','p','y')   \n";
        query +=" AND  XA.BSTYP_CD  =  XB.GRAD_GBN   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  (XC.CLSBS_YRMN  IS  NULL   \n";
        query +=" OR  NVL(SUBSTR(XC.CLSBS_INS_DAY,1,6),'  ')  >  TO_CHAR(SYSDATE,'YYYYMM'))   \n";
        query +=" AND  XC.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  XC.NEW_DAY  <=  TO_CHAR(SYSDATE,'YYYYMM')  ||  '31'  )  ZA  )  GROUP  BY  BRAN_CD  )  TB  ,  INSA.TCPM_DEPT  TC  WHERE  TB.BRAN_CD  =  TC.GIBU  ORDER  BY  BRAN_CD,  SRT ";
        sobj.setSql(query);
        return sobj;
    }
    // 미파악업소현황
    public DOBJ CALLonoff_info_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLonoff_info_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLonoff_info_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ROWNUM  RNUMBER  ,  BRAN_CD  ,  UPSO_CD  ,  UPSO_NM  ,  GRADNM  ,  UPSO_PHON  ,  UPSO_ADDR  FROM(   \n";
        query +=" SELECT  TB.UPSO_CD  ,  MAX(TB.UPSO_NM)  UPSO_NM  ,  MAX(TC.GRADNM)  GRADNM  ,  MAX(TB.UPSO_PHON)  UPSO_PHON  ,  MAX(TB.UPSO_NEW_ADDR1  ||  DECODE(TB.UPSO_NEW_ADDR2,  '',  '',  ',  '||TB.UPSO_NEW_ADDR2)  ||  TB.UPSO_REF_INFO)  UPSO_ADDR  ,  TB.BRAN_CD  FROM  GIBU.TBRA_UPSO  TB  ,  (   \n";
        query +=" SELECT  XC.BRAN_CD  ,  XA.UPSO_CD  ,  XB.GRADNM  ,  TRIM(XA.BSTYP_CD)  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  DECODE(B.BSTYP_CD,  '1',  'o',  '2',  'k',  '3',  'l',  BSTYP_CD)  BSTYP_CD  FROM  (   \n";
        query +=" SELECT  UPSO_CD  ,  MAX(JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  GROUP  BY  UPSO_CD  )  A  ,  GIBU.TBRA_UPSORTAL_INFO  B  WHERE  A.JOIN_SEQ  =  B.JOIN_SEQ   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  )  XA  ,  GIBU.TBRA_BSTYPGRAD  XB  ,  GIBU.TBRA_UPSO  XC  WHERE  XB.BSTYP_CD  =  'z'   \n";
        query +=" AND  XB.GRAD_GBN  IN  ('o','k','l','p','y')   \n";
        query +=" AND  XA.BSTYP_CD  =  XB.GRAD_GBN   \n";
        query +=" AND  XC.UPSO_CD  =  XA.UPSO_CD  )  TC  WHERE  TB.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  TB.BRAN_CD,  'AL',  TB.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TC.UPSO_CD  =  TB.UPSO_CD   \n";
        query +=" AND  TB.UPSO_CD  NOT  IN   \n";
        query +=" (SELECT  UPSO_CD  FROM  GIBU.TBRA_UPSO_ONOFF_INFO)   \n";
        query +=" AND  TB.UPSO_STAT  =  '1'   \n";
        query +=" AND  TB.CLSBS_YRMN  IS  NULL  GROUP  BY  TB.UPSO_CD,  TB.BRAN_CD  ORDER  BY  TB.BRAN_CD,  TB.UPSO_CD  ) ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$onoff_info
    //##**$$end
}
