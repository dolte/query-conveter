
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_r21
{
    public bra04_r21()
    {
    }
    //##**$$col_upso_list
    /*
    */
    public DOBJ CTLcol_upso_list(DOBJ dobj)
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
                dobj  = CALLcol_upso_list_SEL1(Conn, dobj);           //  징수업소내역
                dobj  = CALLcol_upso_list_MRG6( dobj);        //  결과취합
            }
            else
            {
                dobj  = CALLcol_upso_list_SEL4(Conn, dobj);           //  신규일자 오류업소 내역서
                dobj  = CALLcol_upso_list_MRG6( dobj);        //  결과취합
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
    public DOBJ CTLcol_upso_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("GBN").equals("1"))
        {
            dobj  = CALLcol_upso_list_SEL1(Conn, dobj);           //  징수업소내역
            dobj  = CALLcol_upso_list_MRG6( dobj);        //  결과취합
        }
        else
        {
            dobj  = CALLcol_upso_list_SEL4(Conn, dobj);           //  신규일자 오류업소 내역서
            dobj  = CALLcol_upso_list_MRG6( dobj);        //  결과취합
        }
        return dobj;
    }
    // 징수업소내역
    // 해당년월의 징수업소수를 구한다. 가수매칭 업소를 포함한다.
    public DOBJ CALLcol_upso_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcol_upso_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcol_upso_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  AA.UPSO_CD  ,  CC.GRADNM  ,  CC.MONPRNCFEE  ,  BB.UPSO_NM  ,  BB.MNGEMSTR_NM  ,  BB.UPSO_ADDR  ,  BB.UPSO_PHON  ,  AA.START_YRMN||'~'||AA.END_YRMN  YRMN  ,  NVL(AA.REPT_AMT,0)  -  NVL(AA.COMIS,0)  REPT_AMT  ,  BB.STAFF_NM  ,  DECODE(AA.ACCU_GBN,  NULL,  '',  '고소입금')  REMAK  ,  DECODE(AA.ACCU_GBN,  NULL,  0,  1)  CNT  FROM  (   \n";
        query +=" SELECT  TA.REPT_DAY  ,  TA.REPT_NUM  ,  TA.REPT_GBN  ,  '-'  DISTR_SEQ  ,  TA.REPT_AMT  ,  TA.COMIS  ,  TA.UPSO_CD  ,  TB.START_YRMN  ,  TB.END_YRMN  ,  TB.ACCU_GBN  FROM  GIBU.TBRA_REPT  TA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  ACCU_GBN  ,  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  MAPPING_DAY  LIKE  :YRMN||'%'  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  ACCU_GBN  )  TB  WHERE  TA.MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  TA.DISTR_GBN  IS  NULL   \n";
        query +=" AND  TA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TB.UPSO_CD  (+)  =  TA.UPSO_CD   \n";
        query +=" AND  TB.REPT_DAY(+)  =  TA.REPT_DAY   \n";
        query +=" AND  TB.REPT_NUM(+)  =  TA.REPT_NUM   \n";
        query +=" AND  TB.REPT_GBN(+)  =  TA.REPT_GBN  UNION  ALL   \n";
        query +=" SELECT  TA.REPT_DAY  ,  TA.REPT_NUM  ,  TA.REPT_GBN  ,  TA.DISTR_SEQ  ,  TA.DISTR_AMT  ,  0  ,  TA.UPSO_CD  ,  TB.START_YRMN  ,  TB.END_YRMN  ,  TB.ACCU_GBN  FROM  GIBU.TBRA_REPT_UPSO_DISTR  TA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  DISTR_SEQ  ,  ACCU_GBN  ,  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  FROM  GIBU.TBRA_NOTE  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_SEQ  IS  NOT  NULL  GROUP  BY  UPSO_CD,  REPT_DAY,  REPT_NUM,  REPT_GBN,  DISTR_SEQ,  ACCU_GBN  )  TB  WHERE  TA.MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  TA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TB.UPSO_CD  (+)  =  TA.UPSO_CD   \n";
        query +=" AND  TB.REPT_DAY  (+)  =  TA.REPT_DAY   \n";
        query +=" AND  TB.REPT_NUM  (+)  =  TA.REPT_NUM   \n";
        query +=" AND  TB.REPT_GBN  (+)  =  TA.REPT_GBN   \n";
        query +=" AND  TB.DISTR_SEQ(+)  =  TA.DISTR_SEQ  )  AA  ,  (   \n";
        query +=" SELECT  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.MNGEMSTR_NM  ,  TA.STAFF_CD  ,  TB.HAN_NM  STAFF_NM  ,  TA.UPSO_NEW_ADDR1  ||  DECODE(TA.UPSO_NEW_ADDR2,  '',  '',  ',  '||TA.UPSO_NEW_ADDR2)  ||  TA.UPSO_REF_INFO  UPSO_ADDR  ,  TA.NEW_DAY  ,  TA.UPSO_PHON  FROM  GIBU.TBRA_UPSO  TA  ,  INSA.TINS_MST01  TB  WHERE  TB.STAFF_NUM(+)  =  TA.STAFF_CD  )  BB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  IS  NOT  NULL   \n";
        query +=" AND  BB.NEW_DAY  <=  :YRMN||'31'   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.UPSO_CD  =  AA.UPSO_CD  ORDER  BY  UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 결과취합
    public DOBJ CALLcol_upso_list_MRG6(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG6");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL4","");
        rvobj.setName("MRG6") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 신규일자 오류업소 내역서
    public DOBJ CALLcol_upso_list_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcol_upso_list_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcol_upso_list_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  AA.UPSO_CD  ,  CC.GRADNM  ,  CC.MONPRNCFEE  ,  BB.UPSO_NM  ,  BB.MNGEMSTR_NM  ,  BB.UPSO_ADDR  ,  BB.UPSO_PHON  ,  BB.START_YRMN||'~'||BB.END_YRMN  YRMN  ,  NVL(AA.REPT_AMT,0)  -  NVL(AA.COMIS,0)  REPT_AMT  ,  BB.STAFF_NM  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_AMT  ,  COMIS  ,  UPSO_CD  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  DISTR_AMT  ,  0  ,  UPSO_CD  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  )  AA  ,  (   \n";
        query +=" SELECT  TA.UPSO_CD  ,  TB.UPSO_NM  ,  TB.MNGEMSTR_NM  ,  TB.STAFF_CD  ,  TC.HAN_NM  STAFF_NM  ,  TB.UPSO_NEW_ADDR1  ||  DECODE(TB.UPSO_NEW_ADDR2,  '',  '',  ',  '||TB.UPSO_NEW_ADDR2)  ||  TB.UPSO_REF_INFO  UPSO_ADDR  ,  TA.START_YRMN  ,  TA.END_YRMN  ,  TB.NEW_DAY  ,  TB.UPSO_PHON  FROM  (   \n";
        query +=" SELECT  ZB.UPSO_CD  ,  MIN(ZA.NOTE_YRMN)  START_YRMN  ,  MAX(ZA.NOTE_YRMN)  END_YRMN  FROM  GIBU.TBRA_NOTE  ZA  ,  (   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_REPT  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  UNION   \n";
        query +=" SELECT  UPSO_CD  ,  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  MAPPING_DAY  LIKE  :YRMN||'%'   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  )  ZB  WHERE  ZA.UPSO_CD(+)  =  ZB.UPSO_CD   \n";
        query +=" AND  ZA.REPT_DAY(+)  =  ZB.REPT_DAY   \n";
        query +=" AND  ZA.REPT_NUM(+)  =  ZB.REPT_NUM   \n";
        query +=" AND  ZA.REPT_GBN(+)  =  ZB.REPT_GBN  GROUP  BY  ZB.UPSO_CD  )  TA  ,  GIBU.TBRA_UPSO  TB  ,  INSA.TINS_MST01  TC  WHERE  TA.UPSO_CD  =  TB.UPSO_CD   \n";
        query +=" AND  TC.STAFF_NUM(+)  =  TB.STAFF_CD  )  BB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM,  ZB.BSTYP_CD,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD(+)  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN(+)  =  ZB.UPSO_GRAD  )  CC  WHERE  AA.UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  (BB.NEW_DAY  >  :YRMN||'32'   \n";
        query +=" OR  BB.NEW_DAY  IS  NULL)   \n";
        query +=" AND  BB.UPSO_CD  =  AA.UPSO_CD   \n";
        query +=" AND  CC.UPSO_CD  =  AA.UPSO_CD  ORDER  BY  UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$col_upso_list
    //##**$$end
}
