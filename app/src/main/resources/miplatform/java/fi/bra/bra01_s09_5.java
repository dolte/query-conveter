
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s09_5
{
    public bra01_s09_5()
    {
    }
    //##**$$upso_visit_regist
    /* * 프로그램명 : bra01_s09
    * 작성자 : 서정재
    * 작성일 : 2009/11/25
    * 설명 : 출장/업소클릭콜/업소명변경/최고서등록/기타 업소관련 정보등을 관리한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLupso_visit_regist(DOBJ dobj)
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
            dobj  = CALLupso_visit_regist_MIUD1(Conn, dobj);           //  업소방문관리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_visit_regist_MIUD13(Conn, dobj);           //  업소방문상세관리
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLupso_visit_regist_SEL24(Conn, dobj);           //  업소방문리스트
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
    public DOBJ CTLupso_visit_regist( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_visit_regist_MIUD1(Conn, dobj);           //  업소방문관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupso_visit_regist_MIUD13(Conn, dobj);           //  업소방문상세관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLupso_visit_regist_SEL24(Conn, dobj);           //  업소방문리스트
        return dobj;
    }
    // 업소방문관리
    public DOBJ CALLupso_visit_regist_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLupso_visit_regist_SEL19(Conn, dobj);           //  VISIT_SEQ구하기
                dobj  = CALLupso_visit_regist_INS5(Conn, dobj);           //  업소방문신규
                if( dobj.getRetObject("S3").getRecordCnt() == 1)
                {
                    dobj  = CALLupso_visit_regist_FUT16( dobj);        //  파일무브
                    dobj  = CALLupso_visit_regist_CVT17( dobj);        //  파일무브이동정보
                    dobj  = CALLupso_visit_regist_INS18(Conn, dobj);           //  파일업로드정보저장
                }
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_SEL30(Conn, dobj);           //  1
                if( dobj.getRetObject("R").getRecord().get("CNT1").equals("1") || dobj.getRetObject("R").getRecord().get("CNT2").equals("1"))
                {
                    dobj  = CALLupso_visit_regist_SEL27(Conn, dobj);           //  VISIT_SEQ구하기
                    dobj  = CALLupso_visit_regist_XIUD31(Conn, dobj);           //  업소방문수정
                    if( dobj.getRetObject("S3").getRecordCnt() == 1)
                    {
                        dobj  = CALLupso_visit_regist_DEL23(Conn, dobj);           //  파일업로드정보삭제
                        dobj  = CALLupso_visit_regist_FUT24( dobj);        //  파일무브
                        dobj  = CALLupso_visit_regist_CVT25( dobj);        //  파일무브이동정보
                        dobj  = CALLupso_visit_regist_INS26(Conn, dobj);           //  파일업로드정보저장
                    }
                }
                else
                {
                    dobj  = CALLupso_visit_regist_UPD32(Conn, dobj);           //  업소방문수정
                    if( dobj.getRetObject("S3").getRecordCnt() == 1)
                    {
                        dobj  = CALLupso_visit_regist_DEL34(Conn, dobj);           //  파일업로드정보삭제
                        dobj  = CALLupso_visit_regist_FUT35( dobj);        //  파일무브
                        dobj  = CALLupso_visit_regist_CVT36( dobj);        //  파일무브이동정보
                        dobj  = CALLupso_visit_regist_INS37(Conn, dobj);           //  파일업로드정보저장
                    }
                }
            }
        }
        return dobj;
    }
    // VISIT_SEQ구하기
    // 업소방문등록의 일련번호(4자리)를 생성한다.
    public DOBJ CALLupso_visit_regist_SEL19(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL19");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL19");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_visit_regist_SEL19(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL19");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_SEL19(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("VISIT_DAY");   //방문 일자
        String   JOB_GBN = dobj.getRetObject("R").getRecord().get("JOB_GBN");   //업무 구분(TENV_CODE TABLE의 HIGH_CD = '00131')
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),  0)  +  1  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분(TENV_CODE TABLE의 HIGH_CD = '00131')
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 1
    public DOBJ CALLupso_visit_regist_SEL30(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL30");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL30");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_visit_regist_SEL30(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL30");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_SEL30(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  1  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 업소방문신규
    // 데이타를 등록한다.
    public DOBJ CALLupso_visit_regist_INS5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_INS5(dobj, dvobj);
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
    private SQLObject SQLupso_visit_regist_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   CONSPRES = dvobj.getRecord().get("CONSPRES");   //상담자
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        String   VISIT_TIME = dvobj.getRecord().get("VISIT_TIME");   //방문 시간
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT (VISIT_SEQ, INS_DATE, VISIT_TIME, INSPRES_ID, VISIT_DAY, JOB_GBN, UPSO_CD, CONSPRES, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :VISIT_TIME , :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :UPSO_CD , :CONSPRES , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("CONSPRES", CONSPRES);               //상담자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("VISIT_TIME", VISIT_TIME);               //방문 시간
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    // VISIT_SEQ구하기
    // 업소방문등록의 일련번호(4자리)를 생성한다.
    public DOBJ CALLupso_visit_regist_SEL27(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL27");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL27");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_visit_regist_SEL27(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL27");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_SEL27(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("NEW_VISIT_DAY");   //방문 일자
        String   JOB_GBN = dobj.getRetObject("R").getRecord().get("NEW_JOB_GBN");   //업무 구분
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(VISIT_SEQ),  0)  +  1  VISIT_SEQ  FROM  GIBU.TBRA_UPSO_VISIT  WHERE  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  VISIT_DAY  =  :VISIT_DAY   \n";
        query +=" AND  JOB_GBN  =  :JOB_GBN ";
        sobj.setSql(query);
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // 파일무브
    public DOBJ CALLupso_visit_regist_FUT16(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT16");
        VOBJ dvobj = dobj.getRetObject("S3");      //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("FUT16");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("UNIFILENAME");         //목적파일명
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //목적파일경로
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("UNIFILENAME");         //소스파일명
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("PATH");         //소스파일경로
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT16") ;
        dvobj.Println("FUT16");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // 업소방문수정
    // 기존에 등록된 데이타를 수정한다.
    public DOBJ CALLupso_visit_regist_XIUD31(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD31");
        VOBJ dvobj = dobj.getRetObject("R");            //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_XIUD31(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD31");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_XIUD31(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   CONSPRES = dobj.getRetObject("R").getRecord().get("CONSPRES");   //상담자
        String   CONS_DATE = dobj.getRetObject("R").getRecord().get("CONS_DATE");   //상담 일시
        int   CONS_SEQ = dobj.getRetObject("R").getRecord().getInt("CONS_SEQ");   //상담 순번
        String   JOB_GBN = dobj.getRetObject("R").getRecord().get("JOB_GBN");   //업무 구분
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        String   NEW_JOB_GBN = dobj.getRetObject("R").getRecord().get("NEW_JOB_GBN");   //신규방문구분
        String   NEW_VISIT_DAY = dobj.getRetObject("R").getRecord().get("NEW_VISIT_DAY");   //신규방문일자
        String   NEW_VISIT_SEQ = dobj.getRetObject("SEL27").getRecord().get("VISIT_SEQ");   //신규방문순번
        String   REMAK = dobj.getRetObject("R").getRecord().get("REMAK");   //비고
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   VISIT_DAY = dobj.getRetObject("R").getRecord().get("VISIT_DAY");   //방문 일자
        int   VISIT_SEQ = dobj.getRetObject("R").getRecord().getInt("VISIT_SEQ");   //방문자순번
        String   VISIT_TIME = dobj.getRetObject("R").getRecord().get("VISIT_TIME");   //방문 시간
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_UPSO_VISIT  \n";
        query +=" SET CONSPRES = :CONSPRES , REMAK = :REMAK , MODPRES_ID = :MODPRES_ID , MOD_DATE = SYSDATE , VISIT_DAY = :NEW_VISIT_DAY , VISIT_SEQ = :NEW_VISIT_SEQ , JOB_GBN = :NEW_JOB_GBN , CONS_DATE = :CONS_DATE , CONS_SEQ = :CONS_SEQ , VISIT_TIME = :VISIT_TIME  \n";
        query +=" WHERE UPSO_CD = :UPSO_CD  \n";
        query +=" AND VISIT_DAY = :VISIT_DAY  \n";
        query +=" AND VISIT_SEQ = :VISIT_SEQ  \n";
        query +=" AND JOB_GBN = :JOB_GBN";
        sobj.setSql(query);
        sobj.setString("CONSPRES", CONSPRES);               //상담자
        sobj.setString("CONS_DATE", CONS_DATE);               //상담 일시
        sobj.setInt("CONS_SEQ", CONS_SEQ);               //상담 순번
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("NEW_JOB_GBN", NEW_JOB_GBN);               //신규방문구분
        sobj.setString("NEW_VISIT_DAY", NEW_VISIT_DAY);               //신규방문일자
        sobj.setString("NEW_VISIT_SEQ", NEW_VISIT_SEQ);               //신규방문순번
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        sobj.setString("VISIT_TIME", VISIT_TIME);               //방문 시간
        return sobj;
    }
    // 파일무브이동정보
    public DOBJ CALLupso_visit_regist_CVT17(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT17");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("S3");        //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("CVT17");
        String[] outcolumns ={"UNIFILENAME","FILESIZE","PATH","UPFILENAME","DFILEPATH"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.cpRecord();
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //목적파일경로
            record.put("DFILEPATH",DFILEPATH);
            for(int i=0;i<outcolumns.length;i++)
            {
                if(!record.containsKey(outcolumns[i]))
                {
                    record.remove(outcolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        dvobj.setName("CVT17") ;
        dvobj.Println("CVT17");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // 파일업로드정보저장
    public DOBJ CALLupso_visit_regist_INS18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS18");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        String fullname="";
        dvobj.first();
        while(dvobj.next())
        {
            byte[] binFILES=wutil.inputFileStream("/home/jeus/komca_web/upload", dobj.getRetObject("S3").getRecord().get("UNIFILENAME"));
            dvobj.getRecord().put("FILES", binFILES);
        }
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_INS18(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS18") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_INS18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        byte[]   FILES = dvobj.getRecord().getBytes("FILES");   //파일
        String   FILE_NM = dobj.getRetObject("S3").getRecord().get("UPFILENAME");   //파일명
        String   FILE_ROUT = dobj.getRetObject("CVT17").getRecord().get("DFILEPATH");   //파일경로
        double   FILE_SIZE = dobj.getRetObject("S3").getRecord().getDouble("FILESIZE");   //파일 크기
        String   FILE_TEMPNM = dobj.getRetObject("S3").getRecord().get("UNIFILENAME");   //변환 파일명
        double   SEQ = 1;   //관리번호
        int   VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_ATTCH (VISIT_SEQ, FILE_SIZE, FILE_TEMPNM, VISIT_DAY, JOB_GBN, FILE_ROUT, UPSO_CD, SEQ, FILE_NM, FILES)  \n";
        query +=" values(:VISIT_SEQ , :FILE_SIZE , :FILE_TEMPNM , :VISIT_DAY , :JOB_GBN , :FILE_ROUT , :UPSO_CD , :SEQ , :FILE_NM , :FILES )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setBlob("FILES", FILES);               //파일
        sobj.setString("FILE_NM", FILE_NM);               //파일명
        sobj.setString("FILE_ROUT", FILE_ROUT);               //파일경로
        sobj.setDouble("FILE_SIZE", FILE_SIZE);               //파일 크기
        sobj.setString("FILE_TEMPNM", FILE_TEMPNM);               //변환 파일명
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //관리번호
        return sobj;
    }
    // 파일업로드정보삭제
    public DOBJ CALLupso_visit_regist_DEL23(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL23");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_DEL23(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL23") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_DEL23(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_VISIT_ATTCH  \n";
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
    // 파일무브
    public DOBJ CALLupso_visit_regist_FUT24(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT24");
        VOBJ dvobj = dobj.getRetObject("S3");      //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("FUT24");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("UNIFILENAME");         //목적파일명
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //목적파일경로
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("UNIFILENAME");         //소스파일명
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("PATH");         //소스파일경로
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT24") ;
        dvobj.Println("FUT24");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // 파일무브이동정보
    public DOBJ CALLupso_visit_regist_CVT25(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT25");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("S3");        //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("CVT25");
        String[] outcolumns ={"UNIFILENAME","FILESIZE","PATH","UPFILENAME","DFILEPATH"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.cpRecord();
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //목적파일경로
            record.put("DFILEPATH",DFILEPATH);
            for(int i=0;i<outcolumns.length;i++)
            {
                if(!record.containsKey(outcolumns[i]))
                {
                    record.remove(outcolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        dvobj.setName("CVT25") ;
        dvobj.Println("CVT25");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // 파일업로드정보저장
    public DOBJ CALLupso_visit_regist_INS26(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS26");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        String fullname="";
        dvobj.first();
        while(dvobj.next())
        {
            byte[] binFILES=wutil.inputFileStream("/home/jeus/komca_web/upload", dobj.getRetObject("S3").getRecord().get("UNIFILENAME"));
            dvobj.getRecord().put("FILES", binFILES);
        }
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_INS26(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS26") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_INS26(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //방문자순번
        byte[]   FILES = dvobj.getRecord().getBytes("FILES");   //파일
        String   FILE_NM = dobj.getRetObject("S3").getRecord().get("UPFILENAME");   //파일명
        String   FILE_ROUT = dobj.getRetObject("CVT25").getRecord().get("DFILEPATH");   //파일경로
        double   FILE_SIZE = dobj.getRetObject("S3").getRecord().getDouble("FILESIZE");   //파일 크기
        String   FILE_TEMPNM = dobj.getRetObject("S3").getRecord().get("UNIFILENAME");   //변환 파일명
        double   SEQ = 1;   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_ATTCH (VISIT_SEQ, FILE_SIZE, FILE_TEMPNM, VISIT_DAY, JOB_GBN, FILE_ROUT, UPSO_CD, SEQ, FILE_NM, FILES)  \n";
        query +=" values(:VISIT_SEQ , :FILE_SIZE , :FILE_TEMPNM , :VISIT_DAY , :JOB_GBN , :FILE_ROUT , :UPSO_CD , :SEQ , :FILE_NM , :FILES )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        sobj.setBlob("FILES", FILES);               //파일
        sobj.setString("FILE_NM", FILE_NM);               //파일명
        sobj.setString("FILE_ROUT", FILE_ROUT);               //파일경로
        sobj.setDouble("FILE_SIZE", FILE_SIZE);               //파일 크기
        sobj.setString("FILE_TEMPNM", FILE_TEMPNM);               //변환 파일명
        sobj.setDouble("SEQ", SEQ);               //관리번호
        return sobj;
    }
    // 업소방문수정
    // 기존에 등록된 데이타를 수정한다.
    public DOBJ CALLupso_visit_regist_UPD32(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD32");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_UPD32(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD32") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_UPD32(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   CONSPRES = dvobj.getRecord().get("CONSPRES");   //상담자
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   CONS_DATE = dvobj.getRecord().get("CONS_DATE");   //상담 일시
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        int   CONS_SEQ = dvobj.getRecord().getInt("CONS_SEQ");   //상담 순번
        String   VISIT_TIME = dvobj.getRecord().get("VISIT_TIME");   //방문 시간
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //방문자순번
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , VISIT_TIME=:VISIT_TIME , CONS_SEQ=:CONS_SEQ , CONS_DATE=:CONS_DATE , MOD_DATE=SYSDATE , CONSPRES=:CONSPRES , REMAK=:REMAK  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("CONSPRES", CONSPRES);               //상담자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("CONS_DATE", CONS_DATE);               //상담 일시
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("CONS_SEQ", CONS_SEQ);               //상담 순번
        sobj.setString("VISIT_TIME", VISIT_TIME);               //방문 시간
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 파일업로드정보삭제
    public DOBJ CALLupso_visit_regist_DEL34(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL34");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_DEL34(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL34") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_DEL34(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_VISIT_ATTCH  \n";
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
    // 파일무브
    public DOBJ CALLupso_visit_regist_FUT35(DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("FUT35");
        VOBJ dvobj = dobj.getRetObject("S3");      //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("FUT35");
        String     tmpname ="";
        HashMap record = null;
        dvobj.first();
        while(dvobj.next())
        {
            record = new HashMap();
            ////
            String   DFILENAME = dvobj.getRecord().get("UNIFILENAME");         //목적파일명
            record.put("DFILENAME",DFILENAME);
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //목적파일경로
            record.put("DFILEPATH",DFILEPATH);
            ////
            String   SFILENAME = dvobj.getRecord().get("UNIFILENAME");         //소스파일명
            record.put("SFILENAME",SFILENAME);
            ////
            String   SFILEPATH = dvobj.getRecord().get("PATH");         //소스파일경로
            record.put("SFILEPATH",SFILEPATH);
            tmpname= wutil.move(record.get("SFILEPATH").toString() , record.get("SFILENAME").toString(),record.get("DFILEPATH").toString(),"","1");
            dvobj.getRecord().put("DFILENAME",tmpname);
        }
        dvobj.setName("FUT35") ;
        dvobj.Println("FUT35");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // 파일무브이동정보
    public DOBJ CALLupso_visit_regist_CVT36(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("CVT36");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ dvobj = dobj.getRetObject("S3");        //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("CVT36");
        String[] outcolumns ={"UNIFILENAME","FILESIZE","PATH","UPFILENAME","DFILEPATH"};
        VOBJ       rvobj= null;
        HashMap    record =null;
        dvobj.first();
        while(dvobj.next())
        {
            record = dvobj.cpRecord();
            ////
            String   DFILEPATH ="/home/jeus/komca_web/upload";         //목적파일경로
            record.put("DFILEPATH",DFILEPATH);
            for(int i=0;i<outcolumns.length;i++)
            {
                if(!record.containsKey(outcolumns[i]))
                {
                    record.remove(outcolumns[i]);
                }
            }
            dvobj.setRecord(record);
        }
        dvobj.setName("CVT36") ;
        dvobj.Println("CVT36");
        dobj.setRetObject(dvobj);
        return dobj;
    }
    // 파일업로드정보저장
    public DOBJ CALLupso_visit_regist_INS37(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS37");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_INS37(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS37") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_INS37(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //방문자순번
        byte[]   FILES = dvobj.getRecord().getBytes("FILES");   //파일
        String   FILE_NM = dobj.getRetObject("S3").getRecord().get("UPFILENAME");   //파일명
        String   FILE_ROUT = dobj.getRetObject("CVT25").getRecord().get("DFILEPATH");   //파일경로
        double   FILE_SIZE = dobj.getRetObject("S3").getRecord().getDouble("FILESIZE");   //파일 크기
        String   FILE_TEMPNM = dobj.getRetObject("S3").getRecord().get("UNIFILENAME");   //변환 파일명
        double   SEQ = 1;   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_ATTCH (VISIT_SEQ, FILE_SIZE, FILE_TEMPNM, VISIT_DAY, JOB_GBN, FILE_ROUT, UPSO_CD, SEQ, FILE_NM, FILES)  \n";
        query +=" values(:VISIT_SEQ , :FILE_SIZE , :FILE_TEMPNM , :VISIT_DAY , :JOB_GBN , :FILE_ROUT , :UPSO_CD , :SEQ , :FILE_NM , :FILES )";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        sobj.setBlob("FILES", FILES);               //파일
        sobj.setString("FILE_NM", FILE_NM);               //파일명
        sobj.setString("FILE_ROUT", FILE_ROUT);               //파일경로
        sobj.setDouble("FILE_SIZE", FILE_SIZE);               //파일 크기
        sobj.setString("FILE_TEMPNM", FILE_TEMPNM);               //변환 파일명
        sobj.setDouble("SEQ", SEQ);               //관리번호
        return sobj;
    }
    // 업소방문상세관리
    public DOBJ CALLupso_visit_regist_MIUD13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD13");
        VOBJ dvobj = dobj.getRetObject("S1");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_INS7(Conn, dobj);           //  업소방문상세입력
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_UPD17(Conn, dobj);           //  업소방문상세수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_DEL18(Conn, dobj);           //  업소방문상세삭제
            }
        }
        return dobj;
    }
    // 업소방문상세삭제
    // 기존에 등록한 상세내용을 삭제한다.
    public DOBJ CALLupso_visit_regist_DEL18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL18");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_DEL18(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL18") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_DEL18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        int   VISIT_NUM = dvobj.getRecord().getInt("VISIT_NUM");   //방문 번호
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and VISIT_NUM=:VISIT_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //방문 번호
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    // 업소방문상세입력
    // 상세내용을 등록한다.
    public DOBJ CALLupso_visit_regist_INS7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_INS7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        int   VISIT_NUM = dvobj.getRecord().getInt("VISIT_NUM");   //방문 번호
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        int  VISIT_SEQ ;         //방문자순번
        if( ( dobj.getRetObject("S").getRecord().get("IUDFLAG").equals("I") ))
        {
            VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");
        }
        else
        {
            VISIT_SEQ = dobj.getRetObject("R").getRecord().getInt("VISIT_SEQ");
        }
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :VISIT_NUM , :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //방문 번호
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    // 업소방문상세수정
    // 기존에 등록된 상세내용을 수정한다.
    public DOBJ CALLupso_visit_regist_UPD17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD17");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_UPD17(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD17") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_UPD17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        int   VISIT_NUM = dvobj.getRecord().getInt("VISIT_NUM");   //방문 번호
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //방문자순번
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MOD_DATE=SYSDATE , REMAK=:REMAK  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and VISIT_NUM=:VISIT_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //방문 번호
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 업소방문리스트
    // 업소방문 테이블에 등록된 데이타를 조회한다.
    public DOBJ CALLupso_visit_regist_SEL24(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL24");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL24");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLupso_visit_regist_SEL24(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL24");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_SEL24(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S2").getRecord().get("START_DAY");   //시작일
        String   UPSO_CD = dobj.getRetObject("S2").getRecord().get("UPSO_CD");   //업소 코드
        String   END_DAY = dobj.getRetObject("S2").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  A.VISIT_DAY  ,  A.JOB_GBN  ,  B.CODE_NM  ,  A.CONSPRES  ,  A.REMAK  ,  A.VISIT_SEQ  ,  A.CONS_DATE  ,  A.CONS_SEQ  ,  '/home/jeus/komca_web/upload'  AS  FILE_ROUT  ,  C.FILE_NM  FROM  GIBU.TBRA_UPSO_VISIT  A  ,  FIDU.TENV_CODE  B  ,  GIBU.TBRA_UPSO_ADRS_FILEINFO  C  WHERE  A.UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  B.HIGH_CD  =  '00198'   \n";
        query +=" AND  A.JOB_GBN  =  B.CODE_CD   \n";
        query +=" AND  A.VISIT_DAY  BETWEEN  :START_DAY   \n";
        query +=" AND  :END_DAY   \n";
        query +=" AND  A.CONS_DATE  =  C.CONS_DATE(+)   \n";
        query +=" AND  A.CONS_SEQ  =  C.CONS_SEQ(+)  ORDER  BY  VISIT_DAY  DESC ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$upso_visit_regist
    //##**$$upso_visit_regist_bre
    /*
    */
    public DOBJ CTLupso_visit_regist_bre(DOBJ dobj)
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
            dobj  = CALLupso_visit_regist_bre_MIUD13(Conn, dobj);           //  업소방문상세관리
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
    public DOBJ CTLupso_visit_regist_bre( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLupso_visit_regist_bre_MIUD13(Conn, dobj);           //  업소방문상세관리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 업소방문상세관리
    public DOBJ CALLupso_visit_regist_bre_MIUD13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD13");
        VOBJ dvobj = dobj.getRetObject("S1");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_bre_INS7(Conn, dobj);           //  업소방문상세입력
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_bre_UPD17(Conn, dobj);           //  업소방문상세수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLupso_visit_regist_bre_DEL18(Conn, dobj);           //  업소방문상세삭제
            }
        }
        return dobj;
    }
    // 업소방문상세삭제
    // 기존에 등록한 상세내용을 삭제한다.
    public DOBJ CALLupso_visit_regist_bre_DEL18(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL18");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_bre_DEL18(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL18") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_bre_DEL18(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        int   VISIT_NUM = dvobj.getRecord().getInt("VISIT_NUM");   //방문 번호
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //방문자순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and VISIT_NUM=:VISIT_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //방문 번호
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    // 업소방문상세입력
    // 상세내용을 등록한다.
    public DOBJ CALLupso_visit_regist_bre_INS7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_bre_INS7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_bre_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        int   VISIT_NUM = dvobj.getRecord().getInt("VISIT_NUM");   //방문 번호
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        int  VISIT_SEQ ;         //방문자순번
        if( ( dobj.getRetObject("S").getRecord().get("IUDFLAG").equals("I") ))
        {
            VISIT_SEQ = dobj.getRetObject("SEL19").getRecord().getInt("VISIT_SEQ");
        }
        else
        {
            VISIT_SEQ = dobj.getRetObject("R").getRecord().getInt("VISIT_SEQ");
        }
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_UPSO_VISIT_BRE (VISIT_SEQ, INS_DATE, INSPRES_ID, VISIT_DAY, JOB_GBN, VISIT_NUM, UPSO_CD, REMAK)  \n";
        query +=" values(:VISIT_SEQ , SYSDATE, :INSPRES_ID , :VISIT_DAY , :JOB_GBN , :VISIT_NUM , :UPSO_CD , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //방문 번호
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        return sobj;
    }
    // 업소방문상세수정
    // 기존에 등록된 상세내용을 수정한다.
    public DOBJ CALLupso_visit_regist_bre_UPD17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD17");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLupso_visit_regist_bre_UPD17(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD17") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLupso_visit_regist_bre_UPD17(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        int   VISIT_NUM = dvobj.getRecord().getInt("VISIT_NUM");   //방문 번호
        String   JOB_GBN = dvobj.getRecord().get("JOB_GBN");   //업무 구분
        String   VISIT_DAY = dvobj.getRecord().get("VISIT_DAY");   //방문 일자
        int   VISIT_SEQ = dvobj.getRecord().getInt("VISIT_SEQ");   //방문자순번
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_UPSO_VISIT_BRE  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , MOD_DATE=SYSDATE , REMAK=:REMAK  \n";
        query +=" where VISIT_SEQ=:VISIT_SEQ  \n";
        query +=" and VISIT_DAY=:VISIT_DAY  \n";
        query +=" and JOB_GBN=:JOB_GBN  \n";
        query +=" and VISIT_NUM=:VISIT_NUM  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setInt("VISIT_NUM", VISIT_NUM);               //방문 번호
        sobj.setString("JOB_GBN", JOB_GBN);               //업무 구분
        sobj.setString("VISIT_DAY", VISIT_DAY);               //방문 일자
        sobj.setInt("VISIT_SEQ", VISIT_SEQ);               //방문자순번
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    //##**$$upso_visit_regist_bre
    //##**$$end
}
