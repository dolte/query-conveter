
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r09
{
    public tac10_r09()
    {
    }
    //##**$$tac10_r09_dt_select
    /* * 프로그램명 : tac10_r09
    * 작성자 : 성낙문
    * 작성일 : 2009/10/20
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac10_r09_dt_select(DOBJ dobj)
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
            dobj  = CALLtac10_r09_dt_select_SEL1(Conn, dobj);           //  출판사 부가세 명세서(디테일)
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
    public DOBJ CTLtac10_r09_dt_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r09_dt_select_SEL1(Conn, dobj);           //  출판사 부가세 명세서(디테일)
        return dobj;
    }
    // 출판사 부가세 명세서(디테일)
    public DOBJ CALLtac10_r09_dt_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r09_dt_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r09_dt_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  RW,  MB_CD,  HANMB_NM,  SUM(DISTR_AMT1)  DISTR_AMT1,  SUM(DISTR_AMT2)  DISTR_AMT2,  SUM(DISTR_AMT3)  DISTR_AMT3,  SUM(DISTR_AMT4)  DISTR_AMT4,  SUM(DISTR_AMT5)  DISTR_AMT5,  SUM(DISTR_AMT6)  DISTR_AMT6,  SUM(DISTR_AMT7)  DISTR_AMT7,  SUM(DISTR_AMT8)  DISTR_AMT8,  SUM(DISTR_AMT9)  DISTR_AMT9,  MAX(DISTR_AMT10)  DISTR_AMT10  FROM  (   \n";
        query +=" SELECT  '1'  AS  RW,  A.MB_CD,  A.MB_CD  AS  HANMB_NM,  DECODE(B.PRNT_SEQ,  '1',  SUM(A.ORGDISTR_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '4',  SUM(A.ORGDISTR_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '7',  SUM(A.ORGDISTR_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '10',  SUM(A.ORGDISTR_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '13',  SUM(A.ORGDISTR_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '16',  SUM(A.ORGDISTR_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '19',  SUM(A.ORGDISTR_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '22',  SUM(A.ORGDISTR_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '25',  SUM(A.ORGDISTR_AMT))  DISTR_AMT9,  0  AS  DISTR_AMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  (   \n";
        query +=" SELECT  C.MB_GBN1  FROM  FIDU.TMEM_MB  C  WHERE  MB_CD  =  A.MB_CD)  IN  ('P','F')  GROUP  BY  A.MB_CD,  B.PRNT_SEQ  UNION  ALL   \n";
        query +=" SELECT  '2'  AS  RW,  A.MB_CD,  (   \n";
        query +=" SELECT  D.HANMB_NM  FROM  FIDU.TMEM_MB  D  WHERE  D.MB_CD  =  A.MB_CD  )  AS  HANMB_NM,  DECODE(B.PRNT_SEQ,  '1',  SUM(A.ATAX_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '4',  SUM(A.ATAX_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '7',  SUM(A.ATAX_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '10',  SUM(A.ATAX_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '13',  SUM(A.ATAX_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '16',  SUM(A.ATAX_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '19',  SUM(A.ATAX_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '22',  SUM(A.ATAX_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '25',  SUM(A.ATAX_AMT))  DISTR_AMT9,  0  AS  DISTR_AMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  (   \n";
        query +=" SELECT  C.MB_GBN1  FROM  FIDU.TMEM_MB  C  WHERE  MB_CD  =  A.MB_CD)  IN  ('P','F')  GROUP  BY  A.MB_CD,  B.PRNT_SEQ  UNION  ALL   \n";
        query +=" SELECT  '3'  AS  RW,  A.MB_CD,  ''  AS  HANMB_NM,  DECODE(B.PRNT_SEQ,  '2',  SUM(A.ORGDISTR_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '5',  SUM(A.ORGDISTR_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '8',  SUM(A.ORGDISTR_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '11',  SUM(A.ORGDISTR_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '14',  SUM(A.ORGDISTR_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '17',  SUM(A.ORGDISTR_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '20',  SUM(A.ORGDISTR_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '23',  SUM(A.ORGDISTR_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '26',  SUM(A.ORGDISTR_AMT))  DISTR_AMT9,  0  AS  DISTR_AMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  (   \n";
        query +=" SELECT  C.MB_GBN1  FROM  FIDU.TMEM_MB  C  WHERE  MB_CD  =  A.MB_CD)  IN  ('P','F')  GROUP  BY  A.MB_CD,  B.PRNT_SEQ  UNION  ALL   \n";
        query +=" SELECT  '4'  AS  RW,  A.MB_CD,  ''  AS  HANMB_NM,  DECODE(B.PRNT_SEQ,  '2',  SUM(A.ATAX_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '5',  SUM(A.ATAX_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '8',  SUM(A.ATAX_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '11',  SUM(A.ATAX_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '14',  SUM(A.ATAX_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '17',  SUM(A.ATAX_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '20',  SUM(A.ATAX_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '23',  SUM(A.ATAX_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '26',  SUM(A.ATAX_AMT))  DISTR_AMT9,  0  AS  DISTR_AMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  (   \n";
        query +=" SELECT  C.MB_GBN1  FROM  FIDU.TMEM_MB  C  WHERE  MB_CD  =  A.MB_CD)  IN  ('P','F')  GROUP  BY  A.MB_CD,  B.PRNT_SEQ  UNION  ALL   \n";
        query +=" SELECT  '5'  AS  RW,  A.MB_CD,  ''  AS  HANMB_NM,  DECODE(B.PRNT_SEQ,  '3',  SUM(A.ORGDISTR_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '6',  SUM(A.ORGDISTR_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '9',  SUM(A.ORGDISTR_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '12',  SUM(A.ORGDISTR_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '15',  SUM(A.ORGDISTR_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '18',  SUM(A.ORGDISTR_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '21',  SUM(A.ORGDISTR_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '24',  SUM(A.ORGDISTR_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '27',  SUM(A.ORGDISTR_AMT))  DISTR_AMT9,   \n";
        query +=" (SELECT  SUM(ORGDISTR_AMT)  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  WHERE  SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  MB_CD  =  A.MB_CD  )  DISTR_AMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  (   \n";
        query +=" SELECT  C.MB_GBN1  FROM  FIDU.TMEM_MB  C  WHERE  MB_CD  =  A.MB_CD)  IN  ('P','F')  GROUP  BY  A.MB_CD,  B.PRNT_SEQ  UNION  ALL   \n";
        query +=" SELECT  '6'  AS  RW,  A.MB_CD,  ''  AS  HANMB_NM,  DECODE(B.PRNT_SEQ,  '3',  SUM(A.ATAX_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '6',  SUM(A.ATAX_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '9',  SUM(A.ATAX_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '12',  SUM(A.ATAX_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '15',  SUM(A.ATAX_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '18',  SUM(A.ATAX_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '21',  SUM(A.ATAX_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '24',  SUM(A.ATAX_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '27',  SUM(A.ATAX_AMT))  DISTR_AMT9,   \n";
        query +=" (SELECT  SUM(ATAX_AMT)  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  WHERE  SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  MB_CD  =  A.MB_CD  )  DISTR_AMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  (   \n";
        query +=" SELECT  C.MB_GBN1  FROM  FIDU.TMEM_MB  C  WHERE  MB_CD  =  A.MB_CD)  IN  ('P','F')  GROUP  BY  A.MB_CD,  B.PRNT_SEQ  )X  GROUP  BY  RW,  MB_CD,  HANMB_NM  ORDER  BY  MB_CD,  RW ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        return sobj;
    }
    //##**$$tac10_r09_dt_select
    //##**$$tac10_r09_prt_select
    /* * 프로그램명 : tac10_r09
    * 작성자 : 손주환
    * 작성일 : 2009/10/23
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac10_r09_prt_select(DOBJ dobj)
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
            dobj  = CALLtac10_r09_prt_select_SEL1(Conn, dobj);           //  출판사 부가세 명세서(출력)
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
    public DOBJ CTLtac10_r09_prt_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r09_prt_select_SEL1(Conn, dobj);           //  출판사 부가세 명세서(출력)
        return dobj;
    }
    // 출판사 부가세 명세서(출력)
    public DOBJ CALLtac10_r09_prt_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r09_prt_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r09_prt_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  C.MB_CD,  C.HANMB_NM,  SUM(C.DISTR_AMT1)  AS  DISTR_AMT1,  SUM(C.DISTR_AMT2)  AS  DISTR_AMT2,  SUM(C.DISTR_AMT3)  AS  DISTR_AMT3,  SUM(C.DISTR_AMT4)  AS  DISTR_AMT4,  SUM(C.DISTR_AMT5)  AS  DISTR_AMT5,  SUM(C.DISTR_AMT6)  AS  DISTR_AMT6,  SUM(C.DISTR_AMT7)  AS  DISTR_AMT7,  SUM(C.DISTR_AMT8)  AS  DISTR_AMT8,  SUM(C.DISTR_AMT9)  AS  DISTR_AMT9,  SUM(C.DISTR_AMT10)  AS  DISTR_AMT10,  SUM(C.DISTR_AMT11)  AS  DISTR_AMT11,  SUM(C.DISTR_AMT12)  AS  DISTR_AMT12,  SUM(C.DISTR_AMT13)  AS  DISTR_AMT13,  SUM(C.DISTR_AMT14)  AS  DISTR_AMT14,  SUM(C.DISTR_AMT15)  AS  DISTR_AMT15,  SUM(C.DISTR_AMT16)  AS  DISTR_AMT16,  SUM(C.DISTR_AMT17)  AS  DISTR_AMT17,  SUM(C.DISTR_AMT18)  AS  DISTR_AMT18,  SUM(C.DISTR_AMT19)  AS  DISTR_AMT19,  SUM(C.DISTR_AMT20)  AS  DISTR_AMT20,  SUM(C.DISTR_AMT21)  AS  DISTR_AMT21,  SUM(C.DISTR_AMT22)  AS  DISTR_AMT22,  SUM(C.DISTR_AMT23)  AS  DISTR_AMT23,  SUM(C.DISTR_AMT24)  AS  DISTR_AMT24,  SUM(C.DISTR_AMT25)  AS  DISTR_AMT25,  SUM(C.DISTR_AMT26)  AS  DISTR_AMT26,  SUM(C.DISTR_AMT27)  AS  DISTR_AMT27,  SUM(C.DISTR_XAMT1)  AS  DISTR_XAMT1,  SUM(C.DISTR_XAMT2)  AS  DISTR_XAMT2,  SUM(C.DISTR_XAMT3)  AS  DISTR_XAMT3,  SUM(C.DISTR_XAMT4)  AS  DISTR_XAMT4,  SUM(C.DISTR_XAMT5)  AS  DISTR_XAMT5,  SUM(C.DISTR_XAMT6)  AS  DISTR_XAMT6,  SUM(C.DISTR_XAMT7)  AS  DISTR_XAMT7,  SUM(C.DISTR_XAMT8)  AS  DISTR_XAMT8,  SUM(C.DISTR_XAMT9)  AS  DISTR_XAMT9,  SUM(C.DISTR_XAMT10)  AS  DISTR_XAMT10,  SUM(C.DISTR_XAMT11)  AS  DISTR_XAMT11,  SUM(C.DISTR_XAMT12)  AS  DISTR_XAMT12,  SUM(C.DISTR_XAMT13)  AS  DISTR_XAMT13,  SUM(C.DISTR_XAMT14)  AS  DISTR_XAMT14,  SUM(C.DISTR_XAMT15)  AS  DISTR_XAMT15,  SUM(C.DISTR_XAMT16)  AS  DISTR_XAMT16,  SUM(C.DISTR_XAMT17)  AS  DISTR_XAMT17,  SUM(C.DISTR_XAMT18)  AS  DISTR_XAMT18,  SUM(C.DISTR_XAMT19)  AS  DISTR_XAMT19,  SUM(C.DISTR_XAMT20)  AS  DISTR_XAMT20,  SUM(C.DISTR_XAMT21)  AS  DISTR_XAMT21,  SUM(C.DISTR_XAMT22)  AS  DISTR_XAMT22,  SUM(C.DISTR_XAMT23)  AS  DISTR_XAMT23,  SUM(C.DISTR_XAMT24)  AS  DISTR_XAMT24,  SUM(C.DISTR_XAMT25)  AS  DISTR_XAMT25,  SUM(C.DISTR_XAMT26)  AS  DISTR_XAMT26,  SUM(C.DISTR_XAMT27)  AS  DISTR_XAMT27,  (   \n";
        query +=" SELECT  SUM(TB1.ORGDISTR_AMT)  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  TB1  ,FIDU.TENV_AVECLASSCD  TB2  WHERE  TB1.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  TB1.MB_CD  =C.MB_CD   \n";
        query +=" AND  (   \n";
        query +=" SELECT  TB3.MB_GBN1  FROM  FIDU.TMEM_MB  TB3  WHERE  TB3.MB_CD  =  TB1.MB_CD)  IN  ('P','F')   \n";
        query +=" AND  SUBSTR(TB1.MDM_CD,1,2)  =  TB2.AVECLASS_CD  )  DISTR_TOT,  (   \n";
        query +=" SELECT  SUM(TB1.ATAX_AMT)  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  TB1  ,FIDU.TENV_AVECLASSCD  TB2  WHERE  TB1.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  TB1.MB_CD  =C.MB_CD   \n";
        query +=" AND  (   \n";
        query +=" SELECT  TB3.MB_GBN1  FROM  FIDU.TMEM_MB  TB3  WHERE  TB3.MB_CD  =  TB1.MB_CD)  IN  ('P','F')   \n";
        query +=" AND  SUBSTR(TB1.MDM_CD,1,2)  =  TB2.AVECLASS_CD  )  DISTR_XTOT,  MAX(E.DISTR_TAMT1  )  DISTR_TAMT1  ,  MAX(E.DISTR_TAMT2  )  DISTR_TAMT2  ,  MAX(E.DISTR_TAMT3  )  DISTR_TAMT3  ,  MAX(E.DISTR_TAMT4  )  DISTR_TAMT4  ,  MAX(E.DISTR_TAMT5  )  DISTR_TAMT5  ,  MAX(E.DISTR_TAMT6  )  DISTR_TAMT6  ,  MAX(E.DISTR_TAMT7  )  DISTR_TAMT7  ,  MAX(E.DISTR_TAMT8  )  DISTR_TAMT8  ,  MAX(E.DISTR_TAMT9  )  DISTR_TAMT9  ,  MAX(E.DISTR_TAMT10)  DISTR_TAMT10,  MAX(E.DISTR_TAMT11)  DISTR_TAMT11,  MAX(E.DISTR_TAMT12)  DISTR_TAMT12,  MAX(E.DISTR_TAMT13)  DISTR_TAMT13,  MAX(E.DISTR_TAMT14)  DISTR_TAMT14,  MAX(E.DISTR_TAMT15)  DISTR_TAMT15,  MAX(E.DISTR_TAMT16)  DISTR_TAMT16,  MAX(E.DISTR_TAMT17)  DISTR_TAMT17,  MAX(E.DISTR_TAMT18)  DISTR_TAMT18,  MAX(E.DISTR_TAMT19)  DISTR_TAMT19,  MAX(E.DISTR_TAMT20)  DISTR_TAMT20,  MAX(E.DISTR_TAMT21)  DISTR_TAMT21,  MAX(E.DISTR_TAMT22)  DISTR_TAMT22,  MAX(E.DISTR_TAMT23)  DISTR_TAMT23,  MAX(E.DISTR_TAMT24)  DISTR_TAMT24,  MAX(E.DISTR_TAMT25)  DISTR_TAMT25,  MAX(E.DISTR_TAMT26)  DISTR_TAMT26,  MAX(E.DISTR_TAMT27)  DISTR_TAMT27  FROM  (   \n";
        query +=" SELECT  A.MB_CD,  (   \n";
        query +=" SELECT  D.HANMB_NM  FROM  FIDU.TMEM_MB  D  WHERE  D.MB_CD  =  A.MB_CD  )  AS  HANMB_NM,  DECODE(B.PRNT_SEQ,  '1',  SUM(A.ORGDISTR_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '2',  SUM(A.ORGDISTR_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '3',  SUM(A.ORGDISTR_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '4',  SUM(A.ORGDISTR_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '5',  SUM(A.ORGDISTR_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '6',  SUM(A.ORGDISTR_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '7',  SUM(A.ORGDISTR_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '8',  SUM(A.ORGDISTR_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '9',  SUM(A.ORGDISTR_AMT))  DISTR_AMT9,  DECODE(B.PRNT_SEQ,  '10',  SUM(A.ORGDISTR_AMT))  DISTR_AMT10,  DECODE(B.PRNT_SEQ,  '11',  SUM(A.ORGDISTR_AMT))  DISTR_AMT11,  DECODE(B.PRNT_SEQ,  '12',  SUM(A.ORGDISTR_AMT))  DISTR_AMT12,  DECODE(B.PRNT_SEQ,  '13',  SUM(A.ORGDISTR_AMT))  DISTR_AMT13,  DECODE(B.PRNT_SEQ,  '14',  SUM(A.ORGDISTR_AMT))  DISTR_AMT14,  DECODE(B.PRNT_SEQ,  '15',  SUM(A.ORGDISTR_AMT))  DISTR_AMT15,  DECODE(B.PRNT_SEQ,  '16',  SUM(A.ORGDISTR_AMT))  DISTR_AMT16,  DECODE(B.PRNT_SEQ,  '17',  SUM(A.ORGDISTR_AMT))  DISTR_AMT17,  DECODE(B.PRNT_SEQ,  '18',  SUM(A.ORGDISTR_AMT))  DISTR_AMT18,  DECODE(B.PRNT_SEQ,  '19',  SUM(A.ORGDISTR_AMT))  DISTR_AMT19,  DECODE(B.PRNT_SEQ,  '20',  SUM(A.ORGDISTR_AMT))  DISTR_AMT20,  DECODE(B.PRNT_SEQ,  '21',  SUM(A.ORGDISTR_AMT))  DISTR_AMT21,  DECODE(B.PRNT_SEQ,  '22',  SUM(A.ORGDISTR_AMT))  DISTR_AMT22,  DECODE(B.PRNT_SEQ,  '23',  SUM(A.ORGDISTR_AMT))  DISTR_AMT23,  DECODE(B.PRNT_SEQ,  '24',  SUM(A.ORGDISTR_AMT))  DISTR_AMT24,  DECODE(B.PRNT_SEQ,  '25',  SUM(A.ORGDISTR_AMT))  DISTR_AMT25,  DECODE(B.PRNT_SEQ,  '26',  SUM(A.ORGDISTR_AMT))  DISTR_AMT26,  DECODE(B.PRNT_SEQ,  '27',  SUM(A.ORGDISTR_AMT))  DISTR_AMT27,  DECODE(B.PRNT_SEQ,  '1',  SUM(A.ATAX_AMT))  DISTR_XAMT1,  DECODE(B.PRNT_SEQ,  '2',  SUM(A.ATAX_AMT))  DISTR_XAMT2,  DECODE(B.PRNT_SEQ,  '3',  SUM(A.ATAX_AMT))  DISTR_XAMT3,  DECODE(B.PRNT_SEQ,  '4',  SUM(A.ATAX_AMT))  DISTR_XAMT4,  DECODE(B.PRNT_SEQ,  '5',  SUM(A.ATAX_AMT))  DISTR_XAMT5,  DECODE(B.PRNT_SEQ,  '6',  SUM(A.ATAX_AMT))  DISTR_XAMT6,  DECODE(B.PRNT_SEQ,  '7',  SUM(A.ATAX_AMT))  DISTR_XAMT7,  DECODE(B.PRNT_SEQ,  '8',  SUM(A.ATAX_AMT))  DISTR_XAMT8,  DECODE(B.PRNT_SEQ,  '9',  SUM(A.ATAX_AMT))  DISTR_XAMT9,  DECODE(B.PRNT_SEQ,  '10',  SUM(A.ATAX_AMT))  DISTR_XAMT10,  DECODE(B.PRNT_SEQ,  '11',  SUM(A.ATAX_AMT))  DISTR_XAMT11,  DECODE(B.PRNT_SEQ,  '12',  SUM(A.ATAX_AMT))  DISTR_XAMT12,  DECODE(B.PRNT_SEQ,  '13',  SUM(A.ATAX_AMT))  DISTR_XAMT13,  DECODE(B.PRNT_SEQ,  '14',  SUM(A.ATAX_AMT))  DISTR_XAMT14,  DECODE(B.PRNT_SEQ,  '15',  SUM(A.ATAX_AMT))  DISTR_XAMT15,  DECODE(B.PRNT_SEQ,  '16',  SUM(A.ATAX_AMT))  DISTR_XAMT16,  DECODE(B.PRNT_SEQ,  '17',  SUM(A.ATAX_AMT))  DISTR_XAMT17,  DECODE(B.PRNT_SEQ,  '18',  SUM(A.ATAX_AMT))  DISTR_XAMT18,  DECODE(B.PRNT_SEQ,  '19',  SUM(A.ATAX_AMT))  DISTR_XAMT19,  DECODE(B.PRNT_SEQ,  '20',  SUM(A.ATAX_AMT))  DISTR_XAMT20,  DECODE(B.PRNT_SEQ,  '21',  SUM(A.ATAX_AMT))  DISTR_XAMT21,  DECODE(B.PRNT_SEQ,  '22',  SUM(A.ATAX_AMT))  DISTR_XAMT22,  DECODE(B.PRNT_SEQ,  '23',  SUM(A.ATAX_AMT))  DISTR_XAMT23,  DECODE(B.PRNT_SEQ,  '24',  SUM(A.ATAX_AMT))  DISTR_XAMT24,  DECODE(B.PRNT_SEQ,  '25',  SUM(A.ATAX_AMT))  DISTR_XAMT25,  DECODE(B.PRNT_SEQ,  '26',  SUM(A.ATAX_AMT))  DISTR_XAMT26,  DECODE(B.PRNT_SEQ,  '27',  SUM(A.ATAX_AMT))  DISTR_XAMT27  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  (   \n";
        query +=" SELECT  C.MB_GBN1  FROM  FIDU.TMEM_MB  C  WHERE  MB_CD  =  A.MB_CD)  IN  ('P','F')  GROUP  BY  A.MB_CD,  B.PRNT_SEQ,  A.SUPP_YRMN  )C,  (   \n";
        query +=" SELECT  MAX(X.DISTR_TAMT1  )  DISTR_TAMT1  ,  MAX(X.DISTR_TAMT2  )  DISTR_TAMT2  ,  MAX(X.DISTR_TAMT3  )  DISTR_TAMT3  ,  MAX(X.DISTR_TAMT4  )  DISTR_TAMT4  ,  MAX(X.DISTR_TAMT5  )  DISTR_TAMT5  ,  MAX(X.DISTR_TAMT6  )  DISTR_TAMT6  ,  MAX(X.DISTR_TAMT7  )  DISTR_TAMT7  ,  MAX(X.DISTR_TAMT8  )  DISTR_TAMT8  ,  MAX(X.DISTR_TAMT9  )  DISTR_TAMT9  ,  MAX(X.DISTR_TAMT10)  DISTR_TAMT10,  MAX(X.DISTR_TAMT11)  DISTR_TAMT11,  MAX(X.DISTR_TAMT12)  DISTR_TAMT12,  MAX(X.DISTR_TAMT13)  DISTR_TAMT13,  MAX(X.DISTR_TAMT14)  DISTR_TAMT14,  MAX(X.DISTR_TAMT15)  DISTR_TAMT15,  MAX(X.DISTR_TAMT16)  DISTR_TAMT16,  MAX(X.DISTR_TAMT17)  DISTR_TAMT17,  MAX(X.DISTR_TAMT18)  DISTR_TAMT18,  MAX(X.DISTR_TAMT19)  DISTR_TAMT19,  MAX(X.DISTR_TAMT20)  DISTR_TAMT20,  MAX(X.DISTR_TAMT21)  DISTR_TAMT21,  MAX(X.DISTR_TAMT22)  DISTR_TAMT22,  MAX(X.DISTR_TAMT23)  DISTR_TAMT23,  MAX(X.DISTR_TAMT24)  DISTR_TAMT24,  MAX(X.DISTR_TAMT25)  DISTR_TAMT25,  MAX(X.DISTR_TAMT26)  DISTR_TAMT26,  MAX(X.DISTR_TAMT27)  DISTR_TAMT27  FROM  (   \n";
        query +=" SELECT  NVL(  DECODE(PRNT_SEQ,  '1',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT1,  NVL(  DECODE(PRNT_SEQ,  '2',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT2,  NVL(  DECODE(PRNT_SEQ,  '3',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT3,  NVL(  DECODE(PRNT_SEQ,  '4',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT4,  NVL(  DECODE(PRNT_SEQ,  '5',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT5,  NVL(  DECODE(PRNT_SEQ,  '6',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT6,  NVL(  DECODE(PRNT_SEQ,  '7',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT7,  NVL(  DECODE(PRNT_SEQ,  '8',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT8,  NVL(  DECODE(PRNT_SEQ,  '9',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT9,  NVL(  DECODE(PRNT_SEQ,  '10',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT10,  NVL(  DECODE(PRNT_SEQ,  '11',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT11,  NVL(  DECODE(PRNT_SEQ,  '12',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT12,  NVL(  DECODE(PRNT_SEQ,  '13',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT13,  NVL(  DECODE(PRNT_SEQ,  '14',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT14,  NVL(  DECODE(PRNT_SEQ,  '15',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT15,  NVL(  DECODE(PRNT_SEQ,  '16',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT16,  NVL(  DECODE(PRNT_SEQ,  '17',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT17,  NVL(  DECODE(PRNT_SEQ,  '18',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT18,  NVL(  DECODE(PRNT_SEQ,  '19',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT19,  NVL(  DECODE(PRNT_SEQ,  '20',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT20,  NVL(  DECODE(PRNT_SEQ,  '21',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT21,  NVL(  DECODE(PRNT_SEQ,  '22',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT22,  NVL(  DECODE(PRNT_SEQ,  '23',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT23,  NVL(  DECODE(PRNT_SEQ,  '24',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT24,  NVL(  DECODE(PRNT_SEQ,  '25',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT25,  NVL(  DECODE(PRNT_SEQ,  '26',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT26,  NVL(  DECODE(PRNT_SEQ,  '27',  AVECLASS_CD_NM),  '')  AS  DISTR_TAMT27  FROM  FIDU.TENV_AVECLASSCD  )  X  )  E  GROUP  BY  C.MB_CD,  C.HANMB_NM  ORDER  BY  C.MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        return sobj;
    }
    //##**$$tac10_r09_prt_select
    //##**$$tac10_r09_sum_select
    /* * 프로그램명 : tac10_r09
    * 작성자 : 성낙문
    * 작성일 : 2009/10/20
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac10_r09_sum_select(DOBJ dobj)
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
            dobj  = CALLtac10_r09_sum_select_SEL1(Conn, dobj);           //  출판사 부가세 명세서(합계)
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
    public DOBJ CTLtac10_r09_sum_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r09_sum_select_SEL1(Conn, dobj);           //  출판사 부가세 명세서(합계)
        return dobj;
    }
    // 출판사 부가세 명세서(합계)
    public DOBJ CALLtac10_r09_sum_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r09_sum_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r09_sum_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  RW,  SUM(DISTR_AMT1)  DISTR_SAMT1,  SUM(DISTR_AMT2)  DISTR_SAMT2,  SUM(DISTR_AMT3)  DISTR_SAMT3,  SUM(DISTR_AMT4)  DISTR_SAMT4,  SUM(DISTR_AMT5)  DISTR_SAMT5,  SUM(DISTR_AMT6)  DISTR_SAMT6,  SUM(DISTR_AMT7)  DISTR_SAMT7,  SUM(DISTR_AMT8)  DISTR_SAMT8,  SUM(DISTR_AMT9)  DISTR_SAMT9,  MAX(DISTR_AMT10)  DISTR_SAMT10  FROM  (   \n";
        query +=" SELECT  '1'  AS  RW,  DECODE(B.PRNT_SEQ,  '1',  SUM(A.ORGDISTR_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '4',  SUM(A.ORGDISTR_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '7',  SUM(A.ORGDISTR_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '10',  SUM(A.ORGDISTR_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '13',  SUM(A.ORGDISTR_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '16',  SUM(A.ORGDISTR_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '19',  SUM(A.ORGDISTR_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '22',  SUM(A.ORGDISTR_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '25',  SUM(A.ORGDISTR_AMT))  DISTR_AMT9,  0  AS  DISTR_AMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  (   \n";
        query +=" SELECT  C.MB_GBN1  FROM  FIDU.TMEM_MB  C  WHERE  MB_CD  =  A.MB_CD)  IN  ('P','F')  GROUP  BY  B.PRNT_SEQ  UNION  ALL   \n";
        query +=" SELECT  '2'  AS  RW,  DECODE(B.PRNT_SEQ,  '1',  SUM(A.ATAX_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '4',  SUM(A.ATAX_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '7',  SUM(A.ATAX_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '10',  SUM(A.ATAX_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '13',  SUM(A.ATAX_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '16',  SUM(A.ATAX_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '19',  SUM(A.ATAX_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '22',  SUM(A.ATAX_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '25',  SUM(A.ATAX_AMT))  DISTR_AMT9,  0  AS  DISTR_AMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  (   \n";
        query +=" SELECT  C.MB_GBN1  FROM  FIDU.TMEM_MB  C  WHERE  MB_CD  =  A.MB_CD)  IN  ('P','F')  GROUP  BY  B.PRNT_SEQ  UNION  ALL   \n";
        query +=" SELECT  '3'  AS  RW,  DECODE(B.PRNT_SEQ,  '2',  SUM(A.ORGDISTR_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '5',  SUM(A.ORGDISTR_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '8',  SUM(A.ORGDISTR_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '11',  SUM(A.ORGDISTR_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '14',  SUM(A.ORGDISTR_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '17',  SUM(A.ORGDISTR_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '20',  SUM(A.ORGDISTR_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '23',  SUM(A.ORGDISTR_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '26',  SUM(A.ORGDISTR_AMT))  DISTR_AMT9,  0  AS  DISTR_AMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  (   \n";
        query +=" SELECT  C.MB_GBN1  FROM  FIDU.TMEM_MB  C  WHERE  MB_CD  =  A.MB_CD)  IN  ('P','F')  GROUP  BY  B.PRNT_SEQ  UNION  ALL   \n";
        query +=" SELECT  '4'  AS  RW,  DECODE(B.PRNT_SEQ,  '2',  SUM(A.ATAX_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '5',  SUM(A.ATAX_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '8',  SUM(A.ATAX_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '11',  SUM(A.ATAX_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '14',  SUM(A.ATAX_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '17',  SUM(A.ATAX_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '20',  SUM(A.ATAX_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '23',  SUM(A.ATAX_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '26',  SUM(A.ATAX_AMT))  DISTR_AMT9,  0  AS  DISTR_AMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  (   \n";
        query +=" SELECT  C.MB_GBN1  FROM  FIDU.TMEM_MB  C  WHERE  MB_CD  =  A.MB_CD)  IN  ('P','F')  GROUP  BY  B.PRNT_SEQ  UNION  ALL   \n";
        query +=" SELECT  '5'  AS  RW,  DECODE(B.PRNT_SEQ,  '3',  SUM(A.ORGDISTR_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '6',  SUM(A.ORGDISTR_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '9',  SUM(A.ORGDISTR_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '12',  SUM(A.ORGDISTR_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '15',  SUM(A.ORGDISTR_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '18',  SUM(A.ORGDISTR_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '21',  SUM(A.ORGDISTR_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '24',  SUM(A.ORGDISTR_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '27',  SUM(A.ORGDISTR_AMT))  DISTR_AMT9,  (   \n";
        query +=" SELECT  SUM(B.ORGDISTR_AMT)  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  B  ,FIDU.TENV_AVECLASSCD  C  WHERE  B.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  B.MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  (   \n";
        query +=" SELECT  C.MB_GBN1  FROM  FIDU.TMEM_MB  C  WHERE  MB_CD  =  B.MB_CD)  IN  ('P','F')   \n";
        query +=" AND  SUBSTR(B.MDM_CD,1,2)  =  C.AVECLASS_CD  )  DISTR_AMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  (   \n";
        query +=" SELECT  C.MB_GBN1  FROM  FIDU.TMEM_MB  C  WHERE  MB_CD  =  A.MB_CD)  IN  ('P','F')  GROUP  BY  B.PRNT_SEQ  UNION  ALL   \n";
        query +=" SELECT  '6'  AS  RW,  DECODE(B.PRNT_SEQ,  '3',  SUM(A.ATAX_AMT))  DISTR_AMT1,  DECODE(B.PRNT_SEQ,  '6',  SUM(A.ATAX_AMT))  DISTR_AMT2,  DECODE(B.PRNT_SEQ,  '9',  SUM(A.ATAX_AMT))  DISTR_AMT3,  DECODE(B.PRNT_SEQ,  '12',  SUM(A.ATAX_AMT))  DISTR_AMT4,  DECODE(B.PRNT_SEQ,  '15',  SUM(A.ATAX_AMT))  DISTR_AMT5,  DECODE(B.PRNT_SEQ,  '18',  SUM(A.ATAX_AMT))  DISTR_AMT6,  DECODE(B.PRNT_SEQ,  '21',  SUM(A.ATAX_AMT))  DISTR_AMT7,  DECODE(B.PRNT_SEQ,  '24',  SUM(A.ATAX_AMT))  DISTR_AMT8,  DECODE(B.PRNT_SEQ,  '27',  SUM(A.ATAX_AMT))  DISTR_AMT9,  (   \n";
        query +=" SELECT  SUM(B.ATAX_AMT)  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  B,  FIDU.TENV_AVECLASSCD  C  WHERE  B.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  B.MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  (   \n";
        query +=" SELECT  C.MB_GBN1  FROM  FIDU.TMEM_MB  C  WHERE  MB_CD  =  B.MB_CD)  IN  ('P','F')   \n";
        query +=" AND  SUBSTR(B.MDM_CD,1,2)  =  C.AVECLASS_CD  )  DISTR_AMT10  FROM  FIDU.TTAC_MDMCLASSSUPPAMT  A,  FIDU.TENV_AVECLASSCD  B  WHERE  SUBSTR(A.MDM_CD,1,2)  =  B.AVECLASS_CD   \n";
        query +=" AND  A.SUPP_YRMN  BETWEEN  SUBSTR(:FROMDATE,1,6)   \n";
        query +=" AND  SUBSTR(:TODATE,1,6)   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  (   \n";
        query +=" SELECT  C.MB_GBN1  FROM  FIDU.TMEM_MB  C  WHERE  MB_CD  =  A.MB_CD)  IN  ('P','F')  GROUP  BY  B.PRNT_SEQ  )X  GROUP  BY  RW  ORDER  BY  RW ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        return sobj;
    }
    //##**$$tac10_r09_sum_select
    //##**$$tac10_r09_hd_select
    /* * 프로그램명 : tac10_r09
    * 작성자 : 성낙문
    * 작성일 : 2009/10/20
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac10_r09_hd_select(DOBJ dobj)
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
            dobj  = CALLtac10_r09_hd_select_SEL1(Conn, dobj);           //  출판사 부가세 명세서(헤더)
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
    public DOBJ CTLtac10_r09_hd_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r09_hd_select_SEL1(Conn, dobj);           //  출판사 부가세 명세서(헤더)
        return dobj;
    }
    // 출판사 부가세 명세서(헤더)
    public DOBJ CALLtac10_r09_hd_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r09_hd_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r09_hd_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  RW,  MAX(DISTR_TAMT0)  DISTR_TAMT0,  MAX(DISTR_TAMT1)  DISTR_TAMT1,  MAX(DISTR_TAMT2)  DISTR_TAMT2,  MAX(DISTR_TAMT3)  DISTR_TAMT3,  MAX(DISTR_TAMT4)  DISTR_TAMT4,  MAX(DISTR_TAMT5)  DISTR_TAMT5,  MAX(DISTR_TAMT6)  DISTR_TAMT6,  MAX(DISTR_TAMT7)  DISTR_TAMT7,  MAX(DISTR_TAMT8)  DISTR_TAMT8,  MAX(DISTR_TAMT9)  DISTR_TAMT9,  MAX(DISTR_TAMT10)  DISTR_TAMT10  FROM  (   \n";
        query +=" SELECT  '1'  AS  RW,  '출판사코드'  AS  DISTR_TAMT0,  DECODE(PRNT_SEQ,  '1',  AVECLASS_CD_NM)  DISTR_TAMT1,  DECODE(PRNT_SEQ,  '4',  AVECLASS_CD_NM)  DISTR_TAMT2,  DECODE(PRNT_SEQ,  '7',  AVECLASS_CD_NM)  DISTR_TAMT3,  DECODE(PRNT_SEQ,  '10',  AVECLASS_CD_NM)  DISTR_TAMT4,  DECODE(PRNT_SEQ,  '13',  AVECLASS_CD_NM)  DISTR_TAMT5,  DECODE(PRNT_SEQ,  '16',  AVECLASS_CD_NM)  DISTR_TAMT6,  DECODE(PRNT_SEQ,  '19',  AVECLASS_CD_NM)  DISTR_TAMT7,  DECODE(PRNT_SEQ,  '22',  AVECLASS_CD_NM)  DISTR_TAMT8,  DECODE(PRNT_SEQ,  '25',  AVECLASS_CD_NM)  DISTR_TAMT9,  ''  AS  DISTR_TAMT10  FROM  FIDU.TENV_AVECLASSCD  B  UNION  ALL   \n";
        query +=" SELECT  '2'  AS  RW,  '출판사명'  AS  DISTR_TAMT0,  ''  AS  DISTR_TAMT1,  ''  AS  DISTR_TAMT2,  ''  AS  DISTR_TAMT3,  ''  AS  DISTR_TAMT4,  ''  AS  DISTR_TAMT5,  ''  AS  DISTR_TAMT6,  ''  AS  DISTR_TAMT7,  ''  AS  DISTR_TAMT8,  ''  AS  DISTR_TAMT9,  ''  AS  DISTR_TAMT10  FROM  FIDU.TENV_AVECLASSCD  B  UNION  ALL   \n";
        query +=" SELECT  '3'  AS  RW,  ''  AS  DISTR_TAMT0,  DECODE(PRNT_SEQ,  '2',  AVECLASS_CD_NM)  DISTR_TAMT1,  DECODE(PRNT_SEQ,  '5',  AVECLASS_CD_NM)  DISTR_TAMT2,  DECODE(PRNT_SEQ,  '8',  AVECLASS_CD_NM)  DISTR_TAMT3,  DECODE(PRNT_SEQ,  '11',  AVECLASS_CD_NM)  DISTR_TAMT4,  DECODE(PRNT_SEQ,  '14',  AVECLASS_CD_NM)  DISTR_TAMT5,  DECODE(PRNT_SEQ,  '17',  AVECLASS_CD_NM)  DISTR_TAMT6,  DECODE(PRNT_SEQ,  '20',  AVECLASS_CD_NM)  DISTR_TAMT7,  DECODE(PRNT_SEQ,  '23',  AVECLASS_CD_NM)  DISTR_TAMT8,  DECODE(PRNT_SEQ,  '26',  AVECLASS_CD_NM)  DISTR_TAMT9,  ''  AS  DISTR_TAMT10  FROM  FIDU.TENV_AVECLASSCD  UNION  ALL   \n";
        query +=" SELECT  '4'  AS  RW,  ''  AS  DISTR_TAMT0,  ''  AS  DISTR_TAMT1,  ''  AS  DISTR_TAMT2,  ''  AS  DISTR_TAMT3,  ''  AS  DISTR_TAMT4,  ''  AS  DISTR_TAMT5,  ''  AS  DISTR_TAMT6,  ''  AS  DISTR_TAMT7,  ''  AS  DISTR_TAMT8,  ''  AS  DISTR_TAMT9,  ''  AS  DISTR_TAMT10  FROM  FIDU.TENV_AVECLASSCD  B  UNION  ALL   \n";
        query +=" SELECT  '5'  AS  RW,  ''  AS  DISTR_TAMT0,  DECODE(PRNT_SEQ,  '3',  AVECLASS_CD_NM)  DISTR_TAMT1,  DECODE(PRNT_SEQ,  '6',  AVECLASS_CD_NM)  DISTR_TAMT2,  DECODE(PRNT_SEQ,  '9',  AVECLASS_CD_NM)  DISTR_TAMT3,  DECODE(PRNT_SEQ,  '12',  AVECLASS_CD_NM)  DISTR_TAMT4,  DECODE(PRNT_SEQ,  '15',  AVECLASS_CD_NM)  DISTR_TAMT5,  DECODE(PRNT_SEQ,  '18',  AVECLASS_CD_NM)  DISTR_TAMT6,  DECODE(PRNT_SEQ,  '21',  AVECLASS_CD_NM)  DISTR_TAMT7,  DECODE(PRNT_SEQ,  '24',  AVECLASS_CD_NM)  DISTR_TAMT8,  DECODE(PRNT_SEQ,  '27',  AVECLASS_CD_NM)  DISTR_TAMT9,  '합  계'  AS  DISTR_TAMT10  FROM  FIDU.TENV_AVECLASSCD  )X  GROUP  BY  RW  ORDER  BY  RW ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$tac10_r09_hd_select
    //##**$$end
}
