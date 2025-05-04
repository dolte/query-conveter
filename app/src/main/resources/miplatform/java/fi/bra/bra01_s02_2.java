
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s02_2
{
    public bra01_s02_2()
    {
    }
    //##**$$upso_acmcn_select
    /* * 프로그램명 : bra01_s02
    * 작성자 : 서정재
    * 작성일 : 2009/10/15
    * 설명 : 업소별 반주기 정보를 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLupso_acmcn_select(DOBJ dobj)
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
            dobj  = CALLupso_acmcn_select_SEL1(Conn, dobj);           //  업소별 온오프 반주기조회
            dobj  = CALLupso_acmcn_select_SEL2(Conn, dobj);           //  업소별 반주기조회
            dobj  = CALLupso_acmcn_select_SEL3(Conn, dobj);           //  업소별 오브리 정보 조회
            dobj  = CALLupso_acmcn_select_SEL4(Conn, dobj);           //  설치 불가능 여부
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
    public DOBJ CTLupso_acmcn_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_acmcn_select_SEL1(Conn, dobj);           //  업소별 온오프 반주기조회
        dobj  = CALLupso_acmcn_select_SEL2(Conn, dobj);           //  업소별 반주기조회
        dobj  = CALLupso_acmcn_select_SEL3(Conn, dobj);           //  업소별 오브리 정보 조회
        dobj  = CALLupso_acmcn_select_SEL4(Conn, dobj);           //  설치 불가능 여부
        return dobj;
    }
    // 업소별 온오프 반주기조회
    // 해당업소의 온오프별 반주기 정보를 조회한다.
    public DOBJ CALLupso_acmcn_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_acmcn_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.UPSO_CD  ,  XB.KIND  MCHN_COMPY  ,  NVL(SUM(XA.ACMCN_DAESU),  0)  ACMCN_DAESU  FROM  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  (CASE  B.MCHN_COMPY  WHEN  'E0006'  THEN  B.MCHN_COMPY  WHEN  'E0003'  THEN  B.MCHN_COMPY  ELSE  'ETC'  END)  MCHN_COMPY  ,  A.ONOFF_GBN  ,  ACMCN_DAESU  FROM  GIBU.TBRA_UPSO_ONOFF_INFO  A  ,  GIBU.TBRA_ACMCN_MODEL  B  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD  )  XA  ,  (   \n";
        query +=" SELECT  '1_KY_O'  KIND,  'E0006'  MCHN_COMPY,  'O'  ONOFF_GBN  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '4_KY_F',  'E0006',  'F'  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '2_TJ_O',  'E0003',  'O'  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '5_TJ_F',  'E0003',  'F'  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '3_ETC_O',  'ETC'  ,  'O'  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  '6_ETC_F',  'ETC'  ,  'F'  FROM  DUAL  )  XB  WHERE  XB.ONOFF_GBN  =  XA.ONOFF_GBN  (+)   \n";
        query +=" AND  XB.MCHN_COMPY  =  XA.MCHN_COMPY  (+)  GROUP  BY  XB.KIND,  XA.UPSO_CD  ORDER  BY  XB.KIND ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소별 반주기조회
    // 해당업소의 반주기 정보를 조회한다.
    public DOBJ CALLupso_acmcn_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_acmcn_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  XA.MCHN_COMPY  KMCHN_COMPY  ,  XA.MODEL_CD  KMODEL_CD  ,  XA.MODEL_NM  KMODEL_NM  ,  XA.ACMCN_DAESU  KACMCN_DAESU  ,  XA.GBN  KGBN  ,  XB.MCHN_COMPY  TMCHN_COMPY  ,  XB.MODEL_CD  TMODEL_CD  ,  XB.MODEL_NM  TMODEL_NM  ,  XB.ACMCN_DAESU  TACMCN_DAESU  ,  XB.GBN  TGBN  ,  XC.MCHN_COMPY  EMCHN_COMPY  ,  XC.MODEL_CD  EMODEL_CD  ,  XC.MODEL_NM  EMODEL_NM  ,  XC.ACMCN_DAESU  EACMCN_DAESU  ,  XC.GBN  EGBN  FROM  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0006'  )  XA  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0003'  )  XB  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  NOT  IN  ('E0003',  'E0006')  )  XC  WHERE  XA.N  =  XB.N  (+)   \n";
        query +=" AND  XA.N  =  XC.N  (+)  UNION   \n";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  XA.MCHN_COMPY  ,  XA.MODEL_CD  ,  XA.MODEL_NM  ,  XA.ACMCN_DAESU  ,  XA.GBN  ,  XB.MCHN_COMPY  ,  XB.MODEL_CD  ,  XB.MODEL_NM  ,  XB.ACMCN_DAESU  ,  XB.GBN  ,  XC.MCHN_COMPY  ,  XC.MODEL_CD  ,  XC.MODEL_NM  ,  XC.ACMCN_DAESU  ,  XC.GBN  FROM  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0006'  )  XA  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0003'  )  XB  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  NOT  IN  ('E0003',  'E0006')  )  XC  WHERE  XB.N  =  XA.N  (+)   \n";
        query +=" AND  XB.N  =  XC.N  (+)  UNION   \n";
        query +=" SELECT  :UPSO_CD  AS  UPSO_CD  ,  XA.MCHN_COMPY  ,  XA.MODEL_CD  ,  XA.MODEL_NM  ,  XA.ACMCN_DAESU  ,  XA.GBN  ,  XB.MCHN_COMPY  ,  XB.MODEL_CD  ,  XB.MODEL_NM  ,  XB.ACMCN_DAESU  ,  XB.GBN  ,  XC.MCHN_COMPY  ,  XC.MODEL_CD  ,  XC.MODEL_NM  ,  XC.ACMCN_DAESU  ,  XC.GBN  FROM  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0006'  )  XA  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  =  'E0003'  )  XB  ,  (   \n";
        query +=" SELECT  ROWNUM  N  ,  A.MCHN_COMPY  ,  A.MODEL_CD  ,  A.MODEL_NM  ,  B.ACMCN_DAESU  ,  A.GBN  FROM  GIBU.TBRA_ACMCN_MODEL  A  ,  GIBU.TBRA_UPSO_ACMCN_INFO  B  WHERE  B.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.MODEL_CD  =  B.MODEL_CD   \n";
        query +=" AND  A.MCHN_COMPY  NOT  IN  ('E0003',  'E0006')  )  XC  WHERE  XC.N  =  XA.N  (+)   \n";
        query +=" AND  XC.N  =  XB.N  (+) ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소별 오브리 정보 조회
    // 해당 업소의 오브리 정보를 조회한다.
    public DOBJ CALLupso_acmcn_select_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_acmcn_select_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_select_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  UPSO_CD  ,  MODEL_CD  ,  MODEL_NM  ,  MCHN_COMPYNM  ,  MCHN_COMPY  FROM  GIBU.TBRA_UPSO_AUBRY_INFO  WHERE  UPSO_CD=:UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 설치 불가능 여부
    // TJ반주기 오프라인 로그데이터 설치가능 여부
    public DOBJ CALLupso_acmcn_select_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_acmcn_select_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_acmcn_select_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(COL_MCH_YN,  '1','불가능','가능')  AS  COL_MCH_YN  FROM  GIBU.SDB_TBRA_UPSO  WHERE  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$upso_acmcn_select
    //##**$$end
}
