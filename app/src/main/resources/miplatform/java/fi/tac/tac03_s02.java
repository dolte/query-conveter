
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac03_s02
{
    public tac03_s02()
    {
    }
    //##**$$tac03_s02dtselect
    /* * 프로그램명 : tac03_s02
    * 작성자 : 성낙문
    * 작성일 : 2009/09/21
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac03_s02dtselect(DOBJ dobj)
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
            dobj  = CALLtac03_s02dtselect_SEL1(Conn, dobj);           //  동호회비 상세조회
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
    public DOBJ CTLtac03_s02dtselect( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac03_s02dtselect_SEL1(Conn, dobj);           //  동호회비 상세조회
        return dobj;
    }
    // 동호회비 상세조회
    public DOBJ CALLtac03_s02dtselect_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac03_s02dtselect_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac03_s02dtselect_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  MB_CD,  MNG_NUM,  CLUB_GBN,  DEDCTTRM_START_DAY,  DEDCTTRM_END_DAY,  DEDCT_YN,  DEDCT_AMT  FROM  FIDU.TMEM_CLUBEXPSDEDCTBRE  WHERE  MB_CD  =  :MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    //##**$$tac03_s02dtselect
    //##**$$tmem_club_save
    /* * 프로그램명 : tac03_s02
    * 작성자 : 성낙문
    * 작성일 : 2009/12/01
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtmem_club_save(DOBJ dobj)
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
            dobj  = CALLtmem_club_save_MIUD9(Conn, dobj);           //  동호회비공제등록
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
    public DOBJ CTLtmem_club_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtmem_club_save_MIUD9(Conn, dobj);           //  동호회비공제등록
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 동호회비공제등록
    // 동호회비공제등록
    public DOBJ CALLtmem_club_save_MIUD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD9");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtmem_club_save_INS13(Conn, dobj);           //  동호회비공제저장
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtmem_club_save_UPD16(Conn, dobj);           //  동호회비공제수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtmem_club_save_DEL7(Conn, dobj);           //  DEL
            }
        }
        return dobj;
    }
    // DEL
    public DOBJ CALLtmem_club_save_DEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL7");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtmem_club_save_DEL7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL7") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtmem_club_save_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //관리번호
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TMEM_CLUBEXPSDEDCTBRE  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //관리번호
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    // 동호회비공제저장
    // 동호회비공제저장
    public DOBJ CALLtmem_club_save_INS13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS13");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtmem_club_save_INS13(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS13") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtmem_club_save_INS13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        double MNG_NUM = 0;        //관리번호
        String MOD_DATE ="";        //수정 일시
        String   PROG_STAT = dvobj.getRecord().get("PROG_STAT");   //진행 상태
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //신청 구분
        String   DEDCTTRM_END_YRMN = dvobj.getRecord().get("DEDCTTRM_END_YRMN");   //공제기간 종료 년월
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
        String   CLUB_GBN = dvobj.getRecord().get("CLUB_GBN");   //동호회 구분
        String   DEDCTTRM_START_YRMN = dvobj.getRecord().get("DEDCTTRM_START_YRMN");   //공제기간 시작 년월
        String   RECPT_FORM = dvobj.getRecord().get("RECPT_FORM");   //접수형태
        double   DEDCT_AMT = dvobj.getRecord().getDouble("DEDCT_AMT");   //공제 금액
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //수정자 ID
        String   INSPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //등록자 ID
        String   MB_CD = dobj.getRetObject("R").getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TMEM_CLUBEXPSDEDCTBRE (MODPRES_ID, DEDCT_AMT, INSPRES_ID, MNG_NUM, RECPT_FORM, DEDCTTRM_START_YRMN, MB_CD, CLUB_GBN, INS_DATE, APPTN_DAY, DEDCTTRM_END_YRMN, MOD_DATE, APPTN_GBN, PROG_STAT)  \n";
        query +=" values(:MODPRES_ID , :DEDCT_AMT , :INSPRES_ID , (SELECT NVL(MAX(MNG_NUM),0) + 1 AS MNG_NUM from FIDU.TMEM_CLUBEXPSDEDCTBRE), :RECPT_FORM , :DEDCTTRM_START_YRMN , :MB_CD , :CLUB_GBN , TO_CHAR(SYSDATE,'YYYYMMDD'), :APPTN_DAY , :DEDCTTRM_END_YRMN , TO_CHAR(SYSDATE,'YYYYMMDD'), :APPTN_GBN , :PROG_STAT )";
        sobj.setSql(query);
        sobj.setString("PROG_STAT", PROG_STAT);               //진행 상태
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        sobj.setString("DEDCTTRM_END_YRMN", DEDCTTRM_END_YRMN);               //공제기간 종료 년월
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("CLUB_GBN", CLUB_GBN);               //동호회 구분
        sobj.setString("DEDCTTRM_START_YRMN", DEDCTTRM_START_YRMN);               //공제기간 시작 년월
        sobj.setString("RECPT_FORM", RECPT_FORM);               //접수형태
        sobj.setDouble("DEDCT_AMT", DEDCT_AMT);               //공제 금액
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    // 동호회비공제수정
    // 동호회비공제수정
    public DOBJ CALLtmem_club_save_UPD16(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD16");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtmem_club_save_UPD16(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD16") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtmem_club_save_UPD16(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   PROG_STAT = dvobj.getRecord().get("PROG_STAT");   //진행 상태
        String   APPTN_GBN = dvobj.getRecord().get("APPTN_GBN");   //신청 구분
        String   DEDCTTRM_END_YRMN = dvobj.getRecord().get("DEDCTTRM_END_YRMN");   //공제기간 종료 년월
        String   APPTN_DAY = dvobj.getRecord().get("APPTN_DAY");   //신청 일자
        String   INS_DATE = dvobj.getRecord().get("INS_DATE");   //등록 일시
        String   CLUB_GBN = dvobj.getRecord().get("CLUB_GBN");   //동호회 구분
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        String   DEDCTTRM_START_YRMN = dvobj.getRecord().get("DEDCTTRM_START_YRMN");   //공제기간 시작 년월
        String   RECPT_FORM = dvobj.getRecord().get("RECPT_FORM");   //접수형태
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //관리번호
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        double   DEDCT_AMT = dvobj.getRecord().getDouble("DEDCT_AMT");   //공제 금액
        String   MODPRES_ID = dobj.getRetObject("G").getRecord().get("USERID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TMEM_CLUBEXPSDEDCTBRE  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , DEDCT_AMT=:DEDCT_AMT , INSPRES_ID=:INSPRES_ID ,  \n";
        query +=" RECPT_FORM=:RECPT_FORM , DEDCTTRM_START_YRMN=:DEDCTTRM_START_YRMN , CLUB_GBN=:CLUB_GBN , INS_DATE=:INS_DATE , APPTN_DAY=:APPTN_DAY , DEDCTTRM_END_YRMN=:DEDCTTRM_END_YRMN , MOD_DATE=TO_CHAR(SYSDATE,'YYYYMMDD') , APPTN_GBN=:APPTN_GBN , PROG_STAT=:PROG_STAT  \n";
        query +=" where MNG_NUM=:MNG_NUM  \n";
        query +=" and MB_CD=:MB_CD";
        sobj.setSql(query);
        sobj.setString("PROG_STAT", PROG_STAT);               //진행 상태
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        sobj.setString("DEDCTTRM_END_YRMN", DEDCTTRM_END_YRMN);               //공제기간 종료 년월
        sobj.setString("APPTN_DAY", APPTN_DAY);               //신청 일자
        sobj.setString("INS_DATE", INS_DATE);               //등록 일시
        sobj.setString("CLUB_GBN", CLUB_GBN);               //동호회 구분
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("DEDCTTRM_START_YRMN", DEDCTTRM_START_YRMN);               //공제기간 시작 년월
        sobj.setString("RECPT_FORM", RECPT_FORM);               //접수형태
        sobj.setDouble("MNG_NUM", MNG_NUM);               //관리번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setDouble("DEDCT_AMT", DEDCT_AMT);               //공제 금액
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    //##**$$tmem_club_save
    //##**$$tmem_club_del
    /* * 프로그램명 : tac03_s02
    * 작성자 : 이세준
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtmem_club_del(DOBJ dobj)
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
            dobj  = CALLtmem_club_del_DEL1(Conn, dobj);           //  동호회비정보삭제
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
    public DOBJ CTLtmem_club_del( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtmem_club_del_DEL1(Conn, dobj);           //  동호회비정보삭제
        return dobj;
    }
    // 동호회비정보삭제
    // 동호회비정보삭제
    public DOBJ CALLtmem_club_del_DEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("DEL1");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtmem_club_del_DEL1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL1") ;
        rvobj.Println("DEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtmem_club_del_DEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double   MNG_NUM = dvobj.getRecord().getDouble("MNG_NUM");   //관리번호
        String   MB_CD = dvobj.getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TMEM_CLUBEXPSDEDCTBRE  \n";
        query +=" where MB_CD=:MB_CD  \n";
        query +=" and MNG_NUM=:MNG_NUM";
        sobj.setSql(query);
        sobj.setDouble("MNG_NUM", MNG_NUM);               //관리번호
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    //##**$$tmem_club_del
    //##**$$tac03_s02mbselect
    /* * 프로그램명 : tac03_s02
    * 작성자 : 이세준
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtac03_s02mbselect(DOBJ dobj)
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
            dobj  = CALLtac03_s02mbselect_SEL1(Conn, dobj);           //  동호회비공제자조회
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
    public DOBJ CTLtac03_s02mbselect( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtac03_s02mbselect_SEL1(Conn, dobj);           //  동호회비공제자조회
        return dobj;
    }
    // 동호회비공제자조회
    public DOBJ CALLtac03_s02mbselect_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtac03_s02mbselect_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtac03_s02mbselect_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.MB_CD,  A.HANMB_NM,  A.SEX_GBN,  A.INS_NUM,  A.PHON_NUM,  A.ENTRN_DAY,  A.NATY_CD,  A.MB_GBN1,  A.OFFCL_GBN,  B.POST_NUM,  TRIM(B.ADDR||'  '||B.ADDR_DETED)  AS  JUSO  FROM  FIDU.TMEM_MB  A,  FIDU.TMEM_ADBK  B  WHERE  A.MB_CD  =  B.MB_CD   \n";
        query +=" AND  '2'  =  B.ADDR_GBN   \n";
        query +=" AND  A.MB_CD  =  :MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        return sobj;
    }
    //##**$$tac03_s02mbselect
    //##**$$tmem_mb_select
    /* * 프로그램명 : tac03_s02
    * 작성자 : 이세준
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtmem_mb_select(DOBJ dobj)
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
            dobj  = CALLtmem_mb_select_SEL1(Conn, dobj);           //  동호회비조회
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
    public DOBJ CTLtmem_mb_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtmem_mb_select_SEL1(Conn, dobj);           //  동호회비조회
        return dobj;
    }
    // 동호회비조회
    public DOBJ CALLtmem_mb_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtmem_mb_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtmem_mb_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   MB_CD = dobj.getRetObject("S").getRecord().get("MB_CD");   //회원 코드
        String   MB_GBN1 = dobj.getRetObject("S").getRecord().get("MB_GBN1");   //회원 구분1
        String   MB_GBN2 = dobj.getRetObject("S").getRecord().get("MB_GBN2");   //회원 구분2
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DISTINCT  A.MB_CD,  A.HANMB_NM,  A.ENTRN_DAY  FROM  FIDU.TMEM_MB  A  WHERE  A.MB_GBN1  LIKE  :MB_GBN1  ||  '%'   \n";
        query +=" AND  A.MB_GBN2  LIKE  :MB_GBN2  ||  '%'   \n";
        query +=" AND  A.MB_CD  LIKE  :MB_CD||'%'  ORDER  BY  A.HANMB_NM,  A.MB_CD ";
        sobj.setSql(query);
        sobj.setString("MB_CD", MB_CD);               //회원 코드
        sobj.setString("MB_GBN1", MB_GBN1);               //회원 구분1
        sobj.setString("MB_GBN2", MB_GBN2);               //회원 구분2
        return sobj;
    }
    //##**$$tmem_mb_select
    //##**$$end
}
