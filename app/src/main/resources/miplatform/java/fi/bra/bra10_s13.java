
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s13
{
    public bra10_s13()
    {
    }
    //##**$$chk_demd_upload
    /*
    */
    public DOBJ CTLchk_demd_upload(DOBJ dobj)
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
            dobj  = CALLchk_demd_upload_SEL1(Conn, dobj);           //  해당업소의청구자료유무확인
            dobj  = CALLchk_demd_upload_SEL2(Conn, dobj);           //  입금정보유무확인
            dobj  = CALLchk_demd_upload_XIUD4(Conn, dobj);           //  임시테이블삭제
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
    public DOBJ CTLchk_demd_upload( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLchk_demd_upload_SEL1(Conn, dobj);           //  해당업소의청구자료유무확인
        dobj  = CALLchk_demd_upload_SEL2(Conn, dobj);           //  입금정보유무확인
        dobj  = CALLchk_demd_upload_XIUD4(Conn, dobj);           //  임시테이블삭제
        return dobj;
    }
    // 해당업소의청구자료유무확인
    public DOBJ CALLchk_demd_upload_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLchk_demd_upload_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_demd_upload_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_BSCON_DEMD_UPLOAD  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  DEMD_YRMN  =  :DEMD_YRMN ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    // 입금정보유무확인
    public DOBJ CALLchk_demd_upload_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLchk_demd_upload_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_demd_upload_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.DEMD_YRMN  ,  A.BSCON_CD  ,  B.BSCON_UPSO_CD  ,  A.UPSO_CD  ,  A.REPT_YRMN  ,  A.PROC_AMT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  A  ,  GIBU.TBRA_BSCON_CONTRINFO  B  WHERE  A.BSCON_CD  =  B.BSCON_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  B.USE_YN  =  'Y'   \n";
        query +=" AND  A.CERT_GBN  <>  '36'   \n";
        query +=" AND  A.REPT_YRMN  IS  NOT  NULL  MINUS   \n";
        query +=" SELECT  A.DEMD_YRMN  ,  A.BSCON_CD  ,  B.BSCON_UPSO_CD  ,  A.UPSO_CD  ,  A.REPT_YRMN  ,  A.PROC_AMT  FROM  GIBU.TBRA_DEMD_REPT_BSCON  A  ,  GIBU.TBRA_BSCON_CONTRINFO  B  ,  GIBU.TBRA_BSCON_RETURN_NOTE  C  WHERE  A.BSCON_CD  =  B.BSCON_CD   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  A.DEMD_YRMN  =  :DEMD_YRMN   \n";
        query +=" AND  B.USE_YN  =  'Y'   \n";
        query +=" AND  A.REPT_YRMN  IS  NOT  NULL   \n";
        query +=" AND  A.BSCON_CD  =  C.BSCON_CD   \n";
        query +=" AND  A.UPSO_CD  =  C.UPSO_CD   \n";
        query +=" AND  A.DEMD_YRMN  =  C.DEMD_YRMN   \n";
        query +=" AND  A.SEQ  =  C.SEQ ";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    // 임시테이블삭제
    public DOBJ CALLchk_demd_upload_XIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD4");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLchk_demd_upload_XIUD4(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLchk_demd_upload_XIUD4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" TRUNCATE  TABLE  GIBU.TBRA_BSCON_DEMD_UPLOAD_TEMP ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$chk_demd_upload
    //##**$$del_bscon_demd
    /*
    */
    public DOBJ CTLdel_bscon_demd(DOBJ dobj)
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
            dobj  = CALLdel_bscon_demd_DEL9(Conn, dobj);           //  청구자료삭제
            if( 1 == 2)
            {
                dobj  = CALLdel_bscon_demd_XIUD3(Conn, dobj);           //  청구입금내역삭제
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
    public DOBJ CTLdel_bscon_demd( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdel_bscon_demd_DEL9(Conn, dobj);           //  청구자료삭제
        if( 1 == 2)
        {
            dobj  = CALLdel_bscon_demd_XIUD3(Conn, dobj);           //  청구입금내역삭제
        }
        return dobj;
    }
    // 청구자료삭제
    public DOBJ CALLdel_bscon_demd_DEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdel_bscon_demd_DEL9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdel_bscon_demd_DEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   DEMD_YRMN = dvobj.getRecord().get("DEMD_YRMN");   //청구 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BSCON_DEMD_UPLOAD  \n";
        query +=" where DEMD_YRMN=:DEMD_YRMN  \n";
        query +=" and BSCON_CD=:BSCON_CD";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        return sobj;
    }
    // 청구입금내역삭제
    public DOBJ CALLdel_bscon_demd_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdel_bscon_demd_XIUD3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdel_bscon_demd_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("S").getRecord().get("BSCON_CD");   //거래처 코드
        String   DEMD_YRMN = dobj.getRetObject("S").getRecord().get("DEMD_YRMN");   //청구 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_DEMD_REPT_BSCON  \n";
        query +=" WHERE DEMD_YRMN = :DEMD_YRMN  \n";
        query +=" AND BSCON_CD = :BSCON_CD  \n";
        query +=" AND BSTYP_CD NOT IN ('k', 'l', 'o')  \n";
        query +=" AND REPT_YRMN IS NULL";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        return sobj;
    }
    //##**$$del_bscon_demd
    //##**$$demd_match_upload
    /*
    */
    public DOBJ CTLdemd_match_upload(DOBJ dobj)
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
            dobj  = CALLdemd_match_upload_XIUD1(Conn, dobj);           //  업소코드매칭
            dobj  = CALLdemd_match_upload_XIUD3(Conn, dobj);           //  업소매칭자료등록
            dobj  = CALLdemd_match_upload_XIUD4(Conn, dobj);           //  TEMP테이블 삭제
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
    public DOBJ CTLdemd_match_upload( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLdemd_match_upload_XIUD1(Conn, dobj);           //  업소코드매칭
        dobj  = CALLdemd_match_upload_XIUD3(Conn, dobj);           //  업소매칭자료등록
        dobj  = CALLdemd_match_upload_XIUD4(Conn, dobj);           //  TEMP테이블 삭제
        return dobj;
    }
    // 업소코드매칭
    public DOBJ CALLdemd_match_upload_XIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdemd_match_upload_XIUD1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD1");
        rvobj.Println("XIUD1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdemd_match_upload_XIUD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YRMN = dvobj.getRecord().get("DEMD_YRMN");   //청구 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_BSCON_DEMD_UPLOAD_TEMP A  \n";
        query +=" SET UPSO_CD = (SELECT UPSO_CD FROM GIBU.TBRA_BSCON_CONTRINFO B  \n";
        query +=" WHERE BSCON_CD = A.BSCON_CD  \n";
        query +=" AND BSCON_UPSO_CD = A.BSCON_UPSO_CD  \n";
        query +=" AND UPSO_CD IS NOT NULL  \n";
        query +=" AND APPL_DAY < :DEMD_YRMN || '32'  \n";
        query +=" AND SEQ = (SELECT MAX(SEQ) FROM GIBU.TBRA_BSCON_CONTRINFO  \n";
        query +=" WHERE BSCON_CD = A.BSCON_CD  \n";
        query +=" AND BSCON_UPSO_CD = A.BSCON_UPSO_CD  \n";
        query +=" AND APPL_DAY < :DEMD_YRMN || '32'))";
        sobj.setSql(query);
        sobj.setString("DEMD_YRMN", DEMD_YRMN);               //청구 년월
        return sobj;
    }
    // 업소매칭자료등록
    public DOBJ CALLdemd_match_upload_XIUD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD3");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD3");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdemd_match_upload_XIUD3(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeSqlUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD3");
        rvobj.Println("XIUD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdemd_match_upload_XIUD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" INSERT INTO GIBU.TBRA_BSCON_DEMD_UPLOAD(DEMD_YRMN , BSCON_CD , BSCON_UPSO_CD , BSCON_UPSO_NM , UPSO_CD , UPSO_ZIP , UPSO_ADDR1 , UPSO_ADDR2 , DEMD_AMT , USE_AMT , ADDT_AMT , EADDT_AMT , ATAX_AMT , MATCH_GBN , INSPRES_ID , INS_DATE) SELECT DEMD_YRMN , BSCON_CD , BSCON_UPSO_CD , BSCON_UPSO_NM , UPSO_CD, UPSO_ZIP , UPSO_ADDR1 , UPSO_ADDR2 , DEMD_AMT , USE_AMT , ADDT_AMT , EADDT_AMT , ATAX_AMT , DECODE(UPSO_CD, NULL, 'N', 'Y') AS MATCH_GBN , INSPRES_ID , INS_DATE FROM GIBU.TBRA_BSCON_DEMD_UPLOAD_TEMP";
        sobj.setSql(query);
        return sobj;
    }
    // TEMP테이블 삭제
    public DOBJ CALLdemd_match_upload_XIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD4");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLdemd_match_upload_XIUD4(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLdemd_match_upload_XIUD4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" TRUNCATE  TABLE  GIBU.TBRA_BSCON_DEMD_UPLOAD_TEMP ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$demd_match_upload
    //##**$$end
}
