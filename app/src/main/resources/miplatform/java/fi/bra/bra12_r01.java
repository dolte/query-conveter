
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra12_r01
{
    public bra12_r01()
    {
    }
    //##**$$accn_num_select
    /* * 프로그램명 : bra12_r01
    * 작성자 : 박태현
    * 작성일 : 2009/11/03
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLaccn_num_select(DOBJ dobj)
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
            dobj  = CALLaccn_num_select_SEL1(Conn, dobj);           //  지부별 은행계좌 조회
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
    public DOBJ CTLaccn_num_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLaccn_num_select_SEL1(Conn, dobj);           //  지부별 은행계좌 조회
        return dobj;
    }
    // 지부별 은행계좌 조회
    // 지부별 은행계좌 조회
    public DOBJ CALLaccn_num_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccn_num_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccn_num_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ALL_TYPE = dobj.getRetObject("S").getRecord().get("ALL_TYPE");   //전체포함여부
        String   USE_TYPE = dobj.getRetObject("S").getRecord().get("USE_TYPE");   //사용 유형
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCN_SEQ  ,  ACCN_NUM  ,  BANK_CD  ,  BANK_NM  ,  BRAN_CD  ,  BRAN_NM  ,  USAG  FROM  (   \n";
        query +=" SELECT  ''  AS  ACCN_SEQ  ,  ''  AS  ACCN_NUM  ,  ''  AS  BANK_CD  ,  '전체'  AS  BANK_NM  ,  :BRAN_CD  AS  BRAN_CD  ,  DEPT_NM  AS  BRAN_NM  ,  ''  AS  USAG  ,  0  AS  ALL_TYPE  FROM  INSA.TCPM_DEPT  WHERE  GIBU  =  :BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  A.ACCN_SEQ  ,  A.ACCN_NUM  ,  A.BANK_CD  ,  B.BANK_NM  ||  '  '  ||  ACCN_NUM  AS  BANK_NM  ,  C.GIBU  AS  BRAN_CD  ,  C.DEPT_NM  AS  BRAN_NM  ,  A.USAG  ,  1  FROM  ACCT.TCAC_ACCOUNT  A  ,  ACCT.TCAC_BANK  B  ,  INSA.TCPM_DEPT  C  WHERE  NVL(C.GIBU,  '-')  =  DECODE(:USE_TYPE,  '002',  '-',  '003',  '-',  '004',  '-','007','-',  DECODE(:BRAN_CD,  'AL',  C.GIBU,  :BRAN_CD))   \n";
        query +=" AND  A.DEPT_CD  =  C.DEPT_CD   \n";
        query +=" AND  B.BANK_CD  =  A.BANK_CD   \n";
        query +=" AND  A.USE_TYPE  =  NVL(:USE_TYPE,  A.USE_TYPE)   \n";
        query +=" AND  A.ACCTN_GBN  =  '001'   \n";
        query +=" AND  A.USE_YN  =  'Y'  UNION  ALL   \n";
        query +=" SELECT  A.ACCN_SEQ  ,  A.ACCN_NUM  ,  A.BANK_CD  ,  B.BANK_NM  ||  '  '  ||  ACCN_NUM  AS  BANK_NM  ,  C.GIBU  AS  BRAN_CD  ,  C.DEPT_NM  AS  BRAN_NM  ,  A.USAG  ,  1  FROM  ACCT.TCAC_ACCOUNT  A  ,  ACCT.TCAC_BANK  B  ,  INSA.TCPM_DEPT  C  WHERE  C.GIBU  =  'AL'   \n";
        query +=" AND  A.DEPT_CD  =  C.DEPT_CD   \n";
        query +=" AND  B.BANK_CD  =  A.BANK_CD   \n";
        query +=" AND  A.ACCTN_GBN  =  '001'   \n";
        query +=" AND  :USE_TYPE  =  '001'   \n";
        query +=" AND  A.USE_YN  =  'Y'  )  XA  WHERE  ALL_TYPE  =  DECODE(:ALL_TYPE,  NULL,  1,  XA.ALL_TYPE)  ORDER  BY  BANK_NM  ASC ";
        sobj.setSql(query);
        sobj.setString("ALL_TYPE", ALL_TYPE);               //전체포함여부
        sobj.setString("USE_TYPE", USE_TYPE);               //사용 유형
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$accn_num_select
    //##**$$c_norebang_amt
    /* * 프로그램명 : bra12_r01
    * 작성자 : 서정재
    * 작성일 : 2009/11/25
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLc_norebang_amt(DOBJ dobj)
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
            dobj  = CALLc_norebang_amt_SEL1(Conn, dobj);           //  노래방사용료
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
    public DOBJ CTLc_norebang_amt( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLc_norebang_amt_SEL1(Conn, dobj);           //  노래방사용료
        return dobj;
    }
    // 노래방사용료
    public DOBJ CALLc_norebang_amt_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLc_norebang_amt_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLc_norebang_amt_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TRIM(BSTYP_CD)||GRAD_GBN  GRAD  ,	GRADNM  ,	STNDAMT  ,  STNDAREA_START  ,  STNDAREA_END  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  TRIM(BSTYP_CD)  =  'o' ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$c_norebang_amt
    //##**$$m_grade_select
    /* * 프로그램명 : bra12_r01
    * 작성자 : 서정재
    * 작성일 : 2009/08/24
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLm_grade_select(DOBJ dobj)
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
            dobj  = CALLm_grade_select_SEL1(Conn, dobj);           //  대표 업종 조회
            dobj  = CALLm_grade_select_SEL2(Conn, dobj);           //  대표업종조회
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
    public DOBJ CTLm_grade_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLm_grade_select_SEL1(Conn, dobj);           //  대표 업종 조회
        dobj  = CALLm_grade_select_SEL2(Conn, dobj);           //  대표업종조회
        return dobj;
    }
    // 대표 업종 조회
    // 대표 업종을 조회한다
    public DOBJ CALLm_grade_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLm_grade_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLm_grade_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  GRAD_GBN  ,  '--전체--'  GRAD_NM  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  TRIM(GRAD_GBN)  ,  TRIM(GRAD_GBN)  ||  '  :  '  ||  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z' ";
        sobj.setSql(query);
        return sobj;
    }
    // 대표업종조회
    public DOBJ CALLm_grade_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLm_grade_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLm_grade_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  GRAD_GBN  AS  GRAD  ,  GRADNM  ,  STNDAMT  ,  BSTYP_CD  ,  GRAD_GBN  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'  ORDER  BY  GRADNM  ASC ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$m_grade_select
    //##**$$loginfo_select
    /* * 프로그램명 : bra12_r01
    * 작성자 : 999999
    * 작성일 : 2009/10/22
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLloginfo_select(DOBJ dobj)
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
            dobj  = CALLloginfo_select_SEL1(Conn, dobj);           //  NLL
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
    public DOBJ CTLloginfo_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLloginfo_select_SEL1(Conn, dobj);           //  NLL
        return dobj;
    }
    // NLL
    public DOBJ CALLloginfo_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLloginfo_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLloginfo_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  'NLL'  AS  NLL  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$loginfo_select
    //##**$$rept_list
    /* * 프로그램명 : bra12_r01
    * 작성자 : 박태현
    * 작성일 : 2009/11/30
    * 설명 :
    * 수정일1: 2010/02/18
    * 수정자 :
    * 수정내용 :
    1) 기존의 QUERY는 입금자가 NULL값일 경우에는 아예 비교조건에서 빠지게 됨
    -> NULL 인데이타도 찾을수 있게 수정
    2) REPT_DAY, REPT_NUM으로 정렬 추가
    */
    public DOBJ CTLrept_list(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("DISTR_GBN").equals(""))
            {
                dobj  = CALLrept_list_SEL1(Conn, dobj);           //  입금내역 리스트 조회
                dobj  = CALLrept_list_MRG6( dobj);        //  결과 취합
            }
            else
            {
                dobj  = CALLrept_list_SEL3(Conn, dobj);           //  입금내역 리스트 조회
                dobj  = CALLrept_list_MRG6( dobj);        //  결과 취합
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
    public DOBJ CTLrept_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("DISTR_GBN").equals(""))
        {
            dobj  = CALLrept_list_SEL1(Conn, dobj);           //  입금내역 리스트 조회
            dobj  = CALLrept_list_MRG6( dobj);        //  결과 취합
        }
        else
        {
            dobj  = CALLrept_list_SEL3(Conn, dobj);           //  입금내역 리스트 조회
            dobj  = CALLrept_list_MRG6( dobj);        //  결과 취합
        }
        return dobj;
    }
    // 입금내역 리스트 조회
    public DOBJ CALLrept_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_list_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   REPTPRES = dobj.getRetObject("S").getRecord().get("REPTPRES");   //입금자
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //계좌 번호
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  '03'  REPT_GBN  ,  NULL  DISTR_SEQ  ,   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00141'   \n";
        query +=" AND  CODE_ETC  =  'A'   \n";
        query +=" AND  CODE_CD  =  '03')  AS  REPT_GBN_NM  ,  A.REPT_AMT  ,  A.RECV_DAY  ,  A.BRAN_CD  ,  A.BANK_CD  ,  C.BANK_NM  ,  A.ACCN_NUM  ,  A.UPSO_CD  ,  A.DISTR_GBN  ,  DECODE  (A.DISTR_GBN,  NULL,  B.UPSO_NM,  '41',  '지부분배',  '42',  '업소분배')  UPSO_NM  ,  A.REPTPRES  ,  A.REMAK  FROM  GIBU.TBRA_REPT_TRANS  A  ,  GIBU.TBRA_UPSO  B  ,  ACCT.TCAC_BANK  C  WHERE  A.RECV_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  B.UPSO_CD  (+)  =  A.UPSO_CD   \n";
        query +=" AND  C.BANK_CD  (+)  =  A.BANK_CD   \n";
        query +=" AND  NVL(A.ACCN_NUM,  '-')  =  DECODE(:ACCN_NUM,  NULL,  NVL(A.ACCN_NUM,  '-'),  :ACCN_NUM)   \n";
        query +=" AND  NVL(A.REPTPRES,  '  ')  LIKE  '%'  ||  :REPTPRES  ||  '%'   \n";
        query +=" AND  A.DISTR_GBN  IS  NULL  ORDER  BY  A.REPT_DAY,  A.REPT_NUM ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("REPTPRES", REPTPRES);               //입금자
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 결과 취합
    public DOBJ CALLrept_list_MRG6(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG6");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL3","");
        rvobj.setName("MRG6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 입금내역 리스트 조회
    public DOBJ CALLrept_list_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_list_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_list_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   REPTPRES = dobj.getRetObject("S").getRecord().get("REPTPRES");   //입금자
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //계좌 번호
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.REPT_DAY  ,  XA.REPT_NUM  ,  '03'  REPT_GBN  ,  XA.DISTR_SEQ  ,   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00141'   \n";
        query +=" AND  CODE_ETC  =  'A'   \n";
        query +=" AND  CODE_CD  =  '03')  AS  REPT_GBN_NM  ,  XA.REPT_AMT  ,  XA.RECV_DAY  ,  XA.BRAN_CD  ,  XA.BANK_CD  ,  XB.BANK_NM  ,  XA.ACCN_NUM  ,  XA.UPSO_CD  ,  XA.DISTR_GBN  ,  XA.UPSO_NM  ,  XA.REPTPRES  ,  XA.REMAK  FROM  (   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  NULL  DISTR_SEQ  ,  A.BRAN_CD  ,  A.DISTR_AMT  -  NVL(B.REPT_AMT,  0)  REPT_AMT  ,  '지부분배'  UPSO_CD  ,  NULL  UPSO_NM  ,  A.REMAK  ,  '  '  ||  C.REPTPRES  REPTPRES  ,  C.BANK_CD  ,  C.ACCN_NUM  ,  C.RECV_DAY  ,  C.DISTR_GBN  FROM  GIBU.TBRA_REPT_DISTR  A  ,  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  REPT_GBN  ,  BRAN_CD  ,  SUM(NVL(DISTR_AMT,  0))  REPT_AMT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  BRAN_CD  =  :BRAN_CD  GROUP  BY  REPT_DAY,  REPT_NUM,  REPT_GBN,  BRAN_CD  )  B  ,  GIBU.TBRA_REPT_TRANS  C  WHERE  A.REPT_DAY  =  B.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM(+)   \n";
        query +=" AND  A.REPT_GBN  =  B.REPT_GBN(+)   \n";
        query +=" AND  A.BRAN_CD  =  B.BRAN_CD(+)   \n";
        query +=" AND  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  C.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  C.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  C.DISTR_GBN  =  '41'   \n";
        query +=" AND  C.RECV_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY  UNION  ALL   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.REPT_GBN  ,  A.DISTR_SEQ  ,  B.BRAN_CD  ,  A.DISTR_AMT  ,  A.UPSO_CD  ,  B.UPSO_NM  ,  A.REMAK  ,  '  '  ||  C.REPTPRES  REPTPRES  ,  C.BANK_CD  ,  C.ACCN_NUM  ,  C.RECV_DAY  ,  C.DISTR_GBN  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_UPSO  B  ,  GIBU.TBRA_REPT_TRANS  C  WHERE  C.RECV_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  C.REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  C.DISTR_GBN  =  '41'  )  XA  ,  ACCT.TCAC_BANK  XB  WHERE  XB.BANK_CD  (+)  =  XA.BANK_CD   \n";
        query +=" AND  XA.REPTPRES  LIKE  '%'  ||  :REPTPRES  ||  '%'   \n";
        query +=" AND  XA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NVL(XA.ACCN_NUM,  '-')  =  DECODE(:ACCN_NUM,  NULL,  NVL(XA.ACCN_NUM,  '-'),  :ACCN_NUM)   \n";
        query +=" AND  XA.REPT_AMT  >  0  UNION  ALL   \n";
        query +=" SELECT  XB.REPT_DAY  ,  XB.REPT_NUM  ,  '03'  REPT_GBN  ,  XA.DISTR_SEQ  ,   \n";
        query +=" (SELECT  CODE_NM  FROM  FIDU.TENV_CODE  WHERE  HIGH_CD  =  '00141'   \n";
        query +=" AND  CODE_ETC  =  'A'   \n";
        query +=" AND  CODE_CD  =  '03')  AS  REPT_GBN_NM  ,  XA.REPT_AMT  ,  XB.RECV_DAY  ,  XB.BRAN_CD  ,  XB.BANK_CD  ,  XD.BANK_NM  ,  XB.ACCN_NUM  ,  XA.UPSO_CD  ,  XB.DISTR_GBN  ,  XC.UPSO_NM  ,  XB.REPTPRES  ,  XA.REMAK  FROM  (   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  NULL  DISTR_SEQ  ,  A.REPT_AMT  -  NVL(B.REPT_AMT,  0)  REPT_AMT  ,  '업소분배'  UPSO_CD  ,  NULL  REMAK  FROM  GIBU.TBRA_REPT_TRANS  A  ,  (   \n";
        query +=" SELECT  REPT_DAY  ,  REPT_NUM  ,  SUM(NVL(DISTR_AMT,  0))  REPT_AMT  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  BRAN_CD  =  :BRAN_CD  GROUP  BY  REPT_DAY,  REPT_NUM  )  B  WHERE  A.RECV_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY(+)   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM(+)   \n";
        query +=" AND  A.DISTR_GBN  =  '42'  UNION  ALL   \n";
        query +=" SELECT  A.REPT_DAY  ,  A.REPT_NUM  ,  A.DISTR_SEQ  ,  NVL(A.DISTR_AMT,  0)  REPT_AMT  ,  A.UPSO_CD  ,  A.REMAK  FROM  GIBU.TBRA_REPT_UPSO_DISTR  A  ,  GIBU.TBRA_REPT_TRANS  B  WHERE  B.RECV_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  B.DISTR_GBN  =  '42'   \n";
        query +=" AND  A.REPT_DAY  =  B.REPT_DAY   \n";
        query +=" AND  A.REPT_NUM  =  B.REPT_NUM  )  XA  ,  GIBU.TBRA_REPT_TRANS  XB  ,  GIBU.TBRA_UPSO  XC  ,  ACCT.TCAC_BANK  XD  WHERE  XB.REPT_DAY  =  XA.REPT_DAY   \n";
        query +=" AND  XB.REPT_NUM  =  XA.REPT_NUM   \n";
        query +=" AND  XC.UPSO_CD  (+)  =  XA.UPSO_CD   \n";
        query +=" AND  XD.BANK_CD  (+)  =  XB.BANK_CD   \n";
        query +=" AND  XB.REPTPRES  LIKE  '%'  ||  :REPTPRES  ||  '%'   \n";
        query +=" AND  XB.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  NVL(XB.ACCN_NUM,  '-')  =  DECODE(:ACCN_NUM,  NULL,  NVL(XB.ACCN_NUM,  '-'),  :ACCN_NUM)   \n";
        query +=" AND  XA.REPT_AMT  >  0 ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("REPTPRES", REPTPRES);               //입금자
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$rept_list
    //##**$$upso_simple_select
    /* * 프로그램명 : bra12_r01
    * 작성자 : 서정재
    * 작성일 : 2009/11/25
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLupso_simple_select(DOBJ dobj)
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
            dobj  = CALLupso_simple_select_SEL8(Conn, dobj);           //  정보확인
            if( dobj.getRetObject("SEL8").getRecord().getDouble("CNT") == 1)
            {
                dobj  = CALLupso_simple_select_SEL1(Conn, dobj);           //  업소간단조회
                dobj  = CALLupso_simple_select_MRG1( dobj);        //  업소간단조회
            }
            else
            {
                dobj  = CALLupso_simple_select_SEL2(Conn, dobj);           //  조회결과가없을경우
                dobj  = CALLupso_simple_select_MRG1( dobj);        //  업소간단조회
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
    public DOBJ CTLupso_simple_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_simple_select_SEL8(Conn, dobj);           //  정보확인
        if( dobj.getRetObject("SEL8").getRecord().getDouble("CNT") == 1)
        {
            dobj  = CALLupso_simple_select_SEL1(Conn, dobj);           //  업소간단조회
            dobj  = CALLupso_simple_select_MRG1( dobj);        //  업소간단조회
        }
        else
        {
            dobj  = CALLupso_simple_select_SEL2(Conn, dobj);           //  조회결과가없을경우
            dobj  = CALLupso_simple_select_MRG1( dobj);        //  업소간단조회
        }
        return dobj;
    }
    // 정보확인
    public DOBJ CALLupso_simple_select_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_simple_select_SEL8(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        rvobj.Println("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_simple_select_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //업소 명
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  UPSO_CD  =  NVL(:UPSO_CD,UPSO_CD)   \n";
        query +=" AND  UPSO_NM  LIKE  '%'||:UPSO_NM||'%' ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 업소간단조회
    // 업소코드나 명으로 검색시 해당 정보를 조회한다
    public DOBJ CALLupso_simple_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_simple_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_simple_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //업소 명
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.BRAN_CD  ,  TA.UPSO_CD  ,  TA.UPSO_NM  ,  TA.STAFF_CD  ,  FIDU.GET_STAFF_NM(TA.STAFF_CD)  AS  STAFF_NM  ,  TA.UPSO_NEW_ADDR1  ||  DECODE(TA.UPSO_NEW_ADDR2,  '',  '',  ',  '||TA.UPSO_NEW_ADDR2)  ||  TA.UPSO_REF_INFO  UPSO_ADDR  ,  TB.BSTYP_CD  ,  TB.UPSO_GRAD  ,  TB.GRADNM  ,  TB.MONPRNCFEE  ,  TA.NEW_DAY  FROM  GIBU.TBRA_UPSO  TA  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD  ,  ZB.MONPRNCFEE  ,  ZC.GRADNM  ,  ZB.BSTYP_CD  ,  ZB.UPSO_GRAD  FROM(   \n";
        query +=" SELECT  A.UPSO_CD  ,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TB  WHERE  TA.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  TA.UPSO_CD  =  NVL(:UPSO_CD,  TA.UPSO_CD)   \n";
        query +=" AND  TA.UPSO_NM  LIKE  '%'||:UPSO_NM||'%'   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 업소간단조회
    public DOBJ CALLupso_simple_select_MRG1(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG1");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL1, SEL2","");
        rvobj.setName("MRG1") ;
        rvobj.setRetcode(1);
        rvobj.Println("MRG1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 조회결과가없을경우
    public DOBJ CALLupso_simple_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_simple_select_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_simple_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  BRAN_CD  ,  ''  UPSO_CD  ,  ''  UPSO_NM  ,  ''  STAFF_CD  ,  ''  STAFF_NM  ,  ''  UPSO_ADDR  ,  ''  BSTYP_CD  ,  ''  UPSO_GRAD  ,  ''  GRADNM  ,  ''  MONPRNCFEE  ,  ''  NEW_DAY  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$upso_simple_select
    //##**$$grade_simple_select
    /*
    */
    public DOBJ CTLgrade_simple_select(DOBJ dobj)
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
            dobj  = CALLgrade_simple_select_SEL1(Conn, dobj);           //  업종,사용료리스트
            dobj  = CALLgrade_simple_select_SEL2(Conn, dobj);           //  상세업종조회
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
    public DOBJ CTLgrade_simple_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLgrade_simple_select_SEL1(Conn, dobj);           //  업종,사용료리스트
        dobj  = CALLgrade_simple_select_SEL2(Conn, dobj);           //  상세업종조회
        return dobj;
    }
    // 업종,사용료리스트
    public DOBJ CALLgrade_simple_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLgrade_simple_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgrade_simple_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GRAD = dobj.getRetObject("S").getRecord().get("GRAD");   //등급
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BSTYP_CD  ||  GRAD_GBN  GRAD  ,  GRADNM  ,  STNDAMT  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  SUBSTR(:GRAD,  1,  1)   \n";
        query +=" AND  GRAD_GBN  =  SUBSTR(:GRAD,  2,  2) ";
        sobj.setSql(query);
        sobj.setString("GRAD", GRAD);               //등급
        return sobj;
    }
    // 상세업종조회
    public DOBJ CALLgrade_simple_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLgrade_simple_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLgrade_simple_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   GRAD = dobj.getRetObject("S").getRecord().get("GRAD");   //등급
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BSTYP_CD  ||  GRAD_GBN  AS  GRAD  ,  GRADNM  ,  STNDAMT  ,  STNDAREA_START  ,  STNDAREA_END  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  SUBSTR(:GRAD,  1,  1)  ORDER  BY  BSTYP_CD,  GRAD_GBN  ASC ";
        sobj.setSql(query);
        sobj.setString("GRAD", GRAD);               //등급
        return sobj;
    }
    //##**$$grade_simple_select
    //##**$$bank_select
    /* * 프로그램명 : bra12_r01
    * 작성자 : 박태현
    * 작성일 : 2009/09/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbank_select(DOBJ dobj)
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
            dobj  = CALLbank_select_SEL1(Conn, dobj);           //  은행 지점 조회
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
    public DOBJ CTLbank_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbank_select_SEL1(Conn, dobj);           //  은행 지점 조회
        return dobj;
    }
    // 은행 지점 조회
    // 은행 지점 조회
    public DOBJ CALLbank_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbank_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbank_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   SHOP_NM = dobj.getRetObject("S").getRecord().get("SHOP_NM");   //점포명
        String   BANK_NM = dobj.getRetObject("S").getRecord().get("BANK_NM");   //은행 명
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BANK_CD  ,  A.BANK_NM  ,  A.SHOP_NM  ,  A.PHON_NUM  ,  A.FAX_NUM  ,  A.POST_NUM  ,  A.REMAK  FROM  ACCT.TCAC_BANK_7  A  WHERE  A.BANK_NM  LIKE  DECODE(:BANK_NM,  NULL,  A.BANK_NM,  :BANK_NM)  ||  '%'   \n";
        query +=" AND  A.SHOP_NM  LIKE  DECODE(:SHOP_NM,  NULL,  A.SHOP_NM,  :SHOP_NM)  ||  '%' ";
        sobj.setSql(query);
        sobj.setString("SHOP_NM", SHOP_NM);               //점포명
        sobj.setString("BANK_NM", BANK_NM);               //은행 명
        return sobj;
    }
    //##**$$bank_select
    //##**$$bank_search
    /*
    */
    public DOBJ CTLbank_search(DOBJ dobj)
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
            dobj  = CALLbank_search_SEL1(Conn, dobj);           //  은행정보조회
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
    public DOBJ CTLbank_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbank_search_SEL1(Conn, dobj);           //  은행정보조회
        return dobj;
    }
    // 은행정보조회
    // 업소별자동이체은행등록 메뉴에서 사용할 은행정보를 조회한다
    public DOBJ CALLbank_search_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbank_search_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbank_search_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //은행 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BANK_CD  ,  BANK_NM  ,  SHOP_NM  FROM  (   \n";
        query +=" SELECT  BANK_CD  ,  BANK_NM  ,  SHOP_NM  FROM  ACCT.TCAC_BANK_7  WHERE  BANK_CD  =  :BANK_CD  UNION  ALL   \n";
        query +=" SELECT  :BANK_CD  ,  BANK_NM  ,  NULL  FROM  ACCT.TCAC_BANK_7  WHERE  BANK_CD  LIKE  SUBSTR(:BANK_CD,1,3)||'%'   \n";
        query +=" AND  ROWNUM  =  1  )  WHERE  ROWNUM  =1 ";
        sobj.setSql(query);
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        return sobj;
    }
    //##**$$bank_search
    //##**$$accn_num_select2
    /*
    */
    public DOBJ CTLaccn_num_select2(DOBJ dobj)
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
            dobj  = CALLaccn_num_select2_SEL1(Conn, dobj);           //  지부별 은행계좌 조회
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
    public DOBJ CTLaccn_num_select2( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLaccn_num_select2_SEL1(Conn, dobj);           //  지부별 은행계좌 조회
        return dobj;
    }
    // 지부별 은행계좌 조회
    // 지부별 은행계좌 조회
    public DOBJ CALLaccn_num_select2_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLaccn_num_select2_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLaccn_num_select2_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ALL_TYPE = dvobj.getRecord().get("ALL_TYPE");   //전체포함여부
        String   USE_TYPE = dobj.getRetObject("S").getRecord().get("USE_TYPE");   //사용 유형
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ACCN_SEQ  ,  ACCN_NUM  ,  BANK_CD  ,  BANK_NM  ,  BRAN_CD  ,  BRAN_NM  ,  USAG  FROM  (   \n";
        query +=" SELECT  ''  AS  ACCN_SEQ  ,  ''  AS  ACCN_NUM  ,  ''  AS  BANK_CD  ,  '전체'  AS  BANK_NM  ,  :BRAN_CD  AS  BRAN_CD  ,  DEPT_NM  AS  BRAN_NM  ,  ''  AS  USAG  ,  0  AS  ALL_TYPE  FROM  INSA.TCPM_DEPT  WHERE  GIBU  =  :BRAN_CD  UNION  ALL   \n";
        query +=" SELECT  A.ACCN_SEQ  ,  A.ACCN_NUM  ,  A.BANK_CD  ,  B.BANK_NM  ,  C.GIBU  BRAN_CD  ,  C.DEPT_NM  BRAN_NM  ,  A.USAG  ,  1  FROM  ACCT.TCAC_ACCOUNT  A  ,  ACCT.TCAC_BANK  B  ,  INSA.TCPM_DEPT  C  WHERE  NVL(C.GIBU,  '-')  =  DECODE(:USE_TYPE,  '002',  '-','003',  '-',  '004',  '-',  DECODE(:BRAN_CD,  'AL',  C.GIBU,  :BRAN_CD))   \n";
        query +=" AND  A.DEPT_CD  =  C.DEPT_CD   \n";
        query +=" AND  B.BANK_CD  =  A.BANK_CD   \n";
        query +=" AND  A.USE_TYPE  =  NVL(:USE_TYPE,  A.USE_TYPE)   \n";
        query +=" AND  A.ACCTN_GBN  =  '001'   \n";
        query +=" AND  A.USE_YN  =  'Y'  )  XA  WHERE  ALL_TYPE  =  DECODE(:ALL_TYPE,  NULL,  1,  XA.ALL_TYPE)  ORDER  BY  BANK_NM  ASC ";
        sobj.setSql(query);
        sobj.setString("ALL_TYPE", ALL_TYPE);               //전체포함여부
        sobj.setString("USE_TYPE", USE_TYPE);               //사용 유형
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$accn_num_select2
    //##**$$c_gibu_select
    /* * 프로그램명 : bra12_r01
    * 작성자 : 윤지환
    * 작성일 : 2009/10/02
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLc_gibu_select(DOBJ dobj)
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
            dobj  = CALLc_gibu_select_SEL1(Conn, dobj);           //  지부리스트
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
    public DOBJ CTLc_gibu_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLc_gibu_select_SEL1(Conn, dobj);           //  지부리스트
        return dobj;
    }
    // 지부리스트
    public DOBJ CALLc_gibu_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLc_gibu_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLc_gibu_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  'AL'  AS  GIBU  ,  '--전체--'  AS  DEPT_NM  ,  '106010000'  AS  DEPT_CD  FROM  DUAL  UNION  ALL   \n";
        query +=" SELECT  GIBU,  DEPT_NM,  DEPT_CD  FROM  INSA.TCPM_DEPT  WHERE  GIBU  IS  NOT  NULL   \n";
        query +=" AND  USE_YN  =  'Y'   \n";
        query +=" AND  GIBU  <>  'AL'  ORDER  BY  DEPT_CD ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$c_gibu_select
    //##**$$p_staff_select
    /* * 프로그램명 : bra12_r01
    * 작성자 : 서정재
    * 작성일 : 2009/07/28
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLp_staff_select(DOBJ dobj)
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
            dobj  = CALLp_staff_select_SEL1(Conn, dobj);           //  지부사원리스트
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
    public DOBJ CTLp_staff_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLp_staff_select_SEL1(Conn, dobj);           //  지부사원리스트
        return dobj;
    }
    // 지부사원리스트
    // 퇴사사원을 제외한 해당지부의 사원들을 검색한다
    public DOBJ CALLp_staff_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_staff_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_staff_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.STAFF_NUM  STAFF_CD  ,  B.HAN_NM  STAFF_NM  ,  B.ETCOM_DAY  FROM  INSA.TCPM_DEPT  A  ,  INSA.TINS_MST01  B  WHERE  A.DEPT_CD  =  B.DEPT_CD   \n";
        query +=" AND  A.GIBU  =:BRAN_CD   \n";
        query +=" AND  B.RETIRE_DAY  IS  NULL  UNION   \n";
        query +=" SELECT  B.STAFF_NUM  STAFF_CD  ,  B.HAN_NM  STAFF_NM  ,  B.ETCOM_DAY  FROM  INSA.TCPM_DEPT  A  ,  INSA.TINS_MST01  B  WHERE  A.DEPT_CD  =  B.DUAL_DEPT_CD   \n";
        query +=" AND  NVL(B.DUAL_YN,0)=1   \n";
        query +=" AND  A.GIBU  =:BRAN_CD   \n";
        query +=" AND  B.RETIRE_DAY  IS  NULL  ORDER  BY  STAFF_CD ";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$p_staff_select
    //##**$$p_grade_select
    /* * 프로그램명 : bra12_r01
    * 작성자 : 서정재
    * 작성일 : 2009/07/17
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLp_grade_select(DOBJ dobj)
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
            dobj  = CALLp_grade_select_SEL1(Conn, dobj);           //  업종,사용료리스트
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
    public DOBJ CTLp_grade_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLp_grade_select_SEL1(Conn, dobj);           //  업종,사용료리스트
        return dobj;
    }
    // 업종,사용료리스트
    public DOBJ CALLp_grade_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLp_grade_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLp_grade_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.BSTYP_CD  ||  A.GRAD_GBN  GRAD  ,  A.GRADNM  ,  A.STNDAMT  ,  A.STNDAREA_START  ,  A.STNDAREA_END  FROM  GIBU.TBRA_BSTYPGRAD  A  ,  GIBU.TBRA_BSTYPGRAD  B  WHERE  A.BSTYP_CD  =  B.GRAD_GBN   \n";
        query +=" AND  B.BSTYP_CD  =  'z'  ORDER  BY  A.BSTYP_CD  ||  A.GRAD_GBN  ASC ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$p_grade_select
    //##**$$chk_closed_yrmn
    /*
    */
    public DOBJ CTLchk_closed_yrmn(DOBJ dobj)
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
            dobj  = CALLchk_closed_yrmn_SEL3(Conn, dobj);           //  휴폐업체크
            if(!dobj.getRetObject("SEL3").getRecord().get("REPT_GBN").equals("") && dobj.getRetObject("SEL3").getRecord().get("REPT_GBN").equals("14"))
            {
                dobj  = CALLchk_closed_yrmn_SEL6(Conn, dobj);           //  폐업
                dobj  = CALLchk_closed_yrmn_MRG5( dobj);        //  최종결과
            }
            else if(!dobj.getRetObject("SEL3").getRecord().get("REPT_GBN").equals("") && !dobj.getRetObject("SEL3").getRecord().get("REPT_GBN").equals("14"))
            {
                dobj  = CALLchk_closed_yrmn_SEL1(Conn, dobj);           //  휴업
                dobj  = CALLchk_closed_yrmn_MRG5( dobj);        //  최종결과
            }
            else
            {
                dobj  = CALLchk_closed_yrmn_SEL8(Conn, dobj);           //  정상
                dobj  = CALLchk_closed_yrmn_MRG5( dobj);        //  최종결과
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
    public DOBJ CTLchk_closed_yrmn( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLchk_closed_yrmn_SEL3(Conn, dobj);           //  휴폐업체크
        if(!dobj.getRetObject("SEL3").getRecord().get("REPT_GBN").equals("") && dobj.getRetObject("SEL3").getRecord().get("REPT_GBN").equals("14"))
        {
            dobj  = CALLchk_closed_yrmn_SEL6(Conn, dobj);           //  폐업
            dobj  = CALLchk_closed_yrmn_MRG5( dobj);        //  최종결과
        }
        else if(!dobj.getRetObject("SEL3").getRecord().get("REPT_GBN").equals("") && !dobj.getRetObject("SEL3").getRecord().get("REPT_GBN").equals("14"))
        {
            dobj  = CALLchk_closed_yrmn_SEL1(Conn, dobj);           //  휴업
            dobj  = CALLchk_closed_yrmn_MRG5( dobj);        //  최종결과
        }
        else
        {
            dobj  = CALLchk_closed_yrmn_SEL8(Conn, dobj);           //  정상
            dobj  = CALLchk_closed_yrmn_MRG5( dobj);        //  최종결과
        }
        return dobj;
    }
    // 휴폐업체크
    // 조회조건으로 넘어온 END_YRMN이 휴업또는 폐업인지 체크한다
    public DOBJ CALLchk_closed_yrmn_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLchk_closed_yrmn_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_closed_yrmn_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //종료년월
        String   END_YRMN_1 = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  REPT_GBN  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  =  substr(:END_YRMN_1,  0,  6) ";
        sobj.setSql(query);
        sobj.setString("END_YRMN_1", END_YRMN_1);               //종료년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 폐업
    public DOBJ CALLchk_closed_yrmn_SEL6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL6");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL6");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLchk_closed_yrmn_SEL6(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL6");
        rvobj.Println("SEL6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_closed_yrmn_SEL6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_GBN = dobj.getRetObject("SEL3").getRecord().get("REPT_GBN");   //입금 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  :REPT_GBN  AS  REPT_GBN  ,  ''  AS  END_YRMN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        return sobj;
    }
    // 최종결과
    public DOBJ CALLchk_closed_yrmn_MRG5(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG5");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL6, SEL1, SEL8","");
        rvobj.setName("MRG5") ;
        rvobj.setRetcode(1);
        rvobj.Println("MRG5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 휴업
    public DOBJ CALLchk_closed_yrmn_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLchk_closed_yrmn_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_closed_yrmn_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //종료년월
        String   END_YRMN_1 = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(ADD_MONTHS(TO_DATE(LAST_YRMN,  'YYYYMM'),  1),  'YYYYMM')  END_YRMN  ,  REPT_GBN  FROM  GIBU.TBRA_NOTE  A  ,  (   \n";
        query +=" SELECT  MAX(NOTE_YRMN)  LAST_YRMN  ,  UPSO_CD  FROM  GIBU.TBRA_NOTE  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  NOTE_YRMN  >=  substr(:END_YRMN_1,  0,  6)   \n";
        query +=" AND  REPT_GBN  BETWEEN  '11'   \n";
        query +=" AND  '19'  GROUP  BY  UPSO_CD  )  B  WHERE  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  B.LAST_YRMN  =  A.NOTE_YRMN ";
        sobj.setSql(query);
        sobj.setString("END_YRMN_1", END_YRMN_1);               //종료년월
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 정상
    // 해당 END_YRMN으로 N EXT_AMT를 구하면 된다
    public DOBJ CALLchk_closed_yrmn_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLchk_closed_yrmn_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        rvobj.Println("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_closed_yrmn_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String END_YRMN ="";        //종료년월
        String   END_YRMN_1 = dobj.getRetObject("S").getRecord().get("END_YRMN");   //종료년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ''  AS  REPT_GBN  ,  substr(:END_YRMN_1,  0,  6)  AS  END_YRMN  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("END_YRMN_1", END_YRMN_1);               //종료년월
        return sobj;
    }
    //##**$$chk_closed_yrmn
    //##**$$end
}
