
package komca.fi.bra;
import java.sql.*;
import java.util.*;
import WIZ.FR.COM.*;
import WIZ.FR.DAO.*;
import WIZ.FR.MSG.*;
import WIZ.FR.UTIL.*;
public class bra01_s12
{
    public bra01_s12()
    {
    }
    //##**$$off_upso_list
    /* * 프로그램명 : bra01_s12
    * 작성자 : 서정재
    * 작성일 : 2009/11/24
    * 설명 : 지부별, 신청년월별 오프라인 데이타 업소로 신청된 리스트를 조회한다.
    * 수정일1: 2010.07.14
    * 수정자 : 권남균
    * 수정내용 : 전화번호, 핸드폰 필드 추가.
    메모 필드 테이블 추가 및 조회항목 추가.
    */
    public DOBJ CTLoff_upso_list(DOBJ dobj)
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
            dobj  = CALLoff_upso_list_SEL1(Conn, dobj);           //  오프라인데이타설치업소조회
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
    public DOBJ CTLoff_upso_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLoff_upso_list_SEL1(Conn, dobj);           //  오프라인데이타설치업소조회
        return dobj;
    }
    // 오프라인데이타설치업소조회
    public DOBJ CALLoff_upso_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLoff_upso_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = wutil.substr(dobj.getRetObject("S").getRecord().get("YRMN"),0,6);   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.ESTAB_YRMN  ||'01'  ESTAB_YRMN  ,  TD.DEPT_NM  BRAN_NM  ,  TA.UPSO_CD  ,  TB.UPSO_NM  ,  TC.GRADNM  ,  TC.MONPRNCFEE  ,  TA.SEQ  ,  TA.BSCON_CD  ,  TB.UPSO_NEW_ADDR1  ||  DECODE(TB.UPSO_NEW_ADDR2,  '',  '',  ',  '||TB.UPSO_NEW_ADDR2)  ||  TB.UPSO_REF_INFO  UPSO_ADDR  ,  TB.UPSO_PHON  UPSO_PHON  ,  TB.MNGEMSTR_HPNUM  MNGEMSTR_HPNUM  ,  TA.INS_STAFF_CD  ,  TE.HAN_NM  INS_STAFF_NM  ,  TA.INS_DAY  ,  TA.COL_YN  ,  TA.COL_STAFF_CD  ,  TF.HAN_NM  COL_STAFF_NM  ,  TA.COL_DAY  ,  TB.BRAN_CD  ,  TA.ACMCN_NUM1  ,  TA.ACMCN_NUM2  ,  TA.INSTALL_LOCATION1  ,  TA.INSTALL_LOCATION2  ,  TA.MEMO  ,  TA.MODEL_NM1  ,  TA.MODEL_NM2  FROM  GIBU.TBRA_OFF_UPSO_MNG  TA  ,  GIBU.TBRA_UPSO  TB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  ,  INSA.TCPM_DEPT  TD  ,  INSA.TINS_MST01  TE  ,  INSA.TINS_MST01  TF  WHERE  TA.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  TB.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  TB.BRAN_CD,  'AL',  TB.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TC.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TD.GIBU  =  TB.BRAN_CD   \n";
        query +=" AND  TE.STAFF_NUM(+)  =  TA.INS_STAFF_CD   \n";
        query +=" AND  TF.STAFF_NUM(+)  =  TA.COL_STAFF_CD  ORDER  BY  TB.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$off_upso_list
    //##**$$off_upso_copy
    /* * 프로그램명 : bra01_s12
    * 작성자 : 서정재
    * 작성일 : 2009/11/24
    * 설명 : 해당지부의 데이타를 조건에 주어진 년월로 COPY 해서 입력한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLoff_upso_copy(DOBJ dobj)
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
            if( dobj.getRetObject("S").getRecord().get("BRAN_CD").equals("AL"))
            {
                dobj  = CALLoff_upso_copy_XIUD7(Conn, dobj);           //  전체지부삭제
                dobj  = CALLoff_upso_copy_SEL1(Conn, dobj);           //  복사할데이타조회
                dobj  = CALLoff_upso_copy_INS2(Conn, dobj);           //  데이타복사
                dobj  = CALLoff_upso_copy_SEL3(Conn, dobj);           //  복사결과조회
            }
            else
            {
                dobj  = CALLoff_upso_copy_XIUD5(Conn, dobj);           //  기존데이타삭제
                dobj  = CALLoff_upso_copy_SEL1(Conn, dobj);           //  복사할데이타조회
                dobj  = CALLoff_upso_copy_INS2(Conn, dobj);           //  데이타복사
                dobj  = CALLoff_upso_copy_SEL3(Conn, dobj);           //  복사결과조회
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
    public DOBJ CTLoff_upso_copy( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        if( dobj.getRetObject("S").getRecord().get("BRAN_CD").equals("AL"))
        {
            dobj  = CALLoff_upso_copy_XIUD7(Conn, dobj);           //  전체지부삭제
            dobj  = CALLoff_upso_copy_SEL1(Conn, dobj);           //  복사할데이타조회
            dobj  = CALLoff_upso_copy_INS2(Conn, dobj);           //  데이타복사
            dobj  = CALLoff_upso_copy_SEL3(Conn, dobj);           //  복사결과조회
        }
        else
        {
            dobj  = CALLoff_upso_copy_XIUD5(Conn, dobj);           //  기존데이타삭제
            dobj  = CALLoff_upso_copy_SEL1(Conn, dobj);           //  복사할데이타조회
            dobj  = CALLoff_upso_copy_INS2(Conn, dobj);           //  데이타복사
            dobj  = CALLoff_upso_copy_SEL3(Conn, dobj);           //  복사결과조회
        }
        return dobj;
    }
    // 전체지부삭제
    public DOBJ CALLoff_upso_copy_XIUD7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLoff_upso_copy_XIUD7(dobj, dvobj);
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
    private SQLObject SQLoff_upso_copy_XIUD7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_OFF_UPSO_MNG  \n";
        query +=" WHERE ESTAB_YRMN = :TO_YRMN";
        sobj.setSql(query);
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        return sobj;
    }
    // 복사할데이타조회
    // 복사해올 리스트를 조회한다.
    public DOBJ CALLoff_upso_copy_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLoff_upso_copy_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_copy_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   FROM_YRMN = dobj.getRetObject("S").getRecord().get("FROM_YRMN");   //시작년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.ESTAB_YRMN  ,  A.UPSO_CD  ,  A.SEQ  ,  A.BSCON_CD  ,  A.COL_YN  ,  A.INSTALL_LOCATION1  ,  A.INSTALL_LOCATION2  ,  A.MODEL_NM1  ,  A.MODEL_NM2  ,  B.STAFF_CD  ,  A.ACMCN_NUM1  ,  A.ACMCN_NUM2  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.ESTAB_YRMN  =  :FROM_YRMN   \n";
        query +=" AND  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  B.UPSO_CD  =  A.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("FROM_YRMN", FROM_YRMN);               //시작년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 데이타복사
    // 데이타를 복사할경우 1. INS_STAFF_CD와 INS_DAY 에는 현재 로그인한 사원의 ID와 SYSDATE를 입력해준다. -----------> 추후 수정이 될수도 있다.(INS_STAFF_CD는 해당 업소의 담당사원 ID가 될수 있음을 고려) 2. 복사대상은 수집사원, 수집일까지 포함한다. 3. 권남균. 2010.06.04    복사시 수집여부, 수집일, 수집사원은 복사대상에서 제외한다.
    public DOBJ CALLoff_upso_copy_INS2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS2");
        VOBJ dvobj = dobj.getRetObject("SEL1");           //복사할데이타조회에서 생성시킨 OBJECT입니다.(CALLoff_upso_copy_SEL1)
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLoff_upso_copy_INS2(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("INS2") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_copy_INS2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String INS_DATE ="";        //등록 일시
        String INS_DAY ="";        //등록 일자
        String   ACMCN_NUM2 = dvobj.getRecord().get("ACMCN_NUM2");   //반주기기 번호2
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   ACMCN_NUM1 = dvobj.getRecord().get("ACMCN_NUM1");   //반주기기 번호1
        String   INSTALL_LOCATION1 = dvobj.getRecord().get("INSTALL_LOCATION1");
        String   MODEL_NM1 = dvobj.getRecord().get("MODEL_NM1");   //모델명1
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //관리번호
        String   INSTALL_LOCATION2 = dvobj.getRecord().get("INSTALL_LOCATION2");
        String   MODEL_NM2 = dvobj.getRecord().get("MODEL_NM2");   //모델명2
        String   ESTAB_YRMN = wutil.substr(dobj.getRetObject("S").getRecord().get("TO_YRMN"),0,6);   //설치 년월
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        String   INS_STAFF_CD = dobj.getRetObject("SEL1").getRecord().get("STAFF_CD");   //등록 사원 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_OFF_UPSO_MNG (ESTAB_YRMN, MODEL_NM2, INSPRES_ID, INSTALL_LOCATION2, INS_STAFF_CD, INS_DAY, SEQ, BSCON_CD, INS_DATE, MODEL_NM1, INSTALL_LOCATION1, ACMCN_NUM1, UPSO_CD, ACMCN_NUM2)  \n";
        query +=" values(:ESTAB_YRMN , :MODEL_NM2 , :INSPRES_ID , :INSTALL_LOCATION2 , :INS_STAFF_CD , TO_CHAR(SYSDATE, 'YYYYMMDD'), :SEQ , :BSCON_CD , SYSDATE, :MODEL_NM1 , :INSTALL_LOCATION1 , :ACMCN_NUM1 , :UPSO_CD , :ACMCN_NUM2 )";
        sobj.setSql(query);
        sobj.setString("ACMCN_NUM2", ACMCN_NUM2);               //반주기기 번호2
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("ACMCN_NUM1", ACMCN_NUM1);               //반주기기 번호1
        sobj.setString("INSTALL_LOCATION1", INSTALL_LOCATION1);
        sobj.setString("MODEL_NM1", MODEL_NM1);               //모델명1
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("INSTALL_LOCATION2", INSTALL_LOCATION2);
        sobj.setString("MODEL_NM2", MODEL_NM2);               //모델명2
        sobj.setString("ESTAB_YRMN", ESTAB_YRMN);               //설치 년월
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        sobj.setString("INS_STAFF_CD", INS_STAFF_CD);               //등록 사원 코드
        return sobj;
    }
    // 복사결과조회
    // 복사된 결과를 조회한다.
    public DOBJ CALLoff_upso_copy_SEL3(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL3");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL3");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLoff_upso_copy_SEL3(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL3");
        rvobj.setRetcode(1);
        rvobj.Println("SEL3");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_copy_SEL3(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  TA.ESTAB_YRMN  ||'01'  ESTAB_YRMN  ,  TD.DEPT_NM  BRAN_NM  ,  TA.UPSO_CD  ,  TB.UPSO_NM  ,  TC.GRADNM  ,  TA.SEQ  ,  TA.BSCON_CD  ,  TA.INS_STAFF_CD  ,  TE.HAN_NM  INS_STAFF_NM  ,  TA.INS_DAY  ,  DECODE(TA.COL_YN  ,  'Y',  '1')  COL_YN  ,  TA.COL_STAFF_CD  ,  TF.HAN_NM  COL_STAFF_NM  ,  TA.COL_DAY  ,  TB.BRAN_CD  ,  TB.UPSO_NEW_ADDR1  ||  DECODE(TB.UPSO_NEW_ADDR2,  '',  '',  ',  '||TB.UPSO_NEW_ADDR2)  ||  TB.UPSO_REF_INFO  UPSO_ADDR  ,  TA.INSTALL_LOCATION1  ,  TA.INSTALL_LOCATION2  ,  TA.ACMCN_NUM1  ,  TA.ACMCN_NUM2  ,  TA.MODEL_NM1  ,  TA.MODEL_NM2  ,  TB.UPSO_PHON  UPSO_PHON  ,  TB.MNGEMSTR_HPNUM  MNGEMSTR_HPNUM  FROM  GIBU.TBRA_OFF_UPSO_MNG  TA  ,  GIBU.TBRA_UPSO  TB  ,  (   \n";
        query +=" SELECT  ZA.UPSO_CD,  ZB.MONPRNCFEE,  ZC.GRADNM  FROM(   \n";
        query +=" SELECT  A.UPSO_CD,  MAX(A.JOIN_SEQ)  JOIN_SEQ  FROM  GIBU.TBRA_UPSORTAL_INFO  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD  GROUP  BY  A.UPSO_CD  )  ZA  ,  GIBU.TBRA_UPSORTAL_INFO  ZB  ,  GIBU.TBRA_BSTYPGRAD  ZC  WHERE  ZB.JOIN_SEQ  =  ZA.JOIN_SEQ   \n";
        query +=" AND  ZB.UPSO_CD  =  ZA.UPSO_CD   \n";
        query +=" AND  ZC.BSTYP_CD  =  ZB.BSTYP_CD   \n";
        query +=" AND  ZC.GRAD_GBN  =  ZB.UPSO_GRAD  )  TC  ,  INSA.TCPM_DEPT  TD  ,  INSA.TINS_MST01  TE  ,  INSA.TINS_MST01  TF  WHERE  TA.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  TB.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  TB.BRAN_CD,  'AL',  TB.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  TB.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TC.UPSO_CD  =  TA.UPSO_CD   \n";
        query +=" AND  TD.GIBU  =  TB.BRAN_CD   \n";
        query +=" AND  TE.STAFF_NUM(+)  =  TA.INS_STAFF_CD   \n";
        query +=" AND  TF.STAFF_NUM(+)  =  TA.COL_STAFF_CD  ORDER  BY  TB.BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    // 기존데이타삭제
    // 해당년월의 기존데이타를 삭제한다.
    public DOBJ CALLoff_upso_copy_XIUD5(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("XIUD5");
        VOBJ dvobj = dobj.getRetObject("S");            //사용자 화면에서 발생한 Object입니다.
        dvobj.Println("XIUD5");
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLoff_upso_copy_XIUD5(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("XIUD5");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_copy_XIUD5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        String   TO_YRMN = dobj.getRetObject("S").getRecord().get("TO_YRMN");   //종료년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" DELETE FROM GIBU.TBRA_OFF_UPSO_MNG  \n";
        query +=" WHERE ESTAB_YRMN = :TO_YRMN  \n";
        query +=" AND UPSO_CD IN ( SELECT UPSO_CD FROM GIBU.TBRA_UPSO  \n";
        query +=" WHERE BRAN_CD = DECODE(:BRAN_CD, null, BRAN_CD, 'AL', BRAN_CD, :BRAN_CD))";
        sobj.setSql(query);
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        return sobj;
    }
    //##**$$off_upso_copy
    //##**$$off_upso_dup_chk
    /* * 프로그램명 : bra01_s12
    * 작성자 : 서정재
    * 작성일 : 2009/11/24
    * 설명 :
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLoff_upso_dup_chk(DOBJ dobj)
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
            dobj  = CALLoff_upso_dup_chk_SEL1(Conn, dobj);           //  복사할테이타중복체크
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
    public DOBJ CTLoff_upso_dup_chk( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLoff_upso_dup_chk_SEL1(Conn, dobj);           //  복사할테이타중복체크
        return dobj;
    }
    // 복사할테이타중복체크
    public DOBJ CALLoff_upso_dup_chk_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLoff_upso_dup_chk_SEL1(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_dup_chk_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   TO_YRMN = wutil.substr(dobj.getRetObject("S").getRecord().get("TO_YRMN"),0,6);   //종료년월
        String   BRAN_CD = dobj.getRetObject("S").getRecord().get("BRAN_CD");   //지부 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  COUNT(*)  CNT  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  B.BRAN_CD  =  DECODE(:BRAN_CD,  NULL,  B.BRAN_CD,  'AL',  B.BRAN_CD,  :BRAN_CD)   \n";
        query +=" AND  A.ESTAB_YRMN  =  :TO_YRMN   \n";
        query +=" AND  A.UPSO_CD  =  B.UPSO_CD ";
        sobj.setSql(query);
        sobj.setString("TO_YRMN", TO_YRMN);               //종료년월
        sobj.setString("BRAN_CD", BRAN_CD);               //지부 코드
        return sobj;
    }
    //##**$$off_upso_dup_chk
    //##**$$off_upso_mng
    /* * 프로그램명 : bra01_s12
    * 작성자 : 서정재
    * 작성일 : 2009/11/02
    * 설명 : 오프라인데이타 설치업소데이타를 신규등록/수정/삭제한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLoff_upso_mng(DOBJ dobj)
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
            dobj  = CALLoff_upso_mng_MIUD1(Conn, dobj);           //  건별처리
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
    public DOBJ CTLoff_upso_mng( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLoff_upso_mng_MIUD1(Conn, dobj);           //  건별처리
        if(dobj.getRtncode() == 9)
        {
            Conn.rollback();
            return dobj;
        }
        return dobj;
    }
    // 건별처리
    public DOBJ CALLoff_upso_mng_MIUD1(Connection Conn, DOBJ dobj) throws Exception
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
                dobj  = CALLoff_upso_mng_INS5(Conn, dobj);           //  저장
                dobj  = CALLoff_upso_mng_INS9(Conn, dobj);           //  KDS_SHOP에 INSERT
                dobj  = CALLoff_upso_mng_INS10(Conn, dobj);           //  KDS_SHOPROOM에 INSERT
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("U"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLoff_upso_mng_UPD6(Conn, dobj);           //  수정
            }
            else if(dvobj.getRecord().get("IUDFLAG").equals("D"))
            {
                dobj.setRetObject(dvobj.getRecVobj("R"));
                dobj  = CALLoff_upso_mng_DEL7(Conn, dobj);           //  삭제
                dobj  = CALLoff_upso_mng_DEL13(Conn, dobj);           //  KDS_SHOP에 DELETE
                dobj  = CALLoff_upso_mng_DEL14(Conn, dobj);           //  KDS_SHOPROOM에 DELETE
            }
        }
        return dobj;
    }
    // 삭제
    // 기존데이타를 삭제한다
    public DOBJ CALLoff_upso_mng_DEL7(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLoff_upso_mng_DEL7(dobj, dvobj);
            qexe.DispSelectSql(sobj);
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
    private SQLObject SQLoff_upso_mng_DEL7(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ESTAB_YRMN ="";        //설치 년월
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //관리번호
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   ESTAB_YRMN_1 = dobj.getRetObject("R").getRecord().get("ESTAB_YRMN");   //설치 년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from GIBU.TBRA_OFF_UPSO_MNG  \n";
        query +=" where ESTAB_YRMN=substr(:ESTAB_YRMN_1, 0, 6)  \n";
        query +=" and UPSO_CD=:UPSO_CD  \n";
        query +=" and SEQ=:SEQ";
        sobj.setSql(query);
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("ESTAB_YRMN_1", ESTAB_YRMN_1);               //설치 년월
        return sobj;
    }
    // 저장
    // 신규데이타를 등록한다
    public DOBJ CALLoff_upso_mng_INS5(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLoff_upso_mng_INS5(dobj, dvobj);
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
    private SQLObject SQLoff_upso_mng_INS5(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ESTAB_YRMN ="";        //설치 년월
        String INS_DATE ="";        //등록 일시
        String   ACMCN_NUM2 = dvobj.getRecord().get("ACMCN_NUM2");   //반주기기 번호2
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   COL_DAY = dvobj.getRecord().get("COL_DAY");   //수집 일자
        String   COL_STAFF_CD = dvobj.getRecord().get("COL_STAFF_CD");   //수집 사원 코드
        String   ACMCN_NUM1 = dvobj.getRecord().get("ACMCN_NUM1");   //반주기기 번호1
        String   MEMO = dvobj.getRecord().get("MEMO");   //메모
        String   INSTALL_LOCATION1 = dvobj.getRecord().get("INSTALL_LOCATION1");
        String   MODEL_NM1 = dvobj.getRecord().get("MODEL_NM1");   //모델명1
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //관리번호
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //등록 일자
        String   INS_STAFF_CD = dvobj.getRecord().get("INS_STAFF_CD");   //등록 사원 코드
        String   INSTALL_LOCATION2 = dvobj.getRecord().get("INSTALL_LOCATION2");
        String   MODEL_NM2 = dvobj.getRecord().get("MODEL_NM2");   //모델명2
        String   COL_YN = dvobj.getRecord().get("COL_YN");   //수집 여부
        String   ESTAB_YRMN_1 = dobj.getRetObject("R").getRecord().get("ESTAB_YRMN");   //설치 년월
        String   INSPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //등록자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_OFF_UPSO_MNG (ESTAB_YRMN, COL_YN, MODEL_NM2, INSPRES_ID, INSTALL_LOCATION2, INS_STAFF_CD, INS_DAY, SEQ, BSCON_CD, MODEL_NM1, INS_DATE, INSTALL_LOCATION1, MEMO, ACMCN_NUM1, COL_STAFF_CD, COL_DAY, UPSO_CD, ACMCN_NUM2)  \n";
        query +=" values(substr(:ESTAB_YRMN_1, 0, 6), :COL_YN , :MODEL_NM2 , :INSPRES_ID , :INSTALL_LOCATION2 , :INS_STAFF_CD , :INS_DAY , :SEQ , :BSCON_CD , :MODEL_NM1 , SYSDATE, :INSTALL_LOCATION1 , :MEMO , :ACMCN_NUM1 , :COL_STAFF_CD , :COL_DAY , :UPSO_CD , :ACMCN_NUM2 )";
        sobj.setSql(query);
        sobj.setString("ACMCN_NUM2", ACMCN_NUM2);               //반주기기 번호2
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("COL_DAY", COL_DAY);               //수집 일자
        sobj.setString("COL_STAFF_CD", COL_STAFF_CD);               //수집 사원 코드
        sobj.setString("ACMCN_NUM1", ACMCN_NUM1);               //반주기기 번호1
        sobj.setString("MEMO", MEMO);               //메모
        sobj.setString("INSTALL_LOCATION1", INSTALL_LOCATION1);
        sobj.setString("MODEL_NM1", MODEL_NM1);               //모델명1
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("INS_DAY", INS_DAY);               //등록 일자
        sobj.setString("INS_STAFF_CD", INS_STAFF_CD);               //등록 사원 코드
        sobj.setString("INSTALL_LOCATION2", INSTALL_LOCATION2);
        sobj.setString("MODEL_NM2", MODEL_NM2);               //모델명2
        sobj.setString("COL_YN", COL_YN);               //수집 여부
        sobj.setString("ESTAB_YRMN_1", ESTAB_YRMN_1);               //설치 년월
        sobj.setString("INSPRES_ID", INSPRES_ID);               //등록자 ID
        return sobj;
    }
    // 수정
    // 기존데이타를 수정한다
    public DOBJ CALLoff_upso_mng_UPD6(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLoff_upso_mng_UPD6(dobj, dvobj);
            qexe.DispSelectSql(sobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("UPD6") ;
        rvobj.Println("UPD6");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_mng_UPD6(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String ESTAB_YRMN ="";        //설치 년월
        String MOD_DATE ="";        //수정 일시
        String   ACMCN_NUM2 = dvobj.getRecord().get("ACMCN_NUM2");   //반주기기 번호2
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   COL_DAY = dvobj.getRecord().get("COL_DAY");   //수집 일자
        String   COL_STAFF_CD = dvobj.getRecord().get("COL_STAFF_CD");   //수집 사원 코드
        String   ACMCN_NUM1 = dvobj.getRecord().get("ACMCN_NUM1");   //반주기기 번호1
        String   MEMO = dvobj.getRecord().get("MEMO");   //메모
        String   INSTALL_LOCATION1 = dvobj.getRecord().get("INSTALL_LOCATION1");
        String   MODEL_NM1 = dvobj.getRecord().get("MODEL_NM1");   //모델명1
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        double   SEQ = dvobj.getRecord().getDouble("SEQ");   //관리번호
        String   INS_DAY = dvobj.getRecord().get("INS_DAY");   //등록 일자
        String   INS_STAFF_CD = dvobj.getRecord().get("INS_STAFF_CD");   //등록 사원 코드
        String   INSTALL_LOCATION2 = dvobj.getRecord().get("INSTALL_LOCATION2");
        String   MODEL_NM2 = dvobj.getRecord().get("MODEL_NM2");   //모델명2
        String   COL_YN = dvobj.getRecord().get("COL_YN");   //수집 여부
        String   ESTAB_YRMN_1 = dobj.getRetObject("R").getRecord().get("ESTAB_YRMN");   //설치 년월
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Update GIBU.TBRA_OFF_UPSO_MNG  \n";
        query +=" set COL_YN=:COL_YN , MODPRES_ID=:MODPRES_ID , MODEL_NM2=:MODEL_NM2 , INSTALL_LOCATION2=:INSTALL_LOCATION2 , INS_STAFF_CD=:INS_STAFF_CD , INS_DAY=:INS_DAY , BSCON_CD=:BSCON_CD , MODEL_NM1=:MODEL_NM1 , INSTALL_LOCATION1=:INSTALL_LOCATION1 , MEMO=:MEMO , ACMCN_NUM1=:ACMCN_NUM1 , COL_STAFF_CD=:COL_STAFF_CD , COL_DAY=:COL_DAY , ACMCN_NUM2=:ACMCN_NUM2 , MOD_DATE=SYSDATE  \n";
        query +=" where ESTAB_YRMN=substr(:ESTAB_YRMN_1, 0, 6)  \n";
        query +=" and SEQ=:SEQ  \n";
        query +=" and UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("ACMCN_NUM2", ACMCN_NUM2);               //반주기기 번호2
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("COL_DAY", COL_DAY);               //수집 일자
        sobj.setString("COL_STAFF_CD", COL_STAFF_CD);               //수집 사원 코드
        sobj.setString("ACMCN_NUM1", ACMCN_NUM1);               //반주기기 번호1
        sobj.setString("MEMO", MEMO);               //메모
        sobj.setString("INSTALL_LOCATION1", INSTALL_LOCATION1);
        sobj.setString("MODEL_NM1", MODEL_NM1);               //모델명1
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setDouble("SEQ", SEQ);               //관리번호
        sobj.setString("INS_DAY", INS_DAY);               //등록 일자
        sobj.setString("INS_STAFF_CD", INS_STAFF_CD);               //등록 사원 코드
        sobj.setString("INSTALL_LOCATION2", INSTALL_LOCATION2);
        sobj.setString("MODEL_NM2", MODEL_NM2);               //모델명2
        sobj.setString("COL_YN", COL_YN);               //수집 여부
        sobj.setString("ESTAB_YRMN_1", ESTAB_YRMN_1);               //설치 년월
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // KDS_SHOP에 DELETE
    public DOBJ CALLoff_upso_mng_DEL13(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("DEL13");
        VOBJ dvobj = dobj.getRetObject("R");           //MULTI-분기처리(MPD,MIUD)의 Currect Record Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLoff_upso_mng_DEL13(dobj, dvobj);
            updcnt += qexe.executeUpdate(Conn, sobj);
        }
        rvobj = new VOBJ();
        HashMap recordx = new HashMap();
        recordx.put("UPDCNT",updcnt+"");
        rvobj.addRecord(recordx);
        rvobj.setName("DEL13") ;
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_mng_DEL13(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from LOG.KDS_SHOP  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // KDS_SHOP에 INSERT
    public DOBJ CALLoff_upso_mng_INS9(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLoff_upso_mng_INS9(dobj, dvobj);
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
    private SQLObject SQLoff_upso_mng_INS9(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String MOD_DATE ="";        //수정 일시
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   MODPRES_ID = dobj.getRetObject("GOV").getRecord().get("USER_ID");   //수정자 ID
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOP (MODPRES_ID, UPSO_CD, MOD_DATE)  \n";
        query +=" values(:MODPRES_ID , :UPSO_CD , SYSDATE)";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("MODPRES_ID", MODPRES_ID);               //수정자 ID
        return sobj;
    }
    // KDS_SHOPROOM에 DELETE
    public DOBJ CALLoff_upso_mng_DEL14(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLoff_upso_mng_DEL14(dobj, dvobj);
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
    private SQLObject SQLoff_upso_mng_DEL14(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Delete from LOG.KDS_SHOPROOM  \n";
        query +=" where UPSO_CD=:UPSO_CD";
        sobj.setSql(query);
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        return sobj;
    }
    // KDS_SHOPROOM에 INSERT
    public DOBJ CALLoff_upso_mng_INS10(Connection Conn, DOBJ dobj) throws Exception
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
            sobj = SQLoff_upso_mng_INS10(dobj, dvobj);
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
    private SQLObject SQLoff_upso_mng_INS10(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String REG_DATE ="";        //등록일시
        double SEQ = 0;        //관리번호
        String   BSCON_CD = dvobj.getRecord().get("BSCON_CD");   //거래처 코드
        String   UPSO_CD = dvobj.getRecord().get("UPSO_CD");   //업소 코드
        String   CO_STATUS ="07001";   //운영상태
        String   ROOM_NAME ="임시방번호";   //방 이름
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into LOG.KDS_SHOPROOM (CO_STATUS, UPSO_CD, SEQ, REG_DATE, BSCON_CD, ROOM_NAME)  \n";
        query +=" values(:CO_STATUS , :UPSO_CD , LOG.IMT_KDSSR_SEQ.NEXTVAL, SYSDATE, :BSCON_CD , :ROOM_NAME )";
        sobj.setSql(query);
        sobj.setString("BSCON_CD", BSCON_CD);               //거래처 코드
        sobj.setString("UPSO_CD", UPSO_CD);               //업소 코드
        sobj.setString("CO_STATUS", CO_STATUS);               //운영상태
        sobj.setString("ROOM_NAME", ROOM_NAME);               //방 이름
        return sobj;
    }
    //##**$$off_upso_mng
    //##**$$excel_save_list
    /*
    */
    public DOBJ CTLexcel_save_list(DOBJ dobj)
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
            dobj  = CALLexcel_save_list_SEL1(Conn, dobj);           //  엑셀저장리스트
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
    public DOBJ CTLexcel_save_list( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLexcel_save_list_SEL1(Conn, dobj);           //  엑셀저장리스트
        return dobj;
    }
    // 엑셀저장리스트
    public DOBJ CALLexcel_save_list_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLexcel_save_list_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.setRetcode(1);
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLexcel_save_list_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  A.SAVE_DAY  ,  A.SAVE_SEQ  ,  A.STAFF_CD  ,  B.HAN_NM  STAFF_NM  ,  C.DEPT_CD  ,  C.DEPT_NM  FROM  GIBU.TBRA_OFF_UPSO_SAVE_HISTY  A  ,  INSA.TINS_MST01  B  ,  INSA.TCPM_DEPT  C  WHERE  A.STAFF_CD  =  B.STAFF_NUM   \n";
        query +=" AND  A.DEPT_CD  =  B.DEPT_CD   \n";
        query +=" AND  B.DEPT_CD  =  C.DEPT_CD  ORDER  BY  A.SAVE_DAY  DESC,  A.SAVE_SEQ  ASC ";
        sobj.setSql(query);
        return sobj;
    }
    //##**$$excel_save_list
    //##**$$off_upso_save_histy
    /* * 프로그램명 : bra01_s12
    * 작성자 : 서정재
    * 작성일 : 2010/05/23
    * 설명 : 오프라인 설치업소 엑셀저장 이력
    누가, 언제 저장했는지 이력을 저장한다.
    * 수정일1:
    * 수정자 :
    * 수정내용 :
    */
    public DOBJ CTLoff_upso_save_histy(DOBJ dobj)
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
            dobj  = CALLoff_upso_save_histy_SEL2(Conn, dobj);           //  SAVE_SEQ구하기
            dobj  = CALLoff_upso_save_histy_INS1(Conn, dobj);           //  엑셀저장이력
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
    public DOBJ CTLoff_upso_save_histy( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLoff_upso_save_histy_SEL2(Conn, dobj);           //  SAVE_SEQ구하기
        dobj  = CALLoff_upso_save_histy_INS1(Conn, dobj);           //  엑셀저장이력
        return dobj;
    }
    // SAVE_SEQ구하기
    public DOBJ CALLoff_upso_save_histy_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLoff_upso_save_histy_SEL2(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_save_histy_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  NVL(MAX(SAVE_SEQ),0)+1  SAVE_SEQ  FROM  GIBU.TBRA_OFF_UPSO_SAVE_HISTY  WHERE  SAVE_DAY  =  TO_CHAR(SYSDATE,'YYYYMMDD') ";
        sobj.setSql(query);
        return sobj;
    }
    // 엑셀저장이력
    public DOBJ CALLoff_upso_save_histy_INS1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("INS1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = null;
        VOBJ       rvobj= null;
        int        updcnt =0;
        dvobj.first();
        while(dvobj.next())
        {
            sobj = SQLoff_upso_save_histy_INS1(dobj, dvobj);
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
    private SQLObject SQLoff_upso_save_histy_INS1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String SAVE_DAY ="";        //저장 일자
        String   STAFF_CD = dvobj.getRecord().get("STAFF_CD");   //사원 코드
        String   DEPT_CD = dvobj.getRecord().get("DEPT_CD");   //부서 코드
        double   SAVE_SEQ = dobj.getRetObject("SEL2").getRecord().getDouble("SAVE_SEQ");   //저장 순번
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" Insert into GIBU.TBRA_OFF_UPSO_SAVE_HISTY (DEPT_CD, STAFF_CD, SAVE_DAY, SAVE_SEQ)  \n";
        query +=" values(:DEPT_CD , :STAFF_CD , TO_CHAR(SYSDATE, 'YYYYMMDD'), :SAVE_SEQ )";
        sobj.setSql(query);
        sobj.setString("STAFF_CD", STAFF_CD);               //사원 코드
        sobj.setString("DEPT_CD", DEPT_CD);               //부서 코드
        sobj.setDouble("SAVE_SEQ", SAVE_SEQ);               //저장 순번
        return sobj;
    }
    //##**$$off_upso_save_histy
    //##**$$off_upso_stat
    /*
    */
    public DOBJ CTLoff_upso_stat(DOBJ dobj)
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
            dobj  = CALLoff_upso_stat_SEL1(Conn, dobj);           //  숫자통계
            dobj  = CALLoff_upso_stat_SEL2(Conn, dobj);           //  비고
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
    public DOBJ CTLoff_upso_stat( DOBJ dobj, Object Connx) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        Connection Conn = (Connection)Connx;
        dobj  = CALLoff_upso_stat_SEL1(Conn, dobj);           //  숫자통계
        dobj  = CALLoff_upso_stat_SEL2(Conn, dobj);           //  비고
        return dobj;
    }
    // 숫자통계
    public DOBJ CALLoff_upso_stat_SEL1(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL1");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL1");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLoff_upso_stat_SEL1(dobj, dvobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL1");
        rvobj.Println("SEL1");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_stat_SEL1(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT  BRAN_CD,  BRAN_NM  ,  :YRMN  AS  YRMN,  '  '  AS  MEMO  ,  SUM(DECODE(GRAD,  'o',  DECODE(BSCON_CD,  'E0006',  1,  0),  0))  AS  VAL_ALL_O_KY  ,  SUM(DECODE(GRAD,  'o',  DECODE(BSCON_CD,  'E0003',  1,  0),  0))  AS  VAL_ALL_O_TJ  ,  SUM(DECODE(GRAD,  'k',  DECODE(BSCON_CD,  'E0006',  1,  0),  0))  AS  VAL_ALL_K_KY  ,  SUM(DECODE(GRAD,  'k',  DECODE(BSCON_CD,  'E0003',  1,  0),  0))  AS  VAL_ALL_K_TJ  ,  SUM(DECODE(GRAD,  'l',  DECODE(BSCON_CD,  'E0006',  1,  0),  0))  AS  VAL_ALL_L_KY  ,  SUM(DECODE(GRAD,  'l',  DECODE(BSCON_CD,  'E0003',  1,  0),  0))  AS  VAL_ALL_L_TJ  ,  SUM(DECODE(COL_YN,  '1',  DECODE(GRAD,  'o',  DECODE(BSCON_CD,  'E0006',  1,  0),  0),  0))  AS  VAL_COL_O_KY  ,  SUM(DECODE(COL_YN,  '1',  DECODE(GRAD,  'o',  DECODE(BSCON_CD,  'E0003',  1,  0),  0),  0))  AS  VAL_COL_O_TJ  ,  SUM(DECODE(COL_YN,  '1',  DECODE(GRAD,  'k',  DECODE(BSCON_CD,  'E0006',  1,  0),  0),  0))  AS  VAL_COL_K_KY  ,  SUM(DECODE(COL_YN,  '1',  DECODE(GRAD,  'k',  DECODE(BSCON_CD,  'E0003',  1,  0),  0),  0))  AS  VAL_COL_K_TJ  ,  SUM(DECODE(COL_YN,  '1',  DECODE(GRAD,  'l',  DECODE(BSCON_CD,  'E0006',  1,  0),  0),  0))  AS  VAL_COL_L_KY  ,  SUM(DECODE(COL_YN,  '1',  DECODE(GRAD,  'l',  DECODE(BSCON_CD,  'E0003',  1,  0),  0),  0))  AS  VAL_COL_L_TJ  FROM   \n";
        query +=" (SELECT  B.BRAN_CD  ,  C.DEPT_NM  AS  BRAN_NM  ,  A.UPSO_CD  ,  A.COL_YN  ,  A.BSCON_CD  ,  SUBSTR(GIBU.FT_GET_UPSORTAL_INFO(A.UPSO_CD),1,1)  GRAD  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  ,  INSA.TCPM_DEPT  C  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  C.GIBU  =  B.BRAN_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN)  GROUP  BY  BRAN_CD,  BRAN_NM  ORDER  BY  BRAN_CD ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        return sobj;
    }
    // 비고
    public DOBJ CALLoff_upso_stat_SEL2(Connection Conn, DOBJ dobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        dobj.setRtnname("SEL2");
        QExecutor qexe = new QExecutor(dobj);
        dobj.setRtnname("SEL2");
        VOBJ dvobj = dobj.getRetObject("S");           //사용자 화면에서 발생한 Object입니다.
        SQLObject  sobj = SQLoff_upso_stat_SEL2(dobj, dvobj);
        qexe.DispSelectSql(sobj);
        VOBJ rvobj = qexe.executeQuery(Conn, sobj);
        rvobj.setName("SEL2");
        rvobj.Println("SEL2");
        dobj.setRetObject(rvobj);
        return dobj;
    }
    private SQLObject SQLoff_upso_stat_SEL2(DOBJ dobj, VOBJ dvobj) throws Exception
    {
        WizUtil wutil = new WizUtil(dobj);
        String   YRMN = dobj.getRetObject("S").getRecord().get("YRMN");   //년월
        SQLObject sobj = new SQLObject();
        String    query="";
        query +=" SELECT   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'A'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_A,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'B'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_B,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'E'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_E,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'F'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_F,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'G'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_G,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'I'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_I,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'K'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_K,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'L'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_L,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'M'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_M,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'N'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_N,   \n";
        query +=" (SELECT  MAX(SYS_CONNECT_BY_PATH  (MEMO  ,  '|'))  FROM  (   \n";
        query +=" SELECT  BRAN_CD,  MEMO,  ROWNUM  RNUM  FROM  (   \n";
        query +=" SELECT  B.BRAN_CD,  A.MEMO  ||  ':'  ||  COUNT(0)  AS  MEMO  FROM  GIBU.TBRA_OFF_UPSO_MNG  A  ,  GIBU.TBRA_UPSO  B  WHERE  A.UPSO_CD  =  B.UPSO_CD   \n";
        query +=" AND  A.ESTAB_YRMN  =  :YRMN   \n";
        query +=" AND  B.BRAN_CD  =  'O'   \n";
        query +=" AND  MEMO  IS  NOT  NULL  GROUP  BY  B.BRAN_CD,  MEMO  ORDER  BY  MEMO  )  )  START  WITH  RNUM  =  1  CONNECT  BY  PRIOR  RNUM  +  1  =  RNUM)  AS  MEMO_O  FROM  DUAL ";
        sobj.setSql(query);
        sobj.setString("YRMN", YRMN);               //년월
        return sobj;
    }
    //##**$$off_upso_stat
    //##**$$end
}
