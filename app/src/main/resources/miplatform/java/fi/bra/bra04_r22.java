
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_r22
{
    public bra04_r22()
    {
    }
    //##**$$select_tmp_account
    /* * 프로그램명 : 가수금 월 매칭 리스트 조회
    * 작성자 : 박태현
    * 작성일 : 2010/02/24
    * 설명    :
    * 수정일 : 2010/03/04
    * 수정자 :
    * 수정내용 : 입금일자별 정렬
    */
    public DOBJ CTLselect_tmp_account(DOBJ dobj)
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
            dobj  = CALLselect_tmp_account_SEL1(Conn, dobj);           //  가수금 내역 조회
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
    public DOBJ CTLselect_tmp_account( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLselect_tmp_account_SEL1(Conn, dobj);           //  가수금 내역 조회
        return dobj;
    }
    // 가수금 내역 조회
    public DOBJ CALLselect_tmp_account_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_tmp_account_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_tmp_account_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //계좌 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.REPT_DAY  ,  XA.REPT_NUM  ,  XA.REPT_GBN  ,  XC.CODE_NM  ,  XA.BRAN_CD  ,  XA.REPT_AMT  ,  XA.OVER_AMT  ,  DECODE(XA.REPT_GBN,  '03',   \n";
        query +=" (SELECT  REPTPRES  FROM  GIBU.TBRA_REPT_TRANS  WHERE  REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  XA.REPT_NUM)  ,'')  AS  REPTPRES  ,  DECODE(XA.REPT_GBN,  '03',   \n";
        query +=" (SELECT  RECEPT_BANK  FROM  GIBU.TBRA_REPT_TRANS  WHERE  REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  XA.REPT_NUM)  ,'')  AS  TRANS_REMAK  ,  XB.BANK_NM  ,  XA.BANK_CD  ,  XA.ACCN_NUM  ,  XA.REMAK  ,  XA.RECV_DAY  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  BRAN_CD  ,  REPT_AMT  -  COMIS  REPT_AMT  ,  REPT_AMT  -  COMIS  OVER_AMT  ,  REMAK  ,  BANK_CD  ,  ACCN_NUM  ,  RECV_DAY  FROM  GIBU.TBRA_REPT  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  UPSO_CD  IS  NULL  UNION  ALL   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  BRAN_CD  ,  REPT_AMT  -  COMIS  REPT_AMT  ,  REPT_AMT  -  COMIS  OVER_AMT  ,  '지부분배  가수금  (미분배)'  REMAK  ,  BANK_CD  ,  ACCN_NUM  ,  RECV_DAY  FROM  GIBU.TBRA_REPT  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  DISTR_GBN  =  '41'   \n";
        query +=" AND  REPT_DAY  ||  REPT_NUM  ||  REPT_GBN  NOT  IN  (   \n";
        query +=" SELECT  REPT_DAY  ||  REPT_NUM  ||  REPT_GBN  FROM  GIBU.TBRA_REPT_DISTR  )  UNION  ALL   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  C.BRAN_CD  ,  C.REPT_AMT  -  NVL(C.COMIS,  0)  REPT_AMT  ,  C.REPT_AMT  -  NVL(C.COMIS,  0)-  NVL(B.REPT_AMT,0)  OVER_AMT  ,  '지부분배  가수금'  REMAK  ,  C.BANK_CD  ,  C.ACCN_NUM  ,  C.RECV_DAY  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  SUM(NVL(DISTR_AMT,  0))  REPT_AMT  FROM  GIBU.TBRA_REPT_DISTR  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN  )  A,  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  SUM(NVL(DISTR_AMT,  0))  REPT_AMT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN  )  B  ,  GIBU.TBRA_REPT  C  WHERE  C.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  B.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  =  A.REPT_GBN   \n";
        query +=" AND  C.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  C.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  C.REPT_GBN  =  A.REPT_GBN   \n";
        query +=" AND  A.REPT_AMT  -  NVL(B.REPT_AMT,  0)  >  0  UNION  ALL   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.BRAN_CD  ,  (A.REPT_AMT  -  A.COMIS)  REPT_AMT  ,  (A.REPT_AMT  -  A.COMIS)  -  NVL(B.REPT_AMT,0)  OVER_AMT  ,  '업소분배  가수금'  REMAK  ,  A.BANK_CD  ,  A.ACCN_NUM  ,  A.RECV_DAY  FROM  GIBU.TBRA_REPT  A  ,  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  SUM(NVL(DISTR_AMT,  0))  REPT_AMT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  BRAN_CD  =  :BRAN_CD  GROUP  BY  REPT_DAY,  REPT_NUM  )  B  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM(+)   \n";
        query +=" AND  A.DISTR_GBN  =  '42'   \n";
        query +=" AND  (A.REPT_AMT  -  A.COMIS)  -  NVL(B.REPT_AMT,  0)  >  0  )  XA  ,  ACCT.TCAC_BANK  XB  ,  FIDU.TENV_CODE  XC  WHERE  XB.BANK_CD  =  XA.BANK_CD   \n";
        query +=" AND  XC.HIGH_CD  =  '00141'   \n";
        query +=" AND  XC.CODE_CD  =  XA.REPT_GBN   \n";
        query +=" AND  XA.ACCN_NUM  =  DECODE(:ACCN_NUM,  NULL,  XA.ACCN_NUM,  :ACCN_NUM)   \n";
        query +=" AND  XA.REPT_AMT  <>  0  ORDER  BY  XA.REPT_DAY,  XA.REPT_NUM ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        return sobj;
    }
    //##**$$select_tmp_account
    //##**$$select_tmp_account_mapping
    /* * 프로그램명 : 가수금 월 매칭 리스트 조회
    * 작성자 : 박태현
    * 작성일 : 2010/02/24
    * 설명    :
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLselect_tmp_account_mapping(DOBJ dobj)
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
            dobj  = CALLselect_tmp_account_mapping_SEL1(Conn, dobj);           //  가수금 월 매칭 리스트
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
    public DOBJ CTLselect_tmp_account_mapping( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLselect_tmp_account_mapping_SEL1(Conn, dobj);           //  가수금 월 매칭 리스트
        return dobj;
    }
    // 가수금 월 매칭 리스트
    public DOBJ CALLselect_tmp_account_mapping_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_tmp_account_mapping_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_tmp_account_mapping_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_YRMN = dobj.getRetObject("S").getRecord().get("REPT_YRMN");   //입금 년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.REPT_DAY  ,  XA.REPT_NUM  ,  XA.REPT_GBN  ,  XE.CODE_NM  ,  XA.MAPPING_DAY  ,  XA.BRAN_CD  ,  XA.UPSO_CD  ,  XB.UPSO_NM  ,  MIN(NOTE_YRMN)  START_YRMN  ,  MAX(NOTE_YRMN)  END_YRMN  ,  XA.REPT_AMT  ,  DECODE(XA.REPT_GBN,  '03',   \n";
        query +=" (SELECT  REPTPRES  FROM  GIBU.TBRA_REPT_TRANS  WHERE  REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  XA.REPT_NUM)  ,'')  AS  REPTPRES  ,  XD.BANK_NM  ,  XA.BANK_CD  ,  XA.ACCN_NUM  ,  XA.REMAK  ,  XA.RECV_DAY  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  MAPPING_DAY  ,  BRAN_CD  ,  UPSO_CD  ,  REPT_AMT  -  COMIS  REPT_AMT  ,  REMAK  ,  BANK_CD  ,  ACCN_NUM  ,  RECV_DAY  FROM  GIBU.TBRA_REPT  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  SUBSTR(REPT_DAY,  1,  6)  <  SUBSTR(MAPPING_DAY,  1,  6)   \n";
        query +=" AND  MAPPING_DAY  BETWEEN  :REPT_YRMN  ||  '01'   \n";
        query +=" AND  :REPT_YRMN  ||  '31'   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  REPT_GBN  =  '03'  UNION  ALL   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.MAPPING_DAY  ,  A.BRAN_CD  ,  A.UPSO_CD  ,  A.REPT_AMT  ,  A.REMAK  ,  B.BANK_CD  ,  B.ACCN_NUM  ,  B.RECV_DAY  FROM  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  BRAN_CD  ,  UPSO_CD  ,  DISTR_AMT  REPT_AMT  ,  MAPPING_DAY  ,  REMAK  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  MAPPING_DAY  BETWEEN  :REPT_YRMN  ||  '01'   \n";
        query +=" AND  :REPT_YRMN  ||  '31'  )  A  ,  GIBU.TBRA_REPT  B  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  SUBSTR(A.REPT_DAY,  1,  6)  <  SUBSTR(A.MAPPING_DAY,  1,  6)   \n";
        query +=" AND  B.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  B.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  B.REPT_GBN  =  A.REPT_GBN   \n";
        query +=" AND  B.REPT_GBN  =  '03'  )  XA  ,  GIBU.TBRA_UPSO  XB  ,  GIBU.TBRA_NOTE  XC  ,  ACCT.TCAC_BANK  XD  ,  FIDU.TENV_CODE  XE  WHERE  XB.UPSO_CD  =  XA.UPSO_CD   \n";
        query +=" AND  XC.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XC.REPT_DAY(+)  =  XA.REPT_DAY   \n";
        query +=" AND  XC.REPT_NUM(+)  =  XA.REPT_NUM   \n";
        query +=" AND  XC.REPT_GBN(+)  =  XA.REPT_GBN   \n";
        query +=" AND  XD.BANK_CD  =  XA.BANK_CD   \n";
        query +=" AND  XE.HIGH_CD  =  '00141'   \n";
        query +=" AND  XE.CODE_CD  =  XA.REPT_GBN  GROUP  BY  XA.REPT_DAY,  XA.REPT_NUM,  XA.REPT_GBN,  XE.CODE_NM,  XA.MAPPING_DAY,  XA.BRAN_CD,  XA.UPSO_CD,  XB.UPSO_NM,  XA.REPT_AMT,  XD.BANK_NM,  XA.BANK_CD,  XA.ACCN_NUM,  XA.REMAK,  XA.RECV_DAY  ORDER  BY  REPT_DAY  DESC,  REPT_NUM  DESC ";
        sobj.setSql(query);
        sobj.setString("REPT_YRMN", REPT_YRMN);               //입금 년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$select_tmp_account_mapping
    //##**$$end
}
