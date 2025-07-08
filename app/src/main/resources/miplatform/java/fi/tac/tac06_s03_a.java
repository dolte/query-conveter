
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac06_s03_a
{
    public tac06_s03_a()
    {
    }
    //##**$$EventID24
    /*
    */
    public DOBJ CTLEventID24(DOBJ dobj)
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = null;
        try
        {
            Connector connection = new Connector();
            Conn = connection.getConnection("PFDB",dobj);
            Conn.setAutoCommit(false);
        }
        catch(Exception e)
        {
            dobj.setRetmsg(e,"DataBase Connection Error");
            e.printStackTrace();
            return dobj;
        }
        try
        {
            dobj  = CALLEventID24_SEL1(Conn, dobj);           //  SEL
            dobj  = CALLEventID24_MPD3(Conn, dobj);           //  분기
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            Conn.commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            try
            {
                dobj.setRtncode(-1);
                dobj.setRetmsg(e.getMessage());
                Conn.rollback();
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
    public DOBJ CTLEventID24( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLEventID24_SEL1(Conn, dobj);           //  SEL
        dobj  = CALLEventID24_MPD3(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // SEL
    public DOBJ CALLEventID24_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLEventID24_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID24_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   A_TO = dobj.getRetObject("S").getRecord().get("A_TO");   //종료일자
        String   A_FROM = dobj.getRetObject("S").getRecord().get("A_FROM");   //시작일자
        String   STAT_GBN = dvobj.getRecord().get("STAT_GBN");   //처리상태
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT B.MB_CD , B.HANMB_NM , BANKCD , ACCNNUM , ACCNNUM AS ACCNNUM_1 , INSDATE , A.APPTNLETTER_MNG_NUM , NVL(STAT_GBN,'1') STAT_GBN , C.FILE_ROUT , C.FILE_NM , C.FILE_TEMPNM , C.SEQ , NVL(LENGTH(C.FILES),0) AS FILE_LENGTH  ";
        query +=" FROM HOMP.THOM_ACCNCHGAPPTNLETTER A , FIDU.TMEM_MB B , HOMP.THOM_ACCNCHG_FILE C  ";
        query +=" WHERE A.MB_ID = B.MB_ID  ";
        query +=" AND A.APPTNLETTER_MNG_NUM = C.APPTNLETTER_MNG_NUM(+)  ";
        query +=" AND TO_CHAR(INSDATE,'YYYYMM') >= :A_FROM  ";
        query +=" AND TO_CHAR(INSDATE,'YYYYMM') <= :A_TO  ";
        query +=" AND NVL(A.STAT_GBN,'1') LIKE :STAT_GBN  ";
        query +=" AND NVL(A.STAT_GBN,'1') <> '9'  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND B.MB_CD = :MB_CD  ";
        }
        query +=" ORDER BY B.MB_CD, A.INSDATE DESC  ";
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        sobj.setString("A_TO", A_TO);               //종료일자
        sobj.setString("A_FROM", A_FROM);               //시작일자
        sobj.setString("STAT_GBN", STAT_GBN);               //처리상태
        return sobj;
    }
    // 분기
    public DOBJ CALLEventID24_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("SEL1");         //SEL에서 생성시킨 OBJECT입니다.(CALLEventID24_SEL1)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( 1 == 2)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLEventID24_SEL7(Conn, dobj);           //  첨부파일조회
            }
        }
        return dobj;
    }
    // 첨부파일조회
    public DOBJ CALLEventID24_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLEventID24_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        String fullname="";
        rvobj.first();
        while(rvobj.next())
        {
            wutil.makeFileOverwirte( dobj.getRetObject("R").getRecord().get("FILE_ROUT"), dobj.getRetObject("R").getRecord().get("FILE_TEMPNM"),rvobj.getRecord().getBytes("FILES"));
        }
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLEventID24_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   APPTNLETTER_MNG_NUM = dobj.getRetObject("R").getRecord().getDouble("APPTNLETTER_MNG_NUM");   //신청서 관리번호
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  FILES  FROM  HOMP.THOM_ACCNCHG_FILE  WHERE  APPTNLETTER_MNG_NUM  =  :APPTNLETTER_MNG_NUM   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setDouble("APPTNLETTER_MNG_NUM", APPTNLETTER_MNG_NUM);               //신청서 관리번호
        sobj.setDouble("SEQ", SEQ);               //관리번호
        return sobj;
    }
    //##**$$EventID24
    //##**$$end
}
