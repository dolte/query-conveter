
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac07_r04
{
    public tac07_r04()
    {
    }
    //##**$$searchTac07
    /* * 프로그램명 : tac07_r04
    * 작성자 : 손주환
    * 작성일 : 2009/10/12
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchTac07(DOBJ dobj)
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
            dobj  = CALLsearchTac07_SEL1(Conn, dobj);           //  채권자조회
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
    public DOBJ CTLsearchTac07( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchTac07_SEL1(Conn, dobj);           //  채권자조회
        return dobj;
    }
    // 채권자조회
    public DOBJ CALLsearchTac07_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchTac07_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchTac07_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CLAIMPRES_MB_CD = dobj.getRetObject("S").getRecord().get("CLAIMPRES_MB_CD");   //채권자 회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  A.MNG_NUM,  A.CLAIMDEBT_GBN,  A.CLAIM_KND,  A.FIXGRD_CTENT,  A.CLAIMPRES_MB_CD,  A.CLAIMPRES_NM,  A.CLAIMPRES_BANK_CD,  A.CLAIMPRES_ACCN_NUM,  A.CLAIM_BST_AMT,  A.CLAIM_PTTNRATE,  A.CLAIM_RFND_AMT,  A.REPAYPROC_START_DAY,  A.REPAYPROC_END_DAY,  A.COMPL_YN,  A.REMAK,  A.CLAIMPRES_MEMO,  A.DEBTPRES_GBN,  A.SUPP_GBN  FROM  FIDU.TMEM_CLAIMDEBT  A  WHERE  A.CLAIMPRES_MB_CD  =  :CLAIMPRES_MB_CD  ORDER  BY  A.MNG_NUM ";
        sobj.setSql(query);
        sobj.setString("CLAIMPRES_MB_CD", CLAIMPRES_MB_CD);               //채권자 회원 코드
        return sobj;
    }
    //##**$$searchTac07
    //##**$$tac07_r04_select
    /*
    */
    public DOBJ CTLtac07_r04_select(DOBJ dobj)
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
            dobj  = CALLtac07_r04_select_SEL1(Conn, dobj);           //  분배금액조회
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
    public DOBJ CTLtac07_r04_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac07_r04_select_SEL1(Conn, dobj);           //  분배금액조회
        return dobj;
    }
    // 분배금액조회
    public DOBJ CALLtac07_r04_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac07_r04_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac07_r04_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   WRK_YRMN = dobj.getRetObject("S").getRecord().get("WRK_YRMN");   //작업 년월
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        String   PROD_CD = dobj.getRetObject("S").getRecord().get("PROD_CD");   //작품 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT MB_CD, FIDU.GET_MB_NM (MB_CD) MB_NM, PROD_CD, FIDU.GET_PROD_NM (PROD_CD) PROD_NM, MDM_CD, FIDU.GET_MDM_NM_EX (MDM_CD, 1) MDM_NM_L, FIDU.GET_MDM_NM_EX (MDM_CD, 2) MDM_NM_M, FIDU.GET_MDM_NM_EX (MDM_CD, 3) MDM_NM_S, FIDU.GET_MDM_NM_EX (MDM_CD, 4) MDM_NM, TRNSF_GBN, NVL(TRUNC(DISTR_AMT,2),0) DISTR_AMT, MNGCOMIS_RATE, NVL(TRUNC (DISTR_AMT * MNGCOMIS_RATE * 0.01),0) COMIS  ";
        query +=" FROM (  ";
        query +=" SELECT A.RIGHTPRES_MB_CD MB_CD, PROD_CD, A.MDM_CD, CASE WHEN RIGHTPRES_MB_CD = ORGAU_MB_CD THEN '1' ELSE '2' END TRNSF_GBN , SUM (DISTR_AMT) DISTR_AMT, B.MNGCOMIS_RATE  ";
        query +=" FROM FIDU.TDIS_DISTR A, FIDU.TENV_MNGCOMIS B  ";
        query +=" WHERE A.MDM_CD = B.MDM_CD  ";
        query +=" AND A.WRK_YRMN = B.APPL_YRMN  ";
        query +=" AND WRK_YRMN = :WRK_YRMN  ";
        query +=" AND RIGHTPRES_MB_CD = :MB_CD  ";
        if( !PROD_CD.equals("") )
        {
            query +=" AND PROD_CD=:PROD_CD  ";
        }
        query +=" GROUP BY A.MDM_CD, PROD_CD, RIGHTPRES_MB_CD, ORGAU_MB_CD, B.MNGCOMIS_RATE)  ";
        query +=" WHERE 1=1  ";
        if( !TRNSF_GBN.equals("") )
        {
            query +=" AND TRNSF_GBN=:TRNSF_GBN  ";
        }
        query +=" ORDER BY MB_CD,PROD_NM, MDM_CD  ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("WRK_YRMN", WRK_YRMN);               //작업 년월
        if(!TRNSF_GBN.equals(""))
        {
            sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        }
        if(!PROD_CD.equals(""))
        {
            sobj.setString("PROD_CD", PROD_CD);               //작품 코드
        }
        return sobj;
    }
    //##**$$tac07_r04_select
    //##**$$end
}
