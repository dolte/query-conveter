
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_r01
{
    public bra10_r01()
    {
    }
    //##**$$bscon_create_file
    /*
    */
    public DOBJ CTLbscon_create_file(DOBJ dobj)
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
            dobj  = CALLbscon_create_file_SEL1(Conn, dobj);           //  파일생성
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
    public DOBJ CTLbscon_create_file( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbscon_create_file_SEL1(Conn, dobj);           //  파일생성
        return dobj;
    }
    // 파일생성
    // 2017.12.15 이다섭 수정 1. 청구는 되고 입금이 안된 건도 표시   1-1. 기존코드에서 'PROC_AMT(REPT_AMT로 출력)>0' 삭제   1-2. 입금이 없는경우 MAPPING도 없으므로 DECODE추가 하여 처리년월과 청구년월이 같도록 설정 2. 계산서 발행 여부 표시   2-1. MYB를 사용하여 계산서 발행 여부 표시  2017.12.19 이다섭 수정 1. 청구년월이 처리년월과 같은 것 출력 2. 매핑년월이 처리년월과 같은 것 출력 3. 매핑년월이 처리년월보다 큰 경우 입금이 있어도 표시하지 않음  2017.12.27 이다섭 수정 1. 페이징처리 오류 수정  2017.12.28 함저협과 음실연,음산협으로 분기 함저협은 이전처럼 입금된 것만 표기, 나머지는 청구만 된것도 표기
    public DOBJ CALLbscon_create_file_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbscon_create_file_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbscon_create_file_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PAGECNT = dobj.getRetObject("S").getRecord().get("PAGECNT");   //페이지조회건수
        String   PROC_YRMN = dobj.getRetObject("S").getRecord().get("PROC_YRMN");   //처리 년월
        double   PAGENUMBER = dobj.getRetObject("S").getRecord().getDouble("PAGENUMBER");   //페이지 넘버값
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEMD_YRMN  ,  BSTYP_NM  ,  BSCON_CD  ,  BSCON_UPSO_CD  ,  BSCON_UPSO_NM  ,  DEMD_DAY  ,  DEMD_AMT  ,  CASE  WHEN  TO_NUMBER(MAPPING_YRMN)  >  TO_NUMBER(:PROC_YRMN)  THEN  ''  ELSE  REPT_YRMN  END  REPT_YRMN  ,  CASE  WHEN  TO_NUMBER(MAPPING_YRMN)  >  TO_NUMBER(:PROC_YRMN)  THEN  ''  ELSE  REPT_DAY  END  REPT_DAY  ,  CASE  WHEN  TO_NUMBER(MAPPING_YRMN)  >  TO_NUMBER(:PROC_YRMN)  THEN  0  ELSE  REPT_AMT  END  REPT_AMT  ,  RETURN_YRMN  ,  RETURN_DAY  ,  RETURN_AMT  ,  BIGO  ,  TOT_CNT  ,  BILL_YN  FROM  (   \n";
        query +=" SELECT  ROWNUM  AS  RNUM  ,   \n";
        query +=" (SELECT  GRADNM  FROM  GIBU.TBRA_BSTYPGRAD  WHERE  BSTYP_CD  =  'z'   \n";
        query +=" AND  GRAD_GBN  =  A.BSTYP_CD)  AS  BSTYP_NM  ,  DEMD_YRMN  ,  BSCON_CD  ,  BSCON_UPSO_CD  ,  BSCON_UPSO_NM  ,  DEMD_DAY  ,  DEMD_AMT  ,  REPT_YRMN  ,  REPT_DAY  ,  REPT_AMT  ,  RETURN_YRMN  ,  RETURN_DAY  ,  RETURN_AMT  ,  BIGO  ,  TOT_CNT  ,  UPSO_CD  ,  MAPPING_YRMN  ,  BILL_YN  FROM  (   \n";
        query +=" SELECT  MYA.DEMD_YRMN  ,  MYA.BSCON_CD  ,  MYA.BSCON_UPSO_CD  ,  MYA.BSCON_UPSO_NM  ,  MYA.DEMD_DAY  ,  MYA.DEMD_AMT  ,  MYA.REPT_YRMN  ,  MYA.REPT_DAY  ,  MYA.REPT_AMT  ,  MYA.RETURN_YRMN  ,  MYA.RETURN_DAY  ,  MYA.RETURN_AMT  ,  MYA.BIGO  ,  MYA.BSTYP_CD  ,  COUNT(0)  OVER  ()  AS  TOT_CNT  ,  MYA.UPSO_CD  ,  MYA.MAPPING_YRMN  ,  DECODE(BILL_NUM,  NULL,  'N',  'Y')  AS  BILL_YN  FROM  (   \n";
        query +=" SELECT  A.DEMD_YRMN  ,  A.BSCON_CD  ,   \n";
        query +=" (SELECT  MAX(BSCON_UPSO_CD)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  BSCON_CD  =  A.BSCON_CD)  AS  BSCON_UPSO_CD  ,   \n";
        query +=" (SELECT  MAX(BSCON_UPSO_NM)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  BSCON_CD  =  A.BSCON_CD)  AS  BSCON_UPSO_NM  ,  A.DEMD_DAY  ,  A.DEMD_AMT  ,  A.REPT_YRMN  ,  A.REPT_DAY  ,  A.PROC_AMT  AS  REPT_AMT  ,  ''  AS  RETURN_YRMN  ,  ''  AS  RETURN_DAY  ,  0  AS  RETURN_AMT  ,  ''  AS  BIGO  ,  A.BSTYP_CD  ,  A.UPSO_CD  ,  A.MAPPING_YRMN  FROM  GIBU.TBRA_DEMD_REPT_BSCON  A  WHERE  (DECODE(:BSCON_CD,  'T0000001',  MAPPING_YRMN,  DECODE(MAPPING_YRMN,  NULL,  DEMD_YRMN,  MAPPING_YRMN))  =  :PROC_YRMN  )   \n";
        query +=" AND  A.BSCON_CD  =  :BSCON_CD  UNION  ALL   \n";
        query +=" SELECT  ''  AS  DEMD_YRMN  ,  A.BSCON_CD  ,   \n";
        query +=" (SELECT  MAX(BSCON_UPSO_CD)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  BSCON_CD  =  A.BSCON_CD)  AS  BSCON_UPSO_CD  ,   \n";
        query +=" (SELECT  MAX(BSCON_UPSO_NM)  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  BSCON_CD  =  A.BSCON_CD)  AS  BSCON_UPSO_NM  ,  ''  AS  DEMD_DAY  ,  0  AS  DEMD_AMT  ,  ''  AS  REPT_YRMN  ,  ''  AS  REPT_DAY  ,  0  AS  REPT_AMT  ,  A.RETURN_YRMN  ,  A.RETURN_DAY  ,  A.PROC_AMT  AS  RETURN_AMT  ,  C.REMAK  AS  BIGO  ,  A.BSTYP_CD  ,  A.UPSO_CD  ,  ''  AS  MAPPING_YRMN  FROM  GIBU.TBRA_BSCON_RETURN  A  ,  GIBU.TBRA_REPT_RETURN  C  WHERE  A.RETURN_DAY  =  C.RETURN_DAY   \n";
        query +=" AND  A.RETURN_NUM  =  C.RETURN_NUM   \n";
        query +=" AND  A.RETURN_YRMN  =  :PROC_YRMN   \n";
        query +=" AND  A.BSCON_CD  =  :BSCON_CD  )  MYA,  (   \n";
        query +=" SELECT  UPSO_CD  ,  SUPPBSCON_CD  ,  MAX(BILL_NUM)  AS  BILL_NUM  FROM  GIBU.TBRA_BILL_ISS_MST  A  ,  GIBU.TBRA_BILL_ISS_DTL  B  WHERE  A.DEMD_NUM  =  B.DEMD_NUM   \n";
        query +=" AND  B.BILL_KND  IN  ('4',  '5',  '6')   \n";
        query +=" AND  A.APPTN_YRMN  =  :PROC_YRMN   \n";
        query +=" AND  SUPPBSCON_CD  =  :BSCON_CD  GROUP  BY  UPSO_CD,  SUPPBSCON_CD  )  MYB  WHERE  MYA.UPSO_CD  =  MYB.UPSO_CD(+)   \n";
        query +=" AND  (DEMD_AMT  !=0   \n";
        query +=" OR  REPT_AMT  !=0   \n";
        query +=" OR  RETURN_AMT  !=0)  ORDER  BY  UPSO_CD,  DEMD_DAY  )  A  WHERE  ROWNUM  <=  TO_NUMBER(:PAGENUMBER)  *  TO_NUMBER(:PAGECNT)  )  WHERE  RNUM  BETWEEN  ((TO_NUMBER(:PAGENUMBER)  -  1)  *  TO_NUMBER(:PAGECNT)  +  1)   \n";
        query +=" AND  (TO_NUMBER(:PAGENUMBER)  *  TO_NUMBER(:PAGECNT)) ";
        sobj.setSql(query);
        sobj.setString("PAGECNT", PAGECNT);               //페이지조회건수
        sobj.setString("PROC_YRMN", PROC_YRMN);               //처리 년월
        sobj.setDouble("PAGENUMBER", PAGENUMBER);               //페이지 넘버값
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    //##**$$bscon_create_file
    //##**$$sel_bscon_rept
    /*
    */
    public DOBJ CTLsel_bscon_rept(DOBJ dobj)
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
            dobj  = CALLsel_bscon_rept_SEL1(Conn, dobj);           //  입금내역조회
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
    public DOBJ CTLsel_bscon_rept( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsel_bscon_rept_SEL1(Conn, dobj);           //  입금내역조회
        return dobj;
    }
    // 입금내역조회
    // 2017.12.19 이다섭 수정 파일생성방식이 달라짐에 따라 내용 수정 청구 : 입력된 처리년월에 청구된 것을 합 입금 : 입력된 처리년월에 매핑된 것을 합 반환 : 입력된 처리년월에 반환된 것을 합  2017.12.28 이다섭 수정 함저협과 음실연, 음산협 분기로 함저협 청구는 안맞음
    public DOBJ CALLsel_bscon_rept_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsel_bscon_rept_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsel_bscon_rept_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PROC_YRMN = dobj.getRetObject("S").getRecord().get("PROC_YRMN");   //처리 년월
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  YRMN  AS  PROC_YRMN  ,  BSCON_CD  ,  NVL(SUM(DEMD_AMT),  0)  AS  DEMD_AMT  ,  NVL(SUM(REPT_AMT),  0)  AS  REPT_AMT  ,  NVL(SUM(RETURN_AMT),  0)  AS  RETURN_AMT  FROM  (   \n";
        query +=" SELECT  DEMD_YRMN  AS  YRMN  ,  BSCON_CD  ,  SUM(DEMD_AMT)  AS  DEMD_AMT  ,  0  AS  REPT_AMT  ,  0  AS  RETURN_AMT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  DEMD_YRMN  =  :PROC_YRMN  GROUP  BY  DEMD_YRMN,  BSCON_CD  UNION  ALL   \n";
        query +=" SELECT  MAPPING_YRMN  AS  YRMN  ,  BSCON_CD  ,  0  AS  DEMD_AMT  ,  SUM(PROC_AMT)  AS  REPT_AMT  ,  0  AS  RETURN_AMT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  WHERE  MAPPING_YRMN  =  :PROC_YRMN  GROUP  BY  MAPPING_YRMN,  BSCON_CD  UNION  ALL   \n";
        query +=" SELECT  RETURN_YRMN  AS  YRMN  ,  BSCON_CD  ,  0  AS  DEMD_AMT  ,  0  AS  REPT_AMT  ,  SUM(PROC_AMT)  AS  RETURN_AMT  FROM  GIBU.TBRA_BSCON_RETURN  WHERE  RETURN_YRMN  =  :PROC_YRMN  GROUP  BY  RETURN_YRMN,  BSCON_CD  )  WHERE  1=1   \n";
        query +=" AND  BSCON_CD  =  NVL(:BSCON_CD,BSCON_CD)  GROUP  BY  YRMN,  BSCON_CD  ORDER  BY  BSCON_CD ";
        sobj.setSql(query);
        sobj.setString("PROC_YRMN", PROC_YRMN);               //처리 년월
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    //##**$$sel_bscon_rept
    //##**$$end
}
