
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac14_s01
{
    public tac14_s01()
    {
    }
    //##**$$credit_report
    /*
    */
    public DOBJ CTLcredit_report(DOBJ dobj)
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
            dobj  = CALLcredit_report_SEL1(Conn, dobj);           //  회원채권정보
            dobj  = CALLcredit_report_SEL2(Conn, dobj);           //  회원계약정
            dobj  = CALLcredit_report_SEL3(Conn, dobj);           //  회원정보
            dobj  = CALLcredit_report_SEL4(Conn, dobj);           //  회원 확인서 출력 이력
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
    public DOBJ CTLcredit_report( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcredit_report_SEL1(Conn, dobj);           //  회원채권정보
        dobj  = CALLcredit_report_SEL2(Conn, dobj);           //  회원계약정
        dobj  = CALLcredit_report_SEL3(Conn, dobj);           //  회원정보
        dobj  = CALLcredit_report_SEL4(Conn, dobj);           //  회원 확인서 출력 이력
        return dobj;
    }
    // 회원채권정보
    // S.MB_CD
    public DOBJ CALLcredit_report_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcredit_report_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcredit_report_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  A.MNG_NUM,  A.CLAIMDEBT_GBN,  A.CLAIM_KND,  A.FIXGRD_CTENT,  A.CLAIMPRES_MB_CD,  A.CLAIMPRES_NM,  A.CLAIMPRES_BANK_CD,  A.CLAIMPRES_ACCN_NUM,  A.CLAIM_BST_AMT,  A.CLAIM_PTTNRATE,  A.CLAIM_RFND_AMT,  A.REPAYPROC_START_DAY,  A.REPAYPROC_END_DAY,  A.COMPL_YN,  A.REMAK,  A.CLAIMPRES_MEMO,  A.DEBTPRES_GBN,  A.SUPP_GBN,  A.REPAYPROC_START_YRMN,  A.REPAYPROC_END_YRMN  FROM  FIDU.TMEM_CLAIMDEBT  A  WHERE  A.MB_CD  =  :MB_CD  ORDER  BY  A.MNG_NUM ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    // 회원계약정
    public DOBJ CALLcredit_report_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcredit_report_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcredit_report_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TRNSF_MB_NM = dobj.getRetObject("S").getRecord().get("HANMB_NM");   //양도 회원 명
        String   TRNSF_MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //양도 회원 코드
        String   CHIT_GBN = dobj.getRetObject("S").getRecord().get("CHIT_GBN");   //전표 구분
        String   ONGCHG_CHIT_NUM = dobj.getRetObject("S").getRecord().get("ONGCHG_CHIT_NUM");   //지분변동 전표 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.ONGCHG_CHIT_NUM, FIDU.GET_CODE_NM( '00191' , A.CHIT_GBN ) as CHIT_GBN, A.TRNSF_MB_CD, FIDU.GET_MB_NM(A.TRNSF_MB_CD) as TRNSF_MB_NM, B.INTN_MB_CD, FIDU.GET_MB_NM(B.INTN_MB_CD) as INTN_MB_NM, A.INTNTRM_START_DAY|| '~' || A.INTNTRM_END_DAY as INTNTRM_TERM , B.TRNSFPERGT_ONGRATE, B.TRNSFRPDR_ONGRATE, A.INFU_ACQU_YN, A.CONTR_PER, DECODE(A.CONTROBJ_LOC_GBN,'I','국내','O','국외','A','전체') CONTROBJ_LOC_GBN , DECODE(C.CTRY,'ALL','전체',NULL,'-',C.CTRY||'개국') CTRY , DECODE(A.CHIT_CANC_GBN,'2','계약해제','3','중도해지') CHIT_CANC_GBN , (  ";
        query +=" SELECT COUNT(*)  ";
        query +=" FROM FIDU.TOPU_INTNPROD X, FIDU.TOPU_PROD Y  ";
        query +=" WHERE X.ONGCHG_CHIT_NUM = A.ONGCHG_CHIT_NUM  ";
        query +=" AND X.INTN_MB_CD =B.INTN_MB_CD  ";
        query +=" AND X.PROD_CD = Y.PROD_CD  ";
        query +=" AND Y.DELPRES_ID IS NULL ) AS PROD_CNT  ";
        query +=" FROM FIDU.TOPU_ONGCHG A, FIDU.TOPU_INTNMB B, (SELECT ONGCHG_CHIT_NUM, CTRY_CD AS CTRY  ";
        query +=" FROM FIDU.TOPU_CONTROBJ_LOC  ";
        query +=" WHERE CTRY_CD = 'ALL'  ";
        query +=" UNION ALL  ";
        query +=" SELECT ONGCHG_CHIT_NUM, TO_CHAR(COUNT(CTRY_CD)) AS CTRY  ";
        query +=" FROM FIDU.TOPU_CONTROBJ_LOC  ";
        query +=" WHERE CTRY_CD <> 'ALL'  ";
        query +=" GROUP BY ONGCHG_CHIT_NUM ) C  ";
        query +=" WHERE A.ONGCHG_CHIT_NUM = B.ONGCHG_CHIT_NUM(+)  ";
        query +=" AND A.ONGCHG_CHIT_NUM = C.ONGCHG_CHIT_NUM(+)  ";
        if( !ONGCHG_CHIT_NUM.equals("") )
        {
            query +=" AND A.ONGCHG_CHIT_NUM = :ONGCHG_CHIT_NUM  ";
        }
        if( !TRNSF_MB_CD.equals("") )
        {
            query +=" AND A.TRNSF_MB_CD = :TRNSF_MB_CD  ";
        }
        if( !TRNSF_MB_NM.equals("") )
        {
            query +=" AND A.TRNSF_MB_CD IN (  ";
            query +=" SELECT MB_CD  ";
            query +=" FROM FIDU.TMEM_MB  ";
            query +=" WHERE HANMB_NM LIKE '%'||:TRNSF_MB_NM ||'%' )  ";
        }
        if( !CHIT_GBN.equals("") )
        {
            query +=" AND A.CHIT_GBN LIKE :CHIT_GBN  ";
        }
        query +=" ORDER BY A.ONGCHG_CHIT_NUM DESC  ";
        sobj.setSql(query);
        if(!TRNSF_MB_NM.equals(""))
        {
            sobj.setString("TRNSF_MB_NM", TRNSF_MB_NM);               //양도 회원 명
        }
        if(!TRNSF_MB_CD.equals(""))
        {
            sobj.setString("TRNSF_MB_CD", TRNSF_MB_CD);               //양도 회원 코드
        }
        if(!CHIT_GBN.equals(""))
        {
            sobj.setString("CHIT_GBN", CHIT_GBN);               //전표 구분
        }
        if(!ONGCHG_CHIT_NUM.equals(""))
        {
            sobj.setString("ONGCHG_CHIT_NUM", ONGCHG_CHIT_NUM);               //지분변동 전표 번호
        }
        return sobj;
    }
    // 회원정보
    public DOBJ CALLcredit_report_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcredit_report_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcredit_report_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.MB_CD , A.HANMB_NM , B.ADDR||' '||ADDR_DETED AS ADDR , SUBSTR(A.INS_NUM,1,6)||'-'||SUBSTR(A.INS_NUM,7,1)||'******' AS INS_NUM  ";
        query +=" FROM FIDU.TMEM_MB A , FIDU.TMEM_ADBK B  ";
        query +=" WHERE A.MB_CD = B.MB_CD  ";
        query +=" AND B.ADDR_GBN = '2'  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND A.MB_CD = :MB_CD  ";
        }
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        return sobj;
    }
    // 회원 확인서 출력 이력
    public DOBJ CALLcredit_report_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcredit_report_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcredit_report_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT SEQ , MB_CD , OUTPUT_RS , SUBMIT_NM , AF_CHK , CS_CHK , INS_DATE , INSPRES_ID  ";
        query +=" FROM FIDU.TOPU_CERTIFICATE_HISTORY  ";
        query +=" WHERE 1=1  ";
        if( !MB_CD.equals("") )
        {
            query +=" AND MB_CD = :MB_CD  ";
        }
        sobj.setSql(query);
        if(!MB_CD.equals(""))
        {
            sobj.setString("MB_CD", MB_CD);               //회원 코드
        }
        return sobj;
    }
    //##**$$credit_report
    //##**$$credit_report_history
    /*
    */
    public DOBJ CTLcredit_report_history(DOBJ dobj)
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
            dobj  = CALLcredit_report_history_SEL1(Conn, dobj);           //  seq획득
            dobj  = CALLcredit_report_history_INS1(Conn, dobj);           //  확인서 출력 버튼 클릭
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
    public DOBJ CTLcredit_report_history( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcredit_report_history_SEL1(Conn, dobj);           //  seq획득
        dobj  = CALLcredit_report_history_INS1(Conn, dobj);           //  확인서 출력 버튼 클릭
        return dobj;
    }
    // seq획득
    public DOBJ CALLcredit_report_history_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcredit_report_history_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcredit_report_history_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SEQ),0)  +  1  AS  SEQ  FROM  FIDU.TOPU_CERTIFICATE_HISTORY ";
        sobj.setSql(query);
        return sobj;
    }
    // 확인서 출력 버튼 클릭
    // 확인서 출력 버튼 클릭시 이력 기록
    public DOBJ CALLcredit_report_history_INS1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("INS1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcredit_report_history_INS1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS1") ;
        rvobj.Println("INS1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcredit_report_history_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   CS_CHK = dvobj.getRecord().get("CS_CHK");
        String   SUBMIT_NM = dvobj.getRecord().get("SUBMIT_NM");
        String   AF_CHK = dvobj.getRecord().get("AF_CHK");
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        String   OUTPUT_RS = dvobj.getRecord().get("OUTPUT_RS");
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자ID
        double   SEQ = dobj.getRetObject("SEL1").getRecord().getDouble("SEQ");   //게시글 관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TOPU_CERTIFICATE_HISTORY (OUTPUT_RS, MB_CD, INS_DATE, AF_CHK, SUBMIT_NM, INSPRES_ID, SEQ, CS_CHK)  \n";
        query +=" values(:OUTPUT_RS , :MB_CD , SYSDATE, :AF_CHK , :SUBMIT_NM , :INSPRES_ID , :SEQ , :CS_CHK )";
        sobj.setSql(query);
        sobj.setString("CS_CHK", CS_CHK);
        sobj.setString("SUBMIT_NM", SUBMIT_NM);
        sobj.setString("AF_CHK", AF_CHK);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("OUTPUT_RS", OUTPUT_RS);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자ID
        sobj.setDouble("SEQ", SEQ);               //게시글 관리번호
        return sobj;
    }
    //##**$$credit_report_history
    //##**$$end
}
