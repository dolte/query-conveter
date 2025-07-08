
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac04_s03
{
    public tac04_s03()
    {
    }
    //##**$$saveTac04
    /* * 프로그램명 : tac04_s03
    * 작성자 : 이세준
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsaveTac03(DOBJ dobj)
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
            dobj  = CALLsaveTac04_DEL2(Conn, dobj);           //  회원재분배삭제
            dobj  = CALLsaveTac04_INS1(Conn, dobj);           //  회원재분배파일업로드
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
    public DOBJ CTLsaveTac03( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsaveTac04_DEL2(Conn, dobj);           //  회원재분배삭제
        dobj  = CALLsaveTac04_INS1(Conn, dobj);           //  회원재분배파일업로드
        return dobj;
    }
    // 회원재분배삭제
    public DOBJ CALLsaveTac04_DEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveTac04_DEL2(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveTac04_DEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REDEM_YRMN = dvobj.getRecord().get("REDEM_YRMN");
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TTAC_REDEMREDISTR_UPLOAD  \n";
        query +=" where REDEM_YRMN=:REDEM_YRMN";
        sobj.setSql(query);
        sobj.setString("REDEM_YRMN", REDEM_YRMN);
        return sobj;
    }
    // 회원재분배파일업로드
    public DOBJ CALLsaveTac04_INS1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLsaveTac04_INS1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsaveTac04_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        double   REDEM_AMT = dvobj.getRecord().getDouble("REDEM_AMT");   //환수 금액
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   REDEM_MB_CD = dvobj.getRecord().get("REDEM_MB_CD");   //환수회원코드
        String   TRNSF_GBN = dvobj.getRecord().get("TRNSF_GBN");   //양도 구분
        String   REDEM_YRMN = dvobj.getRecord().get("REDEM_YRMN");
        String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_REDEMREDISTR_UPLOAD (REDEM_YRMN, INS_DATE, INSPRES_ID, TRNSF_GBN, REDEM_MB_CD, REMAK, REDEM_AMT)  \n";
        query +=" values(:REDEM_YRMN , TO_CHAR(SYSDATE,'YYYYMMDD'), :INSPRES_ID , :TRNSF_GBN , :REDEM_MB_CD , :REMAK , :REDEM_AMT )";
        sobj.setSql(query);
        sobj.setDouble("REDEM_AMT", REDEM_AMT);               //환수 금액
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("REDEM_MB_CD", REDEM_MB_CD);               //환수회원코드
        sobj.setString("TRNSF_GBN", TRNSF_GBN);               //양도 구분
        sobj.setString("REDEM_YRMN", REDEM_YRMN);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    //##**$$saveTac04
    //##**$$searchTac04
    /* * 프로그램명 : tac04_s03
    * 작성자 : 이세준
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLsearchTac04(DOBJ dobj)
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
            dobj  = CALLsearchTac04_SEL1(Conn, dobj);           //  회원재분배조회
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
    public DOBJ CTLsearchTac04( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLsearchTac04_SEL1(Conn, dobj);           //  회원재분배조회
        return dobj;
    }
    // 회원재분배조회
    public DOBJ CALLsearchTac04_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLsearchTac04_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLsearchTac04_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REDEM_YRMN = dobj.getRetObject("S").getRecord().get("REDEM_YRMN");   //REDEM_YRMN
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REDEM_MB_CD,  A.REDEM_YRMN,  A.TRNSF_GBN,  A.REDEM_AMT,  B.HANMB_NM,  A.REMAK  FROM  FIDU.TTAC_REDEMREDISTR_UPLOAD  A,  FIDU.TMEM_MB  B  WHERE  A.REDEM_MB_CD=B.MB_CD(+)   \n";
        query +=" AND  A.REDEM_MB_CD  LIKE  :MB_CD  ||  '%'   \n";
        query +=" AND  A.REDEM_YRMN  LIKE  :REDEM_YRMN||'%'  ORDER  BY  B.HANMB_NM  ASC ";
        sobj.setSql(query);
        sobj.setString("REDEM_YRMN", REDEM_YRMN);               //REDEM_YRMN
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    //##**$$searchTac04
    //##**$$end
}
