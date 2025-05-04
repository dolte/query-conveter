
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s11_1
{
    public bra01_s11_1()
    {
    }
    //##**$$bra01_s11_save01
    /* * 프로그램명 : bra01_s11
    * 작성자 : 999999
    * 작성일 : 2009/10/16
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbra01_s11_save01(DOBJ dobj)
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
            dobj  = CALLbra01_s11_save01_MPD1(Conn, dobj);           //  계산서발급데이터
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
    public DOBJ CTLbra01_s11_save01( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_save01_MPD1(Conn, dobj);           //  계산서발급데이터
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 계산서발급데이터
    public DOBJ CALLbra01_s11_save01_MPD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD1");
        VOBJ dvobj = dobj.getRetObject("S");         //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("MPD1");
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if( dvobj.getRecord().get("CRUD").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra01_s11_save01_SEL7(Conn, dobj);           //  발급일변경여부
                if( dobj.getRetObject("SEL7").getRecord().get("UPD_YN").equals("1"))
                {
                    dobj  = CALLbra01_s11_save01_UPD9(Conn, dobj);           //  계산서발급수정
                }
                else
                {
                    dobj  = CALLbra01_s11_save01_UPD6(Conn, dobj);           //  계산서발급수정
                }
            }
            else if( dvobj.getRecord().get("CRUD").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra01_s11_save01_SEL11(Conn, dobj);           //  SEQ생성
                dobj  = CALLbra01_s11_save01_INS5(Conn, dobj);           //  계산서발급입력
            }
        }
        return dobj;
    }
    // 발급일변경여부
    public DOBJ CALLbra01_s11_save01_SEL7(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL7");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL7");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra01_s11_save01_SEL7(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL7");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_save01_SEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   APPTN_YRMN = dobj.getRetObject("R").getRecord().get("APPTN_YRMN");   //신청 일시
        String   ISS_DAY = dobj.getRetObject("R").getRecord().get("ISS_DAY");   //발행 일자
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        double   SEQ = dobj.getRetObject("R").getRecord().getDouble("SEQ");   //관리번호
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(ISS_DAY,:ISS_DAY,'1','0')  AS  UPD_YN  FROM  GIBU.TBRA_BILL_ISS_MNG  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  APPTN_YRMN  =  :APPTN_YRMN   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD   \n";
        query +=" AND  SEQ  =  :SEQ ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //신청 일시
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // SEQ생성
    public DOBJ CALLbra01_s11_save01_SEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL11");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL11");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra01_s11_save01_SEL11(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL11");
        rvobj.Println("SEL11");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_save01_SEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPTN_YRMN ="";        //신청 일시
        String   APPTN_YRMN_1 = dobj.getRetObject("S1").getRecord().get("ISSUE_YRMN");   //신청 일시
        String   UPSO_CD = dobj.getRetObject("R").getRecord().get("UPSO_CD");   //업소 코드
        String   BRAN_CD = dobj.getRetObject("R").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(to_number(MAX(SEQ)),  0)  +1  AS  SEQ  FROM  GIBU.TBRA_BILL_ISS_MNG  WHERE  BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  APPTN_YRMN  =  SUBSTR(  :APPTN_YRMN_1,1,6  )   \n";
        query +=" AND  UPSO_CD  =  :UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN_1", APPTN_YRMN_1);               //신청 일시
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 계산서발급입력
    public DOBJ CALLbra01_s11_save01_INS5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLbra01_s11_save01_INS5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS5") ;
        rvobj.Println("INS5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_save01_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPRV_NUM ="";        //승인 번호
        String APPTN_YRMN ="";        //신청 일시
        String INS_DATE ="";        //등록 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   APPRV_NUM_2 = dobj.getRetObject("R").getRecord().get("ISS_DAY");   //승인 번호
        String   APPRV_NUM_1 = dobj.getRetObject("R").getRecord().get("ISS_DAY");   //승인 번호
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   ISS_DAY = dvobj.getRecord().get("ISS_DAY");   //발행 일자
        String   ISS_BRE = dvobj.getRecord().get("ISS_BRE");   //발행 내역
        double   ISS_AMT = dvobj.getRecord().getDouble("ISS_AMT");   //발행 금액
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   APPTN_YRMN_1 = dobj.getRetObject("S1").getRecord().get("ISSUE_YRMN");   //신청 일시
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String  APPTN_GBN="";          //신청 구분
        if( ( dobj.getRetObject("R").getRecord().get("APPTN_GBN").equals("") ))
        {
            APPTN_GBN ="1";
        }
        else
        {
            APPTN_GBN = dobj.getRetObject("R").getRecord().get("APPTN_GBN");
        }
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   ISS_COMPL_YN = dobj.getRetObject("R").getRecord().get("ISSADD_YN");   //발행 완료 여부
        double   SEQ = dobj.getRetObject("SEL11").getRecord().getDouble("SEQ");   //관리번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_BILL_ISS_MNG (ISS_COMPL_YN, INSPRES_ID, BILL_GBN, APPTN_YRMN, SEQ, BSCON_CD, INS_DATE, ISS_AMT, ISS_BRE, ISS_DAY, UPSO_CD, APPTN_GBN, BRAN_CD, APPRV_NUM, REMAK)  \n";
        query +=" values(:ISS_COMPL_YN , :INSPRES_ID , :BILL_GBN , SUBSTR( :APPTN_YRMN_1,1,6 ), :SEQ , :BSCON_CD , SYSDATE, :ISS_AMT , :ISS_BRE , :ISS_DAY , :UPSO_CD , :APPTN_GBN , :BRAN_CD , (SELECT :APPRV_NUM_1 || LPAD(NVL(SUBSTR(MAX(APPRV_NUM),9,3) + 1,1),3,'0') FROM GIBU.TBRA_BILL_ISS_MNG WHERE ISS_DAY = :APPRV_NUM_2), :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("APPRV_NUM_2", APPRV_NUM_2);               //승인 번호
        sobj.setString("APPRV_NUM_1", APPRV_NUM_1);               //승인 번호
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setString("ISS_BRE", ISS_BRE);               //발행 내역
        sobj.setDouble("ISS_AMT", ISS_AMT);               //발행 금액
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("APPTN_YRMN_1", APPTN_YRMN_1);               //신청 일시
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("APPTN_GBN", APPTN_GBN);               //신청 구분
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("ISS_COMPL_YN", ISS_COMPL_YN);               //발행 완료 여부
        sobj.setDouble("SEQ", SEQ);               //관리번호
        return sobj;
    }
    // 계산서발급수정
    public DOBJ CALLbra01_s11_save01_UPD9(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD9");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra01_s11_save01_UPD9(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD9") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_save01_UPD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPTN_YRMN = dobj.getRetObject("S1").getRecord().get("ISSUE_YRMN");        //신청 일시
        String MOD_DATE ="";        //수정 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   ISS_DAY = dvobj.getRecord().get("ISS_DAY");   //발행 일자
        String   ISS_BRE = dvobj.getRecord().get("ISS_BRE");   //발행 내역
        double   ISS_AMT = dvobj.getRecord().getDouble("ISS_AMT");   //발행 금액
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //관리번호
        String   APPTN_YRMN_1 = dobj.getRetObject("S1").getRecord().get("ISSUE_YRMN");   //신청 일시
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String   ISS_COMPL_YN = dobj.getRetObject("R").getRecord().get("ISSADD_YN");   //발행 완료 여부
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BILL_ISS_MNG  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ISS_COMPL_YN=:ISS_COMPL_YN , BILL_GBN=:BILL_GBN , BSCON_CD=:BSCON_CD , ISS_AMT=:ISS_AMT , ISS_BRE=:ISS_BRE , ISS_DAY=:ISS_DAY , MOD_DATE=SYSDATE , REMAK=:REMAK  \n";
        query +=" where APPTN_YRMN=SUBSTR(:APPTN_YRMN_1,0,6)  \n";
        query +=" and SEQ=:SEQ  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setString("ISS_BRE", ISS_BRE);               //발행 내역
        sobj.setDouble("ISS_AMT", ISS_AMT);               //발행 금액
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("APPTN_YRMN_1", APPTN_YRMN_1);               //신청 일시
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("ISS_COMPL_YN", ISS_COMPL_YN);               //발행 완료 여부
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 계산서발급수정
    public DOBJ CALLbra01_s11_save01_UPD6(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD6");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra01_s11_save01_UPD6(dobj, dvobj);
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
    private SQLObject SQLbra01_s11_save01_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPRV_NUM ="";        //승인 번호
        String APPTN_YRMN = dobj.getRetObject("S1").getRecord().get("ISSUE_YRMN");        //신청 일시
        String MOD_DATE ="";        //수정 일시
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   APPRV_NUM_2 = dobj.getRetObject("R").getRecord().get("ISS_DAY");   //승인 번호
        String   APPRV_NUM_1 = dobj.getRetObject("R").getRecord().get("ISS_DAY");   //승인 번호
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   ISS_DAY = dvobj.getRecord().get("ISS_DAY");   //발행 일자
        String   ISS_BRE = dvobj.getRecord().get("ISS_BRE");   //발행 내역
        double   ISS_AMT = dvobj.getRecord().getDouble("ISS_AMT");   //발행 금액
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //관리번호
        String   APPTN_YRMN_1 = dobj.getRetObject("S1").getRecord().get("ISSUE_YRMN");   //신청 일시
        String   BILL_GBN = dvobj.getRecord().get("BILL_GBN");   //계산서 구분
        String   ISS_COMPL_YN = dobj.getRetObject("R").getRecord().get("ISSADD_YN");   //발행 완료 여부
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_BILL_ISS_MNG  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , ISS_COMPL_YN=:ISS_COMPL_YN , BILL_GBN=:BILL_GBN , BSCON_CD=:BSCON_CD , ISS_AMT=:ISS_AMT , ISS_BRE=:ISS_BRE , ISS_DAY=:ISS_DAY , MOD_DATE=SYSDATE , APPRV_NUM=(SELECT :APPRV_NUM_1 || LPAD(NVL(SUBSTR(MAX(APPRV_NUM),9,3) + 1,1),3,'0') FROM GIBU.TBRA_BILL_ISS_MNG  \n";
        query +=" WHERE ISS_DAY = :APPRV_NUM_2) , REMAK=:REMAK  \n";
        query +=" where APPTN_YRMN=SUBSTR(:APPTN_YRMN_1,0,6)  \n";
        query +=" and SEQ=:SEQ  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("APPRV_NUM_2", APPRV_NUM_2);               //승인 번호
        sobj.setString("APPRV_NUM_1", APPRV_NUM_1);               //승인 번호
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("ISS_DAY", ISS_DAY);               //발행 일자
        sobj.setString("ISS_BRE", ISS_BRE);               //발행 내역
        sobj.setDouble("ISS_AMT", ISS_AMT);               //발행 금액
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("APPTN_YRMN_1", APPTN_YRMN_1);               //신청 일시
        sobj.setString("BILL_GBN", BILL_GBN);               //계산서 구분
        sobj.setString("ISS_COMPL_YN", ISS_COMPL_YN);               //발행 완료 여부
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    //##**$$bra01_s11_save01
    //##**$$bra01_s11_delete
    /* * 프로그램명 : bra01_s11
    * 작성자 : 999999
    * 작성일 : 2009/10/08
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbra01_s11_delete(DOBJ dobj)
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
            dobj  = CALLbra01_s11_delete_MIUD1(Conn, dobj);           //  삭제
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
    public DOBJ CTLbra01_s11_delete( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_delete_MIUD1(Conn, dobj);           //  삭제
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 삭제
    public DOBJ CALLbra01_s11_delete_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra01_s11_delete_DEL5(Conn, dobj);           //  계산서발급삭제
            }
        }
        return dobj;
    }
    // 계산서발급삭제
    public DOBJ CALLbra01_s11_delete_DEL5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL5");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra01_s11_delete_DEL5(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL5") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_delete_DEL5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dvobj.getRecord().get("BRAN_CD");   //지부 코드
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //관리번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   APPTN_YRMN = dvobj.getRecord().get("APPTN_YRMN");   //신청 일시
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_BILL_ISS_MNG  \n";
        query +=" where APPTN_YRMN=:APPTN_YRMN  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ  \n";
        query +=" and BRAN_CD=:BRAN_CD";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("APPTN_YRMN", APPTN_YRMN);               //신청 일시
        return sobj;
    }
    //##**$$bra01_s11_delete
    //##**$$bra01_s11_select01
    /* * 프로그램명 : bra01_s11
    * 작성자 : 999999
    * 작성일 : 2009/10/13
    * 설명 :
    * 수정일1: 2010/02/10
    * 수정자 :
    * 수정내용 : 지부정보 가져오는거 수정
    * 수정일1: 2010/06/10
    * 수정자 : 권남균
    * 수정내용 : '1' AS ISSADD_YN => NVL(A.ISS_COMPL_YN,0) AS ISSADD_YN
    */
    public DOBJ CTLbra01_s11_select01(DOBJ dobj)
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
            dobj  = CALLbra01_s11_select01_SEL1(Conn, dobj);           //  계산서발급조회
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
    public DOBJ CTLbra01_s11_select01( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra01_s11_select01_SEL1(Conn, dobj);           //  계산서발급조회
        return dobj;
    }
    // 계산서발급조회
    public DOBJ CALLbra01_s11_select01_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra01_s11_select01_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra01_s11_select01_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String APPTN_YRMN ="";        //신청 일시
        String   APPTN_YRMN_1 = dobj.getRetObject("S").getRecord().get("ISSUE_YRMN");   //신청 일시
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.UPSO_CD  ,  B.UPSO_NM  ,  A.BSCON_CD  ,  A.SEQ  ,  C.BSCONHAN_NM  ,  A.BRAN_CD  ,  D.DEPT_NM  AS  BRAN_NM  ,  A.APPTN_YRMN  ,  A.ISS_BRE  ,  A.ISS_AMT  ,  A.BILL_GBN  ,  A.REMAK  ,  A.ISS_DAY  ,  NVL(A.ISS_COMPL_YN,0)  AS  ISS_COMPL_YN  ,  'U'  AS  CRUD  ,  NVL(A.ISS_COMPL_YN,0)  AS  ISSADD_YN  ,  A.BILL_NUM  ,  DECODE(A.APPTN_GBN,'1','단체','2','개별')  APPTN_GBN  FROM  GIBU.TBRA_BILL_ISS_MNG  A  ,  GIBU.TBRA_UPSO  B  ,  FIDU.TLEV_BSCON  C  ,  INSA.TCPM_DEPT  D  WHERE  A.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  A.BRAN_CD,  'AL',  A.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.APPTN_YRMN  =  SUBSTR(:APPTN_YRMN_1,0,6)   \n";
        query +=" AND  B.BRAN_CD  =  A.BRAN_CD   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD   \n";
        query +=" AND  C.BSCON_CD  =  A.BSCON_CD   \n";
        query +=" AND  D.GIBU  =  A.BRAN_CD  ORDER  BY  B.BRAN_CD,  A.INS_DATE,  A.BSCON_CD ";
        sobj.setSql(query);
        sobj.setString("APPTN_YRMN_1", APPTN_YRMN_1);               //신청 일시
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$bra01_s11_select01
    //##**$$end
}
