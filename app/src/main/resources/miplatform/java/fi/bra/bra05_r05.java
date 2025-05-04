
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra05_r05
{
    public bra05_r05()
    {
    }
    //##**$$bpap_reprint
    /* * 프로그램명 : bra05_r05
    * 작성자 : 서정재
    * 작성일 : 2009/09/30
    * 설명 :
    GIBU.TBRA_BPAP_PRNT_HISTY의 BPAP_GBN = '2'인 정보를 조회한다.
    1.SEL.SEL1 :
    - 지부의 은행이름(BANK), 계좌번호(ACCT_NO)는 기존 CS에서 설정한 값 그대로 사용하기로 함
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbpap_reprint(DOBJ dobj)
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
            dobj  = CALLbpap_reprint_SEL1(Conn, dobj);           //  최고서 재출력
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
    public DOBJ CTLbpap_reprint( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbpap_reprint_SEL1(Conn, dobj);           //  최고서 재출력
        return dobj;
    }
    // 최고서 재출력
    public DOBJ CALLbpap_reprint_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbpap_reprint_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbpap_reprint_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_CD = dobj.getRetObject("S").getRecord().get("STAFF_CD");   //사원 코드
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  B.UPSO_NM  ,  B.MNGEMSTR_NM  AS  RECVPRES  ,  A.START_YRMN  ,  A.END_YRMN  ,  A.MONPRNCFEE  ,  A.NONPY_AMT  ,  A.TOT_ADDT_AMT  +  A.TOT_EADDT_AMT  AS  ADDT_AMT  ,  A.TOT_EADDT_AMT  AS  EXT_ADDT_AMT  ,  A.TOT_DEMD_AMT  AS  TOT_AMT  ,  C.BIPLC_NM  AS  BRAN_NM  ,  C.ADDR||'  '||C.HNM  AS  BRAN_ADDR  ,  C.POST_NUM  AS  BRAN_ZIP  ,   \n";
        query +=" (SELECT  IPPBX_INPHONE_NUM  FROM  FIDU.TENV_MEMBER  WHERE  USER_ID  =  B.STAFF_CD)  AS  BRAN_PHON  ,  B.UPSO_NEW_ADDR1  ||  DECODE(B.UPSO_NEW_ADDR2,  '',  '',  ',  '||B.UPSO_NEW_ADDR2)  ||  B.UPSO_REF_INFO  AS  RECV_ADDR  ,  B.UPSO_NEW_ZIP  AS  RECV_ZIP  ,  A.PAY_DAY  ,  DECODE(B.BRAN_CD,  'A',  '국민은행','B',  '국민은행','C',  '국민은행',  'E'  ,  '국민은행'  ,  'F'  ,  '국민은행'  ,  'G'  ,  '농협'  ,  'H'  ,  '농협'  ,  'I'  ,  '농협'  ,  'J'  ,  '농협'  ,  'K'  ,  '농협'  ,  'L'  ,  '국민은행'  ,  'M'  ,  '농협'  ,  'N'  ,  '부산은행'  ,  'O'  ,  '농협')  AS  BANK  ,  DECODE(B.BRAN_CD,  'A'  ,  '695037-01-001228'  ,  'B'  ,  '695037-01-001257'  ,  'C'  ,  '695037-01-001231'  ,  'E'  ,  '695037-01-001260'  ,  'F'  ,  '695037-01-001244'  ,  'G'  ,  '209-01-581021'  ,  'H'  ,  '311-01-155951'  ,  'I'  ,  '311-01-155951'  ,  'J'  ,  '511-01-073417'  ,  'K'  ,  '661-01-033882'  ,  'L'  ,  '632-01-0046-816'  ,  'M'  ,  '815135-51-018283'  ,  'N'  ,  '131-01-000342-2'  ,  'O'  ,  '901017-51-011928')  AS  ACCT_NO  ,  FIDU.GET_STAFF_NM(B.STAFF_CD)  AS  STAFF_NM  FROM  GIBU.TBRA_BPAP_PRNT_HISTY  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_BIPLC  C  WHERE  B.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  B.UPSO_CD  =  DECODE(:UPSO_CD,NULL,B.UPSO_CD,:UPSO_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.BPAP_DAY  LIKE  :YRMN  ||'%'   \n";
        query +=" AND  A.BPAP_GBN  =  '2'   \n";
        query +=" AND  C.GIBU  =  B.BRAN_CD   \n";
        query +=" AND  B.STAFF_CD  =  NVL(:STAFF_CD,  B.STAFF_CD)  ORDER  BY  B.STAFF_CD,  B.UPSO_NM ";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$bpap_reprint
    //##**$$end
}
