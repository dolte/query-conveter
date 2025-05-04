
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s05
{
    public bra01_s05()
    {
    }
    //##**$$upso_adrs_select
    /* * 프로그램명 : bra01_s05
    * 작성자 : 이광노
    * 작성일 : 2009/11/26
    * 설명 : 업소클릭콜로 등록된 리스트를 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLupso_adrs_select(DOBJ dobj)
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
            dobj  = CALLupso_adrs_select_SEL1(Conn, dobj);           //  녹취 파일 정보
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
    public DOBJ CTLupso_adrs_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_adrs_select_SEL1(Conn, dobj);           //  녹취 파일 정보
        return dobj;
    }
    // 녹취 파일 정보
    // 녹취된 파일의 정보를 가져온다.
    public DOBJ CALLupso_adrs_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_adrs_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CONSORG_ID = dobj.getRetObject("S").getRecord().get("CONSORG_ID");   //상담원 ID
        String   START_DATE = dobj.getRetObject("S").getRecord().get("START_DATE");   //협회등록일
        String   END_DATE = dobj.getRetObject("S").getRecord().get("END_DATE");   //마감 일시
        String   MATCH_YN = dobj.getRetObject("S").getRecord().get("MATCH_YN");   //매칭 유무(Y/N/D)
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  XA.CONS_DATE  ,  XA.CONS_SEQ  ,  XA.UPSO_PHON_NUM  ,  XA.CONSORG_ID  ,  XA.BRAN_CD  ,  XB.DEPT_NM  BRAN_NM  ,  XA.UPSO_CD  ,  XC.UPSO_NM  ,  XA.FILE_ROUT  ,  XA.FILE_NM  ,  XA.MATCH_YN  ,  XA.IO_GBN  ,  XD.REMAK  ,  XD.BRE_REMAK  ,  '1'  CHK_IO  ,  XA.CONS_TM  ,  XA.FILE_SIZE  ,  XD.VISIT_SEQ  FROM  GIBU.TBRA_UPSO_ADRS_FILEINFO  XA  ,  INSA.TCPM_DEPT  XB  ,  GIBU.TBRA_UPSO  XC  ,  (   \n";
        query +=" SELECT  A.CONS_DATE  ,  A.CONS_SEQ  ,  A.REMAK  ,  B.REMAK  BRE_REMAK  ,  A.VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  GIBU.TBRA_UPSO_VISIT_BRE  B  WHERE  A.UPSO_CD  =  B.UPSO_CD(+)   \n";
        query +=" AND  A.VISIT_DAY  =  B.VISIT_DAY(+)   \n";
        query +=" AND  A.JOB_GBN  =  B.JOB_GBN(+)   \n";
        query +=" AND  A.VISIT_SEQ  =  B.VISIT_SEQ(+)   \n";
        query +=" AND  B.VISIT_NUM  =  1  )  XD  WHERE  XA.CONSORG_ID  =  :CONSORG_ID   \n";
        query +=" AND  XA.CONS_DATE  BETWEEN  :START_DATE   \n";
        query +=" AND  :END_DATE   \n";
        query +=" AND  XA.CONS_TM  NOT  IN  '000000'   \n";
        query +=" AND  XA.MATCH_YN  LIKE  :MATCH_YN   \n";
        query +=" AND  XC.UPSO_CD(+)  =  XA.UPSO_CD   \n";
        query +=" AND  XB.GIBU(+)  =  XA.BRAN_CD   \n";
        query +=" AND  XD.CONS_DATE(+)  =  XA.CONS_DATE   \n";
        query +=" AND  XD.CONS_SEQ(+)  =  XA.CONS_SEQ  ORDER  BY  XA.CONS_DATE  DESC,  XA.CONS_SEQ ";
        sobj.setSql(query);
        sobj.setString("CONSORG_ID", CONSORG_ID);               //상담원 ID
        sobj.setString("START_DATE", START_DATE);               //협회등록일
        sobj.setString("END_DATE", END_DATE);               //마감 일시
        sobj.setString("MATCH_YN", MATCH_YN);               //매칭 유무(Y/N/D)
        return sobj;
    }
    //##**$$upso_adrs_select
    //##**$$upso_adrs_save
    /* * 프로그램명 : bra01_s05
    * 작성자 : 이광노
    * 작성일 : 2009/11/26
    * 설명 : 업소클릭콜로 등록된 내용과 해당 업소의 매칭 정보를 관리한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLupso_adrs_save(DOBJ dobj)
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
            dobj  = CALLupso_adrs_save_MIUD1(Conn, dobj);           //  통화내역저장
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
    public DOBJ CTLupso_adrs_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_adrs_save_MIUD1(Conn, dobj);           //  통화내역저장
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 통화내역저장
    public DOBJ CALLupso_adrs_save_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_adrs_save_SEL10(Conn, dobj);           //  방문등록체크
                if(dobj.getRetObject("SEL10").getRecord().getDouble("CNT") == 0 && !dobj.getRetObject("R").getRecord().get("MATCH_YN").equals("D") && !dobj.getRetObject("R").getRecord().get("UPSO_CD").equals(""))
                {
                    dobj  = CALLupso_adrs_save_SEL11(Conn, dobj);           //  순번최대값구하기
                    dobj  = CALLupso_adrs_save_INS5(Conn, dobj);           //  업소방문등록
                    if(!dobj.getRetObject("R").getRecord().get("BRE_REMAK").equals(""))
                    {
                        dobj  = CALLupso_adrs_save_INS10(Conn, dobj);           //  녹취내용메모
                        dobj  = CALLupso_adrs_save_UPD6(Conn, dobj);           //  통화내역수정
                    }
                    else
                    {
                        dobj  = CALLupso_adrs_save_UPD6(Conn, dobj);           //  통화내역수정
                    }
                }
                else if(dobj.getRetObject("SEL10").getRecord().getDouble("CNT") > 0 && !dobj.getRetObject("R").getRecord().get("MATCH_YN").equals("D") && !dobj.getRetObject("R").getRecord().get("UPSO_CD").equals("") && !dobj.getRetObject("R").getRecord().get("BRE_REMAK").equals(""))
                {
                    dobj  = CALLupso_adrs_save_UPD12(Conn, dobj);           //  방문등록 메모 수정
                    dobj  = CALLupso_adrs_save_UPD6(Conn, dobj);           //  통화내역수정
                }
                else
                {
                    dobj  = CALLupso_adrs_save_UPD6(Conn, dobj);           //  통화내역수정
                }
            }
        }
        return dobj;
    }
    // 방문등록체크
    // 방문등록이 되어있는지 확인한다.
    public DOBJ CALLupso_adrs_save_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_adrs_save_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.Println("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_save_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CONS_DATE = dobj.getRetObject("R").getRecord().get("CONS_DATE");   //상담 일시
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  SUBSTR(:CONS_DATE  ,1,8)   \n";
        query +=" AND  VISIT_SEQ  =  :VISIT_SEQ   \n";
        query +=" AND  JOB_GBN  =  'R' ";
        sobj.setSql(query);
        sobj.setString("CONS_DATE", CONS_DATE);               //상담 일시
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 순번최대값구하기
    // 업소방문등록의 일렬번호(4자리)를 생성한다.
    public DOBJ CALLupso_adrs_save_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_adrs_save_SEL11(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        rvobj.Println("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_save_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CONS_DATE = dobj.getRetObject("R").getRecord().get("CONS_DATE");   //상담 일시
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),0)  +  1  MAX_VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  SUBSTR(:CONS_DATE  ,1,8)   \n";
        query +=" AND  JOB_GBN  =  'R' ";
        sobj.setSql(query);
        sobj.setString("CONS_DATE", CONS_DATE);               //상담 일시
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 업소방문등록
    // 업소방문내역을 신규로 등록한다
    public DOBJ CALLupso_adrs_save_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_adrs_save_INS5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_save_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록일시
        String   CONSPRES = dobj.getRetObject("GOV").getRecord().get("HAN_NM");   //상담자
        String   CONS_DATE = dobj.getRetObject("R").getRecord().get("CONS_DATE");   //상담 일시
        int   CONS_SEQ = dobj.getRetObject("R").getRecord().getInt("CONS_SEQ");   //상담 순번
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   JOB_GBN ="R";   //업무 구분(TENV_CODE TABLE의 HIGH_CD = '00131')
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //비고
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   VISIT_DAY = wutil.substr(dobj.getRetObject("R").getRecord().get("CONS_DATE"),0,8);   //방문 일자
        int   VISIT_SEQ = dobj.getRetObject("SEL11").getRecord().getInt("MAX_VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, INSPRES_ID, CONS_SEQ, VISIT_DAY, CONS_DATE, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :CONS_SEQ , :VISIT_DAY , :CONS_DATE , :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("CONSPRES", CONSPRES);               //상담자
        sobj.setString("CONS_DATE", CONS_DATE);               //상담 일시
        sobj.setInt("CONS_SEQ", CONS_SEQ);               //상담 순번
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분(TENV_CODE TABLE의 HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    // 녹취내용메모
    // 녹취내용메모를저장한다.
    public DOBJ CALLupso_adrs_save_INS10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS10");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_adrs_save_INS10(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS10") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_save_INS10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   JOB_GBN ="R";   //업무 구분(TENV_CODE TABLE의 HIGH_CD = '00131')
        String   REMAK = dobj.getRetObject("R").getRecord().get("BRE_REMAK");   //비고
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   VISIT_DAY = wutil.substr(dobj.getRetObject("R").getRecord().get("CONS_DATE"),0,8);   //방문 일자
        int   VISIT_NUM = 1;   //방문 번호
        int   VISIT_SEQ = dobj.getRetObject("SEL11").getRecord().getInt("MAX_VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , sysdate, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :VISIT_NUM , :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분(TENV_CODE TABLE의 HIGH_CD = '00131')
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //방문 번호
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    // 통화내역수정
    // 지부,업소코드를 매칭정보를 수정저장한다.
    public DOBJ CALLupso_adrs_save_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("UPD6");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_adrs_save_UPD6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_save_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   MATCH_YN = dvobj.getRecord().get("MATCH_YN");   //매칭 유무(Y/N/D)
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CONS_DATE = dvobj.getRecord().get("CONS_DATE");   //상담 일시
        int   CONS_SEQ = dvobj.getRecord().getInt("CONS_SEQ");   //상담 순번
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_ADRS_FILEINFO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , UPSO_CD=:UPSO_CD , MOD_DATE=SYSDATE , BRAN_CD=:BRAN_CD , MATCH_YN=:MATCH_YN  \n";
        query +=" where CONS_SEQ=:CONS_SEQ  \n";
        query +=" and CONS_DATE=:CONS_DATE";
        sobj.setSql(query);
        sobj.setString("MATCH_YN", MATCH_YN);               //매칭 유무(Y/N/D)
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CONS_DATE", CONS_DATE);               //상담 일시
        sobj.setInt("CONS_SEQ", CONS_SEQ);               //상담 순번
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 방문등록 메모 수정
    // 방문등록의 메모를 수정한다.
    public DOBJ CALLupso_adrs_save_UPD12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("UPD12");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_adrs_save_UPD12(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_save_UPD12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   MOD_DATE = dvobj.getRecord().get("MOD_DATE");   //수정 일시
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //수정자 ID
        String   JOB_GBN ="R";   //업무 구분(TENV_CODE TABLE의 HIGH_CD = '00131')
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   VISIT_DAY = wutil.substr(dobj.getRetObject("R").getRecord().get("CONS_DATE"),0,8);   //방문 일자
        int   VISIT_SEQ = dobj.getRetObject("R").getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MOD_DATE=:MOD_DATE , REMAK=:REMAK  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("MOD_DATE", MOD_DATE);               //수정 일시
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분(TENV_CODE TABLE의 HIGH_CD = '00131')
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    //##**$$upso_adrs_save
    //##**$$upso_adrs_delete
    /* * 프로그램명 : bra01_s05
    * 작성자 : 서정재
    * 작성일 : 2009/11/26
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLupso_adrs_delete(DOBJ dobj)
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
            dobj  = CALLupso_adrs_delete_MIUD4(Conn, dobj);           //  통화내역삭제
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
    public DOBJ CTLupso_adrs_delete( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_adrs_delete_MIUD4(Conn, dobj);           //  통화내역삭제
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 통화내역삭제
    public DOBJ CALLupso_adrs_delete_MIUD4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD4");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_adrs_delete_UPD1(Conn, dobj);           //  통화내역초기화
                dobj  = CALLupso_adrs_delete_DEL2(Conn, dobj);           //  방문등록삭제
                dobj  = CALLupso_adrs_delete_DEL3(Conn, dobj);           //  메모삭제
            }
        }
        return dobj;
    }
    // 통화내역초기화
    // 통화내역의 업소 정보를 클리어한다
    public DOBJ CALLupso_adrs_delete_UPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD1");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_adrs_delete_UPD1(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD1") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_delete_UPD1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MOD_DATE = dvobj.getRecord().get("MOD_DATE");   //수정 일시
        String   CONS_DATE = dvobj.getRecord().get("CONS_DATE");   //상담 일시
        int   CONS_SEQ = dvobj.getRecord().getInt("CONS_SEQ");   //상담 순번
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //수정자 ID
        String   BRAN_CD ="";   //지부 코드
        String   MATCH_YN ="N";   //순번
        String   UPSO_CD ="";   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_ADRS_FILEINFO  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , UPSO_CD=:UPSO_CD , MOD_DATE=:MOD_DATE , BRAN_CD=:BRAN_CD , MATCH_YN=:MATCH_YN  \n";
        query +=" where CONS_SEQ=:CONS_SEQ  \n";
        query +=" and CONS_DATE=:CONS_DATE";
        sobj.setSql(query);
        sobj.setString("MOD_DATE", MOD_DATE);               //수정 일시
        sobj.setString("CONS_DATE", CONS_DATE);               //상담 일시
        sobj.setInt("CONS_SEQ", CONS_SEQ);               //상담 순번
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("MATCH_YN", MATCH_YN);               //순번
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 방문등록삭제
    // 방문 등록 내역을 삭제 한다.
    public DOBJ CALLupso_adrs_delete_DEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL2");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_adrs_delete_DEL2(dobj, dvobj);
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
    private SQLObject SQLupso_adrs_delete_DEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_VISIT  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    // 메모삭제
    // 메모삭제한다
    public DOBJ CALLupso_adrs_delete_DEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL3");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_adrs_delete_DEL3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL3") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_delete_DEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    //##**$$upso_adrs_delete
    //##**$$upso_adrs_staff
    /* * 프로그램명 : bra01_s05
    * 작성자 : 이광노
    * 작성일 : 2009/09/07
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLupso_adrs_staff(DOBJ dobj)
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
            dobj  = CALLupso_adrs_staff_SEL3(Conn, dobj);           //  지부팀원조회
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
    public DOBJ CTLupso_adrs_staff( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_adrs_staff_SEL3(Conn, dobj);           //  지부팀원조회
        return dobj;
    }
    // 지부팀원조회
    // 해당 사번의 지부팀원을 조회한다.
    public DOBJ CALLupso_adrs_staff_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_adrs_staff_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_adrs_staff_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   STAFF_NUM = dobj.getRetObject("S").getRecord().get("STAFF_NUM");   //사원번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.STAFF_NUM  STAFF_CD  ,  B.HAN_NM  STAFF_NM  ,  B.HAN_NM  ||  '('  ||  NVL(C.IPPBX_USER_ID,'미등록')  ||  ')'  CALL_NM  ,  B.ETCOM_DAY  ,  A.GIBU,  NVL(C.IPPBX_USER_ID,'미등록')  IPPBX_USER_ID  FROM  INSA.TCPM_DEPT  A  ,  INSA.TINS_MST01  B  ,  FIDU.TENV_MEMBER  C  WHERE  A.DEPT_CD  =  B.DEPT_CD   \n";
        query +=" AND  A.GIBU  =   \n";
        query +=" (SELECT  A.GIBU  FROM  INSA.TCPM_DEPT  A  ,  INSA.TINS_MST01  B  WHERE  A.DEPT_CD  =  B.DEPT_CD   \n";
        query +=" AND  B.STAFF_NUM  =  :STAFF_NUM   \n";
        query +=" AND  B.RETIRE_DAY  IS  NULL)   \n";
        query +=" AND  B.RETIRE_DAY  IS  NULL   \n";
        query +=" AND  B.STAFF_NUM  =  C.STAFF_NUM(+) ";
        sobj.setSql(query);
        sobj.setString("STAFF_NUM", STAFF_NUM);               //사원번호
        return sobj;
    }
    //##**$$upso_adrs_staff
    //##**$$end
}
