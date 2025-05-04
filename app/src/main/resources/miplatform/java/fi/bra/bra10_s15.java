
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_s15
{
    public bra10_s15()
    {
    }
    //##**$$contr_match_upload
    /*
    */
    public DOBJ CTLcontr_match_upload(DOBJ dobj)
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
            dobj  = CALLcontr_match_upload_SEL1(Conn, dobj);           //  업로드자료조회
            dobj  = CALLcontr_match_upload_MPD3(Conn, dobj);           //  분기
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLcontr_match_upload_XIUD10(Conn, dobj);           //  TEMP테이블삭제
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
    public DOBJ CTLcontr_match_upload( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcontr_match_upload_SEL1(Conn, dobj);           //  업로드자료조회
        dobj  = CALLcontr_match_upload_MPD3(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLcontr_match_upload_XIUD10(Conn, dobj);           //  TEMP테이블삭제
        return dobj;
    }
    // 업로드자료조회
    public DOBJ CALLcontr_match_upload_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcontr_match_upload_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_match_upload_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BSCON_CD  ,  BSCON_UPSO_CD  ,  BSCON_UPSO_NM  ,  APPL_DAY  ,  UPSO_CD  ,  UPSO_ZIP  ,  UPSO_ADDR1  ,  UPSO_ADDR2  ,  MONPRNCFEE  ,  INSPRES_ID  ,  INS_DATE  ,  FILE_NM  ,  BSTYP_CD  ,  ATAX_YN  FROM  GIBU.TBRA_BSCON_CONTRINFO_TEMP ";
        sobj.setSql(query);
        return sobj;
    }
    // 분기
    public DOBJ CALLcontr_match_upload_MPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD3");
        VOBJ dvobj = dobj.getRetObject("SEL1");         //업로드자료조회에서 생성시킨 OBJECT입니다.(CALLcontr_match_upload_SEL1)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLcontr_match_upload_SEL4(Conn, dobj);           //  기존등록여부조회
                if( dobj.getRetObject("SEL4").getRecord().getDouble("CNT") > 0)
                {
                    dobj  = CALLcontr_match_upload_XIUD11(Conn, dobj);           //  기존계약정보 수
                    dobj  = CALLcontr_match_upload_SEL8(Conn, dobj);           //  순번채번
                    dobj  = CALLcontr_match_upload_INS9(Conn, dobj);           //  계약정보입력
                }
                else
                {
                    dobj  = CALLcontr_match_upload_SEL8(Conn, dobj);           //  순번채번
                    dobj  = CALLcontr_match_upload_INS9(Conn, dobj);           //  계약정보입력
                }
            }
        }
        return dobj;
    }
    // 기존등록여부조회
    public DOBJ CALLcontr_match_upload_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcontr_match_upload_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_match_upload_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //타단체업소코드
        String   UPSO_ZIP = dobj.getRetObject("R").getRecord().get("UPSO_ZIP");   //업소 우편번호
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(1)  AS  CNT  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  BSCON_UPSO_CD  =  :BSCON_UPSO_CD   \n";
        query +=" AND  UPSO_ZIP  =  :UPSO_ZIP ";
        sobj.setSql(query);
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //타단체업소코드
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //업소 우편번호
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    // 기존계약정보 수
    // 마지막값 사용구분 N으로 변경
    public DOBJ CALLcontr_match_upload_XIUD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD11");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcontr_match_upload_XIUD11(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_match_upload_XIUD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //거래처 코드
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //타단체업소코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   UPSO_ZIP = dobj.getRetObject("R").getRecord().get("UPSO_ZIP");   //업소 우편번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_BSCON_CONTRINFO  \n";
        query +=" SET MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE , USE_YN = 'N'  \n";
        query +=" WHERE BSCON_CD = :BSCON_CD  \n";
        query +=" AND BSCON_UPSO_CD = :BSCON_UPSO_CD  \n";
        query +=" AND UPSO_ZIP = :UPSO_ZIP  \n";
        query +=" AND USE_YN = 'Y'";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //타단체업소코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //업소 우편번호
        return sobj;
    }
    // 순번채번
    public DOBJ CALLcontr_match_upload_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcontr_match_upload_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_match_upload_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BSCON_UPSO_CD = dobj.getRetObject("R").getRecord().get("BSCON_UPSO_CD");   //타단체업소코드
        String   UPSO_ZIP = dobj.getRetObject("R").getRecord().get("UPSO_ZIP");   //업소 우편번호
        String   BSCON_CD = dobj.getRetObject("R").getRecord().get("BSCON_CD");   //거래처 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),  0)  +  1  AS  SEQ  FROM  GIBU.TBRA_BSCON_CONTRINFO  WHERE  BSCON_CD  =  :BSCON_CD   \n";
        query +=" AND  BSCON_UPSO_CD  =  :BSCON_UPSO_CD   \n";
        query +=" AND  UPSO_ZIP  =  :UPSO_ZIP ";
        sobj.setSql(query);
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //타단체업소코드
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //업소 우편번호
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        return sobj;
    }
    // 계약정보입력
    public DOBJ CALLcontr_match_upload_INS9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcontr_match_upload_INS9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_match_upload_INS9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   UPSO_ZIP = dvobj.getRecord().get("UPSO_ZIP");   //업소 우편번호
        String   UPSO_ADDR1 = dvobj.getRecord().get("UPSO_ADDR1");   //업소 주소1
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   BSCON_UPSO_NM = dvobj.getRecord().get("BSCON_UPSO_NM");   //타단체업소명
        String   BSTYP_CD = dvobj.getRecord().get("BSTYP_CD");   //업종 코드
        double   MONPRNCFEE = dvobj.getRecord().getDouble("MONPRNCFEE");   //월정료
        String   BSCON_UPSO_CD = dvobj.getRecord().get("BSCON_UPSO_CD");   //타단체업소코드
        String   ATAX_YN = dvobj.getRecord().get("ATAX_YN");   //부가세 여부
        String   UPSO_ADDR2 = dvobj.getRecord().get("UPSO_ADDR2");   //업소 주소2
        String   APPL_DAY = dvobj.getRecord().get("APPL_DAY");   //적용 일자
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   MATCH_GBN ="N";   //매칭 구분
        double   SEQ = dobj.getRetObject("SEL8").getRecord().getDouble("SEQ");   //관리번호
        String   USE_YN ="Y";   //사용구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BSCON_CONTRINFO (USE_YN, APPL_DAY, INSPRES_ID, UPSO_ADDR2, ATAX_YN, BSCON_UPSO_CD, MONPRNCFEE, BSTYP_CD, BSCON_UPSO_NM, SEQ, BSCON_CD, INS_DATE, UPSO_ADDR1, MATCH_GBN, UPSO_ZIP)  \n";
        query +=" values(:USE_YN , :APPL_DAY , :INSPRES_ID , :UPSO_ADDR2 , :ATAX_YN , :BSCON_UPSO_CD , :MONPRNCFEE , :BSTYP_CD , :BSCON_UPSO_NM , :SEQ , :BSCON_CD , SYSDATE, :UPSO_ADDR1 , :MATCH_GBN , :UPSO_ZIP )";
        sobj.setSql(query);
        sobj.setString("UPSO_ZIP", UPSO_ZIP);               //업소 우편번호
        sobj.setString("UPSO_ADDR1", UPSO_ADDR1);               //업소 주소1
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("BSCON_UPSO_NM", BSCON_UPSO_NM);               //타단체업소명
        sobj.setString("BSTYP_CD", BSTYP_CD);               //업종 코드
        sobj.setDouble("MONPRNCFEE", MONPRNCFEE);               //월정료
        sobj.setString("BSCON_UPSO_CD", BSCON_UPSO_CD);               //타단체업소코드
        sobj.setString("ATAX_YN", ATAX_YN);               //부가세 여부
        sobj.setString("UPSO_ADDR2", UPSO_ADDR2);               //업소 주소2
        sobj.setString("APPL_DAY", APPL_DAY);               //적용 일자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("MATCH_GBN", MATCH_GBN);               //매칭 구분
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("USE_YN", USE_YN);               //사용구분
        return sobj;
    }
    // TEMP테이블삭제
    public DOBJ CALLcontr_match_upload_XIUD10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD10");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcontr_match_upload_XIUD10(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcontr_match_upload_XIUD10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" TRUNCATE  TABLE  GIBU.TBRA_BSCON_CONTRINFO_TEMP ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$contr_match_upload
    //##**$$end
}
