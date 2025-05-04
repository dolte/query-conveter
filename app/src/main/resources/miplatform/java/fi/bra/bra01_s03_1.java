
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s03_1
{
    public bra01_s03_1()
    {
    }
    //##**$$upso_amt_select
    /* * 프로그램명 : bra01_s03
    * 작성자 : 서정재
    * 작성일 : 2009/09/03
    * 설명 : 업소의 사용료 히스토리를 조회한다. 노래방일 경우 노래방 상세정보도 같이 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLupso_amt_select(DOBJ dobj)
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
            dobj  = CALLupso_amt_select_SEL1(Conn, dobj);           //  업소사용료리스트
            dobj  = CALLupso_amt_select_SEL4(Conn, dobj);           //  월사용료 지분율 등록정보
            if( dobj.getRetObject("SEL1").firstRecord().get("ROWNUM").equals("1") && dobj.getRetObject("SEL1").firstRecord().get("BSTYP_CD").equals("o"))
            {
                dobj  = CALLupso_amt_select_SEL2(Conn, dobj);           //  노래방상세정보
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
    public DOBJ CTLupso_amt_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_amt_select_SEL1(Conn, dobj);           //  업소사용료리스트
        dobj  = CALLupso_amt_select_SEL4(Conn, dobj);           //  월사용료 지분율 등록정보
        if( dobj.getRetObject("SEL1").firstRecord().get("ROWNUM").equals("1") && dobj.getRetObject("SEL1").firstRecord().get("BSTYP_CD").equals("o"))
        {
            dobj  = CALLupso_amt_select_SEL2(Conn, dobj);           //  노래방상세정보
        }
        return dobj;
    }
    // 업소사용료리스트
    // 해당 업소의 사용료 히스토리를 조회한다.
    public DOBJ CALLupso_amt_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_amt_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  ROWNUM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  ,  TRIM(A.BSTYP_CD)  ||  A.UPSO_GRAD  GRAD  ,  TRIM(A.BSTYP_CD)  BSTYP_CD  ,  A.UPSO_GRAD  ,  A.MONPRNCFEE  ,  A.MONPRNCFEE2  ,  A.APPL_DAY  ,  A.MCHNDAESU  ,  A.REMAK  ,  A.UPSO_CD  ,  A.USE_TIME  FROM  GIBU.TBRA_UPSORTAL_INFO  A  WHERE  A.UPSO_CD  =:UPSO_CD  ORDER  BY  JOIN_SEQ ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 월사용료 지분율 등록정보
    public DOBJ CALLupso_amt_select_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_amt_select_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        rvobj.Println("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_select_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  APPL_DAY,  RATE  FROM  GIBU.TBRA_FEERATE_HISTY  WHERE  BSTYP_CD  =  GIBU.FT_GET_BSTYP_INFO(:UPSO_CD)  ORDER  BY  APPL_DAY ";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 노래방상세정보
    // 첫번째 정보의 업종코드가 노래방일 경우 노래방 상세정보도 같이 보내준다
    public DOBJ CALLupso_amt_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_amt_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.setRetcode(1);
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_amt_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CHG_BRAN = dobj.getRetObject("SEL1").firstRecord().get("CHG_BRAN");   //변경 지부
        String   CHG_NUM = dobj.getRetObject("SEL1").firstRecord().get("CHG_NUM");   //변경 번호
        String   CHG_DAY = dobj.getRetObject("SEL1").firstRecord().get("CHG_DAY");   //변경 일자
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.BSTYP_CD  ||  A.GRAD_GBN  GRAD  ,  A.BSTYP_CD  BSTYP_CD  ,  A.GRAD_GBN  ,  A.AREA  ,  A.MCHNDAESU  ,  A.STNDAMT  ,  B.GRADNM  ,  A.CHG_DAY  ,  A.CHG_NUM  ,  A.CHG_BRAN  ,  A.GRAD_NUM  ,  A.MCHNDAESU  *  A.STNDAMT  AMT  FROM  GIBU.TBRA_NOREBANG_INFO  A  ,  GIBU.TBRA_BSTYPGRAD  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.BSTYP_CD  =  B.BSTYP_CD   \n";
        query +=" AND  A.GRAD_GBN  =  B.GRAD_GBN   \n";
        query +=" AND  A.CHG_DAY  =  :CHG_DAY   \n";
        query +=" AND  A.CHG_NUM  =  :CHG_NUM   \n";
        query +=" AND  A.CHG_BRAN  =  :CHG_BRAN ";
        sobj.setSql(query);
        sobj.setString("CHG_BRAN", CHG_BRAN);               //변경 지부
        sobj.setString("CHG_NUM", CHG_NUM);               //변경 번호
        sobj.setString("CHG_DAY", CHG_DAY);               //변경 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    //##**$$upso_amt_select
    //##**$$end
}
