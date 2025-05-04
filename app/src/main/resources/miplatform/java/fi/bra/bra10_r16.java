
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra10_r16
{
    public bra10_r16()
    {
    }
    //##**$$tracelog_save
    /* * 프로그램명 : bra10_r16
    * 작성자 : 이광노
    * 작성일 : 2009/11/26
    * 설명 : 데이타 조작(insert,delete,update)내역을 저장 한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtracelog_save(DOBJ dobj)
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
            dobj  = CALLtracelog_save_SEL9(Conn, dobj);           //  프로그램이력시퀀스
            dobj  = CALLtracelog_save_MIUD2(Conn, dobj);           //  로그 저장
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
    public DOBJ CTLtracelog_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtracelog_save_SEL9(Conn, dobj);           //  프로그램이력시퀀스
        dobj  = CALLtracelog_save_MIUD2(Conn, dobj);           //  로그 저장
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 프로그램이력시퀀스
    public DOBJ CALLtracelog_save_SEL9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL9");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtracelog_save_SEL9(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL9");
        rvobj.Println("SEL9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtracelog_save_SEL9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  FIDU.TENV_PGM_HISTY_SEQ.NEXTVAL  AS  PGM_HISTY_SEQ  FROM  DUAL ";
        sobj.setSql(query);
        return sobj;
    }
    // 로그 저장
    public DOBJ CALLtracelog_save_MIUD2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD2");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLtracelog_save_SEL10(Conn, dobj);           //  이력확인
                if( dobj.getRetObject("SEL10").getRecord().get("C_CNT").equals("0"))
                {
                    dobj  = CALLtracelog_save_INS8(Conn, dobj);           //  프로그램이력저장
                    dobj  = CALLtracelog_save_INS1(Conn, dobj);           //  로그저장
                }
                else
                {
                    dobj  = CALLtracelog_save_INS1(Conn, dobj);           //  로그저장
                }
            }
        }
        return dobj;
    }
    // 이력확인
    public DOBJ CALLtracelog_save_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtracelog_save_SEL10(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        rvobj.Println("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtracelog_save_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   PGM_HISTY_SEQ = dobj.getRetObject("SEL9").getRecord().get("PGM_HISTY_SEQ");   //프로그램이력일련번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  as  C_CNT  FROM  FIDU.TENV_PGM_HISTY  WHERE  PGM_MNG_NUM  =  :PGM_HISTY_SEQ ";
        sobj.setSql(query);
        sobj.setString("PGM_HISTY_SEQ", PGM_HISTY_SEQ);               //프로그램이력일련번호
        return sobj;
    }
    // 로그저장
    // 프로그램의 변경된 컬럼의 내역을 저장한다.
    public DOBJ CALLtracelog_save_INS1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS1");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtracelog_save_INS1(dobj, dvobj);
            qexe.DispSelectSql(sobj);
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
    private SQLObject SQLtracelog_save_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int  COL_MNG_NUM = 0;        //컬럼 관리번호
        String   CHGATR_CTENT = dvobj.getRecord().get("CHGATR_CTENT");   //변경후 내용
        String   COL_MNG_NUM_1 = dobj.getRetObject("SEL9").getRecord().get("PGM_HISTY_SEQ");   //컬럼 관리번호
        String   RECORD_CNT = dvobj.getRecord().get("RECORD_CNT");
        String   COL_NM = dvobj.getRecord().get("COL_NM");   //컬럼 명
        String   ROW_TYPE = dvobj.getRecord().get("ROW_TYPE");   //ROW TYPE
        String   CHGBFR_CTENT = dvobj.getRecord().get("CHGBFR_CTENT");   //변경전 내용
        int   PGM_MNG_NUM = dobj.getRetObject("SEL9").getRecord().getInt("PGM_HISTY_SEQ");   //프로그램 관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TENV_COL_HISTY (CHGBFR_CTENT, ROW_TYPE, PGM_MNG_NUM, COL_NM, RECORD_CNT, COL_MNG_NUM, CHGATR_CTENT)  \n";
        query +=" values(:CHGBFR_CTENT , :ROW_TYPE , :PGM_MNG_NUM , :COL_NM , :RECORD_CNT , (SELECT NVL(MAX(COL_MNG_NUM),0) + 1 FROM FIDU.TENV_COL_HISTY WHERE PGM_MNG_NUM = :COL_MNG_NUM_1), :CHGATR_CTENT )";
        sobj.setSql(query);
        sobj.setString("CHGATR_CTENT", CHGATR_CTENT);               //변경후 내용
        sobj.setString("COL_MNG_NUM_1", COL_MNG_NUM_1);               //컬럼 관리번호
        sobj.setString("RECORD_CNT", RECORD_CNT);
        sobj.setString("COL_NM", COL_NM);               //컬럼 명
        sobj.setString("ROW_TYPE", ROW_TYPE);               //ROW TYPE
        sobj.setString("CHGBFR_CTENT", CHGBFR_CTENT);               //변경전 내용
        sobj.setInt("PGM_MNG_NUM", PGM_MNG_NUM);               //프로그램 관리번호
        return sobj;
    }
    // 프로그램이력저장
    // 프로그램 이력을 저장한다.
    public DOBJ CALLtracelog_save_INS8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS8");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtracelog_save_INS8(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS8") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtracelog_save_INS8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DAY ="";        //등록 일자
        String INS_TM ="";        //등록시간
        String   STAFF_NUM = dvobj.getRecord().get("STAFF_NUM");   //사원번호
        String   MENU_ID = dvobj.getRecord().get("MENU_ID");   //메뉴 명
        String   IPADDRESS = dvobj.getRecord().get("IPADDRESS");
        String   COMPUTER_NM = dvobj.getRecord().get("COMPUTER_NM");   //컴퓨터 명
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        int   PGM_MNG_NUM = dobj.getRetObject("SEL9").getRecord().getInt("PGM_HISTY_SEQ");   //프로그램 관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TENV_PGM_HISTY (INS_TM, INSPRES_ID, COMPUTER_NM, PGM_MNG_NUM, IPADDRESS, INS_DAY, MENU_ID, STAFF_NUM) values(TO_CHAR(SYSDATE,'HH24MISS'), :INSPRES_ID , :COMPUTER_NM , :PGM_MNG_NUM , :IPADDRESS , TO_CHAR(SYSDATE,'YYYYMMDD'), :MENU_ID , :STAFF_NUM )";
        sobj.setSql(query);
        sobj.setString("STAFF_NUM", STAFF_NUM);               //사원번호
        sobj.setString("MENU_ID", MENU_ID);               //메뉴 명
        sobj.setString("IPADDRESS", IPADDRESS);
        sobj.setString("COMPUTER_NM", COMPUTER_NM);               //컴퓨터 명
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setInt("PGM_MNG_NUM", PGM_MNG_NUM);               //프로그램 관리번호
        return sobj;
    }
    //##**$$tracelog_save
    //##**$$tracelog_search
    /* * 프로그램명 : bra10_r16
    * 작성자 : 이광노
    * 작성일 : 2009/11/09
    * 설명 : 등록된 로그 내역을 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtracelog_search(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("UPSO_CD").equals(""))
            {
                dobj  = CALLtracelog_search_SEL3(Conn, dobj);           //  업소전체조회
                dobj  = CALLtracelog_search_MRG5( dobj);        //  결과취합
            }
            else
            {
                dobj  = CALLtracelog_search_SEL4(Conn, dobj);           //  업소조회
                dobj  = CALLtracelog_search_MRG5( dobj);        //  결과취합
            }
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
    public DOBJ CTLtracelog_search( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("UPSO_CD").equals(""))
        {
            dobj  = CALLtracelog_search_SEL3(Conn, dobj);           //  업소전체조회
            dobj  = CALLtracelog_search_MRG5( dobj);        //  결과취합
        }
        else
        {
            dobj  = CALLtracelog_search_SEL4(Conn, dobj);           //  업소조회
            dobj  = CALLtracelog_search_MRG5( dobj);        //  결과취합
        }
        return dobj;
    }
    // 업소전체조회
    public DOBJ CALLtracelog_search_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtracelog_search_SEL3(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtracelog_search_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ROW_TYPE = dobj.getRetObject("S").getRecord().get("ROW_TYPE");   //ROW TYPE
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   COL_NM = dobj.getRetObject("S").getRecord().get("COL_NM");   //컬럼 명
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   MENU_ID = dobj.getRetObject("S").getRecord().get("MENU_ID");   //메뉴 명
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   STAFF_NUM = dobj.getRetObject("S").getRecord().get("STAFF_NUM");   //사원번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.PGM_MNG_NUM,  C.UPSO_CD,  G.UPSO_NM,  A.MENU_ID,  F.MENU_NM,  A.INSPRES_ID,  A.STAFF_NUM,  E.HAN_NM,  A.INS_DAY,  A.INS_TM,  B.COL_NM,  DECODE(D.LABEL,NULL,B.COL_NM,  D.LABEL)  LABEL,  B.CHGBFR_CTENT,  B.CHGATR_CTENT,  A.IPADDRESS,  A.COMPUTER_NM,B.ROW_TYPE  ,  B.RECORD_CNT  FROM  FIDU.TENV_PGM_HISTY  A,  FIDU.TENV_COL_HISTY  B,   \n";
        query +=" (SELECT  DISTINCT  A.PGM_MNG_NUM,  A.CHGBFR_CTENT  UPSO_CD  ,  A.RECORD_CNT  FROM  FIDU.TENV_COL_HISTY  A  ,  FIDU.TENV_PGM_HISTY  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.COL_NM  =  'UPSO_CD'   \n";
        query +=" AND  A.CHGBFR_CTENT  LIKE  :UPSO_CD  ||  '%'   \n";
        query +=" AND  C.BRAN_CD  LIKE  :BRAN_CD  ||  '%'   \n";
        query +=" AND  A.CHGBFR_CTENT  =  C.UPSO_CD   \n";
        query +=" AND  A.PGM_MNG_NUM  =  B.PGM_MNG_NUM   \n";
        query +=" AND  B.INS_DAY  BETWEEN  :FROMDATE   \n";
        query +=" AND  :TODATE   \n";
        query +=" AND  B.STAFF_NUM  LIKE  :STAFF_NUM  ||  '%'   \n";
        query +=" AND  B.MENU_ID  LIKE  :MENU_ID  ||  '%'   \n";
        query +=" AND  A.ROW_TYPE  LIKE  :ROW_TYPE  ||'%')  C,  ADSH.PDD01ZT  D,  INSA.TINS_MST01  E,   \n";
        query +=" (SELECT  SUBSTR(REPLACE(MENU_PATH,'.xml',''),INSTR(MENU_PATH,'::')+2,LENGTH(MENU_PATH))  MENU_ID,  MENU_NM  FROM  FIDU.TENV_MENU  WHERE  MENU_PATH  IS  NOT  NULL  )  F,  GIBU.TBRA_UPSO  G  WHERE  A.PGM_MNG_NUM  =  B.PGM_MNG_NUM   \n";
        query +=" AND  A.PGM_MNG_NUM  =  C.PGM_MNG_NUM   \n";
        query +=" AND  B.RECORD_CNT  =  C.RECORD_CNT   \n";
        query +=" AND  B.COL_NM  =  D.COLNM(+)   \n";
        query +=" AND  A.STAFF_NUM  =  E.STAFF_NUM(+)   \n";
        query +=" AND  A.MENU_ID  =  F.MENU_ID(+)   \n";
        query +=" AND  C.UPSO_CD  =  G.UPSO_CD(+)   \n";
        query +=" AND  A.INS_DAY  BETWEEN  :FROMDATE   \n";
        query +=" AND  :TODATE   \n";
        query +=" AND  A.STAFF_NUM  LIKE  :STAFF_NUM  ||  '%'   \n";
        query +=" AND  A.MENU_ID  LIKE  :MENU_ID  ||  '%'   \n";
        query +=" AND  B.ROW_TYPE  LIKE  :ROW_TYPE  ||'%'   \n";
        query +=" AND  B.COL_NM  LIKE  '%'||  :COL_NM  ||  '%'  UNION  ALL   \n";
        query +=" SELECT  A.PGM_MNG_NUM,  '00000000'  UPSO_CD,''  UPSO_NM  ,  A.MENU_ID,  F.MENU_NM,  A.INSPRES_ID,  A.STAFF_NUM,  E.HAN_NM,  A.INS_DAY,  A.INS_TM,  B.COL_NM,  DECODE(D.LABEL,NULL,B.COL_NM,  D.LABEL)  LABEL,  B.CHGBFR_CTENT,  B.CHGATR_CTENT,  A.IPADDRESS,  A.COMPUTER_NM,B.ROW_TYPE  ,  B.RECORD_CNT  FROM  FIDU.TENV_PGM_HISTY  A,  FIDU.TENV_COL_HISTY  B,   \n";
        query +=" (SELECT  DISTINCT  A.PGM_MNG_NUM  FROM  FIDU.TENV_COL_HISTY  A  ,  FIDU.TENV_PGM_HISTY  B  WHERE  A.PGM_MNG_NUM  NOT  IN  (   \n";
        query +=" SELECT  DISTINCT  A.PGM_MNG_NUM  FROM  FIDU.TENV_COL_HISTY  A  ,  FIDU.TENV_PGM_HISTY  B  WHERE  A.COL_NM  =  'UPSO_CD'   \n";
        query +=" AND  A.CHGBFR_CTENT  LIKE  '%'   \n";
        query +=" AND  A.PGM_MNG_NUM  =  B.PGM_MNG_NUM   \n";
        query +=" AND  B.INS_DAY  BETWEEN  :FROMDATE   \n";
        query +=" AND  :TODATE   \n";
        query +=" AND  B.STAFF_NUM  LIKE  :STAFF_NUM  ||  '%'   \n";
        query +=" AND  B.MENU_ID  LIKE  :MENU_ID  ||  '%'   \n";
        query +=" AND  A.ROW_TYPE  LIKE  :ROW_TYPE  ||'%')   \n";
        query +=" AND  A.PGM_MNG_NUM  =  B.PGM_MNG_NUM   \n";
        query +=" AND  B.INS_DAY  BETWEEN  :FROMDATE   \n";
        query +=" AND  :TODATE   \n";
        query +=" AND  B.STAFF_NUM  LIKE  '%'  ||  :STAFF_NUM  ||  '%'   \n";
        query +=" AND  B.MENU_ID  LIKE  :MENU_ID  ||  '%'   \n";
        query +=" AND  A.ROW_TYPE  LIKE  :ROW_TYPE  ||'%'  )  C,  ADSH.PDD01ZT  D,  INSA.TINS_MST01  E,   \n";
        query +=" (SELECT  SUBSTR(REPLACE(MENU_PATH,'.xml',''),INSTR(MENU_PATH,'::')+2,LENGTH(MENU_PATH))  MENU_ID,  MENU_NM  FROM  FIDU.TENV_MENU  WHERE  MENU_PATH  IS  NOT  NULL  )  F  WHERE  A.PGM_MNG_NUM  =  B.PGM_MNG_NUM   \n";
        query +=" AND  A.PGM_MNG_NUM  =  C.PGM_MNG_NUM   \n";
        query +=" AND  B.COL_NM  =  D.COLNM(+)   \n";
        query +=" AND  A.STAFF_NUM  =  E.STAFF_NUM(+)   \n";
        query +=" AND  A.MENU_ID  =  F.MENU_ID(+)   \n";
        query +=" AND  A.INS_DAY  BETWEEN  :FROMDATE   \n";
        query +=" AND  :TODATE   \n";
        query +=" AND  A.STAFF_NUM  LIKE  :STAFF_NUM  ||  '%'   \n";
        query +=" AND  A.MENU_ID  LIKE  :MENU_ID  ||  '%'   \n";
        query +=" AND  B.ROW_TYPE  LIKE  :ROW_TYPE  ||'%'   \n";
        query +=" AND  B.COL_NM  LIKE  '%'||  :COL_NM  ||  '%'  ORDER  BY  INS_DAY,  INS_TM,  UPSO_NM,  ROW_TYPE,MENU_NM,  RECORD_CNT,  LABEL ";
        sobj.setSql(query);
        sobj.setString("ROW_TYPE", ROW_TYPE);               //ROW TYPE
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("COL_NM", COL_NM);               //컬럼 명
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MENU_ID", MENU_ID);               //메뉴 명
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("STAFF_NUM", STAFF_NUM);               //사원번호
        return sobj;
    }
    // 결과취합
    public DOBJ CALLtracelog_search_MRG5(DOBJ dobj) throws Exception
    {
        dobj.setRtnname("MRG5");
        WizUtil wutil = new WizUtil(dobj);
        VOBJ       rvobj= null;
        rvobj = wutil.getMergeObject(dobj,"SEL4, SEL3","");
        rvobj.setName("MRG5") ;
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    // 업소조회
    public DOBJ CALLtracelog_search_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtracelog_search_SEL4(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtracelog_search_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ROW_TYPE = dobj.getRetObject("S").getRecord().get("ROW_TYPE");   //ROW TYPE
        String   TODATE = dobj.getRetObject("S").getRecord().get("TODATE");   //종료일자
        String   FROMDATE = dobj.getRetObject("S").getRecord().get("FROMDATE");   //시작일자
        String   COL_NM = dobj.getRetObject("S").getRecord().get("COL_NM");   //컬럼 명
        String   UPSO_CD = dobj.getRetObject("S").getRecord().get("UPSO_CD");   //업소 코드
        String   MENU_ID = dobj.getRetObject("S").getRecord().get("MENU_ID");   //메뉴 명
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   STAFF_NUM = dobj.getRetObject("S").getRecord().get("STAFF_NUM");   //사원번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.PGM_MNG_NUM,  C.UPSO_CD,  G.UPSO_NM,  A.MENU_ID,  F.MENU_NM,  A.INSPRES_ID,  A.STAFF_NUM,  E.HAN_NM,  A.INS_DAY,  A.INS_TM,  B.COL_NM,  DECODE(D.LABEL,NULL,B.COL_NM,  D.LABEL)  LABEL,  B.CHGBFR_CTENT,  B.CHGATR_CTENT,  A.IPADDRESS,  A.COMPUTER_NM,B.ROW_TYPE  ,  B.RECORD_CNT  FROM  FIDU.TENV_PGM_HISTY  A,  FIDU.TENV_COL_HISTY  B,   \n";
        query +=" (SELECT  DISTINCT  A.PGM_MNG_NUM,  A.CHGBFR_CTENT  UPSO_CD  ,  A.RECORD_CNT  FROM  FIDU.TENV_COL_HISTY  A  ,  FIDU.TENV_PGM_HISTY  B  ,  GIBU.TBRA_UPSO  C  WHERE  A.COL_NM  =  'UPSO_CD'   \n";
        query +=" AND  A.CHGBFR_CTENT  LIKE  :UPSO_CD  ||  '%'   \n";
        query +=" AND  C.BRAN_CD  LIKE  :BRAN_CD  ||  '%'   \n";
        query +=" AND  A.CHGBFR_CTENT  =  C.UPSO_CD   \n";
        query +=" AND  A.PGM_MNG_NUM  =  B.PGM_MNG_NUM   \n";
        query +=" AND  B.INS_DAY  BETWEEN  :FROMDATE   \n";
        query +=" AND  :TODATE   \n";
        query +=" AND  B.STAFF_NUM  LIKE  :STAFF_NUM  ||  '%'   \n";
        query +=" AND  B.MENU_ID  LIKE  :MENU_ID  ||  '%'   \n";
        query +=" AND  A.ROW_TYPE  LIKE  :ROW_TYPE  ||'%')  C,  ADSH.PDD01ZT  D,  INSA.TINS_MST01  E,   \n";
        query +=" (SELECT  SUBSTR(REPLACE(MENU_PATH,'.xml',''),INSTR(MENU_PATH,'::')+2,LENGTH(MENU_PATH))  MENU_ID,  MENU_NM  FROM  FIDU.TENV_MENU  WHERE  MENU_PATH  IS  NOT  NULL  )  F,  GIBU.TBRA_UPSO  G  WHERE  A.PGM_MNG_NUM  =  B.PGM_MNG_NUM   \n";
        query +=" AND  A.PGM_MNG_NUM  =  C.PGM_MNG_NUM   \n";
        query +=" AND  B.RECORD_CNT  =  C.RECORD_CNT   \n";
        query +=" AND  B.COL_NM  =  D.COLNM(+)   \n";
        query +=" AND  A.STAFF_NUM  =  E.STAFF_NUM(+)   \n";
        query +=" AND  A.MENU_ID  =  F.MENU_ID(+)   \n";
        query +=" AND  C.UPSO_CD  =  G.UPSO_CD(+)   \n";
        query +=" AND  A.INS_DAY  BETWEEN  :FROMDATE   \n";
        query +=" AND  :TODATE   \n";
        query +=" AND  A.STAFF_NUM  LIKE  :STAFF_NUM  ||  '%'   \n";
        query +=" AND  A.MENU_ID  LIKE  :MENU_ID  ||  '%'   \n";
        query +=" AND  B.ROW_TYPE  LIKE  :ROW_TYPE  ||'%'   \n";
        query +=" AND  B.COL_NM  LIKE  '%'||  :COL_NM  ||  '%'  ORDER  BY  INS_DAY,  INS_TM,  UPSO_NM,  ROW_TYPE,MENU_NM,  RECORD_CNT,  LABEL ";
        sobj.setSql(query);
        sobj.setString("ROW_TYPE", ROW_TYPE);               //ROW TYPE
        sobj.setString("TODATE", TODATE);               //종료일자
        sobj.setString("FROMDATE", FROMDATE);               //시작일자
        sobj.setString("COL_NM", COL_NM);               //컬럼 명
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MENU_ID", MENU_ID);               //메뉴 명
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("STAFF_NUM", STAFF_NUM);               //사원번호
        return sobj;
    }
    //##**$$tracelog_search
    //##**$$tracelog_upso_delete
    /* * 프로그램명 : bra10_r16
    * 작성자 : 이광노
    * 작성일 : 2009/11/09
    * 설명 : 사용안함
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLtracelog_upso_delete(DOBJ dobj)
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
            dobj  = CALLtracelog_upso_delete_SEL1(Conn, dobj);           //  로그번호조회
            dobj  = CALLtracelog_upso_delete_DEL2(Conn, dobj);           //  로그마스터삭제
            dobj  = CALLtracelog_upso_delete_DEL3(Conn, dobj);           //  로그디테일삭제
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
    public DOBJ CTLtracelog_upso_delete( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLtracelog_upso_delete_SEL1(Conn, dobj);           //  로그번호조회
        dobj  = CALLtracelog_upso_delete_DEL2(Conn, dobj);           //  로그마스터삭제
        dobj  = CALLtracelog_upso_delete_DEL3(Conn, dobj);           //  로그디테일삭제
        return dobj;
    }
    // 로그번호조회
    public DOBJ CALLtracelog_upso_delete_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLtracelog_upso_delete_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLtracelog_upso_delete_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.PGM_MNG_NUM  FROM  TENV_PGM_HISTY  A,  TENV_COL_HISTY  B  WHERE  A.PGM_MNG_NUM  =  B.PGM_MNG_NUM   \n";
        query +=" AND  B.COL_NM  =  'UPSO_CD'   \n";
        query +=" AND  A.MENU_ID  =  'bra01_s01'   \n";
        query +=" AND  B.CHGBFR_CTENT  IS  NULL ";
        sobj.setSql(query);
        return sobj;
    }
    // 로그마스터삭제
    public DOBJ CALLtracelog_upso_delete_DEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL2");
        VOBJ dvobj = dobj.getRetObject("SEL1");           //로그번호조회에서 생성시킨 OBJECT입니다.(CALLtracelog_upso_delete_SEL1)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtracelog_upso_delete_DEL2(dobj, dvobj);
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
    private SQLObject SQLtracelog_upso_delete_DEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int   PGM_MNG_NUM = dobj.getRetObject("SEL1").getRecord().getInt("PGM_MNG_NUM");   //프로그램 관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TENV_PGM_HISTY  \n";
        query +=" where PGM_MNG_NUM=:PGM_MNG_NUM";
        sobj.setSql(query);
        sobj.setInt("PGM_MNG_NUM", PGM_MNG_NUM);               //프로그램 관리번호
        return sobj;
    }
    // 로그디테일삭제
    public DOBJ CALLtracelog_upso_delete_DEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL3");
        VOBJ dvobj = dobj.getRetObject("SEL1");           //로그번호조회에서 생성시킨 OBJECT입니다.(CALLtracelog_upso_delete_SEL1)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLtracelog_upso_delete_DEL3(dobj, dvobj);
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
    private SQLObject SQLtracelog_upso_delete_DEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        int   PGM_MNG_NUM = dobj.getRetObject("SEL1").getRecord().getInt("PGM_MNG_NUM");   //프로그램 관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU.TENV_COL_HISTY  \n";
        query +=" where PGM_MNG_NUM=:PGM_MNG_NUM";
        sobj.setSql(query);
        sobj.setInt("PGM_MNG_NUM", PGM_MNG_NUM);               //프로그램 관리번호
        return sobj;
    }
    //##**$$tracelog_upso_delete
    //##**$$end
}
