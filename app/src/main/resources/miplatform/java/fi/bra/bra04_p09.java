
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_p09
{
    public bra04_p09()
    {
    }
    //##**$$sel_day
    /*
    */
    public DOBJ CTLsel_day(DOBJ dobj)
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
            dobj  = CALLsel_day_SEL1(Conn, dobj);           //  MAX 년월일구하기
            if(!dobj.getRetObject("SEL1").getRecord().get("DISP_DAY").equals(""))
            {
                dobj  = CALLsel_day_SEL3(Conn, dobj);           //  최종일자
                dobj  = CALLsel_day_SEL2( dobj);        //  결과통합
            }
            else
            {
                dobj  = CALLsel_day_SEL4(Conn, dobj);           //  최종일자
                dobj  = CALLsel_day_SEL2( dobj);        //  결과통합
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
    public DOBJ CTLsel_day( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_day_SEL1(Conn, dobj);           //  MAX 년월일구하기
        if(!dobj.getRetObject("SEL1").getRecord().get("DISP_DAY").equals(""))
        {
            dobj  = CALLsel_day_SEL3(Conn, dobj);           //  최종일자
            dobj  = CALLsel_day_SEL2( dobj);        //  결과통합
        }
        else
        {
            dobj  = CALLsel_day_SEL4(Conn, dobj);           //  최종일자
            dobj  = CALLsel_day_SEL2( dobj);        //  결과통합
        }
        return dobj;
    }
    // MAX 년월일구하기
    // 해당년월에 등록되어 있는 날짜의 MAX값을 구한다
    public DOBJ CALLsel_day_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_day_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_day_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MAX(DISP_DAY)  DISP_DAY  FROM  GIBU.TBRA_NOLEV_DISP_LIST  WHERE  DISP_DAY  LIKE  :YRMN||'%' ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        return sobj;
    }
    // 최종일자
    public DOBJ CALLsel_day_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_day_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_day_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DISP_DAY = dobj.getRetObject("SEL1").getRecord().get("DISP_DAY");   //발송 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR((TO_DATE(:DISP_DAY,'YYYYMMDD')  +  1  )  ,  'YYYYMMDD')  START_DAY  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("DISP_DAY", DISP_DAY);               //발송 일자
        return sobj;
    }
    // 결과통합
    public DOBJ CALLsel_day_SEL2(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("SEL2");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL3, SEL4","START_DAY");
        rvobj.setName("SEL2") ;
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 최종일자
    public DOBJ CALLsel_day_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_day_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_day_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :YRMN  ||  '01'  AS  START_DAY  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        return sobj;
    }
    //##**$$sel_day
    //##**$$nolev_disp_rept
    /* * 프로그램명 : bra04_p09
    * 작성자 : 서정재
    * 작성일 : 2009/10/01
    * 설명    :
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLnolev_disp_rept(DOBJ dobj)
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
            dobj  = CALLnolev_disp_rept_SEL1(Conn, dobj);           //  외부발송대비 입금현황
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
    public DOBJ CTLnolev_disp_rept( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLnolev_disp_rept_SEL1(Conn, dobj);           //  외부발송대비 입금현황
        return dobj;
    }
    // 외부발송대비 입금현황
    public DOBJ CALLnolev_disp_rept_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLnolev_disp_rept_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLnolev_disp_rept_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   BSTYP_CD = dobj.getRetObject("S").getRecord().get("BSTYP_CD");   //업종 코드
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TD.UPSO_CD  ,  TD.UPSO_NM  ,  TD.MNGEMSTR_NM  ,  TE.GRADNM  BSTYP_NM  ,  TA.DEMD_AMT+TA.ADDT_AMT  DISP_AMT  ,  TB.START_YRMN  ,  TB.END_YRMN  ,  TB.REPT_AMT  FROM  GIBU.TBRA_NOLEV_DISP_LIST  TA  ,  (   \n";
        query +=" SELECT  A.UPSO_CD  ,  SUM(USE_AMT  +  NVL(BALANCE,  0))  REPT_AMT  ,  MAX(REPT_DAY)  REPT_DAY  ,  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  FROM  GIBU.TBRA_NOTE  A  ,  GIBU.TBRA_UPSO  B  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  BALANCE  FROM  GIBU.TBRA_REPT_BALANCE  WHERE  REPT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  )  C  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  C.UPSO_CD(+)  =  A.UPSO_CD   \n";
        query +=" AND  A.REPT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.USE_AMT  <>  0  GROUP  BY  A.UPSO_CD  )  TB  ,  GIBU.TBRA_UPSO  TD  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZC.GRADNM  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TE  WHERE  TA.DISP_DAY  BETWEEN  :YRMN||'01'   \n";
        query +=" AND  :YRMN||'31'   \n";
        query +=" AND  TD.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TB.UPSO_CD(+)  =  TA.UPSO_CD   \n";
        query +=" AND  TD.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TE.UPSO_CD(+)  =  TA.UPSO_CD   \n";
        query +=" AND  TRIM(TE.BSTYP_CD)  =  DECODE(:BSTYP_CD,  NULL,  TRIM(TE.BSTYP_CD),  :BSTYP_CD)  ORDER  BY  TD.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$nolev_disp_rept
    //##**$$end
}
