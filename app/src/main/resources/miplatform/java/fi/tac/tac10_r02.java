
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r02
{
    public tac10_r02()
    {
    }
    //##**$$tac10_r02_dt_select
    /* * 프로그램명 : tac10_r02
    * 작성자 : 성낙문
    * 작성일 : 2009/11/18
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac10_r02_dt_select(DOBJ dobj)
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
            dobj  = CALLtac10_r02_dt_select_SEL1(Conn, dobj);           //  월단위 매체별 지급내역(디데일)
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
    public DOBJ CTLtac10_r02_dt_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r02_dt_select_SEL1(Conn, dobj);           //  월단위 매체별 지급내역(디데일)
        return dobj;
    }
    // 월단위 매체별 지급내역(디데일)
    public DOBJ CALLtac10_r02_dt_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r02_dt_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r02_dt_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   DISTR_YRMN = dobj.getRetObject("S").getRecord().get("DISTR_YRMN");   //분배 년월
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  RW,  DISTR_YRMN,  SUM(DISTR_AMT1)  DISTR_AMT1,  SUM(DISTR_AMT2)  DISTR_AMT2,  SUM(DISTR_AMT3)  DISTR_AMT3,  SUM(DISTR_AMT4)  DISTR_AMT4,  SUM(DISTR_AMT5)  DISTR_AMT5,  SUM(DISTR_AMT6)  DISTR_AMT6,  SUM(DISTR_AMT7)  DISTR_AMT7,  SUM(DISTR_AMT8)  DISTR_AMT8,  SUM(DISTR_AMT9)  DISTR_AMT9,  MAX(DISTR_AMT10)  DISTR_AMT10  FROM  (   \n";
        query +=" SELECT  '1'  AS  RW,  A.SUPP_YRMN  AS  DISTR_YRMN,  SUBSTR(A.MDM_CD,1,2)  MDM_CD,  DECODE(B.PRNT_SEQ,  '1',  SUM(A.ORGDISTR_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '4',  SUM(A.ORGDISTR_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '7',  SUM(A.ORGDISTR_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '10',  SUM(A.ORGDISTR_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '13',  SUM(A.ORGDISTR_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '16',  SUM(A.ORGDISTR_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '19',  SUM(A.ORGDISTR_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '22',  SUM(A.ORGDISTR_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '25',  SUM(A.ORGDISTR_AMT))  DISTR_AMT9,  0  AS  DISTR_AMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  SUBSTR(A.SUPP_YRMN,1,4)  =  SUBSTR(:DISTR_YRMN,1,4)   \n";
        query +=" AND  A.MB_CD  =  :MB_CD   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'  GROUP  BY  A.SUPP_YRMN,  SUBSTR(A.MDM_CD,1,2),  B.PRNT_SEQ  UNION  ALL   \n";
        query +=" SELECT  '2'  AS  RW,  A.SUPP_YRMN  AS  DISTR_YRMN,  SUBSTR(A.MDM_CD,1,2)  MDM_CD,  DECODE(B.PRNT_SEQ,  '2',  SUM(A.ORGDISTR_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '5',  SUM(A.ORGDISTR_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '8',  SUM(A.ORGDISTR_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '11',  SUM(A.ORGDISTR_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '14',  SUM(A.ORGDISTR_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '17',  SUM(A.ORGDISTR_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '20',  SUM(A.ORGDISTR_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '23',  SUM(A.ORGDISTR_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '26',  SUM(A.ORGDISTR_AMT))  DISTR_AMT9,  0  AS  DISTR_AMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  SUBSTR(A.SUPP_YRMN,1,4)  =  SUBSTR(:DISTR_YRMN,1,4)   \n";
        query +=" AND  A.MB_CD  =  :MB_CD   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'  GROUP  BY  A.SUPP_YRMN,  SUBSTR(A.MDM_CD,1,2),  B.PRNT_SEQ  UNION  ALL   \n";
        query +=" SELECT  '3'  AS  RW,  A.SUPP_YRMN  AS  DISTR_YRMN,  SUBSTR(A.MDM_CD,1,2)  MDM_CD,  DECODE(B.PRNT_SEQ,  '3',  SUM(A.ORGDISTR_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '6',  SUM(A.ORGDISTR_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '9',  SUM(A.ORGDISTR_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '12',  SUM(A.ORGDISTR_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '15',  SUM(A.ORGDISTR_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '18',  SUM(A.ORGDISTR_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '21',  SUM(A.ORGDISTR_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '24',  SUM(A.ORGDISTR_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '27',  SUM(A.ORGDISTR_AMT))  DISTR_AMT9,  (   \n";
        query +=" SELECT  SUM(ORGDISTR_AMT)  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  TB1,FIDU.TENV_AVECLASSCD  TB2  WHERE  TB1.SUPP_YRMN  =  A.SUPP_YRMN   \n";
        query +=" AND  TB1.MB_CD  =  :MB_CD   \n";
        query +=" AND  TB1.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'   \n";
        query +=" AND  SUBSTR(TB1.MDM_CD,1,2)  =  TB2.AVECLASS_CD  )  DISTR_AMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  SUBSTR(A.SUPP_YRMN,1,4)  =  SUBSTR(:DISTR_YRMN,1,4)   \n";
        query +=" AND  A.MB_CD  =  :MB_CD   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'  GROUP  BY  A.SUPP_YRMN,  SUBSTR(A.MDM_CD,1,2),  B.PRNT_SEQ  )X  GROUP  BY  DISTR_YRMN,  RW  ORDER  BY  DISTR_YRMN ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("DISTR_YRMN", DISTR_YRMN);               //분배 년월
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        return sobj;
    }
    //##**$$tac10_r02_dt_select
    //##**$$tac10_r02_prt_select
    /* * 프로그램명 : tac10_r02
    * 작성자 : 성낙문
    * 작성일 : 2009/10/13
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac10_r02_prt_select(DOBJ dobj)
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
            dobj  = CALLtac10_r02_prt_select_SEL1(Conn, dobj);           //  월단위 매체별 지급내역(출력)
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
    public DOBJ CTLtac10_r02_prt_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r02_prt_select_SEL1(Conn, dobj);           //  월단위 매체별 지급내역(출력)
        return dobj;
    }
    // 월단위 매체별 지급내역(출력)
    public DOBJ CALLtac10_r02_prt_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r02_prt_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r02_prt_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   DISTR_YRMN = dobj.getRetObject("S").getRecord().get("DISTR_YRMN");   //분배 년월
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  C.DISTR_YRMN,  SUM(C.DISTR_AMT1)  AS  DISTR_AMT1,  SUM(C.DISTR_AMT2)  AS  DISTR_AMT2,  SUM(C.DISTR_AMT3)  AS  DISTR_AMT3,  SUM(C.DISTR_AMT4)  AS  DISTR_AMT4,  SUM(C.DISTR_AMT5)  AS  DISTR_AMT5,  SUM(C.DISTR_AMT6)  AS  DISTR_AMT6,  SUM(C.DISTR_AMT7)  AS  DISTR_AMT7,  SUM(C.DISTR_AMT8)  AS  DISTR_AMT8,  SUM(C.DISTR_AMT9)  AS  DISTR_AMT9,  SUM(C.DISTR_AMT10)  AS  DISTR_AMT10,  SUM(C.DISTR_AMT11)  AS  DISTR_AMT11,  SUM(C.DISTR_AMT12)  AS  DISTR_AMT12,  SUM(C.DISTR_AMT13)  AS  DISTR_AMT13,  SUM(C.DISTR_AMT14)  AS  DISTR_AMT14,  SUM(C.DISTR_AMT15)  AS  DISTR_AMT15,  SUM(C.DISTR_AMT16)  AS  DISTR_AMT16,  SUM(C.DISTR_AMT17)  AS  DISTR_AMT17,  SUM(C.DISTR_AMT18)  AS  DISTR_AMT18,  SUM(C.DISTR_AMT19)  AS  DISTR_AMT19,  SUM(C.DISTR_AMT20)  AS  DISTR_AMT20,  SUM(C.DISTR_AMT21)  AS  DISTR_AMT21,  SUM(C.DISTR_AMT22)  AS  DISTR_AMT22,  SUM(C.DISTR_AMT23)  AS  DISTR_AMT23,  SUM(C.DISTR_AMT24)  AS  DISTR_AMT24,  SUM(C.DISTR_AMT25)  AS  DISTR_AMT25,  SUM(C.DISTR_AMT26)  AS  DISTR_AMT26,  SUM(C.DISTR_AMT27)  AS  DISTR_AMT27,  MAX(C.DISTR_TOT)  AS  DISTR_TOT,  MAX(E.DISTR_TAMT1  )  DISTR_TAMT1  ,  MAX(E.DISTR_TAMT2  )  DISTR_TAMT2  ,  MAX(E.DISTR_TAMT3  )  DISTR_TAMT3  ,  MAX(E.DISTR_TAMT4  )  DISTR_TAMT4  ,  MAX(E.DISTR_TAMT5  )  DISTR_TAMT5  ,  MAX(E.DISTR_TAMT6  )  DISTR_TAMT6  ,  MAX(E.DISTR_TAMT7  )  DISTR_TAMT7  ,  MAX(E.DISTR_TAMT8  )  DISTR_TAMT8  ,  MAX(E.DISTR_TAMT9  )  DISTR_TAMT9  ,  MAX(E.DISTR_TAMT10)  DISTR_TAMT10,  MAX(E.DISTR_TAMT11)  DISTR_TAMT11,  MAX(E.DISTR_TAMT12)  DISTR_TAMT12,  MAX(E.DISTR_TAMT13)  DISTR_TAMT13,  MAX(E.DISTR_TAMT14)  DISTR_TAMT14,  MAX(E.DISTR_TAMT15)  DISTR_TAMT15,  MAX(E.DISTR_TAMT16)  DISTR_TAMT16,  MAX(E.DISTR_TAMT17)  DISTR_TAMT17,  MAX(E.DISTR_TAMT18)  DISTR_TAMT18,  MAX(E.DISTR_TAMT19)  DISTR_TAMT19,  MAX(E.DISTR_TAMT20)  DISTR_TAMT20,  MAX(E.DISTR_TAMT21)  DISTR_TAMT21,  MAX(E.DISTR_TAMT22)  DISTR_TAMT22,  MAX(E.DISTR_TAMT23)  DISTR_TAMT23,  MAX(E.DISTR_TAMT24)  DISTR_TAMT24,  MAX(E.DISTR_TAMT25)  DISTR_TAMT25,  MAX(E.DISTR_TAMT26)  DISTR_TAMT26,  MAX(E.DISTR_TAMT27)  DISTR_TAMT27  FROM  (   \n";
        query +=" SELECT  A.SUPP_YRMN  AS  DISTR_YRMN,  DECODE(B.PRNT_SEQ,  '1',  SUM(A.ORGDISTR_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '2',  SUM(A.ORGDISTR_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '3',  SUM(A.ORGDISTR_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '4',  SUM(A.ORGDISTR_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '5',  SUM(A.ORGDISTR_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '6',  SUM(A.ORGDISTR_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '7',  SUM(A.ORGDISTR_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '8',  SUM(A.ORGDISTR_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '9',  SUM(A.ORGDISTR_AMT))  DISTR_AMT9,  DECODE(B.PRNT_SEQ,  '10',  SUM(A.ORGDISTR_AMT))  DISTR_AMT10,  DECODE(B.PRNT_SEQ,  '11',  SUM(A.ORGDISTR_AMT))  DISTR_AMT11,  DECODE(B.PRNT_SEQ,  '12',  SUM(A.ORGDISTR_AMT))  DISTR_AMT12,  DECODE(B.PRNT_SEQ,  '13',  SUM(A.ORGDISTR_AMT))  DISTR_AMT13,  DECODE(B.PRNT_SEQ,  '14',  SUM(A.ORGDISTR_AMT))  DISTR_AMT14,  DECODE(B.PRNT_SEQ,  '15',  SUM(A.ORGDISTR_AMT))  DISTR_AMT15,  DECODE(B.PRNT_SEQ,  '16',  SUM(A.ORGDISTR_AMT))  DISTR_AMT16,  DECODE(B.PRNT_SEQ,  '17',  SUM(A.ORGDISTR_AMT))  DISTR_AMT17,  DECODE(B.PRNT_SEQ,  '18',  SUM(A.ORGDISTR_AMT))  DISTR_AMT18,  DECODE(B.PRNT_SEQ,  '19',  SUM(A.ORGDISTR_AMT))  DISTR_AMT19,  DECODE(B.PRNT_SEQ,  '20',  SUM(A.ORGDISTR_AMT))  DISTR_AMT20,  DECODE(B.PRNT_SEQ,  '21',  SUM(A.ORGDISTR_AMT))  DISTR_AMT21,  DECODE(B.PRNT_SEQ,  '22',  SUM(A.ORGDISTR_AMT))  DISTR_AMT22,  DECODE(B.PRNT_SEQ,  '23',  SUM(A.ORGDISTR_AMT))  DISTR_AMT23,  DECODE(B.PRNT_SEQ,  '24',  SUM(A.ORGDISTR_AMT))  DISTR_AMT24,  DECODE(B.PRNT_SEQ,  '25',  SUM(A.ORGDISTR_AMT))  DISTR_AMT25,  DECODE(B.PRNT_SEQ,  '26',  SUM(A.ORGDISTR_AMT))  DISTR_AMT26,  DECODE(B.PRNT_SEQ,  '27',  SUM(A.ORGDISTR_AMT))  DISTR_AMT27,  (   \n";
        query +=" SELECT  SUM(ORGDISTR_AMT)  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  TB1,FIDU.TENV_AVECLASSCD  TB2  WHERE  TB1.SUPP_YRMN  =  A.SUPP_YRMN   \n";
        query +=" AND  TB1.MB_CD  =  :MB_CD   \n";
        query +=" AND  TB1.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'   \n";
        query +=" AND  SUBSTR(TB1.MDM_CD,1,2)  =  TB2.AVECLASS_CD  )  DISTR_TOT  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  SUBSTR(A.SUPP_YRMN,1,4)  =  SUBSTR(:DISTR_YRMN,1,4)   \n";
        query +=" AND  A.MB_CD  =  :MB_CD   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'  GROUP  BY  A.SUPP_YRMN,  B.PRNT_SEQ  )C,  (   \n";
        query +=" SELECT  MAX(X.DISTR_TAMT1  )  DISTR_TAMT1  ,  MAX(X.DISTR_TAMT2  )  DISTR_TAMT2  ,  MAX(X.DISTR_TAMT3  )  DISTR_TAMT3  ,  MAX(X.DISTR_TAMT4  )  DISTR_TAMT4  ,  MAX(X.DISTR_TAMT5  )  DISTR_TAMT5  ,  MAX(X.DISTR_TAMT6  )  DISTR_TAMT6  ,  MAX(X.DISTR_TAMT7  )  DISTR_TAMT7  ,  MAX(X.DISTR_TAMT8  )  DISTR_TAMT8  ,  MAX(X.DISTR_TAMT9  )  DISTR_TAMT9  ,  MAX(X.DISTR_TAMT10)  DISTR_TAMT10,  MAX(X.DISTR_TAMT11)  DISTR_TAMT11,  MAX(X.DISTR_TAMT12)  DISTR_TAMT12,  MAX(X.DISTR_TAMT13)  DISTR_TAMT13,  MAX(X.DISTR_TAMT14)  DISTR_TAMT14,  MAX(X.DISTR_TAMT15)  DISTR_TAMT15,  MAX(X.DISTR_TAMT16)  DISTR_TAMT16,  MAX(X.DISTR_TAMT17)  DISTR_TAMT17,  MAX(X.DISTR_TAMT18)  DISTR_TAMT18,  MAX(X.DISTR_TAMT19)  DISTR_TAMT19,  MAX(X.DISTR_TAMT20)  DISTR_TAMT20,  MAX(X.DISTR_TAMT21)  DISTR_TAMT21,  MAX(X.DISTR_TAMT22)  DISTR_TAMT22,  MAX(X.DISTR_TAMT23)  DISTR_TAMT23,  MAX(X.DISTR_TAMT24)  DISTR_TAMT24,  MAX(X.DISTR_TAMT25)  DISTR_TAMT25,  MAX(X.DISTR_TAMT26)  DISTR_TAMT26,  MAX(X.DISTR_TAMT27)  DISTR_TAMT27  FROM  (   \n";
        query +=" SELECT  NVL(  DECODE(PRNT_SEQ,  '1',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT1,  NVL(  DECODE(PRNT_SEQ,  '2',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT2,  NVL(  DECODE(PRNT_SEQ,  '3',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT3,  NVL(  DECODE(PRNT_SEQ,  '4',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT4,  NVL(  DECODE(PRNT_SEQ,  '5',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT5,  NVL(  DECODE(PRNT_SEQ,  '6',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT6,  NVL(  DECODE(PRNT_SEQ,  '7',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT7,  NVL(  DECODE(PRNT_SEQ,  '8',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT8,  NVL(  DECODE(PRNT_SEQ,  '9',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT9,  NVL(  DECODE(PRNT_SEQ,  '10',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT10,  NVL(  DECODE(PRNT_SEQ,  '11',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT11,  NVL(  DECODE(PRNT_SEQ,  '12',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT12,  NVL(  DECODE(PRNT_SEQ,  '13',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT13,  NVL(  DECODE(PRNT_SEQ,  '14',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT14,  NVL(  DECODE(PRNT_SEQ,  '15',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT15,  NVL(  DECODE(PRNT_SEQ,  '16',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT16,  NVL(  DECODE(PRNT_SEQ,  '17',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT17,  NVL(  DECODE(PRNT_SEQ,  '18',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT18,  NVL(  DECODE(PRNT_SEQ,  '19',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT19,  NVL(  DECODE(PRNT_SEQ,  '20',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT20,  NVL(  DECODE(PRNT_SEQ,  '21',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT21,  NVL(  DECODE(PRNT_SEQ,  '22',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT22,  NVL(  DECODE(PRNT_SEQ,  '23',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT23,  NVL(  DECODE(PRNT_SEQ,  '24',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT24,  NVL(  DECODE(PRNT_SEQ,  '25',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT25,  NVL(  DECODE(PRNT_SEQ,  '26',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT26,  NVL(  DECODE(PRNT_SEQ,  '27',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT27  FROM  FIDU.TENV_AVECLASSCD  )  X  )  E  GROUP  BY  C.DISTR_YRMN  ORDER  BY  C.DISTR_YRMN ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("DISTR_YRMN", DISTR_YRMN);               //분배 년월
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        return sobj;
    }
    //##**$$tac10_r02_prt_select
    //##**$$tac10_r02_sum_select
    /* * 프로그램명 : tac10_r02
    * 작성자 : 성낙문
    * 작성일 : 2009/11/18
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac10_r02_sum_select(DOBJ dobj)
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
            dobj  = CALLtac10_r02_sum_select_SEL1(Conn, dobj);           //  월단위 매체별 지급내역(합계)
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
    public DOBJ CTLtac10_r02_sum_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r02_sum_select_SEL1(Conn, dobj);           //  월단위 매체별 지급내역(합계)
        return dobj;
    }
    // 월단위 매체별 지급내역(합계)
    public DOBJ CALLtac10_r02_sum_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r02_sum_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r02_sum_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   DISTR_YRMN = dobj.getRetObject("S").getRecord().get("DISTR_YRMN");   //분배 년월
        String   TRNSF_GBN = dobj.getRetObject("S").getRecord().get("TRNSF_GBN");   //양도 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  RW,  DISTR_YRMN,  SUM(DISTR_SAMT1)  DISTR_SAMT1,  SUM(DISTR_SAMT2)  DISTR_SAMT2,  SUM(DISTR_SAMT3)  DISTR_SAMT3,  SUM(DISTR_SAMT4)  DISTR_SAMT4,  SUM(DISTR_SAMT5)  DISTR_SAMT5,  SUM(DISTR_SAMT6)  DISTR_SAMT6,  SUM(DISTR_SAMT7)  DISTR_SAMT7,  SUM(DISTR_SAMT8)  DISTR_SAMT8,  SUM(DISTR_SAMT9)  DISTR_SAMT9,  MAX(DISTR_SAMT10)  DISTR_SAMT10  FROM  (   \n";
        query +=" SELECT  '1'  RW,  SUBSTR(A.SUPP_YRMN,1,4)  DISTR_YRMN,  DECODE(B.PRNT_SEQ,  '1',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT1,  DECODE(B.PRNT_SEQ,  '4',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT2,  DECODE(B.PRNT_SEQ,  '7',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT3,  DECODE(B.PRNT_SEQ,  '10',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT4,  DECODE(B.PRNT_SEQ,  '13',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT5,  DECODE(B.PRNT_SEQ,  '16',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT6,  DECODE(B.PRNT_SEQ,  '19',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT7,  DECODE(B.PRNT_SEQ,  '22',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT8,  DECODE(B.PRNT_SEQ,  '25',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT9,  0  AS  DISTR_SAMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  SUBSTR(A.SUPP_YRMN,1,4)  =  SUBSTR(:DISTR_YRMN,1,4)   \n";
        query +=" AND  A.MB_CD  =  :MB_CD   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'  GROUP  BY  SUBSTR(A.SUPP_YRMN,1,4),  B.PRNT_SEQ  UNION  ALL   \n";
        query +=" SELECT  '2'  RW,  SUBSTR(A.SUPP_YRMN,1,4)  AS  DISTR_YRMN,  DECODE(B.PRNT_SEQ,  '2',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT1,  DECODE(B.PRNT_SEQ,  '5',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT2,  DECODE(B.PRNT_SEQ,  '8',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT3,  DECODE(B.PRNT_SEQ,  '11',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT4,  DECODE(B.PRNT_SEQ,  '14',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT5,  DECODE(B.PRNT_SEQ,  '17',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT6,  DECODE(B.PRNT_SEQ,  '20',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT7,  DECODE(B.PRNT_SEQ,  '23',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT8,  DECODE(B.PRNT_SEQ,  '26',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT9,  0  AS  DISTR_SAMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  SUBSTR(A.SUPP_YRMN,1,4)  =  SUBSTR(:DISTR_YRMN,1,4)   \n";
        query +=" AND  A.MB_CD  =  :MB_CD   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'  GROUP  BY  SUBSTR(A.SUPP_YRMN,1,4),  B.PRNT_SEQ  UNION  ALL   \n";
        query +=" SELECT  '3'  RW,  SUBSTR(A.SUPP_YRMN,1,4)  AS  DISTR_YRMN,  DECODE(B.PRNT_SEQ,  '3',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT1,  DECODE(B.PRNT_SEQ,  '6',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT2,  DECODE(B.PRNT_SEQ,  '9',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT3,  DECODE(B.PRNT_SEQ,  '12',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT4,  DECODE(B.PRNT_SEQ,  '15',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT5,  DECODE(B.PRNT_SEQ,  '18',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT6,  DECODE(B.PRNT_SEQ,  '21',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT7,  DECODE(B.PRNT_SEQ,  '24',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT8,  DECODE(B.PRNT_SEQ,  '27',  SUM(A.ORGDISTR_AMT))  DISTR_SAMT9,  (   \n";
        query +=" SELECT  SUM(ORGDISTR_AMT)  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  TB1,FIDU.TENV_AVECLASSCD  TB2  WHERE  SUBSTR(TB1.SUPP_YRMN,1,4)  =  SUBSTR(:DISTR_YRMN,1,4)   \n";
        query +=" AND  TB1.MB_CD  =  :MB_CD   \n";
        query +=" AND  TB1.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'   \n";
        query +=" AND  SUBSTR(TB1.MDM_CD,1,2)  =  TB2.AVECLASS_CD  )  DISTR_SAMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  SUBSTR(A.SUPP_YRMN,1,4)  =  SUBSTR(:DISTR_YRMN,1,4)   \n";
        query +=" AND  A.MB_CD  =  :MB_CD   \n";
        query +=" AND  A.TRNSF_GBN  LIKE  :TRNSF_GBN  ||  '%'  GROUP  BY  SUBSTR(A.SUPP_YRMN,1,4),  B.PRNT_SEQ  )X  GROUP  BY  RW,  DISTR_YRMN  ORDER  BY  DISTR_YRMN ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("DISTR_YRMN", DISTR_YRMN);               //분배 년월
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        return sobj;
    }
    //##**$$tac10_r02_sum_select
    //##**$$tac10_r02_hd_select
    /* * 프로그램명 : tac10_r02
    * 작성자 : 성낙문
    * 작성일 : 2009/11/18
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac10_r02_hd_select(DOBJ dobj)
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
            dobj  = CALLtac10_r02_hd_select_SEL1(Conn, dobj);           //  월단위 매체별 지급내역(헤더)
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
    public DOBJ CTLtac10_r02_hd_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r02_hd_select_SEL1(Conn, dobj);           //  월단위 매체별 지급내역(헤더)
        return dobj;
    }
    // 월단위 매체별 지급내역(헤더)
    public DOBJ CALLtac10_r02_hd_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r02_hd_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r02_hd_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  RW,  MAX(DISTR_TAMT0)  DISTR_TAMT0,  MAX(DISTR_TAMT1)  DISTR_TAMT1,  MAX(DISTR_TAMT2)  DISTR_TAMT2,  MAX(DISTR_TAMT3)  DISTR_TAMT3,  MAX(DISTR_TAMT4)  DISTR_TAMT4,  MAX(DISTR_TAMT5)  DISTR_TAMT5,  MAX(DISTR_TAMT6)  DISTR_TAMT6,  MAX(DISTR_TAMT7)  DISTR_TAMT7,  MAX(DISTR_TAMT8)  DISTR_TAMT8,  MAX(DISTR_TAMT9)  DISTR_TAMT9,  MAX(DISTR_TAMT10)  DISTR_TAMT10  FROM  (   \n";
        query +=" SELECT  '1'  AS  RW,  '년월'  AS  DISTR_TAMT0,  DECODE(PRNT_SEQ,  '1',  AVECLASS_CD_NM)  DISTR_TAMT1,  DECODE(PRNT_SEQ,  '4',  AVECLASS_CD_NM)  DISTR_TAMT2,  DECODE(PRNT_SEQ,  '7',  AVECLASS_CD_NM)  DISTR_TAMT3,  DECODE(PRNT_SEQ,  '10',  AVECLASS_CD_NM)  DISTR_TAMT4,  DECODE(PRNT_SEQ,  '13',  AVECLASS_CD_NM)  DISTR_TAMT5,  DECODE(PRNT_SEQ,  '16',  AVECLASS_CD_NM)  DISTR_TAMT6,  DECODE(PRNT_SEQ,  '19',  AVECLASS_CD_NM)  DISTR_TAMT7,  DECODE(PRNT_SEQ,  '22',  AVECLASS_CD_NM)  DISTR_TAMT8,  DECODE(PRNT_SEQ,  '25',  AVECLASS_CD_NM)  DISTR_TAMT9,  ''  AS  DISTR_TAMT10  FROM  FIDU.TENV_AVECLASSCD  B  UNION  ALL   \n";
        query +=" SELECT  '2'  AS  RW,  ''  AS  DISTR_TAMT0,  DECODE(PRNT_SEQ,  '2',  AVECLASS_CD_NM)  DISTR_TAMT1,  DECODE(PRNT_SEQ,  '5',  AVECLASS_CD_NM)  DISTR_TAMT2,  DECODE(PRNT_SEQ,  '8',  AVECLASS_CD_NM)  DISTR_TAMT3,  DECODE(PRNT_SEQ,  '11',  AVECLASS_CD_NM)  DISTR_TAMT4,  DECODE(PRNT_SEQ,  '14',  AVECLASS_CD_NM)  DISTR_TAMT5,  DECODE(PRNT_SEQ,  '17',  AVECLASS_CD_NM)  DISTR_TAMT6,  DECODE(PRNT_SEQ,  '20',  AVECLASS_CD_NM)  DISTR_TAMT7,  DECODE(PRNT_SEQ,  '23',  AVECLASS_CD_NM)  DISTR_TAMT8,  DECODE(PRNT_SEQ,  '26',  AVECLASS_CD_NM)  DISTR_TAMT9,  ''  AS  DISTR_TAMT10  FROM  FIDU.TENV_AVECLASSCD  B  UNION  ALL   \n";
        query +=" SELECT  '3'  AS  RW,  ''  AS  DISTR_TAMT0,  DECODE(PRNT_SEQ,  '3',  AVECLASS_CD_NM)  DISTR_TAMT1,  DECODE(PRNT_SEQ,  '6',  AVECLASS_CD_NM)  DISTR_TAMT2,  DECODE(PRNT_SEQ,  '9',  AVECLASS_CD_NM)  DISTR_TAMT3,  DECODE(PRNT_SEQ,  '12',  AVECLASS_CD_NM)  DISTR_TAMT4,  DECODE(PRNT_SEQ,  '15',  AVECLASS_CD_NM)  DISTR_TAMT5,  DECODE(PRNT_SEQ,  '18',  AVECLASS_CD_NM)  DISTR_TAMT6,  DECODE(PRNT_SEQ,  '21',  AVECLASS_CD_NM)  DISTR_TAMT7,  DECODE(PRNT_SEQ,  '24',  AVECLASS_CD_NM)  DISTR_TAMT8,  DECODE(PRNT_SEQ,  '27',  AVECLASS_CD_NM)  DISTR_TAMT9,  '합  계'  AS  DISTR_TAMT10  FROM  FIDU.TENV_AVECLASSCD  )X  GROUP  BY  RW  ORDER  BY  RW ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$tac10_r02_hd_select
    //##**$$end
}
