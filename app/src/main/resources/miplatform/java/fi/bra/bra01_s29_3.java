
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s29_3
{
    public bra01_s29_3()
    {
    }
    //##**$$select_letter
    /*
    */
    public DOBJ CTLselect_letter(DOBJ dobj)
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
            dobj  = CALLselect_letter_SEL1(Conn, dobj);           //  목록조회
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
    public DOBJ CTLselect_letter( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLselect_letter_SEL1(Conn, dobj);           //  목록조회
        return dobj;
    }
    // 목록조회
    public DOBJ CALLselect_letter_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLselect_letter_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLselect_letter_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   OPT_LIST = dobj.getRetObject("S").getRecord().get("OPT_LIST");   //목록 조건
        String   TO = dobj.getRetObject("S").getRecord().get("TO");   //종료조건
        String   UPSO_NM = dobj.getRetObject("S").getRecord().get("UPSO_NM");   //업소 명
        String   STAT_GBN = dobj.getRetObject("S").getRecord().get("STAT_GBN");   //처리상태
        String   REPPRES = dobj.getRetObject("S").getRecord().get("REPPRES");   //대표자
        String   FROM = dobj.getRetObject("S").getRecord().get("FROM");   //시작조건
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT LETTER_NUM, POST_NUM, ADDR || DECODE(ADDR_DETED, '', '', ', ' || ADDR_DETED) AS ADDR, UPSO_NM, BSTYP, REPPRES, INS_NUM, BIOWN_NUM, AREA1, PHON_NUM, EMAIL_ADDR, TO_CHAR(INS_DATE, 'YYYY-MM-DD') AS INS_DATE, TO_CHAR(COMPL_DAY,'YYYY-MM-DD') AS COMPL_DAY, STAT_GBN, BRAN_CD, REMAK  ";
        query +=" FROM HOMP.THOM_UPSO_LETTER  ";
        query +=" WHERE 1=1  ";
        query +=" AND TO_CHAR(INS_DATE,'YYYYMMDD') BETWEEN :FROM  ";
        query +=" AND :TO  ";
        if( !UPSO_NM.equals("") )
        {
            query +=" AND UPSO_NM LIKE '%'|| :UPSO_NM ||'%'  ";
        }
        if( !REPPRES.equals("") )
        {
            query +=" AND REPPRES LIKE '%'|| :REPPRES ||'%'  ";
        }
        if( !STAT_GBN.equals("") )
        {
            query +=" AND STAT_GBN LIKE :STAT_GBN || '%'  ";
        }
        if( !OPT_LIST.equals("") )
        {
            query +=" AND BRAN_CD LIKE :OPT_LIST || '%'  ";
        }
        query +=" ORDER BY LETTER_NUM DESC  ";
        sobj.setSql(query);
        if(!OPT_LIST.equals(""))
        {
            sobj.setString("OPT_LIST", OPT_LIST);               //목록 조건
        }
        sobj.setString("TO", TO);               //종료조건
        if(!UPSO_NM.equals(""))
        {
            sobj.setString("UPSO_NM", UPSO_NM);               //업소 명
        }
        if(!STAT_GBN.equals(""))
        {
            sobj.setString("STAT_GBN", STAT_GBN);               //처리상태
        }
        if(!REPPRES.equals(""))
        {
            sobj.setString("REPPRES", REPPRES);               //대표자
        }
        sobj.setString("FROM", FROM);               //시작조건
        return sobj;
    }
    //##**$$select_letter
    //##**$$end
}
