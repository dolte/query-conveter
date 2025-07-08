
package komca.fi.tac;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class tac01_s01
{
    public tac01_s01()
    {
    }
    //##**$$bill_save
    /* * 프로그램명 : tac01_s01
    * 작성자 : 송유빈
    * 작성일 : 2009/08/04
    * 설명 : 저작물 사용승인 건 중 입관완료된 건에 대해 계산서 발행을 한다
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbill_save(DOBJ dobj)
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
            dobj  = CALLbill_save_SEL1(Conn, dobj);           //  계산서번호 발생
            dobj  = CALLbill_save_INS9(Conn, dobj);           //  계산서 기본정보 입력
            dobj  = CALLbill_save_MIUD5(Conn, dobj);           //  입금내역MIUD
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLbill_save_MIUD17(Conn, dobj);           //  회계전표연계MIUD
            if(dobj.getRtncode() == 9)
            {
                Conn.rollback();
                return dobj;
            }
            dobj  = CALLbill_save_UPD33(Conn, dobj);           //  발생금액 등록
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
    public DOBJ CTLbill_save( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbill_save_SEL1(Conn, dobj);           //  계산서번호 발생
        dobj  = CALLbill_save_INS9(Conn, dobj);           //  계산서 기본정보 입력
        dobj  = CALLbill_save_MIUD5(Conn, dobj);           //  입금내역MIUD
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLbill_save_MIUD17(Conn, dobj);           //  회계전표연계MIUD
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        dobj  = CALLbill_save_UPD33(Conn, dobj);           //  발생금액 등록
        return dobj;
    }
    // 계산서번호 발생
    // 계산서번호 발생
    public DOBJ CALLbill_save_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbill_save_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbill_save_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TO_CHAR(SYSDATE,  'YYYYMM')||  LPAD(TO_CHAR(NVL(MAX(TO_NUMBER(SUBSTR(BILL_NUM,  7,  10))  +  1),  1)),  4,  '0')  AS  BILL_NUM_MAX  FROM  FIDU.TTAC_BILL  WHERE  SUBSTR(BILL_NUM,  1,  6)  =  TO_CHAR(SYSDATE,  'YYYYMM') ";
        sobj.setSql(query);
        return sobj;
    }
    // 계산서 기본정보 입력
    // 계산서 기본정보 입력
    public DOBJ CALLbill_save_INS9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbill_save_INS9(dobj, dvobj);
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
    private SQLObject SQLbill_save_INS9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   ISS_DAY = dvobj.getRecord().get("ISS_DAY");   //발행 일자
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   BILL_NUM = dobj.getRetObject("SEL1").getRecord().get("BILL_NUM_MAX");   //계산서 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_BILL (INS_DATE, BILL_NUM, INSPRES_ID, BILL_GBN, ISS_DAY, BSCON_CD)  \n";
        query +=" values(SYSDATE, :BILL_NUM , :INSPRES_ID , :BILL_GBN , :ISS_DAY , :BSCON_CD )";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        return sobj;
    }
    // 입금내역MIUD
    // 입금내역MIUD
    public DOBJ CALLbill_save_MIUD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD5");
        VOBJ dvobj = dobj.getRetObject("A");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbill_save_INS12(Conn, dobj);           //  입금내역등록
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbill_save_UPD13(Conn, dobj);           //  입금내역수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbill_save_DEL14(Conn, dobj);           //  입금내역삭제
            }
        }
        return dobj;
    }
    // 입금내역삭제
    // 입금내역삭제
    public DOBJ CALLbill_save_DEL14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL14");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbill_save_DEL14(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL14") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbill_save_DEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");        //계산서 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from FIDU._USEAPPRVBRE  \n";
        query +=" where";
        sobj.setSql(query);
        return sobj;
    }
    // 입금내역등록
    // 입금내역등록
    public DOBJ CALLbill_save_INS12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbill_save_INS12(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbill_save_INS12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //승인 번호
        String   TITLE_NM = dvobj.getRecord().get("TITLE_NM");   //타이틀 명
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //매체 코드
        String   INSPRES_ID = dvobj.getRecord().get("INSPRES_ID");   //등록자 ID
        String   BILL_NUM = dobj.getRetObject("SEL1").getRecord().get("BILL_NUM_MAX");   //계산서 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_USEAPPRVBRE (INS_DATE, BILL_NUM, INSPRES_ID, MDM_CD, TITLE_NM, APPRV_NUM)  \n";
        query +=" values(SYSDATE, :BILL_NUM , :INSPRES_ID , :MDM_CD , :TITLE_NM , :APPRV_NUM )";
        sobj.setSql(query);
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("TITLE_NM", TITLE_NM);               //타이틀 명
        sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        return sobj;
    }
    // 입금내역수정
    // 입금내역수정
    public DOBJ CALLbill_save_UPD13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD13");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbill_save_UPD13(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD13") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbill_save_UPD13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");        //계산서 번호
        String MOD_DATE ="";        //수정 일시
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU._USEAPPRVBRE  \n";
        query +=" set  \n";
        query +=" where";
        sobj.setSql(query);
        return sobj;
    }
    // 회계전표연계MIUD
    public DOBJ CALLbill_save_MIUD17(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD17");
        VOBJ dvobj = dobj.getRetObject("SEL1");            //계산서번호 발생에서 생성시킨 OBJECT입니다.(CALLbill_save_SEL1)
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
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
            }
        }
        return dobj;
    }
    // 발생금액 등록
    // 발생금액 등록
    public DOBJ CALLbill_save_UPD33(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD33");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbill_save_UPD33(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD33") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbill_save_UPD33(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        double OCC_AMT = 0;        //발생 금액
        String   BILL_NUM = dobj.getRetObject("SEL1").getRecord().get("BILL_NUM_MAX");   //계산서 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_BILL  \n";
        query +=" set  \n";
        query +=" where BILL_NUM=:BILL_NUM";
        sobj.setSql(query);
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        return sobj;
    }
    //##**$$bill_save
    //##**$$bill_det_select
    /* * 프로그램명 : tac01_s01
    * 작성자 : 송유빈
    * 작성일 : 2009/08/05
    * 설명 : 계산서 상세정보를 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbill_det_select(DOBJ dobj)
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
            dobj  = CALLbill_det_select_SEL1(Conn, dobj);           //  계산서내역조회
            dobj  = CALLbill_det_select_SEL2(Conn, dobj);           //  입금내역조회
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
    public DOBJ CTLbill_det_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbill_det_select_SEL1(Conn, dobj);           //  계산서내역조회
        dobj  = CALLbill_det_select_SEL2(Conn, dobj);           //  입금내역조회
        return dobj;
    }
    // 계산서내역조회
    // 계산서내역조회
    public DOBJ CALLbill_det_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbill_det_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbill_det_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BILL_NUM, BILL_GBN, BSCON_CD, FIDU.GET_BSCON_NM(BSCON_CD) AS BSCON_NM, OCC_AMT, OCC_DAY, OCC_CTENT, ISS_DAY, ISS_CTENT, INSPRES_ID, INS_DATE, MODPRES_ID, MOD_DATE  ";
        query +=" FROM FIDU.TTAC_BILL  ";
        query +=" WHERE 1 = 1  ";
        if( !BILL_NUM.equals("") )
        {
            query +=" AND BILL_NUM = :BILL_NUM  ";
        }
        sobj.setSql(query);
        if(!BILL_NUM.equals(""))
        {
            sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        }
        return sobj;
    }
    // 입금내역조회
    // 입금내역조회
    public DOBJ CALLbill_det_select_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbill_det_select_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbill_det_select_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BILL_NUM = dobj.getRetObject("S").getRecord().get("BILL_NUM");   //계산서 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT BILL_NUM, APPRV_NUM, MDM_CD, FIDU.GET_MDM_NM(MDM_CD) AS MDM_NM, TITLE_NM, QTY, BILL_AMT, STAMP_ID, STAMP_NUM, INSPRES_ID, INS_DATE, MODPRES_ID, MOD_DATE  ";
        query +=" FROM FIDU.TTAC_USEAPPRVBRE  ";
        query +=" WHERE 1 = 1  ";
        if( !BILL_NUM.equals("") )
        {
            query +=" AND BILL_NUM = :BILL_NUM  ";
        }
        sobj.setSql(query);
        if(!BILL_NUM.equals(""))
        {
            sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        }
        return sobj;
    }
    //##**$$bill_det_select
    //##**$$bill_update
    /* * 프로그램명 : tac01_s01
    * 작성자 : 이세준
    * 작성일 : 2010/05/10
    * 설명 : 계산서를 수정한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbill_update(DOBJ dobj)
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
            dobj  = CALLbill_update_UPD9(Conn, dobj);           //  계산서수정
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
    public DOBJ CTLbill_update( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbill_update_UPD9(Conn, dobj);           //  계산서수정
        return dobj;
    }
    // 계산서수정
    // 계산서수정
    public DOBJ CALLbill_update_UPD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD9");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("UPD9");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbill_update_UPD9(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD9") ;
        rvobj.Println("UPD9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbill_update_UPD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   SUPPPRES_REPPRES_NM = dvobj.getRecord().get("SUPPPRES_REPPRES_NM");   //공급자 대표자 명
        String   BSCON_FNM_NM = dvobj.getRecord().get("BSCON_FNM_NM");   //거래처 상호 명
        String   BSCON_BSCDTN = dvobj.getRecord().get("BSCON_BSCDTN");   //거래처업태
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   BSCON_BSTYP = dvobj.getRecord().get("BSCON_BSTYP");   //거래처업종
        String   SUPPPRES_BSTYP = dvobj.getRecord().get("SUPPPRES_BSTYP");   //공급자업종
        String   BSCON_INS_NUM = dvobj.getRecord().get("BSCON_INS_NUM");   //거래처 등록 번호
        String   BSCON_REPPRES_NM = dvobj.getRecord().get("BSCON_REPPRES_NM");   //거래처 대표자 명
        double   SUPPTEMP_AMT = dvobj.getRecord().getDouble("SUPPTEMP_AMT");   //공급가 액
        String   BILL_NUM = dvobj.getRecord().get("BILL_NUM");   //계산서 번호
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //청구서번호
        String   SUPPPRES_INS_NUM = dvobj.getRecord().get("SUPPPRES_INS_NUM");   //공급자 등록 번호
        double   ATAX_AMT = dvobj.getRecord().getDouble("ATAX_AMT");   //부가세 금액
        String   BSCON_ADDR = dvobj.getRecord().get("BSCON_ADDR");   //거래처 주소
        String   SUPPPRES_FNM_NM = dvobj.getRecord().get("SUPPPRES_FNM_NM");   //공급자 상호 명
        String   SUPPPRES_ADDR = dvobj.getRecord().get("SUPPPRES_ADDR");   //공급자 주소
        String   BSCON_POST_NUM = dvobj.getRecord().get("BSCON_POST_NUM");   //거래처 우편 번호
        String   MODPRES_ID = dvobj.getRecord().get("MODPRES_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_BILL  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , BSCON_POST_NUM=:BSCON_POST_NUM , SUPPPRES_ADDR=:SUPPPRES_ADDR , SUPPPRES_FNM_NM=:SUPPPRES_FNM_NM , BSCON_ADDR=:BSCON_ADDR , ATAX_AMT=:ATAX_AMT , SUPPPRES_INS_NUM=:SUPPPRES_INS_NUM , SUPPTEMP_AMT=:SUPPTEMP_AMT , BSCON_REPPRES_NM=:BSCON_REPPRES_NM , BSCON_INS_NUM=:BSCON_INS_NUM , MOD_DATE=SYSDATE , SUPPPRES_BSTYP=:SUPPPRES_BSTYP , BSCON_BSTYP=:BSCON_BSTYP , REMAK=:REMAK , BSCON_BSCDTN=:BSCON_BSCDTN , BSCON_FNM_NM=:BSCON_FNM_NM , SUPPPRES_REPPRES_NM=:SUPPPRES_REPPRES_NM  \n";
        query +=" where DEMD_NUM=:DEMD_NUM  \n";
        query +=" and BILL_NUM=:BILL_NUM";
        sobj.setSql(query);
        sobj.setString("SUPPPRES_REPPRES_NM", SUPPPRES_REPPRES_NM);               //공급자 대표자 명
        sobj.setString("BSCON_FNM_NM", BSCON_FNM_NM);               //거래처 상호 명
        sobj.setString("BSCON_BSCDTN", BSCON_BSCDTN);               //거래처업태
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("BSCON_BSTYP", BSCON_BSTYP);               //거래처업종
        sobj.setString("SUPPPRES_BSTYP", SUPPPRES_BSTYP);               //공급자업종
        sobj.setString("BSCON_INS_NUM", BSCON_INS_NUM);               //거래처 등록 번호
        sobj.setString("BSCON_REPPRES_NM", BSCON_REPPRES_NM);               //거래처 대표자 명
        sobj.setDouble("SUPPTEMP_AMT", SUPPTEMP_AMT);               //공급가 액
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("SUPPPRES_INS_NUM", SUPPPRES_INS_NUM);               //공급자 등록 번호
        sobj.setDouble("ATAX_AMT", ATAX_AMT);               //부가세 금액
        sobj.setString("BSCON_ADDR", BSCON_ADDR);               //거래처 주소
        sobj.setString("SUPPPRES_FNM_NM", SUPPPRES_FNM_NM);               //공급자 상호 명
        sobj.setString("SUPPPRES_ADDR", SUPPPRES_ADDR);               //공급자 주소
        sobj.setString("BSCON_POST_NUM", BSCON_POST_NUM);               //거래처 우편 번호
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    //##**$$bill_update
    //##**$$bill_delete
    /* * 프로그램명 : tac01_s01
    * 작성자 : 이세준
    * 작성일 : 2010/05/10
    * 설명 : 계산서를 삭제한다
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbill_delete(DOBJ dobj)
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
            dobj  = CALLbill_delete_UPD3(Conn, dobj);           //  계산서수정
            dobj  = CALLbill_delete_UPD10(Conn, dobj);           //  승인내역삭제
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
    public DOBJ CTLbill_delete( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbill_delete_UPD3(Conn, dobj);           //  계산서수정
        dobj  = CALLbill_delete_UPD10(Conn, dobj);           //  승인내역삭제
        return dobj;
    }
    // 계산서수정
    // 계산서수정
    public DOBJ CALLbill_delete_UPD3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("UPD3");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbill_delete_UPD3(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD3") ;
        rvobj.Println("UPD3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbill_delete_UPD3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   BILL_NUM = dvobj.getRecord().get("BILL_NUM");   //계산서 번호
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //청구서번호
        String   DEL_YN ="Y";   //삭제여부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_BILL  \n";
        query +=" set DEL_YN=:DEL_YN  \n";
        query +=" where DEMD_NUM=:DEMD_NUM  \n";
        query +=" and BILL_NUM=:BILL_NUM";
        sobj.setSql(query);
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("DEL_YN", DEL_YN);               //삭제여부
        return sobj;
    }
    // 승인내역삭제
    // 승인내역삭제
    public DOBJ CALLbill_delete_UPD10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("UPD10");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbill_delete_UPD10(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD10") ;
        rvobj.Println("UPD10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbill_delete_UPD10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   BILL_NUM = dvobj.getRecord().get("BILL_NUM");   //계산서 번호
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //청구서번호
        String   DEL_YN ="Y";   //삭제여부
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update FIDU.TTAC_USEAPPRVBRE  \n";
        query +=" set DEL_YN=:DEL_YN  \n";
        query +=" where DEMD_NUM=:DEMD_NUM  \n";
        query +=" and BILL_NUM=:BILL_NUM";
        sobj.setSql(query);
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("DEL_YN", DEL_YN);               //삭제여부
        return sobj;
    }
    //##**$$bill_delete
    //##**$$createBill01tac_s01
    /* * 프로그램명 : tac01_s01
    * 작성자 : 손주환
    * 작성일 : 2009/11/27
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLcreateBilltac01_s01(DOBJ dobj)
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
            dobj  = CALLcreateBill01tac_s01_XIUD7(Conn, dobj);           //  계산서 삭제
            dobj  = CALLcreateBill01tac_s01_XIUD9(Conn, dobj);           //  사용승인내역 삭제
            dobj  = CALLcreateBill01tac_s01_SEL2(Conn, dobj);           //  계산서 쿼리1
            dobj  = CALLcreateBill01tac_s01_MPD7(Conn, dobj);           //  분기
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
    public DOBJ CTLcreateBilltac01_s01( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLcreateBill01tac_s01_XIUD7(Conn, dobj);           //  계산서 삭제
        dobj  = CALLcreateBill01tac_s01_XIUD9(Conn, dobj);           //  사용승인내역 삭제
        dobj  = CALLcreateBill01tac_s01_SEL2(Conn, dobj);           //  계산서 쿼리1
        dobj  = CALLcreateBill01tac_s01_MPD7(Conn, dobj);           //  분기
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 계산서 삭제
    public DOBJ CALLcreateBill01tac_s01_XIUD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD7");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s01_XIUD7(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s01_XIUD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM FIDU.TTAC_BILL  \n";
        query +=" WHERE SUBSTR(BILL_NUM,1,6) LIKE :DEMD_DAY||'%'  \n";
        query +=" AND BILL_KND IN(1,2)";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        return sobj;
    }
    // 사용승인내역 삭제
    public DOBJ CALLcreateBill01tac_s01_XIUD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD9");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s01_XIUD9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s01_XIUD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM FIDU.TTAC_USEAPPRVBRE  \n";
        query +=" WHERE SUBSTR(BILL_NUM,1,6) LIKE :DEMD_DAY||'%'  \n";
        query +=" AND BILL_KND IN(1,2)";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        return sobj;
    }
    // 계산서 쿼리1
    public DOBJ CALLcreateBill01tac_s01_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcreateBill01tac_s01_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s01_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_YYMN = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //DEMD_YYMN
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DEMD_NUM,  BILL_KND,  BILL_GBN,  DEMD_DAY,  BRAN_CD,  BSCON_CD,  BSCON_NM,  BSCON_INS_NUM,  BSCON_FNM_NM,  BSCON_REPPRES_NM,  BSCON_POST_NUM,  BSCON_ADDR,  BSCON_ADDR_DETED,  BSCON_BSTYP_CTENT,  BSCON_BSCDTN_CTENT,  SUPPPRES_CD,  SUPPPRES_NM,  SUPPPRES_INS_NUM,  SUPPPRES_FNM_NM,  SUPPPRES_REPPRES_NM,  SUPPPRES_POST_NUM,  SUPPPRES_ADDR,  SUPPPRES_ADDR_DETED,  SUPPPRES_BSTYP_CTENT,  SUPPPRES_BSCDTN_CTENT,  OCC_AMT,  ATAX_AMT,  BILL_ISSREQ_YN,  ACCTNCHIT_ISS_YN  FROM  (   \n";
        query +=" SELECT  TO_CHAR(A.DEMD_NUM)  AS  DEMD_NUM,  '1'  AS  BILL_KND,  (CASE  WHEN  A.REPT_AMT  >  0  THEN  '1'  ELSE  '2'  END)  AS  BILL_GBN,  A.DEMD_DAY  AS  DEMD_DAY,  A.BRAN_CD  AS  BRAN_CD,  B.BSCON_CD  AS  BSCON_CD,  B.BSCONHAN_NM  AS  BSCON_NM,  B.INS_NUM  AS  BSCON_INS_NUM,  B.BSCONHAN_NM  AS  BSCON_FNM_NM,  B.REPPRES_NM  AS  BSCON_REPPRES_NM,  B.POST_NUM  AS  BSCON_POST_NUM,  B.ADDR  AS  BSCON_ADDR,  B.ADDR_DETED  AS  BSCON_ADDR_DETED,  B.BSTYP_CTENT  AS  BSCON_BSTYP_CTENT,  B.BSCDTN_CTENT  AS  BSCON_BSCDTN_CTENT,  C.BSCON_CD  AS  SUPPPRES_CD,  C.BSCONHAN_NM  AS  SUPPPRES_NM,  C.INS_NUM  AS  SUPPPRES_INS_NUM,  C.BSCONHAN_NM  AS  SUPPPRES_FNM_NM,  C.REPPRES_NM  AS  SUPPPRES_REPPRES_NM,  C.POST_NUM  AS  SUPPPRES_POST_NUM,  C.ADDR  AS  SUPPPRES_ADDR,  C.ADDR_DETED  AS  SUPPPRES_ADDR_DETED,  C.BSTYP_CTENT  AS  SUPPPRES_BSTYP_CTENT,  C.BSCDTN_CTENT  AS  SUPPPRES_BSCDTN_CTENT,  (CASE  WHEN  A.REPT_AMT  >  0  THEN  A.REPT_AMT  ELSE  A.DEMD_AMT  END)  AS  OCC_AMT,  A.ATAX_AMT  AS  ATAX_AMT,  A.BILL_ISSREQ_YN  AS  BILL_ISSREQ_YN,  A.ACCTNCHIT_ISS_YN  AS  ACCTNCHIT_ISS_YN  FROM  FIDU.TLEV_USEDEMDREPT  A,  FIDU.TLEV_BSCON  B,  FIDU.TLEV_BSCON  C  WHERE  A.BSCON_CD  =  B.BSCON_CD   \n";
        query +=" AND  C.BSCON_CD  =  'C0438'  UNION  ALL   \n";
        query +=" SELECT  TO_CHAR(A.DEMD_NUM)  AS  DEMD_NUM,  '2'  AS  BILL_KND,  '2'  AS  BILL_GBN,  MAX(A.DEMD_DAY)  AS  DEMD_DAY,  MAX(A.BRAN_CD)  AS  BRAN_CD,  A.BSCON_CD  AS  BSCON_CD,  MAX(F.BSCONHAN_NM)  AS  BSCONHAN_NM,  MAX(F.INS_NUM)  AS  BSCON_INS_NUM,  MAX(F.BSCONHAN_NM)  AS  BSCON_FNM_NM,  MAX(F.REPPRES_NM)  AS  BSCON_REPPRES_NM,  MAX(F.POST_NUM)  AS  BSOCN_POST_NUM,  MAX(F.ADDR)  AS  BSCON_ADDR,  MAX(F.ADDR_DETED)  AS  BSCON_ADDR_DETED,  MAX(F.BSTYP_CTENT)  AS  BSCON_BSTYP_CTENT,  MAX(F.BSCDTN_CTENT)  AS  BSCON_BSCDTN_CTENT,  MAX(C.RIGHTPRES_MB_CD)  AS  SUPPPRES_CD,  MAX(D.HANMB_NM)  AS  SUPPPRES_NM,  MAX(D.INS_NUM)  AS  SUPPPRES_INS_NUM,  MAX(D.HANMB_NM)  AS  SUPPPRES_FNM_NM,  MAX(D.REPPRES_NM)  AS  SUPPPRES_REPPRES_NM,  MAX(E.POST_NUM)  AS  SUPPPRES_POST_NUM,  MAX(E.ADDR)  AS  SUPPPRES_ADDR,  MAX(E.ADDR_DETED)  AS  SUPPPRES_ADDR_DETED,  MAX(D.BSTYP_CTENT)  AS  SUPPPRES_BSTYP_CTENT,  MAX(D.BSCDTN_CTENT)  AS  SUPPPRES_BSCDTN_CTENT,  SUM(C.OCC_AMT)  AS  OCC_AMT,  SUM(C.ATAX_AMT)  AS  ATAX_AMT,  MAX(A.BILL_ISSREQ_YN)  AS  BILL_ISSREQ_YN,  MAX(A.ACCTNCHIT_ISS_YN)  AS  ACCTNCHIT_ISS_YN  FROM  FIDU.TLEV_USEDEMDREPT  A,  FIDU.TLEV_CLRREC  B,  FIDU.TLEV_RIGHTPRESOCCAMT  C,  FIDU.TMEM_MB  D,   \n";
        query +=" (SELECT  MB_CD,  POST_NUM,  ADDR,  ADDR_DETED  FROM  FIDU.TMEM_ADBK  WHERE  ADDR_GBN  =  '2')  E,  FIDU.TLEV_BSCON  F  WHERE  1  =  1   \n";
        query +=" AND  A.DEMD_NUM  =  B.DEMD_NUM   \n";
        query +=" AND  B.APPRV_NUM  =  C.APPRV_NUM   \n";
        query +=" AND  B.CLR_NUM  =  C.CLR_NUM   \n";
        query +=" AND  C.RIGHTPRES_MB_CD  =  D.MB_CD   \n";
        query +=" AND  D.MB_CD  =  E.MB_CD(+)   \n";
        query +=" AND  D.MB_GBN1  =  'F'   \n";
        query +=" AND  A.BSCON_CD  =  F.BSCON_CD  GROUP  BY  A.BSCON_CD,  A.DEMD_NUM  )  WHERE  1  =  1   \n";
        query +=" AND  BILL_ISSREQ_YN  =  1   \n";
        query +=" AND  DEMD_DAY  BETWEEN  :DEMD_YYMN  ||'01'   \n";
        query +=" AND  :DEMD_YYMN  ||  '31'  ORDER  BY  DEMD_NUM  ASC ";
        sobj.setSql(query);
        sobj.setString("DEMD_YYMN", DEMD_YYMN);               //DEMD_YYMN
        return sobj;
    }
    // 분기
    public DOBJ CALLcreateBill01tac_s01_MPD7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD7");
        VOBJ dvobj = dobj.getRetObject("SEL2");         //계산서 쿼리1에서 생성시킨 OBJECT입니다.(CALLcreateBill01tac_s01_SEL2)
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLcreateBill01tac_s01_SEL10(Conn, dobj);           //  계산서번호맥스값 가져오기
                dobj  = CALLcreateBill01tac_s01_INS9(Conn, dobj);           //  계산서 저장
                dobj  = CALLcreateBill01tac_s01_SEL12(Conn, dobj);           //  사용승인내역조회
                dobj  = CALLcreateBill01tac_s01_INS14(Conn, dobj);           //  사용승인내역
            }
        }
        return dobj;
    }
    // 계산서번호맥스값 가져오기
    public DOBJ CALLcreateBill01tac_s01_SEL10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL10");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL10");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcreateBill01tac_s01_SEL10(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL10");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s01_SEL10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  CASE  WHEN  COUNT(A.BILL_NUM)  =  0  THEN  :DEMD_DAY||'001'  ELSE  TO_CHAR(TO_NUMBER(MAX(A.BILL_NUM))  +  1)  END  AS  BILL_NUM  FROM  FIDU.TTAC_BILL  A  WHERE  SUBSTR(A.BILL_NUM,1,6)  =  :DEMD_DAY ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        return sobj;
    }
    // 계산서 저장
    public DOBJ CALLcreateBill01tac_s01_INS9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        dvobj.Println("INS9");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s01_INS9(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS9") ;
        rvobj.Println("INS9");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s01_INS9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   BSCON_FNM_NM = dvobj.getRecord().get("BSCON_FNM_NM");   //거래처 상호 명
        String   BILL_KND = dvobj.getRecord().get("BILL_KND");   //계산서 종류
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   BSCON_REPPRES_NM = dvobj.getRecord().get("BSCON_REPPRES_NM");   //거래처 대표자 명
        String   SUPPPRES_INS_NUM = dvobj.getRecord().get("SUPPPRES_INS_NUM");   //공급자 등록 번호
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   SUPPPRES_NM = dvobj.getRecord().get("SUPPPRES_NM");   //공급자 명
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String   SUPPPRES_REPPRES_NM = dvobj.getRecord().get("SUPPPRES_REPPRES_NM");   //공급자 대표자 명
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   BSCON_NM = dvobj.getRecord().get("BSCON_NM");   //거래처 명
        String   BSCON_INS_NUM = dvobj.getRecord().get("BSCON_INS_NUM");   //거래처 등록 번호
        String   DEMD_NUM = dvobj.getRecord().get("DEMD_NUM");   //청구서번호
        String   SUPPPRES_CD = dvobj.getRecord().get("SUPPPRES_CD");   //공급자_코드
        String   SUPPPRES_FNM_NM = dvobj.getRecord().get("SUPPPRES_FNM_NM");   //공급자 상호 명
        String   BSCON_POST_NUM = dvobj.getRecord().get("BSCON_POST_NUM");   //거래처 우편 번호
        double   ATAX_AMT = dobj.getRetObject("R").getRecord().getDouble("ATAX_AMT");   //부가세 금액
        String   BILL_NUM = dobj.getRetObject("SEL10").getRecord().get("BILL_NUM");   //계산서 번호
        String   BIPLC_GBN ="001";   //사업장 구분
        String   BSCON_ADDR = dobj.getRetObject("R").getRecord().get("BSCON_ADDR")+dobj.getRetObject("R").getRecord().get("BSCON_ADDR_DETED");   //거래처 주소
        String   BSCON_BSCDTN = dobj.getRetObject("R").getRecord().get("BSCON_BSCDTN_CTENT");   //거래처업태
        String   BSCON_BSTYP = dobj.getRetObject("R").getRecord().get("SUPPPRES_BSTYP_CTENT");   //거래처업종
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");   //발행 일자
        String   ISS_YN ="1";   //발행 유무
        String   SUPPPRES_ADDR = dobj.getRetObject("R").getRecord().get("SUPPPRES_ADDR")+dobj.getRetObject("R").getRecord().get("SUPPPRES_ADDR_DETED");   //공급자 주소
        String   SUPPPRES_BSCDTN = dobj.getRetObject("R").getRecord().get("SUPPPRES_BSCDTN_CTENT");   //공급자_업태
        String   SUPPPRES_BSTYP = dobj.getRetObject("R").getRecord().get("SUPPPRES_BSTYP_CTENT");   //공급자업종
        double   SUPPTEMP_AMT = dobj.getRetObject("R").getRecord().getDouble("OCC_AMT");   //공급가 액
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_BILL (ISS_YN, BSCON_POST_NUM, SUPPPRES_FNM_NM, BSCON_ADDR, ATAX_AMT, BIPLC_GBN, SUPPPRES_CD, DEMD_NUM, INS_DATE, BSCON_INS_NUM, BSCON_NM, SUPPPRES_BSTYP, BSCON_BSTYP, REMAK, BSCON_BSCDTN, SUPPPRES_REPPRES_NM, BILL_GBN, SUPPPRES_ADDR, SUPPPRES_NM, BSCON_CD, SUPPPRES_INS_NUM, BILL_NUM, ISS_DAY, SUPPTEMP_AMT, SUPPPRES_BSCDTN, BSCON_REPPRES_NM, BRAN_CD, BILL_KND, BSCON_FNM_NM)  \n";
        query +=" values(:ISS_YN , :BSCON_POST_NUM , :SUPPPRES_FNM_NM , :BSCON_ADDR , :ATAX_AMT , :BIPLC_GBN , :SUPPPRES_CD , :DEMD_NUM , SYSDATE, :BSCON_INS_NUM , :BSCON_NM , :SUPPPRES_BSTYP , :BSCON_BSTYP , :REMAK , :BSCON_BSCDTN , :SUPPPRES_REPPRES_NM , :BILL_GBN , :SUPPPRES_ADDR , :SUPPPRES_NM , :BSCON_CD , :SUPPPRES_INS_NUM , :BILL_NUM , :ISS_DAY , :SUPPTEMP_AMT , :SUPPPRES_BSCDTN , :BSCON_REPPRES_NM , :BRAN_CD , :BILL_KND , :BSCON_FNM_NM )";
        sobj.setSql(query);
        sobj.setString("BSCON_FNM_NM", BSCON_FNM_NM);               //거래처 상호 명
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("BSCON_REPPRES_NM", BSCON_REPPRES_NM);               //거래처 대표자 명
        sobj.setString("SUPPPRES_INS_NUM", SUPPPRES_INS_NUM);               //공급자 등록 번호
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("SUPPPRES_NM", SUPPPRES_NM);               //공급자 명
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("SUPPPRES_REPPRES_NM", SUPPPRES_REPPRES_NM);               //공급자 대표자 명
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("BSCON_NM", BSCON_NM);               //거래처 명
        sobj.setString("BSCON_INS_NUM", BSCON_INS_NUM);               //거래처 등록 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("SUPPPRES_CD", SUPPPRES_CD);               //공급자_코드
        sobj.setString("SUPPPRES_FNM_NM", SUPPPRES_FNM_NM);               //공급자 상호 명
        sobj.setString("BSCON_POST_NUM", BSCON_POST_NUM);               //거래처 우편 번호
        sobj.setDouble("ATAX_AMT", ATAX_AMT);               //부가세 금액
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("BIPLC_GBN", BIPLC_GBN);               //사업장 구분
        sobj.setString("BSCON_ADDR", BSCON_ADDR);               //거래처 주소
        sobj.setString("BSCON_BSCDTN", BSCON_BSCDTN);               //거래처업태
        sobj.setString("BSCON_BSTYP", BSCON_BSTYP);               //거래처업종
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setString("ISS_YN", ISS_YN);               //발행 유무
        sobj.setString("SUPPPRES_ADDR", SUPPPRES_ADDR);               //공급자 주소
        sobj.setString("SUPPPRES_BSCDTN", SUPPPRES_BSCDTN);               //공급자_업태
        sobj.setString("SUPPPRES_BSTYP", SUPPPRES_BSTYP);               //공급자업종
        sobj.setDouble("SUPPTEMP_AMT", SUPPTEMP_AMT);               //공급가 액
        return sobj;
    }
    // 사용승인내역조회
    public DOBJ CALLcreateBill01tac_s01_SEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL12");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL12");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLcreateBill01tac_s01_SEL12(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL12");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s01_SEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_NUM = dobj.getRetObject("R").getRecord().get("DEMD_NUM");   //청구서번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.APPRV_NUM,  B.CLR_NUM,  B.MDM_CD,  FIDU.GET_MDM_NM(B.MDM_CD)  AS  MDM_NM,  B.SVC_CD,  FIDU.GET_SVC_NM(B.SVC_CD)  AS  SVC_NM,  A.USE_TTL  TITLE_NM,  B.QTY_PNUM,  C.CTST_ID,  C.CTST_KND,  C.CTST_START_NUM,  C.CTST_END_NUM,  B.LEVY_AMT  AS  SUPPTEMP_AMT,  B.ATAX_AMT  FROM  FIDU.TLEV_USEAPPRV  A,  FIDU.TLEV_CLRREC  B,  FIDU.TLEV_CTSTNUMISSUEBRE  C  WHERE  A.APPRV_NUM  =  B.APPRV_NUM   \n";
        query +=" AND  B.APPRV_NUM  =  C.APPRV_NUM(+)   \n";
        query +=" AND  B.CLR_NUM  =  C.CLR_NUM(+)   \n";
        query +=" AND  B.DEMD_NUM  =  :DEMD_NUM   \n";
        query +=" AND  B.CLR_NUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        return sobj;
    }
    // 사용승인내역
    public DOBJ CALLcreateBill01tac_s01_INS14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS14");
        VOBJ dvobj = dobj.getRetObject("SEL12");           //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("INS14");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLcreateBill01tac_s01_INS14(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS14") ;
        rvobj.Println("INS14");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLcreateBill01tac_s01_INS14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String   TITLE_NM = dvobj.getRecord().get("TITLE_NM");   //타이틀 명
        String   SVC_NM = dvobj.getRecord().get("SVC_NM");   //서비스 명
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //승인 번호
        String   MDM_NM = dvobj.getRecord().get("MDM_NM");   //매체 명
        String   MDM_CD = dvobj.getRecord().get("MDM_CD");   //매체 코드
        String   CTST_START_NUM = dvobj.getRecord().get("CTST_START_NUM");   //증지 시작 번호
        double   SUPPTEMP_AMT = dvobj.getRecord().getDouble("SUPPTEMP_AMT");   //공급가 액
        String   SVC_CD = dvobj.getRecord().get("SVC_CD");   //서비스 코드
        String   CTST_KND = dvobj.getRecord().get("CTST_KND");   //증지 종류
        String   CTST_END_NUM = dvobj.getRecord().get("CTST_END_NUM");   //증지 종료 번호
        int   QTY_PNUM = dvobj.getRecord().getInt("QTY_PNUM");   //수량 개수
        double   ATAX_AMT = dvobj.getRecord().getDouble("ATAX_AMT");   //부가세 금액
        String   CTST_ID = dvobj.getRecord().get("CTST_ID");   //증지ID
        String   BILL_GBN = dobj.getRetObject("R").getRecord().get("BILL_GBN");   //계산서 구분
        String   BILL_KND = dobj.getRetObject("R").getRecord().get("BILL_KND");   //계산서 종류
        String   BILL_NUM = dobj.getRetObject("SEL10").getRecord().get("BILL_NUM");   //계산서 번호
        String   DEMD_NUM = dobj.getRetObject("R").getRecord().get("DEMD_NUM");   //청구서번호
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("DEMD_DAY");   //발행 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into FIDU.TTAC_USEAPPRVBRE (BILL_GBN, CTST_ID, ATAX_AMT, QTY_PNUM, DEMD_NUM, INS_DATE, CTST_END_NUM, BILL_NUM, CTST_KND, SVC_CD, SUPPTEMP_AMT, CTST_START_NUM, ISS_DAY, MDM_CD, MDM_NM, APPRV_NUM, BILL_KND, SVC_NM, TITLE_NM)  \n";
        query +=" values(:BILL_GBN , :CTST_ID , :ATAX_AMT , :QTY_PNUM , :DEMD_NUM , SYSDATE, :CTST_END_NUM , :BILL_NUM , :CTST_KND , :SVC_CD , :SUPPTEMP_AMT , :CTST_START_NUM , :ISS_DAY , :MDM_CD , :MDM_NM , :APPRV_NUM , :BILL_KND , :SVC_NM , :TITLE_NM )";
        sobj.setSql(query);
        sobj.setString("TITLE_NM", TITLE_NM);               //타이틀 명
        sobj.setString("SVC_NM", SVC_NM);               //서비스 명
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("MDM_NM", MDM_NM);               //매체 명
        sobj.setString("MDM_CD", MDM_CD);               //매체 코드
        sobj.setString("CTST_START_NUM", CTST_START_NUM);               //증지 시작 번호
        sobj.setDouble("SUPPTEMP_AMT", SUPPTEMP_AMT);               //공급가 액
        sobj.setString("SVC_CD", SVC_CD);               //서비스 코드
        sobj.setString("CTST_KND", CTST_KND);               //증지 종류
        sobj.setString("CTST_END_NUM", CTST_END_NUM);               //증지 종료 번호
        sobj.setInt("QTY_PNUM", QTY_PNUM);               //수량 개수
        sobj.setDouble("ATAX_AMT", ATAX_AMT);               //부가세 금액
        sobj.setString("CTST_ID", CTST_ID);               //증지ID
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("BILL_KND", BILL_KND);               //계산서 종류
        sobj.setString("BILL_NUM", BILL_NUM);               //계산서 번호
        sobj.setString("DEMD_NUM", DEMD_NUM);               //청구서번호
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        return sobj;
    }
    //##**$$createBill01tac_s01
    //##**$$apprv_list_select
    /* * 프로그램명 : tac01_s01
    * 작성자 : 송유빈
    * 작성일 : 2009/08/04
    * 설명 : 계산서 발행에 필요한 사용승인내역 기본정보를 조회한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLapprv_list_select(DOBJ dobj)
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
            dobj  = CALLapprv_list_select_SEL1(Conn, dobj);           //  사용승인내역 조회
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
    public DOBJ CTLapprv_list_select( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLapprv_list_select_SEL1(Conn, dobj);           //  사용승인내역 조회
        return dobj;
    }
    // 사용승인내역 조회
    // 사용승인내역 조회
    public DOBJ CALLapprv_list_select_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLapprv_list_select_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLapprv_list_select_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPRV_NUM = dobj.getRetObject("S").getRecord().get("APPRV_NUM");   //승인 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT A.APPRV_NUM AS APPRV_NUM, A.MDM_CD AS MDM_CD, FIDU.GET_MDM_NM(A.MDM_CD) AS MDM_NM, A.USE_TTL AS TITLE_NM, B.QTY_PNUM AS QTY, NVL(C.REPT_AMT, 0) AS BILL_AMT  ";
        query +=" FROM FIDU.TLEV_USEAPPRV A, FIDU.TLEV_CLRREC B, FIDU.TLEV_USEDEMDREPT C  ";
        query +=" WHERE A.APPRV_NUM = B.APPRV_NUM  ";
        query +=" AND A.APPRV_NUM = C.APPRV_NUM  ";
        if( !APPRV_NUM.equals("") )
        {
            query +=" AND A.APPRV_NUM = :APPRV_NUM  ";
        }
        sobj.setSql(query);
        if(!APPRV_NUM.equals(""))
        {
            sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        }
        return sobj;
    }
    //##**$$apprv_list_select
    //##**$$bill_elec_count
    /*
    */
    public DOBJ CTLbill_elec_count(DOBJ dobj)
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
            dobj  = CALLbill_elec_count_SEL1(Conn, dobj);           //  전자계산서 발행건수
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
    public DOBJ CTLbill_elec_count( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbill_elec_count_SEL1(Conn, dobj);           //  전자계산서 발행건수
        return dobj;
    }
    // 전자계산서 발행건수
    public DOBJ CALLbill_elec_count_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbill_elec_count_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbill_elec_count_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   DEMD_DAY = dobj.getRetObject("S").getRecord().get("DEMD_DAY");   //청구 일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  BILL_CNT  FROM  FIDU.TTAC_BILL  WHERE  ISS_DAY  LIKE  :DEMD_DAY||'%'   \n";
        query +=" AND  BILL_KND  IN  (1,  2)   \n";
        query +=" AND  DEL_YN  IS  NULL   \n";
        query +=" AND  ELEC_SEND_YN=  '1' ";
        sobj.setSql(query);
        sobj.setString("DEMD_DAY", DEMD_DAY);               //청구 일자
        return sobj;
    }
    //##**$$bill_elec_count
    //##**$$end
}
