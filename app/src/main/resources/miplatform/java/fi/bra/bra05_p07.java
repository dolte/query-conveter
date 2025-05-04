
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_p07
{
    public bra05_p07()
    {
    }
    //##**$$accu_sol_list
    /* * 프로그램명 : bra05_p07
    * 작성자 : 서정재
    * 작성일 : 2009/09/29
    * 설명 :
    고소의뢰대비 해결현황을 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLaccu_sol_list(DOBJ dobj)
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
            dobj  = CALLaccu_sol_list_SEL1(Conn, dobj);           //  고소의뢰대비해결현황
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
    public DOBJ CTLaccu_sol_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLaccu_sol_list_SEL1(Conn, dobj);           //  고소의뢰대비해결현황
        return dobj;
    }
    // 고소의뢰대비해결현황
    public DOBJ CALLaccu_sol_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccu_sol_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccu_sol_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   END_YRMN = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   START_YRMN = dobj.getRetObject("S").getRecord().get("START_YRMN");   //시작년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.ACCU_DAY  ,  TA.UPSO_CD  ,  TB.UPSO_NM  ,  TC.GRADNM  ,  TC.MONPRNCFEE  ,  DECODE(TA.START_YRMN,  NULL,'',  TA.START_YRMN||'  ~  '||TA.END_YRMN)  YRMN  ,  NVL(TA.REQ_ORG_AMT,0)  +  NVL(TA.REQ_ADDT_AMT,0)  TOT_AMT  ,  TA.COMPN_DAY  ,  DECODE(TA.JUDG_CD,  '1',  DECODE(TA.COMPN_DAY,  NULL,'',  TA.SOL_START_YRMN||'  ~  '||TA.SOL_END_YRMN),  DECODE(TA.SOL_START_YRMN,  NULL,'',  TA.SOL_START_YRMN||'  ~  '||TA.SOL_END_YRMN))  SOL_YRMN  ,  DECODE(TA.JUDG_CD,  '1',  DECODE(TA.COMPN_DAY,  NULL,  0,  NVL(TA.SOL_ORG_AMT,0)  +  NVL(TA.SOL_ADDT_AMT,0)),  NVL(TA.SOL_ORG_AMT,0)  +  NVL(TA.SOL_ADDT_AMT,0))  SOL_AMT  ,  TE.CODE_NM  JUDG_CD  ,  TD.HAN_NM  STAFF_NM  FROM  GIBU.TBRA_ACCU  TA  ,  GIBU.TBRA_UPSO  TB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  TRIM(ZB.BSTYP_CD)  BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  ,  INSA.TINS_MST01  TD  ,  FIDU.TENV_CODE  TE  WHERE  TB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TA.UPSO_CD  =  TB.UPSO_CD   \n";
        query +=" AND  TA.ACCU_DAY  >=  :START_YRMN  ||  '01'   \n";
        query +=" AND  TA.ACCU_DAY  <=  :END_YRMN  ||  '31'   \n";
        query +=" AND  TC.UPSO_CD  =  TB.UPSO_CD   \n";
        query +=" AND  TD.STAFF_NUM(+)  =  TB.STAFF_CD   \n";
        query +=" AND  TE.HIGH_CD(+)  =  '00220'   \n";
        query +=" AND  TE.CODE_CD(+)  =  TA.JUDG_CD  ORDER  BY  TA.ACCU_DAY,  TB.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("END_YRMN", END_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("START_YRMN", START_YRMN);               //시작년월
        return sobj;
    }
    //##**$$accu_sol_list
    //##**$$end
}
