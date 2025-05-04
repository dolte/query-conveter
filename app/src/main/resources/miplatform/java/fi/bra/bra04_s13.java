
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra04_s13
{
    public bra04_s13()
    {
    }
    //##**$$bra04_s13_save01
    /* * 프로그램명 : bra04_s13
    * 작성자 : 999999
    * 작성일 : 2009/11/26
    * 설명    : 은행 계좌번호별로 Excel 로 다운받은 입금내역 리스트의 입금된 입금내역을 저장한다.
    현재 Excel 로 upload 된 입금내역은 수정/삭제가 불가능하다.
    (DB 테이블에서 직접 수정/삭제해야 함, 수정/삭제 시 매핑된 내역이 있는 경우를 확인하고 삭제할 것)
    Input
    ACCN_NUM (계좌 번호)
    BALANCE (잔액)
    BANK_CD (은행 코드)
    BRAN_CD (지부 코드)
    DISTR_GBN (입금 분배 구분)
    END_OPBI_DAY (종료일자)
    EXCEL_YN (엑셀UPLOAD데이터여부)
    FROM_DAY (등록일)
    OUT_AMT (출금 금액)
    RECEPT_BANK (취급점)
    REMAK (비고)
    REPTPRES (입금자)
    REPT_AMT (입금 금액)
    REPT_DAY (입금일자)
    START_DAY (시작일)
    START_OPBI_DAY (시작일자)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbra04_s13_save01(DOBJ dobj)
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
            dobj  = CALLbra04_s13_save01_SEL8(Conn, dobj);           //  엑셀업로드일련번호생성
            dobj  = CALLbra04_s13_save01_MPD5(Conn, dobj);           //  로우 단위 처리
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
    public DOBJ CTLbra04_s13_save01( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra04_s13_save01_SEL8(Conn, dobj);           //  엑셀업로드일련번호생성
        dobj  = CALLbra04_s13_save01_MPD5(Conn, dobj);           //  로우 단위 처리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 엑셀업로드일련번호생성
    public DOBJ CALLbra04_s13_save01_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra04_s13_save01_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save01_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //등록일
        String   EXCEL_YN = dobj.getRetObject("S1").firstRecord().get("EXCEL_YN");   //엑셀UPLOAD데이터여부
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  DECODE(SUBSTR(MAX(INP_SEQ),1,8),NULL,TO_CHAR(SYSDATE,'YYYYMMDD')  ||  '01',TO_CHAR(SYSDATE,'YYYYMMDD')  ||  LPAD(TO_NUMBER(SUBSTR(MAX(INP_SEQ),9,2))  +  1  ,2,'0'))  AS  INP_SEQ  ,  DECODE(:EXCEL_YN,  'Y',  :FROM_DAY,  TO_CHAR(SYSDATE,  'YYYYMMDD'))  REPT_DAY  FROM  GIBU.TBRA_REPT_TRANS  A  WHERE  SUBSTR(INP_SEQ,1,8)  =  TO_CHAR(SYSDATE,'YYYYMMDD')   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("FROM_DAY", FROM_DAY);               //등록일
        sobj.setString("EXCEL_YN", EXCEL_YN);               //엑셀UPLOAD데이터여부
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 로우 단위 처리
    public DOBJ CALLbra04_s13_save01_MPD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MPD5");
        VOBJ dvobj = dobj.getRetObject("S1");         //사용자 화면에서 발생한 Object입니다.
        VOBJ       rvobj= null;
        dvobj.first();
        while(dvobj.next())
        {
            if(true)
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra04_s13_save01_SEL4(Conn, dobj);           //  입금번호 발번
                if( dobj.getRetObject("R").getRecord().get("EXCEL_YN").equals("Y"))
                {
                    dobj  = CALLbra04_s13_save01_INS10(Conn, dobj);           //  무통장입금정보UPLOAD
                    dobj  = CALLbra04_s13_save01_INS3(Conn, dobj);           //  무통장입금정보UPLOAD
                    dobj  = CALLbra04_s13_save01_XIUD9(Conn, dobj);           //  입금자 수정
                }
                else
                {
                    dobj  = CALLbra04_s13_save01_INS1(Conn, dobj);           //  무통장입금정보UPLOAD
                    dobj  = CALLbra04_s13_save01_INS3(Conn, dobj);           //  무통장입금정보UPLOAD
                    dobj  = CALLbra04_s13_save01_XIUD9(Conn, dobj);           //  입금자 수정
                }
            }
        }
        return dobj;
    }
    // 입금번호 발번
    // 이관으로 인해  발번은 TBRA_REPT 에서 진행한다. (이전 데이터는 TBRA_REPT 에만 이관하기 때문)
    public DOBJ CALLbra04_s13_save01_SEL4(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL4");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL4");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra04_s13_save01_SEL4(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL4");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save01_SEL4(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //입금일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(TO_NUMBER(REPT_NUM)),0)+1,5,'0')  AS  REPT_NUM  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  '03' ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        return sobj;
    }
    // 무통장입금정보UPLOAD
    public DOBJ CALLbra04_s13_save01_INS10(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS10");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra04_s13_save01_INS10(dobj, dvobj);
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
    private SQLObject SQLbra04_s13_save01_INS10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String RECV_DAY ="";        //영수 일자
        double   BALANCE = dvobj.getRecord().getDouble("BALANCE");   //잔액
        double   OUT_AMT = dvobj.getRecord().getDouble("OUT_AMT");   //출금 금액
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //승인 번호
        String   RECV_DAY_1 = dobj.getRetObject("R").getRecord().get("RECV_DAY");   //영수 일자
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //입금 금액
        String   REPTPRES = dvobj.getRecord().get("REPTPRES");   //입금자
        String   RECEPT_BANK = dvobj.getRecord().get("RECEPT_BANK");   //취급점
        String   EXCEL_YN = dvobj.getRecord().get("EXCEL_YN");   //엑셀UPLOAD데이터여부
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //계좌 번호
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //은행 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String  DISTR_GBN="";          //입금 분배 구분
        if( dobj.getRetObject("R").getRecord().get("DISTR_GBN").equals("00"))
        {
            DISTR_GBN ="";
        }
        else
        {
            DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");
        }
        String   INP_SEQ = dobj.getRetObject("SEL8").getRecord().get("INP_SEQ");   //엑셀업로드일련번호
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   REPT_DAY = dobj.getRetObject("SEL8").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL4").getRecord().get("REPT_NUM");   //입금 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_TRANS (REPT_DAY, INP_SEQ, REPT_NUM, DISTR_GBN, INSPRES_ID, BANK_CD, EXCEL_YN, RECEPT_BANK, INS_DATE, REPTPRES, REPT_AMT, RECV_DAY, BRAN_CD, APPRV_NUM, REMAK, ACCN_NUM, OUT_AMT, BALANCE)  \n";
        query +=" values(:REPT_DAY , :INP_SEQ , :REPT_NUM , :DISTR_GBN , :INSPRES_ID , :BANK_CD , :EXCEL_YN , :RECEPT_BANK , SYSDATE, :REPTPRES , :REPT_AMT , REPLACE(:RECV_DAY_1,'-',''), :BRAN_CD , :APPRV_NUM , :REMAK , :ACCN_NUM , :OUT_AMT , :BALANCE )";
        sobj.setSql(query);
        sobj.setDouble("BALANCE", BALANCE);               //잔액
        sobj.setDouble("OUT_AMT", OUT_AMT);               //출금 금액
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("RECV_DAY_1", RECV_DAY_1);               //영수 일자
        sobj.setDouble("REPT_AMT", REPT_AMT);               //입금 금액
        sobj.setString("REPTPRES", REPTPRES);               //입금자
        sobj.setString("RECEPT_BANK", RECEPT_BANK);               //취급점
        sobj.setString("EXCEL_YN", EXCEL_YN);               //엑셀UPLOAD데이터여부
        sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("DISTR_GBN", DISTR_GBN);               //입금 분배 구분
        sobj.setString("INP_SEQ", INP_SEQ);               //엑셀업로드일련번호
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        return sobj;
    }
    // 무통장입금정보UPLOAD
    public DOBJ CALLbra04_s13_save01_INS3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS3");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra04_s13_save01_INS3(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS3") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save01_INS3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String RECV_DAY ="";        //영수 일자
        String REMAK ="";        //비고
        String   REMAK_1 = dobj.getRetObject("R").getRecord().get("REPTPRES");   //비고
        String   RECV_DAY_1 = dobj.getRetObject("R").getRecord().get("RECV_DAY");   //영수 일자
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //입금 금액
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   EXCEL_YN = dvobj.getRecord().get("EXCEL_YN");   //엑셀UPLOAD데이터여부
        double   COMIS = dvobj.getRecord().getDouble("COMIS");   //수수료
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //계좌 번호
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //은행 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String  DISTR_GBN="";          //입금 분배 구분
        if( dobj.getRetObject("R").getRecord().get("DISTR_GBN").equals("00"))
        {
            DISTR_GBN ="";
        }
        else
        {
            DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");
        }
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   REPT_DAY = dobj.getRetObject("SEL8").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_GBN ="03";   //입금 구분
        String   REPT_NUM = dobj.getRetObject("SEL4").getRecord().get("REPT_NUM");   //입금 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT (REPT_DAY, COMIS, REPT_NUM, DISTR_GBN, INSPRES_ID, BANK_CD, EXCEL_YN, REPT_GBN, INS_DATE, UPSO_CD, REPT_AMT, RECV_DAY, BRAN_CD, ACCN_NUM, REMAK)  \n";
        query +=" values(:REPT_DAY , :COMIS , :REPT_NUM , :DISTR_GBN , :INSPRES_ID , :BANK_CD , :EXCEL_YN , :REPT_GBN , SYSDATE, :UPSO_CD , :REPT_AMT , REPLACE(:RECV_DAY_1,'-',''), :BRAN_CD , :ACCN_NUM , REPLACE(:REMAK_1, chr(49824), ''))";
        sobj.setSql(query);
        sobj.setString("REMAK_1", REMAK_1);               //비고
        sobj.setString("RECV_DAY_1", RECV_DAY_1);               //영수 일자
        sobj.setDouble("REPT_AMT", REPT_AMT);               //입금 금액
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("EXCEL_YN", EXCEL_YN);               //엑셀UPLOAD데이터여부
        sobj.setDouble("COMIS", COMIS);               //수수료
        sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("DISTR_GBN", DISTR_GBN);               //입금 분배 구분
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        return sobj;
    }
    // 입금자 수정
    // chr(49824) 특수문자가 엑셀 업로드 시 잘 붙어들어가서 제거
    public DOBJ CALLbra04_s13_save01_XIUD9(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLbra04_s13_save01_XIUD9(dobj, dvobj);
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
    private SQLObject SQLbra04_s13_save01_XIUD9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   REPT_DAY = dobj.getRetObject("SEL8").getRecord().get("REPT_DAY");   //입금일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" UPDATE GIBU.TBRA_REPT_TRANS  \n";
        query +=" SET REPTPRES = REPLACE(REPTPRES, chr(49824), '')  \n";
        query +=" WHERE BRAN_CD = :BRAN_CD  \n";
        query +=" AND REPT_DAY = :REPT_DAY  \n";
        query +=" AND REPTPRES LIKE '%' || chr(49824) || '%'";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        return sobj;
    }
    // 무통장입금정보UPLOAD
    public DOBJ CALLbra04_s13_save01_INS1(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLbra04_s13_save01_INS1(dobj, dvobj);
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
    private SQLObject SQLbra04_s13_save01_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String RECV_DAY ="";        //영수 일자
        double   BALANCE = dvobj.getRecord().getDouble("BALANCE");   //잔액
        double   OUT_AMT = dvobj.getRecord().getDouble("OUT_AMT");   //출금 금액
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //승인 번호
        String   RECV_DAY_1 = dobj.getRetObject("R").getRecord().get("RECV_DAY");   //영수 일자
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //입금 금액
        String   REPTPRES = dvobj.getRecord().get("REPTPRES");   //입금자
        String   RECEPT_BANK = dvobj.getRecord().get("RECEPT_BANK");   //취급점
        String   EXCEL_YN = dvobj.getRecord().get("EXCEL_YN");   //엑셀UPLOAD데이터여부
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //계좌 번호
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //은행 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String  DISTR_GBN="";          //입금 분배 구분
        if( dobj.getRetObject("R").getRecord().get("DISTR_GBN").equals("00"))
        {
            DISTR_GBN ="";
        }
        else
        {
            DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");
        }
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   REPT_DAY = dobj.getRetObject("SEL8").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL4").getRecord().get("REPT_NUM");   //입금 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_TRANS (REPT_DAY, REPT_NUM, DISTR_GBN, INSPRES_ID, BANK_CD, EXCEL_YN, RECEPT_BANK, INS_DATE, REPTPRES, REPT_AMT, RECV_DAY, BRAN_CD, APPRV_NUM, REMAK, ACCN_NUM, OUT_AMT, BALANCE)  \n";
        query +=" values(:REPT_DAY , :REPT_NUM , :DISTR_GBN , :INSPRES_ID , :BANK_CD , :EXCEL_YN , :RECEPT_BANK , SYSDATE, :REPTPRES , :REPT_AMT , REPLACE(:RECV_DAY_1,'-',''), :BRAN_CD , :APPRV_NUM , :REMAK , :ACCN_NUM , :OUT_AMT , :BALANCE )";
        sobj.setSql(query);
        sobj.setDouble("BALANCE", BALANCE);               //잔액
        sobj.setDouble("OUT_AMT", OUT_AMT);               //출금 금액
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("RECV_DAY_1", RECV_DAY_1);               //영수 일자
        sobj.setDouble("REPT_AMT", REPT_AMT);               //입금 금액
        sobj.setString("REPTPRES", REPTPRES);               //입금자
        sobj.setString("RECEPT_BANK", RECEPT_BANK);               //취급점
        sobj.setString("EXCEL_YN", EXCEL_YN);               //엑셀UPLOAD데이터여부
        sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("DISTR_GBN", DISTR_GBN);               //입금 분배 구분
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        return sobj;
    }
    //##**$$bra04_s13_save01
    //##**$$bra04_s13_search01
    /* * 프로그램명 : bra04_s13
    * 작성자 : 999999
    * 작성일 : 2009/12/04
    * 설명    : 계좌별로 무통장 입금내역을 조회한다.
    Input
    ACCN_NUM (계좌 번호)
    BANK_CD (은행 코드)
    BRAN_CD (지부 코드)
    END_DAY (종료일)
    FROM_DAY (등록일)
    REPT_DAY (입금일자)
    START_DAY (시작일)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbra04_s13_search01(DOBJ dobj)
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
            dobj  = CALLbra04_s13_search01_SEL1(Conn, dobj);           //  무토장입금정보조회
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
    public DOBJ CTLbra04_s13_search01( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra04_s13_search01_SEL1(Conn, dobj);           //  무토장입금정보조회
        return dobj;
    }
    // 무토장입금정보조회
    public DOBJ CALLbra04_s13_search01_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra04_s13_search01_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_search01_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   START_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //시작일
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //계좌 번호
        String   END_DAY = dobj.getRetObject("S").getRecord().get("END_DAY");   //종료일
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.REPT_DAY,  A.REPT_NUM,  A.BANK_CD,  A.ACCN_NUM,  A.REPTPRES,  A.REPT_AMT,  A.OUT_AMT,  A.BALANCE,  NVL(A.DISTR_GBN,'00')  AS  DISTR_GBN,  A.RECV_DAY,  A.RECEPT_BANK,  A.BRAN_CD,  A.REMAK,  A.UPSO_CD,  A.EXCEL_YN,   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'   \n";
        query +=" AND  UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  DISTR_GBN  IS  NULL)+   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  REPT_GBN  =  '03'   \n";
        query +=" AND  DISTR_GBN  =  A.DISTR_GBN)+   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TBRA_REPT_DISTR  WHERE  REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  DISTR_GBN  =  A.DISTR_GBN   \n";
        query +=" AND  BRAN_CD  =  A.BRAN_CD   \n";
        query +=" AND  REPT_GBN  =  '03')  AS  DISTR_YN  ,  A.APPRV_NUM  FROM  GIBU.TBRA_REPT_TRANS  A  WHERE  A.BRAN_CD  =  :BRAN_CD   \n";
        query +=" AND  A.REPT_DAY  >=  :START_DAY   \n";
        query +=" AND  A.REPT_DAY  <=  :END_DAY   \n";
        query +=" AND  A.ACCN_NUM  =  NVL(:ACCN_NUM,A.ACCN_NUM)  ORDER  BY  REPT_DAY,REPT_NUM ";
        sobj.setSql(query);
        sobj.setString("START_DAY", START_DAY);               //시작일
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        sobj.setString("END_DAY", END_DAY);               //종료일
        return sobj;
    }
    //##**$$bra04_s13_search01
    //##**$$bra04_s13_save02
    /* * 프로그램명 : bra04_s13
    * 작성자 : 박태현
    * 작성일 : 2009/11/27
    * 설명    : 은행 계좌번호별로 관리자가 수동으로 입금내역을 저장한다.
    수동으로 저장된 내역은 입금 매핑이 발생하지 않은 경우 수정/삭제가 가능하다.
    Input
    ACCN_NUM (계좌 번호)
    BALANCE (잔액)
    BANK_CD (은행 코드)
    BRAN_CD (지부 코드)
    FROM_DAY (등록일)
    OUT_AMT (출금 금액)
    RECEPT_BANK (취급점)
    REMAK (비고)
    REPTPRES (입금자)
    REPT_AMT (입금 금액)
    REPT_DAY (입금일자)
    START_DAY (시작일)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbra04_s13_save02(DOBJ dobj)
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
            dobj  = CALLbra04_s13_save02_MIUD1(Conn, dobj);           //  무통장입금조회정보
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
    public DOBJ CTLbra04_s13_save02( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra04_s13_save02_MIUD1(Conn, dobj);           //  무통장입금조회정보
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 무통장입금조회정보
    public DOBJ CALLbra04_s13_save02_MIUD1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("MIUD1");
        VOBJ dvobj = dobj.getRetObject("S1");            //사용자 화면에서 발생한 Object입니다.
        dvobj.first();
        while(dvobj.next())
        {
            if(dvobj.getRecord().get("IUDFLAG").equals("I"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra04_s13_save02_SEL8(Conn, dobj);           //  입금번호 발번
                dobj  = CALLbra04_s13_save02_INS14(Conn, dobj);           //  무통장입금정보저장
                dobj  = CALLbra04_s13_save02_INS7(Conn, dobj);           //  무통장입금정보저장
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra04_s13_save02_UPD6(Conn, dobj);           //  무통장입금정보수정
                dobj  = CALLbra04_s13_save02_UPD11(Conn, dobj);           //  무통장입금정수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLbra04_s13_save02_DEL11(Conn, dobj);           //  무통장입금정보삭제
                dobj  = CALLbra04_s13_save02_DEL12(Conn, dobj);           //  무통장입금정보삭제
            }
        }
        return dobj;
    }
    // 무통장입금정보삭제
    public DOBJ CALLbra04_s13_save02_DEL11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL11");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra04_s13_save02_DEL11(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL11") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save02_DEL11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //입금 번호
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //입금일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT_TRANS  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        return sobj;
    }
    // 입금번호 발번
    public DOBJ CALLbra04_s13_save02_SEL8(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL8");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL8");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra04_s13_save02_SEL8(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL8");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save02_SEL8(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //입금일자
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  LPAD(NVL(MAX(TO_NUMBER(REPT_NUM)),0)+1,5,'0')  AS  REPT_NUM  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_GBN  =  '03' ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        return sobj;
    }
    // 무통장입금정보수정
    public DOBJ CALLbra04_s13_save02_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLbra04_s13_save02_UPD6(dobj, dvobj);
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
    private SQLObject SQLbra04_s13_save02_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //승인 번호
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //입금 번호
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //입금일자
        String  DISTR_GBN="";          //입금 분배 구분
        if( dobj.getRetObject("R").getRecord().get("DISTR_GBN").equals("00"))
        {
            DISTR_GBN ="";
        }
        else
        {
            DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");
        }
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT_TRANS  \n";
        query +=" set MODPRES_ID=:MODPRES_ID , DISTR_GBN=:DISTR_GBN , MOD_DATE=SYSDATE , APPRV_NUM=:APPRV_NUM  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM";
        sobj.setSql(query);
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("DISTR_GBN", DISTR_GBN);               //입금 분배 구분
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // 무통장입금정보삭제
    public DOBJ CALLbra04_s13_save02_DEL12(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL12");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra04_s13_save02_DEL12(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL12") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save02_DEL12(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //입금 번호
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //입금일자
        String   REPT_GBN ="03";   //입금 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_REPT  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        return sobj;
    }
    // 무통장입금정보저장
    public DOBJ CALLbra04_s13_save02_INS14(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS14");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra04_s13_save02_INS14(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS14") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save02_INS14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String RECV_DAY ="";        //영수 일자
        double   BALANCE = dvobj.getRecord().getDouble("BALANCE");   //잔액
        double   OUT_AMT = dvobj.getRecord().getDouble("OUT_AMT");   //출금 금액
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   APPRV_NUM = dvobj.getRecord().get("APPRV_NUM");   //승인 번호
        String   RECV_DAY_1 = dobj.getRetObject("R").getRecord().get("RECV_DAY");   //영수 일자
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //입금 금액
        String   REPTPRES = dvobj.getRecord().get("REPTPRES");   //입금자
        String   RECEPT_BANK = dvobj.getRecord().get("RECEPT_BANK");   //취급점
        String   EXCEL_YN = dvobj.getRecord().get("EXCEL_YN");   //엑셀UPLOAD데이터여부
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //계좌 번호
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //은행 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String  DISTR_GBN="";          //입금 분배 구분
        if( dobj.getRetObject("R").getRecord().get("DISTR_GBN").equals("00"))
        {
            DISTR_GBN ="";
        }
        else
        {
            DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");
        }
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("SEL8").getRecord().get("REPT_NUM");   //입금 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT_TRANS (REPT_DAY, REPT_NUM, DISTR_GBN, INSPRES_ID, BANK_CD, EXCEL_YN, RECEPT_BANK, INS_DATE, REPTPRES, REPT_AMT, RECV_DAY, BRAN_CD, APPRV_NUM, REMAK, ACCN_NUM, OUT_AMT, BALANCE)  \n";
        query +=" values(:REPT_DAY , :REPT_NUM , :DISTR_GBN , :INSPRES_ID , :BANK_CD , :EXCEL_YN , :RECEPT_BANK , SYSDATE, :REPTPRES , :REPT_AMT , REPLACE(:RECV_DAY_1,'-',''), :BRAN_CD , :APPRV_NUM , :REMAK , :ACCN_NUM , :OUT_AMT , :BALANCE )";
        sobj.setSql(query);
        sobj.setDouble("BALANCE", BALANCE);               //잔액
        sobj.setDouble("OUT_AMT", OUT_AMT);               //출금 금액
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("APPRV_NUM", APPRV_NUM);               //승인 번호
        sobj.setString("RECV_DAY_1", RECV_DAY_1);               //영수 일자
        sobj.setDouble("REPT_AMT", REPT_AMT);               //입금 금액
        sobj.setString("REPTPRES", REPTPRES);               //입금자
        sobj.setString("RECEPT_BANK", RECEPT_BANK);               //취급점
        sobj.setString("EXCEL_YN", EXCEL_YN);               //엑셀UPLOAD데이터여부
        sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("DISTR_GBN", DISTR_GBN);               //입금 분배 구분
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        return sobj;
    }
    // 무통장입금정수정
    public DOBJ CALLbra04_s13_save02_UPD11(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("UPD11");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLbra04_s13_save02_UPD11(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD11") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_save02_UPD11(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_NUM = dvobj.getRecord().get("REPT_NUM");   //입금 번호
        String   REPT_DAY = dvobj.getRecord().get("REPT_DAY");   //입금일자
        String  DISTR_GBN="";          //입금 분배 구분
        if( dobj.getRetObject("R").getRecord().get("DISTR_GBN").equals("00"))
        {
            DISTR_GBN ="";
        }
        else
        {
            DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");
        }
        String   REPT_GBN ="03";   //입금 구분
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_REPT  \n";
        query +=" set DISTR_GBN=:DISTR_GBN  \n";
        query +=" where REPT_DAY=:REPT_DAY  \n";
        query +=" and REPT_NUM=:REPT_NUM  \n";
        query +=" and REPT_GBN=:REPT_GBN";
        sobj.setSql(query);
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("DISTR_GBN", DISTR_GBN);               //입금 분배 구분
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        return sobj;
    }
    // 무통장입금정보저장
    public DOBJ CALLbra04_s13_save02_INS7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLbra04_s13_save02_INS7(dobj, dvobj);
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
    private SQLObject SQLbra04_s13_save02_INS7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String RECV_DAY ="";        //영수 일자
        String   REMAK = dvobj.getRecord().get("REMAK");   //비고
        String   RECV_DAY_1 = dobj.getRetObject("R").getRecord().get("RECV_DAY");   //영수 일자
        double   REPT_AMT = dvobj.getRecord().getDouble("REPT_AMT");   //입금 금액
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        double   COMIS = dvobj.getRecord().getDouble("COMIS");   //수수료
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //계좌 번호
        String   BANK_CD = dobj.getRetObject("S").getRecord().get("BANK_CD");   //은행 코드
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String  DISTR_GBN="";          //입금 분배 구분
        if( dobj.getRetObject("R").getRecord().get("DISTR_GBN").equals("00"))
        {
            DISTR_GBN ="";
        }
        else
        {
            DISTR_GBN = dobj.getRetObject("R").getRecord().get("DISTR_GBN");
        }
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("FROM_DAY");   //입금일자
        String   REPT_GBN ="03";   //입금 구분
        String   REPT_NUM = dobj.getRetObject("SEL8").getRecord().get("REPT_NUM");   //입금 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_REPT (REPT_DAY, COMIS, REPT_NUM, DISTR_GBN, INSPRES_ID, BANK_CD, REPT_GBN, INS_DATE, UPSO_CD, REPT_AMT, RECV_DAY, BRAN_CD, ACCN_NUM, REMAK)  \n";
        query +=" values(:REPT_DAY , :COMIS , :REPT_NUM , :DISTR_GBN , :INSPRES_ID , :BANK_CD , :REPT_GBN , SYSDATE, :UPSO_CD , :REPT_AMT , REPLACE(:RECV_DAY_1,'-',''), :BRAN_CD , :ACCN_NUM , :REMAK )";
        sobj.setSql(query);
        sobj.setString("REMAK", REMAK);               //비고
        sobj.setString("RECV_DAY_1", RECV_DAY_1);               //영수 일자
        sobj.setDouble("REPT_AMT", REPT_AMT);               //입금 금액
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setDouble("COMIS", COMIS);               //수수료
        sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        sobj.setString("BANK_CD", BANK_CD);               //은행 코드
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("DISTR_GBN", DISTR_GBN);               //입금 분배 구분
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_GBN", REPT_GBN);               //입금 구분
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        return sobj;
    }
    //##**$$bra04_s13_save02
    //##**$$rept_closing
    /*
    */
    public DOBJ CTLrept_closing(DOBJ dobj)
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
            dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  지부별마감체크
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
    public DOBJ CTLrept_closing( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLrept_closing_SEL1(Conn, dobj);           //  지부별마감체크
        return dobj;
    }
    // 지부별마감체크
    // 지부별 마감테이블의 해당 년월 정보가 있는지 체크한다
    public DOBJ CALLrept_closing_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLrept_closing_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLrept_closing_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //입금 일자
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_BRANEND  WHERE  SUBSTR(:REPT_DAY,1,4)  =  END_YEAR   \n";
        query +=" AND  SUBSTR(:REPT_DAY,5,2)  =  END_MON   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금 일자
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$rept_closing
    //##**$$bra04_s13_balanceCheck
    /* * 프로그램명 : bra04_s13
    * 작성자 : 999999
    * 작성일 : 2009/11/16
    * 설명    : 무통장 입금내역 저장 시 계좌별 마지막 row 의 최종잔액과 저장하고자 하는 내역의 최종 잔액이 일치하는지 확인
    -> 모든 무통장 입금내역이 저장되는지 확인하기 위한 방법.
    Input
    ACCN_NUM (계좌 번호)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbra04_s13_balanceCheck(DOBJ dobj)
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
            dobj  = CALLbra04_s13_balanceCheck_SEL1(Conn, dobj);           //  잔액정합성체크
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
    public DOBJ CTLbra04_s13_balanceCheck( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra04_s13_balanceCheck_SEL1(Conn, dobj);           //  잔액정합성체크
        return dobj;
    }
    // 잔액정합성체크
    public DOBJ CALLbra04_s13_balanceCheck_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra04_s13_balanceCheck_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_balanceCheck_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //계좌 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  B.ACCN_NUM  ,  B.REPT_NUM  ,  B.BALANCE  ,  B.RECV_DAY  ,  B.REPT_DAY  ,  B.REPT_AMT  FROM  (   \n";
        query +=" SELECT  MAX(REPT_DAY  ||  REPT_NUM)  REPT_DAYS  FROM  GIBU.TBRA_REPT_TRANS  WHERE  ACCN_NUM  =  :ACCN_NUM  )  A  ,  GIBU.TBRA_REPT_TRANS  B  WHERE  B.REPT_DAY  ||  REPT_NUM  =  A.REPT_DAYS   \n";
        query +=" AND  B.ACCN_NUM  =  :ACCN_NUM ";
        sobj.setSql(query);
        sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        return sobj;
    }
    //##**$$bra04_s13_balanceCheck
    //##**$$bra04_s13_delCheck
    /* * 프로그램명 : bra04_s13
    * 작성자 : 999999
    * 작성일 : 2009/12/04
    * 설명    : 무통장 입금내역의 삭제 가능여부를 검사한다.
    - 입력된 입금내역이 업소와 매핑이 발생한 경우 수정/삭제 불가능 -> 업소 청구정보/잔액정보에 영향을 미침
    - 입력된 입금내역 이후에 다시 입력내역이 있는 경우 수정/삭제 불가능 -> 전체 잔액에 영향을 미침
    Input
    BRAN_CD (지부 코드)
    REPT_DAY (입금일자)
    REPT_NUM (입금 번호)
    * 수정일 :
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLbra04_s13_delCheck(DOBJ dobj)
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
            dobj  = CALLbra04_s13_delCheck_SEL1(Conn, dobj);           //  삭제체크
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
    public DOBJ CTLbra04_s13_delCheck( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLbra04_s13_delCheck_SEL1(Conn, dobj);           //  삭제체크
        return dobj;
    }
    // 삭제체크
    public DOBJ CALLbra04_s13_delCheck_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLbra04_s13_delCheck_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLbra04_s13_delCheck_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   REPT_DAY = dobj.getRetObject("S").getRecord().get("REPT_DAY");   //입금일자
        String   REPT_NUM = dobj.getRetObject("S").getRecord().get("REPT_NUM");   //입금 번호
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TBRA_REPT  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM   \n";
        query +=" AND  DISTR_GBN  IS  NULL   \n";
        query +=" AND  UPSO_CD  IS  NOT  NULL   \n";
        query +=" AND  REPT_GBN  =  '03')+   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TBRA_REPT_UPSO_DISTR  WHERE  REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  DISTR_GBN  =  A.DISTR_GBN   \n";
        query +=" AND  REPT_GBN  =  '03')+   \n";
        query +=" (SELECT  COUNT(*)  FROM  GIBU.TBRA_REPT_DISTR  WHERE  REPT_DAY  =  A.REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  A.REPT_NUM   \n";
        query +=" AND  DISTR_GBN  =  A.DISTR_GBN   \n";
        query +=" AND  BRAN_CD  =  A.BRAN_CD   \n";
        query +=" AND  REPT_GBN  =  '03')  AS  UPSO_CD,   \n";
        query +=" (SELECT  MAX(REPT_DAY)  FROM  GIBU.TBRA_REPT_TRANS  WHERE  INS_DATE  =   \n";
        query +=" (SELECT  MAX(INS_DATE)  FROM  GIBU.TBRA_REPT_TRANS  WHERE  BRAN_CD  =  :BRAN_CD  )   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  )  AS  REPT_DAY,   \n";
        query +=" (SELECT  MAX(REPT_NUM)  FROM  GIBU.TBRA_REPT_TRANS  WHERE  INS_DATE  =   \n";
        query +=" (SELECT  MAX(INS_DATE)  FROM  GIBU.TBRA_REPT_TRANS  WHERE  BRAN_CD  =  :BRAN_CD  )   \n";
        query +=" AND  BRAN_CD  =  :BRAN_CD  )  AS  REPT_NUM  FROM  GIBU.TBRA_REPT_TRANS  A  WHERE  REPT_DAY  =  :REPT_DAY   \n";
        query +=" AND  REPT_NUM  =  :REPT_NUM ";
        sobj.setSql(query);
        sobj.setString("REPT_DAY", REPT_DAY);               //입금일자
        sobj.setString("REPT_NUM", REPT_NUM);               //입금 번호
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$bra04_s13_delCheck
    //##**$$get_Last_balance
    /*
    */
    public DOBJ CTLget_Last_balance(DOBJ dobj)
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
            dobj  = CALLget_Last_balance_SEL1(Conn, dobj);           //  마지막 계좌 잔고 조회
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
    public DOBJ CTLget_Last_balance( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLget_Last_balance_SEL1(Conn, dobj);           //  마지막 계좌 잔고 조회
        return dobj;
    }
    // 마지막 계좌 잔고 조회
    public DOBJ CALLget_Last_balance_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLget_Last_balance_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLget_Last_balance_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   ACCN_NUM = dobj.getRetObject("S").getRecord().get("ACCN_NUM");   //계좌 번호
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BALANCE  FROM  (   \n";
        query +=" SELECT  NVL(BALANCE,  0)  BALANCE  FROM  GIBU.TBRA_REPT_TRANS  WHERE  ACCN_NUM  =  :ACCN_NUM  ORDER  BY  REPT_DAY  DESC,  REPT_NUM  DESC  )  WHERE  ROWNUM  =  1 ";
        sobj.setSql(query);
        sobj.setString("ACCN_NUM", ACCN_NUM);               //계좌 번호
        return sobj;
    }
    //##**$$get_Last_balance
    //##**$$end
}
