
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra06_p07
{
    public bra06_p07()
    {
    }
    //##**$$auto_upso_list
    /* * 프로그램명 : bra06_p07
    * 작성자 : 서정재
    * 작성일 : 2009/10/07
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLauto_upso_list(DOBJ dobj)
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
            dobj  = CALLauto_upso_list_SEL1(Conn, dobj);           //  자동이체업소현황
            dobj  = CALLauto_upso_list_SEL2(Conn, dobj);           //  담당자별 업소수
            dobj  = CALLauto_upso_list_SEL3(Conn, dobj);           //  그룹핑
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
    public DOBJ CTLauto_upso_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLauto_upso_list_SEL1(Conn, dobj);           //  자동이체업소현황
        dobj  = CALLauto_upso_list_SEL2(Conn, dobj);           //  담당자별 업소수
        dobj  = CALLauto_upso_list_SEL3(Conn, dobj);           //  그룹핑
        return dobj;
    }
    // 자동이체업소현황
    public DOBJ CALLauto_upso_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_upso_list_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_upso_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   TERM_YN = dobj.getRetObject("S").getRecord().get("TERM_YN");   //해지 여부
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT TA.APPTN_DAY INS_DATE , TB.UPSO_CD , TB.UPSO_NM , TB.MNGEMSTR_NM , TC.GRADNM , TB.UPSO_NEW_ADDR1 || DECODE(TB.UPSO_NEW_ADDR2, '', '', ', '||TB.UPSO_NEW_ADDR2) || TB.UPSO_REF_INFO ADDR , DECODE(TA.RECEPTION_GBN, '7', TA.CARD_NUM, TA.AUTO_ACCNNUM) AS AUTO_ACCNNUM , TA.RECEPTION_GBN , DECODE(TA.RECEPTION_GBN, '7', TA.CARD_GBN, TA.BANK_CD) AS BANK_CD , DECODE(TA.RECEPTION_GBN, '7', DECODE(CARD_GBN, 'WIN', '삼성카드','LGC', '신한카드', CARD_GBN) , (SELECT DISTINCT BANK_NM  ";
        query +=" FROM ACCT.TCAC_BANK_7  ";
        query +=" WHERE SUBSTR(BANK_CD,1,3) = SUBSTR(TA.BANK_CD,1,3))) BANK_NM , DECODE(TERM_YN, 'Y', DECODE(TA.TERM_YRMN,NULL,TA.APPTN_DAY||'*', TA.TERM_YRMN||' '), TA.APPTN_DAY||' ') APPTN_DAY , TA.REMAK , TC.MONPRNCFEE , DECODE(TERM_YN, 'Y', '해지',DECODE(TB.CLSBS_YRMN, NULL, '관리중', '폐업(미해지)')) TERM , TB.STAFF_CD , (  ";
        query +=" SELECT HAN_NM  ";
        query +=" FROM INSA.TINS_MST01  ";
        query +=" WHERE STAFF_NUM = TB.STAFF_CD ) STAFF_NM  ";
        query +=" FROM GIBU.TBRA_UPSO_AUTO TA , GIBU.TBRA_UPSO TB , (  ";
        query +=" SELECT ZA.UPSO_CD, ZB.MONPRNCFEE, ZC.GRADNM FROM(  ";
        query +=" SELECT A.UPSO_CD, MAX(A.JOIN_SEQ) JOIN_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE B.BRAN_CD = :BRAN_CD  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD  ";
        query +=" GROUP BY A.UPSO_CD ) ZA , GIBU.TBRA_UPSORTAL_INFO ZB , GIBU.TBRA_BSTYPGRAD ZC  ";
        query +=" WHERE ZB.JOIN_SEQ = ZA.JOIN_SEQ  ";
        query +=" AND ZB.UPSO_CD = ZA.UPSO_CD  ";
        query +=" AND ZC.BSTYP_CD = ZB.BSTYP_CD  ";
        query +=" AND ZC.GRAD_GBN = ZB.UPSO_GRAD ) TC  ";
        query +=" WHERE TB.BRAN_CD = :BRAN_CD  ";
        query +=" AND TA.UPSO_CD = TB.UPSO_CD  ";
        query +=" AND TA.APPTN_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" AND TC.UPSO_CD = TB.UPSO_CD  ";
        if("Y".equals(TERM_YN))
        {
            query +=" AND TA.TERM_YN = :TERM_YN  ";
        }
        if("N".equals(TERM_YN))
        {
            query +=" AND TA.TERM_YN = :TERM_YN  ";
            query +=" AND TB.CLSBS_YRMN IS NULL  ";
        }
        if("C".equals(TERM_YN))
        {
            query +=" AND TA.TERM_YN = 'N'  ";
            query +=" AND TB.CLSBS_YRMN IS NOT NULL  ";
        }
        query +=" ORDER BY TA.INS_DATE  ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("TERM_YN", TERM_YN);               //해지 여부
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 담당자별 업소수
    public DOBJ CALLauto_upso_list_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_upso_list_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_upso_list_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   TERM_YN = dobj.getRetObject("S").getRecord().get("TERM_YN");   //해지 여부
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT (  ";
        query +=" SELECT HAN_NM  ";
        query +=" FROM INSA.TINS_MST01  ";
        query +=" WHERE STAFF_NUM = TB.STAFF_CD ) STAFF_NM, COUNT(*) AS CNT_STAFF  ";
        query +=" FROM GIBU.TBRA_UPSO_AUTO TA , GIBU.TBRA_UPSO TB  ";
        query +=" WHERE TB.BRAN_CD = :BRAN_CD  ";
        query +=" AND TA.UPSO_CD = TB.UPSO_CD  ";
        query +=" AND TA.APPTN_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        if("Y".equals(TERM_YN))
        {
            query +=" AND TA.TERM_YN = :TERM_YN  ";
        }
        if("N".equals(TERM_YN))
        {
            query +=" AND TA.TERM_YN = :TERM_YN  ";
            query +=" AND TB.CLSBS_YRMN IS NULL  ";
        }
        if("C".equals(TERM_YN))
        {
            query +=" AND TA.TERM_YN = 'N'  ";
            query +=" AND TB.CLSBS_YRMN IS NOT NULL  ";
        }
        query +=" GROUP BY TB.STAFF_CD  ";
        query +=" ORDER BY STAFF_NM  ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("TERM_YN", TERM_YN);               //해지 여부
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    // 그룹핑
    public DOBJ CALLauto_upso_list_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLauto_upso_list_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLauto_upso_list_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("START_DAY");   //시작일
        String   TERM_YN = dobj.getRetObject("S").getRecord().get("TERM_YN");   //해지 여부
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT DECODE(TA.RECEPTION_GBN,'4','계좌은행', '5','계좌협회', '6','계좌금결원', '7','카드') RECEPTION_GBN, COUNT(*) as CNT, SUM(TC.MONPRNCFEE) as MONPRNCFEE  ";
        query +=" FROM GIBU.TBRA_UPSO_AUTO TA , GIBU.TBRA_UPSO TB , (  ";
        query +=" SELECT ZA.UPSO_CD, ZB.MONPRNCFEE, ZC.GRADNM FROM(  ";
        query +=" SELECT A.UPSO_CD, MAX(A.JOIN_SEQ) JOIN_SEQ  ";
        query +=" FROM GIBU.TBRA_UPSORTAL_INFO A , GIBU.TBRA_UPSO B  ";
        query +=" WHERE B.BRAN_CD = :BRAN_CD  ";
        query +=" AND A.UPSO_CD = B.UPSO_CD  ";
        query +=" GROUP BY A.UPSO_CD ) ZA , GIBU.TBRA_UPSORTAL_INFO ZB , GIBU.TBRA_BSTYPGRAD ZC  ";
        query +=" WHERE ZB.JOIN_SEQ = ZA.JOIN_SEQ  ";
        query +=" AND ZB.UPSO_CD = ZA.UPSO_CD  ";
        query +=" AND ZC.BSTYP_CD = ZB.BSTYP_CD  ";
        query +=" AND ZC.GRAD_GBN = ZB.UPSO_GRAD ) TC  ";
        query +=" WHERE TB.BRAN_CD = :BRAN_CD  ";
        query +=" AND TA.UPSO_CD = TB.UPSO_CD  ";
        query +=" AND TA.APPTN_DAY BETWEEN :START_DAY  ";
        query +=" AND :END_DAY  ";
        query +=" AND TC.UPSO_CD = TB.UPSO_CD  ";
        if("Y".equals(TERM_YN))
        {
            query +=" AND TA.TERM_YN = :TERM_YN  ";
        }
        if("N".equals(TERM_YN))
        {
            query +=" AND TA.TERM_YN = :TERM_YN  ";
            query +=" AND TB.CLSBS_YRMN IS NULL  ";
        }
        if("C".equals(TERM_YN))
        {
            query +=" AND TA.TERM_YN = 'N'  ";
            query +=" AND TB.CLSBS_YRMN IS NOT NULL  ";
        }
        query +=" GROUP BY TA.RECEPTION_GBN  ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("TERM_YN", TERM_YN);               //해지 여부
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$auto_upso_list
    //##**$$end
}
