
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac10_r20
{
    public tac10_r20()
    {
    }
    //##**$$tac10_r20_select
    /* * 프로그램명 : tac10_r20
    * 작성자 : 성낙문
    * 작성일 : 2009/10/15
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac10_r20_select(DOBJ dobj)
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
            dobj  = CALLtac10_r20_select_SEL1(Conn, dobj);           //  은행파일 지급현황
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
    public DOBJ CTLtac10_r20_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac10_r20_select_SEL1(Conn, dobj);           //  은행파일 지급현황
        return dobj;
    }
    // 은행파일 지급현황
    public DOBJ CALLtac10_r20_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac10_r20_select_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac10_r20_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   SUPP_AMT = dobj.getRetObject("S").getRecord().getDouble("SUPP_AMT");   //지급 금액
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //은행 코드
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   CLAIMPRES_CD = dobj.getRetObject("S").getRecord().get("CLAIMPRES_CD");   //채권자코드
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //계좌 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.SUPP_YRMN, A.TRNSF_GBN, D.CODE_NM, A.MB_CD, A.NM, A.ACCN_NUM, A.BANK_CD, C.BANK_NM, A.SUPP_AMT, A.CLAIMPRES_CD, A.CLAIMPRES_NM, A.PTTN_RATE  ";
        query +=" FROM FIDU.TTAC_BANKFILE A, ACCT.TCAC_BANK C, FIDU.TENV_CODE D  ";
        query +=" WHERE A.BANK_CD = C.BANK_CD  ";
        query +=" AND A.TRNSF_GBN = D.CODE_CD  ";
        query +=" AND D.HIGH_CD = '00294'  ";
        query +=" AND A.SUPP_YRMN BETWEEN SUBSTR(:FROMDATE,1,6)  ";
        query +=" AND SUBSTR(:TODATE,1,6)  ";
        query +=" AND A.BANK_CD LIKE :BANK_CD || '%'  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND (A.MB_CD LIKE :MB_CD || '%')  ";
        }
        if( SUPP_AMT!= 0)
        {
            query +=" AND A.SUPP_AMT = :SUPP_AMT  ";
        }
        if( !ACCN_NUM.equals("") )
        {
            query +=" AND A.ACCN_NUM = :ACCN_NUM  ";
        }
        if( !CLAIMPRES_CD.equals("") )
        {
            query +=" AND (A.MB_CD LIKE :CLAIMPRES_CD || '%')  ";
        }
        query +=" ORDER BY A.NM  ";
        sobj.setSql(query);
        if(SUPP_AMT!=0)
        {
            sobj.setDouble("SUPP_AMT", SUPP_AMT);               //지급 금액
        }
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        if(!CLAIMPRES_CD.equals(""))
        {
            sobj.setString("CLAIMPRES_CD", CLAIMPRES_CD);               //채권자코드
        }
        if(!ACCN_NUM.equals(""))
        {
            sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        }
        return sobj;
    }
    //##**$$tac10_r20_select
    //##**$$end
}
